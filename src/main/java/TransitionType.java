import org.neo4j.graphdb.RelationshipType;

public class TransitionType implements RelationshipType {
    private final String transitionId;

    public TransitionType(String transitionId) {
        this.transitionId = transitionId;
    }

    @Override
    public String name() {
        return transitionId;
    }
}
