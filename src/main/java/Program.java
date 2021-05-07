import java.net.URL;

public class Program {
    private DbWrapperEmbedded dbWrapper;


    // TODO: Team: notice change in POM
    public static void main( String... args )
    {
        int errorCode = 0;
        try {
            WebUsage webUsage = new WebUsage();

            webUsage.addPage(new URL("https://sjsu.instructure.com/11"));
            webUsage.addPage(new URL("https://sjsu.instructure.com/4"));
            webUsage.addPage(new URL("https://sjsu.instructure.com/5"));
            webUsage.addPage(new URL("https://sjsu.instructure.com/4"));
            webUsage.addPage(new URL("https://sjsu.instructure.com/6"));
            webUsage.addPage(new URL("https://sjsu.instructure.com"));
            webUsage.addTransition(new URL("https://sjsu.instructure.com/7"),
                    new URL("https://sjsu.instructure.com/8"), new UserSession("1"));
            webUsage.addTransition(new URL("https://sjsu.instructure.com"),
                    new URL("https://sjsu.instructure.com/4"), new UserSession("2"));
            webUsage.addTransition(new URL("https://sjsu.instructure.com/13"),
                    new URL("https://sjsu.instructure.com/14"), new UserSession("3"));
        } catch (Exception exception){
            System.out.println("At Program.main");
            System.out.println(exception.getMessage());
            System.out.println(exception.getCause());
            System.out.println(exception.getStackTrace());
            errorCode = 1;
        } finally {
            System.out.println("Exiting...");
            // Using System.exit to ensure shutdown of db
            System.exit(errorCode);
        }

    }
}
