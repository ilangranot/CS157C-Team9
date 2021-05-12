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

    private final GraphWum.Model m =
            new GraphWum.Model(new DbWrapperDriver("bolt://localhost:7687", "neo4j", "1234" ));
    private GraphStats g;

    /* index methods */
    @GetMapping("/index")
    public String getIndex(Model model) {
        g = new GraphStats("index");
        model.addAttribute("graphstats", g);
        return "index";
    }

    @PostMapping("/index")
    public String loadData()
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
        g = new GraphStats("create");
        model.addAttribute("graphstats", g);
        return "create";
    }

   @PostMapping("/create")
    public String submitCreateForm(@ModelAttribute("url") CreationObject urlObj) {
        try {
            // creating a webpage
            if (urlObj.getmode() == 1) {
                URL url = new URL(urlObj.geturl());
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

    /* search page methods */
    @GetMapping("/search")
    public String getMine(Model model) {
        g = new GraphStats("search");
        model.addAttribute("graphstats", g);
        return "search";
    }

}
