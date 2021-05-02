import org.apache.commons.beanutils.ResultSetIterator;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;
import org.neo4j.graphdb.*;
import java.util.HashMap;
import java.util.Map;

import static org.neo4j.driver.Values.parameters;

// TODO: Suppose to implement db wrapper
public class DbWrapperEmbedded {
    private final GraphDatabaseService graphDatabaseService;


    public DbWrapperEmbedded(GraphDatabaseService graphDatabaseService) {
        this.graphDatabaseService = graphDatabaseService;
    }



    // transaction enforce ACID
    private void commitTransaction(Map<String, Object> properties){
        try ( Transaction transaction = graphDatabaseService.beginTx() )
        {
            // Database operations go here
            transaction.commit();
        }
    }

    // TODO: change Label into list of labels and to multiple key values
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


    public Relationship createRelationshipIfNotExists(RelationshipType relationshipType, Node nodeA, Node nodeB,
                                                      Label label, String matchProperty, String key, Object value){
        Relationship result = null;
        ResourceIterator<Relationship> resultIterator = null;
        try ( Transaction transaction = graphDatabaseService.beginTx() )
        {
            String query = "MATCH (a:" + label + "), (b:" + label +")" +
                    "WHERE a."+ matchProperty + " = '"+nodeA.getProperty(matchProperty) + "'" +
                    " AND b.name = '" + nodeB.getProperty(matchProperty) + "'" +
                    "MERGE (a)-[r:" + relationshipType.name() +" {"+ key + ": $" + key + "}]->(b)" +
                    "RETURN type(r)";
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
