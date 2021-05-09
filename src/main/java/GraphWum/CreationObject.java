package GraphWum;

public class CreationObject
{
    private int mode;
    /* create node fields */
    private String url;
    private String title;
    /* create transition fields */
    private String urlFrom;
    private String urlTo;
    private int userid;
    private String sessionid;
    private String dateFrom;
    private String dateTo;
    private String timeFrom;
    private String timeTo;


    /* methods for creating a node */
    public String geturl()
    {
        return this.url;
    }
    public void seturl(String u)
    {
        this.url = u;
    }

    public String gettitle() { return this.title; }
    public void settitle(String t) { this.title = t; }

    /* methods for creating a transition */
    public String geturlFrom()
    {
        return this.urlFrom;
    }
    public void seturlFrom(String u)
    {
        this.urlFrom = u;
    }

    public String geturlTo()
    {
        return this.urlTo;
    }
    public void seturlTo(String u)
    {
        this.urlTo = u;
    }

    public int getmode()
    {
        return this.mode;
    }
    public void setmode(int m)
    {
        this.mode = m;
    }

    public int getUserid() {
        return userid;
    }
    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getSessionid() {
        return sessionid;
    }
    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public String getDateFrom() {
        return dateFrom;
    }
    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }
    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getTimeFrom() {
        return timeFrom;
    }
    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }
    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }
}
