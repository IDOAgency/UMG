package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.EnhancedProperties;
import com.techempower.gemini.Context;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.JdbcConnector;
import com.universal.milestone.MilestoneConstants;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.Notepad;
import com.universal.milestone.PriceCode;
import com.universal.milestone.PriceCodeManager;
import java.util.Vector;

public class PriceCodeManager implements MilestoneConstants {
  public static final String COMPONENT_CODE = "mPrC";
  
  public static final String DEFAULT_QUERY = "SELECT sell_code, retail_code, description, isDigital FROM vi_Price_Code";
  
  public static final String DEFAULT_ORDER = " ORDER BY sell_code";
  
  protected static PriceCodeManager priceCodeManager = null;
  
  protected static ComponentLog log;
  
  public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getLog("mPrC"); }
  
  public static PriceCodeManager getInstance() {
    if (priceCodeManager == null)
      priceCodeManager = new PriceCodeManager(); 
    return priceCodeManager;
  }
  
  public PriceCode getPriceCode(String id, boolean getTimestamp, boolean isDigital) {
    String isDigitalStr = isDigital ? "1" : "0";
    String priceCodeQuery = "SELECT [sell_code], [retail_code], [units], [price_point], [description], [unit_cost], [total_cost], [last_updated_on], [last_updated_by], [last_updated_ck], [isDigital] FROM vi_Price_Code WHERE sell_code = '" + 
      
      MilestoneHelper.escapeSingleQuotes(id) + "'";
    PriceCode priceCode = null;
    JdbcConnector connector = MilestoneHelper.getConnector(priceCodeQuery);
    connector.runQuery();
    if (connector.more()) {
      priceCode = new PriceCode();
      priceCode.setSellCode(connector.getField("sell_code", ""));
      priceCode.setRetailCode(connector.getField("retail_code", ""));
      priceCode.setUnits(connector.getIntegerField("units"));
      priceCode.setPricePoint(connector.getField("price_point", ""));
      priceCode.setDescription(connector.getField("description", ""));
      priceCode.setUnitCost(connector.getFloat("unit_cost"));
      priceCode.setTotalCost(connector.getFloat("total_cost"));
      String lastDateString = connector.getFieldByName("last_updated_on");
      if (lastDateString != null)
        priceCode.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastDateString)); 
      priceCode.setLastUpdatingUser(connector.getIntegerField("last_updated_by"));
      if (getTimestamp) {
        long lastUpdatedLong = Long.parseLong(connector.getField("last_updated_ck"), 16);
        priceCode.setLastUpdatedCheck(lastUpdatedLong);
      } 
      priceCode.setIsDigital(connector.getBoolean("isDigital"));
    } 
    connector.close();
    return priceCode;
  }
  
  private PriceCode getNotepadPriceCode(JdbcConnector connector) {
    PriceCode priceCode = null;
    if (connector != null) {
      priceCode = new PriceCode();
      priceCode.setSellCode(connector.getFieldByName("sell_code"));
      priceCode.setRetailCode(connector.getFieldByName("retail_code"));
      priceCode.setDescription(connector.getFieldByName("description"));
      priceCode.setIsDigital(connector.getBoolean("isDigital"));
    } 
    return priceCode;
  }
  
  public PriceCode savePriceCode(PriceCode priceCode, int userID) {
    long timestamp = priceCode.getLastUpdatedCheck();
    int isDigital = priceCode.getIsDigital() ? 1 : 0;
    String query = "sp_upd_Price_Code '" + 
      MilestoneHelper.escapeSingleQuotes(priceCode.getSellCode()) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(priceCode.getRetailCode()) + "'," + 
      priceCode.getUnits() + "," + 
      "'" + MilestoneHelper.escapeSingleQuotes(priceCode.getPricePoint()) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(priceCode.getDescription()) + "'," + 
      priceCode.getUnitCost() + "," + 
      priceCode.getTotalCost() + "," + 
      userID + "," + 
      timestamp + "," + 
      isDigital + 
      ";";
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    connector.close();
    String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM vi_Price_Code WHERE sell_code = '" + 
      
      MilestoneHelper.escapeSingleQuotes(priceCode.getSellCode()) + "';";
    JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
    connectorTimestamp.runQuery();
    if (connectorTimestamp.more()) {
      priceCode.setLastUpdatedCheck(Long.parseLong(connectorTimestamp.getField("last_updated_ck"), 16));
      String lastUpdated = "";
      lastUpdated = connectorTimestamp.getField("last_updated_on", "");
      if (lastUpdated.length() > 0)
        priceCode.setLastUpdateDate(MilestoneHelper.getDatabaseDate(connectorTimestamp.getField("last_updated_on", ""))); 
    } 
    connectorTimestamp.close();
    return priceCode;
  }
  
  public PriceCode saveNewPriceCode(PriceCode priceCode, int userID) {
    int isDigital = priceCode.getIsDigital() ? 1 : 0;
    String query = "sp_ins_Price_Code '" + 
      MilestoneHelper.escapeSingleQuotes(priceCode.getSellCode()) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(priceCode.getRetailCode()) + "'," + 
      priceCode.getUnits() + "," + 
      "'" + MilestoneHelper.escapeSingleQuotes(priceCode.getPricePoint()) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(priceCode.getDescription()) + "'," + 
      priceCode.getUnitCost() + "," + 
      priceCode.getTotalCost() + "," + 
      userID + "," + 
      isDigital;
    log.debug("Insert query:\n" + query);
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    connector.close();
    return priceCode;
  }
  
  public void deletePriceCode(PriceCode priceCode, int userID) {
    String query = "sp_del_Price_Code '" + 
      MilestoneHelper.escapeSingleQuotes(priceCode.getSellCode()) + "'";
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    connector.close();
  }
  
  public void setNotepadQuery(Context context, int UserId, Notepad notepad) {
    if (notepad != null) {
      String sellCodeSearch = context.getParameter("SellCodeSearch");
      String retailCodeSearch = context.getParameter("RetailCodeSearch");
      String descriptionSearch = context.getParameter("DescriptionSearch");
      String isDigitalSearch = context.getParameter("IsDigitalSearch");
      String priceCodeQuery = "SELECT sell_code, retail_code, description, isDigital FROM vi_Price_Code";
      if (MilestoneHelper.isStringNotEmpty(sellCodeSearch))
        priceCodeQuery = String.valueOf(priceCodeQuery) + MilestoneHelper.addQueryParams(priceCodeQuery, " sell_code " + MilestoneHelper.setWildCardsEscapeSingleQuotes(sellCodeSearch)); 
      if (MilestoneHelper.isStringNotEmpty(retailCodeSearch))
        priceCodeQuery = String.valueOf(priceCodeQuery) + MilestoneHelper.addQueryParams(priceCodeQuery, " retail_code " + MilestoneHelper.setWildCardsEscapeSingleQuotes(retailCodeSearch)); 
      if (MilestoneHelper.isStringNotEmpty(descriptionSearch))
        priceCodeQuery = String.valueOf(priceCodeQuery) + MilestoneHelper.addQueryParams(priceCodeQuery, " description " + MilestoneHelper.setWildCardsEscapeSingleQuotes(descriptionSearch)); 
      if (MilestoneHelper.isStringNotEmpty(isDigitalSearch) && isDigitalSearch.compareTo("2") != 0)
        priceCodeQuery = String.valueOf(priceCodeQuery) + MilestoneHelper.addQueryParams(priceCodeQuery, " isDigital = " + isDigitalSearch); 
      String order = " ORDER BY sell_code";
      log.debug("notepad PCCPCPCPCPC priceCodeQuery:\n" + priceCodeQuery);
      notepad.setSearchQuery(priceCodeQuery);
      notepad.setOrderBy(order);
    } 
  }
  
  public Vector getPriceCodeNotepadList(int UserId, Notepad notepad) {
    String priceCodeQuery = "";
    if (notepad != null && !notepad.getSearchQuery().equals("")) {
      priceCodeQuery = notepad.getSearchQuery();
      priceCodeQuery = String.valueOf(priceCodeQuery) + notepad.getOrderBy();
    } else {
      priceCodeQuery = "SELECT sell_code, retail_code, description, isDigital FROM vi_Price_Code ORDER BY sell_code";
    } 
    PriceCode priceCode = null;
    Vector precache = new Vector();
    JdbcConnector connector = MilestoneHelper.getConnector(priceCodeQuery);
    connector.runQuery();
    while (connector.more()) {
      priceCode = getNotepadPriceCode(connector);
      precache.addElement(priceCode);
      priceCode = null;
      connector.next();
    } 
    connector.close();
    return precache;
  }
  
  public boolean isTimestampValid(PriceCode priceCode) {
    if (priceCode != null) {
      String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM vi_Price_Code WHERE sell_code = '" + 
        
        MilestoneHelper.escapeSingleQuotes(priceCode.getSellCode()) + "';";
      JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
      connectorTimestamp.runQuery();
      if (connectorTimestamp.more())
        if (priceCode.getLastUpdatedCheck() != Long.parseLong(connectorTimestamp.getField("last_updated_ck", ""), 16)) {
          connectorTimestamp.close();
          return false;
        }  
      connectorTimestamp.close();
      return true;
    } 
    return false;
  }
  
  public boolean isDuplicate(PriceCode priceCode) {
    boolean isDuplicate = false;
    if (priceCode != null) {
      String isDigital = priceCode.isDigital ? "1" : "0";
      String query = "SELECT * FROM vi_Price_Code  WHERE sell_code = '" + 
        MilestoneHelper.escapeSingleQuotes(priceCode.getSellCode()) + "'";
      JdbcConnector connector = MilestoneHelper.getConnector(query);
      connector.runQuery();
      if (connector.more())
        isDuplicate = true; 
      connector.close();
    } 
    return isDuplicate;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\PriceCodeManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */