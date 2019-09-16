package WEB-INF.classes.com.universal.milestone;

import com.techempower.gemini.Context;
import com.techempower.gemini.FormDropDownMenu;
import com.universal.milestone.MilestoneFormDropDownMenu;
import java.util.ArrayList;

public class MilestoneFormDropDownMenu extends FormDropDownMenu {
  protected boolean multiple = false;
  
  protected ArrayList multSelections = new ArrayList();
  
  public MilestoneFormDropDownMenu(String name, String selection, String[] values, String[] menuText, boolean required) {
    super(name, selection, required);
    setMenuTextList(menuText);
    setValueList(values);
    this.multSelections.add(selection);
  }
  
  public MilestoneFormDropDownMenu(FormDropDownMenu formDropDownMenu) {
    super(formDropDownMenu.getName(), formDropDownMenu.getStringValue(), formDropDownMenu.isRequired());
    setMenuTextList(formDropDownMenu.getMenuTextList());
    setValueList(formDropDownMenu.getValueList());
    this.multSelections.add(formDropDownMenu.getStringValue());
  }
  
  public MilestoneFormDropDownMenu(String name, String selection, Integer[] values, String[] menuText, boolean required) {
    super(name, selection, required);
    setMenuTextList(menuText);
    setValueList(values);
    this.multSelections.add(selection);
  }
  
  public MilestoneFormDropDownMenu(String name, String selection, String values, String menuText, boolean required) {
    super(name, selection, required);
    setMenuTextList(menuText);
    setValueList(values);
    this.multSelections.add(selection);
  }
  
  public MilestoneFormDropDownMenu(String name, String selection, String[] values, boolean required) {
    this(name, selection, values, values, required);
    this.multSelections.add(selection);
  }
  
  public MilestoneFormDropDownMenu(String name, String selection, String values, boolean required) {
    this(name, selection, values, values, required);
    this.multSelections.add(selection);
  }
  
  public MilestoneFormDropDownMenu(String name, String values, boolean required) { this(name, "", values, required); }
  
  public MilestoneFormDropDownMenu(String name, String[] values, boolean required) { this(name, "", values, required); }
  
  public MilestoneFormDropDownMenu(String name, String values) { this(name, "", values, false); }
  
  public MilestoneFormDropDownMenu(String name) { this(name, "", "", false); }
  
  public void setMultiple(boolean multiple) { this.multiple = multiple; }
  
  public boolean getMultiple() { return this.multiple; }
  
  public ArrayList getMultipleSelections() { return this.multSelections; }
  
  public void setValue(Context context) {
    if (this.multiple) {
      String[] multSels = (String[])null;
      try {
        multSels = context.getRequest().getParameterValues(getName());
      } catch (IllegalArgumentException iaexc) {
        System.out.println("Illegal argument exception: " + this.name);
      } 
      if (multSels != null) {
        this.multSelections = new ArrayList();
        for (int i = 0; i < multSels.length; i++)
          this.multSelections.add(multSels[i]); 
      } 
    } else {
      setValue(context.getRequestValue(getName(), this.selection));
      this.multSelections = new ArrayList();
      this.multSelections.add(context.getRequestValue(getName(), this.selection));
    } 
  }
  
  public ArrayList getStringValues() { return this.multSelections; }
  
  public String render() {
    String action = " ";
    if (getAction() != null)
      action = " onChange=\"" + getAction() + "\" "; 
    StringBuffer buffer = new StringBuffer(100);
    buffer.append("<select name=\"");
    buffer.append(getName());
    buffer.append('"');
    buffer.append(getTabIndex());
    buffer.append(getFormEvents());
    buffer.append(getEnabledString());
    buffer.append(getId());
    buffer.append(getClassName());
    buffer.append(action);
    if (this.multiple)
      buffer.append("MULTIPLE"); 
    buffer.append('>');
    for (int i = 0; i < this.values.size(); i++) {
      String currentElement = (String)this.values.elementAt(i);
      buffer.append("<option value=\"");
      buffer.append(currentElement);
      buffer.append('"');
      if (this.multiple && this.multSelections != null) {
        for (int m = 0; m < this.multSelections.size(); m++) {
          if (currentElement.equals((String)this.multSelections.get(m))) {
            buffer.append(" selected");
            break;
          } 
        } 
      } else if (currentElement.equals(getStringValue())) {
        buffer.append(" selected");
      } 
      buffer.append('>');
      buffer.append(this.menuText.elementAt(i));
    } 
    buffer.append("</select>");
    return buffer.toString();
  }
  
  public int getTabIndexInt() { return this.tabIndex; }
  
  public void setMultSelections(ArrayList multSelections) { this.multSelections = multSelections; }
  
  public ArrayList getMultSelections() { return this.multSelections; }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\MilestoneFormDropDownMenu.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */