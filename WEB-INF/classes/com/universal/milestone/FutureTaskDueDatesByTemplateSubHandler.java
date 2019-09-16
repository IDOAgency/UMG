package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Cache;
import com.universal.milestone.DatePeriod;
import com.universal.milestone.Day;
import com.universal.milestone.Family;
import com.universal.milestone.Form;
import com.universal.milestone.FutureTaskDueDatesByTemplateSubHandler;
import com.universal.milestone.JdbcConnector;
import com.universal.milestone.MilestoneFormDropDownMenu;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.Report;
import com.universal.milestone.ReportSelectionsHelper;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.StatusJSPupdate;
import inetsoft.report.SectionBand;
import inetsoft.report.StyleFont;
import inetsoft.report.XStyleSheet;
import inetsoft.report.lens.DefaultSectionLens;
import inetsoft.report.lens.DefaultTableLens;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

public class FutureTaskDueDatesByTemplateSubHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "fTaskDue";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public static final int NUM_COLUMNS = 14;
  
  public static final int addDays = 7;
  
  public static final int NUM_DATE_COLS = 12;
  
  public DefaultTableLens templateLens;
  
  public DefaultTableLens taskLens;
  
  public DefaultTableLens rowCountTable;
  
  public DefaultSectionLens group;
  
  public StatusJSPupdate statusJSPupdate;
  
  public int row;
  
  public FutureTaskDueDatesByTemplateSubHandler(GeminiApplication application) {
    this.templateLens = null;
    this.taskLens = null;
    this.rowCountTable = null;
    this.group = null;
    this.statusJSPupdate = null;
    this.row = 0;
    this.application = application;
    this.log = application.getLog("fTaskDue");
  }
  
  public String getDescription() { return "Future Task Due Dates By Template"; }
  
  protected void fillFutureTaskDueDatesByTemplateSubHandler(XStyleSheet report, Context context) {
    this.statusJSPupdate = new StatusJSPupdate(context);
    Color SHADED_AREA_COLOR = Color.lightGray;
    int COL_LINE_STYLE = 4097;
    int HEADER_FONT_SIZE = 12;
    double ldLineVal = 0.3D;
    int separatorLineStyle = 266240;
    Color separatorLineColor = Color.black;
    int tableHeaderLineStyle = 266240;
    Color tableHeaderLineColor = Color.black;
    int tableRowLineStyle = 4097;
    Color tableRowLineColor = new Color(208, 206, 206, 0);
    ArrayList taskDueTemplates = getAllTemplatesForUser(context);
    int totalCount = 0;
    int recordCount = 0;
    if (taskDueTemplates.size() > 0) {
      TemplateObj templateObj = (TemplateObj)taskDueTemplates.get(0);
      totalCount = templateObj.totalCount;
    } 
    try {
      this.rowCountTable = new DefaultTableLens(1, 20000);
      SectionBand hbandType = new SectionBand(report);
      SectionBand body = new SectionBand(report);
      SectionBand spacer = new SectionBand(report);
      spacer.setVisible(true);
      spacer.setHeight(0.05F);
      spacer.setShrinkToFit(false);
      spacer.setBottomBorder(0);
      Form reportForm = (Form)context.getSessionValue("reportForm");
      Calendar beginDueDate = (reportForm.getStringValue("beginDate") != null && 
        reportForm.getStringValue("beginDate").length() > 0) ? 
        MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
      Calendar endDueDate = (reportForm.getStringValue("endDate") != null && 
        reportForm.getStringValue("endDate").length() > 0) ? 
        MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : null;
      report.setElement("startdate", MilestoneHelper.getFormatedDate(beginDueDate));
      report.setElement("enddate", MilestoneHelper.getFormatedDate(endDueDate));
      SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
      String todayLong = formatter.format(new Date());
      report.setElement("bottomdate", todayLong);
      this.statusJSPupdate.updateStatus(totalCount, recordCount, "start_report", 0);
      for (int i = 0; i < taskDueTemplates.size(); i++) {
        int nextRow = 0;
        TemplateObj templateObj = (TemplateObj)taskDueTemplates.get(i);
        if (templateObj.taskObjs == null || templateObj.taskObjs.size() == 0) {
          recordCount++;
          this.statusJSPupdate.updateStatus(totalCount, recordCount, "start_report", 0);
        } 
        int numDateCols = templateObj.streetDates.size();
        int startDateIndex = 0;
        boolean isFirstTime = true;
        boolean shade = true;
        boolean addBlankRow = false;
        boolean addCont = false;
        updateSortVar(templateObj);
        Collections.sort(templateObj.taskObjs);
        do {
          int toColumn = (numDateCols > 12) ? (startDateIndex + 12) : (startDateIndex + numDateCols);
          printTemplateHdr(report, context, templateObj, startDateIndex, toColumn, addCont);
          ArrayList taskObjs = templateObj.taskObjs;
          this.taskLens = new DefaultTableLens(taskObjs.size(), 14);
          this.row = 0;
          for (int t = 0; t < taskObjs.size(); t++) {
            if (isFirstTime) {
              recordCount++;
              this.statusJSPupdate.updateStatus(totalCount, recordCount, "start_report", 0);
            } 
            TaskObj taskObj = (TaskObj)taskObjs.get(t);
            printTemplateDetail(report, context, taskObj, startDateIndex, toColumn, shade, 
                templateObj, addCont);
            shade = !shade;
            this.row++;
          } 
          numDateCols -= 12;
          startDateIndex += 12;
          isFirstTime = false;
          shade = true;
          addCont = true;
          if (numDateCols <= 0)
            continue; 
          report.addPageBreak();
        } while (numDateCols > 0);
        if (i < taskDueTemplates.size() - 1)
          report.addPageBreak(); 
      } 
    } catch (Exception e) {
      System.out.println(">>>>>>>>FutureTaskDueDatesByTemplateSubHandler: exception: " + e);
      e.printStackTrace();
    } 
    this.statusJSPupdate = null;
  }
  
  public void printTemplateHdr(XStyleSheet report, Context context, TemplateObj templateObj, int dateIndex, int toColumn, boolean addCont) {
    this.rowCountTable = new DefaultTableLens(2, 10000);
    SectionBand hbandType = new SectionBand(report);
    SectionBand body = new SectionBand(report);
    this.group = null;
    int lensRows = 3;
    this.templateLens = new DefaultTableLens(lensRows, 14);
    int nextRow = 0;
    this.templateLens.setColAlignment(0, 1);
    this.templateLens.setColAlignment(1, 1);
    this.templateLens.setColAlignment(2, 4);
    this.templateLens.setColAlignment(3, 4);
    this.templateLens.setColAlignment(4, 4);
    this.templateLens.setColAlignment(5, 4);
    this.templateLens.setColAlignment(6, 4);
    this.templateLens.setColAlignment(7, 4);
    this.templateLens.setColAlignment(8, 4);
    this.templateLens.setColAlignment(9, 4);
    this.templateLens.setColAlignment(10, 4);
    this.templateLens.setColAlignment(11, 4);
    this.templateLens.setColAlignment(12, 4);
    this.templateLens.setColAlignment(13, 4);
    this.templateLens.setColBorderColor(Color.white);
    this.templateLens.setRowBorderColor(Color.white);
    this.templateLens.setRowBorder(0);
    this.templateLens.setColBorder(0);
    this.templateLens.setColWidth(0, 200);
    this.templateLens.setColWidth(1, 50);
    this.templateLens.setColWidth(2, 50);
    this.templateLens.setColWidth(3, 50);
    this.templateLens.setColWidth(4, 50);
    this.templateLens.setColWidth(5, 50);
    this.templateLens.setColWidth(6, 50);
    this.templateLens.setColWidth(7, 50);
    this.templateLens.setColWidth(8, 50);
    this.templateLens.setColWidth(9, 50);
    this.templateLens.setColWidth(10, 50);
    this.templateLens.setColWidth(11, 50);
    this.templateLens.setColWidth(12, 50);
    this.templateLens.setColWidth(13, 50);
    this.templateLens.setSpan(nextRow, 0, new Dimension(7, 1));
    if (!addCont) {
      this.templateLens.setObject(nextRow, 0, templateObj.name);
    } else {
      this.templateLens.setObject(nextRow, 0, String.valueOf(templateObj.name) + " - Continued");
    } 
    this.templateLens.setFont(nextRow, 0, new StyleFont("Arial", 1, 12));
    nextRow++;
    this.templateLens.setSpan(nextRow, 1, new Dimension(1, 1));
    this.templateLens.setObject(nextRow, 1, "Street Date");
    this.templateLens.setFont(nextRow, 1, new StyleFont("Arial", 1, 8));
    this.templateLens.setRowBorder(nextRow, 1, 4097);
    this.templateLens.setRowBorderColor(nextRow, 1, Color.black);
    this.templateLens.setColBorder(1, 0);
    int col = 2;
    for (int x = dateIndex; x < toColumn; x++) {
      this.templateLens.setObject(nextRow, col, MilestoneHelper.getFormatedDate((Calendar)templateObj.streetDates.get(x)));
      this.templateLens.setFont(nextRow, col, new StyleFont("Arial", 1, 9));
      this.templateLens.setRowBorder(nextRow, col, 4097);
      this.templateLens.setRowBorderColor(nextRow, col, Color.black);
      col++;
    } 
    nextRow++;
    this.templateLens.setObject(nextRow, 0, "Task Description");
    this.templateLens.setFont(nextRow, 0, new Font("Arial", 1, 10));
    this.templateLens.setSpan(nextRow, 1, new Dimension(1, 1));
    this.templateLens.setObject(nextRow, 1, "Wks To Rel");
    this.templateLens.setFont(nextRow, 1, new Font("Arial", 1, 7));
    this.templateLens.setColBorder(1, 4097);
    this.templateLens.setColBorderColor(1, Color.black);
    this.templateLens.setColBorder(nextRow - 1, 1, 0);
    body = new SectionBand(report);
    body.setHeight(1.2F);
    body.addTable(this.templateLens, new Rectangle(800, 100));
    body.setBottomBorder(0);
    body.setTopBorder(0);
    body.setShrinkToFit(true);
    body.setVisible(true);
    this.group = new DefaultSectionLens(null, this.group, body);
    report.addSection(this.group, this.rowCountTable);
    this.group = null;
  }
  
  public void printTemplateDetail(XStyleSheet report, Context context, TaskObj taskObj, int dateIndex, int toColumn, boolean shade, TemplateObj templateObj, boolean addCont) {
    SectionBand body = new SectionBand(report);
    int nextRow = 0;
    if (this.row > 29) {
      this.taskLens = new DefaultTableLens(4, 14);
    } else {
      this.taskLens = new DefaultTableLens(1, 14);
    } 
    this.taskLens.setRowHeight(14);
    this.taskLens.setColAlignment(0, 1);
    this.taskLens.setColAlignment(1, 1);
    this.taskLens.setColAlignment(2, 4);
    this.taskLens.setColAlignment(3, 4);
    this.taskLens.setColAlignment(4, 4);
    this.taskLens.setColAlignment(5, 4);
    this.taskLens.setColAlignment(6, 4);
    this.taskLens.setColAlignment(7, 4);
    this.taskLens.setColAlignment(8, 4);
    this.taskLens.setColAlignment(9, 4);
    this.taskLens.setColAlignment(10, 4);
    this.taskLens.setColAlignment(11, 4);
    this.taskLens.setColAlignment(12, 4);
    this.taskLens.setColAlignment(13, 4);
    this.taskLens.setColBorderColor(Color.white);
    this.taskLens.setRowBorderColor(Color.white);
    this.taskLens.setRowBorder(0);
    this.taskLens.setColBorder(0);
    this.taskLens.setColWidth(0, 200);
    this.taskLens.setColWidth(1, 50);
    this.taskLens.setColWidth(2, 50);
    this.taskLens.setColWidth(3, 50);
    this.taskLens.setColWidth(4, 50);
    this.taskLens.setColWidth(5, 50);
    this.taskLens.setColWidth(6, 50);
    this.taskLens.setColWidth(7, 50);
    this.taskLens.setColWidth(8, 50);
    this.taskLens.setColWidth(9, 50);
    this.taskLens.setColWidth(10, 50);
    this.taskLens.setColWidth(11, 50);
    this.taskLens.setColWidth(12, 50);
    this.taskLens.setColWidth(13, 50);
    String fontName = "Arial";
    if (this.row > 29) {
      this.row = 0;
      this.taskLens.setSpan(nextRow, 0, new Dimension(7, 1));
      if (!addCont) {
        this.taskLens.setObject(nextRow, 0, templateObj.name);
      } else {
        this.taskLens.setObject(nextRow, 0, String.valueOf(templateObj.name) + " - Continued");
      } 
      this.taskLens.setFont(nextRow, 0, new StyleFont("Arial", 1, 12));
      nextRow++;
      this.taskLens.setSpan(nextRow, 1, new Dimension(1, 1));
      this.taskLens.setObject(nextRow, 1, "Street Date");
      this.taskLens.setFont(nextRow, 1, new StyleFont("Arial", 1, 8));
      this.taskLens.setRowBorder(nextRow, 1, 4097);
      this.taskLens.setRowBorderColor(nextRow, 1, Color.black);
      this.taskLens.setColBorder(1, 0);
      int cols = 2;
      for (int y = dateIndex; y < toColumn; y++) {
        this.taskLens.setObject(nextRow, cols, MilestoneHelper.getFormatedDate((Calendar)templateObj.streetDates.get(y)));
        this.taskLens.setFont(nextRow, cols, new StyleFont("Arial", 1, 9));
        this.taskLens.setRowBorder(nextRow, cols, 4097);
        this.taskLens.setRowBorderColor(nextRow, cols, Color.black);
        cols++;
      } 
      nextRow++;
      this.taskLens.setObject(nextRow, 0, "Task Description");
      this.taskLens.setFont(nextRow, 0, new Font("Arial", 1, 10));
      this.taskLens.setSpan(nextRow, 1, new Dimension(1, 1));
      this.taskLens.setObject(nextRow, 1, "Wks To Rel");
      this.taskLens.setFont(nextRow, 1, new Font("Arial", 1, 7));
      this.taskLens.setColBorder(1, 4097);
      this.taskLens.setColBorderColor(1, Color.black);
      this.taskLens.setColBorder(nextRow - 1, 1, 0);
      nextRow++;
    } 
    if (shade)
      this.taskLens.setRowBackground(nextRow, Color.lightGray); 
    this.taskLens.setObject(nextRow, 0, taskObj.name);
    if (!shade) {
      this.taskLens.setFont(nextRow, 0, new Font(fontName, 0, 8));
    } else {
      this.taskLens.setFont(nextRow, 0, new Font(fontName, 1, 8));
    } 
    String dayStr = (new Day(taskObj.dayOfWeek)).getDay();
    String weeks = " ";
    if (!dayStr.equalsIgnoreCase("SOL"))
      weeks = String.valueOf(weeks) + (new Integer(taskObj.weeksToRelease)).toString(); 
    this.taskLens.setObject(nextRow, 1, String.valueOf(dayStr) + weeks);
    if (!shade) {
      this.taskLens.setFont(nextRow, 1, new Font(fontName, 0, 8));
    } else {
      this.taskLens.setFont(nextRow, 1, new Font(fontName, 1, 8));
    } 
    this.taskLens.setColBorder(1, 4097);
    this.taskLens.setColBorderColor(1, Color.black);
    int col = 2;
    for (int x = dateIndex; x < toColumn; x++) {
      String dueDate = MilestoneHelper.getFormatedDate((Calendar)taskObj.dueDates.get(x));
      this.taskLens.setObject(nextRow, col, !dueDate.equals("") ? dueDate : "n/a");
      if (!shade) {
        this.taskLens.setFont(nextRow, col, new Font(fontName, 0, 8));
      } else {
        this.taskLens.setFont(nextRow, col, new Font(fontName, 1, 8));
      } 
      col++;
    } 
    body = new SectionBand(report);
    body.setHeight(1.5F);
    body.addTable(this.taskLens, new Rectangle(800, 800));
    body.setBottomBorder(0);
    body.setTopBorder(0);
    body.setShrinkToFit(true);
    body.setVisible(true);
    this.group = new DefaultSectionLens(null, this.group, body);
    report.addSection(this.group, this.rowCountTable);
    this.group = null;
  }
  
  public ArrayList getAllTemplatesForUser(Context context) {
    HashMap weekDayConv = buildDayConvTable();
    StringBuffer query = new StringBuffer();
    Form reportForm = (Form)context.getSessionValue("reportForm");
    Report report = (Report)context.getSessionValue("Report");
    query.append("SELECT hdr.[template_id] as hdrTemplate_id, hdr.[name] as templateName, hdr.[owner], dtl.[task_id], task.[name] as taskName, task.[day_of_week], task.[weeks_to_release] FROM vi_Template_Header hdr INNER JOIN vi_Template_Detail dtl ON hdr.template_id = dtl.template_id INNER JOIN vi_Task task ON dtl.task_id = task.task_id ");
    try {
      MilestoneFormDropDownMenu templateList = (MilestoneFormDropDownMenu)reportForm.getElement("templatesList");
      if (templateList != null) {
        ArrayList templateIdStr = templateList.getStringValues();
        if (templateIdStr != null)
          for (int x = 0; x < templateIdStr.size(); x++) {
            if (x == 0) {
              query.append(" WHERE ");
            } else {
              query.append(" OR ");
            } 
            query.append(" hdr.template_id = " + (String)templateIdStr.get(x));
          }  
      } 
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("<<< template vector execption " + e.getMessage());
    } 
    String strUmlKey = (reportForm.getStringValue("umlKeyTask") != null) ? reportForm.getStringValue("umlKeyTask") : "";
    if (!strUmlKey.equalsIgnoreCase("") && !strUmlKey.trim().equalsIgnoreCase("all")) {
      int intUml = -1;
      Vector theFamilies = Cache.getFamilies();
      for (int i = 0; i < theFamilies.size(); i++) {
        Family family = (Family)theFamilies.get(i);
        if (family.getName().trim().equalsIgnoreCase("UML")) {
          intUml = family.getStructureID();
          break;
        } 
      } 
      if (intUml > 0)
        if (strUmlKey.equalsIgnoreCase("UML")) {
          query.append(" AND task.owner = " + intUml);
        } else {
          query.append(" AND task.owner != " + intUml);
        }  
    } 
    String[] strFamily = ReportSelectionsHelper.getMultiSelectionListValues("family", reportForm);
    if (strFamily != null && strFamily.length > 0) {
      String famStr = "";
      for (int i = 0; i < strFamily.length; i++) {
        if (!strFamily[i].equals("0") && !strFamily[i].equals("") && !strFamily[i].equals("-1"))
          if (famStr.equals("")) {
            famStr = String.valueOf(famStr) + " AND hdr.owner in (" + strFamily[i];
          } else {
            famStr = String.valueOf(famStr) + "," + strFamily[i];
          }  
      } 
      if (!famStr.equals(""))
        famStr = String.valueOf(famStr) + ") "; 
      query.append(famStr);
    } 
    query.append(" AND task.active_flag = 1 ");
    query.append(" ORDER BY templateName, hdrTemplate_id, weeks_to_release desc, taskName ");
    JdbcConnector connector = MilestoneHelper.getConnector(query.toString());
    connector.setForwardOnly(false);
    connector.runQuery();
    int totalCount = 0;
    totalCount = connector.getRowCount();
    this.statusJSPupdate.updateStatus(0, 0, "start_gathering", 10);
    int recordCount = 0;
    Calendar effDateFrom = (reportForm.getStringValue("beginDate") != null && 
      reportForm.getStringValue("beginDate").length() > 0) ? 
      MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : 
      null;
    Calendar effDateTo = (reportForm.getStringValue("endDate") != null && 
      reportForm.getStringValue("endDate").length() > 0) ? 
      MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : 
      null;
    ArrayList templateObjs = new ArrayList();
    ArrayList taskObjs = new ArrayList();
    TemplateObj templateObj = null;
    TaskObj taskObj = null;
    ArrayList reportStreeDates = computeStreetDates(effDateFrom, effDateTo);
    int saveTemplateId = -99;
    boolean isFirstRecord = true;
    boolean hasSOL = false;
    while (connector.more()) {
      recordCount++;
      this.statusJSPupdate.updateStatus(totalCount, recordCount, "start_gathering", 0);
      try {
        int templateId = connector.getInt("hdrTemplate_id");
        if (templateId != saveTemplateId) {
          if (!isFirstRecord) {
            templateObj.taskObjs = taskObjs;
            templateObj.hasSOL = hasSOL;
            templateObjs.add(templateObj);
            taskObjs = new ArrayList();
            hasSOL = false;
          } 
          isFirstRecord = false;
          saveTemplateId = templateId;
          templateObj = new TemplateObj(this);
          templateObj.templateId = connector.getInt("dhrTemplate_id");
          templateObj.name = connector.getField("templateName", "");
          templateObj.streetDates = reportStreeDates;
          templateObj.totalCount = totalCount;
        } 
        taskObj = new TaskObj(this);
        taskObj.taskId = connector.getInt("task_Id");
        taskObj.name = connector.getField("taskName", "");
        taskObj.dayOfWeek = connector.getInt("day_of_week", -1);
        taskObj.weeksToRelease = connector.getInt("weeks_to_release", -1);
        if (taskObj.dayOfWeek == 9) {
          taskObj.dueDates = computeSOLdates(templateObj);
          taskObj.isSOL = true;
          hasSOL = true;
        } else {
          taskObj.dueDates = computeDueDates(effDateFrom, effDateTo, taskObj.weeksToRelease, 
              taskObj.dayOfWeek, weekDayConv, templateObj.streetDates.size());
        } 
        taskObjs.add(taskObj);
        taskObj = null;
        connector.next();
      } catch (Exception e) {
        e.printStackTrace();
        break;
      } 
    } 
    if (!isFirstRecord) {
      templateObj.taskObjs = taskObjs;
      templateObj.hasSOL = hasSOL;
      templateObjs.add(templateObj);
    } 
    connector.close();
    taskObjs = null;
    templateObj = null;
    taskObj = null;
    return templateObjs;
  }
  
  public ArrayList computeDueDates(Calendar effDateFrom, Calendar effDateTo, int weeksToRel, int dayOfWeek, HashMap weekDayConv, int numStreetDates) {
    ArrayList dueDates = new ArrayList();
    if (dayOfWeek < 0 || dayOfWeek > 7) {
      int dayOfTheWeek = effDateFrom.get(7);
      int liRest = weeksToRel - dayOfTheWeek + 1;
      int liOffset = 0;
      if (liRest >= 0)
        liOffset = (liRest / 5 + 1) * 2; 
      int days = weeksToRel - liOffset;
      Calendar dueDate = (Calendar)effDateFrom.clone();
      Calendar dueDateTo = (Calendar)effDateTo.clone();
      dueDate.add(5, -weeksToRel - liOffset);
      dueDateTo.add(5, -weeksToRel - liOffset);
      Calendar newDate = Calendar.getInstance();
      newDate.setTime(dueDate.getTime());
      dueDates.add(newDate);
      while (dueDate.before(dueDateTo) || dueDates.size() < numStreetDates) {
        dueDate.add(5, 7);
        newDate = Calendar.getInstance();
        newDate.setTime(dueDate.getTime());
        dueDates.add(newDate);
      } 
    } else {
      int days = weeksToRel * 7;
      Calendar dueDate = (Calendar)effDateFrom.clone();
      dueDate.add(5, days * -1);
      int calDay = dueDate.get(7);
      int convDay = calDay;
      if (weekDayConv.get(new Integer(dayOfWeek)) != null)
        convDay = ((Integer)weekDayConv.get(new Integer(dayOfWeek))).intValue(); 
      if (convDay != calDay)
        dueDate.add(5, convDay - calDay); 
      Calendar dueDateTo = (Calendar)effDateTo.clone();
      dueDateTo.add(5, days * -1);
      calDay = dueDateTo.get(7);
      if (convDay != calDay)
        dueDateTo.add(5, convDay - calDay); 
      Calendar newDate = Calendar.getInstance();
      newDate.setTime(dueDate.getTime());
      dueDates.add(newDate);
      while (dueDate.before(dueDateTo) || dueDates.size() < numStreetDates) {
        dueDate.add(5, 7);
        newDate = Calendar.getInstance();
        newDate.setTime(dueDate.getTime());
        dueDates.add(newDate);
      } 
    } 
    return dueDates;
  }
  
  public void updateSortVar(TemplateObj templateObj) {
    int sortCol = 0;
    if (templateObj.hasSOL)
      for (int x = 0; x < templateObj.taskObjs.size(); x++) {
        TaskObj taskObj = (TaskObj)templateObj.taskObjs.get(x);
        if (taskObj.isSOL) {
          for (int y = 0; y < taskObj.dueDates.size(); y++) {
            if (taskObj.dueDates.get(y) != null);
            String dueDate = MilestoneHelper.getFormatedDate((Calendar)taskObj.dueDates.get(y));
            if (!dueDate.equals("")) {
              sortCol = y;
              break;
            } 
          } 
          break;
        } 
      }  
    if (templateObj.taskObjs != null)
      for (int x = 0; x < templateObj.taskObjs.size(); x++) {
        TaskObj taskObj = (TaskObj)templateObj.taskObjs.get(x);
        if (taskObj != null && taskObj.dueDates != null && 
          taskObj.dueDates.get(sortCol) != null) {
          taskObj.compareDate.setTime(((Calendar)taskObj.dueDates.get(sortCol)).getTime());
          templateObj.taskObjs.set(x, taskObj);
        } 
      }  
  }
  
  public ArrayList computeStreetDates(Calendar effDateFrom, Calendar effDateTo) {
    ArrayList streetDates = new ArrayList();
    Calendar streetDate = (Calendar)effDateFrom.clone();
    Calendar newDate = Calendar.getInstance();
    newDate.setTime(streetDate.getTime());
    streetDates.add(newDate);
    while (streetDate.before(effDateTo)) {
      streetDate.add(5, 7);
      newDate = Calendar.getInstance();
      newDate.setTime(streetDate.getTime());
      streetDates.add(newDate);
    } 
    return streetDates;
  }
  
  public ArrayList computeSOLdates(TemplateObj templateObj) {
    ArrayList streetDates = templateObj.streetDates;
    ArrayList solDates = new ArrayList();
    for (int i = 0; i < streetDates.size(); i++) {
      Calendar streetDate = (Calendar)streetDates.get(i);
      DatePeriod datePeriod = MilestoneHelper.findDatePeriod(streetDate);
      if (datePeriod != null && datePeriod.getSolDate() != null) {
        Calendar newDate = Calendar.getInstance();
        newDate.setTime(datePeriod.getSolDate().getTime());
        solDates.add(newDate);
      } else {
        solDates.add(null);
      } 
    } 
    return solDates;
  }
  
  public HashMap buildDayConvTable() {
    HashMap dayConv = new HashMap();
    dayConv.put(new Integer(7), new Integer(1));
    dayConv.put(new Integer(1), new Integer(2));
    dayConv.put(new Integer(2), new Integer(3));
    dayConv.put(new Integer(3), new Integer(4));
    dayConv.put(new Integer(4), new Integer(5));
    dayConv.put(new Integer(5), new Integer(6));
    dayConv.put(new Integer(6), new Integer(7));
    dayConv.put(new Integer(0), new Integer(1));
    dayConv.put(new Integer(-1), new Integer(1));
    return dayConv;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\FutureTaskDueDatesByTemplateSubHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */