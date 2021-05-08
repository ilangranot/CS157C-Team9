package GraphWum;
import org.neo4j.driver.types.Node;

import java.net.URL;

public class WebUsage {
    private final DbWrapper dbWrapper;

    public WebUsage(DbWrapper dbWrapper) {
        this.dbWrapper = dbWrapper;
        dbWrapper.deleteEntireDb();
        dbWrapper.createConstraintsIndexes(NodeLabel.Page, "url");
    }


    public Node addPage(URL url){
        return dbWrapper.createNodeIfNotExists(NodeLabel.Page, "url", url.toExternalForm());
    }

    public void removePage(URL url){
        dbWrapper.deleteNode(NodeLabel.Page, "url", url.toExternalForm());
    }

    // TODO: CHECK if to set all as TRANSITION_TO
    public void addTransition(URL fromURL, URL toURL, UserSession userSession){
        dbWrapper.createRelationshipIfNotExists(new TransitionType("TRANSITION_TO"), //userSession.getId()
                fromURL.toExternalForm(), toURL.toExternalForm(), NodeLabel.Page, "url", "session", userSession.getId()); //"session"
    }

    public void addTransition(Node fromPage, Node toPage, UserSession userSession){
        dbWrapper.createRelationshipIfNotExists(new TransitionType("TRANSITION_TO"), //userSession.getId()
                fromPage, toPage, NodeLabel.Page, "url", "sessions", userSession.getId()); //"session"
    }

//    public void addTransition(URL fromURL, URL toURL, UserSession userSession){
//        Node fromPage = addPage(fromURL);
//        Node toPage = addPage(toURL);
//        dbWrapper.createRelationshipIfNotExists(new TransitionType("TRANSITION_TO"), //userSession.getId()
//                fromPage, toPage, NodeLabel.Page, "url", "s" + userSession.getId(), userSession.getId()); //"session"
//    }
}
