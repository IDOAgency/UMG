package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.EnhancedProperties;
import com.techempower.gemini.Context;
import com.techempower.gemini.Dispatcher;
import com.techempower.gemini.FormCheckBox;
import com.techempower.gemini.FormDropDownMenu;
import com.techempower.gemini.FormHidden;
import com.techempower.gemini.FormRadioButtonGroup;
import com.techempower.gemini.FormTextField;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.AddsAndMovesForPrintSubHandler;
import com.universal.milestone.Cache;
import com.universal.milestone.CapitolProductionSchedulePrintSubHandler;
import com.universal.milestone.CarolineProductionSchedulePrintSubHandler;
import com.universal.milestone.ClassicsProductionScheduleForPrintSubHandler;
import com.universal.milestone.ClassicsRelSchdForPrintSubHandler;
import com.universal.milestone.Company;
import com.universal.milestone.CorporateStructureObject;
import com.universal.milestone.DigitalProductionLabelScheduleForPrintSubHandler;
import com.universal.milestone.DigitalProductionScheduleForPrintSubHandler;
import com.universal.milestone.Division;
import com.universal.milestone.DreamWorksPromoScheduleForPrintSubHandler;
import com.universal.milestone.DreamWorksRecordScheduleForPrintSubHandler;
import com.universal.milestone.DreamWorksRelScheduleForPrintSubHandler;
import com.universal.milestone.EntCommRelScheduleForPrintSubHandler;
import com.universal.milestone.EntPastRelAlbumsForPrintSubHandler;
import com.universal.milestone.EntPastRelOrphansForPrintSubHandler;
import com.universal.milestone.EntProdStatusForPrintSubHandler;
import com.universal.milestone.EntRelScheduleForPrintSubHandler;
import com.universal.milestone.EntRelWithoutScheduleForPrintSubHandler;
import com.universal.milestone.EntReleaseSchedByLabelSubHandler;
import com.universal.milestone.Environment;
import com.universal.milestone.Family;
import com.universal.milestone.Form;
import com.universal.milestone.FutureTaskDueDatesByTemplateSubHandler;
import com.universal.milestone.IdjProductionScheduleForPrintSubHandlerAlternate;
import com.universal.milestone.IdjProductionScheduleForPrintSubHandlerNew2091;
import com.universal.milestone.IgaProductionScheduleUpdateForPrintSubHandler;
import com.universal.milestone.Label;
import com.universal.milestone.LatinoProductionScheduleUpdateForPrintSubHandler;
import com.universal.milestone.MCACustomImpactScheduleForPrintSubHandler;
import com.universal.milestone.MCACustomReleaseForPrintSubHandler;
import com.universal.milestone.MCANewReleasePlanningSchedulePrintSubHandler;
import com.universal.milestone.MCAProductionScheduleForPrintSubHandler;
import com.universal.milestone.MexicoScheduleForPrintSubHandler;
import com.universal.milestone.MilestoneFormDropDownMenu;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneHelper_2;
import com.universal.milestone.MilestoneSecurity;
import com.universal.milestone.MilestoneVector;
import com.universal.milestone.NashProdScheduleForPrintSubHandler;
import com.universal.milestone.NashRelScheduleForPrintSubHandler;
import com.universal.milestone.Notepad;
import com.universal.milestone.PastDueReleasesForPrintSubHandler;
import com.universal.milestone.PhysicalProductActivitySummaryForPrintSubHandler;
import com.universal.milestone.PressPlayProductionScheduleForPrintSubHandler;
import com.universal.milestone.ProductionGroupCode;
import com.universal.milestone.ReleasingFamily;
import com.universal.milestone.Report;
import com.universal.milestone.ReportConfigManager;
import com.universal.milestone.ReportHandler;
import com.universal.milestone.ReportManager;
import com.universal.milestone.ReportSelectionsHelper;
import com.universal.milestone.ReportingServices;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.SelectionHandler;
import com.universal.milestone.SelectionManager;
import com.universal.milestone.TaskDueDateByTitleForPrintHandler;
import com.universal.milestone.TaskDueDateForPrintSubHandler;
import com.universal.milestone.Template;
import com.universal.milestone.ToDoListReportForPrintSubHandler;
import com.universal.milestone.UmeCustomProdScheduleForPrintSubHandler;
import com.universal.milestone.UmeProdScheduleForPrintSubHandler;
import com.universal.milestone.UmlAddsMovesReportSubHandler;
import com.universal.milestone.UmlNewReleaseMasterScheduleMetaSubHandler;
import com.universal.milestone.UmlNewReleaseMasterScheduleSubHandler;
import com.universal.milestone.UmlProductionScheduleForPrintSubHandler;
import com.universal.milestone.User;
import com.universal.milestone.VerveCommercialReleaseScheduleForPrintSubHandler;
import com.universal.milestone.VerveScheduleUpdateForPrintSubHandler;
import com.universal.milestone.eInitiativeRelScheduleForPrintSubHandler;
import inetsoft.report.ReportEnv;
import inetsoft.report.Size;
import inetsoft.report.StyleSheet;
import inetsoft.report.XStyleSheet;
import inetsoft.report.io.Builder;
import inetsoft.report.lens.DefaultTableLens;
import inetsoft.report.pdf.PDF4Generator;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

public class ReportHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hRep";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public static String reportPath = "";
  
  public static boolean ie55 = false;
  
  public HashMap corpHashMap;
  
  public ReportHandler(GeminiApplication application) {
    this.corpHashMap = null;
    this.application = application;
    this.log = application.getLog("hRep");
  }
  
  public String getDescription() { return "Report"; }
  
  public static void configure(EnhancedProperties props, GeminiApplication application) { reportPath = props.getProperty("ReportPath", ""); }
  
  public boolean acceptRequest(Dispatcher dispatcher, Context context, String command) {
    if (super.acceptRequest(dispatcher, context, command))
      if (command.startsWith("reports"))
        return handleRequest(dispatcher, context, command);  
    return false;
  }
  
  public boolean handleRequest(Dispatcher dispatcher, Context context, String command) {
    if (command.equalsIgnoreCase("reports-editor")) {
      edit(context);
    } else if (command.equalsIgnoreCase("reports-standard")) {
      standard(context);
    } else if (command.equalsIgnoreCase("reports-print")) {
      ie55 = true;
      print(context, 0, true, dispatcher);
    } else if (command.equalsIgnoreCase("reports-save")) {
      save(context);
    } else if (command.equalsIgnoreCase("reports-search")) {
      search(dispatcher, context, command);
    } 
    if (command.equalsIgnoreCase("reports-sort")) {
      sort(dispatcher, context);
    } else if (command.equalsIgnoreCase("reports-criteria")) {
      criteria(context, dispatcher);
    } else if (command.equalsIgnoreCase("reports-summary")) {
      summary(context);
    } else if (command.equalsIgnoreCase("reports-print-rtf")) {
      print(context, 1, true, dispatcher);
    } else if (command.equalsIgnoreCase("reports-print-pdf4")) {
      ie55 = false;
      print(context, 0, false, dispatcher);
    } else if (command.equalsIgnoreCase("reports-print-rtf4")) {
      print(context, 1, false, dispatcher);
    } else if (command.equalsIgnoreCase("reports-print-download")) {
      if (!ReportingServices.usingReportServicesByContext(context))
        sendReport(context); 
    } else if (command.equalsIgnoreCase("reports-print-download-done")) {
      downloadDone(context);
    } 
    return true;
  }
  
  public boolean edit(Context context) {
    SelectionManager.getInstance().setSelectionNotepadUserDefaults(context);
    User user = MilestoneSecurity.getUser(context);
    Vector contents = new Vector();
    Notepad notepad = getReportsNotepad(contents, context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Report report = MilestoneHelper.getScreenReport(context);
    if (report != null) {
      Form reportForm = buildForm(context, report);
      context.putDelivery("corporate-array", ReleasingFamily.getJavaScriptCorporateArrayReleasingFamily(context));
      context.putDelivery("configArrays", String.valueOf(Cache.getJavaScriptConfigArray("")) + " " + Cache.getJavaScriptSubConfigArray(""));
      ReportSelectionsHelper.deliverMultSelectValuesForRefresh("family", reportForm, context);
      ReportSelectionsHelper.deliverMultSelectValuesForRefresh("environment", reportForm, context);
      ReportSelectionsHelper.deliverMultSelectValuesForRefresh("company", reportForm, context);
      ReportSelectionsHelper.deliverMultSelectValuesForRefresh("Label", reportForm, context);
      ReportSelectionsHelper.deliverMultSelectValuesForRefresh("configuration", reportForm, context);
      ReportSelectionsHelper.deliverMultSelectValuesForRefresh("subconfiguration", reportForm, context);
      context.putDelivery("Form", reportForm);
      context.putSessionValue("report", report);
      return context.includeJSP("reports-editor.jsp");
    } 
    return goToBlank(context);
  }
  
  private boolean goToBlank(Context context) {
    context.putDelivery("isNotePadVisible", new Boolean(MilestoneHelper.getNotePadVisiblitiy(3, context)));
    Form form = new Form(this.application, "form", this.application.getInfrastructure().getServletURL(), "Post");
    form.addElement(new FormHidden("cmd", "reports-editor"));
    form.addElement(new FormHidden("OrderBy", "", true));
    FormTextField ReportDescriptionSearch = new FormTextField("ReportDescriptionSearch", "", false, 30, 30);
    ReportDescriptionSearch.setId("ReportDescriptionSearch");
    form.addElement(ReportDescriptionSearch);
    form.addElement(new FormHidden("OrderBy", "", true));
    if (context.getRequest().getParameter("OrderByDirection") != null) {
      form.addElement(new FormHidden("OrderByDirection", context.getRequest().getParameter("OrderByDirection"), true));
    } else {
      form.addElement(new FormHidden("OrderByDirection", "0", true));
    } 
    context.putDelivery("Form", form);
    return context.includeJSP("blank-reports-editor.jsp");
  }
  
  public void standard(Context context) { context.includeJSP("reports-standard.jsp"); }
  
  public void print(Context context, int pdfRtf, boolean ie55, Dispatcher dispatcher) {
    ReportEnv.setProperty("license_key", "E76B-566-ERX-F3601DCC7ABF");
    context.removeSessionValue("currentReport");
    Report rep = (Report)context.getSessionValue("Report");
    if (rep != null) {
      String repName = rep.getReportName();
      if (repName == null)
        repName = ""; 
      String reportTemplate = null;
      ReportingServices.getReportServices(repName);
      System.out.println("--- After getReportServices - repName = " + repName + " --- ");
      if (repName.equalsIgnoreCase("Com Rel")) {
        reportTemplate = String.valueOf(reportPath) + "\\ent_com_release_sched.xml";
      } else if (repName.equalsIgnoreCase("eInitRel")) {
        reportTemplate = String.valueOf(reportPath) + "\\eInitiative_release_sched.xml";
      } else if (repName.equalsIgnoreCase("Rel.Shed.")) {
        reportTemplate = String.valueOf(reportPath) + "\\ent_release_sched.xml";
      } else if (repName.equalsIgnoreCase("ToDoList")) {
        reportTemplate = String.valueOf(reportPath) + "\\ent_todo_list.xml";
      } else if (repName.equalsIgnoreCase("Nash Rel")) {
        reportTemplate = String.valueOf(reportPath) + "\\nas_release_sched.xml";
      } else if (repName.equalsIgnoreCase("UmeProdSch")) {
        reportTemplate = String.valueOf(reportPath) + "\\ume_prod_sched.xml";
      } else if (repName.equalsIgnoreCase("UmeCustomS")) {
        reportTemplate = String.valueOf(reportPath) + "\\ume_custom_prod_sched.xml";
      } else if (repName.equalsIgnoreCase("NasProdSch")) {
        reportTemplate = String.valueOf(reportPath) + "\\nashProductionSchedule.xml";
      } else if (repName.equalsIgnoreCase("DWNashProd")) {
        reportTemplate = String.valueOf(reportPath) + "\\dream_nashProductionSchedule.xml";
      } else if (repName.equalsIgnoreCase("New Rel.")) {
        reportTemplate = String.valueOf(reportPath) + "\\umlNewReleaseMasterSchedule.xml";
      } else if (repName.equalsIgnoreCase("New Rel Me")) {
        reportTemplate = String.valueOf(reportPath) + "\\umlNewReleaseMasterScheduleMetadata.xml";
      } else if (repName.equalsIgnoreCase("UmlAddMov")) {
        reportTemplate = String.valueOf(reportPath) + "\\umlAddsMovesReport.xml";
      } else if (repName.equalsIgnoreCase("FutDueDate")) {
        reportTemplate = String.valueOf(reportPath) + "\\FutureTaskDueDates.xml";
      } else if (repName.equalsIgnoreCase("VerProdSch")) {
        reportTemplate = String.valueOf(reportPath) + "\\verveProductionSchedule.xml";
      } else if (repName.equalsIgnoreCase("LatProdSch")) {
        reportTemplate = String.valueOf(reportPath) + "\\latinoProductionSchedule.xml";
      } else if (repName.equalsIgnoreCase("IgaProd")) {
        reportTemplate = String.valueOf(reportPath) + "\\igaProductionSchedule.xml";
      } else if (repName.equalsIgnoreCase("ent rel La")) {
        reportTemplate = String.valueOf(reportPath) + "\\ent_release_sched_by_label.xml";
      } else if (repName.equalsIgnoreCase("igaprod")) {
        reportTemplate = String.valueOf(reportPath) + "\\igaproductionschedule.xml";
      } else if (repName.equalsIgnoreCase("IDJProd")) {
        reportTemplate = String.valueOf(reportPath) + "\\idjProductionSchedule.xml";
      } else if (repName.equalsIgnoreCase("addsmoves")) {
        reportTemplate = String.valueOf(reportPath) + "\\Adds_and_moves.xml";
      } else if (repName.equalsIgnoreCase("ClassRls")) {
        reportTemplate = String.valueOf(reportPath) + "\\ent_classics_rel_sched.xml";
      } else if (repName.equalsIgnoreCase("ClassProd")) {
        reportTemplate = String.valueOf(reportPath) + "\\ent_classics_prod_sched.xml";
      } else if (repName.equalsIgnoreCase("CapitlProd")) {
        reportTemplate = String.valueOf(reportPath) + "\\ent_capitol_prod_sched.xml";
      } else if (repName.equalsIgnoreCase("CarolnProd")) {
        reportTemplate = String.valueOf(reportPath) + "\\ent_caroline_prod_sched.xml";
      } else if (repName.equalsIgnoreCase("DWNashRls")) {
        reportTemplate = String.valueOf(reportPath) + "\\ent_dreamworks_release_schedule.xml";
      } else if (repName.equalsIgnoreCase("RlswoutSch")) {
        reportTemplate = String.valueOf(reportPath) + "\\ent_release_withoutsched.xml";
      } else if (repName.equalsIgnoreCase("pastduerls")) {
        reportTemplate = String.valueOf(reportPath) + "\\ent_past_due_releases.xml";
      } else if (repName.equalsIgnoreCase("McaProdScd")) {
        reportTemplate = String.valueOf(reportPath) + "\\mca_production_schedule.xml";
      } else if (repName.equalsIgnoreCase("McaCustImp")) {
        reportTemplate = String.valueOf(reportPath) + "\\mca_impact_schedule.xml";
      } else if (repName.equalsIgnoreCase("UmlProdScd")) {
        reportTemplate = String.valueOf(reportPath) + "\\uml_production_schedule.xml";
      } else if (repName.equalsIgnoreCase("PressPProd")) {
        reportTemplate = String.valueOf(reportPath) + "\\press_play_production_schedule.xml";
      } else if (repName.equalsIgnoreCase("DreRecSchd")) {
        reportTemplate = String.valueOf(reportPath) + "\\dream_record_schedule.xml";
      } else if (repName.equalsIgnoreCase("DreProSchd")) {
        reportTemplate = String.valueOf(reportPath) + "\\dream_promo_schedule.xml";
      } else if (repName.equalsIgnoreCase("NewPlanScd")) {
        reportTemplate = String.valueOf(reportPath) + "\\new_release_planning_schedule.xml";
      } else if (repName.equalsIgnoreCase("EntComAlb")) {
        reportTemplate = String.valueOf(reportPath) + "\\ent_comm_past_rel_dates_albums.xml";
      } else if (repName.equalsIgnoreCase("EntProAlb")) {
        reportTemplate = String.valueOf(reportPath) + "\\ent_promo_past_rel_dates_albums.xml";
      } else if (repName.equalsIgnoreCase("EntComOrph")) {
        reportTemplate = String.valueOf(reportPath) + "\\ent_comm_past_rel_dates_orphans.xml";
      } else if (repName.equalsIgnoreCase("EntProOrph")) {
        reportTemplate = String.valueOf(reportPath) + "\\ent_promo_past_rel_dates_orphans.xml";
      } else if (repName.equalsIgnoreCase("VerveComm")) {
        reportTemplate = String.valueOf(reportPath) + "\\verveCommercialSchedule.xml";
      } else if (repName.equalsIgnoreCase("DigProdSch")) {
        reportTemplate = String.valueOf(reportPath) + "\\digitalProductionSchedule.xml";
      } else if (repName.equalsIgnoreCase("DigProLSch")) {
        reportTemplate = String.valueOf(reportPath) + "\\digitalProductionLabelSchedule.xml";
      } else if (repName.equalsIgnoreCase("PhyProdAct")) {
        reportTemplate = String.valueOf(reportPath) + "\\physicalProductActivitySummary.xml";
      } else if (repName.equalsIgnoreCase("IDJProdAlt")) {
        reportTemplate = String.valueOf(reportPath) + "\\idjProductionScheduleAlternate.xml";
      } else if (repName.equalsIgnoreCase("MexProdSch")) {
        reportTemplate = String.valueOf(reportPath) + "\\mexicoProductionSchedule.xml";
      } else {
        if (repName.equalsIgnoreCase("Prod.Stat.")) {
          try {
            StyleSheet report = EntProdStatusForPrintSubHandler.fillEntProdStatusForPrint(context, 
                reportPath);
            HttpServletResponse sresponse = context.getResponse();
            context.putDelivery("status", new String("start_download"));
            sresponse.setContentType("text/plain");
            context.putSessionValue("currentReport", report);
            context.putSessionValue("currentReportName", new String("Prod.Stat."));
            context.putDelivery("download", new String("yes"));
            if (!ReportingServices.usingReportServicesByContext(context)) {
              context.includeJSP("status.jsp", "hiddenFrame");
              sresponse.flushBuffer();
            } 
          } catch (Exception exception) {}
          return;
        } 
        if (repName.equalsIgnoreCase("TaskDueDt")) {
          try {
            StyleSheet report = TaskDueDateForPrintSubHandler.fillTaskDueDateForPrint(context, 
                reportPath);
            HttpServletResponse sresponse = context.getResponse();
            context.putDelivery("status", new String("start_download"));
            sresponse.setContentType("text/plain");
            context.putSessionValue("currentReport", report);
            context.putSessionValue("currentReportName", new String("TaskDueDt"));
            context.putDelivery("download", new String("yes"));
            if (!ReportingServices.usingReportServicesByContext(context)) {
              context.includeJSP("status.jsp", "hiddenFrame");
              sresponse.flushBuffer();
            } 
          } catch (Exception exception) {}
          return;
        } 
        if (repName.equalsIgnoreCase("TaskDueTit")) {
          try {
            StyleSheet report = TaskDueDateByTitleForPrintHandler.fillTaskDueDateByTitleForPrint(context, reportPath, this.log);
            HttpServletResponse sresponse = context.getResponse();
            context.putDelivery("status", new String("start_download"));
            sresponse.setContentType("text/plain");
            context.putSessionValue("currentReport", report);
            context.putSessionValue("currentReportName", new String("TaskDueTit"));
            context.putDelivery("download", new String("yes"));
            if (!ReportingServices.usingReportServicesByContext(context)) {
              context.includeJSP("status.jsp", "hiddenFrame");
              sresponse.flushBuffer();
            } 
          } catch (Exception exception) {}
          return;
        } 
        if (repName.equalsIgnoreCase("MCARelSchd")) {
          try {
            StyleSheet report = MCACustomReleaseForPrintSubHandler.fillMCACustomReleaseForPrint(context, 
                reportPath);
            HttpServletResponse sresponse = context.getResponse();
            context.putDelivery("status", new String("start_download"));
            sresponse.setContentType("text/plain");
            context.putSessionValue("currentReport", report);
            context.putSessionValue("currentReportName", new String("MCARelSchd"));
            context.putDelivery("download", new String("yes"));
            if (!ReportingServices.usingReportServicesByContext(context)) {
              context.includeJSP("status.jsp", "hiddenFrame");
              sresponse.flushBuffer();
            } 
          } catch (Exception exception) {}
          return;
        } 
      } 
      if (reportTemplate != null) {
        System.out.println("Getting the report template - " + reportTemplate);
        try {
          InputStream input = new FileInputStream(reportTemplate);
          System.out.println("Building report ");
          XStyleSheet report = 
            (XStyleSheet)Builder.getBuilder(1, input).read(null);
          HttpServletResponse sresponse = context.getResponse();
          System.out.println("Executing report ");
          context.putDelivery("status", new String("start_gathering"));
          context.putDelivery("percent", new String("10"));
          if (!ReportingServices.usingReportServicesByContext(context)) {
            context.includeJSP("status.jsp", "hiddenFrame");
            sresponse.setContentType("text/plain");
            sresponse.flushBuffer();
          } 
          context.putDelivery("status", new String("start_gathering"));
          context.putDelivery("percent", new String("10"));
          if (!ReportingServices.usingReportServicesByContext(context)) {
            context.includeJSP("status.jsp", "hiddenFrame");
            sresponse.setContentType("text/plain");
            sresponse.flushBuffer();
          } 
          if (report != null) {
            if (repName.equalsIgnoreCase("Com Rel"))
              EntCommRelScheduleForPrintSubHandler.fillEntCommRelScheduleForPrint(report, context); 
            if (repName.equalsIgnoreCase("eInitRel")) {
              eInitiativeRelScheduleForPrintSubHandler.filleInitiativeRelScheduleForPrint(report, context, this.application);
            } else if (repName.equalsIgnoreCase("Rel.Shed.")) {
              EntRelScheduleForPrintSubHandler.fillEntRelScheduleForPrint(report, context);
            } else if (repName.equalsIgnoreCase("Nash Rel")) {
              NashRelScheduleForPrintSubHandler.fillNashRelScheduleForPrint(report, context);
            } else if (repName.equalsIgnoreCase("RlswoutSch")) {
              EntRelWithoutScheduleForPrintSubHandler.fillEntRelWithoutScheduleForPrint(report, context);
            } else if (repName.equalsIgnoreCase("DWNashRls")) {
              DreamWorksRelScheduleForPrintSubHandler.fillDreamWorksRelScheduleForPrint(report, context);
            } else if (repName.equalsIgnoreCase("UmeProdSch")) {
              UmeProdScheduleForPrintSubHandler.fillUmeProdScheduleForPrint(report, context);
            } else if (repName.equalsIgnoreCase("UmeCustomS")) {
              UmeCustomProdScheduleForPrintSubHandler.fillUmeCustomProdScheduleForPrint(report, context);
            } else if (repName.equalsIgnoreCase("New Rel.")) {
              UmlNewReleaseMasterScheduleSubHandler.fillUmlNewReleaseMasterScheduleForPrint(report, context);
            } else if (repName.equalsIgnoreCase("New Rel Me")) {
              UmlNewReleaseMasterScheduleMetaSubHandler.fillUmlNewReleaseMasterScheduleForPrint(report, context);
            } else if (repName.equalsIgnoreCase("UmlAddMov")) {
              UmlAddsMovesReportSubHandler.fillUmlAddsMovesReportForPrint(report, context);
            } else if (repName.equalsIgnoreCase("FutDueDate")) {
              FutureTaskDueDatesByTemplateSubHandler futureDueDates = 
                new FutureTaskDueDatesByTemplateSubHandler(this.application);
              futureDueDates.fillFutureTaskDueDatesByTemplateSubHandler(report, context);
              futureDueDates = null;
            } else if (repName.equalsIgnoreCase("VerProdSch")) {
              VerveScheduleUpdateForPrintSubHandler.fillVerveScheduleUpdateForPrint(report, context);
            } else if (repName.equalsIgnoreCase("LatProdSch")) {
              LatinoProductionScheduleUpdateForPrintSubHandler.fillLatinoProductionScheduleUpdateForPrint(report, context);
            } else if (repName.equalsIgnoreCase("ent rel la")) {
              EntReleaseSchedByLabelSubHandler.fillEntReleaseSchedByLabelForPrint(report, context);
            } else if (repName.equalsIgnoreCase("igaprod")) {
              IgaProductionScheduleUpdateForPrintSubHandler.fillIgaProductionScheduleForPrint(report, context);
            } else if (repName.equalsIgnoreCase("IDJProd")) {
              IdjProductionScheduleForPrintSubHandlerNew2091.fillIdjProductionScheduleForPrint(report, context);
            } else if (repName.equalsIgnoreCase("IDJProdAlt")) {
              IdjProductionScheduleForPrintSubHandlerAlternate.fillIdjProductionScheduleForPrint(report, context);
            } else if (repName.equalsIgnoreCase("addsmoves")) {
              AddsAndMovesForPrintSubHandler.fillAddsAndMovesForPrint(report, context);
            } else if (repName.equalsIgnoreCase("NasProdSch") || repName.equalsIgnoreCase("DWNashProd")) {
              NashProdScheduleForPrintSubHandler.fillNashProdScheduleForPrint2(report, context);
            } else if (repName.equalsIgnoreCase("pastduerls")) {
              PastDueReleasesForPrintSubHandler.fillPastDueReleasesForPrint(report, context);
            } else if (repName.equalsIgnoreCase("ClassRls")) {
              ClassicsRelSchdForPrintSubHandler.fillClassicsRelSchdForPrint(report, context);
            } else if (repName.equalsIgnoreCase("McaProdScd")) {
              MCAProductionScheduleForPrintSubHandler.fillMCAProductionScheduleForPrint(report, context);
            } else if (repName.equalsIgnoreCase("ClassProd")) {
              ClassicsProductionScheduleForPrintSubHandler.fillClassicProductionUpdateForPrint(report, context);
            } else if (repName.equalsIgnoreCase("CapitlProd")) {
              CapitolProductionSchedulePrintSubHandler.fillCapitolProductionUpdateForPrint(report, context);
            } else if (repName.equalsIgnoreCase("CarolnProd")) {
              CarolineProductionSchedulePrintSubHandler.fillCarolineProductionUpdateForPrint(report, context);
            } else if (repName.equalsIgnoreCase("ToDoList")) {
              ToDoListReportForPrintSubHandler.fillEntToDoListForPrint(report, context);
            } else if (repName.equalsIgnoreCase("McaCustImp")) {
              MCACustomImpactScheduleForPrintSubHandler.fillMCACustomImpactScheduleForPrint(report, context);
            } else if (repName.equalsIgnoreCase("UmlProdScd")) {
              UmlProductionScheduleForPrintSubHandler.fillUmlProductionScheduleForPrint(report, context);
            } else if (repName.equalsIgnoreCase("PressPProd")) {
              PressPlayProductionScheduleForPrintSubHandler.fillPressPlayProductionScheduleForPrint(report, context);
            } else if (repName.equalsIgnoreCase("DreRecSchd")) {
              DreamWorksRecordScheduleForPrintSubHandler.fillDreamWorksRecordScheduleForPrint(report, context);
            } else if (repName.equalsIgnoreCase("DreProSchd")) {
              DreamWorksPromoScheduleForPrintSubHandler.fillDreamWorksPromoScheduleForPrint(report, context);
            } else if (repName.equalsIgnoreCase("NewPlanScd")) {
              MCANewReleasePlanningSchedulePrintSubHandler.fillMCANewReleasePlanningScheduleForPrint(report, context);
            } else if (repName.equalsIgnoreCase("EntProAlb")) {
              EntPastRelAlbumsForPrintSubHandler.fillEntPastRelDatesForPrint(report, context, "promo");
            } else if (repName.equalsIgnoreCase("EntComAlb")) {
              EntPastRelAlbumsForPrintSubHandler.fillEntPastRelDatesForPrint(report, context, "commercial");
            } else if (repName.equalsIgnoreCase("EntProOrph")) {
              EntPastRelOrphansForPrintSubHandler.fillEntPastRelDatesForPrint(report, context, "promo");
            } else if (repName.equalsIgnoreCase("EntComOrph")) {
              EntPastRelOrphansForPrintSubHandler.fillEntPastRelDatesForPrint(report, context, "commercial");
            } else if (repName.equalsIgnoreCase("VerveComm")) {
              VerveCommercialReleaseScheduleForPrintSubHandler.fillVerveScheduleUpdateForPrint(report, context);
            } else if (repName.equalsIgnoreCase("DigProdSch")) {
              DigitalProductionScheduleForPrintSubHandler.fillDigitalProductionScheduleUpdateForPrint(report, context);
            } else if (repName.equalsIgnoreCase("DigProLSch")) {
              DigitalProductionLabelScheduleForPrintSubHandler.fillDigitalProductionLabelScheduleUpdateForPrint(report, context);
            } else if (repName.equalsIgnoreCase("PhyProdAct")) {
              PhysicalProductActivitySummaryForPrintSubHandler.fillPhysicalProductActivitySummaryScheduleUpdateForPrint(report, context);
            } else if (repName.equalsIgnoreCase("MexProdSch")) {
              MexicoScheduleForPrintSubHandler.fillMexicoScheduleForPrint(report, context);
            } 
          } 
          context.putDelivery("status", new String("start_download"));
          sresponse.setContentType("text/plain");
          context.putSessionValue("currentReport", report);
          context.putDelivery("download", new String("yes"));
          if (!ReportingServices.usingReportServicesByContext(context)) {
            context.includeJSP("status.jsp", "hiddenFrame");
            sresponse.flushBuffer();
          } 
          return;
        } catch (Exception e) {
          System.out.print("Exceptin error found " + e.getMessage());
        } 
      } 
    } 
    edit(context);
  }
  
  public void save(Context context) { edit(context); }
  
  public Notepad getReportsNotepad(Vector contents, Context context, int userId) {
    if (MilestoneHelper.getNotepadFromSession(3, context) != null) {
      Notepad notepad = MilestoneHelper.getNotepadFromSession(3, context);
      if (notepad.getAllContents() == null) {
        this.log.debug("---------Reseting note pad contents------------");
        contents = ReportManager.getInstance().getReportNotepadList(notepad, context);
        notepad.setAllContents(contents);
      } 
      return notepad;
    } 
    String[] columnNames = { "Description" };
    contents = ReportManager.getInstance().getReportNotepadList(null, context);
    return new Notepad(contents, 0, 15, "Reports", 3, columnNames);
  }
  
  protected Form buildForm(Context context, Report report) {
    User user = MilestoneSecurity.getUser(context);
    Form reportForm = new Form(this.application, "reportForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    reportForm.addElement(new FormHidden("cmd", "reports-editor", true));
    FormTextField ReportDescriptionSearch = new FormTextField("ReportDescriptionSearch", "", false, 30, 30);
    ReportDescriptionSearch.setId("ReportDescriptionSearch");
    reportForm.addElement(ReportDescriptionSearch);
    reportForm.addElement(new FormHidden("OrderBy", "", true));
    if (context.getRequest().getParameter("OrderByDirection") != null) {
      reportForm.addElement(new FormHidden("OrderByDirection", context.getRequest().getParameter("OrderByDirection"), true));
    } else {
      reportForm.addElement(new FormHidden("OrderByDirection", "0", true));
    } 
    if (report != null) {
      boolean endDate = false;
      endDate = report.getEndDateFlag();
      if (endDate) {
        FormTextField EndDate = new FormTextField("endDate", "", true, 10);
        EndDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
        EndDate.addFormEvent("onBlur", "JavaScript:removeSpacesInDate(document.forms[0].endDate.value,this);if (!checkField(this))this.focus();");
        EndDate.addFormEvent("onClick", "Javascript:hidePrintButtons('printWindow','');");
        EndDate.setTabIndex(2);
        reportForm.addElement(EndDate);
      } 
      boolean beginDate = false;
      beginDate = report.getBeginDateFlag();
      if (beginDate) {
        FormTextField BeginDate = new FormTextField("beginDate", "", true, 10);
        BeginDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
        BeginDate.addFormEvent("onBlur", "JavaScript:removeSpacesInDate(document.forms[0].beginDate.value,this);if (!checkField(this))this.focus();");
        BeginDate.addFormEvent("onClick", "Javascript:hidePrintButtons('printWindow','');");
        BeginDate.setTabIndex(1);
        reportForm.addElement(BeginDate);
      } 
      boolean endDueDate = false;
      endDueDate = report.getEndDueDateFlag();
      if (endDueDate) {
        FormTextField EndDueDate = new FormTextField("endDueDate", "", true, 10);
        EndDueDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
        EndDueDate.addFormEvent("onBlur", "JavaScript:removeSpacesInDate(document.forms[0].endDueDate.value,this);if (!checkField(this))this.focus();");
        EndDueDate.addFormEvent("onClick", "Javascript:hidePrintButtons('printWindow','');");
        EndDueDate.setTabIndex(4);
        reportForm.addElement(EndDueDate);
      } 
      boolean beginDueDate = false;
      beginDueDate = report.getBeginDueDateFlag();
      if (beginDueDate) {
        FormTextField BeginDueDate = new FormTextField("beginDueDate", "", true, 10);
        BeginDueDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
        BeginDueDate.addFormEvent("onBlur", "JavaScript:removeSpacesInDate(document.forms[0].beginDueDate.value,this);if (!checkField(this))this.focus();");
        BeginDueDate.addFormEvent("onClick", "Javascript:hidePrintButtons('printWindow','');");
        BeginDueDate.setTabIndex(3);
        reportForm.addElement(BeginDueDate);
      } 
      boolean region = false;
      region = report.getRegionFlag();
      if (region) {
        FormRadioButtonGroup Region = new FormRadioButtonGroup("distribution", "All", "West,East,All", false);
        Region.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        Region.setTabIndex(7);
        reportForm.addElement(Region);
      } 
      boolean productGroupCode = false;
      productGroupCode = report.getProductGroupCodeFlag();
      if (productGroupCode) {
        Vector productionGroupCodeVector = ReportConfigManager.getATLASProductionGroupCodes();
        String productionGroupCodeList = "All,";
        String idList = "0,";
        if (productionGroupCodeVector != null) {
          for (int i = 0; i < productionGroupCodeVector.size(); i++) {
            ProductionGroupCode productionGroupCode = (ProductionGroupCode)productionGroupCodeVector.get(i);
            if (i < productionGroupCodeVector.size()) {
              if (productionGroupCode != null) {
                productionGroupCodeList = String.valueOf(productionGroupCodeList) + productionGroupCode.getName() + ",";
                idList = String.valueOf(idList) + productionGroupCode.getId() + ",";
              } 
            } else if (productionGroupCode != null) {
              productionGroupCodeList = String.valueOf(productionGroupCodeList) + productionGroupCode.getName();
              idList = String.valueOf(idList) + productionGroupCode.getId();
            } 
          } 
        } else {
          productionGroupCodeList = "All";
          idList = "0";
        } 
        int defaultProductionGroupCode = 0;
        FormDropDownMenu prodGroupCodeDropDown = new FormDropDownMenu("productionGroupCode", String.valueOf(defaultProductionGroupCode), productionGroupCodeList, productionGroupCodeList, false);
        prodGroupCodeDropDown.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        prodGroupCodeDropDown.setTabIndex(10);
        reportForm.addElement(prodGroupCodeDropDown);
      } 
      boolean releaseType = false;
      releaseType = report.getReleaseTypeFlag();
      if (releaseType) {
        String defaultReleaseType = "All";
        if (user.getPreferences().getReportsReleaseType() == 1) {
          defaultReleaseType = "Commercial";
        } else if (user.getPreferences().getReportsReleaseType() == 2) {
          defaultReleaseType = "Promotional";
        } 
        FormRadioButtonGroup ReleaseType = new FormRadioButtonGroup("releaseType", defaultReleaseType, "Commercial, Promotional, All", false);
        ReleaseType.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        ReleaseType.setTabIndex(8);
        reportForm.addElement(ReleaseType);
      } 
      boolean status = false;
      status = report.getStatusFlag();
      String[] statusArray = { "Closed", "Active", "Active, excluding TBS", "All", "TBS", "All, excluding TBS", "Cancelled" };
      if (status) {
        boolean defaultStatus = false;
        defaultStatus = (user.getPreferences().getReportsStatusClosed() == 1);
        FormCheckBox Status_closed = new FormCheckBox("status_closed", "Closed", false, defaultStatus);
        Status_closed.addFormEvent("onClick", "hidePrintButtons('printWindow','');clickStatus(this);");
        Status_closed.setTabIndex(9);
        reportForm.addElement(Status_closed);
        defaultStatus = (user.getPreferences().getReportsStatusActive() == 1);
        FormCheckBox Status_active = new FormCheckBox("status_active", "Active", false, defaultStatus);
        Status_active.addFormEvent("onClick", "hidePrintButtons('printWindow','');clickStatus(this);");
        Status_active.setTabIndex(10);
        reportForm.addElement(Status_active);
        defaultStatus = (user.getPreferences().getReportsStatusAll() == 1);
        FormCheckBox Status_all = new FormCheckBox("status_all", "All", false, defaultStatus);
        Status_all.addFormEvent("onClick", "hidePrintButtons('printWindow','');clickStatus(this)");
        Status_all.setTabIndex(12);
        reportForm.addElement(Status_all);
        defaultStatus = (user.getPreferences().getReportsStatusTBS() == 1);
        FormCheckBox Status_tbs = new FormCheckBox("status_tbs", "TBS", false, defaultStatus);
        Status_tbs.addFormEvent("onClick", "hidePrintButtons('printWindow','');clickStatus(this);");
        Status_tbs.setTabIndex(13);
        reportForm.addElement(Status_tbs);
        defaultStatus = (user.getPreferences().getReportsStatusCancelled() == 1);
        FormCheckBox Status_cancelled = new FormCheckBox("status_cancelled", "Cancelled", false, defaultStatus);
        Status_cancelled.addFormEvent("onClick", "hidePrintButtons('printWindow','');clickStatus(this);");
        Status_cancelled.setTabIndex(15);
        reportForm.addElement(Status_cancelled);
      } 
      boolean contact = false;
      contact = report.getContactFlag();
      if (contact) {
        Vector contactVector = ReportConfigManager.getLabelContacts();
        String contactList = "All,";
        String idList = "0,";
        if (contactVector != null) {
          for (int i = 0; i < contactVector.size(); i++) {
            User userContact = (User)contactVector.get(i);
            if (i < contactVector.size() - 1) {
              if (userContact != null) {
                contactList = String.valueOf(contactList) + userContact.getName() + ",";
                idList = String.valueOf(idList) + userContact.getUserId() + ",";
              } 
            } else if (userContact != null) {
              contactList = String.valueOf(contactList) + userContact.getName();
              idList = String.valueOf(idList) + userContact.getUserId();
            } 
          } 
        } else {
          contactList = "All";
          idList = "0";
        } 
        int defaultLabelContact = 0;
        if (user.getPreferences().getReportsLabelContact() > 0)
          defaultLabelContact = user.getPreferences().getReportsLabelContact(); 
        FormDropDownMenu ContactList = new FormDropDownMenu("contact", String.valueOf(defaultLabelContact), idList, contactList, false);
        ContactList.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        ContactList.setTabIndex(10);
        reportForm.addElement(ContactList);
      } 
      boolean umlContact = false;
      umlContact = report.getFuture1Flag();
      if (umlContact) {
        Vector contactVector1 = ReportConfigManager.getUmlContacts();
        String contactList1 = "All,";
        String idList = "0,";
        if (contactVector1 != null) {
          for (int i = 0; i < contactVector1.size(); i++) {
            User userContact1 = (User)contactVector1.get(i);
            if (i < contactVector1.size() - 1) {
              if (userContact1 != null) {
                contactList1 = String.valueOf(contactList1) + userContact1.getName() + ",";
                idList = String.valueOf(idList) + userContact1.getUserId() + ",";
              } 
            } else if (userContact1 != null) {
              contactList1 = String.valueOf(contactList1) + userContact1.getName();
              idList = String.valueOf(idList) + userContact1.getUserId();
            } 
          } 
        } else {
          contactList1 = "All";
          idList = "0";
        } 
        int defaultContact = 0;
        if (user.getPreferences().getReportsUMLContact() > 0)
          defaultContact = user.getPreferences().getReportsUMLContact(); 
        FormDropDownMenu ContactList1 = new FormDropDownMenu("umlContact", String.valueOf(defaultContact), idList, contactList1, false);
        ContactList1.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        ContactList1.setTabIndex(11);
        reportForm.addElement(ContactList1);
      } 
      boolean completeKeyTask = false;
      completeKeyTask = report.getCompleteKeyTaskFlag();
      if (completeKeyTask) {
        FormCheckBox KeyDatesOnly = new FormCheckBox("keyDatesOnly", "", false, false);
        KeyDatesOnly.setTabIndex(12);
        reportForm.addElement(KeyDatesOnly);
        FormCheckBox CompleteKeyTask = new FormCheckBox("completeKeyTask", "", false, false);
        CompleteKeyTask.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        CompleteKeyTask.setTabIndex(13);
        reportForm.addElement(CompleteKeyTask);
      } 
      boolean taskOwner = false;
      taskOwner = report.getUmlDatesFlag();
      boolean umlKeyTask = false;
      umlKeyTask = report.getUmlKeyTaskFlag();
      if (umlKeyTask) {
        FormRadioButtonGroup UmlKeyTask = new FormRadioButtonGroup("umlKeyTask", "All", "UML, Label, All", false);
        UmlKeyTask.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        UmlKeyTask.setTabIndex(12);
        reportForm.addElement(UmlKeyTask);
      } else if (report.getReportName().trim().equalsIgnoreCase("New Rel.")) {
        report.setUmlKeyTaskFlag(true);
        FormRadioButtonGroup UmlKeyTask = new FormRadioButtonGroup("umlKeyTask", "UML", "UML", false);
        reportForm.addElement(UmlKeyTask);
      } 
      boolean parentsOnly = false;
      parentsOnly = report.getParentsOnlyFlag();
      if (parentsOnly) {
        FormCheckBox ParentsOnly = new FormCheckBox("ParentsOnly", "", false);
        ParentsOnly.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        ParentsOnly.setTabIndex(18);
        reportForm.addElement(ParentsOnly);
      } 
      boolean artist = false;
      artist = report.getArtistFlag();
      if (artist) {
        FormTextField Artist = new FormTextField("artist", "", false, 34, 34);
        Artist.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        Artist.setTabIndex(16);
        reportForm.addElement(Artist);
      } 
      boolean future2 = false;
      future2 = report.getFuture2Flag();
      if (future2) {
        FormCheckBox Future2 = new FormCheckBox("Future2", "", false, false);
        Future2.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        Future2.setTabIndex(19);
        reportForm.addElement(Future2);
      } 
      boolean family = false;
      family = report.getFamilyFlag();
      if (family) {
        int defaultFamily = -1;
        if (user.getPreferences().getReportsReleasingFamily() > 0)
          defaultFamily = user.getPreferences().getReportsReleasingFamily(); 
        Vector families = SelectionHandler.filterCSO(ReleasingFamily.getUserReleasingFamiliesVectorOfFamilies(context));
        MilestoneFormDropDownMenu Family = getCorporateStructureDropDownMultSel("family", families, String.valueOf(defaultFamily), false, true);
        Family.addFormEvent("onChange", "return(clickFamily(this))");
        Family.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        Family.setTabIndex(20);
        Family.setMultiple(true);
        reportForm.addElement(Family);
        String hiddenFamilyIndex = "0";
        if (context.getRequest().getParameter("hiddenFamilyIndex") != null)
          hiddenFamilyIndex = context.getRequest().getParameter("hiddenFamilyIndex"); 
        reportForm.addElement(new FormHidden("hiddenFamilyIndex", hiddenFamilyIndex, false));
      } 
      boolean environment = false;
      environment = report.getEnvironmentFlag();
      if (environment) {
        Vector environments = MilestoneHelper.getUserEnvironments(context);
        Vector companies = MilestoneHelper.getUserCompanies(context);
        environments = SelectionHandler.filterSelectionEnvironments(companies);
        companies = MilestoneHelper.removeUnusedCSO(companies, context, -1);
        int defaultEnv = -1;
        if (user.getPreferences().getReportsEnvironment() > 0)
          defaultEnv = user.getPreferences().getReportsEnvironment(); 
        MilestoneFormDropDownMenu envMenu = getCorporateStructureDropDownMultSel("environment", SelectionHandler.filterSelectionEnvironments(companies), String.valueOf(defaultEnv), false, true);
        envMenu.setMultiple(true);
        envMenu.addFormEvent("onChange", "return(clickEnvironment(this))");
        envMenu.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        envMenu.setTabIndex(21);
        reportForm.addElement(envMenu);
        String hiddenEnvironmentIndex = "0";
        if (context.getRequest().getParameter("hiddenEnvironmentIndex") != null)
          hiddenEnvironmentIndex = context.getRequest().getParameter("hiddenEnvironmentIndex"); 
        reportForm.addElement(new FormHidden("hiddenEnvironmentIndex", hiddenEnvironmentIndex, false));
      } 
      boolean company = false;
      company = report.getCompanyFlag();
      if (company) {
        Vector companies = MilestoneHelper.getUserCompanies(context);
        companies = SelectionHandler.filterSelectionCompanies(companies);
        companies = MilestoneHelper.removeUnusedCSO(companies, context, -1);
        MilestoneFormDropDownMenu Company = getCorporateStructureDropDownMultSel("company", SelectionHandler.filterSelectionCompanies(companies), "-1", false, true);
        Company.setMultiple(true);
        Company.addFormEvent("onChange", "return(clickCompany(this))");
        Company.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        Company.setTabIndex(22);
        reportForm.addElement(Company);
        String hiddenCompany = "0";
        if (context.getRequest().getParameter("hiddenCompany") != null)
          hiddenCompany = context.getRequest().getParameter("hiddenCompany"); 
        reportForm.addElement(new FormHidden("hiddenCompany", hiddenCompany, false));
        String hiddenCompanyIndex = "0";
        if (context.getRequest().getParameter("hiddenCompanyIndex") != null)
          hiddenCompanyIndex = context.getRequest().getParameter("hiddenCompanyIndex"); 
        reportForm.addElement(new FormHidden("hiddenCompanyIndex", hiddenCompanyIndex, false));
      } 
      boolean label = false;
      label = report.getLabelFlag();
      if (label) {
        Vector companies = MilestoneHelper.getUserCompanies(context);
        Vector labels = MilestoneHelper.getUserLabels(companies);
        labels = MilestoneHelper.removeUnusedCSO(labels, context, -1);
        MilestoneFormDropDownMenu Label = getCorporateStructureDropDownDuplicates("Label", labels, "", false, true, false);
        Label.setId("Label");
        Label.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        Label.setTabIndex(22);
        Label.setMultiple(true);
        reportForm.addElement(Label);
        String hiddenLabel = "0";
        if (context.getRequest().getParameter("hiddenLabel") != null)
          hiddenLabel = context.getRequest().getParameter("hiddenLabel"); 
        reportForm.addElement(new FormHidden("hiddenLabel", hiddenLabel, false));
        String hiddenLabelIndex = "0";
        if (context.getRequest().getParameter("hiddenLabelIndex") != null)
          hiddenLabelIndex = context.getRequest().getParameter("hiddenLabelIndex"); 
        reportForm.addElement(new FormHidden("hiddenLabelIndex", hiddenLabelIndex, false));
      } 
      boolean endEffectiveDate = false;
      endEffectiveDate = report.getEndEffectiveDateFlag();
      if (endEffectiveDate) {
        FormTextField EndEffectiveDate = new FormTextField("endEffectiveDate", "", true, 10);
        EndEffectiveDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
        EndEffectiveDate.addFormEvent("onBlur", "JavaScript:removeSpacesInDate(document.forms[0].endEffectiveDate.value,this);if (!checkField(this))this.focus();");
        EndEffectiveDate.addFormEvent("onClick", "Javascript:hidePrintButtons('printWindow','');");
        EndEffectiveDate.setTabIndex(6);
        reportForm.addElement(EndEffectiveDate);
      } 
      boolean beginEffectiveDate = false;
      beginEffectiveDate = report.getBeginEffectiveDateFlag();
      if (beginEffectiveDate) {
        FormTextField BeginEffectiveDate = new FormTextField("beginEffectiveDate", "", true, 10);
        BeginEffectiveDate.addFormEvent("onDblClick", "setDateField( this );top.newWin = window.open( 'html/calendar.html', 'cal', 'WIDTH=208,HEIGHT=230,resizable=yes')");
        BeginEffectiveDate.addFormEvent("onBlur", "JavaScript:removeSpacesInDate(document.forms[0].beginEffectiveDate.value,this);if (!checkField(this))this.focus();");
        BeginEffectiveDate.addFormEvent("onClick", "Javascript:hidePrintButtons('printWindow','');");
        BeginEffectiveDate.setTabIndex(5);
        reportForm.addElement(BeginEffectiveDate);
      } 
      boolean configuration = false;
      configuration = report.getConfiguration();
      if (configuration) {
        MilestoneFormDropDownMenu ConfigList;
        if (report.getProductType() == 2) {
          ConfigList = new MilestoneFormDropDownMenu(MilestoneHelper.getSelectionConfigurationDropDown("configurationList", "", false));
        } else {
          ConfigList = new MilestoneFormDropDownMenu(MilestoneHelper.getSelectionConfigurationDropDown("configurationList", "", false, report.getProductType()));
        } 
        ConfigList.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        ConfigList.addFormEvent("onChange", "getSubconfigs(this.selectedIndex);");
        String[] values = ConfigList.getValueList();
        String[] menuText = ConfigList.getMenuTextList();
        values[0] = "All";
        menuText[0] = "All";
        ConfigList.setValueList(values);
        ConfigList.setMenuTextList(menuText);
        ConfigList.setTabIndex(23);
        ConfigList.setMultiple(true);
        ConfigList.addFormEvent("SIZE", "5");
        reportForm.addElement(ConfigList);
        String hiddenFormatIndex = "0";
        if (context.getRequest().getParameter("hiddenFormatIndex") != null)
          hiddenFormatIndex = context.getRequest().getParameter("hiddenFormatIndex"); 
        reportForm.addElement(new FormHidden("hiddenFormatIndex", hiddenFormatIndex, false));
      } 
      boolean subconfiguration = false;
      subconfiguration = report.getSubconfigFlag();
      if (subconfiguration) {
        MilestoneFormDropDownMenu SubconfigList = new MilestoneFormDropDownMenu("subconfigurationList", "All");
        SubconfigList.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        SubconfigList.setTabIndex(23);
        SubconfigList.setMultiple(true);
        SubconfigList.addFormEvent("SIZE", "5");
        reportForm.addElement(SubconfigList);
        String hiddenSubFormatIndex = "0";
        if (context.getRequest().getParameter("hiddenSubFormatIndex") != null)
          hiddenSubFormatIndex = context.getRequest().getParameter("hiddenFormatIndex"); 
        reportForm.addElement(new FormHidden("hiddenSubFormatIndex", hiddenSubFormatIndex, false));
      } 
      boolean scheduledReleases = false;
      scheduledReleases = report.getScheduledReleasesFlag();
      if (scheduledReleases) {
        FormCheckBox ScheduledReleases = new FormCheckBox("ScheduledReleases", "", false);
        ScheduledReleases.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        ScheduledReleases.setTabIndex(17);
        reportForm.addElement(ScheduledReleases);
      } 
      boolean addsMovesBoth = false;
      addsMovesBoth = report.getAddsMovesBoth();
      if (addsMovesBoth) {
        FormRadioButtonGroup addsMoves = new FormRadioButtonGroup("AddsMovesBoth", "Both", "Adds,Moves,Both", false);
        addsMoves.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        addsMoves.setTabIndex(18);
        reportForm.addElement(addsMoves);
      } 
      boolean distCoBoo = false;
      distCoBoo = report.getDistCo();
      if (distCoBoo) {
        Hashtable distCoHash = MilestoneHelper_2.getDistCoNames();
        Enumeration distCo = distCoHash.keys();
        Vector distIDs = new Vector();
        while (distCo.hasMoreElements())
          distIDs.add((Integer)distCo.nextElement()); 
        for (int distCoCounter = 0; distCoCounter < distIDs.size(); distCoCounter++) {
          Integer discoIdInt = (Integer)distIDs.elementAt(distCoCounter);
          int discoId = discoIdInt.intValue();
          String checkBoxID = "distCo" + Integer.toString(discoId);
          String checkBoxValue = (String)distCoHash.get(discoIdInt);
          FormCheckBox DistCo = new FormCheckBox(checkBoxID, checkBoxValue, false, false);
          DistCo.addFormEvent("onClick", "hidePrintButtons('printWindow','');clickDistCo(this);");
          reportForm.addElement(DistCo);
        } 
        FormCheckBox DistCo = new FormCheckBox("distCo0", "All", false, true);
        DistCo.addFormEvent("onClick", "hidePrintButtons('printWindow','');clickDistCo(this);");
        reportForm.addElement(DistCo);
      } 
      boolean physicalProductActivity = false;
      physicalProductActivity = report.getPhysicalProductActivity();
      if (physicalProductActivity) {
        FormCheckBox AllActivity = new FormCheckBox("AllActivity", "All", false, false);
        AllActivity.setChecked(true);
        AllActivity.addFormEvent("onClick", "productActivitySelected(this);");
        AllActivity.setTabIndex(12);
        AllActivity.setId("AllActivity");
        reportForm.addElement(AllActivity);
        FormCheckBox ChangesActivity = new FormCheckBox("ChangesActivity", "Changed", false);
        ChangesActivity.setChecked(true);
        ChangesActivity.addFormEvent("onClick", "productActivitySelected(this);");
        ChangesActivity.setTabIndex(12);
        ChangesActivity.setId("ChangesActivity");
        reportForm.addElement(ChangesActivity);
        FormCheckBox CancelsActivity = new FormCheckBox("CancelsActivity", "Cancelled", false);
        CancelsActivity.setChecked(true);
        CancelsActivity.addFormEvent("onClick", "productActivitySelected(this);");
        CancelsActivity.setTabIndex(12);
        CancelsActivity.setId("CancelsActivity");
        reportForm.addElement(CancelsActivity);
        FormCheckBox DeletesActivity = new FormCheckBox("DeletesActivity", "Deleted", false);
        DeletesActivity.setChecked(true);
        DeletesActivity.addFormEvent("onClick", "productActivitySelected(this);");
        DeletesActivity.setTabIndex(12);
        DeletesActivity.setId("DeletesActivity");
        reportForm.addElement(DeletesActivity);
        FormCheckBox AddsActivity = new FormCheckBox("AddsActivity", "Added", false);
        AddsActivity.setChecked(true);
        AddsActivity.addFormEvent("onClick", "productActivitySelected(this);");
        AddsActivity.setTabIndex(12);
        AddsActivity.setId("AddsActivity");
        reportForm.addElement(AddsActivity);
        FormCheckBox MovesActivity = new FormCheckBox("MovesActivity", "Moved", false);
        MovesActivity.setChecked(true);
        MovesActivity.addFormEvent("onClick", "productActivitySelected(this);");
        MovesActivity.setTabIndex(13);
        MovesActivity.setId("MovesActivity");
        reportForm.addElement(MovesActivity);
      } 
      int productType = 0;
      productType = report.getProductType();
      if (productType == 2) {
        FormRadioButtonGroup ProductType;
        String reportName = report.getReportName();
        if (reportName.equalsIgnoreCase("IDJProdAlt")) {
          ProductType = new FormRadioButtonGroup("ProductType", "Both", "Physical,Digital,Both", false);
        } else {
          ProductType = new FormRadioButtonGroup("ProductType", "Physical", "Physical,Digital,Both", false);
        } 
        ProductType.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        ProductType.setTabIndex(19);
        reportForm.addElement(ProductType);
      } 
      String reportName = report.getReportName();
      if (reportName.equalsIgnoreCase("eInitRel")) {
        FormDropDownMenu eInitRelList = new FormDropDownMenu("eInitRelList", "Artist", "0,1,2", "Artist,Volume,ORG Release Date", false);
        eInitRelList.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        eInitRelList.setTabIndex(24);
        reportForm.addElement(eInitRelList);
      } 
      if (reportName.equalsIgnoreCase("FutDueDate")) {
        Vector templates = ReportConfigManager.getTemplateNames(context);
        String templatesList = "All";
        String idList = "0";
        if (templates != null)
          for (int i = 0; i < templates.size(); i++) {
            Template template = (Template)templates.get(i);
            templatesList = String.valueOf(templatesList) + "," + template.getTempateName();
            idList = String.valueOf(idList) + "," + template.getTemplateID();
          }  
        MilestoneFormDropDownMenu deadLineList = new MilestoneFormDropDownMenu("templatesList", "0", idList, templatesList, false);
        deadLineList.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
        deadLineList.setTabIndex(25);
        deadLineList.setMultiple(true);
        deadLineList.addFormEvent("SIZE", "10");
        reportForm.addElement(deadLineList);
        context.putSessionValue("templatesAll", templates);
      } 
    } 
    if (context.getSessionValue("NOTEPAD_REPORTS_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_REPORTS_VISIBLE")); 
    return reportForm;
  }
  
  protected boolean summary(Context context) {
    Report report = MilestoneHelper.getScreenReport(context);
    context.putSessionValue("report", report);
    return context.includeJSP("report-summary.jsp");
  }
  
  protected boolean criteria(Context context, Dispatcher dispatcher) {
    Report report = MilestoneHelper.getScreenReport(context);
    Form reportForm = buildForm(context, report);
    reportForm.setValues(context);
    ReportSelectionsHelper.setReportHiddenValues("family", "hiddenFamilyIndex", context, reportForm);
    ReportSelectionsHelper.setReportHiddenValues("environment", "hiddenEnvironmentIndex", context, reportForm);
    ReportSelectionsHelper.setReportHiddenValues("company", "hiddenCompanyIndex", context, reportForm);
    ReportSelectionsHelper.setReportHiddenValues("Label", "hiddenLabelIndex", context, reportForm);
    ReportSelectionsHelper.setReportHiddenValues("configurationList", "hiddenFormatIndex", context, reportForm);
    ReportSelectionsHelper.setReportHiddenValues("subconfigurationList", "hiddenSubFormatIndex", context, reportForm);
    context.putDelivery("Form", reportForm);
    context.putSessionValue("report", report);
    context.putSessionValue("reportForm", reportForm);
    ie55 = true;
    print(context, 0, true, dispatcher);
    return true;
  }
  
  private String getJavaScriptCorporateArray(Context context) {
    StringBuffer result = new StringBuffer(100);
    String str = "";
    String value = new String();
    this.corpHashMap = MilestoneHelper.buildActiveCorporateStructureHashMap();
    User user = (User)context.getSessionValue("user");
    Vector vUserCompanies = SelectionHandler.filterSelectionActiveCompanies(user.getUserId(), MilestoneHelper.getUserCompanies(context));
    Vector vUserEnvironments = SelectionHandler.filterSelectionEnvironments(vUserCompanies);
    result.append("\n");
    result.append("var familyArray = new Array();\n");
    result.append("var environmentArray = new Array();\n");
    result.append("var companyArray = new Array();\n");
    int arrayIndex = 0;
    result.append("familyArray[0] = new Array( 0, 'All');\n");
    result.append("environmentArray[0] = new Array( 0, 'All');\n");
    Vector vUserFamilies = SelectionHandler.filterCSO(MilestoneHelper.getNonSecureActiveUserFamilies(context));
    for (int l = 0; l < vUserFamilies.size(); l++) {
      Family family = (Family)vUserFamilies.elementAt(l);
      int structureId = family.getStructureID();
      boolean familyVal = true;
      if (this.corpHashMap.containsKey(new Integer(structureId)))
        familyVal = false; 
      if (family != null && familyVal) {
        result.append("familyArray[");
        result.append(family.getStructureID());
        result.append("] = new Array(");
        boolean foundZeroth = false;
        Vector environmentVector = new Vector();
        Vector environments = getUserEnvironmentsFromFamily(family, context);
        if (environments != null) {
          result.append(" 0, 'All',");
          for (int j = 0; j < environments.size(); j++) {
            Environment environment = (Environment)environments.elementAt(j);
            int structureIdc = environment.getStructureID();
            boolean boolVal = true;
            if (this.corpHashMap.containsKey(new Integer(structureIdc)))
              boolVal = false; 
            if (environment.getParentID() == family.getStructureID() && boolVal) {
              if (foundZeroth)
                result.append(','); 
              result.append(' ');
              result.append(environment.getStructureID());
              result.append(", '");
              result.append(MilestoneHelper.urlEncode(environment.getName()));
              result.append('\'');
              foundZeroth = true;
              environmentVector.addElement(environment);
            } 
          } 
          if (foundZeroth) {
            result.append(");\n");
          } else {
            result.append(" 0, 'All');\n");
          } 
          for (int k = 0; k < environmentVector.size(); k++) {
            Environment environmentNode = (Environment)environmentVector.elementAt(k);
            result.append("environmentArray[");
            result.append(environmentNode.getStructureID());
            result.append("] = new Array(");
            Vector companyVector = new Vector();
            Vector companies = environmentNode.getChildren();
            if (companies != null) {
              result.append(" 0, 'All',");
              boolean foundZeroth2 = false;
              for (int m = 0; m < companies.size(); m++) {
                Company company = (Company)companies.elementAt(m);
                int structureIdc = company.getStructureID();
                boolean boolVal = true;
                if (this.corpHashMap.containsKey(new Integer(structureIdc)))
                  boolVal = false; 
                if (company.getParentID() == environmentNode.getStructureID() && boolVal) {
                  if (foundZeroth2)
                    result.append(','); 
                  result.append(' ');
                  result.append(company.getStructureID());
                  result.append(", '");
                  result.append(MilestoneHelper.urlEncode(company.getName()));
                  result.append('\'');
                  foundZeroth2 = true;
                  companyVector.addElement(company);
                } 
              } 
              if (foundZeroth2) {
                result.append(");\n");
              } else {
                result.append(" 0, 'All');\n");
              } 
              for (int n = 0; n < companyVector.size(); n++) {
                Company companyNode = (Company)companyVector.elementAt(n);
                result.append("companyArray[");
                result.append(companyNode.getStructureID());
                result.append("] = new Array(");
                Vector divisions = companyNode.getChildren();
                boolean foundSecond2 = false;
                result.append(" 0, 'All',");
                for (int x = 0; x < divisions.size(); x++) {
                  Division division = (Division)divisions.elementAt(x);
                  int structureIds = division.getStructureID();
                  boolean boolRes = true;
                  if (this.corpHashMap.containsKey(new Integer(structureIds)))
                    boolRes = false; 
                  if (division != null && boolRes) {
                    Vector labels = division.getChildren();
                    for (int y = 0; y < labels.size(); y++) {
                      Label labelNode = (Label)labels.get(y);
                      int structureIdl = labelNode.getStructureID();
                      boolean boolVal = true;
                      if (this.corpHashMap.containsKey(new Integer(structureIdl)))
                        boolVal = false; 
                      if (labelNode.getParentID() == division.getStructureID() && boolVal) {
                        if (foundSecond2)
                          result.append(','); 
                        result.append(' ');
                        result.append(labelNode.getStructureID());
                        result.append(", '");
                        result.append(MilestoneHelper.urlEncode(labelNode.getName()));
                        result.append('\'');
                        foundSecond2 = true;
                      } 
                    } 
                  } 
                } 
                if (foundSecond2) {
                  result.append(");\n");
                } else {
                  result.append(" 0, 'All');\n");
                } 
              } 
            } 
          } 
        } 
      } 
    } 
    this.corpHashMap = null;
    return result.toString();
  }
  
  private Vector getUserCompaniesFromFamily(Family family, Context context) {
    Vector userCompanies = MilestoneHelper.getUserCompanies(context);
    Vector result = new Vector();
    if (family != null) {
      Vector familyCompanies = family.getChildren();
      if (familyCompanies != null)
        for (int i = 0; i < familyCompanies.size(); i++) {
          Company familyCompany = (Company)familyCompanies.get(i);
          for (int j = 0; j < userCompanies.size(); j++) {
            Company userCompany = (Company)userCompanies.get(j);
            int structureId = userCompany.getStructureID();
            boolean resultStructure = true;
            if (this.corpHashMap.containsKey(new Integer(structureId)))
              resultStructure = false; 
            if (userCompany.getStructureID() == familyCompany.getStructureID() && resultStructure)
              result.add(familyCompany); 
          } 
        }  
    } 
    return result;
  }
  
  private Vector getUserEnvironmentsFromFamily(Family family, Context context) {
    Vector userEnvironments = MilestoneHelper.getUserEnvironments(context);
    Vector result = new Vector();
    if (family != null) {
      Vector familyEnvironments = family.getChildren();
      if (familyEnvironments != null)
        for (int i = 0; i < familyEnvironments.size(); i++) {
          Environment familyEnvironment = (Environment)familyEnvironments.get(i);
          for (int j = 0; j < userEnvironments.size(); j++) {
            Environment userEnvironment = (Environment)userEnvironments.get(j);
            int structureId = userEnvironment.getStructureID();
            boolean resultStructure = true;
            if (this.corpHashMap.containsKey(new Integer(structureId)))
              resultStructure = false; 
            if (userEnvironment.getStructureID() == familyEnvironment.getStructureID() && resultStructure)
              result.add(familyEnvironment); 
          } 
        }  
    } 
    return result;
  }
  
  protected int insertConfigHeader(DefaultTableLens table_contents, String title, int nextRow, int cols) {
    int COL_LINE_STYLE = 4097;
    int HEADER_FONT_SIZE = 12;
    table_contents.setObject(nextRow, 0, "");
    table_contents.setSpan(nextRow, 0, new Dimension(cols, 1));
    table_contents.setRowHeight(nextRow, 1);
    table_contents.setRowBackground(nextRow, Color.white);
    for (int col = -1; col < cols; col++) {
      table_contents.setColBorderColor(nextRow, col, Color.white);
      table_contents.setColBorder(nextRow, col, 4097);
    } 
    table_contents.setRowBorderColor(nextRow, Color.white);
    table_contents.setRowBorder(nextRow, 266240);
    table_contents.setColBorderColor(nextRow + 1, -1, Color.black);
    table_contents.setColBorder(nextRow + 1, -1, 4097);
    table_contents.setColBorderColor(nextRow + 1, cols - 1, Color.black);
    table_contents.setColBorder(nextRow + 1, cols - 1, 4097);
    table_contents.setRowBorderColor(nextRow + 1, Color.black);
    table_contents.setRowBorder(nextRow + 1, 266240);
    table_contents.setAlignment(nextRow + 1, 0, 2);
    table_contents.setSpan(nextRow + 1, 0, new Dimension(cols, 1));
    table_contents.setObject(nextRow + 1, 0, title);
    table_contents.setRowFont(nextRow + 1, new Font("Arial", 3, 12));
    nextRow += 2;
    table_contents.setObject(nextRow, 0, "");
    table_contents.setSpan(nextRow, 0, new Dimension(cols, 1));
    table_contents.setRowHeight(nextRow, 1);
    table_contents.setRowBackground(nextRow, Color.white);
    for (int col = -1; col < cols; col++) {
      table_contents.setColBorderColor(nextRow, col, Color.black);
      table_contents.setColBorder(nextRow, col, 4097);
    } 
    table_contents.setRowBorderColor(nextRow, Color.white);
    table_contents.setRowBorder(nextRow, 266240);
    nextRow++;
    return nextRow;
  }
  
  protected static int insertLightGrayHeader(DefaultTableLens table_contents, String title, int nextRow, int cols) {
    int COL_LINE_STYLE = 4097;
    int HEADER_FONT_SIZE = 10;
    table_contents.setObject(nextRow, 0, "");
    table_contents.setSpan(nextRow, 0, new Dimension(cols, 1));
    table_contents.setRowHeight(nextRow, 8);
    table_contents.setRowBackground(nextRow, Color.white);
    for (int col = -1; col < cols; col++) {
      table_contents.setColBorderColor(nextRow, col, Color.white);
      table_contents.setColBorder(nextRow, col, 4097);
    } 
    table_contents.setRowBorderColor(nextRow, Color.white);
    nextRow++;
    table_contents.setRowBorderColor(nextRow, Color.white);
    table_contents.setRowBorder(nextRow, 266240);
    table_contents.setObject(nextRow, 0, "");
    table_contents.setSpan(nextRow, 0, new Dimension(cols, 1));
    table_contents.setRowHeight(nextRow, 1);
    for (int col = -1; col < cols; col++) {
      table_contents.setColBorderColor(nextRow, col, Color.white);
      table_contents.setColBorder(nextRow, col, 4097);
    } 
    table_contents.setColBorderColor(nextRow + 1, -1, Color.white);
    table_contents.setColBorder(nextRow + 1, -1, 4097);
    table_contents.setColBorderColor(nextRow + 1, cols - 1, Color.white);
    table_contents.setColBorder(nextRow + 1, cols - 1, 4097);
    table_contents.setRowBorderColor(nextRow + 1, Color.lightGray);
    table_contents.setRowBorder(nextRow + 1, 266240);
    table_contents.setAlignment(nextRow + 1, 0, 1);
    table_contents.setSpan(nextRow + 1, 0, new Dimension(cols, 1));
    table_contents.setObject(nextRow + 1, 0, title);
    table_contents.setRowFont(nextRow + 1, new Font("Arial", 1, 10));
    table_contents.setRowBackground(nextRow + 1, Color.lightGray);
    table_contents.setRowForeground(nextRow + 1, Color.black);
    nextRow += 2;
    table_contents.setObject(nextRow, 0, "");
    table_contents.setSpan(nextRow, 0, new Dimension(cols, 1));
    table_contents.setRowHeight(nextRow, 1);
    table_contents.setRowBackground(nextRow, Color.white);
    for (int col = -1; col < cols; col++) {
      table_contents.setColBorderColor(nextRow, col, Color.white);
      table_contents.setColBorder(nextRow, col, 4097);
    } 
    table_contents.setRowBorderColor(nextRow, Color.white);
    table_contents.setRowBorder(nextRow, 266240);
    nextRow++;
    return nextRow;
  }
  
  public void sendReport(Context context) {
    try {
      HttpServletResponse sresponse = context.getResponse();
      Calendar cal = Calendar.getInstance();
      cal.add(5, -5);
      SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy hh:mm:ss 'GMT'");
      String currentDate = formatter.format(cal.getTime());
      sresponse.setHeader("Last-Modified", currentDate);
      cal.add(5, -15);
      String expireDate = formatter.format(cal.getTime());
      sresponse.setHeader("Expires", expireDate);
      String reportName = (context.getSessionValue("currentReportName") != null) ? (String)context.getSessionValue("currentReportName") : "";
      if (reportName.equalsIgnoreCase("Prod.Stat.")) {
        StyleSheet report = (StyleSheet)context.getSessionValue("currentReport");
        String reportFilename = "report.pdf";
        if (report != null) {
          if (ie55) {
            sresponse.setHeader("extension", "pdf");
            sresponse.setContentType("application/pdf");
            sresponse.setHeader("Content-disposition", "attachment; filename=" + reportFilename);
          } else {
            sresponse.setHeader("extension", "pdf");
            sresponse.setContentType("application/force-download");
            sresponse.setHeader("Content-disposition", "attachment; filename=" + reportFilename);
          } 
          ServletOutputStream servletOutputStream = sresponse.getOutputStream();
          servletOutputStream.flush();
          PDF4Generator pdfGenerator = new PDF4Generator(servletOutputStream);
          pdfGenerator.generate(report);
          servletOutputStream.close();
        } else {
          downloadDone(context);
        } 
      } else if (reportName.equalsIgnoreCase("TaskDueDt")) {
        StyleSheet report = (StyleSheet)context.getSessionValue("currentReport");
        String reportFilename = "report.pdf";
        if (report != null) {
          if (ie55) {
            sresponse.setHeader("extension", "pdf");
            sresponse.setContentType("application/pdf");
            sresponse.setHeader("Content-disposition", "attachment; filename=" + reportFilename);
          } else {
            sresponse.setHeader("extension", "pdf");
            sresponse.setContentType("application/force-download");
            sresponse.setHeader("Content-disposition", "attachment; filename=" + reportFilename);
          } 
          ServletOutputStream servletOutputStream = sresponse.getOutputStream();
          servletOutputStream.flush();
          PDF4Generator pdfGenerator = new PDF4Generator(servletOutputStream);
          pdfGenerator.generate(report);
          servletOutputStream.close();
        } else {
          downloadDone(context);
        } 
      } else if (reportName.equalsIgnoreCase("TaskDueTit")) {
        StyleSheet report = (StyleSheet)context.getSessionValue("currentReport");
        String reportFilename = "report.pdf";
        if (report != null) {
          if (ie55) {
            sresponse.setHeader("extension", "pdf");
            sresponse.setContentType("application/pdf");
            sresponse.setHeader("Content-disposition", "attachment; filename=" + reportFilename);
          } else {
            sresponse.setHeader("extension", "pdf");
            sresponse.setContentType("application/force-download");
            sresponse.setHeader("Content-disposition", "attachment; filename=" + reportFilename);
          } 
          ServletOutputStream servletOutputStream = sresponse.getOutputStream();
          servletOutputStream.flush();
          PDF4Generator pdfGenerator = new PDF4Generator(servletOutputStream);
          pdfGenerator.generate(report);
          servletOutputStream.close();
        } else {
          downloadDone(context);
        } 
      } else if (reportName.equalsIgnoreCase("MCARelSchd")) {
        StyleSheet report = (StyleSheet)context.getSessionValue("currentReport");
        String reportFilename = "report.pdf";
        if (report != null) {
          if (ie55) {
            sresponse.setHeader("extension", "pdf");
            sresponse.setContentType("application/pdf");
            sresponse.setHeader("Content-disposition", "attachment; filename=" + reportFilename);
          } else {
            sresponse.setHeader("extension", "pdf");
            sresponse.setContentType("application/force-download");
            sresponse.setHeader("Content-disposition", "attachment; filename=" + reportFilename);
          } 
          ServletOutputStream servletOutputStream = sresponse.getOutputStream();
          servletOutputStream.flush();
          PDF4Generator pdfGenerator = new PDF4Generator(servletOutputStream);
          pdfGenerator.generate(report);
          servletOutputStream.close();
        } else {
          downloadDone(context);
        } 
      } else {
        XStyleSheet report = (XStyleSheet)context.getSessionValue("currentReport");
        String reportFilename = "report.pdf";
        if (report != null) {
          if (ie55) {
            sresponse.setHeader("extension", "pdf");
            sresponse.setContentType("application/pdf");
            sresponse.setHeader("Content-disposition", "attachment; filename=" + reportFilename);
          } else {
            sresponse.setHeader("extension", "pdf");
            sresponse.setContentType("application/pdf");
            sresponse.setHeader("Content-disposition", "attachment; filename=" + reportFilename);
          } 
          ServletOutputStream servletOutputStream = sresponse.getOutputStream();
          servletOutputStream.flush();
          PDF4Generator pdfGenerator = new PDF4Generator(servletOutputStream);
          if (reportName.equalsIgnoreCase("MexProdSch")) {
            Size a4Landscape = new Size(11.75D, 8.25D);
            pdfGenerator.setPageSize(a4Landscape);
          } 
          pdfGenerator.generate(report);
          servletOutputStream.close();
        } else {
          downloadDone(context);
        } 
      } 
    } catch (Exception exception) {}
  }
  
  public void downloadDone(Context context) {
    try {
      HttpServletResponse sresponse = context.getResponse();
      context.putDelivery("status", new String("done"));
      if (!ReportingServices.usingReportServicesByContext(context)) {
        context.includeJSP("status.jsp", "hiddenFrame");
        sresponse.setContentType("text/plain");
        sresponse.flushBuffer();
      } 
    } catch (Exception exception) {}
  }
  
  private boolean search(Dispatcher dispatcher, Context context, String command) {
    Notepad notepad = MilestoneHelper.getNotepadFromSession(3, context);
    notepad.setAllContents(null);
    notepad.setSelected(null);
    ReportManager.getInstance().setNotepadQuery(context, MilestoneSecurity.getUser(context).getUserId(), notepad);
    dispatcher.redispatch(context, "reports-editor");
    return true;
  }
  
  private boolean sort(Dispatcher dispatcher, Context context) {
    Notepad notepad = MilestoneHelper.getNotepadFromSession(3, context);
    if (notepad.getSearchQuery() == null || notepad.getSearchQuery().equals("")) {
      ReportManager.getInstance();
      notepad.setSearchQuery("SELECT DISTINCT report_id, report_name, file_name, description, report_owner FROM vi_Report_Config where report_status = 'ACTIVE' ");
    } 
    if (context.getParameter("OrderByDirection").equals("0")) {
      notepad.setOrderBy(" ORDER BY " + context.getParameter("OrderBy") + " ASC");
    } else {
      notepad.setOrderBy(" ORDER BY " + context.getParameter("OrderBy") + " DESC");
    } 
    notepad.setAllContents(null);
    Vector contents = new Vector();
    notepad = getReportsNotepad(contents, context, MilestoneSecurity.getUser(context).getUserId());
    notepad.setSelected(notepad.getSelected());
    dispatcher.redispatch(context, "reports-editor");
    return true;
  }
  
  public static MilestoneFormDropDownMenu getCorporateStructureDropDownMultSel(String name, Vector corporateVector, String selectedOption, boolean required, boolean blankFirst) {
    Vector values = new Vector();
    Vector menuText = new Vector();
    MilestoneFormDropDownMenu dropdown = null;
    boolean selectedFound = false;
    HashMap corpHashMap = MilestoneHelper.buildActiveCorporateStructureHashMap();
    int selectionInt = 0;
    try {
      selectionInt = Integer.parseInt(selectedOption);
    } catch (NumberFormatException numberFormatException) {}
    if (corporateVector != null) {
      Vector sortedVector = new Vector();
      sortedVector = MilestoneHelper.sortCorporateVectorByName(corporateVector);
      if (blankFirst)
        if (name.equalsIgnoreCase("family")) {
          values.addElement("0");
          menuText.addElement("All");
        } else {
          values.addElement("0");
          menuText.addElement(" All");
        }  
      CorporateStructureObject cso = null;
      for (int i = 0; i < sortedVector.size(); i++) {
        cso = (CorporateStructureObject)sortedVector.elementAt(i);
        if (cso != null && !corpHashMap.containsKey(new Integer(cso.getStructureID()))) {
          values.addElement(cso.getStructureID());
          menuText.addElement(cso.getName());
          if (cso.getStructureID() == selectionInt)
            selectedFound = true; 
        } 
      } 
      if (!selectedFound && selectedOption != null) {
        CorporateStructureObject csoSelected = 
          (CorporateStructureObject)MilestoneHelper.getStructureObject(selectionInt);
        if (csoSelected != null) {
          values.addElement(csoSelected.getStructureID());
          menuText.addElement(csoSelected.getName());
        } 
      } 
      String[] arrayValues = new String[values.size()];
      String[] arrayMenuText = new String[menuText.size()];
      arrayValues = (String[])values.toArray(arrayValues);
      arrayMenuText = (String[])menuText.toArray(arrayMenuText);
      dropdown = new MilestoneFormDropDownMenu(name, 
          selectedOption, 
          arrayValues, 
          arrayMenuText, 
          required);
    } else {
      dropdown = new MilestoneFormDropDownMenu(name, "", "", required);
    } 
    corpHashMap = null;
    return dropdown;
  }
  
  public static MilestoneFormDropDownMenu getCorporateStructureDropDownDuplicates(String name, Vector corporateVector, String selectedOption, boolean required, boolean blankFirst, boolean activesOnly) {
    Vector values = new Vector();
    MilestoneVector menuText = new MilestoneVector();
    MilestoneFormDropDownMenu dropdown = null;
    boolean selectedFound = false;
    HashMap corpHashMap = MilestoneHelper.buildActiveCorporateStructureHashMap();
    int selectionInt = 0;
    try {
      selectionInt = Integer.parseInt(selectedOption);
    } catch (NumberFormatException numberFormatException) {}
    if (corporateVector != null) {
      Vector sortedVector = new Vector();
      sortedVector = MilestoneHelper.sortCorporateVectorByName(corporateVector);
      if (blankFirst)
        if (name.equalsIgnoreCase("family")) {
          values.addElement("0");
          menuText.addElement("All");
        } else {
          values.addElement("0");
          menuText.addElement(" All");
        }  
      CorporateStructureObject cso = null;
      for (int i = 0; i < sortedVector.size(); i++) {
        cso = (CorporateStructureObject)sortedVector.elementAt(i);
        if (activesOnly) {
          if (cso instanceof Label) {
            Label csoLabel = (Label)cso;
            if (!csoLabel.getActive())
              continue; 
          } 
          if (cso instanceof Division) {
            Division csoDiv = (Division)cso;
            if (!csoDiv.getActive())
              continue; 
          } 
          if (cso instanceof Company) {
            Company csoCo = (Company)cso;
            if (!csoCo.getActive())
              continue; 
          } 
          if (cso instanceof Environment) {
            Environment csoEnv = (Environment)cso;
            if (!csoEnv.getActive())
              continue; 
          } 
          if (cso instanceof Family) {
            Family csoFam = (Family)cso;
            if (!csoFam.getActive())
              continue; 
          } 
        } 
        if (cso != null && !corpHashMap.containsKey(new Integer(cso.getStructureID())))
          if (!menuText.contains(cso.getName())) {
            values.addElement(cso.getStructureID());
            menuText.addElement(cso.getName());
            if (cso.getStructureID() == selectionInt)
              selectedFound = true; 
          } else {
            int index = menuText.indexOf(cso.getName());
            if (index != -1) {
              String value = (String)values.get(index);
              values.set(index, String.valueOf(value) + "," + cso.getStructureID());
              if (cso.getStructureID() == selectionInt)
                selectedFound = true; 
            } 
          }  
        continue;
      } 
      if (!selectedFound && selectedOption != null) {
        CorporateStructureObject csoSelected = 
          (CorporateStructureObject)MilestoneHelper.getStructureObject(selectionInt);
        if (csoSelected != null) {
          values.addElement(csoSelected.getStructureID());
          menuText.addElement(csoSelected.getName());
        } 
      } 
      String[] arrayValues = new String[values.size()];
      String[] arrayMenuText = new String[menuText.size()];
      arrayValues = (String[])values.toArray(arrayValues);
      arrayMenuText = (String[])menuText.toArray(arrayMenuText);
      dropdown = new MilestoneFormDropDownMenu(name, 
          selectedOption, 
          arrayValues, 
          arrayMenuText, 
          required);
    } else {
      dropdown = new MilestoneFormDropDownMenu(name, "", "", required);
    } 
    corpHashMap = null;
    return dropdown;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\ReportHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */