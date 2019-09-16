package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.techempower.gemini.FormDropDownMenu;
import com.universal.milestone.Acl;
import com.universal.milestone.Cache;
import com.universal.milestone.CompanyAcl;
import com.universal.milestone.CorporateStructureObject;
import com.universal.milestone.JdbcConnector;
import com.universal.milestone.Label;
import com.universal.milestone.LookupObject;
import com.universal.milestone.MilestoneConstants;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneHelper_2;
import com.universal.milestone.Selection;
import com.universal.milestone.SelectionManager;
import com.universal.milestone.User;
import com.universal.milestone.projectSearchSvcClient;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;

public class MilestoneHelper_2 implements MilestoneConstants {
  public static final String COMPONENT_CODE = "help_2";
  
  public static String MILESTONE_CONFIG_FILE = "milestone.conf";
  
  protected static ComponentLog log;
  
  protected static int[] UPC_SSG_Dashes = { 1, 6, 13 };
  
  protected static int[] ISRC_Dashes = { 2, 5, 7 };
  
  protected static int UPC_maxLen = 14;
  
  protected static int SSG_maxLen = 14;
  
  protected static int ISRC_maxLen = 12;
  
  public static String getRMSReportFormat(String inputString, String formatType, boolean isDigital) {
    if (inputString == null || inputString.length() == 0)
      return inputString; 
    StringBuffer rmsFormat = new StringBuffer();
    Hashtable dashsHash = new Hashtable();
    if (isDigital && formatType.equalsIgnoreCase("SSG"))
      if (isISRC(inputString, isDigital)) {
        formatType = "ISRC";
      } else {
        formatType = "UPC";
      }  
    if (formatType.equalsIgnoreCase("UPC") || formatType.equalsIgnoreCase("SSG"))
      for (int i = 0; i < UPC_SSG_Dashes.length; i++)
        dashsHash.put(new Integer(UPC_SSG_Dashes[i]), "-");  
    if (formatType.equalsIgnoreCase("ISRC"))
      for (int i = 0; i < ISRC_Dashes.length; i++)
        dashsHash.put(new Integer(ISRC_Dashes[i]), "-");  
    for (int i = 0; i < inputString.length(); i++) {
      Integer d = new Integer(i);
      if (dashsHash.containsKey(d))
        rmsFormat.append((String)dashsHash.get(d)); 
      rmsFormat.append(inputString.charAt(i));
    } 
    return rmsFormat.toString();
  }
  
  public static String reformat_UPC_SSG_SGC_forSave(String inputString, String formatType, boolean isDigital, boolean zeroFill) {
    if (inputString == null || inputString.length() == 0)
      return inputString; 
    StringBuffer newStringBuf = new StringBuffer();
    boolean isISRC = false;
    if (isDigital && formatType.equalsIgnoreCase("SSG"))
      if (isISRC(inputString, isDigital)) {
        isISRC = true;
        formatType = "ISRC";
      } else {
        isISRC = false;
        formatType = "UPC";
      }  
    for (int i = 0; i < inputString.length(); i++) {
      char c = inputString.charAt(i);
      if (isISRC) {
        if (Character.isLetterOrDigit(c))
          newStringBuf.append(inputString.charAt(i)); 
      } else if (Character.isDigit(c)) {
        newStringBuf.append(inputString.charAt(i));
      } 
    } 
    int maxLen = -1;
    if (formatType.equalsIgnoreCase("UPC"))
      maxLen = UPC_maxLen; 
    if (formatType.equalsIgnoreCase("SSG"))
      maxLen = SSG_maxLen; 
    if (formatType.equalsIgnoreCase("ISRC"))
      maxLen = ISRC_maxLen; 
    if (zeroFill && !isISRC) {
      int zeros = maxLen - newStringBuf.toString().length();
      for (int i = 0; i < zeros; i++)
        newStringBuf.insert(0, '0'); 
    } 
    if (newStringBuf.length() > maxLen && maxLen > 0)
      newStringBuf.setLength(maxLen); 
    return newStringBuf.toString();
  }
  
  public static boolean isISRC(String inputString, boolean isDigital) {
    boolean isISRC = false;
    if (inputString == null || inputString.length() < 2)
      return isISRC; 
    if (isDigital)
      if (!Character.isDigit(inputString.charAt(0)) && !Character.isDigit(inputString.charAt(1)))
        isISRC = true;  
    return isISRC;
  }
  
  public static boolean userHasSelectionEditPermission(Context context) {
    User user = (User)context.getSession().getAttribute("user");
    boolean hasSelPermission = false;
    if (user == null)
      return false; 
    Acl acl = user.getAcl();
    if (acl == null)
      return false; 
    Vector companyAcls = acl.getCompanyAcl();
    if (companyAcls == null)
      return false; 
    for (int i = 0; i < companyAcls.size(); i++) {
      CompanyAcl companyAcl = (CompanyAcl)companyAcls.get(i);
      if (companyAcl != null)
        if (companyAcl.getAccessSelection() == 2)
          return true;  
    } 
    return false;
  }
  
  public static boolean userHasSelectionEditPermission(Acl acl) {
    boolean hasSelPermission = false;
    if (acl == null)
      return false; 
    Vector companyAcls = acl.getCompanyAcl();
    if (companyAcls == null)
      return false; 
    for (int i = 0; i < companyAcls.size(); i++) {
      CompanyAcl companyAcl = (CompanyAcl)companyAcls.get(i);
      if (companyAcl != null)
        if (companyAcl.getAccessSelection() == 2)
          return true;  
    } 
    return false;
  }
  
  public static int getIsStructureArchimedes(int structureId) {
    int result = 0;
    String query = "sp_get_Structure_IsArchimedes " + structureId;
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    result = connector.getIntegerField("ReturnId");
    connector.close();
    return result;
  }
  
  public static Vector filterCorporateByEntityName(Vector corporateObjects, String filter) {
    if (MilestoneHelper.isStringNotEmpty(filter)) {
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
        Label corporateObject = (Label)corporateObjects.get(i);
        String description = corporateObject.getEntityName().toUpperCase();
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
  
  public static void initializeCorporateStructure(CorporateStructureObject COS, JdbcConnector connector) {
    COS.setStructureID(connector.getInt("structure_id"));
    COS.setParentID(connector.getInt("parent_id"));
    COS.setStructureType(connector.getInt("type"));
    COS.setName(connector.getField("name"));
    COS.setStructureAbbreviation(connector.getField("abbreviation"));
    Calendar lastUpdatedOn = Calendar.getInstance();
    lastUpdatedOn.setTime(connector.getTimestamp("last_updated_on"));
    COS.setLastUpdateDate(lastUpdatedOn);
    COS.setLastUpdatingUser(connector.getInt("last_updated_by"));
    COS.setArchimedesId(connector.getInt("archimedes_id", -1));
    COS.setLastUpdatedCk(Long.parseLong(connector.getField("last_updated_ck"), 16));
  }
  
  public static boolean hasSchedule(int selectionID) {
    boolean exists = true;
    String sqlQuery = "Select top 1 release_id from release_detail where release_id = " + 
      selectionID;
    JdbcConnector connector = MilestoneHelper.getConnector(sqlQuery);
    connector.runQuery();
    if (!connector.more())
      exists = false; 
    connector.close();
    return exists;
  }
  
  public static String convertProductTypeToReleaseCalendar(String productType) {
    String retType = productType;
    if (productType.equalsIgnoreCase("physical"))
      retType = "Physical"; 
    if (productType.equalsIgnoreCase("digital"))
      retType = "Digital"; 
    if (productType.equalsIgnoreCase("both"))
      retType = "All"; 
    return retType;
  }
  
  public static String convertProductTypeToUserPref(String productType) {
    String retType = productType;
    if (productType.equalsIgnoreCase("Physical"))
      retType = "physical"; 
    if (productType.equalsIgnoreCase("Digital"))
      retType = "digital"; 
    if (productType.equalsIgnoreCase("All"))
      retType = "both"; 
    return retType;
  }
  
  public static String cleanText(String str) {
    int s = 0;
    int e = 0;
    String cleanString = "";
    boolean cleaned = false;
    try {
      byte[] utf8Bytes = str.getBytes("UTF8");
      for (int b = 0; b < utf8Bytes.length; b++) {
        if (utf8Bytes[b] == -62 && 
          b < utf8Bytes.length && (utf8Bytes[b + 1] == -108 || utf8Bytes[b + 1] == -109)) {
          utf8Bytes = shiftArray(utf8Bytes, b, b + 1, (byte)34);
          cleaned = true;
        } 
      } 
      if (cleaned) {
        cleanString = new String(utf8Bytes);
      } else {
        cleanString = str;
      } 
    } catch (UnsupportedEncodingException eN) {
      eN.printStackTrace();
    } 
    return cleanString;
  }
  
  public static byte[] shiftArray(byte[] byteArray, int startIndex, int endIndex, byte replacementValue) {
    byte[] returnArray = new byte[byteArray.length - 1];
    for (int j = 0; j < startIndex; j++)
      returnArray[j] = byteArray[j]; 
    returnArray[startIndex] = replacementValue;
    for (int k = endIndex + 1; k < byteArray.length; k++)
      returnArray[k - 1] = byteArray[k]; 
    return returnArray;
  }
  
  public static String getLabelDistCo(int labelId) {
    String retDistCo = "";
    Integer id = new Integer(labelId);
    if (Cache.cachedCorporateStructureHash.containsKey(id))
      try {
        Label label = (Label)Cache.cachedCorporateStructureHash.get(id);
        retDistCo = label.getDistCoName();
      } catch (Exception le) {
        System.out.println("    getLabelDistCo - ID: " + id.toString() + "  Msg: " + le.getMessage());
      }  
    return retDistCo;
  }
  
  public static int getLabelDistCoId(int labelId) {
    int retDistCoId = -1;
    Integer id = new Integer(labelId);
    if (Cache.cachedCorporateStructureHash.containsKey(id))
      try {
        Label label = (Label)Cache.cachedCorporateStructureHash.get(id);
        retDistCoId = label.getDistCoId();
      } catch (Exception le) {
        System.out.println("   getLabelDistCoId - ID: " + id.toString() + "  Msg: " + le.getMessage());
      }  
    return retDistCoId;
  }
  
  public static Hashtable<Integer, String> getDistCoNames() {
    names = new Hashtable();
    try {
      names = projectSearchSvcClient.DistributionCodGet();
      System.out.println("Distribution Co from WCF: " + names.size());
    } catch (Exception e) {
      System.out.println(e.getMessage());
    } 
    return names;
  }
  
  public static FormDropDownMenu BuildDistCoDropDown() {
    distCoHash = getDistCoNames();
    StringBuffer keys = new StringBuffer("-1");
    StringBuffer values = new StringBuffer("All");
    for (Enumeration e = distCoHash.keys(); e.hasMoreElements(); ) {
      Integer key = (Integer)e.nextElement();
      String value = (String)distCoHash.get(key);
      keys.append("," + key.toString());
      values.append("," + value);
    } 
    return new FormDropDownMenu("DistCoNames", keys.toString(), values.toString(), false);
  }
  
  public static Hashtable groupSelectionsForMexicoProductionByTypeConfigAndStreetDate(Vector selections) {
    Hashtable groupSelectionsByTypeAndStreetDate = new Hashtable();
    Vector finalVector = new Vector();
    if (selections == null)
      return groupSelectionsByTypeAndStreetDate; 
    for (int i = 0; i < selections.size(); i++) {
      Selection sel = (Selection)selections.elementAt(i);
      if (sel != null) {
        sel = SelectionManager.getInstance().getSelectionAndSchedule(sel.getSelectionID());
        String typeConfigString = "";
        String dayString = "";
        String status = (sel.getSelectionStatus() != null && sel.getSelectionStatus().getName() != null) ? 
          sel.getSelectionStatus().getName() : "";
        String typeString = (sel.getReleaseType() != null) ? sel.getReleaseType().getName() : "";
        String categoryString = (sel.getProductCategory() != null) ? sel.getProductCategory().getName() : "";
        String configString = (sel.getSelectionConfig().getSelectionConfigurationAbbreviation() != null) ? sel.getSelectionConfig().getSelectionConfigurationAbbreviation() : "";
        String labelName = "";
        boolean pAndD = sel.getPressAndDistribution();
        boolean intFlag = sel.getInternationalFlag();
        if (sel.getLabel() != null)
          labelName = sel.getLabel().getName().trim(); 
        String selProductCategory = (sel.getProductCategory() != null) ? sel.getProductCategory().getName() : "";
        int releasingFamilyId = sel.getReleaseFamilyId();
        if (selProductCategory.equals("Special Products")) {
          typeConfigString = "Special Products";
        } else if (typeString.startsWith("Promo") && 
          !labelName.equals("Polydor") && !pAndD && !intFlag) {
          typeConfigString = "Promos in Spanish";
        } else if (typeString.startsWith("Promo") && 
          labelName.equalsIgnoreCase("Polydor") && !pAndD && !intFlag) {
          typeConfigString = "Promos in English";
        } else if (typeString.startsWith("Commercial") && status.equalsIgnoreCase("TBS") && 
          !pAndD && !intFlag) {
          typeConfigString = "Pending Albums";
        } else if (typeString.startsWith("Commercial") && (
          status.equalsIgnoreCase("Active") || status.equalsIgnoreCase("Closed") || status.equalsIgnoreCase("Cancel")) && 
          !labelName.equalsIgnoreCase("Decca") && !labelName.equalsIgnoreCase("Deutsche Grammophon") && !labelName.equalsIgnoreCase("Philips") && 
          !pAndD && !intFlag && (!configString.equals("DVV") || !labelName.equalsIgnoreCase("Polystar"))) {
          typeConfigString = "Commercial";
        } else if (typeString.startsWith("Commercial") && (
          labelName.equalsIgnoreCase("Decca") || labelName.equalsIgnoreCase("Deutsche Grammophon") || labelName.equalsIgnoreCase("Philips")) && 
          !pAndD && !intFlag) {
          typeConfigString = "National Classical";
        } else if (typeString.startsWith("Commercial") && intFlag) {
          typeConfigString = "Popular Imports";
        } else if (typeString.startsWith("Commercial") && configString.equals("DVV") && 
          labelName.equalsIgnoreCase("Polystar") && !pAndD && !intFlag) {
          typeConfigString = "DVDs";
        } else if (typeString.startsWith("Promo") && pAndD) {
          typeConfigString = "Distributed Promos";
        } else if (typeString.startsWith("Commercial") && pAndD) {
          typeConfigString = "Distributed Commercial";
        } 
        if (sel.getStreetDate() != null && !status.equals("TBS")) {
          SimpleDateFormat formatter = new SimpleDateFormat("MM/yyyy");
          dayString = MilestoneHelper.getFormatedDate(sel.getStreetDate());
        } else {
          dayString = "TBS";
        } 
        String compositeName = String.valueOf(typeConfigString) + "|" + dayString;
        Vector selectionsForDates = (Vector)groupSelectionsByTypeAndStreetDate.get(compositeName);
        if (selectionsForDates == null) {
          selectionsForDates = new Vector();
          groupSelectionsByTypeAndStreetDate.put(compositeName, selectionsForDates);
        } 
        selectionsForDates.addElement(sel);
      } 
    } 
    return groupSelectionsByTypeAndStreetDate;
  }
  
  public static String getAbbrevAndDescription(String idVal, Vector descriptionVector) {
    String description = "";
    if (idVal != null && idVal.length() > 0) {
      LookupObject lookupObject = MilestoneHelper.getLookupObject(idVal, descriptionVector);
      if (lookupObject != null)
        description = String.valueOf(lookupObject.getAbbreviation()) + ":" + lookupObject.getName(); 
    } 
    return description;
  }
  
  public static Properties readConfigFile(String configFile) throws Exception {
    InputStream in = ClassLoader.getSystemResourceAsStream(configFile);
    if (in == null)
      in = new FileInputStream(configFile); 
    Properties defaultProps = new Properties();
    defaultProps.load(in);
    if (in != null)
      in.close(); 
    return defaultProps;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\MilestoneHelper_2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */