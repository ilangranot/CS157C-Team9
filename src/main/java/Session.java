import java.util.List;

public class Session {
    private int sessionID;
    private List<Transaction> list;
    private int userID;

    public Session(Date userID) {
        this.userID = userID;
    }

    public void addTransaction(Transaction transaction) {
        list.add(transaction);
    }

    public void deleteTransaction() {

    }

    public int getSessionID() {
        return sessionID;
    }

    public getUserID() {
        return userID;
    }

}