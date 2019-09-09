/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.gemini.Context;
/*     */ import com.techempower.gemini.Dispatcher;
/*     */ import com.techempower.gemini.FormDateField;
/*     */ import com.techempower.gemini.FormDropDownMenu;
/*     */ import com.techempower.gemini.FormElement;
/*     */ import com.techempower.gemini.FormHidden;
/*     */ import com.techempower.gemini.FormTextField;
/*     */ import com.techempower.gemini.FormValidation;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.Cache;
/*     */ import com.universal.milestone.Day;
/*     */ import com.universal.milestone.DayHandler;
/*     */ import com.universal.milestone.DayManager;
/*     */ import com.universal.milestone.Form;
/*     */ import com.universal.milestone.MilestoneConstants;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.MilestoneSecurity;
/*     */ import com.universal.milestone.Notepad;
/*     */ import com.universal.milestone.SecureHandler;
/*     */ import com.universal.milestone.User;
/*     */ import com.universal.milestone.UserManager;
/*     */ import java.text.DateFormat;
/*     */ import java.util.Calendar;
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
/*     */ public class DayHandler
/*     */   extends SecureHandler
/*     */ {
/*     */   public static final String COMPONENT_CODE = "hDay";
/*     */   public GeminiApplication application;
/*     */   public ComponentLog log;
/*     */   
/*     */   public DayHandler(GeminiApplication application) {
/*  62 */     this.application = application;
/*  63 */     this.log = application.getLog("hDay");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   public String getDescription() { return "Day Type Handler"; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean acceptRequest(Dispatcher dispatcher, Context context, String command) {
/*  81 */     if (super.acceptRequest(dispatcher, context, command))
/*     */     {
/*  83 */       if (command.startsWith("daytype"))
/*     */       {
/*  85 */         return handleRequest(dispatcher, context, command);
/*     */       }
/*     */     }
/*  88 */     return false;
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
/* 100 */     if (command.equalsIgnoreCase("daytype-search")) {
/*     */       
/* 102 */       search(dispatcher, context, command);
/*     */     }
/* 104 */     else if (command.equalsIgnoreCase("daytype-editor")) {
/*     */       
/* 106 */       edit(context);
/*     */     }
/* 108 */     else if (command.equalsIgnoreCase("daytype-edit-save")) {
/*     */       
/* 110 */       editSave(context);
/*     */     }
/* 112 */     else if (command.equalsIgnoreCase("daytype-edit-save-new")) {
/*     */       
/* 114 */       saveNew(context);
/*     */     }
/* 116 */     else if (command.equalsIgnoreCase("daytype-edit-delete")) {
/*     */       
/* 118 */       delete(context);
/*     */     }
/* 120 */     else if (command.equalsIgnoreCase("daytype-edit-new")) {
/*     */       
/* 122 */       newForm(context);
/*     */     }
/* 124 */     else if (command.equalsIgnoreCase("daytype-sort")) {
/*     */       
/* 126 */       sort(dispatcher, context);
/*     */     } 
/* 128 */     return true;
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
/*     */   private boolean search(Dispatcher dispatcher, Context context, String command) {
/* 141 */     User user = MilestoneSecurity.getUser(context);
/* 142 */     Notepad notepad = getDayNotepad(context, user.getUserId());
/*     */ 
/*     */     
/* 145 */     notepad.setAllContents(null);
/* 146 */     notepad.setSelected(null);
/*     */     
/* 148 */     DayManager.getInstance().setNotepadQuery(context, MilestoneSecurity.getUser(context).getUserId(), notepad);
/* 149 */     dispatcher.redispatch(context, "daytype-editor");
/*     */     
/* 151 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean sort(Dispatcher dispatcher, Context context) {
/* 161 */     int sort = Integer.parseInt(context.getParameter("OrderBy"));
/*     */     
/* 163 */     Notepad notepad = getDayNotepad(context, MilestoneSecurity.getUser(context).getUserId());
/*     */     
/* 165 */     if (notepad.getSearchQuery() == null || notepad.getSearchQuery().equals("")) {
/* 166 */       DayManager.getInstance(); notepad.setSearchQuery("SELECT [grouping], [date], [value], [type_id]  FROM vi_Day_Type");
/*     */     } 
/* 168 */     notepad.setOrderBy(" ORDER BY " + MilestoneConstants.SORT_DAYTYPE[sort]);
/*     */ 
/*     */     
/* 171 */     notepad.setAllContents(null);
/* 172 */     notepad = getDayNotepad(context, MilestoneSecurity.getUser(context).getUserId());
/* 173 */     notepad.goToSelectedPage();
/*     */     
/* 175 */     dispatcher.redispatch(context, "daytype-editor");
/*     */     
/* 177 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean edit(Context context) {
/* 184 */     User user = MilestoneSecurity.getUser(context);
/*     */     
/* 186 */     Notepad notepad = getDayNotepad(context, user.getUserId());
/* 187 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*     */     
/* 189 */     Day day = MilestoneHelper.getScreenDay(context);
/*     */     
/* 191 */     if (day != null) {
/*     */       
/* 193 */       Form form = null;
/*     */       
/* 195 */       if (day != null) {
/* 196 */         form = buildForm(context, day);
/*     */       } else {
/* 198 */         form = buildNewForm(context);
/*     */       } 
/* 200 */       context.putDelivery("Form", form);
/* 201 */       return context.includeJSP("daytype-editor.jsp");
/*     */     } 
/*     */ 
/*     */     
/* 205 */     return goToBlank(context);
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
/*     */   protected Form buildForm(Context context, Day day) {
/* 217 */     DateFormat dateFormatter = DateFormat.getDateInstance(3);
/* 218 */     Form daytypeForm = new Form(this.application, "daytypeForm", 
/* 219 */         this.application.getInfrastructure().getServletURL(), "POST");
/* 220 */     User user = MilestoneSecurity.getUser(context);
/*     */ 
/*     */     
/* 223 */     FormDropDownMenu Group = new FormDropDownMenu("Group", Integer.toString(day.getCalendarGroup()), "2,1", "Canada, United States", true);
/* 224 */     Group.setTabIndex(1);
/* 225 */     daytypeForm.addElement(Group);
/*     */ 
/*     */     
/* 228 */     FormTextField DayType = new FormTextField("DayType", "", false, 2, 2);
/* 229 */     DayType.setTabIndex(2);
/* 230 */     DayType.setValue(day.getDayType());
/* 231 */     daytypeForm.addElement(DayType);
/*     */ 
/*     */     
/* 234 */     FormTextField Description = new FormTextField("Description", "", false, 50, 50);
/* 235 */     Description.setTabIndex(3);
/* 236 */     Description.setValue(day.getDescription());
/* 237 */     daytypeForm.addElement(Description);
/*     */ 
/*     */     
/* 240 */     FormTextField SpecialDate = new FormTextField("SpecialDate", "", true, 10);
/* 241 */     SpecialDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/* 242 */     SpecialDate.addFormEvent("onBlur", "Javascript:checkField( this )");
/* 243 */     SpecialDate.setTabIndex(4);
/* 244 */     SpecialDate.setValue(dateFormatter.format(day.getSpecificDate().getTime()));
/* 245 */     daytypeForm.addElement(SpecialDate);
/*     */ 
/*     */     
/* 248 */     FormTextField lastUpdated = new FormTextField("lastUpdatedDate", false, 50);
/* 249 */     if (day.getLastUpdateDate() != null)
/* 250 */       lastUpdated.setValue(MilestoneHelper.getCustomFormatedDate(day.getLastUpdateDate(), "M/d/yyyy hh:mm:ss a 'ET'")); 
/* 251 */     daytypeForm.addElement(lastUpdated);
/*     */     
/* 253 */     FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
/* 254 */     if (UserManager.getInstance().getUser(day.getLastUpdatingUser()) != null)
/* 255 */       lastUpdatedBy.setValue(UserManager.getInstance().getUser(day.getLastUpdatingUser()).getName()); 
/* 256 */     daytypeForm.addElement(lastUpdatedBy);
/*     */ 
/*     */     
/* 259 */     FormDropDownMenu GroupSearch = new FormDropDownMenu("GroupSearch", "", "0,2,1", "&nbsp;,Canada, United States", false);
/* 260 */     GroupSearch.setId("GroupSearch");
/* 261 */     daytypeForm.addElement(GroupSearch);
/*     */     
/* 263 */     FormTextField SpecialDateSearch = new FormTextField("SpecialDateSearch", "", false, 8);
/* 264 */     SpecialDateSearch.setId("SpecialDateSearch");
/* 265 */     daytypeForm.addElement(SpecialDateSearch);
/*     */     
/* 267 */     FormTextField DayTypeSearch = new FormTextField("DayTypeSearch", "", false, 2);
/* 268 */     DayTypeSearch.setId("DayTypeSearch");
/* 269 */     daytypeForm.addElement(DayTypeSearch);
/*     */     
/* 271 */     FormTextField DescriptionSearch = new FormTextField("DescriptionSearch", "", false, 20);
/* 272 */     DescriptionSearch.setId("DescriptionSearch");
/* 273 */     daytypeForm.addElement(DescriptionSearch);
/*     */     
/* 275 */     daytypeForm.addElement(new FormHidden("cmd", "daytype-editor"));
/* 276 */     daytypeForm.addElement(new FormHidden("OrderBy", "", true));
/*     */ 
/*     */     
/* 279 */     context.putSessionValue("day", day);
/*     */ 
/*     */     
/* 282 */     if (context.getSessionValue("NOTEPAD_DAYTYPE_VISIBLE") != null) {
/* 283 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_DAYTYPE_VISIBLE"));
/*     */     }
/* 285 */     return daytypeForm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Notepad getDayNotepad(Context context, int userId) {
/* 294 */     Vector contents = new Vector();
/*     */     
/* 296 */     if (MilestoneHelper.getNotepadFromSession(6, context) != null) {
/*     */       
/* 298 */       Notepad notepad = MilestoneHelper.getNotepadFromSession(6, context);
/*     */       
/* 300 */       if (notepad.getAllContents() == null) {
/*     */         
/* 302 */         contents = DayManager.getInstance().getDayNotepadList(userId, notepad);
/* 303 */         notepad.setAllContents(contents);
/*     */       } 
/*     */       
/* 306 */       return notepad;
/*     */     } 
/*     */ 
/*     */     
/* 310 */     String[] columnNames = { "Group", "Special Date", "Day Type" };
/* 311 */     contents = DayManager.getInstance().getDayNotepadList(userId, null);
/* 312 */     return new Notepad(contents, 0, 7, "Day Type", 6, columnNames);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean editSave(Context context) {
/* 319 */     User user = MilestoneSecurity.getUser(context);
/*     */ 
/*     */     
/* 322 */     Notepad notepad = getDayNotepad(context, user.getUserId());
/* 323 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*     */     
/* 325 */     Day day = MilestoneHelper.getScreenDay(context);
/*     */     
/* 327 */     Form form = buildForm(context, day);
/*     */ 
/*     */     
/* 330 */     if (DayManager.getInstance().isTimestampValid(day)) {
/*     */ 
/*     */       
/* 333 */       form.setValues(context);
/*     */       
/* 335 */       int groupNum = 0;
/*     */ 
/*     */       
/*     */       try {
/* 339 */         groupNum = Integer.parseInt(form.getStringValue("Group"));
/*     */       }
/* 341 */       catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 347 */       String dayTypeString = form.getStringValue("DayType");
/*     */ 
/*     */       
/* 350 */       if (dayTypeString.equals(""))
/*     */       {
/* 352 */         dayTypeString = "H";
/*     */       }
/*     */ 
/*     */       
/* 356 */       String descriptionString = form.getStringValue("Description");
/*     */ 
/*     */       
/* 359 */       String dateString = form.getStringValue("SpecialDate");
/*     */       
/* 361 */       day.setCalendarGroup(groupNum);
/*     */       
/* 363 */       day.setDayType(dayTypeString);
/* 364 */       day.setDescription(descriptionString);
/* 365 */       day.setSpecificDate(MilestoneHelper.getDate(dateString));
/* 366 */       if (!form.isUnchanged()) {
/*     */         
/* 368 */         FormValidation formValidation = form.validate();
/* 369 */         if (formValidation.isGood()) {
/*     */           
/* 371 */           if (!DayManager.getInstance().isDuplicate(day))
/*     */           {
/* 373 */             Day savedDay = DayManager.getInstance().saveDay(day, user.getUserId());
/*     */ 
/*     */             
/* 376 */             FormElement lastUpdated = form.getElement("lastupdateddate");
/* 377 */             lastUpdated.setValue(MilestoneHelper.getLongDate(savedDay.getLastUpdateDate()));
/*     */ 
/*     */             
/* 380 */             notepad.setAllContents(null);
/*     */             
/* 382 */             Cache.flushDayTypes();
/* 383 */             notepad = getDayNotepad(context, user.getUserId());
/* 384 */             notepad.setSelected(savedDay);
/* 385 */             day = (Day)notepad.validateSelected();
/* 386 */             context.putSessionValue("Day", day);
/*     */             
/* 388 */             if (day == null) {
/* 389 */               return goToBlank(context);
/*     */             }
/* 391 */             form = buildForm(context, day);
/*     */           }
/*     */           else
/*     */           {
/* 395 */             context.putDelivery("AlertMessage", "Could not save the day type because there is already such a record. Please try it again later");
/* 396 */             return edit(context);
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 401 */           context.putDelivery("FormValidation", formValidation);
/*     */         }
/*     */       
/*     */       } 
/*     */     } else {
/*     */       
/* 407 */       context.putDelivery("AlertMessage", "The record is unavailable for editing at this time.  Please refresh your screen and edit it at a later time.");
/* 408 */       return edit(context);
/*     */     } 
/*     */     
/* 411 */     context.putDelivery("Form", form);
/* 412 */     return context.includeJSP("daytype-editor.jsp");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean delete(Context context) {
/* 418 */     User user = MilestoneSecurity.getUser(context);
/* 419 */     Notepad notepad = getDayNotepad(context, user.getUserId());
/* 420 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*     */     
/* 422 */     Day day = MilestoneHelper.getScreenDay(context);
/*     */ 
/*     */     
/* 425 */     if (day != null) {
/*     */       
/* 427 */       DayManager.getInstance().deleteDay(day, user.getUserId());
/*     */ 
/*     */       
/* 430 */       notepad.setSelected(null);
/* 431 */       notepad.setAllContents(null);
/*     */       
/* 433 */       Cache.flushDayTypes();
/* 434 */       notepad = getDayNotepad(context, user.getUserId());
/* 435 */       notepad.setSelected(null);
/*     */     } 
/*     */     
/* 438 */     return edit(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean newForm(Context context) {
/* 444 */     User user = MilestoneSecurity.getUser(context);
/*     */     
/* 446 */     Notepad notepad = getDayNotepad(context, user.getUserId());
/* 447 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*     */     
/* 449 */     Form form = buildNewForm(context);
/* 450 */     context.putDelivery("Form", form);
/*     */ 
/*     */     
/* 453 */     if (context.getSessionValue("NOTEPAD_DAYTYPE_VISIBLE") != null) {
/* 454 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_DAYTYPE_VISIBLE"));
/*     */     }
/* 456 */     return context.includeJSP("daytype-editor.jsp");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Form buildNewForm(Context context) {
/* 465 */     DateFormat dateFormatter = DateFormat.getDateInstance(3);
/* 466 */     Form daytypeForm = new Form(this.application, "daytypeForm", 
/* 467 */         this.application.getInfrastructure().getServletURL(), "POST");
/* 468 */     User user = MilestoneSecurity.getUser(context);
/*     */ 
/*     */     
/* 471 */     FormDropDownMenu Group = new FormDropDownMenu("Group", "0", "2,1", "Canada, United States", true);
/* 472 */     Group.setTabIndex(1);
/* 473 */     daytypeForm.addElement(Group);
/*     */ 
/*     */     
/* 476 */     FormTextField DayType = new FormTextField("DayType", "", false, 2, 2);
/* 477 */     DayType.setTabIndex(2);
/* 478 */     daytypeForm.addElement(DayType);
/*     */ 
/*     */     
/* 481 */     FormTextField Description = new FormTextField("Description", "", false, 50, 50);
/* 482 */     Description.setTabIndex(3);
/* 483 */     daytypeForm.addElement(Description);
/*     */ 
/*     */     
/* 486 */     FormDateField SpecialDate = new FormDateField("SpecialDate", MilestoneHelper.getFormatedDate(Calendar.getInstance()), true, 10);
/* 487 */     SpecialDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/* 488 */     SpecialDate.addFormEvent("onBlur", "Javascript:checkField( this )");
/* 489 */     SpecialDate.setTabIndex(4);
/* 490 */     daytypeForm.addElement(SpecialDate);
/*     */ 
/*     */     
/* 493 */     FormDropDownMenu GroupSearch = new FormDropDownMenu("GroupSearch", "", "0,2,1", "&nbsp;,Canada, United States", false);
/* 494 */     GroupSearch.setId("GroupSearch");
/* 495 */     daytypeForm.addElement(GroupSearch);
/*     */     
/* 497 */     FormTextField SpecialDateSearch = new FormTextField("SpecialDateSearch", "", false, 8);
/* 498 */     SpecialDateSearch.setId("SpecialDateSearch");
/* 499 */     daytypeForm.addElement(SpecialDateSearch);
/*     */     
/* 501 */     FormTextField DayTypeSearch = new FormTextField("DayTypeSearch", "", false, 2);
/* 502 */     DayTypeSearch.setId("DayTypeSearch");
/* 503 */     daytypeForm.addElement(DayTypeSearch);
/*     */     
/* 505 */     FormTextField DescriptionSearch = new FormTextField("DescriptionSearch", "", false, 20);
/* 506 */     DescriptionSearch.setId("DescriptionSearch");
/* 507 */     daytypeForm.addElement(DescriptionSearch);
/*     */     
/* 509 */     daytypeForm.addElement(new FormHidden("cmd", "daytype-edit-new"));
/* 510 */     daytypeForm.addElement(new FormHidden("OrderBy", "", true));
/*     */ 
/*     */     
/* 513 */     if (context.getSessionValue("NOTEPAD_DAYTYPE_VISIBLE") != null) {
/* 514 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_DAYTYPE_VISIBLE"));
/*     */     }
/* 516 */     return daytypeForm;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean saveNew(Context context) {
/* 522 */     User user = MilestoneSecurity.getUser(context);
/* 523 */     Notepad notepad = getDayNotepad(context, user.getUserId());
/* 524 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*     */     
/* 526 */     Day dayType = new Day();
/*     */     
/* 528 */     Form form = buildNewForm(context);
/*     */     
/* 530 */     form.setValues(context);
/*     */ 
/*     */     
/* 533 */     String groupString = form.getStringValue("Group");
/*     */     
/* 535 */     int groupNum = 0;
/*     */ 
/*     */     
/*     */     try {
/* 539 */       groupNum = Integer.parseInt(form.getStringValue("Group"));
/*     */     }
/* 541 */     catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 547 */     String dayTypeString = form.getStringValue("DayType");
/* 548 */     if (dayTypeString.equals(""))
/*     */     {
/*     */ 
/*     */       
/* 552 */       dayTypeString = "H";
/*     */     }
/*     */ 
/*     */     
/* 556 */     String descriptionString = form.getStringValue("Description");
/*     */ 
/*     */     
/* 559 */     String dateString = form.getStringValue("SpecialDate");
/*     */     
/* 561 */     dayType.setCalendarGroup(groupNum);
/* 562 */     dayType.setDayType(dayTypeString);
/* 563 */     dayType.setDescription(descriptionString);
/* 564 */     dayType.setSpecificDate(MilestoneHelper.getDate(dateString));
/*     */     
/* 566 */     if (!DayManager.getInstance().isDuplicate(dayType)) {
/*     */       
/* 568 */       if (!form.isUnchanged()) {
/*     */         
/* 570 */         FormValidation formValidation = form.validate();
/* 571 */         if (formValidation.isGood())
/*     */         {
/* 573 */           Day saveNewDayType = DayManager.getInstance().saveNewDayType(dayType, user.getUserId());
/*     */           
/* 575 */           context.putSessionValue("DayType", saveNewDayType);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 580 */           if (notepad.getAllContents() == null || notepad.getAllContents().size() < 1)
/*     */           {
/* 582 */             notepad.setSearchQuery("");
/*     */           }
/* 584 */           notepad.setAllContents(null);
/* 585 */           notepad.newSelectedReset();
/*     */           
/* 587 */           Cache.flushDayTypes();
/* 588 */           notepad = getDayNotepad(context, user.getUserId());
/* 589 */           notepad.setSelected(saveNewDayType);
/*     */         }
/*     */         else
/*     */         {
/* 593 */           context.putDelivery("FormValidation", formValidation);
/* 594 */           form.addElement(new FormHidden("OrderBy", "", true));
/* 595 */           context.putDelivery("Form", form);
/* 596 */           return context.includeJSP("daytype-editor.jsp");
/*     */         }
/*     */       
/*     */       } 
/*     */     } else {
/*     */       
/* 602 */       context.putDelivery("AlertMessage", "Could not save the day type because there is already such a record. Please try it again later");
/*     */     } 
/*     */     
/* 605 */     return edit(context);
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
/*     */   
/*     */   private boolean goToBlank(Context context) {
/* 622 */     context.putDelivery("isNotePadVisible", new Boolean(MilestoneHelper.getNotePadVisiblitiy(6, context)));
/*     */     
/* 624 */     Form form = new Form(this.application, "form", this.application.getInfrastructure().getServletURL(), "Post");
/* 625 */     form.addElement(new FormHidden("cmd", "daytype-editor"));
/* 626 */     form.addElement(new FormHidden("OrderBy", "", true));
/*     */ 
/*     */     
/* 629 */     FormDropDownMenu GroupSearch = new FormDropDownMenu("GroupSearch", "", "0,2,1", "&nbsp;,Canada, United States", true);
/* 630 */     GroupSearch.setId("GroupSearch");
/* 631 */     form.addElement(GroupSearch);
/*     */     
/* 633 */     FormTextField SpecialDateSearch = new FormTextField("SpecialDateSearch", "", false, 8);
/* 634 */     SpecialDateSearch.setId("SpecialDateSearch");
/* 635 */     form.addElement(SpecialDateSearch);
/*     */     
/* 637 */     FormTextField DayTypeSearch = new FormTextField("DayTypeSearch", "", false, 2);
/* 638 */     DayTypeSearch.setId("DayTypeSearch");
/* 639 */     form.addElement(DayTypeSearch);
/*     */     
/* 641 */     FormTextField DescriptionSearch = new FormTextField("DescriptionSearch", "", false, 20);
/* 642 */     DescriptionSearch.setId("DescriptionSearch");
/* 643 */     form.addElement(DescriptionSearch);
/*     */     
/* 645 */     context.putDelivery("Form", form);
/*     */     
/* 647 */     return context.includeJSP("blank-daytype-editor.jsp");
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\DayHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */