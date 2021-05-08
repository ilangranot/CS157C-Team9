import java.net.MalformedURLException;
import java.net.URL;

public class PageUrl {
    private final URL url;
    private final String rest;

    public PageUrl(String string) throws MalformedURLException {
        url = new URL(string.split("//?",2)[0]);
        rest = string.split("//?",2)[1];
    }



}
