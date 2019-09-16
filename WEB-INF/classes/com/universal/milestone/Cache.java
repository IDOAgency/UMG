package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.EnhancedProperties;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Cache;
import com.universal.milestone.CachingComponent;
import com.universal.milestone.Company;
import com.universal.milestone.CorpStructParentNameComparator;
import com.universal.milestone.CorporateStructureObject;
import com.universal.milestone.DatePeriod;
import com.universal.milestone.Day;
import com.universal.milestone.Division;
import com.universal.milestone.Environment;
import com.universal.milestone.Family;
import com.universal.milestone.JdbcConnector;
import com.universal.milestone.Label;
import com.universal.milestone.LabelManager;
import com.universal.milestone.LookupObject;
import com.universal.milestone.MilestoneConstants;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneHelper_2;
import com.universal.milestone.PrefixCode;
import com.universal.milestone.PriceCode;
import com.universal.milestone.SelectionConfiguration;
import com.universal.milestone.SelectionSubConfiguration;
import com.universal.milestone.User;
import com.universal.milestone.UserManager;
import com.universal.milestone.projectSearchSvcClient;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

public class Cache extends CachingComponent implements MilestoneConstants {
  public static final String COMPONENT_CODE = "cach";
  
  protected static Cache cache = null;
  
  protected static Object lockObject = new Object();
  
  protected static Vector cachedCorporateStructure = null;
  
  protected static Hashtable cachedCorporateStructureHash = null;
  
  protected static Vector cachedFamilies = null;
  
  protected static Vector cachedEnvironments = null;
  
  protected static Vector cachedCompanies = null;
  
  protected static Vector cachedDivisions = null;
  
  protected static Vector cachedLabels = null;
  
  protected static Vector sellCodes = null;
  
  protected static Vector sellCodesDPC = null;
  
  protected static Vector selectionConfigs = null;
  
  protected static Vector selectionStatusList = null;
  
  protected static Vector theFamilies = null;
  
  protected static Vector theFamilyList = null;
  
  protected static Vector theEnvironments = null;
  
  protected static Vector theEnvironmentList = null;
  
  protected static Vector theCompanies = null;
  
  protected static Vector theCompanyList = null;
  
  protected static Vector theDivisions = null;
  
  protected static Vector theLabels = null;
  
  protected static Vector releaseTypes = null;
  
  protected static Vector genres = null;
  
  protected static Vector prefixCodes = null;
  
  protected static Vector productCategories = null;
  
  protected static Vector distributionCodes = null;
  
  protected static Vector vendors = null;
  
  protected static Vector umlUsers = null;
  
  protected static Vector datePeriods = null;
  
  protected static Vector reportConfigTypes = null;
  
  protected static Vector reportConfigFormats = null;
  
  protected static Vector reportConfigStatuses = null;
  
  protected static Vector operatingCompanies = null;
  
  protected static Vector musicLines = null;
  
  protected static Vector configCodes = null;
  
  protected static Vector repOwners = null;
  
  protected static Vector repClasses = null;
  
  protected static Vector modifiers = null;
  
  protected static Vector returnCodes = null;
  
  protected static Vector exportFlags = null;
  
  protected static Vector companyCodes = null;
  
  protected static Vector poMergeCodes = null;
  
  protected static Vector guaranteeCodes = null;
  
  protected static Vector loosePickExemptCodes = null;
  
  protected static Vector compilationCodes = null;
  
  protected static Vector impRateCodes = null;
  
  protected static Vector musicTypes = null;
  
  protected static Vector suppliers = null;
  
  protected static Vector narm = null;
  
  protected static Vector pricePoints = null;
  
  protected static Vector importIndicators = null;
  
  protected static Vector bomSuppliers = null;
  
  protected static Vector dayTypes = null;
  
  protected static Vector releaseCompanies = null;
  
  protected static Vector taskAbbreviations = null;
  
  protected static Vector allUsers = null;
  
  protected static Hashtable allUsersHash = null;
  
  protected static Vector labelUsers = null;
  
  protected static Vector formats = null;
  
  protected static Vector priceCodes = null;
  
  protected static Hashtable lookupObjects = new Hashtable();
  
  protected static Vector encryptionFlags = null;
  
  protected static Hashtable userCompaniesTable = null;
  
  protected static Hashtable userEnvironmentsTable = null;
  
  protected static String javaScriptConfigArray = null;
  
  protected static String javaScriptPriceCodeArray = null;
  
  protected static String javaScriptPriceCodeDPCArray = null;
  
  protected static String javaScriptSubConfigArray = null;
  
  protected static String javaScriptPFMConfigArray = null;
  
  protected static ComponentLog log = null;
  
  protected static Vector RefreshServers = null;
  
  protected static Hashtable sortLookupTableByValueDesc = new Hashtable();
  
  protected static Vector cachedEnvironmentsByFamily = null;
  
  protected static Vector cachedLabelsUsed = null;
  
  protected static Hashtable cachedProductRelFamilies = null;
  
  public static final String REFRESH_CACHE_PARAM_TYPE = "cache-refresh-param-type";
  
  public static final String REFRESH_CACHE_PARAM_USERID = "cache-refresh-param-userid";
  
  public static Cache getInstance() {
    if (cache == null)
      cache = new Cache(); 
    return cache;
  }
  
  public Cache() {
    log.debug("Loading Cache.");
    sortLookupTableByValueDesc.put(new Integer(31), "1");
    sortLookupTableByValueDesc.put(new Integer(7), "1");
    sortLookupTableByValueDesc.put(new Integer(29), "1");
    sortLookupTableByValueDesc.put(new Integer(8), "1");
    sortLookupTableByValueDesc.put(new Integer(9), "1");
    sortLookupTableByValueDesc.put(new Integer(30), "1");
    sortLookupTableByValueDesc.put(new Integer(10), "1");
    sortLookupTableByValueDesc.put(new Integer(11), "1");
    sortLookupTableByValueDesc.put(new Integer(36), "1");
    sortLookupTableByValueDesc.put(new Integer(6), "1");
    sortLookupTableByValueDesc.put(new Integer(37), "1");
    sortLookupTableByValueDesc.put(new Integer(12), "1");
    sortLookupTableByValueDesc.put(new Integer(13), "1");
    sortLookupTableByValueDesc.put(new Integer(19), "1");
    sortLookupTableByValueDesc.put(new Integer(56), "1");
    sortLookupTableByValueDesc.put(new Integer(14), "1");
    sortLookupTableByValueDesc.put(new Integer(54), "1");
    sortLookupTableByValueDesc.put(new Integer(57), "1");
    sortLookupTableByValueDesc.put(new Integer(60), "1");
    sortLookupTableByValueDesc.put(new Integer(61), "1");
    log.debug("Retrieved Cache.");
  }
  
  public static void configure(EnhancedProperties props, GeminiApplication application) {
    log = application.getLog("cach");
    int count = props.getIntegerProperty("RefreshServers", 0);
    String strUrl = "";
    for (int i = 1; count >= i; i++) {
      strUrl = props.getProperty("RefreshServer" + i, "");
      if (strUrl.compareToIgnoreCase("") != 0) {
        strUrl = String.valueOf(strUrl) + "?cmd=cache-refresh&cache-refresh-param=";
        if (RefreshServers == null)
          RefreshServers = new Vector(); 
        RefreshServers.add(strUrl);
      } 
    } 
    log.debug("configure() complete.");
  }
  
  public static boolean flushCachedVariableAllUsers(int userId) {
    flushCachedVariableLocalAllUsers(userId);
    RefreshServers("AU&cache-refresh-param-userid=" + 
        userId);
    return true;
  }
  
  public static boolean flushCachedVariableLocalAllUsers(int userId) {
    System.out.println("<<< All Users Cache is being refreshed");
    refreshCacheAllUsers(userId);
    return true;
  }
  
  public static boolean flushCachedVariable(Object object) {
    if (object != null) {
      log.debug("*** flushCachedVariable: " + ((Vector)object).firstElement().getClass().toString().toUpperCase());
      if (((Vector)object).firstElement().getClass().toString().equalsIgnoreCase("CLASS COM.UNIVERSAL.MILESTONE.DAY")) {
        RefreshServers("DT");
      } else if (((Vector)object).firstElement().getClass().toString().equalsIgnoreCase("CLASS COM.UNIVERSAL.MILESTONE.USER")) {
        RefreshServers("AU");
      } 
    } 
    return flushCachedVariableLocal(object);
  }
  
  public static boolean flushCachedVariableLocal(Object object) {
    if (object == null)
      return false; 
    try {
      if (object != null)
        if (((Vector)object).firstElement().getClass().toString().equalsIgnoreCase("CLASS COM.UNIVERSAL.MILESTONE.USER")) {
          allUsers = null;
        } else if (((Vector)object).firstElement().getClass().toString().equalsIgnoreCase("CLASS COM.UNIVERSAL.MILESTONE.DAY")) {
          dayTypes = null;
        } else {
          object = null;
        }  
      return true;
    } catch (Exception e) {
      if (object == null) {
        System.out.println("<<< Cache() Unable to flush object ");
      } else {
        System.out.println("<<< Cache() Unable to flush object " + ((Vector)object).firstElement().getClass().toString());
      } 
      return false;
    } 
  }
  
  public static void flushTableVariables(String tableType) {
    flushTableVariablesLocal(tableType);
    RefreshServers("TV&cache-refresh-param-type=" + 
        tableType);
  }
  
  public static void flushTableVariablesLocal(String tableType) {
    System.out.println("<<< flushTableVariablesLocal() " + tableType);
    if (tableType.equals(String.valueOf(1)) || tableType.equals(""))
      productCategories = null; 
    if (tableType.equals(String.valueOf(40)) || tableType.equals(""))
      releaseCompanies = null; 
    if (tableType.equals(String.valueOf(2)) || tableType.equals(""))
      releaseTypes = null; 
    if (tableType.equals(String.valueOf(15)) || tableType.equals(""))
      selectionStatusList = null; 
    if (tableType.equals(String.valueOf(3)) || 
      tableType.equals(String.valueOf(4)) || 
      tableType.equals("")) {
      selectionConfigs = null;
      javaScriptConfigArray = null;
      javaScriptSubConfigArray = null;
    } 
    if (tableType.equals("")) {
      javaScriptPriceCodeArray = null;
      javaScriptPriceCodeDPCArray = null;
    } 
    if (tableType.equals(String.valueOf(17)) || tableType.equals(""))
      genres = null; 
    if (tableType.equals(String.valueOf(22)) || tableType.equals(""))
      vendors = null; 
    if (tableType.equals(String.valueOf(27)) || tableType.equals(""))
      prefixCodes = null; 
    if (tableType.equals(String.valueOf(48)) || tableType.equals(""))
      reportConfigFormats = null; 
    if (tableType.equals(String.valueOf(51)) || tableType.equals(""))
      reportConfigStatuses = null; 
    if (tableType.equals(String.valueOf(31)) || tableType.equals(""))
      operatingCompanies = null; 
    if (tableType.equals(String.valueOf(7)) || tableType.equals(""))
      musicLines = null; 
    if (tableType.equals(String.valueOf(29)) || tableType.equals(""))
      configCodes = null; 
    if (tableType.equals(String.valueOf(8)) || tableType.equals(""))
      repOwners = null; 
    if (tableType.equals(String.valueOf(9)) || tableType.equals(""))
      repClasses = null; 
    if (tableType.equals(String.valueOf(30)) || tableType.equals(""))
      modifiers = null; 
    if (tableType.equals(String.valueOf(10)) || tableType.equals(""))
      returnCodes = null; 
    if (tableType.equals(String.valueOf(11)) || tableType.equals(""))
      exportFlags = null; 
    if (tableType.equals(String.valueOf(36)) || tableType.equals(""))
      companyCodes = null; 
    if (tableType.equals(String.valueOf(6)) || tableType.equals(""))
      poMergeCodes = null; 
    if (tableType.equals(String.valueOf(37)) || tableType.equals(""))
      guaranteeCodes = null; 
    if (tableType.equals(String.valueOf(60)) || tableType.equals(""))
      loosePickExemptCodes = null; 
    if (tableType.equals(String.valueOf(57)) || tableType.equals(""))
      compilationCodes = null; 
    if (tableType.equals(String.valueOf(12)) || tableType.equals(""))
      impRateCodes = null; 
    if (tableType.equals(String.valueOf(13)) || tableType.equals(""))
      musicTypes = null; 
    if (tableType.equals(String.valueOf(19)) || tableType.equals(""))
      suppliers = null; 
    if (tableType.equals(String.valueOf(56)) || tableType.equals(""))
      narm = null; 
    if (tableType.equals(String.valueOf(58)) || tableType.equals(""))
      formats = null; 
    if (tableType.equals(String.valueOf(14)) || tableType.equals(""))
      pricePoints = null; 
    if (tableType.equals(String.valueOf(54)) || tableType.equals(""))
      importIndicators = null; 
    if (tableType.equals(String.valueOf(33)) || tableType.equals(""))
      taskAbbreviations = null; 
  }
  
  public static void flushCachedVariables() {
    flushCorporateStructure();
    flushSellCode();
    sellCodes = null;
    sellCodesDPC = null;
    priceCodes = null;
    lookupObjects = null;
    selectionConfigs = null;
    releaseTypes = null;
    genres = null;
    prefixCodes = null;
    productCategories = null;
    distributionCodes = null;
    vendors = null;
    umlUsers = null;
    datePeriods = null;
    reportConfigTypes = null;
    reportConfigFormats = null;
    reportConfigStatuses = null;
    operatingCompanies = null;
    musicLines = null;
    configCodes = null;
    repOwners = null;
    repClasses = null;
    modifiers = null;
    returnCodes = null;
    exportFlags = null;
    companyCodes = null;
    poMergeCodes = null;
    guaranteeCodes = null;
    compilationCodes = null;
    impRateCodes = null;
    musicTypes = null;
    suppliers = null;
    narm = null;
    pricePoints = null;
    importIndicators = null;
    javaScriptConfigArray = null;
    javaScriptPriceCodeArray = null;
    javaScriptPriceCodeDPCArray = null;
    bomSuppliers = null;
    theCompanyList = null;
    dayTypes = null;
    releaseCompanies = null;
    javaScriptSubConfigArray = null;
    taskAbbreviations = null;
    allUsers = null;
    allUsersHash = null;
    labelUsers = null;
    formats = null;
  }
  
  public static void flushSellCode() {
    flushSellCodeLocal();
    RefreshServers("PC");
  }
  
  public static void flushSellCodeLocal() {
    sellCodes = null;
    sellCodesDPC = null;
    javaScriptPriceCodeArray = null;
    javaScriptPriceCodeDPCArray = null;
    priceCodes = null;
  }
  
  public static void flushDatePeriodsLocal() { datePeriods = null; }
  
  public static void flushDatePeriods() {
    flushDatePeriodsLocal();
    RefreshServers("DP");
  }
  
  public static void flushDayTypesLocal() { dayTypes = null; }
  
  public static void flushDayTypes() {
    flushDayTypesLocal();
    RefreshServers("DT");
  }
  
  public static void flushCorporateStructure() {
    log.debug("Fushing all flushCorporateStructure and refreshing.");
    flushCorporateStructureLocal();
    RefreshServers("CS");
  }
  
  public static void flushCorporateStructureLocal() {
    log.debug("Fushing the local flushCorporateStructure.");
    cachedCorporateStructure = null;
    cachedCorporateStructureHash = null;
    projectSearchSvcClient.cachedLabelData = null;
    cachedFamilies = null;
    cachedEnvironments = null;
    cachedCompanies = null;
    cachedDivisions = null;
    cachedLabels = null;
    theFamilies = null;
    theFamilyList = null;
    theEnvironments = null;
    theEnvironmentList = null;
    theCompanies = null;
    theDivisions = null;
    theLabels = null;
    theCompanyList = null;
    projectSearchSvcClient.cachedBasicClient = null;
  }
  
  public static void flushUsedLabels() {
    RefreshServers("UL");
    cachedLabelsUsed = null;
    cachedProductRelFamilies = null;
    getProductReleasingFamilies();
  }
  
  public static void flushUsedLabelsLocal() {
    cachedLabelsUsed = null;
    cachedProductRelFamilies = null;
    getProductReleasingFamilies();
  }
  
  public static Vector getUsedLabels() {
    synchronized (lockObject) {
      if (cachedLabelsUsed == null) {
        cachedLabelsUsed = new Vector();
        JdbcConnector connector = MilestoneHelper.getConnector("SELECT DISTINCT s.*  FROM vi_Structure s, vi_release_header rh  where s.structure_id = rh.label_id ");
        connector.runQuery();
        while (connector.more()) {
          Label label = new Label();
          label.initializeByVariables(connector);
          cachedLabelsUsed.add(label);
          connector.next();
        } 
        connector.close();
      } 
      return cachedLabelsUsed;
    } 
  }
  
  public static Hashtable getProductReleasingFamilies() {
    synchronized (lockObject) {
      if (cachedProductRelFamilies == null) {
        cachedProductRelFamilies = new Hashtable();
        JdbcConnector connector = MilestoneHelper.getConnector("sp_get_ProductReleasingFamilies");
        connector.runQuery();
        while (connector.more()) {
          int cso_id = connector.getInt("cso_id", -1);
          int release_family_id = connector.getInt("release_family_id", -1);
          if (!cachedProductRelFamilies.containsKey(Integer.toString(cso_id))) {
            Vector prodRelFamilies = new Vector();
            prodRelFamilies.add(Integer.toString(release_family_id));
            cachedProductRelFamilies.put(Integer.toString(cso_id), prodRelFamilies);
          } else {
            Vector prodRelFamilies = (Vector)cachedProductRelFamilies.get(Integer.toString(cso_id));
            prodRelFamilies.add(Integer.toString(release_family_id));
          } 
          connector.next();
        } 
        connector.close();
      } 
    } 
    return cachedProductRelFamilies;
  }
  
  public static Vector getCorporateStructure() {
    synchronized (lockObject) {
      if (cachedCorporateStructure == null) {
        log.debug("creating new CSO cache.");
        cachedCorporateStructure = new Vector();
        JdbcConnector connector = MilestoneHelper.getConnector("SELECT * FROM vi_Structure ORDER BY name;");
        connector.runQuery();
        while (connector.more()) {
          Label label1;
          boolean active = false;
          int type = connector.getInt("type");
          int id = connector.getInt("structure_id");
          int archimedesId = connector.getInt("archimedes_id", -1);
          int sort = connector.getInt("sort");
          if (sort == 0)
            active = true; 
          Calendar lastUpdatedOn = Calendar.getInstance();
          lastUpdatedOn.setTime(connector.getTimestamp("last_updated_on"));
          if (type == 1) {
            Family family = new Family();
            MilestoneHelper_2.initializeCorporateStructure(family, connector);
            family.setArchimedesId(archimedesId);
            family.setActive(active);
            family.setLastUpdateDate(lastUpdatedOn);
            label1 = family;
          } else if (type == 5) {
            Environment env = new Environment();
            MilestoneHelper_2.initializeCorporateStructure(env, connector);
            env.distribution = connector.getInt("region");
            env.calendarGroup = connector.getInt("grouping");
            env.setArchimedesId(archimedesId);
            env.setActive(active);
            env.setLastUpdateDate(lastUpdatedOn);
            label1 = env;
          } else if (type == 2) {
            Company company = new Company();
            MilestoneHelper_2.initializeCorporateStructure(company, connector);
            company.distribution = connector.getInt("region");
            company.calendarGroup = connector.getInt("grouping");
            company.setActive(active);
            company.setLastUpdateDate(lastUpdatedOn);
            label1 = company;
          } else if (type == 3) {
            Division division = new Division();
            MilestoneHelper_2.initializeCorporateStructure(division, connector);
            division.setActive(active);
            division.setLastUpdateDate(lastUpdatedOn);
            label1 = division;
          } else {
            Label label = new Label();
            MilestoneHelper_2.initializeCorporateStructure(label, connector);
            label.distribution = connector.getInt("region");
            label.setArchimedesId(archimedesId);
            label.setActive(active);
            LabelManager.getArchimedes(label, Boolean.valueOf(true));
            label.setLastUpdateDate(lastUpdatedOn);
            label1 = label;
          } 
          cachedCorporateStructure.add(label1);
          connector.next();
        } 
        connector.close();
        for (int i = 0; i < cachedCorporateStructure.size(); i++) {
          CorporateStructureObject current = (CorporateStructureObject)cachedCorporateStructure.elementAt(i);
          int parentID = current.getParentID();
          if (parentID > 0)
            for (int j = 0; j < cachedCorporateStructure.size(); j++) {
              CorporateStructureObject parent = (CorporateStructureObject)cachedCorporateStructure.elementAt(j);
              if (parent.getStructureID() == parentID)
                try {
                  current.setParent(parent);
                  parent.addChild(current);
                } catch (Exception exc) {
                  log.debug("Warning: parent of " + current + " is not appropriate type.");
                }  
            }  
        } 
        buildCachedCorporateStructrueHash();
      } 
    } 
    return cachedCorporateStructure;
  }
  
  public static void buildCachedCorporateStructrueHash() {
    cachedCorporateStructureHash = new Hashtable();
    for (i = 0; i < cachedCorporateStructure.size(); i++) {
      CorporateStructureObject cso = (CorporateStructureObject)cachedCorporateStructure.elementAt(i);
      cachedCorporateStructureHash.put(new Integer(cso.getIdentity()), cso);
    } 
  }
  
  public static void initializeUnusedLabels() {
    found = false;
    CorporateStructureObject current = null;
    for (int i = 0; i < cachedCorporateStructure.size(); i++) {
      current = (CorporateStructureObject)cachedCorporateStructure.elementAt(i);
      found = false;
      if (current.getStructureType() == 1) {
        Vector currentFamiliesEnvs = current.getChildren();
        Environment childEnv = null;
        for (int e = 0; e < currentFamiliesEnvs.size(); e++) {
          childEnv = (Environment)currentFamiliesEnvs.elementAt(e);
          Vector currentEnvironmentCompanies = childEnv.getChildren();
          Company childCompany = null;
          for (int c = 0; c < currentEnvironmentCompanies.size(); c++) {
            childCompany = (Company)currentEnvironmentCompanies.elementAt(c);
            Vector currentCompaniesChildren = childCompany.getChildren();
            Division childDivision = null;
            for (int d = 0; d < currentCompaniesChildren.size(); d++) {
              childDivision = (Division)currentCompaniesChildren.elementAt(d);
              Vector currentDivisionChildren = childDivision.getChildren();
              Label childLabel = null;
              found = false;
              for (int l = 0; l < currentDivisionChildren.size(); l++) {
                childLabel = (Label)currentDivisionChildren.elementAt(l);
                Label usedLabel = null;
                Vector labelsUsed = getUsedLabels();
                childLabel.setIsUsed(false);
                for (int u = 0; u < labelsUsed.size(); u++) {
                  usedLabel = (Label)labelsUsed.elementAt(u);
                  if (childLabel.getStructureID() == usedLabel.getStructureID()) {
                    childLabel.setIsUsed(true);
                    found = true;
                    break;
                  } 
                } 
              } 
              if (!found) {
                if (!childDivision.getIsUsed())
                  childDivision.setIsUsed(false); 
                if (!childCompany.getIsUsed())
                  childCompany.setIsUsed(false); 
                if (!childEnv.getIsUsed())
                  childEnv.setIsUsed(false); 
                if (!current.getIsUsed())
                  current.setIsUsed(false); 
              } else {
                childDivision.setIsUsed(true);
                childCompany.setIsUsed(true);
                childEnv.setIsUsed(true);
                current.setIsUsed(true);
              } 
            } 
          } 
        } 
      } 
    } 
  }
  
  public static Vector getFamilies() {
    if (cachedFamilies == null) {
      log.debug("Retrieved new Family cache.");
      cachedFamilies = new Vector();
      csos = getCorporateStructure();
      for (int i = 0; i < csos.size(); i++) {
        CorporateStructureObject cso = (CorporateStructureObject)csos.elementAt(i);
        if (cso instanceof Family)
          cachedFamilies.addElement(cso); 
      } 
    } 
    return cachedFamilies;
  }
  
  public static Vector getCompanies() {
    if (cachedCompanies == null) {
      log.debug("creating new Company cache.");
      cachedCompanies = new Vector();
      csos = getCorporateStructure();
      for (int i = 0; i < csos.size(); i++) {
        CorporateStructureObject cso = (CorporateStructureObject)csos.elementAt(i);
        if (cso instanceof Company && cso.getParent() != null)
          cachedCompanies.addElement(cso); 
      } 
    } 
    return cachedCompanies;
  }
  
  public static Vector getDivisions() {
    if (cachedDivisions == null) {
      log.debug("Creating new Division cache.");
      cachedDivisions = new Vector();
      csos = getCorporateStructure();
      for (int i = 0; i < csos.size(); i++) {
        CorporateStructureObject cso = (CorporateStructureObject)csos.elementAt(i);
        if (cso instanceof Division && cso.getParent() != null)
          cachedDivisions.addElement(cso); 
      } 
    } 
    return cachedDivisions;
  }
  
  public static Vector getLabels() {
    if (cachedLabels == null) {
      log.debug("creating new Label cache.");
      cachedLabels = new Vector();
      csos = getCorporateStructure();
      for (int i = 0; i < csos.size(); i++) {
        CorporateStructureObject cso = (CorporateStructureObject)csos.elementAt(i);
        if (cso instanceof Label && cso.getParent() != null)
          cachedLabels.addElement(cso); 
      } 
    } 
    return cachedLabels;
  }
  
  public static Vector getSellCodes() {
    if (sellCodes == null) {
      query = "SELECT Sell_Code FROM vi_Price_Code where isDigital is null or isDigital = 0 ORDER BY Sell_Code;";
      sellCodes = getCachedVectorOfStrings(sellCodes, query, "Sell_Code");
    } 
    return sellCodes;
  }
  
  public static Vector getSellCodesDPC() {
    if (sellCodesDPC == null) {
      log.debug("Retrieved SellCodes.");
      query = "SELECT Sell_Code FROM vi_Price_Code where isDigital = 1 ORDER BY Sell_Code;";
      sellCodesDPC = getCachedVectorOfStrings(sellCodesDPC, query, "Sell_Code");
    } 
    return sellCodesDPC;
  }
  
  public static Vector getPriceCodes() {
    if (priceCodes == null) {
      log.debug("Retrieved PriceCodes.");
      precache = new Vector();
      String priceCodeQuery = "SELECT * FROM vi_Price_Code";
      JdbcConnector connector = MilestoneHelper.getConnector(priceCodeQuery);
      connector.runQuery();
      PriceCode priceCode = null;
      while (connector.more()) {
        priceCode = new PriceCode();
        priceCode.setSellCode(connector.getField("sell_code"));
        priceCode.setRetailCode(connector.getField("retail_code", ""));
        priceCode.setUnits(connector.getIntegerField("units"));
        priceCode.setPricePoint(connector.getField("price_point", ""));
        priceCode.setDescription(connector.getField("description", ""));
        priceCode.setUnitCost(connector.getIntegerField("units"));
        priceCode.setTotalCost(connector.getFloat("total_cost"));
        priceCode.setIsDigital(connector.getBoolean("isDigital"));
        precache.add(priceCode);
        connector.next();
      } 
      connector.close();
      priceCodes = precache;
    } 
    return priceCodes;
  }
  
  public static Vector getSelectionConfigs() {
    if (selectionConfigs == null) {
      query = "SELECT * FROM vi_Lookup_Detail WHERE Field_id = 3 ORDER BY value;";
      JdbcConnector connector = MilestoneHelper.getConnector(query);
      connector.runQuery();
      Vector configs = new Vector();
      while (connector.more()) {
        SelectionConfiguration config = new SelectionConfiguration(
            connector.getField("value", ""), 
            connector.getField("description", ""), 
            new Vector(), 
            connector.getBoolean("inactive"), 
            connector.getIntegerField("productType"), 
            connector.getIntegerField("newBundle"));
        configs.addElement(config);
        connector.next();
      } 
      connector.close();
      String parentquery = "SELECT * FROM vi_Lookup_Subdetail WHERE field_id = 4 ORDER BY field_id, det_value, description;";
      JdbcConnector connector2 = MilestoneHelper.getScrollableConnector(parentquery);
      connector2.runQuery();
      query = "SELECT * FROM vi_Lookup_Subdetail WHERE field_id = 3 ORDER BY det_value, description;";
      connector = MilestoneHelper.getScrollableConnector(query);
      connector.runQuery();
      if (connector.more()) {
        log.debug("Loading subdetails. " + connector.getRowCount());
        for (int i = 0; i < connector.getRowCount(); i++) {
          String currentConfig = "";
          if (connector.more() && connector.getIntegerField("field_id") == 3) {
            currentConfig = connector.getField("det_value", "");
            Vector subConfigs = new Vector();
            log.debug("  processing subdetails. " + currentConfig);
            while (connector.more() && currentConfig.equals(connector.getField("det_value", ""))) {
              String abbrev = connector.getField("sub_value", "");
              String name = connector.getField("description", "");
              SelectionSubConfiguration subConfig = new SelectionSubConfiguration(abbrev, name);
              connector2.first();
              boolean foundParent = false;
              while (connector2.more() && !foundParent) {
                if (connector2.getField("det_value").equals(subConfig.getSelectionSubConfigurationAbbreviation())) {
                  if (connector2.getField("description").equalsIgnoreCase("Y")) {
                    subConfig.setParent(true);
                    foundParent = true;
                  } 
                  subConfig.setInactive(connector2.getBoolean("inactive"));
                  subConfig.setProdType(connector2.getIntegerField("productType"));
                } 
                connector2.next();
              } 
              subConfigs.addElement(subConfig);
              connector.next();
            } 
            SelectionConfiguration config = (SelectionConfiguration)configs.elementAt(i);
            if (currentConfig.equals(config.getSelectionConfigurationAbbreviation())) {
              config.setSubConfigurations(subConfigs);
            } else {
              log.log("There was problem.  Some subconfigs found did not have a matching parent configuration");
            } 
          } else {
            connector.next();
          } 
        } 
      } 
      selectionConfigs = configs;
      connector.close();
      connector2.close();
    } 
    return selectionConfigs;
  }
  
  public static Vector getCompanyList() { return getCompanies(); }
  
  public static Vector getReleaseTypes() {
    if (releaseTypes == null) {
      preCache = new Vector();
      String className = "com.universal.milestone.ReleaseType";
      if (lookupObjects.containsKey(String.valueOf(2)))
        lookupObjects.remove(String.valueOf(2)); 
      preCache = getLookupDetailValuesFromDatabase(className, 2);
      releaseTypes = preCache;
    } 
    return releaseTypes;
  }
  
  public static Vector getGenres() {
    if (genres == null) {
      vGenres = new Vector();
      String className = "com.universal.milestone.Genre";
      if (lookupObjects.containsKey(String.valueOf(17)))
        lookupObjects.remove(String.valueOf(17)); 
      vGenres = getLookupDetailValuesFromDatabase(className, 17);
      genres = vGenres;
    } 
    return genres;
  }
  
  public static Vector getProductCategories() {
    if (productCategories == null) {
      className = "com.universal.milestone.ProductCategory";
      if (lookupObjects.containsKey(String.valueOf(1)))
        lookupObjects.remove(String.valueOf(1)); 
      Vector preCache = getLookupDetailValuesFromDatabase(className, 1);
      productCategories = preCache;
    } 
    return productCategories;
  }
  
  public static Vector getProductCategories(int type) {
    Vector prodCats = new Vector();
    Vector allProdCats = getProductCategories();
    for (int i = 0; i < allProdCats.size(); i++) {
      if (((LookupObject)allProdCats.get(i)).getProdType() == type || ((LookupObject)allProdCats.get(i)).getProdType() == 2)
        prodCats.add(allProdCats.get(i)); 
    } 
    return prodCats;
  }
  
  public static Vector getSelectionStatusList() {
    if (selectionStatusList == null) {
      className = "com.universal.milestone.SelectionStatus";
      if (lookupObjects.containsKey(String.valueOf(15)))
        lookupObjects.remove(String.valueOf(15)); 
      Vector preCache = getLookupDetailValuesFromDatabase(className, 15);
      selectionStatusList = preCache;
    } 
    return selectionStatusList;
  }
  
  public static Vector getPrefixCodes() {
    if (prefixCodes == null) {
      query = "SELECT * FROM vi_lookup_subdetail WHERE Field_id = 27 ORDER BY det_value";
      JdbcConnector connector = MilestoneHelper.getConnector(query);
      connector.runQuery();
      Vector prefixs = new Vector();
      while (connector.more()) {
        String abbrev = connector.getField("det_value");
        int subValue = connector.getIntegerField("sub_value");
        boolean inactive = connector.getBoolean("inactive");
        int prodType = connector.getIntegerField("productType");
        prefixs.addElement(new PrefixCode(abbrev, subValue, inactive, prodType));
        connector.next();
      } 
      connector.close();
      prefixCodes = prefixs;
    } 
    return prefixCodes;
  }
  
  public static Vector getTaskAbbreviations() {
    if (taskAbbreviations == null) {
      if (lookupObjects.containsKey(String.valueOf(33)))
        lookupObjects.remove(String.valueOf(33)); 
      taskAbbreviations = getLookupDetailValuesFromDatabase(33);
    } 
    return taskAbbreviations;
  }
  
  public static Vector getVendors() {
    if (vendors == null) {
      classObj = null;
      try {
        classObj = Class.forName("com.universal.milestone.LookupObject");
      } catch (ClassNotFoundException cnfexc) {
        log.log("ClassNotFoundException: " + cnfexc);
      } 
      String query = "SELECT value, a.description as 'desc', b.description as 'subValue' ,a.inactive  FROM vi_lookup_detail a  LEFT JOIN vi_lookup_subdetail b on a.[value] = b.[det_value]  and a.[field_id] = b.[field_id]  WHERE a.[Field_id] = 22 ORDER BY a.[description];";
      JdbcConnector connector = MilestoneHelper.getConnector(query);
      connector.runQuery();
      Vector lookupVector = new Vector();
      while (connector.more()) {
        try {
          LookupObject lookupObject = (LookupObject)classObj.newInstance();
          lookupObject.setAbbreviation(connector.getField("value", ""));
          lookupObject.setName(connector.getField("desc", ""));
          lookupObject.setSubValue(connector.getField("subValue", ""));
          lookupObject.setInactive(connector.getBoolean("inactive"));
          lookupVector.addElement(lookupObject);
          lookupObject = null;
        } catch (InstantiationException e) {
          log.log("InstantiationException");
        } catch (IllegalAccessException iae) {
          log.log("IllegalAccessException");
        } 
        connector.next();
      } 
      connector.close();
      vendors = lookupVector;
    } 
    return vendors;
  }
  
  public static Vector getDistributionCodes() {
    if (distributionCodes == null) {
      if (lookupObjects.containsKey(String.valueOf(39)))
        lookupObjects.remove(String.valueOf(39)); 
      distributionCodes = getLookupDetailValuesFromDatabase(39);
    } 
    return distributionCodes;
  }
  
  public static Vector getReleaseCompanies() {
    if (releaseCompanies == null) {
      if (lookupObjects.containsKey(String.valueOf(40)))
        lookupObjects.remove(String.valueOf(40)); 
      releaseCompanies = getLookupDetailValuesFromDatabase(40);
    } 
    return releaseCompanies;
  }
  
  public static Vector getReportConfigTypes() {
    if (reportConfigTypes == null) {
      if (lookupObjects.containsKey(String.valueOf(49)))
        lookupObjects.remove(String.valueOf(49)); 
      reportConfigTypes = getLookupDetailValuesFromDatabase(49);
    } 
    return reportConfigTypes;
  }
  
  public static Vector getReportConfigFormats() {
    if (reportConfigFormats == null) {
      if (lookupObjects.containsKey(String.valueOf(48)))
        lookupObjects.remove(String.valueOf(48)); 
      reportConfigFormats = getLookupDetailValuesFromDatabase(48);
    } 
    return reportConfigFormats;
  }
  
  public static Vector getReportConfigStatuses() {
    if (reportConfigStatuses == null) {
      if (lookupObjects.containsKey(String.valueOf(51)))
        lookupObjects.remove(String.valueOf(51)); 
      reportConfigStatuses = getLookupDetailValuesFromDatabase(51);
    } 
    return reportConfigStatuses;
  }
  
  public static Vector getOperatingCompanies() {
    if (operatingCompanies == null) {
      if (lookupObjects.containsKey(String.valueOf(31)))
        lookupObjects.remove(String.valueOf(31)); 
      operatingCompanies = getLookupDetailValuesFromDatabase(31);
    } 
    return operatingCompanies;
  }
  
  public static Vector getMusicLines() {
    if (musicLines == null) {
      if (lookupObjects.containsKey(String.valueOf(7)))
        lookupObjects.remove(String.valueOf(7)); 
      musicLines = getLookupDetailValuesFromDatabase(7);
    } 
    return musicLines;
  }
  
  public static Vector getConfigCodes() {
    if (configCodes == null) {
      if (lookupObjects.containsKey(String.valueOf(29)))
        lookupObjects.remove(String.valueOf(29)); 
      configCodes = getLookupDetailValuesFromDatabase(29);
    } 
    return configCodes;
  }
  
  public static Vector getRepertoireClasses() {
    if (repClasses == null) {
      if (lookupObjects.containsKey(String.valueOf(9)))
        lookupObjects.remove(String.valueOf(9)); 
      repClasses = getLookupDetailValuesFromDatabase(9);
    } 
    return repClasses;
  }
  
  public static Vector getRepertoireOwners() {
    if (repOwners == null) {
      if (lookupObjects.containsKey(String.valueOf(8)))
        lookupObjects.remove(String.valueOf(8)); 
      repOwners = getLookupDetailValuesFromDatabase(8);
    } 
    return repOwners;
  }
  
  public static Vector getModifiers() {
    if (modifiers == null) {
      if (lookupObjects.containsKey(String.valueOf(30)))
        lookupObjects.remove(String.valueOf(30)); 
      modifiers = getLookupDetailValuesFromDatabase(30);
    } 
    return modifiers;
  }
  
  public static Vector getReturnCodes() {
    if (returnCodes == null) {
      if (lookupObjects.containsKey(String.valueOf(10)))
        lookupObjects.remove(String.valueOf(10)); 
      returnCodes = getLookupDetailValuesFromDatabase(10);
    } 
    return returnCodes;
  }
  
  public static Vector getExportFlags() {
    if (exportFlags == null) {
      if (lookupObjects.containsKey(String.valueOf(11)))
        lookupObjects.remove(String.valueOf(11)); 
      exportFlags = getLookupDetailValuesFromDatabase(11);
    } 
    return exportFlags;
  }
  
  public static Vector getCompanyCodes() {
    if (companyCodes == null) {
      if (lookupObjects.containsKey(String.valueOf(36)))
        lookupObjects.remove(String.valueOf(36)); 
      companyCodes = getLookupDetailValuesFromDatabase(36);
    } 
    return companyCodes;
  }
  
  public static Vector getPoMergeCodes() {
    if (poMergeCodes == null) {
      if (lookupObjects.containsKey(String.valueOf(6)))
        lookupObjects.remove(String.valueOf(6)); 
      poMergeCodes = getLookupDetailValuesFromDatabase(6);
    } 
    return poMergeCodes;
  }
  
  public static Vector getGuaranteeCodes() {
    if (guaranteeCodes == null) {
      if (lookupObjects.containsKey(String.valueOf(37)))
        lookupObjects.remove(String.valueOf(37)); 
      guaranteeCodes = getLookupDetailValuesFromDatabase(37);
    } 
    return guaranteeCodes;
  }
  
  public static Vector getLoosePickExempt() {
    if (loosePickExemptCodes == null) {
      if (lookupObjects.containsKey(String.valueOf(60)))
        lookupObjects.remove(String.valueOf(60)); 
      loosePickExemptCodes = getLookupDetailValuesFromDatabase(60);
    } 
    return loosePickExemptCodes;
  }
  
  public static Vector getCompilationCodes() {
    if (compilationCodes == null) {
      if (lookupObjects.containsKey(String.valueOf(57)))
        lookupObjects.remove(String.valueOf(57)); 
      compilationCodes = getLookupDetailValuesFromDatabase(57);
    } 
    return compilationCodes;
  }
  
  public static Vector getImpRateCodes() {
    if (impRateCodes == null) {
      if (lookupObjects.containsKey(String.valueOf(12)))
        lookupObjects.remove(String.valueOf(12)); 
      impRateCodes = getLookupDetailValuesFromDatabase(12);
    } 
    return impRateCodes;
  }
  
  public static Vector getMusicTypes() {
    if (musicTypes == null) {
      vMusicTypes = new Vector();
      String className = "com.universal.milestone.Genre";
      if (lookupObjects.containsKey(String.valueOf(13)))
        lookupObjects.remove(String.valueOf(13)); 
      vMusicTypes = getLookupDetailValuesFromDatabase(className, 13);
      musicTypes = vMusicTypes;
    } 
    return musicTypes;
  }
  
  public static Vector getSuppliers() {
    if (suppliers == null) {
      if (lookupObjects.containsKey(String.valueOf(19)))
        lookupObjects.remove(String.valueOf(19)); 
      suppliers = getLookupDetailValuesFromDatabase(19);
    } 
    return suppliers;
  }
  
  public static Vector getNarmExtracts() {
    if (narm == null) {
      if (lookupObjects.containsKey(String.valueOf(56)))
        lookupObjects.remove(String.valueOf(56)); 
      narm = getLookupDetailValuesFromDatabase(56);
    } 
    return narm;
  }
  
  public static Vector getEncryptionFlags() {
    if (encryptionFlags == null) {
      if (lookupObjects.containsKey(String.valueOf(61)))
        lookupObjects.remove(String.valueOf(61)); 
      encryptionFlags = getLookupDetailValuesFromDatabase(61);
    } 
    return encryptionFlags;
  }
  
  public static Vector getPricePoints() {
    if (pricePoints == null) {
      if (lookupObjects.containsKey(String.valueOf(14)))
        lookupObjects.remove(String.valueOf(14)); 
      pricePoints = getLookupDetailValuesFromDatabase(14);
    } 
    return pricePoints;
  }
  
  public static Vector getImportIndicators() {
    if (importIndicators == null) {
      if (lookupObjects.containsKey(String.valueOf(54)))
        lookupObjects.remove(String.valueOf(54)); 
      importIndicators = getLookupDetailValuesFromDatabase(54);
    } 
    return importIndicators;
  }
  
  public static Vector getFormats() {
    if (formats == null) {
      if (lookupObjects.containsKey(String.valueOf(58)))
        lookupObjects.remove(String.valueOf(58)); 
      formats = getLookupDetailValuesFromDatabase(58);
    } 
    return formats;
  }
  
  public static Vector getUmlUsers() {
    if (umlUsers == null) {
      families = getFamilies();
      int umlId = -1;
      for (int i = 0; i < families.size(); i++) {
        CorporateStructureObject family = (CorporateStructureObject)families.get(i);
        String familyName = family.getName().trim();
        if (familyName.equalsIgnoreCase("UML")) {
          umlId = family.getStructureID();
          break;
        } 
      } 
      Vector precache = new Vector();
      Vector users = getAllUsers();
      for (int i = 0; i < users.size(); i++) {
        User user = (User)users.get(i);
        if (user.getEmployedBy() == umlId) {
          boolean uniqueUMLContact = true;
          for (int j = 0; j < precache.size(); j++) {
            User umlusr = (User)precache.get(j);
            String userName = user.getName().trim();
            String umlusrName = umlusr.getName().trim();
            if (userName.equalsIgnoreCase(umlusrName))
              uniqueUMLContact = false; 
          } 
          if (uniqueUMLContact)
            precache.add(user); 
        } 
      } 
      umlUsers = precache;
    } 
    return umlUsers;
  }
  
  public static Hashtable getAllUsersHash() {
    if (allUsers == null)
      getAllUsers(); 
    return allUsersHash;
  }
  
  public static Vector getAllUsers() {
    synchronized (lockObject) {
      if (allUsers == null) {
        allUsersHash = null;
        allUsersHash = new Hashtable();
        String query = "sp_get_User_All";
        JdbcConnector connector = MilestoneHelper.getConnector(query);
        connector.runQuery();
        Vector precache = new Vector();
        while (connector.more()) {
          User addUser = UserManager.getInstance().getUserObject(connector);
          precache.add(addUser);
          allUsersHash.put(String.valueOf(addUser.getUserId()), addUser);
          connector.next();
        } 
        connector.close();
        allUsers = precache;
        umlUsers = null;
        labelUsers = null;
        buildUserCacheObjects();
      } 
    } 
    return allUsers;
  }
  
  public static void refreshCacheAllUsers(int userId) {
    synchronized (lockObject) {
      String query = "sp_get_User_All " + userId;
      JdbcConnector connector = MilestoneHelper.getConnector(query);
      connector.runQuery();
      if (!connector.more()) {
        for (int i = 0; i < allUsers.size(); i++) {
          User user = (User)allUsers.get(i);
          if (user.getUserId() == userId) {
            allUsers.remove(i);
            break;
          } 
        } 
        allUsersHash.remove(String.valueOf(userId));
      } else {
        User refreshUser = UserManager.getInstance().getUserObject(connector);
        boolean refreshed = false;
        for (int i = 0; i < allUsers.size(); i++) {
          User user = (User)allUsers.get(i);
          if (user.getUserId() == refreshUser.getUserId()) {
            allUsers.set(i, refreshUser);
            refreshed = true;
            break;
          } 
        } 
        if (!refreshed)
          allUsers.add(refreshUser); 
        allUsersHash.put(String.valueOf(refreshUser.getUserId()), refreshUser);
      } 
      connector.close();
    } 
    umlUsers = null;
    labelUsers = null;
    buildUserCacheObjects();
  }
  
  public static Vector getDatePeriods() {
    if (datePeriods == null) {
      query = "SELECT * FROM vi_Date_Period ORDER BY start_date;";
      JdbcConnector connector = MilestoneHelper.getConnector(query);
      connector.runQuery();
      Vector precache = new Vector();
      while (connector.more()) {
        int id = connector.getIntegerField("per_id");
        String name = connector.getField("name");
        String cycle = connector.getField("cycle");
        Calendar startDate = MilestoneHelper.getDatabaseDate(connector.getField("start_date", ""));
        Calendar endDate = MilestoneHelper.getDatabaseDate(connector.getField("end_date", ""));
        Calendar solDate = MilestoneHelper.getDatabaseDate(connector.getField("sol_date", ""));
        precache.addElement(new DatePeriod(id, name, cycle, startDate, endDate, solDate));
        connector.next();
      } 
      connector.close();
      datePeriods = precache;
    } 
    return datePeriods;
  }
  
  public static Vector getLookupDetailValuesFromDatabase(int fieldId) { return getLookupDetailValuesFromDatabase("com.universal.milestone.LookupObject", fieldId); }
  
  public static Vector getLookupDetailValuesFromDatabase(String className, int fieldId) { // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: getstatic com/universal/milestone/Cache.lookupObjects : Ljava/util/Hashtable;
    //   5: ifnull -> 21
    //   8: getstatic com/universal/milestone/Cache.lookupObjects : Ljava/util/Hashtable;
    //   11: iload_1
    //   12: invokestatic valueOf : (I)Ljava/lang/String;
    //   15: invokevirtual containsKey : (Ljava/lang/Object;)Z
    //   18: ifne -> 464
    //   21: aconst_null
    //   22: astore_3
    //   23: aload_0
    //   24: invokestatic forName : (Ljava/lang/String;)Ljava/lang/Class;
    //   27: astore_3
    //   28: goto -> 57
    //   31: astore #4
    //   33: getstatic com/universal/milestone/Cache.log : Lcom/techempower/ComponentLog;
    //   36: new java/lang/StringBuilder
    //   39: dup
    //   40: ldc_w 'ClassNotFoundException: '
    //   43: invokespecial <init> : (Ljava/lang/String;)V
    //   46: aload #4
    //   48: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   51: invokevirtual toString : ()Ljava/lang/String;
    //   54: invokevirtual log : (Ljava/lang/String;)V
    //   57: new java/lang/StringBuilder
    //   60: dup
    //   61: ldc_w 'SELECT * FROM vi_lookup_detail WHERE Field_id = '
    //   64: invokespecial <init> : (Ljava/lang/String;)V
    //   67: iload_1
    //   68: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   71: invokevirtual toString : ()Ljava/lang/String;
    //   74: astore #4
    //   76: getstatic com/universal/milestone/Cache.sortLookupTableByValueDesc : Ljava/util/Hashtable;
    //   79: new java/lang/Integer
    //   82: dup
    //   83: iload_1
    //   84: invokespecial <init> : (I)V
    //   87: invokevirtual containsKey : (Ljava/lang/Object;)Z
    //   90: ifeq -> 145
    //   93: getstatic com/universal/milestone/Cache.sortLookupTableByValueDesc : Ljava/util/Hashtable;
    //   96: new java/lang/Integer
    //   99: dup
    //   100: iload_1
    //   101: invokespecial <init> : (I)V
    //   104: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   107: checkcast java/lang/String
    //   110: ldc_w '1'
    //   113: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   116: ifeq -> 145
    //   119: new java/lang/StringBuilder
    //   122: dup
    //   123: aload #4
    //   125: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   128: invokespecial <init> : (Ljava/lang/String;)V
    //   131: ldc_w ' ORDER BY value, description;'
    //   134: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   137: invokevirtual toString : ()Ljava/lang/String;
    //   140: astore #4
    //   142: goto -> 168
    //   145: new java/lang/StringBuilder
    //   148: dup
    //   149: aload #4
    //   151: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   154: invokespecial <init> : (Ljava/lang/String;)V
    //   157: ldc_w ' ORDER BY description;'
    //   160: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   163: invokevirtual toString : ()Ljava/lang/String;
    //   166: astore #4
    //   168: aload #4
    //   170: invokestatic getConnector : (Ljava/lang/String;)Lcom/universal/milestone/JdbcConnector;
    //   173: astore #5
    //   175: aload #5
    //   177: invokevirtual runQuery : ()V
    //   180: new java/util/Vector
    //   183: dup
    //   184: invokespecial <init> : ()V
    //   187: astore_2
    //   188: new java/util/Vector
    //   191: dup
    //   192: invokespecial <init> : ()V
    //   195: astore #6
    //   197: goto -> 342
    //   200: aload_3
    //   201: invokevirtual newInstance : ()Ljava/lang/Object;
    //   204: checkcast com/universal/milestone/LookupObject
    //   207: astore #7
    //   209: aload #7
    //   211: aload #5
    //   213: ldc_w 'value'
    //   216: ldc_w ''
    //   219: invokevirtual getField : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   222: invokevirtual setAbbreviation : (Ljava/lang/String;)V
    //   225: aload #7
    //   227: aload #5
    //   229: ldc_w 'description'
    //   232: ldc_w ''
    //   235: invokevirtual getField : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   238: invokevirtual setName : (Ljava/lang/String;)V
    //   241: aload #7
    //   243: aload #5
    //   245: ldc_w 'inactive'
    //   248: invokevirtual getBoolean : (Ljava/lang/String;)Z
    //   251: invokevirtual setInactive : (Z)V
    //   254: aload #7
    //   256: aload #5
    //   258: ldc_w 'productType'
    //   261: invokevirtual getIntegerField : (Ljava/lang/String;)I
    //   264: invokevirtual setProdType : (I)V
    //   267: aload #7
    //   269: aload #5
    //   271: ldc_w 'isDigitalEquivalent'
    //   274: invokevirtual getBoolean : (Ljava/lang/String;)Z
    //   277: invokevirtual setIsDigitalEquivalent : (Z)V
    //   280: iload_1
    //   281: bipush #9
    //   283: if_icmpne -> 302
    //   286: aload #7
    //   288: aload #5
    //   290: ldc_w 'sub_description'
    //   293: ldc_w ''
    //   296: invokevirtual getField : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   299: invokevirtual setSubValue : (Ljava/lang/String;)V
    //   302: aload #6
    //   304: aload #7
    //   306: invokevirtual addElement : (Ljava/lang/Object;)V
    //   309: goto -> 337
    //   312: astore #7
    //   314: getstatic com/universal/milestone/Cache.log : Lcom/techempower/ComponentLog;
    //   317: ldc_w 'InstantiationException'
    //   320: invokevirtual log : (Ljava/lang/String;)V
    //   323: goto -> 337
    //   326: astore #7
    //   328: getstatic com/universal/milestone/Cache.log : Lcom/techempower/ComponentLog;
    //   331: ldc_w 'IllegalAccessException'
    //   334: invokevirtual log : (Ljava/lang/String;)V
    //   337: aload #5
    //   339: invokevirtual next : ()V
    //   342: aload #5
    //   344: invokevirtual more : ()Z
    //   347: ifne -> 200
    //   350: iload_1
    //   351: iconst_5
    //   352: if_icmpne -> 422
    //   355: aload #6
    //   357: invokevirtual clone : ()Ljava/lang/Object;
    //   360: checkcast java/util/Vector
    //   363: astore_2
    //   364: iconst_0
    //   365: istore #7
    //   367: goto -> 409
    //   370: aload_2
    //   371: aload #6
    //   373: iload #7
    //   375: invokevirtual elementAt : (I)Ljava/lang/Object;
    //   378: new com/universal/milestone/Day
    //   381: dup
    //   382: aload #6
    //   384: iload #7
    //   386: invokevirtual elementAt : (I)Ljava/lang/Object;
    //   389: checkcast com/universal/milestone/LookupObject
    //   392: invokevirtual getAbbreviation : ()Ljava/lang/String;
    //   395: invokespecial <init> : (Ljava/lang/String;)V
    //   398: invokevirtual getDayID : ()I
    //   401: iconst_1
    //   402: isub
    //   403: invokevirtual setElementAt : (Ljava/lang/Object;I)V
    //   406: iinc #7, 1
    //   409: iload #7
    //   411: aload #6
    //   413: invokevirtual size : ()I
    //   416: if_icmplt -> 370
    //   419: goto -> 431
    //   422: aload #6
    //   424: invokevirtual clone : ()Ljava/lang/Object;
    //   427: checkcast java/util/Vector
    //   430: astore_2
    //   431: aload #5
    //   433: invokevirtual close : ()V
    //   436: getstatic com/universal/milestone/Cache.lookupObjects : Ljava/util/Hashtable;
    //   439: ifnonnull -> 452
    //   442: new java/util/Hashtable
    //   445: dup
    //   446: invokespecial <init> : ()V
    //   449: putstatic com/universal/milestone/Cache.lookupObjects : Ljava/util/Hashtable;
    //   452: getstatic com/universal/milestone/Cache.lookupObjects : Ljava/util/Hashtable;
    //   455: iload_1
    //   456: invokestatic valueOf : (I)Ljava/lang/String;
    //   459: aload_2
    //   460: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   463: pop
    //   464: getstatic com/universal/milestone/Cache.lookupObjects : Ljava/util/Hashtable;
    //   467: iload_1
    //   468: invokestatic valueOf : (I)Ljava/lang/String;
    //   471: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   474: checkcast java/util/Vector
    //   477: astore_2
    //   478: aload_2
    //   479: areturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #2768	-> 0
    //   #2771	-> 2
    //   #2773	-> 21
    //   #2776	-> 23
    //   #2777	-> 28
    //   #2778	-> 31
    //   #2779	-> 33
    //   #2782	-> 57
    //   #2784	-> 67
    //   #2782	-> 71
    //   #2788	-> 76
    //   #2790	-> 93
    //   #2791	-> 110
    //   #2792	-> 119
    //   #2794	-> 142
    //   #2796	-> 145
    //   #2798	-> 168
    //   #2799	-> 175
    //   #2801	-> 180
    //   #2802	-> 188
    //   #2807	-> 197
    //   #2809	-> 200
    //   #2810	-> 209
    //   #2811	-> 225
    //   #2812	-> 241
    //   #2813	-> 254
    //   #2815	-> 267
    //   #2818	-> 280
    //   #2819	-> 286
    //   #2821	-> 302
    //   #2822	-> 309
    //   #2823	-> 312
    //   #2824	-> 314
    //   #2826	-> 326
    //   #2827	-> 328
    //   #2830	-> 337
    //   #2807	-> 342
    //   #2834	-> 350
    //   #2835	-> 355
    //   #2836	-> 364
    //   #2837	-> 370
    //   #2838	-> 378
    //   #2839	-> 384
    //   #2838	-> 395
    //   #2840	-> 398
    //   #2838	-> 402
    //   #2837	-> 403
    //   #2836	-> 406
    //   #2842	-> 419
    //   #2844	-> 422
    //   #2846	-> 431
    //   #2849	-> 436
    //   #2850	-> 442
    //   #2852	-> 452
    //   #2855	-> 464
    //   #2856	-> 478
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	480	0	className	Ljava/lang/String;
    //   0	480	1	fieldId	I
    //   2	478	2	lookupVector	Ljava/util/Vector;
    //   23	441	3	classObj	Ljava/lang/Class;
    //   33	24	4	cnfexc	Ljava/lang/ClassNotFoundException;
    //   76	388	4	query	Ljava/lang/String;
    //   175	289	5	connector	Lcom/universal/milestone/JdbcConnector;
    //   197	267	6	tempLookupVector	Ljava/util/Vector;
    //   209	100	7	lookupObject	Lcom/universal/milestone/LookupObject;
    //   314	9	7	e	Ljava/lang/InstantiationException;
    //   328	9	7	iae	Ljava/lang/IllegalAccessException;
    //   367	52	7	i	I
    // Exception table:
    //   from	to	target	type
    //   23	28	31	java/lang/ClassNotFoundException
    //   200	309	312	java/lang/InstantiationException
    //   200	309	326	java/lang/IllegalAccessException }
  
  public static String getDepartmentSubDescription(String departmentValue) {
    String query = "SELECT * FROM vi_lookup_SubDetail WHERE Field_id = 18 and det_value = '" + 
      
      departmentValue + "';";
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    String departmentSubdescription = "";
    while (connector.more()) {
      departmentSubdescription = connector.getField("description", "");
      connector.next();
    } 
    connector.close();
    return departmentSubdescription;
  }
  
  public static String getJavaScriptPriceCodeArray() {
    if (javaScriptPriceCodeArray == null) {
      result = new String();
      Vector vSellCodeList = getSellCodes();
      result = String.valueOf(result) + "var p = new Array();\n";
      String pc = new String();
      result = String.valueOf(result) + "p[0] = new Array( 0, ' ');\n";
      int arrayIndex = 0;
      for (int i = 0; i < vSellCodeList.size(); i++) {
        int k = i + 1;
        pc = (String)vSellCodeList.get(i);
        String s1 = "p[" + k + "] = new Array(";
        String s2 = new String();
        s2 = String.valueOf(s2) + " " + k + ", '" + pc + "',";
        if (s2.length() > 0) {
          s2 = s2.substring(0, s2.length() - 1);
          result = String.valueOf(result) + s1 + s2 + ");\n";
        } else {
          result = String.valueOf(result) + s1 + " 0, 'None');\n";
        } 
      } 
      javaScriptPriceCodeArray = result;
    } 
    return javaScriptPriceCodeArray;
  }
  
  public static String getJavaScriptPriceCodeDPCArray() {
    if (javaScriptPriceCodeDPCArray == null) {
      result = new String();
      Vector vSellCodeList = getSellCodesDPC();
      result = String.valueOf(result) + "var dpc = new Array();\n";
      String pc = new String();
      result = String.valueOf(result) + "dpc[0] = new Array( 0, ' ');\n";
      int arrayIndex = 0;
      for (int i = 0; i < vSellCodeList.size(); i++) {
        int k = i + 1;
        pc = (String)vSellCodeList.get(i);
        String s1 = "dpc[" + k + "] = new Array(";
        String s2 = new String();
        s2 = String.valueOf(s2) + " " + k + ", '" + pc + "',";
        if (s2.length() > 0) {
          s2 = s2.substring(0, s2.length() - 1);
          result = String.valueOf(result) + s1 + s2 + ");\n";
        } else {
          result = String.valueOf(result) + s1 + " 0, 'None');\n";
        } 
      } 
      javaScriptPriceCodeDPCArray = result;
    } 
    return javaScriptPriceCodeDPCArray;
  }
  
  public static String getJavaScriptPFMConfigs() {
    results = new String();
    Vector configCodes = getConfigCodes();
    int configSize = configCodes.size();
    results = "PFMconfigs = new Array();\n";
    for (int x = 0; x < configCodes.size(); x++) {
      LookupObject lo = (LookupObject)configCodes.get(x);
      results = String.valueOf(results) + "PFMconfigs['" + lo.getAbbreviation() + "'] = " + lo.isDigitalEquivalent + ";\n";
    } 
    return results;
  }
  
  public static String getJavaScriptConfigArray(String selectedOption) {
    if (javaScriptConfigArray == null) {
      String result = new String();
      String arraySetup = "";
      String arrayContents = "";
      Vector configs = getSelectionConfigs();
      selectedOption = "";
      int subConfigSize = 0;
      int configSize = 0;
      int sizeActiveConfigs = getActiveConfigSize(configs);
      arraySetup = "configs = new Array(" + (sizeActiveConfigs + 1) + ");\n";
      arraySetup = String.valueOf(arraySetup) + "configs[0] = new Array(1);\n";
      arraySetup = String.valueOf(arraySetup) + "configsProdType = new Array(" + (sizeActiveConfigs + 1) + ");\n";
      for (int i = 0; i < configs.size(); i++) {
        SelectionConfiguration selectionConfig = (SelectionConfiguration)configs.elementAt(i);
        if (selectionConfig.getInactive())
          if (!selectionConfig.getSelectionConfigurationAbbreviation().equalsIgnoreCase(selectedOption))
            continue;  
        configSize++;
        subConfigSize = 0;
        Vector subConfigs = selectionConfig.getSubConfigurations();
        int sizeActiveSubConfigs = getActiveSubConfigSize(subConfigs);
        arraySetup = String.valueOf(arraySetup) + "configs[" + configSize + "] = new Array(" + sizeActiveSubConfigs + ");\n";
        arrayContents = String.valueOf(arrayContents) + "configsProdType[" + configSize + "] = '" + selectionConfig.getSelectionConfigurationName() + "," + selectionConfig.getSelectionConfigurationAbbreviation() + "," + selectionConfig.getProdType() + "," + selectionConfig.getNewBundle() + "';\n";
        for (int j = 0; j < subConfigs.size(); j++) {
          SelectionSubConfiguration selectionSubConfig = (SelectionSubConfiguration)subConfigs.elementAt(j);
          if (selectionSubConfig.getInactive())
            if (!selectionSubConfig.getSelectionSubConfigurationAbbreviation().equalsIgnoreCase(selectedOption))
              continue;  
          subConfigSize++;
          arrayContents = String.valueOf(arrayContents) + "configs[" + configSize + "][" + subConfigSize + "] = '" + selectionSubConfig.getSelectionSubConfigurationAbbreviation() + "," + selectionSubConfig.getSelectionSubConfigurationName() + "," + selectionSubConfig.isParent() + "," + selectionSubConfig.getProdType() + "';\n";
          continue;
        } 
        continue;
      } 
      result = String.valueOf(result) + arraySetup;
      result = String.valueOf(result) + "configs[0][0]=',';\n";
      result = String.valueOf(result) + arrayContents;
      javaScriptConfigArray = result;
    } 
    return javaScriptConfigArray;
  }
  
  public static String getJavaScriptSubConfigArray(String selectedOption) {
    if (javaScriptSubConfigArray == null) {
      String result = new String();
      String arraySetup = "";
      String arrayContents = "";
      Vector configs = getSelectionConfigs();
      int subConfigSize = 0;
      int sizeActiveConfigs = getActiveConfigSize(configs);
      for (int i = 0; i < configs.size(); i++) {
        SelectionConfiguration selectionConfig = (SelectionConfiguration)configs.elementAt(i);
        if (selectionConfig.getInactive())
          if (!selectionConfig.getSelectionConfigurationAbbreviation().equalsIgnoreCase(selectedOption))
            continue;  
        Vector subConfigs = selectionConfig.getSubConfigurations();
        int sizeActiveSubConfigs = getActiveSubConfigSize(subConfigs);
        for (int j = 0; j < subConfigs.size(); j++) {
          SelectionSubConfiguration selectionSubConfig = (SelectionSubConfiguration)subConfigs.elementAt(j);
          if (selectionSubConfig.getInactive())
            if (!selectionSubConfig.getSelectionSubConfigurationAbbreviation().equalsIgnoreCase(selectedOption))
              continue;  
          subConfigSize++;
          arrayContents = String.valueOf(arrayContents) + "subConfigs[" + subConfigSize + "] = '" + selectionSubConfig.getSelectionSubConfigurationAbbreviation() + "," + selectionSubConfig.isParent() + "," + selectionSubConfig.getProdType() + "';\n";
          continue;
        } 
        continue;
      } 
      arraySetup = "subConfigs = new Array(" + subConfigSize + ");\n";
      result = String.valueOf(result) + arraySetup;
      result = String.valueOf(result) + arrayContents;
      javaScriptSubConfigArray = result;
    } 
    return javaScriptSubConfigArray;
  }
  
  public static Vector getBomSuppliers() {
    if (bomSuppliers == null) {
      query = "SELECT * FROM vi_Supplier where (inactive is null or inactive = 0)  ORDER BY description;";
      JdbcConnector connector = MilestoneHelper.getConnector(query);
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
    } 
    return bomSuppliers;
  }
  
  public static Vector getDayTypes() {
    if (dayTypes == null) {
      query = "SELECT * FROM vi_day_type ORDER BY grouping asc, date desc;";
      JdbcConnector connector = MilestoneHelper.getConnector(query);
      connector.runQuery();
      Vector precache = new Vector();
      Day day = null;
      int curGroup = -1;
      int prevGroup = -1;
      Vector curGroupDates = null;
      while (connector.more()) {
        curGroup = connector.getIntegerField("grouping");
        if (curGroup != prevGroup) {
          if (curGroupDates != null)
            precache.add(curGroupDates); 
          curGroupDates = new Vector();
        } 
        day = new Day(connector.getIntegerField("type_id"));
        day.setDescription(connector.getField("description", ""));
        day.setCalendarGroup(curGroup);
        day.setSpecificDate(MilestoneHelper.getDatabaseDate(connector.getField("date")));
        day.setDayType(connector.getField("value", ""));
        String lastDateString = connector.getFieldByName("last_updated_on");
        if (lastDateString != null)
          day.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastDateString)); 
        day.setLastUpdatingUser(connector.getIntegerField("last_updated_by"));
        long lastUpdatedLong = Long.parseLong(connector.getField("last_updated_ck"), 16);
        day.setLastUpdatedCk(lastUpdatedLong);
        curGroupDates.addElement(day);
        day = null;
        prevGroup = curGroup;
        connector.next();
      } 
      if (curGroupDates != null)
        precache.add(curGroupDates); 
      dayTypes = precache;
      connector.close();
    } 
    return dayTypes;
  }
  
  public static Hashtable getUserCompaniesTable() {
    if (userCompaniesTable == null)
      userCompaniesTable = new Hashtable(); 
    return userCompaniesTable;
  }
  
  private static void RefreshServers(String pstrParam) {
    try {
      if (RefreshServers != null) {
        Iterator it = RefreshServers.iterator();
        while (it.hasNext()) {
          String strUrl = String.valueOf(it.next().toString()) + pstrParam;
          System.out.println("URL: " + strUrl);
          URL url = new URL(strUrl);
          URLConnection connMilestone = url.openConnection();
          connMilestone.getInputStream();
        } 
      } 
    } catch (Exception e) {
      System.out.println("FAILED ATTEMPT");
    } 
  }
  
  public static int getActiveConfigSize(Vector configs) {
    int sizeActives = 0;
    for (int i = 0; i < configs.size(); i++) {
      SelectionConfiguration selectionConfig = 
        (SelectionConfiguration)configs.elementAt(i);
      if (!selectionConfig.getInactive())
        sizeActives++; 
    } 
    return sizeActives;
  }
  
  public static int getActiveSubConfigSize(Vector subConfigs) {
    int sizeActives = 0;
    for (int i = 0; i < subConfigs.size(); i++) {
      SelectionSubConfiguration selectionSubConfig = (SelectionSubConfiguration)subConfigs.elementAt(i);
      if (!selectionSubConfig.getInactive())
        sizeActives++; 
    } 
    return sizeActives;
  }
  
  public static Vector getEnvironments() {
    synchronized (lockObject) {
      if (cachedEnvironments == null) {
        log.debug("creating new Environment cache.");
        cachedEnvironments = new Vector();
        Vector csos = getCorporateStructure();
        for (int i = 0; i < csos.size(); i++) {
          CorporateStructureObject cso = (CorporateStructureObject)csos.elementAt(i);
          if (cso instanceof Environment)
            cachedEnvironments.addElement(cso); 
        } 
        sortEnvironmentsByFamily(cachedEnvironments);
      } 
    } 
    return cachedEnvironments;
  }
  
  public static Vector getEnvironmentsByFamily() { return cachedEnvironmentsByFamily; }
  
  private static void sortEnvironmentsByFamily(Vector environments) {
    cachedEnvironmentsByFamily = new Vector();
    Object[] environmentArray = environments.toArray();
    Arrays.sort(environmentArray, new CorpStructParentNameComparator());
    for (int j = 0; j < environmentArray.length; j++)
      cachedEnvironmentsByFamily.add(environmentArray[j]); 
  }
  
  public static Hashtable getUserEnvironmentsTable() {
    if (userEnvironmentsTable == null)
      userEnvironmentsTable = new Hashtable(); 
    return userEnvironmentsTable;
  }
  
  public static Vector getEnvironmentList() { return getEnvironments(); }
  
  public static Vector getLabelUsers() {
    if (labelUsers == null) {
      precache = new Vector();
      int umlId = MilestoneHelper.getStructureId("UML", 1);
      int enterpriseId = MilestoneHelper.getStructureId("Enterprise", 1);
      String query = "SELECT DISTINCT vi_All_User.Name, vi_All_User.Full_Name, vi_All_User.User_Id FROM vi_All_User, vi_User_Company WHERE (vi_All_User.User_ID = vi_User_Company.User_ID) AND (menu_access LIKE '[1,2]%') AND (vi_All_User.employed_by <> " + 
        
        umlId + ")" + 
        " AND (vi_All_User.employed_by IS NOT NULL)" + 
        " AND (vi_All_User.employed_by <> " + enterpriseId + ")" + 
        " AND (vi_All_User.employed_by <> 0)" + 
        " AND (vi_All_user.employed_by <> -1)" + 
        " AND exists (select 'x' from dbo.release_header rh where rh.contact_id = vi_All_User.[user_id])" + 
        " ORDER BY vi_All_User.Full_Name;";
      JdbcConnector connector = MilestoneHelper.getConnector(query);
      connector.runQuery();
      while (connector.more()) {
        User labelUser = UserManager.getInstance().getUser(connector.getIntegerField("user_id"));
        precache.add(labelUser);
        connector.next();
      } 
      connector.close();
      labelUsers = precache;
    } 
    return labelUsers;
  }
  
  public static void buildUserCacheObjects() {
    getUmlUsers();
    getLabelUsers();
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\Cache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */