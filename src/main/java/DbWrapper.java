import org.neo4j.driver.*;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Relationship;

public interface DbWrapper{
    public Node createNodeIfNotExists(NodeLabel nodeLabel, String key, Object value);

    public Relationship createRelationshipIfNotExists(TransitionType transitionType, Node nodeA, Node nodeB,
                                                      NodeLabel nodeLabel, String matchProperty, String key, Object value);

    public void assertConstraints(NodeLabel nodeLabel, String key);
}
