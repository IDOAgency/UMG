/*      */ package WEB-INF.classes.com.universal.milestone;
/*      */ 
/*      */ import com.techempower.ComponentLog;
/*      */ import com.techempower.EnhancedProperties;
/*      */ import com.techempower.gemini.GeminiApplication;
/*      */ import com.universal.milestone.Cache;
/*      */ import com.universal.milestone.CachingComponent;
/*      */ import com.universal.milestone.Company;
/*      */ import com.universal.milestone.CorpStructParentNameComparator;
/*      */ import com.universal.milestone.CorporateStructureObject;
/*      */ import com.universal.milestone.DatePeriod;
/*      */ import com.universal.milestone.Day;
/*      */ import com.universal.milestone.Division;
/*      */ import com.universal.milestone.Environment;
/*      */ import com.universal.milestone.Family;
/*      */ import com.universal.milestone.JdbcConnector;
/*      */ import com.universal.milestone.Label;
/*      */ import com.universal.milestone.LabelManager;
/*      */ import com.universal.milestone.LookupObject;
/*      */ import com.universal.milestone.MilestoneConstants;
/*      */ import com.universal.milestone.MilestoneHelper;
/*      */ import com.universal.milestone.MilestoneHelper_2;
/*      */ import com.universal.milestone.PrefixCode;
/*      */ import com.universal.milestone.PriceCode;
/*      */ import com.universal.milestone.SelectionConfiguration;
/*      */ import com.universal.milestone.SelectionSubConfiguration;
/*      */ import com.universal.milestone.User;
/*      */ import com.universal.milestone.UserManager;
/*      */ import com.universal.milestone.projectSearchSvcClient;
/*      */ import java.net.URL;
/*      */ import java.net.URLConnection;
/*      */ import java.util.Arrays;
/*      */ import java.util.Calendar;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.Vector;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Cache
/*      */   extends CachingComponent
/*      */   implements MilestoneConstants
/*      */ {
/*      */   public static final String COMPONENT_CODE = "cach";
/*   68 */   protected static Cache cache = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   73 */   protected static Object lockObject = new Object();
/*      */ 
/*      */   
/*   76 */   protected static Vector cachedCorporateStructure = null;
/*      */   
/*   78 */   protected static Hashtable cachedCorporateStructureHash = null;
/*   79 */   protected static Vector cachedFamilies = null;
/*      */   
/*   81 */   protected static Vector cachedEnvironments = null;
/*   82 */   protected static Vector cachedCompanies = null;
/*   83 */   protected static Vector cachedDivisions = null;
/*   84 */   protected static Vector cachedLabels = null;
/*      */   
/*   86 */   protected static Vector sellCodes = null;
/*   87 */   protected static Vector sellCodesDPC = null;
/*   88 */   protected static Vector selectionConfigs = null;
/*   89 */   protected static Vector selectionStatusList = null;
/*   90 */   protected static Vector theFamilies = null;
/*   91 */   protected static Vector theFamilyList = null;
/*      */   
/*   93 */   protected static Vector theEnvironments = null;
/*   94 */   protected static Vector theEnvironmentList = null;
/*   95 */   protected static Vector theCompanies = null;
/*   96 */   protected static Vector theCompanyList = null;
/*   97 */   protected static Vector theDivisions = null;
/*   98 */   protected static Vector theLabels = null;
/*   99 */   protected static Vector releaseTypes = null;
/*  100 */   protected static Vector genres = null;
/*  101 */   protected static Vector prefixCodes = null;
/*  102 */   protected static Vector productCategories = null;
/*  103 */   protected static Vector distributionCodes = null;
/*  104 */   protected static Vector vendors = null;
/*  105 */   protected static Vector umlUsers = null;
/*  106 */   protected static Vector datePeriods = null;
/*  107 */   protected static Vector reportConfigTypes = null;
/*  108 */   protected static Vector reportConfigFormats = null;
/*  109 */   protected static Vector reportConfigStatuses = null;
/*  110 */   protected static Vector operatingCompanies = null;
/*  111 */   protected static Vector musicLines = null;
/*  112 */   protected static Vector configCodes = null;
/*  113 */   protected static Vector repOwners = null;
/*      */   
/*  115 */   protected static Vector repClasses = null;
/*  116 */   protected static Vector modifiers = null;
/*  117 */   protected static Vector returnCodes = null;
/*  118 */   protected static Vector exportFlags = null;
/*  119 */   protected static Vector companyCodes = null;
/*  120 */   protected static Vector poMergeCodes = null;
/*  121 */   protected static Vector guaranteeCodes = null;
/*  122 */   protected static Vector loosePickExemptCodes = null;
/*  123 */   protected static Vector compilationCodes = null;
/*  124 */   protected static Vector impRateCodes = null;
/*  125 */   protected static Vector musicTypes = null;
/*  126 */   protected static Vector suppliers = null;
/*  127 */   protected static Vector narm = null;
/*  128 */   protected static Vector pricePoints = null;
/*  129 */   protected static Vector importIndicators = null;
/*  130 */   protected static Vector bomSuppliers = null;
/*  131 */   protected static Vector dayTypes = null;
/*  132 */   protected static Vector releaseCompanies = null;
/*  133 */   protected static Vector taskAbbreviations = null;
/*  134 */   protected static Vector allUsers = null;
/*  135 */   protected static Hashtable allUsersHash = null;
/*  136 */   protected static Vector labelUsers = null;
/*  137 */   protected static Vector formats = null;
/*      */   
/*  139 */   protected static Vector priceCodes = null;
/*      */   
/*  141 */   protected static Hashtable lookupObjects = new Hashtable();
/*      */ 
/*      */   
/*  144 */   protected static Vector encryptionFlags = null;
/*      */ 
/*      */   
/*  147 */   protected static Hashtable userCompaniesTable = null;
/*      */   
/*  149 */   protected static Hashtable userEnvironmentsTable = null;
/*      */   
/*  151 */   protected static String javaScriptConfigArray = null;
/*  152 */   protected static String javaScriptPriceCodeArray = null;
/*  153 */   protected static String javaScriptPriceCodeDPCArray = null;
/*  154 */   protected static String javaScriptSubConfigArray = null;
/*  155 */   protected static String javaScriptPFMConfigArray = null;
/*      */   
/*  157 */   protected static ComponentLog log = null;
/*      */   
/*  159 */   protected static Vector RefreshServers = null;
/*      */ 
/*      */ 
/*      */   
/*  163 */   protected static Hashtable sortLookupTableByValueDesc = new Hashtable();
/*      */ 
/*      */   
/*  166 */   protected static Vector cachedEnvironmentsByFamily = null;
/*      */ 
/*      */   
/*  169 */   protected static Vector cachedLabelsUsed = null;
/*      */ 
/*      */   
/*  172 */   protected static Hashtable cachedProductRelFamilies = null;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String REFRESH_CACHE_PARAM_TYPE = "cache-refresh-param-type";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String REFRESH_CACHE_PARAM_USERID = "cache-refresh-param-userid";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Cache getInstance() {
/*  186 */     if (cache == null)
/*      */     {
/*  188 */       cache = new Cache();
/*      */     }
/*  190 */     return cache;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Cache() {
/*  199 */     log.debug("Loading Cache.");
/*      */ 
/*      */ 
/*      */     
/*  203 */     sortLookupTableByValueDesc.put(new Integer(31), "1");
/*  204 */     sortLookupTableByValueDesc.put(new Integer(7), "1");
/*  205 */     sortLookupTableByValueDesc.put(new Integer(29), "1");
/*  206 */     sortLookupTableByValueDesc.put(new Integer(8), "1");
/*      */     
/*  208 */     sortLookupTableByValueDesc.put(new Integer(9), "1");
/*  209 */     sortLookupTableByValueDesc.put(new Integer(30), "1");
/*  210 */     sortLookupTableByValueDesc.put(new Integer(10), "1");
/*  211 */     sortLookupTableByValueDesc.put(new Integer(11), "1");
/*  212 */     sortLookupTableByValueDesc.put(new Integer(36), "1");
/*  213 */     sortLookupTableByValueDesc.put(new Integer(6), "1");
/*  214 */     sortLookupTableByValueDesc.put(new Integer(37), "1");
/*  215 */     sortLookupTableByValueDesc.put(new Integer(12), "1");
/*  216 */     sortLookupTableByValueDesc.put(new Integer(13), "1");
/*  217 */     sortLookupTableByValueDesc.put(new Integer(19), "1");
/*  218 */     sortLookupTableByValueDesc.put(new Integer(56), "1");
/*  219 */     sortLookupTableByValueDesc.put(new Integer(14), "1");
/*  220 */     sortLookupTableByValueDesc.put(new Integer(54), "1");
/*  221 */     sortLookupTableByValueDesc.put(new Integer(57), "1");
/*  222 */     sortLookupTableByValueDesc.put(new Integer(60), "1");
/*      */     
/*  224 */     sortLookupTableByValueDesc.put(new Integer(61), "1");
/*      */ 
/*      */ 
/*      */     
/*  228 */     log.debug("Retrieved Cache.");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void configure(EnhancedProperties props, GeminiApplication application) {
/*  237 */     log = application.getLog("cach");
/*      */ 
/*      */ 
/*      */     
/*  241 */     int count = props.getIntegerProperty("RefreshServers", 0);
/*  242 */     String strUrl = "";
/*  243 */     for (int i = 1; count >= i; i++) {
/*  244 */       strUrl = props.getProperty("RefreshServer" + i, "");
/*  245 */       if (strUrl.compareToIgnoreCase("") != 0) {
/*  246 */         strUrl = String.valueOf(strUrl) + "?cmd=cache-refresh&cache-refresh-param=";
/*  247 */         if (RefreshServers == null)
/*  248 */           RefreshServers = new Vector(); 
/*  249 */         RefreshServers.add(strUrl);
/*      */       } 
/*      */     } 
/*      */     
/*  253 */     log.debug("configure() complete.");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean flushCachedVariableAllUsers(int userId) {
/*  264 */     flushCachedVariableLocalAllUsers(userId);
/*      */ 
/*      */     
/*  267 */     RefreshServers("AU&cache-refresh-param-userid=" + 
/*  268 */         userId);
/*      */     
/*  270 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean flushCachedVariableLocalAllUsers(int userId) {
/*  280 */     System.out.println("<<< All Users Cache is being refreshed");
/*      */     
/*  282 */     refreshCacheAllUsers(userId);
/*  283 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean flushCachedVariable(Object object) {
/*  292 */     if (object != null) {
/*      */ 
/*      */ 
/*      */       
/*  296 */       log.debug("*** flushCachedVariable: " + ((Vector)object).firstElement().getClass().toString().toUpperCase());
/*  297 */       if (((Vector)object).firstElement().getClass().toString().equalsIgnoreCase("CLASS COM.UNIVERSAL.MILESTONE.DAY")) {
/*  298 */         RefreshServers("DT");
/*  299 */       } else if (((Vector)object).firstElement().getClass().toString().equalsIgnoreCase("CLASS COM.UNIVERSAL.MILESTONE.USER")) {
/*  300 */         RefreshServers("AU");
/*      */       } 
/*  302 */     }  return flushCachedVariableLocal(object);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean flushCachedVariableLocal(Object object) {
/*  311 */     if (object == null) {
/*  312 */       return false;
/*      */     }
/*      */     
/*      */     try {
/*  316 */       if (object != null)
/*      */       {
/*      */         
/*  319 */         if (((Vector)object).firstElement().getClass().toString().equalsIgnoreCase("CLASS COM.UNIVERSAL.MILESTONE.USER")) {
/*  320 */           allUsers = null;
/*  321 */         } else if (((Vector)object).firstElement().getClass().toString().equalsIgnoreCase("CLASS COM.UNIVERSAL.MILESTONE.DAY")) {
/*  322 */           dayTypes = null;
/*      */         } else {
/*  324 */           object = null;
/*      */         }  } 
/*  326 */       return true;
/*      */     }
/*  328 */     catch (Exception e) {
/*      */ 
/*      */       
/*  331 */       if (object == null) {
/*  332 */         System.out.println("<<< Cache() Unable to flush object ");
/*      */       } else {
/*  334 */         System.out.println("<<< Cache() Unable to flush object " + ((Vector)object).firstElement().getClass().toString());
/*  335 */       }  return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void flushTableVariables(String tableType) {
/*  346 */     flushTableVariablesLocal(tableType);
/*  347 */     RefreshServers("TV&cache-refresh-param-type=" + 
/*  348 */         tableType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void flushTableVariablesLocal(String tableType) {
/*  358 */     System.out.println("<<< flushTableVariablesLocal() " + tableType);
/*      */ 
/*      */     
/*  361 */     if (tableType.equals(String.valueOf(1)) || tableType.equals("")) {
/*  362 */       productCategories = null;
/*      */     }
/*  364 */     if (tableType.equals(String.valueOf(40)) || tableType.equals("")) {
/*  365 */       releaseCompanies = null;
/*      */     }
/*  367 */     if (tableType.equals(String.valueOf(2)) || tableType.equals("")) {
/*  368 */       releaseTypes = null;
/*      */     }
/*  370 */     if (tableType.equals(String.valueOf(15)) || tableType.equals("")) {
/*  371 */       selectionStatusList = null;
/*      */     }
/*  373 */     if (tableType.equals(String.valueOf(3)) || 
/*  374 */       tableType.equals(String.valueOf(4)) || 
/*  375 */       tableType.equals("")) {
/*      */       
/*  377 */       selectionConfigs = null;
/*  378 */       javaScriptConfigArray = null;
/*  379 */       javaScriptSubConfigArray = null;
/*      */     } 
/*      */ 
/*      */     
/*  383 */     if (tableType.equals("")) {
/*      */       
/*  385 */       javaScriptPriceCodeArray = null;
/*  386 */       javaScriptPriceCodeDPCArray = null;
/*      */     } 
/*      */     
/*  389 */     if (tableType.equals(String.valueOf(17)) || tableType.equals("")) {
/*  390 */       genres = null;
/*      */     }
/*  392 */     if (tableType.equals(String.valueOf(22)) || tableType.equals("")) {
/*  393 */       vendors = null;
/*      */     }
/*  395 */     if (tableType.equals(String.valueOf(27)) || tableType.equals("")) {
/*  396 */       prefixCodes = null;
/*      */     }
/*      */     
/*  399 */     if (tableType.equals(String.valueOf(48)) || tableType.equals("")) {
/*  400 */       reportConfigFormats = null;
/*      */     }
/*  402 */     if (tableType.equals(String.valueOf(51)) || tableType.equals("")) {
/*  403 */       reportConfigStatuses = null;
/*      */     }
/*      */     
/*  406 */     if (tableType.equals(String.valueOf(31)) || tableType.equals("")) {
/*  407 */       operatingCompanies = null;
/*      */     }
/*  409 */     if (tableType.equals(String.valueOf(7)) || tableType.equals("")) {
/*  410 */       musicLines = null;
/*      */     }
/*  412 */     if (tableType.equals(String.valueOf(29)) || tableType.equals("")) {
/*  413 */       configCodes = null;
/*      */     }
/*  415 */     if (tableType.equals(String.valueOf(8)) || tableType.equals("")) {
/*  416 */       repOwners = null;
/*      */     }
/*  418 */     if (tableType.equals(String.valueOf(9)) || tableType.equals("")) {
/*  419 */       repClasses = null;
/*      */     }
/*  421 */     if (tableType.equals(String.valueOf(30)) || tableType.equals("")) {
/*  422 */       modifiers = null;
/*      */     }
/*  424 */     if (tableType.equals(String.valueOf(10)) || tableType.equals("")) {
/*  425 */       returnCodes = null;
/*      */     }
/*  427 */     if (tableType.equals(String.valueOf(11)) || tableType.equals("")) {
/*  428 */       exportFlags = null;
/*      */     }
/*  430 */     if (tableType.equals(String.valueOf(36)) || tableType.equals("")) {
/*  431 */       companyCodes = null;
/*      */     }
/*  433 */     if (tableType.equals(String.valueOf(6)) || tableType.equals("")) {
/*  434 */       poMergeCodes = null;
/*      */     }
/*  436 */     if (tableType.equals(String.valueOf(37)) || tableType.equals("")) {
/*  437 */       guaranteeCodes = null;
/*      */     }
/*  439 */     if (tableType.equals(String.valueOf(60)) || tableType.equals("")) {
/*  440 */       loosePickExemptCodes = null;
/*      */     }
/*  442 */     if (tableType.equals(String.valueOf(57)) || tableType.equals("")) {
/*  443 */       compilationCodes = null;
/*      */     }
/*  445 */     if (tableType.equals(String.valueOf(12)) || tableType.equals("")) {
/*  446 */       impRateCodes = null;
/*      */     }
/*  448 */     if (tableType.equals(String.valueOf(13)) || tableType.equals("")) {
/*  449 */       musicTypes = null;
/*      */     }
/*  451 */     if (tableType.equals(String.valueOf(19)) || tableType.equals("")) {
/*  452 */       suppliers = null;
/*      */     }
/*  454 */     if (tableType.equals(String.valueOf(56)) || tableType.equals("")) {
/*  455 */       narm = null;
/*      */     }
/*  457 */     if (tableType.equals(String.valueOf(58)) || tableType.equals("")) {
/*  458 */       formats = null;
/*      */     }
/*  460 */     if (tableType.equals(String.valueOf(14)) || tableType.equals("")) {
/*  461 */       pricePoints = null;
/*      */     }
/*  463 */     if (tableType.equals(String.valueOf(54)) || tableType.equals("")) {
/*  464 */       importIndicators = null;
/*      */     }
/*  466 */     if (tableType.equals(String.valueOf(33)) || tableType.equals("")) {
/*  467 */       taskAbbreviations = null;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void flushCachedVariables() {
/*  475 */     flushCorporateStructure();
/*  476 */     flushSellCode();
/*      */     
/*  478 */     sellCodes = null;
/*  479 */     sellCodesDPC = null;
/*  480 */     priceCodes = null;
/*  481 */     lookupObjects = null;
/*  482 */     selectionConfigs = null;
/*  483 */     releaseTypes = null;
/*  484 */     genres = null;
/*  485 */     prefixCodes = null;
/*  486 */     productCategories = null;
/*  487 */     distributionCodes = null;
/*  488 */     vendors = null;
/*  489 */     umlUsers = null;
/*  490 */     datePeriods = null;
/*  491 */     reportConfigTypes = null;
/*  492 */     reportConfigFormats = null;
/*  493 */     reportConfigStatuses = null;
/*  494 */     operatingCompanies = null;
/*  495 */     musicLines = null;
/*  496 */     configCodes = null;
/*  497 */     repOwners = null;
/*  498 */     repClasses = null;
/*  499 */     modifiers = null;
/*  500 */     returnCodes = null;
/*  501 */     exportFlags = null;
/*  502 */     companyCodes = null;
/*  503 */     poMergeCodes = null;
/*  504 */     guaranteeCodes = null;
/*  505 */     compilationCodes = null;
/*  506 */     impRateCodes = null;
/*  507 */     musicTypes = null;
/*  508 */     suppliers = null;
/*  509 */     narm = null;
/*  510 */     pricePoints = null;
/*  511 */     importIndicators = null;
/*  512 */     javaScriptConfigArray = null;
/*  513 */     javaScriptPriceCodeArray = null;
/*  514 */     javaScriptPriceCodeDPCArray = null;
/*  515 */     bomSuppliers = null;
/*  516 */     theCompanyList = null;
/*  517 */     dayTypes = null;
/*  518 */     releaseCompanies = null;
/*  519 */     javaScriptSubConfigArray = null;
/*  520 */     taskAbbreviations = null;
/*  521 */     allUsers = null;
/*  522 */     allUsersHash = null;
/*  523 */     labelUsers = null;
/*  524 */     formats = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void flushSellCode() {
/*  533 */     flushSellCodeLocal();
/*  534 */     RefreshServers("PC");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void flushSellCodeLocal() {
/*  542 */     sellCodes = null;
/*  543 */     sellCodesDPC = null;
/*  544 */     javaScriptPriceCodeArray = null;
/*  545 */     javaScriptPriceCodeDPCArray = null;
/*  546 */     priceCodes = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  554 */   public static void flushDatePeriodsLocal() { datePeriods = null; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void flushDatePeriods() {
/*  561 */     flushDatePeriodsLocal();
/*  562 */     RefreshServers("DP");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  571 */   public static void flushDayTypesLocal() { dayTypes = null; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void flushDayTypes() {
/*  579 */     flushDayTypesLocal();
/*  580 */     RefreshServers("DT");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void flushCorporateStructure() {
/*  589 */     log.debug("Fushing all flushCorporateStructure and refreshing.");
/*  590 */     flushCorporateStructureLocal();
/*  591 */     RefreshServers("CS");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void flushCorporateStructureLocal() {
/*  600 */     log.debug("Fushing the local flushCorporateStructure.");
/*      */     
/*  602 */     cachedCorporateStructure = null;
/*      */     
/*  604 */     cachedCorporateStructureHash = null;
/*      */ 
/*      */     
/*  607 */     projectSearchSvcClient.cachedLabelData = null;
/*      */     
/*  609 */     cachedFamilies = null;
/*  610 */     cachedEnvironments = null;
/*  611 */     cachedCompanies = null;
/*  612 */     cachedDivisions = null;
/*  613 */     cachedLabels = null;
/*      */     
/*  615 */     theFamilies = null;
/*  616 */     theFamilyList = null;
/*  617 */     theEnvironments = null;
/*  618 */     theEnvironmentList = null;
/*  619 */     theCompanies = null;
/*  620 */     theDivisions = null;
/*  621 */     theLabels = null;
/*  622 */     theCompanyList = null;
/*      */ 
/*      */     
/*  625 */     projectSearchSvcClient.cachedBasicClient = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void flushUsedLabels() {
/*  652 */     RefreshServers("UL");
/*      */     
/*  654 */     cachedLabelsUsed = null;
/*  655 */     cachedProductRelFamilies = null;
/*  656 */     getProductReleasingFamilies();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void flushUsedLabelsLocal() {
/*  666 */     cachedLabelsUsed = null;
/*  667 */     cachedProductRelFamilies = null;
/*      */     
/*  669 */     getProductReleasingFamilies();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getUsedLabels() {
/*  676 */     synchronized (lockObject) {
/*      */       
/*  678 */       if (cachedLabelsUsed == null) {
/*      */         
/*  680 */         cachedLabelsUsed = new Vector();
/*      */ 
/*      */         
/*  683 */         JdbcConnector connector = MilestoneHelper.getConnector("SELECT DISTINCT s.*  FROM vi_Structure s, vi_release_header rh  where s.structure_id = rh.label_id ");
/*      */ 
/*      */ 
/*      */         
/*  687 */         connector.runQuery();
/*      */         
/*  689 */         while (connector.more()) {
/*      */           
/*  691 */           Label label = new Label();
/*  692 */           label.initializeByVariables(connector);
/*  693 */           cachedLabelsUsed.add(label);
/*  694 */           connector.next();
/*      */         } 
/*      */         
/*  697 */         connector.close();
/*      */       } 
/*      */       
/*  700 */       return cachedLabelsUsed;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Hashtable getProductReleasingFamilies() {
/*  709 */     synchronized (lockObject) {
/*      */       
/*  711 */       if (cachedProductRelFamilies == null) {
/*      */ 
/*      */         
/*  714 */         cachedProductRelFamilies = new Hashtable();
/*  715 */         JdbcConnector connector = MilestoneHelper.getConnector("sp_get_ProductReleasingFamilies");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  732 */         connector.runQuery();
/*      */         
/*  734 */         while (connector.more()) {
/*  735 */           int cso_id = connector.getInt("cso_id", -1);
/*  736 */           int release_family_id = connector.getInt("release_family_id", -1);
/*      */           
/*  738 */           if (!cachedProductRelFamilies.containsKey(Integer.toString(cso_id))) {
/*  739 */             Vector prodRelFamilies = new Vector();
/*  740 */             prodRelFamilies.add(Integer.toString(release_family_id));
/*  741 */             cachedProductRelFamilies.put(Integer.toString(cso_id), prodRelFamilies);
/*      */           } else {
/*      */             
/*  744 */             Vector prodRelFamilies = (Vector)cachedProductRelFamilies.get(Integer.toString(cso_id));
/*  745 */             prodRelFamilies.add(Integer.toString(release_family_id));
/*      */           } 
/*  747 */           connector.next();
/*      */         } 
/*      */         
/*  750 */         connector.close();
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  755 */     return cachedProductRelFamilies;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getCorporateStructure() {
/*  766 */     synchronized (lockObject) {
/*      */       
/*  768 */       if (cachedCorporateStructure == null) {
/*      */ 
/*      */         
/*  771 */         log.debug("creating new CSO cache.");
/*  772 */         cachedCorporateStructure = new Vector();
/*      */         
/*  774 */         JdbcConnector connector = MilestoneHelper.getConnector("SELECT * FROM vi_Structure ORDER BY name;");
/*      */ 
/*      */ 
/*      */         
/*  778 */         connector.runQuery();
/*      */         
/*  780 */         while (connector.more()) {
/*      */           Label label1;
/*      */           
/*  783 */           boolean active = false;
/*      */ 
/*      */           
/*  786 */           int type = connector.getInt("type");
/*  787 */           int id = connector.getInt("structure_id");
/*      */           
/*  789 */           int archimedesId = connector.getInt("archimedes_id", -1);
/*  790 */           int sort = connector.getInt("sort");
/*  791 */           if (sort == 0) active = true;
/*      */ 
/*      */ 
/*      */           
/*  795 */           Calendar lastUpdatedOn = Calendar.getInstance();
/*  796 */           lastUpdatedOn.setTime(connector.getTimestamp("last_updated_on"));
/*      */ 
/*      */           
/*  799 */           if (type == 1) {
/*      */             
/*  801 */             Family family = new Family();
/*      */ 
/*      */             
/*  804 */             MilestoneHelper_2.initializeCorporateStructure(family, connector);
/*      */             
/*  806 */             family.setArchimedesId(archimedesId);
/*  807 */             family.setActive(active);
/*  808 */             family.setLastUpdateDate(lastUpdatedOn);
/*  809 */             label1 = family;
/*      */           }
/*  811 */           else if (type == 5) {
/*      */             
/*  813 */             Environment env = new Environment();
/*      */ 
/*      */             
/*  816 */             MilestoneHelper_2.initializeCorporateStructure(env, connector);
/*  817 */             env.distribution = connector.getInt("region");
/*  818 */             env.calendarGroup = connector.getInt("grouping");
/*      */             
/*  820 */             env.setArchimedesId(archimedesId);
/*  821 */             env.setActive(active);
/*  822 */             env.setLastUpdateDate(lastUpdatedOn);
/*  823 */             label1 = env;
/*      */           }
/*  825 */           else if (type == 2) {
/*      */             
/*  827 */             Company company = new Company();
/*      */ 
/*      */             
/*  830 */             MilestoneHelper_2.initializeCorporateStructure(company, connector);
/*  831 */             company.distribution = connector.getInt("region");
/*  832 */             company.calendarGroup = connector.getInt("grouping");
/*  833 */             company.setActive(active);
/*  834 */             company.setLastUpdateDate(lastUpdatedOn);
/*  835 */             label1 = company;
/*      */           }
/*  837 */           else if (type == 3) {
/*      */             
/*  839 */             Division division = new Division();
/*      */ 
/*      */             
/*  842 */             MilestoneHelper_2.initializeCorporateStructure(division, connector);
/*  843 */             division.setActive(active);
/*  844 */             division.setLastUpdateDate(lastUpdatedOn);
/*  845 */             label1 = division;
/*      */           }
/*      */           else {
/*      */             
/*  849 */             Label label = new Label();
/*      */ 
/*      */             
/*  852 */             MilestoneHelper_2.initializeCorporateStructure(label, connector);
/*  853 */             label.distribution = connector.getInt("region");
/*      */             
/*  855 */             label.setArchimedesId(archimedesId);
/*  856 */             label.setActive(active);
/*  857 */             LabelManager.getArchimedes(label, Boolean.valueOf(true));
/*  858 */             label.setLastUpdateDate(lastUpdatedOn);
/*  859 */             label1 = label;
/*      */           } 
/*      */ 
/*      */           
/*  863 */           cachedCorporateStructure.add(label1);
/*      */ 
/*      */ 
/*      */           
/*  867 */           connector.next();
/*      */         } 
/*      */ 
/*      */         
/*  871 */         connector.close();
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  876 */         for (int i = 0; i < cachedCorporateStructure.size(); i++) {
/*      */           
/*  878 */           CorporateStructureObject current = (CorporateStructureObject)cachedCorporateStructure.elementAt(i);
/*  879 */           int parentID = current.getParentID();
/*      */           
/*  881 */           if (parentID > 0)
/*      */           {
/*  883 */             for (int j = 0; j < cachedCorporateStructure.size(); j++) {
/*      */               
/*  885 */               CorporateStructureObject parent = (CorporateStructureObject)cachedCorporateStructure.elementAt(j);
/*      */ 
/*      */               
/*  888 */               if (parent.getStructureID() == parentID) {
/*      */                 
/*      */                 try {
/*      */ 
/*      */                   
/*  893 */                   current.setParent(parent);
/*  894 */                   parent.addChild(current);
/*      */                 }
/*  896 */                 catch (Exception exc) {
/*      */                   
/*  898 */                   log.debug("Warning: parent of " + current + " is not appropriate type.");
/*      */                 } 
/*      */               }
/*      */             } 
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  910 */         buildCachedCorporateStructrueHash();
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  916 */     return cachedCorporateStructure;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void buildCachedCorporateStructrueHash() {
/*  924 */     cachedCorporateStructureHash = new Hashtable();
/*  925 */     for (i = 0; i < cachedCorporateStructure.size(); i++) {
/*      */       
/*  927 */       CorporateStructureObject cso = (CorporateStructureObject)cachedCorporateStructure.elementAt(i);
/*      */ 
/*      */       
/*  930 */       cachedCorporateStructureHash.put(new Integer(cso.getIdentity()), cso);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void initializeUnusedLabels() {
/*  937 */     found = false;
/*  938 */     CorporateStructureObject current = null;
/*  939 */     for (int i = 0; i < cachedCorporateStructure.size(); i++) {
/*      */       
/*  941 */       current = (CorporateStructureObject)cachedCorporateStructure.elementAt(i);
/*  942 */       found = false;
/*  943 */       if (current.getStructureType() == 1) {
/*      */         
/*  945 */         Vector currentFamiliesEnvs = current.getChildren();
/*  946 */         Environment childEnv = null;
/*  947 */         for (int e = 0; e < currentFamiliesEnvs.size(); e++) {
/*      */           
/*  949 */           childEnv = (Environment)currentFamiliesEnvs.elementAt(e);
/*  950 */           Vector currentEnvironmentCompanies = childEnv.getChildren();
/*      */           
/*  952 */           Company childCompany = null;
/*  953 */           for (int c = 0; c < currentEnvironmentCompanies.size(); c++) {
/*      */             
/*  955 */             childCompany = (Company)currentEnvironmentCompanies.elementAt(c);
/*  956 */             Vector currentCompaniesChildren = childCompany.getChildren();
/*      */             
/*  958 */             Division childDivision = null;
/*  959 */             for (int d = 0; d < currentCompaniesChildren.size(); d++) {
/*      */               
/*  961 */               childDivision = (Division)currentCompaniesChildren.elementAt(d);
/*      */               
/*  963 */               Vector currentDivisionChildren = childDivision.getChildren();
/*  964 */               Label childLabel = null;
/*  965 */               found = false;
/*  966 */               for (int l = 0; l < currentDivisionChildren.size(); l++) {
/*      */                 
/*  968 */                 childLabel = (Label)currentDivisionChildren.elementAt(l);
/*  969 */                 Label usedLabel = null;
/*  970 */                 Vector labelsUsed = getUsedLabels();
/*      */ 
/*      */ 
/*      */                 
/*  974 */                 childLabel.setIsUsed(false);
/*  975 */                 for (int u = 0; u < labelsUsed.size(); u++) {
/*      */                   
/*  977 */                   usedLabel = (Label)labelsUsed.elementAt(u);
/*      */                   
/*  979 */                   if (childLabel.getStructureID() == usedLabel.getStructureID()) {
/*      */                     
/*  981 */                     childLabel.setIsUsed(true);
/*  982 */                     found = true;
/*      */                     
/*      */                     break;
/*      */                   } 
/*      */                 } 
/*      */               } 
/*      */               
/*  989 */               if (!found) {
/*      */                 
/*  991 */                 if (!childDivision.getIsUsed()) {
/*  992 */                   childDivision.setIsUsed(false);
/*      */                 }
/*  994 */                 if (!childCompany.getIsUsed()) {
/*  995 */                   childCompany.setIsUsed(false);
/*      */                 }
/*  997 */                 if (!childEnv.getIsUsed()) {
/*  998 */                   childEnv.setIsUsed(false);
/*      */                 }
/* 1000 */                 if (!current.getIsUsed()) {
/* 1001 */                   current.setIsUsed(false);
/*      */                 
/*      */                 }
/*      */               }
/*      */               else {
/*      */                 
/* 1007 */                 childDivision.setIsUsed(true);
/* 1008 */                 childCompany.setIsUsed(true);
/* 1009 */                 childEnv.setIsUsed(true);
/* 1010 */                 current.setIsUsed(true);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getFamilies() {
/* 1030 */     if (cachedFamilies == null) {
/*      */       
/* 1032 */       log.debug("Retrieved new Family cache.");
/*      */       
/* 1034 */       cachedFamilies = new Vector();
/* 1035 */       csos = getCorporateStructure();
/*      */ 
/*      */ 
/*      */       
/* 1039 */       for (int i = 0; i < csos.size(); i++) {
/*      */         
/* 1041 */         CorporateStructureObject cso = (CorporateStructureObject)csos.elementAt(i);
/* 1042 */         if (cso instanceof Family) {
/* 1043 */           cachedFamilies.addElement(cso);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1048 */     return cachedFamilies;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getCompanies() {
/* 1057 */     if (cachedCompanies == null) {
/*      */       
/* 1059 */       log.debug("creating new Company cache.");
/* 1060 */       cachedCompanies = new Vector();
/* 1061 */       csos = getCorporateStructure();
/*      */ 
/*      */ 
/*      */       
/* 1065 */       for (int i = 0; i < csos.size(); i++) {
/*      */         
/* 1067 */         CorporateStructureObject cso = (CorporateStructureObject)csos.elementAt(i);
/* 1068 */         if (cso instanceof Company && cso.getParent() != null) {
/* 1069 */           cachedCompanies.addElement(cso);
/*      */         }
/*      */       } 
/*      */     } 
/* 1073 */     return cachedCompanies;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getDivisions() {
/* 1082 */     if (cachedDivisions == null) {
/*      */       
/* 1084 */       log.debug("Creating new Division cache.");
/* 1085 */       cachedDivisions = new Vector();
/* 1086 */       csos = getCorporateStructure();
/*      */ 
/*      */ 
/*      */       
/* 1090 */       for (int i = 0; i < csos.size(); i++) {
/*      */         
/* 1092 */         CorporateStructureObject cso = (CorporateStructureObject)csos.elementAt(i);
/* 1093 */         if (cso instanceof Division && cso.getParent() != null) {
/* 1094 */           cachedDivisions.addElement(cso);
/*      */         }
/*      */       } 
/*      */     } 
/* 1098 */     return cachedDivisions;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getLabels() {
/* 1107 */     if (cachedLabels == null) {
/*      */       
/* 1109 */       log.debug("creating new Label cache.");
/* 1110 */       cachedLabels = new Vector();
/* 1111 */       csos = getCorporateStructure();
/*      */ 
/*      */ 
/*      */       
/* 1115 */       for (int i = 0; i < csos.size(); i++) {
/*      */         
/* 1117 */         CorporateStructureObject cso = (CorporateStructureObject)csos.elementAt(i);
/* 1118 */         if (cso instanceof Label && cso.getParent() != null) {
/* 1119 */           cachedLabels.addElement(cso);
/*      */         }
/*      */       } 
/*      */     } 
/* 1123 */     return cachedLabels;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getSellCodes() {
/* 1131 */     if (sellCodes == null) {
/*      */ 
/*      */       
/* 1134 */       query = "SELECT Sell_Code FROM vi_Price_Code where isDigital is null or isDigital = 0 ORDER BY Sell_Code;";
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1139 */       sellCodes = getCachedVectorOfStrings(sellCodes, query, "Sell_Code");
/*      */     } 
/*      */     
/* 1142 */     return sellCodes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getSellCodesDPC() {
/* 1150 */     if (sellCodesDPC == null) {
/*      */       
/* 1152 */       log.debug("Retrieved SellCodes.");
/*      */       
/* 1154 */       query = "SELECT Sell_Code FROM vi_Price_Code where isDigital = 1 ORDER BY Sell_Code;";
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1159 */       sellCodesDPC = getCachedVectorOfStrings(sellCodesDPC, query, "Sell_Code");
/*      */     } 
/*      */ 
/*      */     
/* 1163 */     return sellCodesDPC;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getPriceCodes() {
/* 1172 */     if (priceCodes == null) {
/*      */       
/* 1174 */       log.debug("Retrieved PriceCodes.");
/*      */       
/* 1176 */       precache = new Vector();
/*      */       
/* 1178 */       String priceCodeQuery = "SELECT * FROM vi_Price_Code";
/* 1179 */       JdbcConnector connector = MilestoneHelper.getConnector(priceCodeQuery);
/* 1180 */       connector.runQuery();
/*      */       
/* 1182 */       PriceCode priceCode = null;
/*      */       
/* 1184 */       while (connector.more()) {
/*      */         
/* 1186 */         priceCode = new PriceCode();
/*      */ 
/*      */         
/* 1189 */         priceCode.setSellCode(connector.getField("sell_code"));
/*      */ 
/*      */         
/* 1192 */         priceCode.setRetailCode(connector.getField("retail_code", ""));
/*      */ 
/*      */         
/* 1195 */         priceCode.setUnits(connector.getIntegerField("units"));
/*      */ 
/*      */         
/* 1198 */         priceCode.setPricePoint(connector.getField("price_point", ""));
/*      */ 
/*      */         
/* 1201 */         priceCode.setDescription(connector.getField("description", ""));
/*      */ 
/*      */         
/* 1204 */         priceCode.setUnitCost(connector.getIntegerField("units"));
/*      */ 
/*      */         
/* 1207 */         priceCode.setTotalCost(connector.getFloat("total_cost"));
/*      */ 
/*      */         
/* 1210 */         priceCode.setIsDigital(connector.getBoolean("isDigital"));
/*      */ 
/*      */         
/* 1213 */         precache.add(priceCode);
/*      */         
/* 1215 */         connector.next();
/*      */       } 
/*      */       
/* 1218 */       connector.close();
/*      */       
/* 1220 */       priceCodes = precache;
/*      */     } 
/*      */ 
/*      */     
/* 1224 */     return priceCodes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getSelectionConfigs() {
/* 1235 */     if (selectionConfigs == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1240 */       query = "SELECT * FROM vi_Lookup_Detail WHERE Field_id = 3 ORDER BY value;";
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1245 */       JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 1246 */       connector.runQuery();
/*      */       
/* 1248 */       Vector configs = new Vector();
/*      */       
/* 1250 */       while (connector.more()) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1255 */         SelectionConfiguration config = new SelectionConfiguration(
/* 1256 */             connector.getField("value", ""), 
/* 1257 */             connector.getField("description", ""), 
/* 1258 */             new Vector(), 
/* 1259 */             connector.getBoolean("inactive"), 
/* 1260 */             connector.getIntegerField("productType"), 
/* 1261 */             connector.getIntegerField("newBundle"));
/*      */ 
/*      */         
/* 1264 */         configs.addElement(config);
/* 1265 */         connector.next();
/*      */       } 
/*      */ 
/*      */       
/* 1269 */       connector.close();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1291 */       String parentquery = "SELECT * FROM vi_Lookup_Subdetail WHERE field_id = 4 ORDER BY field_id, det_value, description;";
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1296 */       JdbcConnector connector2 = MilestoneHelper.getScrollableConnector(parentquery);
/* 1297 */       connector2.runQuery();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1302 */       query = "SELECT * FROM vi_Lookup_Subdetail WHERE field_id = 3 ORDER BY det_value, description;";
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1307 */       connector = MilestoneHelper.getScrollableConnector(query);
/* 1308 */       connector.runQuery();
/*      */       
/* 1310 */       if (connector.more()) {
/*      */         
/* 1312 */         log.debug("Loading subdetails. " + connector.getRowCount());
/* 1313 */         for (int i = 0; i < connector.getRowCount(); i++) {
/*      */           
/* 1315 */           String currentConfig = "";
/*      */           
/* 1317 */           if (connector.more() && connector.getIntegerField("field_id") == 3) {
/*      */ 
/*      */             
/* 1320 */             currentConfig = connector.getField("det_value", "");
/* 1321 */             Vector subConfigs = new Vector();
/*      */             
/* 1323 */             log.debug("  processing subdetails. " + currentConfig);
/*      */             
/* 1325 */             while (connector.more() && currentConfig.equals(connector.getField("det_value", ""))) {
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1330 */               String abbrev = connector.getField("sub_value", "");
/* 1331 */               String name = connector.getField("description", "");
/* 1332 */               SelectionSubConfiguration subConfig = new SelectionSubConfiguration(abbrev, name);
/*      */ 
/*      */               
/* 1335 */               connector2.first();
/*      */               
/* 1337 */               boolean foundParent = false;
/*      */               
/* 1339 */               while (connector2.more() && !foundParent) {
/*      */                 
/* 1341 */                 if (connector2.getField("det_value").equals(subConfig.getSelectionSubConfigurationAbbreviation())) {
/*      */                   
/* 1343 */                   if (connector2.getField("description").equalsIgnoreCase("Y")) {
/*      */                     
/* 1345 */                     subConfig.setParent(true);
/* 1346 */                     foundParent = true;
/*      */                   } 
/*      */                   
/* 1349 */                   subConfig.setInactive(connector2.getBoolean("inactive"));
/* 1350 */                   subConfig.setProdType(connector2.getIntegerField("productType"));
/*      */                 } 
/* 1352 */                 connector2.next();
/*      */               } 
/*      */               
/* 1355 */               subConfigs.addElement(subConfig);
/*      */               
/* 1357 */               connector.next();
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1366 */             SelectionConfiguration config = (SelectionConfiguration)configs.elementAt(i);
/*      */             
/* 1368 */             if (currentConfig.equals(config.getSelectionConfigurationAbbreviation()))
/*      */             {
/* 1370 */               config.setSubConfigurations(subConfigs);
/*      */             
/*      */             }
/*      */             else
/*      */             {
/* 1375 */               log.log("There was problem.  Some subconfigs found did not have a matching parent configuration");
/*      */             }
/*      */           
/*      */           } else {
/*      */             
/* 1380 */             connector.next();
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1385 */       selectionConfigs = configs;
/*      */       
/* 1387 */       connector.close();
/* 1388 */       connector2.close();
/*      */     } 
/*      */     
/* 1391 */     return selectionConfigs;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1632 */   public static Vector getCompanyList() { return getCompanies(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getReleaseTypes() {
/* 1769 */     if (releaseTypes == null) {
/*      */       
/* 1771 */       preCache = new Vector();
/* 1772 */       String className = "com.universal.milestone.ReleaseType";
/* 1773 */       if (lookupObjects.containsKey(String.valueOf(2)))
/* 1774 */         lookupObjects.remove(String.valueOf(2)); 
/* 1775 */       preCache = getLookupDetailValuesFromDatabase(className, 2);
/* 1776 */       releaseTypes = preCache;
/*      */     } 
/*      */     
/* 1779 */     return releaseTypes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getGenres() {
/* 1789 */     if (genres == null) {
/*      */       
/* 1791 */       vGenres = new Vector();
/* 1792 */       String className = "com.universal.milestone.Genre";
/* 1793 */       if (lookupObjects.containsKey(String.valueOf(17)))
/* 1794 */         lookupObjects.remove(String.valueOf(17)); 
/* 1795 */       vGenres = getLookupDetailValuesFromDatabase(className, 17);
/* 1796 */       genres = vGenres;
/*      */     } 
/*      */     
/* 1799 */     return genres;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getProductCategories() {
/* 1807 */     if (productCategories == null) {
/*      */       
/* 1809 */       className = "com.universal.milestone.ProductCategory";
/* 1810 */       if (lookupObjects.containsKey(String.valueOf(1)))
/* 1811 */         lookupObjects.remove(String.valueOf(1)); 
/* 1812 */       Vector preCache = getLookupDetailValuesFromDatabase(className, 1);
/* 1813 */       productCategories = preCache;
/*      */     } 
/*      */     
/* 1816 */     return productCategories;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getProductCategories(int type) {
/* 1824 */     Vector prodCats = new Vector();
/* 1825 */     Vector allProdCats = getProductCategories();
/*      */     
/* 1827 */     for (int i = 0; i < allProdCats.size(); i++) {
/*      */ 
/*      */       
/* 1830 */       if (((LookupObject)allProdCats.get(i)).getProdType() == type || ((LookupObject)allProdCats.get(i)).getProdType() == 2) {
/* 1831 */         prodCats.add(allProdCats.get(i));
/*      */       }
/*      */     } 
/* 1834 */     return prodCats;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getSelectionStatusList() {
/* 1843 */     if (selectionStatusList == null) {
/*      */       
/* 1845 */       className = "com.universal.milestone.SelectionStatus";
/* 1846 */       if (lookupObjects.containsKey(String.valueOf(15)))
/* 1847 */         lookupObjects.remove(String.valueOf(15)); 
/* 1848 */       Vector preCache = getLookupDetailValuesFromDatabase(className, 15);
/* 1849 */       selectionStatusList = preCache;
/*      */     } 
/*      */     
/* 1852 */     return selectionStatusList;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getPrefixCodes() {
/* 1900 */     if (prefixCodes == null) {
/*      */       
/* 1902 */       query = "SELECT * FROM vi_lookup_subdetail WHERE Field_id = 27 ORDER BY det_value";
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1907 */       JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 1908 */       connector.runQuery();
/* 1909 */       Vector prefixs = new Vector();
/*      */ 
/*      */       
/* 1912 */       while (connector.more()) {
/*      */         
/* 1914 */         String abbrev = connector.getField("det_value");
/* 1915 */         int subValue = connector.getIntegerField("sub_value");
/* 1916 */         boolean inactive = connector.getBoolean("inactive");
/* 1917 */         int prodType = connector.getIntegerField("productType");
/* 1918 */         prefixs.addElement(new PrefixCode(abbrev, subValue, inactive, prodType));
/* 1919 */         connector.next();
/*      */       } 
/*      */       
/* 1922 */       connector.close();
/*      */       
/* 1924 */       prefixCodes = prefixs;
/*      */     } 
/*      */     
/* 1927 */     return prefixCodes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getTaskAbbreviations() {
/* 1938 */     if (taskAbbreviations == null) {
/*      */       
/* 1940 */       if (lookupObjects.containsKey(String.valueOf(33)))
/* 1941 */         lookupObjects.remove(String.valueOf(33)); 
/* 1942 */       taskAbbreviations = getLookupDetailValuesFromDatabase(33);
/*      */     } 
/* 1944 */     return taskAbbreviations;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getVendors() {
/* 1955 */     if (vendors == null) {
/*      */ 
/*      */ 
/*      */       
/* 1959 */       classObj = null;
/*      */ 
/*      */       
/*      */       try {
/* 1963 */         classObj = Class.forName("com.universal.milestone.LookupObject");
/*      */       }
/* 1965 */       catch (ClassNotFoundException cnfexc) {
/*      */         
/* 1967 */         log.log("ClassNotFoundException: " + cnfexc);
/*      */       } 
/*      */       
/* 1970 */       String query = "SELECT value, a.description as 'desc', b.description as 'subValue' ,a.inactive  FROM vi_lookup_detail a  LEFT JOIN vi_lookup_subdetail b on a.[value] = b.[det_value]  and a.[field_id] = b.[field_id]  WHERE a.[Field_id] = 22 ORDER BY a.[description];";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1978 */       JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 1979 */       connector.runQuery();
/*      */       
/* 1981 */       Vector lookupVector = new Vector();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1988 */       while (connector.more()) {
/*      */ 
/*      */         
/*      */         try {
/* 1992 */           LookupObject lookupObject = (LookupObject)classObj.newInstance();
/*      */           
/* 1994 */           lookupObject.setAbbreviation(connector.getField("value", ""));
/* 1995 */           lookupObject.setName(connector.getField("desc", ""));
/* 1996 */           lookupObject.setSubValue(connector.getField("subValue", ""));
/* 1997 */           lookupObject.setInactive(connector.getBoolean("inactive"));
/* 1998 */           lookupVector.addElement(lookupObject);
/* 1999 */           lookupObject = null;
/*      */         }
/* 2001 */         catch (InstantiationException e) {
/*      */           
/* 2003 */           log.log("InstantiationException");
/*      */         }
/* 2005 */         catch (IllegalAccessException iae) {
/*      */           
/* 2007 */           log.log("IllegalAccessException");
/*      */         } 
/*      */         
/* 2010 */         connector.next();
/*      */       } 
/*      */       
/* 2013 */       connector.close();
/*      */       
/* 2015 */       vendors = lookupVector;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2020 */     return vendors;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getDistributionCodes() {
/* 2030 */     if (distributionCodes == null) {
/*      */       
/* 2032 */       if (lookupObjects.containsKey(String.valueOf(39)))
/* 2033 */         lookupObjects.remove(String.valueOf(39)); 
/* 2034 */       distributionCodes = getLookupDetailValuesFromDatabase(39);
/*      */     } 
/* 2036 */     return distributionCodes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getReleaseCompanies() {
/* 2047 */     if (releaseCompanies == null) {
/*      */       
/* 2049 */       if (lookupObjects.containsKey(String.valueOf(40)))
/* 2050 */         lookupObjects.remove(String.valueOf(40)); 
/* 2051 */       releaseCompanies = getLookupDetailValuesFromDatabase(40);
/*      */     } 
/* 2053 */     return releaseCompanies;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getReportConfigTypes() {
/* 2063 */     if (reportConfigTypes == null) {
/*      */       
/* 2065 */       if (lookupObjects.containsKey(String.valueOf(49)))
/* 2066 */         lookupObjects.remove(String.valueOf(49)); 
/* 2067 */       reportConfigTypes = getLookupDetailValuesFromDatabase(49);
/*      */     } 
/* 2069 */     return reportConfigTypes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getReportConfigFormats() {
/* 2079 */     if (reportConfigFormats == null) {
/*      */       
/* 2081 */       if (lookupObjects.containsKey(String.valueOf(48)))
/* 2082 */         lookupObjects.remove(String.valueOf(48)); 
/* 2083 */       reportConfigFormats = getLookupDetailValuesFromDatabase(48);
/*      */     } 
/* 2085 */     return reportConfigFormats;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getReportConfigStatuses() {
/* 2095 */     if (reportConfigStatuses == null) {
/*      */       
/* 2097 */       if (lookupObjects.containsKey(String.valueOf(51)))
/* 2098 */         lookupObjects.remove(String.valueOf(51)); 
/* 2099 */       reportConfigStatuses = getLookupDetailValuesFromDatabase(51);
/*      */     } 
/* 2101 */     return reportConfigStatuses;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getOperatingCompanies() {
/* 2111 */     if (operatingCompanies == null) {
/*      */       
/* 2113 */       if (lookupObjects.containsKey(String.valueOf(31)))
/* 2114 */         lookupObjects.remove(String.valueOf(31)); 
/* 2115 */       operatingCompanies = getLookupDetailValuesFromDatabase(31);
/*      */     } 
/* 2117 */     return operatingCompanies;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getMusicLines() {
/* 2127 */     if (musicLines == null) {
/*      */       
/* 2129 */       if (lookupObjects.containsKey(String.valueOf(7)))
/* 2130 */         lookupObjects.remove(String.valueOf(7)); 
/* 2131 */       musicLines = getLookupDetailValuesFromDatabase(7);
/*      */     } 
/* 2133 */     return musicLines;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getConfigCodes() {
/* 2143 */     if (configCodes == null) {
/*      */       
/* 2145 */       if (lookupObjects.containsKey(String.valueOf(29)))
/* 2146 */         lookupObjects.remove(String.valueOf(29)); 
/* 2147 */       configCodes = getLookupDetailValuesFromDatabase(29);
/*      */     } 
/* 2149 */     return configCodes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getRepertoireClasses() {
/* 2160 */     if (repClasses == null) {
/*      */       
/* 2162 */       if (lookupObjects.containsKey(String.valueOf(9)))
/* 2163 */         lookupObjects.remove(String.valueOf(9)); 
/* 2164 */       repClasses = getLookupDetailValuesFromDatabase(9);
/*      */     } 
/* 2166 */     return repClasses;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getRepertoireOwners() {
/* 2176 */     if (repOwners == null) {
/*      */       
/* 2178 */       if (lookupObjects.containsKey(String.valueOf(8)))
/* 2179 */         lookupObjects.remove(String.valueOf(8)); 
/* 2180 */       repOwners = getLookupDetailValuesFromDatabase(8);
/*      */     } 
/* 2182 */     return repOwners;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getModifiers() {
/* 2192 */     if (modifiers == null) {
/*      */       
/* 2194 */       if (lookupObjects.containsKey(String.valueOf(30)))
/* 2195 */         lookupObjects.remove(String.valueOf(30)); 
/* 2196 */       modifiers = getLookupDetailValuesFromDatabase(30);
/*      */     } 
/* 2198 */     return modifiers;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getReturnCodes() {
/* 2208 */     if (returnCodes == null) {
/*      */       
/* 2210 */       if (lookupObjects.containsKey(String.valueOf(10)))
/* 2211 */         lookupObjects.remove(String.valueOf(10)); 
/* 2212 */       returnCodes = getLookupDetailValuesFromDatabase(10);
/*      */     } 
/* 2214 */     return returnCodes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getExportFlags() {
/* 2224 */     if (exportFlags == null) {
/*      */       
/* 2226 */       if (lookupObjects.containsKey(String.valueOf(11)))
/* 2227 */         lookupObjects.remove(String.valueOf(11)); 
/* 2228 */       exportFlags = getLookupDetailValuesFromDatabase(11);
/*      */     } 
/* 2230 */     return exportFlags;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getCompanyCodes() {
/* 2240 */     if (companyCodes == null) {
/*      */       
/* 2242 */       if (lookupObjects.containsKey(String.valueOf(36)))
/* 2243 */         lookupObjects.remove(String.valueOf(36)); 
/* 2244 */       companyCodes = getLookupDetailValuesFromDatabase(36);
/*      */     } 
/* 2246 */     return companyCodes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getPoMergeCodes() {
/* 2256 */     if (poMergeCodes == null) {
/*      */       
/* 2258 */       if (lookupObjects.containsKey(String.valueOf(6)))
/* 2259 */         lookupObjects.remove(String.valueOf(6)); 
/* 2260 */       poMergeCodes = getLookupDetailValuesFromDatabase(6);
/*      */     } 
/* 2262 */     return poMergeCodes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getGuaranteeCodes() {
/* 2273 */     if (guaranteeCodes == null) {
/*      */       
/* 2275 */       if (lookupObjects.containsKey(String.valueOf(37)))
/* 2276 */         lookupObjects.remove(String.valueOf(37)); 
/* 2277 */       guaranteeCodes = getLookupDetailValuesFromDatabase(37);
/*      */     } 
/* 2279 */     return guaranteeCodes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getLoosePickExempt() {
/* 2289 */     if (loosePickExemptCodes == null) {
/*      */       
/* 2291 */       if (lookupObjects.containsKey(String.valueOf(60)))
/* 2292 */         lookupObjects.remove(String.valueOf(60)); 
/* 2293 */       loosePickExemptCodes = getLookupDetailValuesFromDatabase(60);
/*      */     } 
/* 2295 */     return loosePickExemptCodes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getCompilationCodes() {
/* 2305 */     if (compilationCodes == null) {
/*      */       
/* 2307 */       if (lookupObjects.containsKey(String.valueOf(57)))
/* 2308 */         lookupObjects.remove(String.valueOf(57)); 
/* 2309 */       compilationCodes = getLookupDetailValuesFromDatabase(57);
/*      */     } 
/* 2311 */     return compilationCodes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getImpRateCodes() {
/* 2321 */     if (impRateCodes == null) {
/*      */       
/* 2323 */       if (lookupObjects.containsKey(String.valueOf(12)))
/* 2324 */         lookupObjects.remove(String.valueOf(12)); 
/* 2325 */       impRateCodes = getLookupDetailValuesFromDatabase(12);
/*      */     } 
/* 2327 */     return impRateCodes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getMusicTypes() {
/* 2352 */     if (musicTypes == null) {
/*      */       
/* 2354 */       vMusicTypes = new Vector();
/* 2355 */       String className = "com.universal.milestone.Genre";
/* 2356 */       if (lookupObjects.containsKey(String.valueOf(13)))
/* 2357 */         lookupObjects.remove(String.valueOf(13)); 
/* 2358 */       vMusicTypes = getLookupDetailValuesFromDatabase(className, 13);
/* 2359 */       musicTypes = vMusicTypes;
/*      */     } 
/* 2361 */     return musicTypes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getSuppliers() {
/* 2371 */     if (suppliers == null) {
/*      */       
/* 2373 */       if (lookupObjects.containsKey(String.valueOf(19)))
/* 2374 */         lookupObjects.remove(String.valueOf(19)); 
/* 2375 */       suppliers = getLookupDetailValuesFromDatabase(19);
/*      */     } 
/* 2377 */     return suppliers;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getNarmExtracts() {
/* 2387 */     if (narm == null) {
/*      */       
/* 2389 */       if (lookupObjects.containsKey(String.valueOf(56)))
/* 2390 */         lookupObjects.remove(String.valueOf(56)); 
/* 2391 */       narm = getLookupDetailValuesFromDatabase(56);
/*      */     } 
/* 2393 */     return narm;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getEncryptionFlags() {
/* 2404 */     if (encryptionFlags == null) {
/*      */       
/* 2406 */       if (lookupObjects.containsKey(String.valueOf(61)))
/* 2407 */         lookupObjects.remove(String.valueOf(61)); 
/* 2408 */       encryptionFlags = getLookupDetailValuesFromDatabase(61);
/*      */     } 
/* 2410 */     return encryptionFlags;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getPricePoints() {
/* 2421 */     if (pricePoints == null) {
/*      */       
/* 2423 */       if (lookupObjects.containsKey(String.valueOf(14)))
/* 2424 */         lookupObjects.remove(String.valueOf(14)); 
/* 2425 */       pricePoints = getLookupDetailValuesFromDatabase(14);
/*      */     } 
/* 2427 */     return pricePoints;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getImportIndicators() {
/* 2437 */     if (importIndicators == null) {
/*      */       
/* 2439 */       if (lookupObjects.containsKey(String.valueOf(54)))
/* 2440 */         lookupObjects.remove(String.valueOf(54)); 
/* 2441 */       importIndicators = getLookupDetailValuesFromDatabase(54);
/*      */     } 
/* 2443 */     return importIndicators;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getFormats() {
/* 2453 */     if (formats == null) {
/*      */       
/* 2455 */       if (lookupObjects.containsKey(String.valueOf(58)))
/* 2456 */         lookupObjects.remove(String.valueOf(58)); 
/* 2457 */       formats = getLookupDetailValuesFromDatabase(58);
/*      */     } 
/* 2459 */     return formats;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getUmlUsers() {
/* 2470 */     if (umlUsers == null) {
/*      */ 
/*      */ 
/*      */       
/* 2474 */       families = getFamilies();
/* 2475 */       int umlId = -1;
/*      */       
/* 2477 */       for (int i = 0; i < families.size(); i++) {
/* 2478 */         CorporateStructureObject family = (CorporateStructureObject)families.get(i);
/*      */         
/* 2480 */         String familyName = family.getName().trim();
/*      */         
/* 2482 */         if (familyName.equalsIgnoreCase("UML")) {
/* 2483 */           umlId = family.getStructureID();
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/* 2488 */       Vector precache = new Vector();
/* 2489 */       Vector users = getAllUsers();
/* 2490 */       for (int i = 0; i < users.size(); i++) {
/*      */         
/* 2492 */         User user = (User)users.get(i);
/*      */         
/* 2494 */         if (user.getEmployedBy() == umlId) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2501 */           boolean uniqueUMLContact = true;
/* 2502 */           for (int j = 0; j < precache.size(); j++) {
/*      */             
/* 2504 */             User umlusr = (User)precache.get(j);
/* 2505 */             String userName = user.getName().trim();
/* 2506 */             String umlusrName = umlusr.getName().trim();
/* 2507 */             if (userName.equalsIgnoreCase(umlusrName))
/*      */             {
/*      */               
/* 2510 */               uniqueUMLContact = false;
/*      */             }
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 2516 */           if (uniqueUMLContact)
/* 2517 */             precache.add(user); 
/*      */         } 
/*      */       } 
/* 2520 */       umlUsers = precache;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2565 */     return umlUsers;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Hashtable getAllUsersHash() {
/* 2576 */     if (allUsers == null) {
/* 2577 */       getAllUsers();
/*      */     }
/* 2579 */     return allUsersHash;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getAllUsers() {
/* 2590 */     synchronized (lockObject) {
/*      */ 
/*      */       
/* 2593 */       if (allUsers == null) {
/*      */         
/* 2595 */         allUsersHash = null;
/* 2596 */         allUsersHash = new Hashtable();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2605 */         String query = "sp_get_User_All";
/* 2606 */         JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 2607 */         connector.runQuery();
/*      */         
/* 2609 */         Vector precache = new Vector();
/*      */ 
/*      */         
/* 2612 */         while (connector.more()) {
/*      */           
/* 2614 */           User addUser = UserManager.getInstance().getUserObject(connector);
/* 2615 */           precache.add(addUser);
/* 2616 */           allUsersHash.put(String.valueOf(addUser.getUserId()), addUser);
/* 2617 */           connector.next();
/*      */         } 
/*      */         
/* 2620 */         connector.close();
/* 2621 */         allUsers = precache;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2626 */         umlUsers = null;
/* 2627 */         labelUsers = null;
/* 2628 */         buildUserCacheObjects();
/*      */       } 
/*      */     } 
/* 2631 */     return allUsers;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void refreshCacheAllUsers(int userId) {
/* 2642 */     synchronized (lockObject) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2652 */       String query = "sp_get_User_All " + userId;
/* 2653 */       JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 2654 */       connector.runQuery();
/*      */ 
/*      */       
/* 2657 */       if (!connector.more()) {
/*      */         
/* 2659 */         for (int i = 0; i < allUsers.size(); i++) {
/*      */           
/* 2661 */           User user = (User)allUsers.get(i);
/* 2662 */           if (user.getUserId() == userId) {
/*      */             
/* 2664 */             allUsers.remove(i);
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/* 2669 */         allUsersHash.remove(String.valueOf(userId));
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 2674 */         User refreshUser = UserManager.getInstance().getUserObject(connector);
/* 2675 */         boolean refreshed = false;
/*      */         
/* 2677 */         for (int i = 0; i < allUsers.size(); i++) {
/* 2678 */           User user = (User)allUsers.get(i);
/* 2679 */           if (user.getUserId() == refreshUser.getUserId()) {
/* 2680 */             allUsers.set(i, refreshUser);
/* 2681 */             refreshed = true;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/* 2686 */         if (!refreshed) {
/* 2687 */           allUsers.add(refreshUser);
/*      */         }
/*      */         
/* 2690 */         allUsersHash.put(String.valueOf(refreshUser.getUserId()), refreshUser);
/*      */       } 
/*      */       
/* 2693 */       connector.close();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2699 */     umlUsers = null;
/* 2700 */     labelUsers = null;
/* 2701 */     buildUserCacheObjects();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getDatePeriods() {
/* 2712 */     if (datePeriods == null) {
/*      */       
/* 2714 */       query = "SELECT * FROM vi_Date_Period ORDER BY start_date;";
/*      */ 
/*      */ 
/*      */       
/* 2718 */       JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 2719 */       connector.runQuery();
/*      */       
/* 2721 */       Vector precache = new Vector();
/*      */ 
/*      */       
/* 2724 */       while (connector.more()) {
/*      */         
/* 2726 */         int id = connector.getIntegerField("per_id");
/* 2727 */         String name = connector.getField("name");
/* 2728 */         String cycle = connector.getField("cycle");
/* 2729 */         Calendar startDate = MilestoneHelper.getDatabaseDate(connector.getField("start_date", ""));
/* 2730 */         Calendar endDate = MilestoneHelper.getDatabaseDate(connector.getField("end_date", ""));
/* 2731 */         Calendar solDate = MilestoneHelper.getDatabaseDate(connector.getField("sol_date", ""));
/*      */         
/* 2733 */         precache.addElement(new DatePeriod(id, name, cycle, startDate, endDate, solDate));
/* 2734 */         connector.next();
/*      */       } 
/*      */       
/* 2737 */       connector.close();
/*      */       
/* 2739 */       datePeriods = precache;
/*      */     } 
/*      */     
/* 2742 */     return datePeriods;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2753 */   public static Vector getLookupDetailValuesFromDatabase(int fieldId) { return getLookupDetailValuesFromDatabase("com.universal.milestone.LookupObject", fieldId); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getLookupDetailValuesFromDatabase(String className, int fieldId) { // Byte code:
/*      */     //   0: aconst_null
/*      */     //   1: astore_2
/*      */     //   2: getstatic com/universal/milestone/Cache.lookupObjects : Ljava/util/Hashtable;
/*      */     //   5: ifnull -> 21
/*      */     //   8: getstatic com/universal/milestone/Cache.lookupObjects : Ljava/util/Hashtable;
/*      */     //   11: iload_1
/*      */     //   12: invokestatic valueOf : (I)Ljava/lang/String;
/*      */     //   15: invokevirtual containsKey : (Ljava/lang/Object;)Z
/*      */     //   18: ifne -> 464
/*      */     //   21: aconst_null
/*      */     //   22: astore_3
/*      */     //   23: aload_0
/*      */     //   24: invokestatic forName : (Ljava/lang/String;)Ljava/lang/Class;
/*      */     //   27: astore_3
/*      */     //   28: goto -> 57
/*      */     //   31: astore #4
/*      */     //   33: getstatic com/universal/milestone/Cache.log : Lcom/techempower/ComponentLog;
/*      */     //   36: new java/lang/StringBuilder
/*      */     //   39: dup
/*      */     //   40: ldc_w 'ClassNotFoundException: '
/*      */     //   43: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   46: aload #4
/*      */     //   48: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
/*      */     //   51: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   54: invokevirtual log : (Ljava/lang/String;)V
/*      */     //   57: new java/lang/StringBuilder
/*      */     //   60: dup
/*      */     //   61: ldc_w 'SELECT * FROM vi_lookup_detail WHERE Field_id = '
/*      */     //   64: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   67: iload_1
/*      */     //   68: invokevirtual append : (I)Ljava/lang/StringBuilder;
/*      */     //   71: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   74: astore #4
/*      */     //   76: getstatic com/universal/milestone/Cache.sortLookupTableByValueDesc : Ljava/util/Hashtable;
/*      */     //   79: new java/lang/Integer
/*      */     //   82: dup
/*      */     //   83: iload_1
/*      */     //   84: invokespecial <init> : (I)V
/*      */     //   87: invokevirtual containsKey : (Ljava/lang/Object;)Z
/*      */     //   90: ifeq -> 145
/*      */     //   93: getstatic com/universal/milestone/Cache.sortLookupTableByValueDesc : Ljava/util/Hashtable;
/*      */     //   96: new java/lang/Integer
/*      */     //   99: dup
/*      */     //   100: iload_1
/*      */     //   101: invokespecial <init> : (I)V
/*      */     //   104: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   107: checkcast java/lang/String
/*      */     //   110: ldc_w '1'
/*      */     //   113: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
/*      */     //   116: ifeq -> 145
/*      */     //   119: new java/lang/StringBuilder
/*      */     //   122: dup
/*      */     //   123: aload #4
/*      */     //   125: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
/*      */     //   128: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   131: ldc_w ' ORDER BY value, description;'
/*      */     //   134: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   137: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   140: astore #4
/*      */     //   142: goto -> 168
/*      */     //   145: new java/lang/StringBuilder
/*      */     //   148: dup
/*      */     //   149: aload #4
/*      */     //   151: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
/*      */     //   154: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   157: ldc_w ' ORDER BY description;'
/*      */     //   160: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   163: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   166: astore #4
/*      */     //   168: aload #4
/*      */     //   170: invokestatic getConnector : (Ljava/lang/String;)Lcom/universal/milestone/JdbcConnector;
/*      */     //   173: astore #5
/*      */     //   175: aload #5
/*      */     //   177: invokevirtual runQuery : ()V
/*      */     //   180: new java/util/Vector
/*      */     //   183: dup
/*      */     //   184: invokespecial <init> : ()V
/*      */     //   187: astore_2
/*      */     //   188: new java/util/Vector
/*      */     //   191: dup
/*      */     //   192: invokespecial <init> : ()V
/*      */     //   195: astore #6
/*      */     //   197: goto -> 342
/*      */     //   200: aload_3
/*      */     //   201: invokevirtual newInstance : ()Ljava/lang/Object;
/*      */     //   204: checkcast com/universal/milestone/LookupObject
/*      */     //   207: astore #7
/*      */     //   209: aload #7
/*      */     //   211: aload #5
/*      */     //   213: ldc_w 'value'
/*      */     //   216: ldc_w ''
/*      */     //   219: invokevirtual getField : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
/*      */     //   222: invokevirtual setAbbreviation : (Ljava/lang/String;)V
/*      */     //   225: aload #7
/*      */     //   227: aload #5
/*      */     //   229: ldc_w 'description'
/*      */     //   232: ldc_w ''
/*      */     //   235: invokevirtual getField : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
/*      */     //   238: invokevirtual setName : (Ljava/lang/String;)V
/*      */     //   241: aload #7
/*      */     //   243: aload #5
/*      */     //   245: ldc_w 'inactive'
/*      */     //   248: invokevirtual getBoolean : (Ljava/lang/String;)Z
/*      */     //   251: invokevirtual setInactive : (Z)V
/*      */     //   254: aload #7
/*      */     //   256: aload #5
/*      */     //   258: ldc_w 'productType'
/*      */     //   261: invokevirtual getIntegerField : (Ljava/lang/String;)I
/*      */     //   264: invokevirtual setProdType : (I)V
/*      */     //   267: aload #7
/*      */     //   269: aload #5
/*      */     //   271: ldc_w 'isDigitalEquivalent'
/*      */     //   274: invokevirtual getBoolean : (Ljava/lang/String;)Z
/*      */     //   277: invokevirtual setIsDigitalEquivalent : (Z)V
/*      */     //   280: iload_1
/*      */     //   281: bipush #9
/*      */     //   283: if_icmpne -> 302
/*      */     //   286: aload #7
/*      */     //   288: aload #5
/*      */     //   290: ldc_w 'sub_description'
/*      */     //   293: ldc_w ''
/*      */     //   296: invokevirtual getField : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
/*      */     //   299: invokevirtual setSubValue : (Ljava/lang/String;)V
/*      */     //   302: aload #6
/*      */     //   304: aload #7
/*      */     //   306: invokevirtual addElement : (Ljava/lang/Object;)V
/*      */     //   309: goto -> 337
/*      */     //   312: astore #7
/*      */     //   314: getstatic com/universal/milestone/Cache.log : Lcom/techempower/ComponentLog;
/*      */     //   317: ldc_w 'InstantiationException'
/*      */     //   320: invokevirtual log : (Ljava/lang/String;)V
/*      */     //   323: goto -> 337
/*      */     //   326: astore #7
/*      */     //   328: getstatic com/universal/milestone/Cache.log : Lcom/techempower/ComponentLog;
/*      */     //   331: ldc_w 'IllegalAccessException'
/*      */     //   334: invokevirtual log : (Ljava/lang/String;)V
/*      */     //   337: aload #5
/*      */     //   339: invokevirtual next : ()V
/*      */     //   342: aload #5
/*      */     //   344: invokevirtual more : ()Z
/*      */     //   347: ifne -> 200
/*      */     //   350: iload_1
/*      */     //   351: iconst_5
/*      */     //   352: if_icmpne -> 422
/*      */     //   355: aload #6
/*      */     //   357: invokevirtual clone : ()Ljava/lang/Object;
/*      */     //   360: checkcast java/util/Vector
/*      */     //   363: astore_2
/*      */     //   364: iconst_0
/*      */     //   365: istore #7
/*      */     //   367: goto -> 409
/*      */     //   370: aload_2
/*      */     //   371: aload #6
/*      */     //   373: iload #7
/*      */     //   375: invokevirtual elementAt : (I)Ljava/lang/Object;
/*      */     //   378: new com/universal/milestone/Day
/*      */     //   381: dup
/*      */     //   382: aload #6
/*      */     //   384: iload #7
/*      */     //   386: invokevirtual elementAt : (I)Ljava/lang/Object;
/*      */     //   389: checkcast com/universal/milestone/LookupObject
/*      */     //   392: invokevirtual getAbbreviation : ()Ljava/lang/String;
/*      */     //   395: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   398: invokevirtual getDayID : ()I
/*      */     //   401: iconst_1
/*      */     //   402: isub
/*      */     //   403: invokevirtual setElementAt : (Ljava/lang/Object;I)V
/*      */     //   406: iinc #7, 1
/*      */     //   409: iload #7
/*      */     //   411: aload #6
/*      */     //   413: invokevirtual size : ()I
/*      */     //   416: if_icmplt -> 370
/*      */     //   419: goto -> 431
/*      */     //   422: aload #6
/*      */     //   424: invokevirtual clone : ()Ljava/lang/Object;
/*      */     //   427: checkcast java/util/Vector
/*      */     //   430: astore_2
/*      */     //   431: aload #5
/*      */     //   433: invokevirtual close : ()V
/*      */     //   436: getstatic com/universal/milestone/Cache.lookupObjects : Ljava/util/Hashtable;
/*      */     //   439: ifnonnull -> 452
/*      */     //   442: new java/util/Hashtable
/*      */     //   445: dup
/*      */     //   446: invokespecial <init> : ()V
/*      */     //   449: putstatic com/universal/milestone/Cache.lookupObjects : Ljava/util/Hashtable;
/*      */     //   452: getstatic com/universal/milestone/Cache.lookupObjects : Ljava/util/Hashtable;
/*      */     //   455: iload_1
/*      */     //   456: invokestatic valueOf : (I)Ljava/lang/String;
/*      */     //   459: aload_2
/*      */     //   460: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   463: pop
/*      */     //   464: getstatic com/universal/milestone/Cache.lookupObjects : Ljava/util/Hashtable;
/*      */     //   467: iload_1
/*      */     //   468: invokestatic valueOf : (I)Ljava/lang/String;
/*      */     //   471: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   474: checkcast java/util/Vector
/*      */     //   477: astore_2
/*      */     //   478: aload_2
/*      */     //   479: areturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #2768	-> 0
/*      */     //   #2771	-> 2
/*      */     //   #2773	-> 21
/*      */     //   #2776	-> 23
/*      */     //   #2777	-> 28
/*      */     //   #2778	-> 31
/*      */     //   #2779	-> 33
/*      */     //   #2782	-> 57
/*      */     //   #2784	-> 67
/*      */     //   #2782	-> 71
/*      */     //   #2788	-> 76
/*      */     //   #2790	-> 93
/*      */     //   #2791	-> 110
/*      */     //   #2792	-> 119
/*      */     //   #2794	-> 142
/*      */     //   #2796	-> 145
/*      */     //   #2798	-> 168
/*      */     //   #2799	-> 175
/*      */     //   #2801	-> 180
/*      */     //   #2802	-> 188
/*      */     //   #2807	-> 197
/*      */     //   #2809	-> 200
/*      */     //   #2810	-> 209
/*      */     //   #2811	-> 225
/*      */     //   #2812	-> 241
/*      */     //   #2813	-> 254
/*      */     //   #2815	-> 267
/*      */     //   #2818	-> 280
/*      */     //   #2819	-> 286
/*      */     //   #2821	-> 302
/*      */     //   #2822	-> 309
/*      */     //   #2823	-> 312
/*      */     //   #2824	-> 314
/*      */     //   #2826	-> 326
/*      */     //   #2827	-> 328
/*      */     //   #2830	-> 337
/*      */     //   #2807	-> 342
/*      */     //   #2834	-> 350
/*      */     //   #2835	-> 355
/*      */     //   #2836	-> 364
/*      */     //   #2837	-> 370
/*      */     //   #2838	-> 378
/*      */     //   #2839	-> 384
/*      */     //   #2838	-> 395
/*      */     //   #2840	-> 398
/*      */     //   #2838	-> 402
/*      */     //   #2837	-> 403
/*      */     //   #2836	-> 406
/*      */     //   #2842	-> 419
/*      */     //   #2844	-> 422
/*      */     //   #2846	-> 431
/*      */     //   #2849	-> 436
/*      */     //   #2850	-> 442
/*      */     //   #2852	-> 452
/*      */     //   #2855	-> 464
/*      */     //   #2856	-> 478
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   0	480	0	className	Ljava/lang/String;
/*      */     //   0	480	1	fieldId	I
/*      */     //   2	478	2	lookupVector	Ljava/util/Vector;
/*      */     //   23	441	3	classObj	Ljava/lang/Class;
/*      */     //   33	24	4	cnfexc	Ljava/lang/ClassNotFoundException;
/*      */     //   76	388	4	query	Ljava/lang/String;
/*      */     //   175	289	5	connector	Lcom/universal/milestone/JdbcConnector;
/*      */     //   197	267	6	tempLookupVector	Ljava/util/Vector;
/*      */     //   209	100	7	lookupObject	Lcom/universal/milestone/LookupObject;
/*      */     //   314	9	7	e	Ljava/lang/InstantiationException;
/*      */     //   328	9	7	iae	Ljava/lang/IllegalAccessException;
/*      */     //   367	52	7	i	I
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   23	28	31	java/lang/ClassNotFoundException
/*      */     //   200	309	312	java/lang/InstantiationException
/*      */     //   200	309	326	java/lang/IllegalAccessException }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getDepartmentSubDescription(String departmentValue) {
/* 2869 */     String query = "SELECT * FROM vi_lookup_SubDetail WHERE Field_id = 18 and det_value = '" + 
/*      */ 
/*      */       
/* 2872 */       departmentValue + "';";
/*      */     
/* 2874 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 2875 */     connector.runQuery();
/*      */     
/* 2877 */     String departmentSubdescription = "";
/*      */     
/* 2879 */     while (connector.more()) {
/*      */       
/* 2881 */       departmentSubdescription = connector.getField("description", "");
/*      */       
/* 2883 */       connector.next();
/*      */     } 
/*      */     
/* 2886 */     connector.close();
/*      */     
/* 2888 */     return departmentSubdescription;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getJavaScriptPriceCodeArray() {
/* 2901 */     if (javaScriptPriceCodeArray == null) {
/*      */       
/* 2903 */       result = new String();
/* 2904 */       Vector vSellCodeList = getSellCodes();
/*      */ 
/*      */       
/* 2907 */       result = String.valueOf(result) + "var p = new Array();\n";
/* 2908 */       String pc = new String();
/*      */       
/* 2910 */       result = String.valueOf(result) + "p[0] = new Array( 0, ' ');\n";
/* 2911 */       int arrayIndex = 0;
/*      */       
/* 2913 */       for (int i = 0; i < vSellCodeList.size(); i++) {
/*      */ 
/*      */         
/* 2916 */         int k = i + 1;
/* 2917 */         pc = (String)vSellCodeList.get(i);
/*      */         
/* 2919 */         String s1 = "p[" + k + "] = new Array(";
/* 2920 */         String s2 = new String();
/* 2921 */         s2 = String.valueOf(s2) + " " + k + ", '" + pc + "',";
/*      */         
/* 2923 */         if (s2.length() > 0) {
/*      */           
/* 2925 */           s2 = s2.substring(0, s2.length() - 1);
/* 2926 */           result = String.valueOf(result) + s1 + s2 + ");\n";
/*      */         }
/*      */         else {
/*      */           
/* 2930 */           result = String.valueOf(result) + s1 + " 0, 'None');\n";
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 2935 */       javaScriptPriceCodeArray = result;
/*      */     } 
/*      */     
/* 2938 */     return javaScriptPriceCodeArray;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getJavaScriptPriceCodeDPCArray() {
/* 2949 */     if (javaScriptPriceCodeDPCArray == null) {
/*      */       
/* 2951 */       result = new String();
/* 2952 */       Vector vSellCodeList = getSellCodesDPC();
/*      */ 
/*      */       
/* 2955 */       result = String.valueOf(result) + "var dpc = new Array();\n";
/* 2956 */       String pc = new String();
/*      */       
/* 2958 */       result = String.valueOf(result) + "dpc[0] = new Array( 0, ' ');\n";
/* 2959 */       int arrayIndex = 0;
/*      */       
/* 2961 */       for (int i = 0; i < vSellCodeList.size(); i++) {
/*      */ 
/*      */         
/* 2964 */         int k = i + 1;
/* 2965 */         pc = (String)vSellCodeList.get(i);
/*      */         
/* 2967 */         String s1 = "dpc[" + k + "] = new Array(";
/* 2968 */         String s2 = new String();
/* 2969 */         s2 = String.valueOf(s2) + " " + k + ", '" + pc + "',";
/*      */         
/* 2971 */         if (s2.length() > 0) {
/*      */           
/* 2973 */           s2 = s2.substring(0, s2.length() - 1);
/* 2974 */           result = String.valueOf(result) + s1 + s2 + ");\n";
/*      */         }
/*      */         else {
/*      */           
/* 2978 */           result = String.valueOf(result) + s1 + " 0, 'None');\n";
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 2983 */       javaScriptPriceCodeDPCArray = result;
/*      */     } 
/*      */     
/* 2986 */     return javaScriptPriceCodeDPCArray;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getJavaScriptPFMConfigs() {
/* 2998 */     results = new String();
/* 2999 */     Vector configCodes = getConfigCodes();
/*      */     
/* 3001 */     int configSize = configCodes.size();
/*      */     
/* 3003 */     results = "PFMconfigs = new Array();\n";
/*      */     
/* 3005 */     for (int x = 0; x < configCodes.size(); x++) {
/*      */ 
/*      */       
/* 3008 */       LookupObject lo = (LookupObject)configCodes.get(x);
/*      */ 
/*      */       
/* 3011 */       results = String.valueOf(results) + "PFMconfigs['" + lo.getAbbreviation() + "'] = " + lo.isDigitalEquivalent + ";\n";
/*      */     } 
/*      */     
/* 3014 */     return results;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getJavaScriptConfigArray(String selectedOption) {
/* 3025 */     if (javaScriptConfigArray == null) {
/*      */       
/* 3027 */       String result = new String();
/* 3028 */       String arraySetup = "";
/* 3029 */       String arrayContents = "";
/* 3030 */       Vector configs = getSelectionConfigs();
/* 3031 */       selectedOption = "";
/*      */       
/* 3033 */       int subConfigSize = 0;
/* 3034 */       int configSize = 0;
/*      */       
/* 3036 */       int sizeActiveConfigs = getActiveConfigSize(configs);
/*      */ 
/*      */       
/* 3039 */       arraySetup = "configs = new Array(" + (sizeActiveConfigs + 1) + ");\n";
/* 3040 */       arraySetup = String.valueOf(arraySetup) + "configs[0] = new Array(1);\n";
/*      */       
/* 3042 */       arraySetup = String.valueOf(arraySetup) + "configsProdType = new Array(" + (sizeActiveConfigs + 1) + ");\n";
/*      */       
/* 3044 */       for (int i = 0; i < configs.size(); i++) {
/*      */         
/* 3046 */         SelectionConfiguration selectionConfig = (SelectionConfiguration)configs.elementAt(i);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3051 */         if (selectionConfig.getInactive())
/*      */         {
/*      */           
/* 3054 */           if (!selectionConfig.getSelectionConfigurationAbbreviation().equalsIgnoreCase(selectedOption)) {
/*      */             continue;
/*      */           }
/*      */         }
/* 3058 */         configSize++;
/* 3059 */         subConfigSize = 0;
/*      */         
/* 3061 */         Vector subConfigs = selectionConfig.getSubConfigurations();
/*      */         
/* 3063 */         int sizeActiveSubConfigs = getActiveSubConfigSize(subConfigs);
/*      */ 
/*      */         
/* 3066 */         arraySetup = String.valueOf(arraySetup) + "configs[" + configSize + "] = new Array(" + sizeActiveSubConfigs + ");\n";
/*      */ 
/*      */         
/* 3069 */         arrayContents = String.valueOf(arrayContents) + "configsProdType[" + configSize + "] = '" + selectionConfig.getSelectionConfigurationName() + "," + selectionConfig.getSelectionConfigurationAbbreviation() + "," + selectionConfig.getProdType() + "," + selectionConfig.getNewBundle() + "';\n";
/*      */         
/* 3071 */         for (int j = 0; j < subConfigs.size(); j++) {
/*      */           
/* 3073 */           SelectionSubConfiguration selectionSubConfig = (SelectionSubConfiguration)subConfigs.elementAt(j);
/*      */ 
/*      */ 
/*      */           
/* 3077 */           if (selectionSubConfig.getInactive())
/*      */           {
/*      */             
/* 3080 */             if (!selectionSubConfig.getSelectionSubConfigurationAbbreviation().equalsIgnoreCase(selectedOption)) {
/*      */               continue;
/*      */             }
/*      */           }
/* 3084 */           subConfigSize++;
/*      */           
/* 3086 */           arrayContents = String.valueOf(arrayContents) + "configs[" + configSize + "][" + subConfigSize + "] = '" + selectionSubConfig.getSelectionSubConfigurationAbbreviation() + "," + selectionSubConfig.getSelectionSubConfigurationName() + "," + selectionSubConfig.isParent() + "," + selectionSubConfig.getProdType() + "';\n"; continue;
/*      */         } 
/*      */         continue;
/*      */       } 
/* 3090 */       result = String.valueOf(result) + arraySetup;
/* 3091 */       result = String.valueOf(result) + "configs[0][0]=',';\n";
/* 3092 */       result = String.valueOf(result) + arrayContents;
/*      */       
/* 3094 */       javaScriptConfigArray = result;
/*      */     } 
/*      */     
/* 3097 */     return javaScriptConfigArray;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getJavaScriptSubConfigArray(String selectedOption) {
/* 3109 */     if (javaScriptSubConfigArray == null) {
/*      */       
/* 3111 */       String result = new String();
/* 3112 */       String arraySetup = "";
/* 3113 */       String arrayContents = "";
/* 3114 */       Vector configs = getSelectionConfigs();
/*      */       
/* 3116 */       int subConfigSize = 0;
/*      */       
/* 3118 */       int sizeActiveConfigs = getActiveConfigSize(configs);
/*      */       
/* 3120 */       for (int i = 0; i < configs.size(); i++) {
/*      */         
/* 3122 */         SelectionConfiguration selectionConfig = (SelectionConfiguration)configs.elementAt(i);
/*      */ 
/*      */ 
/*      */         
/* 3126 */         if (selectionConfig.getInactive())
/*      */         {
/*      */           
/* 3129 */           if (!selectionConfig.getSelectionConfigurationAbbreviation().equalsIgnoreCase(selectedOption)) {
/*      */             continue;
/*      */           }
/*      */         }
/* 3133 */         Vector subConfigs = selectionConfig.getSubConfigurations();
/*      */         
/* 3135 */         int sizeActiveSubConfigs = getActiveSubConfigSize(subConfigs);
/*      */         
/* 3137 */         for (int j = 0; j < subConfigs.size(); j++) {
/*      */           
/* 3139 */           SelectionSubConfiguration selectionSubConfig = (SelectionSubConfiguration)subConfigs.elementAt(j);
/*      */ 
/*      */ 
/*      */           
/* 3143 */           if (selectionSubConfig.getInactive())
/*      */           {
/*      */             
/* 3146 */             if (!selectionSubConfig.getSelectionSubConfigurationAbbreviation().equalsIgnoreCase(selectedOption)) {
/*      */               continue;
/*      */             }
/*      */           }
/* 3150 */           subConfigSize++;
/* 3151 */           arrayContents = String.valueOf(arrayContents) + "subConfigs[" + subConfigSize + "] = '" + selectionSubConfig.getSelectionSubConfigurationAbbreviation() + "," + selectionSubConfig.isParent() + "," + selectionSubConfig.getProdType() + "';\n"; continue;
/*      */         }  continue;
/*      */       } 
/* 3154 */       arraySetup = "subConfigs = new Array(" + subConfigSize + ");\n";
/*      */       
/* 3156 */       result = String.valueOf(result) + arraySetup;
/* 3157 */       result = String.valueOf(result) + arrayContents;
/*      */       
/* 3159 */       javaScriptSubConfigArray = result;
/*      */     } 
/*      */     
/* 3162 */     return javaScriptSubConfigArray;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getBomSuppliers() {
/* 3172 */     if (bomSuppliers == null) {
/*      */       
/* 3174 */       query = "SELECT * FROM vi_Supplier where (inactive is null or inactive = 0)  ORDER BY description;";
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3179 */       JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 3180 */       connector.runQuery();
/*      */       
/* 3182 */       Vector precache = new Vector();
/*      */ 
/*      */       
/* 3185 */       while (connector.more()) {
/*      */         
/* 3187 */         LookupObject lookupObject = new LookupObject();
/* 3188 */         lookupObject.setAbbreviation(connector.getField("supplier_id", ""));
/* 3189 */         lookupObject.setName(connector.getField("description", ""));
/* 3190 */         precache.addElement(lookupObject);
/* 3191 */         connector.next();
/*      */       } 
/*      */       
/* 3194 */       bomSuppliers = precache;
/*      */       
/* 3196 */       connector.close();
/*      */     } 
/*      */     
/* 3199 */     return bomSuppliers;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getDayTypes() {
/* 3214 */     if (dayTypes == null) {
/*      */       
/* 3216 */       query = "SELECT * FROM vi_day_type ORDER BY grouping asc, date desc;";
/*      */ 
/*      */ 
/*      */       
/* 3220 */       JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 3221 */       connector.runQuery();
/*      */       
/* 3223 */       Vector precache = new Vector();
/*      */       
/* 3225 */       Day day = null;
/*      */       
/* 3227 */       int curGroup = -1;
/* 3228 */       int prevGroup = -1;
/* 3229 */       Vector curGroupDates = null;
/*      */       
/* 3231 */       while (connector.more()) {
/*      */         
/* 3233 */         curGroup = connector.getIntegerField("grouping");
/*      */         
/* 3235 */         if (curGroup != prevGroup) {
/* 3236 */           if (curGroupDates != null) {
/* 3237 */             precache.add(curGroupDates);
/*      */           }
/* 3239 */           curGroupDates = new Vector();
/*      */         } 
/*      */         
/* 3242 */         day = new Day(connector.getIntegerField("type_id"));
/*      */ 
/*      */         
/* 3245 */         day.setDescription(connector.getField("description", ""));
/*      */ 
/*      */         
/* 3248 */         day.setCalendarGroup(curGroup);
/*      */ 
/*      */         
/* 3251 */         day.setSpecificDate(MilestoneHelper.getDatabaseDate(connector.getField("date")));
/*      */ 
/*      */         
/* 3254 */         day.setDayType(connector.getField("value", ""));
/*      */ 
/*      */         
/* 3257 */         String lastDateString = connector.getFieldByName("last_updated_on");
/* 3258 */         if (lastDateString != null) {
/* 3259 */           day.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastDateString));
/*      */         }
/*      */         
/* 3262 */         day.setLastUpdatingUser(connector.getIntegerField("last_updated_by"));
/*      */ 
/*      */         
/* 3265 */         long lastUpdatedLong = Long.parseLong(connector.getField("last_updated_ck"), 16);
/* 3266 */         day.setLastUpdatedCk(lastUpdatedLong);
/*      */         
/* 3268 */         curGroupDates.addElement(day);
/* 3269 */         day = null;
/* 3270 */         prevGroup = curGroup;
/* 3271 */         connector.next();
/*      */       } 
/*      */       
/* 3274 */       if (curGroupDates != null) {
/* 3275 */         precache.add(curGroupDates);
/*      */       }
/*      */       
/* 3278 */       dayTypes = precache;
/* 3279 */       connector.close();
/*      */     } 
/*      */     
/* 3282 */     return dayTypes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Hashtable getUserCompaniesTable() {
/* 3290 */     if (userCompaniesTable == null) {
/* 3291 */       userCompaniesTable = new Hashtable();
/*      */     }
/* 3293 */     return userCompaniesTable;
/*      */   }
/*      */   
/*      */   private static void RefreshServers(String pstrParam) {
/*      */     try {
/* 3298 */       if (RefreshServers != null)
/*      */       {
/*      */ 
/*      */ 
/*      */         
/* 3303 */         Iterator it = RefreshServers.iterator();
/* 3304 */         while (it.hasNext()) {
/* 3305 */           String strUrl = String.valueOf(it.next().toString()) + pstrParam;
/* 3306 */           System.out.println("URL: " + strUrl);
/* 3307 */           URL url = new URL(strUrl);
/* 3308 */           URLConnection connMilestone = url.openConnection();
/* 3309 */           connMilestone.getInputStream();
/*      */         }
/*      */       
/*      */       }
/*      */     
/* 3314 */     } catch (Exception e) {
/* 3315 */       System.out.println("FAILED ATTEMPT");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getActiveConfigSize(Vector configs) {
/* 3325 */     int sizeActives = 0;
/* 3326 */     for (int i = 0; i < configs.size(); i++) {
/* 3327 */       SelectionConfiguration selectionConfig = 
/* 3328 */         (SelectionConfiguration)configs.elementAt(i);
/* 3329 */       if (!selectionConfig.getInactive()) {
/* 3330 */         sizeActives++;
/*      */       }
/*      */     } 
/* 3333 */     return sizeActives;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getActiveSubConfigSize(Vector subConfigs) {
/* 3341 */     int sizeActives = 0;
/* 3342 */     for (int i = 0; i < subConfigs.size(); i++) {
/*      */       
/* 3344 */       SelectionSubConfiguration selectionSubConfig = (SelectionSubConfiguration)subConfigs.elementAt(i);
/* 3345 */       if (!selectionSubConfig.getInactive()) {
/* 3346 */         sizeActives++;
/*      */       }
/*      */     } 
/* 3349 */     return sizeActives;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getEnvironments() {
/* 3358 */     synchronized (lockObject) {
/*      */       
/* 3360 */       if (cachedEnvironments == null) {
/*      */         
/* 3362 */         log.debug("creating new Environment cache.");
/* 3363 */         cachedEnvironments = new Vector();
/* 3364 */         Vector csos = getCorporateStructure();
/*      */ 
/*      */ 
/*      */         
/* 3368 */         for (int i = 0; i < csos.size(); i++) {
/*      */           
/* 3370 */           CorporateStructureObject cso = (CorporateStructureObject)csos.elementAt(i);
/* 3371 */           if (cso instanceof Environment) {
/* 3372 */             cachedEnvironments.addElement(cso);
/*      */           }
/*      */         } 
/*      */         
/* 3376 */         sortEnvironmentsByFamily(cachedEnvironments);
/*      */       } 
/*      */     } 
/* 3379 */     return cachedEnvironments;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3387 */   public static Vector getEnvironmentsByFamily() { return cachedEnvironmentsByFamily; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void sortEnvironmentsByFamily(Vector environments) {
/* 3396 */     cachedEnvironmentsByFamily = new Vector();
/*      */ 
/*      */     
/* 3399 */     Object[] environmentArray = environments.toArray();
/*      */ 
/*      */     
/* 3402 */     Arrays.sort(environmentArray, new CorpStructParentNameComparator());
/*      */     
/* 3404 */     for (int j = 0; j < environmentArray.length; j++) {
/* 3405 */       cachedEnvironmentsByFamily.add(environmentArray[j]);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Hashtable getUserEnvironmentsTable() {
/* 3417 */     if (userEnvironmentsTable == null) {
/* 3418 */       userEnvironmentsTable = new Hashtable();
/*      */     }
/* 3420 */     return userEnvironmentsTable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3429 */   public static Vector getEnvironmentList() { return getEnvironments(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getLabelUsers() {
/* 3439 */     if (labelUsers == null) {
/*      */       
/* 3441 */       precache = new Vector();
/*      */       
/* 3443 */       int umlId = MilestoneHelper.getStructureId("UML", 1);
/* 3444 */       int enterpriseId = MilestoneHelper.getStructureId("Enterprise", 1);
/*      */ 
/*      */       
/* 3447 */       String query = "SELECT DISTINCT vi_All_User.Name, vi_All_User.Full_Name, vi_All_User.User_Id FROM vi_All_User, vi_User_Company WHERE (vi_All_User.User_ID = vi_User_Company.User_ID) AND (menu_access LIKE '[1,2]%') AND (vi_All_User.employed_by <> " + 
/*      */ 
/*      */ 
/*      */         
/* 3451 */         umlId + ")" + 
/* 3452 */         " AND (vi_All_User.employed_by IS NOT NULL)" + 
/* 3453 */         " AND (vi_All_User.employed_by <> " + enterpriseId + ")" + 
/* 3454 */         " AND (vi_All_User.employed_by <> 0)" + 
/* 3455 */         " AND (vi_All_user.employed_by <> -1)" + 
/* 3456 */         " AND exists (select 'x' from dbo.release_header rh where rh.contact_id = vi_All_User.[user_id])" + 
/* 3457 */         " ORDER BY vi_All_User.Full_Name;";
/*      */ 
/*      */ 
/*      */       
/* 3461 */       JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 3462 */       connector.runQuery();
/*      */       
/* 3464 */       while (connector.more()) {
/*      */         
/* 3466 */         User labelUser = UserManager.getInstance().getUser(connector.getIntegerField("user_id"));
/* 3467 */         precache.add(labelUser);
/* 3468 */         connector.next();
/*      */       } 
/*      */       
/* 3471 */       connector.close();
/* 3472 */       labelUsers = precache;
/*      */     } 
/*      */     
/* 3475 */     return labelUsers;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void buildUserCacheObjects() {
/* 3490 */     getUmlUsers();
/* 3491 */     getLabelUsers();
/*      */   }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\Cache.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */