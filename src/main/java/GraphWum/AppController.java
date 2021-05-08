package GraphWum;

import org.eclipse.jetty.server.session.Session;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

@Controller
public class AppController {

    private static final GraphWum.Model m = new GraphWum.Model();

    /* index methods */
    @GetMapping("/index")
    public String getIndex(Model model) {
        model.addAttribute("model", m);
        return "index";
    }

    @PostMapping("/index")
    public String loadData(@ModelAttribute("reader") GraphWum.Model m)
    {
        m.processFile();
        // TODO: add a loading wheel and success message
        return "index";
    }

    /* create page methods */
    @GetMapping("/create")
    public String setupCreate(Model model) {
        CreationObject urlObj = new CreationObject();
        model.addAttribute("url", urlObj);
        return "create";
    }

   @PostMapping("/create")
    public String submitCreateForm(@ModelAttribute("url") CreationObject urlObj) {
        try {
            // creating a webpage
            if (urlObj.getmode() == 1) {
                URL url = new URL(urlObj.geturl());
                String title;
                // TODO: ensure support for adding a node with title and URL, not just URL
                if (urlObj.gettitle() != null) title = urlObj.gettitle();
                System.out.println(url.toString()); // delete later
                m.addPage(url);
            }
            else {
                // creating a transition
                URL urlFrom = new URL(urlObj.geturlFrom());
                URL urlTo = new URL(urlObj.geturlTo());
                UserSession session = new UserSession(urlObj.getSessionid());
                m.addTransition(urlFrom, urlTo, session);
            }
        } catch (MalformedURLException e) {
           e.printStackTrace();
        }
        // TODO: add success message when the node is created
        return "create";
    }

    /* delete page methods */
    @GetMapping("/delete")
    public String getDelete(Model model) {
        CreationObject urlObj = new CreationObject();
        model.addAttribute("url", urlObj);
        return "delete";
    }

    @PostMapping("/delete")
    public String submitDeleteForm(@ModelAttribute("url") CreationObject urlObj) {
        try {
            // deleting a webpage
            if (urlObj.getmode() == 1) {
                URL url = new URL(urlObj.geturl());
                System.out.println(url.toString()); // delete later
                m.deletePage(url);
            }
            else {
                // deleting a transition
                URL urlFrom = new URL(urlObj.geturlFrom());
                URL urlTo = new URL(urlObj.geturlTo());
                UserSession session = new UserSession(urlObj.getSessionid());
                m.deleteTransition(urlFrom, urlTo, session);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        // TODO: add success message when the node is deleted
        return "delete";
    }

    /* mine page methods */
    @GetMapping("/mine")
    public String getMine(Model model) {
        CreationObject urlObj = new CreationObject();
        model.addAttribute("url", urlObj);
        return "mine";
    }

    @PostMapping("/mine")
    public String submitMineForm(@ModelAttribute("url") CreationObject urlObj) {
        try {
            // searching for a webpage a webpage
            if (urlObj.getmode() == 1) {
                URL url = new URL(urlObj.geturl());
                String title = urlObj.gettitle();
                System.out.println(url.toString()); // delete later
                m.searchPage(title, url); // title or URL can be null, not both though
            }
            else {
                // searching for a transition
                HashMap<String, String> filters = new HashMap<>();
                // TODO: get all the fields filled out and add to hashmap
                m.searchTransition(filters);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return "mine";
    }

}
