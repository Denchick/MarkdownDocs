package markdowndocs.web.app.MarkdownDocsWebApp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(value = "/hello")
public class MainController {

 @RequestMapping(method = RequestMethod.GET)
 @ResponseBody
 public String getMyData() {
     return new String("Hello from mrkdwn");
 	}
 }