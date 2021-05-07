import java.util.List;

public class Model {
    private final WebUsage webUsage;

    public Model(DbWrapper dbWrapper) {
        this.webUsage = new WebUsage(dbWrapper);
    }

    public void processFile() {
        Reader reader = new Reader(webUsage);
        reader.read();
        List<SessionSIN> sessions = reader.getSessions();
    }
}
