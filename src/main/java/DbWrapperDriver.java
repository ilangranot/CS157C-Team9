import org.neo4j.driver.*;
import org.neo4j.driver.Result;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Relationship;


public class DbWrapperDriver implements DbWrapper, AutoCloseable {
    private final Driver driver;


    public DbWrapperDriver(String uri, String user, String password )
    {
        driver = GraphDatabase.driver( uri, AuthTokens.basic( user, password ) );
    }

    //  Wrapping a Cypher Query in a Managed Transaction provides atomicity
    //             and makes handling errors much easier.
    //             Use `session.writeTransaction` for writes and `session.readTransaction` for reading data.
    //             These methods are also able to handle connection problems and transient errors using an automatic retry mechanism.
//    private Result runQuery(String query, Value parameters)
//    {
//        Result result = null;
//        // Sessions are lightweight and disposable connection wrappers.
//        try (Session session = driver.session())
//        {
//            result = session.writeTransaction(tx -> tx.run(query, parameters));
//        }
//        return result;
//    }


    public void deleteNode(NodeLabel nodeLabel, String key, Object value){
        String query = "MATCH (n:" + nodeLabel + " {"+ key + ": $" + key + "} DELETE n";
        try (Session session = driver.session()) {
            session.writeTransaction(
                    transaction -> transaction.run(query, Values.parameters(key, value)));
        } catch (Exception exception) {
            System.out.println("At DbWrapperDriver.createNodeIfNotExists");
            System.out.println(exception.getMessage());
            System.out.println(exception.getStackTrace());
        }
    }

    public String deleteEntireDb(){
        Result result;
        try ( Session session = driver.session() )
        {
            String query =  "MATCH (n) DETACH DELETE n";
            result = session.run(query);
        }
        return result.consume().toString();
    }


    // TODO: change Label into list of labels and to multiple key values
    @Override
    public Node createNodeIfNotExists(NodeLabel nodeLabel, String key, Object value){
        Node node = null;
        String query = "MERGE (n:" + nodeLabel +" {"+ key + ": $" + key + "}) RETURN n";
        try (Session session = driver.session()) {
            node = session.writeTransaction(
                    transaction -> transaction.run(query, Values.parameters(key, value)).single().get(0).asNode());
        } catch (Exception exception) {
            System.out.println("At DbWrapperDriver.createNodeIfNotExists");
            System.out.println(exception.getMessage());
            System.out.println(exception.getStackTrace());
        } finally {
            return node;
        }
    }

    @Override
    public void createRelationshipIfNotExists(
            TransitionType transitionType, String propertyA, String propertyB,
            NodeLabel nodeLabel, String matchProperty, String key, String value) {
        String query = "MERGE (a:" + nodeLabel +" {"+ matchProperty + ": '" + propertyA + "'}) " +
                "MERGE (b:" + nodeLabel +" {"+ matchProperty + ": '" + propertyB + "'}) " +
                " MERGE (a)-[r: " + transitionType.name() +"]->(b)" +
                "SET r." + key + "=coalesce(r." + key + ", []) +  $" + key +
                " RETURN type(r)";
//        String query = " MERGE (a:" + nodeLabel + " {url: '" + propertyA + "'})-[r: " + transitionType.name() +
//                " {"+ key + ": $" + key + "}]->(b:" + nodeLabel + " {url: '" + propertyB + "'})" +
//                " RETURN type(r)";
        try (Session session = driver.session()) {
            session.writeTransaction(tx -> tx.run(query, Values.parameters(key, value)));
        } catch (Exception exception) {
            System.out.println("At DbWrapperDriver.createRelationshipIfNotExists 1");
            System.out.println(exception.getMessage());
            System.out.println(exception.getCause());
            System.out.println(exception.getStackTrace());
        }
    }

    @Override
    public void createRelationshipIfNotExists(
            TransitionType transitionType, Node nodeA, Node nodeB,
            NodeLabel nodeLabel, String matchProperty, String key, String value) {
        Relationship relationship = null;
        String query = "MERGE (n:" + nodeLabel +" {"+ matchProperty + ": $" + nodeA.get(matchProperty) + "}) " +
                "MERGE (n:" + nodeLabel +" {"+ matchProperty + ": $" + nodeB.get(matchProperty) + "}) " +
                " MERGE (a)-[r: " + transitionType.name() +"]->(b)" +
                "SET r." + key + "=coalesce(r." + key + ", []) +  $" + key +
                " RETURN type(r)";
//            String query = "MATCH (a:Page {url: " + nodeA.get(matchProperty) + "}), (b:Page{url: " + nodeB.get(matchProperty) + "})" +
//                    " MERGE (a)-[r: " + transitionType.name() +"]->(b)" +
//                    "SET r." + key + "=coalesce(r." + key + ", []) +  $" + key +
//                    " RETURN type(r)";
        try (Session session = driver.session()) {
            session.writeTransaction(tx -> tx.run(query, Values.parameters(key, value)));
        } catch (Exception exception) {
            System.out.println("At DbWrapperDriver.createRelationshipIfNotExists 2");
            System.out.println(exception.getMessage());
            System.out.println(exception.getCause());
            System.out.println(exception.getStackTrace());
        }
    }

    // TODO: Too lazy to generalize ...
    @Override
    public void createConstraintsIndexes(NodeLabel nodeLabel, String key) {
        try (Session session = driver.session()) {
            String query = "CREATE CONSTRAINT unique_webpages_url IF NOT EXISTS ON (n: " + nodeLabel + ") ASSERT n." + key + " IS UNIQUE";
            session.run(query);
            query = "CREATE INDEX index_webpages_url IF NOT EXISTS FOR (n:" + nodeLabel + ") ON (n." + key + ")";
            session.run(query);
        } catch (Exception exception){
            System.out.println("At DbWrapperDriver.assertConstraints");
            System.out.println(exception.getMessage());
            System.out.println(exception.getCause());
            System.out.println(exception.getStackTrace());
        }
    }

    @Override
    public void assertConstraintsIndexes(NodeLabel nodeLabel, String key) {
        try (Session session = driver.session()) {
            String query = "SHOW INDEXES";
            session.run(query);
        } catch (Exception exception){
            System.out.println("At DbWrapperDriver.assertConstraints");
            System.out.println(exception.getMessage());
            System.out.println(exception.getCause());
            System.out.println(exception.getStackTrace());
        }
    }


//    @Override
//    public Relationship createRelationshipIfNotExists(TransitionType transitionType, Node nodeA, Node nodeB, NodeLabel nodeLabel, String matchProperty, String key, Object value) {

//    }

    @Override
    public void close() throws Exception {
        System.out.println("Shutting down DB");
        driver.close();
    }



    // TODO: to remove
//    public void printGreeting( final String message )
//    {
//        try ( Transaction transaction = graphDatabaseService.beginTx() )
//        {
//            String query =  "CREATE (a:Greeting) " +
//                    "SET a.message = $message " +
//                    "RETURN a.message + ', from node ' + id(a)";
//            Map<String, Object> parameters = new HashMap<>();
//            parameters.put( "message", message );
//            Result result = transaction.execute(query, parameters);
//            transaction.commit();
//            System.out.println(result.resultAsString());
//        }
//
//    }

}
