package markdowndocs.web.app.MarkdownDocsWebApp;

import java.util.logging.Logger;

public class LoggerSingleton {

    private static Logger logger;

    public static Logger create() {

        if (logger != null)
            return logger;

        logger = Logger.getLogger("all logger");
        return logger;
    }
}
