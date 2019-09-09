/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.EnhancedProperties;
/*     */ import com.techempower.gemini.Context;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.Cache;
/*     */ import com.universal.milestone.JdbcConnector;
/*     */ import com.universal.milestone.MilestoneConstants;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.Notepad;
/*     */ import com.universal.milestone.ReleaseWeek;
/*     */ import com.universal.milestone.ReleaseWeekManager;
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
/*     */ public class ReleaseWeekManager
/*     */   implements MilestoneConstants
/*     */ {
/*     */   public static final String COMPONENT_CODE = "mFam";
/*     */   public static final String DEFAULT_QUERY = "SELECT [name], [start_date], [end_date]FROM vi_Date_Period";
/*     */   public static final String DEFAULT_ORDER = "  ORDER BY name";
/*  45 */   protected static ReleaseWeekManager releaseWeekManager = null;
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
/*  57 */   public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getLog("mFam"); }
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
/*     */   public static ReleaseWeekManager getInstance() {
/*  76 */     if (releaseWeekManager == null)
/*     */     {
/*  78 */       releaseWeekManager = new ReleaseWeekManager();
/*     */     }
/*  80 */     return releaseWeekManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReleaseWeek getReleaseWeek(String id, boolean getTimestamp) {
/*  88 */     String releaseWeekQuery = "SELECT [per_id], [name], [cycle], [start_date], [end_date], [sol_date], [last_updated_on], [last_updated_by], [last_updated_ck] FROM vi_Date_Period WHERE name = '" + 
/*     */ 
/*     */ 
/*     */       
/*  92 */       MilestoneHelper.escapeSingleQuotes(id) + "'";
/*     */     
/*  94 */     ReleaseWeek releaseWeek = null;
/*     */     
/*  96 */     JdbcConnector connector = MilestoneHelper.getConnector(releaseWeekQuery);
/*  97 */     connector.runQuery();
/*     */     
/*  99 */     if (connector.more()) {
/*     */       
/* 101 */       releaseWeek = new ReleaseWeek();
/*     */ 
/*     */       
/* 104 */       releaseWeek.setReleaseWeekID(connector.getIntegerField("per_id"));
/*     */ 
/*     */       
/* 107 */       releaseWeek.setName(connector.getField("name"));
/*     */ 
/*     */       
/* 110 */       releaseWeek.setCycle(connector.getField("cycle"));
/*     */ 
/*     */       
/* 113 */       String startDateString = connector.getFieldByName("start_date");
/* 114 */       if (startDateString != null) {
/* 115 */         releaseWeek.setStartDate(MilestoneHelper.getDatabaseDate(startDateString));
/*     */       }
/*     */       
/* 118 */       String endDateString = connector.getFieldByName("end_date");
/* 119 */       if (endDateString != null) {
/* 120 */         releaseWeek.setEndDate(MilestoneHelper.getDatabaseDate(endDateString));
/*     */       }
/*     */       
/* 123 */       String solDateString = connector.getFieldByName("sol_date");
/* 124 */       if (solDateString != null) {
/* 125 */         releaseWeek.setSolDate(MilestoneHelper.getDatabaseDate(solDateString));
/*     */       }
/*     */       
/* 128 */       String lastDateString = connector.getFieldByName("last_updated_on");
/* 129 */       if (lastDateString != null) {
/* 130 */         releaseWeek.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastDateString));
/*     */       }
/*     */       
/* 133 */       releaseWeek.setLastUpdatingUser(connector.getIntegerField("last_updated_by"));
/*     */ 
/*     */       
/* 136 */       if (getTimestamp) {
/*     */         
/* 138 */         long lastUpdatedLong = Long.parseLong(connector.getField("last_updated_ck"), 16);
/* 139 */         releaseWeek.setLastUpdatedCheck(lastUpdatedLong);
/*     */       } 
/*     */     } 
/*     */     
/* 143 */     connector.close();
/*     */     
/* 145 */     return releaseWeek;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ReleaseWeek getNotepadReleaseWeek(JdbcConnector connector) {
/* 156 */     ReleaseWeek releaseWeek = null;
/*     */     
/* 158 */     if (connector != null) {
/*     */       
/* 160 */       releaseWeek = new ReleaseWeek();
/*     */ 
/*     */       
/* 163 */       releaseWeek.setName(connector.getFieldByName("name"));
/*     */ 
/*     */       
/* 166 */       String startDateString = connector.getFieldByName("start_date");
/* 167 */       if (startDateString != null) {
/* 168 */         releaseWeek.setStartDate(MilestoneHelper.getDatabaseDate(startDateString));
/*     */       }
/*     */       
/* 171 */       String endDateString = connector.getFieldByName("end_date");
/* 172 */       if (endDateString != null) {
/* 173 */         releaseWeek.setEndDate(MilestoneHelper.getDatabaseDate(endDateString));
/*     */       }
/*     */     } 
/* 176 */     return releaseWeek;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReleaseWeek saveReleaseWeek(ReleaseWeek releaseWeek, int userID) {
/* 185 */     long timestamp = releaseWeek.getLastUpdatedCheck();
/*     */     
/* 187 */     String query = "sp_sav_Date_Period " + 
/* 188 */       releaseWeek.getReleaseWeekID() + "," + 
/* 189 */       "'" + MilestoneHelper.escapeSingleQuotes(releaseWeek.getName()) + "'," + 
/* 190 */       "'" + MilestoneHelper.escapeSingleQuotes(releaseWeek.getCycle()) + "'," + 
/* 191 */       "'" + MilestoneHelper.getFormatedDate(releaseWeek.getStartDate()) + "'," + 
/* 192 */       "'" + MilestoneHelper.getFormatedDate(releaseWeek.getEndDate()) + "'," + 
/* 193 */       "'" + MilestoneHelper.getFormatedDate(releaseWeek.getSolDate()) + "'," + 
/* 194 */       userID + "," + 
/* 195 */       timestamp;
/*     */     
/* 197 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 198 */     connector.runQuery();
/* 199 */     connector.close();
/*     */ 
/*     */     
/* 202 */     releaseWeek.flushAudits(userID);
/*     */ 
/*     */ 
/*     */     
/* 206 */     String timestampQuery = "Select last_updated_ck, last_updated_on from vi_Date_Period where per_id = " + releaseWeek.getReleaseWeekID();
/* 207 */     JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
/* 208 */     connectorTimestamp.runQuery();
/*     */     
/* 210 */     if (connectorTimestamp.more()) {
/*     */       
/* 212 */       releaseWeek.setLastUpdatedCheck(Long.parseLong(connectorTimestamp.getField("last_updated_ck"), 16));
/* 213 */       releaseWeek.setLastUpdateDate(MilestoneHelper.getDatabaseDate(connectorTimestamp.getField("last_updated_on")));
/*     */     } 
/* 215 */     connectorTimestamp.close();
/*     */     
/* 217 */     Cache.flushDatePeriods();
/*     */     
/* 219 */     return releaseWeek;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReleaseWeek saveNewReleaseWeek(ReleaseWeek releaseWeek, int userID) {
/* 227 */     String query = "sp_sav_Date_Period -1,'" + 
/*     */       
/* 229 */       MilestoneHelper.escapeSingleQuotes(releaseWeek.getName()) + "'," + 
/* 230 */       "'" + MilestoneHelper.escapeSingleQuotes(releaseWeek.getCycle()) + "'," + 
/* 231 */       "'" + MilestoneHelper.getFormatedDate(releaseWeek.getStartDate()) + "'," + 
/* 232 */       "'" + MilestoneHelper.getFormatedDate(releaseWeek.getEndDate()) + "'," + 
/* 233 */       "'" + MilestoneHelper.getFormatedDate(releaseWeek.getSolDate()) + "'," + 
/* 234 */       userID + "," + 
/* 235 */       -1;
/*     */ 
/*     */     
/* 238 */     releaseWeek.flushAudits(userID);
/*     */     
/* 240 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 241 */     connector.runQuery();
/*     */     
/* 243 */     if (connector.getIntegerField("ReturnId") > 0) {
/* 244 */       releaseWeek.setReleaseWeekID(connector.getIntegerField("ReturnId"));
/*     */     }
/* 246 */     connector.close();
/*     */     
/* 248 */     Cache.flushDatePeriods();
/*     */     
/* 250 */     return releaseWeek;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleteReleaseWeek(ReleaseWeek releaseWeek, int userID) {
/* 258 */     String query = "sp_del_Date_Period " + 
/* 259 */       releaseWeek.getReleaseWeekID();
/*     */     
/* 261 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 262 */     connector.runQuery();
/* 263 */     connector.close();
/*     */     
/* 265 */     Cache.flushDatePeriods();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNotepadQuery(Context context, int UserId, Notepad notepad) {
/* 275 */     if (notepad != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 280 */       String nameSearch = context.getParameter("NameSearch");
/* 281 */       String cycleSearch = context.getParameter("CycleSearch");
/* 282 */       String startDateSearch = context.getParameter("StartDateSearch");
/* 283 */       String endDateSearch = context.getParameter("EndDateSearch");
/*     */ 
/*     */       
/* 286 */       String releaseWeekQuery = "SELECT * FROM vi_Date_Period ";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 292 */       if (MilestoneHelper.isStringNotEmpty(nameSearch)) {
/* 293 */         releaseWeekQuery = String.valueOf(releaseWeekQuery) + MilestoneHelper.addQueryParams(releaseWeekQuery, " name " + MilestoneHelper.setWildCardsEscapeSingleQuotes(nameSearch));
/*     */       }
/* 295 */       if (MilestoneHelper.isStringNotEmpty(cycleSearch)) {
/* 296 */         releaseWeekQuery = String.valueOf(releaseWeekQuery) + MilestoneHelper.addQueryParams(releaseWeekQuery, " cycle " + MilestoneHelper.setWildCardsEscapeSingleQuotes(cycleSearch));
/*     */       }
/* 298 */       if (MilestoneHelper.isStringNotEmpty(startDateSearch)) {
/* 299 */         releaseWeekQuery = String.valueOf(releaseWeekQuery) + MilestoneHelper.addQueryParams(releaseWeekQuery, " start_date = '" + MilestoneHelper.escapeSingleQuotes(startDateSearch) + "'");
/*     */       }
/* 301 */       if (MilestoneHelper.isStringNotEmpty(endDateSearch)) {
/* 302 */         releaseWeekQuery = String.valueOf(releaseWeekQuery) + MilestoneHelper.addQueryParams(releaseWeekQuery, " end_date = '" + MilestoneHelper.escapeSingleQuotes(endDateSearch) + "'");
/*     */       }
/* 304 */       String order = " ORDER BY name";
/*     */       
/* 306 */       log.debug("Notepad rwrwrwrw releaseWeekQuery:\n" + releaseWeekQuery);
/*     */       
/* 308 */       notepad.setSearchQuery(releaseWeekQuery);
/* 309 */       notepad.setOrderBy(order);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector getReleaseWeekNotepadList(int UserId, Notepad notepad) {
/* 315 */     String releaseWeekQuery = "";
/*     */     
/* 317 */     if (notepad != null && !notepad.getSearchQuery().equals("")) {
/*     */       
/* 319 */       releaseWeekQuery = notepad.getSearchQuery();
/* 320 */       releaseWeekQuery = String.valueOf(releaseWeekQuery) + notepad.getOrderBy();
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 325 */       releaseWeekQuery = "SELECT [name], [start_date], [end_date]FROM vi_Date_Period  ORDER BY name";
/*     */     } 
/*     */     
/* 328 */     ReleaseWeek releaseWeek = null;
/* 329 */     Vector precache = new Vector();
/*     */     
/* 331 */     JdbcConnector connector = MilestoneHelper.getConnector(releaseWeekQuery);
/* 332 */     connector.runQuery();
/*     */     
/* 334 */     while (connector.more()) {
/*     */ 
/*     */       
/* 337 */       releaseWeek = getNotepadReleaseWeek(connector);
/* 338 */       precache.addElement(releaseWeek);
/* 339 */       releaseWeek = null;
/* 340 */       connector.next();
/*     */     } 
/* 342 */     connector.close();
/*     */     
/* 344 */     return precache;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTimestampValid(ReleaseWeek releaseWeek) {
/* 353 */     if (releaseWeek != null) {
/*     */       
/* 355 */       String timestampQuery = "Select last_updated_ck, last_updated_on from vi_Date_Period where per_id = " + releaseWeek.getReleaseWeekID();
/*     */       
/* 357 */       JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
/* 358 */       connectorTimestamp.runQuery();
/* 359 */       if (connectorTimestamp.more())
/*     */       {
/* 361 */         if (releaseWeek.getLastUpdatedCheck() != Long.parseLong(connectorTimestamp.getField("last_updated_ck", ""), 16)) {
/*     */           
/* 363 */           connectorTimestamp.close();
/* 364 */           return false;
/*     */         } 
/*     */       }
/* 367 */       connectorTimestamp.close();
/* 368 */       return true;
/*     */     } 
/* 370 */     return false;
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
/*     */   public boolean isDuplicate(ReleaseWeek releaseWeek) {
/* 382 */     boolean isDuplicate = false;
/*     */     
/* 384 */     if (releaseWeek != null) {
/*     */       
/* 386 */       String query = "SELECT * FROM vi_date_period  WHERE name = '" + 
/* 387 */         MilestoneHelper.escapeSingleQuotes(releaseWeek.getName()) + "' " + 
/* 388 */         " AND per_id <> " + releaseWeek.getReleaseWeekID();
/*     */       
/* 390 */       JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 391 */       connector.runQuery();
/*     */       
/* 393 */       if (connector.more()) {
/* 394 */         isDuplicate = true;
/*     */       }
/* 396 */       connector.close();
/*     */     } 
/*     */     
/* 399 */     return isDuplicate;
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\ReleaseWeekManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */