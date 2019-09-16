package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.CapitolProductionSchedulePrintSubHandler;
import com.universal.milestone.SecureHandler;
import inetsoft.report.XStyleSheet;

public class CapitolProductionSchedulePrintSubHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hCapProd";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public CapitolProductionSchedulePrintSubHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hCapProd");
  }
  
  public String getDescription() { return "Sub Report"; }
  
  protected static void fillCapitolProductionUpdateForPrint(XStyleSheet paramXStyleSheet, Context paramContext) { throw new Error("Unresolved compilation problems: \n\torderQty cannot be resolved to a variable\n\tgetPlantname cannot be resolved to a variable\n\torderQty cannot be resolved to a variable\n"); }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\CapitolProductionSchedulePrintSubHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */