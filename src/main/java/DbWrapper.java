import org.apache.commons.beanutils.ResultSetIterator;
import org.neo4j.graphdb.*;

import java.util.HashMap;
import java.util.Map;



public class DbWrapper implements AutoCloseable {
    //    private final Driver driver;
    private final GraphDatabaseService graphDatabaseService;


    public DbWrapper(GraphDatabaseService graphDatabaseService) {
        this.graphDatabaseService = graphDatabaseService;
    }

//    public DbWrapper( String uri, String user, String password )
//    {
////        driver = GraphDatabase.driver( uri, AuthTokens.basic( user, password ) );
//    }

    @Override
    public void close() throws Exception {
//        driver.close();
    }


    // transaction enforce ACID
    private void commitTransaction(){
        try ( Transaction transaction = graphDatabaseService.beginTx() )
        {
            // Database operations go here
            transaction.commit();
        }
    }

    // TODO: check if to add or not
    public Node createNodeIfNotExists(Label label, String key, Object value){
        Node result = null;
        ResourceIterator<Node> resultIterator = null;
        try ( Transaction transaction = graphDatabaseService.beginTx() )
        {
            String query = "MERGE (n:" + label +" {"+ key + ": $" + key + "}) RETURN n";
            Map<String, Object> parameters = new HashMap<>();
            parameters.put( key, value );
            resultIterator = transaction.execute(query, parameters).columnAs("n");
            result = resultIterator.next();
            transaction.commit();
            return result;
        }
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
