import java.util.Date;

public class Transaction {
    private String fromURL;
    private String toURL;
    private Date atTime;

    public Transaction(String fromURL, String toURL, Date atTime) {
        this.fromURL = fromURL;
        this.toURL = toURL;
        this.atTime = atTime;
    }

    public String getFromURL() {
        return fromURL;
    }

    public String getToURL() {
        return toURL;
    }

    public Date getAtTime() {
        return atTime;
    }
}