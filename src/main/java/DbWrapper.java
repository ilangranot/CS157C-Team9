import com.sun.javafx.geom.ArcIterator;
import org.neo4j.graphdb.*;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.neo4j.driver.Values.parameters;


public class DbWrapper implements AutoCloseable {
//    private final Driver driver;
    private final GraphDatabaseService graphDatabaseService;


    public DbWrapper( String uri, String user, String password )
    {
//        driver = GraphDatabase.driver( uri, AuthTokens.basic( user, password ) );
    }

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
    public Node addNodeIfNotExists(Label label, String key, Object value){
        Node result = null;
        ResourceIterator<Node> resultIterator = null;
        try ( Transaction transaction = graphDatabaseService.beginTx() )
        {
            String queryString = "MERGE (n:User {name: $name}) RETURN n";
            Map<String, Object> parameters = new HashMap<>();
            parameters.put( key, value );
            resultIterator = transaction.execute(queryString, parameters).columnAs("n");
            result = resultIterator.next();
            transaction.commit();
            return result;
        }
    }


    // TODO: to remove
//    public void printGreeting( final String message )
//    {
//        try ( Session session = driver.session() )
//        {
//            String greeting = session.writeTransaction( new TransactionWork<String>()
//            {
//                @Override
//                public String execute( Transaction transaction )
//                {
//                    Result result = transaction.run( "CREATE (a:Greeting) " +
//                                    "SET a.message = $message " +
//                                    "RETURN a.message + ', from node ' + id(a)",
//                            parameters( "message", message ) );
//                    return result.single().get( 0 ).asString();
//                }
//            } );
//            System.out.println( greeting );
//        }
//    }
}
