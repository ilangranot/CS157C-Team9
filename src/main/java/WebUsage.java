import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

import java.net.URL;
import java.util.Map;

public class WebUsage {
    private DbWrapper dbWrapper;

    public WebUsage() {
        dbWrapper = new DbWrapper(DbStartupSingleton.getGraphDatabaseService());
    }


    public Node addPage(URL url){
        return dbWrapper.createNodeIfNotExists(NodeLabels.Page, "url", url.toExternalForm());
    }

    public Relationship addTransition(URL fromURL, URL toURL, String sessionId, Map<String, Object> properties){
        Relationship result = null;
        Node fromPage = addPage(fromURL);
        Node toPage = addPage(fromURL);

        return result;
    }

}
