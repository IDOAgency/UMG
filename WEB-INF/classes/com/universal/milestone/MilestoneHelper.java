package WEB-INF.classes.com.universal.milestone;

import com.techempower.BasicHelper;
import com.techempower.ComponentLog;
import com.techempower.DataEntity;
import com.techempower.DatabaseConnector;
import com.techempower.EnhancedProperties;
import com.techempower.StringList;
import com.techempower.gemini.Context;
import com.techempower.gemini.FormCheckBox;
import com.techempower.gemini.FormDropDownMenu;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Acl;
import com.universal.milestone.Cache;
import com.universal.milestone.Company;
import com.universal.milestone.CompanyAcl;
import com.universal.milestone.CompanyManager;
import com.universal.milestone.CorpStructNameComparator;
import com.universal.milestone.CorpStructParentNameComparator;
import com.universal.milestone.CorporateStructureObject;
import com.universal.milestone.DateConverterComparator;
import com.universal.milestone.DatePeriod;
import com.universal.milestone.Day;
import com.universal.milestone.DayManager;
import com.universal.milestone.Division;
import com.universal.milestone.DivisionManager;
import com.universal.milestone.Environment;
import com.universal.milestone.EnvironmentAcl;
import com.universal.milestone.EnvironmentManager;
import com.universal.milestone.Family;
import com.universal.milestone.FamilyManager;
import com.universal.milestone.Form;
import com.universal.milestone.Genre;
import com.universal.milestone.JdbcConnector;
import com.universal.milestone.Label;
import com.universal.milestone.LabelManager;
import com.universal.milestone.LookupObject;
import com.universal.milestone.MilestoneConstants;
import com.universal.milestone.MilestoneFormDropDownMenu;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneSecurity;
import com.universal.milestone.MilestoneVector;
import com.universal.milestone.Notepad;
import com.universal.milestone.PrefixCode;
import com.universal.milestone.PriceCode;
import com.universal.milestone.PriceCodeManager;
import com.universal.milestone.ProductCategory;
import com.universal.milestone.ReleaseType;
import com.universal.milestone.ReleaseWeek;
import com.universal.milestone.ReleaseWeekManager;
import com.universal.milestone.ReleasingFamily;
import com.universal.milestone.Report;
import com.universal.milestone.ReportConfigManager;
import com.universal.milestone.ReportSelections;
import com.universal.milestone.Schedule;
import com.universal.milestone.ScheduleManager;
import com.universal.milestone.ScheduledTask;
import com.universal.milestone.Selection;
import com.universal.milestone.SelectionConfiguration;
import com.universal.milestone.SelectionManager;
import com.universal.milestone.SelectionStatus;
import com.universal.milestone.SelectionSubConfiguration;
import com.universal.milestone.StringComparator;
import com.universal.milestone.StringDateComparator;
import com.universal.milestone.Table;
import com.universal.milestone.TableManager;
import com.universal.milestone.Task;
import com.universal.milestone.TaskManager;
import com.universal.milestone.Template;
import com.universal.milestone.TemplateManager;
import com.universal.milestone.User;
import com.universal.milestone.UserManager;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.servlet.http.HttpServletResponse;

public class MilestoneHelper extends BasicHelper implements MilestoneConstants {
  public static final String COMPONENT_CODE = "help";
  
  protected static Vector bomSuppliers = null;
  
  protected static String databaseName = "Milestone";
  
  protected static String dbLoginName = "milestone";
  
  protected static String dbLoginPass = "milestone";
  
  protected static String urlPrefix = "jdbc:odbc:";
  
  protected static ComponentLog log;
  
  public static void configure(EnhancedProperties props, GeminiApplication application) {
    databaseName = props.getProperty("DatabaseName", databaseName);
    dbLoginName = props.getProperty("DatabaseLogin", dbLoginName);
    dbLoginPass = props.getProperty("DatabasePassword", dbLoginPass);
    urlPrefix = props.getProperty("JDBCURLPrefix", urlPrefix);
    log = application.getLog("help");
  }
  
  public static JdbcConnector createConnector(String query) {
    JdbcConnector connector = new JdbcConnector(urlPrefix, databaseName, query);
    connector.setForwardOnly(true);
    connector.setUsername(dbLoginName);
    connector.setPassword(dbLoginPass);
    return connector;
  }
  
  public static JdbcConnector createScrollableConnector(String query) {
    JdbcConnector connector = new JdbcConnector(urlPrefix, databaseName, query);
    connector.setForwardOnly(false);
    connector.setUsername(dbLoginName);
    connector.setPassword(dbLoginPass);
    return connector;
  }
  
  public static JdbcConnector getConnector(String query) { return createConnector(query); }
  
  public static JdbcConnector getScrollableConnector(String query) { return createScrollableConnector(query); }
  
  public static Vector getDataEntities(Class dataEntityClass, DatabaseConnector connector) {
    Vector toReturn = new Vector();
    while (connector.more()) {
      try {
        DataEntity dataEntity = (DataEntity)dataEntityClass.newInstance();
        dataEntity.initializeByVariables(connector);
        toReturn.addElement(dataEntity);
      } catch (Exception exception) {}
      connector.next();
    } 
    connector.close();
    return toReturn;
  }
  
  public static String getFormatedDate(Calendar pdateDate) {
    String strDate = "";
    if (pdateDate != null) {
      SimpleDateFormat formatter = new SimpleDateFormat("M/d/yy");
      strDate = formatter.format(pdateDate.getTime());
    } 
    return strDate;
  }
  
  public static String getCustomFormatedDate(Calendar pdateDate, String pstrFormat) {
    String strDate = "";
    if (pdateDate != null) {
      SimpleDateFormat formatter = new SimpleDateFormat(pstrFormat);
      strDate = formatter.format(pdateDate.getTime());
    } 
    return strDate;
  }
  
  public static String getFormatedDateNoYear(Calendar pdateDate) {
    String strDate = "";
    if (pdateDate != null) {
      SimpleDateFormat formatter = new SimpleDateFormat("M/d");
      strDate = formatter.format(pdateDate.getTime());
    } 
    return strDate;
  }
  
  public static String getShortDate(Calendar pdateDate) {
    String strDate = "";
    if (pdateDate != null) {
      SimpleDateFormat formatter = new SimpleDateFormat("M/d");
      strDate = formatter.format(pdateDate.getTime());
    } 
    return strDate;
  }
  
  public static String getLongDate(Calendar pdateDate) {
    String strDate = "";
    if (pdateDate != null && !pdateDate.equals("")) {
      SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM d hh:mm:ss 'ET' yyyy");
      strDate = formatter.format(pdateDate.getTime());
    } 
    return strDate.trim();
  }
  
  public static Calendar getDate(String date) {
    if (date == null)
      return null; 
    try {
      StringTokenizer tokenizer = new StringTokenizer(date, "-/.");
      String strMonth = tokenizer.nextToken();
      String strDay = tokenizer.nextToken();
      String strYear = tokenizer.nextToken();
      int month = Integer.parseInt(strMonth) - 1;
      int day = Integer.parseInt(strDay);
      int year = Integer.parseInt(strYear);
      if (year < 100)
        if (year > 50) {
          year += 1900;
        } else {
          year += 2000;
        }  
      Calendar cal = Calendar.getInstance();
      cal.clear(14);
      cal.set(year, month, day, 0, 0, 0);
      return cal;
    } catch (Exception exception) {
      return null;
    } 
  }
  
  public static Calendar getMYDate(String date) {
    if (date == null)
      return null; 
    try {
      StringTokenizer tokenizer = new StringTokenizer(date, "-/.");
      String strMonth = tokenizer.nextToken();
      String strYear = tokenizer.nextToken();
      int month = Integer.parseInt(strMonth) - 1;
      int year = Integer.parseInt(strYear);
      if (year < 100)
        if (year > 50) {
          year += 1900;
        } else {
          year += 2000;
        }  
      Calendar cal = Calendar.getInstance();
      cal.set(year, month, 1);
      return cal;
    } catch (Exception exception) {
      return null;
    } 
  }
  
  public static int getMonth(String date) {
    if (date == null)
      return 0; 
    int dateInt = 0;
    try {
      if (date.equalsIgnoreCase("January")) {
        dateInt = 1;
      } else if (date.equalsIgnoreCase("February")) {
        dateInt = 2;
      } else if (date.equalsIgnoreCase("March")) {
        dateInt = 3;
      } else if (date.equalsIgnoreCase("April")) {
        dateInt = 4;
      } else if (date.equalsIgnoreCase("May")) {
        dateInt = 5;
      } else if (date.equalsIgnoreCase("June")) {
        dateInt = 6;
      } else if (date.equalsIgnoreCase("July")) {
        dateInt = 7;
      } else if (date.equalsIgnoreCase("August")) {
        dateInt = 8;
      } else if (date.equalsIgnoreCase("September")) {
        dateInt = 9;
      } else if (date.equalsIgnoreCase("October")) {
        dateInt = 10;
      } else if (date.equalsIgnoreCase("November")) {
        dateInt = 11;
      } else if (date.equalsIgnoreCase("December")) {
        dateInt = 12;
      } 
      return dateInt;
    } catch (Exception exception) {
      return 0;
    } 
  }
  
  public static Calendar getDatabaseDate(String pdateDate) {
    if (pdateDate.length() > 0) {
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
      ParsePosition pos = new ParsePosition(0);
      Date current = formatter.parse(pdateDate, pos);
      Calendar strDate = Calendar.getInstance();
      strDate.setTime(current);
      return strDate;
    } 
    return null;
  }
  
  public static void putNotepadIntoSession(Notepad notepad, Context context) { context.putSessionValue(NOTEPAD_SESSION_NAMES[notepad.getNotePadType()], notepad); }
  
  public static Notepad getNotepadFromSession(int notepadType, Context context) { return (Notepad)context.getSessionValue(NOTEPAD_SESSION_NAMES[notepadType]); }
  
  public static FormDropDownMenu getSelectionConfigurationDropDown(String name, String selectedOption, boolean required) {
    Vector configs = Cache.getSelectionConfigs();
    String[] values = new String[configs.size() + 1];
    String[] menuText = new String[configs.size() + 1];
    ArrayList valuesA = new ArrayList();
    ArrayList menuTextA = new ArrayList();
    values[0] = "0";
    menuText[0] = "All";
    valuesA.add("0");
    menuTextA.add("All");
    if (selectedOption == null)
      selectedOption = ""; 
    for (int i = 0; i < configs.size(); i++) {
      SelectionConfiguration config = (SelectionConfiguration)configs.elementAt(i);
      if (config.getInactive())
        if (!config.getSelectionConfigurationAbbreviation().equalsIgnoreCase(selectedOption))
          continue;  
      values[i + 1] = config.getSelectionConfigurationAbbreviation();
      menuText[i + 1] = config.getSelectionConfigurationName();
      valuesA.add(config.getSelectionConfigurationAbbreviation());
      menuTextA.add(config.getSelectionConfigurationName());
      continue;
    } 
    values = new String[valuesA.size()];
    menuText = new String[valuesA.size()];
    values = (String[])valuesA.toArray(values);
    menuText = (String[])menuTextA.toArray(menuText);
    return new FormDropDownMenu(name, 
        selectedOption, 
        values, 
        menuText, 
        required);
  }
  
  public static FormDropDownMenu getSelectionConfigurationDropDown(String name, String selectedOption, boolean required, int prodType) {
    Vector configs = Cache.getSelectionConfigs();
    String[] values = new String[configs.size() + 1];
    String[] menuText = new String[configs.size() + 1];
    ArrayList valuesA = new ArrayList();
    ArrayList menuTextA = new ArrayList();
    values[0] = "0";
    menuText[0] = "&nbsp;";
    valuesA.add("0");
    menuTextA.add("&nbsp;");
    if (selectedOption == null)
      selectedOption = ""; 
    int currIndex = 0;
    for (int i = 0; i < configs.size(); i++) {
      SelectionConfiguration config = (SelectionConfiguration)configs.elementAt(i);
      if (config.getInactive()) {
        if (!config.getSelectionConfigurationAbbreviation().equalsIgnoreCase(selectedOption))
          continue; 
      } else if (config.getProdType() != prodType && config.getProdType() != 2) {
        continue;
      } 
      values[currIndex + 1] = config.getSelectionConfigurationAbbreviation();
      menuText[currIndex + 1] = config.getSelectionConfigurationName();
      valuesA.add(config.getSelectionConfigurationAbbreviation());
      menuTextA.add(config.getSelectionConfigurationName());
      currIndex++;
      continue;
    } 
    values = new String[valuesA.size()];
    menuText = new String[valuesA.size()];
    values = (String[])valuesA.toArray(values);
    menuText = (String[])menuTextA.toArray(menuText);
    return new FormDropDownMenu(name, 
        selectedOption, 
        values, 
        menuText, 
        required);
  }
  
  public static FormDropDownMenu getSelectionConfigurationDropDown(String name, String selectedOption, boolean required, int prodType, boolean newBundle) {
    Vector configs = Cache.getSelectionConfigs();
    String[] values = new String[configs.size() + 1];
    String[] menuText = new String[configs.size() + 1];
    ArrayList valuesA = new ArrayList();
    ArrayList menuTextA = new ArrayList();
    values[0] = "0";
    menuText[0] = "&nbsp;";
    valuesA.add("0");
    menuTextA.add("&nbsp;");
    if (selectedOption == null)
      selectedOption = ""; 
    int currIndex = 0;
    for (int i = 0; i < configs.size(); i++) {
      SelectionConfiguration config = (SelectionConfiguration)configs.elementAt(i);
      if (config.getInactive()) {
        if (!config.getSelectionConfigurationAbbreviation().equalsIgnoreCase(selectedOption))
          continue; 
      } else {
        if (config.getProdType() != prodType && config.getProdType() != 2)
          continue; 
        if (!config.getSelectionConfigurationAbbreviation().equalsIgnoreCase(selectedOption) && (config.getProdType() == 1 || config.getProdType() == 2) && ((
          newBundle && config.getNewBundle() != 1) || (!newBundle && config.getNewBundle() == 1)))
          continue; 
      } 
      values[currIndex + 1] = config.getSelectionConfigurationAbbreviation();
      menuText[currIndex + 1] = config.getSelectionConfigurationName();
      valuesA.add(config.getSelectionConfigurationAbbreviation());
      menuTextA.add(config.getSelectionConfigurationName());
      currIndex++;
      continue;
    } 
    values = new String[valuesA.size()];
    menuText = new String[valuesA.size()];
    values = (String[])valuesA.toArray(values);
    menuText = (String[])menuTextA.toArray(menuText);
    return new FormDropDownMenu(name, 
        selectedOption, 
        values, 
        menuText, 
        required);
  }
  
  public static FormDropDownMenu getSelectionSubConfigurationDropDown(String name, SelectionConfiguration config, String selectedOption, boolean required) {
    FormDropDownMenu dropDown = null;
    if (selectedOption == null)
      selectedOption = ""; 
    if (config != null) {
      Vector subConfigs = config.getSubConfigurations();
      String[] values = new String[subConfigs.size() + 1];
      String[] menuText = new String[subConfigs.size() + 1];
      ArrayList valuesA = new ArrayList();
      ArrayList menuTextA = new ArrayList();
      values[0] = "0";
      menuText[0] = "&nbsp;";
      valuesA.add("0");
      menuTextA.add("&nbsp;");
      for (int i = 0; i < subConfigs.size(); i++) {
        SelectionSubConfiguration subConfig = (SelectionSubConfiguration)subConfigs.elementAt(i);
        if (subConfig.getInactive())
          if (!subConfig.getSelectionSubConfigurationAbbreviation().equalsIgnoreCase(selectedOption))
            continue;  
        values[i + 1] = subConfig.getSelectionSubConfigurationAbbreviation();
        menuText[i + 1] = subConfig.getSelectionSubConfigurationName();
        valuesA.add(subConfig.getSelectionSubConfigurationAbbreviation());
        menuTextA.add(subConfig.getSelectionSubConfigurationName());
        continue;
      } 
      values = new String[valuesA.size()];
      menuText = new String[menuTextA.size()];
      values = (String[])valuesA.toArray(values);
      menuText = (String[])menuTextA.toArray(menuText);
      dropDown = new FormDropDownMenu(name, 
          selectedOption, 
          values, 
          menuText, 
          required);
    } else {
      dropDown = new FormDropDownMenu(name, "", "", "", required);
    } 
    return dropDown;
  }
  
  public static FormDropDownMenu getSelectionSubConfigurationDropDown(String name, SelectionConfiguration config, String selectedOption, boolean required, int prodType) {
    FormDropDownMenu dropDown = null;
    if (selectedOption == null)
      selectedOption = ""; 
    if (config != null) {
      Vector subConfigs = config.getSubConfigurations();
      String[] values = new String[subConfigs.size() + 1];
      String[] menuText = new String[subConfigs.size() + 1];
      ArrayList valuesA = new ArrayList();
      ArrayList menuTextA = new ArrayList();
      values[0] = "0";
      menuText[0] = "&nbsp;";
      valuesA.add("0");
      menuTextA.add("&nbsp;");
      int currIndex = 0;
      for (int i = 0; i < subConfigs.size(); i++) {
        SelectionSubConfiguration subConfig = (SelectionSubConfiguration)subConfigs.elementAt(i);
        if (subConfig.getInactive()) {
          if (!subConfig.getSelectionSubConfigurationAbbreviation().equalsIgnoreCase(selectedOption))
            continue; 
        } else if (subConfig.getProdType() != prodType && subConfig.getProdType() != 2) {
          continue;
        } 
        values[i + 1] = subConfig.getSelectionSubConfigurationAbbreviation();
        menuText[i + 1] = subConfig.getSelectionSubConfigurationName();
        valuesA.add(subConfig.getSelectionSubConfigurationAbbreviation());
        menuTextA.add(subConfig.getSelectionSubConfigurationName());
        currIndex++;
        continue;
      } 
      values = new String[valuesA.size()];
      menuText = new String[menuTextA.size()];
      values = (String[])valuesA.toArray(values);
      menuText = (String[])menuTextA.toArray(menuText);
      dropDown = new FormDropDownMenu(name, 
          selectedOption, 
          values, 
          menuText, 
          required);
    } else {
      dropDown = new FormDropDownMenu(name, "", "", "", required);
    } 
    return dropDown;
  }
  
  public static FormDropDownMenu getLookupDropDown(String name, Vector menuVector, String selectedOption, boolean required, boolean blankFirst) {
    FormDropDownMenu dropDown = null;
    if (menuVector != null) {
      int size = menuVector.size();
      int offset = 0;
      ArrayList valuesA = new ArrayList();
      ArrayList menuTextA = new ArrayList();
      if (blankFirst) {
        size++;
        offset = 1;
      } 
      String[] values = new String[size];
      String[] menuText = new String[size];
      if (blankFirst) {
        values[0] = "0";
        menuText[0] = "&nbsp;";
        valuesA.add("0");
        menuTextA.add("&nbsp;");
      } 
      if (selectedOption == null)
        selectedOption = ""; 
      for (int i = 0; i < menuVector.size(); i++) {
        LookupObject lookupObject = (LookupObject)menuVector.elementAt(i);
        if (lookupObject.getInactive())
          if (!lookupObject.getAbbreviation().equalsIgnoreCase(selectedOption))
            continue;  
        values[i + offset] = lookupObject.getAbbreviation();
        menuText[i + offset] = lookupObject.getName();
        valuesA.add(lookupObject.getAbbreviation());
        menuTextA.add(lookupObject.getName());
        continue;
      } 
      values = new String[valuesA.size()];
      menuText = new String[valuesA.size()];
      values = (String[])valuesA.toArray(values);
      menuText = (String[])menuTextA.toArray(menuText);
      dropDown = new FormDropDownMenu(name, 
          selectedOption, 
          values, 
          menuText, 
          required);
    } else {
      dropDown = new FormDropDownMenu(name, "", "", "", required);
    } 
    return dropDown;
  }
  
  public static FormDropDownMenu getLookupDropDownBom(String name, Vector menuVector, String selectedOption, boolean required, boolean blankFirst) {
    FormDropDownMenu dropDown = null;
    if (menuVector != null) {
      int size = menuVector.size();
      int offset = 0;
      ArrayList valuesA = new ArrayList();
      ArrayList menuTextA = new ArrayList();
      if (blankFirst) {
        size++;
        offset = 1;
      } 
      String[] values = new String[size];
      String[] menuText = new String[size];
      if (blankFirst) {
        values[0] = "&nbsp;";
        menuText[0] = "&nbsp;";
        valuesA.add("0");
        menuTextA.add("&nbsp;");
      } 
      if (selectedOption == null)
        selectedOption = ""; 
      for (int i = 0; i < menuVector.size(); i++) {
        LookupObject lookupObject = (LookupObject)menuVector.elementAt(i);
        if (lookupObject.getInactive())
          if (!lookupObject.getName().equalsIgnoreCase(selectedOption))
            continue;  
        values[i + offset] = lookupObject.getName();
        menuText[i + offset] = lookupObject.getName();
        valuesA.add(lookupObject.getName());
        menuTextA.add(lookupObject.getName());
        continue;
      } 
      values = new String[valuesA.size()];
      menuText = new String[valuesA.size()];
      values = (String[])valuesA.toArray(values);
      menuText = (String[])menuTextA.toArray(menuText);
      dropDown = new FormDropDownMenu(name, 
          selectedOption, 
          values, 
          menuText, 
          required);
    } else {
      dropDown = new FormDropDownMenu(name, "", "", "", required);
    } 
    return dropDown;
  }
  
  public static FormDropDownMenu getPfmLookupDropDown(String name, Vector menuVector, String selectedOption, boolean required, boolean blankFirst) {
    FormDropDownMenu dropDown = null;
    if (menuVector != null) {
      int size = menuVector.size();
      int offset = 0;
      ArrayList valuesA = new ArrayList();
      ArrayList menuTextA = new ArrayList();
      if (blankFirst) {
        size++;
        offset = 1;
      } 
      String[] values = new String[size];
      String[] menuText = new String[size];
      if (blankFirst) {
        values[0] = "-1";
        menuText[0] = "&nbsp;";
        valuesA.add("-1");
        menuTextA.add("&nbsp;");
      } 
      if (selectedOption == null)
        selectedOption = ""; 
      for (int i = 0; i < menuVector.size(); i++) {
        LookupObject lookupObject = (LookupObject)menuVector.elementAt(i);
        String temporaryHold = lookupObject.getName();
        if (lookupObject.getInactive())
          if (!lookupObject.getAbbreviation().equalsIgnoreCase(selectedOption))
            continue;  
        values[i + offset] = lookupObject.getAbbreviation();
        menuText[i + offset] = String.valueOf(lookupObject.getAbbreviation()) + ":" + temporaryHold;
        valuesA.add(lookupObject.getAbbreviation());
        menuTextA.add(String.valueOf(lookupObject.getAbbreviation()) + ":" + temporaryHold);
        continue;
      } 
      values = new String[valuesA.size()];
      menuText = new String[valuesA.size()];
      values = (String[])valuesA.toArray(values);
      menuText = (String[])menuTextA.toArray(menuText);
      dropDown = new FormDropDownMenu(name, 
          selectedOption, 
          values, 
          menuText, 
          required);
    } else {
      dropDown = new FormDropDownMenu(name, "", "", "", required);
    } 
    return dropDown;
  }
  
  public static FormDropDownMenu getPrefixCodeDropDown(int userId, String name, Vector prefixVector, String selectedOption, boolean required, Context context) {
    Vector values = new Vector();
    Vector menuText = new Vector();
    ArrayList valuesA = new ArrayList();
    ArrayList menuTextA = new ArrayList();
    values.addElement("-1");
    menuText.addElement("&nbsp;&nbsp;");
    valuesA.add("-1");
    menuTextA.add("&nbsp;&nbsp;");
    FormDropDownMenu dropDown = null;
    if (selectedOption == null)
      selectedOption = ""; 
    Vector userEnvironments = getUserEnvironments(context);
    if (userEnvironments != null) {
      for (int i = 0; i < userEnvironments.size(); i++) {
        Environment env = (Environment)userEnvironments.elementAt(i);
        if (env != null) {
          int envID = env.getStructureID();
          Family family = env.getParentFamily();
          String familyName = "";
          if (family != null)
            familyName = family.getStructureAbbreviation(); 
          if (!familyName.equalsIgnoreCase("UML") && 
            !familyName.equalsIgnoreCase("Enterprise"))
            if (prefixVector != null)
              for (int j = 0; j < prefixVector.size(); j++) {
                PrefixCode prefixCode = (PrefixCode)prefixVector.elementAt(j);
                if (prefixCode.getPrefixCodeSubValue() == envID) {
                  if (prefixCode.getInactive())
                    if (!prefixCode.getAbbreviation().equalsIgnoreCase(selectedOption))
                      continue;  
                  values.addElement(prefixCode.getAbbreviation());
                  menuText.addElement(prefixCode.getAbbreviation());
                  valuesA.add(prefixCode.getAbbreviation());
                  menuTextA.add(prefixCode.getAbbreviation());
                } 
                continue;
              }   
        } 
      } 
      String[] arrayValues = new String[valuesA.size()];
      String[] arrayMenuText = new String[menuTextA.size()];
      arrayValues = (String[])valuesA.toArray(arrayValues);
      arrayMenuText = (String[])menuTextA.toArray(arrayMenuText);
      String[] arrayCombined = new String[valuesA.size()];
      for (int c = 0; c < arrayCombined.length; c++)
        arrayCombined[c] = String.valueOf(arrayValues[c]) + "||" + arrayMenuText[c]; 
      Arrays.sort(arrayCombined, new StringComparator());
      String[] sortedArrayValues = new String[valuesA.size()];
      String[] sortedArrayMenuText = new String[menuTextA.size()];
      String concatenatedValueText = "";
      int symbolPosition = 0;
      for (int d = 0; d < arrayCombined.length; d++) {
        concatenatedValueText = arrayCombined[d];
        symbolPosition = concatenatedValueText.indexOf("||");
        sortedArrayValues[d] = concatenatedValueText.substring(0, symbolPosition);
        sortedArrayMenuText[d] = concatenatedValueText.substring(symbolPosition + 2, concatenatedValueText.length());
      } 
      dropDown = new FormDropDownMenu(name, 
          selectedOption, 
          sortedArrayValues, 
          sortedArrayMenuText, 
          required);
    } 
    return dropDown;
  }
  
  public static FormDropDownMenu getPreparedByDropDown(Context context, String name, Vector labelUsers, String userName, boolean required) {
    boolean selectedUserFound = false;
    Vector values = new Vector();
    Vector menuText = new Vector();
    values.addElement("0");
    menuText.addElement("&nbsp;&nbsp;");
    FormDropDownMenu dropdown = null;
    if (labelUsers != null && labelUsers.size() > 0) {
      for (int i = 0; i < labelUsers.size(); i++) {
        User labelContactUser = (User)labelUsers.get(i);
        if (labelContactUser != null) {
          String labelContactName = labelContactUser.getName();
          if (labelContactName.trim().equalsIgnoreCase(userName.trim()))
            selectedUserFound = true; 
          values.addElement(labelContactName);
          menuText.addElement(labelContactName);
        } 
      } 
      if (!selectedUserFound && !userName.equals("")) {
        values.addElement(userName);
        menuText.addElement(userName);
      } 
      String[] arrayValues = new String[values.size()];
      String[] arrayMenuText = new String[menuText.size()];
      arrayValues = (String[])values.toArray(arrayValues);
      arrayMenuText = (String[])menuText.toArray(arrayMenuText);
      dropdown = new FormDropDownMenu(name, 
          userName, 
          arrayValues, 
          arrayMenuText, 
          required);
    } else if (!userName.equals("")) {
      values.addElement(userName);
      menuText.addElement(userName);
      String[] arrayValues = new String[values.size()];
      String[] arrayMenuText = new String[menuText.size()];
      arrayValues = (String[])values.toArray(arrayValues);
      arrayMenuText = (String[])menuText.toArray(arrayMenuText);
      dropdown = new FormDropDownMenu(name, 
          userName, 
          arrayValues, 
          arrayMenuText, 
          required);
    } else {
      User user = MilestoneSecurity.getUser(context);
      values.addElement(user.getName());
      menuText.addElement(user.getName());
      String[] arrayValues = new String[values.size()];
      String[] arrayMenuText = new String[menuText.size()];
      arrayValues = (String[])values.toArray(arrayValues);
      arrayMenuText = (String[])menuText.toArray(arrayMenuText);
      dropdown = new FormDropDownMenu(name, 
          userName, 
          arrayValues, 
          arrayMenuText, 
          required);
    } 
    return dropdown;
  }
  
  public static FormDropDownMenu getCorporateStructureDropDown(String name, Vector corporateVector, String selectedOption, boolean required, boolean blankFirst) {
    Vector values = new Vector();
    Vector menuText = new Vector();
    FormDropDownMenu dropdown = null;
    boolean selectedFound = false;
    HashMap corpHashMap = buildActiveCorporateStructureHashMap();
    int selectionInt = 0;
    try {
      selectionInt = Integer.parseInt(selectedOption);
    } catch (NumberFormatException numberFormatException) {}
    if (corporateVector != null) {
      Vector sortedVector = new Vector();
      sortedVector = sortCorporateVectorByName(corporateVector);
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
          (CorporateStructureObject)getStructureObject(selectionInt);
        if (csoSelected != null) {
          values.addElement(csoSelected.getStructureID());
          menuText.addElement(csoSelected.getName());
        } 
      } 
      String[] arrayValues = new String[values.size()];
      String[] arrayMenuText = new String[menuText.size()];
      arrayValues = (String[])values.toArray(arrayValues);
      arrayMenuText = (String[])menuText.toArray(arrayMenuText);
      dropdown = new FormDropDownMenu(name, 
          selectedOption, 
          arrayValues, 
          arrayMenuText, 
          required);
    } else {
      dropdown = new FormDropDownMenu(name, "", "", required);
    } 
    corpHashMap = null;
    return dropdown;
  }
  
  public static FormDropDownMenu getCorporateStructureDropDownDuplicates(String name, Vector corporateVector, String selectedOption, boolean required, boolean blankFirst, boolean activesOnly) {
    Vector values = new Vector();
    MilestoneVector menuText = new MilestoneVector();
    FormDropDownMenu dropdown = null;
    boolean selectedFound = false;
    HashMap corpHashMap = buildActiveCorporateStructureHashMap();
    int selectionInt = 0;
    try {
      selectionInt = Integer.parseInt(selectedOption);
    } catch (NumberFormatException numberFormatException) {}
    if (corporateVector != null) {
      Vector sortedVector = new Vector();
      sortedVector = sortCorporateVectorByName(corporateVector);
      if (blankFirst)
        if (name.equalsIgnoreCase("family")) {
          values.addElement("0");
          menuText.addElement("All");
        } else if (name.equalsIgnoreCase("labels")) {
          values.addElement("0");
          menuText.addElement(" ");
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
          (CorporateStructureObject)getStructureObject(selectionInt);
        if (csoSelected != null) {
          values.addElement(csoSelected.getStructureID());
          menuText.addElement(csoSelected.getName());
        } 
      } 
      String[] arrayValues = new String[values.size()];
      String[] arrayMenuText = new String[menuText.size()];
      arrayValues = (String[])values.toArray(arrayValues);
      arrayMenuText = (String[])menuText.toArray(arrayMenuText);
      dropdown = new FormDropDownMenu(name, 
          selectedOption, 
          arrayValues, 
          arrayMenuText, 
          required);
    } else {
      dropdown = new FormDropDownMenu(name, "", "", required);
    } 
    corpHashMap = null;
    return dropdown;
  }
  
  public static FormDropDownMenu getContactsDropDown(Context context, String name, Vector labelUsers, User selectedUser, boolean required) { // Byte code:
    //   0: iconst_m1
    //   1: istore #5
    //   3: aload_3
    //   4: ifnull -> 13
    //   7: aload_3
    //   8: invokevirtual getUserId : ()I
    //   11: istore #5
    //   13: iconst_0
    //   14: istore #6
    //   16: new java/util/Vector
    //   19: dup
    //   20: invokespecial <init> : ()V
    //   23: astore #7
    //   25: new java/util/Vector
    //   28: dup
    //   29: invokespecial <init> : ()V
    //   32: astore #8
    //   34: aload #7
    //   36: ldc_w '0'
    //   39: invokevirtual addElement : (Ljava/lang/Object;)V
    //   42: aload #8
    //   44: ldc_w 'All'
    //   47: invokevirtual addElement : (Ljava/lang/Object;)V
    //   50: aconst_null
    //   51: astore #9
    //   53: aload_2
    //   54: ifnull -> 265
    //   57: aload_2
    //   58: invokevirtual size : ()I
    //   61: ifle -> 265
    //   64: iconst_0
    //   65: istore #10
    //   67: goto -> 140
    //   70: aload_2
    //   71: iload #10
    //   73: invokevirtual get : (I)Ljava/lang/Object;
    //   76: checkcast com/universal/milestone/User
    //   79: astore #11
    //   81: aload #11
    //   83: ifnull -> 137
    //   86: aload #11
    //   88: invokevirtual getName : ()Ljava/lang/String;
    //   91: astore #12
    //   93: aload #11
    //   95: invokevirtual getUserId : ()I
    //   98: istore #13
    //   100: iload #13
    //   102: iload #5
    //   104: if_icmpne -> 110
    //   107: iconst_1
    //   108: istore #6
    //   110: aload #7
    //   112: new java/lang/StringBuilder
    //   115: dup
    //   116: invokespecial <init> : ()V
    //   119: iload #13
    //   121: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   124: invokevirtual toString : ()Ljava/lang/String;
    //   127: invokevirtual addElement : (Ljava/lang/Object;)V
    //   130: aload #8
    //   132: aload #12
    //   134: invokevirtual addElement : (Ljava/lang/Object;)V
    //   137: iinc #10, 1
    //   140: iload #10
    //   142: aload_2
    //   143: invokevirtual size : ()I
    //   146: if_icmplt -> 70
    //   149: iload #6
    //   151: ifne -> 187
    //   154: aload_3
    //   155: ifnull -> 187
    //   158: aload #7
    //   160: new java/lang/StringBuilder
    //   163: dup
    //   164: invokespecial <init> : ()V
    //   167: iload #5
    //   169: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   172: invokevirtual toString : ()Ljava/lang/String;
    //   175: invokevirtual addElement : (Ljava/lang/Object;)V
    //   178: aload #8
    //   180: aload_3
    //   181: invokevirtual getName : ()Ljava/lang/String;
    //   184: invokevirtual addElement : (Ljava/lang/Object;)V
    //   187: aload #7
    //   189: invokevirtual size : ()I
    //   192: anewarray java/lang/String
    //   195: astore #10
    //   197: aload #8
    //   199: invokevirtual size : ()I
    //   202: anewarray java/lang/String
    //   205: astore #11
    //   207: aload #7
    //   209: aload #10
    //   211: invokevirtual toArray : ([Ljava/lang/Object;)[Ljava/lang/Object;
    //   214: checkcast [Ljava/lang/String;
    //   217: astore #10
    //   219: aload #8
    //   221: aload #11
    //   223: invokevirtual toArray : ([Ljava/lang/Object;)[Ljava/lang/Object;
    //   226: checkcast [Ljava/lang/String;
    //   229: astore #11
    //   231: new com/techempower/gemini/FormDropDownMenu
    //   234: dup
    //   235: aload_1
    //   236: new java/lang/StringBuilder
    //   239: dup
    //   240: invokespecial <init> : ()V
    //   243: iload #5
    //   245: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   248: invokevirtual toString : ()Ljava/lang/String;
    //   251: aload #10
    //   253: aload #11
    //   255: iload #4
    //   257: invokespecial <init> : (Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;[Ljava/lang/String;Z)V
    //   260: astore #9
    //   262: goto -> 490
    //   265: aload_3
    //   266: ifnull -> 376
    //   269: aload #7
    //   271: new java/lang/StringBuilder
    //   274: dup
    //   275: invokespecial <init> : ()V
    //   278: iload #5
    //   280: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   283: invokevirtual toString : ()Ljava/lang/String;
    //   286: invokevirtual addElement : (Ljava/lang/Object;)V
    //   289: aload #8
    //   291: aload_3
    //   292: invokevirtual getName : ()Ljava/lang/String;
    //   295: invokevirtual addElement : (Ljava/lang/Object;)V
    //   298: aload #7
    //   300: invokevirtual size : ()I
    //   303: anewarray java/lang/String
    //   306: astore #10
    //   308: aload #8
    //   310: invokevirtual size : ()I
    //   313: anewarray java/lang/String
    //   316: astore #11
    //   318: aload #7
    //   320: aload #10
    //   322: invokevirtual toArray : ([Ljava/lang/Object;)[Ljava/lang/Object;
    //   325: checkcast [Ljava/lang/String;
    //   328: astore #10
    //   330: aload #8
    //   332: aload #11
    //   334: invokevirtual toArray : ([Ljava/lang/Object;)[Ljava/lang/Object;
    //   337: checkcast [Ljava/lang/String;
    //   340: astore #11
    //   342: new com/techempower/gemini/FormDropDownMenu
    //   345: dup
    //   346: aload_1
    //   347: new java/lang/StringBuilder
    //   350: dup
    //   351: invokespecial <init> : ()V
    //   354: iload #5
    //   356: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   359: invokevirtual toString : ()Ljava/lang/String;
    //   362: aload #10
    //   364: aload #11
    //   366: iload #4
    //   368: invokespecial <init> : (Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;[Ljava/lang/String;Z)V
    //   371: astore #9
    //   373: goto -> 490
    //   376: aload_0
    //   377: invokestatic getUser : (Lcom/techempower/gemini/Context;)Lcom/universal/milestone/User;
    //   380: astore #10
    //   382: aload #7
    //   384: new java/lang/StringBuilder
    //   387: dup
    //   388: invokespecial <init> : ()V
    //   391: aload #10
    //   393: invokevirtual getUserId : ()I
    //   396: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   399: invokevirtual toString : ()Ljava/lang/String;
    //   402: invokevirtual addElement : (Ljava/lang/Object;)V
    //   405: aload #8
    //   407: aload #10
    //   409: invokevirtual getName : ()Ljava/lang/String;
    //   412: invokevirtual addElement : (Ljava/lang/Object;)V
    //   415: aload #7
    //   417: invokevirtual size : ()I
    //   420: anewarray java/lang/String
    //   423: astore #11
    //   425: aload #8
    //   427: invokevirtual size : ()I
    //   430: anewarray java/lang/String
    //   433: astore #12
    //   435: aload #7
    //   437: aload #11
    //   439: invokevirtual toArray : ([Ljava/lang/Object;)[Ljava/lang/Object;
    //   442: checkcast [Ljava/lang/String;
    //   445: astore #11
    //   447: aload #8
    //   449: aload #12
    //   451: invokevirtual toArray : ([Ljava/lang/Object;)[Ljava/lang/Object;
    //   454: checkcast [Ljava/lang/String;
    //   457: astore #12
    //   459: new com/techempower/gemini/FormDropDownMenu
    //   462: dup
    //   463: aload_1
    //   464: new java/lang/StringBuilder
    //   467: dup
    //   468: invokespecial <init> : ()V
    //   471: iload #5
    //   473: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   476: invokevirtual toString : ()Ljava/lang/String;
    //   479: aload #11
    //   481: aload #12
    //   483: iload #4
    //   485: invokespecial <init> : (Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;[Ljava/lang/String;Z)V
    //   488: astore #9
    //   490: aload #9
    //   492: areturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #1878	-> 0
    //   #1880	-> 3
    //   #1881	-> 7
    //   #1883	-> 13
    //   #1885	-> 16
    //   #1886	-> 25
    //   #1889	-> 34
    //   #1890	-> 42
    //   #1892	-> 50
    //   #1894	-> 53
    //   #1896	-> 64
    //   #1898	-> 70
    //   #1900	-> 81
    //   #1902	-> 86
    //   #1903	-> 93
    //   #1904	-> 100
    //   #1905	-> 107
    //   #1907	-> 110
    //   #1908	-> 130
    //   #1896	-> 137
    //   #1916	-> 149
    //   #1918	-> 158
    //   #1920	-> 178
    //   #1923	-> 187
    //   #1924	-> 197
    //   #1927	-> 207
    //   #1928	-> 219
    //   #1930	-> 231
    //   #1931	-> 236
    //   #1932	-> 251
    //   #1933	-> 253
    //   #1934	-> 255
    //   #1930	-> 257
    //   #1935	-> 262
    //   #1938	-> 265
    //   #1940	-> 269
    //   #1941	-> 289
    //   #1943	-> 298
    //   #1944	-> 308
    //   #1947	-> 318
    //   #1948	-> 330
    //   #1950	-> 342
    //   #1951	-> 347
    //   #1952	-> 362
    //   #1953	-> 364
    //   #1954	-> 366
    //   #1950	-> 368
    //   #1955	-> 373
    //   #1962	-> 376
    //   #1964	-> 382
    //   #1965	-> 405
    //   #1967	-> 415
    //   #1968	-> 425
    //   #1971	-> 435
    //   #1972	-> 447
    //   #1974	-> 459
    //   #1975	-> 464
    //   #1976	-> 479
    //   #1977	-> 481
    //   #1978	-> 483
    //   #1974	-> 485
    //   #1982	-> 490
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	493	0	context	Lcom/techempower/gemini/Context;
    //   0	493	1	name	Ljava/lang/String;
    //   0	493	2	labelUsers	Ljava/util/Vector;
    //   0	493	3	selectedUser	Lcom/universal/milestone/User;
    //   0	493	4	required	Z
    //   3	490	5	selectedUserID	I
    //   16	477	6	selectedUserFound	Z
    //   25	468	7	values	Ljava/util/Vector;
    //   34	459	8	menuText	Ljava/util/Vector;
    //   53	440	9	dropdown	Lcom/techempower/gemini/FormDropDownMenu;
    //   67	82	10	i	I
    //   81	56	11	labelContactUser	Lcom/universal/milestone/User;
    //   93	44	12	labelContactName	Ljava/lang/String;
    //   100	37	13	labelContactID	I
    //   197	65	10	arrayValues	[Ljava/lang/String;
    //   207	55	11	arrayMenuText	[Ljava/lang/String;
    //   308	65	10	arrayValues	[Ljava/lang/String;
    //   318	55	11	arrayMenuText	[Ljava/lang/String;
    //   382	108	10	user	Lcom/universal/milestone/User;
    //   425	65	11	arrayValues	[Ljava/lang/String;
    //   435	55	12	arrayMenuText	[Ljava/lang/String; }
  
  public static Vector getNonSecureUserFamilies(Context context) {
    theFamilyList = null;
    Vector vUserEnvironments = getUserEnvironments(context);
    Environment env = null;
    Vector theFamilies = Cache.getFamilies();
    Vector precache = new Vector();
    Family family = null;
    for (int i = 0; i < theFamilies.size(); i++) {
      family = (Family)theFamilies.elementAt(i);
      if (family != null)
        for (int j = 0; j < vUserEnvironments.size(); j++) {
          env = (Environment)vUserEnvironments.elementAt(j);
          if (env != null) {
            Family parent = (Family)getStructureObject(env.getParentID());
            if (parent != null && parent.getStructureID() == family.getStructureID()) {
              precache.addElement(family);
              break;
            } 
            env = null;
            parent = null;
          } 
        }  
      family = null;
    } 
    return precache;
  }
  
  public static Vector getNonSecureActiveUserFamilies(Context context) {
    Vector theFamilyList = null;
    Vector vUserEnvironments = getUserEnvironments(context);
    Environment env = null;
    Vector theFamilies = Cache.getFamilies();
    Vector precache = new Vector();
    Family family = null;
    HashMap corpHashMap = buildActiveCorporateStructureHashMap();
    for (int i = 0; i < theFamilies.size(); i++) {
      family = (Family)theFamilies.elementAt(i);
      int structId = family.getStructureID();
      boolean boolVal = !corpHashMap.containsKey(new Integer(structId));
      if (family != null && boolVal)
        for (int j = 0; j < vUserEnvironments.size(); j++) {
          env = (Environment)vUserEnvironments.elementAt(j);
          if (env != null) {
            Family parent = (Family)getStructureObject(env.getParentID());
            if (parent != null && parent.getStructureID() == family.getStructureID()) {
              precache.addElement(family);
              break;
            } 
            env = null;
            parent = null;
          } 
        }  
      family = null;
    } 
    theFamilyList = precache;
    corpHashMap = null;
    return theFamilyList;
  }
  
  public static Vector getSecureUserFamilies(User user, int area, Context context) {
    theFamilyList = null;
    Vector vUserEnvironments = getUserEnvironments(context);
    Environment env = null;
    Vector theFamilies = Cache.getFamilies();
    Vector precache = new Vector();
    Family family = null;
    for (int i = 0; i < theFamilies.size(); i++) {
      family = (Family)theFamilies.elementAt(i);
      if (family != null)
        for (int j = 0; j < vUserEnvironments.size(); j++) {
          env = (Environment)vUserEnvironments.elementAt(j);
          if (env != null) {
            Family parent = (Family)getStructureObject(env.getParentID());
            if (parent != null && parent.getStructureID() == family.getStructureID()) {
              Company company = (Company)env.getCompanies().get(0);
              if (company != null) {
                CompanyAcl companyAcl = getScreenPermissions(company, user);
                boolean editable = false;
                if (companyAcl != null) {
                  if (area == 0 && companyAcl.getAccessSchedule() > 1)
                    editable = true; 
                  if (area == 1 && companyAcl.getAccessTemplate() > 1)
                    editable = true; 
                  if (area == 2 && companyAcl.getAccessTask() > 1)
                    editable = true; 
                } 
                if (editable) {
                  precache.addElement(family);
                  break;
                } 
                companyAcl = null;
              } 
            } 
            env = null;
            parent = null;
          } 
        }  
      family = null;
    } 
    return precache;
  }
  
  public static boolean getActiveCorporateStructure(int structureId) {
    boolean flag = false;
    String structureQuery = "SELECT  * FROM  vi_Structure WHERE  structure_id = " + 
      
      structureId;
    JdbcConnector structureConnector = createConnector(structureQuery);
    structureConnector.runQuery();
    while (structureConnector.more()) {
      int sortField = structureConnector.getIntegerField("sort");
      if (sortField == 0)
        flag = true; 
      structureConnector.next();
    } 
    structureConnector.close();
    return flag;
  }
  
  public static HashMap buildActiveCorporateStructureHashMap() { return new HashMap(); }
  
  public static Vector getUserCompanies(int userID) {
    String companyQuery = "sp_get_User_Companies " + userID;
    Vector precache = new Vector();
    Company company = null;
    JdbcConnector companyConnector = createConnector(companyQuery);
    companyConnector.runQuery();
    while (companyConnector.more()) {
      company = (Company)getStructureObject(companyConnector.getIntegerField("structure_id"));
      if (company != null)
        precache.addElement(company); 
      company = null;
      companyConnector.next();
    } 
    companyConnector.close();
    return precache;
  }
  
  public static Vector getUserCompaniesExcludeUmlEnterprise(int userID) {
    String companyQuery = "sp_get_User_Companies_Exclude " + userID;
    Vector precache = new Vector();
    Company company = null;
    JdbcConnector companyConnector = createConnector(companyQuery);
    companyConnector.runQuery();
    while (companyConnector.more()) {
      company = (Company)getStructureObject(companyConnector.getIntegerField("structure_id"));
      if (company != null)
        precache.addElement(company); 
      company = null;
      companyConnector.next();
    } 
    companyConnector.close();
    return precache;
  }
  
  public static Vector getUserCompanies(Context context) {
    User user = (User)context.getSessionValue("user");
    int userId = user.getUserId();
    if (context.getSessionValue("user-companies") != null && !((Vector)context.getSessionValue("user-companies")).isEmpty())
      return (Vector)context.getSessionValue("user-companies"); 
    Vector userCompanies = getUserCompanies(userId);
    if (userCompanies != null)
      context.putSessionValue("user-companies", userCompanies); 
    return userCompanies;
  }
  
  public static Vector getUserCompaniesExcludeUmlEnterprise(Context context) {
    User user = (User)context.getSessionValue("user");
    int userId = user.getUserId();
    return getUserCompaniesExcludeUmlEnterprise(userId);
  }
  
  public static void clearUserCompaniesFromSession(Context context) { context.putSessionValue("user-companies", null); }
  
  public static Vector getUserLabels(Context context) {
    labels = new Vector();
    return getUserLabels(getUserCompanies(context));
  }
  
  public static Vector getUserLabels(Vector companies) {
    Vector userLabels = new Vector();
    if (companies != null)
      for (int i = 0; i < companies.size(); i++) {
        Company company = (Company)companies.get(i);
        if (company.getDivisions() != null) {
          Vector divisions = company.getDivisions();
          for (int j = 0; j < divisions.size(); j++) {
            Division division = (Division)divisions.get(j);
            if (division.getLabels() != null) {
              Vector labels = division.getLabels();
              for (int k = 0; k < labels.size(); k++) {
                Label label = (Label)labels.get(k);
                userLabels.add(label);
              } 
            } 
          } 
        } 
      }  
    return userLabels;
  }
  
  public static int getStructureId(String name, int structureType) {
    Vector csoObjects = Cache.getCorporateStructure();
    for (int i = 0; i < csoObjects.size(); i++) {
      CorporateStructureObject cso = (CorporateStructureObject)csoObjects.elementAt(i);
      if (name.equalsIgnoreCase(cso.getName().trim()) && 
        cso.getStructureType() == structureType)
        return cso.getStructureID(); 
    } 
    return -1;
  }
  
  public static String getStructureName(int id) {
    CorporateStructureObject cso = (CorporateStructureObject)getStructureObject(id);
    if (cso != null)
      return cso.getName(); 
    return "";
  }
  
  public static Object getStructureObject(int id) {
    Vector csoObjects = Cache.getCorporateStructure();
    for (int i = 0; i < csoObjects.size(); i++) {
      CorporateStructureObject cso = (CorporateStructureObject)csoObjects.elementAt(i);
      if (cso.getStructureID() == id)
        return cso; 
    } 
    return null;
  }
  
  public static Vector getTaskAbbreviationList() {
    query = "Select DISTINCT ld.description From vi_lookup_detail ld, vi_lookup_header lh with (nolock), vi_task tk with (nolock) Where convert(varchar(10),lh.field_id) = ld.field_id and convert(varchar(10),tk.abbrev_id) = ld.value and lh.field_name = 'task_abbrev'";
    Vector precache = null;
    JdbcConnector connector = createConnector(query);
    connector.runQuery();
    if (connector.more())
      precache = new Vector(); 
    while (connector.more()) {
      precache.add(new String(connector.getField("description")));
      connector.next();
    } 
    connector.close();
    return precache;
  }
  
  public static String getTaskAbbreviationDescription(int id) {
    String query = "Select DISTINCT ld.description From vi_lookup_detail ld, vi_lookup_header lh with (nolock), vi_task tk  with (nolock) Where convert(varchar(10),lh.field_id) = ld.field_id and convert(varchar(10),tk.abbrev_id) = ld.value and ld.value = " + 
      
      id + " and " + 
      "lh.field_name = 'task_abbrev'";
    JdbcConnector connector = createConnector(query);
    connector.runQuery();
    if (connector.more()) {
      connector.close();
      return connector.getField("description");
    } 
    connector.close();
    return null;
  }
  
  public static String getTaskAbbreivationNameById(int id) {
    String name = "";
    if (id > 0) {
      Vector abbrevs = Cache.getTaskAbbreviations();
      for (int i = 0; i < abbrevs.size(); i++) {
        LookupObject taskAbbrev = (LookupObject)abbrevs.get(i);
        if (taskAbbrev.getAbbreviation().equalsIgnoreCase(Integer.toString(id)))
          name = taskAbbrev.getName(); 
      } 
    } 
    return name;
  }
  
  public static int getTaskAbbreviationID(String description) {
    String query = "Select DISTINCT ld.value From vi_lookup_detail ld with (nolock), vi_lookup_header lh with (nolock), vi_task tk with (nolock) Where convert(varchar(10),lh.field_id) = ld.field_id and convert(varchar(10),tk.abbrev_id) = ld.value and ld.description = '" + 
      
      description + "' and " + 
      "lh.field_name = 'task_abbrev'";
    System.out.println("getTaskAbbreviationID: " + query);
    JdbcConnector connector = createConnector(query);
    connector.runQuery();
    int abbrevId = -99;
    if (connector.more())
      abbrevId = connector.getIntegerField("value"); 
    connector.close();
    return abbrevId;
  }
  
  public static Vector getDepartmentList() {
    query = "Select DISTINCT ld.description From vi_lookup_detail ld, vi_lookup_header lh with (nolock), vi_task tk with (nolock) Where convert(varchar(10),lh.field_id) = ld.field_id and lh.field_name = 'department'";
    Vector precache = null;
    JdbcConnector connector = createConnector(query);
    connector.runQuery();
    if (connector.more())
      precache = new Vector(); 
    while (connector.more()) {
      precache.add(new String(connector.getField("description")));
      connector.next();
    } 
    connector.close();
    return precache;
  }
  
  public static String getDepartmentDescription(String value) {
    String query = "Select DISTINCT ld.description From vi_lookup_detail ld, vi_lookup_header lh with (nolock), vi_task tk  with (nolock) Where convert(varchar(10),lh.field_id) = ld.field_id and ld.value = '" + 
      
      value + "' and " + 
      "lh.field_name = 'department'";
    JdbcConnector connector = createConnector(query);
    connector.runQuery();
    if (connector.more()) {
      connector.close();
      return connector.getField("description");
    } 
    connector.close();
    return null;
  }
  
  public static int getDepartmentID(String value) {
    String query = "Select DISTINCT ld.field_id From vi_lookup_detail ld, vi_lookup_header lh with (nolock), vi_task tk  with (nolock) Where convert(varchar(10),lh.field_id) = ld.field_id and ld.value = '" + 
      
      value + "' and " + 
      "lh.field_name = 'department'";
    JdbcConnector connector = createConnector(query);
    connector.runQuery();
    if (connector.more()) {
      connector.close();
      return connector.getIntegerField("field_id");
    } 
    connector.close();
    return -1;
  }
  
  public static String getDepartmentAbbreviation(String description) {
    String query = "Select DISTINCT ld.description From vi_lookup_detail ld, vi_lookup_header lh with (nolock), vi_task tk with (nolock) Where convert(varchar(10),lh.field_id) = ld.field_id and convert(varchar(10),tk.abbrev_id) = ld.value and ld.description = " + 
      
      description + " and " + 
      "lh.field_name = 'department'";
    JdbcConnector connector = createConnector(query);
    connector.runQuery();
    if (connector.more()) {
      connector.close();
      return connector.getField("value");
    } 
    connector.close();
    return null;
  }
  
  public static Selection getScreenSelection(Context context) {
    Selection selection = null;
    int selectionID = -1;
    Notepad notepad = getNotepadFromSession(0, context);
    if (context.getRequestValue("selection-id") != null) {
      selectionID = Integer.parseInt(context.getRequestValue("selection-id"));
      selection = SelectionManager.getInstance().getSelectionHeader(selectionID);
      notepad.setSelected(selection);
    } else if ((Selection)notepad.getSelected() != null) {
      Selection notepadSelection = (Selection)notepad.getSelected();
      if (notepadSelection.isFullSelection()) {
        selectionID = notepadSelection.getSelectionID();
        selection = SelectionManager.getInstance().getSelectionHeader(selectionID);
      } else {
        selectionID = notepadSelection.getSelectionID();
        selection = SelectionManager.getInstance().getSelectionHeader(selectionID);
        notepad.setSelected(selection);
      } 
    } else if (notepad.getAllContents() != null && notepad.getAllContents().size() > 0) {
      selection = (Selection)notepad.getAllContents().get(0);
      notepad.setSelected(selection);
    } 
    return selection;
  }
  
  public static Task getScreenTask(Context context) {
    Task task = null;
    int taskID = -1;
    Notepad notepad = getNotepadFromSession(4, context);
    if (context.getRequestValue("task-id") != null) {
      taskID = Integer.parseInt(context.getRequestValue("task-id"));
      task = TaskManager.getInstance().getTask(taskID, true);
      context.putSessionValue("Task", task);
      notepad.setSelected(task);
    } else if ((Task)notepad.getSelected() != null) {
      Task notepadTask = (Task)notepad.getSelected();
      taskID = notepadTask.getTaskID();
      if (notepadTask.getLastUpdatedCk() > 0L) {
        task = TaskManager.getInstance().getTask(taskID, false);
        task.setLastUpdatedCk(notepadTask.getLastUpdatedCk());
      } else {
        task = TaskManager.getInstance().getTask(taskID, true);
      } 
      context.putSessionValue("Task", task);
    } else if (notepad.getAllContents() != null && notepad.getAllContents().size() > 0) {
      task = (Task)notepad.getAllContents().get(0);
      notepad.setSelected(task);
    } 
    return task;
  }
  
  public static User getScreenUser(Context context) {
    User user = null;
    int userID = -1;
    Notepad notepad = getNotepadFromSession(7, context);
    if (context.getRequestValue("user-id") != null) {
      userID = Integer.parseInt(context.getRequestValue("user-id"));
      user = UserManager.getInstance().getUser(userID, true);
      context.putSessionValue("securityUser", user);
      notepad.setSelected(user);
    } else if ((User)notepad.getSelected() != null) {
      User notepadUser = (User)notepad.getSelected();
      userID = notepadUser.getUserId();
      if (notepadUser.getLastUpdatedCk() > 0L) {
        user = UserManager.getInstance().getUser(userID, false);
        user.setLastUpdatedCk(notepadUser.getLastUpdatedCk());
      } else {
        user = UserManager.getInstance().getUser(userID, true);
      } 
      context.putSessionValue("securityUser", user);
    } else if (notepad.getAllContents() != null && notepad.getAllContents().size() > 0) {
      user = (User)notepad.getAllContents().get(0);
      context.putSessionValue("securityUser", user);
      notepad.setSelected(user);
    } 
    return user;
  }
  
  public static Company getScreenUserCompany(Context context) {
    Company company = null;
    int companyID = -1;
    Notepad notepad = getNotepadFromSession(8, context);
    if (context.getRequestValue("company-id") != null) {
      try {
        companyID = Integer.parseInt(context.getRequestValue("company-id"));
        company = (Company)getStructureObject(companyID);
        context.putSessionValue("UserCompany", company);
        notepad.setSelected(company);
      } catch (ClassCastException classCastException) {}
    } else if ((Company)notepad.getSelected() != null) {
      try {
        Company notepadCompany = (Company)notepad.getSelected();
        companyID = notepadCompany.getStructureID();
        company = (Company)getStructureObject(companyID);
        context.putSessionValue("UserCompany", company);
      } catch (ClassCastException classCastException) {}
    } else if (notepad.getAllContents() != null && notepad.getAllContents().size() > 0) {
      company = (Company)notepad.getAllContents().get(0);
      context.putSessionValue("UserCompany", company);
      notepad.setSelected(company);
    } 
    return company;
  }
  
  public static Day getScreenDay(Context context) {
    Day day = null;
    int dayID = -1;
    Notepad notepad = getNotepadFromSession(6, context);
    if (context.getRequestValue("day-id") != null) {
      dayID = Integer.parseInt(context.getRequestValue("day-id"));
      day = DayManager.getInstance().getDay(dayID, true);
      context.putSessionValue("Day", day);
      notepad.setSelected(day);
    } else if ((Day)notepad.getSelected() != null) {
      Day notepadDay = (Day)notepad.getSelected();
      dayID = notepadDay.getDayID();
      if (notepadDay.getLastUpdatedCk() > 0L) {
        day = DayManager.getInstance().getDay(dayID, false);
        day.setLastUpdatedCk(notepadDay.getLastUpdatedCk());
      } else {
        day = DayManager.getInstance().getDay(dayID, true);
      } 
      context.putSessionValue("Day", day);
    } else if (notepad.getAllContents() != null && notepad.getAllContents().size() > 0) {
      day = (Day)notepad.getAllContents().get(0);
      notepad.setSelected(day);
    } 
    return day;
  }
  
  public static Family getScreenFamily(Context context) {
    Family family = null;
    int familyID = -1;
    Notepad notepad = getNotepadFromSession(9, context);
    if (context.getRequestValue("family-id") != null) {
      familyID = Integer.parseInt(context.getRequestValue("family-id"));
      family = FamilyManager.getInstance().getFamily(familyID);
      context.putSessionValue("Family", family);
      notepad.setSelected(family);
    } else if ((Family)notepad.getSelected() != null) {
      family = (Family)notepad.getSelected();
      family = (Family)getStructureObject(family.getStructureID());
    } else if (notepad.getAllContents() != null && notepad.getAllContents().size() > 0) {
      family = (Family)notepad.getAllContents().get(0);
      notepad.setSelected(family);
      context.putSessionValue("Family", family);
    } 
    return family;
  }
  
  public static Company getScreenCompany(Context context) {
    Company company = null;
    int companyID = -1;
    Notepad notepad = getNotepadFromSession(10, context);
    if (context.getRequestValue("company-id") != null) {
      companyID = Integer.parseInt(context.getRequestValue("company-id"));
      company = CompanyManager.getInstance().getCompany(companyID);
      context.putSessionValue("Company", company);
      notepad.setSelected(company);
    } else if ((Company)notepad.getSelected() != null) {
      company = (Company)notepad.getSelected();
      company = (Company)getStructureObject(company.getStructureID());
    } else if (notepad.getAllContents() != null && notepad.getAllContents().size() > 0) {
      company = (Company)notepad.getAllContents().get(0);
      notepad.setSelected(company);
      context.putSessionValue("Company", company);
    } 
    return company;
  }
  
  public static Division getScreenDivision(Context context) {
    Division division = null;
    int divisionID = -1;
    Notepad notepad = getNotepadFromSession(11, context);
    if (context.getRequestValue("division-id") != null) {
      divisionID = Integer.parseInt(context.getRequestValue("division-id"));
      division = DivisionManager.getInstance().getDivision(divisionID);
      context.putSessionValue("Division", division);
      notepad.setSelected(division);
    } else if ((Division)notepad.getSelected() != null) {
      division = (Division)notepad.getSelected();
      division = (Division)getStructureObject(division.getStructureID());
    } else if (notepad.getAllContents() != null && notepad.getAllContents().size() > 0) {
      division = (Division)notepad.getAllContents().get(0);
      notepad.setSelected(division);
      context.putSessionValue("Division", division);
    } 
    return division;
  }
  
  public static Label getScreenLabel(Context context) {
    Label label = null;
    int labelID = -1;
    Notepad notepad = getNotepadFromSession(12, context);
    if (context.getRequestValue("label-id") != null) {
      labelID = Integer.parseInt(context.getRequestValue("label-id"));
      label = LabelManager.getInstance().getLabel(labelID);
      context.putSessionValue("Label", label);
      notepad.setSelected(label);
    } else if ((Label)notepad.getSelected() != null) {
      label = (Label)notepad.getSelected();
      label = (Label)getStructureObject(label.getStructureID());
    } else if (notepad.getAllContents() != null && notepad.getAllContents().size() > 0) {
      label = (Label)notepad.getAllContents().get(0);
      notepad.setSelected(label);
      context.putSessionValue("Label", label);
    } 
    return label;
  }
  
  public static Template getScreenTemplate(Context context) {
    Template template = null;
    int templateID = -1;
    Notepad notepad = getNotepadFromSession(5, context);
    if (context.getRequestValue("template-id") != null) {
      templateID = Integer.parseInt(context.getRequestValue("template-id"));
      template = TemplateManager.getInstance().getTemplate(templateID, true);
      context.putSessionValue("Template", template);
      notepad.setSelected(template);
    } else if ((Template)notepad.getSelected() != null) {
      Template notepadTemplate = (Template)notepad.getSelected();
      templateID = notepadTemplate.getTemplateID();
      if (notepadTemplate.getLastUpdatedCk() > 0L) {
        template = TemplateManager.getInstance().getTemplate(templateID, false);
        template.setLastUpdatedCk(notepadTemplate.getLastUpdatedCk());
      } else {
        template = TemplateManager.getInstance().getTemplate(templateID, true);
      } 
      context.putSessionValue("Template", template);
    } else if (notepad.getAllContents() != null && notepad.getAllContents().size() > 0) {
      template = (Template)notepad.getAllContents().get(0);
      notepad.setSelected(template);
    } 
    return template;
  }
  
  public static PriceCode getScreenPriceCode(Context context) {
    PriceCode priceCode = null;
    String priceCodeID = null;
    boolean priceCodeIsDigital = false;
    Notepad notepad = getNotepadFromSession(16, context);
    if (context.getRequestValue("pricecode-id") != null) {
      priceCodeID = context.getRequestValue("pricecode-id");
      String priceCodeIsDigitalStr = context.getRequestValue("pricecode-isDigital");
      if (priceCodeIsDigitalStr != null && priceCodeIsDigitalStr.compareToIgnoreCase("true") != -1)
        priceCodeIsDigital = true; 
      priceCode = PriceCodeManager.getInstance().getPriceCode(priceCodeID, true, priceCodeIsDigital);
      context.putSessionValue("PriceCode", priceCode);
      notepad.setSelected(priceCode);
    } else if ((PriceCode)notepad.getSelected() != null) {
      PriceCode notepadPriceCode = (PriceCode)notepad.getSelected();
      priceCodeID = notepadPriceCode.getSellCode();
      priceCodeIsDigital = notepadPriceCode.getIsDigital();
      if (notepadPriceCode.getLastUpdatedCheck() > 0L) {
        priceCode = PriceCodeManager.getInstance().getPriceCode(priceCodeID, false, priceCodeIsDigital);
        priceCode.setLastUpdatedCheck(notepadPriceCode.getLastUpdatedCheck());
      } else {
        priceCode = PriceCodeManager.getInstance().getPriceCode(priceCodeID, true, priceCodeIsDigital);
      } 
      context.putSessionValue("PriceCode", priceCode);
    } else if (notepad.getAllContents() != null && notepad.getAllContents().size() > 0) {
      priceCode = (PriceCode)notepad.getAllContents().get(0);
      notepad.setSelected(priceCode);
    } 
    return priceCode;
  }
  
  public static ReleaseWeek getScreenReleaseWeek(Context context) {
    ReleaseWeek releaseWeek = null;
    String releaseWeekID = null;
    Notepad notepad = getNotepadFromSession(15, context);
    if (context.getRequestValue("releaseweek-id") != null) {
      releaseWeekID = context.getRequestValue("releaseweek-id");
      releaseWeek = ReleaseWeekManager.getInstance().getReleaseWeek(releaseWeekID, true);
      context.putSessionValue("ReleaseWeek", releaseWeek);
      notepad.setSelected(releaseWeek);
    } else if ((ReleaseWeek)notepad.getSelected() != null) {
      ReleaseWeek notepadReleaseWeek = (ReleaseWeek)notepad.getSelected();
      releaseWeekID = notepadReleaseWeek.getName();
      if (notepadReleaseWeek.getLastUpdatedCheck() > 0L) {
        releaseWeek = ReleaseWeekManager.getInstance().getReleaseWeek(releaseWeekID, false);
        releaseWeek.setLastUpdatedCheck(notepadReleaseWeek.getLastUpdatedCheck());
      } else {
        releaseWeek = ReleaseWeekManager.getInstance().getReleaseWeek(releaseWeekID, true);
      } 
      context.putSessionValue("ReleaseWeek", releaseWeek);
    } else if (notepad.getAllContents() != null && notepad.getAllContents().size() > 0) {
      releaseWeek = (ReleaseWeek)notepad.getAllContents().get(0);
      notepad.setSelected(releaseWeek);
    } 
    return releaseWeek;
  }
  
  public static Report getScreenReportConfig(Context context) {
    Report reportConfig = null;
    int reportID = -1;
    Notepad notepad = getNotepadFromSession(17, context);
    if (context.getRequestValue("report-config-id") != null) {
      reportID = Integer.parseInt(context.getRequestValue("report-config-id"));
      reportConfig = ReportConfigManager.getInstance().getReportConfig(reportID, true);
      context.putSessionValue("ReportConfig", reportConfig);
      notepad.setSelected(reportConfig);
    } else if ((Report)notepad.getSelected() != null) {
      Report notepadReport = (Report)notepad.getSelected();
      reportID = notepadReport.getReportID();
      if (notepadReport.getLastUpdatedCk() > 0L) {
        reportConfig = ReportConfigManager.getInstance().getReportConfig(reportID, false);
        reportConfig.setLastUpdatedCk(notepadReport.getLastUpdatedCk());
      } else {
        reportConfig = ReportConfigManager.getInstance().getReportConfig(reportID, true);
      } 
      context.putSessionValue("ReportConfig", reportConfig);
    } else if (notepad.getAllContents() != null && notepad.getAllContents().size() > 0) {
      reportConfig = (Report)notepad.getAllContents().get(0);
      notepad.setSelected(reportConfig);
    } 
    return reportConfig;
  }
  
  public static Report getScreenReport(Context context) {
    Report report = null;
    int reportID = -1;
    Notepad notepad = getNotepadFromSession(3, context);
    if (context.getRequestValue("report-id") != null) {
      reportID = Integer.parseInt(context.getRequestValue("report-id"));
      report = ReportConfigManager.getInstance().getReportConfig(reportID, true);
      context.putSessionValue("Report", report);
      notepad.setSelected(report);
    } else if ((Report)notepad.getSelected() != null) {
      Report notepadReport = (Report)notepad.getSelected();
      reportID = notepadReport.getReportID();
      report = ReportConfigManager.getInstance().getReportConfig(reportID, true);
      context.putSessionValue("Report", report);
    } 
    return report;
  }
  
  public static Table getScreenTable(Context context) {
    Table table = null;
    int tableID = -1;
    Notepad notepad = getNotepadFromSession(14, context);
    if (context.getRequestValue("table-id") != null) {
      tableID = Integer.parseInt(context.getRequestValue("table-id"));
      table = TableManager.getInstance().getTable(tableID, true);
      context.putSessionValue("Table", table);
      notepad.setSelected(table);
    } else if ((Table)notepad.getSelected() != null) {
      Table notepadTable = (Table)notepad.getSelected();
      tableID = notepadTable.getId();
      if (notepadTable.getLastUpdatedCk() > 0L) {
        table = TableManager.getInstance().getTable(tableID, false);
        table.setLastUpdatedCk(notepadTable.getLastUpdatedCk());
      } else {
        table = TableManager.getInstance().getTable(tableID, true);
      } 
      context.putSessionValue("Table", table);
    } else if (notepad.getAllContents() != null && notepad.getAllContents().size() > 0) {
      table = (Table)notepad.getAllContents().get(0);
      notepad.setSelected(table);
    } 
    return table;
  }
  
  public static Table getScreenTableDetail(Context context) {
    Table tableDetail = null;
    int tableID = -1;
    String tableDetailID = "";
    Notepad notepad = getNotepadFromSession(14, context);
    Notepad notepadDetail = getNotepadFromSession(18, context);
    if (context.getRequestValue("table-id") != null && context.getRequestValue("table-detail-id") != null) {
      tableID = Integer.parseInt(context.getRequestValue("table-id"));
      tableDetailID = context.getRequestValue("table-detail-id");
      tableDetail = TableManager.getInstance().getTableDetail(tableID, tableDetailID, true);
      context.putSessionValue("TableDetail", tableDetail);
      notepadDetail.setSelected(tableDetail);
    } else if ((Table)notepad.getSelected() != null) {
      Table notepadTable = (Table)notepad.getSelected();
      Table notepadTableDetail = (Table)notepadDetail.getSelected();
      LookupObject detail = null;
      if (notepadTable != null)
        tableID = notepadTable.getId(); 
      if (notepadTableDetail != null) {
        detail = notepadTableDetail.getDetail();
        tableDetailID = detail.getAbbreviation();
      } 
      tableDetail = TableManager.getInstance().getTableDetail(tableID, tableDetailID, true);
      context.putSessionValue("TableDetail", tableDetail);
    } else if (notepadDetail.getAllContents() != null && notepadDetail.getAllContents().size() > 0) {
      tableDetail = (Table)notepadDetail.getAllContents().get(0);
      notepadDetail.setSelected(tableDetail);
      context.putSessionValue("TableDetail", tableDetail);
    } 
    return tableDetail;
  }
  
  public static String formatDollarPrice(String price) {
    if (price.substring(price.length() - 2, 
        price.length() - 1).compareTo(".") == 0)
      return String.valueOf(price) + "0"; 
    return price;
  }
  
  public static String formatDollarPrice(float fltPrice) { return formatDollarPrice(Float.toString(fltPrice)); }
  
  public static String getLookupObjectValue(LookupObject lookupObject) {
    String lookupValue = "";
    if (lookupObject != null)
      lookupValue = lookupObject.getAbbreviation(); 
    return lookupValue;
  }
  
  public static SelectionConfiguration getSelectionConfigObject(String abbreviation, Vector configs) {
    for (int j = 0; j < configs.size(); j++) {
      SelectionConfiguration selectionConfiguration = (SelectionConfiguration)configs.get(j);
      if (selectionConfiguration.getSelectionConfigurationAbbreviation().equalsIgnoreCase(abbreviation))
        return selectionConfiguration; 
    } 
    return null;
  }
  
  public static SelectionSubConfiguration getSelectionSubConfigObject(String abbreviation, SelectionConfiguration config) {
    Vector subConfigs = config.getSubConfigurations();
    for (int j = 0; j < subConfigs.size(); j++) {
      SelectionSubConfiguration subConfig = (SelectionSubConfiguration)subConfigs.get(j);
      if (subConfig.getSelectionSubConfigurationAbbreviation().equalsIgnoreCase(abbreviation))
        return subConfig; 
    } 
    return null;
  }
  
  public static LookupObject getLookupObject(String abbreviation, Vector lookupVector) {
    for (int j = 0; j < lookupVector.size(); j++) {
      LookupObject lookupObject = (LookupObject)lookupVector.get(j);
      if (lookupObject.getAbbreviation().equalsIgnoreCase(abbreviation))
        return lookupObject; 
    } 
    return null;
  }
  
  public static DatePeriod findDatePeriod(Calendar date) {
    Vector datePeriods = Cache.getDatePeriods();
    DatePeriod datePeriod = null;
    if (date != null) {
      for (int i = 0; i < datePeriods.size(); i++) {
        DatePeriod tmpDP = (DatePeriod)datePeriods.elementAt(i);
        if (i != datePeriods.size())
          if ((date.getTime().compareTo(tmpDP.getStartDate().getTime()) == 0 || date.after(tmpDP.getStartDate())) && (
            date.before(tmpDP.getEndDate()) || date.getTime().compareTo(tmpDP.getEndDate().getTime()) == 0))
            return tmpDP;  
      } 
      datePeriod = (DatePeriod)datePeriods.elementAt(datePeriods.size() - 1);
    } 
    return datePeriod;
  }
  
  public static String getReleaseWeekString(Selection selection) {
    if (selection.getStreetDate() != null) {
      DatePeriod datePeriod = findDatePeriod(selection.getStreetDate());
      return String.valueOf(datePeriod.getName()) + " / " + datePeriod.getCycle();
    } 
    return "";
  }
  
  public static DatePeriod getReleaseWeek(Selection selection) {
    if (selection.getStreetDate() != null)
      return findDatePeriod(selection.getStreetDate()); 
    return null;
  }
  
  public static Calendar getDueDate(Calendar pStreetDate, int pDayOfWeek, int pWeeksToRelease) {
    Calendar calendar = Calendar.getInstance();
    if (pStreetDate == null) {
      calendar = Calendar.getInstance();
    } else {
      calendar = (Calendar)pStreetDate.clone();
    } 
    if (pDayOfWeek < 0 || pDayOfWeek > 7) {
      int dayOfTheWeek = calendar.get(7);
      int liRest = pWeeksToRelease - dayOfTheWeek + 1;
      int liOffset = 0;
      if (liRest >= 0)
        liOffset = (liRest / 5 + 1) * 2; 
      calendar.add(6, -pWeeksToRelease - liOffset);
    } else {
      calendar.set(7, pDayOfWeek + 1);
      calendar.add(3, -pWeeksToRelease);
    } 
    return calendar;
  }
  
  public static boolean isStringNotEmpty(String theString) { return (theString != null && !theString.equals("")); }
  
  public static String addQueryParams(String params, String newParam) {
    if (!isStringNotEmpty(params) || params.toUpperCase().indexOf("WHERE") < 0) {
      params = " WHERE " + newParam;
    } else {
      params = " AND " + newParam;
    } 
    return params;
  }
  
  public static Vector filterCorporateByDescription(Vector corporateObjects, String filter) {
    if (isStringNotEmpty(filter)) {
      int searchType = 0;
      filter = filter.replace('*', '%');
      int firstWildOccur = filter.indexOf("%");
      int lastWildOccur = filter.lastIndexOf("%");
      if (firstWildOccur < 0) {
        searchType = 0;
      } else if (firstWildOccur > -1 && lastWildOccur > -1 && firstWildOccur != lastWildOccur) {
        searchType = 1;
        filter = filter.substring(firstWildOccur + 1, lastWildOccur);
      } else if (firstWildOccur == 0) {
        searchType = 2;
        filter = filter.substring(1, filter.length());
      } else {
        searchType = 3;
        filter = filter.substring(0, filter.length() - 1);
      } 
      Vector filteredObjects = new Vector();
      filter = filter.toUpperCase();
      for (int i = 0; i < corporateObjects.size(); i++) {
        CorporateStructureObject corporateObject = (CorporateStructureObject)corporateObjects.get(i);
        String description = corporateObject.getName().toUpperCase();
        switch (searchType) {
          case 0:
            if (description.indexOf(filter) >= 0)
              filteredObjects.add(corporateObject); 
            break;
          case 1:
            if (description.indexOf(filter) >= 0)
              filteredObjects.add(corporateObject); 
            break;
          case 2:
            try {
              if (description.indexOf(filter) > -1 && 
                description.indexOf(filter) == description.length() - filter.length())
                filteredObjects.add(corporateObject); 
            } catch (Exception exception) {}
            break;
          case 3:
            if (description.indexOf(filter) == 0)
              filteredObjects.add(corporateObject); 
            break;
        } 
      } 
      return filteredObjects;
    } 
    return corporateObjects;
  }
  
  public static CompanyAcl getScreenPermissions(Company company, User user) {
    Hashtable companyAclHash = user.getAcl().getCompanyAclHash();
    int companyId = company.getStructureID();
    if (companyAclHash != null)
      return (CompanyAcl)companyAclHash.get(String.valueOf(companyId)); 
    return null;
  }
  
  public static String getOwnerCompanyWhereClause(Context context) {
    Vector companies = getUserCompanies(context);
    Company company = null;
    StringList list = new StringList(" OR ");
    for (int i = 0; i < companies.size(); i++) {
      company = (Company)companies.elementAt(i);
      if (company != null && company.getParentEnvironment().getParentFamily() != null)
        list.add("owner = " + company.getParentEnvironment().getParentFamily().getStructureID()); 
    } 
    String whereClause = list.toString();
    if (whereClause.length() > 1) {
      whereClause = " WHERE (" + whereClause + ")";
    } else {
      whereClause = " WHERE (owner = -1)";
    } 
    return whereClause;
  }
  
  public static boolean getNotePadVisiblitiy(int type, Context context) {
    Notepad notepad = getNotepadFromSession(type, context);
    boolean result = true;
    if (notepad != null) {
      if (context.getParameter("showNotepad") != null && context.getParameter("showNotepad").equalsIgnoreCase("true"))
        notepad.setVisible(true); 
      result = notepad.isVisible();
    } 
    return result;
  }
  
  public static Object[] sortStringVector(Vector stringVector) {
    Vector sortedVector = new Vector();
    Object[] stringArray = null;
    if (stringVector != null) {
      stringArray = stringVector.toArray();
      Arrays.sort(stringArray, new StringComparator());
    } 
    return stringArray;
  }
  
  public static Vector sortStrings(Vector stringVector) {
    Vector sortedVector = new Vector();
    Object[] stringArray = null;
    if (stringVector != null) {
      stringArray = stringVector.toArray();
      Arrays.sort(stringArray);
      for (int i = 0; i < stringArray.length; i++) {
        String cso = (String)stringArray[i];
        sortedVector.add(cso);
      } 
    } 
    return sortedVector;
  }
  
  public static Vector sortDates(Vector stringVector) {
    Vector sortedVector = new Vector();
    Object[] stringArray = null;
    if (stringVector != null) {
      stringArray = stringVector.toArray();
      Arrays.sort(stringArray, new StringDateComparator());
      for (int i = 0; i < stringArray.length; i++) {
        String cso = (String)stringArray[i];
        sortedVector.add(cso);
      } 
    } 
    return sortedVector;
  }
  
  public static Vector sortCorporateVectorByName(Vector csoVector) {
    Vector sortedVector = new Vector();
    if (csoVector != null) {
      Object[] csoArray = csoVector.toArray();
      Arrays.sort(csoArray, new CorpStructNameComparator());
      for (int i = 0; i < csoArray.length; i++) {
        CorporateStructureObject cso = (CorporateStructureObject)csoArray[i];
        sortedVector.add(cso);
      } 
    } 
    return sortedVector;
  }
  
  public static Vector sortCorporateVectorByParentName(Vector csoVector) {
    Vector sortedVector = new Vector();
    if (csoVector != null) {
      Object[] csoArray = csoVector.toArray();
      Arrays.sort(csoArray, new CorpStructParentNameComparator());
      for (int i = 0; i < csoArray.length; i++) {
        CorporateStructureObject cso = (CorporateStructureObject)csoArray[i];
        sortedVector.add(cso);
      } 
    } 
    return sortedVector;
  }
  
  public static Vector sortSelectionVector(Vector csoVector) {
    Vector sortedVector = new Vector();
    if (csoVector != null) {
      Object[] csoArray = csoVector.toArray();
      Arrays.sort(csoArray);
      for (int i = 0; i < csoArray.length; i++) {
        Selection cso = (Selection)csoArray[i];
        sortedVector.add(cso);
      } 
    } 
    return sortedVector;
  }
  
  public static String setWildCardsEscapeSingleQuotes(String wildCard) {
    if (wildCard != null && !wildCard.equals("")) {
      wildCard = escapeSingleQuotes(wildCard);
      if (wildCard.indexOf("%") > -1 || wildCard.indexOf("*") > -1) {
        wildCard = wildCard.replace('*', '%');
        wildCard = " like '" + wildCard + "'";
      } else {
        wildCard = " like '%" + wildCard + "%'";
      } 
    } 
    return wildCard;
  }
  
  public static LookupObject getSupplierById(String id) {
    Vector suppliers = Cache.getInstance().getBomSuppliers();
    if (suppliers != null)
      for (int i = 0; i < suppliers.size(); i++) {
        LookupObject supplierTemp = (LookupObject)suppliers.elementAt(i);
        if (supplierTemp != null && supplierTemp.getAbbreviation().equals(id))
          return supplierTemp; 
      }  
    return null;
  }
  
  public static Vector getBomSuppliers(int partId) { return getBomSuppliers(partId, "0,1"); }
  
  public static Vector getBomSuppliers(int partId, int prevSupplier) { return getBomSuppliers(partId, "0,1", prevSupplier); }
  
  public static Vector getBomSuppliers(int partId, String flag) {
    String query = "SELECT * FROM vi_Supplier s with (nolock), vi_Part_Supplier ps with (nolock) WHERE ps.part_id =" + 
      
      partId + 
      " AND ps.supplier_id = s.supplier_id" + 
      " AND (s.inactive is null or s.inactive = 0) " + 
      " AND typeFlag in (" + flag + ")" + 
      " ORDER BY s.description;";
    JdbcConnector connector = getConnector(query);
    connector.runQuery();
    Vector precache = new Vector();
    while (connector.more()) {
      LookupObject lookupObject = new LookupObject();
      lookupObject.setAbbreviation(connector.getField("supplier_id", ""));
      lookupObject.setName(connector.getField("description", ""));
      precache.addElement(lookupObject);
      connector.next();
    } 
    bomSuppliers = precache;
    connector.close();
    return bomSuppliers;
  }
  
  public static Vector getBomSuppliers(int partId, String flag, int prevSupplier) {
    String query = "SELECT * FROM vi_Supplier s with (nolock), vi_Part_Supplier ps with (nolock) WHERE ps.part_id =" + 
      
      partId + 
      " AND ps.supplier_id = s.supplier_id" + 
      " AND ((s.inactive is null or s.inactive = 0) OR s.supplier_id = " + prevSupplier + ") " + 
      " AND typeFlag in (" + flag + ")" + 
      " ORDER BY s.description;";
    JdbcConnector connector = getConnector(query);
    connector.runQuery();
    Vector precache = new Vector();
    while (connector.more()) {
      LookupObject lookupObject = new LookupObject();
      lookupObject.setAbbreviation(connector.getField("supplier_id", ""));
      lookupObject.setName(connector.getField("description", ""));
      precache.addElement(lookupObject);
      connector.next();
    } 
    bomSuppliers = precache;
    connector.close();
    return bomSuppliers;
  }
  
  public static Company getCompanyById(int id) {
    Vector companies = Cache.getInstance().getCompanies();
    if (companies != null)
      for (int i = 0; i < companies.size(); i++) {
        Company companyTemp = (Company)companies.elementAt(i);
        if (companyTemp != null && companyTemp.getIdentity() == id)
          return companyTemp; 
      }  
    return null;
  }
  
  public static Vector filterUmlTasks(Vector tasks, boolean uml) {
    Vector filteredTasks = new Vector();
    for (int i = 0; i < tasks.size(); i++) {
      ScheduledTask task = (ScheduledTask)tasks.get(i);
      if (uml) {
        if (isUml(task))
          filteredTasks.add(task); 
      } else if (!isUml(task)) {
        filteredTasks.add(task);
      } 
    } 
    return filteredTasks;
  }
  
  public static boolean isUml(ScheduledTask task) {
    Family family = (task.getOwner() != null) ? task.getOwner() : null;
    if (family != null) {
      String familyName = family.getName();
      familyName = familyName.trim();
      return familyName.equalsIgnoreCase("UML");
    } 
    return false;
  }
  
  public static boolean isUml(Task task) {
    Family family = (task.getOwner() != null) ? task.getOwner() : null;
    if (family != null)
      return (family.getStructureID() == 417); 
    return false;
  }
  
  public static boolean isUml(Family family) {
    if (family != null)
      return (family.getStructureID() == 417); 
    return false;
  }
  
  public static boolean hasUmlTasks(Schedule schedule) {
    boolean hasUmlTask = false;
    if (schedule != null && schedule.getTasks().size() > 0) {
      Vector tasks = schedule.getTasks();
      ScheduledTask scheduledTask = null;
      for (int i = 0; i < tasks.size(); i++) {
        scheduledTask = (ScheduledTask)tasks.get(i);
        Family family = (scheduledTask.getOwner() != null) ? scheduledTask.getOwner() : null;
        if (family != null) {
          String familyName = family.getName();
          familyName = familyName.trim();
          hasUmlTask = familyName.equalsIgnoreCase("UML");
          if (hasUmlTask)
            return hasUmlTask; 
        } 
        scheduledTask = null;
      } 
    } 
    return hasUmlTask;
  }
  
  public static boolean isEcommerce(ScheduledTask task) {
    Family family = (task.getOwner() != null) ? task.getOwner() : null;
    if (family != null) {
      String familyName = family.getName();
      familyName = familyName.trim();
      return familyName.equalsIgnoreCase("eCommerce");
    } 
    return false;
  }
  
  public static boolean isUmlUser(User user) {
    Acl acl = user.getAcl();
    Vector envAcls = acl.getEnvironmentAcl();
    String envName = "";
    EnvironmentAcl envAcl = null;
    for (int i = 0; i < envAcls.size(); i++) {
      envAcl = (EnvironmentAcl)envAcls.get(i);
      int aclEnvironmentId = envAcl.getEnvironmentId();
      envName = getStructureName(aclEnvironmentId);
      if (envName.equalsIgnoreCase("UML"))
        return true; 
      envName = "";
    } 
    return false;
  }
  
  public static String[] parseFilter(String filter) {
    String filterString = "All";
    String filterFlag = "Yes";
    String userFilter = filter;
    String[] filterArray = { "Yes", "All" };
    if (userFilter != null && userFilter.length() > 0) {
      if (userFilter.substring(0, 0).equalsIgnoreCase("t")) {
        filterFlag = userFilter.substring(0, 3);
      } else {
        filterFlag = userFilter.substring(0, 4);
      } 
      if (filterFlag.equalsIgnoreCase("true")) {
        filterString = userFilter.substring(5, userFilter.length());
      } else {
        filterString = userFilter.substring(6, userFilter.length());
      } 
      if (filterFlag.equalsIgnoreCase("true")) {
        filterFlag = "Yes";
      } else {
        filterFlag = "No";
      } 
      if (filterString.equalsIgnoreCase("milestone.filter.FilterNone")) {
        filterString = "All";
      } else if (filterString.equalsIgnoreCase("milestone.filter.FilterExcludeOwnerUML")) {
        filterString = "Only Label Tasks";
      } else if (filterString.equalsIgnoreCase("milestone.filter.FilterIncludeOwnerUML")) {
        filterString = "Only UML Tasks";
      } 
      filterArray[0] = filterFlag;
      filterArray[1] = filterString;
    } 
    return filterArray;
  }
  
  public static String getDayType(ScheduledTask scheduledTask) {
    System.out.println("***** MilestoneHelper.getDayType(ScheduledTask) has been replaced by getDayType(int, ScheduledTask)");
    return null;
  }
  
  public static String getDayType(Calendar date) {
    System.out.println("***** MilestoneHelper.getDayType(Calendar) has been replaced by getDayType(int, Calendar)");
    return null;
  }
  
  public static String getDayType(int calendarGroup, ScheduledTask scheduledTask) {
    if (scheduledTask != null)
      return getDayType(calendarGroup, scheduledTask.getDueDate()); 
    return "";
  }
  
  public static String getDayType(int calendarGroup, Calendar date) {
    String dayType = getCalendarGroupDayType(1, date);
    if (!isStringNotEmpty(dayType) && calendarGroup != 1)
      dayType = getCalendarGroupDayType(calendarGroup, date); 
    return dayType;
  }
  
  protected static String getCalendarGroupDayType(int calendarGroup, Calendar date) {
    String retDayTypeStr = "";
    if (date != null) {
      Vector dayTypes = Cache.getDayTypes();
      Vector groupDays = null;
      boolean found = false;
      int i = 0;
      while (!found && i < dayTypes.size()) {
        groupDays = (Vector)dayTypes.get(i);
        if (groupDays != null && groupDays.get(false) != null)
          if (((Day)groupDays.get(0)).getCalendarGroup() == calendarGroup)
            found = true;  
        i++;
      } 
      if (found && groupDays != null && groupDays.size() > 0) {
        Day dayType = null;
        boolean done = false;
        int j = 0;
        int groupDaysSize = groupDays.size();
        while (!done && j < groupDaysSize) {
          dayType = (Day)groupDays.get(j);
          if (dayType.getSpecificDate() != null && date.equals(dayType.getSpecificDate())) {
            retDayTypeStr = dayType.getDayType();
            done = true;
          } else if (date.after(dayType.getSpecificDate())) {
            done = true;
          } 
          j++;
        } 
      } 
    } 
    return retDayTypeStr;
  }
  
  public static boolean isTaskUsed(Task task) {
    boolean isTaskUsed = false;
    if (task != null) {
      int taskId = task.getTaskID();
      String query = "SELECT * from vi_release_detail a with (nolock) full outer join vi_template_detail b with (nolock) on a.task_id = b.task_id where a.task_id = " + 
        
        taskId + " or " + 
        "b.task_id = " + taskId;
      JdbcConnector connector = getConnector(query);
      connector.runQuery();
      if (connector.more())
        isTaskUsed = true; 
      connector.close();
    } 
    return isTaskUsed;
  }
  
  public static Hashtable groupSelectionsByStatus(Vector selections) {
    Hashtable groupedSelections = new Hashtable();
    if (selections == null)
      return groupedSelections; 
    for (int i = 0; i < selections.size(); i++) {
      Selection sel = (Selection)selections.elementAt(i);
      if (sel != null) {
        sel = SelectionManager.getInstance().getSelectionHeader(sel.getSelectionID());
        String selStatus = SelectionManager.getLookupObjectValue(sel.getSelectionStatus());
        Vector group = (Vector)groupedSelections.get(selStatus);
        if (group == null) {
          group = new Vector();
          group.addElement(sel);
          groupedSelections.put(selStatus, group);
        } else {
          group.addElement(sel);
        } 
      } 
    } 
    return groupedSelections;
  }
  
  public static Hashtable groupSelectionsByFamilyAndCompany(Vector selections) {
    Hashtable groupedByFamilyAndCompany = new Hashtable();
    if (selections == null)
      return groupedByFamilyAndCompany; 
    for (int i = 0; i < selections.size(); i++) {
      Selection sel = (Selection)selections.elementAt(i);
      if (sel != null) {
        String familyName = "", companyName = "";
        Family family = sel.getFamily();
        Company company = sel.getCompany();
        if (family != null)
          familyName = (family.getName() == null) ? "" : family.getName(); 
        if (company != null)
          companyName = (company.getName() == null) ? "" : company.getName(); 
        Hashtable familySubTable = (Hashtable)groupedByFamilyAndCompany.get(familyName);
        if (familySubTable == null) {
          familySubTable = new Hashtable();
          groupedByFamilyAndCompany.put(familyName, familySubTable);
        } 
        Vector selectionsForCompany = (Vector)familySubTable.get(companyName);
        if (selectionsForCompany == null) {
          selectionsForCompany = new Vector();
          familySubTable.put(companyName, selectionsForCompany);
        } 
        selectionsForCompany.addElement(sel);
      } 
    } 
    return groupedByFamilyAndCompany;
  }
  
  public static Hashtable groupSelectionsByCompanyAndSubconfig(Vector selections) {
    Hashtable groupedByCompanyAndSubconfig = new Hashtable();
    if (selections == null)
      return groupedByCompanyAndSubconfig; 
    for (int i = 0; i < selections.size(); i++) {
      Selection sel = (Selection)selections.elementAt(i);
      if (sel != null) {
        String familyName = "", companyName = "";
        String configName = "", subconfigName = "";
        Family family = sel.getFamily();
        Company company = sel.getCompany();
        SelectionConfiguration config = sel.getSelectionConfig();
        SelectionSubConfiguration subconfig = sel.getSelectionSubConfig();
        if (family != null)
          familyName = (family.getName() == null) ? "" : family.getName(); 
        if (company != null)
          companyName = (company.getName() == null) ? "" : company.getName(); 
        if (config != null)
          configName = (config.getSelectionConfigurationName() == null) ? 
            "" : config.getSelectionConfigurationName(); 
        if (subconfig != null)
          subconfigName = (subconfig.getSelectionSubConfigurationName() == null) ? 
            "" : subconfig.getSelectionSubConfigurationName(); 
        if (subconfigName.equalsIgnoreCase("EP/Sampler") || 
          subconfigName.equalsIgnoreCase("Full") || 
          subconfigName.equalsIgnoreCase("Full Length") || 
          configName.equalsIgnoreCase("DualDisc")) {
          subconfigName = "Full Length";
        } else if (!subconfigName.equals("")) {
          subconfigName = "Singles";
        } 
        Hashtable companySubTable = (Hashtable)groupedByCompanyAndSubconfig.get(companyName);
        if (companySubTable == null) {
          companySubTable = new Hashtable();
          groupedByCompanyAndSubconfig.put(companyName, companySubTable);
        } 
        Vector selectionsForSubconfig = (Vector)companySubTable.get(subconfigName);
        if (selectionsForSubconfig == null) {
          selectionsForSubconfig = new Vector();
          companySubTable.put(subconfigName, selectionsForSubconfig);
        } 
        selectionsForSubconfig.addElement(sel);
      } 
    } 
    return groupedByCompanyAndSubconfig;
  }
  
  protected static Vector filterSelectionsAndTaskWithReportCriteria(Vector selections, Context context) {
    Vector selectionsForReport = new Vector();
    Form reportForm = (Form)context.getSessionValue("reportForm");
    if (selections == null || selections.size() == 0)
      return selectionsForReport; 
    Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && reportForm.getStringValue("beginDate").length() > 0) ? getDate(reportForm.getStringValue("beginDate")) : null;
    if (beginStDate == null) {
      beginStDate = Calendar.getInstance();
      beginStDate.setTime(new Date(0L));
    } 
    beginStDate.set(11, 0);
    beginStDate.set(12, 0);
    beginStDate.set(13, 0);
    beginStDate.set(14, 0);
    Calendar endStDate = (reportForm.getStringValue("endDate") != null && reportForm.getStringValue("endDate").length() > 0) ? getDate(reportForm.getStringValue("endDate")) : null;
    if (endStDate != null) {
      endStDate.set(11, 23);
      endStDate.set(12, 59);
      endStDate.set(13, 59);
      endStDate.set(14, 999);
    } 
    String strDistribution = (reportForm.getStringValue("distribution") != null) ? reportForm.getStringValue("distribution") : "";
    String strLabelContact = (reportForm.getStringValue("contact") != null) ? reportForm.getStringValue("contact") : "";
    String strUmlContact = (reportForm.getStringValue("umlContact") != null) ? reportForm.getStringValue("umlContact") : "";
    String strFuture2 = (reportForm.getStringValue("Future2") != null) ? reportForm.getStringValue("Future2") : "";
    String strReleaseType = (reportForm.getStringValue("releaseType") != null) ? reportForm.getStringValue("releaseType") : "";
    String strStatus = (reportForm.getStringValue("status") != null) ? reportForm.getStringValue("status") : "";
    String strArtist = (reportForm.getStringValue("artist") != null) ? reportForm.getStringValue("artist") : "";
    String strFamily = (reportForm.getStringValue("family") != null) ? reportForm.getStringValue("family") : "";
    String strCompany = (reportForm.getStringValue("company") != null) ? reportForm.getStringValue("company") : "";
    String strLabel = (reportForm.getStringValue("Label") != null) ? reportForm.getStringValue("Label") : "";
    String[] strConfiguration = null;
    try {
      MilestoneFormDropDownMenu configList = (MilestoneFormDropDownMenu)reportForm.getElement("configurationList");
      if (configList != null) {
        ArrayList configListAl = configList.getStringValues();
        if (configListAl != null) {
          strConfiguration = new String[configListAl.size()];
          strConfiguration = (String[])configListAl.toArray(strConfiguration);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("<<< Configuration vector execption " + e.getMessage());
    } 
    String[] strSubconfiguration = null;
    try {
      MilestoneFormDropDownMenu subconfigList = (MilestoneFormDropDownMenu)reportForm.getElement("subconfigurationList");
      if (subconfigList != null) {
        ArrayList subconfigListAl = subconfigList.getStringValues();
        if (subconfigListAl != null) {
          strSubconfiguration = new String[subconfigListAl.size()];
          strSubconfiguration = (String[])subconfigListAl.toArray(strSubconfiguration);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("<<< Subconfiguration vector execption " + e.getMessage());
    } 
    boolean bParent = (reportForm.getElement("ParentsOnly") != null) ? ((FormCheckBox)reportForm.getElement("ParentsOnly")).isChecked() : 0;
    int totalCount = selections.size();
    int tenth = 0;
    tenth = totalCount / 5;
    if (tenth < 1)
      tenth = 1; 
    try {
      HttpServletResponse sresponse = context.getResponse();
      context.putDelivery("status", new String("start_gathering"));
      context.putDelivery("percent", new String("10"));
      context.includeJSP("status.jsp", "hiddenFrame");
      sresponse.setContentType("text/plain");
      sresponse.flushBuffer();
    } catch (Exception exception) {}
    int recordCount = 0;
    int count = 0;
    for (int i = 0; i < selections.size(); i++) {
      try {
        if (count < recordCount / tenth) {
          count = recordCount / tenth;
          HttpServletResponse sresponse = context.getResponse();
          context.putDelivery("status", new String("start_gathering"));
          context.putDelivery("percent", new String(String.valueOf(count * 10 + 50)));
          context.includeJSP("status.jsp", "hiddenFrame");
          sresponse.setContentType("text/plain");
          sresponse.flushBuffer();
        } 
        recordCount++;
        Selection temp_sel = (Selection)selections.elementAt(i);
        Selection sel = SelectionManager.getInstance().getSelectionHeader(temp_sel.getSelectionID());
        sel.setDueDate(temp_sel.getDueDate());
        sel.setDepartment(temp_sel.getDepartment());
        sel.setTaskName(temp_sel.getTaskName());
        sel.setCompletionDate(temp_sel.getCompletionDate());
        String status = "";
        if (sel.getSelectionStatus() != null)
          status = sel.getSelectionStatus().getName(); 
        if (!status.equalsIgnoreCase("TBS") && !status.equalsIgnoreCase("In the works")) {
          Calendar stDate = sel.getStreetDate();
          if (stDate != null && ((beginStDate != null && stDate.before(beginStDate)) || (endStDate != null && stDate.after(endStDate))))
            continue; 
        } 
        if (!strDistribution.equalsIgnoreCase("") && !strDistribution.equalsIgnoreCase("All")) {
          String selDistribution = "";
          if (sel.getLabel().getDistribution() == 1) {
            selDistribution = "East";
          } else if (sel.getLabel().getDistribution() == 0) {
            selDistribution = "West";
          } 
          if (!selDistribution.equalsIgnoreCase(strDistribution.trim()))
            continue; 
        } 
        if (!strLabelContact.trim().equalsIgnoreCase("0") && !strLabelContact.trim().equalsIgnoreCase("")) {
          if (sel.getLabelContact() == null)
            continue; 
          String selLabelContactName = Integer.toString(sel.getLabelContact().getUserId());
          if (!selLabelContactName.equalsIgnoreCase(strLabelContact))
            continue; 
        } 
        if (!strUmlContact.trim().equalsIgnoreCase("0") && !strUmlContact.trim().equalsIgnoreCase("")) {
          SelectionManager.getInstance().getSelectionManufacturingSubDetail(sel);
          if (sel.getUmlContact() == null)
            continue; 
          String selUmlContactName = Integer.toString(sel.getUmlContact().getUserId());
          if (!selUmlContactName.equalsIgnoreCase(strUmlContact))
            continue; 
        } 
        if (!strReleaseType.equalsIgnoreCase("") && !strReleaseType.equalsIgnoreCase("All")) {
          String releaseType = "";
          if (sel.getReleaseType() != null) {
            releaseType = (sel.getReleaseType().getName() != null) ? sel.getReleaseType().getName() : "";
            if (!releaseType.equalsIgnoreCase(strReleaseType))
              continue; 
          } else {
            continue;
          } 
        } 
        String selectionStatus = SelectionManager.getLookupObjectValue(sel.getSelectionStatus()).trim();
        if (!strStatus.equalsIgnoreCase("") && !strStatus.equalsIgnoreCase("All")) {
          if (strStatus.trim().equalsIgnoreCase("active")) {
            if (!selectionStatus.equalsIgnoreCase("TBS") && !selectionStatus.equalsIgnoreCase("ITW") && !selectionStatus.equalsIgnoreCase("active"))
              continue; 
          } else if (strStatus.trim().equalsIgnoreCase("Active, excluding TBS")) {
            if (!selectionStatus.trim().equalsIgnoreCase("active"))
              continue; 
          } else if (strStatus.trim().equalsIgnoreCase("TBS")) {
            if (!selectionStatus.trim().equalsIgnoreCase("TBS") && !selectionStatus.trim().equalsIgnoreCase("ITW"))
              continue; 
          } else if (strStatus.trim().equalsIgnoreCase("All, excluding TBS")) {
            if (!selectionStatus.trim().equalsIgnoreCase("active") && !selectionStatus.trim().equalsIgnoreCase("CLOSED"))
              continue; 
          } else if (strStatus.trim().equalsIgnoreCase("Cancelled")) {
            if (!selectionStatus.equalsIgnoreCase("CANCEL"))
              continue; 
          } else if (!selectionStatus.equalsIgnoreCase("CLOSED")) {
            continue;
          } 
        } else if (!selectionStatus.equalsIgnoreCase("CANCEL")) {
          continue;
        } 
        if (!strArtist.trim().equals("") && sel.getArtist() != null) {
          String artistUpperCase = sel.getArtist().trim().toUpperCase();
          if (!artistUpperCase.startsWith(strArtist.trim().toUpperCase()))
            continue; 
        } 
        if (!strFamily.equals("") && !strFamily.equals("0"))
          if (sel.getFamily() == null || !strFamily.equals(sel.getFamily().getIdentity()))
            continue;  
        if (!strCompany.equals("") && !strCompany.equals("0"))
          if (sel.getCompany() == null || !strCompany.equals(sel.getCompany().getStructureID()))
            continue;  
        if (!strLabel.equals("") && !strLabel.equals("0"))
          if (sel.getLabel() == null || !strLabel.equals(sel.getLabel().getStructureID()))
            continue;  
        if (bParent) {
          String prefixId = "";
          if (sel.getPrefixID() != null)
            prefixId = SelectionManager.getLookupObjectValue(sel.getPrefixID()); 
          if (!sel.getTitleID().equalsIgnoreCase(String.valueOf(prefixId) + sel.getSelectionNo()))
            continue; 
        } 
        if (strSubconfiguration != null && 
          !strSubconfiguration[0].trim().equalsIgnoreCase("0") && 
          !strSubconfiguration[0].trim().equalsIgnoreCase("") && 
          !strSubconfiguration[0].trim().equalsIgnoreCase("all")) {
          if (sel.getSelectionSubConfig() != null && sel.getSelectionConfig() != null) {
            String selConfigurationName = sel.getSelectionConfig().getSelectionConfigurationAbbreviation();
            String selSubconfigurationName = sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation();
            boolean subconfigFnd = false;
            for (int x = 0; x < strSubconfiguration.length; x++) {
              String txtvalue = strSubconfiguration[x];
              String txtconfigcode = txtvalue.substring(1, txtvalue.indexOf("]"));
              String txtsubconfigcode = txtvalue.substring(txtvalue.indexOf("]") + 1, txtvalue.length());
              if (selConfigurationName.equalsIgnoreCase(txtconfigcode) && 
                selSubconfigurationName.equalsIgnoreCase(txtsubconfigcode)) {
                subconfigFnd = true;
                break;
              } 
            } 
            if (!subconfigFnd)
              continue; 
          } 
        } else if (strConfiguration != null && !strConfiguration[0].trim().equalsIgnoreCase("0") && !strConfiguration[0].trim().equalsIgnoreCase("") && 
          !strConfiguration[0].trim().equalsIgnoreCase("all")) {
          if (sel.getSelectionConfig() != null) {
            String selConfigurationName = sel.getSelectionConfig().getSelectionConfigurationAbbreviation();
            boolean configFnd = false;
            for (int x = 0; x < strConfiguration.length; x++) {
              if (selConfigurationName.equalsIgnoreCase(strConfiguration[x])) {
                configFnd = true;
                break;
              } 
            } 
            if (!configFnd)
              continue; 
          } 
        } 
        selectionsForReport.addElement(sel);
        continue;
      } catch (Exception exception) {
        continue;
      } 
    } 
    return selectionsForReport;
  }
  
  protected static Vector filterMCAImpactSelectionsWithReportCriteria(Vector selections, Context context) {
    Vector selectionsForReport = new Vector();
    Form reportForm = (Form)context.getSessionValue("reportForm");
    if (selections == null || selections.size() == 0)
      return selectionsForReport; 
    Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && reportForm.getStringValue("beginDate").length() > 0) ? getDate(reportForm.getStringValue("beginDate")) : null;
    if (beginStDate == null) {
      beginStDate = Calendar.getInstance();
      beginStDate.setTime(new Date(0L));
    } 
    beginStDate.set(11, 0);
    beginStDate.set(12, 0);
    beginStDate.set(13, 0);
    beginStDate.set(14, 0);
    Calendar endStDate = (reportForm.getStringValue("endDate") != null && reportForm.getStringValue("endDate").length() > 0) ? getDate(reportForm.getStringValue("endDate")) : null;
    if (endStDate != null) {
      endStDate.set(11, 23);
      endStDate.set(12, 59);
      endStDate.set(13, 59);
      endStDate.set(14, 999);
    } 
    String strDistribution = (reportForm.getStringValue("distribution") != null) ? reportForm.getStringValue("distribution") : "";
    String strLabelContact = (reportForm.getStringValue("contact") != null) ? reportForm.getStringValue("contact") : "";
    String strUmlContact = (reportForm.getStringValue("umlContact") != null) ? reportForm.getStringValue("umlContact") : "";
    String strFuture2 = (reportForm.getStringValue("Future2") != null) ? reportForm.getStringValue("Future2") : "";
    String strReleaseType = (reportForm.getStringValue("releaseType") != null) ? reportForm.getStringValue("releaseType") : "";
    String strStatus = (reportForm.getStringValue("status") != null) ? reportForm.getStringValue("status") : "";
    String strArtist = (reportForm.getStringValue("artist") != null) ? reportForm.getStringValue("artist") : "";
    String strFamily = (reportForm.getStringValue("family") != null) ? reportForm.getStringValue("family") : "";
    String strCompany = (reportForm.getStringValue("company") != null) ? reportForm.getStringValue("company") : "";
    String strLabel = (reportForm.getStringValue("Label") != null) ? reportForm.getStringValue("Label") : "";
    String[] strConfiguration = null;
    try {
      MilestoneFormDropDownMenu configList = (MilestoneFormDropDownMenu)reportForm.getElement("configurationList");
      if (configList != null) {
        ArrayList configListAl = configList.getStringValues();
        if (configListAl != null) {
          strConfiguration = new String[configListAl.size()];
          strConfiguration = (String[])configListAl.toArray(strConfiguration);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("<<< Configuration vector execption " + e.getMessage());
    } 
    String[] strSubconfiguration = null;
    try {
      MilestoneFormDropDownMenu subconfigList = (MilestoneFormDropDownMenu)reportForm.getElement("subconfigurationList");
      if (subconfigList != null) {
        ArrayList subconfigListAl = subconfigList.getStringValues();
        if (subconfigListAl != null) {
          strSubconfiguration = new String[subconfigListAl.size()];
          strSubconfiguration = (String[])subconfigListAl.toArray(strSubconfiguration);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("<<< Subconfiguration vector execption " + e.getMessage());
    } 
    boolean bParent = (reportForm.getElement("ParentsOnly") != null) ? ((FormCheckBox)reportForm.getElement("ParentsOnly")).isChecked() : 0;
    int totalCount = selections.size();
    int tenth = 0;
    tenth = totalCount / 5;
    if (tenth < 1)
      tenth = 1; 
    try {
      HttpServletResponse sresponse = context.getResponse();
      context.putDelivery("status", new String("start_gathering"));
      context.putDelivery("percent", new String("10"));
      context.includeJSP("status.jsp", "hiddenFrame");
      sresponse.setContentType("text/plain");
      sresponse.flushBuffer();
    } catch (Exception exception) {}
    int recordCount = 0;
    int count = 0;
    for (int i = 0; i < selections.size(); i++) {
      try {
        if (count < recordCount / tenth) {
          count = recordCount / tenth;
          HttpServletResponse sresponse = context.getResponse();
          context.putDelivery("status", new String("start_gathering"));
          context.putDelivery("percent", new String(String.valueOf(count * 10 + 50)));
          context.includeJSP("status.jsp", "hiddenFrame");
          sresponse.setContentType("text/plain");
          sresponse.flushBuffer();
        } 
        recordCount++;
        Selection sel = (Selection)selections.elementAt(i);
        sel = SelectionManager.getInstance().getSelectionHeader(sel.getSelectionID());
        String status = "";
        if (sel.getSelectionStatus() != null)
          status = sel.getSelectionStatus().getName(); 
        if (!status.equalsIgnoreCase("TBS") && !status.equalsIgnoreCase("In the works"))
          Calendar calendar = sel.getStreetDate(); 
        if (!strDistribution.equalsIgnoreCase("") && !strDistribution.equalsIgnoreCase("All")) {
          String selDistribution = "";
          if (sel.getLabel().getDistribution() == 1) {
            selDistribution = "East";
          } else if (sel.getLabel().getDistribution() == 0) {
            selDistribution = "West";
          } 
          if (!selDistribution.equalsIgnoreCase(strDistribution.trim()))
            continue; 
        } 
        if (!strLabelContact.trim().equalsIgnoreCase("0") && !strLabelContact.trim().equalsIgnoreCase("")) {
          if (sel.getLabelContact() == null)
            continue; 
          String selLabelContactName = Integer.toString(sel.getLabelContact().getUserId());
          if (!selLabelContactName.equalsIgnoreCase(strLabelContact))
            continue; 
        } 
        if (!strUmlContact.trim().equalsIgnoreCase("0") && !strUmlContact.trim().equalsIgnoreCase("")) {
          SelectionManager.getInstance().getSelectionManufacturingSubDetail(sel);
          if (sel.getUmlContact() == null)
            continue; 
          String selUmlContactName = Integer.toString(sel.getUmlContact().getUserId());
          if (!selUmlContactName.equalsIgnoreCase(strUmlContact))
            continue; 
        } 
        if (!strReleaseType.equalsIgnoreCase("") && !strReleaseType.equalsIgnoreCase("All")) {
          String releaseType = "";
          if (sel.getReleaseType() != null) {
            releaseType = (sel.getReleaseType().getName() != null) ? sel.getReleaseType().getName() : "";
            if (!releaseType.equalsIgnoreCase(strReleaseType))
              continue; 
          } else {
            continue;
          } 
        } 
        String selectionStatus = SelectionManager.getLookupObjectValue(sel.getSelectionStatus()).trim();
        if (!strStatus.equalsIgnoreCase("") && !strStatus.equalsIgnoreCase("All")) {
          if (strStatus.trim().equalsIgnoreCase("active")) {
            if (!selectionStatus.equalsIgnoreCase("TBS") && !selectionStatus.equalsIgnoreCase("ITW") && !selectionStatus.equalsIgnoreCase("active"))
              continue; 
          } else if (strStatus.trim().equalsIgnoreCase("Active, excluding TBS")) {
            if (!selectionStatus.trim().equalsIgnoreCase("active"))
              continue; 
          } else if (strStatus.trim().equalsIgnoreCase("TBS")) {
            if (!selectionStatus.trim().equalsIgnoreCase("TBS") && !selectionStatus.trim().equalsIgnoreCase("ITW"))
              continue; 
          } else if (strStatus.trim().equalsIgnoreCase("All, excluding TBS")) {
            if (!selectionStatus.trim().equalsIgnoreCase("active") && !selectionStatus.trim().equalsIgnoreCase("CLOSED"))
              continue; 
          } else if (strStatus.trim().equalsIgnoreCase("Cancelled")) {
            if (!selectionStatus.equalsIgnoreCase("CANCEL"))
              continue; 
          } else if (!selectionStatus.equalsIgnoreCase("CLOSED")) {
            continue;
          } 
        } else if (!selectionStatus.equalsIgnoreCase("CANCEL")) {
          continue;
        } 
        if (!strArtist.trim().equals("") && sel.getArtist() != null) {
          String artistUpperCase = sel.getArtist().trim().toUpperCase();
          if (!artistUpperCase.startsWith(strArtist.trim().toUpperCase()))
            continue; 
        } 
        if (!strFamily.equals("") && !strFamily.equals("0"))
          if (sel.getFamily() == null || !strFamily.equals(sel.getFamily().getIdentity()))
            continue;  
        if (!strCompany.equals("") && !strCompany.equals("0"))
          if (sel.getCompany() == null || !strCompany.equals(sel.getCompany().getStructureID()))
            continue;  
        if (!strLabel.equals("") && !strLabel.equals("0"))
          if (sel.getLabel() == null || !strLabel.equals(sel.getLabel().getStructureID()))
            continue;  
        if (bParent) {
          String prefixId = "";
          if (sel.getPrefixID() != null)
            prefixId = SelectionManager.getLookupObjectValue(sel.getPrefixID()); 
          if (!sel.getTitleID().equalsIgnoreCase(String.valueOf(prefixId) + sel.getSelectionNo()))
            continue; 
        } 
        if (strSubconfiguration != null && 
          !strSubconfiguration[0].trim().equalsIgnoreCase("0") && 
          !strSubconfiguration[0].trim().equalsIgnoreCase("") && 
          !strSubconfiguration[0].trim().equalsIgnoreCase("all")) {
          if (sel.getSelectionSubConfig() != null && sel.getSelectionConfig() != null) {
            String selConfigurationName = sel.getSelectionConfig().getSelectionConfigurationAbbreviation();
            String selSubconfigurationName = sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation();
            boolean subconfigFnd = false;
            for (int x = 0; x < strSubconfiguration.length; x++) {
              String txtvalue = strSubconfiguration[x];
              String txtconfigcode = txtvalue.substring(1, txtvalue.indexOf("]"));
              String txtsubconfigcode = txtvalue.substring(txtvalue.indexOf("]") + 1, txtvalue.length());
              if (selConfigurationName.equalsIgnoreCase(txtconfigcode) && 
                selSubconfigurationName.equalsIgnoreCase(txtsubconfigcode)) {
                subconfigFnd = true;
                break;
              } 
            } 
            if (!subconfigFnd)
              continue; 
          } 
        } else if (strConfiguration != null && !strConfiguration[0].trim().equalsIgnoreCase("0") && !strConfiguration[0].trim().equalsIgnoreCase("") && 
          !strConfiguration[0].trim().equalsIgnoreCase("all")) {
          if (sel.getSelectionConfig() != null) {
            String selConfigurationName = sel.getSelectionConfig().getSelectionConfigurationAbbreviation();
            boolean configFnd = false;
            for (int x = 0; x < strConfiguration.length; x++) {
              if (selConfigurationName.equalsIgnoreCase(strConfiguration[x])) {
                configFnd = true;
                break;
              } 
            } 
            if (!configFnd)
              continue; 
          } 
        } 
        selectionsForReport.addElement(sel);
        continue;
      } catch (Exception exception) {
        continue;
      } 
    } 
    return selectionsForReport;
  }
  
  public static Vector getSelectionsForReport(Context context) {
    ReportSelections reportSelObj = new ReportSelections(context);
    System.out.println("MilestoneHelper::getSelectionsForReport()");
    return reportSelObj.getFinalSelections();
  }
  
  public static Vector getFilteredSelectionsForReport(Context context) {
    User user = (User)context.getSessionValue("user");
    int userId = 0;
    if (user != null)
      userId = user.getUserId(); 
    Form reportForm = (Form)context.getSessionValue("reportForm");
    boolean bScheduled = (reportForm.getElement("ScheduledReleases") != null) ? ((FormCheckBox)reportForm.getElement("ScheduledReleases")).isChecked() : 0;
    Vector companies = getUserCompanies(context);
    Company company = null;
    Vector precache = new Vector();
    StringBuffer query = new StringBuffer();
    query.append("SELECT header.release_id, header.title_id, header.selection_no, header.prefix,   pfm.units_per_set, detail.due_date AS taskDue, detail.comments AS taskComments,   detail.owner AS taskOwner, detail.completion_date AS taskComplete,   detail.status AS taskStatus, detail.day_of_week AS taskDayOfWeek,   detail.weeks_to_release AS taskWeeksToRelease, detail.key_task_indicator AS taskKey,    detail.vendor AS taskVendor, detail.authorization_name AS taskAuthName,   detail.authorization_date AS taskAuthDate, detail.last_updated_ck, header.project_no,   header.title, header.artist_first_name, header.artist_last_name,   header.artist, header.artist_first_name + ' ' + header.artist_last_name AS fl_artist ,   header.side_a_title, header.side_b_title, header.product_line, header.release_type,   header.configuration, header.sub_configuration, header.UPC, header.price_code, header.genre,   header.family_id, header.company_id, header.division_id, header.label_id, header.street_date,   header.international_date, header.coordinator_contacts, header.contact_id, header.status,   header.hold_indicator, header.hold_reason, header.comments, header.special_pkg_indicator,   header.pd_indicator, header.packaging, header.impact_date, header.last_updated_on,   header.last_updated_by, header.entered_on, mfg.uml_id, mfg.plant, mfg.distribution,   mfg.order_qty, mfg.complete_qty, mfg.order_comments, Task.task_id, Task.department,   Task.name, Task.abbrev_id  FROM  Release_Header header with (nolock)  LEFT JOIN Release_Subdetail mfg with(nolock)   ON header.release_id = mfg.release_id ");
    if (bScheduled) {
      query.append(" INNER JOIN release_detail detail with (nolock) ON header.[release_id] = detail.[release_id]");
    } else {
      query.append(" LEFT JOIN release_detail detail with (nolock) ON header.[release_id] = detail.[release_id]");
    } 
    query.append("  LEFT JOIN Task   ON detail.task_id = Task.task_id   LEFT JOIN Pfm_Selection pfm   ON header.release_id = pfm.release_id    WHERE ");
    String strLabelContact = (reportForm.getStringValue("contact") != null) ? reportForm.getStringValue("contact") : "";
    String strUmlContact = (reportForm.getStringValue("umlContact") != null) ? reportForm.getStringValue("umlContact") : "";
    String strReleaseType = (reportForm.getStringValue("releaseType") != null) ? reportForm.getStringValue("releaseType") : "";
    String strStatus = (reportForm.getStringValue("status") != null) ? reportForm.getStringValue("status") : "";
    String strArtist = (reportForm.getStringValue("artist") != null) ? reportForm.getStringValue("artist") : "";
    String strFamily = (reportForm.getStringValue("family") != null) ? reportForm.getStringValue("family") : "";
    String strCompany = (reportForm.getStringValue("company") != null) ? reportForm.getStringValue("company") : "";
    String strLabel = (reportForm.getStringValue("Label") != null) ? reportForm.getStringValue("Label") : "";
    String[] strConfiguration = null;
    try {
      MilestoneFormDropDownMenu configList = (MilestoneFormDropDownMenu)reportForm.getElement("configurationList");
      if (configList != null) {
        ArrayList configListAl = configList.getStringValues();
        if (configListAl != null) {
          strConfiguration = new String[configListAl.size()];
          strConfiguration = (String[])configListAl.toArray(strConfiguration);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("<<< Configuration vector execption " + e.getMessage());
    } 
    String[] strSubconfiguration = null;
    try {
      MilestoneFormDropDownMenu subconfigList = (MilestoneFormDropDownMenu)reportForm.getElement("subconfigurationList");
      if (subconfigList != null) {
        ArrayList subconfigListAl = subconfigList.getStringValues();
        if (subconfigListAl != null) {
          strSubconfiguration = new String[subconfigListAl.size()];
          strSubconfiguration = (String[])subconfigListAl.toArray(strSubconfiguration);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("<<< Subconfiguration vector execption " + e.getMessage());
    } 
    boolean bParent = (reportForm.getElement("ParentsOnly") != null) ? ((FormCheckBox)reportForm.getElement("ParentsOnly")).isChecked() : 0;
    if (!strCompany.equalsIgnoreCase("") && !strCompany.equalsIgnoreCase("0")) {
      query.append(" header.[company_id]= " + strCompany);
    } else {
      StringList list = new StringList(" OR ");
      query.append(String.valueOf(list.toString()) + " (");
      for (int i = 0; i < companies.size(); i++) {
        company = (Company)companies.get(i);
        if (company != null)
          list.add(" company_id = " + company.getStructureID()); 
      } 
      list.add(" company_id = " + company.getStructureID());
      query.append(String.valueOf(list.toString()) + ") ");
    } 
    if (!strFamily.equalsIgnoreCase("") && !strFamily.equalsIgnoreCase("0"))
      query.append(" AND header.[family_id]= " + strFamily); 
    if (!strLabel.equalsIgnoreCase("") && !strLabel.equalsIgnoreCase("0"))
      query.append(" AND header.[label_id]= " + strLabel); 
    if (!strArtist.equalsIgnoreCase(""))
      query.append(" AND header.[artist] LIKE '%" + escapeSingleQuotes(strArtist) + "%'"); 
    if (!strStatus.equalsIgnoreCase("") && !strStatus.equalsIgnoreCase("all"))
      if (strStatus.equalsIgnoreCase("Active, excluding TBS")) {
        query.append(" AND header.[status] ='active'");
      } else if (strStatus.equalsIgnoreCase("TBS")) {
        query.append(" AND (header.[status] ='TBS' OR header.[status] ='ITW')");
      } else if (strStatus.equalsIgnoreCase("active")) {
        query.append(" AND (header.[status] ='active' OR header.[status] ='TBS' OR header.[status] ='ITW')");
      } else if (strStatus.equalsIgnoreCase("All, excluding TBS")) {
        query.append(" AND (header.[status] ='active' OR header.[status] ='closed' OR header.[status] ='cancel')");
      } else {
        query.append(" AND header.[status] ='" + strStatus + "'");
      }  
    if (!strReleaseType.equalsIgnoreCase("") && !strReleaseType.equalsIgnoreCase("all"))
      if (strReleaseType.equalsIgnoreCase("commercial")) {
        query.append(" AND header.[release_type] ='CO'");
      } else {
        query.append(" AND header.[release_type] ='PR'");
      }  
    if (!strLabelContact.equalsIgnoreCase("") && !strLabelContact.equalsIgnoreCase("0"))
      query.append(" AND header.[contact_id] =" + strLabelContact); 
    if (strSubconfiguration != null && 
      !strSubconfiguration[0].trim().equalsIgnoreCase("0") && 
      !strSubconfiguration[0].trim().equalsIgnoreCase("") && 
      !strSubconfiguration[0].trim().equalsIgnoreCase("all")) {
      boolean addOr = false;
      query.append(" AND (");
      for (int x = 0; x < strSubconfiguration.length; x++) {
        if (addOr)
          query.append(" OR "); 
        String txtvalue = strSubconfiguration[x];
        String txtconfigcode = txtvalue.substring(1, txtvalue.indexOf("]"));
        String txtsubconfigcode = txtvalue.substring(txtvalue.indexOf("]") + 1, txtvalue.length());
        query.append(" (header.[configuration] = '" + txtconfigcode.trim() + "' AND ");
        query.append(" header.[sub_configuration] = '" + txtsubconfigcode.trim() + "') ");
        addOr = true;
      } 
      query.append(") ");
    } else if (strConfiguration != null && !strConfiguration[0].equalsIgnoreCase("") && !strConfiguration[0].equalsIgnoreCase("0") && 
      !strConfiguration[0].equalsIgnoreCase("all")) {
      boolean addOr = false;
      query.append(" AND (");
      for (int x = 0; x < strConfiguration.length; x++) {
        if (addOr)
          query.append(" OR "); 
        query.append(" header.[configuration] = '" + strConfiguration[x].trim() + "' ");
        addOr = true;
      } 
      query.append(") ");
    } 
    if (!strUmlContact.equalsIgnoreCase("") && !strUmlContact.equalsIgnoreCase("0"))
      query.append(" AND mfg.[uml_id] =" + strUmlContact); 
    String beginDate = "";
    beginDate = (reportForm.getStringValue("beginDate") != null && reportForm.getStringValue("beginDate").length() > 0) ? reportForm.getStringValue("beginDate") : "";
    String endDate = "";
    endDate = (reportForm.getStringValue("endDate") != null && reportForm.getStringValue("endDate").length() > 0) ? reportForm.getStringValue("endDate") : "";
    if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase(""))
      query.append(" AND ((header.[status] ='TBS' OR header.[status] ='ITW') OR ("); 
    if (!beginDate.equalsIgnoreCase(""))
      query.append(" street_date >= '" + escapeSingleQuotes(beginDate) + "'"); 
    if (!endDate.equalsIgnoreCase(""))
      if (!beginDate.equalsIgnoreCase("")) {
        query.append(" AND street_date <= '" + escapeSingleQuotes(endDate) + "'");
      } else {
        query.append(" street_date <= '" + escapeSingleQuotes(endDate) + "'");
      }  
    if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase(""))
      query.append("))"); 
    query.append(" ORDER BY header.[release_id], header.[artist], header.[street_date], header.[title]");
    System.out.println("\n\n===========Report Query Start===============\n\n" + query);
    System.out.println("\n\n===========Report Query End===============\n\n");
    JdbcConnector connector = getConnector(query.toString());
    connector.runQuery();
    Vector selections = null;
    int totalCount = 0;
    int tenth = 0;
    JdbcConnector connectorCount = getConnector(query.toString());
    connectorCount.runQuery();
    while (connectorCount.more()) {
      totalCount++;
      connectorCount.next();
    } 
    tenth = totalCount / 10;
    if (tenth < 1)
      tenth = 1; 
    try {
      HttpServletResponse sresponse = context.getResponse();
      context.putDelivery("status", new String("start_gathering"));
      context.putDelivery("percent", new String("10"));
      context.includeJSP("status.jsp", "hiddenFrame");
      sresponse.setContentType("text/plain");
      sresponse.flushBuffer();
    } catch (Exception exception) {}
    int recordCount = 0;
    int count = 0;
    selections = new Vector();
    while (connector.more()) {
      try {
        if (count < recordCount / tenth) {
          count = recordCount / tenth;
          HttpServletResponse sresponse = context.getResponse();
          context.putDelivery("status", new String("start_gathering"));
          context.putDelivery("percent", new String(String.valueOf(count * 10)));
          context.includeJSP("status.jsp", "hiddenFrame");
          sresponse.setContentType("text/plain");
          sresponse.flushBuffer();
        } 
        recordCount++;
        if (bParent) {
          String prefixId = "";
          String tmpTitleId = connector.getField("title_id", "");
          String tmpSelectionNo = connector.getField("selection_no", "");
          prefixId = SelectionManager.getLookupObjectValue((PrefixCode)getLookupObject(connector.getField("prefix", ""), Cache.getPrefixCodes()));
          if (!tmpTitleId.equalsIgnoreCase(String.valueOf(prefixId) + tmpSelectionNo)) {
            connector.next();
            continue;
          } 
        } 
        int numberOfUnits = 0;
        try {
          numberOfUnits = connector.getIntegerField("pfm.[units_per_set]");
        } catch (Exception exception) {}
        Selection selection = null;
        selection = new Selection();
        selection.setSelectionID(connector.getIntegerField("release_id"));
        String selectionNo = "";
        if (connector.getFieldByName("selection_no") != null)
          selectionNo = connector.getFieldByName("selection_no"); 
        selection.setSelectionNo(selectionNo);
        selection.setProjectID(connector.getField("project_no", ""));
        String titleId = "";
        if (connector.getFieldByName("title_id") != null)
          titleId = connector.getFieldByName("title_id"); 
        selection.setTitleID(titleId);
        selection.setTitle(connector.getField("title", ""));
        selection.setArtistFirstName(connector.getField("artist_first_name", ""));
        selection.setArtistLastName(connector.getField("artist_last_name", ""));
        selection.setArtist(connector.getField("artist", ""));
        selection.setFlArtist(connector.getField("fl_artist", ""));
        selection.setASide(connector.getField("side_a_title", ""));
        selection.setBSide(connector.getField("side_b_title", ""));
        selection.setProductCategory((ProductCategory)getLookupObject(connector.getField("product_line"), 
              Cache.getProductCategories()));
        selection.setReleaseType((ReleaseType)getLookupObject(connector.getField("release_type"), 
              Cache.getReleaseTypes()));
        selection.setSelectionConfig(getSelectionConfigObject(connector.getField("configuration"), 
              Cache.getSelectionConfigs()));
        selection.setSelectionSubConfig(getSelectionSubConfigObject(connector.getField("sub_configuration"), 
              selection.getSelectionConfig()));
        selection.setUpc(connector.getField("upc", ""));
        String sellCodeString = connector.getFieldByName("price_code");
        if (sellCodeString != null)
          selection.setPriceCode(SelectionManager.getInstance().getPriceCode(sellCodeString)); 
        selection.setSellCode(sellCodeString);
        selection.setGenre((Genre)getLookupObject(connector.getField("genre"), 
              Cache.getMusicTypes()));
        selection.setFamily((Family)getStructureObject(connector.getIntegerField("family_id")));
        selection.setCompany((Company)getStructureObject(connector.getIntegerField("company_id")));
        selection.setDivision((Division)getStructureObject(connector.getIntegerField("division_id")));
        selection.setLabel((Label)getStructureObject(connector.getIntegerField("label_id")));
        String streetDateString = connector.getFieldByName("street_date");
        if (streetDateString != null)
          selection.setStreetDate(getDatabaseDate(streetDateString)); 
        String internationalDateString = connector.getFieldByName("international_date");
        if (internationalDateString != null)
          selection.setInternationalDate(getDatabaseDate(internationalDateString)); 
        selection.setOtherContact(connector.getField("coordinator_contacts", ""));
        if (UserManager.getInstance().getUser(connector.getIntegerField("contact_id")) != null)
          selection.setLabelContact(UserManager.getInstance().getUser(connector.getIntegerField("contact_id"))); 
        selection.setSelectionStatus((SelectionStatus)getLookupObject(connector.getField("status"), 
              Cache.getSelectionStatusList()));
        selection.setHoldSelection(connector.getBoolean("hold_indicator"));
        selection.setHoldReason(connector.getField("hold_reason", ""));
        selection.setComments(connector.getField("comments", ""));
        selection.setSpecialPackaging(connector.getBoolean("special_pkg_indicator"));
        selection.setNumberOfUnits(numberOfUnits);
        selection.setPressAndDistribution(connector.getBoolean("pd_indicator"));
        selection.setPrefixID((PrefixCode)getLookupObject(connector.getField("prefix"), Cache.getPrefixCodes()));
        selection.setSelectionPackaging(connector.getField("packaging", ""));
        String impactDateString = connector.getFieldByName("impact_date");
        if (impactDateString != null)
          selection.setImpactDate(getDatabaseDate(impactDateString)); 
        String lastUpdateDateString = connector.getFieldByName("last_updated_on");
        if (lastUpdateDateString != null)
          selection.setLastUpdateDate(getDatabaseDate(lastUpdateDateString)); 
        selection.setLastUpdatingUser(UserManager.getInstance().getUser(connector.getIntegerField("last_updated_by")));
        String originDateString = connector.getFieldByName("entered_on");
        if (originDateString != null)
          selection.setOriginDate(getDatabaseDate(originDateString)); 
        User umlContact = UserManager.getInstance().getUser(connector.getIntegerField("mfg.[uml_id]"));
        selection.setUmlContact(umlContact);
        selection.setPlant(getLookupObject(connector.getField("mfg.[plant]"), Cache.getVendors()));
        selection.setDistribution(getLookupObject(connector.getField("mfg.[distribution]"), Cache.getDistributionCodes()));
        selection.setPoQuantity(connector.getIntegerField("mfg.[order_qty]"));
        selection.setCompletedQuantity(connector.getIntegerField("mfg.[complete_qty]"));
        selection.setManufacturingComments(connector.getField("mfg.[order_comments]", ""));
        selection.setPrice(connector.getFloat("mfg.[list_price]"));
        selection.setFullSelection(true);
        String strDistribution = (reportForm.getStringValue("distribution") != null) ? reportForm.getStringValue("distribution") : "";
        String strFuture2 = (reportForm.getStringValue("Future2") != null) ? reportForm.getStringValue("Future2") : "";
        if (!strDistribution.equalsIgnoreCase("") && !strDistribution.equalsIgnoreCase("All")) {
          String selDistribution = SelectionManager.getLookupObjectValue(selection.getDistribution());
          if (!selDistribution.equalsIgnoreCase(strDistribution.trim()))
            continue; 
        } 
        int nextReleaseId = connector.getIntegerField("release_id");
        Schedule schedule = new Schedule();
        schedule.setSelectionID(nextReleaseId);
        Vector precacheSchedule = new Vector();
        while (connector.more() && connector.getIntegerField("release_id") == nextReleaseId) {
          ScheduledTask scheduledTask = null;
          scheduledTask = new ScheduledTask();
          scheduledTask.setReleaseID(connector.getIntegerField("release_Id"));
          scheduledTask.setTaskID(connector.getIntegerField("task_id"));
          scheduledTask.setOwner((Family)getStructureObject(connector.getIntegerField("taskOwner")));
          String dueDateString = connector.getField("taskDue", "");
          if (dueDateString.length() > 0)
            scheduledTask.setDueDate(getDatabaseDate(dueDateString)); 
          String completionDateString = connector.getField("taskComplete", "");
          if (completionDateString.length() > 0)
            scheduledTask.setCompletionDate(getDatabaseDate(completionDateString)); 
          String taskStatus = connector.getField("taskStatus", "");
          if (taskStatus.length() > 1)
            scheduledTask.setScheduledTaskStatus(taskStatus); 
          int day = connector.getIntegerField("taskDayOfWeek");
          if (day > 0)
            scheduledTask.setDayOfTheWeek(new Day(connector.getIntegerField("taskDayOfWeek"))); 
          int weeks = connector.getIntegerField("taskWeeksToRelease");
          if (weeks > 0)
            scheduledTask.setWeeksToRelease(connector.getIntegerField("taskWeeksToRelease")); 
          String vendorString = connector.getField("taskVendor", "");
          if (vendorString.length() > 0)
            scheduledTask.setVendor(vendorString); 
          int taskAbbrevID = connector.getIntegerField("abbrev_id");
          scheduledTask.setTaskAbbreviationID(taskAbbrevID);
          int taskID = connector.getIntegerField("task_id");
          scheduledTask.setScheduledTaskID(taskID);
          String taskDept = connector.getField("department", "");
          scheduledTask.setDepartment(taskDept);
          scheduledTask.setKeyTask(connector.getBoolean("taskKey"));
          scheduledTask.setAuthorizationName(connector.getField("taskAuthName", ""));
          String authDateString = connector.getField("taskAuthDate", "");
          if (authDateString.length() > 0)
            scheduledTask.setAuthorizationDate(getDatabaseDate(authDateString)); 
          String comments = connector.getField("taskComments", "");
          scheduledTask.setComments(comments);
          scheduledTask.setName(connector.getField("name", ""));
          int selNo = scheduledTask.getReleaseID();
          int taskId = scheduledTask.getTaskID();
          Vector multCompleteDates = ScheduleManager.getInstance().getMultCompleteDates(selNo, taskId);
          scheduledTask.setMultCompleteDates(multCompleteDates);
          scheduledTask.setAllowMultCompleteDatesFlag(connector.getBoolean("allowMultcompleteDatesFlag"));
          precacheSchedule.add(scheduledTask);
          scheduledTask = null;
          if (connector.more()) {
            connector.next();
            recordCount++;
            continue;
          } 
          break;
        } 
        schedule.setTasks(precacheSchedule);
        selection.setSchedule(schedule);
        selections.add(selection);
      } catch (Exception exception) {}
    } 
    connector.close();
    return selections;
  }
  
  public static Vector getTaskDueSelectionsForReport(Context context) {
    User user = (User)context.getSessionValue("user");
    int userId = 0;
    if (user != null)
      userId = user.getUserId(); 
    Form reportForm = (Form)context.getSessionValue("reportForm");
    String beginDate = "";
    beginDate = (reportForm.getStringValue("beginDate") != null && reportForm.getStringValue("beginDate").length() > 0) ? reportForm.getStringValue("beginDate") : "";
    String endDate = "";
    endDate = (reportForm.getStringValue("endDate") != null && reportForm.getStringValue("endDate").length() > 0) ? reportForm.getStringValue("endDate") : "";
    String beginDueDate = "";
    beginDueDate = (reportForm.getStringValue("beginDueDate") != null && reportForm.getStringValue("beginDueDate").length() > 0) ? reportForm.getStringValue("beginDueDate") : "";
    String endDueDate = "";
    endDueDate = (reportForm.getStringValue("endDueDate") != null && reportForm.getStringValue("endDueDate").length() > 0) ? reportForm.getStringValue("endDueDate") : "";
    StringBuffer dueDateQuery = new StringBuffer(200);
    if (!beginDueDate.equalsIgnoreCase(""))
      dueDateQuery.append("AND due_date >= '" + escapeSingleQuotes(beginDueDate) + "'"); 
    if (!endDueDate.equalsIgnoreCase(""))
      if (!beginDueDate.equalsIgnoreCase("")) {
        dueDateQuery.append(" AND due_date <= '" + escapeSingleQuotes(endDueDate) + "'");
      } else {
        dueDateQuery.append(" due_date <= '" + escapeSingleQuotes(endDueDate) + "'");
      }  
    Vector companies = getUserCompanies(context);
    Company company = null;
    Vector precache = new Vector();
    StringBuffer query = new StringBuffer();
    query.append("SELECT header.release_id, header.title_id, header.selection_no, header.prefix,   pfm.units_per_set, detail.due_date AS taskDue, detail.comments AS taskComments,   detail.owner AS taskOwner, detail.completion_date AS taskComplete,   detail.status AS taskStatus, detail.day_of_week AS taskDayOfWeek,   detail.weeks_to_release AS taskWeeksToRelease, detail.key_task_indicator AS taskKey,    detail.vendor AS taskVendor, detail.authorization_name AS taskAuthName,   detail.authorization_date AS taskAuthDate, detail.last_updated_ck, header.project_no,   header.title, header.artist_first_name, header.artist_last_name, header.artist,   header.side_a_title, header.side_b_title, header.product_line, header.release_type,   header.configuration, header.sub_configuration, header.UPC, header.price_code, header.genre,   header.family_id, header.company_id, header.division_id, header.label_id, header.street_date,   header.international_date, header.coordinator_contacts, header.contact_id, header.status,   header.hold_indicator, header.hold_reason, header.comments, header.special_pkg_indicator,   header.pd_indicator, header.packaging, header.impact_date, header.last_updated_on,   header.last_updated_by, header.entered_on, mfg.uml_id, mfg.plant, mfg.distribution,   mfg.order_qty, mfg.complete_qty, mfg.order_comments, Task.task_id, Task.department,   Task.name, Task.abbrev_id  FROM vi_release_header header with (nolock) LEFT JOIN vi_release_subdetail mfg with (nolock) ON header.[release_id] = mfg.[release_id] LEFT JOIN vi_release_detail detail with (nolock) ON header.[release_id] = detail.[release_id] " + 
        
        dueDateQuery.toString() + 
        " INNER JOIN vi_Task task with (nolock)" + 
        " ON detail.[task_id] = task.[task_id]" + 
        " LEFT JOIN vi_pfm_selection pfm with (nolock)" + 
        " ON header.[release_id] = pfm.[release_id]" + 
        " WHERE ");
    String strLabelContact = (reportForm.getStringValue("contact") != null) ? reportForm.getStringValue("contact") : "";
    String strUmlContact = (reportForm.getStringValue("umlContact") != null) ? reportForm.getStringValue("umlContact") : "";
    String strReleaseType = (reportForm.getStringValue("releaseType") != null) ? reportForm.getStringValue("releaseType") : "";
    String strStatus = (reportForm.getStringValue("status") != null) ? reportForm.getStringValue("status") : "";
    String strArtist = (reportForm.getStringValue("artist") != null) ? reportForm.getStringValue("artist") : "";
    String strFamily = (reportForm.getStringValue("family") != null) ? reportForm.getStringValue("family") : "";
    String strCompany = (reportForm.getStringValue("company") != null) ? reportForm.getStringValue("company") : "";
    String strLabel = (reportForm.getStringValue("Label") != null) ? reportForm.getStringValue("Label") : "";
    boolean bParent = (reportForm.getElement("ParentsOnly") != null) ? ((FormCheckBox)reportForm.getElement("ParentsOnly")).isChecked() : 0;
    if (!strCompany.equalsIgnoreCase("") && !strCompany.equalsIgnoreCase("0")) {
      query.append(" header.[company_id]= " + strCompany);
    } else {
      StringList list = new StringList(" OR ");
      query.append(String.valueOf(list.toString()) + " (");
      for (int i = 0; i < companies.size(); i++) {
        company = (Company)companies.get(i);
        if (company != null)
          list.add(" company_id = " + company.getStructureID()); 
      } 
      list.add(" company_id = " + company.getStructureID());
      query.append(String.valueOf(list.toString()) + ") ");
    } 
    if (!strFamily.equalsIgnoreCase("") && !strFamily.equalsIgnoreCase("0"))
      query.append(" AND header.[family_id]= " + strFamily); 
    if (!strLabel.equalsIgnoreCase("") && !strLabel.equalsIgnoreCase("0"))
      query.append(" AND header.[label_id]= " + strLabel); 
    if (!strArtist.equalsIgnoreCase(""))
      query.append(" AND header.[artist] LIKE '%" + escapeSingleQuotes(strArtist) + "%'"); 
    if (!strStatus.equalsIgnoreCase("") && !strStatus.equalsIgnoreCase("all"))
      if (strStatus.equalsIgnoreCase("Active, excluding TBS")) {
        query.append(" AND header.[status] ='active'");
      } else if (strStatus.equalsIgnoreCase("TBS")) {
        query.append(" AND (header.[status] ='TBS' OR header.[status] ='ITW')");
      } else if (strStatus.equalsIgnoreCase("active")) {
        query.append(" AND (header.[status] ='active' OR header.[status] ='TBS' OR header.[status] ='ITW')");
      } else if (strStatus.equalsIgnoreCase("All, excluding TBS")) {
        query.append(" AND (header.[status] ='active' OR header.[status] ='closed' OR header.[status] ='cancel')");
      } else {
        query.append(" AND header.[status] ='" + strStatus + "'");
      }  
    if (!strReleaseType.equalsIgnoreCase("") && !strReleaseType.equalsIgnoreCase("all"))
      if (strReleaseType.equalsIgnoreCase("commercial")) {
        query.append(" AND header.[release_type] ='CO'");
      } else {
        query.append(" AND header.[release_type] ='PR'");
      }  
    if (!strLabelContact.equalsIgnoreCase("") && !strLabelContact.equalsIgnoreCase("0"))
      query.append(" AND header.[contact_id] =" + strLabelContact); 
    if (!strUmlContact.equalsIgnoreCase("") && !strUmlContact.equalsIgnoreCase("0"))
      query.append(" AND mfg.[uml_id] =" + strUmlContact); 
    if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase(""))
      query.append(" AND ((header.[status] ='TBS' OR header.[status] ='ITW') OR ("); 
    if (!beginDate.equalsIgnoreCase(""))
      query.append(" street_date >= '" + escapeSingleQuotes(beginDate) + "'"); 
    if (!endDate.equalsIgnoreCase(""))
      if (!beginDate.equalsIgnoreCase("")) {
        query.append(" AND street_date <= '" + escapeSingleQuotes(endDate) + "'");
      } else {
        query.append(" street_date <= '" + escapeSingleQuotes(endDate) + "'");
      }  
    if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase(""))
      query.append("))"); 
    query.append(" ORDER BY header.[release_id], header.[artist], header.[street_date], header.[title]");
    JdbcConnector connector = getConnector(query.toString());
    connector.setForwardOnly(false);
    connector.runQuery();
    Vector selections = null;
    int totalCount = 0;
    int tenth = 0;
    totalCount = connector.getRowCount();
    tenth = totalCount / 10;
    if (tenth < 1)
      tenth = 1; 
    try {
      HttpServletResponse sresponse = context.getResponse();
      context.putDelivery("status", new String("start_gathering"));
      context.putDelivery("percent", new String("10"));
      context.includeJSP("status.jsp", "hiddenFrame");
      sresponse.setContentType("text/plain");
      sresponse.flushBuffer();
    } catch (Exception exception) {}
    int recordCount = 0;
    int count = 0;
    selections = new Vector();
    while (connector.more()) {
      try {
        if (count < recordCount / tenth) {
          count = recordCount / tenth;
          HttpServletResponse sresponse = context.getResponse();
          context.putDelivery("status", new String("start_gathering"));
          context.putDelivery("percent", new String(String.valueOf(count * 10)));
          context.includeJSP("status.jsp", "hiddenFrame");
          sresponse.setContentType("text/plain");
          sresponse.flushBuffer();
        } 
        recordCount++;
        if (bParent) {
          String prefixId = "";
          String tmpTitleId = connector.getField("title_id", "");
          String tmpSelectionNo = connector.getField("selection_no", "");
          prefixId = SelectionManager.getLookupObjectValue((PrefixCode)getLookupObject(connector.getField("prefix", ""), Cache.getPrefixCodes()));
          if (!tmpTitleId.equalsIgnoreCase(String.valueOf(prefixId) + tmpSelectionNo)) {
            connector.next();
            continue;
          } 
        } 
        int numberOfUnits = 0;
        try {
          numberOfUnits = connector.getIntegerField("pfm.[units_per_set]");
        } catch (Exception exception) {}
        Selection selection = null;
        selection = new Selection();
        selection.setSelectionID(connector.getIntegerField("release_id"));
        String selectionNo = "";
        if (connector.getFieldByName("selection_no") != null)
          selectionNo = connector.getFieldByName("selection_no"); 
        selection.setSelectionNo(selectionNo);
        selection.setProjectID(connector.getField("project_no", ""));
        String titleId = "";
        if (connector.getFieldByName("title_id") != null)
          titleId = connector.getFieldByName("title_id"); 
        selection.setTitleID(titleId);
        selection.setTitle(connector.getField("title", ""));
        selection.setArtistFirstName(connector.getField("artist_first_name", ""));
        selection.setArtistLastName(connector.getField("artist_last_name", ""));
        selection.setArtist(connector.getField("artist", ""));
        selection.setASide(connector.getField("side_a_title", ""));
        selection.setBSide(connector.getField("side_b_title", ""));
        selection.setProductCategory((ProductCategory)getLookupObject(connector.getField("product_line"), 
              Cache.getProductCategories()));
        selection.setReleaseType((ReleaseType)getLookupObject(connector.getField("release_type"), 
              Cache.getReleaseTypes()));
        selection.setSelectionConfig(getSelectionConfigObject(connector.getField("configuration"), 
              Cache.getSelectionConfigs()));
        selection.setSelectionSubConfig(getSelectionSubConfigObject(connector.getField("sub_configuration"), 
              selection.getSelectionConfig()));
        selection.setUpc(connector.getField("upc", ""));
        String sellCodeString = connector.getFieldByName("price_code");
        if (sellCodeString != null)
          selection.setPriceCode(SelectionManager.getInstance().getPriceCode(sellCodeString)); 
        selection.setSellCode(sellCodeString);
        selection.setGenre((Genre)getLookupObject(connector.getField("genre"), 
              Cache.getMusicTypes()));
        selection.setFamily((Family)getStructureObject(connector.getIntegerField("family_id")));
        selection.setCompany((Company)getStructureObject(connector.getIntegerField("company_id")));
        selection.setDivision((Division)getStructureObject(connector.getIntegerField("division_id")));
        selection.setLabel((Label)getStructureObject(connector.getIntegerField("label_id")));
        String streetDateString = connector.getFieldByName("street_date");
        if (streetDateString != null)
          selection.setStreetDate(getDatabaseDate(streetDateString)); 
        String internationalDateString = connector.getFieldByName("international_date");
        if (internationalDateString != null)
          selection.setInternationalDate(getDatabaseDate(internationalDateString)); 
        selection.setOtherContact(connector.getField("coordinator_contacts", ""));
        if (UserManager.getInstance().getUser(connector.getIntegerField("contact_id")) != null)
          selection.setLabelContact(UserManager.getInstance().getUser(connector.getIntegerField("contact_id"))); 
        selection.setSelectionStatus((SelectionStatus)getLookupObject(connector.getField("status"), 
              Cache.getSelectionStatusList()));
        selection.setHoldSelection(connector.getBoolean("hold_indicator"));
        selection.setHoldReason(connector.getField("hold_reason", ""));
        selection.setComments(connector.getField("comments", ""));
        selection.setSpecialPackaging(connector.getBoolean("special_pkg_indicator"));
        selection.setNumberOfUnits(numberOfUnits);
        selection.setPressAndDistribution(connector.getBoolean("pd_indicator"));
        selection.setPrefixID((PrefixCode)getLookupObject(connector.getField("prefix"), Cache.getPrefixCodes()));
        selection.setSelectionPackaging(connector.getField("packaging", ""));
        String impactDateString = connector.getFieldByName("impact_date");
        if (impactDateString != null)
          selection.setImpactDate(getDatabaseDate(impactDateString)); 
        String lastUpdateDateString = connector.getFieldByName("last_updated_on");
        if (lastUpdateDateString != null)
          selection.setLastUpdateDate(getDatabaseDate(lastUpdateDateString)); 
        selection.setLastUpdatingUser(UserManager.getInstance().getUser(connector.getIntegerField("last_updated_by")));
        String originDateString = connector.getFieldByName("entered_on");
        if (originDateString != null)
          selection.setOriginDate(getDatabaseDate(originDateString)); 
        User umlContact = UserManager.getInstance().getUser(connector.getIntegerField("mfg.[uml_id]"));
        selection.setUmlContact(umlContact);
        selection.setPlant(getLookupObject(connector.getField("mfg.[plant]"), Cache.getVendors()));
        selection.setDistribution(getLookupObject(connector.getField("mfg.[distribution]"), Cache.getDistributionCodes()));
        selection.setPoQuantity(connector.getIntegerField("mfg.[order_qty]"));
        selection.setCompletedQuantity(connector.getIntegerField("mfg.[complete_qty]"));
        selection.setManufacturingComments(connector.getField("mfg.[order_comments]", ""));
        selection.setPrice(connector.getFloat("mfg.[list_price]"));
        selection.setFullSelection(true);
        String strDistribution = (reportForm.getStringValue("distribution") != null) ? reportForm.getStringValue("distribution") : "";
        String strFuture2 = (reportForm.getStringValue("Future2") != null) ? reportForm.getStringValue("Future2") : "";
        if (!strDistribution.equalsIgnoreCase("") && !strDistribution.equalsIgnoreCase("All")) {
          String selDistribution = SelectionManager.getLookupObjectValue(selection.getDistribution());
          if (!selDistribution.equalsIgnoreCase(strDistribution.trim()))
            continue; 
        } 
        int nextReleaseId = connector.getIntegerField("release_id");
        Schedule schedule = new Schedule();
        schedule.setSelectionID(nextReleaseId);
        Vector precacheSchedule = new Vector();
        while (connector.more() && connector.getIntegerField("release_id") == nextReleaseId) {
          ScheduledTask scheduledTask = null;
          scheduledTask = new ScheduledTask();
          scheduledTask.setReleaseID(connector.getIntegerField("release_Id"));
          scheduledTask.setTaskID(connector.getIntegerField("task_id"));
          scheduledTask.setOwner((Family)getStructureObject(connector.getIntegerField("taskOwner")));
          String dueDateString = connector.getField("taskDue", "");
          if (dueDateString.length() > 0)
            scheduledTask.setDueDate(getDatabaseDate(dueDateString)); 
          String completionDateString = connector.getField("taskComplete", "");
          if (completionDateString.length() > 0)
            scheduledTask.setCompletionDate(getDatabaseDate(completionDateString)); 
          String taskStatus = connector.getField("taskStatus", "");
          if (taskStatus.length() > 1)
            scheduledTask.setScheduledTaskStatus(taskStatus); 
          int day = connector.getIntegerField("taskDayOfWeek");
          if (day > 0)
            scheduledTask.setDayOfTheWeek(new Day(connector.getIntegerField("taskDayOfWeek"))); 
          int weeks = connector.getIntegerField("taskWeeksToRelease");
          if (weeks > 0)
            scheduledTask.setWeeksToRelease(connector.getIntegerField("taskWeeksToRelease")); 
          String vendorString = connector.getField("taskVendor", "");
          if (vendorString.length() > 0)
            scheduledTask.setVendor(vendorString); 
          int taskAbbrevID = connector.getIntegerField("abbrev_id");
          scheduledTask.setTaskAbbreviationID(taskAbbrevID);
          int taskID = connector.getIntegerField("task_id");
          scheduledTask.setScheduledTaskID(taskID);
          String taskDept = connector.getField("department", "");
          scheduledTask.setDepartment(taskDept);
          scheduledTask.setKeyTask(connector.getBoolean("taskKey"));
          scheduledTask.setAuthorizationName(connector.getField("taskAuthName", ""));
          String authDateString = connector.getField("taskAuthDate", "");
          if (authDateString.length() > 0)
            scheduledTask.setAuthorizationDate(getDatabaseDate(authDateString)); 
          String comments = connector.getField("taskComments", "");
          scheduledTask.setComments(comments);
          int selNo = scheduledTask.getReleaseID();
          int taskId = scheduledTask.getTaskID();
          Vector multCompleteDates = ScheduleManager.getInstance().getMultCompleteDates(selNo, taskId);
          scheduledTask.setMultCompleteDates(multCompleteDates);
          scheduledTask.setAllowMultCompleteDatesFlag(connector.getBoolean("allowMultcompleteDatesFlag"));
          scheduledTask.setName(connector.getField("name", ""));
          precacheSchedule.add(scheduledTask);
          scheduledTask = null;
          if (connector.more()) {
            connector.next();
            recordCount++;
            continue;
          } 
          break;
        } 
        schedule.setTasks(precacheSchedule);
        selection.setSchedule(schedule);
        selections.add(selection);
      } catch (Exception exception) {}
    } 
    connector.close();
    return selections;
  }
  
  public static Vector getFullSelectionsForReport(Context context) {
    User user = (User)context.getSessionValue("user");
    int userId = 0;
    if (user != null)
      userId = user.getUserId(); 
    Vector companies = getUserCompanies(context);
    Company company = null;
    Vector precache = new Vector();
    StringBuffer query = new StringBuffer(200);
    query.append("SELECT * FROM vi_release_header header with (nolock) LEFT JOIN vi_release_subdetail mfg with (nolock) ON header.[release_id] = mfg.[release_id] LEFT JOIN vi_pfm_selection pfm with (nolock) ON header.[release_id] = pfm.[release_id] WHERE ");
    Form reportForm = (Form)context.getSessionValue("reportForm");
    String strLabelContact = (reportForm.getStringValue("contact") != null) ? reportForm.getStringValue("contact") : "";
    String strUmlContact = (reportForm.getStringValue("umlContact") != null) ? reportForm.getStringValue("umlContact") : "";
    String strReleaseType = (reportForm.getStringValue("releaseType") != null) ? reportForm.getStringValue("releaseType") : "";
    String strStatus = (reportForm.getStringValue("status") != null) ? reportForm.getStringValue("status") : "";
    String strArtist = (reportForm.getStringValue("artist") != null) ? reportForm.getStringValue("artist") : "";
    String strFamily = (reportForm.getStringValue("family") != null) ? reportForm.getStringValue("family") : "";
    String strCompany = (reportForm.getStringValue("company") != null) ? reportForm.getStringValue("company") : "";
    String strLabel = (reportForm.getStringValue("Label") != null) ? reportForm.getStringValue("Label") : "";
    String[] strConfiguration = null;
    try {
      MilestoneFormDropDownMenu configList = (MilestoneFormDropDownMenu)reportForm.getElement("configurationList");
      if (configList != null) {
        ArrayList configListAl = configList.getStringValues();
        if (configListAl != null) {
          strConfiguration = new String[configListAl.size()];
          strConfiguration = (String[])configListAl.toArray(strConfiguration);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("<<< Configuration vector execption " + e.getMessage());
    } 
    String[] strSubconfiguration = null;
    try {
      MilestoneFormDropDownMenu subconfigList = (MilestoneFormDropDownMenu)reportForm.getElement("subconfigurationList");
      if (subconfigList != null) {
        ArrayList subconfigListAl = subconfigList.getStringValues();
        if (subconfigListAl != null) {
          strSubconfiguration = new String[subconfigListAl.size()];
          strSubconfiguration = (String[])subconfigListAl.toArray(strSubconfiguration);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("<<< Subconfiguration vector execption " + e.getMessage());
    } 
    boolean bParent = (reportForm.getElement("ParentsOnly") != null) ? ((FormCheckBox)reportForm.getElement("ParentsOnly")).isChecked() : 0;
    if (!strCompany.equalsIgnoreCase("") && !strCompany.equalsIgnoreCase("0")) {
      query.append(" header.[company_id]= " + strCompany);
    } else {
      StringList list = new StringList(" OR ");
      query.append(String.valueOf(list.toString()) + " (");
      for (int i = 0; i < companies.size(); i++) {
        company = (Company)companies.get(i);
        if (company != null)
          list.add(" company_id = " + company.getStructureID()); 
      } 
      list.add(" company_id = " + company.getStructureID());
      query.append(String.valueOf(list.toString()) + ") ");
    } 
    if (!strFamily.equalsIgnoreCase("") && !strFamily.equalsIgnoreCase("0"))
      query.append(" AND header.[family_id]= " + strFamily); 
    if (!strLabel.equalsIgnoreCase("") && !strLabel.equalsIgnoreCase("0"))
      query.append(" AND header.[label_id]= " + strLabel); 
    if (!strArtist.equalsIgnoreCase(""))
      query.append(" AND header.[artist] LIKE '%" + escapeSingleQuotes(strArtist) + "%'"); 
    if (!strStatus.equalsIgnoreCase("") && !strStatus.equalsIgnoreCase("all"))
      if (strStatus.equalsIgnoreCase("Active, excluding TBS")) {
        query.append(" AND header.[status] ='active'");
      } else if (strStatus.equalsIgnoreCase("TBS")) {
        query.append(" AND (header.[status] ='TBS' OR header.[status] ='ITW')");
      } else if (strStatus.equalsIgnoreCase("active")) {
        query.append(" AND (header.[status] ='active' OR header.[status] ='TBS' OR header.[status] ='ITW')");
      } else if (strStatus.equalsIgnoreCase("All, excluding TBS")) {
        query.append(" AND (header.[status] ='active' OR header.[status] ='closed' OR header.[status] ='cancel')");
      } else {
        query.append(" AND header.[status] ='" + strStatus + "'");
      }  
    if (!strReleaseType.equalsIgnoreCase("") && !strReleaseType.equalsIgnoreCase("all"))
      if (strReleaseType.equalsIgnoreCase("commercial")) {
        query.append(" AND header.[release_type] ='CO'");
      } else {
        query.append(" AND header.[release_type] ='PR'");
      }  
    if (!strLabelContact.equalsIgnoreCase("") && !strLabelContact.equalsIgnoreCase("0"))
      query.append(" AND header.[contact_id] =" + strLabelContact); 
    if (strSubconfiguration != null && 
      !strSubconfiguration[0].trim().equalsIgnoreCase("0") && 
      !strSubconfiguration[0].trim().equalsIgnoreCase("") && 
      !strSubconfiguration[0].trim().equalsIgnoreCase("all")) {
      boolean addOr = false;
      query.append(" AND (");
      for (int x = 0; x < strSubconfiguration.length; x++) {
        if (addOr)
          query.append(" OR "); 
        String txtvalue = strSubconfiguration[x];
        String txtconfigcode = txtvalue.substring(1, txtvalue.indexOf("]"));
        String txtsubconfigcode = txtvalue.substring(txtvalue.indexOf("]") + 1, txtvalue.length());
        query.append(" (header.[configuration] = '" + txtconfigcode.trim() + "' AND ");
        query.append(" header.[sub_configuration] = '" + txtsubconfigcode.trim() + "') ");
        addOr = true;
      } 
      query.append(") ");
    } else if (strConfiguration != null && !strConfiguration[0].equalsIgnoreCase("") && !strConfiguration[0].equalsIgnoreCase("0") && 
      !strConfiguration[0].equalsIgnoreCase("all")) {
      boolean addOr = false;
      query.append(" AND (");
      for (int x = 0; x < strConfiguration.length; x++) {
        if (addOr)
          query.append(" OR "); 
        query.append(" header.[configuration] = '" + strConfiguration[x].trim() + "' ");
        addOr = true;
      } 
      query.append(") ");
    } 
    if (!strUmlContact.equalsIgnoreCase("") && !strUmlContact.equalsIgnoreCase("0"))
      query.append(" AND mfg.[uml_id] =" + strUmlContact); 
    String beginDate = "";
    beginDate = (reportForm.getStringValue("beginDate") != null && reportForm.getStringValue("beginDate").length() > 0) ? reportForm.getStringValue("beginDate") : "";
    String endDate = "";
    endDate = (reportForm.getStringValue("endDate") != null && reportForm.getStringValue("endDate").length() > 0) ? reportForm.getStringValue("endDate") : "";
    if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase(""))
      query.append(" AND ((header.[status] ='TBS' OR header.[status] ='ITW') OR ("); 
    if (!beginDate.equalsIgnoreCase(""))
      query.append(" street_date >= '" + escapeSingleQuotes(beginDate) + "'"); 
    if (!endDate.equalsIgnoreCase(""))
      if (!beginDate.equalsIgnoreCase("")) {
        query.append(" AND street_date <= '" + escapeSingleQuotes(endDate) + "'");
      } else {
        query.append(" street_date <= '" + escapeSingleQuotes(endDate) + "'");
      }  
    if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase(""))
      query.append("))"); 
    query.append(" ORDER BY header.[release_id], header.[artist], header.[street_date], header.[title]");
    JdbcConnector connector = getConnector(query.toString());
    connector.runQuery();
    Vector selections = null;
    selections = new Vector();
    while (connector.more()) {
      if (bParent) {
        String prefixId = "";
        String tmpTitleId = connector.getField("title_id", "");
        String tmpSelectionNo = connector.getField("selection_no", "");
        prefixId = SelectionManager.getLookupObjectValue((PrefixCode)getLookupObject(connector.getField("prefix", ""), Cache.getPrefixCodes()));
        if (!tmpTitleId.equalsIgnoreCase(String.valueOf(prefixId) + tmpSelectionNo)) {
          connector.next();
          continue;
        } 
      } 
      int numberOfUnits = 0;
      try {
        numberOfUnits = connector.getIntegerField("pfm.[units_per_set]");
      } catch (Exception exception) {}
      Selection selection = null;
      selection = new Selection();
      selection.setSelectionID(connector.getIntegerField("release_id"));
      String selectionNo = "";
      if (connector.getFieldByName("selection_no") != null)
        selectionNo = connector.getFieldByName("selection_no"); 
      selection.setSelectionNo(selectionNo);
      selection.setProjectID(connector.getField("project_no", ""));
      String titleId = "";
      if (connector.getFieldByName("title_id") != null)
        titleId = connector.getFieldByName("title_id"); 
      selection.setTitleID(titleId);
      selection.setTitle(connector.getField("title", ""));
      selection.setArtistFirstName(connector.getField("artist_first_name", ""));
      selection.setArtistLastName(connector.getField("artist_last_name", ""));
      selection.setArtist(connector.getField("artist", ""));
      selection.setASide(connector.getField("side_a_title", ""));
      selection.setBSide(connector.getField("side_b_title", ""));
      selection.setProductCategory((ProductCategory)getLookupObject(connector.getField("product_line"), 
            Cache.getProductCategories()));
      selection.setReleaseType((ReleaseType)getLookupObject(connector.getField("release_type"), 
            Cache.getReleaseTypes()));
      selection.setSelectionConfig(getSelectionConfigObject(connector.getField("configuration"), 
            Cache.getSelectionConfigs()));
      selection.setSelectionSubConfig(getSelectionSubConfigObject(connector.getField("sub_configuration"), 
            selection.getSelectionConfig()));
      selection.setUpc(connector.getField("upc", ""));
      String sellCodeString = connector.getFieldByName("price_code");
      if (sellCodeString != null)
        selection.setPriceCode(SelectionManager.getInstance().getPriceCode(sellCodeString)); 
      selection.setSellCode(sellCodeString);
      selection.setGenre((Genre)getLookupObject(connector.getField("genre"), 
            Cache.getMusicTypes()));
      selection.setFamily((Family)getStructureObject(connector.getIntegerField("family_id")));
      selection.setCompany((Company)getStructureObject(connector.getIntegerField("company_id")));
      selection.setDivision((Division)getStructureObject(connector.getIntegerField("division_id")));
      selection.setLabel((Label)getStructureObject(connector.getIntegerField("label_id")));
      String streetDateString = connector.getFieldByName("street_date");
      if (streetDateString != null)
        selection.setStreetDate(getDatabaseDate(streetDateString)); 
      String internationalDateString = connector.getFieldByName("international_date");
      if (internationalDateString != null)
        selection.setInternationalDate(getDatabaseDate(internationalDateString)); 
      selection.setOtherContact(connector.getField("coordinator_contacts", ""));
      if (UserManager.getInstance().getUser(connector.getIntegerField("contact_id")) != null)
        selection.setLabelContact(UserManager.getInstance().getUser(connector.getIntegerField("contact_id"))); 
      selection.setSelectionStatus((SelectionStatus)getLookupObject(connector.getField("status"), 
            Cache.getSelectionStatusList()));
      selection.setHoldSelection(connector.getBoolean("hold_indicator"));
      selection.setHoldReason(connector.getField("hold_reason", ""));
      selection.setComments(connector.getField("comments", ""));
      selection.setSpecialPackaging(connector.getBoolean("special_pkg_indicator"));
      selection.setNumberOfUnits(numberOfUnits);
      selection.setPressAndDistribution(connector.getBoolean("pd_indicator"));
      selection.setPrefixID((PrefixCode)getLookupObject(connector.getField("prefix"), Cache.getPrefixCodes()));
      selection.setSelectionPackaging(connector.getField("packaging", ""));
      String impactDateString = connector.getFieldByName("impact_date");
      if (impactDateString != null)
        selection.setImpactDate(getDatabaseDate(impactDateString)); 
      String lastUpdateDateString = connector.getFieldByName("last_updated_on");
      if (lastUpdateDateString != null)
        selection.setLastUpdateDate(getDatabaseDate(lastUpdateDateString)); 
      selection.setLastUpdatingUser(UserManager.getInstance().getUser(connector.getIntegerField("last_updated_by")));
      String originDateString = connector.getFieldByName("entered_on");
      if (originDateString != null)
        selection.setOriginDate(getDatabaseDate(originDateString)); 
      User umlContact = UserManager.getInstance().getUser(connector.getIntegerField("mfg.[uml_id]"));
      selection.setUmlContact(umlContact);
      selection.setPlant(getLookupObject(connector.getField("mfg.[plant]"), Cache.getVendors()));
      selection.setDistribution(getLookupObject(connector.getField("mfg.[distribution]"), Cache.getDistributionCodes()));
      selection.setPoQuantity(connector.getIntegerField("mfg.[order_qty]"));
      selection.setCompletedQuantity(connector.getIntegerField("mfg.[complete_qty]"));
      selection.setManufacturingComments(connector.getField("mfg.[order_comments]", ""));
      selection.setPrice(connector.getFloat("mfg.[list_price]"));
      selection.setFullSelection(true);
      String strDistribution = (reportForm.getStringValue("distribution") != null) ? reportForm.getStringValue("distribution") : "";
      String strFuture2 = (reportForm.getStringValue("Future2") != null) ? reportForm.getStringValue("Future2") : "";
      if (!strDistribution.equalsIgnoreCase("") && !strDistribution.equalsIgnoreCase("All")) {
        String selDistribution = SelectionManager.getLookupObjectValue(selection.getDistribution());
        if (!selDistribution.equalsIgnoreCase(strDistribution.trim()))
          continue; 
      } 
      selections.add(selection);
      connector.next();
    } 
    connector.close();
    StringBuffer scheduleQuery = new StringBuffer(200);
    StringBuffer idList = new StringBuffer(200);
    scheduleQuery.append("SELECT header.[release_id], detail.*, task.* FROM vi_release_header header with (nolock) LEFT JOIN vi_release_detail detail with (nolock) ON header.[release_id] = detail.[release_id] LEFT JOIN vi_Task task with (nolock) ON detail.[task_id] = task.[task_id] WHERE ");
    for (int z = 0; z < selections.size(); z++) {
      Selection current = (Selection)selections.get(z);
      idList.append(" header.[release_id] = " + current.getSelectionID());
      if (z < selections.size() - 1)
        idList.append(" OR "); 
    } 
    scheduleQuery.append(idList.toString());
    JdbcConnector scheduleConnector = getConnector(scheduleQuery.toString());
    scheduleConnector.runQuery();
    while (scheduleConnector.more()) {
      int nextReleaseId = scheduleConnector.getIntegerField("release_id");
      Schedule schedule = new Schedule();
      schedule.setSelectionID(nextReleaseId);
      Vector precacheSchedule = new Vector();
      while (scheduleConnector.more() && scheduleConnector.getIntegerField("release_id") == nextReleaseId) {
        ScheduledTask scheduledTask = null;
        scheduledTask = new ScheduledTask();
        scheduledTask.setReleaseID(scheduleConnector.getIntegerField("release_Id"));
        scheduledTask.setTaskID(scheduleConnector.getIntegerField("task_id"));
        scheduledTask.setOwner((Family)getStructureObject(scheduleConnector.getIntegerField("task.owner")));
        String dueDateString = scheduleConnector.getField("task.due_date", "");
        if (dueDateString.length() > 0)
          scheduledTask.setDueDate(getDatabaseDate(dueDateString)); 
        String completionDateString = scheduleConnector.getField("task.completion_date", "");
        if (completionDateString.length() > 0)
          scheduledTask.setCompletionDate(getDatabaseDate(completionDateString)); 
        String taskStatus = scheduleConnector.getField("task.status", "");
        if (taskStatus.length() > 1)
          scheduledTask.setScheduledTaskStatus(taskStatus); 
        int day = scheduleConnector.getIntegerField("task.day_of_week");
        if (day > 0)
          scheduledTask.setDayOfTheWeek(new Day(scheduleConnector.getIntegerField("task.day_of_week"))); 
        int weeks = scheduleConnector.getIntegerField("task.weeks_to_release");
        if (weeks > 0)
          scheduledTask.setWeeksToRelease(scheduleConnector.getIntegerField("task.weeks_to_release")); 
        String vendorString = scheduleConnector.getField("vendor", "");
        if (vendorString.length() > 0)
          scheduledTask.setVendor(vendorString); 
        int taskAbbrevID = scheduleConnector.getIntegerField("task.abbrev_id");
        scheduledTask.setTaskAbbreviationID(taskAbbrevID);
        int taskID = connector.getIntegerField("task_id");
        scheduledTask.setScheduledTaskID(taskID);
        String taskDept = scheduleConnector.getField("task.department", "");
        scheduledTask.setDepartment(taskDept);
        scheduledTask.setKeyTask(scheduleConnector.getBoolean("task.key_task_indicator"));
        scheduledTask.setAuthorizationName(scheduleConnector.getField("task.authorization_name", ""));
        String authDateString = scheduleConnector.getField("task.authorization_date", "");
        if (authDateString.length() > 0)
          scheduledTask.setAuthorizationDate(getDatabaseDate(authDateString)); 
        String comments = scheduleConnector.getField("task.comments", "");
        scheduledTask.setComments(comments);
        int selNo = scheduledTask.getReleaseID();
        int taskId = scheduledTask.getTaskID();
        Vector multCompleteDates = ScheduleManager.getInstance().getMultCompleteDates(selNo, taskId);
        scheduledTask.setMultCompleteDates(multCompleteDates);
        scheduledTask.setAllowMultCompleteDatesFlag(connector.getBoolean("allowMultcompleteDatesFlag"));
        scheduledTask.setName(scheduleConnector.getField("task.name", ""));
        precacheSchedule.add(scheduledTask);
        scheduledTask = null;
        if (scheduleConnector.more()) {
          scheduleConnector.next();
          continue;
        } 
        break;
      } 
      schedule.setTasks(precacheSchedule);
      for (int y = 0; y < selections.size(); y++) {
        Selection currentSelection = (Selection)selections.get(y);
        if (currentSelection.getSelectionID() == schedule.getSelectionID());
        if (schedule.getTasks().size() > 0) {
          currentSelection.setSchedule(schedule);
          break;
        } 
        selections.remove(y);
      } 
    } 
    return selections;
  }
  
  public static void applyManufacturingToSelections(Vector selections) {
    if (selections != null)
      for (int i = 0; i < selections.size(); i++) {
        Selection selection = (Selection)selections.get(i);
        SelectionManager.getInstance().getSelectionManufacturingSubDetail(selection);
      }  
  }
  
  protected static Vector filterSelectionsWithReportCriteria(Vector selections, Context context) {
    Vector selectionsForReport = new Vector();
    Form reportForm = (Form)context.getSessionValue("reportForm");
    if (selections == null || selections.size() == 0)
      return selectionsForReport; 
    Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && reportForm.getStringValue("beginDate").length() > 0) ? getDate(reportForm.getStringValue("beginDate")) : null;
    if (beginStDate == null) {
      beginStDate = Calendar.getInstance();
      beginStDate.setTime(new Date(0L));
    } 
    beginStDate.set(11, 0);
    beginStDate.set(12, 0);
    beginStDate.set(13, 0);
    beginStDate.set(14, 0);
    Calendar endStDate = (reportForm.getStringValue("endDate") != null && reportForm.getStringValue("endDate").length() > 0) ? getDate(reportForm.getStringValue("endDate")) : null;
    if (endStDate != null) {
      endStDate.set(11, 23);
      endStDate.set(12, 59);
      endStDate.set(13, 59);
      endStDate.set(14, 999);
    } 
    String strDistribution = (reportForm.getStringValue("distribution") != null) ? reportForm.getStringValue("distribution") : "";
    String strLabelContact = (reportForm.getStringValue("contact") != null) ? reportForm.getStringValue("contact") : "";
    String strUmlContact = (reportForm.getStringValue("umlContact") != null) ? reportForm.getStringValue("umlContact") : "";
    String strFuture2 = (reportForm.getStringValue("Future2") != null) ? reportForm.getStringValue("Future2") : "";
    String strReleaseType = (reportForm.getStringValue("releaseType") != null) ? reportForm.getStringValue("releaseType") : "";
    String strStatus = (reportForm.getStringValue("status") != null) ? reportForm.getStringValue("status") : "";
    String strArtist = (reportForm.getStringValue("artist") != null) ? reportForm.getStringValue("artist") : "";
    String strFamily = (reportForm.getStringValue("family") != null) ? reportForm.getStringValue("family") : "";
    String strCompany = (reportForm.getStringValue("company") != null) ? reportForm.getStringValue("company") : "";
    String strLabel = (reportForm.getStringValue("Label") != null) ? reportForm.getStringValue("Label") : "";
    String[] strConfiguration = null;
    try {
      MilestoneFormDropDownMenu configList = (MilestoneFormDropDownMenu)reportForm.getElement("configurationList");
      if (configList != null) {
        ArrayList configListAl = configList.getStringValues();
        if (configListAl != null) {
          strConfiguration = new String[configListAl.size()];
          strConfiguration = (String[])configListAl.toArray(strConfiguration);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("<<< Configuration vector execption " + e.getMessage());
    } 
    String[] strSubconfiguration = null;
    try {
      MilestoneFormDropDownMenu subconfigList = (MilestoneFormDropDownMenu)reportForm.getElement("subconfigurationList");
      if (subconfigList != null) {
        ArrayList subconfigListAl = subconfigList.getStringValues();
        if (subconfigListAl != null) {
          strSubconfiguration = new String[subconfigListAl.size()];
          strSubconfiguration = (String[])subconfigListAl.toArray(strSubconfiguration);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("<<< Subonfiguration vector execption " + e.getMessage());
    } 
    boolean bParent = (reportForm.getElement("ParentsOnly") != null) ? ((FormCheckBox)reportForm.getElement("ParentsOnly")).isChecked() : 0;
    int totalCount = selections.size();
    int tenth = 0;
    tenth = totalCount / 5;
    if (tenth < 1)
      tenth = 1; 
    try {
      HttpServletResponse sresponse = context.getResponse();
      context.putDelivery("status", new String("start_gathering"));
      context.putDelivery("percent", new String("10"));
      context.includeJSP("status.jsp", "hiddenFrame");
      sresponse.setContentType("text/plain");
      sresponse.flushBuffer();
    } catch (Exception exception) {}
    int recordCount = 0;
    int count = 0;
    for (int i = 0; i < selections.size(); i++) {
      try {
        if (count < recordCount / tenth) {
          count = recordCount / tenth;
          HttpServletResponse sresponse = context.getResponse();
          context.putDelivery("status", new String("start_gathering"));
          context.putDelivery("percent", new String(String.valueOf(count * 10 + 50)));
          context.includeJSP("status.jsp", "hiddenFrame");
          sresponse.setContentType("text/plain");
          sresponse.flushBuffer();
        } 
        recordCount++;
        Selection sel = (Selection)selections.elementAt(i);
        sel = SelectionManager.getInstance().getSelectionHeader(sel.getSelectionID());
        String status = "";
        if (sel.getSelectionStatus() != null)
          status = sel.getSelectionStatus().getName(); 
        if (!status.equalsIgnoreCase("TBS") && !status.equalsIgnoreCase("In the works")) {
          Calendar stDate = sel.getStreetDate();
          if (stDate != null && ((beginStDate != null && stDate.before(beginStDate)) || (endStDate != null && stDate.after(endStDate))))
            continue; 
        } 
        if (!strDistribution.equalsIgnoreCase("") && !strDistribution.equalsIgnoreCase("All")) {
          String selDistribution = "";
          if (sel.getLabel().getDistribution() == 1) {
            selDistribution = "East";
          } else if (sel.getLabel().getDistribution() == 0) {
            selDistribution = "West";
          } 
          if (!selDistribution.equalsIgnoreCase(strDistribution.trim()))
            continue; 
        } 
        if (!strLabelContact.trim().equalsIgnoreCase("0") && !strLabelContact.trim().equalsIgnoreCase("")) {
          if (sel.getLabelContact() == null)
            continue; 
          String selLabelContactName = Integer.toString(sel.getLabelContact().getUserId());
          if (!selLabelContactName.equalsIgnoreCase(strLabelContact))
            continue; 
        } 
        if (!strUmlContact.trim().equalsIgnoreCase("0") && !strUmlContact.trim().equalsIgnoreCase("")) {
          SelectionManager.getInstance().getSelectionManufacturingSubDetail(sel);
          if (sel.getUmlContact() == null)
            continue; 
          String selUmlContactName = Integer.toString(sel.getUmlContact().getUserId());
          if (!selUmlContactName.equalsIgnoreCase(strUmlContact))
            continue; 
        } 
        if (!strReleaseType.equalsIgnoreCase("") && !strReleaseType.equalsIgnoreCase("All")) {
          String releaseType = "";
          if (sel.getReleaseType() != null) {
            releaseType = (sel.getReleaseType().getName() != null) ? sel.getReleaseType().getName() : "";
            if (!releaseType.equalsIgnoreCase(strReleaseType))
              continue; 
          } else {
            continue;
          } 
        } 
        String selectionStatus = SelectionManager.getLookupObjectValue(sel.getSelectionStatus()).trim();
        if (!strStatus.equalsIgnoreCase("") && !strStatus.equalsIgnoreCase("All")) {
          if (strStatus.trim().equalsIgnoreCase("active")) {
            if (!selectionStatus.equalsIgnoreCase("TBS") && !selectionStatus.equalsIgnoreCase("ITW") && !selectionStatus.equalsIgnoreCase("active"))
              continue; 
          } else if (strStatus.trim().equalsIgnoreCase("Active, excluding TBS")) {
            if (!selectionStatus.trim().equalsIgnoreCase("active"))
              continue; 
          } else if (strStatus.trim().equalsIgnoreCase("TBS")) {
            if (!selectionStatus.trim().equalsIgnoreCase("TBS") && !selectionStatus.trim().equalsIgnoreCase("ITW"))
              continue; 
          } else if (strStatus.trim().equalsIgnoreCase("All, excluding TBS")) {
            if (!selectionStatus.trim().equalsIgnoreCase("active") && !selectionStatus.trim().equalsIgnoreCase("CLOSED"))
              continue; 
          } else if (strStatus.trim().equalsIgnoreCase("Cancelled")) {
            if (!selectionStatus.equalsIgnoreCase("CANCEL"))
              continue; 
          } else if (!selectionStatus.equalsIgnoreCase("CLOSED")) {
            continue;
          } 
        } else if (!selectionStatus.equalsIgnoreCase("CANCEL")) {
          continue;
        } 
        if (!strArtist.trim().equals("") && sel.getArtist() != null) {
          String artistUpperCase = sel.getArtist().trim().toUpperCase();
          if (!artistUpperCase.startsWith(strArtist.trim().toUpperCase()))
            continue; 
        } 
        if (!strFamily.equals("") && !strFamily.equals("0"))
          if (sel.getFamily() == null || !strFamily.equals(sel.getFamily().getIdentity()))
            continue;  
        if (!strCompany.equals("") && !strCompany.equals("0"))
          if (sel.getCompany() == null || !strCompany.equals(sel.getCompany().getStructureID()))
            continue;  
        if (!strLabel.equals("") && !strLabel.equals("0"))
          if (sel.getLabel() == null || !strLabel.equals(sel.getLabel().getStructureID()))
            continue;  
        if (bParent) {
          String prefixId = "";
          if (sel.getPrefixID() != null)
            prefixId = SelectionManager.getLookupObjectValue(sel.getPrefixID()); 
          if (!sel.getTitleID().equalsIgnoreCase(String.valueOf(prefixId) + sel.getSelectionNo()))
            continue; 
        } 
        if (strSubconfiguration != null && 
          !strSubconfiguration[0].trim().equalsIgnoreCase("0") && 
          !strSubconfiguration[0].trim().equalsIgnoreCase("") && 
          !strSubconfiguration[0].trim().equalsIgnoreCase("all")) {
          if (sel.getSelectionSubConfig() != null && sel.getSelectionConfig() != null) {
            String selConfigurationName = sel.getSelectionConfig().getSelectionConfigurationAbbreviation();
            String selSubconfigurationName = sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation();
            boolean subconfigFnd = false;
            for (int x = 0; x < strSubconfiguration.length; x++) {
              String txtvalue = strSubconfiguration[x];
              String txtconfigcode = txtvalue.substring(1, txtvalue.indexOf("]"));
              String txtsubconfigcode = txtvalue.substring(txtvalue.indexOf("]") + 1, txtvalue.length());
              if (selConfigurationName.equalsIgnoreCase(txtconfigcode) && 
                selSubconfigurationName.equalsIgnoreCase(txtsubconfigcode)) {
                subconfigFnd = true;
                break;
              } 
            } 
            if (!subconfigFnd)
              continue; 
          } 
        } else if (strConfiguration != null && !strConfiguration[0].trim().equalsIgnoreCase("0") && !strConfiguration[0].trim().equalsIgnoreCase("") && 
          !strConfiguration[0].trim().equalsIgnoreCase("all")) {
          if (sel.getSelectionConfig() != null) {
            String selConfigurationName = sel.getSelectionConfig().getSelectionConfigurationAbbreviation();
            boolean configFnd = false;
            for (int x = 0; x < strConfiguration.length; x++) {
              if (selConfigurationName.equalsIgnoreCase(strConfiguration[x])) {
                configFnd = true;
                break;
              } 
            } 
            if (!configFnd)
              continue; 
          } 
        } 
        selectionsForReport.addElement(sel);
        continue;
      } catch (Exception exception) {
        continue;
      } 
    } 
    return selectionsForReport;
  }
  
  public static FormDropDownMenu getTaskAbbreviationsDropDown(String name, String selectedOption, boolean required) {
    Vector values = new Vector();
    Vector menuText = new Vector();
    values.addElement("-1");
    menuText.addElement("&nbsp;&nbsp;");
    FormDropDownMenu dropDown = null;
    if (selectedOption == null)
      selectedOption = ""; 
    Vector deptList = Cache.getLookupDetailValuesFromDatabase(33);
    if (deptList != null) {
      for (int i = 0; i < deptList.size(); i++) {
        LookupObject department = (LookupObject)deptList.elementAt(i);
        if (department != null) {
          if (department.getInactive())
            if (!department.getAbbreviation().equalsIgnoreCase(selectedOption))
              continue;  
          values.addElement(department.getAbbreviation());
          menuText.addElement(department.getName());
        } 
        continue;
      } 
      String[] arrayValues = new String[values.size()];
      String[] arrayMenuText = new String[menuText.size()];
      arrayValues = (String[])values.toArray(arrayValues);
      arrayMenuText = (String[])menuText.toArray(arrayMenuText);
      dropDown = new FormDropDownMenu(name, 
          selectedOption, 
          arrayValues, 
          arrayMenuText, 
          required);
    } 
    return dropDown;
  }
  
  public static FormDropDownMenu getDepartmentDropDown(String name, String selectedOption, boolean required) {
    Vector values = new Vector();
    Vector menuText = new Vector();
    values.addElement("-1");
    menuText.addElement("&nbsp;&nbsp;");
    FormDropDownMenu dropDown = null;
    if (selectedOption == null)
      selectedOption = ""; 
    Vector deptList = Cache.getLookupDetailValuesFromDatabase(18);
    if (deptList != null) {
      for (int i = 0; i < deptList.size(); i++) {
        LookupObject department = (LookupObject)deptList.elementAt(i);
        if (department != null) {
          if (department.getInactive())
            if (!department.getAbbreviation().equalsIgnoreCase(selectedOption))
              continue;  
          values.addElement(department.getAbbreviation());
          menuText.addElement(department.getName());
        } 
        continue;
      } 
      String[] arrayValues = new String[values.size()];
      String[] arrayMenuText = new String[menuText.size()];
      arrayValues = (String[])values.toArray(arrayValues);
      arrayMenuText = (String[])menuText.toArray(arrayMenuText);
      dropDown = new FormDropDownMenu(name, 
          selectedOption, 
          arrayValues, 
          arrayMenuText, 
          required);
    } 
    return dropDown;
  }
  
  public static Vector getAllSelectionsForUser(Context context) {
    Vector companies = getUserCompanies(context);
    Company company = null;
    Vector precache = new Vector();
    Selection selection = null;
    StringBuffer query = new StringBuffer(200);
    Form reportForm = (Form)context.getSessionValue("reportForm");
    boolean bScheduled = (reportForm.getElement("ScheduledReleases") != null) ? ((FormCheckBox)reportForm.getElement("ScheduledReleases")).isChecked() : 0;
    String strUmlKey = (reportForm.getStringValue("umlKeyTask") != null) ? reportForm.getStringValue("umlKeyTask") : "";
    Report report = (Report)context.getSessionValue("Report");
    if (bScheduled || (!strUmlKey.equalsIgnoreCase("") && !strUmlKey.trim().equalsIgnoreCase("all") && 
      !report.getReportName().trim().equalsIgnoreCase("New Rel."))) {
      query.append("SELECT DISTINCT header.[release_id], header.[artist],  header.[title], header.[artist_first_name], header.[artist_last_name],  header.[artist_first_name] + ' ' + header.[artist_last_name] AS fl_artist, header.[street_date], header.[configuration], header.[sub_configuration], header.[upc], header.[prefix], header.[selection_no], header.[status],  header.[company_id], header.[family_id], header.[label_id]  FROM vi_Release_Header header with (nolock), vi_Release_Detail detail  with (nolock) WHERE (header.[release_id] = detail.[release_id]) AND (");
    } else {
      query.append("SELECT release_id, title, artist_first_name, artist_last_name, street_date, configuration, sub_configuration, artist_first_name + ' ' + artist_last_name AS fl_artist, upc, prefix, selection_no, status,  company_id, family_id, label_id  from vi_Release_Header header with (nolock) WHERE (");
    } 
    String strFamily = (reportForm.getStringValue("family") != null) ? reportForm.getStringValue("family") : "";
    String strCompany = (reportForm.getStringValue("company") != null) ? reportForm.getStringValue("company") : "";
    String strLabel = (reportForm.getStringValue("Label") != null) ? reportForm.getStringValue("Label") : "";
    if (!strCompany.equalsIgnoreCase("") && !strCompany.equalsIgnoreCase("0")) {
      query.append(" company_id = " + strCompany);
    } else {
      StringList list = new StringList(" OR ");
      query.append(String.valueOf(list.toString()) + " (");
      for (int i = 0; i < companies.size(); i++) {
        company = (Company)companies.get(i);
        if (company != null)
          list.add(" company_id = " + company.getStructureID()); 
      } 
      query.append(String.valueOf(list.toString()) + ") ");
    } 
    if (!strFamily.equalsIgnoreCase("") && !strFamily.equalsIgnoreCase("0"))
      query.append(" AND family_id = " + strFamily); 
    if (!strLabel.equalsIgnoreCase("") && !strLabel.equalsIgnoreCase("0"))
      query.append(" AND label_id = " + strLabel); 
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
        if (report.getReportName().trim().equalsIgnoreCase("New Rel.")) {
          query.append(" AND (EXISTS (SELECT owner FROM vi_release_detail detail where owner = " + intUml);
          query.append(" AND detail.release_id = header.release_id)");
          query.append(" OR NOT EXISTS ");
          query.append(" (SELECT detailA.release_id FROM vi_release_detail detailA");
          query.append(" WHERE detailA.release_id = header.release_id)) ");
        } else if (strUmlKey.equalsIgnoreCase("UML")) {
          query.append(" AND owner = " + intUml);
        } else {
          query.append(" AND owner != " + intUml);
        }  
    } 
    String[] strConfiguration = null;
    try {
      MilestoneFormDropDownMenu configList = (MilestoneFormDropDownMenu)reportForm.getElement("configurationList");
      if (configList != null) {
        ArrayList configListAl = configList.getStringValues();
        if (configListAl != null) {
          strConfiguration = new String[configListAl.size()];
          strConfiguration = (String[])configListAl.toArray(strConfiguration);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("<<< Configuration vector execption " + e.getMessage());
    } 
    String[] strSubconfiguration = null;
    try {
      MilestoneFormDropDownMenu subconfigList = (MilestoneFormDropDownMenu)reportForm.getElement("subconfigurationList");
      if (subconfigList != null) {
        ArrayList subconfigListAl = subconfigList.getStringValues();
        if (subconfigListAl != null) {
          strSubconfiguration = new String[subconfigListAl.size()];
          strSubconfiguration = (String[])subconfigListAl.toArray(strSubconfiguration);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("<<< Subconfiguration vector execption " + e.getMessage());
    } 
    if (strSubconfiguration != null && 
      !strSubconfiguration[0].trim().equalsIgnoreCase("0") && 
      !strSubconfiguration[0].trim().equalsIgnoreCase("") && 
      !strSubconfiguration[0].trim().equalsIgnoreCase("all")) {
      boolean addOr = false;
      query.append(" AND (");
      for (int x = 0; x < strSubconfiguration.length; x++) {
        if (addOr)
          query.append(" OR "); 
        String txtvalue = strSubconfiguration[x];
        String txtconfigcode = txtvalue.substring(1, txtvalue.indexOf("]"));
        String txtsubconfigcode = txtvalue.substring(txtvalue.indexOf("]") + 1, txtvalue.length());
        query.append(" (header.[configuration] = '" + txtconfigcode.trim() + "' AND ");
        query.append(" header.[sub_configuration] = '" + txtsubconfigcode.trim() + "') ");
        addOr = true;
      } 
      query.append(") ");
    } else if (strConfiguration != null && !strConfiguration[0].equalsIgnoreCase("") && !strConfiguration[0].equalsIgnoreCase("0") && 
      !strConfiguration[0].equalsIgnoreCase("all")) {
      boolean addOr = false;
      query.append(" AND (");
      for (int x = 0; x < strConfiguration.length; x++) {
        if (addOr)
          query.append(" OR "); 
        query.append(" header.[configuration] = '" + strConfiguration[x].trim() + "' ");
        addOr = true;
      } 
      query.append(") ");
    } 
    String beginDate = "";
    beginDate = (reportForm.getStringValue("beginDate") != null && reportForm.getStringValue("beginDate").length() > 0) ? reportForm.getStringValue("beginDate") : "";
    String endDate = "";
    endDate = (reportForm.getStringValue("endDate") != null && reportForm.getStringValue("endDate").length() > 0) ? reportForm.getStringValue("endDate") : "";
    if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase(""))
      query.append(" AND (( header.status ='TBS' OR header.status ='ITW') OR ("); 
    if (!beginDate.equalsIgnoreCase(""))
      query.append(" street_date >= '" + escapeSingleQuotes(beginDate) + "'"); 
    if (!endDate.equalsIgnoreCase(""))
      if (!beginDate.equalsIgnoreCase("")) {
        query.append(" AND street_date <= '" + escapeSingleQuotes(endDate) + "'");
      } else {
        query.append(" street_date <= '" + escapeSingleQuotes(endDate) + "'");
      }  
    if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase(""))
      query.append("))"); 
    query.append(") ORDER BY artist, street_date, title");
    JdbcConnector connector = getConnector(query.toString());
    connector.setForwardOnly(false);
    connector.runQuery();
    int totalCount = 0;
    int tenth = 0;
    totalCount = connector.getRowCount();
    tenth = totalCount / 5;
    if (tenth < 1)
      tenth = 1; 
    try {
      HttpServletResponse sresponse = context.getResponse();
      context.putDelivery("status", new String("start_gathering"));
      context.putDelivery("percent", new String("10"));
      context.includeJSP("status.jsp", "hiddenFrame");
      sresponse.setContentType("text/plain");
      sresponse.flushBuffer();
    } catch (Exception exception) {}
    int recordCount = 0;
    int count = 0;
    while (connector.more()) {
      try {
        if (count < recordCount / tenth) {
          count = recordCount / tenth;
          HttpServletResponse sresponse = context.getResponse();
          context.putDelivery("status", new String("start_gathering"));
          context.putDelivery("percent", new String(String.valueOf(count * 10)));
          context.includeJSP("status.jsp", "hiddenFrame");
          sresponse.setContentType("text/plain");
          sresponse.flushBuffer();
        } 
        recordCount++;
        selection = new Selection();
        selection.setSelectionID(connector.getIntegerField("release_id"));
        selection.setTitle(connector.getField("title", ""));
        selection.setArtistFirstName(connector.getField("artist_first_name", ""));
        selection.setArtistLastName(connector.getField("artist_last_name", ""));
        selection.setFlArtist(connector.getField("fl_artist", ""));
        String streetDateString = connector.getFieldByName("street_date");
        if (streetDateString != null)
          selection.setStreetDate(getDatabaseDate(streetDateString)); 
        selection.setUpc(connector.getField("upc", ""));
        selection.setSelectionConfig(
            getSelectionConfigObject(connector.getField("configuration"), 
              Cache.getSelectionConfigs()));
        selection.setSelectionSubConfig(getSelectionSubConfigObject(connector.getField("sub_configuration"), selection.getSelectionConfig()));
        selection.setPrefixID((PrefixCode)getLookupObject(connector.getField("prefix"), Cache.getPrefixCodes()));
        selection.setSelectionNo(connector.getField("selection_no"));
        selection.setSelectionStatus((SelectionStatus)getLookupObject(connector.getField("status"), 
              Cache.getSelectionStatusList()));
        precache.add(selection);
        selection = null;
        connector.next();
      } catch (Exception exception) {}
    } 
    connector.close();
    company = null;
    return precache;
  }
  
  public static Hashtable groupSelectionsByTypeAndCategory(Vector selections) {
    Hashtable groupedByTypeAndCategory = new Hashtable();
    if (selections == null)
      return groupedByTypeAndCategory; 
    for (int i = 0; i < selections.size(); i++) {
      Selection sel = (Selection)selections.elementAt(i);
      if (sel != null) {
        sel = SelectionManager.getInstance().getSelectionAndSchedule(sel.getSelectionID());
        String typeString = "", categoryString = "";
        typeString = (sel.getReleaseType() != null) ? sel.getReleaseType().getName() : "";
        categoryString = (sel.getProductCategory() != null) ? sel.getProductCategory().getName() : "";
        Hashtable typeSubTable = (Hashtable)groupedByTypeAndCategory.get(typeString);
        if (typeSubTable == null) {
          typeSubTable = new Hashtable();
          groupedByTypeAndCategory.put(typeString, typeSubTable);
        } 
        Vector selectionsForCategory = (Vector)typeSubTable.get(categoryString);
        if (selectionsForCategory == null) {
          selectionsForCategory = new Vector();
          typeSubTable.put(categoryString, selectionsForCategory);
        } 
        selectionsForCategory.addElement(sel);
      } 
    } 
    return groupedByTypeAndCategory;
  }
  
  public static Hashtable groupSelectionsByCompanyAndSubconfigAndStreetDate(Vector selections) {
    Hashtable companyTable = new Hashtable();
    if (selections == null)
      return companyTable; 
    for (int i = 0; i < selections.size(); i++) {
      Selection sel = (Selection)selections.elementAt(i);
      if (sel != null) {
        sel = SelectionManager.getInstance().getSelectionAndSchedule(sel.getSelectionID());
        String companyName = "";
        String dateString = "";
        String configAbbreviation = "";
        String subconfigAbbreviation = "";
        String monthString = "";
        String statusString = "";
        dateString = getFormatedDate(sel.getStreetDate());
        if (sel.getStreetDate() != null) {
          SimpleDateFormat formatter = new SimpleDateFormat("MM");
          monthString = formatter.format(sel.getStreetDate().getTime());
        } 
        Company company = sel.getCompany();
        SelectionConfiguration config = sel.getSelectionConfig();
        SelectionSubConfiguration subconfig = sel.getSelectionSubConfig();
        SelectionStatus status = sel.getSelectionStatus();
        if (company != null)
          companyName = (company.getName() == null) ? "" : company.getName(); 
        if (config != null)
          configAbbreviation = (config.getSelectionConfigurationAbbreviation() == null) ? 
            "" : config.getSelectionConfigurationAbbreviation(); 
        if (subconfig != null)
          subconfigAbbreviation = (subconfig.getSelectionSubConfigurationAbbreviation() == null) ? 
            "" : subconfig.getSelectionSubConfigurationAbbreviation(); 
        if (status != null)
          statusString = (status.getName() == null) ? "" : status.getName(); 
        if (subconfigAbbreviation.equalsIgnoreCase("DVDVID") || 
          subconfigAbbreviation.equalsIgnoreCase("ECD") || 
          subconfigAbbreviation.equalsIgnoreCase("ECDEP") || 
          subconfigAbbreviation.equalsIgnoreCase("CASS") || 
          subconfigAbbreviation.equalsIgnoreCase("CASSEP") || 
          subconfigAbbreviation.equalsIgnoreCase("8TRK") || 
          subconfigAbbreviation.equalsIgnoreCase("CDROM") || 
          subconfigAbbreviation.equalsIgnoreCase("CDVID") || 
          subconfigAbbreviation.equalsIgnoreCase("DCCASS") || 
          subconfigAbbreviation.equalsIgnoreCase("LASER") || 
          subconfigAbbreviation.equalsIgnoreCase("VIDEO") || 
          subconfigAbbreviation.equalsIgnoreCase("CD") || 
          subconfigAbbreviation.equalsIgnoreCase("CDEP") || 
          subconfigAbbreviation.equalsIgnoreCase("CDADVD") || 
          subconfigAbbreviation.equalsIgnoreCase("DP") || 
          subconfigAbbreviation.equalsIgnoreCase("DVDAUD") || 
          subconfigAbbreviation.equalsIgnoreCase("SACD1") || 
          subconfigAbbreviation.equalsIgnoreCase("SACD2") || 
          subconfigAbbreviation.equalsIgnoreCase("SACD3") || 
          subconfigAbbreviation.equalsIgnoreCase("SACD4") || 
          subconfigAbbreviation.equalsIgnoreCase("ALBUM") || 
          subconfigAbbreviation.equalsIgnoreCase("VNYL10") || 
          subconfigAbbreviation.equalsIgnoreCase("VNYL12") || 
          configAbbreviation.equalsIgnoreCase("DUALDISC")) {
          subconfigAbbreviation = "Full Length";
        } else if (!subconfigAbbreviation.equals("")) {
          subconfigAbbreviation = "Singles";
        } 
        if (statusString.equalsIgnoreCase("TBS")) {
          monthString = "13";
        } else if (statusString.equalsIgnoreCase("In The Works")) {
          monthString = "26";
        } else {
          try {
            Integer.parseInt(monthString);
          } catch (Exception e) {
            monthString = "52";
          } 
        } 
        Hashtable companySubTable = (Hashtable)companyTable.get(companyName);
        if (companySubTable == null) {
          companySubTable = new Hashtable();
          companyTable.put(companyName, companySubTable);
        } 
        Hashtable subConfigTable = (Hashtable)companySubTable.get(subconfigAbbreviation);
        if (subConfigTable == null) {
          subConfigTable = new Hashtable();
          companySubTable.put(subconfigAbbreviation, subConfigTable);
        } 
        Hashtable monthsTable = (Hashtable)subConfigTable.get(monthString);
        if (monthsTable == null) {
          monthsTable = new Hashtable();
          subConfigTable.put(monthString, monthsTable);
        } 
        Vector selectionsForDates = (Vector)monthsTable.get(dateString);
        if (selectionsForDates == null) {
          selectionsForDates = new Vector();
          monthsTable.put(dateString, selectionsForDates);
        } 
        selectionsForDates.add(sel);
      } 
    } 
    return companyTable;
  }
  
  public static Hashtable groupSelectionsByTypeAndSubconfigAndStreetDate(Vector selections) {
    Hashtable configTable = new Hashtable();
    if (selections == null)
      return configTable; 
    for (int i = 0; i < selections.size(); i++) {
      Selection sel = (Selection)selections.elementAt(i);
      if (sel != null) {
        sel = SelectionManager.getInstance().getSelectionAndSchedule(sel.getSelectionID());
        String typeString = "";
        String dateString = "";
        String configName = "";
        String subconfigName = "";
        String monthString = "";
        String statusString = "";
        String typeConfigString = "";
        String cycleString = "";
        dateString = getFormatedDate(sel.getStreetDate());
        DatePeriod datePeriod = findDatePeriod(sel.getStreetDate());
        if (datePeriod != null)
          cycleString = datePeriod.getCycle(); 
        if (sel.getStreetDate() != null) {
          SimpleDateFormat formatter = new SimpleDateFormat("MMMMM");
          monthString = formatter.format(sel.getStreetDate().getTime());
        } 
        ReleaseType type = sel.getReleaseType();
        Company company = sel.getCompany();
        SelectionSubConfiguration subconfig = sel.getSelectionSubConfig();
        SelectionStatus status = sel.getSelectionStatus();
        if (subconfig != null)
          subconfigName = (subconfig.getSelectionSubConfigurationName() == null) ? 
            "" : subconfig.getSelectionSubConfigurationName(); 
        if (status != null)
          statusString = (status.getName() == null) ? "" : status.getName(); 
        if (type != null)
          typeString = (sel.getReleaseType() != null) ? sel.getReleaseType().getName() : ""; 
        if (typeString.equalsIgnoreCase("Commercial")) {
          if (subconfigName.equalsIgnoreCase("EP/Sampler") || 
            subconfigName.equalsIgnoreCase("Full") || 
            subconfigName.equalsIgnoreCase("Full Length") || 
            configName.equalsIgnoreCase("DualDisc")) {
            subconfigName = "Full Length";
          } else if (!subconfigName.equals("")) {
            subconfigName = "Singles";
          } 
        } else {
          typeString = "Promos";
          subconfigName = "";
        } 
        typeConfigString = String.valueOf(typeString) + " " + subconfigName;
        if (statusString.equalsIgnoreCase("TBS")) {
          monthString = "13";
          dateString = statusString;
        } else if (statusString.equalsIgnoreCase("In The Works")) {
          monthString = "26";
          dateString = "ITW";
        } else if (monthString.length() < 1) {
          monthString = "52";
        } 
        Hashtable typeConfigTable = (Hashtable)configTable.get(typeConfigString);
        if (typeConfigTable == null) {
          typeConfigTable = new Hashtable();
          configTable.put(typeConfigString, typeConfigTable);
        } 
        Hashtable monthsTable = (Hashtable)typeConfigTable.get(monthString);
        if (monthsTable == null) {
          monthsTable = new Hashtable();
          typeConfigTable.put(monthString, monthsTable);
        } 
        Vector selectionsForDates = (Vector)monthsTable.get(dateString);
        if (selectionsForDates == null) {
          selectionsForDates = new Vector();
          monthsTable.put(dateString, selectionsForDates);
        } 
        selectionsForDates.add(sel);
      } 
    } 
    return configTable;
  }
  
  public static Hashtable groupSelectionsByConfigAndStreetDate(Vector selections) {
    Hashtable groupSelectionsByConfigAndStreetDate = new Hashtable();
    if (selections == null)
      return groupSelectionsByConfigAndStreetDate; 
    for (int i = 0; i < selections.size(); i++) {
      Selection sel = (Selection)selections.elementAt(i);
      if (sel != null) {
        sel = SelectionManager.getInstance().getSelectionAndSchedule(sel.getSelectionID());
        String configString = "", dateString = "";
        configString = (sel.getSelectionConfig() != null) ? sel.getSelectionConfig().getSelectionConfigurationName() : "";
        dateString = (sel.getStreetDate() != null) ? getFormatedDate(sel.getStreetDate()) : "";
        SelectionStatus status = sel.getSelectionStatus();
        String statusString = "";
        if (status != null)
          statusString = (status.getName() == null) ? "" : status.getName(); 
        if (statusString.equalsIgnoreCase("TBS")) {
          dateString = statusString;
        } else if (statusString.equalsIgnoreCase("In The Works")) {
          dateString = "ITW";
        } 
        Hashtable streetTable = (Hashtable)groupSelectionsByConfigAndStreetDate.get(dateString);
        if (streetTable == null) {
          streetTable = new Hashtable();
          groupSelectionsByConfigAndStreetDate.put(dateString, streetTable);
        } 
        Vector configsForDate = (Vector)streetTable.get(configString);
        if (configsForDate == null) {
          configsForDate = new Vector();
          streetTable.put(configString, configsForDate);
        } 
        configsForDate.addElement(sel);
      } 
    } 
    return groupSelectionsByConfigAndStreetDate;
  }
  
  public static Vector groupSelectionsByReleaseMonth(Vector selections) {
    Vector groupedSelections = new Vector();
    if (selections == null)
      return groupedSelections; 
    setSelectionSorting(selections, 1);
    Collections.sort(selections);
    setSelectionSorting(selections, 16);
    Collections.sort(selections);
    Vector monthVector = new Vector();
    int currMonth = -10, currYear = -10;
    boolean justStarted = true;
    for (int i = 0; i < selections.size(); i++) {
      Selection sel = (Selection)selections.elementAt(i);
      if (sel != null) {
        Calendar streetDate = sel.getStreetDate();
        String status = (sel.getSelectionStatus() != null && sel.getSelectionStatus().getName() != null) ? 
          sel.getSelectionStatus().getName() : "";
        int selMonth = -1;
        int selYear = -1;
        if (status.equalsIgnoreCase("TBS")) {
          selMonth = 13;
          selYear = 13;
        } else if (status.equalsIgnoreCase("In the works")) {
          selMonth = 26;
          selYear = 26;
        } 
        if (streetDate != null && streetDate.get(2) == currMonth && 
          streetDate.get(1) == currYear) {
          monthVector.addElement(sel);
        } else if (selMonth == currMonth && 
          selYear == currYear) {
          monthVector.addElement(sel);
        } else if (streetDate == null && !status.equalsIgnoreCase("TBS") && 
          !status.equalsIgnoreCase("In The Works") && currMonth == 52) {
          monthVector.addElement(sel);
        } else {
          if (justStarted) {
            justStarted = false;
          } else {
            groupedSelections.addElement(monthVector.clone());
          } 
          monthVector = new Vector();
          monthVector.addElement(sel);
          if (streetDate != null && !status.equalsIgnoreCase("TBS") && 
            !status.equalsIgnoreCase("In The Works")) {
            currMonth = streetDate.get(2);
            currYear = streetDate.get(1);
          } else if (status.equalsIgnoreCase("TBS")) {
            currMonth = 13;
            currYear = 13;
          } else if (status.equalsIgnoreCase("In The Works")) {
            currMonth = 26;
            currYear = 26;
          } else {
            currMonth = 52;
            currYear = 52;
          } 
        } 
      } 
    } 
    groupedSelections.addElement(monthVector.clone());
    return groupedSelections;
  }
  
  public static String getMonthNameForUmeReport(Vector selections) {
    monthName = "";
    if (selections == null || selections.size() == 0)
      return monthName; 
    Selection sel = (Selection)selections.elementAt(0);
    if (sel == null)
      return monthName; 
    String status = (sel.getSelectionStatus() != null && sel.getSelectionStatus().getName() != null) ? 
      sel.getSelectionStatus().getName() : "";
    if (status.equalsIgnoreCase("TBS"))
      return "TBS"; 
    if (status.equalsIgnoreCase("In The Works"))
      return "ITW"; 
    if (sel.getStreetDate() == null)
      return "No Street Date"; 
    int monthNumber = sel.getStreetDate().get(2);
    switch (monthNumber) {
      case 0:
        return "January";
      case 1:
        return "February";
      case 2:
        return "March";
      case 3:
        return "April";
      case 4:
        return "May";
      case 5:
        return "June";
      case 6:
        return "July";
      case 7:
        return "August";
      case 8:
        return "September";
      case 9:
        return "October";
      case 10:
        return "November";
      case 11:
        return "December";
    } 
    return "TBS";
  }
  
  public static Vector getTasksForUmeProdScheduleReport(Selection sel) {
    Vector tasks = new Vector();
    int numTasks = 12;
    for (int i = 0; i < numTasks; i++)
      tasks.addElement(null); 
    Schedule schedule = ScheduleManager.getInstance().getSchedule(sel.getSelectionID());
    Vector allTasks = new Vector();
    if (schedule != null) {
      allTasks = schedule.getTasks();
      if (allTasks == null)
        allTasks = new Vector(); 
    } 
    for (int i = 0; i < allTasks.size(); i++) {
      ScheduledTask task = (ScheduledTask)allTasks.elementAt(i);
      if (task != null) {
        String taskName = (task.getName() != null) ? 
          task.getName().toUpperCase() : "";
        if (taskName.indexOf("START MEMO RECEIVED") != -1)
          tasks.set(0, task); 
        if (taskName.indexOf("CLEARANCE DEADLINE") != -1) {
          tasks.set(1, task);
        } else if (taskName.indexOf("COMPLETED") != -1 && taskName.indexOf("CREDITS") != -1) {
          tasks.set(2, task);
        } else if (taskName.indexOf("FINAL PACKAGING COPY") != -1) {
          tasks.set(3, task);
        } else if (taskName.indexOf("MECH") != -1 && taskName.indexOf("ROUTING") != -1) {
          tasks.set(4, task);
        } else if (taskName.indexOf("MECH") != -1 && taskName.indexOf("SEPARATIONS") != -1) {
          tasks.set(5, task);
        } else if (taskName.indexOf("SOLIC") != -1 && taskName.indexOf("FILM") != -1 && 
          taskName.indexOf("UMVD") != -1) {
          tasks.set(6, task);
        } else if (taskName.indexOf("PACKAGE") != -1 && taskName.indexOf("FILM") != -1 && 
          taskName.indexOf("SHIPS") != -1 && taskName.indexOf("PRINTER") != -1) {
          tasks.set(7, task);
        } else if (taskName.indexOf("MASTER") != -1 && taskName.indexOf("RECEIVED") != -1 && 
          taskName.indexOf("PLANT") != -1) {
          tasks.set(8, task);
        } else if (taskName.indexOf("INITIAL") != -1 && taskName.indexOf("MANUF") != -1 && 
          taskName.indexOf("QUANTIT") != -1) {
          tasks.set(9, task);
        } else if (taskName.indexOf("PRINT") != -1 && taskName.indexOf("RECEIVED") != -1 && 
          taskName.indexOf("PLANT") != -1) {
          tasks.set(10, task);
        } else if (taskName.indexOf("PLANT SHIP") != -1) {
          tasks.set(11, task);
        } 
      } 
    } 
    return tasks;
  }
  
  public static Vector getTasksForUmeProdScheduleReportAbbrev(Selection sel) {
    Vector tasks = new Vector();
    int numTasks = 14;
    for (int i = 0; i < numTasks; i++)
      tasks.addElement(null); 
    Schedule schedule = ScheduleManager.getInstance().getSchedule(sel.getSelectionID());
    Vector allTasks = new Vector();
    if (schedule != null) {
      allTasks = schedule.getTasks();
      if (allTasks == null)
        allTasks = new Vector(); 
    } 
    for (int i = 0; i < allTasks.size(); i++) {
      ScheduledTask task = (ScheduledTask)allTasks.elementAt(i);
      if (task != null) {
        String taskAbbrev = getTaskAbbreivationNameById(task.getTaskAbbreviationID());
        if (taskAbbrev.equalsIgnoreCase("CLR")) {
          tasks.set(1, task);
        } else if (taskAbbrev.equalsIgnoreCase("CRD")) {
          tasks.set(2, task);
        } else if (taskAbbrev.equalsIgnoreCase("FPC")) {
          tasks.set(3, task);
        } else if (taskAbbrev.equalsIgnoreCase("MBR")) {
          tasks.set(4, task);
        } else if (taskAbbrev.equalsIgnoreCase("SEPS")) {
          tasks.set(5, task);
        } else if (taskAbbrev.equalsIgnoreCase("SFD")) {
          tasks.set(6, task);
        } else if (taskAbbrev.equalsIgnoreCase("TAPE")) {
          tasks.set(8, task);
        } else if (taskAbbrev.equalsIgnoreCase("PFS")) {
          tasks.set(7, task);
        } else if (taskAbbrev.equalsIgnoreCase("MQD")) {
          tasks.set(9, task);
        } else if (taskAbbrev.equalsIgnoreCase("PAP")) {
          tasks.set(10, task);
        } else if (taskAbbrev.equalsIgnoreCase("PSD")) {
          tasks.set(11, task);
        } else if (taskAbbrev.equalsIgnoreCase("SMR")) {
          tasks.set(0, task);
        } else if (taskAbbrev.equalsIgnoreCase("P&L")) {
          tasks.set(12, task);
        } else if (taskAbbrev.equalsIgnoreCase("PO")) {
          tasks.set(13, task);
        } 
      } 
    } 
    return tasks;
  }
  
  public static Vector getTasksForUmeCustomProdScheduleReport(Selection sel) {
    Vector tasks = new Vector();
    int numTasks = 9;
    for (int i = 0; i < numTasks; i++)
      tasks.addElement(null); 
    Schedule schedule = ScheduleManager.getInstance().getSchedule(sel.getSelectionID());
    Vector allTasks = new Vector();
    if (schedule != null) {
      allTasks = schedule.getTasks();
      if (allTasks == null)
        allTasks = new Vector(); 
    } 
    for (int i = 0; i < allTasks.size(); i++) {
      ScheduledTask task = (ScheduledTask)allTasks.elementAt(i);
      if (task != null) {
        String taskName = (task.getName() != null) ? 
          task.getName().toUpperCase() : "";
        if (taskName.indexOf("CLEARANCE DEADLINE") != -1) {
          tasks.set(0, task);
        } else if (taskName.indexOf("ARTWORK SPECS/LOGOS TO CLIENT") != -1) {
          tasks.set(1, task);
        } else if (taskName.indexOf("LABEL COPY TO CLIENT") != -1) {
          tasks.set(2, task);
        } else if (taskName.indexOf("ACCOUNT NUMBER") != -1) {
          tasks.set(3, task);
        } else if (taskName.indexOf("RECEIVE CLIENT MASTER") != -1) {
          tasks.set(4, task);
        } else if (taskName.indexOf("PURCHASE ORDER FROM CLIENT") != -1) {
          tasks.set(5, task);
        } else if (taskName.indexOf("GRAPHICS") != -1 && taskName.indexOf("FILM") != -1) {
          tasks.set(6, task);
        } else if (taskName.indexOf("PLACE INITIAL ORDER") != -1) {
          tasks.set(7, task);
        } else if (taskName.indexOf("PRINT") != -1 && taskName.indexOf("RECEIVED") != -1 && 
          taskName.indexOf("PLANT") != -1) {
          tasks.set(8, task);
        } 
      } 
    } 
    return tasks;
  }
  
  public static Hashtable groupSelectionsByTypeConfigAndStreetDate(Vector selections) {
    Hashtable groupSelectionsByTypeConfigAndStreetDate = new Hashtable();
    if (selections == null)
      return groupSelectionsByTypeConfigAndStreetDate; 
    for (int i = 0; i < selections.size(); i++) {
      Selection sel = (Selection)selections.elementAt(i);
      if (sel != null) {
        sel = SelectionManager.getInstance().getSelectionAndSchedule(sel.getSelectionID());
        String typeConfigString = "";
        String monthString = "";
        String dayString = "";
        String typeString = (sel.getReleaseType() != null) ? sel.getReleaseType().getName() : "";
        String configString = (sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() != null) ? sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() : "";
        if (typeString.startsWith("Commercial")) {
          if (configString.equals("8TRK") || 
            configString.equals("CDROM") || 
            configString.equals("CDVID") || 
            configString.equals("DCCASS") || 
            configString.equals("CASSEP") || 
            configString.equals("CDEP") || 
            configString.equals("ECDEP") || 
            configString.equals("ALBUM") || 
            configString.equals("CASS") || 
            configString.equals("CD") || 
            configString.equals("ECD") || 
            configString.equals("LASER") || 
            configString.equals("MIXED") || 
            configString.equals("DVDVID") || 
            configString.equals("VIDEO") || 
            configString.equals("DVDAUD") || 
            configString.equalsIgnoreCase("DUALDISC")) {
            typeConfigString = "Commercial Full Length";
          } else {
            typeConfigString = "Commercial Single";
          } 
        } else if (typeString.startsWith("Pro")) {
          typeConfigString = "Promos";
        } 
        if (sel.getStreetDate() != null) {
          SimpleDateFormat formatter = new SimpleDateFormat("MM/yyyy");
          monthString = formatter.format(sel.getStreetDate().getTime());
          dayString = getFormatedDate(sel.getStreetDate());
        } 
        String statusString = "";
        SelectionStatus status = sel.getSelectionStatus();
        if (status != null)
          statusString = (status.getName() == null) ? "" : status.getName(); 
        if (statusString.equalsIgnoreCase("TBS")) {
          monthString = "13";
        } else if (statusString.equalsIgnoreCase("In The Works")) {
          monthString = "26";
        } else if (monthString.length() < 1) {
          monthString = "52";
        } 
        Hashtable typeConfigSubTable = (Hashtable)groupSelectionsByTypeConfigAndStreetDate.get(typeConfigString);
        if (typeConfigSubTable == null) {
          typeConfigSubTable = new Hashtable();
          groupSelectionsByTypeConfigAndStreetDate.put(typeConfigString, typeConfigSubTable);
        } 
        Hashtable monthsTable = (Hashtable)typeConfigSubTable.get(monthString);
        if (monthsTable == null) {
          monthsTable = new Hashtable();
          typeConfigSubTable.put(monthString, monthsTable);
        } 
        Vector selectionsForDates = (Vector)monthsTable.get(dayString);
        if (selectionsForDates == null) {
          selectionsForDates = new Vector();
          monthsTable.put(dayString, selectionsForDates);
        } 
        selectionsForDates.addElement(sel);
      } 
    } 
    return groupSelectionsByTypeConfigAndStreetDate;
  }
  
  public static Hashtable groupSelectionsforIDJByConfigAndStreetDate(Vector selections) {
    Hashtable groupSelectionsByTypeConfigAndStreetDate = new Hashtable();
    if (selections == null)
      return groupSelectionsByTypeConfigAndStreetDate; 
    for (int i = 0; i < selections.size(); i++) {
      Selection sel = (Selection)selections.elementAt(i);
      if (sel != null) {
        sel = SelectionManager.getInstance().getSelectionAndSchedule(sel.getSelectionID());
        String typeConfigString = "";
        String monthString = "";
        String dayString = "";
        String typeString = (sel.getReleaseType() != null) ? sel.getReleaseType().getName() : "";
        String configString = (sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() != null) ? sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() : "";
        String realConfig = (sel.getSelectionConfig().getSelectionConfigurationAbbreviation() != null) ? sel.getSelectionConfig().getSelectionConfigurationAbbreviation() : "";
        if (typeString.startsWith("Commercial")) {
          if (configString.equals("8TRK") || 
            configString.equals("CDROM") || 
            configString.equals("CDVID") || 
            configString.equals("DCCASS") || 
            configString.equals("CASSEP") || 
            configString.equals("CDEP") || 
            configString.equals("ECDEP") || 
            configString.equals("ALBUM") || 
            configString.equals("CASS") || 
            configString.equals("CD") || 
            configString.equals("ECD") || 
            configString.equals("LASER") || 
            configString.equals("MIXED") || 
            configString.equals("DVDVID") || 
            configString.equals("VIDEO") || 
            configString.equals("DVDAUD") || 
            configString.equals("CDADVD") || 
            configString.equals("CDADDV") || 
            realConfig.equals("SACD") || 
            realConfig.equals("DP") || 
            realConfig.equalsIgnoreCase("DUALDISC") || 
            realConfig.equals("UMD") || 
            realConfig.equals("UMDFL")) {
            typeConfigString = "Commercial Full Length";
          } else {
            typeConfigString = "Commercial Single";
          } 
        } else if (typeString.startsWith("Pro")) {
          typeConfigString = "Promos";
        } 
        if (sel.getStreetDate() != null) {
          SimpleDateFormat formatter = new SimpleDateFormat("MM/yyyy");
          monthString = formatter.format(sel.getStreetDate().getTime());
          dayString = getFormatedDate(sel.getStreetDate());
        } 
        String statusString = "";
        SelectionStatus status = sel.getSelectionStatus();
        if (status != null)
          statusString = (status.getName() == null) ? "" : status.getName(); 
        if (statusString.equalsIgnoreCase("TBS")) {
          monthString = "13";
        } else if (statusString.equalsIgnoreCase("In The Works")) {
          monthString = "26";
        } else if (monthString.length() < 1) {
          monthString = "52";
        } 
        Hashtable typeConfigSubTable = (Hashtable)groupSelectionsByTypeConfigAndStreetDate.get(typeConfigString);
        if (typeConfigSubTable == null) {
          typeConfigSubTable = new Hashtable();
          groupSelectionsByTypeConfigAndStreetDate.put(typeConfigString, typeConfigSubTable);
        } 
        Hashtable monthsTable = (Hashtable)typeConfigSubTable.get(monthString);
        if (monthsTable == null) {
          monthsTable = new Hashtable();
          typeConfigSubTable.put(monthString, monthsTable);
        } 
        Vector selectionsForDates = (Vector)monthsTable.get(dayString);
        if (selectionsForDates == null) {
          selectionsForDates = new Vector();
          monthsTable.put(dayString, selectionsForDates);
        } 
        selectionsForDates.addElement(sel);
      } 
    } 
    return groupSelectionsByTypeConfigAndStreetDate;
  }
  
  public static Hashtable groupSelectionsByTypeAndStreetDate(Vector selections) {
    Hashtable groupSelectionsByConfigAndStreetDate = new Hashtable();
    if (selections == null)
      return groupSelectionsByConfigAndStreetDate; 
    for (int i = 0; i < selections.size(); i++) {
      Selection sel = (Selection)selections.elementAt(i);
      if (sel != null) {
        String typeString = "", monthString = "";
        ReleaseType type = sel.getReleaseType();
        if (type != null)
          typeString = (sel.getReleaseType() != null) ? sel.getReleaseType().getName() : ""; 
        if (sel.getStreetDate() != null) {
          SimpleDateFormat formatter = new SimpleDateFormat("MM/yyyy");
          monthString = formatter.format(sel.getStreetDate().getTime());
        } 
        Hashtable typeSubTable = (Hashtable)groupSelectionsByConfigAndStreetDate.get(typeString);
        if (typeSubTable == null) {
          typeSubTable = new Hashtable();
          groupSelectionsByConfigAndStreetDate.put(typeString, typeSubTable);
        } 
        SelectionStatus status = sel.getSelectionStatus();
        String statusString = "";
        if (status != null)
          statusString = (status.getName() == null) ? "" : status.getName(); 
        if (statusString.equalsIgnoreCase("TBS")) {
          monthString = "13";
        } else if (statusString.equalsIgnoreCase("In The Works")) {
          monthString = "26";
        } else if (monthString.length() < 1) {
          monthString = "52";
        } 
        Vector selectionsForDate = (Vector)typeSubTable.get(monthString);
        if (selectionsForDate == null) {
          selectionsForDate = new Vector();
          typeSubTable.put(monthString, selectionsForDate);
        } 
        selectionsForDate.addElement(sel);
      } 
    } 
    return groupSelectionsByConfigAndStreetDate;
  }
  
  public static Hashtable groupSelectionsByDigitalTypeAndStreetDate(Vector selections) {
    Hashtable groupSelectionsByTypeAndStreetDate = new Hashtable();
    if (selections == null)
      return groupSelectionsByTypeAndStreetDate; 
    for (int i = 0; i < selections.size(); i++) {
      Selection sel = (Selection)selections.elementAt(i);
      if (sel != null) {
        String typeString = "", monthString = "";
        String subConfigString = (sel.getSelectionSubConfig().getSelectionSubConfigurationName() != null) ? sel.getSelectionSubConfig().getSelectionSubConfigurationName() : "";
        if (sel.getDigitalRlsDate() != null) {
          SimpleDateFormat formatter = new SimpleDateFormat("MM/yyyy");
          monthString = formatter.format(sel.getDigitalRlsDate().getTime());
        } 
        Hashtable typeSubTable = (Hashtable)groupSelectionsByTypeAndStreetDate.get(subConfigString);
        if (typeSubTable == null) {
          typeSubTable = new Hashtable();
          groupSelectionsByTypeAndStreetDate.put(subConfigString, typeSubTable);
        } 
        SelectionStatus status = sel.getSelectionStatus();
        String statusString = "";
        if (status != null)
          statusString = (status.getName() == null) ? "" : status.getName(); 
        if (statusString.equalsIgnoreCase("TBS")) {
          monthString = "13";
        } else if (statusString.equalsIgnoreCase("In The Works")) {
          monthString = "26";
        } else if (monthString.length() < 1) {
          monthString = "52";
        } 
        Vector selectionsForDate = (Vector)typeSubTable.get(monthString);
        if (selectionsForDate == null) {
          selectionsForDate = new Vector();
          typeSubTable.put(monthString, selectionsForDate);
        } 
        selectionsForDate.addElement(sel);
      } 
    } 
    return groupSelectionsByTypeAndStreetDate;
  }
  
  public static Hashtable groupSelectionsByMonthAndDayAndDivision(Vector selections) {
    Hashtable groupSelectionsByMonthAndDayAndDivision = new Hashtable();
    if (selections == null)
      return groupSelectionsByMonthAndDayAndDivision; 
    for (int i = 0; i < selections.size(); i++) {
      Selection sel = (Selection)selections.elementAt(i);
      if (sel != null) {
        String divisionString = "", dateString = "", monthString = "";
        String type = SelectionManager.getLookupObjectValue(sel.getReleaseType());
        Division selDivision = sel.getDivision();
        if (selDivision != null)
          divisionString = (selDivision != null) ? selDivision.getName() : ""; 
        if (sel.getStreetDate() != null) {
          SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
          dateString = formatter.format(sel.getStreetDate().getTime());
          SimpleDateFormat formatter2 = new SimpleDateFormat("MM/yyyy");
          monthString = formatter2.format(sel.getStreetDate().getTime());
        } 
        SelectionStatus status = sel.getSelectionStatus();
        String statusString = "";
        if (status != null)
          statusString = (status.getName() == null) ? "" : status.getName(); 
        if (statusString.equalsIgnoreCase("TBS")) {
          monthString = "13";
        } else if (statusString.equalsIgnoreCase("In The Works")) {
          monthString = "26";
        } else if (monthString.length() < 1) {
          monthString = "52";
        } 
        Hashtable monthSubTable = (Hashtable)groupSelectionsByMonthAndDayAndDivision.get(monthString);
        if (monthSubTable == null) {
          monthSubTable = new Hashtable();
          groupSelectionsByMonthAndDayAndDivision.put(monthString, monthSubTable);
        } 
        Hashtable dateSubTable = (Hashtable)monthSubTable.get(dateString);
        if (dateSubTable == null) {
          dateSubTable = new Hashtable();
          monthSubTable.put(dateString, dateSubTable);
        } 
        Vector selectionsForDate = (Vector)dateSubTable.get(divisionString);
        if (selectionsForDate == null) {
          selectionsForDate = new Vector();
          dateSubTable.put(divisionString, selectionsForDate);
        } 
        selectionsForDate.addElement(sel);
      } 
    } 
    return groupSelectionsByMonthAndDayAndDivision;
  }
  
  public static Hashtable groupSelectionsByArtistAndMonth(Vector selections) {
    Hashtable groupSelectionsByArtistAndMonth = new Hashtable();
    if (selections == null)
      return groupSelectionsByArtistAndMonth; 
    for (int i = 0; i < selections.size(); i++) {
      Selection sel = (Selection)selections.elementAt(i);
      if (sel != null) {
        String artistString = "", monthString = "";
        if (sel.getStreetDate() != null) {
          SimpleDateFormat formatter2 = new SimpleDateFormat("MM/yyyy");
          monthString = formatter2.format(sel.getStreetDate().getTime());
        } 
        artistString = sel.getArtist();
        SelectionStatus status = sel.getSelectionStatus();
        String statusString = "";
        if (status != null)
          statusString = (status.getName() == null) ? "" : status.getName(); 
        if (statusString.equalsIgnoreCase("TBS")) {
          monthString = "13";
        } else if (statusString.equalsIgnoreCase("In The Works")) {
          monthString = "26";
        } else if (monthString.length() < 1) {
          monthString = "52";
        } 
        String keyString = String.valueOf(sel.getSelectionID());
        Hashtable artistSubTable = (Hashtable)groupSelectionsByArtistAndMonth.get(artistString);
        if (artistSubTable == null) {
          artistSubTable = new Hashtable();
          groupSelectionsByArtistAndMonth.put(artistString, artistSubTable);
        } 
        Vector selectionsForMonth = (Vector)artistSubTable.get(monthString);
        if (selectionsForMonth == null) {
          selectionsForMonth = new Vector();
          artistSubTable.put(monthString, selectionsForMonth);
        } 
        selectionsForMonth.addElement(sel);
      } 
    } 
    return groupSelectionsByArtistAndMonth;
  }
  
  public static Hashtable groupSelectionsForMcaByTypeConfigAndStreetDate(Vector selections) {
    Hashtable groupSelectionsByTypeConfigAndStreetDate = new Hashtable();
    if (selections == null)
      return groupSelectionsByTypeConfigAndStreetDate; 
    for (int i = 0; i < selections.size(); i++) {
      Selection sel = (Selection)selections.elementAt(i);
      if (sel != null) {
        sel = SelectionManager.getInstance().getSelectionAndSchedule(sel.getSelectionID());
        String monthString = "";
        String dayString = "";
        if (sel.getStreetDate() != null) {
          SimpleDateFormat formatter = new SimpleDateFormat("MM/yyyy");
          monthString = formatter.format(sel.getStreetDate().getTime());
          dayString = getFormatedDate(sel.getStreetDate());
        } 
        Vector selectionsForDates = (Vector)groupSelectionsByTypeConfigAndStreetDate.get(monthString);
        if (selectionsForDates == null) {
          selectionsForDates = new Vector();
          groupSelectionsByTypeConfigAndStreetDate.put(monthString, selectionsForDates);
        } 
        selectionsForDates.addElement(sel);
      } 
    } 
    return groupSelectionsByTypeConfigAndStreetDate;
  }
  
  public static Hashtable groupSelectionsForMcaProductionByTypeConfigAndStreetDate(Vector selections) {
    Hashtable groupSelectionsByTypeConfigAndStreetDate = new Hashtable();
    if (selections == null)
      return groupSelectionsByTypeConfigAndStreetDate; 
    for (int i = 0; i < selections.size(); i++) {
      Selection sel = (Selection)selections.elementAt(i);
      if (sel != null) {
        sel = SelectionManager.getInstance().getSelectionAndSchedule(sel.getSelectionID());
        String typeConfigString = "";
        String monthString = "";
        String dayString = "";
        String typeString = (sel.getReleaseType() != null) ? sel.getReleaseType().getName() : "";
        String categoryString = (sel.getProductCategory() != null) ? sel.getProductCategory().getName() : "";
        String configString = (sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() != null) ? sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() : "";
        String realConfig = (sel.getSelectionConfig().getSelectionConfigurationAbbreviation() != null) ? sel.getSelectionConfig().getSelectionConfigurationAbbreviation() : "";
        if (categoryString.equals("Advances")) {
          typeConfigString = "Frontline";
        } else if (typeString.startsWith("Commercial")) {
          if (configString.equals("8TRK") || 
            realConfig.equals("DVV") || 
            configString.equals("ALBUM") || 
            configString.equals("CASS") || 
            configString.equals("CD") || 
            configString.equals("ECD") || 
            configString.equals("CDROM") || 
            configString.equals("CDVID") || 
            configString.equals("DCCASS") || 
            configString.equals("LASER") || 
            configString.equals("MIXED") || 
            configString.equals("VIDEO") || 
            configString.equals("CDADVD") || 
            configString.equals("DP") || 
            realConfig.equals("DVA") || 
            realConfig.equals("SACD") || 
            realConfig.equalsIgnoreCase("DUALDISC")) {
            typeConfigString = "Frontline";
          } else if (configString.equals("VNYL7") || 
            configString.equals("VNYL10") || 
            configString.equals("VNYL12") || 
            configString.equals("ESNGL") || 
            configString.equals("CASSMX") || 
            configString.equals("CDMX") || 
            configString.equals("ECDMX") || 
            configString.equals("CASSGL") || 
            configString.equals("CDSGL") || 
            configString.equals("DVDASL") || 
            configString.equals("DVDVSL") || 
            configString.equals("ECDSGL")) {
            typeConfigString = "Singles";
          } 
        } else if (typeString.startsWith("Pro")) {
          if (configString.equals("8TRK") || 
            configString.equals("CDROM") || 
            configString.equals("CDVID") || 
            configString.equals("DCCASS") || 
            configString.equals("CASSEP") || 
            configString.equals("CDEP") || 
            configString.equals("CDMX") || 
            configString.equals("ECDEP") || 
            configString.equals("ALBUM") || 
            configString.equals("CASS") || 
            configString.equals("CD") || 
            configString.equals("ECD") || 
            configString.equals("LASER") || 
            configString.equals("MIXED") || 
            configString.equals("VIDEO") || 
            configString.equals("VNYL12") || 
            configString.equals("VNYL7") || 
            configString.equals("VNYL10") || 
            configString.equals("ESNGL") || 
            configString.equals("CASSMX") || 
            configString.equals("CASSGL") || 
            configString.equals("CDSGL") || 
            configString.equals("ECDSGL")) {
            typeConfigString = "Singles";
          } else if (configString.equals("DVDASL") || 
            configString.equals("DVDAUD") || 
            configString.equals("DVDVID") || 
            configString.equals("DVDVSL") || 
            realConfig.equalsIgnoreCase("DUALDISC")) {
            typeConfigString = "Frontline";
          } 
        } 
        if (sel.getStreetDate() != null) {
          SimpleDateFormat formatter = new SimpleDateFormat("MM/yyyy");
          monthString = formatter.format(sel.getStreetDate().getTime());
          dayString = getFormatedDate(sel.getStreetDate());
        } 
        String statusString = "";
        SelectionStatus status = sel.getSelectionStatus();
        if (status != null)
          statusString = (status.getName() == null) ? "" : status.getName(); 
        if (statusString.equalsIgnoreCase("TBS")) {
          monthString = "13";
        } else if (statusString.equalsIgnoreCase("In The Works")) {
          monthString = "26";
        } else if (monthString.length() < 1) {
          monthString = "52";
        } 
        Hashtable typeConfigSubTable = (Hashtable)groupSelectionsByTypeConfigAndStreetDate.get(typeConfigString);
        if (typeConfigSubTable == null) {
          typeConfigSubTable = new Hashtable();
          groupSelectionsByTypeConfigAndStreetDate.put(typeConfigString, typeConfigSubTable);
        } 
        Hashtable monthsTable = (Hashtable)typeConfigSubTable.get(monthString);
        if (monthsTable == null) {
          monthsTable = new Hashtable();
          typeConfigSubTable.put(monthString, monthsTable);
        } 
        Vector selectionsForDates = (Vector)monthsTable.get(dayString);
        if (selectionsForDates == null) {
          selectionsForDates = new Vector();
          monthsTable.put(dayString, selectionsForDates);
        } 
        selectionsForDates.addElement(sel);
      } 
    } 
    return groupSelectionsByTypeConfigAndStreetDate;
  }
  
  public static Hashtable groupSelectionsBySubConfigAndStreetDate(Vector selections) {
    Hashtable groupSelectionsBySubConfigAndStreetDate = new Hashtable();
    if (selections == null)
      return groupSelectionsBySubConfigAndStreetDate; 
    for (int i = 0; i < selections.size(); i++) {
      Selection sel = (Selection)selections.elementAt(i);
      if (sel != null) {
        sel = SelectionManager.getInstance().getSelectionAndSchedule(sel.getSelectionID());
        String configString = "", dateString = "";
        configString = (sel.getSelectionSubConfig() != null) ? sel.getSelectionSubConfig().getSelectionSubConfigurationName() : "";
        SelectionStatus status = sel.getSelectionStatus();
        String statusString = "";
        if (status != null)
          statusString = (status.getName() == null) ? "" : status.getName(); 
        if (statusString.equalsIgnoreCase("TBS")) {
          dateString = "TBS " + ((sel.getStreetDate() != null) ? getFormatedDate(sel.getStreetDate()) : "");
        } else if (statusString.equalsIgnoreCase("In The Works")) {
          dateString = "ITW " + ((sel.getStreetDate() != null) ? getFormatedDate(sel.getStreetDate()) : "");
        } else {
          dateString = (sel.getStreetDate() != null) ? getFormatedDate(sel.getStreetDate()) : "No street date";
        } 
        if (configString != null)
          if (configString.indexOf("Single") > 0) {
            configString = "Singles";
          } else {
            configString = "Full Length";
          }  
        Hashtable configSubTable = (Hashtable)groupSelectionsBySubConfigAndStreetDate.get(configString);
        if (configSubTable == null) {
          configSubTable = new Hashtable();
          groupSelectionsBySubConfigAndStreetDate.put(configString, configSubTable);
        } 
        Vector selectionsForDate = (Vector)configSubTable.get(dateString);
        if (selectionsForDate == null) {
          selectionsForDate = new Vector();
          configSubTable.put(dateString, selectionsForDate);
        } 
        selectionsForDate.addElement(sel);
      } 
    } 
    return groupSelectionsBySubConfigAndStreetDate;
  }
  
  public static void setSelectionSorting(Vector selections, int sortBy) {
    if (selections == null)
      return; 
    for (int i = 0; i < selections.size(); i++)
      ((Selection)selections.elementAt(i)).setSortBy(sortBy); 
  }
  
  public static Vector getPrefixCodes(int companyId) {
    Vector prefixCodes = new Vector();
    Vector prefixVector = Cache.getPrefixCodes();
    for (int j = 0; j < prefixVector.size(); j++) {
      PrefixCode prefixCode = (PrefixCode)prefixVector.elementAt(j);
      if (prefixCode.getPrefixCodeSubValue() == companyId)
        prefixCodes.addElement(prefixCode); 
    } 
    return prefixCodes;
  }
  
  public static int countTasksWithComments(Vector tasks) {
    int count = 0;
    if (tasks == null)
      return count; 
    for (int i = 0; i < tasks.size(); i++) {
      ScheduledTask task = (ScheduledTask)tasks.get(i);
      String taskComments = (task.getComments() != null) ? task.getComments().trim() : "";
      if (!taskComments.equals("") && !taskComments.equalsIgnoreCase("null"))
        count++; 
    } 
    return count;
  }
  
  public static String formatQuantityWithCommas(String quantity) {
    if (quantity == null || quantity.length() < 4)
      return quantity; 
    quantity = removeCommas(quantity);
    int commas = quantity.length() / 3;
    int j = 0;
    for (int i = 1; i <= commas; i++) {
      quantity = String.valueOf(quantity.substring(0, quantity.length() - 3 * i + j)) + 
        "," + 
        quantity.substring(quantity.length() - 3 * i + j, quantity.length());
      j++;
    } 
    if (quantity.charAt(0) == ',')
      quantity = quantity.substring(1, quantity.length()); 
    return quantity;
  }
  
  public static String removeCommas(String value) {
    int index = -1;
    while (true) {
      index = value.indexOf(",");
      if (index > 0) {
        value = String.valueOf(value.substring(0, index)) + value.substring(index + 1, value.length());
        continue;
      } 
      break;
    } 
    return value;
  }
  
  public static int lineCount(String[] strings, int[] maxChars) {
    int lineCount = 0;
    int stringLength = 0;
    for (int i = 0; i < strings.length; i++) {
      int lines = 0;
      String theString = strings[i];
      int lineLength = maxChars[i];
      stringLength = theString.length();
      if (stringLength > 0)
        lines = stringLength / lineLength; 
      if (lines > lineCount)
        lineCount = lines; 
    } 
    return lineCount;
  }
  
  public static int lineCount(String s1, String s2) {
    int lineCount = 0;
    int stringLength = 0;
    int maxChars = 21;
    stringLength = s1.length();
    if (s2.length() > stringLength)
      stringLength = s2.length(); 
    if (stringLength > 0)
      lineCount = stringLength / 21; 
    return lineCount;
  }
  
  public static String urlEncode(String s1) {
    if (s1 != null) {
      String newString = "";
      for (int i = 0; i < s1.length(); i++) {
        if (s1.substring(i, i + 1).equalsIgnoreCase("'")) {
          newString = String.valueOf(newString) + "\\'";
        } else if (s1.substring(i, i + 1).equalsIgnoreCase("\"")) {
          newString = String.valueOf(newString) + "\\\"";
        } else {
          newString = String.valueOf(newString) + s1.substring(i, i + 1);
        } 
      } 
      return newString;
    } 
    return s1;
  }
  
  public static String stringFormatFont(String strings, int colWidth, Font fontUsed) {
    int stringLength = 0;
    int charcount = 0;
    String theString = strings;
    String theNewString = "";
    String theTempCharArray = "";
    char lcSpaceCheck = '\n';
    char lcSpace = ' ';
    int canFit = 0;
    int i = 0;
    int ispaceLocation = 0;
    Frame frame = null;
    frame = new Frame();
    frame.addNotify();
    Image image = frame.createImage(640, 480);
    Graphics g = image.getGraphics();
    Graphics2D g2 = (Graphics2D)g;
    FontMetrics fm = g2.getFontMetrics(fontUsed);
    stringLength = theString.length();
    for (int x = 0; x < stringLength; x++) {
      i = 0;
      if (theString.charAt(x) == '\n' || canFit >= colWidth) {
        if (theString.charAt(x) == '\n') {
          theNewString = String.valueOf(theNewString.trim()) + theString.charAt(x);
        } else if (theString.charAt(x) == ' ') {
          theNewString = String.valueOf(theNewString.trim()) + '\n';
        } else {
          theNewString = String.valueOf(theNewString) + theString.charAt(x);
          charcount++;
          int liCurrentLength = theNewString.length() - 1;
          i = charcount;
          do {
            ispaceLocation = liCurrentLength - charcount - i;
            lcSpaceCheck = theNewString.charAt(ispaceLocation);
            --i;
          } while (i > 1 && lcSpaceCheck != ' ');
          if (i <= 1) {
            theNewString = String.valueOf(theNewString) + '\n';
          } else {
            theNewString = String.valueOf(theNewString.substring(0, ispaceLocation)) + 
              '\n' + theNewString.substring(ispaceLocation + 1, liCurrentLength + 1);
          } 
        } 
        charcount = 0;
        canFit = 0;
      } else {
        theNewString = String.valueOf(theNewString) + theString.charAt(x);
        if (charcount > 3) {
          theTempCharArray = theNewString.substring(theNewString.length() - charcount + 1, theNewString.length() - 1);
          canFit = fm.stringWidth(theTempCharArray);
          canFit = (int)(canFit * 2.4D);
        } 
        charcount++;
      } 
    } 
    if (g2 != null)
      g2.dispose(); 
    if (g != null)
      g.dispose(); 
    return theNewString;
  }
  
  public static int lineCountWCR(String[] strings, int[] maxChars) {
    int lineCount = 0;
    int stringLength = 0;
    int charcount = 0;
    for (int i = 0; i < strings.length; i++) {
      int lines = 0;
      String theString = strings[i];
      int lineLength = maxChars[i];
      stringLength = theString.length();
      for (int x = 0; x < stringLength; x++) {
        if ((theString.charAt(x) == '\n' && stringLength - 1 != x) || charcount >= lineLength) {
          charcount = 0;
          lines++;
        } else {
          charcount++;
        } 
      } 
      if (lines > lineCount)
        lineCount = lines; 
    } 
    return lineCount;
  }
  
  public static String stringFormat(String strings, int maxChars) {
    int lineCount = 0;
    int stringLength = 0;
    int charcount = 0;
    int lines = 0;
    int lineLength = maxChars;
    String theString = strings;
    String theNewString = "";
    char lcSpaceCheck = '\n';
    int i = 0;
    int ispaceLocation = 0;
    stringLength = theString.length();
    for (int x = 0; x < stringLength; x++) {
      i = 0;
      if (theString.charAt(x) == '\n' || charcount >= maxChars) {
        if (theString.charAt(x) == '\n') {
          theNewString = String.valueOf(theNewString) + theString.charAt(x);
        } else if (theString.charAt(x) == ' ') {
          theNewString = String.valueOf(theNewString) + '\n';
        } else {
          theNewString = String.valueOf(theNewString) + theString.charAt(x);
          charcount++;
          int liCurrentLength = theNewString.length() - 1;
          i = charcount;
          while (i > 1 && lcSpaceCheck != ' ') {
            ispaceLocation = liCurrentLength - charcount - i;
            lcSpaceCheck = theNewString.charAt(ispaceLocation);
            i--;
          } 
          lcSpaceCheck = '@';
          if (i <= 1) {
            theNewString = String.valueOf(theNewString) + '\n';
          } else {
            theNewString = String.valueOf(theNewString.substring(0, ispaceLocation)) + '\n' + theNewString.substring(ispaceLocation + 1, liCurrentLength + 1);
          } 
        } 
        charcount = 0;
      } else {
        theNewString = String.valueOf(theNewString) + theString.charAt(x);
        charcount++;
      } 
    } 
    return theNewString;
  }
  
  public static void updateFamilyForAllSelections() {
    query = "SELECT * from vi_Release_Header with (nolock)";
    JdbcConnector connector = createConnector(query);
    connector.runQuery();
    while (connector.more()) {
      try {
        Company company = (Company)getStructureObject(connector.getIntegerField("company_id"));
        String familyID = String.valueOf(company.getParentID());
        String queryUpdate = "UPDATE vi_Release_Header set family_id = " + familyID + " where release_id = " + connector.getIntegerField("release_id");
        JdbcConnector connectorUpdate = createConnector(queryUpdate);
        connectorUpdate.runQuery();
        connectorUpdate.close();
      } catch (Exception exception) {}
      connector.next();
    } 
    connector.close();
  }
  
  public static Vector getUserEnvironments(int userID) { return getEnvironmentsFromCompanies(getUserCompanies(userID)); }
  
  public static Vector sortUserEnvironmentsByFamily(Vector environments) {
    Vector userEnvironmentsByFamily = new Vector();
    Object[] environmentArray = environments.toArray();
    Arrays.sort(environmentArray, new CorpStructNameComparator());
    Arrays.sort(environmentArray, new CorpStructParentNameComparator());
    for (int j = 0; j < environmentArray.length; j++)
      userEnvironmentsByFamily.add(environmentArray[j]); 
    return userEnvironmentsByFamily;
  }
  
  public static Vector buildAssignedEnvironments(Vector environments) {
    for (int i = 0; i < environments.size(); i++) {
      Environment env = (Environment)environments.elementAt(i);
      if (env != null) {
        Family family = env.getParentFamily();
        if (family != null) {
          Vector famEnvs = family.getEnvironments();
          if (famEnvs != null)
            for (int x = 0; x < famEnvs.size(); x++) {
              Environment famEnv = (Environment)famEnvs.elementAt(x);
              if (famEnv != null)
                if (!environments.contains(famEnv))
                  environments.add(famEnv);  
            }  
        } 
      } 
    } 
    return sortUserEnvironmentsByFamily(environments);
  }
  
  public static Vector getEnvironmentsFromCompanies(Vector companyVector) {
    Vector environmentVector = new Vector();
    for (int i = 0; i < companyVector.size(); i++) {
      Company currentCompany = (Company)companyVector.elementAt(i);
      Environment currentEnvironment = currentCompany.getParentEnvironment();
      if (currentEnvironment != null)
        if (!environmentVector.contains(currentEnvironment))
          environmentVector.add(currentEnvironment);  
    } 
    Object[] EnvironmentArray = environmentVector.toArray();
    Arrays.sort(EnvironmentArray, new CorpStructNameComparator());
    Vector sortedEnvironmentVector = new Vector();
    for (int j = 0; j < EnvironmentArray.length; j++)
      sortedEnvironmentVector.add(EnvironmentArray[j]); 
    return sortedEnvironmentVector;
  }
  
  public static Vector getUserEnvironmentsExcludeUmlEnterprise(int userID) {
    String envQuery = "sp_get_User_Environments_Exclude " + userID;
    Vector precache = new Vector();
    Environment env = null;
    JdbcConnector envConnector = createConnector(envQuery);
    envConnector.runQuery();
    while (envConnector.more()) {
      env = (Environment)getStructureObject(envConnector.getIntegerField("structure_id"));
      if (env != null)
        precache.addElement(env); 
      env = null;
      envConnector.next();
    } 
    return precache;
  }
  
  public static Environment getScreenUserEnvironment(Context context) {
    Environment env = null;
    int envID = -1;
    Notepad notepad = getNotepadFromSession(21, context);
    if (context.getRequestValue("environment-id") != null) {
      try {
        envID = Integer.parseInt(context.getRequestValue("environment-id"));
        env = (Environment)getStructureObject(envID);
        context.putSessionValue("UserEnvironment", env);
        notepad.setSelected(env);
      } catch (ClassCastException classCastException) {}
    } else if ((Environment)notepad.getSelected() != null) {
      try {
        Environment notepadEnvironment = (Environment)notepad.getSelected();
        envID = notepadEnvironment.getStructureID();
        env = (Environment)getStructureObject(envID);
        context.putSessionValue("UserEnvironment", env);
      } catch (ClassCastException classCastException) {}
    } else if (notepad.getAllContents() != null && notepad.getAllContents().size() > 0) {
      env = (Environment)notepad.getAllContents().get(0);
      context.putSessionValue("UserEnvironment", env);
      notepad.setSelected(env);
    } 
    return env;
  }
  
  public static Environment getScreenEnvironment(Context context) {
    Environment env = null;
    int envID = -1;
    Notepad notepad = getNotepadFromSession(21, context);
    if (context.getRequestValue("environment-id") != null) {
      envID = Integer.parseInt(context.getRequestValue("environment-id"));
      env = EnvironmentManager.getInstance().getEnvironment(envID);
      context.putSessionValue("Environment", env);
      notepad.setSelected(env);
    } else if ((Environment)notepad.getSelected() != null) {
      env = (Environment)notepad.getSelected();
      env = (Environment)getStructureObject(env.getStructureID());
    } else if (notepad.getAllContents() != null && notepad.getAllContents().size() > 0) {
      env = (Environment)notepad.getAllContents().get(0);
      notepad.setSelected(env);
      context.putSessionValue("Environment", env);
    } 
    return env;
  }
  
  public static CompanyAcl getScreenPermissions(Environment env, User user) {
    Hashtable envCompanies = new Hashtable();
    if (env == null)
      return null; 
    Vector companies = env.getCompanies();
    if (companies == null)
      return null; 
    for (int i = 0; i < companies.size(); i++) {
      Company company = (Company)companies.get(i);
      if (company != null)
        envCompanies.put(Integer.toString(company.getStructureID()), company.getName()); 
    } 
    CompanyAcl companyAcl = null;
    Acl acl = user.getAcl();
    if (acl == null)
      return null; 
    Vector companyAcls = acl.getCompanyAcl();
    if (companyAcls == null)
      return null; 
    int envId = -1;
    if (env != null) {
      envId = env.getStructureID();
      for (int i = 0; i < companyAcls.size(); i++) {
        companyAcl = (CompanyAcl)companyAcls.get(i);
        if (companyAcl != null)
          if (envCompanies.containsKey(Integer.toString(companyAcl.getCompanyId())))
            return companyAcl;  
      } 
    } 
    return null;
  }
  
  public static String getOwnerEnvironmentWhereClause(Context context) {
    Vector envs = getUserEnvironments(context);
    Environment env = null;
    StringList list = new StringList(" OR ");
    for (int i = 0; i < envs.size(); i++) {
      env = (Environment)envs.elementAt(i);
      if (env != null && env.getParentFamily() != null)
        list.add("owner = " + env.getParentFamily().getStructureID()); 
    } 
    String whereClause = list.toString();
    if (whereClause.length() > 1) {
      whereClause = " WHERE (" + whereClause + ")";
    } else {
      whereClause = " WHERE (owner = -1)";
    } 
    return whereClause;
  }
  
  public static Environment getEnvironmentById(int id) {
    Vector envs = Cache.getInstance().getEnvironments();
    if (envs != null)
      for (int i = 0; i < envs.size(); i++) {
        Environment envTemp = (Environment)envs.elementAt(i);
        if (envTemp != null && envTemp.getIdentity() == id)
          return envTemp; 
      }  
    return null;
  }
  
  public static Vector getUserEnvironments(Context context) {
    User user = (User)context.getSessionValue("user");
    int userId = user.getUserId();
    if (context.getSessionValue("user-environments") != null && !((Vector)context.getSessionValue("user-environments")).isEmpty())
      return (Vector)context.getSessionValue("user-environments"); 
    Vector userEnvironments = getUserEnvironments(userId);
    if (userEnvironments != null)
      context.putSessionValue("user-environments", userEnvironments); 
    return userEnvironments;
  }
  
  public static Vector getUserEnvironmentsExcludeUmlEnterprise(Context context) {
    User user = (User)context.getSessionValue("user");
    int userId = user.getUserId();
    return getUserEnvironmentsExcludeUmlEnterprise(userId);
  }
  
  public static FormDropDownMenu getReleasingFamilyDropDown(String name, String selectedOption, Vector relList, boolean required, Selection selection) {
    Vector values = new Vector();
    Vector menuText = new Vector();
    FormDropDownMenu dropDown = null;
    if (selectedOption == null)
      selectedOption = ""; 
    if (relList != null) {
      boolean foundMatch = false;
      for (int i = 0; i < relList.size(); i++) {
        ReleasingFamily rFamily = (ReleasingFamily)relList.elementAt(i);
        if (rFamily != null) {
          values.addElement(Integer.toString(rFamily.getReleasingFamilyId()));
          menuText.addElement(rFamily.getFamillyName());
          if (selectedOption.equalsIgnoreCase(Integer.toString(rFamily.getReleasingFamilyId())))
            foundMatch = true; 
        } 
      } 
      if (!foundMatch) {
        values.addElement(String.valueOf(selection.getReleaseFamilyId()));
        menuText.addElement(ReleasingFamily.getName(selection.getReleaseFamilyId()));
      } 
      String[] arrayValues = new String[values.size()];
      String[] arrayMenuText = new String[menuText.size()];
      arrayValues = (String[])values.toArray(arrayValues);
      arrayMenuText = (String[])menuText.toArray(arrayMenuText);
      dropDown = new FormDropDownMenu(name, 
          selectedOption, 
          arrayValues, 
          arrayMenuText, 
          required);
    } else {
      values.addElement(String.valueOf(selection.getReleaseFamilyId()));
      menuText.addElement(ReleasingFamily.getName(selection.getReleaseFamilyId()));
      String[] arrayValues = new String[values.size()];
      String[] arrayMenuText = new String[menuText.size()];
      arrayValues = (String[])values.toArray(arrayValues);
      arrayMenuText = (String[])menuText.toArray(arrayMenuText);
      dropDown = new FormDropDownMenu(name, 
          selectedOption, 
          arrayValues, 
          arrayMenuText, 
          required);
    } 
    return dropDown;
  }
  
  public static Vector removeUnusedCSO(Vector cso, Context context, int thisReleasingFamilyOnlyId) {
    Vector csoClone = (Vector)cso.clone();
    Hashtable userRelFamiliesHash = null;
    User user = (User)context.getSessionValue("user");
    if (user != null)
      userRelFamiliesHash = user.getReleasingFamily(); 
    if (userRelFamiliesHash == null)
      userRelFamiliesHash = ReleasingFamily.get(user.getUserId()); 
    Hashtable productRelFamiliesHash = Cache.getProductReleasingFamilies();
    CorporateStructureObject currentObject = null;
    for (int i = csoClone.size() - 1; i >= 0; i--) {
      currentObject = (CorporateStructureObject)csoClone.get(i);
      boolean isUserRelFamEqualToProdRelFam = false;
      boolean isFamilyObj = false;
      if (productRelFamiliesHash != null && userRelFamiliesHash != null) {
        String csoLabelFamilyStr = "";
        int csoLabelFamilyId = -1;
        if (currentObject instanceof Label)
          csoLabelFamilyStr = Integer.toString(currentObject.getParent().getParent().getParent().getParent().getStructureID()); 
        if (currentObject instanceof Division)
          csoLabelFamilyStr = Integer.toString(currentObject.getParent().getParent().getParent().getStructureID()); 
        if (currentObject instanceof Company)
          csoLabelFamilyStr = Integer.toString(currentObject.getParent().getParent().getStructureID()); 
        if (currentObject instanceof Environment)
          csoLabelFamilyStr = Integer.toString(currentObject.getParent().getStructureID()); 
        if (currentObject instanceof Family) {
          isFamilyObj = true;
          csoLabelFamilyStr = Integer.toString(currentObject.getStructureID());
        } 
        csoLabelFamilyId = Integer.parseInt(csoLabelFamilyStr);
        Vector vProdRelFamilies = (Vector)productRelFamiliesHash.get(Integer.toString(currentObject.getStructureID()));
        if (vProdRelFamilies != null && vProdRelFamilies.size() > 0)
          for (int r = 0; r < vProdRelFamilies.size(); r++) {
            int prodRelFamilyId = Integer.parseInt((String)vProdRelFamilies.get(r));
            boolean containsKey = false;
            if (userRelFamiliesHash.containsKey(csoLabelFamilyStr)) {
              Vector vUserRelFamilies = (Vector)userRelFamiliesHash.get(csoLabelFamilyStr);
              if (vUserRelFamilies != null) {
                containsKey = true;
                for (int x = 0; x < vUserRelFamilies.size(); x++) {
                  ReleasingFamily relFamily = (ReleasingFamily)vUserRelFamilies.get(x);
                  if (relFamily != null)
                    if (thisReleasingFamilyOnlyId == -1 || relFamily.getReleasingFamilyId() == thisReleasingFamilyOnlyId)
                      if (relFamily.getReleasingFamilyId() == prodRelFamilyId) {
                        isUserRelFamEqualToProdRelFam = true;
                        break;
                      }   
                } 
              } 
            } 
            if (!isUserRelFamEqualToProdRelFam) {
              if (isFamilyObj)
                if (checkUserRelFamilyByFamilyId(userRelFamiliesHash, csoLabelFamilyId, productRelFamiliesHash))
                  isUserRelFamEqualToProdRelFam = true;  
              if (!containsKey && prodRelFamilyId == csoLabelFamilyId)
                isUserRelFamEqualToProdRelFam = true; 
            } 
            if (isUserRelFamEqualToProdRelFam)
              break; 
          }  
      } else {
        isUserRelFamEqualToProdRelFam = true;
      } 
      if (!isUserRelFamEqualToProdRelFam)
        csoClone.remove(i); 
    } 
    return csoClone;
  }
  
  public static boolean checkUserRelFamilyByFamilyId(Hashtable userRelFamiliesHash, int csoLabelFamilyId, Hashtable productRelFamiliesHash) {
    boolean result = false;
    if (userRelFamiliesHash == null)
      return result; 
    Hashtable userRelFamByValueHash = new Hashtable();
    Enumeration userRelFamiliesEnum = userRelFamiliesHash.elements();
    while (userRelFamiliesEnum.hasMoreElements()) {
      Vector userReleasingFamilies = (Vector)userRelFamiliesEnum.nextElement();
      if (userReleasingFamilies != null)
        for (int f = 0; f < userReleasingFamilies.size(); f++) {
          ReleasingFamily relFamily = (ReleasingFamily)userReleasingFamilies.get(f);
          if (relFamily != null && relFamily.getReleasingFamilyId() == csoLabelFamilyId) {
            int relLabelFamilyId = relFamily.getLabelFamilyId();
            Vector vProdRelFamilies = (Vector)productRelFamiliesHash.get(Integer.toString(relLabelFamilyId));
            if (vProdRelFamilies != null && vProdRelFamilies.size() > 0)
              for (int r = 0; r < vProdRelFamilies.size(); r++) {
                int prodRelFamilyId = Integer.parseInt((String)vProdRelFamilies.get(r));
                if (prodRelFamilyId == relLabelFamilyId)
                  return true; 
              }  
          } 
        }  
    } 
    return result;
  }
  
  public static CorporateStructureObject getCoporateStructure(int id) {
    Vector sList = Cache.getCorporateStructure();
    CorporateStructureObject currentObject = null;
    for (int i = 0; i < sList.size(); i++) {
      currentObject = (CorporateStructureObject)sList.get(i);
      if (currentObject.getStructureID() == id)
        break; 
    } 
    return currentObject;
  }
  
  public static Vector getConfigCodes(int type) {
    Vector configCodes = Cache.getInstance().getConfigCodes();
    Vector precache = new Vector();
    for (int i = 0; i < configCodes.size(); i++) {
      LookupObject luo = (LookupObject)configCodes.get(i);
      if (luo.getProdType() == type || luo.getProdType() == 2)
        precache.add(luo); 
    } 
    return precache;
  }
  
  public static Hashtable groupSelectionsForIDJAlternate(Vector selections) {
    Hashtable groupedSelections = new Hashtable();
    if (selections == null)
      return groupedSelections; 
    for (int i = 0; i < selections.size(); i++) {
      Selection sel = (Selection)selections.elementAt(i);
      if (sel != null) {
        sel = SelectionManager.getInstance().getSelectionAndSchedule(sel.getSelectionID());
        String selArtist = sel.getFlArtist();
        Vector group = (Vector)groupedSelections.get(selArtist);
        if (group == null) {
          group = new Vector();
          group.addElement(sel);
          groupedSelections.put(selArtist, group);
        } else {
          group.addElement(sel);
        } 
      } 
    } 
    return groupedSelections;
  }
  
  public static Hashtable groupSelectionsForIDJAltByRelDate(Vector selections, boolean isTBS) {
    Hashtable groupedSelections = new Hashtable();
    if (selections == null)
      return groupedSelections; 
    for (int i = 0; i < selections.size(); i++) {
      Selection sel = (Selection)selections.elementAt(i);
      if (sel != null) {
        sel = SelectionManager.getInstance().getSelectionAndSchedule(sel.getSelectionID());
        String status = (sel.getSelectionStatus() != null && sel.getSelectionStatus().getName() != null) ? sel.getSelectionStatus().getName() : "";
        if (!status.equalsIgnoreCase("TBS") || isTBS) {
          String dateString = "";
          if (sel.getIsDigital()) {
            dateString = (sel.getDigitalRlsDate() != null) ? getFormatedDate(sel.getDigitalRlsDate()) : "";
          } else {
            dateString = (sel.getStreetDate() != null) ? getFormatedDate(sel.getStreetDate()) : "";
          } 
          if (dateString.equals("") && isTBS)
            dateString = "NoDate"; 
          Vector group = (Vector)groupedSelections.get(dateString);
          if (group == null) {
            group = new Vector();
            group.addElement(sel);
            groupedSelections.put(dateString, group);
          } else {
            group.addElement(sel);
          } 
        } 
      } 
    } 
    return groupedSelections;
  }
  
  public static Hashtable buildFinalIDJAlternateSelections(Vector selections) {
    Hashtable finalSelections = new Hashtable();
    Hashtable selArtistSelections = groupSelectionsForIDJAlternate(selections);
    Hashtable selFullLengths = groupSelectionsForIDJAltByRelDate(selections, false);
    Vector artistProcessed = new Vector();
    Enumeration fullLengthReleaseDatesEnum = selFullLengths.keys();
    Vector fullLengthReleaseDateVector = new Vector();
    while (fullLengthReleaseDatesEnum.hasMoreElements())
      fullLengthReleaseDateVector.addElement(fullLengthReleaseDatesEnum.nextElement()); 
    Enumeration artistEnum = selArtistSelections.keys();
    Vector artistVector = new Vector();
    while (artistEnum.hasMoreElements())
      artistVector.addElement(artistEnum.nextElement()); 
    Collections.sort(fullLengthReleaseDateVector, new DateConverterComparator());
    for (int fullLength = 0; fullLength < fullLengthReleaseDateVector.size(); fullLength++) {
      String fullLengthDate = (String)fullLengthReleaseDateVector.elementAt(fullLength);
      Vector selectionsForDate = (Vector)selFullLengths.get(fullLengthDate);
      for (int selectionCount = 0; selectionCount < selectionsForDate.size(); selectionCount++) {
        Selection sel = (Selection)selectionsForDate.elementAt(selectionCount);
        String artistName = sel.getFlArtist();
        if (!artistProcessed.contains(artistName)) {
          artistProcessed.add(artistName);
          Vector artistsSelections = (Vector)selArtistSelections.get(artistName);
          finalSelections.put(String.valueOf(fullLengthDate) + " * " + artistName, artistsSelections);
        } 
      } 
    } 
    Vector exceptionsVector = new Vector();
    for (int artistCounter = 0; artistCounter < artistVector.size(); artistCounter++) {
      String artistName = (String)artistVector.elementAt(artistCounter);
      if (!artistProcessed.contains(artistName)) {
        Vector artistsSelections = (Vector)selArtistSelections.get(artistName);
        for (int artistSelectionCount = 0; artistSelectionCount < artistsSelections.size(); artistSelectionCount++)
          exceptionsVector.add((Selection)artistsSelections.elementAt(artistSelectionCount)); 
        artistProcessed.add(artistName);
      } 
    } 
    Hashtable exceptionsHash = new Hashtable();
    Vector exceptionsArtists = new Vector();
    Vector totalExceptions = new Vector();
    String earliestDate = "";
    Hashtable selExceptionArtistSelections = groupSelectionsForIDJAlternate(exceptionsVector);
    Hashtable selExceptionsStreetDates = groupSelectionsForIDJAltByRelDate(exceptionsVector, true);
    Vector exceptionsartistProcessed = new Vector();
    Enumeration TBSReleaseDatesEnum = selExceptionsStreetDates.keys();
    Vector TBSReleaseDateVector = new Vector();
    while (TBSReleaseDatesEnum.hasMoreElements())
      TBSReleaseDateVector.addElement(TBSReleaseDatesEnum.nextElement()); 
    Enumeration TBSartistEnum = selExceptionArtistSelections.keys();
    Vector TBSartistVector = new Vector();
    while (TBSartistEnum.hasMoreElements())
      TBSartistVector.addElement(TBSartistEnum.nextElement()); 
    Collections.sort(TBSReleaseDateVector, new DateConverterComparator());
    for (int TBSDateCounter = 0; TBSDateCounter < TBSReleaseDateVector.size(); TBSDateCounter++) {
      String TBSDate = (String)TBSReleaseDateVector.elementAt(TBSDateCounter);
      Vector selectionsForDate = (Vector)selExceptionsStreetDates.get(TBSDate);
      for (int selectionCount = 0; selectionCount < selectionsForDate.size(); selectionCount++) {
        Selection sel = (Selection)selectionsForDate.elementAt(selectionCount);
        String artistName = sel.getFlArtist();
        if (!exceptionsartistProcessed.contains(artistName)) {
          exceptionsartistProcessed.add(artistName);
          Vector artistsSelections = (Vector)selExceptionArtistSelections.get(artistName);
          exceptionsHash.put("TBS * " + TBSDate + " * " + artistName, artistsSelections);
        } 
      } 
    } 
    Hashtable finalhashtable2 = new Hashtable();
    finalhashtable2.put("TBS", exceptionsHash);
    finalhashtable2.put("Product", finalSelections);
    return finalhashtable2;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\MilestoneHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */