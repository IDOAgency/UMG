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
/*      */ import com.universal.milestone.CorporateObjectSearchObj;
/*      */ import com.universal.milestone.CorporateStructureManager;
/*      */ import com.universal.milestone.Division;
/*      */ import com.universal.milestone.Environment;
/*      */ import com.universal.milestone.Family;
/*      */ import com.universal.milestone.Form;
/*      */ import com.universal.milestone.Label;
/*      */ import com.universal.milestone.LabelHandler;
/*      */ import com.universal.milestone.LabelManager;
/*      */ import com.universal.milestone.MilestoneHelper;
/*      */ import com.universal.milestone.MilestoneHelper_2;
/*      */ import com.universal.milestone.MilestoneMessage;
/*      */ import com.universal.milestone.MilestoneSecurity;
/*      */ import com.universal.milestone.Notepad;
/*      */ import com.universal.milestone.SecureHandler;
/*      */ import com.universal.milestone.User;
/*      */ import com.universal.milestone.UserManager;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Hashtable;
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
/*      */ public class LabelHandler
/*      */   extends SecureHandler
/*      */ {
/*      */   public static final String COMPONENT_CODE = "hLab";
/*      */   public GeminiApplication application;
/*      */   public ComponentLog log;
/*      */   
/*      */   public LabelHandler(GeminiApplication application) {
/*   70 */     this.application = application;
/*   71 */     this.log = application.getLog("hLab");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   79 */   public String getDescription() { return "Label Handler"; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean acceptRequest(Dispatcher dispatcher, Context context, String command) {
/*   89 */     if (super.acceptRequest(dispatcher, context, command))
/*      */     {
/*   91 */       if (command.startsWith("label"))
/*      */       {
/*   93 */         return handleRequest(dispatcher, context, command);
/*      */       }
/*      */     }
/*   96 */     return false;
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
/*  108 */     if (command.equalsIgnoreCase("label-search"))
/*      */     {
/*  110 */       search(dispatcher, context, command);
/*      */     }
/*  112 */     if (command.equalsIgnoreCase("label-sort")) {
/*      */       
/*  114 */       sort(dispatcher, context);
/*      */     }
/*  116 */     else if (command.equalsIgnoreCase("label-editor")) {
/*      */       
/*  118 */       edit(context);
/*      */     }
/*  120 */     else if (command.equalsIgnoreCase("label-edit-save")) {
/*      */       
/*  122 */       save(context);
/*      */     }
/*  124 */     else if (command.equalsIgnoreCase("label-edit-save-new")) {
/*      */       
/*  126 */       saveNew(context);
/*      */     }
/*  128 */     else if (command.equalsIgnoreCase("label-edit-delete")) {
/*      */       
/*  130 */       delete(context);
/*      */     }
/*  132 */     else if (command.equalsIgnoreCase("label-edit-new")) {
/*      */       
/*  134 */       newForm(context);
/*      */     } 
/*  136 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean search(Dispatcher dispatcher, Context context, String command) {
/*  147 */     Notepad notepad = MilestoneHelper.getNotepadFromSession(12, context);
/*      */ 
/*      */     
/*  150 */     notepad.setAllContents(null);
/*  151 */     notepad.setSelected(null);
/*      */ 
/*      */ 
/*      */     
/*  155 */     String environmentDescriptionSearch = "";
/*  156 */     String familyDescriptionSearch = "";
/*  157 */     String companyDescriptionSearch = "";
/*  158 */     String divisionDescriptionSearch = "";
/*  159 */     String labelDescriptionSearch = "";
/*  160 */     String entityDescriptionSearch = "";
/*      */ 
/*      */ 
/*      */     
/*  164 */     if (context.getParameter("EnvironmentDescriptionSearch") != null) {
/*  165 */       environmentDescriptionSearch = context.getParameter("EnvironmentDescriptionSearch");
/*      */     }
/*  167 */     if (context.getParameter("FamilyDescriptionSearch") != null) {
/*  168 */       familyDescriptionSearch = context.getParameter("FamilyDescriptionSearch");
/*      */     }
/*  170 */     if (context.getParameter("CompanyDescriptionSearch") != null) {
/*  171 */       companyDescriptionSearch = context.getParameter("CompanyDescriptionSearch");
/*      */     }
/*  173 */     if (context.getParameter("DivisionDescriptionSearch") != null) {
/*  174 */       divisionDescriptionSearch = context.getParameter("DivisionDescriptionSearch");
/*      */     }
/*  176 */     if (context.getParameter("LabelDescriptionSearch") != null) {
/*  177 */       labelDescriptionSearch = context.getParameter("LabelDescriptionSearch");
/*      */     }
/*  179 */     if (context.getParameter("EntityDescriptionSearch") != null) {
/*  180 */       entityDescriptionSearch = context.getParameter("EntityDescriptionSearch");
/*      */     }
/*      */     
/*  183 */     CorporateObjectSearchObj corpSearch = new CorporateObjectSearchObj();
/*      */     
/*  185 */     corpSearch.setEnvironmentSearch(environmentDescriptionSearch);
/*  186 */     corpSearch.setFamilySearch(familyDescriptionSearch);
/*  187 */     corpSearch.setCompanySearch(companyDescriptionSearch);
/*  188 */     corpSearch.setDivisionSearch(divisionDescriptionSearch);
/*  189 */     corpSearch.setLabelSearch(labelDescriptionSearch);
/*  190 */     corpSearch.setEntitySearch(entityDescriptionSearch);
/*  191 */     notepad.setCorporateObjectSearchObj(corpSearch);
/*      */ 
/*      */     
/*  194 */     LabelManager.getInstance().setNotepadQuery(notepad);
/*  195 */     dispatcher.redispatch(context, "label-editor");
/*      */     
/*  197 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean sort(Dispatcher dispatcher, Context context) {
/*  207 */     int sort = Integer.parseInt(context.getParameter("OrderBy"));
/*      */     
/*  209 */     Notepad notepad = getLabelNotepad(context, MilestoneSecurity.getUser(context).getUserId());
/*      */     
/*  211 */     if (notepad.getAllContents() != null) {
/*      */ 
/*      */       
/*  214 */       Vector sortedVector = notepad.getAllContents();
/*      */ 
/*      */       
/*  217 */       if (sort == 0) {
/*      */         
/*  219 */         sortedVector = MilestoneHelper.sortCorporateVectorByName(notepad.getAllContents());
/*      */       }
/*      */       else {
/*      */         
/*  223 */         sortedVector = MilestoneHelper.sortCorporateVectorByParentName(notepad.getAllContents());
/*      */       } 
/*      */       
/*  226 */       notepad.setAllContents(sortedVector);
/*      */     } 
/*      */ 
/*      */     
/*  230 */     notepad.goToSelectedPage();
/*  231 */     dispatcher.redispatch(context, "label-editor");
/*      */     
/*  233 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Notepad getLabelNotepad(Context context, int userId) {
/*  243 */     Vector contents = new Vector();
/*      */     
/*  245 */     if (MilestoneHelper.getNotepadFromSession(12, context) != null) {
/*      */       
/*  247 */       Notepad notepad = MilestoneHelper.getNotepadFromSession(12, context);
/*      */       
/*  249 */       if (notepad.getAllContents() == null) {
/*      */         
/*  251 */         contents = LabelManager.getInstance().getLabelNotepadList(userId, notepad);
/*  252 */         notepad.setAllContents(contents);
/*      */       } 
/*      */       
/*  255 */       return notepad;
/*      */     } 
/*      */ 
/*      */     
/*  259 */     String[] columnNames = { "Label", "Division" };
/*  260 */     contents = MilestoneHelper.sortCorporateVectorByName(LabelManager.getInstance().getLabelNotepadList(userId, null));
/*  261 */     return new Notepad(contents, 0, 7, "Label", 12, columnNames);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean edit(Context context) {
/*  269 */     User user = MilestoneSecurity.getUser(context);
/*      */     
/*  271 */     Notepad notepad = getLabelNotepad(context, user.getUserId());
/*  272 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/*  274 */     Label label = MilestoneHelper.getScreenLabel(context);
/*      */     
/*  276 */     if (label != null) {
/*      */       
/*  278 */       Form form = null;
/*      */       
/*  280 */       if (label != null) {
/*      */         
/*  282 */         form = buildForm(context, label);
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  287 */         form = buildNewForm(context);
/*      */       } 
/*  289 */       form.addElement(new FormHidden("OrderBy", "", true));
/*  290 */       context.putDelivery("CorporateArrays", getJavaScriptCorporateArray(context));
/*  291 */       context.putDelivery("Form", form);
/*  292 */       return context.includeJSP("label-editor.jsp");
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  297 */     return goToBlank(context);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private Form buildForm(Context context, Label label) {
/*      */     String selectedDistCo;
/*  304 */     this.log.debug("Building form.");
/*  305 */     Form labelForm = new Form(this.application, "labelForm", 
/*  306 */         this.application.getInfrastructure().getServletURL(), "POST");
/*      */ 
/*      */     
/*  309 */     Division division = label.getParentDivision();
/*  310 */     Company company = null;
/*  311 */     Family family = null;
/*      */     
/*  313 */     Environment environment = null;
/*  314 */     String environmentString = "";
/*  315 */     String familyString = "";
/*  316 */     String companyString = "";
/*  317 */     String divisionString = "";
/*  318 */     boolean boolVal = MilestoneHelper.getActiveCorporateStructure(label.getStructureID());
/*  319 */     label.setActive(boolVal);
/*      */     
/*  321 */     if (division != null) {
/*      */       
/*  323 */       company = (Company)MilestoneHelper.getStructureObject(division.getParentCompany().getStructureID());
/*  324 */       environment = (Environment)MilestoneHelper.getStructureObject(company.getParentEnvironment().getStructureID());
/*  325 */       environmentString = environment.getName();
/*  326 */       family = (Family)MilestoneHelper.getStructureObject(environment.getParentFamily().getStructureID());
/*  327 */       familyString = family.getName();
/*  328 */       companyString = company.getName();
/*  329 */       divisionString = Integer.toString(division.getStructureID());
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
/*      */ 
/*      */ 
/*      */     
/*  343 */     Vector environments = family.getEnvironments();
/*  344 */     FormDropDownMenu parent4Selection = MilestoneHelper.getCorporateStructureDropDown("Parent4Selection", environments, Integer.toString(environment.getStructureID()), true, false);
/*  345 */     parent4Selection.setTabIndex(3);
/*  346 */     parent4Selection.addFormEvent("onChange", "adjustSelection(this)");
/*  347 */     labelForm.addElement(parent4Selection);
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
/*  358 */     Vector families = Cache.getFamilies();
/*  359 */     FormDropDownMenu Parent3Selection = MilestoneHelper.getCorporateStructureDropDown("Parent3Selection", families, Integer.toString(family.getStructureID()), true, false);
/*  360 */     Parent3Selection.addFormEvent("onChange", "adjustSelection(this)");
/*  361 */     Parent3Selection.setTabIndex(1);
/*  362 */     labelForm.addElement(Parent3Selection);
/*      */ 
/*      */     
/*  365 */     FormCheckBox active = new FormCheckBox("active", "", true, label.getActive());
/*  366 */     active.setTabIndex(2);
/*  367 */     labelForm.addElement(active);
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
/*  379 */     Vector companies = environment.getCompanies();
/*  380 */     FormDropDownMenu parent2Selection = MilestoneHelper.getCorporateStructureDropDown("Parent2Selection", 
/*  381 */         companies, 
/*  382 */         Integer.toString(company.getStructureID()), 
/*  383 */         true, false);
/*  384 */     parent2Selection.setTabIndex(4);
/*  385 */     parent2Selection.addFormEvent("onChange", "adjustSelection(this)");
/*  386 */     labelForm.addElement(parent2Selection);
/*      */ 
/*      */ 
/*      */     
/*  390 */     Vector divisions = company.getDivisions();
/*  391 */     FormDropDownMenu Parent1Selection = MilestoneHelper.getCorporateStructureDropDown("Parent1Selection", divisions, divisionString, true, false);
/*  392 */     Parent1Selection.setTabIndex(4);
/*  393 */     labelForm.addElement(Parent1Selection);
/*      */ 
/*      */     
/*  396 */     String westEast = "West";
/*  397 */     if (label.getDistribution() == 1) {
/*  398 */       westEast = "East";
/*      */     }
/*  400 */     else if (label.getDistribution() == 2) {
/*  401 */       westEast = "Unassigned";
/*  402 */     }  FormRadioButtonGroup distribution = new FormRadioButtonGroup("Distribution", westEast, "West, East, Unassigned", false);
/*  403 */     distribution.setTabIndex(4);
/*  404 */     labelForm.addElement(distribution);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  410 */     int labelDistCo = label.getDistCoId();
/*  411 */     if (labelDistCo == -1) {
/*  412 */       selectedDistCo = "1";
/*      */     } else {
/*  414 */       selectedDistCo = String.valueOf(labelDistCo);
/*      */     } 
/*  416 */     Hashtable distCoHash = MilestoneHelper_2.getDistCoNames();
/*      */     
/*  418 */     String[] arrayIds = new String[distCoHash.size()];
/*  419 */     String[] arrayText = new String[distCoHash.size()];
/*  420 */     Enumeration distCoKeys = distCoHash.keys();
/*  421 */     int counter = 0;
/*  422 */     while (distCoKeys.hasMoreElements()) {
/*  423 */       Integer currentKey = (Integer)distCoKeys.nextElement();
/*  424 */       arrayIds[counter] = currentKey.toString();
/*  425 */       arrayText[counter] = (String)distCoHash.get(currentKey);
/*  426 */       counter++;
/*      */     } 
/*      */     
/*  429 */     FormDropDownMenu distCo = new FormDropDownMenu("DistributionCompany", selectedDistCo, arrayIds, arrayText, true);
/*      */     
/*  431 */     labelForm.addElement(distCo);
/*      */ 
/*      */     
/*  434 */     FormTextField corporateDescription = new FormTextField("CorporateDescription", label.getName(), true, 50, 50);
/*  435 */     corporateDescription.addFormEvent("onBlur", "Javascript:checkField(this);fillAbbreviation()");
/*  436 */     corporateDescription.setTabIndex(2);
/*  437 */     labelForm.addElement(corporateDescription);
/*      */ 
/*      */     
/*  440 */     FormTextField corporateAbbreviation = new FormTextField("CorporateAbbreviation", label.getStructureAbbreviation(), true, 3, 3);
/*  441 */     corporateAbbreviation.addFormEvent("onBlur", "Javascript:checkField(this)");
/*  442 */     corporateAbbreviation.setTabIndex(3);
/*  443 */     labelForm.addElement(corporateAbbreviation);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  451 */     String archimedesIdStr = Integer.toString(label.getArchimedesId());
/*  452 */     FormTextField archimedesId = new FormTextField("archimedesId", archimedesIdStr, false, 10);
/*  453 */     labelForm.addElement(archimedesId);
/*      */     
/*  455 */     FormTextField entityName = new FormTextField("entityName", label.getEntityName(), false, 50);
/*  456 */     labelForm.addElement(entityName);
/*      */     
/*  458 */     FormTextField legacyOpCo = new FormTextField("legacyOpCo", label.getLegacyOpCo(), false, 50);
/*  459 */     labelForm.addElement(legacyOpCo);
/*      */     
/*  461 */     FormTextField legacyOpUnit = new FormTextField("legacyOpUnit", label.getLegacyOpUnit(), false, 50);
/*  462 */     labelForm.addElement(legacyOpUnit);
/*      */     
/*  464 */     FormTextField legacySuperLabel = new FormTextField("legacySuperLabel", label.getLegacySuperLabel(), false, 50);
/*  465 */     labelForm.addElement(legacySuperLabel);
/*      */     
/*  467 */     FormTextField legacySubLabel = new FormTextField("legacySubLabel", label.getLegacySubLabel(), false, 50);
/*  468 */     labelForm.addElement(legacySubLabel);
/*      */     
/*  470 */     FormTextField levelOfActivity = new FormTextField("levelOfActivity", label.getLevelOfActivity(), false, 50);
/*  471 */     labelForm.addElement(levelOfActivity);
/*      */     
/*  473 */     FormTextField productionGroupCode = new FormTextField("productionGroupCode", label.getProductionGroupCode(), false, 50);
/*  474 */     labelForm.addElement(productionGroupCode);
/*      */     
/*  476 */     FormTextField entityType = new FormTextField("entityType", label.getEntityType(), false, 50);
/*  477 */     labelForm.addElement(entityType);
/*      */ 
/*      */     
/*  480 */     FormCheckBox apngInd = new FormCheckBox("APNGInd", "", false, label.getAPGNInd());
/*  481 */     apngInd.setEnabled(false);
/*  482 */     apngInd.setStartingChecked(label.getAPGNInd());
/*  483 */     labelForm.addElement(apngInd);
/*      */ 
/*      */     
/*  486 */     FormTextField distCoName = new FormTextField("DistCoName", label.getDistCoName(), false, 100);
/*  487 */     distCo.setId("");
/*  488 */     labelForm.addElement(distCoName);
/*      */ 
/*      */     
/*  491 */     FormTextField lastUpdated = new FormTextField("lastUpdatedDate", false, 50);
/*  492 */     if (label.getLastUpdateDate() != null)
/*  493 */       lastUpdated.setValue(MilestoneHelper.getCustomFormatedDate(label.getLastUpdateDate(), "M/d/yyyy hh:mm:ss a 'ET'")); 
/*  494 */     labelForm.addElement(lastUpdated);
/*      */     
/*  496 */     FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
/*  497 */     if (UserManager.getInstance().getUser(label.getLastUpdatingUser()) != null)
/*  498 */       lastUpdatedBy.setValue(UserManager.getInstance().getUser(label.getLastUpdatingUser()).getName()); 
/*  499 */     labelForm.addElement(lastUpdatedBy);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  504 */     FormTextField familyDescriptionSearch = new FormTextField("FamilyDescriptionSearch", "", false, 20);
/*  505 */     familyDescriptionSearch.setId("FamilyDescriptionSearch");
/*  506 */     labelForm.addElement(familyDescriptionSearch);
/*      */ 
/*      */     
/*  509 */     FormTextField environmentDescriptionSearch = new FormTextField("EnvironmentDescriptionSearch", "", false, 20);
/*  510 */     environmentDescriptionSearch.setId("EnvironmentDescriptionSearch");
/*  511 */     labelForm.addElement(environmentDescriptionSearch);
/*      */     
/*  513 */     FormTextField companyDescriptionSearch = new FormTextField("CompanyDescriptionSearch", "", false, 20);
/*  514 */     companyDescriptionSearch.setId("CompanyDescriptionSearch");
/*  515 */     labelForm.addElement(companyDescriptionSearch);
/*      */     
/*  517 */     FormTextField divisionDescriptionSearch = new FormTextField("DivisionDescriptionSearch", "", false, 20);
/*  518 */     divisionDescriptionSearch.setId("DivisionDescriptionSearch");
/*  519 */     labelForm.addElement(divisionDescriptionSearch);
/*      */     
/*  521 */     FormTextField labelDescriptionSearch = new FormTextField("LabelDescriptionSearch", "", false, 20);
/*  522 */     labelDescriptionSearch.setId("LabelDescriptionSearch");
/*  523 */     labelForm.addElement(labelDescriptionSearch);
/*      */ 
/*      */     
/*  526 */     FormTextField entityDescriptionSearch = new FormTextField("EntityDescriptionSearch", "", false, 20);
/*  527 */     entityDescriptionSearch.setId("EntityDescriptionSearch");
/*  528 */     labelForm.addElement(entityDescriptionSearch);
/*      */     
/*  530 */     labelForm.addElement(new FormHidden("cmd", "label-editor"));
/*  531 */     labelForm.addElement(new FormHidden("OrderBy", "", true));
/*      */ 
/*      */     
/*  534 */     if (context.getSessionValue("NOTEPAD_LABEL_VISIBLE") != null) {
/*  535 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_LABEL_VISIBLE"));
/*      */     }
/*  537 */     return labelForm;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean save(Context context) {
/*  543 */     User user = MilestoneSecurity.getUser(context);
/*      */     
/*  545 */     Notepad notepad = getLabelNotepad(context, user.getUserId());
/*  546 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/*  548 */     Label label = MilestoneHelper.getScreenLabel(context);
/*      */     
/*  550 */     Form form = buildForm(context, label);
/*      */     
/*  552 */     Label timestampLabel = (Label)context.getSessionValue("Label");
/*  553 */     if (timestampLabel == null || (timestampLabel != null && LabelManager.getInstance().isTimestampValid(timestampLabel))) {
/*      */       
/*  555 */       form.setValues(context);
/*  556 */       String descriptionString = form.getStringValue("CorporateDescription");
/*      */ 
/*      */ 
/*      */       
/*  560 */       String parentStr = form.getStringValue("Parent1Selection");
/*  561 */       int pId = Integer.parseInt(parentStr);
/*  562 */       if (!LabelManager.getInstance().isDuplicate(descriptionString, 4, label.getStructureID(), 
/*  563 */           pId)) {
/*      */ 
/*      */         
/*  566 */         String parentString = form.getStringValue("Parent1Selection");
/*  567 */         int parentId = -1;
/*      */         
/*  569 */         parentId = Integer.parseInt(parentString);
/*      */         
/*  571 */         if (parentId > 0) {
/*  572 */           label.setParentDivision((Division)MilestoneHelper.getStructureObject(parentId));
/*      */         }
/*      */         
/*  575 */         label.setName(descriptionString);
/*      */         
/*  577 */         label.setActive(((FormCheckBox)form.getElement("active")).isChecked());
/*      */ 
/*      */ 
/*      */         
/*  581 */         String westString = form.getStringValue("Distribution");
/*  582 */         if (westString.equalsIgnoreCase("West")) {
/*  583 */           label.setDistribution(0);
/*      */         }
/*  585 */         else if (westString.equalsIgnoreCase("East")) {
/*  586 */           label.setDistribution(1);
/*      */         } else {
/*  588 */           label.setDistribution(2);
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  593 */         String disCoString = form.getStringValue("DistributionCompany");
/*  594 */         label.setDistCoId(Integer.parseInt(disCoString));
/*      */ 
/*      */ 
/*      */         
/*  598 */         label.setDistCoName(getDistCoName((FormDropDownMenu)form.getElement("DistributionCompany")));
/*      */ 
/*      */         
/*  601 */         String abbString = form.getStringValue("CorporateAbbreviation");
/*  602 */         label.setStructureAbbreviation(abbString);
/*      */         
/*  604 */         if (!form.isUnchanged()) {
/*      */           
/*  606 */           FormValidation formValidation = form.validate();
/*  607 */           if (formValidation.isGood()) {
/*      */             
/*  609 */             Label savedLabel = LabelManager.getInstance().saveLabel(label, user.getUserId());
/*      */ 
/*      */             
/*  612 */             FormElement lastUpdated = form.getElement("lastupdateddate");
/*  613 */             if (savedLabel.getLastUpdateDate() != null) {
/*  614 */               lastUpdated.setValue(MilestoneHelper.getLongDate(savedLabel.getLastUpdateDate()));
/*      */             }
/*      */             
/*  617 */             Cache.flushCorporateStructure();
/*      */ 
/*      */             
/*  620 */             context.removeSessionValue("user-companies");
/*  621 */             context.removeSessionValue("user-environments");
/*      */             
/*  623 */             notepad.setAllContents(null);
/*  624 */             notepad = getLabelNotepad(context, user.getUserId());
/*  625 */             notepad.setSelected(savedLabel);
/*  626 */             label = (Label)notepad.validateSelected();
/*      */             
/*  628 */             if (label == null) {
/*  629 */               return goToBlank(context);
/*      */             }
/*  631 */             form = buildForm(context, label);
/*      */             
/*  633 */             context.putDelivery("Form", form);
/*  634 */             context.putSessionValue("Label", label);
/*      */           }
/*      */           else {
/*      */             
/*  638 */             context.putDelivery("FormValidation", formValidation);
/*      */           } 
/*      */         } 
/*  641 */         form.addElement(new FormHidden("OrderBy", "", true));
/*  642 */         context.putDelivery("Form", form);
/*  643 */         return edit(context);
/*      */       } 
/*      */ 
/*      */       
/*  647 */       context.putSessionValue("Label", label);
/*  648 */       context.putDelivery("AlertMessage", 
/*  649 */           MilestoneMessage.getMessage(5, 
/*      */             
/*  651 */             new String[] { "Label", descriptionString }));
/*  652 */       return edit(context);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  657 */     context.putDelivery("AlertMessage", "The record is unavailable for editing at this time.  Please refresh your screen and edit it at a later time.");
/*  658 */     return edit(context);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean delete(Context context) {
/*  669 */     User user = MilestoneSecurity.getUser(context);
/*      */     
/*  671 */     Notepad notepad = getLabelNotepad(context, user.getUserId());
/*  672 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/*  674 */     Label label = MilestoneHelper.getScreenLabel(context);
/*      */ 
/*      */     
/*  677 */     if (label != null) {
/*      */       
/*  679 */       String errorMsg = "";
/*  680 */       errorMsg = CorporateStructureManager.getInstance().delete(label, user.getUserId());
/*  681 */       if (errorMsg != null) {
/*      */         
/*  683 */         context.putDelivery("AlertMessage", errorMsg);
/*      */       }
/*      */       else {
/*      */         
/*  687 */         context.removeSessionValue("Label");
/*      */ 
/*      */         
/*  690 */         Cache.flushCorporateStructure();
/*      */ 
/*      */         
/*  693 */         context.removeSessionValue("user-companies");
/*  694 */         context.removeSessionValue("user-environments");
/*      */         
/*  696 */         notepad.setAllContents(null);
/*  697 */         notepad = getLabelNotepad(context, user.getUserId());
/*  698 */         notepad.setSelected(null);
/*      */       } 
/*      */     } 
/*      */     
/*  702 */     return edit(context);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean newForm(Context context) {
/*  709 */     User user = MilestoneSecurity.getUser(context);
/*      */     
/*  711 */     Notepad notepad = getLabelNotepad(context, user.getUserId());
/*  712 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */ 
/*      */     
/*  715 */     context.putDelivery("CorporateArrays", getJavaScriptCorporateArray(context));
/*      */     
/*  717 */     Form form = buildNewForm(context);
/*  718 */     context.putDelivery("Form", form);
/*      */ 
/*      */     
/*  721 */     if (context.getSessionValue("NOTEPAD_LABEL_VISIBLE") != null) {
/*  722 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_LABEL_VISIBLE"));
/*      */     }
/*  724 */     return context.includeJSP("label-editor.jsp");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Form buildNewForm(Context context) {
/*  733 */     Form labelForm = new Form(this.application, "labelForm", 
/*  734 */         this.application.getInfrastructure().getServletURL(), "POST");
/*      */     
/*  736 */     User user = MilestoneSecurity.getUser(context);
/*      */ 
/*      */     
/*  739 */     Vector families = Cache.getFamilies();
/*  740 */     FormDropDownMenu family = MilestoneHelper.getCorporateStructureDropDown("Parent3Selection", families, "0", true, false);
/*  741 */     family.addFormEvent("onChange", "adjustSelection(this)");
/*  742 */     family.setTabIndex(1);
/*  743 */     labelForm.addElement(family);
/*      */ 
/*      */     
/*  746 */     Vector environments = Cache.getEnvironments();
/*  747 */     FormDropDownMenu environment = MilestoneHelper.getCorporateStructureDropDown("Parent4Selection", environments, "0", true, true);
/*  748 */     environment.addFormEvent("onChange", "adjustSelection(this)");
/*  749 */     environment.setTabIndex(1);
/*  750 */     labelForm.addElement(environment);
/*      */ 
/*      */     
/*  753 */     FormCheckBox active = new FormCheckBox("active", "", true, true);
/*  754 */     active.setTabIndex(6);
/*  755 */     labelForm.addElement(active);
/*      */ 
/*      */     
/*  758 */     Vector companies = Cache.getCompanies();
/*  759 */     FormDropDownMenu company = MilestoneHelper.getCorporateStructureDropDown("Parent2Selection", companies, "0", true, true);
/*  760 */     company.addFormEvent("onChange", "adjustSelection(this)");
/*  761 */     company.setTabIndex(2);
/*  762 */     labelForm.addElement(company);
/*      */ 
/*      */     
/*  765 */     Vector divisions = Cache.getDivisions();
/*  766 */     FormDropDownMenu division = MilestoneHelper.getCorporateStructureDropDown("Parent1Selection", divisions, "0", true, true);
/*  767 */     division.addFormEvent("onChange", "adjustSelection(this)");
/*  768 */     division.setTabIndex(3);
/*  769 */     labelForm.addElement(division);
/*      */ 
/*      */     
/*  772 */     String westEast = "West";
/*  773 */     FormRadioButtonGroup distribution = new FormRadioButtonGroup("Distribution", westEast, "West, East, Unassigned", false);
/*  774 */     distribution.setTabIndex(4);
/*  775 */     labelForm.addElement(distribution);
/*      */ 
/*      */ 
/*      */     
/*  779 */     Hashtable distCoHash = MilestoneHelper_2.getDistCoNames();
/*  780 */     String[] arrayIds = new String[distCoHash.size()];
/*  781 */     String[] arrayText = new String[distCoHash.size()];
/*  782 */     Enumeration distCoKeys = distCoHash.keys();
/*  783 */     int counter = 0;
/*  784 */     while (distCoKeys.hasMoreElements()) {
/*  785 */       Integer currentKey = (Integer)distCoKeys.nextElement();
/*  786 */       arrayIds[counter] = currentKey.toString();
/*  787 */       arrayText[counter] = (String)distCoHash.get(currentKey);
/*  788 */       counter++;
/*      */     } 
/*      */ 
/*      */     
/*  792 */     FormDropDownMenu distCo = new FormDropDownMenu("DistributionCompany", "1", arrayIds, arrayText, true);
/*      */     
/*  794 */     labelForm.addElement(distCo);
/*      */ 
/*      */     
/*  797 */     FormTextField corporateDescription = new FormTextField("CorporateDescription", "", true, 50, 50);
/*  798 */     corporateDescription.addFormEvent("onBlur", "Javascript:checkField(this);fillAbbreviation()");
/*  799 */     corporateDescription.setTabIndex(4);
/*  800 */     labelForm.addElement(corporateDescription);
/*      */ 
/*      */     
/*  803 */     FormTextField corporateAbbreviation = new FormTextField("CorporateAbbreviation", "", true, 3, 3);
/*  804 */     corporateAbbreviation.addFormEvent("onBlur", "checkField(this)");
/*  805 */     corporateAbbreviation.setTabIndex(5);
/*  806 */     labelForm.addElement(corporateAbbreviation);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  812 */     FormTextField familyDescriptionSearch = new FormTextField("FamilyDescriptionSearch", "", false, 20);
/*  813 */     familyDescriptionSearch.setId("FamilyDescriptionSearch");
/*  814 */     labelForm.addElement(familyDescriptionSearch);
/*      */ 
/*      */ 
/*      */     
/*  818 */     FormTextField environmentDescriptionSearch = new FormTextField("EnvironmentDescriptionSearch", "", false, 20);
/*  819 */     environmentDescriptionSearch.setId("EnvironmentDescriptionSearch");
/*  820 */     labelForm.addElement(environmentDescriptionSearch);
/*      */     
/*  822 */     FormTextField companyDescriptionSearch = new FormTextField("CompanyDescriptionSearch", "", false, 20);
/*  823 */     companyDescriptionSearch.setId("CompanyDescriptionSearch");
/*  824 */     labelForm.addElement(companyDescriptionSearch);
/*      */     
/*  826 */     FormTextField divisionDescriptionSearch = new FormTextField("DivisionDescriptionSearch", "", false, 20);
/*  827 */     divisionDescriptionSearch.setId("DivisionDescriptionSearch");
/*  828 */     labelForm.addElement(divisionDescriptionSearch);
/*      */     
/*  830 */     FormTextField labelDescriptionSearch = new FormTextField("LabelDescriptionSearch", "", false, 20);
/*  831 */     labelDescriptionSearch.setId("LabelDescriptionSearch");
/*  832 */     labelForm.addElement(labelDescriptionSearch);
/*      */ 
/*      */     
/*  835 */     FormTextField entityDescriptionSearch = new FormTextField("EntityDescriptionSearch", "", false, 20);
/*  836 */     entityDescriptionSearch.setId("EntityDescriptionSearch");
/*  837 */     labelForm.addElement(entityDescriptionSearch);
/*      */     
/*  839 */     labelForm.addElement(new FormHidden("cmd", "label-edit-new", true));
/*  840 */     labelForm.addElement(new FormHidden("OrderBy", "", true));
/*      */ 
/*      */     
/*  843 */     if (context.getSessionValue("NOTEPAD_LABEL_VISIBLE") != null) {
/*  844 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_LABEL_VISIBLE"));
/*      */     }
/*  846 */     return labelForm;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean saveNew(Context context) {
/*  853 */     User user = MilestoneSecurity.getUser(context);
/*      */     
/*  855 */     Notepad notepad = getLabelNotepad(context, user.getUserId());
/*  856 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/*  858 */     Label label = new Label();
/*      */     
/*  860 */     Form form = buildNewForm(context);
/*  861 */     form.setValues(context);
/*      */     
/*  863 */     String descriptionString = form.getStringValue("CorporateDescription");
/*  864 */     String parentString = form.getStringValue("Parent1Selection");
/*      */     
/*  866 */     int parentID = -1;
/*      */ 
/*      */     
/*      */     try {
/*  870 */       parentID = Integer.parseInt(parentString);
/*      */     }
/*  872 */     catch (NumberFormatException numberFormatException) {}
/*      */ 
/*      */     
/*  875 */     if (!parentString.equalsIgnoreCase("") && parentID > 0) {
/*  876 */       label.setParentDivision((Division)MilestoneHelper.getStructureObject(parentID));
/*      */     }
/*  878 */     label.setName(descriptionString);
/*      */ 
/*      */ 
/*      */     
/*  882 */     String westString = form.getStringValue("Distribution");
/*  883 */     if (westString.equalsIgnoreCase("West")) {
/*  884 */       label.setDistribution(0);
/*      */     } else {
/*  886 */       label.setDistribution(1);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  891 */     String disCoString = form.getStringValue("DistributionCompany");
/*  892 */     label.setDistCoId(Integer.parseInt(disCoString));
/*      */ 
/*      */ 
/*      */     
/*  896 */     label.setDistCoName(getDistCoName((FormDropDownMenu)form.getElement("DistributionCompany")));
/*      */ 
/*      */     
/*  899 */     String abbString = form.getStringValue("CorporateAbbreviation");
/*  900 */     label.setStructureAbbreviation(abbString);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  905 */     label.setStructureID(-2);
/*      */     
/*  907 */     label.setActive(((FormCheckBox)form.getElement("active")).isChecked());
/*      */ 
/*      */     
/*  910 */     int pId = label.getParentDivision().getStructureID();
/*  911 */     if (!LabelManager.getInstance().isDuplicate(descriptionString, 4, label.getStructureID(), 
/*  912 */         pId)) {
/*      */ 
/*      */       
/*  915 */       if (!form.isUnchanged())
/*      */       {
/*  917 */         FormValidation formValidation = form.validate();
/*  918 */         if (formValidation.isGood())
/*      */         {
/*      */           
/*  921 */           Label savedNewLabel = LabelManager.getInstance().saveNewLabel(label, user.getUserId());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  927 */           if (notepad.getAllContents() == null || notepad.getAllContents().size() < 1) {
/*  928 */             notepad.setCorporateObjectSearchObj(null);
/*      */           }
/*  930 */           Cache.flushCorporateStructure();
/*      */ 
/*      */           
/*  933 */           context.removeSessionValue("user-companies");
/*  934 */           context.removeSessionValue("user-environments");
/*      */           
/*  936 */           notepad.setAllContents(null);
/*  937 */           notepad.newSelectedReset();
/*  938 */           notepad = getLabelNotepad(context, user.getUserId());
/*  939 */           notepad.setSelected(savedNewLabel);
/*      */           
/*  941 */           context.putSessionValue("Label", savedNewLabel);
/*      */         }
/*      */         else
/*      */         {
/*  945 */           context.putDelivery("FormValidation", formValidation);
/*  946 */           form.addElement(new FormHidden("OrderBy", "", true));
/*  947 */           context.putDelivery("Form", form);
/*      */           
/*  949 */           context.putDelivery("CorporateArrays", getJavaScriptCorporateArray(context));
/*  950 */           return context.includeJSP("label-editor.jsp");
/*      */         }
/*      */       
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  957 */       context.putDelivery("AlertMessage", 
/*  958 */           MilestoneMessage.getMessage(5, 
/*      */             
/*  960 */             new String[] { "Environment", descriptionString }));
/*      */     } 
/*      */     
/*  963 */     return edit(context);
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
/*      */   private String getJavaScriptCorporateArray(Context context) {
/*  977 */     Vector families = Cache.getFamilies();
/*      */     
/*  979 */     StringBuffer buffer = new StringBuffer("function createChildren()\n{\n  lRoot = ");
/*  980 */     buffer.append("new Node(0, 'Root', [\n");
/*      */     
/*  982 */     for (int i = 0; i < families.size(); i++) {
/*      */       
/*  984 */       Family family = (Family)families.elementAt(i);
/*  985 */       if (family != null) {
/*      */ 
/*      */         
/*  988 */         buffer.append("new Node(");
/*  989 */         buffer.append(family.getStructureID());
/*  990 */         buffer.append(", '");
/*  991 */         buffer.append(MilestoneHelper.urlEncode(family.getName()));
/*  992 */         buffer.append("',[\n");
/*      */         
/*  994 */         Vector environments = family.getEnvironments();
/*      */         
/*  996 */         for (int e = 0; e < environments.size(); e++) {
/*      */           
/*  998 */           Environment environment = (Environment)environments.elementAt(e);
/*  999 */           if (environment != null) {
/*      */ 
/*      */             
/* 1002 */             buffer.append("new Node(");
/* 1003 */             buffer.append(environment.getStructureID());
/* 1004 */             buffer.append(", '");
/* 1005 */             buffer.append(MilestoneHelper.urlEncode(environment.getName()));
/* 1006 */             buffer.append("',[\n");
/*      */ 
/*      */             
/* 1009 */             Vector companies = environment.getCompanies();
/* 1010 */             for (int j = 0; j < companies.size(); j++) {
/*      */               
/* 1012 */               Company company = (Company)companies.elementAt(j);
/* 1013 */               if (company != null) {
/*      */                 
/* 1015 */                 buffer.append("new Node(");
/* 1016 */                 buffer.append(company.getStructureID());
/* 1017 */                 buffer.append(",'");
/* 1018 */                 buffer.append(MilestoneHelper.urlEncode(company.getName()));
/* 1019 */                 buffer.append("',[\n");
/*      */ 
/*      */                 
/* 1022 */                 Vector divisions = company.getDivisions();
/* 1023 */                 for (int y = 0; y < divisions.size(); y++) {
/*      */                   
/* 1025 */                   Division division = (Division)divisions.elementAt(y);
/* 1026 */                   if (division != null) {
/*      */                     
/* 1028 */                     buffer.append("new Node(");
/* 1029 */                     buffer.append(division.getStructureID());
/* 1030 */                     buffer.append(",'");
/* 1031 */                     buffer.append(MilestoneHelper.urlEncode(division.getName()));
/* 1032 */                     buffer.append("',[\n");
/*      */ 
/*      */                     
/* 1035 */                     if (y == divisions.size() - 1) {
/*      */ 
/*      */                       
/* 1038 */                       buffer.append("])");
/*      */                     
/*      */                     }
/*      */                     else {
/*      */                       
/* 1043 */                       buffer.append("]),");
/*      */                     } 
/*      */                   } 
/*      */                 } 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 1051 */                 if (j == companies.size() - 1) {
/*      */ 
/*      */                   
/* 1054 */                   buffer.append("])");
/*      */                 
/*      */                 }
/*      */                 else {
/*      */                   
/* 1059 */                   buffer.append("]),");
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */ 
/*      */ 
/*      */             
/* 1066 */             if (e == environments.size() - 1) {
/*      */ 
/*      */               
/* 1069 */               buffer.append("])");
/*      */             
/*      */             }
/*      */             else {
/*      */               
/* 1074 */               buffer.append("]),");
/*      */             } 
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 1080 */         if (i == families.size() - 1) {
/*      */ 
/*      */           
/* 1083 */           buffer.append("])");
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 1088 */           buffer.append("]),");
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1096 */     buffer.append("]);\n");
/*      */     
/* 1098 */     buffer.append("\n }//end function createChildren");
/*      */     
/* 1100 */     return buffer.toString();
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
/*      */   private boolean goToBlank(Context context) {
/* 1115 */     context.putDelivery("isNotePadVisible", new Boolean(MilestoneHelper.getNotePadVisiblitiy(12, context)));
/*      */     
/* 1117 */     Form form = new Form(this.application, "form", this.application.getInfrastructure().getServletURL(), "Post");
/* 1118 */     form.addElement(new FormHidden("cmd", "label-edit-new"));
/* 1119 */     form.addElement(new FormHidden("OrderBy", "", true));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1124 */     FormTextField environmentDescriptionSearch = new FormTextField("EnvironmentDescriptionSearch", "", true, 20);
/* 1125 */     environmentDescriptionSearch.setId("EnvironmentDescriptionSearch");
/* 1126 */     form.addElement(environmentDescriptionSearch);
/*      */ 
/*      */ 
/*      */     
/* 1130 */     FormTextField familyDescriptionSearch = new FormTextField("FamilyDescriptionSearch", "", true, 20);
/* 1131 */     familyDescriptionSearch.setId("FamilyDescriptionSearch");
/* 1132 */     form.addElement(familyDescriptionSearch);
/*      */ 
/*      */     
/* 1135 */     FormTextField companyDescriptionSearch = new FormTextField("CompanyDescriptionSearch", "", true, 20);
/* 1136 */     companyDescriptionSearch.setId("CompanyDescriptionSearch");
/* 1137 */     form.addElement(companyDescriptionSearch);
/*      */     
/* 1139 */     FormTextField divisionDescriptionSearch = new FormTextField("DivisionDescriptionSearch", "", true, 20);
/* 1140 */     divisionDescriptionSearch.setId("DivisionDescriptionSearch");
/* 1141 */     form.addElement(divisionDescriptionSearch);
/*      */     
/* 1143 */     FormTextField labelDescriptionSearch = new FormTextField("LabelDescriptionSearch", "", true, 20);
/* 1144 */     labelDescriptionSearch.setId("LabelDescriptionSearch");
/* 1145 */     form.addElement(labelDescriptionSearch);
/*      */ 
/*      */     
/* 1148 */     FormTextField entityDescriptionSearch = new FormTextField("EntityDescriptionSearch", "", false, 20);
/* 1149 */     entityDescriptionSearch.setId("EntityDescriptionSearch");
/* 1150 */     form.addElement(entityDescriptionSearch);
/*      */     
/* 1152 */     context.putDelivery("Form", form);
/*      */     
/* 1154 */     return context.includeJSP("blank-label-editor.jsp");
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
/*      */   public String getDistCoName(FormDropDownMenu disCoElement) {
/* 1168 */     String[] menuItems = disCoElement.getMenuTextList();
/* 1169 */     String[] valueVector = disCoElement.getValueList();
/* 1170 */     for (int i = 0; i < menuItems.length; i++) {
/* 1171 */       if (valueVector[i].equals(disCoElement.getStringValue())) {
/* 1172 */         return menuItems[i];
/*      */       }
/*      */     } 
/* 1175 */     return "";
/*      */   }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\LabelHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */