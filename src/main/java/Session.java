import java.util.ArrayList;
import java.util.List;

public class Session {
    private int sessionID;
    private List<Transaction> list;
    private int userID;

    public Session(int sessionID, int userID) {
        this.userID = userID;
        this.list = new ArrayList<>();
    }

    public void addTransaction(Transaction transaction) {
        list.add(transaction);
    }

    public void deleteTransaction(Transaction transaction) {
        list.remove(transaction);
    }

    public int getSessionID() {
        return sessionID;
    }

    public int getUserID() {
        return userID;
    }

}