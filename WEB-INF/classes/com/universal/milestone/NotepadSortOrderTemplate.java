package WEB-INF.classes.com.universal.milestone;

import com.techempower.gemini.Context;
import com.techempower.gemini.Dispatcher;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.Notepad;
import com.universal.milestone.NotepadSortOrderInterface;
import com.universal.milestone.NotepadSortOrderTemplate;
import com.universal.milestone.User;

public class NotepadSortOrderTemplate implements NotepadSortOrderInterface {
  protected String templateOrderCol = "Template";
  
  protected String templateOrderBy = "";
  
  protected int templateOrderColNo = 0;
  
  public void setTemplateOrderCol(String orderCol) { this.templateOrderCol = orderCol; }
  
  public String getTemplateOrderCol() { return this.templateOrderCol; }
  
  public void setTemplateOrderBy(String orderBy) { this.templateOrderBy = orderBy; }
  
  public String getTemplateOrderBy() { return this.templateOrderBy; }
  
  public void setTemplateOrderColNo(int colNo) { this.templateOrderColNo = colNo; }
  
  public int getTemplateOrderColNo() { return this.templateOrderColNo; }
  
  public String getNotepadOrderCol() { return this.templateOrderCol; }
  
  public boolean isSortAscending() {
    if (this.templateOrderBy.indexOf(" DESC ") == -1)
      return true; 
    return false;
  }
  
  public boolean sortHelper(Dispatcher dispatcher, Context context, Notepad notepad) {
    int userId = ((User)context.getSessionValue("user")).getUserId();
    String sort = "";
    int sortInd = 0;
    String orderColLast = getTemplateOrderCol();
    String orderCol = context.getParameter("OrderBy");
    String orderBy = getTemplateOrderBy();
    if (orderCol.equalsIgnoreCase("Template"))
      sortInd = 0; 
    if (orderCol.equalsIgnoreCase("Format"))
      sortInd = 1; 
    if (orderCol.equalsIgnoreCase("Owner"))
      sortInd = 2; 
    if (orderCol.equalsIgnoreCase(orderColLast)) {
      if (orderBy.indexOf(" DESC ") == -1) {
        sort = String.valueOf(MilestoneHelper.SORT_TEMPLATE[sortInd]) + " DESC ";
      } else {
        sort = MilestoneHelper.SORT_TEMPLATE[sortInd];
      } 
    } else if (orderBy.indexOf(" DESC ") == -1) {
      sort = MilestoneHelper.SORT_TEMPLATE[sortInd];
    } else {
      sort = String.valueOf(MilestoneHelper.SORT_TEMPLATE[sortInd]) + " DESC ";
    } 
    if (sortInd != 0)
      sort = String.valueOf(sort) + "," + MilestoneHelper.SORT_TEMPLATE[0]; 
    setTemplateOrderBy(sort);
    setTemplateOrderCol(orderCol);
    setTemplateOrderColNo(sortInd);
    notepad.setOrderBy(" ORDER BY " + sort);
    return true;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\NotepadSortOrderTemplate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */