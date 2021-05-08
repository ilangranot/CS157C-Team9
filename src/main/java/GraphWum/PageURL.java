package GraphWum;

public class PageURL {
    private String stuff = null;
    private String otherStuff = null;

    public PageURL(String url) {
        if(url != null) {
            String[] data = url.split("\\?", 2);
            this.stuff = data[0];
            this.otherStuff = data[data.length - 1];
            System.out.println(stuff);
            System.out.println(otherStuff);
        }

    }

}
