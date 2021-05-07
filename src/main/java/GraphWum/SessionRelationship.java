package GraphWum;

import org.neo4j.graphdb.RelationshipType;

public class SessionRelationship implements RelationshipType {
    private final String sessionId;

    public SessionRelationship(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String name() {
        return sessionId;
    }
}
