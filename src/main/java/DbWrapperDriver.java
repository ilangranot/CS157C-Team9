import org.neo4j.driver.*;
import org.neo4j.driver.Result;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Relationship;
import scala.Enumeration;

import java.util.HashMap;
import java.util.Map;


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
        Result result = null;
        String query = "MERGE (n:" + nodeLabel +" {"+ key + ": $" + key + "}) RETURN n";
        try (Session session = driver.session()) {
            result = session.writeTransaction(tx -> tx.run(query, Values.parameters(key, value)));
            return result.single().get( 0 ).asNode();
        } catch (Exception exception) {
            return null;
        }
    }

    @Override
    public Relationship createRelationshipIfNotExists(
            TransitionType transitionType, Node nodeA, Node nodeB,
            NodeLabel nodeLabel, String matchProperty, String key, Object value) {
        String query = "MATCH (a:" + nodeLabel + "), (b:" + nodeLabel +")" +
                "WHERE a."+ matchProperty + " = '" + nodeA.get(matchProperty) + "'" +
                " AND b.name = '" + nodeB.get(matchProperty) + "'" +
                "MERGE (a)-[r:" + transitionType.name() +" {"+ key + ": $" + key + "}]->(b)" +
                "RETURN type(r)";
        Result result = null;
        try (Session session = driver.session()) {
            result = session.writeTransaction(tx -> tx.run(query, Values.parameters(key, value)));
            return result.single().get(0).asRelationship();
        } catch (Exception exception) {
            return null;
        }
    }

    // TODO: Too lazy to generalize ...
    @Override
    public void assertConstraints(NodeLabel nodeLabel, String key) {
        try (Session session = driver.session()) {
            String query = "CREATE CONSTRAINT unique_webpages_url ON (n: " + nodeLabel + ") ASSERT n." + key + " IS UNIQUE";
        } catch (Exception exception){
            System.out.println("At Db Wrapper Driver");
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
