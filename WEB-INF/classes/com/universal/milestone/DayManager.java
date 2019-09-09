/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.EnhancedProperties;
/*     */ import com.techempower.gemini.Context;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.Day;
/*     */ import com.universal.milestone.DayManager;
/*     */ import com.universal.milestone.JdbcConnector;
/*     */ import com.universal.milestone.MilestoneConstants;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.Notepad;
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
/*     */ public class DayManager
/*     */   implements MilestoneConstants
/*     */ {
/*     */   public static final String COMPONENT_CODE = "mDay";
/*     */   public static final String DEFAULT_QUERY = "SELECT [grouping], [date], [value], [type_id]  FROM vi_Day_Type";
/*     */   public static final String DEFAULT_ORDER = " ORDER BY grouping DESC";
/*  44 */   protected static DayManager dayManager = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static ComponentLog log;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getLog("mDay"); }
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
/*     */   public static DayManager getInstance() {
/*  74 */     if (dayManager == null)
/*     */     {
/*  76 */       dayManager = new DayManager();
/*     */     }
/*  78 */     return dayManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Day getDay(int id, boolean getTimestamp) {
/*  86 */     String dayQuery = "SELECT [grouping], [date], [value], [type_id], [description], [entered_by], [last_updated_by], [last_updated_on], [last_updated_ck] FROM vi_Day_Type WHERE type_id = " + 
/*     */ 
/*     */ 
/*     */       
/*  90 */       id + 
/*  91 */       ";";
/*     */     
/*  93 */     Day day = null;
/*     */     
/*  95 */     JdbcConnector connector = MilestoneHelper.getConnector(dayQuery);
/*  96 */     connector.runQuery();
/*     */     
/*  98 */     if (connector.more()) {
/*     */       
/* 100 */       day = new Day(connector.getIntegerField("type_id"));
/*     */ 
/*     */       
/* 103 */       day.setDescription(connector.getField("description", ""));
/*     */ 
/*     */       
/* 106 */       day.setCalendarGroup(connector.getIntegerField("grouping"));
/*     */ 
/*     */       
/* 109 */       String dateString = connector.getField("date", "");
/* 110 */       if (dateString.length() > 0) {
/* 111 */         day.setSpecificDate(MilestoneHelper.getDatabaseDate(dateString));
/*     */       }
/*     */       
/* 114 */       day.setDayType(connector.getField("value", ""));
/*     */ 
/*     */       
/* 117 */       String lastDateString = connector.getFieldByName("last_updated_on");
/* 118 */       if (lastDateString != null) {
/* 119 */         day.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastDateString));
/*     */       }
/*     */       
/* 122 */       day.setLastUpdatingUser(connector.getIntegerField("last_updated_by"));
/*     */ 
/*     */       
/* 125 */       if (getTimestamp) {
/*     */         
/* 127 */         long lastUpdatedLong = Long.parseLong(connector.getField("last_updated_ck"), 16);
/* 128 */         day.setLastUpdatedCk(lastUpdatedLong);
/*     */       } 
/*     */     } 
/*     */     
/* 132 */     connector.close();
/*     */     
/* 134 */     return day;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Day getNotepadDay(JdbcConnector connector) {
/* 144 */     Day day = null;
/*     */     
/* 146 */     if (connector != null) {
/*     */       
/* 148 */       day = new Day(connector.getIntegerField("type_id"));
/*     */ 
/*     */       
/* 151 */       day.setCalendarGroup(connector.getIntegerField("grouping"));
/*     */ 
/*     */       
/* 154 */       String dateString = connector.getField("date", "");
/* 155 */       if (dateString.length() > 0) {
/* 156 */         day.setSpecificDate(MilestoneHelper.getDatabaseDate(dateString));
/*     */       }
/*     */       
/* 159 */       day.setDayType(connector.getField("value", ""));
/*     */     } 
/*     */     
/* 162 */     return day;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Day saveDay(Day day, int userID) {
/* 170 */     long timestamp = day.getLastUpdatedCk();
/*     */     
/* 172 */     String query = "sp_sav_Day_Type " + 
/* 173 */       day.getDayID() + "," + 
/* 174 */       "'" + MilestoneHelper.escapeSingleQuotes(day.getDayType()) + "'," + 
/* 175 */       "'" + MilestoneHelper.escapeSingleQuotes(day.getDescription()) + "'," + 
/* 176 */       "'" + MilestoneHelper.getFormatedDate(day.getSpecificDate()) + "'," + 
/* 177 */       day.getCalendarGroup() + "," + 
/* 178 */       userID + "," + 
/* 179 */       timestamp + 
/* 180 */       ";";
/*     */     
/* 182 */     log.debug("Day save query:\n" + query);
/* 183 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 184 */     connector.runQuery();
/*     */     
/* 186 */     if (connector.getIntegerField("ReturnId") > 0) {
/* 187 */       day.setDayID(connector.getIntegerField("ReturnId"));
/*     */     }
/* 189 */     connector.close();
/*     */     
/* 191 */     String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM vi_Day_Type WHERE type_id = " + 
/*     */       
/* 193 */       day.getDayID() + 
/* 194 */       ";";
/*     */ 
/*     */     
/* 197 */     day.flushAudits(userID);
/*     */     
/* 199 */     JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
/* 200 */     connectorTimestamp.runQuery();
/* 201 */     if (connectorTimestamp.more()) {
/*     */       
/* 203 */       day.setLastUpdatedCk(Long.parseLong(connectorTimestamp.getField("last_updated_ck"), 16));
/*     */       
/* 205 */       String lastUpdatedString = connectorTimestamp.getField("last_updated_on", "");
/* 206 */       if (lastUpdatedString.length() > 0)
/* 207 */         day.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastUpdatedString)); 
/*     */     } 
/* 209 */     connectorTimestamp.close();
/*     */     
/* 211 */     return day;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Day saveNewDayType(Day day, int userID) {
/* 220 */     day.flushAudits(userID);
/*     */     
/* 222 */     String query = "sp_sav_Day_Type -1,'" + 
/*     */       
/* 224 */       MilestoneHelper.escapeSingleQuotes(day.getDayType()) + "'," + 
/* 225 */       "'" + MilestoneHelper.escapeSingleQuotes(day.getDescription()) + "'," + 
/* 226 */       "'" + MilestoneHelper.getFormatedDate(day.getSpecificDate()) + "'," + 
/* 227 */       day.getCalendarGroup() + "," + 
/* 228 */       userID + "," + 
/* 229 */       -1;
/*     */     
/* 231 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 232 */     connector.runQuery();
/* 233 */     day.setDayID(connector.getIntegerField("returnId"));
/* 234 */     connector.close();
/* 235 */     return day;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleteDay(Day day, int userID) {
/* 243 */     String query = "sp_del_Day_Type " + 
/* 244 */       day.getDayID();
/*     */     
/* 246 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 247 */     connector.runQuery();
/* 248 */     connector.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNotepadQuery(Context context, int UserId, Notepad notepad) {
/* 259 */     if (notepad != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 264 */       String groupSearch = context.getParameter("GroupSearch");
/* 265 */       String specialDateSearch = context.getParameter("SpecialDateSearch");
/* 266 */       String dayTypeSearch = context.getParameter("DayTypeSearch");
/* 267 */       String descriptionSearch = context.getParameter("DescriptionSearch");
/*     */ 
/*     */       
/* 270 */       StringBuffer dayQuery = new StringBuffer(
/* 271 */           "SELECT [grouping], [date], [value], [type_id]  FROM vi_Day_Type");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 278 */       if (MilestoneHelper.isStringNotEmpty(groupSearch) && !groupSearch.equals("0")) {
/* 279 */         dayQuery.append(MilestoneHelper.addQueryParams(dayQuery.toString(), " grouping like '%" + groupSearch + "%'"));
/*     */       }
/* 281 */       if (MilestoneHelper.isStringNotEmpty(specialDateSearch)) {
/* 282 */         dayQuery.append(MilestoneHelper.addQueryParams(dayQuery.toString(), " date = '" + MilestoneHelper.escapeSingleQuotes(specialDateSearch) + "'"));
/*     */       }
/* 284 */       if (MilestoneHelper.isStringNotEmpty(dayTypeSearch)) {
/* 285 */         dayQuery.append(MilestoneHelper.addQueryParams(dayQuery.toString(), " value " + MilestoneHelper.setWildCardsEscapeSingleQuotes(dayTypeSearch)));
/*     */       }
/* 287 */       if (MilestoneHelper.isStringNotEmpty(descriptionSearch)) {
/* 288 */         dayQuery.append(MilestoneHelper.addQueryParams(dayQuery.toString(), " description " + MilestoneHelper.setWildCardsEscapeSingleQuotes(descriptionSearch)));
/*     */       }
/* 290 */       String order = " ORDER BY grouping";
/*     */       
/* 292 */       notepad.setSearchQuery(dayQuery.toString());
/* 293 */       notepad.setOrderBy(order);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector getDayNotepadList(int UserId, Notepad notepad) {
/* 299 */     String dayQuery = "";
/*     */     
/* 301 */     if (notepad != null && !notepad.getSearchQuery().equals("")) {
/*     */       
/* 303 */       dayQuery = notepad.getSearchQuery();
/* 304 */       dayQuery = String.valueOf(dayQuery) + notepad.getOrderBy();
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 309 */       dayQuery = "SELECT [grouping], [date], [value], [type_id]  FROM vi_Day_Type ORDER BY grouping DESC";
/*     */     } 
/*     */ 
/*     */     
/* 313 */     Day day = null;
/* 314 */     Vector precache = new Vector();
/*     */     
/* 316 */     JdbcConnector connector = MilestoneHelper.getConnector(dayQuery);
/* 317 */     connector.runQuery();
/*     */     
/* 319 */     while (connector.more()) {
/*     */       
/* 321 */       day = getNotepadDay(connector);
/*     */       
/* 323 */       precache.addElement(day);
/* 324 */       day = null;
/* 325 */       connector.next();
/*     */     } 
/* 327 */     connector.close();
/*     */     
/* 329 */     return precache;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTimestampValid(Day day) {
/* 339 */     if (day != null) {
/*     */       
/* 341 */       String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM vi_Day_Type WHERE type_id = " + 
/*     */         
/* 343 */         day.getDayID() + 
/* 344 */         ";";
/* 345 */       JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
/* 346 */       connectorTimestamp.runQuery();
/* 347 */       if (connectorTimestamp.more())
/*     */       {
/* 349 */         if (day.getLastUpdatedCk() != Long.parseLong(connectorTimestamp.getField("last_updated_ck", ""), 16)) {
/*     */           
/* 351 */           connectorTimestamp.close();
/* 352 */           return false;
/*     */         } 
/*     */       }
/* 355 */       connectorTimestamp.close();
/* 356 */       return true;
/*     */     } 
/* 358 */     return false;
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
/*     */   public boolean isDuplicate(Day day) {
/* 370 */     boolean isDuplicate = false;
/*     */     
/* 372 */     if (day != null) {
/*     */       
/* 374 */       String query = "SELECT * FROM vi_day_type  WHERE grouping = " + 
/* 375 */         day.getCalendarGroup() + 
/* 376 */         " AND date = '" + MilestoneHelper.getFormatedDate(day.getSpecificDate()) + "'" + 
/* 377 */         " AND type_id <> " + day.getDayID();
/*     */       
/* 379 */       JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 380 */       connector.runQuery();
/*     */       
/* 382 */       if (connector.more()) {
/* 383 */         isDuplicate = true;
/*     */       }
/* 385 */       connector.close();
/*     */     } 
/*     */     
/* 388 */     return isDuplicate;
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\DayManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */