package WEB-INF.classes.com.universal.milestone;

import com.techempower.gemini.Form;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Form;

public class Form extends Form {
  static final String[] unChangedVariables = { "", "0", "-1", "null", 
      "1/1/00", "&nbsp;", " ", " " };
  
  static final String[] unChangedElems = { "lastUpdatedDate", 
      "lastUpdatedBy", "enteredBy", "cmd", "new", 
      "APNGInd" };
  
  public Form(GeminiApplication application) { super(application); }
  
  public Form(GeminiApplication application, String name, String action, String method) { super(application, name, action, method); }
  
  public boolean isUnchanged() { return super.isUnchanged(); }
  
  public boolean isUnchangedVarible(String value, String elemName) {
    for (int i = 0; i < unChangedElems.length; i++) {
      if (elemName != null && elemName.equals(unChangedElems[i]))
        return true; 
    } 
    for (int i = 0; i < unChangedVariables.length; i++) {
      if (value != null && value.equals(unChangedVariables[i]))
        return true; 
    } 
    return false;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\Form.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */