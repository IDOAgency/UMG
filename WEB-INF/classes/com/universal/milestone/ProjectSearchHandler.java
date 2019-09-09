/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.gemini.Context;
/*     */ import com.techempower.gemini.Dispatcher;
/*     */ import com.techempower.gemini.FormDropDownMenu;
/*     */ import com.techempower.gemini.FormHidden;
/*     */ import com.techempower.gemini.FormRadioButtonGroup;
/*     */ import com.techempower.gemini.FormTextField;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.Form;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.Notepad;
/*     */ import com.universal.milestone.ProjectSearchHandler;
/*     */ import com.universal.milestone.ProjectSearchManager;
/*     */ import com.universal.milestone.SecureHandler;
/*     */ import com.universal.milestone.Selection;
/*     */ import com.universal.milestone.SelectionManager;
/*     */ import com.universal.milestone.User;
/*     */ import java.util.Vector;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProjectSearchHandler
/*     */   extends SecureHandler
/*     */ {
/*     */   public static final String COMPONENT_CODE = "hSel";
/*     */   public GeminiApplication application;
/*     */   public ComponentLog log;
/*     */   
/*     */   public ProjectSearchHandler(GeminiApplication application) {
/*  81 */     this.application = application;
/*  82 */     this.log = application.getLog("hSel");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   public String getDescription() { return "ProjectSearch"; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean acceptRequest(Dispatcher dispatcher, Context context, String command) {
/* 100 */     if (super.acceptRequest(dispatcher, context, command))
/*     */     {
/* 102 */       if (command.startsWith("project-search"))
/*     */       {
/* 104 */         return handleRequest(dispatcher, context, command);
/*     */       }
/*     */     }
/* 107 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean handleRequest(Dispatcher dispatcher, Context context, String command) {
/* 119 */     if (command.equalsIgnoreCase("project-search")) {
/*     */       
/* 121 */       context.putSessionValue("selectionScreenType", "new");
/* 122 */       projectSearchEdit(dispatcher, context, command);
/*     */     }
/* 124 */     else if (command.equalsIgnoreCase("project-search-revise")) {
/*     */       
/* 126 */       context.putSessionValue("selectionScreenType", "revise");
/* 127 */       projectSearchEdit(dispatcher, context, command);
/*     */     }
/* 129 */     else if (command.equalsIgnoreCase("project-search-results")) {
/*     */       
/* 131 */       projectSearchResults(dispatcher, context, command);
/*     */     }
/* 133 */     else if (command.equalsIgnoreCase("project-search-results-order")) {
/*     */       
/* 135 */       sortProjectSearchResults(dispatcher, context, command);
/*     */     }
/* 137 */     else if (command.equalsIgnoreCase("project-search-cancel")) {
/*     */       
/* 139 */       projectCancel(dispatcher, context, command);
/*     */     }
/* 141 */     else if (command.equalsIgnoreCase("project-search-clear")) {
/*     */       
/* 143 */       projectClear(dispatcher, context, command);
/*     */     }
/* 145 */     else if (command.equalsIgnoreCase("project-search-goto-selection")) {
/*     */       
/* 147 */       projectGotoSelection(dispatcher, context, command);
/*     */     } 
/* 149 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean search(Dispatcher dispatcher, Context context, String command) {
/* 160 */     Notepad notepad = MilestoneHelper.getNotepadFromSession(0, context);
/*     */ 
/*     */     
/* 163 */     notepad.setAllContents(null);
/* 164 */     notepad.setSelected(null);
/*     */     
/* 166 */     SelectionManager.getInstance().setSelectionNotepadQuery(context, ((User)context.getSessionValue("user")).getUserId(), notepad, null);
/*     */     
/* 168 */     if (command.equals("selection-search")) {
/*     */       
/* 170 */       dispatcher.redispatch(context, "selection-editor");
/*     */     }
/*     */     else {
/*     */       
/* 174 */       dispatcher.redispatch(context, "selection-manufacturing-editor");
/*     */     } 
/* 176 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean projectSearchEdit(Dispatcher dispatcher, Context context, String command) {
/* 186 */     User user = (User)context.getSessionValue("user");
/*     */ 
/*     */     
/* 189 */     context.removeSessionValue("searchResults");
/*     */ 
/*     */     
/* 192 */     Form form = null;
/* 193 */     form = buildNewForm(context, command);
/*     */     
/* 195 */     context.putDelivery("Form", form);
/*     */     
/* 197 */     String sortColumn = "";
/* 198 */     String sortDirection = "";
/* 199 */     if (context.getParameter("sortColumn") != null) {
/* 200 */       sortColumn = context.getParameter("sortColumn");
/*     */     }
/* 202 */     if (context.getParameter("sortDirection") != null) {
/* 203 */       sortDirection = context.getParameter("sortDirection");
/*     */     }
/*     */     
/* 206 */     context.putSessionValue("sortingColumn", sortColumn);
/* 207 */     context.putSessionValue("sortingDirection", sortDirection);
/*     */     
/* 209 */     return context.includeJSP("project-search.jsp");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean projectGotoSelection(Dispatcher dispatcher, Context context, String command) {
/* 217 */     Selection selection = new Selection();
/* 218 */     User user = (User)context.getSessionValue("user");
/*     */     
/* 220 */     String counter = context.getParameter("selectedCounter");
/* 221 */     context.putSessionValue("selectionScreenTypeIndex", counter);
/*     */ 
/*     */     
/* 224 */     if (context.getParameter("isPhysical").equals("Digital")) {
/* 225 */       return dispatcher.redispatch(context, "selection-edit-new-digital-archie-project");
/*     */     }
/* 227 */     return dispatcher.redispatch(context, "selection-edit-new-physical-archie-project");
/*     */   }
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
/*     */   private boolean projectCancel(Dispatcher dispatcher, Context context, String command) {
/* 241 */     context.removeSessionValue("searchResults");
/* 242 */     context.removeSessionValue("selectionScreenType");
/*     */ 
/*     */     
/* 245 */     dispatcher.redispatch(context, "selection-editor");
/*     */     
/* 247 */     return true;
/*     */   }
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
/* 262 */   private boolean projectClear(Dispatcher dispatcher, Context context, String command) { return projectSearchEdit(dispatcher, context, command); }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean sortProjectSearchResults(Dispatcher dispatcher, Context context, String command) {
/* 267 */     User user = (User)context.getSessionValue("user");
/*     */ 
/*     */ 
/*     */     
/* 271 */     Vector searchResults = new Vector();
/* 272 */     if (context.getSessionValue("searchResults") != null) {
/* 273 */       searchResults = (Vector)context.getSessionValue("searchResults");
/*     */     }
/* 275 */     Vector archieProjects = ProjectSearchManager.getInstance().sortResults(context, user, searchResults, false);
/*     */ 
/*     */ 
/*     */     
/* 279 */     Form form = null;
/* 280 */     form = buildForm(context, command);
/* 281 */     context.putDelivery("Form", form);
/*     */     
/* 283 */     String sortColumn = "";
/* 284 */     String sortDirection = "";
/* 285 */     if (context.getParameter("sortColumn") != null) {
/* 286 */       sortColumn = context.getParameter("sortColumn");
/*     */     }
/* 288 */     if (context.getParameter("sortDirection") != null) {
/* 289 */       sortDirection = context.getParameter("sortDirection");
/*     */     }
/* 291 */     context.putSessionValue("sortingColumn", sortColumn);
/* 292 */     context.putSessionValue("sortingDirection", sortDirection);
/* 293 */     context.putSessionValue("searchResults", archieProjects);
/*     */     
/* 295 */     return context.includeJSP("project-search.jsp");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean projectSearchResults(Dispatcher dispatcher, Context context, String command) {
/* 304 */     User user = (User)context.getSessionValue("user");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 310 */       Vector archieProjects = ProjectSearchManager.getInstance().getArchieProjects(context, user);
/*     */ 
/*     */ 
/*     */       
/* 314 */       Form form = null;
/* 315 */       form = buildForm(context, command);
/* 316 */       context.putDelivery("Form", form);
/*     */       
/* 318 */       String sortColumn = "";
/* 319 */       String sortDirection = "";
/*     */       
/* 321 */       if (context.getParameter("sortColumn") != null) {
/* 322 */         sortColumn = context.getParameter("sortColumn");
/*     */       }
/* 324 */       if (context.getParameter("sortDirection") != null) {
/* 325 */         sortDirection = context.getParameter("sortDirection");
/*     */       }
/*     */       
/* 328 */       context.putSessionValue("sortingColumn", sortColumn);
/* 329 */       context.putSessionValue("sortingDirection", sortDirection);
/*     */       
/* 331 */       context.putSessionValue("searchResults", archieProjects);
/* 332 */       return context.includeJSP("project-search.jsp");
/*     */     }
/* 334 */     catch (Exception exception) {
/* 335 */       System.out.println("ATLAS WEBSERVICE ERROR: " + exception);
/* 336 */       context.putSessionValue("atlasFailureException", exception);
/* 337 */       return context.includeJSP("project-search-results.jsp");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Form buildForm(Context context, String command) {
/* 348 */     Form projectSearchForm = new Form(this.application, "projectSearchForm", 
/* 349 */         this.application.getInfrastructure().getServletURL(), "POST");
/* 350 */     User user = (User)context.getSession().getAttribute("user");
/* 351 */     int userId = user.getUserId();
/*     */ 
/*     */     
/* 354 */     projectSearchForm.addElement(new FormHidden("cmd", command, true));
/*     */     
/* 356 */     Vector companies = null;
/* 357 */     companies = MilestoneHelper.getUserCompanies(context);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 366 */     String radioSelected = "Physical";
/* 367 */     if (context.getParameter("isPhysical").equals("Digital")) {
/* 368 */       radioSelected = "Digital";
/*     */     }
/*     */ 
/*     */     
/* 372 */     FormRadioButtonGroup isPhysical = new FormRadioButtonGroup("isPhysical", radioSelected, "Physical, Digital", false);
/* 373 */     isPhysical.setTabIndex(1);
/*     */ 
/*     */ 
/*     */     
/* 377 */     projectSearchForm.addElement(isPhysical);
/*     */ 
/*     */ 
/*     */     
/* 381 */     FormTextField artistFirstName = new FormTextField("artistFirstName2", context.getParameter("artistFirstName2"), true, 20, 50);
/* 382 */     artistFirstName.setTabIndex(2);
/* 383 */     artistFirstName.setClassName("ctrlMedium");
/* 384 */     projectSearchForm.addElement(artistFirstName);
/*     */ 
/*     */     
/* 387 */     FormTextField artistLastName = new FormTextField("artistLastName", context.getParameter("artistLastName"), true, 20, 50);
/* 388 */     artistLastName.setTabIndex(3);
/* 389 */     artistLastName.setClassName("ctrlMedium");
/* 390 */     projectSearchForm.addElement(artistLastName);
/*     */ 
/*     */     
/* 393 */     FormTextField projectTitle = new FormTextField("title", context.getParameter("title"), true, 73, 125);
/* 394 */     projectTitle.setTabIndex(4);
/* 395 */     projectTitle.setClassName("ctrlMedium");
/* 396 */     projectSearchForm.addElement(projectTitle);
/*     */ 
/*     */     
/* 399 */     FormTextField projectDesc = new FormTextField("projectDesc", context.getParameter("projectDesc"), true, 73, 125);
/* 400 */     projectDesc.setTabIndex(5);
/* 401 */     projectDesc.setClassName("ctrlMedium");
/* 402 */     projectSearchForm.addElement(projectDesc);
/*     */ 
/*     */     
/* 405 */     FormTextField projectID = new FormTextField("projectID", context.getParameter("projectID"), true, 73, 125);
/* 406 */     projectID.setTabIndex(7);
/* 407 */     projectID.setClassName("ctrlMedium");
/* 408 */     projectSearchForm.addElement(projectID);
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
/* 419 */     Vector userCompanies = null;
/* 420 */     userCompanies = MilestoneHelper.getUserCompanies(context);
/* 421 */     Vector userEditableCompanies = ProjectSearchManager.getInstance().filterSelectionCompaniesWithEditRights(userCompanies, user);
/* 422 */     Vector userEditableLabels = MilestoneHelper.getUserLabels(userEditableCompanies);
/* 423 */     FormDropDownMenu labels = MilestoneHelper.getCorporateStructureDropDownDuplicates("labels", userEditableLabels, context.getParameter("labels"), false, true, true);
/* 424 */     labels.addFormEvent("onKeyPress", "scanKey(window.event.keyCode, 'document.all.labels.options', getMaxLength(document.all.labels.options))");
/* 425 */     labels.setTabIndex(6);
/* 426 */     projectSearchForm.addElement(labels);
/*     */ 
/*     */     
/* 429 */     return projectSearchForm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Form buildNewForm(Context context, String command) {
/* 439 */     Form projectSearchForm = new Form(this.application, "projectSearchForm", 
/* 440 */         this.application.getInfrastructure().getServletURL(), "POST");
/* 441 */     User user = (User)context.getSession().getAttribute("user");
/* 442 */     int userId = user.getUserId();
/*     */ 
/*     */     
/* 445 */     projectSearchForm.addElement(new FormHidden("cmd", command, true));
/*     */     
/* 447 */     Vector companies = null;
/* 448 */     companies = MilestoneHelper.getUserCompanies(context);
/*     */ 
/*     */ 
/*     */     
/* 452 */     for (int c = 0; c < companies.size(); c++) {
/* 453 */       System.out.println("companies:[" + companies.elementAt(c) + "]");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 458 */     FormRadioButtonGroup isPhysical = new FormRadioButtonGroup("isPhysical", "", "Physical, Digital", false);
/* 459 */     isPhysical.setTabIndex(1);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 464 */     projectSearchForm.addElement(isPhysical);
/*     */ 
/*     */     
/* 467 */     FormTextField artistFirstName = new FormTextField("artistFirstName2", "", true, 20, 50);
/* 468 */     artistFirstName.setTabIndex(2);
/* 469 */     artistFirstName.setClassName("ctrlMedium");
/* 470 */     projectSearchForm.addElement(artistFirstName);
/*     */ 
/*     */     
/* 473 */     FormTextField artistLastName = new FormTextField("artistLastName", "", true, 20, 50);
/* 474 */     artistLastName.setTabIndex(3);
/* 475 */     artistLastName.setClassName("ctrlMedium");
/* 476 */     projectSearchForm.addElement(artistLastName);
/*     */ 
/*     */     
/* 479 */     FormTextField projectTitle = new FormTextField("title", "", true, 73, 125);
/* 480 */     projectTitle.setTabIndex(4);
/* 481 */     projectTitle.setClassName("ctrlMedium");
/* 482 */     projectSearchForm.addElement(projectTitle);
/*     */ 
/*     */     
/* 485 */     FormTextField projectDesc = new FormTextField("projectDesc", "", true, 73, 125);
/* 486 */     projectDesc.setTabIndex(5);
/* 487 */     projectDesc.setClassName("ctrlMedium");
/* 488 */     projectSearchForm.addElement(projectDesc);
/*     */ 
/*     */     
/* 491 */     FormTextField projectID = new FormTextField("projectID", "", true, 73, 125);
/* 492 */     projectID.setTabIndex(7);
/* 493 */     projectID.setClassName("ctrlMedium");
/* 494 */     projectSearchForm.addElement(projectID);
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
/* 506 */     Vector userCompanies = null;
/* 507 */     userCompanies = MilestoneHelper.getUserCompanies(context);
/* 508 */     Vector userEditableCompanies = ProjectSearchManager.getInstance().filterSelectionCompaniesWithEditRights(userCompanies, user);
/* 509 */     Vector userEditableLabels = MilestoneHelper.getUserLabels(userEditableCompanies);
/* 510 */     FormDropDownMenu labels = MilestoneHelper.getCorporateStructureDropDownDuplicates("labels", userEditableLabels, "", false, true, true);
/* 511 */     labels.addFormEvent("onKeyPress", "scanKey(window.event.keyCode, 'document.all.labels.options', getMaxLength(document.all.labels.options))");
/* 512 */     labels.setTabIndex(6);
/* 513 */     projectSearchForm.addElement(labels);
/*     */ 
/*     */     
/* 516 */     return projectSearchForm;
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\ProjectSearchHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */