package WEB-INF.classes.com.universal.milestone;

import com.techempower.EnhancedProperties;
import com.techempower.Log;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.JspDebug;

public class JspDebug {
  protected static Log log;
  
  public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getApplicationLog(); }
  
  public static void out(String text) { log.debug("jNon", text); }
  
  public static void out(String threeLetterCode, String text) {
    if (threeLetterCode.length() > 3)
      log.debug("jspd", "Please use a three-letter code when calling JspDebug.out."); 
    log.debug("j" + threeLetterCode, text);
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\JspDebug.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */