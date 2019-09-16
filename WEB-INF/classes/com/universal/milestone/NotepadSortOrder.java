package WEB-INF.classes.com.universal.milestone;

import com.techempower.gemini.Context;
import com.techempower.gemini.Dispatcher;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.Notepad;
import com.universal.milestone.NotepadSortOrder;
import com.universal.milestone.NotepadSortOrderInterface;
import com.universal.milestone.NotepadSortOrderTemplate;
import com.universal.milestone.SelectionManager;
import com.universal.milestone.User;

public class NotepadSortOrder implements NotepadSortOrderInterface {
  protected NotepadSortOrderTemplate notepadSortOrderTemplate = new NotepadSortOrderTemplate();
  
  protected String selectionOrderCol = "Artist";
  
  protected String selectionOrderBy = "";
  
  protected boolean showGroupButtons = true;
  
  protected int selectionOrderColNo = 0;
  
  public NotepadSortOrderTemplate getNotepadSortOrderTemplate() { return this.notepadSortOrderTemplate; }
  
  public void setSelectionOrderCol(String orderCol) { this.selectionOrderCol = orderCol; }
  
  public String getSelectionOrderCol() { return this.selectionOrderCol; }
  
  public void setSelectionOrderBy(String orderBy) { this.selectionOrderBy = orderBy; }
  
  public String getSelectionOrderBy() { return this.selectionOrderBy; }
  
  public void setShowGroupButtons(boolean showGroupButtons) { this.showGroupButtons = showGroupButtons; }
  
  public boolean getShowGroupButtons() { return this.showGroupButtons; }
  
  public void setSelectionOrderColNo(int colNo) { this.selectionOrderColNo = colNo; }
  
  public int getSelectionOrderColNo() { return this.selectionOrderColNo; }
  
  public String getNotepadOrderCol() { return this.selectionOrderCol; }
  
  public int getSelectionGroupSortColNo() {
    int sortColNo = 0;
    if (this.selectionOrderCol.equalsIgnoreCase("Artist"))
      if (this.selectionOrderBy.indexOf(" DESC ") == -1) {
        sortColNo = 0;
      } else {
        sortColNo = 7;
      }  
    if (this.selectionOrderCol.equalsIgnoreCase("Title"))
      if (this.selectionOrderBy.indexOf(" DESC ") == -1) {
        sortColNo = 1;
      } else {
        sortColNo = 8;
      }  
    return sortColNo;
  }
  
  public boolean isSortAscending() {
    if (this.selectionOrderBy.indexOf(" DESC ") == -1)
      return true; 
    return false;
  }
  
  public static NotepadSortOrder getNotepadSortOrderFromSession(Context context) {
    NotepadSortOrder notepadSortOrder = (NotepadSortOrder)context.getSessionValue("notepadSortOrder");
    if (notepadSortOrder == null) {
      context.putSessionValue("notepadSortOrder", new NotepadSortOrder());
      notepadSortOrder = (NotepadSortOrder)context.getSessionValue("notepadSortOrder");
    } 
    return notepadSortOrder;
  }
  
  public boolean sortHelper(Dispatcher dispatcher, Context context, Notepad notepad) {
    User user = (User)context.getSessionValue("user");
    if (user != null)
      user.SS_searchInitiated = true; 
    int userId = ((User)context.getSessionValue("user")).getUserId();
    String sort = "";
    int sortInd = 0;
    String orderColLast = getSelectionOrderCol();
    String orderCol = context.getParameter("OrderBy");
    String orderBy = getSelectionOrderBy();
    String alphaGroupChr = context.getParameter("alphaGroupChr");
    if (notepad.getSearchQuery() == null || notepad.getSearchQuery().equals(""))
      notepad.setSearchQuery(SelectionManager.getInstance().getDefaultQuery(context)); 
    if (orderCol.equalsIgnoreCase("Str Dt")) {
      sortInd = 5;
      if (orderCol.equalsIgnoreCase(orderColLast)) {
        if (orderBy.indexOf(" DESC ") == -1) {
          sort = String.valueOf(MilestoneHelper.NEW_SORT_SELECTION[5]) + " DESC " + ", artist,title,selection_no";
        } else {
          sort = String.valueOf(MilestoneHelper.NEW_SORT_SELECTION[5]) + ",artist,title,selection_no";
        } 
      } else if (orderBy.indexOf(" DESC ") == -1) {
        sort = String.valueOf(MilestoneHelper.NEW_SORT_SELECTION[5]) + ",artist,title,selection_no";
      } else {
        sort = String.valueOf(MilestoneHelper.NEW_SORT_SELECTION[5]) + " DESC " + ",artist,title,selection_no";
      } 
    } 
    if (orderCol.equalsIgnoreCase("Artist"))
      if (orderCol.equalsIgnoreCase(orderColLast)) {
        if (orderBy.indexOf(" DESC ") == -1) {
          sort = String.valueOf(MilestoneHelper.NEW_SORT_SELECTION[0]) + " DESC " + ",title,selection_no,street_date";
          sortInd = 7;
        } else {
          sort = String.valueOf(MilestoneHelper.NEW_SORT_SELECTION[0]) + ",title,selection_no,street_date";
          sortInd = 0;
        } 
      } else if (orderBy.indexOf(" DESC ") == -1) {
        sort = String.valueOf(MilestoneHelper.NEW_SORT_SELECTION[0]) + ",title,selection_no,street_date";
        sortInd = 0;
      } else {
        sort = String.valueOf(MilestoneHelper.NEW_SORT_SELECTION[0]) + " DESC " + ",title,selection_no,street_date";
        sortInd = 7;
      }  
    if (orderCol.equalsIgnoreCase("Title")) {
      sortInd = 1;
      if (orderCol.equalsIgnoreCase(orderColLast)) {
        if (orderBy.indexOf(" DESC ") == -1) {
          sort = String.valueOf(MilestoneHelper.NEW_SORT_SELECTION[1]) + " DESC " + ",artist,selection_no,street_date";
          sortInd = 8;
        } else {
          sort = String.valueOf(MilestoneHelper.NEW_SORT_SELECTION[1]) + ",artist,selection_no,street_date";
          sortInd = 1;
        } 
      } else if (orderBy.indexOf(" DESC ") == -1) {
        sort = String.valueOf(MilestoneHelper.NEW_SORT_SELECTION[1]) + ",artist,selection_no,street_date";
        sortInd = 1;
      } else {
        sort = String.valueOf(MilestoneHelper.NEW_SORT_SELECTION[1]) + " DESC " + ",artist,selection_no,street_date";
        sortInd = 8;
      } 
    } 
    if (orderCol.equalsIgnoreCase("Local Prod #")) {
      sortInd = 2;
      if (orderCol.equalsIgnoreCase(orderColLast)) {
        if (orderBy.indexOf(" DESC ") == -1) {
          sort = String.valueOf(MilestoneHelper.NEW_SORT_SELECTION[2]) + " DESC ";
        } else {
          sort = MilestoneHelper.NEW_SORT_SELECTION[2];
        } 
      } else if (orderBy.indexOf(" DESC ") == -1) {
        sort = MilestoneHelper.NEW_SORT_SELECTION[2];
      } else {
        sort = String.valueOf(MilestoneHelper.NEW_SORT_SELECTION[2]) + " DESC ";
      } 
    } 
    if (orderCol.equalsIgnoreCase("UPC")) {
      sortInd = 3;
      if (orderCol.equalsIgnoreCase(orderColLast)) {
        if (orderBy.indexOf(" DESC ") == -1) {
          sort = String.valueOf(MilestoneHelper.NEW_SORT_SELECTION[3]) + " DESC " + ",selection_no";
        } else {
          sort = String.valueOf(MilestoneHelper.NEW_SORT_SELECTION[3]) + ",selection_no";
        } 
      } else if (orderBy.indexOf(" DESC ") == -1) {
        sort = String.valueOf(MilestoneHelper.NEW_SORT_SELECTION[3]) + ",selection_no";
      } else {
        sort = String.valueOf(MilestoneHelper.NEW_SORT_SELECTION[3]) + " DESC " + ",selection_no";
      } 
    } 
    if (orderCol.equalsIgnoreCase("Prefix")) {
      sortInd = 4;
      if (orderCol.equalsIgnoreCase(orderColLast)) {
        if (orderBy.indexOf(" DESC ") == -1) {
          sort = String.valueOf(MilestoneHelper.NEW_SORT_SELECTION[4]) + " DESC " + ",selection_no";
        } else {
          sort = String.valueOf(MilestoneHelper.NEW_SORT_SELECTION[4]) + ",selection_no";
        } 
      } else if (orderBy.indexOf(" DESC ") == -1) {
        sort = String.valueOf(MilestoneHelper.NEW_SORT_SELECTION[4]) + ",selection_no";
      } else {
        sort = String.valueOf(MilestoneHelper.NEW_SORT_SELECTION[4]) + " DESC " + ",selection_no";
      } 
    } 
    if (orderCol.equalsIgnoreCase("Artist") || orderCol.equalsIgnoreCase("Title")) {
      setShowGroupButtons(true);
    } else {
      setShowGroupButtons(false);
    } 
    setSelectionOrderBy(sort);
    setSelectionOrderCol(orderCol);
    setSelectionOrderColNo(sortInd);
    notepad.setOrderBy(" ORDER BY " + sort);
    if (alphaGroupChr != null && alphaGroupChr.length() > 0)
      if (orderCol.equalsIgnoreCase("Artist")) {
        notepad.setOrderBy(" AND left(artist,1) >= '" + alphaGroupChr + "'" + " ORDER BY " + sort);
      } else {
        notepad.setOrderBy(" AND left(title,1) >= '" + alphaGroupChr + "'" + " ORDER BY " + sort);
      }  
    notepad.setAllContents(null);
    notepad = SelectionManager.getInstance().getSelectionNotepad(context, userId, 0);
    if (alphaGroupChr != null && alphaGroupChr.length() > 0) {
      notepad.setSortOrder(sortInd);
      notepad.setSelected(null);
    } 
    notepad.goToSelectedPage();
    return true;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\NotepadSortOrder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */