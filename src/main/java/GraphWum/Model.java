package GraphWum;

import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class Model {

    private final WebUsage webUsage;

    public Model(DbWrapper dbWrapper) {
        this.webUsage = new WebUsage(dbWrapper);
    }

    public void processFile() {
        Reader r = new Reader(webUsage);
        r.read();
        List<SessionSIN> sessions = r.getSessions();
    }

    public void addPage(URL url) {
        webUsage.addPage(url);
    }

    public void addTransition(URL fromURL, URL toURL, UserSession userSession) {
        webUsage.addTransition(fromURL, toURL, userSession);
    }

    public void deletePage(URL url) {
        // TODO
        // this should be similar to add page except it removes the node instead of adding
    }

    public void deleteTransition(URL fromURL, URL toURL, UserSession userSession) {
        // TODO
        // this should be similar to addTransition execpt it removes the transition instead of adding
    }

    public List<String> searchPage(String title, URL url)
    {
        // TODO
        // this will use some other method to connect to the DB and compare the url given to the URL of nodes in DB
        // should also be able to search by title if the user wants (they can choose)
        // will return a list of matching nodes hopefully (return type TBD)
        return null;
    }

    public List<String> searchTransition(HashMap<String, String> filter_values)
    {
        // TODO
        // this will use some other method to connect to the DB and compare the given info to filter the results
        // any of the fields that pertain to a transition should be able to be used
        // the filter values mapes String of property name to the property value for the transition
        // will retunr  alist of matching trnasiitons hopefully (return type TBD)
        return null;
    }
}
