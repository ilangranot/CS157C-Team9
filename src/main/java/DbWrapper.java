import org.neo4j.driver.types.Node;

public interface DbWrapper{
    public Node createNodeIfNotExists(NodeLabel nodeLabel, String key, Object value);

    public void createRelationshipIfNotExists(TransitionType transitionType, Node nodeA, Node nodeB,
                                              NodeLabel nodeLabel, String matchProperty, String key, String value);

    public void assertConstraintsIndexes(NodeLabel nodeLabel, String key);

    public String deleteEntireDb();
}
