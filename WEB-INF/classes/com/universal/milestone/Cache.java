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
/*      */ 
/*      */ 
/*      */                 
/*  973 */                 Vector labelsUsed = (cachedLabelsUsed != null) ? cachedLabelsUsed : getUsedLabels();
/*      */ 
/*      */ 
/*      */                 
/*  977 */                 childLabel.setIsUsed(false);
/*  978 */                 for (int u = 0; u < labelsUsed.size(); u++) {
/*      */                   
/*  980 */                   usedLabel = (Label)labelsUsed.elementAt(u);
/*      */                   
/*  982 */                   if (childLabel.getStructureID() == usedLabel.getStructureID()) {
/*      */                     
/*  984 */                     childLabel.setIsUsed(true);
/*  985 */                     found = true;
/*      */                     
/*      */                     break;
/*      */                   } 
/*      */                 } 
/*      */               } 
/*      */               
/*  992 */               if (!found) {
/*      */                 
/*  994 */                 if (!childDivision.getIsUsed()) {
/*  995 */                   childDivision.setIsUsed(false);
/*      */                 }
/*  997 */                 if (!childCompany.getIsUsed()) {
/*  998 */                   childCompany.setIsUsed(false);
/*      */                 }
/* 1000 */                 if (!childEnv.getIsUsed()) {
/* 1001 */                   childEnv.setIsUsed(false);
/*      */                 }
/* 1003 */                 if (!current.getIsUsed()) {
/* 1004 */                   current.setIsUsed(false);
/*      */                 
/*      */                 }
/*      */               }
/*      */               else {
/*      */                 
/* 1010 */                 childDivision.setIsUsed(true);
/* 1011 */                 childCompany.setIsUsed(true);
/* 1012 */                 childEnv.setIsUsed(true);
/* 1013 */                 current.setIsUsed(true);
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
/* 1033 */     if (cachedFamilies == null) {
/*      */       
/* 1035 */       log.debug("Retrieved new Family cache.");
/*      */       
/* 1037 */       cachedFamilies = new Vector();
/*      */       
/* 1039 */       csos = (cachedCorporateStructure != null) ? cachedCorporateStructure : getCorporateStructure();
/*      */ 
/*      */ 
/*      */       
/* 1043 */       for (int i = 0; i < csos.size(); i++) {
/*      */         
/* 1045 */         CorporateStructureObject cso = (CorporateStructureObject)csos.elementAt(i);
/* 1046 */         if (cso instanceof Family) {
/* 1047 */           cachedFamilies.addElement(cso);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1052 */     return cachedFamilies;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getCompanies() {
/* 1061 */     if (cachedCompanies == null) {
/*      */       
/* 1063 */       log.debug("creating new Company cache.");
/* 1064 */       cachedCompanies = new Vector();
/* 1065 */       csos = (cachedCorporateStructure != null) ? cachedCorporateStructure : getCorporateStructure();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1070 */       for (int i = 0; i < csos.size(); i++) {
/*      */         
/* 1072 */         CorporateStructureObject cso = (CorporateStructureObject)csos.elementAt(i);
/* 1073 */         if (cso instanceof Company && cso.getParent() != null) {
/* 1074 */           cachedCompanies.addElement(cso);
/*      */         }
/*      */       } 
/*      */     } 
/* 1078 */     return cachedCompanies;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getDivisions() {
/* 1087 */     if (cachedDivisions == null) {
/*      */       
/* 1089 */       log.debug("Creating new Division cache.");
/* 1090 */       cachedDivisions = new Vector();
/* 1091 */       csos = (cachedCorporateStructure != null) ? cachedCorporateStructure : getCorporateStructure();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1096 */       for (int i = 0; i < csos.size(); i++) {
/*      */         
/* 1098 */         CorporateStructureObject cso = (CorporateStructureObject)csos.elementAt(i);
/* 1099 */         if (cso instanceof Division && cso.getParent() != null) {
/* 1100 */           cachedDivisions.addElement(cso);
/*      */         }
/*      */       } 
/*      */     } 
/* 1104 */     return cachedDivisions;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getLabels() {
/* 1113 */     if (cachedLabels == null) {
/*      */       
/* 1115 */       log.debug("creating new Label cache.");
/* 1116 */       cachedLabels = new Vector();
/* 1117 */       csos = (cachedCorporateStructure != null) ? cachedCorporateStructure : getCorporateStructure();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1122 */       for (int i = 0; i < csos.size(); i++) {
/*      */         
/* 1124 */         CorporateStructureObject cso = (CorporateStructureObject)csos.elementAt(i);
/* 1125 */         if (cso instanceof Label && cso.getParent() != null) {
/* 1126 */           cachedLabels.addElement(cso);
/*      */         }
/*      */       } 
/*      */     } 
/* 1130 */     return cachedLabels;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getSellCodes() {
/* 1138 */     if (sellCodes == null) {
/*      */ 
/*      */       
/* 1141 */       query = "SELECT Sell_Code FROM vi_Price_Code where isDigital is null or isDigital = 0 ORDER BY Sell_Code;";
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1146 */       sellCodes = getCachedVectorOfStrings(sellCodes, query, "Sell_Code");
/*      */     } 
/*      */     
/* 1149 */     return sellCodes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getSellCodesDPC() {
/* 1157 */     if (sellCodesDPC == null) {
/*      */       
/* 1159 */       log.debug("Retrieved SellCodes.");
/*      */       
/* 1161 */       query = "SELECT Sell_Code FROM vi_Price_Code where isDigital = 1 ORDER BY Sell_Code;";
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1166 */       sellCodesDPC = getCachedVectorOfStrings(sellCodesDPC, query, "Sell_Code");
/*      */     } 
/*      */ 
/*      */     
/* 1170 */     return sellCodesDPC;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getPriceCodes() {
/* 1179 */     if (priceCodes == null) {
/*      */       
/* 1181 */       log.debug("Retrieved PriceCodes.");
/*      */       
/* 1183 */       precache = new Vector();
/*      */       
/* 1185 */       String priceCodeQuery = "SELECT * FROM vi_Price_Code";
/* 1186 */       JdbcConnector connector = MilestoneHelper.getConnector(priceCodeQuery);
/* 1187 */       connector.runQuery();
/*      */       
/* 1189 */       PriceCode priceCode = null;
/*      */       
/* 1191 */       while (connector.more()) {
/*      */         
/* 1193 */         priceCode = new PriceCode();
/*      */ 
/*      */         
/* 1196 */         priceCode.setSellCode(connector.getField("sell_code"));
/*      */ 
/*      */         
/* 1199 */         priceCode.setRetailCode(connector.getField("retail_code", ""));
/*      */ 
/*      */         
/* 1202 */         priceCode.setUnits(connector.getIntegerField("units"));
/*      */ 
/*      */         
/* 1205 */         priceCode.setPricePoint(connector.getField("price_point", ""));
/*      */ 
/*      */         
/* 1208 */         priceCode.setDescription(connector.getField("description", ""));
/*      */ 
/*      */         
/* 1211 */         priceCode.setUnitCost(connector.getIntegerField("units"));
/*      */ 
/*      */         
/* 1214 */         priceCode.setTotalCost(connector.getFloat("total_cost"));
/*      */ 
/*      */         
/* 1217 */         priceCode.setIsDigital(connector.getBoolean("isDigital"));
/*      */ 
/*      */         
/* 1220 */         precache.add(priceCode);
/*      */         
/* 1222 */         connector.next();
/*      */       } 
/*      */       
/* 1225 */       connector.close();
/*      */       
/* 1227 */       priceCodes = precache;
/*      */     } 
/*      */ 
/*      */     
/* 1231 */     return priceCodes;
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
/* 1242 */     if (selectionConfigs == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1247 */       query = "SELECT * FROM vi_Lookup_Detail WHERE Field_id = 3 ORDER BY value;";
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1252 */       JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 1253 */       connector.runQuery();
/*      */       
/* 1255 */       Vector configs = new Vector();
/*      */       
/* 1257 */       while (connector.more()) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1262 */         SelectionConfiguration config = new SelectionConfiguration(
/* 1263 */             connector.getField("value", ""), 
/* 1264 */             connector.getField("description", ""), 
/* 1265 */             new Vector(), 
/* 1266 */             connector.getBoolean("inactive"), 
/* 1267 */             connector.getIntegerField("productType"), 
/* 1268 */             connector.getIntegerField("newBundle"));
/*      */ 
/*      */         
/* 1271 */         configs.addElement(config);
/* 1272 */         connector.next();
/*      */       } 
/*      */ 
/*      */       
/* 1276 */       connector.close();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1298 */       String parentquery = "SELECT * FROM vi_Lookup_Subdetail WHERE field_id = 4 ORDER BY field_id, det_value, description;";
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1303 */       JdbcConnector connector2 = MilestoneHelper.getScrollableConnector(parentquery);
/* 1304 */       connector2.runQuery();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1309 */       query = "SELECT * FROM vi_Lookup_Subdetail WHERE field_id = 3 ORDER BY det_value, description;";
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1314 */       connector = MilestoneHelper.getScrollableConnector(query);
/* 1315 */       connector.runQuery();
/*      */       
/* 1317 */       if (connector.more()) {
/*      */         
/* 1319 */         log.debug("Loading subdetails. " + connector.getRowCount());
/* 1320 */         for (int i = 0; i < connector.getRowCount(); i++) {
/*      */           
/* 1322 */           String currentConfig = "";
/*      */           
/* 1324 */           if (connector.more() && connector.getIntegerField("field_id") == 3) {
/*      */ 
/*      */             
/* 1327 */             currentConfig = connector.getField("det_value", "");
/* 1328 */             Vector subConfigs = new Vector();
/*      */             
/* 1330 */             log.debug("  processing subdetails. " + currentConfig);
/*      */             
/* 1332 */             while (connector.more() && currentConfig.equals(connector.getField("det_value", ""))) {
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1337 */               String abbrev = connector.getField("sub_value", "");
/* 1338 */               String name = connector.getField("description", "");
/* 1339 */               SelectionSubConfiguration subConfig = new SelectionSubConfiguration(abbrev, name);
/*      */ 
/*      */               
/* 1342 */               connector2.first();
/*      */               
/* 1344 */               boolean foundParent = false;
/*      */               
/* 1346 */               while (connector2.more() && !foundParent) {
/*      */                 
/* 1348 */                 if (connector2.getField("det_value").equals(subConfig.getSelectionSubConfigurationAbbreviation())) {
/*      */                   
/* 1350 */                   if (connector2.getField("description").equalsIgnoreCase("Y")) {
/*      */                     
/* 1352 */                     subConfig.setParent(true);
/* 1353 */                     foundParent = true;
/*      */                   } 
/*      */                   
/* 1356 */                   subConfig.setInactive(connector2.getBoolean("inactive"));
/* 1357 */                   subConfig.setProdType(connector2.getIntegerField("productType"));
/*      */                 } 
/* 1359 */                 connector2.next();
/*      */               } 
/*      */               
/* 1362 */               subConfigs.addElement(subConfig);
/*      */               
/* 1364 */               connector.next();
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1373 */             SelectionConfiguration config = (SelectionConfiguration)configs.elementAt(i);
/*      */             
/* 1375 */             if (currentConfig.equals(config.getSelectionConfigurationAbbreviation()))
/*      */             {
/* 1377 */               config.setSubConfigurations(subConfigs);
/*      */             
/*      */             }
/*      */             else
/*      */             {
/* 1382 */               log.log("There was problem.  Some subconfigs found did not have a matching parent configuration");
/*      */             }
/*      */           
/*      */           } else {
/*      */             
/* 1387 */             connector.next();
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1392 */       selectionConfigs = configs;
/*      */       
/* 1394 */       connector.close();
/* 1395 */       connector2.close();
/*      */     } 
/*      */     
/* 1398 */     return selectionConfigs;
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
/* 1639 */   public static Vector getCompanyList() { return getCompanies(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 1776 */     if (releaseTypes == null) {
/*      */       
/* 1778 */       preCache = new Vector();
/* 1779 */       String className = "com.universal.milestone.ReleaseType";
/* 1780 */       if (lookupObjects.containsKey(String.valueOf(2)))
/* 1781 */         lookupObjects.remove(String.valueOf(2)); 
/* 1782 */       preCache = getLookupDetailValuesFromDatabase(className, 2);
/* 1783 */       releaseTypes = preCache;
/*      */     } 
/*      */     
/* 1786 */     return releaseTypes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getGenres() {
/* 1796 */     if (genres == null) {
/*      */       
/* 1798 */       vGenres = new Vector();
/* 1799 */       String className = "com.universal.milestone.Genre";
/* 1800 */       if (lookupObjects.containsKey(String.valueOf(17)))
/* 1801 */         lookupObjects.remove(String.valueOf(17)); 
/* 1802 */       vGenres = getLookupDetailValuesFromDatabase(className, 17);
/* 1803 */       genres = vGenres;
/*      */     } 
/*      */     
/* 1806 */     return genres;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getProductCategories() {
/* 1814 */     if (productCategories == null) {
/*      */       
/* 1816 */       className = "com.universal.milestone.ProductCategory";
/* 1817 */       if (lookupObjects.containsKey(String.valueOf(1)))
/* 1818 */         lookupObjects.remove(String.valueOf(1)); 
/* 1819 */       Vector preCache = getLookupDetailValuesFromDatabase(className, 1);
/* 1820 */       productCategories = preCache;
/*      */     } 
/*      */     
/* 1823 */     return productCategories;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getProductCategories(int type) {
/* 1831 */     Vector prodCats = new Vector();
/* 1832 */     Vector allProdCats = getProductCategories();
/*      */     
/* 1834 */     for (int i = 0; i < allProdCats.size(); i++) {
/*      */ 
/*      */       
/* 1837 */       if (((LookupObject)allProdCats.get(i)).getProdType() == type || ((LookupObject)allProdCats.get(i)).getProdType() == 2) {
/* 1838 */         prodCats.add(allProdCats.get(i));
/*      */       }
/*      */     } 
/* 1841 */     return prodCats;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getSelectionStatusList() {
/* 1850 */     if (selectionStatusList == null) {
/*      */       
/* 1852 */       className = "com.universal.milestone.SelectionStatus";
/* 1853 */       if (lookupObjects.containsKey(String.valueOf(15)))
/* 1854 */         lookupObjects.remove(String.valueOf(15)); 
/* 1855 */       Vector preCache = getLookupDetailValuesFromDatabase(className, 15);
/* 1856 */       selectionStatusList = preCache;
/*      */     } 
/*      */     
/* 1859 */     return selectionStatusList;
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
/* 1907 */     if (prefixCodes == null) {
/*      */       
/* 1909 */       query = "SELECT * FROM vi_lookup_subdetail WHERE Field_id = 27 ORDER BY det_value";
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1914 */       JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 1915 */       connector.runQuery();
/* 1916 */       Vector prefixs = new Vector();
/*      */ 
/*      */       
/* 1919 */       while (connector.more()) {
/*      */         
/* 1921 */         String abbrev = connector.getField("det_value");
/* 1922 */         int subValue = connector.getIntegerField("sub_value");
/* 1923 */         boolean inactive = connector.getBoolean("inactive");
/* 1924 */         int prodType = connector.getIntegerField("productType");
/* 1925 */         prefixs.addElement(new PrefixCode(abbrev, subValue, inactive, prodType));
/* 1926 */         connector.next();
/*      */       } 
/*      */       
/* 1929 */       connector.close();
/*      */       
/* 1931 */       prefixCodes = prefixs;
/*      */     } 
/*      */     
/* 1934 */     return prefixCodes;
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
/* 1945 */     if (taskAbbreviations == null) {
/*      */       
/* 1947 */       if (lookupObjects.containsKey(String.valueOf(33)))
/* 1948 */         lookupObjects.remove(String.valueOf(33)); 
/* 1949 */       taskAbbreviations = getLookupDetailValuesFromDatabase(33);
/*      */     } 
/* 1951 */     return taskAbbreviations;
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
/* 1962 */     if (vendors == null) {
/*      */ 
/*      */ 
/*      */       
/* 1966 */       classObj = null;
/*      */ 
/*      */       
/*      */       try {
/* 1970 */         classObj = Class.forName("com.universal.milestone.LookupObject");
/*      */       }
/* 1972 */       catch (ClassNotFoundException cnfexc) {
/*      */         
/* 1974 */         log.log("ClassNotFoundException: " + cnfexc);
/*      */       } 
/*      */       
/* 1977 */       String query = "SELECT value, a.description as 'desc', b.description as 'subValue' ,a.inactive  FROM vi_lookup_detail a  LEFT JOIN vi_lookup_subdetail b on a.[value] = b.[det_value]  and a.[field_id] = b.[field_id]  WHERE a.[Field_id] = 22 ORDER BY a.[description];";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1985 */       JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 1986 */       connector.runQuery();
/*      */       
/* 1988 */       Vector lookupVector = new Vector();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1995 */       while (connector.more()) {
/*      */ 
/*      */         
/*      */         try {
/* 1999 */           LookupObject lookupObject = (LookupObject)classObj.newInstance();
/*      */           
/* 2001 */           lookupObject.setAbbreviation(connector.getField("value", ""));
/* 2002 */           lookupObject.setName(connector.getField("desc", ""));
/* 2003 */           lookupObject.setSubValue(connector.getField("subValue", ""));
/* 2004 */           lookupObject.setInactive(connector.getBoolean("inactive"));
/* 2005 */           lookupVector.addElement(lookupObject);
/* 2006 */           lookupObject = null;
/*      */         }
/* 2008 */         catch (InstantiationException e) {
/*      */           
/* 2010 */           log.log("InstantiationException");
/*      */         }
/* 2012 */         catch (IllegalAccessException iae) {
/*      */           
/* 2014 */           log.log("IllegalAccessException");
/*      */         } 
/*      */         
/* 2017 */         connector.next();
/*      */       } 
/*      */       
/* 2020 */       connector.close();
/*      */       
/* 2022 */       vendors = lookupVector;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2027 */     return vendors;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getDistributionCodes() {
/* 2037 */     if (distributionCodes == null) {
/*      */       
/* 2039 */       if (lookupObjects.containsKey(String.valueOf(39)))
/* 2040 */         lookupObjects.remove(String.valueOf(39)); 
/* 2041 */       distributionCodes = getLookupDetailValuesFromDatabase(39);
/*      */     } 
/* 2043 */     return distributionCodes;
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
/* 2054 */     if (releaseCompanies == null) {
/*      */       
/* 2056 */       if (lookupObjects.containsKey(String.valueOf(40)))
/* 2057 */         lookupObjects.remove(String.valueOf(40)); 
/* 2058 */       releaseCompanies = getLookupDetailValuesFromDatabase(40);
/*      */     } 
/* 2060 */     return releaseCompanies;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getReportConfigTypes() {
/* 2070 */     if (reportConfigTypes == null) {
/*      */       
/* 2072 */       if (lookupObjects.containsKey(String.valueOf(49)))
/* 2073 */         lookupObjects.remove(String.valueOf(49)); 
/* 2074 */       reportConfigTypes = getLookupDetailValuesFromDatabase(49);
/*      */     } 
/* 2076 */     return reportConfigTypes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getReportConfigFormats() {
/* 2086 */     if (reportConfigFormats == null) {
/*      */       
/* 2088 */       if (lookupObjects.containsKey(String.valueOf(48)))
/* 2089 */         lookupObjects.remove(String.valueOf(48)); 
/* 2090 */       reportConfigFormats = getLookupDetailValuesFromDatabase(48);
/*      */     } 
/* 2092 */     return reportConfigFormats;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getReportConfigStatuses() {
/* 2102 */     if (reportConfigStatuses == null) {
/*      */       
/* 2104 */       if (lookupObjects.containsKey(String.valueOf(51)))
/* 2105 */         lookupObjects.remove(String.valueOf(51)); 
/* 2106 */       reportConfigStatuses = getLookupDetailValuesFromDatabase(51);
/*      */     } 
/* 2108 */     return reportConfigStatuses;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getOperatingCompanies() {
/* 2118 */     if (operatingCompanies == null) {
/*      */       
/* 2120 */       if (lookupObjects.containsKey(String.valueOf(31)))
/* 2121 */         lookupObjects.remove(String.valueOf(31)); 
/* 2122 */       operatingCompanies = getLookupDetailValuesFromDatabase(31);
/*      */     } 
/* 2124 */     return operatingCompanies;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getMusicLines() {
/* 2134 */     if (musicLines == null) {
/*      */       
/* 2136 */       if (lookupObjects.containsKey(String.valueOf(7)))
/* 2137 */         lookupObjects.remove(String.valueOf(7)); 
/* 2138 */       musicLines = getLookupDetailValuesFromDatabase(7);
/*      */     } 
/* 2140 */     return musicLines;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getConfigCodes() {
/* 2150 */     if (configCodes == null) {
/*      */       
/* 2152 */       if (lookupObjects.containsKey(String.valueOf(29)))
/* 2153 */         lookupObjects.remove(String.valueOf(29)); 
/* 2154 */       configCodes = getLookupDetailValuesFromDatabase(29);
/*      */     } 
/* 2156 */     return configCodes;
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
/* 2167 */     if (repClasses == null) {
/*      */       
/* 2169 */       if (lookupObjects.containsKey(String.valueOf(9)))
/* 2170 */         lookupObjects.remove(String.valueOf(9)); 
/* 2171 */       repClasses = getLookupDetailValuesFromDatabase(9);
/*      */     } 
/* 2173 */     return repClasses;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getRepertoireOwners() {
/* 2183 */     if (repOwners == null) {
/*      */       
/* 2185 */       if (lookupObjects.containsKey(String.valueOf(8)))
/* 2186 */         lookupObjects.remove(String.valueOf(8)); 
/* 2187 */       repOwners = getLookupDetailValuesFromDatabase(8);
/*      */     } 
/* 2189 */     return repOwners;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getModifiers() {
/* 2199 */     if (modifiers == null) {
/*      */       
/* 2201 */       if (lookupObjects.containsKey(String.valueOf(30)))
/* 2202 */         lookupObjects.remove(String.valueOf(30)); 
/* 2203 */       modifiers = getLookupDetailValuesFromDatabase(30);
/*      */     } 
/* 2205 */     return modifiers;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getReturnCodes() {
/* 2215 */     if (returnCodes == null) {
/*      */       
/* 2217 */       if (lookupObjects.containsKey(String.valueOf(10)))
/* 2218 */         lookupObjects.remove(String.valueOf(10)); 
/* 2219 */       returnCodes = getLookupDetailValuesFromDatabase(10);
/*      */     } 
/* 2221 */     return returnCodes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getExportFlags() {
/* 2231 */     if (exportFlags == null) {
/*      */       
/* 2233 */       if (lookupObjects.containsKey(String.valueOf(11)))
/* 2234 */         lookupObjects.remove(String.valueOf(11)); 
/* 2235 */       exportFlags = getLookupDetailValuesFromDatabase(11);
/*      */     } 
/* 2237 */     return exportFlags;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getCompanyCodes() {
/* 2247 */     if (companyCodes == null) {
/*      */       
/* 2249 */       if (lookupObjects.containsKey(String.valueOf(36)))
/* 2250 */         lookupObjects.remove(String.valueOf(36)); 
/* 2251 */       companyCodes = getLookupDetailValuesFromDatabase(36);
/*      */     } 
/* 2253 */     return companyCodes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getPoMergeCodes() {
/* 2263 */     if (poMergeCodes == null) {
/*      */       
/* 2265 */       if (lookupObjects.containsKey(String.valueOf(6)))
/* 2266 */         lookupObjects.remove(String.valueOf(6)); 
/* 2267 */       poMergeCodes = getLookupDetailValuesFromDatabase(6);
/*      */     } 
/* 2269 */     return poMergeCodes;
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
/* 2280 */     if (guaranteeCodes == null) {
/*      */       
/* 2282 */       if (lookupObjects.containsKey(String.valueOf(37)))
/* 2283 */         lookupObjects.remove(String.valueOf(37)); 
/* 2284 */       guaranteeCodes = getLookupDetailValuesFromDatabase(37);
/*      */     } 
/* 2286 */     return guaranteeCodes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getLoosePickExempt() {
/* 2296 */     if (loosePickExemptCodes == null) {
/*      */       
/* 2298 */       if (lookupObjects.containsKey(String.valueOf(60)))
/* 2299 */         lookupObjects.remove(String.valueOf(60)); 
/* 2300 */       loosePickExemptCodes = getLookupDetailValuesFromDatabase(60);
/*      */     } 
/* 2302 */     return loosePickExemptCodes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getCompilationCodes() {
/* 2312 */     if (compilationCodes == null) {
/*      */       
/* 2314 */       if (lookupObjects.containsKey(String.valueOf(57)))
/* 2315 */         lookupObjects.remove(String.valueOf(57)); 
/* 2316 */       compilationCodes = getLookupDetailValuesFromDatabase(57);
/*      */     } 
/* 2318 */     return compilationCodes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getImpRateCodes() {
/* 2328 */     if (impRateCodes == null) {
/*      */       
/* 2330 */       if (lookupObjects.containsKey(String.valueOf(12)))
/* 2331 */         lookupObjects.remove(String.valueOf(12)); 
/* 2332 */       impRateCodes = getLookupDetailValuesFromDatabase(12);
/*      */     } 
/* 2334 */     return impRateCodes;
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
/* 2359 */     if (musicTypes == null) {
/*      */       
/* 2361 */       vMusicTypes = new Vector();
/* 2362 */       String className = "com.universal.milestone.Genre";
/* 2363 */       if (lookupObjects.containsKey(String.valueOf(13)))
/* 2364 */         lookupObjects.remove(String.valueOf(13)); 
/* 2365 */       vMusicTypes = getLookupDetailValuesFromDatabase(className, 13);
/* 2366 */       musicTypes = vMusicTypes;
/*      */     } 
/* 2368 */     return musicTypes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getSuppliers() {
/* 2378 */     if (suppliers == null) {
/*      */       
/* 2380 */       if (lookupObjects.containsKey(String.valueOf(19)))
/* 2381 */         lookupObjects.remove(String.valueOf(19)); 
/* 2382 */       suppliers = getLookupDetailValuesFromDatabase(19);
/*      */     } 
/* 2384 */     return suppliers;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getNarmExtracts() {
/* 2394 */     if (narm == null) {
/*      */       
/* 2396 */       if (lookupObjects.containsKey(String.valueOf(56)))
/* 2397 */         lookupObjects.remove(String.valueOf(56)); 
/* 2398 */       narm = getLookupDetailValuesFromDatabase(56);
/*      */     } 
/* 2400 */     return narm;
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
/* 2411 */     if (encryptionFlags == null) {
/*      */       
/* 2413 */       if (lookupObjects.containsKey(String.valueOf(61)))
/* 2414 */         lookupObjects.remove(String.valueOf(61)); 
/* 2415 */       encryptionFlags = getLookupDetailValuesFromDatabase(61);
/*      */     } 
/* 2417 */     return encryptionFlags;
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
/* 2428 */     if (pricePoints == null) {
/*      */       
/* 2430 */       if (lookupObjects.containsKey(String.valueOf(14)))
/* 2431 */         lookupObjects.remove(String.valueOf(14)); 
/* 2432 */       pricePoints = getLookupDetailValuesFromDatabase(14);
/*      */     } 
/* 2434 */     return pricePoints;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getImportIndicators() {
/* 2444 */     if (importIndicators == null) {
/*      */       
/* 2446 */       if (lookupObjects.containsKey(String.valueOf(54)))
/* 2447 */         lookupObjects.remove(String.valueOf(54)); 
/* 2448 */       importIndicators = getLookupDetailValuesFromDatabase(54);
/*      */     } 
/* 2450 */     return importIndicators;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getFormats() {
/* 2460 */     if (formats == null) {
/*      */       
/* 2462 */       if (lookupObjects.containsKey(String.valueOf(58)))
/* 2463 */         lookupObjects.remove(String.valueOf(58)); 
/* 2464 */       formats = getLookupDetailValuesFromDatabase(58);
/*      */     } 
/* 2466 */     return formats;
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
/* 2477 */     if (umlUsers == null) {
/*      */ 
/*      */ 
/*      */       
/* 2481 */       families = getFamilies();
/* 2482 */       int umlId = -1;
/*      */       
/* 2484 */       for (int i = 0; i < families.size(); i++) {
/* 2485 */         CorporateStructureObject family = (CorporateStructureObject)families.get(i);
/*      */         
/* 2487 */         String familyName = family.getName().trim();
/*      */         
/* 2489 */         if (familyName.equalsIgnoreCase("UML")) {
/* 2490 */           umlId = family.getStructureID();
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/* 2495 */       Vector precache = new Vector();
/* 2496 */       Vector users = getAllUsers();
/* 2497 */       for (int i = 0; i < users.size(); i++) {
/*      */         
/* 2499 */         User user = (User)users.get(i);
/*      */         
/* 2501 */         if (user.getEmployedBy() == umlId) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2508 */           boolean uniqueUMLContact = true;
/* 2509 */           for (int j = 0; j < precache.size(); j++) {
/*      */             
/* 2511 */             User umlusr = (User)precache.get(j);
/* 2512 */             String userName = user.getName().trim();
/* 2513 */             String umlusrName = umlusr.getName().trim();
/* 2514 */             if (userName.equalsIgnoreCase(umlusrName))
/*      */             {
/*      */               
/* 2517 */               uniqueUMLContact = false;
/*      */             }
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 2523 */           if (uniqueUMLContact)
/* 2524 */             precache.add(user); 
/*      */         } 
/*      */       } 
/* 2527 */       umlUsers = precache;
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
/* 2572 */     return umlUsers;
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
/* 2583 */     if (allUsers == null) {
/* 2584 */       getAllUsers();
/*      */     }
/* 2586 */     return allUsersHash;
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
/* 2597 */     synchronized (lockObject) {
/*      */ 
/*      */       
/* 2600 */       if (allUsers == null) {
/*      */         
/* 2602 */         allUsersHash = null;
/* 2603 */         allUsersHash = new Hashtable();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2612 */         String query = "sp_get_User_All";
/* 2613 */         JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 2614 */         connector.runQuery();
/*      */         
/* 2616 */         Vector precache = new Vector();
/*      */ 
/*      */         
/* 2619 */         while (connector.more()) {
/*      */           
/* 2621 */           User addUser = UserManager.getInstance().getUserObject(connector);
/* 2622 */           precache.add(addUser);
/* 2623 */           allUsersHash.put(String.valueOf(addUser.getUserId()), addUser);
/* 2624 */           connector.next();
/*      */         } 
/*      */         
/* 2627 */         connector.close();
/* 2628 */         allUsers = precache;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2633 */         umlUsers = null;
/* 2634 */         labelUsers = null;
/* 2635 */         buildUserCacheObjects();
/*      */       } 
/*      */     } 
/* 2638 */     return allUsers;
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
/* 2649 */     synchronized (lockObject) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2659 */       String query = "sp_get_User_All " + userId;
/* 2660 */       JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 2661 */       connector.runQuery();
/*      */ 
/*      */       
/* 2664 */       if (!connector.more()) {
/*      */         
/* 2666 */         for (int i = 0; i < allUsers.size(); i++) {
/*      */           
/* 2668 */           User user = (User)allUsers.get(i);
/* 2669 */           if (user.getUserId() == userId) {
/*      */             
/* 2671 */             allUsers.remove(i);
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/* 2676 */         allUsersHash.remove(String.valueOf(userId));
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 2681 */         User refreshUser = UserManager.getInstance().getUserObject(connector);
/* 2682 */         boolean refreshed = false;
/*      */         
/* 2684 */         for (int i = 0; i < allUsers.size(); i++) {
/* 2685 */           User user = (User)allUsers.get(i);
/* 2686 */           if (user.getUserId() == refreshUser.getUserId()) {
/* 2687 */             allUsers.set(i, refreshUser);
/* 2688 */             refreshed = true;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/* 2693 */         if (!refreshed) {
/* 2694 */           allUsers.add(refreshUser);
/*      */         }
/*      */         
/* 2697 */         allUsersHash.put(String.valueOf(refreshUser.getUserId()), refreshUser);
/*      */       } 
/*      */       
/* 2700 */       connector.close();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2706 */     umlUsers = null;
/* 2707 */     labelUsers = null;
/* 2708 */     buildUserCacheObjects();
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
/* 2719 */     if (datePeriods == null) {
/*      */       
/* 2721 */       query = "SELECT * FROM vi_Date_Period ORDER BY start_date;";
/*      */ 
/*      */ 
/*      */       
/* 2725 */       JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 2726 */       connector.runQuery();
/*      */       
/* 2728 */       Vector precache = new Vector();
/*      */ 
/*      */       
/* 2731 */       while (connector.more()) {
/*      */         
/* 2733 */         int id = connector.getIntegerField("per_id");
/* 2734 */         String name = connector.getField("name");
/* 2735 */         String cycle = connector.getField("cycle");
/* 2736 */         Calendar startDate = MilestoneHelper.getDatabaseDate(connector.getField("start_date", ""));
/* 2737 */         Calendar endDate = MilestoneHelper.getDatabaseDate(connector.getField("end_date", ""));
/* 2738 */         Calendar solDate = MilestoneHelper.getDatabaseDate(connector.getField("sol_date", ""));
/*      */         
/* 2740 */         precache.addElement(new DatePeriod(id, name, cycle, startDate, endDate, solDate));
/* 2741 */         connector.next();
/*      */       } 
/*      */       
/* 2744 */       connector.close();
/*      */       
/* 2746 */       datePeriods = precache;
/*      */     } 
/*      */     
/* 2749 */     return datePeriods;
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
/* 2760 */   public static Vector getLookupDetailValuesFromDatabase(int fieldId) { return getLookupDetailValuesFromDatabase("com.universal.milestone.LookupObject", fieldId); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*      */     //   #2775	-> 0
/*      */     //   #2778	-> 2
/*      */     //   #2780	-> 21
/*      */     //   #2783	-> 23
/*      */     //   #2784	-> 28
/*      */     //   #2785	-> 31
/*      */     //   #2786	-> 33
/*      */     //   #2789	-> 57
/*      */     //   #2791	-> 67
/*      */     //   #2789	-> 71
/*      */     //   #2795	-> 76
/*      */     //   #2797	-> 93
/*      */     //   #2798	-> 110
/*      */     //   #2799	-> 119
/*      */     //   #2801	-> 142
/*      */     //   #2803	-> 145
/*      */     //   #2805	-> 168
/*      */     //   #2806	-> 175
/*      */     //   #2808	-> 180
/*      */     //   #2809	-> 188
/*      */     //   #2814	-> 197
/*      */     //   #2816	-> 200
/*      */     //   #2817	-> 209
/*      */     //   #2818	-> 225
/*      */     //   #2819	-> 241
/*      */     //   #2820	-> 254
/*      */     //   #2822	-> 267
/*      */     //   #2825	-> 280
/*      */     //   #2826	-> 286
/*      */     //   #2828	-> 302
/*      */     //   #2829	-> 309
/*      */     //   #2830	-> 312
/*      */     //   #2831	-> 314
/*      */     //   #2833	-> 326
/*      */     //   #2834	-> 328
/*      */     //   #2837	-> 337
/*      */     //   #2814	-> 342
/*      */     //   #2841	-> 350
/*      */     //   #2842	-> 355
/*      */     //   #2843	-> 364
/*      */     //   #2844	-> 370
/*      */     //   #2845	-> 378
/*      */     //   #2846	-> 384
/*      */     //   #2845	-> 395
/*      */     //   #2847	-> 398
/*      */     //   #2845	-> 402
/*      */     //   #2844	-> 403
/*      */     //   #2843	-> 406
/*      */     //   #2849	-> 419
/*      */     //   #2851	-> 422
/*      */     //   #2853	-> 431
/*      */     //   #2856	-> 436
/*      */     //   #2857	-> 442
/*      */     //   #2859	-> 452
/*      */     //   #2862	-> 464
/*      */     //   #2863	-> 478
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
/* 2876 */     String query = "SELECT * FROM vi_lookup_SubDetail WHERE Field_id = 18 and det_value = '" + 
/*      */ 
/*      */       
/* 2879 */       departmentValue + "';";
/*      */     
/* 2881 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 2882 */     connector.runQuery();
/*      */     
/* 2884 */     String departmentSubdescription = "";
/*      */     
/* 2886 */     while (connector.more()) {
/*      */       
/* 2888 */       departmentSubdescription = connector.getField("description", "");
/*      */       
/* 2890 */       connector.next();
/*      */     } 
/*      */     
/* 2893 */     connector.close();
/*      */     
/* 2895 */     return departmentSubdescription;
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
/* 2908 */     if (javaScriptPriceCodeArray == null) {
/*      */       
/* 2910 */       result = new String();
/* 2911 */       Vector vSellCodeList = getSellCodes();
/*      */ 
/*      */       
/* 2914 */       result = String.valueOf(result) + "var p = new Array();\n";
/* 2915 */       String pc = new String();
/*      */       
/* 2917 */       result = String.valueOf(result) + "p[0] = new Array( 0, ' ');\n";
/* 2918 */       int arrayIndex = 0;
/*      */       
/* 2920 */       for (int i = 0; i < vSellCodeList.size(); i++) {
/*      */ 
/*      */         
/* 2923 */         int k = i + 1;
/* 2924 */         pc = (String)vSellCodeList.get(i);
/*      */         
/* 2926 */         String s1 = "p[" + k + "] = new Array(";
/* 2927 */         String s2 = new String();
/* 2928 */         s2 = String.valueOf(s2) + " " + k + ", '" + pc + "',";
/*      */         
/* 2930 */         if (s2.length() > 0) {
/*      */           
/* 2932 */           s2 = s2.substring(0, s2.length() - 1);
/* 2933 */           result = String.valueOf(result) + s1 + s2 + ");\n";
/*      */         }
/*      */         else {
/*      */           
/* 2937 */           result = String.valueOf(result) + s1 + " 0, 'None');\n";
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 2942 */       javaScriptPriceCodeArray = result;
/*      */     } 
/*      */     
/* 2945 */     return javaScriptPriceCodeArray;
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
/* 2956 */     if (javaScriptPriceCodeDPCArray == null) {
/*      */       
/* 2958 */       result = new String();
/* 2959 */       Vector vSellCodeList = getSellCodesDPC();
/*      */ 
/*      */       
/* 2962 */       result = String.valueOf(result) + "var dpc = new Array();\n";
/* 2963 */       String pc = new String();
/*      */       
/* 2965 */       result = String.valueOf(result) + "dpc[0] = new Array( 0, ' ');\n";
/* 2966 */       int arrayIndex = 0;
/*      */       
/* 2968 */       for (int i = 0; i < vSellCodeList.size(); i++) {
/*      */ 
/*      */         
/* 2971 */         int k = i + 1;
/* 2972 */         pc = (String)vSellCodeList.get(i);
/*      */         
/* 2974 */         String s1 = "dpc[" + k + "] = new Array(";
/* 2975 */         String s2 = new String();
/* 2976 */         s2 = String.valueOf(s2) + " " + k + ", '" + pc + "',";
/*      */         
/* 2978 */         if (s2.length() > 0) {
/*      */           
/* 2980 */           s2 = s2.substring(0, s2.length() - 1);
/* 2981 */           result = String.valueOf(result) + s1 + s2 + ");\n";
/*      */         }
/*      */         else {
/*      */           
/* 2985 */           result = String.valueOf(result) + s1 + " 0, 'None');\n";
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 2990 */       javaScriptPriceCodeDPCArray = result;
/*      */     } 
/*      */     
/* 2993 */     return javaScriptPriceCodeDPCArray;
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
/* 3005 */     results = new String();
/* 3006 */     Vector configCodes = getConfigCodes();
/*      */     
/* 3008 */     int configSize = configCodes.size();
/*      */     
/* 3010 */     results = "PFMconfigs = new Array();\n";
/*      */     
/* 3012 */     for (int x = 0; x < configCodes.size(); x++) {
/*      */ 
/*      */       
/* 3015 */       LookupObject lo = (LookupObject)configCodes.get(x);
/*      */ 
/*      */       
/* 3018 */       results = String.valueOf(results) + "PFMconfigs['" + lo.getAbbreviation() + "'] = " + lo.isDigitalEquivalent + ";\n";
/*      */     } 
/*      */     
/* 3021 */     return results;
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
/* 3032 */     if (javaScriptConfigArray == null) {
/*      */       
/* 3034 */       String result = new String();
/* 3035 */       String arraySetup = "";
/* 3036 */       String arrayContents = "";
/* 3037 */       Vector configs = getSelectionConfigs();
/* 3038 */       selectedOption = "";
/*      */       
/* 3040 */       int subConfigSize = 0;
/* 3041 */       int configSize = 0;
/*      */       
/* 3043 */       int sizeActiveConfigs = getActiveConfigSize(configs);
/*      */ 
/*      */       
/* 3046 */       arraySetup = "configs = new Array(" + (sizeActiveConfigs + 1) + ");\n";
/* 3047 */       arraySetup = String.valueOf(arraySetup) + "configs[0] = new Array(1);\n";
/*      */       
/* 3049 */       arraySetup = String.valueOf(arraySetup) + "configsProdType = new Array(" + (sizeActiveConfigs + 1) + ");\n";
/*      */       
/* 3051 */       for (int i = 0; i < configs.size(); i++) {
/*      */         
/* 3053 */         SelectionConfiguration selectionConfig = (SelectionConfiguration)configs.elementAt(i);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3058 */         if (selectionConfig.getInactive())
/*      */         {
/*      */           
/* 3061 */           if (!selectionConfig.getSelectionConfigurationAbbreviation().equalsIgnoreCase(selectedOption)) {
/*      */             continue;
/*      */           }
/*      */         }
/* 3065 */         configSize++;
/* 3066 */         subConfigSize = 0;
/*      */         
/* 3068 */         Vector subConfigs = selectionConfig.getSubConfigurations();
/*      */         
/* 3070 */         int sizeActiveSubConfigs = getActiveSubConfigSize(subConfigs);
/*      */ 
/*      */         
/* 3073 */         arraySetup = String.valueOf(arraySetup) + "configs[" + configSize + "] = new Array(" + sizeActiveSubConfigs + ");\n";
/*      */ 
/*      */         
/* 3076 */         arrayContents = String.valueOf(arrayContents) + "configsProdType[" + configSize + "] = '" + selectionConfig.getSelectionConfigurationName() + "," + selectionConfig.getSelectionConfigurationAbbreviation() + "," + selectionConfig.getProdType() + "," + selectionConfig.getNewBundle() + "';\n";
/*      */         
/* 3078 */         for (int j = 0; j < subConfigs.size(); j++) {
/*      */           
/* 3080 */           SelectionSubConfiguration selectionSubConfig = (SelectionSubConfiguration)subConfigs.elementAt(j);
/*      */ 
/*      */ 
/*      */           
/* 3084 */           if (selectionSubConfig.getInactive())
/*      */           {
/*      */             
/* 3087 */             if (!selectionSubConfig.getSelectionSubConfigurationAbbreviation().equalsIgnoreCase(selectedOption)) {
/*      */               continue;
/*      */             }
/*      */           }
/* 3091 */           subConfigSize++;
/*      */           
/* 3093 */           arrayContents = String.valueOf(arrayContents) + "configs[" + configSize + "][" + subConfigSize + "] = '" + selectionSubConfig.getSelectionSubConfigurationAbbreviation() + "," + selectionSubConfig.getSelectionSubConfigurationName() + "," + selectionSubConfig.isParent() + "," + selectionSubConfig.getProdType() + "';\n"; continue;
/*      */         } 
/*      */         continue;
/*      */       } 
/* 3097 */       result = String.valueOf(result) + arraySetup;
/* 3098 */       result = String.valueOf(result) + "configs[0][0]=',';\n";
/* 3099 */       result = String.valueOf(result) + arrayContents;
/*      */       
/* 3101 */       javaScriptConfigArray = result;
/*      */     } 
/*      */     
/* 3104 */     return javaScriptConfigArray;
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
/* 3116 */     if (javaScriptSubConfigArray == null) {
/*      */       
/* 3118 */       String result = new String();
/* 3119 */       String arraySetup = "";
/* 3120 */       String arrayContents = "";
/* 3121 */       Vector configs = getSelectionConfigs();
/*      */       
/* 3123 */       int subConfigSize = 0;
/*      */       
/* 3125 */       int sizeActiveConfigs = getActiveConfigSize(configs);
/*      */       
/* 3127 */       for (int i = 0; i < configs.size(); i++) {
/*      */         
/* 3129 */         SelectionConfiguration selectionConfig = (SelectionConfiguration)configs.elementAt(i);
/*      */ 
/*      */ 
/*      */         
/* 3133 */         if (selectionConfig.getInactive())
/*      */         {
/*      */           
/* 3136 */           if (!selectionConfig.getSelectionConfigurationAbbreviation().equalsIgnoreCase(selectedOption)) {
/*      */             continue;
/*      */           }
/*      */         }
/* 3140 */         Vector subConfigs = selectionConfig.getSubConfigurations();
/*      */         
/* 3142 */         int sizeActiveSubConfigs = getActiveSubConfigSize(subConfigs);
/*      */         
/* 3144 */         for (int j = 0; j < subConfigs.size(); j++) {
/*      */           
/* 3146 */           SelectionSubConfiguration selectionSubConfig = (SelectionSubConfiguration)subConfigs.elementAt(j);
/*      */ 
/*      */ 
/*      */           
/* 3150 */           if (selectionSubConfig.getInactive())
/*      */           {
/*      */             
/* 3153 */             if (!selectionSubConfig.getSelectionSubConfigurationAbbreviation().equalsIgnoreCase(selectedOption)) {
/*      */               continue;
/*      */             }
/*      */           }
/* 3157 */           subConfigSize++;
/* 3158 */           arrayContents = String.valueOf(arrayContents) + "subConfigs[" + subConfigSize + "] = '" + selectionSubConfig.getSelectionSubConfigurationAbbreviation() + "," + selectionSubConfig.isParent() + "," + selectionSubConfig.getProdType() + "';\n"; continue;
/*      */         }  continue;
/*      */       } 
/* 3161 */       arraySetup = "subConfigs = new Array(" + subConfigSize + ");\n";
/*      */       
/* 3163 */       result = String.valueOf(result) + arraySetup;
/* 3164 */       result = String.valueOf(result) + arrayContents;
/*      */       
/* 3166 */       javaScriptSubConfigArray = result;
/*      */     } 
/*      */     
/* 3169 */     return javaScriptSubConfigArray;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getBomSuppliers() {
/* 3179 */     if (bomSuppliers == null) {
/*      */       
/* 3181 */       query = "SELECT * FROM vi_Supplier where (inactive is null or inactive = 0)  ORDER BY description;";
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3186 */       JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 3187 */       connector.runQuery();
/*      */       
/* 3189 */       Vector precache = new Vector();
/*      */ 
/*      */       
/* 3192 */       while (connector.more()) {
/*      */         
/* 3194 */         LookupObject lookupObject = new LookupObject();
/* 3195 */         lookupObject.setAbbreviation(connector.getField("supplier_id", ""));
/* 3196 */         lookupObject.setName(connector.getField("description", ""));
/* 3197 */         precache.addElement(lookupObject);
/* 3198 */         connector.next();
/*      */       } 
/*      */       
/* 3201 */       bomSuppliers = precache;
/*      */       
/* 3203 */       connector.close();
/*      */     } 
/*      */     
/* 3206 */     return bomSuppliers;
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
/* 3221 */     if (dayTypes == null) {
/*      */       
/* 3223 */       query = "SELECT * FROM vi_day_type ORDER BY grouping asc, date desc;";
/*      */ 
/*      */ 
/*      */       
/* 3227 */       JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 3228 */       connector.runQuery();
/*      */       
/* 3230 */       Vector precache = new Vector();
/*      */       
/* 3232 */       Day day = null;
/*      */       
/* 3234 */       int curGroup = -1;
/* 3235 */       int prevGroup = -1;
/* 3236 */       Vector curGroupDates = null;
/*      */       
/* 3238 */       while (connector.more()) {
/*      */         
/* 3240 */         curGroup = connector.getIntegerField("grouping");
/*      */         
/* 3242 */         if (curGroup != prevGroup) {
/* 3243 */           if (curGroupDates != null) {
/* 3244 */             precache.add(curGroupDates);
/*      */           }
/* 3246 */           curGroupDates = new Vector();
/*      */         } 
/*      */         
/* 3249 */         day = new Day(connector.getIntegerField("type_id"));
/*      */ 
/*      */         
/* 3252 */         day.setDescription(connector.getField("description", ""));
/*      */ 
/*      */         
/* 3255 */         day.setCalendarGroup(curGroup);
/*      */ 
/*      */         
/* 3258 */         day.setSpecificDate(MilestoneHelper.getDatabaseDate(connector.getField("date")));
/*      */ 
/*      */         
/* 3261 */         day.setDayType(connector.getField("value", ""));
/*      */ 
/*      */         
/* 3264 */         String lastDateString = connector.getFieldByName("last_updated_on");
/* 3265 */         if (lastDateString != null) {
/* 3266 */           day.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastDateString));
/*      */         }
/*      */         
/* 3269 */         day.setLastUpdatingUser(connector.getIntegerField("last_updated_by"));
/*      */ 
/*      */         
/* 3272 */         long lastUpdatedLong = Long.parseLong(connector.getField("last_updated_ck"), 16);
/* 3273 */         day.setLastUpdatedCk(lastUpdatedLong);
/*      */         
/* 3275 */         curGroupDates.addElement(day);
/* 3276 */         day = null;
/* 3277 */         prevGroup = curGroup;
/* 3278 */         connector.next();
/*      */       } 
/*      */       
/* 3281 */       if (curGroupDates != null) {
/* 3282 */         precache.add(curGroupDates);
/*      */       }
/*      */       
/* 3285 */       dayTypes = precache;
/* 3286 */       connector.close();
/*      */     } 
/*      */     
/* 3289 */     return dayTypes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Hashtable getUserCompaniesTable() {
/* 3297 */     if (userCompaniesTable == null) {
/* 3298 */       userCompaniesTable = new Hashtable();
/*      */     }
/* 3300 */     return userCompaniesTable;
/*      */   }
/*      */   
/*      */   private static void RefreshServers(String pstrParam) {
/*      */     try {
/* 3305 */       if (RefreshServers != null)
/*      */       {
/*      */ 
/*      */ 
/*      */         
/* 3310 */         Iterator it = RefreshServers.iterator();
/* 3311 */         while (it.hasNext()) {
/* 3312 */           String strUrl = String.valueOf(it.next().toString()) + pstrParam;
/* 3313 */           System.out.println("URL: " + strUrl);
/* 3314 */           URL url = new URL(strUrl);
/* 3315 */           URLConnection connMilestone = url.openConnection();
/* 3316 */           connMilestone.getInputStream();
/*      */         }
/*      */       
/*      */       }
/*      */     
/* 3321 */     } catch (Exception e) {
/* 3322 */       System.out.println("FAILED ATTEMPT");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getActiveConfigSize(Vector configs) {
/* 3332 */     int sizeActives = 0;
/* 3333 */     for (int i = 0; i < configs.size(); i++) {
/* 3334 */       SelectionConfiguration selectionConfig = 
/* 3335 */         (SelectionConfiguration)configs.elementAt(i);
/* 3336 */       if (!selectionConfig.getInactive()) {
/* 3337 */         sizeActives++;
/*      */       }
/*      */     } 
/* 3340 */     return sizeActives;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getActiveSubConfigSize(Vector subConfigs) {
/* 3348 */     int sizeActives = 0;
/* 3349 */     for (int i = 0; i < subConfigs.size(); i++) {
/*      */       
/* 3351 */       SelectionSubConfiguration selectionSubConfig = (SelectionSubConfiguration)subConfigs.elementAt(i);
/* 3352 */       if (!selectionSubConfig.getInactive()) {
/* 3353 */         sizeActives++;
/*      */       }
/*      */     } 
/* 3356 */     return sizeActives;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getEnvironments() {
/* 3365 */     synchronized (lockObject) {
/*      */       
/* 3367 */       if (cachedEnvironments == null) {
/*      */         
/* 3369 */         log.debug("creating new Environment cache.");
/* 3370 */         cachedEnvironments = new Vector();
/* 3371 */         Vector csos = getCorporateStructure();
/*      */ 
/*      */ 
/*      */         
/* 3375 */         for (int i = 0; i < csos.size(); i++) {
/*      */           
/* 3377 */           CorporateStructureObject cso = (CorporateStructureObject)csos.elementAt(i);
/* 3378 */           if (cso instanceof Environment) {
/* 3379 */             cachedEnvironments.addElement(cso);
/*      */           }
/*      */         } 
/*      */         
/* 3383 */         sortEnvironmentsByFamily(cachedEnvironments);
/*      */       } 
/*      */     } 
/* 3386 */     return cachedEnvironments;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3394 */   public static Vector getEnvironmentsByFamily() { return cachedEnvironmentsByFamily; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void sortEnvironmentsByFamily(Vector environments) {
/* 3403 */     cachedEnvironmentsByFamily = new Vector();
/*      */ 
/*      */     
/* 3406 */     Object[] environmentArray = environments.toArray();
/*      */ 
/*      */     
/* 3409 */     Arrays.sort(environmentArray, new CorpStructParentNameComparator());
/*      */     
/* 3411 */     for (int j = 0; j < environmentArray.length; j++) {
/* 3412 */       cachedEnvironmentsByFamily.add(environmentArray[j]);
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
/* 3424 */     if (userEnvironmentsTable == null) {
/* 3425 */       userEnvironmentsTable = new Hashtable();
/*      */     }
/* 3427 */     return userEnvironmentsTable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3436 */   public static Vector getEnvironmentList() { return getEnvironments(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getLabelUsers() {
/* 3446 */     if (labelUsers == null) {
/*      */       
/* 3448 */       precache = new Vector();
/*      */       
/* 3450 */       int umlId = MilestoneHelper.getStructureId("UML", 1);
/* 3451 */       int enterpriseId = MilestoneHelper.getStructureId("Enterprise", 1);
/*      */ 
/*      */       
/* 3454 */       String query = "SELECT DISTINCT vi_All_User.Name, vi_All_User.Full_Name, vi_All_User.User_Id FROM vi_All_User, vi_User_Company WHERE (vi_All_User.User_ID = vi_User_Company.User_ID) AND (menu_access LIKE '[1,2]%') AND (vi_All_User.employed_by <> " + 
/*      */ 
/*      */ 
/*      */         
/* 3458 */         umlId + ")" + 
/* 3459 */         " AND (vi_All_User.employed_by IS NOT NULL)" + 
/* 3460 */         " AND (vi_All_User.employed_by <> " + enterpriseId + ")" + 
/* 3461 */         " AND (vi_All_User.employed_by <> 0)" + 
/* 3462 */         " AND (vi_All_user.employed_by <> -1)" + 
/* 3463 */         " AND exists (select 'x' from dbo.release_header rh where rh.contact_id = vi_All_User.[user_id])" + 
/* 3464 */         " ORDER BY vi_All_User.Full_Name;";
/*      */ 
/*      */ 
/*      */       
/* 3468 */       JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 3469 */       connector.runQuery();
/*      */       
/* 3471 */       while (connector.more()) {
/*      */         
/* 3473 */         User labelUser = UserManager.getInstance().getUser(connector.getIntegerField("user_id"));
/* 3474 */         precache.add(labelUser);
/* 3475 */         connector.next();
/*      */       } 
/*      */       
/* 3478 */       connector.close();
/* 3479 */       labelUsers = precache;
/*      */     } 
/*      */     
/* 3482 */     return labelUsers;
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
/* 3497 */     getUmlUsers();
/* 3498 */     getLabelUsers();
/*      */   }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\Cache.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */