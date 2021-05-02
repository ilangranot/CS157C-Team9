import java.util.List;

public class Model {
    public static void processFile() {
            Reader r = new Reader();
            r.read();
            List<SessionSIN> sessions = r.getSessions();
    }
}
