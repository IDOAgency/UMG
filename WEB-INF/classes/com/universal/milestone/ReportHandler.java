/*      */ package WEB-INF.classes.com.universal.milestone;
/*      */ import com.techempower.ComponentLog;
/*      */ import com.techempower.EnhancedProperties;
/*      */ import com.techempower.gemini.Context;
/*      */ import com.techempower.gemini.Dispatcher;
/*      */ import com.techempower.gemini.FormCheckBox;
/*      */ import com.techempower.gemini.FormDropDownMenu;
/*      */ import com.techempower.gemini.FormHidden;
/*      */ import com.techempower.gemini.FormRadioButtonGroup;
/*      */ import com.techempower.gemini.FormTextField;
/*      */ import com.techempower.gemini.GeminiApplication;
/*      */ import com.universal.milestone.AddsAndMovesForPrintSubHandler;
/*      */ import com.universal.milestone.Cache;
/*      */ import com.universal.milestone.CapitolProductionSchedulePrintSubHandler;
/*      */ import com.universal.milestone.CarolineProductionSchedulePrintSubHandler;
/*      */ import com.universal.milestone.ClassicsProductionScheduleForPrintSubHandler;
/*      */ import com.universal.milestone.Company;
/*      */ import com.universal.milestone.CorporateStructureObject;
/*      */ import com.universal.milestone.DigitalProductionLabelScheduleForPrintSubHandler;
/*      */ import com.universal.milestone.DigitalProductionScheduleForPrintSubHandler;
/*      */ import com.universal.milestone.Division;
/*      */ import com.universal.milestone.DreamWorksPromoScheduleForPrintSubHandler;
/*      */ import com.universal.milestone.DreamWorksRecordScheduleForPrintSubHandler;
/*      */ import com.universal.milestone.DreamWorksRelScheduleForPrintSubHandler;
/*      */ import com.universal.milestone.EntCommRelScheduleForPrintSubHandler;
/*      */ import com.universal.milestone.EntPastRelAlbumsForPrintSubHandler;
/*      */ import com.universal.milestone.EntPastRelOrphansForPrintSubHandler;
/*      */ import com.universal.milestone.EntProdStatusForPrintSubHandler;
/*      */ import com.universal.milestone.EntRelScheduleForPrintSubHandler;
/*      */ import com.universal.milestone.EntRelWithoutScheduleForPrintSubHandler;
/*      */ import com.universal.milestone.EntReleaseSchedByLabelSubHandler;
/*      */ import com.universal.milestone.Environment;
/*      */ import com.universal.milestone.Family;
/*      */ import com.universal.milestone.Form;
/*      */ import com.universal.milestone.FutureTaskDueDatesByTemplateSubHandler;
/*      */ import com.universal.milestone.IdjProductionScheduleForPrintSubHandlerAlternate;
/*      */ import com.universal.milestone.IdjProductionScheduleForPrintSubHandlerNew2091;
/*      */ import com.universal.milestone.IgaProductionScheduleUpdateForPrintSubHandler;
/*      */ import com.universal.milestone.Label;
/*      */ import com.universal.milestone.LatinoProductionScheduleUpdateForPrintSubHandler;
/*      */ import com.universal.milestone.MCACustomImpactScheduleForPrintSubHandler;
/*      */ import com.universal.milestone.MCACustomReleaseForPrintSubHandler;
/*      */ import com.universal.milestone.MCANewReleasePlanningSchedulePrintSubHandler;
/*      */ import com.universal.milestone.MCAProductionScheduleForPrintSubHandler;
/*      */ import com.universal.milestone.MexicoScheduleForPrintSubHandler;
/*      */ import com.universal.milestone.MilestoneFormDropDownMenu;
/*      */ import com.universal.milestone.MilestoneHelper;
/*      */ import com.universal.milestone.MilestoneHelper_2;
/*      */ import com.universal.milestone.MilestoneSecurity;
/*      */ import com.universal.milestone.MilestoneVector;
/*      */ import com.universal.milestone.NashProdScheduleForPrintSubHandler;
/*      */ import com.universal.milestone.NashRelScheduleForPrintSubHandler;
/*      */ import com.universal.milestone.Notepad;
/*      */ import com.universal.milestone.PastDueReleasesForPrintSubHandler;
/*      */ import com.universal.milestone.PhysicalProductActivitySummaryForPrintSubHandler;
/*      */ import com.universal.milestone.PressPlayProductionScheduleForPrintSubHandler;
/*      */ import com.universal.milestone.ProductionGroupCode;
/*      */ import com.universal.milestone.ReleasingFamily;
/*      */ import com.universal.milestone.Report;
/*      */ import com.universal.milestone.ReportConfigManager;
/*      */ import com.universal.milestone.ReportHandler;
/*      */ import com.universal.milestone.ReportManager;
/*      */ import com.universal.milestone.ReportSelectionsHelper;
/*      */ import com.universal.milestone.ReportingServices;
/*      */ import com.universal.milestone.SecureHandler;
/*      */ import com.universal.milestone.SelectionHandler;
/*      */ import com.universal.milestone.SelectionManager;
/*      */ import com.universal.milestone.TaskDueDateByTitleForPrintHandler;
/*      */ import com.universal.milestone.TaskDueDateForPrintSubHandler;
/*      */ import com.universal.milestone.Template;
/*      */ import com.universal.milestone.ToDoListReportForPrintSubHandler;
/*      */ import com.universal.milestone.UmeProdScheduleForPrintSubHandler;
/*      */ import com.universal.milestone.UmlAddsMovesReportSubHandler;
/*      */ import com.universal.milestone.UmlNewReleaseMasterScheduleSubHandler;
/*      */ import com.universal.milestone.UmlProductionScheduleForPrintSubHandler;
/*      */ import com.universal.milestone.User;
/*      */ import com.universal.milestone.VerveScheduleUpdateForPrintSubHandler;
/*      */ import inetsoft.report.ReportEnv;
/*      */ import inetsoft.report.Size;
/*      */ import inetsoft.report.StyleSheet;
/*      */ import inetsoft.report.XStyleSheet;
/*      */ import inetsoft.report.io.Builder;
/*      */ import inetsoft.report.lens.DefaultTableLens;
/*      */ import inetsoft.report.pdf.PDF4Generator;
/*      */ import java.awt.Color;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Font;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.InputStream;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.Calendar;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.ServletOutputStream;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ 
/*      */ public class ReportHandler extends SecureHandler {
/*      */   public static final String COMPONENT_CODE = "hRep";
/*  101 */   public static String reportPath = ""; public GeminiApplication application; public ComponentLog log; public static boolean ie55 = false; public HashMap corpHashMap;
/*      */   
/*      */   public ReportHandler(GeminiApplication application) {
/*  104 */     this.corpHashMap = null;
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
/*  115 */     this.application = application;
/*  116 */     this.log = application.getLog("hRep");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  124 */   public String getDescription() { return "Report"; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  132 */   public static void configure(EnhancedProperties props, GeminiApplication application) { reportPath = props.getProperty("ReportPath", ""); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean acceptRequest(Dispatcher dispatcher, Context context, String command) {
/*  142 */     if (super.acceptRequest(dispatcher, context, command))
/*      */     {
/*  144 */       if (command.startsWith("reports"))
/*      */       {
/*  146 */         return handleRequest(dispatcher, context, command);
/*      */       }
/*      */     }
/*      */     
/*  150 */     return false;
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
/*  162 */     if (command.equalsIgnoreCase("reports-editor")) {
/*      */       
/*  164 */       edit(context);
/*      */     }
/*  166 */     else if (command.equalsIgnoreCase("reports-standard")) {
/*      */       
/*  168 */       standard(context);
/*      */     }
/*  170 */     else if (command.equalsIgnoreCase("reports-print")) {
/*      */       
/*  172 */       ie55 = true;
/*  173 */       print(context, 0, true, dispatcher);
/*      */     }
/*  175 */     else if (command.equalsIgnoreCase("reports-save")) {
/*      */       
/*  177 */       save(context);
/*      */     
/*      */     }
/*  180 */     else if (command.equalsIgnoreCase("reports-search")) {
/*      */       
/*  182 */       search(dispatcher, context, command);
/*      */     } 
/*  184 */     if (command.equalsIgnoreCase("reports-sort")) {
/*      */       
/*  186 */       sort(dispatcher, context);
/*      */     
/*      */     }
/*  189 */     else if (command.equalsIgnoreCase("reports-criteria")) {
/*      */       
/*  191 */       criteria(context, dispatcher);
/*      */     }
/*  193 */     else if (command.equalsIgnoreCase("reports-summary")) {
/*      */       
/*  195 */       summary(context);
/*      */     }
/*  197 */     else if (command.equalsIgnoreCase("reports-print-rtf")) {
/*      */       
/*  199 */       print(context, 1, true, dispatcher);
/*      */     }
/*  201 */     else if (command.equalsIgnoreCase("reports-print-pdf4")) {
/*      */ 
/*      */       
/*  204 */       ie55 = false;
/*  205 */       print(context, 0, false, dispatcher);
/*      */     }
/*  207 */     else if (command.equalsIgnoreCase("reports-print-rtf4")) {
/*      */       
/*  209 */       print(context, 1, false, dispatcher);
/*      */     }
/*  211 */     else if (command.equalsIgnoreCase("reports-print-download")) {
/*      */ 
/*      */       
/*  214 */       if (!ReportingServices.usingReportServicesByContext(context)) {
/*  215 */         sendReport(context);
/*      */       }
/*  217 */     } else if (command.equalsIgnoreCase("reports-print-download-done")) {
/*      */       
/*  219 */       downloadDone(context);
/*      */     } 
/*      */     
/*  222 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean edit(Context context) {
/*  232 */     SelectionManager.getInstance().setSelectionNotepadUserDefaults(context);
/*      */     
/*  234 */     User user = MilestoneSecurity.getUser(context);
/*  235 */     Vector contents = new Vector();
/*      */     
/*  237 */     Notepad notepad = getReportsNotepad(contents, context, user.getUserId());
/*  238 */     MilestoneHelper.putNotepadIntoSession(notepad, context);
/*      */     
/*  240 */     Report report = MilestoneHelper.getScreenReport(context);
/*      */     
/*  242 */     if (report != null) {
/*      */       
/*  244 */       Form reportForm = buildForm(context, report);
/*      */ 
/*      */ 
/*      */       
/*  248 */       context.putDelivery("corporate-array", ReleasingFamily.getJavaScriptCorporateArrayReleasingFamily(context));
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  253 */       context.putDelivery("configArrays", String.valueOf(Cache.getJavaScriptConfigArray("")) + " " + Cache.getJavaScriptSubConfigArray(""));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  260 */       ReportSelectionsHelper.deliverMultSelectValuesForRefresh("family", reportForm, context);
/*  261 */       ReportSelectionsHelper.deliverMultSelectValuesForRefresh("environment", reportForm, context);
/*  262 */       ReportSelectionsHelper.deliverMultSelectValuesForRefresh("company", reportForm, context);
/*  263 */       ReportSelectionsHelper.deliverMultSelectValuesForRefresh("Label", reportForm, context);
/*  264 */       ReportSelectionsHelper.deliverMultSelectValuesForRefresh("configuration", reportForm, context);
/*  265 */       ReportSelectionsHelper.deliverMultSelectValuesForRefresh("subconfiguration", reportForm, context);
/*      */ 
/*      */       
/*  268 */       context.putDelivery("Form", reportForm);
/*  269 */       context.putSessionValue("report", report);
/*      */       
/*  271 */       return context.includeJSP("reports-editor.jsp");
/*      */     } 
/*  273 */     return goToBlank(context);
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
/*      */   private boolean goToBlank(Context context) {
/*  286 */     context.putDelivery("isNotePadVisible", new Boolean(MilestoneHelper.getNotePadVisiblitiy(3, context)));
/*      */     
/*  288 */     Form form = new Form(this.application, "form", this.application.getInfrastructure().getServletURL(), "Post");
/*  289 */     form.addElement(new FormHidden("cmd", "reports-editor"));
/*  290 */     form.addElement(new FormHidden("OrderBy", "", true));
/*      */ 
/*      */ 
/*      */     
/*  294 */     FormTextField ReportDescriptionSearch = new FormTextField("ReportDescriptionSearch", "", false, 30, 30);
/*  295 */     ReportDescriptionSearch.setId("ReportDescriptionSearch");
/*  296 */     form.addElement(ReportDescriptionSearch);
/*      */     
/*  298 */     form.addElement(new FormHidden("OrderBy", "", true));
/*      */     
/*  300 */     if (context.getRequest().getParameter("OrderByDirection") != null) {
/*  301 */       form.addElement(new FormHidden("OrderByDirection", context.getRequest().getParameter("OrderByDirection"), true));
/*      */     } else {
/*  303 */       form.addElement(new FormHidden("OrderByDirection", "0", true));
/*      */     } 
/*  305 */     context.putDelivery("Form", form);
/*      */     
/*  307 */     return context.includeJSP("blank-reports-editor.jsp");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  313 */   public void standard(Context context) { context.includeJSP("reports-standard.jsp"); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void print(Context context, int pdfRtf, boolean ie55, Dispatcher dispatcher) {
/*  323 */     ReportEnv.setProperty("license_key", "E76B-566-ERX-F3601DCC7ABF");
/*      */ 
/*      */     
/*  326 */     context.removeSessionValue("currentReport");
/*      */ 
/*      */     
/*  329 */     Report rep = (Report)context.getSessionValue("Report");
/*  330 */     if (rep != null) {
/*      */       
/*  332 */       String repName = rep.getReportName();
/*  333 */       if (repName == null)
/*  334 */         repName = ""; 
/*  335 */       String reportTemplate = null;
/*      */ 
/*      */ 
/*      */       
/*  339 */       ReportingServices.getReportServices(repName);
/*  340 */       System.out.println("--- After getReportServices - repName = " + repName + " --- ");
/*      */       
/*  342 */       if (repName.equalsIgnoreCase("Com Rel")) {
/*  343 */         reportTemplate = String.valueOf(reportPath) + "\\ent_com_release_sched.xml";
/*      */       }
/*  345 */       else if (repName.equalsIgnoreCase("eInitRel")) {
/*  346 */         reportTemplate = String.valueOf(reportPath) + "\\eInitiative_release_sched.xml";
/*      */       }
/*  348 */       else if (repName.equalsIgnoreCase("Rel.Shed.")) {
/*  349 */         reportTemplate = String.valueOf(reportPath) + "\\ent_release_sched.xml";
/*      */       }
/*  351 */       else if (repName.equalsIgnoreCase("ToDoList")) {
/*  352 */         reportTemplate = String.valueOf(reportPath) + "\\ent_todo_list.xml";
/*      */       }
/*  354 */       else if (repName.equalsIgnoreCase("Nash Rel")) {
/*  355 */         reportTemplate = String.valueOf(reportPath) + "\\nas_release_sched.xml";
/*      */       }
/*  357 */       else if (repName.equalsIgnoreCase("UmeProdSch")) {
/*  358 */         reportTemplate = String.valueOf(reportPath) + "\\ume_prod_sched.xml";
/*      */       }
/*  360 */       else if (repName.equalsIgnoreCase("UmeCustomS")) {
/*  361 */         reportTemplate = String.valueOf(reportPath) + "\\ume_custom_prod_sched.xml";
/*      */       }
/*  363 */       else if (repName.equalsIgnoreCase("NasProdSch")) {
/*  364 */         reportTemplate = String.valueOf(reportPath) + "\\nashProductionSchedule.xml";
/*      */       }
/*  366 */       else if (repName.equalsIgnoreCase("DWNashProd")) {
/*  367 */         reportTemplate = String.valueOf(reportPath) + "\\dream_nashProductionSchedule.xml";
/*      */       }
/*  369 */       else if (repName.equalsIgnoreCase("New Rel.")) {
/*  370 */         reportTemplate = String.valueOf(reportPath) + "\\umlNewReleaseMasterSchedule.xml";
/*  371 */       } else if (repName.equalsIgnoreCase("New Rel Me")) {
/*  372 */         reportTemplate = String.valueOf(reportPath) + "\\umlNewReleaseMasterScheduleMetadata.xml";
/*      */       }
/*  374 */       else if (repName.equalsIgnoreCase("UmlAddMov")) {
/*  375 */         reportTemplate = String.valueOf(reportPath) + "\\umlAddsMovesReport.xml";
/*      */       }
/*  377 */       else if (repName.equalsIgnoreCase("FutDueDate")) {
/*  378 */         reportTemplate = String.valueOf(reportPath) + "\\FutureTaskDueDates.xml";
/*      */       }
/*  380 */       else if (repName.equalsIgnoreCase("VerProdSch")) {
/*  381 */         reportTemplate = String.valueOf(reportPath) + "\\verveProductionSchedule.xml";
/*      */       }
/*  383 */       else if (repName.equalsIgnoreCase("LatProdSch")) {
/*  384 */         reportTemplate = String.valueOf(reportPath) + "\\latinoProductionSchedule.xml";
/*      */       }
/*  386 */       else if (repName.equalsIgnoreCase("IgaProd")) {
/*  387 */         reportTemplate = String.valueOf(reportPath) + "\\igaProductionSchedule.xml";
/*      */       }
/*  389 */       else if (repName.equalsIgnoreCase("ent rel La")) {
/*  390 */         reportTemplate = String.valueOf(reportPath) + "\\ent_release_sched_by_label.xml";
/*      */       }
/*  392 */       else if (repName.equalsIgnoreCase("igaprod")) {
/*  393 */         reportTemplate = String.valueOf(reportPath) + "\\igaproductionschedule.xml";
/*      */       }
/*  395 */       else if (repName.equalsIgnoreCase("IDJProd")) {
/*      */         
/*  397 */         reportTemplate = String.valueOf(reportPath) + "\\idjProductionSchedule.xml";
/*      */       
/*      */       }
/*  400 */       else if (repName.equalsIgnoreCase("addsmoves")) {
/*  401 */         reportTemplate = String.valueOf(reportPath) + "\\Adds_and_moves.xml";
/*      */       }
/*  403 */       else if (repName.equalsIgnoreCase("ClassRls")) {
/*  404 */         reportTemplate = String.valueOf(reportPath) + "\\ent_classics_rel_sched.xml";
/*      */       }
/*  406 */       else if (repName.equalsIgnoreCase("ClassProd")) {
/*  407 */         reportTemplate = String.valueOf(reportPath) + "\\ent_classics_prod_sched.xml";
/*      */       }
/*  409 */       else if (repName.equalsIgnoreCase("CapitlProd")) {
/*  410 */         reportTemplate = String.valueOf(reportPath) + "\\ent_capitol_prod_sched.xml";
/*      */       }
/*  412 */       else if (repName.equalsIgnoreCase("CarolnProd")) {
/*  413 */         reportTemplate = String.valueOf(reportPath) + "\\ent_caroline_prod_sched.xml";
/*      */       }
/*  415 */       else if (repName.equalsIgnoreCase("DWNashRls")) {
/*  416 */         reportTemplate = String.valueOf(reportPath) + "\\ent_dreamworks_release_schedule.xml";
/*      */       }
/*  418 */       else if (repName.equalsIgnoreCase("RlswoutSch")) {
/*  419 */         reportTemplate = String.valueOf(reportPath) + "\\ent_release_withoutsched.xml";
/*      */       }
/*  421 */       else if (repName.equalsIgnoreCase("pastduerls")) {
/*  422 */         reportTemplate = String.valueOf(reportPath) + "\\ent_past_due_releases.xml";
/*      */       }
/*  424 */       else if (repName.equalsIgnoreCase("McaProdScd")) {
/*  425 */         reportTemplate = String.valueOf(reportPath) + "\\mca_production_schedule.xml";
/*      */       }
/*  427 */       else if (repName.equalsIgnoreCase("McaCustImp")) {
/*  428 */         reportTemplate = String.valueOf(reportPath) + "\\mca_impact_schedule.xml";
/*      */       }
/*  430 */       else if (repName.equalsIgnoreCase("UmlProdScd")) {
/*  431 */         reportTemplate = String.valueOf(reportPath) + "\\uml_production_schedule.xml";
/*      */       }
/*  433 */       else if (repName.equalsIgnoreCase("PressPProd")) {
/*  434 */         reportTemplate = String.valueOf(reportPath) + "\\press_play_production_schedule.xml";
/*      */       }
/*  436 */       else if (repName.equalsIgnoreCase("DreRecSchd")) {
/*  437 */         reportTemplate = String.valueOf(reportPath) + "\\dream_record_schedule.xml";
/*      */       }
/*  439 */       else if (repName.equalsIgnoreCase("DreProSchd")) {
/*  440 */         reportTemplate = String.valueOf(reportPath) + "\\dream_promo_schedule.xml";
/*      */       }
/*  442 */       else if (repName.equalsIgnoreCase("NewPlanScd")) {
/*  443 */         reportTemplate = String.valueOf(reportPath) + "\\new_release_planning_schedule.xml";
/*      */       }
/*  445 */       else if (repName.equalsIgnoreCase("EntComAlb")) {
/*  446 */         reportTemplate = String.valueOf(reportPath) + "\\ent_comm_past_rel_dates_albums.xml";
/*      */       }
/*  448 */       else if (repName.equalsIgnoreCase("EntProAlb")) {
/*  449 */         reportTemplate = String.valueOf(reportPath) + "\\ent_promo_past_rel_dates_albums.xml";
/*      */       }
/*  451 */       else if (repName.equalsIgnoreCase("EntComOrph")) {
/*  452 */         reportTemplate = String.valueOf(reportPath) + "\\ent_comm_past_rel_dates_orphans.xml";
/*      */       }
/*  454 */       else if (repName.equalsIgnoreCase("EntProOrph")) {
/*  455 */         reportTemplate = String.valueOf(reportPath) + "\\ent_promo_past_rel_dates_orphans.xml";
/*      */       }
/*  457 */       else if (repName.equalsIgnoreCase("VerveComm")) {
/*  458 */         reportTemplate = String.valueOf(reportPath) + "\\verveCommercialSchedule.xml";
/*      */       }
/*  460 */       else if (repName.equalsIgnoreCase("DigProdSch")) {
/*  461 */         reportTemplate = String.valueOf(reportPath) + "\\digitalProductionSchedule.xml";
/*      */       }
/*  463 */       else if (repName.equalsIgnoreCase("DigProLSch")) {
/*  464 */         reportTemplate = String.valueOf(reportPath) + "\\digitalProductionLabelSchedule.xml";
/*      */       }
/*  466 */       else if (repName.equalsIgnoreCase("PhyProdAct")) {
/*  467 */         reportTemplate = String.valueOf(reportPath) + "\\physicalProductActivitySummary.xml";
/*      */       }
/*  469 */       else if (repName.equalsIgnoreCase("IDJProdAlt")) {
/*  470 */         reportTemplate = String.valueOf(reportPath) + "\\idjProductionScheduleAlternate.xml";
/*      */       }
/*  472 */       else if (repName.equalsIgnoreCase("MexProdSch")) {
/*  473 */         reportTemplate = String.valueOf(reportPath) + "\\mexicoProductionSchedule.xml";
/*      */       } else {
/*      */         
/*  476 */         if (repName.equalsIgnoreCase("Prod.Stat.")) {
/*      */ 
/*      */           
/*      */           try {
/*      */             
/*  481 */             StyleSheet report = EntProdStatusForPrintSubHandler.fillEntProdStatusForPrint(context, 
/*  482 */                 reportPath);
/*  483 */             HttpServletResponse sresponse = context.getResponse();
/*      */             
/*  485 */             context.putDelivery("status", new String("start_download"));
/*  486 */             sresponse.setContentType("text/plain");
/*  487 */             context.putSessionValue("currentReport", report);
/*  488 */             context.putSessionValue("currentReportName", new String("Prod.Stat."));
/*      */             
/*  490 */             context.putDelivery("download", new String("yes"));
/*  491 */             if (!ReportingServices.usingReportServicesByContext(context))
/*      */             {
/*  493 */               context.includeJSP("status.jsp", "hiddenFrame");
/*  494 */               sresponse.flushBuffer();
/*      */             }
/*      */           
/*  497 */           } catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */           
/*      */           return;
/*      */         } 
/*      */ 
/*      */         
/*  505 */         if (repName.equalsIgnoreCase("TaskDueDt")) {
/*      */ 
/*      */           
/*      */           try {
/*  509 */             StyleSheet report = TaskDueDateForPrintSubHandler.fillTaskDueDateForPrint(context, 
/*  510 */                 reportPath);
/*  511 */             HttpServletResponse sresponse = context.getResponse();
/*      */             
/*  513 */             context.putDelivery("status", new String("start_download"));
/*  514 */             sresponse.setContentType("text/plain");
/*  515 */             context.putSessionValue("currentReport", report);
/*  516 */             context.putSessionValue("currentReportName", new String("TaskDueDt"));
/*      */             
/*  518 */             context.putDelivery("download", new String("yes"));
/*  519 */             if (!ReportingServices.usingReportServicesByContext(context))
/*      */             {
/*  521 */               context.includeJSP("status.jsp", "hiddenFrame");
/*  522 */               sresponse.flushBuffer();
/*      */             }
/*      */           
/*  525 */           } catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */           
/*      */           return;
/*      */         } 
/*      */ 
/*      */         
/*  533 */         if (repName.equalsIgnoreCase("TaskDueTit")) {
/*      */ 
/*      */           
/*      */           try {
/*  537 */             StyleSheet report = TaskDueDateByTitleForPrintHandler.fillTaskDueDateByTitleForPrint(context, reportPath, this.log);
/*  538 */             HttpServletResponse sresponse = context.getResponse();
/*      */             
/*  540 */             context.putDelivery("status", new String("start_download"));
/*  541 */             sresponse.setContentType("text/plain");
/*  542 */             context.putSessionValue("currentReport", report);
/*  543 */             context.putSessionValue("currentReportName", new String("TaskDueTit"));
/*      */             
/*  545 */             context.putDelivery("download", new String("yes"));
/*  546 */             if (!ReportingServices.usingReportServicesByContext(context))
/*      */             {
/*  548 */               context.includeJSP("status.jsp", "hiddenFrame");
/*  549 */               sresponse.flushBuffer();
/*      */             }
/*      */           
/*  552 */           } catch (Exception exception) {}
/*      */ 
/*      */           
/*      */           return;
/*      */         } 
/*      */ 
/*      */         
/*  559 */         if (repName.equalsIgnoreCase("MCARelSchd")) {
/*      */ 
/*      */           
/*      */           try {
/*  563 */             StyleSheet report = MCACustomReleaseForPrintSubHandler.fillMCACustomReleaseForPrint(context, 
/*  564 */                 reportPath);
/*  565 */             HttpServletResponse sresponse = context.getResponse();
/*      */             
/*  567 */             context.putDelivery("status", new String("start_download"));
/*  568 */             sresponse.setContentType("text/plain");
/*  569 */             context.putSessionValue("currentReport", report);
/*  570 */             context.putSessionValue("currentReportName", new String("MCARelSchd"));
/*      */             
/*  572 */             context.putDelivery("download", new String("yes"));
/*  573 */             if (!ReportingServices.usingReportServicesByContext(context))
/*      */             {
/*  575 */               context.includeJSP("status.jsp", "hiddenFrame");
/*  576 */               sresponse.flushBuffer();
/*      */             }
/*      */           
/*  579 */           } catch (Exception exception) {}
/*      */ 
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  587 */       if (reportTemplate != null) {
/*      */ 
/*      */         
/*  590 */         System.out.println("Getting the report template - " + reportTemplate);
/*      */ 
/*      */         
/*      */         try {
/*  594 */           InputStream input = new FileInputStream(reportTemplate);
/*  595 */           System.out.println("Building report ");
/*      */           
/*  597 */           XStyleSheet report = 
/*  598 */             (XStyleSheet)Builder.getBuilder(1, input).read(null);
/*      */           
/*  600 */           HttpServletResponse sresponse = context.getResponse();
/*      */           
/*  602 */           System.out.println("Executing report ");
/*      */ 
/*      */           
/*  605 */           context.putDelivery("status", new String("start_gathering"));
/*  606 */           context.putDelivery("percent", new String("10"));
/*  607 */           if (!ReportingServices.usingReportServicesByContext(context)) {
/*      */             
/*  609 */             context.includeJSP("status.jsp", "hiddenFrame");
/*  610 */             sresponse.setContentType("text/plain");
/*  611 */             sresponse.flushBuffer();
/*      */           } 
/*      */           
/*  614 */           context.putDelivery("status", new String("start_gathering"));
/*  615 */           context.putDelivery("percent", new String("10"));
/*  616 */           if (!ReportingServices.usingReportServicesByContext(context)) {
/*      */             
/*  618 */             context.includeJSP("status.jsp", "hiddenFrame");
/*  619 */             sresponse.setContentType("text/plain");
/*  620 */             sresponse.flushBuffer();
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/*  625 */           if (report != null) {
/*      */ 
/*      */             
/*  628 */             if (repName.equalsIgnoreCase("Com Rel")) {
/*  629 */               EntCommRelScheduleForPrintSubHandler.fillEntCommRelScheduleForPrint(report, context);
/*      */             }
/*      */ 
/*      */             
/*  633 */             if (repName.equalsIgnoreCase("eInitRel")) {
/*  634 */               eInitiativeRelScheduleForPrintSubHandler.filleInitiativeRelScheduleForPrint(report, context, this.application);
/*      */ 
/*      */             
/*      */             }
/*  638 */             else if (repName.equalsIgnoreCase("Rel.Shed.")) {
/*  639 */               EntRelScheduleForPrintSubHandler.fillEntRelScheduleForPrint(report, context);
/*      */             
/*      */             }
/*  642 */             else if (repName.equalsIgnoreCase("Nash Rel")) {
/*  643 */               NashRelScheduleForPrintSubHandler.fillNashRelScheduleForPrint(report, context);
/*      */             }
/*  645 */             else if (repName.equalsIgnoreCase("RlswoutSch")) {
/*  646 */               EntRelWithoutScheduleForPrintSubHandler.fillEntRelWithoutScheduleForPrint(report, context);
/*      */             
/*      */             }
/*  649 */             else if (repName.equalsIgnoreCase("DWNashRls")) {
/*  650 */               DreamWorksRelScheduleForPrintSubHandler.fillDreamWorksRelScheduleForPrint(report, context);
/*      */             
/*      */             }
/*  653 */             else if (repName.equalsIgnoreCase("UmeProdSch")) {
/*  654 */               UmeProdScheduleForPrintSubHandler.fillUmeProdScheduleForPrint(report, context);
/*      */             }
/*  656 */             else if (repName.equalsIgnoreCase("UmeCustomS")) {
/*  657 */               UmeCustomProdScheduleForPrintSubHandler.fillUmeCustomProdScheduleForPrint(report, context);
/*      */             }
/*  659 */             else if (repName.equalsIgnoreCase("New Rel.")) {
/*  660 */               UmlNewReleaseMasterScheduleSubHandler.fillUmlNewReleaseMasterScheduleForPrint(report, context);
/*  661 */             } else if (repName.equalsIgnoreCase("New Rel Me")) {
/*  662 */               UmlNewReleaseMasterScheduleMetaSubHandler.fillUmlNewReleaseMasterScheduleForPrint(report, context);
/*      */             }
/*  664 */             else if (repName.equalsIgnoreCase("UmlAddMov")) {
/*  665 */               UmlAddsMovesReportSubHandler.fillUmlAddsMovesReportForPrint(report, context);
/*      */             }
/*  667 */             else if (repName.equalsIgnoreCase("FutDueDate")) {
/*      */               
/*  669 */               FutureTaskDueDatesByTemplateSubHandler futureDueDates = 
/*  670 */                 new FutureTaskDueDatesByTemplateSubHandler(this.application);
/*  671 */               futureDueDates.fillFutureTaskDueDatesByTemplateSubHandler(report, context);
/*  672 */               futureDueDates = null;
/*      */ 
/*      */             
/*      */             }
/*  676 */             else if (repName.equalsIgnoreCase("VerProdSch")) {
/*  677 */               VerveScheduleUpdateForPrintSubHandler.fillVerveScheduleUpdateForPrint(report, context);
/*      */             }
/*  679 */             else if (repName.equalsIgnoreCase("LatProdSch")) {
/*  680 */               LatinoProductionScheduleUpdateForPrintSubHandler.fillLatinoProductionScheduleUpdateForPrint(report, context);
/*      */             }
/*  682 */             else if (repName.equalsIgnoreCase("ent rel la")) {
/*  683 */               EntReleaseSchedByLabelSubHandler.fillEntReleaseSchedByLabelForPrint(report, context);
/*      */             }
/*  685 */             else if (repName.equalsIgnoreCase("igaprod")) {
/*  686 */               IgaProductionScheduleUpdateForPrintSubHandler.fillIgaProductionScheduleForPrint(report, context);
/*      */             }
/*  688 */             else if (repName.equalsIgnoreCase("IDJProd")) {
/*  689 */               IdjProductionScheduleForPrintSubHandlerNew2091.fillIdjProductionScheduleForPrint(report, context);
/*      */             }
/*  691 */             else if (repName.equalsIgnoreCase("IDJProdAlt")) {
/*  692 */               IdjProductionScheduleForPrintSubHandlerAlternate.fillIdjProductionScheduleForPrint(report, context);
/*      */             }
/*  694 */             else if (repName.equalsIgnoreCase("addsmoves")) {
/*  695 */               AddsAndMovesForPrintSubHandler.fillAddsAndMovesForPrint(report, context);
/*      */             }
/*  697 */             else if (repName.equalsIgnoreCase("NasProdSch") || repName.equalsIgnoreCase("DWNashProd")) {
/*  698 */               NashProdScheduleForPrintSubHandler.fillNashProdScheduleForPrint2(report, context);
/*      */             }
/*  700 */             else if (repName.equalsIgnoreCase("pastduerls")) {
/*  701 */               PastDueReleasesForPrintSubHandler.fillPastDueReleasesForPrint(report, context);
/*      */             }
/*  703 */             else if (repName.equalsIgnoreCase("ClassRls")) {
/*  704 */               ClassicsRelSchdForPrintSubHandler.fillClassicsRelSchdForPrint(report, context);
/*      */             }
/*  706 */             else if (repName.equalsIgnoreCase("McaProdScd")) {
/*  707 */               MCAProductionScheduleForPrintSubHandler.fillMCAProductionScheduleForPrint(report, context);
/*      */             }
/*  709 */             else if (repName.equalsIgnoreCase("ClassProd")) {
/*  710 */               ClassicsProductionScheduleForPrintSubHandler.fillClassicProductionUpdateForPrint(report, context);
/*      */             }
/*  712 */             else if (repName.equalsIgnoreCase("CapitlProd")) {
/*  713 */               CapitolProductionSchedulePrintSubHandler.fillCapitolProductionUpdateForPrint(report, context);
/*      */             }
/*  715 */             else if (repName.equalsIgnoreCase("CarolnProd")) {
/*  716 */               CarolineProductionSchedulePrintSubHandler.fillCarolineProductionUpdateForPrint(report, context);
/*      */             }
/*  718 */             else if (repName.equalsIgnoreCase("ToDoList")) {
/*  719 */               ToDoListReportForPrintSubHandler.fillEntToDoListForPrint(report, context);
/*      */             }
/*  721 */             else if (repName.equalsIgnoreCase("McaCustImp")) {
/*  722 */               MCACustomImpactScheduleForPrintSubHandler.fillMCACustomImpactScheduleForPrint(report, context);
/*      */             }
/*  724 */             else if (repName.equalsIgnoreCase("UmlProdScd")) {
/*  725 */               UmlProductionScheduleForPrintSubHandler.fillUmlProductionScheduleForPrint(report, context);
/*      */             }
/*  727 */             else if (repName.equalsIgnoreCase("PressPProd")) {
/*  728 */               PressPlayProductionScheduleForPrintSubHandler.fillPressPlayProductionScheduleForPrint(report, context);
/*      */             }
/*  730 */             else if (repName.equalsIgnoreCase("DreRecSchd")) {
/*  731 */               DreamWorksRecordScheduleForPrintSubHandler.fillDreamWorksRecordScheduleForPrint(report, context);
/*      */             }
/*  733 */             else if (repName.equalsIgnoreCase("DreProSchd")) {
/*  734 */               DreamWorksPromoScheduleForPrintSubHandler.fillDreamWorksPromoScheduleForPrint(report, context);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             }
/*  742 */             else if (repName.equalsIgnoreCase("NewPlanScd")) {
/*  743 */               MCANewReleasePlanningSchedulePrintSubHandler.fillMCANewReleasePlanningScheduleForPrint(report, context);
/*      */             }
/*  745 */             else if (repName.equalsIgnoreCase("EntProAlb")) {
/*  746 */               EntPastRelAlbumsForPrintSubHandler.fillEntPastRelDatesForPrint(report, context, "promo");
/*      */             }
/*  748 */             else if (repName.equalsIgnoreCase("EntComAlb")) {
/*  749 */               EntPastRelAlbumsForPrintSubHandler.fillEntPastRelDatesForPrint(report, context, "commercial");
/*      */             }
/*  751 */             else if (repName.equalsIgnoreCase("EntProOrph")) {
/*  752 */               EntPastRelOrphansForPrintSubHandler.fillEntPastRelDatesForPrint(report, context, "promo");
/*      */             }
/*  754 */             else if (repName.equalsIgnoreCase("EntComOrph")) {
/*  755 */               EntPastRelOrphansForPrintSubHandler.fillEntPastRelDatesForPrint(report, context, "commercial");
/*      */             }
/*  757 */             else if (repName.equalsIgnoreCase("VerveComm")) {
/*  758 */               VerveCommercialReleaseScheduleForPrintSubHandler.fillVerveScheduleUpdateForPrint(report, context);
/*      */             }
/*  760 */             else if (repName.equalsIgnoreCase("DigProdSch")) {
/*  761 */               DigitalProductionScheduleForPrintSubHandler.fillDigitalProductionScheduleUpdateForPrint(report, context);
/*      */             }
/*  763 */             else if (repName.equalsIgnoreCase("DigProLSch")) {
/*  764 */               DigitalProductionLabelScheduleForPrintSubHandler.fillDigitalProductionLabelScheduleUpdateForPrint(report, context);
/*      */             }
/*  766 */             else if (repName.equalsIgnoreCase("PhyProdAct")) {
/*  767 */               PhysicalProductActivitySummaryForPrintSubHandler.fillPhysicalProductActivitySummaryScheduleUpdateForPrint(report, context);
/*      */             }
/*  769 */             else if (repName.equalsIgnoreCase("MexProdSch")) {
/*  770 */               MexicoScheduleForPrintSubHandler.fillMexicoScheduleForPrint(report, context);
/*      */             } 
/*      */           } 
/*      */           
/*  774 */           context.putDelivery("status", new String("start_download"));
/*  775 */           sresponse.setContentType("text/plain");
/*  776 */           context.putSessionValue("currentReport", report);
/*  777 */           context.putDelivery("download", new String("yes"));
/*  778 */           if (!ReportingServices.usingReportServicesByContext(context)) {
/*      */             
/*  780 */             context.includeJSP("status.jsp", "hiddenFrame");
/*  781 */             sresponse.flushBuffer();
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           return;
/*  788 */         } catch (Exception e) {
/*      */           
/*  790 */           System.out.print("Exceptin error found " + e.getMessage());
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  795 */     edit(context);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  802 */   public void save(Context context) { edit(context); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Notepad getReportsNotepad(Vector contents, Context context, int userId) {
/*  812 */     if (MilestoneHelper.getNotepadFromSession(3, context) != null) {
/*      */       
/*  814 */       Notepad notepad = MilestoneHelper.getNotepadFromSession(3, context);
/*      */       
/*  816 */       if (notepad.getAllContents() == null) {
/*      */         
/*  818 */         this.log.debug("---------Reseting note pad contents------------");
/*  819 */         contents = ReportManager.getInstance().getReportNotepadList(notepad, context);
/*      */         
/*  821 */         notepad.setAllContents(contents);
/*      */       } 
/*      */       
/*  824 */       return notepad;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  829 */     String[] columnNames = { "Description" };
/*  830 */     contents = ReportManager.getInstance().getReportNotepadList(null, context);
/*  831 */     return new Notepad(contents, 0, 15, "Reports", 3, columnNames);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Form buildForm(Context context, Report report) {
/*  841 */     User user = MilestoneSecurity.getUser(context);
/*      */ 
/*      */     
/*  844 */     Form reportForm = new Form(this.application, "reportForm", 
/*  845 */         this.application.getInfrastructure().getServletURL(), "POST");
/*      */ 
/*      */     
/*  848 */     reportForm.addElement(new FormHidden("cmd", "reports-editor", true));
/*      */ 
/*      */ 
/*      */     
/*  852 */     FormTextField ReportDescriptionSearch = new FormTextField("ReportDescriptionSearch", "", false, 30, 30);
/*  853 */     ReportDescriptionSearch.setId("ReportDescriptionSearch");
/*  854 */     reportForm.addElement(ReportDescriptionSearch);
/*      */     
/*  856 */     reportForm.addElement(new FormHidden("OrderBy", "", true));
/*      */     
/*  858 */     if (context.getRequest().getParameter("OrderByDirection") != null) {
/*  859 */       reportForm.addElement(new FormHidden("OrderByDirection", context.getRequest().getParameter("OrderByDirection"), true));
/*      */     } else {
/*  861 */       reportForm.addElement(new FormHidden("OrderByDirection", "0", true));
/*      */     } 
/*      */     
/*  864 */     if (report != null) {
/*      */ 
/*      */       
/*  867 */       boolean endDate = false;
/*  868 */       endDate = report.getEndDateFlag();
/*      */       
/*  870 */       if (endDate) {
/*      */ 
/*      */         
/*  873 */         FormTextField EndDate = new FormTextField("endDate", "", true, 10);
/*  874 */         EndDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/*      */         
/*  876 */         EndDate.addFormEvent("onBlur", "JavaScript:removeSpacesInDate(document.forms[0].endDate.value,this);if (!checkField(this))this.focus();");
/*  877 */         EndDate.addFormEvent("onClick", "Javascript:hidePrintButtons('printWindow','');");
/*  878 */         EndDate.setTabIndex(2);
/*  879 */         reportForm.addElement(EndDate);
/*      */       } 
/*      */ 
/*      */       
/*  883 */       boolean beginDate = false;
/*      */       
/*  885 */       beginDate = report.getBeginDateFlag();
/*      */       
/*  887 */       if (beginDate) {
/*      */ 
/*      */         
/*  890 */         FormTextField BeginDate = new FormTextField("beginDate", "", true, 10);
/*  891 */         BeginDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/*      */         
/*  893 */         BeginDate.addFormEvent("onBlur", "JavaScript:removeSpacesInDate(document.forms[0].beginDate.value,this);if (!checkField(this))this.focus();");
/*  894 */         BeginDate.addFormEvent("onClick", "Javascript:hidePrintButtons('printWindow','');");
/*  895 */         BeginDate.setTabIndex(1);
/*  896 */         reportForm.addElement(BeginDate);
/*      */       } 
/*      */ 
/*      */       
/*  900 */       boolean endDueDate = false;
/*  901 */       endDueDate = report.getEndDueDateFlag();
/*      */       
/*  903 */       if (endDueDate) {
/*      */ 
/*      */         
/*  906 */         FormTextField EndDueDate = new FormTextField("endDueDate", "", true, 10);
/*  907 */         EndDueDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/*      */         
/*  909 */         EndDueDate.addFormEvent("onBlur", "JavaScript:removeSpacesInDate(document.forms[0].endDueDate.value,this);if (!checkField(this))this.focus();");
/*  910 */         EndDueDate.addFormEvent("onClick", "Javascript:hidePrintButtons('printWindow','');");
/*  911 */         EndDueDate.setTabIndex(4);
/*  912 */         reportForm.addElement(EndDueDate);
/*      */       } 
/*      */ 
/*      */       
/*  916 */       boolean beginDueDate = false;
/*      */       
/*  918 */       beginDueDate = report.getBeginDueDateFlag();
/*      */       
/*  920 */       if (beginDueDate) {
/*      */ 
/*      */         
/*  923 */         FormTextField BeginDueDate = new FormTextField("beginDueDate", "", true, 10);
/*  924 */         BeginDueDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/*      */         
/*  926 */         BeginDueDate.addFormEvent("onBlur", "JavaScript:removeSpacesInDate(document.forms[0].beginDueDate.value,this);if (!checkField(this))this.focus();");
/*  927 */         BeginDueDate.addFormEvent("onClick", "Javascript:hidePrintButtons('printWindow','');");
/*  928 */         BeginDueDate.setTabIndex(3);
/*  929 */         reportForm.addElement(BeginDueDate);
/*      */       } 
/*      */ 
/*      */       
/*  933 */       boolean region = false;
/*  934 */       region = report.getRegionFlag();
/*      */       
/*  936 */       if (region) {
/*      */         
/*  938 */         FormRadioButtonGroup Region = new FormRadioButtonGroup("distribution", "All", "West,East,All", false);
/*  939 */         Region.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*  940 */         Region.setTabIndex(7);
/*  941 */         reportForm.addElement(Region);
/*      */       } 
/*      */ 
/*      */       
/*  945 */       boolean productGroupCode = false;
/*  946 */       productGroupCode = report.getProductGroupCodeFlag();
/*  947 */       if (productGroupCode) {
/*      */ 
/*      */ 
/*      */         
/*  951 */         Vector productionGroupCodeVector = ReportConfigManager.getATLASProductionGroupCodes();
/*  952 */         String productionGroupCodeList = "All,";
/*  953 */         String idList = "0,";
/*      */         
/*  955 */         if (productionGroupCodeVector != null) {
/*      */           
/*  957 */           for (int i = 0; i < productionGroupCodeVector.size(); i++)
/*      */           {
/*  959 */             ProductionGroupCode productionGroupCode = (ProductionGroupCode)productionGroupCodeVector.get(i);
/*  960 */             if (i < productionGroupCodeVector.size())
/*      */             {
/*  962 */               if (productionGroupCode != null)
/*      */               {
/*  964 */                 productionGroupCodeList = String.valueOf(productionGroupCodeList) + productionGroupCode.getName() + ",";
/*  965 */                 idList = String.valueOf(idList) + productionGroupCode.getId() + ",";
/*      */               
/*      */               }
/*      */             
/*      */             }
/*  970 */             else if (productionGroupCode != null)
/*      */             {
/*  972 */               productionGroupCodeList = String.valueOf(productionGroupCodeList) + productionGroupCode.getName();
/*  973 */               idList = String.valueOf(idList) + productionGroupCode.getId();
/*      */             }
/*      */           
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/*  980 */           productionGroupCodeList = "All";
/*  981 */           idList = "0";
/*      */         } 
/*      */         
/*  984 */         int defaultProductionGroupCode = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  989 */         FormDropDownMenu prodGroupCodeDropDown = new FormDropDownMenu("productionGroupCode", String.valueOf(defaultProductionGroupCode), productionGroupCodeList, productionGroupCodeList, false);
/*      */         
/*  991 */         prodGroupCodeDropDown.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*  992 */         prodGroupCodeDropDown.setTabIndex(10);
/*  993 */         reportForm.addElement(prodGroupCodeDropDown);
/*      */       } 
/*      */ 
/*      */       
/*  997 */       boolean releaseType = false;
/*  998 */       releaseType = report.getReleaseTypeFlag();
/*      */       
/* 1000 */       if (releaseType) {
/*      */ 
/*      */ 
/*      */         
/* 1004 */         String defaultReleaseType = "All";
/*      */         
/* 1006 */         if (user.getPreferences().getReportsReleaseType() == 1) {
/* 1007 */           defaultReleaseType = "Commercial";
/* 1008 */         } else if (user.getPreferences().getReportsReleaseType() == 2) {
/* 1009 */           defaultReleaseType = "Promotional";
/*      */         } 
/* 1011 */         FormRadioButtonGroup ReleaseType = new FormRadioButtonGroup("releaseType", defaultReleaseType, "Commercial, Promotional, All", false);
/* 1012 */         ReleaseType.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 1013 */         ReleaseType.setTabIndex(8);
/* 1014 */         reportForm.addElement(ReleaseType);
/*      */       } 
/*      */ 
/*      */       
/* 1018 */       boolean status = false;
/* 1019 */       status = report.getStatusFlag();
/* 1020 */       String[] statusArray = { "Closed", "Active", "Active, excluding TBS", "All", "TBS", "All, excluding TBS", "Cancelled" };
/*      */       
/* 1022 */       if (status) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1032 */         boolean defaultStatus = false;
/*      */         
/* 1034 */         defaultStatus = (user.getPreferences().getReportsStatusClosed() == 1);
/* 1035 */         FormCheckBox Status_closed = new FormCheckBox("status_closed", "Closed", false, defaultStatus);
/* 1036 */         Status_closed.addFormEvent("onClick", "hidePrintButtons('printWindow','');clickStatus(this);");
/* 1037 */         Status_closed.setTabIndex(9);
/* 1038 */         reportForm.addElement(Status_closed);
/*      */         
/* 1040 */         defaultStatus = (user.getPreferences().getReportsStatusActive() == 1);
/* 1041 */         FormCheckBox Status_active = new FormCheckBox("status_active", "Active", false, defaultStatus);
/* 1042 */         Status_active.addFormEvent("onClick", "hidePrintButtons('printWindow','');clickStatus(this);");
/* 1043 */         Status_active.setTabIndex(10);
/* 1044 */         reportForm.addElement(Status_active);
/*      */         
/* 1046 */         defaultStatus = (user.getPreferences().getReportsStatusAll() == 1);
/* 1047 */         FormCheckBox Status_all = new FormCheckBox("status_all", "All", false, defaultStatus);
/* 1048 */         Status_all.addFormEvent("onClick", "hidePrintButtons('printWindow','');clickStatus(this)");
/* 1049 */         Status_all.setTabIndex(12);
/* 1050 */         reportForm.addElement(Status_all);
/*      */         
/* 1052 */         defaultStatus = (user.getPreferences().getReportsStatusTBS() == 1);
/* 1053 */         FormCheckBox Status_tbs = new FormCheckBox("status_tbs", "TBS", false, defaultStatus);
/* 1054 */         Status_tbs.addFormEvent("onClick", "hidePrintButtons('printWindow','');clickStatus(this);");
/* 1055 */         Status_tbs.setTabIndex(13);
/* 1056 */         reportForm.addElement(Status_tbs);
/*      */         
/* 1058 */         defaultStatus = (user.getPreferences().getReportsStatusCancelled() == 1);
/* 1059 */         FormCheckBox Status_cancelled = new FormCheckBox("status_cancelled", "Cancelled", false, defaultStatus);
/* 1060 */         Status_cancelled.addFormEvent("onClick", "hidePrintButtons('printWindow','');clickStatus(this);");
/* 1061 */         Status_cancelled.setTabIndex(15);
/* 1062 */         reportForm.addElement(Status_cancelled);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1067 */       boolean contact = false;
/* 1068 */       contact = report.getContactFlag();
/*      */       
/* 1070 */       if (contact) {
/*      */ 
/*      */ 
/*      */         
/* 1074 */         Vector contactVector = ReportConfigManager.getLabelContacts();
/* 1075 */         String contactList = "All,";
/* 1076 */         String idList = "0,";
/*      */         
/* 1078 */         if (contactVector != null) {
/*      */           
/* 1080 */           for (int i = 0; i < contactVector.size(); i++)
/*      */           {
/* 1082 */             User userContact = (User)contactVector.get(i);
/* 1083 */             if (i < contactVector.size() - 1)
/*      */             {
/* 1085 */               if (userContact != null)
/*      */               {
/* 1087 */                 contactList = String.valueOf(contactList) + userContact.getName() + ",";
/* 1088 */                 idList = String.valueOf(idList) + userContact.getUserId() + ",";
/*      */               
/*      */               }
/*      */             
/*      */             }
/* 1093 */             else if (userContact != null)
/*      */             {
/* 1095 */               contactList = String.valueOf(contactList) + userContact.getName();
/* 1096 */               idList = String.valueOf(idList) + userContact.getUserId();
/*      */             }
/*      */           
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/* 1103 */           contactList = "All";
/* 1104 */           idList = "0";
/*      */         } 
/*      */ 
/*      */         
/* 1108 */         int defaultLabelContact = 0;
/*      */         
/* 1110 */         if (user.getPreferences().getReportsLabelContact() > 0) {
/* 1111 */           defaultLabelContact = user.getPreferences().getReportsLabelContact();
/*      */         }
/* 1113 */         FormDropDownMenu ContactList = new FormDropDownMenu("contact", String.valueOf(defaultLabelContact), idList, contactList, false);
/*      */         
/* 1115 */         ContactList.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 1116 */         ContactList.setTabIndex(10);
/* 1117 */         reportForm.addElement(ContactList);
/*      */       } 
/*      */ 
/*      */       
/* 1121 */       boolean umlContact = false;
/* 1122 */       umlContact = report.getFuture1Flag();
/*      */       
/* 1124 */       if (umlContact) {
/*      */ 
/*      */         
/* 1127 */         Vector contactVector1 = ReportConfigManager.getUmlContacts();
/* 1128 */         String contactList1 = "All,";
/* 1129 */         String idList = "0,";
/*      */         
/* 1131 */         if (contactVector1 != null) {
/*      */           
/* 1133 */           for (int i = 0; i < contactVector1.size(); i++)
/*      */           {
/* 1135 */             User userContact1 = (User)contactVector1.get(i);
/* 1136 */             if (i < contactVector1.size() - 1)
/*      */             {
/* 1138 */               if (userContact1 != null)
/*      */               {
/* 1140 */                 contactList1 = String.valueOf(contactList1) + userContact1.getName() + ",";
/* 1141 */                 idList = String.valueOf(idList) + userContact1.getUserId() + ",";
/*      */               
/*      */               }
/*      */             
/*      */             }
/* 1146 */             else if (userContact1 != null)
/*      */             {
/* 1148 */               contactList1 = String.valueOf(contactList1) + userContact1.getName();
/* 1149 */               idList = String.valueOf(idList) + userContact1.getUserId();
/*      */             }
/*      */           
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/* 1156 */           contactList1 = "All";
/* 1157 */           idList = "0";
/*      */         } 
/*      */ 
/*      */         
/* 1161 */         int defaultContact = 0;
/*      */         
/* 1163 */         if (user.getPreferences().getReportsUMLContact() > 0) {
/* 1164 */           defaultContact = user.getPreferences().getReportsUMLContact();
/*      */         }
/* 1166 */         FormDropDownMenu ContactList1 = new FormDropDownMenu("umlContact", String.valueOf(defaultContact), idList, contactList1, false);
/*      */         
/* 1168 */         ContactList1.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 1169 */         ContactList1.setTabIndex(11);
/* 1170 */         reportForm.addElement(ContactList1);
/*      */       } 
/*      */ 
/*      */       
/* 1174 */       boolean completeKeyTask = false;
/* 1175 */       completeKeyTask = report.getCompleteKeyTaskFlag();
/*      */       
/* 1177 */       if (completeKeyTask) {
/*      */         
/* 1179 */         FormCheckBox KeyDatesOnly = new FormCheckBox("keyDatesOnly", "", false, false);
/* 1180 */         KeyDatesOnly.setTabIndex(12);
/* 1181 */         reportForm.addElement(KeyDatesOnly);
/*      */         
/* 1183 */         FormCheckBox CompleteKeyTask = new FormCheckBox("completeKeyTask", "", false, false);
/* 1184 */         CompleteKeyTask.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 1185 */         CompleteKeyTask.setTabIndex(13);
/* 1186 */         reportForm.addElement(CompleteKeyTask);
/*      */       } 
/*      */ 
/*      */       
/* 1190 */       boolean taskOwner = false;
/* 1191 */       taskOwner = report.getUmlDatesFlag();
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
/* 1203 */       boolean umlKeyTask = false;
/* 1204 */       umlKeyTask = report.getUmlKeyTaskFlag();
/*      */       
/* 1206 */       if (umlKeyTask) {
/*      */         
/* 1208 */         FormRadioButtonGroup UmlKeyTask = new FormRadioButtonGroup("umlKeyTask", "All", "UML, Label, All", false);
/* 1209 */         UmlKeyTask.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 1210 */         UmlKeyTask.setTabIndex(12);
/* 1211 */         reportForm.addElement(UmlKeyTask);
/*      */       
/*      */       }
/* 1214 */       else if (report.getReportName().trim().equalsIgnoreCase("New Rel.")) {
/* 1215 */         report.setUmlKeyTaskFlag(true);
/* 1216 */         FormRadioButtonGroup UmlKeyTask = new FormRadioButtonGroup("umlKeyTask", "UML", "UML", false);
/* 1217 */         reportForm.addElement(UmlKeyTask);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1224 */       boolean parentsOnly = false;
/* 1225 */       parentsOnly = report.getParentsOnlyFlag();
/*      */       
/* 1227 */       if (parentsOnly) {
/*      */         
/* 1229 */         FormCheckBox ParentsOnly = new FormCheckBox("ParentsOnly", "", false);
/* 1230 */         ParentsOnly.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 1231 */         ParentsOnly.setTabIndex(18);
/* 1232 */         reportForm.addElement(ParentsOnly);
/*      */       } 
/*      */ 
/*      */       
/* 1236 */       boolean artist = false;
/* 1237 */       artist = report.getArtistFlag();
/*      */       
/* 1239 */       if (artist) {
/*      */ 
/*      */         
/* 1242 */         FormTextField Artist = new FormTextField("artist", "", false, 34, 34);
/* 1243 */         Artist.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 1244 */         Artist.setTabIndex(16);
/* 1245 */         reportForm.addElement(Artist);
/*      */       } 
/*      */ 
/*      */       
/* 1249 */       boolean future2 = false;
/* 1250 */       future2 = report.getFuture2Flag();
/*      */       
/* 1252 */       if (future2) {
/*      */         
/* 1254 */         FormCheckBox Future2 = new FormCheckBox("Future2", "", false, false);
/* 1255 */         Future2.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 1256 */         Future2.setTabIndex(19);
/* 1257 */         reportForm.addElement(Future2);
/*      */       } 
/*      */ 
/*      */       
/* 1261 */       boolean family = false;
/* 1262 */       family = report.getFamilyFlag();
/*      */       
/* 1264 */       if (family) {
/*      */ 
/*      */         
/* 1267 */         int defaultFamily = -1;
/*      */         
/* 1269 */         if (user.getPreferences().getReportsReleasingFamily() > 0) {
/* 1270 */           defaultFamily = user.getPreferences().getReportsReleasingFamily();
/*      */         }
/*      */ 
/*      */         
/* 1274 */         Vector families = SelectionHandler.filterCSO(ReleasingFamily.getUserReleasingFamiliesVectorOfFamilies(context));
/*      */         
/* 1276 */         MilestoneFormDropDownMenu Family = getCorporateStructureDropDownMultSel("family", families, String.valueOf(defaultFamily), false, true);
/* 1277 */         Family.addFormEvent("onChange", "return(clickFamily(this))");
/* 1278 */         Family.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 1279 */         Family.setTabIndex(20);
/* 1280 */         Family.setMultiple(true);
/* 1281 */         reportForm.addElement(Family);
/*      */         
/* 1283 */         String hiddenFamilyIndex = "0";
/* 1284 */         if (context.getRequest().getParameter("hiddenFamilyIndex") != null)
/* 1285 */           hiddenFamilyIndex = context.getRequest().getParameter("hiddenFamilyIndex"); 
/* 1286 */         reportForm.addElement(new FormHidden("hiddenFamilyIndex", hiddenFamilyIndex, false));
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1291 */       boolean environment = false;
/* 1292 */       environment = report.getEnvironmentFlag();
/*      */       
/* 1294 */       if (environment) {
/*      */         
/* 1296 */         Vector environments = MilestoneHelper.getUserEnvironments(context);
/* 1297 */         Vector companies = MilestoneHelper.getUserCompanies(context);
/*      */ 
/*      */ 
/*      */         
/* 1301 */         environments = SelectionHandler.filterSelectionEnvironments(companies);
/*      */ 
/*      */         
/* 1304 */         companies = MilestoneHelper.removeUnusedCSO(companies, context, -1);
/*      */ 
/*      */         
/* 1307 */         int defaultEnv = -1;
/* 1308 */         if (user.getPreferences().getReportsEnvironment() > 0) {
/* 1309 */           defaultEnv = user.getPreferences().getReportsEnvironment();
/*      */         }
/*      */ 
/*      */         
/* 1313 */         MilestoneFormDropDownMenu envMenu = getCorporateStructureDropDownMultSel("environment", SelectionHandler.filterSelectionEnvironments(companies), String.valueOf(defaultEnv), false, true);
/* 1314 */         envMenu.setMultiple(true);
/*      */ 
/*      */ 
/*      */         
/* 1318 */         envMenu.addFormEvent("onChange", "return(clickEnvironment(this))");
/* 1319 */         envMenu.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 1320 */         envMenu.setTabIndex(21);
/* 1321 */         reportForm.addElement(envMenu);
/* 1322 */         String hiddenEnvironmentIndex = "0";
/* 1323 */         if (context.getRequest().getParameter("hiddenEnvironmentIndex") != null)
/* 1324 */           hiddenEnvironmentIndex = context.getRequest().getParameter("hiddenEnvironmentIndex"); 
/* 1325 */         reportForm.addElement(new FormHidden("hiddenEnvironmentIndex", hiddenEnvironmentIndex, false));
/*      */       } 
/*      */ 
/*      */       
/* 1329 */       boolean company = false;
/* 1330 */       company = report.getCompanyFlag();
/*      */       
/* 1332 */       if (company) {
/*      */         
/* 1334 */         Vector companies = MilestoneHelper.getUserCompanies(context);
/*      */ 
/*      */ 
/*      */         
/* 1338 */         companies = SelectionHandler.filterSelectionCompanies(companies);
/*      */ 
/*      */         
/* 1341 */         companies = MilestoneHelper.removeUnusedCSO(companies, context, -1);
/*      */ 
/*      */         
/* 1344 */         MilestoneFormDropDownMenu Company = getCorporateStructureDropDownMultSel("company", SelectionHandler.filterSelectionCompanies(companies), "-1", false, true);
/* 1345 */         Company.setMultiple(true);
/* 1346 */         Company.addFormEvent("onChange", "return(clickCompany(this))");
/* 1347 */         Company.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 1348 */         Company.setTabIndex(22);
/* 1349 */         reportForm.addElement(Company);
/*      */         
/* 1351 */         String hiddenCompany = "0";
/* 1352 */         if (context.getRequest().getParameter("hiddenCompany") != null)
/* 1353 */           hiddenCompany = context.getRequest().getParameter("hiddenCompany"); 
/* 1354 */         reportForm.addElement(new FormHidden("hiddenCompany", hiddenCompany, false));
/* 1355 */         String hiddenCompanyIndex = "0";
/* 1356 */         if (context.getRequest().getParameter("hiddenCompanyIndex") != null)
/* 1357 */           hiddenCompanyIndex = context.getRequest().getParameter("hiddenCompanyIndex"); 
/* 1358 */         reportForm.addElement(new FormHidden("hiddenCompanyIndex", hiddenCompanyIndex, false));
/*      */       } 
/*      */ 
/*      */       
/* 1362 */       boolean label = false;
/* 1363 */       label = report.getLabelFlag();
/* 1364 */       if (label) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1369 */         Vector companies = MilestoneHelper.getUserCompanies(context);
/* 1370 */         Vector labels = MilestoneHelper.getUserLabels(companies);
/*      */ 
/*      */         
/* 1373 */         labels = MilestoneHelper.removeUnusedCSO(labels, context, -1);
/*      */         
/* 1375 */         MilestoneFormDropDownMenu Label = getCorporateStructureDropDownDuplicates("Label", labels, "", false, true, false);
/* 1376 */         Label.setId("Label");
/* 1377 */         Label.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 1378 */         Label.setTabIndex(22);
/* 1379 */         Label.setMultiple(true);
/* 1380 */         reportForm.addElement(Label);
/*      */         
/* 1382 */         String hiddenLabel = "0";
/* 1383 */         if (context.getRequest().getParameter("hiddenLabel") != null)
/* 1384 */           hiddenLabel = context.getRequest().getParameter("hiddenLabel"); 
/* 1385 */         reportForm.addElement(new FormHidden("hiddenLabel", hiddenLabel, false));
/* 1386 */         String hiddenLabelIndex = "0";
/* 1387 */         if (context.getRequest().getParameter("hiddenLabelIndex") != null)
/* 1388 */           hiddenLabelIndex = context.getRequest().getParameter("hiddenLabelIndex"); 
/* 1389 */         reportForm.addElement(new FormHidden("hiddenLabelIndex", hiddenLabelIndex, false));
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
/* 1403 */       boolean endEffectiveDate = false;
/* 1404 */       endEffectiveDate = report.getEndEffectiveDateFlag();
/*      */       
/* 1406 */       if (endEffectiveDate) {
/*      */ 
/*      */         
/* 1409 */         FormTextField EndEffectiveDate = new FormTextField("endEffectiveDate", "", true, 10);
/* 1410 */         EndEffectiveDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/*      */         
/* 1412 */         EndEffectiveDate.addFormEvent("onBlur", "JavaScript:removeSpacesInDate(document.forms[0].endEffectiveDate.value,this);if (!checkField(this))this.focus();");
/* 1413 */         EndEffectiveDate.addFormEvent("onClick", "Javascript:hidePrintButtons('printWindow','');");
/* 1414 */         EndEffectiveDate.setTabIndex(6);
/* 1415 */         reportForm.addElement(EndEffectiveDate);
/*      */       } 
/*      */ 
/*      */       
/* 1419 */       boolean beginEffectiveDate = false;
/*      */       
/* 1421 */       beginEffectiveDate = report.getBeginEffectiveDateFlag();
/*      */       
/* 1423 */       if (beginEffectiveDate) {
/*      */ 
/*      */         
/* 1426 */         FormTextField BeginEffectiveDate = new FormTextField("beginEffectiveDate", "", true, 10);
/* 1427 */         BeginEffectiveDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
/*      */         
/* 1429 */         BeginEffectiveDate.addFormEvent("onBlur", "JavaScript:removeSpacesInDate(document.forms[0].beginEffectiveDate.value,this);if (!checkField(this))this.focus();");
/* 1430 */         BeginEffectiveDate.addFormEvent("onClick", "Javascript:hidePrintButtons('printWindow','');");
/* 1431 */         BeginEffectiveDate.setTabIndex(5);
/* 1432 */         reportForm.addElement(BeginEffectiveDate);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1437 */       boolean configuration = false;
/* 1438 */       configuration = report.getConfiguration();
/* 1439 */       if (configuration) {
/*      */         MilestoneFormDropDownMenu ConfigList;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1445 */         if (report.getProductType() == 2) {
/* 1446 */           ConfigList = new MilestoneFormDropDownMenu(MilestoneHelper.getSelectionConfigurationDropDown("configurationList", "", false));
/*      */         } else {
/* 1448 */           ConfigList = new MilestoneFormDropDownMenu(MilestoneHelper.getSelectionConfigurationDropDown("configurationList", "", false, report.getProductType()));
/*      */         } 
/* 1450 */         ConfigList.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 1451 */         ConfigList.addFormEvent("onChange", "getSubconfigs(this.selectedIndex);");
/* 1452 */         String[] values = ConfigList.getValueList();
/* 1453 */         String[] menuText = ConfigList.getMenuTextList();
/*      */         
/* 1455 */         values[0] = "All";
/* 1456 */         menuText[0] = "All";
/* 1457 */         ConfigList.setValueList(values);
/* 1458 */         ConfigList.setMenuTextList(menuText);
/* 1459 */         ConfigList.setTabIndex(23);
/* 1460 */         ConfigList.setMultiple(true);
/* 1461 */         ConfigList.addFormEvent("SIZE", "5");
/* 1462 */         reportForm.addElement(ConfigList);
/*      */         
/* 1464 */         String hiddenFormatIndex = "0";
/* 1465 */         if (context.getRequest().getParameter("hiddenFormatIndex") != null)
/* 1466 */           hiddenFormatIndex = context.getRequest().getParameter("hiddenFormatIndex"); 
/* 1467 */         reportForm.addElement(new FormHidden("hiddenFormatIndex", hiddenFormatIndex, false));
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1474 */       boolean subconfiguration = false;
/* 1475 */       subconfiguration = report.getSubconfigFlag();
/* 1476 */       if (subconfiguration) {
/*      */ 
/*      */         
/* 1479 */         MilestoneFormDropDownMenu SubconfigList = new MilestoneFormDropDownMenu("subconfigurationList", "All");
/*      */         
/* 1481 */         SubconfigList.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1489 */         SubconfigList.setTabIndex(23);
/* 1490 */         SubconfigList.setMultiple(true);
/* 1491 */         SubconfigList.addFormEvent("SIZE", "5");
/* 1492 */         reportForm.addElement(SubconfigList);
/*      */         
/* 1494 */         String hiddenSubFormatIndex = "0";
/* 1495 */         if (context.getRequest().getParameter("hiddenSubFormatIndex") != null)
/* 1496 */           hiddenSubFormatIndex = context.getRequest().getParameter("hiddenFormatIndex"); 
/* 1497 */         reportForm.addElement(new FormHidden("hiddenSubFormatIndex", hiddenSubFormatIndex, false));
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1502 */       boolean scheduledReleases = false;
/* 1503 */       scheduledReleases = report.getScheduledReleasesFlag();
/*      */       
/* 1505 */       if (scheduledReleases) {
/*      */         
/* 1507 */         FormCheckBox ScheduledReleases = new FormCheckBox("ScheduledReleases", "", false);
/* 1508 */         ScheduledReleases.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 1509 */         ScheduledReleases.setTabIndex(17);
/* 1510 */         reportForm.addElement(ScheduledReleases);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1516 */       boolean addsMovesBoth = false;
/* 1517 */       addsMovesBoth = report.getAddsMovesBoth();
/*      */       
/* 1519 */       if (addsMovesBoth) {
/*      */         
/* 1521 */         FormRadioButtonGroup addsMoves = new FormRadioButtonGroup("AddsMovesBoth", "Both", "Adds,Moves,Both", false);
/* 1522 */         addsMoves.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 1523 */         addsMoves.setTabIndex(18);
/* 1524 */         reportForm.addElement(addsMoves);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1529 */       boolean distCoBoo = false;
/* 1530 */       distCoBoo = report.getDistCo();
/*      */       
/* 1532 */       if (distCoBoo) {
/* 1533 */         Hashtable distCoHash = MilestoneHelper_2.getDistCoNames();
/* 1534 */         Enumeration distCo = distCoHash.keys();
/* 1535 */         Vector distIDs = new Vector();
/* 1536 */         while (distCo.hasMoreElements()) {
/* 1537 */           distIDs.add((Integer)distCo.nextElement());
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 1542 */         for (int distCoCounter = 0; distCoCounter < distIDs.size(); distCoCounter++) {
/* 1543 */           Integer discoIdInt = (Integer)distIDs.elementAt(distCoCounter);
/* 1544 */           int discoId = discoIdInt.intValue();
/* 1545 */           String checkBoxID = "distCo" + Integer.toString(discoId);
/* 1546 */           String checkBoxValue = (String)distCoHash.get(discoIdInt);
/* 1547 */           FormCheckBox DistCo = new FormCheckBox(checkBoxID, checkBoxValue, false, false);
/* 1548 */           DistCo.addFormEvent("onClick", "hidePrintButtons('printWindow','');clickDistCo(this);");
/* 1549 */           reportForm.addElement(DistCo);
/*      */         } 
/*      */ 
/*      */         
/* 1553 */         FormCheckBox DistCo = new FormCheckBox("distCo0", "All", false, true);
/* 1554 */         DistCo.addFormEvent("onClick", "hidePrintButtons('printWindow','');clickDistCo(this);");
/* 1555 */         reportForm.addElement(DistCo);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1561 */       boolean physicalProductActivity = false;
/* 1562 */       physicalProductActivity = report.getPhysicalProductActivity();
/* 1563 */       if (physicalProductActivity) {
/*      */         
/* 1565 */         FormCheckBox AllActivity = new FormCheckBox("AllActivity", "All", false, false);
/* 1566 */         AllActivity.setChecked(true);
/* 1567 */         AllActivity.addFormEvent("onClick", "productActivitySelected(this);");
/* 1568 */         AllActivity.setTabIndex(12);
/* 1569 */         AllActivity.setId("AllActivity");
/* 1570 */         reportForm.addElement(AllActivity);
/*      */         
/* 1572 */         FormCheckBox ChangesActivity = new FormCheckBox("ChangesActivity", "Changed", false);
/* 1573 */         ChangesActivity.setChecked(true);
/* 1574 */         ChangesActivity.addFormEvent("onClick", "productActivitySelected(this);");
/* 1575 */         ChangesActivity.setTabIndex(12);
/* 1576 */         ChangesActivity.setId("ChangesActivity");
/* 1577 */         reportForm.addElement(ChangesActivity);
/*      */         
/* 1579 */         FormCheckBox CancelsActivity = new FormCheckBox("CancelsActivity", "Cancelled", false);
/* 1580 */         CancelsActivity.setChecked(true);
/* 1581 */         CancelsActivity.addFormEvent("onClick", "productActivitySelected(this);");
/* 1582 */         CancelsActivity.setTabIndex(12);
/* 1583 */         CancelsActivity.setId("CancelsActivity");
/* 1584 */         reportForm.addElement(CancelsActivity);
/*      */         
/* 1586 */         FormCheckBox DeletesActivity = new FormCheckBox("DeletesActivity", "Deleted", false);
/* 1587 */         DeletesActivity.setChecked(true);
/* 1588 */         DeletesActivity.addFormEvent("onClick", "productActivitySelected(this);");
/* 1589 */         DeletesActivity.setTabIndex(12);
/* 1590 */         DeletesActivity.setId("DeletesActivity");
/* 1591 */         reportForm.addElement(DeletesActivity);
/*      */         
/* 1593 */         FormCheckBox AddsActivity = new FormCheckBox("AddsActivity", "Added", false);
/* 1594 */         AddsActivity.setChecked(true);
/* 1595 */         AddsActivity.addFormEvent("onClick", "productActivitySelected(this);");
/* 1596 */         AddsActivity.setTabIndex(12);
/* 1597 */         AddsActivity.setId("AddsActivity");
/* 1598 */         reportForm.addElement(AddsActivity);
/*      */ 
/*      */         
/* 1601 */         FormCheckBox MovesActivity = new FormCheckBox("MovesActivity", "Moved", false);
/* 1602 */         MovesActivity.setChecked(true);
/* 1603 */         MovesActivity.addFormEvent("onClick", "productActivitySelected(this);");
/* 1604 */         MovesActivity.setTabIndex(13);
/* 1605 */         MovesActivity.setId("MovesActivity");
/* 1606 */         reportForm.addElement(MovesActivity);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1614 */       int productType = 0;
/* 1615 */       productType = report.getProductType();
/*      */       
/* 1617 */       if (productType == 2) {
/*      */         FormRadioButtonGroup ProductType;
/* 1619 */         String reportName = report.getReportName();
/*      */         
/* 1621 */         if (reportName.equalsIgnoreCase("IDJProdAlt")) {
/* 1622 */           ProductType = new FormRadioButtonGroup("ProductType", "Both", "Physical,Digital,Both", false);
/*      */         } else {
/* 1624 */           ProductType = new FormRadioButtonGroup("ProductType", "Physical", "Physical,Digital,Both", false);
/*      */         } 
/* 1626 */         ProductType.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 1627 */         ProductType.setTabIndex(19);
/* 1628 */         reportForm.addElement(ProductType);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1634 */       String reportName = report.getReportName();
/*      */       
/* 1636 */       if (reportName.equalsIgnoreCase("eInitRel")) {
/*      */ 
/*      */         
/* 1639 */         FormDropDownMenu eInitRelList = new FormDropDownMenu("eInitRelList", "Artist", "0,1,2", "Artist,Volume,ORG Release Date", false);
/* 1640 */         eInitRelList.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 1641 */         eInitRelList.setTabIndex(24);
/* 1642 */         reportForm.addElement(eInitRelList);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1651 */       if (reportName.equalsIgnoreCase("FutDueDate")) {
/*      */ 
/*      */ 
/*      */         
/* 1655 */         Vector templates = ReportConfigManager.getTemplateNames(context);
/*      */         
/* 1657 */         String templatesList = "All";
/* 1658 */         String idList = "0";
/*      */         
/* 1660 */         if (templates != null)
/*      */         {
/* 1662 */           for (int i = 0; i < templates.size(); i++) {
/*      */             
/* 1664 */             Template template = (Template)templates.get(i);
/* 1665 */             templatesList = String.valueOf(templatesList) + "," + template.getTempateName();
/* 1666 */             idList = String.valueOf(idList) + "," + template.getTemplateID();
/*      */           } 
/*      */         }
/*      */ 
/*      */         
/* 1671 */         MilestoneFormDropDownMenu deadLineList = new MilestoneFormDropDownMenu("templatesList", "0", idList, templatesList, false);
/* 1672 */         deadLineList.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
/* 1673 */         deadLineList.setTabIndex(25);
/* 1674 */         deadLineList.setMultiple(true);
/* 1675 */         deadLineList.addFormEvent("SIZE", "10");
/*      */         
/* 1677 */         reportForm.addElement(deadLineList);
/*      */ 
/*      */ 
/*      */         
/* 1681 */         context.putSessionValue("templatesAll", templates);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1688 */     if (context.getSessionValue("NOTEPAD_REPORTS_VISIBLE") != null) {
/* 1689 */       context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_REPORTS_VISIBLE"));
/*      */     }
/* 1691 */     return reportForm;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean summary(Context context) {
/* 1700 */     Report report = MilestoneHelper.getScreenReport(context);
/* 1701 */     context.putSessionValue("report", report);
/*      */     
/* 1703 */     return context.includeJSP("report-summary.jsp");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean criteria(Context context, Dispatcher dispatcher) {
/* 1712 */     Report report = MilestoneHelper.getScreenReport(context);
/*      */     
/* 1714 */     Form reportForm = buildForm(context, report);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1785 */     reportForm.setValues(context);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1791 */     ReportSelectionsHelper.setReportHiddenValues("family", "hiddenFamilyIndex", context, reportForm);
/* 1792 */     ReportSelectionsHelper.setReportHiddenValues("environment", "hiddenEnvironmentIndex", context, reportForm);
/* 1793 */     ReportSelectionsHelper.setReportHiddenValues("company", "hiddenCompanyIndex", context, reportForm);
/* 1794 */     ReportSelectionsHelper.setReportHiddenValues("Label", "hiddenLabelIndex", context, reportForm);
/* 1795 */     ReportSelectionsHelper.setReportHiddenValues("configurationList", "hiddenFormatIndex", context, reportForm);
/* 1796 */     ReportSelectionsHelper.setReportHiddenValues("subconfigurationList", "hiddenSubFormatIndex", context, reportForm);
/*      */     
/* 1798 */     context.putDelivery("Form", reportForm);
/* 1799 */     context.putSessionValue("report", report);
/* 1800 */     context.putSessionValue("reportForm", reportForm);
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
/* 1814 */     ie55 = true;
/* 1815 */     print(context, 0, true, dispatcher);
/* 1816 */     return true;
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
/* 1830 */     StringBuffer result = new StringBuffer(100);
/* 1831 */     String str = "";
/* 1832 */     String value = new String();
/*      */ 
/*      */     
/* 1835 */     this.corpHashMap = MilestoneHelper.buildActiveCorporateStructureHashMap();
/*      */ 
/*      */     
/* 1838 */     User user = (User)context.getSessionValue("user");
/* 1839 */     Vector vUserCompanies = SelectionHandler.filterSelectionActiveCompanies(user.getUserId(), MilestoneHelper.getUserCompanies(context));
/* 1840 */     Vector vUserEnvironments = SelectionHandler.filterSelectionEnvironments(vUserCompanies);
/*      */ 
/*      */     
/* 1843 */     result.append("\n");
/* 1844 */     result.append("var familyArray = new Array();\n");
/* 1845 */     result.append("var environmentArray = new Array();\n");
/* 1846 */     result.append("var companyArray = new Array();\n");
/* 1847 */     int arrayIndex = 0;
/*      */ 
/*      */     
/* 1850 */     result.append("familyArray[0] = new Array( 0, 'All');\n");
/* 1851 */     result.append("environmentArray[0] = new Array( 0, 'All');\n");
/*      */     
/* 1853 */     Vector vUserFamilies = SelectionHandler.filterCSO(MilestoneHelper.getNonSecureActiveUserFamilies(context));
/*      */     
/* 1855 */     for (int l = 0; l < vUserFamilies.size(); l++) {
/*      */       
/* 1857 */       Family family = (Family)vUserFamilies.elementAt(l);
/* 1858 */       int structureId = family.getStructureID();
/*      */ 
/*      */ 
/*      */       
/* 1862 */       boolean familyVal = true;
/* 1863 */       if (this.corpHashMap.containsKey(new Integer(structureId))) {
/* 1864 */         familyVal = false;
/*      */       }
/*      */       
/* 1867 */       if (family != null && familyVal) {
/*      */         
/* 1869 */         result.append("familyArray[");
/* 1870 */         result.append(family.getStructureID());
/* 1871 */         result.append("] = new Array(");
/*      */         
/* 1873 */         boolean foundZeroth = false;
/*      */ 
/*      */         
/* 1876 */         Vector environmentVector = new Vector();
/* 1877 */         Vector environments = getUserEnvironmentsFromFamily(family, context);
/*      */         
/* 1879 */         if (environments != null) {
/*      */           
/* 1881 */           result.append(" 0, 'All',");
/* 1882 */           for (int j = 0; j < environments.size(); j++) {
/*      */             
/* 1884 */             Environment environment = (Environment)environments.elementAt(j);
/* 1885 */             int structureIdc = environment.getStructureID();
/*      */             
/* 1887 */             boolean boolVal = true;
/* 1888 */             if (this.corpHashMap.containsKey(new Integer(structureIdc))) {
/* 1889 */               boolVal = false;
/*      */             }
/* 1891 */             if (environment.getParentID() == family.getStructureID() && boolVal) {
/*      */               
/* 1893 */               if (foundZeroth) {
/* 1894 */                 result.append(',');
/*      */               }
/* 1896 */               result.append(' ');
/* 1897 */               result.append(environment.getStructureID());
/* 1898 */               result.append(", '");
/* 1899 */               result.append(MilestoneHelper.urlEncode(environment.getName()));
/* 1900 */               result.append('\'');
/* 1901 */               foundZeroth = true;
/* 1902 */               environmentVector.addElement(environment);
/*      */             } 
/*      */           } 
/* 1905 */           if (foundZeroth) {
/*      */             
/* 1907 */             result.append(");\n");
/*      */           }
/*      */           else {
/*      */             
/* 1911 */             result.append(" 0, 'All');\n");
/*      */           } 
/*      */           
/* 1914 */           for (int k = 0; k < environmentVector.size(); k++) {
/* 1915 */             Environment environmentNode = (Environment)environmentVector.elementAt(k);
/* 1916 */             result.append("environmentArray[");
/* 1917 */             result.append(environmentNode.getStructureID());
/* 1918 */             result.append("] = new Array(");
/* 1919 */             Vector companyVector = new Vector();
/* 1920 */             Vector companies = environmentNode.getChildren();
/*      */             
/* 1922 */             if (companies != null) {
/* 1923 */               result.append(" 0, 'All',");
/*      */               
/* 1925 */               boolean foundZeroth2 = false;
/*      */               
/* 1927 */               for (int m = 0; m < companies.size(); m++) {
/*      */                 
/* 1929 */                 Company company = (Company)companies.elementAt(m);
/* 1930 */                 int structureIdc = company.getStructureID();
/*      */ 
/*      */                 
/* 1933 */                 boolean boolVal = true;
/* 1934 */                 if (this.corpHashMap.containsKey(new Integer(structureIdc))) {
/* 1935 */                   boolVal = false;
/*      */                 }
/*      */                 
/* 1938 */                 if (company.getParentID() == environmentNode.getStructureID() && boolVal) {
/*      */                   
/* 1940 */                   if (foundZeroth2)
/* 1941 */                     result.append(','); 
/* 1942 */                   result.append(' ');
/* 1943 */                   result.append(company.getStructureID());
/* 1944 */                   result.append(", '");
/* 1945 */                   result.append(MilestoneHelper.urlEncode(company.getName()));
/* 1946 */                   result.append('\'');
/* 1947 */                   foundZeroth2 = true;
/* 1948 */                   companyVector.addElement(company);
/*      */                 } 
/*      */               } 
/*      */               
/* 1952 */               if (foundZeroth2) {
/*      */                 
/* 1954 */                 result.append(");\n");
/*      */               }
/*      */               else {
/*      */                 
/* 1958 */                 result.append(" 0, 'All');\n");
/*      */               } 
/*      */               
/* 1961 */               for (int n = 0; n < companyVector.size(); n++) {
/*      */                 
/* 1963 */                 Company companyNode = (Company)companyVector.elementAt(n);
/* 1964 */                 result.append("companyArray[");
/* 1965 */                 result.append(companyNode.getStructureID());
/* 1966 */                 result.append("] = new Array(");
/*      */                 
/* 1968 */                 Vector divisions = companyNode.getChildren();
/*      */                 
/* 1970 */                 boolean foundSecond2 = false;
/*      */                 
/* 1972 */                 result.append(" 0, 'All',");
/*      */                 
/* 1974 */                 for (int x = 0; x < divisions.size(); x++) {
/*      */                   
/* 1976 */                   Division division = (Division)divisions.elementAt(x);
/* 1977 */                   int structureIds = division.getStructureID();
/*      */ 
/*      */ 
/*      */                   
/* 1981 */                   boolean boolRes = true;
/* 1982 */                   if (this.corpHashMap.containsKey(new Integer(structureIds))) {
/* 1983 */                     boolRes = false;
/*      */                   }
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1989 */                   if (division != null && boolRes) {
/*      */ 
/*      */                     
/* 1992 */                     Vector labels = division.getChildren();
/*      */                     
/* 1994 */                     for (int y = 0; y < labels.size(); y++) {
/*      */                       
/* 1996 */                       Label labelNode = (Label)labels.get(y);
/* 1997 */                       int structureIdl = labelNode.getStructureID();
/*      */ 
/*      */ 
/*      */                       
/* 2001 */                       boolean boolVal = true;
/* 2002 */                       if (this.corpHashMap.containsKey(new Integer(structureIdl))) {
/* 2003 */                         boolVal = false;
/*      */                       }
/*      */                       
/* 2006 */                       if (labelNode.getParentID() == division.getStructureID() && boolVal) {
/*      */ 
/*      */                         
/* 2009 */                         if (foundSecond2)
/* 2010 */                           result.append(','); 
/* 2011 */                         result.append(' ');
/* 2012 */                         result.append(labelNode.getStructureID());
/* 2013 */                         result.append(", '");
/* 2014 */                         result.append(MilestoneHelper.urlEncode(labelNode.getName()));
/* 2015 */                         result.append('\'');
/* 2016 */                         foundSecond2 = true;
/*      */                       } 
/*      */                     } 
/*      */                   } 
/*      */                 } 
/*      */ 
/*      */ 
/*      */                 
/* 2024 */                 if (foundSecond2) {
/*      */                   
/* 2026 */                   result.append(");\n");
/*      */                 }
/*      */                 else {
/*      */                   
/* 2030 */                   result.append(" 0, 'All');\n");
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 2039 */     this.corpHashMap = null;
/* 2040 */     return result.toString();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Vector getUserCompaniesFromFamily(Family family, Context context) {
/* 2206 */     Vector userCompanies = MilestoneHelper.getUserCompanies(context);
/* 2207 */     Vector result = new Vector();
/*      */     
/* 2209 */     if (family != null) {
/*      */ 
/*      */       
/* 2212 */       Vector familyCompanies = family.getChildren();
/*      */       
/* 2214 */       if (familyCompanies != null)
/*      */       {
/* 2216 */         for (int i = 0; i < familyCompanies.size(); i++) {
/*      */           
/* 2218 */           Company familyCompany = (Company)familyCompanies.get(i);
/*      */ 
/*      */ 
/*      */           
/* 2222 */           for (int j = 0; j < userCompanies.size(); j++) {
/*      */             
/* 2224 */             Company userCompany = (Company)userCompanies.get(j);
/* 2225 */             int structureId = userCompany.getStructureID();
/*      */ 
/*      */             
/* 2228 */             boolean resultStructure = true;
/* 2229 */             if (this.corpHashMap.containsKey(new Integer(structureId))) {
/* 2230 */               resultStructure = false;
/*      */             }
/*      */             
/* 2233 */             if (userCompany.getStructureID() == familyCompany.getStructureID() && resultStructure) {
/* 2234 */               result.add(familyCompany);
/*      */             }
/*      */           } 
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/* 2241 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private Vector getUserEnvironmentsFromFamily(Family family, Context context) {
/* 2247 */     Vector userEnvironments = MilestoneHelper.getUserEnvironments(context);
/* 2248 */     Vector result = new Vector();
/*      */     
/* 2250 */     if (family != null) {
/*      */ 
/*      */       
/* 2253 */       Vector familyEnvironments = family.getChildren();
/*      */       
/* 2255 */       if (familyEnvironments != null)
/*      */       {
/*      */         
/* 2258 */         for (int i = 0; i < familyEnvironments.size(); i++) {
/*      */ 
/*      */           
/* 2261 */           Environment familyEnvironment = (Environment)familyEnvironments.get(i);
/*      */ 
/*      */           
/* 2264 */           for (int j = 0; j < userEnvironments.size(); j++) {
/*      */             
/* 2266 */             Environment userEnvironment = (Environment)userEnvironments.get(j);
/* 2267 */             int structureId = userEnvironment.getStructureID();
/*      */             
/* 2269 */             boolean resultStructure = true;
/* 2270 */             if (this.corpHashMap.containsKey(new Integer(structureId))) {
/* 2271 */               resultStructure = false;
/*      */             }
/*      */             
/* 2274 */             if (userEnvironment.getStructureID() == familyEnvironment.getStructureID() && resultStructure) {
/* 2275 */               result.add(familyEnvironment);
/*      */             }
/*      */           } 
/*      */         } 
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 2283 */     return result;
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
/*      */   protected int insertConfigHeader(DefaultTableLens table_contents, String title, int nextRow, int cols) {
/* 2296 */     int COL_LINE_STYLE = 4097;
/* 2297 */     int HEADER_FONT_SIZE = 12;
/*      */     
/* 2299 */     table_contents.setObject(nextRow, 0, "");
/* 2300 */     table_contents.setSpan(nextRow, 0, new Dimension(cols, 1));
/* 2301 */     table_contents.setRowHeight(nextRow, 1);
/* 2302 */     table_contents.setRowBackground(nextRow, Color.white);
/*      */     
/* 2304 */     for (int col = -1; col < cols; col++) {
/*      */       
/* 2306 */       table_contents.setColBorderColor(nextRow, col, Color.white);
/* 2307 */       table_contents.setColBorder(nextRow, col, 4097);
/*      */     } 
/*      */     
/* 2310 */     table_contents.setRowBorderColor(nextRow, Color.white);
/* 2311 */     table_contents.setRowBorder(nextRow, 266240);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2316 */     table_contents.setColBorderColor(nextRow + 1, -1, Color.black);
/* 2317 */     table_contents.setColBorder(nextRow + 1, -1, 4097);
/* 2318 */     table_contents.setColBorderColor(nextRow + 1, cols - 1, Color.black);
/* 2319 */     table_contents.setColBorder(nextRow + 1, cols - 1, 4097);
/*      */     
/* 2321 */     table_contents.setRowBorderColor(nextRow + 1, Color.black);
/* 2322 */     table_contents.setRowBorder(nextRow + 1, 266240);
/*      */ 
/*      */     
/* 2325 */     table_contents.setAlignment(nextRow + 1, 0, 2);
/* 2326 */     table_contents.setSpan(nextRow + 1, 0, new Dimension(cols, 1));
/* 2327 */     table_contents.setObject(nextRow + 1, 0, title);
/* 2328 */     table_contents.setRowFont(nextRow + 1, new Font("Arial", 3, 12));
/*      */     
/* 2330 */     nextRow += 2;
/*      */     
/* 2332 */     table_contents.setObject(nextRow, 0, "");
/* 2333 */     table_contents.setSpan(nextRow, 0, new Dimension(cols, 1));
/* 2334 */     table_contents.setRowHeight(nextRow, 1);
/* 2335 */     table_contents.setRowBackground(nextRow, Color.white);
/*      */     
/* 2337 */     for (int col = -1; col < cols; col++) {
/*      */       
/* 2339 */       table_contents.setColBorderColor(nextRow, col, Color.black);
/* 2340 */       table_contents.setColBorder(nextRow, col, 4097);
/*      */     } 
/*      */     
/* 2343 */     table_contents.setRowBorderColor(nextRow, Color.white);
/* 2344 */     table_contents.setRowBorder(nextRow, 266240);
/*      */     
/* 2346 */     nextRow++;
/*      */     
/* 2348 */     return nextRow;
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
/*      */   protected static int insertLightGrayHeader(DefaultTableLens table_contents, String title, int nextRow, int cols) {
/* 2360 */     int COL_LINE_STYLE = 4097;
/* 2361 */     int HEADER_FONT_SIZE = 10;
/*      */     
/* 2363 */     table_contents.setObject(nextRow, 0, "");
/* 2364 */     table_contents.setSpan(nextRow, 0, new Dimension(cols, 1));
/* 2365 */     table_contents.setRowHeight(nextRow, 8);
/* 2366 */     table_contents.setRowBackground(nextRow, Color.white);
/*      */     
/* 2368 */     for (int col = -1; col < cols; col++) {
/*      */       
/* 2370 */       table_contents.setColBorderColor(nextRow, col, Color.white);
/* 2371 */       table_contents.setColBorder(nextRow, col, 4097);
/*      */     } 
/* 2373 */     table_contents.setRowBorderColor(nextRow, Color.white);
/*      */     
/* 2375 */     nextRow++;
/*      */     
/* 2377 */     table_contents.setRowBorderColor(nextRow, Color.white);
/* 2378 */     table_contents.setRowBorder(nextRow, 266240);
/*      */     
/* 2380 */     table_contents.setObject(nextRow, 0, "");
/* 2381 */     table_contents.setSpan(nextRow, 0, new Dimension(cols, 1));
/* 2382 */     table_contents.setRowHeight(nextRow, 1);
/*      */     
/* 2384 */     for (int col = -1; col < cols; col++) {
/*      */       
/* 2386 */       table_contents.setColBorderColor(nextRow, col, Color.white);
/* 2387 */       table_contents.setColBorder(nextRow, col, 4097);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2393 */     table_contents.setColBorderColor(nextRow + 1, -1, Color.white);
/* 2394 */     table_contents.setColBorder(nextRow + 1, -1, 4097);
/* 2395 */     table_contents.setColBorderColor(nextRow + 1, cols - 1, Color.white);
/* 2396 */     table_contents.setColBorder(nextRow + 1, cols - 1, 4097);
/*      */     
/* 2398 */     table_contents.setRowBorderColor(nextRow + 1, Color.lightGray);
/* 2399 */     table_contents.setRowBorder(nextRow + 1, 266240);
/*      */ 
/*      */     
/* 2402 */     table_contents.setAlignment(nextRow + 1, 0, 1);
/* 2403 */     table_contents.setSpan(nextRow + 1, 0, new Dimension(cols, 1));
/* 2404 */     table_contents.setObject(nextRow + 1, 0, title);
/* 2405 */     table_contents.setRowFont(nextRow + 1, new Font("Arial", 1, 10));
/* 2406 */     table_contents.setRowBackground(nextRow + 1, Color.lightGray);
/* 2407 */     table_contents.setRowForeground(nextRow + 1, Color.black);
/*      */     
/* 2409 */     nextRow += 2;
/*      */     
/* 2411 */     table_contents.setObject(nextRow, 0, "");
/* 2412 */     table_contents.setSpan(nextRow, 0, new Dimension(cols, 1));
/* 2413 */     table_contents.setRowHeight(nextRow, 1);
/* 2414 */     table_contents.setRowBackground(nextRow, Color.white);
/*      */     
/* 2416 */     for (int col = -1; col < cols; col++) {
/*      */       
/* 2418 */       table_contents.setColBorderColor(nextRow, col, Color.white);
/* 2419 */       table_contents.setColBorder(nextRow, col, 4097);
/*      */     } 
/*      */     
/* 2422 */     table_contents.setRowBorderColor(nextRow, Color.white);
/* 2423 */     table_contents.setRowBorder(nextRow, 266240);
/*      */     
/* 2425 */     nextRow++;
/*      */     
/* 2427 */     return nextRow;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendReport(Context context) {
/*      */     try {
/* 2436 */       HttpServletResponse sresponse = context.getResponse();
/*      */       
/* 2438 */       Calendar cal = Calendar.getInstance();
/* 2439 */       cal.add(5, -5);
/*      */       
/* 2441 */       SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy hh:mm:ss 'GMT'");
/* 2442 */       String currentDate = formatter.format(cal.getTime());
/*      */       
/* 2444 */       sresponse.setHeader("Last-Modified", currentDate);
/*      */       
/* 2446 */       cal.add(5, -15);
/* 2447 */       String expireDate = formatter.format(cal.getTime());
/*      */       
/* 2449 */       sresponse.setHeader("Expires", expireDate);
/*      */       
/* 2451 */       String reportName = (context.getSessionValue("currentReportName") != null) ? (String)context.getSessionValue("currentReportName") : "";
/*      */       
/* 2453 */       if (reportName.equalsIgnoreCase("Prod.Stat."))
/*      */       {
/*      */ 
/*      */         
/* 2457 */         StyleSheet report = (StyleSheet)context.getSessionValue("currentReport");
/*      */         
/* 2459 */         String reportFilename = "report.pdf";
/*      */         
/* 2461 */         if (report != null)
/*      */         {
/*      */ 
/*      */           
/* 2465 */           if (ie55) {
/*      */             
/* 2467 */             sresponse.setHeader("extension", "pdf");
/* 2468 */             sresponse.setContentType("application/pdf");
/* 2469 */             sresponse.setHeader("Content-disposition", "attachment; filename=" + reportFilename);
/*      */           }
/*      */           else {
/*      */             
/* 2473 */             sresponse.setHeader("extension", "pdf");
/* 2474 */             sresponse.setContentType("application/force-download");
/* 2475 */             sresponse.setHeader("Content-disposition", "attachment; filename=" + reportFilename);
/*      */           } 
/*      */           
/* 2478 */           ServletOutputStream servletOutputStream = sresponse.getOutputStream();
/* 2479 */           servletOutputStream.flush();
/*      */           
/* 2481 */           PDF4Generator pdfGenerator = new PDF4Generator(servletOutputStream);
/*      */           
/* 2483 */           pdfGenerator.generate(report);
/* 2484 */           servletOutputStream.close();
/*      */         }
/*      */         else
/*      */         {
/* 2488 */           downloadDone(context);
/*      */         }
/*      */       
/*      */       }
/* 2492 */       else if (reportName.equalsIgnoreCase("TaskDueDt"))
/*      */       {
/*      */ 
/*      */         
/* 2496 */         StyleSheet report = (StyleSheet)context.getSessionValue("currentReport");
/*      */         
/* 2498 */         String reportFilename = "report.pdf";
/*      */         
/* 2500 */         if (report != null)
/*      */         {
/*      */ 
/*      */           
/* 2504 */           if (ie55) {
/*      */             
/* 2506 */             sresponse.setHeader("extension", "pdf");
/* 2507 */             sresponse.setContentType("application/pdf");
/* 2508 */             sresponse.setHeader("Content-disposition", "attachment; filename=" + reportFilename);
/*      */           }
/*      */           else {
/*      */             
/* 2512 */             sresponse.setHeader("extension", "pdf");
/* 2513 */             sresponse.setContentType("application/force-download");
/* 2514 */             sresponse.setHeader("Content-disposition", "attachment; filename=" + reportFilename);
/*      */           } 
/*      */           
/* 2517 */           ServletOutputStream servletOutputStream = sresponse.getOutputStream();
/* 2518 */           servletOutputStream.flush();
/*      */           
/* 2520 */           PDF4Generator pdfGenerator = new PDF4Generator(servletOutputStream);
/*      */           
/* 2522 */           pdfGenerator.generate(report);
/* 2523 */           servletOutputStream.close();
/*      */         }
/*      */         else
/*      */         {
/* 2527 */           downloadDone(context);
/*      */         }
/*      */       
/*      */       }
/* 2531 */       else if (reportName.equalsIgnoreCase("TaskDueTit"))
/*      */       {
/*      */ 
/*      */         
/* 2535 */         StyleSheet report = (StyleSheet)context.getSessionValue("currentReport");
/*      */         
/* 2537 */         String reportFilename = "report.pdf";
/*      */         
/* 2539 */         if (report != null)
/*      */         {
/*      */ 
/*      */           
/* 2543 */           if (ie55) {
/*      */             
/* 2545 */             sresponse.setHeader("extension", "pdf");
/* 2546 */             sresponse.setContentType("application/pdf");
/* 2547 */             sresponse.setHeader("Content-disposition", "attachment; filename=" + reportFilename);
/*      */           }
/*      */           else {
/*      */             
/* 2551 */             sresponse.setHeader("extension", "pdf");
/* 2552 */             sresponse.setContentType("application/force-download");
/* 2553 */             sresponse.setHeader("Content-disposition", "attachment; filename=" + reportFilename);
/*      */           } 
/*      */           
/* 2556 */           ServletOutputStream servletOutputStream = sresponse.getOutputStream();
/* 2557 */           servletOutputStream.flush();
/*      */           
/* 2559 */           PDF4Generator pdfGenerator = new PDF4Generator(servletOutputStream);
/* 2560 */           pdfGenerator.generate(report);
/* 2561 */           servletOutputStream.close();
/*      */         }
/*      */         else
/*      */         {
/* 2565 */           downloadDone(context);
/*      */         }
/*      */       
/*      */       }
/* 2569 */       else if (reportName.equalsIgnoreCase("MCARelSchd"))
/*      */       {
/*      */ 
/*      */         
/* 2573 */         StyleSheet report = (StyleSheet)context.getSessionValue("currentReport");
/*      */         
/* 2575 */         String reportFilename = "report.pdf";
/*      */         
/* 2577 */         if (report != null)
/*      */         {
/*      */ 
/*      */           
/* 2581 */           if (ie55) {
/*      */             
/* 2583 */             sresponse.setHeader("extension", "pdf");
/* 2584 */             sresponse.setContentType("application/pdf");
/* 2585 */             sresponse.setHeader("Content-disposition", "attachment; filename=" + reportFilename);
/*      */           }
/*      */           else {
/*      */             
/* 2589 */             sresponse.setHeader("extension", "pdf");
/* 2590 */             sresponse.setContentType("application/force-download");
/* 2591 */             sresponse.setHeader("Content-disposition", "attachment; filename=" + reportFilename);
/*      */           } 
/*      */           
/* 2594 */           ServletOutputStream servletOutputStream = sresponse.getOutputStream();
/* 2595 */           servletOutputStream.flush();
/*      */           
/* 2597 */           PDF4Generator pdfGenerator = new PDF4Generator(servletOutputStream);
/*      */           
/* 2599 */           pdfGenerator.generate(report);
/* 2600 */           servletOutputStream.close();
/*      */         }
/*      */         else
/*      */         {
/* 2604 */           downloadDone(context);
/*      */         
/*      */         }
/*      */       
/*      */       }
/*      */       else
/*      */       {
/* 2611 */         XStyleSheet report = (XStyleSheet)context.getSessionValue("currentReport");
/* 2612 */         String reportFilename = "report.pdf";
/*      */         
/* 2614 */         if (report != null)
/*      */         {
/*      */ 
/*      */           
/* 2618 */           if (ie55) {
/*      */             
/* 2620 */             sresponse.setHeader("extension", "pdf");
/* 2621 */             sresponse.setContentType("application/pdf");
/* 2622 */             sresponse.setHeader("Content-disposition", "attachment; filename=" + reportFilename);
/*      */           }
/*      */           else {
/*      */             
/* 2626 */             sresponse.setHeader("extension", "pdf");
/* 2627 */             sresponse.setContentType("application/pdf");
/* 2628 */             sresponse.setHeader("Content-disposition", "attachment; filename=" + reportFilename);
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 2633 */           ServletOutputStream servletOutputStream = sresponse.getOutputStream();
/* 2634 */           servletOutputStream.flush();
/*      */           
/* 2636 */           PDF4Generator pdfGenerator = new PDF4Generator(servletOutputStream);
/*      */           
/* 2638 */           if (reportName.equalsIgnoreCase("MexProdSch")) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 2643 */             Size a4Landscape = new Size(11.75D, 8.25D);
/* 2644 */             pdfGenerator.setPageSize(a4Landscape);
/*      */           } 
/*      */           
/* 2647 */           pdfGenerator.generate(report);
/*      */           
/* 2649 */           servletOutputStream.close();
/*      */         
/*      */         }
/*      */         else
/*      */         {
/* 2654 */           downloadDone(context);
/*      */         }
/*      */       
/*      */       }
/*      */     
/* 2659 */     } catch (Exception exception) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void downloadDone(Context context) {
/*      */     try {
/* 2669 */       HttpServletResponse sresponse = context.getResponse();
/* 2670 */       context.putDelivery("status", new String("done"));
/* 2671 */       if (!ReportingServices.usingReportServicesByContext(context))
/*      */       {
/* 2673 */         context.includeJSP("status.jsp", "hiddenFrame");
/* 2674 */         sresponse.setContentType("text/plain");
/* 2675 */         sresponse.flushBuffer();
/*      */       }
/*      */     
/* 2678 */     } catch (Exception exception) {}
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
/*      */   private boolean search(Dispatcher dispatcher, Context context, String command) {
/* 2690 */     Notepad notepad = MilestoneHelper.getNotepadFromSession(3, context);
/* 2691 */     notepad.setAllContents(null);
/* 2692 */     notepad.setSelected(null);
/*      */ 
/*      */     
/* 2695 */     ReportManager.getInstance().setNotepadQuery(context, MilestoneSecurity.getUser(context).getUserId(), notepad);
/* 2696 */     dispatcher.redispatch(context, "reports-editor");
/*      */     
/* 2698 */     return true;
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
/*      */   private boolean sort(Dispatcher dispatcher, Context context) {
/* 2711 */     Notepad notepad = MilestoneHelper.getNotepadFromSession(3, context);
/*      */     
/* 2713 */     if (notepad.getSearchQuery() == null || notepad.getSearchQuery().equals("")) {
/* 2714 */       ReportManager.getInstance(); notepad.setSearchQuery("SELECT DISTINCT report_id, report_name, file_name, description, report_owner FROM vi_Report_Config where report_status = 'ACTIVE' ");
/*      */     } 
/* 2716 */     if (context.getParameter("OrderByDirection").equals("0")) {
/* 2717 */       notepad.setOrderBy(" ORDER BY " + context.getParameter("OrderBy") + " ASC");
/*      */     } else {
/* 2719 */       notepad.setOrderBy(" ORDER BY " + context.getParameter("OrderBy") + " DESC");
/*      */     } 
/*      */ 
/*      */     
/* 2723 */     notepad.setAllContents(null);
/* 2724 */     Vector contents = new Vector();
/*      */ 
/*      */     
/* 2727 */     notepad = getReportsNotepad(contents, context, MilestoneSecurity.getUser(context).getUserId());
/* 2728 */     notepad.setSelected(notepad.getSelected());
/*      */ 
/*      */     
/* 2731 */     dispatcher.redispatch(context, "reports-editor");
/*      */     
/* 2733 */     return true;
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
/*      */   
/*      */   public static MilestoneFormDropDownMenu getCorporateStructureDropDownMultSel(String name, Vector corporateVector, String selectedOption, boolean required, boolean blankFirst) {
/* 2761 */     Vector values = new Vector();
/* 2762 */     Vector menuText = new Vector();
/*      */     
/* 2764 */     MilestoneFormDropDownMenu dropdown = null;
/* 2765 */     boolean selectedFound = false;
/*      */ 
/*      */     
/* 2768 */     HashMap corpHashMap = MilestoneHelper.buildActiveCorporateStructureHashMap();
/*      */ 
/*      */ 
/*      */     
/* 2772 */     int selectionInt = 0;
/*      */     try {
/* 2774 */       selectionInt = Integer.parseInt(selectedOption);
/*      */     }
/* 2776 */     catch (NumberFormatException numberFormatException) {}
/*      */ 
/*      */ 
/*      */     
/* 2780 */     if (corporateVector != null) {
/*      */ 
/*      */ 
/*      */       
/* 2784 */       Vector sortedVector = new Vector();
/* 2785 */       sortedVector = MilestoneHelper.sortCorporateVectorByName(corporateVector);
/*      */ 
/*      */       
/* 2788 */       if (blankFirst) {
/* 2789 */         if (name.equalsIgnoreCase("family")) {
/* 2790 */           values.addElement("0");
/* 2791 */           menuText.addElement("All");
/*      */         } else {
/*      */           
/* 2794 */           values.addElement("0");
/*      */           
/* 2796 */           menuText.addElement(" All");
/*      */         } 
/*      */       }
/*      */       
/* 2800 */       CorporateStructureObject cso = null;
/*      */       
/* 2802 */       for (int i = 0; i < sortedVector.size(); i++) {
/* 2803 */         cso = (CorporateStructureObject)sortedVector.elementAt(i);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2808 */         if (cso != null && !corpHashMap.containsKey(new Integer(cso.getStructureID()))) {
/*      */ 
/*      */           
/* 2811 */           values.addElement(cso.getStructureID());
/* 2812 */           menuText.addElement(cso.getName());
/*      */           
/* 2814 */           if (cso.getStructureID() == selectionInt) {
/* 2815 */             selectedFound = true;
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2823 */       if (!selectedFound && selectedOption != null) {
/* 2824 */         CorporateStructureObject csoSelected = 
/* 2825 */           (CorporateStructureObject)MilestoneHelper.getStructureObject(selectionInt);
/*      */         
/* 2827 */         if (csoSelected != null) {
/* 2828 */           values.addElement(csoSelected.getStructureID());
/* 2829 */           menuText.addElement(csoSelected.getName());
/*      */         } 
/*      */       } 
/*      */       
/* 2833 */       String[] arrayValues = new String[values.size()];
/* 2834 */       String[] arrayMenuText = new String[menuText.size()];
/*      */ 
/*      */       
/* 2837 */       arrayValues = (String[])values.toArray(arrayValues);
/* 2838 */       arrayMenuText = (String[])menuText.toArray(arrayMenuText);
/*      */       
/* 2840 */       dropdown = new MilestoneFormDropDownMenu(name, 
/* 2841 */           selectedOption, 
/* 2842 */           arrayValues, 
/* 2843 */           arrayMenuText, 
/* 2844 */           required);
/*      */     } else {
/*      */       
/* 2847 */       dropdown = new MilestoneFormDropDownMenu(name, "", "", required);
/*      */     } 
/*      */     
/* 2850 */     corpHashMap = null;
/*      */     
/* 2852 */     return dropdown;
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
/*      */   public static MilestoneFormDropDownMenu getCorporateStructureDropDownDuplicates(String name, Vector corporateVector, String selectedOption, boolean required, boolean blankFirst, boolean activesOnly) {
/* 2879 */     Vector values = new Vector();
/*      */     
/* 2881 */     MilestoneVector menuText = new MilestoneVector();
/*      */     
/* 2883 */     MilestoneFormDropDownMenu dropdown = null;
/* 2884 */     boolean selectedFound = false;
/*      */ 
/*      */     
/* 2887 */     HashMap corpHashMap = MilestoneHelper.buildActiveCorporateStructureHashMap();
/*      */ 
/*      */ 
/*      */     
/* 2891 */     int selectionInt = 0;
/*      */     
/*      */     try {
/* 2894 */       selectionInt = Integer.parseInt(selectedOption);
/*      */     }
/* 2896 */     catch (NumberFormatException numberFormatException) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2901 */     if (corporateVector != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2906 */       Vector sortedVector = new Vector();
/* 2907 */       sortedVector = MilestoneHelper.sortCorporateVectorByName(corporateVector);
/*      */ 
/*      */       
/* 2910 */       if (blankFirst)
/*      */       {
/* 2912 */         if (name.equalsIgnoreCase("family")) {
/*      */           
/* 2914 */           values.addElement("0");
/* 2915 */           menuText.addElement("All");
/*      */         }
/*      */         else {
/*      */           
/* 2919 */           values.addElement("0");
/*      */           
/* 2921 */           menuText.addElement(" All");
/*      */         } 
/*      */       }
/*      */       
/* 2925 */       CorporateStructureObject cso = null;
/*      */       
/* 2927 */       for (int i = 0; i < sortedVector.size(); i++) {
/*      */         
/* 2929 */         cso = (CorporateStructureObject)sortedVector.elementAt(i);
/*      */ 
/*      */         
/* 2932 */         if (activesOnly) {
/*      */ 
/*      */           
/* 2935 */           if (cso instanceof Label) {
/* 2936 */             Label csoLabel = (Label)cso;
/* 2937 */             if (!csoLabel.getActive()) {
/*      */               continue;
/*      */             }
/*      */           } 
/* 2941 */           if (cso instanceof Division) {
/* 2942 */             Division csoDiv = (Division)cso;
/* 2943 */             if (!csoDiv.getActive()) {
/*      */               continue;
/*      */             }
/*      */           } 
/* 2947 */           if (cso instanceof Company) {
/* 2948 */             Company csoCo = (Company)cso;
/* 2949 */             if (!csoCo.getActive()) {
/*      */               continue;
/*      */             }
/*      */           } 
/* 2953 */           if (cso instanceof Environment) {
/* 2954 */             Environment csoEnv = (Environment)cso;
/* 2955 */             if (!csoEnv.getActive()) {
/*      */               continue;
/*      */             }
/*      */           } 
/* 2959 */           if (cso instanceof Family) {
/* 2960 */             Family csoFam = (Family)cso;
/* 2961 */             if (!csoFam.getActive()) {
/*      */               continue;
/*      */             }
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 2968 */         if (cso != null && !corpHashMap.containsKey(new Integer(cso.getStructureID())))
/*      */         {
/*      */ 
/*      */ 
/*      */           
/* 2973 */           if (!menuText.contains(cso.getName())) {
/*      */             
/* 2975 */             values.addElement(cso.getStructureID());
/* 2976 */             menuText.addElement(cso.getName());
/*      */             
/* 2978 */             if (cso.getStructureID() == selectionInt) {
/* 2979 */               selectedFound = true;
/*      */             }
/*      */           }
/*      */           else {
/*      */             
/* 2984 */             int index = menuText.indexOf(cso.getName());
/* 2985 */             if (index != -1) {
/*      */               
/* 2987 */               String value = (String)values.get(index);
/* 2988 */               values.set(index, String.valueOf(value) + "," + cso.getStructureID());
/*      */               
/* 2990 */               if (cso.getStructureID() == selectionInt) {
/* 2991 */                 selectedFound = true;
/*      */               }
/*      */             } 
/*      */           } 
/*      */         }
/*      */ 
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/* 3001 */       if (!selectedFound && selectedOption != null) {
/*      */         
/* 3003 */         CorporateStructureObject csoSelected = 
/* 3004 */           (CorporateStructureObject)MilestoneHelper.getStructureObject(selectionInt);
/*      */         
/* 3006 */         if (csoSelected != null) {
/*      */           
/* 3008 */           values.addElement(csoSelected.getStructureID());
/* 3009 */           menuText.addElement(csoSelected.getName());
/*      */         } 
/*      */       } 
/*      */       
/* 3013 */       String[] arrayValues = new String[values.size()];
/* 3014 */       String[] arrayMenuText = new String[menuText.size()];
/*      */ 
/*      */       
/* 3017 */       arrayValues = (String[])values.toArray(arrayValues);
/* 3018 */       arrayMenuText = (String[])menuText.toArray(arrayMenuText);
/*      */       
/* 3020 */       dropdown = new MilestoneFormDropDownMenu(name, 
/* 3021 */           selectedOption, 
/* 3022 */           arrayValues, 
/* 3023 */           arrayMenuText, 
/* 3024 */           required);
/*      */     }
/*      */     else {
/*      */       
/* 3028 */       dropdown = new MilestoneFormDropDownMenu(name, "", "", required);
/*      */     } 
/*      */     
/* 3031 */     corpHashMap = null;
/*      */     
/* 3033 */     return dropdown;
/*      */   }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\ReportHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */