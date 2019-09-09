/*      */ package WEB-INF.classes.com.universal.milestone;
/*      */ 
/*      */ import com.techempower.ComponentLog;
/*      */ import com.techempower.gemini.Context;
/*      */ import com.techempower.gemini.Dispatcher;
/*      */ import com.techempower.gemini.FormCheckBox;
/*      */ import com.techempower.gemini.FormDropDownMenu;
/*      */ import com.techempower.gemini.FormElement;
/*      */ import com.techempower.gemini.FormHidden;
/*      */ import com.techempower.gemini.FormRadioButtonGroup;
/*      */ import com.techempower.gemini.FormTextField;
/*      */ import com.techempower.gemini.FormValidation;
/*      */ import com.techempower.gemini.GeminiApplication;
/*      */ import com.universal.milestone.Cache;
/*      */ import com.universal.milestone.Company;
/*      */ import com.universal.milestone.CompanyAcl;
/*      */ import com.universal.milestone.Environment;
/*      */ import com.universal.milestone.Family;
/*      */ import com.universal.milestone.Form;
/*      */ import com.universal.milestone.MilestoneConstants;
/*      */ import com.universal.milestone.MilestoneHelper;
/*      */ import com.universal.milestone.MilestoneMessage;
/*      */ import com.universal.milestone.MilestoneSecurity;
/*      */ import com.universal.milestone.Notepad;
/*      */ import com.universal.milestone.NotepadSortOrder;
/*      */ import com.universal.milestone.ProductCategory;
/*      */ import com.universal.milestone.ReleaseType;
/*      */ import com.universal.milestone.SecureHandler;
/*      */ import com.universal.milestone.SelectionConfiguration;
/*      */ import com.universal.milestone.SelectionManager;
/*      */ import com.universal.milestone.SelectionSubConfiguration;
/*      */ import com.universal.milestone.Task;
/*      */ import com.universal.milestone.TaskAbbrComparator;
/*      */ import com.universal.milestone.TaskNameComparator;
/*      */ import com.universal.milestone.TaskOwnerComparator;
/*      */ import com.universal.milestone.TaskWksToReleaseComparator;
/*      */ import com.universal.milestone.Template;
/*      */ import com.universal.milestone.TemplateHandler;
/*      */ import com.universal.milestone.TemplateManager;
/*      */ import com.universal.milestone.User;
/*      */ import com.universal.milestone.UserManager;
/*      */ import java.util.Arrays;
/*      */ import java.util.Calendar;
/*      */ import java.util.Vector;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class TemplateHandler
/*      */   extends SecureHandler
/*      */ {
/*      */   public static final String COMPONENT_CODE = "hTemp";
/*      */   public GeminiApplication application;
/*      */   public ComponentLog log;
/*      */   
/*      */   public TemplateHandler(GeminiApplication application) {
/*   57 */     this.application = application;
/*   58 */     this.log = application.getLog("hTemp");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TemplateHandler() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   74 */   public String getDescription() { return "Template"; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean acceptRequest(Dispatcher dispatcher, Context context, String command) {
/*   84 */     if (super.acceptRequest(dispatcher, context, command))
/*      */     {
/*   86 */       if (command.startsWith("template"))
/*      */       {
/*   88 */         return handleRequest(dispatcher, context, command);
/*      */       }
/*      */     }
/*      */     
/*   92 */     return false;
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
/*      */   public boolean handleRequest(Dispatcher dispatcher, Context context, String command) {
/*  104 */     if (command.equalsIgnoreCase("template-editor"))
/*      */     {
/*  106 */       edit(dispatcher, context, command);
/*      */     }
/*  108 */     if (command.equalsIgnoreCase("template-search"))
/*      */     {
/*  110 */       search(dispatcher, context, command);
/*      */     }
/*  112 */     if (command.equalsIgnoreCase("template-task-search"))
/*      */     {
/*  114 */       templateTaskSearch(dispatcher, context, command);
/*      */     }
/*  116 */     if (command.equalsIgnoreCase("template-copy-sort"))
/*      */     {
/*  118 */       sort(dispatcher, context);
/*      */     }
/*  120 */     if (command.equalsIgnoreCase("template-notepad-tasks-sort"))
/*      */     {
/*  122 */       sortTaskNotepad(dispatcher, context);
/*      */     }
/*  124 */     if (command.equalsIgnoreCase("template-sort-tasks"))
/*      */     {
/*  126 */       sortTasks(dispatcher, context);
/*      */     }
/*  128 */     if (command.equalsIgnoreCase("template-sort-task-tasks")) {
/*      */       
/*  130 */       sortTaskTasks(dispatcher, context);
/*      */     }
/*  132 */     else if (command.equalsIgnoreCase("template-locator")) {
/*      */       
/*  134 */       locator(dispatcher, context, command);
/*      */     }
/*  136 */     else if (command.equalsIgnoreCase("template-locator-copy")) {
/*      */       
/*  138 */       locatorCopy(dispatcher, context, command);
/*      */     }
/*  140 */     else if (command.equalsIgnoreCase("template-assign-to-schedule")) {
/*      */       
/*  142 */       assignToSchedule(dispatcher, context, command);
/*      */     }
/*  144 */     else if (command.equalsIgnoreCase("template-edit-new")) {
/*      */       
/*  146 */       editNew(dispatcher, context, command);
/*      */     }
/*  148 */     else if (command.equalsIgnoreCase("template-edit-save-new")) {
/*      */       
/*  150 */       editSaveNew(dispatcher, context, command);
/*      */     }
/*  152 */     else if (command.equalsIgnoreCase("template-edit-save") || command.equalsIgnoreCase("template-copy-save")) {
/*      */       
/*  154 */       editSave(dispatcher, context, command);
/*      */     }
/*  156 */     else if (command.equalsIgnoreCase("template-edit-delete")) {
/*      */       
/*  158 */       editDelete(dispatcher, context, command);
/*      */     }
/*  160 */     else if (command.equalsIgnoreCase("template-edit-copy")) {
/*      */       
/*  162 */       editCopy(dispatcher, context, command);
/*      */     }
/*  164 */     else if (command.equalsIgnoreCase("template-edit-assign-task")) {
/*      */       
/*  166 */       editAssignTask(dispatcher, context, command);
/*      */     }
/*  168 */     else if (command.equalsIgnoreCase("template-edit-delete-task")) {
/*      */       
/*  170 */       editDeleteTask(dispatcher, context, command);
/*      */     }
/*  172 */     else if (command.equalsIgnoreCase("template-recommended")) {
/*      */       
/*  174 */       recommended(dispatcher, context, command);
/*      */     }
/*  176 */     else if (command.equalsIgnoreCase("template-task-editor")) {
/*      */       
/*  178 */       editTemplateTasks(dispatcher, context, command);
/*      */     }
/*  180 */     else if (command.equalsIgnoreCase("template-task-edit-save") || command.equalsIgnoreCase("template-task-copy-save") || command.equalsIgnoreCase("template-copy-save")) {
/*      */       
/*  182 */       editTemplateTaskSave(dispatcher, context, command);
/*      */     }
/*  184 */     else if (command.equalsIgnoreCase("template-add-task")) {
/*      */       
/*  186 */       addTask(dispatcher, context, command);
/*      */     }
/*  188 */     else if (command.equalsIgnoreCase("template-task-edit-new")) {
/*      */       
/*  190 */       editTemplateTaskNew(dispatcher, context, command);
/*      */     
/*      */     }
/*  193 */     else if (command.equalsIgnoreCase("template-task-edit-save-new")) {
/*      */       
/*  195 */       editTemplateTaskSaveNew(dispatcher, context, command);
/*      */     }
/*  197 */     else if (command.equalsIgnoreCase("template-task-edit-delete")) {
/*      */       
/*  199 */       editTemplateTaskDelete(dispatcher, context, command);
/*      */     }
/*  201 */     else if (command.equalsIgnoreCase("template-task-edit-copy")) {
/*      */       
/*  203 */       editTemplateTaskCopy(dispatcher, context, command);
/*      */     }
/*  205 */     else if (command.equalsIgnoreCase("template-task-edit-delete-task")) {
/*      */       
/*  207 */       editTemplateTaskDeleteTask(dispatcher, context, command);
/*      */     } 
/*      */     
/*  210 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean search(Dispatcher dispatcher, Context context, String command) {
/*  220 */     Notepad notepad = MilestoneHelper.getNotepadFromSession(5, context);
/*      */ 
/*      */     
/*  223 */     notepad.setAllContents(null);
/*  224 */     notepad.setSelected(null);
/*      */     
/*  226 */     TemplateManager.getInstance().setNotepadQuery(context, MilestoneSecurity.getUser(context).getUserId(), notepad);
/*  227 */     dispatcher.redispatch(context, "template-editor");
/*      */     
/*  229 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean templateTaskSearch(Dispatcher dispatcher, Context context, String command) {
/*  239 */     Notepad notepad = MilestoneHelper.getNotepadFromSession(19, context);
/*      */ 
/*      */     
/*  242 */     notepad.setAllContents(null);
/*  243 */     notepad.setSelected(null);
/*      */     
/*  245 */     TemplateManager.getInstance().setTaskNotepadQuery(context, notepad);
/*  246 */     dispatcher.redispatch(context, "template-task-editor");
/*      */     
/*  248 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean sort(Dispatcher dispatcher, Context context) {
/*  259 */     int userId = MilestoneSecurity.getUser(context).getUserId();
/*      */     
/*  261 */     Notepad notepad = getTemplateNotepad(context, userId);
/*      */     
/*  263 */     if (notepad.getSearchQuery() == null || notepad.getSearchQuery().equals("")) {
/*  264 */       notepad.setSearchQuery(TemplateManager.getInstance().getDefaultSearchQuery(context));
/*      */     }
/*      */ 
/*      */     
/*  268 */     NotepadSortOrder.getNotepadSortOrderFromSession(context).getNotepadSortOrderTemplate().sortHelper(dispatcher, context, notepad);
/*      */ 
/*      */     
/*  271 */     notepad.setAllContents(null);
/*  272 */     notepad = getTemplateNotepad(context, userId);
/*  273 */     notepad.goToSelectedPage();
/*      */     
/*  275 */     dispatcher.redispatch(context, "template-editor");
/*      */     
/*  277 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean sortTaskNotepad(Dispatcher dispatcher, Context context) {
/*  286 */     int sort = Integer.parseInt(context.getParameter("OrderBy"));
/*  287 */     int userId = MilestoneSecurity.getUser(context).getUserId();
/*      */     
/*  289 */     Template template = MilestoneHelper.getScreenTemplate(context);
/*  290 */     Notepad notepad = getTaskNotepad(context, userId, template.getTemplateID());
/*      */     
/*  292 */     if (notepad.getSearchQuery() == null || notepad.getSearchQuery().equals("")) {
/*  293 */       notepad.setSearchQuery(TemplateManager.getInstance().getDefaultTaskSearchQuery(template.getTemplateID()));
/*      */     }
/*  295 */     notepad.setOrderBy(" ORDER BY " + MilestoneConstants.SORT_TASK[sort]);
/*      */ 
/*      */     
/*  298 */     notepad.setAllContents(null);
/*  299 */     notepad = getTaskNotepad(context, userId, template.getTemplateID());
/*  300 */     notepad.goToSelectedPage();
/*      */     
/*  302 */     dispatcher.redispatch(context, "template-task-editor");
/*      */     
/*  304 */     return true;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean sortTasks(Dispatcher dispatcher, Context context) {
/*  331 */     int sort = Integer.parseInt(context.getParameter("OrderTasksBy"));
/*  332 */     context.putDelivery("TemplateTaskSort", Integer.toString(sort));
/*  333 */     context.putSessionValue("TemplateTaskSort", Integer.toString(sort));
/*  334 */     dispatcher.redispatch(context, "template-editor");
/*  335 */     return true;
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
/*      */   private boolean sortTaskTasks(Dispatcher dispatcher, Context context) {
/*  347 */     int sort = Integer.parseInt(context.getParameter("OrderTasksBy"));
/*  348 */     context.putDelivery("TemplateTaskSort", Integer.toString(sort));
/*  349 */     context.putSessionValue("TemplateTaskSort", Integer.toString(sort));
/*  350 */     dispatcher.redispatch(context, "template-task-editor");
/*      */     
/*  352 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Notepad getTemplateNotepad(Context context, int userId) {
/*  362 */     Vector contents = new Vector();
/*      */     
/*  364 */     if (MilestoneHelper.getNotepadFromSession(5, context) != null) {
/*      */       
/*  366 */       Notepad notepad = MilestoneHelper.getNotepadFromSession(5, context);
/*      */       
/*  368 */       if (notepad.getAllContents() == null) {
/*      */         
/*  370 */         contents = TemplateManager.getInstance().getTemplateNotepadList(context, notepad);
/*  371 */         notepad.setAllContents(contents);
/*      */       } 
/*      */       
/*  374 */       return notepad;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  379 */     String[] columnNames = { "Template", "Format", "Owner" };
/*  380 */     contents = TemplateManager.getInstance().getTemplateNotepadList(context, null);
/*  381 */     return new Notepad(contents, 0, 7, "Templates", 5, columnNames);
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
/*      */   private boolean edit(Dispatcher dispatcher, Context context, String command) {
/*  393 */     User user = MilestoneSecurity.getUser(context);
/*      */     
/*  395 */     Notepad notepad = getTemplateNotepad(context, user.getUserId());
/*  396 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/*  398 */     Template template = MilestoneHelper.getScreenTemplate(context);
/*      */     
/*  400 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/*  402 */     Form form = null;
/*  403 */     if (template != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  408 */       int secureLevel = getTemplatePermissions(template, user);
/*      */       
/*  410 */       if (secureLevel < 2)
/*      */       {
/*  412 */         notepad.setSwitchToTaskVisible(false);
/*      */       }
/*      */       
/*  415 */       if (context.getSessionValue("TemplateTaskSort") != null) {
/*  416 */         template.setTasks(sortScreenTaskVector(Integer.parseInt((String)context.getSessionValue("TemplateTaskSort")), template.getTasks()));
/*      */       }
/*  418 */       form = buildForm(context, template, command);
/*  419 */       form.addElement(new FormHidden("cmd", "template-editor"));
/*  420 */       context.putDelivery("Form", form);
/*  421 */       return context.includeJSP("template-editor.jsp");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  427 */     Vector contents = null;
/*  428 */     contents = notepad.getAllContents();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  433 */     notepad.setSwitchToTaskVisible(false);
/*      */     
/*  435 */     if (contents != null && contents.size() > 0) {
/*      */       
/*  437 */       form = buildNewForm(context, command);
/*  438 */       context.putDelivery("Form", form);
/*  439 */       return context.includeJSP("template-editor.jsp");
/*      */     } 
/*      */ 
/*      */     
/*  443 */     form = buildNewForm(context, command);
/*  444 */     context.putDelivery("Form", form);
/*      */     
/*  446 */     if (context.getSessionValue("NOTEPAD_TEMPLATES_VISIBLE") != null)
/*  447 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_TEMPLATES_VISIBLE")); 
/*  448 */     return context.includeJSP("blank-template-editor.jsp");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean editNew(Dispatcher dispatcher, Context context, String command) {
/*  459 */     User user = MilestoneSecurity.getUser(context);
/*      */     
/*  461 */     Notepad notepad = getTemplateNotepad(context, user.getUserId());
/*      */ 
/*      */     
/*  464 */     notepad.setSwitchToTaskVisible(false);
/*  465 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/*  467 */     Form form = buildNewForm(context, command);
/*  468 */     context.putDelivery("Form", form);
/*      */     
/*  470 */     return context.includeJSP("template-editor.jsp");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean editSaveNew(Dispatcher dispatcher, Context context, String command) {
/*  478 */     User user = MilestoneSecurity.getUser(context);
/*      */ 
/*      */     
/*  481 */     Notepad notepad = getTemplateNotepad(context, user.getUserId());
/*  482 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/*  484 */     Template template = new Template();
/*      */     
/*  486 */     Form form = buildNewForm(context, command);
/*  487 */     form.addElement(new FormHidden("cmd", "template-edit-save-new"));
/*  488 */     form.setValues(context);
/*      */ 
/*      */ 
/*      */     
/*  492 */     template.setTemplateID(-1);
/*      */ 
/*      */ 
/*      */     
/*  496 */     template.setTasks(null);
/*      */ 
/*      */     
/*  499 */     String name = form.getStringValue("templateName");
/*  500 */     template.setTemplateName(name);
/*      */ 
/*      */     
/*  503 */     String owner = form.getStringValue("owner");
/*  504 */     int ownerInt = 0;
/*  505 */     if (owner != null && !owner.equals("")) {
/*      */       
/*      */       try {
/*      */         
/*  509 */         ownerInt = Integer.parseInt(owner);
/*      */       }
/*  511 */       catch (NumberFormatException numberFormatException) {}
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  516 */     template.setOwner((Family)MilestoneHelper.getStructureObject(ownerInt));
/*      */ 
/*      */ 
/*      */     
/*  520 */     template.setProdType(Integer.parseInt(form.getStringValue("ProdType")));
/*      */ 
/*      */     
/*  523 */     String productLine = form.getStringValue("productLine");
/*  524 */     if (productLine.length() > 0) {
/*  525 */       template.setProductCategory((ProductCategory)SelectionManager.getLookupObject(productLine, Cache.getProductCategories()));
/*      */     }
/*      */     
/*  528 */     String configuration = form.getStringValue("Configuration");
/*  529 */     if (configuration.length() > 0) {
/*  530 */       template.setSelectionConfig(SelectionManager.getSelectionConfigObject(configuration, Cache.getSelectionConfigs()));
/*      */     }
/*      */     
/*  533 */     String subConfiguration = form.getStringValue("SubConfiguration");
/*  534 */     if (subConfiguration.length() > 0) {
/*  535 */       template.setSelectionSubConfig(SelectionManager.getSelectionSubConfigObject(subConfiguration, template.getSelectionConfig()));
/*      */     }
/*      */     
/*  538 */     String releaseType = form.getStringValue("releaseType");
/*  539 */     if (releaseType.length() > 0) {
/*  540 */       template.setReleaseType((ReleaseType)SelectionManager.getLookupObject(releaseType, Cache.getReleaseTypes()));
/*      */     }
/*      */     
/*  543 */     if (!TemplateManager.getInstance().isDuplicate(template)) {
/*      */       
/*  545 */       if (!form.isUnchanged())
/*      */       {
/*  547 */         FormValidation formValidation = form.validate();
/*  548 */         if (formValidation.isGood())
/*      */         {
/*  550 */           Template savedTemplate = TemplateManager.getInstance().saveTemplate(template, user.getUserId());
/*      */           
/*  552 */           if (context.getSessionValue("TemplateTaskSort") != null) {
/*  553 */             savedTemplate.setTasks(sortScreenTaskVector(Integer.parseInt((String)context.getSessionValue("TemplateTaskSort")), savedTemplate.getTasks()));
/*      */           }
/*  555 */           context.putDelivery("Form", form);
/*      */ 
/*      */ 
/*      */           
/*  559 */           notepad.setAllContents(null);
/*  560 */           notepad.newSelectedReset();
/*  561 */           notepad = getTemplateNotepad(context, user.getUserId());
/*  562 */           notepad.setSelected(savedTemplate);
/*      */           
/*  564 */           template = (Template)notepad.validateSelected();
/*      */           
/*  566 */           context.putSessionValue("Template", template);
/*      */         }
/*      */         else
/*      */         {
/*  570 */           context.putDelivery("FormValidation", formValidation);
/*  571 */           form.addElement(new FormHidden("OrderBy", "", true));
/*  572 */           context.putDelivery("Form", form);
/*      */         }
/*      */       
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  580 */       context.putDelivery("AlertMessage", 
/*  581 */           MilestoneMessage.getMessage(5, new String[] { "Template", template.getTempateName() }));
/*  582 */       return edit(dispatcher, context, command);
/*      */     } 
/*      */ 
/*      */     
/*  586 */     return edit(dispatcher, context, "template-editor");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean editSave(Dispatcher dispatcher, Context context, String command) {
/*  595 */     User user = MilestoneSecurity.getUser(context);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  600 */     Notepad notepad = getTemplateNotepad(context, user.getUserId());
/*  601 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */ 
/*      */     
/*  604 */     Template template = (Template)context.getSessionValue("Template");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  613 */     int secureLevel = getTemplatePermissions(template, user);
/*      */     
/*  615 */     if (secureLevel < 2)
/*      */     {
/*  617 */       notepad.setSwitchToTaskVisible(false);
/*      */     }
/*      */     
/*  620 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/*  622 */     Form form = buildForm(context, template, command);
/*  623 */     form.addElement(new FormHidden("cmd", "template-editor"));
/*      */ 
/*      */     
/*  626 */     if (TemplateManager.getInstance().isTimestampValid(template)) {
/*      */       
/*  628 */       form.setValues(context);
/*      */ 
/*      */       
/*  631 */       String name = form.getStringValue("templateName");
/*  632 */       template.setTemplateName(name);
/*      */ 
/*      */       
/*  635 */       String owner = form.getStringValue("owner");
/*  636 */       if (owner.length() > 0) {
/*  637 */         template.setOwner((Family)MilestoneHelper.getStructureObject(Integer.parseInt(owner)));
/*      */       }
/*      */       
/*  640 */       template.setProdType(Integer.parseInt(form.getStringValue("ProdType")));
/*      */ 
/*      */       
/*  643 */       String productLine = form.getStringValue("productLine");
/*  644 */       if (productLine.length() > 0) {
/*  645 */         template.setProductCategory((ProductCategory)SelectionManager.getLookupObject(productLine, Cache.getProductCategories()));
/*      */       }
/*      */       
/*  648 */       String configuration = form.getStringValue("Configuration");
/*  649 */       if (configuration.length() > 0) {
/*  650 */         template.setSelectionConfig(SelectionManager.getSelectionConfigObject(configuration, Cache.getSelectionConfigs()));
/*      */       }
/*      */ 
/*      */       
/*  654 */       String subConfiguration = form.getStringValue("SubConfiguration");
/*  655 */       if (subConfiguration.length() > 0) {
/*  656 */         template.setSelectionSubConfig(SelectionManager.getSelectionSubConfigObject(subConfiguration, template.getSelectionConfig()));
/*      */       }
/*      */       
/*  659 */       String releaseType = form.getStringValue("releaseType");
/*  660 */       if (releaseType.length() > 0) {
/*  661 */         template.setReleaseType((ReleaseType)SelectionManager.getLookupObject(releaseType, Cache.getReleaseTypes()));
/*      */       }
/*      */       
/*  664 */       Template savedTemplate = null;
/*      */ 
/*      */       
/*  667 */       if (!TemplateManager.getInstance().isDuplicate(template)) {
/*      */         
/*  669 */         if (!form.isUnchanged())
/*      */         {
/*  671 */           FormValidation formValidation = form.validate();
/*  672 */           if (formValidation.isGood())
/*      */           {
/*  674 */             if (command.equalsIgnoreCase("template-copy-save")) {
/*      */               
/*  676 */               savedTemplate = TemplateManager.getInstance().saveTemplateCopiedTemplate(template, user.getUserId());
/*      */             }
/*      */             else {
/*      */               
/*  680 */               savedTemplate = TemplateManager.getInstance().saveTemplate(template, user.getUserId());
/*      */             } 
/*      */ 
/*      */             
/*  684 */             FormElement lastUpdated = form.getElement("lastupdateddate");
/*      */             
/*  686 */             lastUpdated.setValue(MilestoneHelper.getLongDate(savedTemplate.getLastUpdateDate()));
/*  687 */             if (context.getSessionValue("TemplateTaskSort") != null) {
/*  688 */               savedTemplate.setTasks(sortScreenTaskVector(Integer.parseInt((String)context.getSessionValue("TemplateTaskSort")), savedTemplate.getTasks()));
/*      */             }
/*      */             
/*  691 */             notepad.setAllContents(null);
/*  692 */             notepad = getTemplateNotepad(context, user.getUserId());
/*  693 */             notepad.setSelected(savedTemplate);
/*  694 */             template = (Template)notepad.validateSelected();
/*      */             
/*  696 */             context.putSessionValue("Template", template);
/*      */             
/*  698 */             if (template == null) {
/*      */               
/*  700 */               form = buildNewForm(context, command);
/*  701 */               context.putDelivery("Form", form);
/*      */               
/*  703 */               if (context.getSessionValue("NOTEPAD_TEMPLATES_VISIBLE") != null)
/*  704 */                 context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_TEMPLATES_VISIBLE")); 
/*  705 */               return context.includeJSP("blank-template-editor.jsp");
/*      */             } 
/*      */ 
/*      */             
/*  709 */             if (template == savedTemplate)
/*      */             {
/*  711 */               form = buildForm(context, template, command);
/*  712 */               form.addElement(new FormHidden("cmd", "template-editor"));
/*      */             }
/*      */             else
/*      */             {
/*  716 */               edit(dispatcher, context, command);
/*  717 */               return true;
/*      */             }
/*      */           
/*      */           }
/*      */           else
/*      */           {
/*  723 */             context.putDelivery("FormValidation", formValidation);
/*      */           }
/*      */         
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/*  730 */         context.putDelivery("AlertMessage", 
/*  731 */             MilestoneMessage.getMessage(5, new String[] { "Template", template.getTempateName() }));
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  736 */       context.putDelivery("AlertMessage", "The record is unavailable for editing at this time.  Please refresh your screen and edit it at a later time.");
/*      */     } 
/*      */     
/*  739 */     context.putDelivery("Form", form);
/*  740 */     return context.includeJSP("template-editor.jsp");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean editDelete(Dispatcher dispatcher, Context context, String command) {
/*  748 */     User user = MilestoneSecurity.getUser(context);
/*      */     
/*  750 */     Notepad notepad = getTemplateNotepad(context, user.getUserId());
/*  751 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/*  753 */     Template template = MilestoneHelper.getScreenTemplate(context);
/*      */ 
/*      */     
/*  756 */     if (template != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  765 */       int secureLevel = getTemplatePermissions(template, user);
/*      */       
/*  767 */       if (secureLevel < 2)
/*      */       {
/*  769 */         notepad.setSwitchToTaskVisible(false);
/*      */       }
/*      */       
/*  772 */       MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */       
/*  774 */       Vector tasks = template.getTasks();
/*      */ 
/*      */ 
/*      */       
/*  778 */       for (int i = 0; i < tasks.size(); i++) {
/*      */         
/*  780 */         Task task = (Task)tasks.elementAt(i);
/*  781 */         TemplateManager.getInstance().deleteTemplateDetail(template, task, user.getUserId());
/*      */       } 
/*      */ 
/*      */       
/*  785 */       TemplateManager.getInstance().deleteTemplate(template, user.getUserId());
/*      */ 
/*      */       
/*  788 */       notepad.setAllContents(null);
/*  789 */       notepad = getTemplateNotepad(context, user.getUserId());
/*  790 */       notepad.setSelected(null);
/*      */     } 
/*      */     
/*  793 */     return edit(dispatcher, context, command);
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
/*  806 */   private boolean locator(Dispatcher dispatcher, Context context, String command) { return context.includeJSP("template-locator.jsp"); }
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
/*  818 */   private boolean locatorCopy(Dispatcher dispatcher, Context context, String command) { return context.includeJSP("template-locator-copy.jsp"); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  827 */   private boolean assignToSchedule(Dispatcher dispatcher, Context context, String command) { return context.includeJSP("schedule-editor.jsp"); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  836 */   private boolean editAssignTask(Dispatcher dispatcher, Context context, String command) { return context.includeJSP("template-editor.jsp"); }
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
/*      */   private boolean editDeleteTask(Dispatcher dispatcher, Context context, String command) {
/*  848 */     User user = MilestoneSecurity.getUser(context);
/*      */     
/*  850 */     Notepad notepad = getTemplateNotepad(context, user.getUserId());
/*  851 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/*  853 */     Template template = MilestoneHelper.getScreenTemplate(context);
/*      */     
/*  855 */     Task task = new Task();
/*      */     
/*  857 */     int taskID = 0;
/*      */     
/*      */     try {
/*  860 */       taskID = Integer.parseInt(context.getParameter("taskID"));
/*      */     }
/*  862 */     catch (NumberFormatException e) {
/*      */       
/*  864 */       taskID = -1;
/*      */     } 
/*  866 */     task.setTaskID(taskID);
/*      */ 
/*      */ 
/*      */     
/*  870 */     TemplateManager.getInstance().deleteTemplateDetail(template, task, user.getUserId());
/*      */     
/*  872 */     template = TemplateManager.getInstance().getTemplate(template.getTemplateID(), true);
/*  873 */     context.putSessionValue("Template", template);
/*      */ 
/*      */ 
/*      */     
/*  877 */     notepad.setAllContents(null);
/*  878 */     notepad = getTemplateNotepad(context, user.getUserId());
/*  879 */     notepad.setSelected(template);
/*      */     
/*  881 */     return edit(dispatcher, context, command);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  891 */   private boolean recommended(Dispatcher dispatcher, Context context, String command) { return context.includeJSP("template-recommended.jsp"); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Form buildForm(Context context, Template template, String command) {
/*  899 */     Calendar testDate = Calendar.getInstance();
/*  900 */     User sessionUser = MilestoneSecurity.getUser(context);
/*      */     
/*  902 */     Form form = new Form(this.application, "TemplateForm", 
/*  903 */         this.application.getInfrastructure().getServletURL(), "POST");
/*      */ 
/*      */     
/*  906 */     context.putDelivery("Configuration", getJavaScriptTemplateConfig(context));
/*      */     
/*  908 */     int secureLevel = getTemplatePermissions(template, sessionUser);
/*      */     
/*  910 */     setButtonVisibilities(template, sessionUser, context, secureLevel, command);
/*      */ 
/*      */     
/*  913 */     FormTextField templateName = new FormTextField("templateName", "", true, 20);
/*  914 */     templateName.setId("templateName");
/*  915 */     templateName.setMaxLength(30);
/*  916 */     templateName.addFormEvent("onBlur", "Javascript:checkField(this)");
/*  917 */     templateName.setValue(template.getTempateName());
/*  918 */     templateName.setTabIndex(1);
/*  919 */     form.addElement(templateName);
/*      */ 
/*      */     
/*  922 */     Vector families = null;
/*  923 */     String familyId = "";
/*      */     
/*  925 */     if (template.getOwner() != null) {
/*      */       
/*  927 */       familyId = Integer.toString(template.getOwner().getStructureID());
/*  928 */       families = MilestoneHelper.getNonSecureUserFamilies(context);
/*      */     }
/*      */     else {
/*      */       
/*  932 */       familyId = "";
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  938 */     if (command.equalsIgnoreCase("template-edit-copy")) {
/*      */       
/*  940 */       familyId = "";
/*  941 */       families = null;
/*  942 */       families = MilestoneHelper.getSecureUserFamilies(sessionUser, 1, context);
/*      */     } 
/*  944 */     FormDropDownMenu owner = MilestoneHelper.getCorporateStructureDropDown("owner", families, familyId, true, true);
/*  945 */     owner.setTabIndex(2);
/*  946 */     form.addElement(owner);
/*      */ 
/*      */     
/*  949 */     String[] dvalues = new String[3];
/*  950 */     dvalues[0] = "0";
/*  951 */     dvalues[1] = "1";
/*  952 */     dvalues[2] = "2";
/*      */     
/*  954 */     String[] dlabels = new String[3];
/*  955 */     dlabels[0] = "Physical";
/*  956 */     dlabels[1] = "Digital";
/*  957 */     dlabels[2] = "Both";
/*      */     
/*  959 */     FormRadioButtonGroup prodType = new FormRadioButtonGroup("ProdType", 
/*  960 */         String.valueOf(template.getProdType()), dvalues, dlabels, false);
/*  961 */     prodType.addFormEvent("onClick", "initConfigs()");
/*  962 */     form.addElement(prodType);
/*      */ 
/*      */     
/*  965 */     FormDropDownMenu productLine = MilestoneHelper.getLookupDropDown("productLine", Cache.getProductCategories(template.getProdType()), MilestoneHelper.getLookupObjectValue(template.getProductCategory()), true, true);
/*  966 */     productLine.setId("productLine");
/*  967 */     productLine.setTabIndex(3);
/*  968 */     form.addElement(productLine);
/*      */ 
/*      */     
/*  971 */     FormDropDownMenu releaseType = MilestoneHelper.getLookupDropDown("releaseType", Cache.getReleaseTypes(), MilestoneHelper.getLookupObjectValue(template.getReleaseType()), true, true);
/*  972 */     releaseType.setTabIndex(4);
/*  973 */     form.addElement(releaseType);
/*      */ 
/*      */     
/*  976 */     String configValue = "";
/*  977 */     if (template.getSelectionConfig() != null)
/*      */     {
/*  979 */       configValue = template.getSelectionConfig().getSelectionConfigurationAbbreviation();
/*      */     }
/*      */     
/*  982 */     FormDropDownMenu configuration = MilestoneHelper.getSelectionConfigurationDropDown("Configuration", configValue.trim(), true);
/*  983 */     configuration.setId("Configuration");
/*  984 */     configuration.setTabIndex(5);
/*  985 */     configuration.addFormEvent("onChange", "adjustSelection(this)");
/*  986 */     form.addElement(configuration);
/*      */ 
/*      */     
/*  989 */     String subConfigValue = "";
/*  990 */     if (template.getSelectionSubConfig() != null) subConfigValue = template.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation(); 
/*  991 */     FormDropDownMenu subConfiguration = MilestoneHelper.getSelectionSubConfigurationDropDown("SubConfiguration", template.getSelectionConfig(), subConfigValue.trim(), true);
/*  992 */     subConfiguration.setTabIndex(6);
/*  993 */     form.addElement(subConfiguration);
/*      */ 
/*      */     
/*  996 */     FormTextField lastUpdated = new FormTextField("lastUpdatedDate", false, 50);
/*  997 */     if (template.getLastUpdateDate() != null)
/*  998 */       lastUpdated.setValue(MilestoneHelper.getCustomFormatedDate(template.getLastUpdateDate(), "M/d/yyyy hh:mm:ss a 'ET'")); 
/*  999 */     form.addElement(lastUpdated);
/*      */     
/* 1001 */     FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
/* 1002 */     if (UserManager.getInstance().getUser(template.getLastUpdatingUser()) != null)
/* 1003 */       lastUpdatedBy.setValue(UserManager.getInstance().getUser(template.getLastUpdatingUser()).getName()); 
/* 1004 */     form.addElement(lastUpdatedBy);
/*      */ 
/*      */ 
/*      */     
/* 1008 */     Vector templateRights = new Vector();
/*      */ 
/*      */     
/* 1011 */     Vector tasks = template.getTasks();
/*      */     
/* 1013 */     for (int i = 0; i < tasks.size(); i++) {
/*      */       
/* 1015 */       Task task = (Task)tasks.get(i);
/*      */ 
/*      */       
/* 1018 */       if (task.getOwner() != null) {
/* 1019 */         templateRights.add(new String(Integer.toString(TemplateManager.getInstance().getTaskEditAccess(sessionUser, task.getOwner().getStructureID()))));
/*      */       } else {
/* 1021 */         templateRights.add("0");
/*      */       } 
/* 1023 */       task = null;
/*      */     } 
/*      */ 
/*      */     
/* 1027 */     context.putDelivery("templateRights", templateRights);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1032 */     FormTextField templateNameSearch = new FormTextField("templateNameSrch", "", false, 20);
/* 1033 */     templateNameSearch.setId("templateNameSrch");
/* 1034 */     form.addElement(templateNameSearch);
/*      */ 
/*      */     
/* 1037 */     String[] dsearchvalues = new String[3];
/* 1038 */     dsearchvalues[0] = "0";
/* 1039 */     dsearchvalues[1] = "1";
/* 1040 */     dsearchvalues[2] = "2";
/*      */     
/* 1042 */     String[] dsearchlabels = new String[3];
/* 1043 */     dsearchlabels[0] = "Physical";
/* 1044 */     dsearchlabels[1] = "Digital";
/* 1045 */     dsearchlabels[2] = "Both";
/*      */     
/* 1047 */     FormRadioButtonGroup searchProdType = new FormRadioButtonGroup("ProdTypeSearch", "2", dsearchvalues, dsearchlabels, false);
/* 1048 */     searchProdType.addFormEvent("onClick", "initSearchConfigs()");
/* 1049 */     form.addElement(searchProdType);
/*      */ 
/*      */     
/* 1052 */     FormDropDownMenu configurationSearch = MilestoneHelper.getSelectionConfigurationDropDown("configurationSrch", "", false);
/* 1053 */     configurationSearch.setId("configurationSrch");
/* 1054 */     form.addElement(configurationSearch);
/*      */ 
/*      */     
/* 1057 */     FormDropDownMenu ownerSearch = MilestoneHelper.getCorporateStructureDropDown("ownerSrch", families, "0", false, true);
/* 1058 */     ownerSearch.setId("ownerSrch");
/* 1059 */     form.addElement(ownerSearch);
/*      */     
/* 1061 */     form.addElement(new FormHidden("OrderBy", ""));
/* 1062 */     form.addElement(new FormHidden("OrderTasksBy", ""));
/*      */ 
/*      */     
/* 1065 */     context.putSessionValue("Template", template);
/*      */ 
/*      */     
/* 1068 */     if (context.getSessionValue("NOTEPAD_TEMPLATES_VISIBLE") != null) {
/* 1069 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_TEMPLATES_VISIBLE"));
/*      */     }
/* 1071 */     return form;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Form buildNewForm(Context context, String command) {
/* 1079 */     Calendar testDate = Calendar.getInstance();
/* 1080 */     User sessionUser = MilestoneSecurity.getUser(context);
/*      */     
/* 1082 */     Form form = new Form(this.application, "TemplateForm", 
/* 1083 */         this.application.getInfrastructure().getServletURL(), "POST");
/*      */     
/* 1085 */     Template template = new Template();
/* 1086 */     template.setTemplateID(-1);
/*      */ 
/*      */     
/* 1089 */     int secureLevel = getTemplatePermissions(template, sessionUser);
/* 1090 */     setButtonVisibilities(template, sessionUser, context, secureLevel, command);
/*      */ 
/*      */     
/* 1093 */     context.putDelivery("Configuration", getJavaScriptTemplateConfig(context));
/*      */ 
/*      */     
/* 1096 */     FormTextField templateName = new FormTextField("templateName", "", true, 20);
/* 1097 */     templateName.setId("templateName");
/* 1098 */     templateName.setMaxLength(30);
/* 1099 */     templateName.addFormEvent("onBlur", "Javascript:checkField(this)");
/* 1100 */     templateName.setTabIndex(1);
/* 1101 */     form.addElement(templateName);
/*      */ 
/*      */     
/* 1104 */     Vector families = null;
/* 1105 */     this.log.debug("before getSecureUserFamilies...");
/* 1106 */     families = MilestoneHelper.getSecureUserFamilies(sessionUser, 1, context);
/* 1107 */     this.log.debug("families...");
/* 1108 */     FormDropDownMenu owner = MilestoneHelper.getCorporateStructureDropDown("owner", families, "0", true, true);
/* 1109 */     owner.setTabIndex(2);
/* 1110 */     form.addElement(owner);
/*      */ 
/*      */     
/* 1113 */     String[] dvalues = new String[3];
/* 1114 */     dvalues[0] = "0";
/* 1115 */     dvalues[1] = "1";
/* 1116 */     dvalues[2] = "2";
/*      */     
/* 1118 */     String[] dlabels = new String[3];
/* 1119 */     dlabels[0] = "Physical";
/* 1120 */     dlabels[1] = "Digital";
/* 1121 */     dlabels[2] = "Both";
/*      */     
/* 1123 */     FormRadioButtonGroup prodType = new FormRadioButtonGroup("ProdType", 
/* 1124 */         "", dvalues, dlabels, false);
/* 1125 */     prodType.addFormEvent("onChange", "initConfigs()");
/* 1126 */     form.addElement(prodType);
/*      */ 
/*      */     
/* 1129 */     FormDropDownMenu productLine = MilestoneHelper.getLookupDropDown("productLine", Cache.getProductCategories(), "0", true, true);
/* 1130 */     productLine.setId("productLine");
/* 1131 */     productLine.setTabIndex(3);
/* 1132 */     form.addElement(productLine);
/*      */ 
/*      */     
/* 1135 */     FormDropDownMenu releaseType = MilestoneHelper.getLookupDropDown("releaseType", Cache.getReleaseTypes(), "0", true, true);
/* 1136 */     releaseType.setTabIndex(4);
/* 1137 */     form.addElement(releaseType);
/*      */ 
/*      */     
/* 1140 */     FormDropDownMenu configuration = MilestoneHelper.getSelectionConfigurationDropDown("Configuration", "0", true);
/* 1141 */     configuration.setId("Configuration");
/* 1142 */     configuration.setTabIndex(5);
/* 1143 */     configuration.addFormEvent("onChange", "adjustSelection(this)");
/* 1144 */     form.addElement(configuration);
/*      */ 
/*      */     
/* 1147 */     FormDropDownMenu subConfiguration = new FormDropDownMenu("SubConfiguration", "&nbsp;", "&nbsp;", true);
/* 1148 */     subConfiguration.setTabIndex(6);
/* 1149 */     form.addElement(subConfiguration);
/*      */ 
/*      */ 
/*      */     
/* 1153 */     FormTextField lastUpdated = new FormTextField("lastUpdatedDate", false, 50);
/* 1154 */     form.addElement(lastUpdated);
/*      */     
/* 1156 */     FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
/* 1157 */     form.addElement(lastUpdatedBy);
/*      */ 
/*      */ 
/*      */     
/* 1161 */     Vector templateRights = new Vector();
/* 1162 */     templateRights.add("0");
/*      */ 
/*      */     
/* 1165 */     context.putDelivery("templateRights", templateRights);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1170 */     FormTextField templateNameSearch = new FormTextField("templateNameSrch", "", false, 20);
/* 1171 */     templateNameSearch.setId("templateNameSrch");
/* 1172 */     form.addElement(templateNameSearch);
/*      */ 
/*      */     
/* 1175 */     String[] dsearchvalues = new String[3];
/* 1176 */     dsearchvalues[0] = "0";
/* 1177 */     dsearchvalues[1] = "1";
/* 1178 */     dsearchvalues[2] = "2";
/*      */     
/* 1180 */     String[] dsearchlabels = new String[3];
/* 1181 */     dsearchlabels[0] = "Physical";
/* 1182 */     dsearchlabels[1] = "Digital";
/* 1183 */     dsearchlabels[2] = "Both";
/*      */     
/* 1185 */     FormRadioButtonGroup searchProdType = new FormRadioButtonGroup("ProdTypeSearch", "2", dsearchvalues, dsearchlabels, false);
/* 1186 */     searchProdType.addFormEvent("onClick", "initSearchConfigs()");
/* 1187 */     form.addElement(searchProdType);
/*      */ 
/*      */     
/* 1190 */     FormDropDownMenu configurationSearch = MilestoneHelper.getSelectionConfigurationDropDown("configurationSrch", "", false);
/* 1191 */     configurationSearch.setId("configurationSrch");
/* 1192 */     form.addElement(configurationSearch);
/*      */ 
/*      */     
/* 1195 */     FormDropDownMenu ownerSearch = MilestoneHelper.getCorporateStructureDropDown("ownerSrch", families, "0", false, true);
/* 1196 */     ownerSearch.setId("ownerSrch");
/* 1197 */     form.addElement(ownerSearch);
/*      */     
/* 1199 */     form.addElement(new FormHidden("cmd", "template-edit-new"));
/* 1200 */     form.addElement(new FormHidden("OrderBy", ""));
/* 1201 */     form.addElement(new FormHidden("OrderTasksBy", ""));
/*      */ 
/*      */     
/* 1204 */     if (context.getSessionValue("NOTEPAD_TEMPLATES_VISIBLE") != null) {
/* 1205 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_TEMPLATES_VISIBLE"));
/*      */     }
/* 1207 */     return form;
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
/*      */   private boolean editCopy(Dispatcher dispatcher, Context context, String command) {
/* 1220 */     User user = MilestoneSecurity.getUser(context);
/*      */     
/* 1222 */     Notepad notepad = getTemplateNotepad(context, user.getUserId());
/* 1223 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/* 1225 */     Template targetTemplate = MilestoneHelper.getScreenTemplate(context);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1234 */     int secureLevel = getTemplatePermissions(targetTemplate, user);
/*      */     
/* 1236 */     if (secureLevel < 2)
/*      */     {
/* 1238 */       notepad.setSwitchToTaskVisible(false);
/*      */     }
/*      */     
/* 1241 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/* 1243 */     Template copiedTemplate = null;
/*      */ 
/*      */     
/*      */     try {
/* 1247 */       copiedTemplate = (Template)targetTemplate.clone();
/*      */     }
/* 1249 */     catch (CloneNotSupportedException cloneNotSupportedException) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1257 */     copiedTemplate.setTemplateID(-1);
/* 1258 */     copiedTemplate.setTemplateName("");
/* 1259 */     copiedTemplate.setReleaseType(null);
/* 1260 */     copiedTemplate.setProductCategory(null);
/* 1261 */     copiedTemplate.setSelectionConfig(null);
/* 1262 */     copiedTemplate.setSelectionSubConfig(null);
/*      */ 
/*      */     
/* 1265 */     Form form = null;
/* 1266 */     form = buildForm(context, copiedTemplate, command);
/* 1267 */     form.addElement(new FormHidden("cmd", "template-edit-copy"));
/*      */     
/* 1269 */     if (context.getSessionValue("TemplateTaskSort") != null) {
/* 1270 */       copiedTemplate.setTasks(sortScreenTaskVector(Integer.parseInt((String)context.getSessionValue("TemplateTaskSort")), copiedTemplate.getTasks()));
/*      */     }
/* 1272 */     context.putSessionValue("Template", copiedTemplate);
/* 1273 */     context.putDelivery("Form", form);
/*      */ 
/*      */     
/* 1276 */     if (context.getSessionValue("NOTEPAD_TEMPLATES_VISIBLE") != null) {
/* 1277 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_TEMPLATES_VISIBLE"));
/*      */     }
/* 1279 */     return context.includeJSP("template-editor.jsp");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Notepad getTaskNotepad(Context context, int userId, int templateId) {
/* 1289 */     Vector contents = new Vector();
/*      */     
/* 1291 */     if (MilestoneHelper.getNotepadFromSession(19, context) != null) {
/*      */       
/* 1293 */       Notepad notepad = MilestoneHelper.getNotepadFromSession(19, context);
/*      */       
/* 1295 */       if (notepad.getAllContents() == null) {
/*      */         
/* 1297 */         contents = TemplateManager.getInstance().getTemplateTaskNotepadList(templateId, userId, notepad, context);
/* 1298 */         notepad.setAllContents(contents);
/*      */       } 
/*      */       
/* 1301 */       return notepad;
/*      */     } 
/*      */ 
/*      */     
/* 1305 */     String[] columnNames = { "Task Name", "Wks", "Own", "Dpt" };
/* 1306 */     contents = TemplateManager.getInstance().getTemplateTaskNotepadList(templateId, userId, null, context);
/* 1307 */     return new Notepad(contents, 0, 7, "Unassigned Tasks", 19, columnNames);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean editTemplateTasks(Dispatcher dispatcher, Context context, String command) {
/* 1316 */     User user = MilestoneSecurity.getUser(context);
/*      */     
/* 1318 */     Template template = MilestoneHelper.getScreenTemplate(context);
/*      */     
/* 1320 */     Notepad notepad = getTaskNotepad(context, user.getUserId(), template.getTemplateID());
/* 1321 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/* 1323 */     Form form = null;
/* 1324 */     if (template != null) {
/*      */       
/* 1326 */       form = buildTaskForm(context, template, notepad, command);
/* 1327 */       form.addElement(new FormHidden("cmd", "template-task-editor"));
/* 1328 */       context.putDelivery("Form", form);
/* 1329 */       return context.includeJSP("template-task-editor.jsp");
/*      */     } 
/*      */ 
/*      */     
/* 1333 */     return dispatcher.redispatch(context, "template-editor");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean editTemplateTaskSave(Dispatcher dispatcher, Context context, String command) {
/* 1343 */     User user = MilestoneSecurity.getUser(context);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1348 */     Notepad notepad = getTemplateNotepad(context, user.getUserId());
/* 1349 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/* 1351 */     Template template = (Template)context.getSessionValue("Template");
/*      */ 
/*      */     
/* 1354 */     Notepad taskNotepad = getTaskNotepad(context, user.getUserId(), template.getTemplateID());
/* 1355 */     MilestoneHelper.putNotepadIntoSession(taskNotepad, context);
/*      */     
/* 1357 */     Form form = buildTaskForm(context, template, taskNotepad, command);
/* 1358 */     form.addElement(new FormHidden("cmd", "template-task-editor", true));
/*      */     
/* 1360 */     form.setValues(context);
/*      */ 
/*      */     
/* 1363 */     String name = form.getStringValue("templateName");
/* 1364 */     template.setTemplateName(name);
/*      */ 
/*      */     
/* 1367 */     String owner = form.getStringValue("owner");
/* 1368 */     template.setOwner((Family)MilestoneHelper.getStructureObject(Integer.parseInt(owner)));
/*      */ 
/*      */     
/* 1371 */     template.setProdType(Integer.parseInt(form.getStringValue("ProdType")));
/*      */ 
/*      */     
/* 1374 */     String productLine = form.getStringValue("productLine");
/* 1375 */     template.setProductCategory((ProductCategory)SelectionManager.getLookupObject(productLine, Cache.getProductCategories()));
/*      */ 
/*      */     
/* 1378 */     String configuration = form.getStringValue("Configuration");
/* 1379 */     template.setSelectionConfig(SelectionManager.getSelectionConfigObject(configuration, Cache.getSelectionConfigs()));
/*      */ 
/*      */     
/* 1382 */     String subConfiguration = form.getStringValue("SubConfiguration");
/* 1383 */     template.setSelectionSubConfig(SelectionManager.getSelectionSubConfigObject(subConfiguration, template.getSelectionConfig()));
/*      */ 
/*      */     
/* 1386 */     String releaseType = form.getStringValue("releaseType");
/* 1387 */     template.setReleaseType((ReleaseType)SelectionManager.getLookupObject(releaseType, Cache.getReleaseTypes()));
/*      */     
/* 1389 */     Template savedTemplate = null;
/*      */     
/* 1391 */     if (!form.isUnchanged()) {
/*      */       
/* 1393 */       FormValidation formValidation = form.validate();
/* 1394 */       if (formValidation.isGood()) {
/*      */         
/* 1396 */         if (command.equalsIgnoreCase("template-task-copy-save")) {
/*      */           
/* 1398 */           savedTemplate = TemplateManager.getInstance().saveTemplateCopiedTemplate(template, user.getUserId());
/*      */         }
/*      */         else {
/*      */           
/* 1402 */           savedTemplate = TemplateManager.getInstance().saveTemplate(template, user.getUserId());
/*      */         } 
/*      */ 
/*      */         
/* 1406 */         FormElement lastUpdated = form.getElement("lastupdateddate");
/*      */         
/* 1408 */         lastUpdated.setValue(MilestoneHelper.getLongDate(savedTemplate.getLastUpdateDate()));
/*      */         
/* 1410 */         if (context.getSessionValue("TemplateTaskSort") != null) {
/* 1411 */           savedTemplate.setTasks(sortScreenTaskVector(Integer.parseInt((String)context.getSessionValue("TemplateTaskSort")), savedTemplate.getTasks()));
/*      */         }
/*      */         
/* 1414 */         context.putSessionValue("Template", savedTemplate);
/*      */         
/* 1416 */         context.putDelivery("Form", form);
/*      */ 
/*      */         
/* 1419 */         notepad.setAllContents(null);
/* 1420 */         notepad = getTemplateNotepad(context, user.getUserId());
/* 1421 */         notepad.setSelected(savedTemplate);
/*      */       }
/*      */       else {
/*      */         
/* 1425 */         context.putDelivery("FormValidation", formValidation);
/*      */       } 
/*      */     } 
/* 1428 */     context.putDelivery("Form", form);
/* 1429 */     return context.includeJSP("template-task-editor.jsp");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Form buildTaskForm(Context context, Template template, Notepad notepadTask, String command) {
/* 1437 */     Calendar testDate = Calendar.getInstance();
/* 1438 */     User sessionUser = MilestoneSecurity.getUser(context);
/*      */     
/* 1440 */     Form form = new Form(this.application, "TemplateForm", 
/* 1441 */         this.application.getInfrastructure().getServletURL(), "POST");
/*      */     
/* 1443 */     int secureLevel = getTemplatePermissions(template, sessionUser);
/* 1444 */     setButtonVisibilities(template, sessionUser, context, secureLevel, command);
/*      */     
/* 1446 */     if (context.getSessionValue("TemplateTaskSort") != null) {
/* 1447 */       template.setTasks(sortScreenTaskVector(Integer.parseInt((String)context.getSessionValue("TemplateTaskSort")), template.getTasks()));
/*      */     }
/*      */     
/* 1450 */     context.putDelivery("Configuration", getJavaScriptTemplateConfig(context));
/*      */ 
/*      */     
/* 1453 */     Vector notepadPageContents = notepadTask.getPageContents();
/* 1454 */     Task task = null;
/* 1455 */     FormCheckBox notepadCheckbox = null;
/*      */     
/* 1457 */     for (int j = 0; j < notepadPageContents.size(); j++) {
/*      */       
/* 1459 */       task = (Task)notepadPageContents.get(j);
/* 1460 */       notepadCheckbox = new FormCheckBox(String.valueOf(task.getTaskID()), "", false, false);
/* 1461 */       form.addElement(notepadCheckbox);
/* 1462 */       task = null;
/* 1463 */       notepadCheckbox = null;
/*      */     } 
/*      */ 
/*      */     
/* 1467 */     FormTextField templateName = new FormTextField("templateName", "", true, 20);
/* 1468 */     templateName.setId("templateName");
/* 1469 */     templateName.setTabIndex(1);
/* 1470 */     templateName.setMaxLength(30);
/* 1471 */     templateName.addFormEvent("onBlur", "Javascript:checkField(this)");
/* 1472 */     templateName.setValue(template.getTempateName());
/* 1473 */     form.addElement(templateName);
/*      */ 
/*      */     
/* 1476 */     Vector families = null;
/* 1477 */     String familyId = "";
/*      */     
/* 1479 */     if (template.getOwner() != null) {
/*      */       
/* 1481 */       familyId = Integer.toString(template.getOwner().getStructureID());
/* 1482 */       families = MilestoneHelper.getNonSecureUserFamilies(context);
/*      */     }
/*      */     else {
/*      */       
/* 1486 */       familyId = "";
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1492 */     if (command.equalsIgnoreCase("template-task-edit-copy")) {
/*      */       
/* 1494 */       familyId = "";
/* 1495 */       families = null;
/* 1496 */       families = MilestoneHelper.getSecureUserFamilies(sessionUser, 1, context);
/*      */     } 
/* 1498 */     FormDropDownMenu owner = MilestoneHelper.getCorporateStructureDropDown("owner", families, familyId, true, true);
/* 1499 */     owner.setTabIndex(2);
/* 1500 */     form.addElement(owner);
/*      */ 
/*      */     
/* 1503 */     String[] dvalues = new String[3];
/* 1504 */     dvalues[0] = "0";
/* 1505 */     dvalues[1] = "1";
/* 1506 */     dvalues[2] = "2";
/*      */     
/* 1508 */     String[] dlabels = new String[3];
/* 1509 */     dlabels[0] = "Physical";
/* 1510 */     dlabels[1] = "Digital";
/* 1511 */     dlabels[2] = "Both";
/*      */     
/* 1513 */     FormRadioButtonGroup prodType = new FormRadioButtonGroup("ProdType", 
/* 1514 */         String.valueOf(template.getProdType()), dvalues, dlabels, false);
/* 1515 */     prodType.addFormEvent("onClick", "initConfigs()");
/* 1516 */     form.addElement(prodType);
/*      */ 
/*      */     
/* 1519 */     FormDropDownMenu productLine = MilestoneHelper.getLookupDropDown("productLine", Cache.getProductCategories(template.getProdType()), MilestoneHelper.getLookupObjectValue(template.getProductCategory()), true, true);
/* 1520 */     productLine.setId("productLine");
/* 1521 */     productLine.setTabIndex(3);
/* 1522 */     form.addElement(productLine);
/*      */ 
/*      */     
/* 1525 */     String configValue = "";
/* 1526 */     if (template.getSelectionConfig() != null)
/*      */     {
/* 1528 */       configValue = template.getSelectionConfig().getSelectionConfigurationAbbreviation();
/*      */     }
/*      */     
/* 1531 */     FormDropDownMenu configuration = MilestoneHelper.getSelectionConfigurationDropDown("Configuration", configValue.trim(), true);
/* 1532 */     configuration.setId("Configuration");
/* 1533 */     configuration.setTabIndex(4);
/* 1534 */     configuration.addFormEvent("onChange", "adjustSelection(this)");
/* 1535 */     form.addElement(configuration);
/*      */ 
/*      */     
/* 1538 */     String subConfigValue = "";
/* 1539 */     if (template.getSelectionSubConfig() != null) subConfigValue = template.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation(); 
/* 1540 */     FormDropDownMenu subConfiguration = MilestoneHelper.getSelectionSubConfigurationDropDown("SubConfiguration", template.getSelectionConfig(), subConfigValue.trim(), true);
/* 1541 */     subConfiguration.setTabIndex(6);
/* 1542 */     form.addElement(subConfiguration);
/*      */ 
/*      */     
/* 1545 */     FormDropDownMenu releaseType = MilestoneHelper.getLookupDropDown("releaseType", Cache.getReleaseTypes(), MilestoneHelper.getLookupObjectValue(template.getReleaseType()), true, true);
/* 1546 */     releaseType.setTabIndex(5);
/* 1547 */     form.addElement(releaseType);
/*      */ 
/*      */     
/* 1550 */     FormTextField lastUpdated = new FormTextField("lastUpdatedDate", false, 50);
/* 1551 */     if (template.getLastUpdateDate() != null)
/* 1552 */       lastUpdated.setValue(MilestoneHelper.getLongDate(template.getLastUpdateDate())); 
/* 1553 */     form.addElement(lastUpdated);
/*      */     
/* 1555 */     FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", true, 50);
/* 1556 */     if (UserManager.getInstance().getUser(template.getLastUpdatingUser()) != null)
/* 1557 */       lastUpdatedBy.setValue(UserManager.getInstance().getUser(template.getLastUpdatingUser()).getLogin()); 
/* 1558 */     form.addElement(lastUpdatedBy);
/*      */ 
/*      */ 
/*      */     
/* 1562 */     Vector templateRights = new Vector();
/*      */ 
/*      */     
/* 1565 */     Vector tasks = template.getTasks();
/*      */     
/* 1567 */     for (int i = 0; i < tasks.size(); i++) {
/*      */       
/* 1569 */       Task templateTask = (Task)tasks.get(i);
/*      */ 
/*      */       
/* 1572 */       if (templateTask.getOwner() != null) {
/* 1573 */         templateRights.add(new String(Integer.toString(TemplateManager.getInstance().getTaskEditAccess(sessionUser, templateTask.getOwner().getStructureID()))));
/*      */       } else {
/* 1575 */         templateRights.add("0");
/*      */       } 
/* 1577 */       templateTask = null;
/*      */     } 
/*      */ 
/*      */     
/* 1581 */     context.putDelivery("templateRights", templateRights);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1586 */     FormTextField TaskNameSearch = new FormTextField("TaskNameSearch", "", false, 20);
/* 1587 */     TaskNameSearch.setId("TaskNameSearch");
/* 1588 */     form.addElement(TaskNameSearch);
/*      */ 
/*      */     
/* 1591 */     FormRadioButtonGroup KeyTaskSearch = new FormRadioButtonGroup("KeyTaskSearch", "", "Yes,No", false);
/* 1592 */     form.addElement(KeyTaskSearch);
/*      */ 
/*      */     
/* 1595 */     FormDropDownMenu TaskOwnerSearch = MilestoneHelper.getCorporateStructureDropDown("TaskOwnerSearch", families, "0", false, true);
/* 1596 */     form.addElement(TaskOwnerSearch);
/*      */ 
/*      */     
/* 1599 */     FormDropDownMenu TaskDepartmentSearch = MilestoneHelper.getDepartmentDropDown("TaskDepartmentSearch", "", false);
/* 1600 */     form.addElement(TaskDepartmentSearch);
/*      */ 
/*      */ 
/*      */     
/* 1604 */     form.addElement(new FormHidden("OrderBy", ""));
/* 1605 */     form.addElement(new FormHidden("OrderTasksBy", ""));
/*      */ 
/*      */     
/* 1608 */     context.putSessionValue("Template", template);
/*      */ 
/*      */     
/* 1611 */     if (context.getSessionValue("NOTEPAD_UNASSIGNED_TASKS_VISIBLE") != null) {
/* 1612 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_UNASSIGNED_TASKS_VISIBLE"));
/*      */     }
/* 1614 */     return form;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Form buildNewTaskForm(Context context, Notepad notepadTasks, String command) {
/* 1622 */     Calendar testDate = Calendar.getInstance();
/* 1623 */     User sessionUser = MilestoneSecurity.getUser(context);
/*      */     
/* 1625 */     Form form = new Form(this.application, "TemplateForm", 
/* 1626 */         this.application.getInfrastructure().getServletURL(), "POST");
/*      */     
/* 1628 */     Template template = new Template();
/* 1629 */     template.setTemplateID(-1);
/*      */ 
/*      */     
/* 1632 */     int secureLevel = getTemplatePermissions(template, sessionUser);
/* 1633 */     setButtonVisibilities(template, sessionUser, context, secureLevel, command);
/*      */ 
/*      */     
/* 1636 */     context.putDelivery("Configuration", getJavaScriptTemplateConfig(context));
/*      */ 
/*      */     
/* 1639 */     Vector notepadPageContents = notepadTasks.getPageContents();
/* 1640 */     Task task = null;
/* 1641 */     FormCheckBox notepadCheckbox = null;
/*      */     
/* 1643 */     for (int j = 0; j < notepadPageContents.size(); j++) {
/*      */       
/* 1645 */       task = (Task)notepadPageContents.get(j);
/* 1646 */       notepadCheckbox = new FormCheckBox(String.valueOf(task.getTaskID()), "", false, false);
/* 1647 */       form.addElement(notepadCheckbox);
/* 1648 */       task = null;
/* 1649 */       notepadCheckbox = null;
/*      */     } 
/*      */ 
/*      */     
/* 1653 */     FormTextField templateName = new FormTextField("templateName", "", true, 20);
/* 1654 */     templateName.setId("templateName");
/* 1655 */     templateName.setTabIndex(1);
/* 1656 */     templateName.setMaxLength(30);
/* 1657 */     templateName.addFormEvent("onBlur", "Javascript:checkField(this)");
/* 1658 */     form.addElement(templateName);
/*      */ 
/*      */     
/* 1661 */     Vector families = null;
/* 1662 */     families = MilestoneHelper.getSecureUserFamilies(sessionUser, 1, context);
/*      */     
/* 1664 */     FormDropDownMenu owner = MilestoneHelper.getCorporateStructureDropDown("owner", families, "", true, true);
/* 1665 */     owner.setTabIndex(2);
/* 1666 */     form.addElement(owner);
/*      */ 
/*      */     
/* 1669 */     String[] dvalues = new String[3];
/* 1670 */     dvalues[0] = "0";
/* 1671 */     dvalues[1] = "1";
/* 1672 */     dvalues[2] = "2";
/*      */     
/* 1674 */     String[] dlabels = new String[3];
/* 1675 */     dlabels[0] = "Physical";
/* 1676 */     dlabels[1] = "Digital";
/* 1677 */     dlabels[2] = "Both";
/*      */     
/* 1679 */     FormRadioButtonGroup prodType = new FormRadioButtonGroup("ProdType", 
/* 1680 */         "", dvalues, dlabels, false);
/* 1681 */     prodType.addFormEvent("onChange", "initConfigs()");
/* 1682 */     form.addElement(prodType);
/*      */ 
/*      */     
/* 1685 */     FormDropDownMenu productLine = MilestoneHelper.getLookupDropDown("productLine", Cache.getProductCategories(), "0", true, true);
/* 1686 */     productLine.setId("productLine");
/* 1687 */     productLine.setTabIndex(3);
/* 1688 */     form.addElement(productLine);
/*      */ 
/*      */     
/* 1691 */     FormDropDownMenu configuration = MilestoneHelper.getSelectionConfigurationDropDown("Configuration", "0", true);
/* 1692 */     configuration.setId("Configuration");
/* 1693 */     configuration.setTabIndex(4);
/* 1694 */     configuration.addFormEvent("onChange", "adjustSelection(this)");
/* 1695 */     form.addElement(configuration);
/*      */ 
/*      */     
/* 1698 */     FormDropDownMenu subConfiguration = new FormDropDownMenu("SubConfiguration", "", "", true);
/* 1699 */     subConfiguration.setTabIndex(6);
/* 1700 */     form.addElement(subConfiguration);
/*      */ 
/*      */     
/* 1703 */     FormDropDownMenu releaseType = MilestoneHelper.getLookupDropDown("releaseType", Cache.getReleaseTypes(), "0", true, true);
/* 1704 */     releaseType.setTabIndex(5);
/* 1705 */     form.addElement(releaseType);
/*      */ 
/*      */     
/* 1708 */     FormTextField lastUpdated = new FormTextField("lastUpdatedDate", false, 50);
/* 1709 */     form.addElement(lastUpdated);
/*      */     
/* 1711 */     FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
/* 1712 */     form.addElement(lastUpdatedBy);
/*      */ 
/*      */ 
/*      */     
/* 1716 */     Vector templateRights = new Vector();
/* 1717 */     templateRights.add("0");
/*      */ 
/*      */     
/* 1720 */     context.putDelivery("templateRights", templateRights);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1725 */     FormTextField TaskNameSearch = new FormTextField("TaskNameSearch", "", false, 20);
/* 1726 */     TaskNameSearch.setId("TaskNameSearch");
/* 1727 */     form.addElement(TaskNameSearch);
/*      */ 
/*      */     
/* 1730 */     FormRadioButtonGroup KeyTaskSearch = new FormRadioButtonGroup("KeyTaskSearch", "", "Yes,No", false);
/* 1731 */     form.addElement(KeyTaskSearch);
/*      */ 
/*      */     
/* 1734 */     FormDropDownMenu TaskOwnerSearch = MilestoneHelper.getCorporateStructureDropDown("TaskOwnerSearch", families, "0", false, true);
/* 1735 */     form.addElement(TaskOwnerSearch);
/*      */ 
/*      */     
/* 1738 */     Vector deptList = MilestoneHelper.getDepartmentList();
/* 1739 */     String dept = "";
/* 1740 */     String deptStringList = "";
/* 1741 */     for (int i = 0; i < deptList.size(); i++) {
/*      */       
/* 1743 */       dept = (String)deptList.get(i);
/* 1744 */       if (i == 0) {
/* 1745 */         deptStringList = "," + dept;
/*      */       } else {
/* 1747 */         deptStringList = String.valueOf(deptStringList) + "," + dept;
/*      */       } 
/* 1749 */     }  FormDropDownMenu TaskDepartmentSearch = new FormDropDownMenu("TaskDepartmentSearch", "", "0," + deptStringList, "&nbsp;," + deptStringList, false);
/* 1750 */     form.addElement(TaskDepartmentSearch);
/*      */ 
/*      */ 
/*      */     
/* 1754 */     form.addElement(new FormHidden("cmd", "template-task-edit-new"));
/* 1755 */     form.addElement(new FormHidden("OrderBy", ""));
/* 1756 */     form.addElement(new FormHidden("OrderTasksBy", ""));
/*      */ 
/*      */     
/* 1759 */     if (context.getSessionValue("NOTEPAD_UNASSIGNED_TASKS_VISIBLE") != null) {
/* 1760 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_UNASSIGNED_TASKS_VISIBLE"));
/*      */     }
/* 1762 */     return form;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean addTask(Dispatcher dispatcher, Context context, String command) {
/* 1770 */     User user = MilestoneSecurity.getUser(context);
/*      */     
/* 1772 */     Template template = MilestoneHelper.getScreenTemplate(context);
/*      */     
/* 1774 */     Notepad notepad = getTaskNotepad(context, user.getUserId(), template.getTemplateID());
/* 1775 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */ 
/*      */     
/* 1778 */     Form form = buildTaskForm(context, template, notepad, command);
/*      */     
/* 1780 */     form.setValues(context);
/*      */     
/* 1782 */     Task task = null;
/* 1783 */     Vector notepadPageContents = notepad.getPageContents();
/* 1784 */     for (int j = 0; j < notepadPageContents.size(); j++) {
/*      */       
/* 1786 */       task = (Task)notepadPageContents.get(j);
/*      */       
/* 1788 */       if (((FormCheckBox)form.getElement(String.valueOf(task.getTaskID()))).isChecked())
/*      */       {
/*      */         
/* 1791 */         if (task.getActiveFlag())
/*      */         {
/* 1793 */           TemplateManager.getInstance().addTask(template, task, user.getUserId());
/*      */         }
/*      */       }
/* 1796 */       task = null;
/*      */     } 
/*      */     
/* 1799 */     notepad.setAllContents(null);
/* 1800 */     notepad = getTaskNotepad(context, user.getUserId(), template.getTemplateID());
/* 1801 */     notepad.goToSelectedPage();
/*      */     
/* 1803 */     return editTemplateTasks(dispatcher, context, command);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean editTemplateTaskNew(Dispatcher dispatcher, Context context, String command) {
/* 1812 */     User user = MilestoneSecurity.getUser(context);
/*      */     
/* 1814 */     Template template = MilestoneHelper.getScreenTemplate(context);
/*      */     
/* 1816 */     Notepad notepad = getTaskNotepad(context, user.getUserId(), template.getTemplateID());
/* 1817 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/* 1819 */     Form form = buildNewTaskForm(context, notepad, command);
/* 1820 */     context.putDelivery("Form", form);
/*      */     
/* 1822 */     return context.includeJSP("template-task-editor.jsp");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean editTemplateTaskSaveNew(Dispatcher dispatcher, Context context, String command) {
/* 1831 */     User user = MilestoneSecurity.getUser(context);
/*      */     
/* 1833 */     Template oldTemplate = MilestoneHelper.getScreenTemplate(context);
/*      */ 
/*      */ 
/*      */     
/* 1837 */     Notepad notepad = getTaskNotepad(context, user.getUserId(), oldTemplate.getTemplateID());
/* 1838 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1844 */     Notepad templateNotepad = getTemplateNotepad(context, user.getUserId());
/* 1845 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/* 1847 */     Template template = new Template();
/*      */     
/* 1849 */     Form form = buildNewTaskForm(context, notepad, command);
/* 1850 */     form.addElement(new FormHidden("cmd", "template-task-editor"));
/*      */     
/* 1852 */     form.setValues(context);
/*      */ 
/*      */ 
/*      */     
/* 1856 */     template.setTemplateID(-1);
/*      */ 
/*      */ 
/*      */     
/* 1860 */     template.setTasks(null);
/*      */ 
/*      */     
/* 1863 */     String name = form.getStringValue("templateName");
/* 1864 */     template.setTemplateName(name);
/*      */ 
/*      */     
/* 1867 */     String owner = form.getStringValue("owner");
/* 1868 */     template.setOwner((Family)MilestoneHelper.getStructureObject(Integer.parseInt(owner)));
/*      */ 
/*      */     
/* 1871 */     template.setProdType(Integer.parseInt(form.getStringValue("ProdType")));
/*      */ 
/*      */     
/* 1874 */     String productLine = form.getStringValue("productLine");
/* 1875 */     template.setProductCategory((ProductCategory)SelectionManager.getLookupObject(productLine, Cache.getProductCategories()));
/*      */ 
/*      */     
/* 1878 */     String configuration = form.getStringValue("Configuration");
/* 1879 */     template.setSelectionConfig(SelectionManager.getSelectionConfigObject(configuration, Cache.getSelectionConfigs()));
/*      */ 
/*      */ 
/*      */     
/* 1883 */     String subConfiguration = form.getStringValue("SubConfiguration");
/* 1884 */     template.setSelectionSubConfig(SelectionManager.getSelectionSubConfigObject(subConfiguration, template.getSelectionConfig()));
/*      */ 
/*      */     
/* 1887 */     String releaseType = form.getStringValue("releaseType");
/* 1888 */     template.setReleaseType((ReleaseType)SelectionManager.getLookupObject(releaseType, Cache.getReleaseTypes()));
/*      */     
/* 1890 */     form.addElement(new FormHidden("cmd", "template-task-edit-save-new"));
/*      */     
/* 1892 */     if (!form.isUnchanged()) {
/*      */       
/* 1894 */       FormValidation formValidation = form.validate();
/* 1895 */       if (formValidation.isGood()) {
/*      */         
/* 1897 */         Template savedTemplate = TemplateManager.getInstance().saveTemplate(template, user.getUserId());
/*      */         
/* 1899 */         if (context.getSessionValue("TemplateTaskSort") != null) {
/* 1900 */           savedTemplate.setTasks(sortScreenTaskVector(Integer.parseInt((String)context.getSessionValue("TemplateTaskSort")), savedTemplate.getTasks()));
/*      */         }
/* 1902 */         context.putSessionValue("Template", savedTemplate);
/*      */         
/* 1904 */         context.putDelivery("Form", form);
/*      */ 
/*      */         
/* 1907 */         templateNotepad.setAllContents(null);
/* 1908 */         templateNotepad = getTemplateNotepad(context, user.getUserId());
/* 1909 */         templateNotepad.setSelected(savedTemplate);
/*      */       }
/*      */       else {
/*      */         
/* 1913 */         context.putDelivery("FormValidation", formValidation);
/* 1914 */         form.addElement(new FormHidden("OrderBy", "", true));
/* 1915 */         context.putDelivery("Form", form);
/*      */       } 
/*      */     } 
/* 1918 */     return context.includeJSP("template-task-editor.jsp");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean editTemplateTaskDelete(Dispatcher dispatcher, Context context, String command) {
/* 1927 */     User user = MilestoneSecurity.getUser(context);
/*      */     
/* 1929 */     Notepad notepad = getTemplateNotepad(context, user.getUserId());
/* 1930 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/* 1932 */     Template template = MilestoneHelper.getScreenTemplate(context);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1938 */     Notepad taskNotepad = getTaskNotepad(context, user.getUserId(), template.getTemplateID());
/* 1939 */     MilestoneHelper.putNotepadIntoSession(taskNotepad, context);
/*      */     
/* 1941 */     Vector tasks = template.getTasks();
/*      */ 
/*      */ 
/*      */     
/* 1945 */     for (int i = 0; i < tasks.size(); i++) {
/*      */       
/* 1947 */       Task task = (Task)tasks.elementAt(i);
/* 1948 */       TemplateManager.getInstance().deleteTemplateDetail(template, task, user.getUserId());
/*      */     } 
/*      */ 
/*      */     
/* 1952 */     TemplateManager.getInstance().deleteTemplate(template, user.getUserId());
/*      */ 
/*      */     
/* 1955 */     notepad.setAllContents(null);
/* 1956 */     notepad = getTemplateNotepad(context, user.getUserId());
/* 1957 */     notepad.setSelected(null);
/*      */     
/* 1959 */     return editTemplateTasks(dispatcher, context, command);
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
/*      */   private boolean editTemplateTaskCopy(Dispatcher dispatcher, Context context, String command) {
/* 1973 */     User user = MilestoneSecurity.getUser(context);
/*      */     
/* 1975 */     Notepad notepad = getTemplateNotepad(context, user.getUserId());
/* 1976 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/* 1978 */     Template targetTemplate = MilestoneHelper.getScreenTemplate(context);
/* 1979 */     Template copiedTemplate = null;
/*      */     
/* 1981 */     Notepad taskNotepad = getTaskNotepad(context, user.getUserId(), targetTemplate.getTemplateID());
/* 1982 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */ 
/*      */     
/*      */     try {
/* 1986 */       copiedTemplate = (Template)targetTemplate.clone();
/*      */     }
/* 1988 */     catch (CloneNotSupportedException cloneNotSupportedException) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1996 */     copiedTemplate.setTemplateID(-1);
/* 1997 */     copiedTemplate.setTemplateName("");
/* 1998 */     copiedTemplate.setReleaseType(null);
/* 1999 */     copiedTemplate.setProductCategory(null);
/* 2000 */     copiedTemplate.setSelectionConfig(null);
/*      */ 
/*      */     
/* 2003 */     Vector tasks = targetTemplate.getTasks();
/* 2004 */     copiedTemplate.setTasks(targetTemplate.getTasks());
/*      */ 
/*      */     
/* 2007 */     Form form = buildTaskForm(context, copiedTemplate, taskNotepad, command);
/* 2008 */     form.addElement(new FormHidden("cmd", "template-task-edit-copy"));
/*      */     
/* 2010 */     if (context.getSessionValue("TemplateTaskSort") != null) {
/* 2011 */       copiedTemplate.setTasks(sortScreenTaskVector(Integer.parseInt((String)context.getSessionValue("TemplateTaskSort")), copiedTemplate.getTasks()));
/*      */     }
/* 2013 */     context.putSessionValue("Template", copiedTemplate);
/* 2014 */     context.putDelivery("Form", form);
/*      */ 
/*      */     
/* 2017 */     if (context.getSessionValue("NOTEPAD_UNASSIGNED_TASKS_VISIBLE") != null) {
/* 2018 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_UNASSIGNED_TASKS_VISIBLE"));
/*      */     }
/* 2020 */     return context.includeJSP("template-task-editor.jsp");
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
/*      */   private boolean editTemplateTaskDeleteTask(Dispatcher dispatcher, Context context, String command) {
/* 2033 */     User user = MilestoneSecurity.getUser(context);
/*      */ 
/*      */     
/* 2036 */     Notepad notepad = getTemplateNotepad(context, user.getUserId());
/* 2037 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/* 2039 */     Template template = MilestoneHelper.getScreenTemplate(context);
/*      */ 
/*      */     
/* 2042 */     Notepad taskNotepad = getTaskNotepad(context, user.getUserId(), template.getTemplateID());
/* 2043 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/* 2045 */     Task task = new Task();
/*      */     
/* 2047 */     int taskID = 0;
/*      */     
/*      */     try {
/* 2050 */       taskID = Integer.parseInt(context.getParameter("taskID"));
/*      */     }
/* 2052 */     catch (NumberFormatException e) {
/*      */       
/* 2054 */       taskID = -1;
/*      */     } 
/* 2056 */     task.setTaskID(taskID);
/*      */ 
/*      */ 
/*      */     
/* 2060 */     TemplateManager.getInstance().deleteTemplateDetail(template, task, user.getUserId());
/*      */     
/* 2062 */     template = TemplateManager.getInstance().getTemplate(template.getTemplateID(), true);
/* 2063 */     context.putSessionValue("Template", template);
/*      */ 
/*      */     
/* 2066 */     notepad.setAllContents(null);
/* 2067 */     notepad = getTemplateNotepad(context, user.getUserId());
/* 2068 */     notepad.setSelected(template);
/*      */     
/* 2070 */     return editTemplateTasks(dispatcher, context, command);
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
/*      */   public static int getTemplatePermissions(Template template, User user) {
/* 2084 */     int level = 0;
/* 2085 */     int familyId = 0;
/*      */     
/* 2087 */     if (template != null && template.getTemplateID() > -1) {
/*      */       
/* 2089 */       Family family = template.getOwner();
/*      */       
/* 2091 */       Vector environments = family.getEnvironments();
/*      */       
/* 2093 */       for (int i = 0; i < environments.size(); i++) {
/*      */         
/* 2095 */         Environment environment = (Environment)environments.get(i);
/* 2096 */         if (environment != null) {
/* 2097 */           familyId = environment.getParentFamily().getStructureID();
/*      */         }
/* 2099 */         if (familyId == family.getStructureID()) {
/*      */ 
/*      */           
/* 2102 */           Vector companies = environment.getCompanies();
/* 2103 */           Company company = null;
/*      */ 
/*      */           
/* 2106 */           if (companies != null && companies.size() > 0) {
/* 2107 */             company = (Company)companies.get(0);
/*      */           }
/* 2109 */           CompanyAcl companyAcl = null;
/* 2110 */           if (company != null) {
/* 2111 */             companyAcl = MilestoneHelper.getScreenPermissions(company, user);
/*      */           }
/* 2113 */           if (companyAcl != null && companyAcl.getAccessTemplate() > level)
/*      */           {
/* 2115 */             level = companyAcl.getAccessTemplate();
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 2121 */     return level;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getTemplateNewButtonPermissions(User user) {
/* 2132 */     int level = 0;
/*      */     
/* 2134 */     Vector companies = MilestoneHelper.getUserCompanies(user.getUserId());
/*      */     
/* 2136 */     if (companies != null)
/*      */     {
/* 2138 */       for (int i = 0; i < companies.size(); i++) {
/*      */         
/* 2140 */         Company company = (Company)companies.get(i);
/* 2141 */         if (company != null) {
/*      */           
/* 2143 */           CompanyAcl companyAcl = MilestoneHelper.getScreenPermissions(company, user);
/* 2144 */           if (companyAcl != null && companyAcl.getAccessTemplate() > level)
/*      */           {
/* 2146 */             level = companyAcl.getAccessTemplate();
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/* 2151 */     return level;
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
/*      */ 
/*      */   
/*      */   public void setButtonVisibilities(Template template, User user, Context context, int level, String command) {
/* 2172 */     String copyVisible = "true";
/* 2173 */     String saveVisible = "false";
/* 2174 */     String deleteVisible = "false";
/* 2175 */     String newVisible = "false";
/*      */     
/* 2177 */     if (level > 1) {
/*      */       
/* 2179 */       saveVisible = "true";
/* 2180 */       deleteVisible = "true";
/*      */     } 
/*      */     
/* 2183 */     int newButtonPermission = getTemplateNewButtonPermissions(user);
/*      */     
/* 2185 */     if (newButtonPermission > 1)
/*      */     {
/* 2187 */       newVisible = "true";
/*      */     }
/*      */ 
/*      */     
/* 2191 */     if (command.indexOf("new") > -1 || command.indexOf("copy") > -1) {
/*      */       
/* 2193 */       saveVisible = "true";
/* 2194 */       copyVisible = "false";
/* 2195 */       deleteVisible = "false";
/* 2196 */       newVisible = "false";
/*      */     } 
/*      */ 
/*      */     
/* 2200 */     context.putDelivery("saveVisible", saveVisible);
/*      */     
/* 2202 */     context.putDelivery("copyVisible", copyVisible);
/*      */     
/* 2204 */     context.putDelivery("deleteVisible", deleteVisible);
/*      */     
/* 2206 */     context.putDelivery("newVisible", newVisible);
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
/*      */   private String getJavaScriptTemplateConfig(Context context) {
/* 2218 */     Vector configs = Cache.getSelectionConfigs();
/* 2219 */     Vector prodCats = Cache.getProductCategories();
/*      */     
/* 2221 */     s1 = "function createChildren()\n{\n  lRoot = ";
/* 2222 */     s1 = String.valueOf(s1) + "new Node(0, 'Root',2,[\n";
/*      */     
/* 2224 */     for (int i = 0; i < configs.size(); i++) {
/*      */       
/* 2226 */       SelectionConfiguration config = (SelectionConfiguration)configs.elementAt(i);
/*      */       
/* 2228 */       if (config != null && !config.getInactive()) {
/*      */ 
/*      */         
/* 2231 */         s1 = String.valueOf(s1) + "new Node('" + config.getSelectionConfigurationAbbreviation() + "', '" + config.getSelectionConfigurationName() + "'," + config.getProdType() + ",[\n";
/*      */         
/* 2233 */         Vector subConfigs = config.getSubConfigurations();
/* 2234 */         for (int j = 0; j < subConfigs.size(); j++) {
/*      */           
/* 2236 */           SelectionSubConfiguration subConfig = (SelectionSubConfiguration)subConfigs.elementAt(j);
/* 2237 */           if (subConfig != null) {
/*      */             
/* 2239 */             s1 = String.valueOf(s1) + "new Node('" + subConfig.getSelectionSubConfigurationAbbreviation() + "','" + subConfig.getSelectionSubConfigurationName() + "'," + subConfig.getProdType() + ",[\n";
/*      */ 
/*      */             
/* 2242 */             if (j == subConfigs.size() - 1) {
/*      */ 
/*      */               
/* 2245 */               s1 = String.valueOf(s1) + "])";
/*      */             
/*      */             }
/*      */             else {
/*      */               
/* 2250 */               s1 = String.valueOf(s1) + "]),";
/*      */             } 
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2258 */         if (i == configs.size() - 1) {
/*      */ 
/*      */           
/* 2261 */           s1 = String.valueOf(s1) + "])";
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 2266 */           s1 = String.valueOf(s1) + "]),";
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2273 */     s1 = String.valueOf(s1) + "]);\n }//end function createChildren";
/*      */ 
/*      */ 
/*      */     
/* 2277 */     s1 = String.valueOf(s1) + "\n\nvar productCategories = ";
/* 2278 */     s1 = String.valueOf(s1) + "new Node(0, 'Root',2,[\n";
/*      */     
/* 2280 */     int activeCount = 0;
/* 2281 */     for (int i = 0; i < prodCats.size(); i++) {
/*      */       
/* 2283 */       ProductCategory prod = (ProductCategory)prodCats.elementAt(i);
/* 2284 */       if (prod != null && !prod.getInactive()) {
/* 2285 */         activeCount++;
/*      */       }
/*      */     } 
/* 2288 */     int currentCount = 0;
/* 2289 */     for (int i = 0; i < prodCats.size(); i++) {
/*      */       
/* 2291 */       ProductCategory prod = (ProductCategory)prodCats.elementAt(i);
/*      */       
/* 2293 */       if (prod != null && !prod.getInactive()) {
/*      */ 
/*      */         
/* 2296 */         s1 = String.valueOf(s1) + "new Node('" + prod.getAbbreviation() + "', '" + prod.getName() + "'," + prod.getProdType() + ",[\n";
/*      */ 
/*      */         
/* 2299 */         if (currentCount == activeCount - 1) {
/*      */ 
/*      */           
/* 2302 */           s1 = String.valueOf(s1) + "])";
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 2307 */           s1 = String.valueOf(s1) + "]),";
/*      */         } 
/* 2309 */         currentCount++;
/*      */       } 
/*      */     } 
/*      */     
/* 2313 */     return String.valueOf(s1) + "]);\n ";
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
/*      */   public static Vector sortScreenTaskVector(int sortBy, Vector taskVector) {
/* 2332 */     Vector sortedVector = new Vector();
/* 2333 */     Object[] taskArray = taskVector.toArray();
/*      */     
/* 2335 */     if (taskVector != null) {
/*      */       
/* 2337 */       switch (sortBy) {
/*      */         
/*      */         case 0:
/* 2340 */           Arrays.sort(taskArray, new TaskNameComparator());
/*      */           break;
/*      */         
/*      */         case 1:
/* 2344 */           Arrays.sort(taskArray, new TaskWksToReleaseComparator());
/*      */           break;
/*      */         
/*      */         case 2:
/* 2348 */           Arrays.sort(taskArray, new TaskOwnerComparator());
/*      */           break;
/*      */ 
/*      */         
/*      */         case 3:
/* 2353 */           Arrays.sort(taskArray, new TaskAbbrComparator());
/*      */           break;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2362 */       for (int i = 0; i < taskArray.length; i++) {
/*      */         
/* 2364 */         Task task = (Task)taskArray[i];
/* 2365 */         sortedVector.add(task);
/*      */       } 
/*      */     } 
/*      */     
/* 2369 */     return sortedVector;
/*      */   }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\TemplateHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */