/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.gemini.Context;
/*     */ import com.techempower.gemini.Dispatcher;
/*     */ import com.techempower.gemini.FormCheckBox;
/*     */ import com.techempower.gemini.FormHidden;
/*     */ import com.techempower.gemini.FormRadioButtonGroup;
/*     */ import com.techempower.gemini.FormTextField;
/*     */ import com.techempower.gemini.FormValidation;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.Cache;
/*     */ import com.universal.milestone.Form;
/*     */ import com.universal.milestone.MilestoneConstants;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.MilestoneMessage;
/*     */ import com.universal.milestone.MilestoneSecurity;
/*     */ import com.universal.milestone.Notepad;
/*     */ import com.universal.milestone.PriceCode;
/*     */ import com.universal.milestone.PriceCodeHandler;
/*     */ import com.universal.milestone.PriceCodeManager;
/*     */ import com.universal.milestone.SecureHandler;
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
/*     */ public class PriceCodeHandler
/*     */   extends SecureHandler
/*     */ {
/*     */   public static final String COMPONENT_CODE = "hPrc";
/*     */   public GeminiApplication application;
/*     */   public ComponentLog log;
/*     */   
/*     */   public PriceCodeHandler(GeminiApplication application) {
/*  59 */     this.application = application;
/*  60 */     this.log = application.getLog("hPrc");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   public String getDescription() { return "Price Code Handler"; }
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
/*  80 */       if (command.startsWith("price-code"))
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
/*  97 */     if (command.equalsIgnoreCase("price-code-search"))
/*     */     {
/*  99 */       search(dispatcher, context, command);
/*     */     }
/* 101 */     if (command.equalsIgnoreCase("price-code-sort")) {
/*     */       
/* 103 */       sort(dispatcher, context);
/*     */     }
/* 105 */     else if (command.equalsIgnoreCase("price-code-editor")) {
/*     */       
/* 107 */       edit(context);
/*     */     }
/* 109 */     else if (command.equalsIgnoreCase("price-code-edit-save")) {
/*     */       
/* 111 */       save(context);
/*     */     }
/* 113 */     else if (command.equalsIgnoreCase("price-code-edit-save-new")) {
/*     */       
/* 115 */       saveNew(context);
/*     */     }
/* 117 */     else if (command.equalsIgnoreCase("price-code-edit-delete")) {
/*     */       
/* 119 */       delete(context);
/*     */     }
/* 121 */     else if (command.equalsIgnoreCase("price-code-edit-new")) {
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
/*     */   
/*     */   private boolean search(Dispatcher dispatcher, Context context, String command) {
/* 136 */     Notepad notepad = MilestoneHelper.getNotepadFromSession(16, context);
/*     */ 
/*     */     
/* 139 */     notepad.setAllContents(null);
/* 140 */     notepad.setSelected(null);
/*     */     
/* 142 */     PriceCodeManager.getInstance().setNotepadQuery(context, MilestoneSecurity.getUser(context).getUserId(), notepad);
/* 143 */     dispatcher.redispatch(context, "price-code-editor");
/*     */     
/* 145 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean sort(Dispatcher dispatcher, Context context) {
/* 156 */     int sort = Integer.parseInt(context.getParameter("OrderBy"));
/* 157 */     Notepad notepad = MilestoneHelper.getNotepadFromSession(16, context);
/*     */     
/* 159 */     if (notepad.getSearchQuery() == null || notepad.getSearchQuery().equals("")) {
/* 160 */       PriceCodeManager.getInstance(); notepad.setSearchQuery("SELECT sell_code, retail_code, description, isDigital FROM vi_Price_Code");
/*     */     } 
/* 162 */     notepad.setOrderBy(" ORDER BY " + MilestoneConstants.SORT_PRICE_CODE[sort]);
/*     */ 
/*     */     
/* 165 */     notepad.setAllContents(null);
/* 166 */     notepad = getPriceCodeNotepad(context, MilestoneSecurity.getUser(context).getUserId());
/* 167 */     notepad.goToSelectedPage();
/*     */     
/* 169 */     dispatcher.redispatch(context, "price-code-editor");
/*     */     
/* 171 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean edit(Context context) {
/* 179 */     User user = MilestoneSecurity.getUser(context);
/*     */     
/* 181 */     Vector contents = new Vector();
/*     */     
/* 183 */     Notepad notepad = getPriceCodeNotepad(context, user.getUserId());
/* 184 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*     */     
/* 186 */     PriceCode priceCode = MilestoneHelper.getScreenPriceCode(context);
/*     */     
/* 188 */     if (priceCode != null) {
/*     */       
/* 190 */       Form form = null;
/* 191 */       if (priceCode != null) {
/* 192 */         form = buildForm(context, priceCode);
/*     */       } else {
/* 194 */         form = buildNewForm(context);
/*     */       } 
/* 196 */       context.putDelivery("Form", form);
/* 197 */       return context.includeJSP("price-code-editor.jsp");
/*     */     } 
/*     */ 
/*     */     
/* 201 */     return goToBlank(context);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Form buildForm(Context context, PriceCode priceCode) {
/* 212 */     Form priceCodeForm = new Form(this.application, "priceCodeForm", 
/* 213 */         this.application.getInfrastructure().getServletURL(), "POST");
/* 214 */     User user = MilestoneSecurity.getUser(context);
/*     */ 
/*     */     
/* 217 */     String sellCode = "";
/* 218 */     sellCode = priceCode.getSellCode();
/* 219 */     FormTextField SellCode = new FormTextField("SellCode", sellCode, true, 10, 10);
/* 220 */     SellCode.setTabIndex(1);
/* 221 */     priceCodeForm.addElement(SellCode);
/*     */ 
/*     */     
/* 224 */     String retailCode = "";
/* 225 */     retailCode = priceCode.getRetailCode();
/* 226 */     FormTextField RetailCode = new FormTextField("RetailCode", retailCode, false, 8, 5);
/* 227 */     RetailCode.setTabIndex(2);
/* 228 */     priceCodeForm.addElement(RetailCode);
/*     */ 
/*     */     
/* 231 */     int units = 0;
/* 232 */     units = priceCode.getUnits();
/* 233 */     FormTextField Units = new FormTextField("Units", String.valueOf(units), false, 10, 10);
/* 234 */     Units.addFormEvent("onBlur", "JavaScript:checkField( this )");
/* 235 */     Units.setTabIndex(3);
/* 236 */     priceCodeForm.addElement(Units);
/*     */ 
/*     */     
/* 239 */     String pricepoint = "";
/* 240 */     pricepoint = priceCode.getPricePoint();
/* 241 */     FormTextField PricePoint = new FormTextField("PricePoint", pricepoint, false, 5, 5);
/* 242 */     PricePoint.addFormEvent("onBlur", "JavaScript:checkField( this )");
/* 243 */     PricePoint.setTabIndex(4);
/* 244 */     priceCodeForm.addElement(PricePoint);
/*     */ 
/*     */     
/* 247 */     String description = "";
/* 248 */     description = priceCode.getDescription();
/* 249 */     FormTextField Description = new FormTextField("Description", description, false, 50, 50);
/* 250 */     Description.addFormEvent("onBlur", "JavaScript:checkField( this )");
/* 251 */     Description.setTabIndex(5);
/* 252 */     priceCodeForm.addElement(Description);
/*     */ 
/*     */     
/* 255 */     float unitcost = 0.0F;
/* 256 */     unitcost = priceCode.getUnitCost();
/* 257 */     FormTextField UnitCost = new FormTextField("UnitCost", String.valueOf(unitcost), false, 10, 10);
/* 258 */     UnitCost.addFormEvent("onBlur", "JavaScript:checkField( this )");
/* 259 */     UnitCost.setTabIndex(6);
/* 260 */     priceCodeForm.addElement(UnitCost);
/*     */ 
/*     */     
/* 263 */     float totalcost = 0.0F;
/* 264 */     totalcost = priceCode.getTotalCost();
/* 265 */     FormTextField TotalCost = new FormTextField("TotalCost", String.valueOf(totalcost), false, 10, 10);
/* 266 */     TotalCost.addFormEvent("onBlur", "JavaScript:checkField( this )");
/* 267 */     TotalCost.setTabIndex(7);
/* 268 */     priceCodeForm.addElement(TotalCost);
/*     */     
/* 270 */     FormCheckBox IsDigital = new FormCheckBox("IsDigital", "", false, priceCode.getIsDigital());
/* 271 */     IsDigital.setTabIndex(8);
/* 272 */     priceCodeForm.addElement(IsDigital);
/*     */ 
/*     */     
/* 275 */     FormTextField SellCodeSearch = new FormTextField("SellCodeSearch", "", false, 5);
/* 276 */     SellCodeSearch.setId("SellCodeSearch");
/* 277 */     priceCodeForm.addElement(SellCodeSearch);
/*     */     
/* 279 */     FormTextField RetailCodeSearch = new FormTextField("RetailCodeSearch", "", false, 5);
/* 280 */     RetailCodeSearch.setId("RetailCodeSearch");
/* 281 */     priceCodeForm.addElement(RetailCodeSearch);
/*     */     
/* 283 */     FormTextField DescriptionSearch = new FormTextField("DescriptionSearch", "", false, 5);
/* 284 */     DescriptionSearch.setId("DescriptionSearch");
/* 285 */     priceCodeForm.addElement(DescriptionSearch);
/*     */     
/* 287 */     String[] dvalues = new String[3];
/* 288 */     dvalues[0] = "0";
/* 289 */     dvalues[1] = "1";
/* 290 */     dvalues[2] = "2";
/*     */     
/* 292 */     String[] dlabels = new String[3];
/* 293 */     dlabels[0] = "Physical";
/* 294 */     dlabels[1] = "Digital";
/* 295 */     dlabels[2] = "Both";
/*     */     
/* 297 */     FormRadioButtonGroup IsDigitalSearch = new FormRadioButtonGroup("IsDigitalSearch", "Both", dvalues, dlabels, false);
/* 298 */     IsDigitalSearch.setId("IsDigitalSearch");
/* 299 */     priceCodeForm.addElement(IsDigitalSearch);
/*     */     
/* 301 */     priceCodeForm.addElement(new FormHidden("cmd", "price-code-editor", true));
/* 302 */     priceCodeForm.addElement(new FormHidden("OrderBy", "", true));
/*     */     
/* 304 */     context.putSessionValue("priceCode", priceCode);
/*     */ 
/*     */     
/* 307 */     if (context.getSessionValue("NOTEPAD_PRICECODE_VISIBLE") != null) {
/* 308 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_PRICECODE_VISIBLE"));
/*     */     }
/* 310 */     return priceCodeForm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean save(Context context) {
/* 317 */     User user = MilestoneSecurity.getUser(context);
/*     */     
/* 319 */     Vector contents = new Vector();
/*     */     
/* 321 */     Notepad notepad = getPriceCodeNotepad(context, user.getUserId());
/* 322 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/* 323 */     PriceCode priceCode = MilestoneHelper.getScreenPriceCode(context);
/*     */     
/* 325 */     Form form = buildForm(context, priceCode);
/*     */ 
/*     */     
/* 328 */     if (PriceCodeManager.getInstance().isTimestampValid(priceCode)) {
/*     */       
/* 330 */       form.setValues(context);
/*     */ 
/*     */       
/* 333 */       String retailString = form.getStringValue("RetailCode");
/*     */ 
/*     */       
/* 336 */       int unitsInt = 0;
/*     */       
/*     */       try {
/* 339 */         unitsInt = Integer.parseInt(form.getStringValue("Units"));
/*     */       }
/* 341 */       catch (Exception e) {
/*     */         
/* 343 */         System.out.println("Exception occurres while converting Units.");
/*     */       } 
/*     */ 
/*     */       
/* 347 */       String pricepointString = form.getStringValue("PricePoint");
/*     */ 
/*     */       
/* 350 */       String descriptionString = form.getStringValue("Description");
/*     */ 
/*     */       
/* 353 */       float unitcostInt = 0.0F;
/*     */       
/*     */       try {
/* 356 */         unitcostInt = Float.parseFloat(form.getStringValue("UnitCost"));
/*     */       }
/* 358 */       catch (Exception e) {
/*     */         
/* 360 */         System.out.println("Exception occurres while converting Unit Cost.");
/*     */       } 
/*     */ 
/*     */       
/* 364 */       float totalcostInt = 0.0F;
/*     */       
/*     */       try {
/* 367 */         totalcostInt = Float.parseFloat(form.getStringValue("TotalCost"));
/*     */       }
/* 369 */       catch (Exception e) {
/*     */         
/* 371 */         System.out.println("Exception occurres while converting Total Cost.");
/*     */       } 
/*     */       
/* 374 */       priceCode.setRetailCode(retailString);
/* 375 */       priceCode.setUnits(unitsInt);
/* 376 */       priceCode.setPricePoint(pricepointString);
/* 377 */       priceCode.setDescription(descriptionString);
/* 378 */       priceCode.setUnitCost(unitcostInt);
/* 379 */       priceCode.setTotalCost(totalcostInt);
/* 380 */       priceCode.setIsDigital(((FormCheckBox)form.getElement("IsDigital")).isChecked());
/*     */       
/* 382 */       if (!form.isUnchanged()) {
/*     */         
/* 384 */         FormValidation formValidation = form.validate();
/* 385 */         if (formValidation.isGood()) {
/*     */ 
/*     */           
/* 388 */           PriceCode savePriceCode = PriceCodeManager.getInstance().savePriceCode(priceCode, user.getUserId());
/*     */ 
/*     */           
/* 391 */           notepad.setAllContents(null);
/* 392 */           Cache.flushSellCode();
/* 393 */           notepad = getPriceCodeNotepad(context, user.getUserId());
/* 394 */           notepad.goToSelectedPage();
/* 395 */           notepad.setSelected(savePriceCode);
/* 396 */           priceCode = (PriceCode)notepad.validateSelected();
/*     */           
/* 398 */           if (context.getSessionValue("NOTEPAD_PRICECODE_VISIBLE") != null) {
/* 399 */             context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_PRICECODE_VISIBLE"));
/*     */           }
/* 401 */           context.putSessionValue("PriceCode", priceCode);
/*     */           
/* 403 */           if (priceCode == null) {
/* 404 */             return goToBlank(context);
/*     */           }
/* 406 */           form = buildForm(context, priceCode);
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 411 */           context.putDelivery("FormValidation", formValidation);
/*     */         } 
/*     */       } 
/* 414 */       form.addElement(new FormHidden("OrderBy", "", true));
/* 415 */       context.putDelivery("Form", form);
/* 416 */       return edit(context);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 421 */     context.putDelivery("AlertMessage", "The record is unavailable for editing at this time.  Please refresh your screen and edit it at a later time.");
/*     */ 
/*     */     
/* 424 */     context.putDelivery("Form", form);
/* 425 */     return context.includeJSP("price-code-editor.jsp");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean delete(Context context) {
/* 436 */     User user = MilestoneSecurity.getUser(context);
/*     */     
/* 438 */     Vector contents = new Vector();
/*     */     
/* 440 */     Notepad notepad = getPriceCodeNotepad(context, user.getUserId());
/* 441 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*     */     
/* 443 */     PriceCode priceCode = MilestoneHelper.getScreenPriceCode(context);
/*     */ 
/*     */     
/* 446 */     if (priceCode != null) {
/*     */       
/* 448 */       PriceCodeManager.getInstance().deletePriceCode(priceCode, user.getUserId());
/*     */ 
/*     */       
/* 451 */       notepad.setAllContents(null);
/* 452 */       Cache.flushSellCode();
/* 453 */       notepad = getPriceCodeNotepad(context, user.getUserId());
/* 454 */       notepad.setSelected(null);
/*     */     } 
/* 456 */     return edit(context);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean newForm(Context context) {
/* 463 */     User user = MilestoneSecurity.getUser(context);
/*     */     
/* 465 */     Notepad notepad = getPriceCodeNotepad(context, user.getUserId());
/* 466 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*     */     
/* 468 */     Form form = buildNewForm(context);
/* 469 */     context.putDelivery("Form", form);
/* 470 */     return context.includeJSP("price-code-editor.jsp");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean saveNew(Context context) {
/* 477 */     User user = MilestoneSecurity.getUser(context);
/*     */     
/* 479 */     Notepad notepad = getPriceCodeNotepad(context, user.getUserId());
/* 480 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*     */     
/* 482 */     PriceCode priceCode = new PriceCode();
/*     */     
/* 484 */     Form form = buildNewForm(context);
/*     */     
/* 486 */     form.setValues(context);
/*     */ 
/*     */     
/* 489 */     String sellString = form.getStringValue("SellCode");
/*     */ 
/*     */     
/* 492 */     String retailString = form.getStringValue("RetailCode");
/*     */ 
/*     */     
/* 495 */     int unitsInt = 0;
/*     */     
/*     */     try {
/* 498 */       unitsInt = Integer.parseInt(form.getStringValue("Units"));
/*     */     }
/* 500 */     catch (Exception e) {
/*     */       
/* 502 */       System.out.println("Exception occurres while converting Units.");
/*     */     } 
/*     */ 
/*     */     
/* 506 */     String pricepointString = form.getStringValue("PricePoint");
/*     */ 
/*     */     
/* 509 */     String descriptionString = form.getStringValue("Description");
/*     */ 
/*     */     
/* 512 */     float unitcostInt = 0.0F;
/*     */     
/*     */     try {
/* 515 */       unitcostInt = Float.parseFloat(form.getStringValue("UnitCost"));
/*     */     }
/* 517 */     catch (Exception e) {
/*     */       
/* 519 */       System.out.println("Exception occurres while converting Unit Cost.");
/*     */     } 
/*     */ 
/*     */     
/* 523 */     float totalcostInt = 0.0F;
/*     */     
/*     */     try {
/* 526 */       totalcostInt = Float.parseFloat(form.getStringValue("TotalCost"));
/*     */     }
/* 528 */     catch (Exception e) {
/*     */       
/* 530 */       System.out.println("Exception occurres while converting Total Cost.");
/*     */     } 
/*     */ 
/*     */     
/* 534 */     priceCode.setSellCode(sellString);
/* 535 */     priceCode.setRetailCode(retailString);
/* 536 */     priceCode.setUnits(unitsInt);
/* 537 */     priceCode.setPricePoint(pricepointString);
/* 538 */     priceCode.setDescription(descriptionString);
/* 539 */     priceCode.setUnitCost(unitcostInt);
/* 540 */     priceCode.setTotalCost(totalcostInt);
/* 541 */     priceCode.setIsDigital(((FormCheckBox)form.getElement("IsDigital")).isChecked());
/*     */     
/* 543 */     if (!PriceCodeManager.getInstance().isDuplicate(priceCode)) {
/*     */       
/* 545 */       if (!form.isUnchanged()) {
/*     */         
/* 547 */         FormValidation formValidation = form.validate();
/* 548 */         if (formValidation.isGood())
/*     */         {
/*     */           
/* 551 */           PriceCode saveNewPriceCode = PriceCodeManager.getInstance().saveNewPriceCode(priceCode, user.getUserId());
/*     */           
/* 553 */           context.putSessionValue("PriceCode", saveNewPriceCode);
/*     */           
/* 555 */           if (notepad.getAllContents() == null || notepad.getAllContents().size() < 1) {
/* 556 */             notepad.setSearchQuery("");
/*     */           }
/*     */           
/* 559 */           notepad.setAllContents(null);
/* 560 */           notepad.newSelectedReset();
/* 561 */           Cache.flushSellCode();
/* 562 */           notepad = getPriceCodeNotepad(context, user.getUserId());
/* 563 */           notepad.setSelected(saveNewPriceCode);
/*     */         }
/*     */         else
/*     */         {
/* 567 */           context.putDelivery("FormValidation", formValidation);
/* 568 */           form.addElement(new FormHidden("OrderBy", "", true));
/* 569 */           context.putDelivery("Form", form);
/* 570 */           return context.includeJSP("price-code-editor.jsp");
/*     */         }
/*     */       
/*     */       } 
/*     */     } else {
/*     */       
/* 576 */       context.putDelivery("AlertMessage", 
/* 577 */           MilestoneMessage.getMessage(5, 
/*     */             
/* 579 */             new String[] { "Price Code", priceCode.getSellCode() }));
/*     */     } 
/* 581 */     return edit(context);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Form buildNewForm(Context context) {
/* 591 */     Form priceCodeForm = new Form(this.application, "priceCodeForm", 
/* 592 */         this.application.getInfrastructure().getServletURL(), "POST");
/* 593 */     User user = MilestoneSecurity.getUser(context);
/*     */ 
/*     */     
/* 596 */     FormTextField SellCode = new FormTextField("SellCode", "", true, 10, 10);
/* 597 */     SellCode.setTabIndex(1);
/* 598 */     priceCodeForm.addElement(SellCode);
/*     */ 
/*     */     
/* 601 */     FormTextField RetailCode = new FormTextField("RetailCode", "", false, 8, 5);
/* 602 */     RetailCode.setTabIndex(2);
/* 603 */     priceCodeForm.addElement(RetailCode);
/*     */ 
/*     */     
/* 606 */     FormTextField Units = new FormTextField("Units", "", false, 10, 10);
/* 607 */     Units.addFormEvent("onBlur", "JavaScript:checkField( this )");
/* 608 */     Units.setTabIndex(3);
/* 609 */     priceCodeForm.addElement(Units);
/*     */ 
/*     */     
/* 612 */     FormTextField PricePoint = new FormTextField("PricePoint", "", false, 5, 5);
/* 613 */     PricePoint.addFormEvent("onBlur", "JavaScript:checkField( this )");
/* 614 */     PricePoint.setTabIndex(4);
/* 615 */     priceCodeForm.addElement(PricePoint);
/*     */ 
/*     */     
/* 618 */     FormTextField Description = new FormTextField("Description", "", false, 50, 50);
/* 619 */     Description.addFormEvent("onBlur", "JavaScript:checkField( this )");
/* 620 */     Description.setTabIndex(5);
/* 621 */     priceCodeForm.addElement(Description);
/*     */ 
/*     */     
/* 624 */     FormTextField UnitCost = new FormTextField("UnitCost", "0.00", false, 10, 10);
/* 625 */     UnitCost.addFormEvent("onBlur", "JavaScript:checkField( this )");
/* 626 */     UnitCost.setTabIndex(6);
/* 627 */     priceCodeForm.addElement(UnitCost);
/*     */ 
/*     */     
/* 630 */     FormTextField TotalCost = new FormTextField("TotalCost", "0.00", false, 10, 10);
/* 631 */     TotalCost.addFormEvent("onBlur", "JavaScript:checkField( this )");
/* 632 */     TotalCost.setTabIndex(7);
/* 633 */     priceCodeForm.addElement(TotalCost);
/*     */     
/* 635 */     FormCheckBox IsDigital = new FormCheckBox("IsDigital", "", false, false);
/* 636 */     IsDigital.setTabIndex(8);
/* 637 */     priceCodeForm.addElement(IsDigital);
/*     */ 
/*     */ 
/*     */     
/* 641 */     FormTextField SellCodeSearch = new FormTextField("SellCodeSearch", "", false, 5);
/* 642 */     SellCodeSearch.setId("SellCodeSearch");
/* 643 */     priceCodeForm.addElement(SellCodeSearch);
/*     */     
/* 645 */     FormTextField RetailCodeSearch = new FormTextField("RetailCodeSearch", "", false, 5);
/* 646 */     RetailCodeSearch.setId("RetailCodeSearch");
/* 647 */     priceCodeForm.addElement(RetailCodeSearch);
/*     */     
/* 649 */     FormTextField DescriptionSearch = new FormTextField("DescriptionSearch", "", false, 5);
/* 650 */     DescriptionSearch.setId("DescriptionSearch");
/* 651 */     priceCodeForm.addElement(DescriptionSearch);
/*     */     
/* 653 */     String[] dvalues = new String[3];
/* 654 */     dvalues[0] = "0";
/* 655 */     dvalues[1] = "1";
/* 656 */     dvalues[2] = "2";
/*     */     
/* 658 */     String[] dlabels = new String[3];
/* 659 */     dlabels[0] = "Physical";
/* 660 */     dlabels[1] = "Digital";
/* 661 */     dlabels[2] = "Both";
/*     */     
/* 663 */     FormRadioButtonGroup IsDigitalSearch = new FormRadioButtonGroup("IsDigitalSearch", "Both", dvalues, dlabels, false);
/* 664 */     IsDigitalSearch.setId("IsDigitalSearch");
/* 665 */     priceCodeForm.addElement(IsDigitalSearch);
/*     */ 
/*     */     
/* 668 */     priceCodeForm.addElement(new FormHidden("cmd", "price-code-edit-new", true));
/* 669 */     priceCodeForm.addElement(new FormHidden("OrderBy", "", true));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 674 */     if (context.getSessionValue("NOTEPAD_PRICECODE_VISIBLE") != null) {
/* 675 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_PRICECODE_VISIBLE"));
/*     */     }
/* 677 */     return priceCodeForm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Notepad getPriceCodeNotepad(Context context, int userId) {
/* 686 */     Vector contents = new Vector();
/*     */     
/* 688 */     if (MilestoneHelper.getNotepadFromSession(16, context) != null) {
/*     */       
/* 690 */       Notepad notepad = MilestoneHelper.getNotepadFromSession(16, context);
/*     */       
/* 692 */       if (notepad.getAllContents() == null) {
/*     */         
/* 694 */         contents = PriceCodeManager.getInstance().getPriceCodeNotepadList(userId, notepad);
/* 695 */         notepad.setAllContents(contents);
/*     */       } 
/*     */       
/* 698 */       return notepad;
/*     */     } 
/*     */ 
/*     */     
/* 702 */     String[] columnNames = { "Sell Code", "Ret. Code", "Description" };
/* 703 */     contents = PriceCodeManager.getInstance().getPriceCodeNotepadList(userId, null);
/* 704 */     return new Notepad(contents, 0, 15, "Price Code", 16, columnNames);
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
/*     */   
/*     */   private boolean goToBlank(Context context) {
/* 722 */     context.putDelivery("isNotePadVisible", new Boolean(MilestoneHelper.getNotePadVisiblitiy(16, context)));
/*     */     
/* 724 */     Form form = new Form(this.application, "form", this.application.getInfrastructure().getServletURL(), "Post");
/* 725 */     form.addElement(new FormHidden("cmd", "price-code-editor"));
/* 726 */     form.addElement(new FormHidden("OrderBy", "", true));
/*     */ 
/*     */     
/* 729 */     FormTextField SellCodeSearch = new FormTextField("SellCodeSearch", "", true, 5);
/* 730 */     SellCodeSearch.setId("SellCodeSearch");
/* 731 */     form.addElement(SellCodeSearch);
/*     */     
/* 733 */     FormTextField RetailCodeSearch = new FormTextField("RetailCodeSearch", "", true, 5);
/* 734 */     RetailCodeSearch.setId("RetailCodeSearch");
/* 735 */     form.addElement(RetailCodeSearch);
/*     */     
/* 737 */     FormTextField DescriptionSearch = new FormTextField("DescriptionSearch", "", true, 5);
/* 738 */     DescriptionSearch.setId("DescriptionSearch");
/* 739 */     form.addElement(DescriptionSearch);
/*     */     
/* 741 */     String[] dvalues = new String[3];
/* 742 */     dvalues[0] = "0";
/* 743 */     dvalues[1] = "1";
/* 744 */     dvalues[2] = "2";
/*     */     
/* 746 */     String[] dlabels = new String[3];
/* 747 */     dlabels[0] = "Physical";
/* 748 */     dlabels[1] = "Digital";
/* 749 */     dlabels[2] = "Both";
/*     */     
/* 751 */     FormRadioButtonGroup IsDigitalSearch = new FormRadioButtonGroup("IsDigitalSearch", "Both", dvalues, dlabels, false);
/* 752 */     IsDigitalSearch.setId("IsDigitalSearch");
/* 753 */     form.addElement(IsDigitalSearch);
/*     */ 
/*     */     
/* 756 */     context.putDelivery("Form", form);
/*     */     
/* 758 */     return context.includeJSP("blank-price-code-editor.jsp");
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\PriceCodeHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */