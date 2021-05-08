/**
 * CS-157C - GraphWUM
 *
 */


public class Program {

    public static void main(String[] args) {
        int errorCode = 0;
        // USE EITHER:
        // FOR EMBEDED IMPLEMENTATION USE: new DbWrapperEmbedded(DbStartupSingleton.getGraphDatabaseService())
        // FOR DRIVER USE: new DbWrapperDriver("bolt://localhost:7687", "neo4j", "password" )
        Model model = new Model( new DbWrapperDriver("bolt://localhost:7687", "neo4j", "password" )   );
        try {
            model.processFile();
        }

        catch (Exception e) {
            e.printStackTrace();
            errorCode = -1;
        }
        System.exit(errorCode);
    }
}
