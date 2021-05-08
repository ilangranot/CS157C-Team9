import java.util.Date;

public class TransactionSIN {
    private PageURL fromURL;
    private PageURL toURL;
    private Date atTime;

    public TransactionSIN(String fromURL, String toURL, Date atTime) {
        this.fromURL = new PageURL(fromURL);
        this.toURL = new PageURL(toURL);
        this.atTime = atTime;
    }

    public PageURL getFromURL() {
        return fromURL;
    }

    public PageURL getToURL() {
        return toURL;
    }

    public Date getAtTime() {
        return atTime;
    }
}