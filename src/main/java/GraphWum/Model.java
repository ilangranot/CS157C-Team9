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
}
