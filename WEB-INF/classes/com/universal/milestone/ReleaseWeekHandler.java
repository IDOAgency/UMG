/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.gemini.Context;
/*     */ import com.techempower.gemini.Dispatcher;
/*     */ import com.techempower.gemini.FormDateField;
/*     */ import com.techempower.gemini.FormHidden;
/*     */ import com.techempower.gemini.FormTextField;
/*     */ import com.techempower.gemini.FormValidation;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.Form;
/*     */ import com.universal.milestone.MilestoneConstants;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.MilestoneMessage;
/*     */ import com.universal.milestone.MilestoneSecurity;
/*     */ import com.universal.milestone.Notepad;
/*     */ import com.universal.milestone.ReleaseWeek;
/*     */ import com.universal.milestone.ReleaseWeekHandler;
/*     */ import com.universal.milestone.ReleaseWeekManager;
/*     */ import com.universal.milestone.SecureHandler;
/*     */ import com.universal.milestone.User;
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
/*     */ 
/*     */ public class ReleaseWeekHandler
/*     */   extends SecureHandler
/*     */ {
/*     */   public static final String COMPONENT_CODE = "hRwk";
/*     */   public GeminiApplication application;
/*     */   public ComponentLog log;
/*     */   
/*     */   public ReleaseWeekHandler(GeminiApplication application) {
/*  59 */     this.application = application;
/*  60 */     this.log = application.getLog("hRwk");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   public String getDescription() { return "Release Week Handler"; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean acceptRequest(Dispatcher dispatcher, Context context, String command) {
/*  78 */     if (super.acceptRequest(dispatcher, context, command))
/*     */     {
/*  80 */       if (command.startsWith("release-week"))
/*     */       {
/*  82 */         return handleRequest(dispatcher, context, command);
/*     */       }
/*     */     }
/*  85 */     return false;
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
/*  97 */     if (command.equalsIgnoreCase("release-week-search"))
/*     */     {
/*  99 */       search(dispatcher, context, command);
/*     */     }
/* 101 */     if (command.equalsIgnoreCase("release-week-sort")) {
/*     */       
/* 103 */       sort(dispatcher, context);
/*     */     }
/* 105 */     else if (command.equalsIgnoreCase("release-week-editor")) {
/*     */       
/* 107 */       edit(context);
/*     */     }
/* 109 */     else if (command.equalsIgnoreCase("release-week-edit-save")) {
/*     */       
/* 111 */       save(context);
/*     */     }
/* 113 */     else if (command.equalsIgnoreCase("release-week-edit-save-new")) {
/*     */       
/* 115 */       saveNew(context);
/*     */     }
/* 117 */     else if (command.equalsIgnoreCase("release-week-edit-delete")) {
/*     */       
/* 119 */       delete(context);
/*     */     }
/* 121 */     else if (command.equalsIgnoreCase("release-week-edit-new")) {
/*     */       
/* 123 */       newForm(context);
/*     */     } 
/* 125 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean search(Dispatcher dispatcher, Context context, String command) {
/* 135 */     Notepad notepad = MilestoneHelper.getNotepadFromSession(15, context);
/*     */ 
/*     */     
/* 138 */     notepad.setAllContents(null);
/* 139 */     notepad.setSelected(null);
/*     */     
/* 141 */     ReleaseWeekManager.getInstance().setNotepadQuery(context, MilestoneSecurity.getUser(context).getUserId(), notepad);
/* 142 */     dispatcher.redispatch(context, "release-week-editor");
/* 143 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean sort(Dispatcher dispatcher, Context context) {
/* 152 */     int sort = Integer.parseInt(context.getParameter("OrderBy"));
/* 153 */     Notepad notepad = MilestoneHelper.getNotepadFromSession(15, context);
/*     */     
/* 155 */     if (notepad.getSearchQuery() == null || notepad.getSearchQuery().equals("")) {
/* 156 */       ReleaseWeekManager.getInstance(); notepad.setSearchQuery("SELECT [name], [start_date], [end_date]FROM vi_Date_Period");
/*     */     } 
/* 158 */     notepad.setOrderBy(" ORDER BY " + MilestoneConstants.SORT_RELEASE_WEEK[sort]);
/*     */ 
/*     */     
/* 161 */     notepad.setAllContents(null);
/* 162 */     notepad = getReleaseWeekNotepad(context, MilestoneSecurity.getUser(context).getUserId());
/* 163 */     notepad.goToSelectedPage();
/*     */     
/* 165 */     dispatcher.redispatch(context, "release-week-editor");
/*     */     
/* 167 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Notepad getReleaseWeekNotepad(Context context, int userId) {
/* 176 */     Vector contents = new Vector();
/*     */     
/* 178 */     if (MilestoneHelper.getNotepadFromSession(15, context) != null) {
/*     */       
/* 180 */       Notepad notepad = MilestoneHelper.getNotepadFromSession(15, context);
/*     */       
/* 182 */       if (notepad.getAllContents() == null) {
/*     */         
/* 184 */         contents = ReleaseWeekManager.getInstance().getReleaseWeekNotepadList(userId, notepad);
/* 185 */         notepad.setAllContents(contents);
/*     */       } 
/*     */       
/* 188 */       return notepad;
/*     */     } 
/*     */ 
/*     */     
/* 192 */     String[] columnNames = { "Name", "Start Date", "End Date" };
/* 193 */     contents = ReleaseWeekManager.getInstance().getReleaseWeekNotepadList(userId, null);
/* 194 */     return new Notepad(contents, 0, 15, "Release Week", 15, columnNames);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean edit(Context context) {
/* 201 */     User user = MilestoneSecurity.getUser(context);
/*     */     
/* 203 */     Notepad notepad = getReleaseWeekNotepad(context, user.getUserId());
/* 204 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*     */     
/* 206 */     ReleaseWeek releaseWeek = MilestoneHelper.getScreenReleaseWeek(context);
/*     */     
/* 208 */     if (releaseWeek != null) {
/*     */       
/* 210 */       Form form = null;
/* 211 */       if (releaseWeek != null) {
/* 212 */         form = buildForm(context, releaseWeek);
/*     */       } else {
/* 214 */         form = buildNewForm(context);
/*     */       } 
/* 216 */       context.putDelivery("Form", form);
/* 217 */       return context.includeJSP("release-week-editor.jsp");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 222 */     goToBlank(context);
/* 223 */     return context.includeJSP("blank-release-week-editor.jsp");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Form buildForm(Context context, ReleaseWeek releaseWeek) {
/* 232 */     Form releaseWeekForm = new Form(this.application, "releaseWeekForm", 
/* 233 */         this.application.getInfrastructure().getServletURL(), "POST");
/* 234 */     User user = MilestoneSecurity.getUser(context);
/*     */ 
/*     */     
/* 237 */     Notepad notepad = getReleaseWeekNotepad(context, user.getUserId());
/* 238 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*     */ 
/*     */     
/* 241 */     String name = "";
/* 242 */     name = releaseWeek.getName();
/* 243 */     FormTextField theName = new FormTextField("theName", name, true, 50, 50);
/* 244 */     theName.setTabIndex(1);
/* 245 */     releaseWeekForm.addElement(theName);
/*     */ 
/*     */     
/* 248 */     String cycle = "";
/* 249 */     cycle = releaseWeek.getCycle();
/* 250 */     FormTextField Cycle = new FormTextField("Cycle", cycle, true, 8, 8);
/* 251 */     Cycle.setTabIndex(2);
/* 252 */     releaseWeekForm.addElement(Cycle);
/*     */ 
/*     */     
/* 255 */     String startDate = "";
/* 256 */     startDate = MilestoneHelper.getFormatedDate(releaseWeek.getStartDate());
/* 257 */     FormDateField StartDate = new FormDateField("StartDate", startDate, true, 10);
/* 258 */     StartDate.addFormEvent("onBlur", "checkField( this );");
/* 259 */     StartDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/* 260 */     StartDate.setTabIndex(3);
/* 261 */     releaseWeekForm.addElement(StartDate);
/*     */ 
/*     */     
/* 264 */     String endDate = "";
/* 265 */     endDate = MilestoneHelper.getFormatedDate(releaseWeek.getEndDate());
/* 266 */     FormDateField EndDate = new FormDateField("EndDate", endDate, true, 10);
/* 267 */     EndDate.addFormEvent("onBlur", "checkField( this );");
/* 268 */     EndDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/* 269 */     EndDate.setTabIndex(4);
/* 270 */     releaseWeekForm.addElement(EndDate);
/*     */ 
/*     */     
/* 273 */     String solDate = "";
/* 274 */     solDate = MilestoneHelper.getFormatedDate(releaseWeek.getSolDate());
/* 275 */     FormDateField SolDate = new FormDateField("SolDate", solDate, false, 10);
/* 276 */     SolDate.addFormEvent("onBlur", "checkField( this );");
/* 277 */     SolDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/* 278 */     SolDate.setTabIndex(5);
/* 279 */     releaseWeekForm.addElement(SolDate);
/*     */ 
/*     */     
/* 282 */     FormTextField NameSearch = new FormTextField("NameSearch", "", false, 20);
/* 283 */     NameSearch.setId("NameSearch");
/* 284 */     releaseWeekForm.addElement(NameSearch);
/*     */     
/* 286 */     FormTextField CycleSearch = new FormTextField("CycleSearch", "", false, 8);
/* 287 */     CycleSearch.setId("CycleSearch");
/* 288 */     releaseWeekForm.addElement(CycleSearch);
/*     */     
/* 290 */     FormTextField StartDateSearch = new FormTextField("StartDateSearch", "", false, 10);
/* 291 */     StartDateSearch.setId("StartDateSearch");
/* 292 */     releaseWeekForm.addElement(StartDateSearch);
/*     */     
/* 294 */     FormTextField EndDateSearch = new FormTextField("EndDateSearch", "", false, 10);
/* 295 */     EndDateSearch.setId("EndDateSearch");
/* 296 */     releaseWeekForm.addElement(EndDateSearch);
/*     */     
/* 298 */     releaseWeekForm.addElement(new FormHidden("cmd", "release-week-editor", true));
/* 299 */     releaseWeekForm.addElement(new FormHidden("OrderBy", "", true));
/*     */     
/* 301 */     context.putSessionValue("releaseWeek", releaseWeek);
/*     */ 
/*     */     
/* 304 */     if (context.getSessionValue("NOTEPAD_RELEASEWEEK_VISIBLE") != null) {
/* 305 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_RELEASEWEEK_VISIBLE"));
/*     */     }
/* 307 */     return releaseWeekForm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean save(Context context) {
/* 315 */     User user = MilestoneSecurity.getUser(context);
/*     */     
/* 317 */     Notepad notepad = getReleaseWeekNotepad(context, user.getUserId());
/* 318 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*     */     
/* 320 */     ReleaseWeek releaseWeek = MilestoneHelper.getScreenReleaseWeek(context);
/*     */     
/* 322 */     Form form = buildForm(context, releaseWeek);
/*     */ 
/*     */     
/* 325 */     if (ReleaseWeekManager.getInstance().isTimestampValid(releaseWeek)) {
/*     */       
/* 327 */       form.setValues(context);
/*     */ 
/*     */       
/* 330 */       String nameString = form.getStringValue("theName");
/*     */ 
/*     */       
/* 333 */       String cycleString = form.getStringValue("Cycle");
/*     */ 
/*     */       
/* 336 */       String startDateString = form.getStringValue("StartDate");
/*     */ 
/*     */       
/* 339 */       String endDateString = form.getStringValue("EndDate");
/*     */ 
/*     */       
/* 342 */       String solDateString = form.getStringValue("SolDate");
/*     */       
/* 344 */       releaseWeek.setName(nameString);
/* 345 */       releaseWeek.setCycle(cycleString);
/* 346 */       releaseWeek.setStartDate(MilestoneHelper.getDate(startDateString));
/* 347 */       releaseWeek.setEndDate(MilestoneHelper.getDate(endDateString));
/* 348 */       releaseWeek.setSolDate(MilestoneHelper.getDate(solDateString));
/*     */ 
/*     */       
/* 351 */       if (!ReleaseWeekManager.getInstance().isDuplicate(releaseWeek)) {
/*     */         
/* 353 */         if (!form.isUnchanged()) {
/*     */           
/* 355 */           FormValidation formValidation = form.validate();
/* 356 */           if (formValidation.isGood()) {
/*     */ 
/*     */             
/* 359 */             ReleaseWeek saveReleaseWeek = ReleaseWeekManager.getInstance().saveReleaseWeek(releaseWeek, user.getUserId());
/*     */ 
/*     */             
/* 362 */             notepad.setAllContents(null);
/* 363 */             notepad = getReleaseWeekNotepad(context, user.getUserId());
/* 364 */             notepad.setSelected(saveReleaseWeek);
/* 365 */             releaseWeek = (ReleaseWeek)notepad.validateSelected();
/* 366 */             context.putSessionValue("ReleaseWeek", releaseWeek);
/*     */             
/* 368 */             if (releaseWeek == null) {
/* 369 */               goToBlank(context);
/*     */             } else {
/* 371 */               form = buildForm(context, releaseWeek);
/*     */             } 
/*     */           } else {
/*     */             
/* 375 */             context.putDelivery("FormValidation", formValidation);
/*     */           }
/*     */         
/*     */         } 
/*     */       } else {
/*     */         
/* 381 */         context.putDelivery("AlertMessage", 
/* 382 */             MilestoneMessage.getMessage(5, 
/*     */               
/* 384 */               new String[] { "Release Week", releaseWeek.getName() }));
/* 385 */         context.putDelivery("Form", form);
/* 386 */         return edit(context);
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 392 */       context.putDelivery("AlertMessage", "The record is unavailable for editing at this time.  Please refresh your screen and edit it at a later time.");
/* 393 */       context.putDelivery("Form", form);
/* 394 */       return edit(context);
/*     */     } 
/* 396 */     context.putDelivery("Form", form);
/* 397 */     return context.includeJSP("release-week-editor.jsp");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean delete(Context context) {
/* 405 */     User user = MilestoneSecurity.getUser(context);
/*     */     
/* 407 */     Notepad notepad = getReleaseWeekNotepad(context, user.getUserId());
/* 408 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*     */     
/* 410 */     ReleaseWeek releaseWeek = MilestoneHelper.getScreenReleaseWeek(context);
/*     */ 
/*     */     
/* 413 */     if (releaseWeek != null) {
/*     */       
/* 415 */       ReleaseWeekManager.getInstance().deleteReleaseWeek(releaseWeek, user.getUserId());
/*     */ 
/*     */       
/* 418 */       notepad.setAllContents(null);
/* 419 */       notepad = getReleaseWeekNotepad(context, user.getUserId());
/* 420 */       notepad.setSelected(null);
/*     */     } 
/*     */     
/* 423 */     return edit(context);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean newForm(Context context) {
/* 431 */     User user = MilestoneSecurity.getUser(context);
/*     */     
/* 433 */     Notepad notepad = getReleaseWeekNotepad(context, user.getUserId());
/* 434 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*     */     
/* 436 */     Form form = buildNewForm(context);
/* 437 */     context.putDelivery("Form", form);
/* 438 */     return context.includeJSP("release-week-editor.jsp");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Form buildNewForm(Context context) {
/* 447 */     Form releaseWeekForm = new Form(this.application, "releaseWeekForm", 
/* 448 */         this.application.getInfrastructure().getServletURL(), "POST");
/* 449 */     User user = MilestoneSecurity.getUser(context);
/*     */ 
/*     */     
/* 452 */     FormTextField theName = new FormTextField("theName", "", true, 50, 50);
/* 453 */     theName.setTabIndex(1);
/* 454 */     releaseWeekForm.addElement(theName);
/*     */ 
/*     */     
/* 457 */     FormTextField Cycle = new FormTextField("Cycle", "", true, 8, 8);
/* 458 */     Cycle.setTabIndex(2);
/* 459 */     releaseWeekForm.addElement(Cycle);
/*     */ 
/*     */     
/* 462 */     FormTextField StartDate = new FormTextField("StartDate", MilestoneHelper.getFormatedDate(Calendar.getInstance()), true, 10);
/* 463 */     StartDate.addFormEvent("onBlur", "checkField( this );");
/* 464 */     StartDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/* 465 */     StartDate.setTabIndex(3);
/* 466 */     releaseWeekForm.addElement(StartDate);
/*     */ 
/*     */     
/* 469 */     FormTextField EndDate = new FormTextField("EndDate", MilestoneHelper.getFormatedDate(Calendar.getInstance()), true, 10);
/* 470 */     EndDate.addFormEvent("onBlur", "checkField( this );");
/* 471 */     EndDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/* 472 */     EndDate.setTabIndex(4);
/* 473 */     releaseWeekForm.addElement(EndDate);
/*     */ 
/*     */     
/* 476 */     FormDateField SolDate = new FormDateField("SolDate", "", false, 10);
/* 477 */     SolDate.addFormEvent("onBlur", "checkField( this );");
/* 478 */     SolDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/* 479 */     SolDate.setTabIndex(5);
/* 480 */     releaseWeekForm.addElement(SolDate);
/*     */ 
/*     */     
/* 483 */     FormTextField NameSearch = new FormTextField("NameSearch", "", false, 20);
/* 484 */     NameSearch.setId("NameSearch");
/* 485 */     releaseWeekForm.addElement(NameSearch);
/*     */     
/* 487 */     FormTextField CycleSearch = new FormTextField("CycleSearch", "", false, 8);
/* 488 */     CycleSearch.setId("CycleSearch");
/* 489 */     releaseWeekForm.addElement(CycleSearch);
/*     */     
/* 491 */     FormTextField StartDateSearch = new FormTextField("StartDateSearch", "", false, 10);
/* 492 */     StartDateSearch.setId("StartDateSearch");
/* 493 */     releaseWeekForm.addElement(StartDateSearch);
/*     */     
/* 495 */     FormTextField EndDateSearch = new FormTextField("EndDateSearch", "", false, 10);
/* 496 */     EndDateSearch.setId("EndDateSearch");
/* 497 */     releaseWeekForm.addElement(EndDateSearch);
/*     */     
/* 499 */     releaseWeekForm.addElement(new FormHidden("cmd", "release-week-edit-new", true));
/* 500 */     releaseWeekForm.addElement(new FormHidden("OrderBy", "", true));
/*     */ 
/*     */     
/* 503 */     if (context.getSessionValue("NOTEPAD_RELEASEWEEK_VISIBLE") != null) {
/* 504 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_RELEASEWEEK_VISIBLE"));
/*     */     }
/* 506 */     return releaseWeekForm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean saveNew(Context context) {
/* 514 */     User user = MilestoneSecurity.getUser(context);
/*     */     
/* 516 */     Notepad notepad = getReleaseWeekNotepad(context, user.getUserId());
/* 517 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*     */     
/* 519 */     ReleaseWeek releaseWeek = new ReleaseWeek();
/*     */     
/* 521 */     Form form = buildNewForm(context);
/*     */     
/* 523 */     form.setValues(context);
/*     */ 
/*     */     
/* 526 */     String nameString = form.getStringValue("theName");
/*     */ 
/*     */     
/* 529 */     String cycleString = form.getStringValue("Cycle");
/*     */ 
/*     */     
/* 532 */     String startString = form.getStringValue("StartDate");
/*     */ 
/*     */     
/* 535 */     String endString = form.getStringValue("EndDate");
/*     */ 
/*     */     
/* 538 */     String solString = form.getStringValue("SolDate");
/*     */     
/* 540 */     releaseWeek.setName(nameString);
/* 541 */     releaseWeek.setCycle(cycleString);
/* 542 */     releaseWeek.setStartDate(MilestoneHelper.getDate(startString));
/* 543 */     releaseWeek.setEndDate(MilestoneHelper.getDate(endString));
/* 544 */     releaseWeek.setSolDate(MilestoneHelper.getDate(solString));
/*     */     
/* 546 */     if (!ReleaseWeekManager.getInstance().isDuplicate(releaseWeek)) {
/*     */       
/* 548 */       if (!form.isUnchanged()) {
/*     */         
/* 550 */         FormValidation formValidation = form.validate();
/* 551 */         if (formValidation.isGood())
/*     */         {
/* 553 */           ReleaseWeek saveNewReleaseWeek = ReleaseWeekManager.getInstance().saveNewReleaseWeek(releaseWeek, user.getUserId());
/*     */           
/* 555 */           context.putSessionValue("ReleaseWeek", saveNewReleaseWeek);
/*     */           
/* 557 */           if (notepad.getAllContents() == null || notepad.getAllContents().size() < 1) {
/* 558 */             notepad.setSearchQuery("");
/*     */           }
/*     */           
/* 561 */           notepad.setAllContents(null);
/* 562 */           notepad.newSelectedReset();
/* 563 */           notepad = getReleaseWeekNotepad(context, user.getUserId());
/* 564 */           notepad.setSelected(releaseWeek);
/*     */         }
/*     */         else
/*     */         {
/* 568 */           context.putDelivery("FormValidation", formValidation);
/* 569 */           form.addElement(new FormHidden("OrderBy", "", true));
/* 570 */           context.putDelivery("Form", form);
/* 571 */           return context.includeJSP("release-week-editor.jsp");
/*     */         }
/*     */       
/*     */       } 
/*     */     } else {
/*     */       
/* 577 */       context.putDelivery("AlertMessage", 
/* 578 */           MilestoneMessage.getMessage(5, 
/*     */             
/* 580 */             new String[] { "Release Week", releaseWeek.getName() }));
/*     */     } 
/* 582 */     return edit(context);
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
/*     */   private Context goToBlank(Context context) {
/* 597 */     context.putDelivery("isNotePadVisible", new Boolean(MilestoneHelper.getNotePadVisiblitiy(15, context)));
/*     */     
/* 599 */     Form form = new Form(this.application, "form", this.application.getInfrastructure().getServletURL(), "Post");
/* 600 */     form.addElement(new FormHidden("cmd", "release-week-editor"));
/* 601 */     form.addElement(new FormHidden("OrderBy", "", true));
/*     */ 
/*     */     
/* 604 */     FormTextField NameSearch = new FormTextField("NameSearch", "", true, 20);
/* 605 */     NameSearch.setId("NameSearch");
/* 606 */     form.addElement(NameSearch);
/*     */     
/* 608 */     FormTextField CycleSearch = new FormTextField("CycleSearch", "", true, 8);
/* 609 */     CycleSearch.setId("CycleSearch");
/* 610 */     form.addElement(CycleSearch);
/*     */     
/* 612 */     FormTextField StartDateSearch = new FormTextField("StartDateSearch", "", true, 10);
/* 613 */     StartDateSearch.setId("StartDateSearch");
/* 614 */     form.addElement(StartDateSearch);
/*     */     
/* 616 */     FormTextField EndDateSearch = new FormTextField("EndDateSearch", "", true, 10);
/* 617 */     EndDateSearch.setId("EndDateSearch");
/* 618 */     form.addElement(EndDateSearch);
/*     */     
/* 620 */     context.putDelivery("Form", form);
/*     */     
/* 622 */     return context;
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\ReleaseWeekHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */