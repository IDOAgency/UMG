/*      */ package WEB-INF.classes.com.universal.milestone;
/*      */ 
/*      */ import com.techempower.ComponentLog;
/*      */ import com.techempower.EnhancedProperties;
/*      */ import com.techempower.gemini.GeminiApplication;
/*      */ import com.universal.milestone.CorporateObjectSearchObj;
/*      */ import com.universal.milestone.MilestoneConstants;
/*      */ import com.universal.milestone.Notepad;
/*      */ import com.universal.milestone.NotepadContentObject;
/*      */ import java.util.Vector;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Notepad
/*      */   implements MilestoneConstants, Cloneable
/*      */ {
/*      */   public static final String COMPONENT_CODE = "note";
/*      */   protected static ComponentLog log;
/*      */   protected String title;
/*      */   protected String pageStatus;
/*      */   protected String errorMessage;
/*      */   protected int totalPages;
/*      */   protected int pageNumber;
/*      */   protected int totalRecords;
/*      */   protected int recordsPerPage;
/*      */   protected String[] columnNames;
/*      */   protected int sort;
/*      */   protected int notepadType;
/*      */   protected Object selected;
/*      */   protected boolean visible;
/*      */   protected boolean nextVisible;
/*      */   protected boolean previousVisible;
/*      */   protected boolean firstVisible;
/*      */   protected boolean lastVisible;
/*      */   protected boolean searchVisible;
/*      */   protected boolean switchToTaskVisible;
/*      */   protected boolean switchToSelectionVisible;
/*      */   protected boolean assignTasksVisible;
/*      */   protected boolean switchToCompaniesVisible;
/*      */   protected boolean switchToUsersVisible;
/*      */   protected boolean switchToDetailsVisible;
/*      */   protected boolean switchToHeadersVisible;
/*      */   protected boolean switchToTemplateVisible;
/*      */   protected Vector allContents;
/*      */   protected Vector pageContents;
/*      */   protected CorporateObjectSearchObj corporateSearchObj;
/*      */   protected String searchQuery;
/*      */   protected String orderBy;
/*      */   protected boolean selectionDateDirection;
/*      */   protected boolean selectionTitleDirection;
/*      */   protected boolean selectionArtistDirection;
/*      */   protected boolean selectionUpcDirection;
/*      */   protected boolean selectionPrefixDirection;
/*      */   protected boolean selectionSelNoDirection;
/*      */   protected String previousSortHandler;
/*      */   protected boolean showGroupButtons;
/*      */   protected int sortOrder;
/*      */   protected int maxRecords;
/*      */   
/*  122 */   public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getLog("note"); } public Notepad(Vector allContents, int pageNumber, int recordsPerPage, String title, int type, String[] columnNames) { this.title = ""; this.pageStatus = ""; this.errorMessage = ""; this.totalPages = 0; this.pageNumber = 0; this.totalRecords = 0; this.recordsPerPage = 0; this.columnNames = null; this.sort = 0; this.notepadType = -1; this.selected = null; this.visible = true; this.nextVisible = false; this.previousVisible = false; this.firstVisible = false; this.lastVisible = false; this.searchVisible = true; this.switchToTaskVisible = false; this.switchToSelectionVisible = false; this.assignTasksVisible = false; this.switchToCompaniesVisible = false; this.switchToUsersVisible = false; this.switchToDetailsVisible = false; this.switchToHeadersVisible = false; this.switchToTemplateVisible = false; this.allContents = null; this.pageContents = null; this.corporateSearchObj = null; this.searchQuery = ""; this.orderBy = ""; this.selectionDateDirection = false; this.selectionTitleDirection = false;
/*      */     this.selectionArtistDirection = false;
/*      */     this.selectionUpcDirection = false;
/*      */     this.selectionPrefixDirection = false;
/*      */     this.selectionSelNoDirection = false;
/*      */     this.previousSortHandler = "";
/*      */     this.showGroupButtons = true;
/*      */     this.sortOrder = 0;
/*      */     this.maxRecords = 225;
/*  131 */     this.title = title;
/*  132 */     this.columnNames = columnNames;
/*  133 */     if (recordsPerPage != 15) {
/*  134 */       this.recordsPerPage = 15;
/*      */     } else {
/*  136 */       this.recordsPerPage = recordsPerPage;
/*      */     } 
/*  138 */     this.pageNumber = pageNumber;
/*  139 */     this.allContents = allContents;
/*  140 */     setPageStats();
/*  141 */     this.pageContents = getPageContents();
/*  142 */     this.notepadType = type;
/*      */     
/*  144 */     setButtonVisiblity(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  153 */   public void setSwitchToTaskVisible(boolean visible) { this.switchToTaskVisible = visible; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  162 */   public void setSwitchToDetailVisible(boolean visible) { this.switchToDetailsVisible = visible; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  171 */   public void setSwitchToCompaniesVisible(boolean visible) { this.switchToCompaniesVisible = visible; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  179 */   public boolean getSelectionDateDirection() { return this.selectionDateDirection; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  186 */   public void setSelectionDateDirection(boolean direction) { this.selectionDateDirection = direction; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  194 */   public boolean getSelectionTitleDirection() { return this.selectionTitleDirection; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  201 */   public void setSelectionTitleDirection(boolean direction) { this.selectionTitleDirection = direction; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  209 */   public boolean getSelectionArtistDirection() { return this.selectionArtistDirection; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  216 */   public void setSelectionArtistDirection(boolean direction) { this.selectionArtistDirection = direction; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  224 */   public boolean getSelectionSelNoDirection() { return this.selectionSelNoDirection; }
/*      */ 
/*      */ 
/*      */   
/*  228 */   public void setSelectionSelNoDirection(boolean direction) { this.selectionSelNoDirection = direction; }
/*      */ 
/*      */ 
/*      */   
/*  232 */   public boolean getSelectionUpcDirection() { return this.selectionUpcDirection; }
/*      */ 
/*      */ 
/*      */   
/*  236 */   public void setSelectionUpcDirection(boolean direction) { this.selectionUpcDirection = direction; }
/*      */ 
/*      */ 
/*      */   
/*  240 */   public boolean getSelectionPrefixDirection() { return this.selectionPrefixDirection; }
/*      */ 
/*      */ 
/*      */   
/*  244 */   public void setSelectionPrefixDirection(boolean direction) { this.selectionPrefixDirection = direction; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  257 */   public String getTitle() { return this.title; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  267 */   public void setTitle(String title) { this.title = title; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  277 */   public String getPreviousSortHandler() { return this.previousSortHandler; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  286 */   public void setPreviousSortHandler(String handler) { this.previousSortHandler = handler; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  296 */   public int getPageNumber() { return this.pageNumber; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  306 */   public void setPageNumber(int pageNumber) { this.pageNumber = pageNumber; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  317 */   public int getTotalPages() { return this.totalPages; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPageStatus() {
/*  327 */     if (this.totalPages > 0) {
/*      */       
/*  329 */       String records = "records";
/*  330 */       if (this.totalPages == 1) {
/*  331 */         records = "record";
/*      */       }
/*  333 */       this.pageStatus = "Page " + (this.pageNumber + 1) + " of " + 
/*  334 */         this.totalPages + " (total " + this.totalRecords + " " + records + ")";
/*      */     }
/*      */     else {
/*      */       
/*  338 */       this.pageStatus = "(0 records)";
/*      */     } 
/*      */ 
/*      */     
/*  342 */     return this.pageStatus;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  352 */   public int getTotalRecords() { return this.totalRecords; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  362 */   public int getRecordsPerPage() { return this.recordsPerPage; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vector getPageContents() {
/*  373 */     Vector pageContents = new Vector();
/*      */     
/*  375 */     int startRecIndex = this.recordsPerPage * this.pageNumber;
/*  376 */     int endRecIndex = startRecIndex + this.recordsPerPage;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  384 */     if (this.allContents != null)
/*      */     {
/*  386 */       for (int i = startRecIndex; i < endRecIndex; i++) {
/*      */         
/*  388 */         if (this.allContents.size() - 1 >= i)
/*      */         {
/*  390 */           pageContents.addElement(this.allContents.elementAt(i));
/*      */         }
/*      */       } 
/*      */     }
/*  394 */     return pageContents;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNextPage() {
/*  404 */     int nextPage = this.pageNumber;
/*      */     
/*  406 */     if (this.pageNumber < this.totalPages) nextPage = this.pageNumber + 1;
/*      */     
/*  408 */     return nextPage;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPreviousPage() {
/*  419 */     int prevPage = this.pageNumber;
/*      */     
/*  421 */     if (this.pageNumber > 0) prevPage = this.pageNumber - 1;
/*      */     
/*  423 */     return prevPage;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void incrementPage() {
/*  433 */     this.pageNumber++;
/*  434 */     setButtonVisiblity();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void decrementPage() {
/*  444 */     this.pageNumber--;
/*  445 */     setButtonVisiblity();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void firstPage() {
/*  455 */     this.pageNumber = 0;
/*  456 */     setButtonVisiblity();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void lastPage() {
/*  466 */     this.pageNumber = this.totalPages - 1;
/*  467 */     setButtonVisiblity();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  477 */   public boolean isNextButtonVisible() { return this.nextVisible; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  487 */   public boolean isPreviousButtonVisible() { return this.previousVisible; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  497 */   public boolean isFirstButtonVisible() { return this.firstVisible; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  507 */   public boolean isLastButtonVisible() { return this.lastVisible; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  518 */   public boolean isSearchButtonVisible() { return this.searchVisible; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  528 */   public boolean isSwitchToTaskButtonVisible() { return this.switchToTaskVisible; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  538 */   public boolean isSwitchToSelectionButtonVisible() { return this.switchToSelectionVisible; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  548 */   public boolean isAssignTasksButtonVisible() { return this.assignTasksVisible; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  558 */   public boolean isSwitchToCompaniesButtonVisible() { return this.switchToCompaniesVisible; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  568 */   public boolean isSwitchToUsersButtonVisible() { return this.switchToUsersVisible; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  578 */   public boolean isSwitchToDetailsButtonVisible() { return this.switchToDetailsVisible; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  588 */   public boolean isSwitchToHeadersButtonVisible() { return this.switchToHeadersVisible; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  598 */   public boolean isSwitchToTemplateButtonVisible() { return this.switchToTemplateVisible; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  608 */   public String[] getColumnNames() { return this.columnNames; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  618 */   public void setColumnNames(String[] columnNames) { this.columnNames = columnNames; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  628 */   public Vector getAllContents() { return this.allContents; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAllContents(Vector contents) {
/*  638 */     this.allContents = contents;
/*      */     
/*  640 */     setPageStats();
/*  641 */     setButtonVisiblity();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAllContents(Vector contents, int totalRecords) {
/*  651 */     this.allContents = contents;
/*  652 */     setPageStats(totalRecords);
/*  653 */     setButtonVisiblity();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getSelected() {
/*  663 */     if (this.allContents.size() > 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  669 */       if (this.selected == null)
/*      */       {
/*      */         
/*  672 */         if (this.sortOrder == 7 || this.sortOrder == 8) {
/*  673 */           this.selected = this.allContents.get(this.allContents.size() - 1);
/*      */         } else {
/*  675 */           this.selected = this.allContents.get(0);
/*      */         } 
/*      */       }
/*  678 */       return this.selected;
/*      */     } 
/*      */     
/*  681 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSelected(Object selected) {
/*  691 */     this.selected = selected;
/*  692 */     goToSelectedPage();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object validateSelected() {
/*  705 */     if (getAllContents() != null && 
/*  706 */       getAllContents().size() > 0 && 
/*  707 */       getSelected() != null) {
/*      */       
/*  709 */       NotepadContentObject selected = (NotepadContentObject)getSelected();
/*      */       
/*  711 */       boolean foundObject = false;
/*      */       
/*  713 */       for (int i = 0; i < getAllContents().size(); i++) {
/*      */         
/*  715 */         NotepadContentObject nco = (NotepadContentObject)getAllContents().get(i);
/*      */         
/*  717 */         if (selected.getNotepadContentObjectId().equals(nco.getNotepadContentObjectId())) {
/*  718 */           foundObject = true;
/*      */         }
/*      */       } 
/*      */       
/*  722 */       if (!foundObject) {
/*  723 */         this.selected = null;
/*      */       
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/*  729 */       this.selected = null;
/*      */     } 
/*      */     
/*  732 */     return this.selected;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  742 */   public int getNotePadType() { return this.notepadType; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNotePadType(int type) {
/*  752 */     this.notepadType = type;
/*  753 */     setButtonVisiblity();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  763 */   public String getSearchQuery() { return this.searchQuery; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSearchQuery(String query) {
/*  773 */     this.searchQuery = query;
/*      */ 
/*      */     
/*  776 */     goToSelectedPage();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  787 */   public String getOrderBy() { return this.orderBy; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  799 */   public void setOrderBy(String order) { this.orderBy = order; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  810 */   public CorporateObjectSearchObj getCorporateObjectSearchObj() { return this.corporateSearchObj; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  822 */   public void setCorporateObjectSearchObj(CorporateObjectSearchObj search) { this.corporateSearchObj = search; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getErrorMessage() {
/*  832 */     this.errorMessage = NOTEPAD_ERROR_MSG[this.notepadType];
/*  833 */     return this.errorMessage;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  843 */   public boolean isVisible() { return this.visible; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  853 */   public void setVisible(boolean visible) { this.visible = visible; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setButtonVisiblity() {
/*  866 */     if (this.pageNumber > 0) {
/*      */       
/*  868 */       this.previousVisible = true;
/*      */     }
/*      */     else {
/*      */       
/*  872 */       this.previousVisible = false;
/*      */     } 
/*      */     
/*  875 */     if (this.pageNumber < this.totalPages - 1) {
/*      */       
/*  877 */       this.nextVisible = true;
/*      */     }
/*      */     else {
/*      */       
/*  881 */       this.nextVisible = false;
/*      */     } 
/*      */     
/*  884 */     this.firstVisible = (this.pageNumber > 0);
/*  885 */     this.lastVisible = (this.pageNumber < this.totalPages - 1);
/*      */     
/*  887 */     this.searchVisible = true;
/*      */     
/*  889 */     this.switchToTaskVisible = false;
/*  890 */     this.switchToSelectionVisible = false;
/*  891 */     this.assignTasksVisible = false;
/*  892 */     this.switchToCompaniesVisible = false;
/*  893 */     this.switchToUsersVisible = false;
/*  894 */     this.switchToDetailsVisible = false;
/*  895 */     this.switchToTemplateVisible = false;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  900 */     switch (this.notepadType) {
/*      */       
/*      */       case 0:
/*  903 */         this.switchToTaskVisible = false;
/*      */         break;
/*      */       
/*      */       case 1:
/*  907 */         this.switchToTaskVisible = true;
/*      */         break;
/*      */       
/*      */       case 2:
/*  911 */         this.switchToSelectionVisible = true;
/*  912 */         this.assignTasksVisible = true;
/*      */         break;
/*      */       
/*      */       case 7:
/*  916 */         this.switchToCompaniesVisible = true;
/*      */         break;
/*      */       
/*      */       case 8:
/*  920 */         this.switchToUsersVisible = true;
/*      */         break;
/*      */       
/*      */       case 21:
/*  924 */         this.switchToUsersVisible = true;
/*      */         break;
/*      */       
/*      */       case 14:
/*  928 */         this.switchToDetailsVisible = true;
/*      */         break;
/*      */       
/*      */       case 18:
/*  932 */         this.switchToHeadersVisible = true;
/*      */         break;
/*      */       
/*      */       case 5:
/*  936 */         this.switchToTaskVisible = true;
/*      */         break;
/*      */       
/*      */       case 19:
/*  940 */         this.switchToTemplateVisible = true;
/*  941 */         this.assignTasksVisible = true;
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setPageStats() {
/*      */     try {
/*  954 */       this.totalRecords = this.allContents.size();
/*  955 */       this.totalPages = this.totalRecords / this.recordsPerPage;
/*  956 */       if (this.totalRecords % this.recordsPerPage != 0) this.totalPages++;
/*      */     
/*  958 */     } catch (NullPointerException nullPointerException) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPageStats(int totalRecords) {
/*      */     try {
/*  973 */       this.totalRecords = totalRecords;
/*  974 */       this.totalPages = this.totalRecords / this.recordsPerPage;
/*  975 */       if (this.totalRecords % this.recordsPerPage != 0) this.totalPages++;
/*      */     
/*  977 */     } catch (NullPointerException nullPointerException) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void goToSelectedPage() {
/*      */     try {
/*  993 */       String selectedObjId = ((NotepadContentObject)getSelected()).getNotepadContentObjectId();
/*      */ 
/*      */       
/*  996 */       if (getSelected() != null && 
/*  997 */         !((NotepadContentObject)getSelected()).getNotepadContentObjectId().equals("0"))
/*      */       {
/*  999 */         for (int i = 0; i < getAllContents().size(); i++)
/*      */         {
/* 1001 */           String contentObjId = ((NotepadContentObject)getAllContents().get(i)).getNotepadContentObjectId();
/*      */           
/* 1003 */           if (selectedObjId.equalsIgnoreCase(contentObjId))
/*      */           {
/*      */             
/* 1006 */             this.pageNumber = i / this.recordsPerPage;
/*      */           
/*      */           }
/*      */         
/*      */         }
/*      */       
/*      */       }
/*      */       else
/*      */       {
/* 1015 */         setPageNumber(0);
/*      */       
/*      */       }
/*      */ 
/*      */     
/*      */     }
/* 1021 */     catch (Exception e) {
/*      */       
/* 1023 */       log.debug("---Problem going to selcted page in the notepad---");
/* 1024 */       setPageNumber(0);
/*      */     } 
/*      */     
/* 1027 */     setButtonVisiblity();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void newSelectedReset() {
/* 1046 */     this.searchQuery = "";
/* 1047 */     this.orderBy = "";
/*      */     
/* 1049 */     this.corporateSearchObj = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setupSortingDirections(String handlerDescription) {
/* 1063 */     if (getPreviousSortHandler().equals("")) {
/* 1064 */       setPreviousSortHandler(handlerDescription);
/*      */     
/*      */     }
/* 1067 */     else if (!getPreviousSortHandler().equals(handlerDescription)) {
/* 1068 */       setPreviousSortHandler(handlerDescription);
/* 1069 */       setSelectionArtistDirection(false);
/* 1070 */       setSelectionDateDirection(false);
/* 1071 */       setSelectionTitleDirection(false);
/* 1072 */       setSelectionSelNoDirection(false);
/* 1073 */       setSelectionUpcDirection(false);
/* 1074 */       setSelectionPrefixDirection(false);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1084 */   public void setShowGroupButtons(boolean showGroupButtons) { this.showGroupButtons = showGroupButtons; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1093 */   public boolean isShowGroupButtonsVisible() { return this.showGroupButtons; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1100 */   public void setSortOrder(int sortOrder) { this.sortOrder = sortOrder; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1107 */   public int getSortOrder() { return this.sortOrder; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1119 */   public void setMaxRecords(int maxRecords) { this.maxRecords = maxRecords; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1126 */   public int getMaxRecords() { return this.maxRecords; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1137 */   public Object clone() { return super.clone(); }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\Notepad.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */