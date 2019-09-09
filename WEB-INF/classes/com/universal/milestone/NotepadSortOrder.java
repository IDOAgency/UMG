/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.gemini.Context;
/*     */ import com.techempower.gemini.Dispatcher;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.Notepad;
/*     */ import com.universal.milestone.NotepadSortOrder;
/*     */ import com.universal.milestone.NotepadSortOrderInterface;
/*     */ import com.universal.milestone.NotepadSortOrderTemplate;
/*     */ import com.universal.milestone.SelectionManager;
/*     */ import com.universal.milestone.User;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NotepadSortOrder
/*     */   implements NotepadSortOrderInterface
/*     */ {
/*  39 */   protected NotepadSortOrderTemplate notepadSortOrderTemplate = new NotepadSortOrderTemplate();
/*     */   
/*  41 */   protected String selectionOrderCol = "Artist";
/*  42 */   protected String selectionOrderBy = "";
/*     */   protected boolean showGroupButtons = true;
/*  44 */   protected int selectionOrderColNo = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   public NotepadSortOrderTemplate getNotepadSortOrderTemplate() { return this.notepadSortOrderTemplate; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   public void setSelectionOrderCol(String orderCol) { this.selectionOrderCol = orderCol; }
/*     */ 
/*     */ 
/*     */   
/*  72 */   public String getSelectionOrderCol() { return this.selectionOrderCol; }
/*     */ 
/*     */ 
/*     */   
/*  76 */   public void setSelectionOrderBy(String orderBy) { this.selectionOrderBy = orderBy; }
/*     */ 
/*     */ 
/*     */   
/*  80 */   public String getSelectionOrderBy() { return this.selectionOrderBy; }
/*     */ 
/*     */ 
/*     */   
/*  84 */   public void setShowGroupButtons(boolean showGroupButtons) { this.showGroupButtons = showGroupButtons; }
/*     */ 
/*     */ 
/*     */   
/*  88 */   public boolean getShowGroupButtons() { return this.showGroupButtons; }
/*     */ 
/*     */ 
/*     */   
/*  92 */   public void setSelectionOrderColNo(int colNo) { this.selectionOrderColNo = colNo; }
/*     */ 
/*     */ 
/*     */   
/*  96 */   public int getSelectionOrderColNo() { return this.selectionOrderColNo; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 101 */   public String getNotepadOrderCol() { return this.selectionOrderCol; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSelectionGroupSortColNo() {
/* 108 */     int sortColNo = 0;
/* 109 */     if (this.selectionOrderCol.equalsIgnoreCase("Artist"))
/*     */     {
/* 111 */       if (this.selectionOrderBy.indexOf(" DESC ") == -1) {
/* 112 */         sortColNo = 0;
/*     */       } else {
/* 114 */         sortColNo = 7;
/*     */       }  } 
/* 116 */     if (this.selectionOrderCol.equalsIgnoreCase("Title"))
/*     */     {
/* 118 */       if (this.selectionOrderBy.indexOf(" DESC ") == -1) {
/* 119 */         sortColNo = 1;
/*     */       } else {
/* 121 */         sortColNo = 8;
/*     */       } 
/*     */     }
/* 124 */     return sortColNo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSortAscending() {
/* 132 */     if (this.selectionOrderBy.indexOf(" DESC ") == -1) {
/* 133 */       return true;
/*     */     }
/* 135 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NotepadSortOrder getNotepadSortOrderFromSession(Context context) {
/* 144 */     NotepadSortOrder notepadSortOrder = (NotepadSortOrder)context.getSessionValue("notepadSortOrder");
/* 145 */     if (notepadSortOrder == null) {
/*     */       
/* 147 */       context.putSessionValue("notepadSortOrder", new NotepadSortOrder());
/* 148 */       notepadSortOrder = (NotepadSortOrder)context.getSessionValue("notepadSortOrder");
/*     */     } 
/* 150 */     return notepadSortOrder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean sortHelper(Dispatcher dispatcher, Context context, Notepad notepad) {
/* 161 */     User user = (User)context.getSessionValue("user");
/* 162 */     if (user != null) {
/* 163 */       user.SS_searchInitiated = true;
/*     */     }
/* 165 */     int userId = ((User)context.getSessionValue("user")).getUserId();
/*     */     
/* 167 */     String sort = "";
/* 168 */     int sortInd = 0;
/*     */ 
/*     */     
/* 171 */     String orderColLast = getSelectionOrderCol();
/*     */ 
/*     */     
/* 174 */     String orderCol = context.getParameter("OrderBy");
/*     */ 
/*     */     
/* 177 */     String orderBy = getSelectionOrderBy();
/*     */ 
/*     */     
/* 180 */     String alphaGroupChr = context.getParameter("alphaGroupChr");
/*     */     
/* 182 */     if (notepad.getSearchQuery() == null || notepad.getSearchQuery().equals("")) {
/* 183 */       notepad.setSearchQuery(SelectionManager.getInstance().getDefaultQuery(context));
/*     */     }
/*     */     
/* 186 */     if (orderCol.equalsIgnoreCase("Str Dt")) {
/*     */       
/* 188 */       sortInd = 5;
/* 189 */       if (orderCol.equalsIgnoreCase(orderColLast)) {
/*     */         
/* 191 */         if (orderBy.indexOf(" DESC ") == -1) {
/* 192 */           sort = String.valueOf(MilestoneHelper.NEW_SORT_SELECTION[5]) + " DESC " + ", artist,title,selection_no";
/*     */         } else {
/* 194 */           sort = String.valueOf(MilestoneHelper.NEW_SORT_SELECTION[5]) + ",artist,title,selection_no";
/*     */         }
/*     */       
/* 197 */       } else if (orderBy.indexOf(" DESC ") == -1) {
/* 198 */         sort = String.valueOf(MilestoneHelper.NEW_SORT_SELECTION[5]) + ",artist,title,selection_no";
/*     */       } else {
/* 200 */         sort = String.valueOf(MilestoneHelper.NEW_SORT_SELECTION[5]) + " DESC " + ",artist,title,selection_no";
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 205 */     if (orderCol.equalsIgnoreCase("Artist"))
/*     */     {
/*     */       
/* 208 */       if (orderCol.equalsIgnoreCase(orderColLast)) {
/*     */         
/* 210 */         if (orderBy.indexOf(" DESC ") == -1) {
/*     */           
/* 212 */           sort = String.valueOf(MilestoneHelper.NEW_SORT_SELECTION[0]) + " DESC " + ",title,selection_no,street_date";
/* 213 */           sortInd = 7;
/*     */         } else {
/*     */           
/* 216 */           sort = String.valueOf(MilestoneHelper.NEW_SORT_SELECTION[0]) + ",title,selection_no,street_date";
/* 217 */           sortInd = 0;
/*     */         }
/*     */       
/*     */       }
/* 221 */       else if (orderBy.indexOf(" DESC ") == -1) {
/*     */         
/* 223 */         sort = String.valueOf(MilestoneHelper.NEW_SORT_SELECTION[0]) + ",title,selection_no,street_date";
/* 224 */         sortInd = 0;
/*     */       }
/*     */       else {
/*     */         
/* 228 */         sort = String.valueOf(MilestoneHelper.NEW_SORT_SELECTION[0]) + " DESC " + ",title,selection_no,street_date";
/* 229 */         sortInd = 7;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 235 */     if (orderCol.equalsIgnoreCase("Title")) {
/*     */       
/* 237 */       sortInd = 1;
/* 238 */       if (orderCol.equalsIgnoreCase(orderColLast)) {
/*     */         
/* 240 */         if (orderBy.indexOf(" DESC ") == -1)
/*     */         {
/* 242 */           sort = String.valueOf(MilestoneHelper.NEW_SORT_SELECTION[1]) + " DESC " + ",artist,selection_no,street_date";
/* 243 */           sortInd = 8;
/*     */         }
/*     */         else
/*     */         {
/* 247 */           sort = String.valueOf(MilestoneHelper.NEW_SORT_SELECTION[1]) + ",artist,selection_no,street_date";
/* 248 */           sortInd = 1;
/*     */         }
/*     */       
/* 251 */       } else if (orderBy.indexOf(" DESC ") == -1) {
/*     */         
/* 253 */         sort = String.valueOf(MilestoneHelper.NEW_SORT_SELECTION[1]) + ",artist,selection_no,street_date";
/* 254 */         sortInd = 1;
/*     */       }
/*     */       else {
/*     */         
/* 258 */         sort = String.valueOf(MilestoneHelper.NEW_SORT_SELECTION[1]) + " DESC " + ",artist,selection_no,street_date";
/* 259 */         sortInd = 8;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 265 */     if (orderCol.equalsIgnoreCase("Local Prod #")) {
/*     */       
/* 267 */       sortInd = 2;
/* 268 */       if (orderCol.equalsIgnoreCase(orderColLast)) {
/*     */         
/* 270 */         if (orderBy.indexOf(" DESC ") == -1) {
/* 271 */           sort = String.valueOf(MilestoneHelper.NEW_SORT_SELECTION[2]) + " DESC ";
/*     */         } else {
/* 273 */           sort = MilestoneHelper.NEW_SORT_SELECTION[2];
/*     */         } 
/* 275 */       } else if (orderBy.indexOf(" DESC ") == -1) {
/* 276 */         sort = MilestoneHelper.NEW_SORT_SELECTION[2];
/*     */       } else {
/* 278 */         sort = String.valueOf(MilestoneHelper.NEW_SORT_SELECTION[2]) + " DESC ";
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 283 */     if (orderCol.equalsIgnoreCase("UPC")) {
/*     */       
/* 285 */       sortInd = 3;
/* 286 */       if (orderCol.equalsIgnoreCase(orderColLast)) {
/*     */         
/* 288 */         if (orderBy.indexOf(" DESC ") == -1) {
/* 289 */           sort = String.valueOf(MilestoneHelper.NEW_SORT_SELECTION[3]) + " DESC " + ",selection_no";
/*     */         } else {
/* 291 */           sort = String.valueOf(MilestoneHelper.NEW_SORT_SELECTION[3]) + ",selection_no";
/*     */         } 
/* 293 */       } else if (orderBy.indexOf(" DESC ") == -1) {
/* 294 */         sort = String.valueOf(MilestoneHelper.NEW_SORT_SELECTION[3]) + ",selection_no";
/*     */       } else {
/* 296 */         sort = String.valueOf(MilestoneHelper.NEW_SORT_SELECTION[3]) + " DESC " + ",selection_no";
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 301 */     if (orderCol.equalsIgnoreCase("Prefix")) {
/*     */       
/* 303 */       sortInd = 4;
/* 304 */       if (orderCol.equalsIgnoreCase(orderColLast)) {
/*     */         
/* 306 */         if (orderBy.indexOf(" DESC ") == -1) {
/* 307 */           sort = String.valueOf(MilestoneHelper.NEW_SORT_SELECTION[4]) + " DESC " + ",selection_no";
/*     */         } else {
/* 309 */           sort = String.valueOf(MilestoneHelper.NEW_SORT_SELECTION[4]) + ",selection_no";
/*     */         } 
/* 311 */       } else if (orderBy.indexOf(" DESC ") == -1) {
/* 312 */         sort = String.valueOf(MilestoneHelper.NEW_SORT_SELECTION[4]) + ",selection_no";
/*     */       } else {
/* 314 */         sort = String.valueOf(MilestoneHelper.NEW_SORT_SELECTION[4]) + " DESC " + ",selection_no";
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 319 */     if (orderCol.equalsIgnoreCase("Artist") || orderCol.equalsIgnoreCase("Title")) {
/* 320 */       setShowGroupButtons(true);
/*     */     } else {
/* 322 */       setShowGroupButtons(false);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 328 */     setSelectionOrderBy(sort);
/* 329 */     setSelectionOrderCol(orderCol);
/* 330 */     setSelectionOrderColNo(sortInd);
/*     */ 
/*     */     
/* 333 */     notepad.setOrderBy(" ORDER BY " + sort);
/*     */ 
/*     */     
/* 336 */     if (alphaGroupChr != null && alphaGroupChr.length() > 0) {
/* 337 */       if (orderCol.equalsIgnoreCase("Artist")) {
/*     */         
/* 339 */         notepad.setOrderBy(" AND left(artist,1) >= '" + alphaGroupChr + "'" + " ORDER BY " + sort);
/*     */       } else {
/* 341 */         notepad.setOrderBy(" AND left(title,1) >= '" + alphaGroupChr + "'" + " ORDER BY " + sort);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 346 */     notepad.setAllContents(null);
/* 347 */     notepad = SelectionManager.getInstance().getSelectionNotepad(context, userId, 0);
/*     */     
/* 349 */     if (alphaGroupChr != null && alphaGroupChr.length() > 0) {
/* 350 */       notepad.setSortOrder(sortInd);
/* 351 */       notepad.setSelected(null);
/*     */     } 
/*     */     
/* 354 */     notepad.goToSelectedPage();
/*     */     
/* 356 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\NotepadSortOrder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */