import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/***
 *
 */
public class Reader {
    public static final int SESSION_TIMEOUT = 960000;
    public static final String fileName = "./src/main/java/1_22_ordered_combined_instructureHistory.tsv";
    public static final String dateFormat = "MM/dd/yy HH:mm:ss";

    private static List<Session> sessions;
    private static Date previousDate;
    private static int previousUser = -1;
    private static String previousURL = null;
    private static Session previousSession;

    /***
     *
     */
    public static void read() {
        try {

            File file = new File(fileName);
            BufferedReader lineReader = new BufferedReader(new FileReader(file));
            String lineText = null;
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
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
                TransactionProcessor processor = new TransactionProcessor();
                processor.process( userID, URL, date );
            }

            System.out.println(sessions.size()); // Remove later
        }
        catch (IOException | ParseException e) {
        }
    }

    public List<Session> getSessions() {
        return sessions;
    }


    /***
     *
     */
    private static class TransactionProcessor {
        /***
         *
         * @param userID
         * @param URL
         * @param date
         */
        public void process(int userID, String URL, Date date) {
//            System.out.println(userID + ": " + URL + " " + date.getTime()); // Remove later
            Transaction transaction = new Transaction(previousURL, URL, date); // Build Object

            if(sameSession(userID, date)) { // Check if same session
                previousSession.addTransaction(transaction);
            }
            else {
                createSession(transaction, userID);
            }
            previousUser = userID; // Store ID
            previousDate = date; // Store date
        }

        /***
         *
         * @param userID
         * @param date
         * @return
         */
        public boolean sameSession(int userID, Date date) {
            if(userID == previousUser) { // Check if same ID or past timeout
                if((date.getTime() - previousDate.getTime()) < SESSION_TIMEOUT) { // Check if time passed is less than 16 min
                    return true;
                }
                return false;
            }
            return false;
        }

        /***
         *
         * @param transaction
         * @param userID
         * @return
         */
        public Session createSession(Transaction transaction, int userID) {
            Session session = new Session(sessions.size(), userID);
            session.addTransaction(transaction);
            previousSession = session;
            sessions.add(session);
            return session;
        }
    }

}