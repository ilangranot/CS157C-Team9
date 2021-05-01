import org.neo4j.dbms.api.DatabaseManagementService;
import org.neo4j.dbms.api.DatabaseManagementServiceBuilder;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Transaction;

import java.io.File;

import static org.neo4j.configuration.GraphDatabaseSettings.DEFAULT_DATABASE_NAME;

public class DbStartupSingleton {
    private static GraphDatabaseService _graphDatabaseService; //TODO: make singleton


    public DbStartupSingleton() {
        //File databaseDirectory;
        //DatabaseManagementService managementService = new DatabaseManagementServiceBuilder(databaseDirectory).build();
        //_graphDatabaseService = managementService.database(DEFAULT_DATABASE_NAME);
       // registerShutdownHook( managementService );
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
                managementService.shutdown();
            }
        } );
    }


    private void setupDbSettingsUniqueConstrain(){
        try ( Transaction transaction = _graphDatabaseService.beginTx() )
        {
            transaction.schema()
                    .constraintFor( Label.label( "Page" ) )
                    .assertPropertyIsUnique( "url" )
                    .create();
            transaction.commit();
        }
    }
}
