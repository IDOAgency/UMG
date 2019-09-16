package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.techempower.gemini.Dispatcher;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.UtilityHandler;

public class UtilityHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hUti";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public UtilityHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hUti");
  }
  
  public String getDescription() { return "Utility"; }
  
  public boolean acceptRequest(Dispatcher dispatcher, Context context, String command) {
    if (super.acceptRequest(dispatcher, context, command))
      if (command.startsWith("tables-header") || 
        command.startsWith("release-week") || 
        command.startsWith("report-config") || 
        command.startsWith("price-code"))
        return handleRequest(dispatcher, context, command);  
    return false;
  }
  
  public boolean handleRequest(Dispatcher dispatcher, Context context, String command) {
    if (command.equalsIgnoreCase("tables-header-search"))
      return tablesHeaderSearch(context); 
    if (command.equalsIgnoreCase("tables-header-search-results"))
      return tablesHeaderSearchResults(context); 
    if (command.equalsIgnoreCase("tables-header-editor"))
      return tablesHeaderEditor(context); 
    if (command.equalsIgnoreCase("release-week-search"))
      return releaseWeekSearch(context); 
    if (command.equalsIgnoreCase("release-week-search-results"))
      return releaseWeekSearchResults(context); 
    if (command.equalsIgnoreCase("release-week-editor"))
      return releaseWeekEditor(context); 
    if (command.equalsIgnoreCase("report-config-search"))
      return reportConfigSearch(context); 
    if (command.equalsIgnoreCase("report-config-search-results"))
      return reportConfigSearchResults(context); 
    if (command.equalsIgnoreCase("report-config-editor"))
      return reportConfigEditor(context); 
    if (command.equalsIgnoreCase("price-code-search"))
      return priceCodeSearch(context); 
    if (command.equalsIgnoreCase("price-code-search-results"))
      return priceCodeSearchResults(context); 
    if (command.equalsIgnoreCase("price-code-editor"))
      return priceCodeEditor(context); 
    return true;
  }
  
  protected boolean tablesHeaderSearch(Context context) { return context.includeJSP("tables-header-search.jsp"); }
  
  protected boolean tablesHeaderSearchResults(Context context) { return context.includeJSP("tables-header-search-results.jsp"); }
  
  protected boolean tablesHeaderEditor(Context context) { return context.includeJSP("tables-header-editor.jsp"); }
  
  protected boolean releaseWeekSearch(Context context) { return context.includeJSP("release-week-search.jsp"); }
  
  protected boolean releaseWeekSearchResults(Context context) { return context.includeJSP("release-week-search-results.jsp"); }
  
  protected boolean releaseWeekEditor(Context context) { return context.includeJSP("release-week-editor.jsp"); }
  
  protected boolean reportConfigSearch(Context context) { return context.includeJSP("report-config-search.jsp"); }
  
  protected boolean reportConfigSearchResults(Context context) { return context.includeJSP("report-config-results.jsp"); }
  
  protected boolean reportConfigEditor(Context context) { return context.includeJSP("report-config-editor.jsp"); }
  
  protected boolean priceCodeSearch(Context context) { return context.includeJSP("price-code-search.jsp"); }
  
  protected boolean priceCodeSearchResults(Context context) { return context.includeJSP("price-code-results.jsp"); }
  
  protected boolean priceCodeEditor(Context context) { return context.includeJSP("price-code-editor.jsp"); }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\UtilityHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */