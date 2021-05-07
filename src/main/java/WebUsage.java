import org.neo4j.driver.types.Node;

import java.net.URL;

public class WebUsage {
    private final DbWrapper dbWrapper;

    public WebUsage(DbWrapper dbWrapper) {
        this.dbWrapper = dbWrapper;
        dbWrapper.assertConstraintsIndexes(NodeLabel.Page, "url");
        dbWrapper.deleteEntireDb();
    }


    public Node addPage(URL url){
        return dbWrapper.createNodeIfNotExists(NodeLabel.Page, "url", url.toExternalForm());
    }

    public void addTransition(URL fromURL, URL toURL, UserSession userSession){
        Node fromPage = addPage(fromURL);
        Node toPage = addPage(toURL);
        dbWrapper.createRelationshipIfNotExists(new TransitionType(userSession.getId()),
                fromPage, toPage, NodeLabel.Page, "url", "session", userSession.getId());
    }

}
