package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.EnhancedProperties;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.CorporateObjectSearchObj;
import com.universal.milestone.MilestoneConstants;
import com.universal.milestone.Notepad;
import com.universal.milestone.NotepadContentObject;
import java.util.Vector;

public class Notepad implements MilestoneConstants, Cloneable {
  public static final String COMPONENT_CODE = "note";
  
  protected static ComponentLog log;
  
  protected String title;
  
  protected String pageStatus;
  
  protected String errorMessage;
  
  protected int totalPages;
  
  protected int pageNumber;
  
  protected int totalRecords;
  
  protected int recordsPerPage;
  
  protected String[] columnNames;
  
  protected int sort;
  
  protected int notepadType;
  
  protected Object selected;
  
  protected boolean visible;
  
  protected boolean nextVisible;
  
  protected boolean previousVisible;
  
  protected boolean firstVisible;
  
  protected boolean lastVisible;
  
  protected boolean searchVisible;
  
  protected boolean switchToTaskVisible;
  
  protected boolean switchToSelectionVisible;
  
  protected boolean assignTasksVisible;
  
  protected boolean switchToCompaniesVisible;
  
  protected boolean switchToUsersVisible;
  
  protected boolean switchToDetailsVisible;
  
  protected boolean switchToHeadersVisible;
  
  protected boolean switchToTemplateVisible;
  
  protected Vector allContents;
  
  protected Vector pageContents;
  
  protected CorporateObjectSearchObj corporateSearchObj;
  
  protected String searchQuery;
  
  protected String orderBy;
  
  protected boolean selectionDateDirection;
  
  protected boolean selectionTitleDirection;
  
  protected boolean selectionArtistDirection;
  
  protected boolean selectionUpcDirection;
  
  protected boolean selectionPrefixDirection;
  
  protected boolean selectionSelNoDirection;
  
  protected String previousSortHandler;
  
  protected boolean showGroupButtons;
  
  protected int sortOrder;
  
  protected int maxRecords;
  
  public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getLog("note"); }
  
  public Notepad(Vector allContents, int pageNumber, int recordsPerPage, String title, int type, String[] columnNames) {
    this.title = "";
    this.pageStatus = "";
    this.errorMessage = "";
    this.totalPages = 0;
    this.pageNumber = 0;
    this.totalRecords = 0;
    this.recordsPerPage = 0;
    this.columnNames = null;
    this.sort = 0;
    this.notepadType = -1;
    this.selected = null;
    this.visible = true;
    this.nextVisible = false;
    this.previousVisible = false;
    this.firstVisible = false;
    this.lastVisible = false;
    this.searchVisible = true;
    this.switchToTaskVisible = false;
    this.switchToSelectionVisible = false;
    this.assignTasksVisible = false;
    this.switchToCompaniesVisible = false;
    this.switchToUsersVisible = false;
    this.switchToDetailsVisible = false;
    this.switchToHeadersVisible = false;
    this.switchToTemplateVisible = false;
    this.allContents = null;
    this.pageContents = null;
    this.corporateSearchObj = null;
    this.searchQuery = "";
    this.orderBy = "";
    this.selectionDateDirection = false;
    this.selectionTitleDirection = false;
    this.selectionArtistDirection = false;
    this.selectionUpcDirection = false;
    this.selectionPrefixDirection = false;
    this.selectionSelNoDirection = false;
    this.previousSortHandler = "";
    this.showGroupButtons = true;
    this.sortOrder = 0;
    this.maxRecords = 225;
    this.title = title;
    this.columnNames = columnNames;
    if (recordsPerPage != 15) {
      this.recordsPerPage = 15;
    } else {
      this.recordsPerPage = recordsPerPage;
    } 
    this.pageNumber = pageNumber;
    this.allContents = allContents;
    setPageStats();
    this.pageContents = getPageContents();
    this.notepadType = type;
    setButtonVisiblity();
  }
  
  public void setSwitchToTaskVisible(boolean visible) { this.switchToTaskVisible = visible; }
  
  public void setSwitchToDetailVisible(boolean visible) { this.switchToDetailsVisible = visible; }
  
  public void setSwitchToCompaniesVisible(boolean visible) { this.switchToCompaniesVisible = visible; }
  
  public boolean getSelectionDateDirection() { return this.selectionDateDirection; }
  
  public void setSelectionDateDirection(boolean direction) { this.selectionDateDirection = direction; }
  
  public boolean getSelectionTitleDirection() { return this.selectionTitleDirection; }
  
  public void setSelectionTitleDirection(boolean direction) { this.selectionTitleDirection = direction; }
  
  public boolean getSelectionArtistDirection() { return this.selectionArtistDirection; }
  
  public void setSelectionArtistDirection(boolean direction) { this.selectionArtistDirection = direction; }
  
  public boolean getSelectionSelNoDirection() { return this.selectionSelNoDirection; }
  
  public void setSelectionSelNoDirection(boolean direction) { this.selectionSelNoDirection = direction; }
  
  public boolean getSelectionUpcDirection() { return this.selectionUpcDirection; }
  
  public void setSelectionUpcDirection(boolean direction) { this.selectionUpcDirection = direction; }
  
  public boolean getSelectionPrefixDirection() { return this.selectionPrefixDirection; }
  
  public void setSelectionPrefixDirection(boolean direction) { this.selectionPrefixDirection = direction; }
  
  public String getTitle() { return this.title; }
  
  public void setTitle(String title) { this.title = title; }
  
  public String getPreviousSortHandler() { return this.previousSortHandler; }
  
  public void setPreviousSortHandler(String handler) { this.previousSortHandler = handler; }
  
  public int getPageNumber() { return this.pageNumber; }
  
  public void setPageNumber(int pageNumber) { this.pageNumber = pageNumber; }
  
  public int getTotalPages() { return this.totalPages; }
  
  public String getPageStatus() {
    if (this.totalPages > 0) {
      String records = "records";
      if (this.totalPages == 1)
        records = "record"; 
      this.pageStatus = "Page " + (this.pageNumber + 1) + " of " + 
        this.totalPages + " (total " + this.totalRecords + " " + records + ")";
    } else {
      this.pageStatus = "(0 records)";
    } 
    return this.pageStatus;
  }
  
  public int getTotalRecords() { return this.totalRecords; }
  
  public int getRecordsPerPage() { return this.recordsPerPage; }
  
  public Vector getPageContents() {
    Vector pageContents = new Vector();
    int startRecIndex = this.recordsPerPage * this.pageNumber;
    int endRecIndex = startRecIndex + this.recordsPerPage;
    if (this.allContents != null)
      for (int i = startRecIndex; i < endRecIndex; i++) {
        if (this.allContents.size() - 1 >= i)
          pageContents.addElement(this.allContents.elementAt(i)); 
      }  
    return pageContents;
  }
  
  public int getNextPage() {
    int nextPage = this.pageNumber;
    if (this.pageNumber < this.totalPages)
      nextPage = this.pageNumber + 1; 
    return nextPage;
  }
  
  public int getPreviousPage() {
    int prevPage = this.pageNumber;
    if (this.pageNumber > 0)
      prevPage = this.pageNumber - 1; 
    return prevPage;
  }
  
  public void incrementPage() {
    this.pageNumber++;
    setButtonVisiblity();
  }
  
  public void decrementPage() {
    this.pageNumber--;
    setButtonVisiblity();
  }
  
  public void firstPage() {
    this.pageNumber = 0;
    setButtonVisiblity();
  }
  
  public void lastPage() {
    this.pageNumber = this.totalPages - 1;
    setButtonVisiblity();
  }
  
  public boolean isNextButtonVisible() { return this.nextVisible; }
  
  public boolean isPreviousButtonVisible() { return this.previousVisible; }
  
  public boolean isFirstButtonVisible() { return this.firstVisible; }
  
  public boolean isLastButtonVisible() { return this.lastVisible; }
  
  public boolean isSearchButtonVisible() { return this.searchVisible; }
  
  public boolean isSwitchToTaskButtonVisible() { return this.switchToTaskVisible; }
  
  public boolean isSwitchToSelectionButtonVisible() { return this.switchToSelectionVisible; }
  
  public boolean isAssignTasksButtonVisible() { return this.assignTasksVisible; }
  
  public boolean isSwitchToCompaniesButtonVisible() { return this.switchToCompaniesVisible; }
  
  public boolean isSwitchToUsersButtonVisible() { return this.switchToUsersVisible; }
  
  public boolean isSwitchToDetailsButtonVisible() { return this.switchToDetailsVisible; }
  
  public boolean isSwitchToHeadersButtonVisible() { return this.switchToHeadersVisible; }
  
  public boolean isSwitchToTemplateButtonVisible() { return this.switchToTemplateVisible; }
  
  public String[] getColumnNames() { return this.columnNames; }
  
  public void setColumnNames(String[] columnNames) { this.columnNames = columnNames; }
  
  public Vector getAllContents() { return this.allContents; }
  
  public void setAllContents(Vector contents) {
    this.allContents = contents;
    setPageStats();
    setButtonVisiblity();
  }
  
  public void setAllContents(Vector contents, int totalRecords) {
    this.allContents = contents;
    setPageStats(totalRecords);
    setButtonVisiblity();
  }
  
  public Object getSelected() {
    if (this.allContents.size() > 0) {
      if (this.selected == null)
        if (this.sortOrder == 7 || this.sortOrder == 8) {
          this.selected = this.allContents.get(this.allContents.size() - 1);
        } else {
          this.selected = this.allContents.get(0);
        }  
      return this.selected;
    } 
    return null;
  }
  
  public void setSelected(Object selected) {
    this.selected = selected;
    goToSelectedPage();
  }
  
  public Object validateSelected() {
    if (getAllContents() != null && 
      getAllContents().size() > 0 && 
      getSelected() != null) {
      NotepadContentObject selected = (NotepadContentObject)getSelected();
      boolean foundObject = false;
      for (int i = 0; i < getAllContents().size(); i++) {
        NotepadContentObject nco = (NotepadContentObject)getAllContents().get(i);
        if (selected.getNotepadContentObjectId().equals(nco.getNotepadContentObjectId()))
          foundObject = true; 
      } 
      if (!foundObject)
        this.selected = null; 
    } else {
      this.selected = null;
    } 
    return this.selected;
  }
  
  public int getNotePadType() { return this.notepadType; }
  
  public void setNotePadType(int type) {
    this.notepadType = type;
    setButtonVisiblity();
  }
  
  public String getSearchQuery() { return this.searchQuery; }
  
  public void setSearchQuery(String query) {
    this.searchQuery = query;
    goToSelectedPage();
  }
  
  public String getOrderBy() { return this.orderBy; }
  
  public void setOrderBy(String order) { this.orderBy = order; }
  
  public CorporateObjectSearchObj getCorporateObjectSearchObj() { return this.corporateSearchObj; }
  
  public void setCorporateObjectSearchObj(CorporateObjectSearchObj search) { this.corporateSearchObj = search; }
  
  public String getErrorMessage() {
    this.errorMessage = NOTEPAD_ERROR_MSG[this.notepadType];
    return this.errorMessage;
  }
  
  public boolean isVisible() { return this.visible; }
  
  public void setVisible(boolean visible) { this.visible = visible; }
  
  private void setButtonVisiblity() {
    if (this.pageNumber > 0) {
      this.previousVisible = true;
    } else {
      this.previousVisible = false;
    } 
    if (this.pageNumber < this.totalPages - 1) {
      this.nextVisible = true;
    } else {
      this.nextVisible = false;
    } 
    this.firstVisible = (this.pageNumber > 0);
    this.lastVisible = (this.pageNumber < this.totalPages - 1);
    this.searchVisible = true;
    this.switchToTaskVisible = false;
    this.switchToSelectionVisible = false;
    this.assignTasksVisible = false;
    this.switchToCompaniesVisible = false;
    this.switchToUsersVisible = false;
    this.switchToDetailsVisible = false;
    this.switchToTemplateVisible = false;
    switch (this.notepadType) {
      case 0:
        this.switchToTaskVisible = false;
        break;
      case 1:
        this.switchToTaskVisible = true;
        break;
      case 2:
        this.switchToSelectionVisible = true;
        this.assignTasksVisible = true;
        break;
      case 7:
        this.switchToCompaniesVisible = true;
        break;
      case 8:
        this.switchToUsersVisible = true;
        break;
      case 21:
        this.switchToUsersVisible = true;
        break;
      case 14:
        this.switchToDetailsVisible = true;
        break;
      case 18:
        this.switchToHeadersVisible = true;
        break;
      case 5:
        this.switchToTaskVisible = true;
        break;
      case 19:
        this.switchToTemplateVisible = true;
        this.assignTasksVisible = true;
        break;
    } 
  }
  
  private void setPageStats() {
    try {
      this.totalRecords = this.allContents.size();
      this.totalPages = this.totalRecords / this.recordsPerPage;
      if (this.totalRecords % this.recordsPerPage != 0)
        this.totalPages++; 
    } catch (NullPointerException nullPointerException) {}
  }
  
  public void setPageStats(int totalRecords) {
    try {
      this.totalRecords = totalRecords;
      this.totalPages = this.totalRecords / this.recordsPerPage;
      if (this.totalRecords % this.recordsPerPage != 0)
        this.totalPages++; 
    } catch (NullPointerException nullPointerException) {}
  }
  
  public void goToSelectedPage() {
    try {
      String selectedObjId = ((NotepadContentObject)getSelected()).getNotepadContentObjectId();
      if (getSelected() != null && 
        !((NotepadContentObject)getSelected()).getNotepadContentObjectId().equals("0")) {
        for (int i = 0; i < getAllContents().size(); i++) {
          String contentObjId = ((NotepadContentObject)getAllContents().get(i)).getNotepadContentObjectId();
          if (selectedObjId.equalsIgnoreCase(contentObjId))
            this.pageNumber = i / this.recordsPerPage; 
        } 
      } else {
        setPageNumber(0);
      } 
    } catch (Exception e) {
      log.debug("---Problem going to selcted page in the notepad---");
      setPageNumber(0);
    } 
    setButtonVisiblity();
  }
  
  public void newSelectedReset() {
    this.searchQuery = "";
    this.orderBy = "";
    this.corporateSearchObj = null;
  }
  
  public void setupSortingDirections(String handlerDescription) {
    if (getPreviousSortHandler().equals("")) {
      setPreviousSortHandler(handlerDescription);
    } else if (!getPreviousSortHandler().equals(handlerDescription)) {
      setPreviousSortHandler(handlerDescription);
      setSelectionArtistDirection(false);
      setSelectionDateDirection(false);
      setSelectionTitleDirection(false);
      setSelectionSelNoDirection(false);
      setSelectionUpcDirection(false);
      setSelectionPrefixDirection(false);
    } 
  }
  
  public void setShowGroupButtons(boolean showGroupButtons) { this.showGroupButtons = showGroupButtons; }
  
  public boolean isShowGroupButtonsVisible() { return this.showGroupButtons; }
  
  public void setSortOrder(int sortOrder) { this.sortOrder = sortOrder; }
  
  public int getSortOrder() { return this.sortOrder; }
  
  public void setMaxRecords(int maxRecords) { this.maxRecords = maxRecords; }
  
  public int getMaxRecords() { return this.maxRecords; }
  
  public Object clone() { return super.clone(); }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\Notepad.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */