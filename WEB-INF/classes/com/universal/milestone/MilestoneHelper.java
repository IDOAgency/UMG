/*       */ package WEB-INF.classes.com.universal.milestone;
/*       */ 
/*       */ import com.techempower.BasicHelper;
/*       */ import com.techempower.ComponentLog;
/*       */ import com.techempower.DataEntity;
/*       */ import com.techempower.DatabaseConnector;
/*       */ import com.techempower.EnhancedProperties;
/*       */ import com.techempower.StringList;
/*       */ import com.techempower.gemini.Context;
/*       */ import com.techempower.gemini.FormCheckBox;
/*       */ import com.techempower.gemini.FormDropDownMenu;
/*       */ import com.techempower.gemini.GeminiApplication;
/*       */ import com.universal.milestone.Acl;
/*       */ import com.universal.milestone.Cache;
/*       */ import com.universal.milestone.Company;
/*       */ import com.universal.milestone.CompanyAcl;
/*       */ import com.universal.milestone.CompanyManager;
/*       */ import com.universal.milestone.CorpStructNameComparator;
/*       */ import com.universal.milestone.CorpStructParentNameComparator;
/*       */ import com.universal.milestone.CorporateStructureObject;
/*       */ import com.universal.milestone.DateConverterComparator;
/*       */ import com.universal.milestone.DatePeriod;
/*       */ import com.universal.milestone.Day;
/*       */ import com.universal.milestone.DayManager;
/*       */ import com.universal.milestone.Division;
/*       */ import com.universal.milestone.DivisionManager;
/*       */ import com.universal.milestone.Environment;
/*       */ import com.universal.milestone.EnvironmentAcl;
/*       */ import com.universal.milestone.EnvironmentManager;
/*       */ import com.universal.milestone.Family;
/*       */ import com.universal.milestone.FamilyManager;
/*       */ import com.universal.milestone.Form;
/*       */ import com.universal.milestone.Genre;
/*       */ import com.universal.milestone.JdbcConnector;
/*       */ import com.universal.milestone.Label;
/*       */ import com.universal.milestone.LabelManager;
/*       */ import com.universal.milestone.LookupObject;
/*       */ import com.universal.milestone.MilestoneConstants;
/*       */ import com.universal.milestone.MilestoneFormDropDownMenu;
/*       */ import com.universal.milestone.MilestoneHelper;
/*       */ import com.universal.milestone.MilestoneSecurity;
/*       */ import com.universal.milestone.MilestoneVector;
/*       */ import com.universal.milestone.Notepad;
/*       */ import com.universal.milestone.PrefixCode;
/*       */ import com.universal.milestone.PriceCode;
/*       */ import com.universal.milestone.PriceCodeManager;
/*       */ import com.universal.milestone.ProductCategory;
/*       */ import com.universal.milestone.ReleaseType;
/*       */ import com.universal.milestone.ReleaseWeek;
/*       */ import com.universal.milestone.ReleaseWeekManager;
/*       */ import com.universal.milestone.ReleasingFamily;
/*       */ import com.universal.milestone.Report;
/*       */ import com.universal.milestone.ReportConfigManager;
/*       */ import com.universal.milestone.ReportSelections;
/*       */ import com.universal.milestone.Schedule;
/*       */ import com.universal.milestone.ScheduleManager;
/*       */ import com.universal.milestone.ScheduledTask;
/*       */ import com.universal.milestone.Selection;
/*       */ import com.universal.milestone.SelectionConfiguration;
/*       */ import com.universal.milestone.SelectionManager;
/*       */ import com.universal.milestone.SelectionStatus;
/*       */ import com.universal.milestone.SelectionSubConfiguration;
/*       */ import com.universal.milestone.StringComparator;
/*       */ import com.universal.milestone.StringDateComparator;
/*       */ import com.universal.milestone.Table;
/*       */ import com.universal.milestone.TableManager;
/*       */ import com.universal.milestone.Task;
/*       */ import com.universal.milestone.TaskManager;
/*       */ import com.universal.milestone.Template;
/*       */ import com.universal.milestone.TemplateManager;
/*       */ import com.universal.milestone.User;
/*       */ import com.universal.milestone.UserManager;
/*       */ import java.awt.Font;
/*       */ import java.awt.FontMetrics;
/*       */ import java.awt.Frame;
/*       */ import java.awt.Graphics;
/*       */ import java.awt.Graphics2D;
/*       */ import java.awt.Image;
/*       */ import java.text.ParsePosition;
/*       */ import java.text.SimpleDateFormat;
/*       */ import java.util.ArrayList;
/*       */ import java.util.Arrays;
/*       */ import java.util.Calendar;
/*       */ import java.util.Collections;
/*       */ import java.util.Date;
/*       */ import java.util.Enumeration;
/*       */ import java.util.HashMap;
/*       */ import java.util.Hashtable;
/*       */ import java.util.StringTokenizer;
/*       */ import java.util.Vector;
/*       */ import javax.servlet.http.HttpServletResponse;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ public class MilestoneHelper
/*       */   extends BasicHelper
/*       */   implements MilestoneConstants
/*       */ {
/*       */   public static final String COMPONENT_CODE = "help";
/*   114 */   protected static Vector bomSuppliers = null;
/*   115 */   protected static String databaseName = "Milestone";
/*   116 */   protected static String dbLoginName = "milestone";
/*   117 */   protected static String dbLoginPass = "milestone";
/*   118 */   protected static String urlPrefix = "jdbc:odbc:";
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   protected static ComponentLog log;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void configure(EnhancedProperties props, GeminiApplication application) {
/*   131 */     databaseName = props.getProperty("DatabaseName", databaseName);
/*   132 */     dbLoginName = props.getProperty("DatabaseLogin", dbLoginName);
/*   133 */     dbLoginPass = props.getProperty("DatabasePassword", dbLoginPass);
/*   134 */     urlPrefix = props.getProperty("JDBCURLPrefix", urlPrefix);
/*       */ 
/*       */     
/*   137 */     log = application.getLog("help");
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static JdbcConnector createConnector(String query) {
/*   146 */     JdbcConnector connector = new JdbcConnector(urlPrefix, databaseName, query);
/*   147 */     connector.setForwardOnly(true);
/*   148 */     connector.setUsername(dbLoginName);
/*   149 */     connector.setPassword(dbLoginPass);
/*       */     
/*   151 */     return connector;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static JdbcConnector createScrollableConnector(String query) {
/*   160 */     JdbcConnector connector = new JdbcConnector(urlPrefix, databaseName, query);
/*   161 */     connector.setForwardOnly(false);
/*   162 */     connector.setUsername(dbLoginName);
/*   163 */     connector.setPassword(dbLoginPass);
/*       */     
/*   165 */     return connector;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*   173 */   public static JdbcConnector getConnector(String query) { return createConnector(query); }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*   181 */   public static JdbcConnector getScrollableConnector(String query) { return createScrollableConnector(query); }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Vector getDataEntities(Class dataEntityClass, DatabaseConnector connector) {
/*   195 */     Vector toReturn = new Vector();
/*       */ 
/*       */     
/*   198 */     while (connector.more()) {
/*       */ 
/*       */       
/*       */       try {
/*   202 */         DataEntity dataEntity = (DataEntity)dataEntityClass.newInstance();
/*   203 */         dataEntity.initializeByVariables(connector);
/*   204 */         toReturn.addElement(dataEntity);
/*       */       }
/*   206 */       catch (Exception exception) {}
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*   211 */       connector.next();
/*       */     } 
/*   213 */     connector.close();
/*       */     
/*   215 */     return toReturn;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static String getFormatedDate(Calendar pdateDate) {
/*   229 */     String strDate = "";
/*       */     
/*   231 */     if (pdateDate != null) {
/*       */       
/*   233 */       SimpleDateFormat formatter = new SimpleDateFormat("M/d/yy");
/*   234 */       strDate = formatter.format(pdateDate.getTime());
/*       */     } 
/*       */     
/*   237 */     return strDate;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static String getCustomFormatedDate(Calendar pdateDate, String pstrFormat) {
/*   250 */     String strDate = "";
/*       */     
/*   252 */     if (pdateDate != null) {
/*       */       
/*   254 */       SimpleDateFormat formatter = new SimpleDateFormat(pstrFormat);
/*   255 */       strDate = formatter.format(pdateDate.getTime());
/*       */     } 
/*       */     
/*   258 */     return strDate;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static String getFormatedDateNoYear(Calendar pdateDate) {
/*   271 */     String strDate = "";
/*       */     
/*   273 */     if (pdateDate != null) {
/*       */       
/*   275 */       SimpleDateFormat formatter = new SimpleDateFormat("M/d");
/*   276 */       strDate = formatter.format(pdateDate.getTime());
/*       */     } 
/*       */     
/*   279 */     return strDate;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static String getShortDate(Calendar pdateDate) {
/*   293 */     String strDate = "";
/*       */     
/*   295 */     if (pdateDate != null) {
/*       */       
/*   297 */       SimpleDateFormat formatter = new SimpleDateFormat("M/d");
/*   298 */       strDate = formatter.format(pdateDate.getTime());
/*       */     } 
/*       */     
/*   301 */     return strDate;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static String getLongDate(Calendar pdateDate) {
/*   314 */     String strDate = "";
/*       */     
/*   316 */     if (pdateDate != null && !pdateDate.equals("")) {
/*       */       
/*   318 */       SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM d hh:mm:ss 'ET' yyyy");
/*   319 */       strDate = formatter.format(pdateDate.getTime());
/*       */     } 
/*   321 */     return strDate.trim();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Calendar getDate(String date) {
/*   332 */     if (date == null) {
/*   333 */       return null;
/*       */     }
/*       */ 
/*       */     
/*       */     try {
/*   338 */       StringTokenizer tokenizer = new StringTokenizer(date, "-/.");
/*   339 */       String strMonth = tokenizer.nextToken();
/*   340 */       String strDay = tokenizer.nextToken();
/*   341 */       String strYear = tokenizer.nextToken();
/*       */       
/*   343 */       int month = Integer.parseInt(strMonth) - 1;
/*   344 */       int day = Integer.parseInt(strDay);
/*   345 */       int year = Integer.parseInt(strYear);
/*       */ 
/*       */       
/*   348 */       if (year < 100)
/*       */       {
/*   350 */         if (year > 50) {
/*   351 */           year += 1900;
/*       */         } else {
/*   353 */           year += 2000;
/*       */         } 
/*       */       }
/*   356 */       Calendar cal = Calendar.getInstance();
/*       */       
/*   358 */       cal.clear(14);
/*   359 */       cal.set(year, month, day, 0, 0, 0);
/*       */       
/*   361 */       return cal;
/*       */     }
/*   363 */     catch (Exception exception) {
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*   368 */       return null;
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Calendar getMYDate(String date) {
/*   381 */     if (date == null) {
/*   382 */       return null;
/*       */     }
/*       */ 
/*       */     
/*       */     try {
/*   387 */       StringTokenizer tokenizer = new StringTokenizer(date, "-/.");
/*   388 */       String strMonth = tokenizer.nextToken();
/*   389 */       String strYear = tokenizer.nextToken();
/*       */       
/*   391 */       int month = Integer.parseInt(strMonth) - 1;
/*   392 */       int year = Integer.parseInt(strYear);
/*       */ 
/*       */       
/*   395 */       if (year < 100)
/*       */       {
/*   397 */         if (year > 50) {
/*   398 */           year += 1900;
/*       */         } else {
/*   400 */           year += 2000;
/*       */         } 
/*       */       }
/*   403 */       Calendar cal = Calendar.getInstance();
/*   404 */       cal.set(year, month, 1);
/*       */       
/*   406 */       return cal;
/*       */     }
/*   408 */     catch (Exception exception) {
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*   413 */       return null;
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int getMonth(String date) {
/*   424 */     if (date == null) {
/*   425 */       return 0;
/*       */     }
/*   427 */     int dateInt = 0;
/*       */ 
/*       */ 
/*       */     
/*       */     try {
/*   432 */       if (date.equalsIgnoreCase("January")) {
/*   433 */         dateInt = 1;
/*   434 */       } else if (date.equalsIgnoreCase("February")) {
/*   435 */         dateInt = 2;
/*   436 */       } else if (date.equalsIgnoreCase("March")) {
/*   437 */         dateInt = 3;
/*   438 */       } else if (date.equalsIgnoreCase("April")) {
/*   439 */         dateInt = 4;
/*   440 */       } else if (date.equalsIgnoreCase("May")) {
/*   441 */         dateInt = 5;
/*   442 */       } else if (date.equalsIgnoreCase("June")) {
/*   443 */         dateInt = 6;
/*   444 */       } else if (date.equalsIgnoreCase("July")) {
/*   445 */         dateInt = 7;
/*   446 */       } else if (date.equalsIgnoreCase("August")) {
/*   447 */         dateInt = 8;
/*   448 */       } else if (date.equalsIgnoreCase("September")) {
/*   449 */         dateInt = 9;
/*   450 */       } else if (date.equalsIgnoreCase("October")) {
/*   451 */         dateInt = 10;
/*   452 */       } else if (date.equalsIgnoreCase("November")) {
/*   453 */         dateInt = 11;
/*   454 */       } else if (date.equalsIgnoreCase("December")) {
/*   455 */         dateInt = 12;
/*       */       } 
/*   457 */       return dateInt;
/*       */     }
/*   459 */     catch (Exception exception) {
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*   464 */       return 0;
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Calendar getDatabaseDate(String pdateDate) {
/*   478 */     if (pdateDate.length() > 0) {
/*       */       
/*   480 */       SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
/*       */       
/*   482 */       ParsePosition pos = new ParsePosition(0);
/*   483 */       Date current = formatter.parse(pdateDate, pos);
/*       */       
/*   485 */       Calendar strDate = Calendar.getInstance();
/*   486 */       strDate.setTime(current);
/*   487 */       return strDate;
/*       */     } 
/*       */     
/*   490 */     return null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*   503 */   public static void putNotepadIntoSession(Notepad notepad, Context context) { context.putSessionValue(NOTEPAD_SESSION_NAMES[notepad.getNotePadType()], notepad); }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*   517 */   public static Notepad getNotepadFromSession(int notepadType, Context context) { return (Notepad)context.getSessionValue(NOTEPAD_SESSION_NAMES[notepadType]); }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static FormDropDownMenu getSelectionConfigurationDropDown(String name, String selectedOption, boolean required) {
/*   532 */     Vector configs = Cache.getSelectionConfigs();
/*       */ 
/*       */     
/*   535 */     String[] values = new String[configs.size() + 1];
/*   536 */     String[] menuText = new String[configs.size() + 1];
/*       */ 
/*       */     
/*   539 */     ArrayList valuesA = new ArrayList();
/*   540 */     ArrayList menuTextA = new ArrayList();
/*       */ 
/*       */     
/*   543 */     values[0] = "0";
/*   544 */     menuText[0] = "All";
/*   545 */     valuesA.add("0");
/*   546 */     menuTextA.add("All");
/*       */     
/*   548 */     if (selectedOption == null) selectedOption = "";
/*       */     
/*   550 */     for (int i = 0; i < configs.size(); i++) {
/*       */       
/*   552 */       SelectionConfiguration config = (SelectionConfiguration)configs.elementAt(i);
/*       */ 
/*       */       
/*   555 */       if (config.getInactive())
/*       */       {
/*       */ 
/*       */         
/*   559 */         if (!config.getSelectionConfigurationAbbreviation().equalsIgnoreCase(selectedOption)) {
/*       */           continue;
/*       */         }
/*       */       }
/*   563 */       values[i + 1] = config.getSelectionConfigurationAbbreviation();
/*   564 */       menuText[i + 1] = config.getSelectionConfigurationName();
/*   565 */       valuesA.add(config.getSelectionConfigurationAbbreviation());
/*   566 */       menuTextA.add(config.getSelectionConfigurationName());
/*       */       continue;
/*       */     } 
/*   569 */     values = new String[valuesA.size()];
/*   570 */     menuText = new String[valuesA.size()];
/*   571 */     values = (String[])valuesA.toArray(values);
/*   572 */     menuText = (String[])menuTextA.toArray(menuText);
/*       */     
/*   574 */     return new FormDropDownMenu(name, 
/*   575 */         selectedOption, 
/*   576 */         values, 
/*   577 */         menuText, 
/*   578 */         required);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static FormDropDownMenu getSelectionConfigurationDropDown(String name, String selectedOption, boolean required, int prodType) {
/*   594 */     Vector configs = Cache.getSelectionConfigs();
/*       */ 
/*       */     
/*   597 */     String[] values = new String[configs.size() + 1];
/*   598 */     String[] menuText = new String[configs.size() + 1];
/*       */ 
/*       */     
/*   601 */     ArrayList valuesA = new ArrayList();
/*   602 */     ArrayList menuTextA = new ArrayList();
/*       */ 
/*       */     
/*   605 */     values[0] = "0";
/*   606 */     menuText[0] = "&nbsp;";
/*   607 */     valuesA.add("0");
/*   608 */     menuTextA.add("&nbsp;");
/*       */     
/*   610 */     if (selectedOption == null) selectedOption = "";
/*       */     
/*   612 */     int currIndex = 0;
/*       */     
/*   614 */     for (int i = 0; i < configs.size(); i++) {
/*       */       
/*   616 */       SelectionConfiguration config = (SelectionConfiguration)configs.elementAt(i);
/*       */ 
/*       */       
/*   619 */       if (config.getInactive()) {
/*       */ 
/*       */ 
/*       */         
/*   623 */         if (!config.getSelectionConfigurationAbbreviation().equalsIgnoreCase(selectedOption)) {
/*       */           continue;
/*       */         }
/*   626 */       } else if (config.getProdType() != prodType && config.getProdType() != 2) {
/*       */         continue;
/*       */       } 
/*       */ 
/*       */ 
/*       */       
/*   632 */       values[currIndex + 1] = config.getSelectionConfigurationAbbreviation();
/*   633 */       menuText[currIndex + 1] = config.getSelectionConfigurationName();
/*   634 */       valuesA.add(config.getSelectionConfigurationAbbreviation());
/*   635 */       menuTextA.add(config.getSelectionConfigurationName());
/*   636 */       currIndex++;
/*       */       continue;
/*       */     } 
/*   639 */     values = new String[valuesA.size()];
/*   640 */     menuText = new String[valuesA.size()];
/*   641 */     values = (String[])valuesA.toArray(values);
/*   642 */     menuText = (String[])menuTextA.toArray(menuText);
/*       */     
/*   644 */     return new FormDropDownMenu(name, 
/*   645 */         selectedOption, 
/*   646 */         values, 
/*   647 */         menuText, 
/*   648 */         required);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static FormDropDownMenu getSelectionConfigurationDropDown(String name, String selectedOption, boolean required, int prodType, boolean newBundle) {
/*   656 */     Vector configs = Cache.getSelectionConfigs();
/*       */ 
/*       */     
/*   659 */     String[] values = new String[configs.size() + 1];
/*   660 */     String[] menuText = new String[configs.size() + 1];
/*       */ 
/*       */     
/*   663 */     ArrayList valuesA = new ArrayList();
/*   664 */     ArrayList menuTextA = new ArrayList();
/*       */ 
/*       */     
/*   667 */     values[0] = "0";
/*   668 */     menuText[0] = "&nbsp;";
/*   669 */     valuesA.add("0");
/*   670 */     menuTextA.add("&nbsp;");
/*       */     
/*   672 */     if (selectedOption == null) selectedOption = "";
/*       */     
/*   674 */     int currIndex = 0;
/*       */     
/*   676 */     for (int i = 0; i < configs.size(); i++) {
/*       */       
/*   678 */       SelectionConfiguration config = (SelectionConfiguration)configs.elementAt(i);
/*       */ 
/*       */       
/*   681 */       if (config.getInactive()) {
/*       */ 
/*       */ 
/*       */         
/*   685 */         if (!config.getSelectionConfigurationAbbreviation().equalsIgnoreCase(selectedOption))
/*       */           continue; 
/*       */       } else {
/*   688 */         if (config.getProdType() != prodType && config.getProdType() != 2) {
/*       */           continue;
/*       */         }
/*       */         
/*   692 */         if (!config.getSelectionConfigurationAbbreviation().equalsIgnoreCase(selectedOption) && (config.getProdType() == 1 || config.getProdType() == 2) && ((
/*   693 */           newBundle && config.getNewBundle() != 1) || (!newBundle && config.getNewBundle() == 1))) {
/*       */           continue;
/*       */         }
/*       */       } 
/*       */ 
/*       */       
/*   699 */       values[currIndex + 1] = config.getSelectionConfigurationAbbreviation();
/*   700 */       menuText[currIndex + 1] = config.getSelectionConfigurationName();
/*   701 */       valuesA.add(config.getSelectionConfigurationAbbreviation());
/*   702 */       menuTextA.add(config.getSelectionConfigurationName());
/*   703 */       currIndex++;
/*       */       continue;
/*       */     } 
/*   706 */     values = new String[valuesA.size()];
/*   707 */     menuText = new String[valuesA.size()];
/*   708 */     values = (String[])valuesA.toArray(values);
/*   709 */     menuText = (String[])menuTextA.toArray(menuText);
/*       */     
/*   711 */     return new FormDropDownMenu(name, 
/*   712 */         selectedOption, 
/*   713 */         values, 
/*   714 */         menuText, 
/*   715 */         required);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static FormDropDownMenu getSelectionSubConfigurationDropDown(String name, SelectionConfiguration config, String selectedOption, boolean required) {
/*   731 */     FormDropDownMenu dropDown = null;
/*       */     
/*   733 */     if (selectedOption == null) {
/*   734 */       selectedOption = "";
/*       */     }
/*   736 */     if (config != null) {
/*       */       
/*   738 */       Vector subConfigs = config.getSubConfigurations();
/*       */ 
/*       */       
/*   741 */       String[] values = new String[subConfigs.size() + 1];
/*   742 */       String[] menuText = new String[subConfigs.size() + 1];
/*       */ 
/*       */       
/*   745 */       ArrayList valuesA = new ArrayList();
/*   746 */       ArrayList menuTextA = new ArrayList();
/*       */ 
/*       */       
/*   749 */       values[0] = "0";
/*   750 */       menuText[0] = "&nbsp;";
/*   751 */       valuesA.add("0");
/*   752 */       menuTextA.add("&nbsp;");
/*       */       
/*   754 */       for (int i = 0; i < subConfigs.size(); i++) {
/*       */         
/*   756 */         SelectionSubConfiguration subConfig = (SelectionSubConfiguration)subConfigs.elementAt(i);
/*       */         
/*   758 */         if (subConfig.getInactive())
/*       */         {
/*       */ 
/*       */           
/*   762 */           if (!subConfig.getSelectionSubConfigurationAbbreviation().equalsIgnoreCase(selectedOption)) {
/*       */             continue;
/*       */           }
/*       */         }
/*       */         
/*   767 */         values[i + 1] = subConfig.getSelectionSubConfigurationAbbreviation();
/*   768 */         menuText[i + 1] = subConfig.getSelectionSubConfigurationName();
/*   769 */         valuesA.add(subConfig.getSelectionSubConfigurationAbbreviation());
/*   770 */         menuTextA.add(subConfig.getSelectionSubConfigurationName());
/*       */         
/*       */         continue;
/*       */       } 
/*   774 */       values = new String[valuesA.size()];
/*   775 */       menuText = new String[menuTextA.size()];
/*   776 */       values = (String[])valuesA.toArray(values);
/*   777 */       menuText = (String[])menuTextA.toArray(menuText);
/*       */ 
/*       */       
/*   780 */       dropDown = new FormDropDownMenu(name, 
/*   781 */           selectedOption, 
/*   782 */           values, 
/*   783 */           menuText, 
/*   784 */           required);
/*       */     }
/*       */     else {
/*       */       
/*   788 */       dropDown = new FormDropDownMenu(name, "", "", "", required);
/*       */     } 
/*       */     
/*   791 */     return dropDown;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static FormDropDownMenu getSelectionSubConfigurationDropDown(String name, SelectionConfiguration config, String selectedOption, boolean required, int prodType) {
/*   805 */     FormDropDownMenu dropDown = null;
/*       */     
/*   807 */     if (selectedOption == null) {
/*   808 */       selectedOption = "";
/*       */     }
/*   810 */     if (config != null) {
/*       */       
/*   812 */       Vector subConfigs = config.getSubConfigurations();
/*       */ 
/*       */       
/*   815 */       String[] values = new String[subConfigs.size() + 1];
/*   816 */       String[] menuText = new String[subConfigs.size() + 1];
/*       */ 
/*       */       
/*   819 */       ArrayList valuesA = new ArrayList();
/*   820 */       ArrayList menuTextA = new ArrayList();
/*       */ 
/*       */       
/*   823 */       values[0] = "0";
/*   824 */       menuText[0] = "&nbsp;";
/*   825 */       valuesA.add("0");
/*   826 */       menuTextA.add("&nbsp;");
/*       */       
/*   828 */       int currIndex = 0;
/*       */       
/*   830 */       for (int i = 0; i < subConfigs.size(); i++) {
/*       */         
/*   832 */         SelectionSubConfiguration subConfig = (SelectionSubConfiguration)subConfigs.elementAt(i);
/*       */         
/*   834 */         if (subConfig.getInactive()) {
/*       */ 
/*       */ 
/*       */           
/*   838 */           if (!subConfig.getSelectionSubConfigurationAbbreviation().equalsIgnoreCase(selectedOption)) {
/*       */             continue;
/*       */           }
/*   841 */         } else if (subConfig.getProdType() != prodType && subConfig.getProdType() != 2) {
/*       */           continue;
/*       */         } 
/*       */ 
/*       */ 
/*       */         
/*   847 */         values[i + 1] = subConfig.getSelectionSubConfigurationAbbreviation();
/*   848 */         menuText[i + 1] = subConfig.getSelectionSubConfigurationName();
/*   849 */         valuesA.add(subConfig.getSelectionSubConfigurationAbbreviation());
/*   850 */         menuTextA.add(subConfig.getSelectionSubConfigurationName());
/*   851 */         currIndex++;
/*       */         
/*       */         continue;
/*       */       } 
/*   855 */       values = new String[valuesA.size()];
/*   856 */       menuText = new String[menuTextA.size()];
/*   857 */       values = (String[])valuesA.toArray(values);
/*   858 */       menuText = (String[])menuTextA.toArray(menuText);
/*       */ 
/*       */       
/*   861 */       dropDown = new FormDropDownMenu(name, 
/*   862 */           selectedOption, 
/*   863 */           values, 
/*   864 */           menuText, 
/*   865 */           required);
/*       */     }
/*       */     else {
/*       */       
/*   869 */       dropDown = new FormDropDownMenu(name, "", "", "", required);
/*       */     } 
/*       */     
/*   872 */     return dropDown;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static FormDropDownMenu getLookupDropDown(String name, Vector menuVector, String selectedOption, boolean required, boolean blankFirst) {
/*   894 */     FormDropDownMenu dropDown = null;
/*       */     
/*   896 */     if (menuVector != null) {
/*       */       
/*   898 */       int size = menuVector.size();
/*   899 */       int offset = 0;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*   905 */       ArrayList valuesA = new ArrayList();
/*   906 */       ArrayList menuTextA = new ArrayList();
/*       */ 
/*       */       
/*   909 */       if (blankFirst) {
/*       */         
/*   911 */         size++;
/*   912 */         offset = 1;
/*       */       } 
/*       */ 
/*       */       
/*   916 */       String[] values = new String[size];
/*   917 */       String[] menuText = new String[size];
/*       */ 
/*       */       
/*   920 */       if (blankFirst) {
/*       */         
/*   922 */         values[0] = "0";
/*   923 */         menuText[0] = "&nbsp;";
/*   924 */         valuesA.add("0");
/*   925 */         menuTextA.add("&nbsp;");
/*       */       } 
/*       */ 
/*       */       
/*   929 */       if (selectedOption == null) {
/*   930 */         selectedOption = "";
/*       */       }
/*   932 */       for (int i = 0; i < menuVector.size(); i++) {
/*       */         
/*   934 */         LookupObject lookupObject = (LookupObject)menuVector.elementAt(i);
/*       */         
/*   936 */         if (lookupObject.getInactive())
/*       */         {
/*       */ 
/*       */           
/*   940 */           if (!lookupObject.getAbbreviation().equalsIgnoreCase(selectedOption)) {
/*       */             continue;
/*       */           }
/*       */         }
/*       */         
/*   945 */         values[i + offset] = lookupObject.getAbbreviation();
/*   946 */         menuText[i + offset] = lookupObject.getName();
/*   947 */         valuesA.add(lookupObject.getAbbreviation());
/*   948 */         menuTextA.add(lookupObject.getName());
/*       */         
/*       */         continue;
/*       */       } 
/*   952 */       values = new String[valuesA.size()];
/*   953 */       menuText = new String[valuesA.size()];
/*   954 */       values = (String[])valuesA.toArray(values);
/*   955 */       menuText = (String[])menuTextA.toArray(menuText);
/*       */ 
/*       */       
/*   958 */       dropDown = new FormDropDownMenu(name, 
/*   959 */           selectedOption, 
/*   960 */           values, 
/*   961 */           menuText, 
/*   962 */           required);
/*       */     }
/*       */     else {
/*       */       
/*   966 */       dropDown = new FormDropDownMenu(name, "", "", "", required);
/*       */     } 
/*       */     
/*   969 */     return dropDown;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static FormDropDownMenu getLookupDropDownBom(String name, Vector menuVector, String selectedOption, boolean required, boolean blankFirst) {
/*   991 */     FormDropDownMenu dropDown = null;
/*       */     
/*   993 */     if (menuVector != null) {
/*       */       
/*   995 */       int size = menuVector.size();
/*   996 */       int offset = 0;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  1002 */       ArrayList valuesA = new ArrayList();
/*  1003 */       ArrayList menuTextA = new ArrayList();
/*       */ 
/*       */       
/*  1006 */       if (blankFirst) {
/*       */         
/*  1008 */         size++;
/*  1009 */         offset = 1;
/*       */       } 
/*       */ 
/*       */       
/*  1013 */       String[] values = new String[size];
/*  1014 */       String[] menuText = new String[size];
/*       */ 
/*       */       
/*  1017 */       if (blankFirst) {
/*       */         
/*  1019 */         values[0] = "&nbsp;";
/*  1020 */         menuText[0] = "&nbsp;";
/*  1021 */         valuesA.add("0");
/*  1022 */         menuTextA.add("&nbsp;");
/*       */       } 
/*       */ 
/*       */       
/*  1026 */       if (selectedOption == null) {
/*  1027 */         selectedOption = "";
/*       */       }
/*  1029 */       for (int i = 0; i < menuVector.size(); i++) {
/*       */         
/*  1031 */         LookupObject lookupObject = (LookupObject)menuVector.elementAt(i);
/*       */         
/*  1033 */         if (lookupObject.getInactive())
/*       */         {
/*       */ 
/*       */           
/*  1037 */           if (!lookupObject.getName().equalsIgnoreCase(selectedOption)) {
/*       */             continue;
/*       */           }
/*       */         }
/*  1041 */         values[i + offset] = lookupObject.getName();
/*  1042 */         menuText[i + offset] = lookupObject.getName();
/*  1043 */         valuesA.add(lookupObject.getName());
/*  1044 */         menuTextA.add(lookupObject.getName());
/*       */         
/*       */         continue;
/*       */       } 
/*  1048 */       values = new String[valuesA.size()];
/*  1049 */       menuText = new String[valuesA.size()];
/*  1050 */       values = (String[])valuesA.toArray(values);
/*  1051 */       menuText = (String[])menuTextA.toArray(menuText);
/*       */ 
/*       */       
/*  1054 */       dropDown = new FormDropDownMenu(name, 
/*  1055 */           selectedOption, 
/*  1056 */           values, 
/*  1057 */           menuText, 
/*  1058 */           required);
/*       */     }
/*       */     else {
/*       */       
/*  1062 */       dropDown = new FormDropDownMenu(name, "", "", "", required);
/*       */     } 
/*       */     
/*  1065 */     return dropDown;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static FormDropDownMenu getPfmLookupDropDown(String name, Vector menuVector, String selectedOption, boolean required, boolean blankFirst) {
/*  1088 */     FormDropDownMenu dropDown = null;
/*       */     
/*  1090 */     if (menuVector != null) {
/*       */       
/*  1092 */       int size = menuVector.size();
/*  1093 */       int offset = 0;
/*       */ 
/*       */ 
/*       */       
/*  1097 */       ArrayList valuesA = new ArrayList();
/*  1098 */       ArrayList menuTextA = new ArrayList();
/*       */ 
/*       */       
/*  1101 */       if (blankFirst) {
/*       */         
/*  1103 */         size++;
/*  1104 */         offset = 1;
/*       */       } 
/*       */ 
/*       */       
/*  1108 */       String[] values = new String[size];
/*  1109 */       String[] menuText = new String[size];
/*       */ 
/*       */       
/*  1112 */       if (blankFirst) {
/*       */         
/*  1114 */         values[0] = "-1";
/*  1115 */         menuText[0] = "&nbsp;";
/*  1116 */         valuesA.add("-1");
/*  1117 */         menuTextA.add("&nbsp;");
/*       */       } 
/*       */ 
/*       */       
/*  1121 */       if (selectedOption == null) {
/*  1122 */         selectedOption = "";
/*       */       }
/*  1124 */       for (int i = 0; i < menuVector.size(); i++) {
/*       */         
/*  1126 */         LookupObject lookupObject = (LookupObject)menuVector.elementAt(i);
/*  1127 */         String temporaryHold = lookupObject.getName();
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  1133 */         if (lookupObject.getInactive())
/*       */         {
/*       */ 
/*       */           
/*  1137 */           if (!lookupObject.getAbbreviation().equalsIgnoreCase(selectedOption)) {
/*       */             continue;
/*       */           }
/*       */         }
/*       */         
/*  1142 */         values[i + offset] = lookupObject.getAbbreviation();
/*  1143 */         menuText[i + offset] = String.valueOf(lookupObject.getAbbreviation()) + ":" + temporaryHold;
/*  1144 */         valuesA.add(lookupObject.getAbbreviation());
/*  1145 */         menuTextA.add(String.valueOf(lookupObject.getAbbreviation()) + ":" + temporaryHold);
/*       */         
/*       */         continue;
/*       */       } 
/*       */       
/*  1150 */       values = new String[valuesA.size()];
/*  1151 */       menuText = new String[valuesA.size()];
/*  1152 */       values = (String[])valuesA.toArray(values);
/*  1153 */       menuText = (String[])menuTextA.toArray(menuText);
/*       */ 
/*       */       
/*  1156 */       dropDown = new FormDropDownMenu(name, 
/*  1157 */           selectedOption, 
/*  1158 */           values, 
/*  1159 */           menuText, 
/*  1160 */           required);
/*       */     }
/*       */     else {
/*       */       
/*  1164 */       dropDown = new FormDropDownMenu(name, "", "", "", required);
/*       */     } 
/*       */     
/*  1167 */     return dropDown;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static FormDropDownMenu getPrefixCodeDropDown(int userId, String name, Vector prefixVector, String selectedOption, boolean required, Context context) {
/*  1303 */     Vector values = new Vector();
/*  1304 */     Vector menuText = new Vector();
/*       */ 
/*       */     
/*  1307 */     ArrayList valuesA = new ArrayList();
/*  1308 */     ArrayList menuTextA = new ArrayList();
/*       */     
/*  1310 */     values.addElement("-1");
/*  1311 */     menuText.addElement("&nbsp;&nbsp;");
/*       */     
/*  1313 */     valuesA.add("-1");
/*  1314 */     menuTextA.add("&nbsp;&nbsp;");
/*       */     
/*  1316 */     FormDropDownMenu dropDown = null;
/*       */     
/*  1318 */     if (selectedOption == null) {
/*  1319 */       selectedOption = "";
/*       */     }
/*       */ 
/*       */ 
/*       */     
/*  1324 */     Vector userEnvironments = getUserEnvironments(context);
/*       */     
/*  1326 */     if (userEnvironments != null) {
/*       */       
/*  1328 */       for (int i = 0; i < userEnvironments.size(); i++) {
/*       */ 
/*       */         
/*  1331 */         Environment env = (Environment)userEnvironments.elementAt(i);
/*       */         
/*  1333 */         if (env != null) {
/*       */           
/*  1335 */           int envID = env.getStructureID();
/*  1336 */           Family family = env.getParentFamily();
/*  1337 */           String familyName = "";
/*  1338 */           if (family != null) {
/*  1339 */             familyName = family.getStructureAbbreviation();
/*       */           }
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*  1345 */           if (!familyName.equalsIgnoreCase("UML") && 
/*  1346 */             !familyName.equalsIgnoreCase("Enterprise"))
/*       */           {
/*       */ 
/*       */ 
/*       */ 
/*       */             
/*  1352 */             if (prefixVector != null)
/*       */             {
/*  1354 */               for (int j = 0; j < prefixVector.size(); j++) {
/*       */                 
/*  1356 */                 PrefixCode prefixCode = (PrefixCode)prefixVector.elementAt(j);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */                 
/*  1363 */                 if (prefixCode.getPrefixCodeSubValue() == envID) {
/*       */ 
/*       */                   
/*  1366 */                   if (prefixCode.getInactive())
/*       */                   {
/*       */ 
/*       */                     
/*  1370 */                     if (!prefixCode.getAbbreviation().equalsIgnoreCase(selectedOption)) {
/*       */                       continue;
/*       */                     }
/*       */                   }
/*       */                   
/*  1375 */                   values.addElement(prefixCode.getAbbreviation());
/*  1376 */                   menuText.addElement(prefixCode.getAbbreviation());
/*  1377 */                   valuesA.add(prefixCode.getAbbreviation());
/*  1378 */                   menuTextA.add(prefixCode.getAbbreviation());
/*       */                 } 
/*       */                 
/*       */                 continue;
/*       */               } 
/*       */             }
/*       */           }
/*       */         } 
/*       */       } 
/*       */       
/*  1388 */       String[] arrayValues = new String[valuesA.size()];
/*  1389 */       String[] arrayMenuText = new String[menuTextA.size()];
/*       */       
/*  1391 */       arrayValues = (String[])valuesA.toArray(arrayValues);
/*  1392 */       arrayMenuText = (String[])menuTextA.toArray(arrayMenuText);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  1398 */       String[] arrayCombined = new String[valuesA.size()];
/*  1399 */       for (int c = 0; c < arrayCombined.length; c++) {
/*  1400 */         arrayCombined[c] = String.valueOf(arrayValues[c]) + "||" + arrayMenuText[c];
/*       */       }
/*       */       
/*  1403 */       Arrays.sort(arrayCombined, new StringComparator());
/*       */ 
/*       */       
/*  1406 */       String[] sortedArrayValues = new String[valuesA.size()];
/*  1407 */       String[] sortedArrayMenuText = new String[menuTextA.size()];
/*  1408 */       String concatenatedValueText = "";
/*  1409 */       int symbolPosition = 0;
/*  1410 */       for (int d = 0; d < arrayCombined.length; d++) {
/*  1411 */         concatenatedValueText = arrayCombined[d];
/*  1412 */         symbolPosition = concatenatedValueText.indexOf("||");
/*  1413 */         sortedArrayValues[d] = concatenatedValueText.substring(0, symbolPosition);
/*  1414 */         sortedArrayMenuText[d] = concatenatedValueText.substring(symbolPosition + 2, concatenatedValueText.length());
/*       */       } 
/*       */       
/*  1417 */       dropDown = new FormDropDownMenu(name, 
/*  1418 */           selectedOption, 
/*  1419 */           sortedArrayValues, 
/*  1420 */           sortedArrayMenuText, 
/*  1421 */           required);
/*       */     } 
/*       */ 
/*       */     
/*  1425 */     return dropDown;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static FormDropDownMenu getPreparedByDropDown(Context context, String name, Vector labelUsers, String userName, boolean required) {
/*  1442 */     boolean selectedUserFound = false;
/*       */     
/*  1444 */     Vector values = new Vector();
/*  1445 */     Vector menuText = new Vector();
/*       */ 
/*       */     
/*  1448 */     values.addElement("0");
/*  1449 */     menuText.addElement("&nbsp;&nbsp;");
/*       */     
/*  1451 */     FormDropDownMenu dropdown = null;
/*       */     
/*  1453 */     if (labelUsers != null && labelUsers.size() > 0) {
/*       */       
/*  1455 */       for (int i = 0; i < labelUsers.size(); i++) {
/*       */         
/*  1457 */         User labelContactUser = (User)labelUsers.get(i);
/*       */         
/*  1459 */         if (labelContactUser != null) {
/*       */           
/*  1461 */           String labelContactName = labelContactUser.getName();
/*  1462 */           if (labelContactName.trim().equalsIgnoreCase(userName.trim()))
/*       */           {
/*  1464 */             selectedUserFound = true;
/*       */           }
/*       */           
/*  1467 */           values.addElement(labelContactName);
/*  1468 */           menuText.addElement(labelContactName);
/*       */         } 
/*       */       } 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  1477 */       if (!selectedUserFound && !userName.equals("")) {
/*       */         
/*  1479 */         values.addElement(userName);
/*  1480 */         menuText.addElement(userName);
/*       */       } 
/*       */       
/*  1483 */       String[] arrayValues = new String[values.size()];
/*  1484 */       String[] arrayMenuText = new String[menuText.size()];
/*       */ 
/*       */       
/*  1487 */       arrayValues = (String[])values.toArray(arrayValues);
/*  1488 */       arrayMenuText = (String[])menuText.toArray(arrayMenuText);
/*       */       
/*  1490 */       dropdown = new FormDropDownMenu(name, 
/*  1491 */           userName, 
/*  1492 */           arrayValues, 
/*  1493 */           arrayMenuText, 
/*  1494 */           required);
/*       */ 
/*       */     
/*       */     }
/*  1498 */     else if (!userName.equals("")) {
/*       */       
/*  1500 */       values.addElement(userName);
/*  1501 */       menuText.addElement(userName);
/*       */       
/*  1503 */       String[] arrayValues = new String[values.size()];
/*  1504 */       String[] arrayMenuText = new String[menuText.size()];
/*       */ 
/*       */       
/*  1507 */       arrayValues = (String[])values.toArray(arrayValues);
/*  1508 */       arrayMenuText = (String[])menuText.toArray(arrayMenuText);
/*       */       
/*  1510 */       dropdown = new FormDropDownMenu(name, 
/*  1511 */           userName, 
/*  1512 */           arrayValues, 
/*  1513 */           arrayMenuText, 
/*  1514 */           required);
/*       */ 
/*       */     
/*       */     }
/*       */     else {
/*       */ 
/*       */ 
/*       */       
/*  1522 */       User user = MilestoneSecurity.getUser(context);
/*       */       
/*  1524 */       values.addElement(user.getName());
/*  1525 */       menuText.addElement(user.getName());
/*       */       
/*  1527 */       String[] arrayValues = new String[values.size()];
/*  1528 */       String[] arrayMenuText = new String[menuText.size()];
/*       */ 
/*       */       
/*  1531 */       arrayValues = (String[])values.toArray(arrayValues);
/*  1532 */       arrayMenuText = (String[])menuText.toArray(arrayMenuText);
/*       */       
/*  1534 */       dropdown = new FormDropDownMenu(name, 
/*  1535 */           userName, 
/*  1536 */           arrayValues, 
/*  1537 */           arrayMenuText, 
/*  1538 */           required);
/*       */     } 
/*       */ 
/*       */     
/*  1542 */     return dropdown;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static FormDropDownMenu getCorporateStructureDropDown(String name, Vector corporateVector, String selectedOption, boolean required, boolean blankFirst) {
/*  1569 */     Vector values = new Vector();
/*  1570 */     Vector menuText = new Vector();
/*       */     
/*  1572 */     FormDropDownMenu dropdown = null;
/*  1573 */     boolean selectedFound = false;
/*       */ 
/*       */     
/*  1576 */     HashMap corpHashMap = buildActiveCorporateStructureHashMap();
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  1581 */     int selectionInt = 0;
/*       */     
/*       */     try {
/*  1584 */       selectionInt = Integer.parseInt(selectedOption);
/*       */     }
/*  1586 */     catch (NumberFormatException numberFormatException) {}
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  1591 */     if (corporateVector != null) {
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  1596 */       Vector sortedVector = new Vector();
/*  1597 */       sortedVector = sortCorporateVectorByName(corporateVector);
/*       */ 
/*       */       
/*  1600 */       if (blankFirst)
/*       */       {
/*  1602 */         if (name.equalsIgnoreCase("family")) {
/*       */           
/*  1604 */           values.addElement("0");
/*  1605 */           menuText.addElement("All");
/*       */         }
/*       */         else {
/*       */           
/*  1609 */           values.addElement("0");
/*       */           
/*  1611 */           menuText.addElement(" All");
/*       */         } 
/*       */       }
/*       */       
/*  1615 */       CorporateStructureObject cso = null;
/*       */       
/*  1617 */       for (int i = 0; i < sortedVector.size(); i++) {
/*       */         
/*  1619 */         cso = (CorporateStructureObject)sortedVector.elementAt(i);
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  1624 */         if (cso != null && !corpHashMap.containsKey(new Integer(cso.getStructureID()))) {
/*       */ 
/*       */           
/*  1627 */           values.addElement(cso.getStructureID());
/*  1628 */           menuText.addElement(cso.getName());
/*       */           
/*  1630 */           if (cso.getStructureID() == selectionInt) {
/*  1631 */             selectedFound = true;
/*       */           }
/*       */         } 
/*       */       } 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  1639 */       if (!selectedFound && selectedOption != null) {
/*       */         
/*  1641 */         CorporateStructureObject csoSelected = 
/*  1642 */           (CorporateStructureObject)getStructureObject(selectionInt);
/*       */         
/*  1644 */         if (csoSelected != null) {
/*       */           
/*  1646 */           values.addElement(csoSelected.getStructureID());
/*  1647 */           menuText.addElement(csoSelected.getName());
/*       */         } 
/*       */       } 
/*       */       
/*  1651 */       String[] arrayValues = new String[values.size()];
/*  1652 */       String[] arrayMenuText = new String[menuText.size()];
/*       */ 
/*       */       
/*  1655 */       arrayValues = (String[])values.toArray(arrayValues);
/*  1656 */       arrayMenuText = (String[])menuText.toArray(arrayMenuText);
/*       */       
/*  1658 */       dropdown = new FormDropDownMenu(name, 
/*  1659 */           selectedOption, 
/*  1660 */           arrayValues, 
/*  1661 */           arrayMenuText, 
/*  1662 */           required);
/*       */     }
/*       */     else {
/*       */       
/*  1666 */       dropdown = new FormDropDownMenu(name, "", "", required);
/*       */     } 
/*       */     
/*  1669 */     corpHashMap = null;
/*       */     
/*  1671 */     return dropdown;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static FormDropDownMenu getCorporateStructureDropDownDuplicates(String name, Vector corporateVector, String selectedOption, boolean required, boolean blankFirst, boolean activesOnly) {
/*  1698 */     Vector values = new Vector();
/*       */     
/*  1700 */     MilestoneVector menuText = new MilestoneVector();
/*       */     
/*  1702 */     FormDropDownMenu dropdown = null;
/*  1703 */     boolean selectedFound = false;
/*       */ 
/*       */     
/*  1706 */     HashMap corpHashMap = buildActiveCorporateStructureHashMap();
/*       */ 
/*       */ 
/*       */     
/*  1710 */     int selectionInt = 0;
/*       */     
/*       */     try {
/*  1713 */       selectionInt = Integer.parseInt(selectedOption);
/*       */     }
/*  1715 */     catch (NumberFormatException numberFormatException) {}
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  1720 */     if (corporateVector != null) {
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  1725 */       Vector sortedVector = new Vector();
/*  1726 */       sortedVector = sortCorporateVectorByName(corporateVector);
/*       */ 
/*       */       
/*  1729 */       if (blankFirst)
/*       */       {
/*  1731 */         if (name.equalsIgnoreCase("family")) {
/*       */           
/*  1733 */           values.addElement("0");
/*  1734 */           menuText.addElement("All");
/*       */         }
/*  1736 */         else if (name.equalsIgnoreCase("labels")) {
/*       */           
/*  1738 */           values.addElement("0");
/*  1739 */           menuText.addElement(" ");
/*       */         }
/*       */         else {
/*       */           
/*  1743 */           values.addElement("0");
/*       */           
/*  1745 */           menuText.addElement(" All");
/*       */         } 
/*       */       }
/*       */       
/*  1749 */       CorporateStructureObject cso = null;
/*       */       
/*  1751 */       for (int i = 0; i < sortedVector.size(); i++) {
/*       */         
/*  1753 */         cso = (CorporateStructureObject)sortedVector.elementAt(i);
/*       */ 
/*       */         
/*  1756 */         if (activesOnly) {
/*       */ 
/*       */           
/*  1759 */           if (cso instanceof Label) {
/*  1760 */             Label csoLabel = (Label)cso;
/*  1761 */             if (!csoLabel.getActive()) {
/*       */               continue;
/*       */             }
/*       */           } 
/*  1765 */           if (cso instanceof Division) {
/*  1766 */             Division csoDiv = (Division)cso;
/*  1767 */             if (!csoDiv.getActive()) {
/*       */               continue;
/*       */             }
/*       */           } 
/*  1771 */           if (cso instanceof Company) {
/*  1772 */             Company csoCo = (Company)cso;
/*  1773 */             if (!csoCo.getActive()) {
/*       */               continue;
/*       */             }
/*       */           } 
/*  1777 */           if (cso instanceof Environment) {
/*  1778 */             Environment csoEnv = (Environment)cso;
/*  1779 */             if (!csoEnv.getActive()) {
/*       */               continue;
/*       */             }
/*       */           } 
/*  1783 */           if (cso instanceof Family) {
/*  1784 */             Family csoFam = (Family)cso;
/*  1785 */             if (!csoFam.getActive()) {
/*       */               continue;
/*       */             }
/*       */           } 
/*       */         } 
/*       */ 
/*       */         
/*  1792 */         if (cso != null && !corpHashMap.containsKey(new Integer(cso.getStructureID())))
/*       */         {
/*       */ 
/*       */ 
/*       */           
/*  1797 */           if (!menuText.contains(cso.getName())) {
/*       */             
/*  1799 */             values.addElement(cso.getStructureID());
/*  1800 */             menuText.addElement(cso.getName());
/*       */             
/*  1802 */             if (cso.getStructureID() == selectionInt) {
/*  1803 */               selectedFound = true;
/*       */             }
/*       */           }
/*       */           else {
/*       */             
/*  1808 */             int index = menuText.indexOf(cso.getName());
/*  1809 */             if (index != -1) {
/*       */               
/*  1811 */               String value = (String)values.get(index);
/*  1812 */               values.set(index, String.valueOf(value) + "," + cso.getStructureID());
/*       */               
/*  1814 */               if (cso.getStructureID() == selectionInt) {
/*  1815 */                 selectedFound = true;
/*       */               }
/*       */             } 
/*       */           } 
/*       */         }
/*       */ 
/*       */         
/*       */         continue;
/*       */       } 
/*       */       
/*  1825 */       if (!selectedFound && selectedOption != null) {
/*       */         
/*  1827 */         CorporateStructureObject csoSelected = 
/*  1828 */           (CorporateStructureObject)getStructureObject(selectionInt);
/*       */         
/*  1830 */         if (csoSelected != null) {
/*       */           
/*  1832 */           values.addElement(csoSelected.getStructureID());
/*  1833 */           menuText.addElement(csoSelected.getName());
/*       */         } 
/*       */       } 
/*       */       
/*  1837 */       String[] arrayValues = new String[values.size()];
/*  1838 */       String[] arrayMenuText = new String[menuText.size()];
/*       */ 
/*       */       
/*  1841 */       arrayValues = (String[])values.toArray(arrayValues);
/*  1842 */       arrayMenuText = (String[])menuText.toArray(arrayMenuText);
/*       */       
/*  1844 */       dropdown = new FormDropDownMenu(name, 
/*  1845 */           selectedOption, 
/*  1846 */           arrayValues, 
/*  1847 */           arrayMenuText, 
/*  1848 */           required);
/*       */     }
/*       */     else {
/*       */       
/*  1852 */       dropdown = new FormDropDownMenu(name, "", "", required);
/*       */     } 
/*       */     
/*  1855 */     corpHashMap = null;
/*       */     
/*  1857 */     return dropdown;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static FormDropDownMenu getContactsDropDown(Context context, String name, Vector labelUsers, User selectedUser, boolean required) { // Byte code:
/*       */     //   0: iconst_m1
/*       */     //   1: istore #5
/*       */     //   3: aload_3
/*       */     //   4: ifnull -> 13
/*       */     //   7: aload_3
/*       */     //   8: invokevirtual getUserId : ()I
/*       */     //   11: istore #5
/*       */     //   13: iconst_0
/*       */     //   14: istore #6
/*       */     //   16: new java/util/Vector
/*       */     //   19: dup
/*       */     //   20: invokespecial <init> : ()V
/*       */     //   23: astore #7
/*       */     //   25: new java/util/Vector
/*       */     //   28: dup
/*       */     //   29: invokespecial <init> : ()V
/*       */     //   32: astore #8
/*       */     //   34: aload #7
/*       */     //   36: ldc_w '0'
/*       */     //   39: invokevirtual addElement : (Ljava/lang/Object;)V
/*       */     //   42: aload #8
/*       */     //   44: ldc_w 'All'
/*       */     //   47: invokevirtual addElement : (Ljava/lang/Object;)V
/*       */     //   50: aconst_null
/*       */     //   51: astore #9
/*       */     //   53: aload_2
/*       */     //   54: ifnull -> 265
/*       */     //   57: aload_2
/*       */     //   58: invokevirtual size : ()I
/*       */     //   61: ifle -> 265
/*       */     //   64: iconst_0
/*       */     //   65: istore #10
/*       */     //   67: goto -> 140
/*       */     //   70: aload_2
/*       */     //   71: iload #10
/*       */     //   73: invokevirtual get : (I)Ljava/lang/Object;
/*       */     //   76: checkcast com/universal/milestone/User
/*       */     //   79: astore #11
/*       */     //   81: aload #11
/*       */     //   83: ifnull -> 137
/*       */     //   86: aload #11
/*       */     //   88: invokevirtual getName : ()Ljava/lang/String;
/*       */     //   91: astore #12
/*       */     //   93: aload #11
/*       */     //   95: invokevirtual getUserId : ()I
/*       */     //   98: istore #13
/*       */     //   100: iload #13
/*       */     //   102: iload #5
/*       */     //   104: if_icmpne -> 110
/*       */     //   107: iconst_1
/*       */     //   108: istore #6
/*       */     //   110: aload #7
/*       */     //   112: new java/lang/StringBuilder
/*       */     //   115: dup
/*       */     //   116: invokespecial <init> : ()V
/*       */     //   119: iload #13
/*       */     //   121: invokevirtual append : (I)Ljava/lang/StringBuilder;
/*       */     //   124: invokevirtual toString : ()Ljava/lang/String;
/*       */     //   127: invokevirtual addElement : (Ljava/lang/Object;)V
/*       */     //   130: aload #8
/*       */     //   132: aload #12
/*       */     //   134: invokevirtual addElement : (Ljava/lang/Object;)V
/*       */     //   137: iinc #10, 1
/*       */     //   140: iload #10
/*       */     //   142: aload_2
/*       */     //   143: invokevirtual size : ()I
/*       */     //   146: if_icmplt -> 70
/*       */     //   149: iload #6
/*       */     //   151: ifne -> 187
/*       */     //   154: aload_3
/*       */     //   155: ifnull -> 187
/*       */     //   158: aload #7
/*       */     //   160: new java/lang/StringBuilder
/*       */     //   163: dup
/*       */     //   164: invokespecial <init> : ()V
/*       */     //   167: iload #5
/*       */     //   169: invokevirtual append : (I)Ljava/lang/StringBuilder;
/*       */     //   172: invokevirtual toString : ()Ljava/lang/String;
/*       */     //   175: invokevirtual addElement : (Ljava/lang/Object;)V
/*       */     //   178: aload #8
/*       */     //   180: aload_3
/*       */     //   181: invokevirtual getName : ()Ljava/lang/String;
/*       */     //   184: invokevirtual addElement : (Ljava/lang/Object;)V
/*       */     //   187: aload #7
/*       */     //   189: invokevirtual size : ()I
/*       */     //   192: anewarray java/lang/String
/*       */     //   195: astore #10
/*       */     //   197: aload #8
/*       */     //   199: invokevirtual size : ()I
/*       */     //   202: anewarray java/lang/String
/*       */     //   205: astore #11
/*       */     //   207: aload #7
/*       */     //   209: aload #10
/*       */     //   211: invokevirtual toArray : ([Ljava/lang/Object;)[Ljava/lang/Object;
/*       */     //   214: checkcast [Ljava/lang/String;
/*       */     //   217: astore #10
/*       */     //   219: aload #8
/*       */     //   221: aload #11
/*       */     //   223: invokevirtual toArray : ([Ljava/lang/Object;)[Ljava/lang/Object;
/*       */     //   226: checkcast [Ljava/lang/String;
/*       */     //   229: astore #11
/*       */     //   231: new com/techempower/gemini/FormDropDownMenu
/*       */     //   234: dup
/*       */     //   235: aload_1
/*       */     //   236: new java/lang/StringBuilder
/*       */     //   239: dup
/*       */     //   240: invokespecial <init> : ()V
/*       */     //   243: iload #5
/*       */     //   245: invokevirtual append : (I)Ljava/lang/StringBuilder;
/*       */     //   248: invokevirtual toString : ()Ljava/lang/String;
/*       */     //   251: aload #10
/*       */     //   253: aload #11
/*       */     //   255: iload #4
/*       */     //   257: invokespecial <init> : (Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;[Ljava/lang/String;Z)V
/*       */     //   260: astore #9
/*       */     //   262: goto -> 490
/*       */     //   265: aload_3
/*       */     //   266: ifnull -> 376
/*       */     //   269: aload #7
/*       */     //   271: new java/lang/StringBuilder
/*       */     //   274: dup
/*       */     //   275: invokespecial <init> : ()V
/*       */     //   278: iload #5
/*       */     //   280: invokevirtual append : (I)Ljava/lang/StringBuilder;
/*       */     //   283: invokevirtual toString : ()Ljava/lang/String;
/*       */     //   286: invokevirtual addElement : (Ljava/lang/Object;)V
/*       */     //   289: aload #8
/*       */     //   291: aload_3
/*       */     //   292: invokevirtual getName : ()Ljava/lang/String;
/*       */     //   295: invokevirtual addElement : (Ljava/lang/Object;)V
/*       */     //   298: aload #7
/*       */     //   300: invokevirtual size : ()I
/*       */     //   303: anewarray java/lang/String
/*       */     //   306: astore #10
/*       */     //   308: aload #8
/*       */     //   310: invokevirtual size : ()I
/*       */     //   313: anewarray java/lang/String
/*       */     //   316: astore #11
/*       */     //   318: aload #7
/*       */     //   320: aload #10
/*       */     //   322: invokevirtual toArray : ([Ljava/lang/Object;)[Ljava/lang/Object;
/*       */     //   325: checkcast [Ljava/lang/String;
/*       */     //   328: astore #10
/*       */     //   330: aload #8
/*       */     //   332: aload #11
/*       */     //   334: invokevirtual toArray : ([Ljava/lang/Object;)[Ljava/lang/Object;
/*       */     //   337: checkcast [Ljava/lang/String;
/*       */     //   340: astore #11
/*       */     //   342: new com/techempower/gemini/FormDropDownMenu
/*       */     //   345: dup
/*       */     //   346: aload_1
/*       */     //   347: new java/lang/StringBuilder
/*       */     //   350: dup
/*       */     //   351: invokespecial <init> : ()V
/*       */     //   354: iload #5
/*       */     //   356: invokevirtual append : (I)Ljava/lang/StringBuilder;
/*       */     //   359: invokevirtual toString : ()Ljava/lang/String;
/*       */     //   362: aload #10
/*       */     //   364: aload #11
/*       */     //   366: iload #4
/*       */     //   368: invokespecial <init> : (Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;[Ljava/lang/String;Z)V
/*       */     //   371: astore #9
/*       */     //   373: goto -> 490
/*       */     //   376: aload_0
/*       */     //   377: invokestatic getUser : (Lcom/techempower/gemini/Context;)Lcom/universal/milestone/User;
/*       */     //   380: astore #10
/*       */     //   382: aload #7
/*       */     //   384: new java/lang/StringBuilder
/*       */     //   387: dup
/*       */     //   388: invokespecial <init> : ()V
/*       */     //   391: aload #10
/*       */     //   393: invokevirtual getUserId : ()I
/*       */     //   396: invokevirtual append : (I)Ljava/lang/StringBuilder;
/*       */     //   399: invokevirtual toString : ()Ljava/lang/String;
/*       */     //   402: invokevirtual addElement : (Ljava/lang/Object;)V
/*       */     //   405: aload #8
/*       */     //   407: aload #10
/*       */     //   409: invokevirtual getName : ()Ljava/lang/String;
/*       */     //   412: invokevirtual addElement : (Ljava/lang/Object;)V
/*       */     //   415: aload #7
/*       */     //   417: invokevirtual size : ()I
/*       */     //   420: anewarray java/lang/String
/*       */     //   423: astore #11
/*       */     //   425: aload #8
/*       */     //   427: invokevirtual size : ()I
/*       */     //   430: anewarray java/lang/String
/*       */     //   433: astore #12
/*       */     //   435: aload #7
/*       */     //   437: aload #11
/*       */     //   439: invokevirtual toArray : ([Ljava/lang/Object;)[Ljava/lang/Object;
/*       */     //   442: checkcast [Ljava/lang/String;
/*       */     //   445: astore #11
/*       */     //   447: aload #8
/*       */     //   449: aload #12
/*       */     //   451: invokevirtual toArray : ([Ljava/lang/Object;)[Ljava/lang/Object;
/*       */     //   454: checkcast [Ljava/lang/String;
/*       */     //   457: astore #12
/*       */     //   459: new com/techempower/gemini/FormDropDownMenu
/*       */     //   462: dup
/*       */     //   463: aload_1
/*       */     //   464: new java/lang/StringBuilder
/*       */     //   467: dup
/*       */     //   468: invokespecial <init> : ()V
/*       */     //   471: iload #5
/*       */     //   473: invokevirtual append : (I)Ljava/lang/StringBuilder;
/*       */     //   476: invokevirtual toString : ()Ljava/lang/String;
/*       */     //   479: aload #11
/*       */     //   481: aload #12
/*       */     //   483: iload #4
/*       */     //   485: invokespecial <init> : (Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;[Ljava/lang/String;Z)V
/*       */     //   488: astore #9
/*       */     //   490: aload #9
/*       */     //   492: areturn
/*       */     // Line number table:
/*       */     //   Java source line number -> byte code offset
/*       */     //   #1878	-> 0
/*       */     //   #1880	-> 3
/*       */     //   #1881	-> 7
/*       */     //   #1883	-> 13
/*       */     //   #1885	-> 16
/*       */     //   #1886	-> 25
/*       */     //   #1889	-> 34
/*       */     //   #1890	-> 42
/*       */     //   #1892	-> 50
/*       */     //   #1894	-> 53
/*       */     //   #1896	-> 64
/*       */     //   #1898	-> 70
/*       */     //   #1900	-> 81
/*       */     //   #1902	-> 86
/*       */     //   #1903	-> 93
/*       */     //   #1904	-> 100
/*       */     //   #1905	-> 107
/*       */     //   #1907	-> 110
/*       */     //   #1908	-> 130
/*       */     //   #1896	-> 137
/*       */     //   #1916	-> 149
/*       */     //   #1918	-> 158
/*       */     //   #1920	-> 178
/*       */     //   #1923	-> 187
/*       */     //   #1924	-> 197
/*       */     //   #1927	-> 207
/*       */     //   #1928	-> 219
/*       */     //   #1930	-> 231
/*       */     //   #1931	-> 236
/*       */     //   #1932	-> 251
/*       */     //   #1933	-> 253
/*       */     //   #1934	-> 255
/*       */     //   #1930	-> 257
/*       */     //   #1935	-> 262
/*       */     //   #1938	-> 265
/*       */     //   #1940	-> 269
/*       */     //   #1941	-> 289
/*       */     //   #1943	-> 298
/*       */     //   #1944	-> 308
/*       */     //   #1947	-> 318
/*       */     //   #1948	-> 330
/*       */     //   #1950	-> 342
/*       */     //   #1951	-> 347
/*       */     //   #1952	-> 362
/*       */     //   #1953	-> 364
/*       */     //   #1954	-> 366
/*       */     //   #1950	-> 368
/*       */     //   #1955	-> 373
/*       */     //   #1962	-> 376
/*       */     //   #1964	-> 382
/*       */     //   #1965	-> 405
/*       */     //   #1967	-> 415
/*       */     //   #1968	-> 425
/*       */     //   #1971	-> 435
/*       */     //   #1972	-> 447
/*       */     //   #1974	-> 459
/*       */     //   #1975	-> 464
/*       */     //   #1976	-> 479
/*       */     //   #1977	-> 481
/*       */     //   #1978	-> 483
/*       */     //   #1974	-> 485
/*       */     //   #1982	-> 490
/*       */     // Local variable table:
/*       */     //   start	length	slot	name	descriptor
/*       */     //   0	493	0	context	Lcom/techempower/gemini/Context;
/*       */     //   0	493	1	name	Ljava/lang/String;
/*       */     //   0	493	2	labelUsers	Ljava/util/Vector;
/*       */     //   0	493	3	selectedUser	Lcom/universal/milestone/User;
/*       */     //   0	493	4	required	Z
/*       */     //   3	490	5	selectedUserID	I
/*       */     //   16	477	6	selectedUserFound	Z
/*       */     //   25	468	7	values	Ljava/util/Vector;
/*       */     //   34	459	8	menuText	Ljava/util/Vector;
/*       */     //   53	440	9	dropdown	Lcom/techempower/gemini/FormDropDownMenu;
/*       */     //   67	82	10	i	I
/*       */     //   81	56	11	labelContactUser	Lcom/universal/milestone/User;
/*       */     //   93	44	12	labelContactName	Ljava/lang/String;
/*       */     //   100	37	13	labelContactID	I
/*       */     //   197	65	10	arrayValues	[Ljava/lang/String;
/*       */     //   207	55	11	arrayMenuText	[Ljava/lang/String;
/*       */     //   308	65	10	arrayValues	[Ljava/lang/String;
/*       */     //   318	55	11	arrayMenuText	[Ljava/lang/String;
/*       */     //   382	108	10	user	Lcom/universal/milestone/User;
/*       */     //   425	65	11	arrayValues	[Ljava/lang/String;
/*       */     //   435	55	12	arrayMenuText	[Ljava/lang/String; }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Vector getNonSecureUserFamilies(Context context) {
/*  2029 */     theFamilyList = null;
/*       */     
/*  2031 */     Vector vUserEnvironments = getUserEnvironments(context);
/*  2032 */     Environment env = null;
/*  2033 */     Vector theFamilies = Cache.getFamilies();
/*  2034 */     Vector precache = new Vector();
/*  2035 */     Family family = null;
/*       */     
/*  2037 */     for (int i = 0; i < theFamilies.size(); i++) {
/*       */       
/*  2039 */       family = (Family)theFamilies.elementAt(i);
/*       */       
/*  2041 */       if (family != null)
/*       */       {
/*  2043 */         for (int j = 0; j < vUserEnvironments.size(); j++) {
/*       */           
/*  2045 */           env = (Environment)vUserEnvironments.elementAt(j);
/*  2046 */           if (env != null) {
/*       */             
/*  2048 */             Family parent = (Family)getStructureObject(env.getParentID());
/*  2049 */             if (parent != null && parent.getStructureID() == family.getStructureID()) {
/*       */               
/*  2051 */               precache.addElement(family);
/*       */               break;
/*       */             } 
/*  2054 */             env = null;
/*  2055 */             parent = null;
/*       */           } 
/*       */         } 
/*       */       }
/*  2059 */       family = null;
/*       */     } 
/*       */     
/*  2062 */     return precache;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Vector getNonSecureActiveUserFamilies(Context context) {
/*  2124 */     Vector theFamilyList = null;
/*       */     
/*  2126 */     Vector vUserEnvironments = getUserEnvironments(context);
/*  2127 */     Environment env = null;
/*  2128 */     Vector theFamilies = Cache.getFamilies();
/*  2129 */     Vector precache = new Vector();
/*  2130 */     Family family = null;
/*       */ 
/*       */     
/*  2133 */     HashMap corpHashMap = buildActiveCorporateStructureHashMap();
/*       */ 
/*       */ 
/*       */     
/*  2137 */     for (int i = 0; i < theFamilies.size(); i++) {
/*       */       
/*  2139 */       family = (Family)theFamilies.elementAt(i);
/*  2140 */       int structId = family.getStructureID();
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  2145 */       boolean boolVal = !corpHashMap.containsKey(new Integer(structId));
/*       */       
/*  2147 */       if (family != null && boolVal)
/*       */       {
/*  2149 */         for (int j = 0; j < vUserEnvironments.size(); j++) {
/*       */           
/*  2151 */           env = (Environment)vUserEnvironments.elementAt(j);
/*  2152 */           if (env != null) {
/*       */             
/*  2154 */             Family parent = (Family)getStructureObject(env.getParentID());
/*  2155 */             if (parent != null && parent.getStructureID() == family.getStructureID()) {
/*       */               
/*  2157 */               precache.addElement(family);
/*       */               break;
/*       */             } 
/*  2160 */             env = null;
/*  2161 */             parent = null;
/*       */           } 
/*       */         } 
/*       */       }
/*  2165 */       family = null;
/*       */     } 
/*       */     
/*  2168 */     theFamilyList = precache;
/*       */     
/*  2170 */     corpHashMap = null;
/*  2171 */     return theFamilyList;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Vector getSecureUserFamilies(User user, int area, Context context) {
/*  2238 */     theFamilyList = null;
/*       */     
/*  2240 */     Vector vUserEnvironments = getUserEnvironments(context);
/*  2241 */     Environment env = null;
/*  2242 */     Vector theFamilies = Cache.getFamilies();
/*  2243 */     Vector precache = new Vector();
/*  2244 */     Family family = null;
/*       */     
/*  2246 */     for (int i = 0; i < theFamilies.size(); i++) {
/*       */       
/*  2248 */       family = (Family)theFamilies.elementAt(i);
/*       */       
/*  2250 */       if (family != null)
/*       */       {
/*  2252 */         for (int j = 0; j < vUserEnvironments.size(); j++) {
/*       */           
/*  2254 */           env = (Environment)vUserEnvironments.elementAt(j);
/*  2255 */           if (env != null) {
/*       */             
/*  2257 */             Family parent = (Family)getStructureObject(env.getParentID());
/*       */             
/*  2259 */             if (parent != null && parent.getStructureID() == family.getStructureID()) {
/*       */               
/*  2261 */               Company company = (Company)env.getCompanies().get(0);
/*  2262 */               if (company != null) {
/*       */                 
/*  2264 */                 CompanyAcl companyAcl = getScreenPermissions(company, user);
/*  2265 */                 boolean editable = false;
/*       */ 
/*       */                 
/*  2268 */                 if (companyAcl != null) {
/*       */                   
/*  2270 */                   if (area == 0 && companyAcl.getAccessSchedule() > 1) {
/*  2271 */                     editable = true;
/*       */                   }
/*  2273 */                   if (area == 1 && companyAcl.getAccessTemplate() > 1) {
/*  2274 */                     editable = true;
/*       */                   }
/*  2276 */                   if (area == 2 && companyAcl.getAccessTask() > 1) {
/*  2277 */                     editable = true;
/*       */                   }
/*       */                 } 
/*  2280 */                 if (editable) {
/*       */                   
/*  2282 */                   precache.addElement(family);
/*       */                   
/*       */                   break;
/*       */                 } 
/*  2286 */                 companyAcl = null;
/*       */               } 
/*       */             } 
/*  2289 */             env = null;
/*  2290 */             parent = null;
/*       */           } 
/*       */         } 
/*       */       }
/*  2294 */       family = null;
/*       */     } 
/*       */     
/*  2297 */     return precache;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean getActiveCorporateStructure(int structureId) {
/*  2308 */     boolean flag = false;
/*  2309 */     String structureQuery = "SELECT  * FROM  vi_Structure WHERE  structure_id = " + 
/*       */ 
/*       */       
/*  2312 */       structureId;
/*       */     
/*  2314 */     JdbcConnector structureConnector = createConnector(structureQuery);
/*  2315 */     structureConnector.runQuery();
/*       */ 
/*       */     
/*  2318 */     while (structureConnector.more()) {
/*       */ 
/*       */       
/*  2321 */       int sortField = structureConnector.getIntegerField("sort");
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  2326 */       if (sortField == 0) flag = true;
/*       */ 
/*       */       
/*  2329 */       structureConnector.next();
/*       */     } 
/*       */     
/*  2332 */     structureConnector.close();
/*       */     
/*  2334 */     return flag;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*  2348 */   public static HashMap buildActiveCorporateStructureHashMap() { return new HashMap(); }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Vector getUserCompanies(int userID) {
/*  2408 */     String companyQuery = "sp_get_User_Companies " + userID;
/*  2409 */     Vector precache = new Vector();
/*  2410 */     Company company = null;
/*       */     
/*  2412 */     JdbcConnector companyConnector = createConnector(companyQuery);
/*  2413 */     companyConnector.runQuery();
/*       */ 
/*       */     
/*  2416 */     while (companyConnector.more()) {
/*       */ 
/*       */       
/*  2419 */       company = (Company)getStructureObject(companyConnector.getIntegerField("structure_id"));
/*       */       
/*  2421 */       if (company != null)
/*  2422 */         precache.addElement(company); 
/*  2423 */       company = null;
/*  2424 */       companyConnector.next();
/*       */     } 
/*       */     
/*  2427 */     companyConnector.close();
/*       */     
/*  2429 */     return precache;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Vector getUserCompaniesExcludeUmlEnterprise(int userID) {
/*  2446 */     String companyQuery = "sp_get_User_Companies_Exclude " + userID;
/*       */     
/*  2448 */     Vector precache = new Vector();
/*  2449 */     Company company = null;
/*       */     
/*  2451 */     JdbcConnector companyConnector = createConnector(companyQuery);
/*  2452 */     companyConnector.runQuery();
/*       */ 
/*       */     
/*  2455 */     while (companyConnector.more()) {
/*       */ 
/*       */       
/*  2458 */       company = (Company)getStructureObject(companyConnector.getIntegerField("structure_id"));
/*       */ 
/*       */       
/*  2461 */       if (company != null)
/*  2462 */         precache.addElement(company); 
/*  2463 */       company = null;
/*  2464 */       companyConnector.next();
/*       */     } 
/*       */     
/*  2467 */     companyConnector.close();
/*       */     
/*  2469 */     return precache;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Vector getUserCompanies(Context context) {
/*  2484 */     User user = (User)context.getSessionValue("user");
/*  2485 */     int userId = user.getUserId();
/*       */ 
/*       */ 
/*       */     
/*  2489 */     if (context.getSessionValue("user-companies") != null && !((Vector)context.getSessionValue("user-companies")).isEmpty())
/*       */     {
/*       */ 
/*       */       
/*  2493 */       return (Vector)context.getSessionValue("user-companies");
/*       */     }
/*       */ 
/*       */ 
/*       */     
/*  2498 */     Vector userCompanies = getUserCompanies(userId);
/*       */     
/*  2500 */     if (userCompanies != null) {
/*  2501 */       context.putSessionValue("user-companies", userCompanies);
/*       */     }
/*  2503 */     return userCompanies;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Vector getUserCompaniesExcludeUmlEnterprise(Context context) {
/*  2520 */     User user = (User)context.getSessionValue("user");
/*  2521 */     int userId = user.getUserId();
/*       */     
/*  2523 */     return getUserCompaniesExcludeUmlEnterprise(userId);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*  2538 */   public static void clearUserCompaniesFromSession(Context context) { context.putSessionValue("user-companies", null); }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Vector getUserLabels(Context context) {
/*  2550 */     labels = new Vector();
/*  2551 */     return getUserLabels(getUserCompanies(context));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Vector getUserLabels(Vector companies) {
/*  2566 */     Vector userLabels = new Vector();
/*       */     
/*  2568 */     if (companies != null)
/*       */     {
/*  2570 */       for (int i = 0; i < companies.size(); i++) {
/*       */         
/*  2572 */         Company company = (Company)companies.get(i);
/*       */         
/*  2574 */         if (company.getDivisions() != null) {
/*       */           
/*  2576 */           Vector divisions = company.getDivisions();
/*       */           
/*  2578 */           for (int j = 0; j < divisions.size(); j++) {
/*       */             
/*  2580 */             Division division = (Division)divisions.get(j);
/*       */             
/*  2582 */             if (division.getLabels() != null) {
/*       */               
/*  2584 */               Vector labels = division.getLabels();
/*       */               
/*  2586 */               for (int k = 0; k < labels.size(); k++) {
/*       */                 
/*  2588 */                 Label label = (Label)labels.get(k);
/*  2589 */                 userLabels.add(label);
/*       */               } 
/*       */             } 
/*       */           } 
/*       */         } 
/*       */       } 
/*       */     }
/*       */     
/*  2597 */     return userLabels;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int getStructureId(String name, int structureType) {
/*  2610 */     Vector csoObjects = Cache.getCorporateStructure();
/*       */     
/*  2612 */     for (int i = 0; i < csoObjects.size(); i++) {
/*       */       
/*  2614 */       CorporateStructureObject cso = (CorporateStructureObject)csoObjects.elementAt(i);
/*  2615 */       if (name.equalsIgnoreCase(cso.getName().trim()) && 
/*  2616 */         cso.getStructureType() == structureType)
/*       */       {
/*  2618 */         return cso.getStructureID();
/*       */       }
/*       */     } 
/*       */     
/*  2622 */     return -1;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static String getStructureName(int id) {
/*  2694 */     CorporateStructureObject cso = (CorporateStructureObject)getStructureObject(id);
/*  2695 */     if (cso != null) {
/*  2696 */       return cso.getName();
/*       */     }
/*  2698 */     return "";
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Object getStructureObject(int id) {
/*  2767 */     Vector csoObjects = Cache.getCorporateStructure();
/*       */     
/*  2769 */     for (int i = 0; i < csoObjects.size(); i++) {
/*       */       
/*  2771 */       CorporateStructureObject cso = (CorporateStructureObject)csoObjects.elementAt(i);
/*       */       
/*  2773 */       if (cso.getStructureID() == id) {
/*  2774 */         return cso;
/*       */       }
/*       */     } 
/*  2777 */     return null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Vector getTaskAbbreviationList() {
/*  2905 */     query = "Select DISTINCT ld.description From vi_lookup_detail ld, vi_lookup_header lh with (nolock), vi_task tk with (nolock) Where convert(varchar(10),lh.field_id) = ld.field_id and convert(varchar(10),tk.abbrev_id) = ld.value and lh.field_name = 'task_abbrev'";
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  2911 */     Vector precache = null;
/*       */     
/*  2913 */     JdbcConnector connector = createConnector(query);
/*  2914 */     connector.runQuery();
/*       */     
/*  2916 */     if (connector.more())
/*       */     {
/*  2918 */       precache = new Vector();
/*       */     }
/*       */     
/*  2921 */     while (connector.more()) {
/*       */       
/*  2923 */       precache.add(new String(connector.getField("description")));
/*  2924 */       connector.next();
/*       */     } 
/*       */     
/*  2927 */     connector.close();
/*       */     
/*  2929 */     return precache;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static String getTaskAbbreviationDescription(int id) {
/*  2942 */     String query = "Select DISTINCT ld.description From vi_lookup_detail ld, vi_lookup_header lh with (nolock), vi_task tk  with (nolock) Where convert(varchar(10),lh.field_id) = ld.field_id and convert(varchar(10),tk.abbrev_id) = ld.value and ld.value = " + 
/*       */ 
/*       */ 
/*       */       
/*  2946 */       id + " and " + 
/*  2947 */       "lh.field_name = 'task_abbrev'";
/*       */     
/*  2949 */     JdbcConnector connector = createConnector(query);
/*  2950 */     connector.runQuery();
/*       */     
/*  2952 */     if (connector.more()) {
/*       */       
/*  2954 */       connector.close();
/*  2955 */       return connector.getField("description");
/*       */     } 
/*       */     
/*  2958 */     connector.close();
/*  2959 */     return null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static String getTaskAbbreivationNameById(int id) {
/*  2974 */     String name = "";
/*       */     
/*  2976 */     if (id > 0) {
/*       */       
/*  2978 */       Vector abbrevs = Cache.getTaskAbbreviations();
/*       */       
/*  2980 */       for (int i = 0; i < abbrevs.size(); i++) {
/*       */         
/*  2982 */         LookupObject taskAbbrev = (LookupObject)abbrevs.get(i);
/*       */         
/*  2984 */         if (taskAbbrev.getAbbreviation().equalsIgnoreCase(Integer.toString(id))) {
/*  2985 */           name = taskAbbrev.getName();
/*       */         }
/*       */       } 
/*       */     } 
/*       */ 
/*       */     
/*  2991 */     return name;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static int getTaskAbbreviationID(String description) {
/*  3006 */     String query = "Select DISTINCT ld.value From vi_lookup_detail ld with (nolock), vi_lookup_header lh with (nolock), vi_task tk with (nolock) Where convert(varchar(10),lh.field_id) = ld.field_id and convert(varchar(10),tk.abbrev_id) = ld.value and ld.description = '" + 
/*       */ 
/*       */ 
/*       */       
/*  3010 */       description + "' and " + 
/*  3011 */       "lh.field_name = 'task_abbrev'";
/*       */     
/*  3013 */     System.out.println("getTaskAbbreviationID: " + query);
/*  3014 */     JdbcConnector connector = createConnector(query);
/*  3015 */     connector.runQuery();
/*       */     
/*  3017 */     int abbrevId = -99;
/*  3018 */     if (connector.more())
/*       */     {
/*  3020 */       abbrevId = connector.getIntegerField("value");
/*       */     }
/*  3022 */     connector.close();
/*  3023 */     return abbrevId;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Vector getDepartmentList() {
/*  3030 */     query = "Select DISTINCT ld.description From vi_lookup_detail ld, vi_lookup_header lh with (nolock), vi_task tk with (nolock) Where convert(varchar(10),lh.field_id) = ld.field_id and lh.field_name = 'department'";
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  3035 */     Vector precache = null;
/*       */     
/*  3037 */     JdbcConnector connector = createConnector(query);
/*  3038 */     connector.runQuery();
/*       */     
/*  3040 */     if (connector.more())
/*       */     {
/*  3042 */       precache = new Vector();
/*       */     }
/*       */     
/*  3045 */     while (connector.more()) {
/*       */       
/*  3047 */       precache.add(new String(connector.getField("description")));
/*  3048 */       connector.next();
/*       */     } 
/*       */     
/*  3051 */     connector.close();
/*       */     
/*  3053 */     return precache;
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   public static String getDepartmentDescription(String value) {
/*  3059 */     String query = "Select DISTINCT ld.description From vi_lookup_detail ld, vi_lookup_header lh with (nolock), vi_task tk  with (nolock) Where convert(varchar(10),lh.field_id) = ld.field_id and ld.value = '" + 
/*       */ 
/*       */       
/*  3062 */       value + "' and " + 
/*  3063 */       "lh.field_name = 'department'";
/*       */     
/*  3065 */     JdbcConnector connector = createConnector(query);
/*  3066 */     connector.runQuery();
/*       */     
/*  3068 */     if (connector.more()) {
/*       */       
/*  3070 */       connector.close();
/*  3071 */       return connector.getField("description");
/*       */     } 
/*       */     
/*  3074 */     connector.close();
/*  3075 */     return null;
/*       */   }
/*       */ 
/*       */   
/*       */   public static int getDepartmentID(String value) {
/*  3080 */     String query = "Select DISTINCT ld.field_id From vi_lookup_detail ld, vi_lookup_header lh with (nolock), vi_task tk  with (nolock) Where convert(varchar(10),lh.field_id) = ld.field_id and ld.value = '" + 
/*       */ 
/*       */       
/*  3083 */       value + "' and " + 
/*  3084 */       "lh.field_name = 'department'";
/*       */     
/*  3086 */     JdbcConnector connector = createConnector(query);
/*  3087 */     connector.runQuery();
/*       */     
/*  3089 */     if (connector.more()) {
/*       */       
/*  3091 */       connector.close();
/*  3092 */       return connector.getIntegerField("field_id");
/*       */     } 
/*       */     
/*  3095 */     connector.close();
/*  3096 */     return -1;
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   public static String getDepartmentAbbreviation(String description) {
/*  3102 */     String query = "Select DISTINCT ld.description From vi_lookup_detail ld, vi_lookup_header lh with (nolock), vi_task tk with (nolock) Where convert(varchar(10),lh.field_id) = ld.field_id and convert(varchar(10),tk.abbrev_id) = ld.value and ld.description = " + 
/*       */ 
/*       */ 
/*       */       
/*  3106 */       description + " and " + 
/*  3107 */       "lh.field_name = 'department'";
/*       */     
/*  3109 */     JdbcConnector connector = createConnector(query);
/*  3110 */     connector.runQuery();
/*       */     
/*  3112 */     if (connector.more()) {
/*       */       
/*  3114 */       connector.close();
/*  3115 */       return connector.getField("value");
/*       */     } 
/*       */     
/*  3118 */     connector.close();
/*  3119 */     return null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Selection getScreenSelection(Context context) {
/*  3136 */     Selection selection = null;
/*  3137 */     int selectionID = -1;
/*  3138 */     Notepad notepad = getNotepadFromSession(0, context);
/*       */     
/*  3140 */     if (context.getRequestValue("selection-id") != null) {
/*       */       
/*  3142 */       selectionID = Integer.parseInt(context.getRequestValue("selection-id"));
/*       */ 
/*       */ 
/*       */       
/*  3146 */       selection = SelectionManager.getInstance().getSelectionHeader(selectionID);
/*  3147 */       notepad.setSelected(selection);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     }
/*  3154 */     else if ((Selection)notepad.getSelected() != null) {
/*       */       
/*  3156 */       Selection notepadSelection = (Selection)notepad.getSelected();
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  3163 */       if (notepadSelection.isFullSelection())
/*       */       {
/*       */         
/*  3166 */         selectionID = notepadSelection.getSelectionID();
/*  3167 */         selection = SelectionManager.getInstance().getSelectionHeader(selectionID);
/*       */       }
/*       */       else
/*       */       {
/*  3171 */         selectionID = notepadSelection.getSelectionID();
/*  3172 */         selection = SelectionManager.getInstance().getSelectionHeader(selectionID);
/*  3173 */         notepad.setSelected(selection);
/*       */ 
/*       */       
/*       */       }
/*       */ 
/*       */     
/*       */     }
/*  3180 */     else if (notepad.getAllContents() != null && notepad.getAllContents().size() > 0) {
/*       */       
/*  3182 */       selection = (Selection)notepad.getAllContents().get(0);
/*  3183 */       notepad.setSelected(selection);
/*       */     } 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  3189 */     return selection;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Task getScreenTask(Context context) {
/*  3209 */     Task task = null;
/*  3210 */     int taskID = -1;
/*  3211 */     Notepad notepad = getNotepadFromSession(4, context);
/*       */     
/*  3213 */     if (context.getRequestValue("task-id") != null) {
/*       */       
/*  3215 */       taskID = Integer.parseInt(context.getRequestValue("task-id"));
/*       */ 
/*       */ 
/*       */       
/*  3219 */       task = TaskManager.getInstance().getTask(taskID, true);
/*  3220 */       context.putSessionValue("Task", task);
/*  3221 */       notepad.setSelected(task);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     }
/*  3228 */     else if ((Task)notepad.getSelected() != null) {
/*       */       
/*  3230 */       Task notepadTask = (Task)notepad.getSelected();
/*  3231 */       taskID = notepadTask.getTaskID();
/*       */ 
/*       */       
/*  3234 */       if (notepadTask.getLastUpdatedCk() > 0L) {
/*       */         
/*  3236 */         task = TaskManager.getInstance().getTask(taskID, false);
/*       */ 
/*       */         
/*  3239 */         task.setLastUpdatedCk(notepadTask.getLastUpdatedCk());
/*       */       }
/*       */       else {
/*       */         
/*  3243 */         task = TaskManager.getInstance().getTask(taskID, true);
/*       */       } 
/*       */       
/*  3246 */       context.putSessionValue("Task", task);
/*       */ 
/*       */ 
/*       */     
/*       */     }
/*  3251 */     else if (notepad.getAllContents() != null && notepad.getAllContents().size() > 0) {
/*       */       
/*  3253 */       task = (Task)notepad.getAllContents().get(0);
/*  3254 */       notepad.setSelected(task);
/*       */     } 
/*       */ 
/*       */ 
/*       */     
/*  3259 */     return task;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static User getScreenUser(Context context) {
/*  3277 */     User user = null;
/*  3278 */     int userID = -1;
/*  3279 */     Notepad notepad = getNotepadFromSession(7, context);
/*       */     
/*  3281 */     if (context.getRequestValue("user-id") != null) {
/*       */       
/*  3283 */       userID = Integer.parseInt(context.getRequestValue("user-id"));
/*  3284 */       user = UserManager.getInstance().getUser(userID, true);
/*  3285 */       context.putSessionValue("securityUser", user);
/*  3286 */       notepad.setSelected(user);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     }
/*  3293 */     else if ((User)notepad.getSelected() != null) {
/*       */       
/*  3295 */       User notepadUser = (User)notepad.getSelected();
/*  3296 */       userID = notepadUser.getUserId();
/*       */ 
/*       */       
/*  3299 */       if (notepadUser.getLastUpdatedCk() > 0L) {
/*       */         
/*  3301 */         user = UserManager.getInstance().getUser(userID, false);
/*       */ 
/*       */         
/*  3304 */         user.setLastUpdatedCk(notepadUser.getLastUpdatedCk());
/*       */       }
/*       */       else {
/*       */         
/*  3308 */         user = UserManager.getInstance().getUser(userID, true);
/*       */       } 
/*       */       
/*  3311 */       context.putSessionValue("securityUser", user);
/*       */ 
/*       */ 
/*       */     
/*       */     }
/*  3316 */     else if (notepad.getAllContents() != null && notepad.getAllContents().size() > 0) {
/*       */       
/*  3318 */       user = (User)notepad.getAllContents().get(0);
/*  3319 */       context.putSessionValue("securityUser", user);
/*  3320 */       notepad.setSelected(user);
/*       */     } 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  3326 */     return user;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Company getScreenUserCompany(Context context) {
/*  3343 */     Company company = null;
/*  3344 */     int companyID = -1;
/*  3345 */     Notepad notepad = getNotepadFromSession(8, context);
/*       */     
/*  3347 */     if (context.getRequestValue("company-id") != null) {
/*       */ 
/*       */       
/*       */       try {
/*  3351 */         companyID = Integer.parseInt(context.getRequestValue("company-id"));
/*  3352 */         company = (Company)getStructureObject(companyID);
/*  3353 */         context.putSessionValue("UserCompany", company);
/*  3354 */         notepad.setSelected(company);
/*       */       }
/*  3356 */       catch (ClassCastException classCastException) {}
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     }
/*  3366 */     else if ((Company)notepad.getSelected() != null) {
/*       */ 
/*       */       
/*       */       try {
/*  3370 */         Company notepadCompany = (Company)notepad.getSelected();
/*  3371 */         companyID = notepadCompany.getStructureID();
/*  3372 */         company = (Company)getStructureObject(companyID);
/*  3373 */         context.putSessionValue("UserCompany", company);
/*       */       }
/*  3375 */       catch (ClassCastException classCastException) {}
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     }
/*  3383 */     else if (notepad.getAllContents() != null && notepad.getAllContents().size() > 0) {
/*       */       
/*  3385 */       company = (Company)notepad.getAllContents().get(0);
/*  3386 */       context.putSessionValue("UserCompany", company);
/*  3387 */       notepad.setSelected(company);
/*       */     } 
/*       */ 
/*       */ 
/*       */     
/*  3392 */     return company;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Day getScreenDay(Context context) {
/*  3410 */     Day day = null;
/*  3411 */     int dayID = -1;
/*  3412 */     Notepad notepad = getNotepadFromSession(6, context);
/*       */     
/*  3414 */     if (context.getRequestValue("day-id") != null) {
/*       */       
/*  3416 */       dayID = Integer.parseInt(context.getRequestValue("day-id"));
/*       */ 
/*       */ 
/*       */       
/*  3420 */       day = DayManager.getInstance().getDay(dayID, true);
/*  3421 */       context.putSessionValue("Day", day);
/*  3422 */       notepad.setSelected(day);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     }
/*  3429 */     else if ((Day)notepad.getSelected() != null) {
/*       */       
/*  3431 */       Day notepadDay = (Day)notepad.getSelected();
/*  3432 */       dayID = notepadDay.getDayID();
/*       */ 
/*       */       
/*  3435 */       if (notepadDay.getLastUpdatedCk() > 0L) {
/*       */         
/*  3437 */         day = DayManager.getInstance().getDay(dayID, false);
/*       */ 
/*       */         
/*  3440 */         day.setLastUpdatedCk(notepadDay.getLastUpdatedCk());
/*       */       }
/*       */       else {
/*       */         
/*  3444 */         day = DayManager.getInstance().getDay(dayID, true);
/*       */       } 
/*       */       
/*  3447 */       context.putSessionValue("Day", day);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     }
/*  3455 */     else if (notepad.getAllContents() != null && notepad.getAllContents().size() > 0) {
/*       */       
/*  3457 */       day = (Day)notepad.getAllContents().get(0);
/*  3458 */       notepad.setSelected(day);
/*       */     } 
/*       */ 
/*       */ 
/*       */     
/*  3463 */     return day;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Family getScreenFamily(Context context) {
/*  3482 */     Family family = null;
/*  3483 */     int familyID = -1;
/*  3484 */     Notepad notepad = getNotepadFromSession(9, context);
/*       */     
/*  3486 */     if (context.getRequestValue("family-id") != null) {
/*       */       
/*  3488 */       familyID = Integer.parseInt(context.getRequestValue("family-id"));
/*       */ 
/*       */ 
/*       */       
/*  3492 */       family = FamilyManager.getInstance().getFamily(familyID);
/*  3493 */       context.putSessionValue("Family", family);
/*  3494 */       notepad.setSelected(family);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     }
/*  3501 */     else if ((Family)notepad.getSelected() != null) {
/*       */       
/*  3503 */       family = (Family)notepad.getSelected();
/*       */ 
/*       */       
/*  3506 */       family = (Family)getStructureObject(family.getStructureID());
/*       */ 
/*       */ 
/*       */     
/*       */     }
/*  3511 */     else if (notepad.getAllContents() != null && notepad.getAllContents().size() > 0) {
/*       */       
/*  3513 */       family = (Family)notepad.getAllContents().get(0);
/*  3514 */       notepad.setSelected(family);
/*  3515 */       context.putSessionValue("Family", family);
/*       */     } 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  3521 */     return family;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Company getScreenCompany(Context context) {
/*  3538 */     Company company = null;
/*  3539 */     int companyID = -1;
/*  3540 */     Notepad notepad = getNotepadFromSession(10, context);
/*       */     
/*  3542 */     if (context.getRequestValue("company-id") != null) {
/*       */       
/*  3544 */       companyID = Integer.parseInt(context.getRequestValue("company-id"));
/*       */ 
/*       */ 
/*       */       
/*  3548 */       company = CompanyManager.getInstance().getCompany(companyID);
/*  3549 */       context.putSessionValue("Company", company);
/*  3550 */       notepad.setSelected(company);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     }
/*  3557 */     else if ((Company)notepad.getSelected() != null) {
/*       */       
/*  3559 */       company = (Company)notepad.getSelected();
/*       */ 
/*       */       
/*  3562 */       company = (Company)getStructureObject(company.getStructureID());
/*       */ 
/*       */ 
/*       */     
/*       */     }
/*  3567 */     else if (notepad.getAllContents() != null && notepad.getAllContents().size() > 0) {
/*       */       
/*  3569 */       company = (Company)notepad.getAllContents().get(0);
/*  3570 */       notepad.setSelected(company);
/*  3571 */       context.putSessionValue("Company", company);
/*       */     } 
/*       */ 
/*       */ 
/*       */     
/*  3576 */     return company;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Division getScreenDivision(Context context) {
/*  3595 */     Division division = null;
/*  3596 */     int divisionID = -1;
/*  3597 */     Notepad notepad = getNotepadFromSession(11, context);
/*       */     
/*  3599 */     if (context.getRequestValue("division-id") != null) {
/*       */       
/*  3601 */       divisionID = Integer.parseInt(context.getRequestValue("division-id"));
/*       */ 
/*       */ 
/*       */       
/*  3605 */       division = DivisionManager.getInstance().getDivision(divisionID);
/*  3606 */       context.putSessionValue("Division", division);
/*  3607 */       notepad.setSelected(division);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     }
/*  3614 */     else if ((Division)notepad.getSelected() != null) {
/*       */       
/*  3616 */       division = (Division)notepad.getSelected();
/*       */ 
/*       */       
/*  3619 */       division = (Division)getStructureObject(division.getStructureID());
/*       */ 
/*       */ 
/*       */     
/*       */     }
/*  3624 */     else if (notepad.getAllContents() != null && notepad.getAllContents().size() > 0) {
/*       */       
/*  3626 */       division = (Division)notepad.getAllContents().get(0);
/*  3627 */       notepad.setSelected(division);
/*  3628 */       context.putSessionValue("Division", division);
/*       */     } 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  3634 */     return division;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Label getScreenLabel(Context context) {
/*  3652 */     Label label = null;
/*  3653 */     int labelID = -1;
/*  3654 */     Notepad notepad = getNotepadFromSession(12, context);
/*       */     
/*  3656 */     if (context.getRequestValue("label-id") != null) {
/*       */       
/*  3658 */       labelID = Integer.parseInt(context.getRequestValue("label-id"));
/*       */ 
/*       */ 
/*       */       
/*  3662 */       label = LabelManager.getInstance().getLabel(labelID);
/*  3663 */       context.putSessionValue("Label", label);
/*  3664 */       notepad.setSelected(label);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     }
/*  3672 */     else if ((Label)notepad.getSelected() != null) {
/*       */       
/*  3674 */       label = (Label)notepad.getSelected();
/*       */ 
/*       */       
/*  3677 */       label = (Label)getStructureObject(label.getStructureID());
/*       */ 
/*       */ 
/*       */     
/*       */     }
/*  3682 */     else if (notepad.getAllContents() != null && notepad.getAllContents().size() > 0) {
/*       */       
/*  3684 */       label = (Label)notepad.getAllContents().get(0);
/*  3685 */       notepad.setSelected(label);
/*  3686 */       context.putSessionValue("Label", label);
/*       */     } 
/*       */ 
/*       */ 
/*       */     
/*  3691 */     return label;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Template getScreenTemplate(Context context) {
/*  3709 */     Template template = null;
/*  3710 */     int templateID = -1;
/*  3711 */     Notepad notepad = getNotepadFromSession(5, context);
/*       */     
/*  3713 */     if (context.getRequestValue("template-id") != null) {
/*       */       
/*  3715 */       templateID = Integer.parseInt(context.getRequestValue("template-id"));
/*       */ 
/*       */ 
/*       */       
/*  3719 */       template = TemplateManager.getInstance().getTemplate(templateID, true);
/*  3720 */       context.putSessionValue("Template", template);
/*  3721 */       notepad.setSelected(template);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     }
/*  3728 */     else if ((Template)notepad.getSelected() != null) {
/*       */       
/*  3730 */       Template notepadTemplate = (Template)notepad.getSelected();
/*  3731 */       templateID = notepadTemplate.getTemplateID();
/*       */ 
/*       */       
/*  3734 */       if (notepadTemplate.getLastUpdatedCk() > 0L) {
/*       */         
/*  3736 */         template = TemplateManager.getInstance().getTemplate(templateID, false);
/*       */ 
/*       */         
/*  3739 */         template.setLastUpdatedCk(notepadTemplate.getLastUpdatedCk());
/*       */       }
/*       */       else {
/*       */         
/*  3743 */         template = TemplateManager.getInstance().getTemplate(templateID, true);
/*       */       } 
/*       */       
/*  3746 */       context.putSessionValue("Template", template);
/*       */ 
/*       */     
/*       */     }
/*  3750 */     else if (notepad.getAllContents() != null && notepad.getAllContents().size() > 0) {
/*       */       
/*  3752 */       template = (Template)notepad.getAllContents().get(0);
/*  3753 */       notepad.setSelected(template);
/*       */     } 
/*       */ 
/*       */ 
/*       */     
/*  3758 */     return template;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static PriceCode getScreenPriceCode(Context context) {
/*  3777 */     PriceCode priceCode = null;
/*  3778 */     String priceCodeID = null;
/*  3779 */     boolean priceCodeIsDigital = false;
/*  3780 */     Notepad notepad = getNotepadFromSession(16, context);
/*       */     
/*  3782 */     if (context.getRequestValue("pricecode-id") != null) {
/*       */       
/*  3784 */       priceCodeID = context.getRequestValue("pricecode-id");
/*  3785 */       String priceCodeIsDigitalStr = context.getRequestValue("pricecode-isDigital");
/*  3786 */       if (priceCodeIsDigitalStr != null && priceCodeIsDigitalStr.compareToIgnoreCase("true") != -1) {
/*  3787 */         priceCodeIsDigital = true;
/*       */       }
/*  3789 */       priceCode = PriceCodeManager.getInstance().getPriceCode(priceCodeID, true, priceCodeIsDigital);
/*  3790 */       context.putSessionValue("PriceCode", priceCode);
/*  3791 */       notepad.setSelected(priceCode);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     }
/*  3798 */     else if ((PriceCode)notepad.getSelected() != null) {
/*       */       
/*  3800 */       PriceCode notepadPriceCode = (PriceCode)notepad.getSelected();
/*  3801 */       priceCodeID = notepadPriceCode.getSellCode();
/*  3802 */       priceCodeIsDigital = notepadPriceCode.getIsDigital();
/*       */ 
/*       */       
/*  3805 */       if (notepadPriceCode.getLastUpdatedCheck() > 0L) {
/*       */         
/*  3807 */         priceCode = PriceCodeManager.getInstance().getPriceCode(priceCodeID, false, priceCodeIsDigital);
/*       */ 
/*       */         
/*  3810 */         priceCode.setLastUpdatedCheck(notepadPriceCode.getLastUpdatedCheck());
/*       */       }
/*       */       else {
/*       */         
/*  3814 */         priceCode = PriceCodeManager.getInstance().getPriceCode(priceCodeID, true, priceCodeIsDigital);
/*       */       } 
/*       */       
/*  3817 */       context.putSessionValue("PriceCode", priceCode);
/*       */ 
/*       */     
/*       */     }
/*  3821 */     else if (notepad.getAllContents() != null && notepad.getAllContents().size() > 0) {
/*       */       
/*  3823 */       priceCode = (PriceCode)notepad.getAllContents().get(0);
/*  3824 */       notepad.setSelected(priceCode);
/*       */     } 
/*       */ 
/*       */ 
/*       */     
/*  3829 */     return priceCode;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static ReleaseWeek getScreenReleaseWeek(Context context) {
/*  3847 */     ReleaseWeek releaseWeek = null;
/*  3848 */     String releaseWeekID = null;
/*  3849 */     Notepad notepad = getNotepadFromSession(15, context);
/*       */     
/*  3851 */     if (context.getRequestValue("releaseweek-id") != null) {
/*       */       
/*  3853 */       releaseWeekID = context.getRequestValue("releaseweek-id");
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  3858 */       releaseWeek = ReleaseWeekManager.getInstance().getReleaseWeek(releaseWeekID, true);
/*  3859 */       context.putSessionValue("ReleaseWeek", releaseWeek);
/*  3860 */       notepad.setSelected(releaseWeek);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     }
/*  3867 */     else if ((ReleaseWeek)notepad.getSelected() != null) {
/*       */       
/*  3869 */       ReleaseWeek notepadReleaseWeek = (ReleaseWeek)notepad.getSelected();
/*  3870 */       releaseWeekID = notepadReleaseWeek.getName();
/*       */ 
/*       */       
/*  3873 */       if (notepadReleaseWeek.getLastUpdatedCheck() > 0L) {
/*       */         
/*  3875 */         releaseWeek = ReleaseWeekManager.getInstance().getReleaseWeek(releaseWeekID, false);
/*       */ 
/*       */         
/*  3878 */         releaseWeek.setLastUpdatedCheck(notepadReleaseWeek.getLastUpdatedCheck());
/*       */       }
/*       */       else {
/*       */         
/*  3882 */         releaseWeek = ReleaseWeekManager.getInstance().getReleaseWeek(releaseWeekID, true);
/*       */       } 
/*       */       
/*  3885 */       context.putSessionValue("ReleaseWeek", releaseWeek);
/*       */ 
/*       */     
/*       */     }
/*  3889 */     else if (notepad.getAllContents() != null && notepad.getAllContents().size() > 0) {
/*       */       
/*  3891 */       releaseWeek = (ReleaseWeek)notepad.getAllContents().get(0);
/*  3892 */       notepad.setSelected(releaseWeek);
/*       */     } 
/*       */ 
/*       */ 
/*       */     
/*  3897 */     return releaseWeek;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Report getScreenReportConfig(Context context) {
/*  3916 */     Report reportConfig = null;
/*  3917 */     int reportID = -1;
/*  3918 */     Notepad notepad = getNotepadFromSession(17, context);
/*       */     
/*  3920 */     if (context.getRequestValue("report-config-id") != null) {
/*       */       
/*  3922 */       reportID = Integer.parseInt(context.getRequestValue("report-config-id"));
/*       */ 
/*       */ 
/*       */       
/*  3926 */       reportConfig = ReportConfigManager.getInstance().getReportConfig(reportID, true);
/*  3927 */       context.putSessionValue("ReportConfig", reportConfig);
/*  3928 */       notepad.setSelected(reportConfig);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     }
/*  3935 */     else if ((Report)notepad.getSelected() != null) {
/*       */       
/*  3937 */       Report notepadReport = (Report)notepad.getSelected();
/*  3938 */       reportID = notepadReport.getReportID();
/*       */ 
/*       */       
/*  3941 */       if (notepadReport.getLastUpdatedCk() > 0L) {
/*       */         
/*  3943 */         reportConfig = ReportConfigManager.getInstance().getReportConfig(reportID, false);
/*       */ 
/*       */         
/*  3946 */         reportConfig.setLastUpdatedCk(notepadReport.getLastUpdatedCk());
/*       */       }
/*       */       else {
/*       */         
/*  3950 */         reportConfig = ReportConfigManager.getInstance().getReportConfig(reportID, true);
/*       */       } 
/*       */       
/*  3953 */       context.putSessionValue("ReportConfig", reportConfig);
/*       */ 
/*       */     
/*       */     }
/*  3957 */     else if (notepad.getAllContents() != null && notepad.getAllContents().size() > 0) {
/*       */       
/*  3959 */       reportConfig = (Report)notepad.getAllContents().get(0);
/*  3960 */       notepad.setSelected(reportConfig);
/*       */     } 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  3966 */     return reportConfig;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Report getScreenReport(Context context) {
/*  3985 */     Report report = null;
/*  3986 */     int reportID = -1;
/*  3987 */     Notepad notepad = getNotepadFromSession(3, context);
/*  3988 */     if (context.getRequestValue("report-id") != null) {
/*       */       
/*  3990 */       reportID = Integer.parseInt(context.getRequestValue("report-id"));
/*       */ 
/*       */ 
/*       */       
/*  3994 */       report = ReportConfigManager.getInstance().getReportConfig(reportID, true);
/*  3995 */       context.putSessionValue("Report", report);
/*  3996 */       notepad.setSelected(report);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     }
/*  4003 */     else if ((Report)notepad.getSelected() != null) {
/*       */       
/*  4005 */       Report notepadReport = (Report)notepad.getSelected();
/*  4006 */       reportID = notepadReport.getReportID();
/*  4007 */       report = ReportConfigManager.getInstance().getReportConfig(reportID, true);
/*  4008 */       context.putSessionValue("Report", report);
/*       */     } 
/*       */ 
/*       */     
/*  4012 */     return report;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Table getScreenTable(Context context) {
/*  4030 */     Table table = null;
/*  4031 */     int tableID = -1;
/*  4032 */     Notepad notepad = getNotepadFromSession(14, context);
/*       */     
/*  4034 */     if (context.getRequestValue("table-id") != null) {
/*       */       
/*  4036 */       tableID = Integer.parseInt(context.getRequestValue("table-id"));
/*       */ 
/*       */ 
/*       */       
/*  4040 */       table = TableManager.getInstance().getTable(tableID, true);
/*       */       
/*  4042 */       context.putSessionValue("Table", table);
/*  4043 */       notepad.setSelected(table);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     }
/*  4050 */     else if ((Table)notepad.getSelected() != null) {
/*       */       
/*  4052 */       Table notepadTable = (Table)notepad.getSelected();
/*  4053 */       tableID = notepadTable.getId();
/*       */ 
/*       */       
/*  4056 */       if (notepadTable.getLastUpdatedCk() > 0L) {
/*       */         
/*  4058 */         table = TableManager.getInstance().getTable(tableID, false);
/*       */ 
/*       */         
/*  4061 */         table.setLastUpdatedCk(notepadTable.getLastUpdatedCk());
/*       */       }
/*       */       else {
/*       */         
/*  4065 */         table = TableManager.getInstance().getTable(tableID, true);
/*       */       } 
/*       */       
/*  4068 */       context.putSessionValue("Table", table);
/*       */ 
/*       */     
/*       */     }
/*  4072 */     else if (notepad.getAllContents() != null && notepad.getAllContents().size() > 0) {
/*       */       
/*  4074 */       table = (Table)notepad.getAllContents().get(0);
/*  4075 */       notepad.setSelected(table);
/*       */     } 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  4081 */     return table;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Table getScreenTableDetail(Context context) {
/*  4100 */     Table tableDetail = null;
/*  4101 */     int tableID = -1;
/*  4102 */     String tableDetailID = "";
/*       */     
/*  4104 */     Notepad notepad = getNotepadFromSession(14, context);
/*  4105 */     Notepad notepadDetail = getNotepadFromSession(18, context);
/*       */     
/*  4107 */     if (context.getRequestValue("table-id") != null && context.getRequestValue("table-detail-id") != null) {
/*       */       
/*  4109 */       tableID = Integer.parseInt(context.getRequestValue("table-id"));
/*  4110 */       tableDetailID = context.getRequestValue("table-detail-id");
/*  4111 */       tableDetail = TableManager.getInstance().getTableDetail(tableID, tableDetailID, true);
/*  4112 */       context.putSessionValue("TableDetail", tableDetail);
/*  4113 */       notepadDetail.setSelected(tableDetail);
/*       */ 
/*       */     
/*       */     }
/*  4117 */     else if ((Table)notepad.getSelected() != null) {
/*       */       
/*  4119 */       Table notepadTable = (Table)notepad.getSelected();
/*  4120 */       Table notepadTableDetail = (Table)notepadDetail.getSelected();
/*  4121 */       LookupObject detail = null;
/*       */       
/*  4123 */       if (notepadTable != null) {
/*  4124 */         tableID = notepadTable.getId();
/*       */       }
/*  4126 */       if (notepadTableDetail != null) {
/*       */         
/*  4128 */         detail = notepadTableDetail.getDetail();
/*  4129 */         tableDetailID = detail.getAbbreviation();
/*       */       } 
/*  4131 */       tableDetail = TableManager.getInstance().getTableDetail(tableID, tableDetailID, true);
/*       */ 
/*       */       
/*  4134 */       context.putSessionValue("TableDetail", tableDetail);
/*       */ 
/*       */ 
/*       */     
/*       */     }
/*  4139 */     else if (notepadDetail.getAllContents() != null && notepadDetail.getAllContents().size() > 0) {
/*       */       
/*  4141 */       tableDetail = (Table)notepadDetail.getAllContents().get(0);
/*  4142 */       notepadDetail.setSelected(tableDetail);
/*  4143 */       context.putSessionValue("TableDetail", tableDetail);
/*       */     } 
/*       */ 
/*       */ 
/*       */     
/*  4148 */     return tableDetail;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static String formatDollarPrice(String price) {
/*  4164 */     if (price.substring(price.length() - 2, 
/*  4165 */         price.length() - 1).compareTo(".") == 0)
/*       */     {
/*  4167 */       return String.valueOf(price) + "0";
/*       */     }
/*       */ 
/*       */     
/*  4171 */     return price;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*  4191 */   public static String formatDollarPrice(float fltPrice) { return formatDollarPrice(Float.toString(fltPrice)); }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static String getLookupObjectValue(LookupObject lookupObject) {
/*  4201 */     String lookupValue = "";
/*       */     
/*  4203 */     if (lookupObject != null)
/*       */     {
/*  4205 */       lookupValue = lookupObject.getAbbreviation();
/*       */     }
/*       */     
/*  4208 */     return lookupValue;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static SelectionConfiguration getSelectionConfigObject(String abbreviation, Vector configs) {
/*  4216 */     for (int j = 0; j < configs.size(); j++) {
/*       */       
/*  4218 */       SelectionConfiguration selectionConfiguration = (SelectionConfiguration)configs.get(j);
/*       */       
/*  4220 */       if (selectionConfiguration.getSelectionConfigurationAbbreviation().equalsIgnoreCase(abbreviation))
/*       */       {
/*  4222 */         return selectionConfiguration;
/*       */       }
/*       */     } 
/*       */     
/*  4226 */     return null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static SelectionSubConfiguration getSelectionSubConfigObject(String abbreviation, SelectionConfiguration config) {
/*  4236 */     Vector subConfigs = config.getSubConfigurations();
/*       */     
/*  4238 */     for (int j = 0; j < subConfigs.size(); j++) {
/*       */       
/*  4240 */       SelectionSubConfiguration subConfig = (SelectionSubConfiguration)subConfigs.get(j);
/*       */       
/*  4242 */       if (subConfig.getSelectionSubConfigurationAbbreviation().equalsIgnoreCase(abbreviation))
/*       */       {
/*  4244 */         return subConfig;
/*       */       }
/*       */     } 
/*       */     
/*  4248 */     return null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static LookupObject getLookupObject(String abbreviation, Vector lookupVector) {
/*  4256 */     for (int j = 0; j < lookupVector.size(); j++) {
/*       */       
/*  4258 */       LookupObject lookupObject = (LookupObject)lookupVector.get(j);
/*       */       
/*  4260 */       if (lookupObject.getAbbreviation().equalsIgnoreCase(abbreviation))
/*       */       {
/*  4262 */         return lookupObject;
/*       */       }
/*       */     } 
/*       */     
/*  4266 */     return null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static DatePeriod findDatePeriod(Calendar date) {
/*  4279 */     Vector datePeriods = Cache.getDatePeriods();
/*  4280 */     DatePeriod datePeriod = null;
/*       */     
/*  4282 */     if (date != null) {
/*       */       
/*  4284 */       for (int i = 0; i < datePeriods.size(); i++) {
/*       */         
/*  4286 */         DatePeriod tmpDP = (DatePeriod)datePeriods.elementAt(i);
/*  4287 */         if (i != datePeriods.size())
/*       */         {
/*       */ 
/*       */           
/*  4291 */           if ((date.getTime().compareTo(tmpDP.getStartDate().getTime()) == 0 || date.after(tmpDP.getStartDate())) && (
/*  4292 */             date.before(tmpDP.getEndDate()) || date.getTime().compareTo(tmpDP.getEndDate().getTime()) == 0))
/*       */           {
/*  4294 */             return tmpDP;
/*       */           }
/*       */         }
/*       */       } 
/*       */       
/*  4299 */       datePeriod = (DatePeriod)datePeriods.elementAt(datePeriods.size() - 1);
/*       */     } 
/*       */ 
/*       */ 
/*       */     
/*  4304 */     return datePeriod;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static String getReleaseWeekString(Selection selection) {
/*  4315 */     if (selection.getStreetDate() != null) {
/*       */       
/*  4317 */       DatePeriod datePeriod = findDatePeriod(selection.getStreetDate());
/*  4318 */       return String.valueOf(datePeriod.getName()) + " / " + datePeriod.getCycle();
/*       */     } 
/*       */     
/*  4321 */     return "";
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static DatePeriod getReleaseWeek(Selection selection) {
/*  4331 */     if (selection.getStreetDate() != null)
/*       */     {
/*  4333 */       return findDatePeriod(selection.getStreetDate());
/*       */     }
/*       */ 
/*       */     
/*  4337 */     return null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Calendar getDueDate(Calendar pStreetDate, int pDayOfWeek, int pWeeksToRelease) {
/*  4362 */     Calendar calendar = Calendar.getInstance();
/*       */     
/*  4364 */     if (pStreetDate == null) {
/*       */       
/*  4366 */       calendar = Calendar.getInstance();
/*       */     
/*       */     }
/*       */     else {
/*       */       
/*  4371 */       calendar = (Calendar)pStreetDate.clone();
/*       */     } 
/*       */     
/*  4374 */     if (pDayOfWeek < 0 || pDayOfWeek > 7) {
/*       */ 
/*       */       
/*  4377 */       int dayOfTheWeek = calendar.get(7);
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  4382 */       int liRest = pWeeksToRelease - dayOfTheWeek + 1;
/*  4383 */       int liOffset = 0;
/*       */ 
/*       */       
/*  4386 */       if (liRest >= 0)
/*       */       {
/*       */ 
/*       */         
/*  4390 */         liOffset = (liRest / 5 + 1) * 2;
/*       */       }
/*       */       
/*  4393 */       calendar.add(6, -pWeeksToRelease - liOffset);
/*       */     
/*       */     }
/*       */     else {
/*       */ 
/*       */       
/*  4399 */       calendar.set(7, pDayOfWeek + 1);
/*       */       
/*  4401 */       calendar.add(3, -pWeeksToRelease);
/*       */     } 
/*       */     
/*  4404 */     return calendar;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*  4419 */   public static boolean isStringNotEmpty(String theString) { return (theString != null && !theString.equals("")); }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static String addQueryParams(String params, String newParam) {
/*  4434 */     if (!isStringNotEmpty(params) || params.toUpperCase().indexOf("WHERE") < 0) {
/*       */       
/*  4436 */       params = " WHERE " + newParam;
/*       */     }
/*       */     else {
/*       */       
/*  4440 */       params = " AND " + newParam;
/*       */     } 
/*       */     
/*  4443 */     return params;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Vector filterCorporateByDescription(Vector corporateObjects, String filter) {
/*  4458 */     if (isStringNotEmpty(filter)) {
/*       */       
/*  4460 */       int searchType = 0;
/*  4461 */       filter = filter.replace('*', '%');
/*  4462 */       int firstWildOccur = filter.indexOf("%");
/*  4463 */       int lastWildOccur = filter.lastIndexOf("%");
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  4469 */       if (firstWildOccur < 0) {
/*       */ 
/*       */         
/*  4472 */         searchType = 0;
/*       */       }
/*  4474 */       else if (firstWildOccur > -1 && lastWildOccur > -1 && firstWildOccur != lastWildOccur) {
/*       */ 
/*       */         
/*  4477 */         searchType = 1;
/*  4478 */         filter = filter.substring(firstWildOccur + 1, lastWildOccur);
/*       */ 
/*       */ 
/*       */       
/*       */       }
/*  4483 */       else if (firstWildOccur == 0) {
/*       */ 
/*       */         
/*  4486 */         searchType = 2;
/*  4487 */         filter = filter.substring(1, filter.length());
/*       */       
/*       */       }
/*       */       else {
/*       */ 
/*       */         
/*  4493 */         searchType = 3;
/*  4494 */         filter = filter.substring(0, filter.length() - 1);
/*       */       } 
/*       */ 
/*       */       
/*  4498 */       Vector filteredObjects = new Vector();
/*  4499 */       filter = filter.toUpperCase();
/*       */       
/*  4501 */       for (int i = 0; i < corporateObjects.size(); i++) {
/*       */         
/*  4503 */         CorporateStructureObject corporateObject = (CorporateStructureObject)corporateObjects.get(i);
/*  4504 */         String description = corporateObject.getName().toUpperCase();
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  4509 */         switch (searchType) {
/*       */           
/*       */           case 0:
/*  4512 */             if (description.indexOf(filter) >= 0) {
/*  4513 */               filteredObjects.add(corporateObject);
/*       */             }
/*       */             break;
/*       */           case 1:
/*  4517 */             if (description.indexOf(filter) >= 0) {
/*  4518 */               filteredObjects.add(corporateObject);
/*       */             }
/*       */             break;
/*       */           
/*       */           case 2:
/*       */             try {
/*  4524 */               if (description.indexOf(filter) > -1 && 
/*  4525 */                 description.indexOf(filter) == description.length() - filter.length()) {
/*  4526 */                 filteredObjects.add(corporateObject);
/*       */               }
/*  4528 */             } catch (Exception exception) {}
/*       */             break;
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*       */           case 3:
/*  4535 */             if (description.indexOf(filter) == 0) {
/*  4536 */               filteredObjects.add(corporateObject);
/*       */             }
/*       */             break;
/*       */         } 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*       */       } 
/*  4545 */       return filteredObjects;
/*       */     } 
/*       */ 
/*       */     
/*  4549 */     return corporateObjects;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static CompanyAcl getScreenPermissions(Company company, User user) {
/*  4569 */     Hashtable companyAclHash = user.getAcl().getCompanyAclHash();
/*  4570 */     int companyId = company.getStructureID();
/*  4571 */     if (companyAclHash != null) {
/*  4572 */       return (CompanyAcl)companyAclHash.get(String.valueOf(companyId));
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  4599 */     return null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static String getOwnerCompanyWhereClause(Context context) {
/*  4614 */     Vector companies = getUserCompanies(context);
/*  4615 */     Company company = null;
/*       */     
/*  4617 */     StringList list = new StringList(" OR ");
/*       */ 
/*       */     
/*  4620 */     for (int i = 0; i < companies.size(); i++) {
/*       */       
/*  4622 */       company = (Company)companies.elementAt(i);
/*       */       
/*  4624 */       if (company != null && company.getParentEnvironment().getParentFamily() != null)
/*       */       {
/*       */         
/*  4627 */         list.add("owner = " + company.getParentEnvironment().getParentFamily().getStructureID());
/*       */       }
/*       */     } 
/*       */     
/*  4631 */     String whereClause = list.toString();
/*       */     
/*  4633 */     if (whereClause.length() > 1) {
/*  4634 */       whereClause = " WHERE (" + whereClause + ")";
/*       */     }
/*       */     else {
/*       */       
/*  4638 */       whereClause = " WHERE (owner = -1)";
/*       */     } 
/*       */ 
/*       */ 
/*       */     
/*  4643 */     return whereClause;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean getNotePadVisiblitiy(int type, Context context) {
/*  4659 */     Notepad notepad = getNotepadFromSession(type, context);
/*       */     
/*  4661 */     boolean result = true;
/*       */     
/*  4663 */     if (notepad != null) {
/*       */       
/*  4665 */       if (context.getParameter("showNotepad") != null && context.getParameter("showNotepad").equalsIgnoreCase("true"))
/*       */       {
/*       */         
/*  4668 */         notepad.setVisible(true);
/*       */       }
/*       */       
/*  4671 */       result = notepad.isVisible();
/*       */     } 
/*       */     
/*  4674 */     return result;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Object[] sortStringVector(Vector stringVector) {
/*  4688 */     Vector sortedVector = new Vector();
/*  4689 */     Object[] stringArray = null;
/*       */     
/*  4691 */     if (stringVector != null) {
/*       */ 
/*       */       
/*  4694 */       stringArray = stringVector.toArray();
/*       */ 
/*       */       
/*  4697 */       Arrays.sort(stringArray, new StringComparator());
/*       */     } 
/*       */     
/*  4700 */     return stringArray;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Vector sortStrings(Vector stringVector) {
/*  4714 */     Vector sortedVector = new Vector();
/*  4715 */     Object[] stringArray = null;
/*       */     
/*  4717 */     if (stringVector != null) {
/*       */ 
/*       */       
/*  4720 */       stringArray = stringVector.toArray();
/*       */ 
/*       */       
/*  4723 */       Arrays.sort(stringArray);
/*       */ 
/*       */       
/*  4726 */       for (int i = 0; i < stringArray.length; i++) {
/*       */         
/*  4728 */         String cso = (String)stringArray[i];
/*  4729 */         sortedVector.add(cso);
/*       */       } 
/*       */     } 
/*       */     
/*  4733 */     return sortedVector;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Vector sortDates(Vector stringVector) {
/*  4747 */     Vector sortedVector = new Vector();
/*  4748 */     Object[] stringArray = null;
/*       */     
/*  4750 */     if (stringVector != null) {
/*       */ 
/*       */       
/*  4753 */       stringArray = stringVector.toArray();
/*       */ 
/*       */       
/*  4756 */       Arrays.sort(stringArray, new StringDateComparator());
/*       */ 
/*       */       
/*  4759 */       for (int i = 0; i < stringArray.length; i++) {
/*       */         
/*  4761 */         String cso = (String)stringArray[i];
/*  4762 */         sortedVector.add(cso);
/*       */       } 
/*       */     } 
/*       */     
/*  4766 */     return sortedVector;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Vector sortCorporateVectorByName(Vector csoVector) {
/*  4781 */     Vector sortedVector = new Vector();
/*       */     
/*  4783 */     if (csoVector != null) {
/*       */ 
/*       */       
/*  4786 */       Object[] csoArray = csoVector.toArray();
/*       */ 
/*       */       
/*  4789 */       Arrays.sort(csoArray, new CorpStructNameComparator());
/*       */ 
/*       */       
/*  4792 */       for (int i = 0; i < csoArray.length; i++) {
/*       */         
/*  4794 */         CorporateStructureObject cso = (CorporateStructureObject)csoArray[i];
/*  4795 */         sortedVector.add(cso);
/*       */       } 
/*       */     } 
/*       */     
/*  4799 */     return sortedVector;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Vector sortCorporateVectorByParentName(Vector csoVector) {
/*  4815 */     Vector sortedVector = new Vector();
/*       */     
/*  4817 */     if (csoVector != null) {
/*       */ 
/*       */       
/*  4820 */       Object[] csoArray = csoVector.toArray();
/*       */ 
/*       */       
/*  4823 */       Arrays.sort(csoArray, new CorpStructParentNameComparator());
/*       */ 
/*       */       
/*  4826 */       for (int i = 0; i < csoArray.length; i++) {
/*       */         
/*  4828 */         CorporateStructureObject cso = (CorporateStructureObject)csoArray[i];
/*  4829 */         sortedVector.add(cso);
/*       */       } 
/*       */     } 
/*       */     
/*  4833 */     return sortedVector;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Vector sortSelectionVector(Vector csoVector) {
/*  4848 */     Vector sortedVector = new Vector();
/*       */     
/*  4850 */     if (csoVector != null) {
/*       */ 
/*       */       
/*  4853 */       Object[] csoArray = csoVector.toArray();
/*       */ 
/*       */       
/*  4856 */       Arrays.sort(csoArray);
/*       */ 
/*       */       
/*  4859 */       for (int i = 0; i < csoArray.length; i++) {
/*       */         
/*  4861 */         Selection cso = (Selection)csoArray[i];
/*  4862 */         sortedVector.add(cso);
/*       */       } 
/*       */     } 
/*       */     
/*  4866 */     return sortedVector;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static String setWildCardsEscapeSingleQuotes(String wildCard) {
/*  4882 */     if (wildCard != null && !wildCard.equals("")) {
/*       */       
/*  4884 */       wildCard = escapeSingleQuotes(wildCard);
/*       */       
/*  4886 */       if (wildCard.indexOf("%") > -1 || wildCard.indexOf("*") > -1) {
/*       */ 
/*       */         
/*  4889 */         wildCard = wildCard.replace('*', '%');
/*  4890 */         wildCard = " like '" + wildCard + "'";
/*       */       
/*       */       }
/*       */       else {
/*       */ 
/*       */         
/*  4896 */         wildCard = " like '%" + wildCard + "%'";
/*       */       } 
/*       */     } 
/*       */     
/*  4900 */     return wildCard;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static LookupObject getSupplierById(String id) {
/*  4916 */     Vector suppliers = Cache.getInstance().getBomSuppliers();
/*  4917 */     if (suppliers != null)
/*       */     {
/*  4919 */       for (int i = 0; i < suppliers.size(); i++) {
/*       */         
/*  4921 */         LookupObject supplierTemp = (LookupObject)suppliers.elementAt(i);
/*  4922 */         if (supplierTemp != null && supplierTemp.getAbbreviation().equals(id)) {
/*  4923 */           return supplierTemp;
/*       */         }
/*       */       } 
/*       */     }
/*  4927 */     return null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*  4938 */   public static Vector getBomSuppliers(int partId) { return getBomSuppliers(partId, "0,1"); }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*  4948 */   public static Vector getBomSuppliers(int partId, int prevSupplier) { return getBomSuppliers(partId, "0,1", prevSupplier); }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Vector getBomSuppliers(int partId, String flag) {
/*  4964 */     String query = "SELECT * FROM vi_Supplier s with (nolock), vi_Part_Supplier ps with (nolock) WHERE ps.part_id =" + 
/*       */       
/*  4966 */       partId + 
/*  4967 */       " AND ps.supplier_id = s.supplier_id" + 
/*  4968 */       " AND (s.inactive is null or s.inactive = 0) " + 
/*  4969 */       " AND typeFlag in (" + flag + ")" + 
/*  4970 */       " ORDER BY s.description;";
/*       */ 
/*       */     
/*  4973 */     JdbcConnector connector = getConnector(query);
/*  4974 */     connector.runQuery();
/*       */     
/*  4976 */     Vector precache = new Vector();
/*       */ 
/*       */     
/*  4979 */     while (connector.more()) {
/*       */       
/*  4981 */       LookupObject lookupObject = new LookupObject();
/*  4982 */       lookupObject.setAbbreviation(connector.getField("supplier_id", ""));
/*  4983 */       lookupObject.setName(connector.getField("description", ""));
/*  4984 */       precache.addElement(lookupObject);
/*  4985 */       connector.next();
/*       */     } 
/*       */     
/*  4988 */     bomSuppliers = precache;
/*       */     
/*  4990 */     connector.close();
/*       */ 
/*       */     
/*  4993 */     return bomSuppliers;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Vector getBomSuppliers(int partId, String flag, int prevSupplier) {
/*  5010 */     String query = "SELECT * FROM vi_Supplier s with (nolock), vi_Part_Supplier ps with (nolock) WHERE ps.part_id =" + 
/*       */       
/*  5012 */       partId + 
/*  5013 */       " AND ps.supplier_id = s.supplier_id" + 
/*  5014 */       " AND ((s.inactive is null or s.inactive = 0) OR s.supplier_id = " + prevSupplier + ") " + 
/*  5015 */       " AND typeFlag in (" + flag + ")" + 
/*  5016 */       " ORDER BY s.description;";
/*       */ 
/*       */     
/*  5019 */     JdbcConnector connector = getConnector(query);
/*  5020 */     connector.runQuery();
/*       */     
/*  5022 */     Vector precache = new Vector();
/*       */ 
/*       */     
/*  5025 */     while (connector.more()) {
/*       */       
/*  5027 */       LookupObject lookupObject = new LookupObject();
/*  5028 */       lookupObject.setAbbreviation(connector.getField("supplier_id", ""));
/*  5029 */       lookupObject.setName(connector.getField("description", ""));
/*  5030 */       precache.addElement(lookupObject);
/*  5031 */       connector.next();
/*       */     } 
/*       */     
/*  5034 */     bomSuppliers = precache;
/*       */     
/*  5036 */     connector.close();
/*       */ 
/*       */     
/*  5039 */     return bomSuppliers;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Company getCompanyById(int id) {
/*  5054 */     Vector companies = Cache.getInstance().getCompanies();
/*  5055 */     if (companies != null)
/*       */     {
/*  5057 */       for (int i = 0; i < companies.size(); i++) {
/*       */         
/*  5059 */         Company companyTemp = (Company)companies.elementAt(i);
/*  5060 */         if (companyTemp != null && companyTemp.getIdentity() == id) {
/*  5061 */           return companyTemp;
/*       */         }
/*       */       } 
/*       */     }
/*  5065 */     return null;
/*       */   }
/*       */ 
/*       */   
/*       */   public static Vector filterUmlTasks(Vector tasks, boolean uml) {
/*  5070 */     Vector filteredTasks = new Vector();
/*       */     
/*  5072 */     for (int i = 0; i < tasks.size(); i++) {
/*       */       
/*  5074 */       ScheduledTask task = (ScheduledTask)tasks.get(i);
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  5079 */       if (uml) {
/*       */         
/*  5081 */         if (isUml(task))
/*       */         {
/*  5083 */           filteredTasks.add(task);
/*       */         
/*       */         }
/*       */       
/*       */       }
/*  5088 */       else if (!isUml(task)) {
/*       */         
/*  5090 */         filteredTasks.add(task);
/*       */       } 
/*       */     } 
/*       */ 
/*       */     
/*  5095 */     return filteredTasks;
/*       */   }
/*       */ 
/*       */   
/*       */   public static boolean isUml(ScheduledTask task) {
/*  5100 */     Family family = (task.getOwner() != null) ? task.getOwner() : null;
/*  5101 */     if (family != null) {
/*       */       
/*  5103 */       String familyName = family.getName();
/*  5104 */       familyName = familyName.trim();
/*  5105 */       return familyName.equalsIgnoreCase("UML");
/*       */     } 
/*  5107 */     return false;
/*       */   }
/*       */ 
/*       */   
/*       */   public static boolean isUml(Task task) {
/*  5112 */     Family family = (task.getOwner() != null) ? task.getOwner() : null;
/*       */     
/*  5114 */     if (family != null)
/*       */     {
/*       */ 
/*       */       
/*  5118 */       return (family.getStructureID() == 417);
/*       */     }
/*  5120 */     return false;
/*       */   }
/*       */ 
/*       */   
/*       */   public static boolean isUml(Family family) {
/*  5125 */     if (family != null)
/*       */     {
/*  5127 */       return (family.getStructureID() == 417);
/*       */     }
/*  5129 */     return false;
/*       */   }
/*       */ 
/*       */   
/*       */   public static boolean hasUmlTasks(Schedule schedule) {
/*  5134 */     boolean hasUmlTask = false;
/*       */     
/*  5136 */     if (schedule != null && schedule.getTasks().size() > 0) {
/*       */       
/*  5138 */       Vector tasks = schedule.getTasks();
/*  5139 */       ScheduledTask scheduledTask = null;
/*       */       
/*  5141 */       for (int i = 0; i < tasks.size(); i++) {
/*       */         
/*  5143 */         scheduledTask = (ScheduledTask)tasks.get(i);
/*       */         
/*  5145 */         Family family = (scheduledTask.getOwner() != null) ? scheduledTask.getOwner() : null;
/*  5146 */         if (family != null) {
/*       */           
/*  5148 */           String familyName = family.getName();
/*  5149 */           familyName = familyName.trim();
/*  5150 */           hasUmlTask = familyName.equalsIgnoreCase("UML");
/*  5151 */           if (hasUmlTask)
/*  5152 */             return hasUmlTask; 
/*       */         } 
/*  5154 */         scheduledTask = null;
/*       */       } 
/*       */     } 
/*  5157 */     return hasUmlTask;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean isEcommerce(ScheduledTask task) {
/*  5167 */     Family family = (task.getOwner() != null) ? task.getOwner() : null;
/*  5168 */     if (family != null) {
/*       */       
/*  5170 */       String familyName = family.getName();
/*  5171 */       familyName = familyName.trim();
/*  5172 */       return familyName.equalsIgnoreCase("eCommerce");
/*       */     } 
/*  5174 */     return false;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean isUmlUser(User user) {
/*  5202 */     Acl acl = user.getAcl();
/*       */     
/*  5204 */     Vector envAcls = acl.getEnvironmentAcl();
/*  5205 */     String envName = "";
/*  5206 */     EnvironmentAcl envAcl = null;
/*       */     
/*  5208 */     for (int i = 0; i < envAcls.size(); i++) {
/*       */       
/*  5210 */       envAcl = (EnvironmentAcl)envAcls.get(i);
/*  5211 */       int aclEnvironmentId = envAcl.getEnvironmentId();
/*  5212 */       envName = getStructureName(aclEnvironmentId);
/*       */       
/*  5214 */       if (envName.equalsIgnoreCase("UML"))
/*       */       {
/*  5216 */         return true;
/*       */       }
/*  5218 */       envName = "";
/*       */     } 
/*  5220 */     return false;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static String[] parseFilter(String filter) {
/*  5227 */     String filterString = "All";
/*  5228 */     String filterFlag = "Yes";
/*  5229 */     String userFilter = filter;
/*  5230 */     String[] filterArray = { "Yes", "All" };
/*       */     
/*  5232 */     if (userFilter != null && userFilter.length() > 0) {
/*       */       
/*  5234 */       if (userFilter.substring(0, 0).equalsIgnoreCase("t")) {
/*  5235 */         filterFlag = userFilter.substring(0, 3);
/*       */       } else {
/*  5237 */         filterFlag = userFilter.substring(0, 4);
/*       */       } 
/*  5239 */       if (filterFlag.equalsIgnoreCase("true")) {
/*  5240 */         filterString = userFilter.substring(5, userFilter.length());
/*       */       } else {
/*  5242 */         filterString = userFilter.substring(6, userFilter.length());
/*       */       } 
/*  5244 */       if (filterFlag.equalsIgnoreCase("true")) {
/*  5245 */         filterFlag = "Yes";
/*       */       } else {
/*  5247 */         filterFlag = "No";
/*       */       } 
/*  5249 */       if (filterString.equalsIgnoreCase("milestone.filter.FilterNone")) {
/*       */         
/*  5251 */         filterString = "All";
/*       */       }
/*  5253 */       else if (filterString.equalsIgnoreCase("milestone.filter.FilterExcludeOwnerUML")) {
/*       */         
/*  5255 */         filterString = "Only Label Tasks";
/*       */       }
/*  5257 */       else if (filterString.equalsIgnoreCase("milestone.filter.FilterIncludeOwnerUML")) {
/*       */         
/*  5259 */         filterString = "Only UML Tasks";
/*       */       } 
/*  5261 */       filterArray[0] = filterFlag;
/*  5262 */       filterArray[1] = filterString;
/*       */     } 
/*  5264 */     return filterArray;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static String getDayType(ScheduledTask scheduledTask) {
/*  5272 */     System.out.println("***** MilestoneHelper.getDayType(ScheduledTask) has been replaced by getDayType(int, ScheduledTask)");
/*  5273 */     return null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static String getDayType(Calendar date) {
/*  5281 */     System.out.println("***** MilestoneHelper.getDayType(Calendar) has been replaced by getDayType(int, Calendar)");
/*  5282 */     return null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static String getDayType(int calendarGroup, ScheduledTask scheduledTask) {
/*  5291 */     if (scheduledTask != null)
/*       */     {
/*  5293 */       return getDayType(calendarGroup, scheduledTask.getDueDate());
/*       */     }
/*  5295 */     return "";
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static String getDayType(int calendarGroup, Calendar date) {
/*  5309 */     String dayType = getCalendarGroupDayType(1, date);
/*       */ 
/*       */     
/*  5312 */     if (!isStringNotEmpty(dayType) && calendarGroup != 1)
/*       */     {
/*  5314 */       dayType = getCalendarGroupDayType(calendarGroup, date);
/*       */     }
/*  5316 */     return dayType;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   protected static String getCalendarGroupDayType(int calendarGroup, Calendar date) {
/*  5327 */     String retDayTypeStr = "";
/*  5328 */     if (date != null) {
/*       */ 
/*       */ 
/*       */       
/*  5332 */       Vector dayTypes = Cache.getDayTypes();
/*       */       
/*  5334 */       Vector groupDays = null;
/*  5335 */       boolean found = false;
/*  5336 */       int i = 0;
/*  5337 */       while (!found && i < dayTypes.size()) {
/*       */ 
/*       */         
/*  5340 */         groupDays = (Vector)dayTypes.get(i);
/*  5341 */         if (groupDays != null && groupDays.get(false) != null)
/*       */         {
/*  5343 */           if (((Day)groupDays.get(0)).getCalendarGroup() == calendarGroup) {
/*  5344 */             found = true;
/*       */           }
/*       */         }
/*  5347 */         i++;
/*       */       } 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  5353 */       if (found && groupDays != null && groupDays.size() > 0) {
/*  5354 */         Day dayType = null;
/*  5355 */         boolean done = false;
/*  5356 */         int j = 0;
/*  5357 */         int groupDaysSize = groupDays.size();
/*       */         
/*  5359 */         while (!done && j < groupDaysSize) {
/*  5360 */           dayType = (Day)groupDays.get(j);
/*  5361 */           if (dayType.getSpecificDate() != null && date.equals(dayType.getSpecificDate())) {
/*       */             
/*  5363 */             retDayTypeStr = dayType.getDayType();
/*  5364 */             done = true;
/*       */           }
/*  5366 */           else if (date.after(dayType.getSpecificDate())) {
/*       */             
/*  5368 */             done = true;
/*       */           } 
/*       */           
/*  5371 */           j++;
/*       */         } 
/*       */       } 
/*       */     } 
/*  5375 */     return retDayTypeStr;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean isTaskUsed(Task task) {
/*  5384 */     boolean isTaskUsed = false;
/*  5385 */     if (task != null) {
/*       */       
/*  5387 */       int taskId = task.getTaskID();
/*       */       
/*  5389 */       String query = "SELECT * from vi_release_detail a with (nolock) full outer join vi_template_detail b with (nolock) on a.task_id = b.task_id where a.task_id = " + 
/*       */ 
/*       */         
/*  5392 */         taskId + " or " + 
/*  5393 */         "b.task_id = " + taskId;
/*       */       
/*  5395 */       JdbcConnector connector = getConnector(query);
/*  5396 */       connector.runQuery();
/*       */       
/*  5398 */       if (connector.more()) {
/*  5399 */         isTaskUsed = true;
/*       */       }
/*  5401 */       connector.close();
/*       */     } 
/*       */     
/*  5404 */     return isTaskUsed;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Hashtable groupSelectionsByStatus(Vector selections) {
/*  5419 */     Hashtable groupedSelections = new Hashtable();
/*  5420 */     if (selections == null) {
/*  5421 */       return groupedSelections;
/*       */     }
/*  5423 */     for (int i = 0; i < selections.size(); i++) {
/*       */       
/*  5425 */       Selection sel = (Selection)selections.elementAt(i);
/*  5426 */       if (sel != null) {
/*       */         
/*  5428 */         sel = SelectionManager.getInstance().getSelectionHeader(sel.getSelectionID());
/*  5429 */         String selStatus = SelectionManager.getLookupObjectValue(sel.getSelectionStatus());
/*  5430 */         Vector group = (Vector)groupedSelections.get(selStatus);
/*       */         
/*  5432 */         if (group == null) {
/*       */           
/*  5434 */           group = new Vector();
/*  5435 */           group.addElement(sel);
/*  5436 */           groupedSelections.put(selStatus, group);
/*       */         }
/*       */         else {
/*       */           
/*  5440 */           group.addElement(sel);
/*       */         } 
/*       */       } 
/*       */     } 
/*       */     
/*  5445 */     return groupedSelections;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Hashtable groupSelectionsByFamilyAndCompany(Vector selections) {
/*  5461 */     Hashtable groupedByFamilyAndCompany = new Hashtable();
/*  5462 */     if (selections == null) {
/*  5463 */       return groupedByFamilyAndCompany;
/*       */     }
/*  5465 */     for (int i = 0; i < selections.size(); i++) {
/*       */       
/*  5467 */       Selection sel = (Selection)selections.elementAt(i);
/*  5468 */       if (sel != null) {
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  5473 */         String familyName = "", companyName = "";
/*  5474 */         Family family = sel.getFamily();
/*  5475 */         Company company = sel.getCompany();
/*       */         
/*  5477 */         if (family != null)
/*  5478 */           familyName = (family.getName() == null) ? "" : family.getName(); 
/*  5479 */         if (company != null) {
/*  5480 */           companyName = (company.getName() == null) ? "" : company.getName();
/*       */         }
/*       */ 
/*       */         
/*  5484 */         Hashtable familySubTable = (Hashtable)groupedByFamilyAndCompany.get(familyName);
/*  5485 */         if (familySubTable == null) {
/*       */ 
/*       */           
/*  5488 */           familySubTable = new Hashtable();
/*  5489 */           groupedByFamilyAndCompany.put(familyName, familySubTable);
/*       */         } 
/*       */ 
/*       */         
/*  5493 */         Vector selectionsForCompany = (Vector)familySubTable.get(companyName);
/*  5494 */         if (selectionsForCompany == null) {
/*       */ 
/*       */           
/*  5497 */           selectionsForCompany = new Vector();
/*  5498 */           familySubTable.put(companyName, selectionsForCompany);
/*       */         } 
/*       */ 
/*       */         
/*  5502 */         selectionsForCompany.addElement(sel);
/*       */       } 
/*       */     } 
/*       */     
/*  5506 */     return groupedByFamilyAndCompany;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Hashtable groupSelectionsByCompanyAndSubconfig(Vector selections) {
/*  5523 */     Hashtable groupedByCompanyAndSubconfig = new Hashtable();
/*  5524 */     if (selections == null) {
/*  5525 */       return groupedByCompanyAndSubconfig;
/*       */     }
/*  5527 */     for (int i = 0; i < selections.size(); i++) {
/*       */       
/*  5529 */       Selection sel = (Selection)selections.elementAt(i);
/*  5530 */       if (sel != null) {
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  5535 */         String familyName = "", companyName = "";
/*  5536 */         String configName = "", subconfigName = "";
/*       */         
/*  5538 */         Family family = sel.getFamily();
/*  5539 */         Company company = sel.getCompany();
/*  5540 */         SelectionConfiguration config = sel.getSelectionConfig();
/*  5541 */         SelectionSubConfiguration subconfig = sel.getSelectionSubConfig();
/*       */         
/*  5543 */         if (family != null)
/*  5544 */           familyName = (family.getName() == null) ? "" : family.getName(); 
/*  5545 */         if (company != null)
/*  5546 */           companyName = (company.getName() == null) ? "" : company.getName(); 
/*  5547 */         if (config != null)
/*  5548 */           configName = (config.getSelectionConfigurationName() == null) ? 
/*  5549 */             "" : config.getSelectionConfigurationName(); 
/*  5550 */         if (subconfig != null) {
/*  5551 */           subconfigName = (subconfig.getSelectionSubConfigurationName() == null) ? 
/*  5552 */             "" : subconfig.getSelectionSubConfigurationName();
/*       */         }
/*       */ 
/*       */ 
/*       */         
/*  5557 */         if (subconfigName.equalsIgnoreCase("EP/Sampler") || 
/*  5558 */           subconfigName.equalsIgnoreCase("Full") || 
/*  5559 */           subconfigName.equalsIgnoreCase("Full Length") || 
/*  5560 */           configName.equalsIgnoreCase("DualDisc")) {
/*       */           
/*  5562 */           subconfigName = "Full Length";
/*       */         }
/*  5564 */         else if (!subconfigName.equals("")) {
/*       */           
/*  5566 */           subconfigName = "Singles";
/*       */         } 
/*       */ 
/*       */ 
/*       */         
/*  5571 */         Hashtable companySubTable = (Hashtable)groupedByCompanyAndSubconfig.get(companyName);
/*  5572 */         if (companySubTable == null) {
/*       */ 
/*       */           
/*  5575 */           companySubTable = new Hashtable();
/*  5576 */           groupedByCompanyAndSubconfig.put(companyName, companySubTable);
/*       */         } 
/*       */ 
/*       */         
/*  5580 */         Vector selectionsForSubconfig = (Vector)companySubTable.get(subconfigName);
/*  5581 */         if (selectionsForSubconfig == null) {
/*       */ 
/*       */           
/*  5584 */           selectionsForSubconfig = new Vector();
/*  5585 */           companySubTable.put(subconfigName, selectionsForSubconfig);
/*       */         } 
/*       */ 
/*       */         
/*  5589 */         selectionsForSubconfig.addElement(sel);
/*       */       } 
/*       */     } 
/*       */     
/*  5593 */     return groupedByCompanyAndSubconfig;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   protected static Vector filterSelectionsAndTaskWithReportCriteria(Vector selections, Context context) {
/*  5637 */     Vector selectionsForReport = new Vector();
/*  5638 */     Form reportForm = (Form)context.getSessionValue("reportForm");
/*       */     
/*  5640 */     if (selections == null || selections.size() == 0) {
/*  5641 */       return selectionsForReport;
/*       */     }
/*       */     
/*  5644 */     Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && reportForm.getStringValue("beginDate").length() > 0) ? getDate(reportForm.getStringValue("beginDate")) : null;
/*  5645 */     if (beginStDate == null) {
/*       */       
/*  5647 */       beginStDate = Calendar.getInstance();
/*  5648 */       beginStDate.setTime(new Date(0L));
/*       */     } 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  5654 */     beginStDate.set(11, 0);
/*  5655 */     beginStDate.set(12, 0);
/*  5656 */     beginStDate.set(13, 0);
/*  5657 */     beginStDate.set(14, 0);
/*       */     
/*  5659 */     Calendar endStDate = (reportForm.getStringValue("endDate") != null && reportForm.getStringValue("endDate").length() > 0) ? getDate(reportForm.getStringValue("endDate")) : null;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  5666 */     if (endStDate != null) {
/*       */       
/*  5668 */       endStDate.set(11, 23);
/*  5669 */       endStDate.set(12, 59);
/*  5670 */       endStDate.set(13, 59);
/*  5671 */       endStDate.set(14, 999);
/*       */     } 
/*       */     
/*  5674 */     String strDistribution = (reportForm.getStringValue("distribution") != null) ? reportForm.getStringValue("distribution") : "";
/*  5675 */     String strLabelContact = (reportForm.getStringValue("contact") != null) ? reportForm.getStringValue("contact") : "";
/*  5676 */     String strUmlContact = (reportForm.getStringValue("umlContact") != null) ? reportForm.getStringValue("umlContact") : "";
/*  5677 */     String strFuture2 = (reportForm.getStringValue("Future2") != null) ? reportForm.getStringValue("Future2") : "";
/*       */     
/*  5679 */     String strReleaseType = (reportForm.getStringValue("releaseType") != null) ? reportForm.getStringValue("releaseType") : "";
/*  5680 */     String strStatus = (reportForm.getStringValue("status") != null) ? reportForm.getStringValue("status") : "";
/*  5681 */     String strArtist = (reportForm.getStringValue("artist") != null) ? reportForm.getStringValue("artist") : "";
/*  5682 */     String strFamily = (reportForm.getStringValue("family") != null) ? reportForm.getStringValue("family") : "";
/*  5683 */     String strCompany = (reportForm.getStringValue("company") != null) ? reportForm.getStringValue("company") : "";
/*  5684 */     String strLabel = (reportForm.getStringValue("Label") != null) ? reportForm.getStringValue("Label") : "";
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  5695 */     String[] strConfiguration = null;
/*       */     try {
/*  5697 */       MilestoneFormDropDownMenu configList = (MilestoneFormDropDownMenu)reportForm.getElement("configurationList");
/*  5698 */       if (configList != null) {
/*  5699 */         ArrayList configListAl = configList.getStringValues();
/*  5700 */         if (configListAl != null) {
/*       */           
/*  5702 */           strConfiguration = new String[configListAl.size()];
/*  5703 */           strConfiguration = (String[])configListAl.toArray(strConfiguration);
/*       */         } 
/*       */       } 
/*  5706 */     } catch (Exception e) {
/*  5707 */       e.printStackTrace();
/*  5708 */       System.out.println("<<< Configuration vector execption " + e.getMessage());
/*       */     } 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  5715 */     String[] strSubconfiguration = null;
/*       */     try {
/*  5717 */       MilestoneFormDropDownMenu subconfigList = (MilestoneFormDropDownMenu)reportForm.getElement("subconfigurationList");
/*  5718 */       if (subconfigList != null) {
/*  5719 */         ArrayList subconfigListAl = subconfigList.getStringValues();
/*  5720 */         if (subconfigListAl != null) {
/*       */           
/*  5722 */           strSubconfiguration = new String[subconfigListAl.size()];
/*  5723 */           strSubconfiguration = (String[])subconfigListAl.toArray(strSubconfiguration);
/*       */         } 
/*       */       } 
/*  5726 */     } catch (Exception e) {
/*  5727 */       e.printStackTrace();
/*  5728 */       System.out.println("<<< Subconfiguration vector execption " + e.getMessage());
/*       */     } 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  5736 */     boolean bParent = (reportForm.getElement("ParentsOnly") != null) ? ((FormCheckBox)reportForm.getElement("ParentsOnly")).isChecked() : 0;
/*       */     
/*  5738 */     int totalCount = selections.size();
/*  5739 */     int tenth = 0;
/*       */     
/*  5741 */     tenth = totalCount / 5;
/*       */     
/*  5743 */     if (tenth < 1) {
/*  5744 */       tenth = 1;
/*       */     }
/*       */     
/*       */     try {
/*  5748 */       HttpServletResponse sresponse = context.getResponse();
/*  5749 */       context.putDelivery("status", new String("start_gathering"));
/*  5750 */       context.putDelivery("percent", new String("10"));
/*  5751 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  5752 */       sresponse.setContentType("text/plain");
/*  5753 */       sresponse.flushBuffer();
/*       */     }
/*  5755 */     catch (Exception exception) {}
/*       */ 
/*       */ 
/*       */     
/*  5759 */     int recordCount = 0;
/*  5760 */     int count = 0;
/*       */ 
/*       */ 
/*       */     
/*  5764 */     for (int i = 0; i < selections.size(); i++) {
/*       */ 
/*       */       
/*       */       try {
/*       */ 
/*       */         
/*  5770 */         if (count < recordCount / tenth) {
/*       */           
/*  5772 */           count = recordCount / tenth;
/*  5773 */           HttpServletResponse sresponse = context.getResponse();
/*  5774 */           context.putDelivery("status", new String("start_gathering"));
/*  5775 */           context.putDelivery("percent", new String(String.valueOf(count * 10 + 50)));
/*  5776 */           context.includeJSP("status.jsp", "hiddenFrame");
/*  5777 */           sresponse.setContentType("text/plain");
/*  5778 */           sresponse.flushBuffer();
/*       */         } 
/*       */         
/*  5781 */         recordCount++;
/*       */         
/*  5783 */         Selection temp_sel = (Selection)selections.elementAt(i);
/*       */         
/*  5785 */         Selection sel = SelectionManager.getInstance().getSelectionHeader(temp_sel.getSelectionID());
/*       */         
/*  5787 */         sel.setDueDate(temp_sel.getDueDate());
/*  5788 */         sel.setDepartment(temp_sel.getDepartment());
/*  5789 */         sel.setTaskName(temp_sel.getTaskName());
/*  5790 */         sel.setCompletionDate(temp_sel.getCompletionDate());
/*       */         
/*  5792 */         String status = "";
/*  5793 */         if (sel.getSelectionStatus() != null) {
/*  5794 */           status = sel.getSelectionStatus().getName();
/*       */         }
/*  5796 */         if (!status.equalsIgnoreCase("TBS") && !status.equalsIgnoreCase("In the works")) {
/*       */ 
/*       */           
/*  5799 */           Calendar stDate = sel.getStreetDate();
/*       */           
/*  5801 */           if (stDate != null && ((beginStDate != null && stDate.before(beginStDate)) || (endStDate != null && stDate.after(endStDate)))) {
/*       */             continue;
/*       */           }
/*       */         } 
/*       */         
/*  5806 */         if (!strDistribution.equalsIgnoreCase("") && !strDistribution.equalsIgnoreCase("All")) {
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*  5811 */           String selDistribution = "";
/*       */           
/*  5813 */           if (sel.getLabel().getDistribution() == 1) {
/*       */             
/*  5815 */             selDistribution = "East";
/*       */           }
/*  5817 */           else if (sel.getLabel().getDistribution() == 0) {
/*       */             
/*  5819 */             selDistribution = "West";
/*       */           } 
/*       */           
/*  5822 */           if (!selDistribution.equalsIgnoreCase(strDistribution.trim())) {
/*       */             continue;
/*       */           }
/*       */         } 
/*       */         
/*  5827 */         if (!strLabelContact.trim().equalsIgnoreCase("0") && !strLabelContact.trim().equalsIgnoreCase("")) {
/*       */           
/*  5829 */           if (sel.getLabelContact() == null) {
/*       */             continue;
/*       */           }
/*  5832 */           String selLabelContactName = Integer.toString(sel.getLabelContact().getUserId());
/*       */           
/*  5834 */           if (!selLabelContactName.equalsIgnoreCase(strLabelContact)) {
/*       */             continue;
/*       */           }
/*       */         } 
/*       */         
/*  5839 */         if (!strUmlContact.trim().equalsIgnoreCase("0") && !strUmlContact.trim().equalsIgnoreCase("")) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*  5845 */           SelectionManager.getInstance().getSelectionManufacturingSubDetail(sel);
/*       */           
/*  5847 */           if (sel.getUmlContact() == null) {
/*       */             continue;
/*       */           }
/*       */ 
/*       */           
/*  5852 */           String selUmlContactName = Integer.toString(sel.getUmlContact().getUserId());
/*       */           
/*  5854 */           if (!selUmlContactName.equalsIgnoreCase(strUmlContact)) {
/*       */             continue;
/*       */           }
/*       */         } 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  5863 */         if (!strReleaseType.equalsIgnoreCase("") && !strReleaseType.equalsIgnoreCase("All")) {
/*       */           
/*  5865 */           String releaseType = "";
/*  5866 */           if (sel.getReleaseType() != null) {
/*       */             
/*  5868 */             releaseType = (sel.getReleaseType().getName() != null) ? sel.getReleaseType().getName() : "";
/*  5869 */             if (!releaseType.equalsIgnoreCase(strReleaseType)) {
/*       */               continue;
/*       */             }
/*       */           } else {
/*       */             continue;
/*       */           } 
/*       */         } 
/*       */ 
/*       */ 
/*       */         
/*  5879 */         String selectionStatus = SelectionManager.getLookupObjectValue(sel.getSelectionStatus()).trim();
/*  5880 */         if (!strStatus.equalsIgnoreCase("") && !strStatus.equalsIgnoreCase("All")) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*  5889 */           if (strStatus.trim().equalsIgnoreCase("active")) {
/*       */             
/*  5891 */             if (!selectionStatus.equalsIgnoreCase("TBS") && !selectionStatus.equalsIgnoreCase("ITW") && !selectionStatus.equalsIgnoreCase("active")) {
/*       */               continue;
/*       */             }
/*  5894 */           } else if (strStatus.trim().equalsIgnoreCase("Active, excluding TBS")) {
/*       */             
/*  5896 */             if (!selectionStatus.trim().equalsIgnoreCase("active")) {
/*       */               continue;
/*       */             }
/*  5899 */           } else if (strStatus.trim().equalsIgnoreCase("TBS")) {
/*       */             
/*  5901 */             if (!selectionStatus.trim().equalsIgnoreCase("TBS") && !selectionStatus.trim().equalsIgnoreCase("ITW")) {
/*       */               continue;
/*       */             }
/*  5904 */           } else if (strStatus.trim().equalsIgnoreCase("All, excluding TBS")) {
/*       */ 
/*       */             
/*  5907 */             if (!selectionStatus.trim().equalsIgnoreCase("active") && !selectionStatus.trim().equalsIgnoreCase("CLOSED")) {
/*       */               continue;
/*       */             }
/*  5910 */           } else if (strStatus.trim().equalsIgnoreCase("Cancelled")) {
/*       */             
/*  5912 */             if (!selectionStatus.equalsIgnoreCase("CANCEL"))
/*       */             {
/*       */               continue;
/*       */             
/*       */             }
/*       */           }
/*  5918 */           else if (!selectionStatus.equalsIgnoreCase("CLOSED")) {
/*       */ 
/*       */             
/*       */             continue;
/*       */           
/*       */           }
/*       */         
/*       */         }
/*  5926 */         else if (!selectionStatus.equalsIgnoreCase("CANCEL")) {
/*       */           continue;
/*       */         } 
/*       */ 
/*       */         
/*  5931 */         if (!strArtist.trim().equals("") && sel.getArtist() != null) {
/*       */           
/*  5933 */           String artistUpperCase = sel.getArtist().trim().toUpperCase();
/*       */           
/*  5935 */           if (!artistUpperCase.startsWith(strArtist.trim().toUpperCase())) {
/*       */             continue;
/*       */           }
/*       */         } 
/*       */         
/*  5940 */         if (!strFamily.equals("") && !strFamily.equals("0"))
/*       */         {
/*  5942 */           if (sel.getFamily() == null || !strFamily.equals(sel.getFamily().getIdentity())) {
/*       */             continue;
/*       */           }
/*       */         }
/*       */         
/*  5947 */         if (!strCompany.equals("") && !strCompany.equals("0"))
/*       */         {
/*       */           
/*  5950 */           if (sel.getCompany() == null || !strCompany.equals(sel.getCompany().getStructureID())) {
/*       */             continue;
/*       */           }
/*       */         }
/*       */         
/*  5955 */         if (!strLabel.equals("") && !strLabel.equals("0"))
/*       */         {
/*  5957 */           if (sel.getLabel() == null || !strLabel.equals(sel.getLabel().getStructureID())) {
/*       */             continue;
/*       */           }
/*       */         }
/*       */         
/*  5962 */         if (bParent) {
/*       */           
/*  5964 */           String prefixId = "";
/*       */           
/*  5966 */           if (sel.getPrefixID() != null) {
/*  5967 */             prefixId = SelectionManager.getLookupObjectValue(sel.getPrefixID());
/*       */           }
/*  5969 */           if (!sel.getTitleID().equalsIgnoreCase(String.valueOf(prefixId) + sel.getSelectionNo())) {
/*       */             continue;
/*       */           }
/*       */         } 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  5977 */         if (strSubconfiguration != null && 
/*  5978 */           !strSubconfiguration[0].trim().equalsIgnoreCase("0") && 
/*  5979 */           !strSubconfiguration[0].trim().equalsIgnoreCase("") && 
/*  5980 */           !strSubconfiguration[0].trim().equalsIgnoreCase("all")) {
/*       */ 
/*       */           
/*  5983 */           if (sel.getSelectionSubConfig() != null && sel.getSelectionConfig() != null) {
/*  5984 */             String selConfigurationName = sel.getSelectionConfig().getSelectionConfigurationAbbreviation();
/*  5985 */             String selSubconfigurationName = sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation();
/*       */             
/*  5987 */             boolean subconfigFnd = false;
/*  5988 */             for (int x = 0; x < strSubconfiguration.length; x++) {
/*  5989 */               String txtvalue = strSubconfiguration[x];
/*  5990 */               String txtconfigcode = txtvalue.substring(1, txtvalue.indexOf("]"));
/*  5991 */               String txtsubconfigcode = txtvalue.substring(txtvalue.indexOf("]") + 1, txtvalue.length());
/*  5992 */               if (selConfigurationName.equalsIgnoreCase(txtconfigcode) && 
/*  5993 */                 selSubconfigurationName.equalsIgnoreCase(txtsubconfigcode)) {
/*  5994 */                 subconfigFnd = true;
/*       */                 break;
/*       */               } 
/*       */             } 
/*  5998 */             if (!subconfigFnd)
/*       */             {
/*       */               continue;
/*       */             
/*       */             }
/*       */           
/*       */           }
/*       */         
/*       */         }
/*  6007 */         else if (strConfiguration != null && !strConfiguration[0].trim().equalsIgnoreCase("0") && !strConfiguration[0].trim().equalsIgnoreCase("") && 
/*  6008 */           !strConfiguration[0].trim().equalsIgnoreCase("all")) {
/*       */           
/*  6010 */           if (sel.getSelectionConfig() != null) {
/*  6011 */             String selConfigurationName = sel.getSelectionConfig().getSelectionConfigurationAbbreviation();
/*       */             
/*  6013 */             boolean configFnd = false;
/*  6014 */             for (int x = 0; x < strConfiguration.length; x++) {
/*  6015 */               if (selConfigurationName.equalsIgnoreCase(strConfiguration[x])) {
/*  6016 */                 configFnd = true;
/*       */                 break;
/*       */               } 
/*       */             } 
/*  6020 */             if (!configFnd) {
/*       */               continue;
/*       */             }
/*       */           } 
/*       */         } 
/*       */ 
/*       */         
/*  6027 */         selectionsForReport.addElement(sel);
/*       */         continue;
/*  6029 */       } catch (Exception exception) {
/*       */         continue;
/*       */       } 
/*       */     } 
/*       */     
/*  6034 */     return selectionsForReport;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   protected static Vector filterMCAImpactSelectionsWithReportCriteria(Vector selections, Context context) {
/*  6050 */     Vector selectionsForReport = new Vector();
/*  6051 */     Form reportForm = (Form)context.getSessionValue("reportForm");
/*       */     
/*  6053 */     if (selections == null || selections.size() == 0) {
/*  6054 */       return selectionsForReport;
/*       */     }
/*       */     
/*  6057 */     Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && reportForm.getStringValue("beginDate").length() > 0) ? getDate(reportForm.getStringValue("beginDate")) : null;
/*  6058 */     if (beginStDate == null) {
/*       */       
/*  6060 */       beginStDate = Calendar.getInstance();
/*  6061 */       beginStDate.setTime(new Date(0L));
/*       */     } 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  6067 */     beginStDate.set(11, 0);
/*  6068 */     beginStDate.set(12, 0);
/*  6069 */     beginStDate.set(13, 0);
/*  6070 */     beginStDate.set(14, 0);
/*       */     
/*  6072 */     Calendar endStDate = (reportForm.getStringValue("endDate") != null && reportForm.getStringValue("endDate").length() > 0) ? getDate(reportForm.getStringValue("endDate")) : null;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  6079 */     if (endStDate != null) {
/*       */       
/*  6081 */       endStDate.set(11, 23);
/*  6082 */       endStDate.set(12, 59);
/*  6083 */       endStDate.set(13, 59);
/*  6084 */       endStDate.set(14, 999);
/*       */     } 
/*       */     
/*  6087 */     String strDistribution = (reportForm.getStringValue("distribution") != null) ? reportForm.getStringValue("distribution") : "";
/*  6088 */     String strLabelContact = (reportForm.getStringValue("contact") != null) ? reportForm.getStringValue("contact") : "";
/*  6089 */     String strUmlContact = (reportForm.getStringValue("umlContact") != null) ? reportForm.getStringValue("umlContact") : "";
/*  6090 */     String strFuture2 = (reportForm.getStringValue("Future2") != null) ? reportForm.getStringValue("Future2") : "";
/*       */     
/*  6092 */     String strReleaseType = (reportForm.getStringValue("releaseType") != null) ? reportForm.getStringValue("releaseType") : "";
/*  6093 */     String strStatus = (reportForm.getStringValue("status") != null) ? reportForm.getStringValue("status") : "";
/*  6094 */     String strArtist = (reportForm.getStringValue("artist") != null) ? reportForm.getStringValue("artist") : "";
/*  6095 */     String strFamily = (reportForm.getStringValue("family") != null) ? reportForm.getStringValue("family") : "";
/*  6096 */     String strCompany = (reportForm.getStringValue("company") != null) ? reportForm.getStringValue("company") : "";
/*  6097 */     String strLabel = (reportForm.getStringValue("Label") != null) ? reportForm.getStringValue("Label") : "";
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  6106 */     String[] strConfiguration = null;
/*       */     try {
/*  6108 */       MilestoneFormDropDownMenu configList = (MilestoneFormDropDownMenu)reportForm.getElement("configurationList");
/*  6109 */       if (configList != null) {
/*  6110 */         ArrayList configListAl = configList.getStringValues();
/*  6111 */         if (configListAl != null) {
/*       */           
/*  6113 */           strConfiguration = new String[configListAl.size()];
/*  6114 */           strConfiguration = (String[])configListAl.toArray(strConfiguration);
/*       */         } 
/*       */       } 
/*  6117 */     } catch (Exception e) {
/*  6118 */       e.printStackTrace();
/*  6119 */       System.out.println("<<< Configuration vector execption " + e.getMessage());
/*       */     } 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  6126 */     String[] strSubconfiguration = null;
/*       */     try {
/*  6128 */       MilestoneFormDropDownMenu subconfigList = (MilestoneFormDropDownMenu)reportForm.getElement("subconfigurationList");
/*  6129 */       if (subconfigList != null) {
/*  6130 */         ArrayList subconfigListAl = subconfigList.getStringValues();
/*  6131 */         if (subconfigListAl != null) {
/*       */           
/*  6133 */           strSubconfiguration = new String[subconfigListAl.size()];
/*  6134 */           strSubconfiguration = (String[])subconfigListAl.toArray(strSubconfiguration);
/*       */         } 
/*       */       } 
/*  6137 */     } catch (Exception e) {
/*  6138 */       e.printStackTrace();
/*  6139 */       System.out.println("<<< Subconfiguration vector execption " + e.getMessage());
/*       */     } 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  6147 */     boolean bParent = (reportForm.getElement("ParentsOnly") != null) ? ((FormCheckBox)reportForm.getElement("ParentsOnly")).isChecked() : 0;
/*       */     
/*  6149 */     int totalCount = selections.size();
/*  6150 */     int tenth = 0;
/*       */     
/*  6152 */     tenth = totalCount / 5;
/*       */     
/*  6154 */     if (tenth < 1) {
/*  6155 */       tenth = 1;
/*       */     }
/*       */     
/*       */     try {
/*  6159 */       HttpServletResponse sresponse = context.getResponse();
/*  6160 */       context.putDelivery("status", new String("start_gathering"));
/*  6161 */       context.putDelivery("percent", new String("10"));
/*  6162 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  6163 */       sresponse.setContentType("text/plain");
/*  6164 */       sresponse.flushBuffer();
/*       */     }
/*  6166 */     catch (Exception exception) {}
/*       */ 
/*       */ 
/*       */     
/*  6170 */     int recordCount = 0;
/*  6171 */     int count = 0;
/*       */ 
/*       */ 
/*       */     
/*  6175 */     for (int i = 0; i < selections.size(); i++) {
/*       */ 
/*       */       
/*       */       try {
/*       */ 
/*       */         
/*  6181 */         if (count < recordCount / tenth) {
/*       */           
/*  6183 */           count = recordCount / tenth;
/*  6184 */           HttpServletResponse sresponse = context.getResponse();
/*  6185 */           context.putDelivery("status", new String("start_gathering"));
/*  6186 */           context.putDelivery("percent", new String(String.valueOf(count * 10 + 50)));
/*  6187 */           context.includeJSP("status.jsp", "hiddenFrame");
/*  6188 */           sresponse.setContentType("text/plain");
/*  6189 */           sresponse.flushBuffer();
/*       */         } 
/*       */         
/*  6192 */         recordCount++;
/*       */         
/*  6194 */         Selection sel = (Selection)selections.elementAt(i);
/*  6195 */         sel = SelectionManager.getInstance().getSelectionHeader(sel.getSelectionID());
/*       */         
/*  6197 */         String status = "";
/*  6198 */         if (sel.getSelectionStatus() != null) {
/*  6199 */           status = sel.getSelectionStatus().getName();
/*       */         }
/*  6201 */         if (!status.equalsIgnoreCase("TBS") && !status.equalsIgnoreCase("In the works"))
/*       */         {
/*       */           
/*  6204 */           Calendar calendar = sel.getStreetDate();
/*       */         }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  6222 */         if (!strDistribution.equalsIgnoreCase("") && !strDistribution.equalsIgnoreCase("All")) {
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*  6227 */           String selDistribution = "";
/*       */           
/*  6229 */           if (sel.getLabel().getDistribution() == 1) {
/*       */             
/*  6231 */             selDistribution = "East";
/*       */           }
/*  6233 */           else if (sel.getLabel().getDistribution() == 0) {
/*       */             
/*  6235 */             selDistribution = "West";
/*       */           } 
/*       */           
/*  6238 */           if (!selDistribution.equalsIgnoreCase(strDistribution.trim())) {
/*       */             continue;
/*       */           }
/*       */         } 
/*       */         
/*  6243 */         if (!strLabelContact.trim().equalsIgnoreCase("0") && !strLabelContact.trim().equalsIgnoreCase("")) {
/*       */           
/*  6245 */           if (sel.getLabelContact() == null) {
/*       */             continue;
/*       */           }
/*  6248 */           String selLabelContactName = Integer.toString(sel.getLabelContact().getUserId());
/*       */           
/*  6250 */           if (!selLabelContactName.equalsIgnoreCase(strLabelContact)) {
/*       */             continue;
/*       */           }
/*       */         } 
/*       */         
/*  6255 */         if (!strUmlContact.trim().equalsIgnoreCase("0") && !strUmlContact.trim().equalsIgnoreCase("")) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*  6261 */           SelectionManager.getInstance().getSelectionManufacturingSubDetail(sel);
/*       */           
/*  6263 */           if (sel.getUmlContact() == null) {
/*       */             continue;
/*       */           }
/*       */ 
/*       */           
/*  6268 */           String selUmlContactName = Integer.toString(sel.getUmlContact().getUserId());
/*       */           
/*  6270 */           if (!selUmlContactName.equalsIgnoreCase(strUmlContact)) {
/*       */             continue;
/*       */           }
/*       */         } 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  6279 */         if (!strReleaseType.equalsIgnoreCase("") && !strReleaseType.equalsIgnoreCase("All")) {
/*       */           
/*  6281 */           String releaseType = "";
/*  6282 */           if (sel.getReleaseType() != null) {
/*       */             
/*  6284 */             releaseType = (sel.getReleaseType().getName() != null) ? sel.getReleaseType().getName() : "";
/*  6285 */             if (!releaseType.equalsIgnoreCase(strReleaseType)) {
/*       */               continue;
/*       */             }
/*       */           } else {
/*       */             continue;
/*       */           } 
/*       */         } 
/*       */ 
/*       */ 
/*       */         
/*  6295 */         String selectionStatus = SelectionManager.getLookupObjectValue(sel.getSelectionStatus()).trim();
/*  6296 */         if (!strStatus.equalsIgnoreCase("") && !strStatus.equalsIgnoreCase("All")) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*  6303 */           if (strStatus.trim().equalsIgnoreCase("active")) {
/*       */             
/*  6305 */             if (!selectionStatus.equalsIgnoreCase("TBS") && !selectionStatus.equalsIgnoreCase("ITW") && !selectionStatus.equalsIgnoreCase("active")) {
/*       */               continue;
/*       */             }
/*  6308 */           } else if (strStatus.trim().equalsIgnoreCase("Active, excluding TBS")) {
/*       */             
/*  6310 */             if (!selectionStatus.trim().equalsIgnoreCase("active")) {
/*       */               continue;
/*       */             }
/*  6313 */           } else if (strStatus.trim().equalsIgnoreCase("TBS")) {
/*       */             
/*  6315 */             if (!selectionStatus.trim().equalsIgnoreCase("TBS") && !selectionStatus.trim().equalsIgnoreCase("ITW")) {
/*       */               continue;
/*       */             }
/*  6318 */           } else if (strStatus.trim().equalsIgnoreCase("All, excluding TBS")) {
/*       */ 
/*       */             
/*  6321 */             if (!selectionStatus.trim().equalsIgnoreCase("active") && !selectionStatus.trim().equalsIgnoreCase("CLOSED")) {
/*       */               continue;
/*       */             }
/*  6324 */           } else if (strStatus.trim().equalsIgnoreCase("Cancelled")) {
/*       */             
/*  6326 */             if (!selectionStatus.equalsIgnoreCase("CANCEL"))
/*       */             {
/*       */               continue;
/*       */             
/*       */             }
/*       */           }
/*  6332 */           else if (!selectionStatus.equalsIgnoreCase("CLOSED")) {
/*       */ 
/*       */             
/*       */             continue;
/*       */           
/*       */           }
/*       */         
/*       */         }
/*  6340 */         else if (!selectionStatus.equalsIgnoreCase("CANCEL")) {
/*       */           continue;
/*       */         } 
/*       */ 
/*       */ 
/*       */         
/*  6346 */         if (!strArtist.trim().equals("") && sel.getArtist() != null) {
/*       */           
/*  6348 */           String artistUpperCase = sel.getArtist().trim().toUpperCase();
/*       */           
/*  6350 */           if (!artistUpperCase.startsWith(strArtist.trim().toUpperCase())) {
/*       */             continue;
/*       */           }
/*       */         } 
/*       */         
/*  6355 */         if (!strFamily.equals("") && !strFamily.equals("0"))
/*       */         {
/*  6357 */           if (sel.getFamily() == null || !strFamily.equals(sel.getFamily().getIdentity())) {
/*       */             continue;
/*       */           }
/*       */         }
/*       */         
/*  6362 */         if (!strCompany.equals("") && !strCompany.equals("0"))
/*       */         {
/*  6364 */           if (sel.getCompany() == null || !strCompany.equals(sel.getCompany().getStructureID())) {
/*       */             continue;
/*       */           }
/*       */         }
/*       */         
/*  6369 */         if (!strLabel.equals("") && !strLabel.equals("0"))
/*       */         {
/*  6371 */           if (sel.getLabel() == null || !strLabel.equals(sel.getLabel().getStructureID())) {
/*       */             continue;
/*       */           }
/*       */         }
/*       */         
/*  6376 */         if (bParent) {
/*       */           
/*  6378 */           String prefixId = "";
/*       */           
/*  6380 */           if (sel.getPrefixID() != null) {
/*  6381 */             prefixId = SelectionManager.getLookupObjectValue(sel.getPrefixID());
/*       */           }
/*  6383 */           if (!sel.getTitleID().equalsIgnoreCase(String.valueOf(prefixId) + sel.getSelectionNo())) {
/*       */             continue;
/*       */           }
/*       */         } 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  6415 */         if (strSubconfiguration != null && 
/*  6416 */           !strSubconfiguration[0].trim().equalsIgnoreCase("0") && 
/*  6417 */           !strSubconfiguration[0].trim().equalsIgnoreCase("") && 
/*  6418 */           !strSubconfiguration[0].trim().equalsIgnoreCase("all")) {
/*       */ 
/*       */           
/*  6421 */           if (sel.getSelectionSubConfig() != null && sel.getSelectionConfig() != null) {
/*  6422 */             String selConfigurationName = sel.getSelectionConfig().getSelectionConfigurationAbbreviation();
/*  6423 */             String selSubconfigurationName = sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation();
/*       */             
/*  6425 */             boolean subconfigFnd = false;
/*  6426 */             for (int x = 0; x < strSubconfiguration.length; x++) {
/*  6427 */               String txtvalue = strSubconfiguration[x];
/*  6428 */               String txtconfigcode = txtvalue.substring(1, txtvalue.indexOf("]"));
/*  6429 */               String txtsubconfigcode = txtvalue.substring(txtvalue.indexOf("]") + 1, txtvalue.length());
/*  6430 */               if (selConfigurationName.equalsIgnoreCase(txtconfigcode) && 
/*  6431 */                 selSubconfigurationName.equalsIgnoreCase(txtsubconfigcode)) {
/*  6432 */                 subconfigFnd = true;
/*       */                 break;
/*       */               } 
/*       */             } 
/*  6436 */             if (!subconfigFnd)
/*       */             {
/*       */               continue;
/*       */             
/*       */             }
/*       */           
/*       */           }
/*       */         
/*       */         }
/*  6445 */         else if (strConfiguration != null && !strConfiguration[0].trim().equalsIgnoreCase("0") && !strConfiguration[0].trim().equalsIgnoreCase("") && 
/*  6446 */           !strConfiguration[0].trim().equalsIgnoreCase("all")) {
/*       */           
/*  6448 */           if (sel.getSelectionConfig() != null) {
/*  6449 */             String selConfigurationName = sel.getSelectionConfig().getSelectionConfigurationAbbreviation();
/*       */             
/*  6451 */             boolean configFnd = false;
/*  6452 */             for (int x = 0; x < strConfiguration.length; x++) {
/*  6453 */               if (selConfigurationName.equalsIgnoreCase(strConfiguration[x])) {
/*  6454 */                 configFnd = true;
/*       */                 break;
/*       */               } 
/*       */             } 
/*  6458 */             if (!configFnd) {
/*       */               continue;
/*       */             }
/*       */           } 
/*       */         } 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  6467 */         selectionsForReport.addElement(sel);
/*       */         
/*       */         continue;
/*  6470 */       } catch (Exception exception) {
/*       */         continue;
/*       */       } 
/*       */     } 
/*       */     
/*  6475 */     return selectionsForReport;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Vector getSelectionsForReport(Context context) {
/*  6490 */     ReportSelections reportSelObj = new ReportSelections(context);
/*  6491 */     System.out.println("MilestoneHelper::getSelectionsForReport()");
/*  6492 */     return reportSelObj.getFinalSelections();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Vector getFilteredSelectionsForReport(Context context) {
/*  6507 */     User user = (User)context.getSessionValue("user");
/*  6508 */     int userId = 0;
/*  6509 */     if (user != null) {
/*  6510 */       userId = user.getUserId();
/*       */     }
/*       */     
/*  6513 */     Form reportForm = (Form)context.getSessionValue("reportForm");
/*  6514 */     boolean bScheduled = (reportForm.getElement("ScheduledReleases") != null) ? ((FormCheckBox)reportForm.getElement("ScheduledReleases")).isChecked() : 0;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  6520 */     Vector companies = getUserCompanies(context);
/*  6521 */     Company company = null;
/*  6522 */     Vector precache = new Vector();
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  6577 */     StringBuffer query = new StringBuffer();
/*       */     
/*  6579 */     query.append("SELECT header.release_id, header.title_id, header.selection_no, header.prefix,   pfm.units_per_set, detail.due_date AS taskDue, detail.comments AS taskComments,   detail.owner AS taskOwner, detail.completion_date AS taskComplete,   detail.status AS taskStatus, detail.day_of_week AS taskDayOfWeek,   detail.weeks_to_release AS taskWeeksToRelease, detail.key_task_indicator AS taskKey,    detail.vendor AS taskVendor, detail.authorization_name AS taskAuthName,   detail.authorization_date AS taskAuthDate, detail.last_updated_ck, header.project_no,   header.title, header.artist_first_name, header.artist_last_name,   header.artist, header.artist_first_name + ' ' + header.artist_last_name AS fl_artist ,   header.side_a_title, header.side_b_title, header.product_line, header.release_type,   header.configuration, header.sub_configuration, header.UPC, header.price_code, header.genre,   header.family_id, header.company_id, header.division_id, header.label_id, header.street_date,   header.international_date, header.coordinator_contacts, header.contact_id, header.status,   header.hold_indicator, header.hold_reason, header.comments, header.special_pkg_indicator,   header.pd_indicator, header.packaging, header.impact_date, header.last_updated_on,   header.last_updated_by, header.entered_on, mfg.uml_id, mfg.plant, mfg.distribution,   mfg.order_qty, mfg.complete_qty, mfg.order_comments, Task.task_id, Task.department,   Task.name, Task.abbrev_id  FROM  Release_Header header with (nolock)  LEFT JOIN Release_Subdetail mfg with(nolock)   ON header.release_id = mfg.release_id ");
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  6601 */     if (bScheduled) {
/*       */       
/*  6603 */       query.append(" INNER JOIN release_detail detail with (nolock) ON header.[release_id] = detail.[release_id]");
/*       */     } else {
/*       */       
/*  6606 */       query.append(" LEFT JOIN release_detail detail with (nolock) ON header.[release_id] = detail.[release_id]");
/*       */     } 
/*       */ 
/*       */     
/*  6610 */     query.append("  LEFT JOIN Task   ON detail.task_id = Task.task_id   LEFT JOIN Pfm_Selection pfm   ON header.release_id = pfm.release_id    WHERE ");
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  6621 */     String strLabelContact = (reportForm.getStringValue("contact") != null) ? reportForm.getStringValue("contact") : "";
/*  6622 */     String strUmlContact = (reportForm.getStringValue("umlContact") != null) ? reportForm.getStringValue("umlContact") : "";
/*  6623 */     String strReleaseType = (reportForm.getStringValue("releaseType") != null) ? reportForm.getStringValue("releaseType") : "";
/*  6624 */     String strStatus = (reportForm.getStringValue("status") != null) ? reportForm.getStringValue("status") : "";
/*  6625 */     String strArtist = (reportForm.getStringValue("artist") != null) ? reportForm.getStringValue("artist") : "";
/*  6626 */     String strFamily = (reportForm.getStringValue("family") != null) ? reportForm.getStringValue("family") : "";
/*  6627 */     String strCompany = (reportForm.getStringValue("company") != null) ? reportForm.getStringValue("company") : "";
/*  6628 */     String strLabel = (reportForm.getStringValue("Label") != null) ? reportForm.getStringValue("Label") : "";
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  6636 */     String[] strConfiguration = null;
/*       */     try {
/*  6638 */       MilestoneFormDropDownMenu configList = (MilestoneFormDropDownMenu)reportForm.getElement("configurationList");
/*  6639 */       if (configList != null) {
/*  6640 */         ArrayList configListAl = configList.getStringValues();
/*  6641 */         if (configListAl != null) {
/*       */           
/*  6643 */           strConfiguration = new String[configListAl.size()];
/*  6644 */           strConfiguration = (String[])configListAl.toArray(strConfiguration);
/*       */         } 
/*       */       } 
/*  6647 */     } catch (Exception e) {
/*  6648 */       e.printStackTrace();
/*  6649 */       System.out.println("<<< Configuration vector execption " + e.getMessage());
/*       */     } 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  6656 */     String[] strSubconfiguration = null;
/*       */     try {
/*  6658 */       MilestoneFormDropDownMenu subconfigList = (MilestoneFormDropDownMenu)reportForm.getElement("subconfigurationList");
/*  6659 */       if (subconfigList != null) {
/*  6660 */         ArrayList subconfigListAl = subconfigList.getStringValues();
/*  6661 */         if (subconfigListAl != null) {
/*       */           
/*  6663 */           strSubconfiguration = new String[subconfigListAl.size()];
/*  6664 */           strSubconfiguration = (String[])subconfigListAl.toArray(strSubconfiguration);
/*       */         } 
/*       */       } 
/*  6667 */     } catch (Exception e) {
/*  6668 */       e.printStackTrace();
/*  6669 */       System.out.println("<<< Subconfiguration vector execption " + e.getMessage());
/*       */     } 
/*       */ 
/*       */     
/*  6673 */     boolean bParent = (reportForm.getElement("ParentsOnly") != null) ? ((FormCheckBox)reportForm.getElement("ParentsOnly")).isChecked() : 0;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  6680 */     if (!strCompany.equalsIgnoreCase("") && !strCompany.equalsIgnoreCase("0")) {
/*       */       
/*  6682 */       query.append(" header.[company_id]= " + strCompany);
/*       */     }
/*       */     else {
/*       */       
/*  6686 */       StringList list = new StringList(" OR ");
/*       */       
/*  6688 */       query.append(String.valueOf(list.toString()) + " (");
/*       */       
/*  6690 */       for (int i = 0; i < companies.size(); i++) {
/*       */         
/*  6692 */         company = (Company)companies.get(i);
/*  6693 */         if (company != null)
/*       */         {
/*  6695 */           list.add(" company_id = " + company.getStructureID());
/*       */         }
/*       */       } 
/*       */       
/*  6699 */       list.add(" company_id = " + company.getStructureID());
/*       */       
/*  6701 */       query.append(String.valueOf(list.toString()) + ") ");
/*       */     } 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  6707 */     if (!strFamily.equalsIgnoreCase("") && !strFamily.equalsIgnoreCase("0")) {
/*  6708 */       query.append(" AND header.[family_id]= " + strFamily);
/*       */     }
/*       */ 
/*       */ 
/*       */     
/*  6713 */     if (!strLabel.equalsIgnoreCase("") && !strLabel.equalsIgnoreCase("0")) {
/*  6714 */       query.append(" AND header.[label_id]= " + strLabel);
/*       */     }
/*       */ 
/*       */ 
/*       */     
/*  6719 */     if (!strArtist.equalsIgnoreCase("")) {
/*  6720 */       query.append(" AND header.[artist] LIKE '%" + escapeSingleQuotes(strArtist) + "%'");
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  6727 */     if (!strStatus.equalsIgnoreCase("") && !strStatus.equalsIgnoreCase("all"))
/*       */     {
/*  6729 */       if (strStatus.equalsIgnoreCase("Active, excluding TBS")) {
/*  6730 */         query.append(" AND header.[status] ='active'");
/*  6731 */       } else if (strStatus.equalsIgnoreCase("TBS")) {
/*  6732 */         query.append(" AND (header.[status] ='TBS' OR header.[status] ='ITW')");
/*  6733 */       } else if (strStatus.equalsIgnoreCase("active")) {
/*  6734 */         query.append(" AND (header.[status] ='active' OR header.[status] ='TBS' OR header.[status] ='ITW')");
/*  6735 */       } else if (strStatus.equalsIgnoreCase("All, excluding TBS")) {
/*  6736 */         query.append(" AND (header.[status] ='active' OR header.[status] ='closed' OR header.[status] ='cancel')");
/*       */       } else {
/*  6738 */         query.append(" AND header.[status] ='" + strStatus + "'");
/*       */       } 
/*       */     }
/*       */ 
/*       */ 
/*       */     
/*  6744 */     if (!strReleaseType.equalsIgnoreCase("") && !strReleaseType.equalsIgnoreCase("all"))
/*       */     {
/*  6746 */       if (strReleaseType.equalsIgnoreCase("commercial")) {
/*  6747 */         query.append(" AND header.[release_type] ='CO'");
/*       */       } else {
/*  6749 */         query.append(" AND header.[release_type] ='PR'");
/*       */       } 
/*       */     }
/*       */ 
/*       */ 
/*       */     
/*  6755 */     if (!strLabelContact.equalsIgnoreCase("") && !strLabelContact.equalsIgnoreCase("0")) {
/*  6756 */       query.append(" AND header.[contact_id] =" + strLabelContact);
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  6781 */     if (strSubconfiguration != null && 
/*  6782 */       !strSubconfiguration[0].trim().equalsIgnoreCase("0") && 
/*  6783 */       !strSubconfiguration[0].trim().equalsIgnoreCase("") && 
/*  6784 */       !strSubconfiguration[0].trim().equalsIgnoreCase("all")) {
/*       */       
/*  6786 */       boolean addOr = false;
/*  6787 */       query.append(" AND (");
/*  6788 */       for (int x = 0; x < strSubconfiguration.length; x++) {
/*  6789 */         if (addOr)
/*  6790 */           query.append(" OR "); 
/*  6791 */         String txtvalue = strSubconfiguration[x];
/*  6792 */         String txtconfigcode = txtvalue.substring(1, txtvalue.indexOf("]"));
/*  6793 */         String txtsubconfigcode = txtvalue.substring(txtvalue.indexOf("]") + 1, txtvalue.length());
/*  6794 */         query.append(" (header.[configuration] = '" + txtconfigcode.trim() + "' AND ");
/*  6795 */         query.append(" header.[sub_configuration] = '" + txtsubconfigcode.trim() + "') ");
/*  6796 */         addOr = true;
/*       */       } 
/*  6798 */       query.append(") ");
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     }
/*  6806 */     else if (strConfiguration != null && !strConfiguration[0].equalsIgnoreCase("") && !strConfiguration[0].equalsIgnoreCase("0") && 
/*  6807 */       !strConfiguration[0].equalsIgnoreCase("all")) {
/*       */       
/*  6809 */       boolean addOr = false;
/*  6810 */       query.append(" AND (");
/*  6811 */       for (int x = 0; x < strConfiguration.length; x++) {
/*  6812 */         if (addOr) {
/*  6813 */           query.append(" OR ");
/*       */         }
/*  6815 */         query.append(" header.[configuration] = '" + strConfiguration[x].trim() + "' ");
/*  6816 */         addOr = true;
/*       */       } 
/*  6818 */       query.append(") ");
/*       */     } 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  6828 */     if (!strUmlContact.equalsIgnoreCase("") && !strUmlContact.equalsIgnoreCase("0")) {
/*  6829 */       query.append(" AND mfg.[uml_id] =" + strUmlContact);
/*       */     }
/*  6831 */     String beginDate = "";
/*  6832 */     beginDate = (reportForm.getStringValue("beginDate") != null && reportForm.getStringValue("beginDate").length() > 0) ? reportForm.getStringValue("beginDate") : "";
/*       */     
/*  6834 */     String endDate = "";
/*  6835 */     endDate = (reportForm.getStringValue("endDate") != null && reportForm.getStringValue("endDate").length() > 0) ? reportForm.getStringValue("endDate") : "";
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  6842 */     if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase("")) {
/*  6843 */       query.append(" AND ((header.[status] ='TBS' OR header.[status] ='ITW') OR (");
/*       */     }
/*  6845 */     if (!beginDate.equalsIgnoreCase("")) {
/*  6846 */       query.append(" street_date >= '" + escapeSingleQuotes(beginDate) + "'");
/*       */     }
/*  6848 */     if (!endDate.equalsIgnoreCase(""))
/*       */     {
/*  6850 */       if (!beginDate.equalsIgnoreCase("")) {
/*  6851 */         query.append(" AND street_date <= '" + escapeSingleQuotes(endDate) + "'");
/*       */       } else {
/*  6853 */         query.append(" street_date <= '" + escapeSingleQuotes(endDate) + "'");
/*       */       } 
/*       */     }
/*  6856 */     if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase("")) query.append("))");
/*       */     
/*  6858 */     query.append(" ORDER BY header.[release_id], header.[artist], header.[street_date], header.[title]");
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  6864 */     System.out.println("\n\n===========Report Query Start===============\n\n" + query);
/*  6865 */     System.out.println("\n\n===========Report Query End===============\n\n");
/*  6866 */     JdbcConnector connector = getConnector(query.toString());
/*       */ 
/*       */     
/*  6869 */     connector.runQuery();
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  6876 */     Vector selections = null;
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  6881 */     int totalCount = 0;
/*  6882 */     int tenth = 0;
/*       */ 
/*       */     
/*  6885 */     JdbcConnector connectorCount = getConnector(query.toString());
/*  6886 */     connectorCount.runQuery();
/*       */     
/*  6888 */     while (connectorCount.more()) {
/*       */       
/*  6890 */       totalCount++;
/*  6891 */       connectorCount.next();
/*       */     } 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  6897 */     tenth = totalCount / 10;
/*       */     
/*  6899 */     if (tenth < 1) {
/*  6900 */       tenth = 1;
/*       */     }
/*       */     
/*       */     try {
/*  6904 */       HttpServletResponse sresponse = context.getResponse();
/*  6905 */       context.putDelivery("status", new String("start_gathering"));
/*  6906 */       context.putDelivery("percent", new String("10"));
/*  6907 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  6908 */       sresponse.setContentType("text/plain");
/*  6909 */       sresponse.flushBuffer();
/*       */     }
/*  6911 */     catch (Exception exception) {}
/*       */ 
/*       */ 
/*       */     
/*  6915 */     int recordCount = 0;
/*  6916 */     int count = 0;
/*       */ 
/*       */ 
/*       */     
/*  6920 */     selections = new Vector();
/*       */     
/*  6922 */     while (connector.more()) {
/*       */ 
/*       */       
/*       */       try {
/*       */ 
/*       */ 
/*       */         
/*  6929 */         if (count < recordCount / tenth) {
/*       */           
/*  6931 */           count = recordCount / tenth;
/*  6932 */           HttpServletResponse sresponse = context.getResponse();
/*  6933 */           context.putDelivery("status", new String("start_gathering"));
/*  6934 */           context.putDelivery("percent", new String(String.valueOf(count * 10)));
/*  6935 */           context.includeJSP("status.jsp", "hiddenFrame");
/*  6936 */           sresponse.setContentType("text/plain");
/*  6937 */           sresponse.flushBuffer();
/*       */         } 
/*       */         
/*  6940 */         recordCount++;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  6951 */         if (bParent) {
/*       */           
/*  6953 */           String prefixId = "";
/*  6954 */           String tmpTitleId = connector.getField("title_id", "");
/*  6955 */           String tmpSelectionNo = connector.getField("selection_no", "");
/*       */           
/*  6957 */           prefixId = SelectionManager.getLookupObjectValue((PrefixCode)getLookupObject(connector.getField("prefix", ""), Cache.getPrefixCodes()));
/*       */           
/*  6959 */           if (!tmpTitleId.equalsIgnoreCase(String.valueOf(prefixId) + tmpSelectionNo)) {
/*       */             
/*  6961 */             connector.next();
/*       */             
/*       */             continue;
/*       */           } 
/*       */         } 
/*       */         
/*  6967 */         int numberOfUnits = 0;
/*       */         
/*       */         try {
/*  6970 */           numberOfUnits = connector.getIntegerField("pfm.[units_per_set]");
/*       */         }
/*  6972 */         catch (Exception exception) {}
/*       */ 
/*       */         
/*  6975 */         Selection selection = null;
/*       */         
/*  6977 */         selection = new Selection();
/*  6978 */         selection.setSelectionID(connector.getIntegerField("release_id"));
/*       */         
/*  6980 */         String selectionNo = "";
/*  6981 */         if (connector.getFieldByName("selection_no") != null)
/*  6982 */           selectionNo = connector.getFieldByName("selection_no"); 
/*  6983 */         selection.setSelectionNo(selectionNo);
/*       */         
/*  6985 */         selection.setProjectID(connector.getField("project_no", ""));
/*       */         
/*  6987 */         String titleId = "";
/*  6988 */         if (connector.getFieldByName("title_id") != null)
/*  6989 */           titleId = connector.getFieldByName("title_id"); 
/*  6990 */         selection.setTitleID(titleId);
/*       */         
/*  6992 */         selection.setTitle(connector.getField("title", ""));
/*  6993 */         selection.setArtistFirstName(connector.getField("artist_first_name", ""));
/*  6994 */         selection.setArtistLastName(connector.getField("artist_last_name", ""));
/*  6995 */         selection.setArtist(connector.getField("artist", ""));
/*  6996 */         selection.setFlArtist(connector.getField("fl_artist", ""));
/*       */         
/*  6998 */         selection.setASide(connector.getField("side_a_title", ""));
/*  6999 */         selection.setBSide(connector.getField("side_b_title", ""));
/*       */         
/*  7001 */         selection.setProductCategory((ProductCategory)getLookupObject(connector.getField("product_line"), 
/*  7002 */               Cache.getProductCategories()));
/*  7003 */         selection.setReleaseType((ReleaseType)getLookupObject(connector.getField("release_type"), 
/*  7004 */               Cache.getReleaseTypes()));
/*       */         
/*  7006 */         selection.setSelectionConfig(getSelectionConfigObject(connector.getField("configuration"), 
/*  7007 */               Cache.getSelectionConfigs()));
/*  7008 */         selection.setSelectionSubConfig(getSelectionSubConfigObject(connector.getField("sub_configuration"), 
/*  7009 */               selection.getSelectionConfig()));
/*       */         
/*  7011 */         selection.setUpc(connector.getField("upc", ""));
/*       */         
/*  7013 */         String sellCodeString = connector.getFieldByName("price_code");
/*  7014 */         if (sellCodeString != null)
/*       */         {
/*  7016 */           selection.setPriceCode(SelectionManager.getInstance().getPriceCode(sellCodeString));
/*       */         }
/*       */         
/*  7019 */         selection.setSellCode(sellCodeString);
/*       */         
/*  7021 */         selection.setGenre((Genre)getLookupObject(connector.getField("genre"), 
/*  7022 */               Cache.getMusicTypes()));
/*  7023 */         selection.setFamily((Family)getStructureObject(connector.getIntegerField("family_id")));
/*  7024 */         selection.setCompany((Company)getStructureObject(connector.getIntegerField("company_id")));
/*  7025 */         selection.setDivision((Division)getStructureObject(connector.getIntegerField("division_id")));
/*  7026 */         selection.setLabel((Label)getStructureObject(connector.getIntegerField("label_id")));
/*       */         
/*  7028 */         String streetDateString = connector.getFieldByName("street_date");
/*  7029 */         if (streetDateString != null) {
/*  7030 */           selection.setStreetDate(getDatabaseDate(streetDateString));
/*       */         }
/*  7032 */         String internationalDateString = connector.getFieldByName("international_date");
/*  7033 */         if (internationalDateString != null) {
/*  7034 */           selection.setInternationalDate(getDatabaseDate(internationalDateString));
/*       */         }
/*  7036 */         selection.setOtherContact(connector.getField("coordinator_contacts", ""));
/*       */         
/*  7038 */         if (UserManager.getInstance().getUser(connector.getIntegerField("contact_id")) != null) {
/*  7039 */           selection.setLabelContact(UserManager.getInstance().getUser(connector.getIntegerField("contact_id")));
/*       */         }
/*  7041 */         selection.setSelectionStatus((SelectionStatus)getLookupObject(connector.getField("status"), 
/*  7042 */               Cache.getSelectionStatusList()));
/*  7043 */         selection.setHoldSelection(connector.getBoolean("hold_indicator"));
/*       */         
/*  7045 */         selection.setHoldReason(connector.getField("hold_reason", ""));
/*  7046 */         selection.setComments(connector.getField("comments", ""));
/*  7047 */         selection.setSpecialPackaging(connector.getBoolean("special_pkg_indicator"));
/*  7048 */         selection.setNumberOfUnits(numberOfUnits);
/*  7049 */         selection.setPressAndDistribution(connector.getBoolean("pd_indicator"));
/*       */         
/*  7051 */         selection.setPrefixID((PrefixCode)getLookupObject(connector.getField("prefix"), Cache.getPrefixCodes()));
/*  7052 */         selection.setSelectionPackaging(connector.getField("packaging", ""));
/*       */         
/*  7054 */         String impactDateString = connector.getFieldByName("impact_date");
/*  7055 */         if (impactDateString != null) {
/*  7056 */           selection.setImpactDate(getDatabaseDate(impactDateString));
/*       */         }
/*  7058 */         String lastUpdateDateString = connector.getFieldByName("last_updated_on");
/*  7059 */         if (lastUpdateDateString != null) {
/*  7060 */           selection.setLastUpdateDate(getDatabaseDate(lastUpdateDateString));
/*       */         }
/*  7062 */         selection.setLastUpdatingUser(UserManager.getInstance().getUser(connector.getIntegerField("last_updated_by")));
/*       */ 
/*       */         
/*  7065 */         String originDateString = connector.getFieldByName("entered_on");
/*  7066 */         if (originDateString != null) {
/*  7067 */           selection.setOriginDate(getDatabaseDate(originDateString));
/*       */         }
/*       */ 
/*       */ 
/*       */         
/*  7072 */         User umlContact = UserManager.getInstance().getUser(connector.getIntegerField("mfg.[uml_id]"));
/*  7073 */         selection.setUmlContact(umlContact);
/*  7074 */         selection.setPlant(getLookupObject(connector.getField("mfg.[plant]"), Cache.getVendors()));
/*  7075 */         selection.setDistribution(getLookupObject(connector.getField("mfg.[distribution]"), Cache.getDistributionCodes()));
/*  7076 */         selection.setPoQuantity(connector.getIntegerField("mfg.[order_qty]"));
/*  7077 */         selection.setCompletedQuantity(connector.getIntegerField("mfg.[complete_qty]"));
/*  7078 */         selection.setManufacturingComments(connector.getField("mfg.[order_comments]", ""));
/*  7079 */         selection.setPrice(connector.getFloat("mfg.[list_price]"));
/*       */         
/*  7081 */         selection.setFullSelection(true);
/*       */         
/*  7083 */         String strDistribution = (reportForm.getStringValue("distribution") != null) ? reportForm.getStringValue("distribution") : "";
/*  7084 */         String strFuture2 = (reportForm.getStringValue("Future2") != null) ? reportForm.getStringValue("Future2") : "";
/*       */ 
/*       */         
/*  7087 */         if (!strDistribution.equalsIgnoreCase("") && !strDistribution.equalsIgnoreCase("All")) {
/*       */           
/*  7089 */           String selDistribution = SelectionManager.getLookupObjectValue(selection.getDistribution());
/*       */           
/*  7091 */           if (!selDistribution.equalsIgnoreCase(strDistribution.trim())) {
/*       */             continue;
/*       */           }
/*       */         } 
/*  7095 */         int nextReleaseId = connector.getIntegerField("release_id");
/*  7096 */         Schedule schedule = new Schedule();
/*  7097 */         schedule.setSelectionID(nextReleaseId);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  7104 */         Vector precacheSchedule = new Vector();
/*  7105 */         while (connector.more() && connector.getIntegerField("release_id") == nextReleaseId) {
/*       */           
/*  7107 */           ScheduledTask scheduledTask = null;
/*       */           
/*  7109 */           scheduledTask = new ScheduledTask();
/*       */ 
/*       */           
/*  7112 */           scheduledTask.setReleaseID(connector.getIntegerField("release_Id"));
/*       */ 
/*       */           
/*  7115 */           scheduledTask.setTaskID(connector.getIntegerField("task_id"));
/*       */ 
/*       */           
/*  7118 */           scheduledTask.setOwner((Family)getStructureObject(connector.getIntegerField("taskOwner")));
/*       */ 
/*       */           
/*  7121 */           String dueDateString = connector.getField("taskDue", "");
/*  7122 */           if (dueDateString.length() > 0) {
/*  7123 */             scheduledTask.setDueDate(getDatabaseDate(dueDateString));
/*       */           }
/*       */           
/*  7126 */           String completionDateString = connector.getField("taskComplete", "");
/*  7127 */           if (completionDateString.length() > 0) {
/*  7128 */             scheduledTask.setCompletionDate(getDatabaseDate(completionDateString));
/*       */           }
/*       */           
/*  7131 */           String taskStatus = connector.getField("taskStatus", "");
/*  7132 */           if (taskStatus.length() > 1) {
/*  7133 */             scheduledTask.setScheduledTaskStatus(taskStatus);
/*       */           }
/*       */           
/*  7136 */           int day = connector.getIntegerField("taskDayOfWeek");
/*  7137 */           if (day > 0) {
/*  7138 */             scheduledTask.setDayOfTheWeek(new Day(connector.getIntegerField("taskDayOfWeek")));
/*       */           }
/*       */           
/*  7141 */           int weeks = connector.getIntegerField("taskWeeksToRelease");
/*  7142 */           if (weeks > 0) {
/*  7143 */             scheduledTask.setWeeksToRelease(connector.getIntegerField("taskWeeksToRelease"));
/*       */           }
/*       */           
/*  7146 */           String vendorString = connector.getField("taskVendor", "");
/*  7147 */           if (vendorString.length() > 0) {
/*  7148 */             scheduledTask.setVendor(vendorString);
/*       */           }
/*       */           
/*  7151 */           int taskAbbrevID = connector.getIntegerField("abbrev_id");
/*  7152 */           scheduledTask.setTaskAbbreviationID(taskAbbrevID);
/*       */ 
/*       */           
/*  7155 */           int taskID = connector.getIntegerField("task_id");
/*  7156 */           scheduledTask.setScheduledTaskID(taskID);
/*       */ 
/*       */           
/*  7159 */           String taskDept = connector.getField("department", "");
/*  7160 */           scheduledTask.setDepartment(taskDept);
/*       */ 
/*       */           
/*  7163 */           scheduledTask.setKeyTask(connector.getBoolean("taskKey"));
/*       */ 
/*       */           
/*  7166 */           scheduledTask.setAuthorizationName(connector.getField("taskAuthName", ""));
/*       */ 
/*       */           
/*  7169 */           String authDateString = connector.getField("taskAuthDate", "");
/*  7170 */           if (authDateString.length() > 0) {
/*  7171 */             scheduledTask.setAuthorizationDate(getDatabaseDate(authDateString));
/*       */           }
/*       */           
/*  7174 */           String comments = connector.getField("taskComments", "");
/*  7175 */           scheduledTask.setComments(comments);
/*       */ 
/*       */           
/*  7178 */           scheduledTask.setName(connector.getField("name", ""));
/*       */ 
/*       */           
/*  7181 */           int selNo = scheduledTask.getReleaseID();
/*  7182 */           int taskId = scheduledTask.getTaskID();
/*  7183 */           Vector multCompleteDates = ScheduleManager.getInstance().getMultCompleteDates(selNo, taskId);
/*  7184 */           scheduledTask.setMultCompleteDates(multCompleteDates);
/*       */ 
/*       */           
/*  7187 */           scheduledTask.setAllowMultCompleteDatesFlag(connector.getBoolean("allowMultcompleteDatesFlag"));
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*  7192 */           precacheSchedule.add(scheduledTask);
/*       */           
/*  7194 */           scheduledTask = null;
/*       */           
/*  7196 */           if (connector.more()) {
/*       */             
/*  7198 */             connector.next();
/*  7199 */             recordCount++;
/*       */             
/*       */             continue;
/*       */           } 
/*       */           
/*       */           break;
/*       */         } 
/*  7206 */         schedule.setTasks(precacheSchedule);
/*       */         
/*  7208 */         selection.setSchedule(schedule);
/*       */         
/*  7210 */         selections.add(selection);
/*       */       
/*       */       }
/*  7213 */       catch (Exception exception) {}
/*       */     } 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  7220 */     connector.close();
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  7225 */     return selections;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Vector getTaskDueSelectionsForReport(Context context) {
/*  7243 */     User user = (User)context.getSessionValue("user");
/*  7244 */     int userId = 0;
/*  7245 */     if (user != null)
/*  7246 */       userId = user.getUserId(); 
/*  7247 */     Form reportForm = (Form)context.getSessionValue("reportForm");
/*       */     
/*  7249 */     String beginDate = "";
/*  7250 */     beginDate = (reportForm.getStringValue("beginDate") != null && reportForm.getStringValue("beginDate").length() > 0) ? reportForm.getStringValue("beginDate") : "";
/*       */     
/*  7252 */     String endDate = "";
/*  7253 */     endDate = (reportForm.getStringValue("endDate") != null && reportForm.getStringValue("endDate").length() > 0) ? reportForm.getStringValue("endDate") : "";
/*       */     
/*  7255 */     String beginDueDate = "";
/*  7256 */     beginDueDate = (reportForm.getStringValue("beginDueDate") != null && reportForm.getStringValue("beginDueDate").length() > 0) ? reportForm.getStringValue("beginDueDate") : "";
/*       */     
/*  7258 */     String endDueDate = "";
/*  7259 */     endDueDate = (reportForm.getStringValue("endDueDate") != null && reportForm.getStringValue("endDueDate").length() > 0) ? reportForm.getStringValue("endDueDate") : "";
/*       */     
/*  7261 */     StringBuffer dueDateQuery = new StringBuffer(200);
/*       */     
/*  7263 */     if (!beginDueDate.equalsIgnoreCase("")) {
/*  7264 */       dueDateQuery.append("AND due_date >= '" + escapeSingleQuotes(beginDueDate) + "'");
/*       */     }
/*  7266 */     if (!endDueDate.equalsIgnoreCase(""))
/*       */     {
/*  7268 */       if (!beginDueDate.equalsIgnoreCase("")) {
/*  7269 */         dueDateQuery.append(" AND due_date <= '" + escapeSingleQuotes(endDueDate) + "'");
/*       */       } else {
/*  7271 */         dueDateQuery.append(" due_date <= '" + escapeSingleQuotes(endDueDate) + "'");
/*       */       } 
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  7278 */     Vector companies = getUserCompanies(context);
/*  7279 */     Company company = null;
/*  7280 */     Vector precache = new Vector();
/*  7281 */     StringBuffer query = new StringBuffer();
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  7307 */     query.append("SELECT header.release_id, header.title_id, header.selection_no, header.prefix,   pfm.units_per_set, detail.due_date AS taskDue, detail.comments AS taskComments,   detail.owner AS taskOwner, detail.completion_date AS taskComplete,   detail.status AS taskStatus, detail.day_of_week AS taskDayOfWeek,   detail.weeks_to_release AS taskWeeksToRelease, detail.key_task_indicator AS taskKey,    detail.vendor AS taskVendor, detail.authorization_name AS taskAuthName,   detail.authorization_date AS taskAuthDate, detail.last_updated_ck, header.project_no,   header.title, header.artist_first_name, header.artist_last_name, header.artist,   header.side_a_title, header.side_b_title, header.product_line, header.release_type,   header.configuration, header.sub_configuration, header.UPC, header.price_code, header.genre,   header.family_id, header.company_id, header.division_id, header.label_id, header.street_date,   header.international_date, header.coordinator_contacts, header.contact_id, header.status,   header.hold_indicator, header.hold_reason, header.comments, header.special_pkg_indicator,   header.pd_indicator, header.packaging, header.impact_date, header.last_updated_on,   header.last_updated_by, header.entered_on, mfg.uml_id, mfg.plant, mfg.distribution,   mfg.order_qty, mfg.complete_qty, mfg.order_comments, Task.task_id, Task.department,   Task.name, Task.abbrev_id  FROM vi_release_header header with (nolock) LEFT JOIN vi_release_subdetail mfg with (nolock) ON header.[release_id] = mfg.[release_id] LEFT JOIN vi_release_detail detail with (nolock) ON header.[release_id] = detail.[release_id] " + 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  7329 */         dueDateQuery.toString() + 
/*  7330 */         " INNER JOIN vi_Task task with (nolock)" + 
/*  7331 */         " ON detail.[task_id] = task.[task_id]" + 
/*  7332 */         " LEFT JOIN vi_pfm_selection pfm with (nolock)" + 
/*  7333 */         " ON header.[release_id] = pfm.[release_id]" + 
/*  7334 */         " WHERE ");
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  7341 */     String strLabelContact = (reportForm.getStringValue("contact") != null) ? reportForm.getStringValue("contact") : "";
/*  7342 */     String strUmlContact = (reportForm.getStringValue("umlContact") != null) ? reportForm.getStringValue("umlContact") : "";
/*  7343 */     String strReleaseType = (reportForm.getStringValue("releaseType") != null) ? reportForm.getStringValue("releaseType") : "";
/*  7344 */     String strStatus = (reportForm.getStringValue("status") != null) ? reportForm.getStringValue("status") : "";
/*  7345 */     String strArtist = (reportForm.getStringValue("artist") != null) ? reportForm.getStringValue("artist") : "";
/*  7346 */     String strFamily = (reportForm.getStringValue("family") != null) ? reportForm.getStringValue("family") : "";
/*  7347 */     String strCompany = (reportForm.getStringValue("company") != null) ? reportForm.getStringValue("company") : "";
/*  7348 */     String strLabel = (reportForm.getStringValue("Label") != null) ? reportForm.getStringValue("Label") : "";
/*       */     
/*  7350 */     boolean bParent = (reportForm.getElement("ParentsOnly") != null) ? ((FormCheckBox)reportForm.getElement("ParentsOnly")).isChecked() : 0;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  7357 */     if (!strCompany.equalsIgnoreCase("") && !strCompany.equalsIgnoreCase("0")) {
/*       */       
/*  7359 */       query.append(" header.[company_id]= " + strCompany);
/*       */     }
/*       */     else {
/*       */       
/*  7363 */       StringList list = new StringList(" OR ");
/*       */       
/*  7365 */       query.append(String.valueOf(list.toString()) + " (");
/*       */       
/*  7367 */       for (int i = 0; i < companies.size(); i++) {
/*       */         
/*  7369 */         company = (Company)companies.get(i);
/*  7370 */         if (company != null)
/*       */         {
/*  7372 */           list.add(" company_id = " + company.getStructureID());
/*       */         }
/*       */       } 
/*       */       
/*  7376 */       list.add(" company_id = " + company.getStructureID());
/*       */       
/*  7378 */       query.append(String.valueOf(list.toString()) + ") ");
/*       */     } 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  7384 */     if (!strFamily.equalsIgnoreCase("") && !strFamily.equalsIgnoreCase("0")) {
/*  7385 */       query.append(" AND header.[family_id]= " + strFamily);
/*       */     }
/*       */ 
/*       */ 
/*       */     
/*  7390 */     if (!strLabel.equalsIgnoreCase("") && !strLabel.equalsIgnoreCase("0")) {
/*  7391 */       query.append(" AND header.[label_id]= " + strLabel);
/*       */     }
/*       */ 
/*       */ 
/*       */     
/*  7396 */     if (!strArtist.equalsIgnoreCase("")) {
/*  7397 */       query.append(" AND header.[artist] LIKE '%" + escapeSingleQuotes(strArtist) + "%'");
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  7403 */     if (!strStatus.equalsIgnoreCase("") && !strStatus.equalsIgnoreCase("all"))
/*       */     {
/*  7405 */       if (strStatus.equalsIgnoreCase("Active, excluding TBS")) {
/*  7406 */         query.append(" AND header.[status] ='active'");
/*  7407 */       } else if (strStatus.equalsIgnoreCase("TBS")) {
/*  7408 */         query.append(" AND (header.[status] ='TBS' OR header.[status] ='ITW')");
/*  7409 */       } else if (strStatus.equalsIgnoreCase("active")) {
/*  7410 */         query.append(" AND (header.[status] ='active' OR header.[status] ='TBS' OR header.[status] ='ITW')");
/*  7411 */       } else if (strStatus.equalsIgnoreCase("All, excluding TBS")) {
/*  7412 */         query.append(" AND (header.[status] ='active' OR header.[status] ='closed' OR header.[status] ='cancel')");
/*       */       } else {
/*  7414 */         query.append(" AND header.[status] ='" + strStatus + "'");
/*       */       } 
/*       */     }
/*       */ 
/*       */ 
/*       */     
/*  7420 */     if (!strReleaseType.equalsIgnoreCase("") && !strReleaseType.equalsIgnoreCase("all"))
/*       */     {
/*  7422 */       if (strReleaseType.equalsIgnoreCase("commercial")) {
/*  7423 */         query.append(" AND header.[release_type] ='CO'");
/*       */       } else {
/*  7425 */         query.append(" AND header.[release_type] ='PR'");
/*       */       } 
/*       */     }
/*       */ 
/*       */ 
/*       */     
/*  7431 */     if (!strLabelContact.equalsIgnoreCase("") && !strLabelContact.equalsIgnoreCase("0")) {
/*  7432 */       query.append(" AND header.[contact_id] =" + strLabelContact);
/*       */     }
/*       */ 
/*       */ 
/*       */     
/*  7437 */     if (!strUmlContact.equalsIgnoreCase("") && !strUmlContact.equalsIgnoreCase("0")) {
/*  7438 */       query.append(" AND mfg.[uml_id] =" + strUmlContact);
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  7446 */     if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase("")) {
/*  7447 */       query.append(" AND ((header.[status] ='TBS' OR header.[status] ='ITW') OR (");
/*       */     }
/*  7449 */     if (!beginDate.equalsIgnoreCase("")) {
/*  7450 */       query.append(" street_date >= '" + escapeSingleQuotes(beginDate) + "'");
/*       */     }
/*  7452 */     if (!endDate.equalsIgnoreCase(""))
/*       */     {
/*  7454 */       if (!beginDate.equalsIgnoreCase("")) {
/*  7455 */         query.append(" AND street_date <= '" + escapeSingleQuotes(endDate) + "'");
/*       */       } else {
/*  7457 */         query.append(" street_date <= '" + escapeSingleQuotes(endDate) + "'");
/*       */       } 
/*       */     }
/*  7460 */     if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase("")) query.append("))");
/*       */     
/*  7462 */     query.append(" ORDER BY header.[release_id], header.[artist], header.[street_date], header.[title]");
/*       */ 
/*       */     
/*  7465 */     JdbcConnector connector = getConnector(query.toString());
/*       */     
/*  7467 */     connector.setForwardOnly(false);
/*       */ 
/*       */     
/*  7470 */     connector.runQuery();
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  7480 */     Vector selections = null;
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  7485 */     int totalCount = 0;
/*  7486 */     int tenth = 0;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  7501 */     totalCount = connector.getRowCount();
/*       */     
/*  7503 */     tenth = totalCount / 10;
/*       */     
/*  7505 */     if (tenth < 1) {
/*  7506 */       tenth = 1;
/*       */     }
/*       */     
/*       */     try {
/*  7510 */       HttpServletResponse sresponse = context.getResponse();
/*  7511 */       context.putDelivery("status", new String("start_gathering"));
/*  7512 */       context.putDelivery("percent", new String("10"));
/*  7513 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  7514 */       sresponse.setContentType("text/plain");
/*  7515 */       sresponse.flushBuffer();
/*       */     }
/*  7517 */     catch (Exception exception) {}
/*       */ 
/*       */ 
/*       */     
/*  7521 */     int recordCount = 0;
/*  7522 */     int count = 0;
/*       */ 
/*       */ 
/*       */     
/*  7526 */     selections = new Vector();
/*       */     
/*  7528 */     while (connector.more()) {
/*       */ 
/*       */       
/*       */       try {
/*       */ 
/*       */ 
/*       */         
/*  7535 */         if (count < recordCount / tenth) {
/*       */           
/*  7537 */           count = recordCount / tenth;
/*  7538 */           HttpServletResponse sresponse = context.getResponse();
/*  7539 */           context.putDelivery("status", new String("start_gathering"));
/*  7540 */           context.putDelivery("percent", new String(String.valueOf(count * 10)));
/*  7541 */           context.includeJSP("status.jsp", "hiddenFrame");
/*  7542 */           sresponse.setContentType("text/plain");
/*  7543 */           sresponse.flushBuffer();
/*       */         } 
/*       */         
/*  7546 */         recordCount++;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  7557 */         if (bParent) {
/*       */           
/*  7559 */           String prefixId = "";
/*  7560 */           String tmpTitleId = connector.getField("title_id", "");
/*  7561 */           String tmpSelectionNo = connector.getField("selection_no", "");
/*       */           
/*  7563 */           prefixId = SelectionManager.getLookupObjectValue((PrefixCode)getLookupObject(connector.getField("prefix", ""), Cache.getPrefixCodes()));
/*       */           
/*  7565 */           if (!tmpTitleId.equalsIgnoreCase(String.valueOf(prefixId) + tmpSelectionNo)) {
/*       */             
/*  7567 */             connector.next();
/*       */             
/*       */             continue;
/*       */           } 
/*       */         } 
/*       */         
/*  7573 */         int numberOfUnits = 0;
/*       */         
/*       */         try {
/*  7576 */           numberOfUnits = connector.getIntegerField("pfm.[units_per_set]");
/*       */         }
/*  7578 */         catch (Exception exception) {}
/*       */ 
/*       */         
/*  7581 */         Selection selection = null;
/*       */         
/*  7583 */         selection = new Selection();
/*  7584 */         selection.setSelectionID(connector.getIntegerField("release_id"));
/*       */         
/*  7586 */         String selectionNo = "";
/*  7587 */         if (connector.getFieldByName("selection_no") != null)
/*  7588 */           selectionNo = connector.getFieldByName("selection_no"); 
/*  7589 */         selection.setSelectionNo(selectionNo);
/*       */         
/*  7591 */         selection.setProjectID(connector.getField("project_no", ""));
/*       */         
/*  7593 */         String titleId = "";
/*  7594 */         if (connector.getFieldByName("title_id") != null)
/*  7595 */           titleId = connector.getFieldByName("title_id"); 
/*  7596 */         selection.setTitleID(titleId);
/*       */         
/*  7598 */         selection.setTitle(connector.getField("title", ""));
/*  7599 */         selection.setArtistFirstName(connector.getField("artist_first_name", ""));
/*  7600 */         selection.setArtistLastName(connector.getField("artist_last_name", ""));
/*  7601 */         selection.setArtist(connector.getField("artist", ""));
/*  7602 */         selection.setASide(connector.getField("side_a_title", ""));
/*  7603 */         selection.setBSide(connector.getField("side_b_title", ""));
/*       */         
/*  7605 */         selection.setProductCategory((ProductCategory)getLookupObject(connector.getField("product_line"), 
/*  7606 */               Cache.getProductCategories()));
/*  7607 */         selection.setReleaseType((ReleaseType)getLookupObject(connector.getField("release_type"), 
/*  7608 */               Cache.getReleaseTypes()));
/*       */         
/*  7610 */         selection.setSelectionConfig(getSelectionConfigObject(connector.getField("configuration"), 
/*  7611 */               Cache.getSelectionConfigs()));
/*  7612 */         selection.setSelectionSubConfig(getSelectionSubConfigObject(connector.getField("sub_configuration"), 
/*  7613 */               selection.getSelectionConfig()));
/*       */         
/*  7615 */         selection.setUpc(connector.getField("upc", ""));
/*       */         
/*  7617 */         String sellCodeString = connector.getFieldByName("price_code");
/*  7618 */         if (sellCodeString != null)
/*       */         {
/*  7620 */           selection.setPriceCode(SelectionManager.getInstance().getPriceCode(sellCodeString));
/*       */         }
/*       */         
/*  7623 */         selection.setSellCode(sellCodeString);
/*       */         
/*  7625 */         selection.setGenre((Genre)getLookupObject(connector.getField("genre"), 
/*  7626 */               Cache.getMusicTypes()));
/*  7627 */         selection.setFamily((Family)getStructureObject(connector.getIntegerField("family_id")));
/*  7628 */         selection.setCompany((Company)getStructureObject(connector.getIntegerField("company_id")));
/*  7629 */         selection.setDivision((Division)getStructureObject(connector.getIntegerField("division_id")));
/*  7630 */         selection.setLabel((Label)getStructureObject(connector.getIntegerField("label_id")));
/*       */         
/*  7632 */         String streetDateString = connector.getFieldByName("street_date");
/*  7633 */         if (streetDateString != null) {
/*  7634 */           selection.setStreetDate(getDatabaseDate(streetDateString));
/*       */         }
/*  7636 */         String internationalDateString = connector.getFieldByName("international_date");
/*  7637 */         if (internationalDateString != null) {
/*  7638 */           selection.setInternationalDate(getDatabaseDate(internationalDateString));
/*       */         }
/*  7640 */         selection.setOtherContact(connector.getField("coordinator_contacts", ""));
/*       */         
/*  7642 */         if (UserManager.getInstance().getUser(connector.getIntegerField("contact_id")) != null) {
/*  7643 */           selection.setLabelContact(UserManager.getInstance().getUser(connector.getIntegerField("contact_id")));
/*       */         }
/*  7645 */         selection.setSelectionStatus((SelectionStatus)getLookupObject(connector.getField("status"), 
/*  7646 */               Cache.getSelectionStatusList()));
/*  7647 */         selection.setHoldSelection(connector.getBoolean("hold_indicator"));
/*       */         
/*  7649 */         selection.setHoldReason(connector.getField("hold_reason", ""));
/*  7650 */         selection.setComments(connector.getField("comments", ""));
/*  7651 */         selection.setSpecialPackaging(connector.getBoolean("special_pkg_indicator"));
/*  7652 */         selection.setNumberOfUnits(numberOfUnits);
/*  7653 */         selection.setPressAndDistribution(connector.getBoolean("pd_indicator"));
/*       */         
/*  7655 */         selection.setPrefixID((PrefixCode)getLookupObject(connector.getField("prefix"), Cache.getPrefixCodes()));
/*  7656 */         selection.setSelectionPackaging(connector.getField("packaging", ""));
/*       */         
/*  7658 */         String impactDateString = connector.getFieldByName("impact_date");
/*  7659 */         if (impactDateString != null) {
/*  7660 */           selection.setImpactDate(getDatabaseDate(impactDateString));
/*       */         }
/*  7662 */         String lastUpdateDateString = connector.getFieldByName("last_updated_on");
/*  7663 */         if (lastUpdateDateString != null) {
/*  7664 */           selection.setLastUpdateDate(getDatabaseDate(lastUpdateDateString));
/*       */         }
/*  7666 */         selection.setLastUpdatingUser(UserManager.getInstance().getUser(connector.getIntegerField("last_updated_by")));
/*       */ 
/*       */         
/*  7669 */         String originDateString = connector.getFieldByName("entered_on");
/*  7670 */         if (originDateString != null) {
/*  7671 */           selection.setOriginDate(getDatabaseDate(originDateString));
/*       */         }
/*       */ 
/*       */ 
/*       */         
/*  7676 */         User umlContact = UserManager.getInstance().getUser(connector.getIntegerField("mfg.[uml_id]"));
/*  7677 */         selection.setUmlContact(umlContact);
/*  7678 */         selection.setPlant(getLookupObject(connector.getField("mfg.[plant]"), Cache.getVendors()));
/*  7679 */         selection.setDistribution(getLookupObject(connector.getField("mfg.[distribution]"), Cache.getDistributionCodes()));
/*  7680 */         selection.setPoQuantity(connector.getIntegerField("mfg.[order_qty]"));
/*  7681 */         selection.setCompletedQuantity(connector.getIntegerField("mfg.[complete_qty]"));
/*  7682 */         selection.setManufacturingComments(connector.getField("mfg.[order_comments]", ""));
/*  7683 */         selection.setPrice(connector.getFloat("mfg.[list_price]"));
/*       */         
/*  7685 */         selection.setFullSelection(true);
/*       */         
/*  7687 */         String strDistribution = (reportForm.getStringValue("distribution") != null) ? reportForm.getStringValue("distribution") : "";
/*  7688 */         String strFuture2 = (reportForm.getStringValue("Future2") != null) ? reportForm.getStringValue("Future2") : "";
/*       */ 
/*       */         
/*  7691 */         if (!strDistribution.equalsIgnoreCase("") && !strDistribution.equalsIgnoreCase("All")) {
/*       */           
/*  7693 */           String selDistribution = SelectionManager.getLookupObjectValue(selection.getDistribution());
/*       */           
/*  7695 */           if (!selDistribution.equalsIgnoreCase(strDistribution.trim())) {
/*       */             continue;
/*       */           }
/*       */         } 
/*  7699 */         int nextReleaseId = connector.getIntegerField("release_id");
/*       */         
/*  7701 */         Schedule schedule = new Schedule();
/*  7702 */         schedule.setSelectionID(nextReleaseId);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  7711 */         Vector precacheSchedule = new Vector();
/*  7712 */         while (connector.more() && connector.getIntegerField("release_id") == nextReleaseId) {
/*       */           
/*  7714 */           ScheduledTask scheduledTask = null;
/*       */           
/*  7716 */           scheduledTask = new ScheduledTask();
/*       */ 
/*       */ 
/*       */           
/*  7720 */           scheduledTask.setReleaseID(connector.getIntegerField("release_Id"));
/*       */ 
/*       */           
/*  7723 */           scheduledTask.setTaskID(connector.getIntegerField("task_id"));
/*       */ 
/*       */           
/*  7726 */           scheduledTask.setOwner((Family)getStructureObject(connector.getIntegerField("taskOwner")));
/*       */ 
/*       */           
/*  7729 */           String dueDateString = connector.getField("taskDue", "");
/*  7730 */           if (dueDateString.length() > 0) {
/*  7731 */             scheduledTask.setDueDate(getDatabaseDate(dueDateString));
/*       */           }
/*       */           
/*  7734 */           String completionDateString = connector.getField("taskComplete", "");
/*  7735 */           if (completionDateString.length() > 0) {
/*  7736 */             scheduledTask.setCompletionDate(getDatabaseDate(completionDateString));
/*       */           }
/*       */           
/*  7739 */           String taskStatus = connector.getField("taskStatus", "");
/*  7740 */           if (taskStatus.length() > 1) {
/*  7741 */             scheduledTask.setScheduledTaskStatus(taskStatus);
/*       */           }
/*       */           
/*  7744 */           int day = connector.getIntegerField("taskDayOfWeek");
/*  7745 */           if (day > 0) {
/*  7746 */             scheduledTask.setDayOfTheWeek(new Day(connector.getIntegerField("taskDayOfWeek")));
/*       */           }
/*       */           
/*  7749 */           int weeks = connector.getIntegerField("taskWeeksToRelease");
/*  7750 */           if (weeks > 0) {
/*  7751 */             scheduledTask.setWeeksToRelease(connector.getIntegerField("taskWeeksToRelease"));
/*       */           }
/*       */           
/*  7754 */           String vendorString = connector.getField("taskVendor", "");
/*  7755 */           if (vendorString.length() > 0) {
/*  7756 */             scheduledTask.setVendor(vendorString);
/*       */           }
/*       */           
/*  7759 */           int taskAbbrevID = connector.getIntegerField("abbrev_id");
/*  7760 */           scheduledTask.setTaskAbbreviationID(taskAbbrevID);
/*       */ 
/*       */           
/*  7763 */           int taskID = connector.getIntegerField("task_id");
/*  7764 */           scheduledTask.setScheduledTaskID(taskID);
/*       */ 
/*       */           
/*  7767 */           String taskDept = connector.getField("department", "");
/*  7768 */           scheduledTask.setDepartment(taskDept);
/*       */ 
/*       */           
/*  7771 */           scheduledTask.setKeyTask(connector.getBoolean("taskKey"));
/*       */ 
/*       */           
/*  7774 */           scheduledTask.setAuthorizationName(connector.getField("taskAuthName", ""));
/*       */ 
/*       */           
/*  7777 */           String authDateString = connector.getField("taskAuthDate", "");
/*  7778 */           if (authDateString.length() > 0) {
/*  7779 */             scheduledTask.setAuthorizationDate(getDatabaseDate(authDateString));
/*       */           }
/*       */           
/*  7782 */           String comments = connector.getField("taskComments", "");
/*  7783 */           scheduledTask.setComments(comments);
/*       */ 
/*       */           
/*  7786 */           int selNo = scheduledTask.getReleaseID();
/*  7787 */           int taskId = scheduledTask.getTaskID();
/*  7788 */           Vector multCompleteDates = ScheduleManager.getInstance().getMultCompleteDates(selNo, taskId);
/*  7789 */           scheduledTask.setMultCompleteDates(multCompleteDates);
/*       */ 
/*       */           
/*  7792 */           scheduledTask.setAllowMultCompleteDatesFlag(connector.getBoolean("allowMultcompleteDatesFlag"));
/*       */ 
/*       */           
/*  7795 */           scheduledTask.setName(connector.getField("name", ""));
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*  7800 */           precacheSchedule.add(scheduledTask);
/*       */           
/*  7802 */           scheduledTask = null;
/*       */           
/*  7804 */           if (connector.more()) {
/*       */             
/*  7806 */             connector.next();
/*  7807 */             recordCount++;
/*       */             
/*       */             continue;
/*       */           } 
/*       */           
/*       */           break;
/*       */         } 
/*  7814 */         schedule.setTasks(precacheSchedule);
/*       */         
/*  7816 */         selection.setSchedule(schedule);
/*       */         
/*  7818 */         selections.add(selection);
/*       */       
/*       */       }
/*  7821 */       catch (Exception exception) {}
/*       */     } 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  7828 */     connector.close();
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  7833 */     return selections;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Vector getFullSelectionsForReport(Context context) {
/*  7850 */     User user = (User)context.getSessionValue("user");
/*  7851 */     int userId = 0;
/*  7852 */     if (user != null) {
/*  7853 */       userId = user.getUserId();
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  7859 */     Vector companies = getUserCompanies(context);
/*  7860 */     Company company = null;
/*  7861 */     Vector precache = new Vector();
/*  7862 */     StringBuffer query = new StringBuffer(200);
/*       */     
/*  7864 */     query.append("SELECT * FROM vi_release_header header with (nolock) LEFT JOIN vi_release_subdetail mfg with (nolock) ON header.[release_id] = mfg.[release_id] LEFT JOIN vi_pfm_selection pfm with (nolock) ON header.[release_id] = pfm.[release_id] WHERE ");
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  7874 */     Form reportForm = (Form)context.getSessionValue("reportForm");
/*       */     
/*  7876 */     String strLabelContact = (reportForm.getStringValue("contact") != null) ? reportForm.getStringValue("contact") : "";
/*  7877 */     String strUmlContact = (reportForm.getStringValue("umlContact") != null) ? reportForm.getStringValue("umlContact") : "";
/*  7878 */     String strReleaseType = (reportForm.getStringValue("releaseType") != null) ? reportForm.getStringValue("releaseType") : "";
/*  7879 */     String strStatus = (reportForm.getStringValue("status") != null) ? reportForm.getStringValue("status") : "";
/*  7880 */     String strArtist = (reportForm.getStringValue("artist") != null) ? reportForm.getStringValue("artist") : "";
/*  7881 */     String strFamily = (reportForm.getStringValue("family") != null) ? reportForm.getStringValue("family") : "";
/*  7882 */     String strCompany = (reportForm.getStringValue("company") != null) ? reportForm.getStringValue("company") : "";
/*  7883 */     String strLabel = (reportForm.getStringValue("Label") != null) ? reportForm.getStringValue("Label") : "";
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  7891 */     String[] strConfiguration = null;
/*       */     try {
/*  7893 */       MilestoneFormDropDownMenu configList = (MilestoneFormDropDownMenu)reportForm.getElement("configurationList");
/*  7894 */       if (configList != null) {
/*  7895 */         ArrayList configListAl = configList.getStringValues();
/*  7896 */         if (configListAl != null) {
/*       */           
/*  7898 */           strConfiguration = new String[configListAl.size()];
/*  7899 */           strConfiguration = (String[])configListAl.toArray(strConfiguration);
/*       */         } 
/*       */       } 
/*  7902 */     } catch (Exception e) {
/*  7903 */       e.printStackTrace();
/*  7904 */       System.out.println("<<< Configuration vector execption " + e.getMessage());
/*       */     } 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  7912 */     String[] strSubconfiguration = null;
/*       */     try {
/*  7914 */       MilestoneFormDropDownMenu subconfigList = (MilestoneFormDropDownMenu)reportForm.getElement("subconfigurationList");
/*  7915 */       if (subconfigList != null) {
/*  7916 */         ArrayList subconfigListAl = subconfigList.getStringValues();
/*  7917 */         if (subconfigListAl != null) {
/*       */           
/*  7919 */           strSubconfiguration = new String[subconfigListAl.size()];
/*  7920 */           strSubconfiguration = (String[])subconfigListAl.toArray(strSubconfiguration);
/*       */         } 
/*       */       } 
/*  7923 */     } catch (Exception e) {
/*  7924 */       e.printStackTrace();
/*  7925 */       System.out.println("<<< Subconfiguration vector execption " + e.getMessage());
/*       */     } 
/*       */     
/*  7928 */     boolean bParent = (reportForm.getElement("ParentsOnly") != null) ? ((FormCheckBox)reportForm.getElement("ParentsOnly")).isChecked() : 0;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  7935 */     if (!strCompany.equalsIgnoreCase("") && !strCompany.equalsIgnoreCase("0")) {
/*       */       
/*  7937 */       query.append(" header.[company_id]= " + strCompany);
/*       */     }
/*       */     else {
/*       */       
/*  7941 */       StringList list = new StringList(" OR ");
/*       */       
/*  7943 */       query.append(String.valueOf(list.toString()) + " (");
/*       */       
/*  7945 */       for (int i = 0; i < companies.size(); i++) {
/*       */         
/*  7947 */         company = (Company)companies.get(i);
/*  7948 */         if (company != null)
/*       */         {
/*  7950 */           list.add(" company_id = " + company.getStructureID());
/*       */         }
/*       */       } 
/*       */       
/*  7954 */       list.add(" company_id = " + company.getStructureID());
/*       */       
/*  7956 */       query.append(String.valueOf(list.toString()) + ") ");
/*       */     } 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  7962 */     if (!strFamily.equalsIgnoreCase("") && !strFamily.equalsIgnoreCase("0")) {
/*  7963 */       query.append(" AND header.[family_id]= " + strFamily);
/*       */     }
/*       */ 
/*       */ 
/*       */     
/*  7968 */     if (!strLabel.equalsIgnoreCase("") && !strLabel.equalsIgnoreCase("0")) {
/*  7969 */       query.append(" AND header.[label_id]= " + strLabel);
/*       */     }
/*       */ 
/*       */ 
/*       */     
/*  7974 */     if (!strArtist.equalsIgnoreCase("")) {
/*  7975 */       query.append(" AND header.[artist] LIKE '%" + escapeSingleQuotes(strArtist) + "%'");
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  7981 */     if (!strStatus.equalsIgnoreCase("") && !strStatus.equalsIgnoreCase("all"))
/*       */     {
/*  7983 */       if (strStatus.equalsIgnoreCase("Active, excluding TBS")) {
/*  7984 */         query.append(" AND header.[status] ='active'");
/*  7985 */       } else if (strStatus.equalsIgnoreCase("TBS")) {
/*  7986 */         query.append(" AND (header.[status] ='TBS' OR header.[status] ='ITW')");
/*  7987 */       } else if (strStatus.equalsIgnoreCase("active")) {
/*  7988 */         query.append(" AND (header.[status] ='active' OR header.[status] ='TBS' OR header.[status] ='ITW')");
/*  7989 */       } else if (strStatus.equalsIgnoreCase("All, excluding TBS")) {
/*  7990 */         query.append(" AND (header.[status] ='active' OR header.[status] ='closed' OR header.[status] ='cancel')");
/*       */       } else {
/*  7992 */         query.append(" AND header.[status] ='" + strStatus + "'");
/*       */       } 
/*       */     }
/*       */ 
/*       */ 
/*       */     
/*  7998 */     if (!strReleaseType.equalsIgnoreCase("") && !strReleaseType.equalsIgnoreCase("all"))
/*       */     {
/*  8000 */       if (strReleaseType.equalsIgnoreCase("commercial")) {
/*  8001 */         query.append(" AND header.[release_type] ='CO'");
/*       */       } else {
/*  8003 */         query.append(" AND header.[release_type] ='PR'");
/*       */       } 
/*       */     }
/*       */ 
/*       */ 
/*       */     
/*  8009 */     if (!strLabelContact.equalsIgnoreCase("") && !strLabelContact.equalsIgnoreCase("0")) {
/*  8010 */       query.append(" AND header.[contact_id] =" + strLabelContact);
/*       */     }
/*       */ 
/*       */     
/*  8014 */     if (strSubconfiguration != null && 
/*  8015 */       !strSubconfiguration[0].trim().equalsIgnoreCase("0") && 
/*  8016 */       !strSubconfiguration[0].trim().equalsIgnoreCase("") && 
/*  8017 */       !strSubconfiguration[0].trim().equalsIgnoreCase("all")) {
/*       */       
/*  8019 */       boolean addOr = false;
/*  8020 */       query.append(" AND (");
/*  8021 */       for (int x = 0; x < strSubconfiguration.length; x++) {
/*  8022 */         if (addOr)
/*  8023 */           query.append(" OR "); 
/*  8024 */         String txtvalue = strSubconfiguration[x];
/*  8025 */         String txtconfigcode = txtvalue.substring(1, txtvalue.indexOf("]"));
/*  8026 */         String txtsubconfigcode = txtvalue.substring(txtvalue.indexOf("]") + 1, txtvalue.length());
/*  8027 */         query.append(" (header.[configuration] = '" + txtconfigcode.trim() + "' AND ");
/*  8028 */         query.append(" header.[sub_configuration] = '" + txtsubconfigcode.trim() + "') ");
/*  8029 */         addOr = true;
/*       */       } 
/*  8031 */       query.append(") ");
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     }
/*  8039 */     else if (strConfiguration != null && !strConfiguration[0].equalsIgnoreCase("") && !strConfiguration[0].equalsIgnoreCase("0") && 
/*  8040 */       !strConfiguration[0].equalsIgnoreCase("all")) {
/*       */       
/*  8042 */       boolean addOr = false;
/*  8043 */       query.append(" AND (");
/*  8044 */       for (int x = 0; x < strConfiguration.length; x++) {
/*  8045 */         if (addOr) {
/*  8046 */           query.append(" OR ");
/*       */         }
/*  8048 */         query.append(" header.[configuration] = '" + strConfiguration[x].trim() + "' ");
/*  8049 */         addOr = true;
/*       */       } 
/*  8051 */       query.append(") ");
/*       */     } 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  8077 */     if (!strUmlContact.equalsIgnoreCase("") && !strUmlContact.equalsIgnoreCase("0")) {
/*  8078 */       query.append(" AND mfg.[uml_id] =" + strUmlContact);
/*       */     }
/*  8080 */     String beginDate = "";
/*  8081 */     beginDate = (reportForm.getStringValue("beginDate") != null && reportForm.getStringValue("beginDate").length() > 0) ? reportForm.getStringValue("beginDate") : "";
/*       */     
/*  8083 */     String endDate = "";
/*  8084 */     endDate = (reportForm.getStringValue("endDate") != null && reportForm.getStringValue("endDate").length() > 0) ? reportForm.getStringValue("endDate") : "";
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  8091 */     if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase("")) {
/*  8092 */       query.append(" AND ((header.[status] ='TBS' OR header.[status] ='ITW') OR (");
/*       */     }
/*  8094 */     if (!beginDate.equalsIgnoreCase("")) {
/*  8095 */       query.append(" street_date >= '" + escapeSingleQuotes(beginDate) + "'");
/*       */     }
/*  8097 */     if (!endDate.equalsIgnoreCase(""))
/*       */     {
/*  8099 */       if (!beginDate.equalsIgnoreCase("")) {
/*  8100 */         query.append(" AND street_date <= '" + escapeSingleQuotes(endDate) + "'");
/*       */       } else {
/*  8102 */         query.append(" street_date <= '" + escapeSingleQuotes(endDate) + "'");
/*       */       } 
/*       */     }
/*  8105 */     if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase("")) query.append("))");
/*       */     
/*  8107 */     query.append(" ORDER BY header.[release_id], header.[artist], header.[street_date], header.[title]");
/*       */ 
/*       */     
/*  8110 */     JdbcConnector connector = getConnector(query.toString());
/*  8111 */     connector.runQuery();
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  8118 */     Vector selections = null;
/*       */ 
/*       */     
/*  8121 */     selections = new Vector();
/*       */     
/*  8123 */     while (connector.more()) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  8132 */       if (bParent) {
/*       */         
/*  8134 */         String prefixId = "";
/*  8135 */         String tmpTitleId = connector.getField("title_id", "");
/*  8136 */         String tmpSelectionNo = connector.getField("selection_no", "");
/*       */         
/*  8138 */         prefixId = SelectionManager.getLookupObjectValue((PrefixCode)getLookupObject(connector.getField("prefix", ""), Cache.getPrefixCodes()));
/*       */         
/*  8140 */         if (!tmpTitleId.equalsIgnoreCase(String.valueOf(prefixId) + tmpSelectionNo)) {
/*       */           
/*  8142 */           connector.next();
/*       */           
/*       */           continue;
/*       */         } 
/*       */       } 
/*  8147 */       int numberOfUnits = 0;
/*       */       
/*       */       try {
/*  8150 */         numberOfUnits = connector.getIntegerField("pfm.[units_per_set]");
/*       */       }
/*  8152 */       catch (Exception exception) {}
/*       */ 
/*       */       
/*  8155 */       Selection selection = null;
/*       */       
/*  8157 */       selection = new Selection();
/*  8158 */       selection.setSelectionID(connector.getIntegerField("release_id"));
/*       */       
/*  8160 */       String selectionNo = "";
/*  8161 */       if (connector.getFieldByName("selection_no") != null)
/*  8162 */         selectionNo = connector.getFieldByName("selection_no"); 
/*  8163 */       selection.setSelectionNo(selectionNo);
/*       */       
/*  8165 */       selection.setProjectID(connector.getField("project_no", ""));
/*       */       
/*  8167 */       String titleId = "";
/*  8168 */       if (connector.getFieldByName("title_id") != null)
/*  8169 */         titleId = connector.getFieldByName("title_id"); 
/*  8170 */       selection.setTitleID(titleId);
/*       */       
/*  8172 */       selection.setTitle(connector.getField("title", ""));
/*  8173 */       selection.setArtistFirstName(connector.getField("artist_first_name", ""));
/*  8174 */       selection.setArtistLastName(connector.getField("artist_last_name", ""));
/*  8175 */       selection.setArtist(connector.getField("artist", ""));
/*  8176 */       selection.setASide(connector.getField("side_a_title", ""));
/*  8177 */       selection.setBSide(connector.getField("side_b_title", ""));
/*       */       
/*  8179 */       selection.setProductCategory((ProductCategory)getLookupObject(connector.getField("product_line"), 
/*  8180 */             Cache.getProductCategories()));
/*  8181 */       selection.setReleaseType((ReleaseType)getLookupObject(connector.getField("release_type"), 
/*  8182 */             Cache.getReleaseTypes()));
/*       */       
/*  8184 */       selection.setSelectionConfig(getSelectionConfigObject(connector.getField("configuration"), 
/*  8185 */             Cache.getSelectionConfigs()));
/*  8186 */       selection.setSelectionSubConfig(getSelectionSubConfigObject(connector.getField("sub_configuration"), 
/*  8187 */             selection.getSelectionConfig()));
/*       */       
/*  8189 */       selection.setUpc(connector.getField("upc", ""));
/*       */       
/*  8191 */       String sellCodeString = connector.getFieldByName("price_code");
/*  8192 */       if (sellCodeString != null)
/*       */       {
/*  8194 */         selection.setPriceCode(SelectionManager.getInstance().getPriceCode(sellCodeString));
/*       */       }
/*       */       
/*  8197 */       selection.setSellCode(sellCodeString);
/*       */       
/*  8199 */       selection.setGenre((Genre)getLookupObject(connector.getField("genre"), 
/*  8200 */             Cache.getMusicTypes()));
/*  8201 */       selection.setFamily((Family)getStructureObject(connector.getIntegerField("family_id")));
/*  8202 */       selection.setCompany((Company)getStructureObject(connector.getIntegerField("company_id")));
/*  8203 */       selection.setDivision((Division)getStructureObject(connector.getIntegerField("division_id")));
/*  8204 */       selection.setLabel((Label)getStructureObject(connector.getIntegerField("label_id")));
/*       */       
/*  8206 */       String streetDateString = connector.getFieldByName("street_date");
/*  8207 */       if (streetDateString != null) {
/*  8208 */         selection.setStreetDate(getDatabaseDate(streetDateString));
/*       */       }
/*  8210 */       String internationalDateString = connector.getFieldByName("international_date");
/*  8211 */       if (internationalDateString != null) {
/*  8212 */         selection.setInternationalDate(getDatabaseDate(internationalDateString));
/*       */       }
/*  8214 */       selection.setOtherContact(connector.getField("coordinator_contacts", ""));
/*       */       
/*  8216 */       if (UserManager.getInstance().getUser(connector.getIntegerField("contact_id")) != null) {
/*  8217 */         selection.setLabelContact(UserManager.getInstance().getUser(connector.getIntegerField("contact_id")));
/*       */       }
/*  8219 */       selection.setSelectionStatus((SelectionStatus)getLookupObject(connector.getField("status"), 
/*  8220 */             Cache.getSelectionStatusList()));
/*  8221 */       selection.setHoldSelection(connector.getBoolean("hold_indicator"));
/*       */       
/*  8223 */       selection.setHoldReason(connector.getField("hold_reason", ""));
/*  8224 */       selection.setComments(connector.getField("comments", ""));
/*  8225 */       selection.setSpecialPackaging(connector.getBoolean("special_pkg_indicator"));
/*  8226 */       selection.setNumberOfUnits(numberOfUnits);
/*  8227 */       selection.setPressAndDistribution(connector.getBoolean("pd_indicator"));
/*       */       
/*  8229 */       selection.setPrefixID((PrefixCode)getLookupObject(connector.getField("prefix"), Cache.getPrefixCodes()));
/*  8230 */       selection.setSelectionPackaging(connector.getField("packaging", ""));
/*       */       
/*  8232 */       String impactDateString = connector.getFieldByName("impact_date");
/*  8233 */       if (impactDateString != null) {
/*  8234 */         selection.setImpactDate(getDatabaseDate(impactDateString));
/*       */       }
/*  8236 */       String lastUpdateDateString = connector.getFieldByName("last_updated_on");
/*  8237 */       if (lastUpdateDateString != null) {
/*  8238 */         selection.setLastUpdateDate(getDatabaseDate(lastUpdateDateString));
/*       */       }
/*  8240 */       selection.setLastUpdatingUser(UserManager.getInstance().getUser(connector.getIntegerField("last_updated_by")));
/*       */ 
/*       */       
/*  8243 */       String originDateString = connector.getFieldByName("entered_on");
/*  8244 */       if (originDateString != null) {
/*  8245 */         selection.setOriginDate(getDatabaseDate(originDateString));
/*       */       }
/*       */ 
/*       */ 
/*       */       
/*  8250 */       User umlContact = UserManager.getInstance().getUser(connector.getIntegerField("mfg.[uml_id]"));
/*  8251 */       selection.setUmlContact(umlContact);
/*  8252 */       selection.setPlant(getLookupObject(connector.getField("mfg.[plant]"), Cache.getVendors()));
/*  8253 */       selection.setDistribution(getLookupObject(connector.getField("mfg.[distribution]"), Cache.getDistributionCodes()));
/*  8254 */       selection.setPoQuantity(connector.getIntegerField("mfg.[order_qty]"));
/*  8255 */       selection.setCompletedQuantity(connector.getIntegerField("mfg.[complete_qty]"));
/*  8256 */       selection.setManufacturingComments(connector.getField("mfg.[order_comments]", ""));
/*  8257 */       selection.setPrice(connector.getFloat("mfg.[list_price]"));
/*       */       
/*  8259 */       selection.setFullSelection(true);
/*       */       
/*  8261 */       String strDistribution = (reportForm.getStringValue("distribution") != null) ? reportForm.getStringValue("distribution") : "";
/*  8262 */       String strFuture2 = (reportForm.getStringValue("Future2") != null) ? reportForm.getStringValue("Future2") : "";
/*       */ 
/*       */       
/*  8265 */       if (!strDistribution.equalsIgnoreCase("") && !strDistribution.equalsIgnoreCase("All")) {
/*       */         
/*  8267 */         String selDistribution = SelectionManager.getLookupObjectValue(selection.getDistribution());
/*       */         
/*  8269 */         if (!selDistribution.equalsIgnoreCase(strDistribution.trim())) {
/*       */           continue;
/*       */         }
/*       */       } 
/*  8273 */       selections.add(selection);
/*       */       
/*  8275 */       connector.next();
/*       */     } 
/*       */     
/*  8278 */     connector.close();
/*       */ 
/*       */     
/*  8281 */     StringBuffer scheduleQuery = new StringBuffer(200);
/*  8282 */     StringBuffer idList = new StringBuffer(200);
/*       */     
/*  8284 */     scheduleQuery.append("SELECT header.[release_id], detail.*, task.* FROM vi_release_header header with (nolock) LEFT JOIN vi_release_detail detail with (nolock) ON header.[release_id] = detail.[release_id] LEFT JOIN vi_Task task with (nolock) ON detail.[task_id] = task.[task_id] WHERE ");
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  8291 */     for (int z = 0; z < selections.size(); z++) {
/*       */       
/*  8293 */       Selection current = (Selection)selections.get(z);
/*       */       
/*  8295 */       idList.append(" header.[release_id] = " + current.getSelectionID());
/*       */       
/*  8297 */       if (z < selections.size() - 1) {
/*  8298 */         idList.append(" OR ");
/*       */       }
/*       */     } 
/*  8301 */     scheduleQuery.append(idList.toString());
/*       */ 
/*       */ 
/*       */     
/*  8305 */     JdbcConnector scheduleConnector = getConnector(scheduleQuery.toString());
/*  8306 */     scheduleConnector.runQuery();
/*       */     
/*  8308 */     while (scheduleConnector.more()) {
/*       */       
/*  8310 */       int nextReleaseId = scheduleConnector.getIntegerField("release_id");
/*  8311 */       Schedule schedule = new Schedule();
/*  8312 */       schedule.setSelectionID(nextReleaseId);
/*       */       
/*  8314 */       Vector precacheSchedule = new Vector();
/*  8315 */       while (scheduleConnector.more() && scheduleConnector.getIntegerField("release_id") == nextReleaseId) {
/*       */         
/*  8317 */         ScheduledTask scheduledTask = null;
/*       */         
/*  8319 */         scheduledTask = new ScheduledTask();
/*       */ 
/*       */         
/*  8322 */         scheduledTask.setReleaseID(scheduleConnector.getIntegerField("release_Id"));
/*       */ 
/*       */         
/*  8325 */         scheduledTask.setTaskID(scheduleConnector.getIntegerField("task_id"));
/*       */ 
/*       */         
/*  8328 */         scheduledTask.setOwner((Family)getStructureObject(scheduleConnector.getIntegerField("task.owner")));
/*       */ 
/*       */         
/*  8331 */         String dueDateString = scheduleConnector.getField("task.due_date", "");
/*  8332 */         if (dueDateString.length() > 0) {
/*  8333 */           scheduledTask.setDueDate(getDatabaseDate(dueDateString));
/*       */         }
/*       */         
/*  8336 */         String completionDateString = scheduleConnector.getField("task.completion_date", "");
/*  8337 */         if (completionDateString.length() > 0) {
/*  8338 */           scheduledTask.setCompletionDate(getDatabaseDate(completionDateString));
/*       */         }
/*       */         
/*  8341 */         String taskStatus = scheduleConnector.getField("task.status", "");
/*  8342 */         if (taskStatus.length() > 1) {
/*  8343 */           scheduledTask.setScheduledTaskStatus(taskStatus);
/*       */         }
/*       */         
/*  8346 */         int day = scheduleConnector.getIntegerField("task.day_of_week");
/*  8347 */         if (day > 0) {
/*  8348 */           scheduledTask.setDayOfTheWeek(new Day(scheduleConnector.getIntegerField("task.day_of_week")));
/*       */         }
/*       */         
/*  8351 */         int weeks = scheduleConnector.getIntegerField("task.weeks_to_release");
/*  8352 */         if (weeks > 0) {
/*  8353 */           scheduledTask.setWeeksToRelease(scheduleConnector.getIntegerField("task.weeks_to_release"));
/*       */         }
/*       */         
/*  8356 */         String vendorString = scheduleConnector.getField("vendor", "");
/*  8357 */         if (vendorString.length() > 0) {
/*  8358 */           scheduledTask.setVendor(vendorString);
/*       */         }
/*       */         
/*  8361 */         int taskAbbrevID = scheduleConnector.getIntegerField("task.abbrev_id");
/*  8362 */         scheduledTask.setTaskAbbreviationID(taskAbbrevID);
/*       */ 
/*       */         
/*  8365 */         int taskID = connector.getIntegerField("task_id");
/*  8366 */         scheduledTask.setScheduledTaskID(taskID);
/*       */ 
/*       */ 
/*       */         
/*  8370 */         String taskDept = scheduleConnector.getField("task.department", "");
/*  8371 */         scheduledTask.setDepartment(taskDept);
/*       */ 
/*       */         
/*  8374 */         scheduledTask.setKeyTask(scheduleConnector.getBoolean("task.key_task_indicator"));
/*       */ 
/*       */         
/*  8377 */         scheduledTask.setAuthorizationName(scheduleConnector.getField("task.authorization_name", ""));
/*       */ 
/*       */         
/*  8380 */         String authDateString = scheduleConnector.getField("task.authorization_date", "");
/*  8381 */         if (authDateString.length() > 0) {
/*  8382 */           scheduledTask.setAuthorizationDate(getDatabaseDate(authDateString));
/*       */         }
/*       */         
/*  8385 */         String comments = scheduleConnector.getField("task.comments", "");
/*  8386 */         scheduledTask.setComments(comments);
/*       */ 
/*       */         
/*  8389 */         int selNo = scheduledTask.getReleaseID();
/*  8390 */         int taskId = scheduledTask.getTaskID();
/*  8391 */         Vector multCompleteDates = ScheduleManager.getInstance().getMultCompleteDates(selNo, taskId);
/*  8392 */         scheduledTask.setMultCompleteDates(multCompleteDates);
/*       */ 
/*       */         
/*  8395 */         scheduledTask.setAllowMultCompleteDatesFlag(connector.getBoolean("allowMultcompleteDatesFlag"));
/*       */ 
/*       */         
/*  8398 */         scheduledTask.setName(scheduleConnector.getField("task.name", ""));
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  8403 */         precacheSchedule.add(scheduledTask);
/*       */         
/*  8405 */         scheduledTask = null;
/*       */         
/*  8407 */         if (scheduleConnector.more()) {
/*       */           
/*  8409 */           scheduleConnector.next();
/*       */           
/*       */           continue;
/*       */         } 
/*       */         break;
/*       */       } 
/*  8415 */       schedule.setTasks(precacheSchedule);
/*       */       
/*  8417 */       for (int y = 0; y < selections.size(); y++) {
/*       */         
/*  8419 */         Selection currentSelection = (Selection)selections.get(y);
/*       */         
/*  8421 */         if (currentSelection.getSelectionID() == schedule.getSelectionID());
/*       */ 
/*       */         
/*  8424 */         if (schedule.getTasks().size() > 0) {
/*       */ 
/*       */           
/*  8427 */           currentSelection.setSchedule(schedule);
/*       */           
/*       */           break;
/*       */         } 
/*       */         
/*  8432 */         selections.remove(y);
/*       */       } 
/*       */     } 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  8439 */     return selections;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void applyManufacturingToSelections(Vector selections) {
/*  8453 */     if (selections != null)
/*       */     {
/*       */       
/*  8456 */       for (int i = 0; i < selections.size(); i++) {
/*       */         
/*  8458 */         Selection selection = (Selection)selections.get(i);
/*  8459 */         SelectionManager.getInstance().getSelectionManufacturingSubDetail(selection);
/*       */       } 
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   protected static Vector filterSelectionsWithReportCriteria(Vector selections, Context context) {
/*  8476 */     Vector selectionsForReport = new Vector();
/*  8477 */     Form reportForm = (Form)context.getSessionValue("reportForm");
/*       */     
/*  8479 */     if (selections == null || selections.size() == 0) {
/*  8480 */       return selectionsForReport;
/*       */     }
/*       */     
/*  8483 */     Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && reportForm.getStringValue("beginDate").length() > 0) ? getDate(reportForm.getStringValue("beginDate")) : null;
/*  8484 */     if (beginStDate == null) {
/*       */       
/*  8486 */       beginStDate = Calendar.getInstance();
/*  8487 */       beginStDate.setTime(new Date(0L));
/*       */     } 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  8493 */     beginStDate.set(11, 0);
/*  8494 */     beginStDate.set(12, 0);
/*  8495 */     beginStDate.set(13, 0);
/*  8496 */     beginStDate.set(14, 0);
/*       */     
/*  8498 */     Calendar endStDate = (reportForm.getStringValue("endDate") != null && reportForm.getStringValue("endDate").length() > 0) ? getDate(reportForm.getStringValue("endDate")) : null;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  8505 */     if (endStDate != null) {
/*       */       
/*  8507 */       endStDate.set(11, 23);
/*  8508 */       endStDate.set(12, 59);
/*  8509 */       endStDate.set(13, 59);
/*  8510 */       endStDate.set(14, 999);
/*       */     } 
/*       */     
/*  8513 */     String strDistribution = (reportForm.getStringValue("distribution") != null) ? reportForm.getStringValue("distribution") : "";
/*  8514 */     String strLabelContact = (reportForm.getStringValue("contact") != null) ? reportForm.getStringValue("contact") : "";
/*  8515 */     String strUmlContact = (reportForm.getStringValue("umlContact") != null) ? reportForm.getStringValue("umlContact") : "";
/*  8516 */     String strFuture2 = (reportForm.getStringValue("Future2") != null) ? reportForm.getStringValue("Future2") : "";
/*       */     
/*  8518 */     String strReleaseType = (reportForm.getStringValue("releaseType") != null) ? reportForm.getStringValue("releaseType") : "";
/*  8519 */     String strStatus = (reportForm.getStringValue("status") != null) ? reportForm.getStringValue("status") : "";
/*  8520 */     String strArtist = (reportForm.getStringValue("artist") != null) ? reportForm.getStringValue("artist") : "";
/*  8521 */     String strFamily = (reportForm.getStringValue("family") != null) ? reportForm.getStringValue("family") : "";
/*  8522 */     String strCompany = (reportForm.getStringValue("company") != null) ? reportForm.getStringValue("company") : "";
/*  8523 */     String strLabel = (reportForm.getStringValue("Label") != null) ? reportForm.getStringValue("Label") : "";
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  8532 */     String[] strConfiguration = null;
/*       */     try {
/*  8534 */       MilestoneFormDropDownMenu configList = (MilestoneFormDropDownMenu)reportForm.getElement("configurationList");
/*  8535 */       if (configList != null) {
/*  8536 */         ArrayList configListAl = configList.getStringValues();
/*  8537 */         if (configListAl != null) {
/*       */           
/*  8539 */           strConfiguration = new String[configListAl.size()];
/*  8540 */           strConfiguration = (String[])configListAl.toArray(strConfiguration);
/*       */         } 
/*       */       } 
/*  8543 */     } catch (Exception e) {
/*  8544 */       e.printStackTrace();
/*  8545 */       System.out.println("<<< Configuration vector execption " + e.getMessage());
/*       */     } 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  8552 */     String[] strSubconfiguration = null;
/*       */     try {
/*  8554 */       MilestoneFormDropDownMenu subconfigList = (MilestoneFormDropDownMenu)reportForm.getElement("subconfigurationList");
/*  8555 */       if (subconfigList != null) {
/*  8556 */         ArrayList subconfigListAl = subconfigList.getStringValues();
/*  8557 */         if (subconfigListAl != null) {
/*       */           
/*  8559 */           strSubconfiguration = new String[subconfigListAl.size()];
/*  8560 */           strSubconfiguration = (String[])subconfigListAl.toArray(strSubconfiguration);
/*       */         } 
/*       */       } 
/*  8563 */     } catch (Exception e) {
/*  8564 */       e.printStackTrace();
/*  8565 */       System.out.println("<<< Subonfiguration vector execption " + e.getMessage());
/*       */     } 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  8574 */     boolean bParent = (reportForm.getElement("ParentsOnly") != null) ? ((FormCheckBox)reportForm.getElement("ParentsOnly")).isChecked() : 0;
/*       */     
/*  8576 */     int totalCount = selections.size();
/*  8577 */     int tenth = 0;
/*       */     
/*  8579 */     tenth = totalCount / 5;
/*       */     
/*  8581 */     if (tenth < 1) {
/*  8582 */       tenth = 1;
/*       */     }
/*       */     
/*       */     try {
/*  8586 */       HttpServletResponse sresponse = context.getResponse();
/*  8587 */       context.putDelivery("status", new String("start_gathering"));
/*  8588 */       context.putDelivery("percent", new String("10"));
/*  8589 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  8590 */       sresponse.setContentType("text/plain");
/*  8591 */       sresponse.flushBuffer();
/*       */     }
/*  8593 */     catch (Exception exception) {}
/*       */ 
/*       */ 
/*       */     
/*  8597 */     int recordCount = 0;
/*  8598 */     int count = 0;
/*       */ 
/*       */ 
/*       */     
/*  8602 */     for (int i = 0; i < selections.size(); i++) {
/*       */ 
/*       */       
/*       */       try {
/*       */ 
/*       */         
/*  8608 */         if (count < recordCount / tenth) {
/*       */           
/*  8610 */           count = recordCount / tenth;
/*  8611 */           HttpServletResponse sresponse = context.getResponse();
/*  8612 */           context.putDelivery("status", new String("start_gathering"));
/*  8613 */           context.putDelivery("percent", new String(String.valueOf(count * 10 + 50)));
/*  8614 */           context.includeJSP("status.jsp", "hiddenFrame");
/*  8615 */           sresponse.setContentType("text/plain");
/*  8616 */           sresponse.flushBuffer();
/*       */         } 
/*       */         
/*  8619 */         recordCount++;
/*       */         
/*  8621 */         Selection sel = (Selection)selections.elementAt(i);
/*  8622 */         sel = SelectionManager.getInstance().getSelectionHeader(sel.getSelectionID());
/*       */         
/*  8624 */         String status = "";
/*  8625 */         if (sel.getSelectionStatus() != null) {
/*  8626 */           status = sel.getSelectionStatus().getName();
/*       */         }
/*  8628 */         if (!status.equalsIgnoreCase("TBS") && !status.equalsIgnoreCase("In the works")) {
/*       */ 
/*       */           
/*  8631 */           Calendar stDate = sel.getStreetDate();
/*       */           
/*  8633 */           if (stDate != null && ((beginStDate != null && stDate.before(beginStDate)) || (endStDate != null && stDate.after(endStDate)))) {
/*       */             continue;
/*       */           }
/*       */         } 
/*       */         
/*  8638 */         if (!strDistribution.equalsIgnoreCase("") && !strDistribution.equalsIgnoreCase("All")) {
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*  8643 */           String selDistribution = "";
/*       */           
/*  8645 */           if (sel.getLabel().getDistribution() == 1) {
/*       */             
/*  8647 */             selDistribution = "East";
/*       */           }
/*  8649 */           else if (sel.getLabel().getDistribution() == 0) {
/*       */             
/*  8651 */             selDistribution = "West";
/*       */           } 
/*       */           
/*  8654 */           if (!selDistribution.equalsIgnoreCase(strDistribution.trim())) {
/*       */             continue;
/*       */           }
/*       */         } 
/*       */         
/*  8659 */         if (!strLabelContact.trim().equalsIgnoreCase("0") && !strLabelContact.trim().equalsIgnoreCase("")) {
/*       */           
/*  8661 */           if (sel.getLabelContact() == null) {
/*       */             continue;
/*       */           }
/*  8664 */           String selLabelContactName = Integer.toString(sel.getLabelContact().getUserId());
/*       */           
/*  8666 */           if (!selLabelContactName.equalsIgnoreCase(strLabelContact)) {
/*       */             continue;
/*       */           }
/*       */         } 
/*       */         
/*  8671 */         if (!strUmlContact.trim().equalsIgnoreCase("0") && !strUmlContact.trim().equalsIgnoreCase("")) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*  8677 */           SelectionManager.getInstance().getSelectionManufacturingSubDetail(sel);
/*       */           
/*  8679 */           if (sel.getUmlContact() == null) {
/*       */             continue;
/*       */           }
/*       */ 
/*       */           
/*  8684 */           String selUmlContactName = Integer.toString(sel.getUmlContact().getUserId());
/*       */           
/*  8686 */           if (!selUmlContactName.equalsIgnoreCase(strUmlContact)) {
/*       */             continue;
/*       */           }
/*       */         } 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  8695 */         if (!strReleaseType.equalsIgnoreCase("") && !strReleaseType.equalsIgnoreCase("All")) {
/*       */           
/*  8697 */           String releaseType = "";
/*  8698 */           if (sel.getReleaseType() != null) {
/*       */             
/*  8700 */             releaseType = (sel.getReleaseType().getName() != null) ? sel.getReleaseType().getName() : "";
/*  8701 */             if (!releaseType.equalsIgnoreCase(strReleaseType)) {
/*       */               continue;
/*       */             }
/*       */           } else {
/*       */             continue;
/*       */           } 
/*       */         } 
/*       */ 
/*       */ 
/*       */         
/*  8711 */         String selectionStatus = SelectionManager.getLookupObjectValue(sel.getSelectionStatus()).trim();
/*  8712 */         if (!strStatus.equalsIgnoreCase("") && !strStatus.equalsIgnoreCase("All")) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*  8719 */           if (strStatus.trim().equalsIgnoreCase("active")) {
/*       */             
/*  8721 */             if (!selectionStatus.equalsIgnoreCase("TBS") && !selectionStatus.equalsIgnoreCase("ITW") && !selectionStatus.equalsIgnoreCase("active")) {
/*       */               continue;
/*       */             }
/*  8724 */           } else if (strStatus.trim().equalsIgnoreCase("Active, excluding TBS")) {
/*       */             
/*  8726 */             if (!selectionStatus.trim().equalsIgnoreCase("active")) {
/*       */               continue;
/*       */             }
/*  8729 */           } else if (strStatus.trim().equalsIgnoreCase("TBS")) {
/*       */             
/*  8731 */             if (!selectionStatus.trim().equalsIgnoreCase("TBS") && !selectionStatus.trim().equalsIgnoreCase("ITW")) {
/*       */               continue;
/*       */             }
/*  8734 */           } else if (strStatus.trim().equalsIgnoreCase("All, excluding TBS")) {
/*       */ 
/*       */             
/*  8737 */             if (!selectionStatus.trim().equalsIgnoreCase("active") && !selectionStatus.trim().equalsIgnoreCase("CLOSED")) {
/*       */               continue;
/*       */             }
/*  8740 */           } else if (strStatus.trim().equalsIgnoreCase("Cancelled")) {
/*       */             
/*  8742 */             if (!selectionStatus.equalsIgnoreCase("CANCEL"))
/*       */             {
/*       */               continue;
/*       */             
/*       */             }
/*       */           }
/*  8748 */           else if (!selectionStatus.equalsIgnoreCase("CLOSED")) {
/*       */ 
/*       */             
/*       */             continue;
/*       */           
/*       */           }
/*       */         
/*       */         }
/*  8756 */         else if (!selectionStatus.equalsIgnoreCase("CANCEL")) {
/*       */           continue;
/*       */         } 
/*       */ 
/*       */         
/*  8761 */         if (!strArtist.trim().equals("") && sel.getArtist() != null) {
/*       */           
/*  8763 */           String artistUpperCase = sel.getArtist().trim().toUpperCase();
/*       */           
/*  8765 */           if (!artistUpperCase.startsWith(strArtist.trim().toUpperCase())) {
/*       */             continue;
/*       */           }
/*       */         } 
/*       */         
/*  8770 */         if (!strFamily.equals("") && !strFamily.equals("0"))
/*       */         {
/*  8772 */           if (sel.getFamily() == null || !strFamily.equals(sel.getFamily().getIdentity())) {
/*       */             continue;
/*       */           }
/*       */         }
/*       */         
/*  8777 */         if (!strCompany.equals("") && !strCompany.equals("0"))
/*       */         {
/*  8779 */           if (sel.getCompany() == null || !strCompany.equals(sel.getCompany().getStructureID())) {
/*       */             continue;
/*       */           }
/*       */         }
/*       */         
/*  8784 */         if (!strLabel.equals("") && !strLabel.equals("0"))
/*       */         {
/*  8786 */           if (sel.getLabel() == null || !strLabel.equals(sel.getLabel().getStructureID())) {
/*       */             continue;
/*       */           }
/*       */         }
/*       */         
/*  8791 */         if (bParent) {
/*       */           
/*  8793 */           String prefixId = "";
/*       */           
/*  8795 */           if (sel.getPrefixID() != null) {
/*  8796 */             prefixId = SelectionManager.getLookupObjectValue(sel.getPrefixID());
/*       */           }
/*  8798 */           if (!sel.getTitleID().equalsIgnoreCase(String.valueOf(prefixId) + sel.getSelectionNo())) {
/*       */             continue;
/*       */           }
/*       */         } 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  8806 */         if (strSubconfiguration != null && 
/*  8807 */           !strSubconfiguration[0].trim().equalsIgnoreCase("0") && 
/*  8808 */           !strSubconfiguration[0].trim().equalsIgnoreCase("") && 
/*  8809 */           !strSubconfiguration[0].trim().equalsIgnoreCase("all")) {
/*       */ 
/*       */           
/*  8812 */           if (sel.getSelectionSubConfig() != null && sel.getSelectionConfig() != null) {
/*  8813 */             String selConfigurationName = sel.getSelectionConfig().getSelectionConfigurationAbbreviation();
/*  8814 */             String selSubconfigurationName = sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation();
/*       */             
/*  8816 */             boolean subconfigFnd = false;
/*  8817 */             for (int x = 0; x < strSubconfiguration.length; x++) {
/*  8818 */               String txtvalue = strSubconfiguration[x];
/*  8819 */               String txtconfigcode = txtvalue.substring(1, txtvalue.indexOf("]"));
/*  8820 */               String txtsubconfigcode = txtvalue.substring(txtvalue.indexOf("]") + 1, txtvalue.length());
/*  8821 */               if (selConfigurationName.equalsIgnoreCase(txtconfigcode) && 
/*  8822 */                 selSubconfigurationName.equalsIgnoreCase(txtsubconfigcode)) {
/*  8823 */                 subconfigFnd = true;
/*       */                 break;
/*       */               } 
/*       */             } 
/*  8827 */             if (!subconfigFnd)
/*       */             {
/*       */               continue;
/*       */ 
/*       */ 
/*       */             
/*       */             }
/*       */ 
/*       */           
/*       */           }
/*       */ 
/*       */ 
/*       */         
/*       */         }
/*  8841 */         else if (strConfiguration != null && !strConfiguration[0].trim().equalsIgnoreCase("0") && !strConfiguration[0].trim().equalsIgnoreCase("") && 
/*  8842 */           !strConfiguration[0].trim().equalsIgnoreCase("all")) {
/*       */           
/*  8844 */           if (sel.getSelectionConfig() != null) {
/*  8845 */             String selConfigurationName = sel.getSelectionConfig().getSelectionConfigurationAbbreviation();
/*       */             
/*  8847 */             boolean configFnd = false;
/*  8848 */             for (int x = 0; x < strConfiguration.length; x++) {
/*  8849 */               if (selConfigurationName.equalsIgnoreCase(strConfiguration[x])) {
/*  8850 */                 configFnd = true;
/*       */                 break;
/*       */               } 
/*       */             } 
/*  8854 */             if (!configFnd) {
/*       */               continue;
/*       */             }
/*       */           } 
/*       */         } 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  8891 */         selectionsForReport.addElement(sel);
/*       */         
/*       */         continue;
/*  8894 */       } catch (Exception exception) {
/*       */         continue;
/*       */       } 
/*       */     } 
/*       */     
/*  8899 */     return selectionsForReport;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static FormDropDownMenu getTaskAbbreviationsDropDown(String name, String selectedOption, boolean required) {
/*  8917 */     Vector values = new Vector();
/*  8918 */     Vector menuText = new Vector();
/*       */     
/*  8920 */     values.addElement("-1");
/*  8921 */     menuText.addElement("&nbsp;&nbsp;");
/*       */     
/*  8923 */     FormDropDownMenu dropDown = null;
/*       */     
/*  8925 */     if (selectedOption == null) {
/*  8926 */       selectedOption = "";
/*       */     }
/*       */     
/*  8929 */     Vector deptList = Cache.getLookupDetailValuesFromDatabase(33);
/*       */     
/*  8931 */     if (deptList != null) {
/*       */       
/*  8933 */       for (int i = 0; i < deptList.size(); i++) {
/*       */         
/*  8935 */         LookupObject department = (LookupObject)deptList.elementAt(i);
/*       */         
/*  8937 */         if (department != null) {
/*       */ 
/*       */           
/*  8940 */           if (department.getInactive())
/*       */           {
/*       */ 
/*       */             
/*  8944 */             if (!department.getAbbreviation().equalsIgnoreCase(selectedOption)) {
/*       */               continue;
/*       */             }
/*       */           }
/*       */           
/*  8949 */           values.addElement(department.getAbbreviation());
/*  8950 */           menuText.addElement(department.getName());
/*       */         } 
/*       */         continue;
/*       */       } 
/*  8954 */       String[] arrayValues = new String[values.size()];
/*  8955 */       String[] arrayMenuText = new String[menuText.size()];
/*       */ 
/*       */       
/*  8958 */       arrayValues = (String[])values.toArray(arrayValues);
/*  8959 */       arrayMenuText = (String[])menuText.toArray(arrayMenuText);
/*       */       
/*  8961 */       dropDown = new FormDropDownMenu(name, 
/*  8962 */           selectedOption, 
/*  8963 */           arrayValues, 
/*  8964 */           arrayMenuText, 
/*  8965 */           required);
/*       */     } 
/*       */     
/*  8968 */     return dropDown;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static FormDropDownMenu getDepartmentDropDown(String name, String selectedOption, boolean required) {
/*  8985 */     Vector values = new Vector();
/*  8986 */     Vector menuText = new Vector();
/*       */     
/*  8988 */     values.addElement("-1");
/*  8989 */     menuText.addElement("&nbsp;&nbsp;");
/*       */     
/*  8991 */     FormDropDownMenu dropDown = null;
/*       */     
/*  8993 */     if (selectedOption == null) {
/*  8994 */       selectedOption = "";
/*       */     }
/*       */     
/*  8997 */     Vector deptList = Cache.getLookupDetailValuesFromDatabase(18);
/*       */     
/*  8999 */     if (deptList != null) {
/*       */       
/*  9001 */       for (int i = 0; i < deptList.size(); i++) {
/*       */         
/*  9003 */         LookupObject department = (LookupObject)deptList.elementAt(i);
/*       */         
/*  9005 */         if (department != null) {
/*       */ 
/*       */           
/*  9008 */           if (department.getInactive())
/*       */           {
/*       */ 
/*       */             
/*  9012 */             if (!department.getAbbreviation().equalsIgnoreCase(selectedOption)) {
/*       */               continue;
/*       */             }
/*       */           }
/*       */           
/*  9017 */           values.addElement(department.getAbbreviation());
/*  9018 */           menuText.addElement(department.getName());
/*       */         } 
/*       */         continue;
/*       */       } 
/*  9022 */       String[] arrayValues = new String[values.size()];
/*  9023 */       String[] arrayMenuText = new String[menuText.size()];
/*       */ 
/*       */       
/*  9026 */       arrayValues = (String[])values.toArray(arrayValues);
/*  9027 */       arrayMenuText = (String[])menuText.toArray(arrayMenuText);
/*       */       
/*  9029 */       dropDown = new FormDropDownMenu(name, 
/*  9030 */           selectedOption, 
/*  9031 */           arrayValues, 
/*  9032 */           arrayMenuText, 
/*  9033 */           required);
/*       */     } 
/*       */     
/*  9036 */     return dropDown;
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   public static Vector getAllSelectionsForUser(Context context) {
/*  9042 */     Vector companies = getUserCompanies(context);
/*  9043 */     Company company = null;
/*  9044 */     Vector precache = new Vector();
/*  9045 */     Selection selection = null;
/*  9046 */     StringBuffer query = new StringBuffer(200);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  9057 */     Form reportForm = (Form)context.getSessionValue("reportForm");
/*  9058 */     boolean bScheduled = (reportForm.getElement("ScheduledReleases") != null) ? ((FormCheckBox)reportForm.getElement("ScheduledReleases")).isChecked() : 0;
/*       */ 
/*       */ 
/*       */     
/*  9062 */     String strUmlKey = (reportForm.getStringValue("umlKeyTask") != null) ? reportForm.getStringValue("umlKeyTask") : "";
/*  9063 */     Report report = (Report)context.getSessionValue("Report");
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  9069 */     if (bScheduled || (!strUmlKey.equalsIgnoreCase("") && !strUmlKey.trim().equalsIgnoreCase("all") && 
/*  9070 */       !report.getReportName().trim().equalsIgnoreCase("New Rel."))) {
/*       */       
/*  9072 */       query.append("SELECT DISTINCT header.[release_id], header.[artist],  header.[title], header.[artist_first_name], header.[artist_last_name],  header.[artist_first_name] + ' ' + header.[artist_last_name] AS fl_artist, header.[street_date], header.[configuration], header.[sub_configuration], header.[upc], header.[prefix], header.[selection_no], header.[status],  header.[company_id], header.[family_id], header.[label_id]  FROM vi_Release_Header header with (nolock), vi_Release_Detail detail  with (nolock) WHERE (header.[release_id] = detail.[release_id]) AND (");
/*       */ 
/*       */ 
/*       */     
/*       */     }
/*       */     else {
/*       */ 
/*       */ 
/*       */       
/*  9081 */       query.append("SELECT release_id, title, artist_first_name, artist_last_name, street_date, configuration, sub_configuration, artist_first_name + ' ' + artist_last_name AS fl_artist, upc, prefix, selection_no, status,  company_id, family_id, label_id  from vi_Release_Header header with (nolock) WHERE (");
/*       */     } 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  9091 */     String strFamily = (reportForm.getStringValue("family") != null) ? reportForm.getStringValue("family") : "";
/*  9092 */     String strCompany = (reportForm.getStringValue("company") != null) ? reportForm.getStringValue("company") : "";
/*  9093 */     String strLabel = (reportForm.getStringValue("Label") != null) ? reportForm.getStringValue("Label") : "";
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  9099 */     if (!strCompany.equalsIgnoreCase("") && !strCompany.equalsIgnoreCase("0")) {
/*       */       
/*  9101 */       query.append(" company_id = " + strCompany);
/*       */     }
/*       */     else {
/*       */       
/*  9105 */       StringList list = new StringList(" OR ");
/*       */       
/*  9107 */       query.append(String.valueOf(list.toString()) + " (");
/*       */       
/*  9109 */       for (int i = 0; i < companies.size(); i++) {
/*       */         
/*  9111 */         company = (Company)companies.get(i);
/*  9112 */         if (company != null)
/*       */         {
/*  9114 */           list.add(" company_id = " + company.getStructureID());
/*       */         }
/*       */       } 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  9121 */       query.append(String.valueOf(list.toString()) + ") ");
/*       */     } 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  9127 */     if (!strFamily.equalsIgnoreCase("") && !strFamily.equalsIgnoreCase("0")) {
/*  9128 */       query.append(" AND family_id = " + strFamily);
/*       */     }
/*       */ 
/*       */ 
/*       */     
/*  9133 */     if (!strLabel.equalsIgnoreCase("") && !strLabel.equalsIgnoreCase("0")) {
/*  9134 */       query.append(" AND label_id = " + strLabel);
/*       */     }
/*       */ 
/*       */ 
/*       */     
/*  9139 */     if (!strUmlKey.equalsIgnoreCase("") && !strUmlKey.trim().equalsIgnoreCase("all")) {
/*       */       
/*  9141 */       int intUml = -1;
/*       */ 
/*       */       
/*  9144 */       Vector theFamilies = Cache.getFamilies();
/*  9145 */       for (int i = 0; i < theFamilies.size(); i++) {
/*  9146 */         Family family = (Family)theFamilies.get(i);
/*       */         
/*  9148 */         if (family.getName().trim().equalsIgnoreCase("UML")) {
/*  9149 */           intUml = family.getStructureID();
/*       */           
/*       */           break;
/*       */         } 
/*       */       } 
/*       */       
/*  9155 */       if (intUml > 0)
/*       */       {
/*       */         
/*  9158 */         if (report.getReportName().trim().equalsIgnoreCase("New Rel.")) {
/*       */           
/*  9160 */           query.append(" AND (EXISTS (SELECT owner FROM vi_release_detail detail where owner = " + intUml);
/*  9161 */           query.append(" AND detail.release_id = header.release_id)");
/*       */           
/*  9163 */           query.append(" OR NOT EXISTS ");
/*  9164 */           query.append(" (SELECT detailA.release_id FROM vi_release_detail detailA");
/*  9165 */           query.append(" WHERE detailA.release_id = header.release_id)) ");
/*       */ 
/*       */         
/*       */         }
/*  9169 */         else if (strUmlKey.equalsIgnoreCase("UML")) {
/*  9170 */           query.append(" AND owner = " + intUml);
/*       */         } else {
/*  9172 */           query.append(" AND owner != " + intUml);
/*       */         } 
/*       */       }
/*       */     } 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  9186 */     String[] strConfiguration = null;
/*       */     try {
/*  9188 */       MilestoneFormDropDownMenu configList = (MilestoneFormDropDownMenu)reportForm.getElement("configurationList");
/*  9189 */       if (configList != null) {
/*  9190 */         ArrayList configListAl = configList.getStringValues();
/*  9191 */         if (configListAl != null) {
/*       */           
/*  9193 */           strConfiguration = new String[configListAl.size()];
/*  9194 */           strConfiguration = (String[])configListAl.toArray(strConfiguration);
/*       */         } 
/*       */       } 
/*  9197 */     } catch (Exception e) {
/*  9198 */       e.printStackTrace();
/*  9199 */       System.out.println("<<< Configuration vector execption " + e.getMessage());
/*       */     } 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  9205 */     String[] strSubconfiguration = null;
/*       */     try {
/*  9207 */       MilestoneFormDropDownMenu subconfigList = (MilestoneFormDropDownMenu)reportForm.getElement("subconfigurationList");
/*  9208 */       if (subconfigList != null) {
/*  9209 */         ArrayList subconfigListAl = subconfigList.getStringValues();
/*  9210 */         if (subconfigListAl != null) {
/*       */           
/*  9212 */           strSubconfiguration = new String[subconfigListAl.size()];
/*  9213 */           strSubconfiguration = (String[])subconfigListAl.toArray(strSubconfiguration);
/*       */         } 
/*       */       } 
/*  9216 */     } catch (Exception e) {
/*  9217 */       e.printStackTrace();
/*  9218 */       System.out.println("<<< Subconfiguration vector execption " + e.getMessage());
/*       */     } 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  9224 */     if (strSubconfiguration != null && 
/*  9225 */       !strSubconfiguration[0].trim().equalsIgnoreCase("0") && 
/*  9226 */       !strSubconfiguration[0].trim().equalsIgnoreCase("") && 
/*  9227 */       !strSubconfiguration[0].trim().equalsIgnoreCase("all")) {
/*       */       
/*  9229 */       boolean addOr = false;
/*  9230 */       query.append(" AND (");
/*  9231 */       for (int x = 0; x < strSubconfiguration.length; x++) {
/*  9232 */         if (addOr)
/*  9233 */           query.append(" OR "); 
/*  9234 */         String txtvalue = strSubconfiguration[x];
/*  9235 */         String txtconfigcode = txtvalue.substring(1, txtvalue.indexOf("]"));
/*  9236 */         String txtsubconfigcode = txtvalue.substring(txtvalue.indexOf("]") + 1, txtvalue.length());
/*  9237 */         query.append(" (header.[configuration] = '" + txtconfigcode.trim() + "' AND ");
/*  9238 */         query.append(" header.[sub_configuration] = '" + txtsubconfigcode.trim() + "') ");
/*  9239 */         addOr = true;
/*       */       } 
/*  9241 */       query.append(") ");
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     }
/*  9249 */     else if (strConfiguration != null && !strConfiguration[0].equalsIgnoreCase("") && !strConfiguration[0].equalsIgnoreCase("0") && 
/*  9250 */       !strConfiguration[0].equalsIgnoreCase("all")) {
/*       */       
/*  9252 */       boolean addOr = false;
/*  9253 */       query.append(" AND (");
/*  9254 */       for (int x = 0; x < strConfiguration.length; x++) {
/*  9255 */         if (addOr) {
/*  9256 */           query.append(" OR ");
/*       */         }
/*  9258 */         query.append(" header.[configuration] = '" + strConfiguration[x].trim() + "' ");
/*  9259 */         addOr = true;
/*       */       } 
/*  9261 */       query.append(") ");
/*       */     } 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  9314 */     String beginDate = "";
/*  9315 */     beginDate = (reportForm.getStringValue("beginDate") != null && reportForm.getStringValue("beginDate").length() > 0) ? reportForm.getStringValue("beginDate") : "";
/*       */     
/*  9317 */     String endDate = "";
/*  9318 */     endDate = (reportForm.getStringValue("endDate") != null && reportForm.getStringValue("endDate").length() > 0) ? reportForm.getStringValue("endDate") : "";
/*       */     
/*  9320 */     if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase("")) {
/*  9321 */       query.append(" AND (( header.status ='TBS' OR header.status ='ITW') OR (");
/*       */     }
/*  9323 */     if (!beginDate.equalsIgnoreCase("")) {
/*  9324 */       query.append(" street_date >= '" + escapeSingleQuotes(beginDate) + "'");
/*       */     }
/*  9326 */     if (!endDate.equalsIgnoreCase(""))
/*       */     {
/*  9328 */       if (!beginDate.equalsIgnoreCase("")) {
/*  9329 */         query.append(" AND street_date <= '" + escapeSingleQuotes(endDate) + "'");
/*       */       } else {
/*  9331 */         query.append(" street_date <= '" + escapeSingleQuotes(endDate) + "'");
/*       */       } 
/*       */     }
/*  9334 */     if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase("")) query.append("))");
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  9339 */     query.append(") ORDER BY artist, street_date, title");
/*       */ 
/*       */ 
/*       */     
/*  9343 */     JdbcConnector connector = getConnector(query.toString());
/*  9344 */     connector.setForwardOnly(false);
/*  9345 */     connector.runQuery();
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  9351 */     int totalCount = 0;
/*  9352 */     int tenth = 0;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  9360 */     totalCount = connector.getRowCount();
/*       */ 
/*       */     
/*  9363 */     tenth = totalCount / 5;
/*       */     
/*  9365 */     if (tenth < 1) {
/*  9366 */       tenth = 1;
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     try {
/*  9373 */       HttpServletResponse sresponse = context.getResponse();
/*  9374 */       context.putDelivery("status", new String("start_gathering"));
/*  9375 */       context.putDelivery("percent", new String("10"));
/*  9376 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  9377 */       sresponse.setContentType("text/plain");
/*  9378 */       sresponse.flushBuffer();
/*       */     }
/*  9380 */     catch (Exception exception) {}
/*       */ 
/*       */ 
/*       */     
/*  9384 */     int recordCount = 0;
/*  9385 */     int count = 0;
/*       */ 
/*       */     
/*  9388 */     while (connector.more()) {
/*       */ 
/*       */       
/*       */       try {
/*  9392 */         if (count < recordCount / tenth) {
/*       */           
/*  9394 */           count = recordCount / tenth;
/*  9395 */           HttpServletResponse sresponse = context.getResponse();
/*  9396 */           context.putDelivery("status", new String("start_gathering"));
/*  9397 */           context.putDelivery("percent", new String(String.valueOf(count * 10)));
/*  9398 */           context.includeJSP("status.jsp", "hiddenFrame");
/*  9399 */           sresponse.setContentType("text/plain");
/*  9400 */           sresponse.flushBuffer();
/*       */         } 
/*       */         
/*  9403 */         recordCount++;
/*       */ 
/*       */         
/*  9406 */         selection = new Selection();
/*       */ 
/*       */         
/*  9409 */         selection.setSelectionID(connector.getIntegerField("release_id"));
/*       */         
/*  9411 */         selection.setTitle(connector.getField("title", ""));
/*       */ 
/*       */         
/*  9414 */         selection.setArtistFirstName(connector.getField("artist_first_name", ""));
/*       */ 
/*       */         
/*  9417 */         selection.setArtistLastName(connector.getField("artist_last_name", ""));
/*       */ 
/*       */         
/*  9420 */         selection.setFlArtist(connector.getField("fl_artist", ""));
/*       */ 
/*       */         
/*  9423 */         String streetDateString = connector.getFieldByName("street_date");
/*  9424 */         if (streetDateString != null) {
/*  9425 */           selection.setStreetDate(getDatabaseDate(streetDateString));
/*       */         }
/*  9427 */         selection.setUpc(connector.getField("upc", ""));
/*       */         
/*  9429 */         selection.setSelectionConfig(
/*  9430 */             getSelectionConfigObject(connector.getField("configuration"), 
/*  9431 */               Cache.getSelectionConfigs()));
/*       */ 
/*       */ 
/*       */         
/*  9435 */         selection.setSelectionSubConfig(getSelectionSubConfigObject(connector.getField("sub_configuration"), selection.getSelectionConfig()));
/*  9436 */         selection.setPrefixID((PrefixCode)getLookupObject(connector.getField("prefix"), Cache.getPrefixCodes()));
/*  9437 */         selection.setSelectionNo(connector.getField("selection_no"));
/*  9438 */         selection.setSelectionStatus((SelectionStatus)getLookupObject(connector.getField("status"), 
/*  9439 */               Cache.getSelectionStatusList()));
/*       */         
/*  9441 */         precache.add(selection);
/*  9442 */         selection = null;
/*  9443 */         connector.next();
/*       */       
/*       */       }
/*  9446 */       catch (Exception exception) {}
/*       */     } 
/*       */ 
/*       */ 
/*       */     
/*  9451 */     connector.close();
/*  9452 */     company = null;
/*       */ 
/*       */     
/*  9455 */     return precache;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Hashtable groupSelectionsByTypeAndCategory(Vector selections) {
/*  9471 */     Hashtable groupedByTypeAndCategory = new Hashtable();
/*  9472 */     if (selections == null) {
/*  9473 */       return groupedByTypeAndCategory;
/*       */     }
/*  9475 */     for (int i = 0; i < selections.size(); i++) {
/*       */       
/*  9477 */       Selection sel = (Selection)selections.elementAt(i);
/*  9478 */       if (sel != null) {
/*       */         
/*  9480 */         sel = SelectionManager.getInstance().getSelectionAndSchedule(sel.getSelectionID());
/*       */ 
/*       */         
/*  9483 */         String typeString = "", categoryString = "";
/*       */         
/*  9485 */         typeString = (sel.getReleaseType() != null) ? sel.getReleaseType().getName() : "";
/*  9486 */         categoryString = (sel.getProductCategory() != null) ? sel.getProductCategory().getName() : "";
/*       */ 
/*       */         
/*  9489 */         Hashtable typeSubTable = (Hashtable)groupedByTypeAndCategory.get(typeString);
/*  9490 */         if (typeSubTable == null) {
/*       */ 
/*       */           
/*  9493 */           typeSubTable = new Hashtable();
/*  9494 */           groupedByTypeAndCategory.put(typeString, typeSubTable);
/*       */         } 
/*       */ 
/*       */         
/*  9498 */         Vector selectionsForCategory = (Vector)typeSubTable.get(categoryString);
/*  9499 */         if (selectionsForCategory == null) {
/*       */ 
/*       */           
/*  9502 */           selectionsForCategory = new Vector();
/*  9503 */           typeSubTable.put(categoryString, selectionsForCategory);
/*       */         } 
/*       */ 
/*       */         
/*  9507 */         selectionsForCategory.addElement(sel);
/*       */       } 
/*       */     } 
/*       */     
/*  9511 */     return groupedByTypeAndCategory;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Hashtable groupSelectionsByCompanyAndSubconfigAndStreetDate(Vector selections) {
/*  9528 */     Hashtable companyTable = new Hashtable();
/*  9529 */     if (selections == null) {
/*  9530 */       return companyTable;
/*       */     }
/*  9532 */     for (int i = 0; i < selections.size(); i++) {
/*       */       
/*  9534 */       Selection sel = (Selection)selections.elementAt(i);
/*  9535 */       if (sel != null) {
/*       */         
/*  9537 */         sel = SelectionManager.getInstance().getSelectionAndSchedule(sel.getSelectionID());
/*       */ 
/*       */         
/*  9540 */         String companyName = "";
/*  9541 */         String dateString = "";
/*  9542 */         String configAbbreviation = "";
/*  9543 */         String subconfigAbbreviation = "";
/*  9544 */         String monthString = "";
/*  9545 */         String statusString = "";
/*       */         
/*  9547 */         dateString = getFormatedDate(sel.getStreetDate());
/*       */ 
/*       */         
/*  9550 */         if (sel.getStreetDate() != null) {
/*       */ 
/*       */           
/*  9553 */           SimpleDateFormat formatter = new SimpleDateFormat("MM");
/*  9554 */           monthString = formatter.format(sel.getStreetDate().getTime());
/*       */         } 
/*       */         
/*  9557 */         Company company = sel.getCompany();
/*  9558 */         SelectionConfiguration config = sel.getSelectionConfig();
/*  9559 */         SelectionSubConfiguration subconfig = sel.getSelectionSubConfig();
/*  9560 */         SelectionStatus status = sel.getSelectionStatus();
/*       */         
/*  9562 */         if (company != null)
/*  9563 */           companyName = (company.getName() == null) ? "" : company.getName(); 
/*  9564 */         if (config != null)
/*  9565 */           configAbbreviation = (config.getSelectionConfigurationAbbreviation() == null) ? 
/*  9566 */             "" : config.getSelectionConfigurationAbbreviation(); 
/*  9567 */         if (subconfig != null)
/*  9568 */           subconfigAbbreviation = (subconfig.getSelectionSubConfigurationAbbreviation() == null) ? 
/*  9569 */             "" : subconfig.getSelectionSubConfigurationAbbreviation(); 
/*  9570 */         if (status != null) {
/*  9571 */           statusString = (status.getName() == null) ? "" : status.getName();
/*       */         }
/*       */ 
/*       */ 
/*       */         
/*  9576 */         if (subconfigAbbreviation.equalsIgnoreCase("DVDVID") || 
/*  9577 */           subconfigAbbreviation.equalsIgnoreCase("ECD") || 
/*  9578 */           subconfigAbbreviation.equalsIgnoreCase("ECDEP") || 
/*  9579 */           subconfigAbbreviation.equalsIgnoreCase("CASS") || 
/*  9580 */           subconfigAbbreviation.equalsIgnoreCase("CASSEP") || 
/*  9581 */           subconfigAbbreviation.equalsIgnoreCase("8TRK") || 
/*  9582 */           subconfigAbbreviation.equalsIgnoreCase("CDROM") || 
/*  9583 */           subconfigAbbreviation.equalsIgnoreCase("CDVID") || 
/*  9584 */           subconfigAbbreviation.equalsIgnoreCase("DCCASS") || 
/*  9585 */           subconfigAbbreviation.equalsIgnoreCase("LASER") || 
/*  9586 */           subconfigAbbreviation.equalsIgnoreCase("VIDEO") || 
/*  9587 */           subconfigAbbreviation.equalsIgnoreCase("CD") || 
/*  9588 */           subconfigAbbreviation.equalsIgnoreCase("CDEP") || 
/*  9589 */           subconfigAbbreviation.equalsIgnoreCase("CDADVD") || 
/*  9590 */           subconfigAbbreviation.equalsIgnoreCase("DP") || 
/*  9591 */           subconfigAbbreviation.equalsIgnoreCase("DVDAUD") || 
/*  9592 */           subconfigAbbreviation.equalsIgnoreCase("SACD1") || 
/*  9593 */           subconfigAbbreviation.equalsIgnoreCase("SACD2") || 
/*  9594 */           subconfigAbbreviation.equalsIgnoreCase("SACD3") || 
/*  9595 */           subconfigAbbreviation.equalsIgnoreCase("SACD4") || 
/*  9596 */           subconfigAbbreviation.equalsIgnoreCase("ALBUM") || 
/*  9597 */           subconfigAbbreviation.equalsIgnoreCase("VNYL10") || 
/*  9598 */           subconfigAbbreviation.equalsIgnoreCase("VNYL12") || 
/*  9599 */           configAbbreviation.equalsIgnoreCase("DUALDISC")) {
/*       */ 
/*       */           
/*  9602 */           subconfigAbbreviation = "Full Length";
/*       */         }
/*  9604 */         else if (!subconfigAbbreviation.equals("")) {
/*       */           
/*  9606 */           subconfigAbbreviation = "Singles";
/*       */         } 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  9614 */         if (statusString.equalsIgnoreCase("TBS")) {
/*       */           
/*  9616 */           monthString = "13";
/*       */         
/*       */         }
/*  9619 */         else if (statusString.equalsIgnoreCase("In The Works")) {
/*       */           
/*  9621 */           monthString = "26";
/*       */         } else {
/*       */ 
/*       */           
/*       */           try {
/*       */ 
/*       */ 
/*       */             
/*  9629 */             Integer.parseInt(monthString);
/*       */           }
/*  9631 */           catch (Exception e) {
/*       */             
/*  9633 */             monthString = "52";
/*       */           } 
/*       */         } 
/*       */ 
/*       */ 
/*       */         
/*  9639 */         Hashtable companySubTable = (Hashtable)companyTable.get(companyName);
/*  9640 */         if (companySubTable == null) {
/*       */ 
/*       */           
/*  9643 */           companySubTable = new Hashtable();
/*  9644 */           companyTable.put(companyName, companySubTable);
/*       */         } 
/*       */ 
/*       */         
/*  9648 */         Hashtable subConfigTable = (Hashtable)companySubTable.get(subconfigAbbreviation);
/*  9649 */         if (subConfigTable == null) {
/*       */ 
/*       */           
/*  9652 */           subConfigTable = new Hashtable();
/*  9653 */           companySubTable.put(subconfigAbbreviation, subConfigTable);
/*       */         } 
/*       */ 
/*       */         
/*  9657 */         Hashtable monthsTable = (Hashtable)subConfigTable.get(monthString);
/*  9658 */         if (monthsTable == null) {
/*       */ 
/*       */           
/*  9661 */           monthsTable = new Hashtable();
/*  9662 */           subConfigTable.put(monthString, monthsTable);
/*       */         } 
/*       */ 
/*       */         
/*  9666 */         Vector selectionsForDates = (Vector)monthsTable.get(dateString);
/*       */         
/*  9668 */         if (selectionsForDates == null) {
/*       */ 
/*       */           
/*  9671 */           selectionsForDates = new Vector();
/*  9672 */           monthsTable.put(dateString, selectionsForDates);
/*       */         } 
/*       */ 
/*       */         
/*  9676 */         selectionsForDates.add(sel);
/*       */       } 
/*       */     } 
/*       */     
/*  9680 */     return companyTable;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Hashtable groupSelectionsByTypeAndSubconfigAndStreetDate(Vector selections) {
/*  9690 */     Hashtable configTable = new Hashtable();
/*  9691 */     if (selections == null) {
/*  9692 */       return configTable;
/*       */     }
/*  9694 */     for (int i = 0; i < selections.size(); i++) {
/*       */       
/*  9696 */       Selection sel = (Selection)selections.elementAt(i);
/*  9697 */       if (sel != null) {
/*       */         
/*  9699 */         sel = SelectionManager.getInstance().getSelectionAndSchedule(sel.getSelectionID());
/*       */ 
/*       */         
/*  9702 */         String typeString = "";
/*  9703 */         String dateString = "";
/*       */         
/*  9705 */         String configName = "";
/*  9706 */         String subconfigName = "";
/*  9707 */         String monthString = "";
/*  9708 */         String statusString = "";
/*  9709 */         String typeConfigString = "";
/*  9710 */         String cycleString = "";
/*       */         
/*  9712 */         dateString = getFormatedDate(sel.getStreetDate());
/*       */         
/*  9714 */         DatePeriod datePeriod = findDatePeriod(sel.getStreetDate());
/*  9715 */         if (datePeriod != null) {
/*  9716 */           cycleString = datePeriod.getCycle();
/*       */         }
/*       */ 
/*       */         
/*  9720 */         if (sel.getStreetDate() != null) {
/*       */ 
/*       */           
/*  9723 */           SimpleDateFormat formatter = new SimpleDateFormat("MMMMM");
/*  9724 */           monthString = formatter.format(sel.getStreetDate().getTime());
/*       */         } 
/*       */         
/*  9727 */         ReleaseType type = sel.getReleaseType();
/*  9728 */         Company company = sel.getCompany();
/*  9729 */         SelectionSubConfiguration subconfig = sel.getSelectionSubConfig();
/*  9730 */         SelectionStatus status = sel.getSelectionStatus();
/*       */         
/*  9732 */         if (subconfig != null)
/*  9733 */           subconfigName = (subconfig.getSelectionSubConfigurationName() == null) ? 
/*  9734 */             "" : subconfig.getSelectionSubConfigurationName(); 
/*  9735 */         if (status != null) {
/*  9736 */           statusString = (status.getName() == null) ? "" : status.getName();
/*       */         }
/*  9738 */         if (type != null) {
/*  9739 */           typeString = (sel.getReleaseType() != null) ? sel.getReleaseType().getName() : "";
/*       */         }
/*       */ 
/*       */ 
/*       */         
/*  9744 */         if (typeString.equalsIgnoreCase("Commercial")) {
/*       */           
/*  9746 */           if (subconfigName.equalsIgnoreCase("EP/Sampler") || 
/*  9747 */             subconfigName.equalsIgnoreCase("Full") || 
/*  9748 */             subconfigName.equalsIgnoreCase("Full Length") || 
/*  9749 */             configName.equalsIgnoreCase("DualDisc"))
/*       */           {
/*       */             
/*  9752 */             subconfigName = "Full Length";
/*       */           }
/*  9754 */           else if (!subconfigName.equals(""))
/*       */           {
/*  9756 */             subconfigName = "Singles";
/*       */           }
/*       */         
/*       */         } else {
/*       */           
/*  9761 */           typeString = "Promos";
/*  9762 */           subconfigName = "";
/*       */         } 
/*       */         
/*  9765 */         typeConfigString = String.valueOf(typeString) + " " + subconfigName;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  9772 */         if (statusString.equalsIgnoreCase("TBS")) {
/*       */           
/*  9774 */           monthString = "13";
/*  9775 */           dateString = statusString;
/*       */         }
/*  9777 */         else if (statusString.equalsIgnoreCase("In The Works")) {
/*       */           
/*  9779 */           monthString = "26";
/*  9780 */           dateString = "ITW";
/*       */ 
/*       */         
/*       */         }
/*  9784 */         else if (monthString.length() < 1) {
/*  9785 */           monthString = "52";
/*       */         } 
/*       */ 
/*       */         
/*  9789 */         Hashtable typeConfigTable = (Hashtable)configTable.get(typeConfigString);
/*  9790 */         if (typeConfigTable == null) {
/*       */ 
/*       */           
/*  9793 */           typeConfigTable = new Hashtable();
/*  9794 */           configTable.put(typeConfigString, typeConfigTable);
/*       */         } 
/*       */ 
/*       */         
/*  9798 */         Hashtable monthsTable = (Hashtable)typeConfigTable.get(monthString);
/*  9799 */         if (monthsTable == null) {
/*       */ 
/*       */           
/*  9802 */           monthsTable = new Hashtable();
/*  9803 */           typeConfigTable.put(monthString, monthsTable);
/*       */         } 
/*       */ 
/*       */         
/*  9807 */         Vector selectionsForDates = (Vector)monthsTable.get(dateString);
/*       */         
/*  9809 */         if (selectionsForDates == null) {
/*       */ 
/*       */           
/*  9812 */           selectionsForDates = new Vector();
/*  9813 */           monthsTable.put(dateString, selectionsForDates);
/*       */         } 
/*       */ 
/*       */         
/*  9817 */         selectionsForDates.add(sel);
/*       */       } 
/*       */     } 
/*       */     
/*  9821 */     return configTable;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Hashtable groupSelectionsByConfigAndStreetDate(Vector selections) {
/*  9837 */     Hashtable groupSelectionsByConfigAndStreetDate = new Hashtable();
/*  9838 */     if (selections == null) {
/*  9839 */       return groupSelectionsByConfigAndStreetDate;
/*       */     }
/*  9841 */     for (int i = 0; i < selections.size(); i++) {
/*       */       
/*  9843 */       Selection sel = (Selection)selections.elementAt(i);
/*  9844 */       if (sel != null) {
/*       */         
/*  9846 */         sel = SelectionManager.getInstance().getSelectionAndSchedule(sel.getSelectionID());
/*       */ 
/*       */         
/*  9849 */         String configString = "", dateString = "";
/*  9850 */         configString = (sel.getSelectionConfig() != null) ? sel.getSelectionConfig().getSelectionConfigurationName() : "";
/*  9851 */         dateString = (sel.getStreetDate() != null) ? getFormatedDate(sel.getStreetDate()) : "";
/*       */         
/*  9853 */         SelectionStatus status = sel.getSelectionStatus();
/*  9854 */         String statusString = "";
/*  9855 */         if (status != null)
/*  9856 */           statusString = (status.getName() == null) ? "" : status.getName(); 
/*  9857 */         if (statusString.equalsIgnoreCase("TBS")) {
/*       */           
/*  9859 */           dateString = statusString;
/*       */         }
/*  9861 */         else if (statusString.equalsIgnoreCase("In The Works")) {
/*       */           
/*  9863 */           dateString = "ITW";
/*       */         } 
/*       */ 
/*       */         
/*  9867 */         Hashtable streetTable = (Hashtable)groupSelectionsByConfigAndStreetDate.get(dateString);
/*  9868 */         if (streetTable == null) {
/*       */ 
/*       */           
/*  9871 */           streetTable = new Hashtable();
/*  9872 */           groupSelectionsByConfigAndStreetDate.put(dateString, streetTable);
/*       */         } 
/*       */ 
/*       */         
/*  9876 */         Vector configsForDate = (Vector)streetTable.get(configString);
/*  9877 */         if (configsForDate == null) {
/*       */ 
/*       */           
/*  9880 */           configsForDate = new Vector();
/*  9881 */           streetTable.put(configString, configsForDate);
/*       */         } 
/*       */ 
/*       */         
/*  9885 */         configsForDate.addElement(sel);
/*       */       } 
/*       */     } 
/*       */     
/*  9889 */     return groupSelectionsByConfigAndStreetDate;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Vector groupSelectionsByReleaseMonth(Vector selections) {
/*  9903 */     Vector groupedSelections = new Vector();
/*  9904 */     if (selections == null) {
/*  9905 */       return groupedSelections;
/*       */     }
/*       */     
/*  9908 */     setSelectionSorting(selections, 1);
/*  9909 */     Collections.sort(selections);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  9916 */     setSelectionSorting(selections, 16);
/*  9917 */     Collections.sort(selections);
/*       */ 
/*       */     
/*  9920 */     Vector monthVector = new Vector();
/*       */ 
/*       */     
/*  9923 */     int currMonth = -10, currYear = -10;
/*       */     
/*  9925 */     boolean justStarted = true;
/*       */     
/*  9927 */     for (int i = 0; i < selections.size(); i++) {
/*       */       
/*  9929 */       Selection sel = (Selection)selections.elementAt(i);
/*       */       
/*  9931 */       if (sel != null) {
/*       */         
/*  9933 */         Calendar streetDate = sel.getStreetDate();
/*       */         
/*  9935 */         String status = (sel.getSelectionStatus() != null && sel.getSelectionStatus().getName() != null) ? 
/*  9936 */           sel.getSelectionStatus().getName() : "";
/*       */ 
/*       */         
/*  9939 */         int selMonth = -1;
/*  9940 */         int selYear = -1;
/*       */         
/*  9942 */         if (status.equalsIgnoreCase("TBS")) {
/*       */           
/*  9944 */           selMonth = 13;
/*  9945 */           selYear = 13;
/*       */         }
/*  9947 */         else if (status.equalsIgnoreCase("In the works")) {
/*       */           
/*  9949 */           selMonth = 26;
/*  9950 */           selYear = 26;
/*       */         } 
/*       */         
/*  9953 */         if (streetDate != null && streetDate.get(2) == currMonth && 
/*  9954 */           streetDate.get(1) == currYear) {
/*       */ 
/*       */           
/*  9957 */           monthVector.addElement(sel);
/*       */         }
/*  9959 */         else if (selMonth == currMonth && 
/*  9960 */           selYear == currYear) {
/*       */ 
/*       */           
/*  9963 */           monthVector.addElement(sel);
/*       */         }
/*  9965 */         else if (streetDate == null && !status.equalsIgnoreCase("TBS") && 
/*  9966 */           !status.equalsIgnoreCase("In The Works") && currMonth == 52) {
/*       */           
/*  9968 */           monthVector.addElement(sel);
/*       */         }
/*       */         else {
/*       */           
/*  9972 */           if (justStarted) {
/*  9973 */             justStarted = false;
/*       */           } else {
/*  9975 */             groupedSelections.addElement(monthVector.clone());
/*       */           } 
/*  9977 */           monthVector = new Vector();
/*  9978 */           monthVector.addElement(sel);
/*  9979 */           if (streetDate != null && !status.equalsIgnoreCase("TBS") && 
/*  9980 */             !status.equalsIgnoreCase("In The Works")) {
/*       */             
/*  9982 */             currMonth = streetDate.get(2);
/*  9983 */             currYear = streetDate.get(1);
/*       */ 
/*       */           
/*       */           }
/*  9987 */           else if (status.equalsIgnoreCase("TBS")) {
/*       */             
/*  9989 */             currMonth = 13;
/*  9990 */             currYear = 13;
/*       */           
/*       */           }
/*  9993 */           else if (status.equalsIgnoreCase("In The Works")) {
/*       */             
/*  9995 */             currMonth = 26;
/*  9996 */             currYear = 26;
/*       */           }
/*       */           else {
/*       */             
/* 10000 */             currMonth = 52;
/* 10001 */             currYear = 52;
/*       */           } 
/*       */         } 
/*       */       } 
/*       */     } 
/*       */ 
/*       */     
/* 10008 */     groupedSelections.addElement(monthVector.clone());
/*       */     
/* 10010 */     return groupedSelections;
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   public static String getMonthNameForUmeReport(Vector selections) {
/* 10016 */     monthName = "";
/*       */     
/* 10018 */     if (selections == null || selections.size() == 0) {
/* 10019 */       return monthName;
/*       */     }
/* 10021 */     Selection sel = (Selection)selections.elementAt(0);
/* 10022 */     if (sel == null) {
/* 10023 */       return monthName;
/*       */     }
/* 10025 */     String status = (sel.getSelectionStatus() != null && sel.getSelectionStatus().getName() != null) ? 
/* 10026 */       sel.getSelectionStatus().getName() : "";
/*       */     
/* 10028 */     if (status.equalsIgnoreCase("TBS")) {
/* 10029 */       return "TBS";
/*       */     }
/* 10031 */     if (status.equalsIgnoreCase("In The Works")) {
/* 10032 */       return "ITW";
/*       */     }
/*       */     
/* 10035 */     if (sel.getStreetDate() == null) {
/* 10036 */       return "No Street Date";
/*       */     }
/*       */     
/* 10039 */     int monthNumber = sel.getStreetDate().get(2);
/*       */     
/* 10041 */     switch (monthNumber) {
/*       */       case 0:
/* 10043 */         return "January";
/* 10044 */       case 1: return "February";
/* 10045 */       case 2: return "March";
/* 10046 */       case 3: return "April";
/* 10047 */       case 4: return "May";
/* 10048 */       case 5: return "June";
/* 10049 */       case 6: return "July";
/* 10050 */       case 7: return "August";
/* 10051 */       case 8: return "September";
/* 10052 */       case 9: return "October";
/* 10053 */       case 10: return "November";
/* 10054 */       case 11: return "December";
/* 10055 */     }  return "TBS";
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Vector getTasksForUmeProdScheduleReport(Selection sel) {
/* 10064 */     Vector tasks = new Vector();
/* 10065 */     int numTasks = 12;
/* 10066 */     for (int i = 0; i < numTasks; i++) {
/* 10067 */       tasks.addElement(null);
/*       */     }
/*       */     
/* 10070 */     Schedule schedule = ScheduleManager.getInstance().getSchedule(sel.getSelectionID());
/* 10071 */     Vector allTasks = new Vector();
/* 10072 */     if (schedule != null) {
/*       */       
/* 10074 */       allTasks = schedule.getTasks();
/* 10075 */       if (allTasks == null) {
/* 10076 */         allTasks = new Vector();
/*       */       }
/*       */     } 
/* 10079 */     for (int i = 0; i < allTasks.size(); i++) {
/*       */       
/* 10081 */       ScheduledTask task = (ScheduledTask)allTasks.elementAt(i);
/* 10082 */       if (task != null) {
/*       */         
/* 10084 */         String taskName = (task.getName() != null) ? 
/* 10085 */           task.getName().toUpperCase() : "";
/*       */ 
/*       */         
/* 10088 */         if (taskName.indexOf("START MEMO RECEIVED") != -1) {
/* 10089 */           tasks.set(0, task);
/*       */         }
/*       */ 
/*       */         
/* 10093 */         if (taskName.indexOf("CLEARANCE DEADLINE") != -1) {
/* 10094 */           tasks.set(1, task);
/*       */         
/*       */         }
/* 10097 */         else if (taskName.indexOf("COMPLETED") != -1 && taskName.indexOf("CREDITS") != -1) {
/* 10098 */           tasks.set(2, task);
/*       */         
/*       */         }
/* 10101 */         else if (taskName.indexOf("FINAL PACKAGING COPY") != -1) {
/* 10102 */           tasks.set(3, task);
/*       */         
/*       */         }
/* 10105 */         else if (taskName.indexOf("MECH") != -1 && taskName.indexOf("ROUTING") != -1) {
/* 10106 */           tasks.set(4, task);
/*       */         
/*       */         }
/* 10109 */         else if (taskName.indexOf("MECH") != -1 && taskName.indexOf("SEPARATIONS") != -1) {
/* 10110 */           tasks.set(5, task);
/*       */         
/*       */         }
/* 10113 */         else if (taskName.indexOf("SOLIC") != -1 && taskName.indexOf("FILM") != -1 && 
/* 10114 */           taskName.indexOf("UMVD") != -1) {
/* 10115 */           tasks.set(6, task);
/*       */         
/*       */         }
/* 10118 */         else if (taskName.indexOf("PACKAGE") != -1 && taskName.indexOf("FILM") != -1 && 
/* 10119 */           taskName.indexOf("SHIPS") != -1 && taskName.indexOf("PRINTER") != -1) {
/* 10120 */           tasks.set(7, task);
/*       */         
/*       */         }
/* 10123 */         else if (taskName.indexOf("MASTER") != -1 && taskName.indexOf("RECEIVED") != -1 && 
/* 10124 */           taskName.indexOf("PLANT") != -1) {
/* 10125 */           tasks.set(8, task);
/*       */         
/*       */         }
/* 10128 */         else if (taskName.indexOf("INITIAL") != -1 && taskName.indexOf("MANUF") != -1 && 
/* 10129 */           taskName.indexOf("QUANTIT") != -1) {
/* 10130 */           tasks.set(9, task);
/*       */         
/*       */         }
/* 10133 */         else if (taskName.indexOf("PRINT") != -1 && taskName.indexOf("RECEIVED") != -1 && 
/* 10134 */           taskName.indexOf("PLANT") != -1) {
/* 10135 */           tasks.set(10, task);
/*       */         
/*       */         }
/* 10138 */         else if (taskName.indexOf("PLANT SHIP") != -1) {
/* 10139 */           tasks.set(11, task);
/*       */         } 
/*       */       } 
/*       */     } 
/* 10143 */     return tasks;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Vector getTasksForUmeProdScheduleReportAbbrev(Selection sel) {
/* 10151 */     Vector tasks = new Vector();
/* 10152 */     int numTasks = 14;
/* 10153 */     for (int i = 0; i < numTasks; i++) {
/* 10154 */       tasks.addElement(null);
/*       */     }
/*       */     
/* 10157 */     Schedule schedule = ScheduleManager.getInstance().getSchedule(sel.getSelectionID());
/* 10158 */     Vector allTasks = new Vector();
/* 10159 */     if (schedule != null) {
/*       */       
/* 10161 */       allTasks = schedule.getTasks();
/* 10162 */       if (allTasks == null) {
/* 10163 */         allTasks = new Vector();
/*       */       }
/*       */     } 
/* 10166 */     for (int i = 0; i < allTasks.size(); i++) {
/*       */       
/* 10168 */       ScheduledTask task = (ScheduledTask)allTasks.elementAt(i);
/* 10169 */       if (task != null) {
/*       */ 
/*       */         
/* 10172 */         String taskAbbrev = getTaskAbbreivationNameById(task.getTaskAbbreviationID());
/*       */         
/* 10174 */         if (taskAbbrev.equalsIgnoreCase("CLR")) {
/*       */           
/* 10176 */           tasks.set(1, task);
/*       */         }
/* 10178 */         else if (taskAbbrev.equalsIgnoreCase("CRD")) {
/*       */           
/* 10180 */           tasks.set(2, task);
/*       */         }
/* 10182 */         else if (taskAbbrev.equalsIgnoreCase("FPC")) {
/*       */           
/* 10184 */           tasks.set(3, task);
/*       */         }
/* 10186 */         else if (taskAbbrev.equalsIgnoreCase("MBR")) {
/*       */           
/* 10188 */           tasks.set(4, task);
/*       */         }
/* 10190 */         else if (taskAbbrev.equalsIgnoreCase("SEPS")) {
/*       */           
/* 10192 */           tasks.set(5, task);
/*       */         }
/* 10194 */         else if (taskAbbrev.equalsIgnoreCase("SFD")) {
/*       */           
/* 10196 */           tasks.set(6, task);
/*       */         }
/* 10198 */         else if (taskAbbrev.equalsIgnoreCase("TAPE")) {
/*       */           
/* 10200 */           tasks.set(8, task);
/*       */         }
/* 10202 */         else if (taskAbbrev.equalsIgnoreCase("PFS")) {
/*       */           
/* 10204 */           tasks.set(7, task);
/*       */         }
/* 10206 */         else if (taskAbbrev.equalsIgnoreCase("MQD")) {
/*       */           
/* 10208 */           tasks.set(9, task);
/*       */         }
/* 10210 */         else if (taskAbbrev.equalsIgnoreCase("PAP")) {
/*       */           
/* 10212 */           tasks.set(10, task);
/*       */         }
/* 10214 */         else if (taskAbbrev.equalsIgnoreCase("PSD")) {
/*       */           
/* 10216 */           tasks.set(11, task);
/*       */         
/*       */         }
/* 10219 */         else if (taskAbbrev.equalsIgnoreCase("SMR")) {
/*       */           
/* 10221 */           tasks.set(0, task);
/*       */         
/*       */         }
/* 10224 */         else if (taskAbbrev.equalsIgnoreCase("P&L")) {
/*       */           
/* 10226 */           tasks.set(12, task);
/*       */         
/*       */         }
/* 10229 */         else if (taskAbbrev.equalsIgnoreCase("PO")) {
/*       */           
/* 10231 */           tasks.set(13, task);
/*       */         } 
/*       */       } 
/*       */     } 
/*       */     
/* 10236 */     return tasks;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Vector getTasksForUmeCustomProdScheduleReport(Selection sel) {
/* 10243 */     Vector tasks = new Vector();
/* 10244 */     int numTasks = 9;
/* 10245 */     for (int i = 0; i < numTasks; i++) {
/* 10246 */       tasks.addElement(null);
/*       */     }
/*       */     
/* 10249 */     Schedule schedule = ScheduleManager.getInstance().getSchedule(sel.getSelectionID());
/* 10250 */     Vector allTasks = new Vector();
/* 10251 */     if (schedule != null) {
/*       */       
/* 10253 */       allTasks = schedule.getTasks();
/* 10254 */       if (allTasks == null) {
/* 10255 */         allTasks = new Vector();
/*       */       }
/*       */     } 
/* 10258 */     for (int i = 0; i < allTasks.size(); i++) {
/*       */       
/* 10260 */       ScheduledTask task = (ScheduledTask)allTasks.elementAt(i);
/* 10261 */       if (task != null) {
/*       */         
/* 10263 */         String taskName = (task.getName() != null) ? 
/* 10264 */           task.getName().toUpperCase() : "";
/*       */ 
/*       */         
/* 10267 */         if (taskName.indexOf("CLEARANCE DEADLINE") != -1) {
/* 10268 */           tasks.set(0, task);
/*       */         
/*       */         }
/* 10271 */         else if (taskName.indexOf("ARTWORK SPECS/LOGOS TO CLIENT") != -1) {
/* 10272 */           tasks.set(1, task);
/*       */         
/*       */         }
/* 10275 */         else if (taskName.indexOf("LABEL COPY TO CLIENT") != -1) {
/* 10276 */           tasks.set(2, task);
/*       */         
/*       */         }
/* 10279 */         else if (taskName.indexOf("ACCOUNT NUMBER") != -1) {
/* 10280 */           tasks.set(3, task);
/*       */         
/*       */         }
/* 10283 */         else if (taskName.indexOf("RECEIVE CLIENT MASTER") != -1) {
/* 10284 */           tasks.set(4, task);
/*       */         
/*       */         }
/* 10287 */         else if (taskName.indexOf("PURCHASE ORDER FROM CLIENT") != -1) {
/* 10288 */           tasks.set(5, task);
/*       */         
/*       */         }
/* 10291 */         else if (taskName.indexOf("GRAPHICS") != -1 && taskName.indexOf("FILM") != -1) {
/* 10292 */           tasks.set(6, task);
/*       */         
/*       */         }
/* 10295 */         else if (taskName.indexOf("PLACE INITIAL ORDER") != -1) {
/* 10296 */           tasks.set(7, task);
/*       */         
/*       */         }
/* 10299 */         else if (taskName.indexOf("PRINT") != -1 && taskName.indexOf("RECEIVED") != -1 && 
/* 10300 */           taskName.indexOf("PLANT") != -1) {
/* 10301 */           tasks.set(8, task);
/*       */         } 
/*       */       } 
/*       */     } 
/*       */     
/* 10306 */     return tasks;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Hashtable groupSelectionsByTypeConfigAndStreetDate(Vector selections) {
/* 10391 */     Hashtable groupSelectionsByTypeConfigAndStreetDate = new Hashtable();
/* 10392 */     if (selections == null) {
/* 10393 */       return groupSelectionsByTypeConfigAndStreetDate;
/*       */     }
/* 10395 */     for (int i = 0; i < selections.size(); i++) {
/*       */       
/* 10397 */       Selection sel = (Selection)selections.elementAt(i);
/* 10398 */       if (sel != null) {
/*       */         
/* 10400 */         sel = SelectionManager.getInstance().getSelectionAndSchedule(sel.getSelectionID());
/*       */ 
/*       */         
/* 10403 */         String typeConfigString = "";
/* 10404 */         String monthString = "";
/* 10405 */         String dayString = "";
/*       */         
/* 10407 */         String typeString = (sel.getReleaseType() != null) ? sel.getReleaseType().getName() : "";
/* 10408 */         String configString = (sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() != null) ? sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() : "";
/*       */ 
/*       */ 
/*       */ 
/*       */         
/* 10413 */         if (typeString.startsWith("Commercial")) {
/* 10414 */           if (configString.equals("8TRK") || 
/* 10415 */             configString.equals("CDROM") || 
/* 10416 */             configString.equals("CDVID") || 
/* 10417 */             configString.equals("DCCASS") || 
/* 10418 */             configString.equals("CASSEP") || 
/* 10419 */             configString.equals("CDEP") || 
/* 10420 */             configString.equals("ECDEP") || 
/* 10421 */             configString.equals("ALBUM") || 
/* 10422 */             configString.equals("CASS") || 
/* 10423 */             configString.equals("CD") || 
/* 10424 */             configString.equals("ECD") || 
/* 10425 */             configString.equals("LASER") || 
/* 10426 */             configString.equals("MIXED") || 
/* 10427 */             configString.equals("DVDVID") || 
/* 10428 */             configString.equals("VIDEO") || 
/* 10429 */             configString.equals("DVDAUD") || 
/* 10430 */             configString.equalsIgnoreCase("DUALDISC"))
/*       */           {
/*       */             
/* 10433 */             typeConfigString = "Commercial Full Length";
/*       */           }
/*       */           else
/*       */           {
/* 10437 */             typeConfigString = "Commercial Single";
/*       */           }
/*       */         
/* 10440 */         } else if (typeString.startsWith("Pro")) {
/*       */           
/* 10442 */           typeConfigString = "Promos";
/*       */         } 
/*       */         
/* 10445 */         if (sel.getStreetDate() != null) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */           
/* 10452 */           SimpleDateFormat formatter = new SimpleDateFormat("MM/yyyy");
/* 10453 */           monthString = formatter.format(sel.getStreetDate().getTime());
/* 10454 */           dayString = getFormatedDate(sel.getStreetDate());
/*       */         } 
/*       */         
/* 10457 */         String statusString = "";
/* 10458 */         SelectionStatus status = sel.getSelectionStatus();
/*       */         
/* 10460 */         if (status != null) {
/* 10461 */           statusString = (status.getName() == null) ? "" : status.getName();
/*       */         }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/* 10468 */         if (statusString.equalsIgnoreCase("TBS")) {
/*       */           
/* 10470 */           monthString = "13";
/*       */         }
/* 10472 */         else if (statusString.equalsIgnoreCase("In The Works")) {
/*       */           
/* 10474 */           monthString = "26";
/*       */ 
/*       */         
/*       */         }
/* 10478 */         else if (monthString.length() < 1) {
/* 10479 */           monthString = "52";
/*       */         } 
/*       */ 
/*       */         
/* 10483 */         Hashtable typeConfigSubTable = (Hashtable)groupSelectionsByTypeConfigAndStreetDate.get(typeConfigString);
/* 10484 */         if (typeConfigSubTable == null) {
/*       */ 
/*       */           
/* 10487 */           typeConfigSubTable = new Hashtable();
/* 10488 */           groupSelectionsByTypeConfigAndStreetDate.put(typeConfigString, typeConfigSubTable);
/*       */         } 
/*       */ 
/*       */         
/* 10492 */         Hashtable monthsTable = (Hashtable)typeConfigSubTable.get(monthString);
/* 10493 */         if (monthsTable == null) {
/*       */ 
/*       */           
/* 10496 */           monthsTable = new Hashtable();
/* 10497 */           typeConfigSubTable.put(monthString, monthsTable);
/*       */         } 
/*       */         
/* 10500 */         Vector selectionsForDates = (Vector)monthsTable.get(dayString);
/*       */ 
/*       */         
/* 10503 */         if (selectionsForDates == null) {
/*       */ 
/*       */           
/* 10506 */           selectionsForDates = new Vector();
/* 10507 */           monthsTable.put(dayString, selectionsForDates);
/*       */         } 
/*       */ 
/*       */         
/* 10511 */         selectionsForDates.addElement(sel);
/*       */       } 
/*       */     } 
/*       */     
/* 10515 */     return groupSelectionsByTypeConfigAndStreetDate;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Hashtable groupSelectionsforIDJByConfigAndStreetDate(Vector selections) {
/* 10531 */     Hashtable groupSelectionsByTypeConfigAndStreetDate = new Hashtable();
/* 10532 */     if (selections == null) {
/* 10533 */       return groupSelectionsByTypeConfigAndStreetDate;
/*       */     }
/* 10535 */     for (int i = 0; i < selections.size(); i++) {
/*       */       
/* 10537 */       Selection sel = (Selection)selections.elementAt(i);
/* 10538 */       if (sel != null) {
/*       */         
/* 10540 */         sel = SelectionManager.getInstance().getSelectionAndSchedule(sel.getSelectionID());
/*       */ 
/*       */         
/* 10543 */         String typeConfigString = "";
/* 10544 */         String monthString = "";
/* 10545 */         String dayString = "";
/*       */         
/* 10547 */         String typeString = (sel.getReleaseType() != null) ? sel.getReleaseType().getName() : "";
/* 10548 */         String configString = (sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() != null) ? sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() : "";
/* 10549 */         String realConfig = (sel.getSelectionConfig().getSelectionConfigurationAbbreviation() != null) ? sel.getSelectionConfig().getSelectionConfigurationAbbreviation() : "";
/*       */ 
/*       */ 
/*       */ 
/*       */         
/* 10554 */         if (typeString.startsWith("Commercial")) {
/* 10555 */           if (configString.equals("8TRK") || 
/* 10556 */             configString.equals("CDROM") || 
/* 10557 */             configString.equals("CDVID") || 
/* 10558 */             configString.equals("DCCASS") || 
/* 10559 */             configString.equals("CASSEP") || 
/* 10560 */             configString.equals("CDEP") || 
/* 10561 */             configString.equals("ECDEP") || 
/* 10562 */             configString.equals("ALBUM") || 
/* 10563 */             configString.equals("CASS") || 
/* 10564 */             configString.equals("CD") || 
/* 10565 */             configString.equals("ECD") || 
/* 10566 */             configString.equals("LASER") || 
/* 10567 */             configString.equals("MIXED") || 
/* 10568 */             configString.equals("DVDVID") || 
/* 10569 */             configString.equals("VIDEO") || 
/* 10570 */             configString.equals("DVDAUD") || 
/* 10571 */             configString.equals("CDADVD") || 
/* 10572 */             configString.equals("CDADDV") || 
/* 10573 */             realConfig.equals("SACD") || 
/* 10574 */             realConfig.equals("DP") || 
/* 10575 */             realConfig.equalsIgnoreCase("DUALDISC") || 
/* 10576 */             realConfig.equals("UMD") || 
/* 10577 */             realConfig.equals("UMDFL"))
/*       */           {
/*       */             
/* 10580 */             typeConfigString = "Commercial Full Length";
/*       */           }
/*       */           else
/*       */           {
/* 10584 */             typeConfigString = "Commercial Single";
/*       */           }
/*       */         
/* 10587 */         } else if (typeString.startsWith("Pro")) {
/*       */           
/* 10589 */           typeConfigString = "Promos";
/*       */         } 
/*       */         
/* 10592 */         if (sel.getStreetDate() != null) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */           
/* 10599 */           SimpleDateFormat formatter = new SimpleDateFormat("MM/yyyy");
/* 10600 */           monthString = formatter.format(sel.getStreetDate().getTime());
/* 10601 */           dayString = getFormatedDate(sel.getStreetDate());
/*       */         } 
/*       */         
/* 10604 */         String statusString = "";
/* 10605 */         SelectionStatus status = sel.getSelectionStatus();
/*       */         
/* 10607 */         if (status != null) {
/* 10608 */           statusString = (status.getName() == null) ? "" : status.getName();
/*       */         }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/* 10615 */         if (statusString.equalsIgnoreCase("TBS")) {
/*       */           
/* 10617 */           monthString = "13";
/*       */         }
/* 10619 */         else if (statusString.equalsIgnoreCase("In The Works")) {
/*       */           
/* 10621 */           monthString = "26";
/*       */ 
/*       */         
/*       */         }
/* 10625 */         else if (monthString.length() < 1) {
/* 10626 */           monthString = "52";
/*       */         } 
/*       */ 
/*       */         
/* 10630 */         Hashtable typeConfigSubTable = (Hashtable)groupSelectionsByTypeConfigAndStreetDate.get(typeConfigString);
/* 10631 */         if (typeConfigSubTable == null) {
/*       */ 
/*       */           
/* 10634 */           typeConfigSubTable = new Hashtable();
/* 10635 */           groupSelectionsByTypeConfigAndStreetDate.put(typeConfigString, typeConfigSubTable);
/*       */         } 
/*       */ 
/*       */         
/* 10639 */         Hashtable monthsTable = (Hashtable)typeConfigSubTable.get(monthString);
/* 10640 */         if (monthsTable == null) {
/*       */ 
/*       */           
/* 10643 */           monthsTable = new Hashtable();
/* 10644 */           typeConfigSubTable.put(monthString, monthsTable);
/*       */         } 
/*       */         
/* 10647 */         Vector selectionsForDates = (Vector)monthsTable.get(dayString);
/*       */ 
/*       */         
/* 10650 */         if (selectionsForDates == null) {
/*       */ 
/*       */           
/* 10653 */           selectionsForDates = new Vector();
/* 10654 */           monthsTable.put(dayString, selectionsForDates);
/*       */         } 
/*       */ 
/*       */         
/* 10658 */         selectionsForDates.addElement(sel);
/*       */       } 
/*       */     } 
/*       */     
/* 10662 */     return groupSelectionsByTypeConfigAndStreetDate;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Hashtable groupSelectionsByTypeAndStreetDate(Vector selections) {
/* 10679 */     Hashtable groupSelectionsByConfigAndStreetDate = new Hashtable();
/* 10680 */     if (selections == null) {
/* 10681 */       return groupSelectionsByConfigAndStreetDate;
/*       */     }
/* 10683 */     for (int i = 0; i < selections.size(); i++) {
/*       */       
/* 10685 */       Selection sel = (Selection)selections.elementAt(i);
/* 10686 */       if (sel != null) {
/*       */ 
/*       */         
/* 10689 */         String typeString = "", monthString = "";
/*       */         
/* 10691 */         ReleaseType type = sel.getReleaseType();
/*       */         
/* 10693 */         if (type != null) {
/* 10694 */           typeString = (sel.getReleaseType() != null) ? sel.getReleaseType().getName() : "";
/*       */         }
/* 10696 */         if (sel.getStreetDate() != null) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */           
/* 10702 */           SimpleDateFormat formatter = new SimpleDateFormat("MM/yyyy");
/* 10703 */           monthString = formatter.format(sel.getStreetDate().getTime());
/*       */         } 
/*       */ 
/*       */         
/* 10707 */         Hashtable typeSubTable = (Hashtable)groupSelectionsByConfigAndStreetDate.get(typeString);
/* 10708 */         if (typeSubTable == null) {
/*       */ 
/*       */           
/* 10711 */           typeSubTable = new Hashtable();
/* 10712 */           groupSelectionsByConfigAndStreetDate.put(typeString, typeSubTable);
/*       */         } 
/*       */         
/* 10715 */         SelectionStatus status = sel.getSelectionStatus();
/*       */         
/* 10717 */         String statusString = "";
/*       */         
/* 10719 */         if (status != null) {
/* 10720 */           statusString = (status.getName() == null) ? "" : status.getName();
/*       */         }
/* 10722 */         if (statusString.equalsIgnoreCase("TBS")) {
/*       */           
/* 10724 */           monthString = "13";
/*       */         }
/* 10726 */         else if (statusString.equalsIgnoreCase("In The Works")) {
/*       */           
/* 10728 */           monthString = "26";
/*       */ 
/*       */         
/*       */         }
/* 10732 */         else if (monthString.length() < 1) {
/* 10733 */           monthString = "52";
/*       */         } 
/*       */ 
/*       */         
/* 10737 */         Vector selectionsForDate = (Vector)typeSubTable.get(monthString);
/* 10738 */         if (selectionsForDate == null) {
/*       */ 
/*       */           
/* 10741 */           selectionsForDate = new Vector();
/* 10742 */           typeSubTable.put(monthString, selectionsForDate);
/*       */         } 
/*       */ 
/*       */         
/* 10746 */         selectionsForDate.addElement(sel);
/*       */       } 
/*       */     } 
/*       */     
/* 10750 */     return groupSelectionsByConfigAndStreetDate;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Hashtable groupSelectionsByDigitalTypeAndStreetDate(Vector selections) {
/* 10767 */     Hashtable groupSelectionsByTypeAndStreetDate = new Hashtable();
/* 10768 */     if (selections == null) {
/* 10769 */       return groupSelectionsByTypeAndStreetDate;
/*       */     }
/* 10771 */     for (int i = 0; i < selections.size(); i++) {
/*       */       
/* 10773 */       Selection sel = (Selection)selections.elementAt(i);
/* 10774 */       if (sel != null) {
/*       */ 
/*       */ 
/*       */         
/* 10778 */         String typeString = "", monthString = "";
/*       */         
/* 10780 */         String subConfigString = (sel.getSelectionSubConfig().getSelectionSubConfigurationName() != null) ? sel.getSelectionSubConfig().getSelectionSubConfigurationName() : "";
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/* 10787 */         if (sel.getDigitalRlsDate() != null) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */           
/* 10793 */           SimpleDateFormat formatter = new SimpleDateFormat("MM/yyyy");
/* 10794 */           monthString = formatter.format(sel.getDigitalRlsDate().getTime());
/*       */         } 
/*       */ 
/*       */ 
/*       */         
/* 10799 */         Hashtable typeSubTable = (Hashtable)groupSelectionsByTypeAndStreetDate.get(subConfigString);
/* 10800 */         if (typeSubTable == null) {
/*       */ 
/*       */           
/* 10803 */           typeSubTable = new Hashtable();
/* 10804 */           groupSelectionsByTypeAndStreetDate.put(subConfigString, typeSubTable);
/*       */         } 
/*       */         
/* 10807 */         SelectionStatus status = sel.getSelectionStatus();
/*       */         
/* 10809 */         String statusString = "";
/*       */         
/* 10811 */         if (status != null) {
/* 10812 */           statusString = (status.getName() == null) ? "" : status.getName();
/*       */         }
/* 10814 */         if (statusString.equalsIgnoreCase("TBS")) {
/*       */           
/* 10816 */           monthString = "13";
/*       */         }
/* 10818 */         else if (statusString.equalsIgnoreCase("In The Works")) {
/*       */           
/* 10820 */           monthString = "26";
/*       */ 
/*       */         
/*       */         }
/* 10824 */         else if (monthString.length() < 1) {
/* 10825 */           monthString = "52";
/*       */         } 
/*       */ 
/*       */         
/* 10829 */         Vector selectionsForDate = (Vector)typeSubTable.get(monthString);
/* 10830 */         if (selectionsForDate == null) {
/*       */ 
/*       */           
/* 10833 */           selectionsForDate = new Vector();
/* 10834 */           typeSubTable.put(monthString, selectionsForDate);
/*       */         } 
/*       */ 
/*       */         
/* 10838 */         selectionsForDate.addElement(sel);
/*       */       } 
/*       */     } 
/*       */     
/* 10842 */     return groupSelectionsByTypeAndStreetDate;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Hashtable groupSelectionsByMonthAndDayAndDivision(Vector selections) {
/* 10860 */     Hashtable groupSelectionsByMonthAndDayAndDivision = new Hashtable();
/* 10861 */     if (selections == null) {
/* 10862 */       return groupSelectionsByMonthAndDayAndDivision;
/*       */     }
/* 10864 */     for (int i = 0; i < selections.size(); i++) {
/*       */       
/* 10866 */       Selection sel = (Selection)selections.elementAt(i);
/* 10867 */       if (sel != null) {
/*       */ 
/*       */         
/* 10870 */         String divisionString = "", dateString = "", monthString = "";
/*       */         
/* 10872 */         String type = SelectionManager.getLookupObjectValue(sel.getReleaseType());
/*       */         
/* 10874 */         Division selDivision = sel.getDivision();
/*       */         
/* 10876 */         if (selDivision != null) {
/* 10877 */           divisionString = (selDivision != null) ? selDivision.getName() : "";
/*       */         }
/* 10879 */         if (sel.getStreetDate() != null) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */           
/* 10885 */           SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
/* 10886 */           dateString = formatter.format(sel.getStreetDate().getTime());
/*       */           
/* 10888 */           SimpleDateFormat formatter2 = new SimpleDateFormat("MM/yyyy");
/* 10889 */           monthString = formatter2.format(sel.getStreetDate().getTime());
/*       */         } 
/*       */         
/* 10892 */         SelectionStatus status = sel.getSelectionStatus();
/*       */         
/* 10894 */         String statusString = "";
/*       */         
/* 10896 */         if (status != null) {
/* 10897 */           statusString = (status.getName() == null) ? "" : status.getName();
/*       */         }
/* 10899 */         if (statusString.equalsIgnoreCase("TBS")) {
/*       */           
/* 10901 */           monthString = "13";
/*       */         }
/* 10903 */         else if (statusString.equalsIgnoreCase("In The Works")) {
/*       */           
/* 10905 */           monthString = "26";
/*       */ 
/*       */         
/*       */         }
/* 10909 */         else if (monthString.length() < 1) {
/* 10910 */           monthString = "52";
/*       */         } 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/* 10916 */         Hashtable monthSubTable = (Hashtable)groupSelectionsByMonthAndDayAndDivision.get(monthString);
/* 10917 */         if (monthSubTable == null) {
/*       */           
/* 10919 */           monthSubTable = new Hashtable();
/* 10920 */           groupSelectionsByMonthAndDayAndDivision.put(monthString, monthSubTable);
/*       */         } 
/*       */ 
/*       */         
/* 10924 */         Hashtable dateSubTable = (Hashtable)monthSubTable.get(dateString);
/* 10925 */         if (dateSubTable == null) {
/*       */           
/* 10927 */           dateSubTable = new Hashtable();
/* 10928 */           monthSubTable.put(dateString, dateSubTable);
/*       */         } 
/*       */ 
/*       */         
/* 10932 */         Vector selectionsForDate = (Vector)dateSubTable.get(divisionString);
/* 10933 */         if (selectionsForDate == null) {
/*       */ 
/*       */           
/* 10936 */           selectionsForDate = new Vector();
/* 10937 */           dateSubTable.put(divisionString, selectionsForDate);
/*       */         } 
/*       */ 
/*       */         
/* 10941 */         selectionsForDate.addElement(sel);
/*       */       } 
/*       */     } 
/*       */     
/* 10945 */     return groupSelectionsByMonthAndDayAndDivision;
/*       */   }
/*       */ 
/*       */   
/*       */   public static Hashtable groupSelectionsByArtistAndMonth(Vector selections) {
/* 10950 */     Hashtable groupSelectionsByArtistAndMonth = new Hashtable();
/* 10951 */     if (selections == null) {
/* 10952 */       return groupSelectionsByArtistAndMonth;
/*       */     }
/* 10954 */     for (int i = 0; i < selections.size(); i++) {
/*       */       
/* 10956 */       Selection sel = (Selection)selections.elementAt(i);
/* 10957 */       if (sel != null) {
/*       */ 
/*       */         
/* 10960 */         String artistString = "", monthString = "";
/*       */         
/* 10962 */         if (sel.getStreetDate() != null) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */           
/* 10968 */           SimpleDateFormat formatter2 = new SimpleDateFormat("MM/yyyy");
/* 10969 */           monthString = formatter2.format(sel.getStreetDate().getTime());
/*       */         } 
/*       */         
/* 10972 */         artistString = sel.getArtist();
/*       */         
/* 10974 */         SelectionStatus status = sel.getSelectionStatus();
/*       */         
/* 10976 */         String statusString = "";
/*       */         
/* 10978 */         if (status != null) {
/* 10979 */           statusString = (status.getName() == null) ? "" : status.getName();
/*       */         }
/* 10981 */         if (statusString.equalsIgnoreCase("TBS")) {
/*       */           
/* 10983 */           monthString = "13";
/*       */         }
/* 10985 */         else if (statusString.equalsIgnoreCase("In The Works")) {
/*       */           
/* 10987 */           monthString = "26";
/*       */ 
/*       */         
/*       */         }
/* 10991 */         else if (monthString.length() < 1) {
/* 10992 */           monthString = "52";
/*       */         } 
/*       */ 
/*       */         
/* 10996 */         String keyString = String.valueOf(sel.getSelectionID());
/*       */ 
/*       */         
/* 10999 */         Hashtable artistSubTable = (Hashtable)groupSelectionsByArtistAndMonth.get(artistString);
/* 11000 */         if (artistSubTable == null) {
/*       */           
/* 11002 */           artistSubTable = new Hashtable();
/* 11003 */           groupSelectionsByArtistAndMonth.put(artistString, artistSubTable);
/*       */         } 
/*       */ 
/*       */         
/* 11007 */         Vector selectionsForMonth = (Vector)artistSubTable.get(monthString);
/* 11008 */         if (selectionsForMonth == null) {
/*       */ 
/*       */           
/* 11011 */           selectionsForMonth = new Vector();
/* 11012 */           artistSubTable.put(monthString, selectionsForMonth);
/*       */         } 
/*       */ 
/*       */         
/* 11016 */         selectionsForMonth.addElement(sel);
/*       */       } 
/*       */     } 
/*       */     
/* 11020 */     return groupSelectionsByArtistAndMonth;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Hashtable groupSelectionsForMcaByTypeConfigAndStreetDate(Vector selections) {
/* 11039 */     Hashtable groupSelectionsByTypeConfigAndStreetDate = new Hashtable();
/* 11040 */     if (selections == null) {
/* 11041 */       return groupSelectionsByTypeConfigAndStreetDate;
/*       */     }
/* 11043 */     for (int i = 0; i < selections.size(); i++) {
/*       */ 
/*       */       
/* 11046 */       Selection sel = (Selection)selections.elementAt(i);
/* 11047 */       if (sel != null) {
/*       */         
/* 11049 */         sel = SelectionManager.getInstance().getSelectionAndSchedule(sel.getSelectionID());
/*       */         
/* 11051 */         String monthString = "";
/* 11052 */         String dayString = "";
/*       */         
/* 11054 */         if (sel.getStreetDate() != null) {
/*       */           
/* 11056 */           SimpleDateFormat formatter = new SimpleDateFormat("MM/yyyy");
/* 11057 */           monthString = formatter.format(sel.getStreetDate().getTime());
/* 11058 */           dayString = getFormatedDate(sel.getStreetDate());
/*       */         } 
/*       */         
/* 11061 */         Vector selectionsForDates = (Vector)groupSelectionsByTypeConfigAndStreetDate.get(monthString);
/* 11062 */         if (selectionsForDates == null) {
/*       */ 
/*       */           
/* 11065 */           selectionsForDates = new Vector();
/* 11066 */           groupSelectionsByTypeConfigAndStreetDate.put(monthString, selectionsForDates);
/*       */         } 
/*       */ 
/*       */         
/* 11070 */         selectionsForDates.addElement(sel);
/*       */       } 
/*       */     } 
/*       */     
/* 11074 */     return groupSelectionsByTypeConfigAndStreetDate;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Hashtable groupSelectionsForMcaProductionByTypeConfigAndStreetDate(Vector selections) {
/* 11092 */     Hashtable groupSelectionsByTypeConfigAndStreetDate = new Hashtable();
/* 11093 */     if (selections == null) {
/* 11094 */       return groupSelectionsByTypeConfigAndStreetDate;
/*       */     }
/* 11096 */     for (int i = 0; i < selections.size(); i++) {
/*       */       
/* 11098 */       Selection sel = (Selection)selections.elementAt(i);
/* 11099 */       if (sel != null) {
/*       */         
/* 11101 */         sel = SelectionManager.getInstance().getSelectionAndSchedule(sel.getSelectionID());
/*       */ 
/*       */         
/* 11104 */         String typeConfigString = "";
/* 11105 */         String monthString = "";
/* 11106 */         String dayString = "";
/*       */         
/* 11108 */         String typeString = (sel.getReleaseType() != null) ? sel.getReleaseType().getName() : "";
/*       */         
/* 11110 */         String categoryString = (sel.getProductCategory() != null) ? sel.getProductCategory().getName() : "";
/* 11111 */         String configString = (sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() != null) ? sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() : "";
/* 11112 */         String realConfig = (sel.getSelectionConfig().getSelectionConfigurationAbbreviation() != null) ? sel.getSelectionConfig().getSelectionConfigurationAbbreviation() : "";
/*       */ 
/*       */ 
/*       */ 
/*       */         
/* 11117 */         if (categoryString.equals("Advances")) {
/* 11118 */           typeConfigString = "Frontline";
/*       */         }
/* 11120 */         else if (typeString.startsWith("Commercial")) {
/* 11121 */           if (configString.equals("8TRK") || 
/* 11122 */             realConfig.equals("DVV") || 
/* 11123 */             configString.equals("ALBUM") || 
/* 11124 */             configString.equals("CASS") || 
/* 11125 */             configString.equals("CD") || 
/* 11126 */             configString.equals("ECD") || 
/* 11127 */             configString.equals("CDROM") || 
/* 11128 */             configString.equals("CDVID") || 
/* 11129 */             configString.equals("DCCASS") || 
/* 11130 */             configString.equals("LASER") || 
/* 11131 */             configString.equals("MIXED") || 
/* 11132 */             configString.equals("VIDEO") || 
/* 11133 */             configString.equals("CDADVD") || 
/* 11134 */             configString.equals("DP") || 
/* 11135 */             realConfig.equals("DVA") || 
/* 11136 */             realConfig.equals("SACD") || 
/* 11137 */             realConfig.equalsIgnoreCase("DUALDISC"))
/*       */           {
/* 11139 */             typeConfigString = "Frontline";
/*       */ 
/*       */           
/*       */           }
/* 11143 */           else if (configString.equals("VNYL7") || 
/* 11144 */             configString.equals("VNYL10") || 
/* 11145 */             configString.equals("VNYL12") || 
/* 11146 */             configString.equals("ESNGL") || 
/* 11147 */             configString.equals("CASSMX") || 
/* 11148 */             configString.equals("CDMX") || 
/* 11149 */             configString.equals("ECDMX") || 
/* 11150 */             configString.equals("CASSGL") || 
/* 11151 */             configString.equals("CDSGL") || 
/* 11152 */             configString.equals("DVDASL") || 
/* 11153 */             configString.equals("DVDVSL") || 
/* 11154 */             configString.equals("ECDSGL"))
/*       */           {
/* 11156 */             typeConfigString = "Singles";
/*       */           
/*       */           }
/*       */ 
/*       */         
/*       */         }
/* 11162 */         else if (typeString.startsWith("Pro")) {
/*       */           
/* 11164 */           if (configString.equals("8TRK") || 
/* 11165 */             configString.equals("CDROM") || 
/* 11166 */             configString.equals("CDVID") || 
/* 11167 */             configString.equals("DCCASS") || 
/* 11168 */             configString.equals("CASSEP") || 
/* 11169 */             configString.equals("CDEP") || 
/* 11170 */             configString.equals("CDMX") || 
/* 11171 */             configString.equals("ECDEP") || 
/* 11172 */             configString.equals("ALBUM") || 
/* 11173 */             configString.equals("CASS") || 
/* 11174 */             configString.equals("CD") || 
/* 11175 */             configString.equals("ECD") || 
/* 11176 */             configString.equals("LASER") || 
/* 11177 */             configString.equals("MIXED") || 
/* 11178 */             configString.equals("VIDEO") || 
/* 11179 */             configString.equals("VNYL12") || 
/* 11180 */             configString.equals("VNYL7") || 
/* 11181 */             configString.equals("VNYL10") || 
/* 11182 */             configString.equals("ESNGL") || 
/* 11183 */             configString.equals("CASSMX") || 
/* 11184 */             configString.equals("CASSGL") || 
/* 11185 */             configString.equals("CDSGL") || 
/* 11186 */             configString.equals("ECDSGL")) {
/*       */             
/* 11188 */             typeConfigString = "Singles";
/*       */           }
/* 11190 */           else if (configString.equals("DVDASL") || 
/* 11191 */             configString.equals("DVDAUD") || 
/* 11192 */             configString.equals("DVDVID") || 
/* 11193 */             configString.equals("DVDVSL") || 
/* 11194 */             realConfig.equalsIgnoreCase("DUALDISC")) {
/* 11195 */             typeConfigString = "Frontline";
/*       */           } 
/*       */         } 
/*       */ 
/*       */ 
/*       */         
/* 11201 */         if (sel.getStreetDate() != null) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */           
/* 11208 */           SimpleDateFormat formatter = new SimpleDateFormat("MM/yyyy");
/* 11209 */           monthString = formatter.format(sel.getStreetDate().getTime());
/* 11210 */           dayString = getFormatedDate(sel.getStreetDate());
/*       */         } 
/*       */         
/* 11213 */         String statusString = "";
/* 11214 */         SelectionStatus status = sel.getSelectionStatus();
/*       */         
/* 11216 */         if (status != null) {
/* 11217 */           statusString = (status.getName() == null) ? "" : status.getName();
/*       */         }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/* 11224 */         if (statusString.equalsIgnoreCase("TBS")) {
/*       */           
/* 11226 */           monthString = "13";
/*       */         }
/* 11228 */         else if (statusString.equalsIgnoreCase("In The Works")) {
/*       */           
/* 11230 */           monthString = "26";
/*       */ 
/*       */         
/*       */         }
/* 11234 */         else if (monthString.length() < 1) {
/* 11235 */           monthString = "52";
/*       */         } 
/*       */ 
/*       */         
/* 11239 */         Hashtable typeConfigSubTable = (Hashtable)groupSelectionsByTypeConfigAndStreetDate.get(typeConfigString);
/* 11240 */         if (typeConfigSubTable == null) {
/*       */ 
/*       */           
/* 11243 */           typeConfigSubTable = new Hashtable();
/* 11244 */           groupSelectionsByTypeConfigAndStreetDate.put(typeConfigString, typeConfigSubTable);
/*       */         } 
/*       */ 
/*       */         
/* 11248 */         Hashtable monthsTable = (Hashtable)typeConfigSubTable.get(monthString);
/* 11249 */         if (monthsTable == null) {
/*       */ 
/*       */           
/* 11252 */           monthsTable = new Hashtable();
/* 11253 */           typeConfigSubTable.put(monthString, monthsTable);
/*       */         } 
/*       */         
/* 11256 */         Vector selectionsForDates = (Vector)monthsTable.get(dayString);
/*       */ 
/*       */         
/* 11259 */         if (selectionsForDates == null) {
/*       */ 
/*       */           
/* 11262 */           selectionsForDates = new Vector();
/* 11263 */           monthsTable.put(dayString, selectionsForDates);
/*       */         } 
/*       */ 
/*       */         
/* 11267 */         selectionsForDates.addElement(sel);
/*       */       } 
/*       */     } 
/*       */     
/* 11271 */     return groupSelectionsByTypeConfigAndStreetDate;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Hashtable groupSelectionsBySubConfigAndStreetDate(Vector selections) {
/* 11289 */     Hashtable groupSelectionsBySubConfigAndStreetDate = new Hashtable();
/* 11290 */     if (selections == null) {
/* 11291 */       return groupSelectionsBySubConfigAndStreetDate;
/*       */     }
/* 11293 */     for (int i = 0; i < selections.size(); i++) {
/*       */       
/* 11295 */       Selection sel = (Selection)selections.elementAt(i);
/* 11296 */       if (sel != null) {
/*       */         
/* 11298 */         sel = SelectionManager.getInstance().getSelectionAndSchedule(sel.getSelectionID());
/*       */ 
/*       */         
/* 11301 */         String configString = "", dateString = "";
/*       */         
/* 11303 */         configString = (sel.getSelectionSubConfig() != null) ? sel.getSelectionSubConfig().getSelectionSubConfigurationName() : "";
/*       */         
/* 11305 */         SelectionStatus status = sel.getSelectionStatus();
/* 11306 */         String statusString = "";
/*       */         
/* 11308 */         if (status != null) {
/* 11309 */           statusString = (status.getName() == null) ? "" : status.getName();
/*       */         }
/* 11311 */         if (statusString.equalsIgnoreCase("TBS")) {
/* 11312 */           dateString = "TBS " + ((sel.getStreetDate() != null) ? getFormatedDate(sel.getStreetDate()) : "");
/* 11313 */         } else if (statusString.equalsIgnoreCase("In The Works")) {
/* 11314 */           dateString = "ITW " + ((sel.getStreetDate() != null) ? getFormatedDate(sel.getStreetDate()) : "");
/*       */         } else {
/* 11316 */           dateString = (sel.getStreetDate() != null) ? getFormatedDate(sel.getStreetDate()) : "No street date";
/*       */         } 
/* 11318 */         if (configString != null)
/*       */         {
/* 11320 */           if (configString.indexOf("Single") > 0) {
/* 11321 */             configString = "Singles";
/*       */           } else {
/* 11323 */             configString = "Full Length";
/*       */           } 
/*       */         }
/*       */         
/* 11327 */         Hashtable configSubTable = (Hashtable)groupSelectionsBySubConfigAndStreetDate.get(configString);
/* 11328 */         if (configSubTable == null) {
/*       */ 
/*       */           
/* 11331 */           configSubTable = new Hashtable();
/* 11332 */           groupSelectionsBySubConfigAndStreetDate.put(configString, configSubTable);
/*       */         } 
/*       */ 
/*       */         
/* 11336 */         Vector selectionsForDate = (Vector)configSubTable.get(dateString);
/* 11337 */         if (selectionsForDate == null) {
/*       */ 
/*       */           
/* 11340 */           selectionsForDate = new Vector();
/* 11341 */           configSubTable.put(dateString, selectionsForDate);
/*       */         } 
/*       */ 
/*       */         
/* 11345 */         selectionsForDate.addElement(sel);
/*       */       } 
/*       */     } 
/*       */     
/* 11349 */     return groupSelectionsBySubConfigAndStreetDate;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static void setSelectionSorting(Vector selections, int sortBy) {
/* 11361 */     if (selections == null) {
/*       */       return;
/*       */     }
/* 11364 */     for (int i = 0; i < selections.size(); i++)
/*       */     {
/* 11366 */       ((Selection)selections.elementAt(i)).setSortBy(sortBy);
/*       */     }
/*       */   }
/*       */ 
/*       */   
/*       */   public static Vector getPrefixCodes(int companyId) {
/* 11372 */     Vector prefixCodes = new Vector();
/*       */     
/* 11374 */     Vector prefixVector = Cache.getPrefixCodes();
/*       */     
/* 11376 */     for (int j = 0; j < prefixVector.size(); j++) {
/*       */       
/* 11378 */       PrefixCode prefixCode = (PrefixCode)prefixVector.elementAt(j);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/* 11385 */       if (prefixCode.getPrefixCodeSubValue() == companyId)
/*       */       {
/* 11387 */         prefixCodes.addElement(prefixCode);
/*       */       }
/*       */     } 
/*       */     
/* 11391 */     return prefixCodes;
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   public static int countTasksWithComments(Vector tasks) {
/* 11397 */     int count = 0;
/*       */     
/* 11399 */     if (tasks == null) {
/* 11400 */       return count;
/*       */     }
/* 11402 */     for (int i = 0; i < tasks.size(); i++) {
/*       */       
/* 11404 */       ScheduledTask task = (ScheduledTask)tasks.get(i);
/*       */ 
/*       */       
/* 11407 */       String taskComments = (task.getComments() != null) ? task.getComments().trim() : "";
/* 11408 */       if (!taskComments.equals("") && !taskComments.equalsIgnoreCase("null")) {
/* 11409 */         count++;
/*       */       }
/*       */     } 
/* 11412 */     return count;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static String formatQuantityWithCommas(String quantity) {
/* 11423 */     if (quantity == null || quantity.length() < 4) {
/* 11424 */       return quantity;
/*       */     }
/* 11426 */     quantity = removeCommas(quantity);
/*       */     
/* 11428 */     int commas = quantity.length() / 3;
/* 11429 */     int j = 0;
/* 11430 */     for (int i = 1; i <= commas; i++) {
/*       */       
/* 11432 */       quantity = String.valueOf(quantity.substring(0, quantity.length() - 3 * i + j)) + 
/* 11433 */         "," + 
/* 11434 */         quantity.substring(quantity.length() - 3 * i + j, quantity.length());
/* 11435 */       j++;
/*       */     } 
/* 11437 */     if (quantity.charAt(0) == ',')
/* 11438 */       quantity = quantity.substring(1, quantity.length()); 
/* 11439 */     return quantity;
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   public static String removeCommas(String value) {
/* 11445 */     int index = -1;
/*       */     
/*       */     while (true) {
/* 11448 */       index = value.indexOf(",");
/* 11449 */       if (index > 0) {
/*       */         
/* 11451 */         value = String.valueOf(value.substring(0, index)) + value.substring(index + 1, value.length());
/*       */         
/*       */         continue;
/*       */       } 
/*       */       break;
/*       */     } 
/* 11457 */     return value;
/*       */   }
/*       */ 
/*       */   
/*       */   public static int lineCount(String[] strings, int[] maxChars) {
/* 11462 */     int lineCount = 0;
/* 11463 */     int stringLength = 0;
/*       */     
/* 11465 */     for (int i = 0; i < strings.length; i++) {
/*       */       
/* 11467 */       int lines = 0;
/*       */       
/* 11469 */       String theString = strings[i];
/* 11470 */       int lineLength = maxChars[i];
/*       */       
/* 11472 */       stringLength = theString.length();
/*       */       
/* 11474 */       if (stringLength > 0) {
/* 11475 */         lines = stringLength / lineLength;
/*       */       }
/* 11477 */       if (lines > lineCount) {
/* 11478 */         lineCount = lines;
/*       */       }
/*       */     } 
/*       */ 
/*       */     
/* 11483 */     return lineCount;
/*       */   }
/*       */ 
/*       */   
/*       */   public static int lineCount(String s1, String s2) {
/* 11488 */     int lineCount = 0;
/* 11489 */     int stringLength = 0;
/* 11490 */     int maxChars = 21;
/*       */     
/* 11492 */     stringLength = s1.length();
/*       */     
/* 11494 */     if (s2.length() > stringLength) {
/* 11495 */       stringLength = s2.length();
/*       */     }
/* 11497 */     if (stringLength > 0)
/*       */     {
/* 11499 */       lineCount = stringLength / 21;
/*       */     }
/*       */     
/* 11502 */     return lineCount;
/*       */   }
/*       */ 
/*       */   
/*       */   public static String urlEncode(String s1) {
/* 11507 */     if (s1 != null) {
/*       */       
/* 11509 */       String newString = "";
/* 11510 */       for (int i = 0; i < s1.length(); i++) {
/*       */         
/* 11512 */         if (s1.substring(i, i + 1).equalsIgnoreCase("'")) {
/*       */           
/* 11514 */           newString = String.valueOf(newString) + "\\'";
/*       */ 
/*       */         
/*       */         }
/* 11518 */         else if (s1.substring(i, i + 1).equalsIgnoreCase("\"")) {
/*       */           
/* 11520 */           newString = String.valueOf(newString) + "\\\"";
/*       */         } else {
/*       */           
/* 11523 */           newString = String.valueOf(newString) + s1.substring(i, i + 1);
/*       */         } 
/* 11525 */       }  return newString;
/*       */     } 
/* 11527 */     return s1;
/*       */   }
/*       */ 
/*       */   
/*       */   public static String stringFormatFont(String strings, int colWidth, Font fontUsed) {
/* 11532 */     int stringLength = 0;
/* 11533 */     int charcount = 0;
/* 11534 */     String theString = strings;
/* 11535 */     String theNewString = "";
/* 11536 */     String theTempCharArray = "";
/* 11537 */     char lcSpaceCheck = '\n';
/* 11538 */     char lcSpace = ' ';
/* 11539 */     int canFit = 0;
/* 11540 */     int i = 0;
/* 11541 */     int ispaceLocation = 0;
/*       */     
/* 11543 */     Frame frame = null;
/*       */     
/* 11545 */     frame = new Frame();
/* 11546 */     frame.addNotify();
/*       */     
/* 11548 */     Image image = frame.createImage(640, 480);
/* 11549 */     Graphics g = image.getGraphics();
/*       */     
/* 11551 */     Graphics2D g2 = (Graphics2D)g;
/* 11552 */     FontMetrics fm = g2.getFontMetrics(fontUsed);
/*       */     
/* 11554 */     stringLength = theString.length();
/* 11555 */     for (int x = 0; x < stringLength; x++) {
/*       */       
/* 11557 */       i = 0;
/* 11558 */       if (theString.charAt(x) == '\n' || canFit >= colWidth) {
/*       */ 
/*       */ 
/*       */ 
/*       */         
/* 11563 */         if (theString.charAt(x) == '\n') {
/* 11564 */           theNewString = String.valueOf(theNewString.trim()) + theString.charAt(x);
/* 11565 */         } else if (theString.charAt(x) == ' ') {
/* 11566 */           theNewString = String.valueOf(theNewString.trim()) + '\n';
/*       */         } else {
/*       */           
/* 11569 */           theNewString = String.valueOf(theNewString) + theString.charAt(x);
/* 11570 */           charcount++;
/* 11571 */           int liCurrentLength = theNewString.length() - 1;
/* 11572 */           i = charcount;
/*       */ 
/*       */           
/*       */           do {
/* 11576 */             ispaceLocation = liCurrentLength - charcount - i;
/* 11577 */             lcSpaceCheck = theNewString.charAt(ispaceLocation);
/* 11578 */             --i;
/* 11579 */           } while (i > 1 && lcSpaceCheck != ' ');
/*       */           
/* 11581 */           if (i <= 1) {
/* 11582 */             theNewString = String.valueOf(theNewString) + '\n';
/*       */           }
/*       */           else {
/*       */             
/* 11586 */             theNewString = String.valueOf(theNewString.substring(0, ispaceLocation)) + 
/* 11587 */               '\n' + theNewString.substring(ispaceLocation + 1, liCurrentLength + 1);
/*       */           } 
/*       */         } 
/*       */ 
/*       */         
/* 11592 */         charcount = 0;
/* 11593 */         canFit = 0;
/*       */       
/*       */       }
/*       */       else {
/*       */         
/* 11598 */         theNewString = String.valueOf(theNewString) + theString.charAt(x);
/* 11599 */         if (charcount > 3) {
/*       */           
/* 11601 */           theTempCharArray = theNewString.substring(theNewString.length() - charcount + 1, theNewString.length() - 1);
/* 11602 */           canFit = fm.stringWidth(theTempCharArray);
/* 11603 */           canFit = (int)(canFit * 2.4D);
/*       */         } 
/* 11605 */         charcount++;
/*       */       } 
/*       */     } 
/* 11608 */     if (g2 != null) g2.dispose(); 
/* 11609 */     if (g != null) g.dispose(); 
/* 11610 */     return theNewString;
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   public static int lineCountWCR(String[] strings, int[] maxChars) {
/* 11616 */     int lineCount = 0;
/* 11617 */     int stringLength = 0;
/* 11618 */     int charcount = 0;
/*       */     
/* 11620 */     for (int i = 0; i < strings.length; i++) {
/*       */       
/* 11622 */       int lines = 0;
/*       */       
/* 11624 */       String theString = strings[i];
/* 11625 */       int lineLength = maxChars[i];
/*       */       
/* 11627 */       stringLength = theString.length();
/*       */ 
/*       */       
/* 11630 */       for (int x = 0; x < stringLength; x++) {
/*       */         
/* 11632 */         if ((theString.charAt(x) == '\n' && stringLength - 1 != x) || charcount >= lineLength) {
/*       */           
/* 11634 */           charcount = 0;
/* 11635 */           lines++;
/*       */         } else {
/*       */           
/* 11638 */           charcount++;
/*       */         } 
/*       */       } 
/* 11641 */       if (lines > lineCount) {
/* 11642 */         lineCount = lines;
/*       */       }
/*       */     } 
/*       */     
/* 11646 */     return lineCount;
/*       */   }
/*       */ 
/*       */   
/*       */   public static String stringFormat(String strings, int maxChars) {
/* 11651 */     int lineCount = 0;
/* 11652 */     int stringLength = 0;
/* 11653 */     int charcount = 0;
/* 11654 */     int lines = 0;
/* 11655 */     int lineLength = maxChars;
/* 11656 */     String theString = strings;
/* 11657 */     String theNewString = "";
/* 11658 */     char lcSpaceCheck = '\n';
/* 11659 */     int i = 0;
/* 11660 */     int ispaceLocation = 0;
/*       */     
/* 11662 */     stringLength = theString.length();
/* 11663 */     for (int x = 0; x < stringLength; x++) {
/*       */       
/* 11665 */       i = 0;
/* 11666 */       if (theString.charAt(x) == '\n' || charcount >= maxChars) {
/*       */         
/* 11668 */         if (theString.charAt(x) == '\n') {
/* 11669 */           theNewString = String.valueOf(theNewString) + theString.charAt(x);
/* 11670 */         } else if (theString.charAt(x) == ' ') {
/* 11671 */           theNewString = String.valueOf(theNewString) + '\n';
/*       */         } else {
/*       */           
/* 11674 */           theNewString = String.valueOf(theNewString) + theString.charAt(x);
/* 11675 */           charcount++;
/* 11676 */           int liCurrentLength = theNewString.length() - 1;
/* 11677 */           i = charcount;
/*       */           
/* 11679 */           while (i > 1 && lcSpaceCheck != ' ') {
/*       */             
/* 11681 */             ispaceLocation = liCurrentLength - charcount - i;
/* 11682 */             lcSpaceCheck = theNewString.charAt(ispaceLocation);
/* 11683 */             i--;
/*       */           } 
/* 11685 */           lcSpaceCheck = '@';
/*       */           
/* 11687 */           if (i <= 1) {
/* 11688 */             theNewString = String.valueOf(theNewString) + '\n';
/*       */           } else {
/*       */             
/* 11691 */             theNewString = String.valueOf(theNewString.substring(0, ispaceLocation)) + '\n' + theNewString.substring(ispaceLocation + 1, liCurrentLength + 1);
/*       */           } 
/*       */         } 
/* 11694 */         charcount = 0;
/*       */       }
/*       */       else {
/*       */         
/* 11698 */         theNewString = String.valueOf(theNewString) + theString.charAt(x);
/* 11699 */         charcount++;
/*       */       } 
/*       */     } 
/* 11702 */     return theNewString;
/*       */   }
/*       */ 
/*       */   
/*       */   public static void updateFamilyForAllSelections() {
/* 11707 */     query = "SELECT * from vi_Release_Header with (nolock)";
/*       */     
/* 11709 */     JdbcConnector connector = createConnector(query);
/* 11710 */     connector.runQuery();
/*       */     
/* 11712 */     while (connector.more()) {
/*       */ 
/*       */       
/*       */       try {
/* 11716 */         Company company = (Company)getStructureObject(connector.getIntegerField("company_id"));
/* 11717 */         String familyID = String.valueOf(company.getParentID());
/*       */         
/* 11719 */         String queryUpdate = "UPDATE vi_Release_Header set family_id = " + familyID + " where release_id = " + connector.getIntegerField("release_id");
/*       */         
/* 11721 */         JdbcConnector connectorUpdate = createConnector(queryUpdate);
/* 11722 */         connectorUpdate.runQuery();
/* 11723 */         connectorUpdate.close();
/*       */       }
/* 11725 */       catch (Exception exception) {}
/*       */ 
/*       */       
/* 11728 */       connector.next();
/*       */     } 
/* 11730 */     connector.close();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/* 11739 */   public static Vector getUserEnvironments(int userID) { return getEnvironmentsFromCompanies(getUserCompanies(userID)); }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Vector sortUserEnvironmentsByFamily(Vector environments) {
/* 11749 */     Vector userEnvironmentsByFamily = new Vector();
/*       */ 
/*       */     
/* 11752 */     Object[] environmentArray = environments.toArray();
/*       */ 
/*       */     
/* 11755 */     Arrays.sort(environmentArray, new CorpStructNameComparator());
/*       */ 
/*       */     
/* 11758 */     Arrays.sort(environmentArray, new CorpStructParentNameComparator());
/*       */     
/* 11760 */     for (int j = 0; j < environmentArray.length; j++) {
/* 11761 */       userEnvironmentsByFamily.add(environmentArray[j]);
/*       */     }
/*       */     
/* 11764 */     return userEnvironmentsByFamily;
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   public static Vector buildAssignedEnvironments(Vector environments) {
/* 11770 */     for (int i = 0; i < environments.size(); i++) {
/*       */ 
/*       */       
/* 11773 */       Environment env = (Environment)environments.elementAt(i);
/* 11774 */       if (env != null) {
/*       */         
/* 11776 */         Family family = env.getParentFamily();
/* 11777 */         if (family != null) {
/*       */           
/* 11779 */           Vector famEnvs = family.getEnvironments();
/* 11780 */           if (famEnvs != null)
/*       */           {
/* 11782 */             for (int x = 0; x < famEnvs.size(); x++) {
/*       */               
/* 11784 */               Environment famEnv = (Environment)famEnvs.elementAt(x);
/* 11785 */               if (famEnv != null)
/*       */               {
/* 11787 */                 if (!environments.contains(famEnv)) {
/* 11788 */                   environments.add(famEnv);
/*       */                 }
/*       */               }
/*       */             } 
/*       */           }
/*       */         } 
/*       */       } 
/*       */     } 
/*       */     
/* 11797 */     return sortUserEnvironmentsByFamily(environments);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Vector getEnvironmentsFromCompanies(Vector companyVector) {
/* 11808 */     Vector environmentVector = new Vector();
/*       */ 
/*       */ 
/*       */     
/* 11812 */     for (int i = 0; i < companyVector.size(); i++) {
/* 11813 */       Company currentCompany = (Company)companyVector.elementAt(i);
/* 11814 */       Environment currentEnvironment = currentCompany.getParentEnvironment();
/*       */ 
/*       */       
/* 11817 */       if (currentEnvironment != null)
/*       */       {
/*       */         
/* 11820 */         if (!environmentVector.contains(currentEnvironment)) {
/* 11821 */           environmentVector.add(currentEnvironment);
/*       */         }
/*       */       }
/*       */     } 
/*       */     
/* 11826 */     Object[] EnvironmentArray = environmentVector.toArray();
/* 11827 */     Arrays.sort(EnvironmentArray, new CorpStructNameComparator());
/*       */     
/* 11829 */     Vector sortedEnvironmentVector = new Vector();
/* 11830 */     for (int j = 0; j < EnvironmentArray.length; j++) {
/* 11831 */       sortedEnvironmentVector.add(EnvironmentArray[j]);
/*       */     }
/*       */     
/* 11834 */     return sortedEnvironmentVector;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Vector getUserEnvironmentsExcludeUmlEnterprise(int userID) {
/* 11888 */     String envQuery = "sp_get_User_Environments_Exclude " + userID;
/* 11889 */     Vector precache = new Vector();
/* 11890 */     Environment env = null;
/*       */     
/* 11892 */     JdbcConnector envConnector = createConnector(envQuery);
/* 11893 */     envConnector.runQuery();
/*       */ 
/*       */     
/* 11896 */     while (envConnector.more()) {
/*       */ 
/*       */       
/* 11899 */       env = (Environment)getStructureObject(envConnector.getIntegerField("structure_id"));
/*       */       
/* 11901 */       if (env != null)
/* 11902 */         precache.addElement(env); 
/* 11903 */       env = null;
/* 11904 */       envConnector.next();
/*       */     } 
/*       */     
/* 11907 */     return precache;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Environment getScreenUserEnvironment(Context context) {
/* 11924 */     Environment env = null;
/* 11925 */     int envID = -1;
/* 11926 */     Notepad notepad = getNotepadFromSession(21, context);
/* 11927 */     if (context.getRequestValue("environment-id") != null) {
/*       */ 
/*       */       
/*       */       try {
/* 11931 */         envID = Integer.parseInt(context.getRequestValue("environment-id"));
/* 11932 */         env = (Environment)getStructureObject(envID);
/* 11933 */         context.putSessionValue("UserEnvironment", env);
/* 11934 */         notepad.setSelected(env);
/*       */       }
/* 11936 */       catch (ClassCastException classCastException) {}
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     }
/* 11946 */     else if ((Environment)notepad.getSelected() != null) {
/*       */ 
/*       */       
/*       */       try {
/* 11950 */         Environment notepadEnvironment = (Environment)notepad.getSelected();
/* 11951 */         envID = notepadEnvironment.getStructureID();
/* 11952 */         env = (Environment)getStructureObject(envID);
/* 11953 */         context.putSessionValue("UserEnvironment", env);
/*       */       }
/* 11955 */       catch (ClassCastException classCastException) {}
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     }
/* 11963 */     else if (notepad.getAllContents() != null && notepad.getAllContents().size() > 0) {
/*       */       
/* 11965 */       env = (Environment)notepad.getAllContents().get(0);
/* 11966 */       context.putSessionValue("UserEnvironment", env);
/* 11967 */       notepad.setSelected(env);
/*       */     } 
/*       */ 
/*       */ 
/*       */     
/* 11972 */     return env;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Environment getScreenEnvironment(Context context) {
/* 11990 */     Environment env = null;
/* 11991 */     int envID = -1;
/* 11992 */     Notepad notepad = getNotepadFromSession(21, context);
/*       */     
/* 11994 */     if (context.getRequestValue("environment-id") != null) {
/*       */       
/* 11996 */       envID = Integer.parseInt(context.getRequestValue("environment-id"));
/*       */ 
/*       */ 
/*       */       
/* 12000 */       env = EnvironmentManager.getInstance().getEnvironment(envID);
/* 12001 */       context.putSessionValue("Environment", env);
/* 12002 */       notepad.setSelected(env);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     }
/* 12009 */     else if ((Environment)notepad.getSelected() != null) {
/*       */       
/* 12011 */       env = (Environment)notepad.getSelected();
/*       */ 
/*       */       
/* 12014 */       env = (Environment)getStructureObject(env.getStructureID());
/*       */ 
/*       */ 
/*       */     
/*       */     }
/* 12019 */     else if (notepad.getAllContents() != null && notepad.getAllContents().size() > 0) {
/*       */       
/* 12021 */       env = (Environment)notepad.getAllContents().get(0);
/* 12022 */       notepad.setSelected(env);
/* 12023 */       context.putSessionValue("Environment", env);
/*       */     } 
/*       */ 
/*       */ 
/*       */     
/* 12028 */     return env;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static CompanyAcl getScreenPermissions(Environment env, User user) {
/* 12046 */     Hashtable envCompanies = new Hashtable();
/*       */ 
/*       */     
/* 12049 */     if (env == null) {
/* 12050 */       return null;
/*       */     }
/* 12052 */     Vector companies = env.getCompanies();
/* 12053 */     if (companies == null) {
/* 12054 */       return null;
/*       */     }
/* 12056 */     for (int i = 0; i < companies.size(); i++) {
/*       */       
/* 12058 */       Company company = (Company)companies.get(i);
/* 12059 */       if (company != null) {
/* 12060 */         envCompanies.put(Integer.toString(company.getStructureID()), company.getName());
/*       */       }
/*       */     } 
/*       */ 
/*       */     
/* 12065 */     CompanyAcl companyAcl = null;
/*       */     
/* 12067 */     Acl acl = user.getAcl();
/* 12068 */     if (acl == null)
/*       */     {
/*       */       
/* 12071 */       return null;
/*       */     }
/*       */     
/* 12074 */     Vector companyAcls = acl.getCompanyAcl();
/* 12075 */     if (companyAcls == null)
/*       */     {
/*       */       
/* 12078 */       return null;
/*       */     }
/*       */     
/* 12081 */     int envId = -1;
/*       */ 
/*       */     
/* 12084 */     if (env != null) {
/*       */       
/* 12086 */       envId = env.getStructureID();
/*       */       
/* 12088 */       for (int i = 0; i < companyAcls.size(); i++) {
/*       */         
/* 12090 */         companyAcl = (CompanyAcl)companyAcls.get(i);
/* 12091 */         if (companyAcl != null)
/*       */         {
/*       */ 
/*       */ 
/*       */           
/* 12096 */           if (envCompanies.containsKey(Integer.toString(companyAcl.getCompanyId())))
/*       */           {
/*       */             
/* 12099 */             return companyAcl;
/*       */           }
/*       */         }
/*       */       } 
/*       */     } 
/*       */     
/* 12105 */     return null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static String getOwnerEnvironmentWhereClause(Context context) {
/* 12121 */     Vector envs = getUserEnvironments(context);
/* 12122 */     Environment env = null;
/*       */     
/* 12124 */     StringList list = new StringList(" OR ");
/*       */ 
/*       */     
/* 12127 */     for (int i = 0; i < envs.size(); i++) {
/*       */       
/* 12129 */       env = (Environment)envs.elementAt(i);
/* 12130 */       if (env != null && env.getParentFamily() != null)
/*       */       {
/* 12132 */         list.add("owner = " + env.getParentFamily().getStructureID());
/*       */       }
/*       */     } 
/*       */     
/* 12136 */     String whereClause = list.toString();
/*       */     
/* 12138 */     if (whereClause.length() > 1) {
/* 12139 */       whereClause = " WHERE (" + whereClause + ")";
/*       */     }
/*       */     else {
/*       */       
/* 12143 */       whereClause = " WHERE (owner = -1)";
/*       */     } 
/*       */ 
/*       */ 
/*       */     
/* 12148 */     return whereClause;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Environment getEnvironmentById(int id) {
/* 12162 */     Vector envs = Cache.getInstance().getEnvironments();
/* 12163 */     if (envs != null)
/*       */     {
/* 12165 */       for (int i = 0; i < envs.size(); i++) {
/*       */         
/* 12167 */         Environment envTemp = (Environment)envs.elementAt(i);
/* 12168 */         if (envTemp != null && envTemp.getIdentity() == id) {
/* 12169 */           return envTemp;
/*       */         }
/*       */       } 
/*       */     }
/* 12173 */     return null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Vector getUserEnvironments(Context context) {
/* 12336 */     User user = (User)context.getSessionValue("user");
/* 12337 */     int userId = user.getUserId();
/*       */ 
/*       */ 
/*       */     
/* 12341 */     if (context.getSessionValue("user-environments") != null && !((Vector)context.getSessionValue("user-environments")).isEmpty())
/*       */     {
/*       */ 
/*       */       
/* 12345 */       return (Vector)context.getSessionValue("user-environments");
/*       */     }
/*       */ 
/*       */ 
/*       */     
/* 12350 */     Vector userEnvironments = getUserEnvironments(userId);
/*       */     
/* 12352 */     if (userEnvironments != null) {
/* 12353 */       context.putSessionValue("user-environments", userEnvironments);
/*       */     }
/* 12355 */     return userEnvironments;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Vector getUserEnvironmentsExcludeUmlEnterprise(Context context) {
/* 12372 */     User user = (User)context.getSessionValue("user");
/* 12373 */     int userId = user.getUserId();
/*       */     
/* 12375 */     return getUserEnvironmentsExcludeUmlEnterprise(userId);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static FormDropDownMenu getReleasingFamilyDropDown(String name, String selectedOption, Vector relList, boolean required, Selection selection) {
/* 12532 */     Vector values = new Vector();
/* 12533 */     Vector menuText = new Vector();
/*       */ 
/*       */ 
/*       */ 
/*       */     
/* 12538 */     FormDropDownMenu dropDown = null;
/*       */     
/* 12540 */     if (selectedOption == null) {
/* 12541 */       selectedOption = "";
/*       */     }
/* 12543 */     if (relList != null) {
/*       */       
/* 12545 */       boolean foundMatch = false;
/*       */       
/* 12547 */       for (int i = 0; i < relList.size(); i++) {
/*       */         
/* 12549 */         ReleasingFamily rFamily = (ReleasingFamily)relList.elementAt(i);
/*       */         
/* 12551 */         if (rFamily != null) {
/*       */           
/* 12553 */           values.addElement(Integer.toString(rFamily.getReleasingFamilyId()));
/* 12554 */           menuText.addElement(rFamily.getFamillyName());
/*       */           
/* 12556 */           if (selectedOption.equalsIgnoreCase(Integer.toString(rFamily.getReleasingFamilyId()))) {
/* 12557 */             foundMatch = true;
/*       */           }
/*       */         } 
/*       */       } 
/* 12561 */       if (!foundMatch) {
/*       */         
/* 12563 */         values.addElement(String.valueOf(selection.getReleaseFamilyId()));
/* 12564 */         menuText.addElement(ReleasingFamily.getName(selection.getReleaseFamilyId()));
/*       */       } 
/*       */       
/* 12567 */       String[] arrayValues = new String[values.size()];
/* 12568 */       String[] arrayMenuText = new String[menuText.size()];
/*       */ 
/*       */       
/* 12571 */       arrayValues = (String[])values.toArray(arrayValues);
/* 12572 */       arrayMenuText = (String[])menuText.toArray(arrayMenuText);
/*       */       
/* 12574 */       dropDown = new FormDropDownMenu(name, 
/* 12575 */           selectedOption, 
/* 12576 */           arrayValues, 
/* 12577 */           arrayMenuText, 
/* 12578 */           required);
/*       */     
/*       */     }
/*       */     else {
/*       */       
/* 12583 */       values.addElement(String.valueOf(selection.getReleaseFamilyId()));
/* 12584 */       menuText.addElement(ReleasingFamily.getName(selection.getReleaseFamilyId()));
/*       */       
/* 12586 */       String[] arrayValues = new String[values.size()];
/* 12587 */       String[] arrayMenuText = new String[menuText.size()];
/*       */ 
/*       */       
/* 12590 */       arrayValues = (String[])values.toArray(arrayValues);
/* 12591 */       arrayMenuText = (String[])menuText.toArray(arrayMenuText);
/*       */       
/* 12593 */       dropDown = new FormDropDownMenu(name, 
/* 12594 */           selectedOption, 
/* 12595 */           arrayValues, 
/* 12596 */           arrayMenuText, 
/* 12597 */           required);
/*       */     } 
/*       */     
/* 12600 */     return dropDown;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Vector removeUnusedCSO(Vector cso, Context context, int thisReleasingFamilyOnlyId) {
/* 12610 */     Vector csoClone = (Vector)cso.clone();
/* 12611 */     Hashtable userRelFamiliesHash = null;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/* 12617 */     User user = (User)context.getSessionValue("user");
/*       */ 
/*       */     
/* 12620 */     if (user != null) {
/* 12621 */       userRelFamiliesHash = user.getReleasingFamily();
/*       */     }
/* 12623 */     if (userRelFamiliesHash == null) {
/* 12624 */       userRelFamiliesHash = ReleasingFamily.get(user.getUserId());
/*       */     }
/*       */     
/* 12627 */     Hashtable productRelFamiliesHash = Cache.getProductReleasingFamilies();
/*       */ 
/*       */ 
/*       */     
/* 12631 */     CorporateStructureObject currentObject = null;
/* 12632 */     for (int i = csoClone.size() - 1; i >= 0; i--) {
/*       */       
/* 12634 */       currentObject = (CorporateStructureObject)csoClone.get(i);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/* 12641 */       boolean isUserRelFamEqualToProdRelFam = false;
/* 12642 */       boolean isFamilyObj = false;
/*       */       
/* 12644 */       if (productRelFamiliesHash != null && userRelFamiliesHash != null) {
/*       */ 
/*       */ 
/*       */         
/* 12648 */         String csoLabelFamilyStr = "";
/* 12649 */         int csoLabelFamilyId = -1;
/* 12650 */         if (currentObject instanceof Label)
/* 12651 */           csoLabelFamilyStr = Integer.toString(currentObject.getParent().getParent().getParent().getParent().getStructureID()); 
/* 12652 */         if (currentObject instanceof Division)
/* 12653 */           csoLabelFamilyStr = Integer.toString(currentObject.getParent().getParent().getParent().getStructureID()); 
/* 12654 */         if (currentObject instanceof Company)
/* 12655 */           csoLabelFamilyStr = Integer.toString(currentObject.getParent().getParent().getStructureID()); 
/* 12656 */         if (currentObject instanceof Environment)
/* 12657 */           csoLabelFamilyStr = Integer.toString(currentObject.getParent().getStructureID()); 
/* 12658 */         if (currentObject instanceof Family) {
/*       */           
/* 12660 */           isFamilyObj = true;
/* 12661 */           csoLabelFamilyStr = Integer.toString(currentObject.getStructureID());
/*       */         } 
/*       */ 
/*       */         
/* 12665 */         csoLabelFamilyId = Integer.parseInt(csoLabelFamilyStr);
/*       */ 
/*       */         
/* 12668 */         Vector vProdRelFamilies = (Vector)productRelFamiliesHash.get(Integer.toString(currentObject.getStructureID()));
/* 12669 */         if (vProdRelFamilies != null && vProdRelFamilies.size() > 0)
/*       */         {
/* 12671 */           for (int r = 0; r < vProdRelFamilies.size(); r++)
/*       */           {
/*       */             
/* 12674 */             int prodRelFamilyId = Integer.parseInt((String)vProdRelFamilies.get(r));
/*       */             
/* 12676 */             boolean containsKey = false;
/*       */ 
/*       */             
/* 12679 */             if (userRelFamiliesHash.containsKey(csoLabelFamilyStr)) {
/*       */               
/* 12681 */               Vector vUserRelFamilies = (Vector)userRelFamiliesHash.get(csoLabelFamilyStr);
/* 12682 */               if (vUserRelFamilies != null) {
/*       */                 
/* 12684 */                 containsKey = true;
/* 12685 */                 for (int x = 0; x < vUserRelFamilies.size(); x++) {
/*       */                   
/* 12687 */                   ReleasingFamily relFamily = (ReleasingFamily)vUserRelFamilies.get(x);
/* 12688 */                   if (relFamily != null)
/*       */                   {
/*       */ 
/*       */                     
/* 12692 */                     if (thisReleasingFamilyOnlyId == -1 || relFamily.getReleasingFamilyId() == thisReleasingFamilyOnlyId)
/*       */                     {
/*       */ 
/*       */                       
/* 12696 */                       if (relFamily.getReleasingFamilyId() == prodRelFamilyId) {
/* 12697 */                         isUserRelFamEqualToProdRelFam = true;
/*       */                         
/*       */                         break;
/*       */                       } 
/*       */                     }
/*       */                   }
/*       */                 } 
/*       */               } 
/*       */             } 
/* 12706 */             if (!isUserRelFamEqualToProdRelFam) {
/*       */               
/* 12708 */               if (isFamilyObj)
/*       */               {
/*       */                 
/* 12711 */                 if (checkUserRelFamilyByFamilyId(userRelFamiliesHash, csoLabelFamilyId, productRelFamiliesHash)) {
/* 12712 */                   isUserRelFamEqualToProdRelFam = true;
/*       */                 }
/*       */               }
/*       */ 
/*       */ 
/*       */               
/* 12718 */               if (!containsKey && prodRelFamilyId == csoLabelFamilyId) {
/* 12719 */                 isUserRelFamEqualToProdRelFam = true;
/*       */               }
/*       */             } 
/*       */ 
/*       */             
/* 12724 */             if (isUserRelFamEqualToProdRelFam) {
/*       */               break;
/*       */             }
/*       */           }
/*       */         
/*       */         }
/*       */       }
/*       */       else {
/*       */         
/* 12733 */         isUserRelFamEqualToProdRelFam = true;
/*       */       } 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/* 12739 */       if (!isUserRelFamEqualToProdRelFam)
/*       */       {
/* 12741 */         csoClone.remove(i);
/*       */       }
/*       */     } 
/*       */ 
/*       */     
/* 12746 */     return csoClone;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static boolean checkUserRelFamilyByFamilyId(Hashtable userRelFamiliesHash, int csoLabelFamilyId, Hashtable productRelFamiliesHash) {
/* 12760 */     boolean result = false;
/*       */ 
/*       */     
/* 12763 */     if (userRelFamiliesHash == null) {
/* 12764 */       return result;
/*       */     }
/* 12766 */     Hashtable userRelFamByValueHash = new Hashtable();
/*       */     
/* 12768 */     Enumeration userRelFamiliesEnum = userRelFamiliesHash.elements();
/* 12769 */     while (userRelFamiliesEnum.hasMoreElements()) {
/*       */       
/* 12771 */       Vector userReleasingFamilies = (Vector)userRelFamiliesEnum.nextElement();
/* 12772 */       if (userReleasingFamilies != null)
/*       */       {
/* 12774 */         for (int f = 0; f < userReleasingFamilies.size(); f++) {
/* 12775 */           ReleasingFamily relFamily = (ReleasingFamily)userReleasingFamilies.get(f);
/* 12776 */           if (relFamily != null && relFamily.getReleasingFamilyId() == csoLabelFamilyId) {
/*       */ 
/*       */             
/* 12779 */             int relLabelFamilyId = relFamily.getLabelFamilyId();
/*       */ 
/*       */             
/* 12782 */             Vector vProdRelFamilies = (Vector)productRelFamiliesHash.get(Integer.toString(relLabelFamilyId));
/* 12783 */             if (vProdRelFamilies != null && vProdRelFamilies.size() > 0) {
/* 12784 */               for (int r = 0; r < vProdRelFamilies.size(); r++) {
/*       */                 
/* 12786 */                 int prodRelFamilyId = Integer.parseInt((String)vProdRelFamilies.get(r));
/* 12787 */                 if (prodRelFamilyId == relLabelFamilyId) {
/* 12788 */                   return true;
/*       */                 }
/*       */               } 
/*       */             }
/*       */           } 
/*       */         } 
/*       */       }
/*       */     } 
/* 12796 */     return result;
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   public static CorporateStructureObject getCoporateStructure(int id) {
/* 12802 */     Vector sList = Cache.getCorporateStructure();
/* 12803 */     CorporateStructureObject currentObject = null;
/* 12804 */     for (int i = 0; i < sList.size(); i++) {
/*       */       
/* 12806 */       currentObject = (CorporateStructureObject)sList.get(i);
/*       */       
/* 12808 */       if (currentObject.getStructureID() == id)
/*       */         break; 
/*       */     } 
/* 12811 */     return currentObject;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Vector getConfigCodes(int type) {
/* 12822 */     Vector configCodes = Cache.getInstance().getConfigCodes();
/* 12823 */     Vector precache = new Vector();
/*       */     
/* 12825 */     for (int i = 0; i < configCodes.size(); i++) {
/*       */       
/* 12827 */       LookupObject luo = (LookupObject)configCodes.get(i);
/* 12828 */       if (luo.getProdType() == type || luo.getProdType() == 2) {
/* 12829 */         precache.add(luo);
/*       */       }
/*       */     } 
/* 12832 */     return precache;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Hashtable groupSelectionsForIDJAlternate(Vector selections) {
/* 12847 */     Hashtable groupedSelections = new Hashtable();
/* 12848 */     if (selections == null) {
/* 12849 */       return groupedSelections;
/*       */     }
/* 12851 */     for (int i = 0; i < selections.size(); i++) {
/*       */       
/* 12853 */       Selection sel = (Selection)selections.elementAt(i);
/* 12854 */       if (sel != null) {
/*       */         
/* 12856 */         sel = SelectionManager.getInstance().getSelectionAndSchedule(sel.getSelectionID());
/*       */         
/* 12858 */         String selArtist = sel.getFlArtist();
/* 12859 */         Vector group = (Vector)groupedSelections.get(selArtist);
/*       */ 
/*       */         
/* 12862 */         if (group == null) {
/*       */           
/* 12864 */           group = new Vector();
/* 12865 */           group.addElement(sel);
/* 12866 */           groupedSelections.put(selArtist, group);
/*       */         }
/*       */         else {
/*       */           
/* 12870 */           group.addElement(sel);
/*       */         } 
/*       */       } 
/*       */     } 
/* 12874 */     return groupedSelections;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Hashtable groupSelectionsForIDJAltByRelDate(Vector selections, boolean isTBS) {
/* 12889 */     Hashtable groupedSelections = new Hashtable();
/* 12890 */     if (selections == null) {
/* 12891 */       return groupedSelections;
/*       */     }
/* 12893 */     for (int i = 0; i < selections.size(); i++) {
/*       */       
/* 12895 */       Selection sel = (Selection)selections.elementAt(i);
/* 12896 */       if (sel != null) {
/*       */ 
/*       */         
/* 12899 */         sel = SelectionManager.getInstance().getSelectionAndSchedule(sel.getSelectionID());
/*       */ 
/*       */ 
/*       */         
/* 12903 */         String status = (sel.getSelectionStatus() != null && sel.getSelectionStatus().getName() != null) ? sel.getSelectionStatus().getName() : "";
/* 12904 */         if (!status.equalsIgnoreCase("TBS") || isTBS) {
/*       */ 
/*       */ 
/*       */ 
/*       */           
/* 12909 */           String dateString = "";
/* 12910 */           if (sel.getIsDigital()) {
/* 12911 */             dateString = (sel.getDigitalRlsDate() != null) ? getFormatedDate(sel.getDigitalRlsDate()) : "";
/*       */           } else {
/*       */             
/* 12914 */             dateString = (sel.getStreetDate() != null) ? getFormatedDate(sel.getStreetDate()) : "";
/*       */           } 
/*       */ 
/*       */           
/* 12918 */           if (dateString.equals("") && isTBS) {
/* 12919 */             dateString = "NoDate";
/*       */           }
/*       */           
/* 12922 */           Vector group = (Vector)groupedSelections.get(dateString);
/*       */           
/* 12924 */           if (group == null) {
/*       */             
/* 12926 */             group = new Vector();
/* 12927 */             group.addElement(sel);
/* 12928 */             groupedSelections.put(dateString, group);
/*       */           }
/*       */           else {
/*       */             
/* 12932 */             group.addElement(sel);
/*       */           } 
/*       */         } 
/*       */       } 
/* 12936 */     }  return groupedSelections;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public static Hashtable buildFinalIDJAlternateSelections(Vector selections) {
/* 12956 */     Hashtable finalSelections = new Hashtable();
/*       */ 
/*       */     
/* 12959 */     Hashtable selArtistSelections = groupSelectionsForIDJAlternate(selections);
/*       */     
/* 12961 */     Hashtable selFullLengths = groupSelectionsForIDJAltByRelDate(selections, false);
/*       */ 
/*       */     
/* 12964 */     Vector artistProcessed = new Vector();
/*       */ 
/*       */     
/* 12967 */     Enumeration fullLengthReleaseDatesEnum = selFullLengths.keys();
/* 12968 */     Vector fullLengthReleaseDateVector = new Vector();
/* 12969 */     while (fullLengthReleaseDatesEnum.hasMoreElements()) {
/* 12970 */       fullLengthReleaseDateVector.addElement(fullLengthReleaseDatesEnum.nextElement());
/*       */     }
/*       */     
/* 12973 */     Enumeration artistEnum = selArtistSelections.keys();
/* 12974 */     Vector artistVector = new Vector();
/* 12975 */     while (artistEnum.hasMoreElements()) {
/* 12976 */       artistVector.addElement(artistEnum.nextElement());
/*       */     }
/*       */     
/* 12979 */     Collections.sort(fullLengthReleaseDateVector, new DateConverterComparator());
/*       */ 
/*       */     
/* 12982 */     for (int fullLength = 0; fullLength < fullLengthReleaseDateVector.size(); fullLength++) {
/*       */       
/* 12984 */       String fullLengthDate = (String)fullLengthReleaseDateVector.elementAt(fullLength);
/*       */       
/* 12986 */       Vector selectionsForDate = (Vector)selFullLengths.get(fullLengthDate);
/*       */ 
/*       */       
/* 12989 */       for (int selectionCount = 0; selectionCount < selectionsForDate.size(); selectionCount++) {
/*       */         
/* 12991 */         Selection sel = (Selection)selectionsForDate.elementAt(selectionCount);
/* 12992 */         String artistName = sel.getFlArtist();
/*       */         
/* 12994 */         if (!artistProcessed.contains(artistName)) {
/*       */ 
/*       */           
/* 12997 */           artistProcessed.add(artistName);
/*       */ 
/*       */           
/* 13000 */           Vector artistsSelections = (Vector)selArtistSelections.get(artistName);
/*       */           
/* 13002 */           finalSelections.put(String.valueOf(fullLengthDate) + " * " + artistName, artistsSelections);
/*       */         } 
/*       */       } 
/*       */     } 
/*       */     
/* 13007 */     Vector exceptionsVector = new Vector();
/*       */ 
/*       */ 
/*       */     
/* 13011 */     for (int artistCounter = 0; artistCounter < artistVector.size(); artistCounter++) {
/* 13012 */       String artistName = (String)artistVector.elementAt(artistCounter);
/* 13013 */       if (!artistProcessed.contains(artistName)) {
/*       */ 
/*       */ 
/*       */         
/* 13017 */         Vector artistsSelections = (Vector)selArtistSelections.get(artistName);
/* 13018 */         for (int artistSelectionCount = 0; artistSelectionCount < artistsSelections.size(); artistSelectionCount++) {
/* 13019 */           exceptionsVector.add((Selection)artistsSelections.elementAt(artistSelectionCount));
/*       */         }
/* 13021 */         artistProcessed.add(artistName);
/*       */       } 
/*       */     } 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/* 13029 */     Hashtable exceptionsHash = new Hashtable();
/* 13030 */     Vector exceptionsArtists = new Vector();
/* 13031 */     Vector totalExceptions = new Vector();
/* 13032 */     String earliestDate = "";
/*       */ 
/*       */     
/* 13035 */     Hashtable selExceptionArtistSelections = groupSelectionsForIDJAlternate(exceptionsVector);
/*       */     
/* 13037 */     Hashtable selExceptionsStreetDates = groupSelectionsForIDJAltByRelDate(exceptionsVector, true);
/*       */ 
/*       */     
/* 13040 */     Vector exceptionsartistProcessed = new Vector();
/*       */ 
/*       */     
/* 13043 */     Enumeration TBSReleaseDatesEnum = selExceptionsStreetDates.keys();
/* 13044 */     Vector TBSReleaseDateVector = new Vector();
/* 13045 */     while (TBSReleaseDatesEnum.hasMoreElements()) {
/* 13046 */       TBSReleaseDateVector.addElement(TBSReleaseDatesEnum.nextElement());
/*       */     }
/*       */     
/* 13049 */     Enumeration TBSartistEnum = selExceptionArtistSelections.keys();
/* 13050 */     Vector TBSartistVector = new Vector();
/* 13051 */     while (TBSartistEnum.hasMoreElements()) {
/* 13052 */       TBSartistVector.addElement(TBSartistEnum.nextElement());
/*       */     }
/*       */     
/* 13055 */     Collections.sort(TBSReleaseDateVector, new DateConverterComparator());
/*       */ 
/*       */     
/* 13058 */     for (int TBSDateCounter = 0; TBSDateCounter < TBSReleaseDateVector.size(); TBSDateCounter++) {
/*       */       
/* 13060 */       String TBSDate = (String)TBSReleaseDateVector.elementAt(TBSDateCounter);
/*       */       
/* 13062 */       Vector selectionsForDate = (Vector)selExceptionsStreetDates.get(TBSDate);
/*       */ 
/*       */       
/* 13065 */       for (int selectionCount = 0; selectionCount < selectionsForDate.size(); selectionCount++) {
/*       */         
/* 13067 */         Selection sel = (Selection)selectionsForDate.elementAt(selectionCount);
/* 13068 */         String artistName = sel.getFlArtist();
/*       */         
/* 13070 */         if (!exceptionsartistProcessed.contains(artistName)) {
/*       */ 
/*       */           
/* 13073 */           exceptionsartistProcessed.add(artistName);
/*       */ 
/*       */           
/* 13076 */           Vector artistsSelections = (Vector)selExceptionArtistSelections.get(artistName);
/*       */           
/* 13078 */           exceptionsHash.put("TBS * " + TBSDate + " * " + artistName, artistsSelections);
/*       */         } 
/*       */       } 
/*       */     } 
/*       */     
/* 13083 */     Hashtable finalhashtable2 = new Hashtable();
/*       */     
/* 13085 */     finalhashtable2.put("TBS", exceptionsHash);
/* 13086 */     finalhashtable2.put("Product", finalSelections);
/*       */     
/* 13088 */     return finalhashtable2;
/*       */   }
/*       */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\MilestoneHelper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */