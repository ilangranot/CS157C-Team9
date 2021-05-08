package GraphWum;

import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Relationship;

import java.net.URL;

public class WebUsage {
    private DbWrapper dbWrapper;

    public WebUsage() {
//        dbWrapper = new DbWrapperDriver("bolt://localhost:7687", "neo4j", "password" );
//        dbWrapper.assertConstraints(NodeLabel.Page, "url");
        dbWrapper = new DbWrapperEmbedded(DbStartupSingleton.getGraphDatabaseService());
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
