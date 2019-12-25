package markdowndocs.web.app.MarkdownDocsWebApp;

import markdowndocs.documents.sharing.ISharingService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/share")
public class ShareController {
    ISharingService sharingService;
    

}
