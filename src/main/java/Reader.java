import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Reader {

    private static List<Session> sessions;

    private static Date previousDate;
    private static int previousUser = -1;
    private static String previousURL = null;
    private static Session previousSession;

    public static void main(String[] args) {
        try {

            File file = new File("./src/main/java/1_22_ordered_combined_instructureHistory.tsv");
            BufferedReader lineReader = new BufferedReader(new FileReader(file));
            String lineText = null;
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
            sessions = new ArrayList<>();

            while( (lineText = lineReader.readLine()) != null) {
                String[] data = lineText.split("\t"); //Read Line in
                //Get Data from line
                int userID = Integer.parseInt(data[0]);
                String URL = data[6];
                Date date = sdf.parse(data[3] + " " + data[4]);

                /* Communicate with Backend
                WUM.addPage(URL);
                if(previousURL != null) {
                    WUM.addTransition(previousURL, URL);
                }
                */

                System.out.println(userID + ": " + URL + " " + date.getTime()); // Remove later
                Transaction transaction = new Transaction(previousURL, URL, date);

                if(userID == previousUser) { //Check if same ID, otherwise new session
                    if((date.getTime() - previousDate.getTime()) <  960000) { //Check if time passed is less than 16 min, otherwise new session
                        previousSession.addTransaction(transaction);
                    }
                    else {
                        Session session = new Session(sessions.size(), userID);
                        session.addTransaction(transaction);
                        previousSession = session;
                        sessions.add(session);
                    }
                }
                else {
                    Session session = new Session(sessions.size(), userID);
                    session.addTransaction(transaction);
                    previousSession = session;
                    sessions.add(session);
                }

                previousUser = userID;
                previousDate = date;
            }

            System.out.println(sessions.size()); //Remove later
        }
        catch (IOException | ParseException e) {
        }
    }

}