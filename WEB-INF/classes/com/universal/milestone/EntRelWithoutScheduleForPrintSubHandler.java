package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.EntRelWithoutScheduleForPrintSubHandler;
import com.universal.milestone.Form;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneHelper_2;
import com.universal.milestone.ReleasingFamily;
import com.universal.milestone.Schedule;
import com.universal.milestone.ScheduledTask;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.Selection;
import com.universal.milestone.SelectionManager;
import inetsoft.report.XStyleSheet;
import inetsoft.report.lens.DefaultTableLens;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.servlet.http.HttpServletResponse;

public class EntRelWithoutScheduleForPrintSubHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hCProd";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public EntRelWithoutScheduleForPrintSubHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hCProd");
  }
  
  public String getDescription() { return "Sub Report"; }
  
  protected static void fillEntRelWithoutScheduleForPrint(XStyleSheet report, Context context) {
    try {
      HttpServletResponse sresponse = context.getResponse();
      context.putDelivery("status", new String("start_gathering"));
      context.includeJSP("status.jsp", "hiddenFrame");
      sresponse.setContentType("text/plain");
      sresponse.flushBuffer();
    } catch (Exception exception) {}
    Vector selections = MilestoneHelper.getSelectionsForReport(context);
    int kount = 0;
    int size = selections.size();
    Vector temp_selections = MilestoneHelper.getSelectionsForReport(context);
    for (i = 0; i < selections.size(); i++) {
      Selection sel = (Selection)selections.elementAt(i);
      sel = SelectionManager.getInstance().getSelectionAndSchedule(sel.getSelectionID());
      if (sel.getSchedule() != null) {
        int temp_i = i - kount;
        temp_selections.remove(temp_i);
        kount++;
      } 
    } 
    selections = temp_selections;
    try {
      HttpServletResponse sresponse = context.getResponse();
      context.putDelivery("status", new String("start_report"));
      context.putDelivery("percent", new String("10"));
      context.includeJSP("status.jsp", "hiddenFrame");
      sresponse.setContentType("text/plain");
      sresponse.flushBuffer();
    } catch (Exception i) {
      Exception exception;
    } 
    try {
      Form reportForm = (Form)context.getSessionValue("reportForm");
      Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && 
        reportForm.getStringValue("beginDate").length() > 0) ? 
        MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
      Calendar endStDate = (reportForm.getStringValue("endDate") != null && 
        reportForm.getStringValue("endDate").length() > 0) ? 
        MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : null;
      report.setElement("crs_startdate", MilestoneHelper.getFormatedDate(beginStDate));
      report.setElement("crs_enddate", MilestoneHelper.getFormatedDate(endStDate));
      SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
      String todayLong = formatter.format(new Date());
      report.setElement("crs_bottomdate", todayLong);
      Hashtable selTable = MilestoneHelper.groupSelectionsByFamilyAndCompany(selections);
      Enumeration families = selTable.keys();
      Vector familyVector = new Vector();
      while (families.hasMoreElements())
        familyVector.addElement(families.nextElement()); 
      int numCompanies = 0;
      for (int i = 0; i < familyVector.size(); i++) {
        String familyName = (familyVector.elementAt(i) != null) ? (String)familyVector.elementAt(i) : "";
        Hashtable companyTable = (Hashtable)selTable.get(familyName);
        if (companyTable != null)
          numCompanies += companyTable.size(); 
      } 
      int numExtraRows = 1 + familyVector.size() * 3 + numCompanies;
      int numSelections = selections.size();
      int numRows = size + numExtraRows - kount;
      DefaultTableLens table_contents = new DefaultTableLens(numRows, 10);
      table_contents.setColBorder(0);
      table_contents.setRowBorderColor(Color.white);
      table_contents.setColWidth(0, 190);
      table_contents.setColWidth(1, 180);
      table_contents.setColWidth(2, 120);
      table_contents.setColWidth(3, 100);
      table_contents.setColWidth(4, 80);
      table_contents.setColWidth(5, 60);
      table_contents.setColWidth(6, 180);
      table_contents.setAlignment(0, 0, 33);
      table_contents.setAlignment(0, 1, 34);
      table_contents.setAlignment(0, 2, 34);
      table_contents.setAlignment(0, 3, 34);
      table_contents.setAlignment(0, 4, 34);
      table_contents.setAlignment(0, 5, 34);
      table_contents.setAlignment(0, 6, 34);
      table_contents.setHeaderRowCount(1);
      table_contents.setRowBorder(-1, 0);
      table_contents.setRowBorder(0, 266240);
      table_contents.setRowBorderColor(0, Color.black);
      table_contents.setRowAlignment(0, 32);
      table_contents.setObject(0, 0, "Artist");
      table_contents.setObject(0, 1, "Title");
      table_contents.setObject(0, 2, "UPC");
      table_contents.setObject(0, 3, "Local\nProduct #");
      table_contents.setObject(0, 4, "Rel. Family/\nLabel");
      table_contents.setObject(0, 5, "Street/Ship\nDate");
      table_contents.setObject(0, 6, "Comments");
      table_contents.setRowInsets(0, new Insets(0, 0, 0, 0));
      table_contents.setRowFont(0, new Font("Arial", 3, 11));
      int nextRow = 1;
      for (int n = 0; n < familyVector.size(); n++) {
        String family = (String)familyVector.elementAt(n);
        String familyHeaderText = !family.trim().equals("") ? family : "Other";
        table_contents.setObject(nextRow, 0, "");
        table_contents.setObject(nextRow, 1, "");
        table_contents.setObject(nextRow, 2, "");
        table_contents.setObject(nextRow, 3, "");
        table_contents.setObject(nextRow, 4, "");
        table_contents.setObject(nextRow, 5, "");
        table_contents.setObject(nextRow, 6, "");
        table_contents.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
        table_contents.setRowHeight(nextRow, 15);
        Hashtable companyTable = (Hashtable)selTable.get(family);
        if (companyTable != null) {
          Enumeration companies = companyTable.keys();
          Vector companiesVector = new Vector();
          while (companies.hasMoreElements())
            companiesVector.add((String)companies.nextElement()); 
          Vector sortedCompaniesVector = MilestoneHelper.sortStrings(companiesVector);
          for (int a = 0; a < sortedCompaniesVector.size(); a++) {
            String company = (String)sortedCompaniesVector.get(a);
            table_contents.setSpan(nextRow, 0, new Dimension(10, 1));
            table_contents.setObject(nextRow, 0, company);
            table_contents.setRowBorder(nextRow, 4097);
            table_contents.setRowBorderColor(nextRow, Color.black);
            table_contents.setRowFont(nextRow, new Font("Arial", 3, 14));
            table_contents.setRowBackground(nextRow, Color.black);
            table_contents.setRowForeground(nextRow, Color.white);
            nextRow++;
            selections = (Vector)companyTable.get(company);
            if (selections == null)
              selections = new Vector(); 
            MilestoneHelper.setSelectionSorting(selections, 14);
            Collections.sort(selections);
            MilestoneHelper.setSelectionSorting(selections, 4);
            Collections.sort(selections);
            MilestoneHelper.setSelectionSorting(selections, 3);
            Collections.sort(selections);
            MilestoneHelper.setSelectionSorting(selections, 1);
            Collections.sort(selections);
            MilestoneHelper.setSelectionSorting(selections, 8);
            Collections.sort(selections);
            MilestoneHelper.setSelectionSorting(selections, 6);
            Collections.sort(selections);
            int count = 2;
            int numRec = selections.size();
            int chunkSize = numRec / 10;
            for (int i = 0; i < selections.size(); i++) {
              try {
                int myPercent = i / chunkSize;
                if (myPercent > 1 && myPercent < 10)
                  count = myPercent; 
                HttpServletResponse sresponse = context.getResponse();
                context.putDelivery("status", new String("start_report"));
                context.putDelivery("percent", new String(String.valueOf(count * 10)));
                context.includeJSP("status.jsp", "hiddenFrame");
                sresponse.setContentType("text/plain");
                sresponse.flushBuffer();
              } catch (Exception exception) {}
              Selection sel = (Selection)selections.elementAt(i);
              sel = SelectionManager.getInstance().getSelectionAndSchedule(sel.getSelectionID());
              String status = (sel.getSelectionStatus() != null && sel.getSelectionStatus().getName() != null) ? 
                sel.getSelectionStatus().getName() : "";
              if (sel.getSchedule() == null) {
                String selectionNo = SelectionManager.getLookupObjectValue(sel.getPrefixID());
                if (selectionNo == null)
                  selectionNo = ""; 
                selectionNo = String.valueOf(selectionNo) + sel.getSelectionNo();
                String label = sel.getImprint();
                String RelFamily = ReleasingFamily.getName(sel.getReleaseFamilyId());
                String comment = (sel.getSelectionComments() != null) ? sel.getSelectionComments() : "";
                String streetDate = MilestoneHelper.getFormatedDate(sel.getStreetDate());
                status = (sel.getSelectionStatus() != null && sel.getSelectionStatus().getName() != null) ? 
                  sel.getSelectionStatus().getName() : "";
                if (status.equalsIgnoreCase("TBS")) {
                  streetDate = "TBS " + streetDate;
                } else if (status.equalsIgnoreCase("In The Works")) {
                  streetDate = "ITW " + streetDate;
                } 
                Schedule schedule = sel.getSchedule();
                Vector tasks = (schedule != null) ? schedule.getTasks() : null;
                ScheduledTask task = null;
                table_contents.setRowInsets(nextRow, new Insets(0, 0, 5, 0));
                table_contents.setObject(nextRow, 0, sel.getArtist());
                table_contents.setObject(nextRow, 1, sel.getTitle());
                String upc = sel.getUpc();
                upc = MilestoneHelper_2.getRMSReportFormat(upc, "UPC", sel.getIsDigital());
                table_contents.setObject(nextRow, 2, upc);
                table_contents.setObject(nextRow, 3, selectionNo);
                table_contents.setObject(nextRow, 4, String.valueOf(RelFamily) + "\n" + label);
                table_contents.setObject(nextRow, 5, streetDate);
                table_contents.setObject(nextRow, 6, comment);
                table_contents.setAlignment(nextRow, 3, 1);
                table_contents.setRowBorder(nextRow, 4097);
                table_contents.setRowBorderColor(nextRow, Color.lightGray);
                table_contents.setAlignment(nextRow, 0, 9);
                table_contents.setAlignment(nextRow, 1, 9);
                table_contents.setAlignment(nextRow, 2, 10);
                table_contents.setAlignment(nextRow, 3, 9);
                table_contents.setAlignment(nextRow, 4, 8);
                table_contents.setAlignment(nextRow, 5, 12);
                table_contents.setAlignment(nextRow, 6, 9);
                nextRow++;
              } 
            } 
          } 
        } 
      } 
      report.setElement("table_colheaders", table_contents);
    } catch (Exception i) {
      Exception e;
      System.out.println(">>>>>>>>ReportHandler.fillEntRelWithoutScheduleForPrint(): exception: " + e);
    } 
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\EntRelWithoutScheduleForPrintSubHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */