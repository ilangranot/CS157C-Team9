import org.neo4j.dbms.api.DatabaseManagementService;
import org.neo4j.dbms.api.DatabaseManagementServiceBuilder;
import org.neo4j.dbms.api.DatabaseNotFoundException;
import org.neo4j.graphdb.ConstraintViolationException;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.schema.Schema;
import java.io.File;



public class DbStartupSingleton {
    private static GraphDatabaseService _graphDatabaseService = null; //TODO: make singleton
    public static final String DATABASE_DIRECTORY = "/Users/ilangranot/IdeaProjects/GraphWum";
    private static final String DATABASE_NAME = "neo4j";


    private DbStartupSingleton() {
    }

    public static GraphDatabaseService getGraphDatabaseService() {
        if (_graphDatabaseService == null){
            File databaseDirectory = new File(DATABASE_DIRECTORY);
            DatabaseManagementService managementService = new DatabaseManagementServiceBuilder(databaseDirectory).build();
            try {
                _graphDatabaseService = managementService.database(DATABASE_NAME);
                setupDbSettingsUniqueConstrain();
            } catch (DatabaseNotFoundException databaseNotFoundException){
                managementService.createDatabase(DATABASE_NAME);
                // TODO: repeating instruction - advise
                _graphDatabaseService = managementService.database(DATABASE_NAME);
                setupDbSettingsUniqueConstrain();
            } catch (ConstraintViolationException constraintViolationException){
                System.out.println(constraintViolationException.getMessage());
            }
            registerShutdownHook( managementService );
        }

        return _graphDatabaseService;
    }

    private static void registerShutdownHook( final DatabaseManagementService managementService )
    {
        // Registers a shutdown hook for the Neo4j instance so that it
        // shuts down nicely when the VM exits (even if you "Ctrl-C" the
        // running application).
        Runtime.getRuntime().addShutdownHook( new Thread()
        {
            @Override
            public void run()
            {
                System.out.println("Shutting down DB");
                managementService.shutdown();
            }
        } );
    }


    //TODO: Getprogress: https://neo4j.com/docs/java-reference/current/java-embedded/indexes/
    public void getProgress(){
        try ( Transaction tx = _graphDatabaseService.beginTx() )
        {
            Schema schema = tx.schema();
//            System.out.println( String.format( "Percent complete: %1.0f%%",
//                    schema.getIndexPopulationProgress( usernamesIndex ).getCompletedPercentage() ) );
        }
    }



    //TODO: Add INDEX


    // Setup settings for the db constraint
    private static void setupDbSettingsUniqueConstrain(){
        Transaction transaction = _graphDatabaseService.beginTx();
        //TODO: Check if exists
//        if (transaction.schema().constraintFor(NodeLabels.Page) == null )
        transaction.schema()
                .constraintFor( NodeLabel.Page )
                .assertPropertyIsUnique( "url" )
                .create();
        transaction.commit();
    }
}
