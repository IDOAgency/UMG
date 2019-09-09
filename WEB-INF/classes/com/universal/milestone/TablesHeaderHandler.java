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
/*      */ import com.universal.milestone.Form;
/*      */ import com.universal.milestone.LookupObject;
/*      */ import com.universal.milestone.MilestoneHelper;
/*      */ import com.universal.milestone.MilestoneSecurity;
/*      */ import com.universal.milestone.Notepad;
/*      */ import com.universal.milestone.PrefixCode;
/*      */ import com.universal.milestone.SecureHandler;
/*      */ import com.universal.milestone.Table;
/*      */ import com.universal.milestone.TableManager;
/*      */ import com.universal.milestone.TablesHeaderHandler;
/*      */ import com.universal.milestone.User;
/*      */ import com.universal.milestone.UserManager;
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
/*      */ public class TablesHeaderHandler
/*      */   extends SecureHandler
/*      */ {
/*      */   public static final String COMPONENT_CODE = "hTbH";
/*      */   public GeminiApplication application;
/*      */   public ComponentLog log;
/*      */   
/*      */   public TablesHeaderHandler(GeminiApplication application) {
/*   62 */     this.application = application;
/*   63 */     this.log = application.getLog("hTbH");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   71 */   public String getDescription() { return "Table Header Handler"; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean acceptRequest(Dispatcher dispatcher, Context context, String command) {
/*   81 */     if (super.acceptRequest(dispatcher, context, command))
/*      */     {
/*   83 */       if (command.startsWith("tables"))
/*      */       {
/*   85 */         return handleRequest(dispatcher, context, command);
/*      */       }
/*      */     }
/*   88 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean handleRequest(Dispatcher dispatcher, Context context, String command) {
/*   97 */     if (command.equalsIgnoreCase("tables-header-search"))
/*      */     {
/*   99 */       searchHeader(dispatcher, context, command);
/*      */     }
/*  101 */     if (command.equalsIgnoreCase("tables-detail-search")) {
/*      */       
/*  103 */       searchDetail(dispatcher, context, command);
/*      */     }
/*  105 */     else if (command.equalsIgnoreCase("tables-header-editor")) {
/*      */       
/*  107 */       edit(context);
/*      */     }
/*  109 */     else if (command.equalsIgnoreCase("tables-detail-editor")) {
/*      */       
/*  111 */       editDetail(context);
/*      */     }
/*  113 */     else if (command.equalsIgnoreCase("tables-header-edit-save")) {
/*      */       
/*  115 */       saveHeader(context);
/*      */     }
/*  117 */     else if (command.equalsIgnoreCase("tables-detail-edit-save")) {
/*      */       
/*  119 */       saveDetail(context);
/*      */     }
/*  121 */     else if (command.equalsIgnoreCase("tables-detail-edit-save-new")) {
/*      */       
/*  123 */       saveDetailNew(context);
/*      */     }
/*  125 */     else if (command.equalsIgnoreCase("tables-header-edit-delete")) {
/*      */       
/*  127 */       deleteHeader(context);
/*      */     }
/*  129 */     else if (command.equalsIgnoreCase("tables-detail-edit-delete")) {
/*      */       
/*  131 */       deleteDetail(context);
/*      */     }
/*  133 */     else if (command.equalsIgnoreCase("tables-header-edit-new")) {
/*      */       
/*  135 */       editNewHeader(context);
/*      */     }
/*  137 */     else if (command.equalsIgnoreCase("tables-detail-edit-new")) {
/*      */       
/*  139 */       editNewDetail(context);
/*      */     } 
/*      */     
/*  142 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean saveHeader(Context context) {
/*  150 */     User user = MilestoneSecurity.getUser(context);
/*      */     
/*  152 */     Table table = (Table)context.getSessionValue("Table");
/*      */     
/*  154 */     if (table.getDetail() != null) {
/*      */       
/*  156 */       Notepad notepad = getTableNotepad(context, user.getUserId());
/*  157 */       MilestoneHelper.putNotepadIntoSession(notepad, context);
/*  158 */       context.removeSessionValue(NOTEPAD_SESSION_NAMES[18]);
/*      */       
/*  160 */       Form form = buildForm(context, table);
/*  161 */       if (table.getName().equals("Prefix Code")) {
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
/*  172 */         Vector environments = Cache.getInstance().getEnvironments();
/*  173 */         FormDropDownMenu environmentsDropDown = MilestoneHelper.getCorporateStructureDropDown("environments", environments, table.getSubDetail().getAbbreviation(), true, false);
/*  174 */         form.addElement(environmentsDropDown);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  180 */       if (table.getName().equals("Sub-Format")) {
/*      */ 
/*      */         
/*  183 */         boolean parentChecked = false;
/*  184 */         if (table.getSubDetail() != null && table.getSubDetail().getName().equalsIgnoreCase("Y"))
/*      */         {
/*  186 */           parentChecked = true;
/*      */         }
/*  188 */         FormCheckBox Parent = new FormCheckBox("SubConfiguration", "SubConfiguratioin", "", false);
/*  189 */         Parent.setChecked(parentChecked);
/*  190 */         Parent.setId("SubConfiguration");
/*  191 */         form.addElement(Parent);
/*      */ 
/*      */ 
/*      */         
/*  195 */         String configValue = "";
/*  196 */         if (table.getSubDetail() != null)
/*  197 */           configValue = TableManager.getInstance().getAssocConfig(3, table.getSubDetail().getDetValue()); 
/*  198 */         FormDropDownMenu configuration = MilestoneHelper.getSelectionConfigurationDropDown("configuration", configValue, true);
/*  199 */         form.addElement(configuration);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  204 */       if (table == null || (table != null && TableManager.getInstance().isTimestampValid(table))) {
/*      */ 
/*      */         
/*  207 */         form.setValues(context);
/*      */ 
/*      */         
/*  210 */         String descriptionString = form.getStringValue("Description");
/*      */ 
/*      */         
/*  213 */         String subdescriptionString = form.getStringValue("SubDescription");
/*      */ 
/*      */         
/*  216 */         String environmentString = "";
/*      */         
/*  218 */         if (form.getStringValue("environments") != null)
/*      */         {
/*  220 */           environmentString = form.getStringValue("environments");
/*      */         }
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
/*  232 */         String assocConfig = "";
/*  233 */         String parent = "";
/*      */         
/*  235 */         if (form.getStringValue("SubConfiguration") != null) {
/*      */           
/*  237 */           if (((FormCheckBox)form.getElement("SubConfiguration")).isChecked()) {
/*      */             
/*  239 */             parent = "Y";
/*      */           }
/*      */           else {
/*      */             
/*  243 */             parent = "N";
/*      */           } 
/*      */           
/*  246 */           assocConfig = form.getStringValue("configuration");
/*      */         } 
/*      */ 
/*      */         
/*  250 */         LookupObject detail = table.getDetail();
/*  251 */         PrefixCode subdetail = table.getSubDetail();
/*      */ 
/*      */ 
/*      */         
/*  255 */         boolean isThereSubDetail = false;
/*      */         
/*  257 */         if (table.getSubDetail().getName() != null || 
/*  258 */           table.getSubDetail().getAbbreviation() != null || 
/*  259 */           table.getSubDetail().getDetValue() != null) {
/*      */           
/*  261 */           isThereSubDetail = true;
/*      */         }
/*      */         else {
/*      */           
/*  265 */           isThereSubDetail = false;
/*      */         } 
/*      */         
/*  268 */         if (form.getStringValue("SubConfiguration") != null) {
/*  269 */           isThereSubDetail = true;
/*      */         }
/*      */         
/*  272 */         detail.setName(descriptionString);
/*      */ 
/*      */         
/*  275 */         detail.setInactive(((FormCheckBox)form.getElement("inactive")).isChecked());
/*      */ 
/*      */         
/*  278 */         if (isThereSubDetail && form.getElement("subdetinactive") != null) {
/*  279 */           subdetail.setInactive(((FormCheckBox)form.getElement("subdetinactive")).isChecked());
/*      */         }
/*      */         
/*  282 */         if (!environmentString.equals("")) {
/*      */           
/*  284 */           subdetail.setAbbreviation(environmentString);
/*      */         }
/*      */         else {
/*      */           
/*  288 */           subdetail.setAbbreviation(detail.getName());
/*      */         } 
/*  290 */         subdetail.setDetValue(detail.getAbbreviation());
/*  291 */         if (!parent.equals("")) {
/*      */           
/*  293 */           subdetail.setName(parent);
/*  294 */           subdetail.setAbbreviation("Parent");
/*      */         }
/*      */         else {
/*      */           
/*  298 */           subdetail.setName(subdescriptionString);
/*      */         } 
/*      */         
/*  301 */         Table savedTable = new Table();
/*      */         
/*  303 */         boolean isNew = false;
/*  304 */         if (!form.isUnchanged())
/*      */         {
/*  306 */           FormValidation formValidation = form.validate();
/*  307 */           if (formValidation.isGood())
/*      */           {
/*      */             
/*  310 */             if (subdetail.getAbbreviation() != null && subdetail.getName() != null) {
/*      */               
/*  312 */               table.setDetail(detail);
/*  313 */               table.setSubDetail(subdetail);
/*      */               
/*  315 */               savedTable = TableManager.getInstance().saveTableDetail(table, user.getUserId(), isThereSubDetail, assocConfig);
/*  316 */               if (savedTable == null) {
/*  317 */                 savedTable = new Table();
/*      */               }
/*      */             } else {
/*      */               
/*  321 */               table.setDetail(detail);
/*  322 */               table.setSubDetail(subdetail);
/*      */               
/*  324 */               String detailString = (detail != null) ? detail.getAbbreviation() : "";
/*      */               
/*  326 */               if (!TableManager.getInstance().isDuplicate(table.getId(), detailString)) {
/*      */                 
/*  328 */                 savedTable = TableManager.getInstance().insertTableDetail(table, user.getUserId(), assocConfig);
/*  329 */                 isNew = true;
/*      */               
/*      */               }
/*      */               else {
/*      */                 
/*  334 */                 context.putDelivery("AlertMessage", "The record has been inserted by another user.\\nTo resubmit your information and view this web page, click the Refresh button.\\nChanges can be printed before refreshing to assist in entering data after refresh.");
/*  335 */                 context.putDelivery("Form", form);
/*  336 */                 return context.includeJSP("tables-header-editor.jsp");
/*      */               } 
/*      */             } 
/*      */ 
/*      */ 
/*      */             
/*  342 */             Cache.flushTableVariables(String.valueOf(table.getId()));
/*      */ 
/*      */             
/*  345 */             FormElement lastUpdated = form.getElement("lastupdateddate");
/*  346 */             if (savedTable.getLastUpdateDate() != null) {
/*  347 */               lastUpdated.setValue(MilestoneHelper.getLongDate(savedTable.getLastUpdateDate()));
/*      */             }
/*  349 */             context.putSessionValue("Table", savedTable);
/*      */             
/*  351 */             context.putDelivery("Form", form);
/*      */ 
/*      */             
/*  354 */             notepad.setAllContents(null);
/*      */             
/*  356 */             if (isNew) {
/*  357 */               notepad.newSelectedReset();
/*      */             }
/*  359 */             notepad = getTableNotepad(context, user.getUserId());
/*      */ 
/*      */ 
/*      */           
/*      */           }
/*      */           else
/*      */           {
/*      */ 
/*      */ 
/*      */             
/*  369 */             context.putDelivery("FormValidation", formValidation);
/*  370 */             context.putDelivery("Form", form);
/*  371 */             return context.includeJSP("tables-header-editor.jsp");
/*      */           }
/*      */         
/*      */         }
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  379 */         context.putDelivery("AlertMessage", "The record is unavailable for editing at this time.  Please refresh your screen and edit it at a later time.");
/*      */       } 
/*  381 */       context.putDelivery("Form", form);
/*  382 */       return edit(context);
/*      */     } 
/*      */ 
/*      */     
/*  386 */     Form form = buildNewHeaderForm(context);
/*  387 */     form.addElement(new FormHidden("cmd", "tables-header-edit-save"));
/*      */     
/*  389 */     Notepad notepad = getTableNotepad(context, user.getUserId());
/*  390 */     Table selected = (Table)notepad.getSelected();
/*      */     
/*  392 */     if (selected.getName().equals("Prefix Code")) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  402 */       Vector environments = Cache.getInstance().getEnvironments();
/*  403 */       FormDropDownMenu environmentsDropDown = MilestoneHelper.getCorporateStructureDropDown("environments", environments, "0", true, false);
/*  404 */       form.addElement(environmentsDropDown);
/*      */     } 
/*      */ 
/*      */     
/*  408 */     String assocConfig = "";
/*      */     
/*  410 */     form.setValues(context);
/*      */ 
/*      */ 
/*      */     
/*  414 */     table.setId(selected.getId());
/*      */ 
/*      */     
/*  417 */     String valueString = form.getStringValue("Value");
/*      */ 
/*      */ 
/*      */     
/*  421 */     String descriptionString = form.getStringValue("Description");
/*      */ 
/*      */     
/*  424 */     String subdescriptionString = form.getStringValue("SubDescription");
/*      */ 
/*      */     
/*  427 */     String environmentString = "";
/*      */     
/*  429 */     if (form.getStringValue("environments") != null)
/*      */     {
/*  431 */       environmentString = form.getStringValue("environments");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  442 */     LookupObject detail = new LookupObject();
/*  443 */     PrefixCode subdetail = new PrefixCode();
/*      */     
/*  445 */     detail.setAbbreviation(valueString);
/*  446 */     detail.setName(descriptionString);
/*  447 */     subdetail.setName(subdescriptionString);
/*      */ 
/*      */     
/*  450 */     if (!environmentString.equals("")) {
/*      */       
/*  452 */       subdetail.setAbbreviation(environmentString);
/*      */     }
/*      */     else {
/*      */       
/*  456 */       subdetail.setAbbreviation(detail.getName());
/*      */     } 
/*  458 */     subdetail.setDetValue(detail.getAbbreviation());
/*      */ 
/*      */     
/*  461 */     detail.setInactive(((FormCheckBox)form.getElement("inactive")).isChecked());
/*      */ 
/*      */     
/*  464 */     if (form.getElement("subdetinactive") != null) {
/*  465 */       subdetail.setInactive(((FormCheckBox)form.getElement("subdetinactive")).isChecked());
/*      */     }
/*  467 */     table.setDetail(detail);
/*  468 */     table.setSubDetail(subdetail);
/*      */     
/*  470 */     if (!form.isUnchanged()) {
/*      */       
/*  472 */       FormValidation formValidation = form.validate();
/*  473 */       if (formValidation.isGood()) {
/*      */         
/*  475 */         String detailString = (detail != null) ? detail.getAbbreviation() : "";
/*      */         
/*  477 */         if (!TableManager.getInstance().isDuplicate(table.getId(), detailString))
/*      */         {
/*  479 */           Table savedTable = TableManager.getInstance().insertTableDetail(table, user.getUserId(), assocConfig);
/*      */ 
/*      */ 
/*      */           
/*  483 */           Cache.flushTableVariables(String.valueOf(table.getId()));
/*      */ 
/*      */           
/*  486 */           FormElement lastUpdated = form.getElement("LastUpdatedDate");
/*      */ 
/*      */ 
/*      */           
/*  490 */           context.putSessionValue("Table", savedTable);
/*      */           
/*  492 */           context.putDelivery("Form", form);
/*      */           
/*  494 */           if (notepad.getAllContents() == null || notepad.getAllContents().size() < 1) {
/*  495 */             notepad.setSearchQuery("");
/*      */           }
/*      */           
/*  498 */           notepad.setAllContents(null);
/*  499 */           notepad.newSelectedReset();
/*  500 */           notepad = getTableNotepad(context, user.getUserId());
/*  501 */           notepad.goToSelectedPage();
/*      */         }
/*      */         else
/*      */         {
/*  505 */           context.putDelivery("AlertMessage", "The record has been inserted by another user.\\nTo resubmit your information and view this web page, click the Refresh button.\\nChanges can be printed before refreshing to assist in entering data after refresh.");
/*  506 */           form.addElement(new FormHidden("OrderBy", "", true));
/*  507 */           form.addElement(new FormHidden("cmd", "tables-header-edit-new"));
/*  508 */           context.putDelivery("Form", form);
/*  509 */           return context.includeJSP("tables-header-editor.jsp");
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/*  514 */         context.putDelivery("FormValidation", formValidation);
/*  515 */         form.addElement(new FormHidden("OrderBy", "", true));
/*  516 */         form.addElement(new FormHidden("cmd", "tables-header-edit-new"));
/*  517 */         context.putDelivery("Form", form);
/*  518 */         return context.includeJSP("tables-header-editor.jsp");
/*      */       } 
/*      */     } 
/*      */     
/*  522 */     return edit(context);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean deleteHeader(Context context) {
/*  528 */     User user = MilestoneSecurity.getUser(context);
/*      */     
/*  530 */     Notepad notepad = getTableNotepad(context, user.getUserId());
/*  531 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*  532 */     context.removeSessionValue(NOTEPAD_SESSION_NAMES[18]);
/*      */     
/*  534 */     Table table = MilestoneHelper.getScreenTable(context);
/*      */     
/*  536 */     TableManager.getInstance().deleteTable(table, user.getUserId());
/*      */ 
/*      */ 
/*      */     
/*  540 */     Cache.flushTableVariables(String.valueOf(table.getId()));
/*      */ 
/*      */     
/*  543 */     notepad.setAllContents(null);
/*  544 */     notepad = getTableNotepad(context, user.getUserId());
/*  545 */     notepad.goToSelectedPage();
/*      */     
/*  547 */     return edit(context);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean deleteDetail(Context context) {
/*  553 */     User user = MilestoneSecurity.getUser(context);
/*      */     
/*  555 */     Notepad notepad = getTableNotepad(context, user.getUserId());
/*  556 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*  557 */     Table selected = (Table)notepad.getSelected();
/*      */     
/*  559 */     Notepad notepadDetail = getTableDetailNotepad(context, selected.getId());
/*  560 */     MilestoneHelper.putNotepadIntoSession(notepadDetail, context);
/*      */     
/*  562 */     Table tableDetail = MilestoneHelper.getScreenTableDetail(context);
/*      */     
/*  564 */     TableManager.getInstance().deleteTableDetail(tableDetail, user.getUserId());
/*      */ 
/*      */ 
/*      */     
/*  568 */     Cache.flushTableVariables(String.valueOf(tableDetail.getSubDetail().getId()));
/*      */ 
/*      */     
/*  571 */     notepadDetail.setAllContents(null);
/*  572 */     notepadDetail = getTableDetailNotepad(context, selected.getId());
/*  573 */     notepadDetail.setSelected(null);
/*      */     
/*  575 */     return editDetail(context);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean saveDetail(Context context) {
/*  583 */     User user = MilestoneSecurity.getUser(context);
/*      */     
/*  585 */     Notepad notepad = getTableNotepad(context, user.getUserId());
/*  586 */     Table selected = (Table)context.getSessionValue("Table");
/*      */ 
/*      */     
/*  589 */     Notepad notepadDetail = getTableDetailNotepad(context, selected.getId());
/*  590 */     MilestoneHelper.putNotepadIntoSession(notepadDetail, context);
/*      */     
/*  592 */     Table tableDetail = MilestoneHelper.getScreenTableDetail(context);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  601 */     if (selected != null && tableDetail.getDetail() != null) {
/*      */       
/*  603 */       Form form = buildDetailForm(context, tableDetail);
/*  604 */       if (selected.getName().equals("Prefix Code")) {
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
/*  615 */         Vector environments = Cache.getInstance().getEnvironments();
/*  616 */         FormDropDownMenu environmentsDropDown = MilestoneHelper.getCorporateStructureDropDown("environments", environments, tableDetail.getSubDetail().getDetValue(), true, false);
/*  617 */         form.addElement(environmentsDropDown);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  622 */       if (selected.getName().equals("Sub-Format")) {
/*      */ 
/*      */         
/*  625 */         boolean parentChecked = false;
/*      */         
/*  627 */         if (selected.getSubDetail() != null && selected.getSubDetail().getName().equalsIgnoreCase("Y"))
/*      */         {
/*  629 */           parentChecked = true;
/*      */         }
/*  631 */         FormCheckBox Parent = new FormCheckBox("SubConfiguration", "SubConfiguration", "", false);
/*  632 */         Parent.setChecked(parentChecked);
/*  633 */         Parent.setId("SubConfiguration");
/*  634 */         form.addElement(Parent);
/*      */ 
/*      */ 
/*      */         
/*  638 */         String configValue = "";
/*  639 */         if (selected.getSubDetail() != null)
/*  640 */           configValue = TableManager.getInstance().getAssocConfig(3, selected.getSubDetail().getDetValue()); 
/*  641 */         FormDropDownMenu configuration = MilestoneHelper.getSelectionConfigurationDropDown("configuration", configValue, true);
/*  642 */         form.addElement(configuration);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  647 */       Table timestampTable = (Table)context.getSessionValue("TableDetail");
/*  648 */       if (timestampTable == null || (timestampTable != null && TableManager.getInstance().isTimestampValid(timestampTable))) {
/*      */ 
/*      */         
/*  651 */         form.setValues(context);
/*      */ 
/*      */         
/*  654 */         String descriptionString = form.getStringValue("Description");
/*      */ 
/*      */         
/*  657 */         String subdescriptionString = form.getStringValue("SubDescription");
/*      */ 
/*      */         
/*  660 */         String environmentString = "";
/*      */         
/*  662 */         if (form.getStringValue("environments") != null)
/*      */         {
/*  664 */           environmentString = form.getStringValue("environments");
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  675 */         String assocConfig = "";
/*  676 */         String parent = "";
/*      */         
/*  678 */         if (form.getStringValue("SubConfiguration") != null) {
/*      */           
/*  680 */           if (((FormCheckBox)form.getElement("SubConfiguration")).isChecked()) {
/*      */             
/*  682 */             parent = "Y";
/*      */           }
/*      */           else {
/*      */             
/*  686 */             parent = "N";
/*      */           } 
/*      */ 
/*      */           
/*  690 */           assocConfig = form.getStringValue("configuration");
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  695 */         LookupObject detail = tableDetail.getDetail();
/*  696 */         PrefixCode subdetail = tableDetail.getSubDetail();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  704 */         boolean isThereSubDetail = false;
/*  705 */         if (tableDetail.getSubDetail().getName() != null || 
/*  706 */           tableDetail.getSubDetail().getAbbreviation() != null || 
/*  707 */           tableDetail.getSubDetail().getDetValue() != null) {
/*      */           
/*  709 */           isThereSubDetail = true;
/*      */         }
/*      */         else {
/*      */           
/*  713 */           isThereSubDetail = false;
/*      */         } 
/*  715 */         detail.setName(descriptionString);
/*      */ 
/*      */         
/*  718 */         detail.setInactive(((FormCheckBox)form.getElement("inactive")).isChecked());
/*      */ 
/*      */         
/*  721 */         if (isThereSubDetail && form.getElement("subdetinactive") != null) {
/*  722 */           subdetail.setInactive(((FormCheckBox)form.getElement("subdetinactive")).isChecked());
/*      */         }
/*      */         
/*  725 */         if (selected.getId() == 3 || selected.getId() == 1 || selected.getId() == 29) {
/*      */           
/*  727 */           if (form.getElement("prodType") != null) {
/*  728 */             detail.setProdType(Integer.parseInt(form.getStringValue("prodType")));
/*      */           }
/*      */           
/*  731 */           if (selected.getId() == 29)
/*      */           {
/*  733 */             if (form.getElement("isDigitalEquivalent") != null) {
/*  734 */               detail.setIsDigitalEquivalent(((FormCheckBox)form.getElement("isDigitalEquivalent")).isChecked());
/*      */             }
/*      */           }
/*      */         } 
/*      */         
/*  739 */         if (!environmentString.equals("")) {
/*      */           
/*  741 */           subdetail.setAbbreviation(environmentString);
/*      */ 
/*      */         
/*      */         }
/*  745 */         else if (selected.getId() == 22) {
/*  746 */           subdetail.setAbbreviation("Abbrev");
/*      */         } else {
/*  748 */           subdetail.setAbbreviation(detail.getName());
/*      */         } 
/*  750 */         subdetail.setDetValue(detail.getAbbreviation());
/*  751 */         if (!parent.equals("")) {
/*      */           
/*  753 */           subdetail.setName(parent);
/*  754 */           subdetail.setAbbreviation("Parent");
/*      */         }
/*      */         else {
/*      */           
/*  758 */           subdetail.setName(subdescriptionString);
/*      */         } 
/*      */         
/*  761 */         tableDetail.setDetail(detail);
/*  762 */         tableDetail.setSubDetail(subdetail);
/*      */         
/*  764 */         if (!form.isUnchanged()) {
/*      */           
/*  766 */           FormValidation formValidation = form.validate();
/*  767 */           if (formValidation.isGood())
/*      */           {
/*  769 */             Table savedTable = TableManager.getInstance().saveTableDetail(tableDetail, user.getUserId(), isThereSubDetail, assocConfig);
/*      */ 
/*      */ 
/*      */             
/*  773 */             Cache.flushTableVariables(String.valueOf(tableDetail.getId()));
/*      */ 
/*      */             
/*  776 */             FormElement lastUpdated = form.getElement("lastupdateddate");
/*  777 */             if (savedTable.getLastUpdateDate() != null) {
/*  778 */               lastUpdated.setValue(MilestoneHelper.getLongDate(savedTable.getLastUpdateDate()));
/*      */             }
/*  780 */             context.putSessionValue("TableDetail", savedTable);
/*      */             
/*  782 */             context.putDelivery("Form", form);
/*      */ 
/*      */             
/*  785 */             notepad.setAllContents(null);
/*  786 */             notepad = getTableNotepad(context, user.getUserId());
/*  787 */             notepad.goToSelectedPage();
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  792 */             notepadDetail.setAllContents(null);
/*      */             
/*  794 */             notepadDetail = getTableDetailNotepad(context, selected.getId());
/*  795 */             notepadDetail.goToSelectedPage();
/*  796 */             notepadDetail.setSelected(savedTable);
/*      */           
/*      */           }
/*      */           else
/*      */           {
/*  801 */             context.putDelivery("FormValidation", formValidation);
/*  802 */             context.putDelivery("Form", form);
/*  803 */             return context.includeJSP("tables-detail-editor.jsp");
/*      */           }
/*      */         
/*      */         } 
/*      */       } else {
/*      */         
/*  809 */         context.putDelivery("AlertMessage", "The record is unavailable for editing at this time.  Please refresh your screen and edit it at a later time.");
/*      */       } 
/*  811 */       context.putDelivery("Form", form);
/*      */     } 
/*  813 */     return editDetail(context);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean saveDetailNew(Context context) {
/*  821 */     User user = MilestoneSecurity.getUser(context);
/*      */     
/*  823 */     Notepad notepad = getTableNotepad(context, user.getUserId());
/*  824 */     Table selected = (Table)context.getSessionValue("Table");
/*      */ 
/*      */     
/*  827 */     Notepad notepadDetail = getTableDetailNotepad(context, selected.getId());
/*  828 */     MilestoneHelper.putNotepadIntoSession(notepadDetail, context);
/*      */     
/*  830 */     Table tableDetail = MilestoneHelper.getScreenTableDetail(context);
/*      */     
/*  832 */     Form form = buildNewDetailForm(context);
/*  833 */     if (selected.getName().equals("Prefix Code")) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  842 */       Vector environments = Cache.getInstance().getEnvironments();
/*  843 */       FormDropDownMenu environmentsDropDown = MilestoneHelper.getCorporateStructureDropDown("environments", environments, "0", true, false);
/*  844 */       form.addElement(environmentsDropDown);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  852 */     if (selected.getName().equals("Sub-Format")) {
/*      */ 
/*      */       
/*  855 */       boolean parentChecked = false;
/*      */       
/*  857 */       if (selected.getSubDetail() != null && selected.getSubDetail().getName().equalsIgnoreCase("Y"))
/*      */       {
/*  859 */         parentChecked = true;
/*      */       }
/*  861 */       FormCheckBox Parent = new FormCheckBox("SubConfiguration", "SubConfiguration", "", false);
/*  862 */       Parent.setChecked(parentChecked);
/*  863 */       Parent.setId("SubConfiguration");
/*  864 */       form.addElement(Parent);
/*      */ 
/*      */ 
/*      */       
/*  868 */       String configValue = "";
/*  869 */       if (selected.getSubDetail() != null)
/*  870 */         configValue = TableManager.getInstance().getAssocConfig(3, selected.getSubDetail().getDetValue()); 
/*  871 */       FormDropDownMenu configuration = MilestoneHelper.getSelectionConfigurationDropDown("configuration", configValue, true);
/*  872 */       form.addElement(configuration);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  877 */     form.setValues(context);
/*      */ 
/*      */ 
/*      */     
/*  881 */     tableDetail.setId(selected.getId());
/*      */ 
/*      */     
/*  884 */     String valueString = form.getStringValue("Value");
/*      */ 
/*      */     
/*  887 */     String descriptionString = form.getStringValue("Description");
/*      */ 
/*      */     
/*  890 */     String subdescriptionString = form.getStringValue("SubDescription");
/*      */ 
/*      */     
/*  893 */     String environmentString = "";
/*      */     
/*  895 */     if (form.getStringValue("environments") != null)
/*      */     {
/*  897 */       environmentString = form.getStringValue("environments");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  908 */     String assocConfig = "";
/*  909 */     String parent = "";
/*      */     
/*  911 */     if (form.getStringValue("SubConfiguration") != null) {
/*      */       
/*  913 */       if (((FormCheckBox)form.getElement("SubConfiguration")).isChecked()) {
/*      */         
/*  915 */         parent = "Y";
/*      */       }
/*      */       else {
/*      */         
/*  919 */         parent = "N";
/*      */       } 
/*      */ 
/*      */       
/*  923 */       assocConfig = form.getStringValue("configuration");
/*      */     } 
/*      */     
/*  926 */     LookupObject detail = new LookupObject();
/*  927 */     PrefixCode subdetail = new PrefixCode();
/*      */     
/*  929 */     detail.setAbbreviation(valueString);
/*  930 */     detail.setName(descriptionString);
/*      */     
/*  932 */     detail.setInactive(((FormCheckBox)form.getElement("inactive")).isChecked());
/*      */ 
/*      */     
/*  935 */     if (form.getElement("subdetinactive") != null) {
/*  936 */       subdetail.setInactive(((FormCheckBox)form.getElement("subdetinactive")).isChecked());
/*      */     }
/*      */     
/*  939 */     if (selected.getId() == 3 || selected.getId() == 1 || selected.getId() == 29) {
/*      */       
/*  941 */       if (form.getElement("prodType") != null) {
/*  942 */         detail.setProdType(Integer.parseInt(form.getStringValue("prodType")));
/*      */       }
/*      */       
/*  945 */       if (selected.getId() == 29)
/*      */       {
/*  947 */         if (form.getElement("isDigitalEquivalent") != null) {
/*  948 */           detail.setIsDigitalEquivalent(((FormCheckBox)form.getElement("isDigitalEquivalent")).isChecked());
/*      */         }
/*      */       }
/*      */     } 
/*      */     
/*  953 */     if (!parent.equals("")) {
/*      */       
/*  955 */       subdetail.setName(parent);
/*  956 */       subdetail.setAbbreviation("Parent");
/*      */     }
/*      */     else {
/*      */       
/*  960 */       subdetail.setName(subdescriptionString);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  965 */     if (!environmentString.equals("")) {
/*      */       
/*  967 */       subdetail.setAbbreviation(environmentString);
/*      */     }
/*      */     else {
/*      */       
/*  971 */       subdetail.setAbbreviation(detail.getName());
/*      */     } 
/*  973 */     subdetail.setDetValue(detail.getAbbreviation());
/*      */     
/*  975 */     tableDetail.setDetail(detail);
/*  976 */     tableDetail.setSubDetail(subdetail);
/*      */     
/*  978 */     if (!form.isUnchanged()) {
/*      */       
/*  980 */       FormValidation formValidation = form.validate();
/*  981 */       if (formValidation.isGood()) {
/*      */         
/*  983 */         String detailString = (detail != null) ? detail.getAbbreviation() : "";
/*  984 */         if (!TableManager.getInstance().isDuplicate(tableDetail.getId(), detailString))
/*      */         {
/*  986 */           Table savedTable = TableManager.getInstance().insertTableDetail(tableDetail, user.getUserId(), assocConfig);
/*      */ 
/*      */ 
/*      */           
/*  990 */           Cache.flushTableVariables(String.valueOf(tableDetail.getId()));
/*      */ 
/*      */           
/*  993 */           FormElement lastUpdated = form.getElement("LastUpdatedDate");
/*      */           
/*  995 */           context.putSessionValue("TableDetail", savedTable);
/*      */           
/*  997 */           context.putDelivery("Form", form);
/*      */ 
/*      */           
/* 1000 */           notepadDetail.setAllContents(null);
/* 1001 */           notepadDetail.newSelectedReset();
/* 1002 */           notepadDetail = getTableDetailNotepad(context, selected.getId());
/* 1003 */           notepadDetail.goToSelectedPage();
/* 1004 */           notepadDetail.setSelected(savedTable);
/* 1005 */           form.addElement(new FormHidden("cmd", "tables-detail-edit-save"));
/*      */         
/*      */         }
/*      */         else
/*      */         {
/* 1010 */           context.putDelivery("AlertMessage", "The record has been inserted by another user.\\nTo resubmit your information and view this web page, click the Refresh button.\\nChanges can be printed before refreshing to assist in entering data after refresh.");
/* 1011 */           form.addElement(new FormHidden("OrderBy", "", true));
/* 1012 */           form.addElement(new FormHidden("cmd", "tables-detail-edit-save"));
/* 1013 */           context.putDelivery("Form", form);
/* 1014 */           return context.includeJSP("tables-detail-editor.jsp");
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 1019 */         context.putDelivery("FormValidation", formValidation);
/* 1020 */         form.addElement(new FormHidden("OrderBy", "", true));
/* 1021 */         form.addElement(new FormHidden("cmd", "tables-detail-edit-new"));
/* 1022 */         context.putDelivery("Form", form);
/* 1023 */         return context.includeJSP("tables-detail-editor.jsp");
/*      */       } 
/*      */     } 
/* 1026 */     return editDetail(context);
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
/*      */   private boolean searchHeader(Dispatcher dispatcher, Context context, String command) {
/* 1038 */     Notepad notepad = MilestoneHelper.getNotepadFromSession(14, context);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1043 */     notepad.setAllContents(null);
/* 1044 */     notepad.setSelected(null);
/*      */     
/* 1046 */     TableManager.getInstance().setNotepadQuery(context, MilestoneSecurity.getUser(context).getUserId(), notepad);
/* 1047 */     dispatcher.redispatch(context, "tables-header-editor");
/*      */     
/* 1049 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean searchDetail(Dispatcher dispatcher, Context context, String command) {
/* 1058 */     Notepad notepadHeader = MilestoneHelper.getNotepadFromSession(14, context);
/* 1059 */     Notepad notepad = MilestoneHelper.getNotepadFromSession(18, context);
/*      */     
/* 1061 */     Table selected = null;
/* 1062 */     Table selectedHeader = (Table)notepadHeader.getSelected();
/* 1063 */     int fieldId = -1;
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1068 */       selected = (Table)notepad.getSelected();
/* 1069 */       if (selected != null)
/* 1070 */       { fieldId = selected.getId(); }
/*      */       else
/* 1072 */       { fieldId = selectedHeader.getId(); } 
/* 1073 */     } catch (Exception ex) {
/* 1074 */       if (selectedHeader != null) {
/* 1075 */         fieldId = selectedHeader.getId();
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1080 */     notepad.setAllContents(null);
/* 1081 */     notepad.setSelected(null);
/*      */     
/* 1083 */     TableManager.getInstance().setDetailNotepadQuery(context, fieldId, notepad);
/* 1084 */     dispatcher.redispatch(context, "tables-detail-editor");
/*      */     
/* 1086 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1096 */   private boolean searchResults(Dispatcher dispatcher, Context context, String command) { return context.includeJSP("tables-header-search-results.jsp"); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Notepad getTableNotepad(Context context, int userId) {
/* 1106 */     Vector contents = new Vector();
/*      */     
/* 1108 */     if (MilestoneHelper.getNotepadFromSession(14, context) != null) {
/*      */       
/* 1110 */       Notepad notepad = MilestoneHelper.getNotepadFromSession(14, context);
/*      */       
/* 1112 */       if (notepad.getAllContents() == null) {
/*      */         
/* 1114 */         contents = TableManager.getInstance().getTableNotepadList(userId, notepad);
/* 1115 */         notepad.setAllContents(contents);
/*      */       } 
/*      */       
/* 1118 */       return notepad;
/*      */     } 
/*      */ 
/*      */     
/* 1122 */     String[] columnNames = { "Description" };
/* 1123 */     contents = TableManager.getInstance().getTableNotepadList(userId, null);
/* 1124 */     return new Notepad(contents, 0, 15, "Tables", 14, columnNames);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Notepad getTableDetailNotepad(Context context, int fieldID) {
/* 1135 */     Vector contents = new Vector();
/*      */     
/* 1137 */     if (MilestoneHelper.getNotepadFromSession(18, context) != null) {
/*      */       
/* 1139 */       Notepad notepad = MilestoneHelper.getNotepadFromSession(18, context);
/*      */       
/* 1141 */       if (notepad.getAllContents() == null) {
/*      */         
/* 1143 */         contents = TableManager.getInstance().getTableDetailNotepadList(fieldID, notepad);
/* 1144 */         notepad.setAllContents(contents);
/*      */       } 
/*      */       
/* 1147 */       return notepad;
/*      */     } 
/*      */ 
/*      */     
/* 1151 */     String[] columnNames = { "Description" };
/* 1152 */     contents = TableManager.getInstance().getTableDetailNotepadList(fieldID, null);
/* 1153 */     return new Notepad(contents, 0, 15, "Table", 18, columnNames);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean edit(Context context) {
/* 1161 */     User user = MilestoneSecurity.getUser(context);
/*      */     
/* 1163 */     Notepad notepad = getTableNotepad(context, user.getUserId());
/* 1164 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/* 1165 */     context.removeSessionValue(NOTEPAD_SESSION_NAMES[18]);
/*      */     
/* 1167 */     Table table = MilestoneHelper.getScreenTable(context);
/* 1168 */     if (table != null) {
/*      */       
/* 1170 */       if (table.getDetail() != null && table.getDetail().getId() > 0) {
/*      */         
/* 1172 */         Form form = buildForm(context, table);
/* 1173 */         if (table.getName().equals("Prefix Code")) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1182 */           Vector environments = Cache.getInstance().getEnvironments();
/* 1183 */           FormDropDownMenu environmentsDropDown = MilestoneHelper.getCorporateStructureDropDown("environments", environments, table.getSubDetail().getAbbreviation(), true, false);
/* 1184 */           form.addElement(environmentsDropDown);
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1189 */         if (table.getName().equals("Sub-Format")) {
/*      */ 
/*      */           
/* 1192 */           boolean parentChecked = false;
/*      */           
/* 1194 */           if (table.getSubDetail().getName().equalsIgnoreCase("Y"))
/*      */           {
/* 1196 */             parentChecked = true;
/*      */           }
/* 1198 */           FormCheckBox Parent = new FormCheckBox("SubConfiguration", "SubConfiguration", "", false);
/* 1199 */           Parent.setChecked(parentChecked);
/* 1200 */           Parent.setId("SubConfiguration");
/* 1201 */           form.addElement(Parent);
/*      */ 
/*      */ 
/*      */           
/* 1205 */           String configValue = "";
/* 1206 */           if (table.getSubDetail() != null)
/* 1207 */             configValue = TableManager.getInstance().getAssocConfig(3, table.getSubDetail().getDetValue()); 
/* 1208 */           FormDropDownMenu configuration = MilestoneHelper.getSelectionConfigurationDropDown("configuration", configValue, true);
/* 1209 */           form.addElement(configuration);
/*      */         } 
/*      */         
/* 1212 */         context.putSessionValue("Table", table);
/* 1213 */         context.putDelivery("Form", form);
/* 1214 */         return context.includeJSP("tables-header-editor.jsp");
/*      */       } 
/*      */ 
/*      */       
/* 1218 */       return editNewHeader(context);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1225 */     Vector contents = null;
/*      */     
/* 1227 */     contents = notepad.getAllContents();
/*      */     
/* 1229 */     notepad.setSwitchToDetailVisible(false);
/*      */     
/* 1231 */     if (contents != null && contents.size() > 0) {
/*      */ 
/*      */       
/* 1234 */       Form form = buildNewHeaderForm(context);
/* 1235 */       if (table.getName().equals("Prefix Code")) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1245 */         Vector environments = Cache.getInstance().getEnvironments();
/* 1246 */         FormDropDownMenu environmentsDropDown = MilestoneHelper.getCorporateStructureDropDown("environments", environments, table.getSubDetail().getAbbreviation(), true, false);
/* 1247 */         form.addElement(environmentsDropDown);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1252 */       if (table.getName().equals("Sub-Format")) {
/*      */ 
/*      */         
/* 1255 */         boolean parentChecked = false;
/*      */         
/* 1257 */         if (table.getSubDetail().getName().equalsIgnoreCase("Y"))
/*      */         {
/* 1259 */           parentChecked = true;
/*      */         }
/* 1261 */         FormCheckBox Parent = new FormCheckBox("SubConfiguration", "SubConfiguration", "", false);
/* 1262 */         Parent.setChecked(parentChecked);
/* 1263 */         Parent.setId("SubConfiguration");
/* 1264 */         form.addElement(Parent);
/*      */ 
/*      */ 
/*      */         
/* 1268 */         String configValue = "";
/* 1269 */         if (table.getSubDetail() != null)
/* 1270 */           configValue = TableManager.getInstance().getAssocConfig(3, table.getSubDetail().getDetValue()); 
/* 1271 */         FormDropDownMenu configuration = MilestoneHelper.getSelectionConfigurationDropDown("configuration", configValue, true);
/* 1272 */         form.addElement(configuration);
/*      */       } 
/*      */       
/* 1275 */       context.putDelivery("Form", form);
/* 1276 */       return context.includeJSP("tables-header-editor.jsp");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1282 */     context.putDelivery("isNotePadVisible", new Boolean(MilestoneHelper.getNotePadVisiblitiy(14, context)));
/*      */     
/* 1284 */     Form form = new Form(this.application, "form", this.application.getInfrastructure().getServletURL(), "Post");
/* 1285 */     form.addElement(new FormHidden("cmd", "tables-header-editor"));
/* 1286 */     form.addElement(new FormHidden("OrderBy", "", true));
/*      */ 
/*      */     
/* 1289 */     FormTextField DescriptionSearch = new FormTextField("DescriptionSearch", "", true, 20);
/* 1290 */     DescriptionSearch.setId("DescriptionSearch");
/* 1291 */     form.addElement(DescriptionSearch);
/*      */ 
/*      */     
/* 1294 */     FormCheckBox isDigitalEquivalentSearch = new FormCheckBox("isDigitalEquivalentSearch", "Digital Equivalent", false, false);
/* 1295 */     isDigitalEquivalentSearch.setId("isDigitalEquivalentSearch");
/* 1296 */     isDigitalEquivalentSearch.setText("");
/* 1297 */     form.addElement(isDigitalEquivalentSearch);
/*      */     
/* 1299 */     context.putDelivery("Form", form);
/*      */     
/* 1301 */     return context.includeJSP("blank-tables-header-editor.jsp");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean editDetail(Context context) {
/* 1309 */     User user = MilestoneSecurity.getUser(context);
/*      */     
/* 1311 */     Notepad notepad = getTableNotepad(context, user.getUserId());
/* 1312 */     Table selected = (Table)notepad.getSelected();
/*      */     
/* 1314 */     int selectedId = -1;
/*      */     
/* 1316 */     if (selected != null) {
/* 1317 */       selectedId = selected.getId();
/*      */     }
/* 1319 */     Notepad notepadDetail = getTableDetailNotepad(context, selectedId);
/* 1320 */     MilestoneHelper.putNotepadIntoSession(notepadDetail, context);
/*      */     
/* 1322 */     Table tableDetail = MilestoneHelper.getScreenTableDetail(context);
/*      */     
/* 1324 */     Form form = null;
/*      */ 
/*      */     
/* 1327 */     if (selected != null) {
/*      */ 
/*      */       
/* 1330 */       if (tableDetail != null) {
/*      */         
/* 1332 */         form = buildDetailForm(context, tableDetail);
/* 1333 */         if (selected.getName().equals("Prefix Code")) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1343 */           Vector environments = Cache.getInstance().getEnvironments();
/* 1344 */           FormDropDownMenu environmentsDropDown = MilestoneHelper.getCorporateStructureDropDown("environments", environments, tableDetail.getSubDetail().getAbbreviation(), true, false);
/* 1345 */           form.addElement(environmentsDropDown);
/*      */         } 
/*      */ 
/*      */         
/* 1349 */         if (selected.getName().equals("Sub-Format")) {
/*      */ 
/*      */           
/* 1352 */           boolean parentChecked = false;
/*      */           
/* 1354 */           if (tableDetail.getSubDetail() != null && 
/* 1355 */             tableDetail.getSubDetail().getName() != null && 
/* 1356 */             tableDetail.getSubDetail().getName().equalsIgnoreCase("Y"))
/*      */           {
/* 1358 */             parentChecked = true;
/*      */           }
/*      */           
/* 1361 */           FormCheckBox Parent = new FormCheckBox("SubConfiguration", "SubConfiguration", "", false);
/* 1362 */           Parent.setChecked(parentChecked);
/* 1363 */           Parent.setId("SubConfiguration");
/* 1364 */           form.addElement(Parent);
/*      */ 
/*      */ 
/*      */           
/* 1368 */           String configValue = "";
/* 1369 */           if (tableDetail.getSubDetail() != null) {
/* 1370 */             configValue = TableManager.getInstance().getAssocConfig(3, tableDetail.getSubDetail().getDetValue());
/*      */           }
/* 1372 */           FormDropDownMenu configuration = MilestoneHelper.getSelectionConfigurationDropDown("configuration", configValue, true);
/* 1373 */           form.addElement(configuration);
/*      */         } 
/*      */         
/* 1376 */         context.putSessionValue("TableDetail", tableDetail);
/* 1377 */         context.putDelivery("Form", form);
/* 1378 */         return context.includeJSP("tables-detail-editor.jsp");
/*      */       } 
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
/* 1420 */       context.putDelivery("isNotePadVisible", new Boolean(MilestoneHelper.getNotePadVisiblitiy(14, context)));
/*      */       
/* 1422 */       form = new Form(this.application, "form", this.application.getInfrastructure().getServletURL(), "Post");
/* 1423 */       form.addElement(new FormHidden("cmd", "tables-detail-editor"));
/* 1424 */       form.addElement(new FormHidden("OrderBy", "", true));
/*      */ 
/*      */       
/* 1427 */       FormTextField DescriptionSearch = new FormTextField("DescriptionSearch", "", true, 20);
/* 1428 */       DescriptionSearch.setId("DescriptionSearch");
/* 1429 */       form.addElement(DescriptionSearch);
/*      */ 
/*      */       
/* 1432 */       FormCheckBox isDigitalEquivalentSearch = new FormCheckBox("isDigitalEquivalentSearch", "Digital Equivalent", false, false);
/* 1433 */       isDigitalEquivalentSearch.setId("isDigitalEquivalentSearch");
/* 1434 */       isDigitalEquivalentSearch.setText("");
/* 1435 */       form.addElement(isDigitalEquivalentSearch);
/*      */       
/* 1437 */       context.putDelivery("Form", form);
/*      */       
/* 1439 */       return context.includeJSP("blank-tables-detail-editor.jsp");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1447 */     context.putDelivery("isNotePadVisible", new Boolean(MilestoneHelper.getNotePadVisiblitiy(14, context)));
/*      */     
/* 1449 */     form = new Form(this.application, "form", this.application.getInfrastructure().getServletURL(), "Post");
/* 1450 */     form.addElement(new FormHidden("cmd", "tables-detail-editor"));
/* 1451 */     form.addElement(new FormHidden("OrderBy", "", true));
/*      */ 
/*      */     
/* 1454 */     FormTextField DescriptionSearch = new FormTextField("DescriptionSearch", "", true, 20);
/* 1455 */     DescriptionSearch.setId("DescriptionSearch");
/* 1456 */     form.addElement(DescriptionSearch);
/*      */ 
/*      */     
/* 1459 */     FormCheckBox isDigitalEquivalentSearch = new FormCheckBox("isDigitalEquivalentSearch", "Digital Equivalent", false, false);
/* 1460 */     isDigitalEquivalentSearch.setId("isDigitalEquivalentSearch");
/* 1461 */     isDigitalEquivalentSearch.setText("");
/* 1462 */     form.addElement(isDigitalEquivalentSearch);
/*      */ 
/*      */     
/* 1465 */     context.putDelivery("Form", form);
/*      */     
/* 1467 */     return context.includeJSP("blank-tables-detail-editor.jsp");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Form buildForm(Context context, Table table) {
/* 1478 */     Form tablesHeaderForm = new Form(this.application, "tablesHeaderForm", 
/* 1479 */         this.application.getInfrastructure().getServletURL(), "POST");
/*      */ 
/*      */     
/* 1482 */     tablesHeaderForm.addElement(new FormHidden("cmd", "tables-header-editor", true));
/*      */ 
/*      */     
/* 1485 */     FormTextField Id = new FormTextField("id", Integer.toString(table.getId()), false, 50, 50);
/* 1486 */     tablesHeaderForm.addElement(Id);
/*      */ 
/*      */     
/* 1489 */     FormTextField Title = new FormTextField("Title", "", false, 50, 50);
/* 1490 */     Title.setValue(table.getName());
/* 1491 */     tablesHeaderForm.addElement(Title);
/*      */ 
/*      */     
/* 1494 */     LookupObject detail = table.getDetail();
/*      */     
/* 1496 */     tablesHeaderForm.addElement(new FormHidden("Value", detail.getAbbreviation(), true));
/*      */ 
/*      */     
/* 1499 */     FormTextField Description = new FormTextField("Description", "", true, 50, 50);
/* 1500 */     Description.addFormEvent("onBlur", "Javascript:checkField(this)");
/* 1501 */     Description.setTabIndex(1);
/* 1502 */     Description.setValue(detail.getName());
/* 1503 */     tablesHeaderForm.addElement(Description);
/*      */ 
/*      */     
/* 1506 */     FormTextField SubDescription = new FormTextField("SubDescription", "", false, 50, 255);
/* 1507 */     SubDescription.setTabIndex(2);
/* 1508 */     PrefixCode subdetail = table.getSubDetail();
/*      */     
/* 1510 */     if (subdetail.getName() != null)
/* 1511 */       SubDescription.setValue(subdetail.getName()); 
/* 1512 */     tablesHeaderForm.addElement(SubDescription);
/*      */ 
/*      */     
/* 1515 */     FormTextField lastUpdated = new FormTextField("lastUpdatedDate", false, 50);
/* 1516 */     if (table.getLastUpdateDate() != null)
/* 1517 */       lastUpdated.setValue(MilestoneHelper.getLongDate(table.getLastUpdateDate())); 
/* 1518 */     tablesHeaderForm.addElement(lastUpdated);
/*      */     
/* 1520 */     FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
/* 1521 */     if (UserManager.getInstance().getUser(table.getLastUpdatingUser()) != null)
/* 1522 */       lastUpdatedBy.setValue(UserManager.getInstance().getUser(table.getLastUpdatingUser()).getLogin()); 
/* 1523 */     tablesHeaderForm.addElement(lastUpdatedBy);
/*      */ 
/*      */     
/* 1526 */     FormTextField DescriptionSearch = new FormTextField("DescriptionSearch", "", false, 20);
/* 1527 */     DescriptionSearch.setId("DescriptionSearch");
/* 1528 */     tablesHeaderForm.addElement(DescriptionSearch);
/*      */ 
/*      */     
/* 1531 */     FormCheckBox isDigitalEquivalentSearch = new FormCheckBox("isDigitalEquivalentSearch", "Digital Equivalent", false, false);
/* 1532 */     isDigitalEquivalentSearch.setId("isDigitalEquivalentSearch");
/* 1533 */     isDigitalEquivalentSearch.setText("");
/* 1534 */     tablesHeaderForm.addElement(isDigitalEquivalentSearch);
/*      */ 
/*      */ 
/*      */     
/* 1538 */     FormCheckBox inactive = new FormCheckBox("inactive", "Inactive", false, !!detail.getInactive());
/* 1539 */     inactive.setId("inactive");
/* 1540 */     tablesHeaderForm.addElement(inactive);
/*      */ 
/*      */     
/* 1543 */     tablesHeaderForm.addElement(new FormHidden("cmd", "tables-header"));
/*      */ 
/*      */     
/* 1546 */     context.putSessionValue("Table", table);
/*      */ 
/*      */     
/* 1549 */     if (context.getSessionValue("NOTEPAD_TABLE_VISIBLE") != null) {
/* 1550 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_TABLE_VISIBLE"));
/*      */     }
/* 1552 */     return tablesHeaderForm;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Form buildDetailForm(Context context, Table table) {
/* 1559 */     Form tablesHeaderForm = new Form(this.application, "tablesDetailForm", 
/* 1560 */         this.application.getInfrastructure().getServletURL(), "POST");
/*      */ 
/*      */     
/* 1563 */     tablesHeaderForm.addElement(new FormHidden("cmd", "tables-detail-editor", true));
/*      */     
/* 1565 */     Notepad notepad = MilestoneHelper.getNotepadFromSession(14, context);
/*      */     
/* 1567 */     Table selected = (Table)notepad.getSelected();
/*      */ 
/*      */     
/* 1570 */     FormTextField Id = new FormTextField("id", Integer.toString(table.getId()), false, 50, 50);
/* 1571 */     tablesHeaderForm.addElement(Id);
/*      */ 
/*      */     
/* 1574 */     FormTextField Title = new FormTextField("Title", "", false, 50, 50);
/* 1575 */     Table tableName = (Table)context.getSessionValue("Table");
/* 1576 */     if (tableName != null)
/* 1577 */       Title.setValue(tableName.getName()); 
/* 1578 */     tablesHeaderForm.addElement(Title);
/*      */ 
/*      */     
/* 1581 */     LookupObject detail = table.getDetail();
/*      */     
/* 1583 */     tablesHeaderForm.addElement(new FormHidden("Value", detail.getAbbreviation(), true));
/*      */ 
/*      */     
/* 1586 */     FormTextField Description = new FormTextField("Description", "", true, 50, 50);
/* 1587 */     Description.addFormEvent("onBlur", "Javascript:checkField(this)");
/* 1588 */     Description.setTabIndex(1);
/* 1589 */     Description.setValue(detail.getName());
/* 1590 */     tablesHeaderForm.addElement(Description);
/*      */ 
/*      */     
/* 1593 */     FormTextField SubDescription = new FormTextField("SubDescription", "", false, 50, 255);
/* 1594 */     SubDescription.setTabIndex(2);
/* 1595 */     PrefixCode prefixCode = table.getSubDetail();
/*      */ 
/*      */ 
/*      */     
/* 1599 */     int ps = 2;
/* 1600 */     String[] dvalues = new String[ps];
/* 1601 */     dvalues[0] = "0";
/* 1602 */     dvalues[1] = "1";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1607 */     String[] dlabels = new String[ps];
/* 1608 */     dlabels[0] = "Physical";
/* 1609 */     dlabels[1] = "Digital";
/*      */ 
/*      */ 
/*      */     
/* 1613 */     if (detail.getId() == 3 || detail.getId() == 1 || detail.getId() == 29) {
/*      */       
/* 1615 */       FormRadioButtonGroup prodType = new FormRadioButtonGroup("ProdType", 
/* 1616 */           String.valueOf(detail.getProdType()), dvalues, dlabels, false);
/* 1617 */       if (selected.getId() == 29) prodType.addFormEvent("onclick", "SetDigEquiv();"); 
/* 1618 */       tablesHeaderForm.addElement(prodType);
/*      */ 
/*      */       
/* 1621 */       if (detail.getId() == 29)
/*      */       {
/* 1623 */         FormCheckBox isDigitalEquivalent = new FormCheckBox("isDigitalEquivalent", "Digital Equivalent", false, !!detail.getIsDigitalEquivalent());
/* 1624 */         isDigitalEquivalent.setId("isDigitalEquivalent");
/* 1625 */         isDigitalEquivalent.setText("");
/* 1626 */         tablesHeaderForm.addElement(isDigitalEquivalent);
/*      */       }
/*      */     
/* 1629 */     } else if (detail.getId() == 4) {
/*      */       
/* 1631 */       if (TableManager.getInstance().getProdType(detail.getAbbreviation()) >= 0) {
/*      */         
/* 1633 */         FormHidden prodType = new FormHidden("prodType", String.valueOf(TableManager.getInstance().getProdType(detail.getAbbreviation())));
/* 1634 */         FormHidden prodTypeLabel = new FormHidden("prodTypeLabel", dlabels[TableManager.getInstance().getProdType(detail.getAbbreviation())], false);
/* 1635 */         tablesHeaderForm.addElement(prodType);
/* 1636 */         tablesHeaderForm.addElement(prodTypeLabel);
/*      */       } 
/*      */     } 
/*      */     
/* 1640 */     if (prefixCode.getName() != null)
/* 1641 */       SubDescription.setValue(prefixCode.getName()); 
/* 1642 */     tablesHeaderForm.addElement(SubDescription);
/*      */ 
/*      */     
/* 1645 */     FormTextField lastUpdated = new FormTextField("lastUpdatedDate", false, 50);
/* 1646 */     if (table.getLastUpdateDate() != null)
/* 1647 */       lastUpdated.setValue(MilestoneHelper.getLongDate(table.getLastUpdateDate())); 
/* 1648 */     tablesHeaderForm.addElement(lastUpdated);
/*      */     
/* 1650 */     FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
/* 1651 */     if (UserManager.getInstance().getUser(table.getLastUpdatingUser()) != null) {
/* 1652 */       lastUpdatedBy.setValue(UserManager.getInstance().getUser(table.getLastUpdatingUser()).getLogin());
/*      */     }
/*      */     
/* 1655 */     FormTextField DescriptionSearch = new FormTextField("DescriptionSearch", "", false, 20);
/* 1656 */     DescriptionSearch.setId("DescriptionSearch");
/* 1657 */     tablesHeaderForm.addElement(DescriptionSearch);
/*      */ 
/*      */     
/* 1660 */     FormCheckBox isDigitalEquivalentSearch = new FormCheckBox("isDigitalEquivalentSearch", "Digital Equivalent", false, false);
/* 1661 */     isDigitalEquivalentSearch.setId("isDigitalEquivalentSearch");
/* 1662 */     isDigitalEquivalentSearch.setText("");
/* 1663 */     tablesHeaderForm.addElement(isDigitalEquivalentSearch);
/*      */ 
/*      */     
/* 1666 */     FormCheckBox inactive = new FormCheckBox("inactive", "Inactive", false, !!detail.getInactive());
/* 1667 */     inactive.setId("inactive");
/* 1668 */     tablesHeaderForm.addElement(inactive);
/*      */     
/* 1670 */     tablesHeaderForm.addElement(new FormHidden("cmd", "tables-header"));
/*      */ 
/*      */     
/* 1673 */     context.putSessionValue("TableDetail", table);
/*      */ 
/*      */     
/* 1676 */     if (context.getSessionValue("NOTEPAD_TABLE_DETAIL_VISIBLE") != null) {
/* 1677 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_TABLE_DETAIL_VISIBLE"));
/*      */     }
/* 1679 */     return tablesHeaderForm;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean editNewHeader(Context context) {
/* 1688 */     User user = MilestoneSecurity.getUser(context);
/*      */     
/* 1690 */     Notepad notepad = getTableNotepad(context, user.getUserId());
/* 1691 */     Table table = (Table)context.getSessionValue("Table");
/*      */     
/* 1693 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/* 1694 */     context.removeSessionValue(NOTEPAD_SESSION_NAMES[18]);
/*      */     
/* 1696 */     Form form = buildNewHeaderForm(context);
/* 1697 */     if (table.getName().equals("Prefix Code")) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1707 */       Vector environments = Cache.getInstance().getEnvironments();
/* 1708 */       FormDropDownMenu environmentsDropDown = MilestoneHelper.getCorporateStructureDropDown("environments", environments, "0", true, false);
/* 1709 */       form.addElement(environmentsDropDown);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1714 */     if (table != null) {
/*      */       
/* 1716 */       table.setDetail(null);
/* 1717 */       table.setSubDetail(null);
/* 1718 */       notepad.setSelected(table);
/*      */     } 
/*      */     
/* 1721 */     context.putDelivery("Form", form);
/*      */ 
/*      */     
/* 1724 */     if (context.getSessionValue("NOTEPAD_TABLE_VISIBLE") != null) {
/* 1725 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_TABLE_VISIBLE"));
/*      */     }
/* 1727 */     return context.includeJSP("tables-header-editor.jsp");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean editNewDetail(Context context) {
/* 1735 */     User user = MilestoneSecurity.getUser(context);
/*      */     
/* 1737 */     Notepad notepad = getTableNotepad(context, user.getUserId());
/* 1738 */     Table selected = (Table)notepad.getSelected();
/* 1739 */     Notepad notepadDetail = null;
/* 1740 */     if (selected != null) {
/* 1741 */       notepadDetail = getTableDetailNotepad(context, selected.getId());
/*      */     } else {
/* 1743 */       notepadDetail = getTableDetailNotepad(context, -1);
/*      */     } 
/* 1745 */     MilestoneHelper.putNotepadIntoSession(notepadDetail, context);
/*      */ 
/*      */     
/* 1748 */     if (selected != null) {
/*      */       
/* 1750 */       selected.setDetail(null);
/* 1751 */       selected.setSubDetail(null);
/* 1752 */       notepad.setSelected(selected);
/*      */     } 
/*      */     
/* 1755 */     Form form = buildNewDetailForm(context);
/* 1756 */     if (selected.getName().equals("Prefix Code")) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1765 */       Vector environments = Cache.getInstance().getEnvironments();
/* 1766 */       FormDropDownMenu environmentsDropDown = MilestoneHelper.getCorporateStructureDropDown("environments", environments, "0", true, false);
/* 1767 */       form.addElement(environmentsDropDown);
/*      */     } 
/*      */ 
/*      */     
/* 1771 */     context.putDelivery("Form", form);
/*      */     
/* 1773 */     if (context.getSessionValue("NOTEPAD_TABLE_DETAIL_VISIBLE") != null) {
/* 1774 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_TABLE_DETAIL_VISIBLE"));
/*      */     }
/* 1776 */     return context.includeJSP("tables-detail-editor.jsp");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Form buildNewHeaderForm(Context context) {
/* 1785 */     User sessionUser = MilestoneSecurity.getUser(context);
/*      */     
/* 1787 */     Form tablesHeaderForm = new Form(this.application, "TablesHeaderForm", 
/* 1788 */         this.application.getInfrastructure().getServletURL(), "POST");
/*      */ 
/*      */     
/* 1791 */     FormTextField Id = new FormTextField("id", Integer.toString(-1), false, 50, 50);
/* 1792 */     tablesHeaderForm.addElement(Id);
/*      */ 
/*      */     
/* 1795 */     Notepad notepad = MilestoneHelper.getNotepadFromSession(14, context);
/* 1796 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/* 1797 */     Table selected = (Table)notepad.getSelected();
/*      */     
/* 1799 */     FormTextField Title = new FormTextField("Title", "", true, 50, 50);
/* 1800 */     Title.setValue(selected.getName());
/* 1801 */     tablesHeaderForm.addElement(Title);
/*      */     
/* 1803 */     if (selected.getId() != 33) {
/*      */ 
/*      */       
/* 1806 */       FormTextField Value = new FormTextField("Value", "", true, 5, 50);
/* 1807 */       Value.setTabIndex(1);
/* 1808 */       tablesHeaderForm.addElement(Value);
/*      */     } 
/*      */ 
/*      */     
/* 1812 */     FormTextField Description = new FormTextField("Description", "", true, 50, 50);
/* 1813 */     Description.addFormEvent("onBlur", "Javascript:checkField(this)");
/* 1814 */     Description.setTabIndex(2);
/* 1815 */     tablesHeaderForm.addElement(Description);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1820 */     if (!selected.getName().equals("Sub-Format")) {
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
/* 1839 */       FormTextField SubDescription = new FormTextField("SubDescription", "", false, 50, 255);
/* 1840 */       SubDescription.setTabIndex(3);
/* 1841 */       tablesHeaderForm.addElement(SubDescription);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1846 */     FormTextField DescriptionSearch = new FormTextField("DescriptionSearch", "", false, 20);
/* 1847 */     DescriptionSearch.setId("DescriptionSearch");
/* 1848 */     tablesHeaderForm.addElement(DescriptionSearch);
/*      */ 
/*      */     
/* 1851 */     FormCheckBox isDigitalEquivalentSearch = new FormCheckBox("isDigitalEquivalentSearch", "Digital Equivalent", false, false);
/* 1852 */     isDigitalEquivalentSearch.setId("isDigitalEquivalentSearch");
/* 1853 */     isDigitalEquivalentSearch.setText("");
/* 1854 */     tablesHeaderForm.addElement(isDigitalEquivalentSearch);
/*      */ 
/*      */ 
/*      */     
/* 1858 */     FormCheckBox inactive = new FormCheckBox("inactive", "Inactive", false, false);
/* 1859 */     inactive.setId("inactive");
/* 1860 */     tablesHeaderForm.addElement(inactive);
/*      */ 
/*      */     
/* 1863 */     tablesHeaderForm.addElement(new FormHidden("cmd", "tables-header-edit-new"));
/*      */ 
/*      */     
/* 1866 */     if (context.getSessionValue("NOTEPAD_TABLE_VISIBLE") != null) {
/* 1867 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_TABLE_VISIBLE"));
/*      */     }
/* 1869 */     return tablesHeaderForm;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Form buildNewDetailForm(Context context) {
/* 1877 */     User sessionUser = MilestoneSecurity.getUser(context);
/*      */     
/* 1879 */     Form tablesHeaderForm = new Form(this.application, "TablesHeaderForm", 
/* 1880 */         this.application.getInfrastructure().getServletURL(), "POST");
/*      */ 
/*      */     
/* 1883 */     FormTextField Id = new FormTextField("id", Integer.toString(-1), false, 50, 50);
/* 1884 */     tablesHeaderForm.addElement(Id);
/*      */ 
/*      */     
/* 1887 */     Notepad notepad = MilestoneHelper.getNotepadFromSession(14, context);
/* 1888 */     Table selected = (Table)notepad.getSelected();
/*      */     
/* 1890 */     FormTextField Title = new FormTextField("Title", "", true, 50, 50);
/* 1891 */     Title.setValue(selected.getName());
/* 1892 */     tablesHeaderForm.addElement(Title);
/*      */     
/* 1894 */     if (selected.getId() != 33) {
/*      */ 
/*      */       
/* 1897 */       FormTextField Value = new FormTextField("Value", "", true, 5, 50);
/* 1898 */       Value.setTabIndex(1);
/* 1899 */       tablesHeaderForm.addElement(Value);
/*      */     } 
/*      */ 
/*      */     
/* 1903 */     FormTextField Description = new FormTextField("Description", "", true, 50, 50);
/* 1904 */     Description.addFormEvent("onBlur", "Javascript:checkField(this)");
/* 1905 */     Description.setTabIndex(2);
/* 1906 */     tablesHeaderForm.addElement(Description);
/*      */ 
/*      */ 
/*      */     
/* 1910 */     if (selected.getName().equals("Sub-Format")) {
/*      */ 
/*      */ 
/*      */       
/* 1914 */       boolean parentChecked = false;
/* 1915 */       FormCheckBox Parent = new FormCheckBox("SubConfiguration", "SubConfiguratioin", "", false);
/* 1916 */       Parent.setChecked(parentChecked);
/* 1917 */       Parent.setId("SubConfiguration");
/* 1918 */       tablesHeaderForm.addElement(Parent);
/*      */ 
/*      */ 
/*      */       
/* 1922 */       String configValue = "";
/* 1923 */       FormDropDownMenu configuration = MilestoneHelper.getSelectionConfigurationDropDown("configuration", configValue, true);
/* 1924 */       tablesHeaderForm.addElement(configuration);
/*      */     }
/*      */     else {
/*      */       
/* 1928 */       FormTextField SubDescription = new FormTextField("SubDescription", "", false, 50, 255);
/* 1929 */       SubDescription.setTabIndex(3);
/* 1930 */       tablesHeaderForm.addElement(SubDescription);
/*      */     } 
/*      */ 
/*      */     
/* 1934 */     int ps = 2;
/* 1935 */     String[] dvalues = new String[ps];
/* 1936 */     dvalues[0] = "0";
/* 1937 */     dvalues[1] = "1";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1942 */     String[] dlabels = new String[2];
/* 1943 */     dlabels[0] = "Physical";
/* 1944 */     dlabels[1] = "Digital";
/*      */ 
/*      */ 
/*      */     
/* 1948 */     if (selected.getId() == 3 || selected.getId() == 1 || selected.getId() == 29) {
/*      */       
/* 1950 */       FormRadioButtonGroup prodType = new FormRadioButtonGroup("ProdType", "", dvalues, dlabels, false);
/* 1951 */       if (selected.getId() == 29) prodType.addFormEvent("onclick", "SetDigEquiv();"); 
/* 1952 */       tablesHeaderForm.addElement(prodType);
/*      */ 
/*      */       
/* 1955 */       if (selected.getId() == 29)
/*      */       {
/* 1957 */         FormCheckBox isDigitalEquivalent = new FormCheckBox("isDigitalEquivalent", "Digital Equivalent", false, false);
/* 1958 */         isDigitalEquivalent.setId("isDigitalEquivalent");
/* 1959 */         isDigitalEquivalent.setText("");
/* 1960 */         tablesHeaderForm.addElement(isDigitalEquivalent);
/*      */       }
/*      */     
/*      */     }
/* 1964 */     else if (selected.getId() == 4) {
/*      */       
/* 1966 */       FormTextField prodType = new FormTextField("prodType", "", false, 50);
/* 1967 */       tablesHeaderForm.addElement(prodType);
/*      */     } 
/*      */ 
/*      */     
/* 1971 */     FormTextField DescriptionSearch = new FormTextField("DescriptionSearch", "", false, 20);
/* 1972 */     DescriptionSearch.setId("DescriptionSearch");
/* 1973 */     tablesHeaderForm.addElement(DescriptionSearch);
/*      */ 
/*      */     
/* 1976 */     FormCheckBox isDigitalEquivalentSearch = new FormCheckBox("isDigitalEquivalentSearch", "Digital Equivalent", false, false);
/* 1977 */     isDigitalEquivalentSearch.setId("isDigitalEquivalentSearch");
/* 1978 */     isDigitalEquivalentSearch.setText("");
/* 1979 */     tablesHeaderForm.addElement(isDigitalEquivalentSearch);
/*      */ 
/*      */ 
/*      */     
/* 1983 */     FormCheckBox inactive = new FormCheckBox("inactive", "Inactive", false, false);
/* 1984 */     inactive.setId("inactive");
/* 1985 */     tablesHeaderForm.addElement(inactive);
/*      */ 
/*      */     
/* 1988 */     tablesHeaderForm.addElement(new FormHidden("cmd", "tables-detail-edit-new"));
/*      */ 
/*      */     
/* 1991 */     if (context.getSessionValue("NOTEPAD_TABLE_DETAIL_VISIBLE") != null) {
/* 1992 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_TABLE_DETAIL_VISIBLE"));
/*      */     }
/* 1994 */     return tablesHeaderForm;
/*      */   }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\TablesHeaderHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */