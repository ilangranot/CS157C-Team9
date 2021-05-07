package GraphWum;

import org.neo4j.driver.*;
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
            TransitionType transitionType, Node nodeA, Node nodeB,
            NodeLabel nodeLabel, String matchProperty, String key, String value) {
        Relationship relationship = null;
        String query = "MATCH (a:" + nodeLabel + "), (b:" + nodeLabel +")" +
                " WHERE a."+ matchProperty + " = " + nodeA.get(matchProperty)  +
                " AND b." + matchProperty + " = " + nodeB.get(matchProperty)  +
                " MERGE (a)-[r: s" + transitionType.name() +" {"+ key + ": $" + key + "}]->(b)" +
                " RETURN type(r)";
        try (Session session = driver.session()) {
            session.writeTransaction(tx -> tx.run(query, Values.parameters(key, value)));
        } catch (Exception exception) {
            System.out.println("At DbWrapperDriver.createRelationshipIfNotExists");
            System.out.println(exception.getMessage());
            System.out.println(exception.getCause());
            System.out.println(exception.getStackTrace());
        }
    }

    // TODO: Too lazy to generalize ...
    @Override
    public void assertConstraints(NodeLabel nodeLabel, String key) {
        try (Session session = driver.session()) {
            String query = "CREATE CONSTRAINT unique_webpages_url ON (n: " + nodeLabel + ") ASSERT n." + key + " IS UNIQUE";
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
