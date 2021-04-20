import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.TransactionWork;
import org.neo4j.driver.Value;
import org.neo4j.driver.exceptions.Neo4jException;

import static org.neo4j.driver.Values.parameters;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class App implements AutoCloseable{
    private final Driver driver;

    public App( String uri, String user, String password )
    {
        driver = GraphDatabase.driver( uri, AuthTokens.basic( user, password ) );
    }

    @Override
    public void close() throws Exception
    {
        driver.close();
    }

    public void printGreeting( final String message )
    {
        try ( Session session = driver.session() )
        {
            String greeting = session.writeTransaction( new TransactionWork<String>()
            {
                @Override
                public String execute( Transaction tx )
                {
                    Result result = tx.run( "CREATE (a:Greeting) " +
                                    "SET a.message = $message " +
                                    "RETURN a.message + ', from node ' + id(a)",
                            parameters( "message", message ) );
                    return result.single().get( 0 ).asString();
                }
            } );
            System.out.println( greeting );
        }
    }
    
    public void getAll() {
    	try ( Session session = driver.session() ) {
    		Result result = session.run("CREATE (n) RETURN n");
    		System.out.println(result.single());
    	}
    }
    
    public boolean checkNode(final String url) {
    	boolean returnVal;
    	String readPersonByNameQuery = "MATCH (n:website)\n" +
                "WHERE n.url = $url\n" +
                "RETURN n.url AS url";

        Map<String, Object> params = Collections.singletonMap("url", url);

        try (Session session = driver.session()) {
            Record record = (Record) session.readTransaction(tx -> {
                Result result = tx.run(readPersonByNameQuery, params);
                return result.single();
            });
            System.out.println(String.format("Found url: %s", ((Record) record).get("url").asString()));
            returnVal = true;
        // You should capture any errors along with the query and data for traceability
        } catch (Neo4jException ex) {
        	returnVal = false;
            throw ex;
        }
        return returnVal;
    }
    
    public void createNavigation(final String url1, final String url2, final String user_id, final String date) {
    	String createNavigationQuery = "CREATE (n1:website {url: $url1, user_id: $user_id, date: $date})\n" +
    			"CREATE (n2:website {url: $url2, user_id: $user_id, date: $date})\n" + 
    			"CREATE (n1)-[:NAVTO2]->(n2)\n" +
    			"RETURN n1, n2";
    	
    	Map<String, Object> params = new HashMap<>();
        params.put("url1", url1);
        params.put("url2", url2);
        params.put("user_id", user_id);
        params.put("date", date);

        try (Session session = driver.session()) {
            // Write transactions allow the driver to handle retries and transient errors
            Record record = session.writeTransaction(tx -> {
                Result result = tx.run(createNavigationQuery, params);
                return result.single();
            });
            System.out.println(String.format("Created friendship between: %s, %s",
                    record.get("n1").get("url").asString(),
                    record.get("n2").get("url").asString()));
        // You should capture any errors along with the query and data for traceability
        } catch (Neo4jException ex) {
            throw ex;
        }
    }

    public boolean deleteNode(final String url) {
        String deleteQuery = "MATCH (n:website {url: $url})\n" +
                "DETACH DELETE n";

        Map<String, Object> params = Collections.singletonMap("url", url);

        try (Session session = driver.session()) {
            Record record = (Record) session.readTransaction(tx -> {
                Result result = tx.run(deleteQuery, params);
                return result.single();
            });
            System.out.println(String.format("Removed url: %s", ((Record) record).get("url").asString()));
            returnVal = true;
            // You should capture any errors along with the query and data for traceability
        } catch (Neo4jException ex) {
            returnVal = false;
            throw ex;
        }
        return returnVal;
    }

    //Not finished
    public boolean deleteRelationship(final String url) {
        String deleteQuery = "MATCH (n:website {url: $url})-[r:NAVTO2]->()\n" +
                "DELETE r";

        Map<String, Object> params = Collections.singletonMap("url", url);

        try (Session session = driver.session()) {
            Record record = (Record) session.readTransaction(tx -> {
                Result result = tx.run(deleteQuery, params);
                return result.single();
            });
            System.out.println(String.format("Removed relationship fom url: %s", ((Record) record).get("url").asString()));
            returnVal = true;
            // You should capture any errors along with the query and data for traceability
        } catch (Neo4jException ex) {
            returnVal = false;
            throw ex;
        }
        return returnVal;
    }
    

    public static void main( String... args ) throws Exception
    {
    	String url1 = "https://sjsu.instructure.com/courses/1377088";
    	String url2 = "https://sjsu.instructure.com/courses/1377088/modules";
        try ( App greeter = new App( "bolt://localhost:7687", "neo4j", "password" ) )
        {
            //greeter.printGreeting( "hello, world" );
            //greeter.checkNode("https://sjsu.instructure.com/courses/1377088/modules");
            //greeter.createNavigation(url1, url2, "9", "07/15/2020");
            greeter.getAll();
        }
    }
}
