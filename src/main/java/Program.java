import java.util.List;
import java.net.URL;

public class Program {
    public static void main(String[] args) {
        int errorCode = 0;
        try {
            Model.processFile();
        }
        catch (Exception e) {
            e.printStackTrace();
            errorCode = -1;
        }
        System.exit(errorCode);
    }
}
