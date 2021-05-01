import java.util.List;

public class Program {
    public static void main(String[] args) {
        Reader r = new Reader();
        r.read();
        List<Session> sessions = r.getSessions();
    }
}
