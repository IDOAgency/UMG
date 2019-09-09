/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.EnhancedProperties;
/*     */ import com.techempower.gemini.Context;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.JdbcConnector;
/*     */ import com.universal.milestone.MilestoneConstants;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.Notepad;
/*     */ import com.universal.milestone.PriceCode;
/*     */ import com.universal.milestone.PriceCodeManager;
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
/*     */ public class PriceCodeManager
/*     */   implements MilestoneConstants
/*     */ {
/*     */   public static final String COMPONENT_CODE = "mPrC";
/*     */   public static final String DEFAULT_QUERY = "SELECT sell_code, retail_code, description, isDigital FROM vi_Price_Code";
/*     */   public static final String DEFAULT_ORDER = " ORDER BY sell_code";
/*  43 */   protected static PriceCodeManager priceCodeManager = null;
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
/*  55 */   public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getLog("mPrC"); }
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
/*     */   public static PriceCodeManager getInstance() {
/*  73 */     if (priceCodeManager == null)
/*     */     {
/*  75 */       priceCodeManager = new PriceCodeManager();
/*     */     }
/*  77 */     return priceCodeManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PriceCode getPriceCode(String id, boolean getTimestamp, boolean isDigital) {
/*  87 */     String isDigitalStr = isDigital ? "1" : "0";
/*     */     
/*  89 */     String priceCodeQuery = "SELECT [sell_code], [retail_code], [units], [price_point], [description], [unit_cost], [total_cost], [last_updated_on], [last_updated_by], [last_updated_ck], [isDigital] FROM vi_Price_Code WHERE sell_code = '" + 
/*     */ 
/*     */ 
/*     */       
/*  93 */       MilestoneHelper.escapeSingleQuotes(id) + "'";
/*     */ 
/*     */     
/*  96 */     PriceCode priceCode = null;
/*     */     
/*  98 */     JdbcConnector connector = MilestoneHelper.getConnector(priceCodeQuery);
/*  99 */     connector.runQuery();
/*     */     
/* 101 */     if (connector.more()) {
/*     */       
/* 103 */       priceCode = new PriceCode();
/*     */ 
/*     */       
/* 106 */       priceCode.setSellCode(connector.getField("sell_code", ""));
/*     */ 
/*     */       
/* 109 */       priceCode.setRetailCode(connector.getField("retail_code", ""));
/*     */ 
/*     */       
/* 112 */       priceCode.setUnits(connector.getIntegerField("units"));
/*     */ 
/*     */       
/* 115 */       priceCode.setPricePoint(connector.getField("price_point", ""));
/*     */ 
/*     */       
/* 118 */       priceCode.setDescription(connector.getField("description", ""));
/*     */ 
/*     */       
/* 121 */       priceCode.setUnitCost(connector.getFloat("unit_cost"));
/*     */ 
/*     */       
/* 124 */       priceCode.setTotalCost(connector.getFloat("total_cost"));
/*     */ 
/*     */       
/* 127 */       String lastDateString = connector.getFieldByName("last_updated_on");
/* 128 */       if (lastDateString != null) {
/* 129 */         priceCode.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastDateString));
/*     */       }
/*     */       
/* 132 */       priceCode.setLastUpdatingUser(connector.getIntegerField("last_updated_by"));
/*     */ 
/*     */       
/* 135 */       if (getTimestamp) {
/*     */         
/* 137 */         long lastUpdatedLong = Long.parseLong(connector.getField("last_updated_ck"), 16);
/* 138 */         priceCode.setLastUpdatedCheck(lastUpdatedLong);
/*     */       } 
/*     */ 
/*     */       
/* 142 */       priceCode.setIsDigital(connector.getBoolean("isDigital"));
/*     */     } 
/*     */     
/* 145 */     connector.close();
/*     */     
/* 147 */     return priceCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PriceCode getNotepadPriceCode(JdbcConnector connector) {
/* 158 */     PriceCode priceCode = null;
/*     */     
/* 160 */     if (connector != null) {
/*     */       
/* 162 */       priceCode = new PriceCode();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 169 */       priceCode.setSellCode(connector.getFieldByName("sell_code"));
/*     */ 
/*     */       
/* 172 */       priceCode.setRetailCode(connector.getFieldByName("retail_code"));
/*     */ 
/*     */       
/* 175 */       priceCode.setDescription(connector.getFieldByName("description"));
/*     */ 
/*     */       
/* 178 */       priceCode.setIsDigital(connector.getBoolean("isDigital"));
/*     */     } 
/*     */ 
/*     */     
/* 182 */     return priceCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PriceCode savePriceCode(PriceCode priceCode, int userID) {
/* 191 */     long timestamp = priceCode.getLastUpdatedCheck();
/* 192 */     int isDigital = priceCode.getIsDigital() ? 1 : 0;
/*     */     
/* 194 */     String query = "sp_upd_Price_Code '" + 
/* 195 */       MilestoneHelper.escapeSingleQuotes(priceCode.getSellCode()) + "'," + 
/* 196 */       "'" + MilestoneHelper.escapeSingleQuotes(priceCode.getRetailCode()) + "'," + 
/* 197 */       priceCode.getUnits() + "," + 
/* 198 */       "'" + MilestoneHelper.escapeSingleQuotes(priceCode.getPricePoint()) + "'," + 
/* 199 */       "'" + MilestoneHelper.escapeSingleQuotes(priceCode.getDescription()) + "'," + 
/* 200 */       priceCode.getUnitCost() + "," + 
/* 201 */       priceCode.getTotalCost() + "," + 
/* 202 */       userID + "," + 
/* 203 */       timestamp + "," + 
/* 204 */       isDigital + 
/* 205 */       ";";
/*     */     
/* 207 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 208 */     connector.runQuery();
/* 209 */     connector.close();
/*     */     
/* 211 */     String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM vi_Price_Code WHERE sell_code = '" + 
/*     */       
/* 213 */       MilestoneHelper.escapeSingleQuotes(priceCode.getSellCode()) + "';";
/*     */     
/* 215 */     JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
/* 216 */     connectorTimestamp.runQuery();
/* 217 */     if (connectorTimestamp.more()) {
/*     */       
/* 219 */       priceCode.setLastUpdatedCheck(Long.parseLong(connectorTimestamp.getField("last_updated_ck"), 16));
/*     */       
/* 221 */       String lastUpdated = "";
/* 222 */       lastUpdated = connectorTimestamp.getField("last_updated_on", "");
/* 223 */       if (lastUpdated.length() > 0)
/* 224 */         priceCode.setLastUpdateDate(MilestoneHelper.getDatabaseDate(connectorTimestamp.getField("last_updated_on", ""))); 
/*     */     } 
/* 226 */     connectorTimestamp.close();
/*     */     
/* 228 */     return priceCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PriceCode saveNewPriceCode(PriceCode priceCode, int userID) {
/* 236 */     int isDigital = priceCode.getIsDigital() ? 1 : 0;
/*     */     
/* 238 */     String query = "sp_ins_Price_Code '" + 
/* 239 */       MilestoneHelper.escapeSingleQuotes(priceCode.getSellCode()) + "'," + 
/* 240 */       "'" + MilestoneHelper.escapeSingleQuotes(priceCode.getRetailCode()) + "'," + 
/* 241 */       priceCode.getUnits() + "," + 
/* 242 */       "'" + MilestoneHelper.escapeSingleQuotes(priceCode.getPricePoint()) + "'," + 
/* 243 */       "'" + MilestoneHelper.escapeSingleQuotes(priceCode.getDescription()) + "'," + 
/* 244 */       priceCode.getUnitCost() + "," + 
/* 245 */       priceCode.getTotalCost() + "," + 
/* 246 */       userID + "," + 
/* 247 */       isDigital;
/*     */     
/* 249 */     log.debug("Insert query:\n" + query);
/* 250 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 251 */     connector.runQuery();
/* 252 */     connector.close();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 258 */     return priceCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deletePriceCode(PriceCode priceCode, int userID) {
/* 266 */     String query = "sp_del_Price_Code '" + 
/* 267 */       MilestoneHelper.escapeSingleQuotes(priceCode.getSellCode()) + "'";
/*     */     
/* 269 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 270 */     connector.runQuery();
/* 271 */     connector.close();
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
/*     */   public void setNotepadQuery(Context context, int UserId, Notepad notepad) {
/* 287 */     if (notepad != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 292 */       String sellCodeSearch = context.getParameter("SellCodeSearch");
/* 293 */       String retailCodeSearch = context.getParameter("RetailCodeSearch");
/* 294 */       String descriptionSearch = context.getParameter("DescriptionSearch");
/* 295 */       String isDigitalSearch = context.getParameter("IsDigitalSearch");
/*     */ 
/*     */       
/* 298 */       String priceCodeQuery = "SELECT sell_code, retail_code, description, isDigital FROM vi_Price_Code";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 304 */       if (MilestoneHelper.isStringNotEmpty(sellCodeSearch)) {
/* 305 */         priceCodeQuery = String.valueOf(priceCodeQuery) + MilestoneHelper.addQueryParams(priceCodeQuery, " sell_code " + MilestoneHelper.setWildCardsEscapeSingleQuotes(sellCodeSearch));
/*     */       }
/* 307 */       if (MilestoneHelper.isStringNotEmpty(retailCodeSearch)) {
/* 308 */         priceCodeQuery = String.valueOf(priceCodeQuery) + MilestoneHelper.addQueryParams(priceCodeQuery, " retail_code " + MilestoneHelper.setWildCardsEscapeSingleQuotes(retailCodeSearch));
/*     */       }
/* 310 */       if (MilestoneHelper.isStringNotEmpty(descriptionSearch)) {
/* 311 */         priceCodeQuery = String.valueOf(priceCodeQuery) + MilestoneHelper.addQueryParams(priceCodeQuery, " description " + MilestoneHelper.setWildCardsEscapeSingleQuotes(descriptionSearch));
/*     */       }
/* 313 */       if (MilestoneHelper.isStringNotEmpty(isDigitalSearch) && isDigitalSearch.compareTo("2") != 0) {
/* 314 */         priceCodeQuery = String.valueOf(priceCodeQuery) + MilestoneHelper.addQueryParams(priceCodeQuery, " isDigital = " + isDigitalSearch);
/*     */       }
/* 316 */       String order = " ORDER BY sell_code";
/*     */       
/* 318 */       log.debug("notepad PCCPCPCPCPC priceCodeQuery:\n" + priceCodeQuery);
/*     */       
/* 320 */       notepad.setSearchQuery(priceCodeQuery);
/* 321 */       notepad.setOrderBy(order);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector getPriceCodeNotepadList(int UserId, Notepad notepad) {
/* 331 */     String priceCodeQuery = "";
/*     */     
/* 333 */     if (notepad != null && !notepad.getSearchQuery().equals("")) {
/*     */       
/* 335 */       priceCodeQuery = notepad.getSearchQuery();
/* 336 */       priceCodeQuery = String.valueOf(priceCodeQuery) + notepad.getOrderBy();
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 341 */       priceCodeQuery = "SELECT sell_code, retail_code, description, isDigital FROM vi_Price_Code ORDER BY sell_code";
/*     */     } 
/*     */     
/* 344 */     PriceCode priceCode = null;
/*     */     
/* 346 */     Vector precache = new Vector();
/*     */     
/* 348 */     JdbcConnector connector = MilestoneHelper.getConnector(priceCodeQuery);
/* 349 */     connector.runQuery();
/*     */     
/* 351 */     while (connector.more()) {
/*     */       
/* 353 */       priceCode = getNotepadPriceCode(connector);
/* 354 */       precache.addElement(priceCode);
/* 355 */       priceCode = null;
/* 356 */       connector.next();
/*     */     } 
/* 358 */     connector.close();
/*     */     
/* 360 */     return precache;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTimestampValid(PriceCode priceCode) {
/* 369 */     if (priceCode != null) {
/*     */       
/* 371 */       String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM vi_Price_Code WHERE sell_code = '" + 
/*     */         
/* 373 */         MilestoneHelper.escapeSingleQuotes(priceCode.getSellCode()) + "';";
/*     */       
/* 375 */       JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
/* 376 */       connectorTimestamp.runQuery();
/* 377 */       if (connectorTimestamp.more())
/*     */       {
/* 379 */         if (priceCode.getLastUpdatedCheck() != Long.parseLong(connectorTimestamp.getField("last_updated_ck", ""), 16)) {
/*     */           
/* 381 */           connectorTimestamp.close();
/* 382 */           return false;
/*     */         } 
/*     */       }
/* 385 */       connectorTimestamp.close();
/* 386 */       return true;
/*     */     } 
/* 388 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDuplicate(PriceCode priceCode) {
/* 399 */     boolean isDuplicate = false;
/*     */     
/* 401 */     if (priceCode != null) {
/*     */       
/* 403 */       String isDigital = priceCode.isDigital ? "1" : "0";
/*     */ 
/*     */       
/* 406 */       String query = "SELECT * FROM vi_Price_Code  WHERE sell_code = '" + 
/* 407 */         MilestoneHelper.escapeSingleQuotes(priceCode.getSellCode()) + "'";
/*     */ 
/*     */       
/* 410 */       JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 411 */       connector.runQuery();
/*     */       
/* 413 */       if (connector.more()) {
/* 414 */         isDuplicate = true;
/*     */       }
/* 416 */       connector.close();
/*     */     } 
/*     */     
/* 419 */     return isDuplicate;
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\PriceCodeManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */