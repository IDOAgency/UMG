package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.EnhancedProperties;
import com.techempower.gemini.Context;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.JdbcConnector;
import com.universal.milestone.LookupObject;
import com.universal.milestone.MilestoneConstants;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.Notepad;
import com.universal.milestone.PrefixCode;
import com.universal.milestone.Table;
import com.universal.milestone.TableManager;
import java.util.Vector;

public class TableManager implements MilestoneConstants {
  public static final String COMPONENT_CODE = "mTab";
  
  protected static TableManager TableManager = null;
  
  protected static ComponentLog log;
  
  public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getLog("mTab"); }
  
  public static TableManager getInstance() {
    if (TableManager == null)
      TableManager = new TableManager(); 
    return TableManager;
  }
  
  public Table getTable(int id, boolean getTimestamp) {
    String TableQuery = "SELECT [field_id], [field_name], [description], [max_length], [add_indicator], [subdetail_indicator],[last_updated_on], [last_updated_by], [last_updated_ck] FROM vi_Lookup_Header where field_id = " + 
      
      id + 
      " ORDER BY [field_name];";
    JdbcConnector connector = MilestoneHelper.getConnector(TableQuery);
    connector.runQuery();
    String detail = "";
    String subdetail = "";
    Table table = null;
    if (connector.more()) {
      detail = "SELECT [field_id], [value], [description], [last_updated_on], [last_updated_by], [last_updated_ck], [inactive] FROM vi_Lookup_Detail  WHERE [field_id] = " + 
        
        connector.getIntegerField("field_id") + 
        " order by [value];";
      table = new Table();
      table.setId(connector.getIntegerField("field_id"));
      table.setAbbreviation(connector.getField("field_name"));
      table.setName(connector.getField("description"));
      JdbcConnector detailConnector = MilestoneHelper.getConnector(detail);
      detailConnector.runQuery();
      if (detailConnector.more()) {
        subdetail = "SELECT [field_id], [det_value], [sub_value], [description], [last_updated_on], [last_updated_by], [last_updated_ck], [inactive] FROM vi_Lookup_SubDetail  WHERE field_id = " + 
          
          connector.getIntegerField("field_id") + 
          " AND det_value = '" + MilestoneHelper.escapeSingleQuotes(detailConnector.getField("value")) + "'" + 
          ";";
        JdbcConnector subdetailConnector = MilestoneHelper.getConnector(subdetail);
        subdetailConnector.runQuery();
        LookupObject detailObj = new LookupObject(detailConnector.getField("value"), detailConnector.getField("description"));
        detailObj.setId(detailConnector.getIntegerField("field_id"));
        PrefixCode subdetailObj = new PrefixCode();
        if (subdetailConnector.more()) {
          subdetailObj.setAbbreviation(subdetailConnector.getField("sub_value"));
          subdetailObj.setName(subdetailConnector.getField("description"));
          subdetailObj.setId(subdetailConnector.getIntegerField("field_id"));
          subdetailObj.setDetValue(subdetailConnector.getField("det_value"));
          String lastDateStringSubDetail = subdetailConnector.getFieldByName("last_updated_on");
          if (lastDateStringSubDetail != null)
            subdetailObj.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastDateStringSubDetail)); 
          subdetailObj.setLastUpdatingUser(subdetailConnector.getIntegerField("last_updated_by"));
          long lastUpdatedLongSubDetail = Long.parseLong(subdetailConnector.getField("last_updated_ck"), 16);
          subdetailObj.setLastUpdatedCk(lastUpdatedLongSubDetail);
          subdetailObj.setInactive(subdetailConnector.getBoolean("inactive"));
        } 
        String lastDateStringDetail = detailConnector.getFieldByName("last_updated_on");
        if (lastDateStringDetail != null)
          detailObj.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastDateStringDetail)); 
        detailObj.setLastUpdatingUser(detailConnector.getIntegerField("last_updated_by"));
        long lastUpdatedLongDetail = Long.parseLong(detailConnector.getField("last_updated_ck"), 16);
        detailObj.setLastUpdatedCk(lastUpdatedLongDetail);
        detailObj.setInactive(detailConnector.getBoolean("inactive"));
        table.setDetail(detailObj);
        table.setSubDetail(subdetailObj);
        detailConnector.close();
        subdetailConnector.close();
      } 
      String lastDateString = connector.getFieldByName("last_updated_on");
      if (lastDateString != null)
        table.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastDateString)); 
      table.setLastUpdatingUser(connector.getIntegerField("last_updated_by"));
      if (getTimestamp) {
        long lastUpdatedLong = Long.parseLong(connector.getField("last_updated_ck"), 16);
        table.setLastUpdatedCk(lastUpdatedLong);
      } 
    } 
    connector.close();
    return table;
  }
  
  private Table getNotepadTable(JdbcConnector connector) {
    Table table = null;
    if (connector != null) {
      table = new Table();
      table.setId(connector.getIntegerField("field_id"));
      table.setAbbreviation(connector.getFieldByName("field_name"));
      table.setName(connector.getFieldByName("description"));
    } 
    return table;
  }
  
  private Table getNotepadTableDetail(JdbcConnector connector) {
    Table table = null;
    if (connector != null) {
      table = new Table();
      table.setId(connector.getIntegerField("field_id"));
      table.setName(connector.getField("description"));
      LookupObject detailObj = new LookupObject(connector.getFieldByName("value"), connector.getFieldByName("description"));
      detailObj.setId(connector.getIntegerField("field_id"));
      table.setDetail(detailObj);
    } 
    return table;
  }
  
  public Table getTableDetail(int id, String value, boolean getTimestamp) {
    String detail = "SELECT [field_id], [value], [description], [last_updated_on], [last_updated_by], [last_updated_ck], [inactive], [productType], isnull([isDigitalEquivalent],0) as isDigitalEquivalent FROM vi_Lookup_Detail  WHERE [field_id] = " + 
      
      id + 
      " AND value= '" + MilestoneHelper.escapeSingleQuotes(value) + "'";
    JdbcConnector detailConnector = MilestoneHelper.getConnector(detail);
    detailConnector.runQuery();
    String subdetail = "";
    Table table = null;
    if (detailConnector.more()) {
      subdetail = "SELECT [field_id], [det_value], [sub_value], [description], [last_updated_on], [last_updated_by], [last_updated_ck], [inactive], [productType] FROM vi_Lookup_SubDetail  WHERE field_id = " + 
        
        id + 
        " AND det_value = '" + MilestoneHelper.escapeSingleQuotes(value) + "'";
      JdbcConnector subdetailConnector = MilestoneHelper.getConnector(subdetail);
      subdetailConnector.runQuery();
      table = new Table();
      table.setId(detailConnector.getIntegerField("field_id"));
      table.setName(detailConnector.getField("description"));
      LookupObject detailObj = new LookupObject(detailConnector.getField("value"), 
          detailConnector.getField("description"));
      detailObj.setId(detailConnector.getIntegerField("field_id"));
      PrefixCode subdetailObj = new PrefixCode();
      if (subdetailConnector.more()) {
        subdetailObj.setAbbreviation(subdetailConnector.getField("sub_value"));
        subdetailObj.setId(subdetailConnector.getIntegerField("field_id"));
        subdetailObj.setName(subdetailConnector.getField("description"));
        subdetailObj.setDetValue(subdetailConnector.getField("det_value"));
        String lastDateStringSubDetail = subdetailConnector.getFieldByName("last_updated_on");
        if (lastDateStringSubDetail != null)
          subdetailObj.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastDateStringSubDetail)); 
        subdetailObj.setLastUpdatingUser(subdetailConnector.getIntegerField("last_updated_by"));
        long lastUpdatedLongSubDetail = Long.parseLong(subdetailConnector.getField("last_updated_ck"), 16);
        subdetailObj.setLastUpdatedCk(lastUpdatedLongSubDetail);
        subdetailObj.setInactive(subdetailConnector.getBoolean("inactive"));
        subdetailObj.setProdType(subdetailConnector.getIntegerField("productType"));
      } 
      String lastDateStringDetail = detailConnector.getField("last_updated_on", "");
      if (lastDateStringDetail.length() > 0)
        detailObj.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastDateStringDetail)); 
      detailObj.setLastUpdatingUser(detailConnector.getIntegerField("last_updated_by"));
      if (getTimestamp) {
        long lastUpdatedLongDetail = Long.parseLong(detailConnector.getField("last_updated_ck"), 16);
        detailObj.setLastUpdatedCk(lastUpdatedLongDetail);
      } 
      detailObj.setInactive(detailConnector.getBoolean("inactive"));
      detailObj.setProdType(detailConnector.getIntegerField("productType"));
      detailObj.setIsDigitalEquivalent(detailConnector.getBoolean("isDigitalEquivalent"));
      table.setDetail(detailObj);
      table.setSubDetail(subdetailObj);
      String lastDateString = detailConnector.getField("last_updated_on", "");
      if (lastDateString.length() > 0)
        table.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastDateString)); 
      table.setLastUpdatingUser(detailConnector.getIntegerField("last_updated_by"));
      long lastUpdatedLong = Long.parseLong(detailConnector.getField("last_updated_ck"), 16);
      table.setLastUpdatedCk(lastUpdatedLong);
      subdetailConnector.close();
    } 
    detailConnector.close();
    return table;
  }
  
  public String getAssocConfig(int id, String value) {
    String assocConfig = "";
    String subdetail = "SELECT [det_value] FROM vi_Lookup_SubDetail  WHERE field_id = 3 AND sub_value = '" + 
      
      MilestoneHelper.escapeSingleQuotes(value) + "'";
    JdbcConnector subdetailConnector = MilestoneHelper.getConnector(subdetail);
    subdetailConnector.runQuery();
    if (subdetailConnector.more())
      assocConfig = subdetailConnector.getField("det_value"); 
    subdetailConnector.close();
    return assocConfig;
  }
  
  public int getProdType(String value) {
    int productType = -1;
    String subdetail = "select productType from vi_Lookup_Detail where field_id = 3 and value in (  SELECT det_value FROM vi_Lookup_SubDetail WHERE [field_id] = 3 AND sub_value = '" + 
      
      MilestoneHelper.escapeSingleQuotes(value) + "')";
    JdbcConnector subdetailConnector = MilestoneHelper.getConnector(subdetail);
    subdetailConnector.runQuery();
    if (subdetailConnector.more())
      productType = subdetailConnector.getIntegerField("productType"); 
    subdetailConnector.close();
    return productType;
  }
  
  public boolean isDuplicate(int id, String value) {
    String detail = "SELECT [field_id], [value] FROM vi_Lookup_Detail  WHERE [field_id] = " + 
      
      id + 
      " AND value= '" + MilestoneHelper.escapeSingleQuotes(value) + "'";
    JdbcConnector detailConnector = MilestoneHelper.getConnector(detail);
    detailConnector.runQuery();
    boolean isDuplicate = false;
    if (detailConnector.more())
      isDuplicate = true; 
    detailConnector.close();
    return isDuplicate;
  }
  
  public void deleteTable(Table table, int userID) {
    String query = "sp_del_Lookup_SubDetail " + 
      table.getDetail().getId() + "," + 
      "'" + MilestoneHelper.escapeSingleQuotes(table.getDetail().getAbbreviation()) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(table.getSubDetail().getAbbreviation()) + "'";
    log.debug("Table subdetail id = " + table.getDetail().getId());
    log.debug("Delete table subdetail query:\n" + query);
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.setQuery(query);
    connector.runQuery();
    connector.close();
    query = "sp_del_Lookup_Detail " + 
      table.getId() + "," + 
      " '" + MilestoneHelper.escapeSingleQuotes(table.getDetail().getAbbreviation()) + "'";
    log.debug("Delete table detail query:\n" + query);
    connector.setQuery(query);
    connector.runQuery();
    connector.close();
    if (table.getId() == 4)
      updateAssocConfig(table, 3, "", true); 
  }
  
  public Table saveTableDetail(Table table, int userID, boolean isThereSubdetail, String assocConfig) {
    LookupObject detail = table.getDetail();
    PrefixCode subdetail = table.getSubDetail();
    long timestamp = detail.getLastUpdatedCk();
    String query = "sp_upd_Lookup_Detail " + 
      table.getId() + "," + 
      "'" + MilestoneHelper.escapeSingleQuotes(detail.getAbbreviation()) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(detail.getName()) + "'," + 
      userID + "," + 
      timestamp + "," + (
      detail.getInactive() ? 1 : 0) + "," + 
      detail.getProdType() + "," + (
      detail.getIsDigitalEquivalent() ? 1 : 0);
    log.debug("Table save query:\n" + query);
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.setQuery(query);
    connector.runQuery();
    connector.close();
    if (isThereSubdetail) {
      long subtimestamp = subdetail.getLastUpdatedCk();
      String subquery = "sp_upd_Lookup_SubDetail " + 
        table.getId() + "," + 
        "'" + MilestoneHelper.escapeSingleQuotes(subdetail.getDetValue()) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(subdetail.getAbbreviation()) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(subdetail.getName()) + "'," + 
        userID + "," + 
        subtimestamp + "," + (
        detail.getInactive() ? 1 : 0) + "," + 
        detail.getProdType();
      log.debug("Table save sub-query:\n" + subquery);
      JdbcConnector subconnector = MilestoneHelper.getConnector(subquery);
      subconnector.setQuery(subquery);
      subconnector.runQuery();
      subconnector.close();
    } else {
      long subtimestamp = subdetail.getLastUpdatedCk();
      String subquery = "sp_ins_Lookup_SubDetail " + 
        table.getId() + "," + 
        "'" + MilestoneHelper.escapeSingleQuotes(subdetail.getDetValue()) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(subdetail.getAbbreviation()) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(subdetail.getName()) + "'," + 
        userID + "," + (
        detail.getInactive() ? 1 : 0) + "," + 
        detail.getProdType();
      log.debug("Table insert sub-query:\n" + subquery);
      JdbcConnector subconnector = MilestoneHelper.getConnector(subquery);
      subconnector.setQuery(subquery);
      subconnector.runQuery();
      subconnector.close();
    } 
    if (table.getId() == 4)
      updateAssocConfig(table, 3, assocConfig, false); 
    return getInstance().getTableDetail(table.getId(), detail.getAbbreviation(), true);
  }
  
  public Table insertTableDetail(Table table, int userID, String assocConfig) {
    LookupObject detail = table.getDetail();
    PrefixCode subdetail = table.getSubDetail();
    long timestamp = detail.getLastUpdatedCk();
    if (table.getId() == 33) {
      String queryTaskId = "SELECT MAX(CONVERT(int, value)) AS newId FROM Lookup_Detail WHERE field_id = 33";
      JdbcConnector connectorTaskId = MilestoneHelper.getConnector(queryTaskId);
      connectorTaskId.runQuery();
      int newTaskID = connectorTaskId.getInt("newId", -1) + 1;
      connectorTaskId.close();
      detail.setAbbreviation(String.valueOf(newTaskID));
      subdetail.setAbbreviation(String.valueOf(newTaskID));
    } 
    String query = "sp_ins_Lookup_Detail " + 
      table.getId() + "," + 
      "'" + MilestoneHelper.escapeSingleQuotes(detail.getAbbreviation()) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(detail.getName()) + "'," + 
      userID + "," + (
      detail.getInactive() ? 1 : 0) + "," + 
      detail.getProdType() + "," + (
      detail.getIsDigitalEquivalent() ? 1 : 0);
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.setQuery(query);
    connector.runQuery();
    connector.close();
    if (table.getId() != 3) {
      long subtimestamp = subdetail.getLastUpdatedCk();
      String subquery = "sp_ins_Lookup_SubDetail " + 
        table.getId() + "," + 
        "'" + MilestoneHelper.escapeSingleQuotes(subdetail.getDetValue()) + 
        "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(subdetail.getAbbreviation()) + 
        "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(subdetail.getName()) + 
        "'," + 
        userID + "," + (
        detail.getInactive() ? 1 : 0) + "," + 
        detail.getProdType();
      JdbcConnector subconnector = MilestoneHelper.getConnector(subquery);
      subconnector.setQuery(subquery);
      subconnector.runQuery();
      subconnector.close();
    } 
    if (table.getId() == 4)
      updateAssocConfig(table, 3, assocConfig, false); 
    return getInstance().getTableDetail(table.getId(), detail.getAbbreviation(), true);
  }
  
  public void deleteTableDetail(Table table, int userID) {
    String query = "sp_del_Lookup_SubDetail " + 
      table.getSubDetail().getId() + "," + 
      "'" + MilestoneHelper.escapeSingleQuotes(table.getSubDetail().getDetValue()) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(table.getSubDetail().getAbbreviation()) + "'";
    log.debug("Table subdetail id = " + table.getSubDetail().getId());
    log.debug("Delete table subdetail query:\n" + query);
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.setQuery(query);
    connector.runQuery();
    connector.close();
    query = "sp_del_Lookup_Detail " + 
      table.getId() + "," + 
      " '" + MilestoneHelper.escapeSingleQuotes(table.getDetail().getAbbreviation()) + "'";
    log.debug("Delete table detail query:\n" + query);
    connector.setQuery(query);
    connector.runQuery();
    connector.close();
    if (table.getId() == 4)
      updateAssocConfig(table, 3, "", true); 
  }
  
  public void updateAssocConfig(Table table, int configId, String configStr, boolean deleteOnly) {
    String query = "DELETE FROM Lookup_SubDetail  WHERE field_Id = " + 
      configId + " AND sub_value = " + 
      "'" + MilestoneHelper.escapeSingleQuotes(table.getDetail().getAbbreviation()) + "'";
    log.debug("Table subdetail id = " + configId);
    log.debug("Delete table subdetail query:\n" + query);
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.setQuery(query);
    connector.runQuery();
    connector.close();
    if (!deleteOnly) {
      query = "sp_ins_Lookup_SubDetail " + 
        configId + "," + 
        "'" + configStr + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(table.getDetail().getAbbreviation()) + "'" + "," + 
        "'" + MilestoneHelper.escapeSingleQuotes(table.getDetail().getName()) + "'" + "," + (
        table.getDetail().getInactive() ? 1 : 0);
      log.debug("Inserted table subdetail query:\n" + query);
      connector = MilestoneHelper.getConnector(query);
      connector.setQuery(query);
      connector.runQuery();
      connector.close();
    } 
  }
  
  public void setNotepadQuery(Context context, int UserId, Notepad notepad) {
    if (notepad != null) {
      String descriptionSearch = context.getParameter("DescriptionSearch");
      String params = "";
      String tableQuery = "SELECT [field_id], [field_name], [description], [max_length], [add_indicator], [subdetail_indicator]  FROM vi_Lookup_Header";
      if (MilestoneHelper.isStringNotEmpty(descriptionSearch))
        tableQuery = String.valueOf(tableQuery) + 
          MilestoneHelper.addQueryParams(tableQuery, " description " + MilestoneHelper.setWildCardsEscapeSingleQuotes(descriptionSearch)); 
      String order = " ORDER BY [field_name]";
      log.debug("Notepad THTHTHTH tableQuery:\n" + tableQuery);
      notepad.setSearchQuery(tableQuery);
      notepad.setOrderBy(order);
    } 
  }
  
  public void setDetailNotepadQuery(Context context, int fieldId, Notepad notepad) {
    if (notepad != null) {
      String descriptionSearch = context.getParameter("DescriptionSearch");
      String isDigitalEquivalentSearch = context.getParameter("isDigitalEquivalentSearch");
      String detailQuery = "SELECT [field_id], [value], [description], [last_updated_on], [last_updated_by], [last_updated_ck], [inactive] FROM vi_Lookup_Detail  WHERE [field_id] = " + 
        
        fieldId;
      if (MilestoneHelper.isStringNotEmpty(descriptionSearch))
        detailQuery = String.valueOf(detailQuery) + " AND description " + MilestoneHelper.setWildCardsEscapeSingleQuotes(descriptionSearch); 
      log.log("isDigitalEquivalentSearch value =  " + isDigitalEquivalentSearch);
      if (isDigitalEquivalentSearch != null && isDigitalEquivalentSearch.length() > 0)
        detailQuery = String.valueOf(detailQuery) + " AND isDigitalEquivalent = 1"; 
      String order = " ORDER BY [value]";
      notepad.setSearchQuery(detailQuery);
      notepad.setOrderBy(order);
    } 
  }
  
  public Vector getTableNotepadList(int UserId, Notepad notepad) {
    String TableQuery = "";
    if (notepad != null && !notepad.getSearchQuery().equals("")) {
      TableQuery = notepad.getSearchQuery();
      TableQuery = String.valueOf(TableQuery) + notepad.getOrderBy();
    } else {
      TableQuery = "SELECT [field_id], [field_name], [description], [max_length], [add_indicator], [subdetail_indicator]  FROM vi_Lookup_Header ORDER BY [field_name];";
    } 
    Table table = null;
    Vector precache = new Vector();
    JdbcConnector connector = MilestoneHelper.getConnector(TableQuery);
    connector.runQuery();
    while (connector.more()) {
      table = getNotepadTable(connector);
      precache.addElement(table);
      table = null;
      connector.next();
    } 
    connector.close();
    return precache;
  }
  
  public Vector getTableDetailNotepadList(int fieldId, Notepad notepad) {
    String detail;
    if (notepad != null && !notepad.getSearchQuery().equals("")) {
      log.debug("notepad DDDDDDDDDDDDDD using notepad query");
      detail = notepad.getSearchQuery();
      detail = String.valueOf(detail) + notepad.getOrderBy();
    } else {
      log.debug("notepad DDDDDDDDDDDDDD using default query");
      detail = "SELECT [field_id], [value], [description], [last_updated_on], [last_updated_by], [last_updated_ck], [inactive] FROM vi_Lookup_Detail  WHERE [field_id] = " + 
        
        fieldId + 
        " order by [value];";
    } 
    try {
      log.debug("Query from notepad was: " + notepad.getSearchQuery());
    } catch (Exception exception) {}
    log.debug("Get table detail query:\n" + detail);
    JdbcConnector detailConnector = MilestoneHelper.getConnector(detail);
    detailConnector.runQuery();
    Table table = null;
    Vector precache = new Vector();
    while (detailConnector.more()) {
      table = getNotepadTableDetail(detailConnector);
      precache.addElement(table);
      table = null;
      detailConnector.next();
    } 
    detailConnector.close();
    return precache;
  }
  
  public boolean isTimestampValid(Table table) {
    if (table != null) {
      LookupObject detailObj = table.getDetail();
      String timestampQuery = "SELECT [last_updated_ck]  FROM vi_Lookup_Detail  WHERE [field_id] = " + 
        
        table.getId() + 
        " AND value= '" + MilestoneHelper.escapeSingleQuotes(detailObj.getAbbreviation()) + "'";
      JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
      connectorTimestamp.runQuery();
      if (connectorTimestamp.more())
        if (detailObj.getLastUpdatedCk() != Long.parseLong(connectorTimestamp.getField("last_updated_ck", ""), 16)) {
          connectorTimestamp.close();
          return false;
        }  
      connectorTimestamp.close();
      return true;
    } 
    return false;
  }
  
  public boolean isSubDetailTimestampValid(Table table) {
    if (table != null) {
      LookupObject detailObj = table.getDetail();
      PrefixCode subdetail = table.getSubDetail();
      String timestampQuery = "SELECT [last_updated_ck]  FROM vi_Lookup_SubDetail  WHERE [field_id] = " + 
        
        table.getId() + 
        " AND det_value = '" + MilestoneHelper.escapeSingleQuotes(detailObj.getAbbreviation()) + "'";
      JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
      connectorTimestamp.runQuery();
      if (connectorTimestamp.more())
        if (subdetail.getLastUpdatedCk() != Long.parseLong(connectorTimestamp.getField("last_updated_ck", ""), 16))
          return false;  
      connectorTimestamp.close();
      return true;
    } 
    return false;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\TableManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */