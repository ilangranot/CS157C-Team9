import java.net.URL;

public class Program {
    private DbWrapper dbWrapper;


    // TODO: Team: notice change in POM
    public static void main( String... args )
    {
        int errorCode = 0;
        try {
            WebUsage webUsage = new WebUsage();
            webUsage.addPage(new URL("https://sjsu.instructure.com"));
            webUsage.addPage(new URL("https://sjsu.instructure.com/4"));
            webUsage.addPage(new URL("https://sjsu.instructure.com/5"));
            webUsage.addPage(new URL("https://sjsu.instructure.com/4"));
            webUsage.addPage(new URL("https://sjsu.instructure.com/6"));
            webUsage.addPage(new URL("https://sjsu.instructure.com"));
        } catch (Exception exception){
            System.out.println(exception.getMessage());
            errorCode = 1;
        } finally {
            System.out.println("Shutting down db");
            // Using System.exit to ensure shutdown of db
            System.exit(errorCode);
        }

    }
}
