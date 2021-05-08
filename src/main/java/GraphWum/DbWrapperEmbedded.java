package GraphWum;
import org.neo4j.graphdb.*;
import java.util.HashMap;
import java.util.Map;

//import static org.neo4j.driver.Values.parameters;

// TODO: Suppose to implement db wrapper
public class DbWrapperEmbedded implements DbWrapper {
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

    @Override
    public org.neo4j.driver.types.Node createNodeIfNotExists(NodeLabel nodeLabel, String key, Object value) {
        return (org.neo4j.driver.types.Node)(createNodeIfNotExists(nodeLabel,key,value));
    }

    @Override
    public void deleteNode(NodeLabel nodeLabel, String key, Object value) {

    }

    @Override
    public void createRelationshipIfNotExists(TransitionType transitionType, org.neo4j.driver.types.Node nodeA, org.neo4j.driver.types.Node nodeB, NodeLabel nodeLabel, String matchProperty, String key, String value) {
        System.out.println("NOT USED");
    }

    @Override
    public void createRelationshipIfNotExists(TransitionType transitionType, String propertyA, String propertyB, NodeLabel nodeLabel, String matchProperty, String key, String value) {
        try ( Transaction tx = graphDatabaseService.beginTx() )
        {
            // Database operations go here
            Node firstNode = tx.createNode();
            firstNode.setProperty( matchProperty, propertyA );
            Node secondNode = tx.createNode();
            secondNode.setProperty( matchProperty, propertyB );

//            Relationship relationship = firstNode.createRelationshipTo( secondNode, transitionType );
//            relationship.setProperty( key, value );
            tx.commit();
            System.out.println("Commited");
        }
    }

    @Override
    public void createConstraintsIndexes(NodeLabel nodeLabel, String key) {
        System.out.println("CALLED ASSERT CONSTRAINTS");
        try ( Transaction transaction = graphDatabaseService.beginTx() )
        {
            String query = "CREATE CONSTRAINT unique_webpages_url IF NOT EXISTS ON (n: " + nodeLabel + ") ASSERT n." + key + " IS UNIQUE";
            Result result = transaction.execute(query);
            transaction.commit();
            System.out.println(result.resultAsString());
        }
    }

    @Override
    public void assertConstraintsIndexes(NodeLabel nodeLabel, String key) {
        System.out.println("CALLED ASSERT CONSTRAINTS");
        try ( Transaction transaction = graphDatabaseService.beginTx() )
        {
            String query = "SHOW INDEXES";
            Result result = transaction.execute(query);
            transaction.commit();
            System.out.println(result.resultAsString());
        }
    }

    @Override
    public String deleteEntireDb() {
        System.out.println("CALLED DELETE ALL");
        try ( Transaction transaction = graphDatabaseService.beginTx() )
        {
            String query = "MATCH (n) DETACH DELETE n";
//            String query = "SHOW INDEXES";
            Result result = transaction.execute(query);

            transaction.commit();
            System.out.println(result.resultAsString());
            return "SUCCESS";
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
