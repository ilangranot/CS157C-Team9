import java.util.ArrayList;
import java.util.List;

public class SessionSIN {
    private int sessionID;
    private List<TransactionSIN> list;
    private int userID;

    public SessionSIN(int sessionID, int userID) {
        this.userID = userID;
        this.list = new ArrayList<>();
        this.sessionID = sessionID;
    }

    public void addTransaction(TransactionSIN transaction) {
        list.add(transaction);
    }

    public void deleteTransaction(TransactionSIN transaction) {
        list.remove(transaction);
    }

    public int getSessionID() {
        return sessionID;
    }

    public int getUserID() {
        return userID;
    }

}