import java.util.List;
import java.net.URL;

public class Program {
    public static void main(String[] args) {
        int errorCode = 0;
        Reader r = new Reader();
        r.read();
        List<SessionSIN> sessions = r.getSessions();
        System.exit(errorCode);
    }
}
