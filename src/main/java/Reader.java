import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.net.URL;

/***
 *
 */
public class Reader {
    public static final int SESSION_TIMEOUT = 960000;
    public static final String fileName = "./src/main/java/1_22_ordered_combined_instructureHistory.tsv";
    public static final String dateFormat = "MM/dd/yy HH:mm:ss";

    private List<SessionSIN> sessions;
    private Date previousDate;
    private int previousUser = -1;
    private String previousURL = null;
    private SessionSIN previousSession;
    private WebUsage webUsage;

    public Reader(WebUsage webUsage) {
        this.webUsage = webUsage;
    }

    /***
     *
     */
    public void read() {
        int count = 0;
        try {
            File file = new File(fileName);
            BufferedReader lineReader = new BufferedReader(new FileReader(file));
            String lineText = null;
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            sessions = new ArrayList<>();

            while( (lineText = lineReader.readLine()) != null && count  < 2000) {
                String[] data = lineText.split("\t"); //Read Line in
                //Get Data from line
                int userID = Integer.parseInt(data[0]);
                String URL = data[6];
                Date date = sdf.parse(data[3] + " " + data[4]);

                TransactionProcessor processor = new TransactionProcessor();
                processor.process( userID, URL, date );
                count++;
                System.out.println(count);
            }

            System.out.println(sessions.size()); // Remove later
        }
        catch (IOException | ParseException e) {
        }
    }

    public List<SessionSIN> getSessions() {
        return sessions;
    }


    /***
     *
     */
    private class TransactionProcessor {
        /***
         *
         * @param userID
         * @param URL
         * @param date
         */
        public void process(int userID, String URL, Date date) {
//            System.out.println(userID + ": " + URL + " " + date.getTime()); // Remove later
            TransactionSIN transaction = new TransactionSIN(previousURL, URL, date); // Build Object
            try {
                webUsage.addPage(new URL(URL));

                if(sameSession(userID, date)) { // Check if same session
                    webUsage.addTransition(new URL(previousURL), new URL(URL), new UserSession(String.valueOf(sessions.size())));
                    previousSession.addTransaction(transaction);
                }
                else {
                    createSession(transaction, userID);
                }
                previousUser = userID; // Store ID
                previousURL = URL;
                previousDate = date; // Store date
            }
            catch (Exception exception){
                exception.printStackTrace();
            }

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
        public SessionSIN createSession(TransactionSIN transaction, int userID) {
            SessionSIN session = new SessionSIN(sessions.size(), userID);
            session.addTransaction(transaction);
            previousSession = session;
            sessions.add(session);
            return session;
        }
    }

}