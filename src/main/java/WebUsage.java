import org.neo4j.driver.*;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Relationship;

import java.net.URL;

public class WebUsage {
    private DbWrapper dbWrapper;

    public WebUsage() {
        dbWrapper = new DbWrapperDriver("bolt://localhost:7687", "neo4j", "password" );
//        dbWrapper.assertConstraints(NodeLabel.Page, "url");
//        dbWrapper = new DbWrapperEmbedded(DbStartupSingleton.getGraphDatabaseService());
    }


    public Node addPage(URL url){
        return dbWrapper.createNodeIfNotExists(NodeLabel.Page, "url", url.toExternalForm());
    }

    public Relationship addTransition(URL fromURL, URL toURL, Object session){
        Relationship result = null;
        Node fromPage = addPage(fromURL);
        Node toPage = addPage(fromURL);
        dbWrapper.createRelationshipIfNotExists(new TransitionType("1"), fromPage, toPage, NodeLabel.Page, "url", "1", session);
        return result;
    }

}
