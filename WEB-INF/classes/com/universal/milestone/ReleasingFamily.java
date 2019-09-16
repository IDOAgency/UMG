package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.universal.milestone.Cache;
import com.universal.milestone.Company;
import com.universal.milestone.Division;
import com.universal.milestone.Environment;
import com.universal.milestone.Family;
import com.universal.milestone.JdbcConnector;
import com.universal.milestone.Label;
import com.universal.milestone.MilestoneHashtable;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.ReleasingFamily;
import com.universal.milestone.SelectionHandler;
import com.universal.milestone.User;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

public class ReleasingFamily {
  public static final String CMD_RELEASING_FAMILY = "-save-releasing-family";
  
  public static final String CMD_SAVE_RELEASING_FAMILY = "user-security-save-releasing-family";
  
  public static ComponentLog log = null;
  
  int labelFamilyId;
  
  int releaseFamilyId;
  
  boolean isChecked;
  
  boolean isDefault;
  
  String familyName;
  
  String familyAbbr;
  
  Vector labelFamilies;
  
  public ReleasingFamily(int labelFamilyId, int releaseFamilyId, boolean isChecked, boolean isDefault, String familyName, String familyAbbr) {
    this.isChecked = false;
    this.isDefault = false;
    this.familyName = "";
    this.familyAbbr = "";
    this.labelFamilies = new Vector();
    this.labelFamilyId = labelFamilyId;
    this.releaseFamilyId = releaseFamilyId;
    this.isChecked = isChecked;
    this.isDefault = isDefault;
    this.familyName = familyName;
    this.familyAbbr = familyAbbr;
  }
  
  public int getLabelFamilyId() { return this.labelFamilyId; }
  
  public int getReleasingFamilyId() { return this.releaseFamilyId; }
  
  public boolean IsChecked() { return this.isChecked; }
  
  public boolean IsDefault() { return this.isDefault; }
  
  public String getFamillyName() { return this.familyName; }
  
  public String getFamilyAbbr() { return this.familyAbbr; }
  
  public Vector getLabelFamilies() { return this.labelFamilies; }
  
  public void setLabelFamilies(Vector labelFamilies) { this.labelFamilies = labelFamilies; }
  
  public static void setDebugLog(Context context) {
    if (log == null)
      log = context.getApplication().getLog("hSec"); 
  }
  
  public static void setDebugLog(ComponentLog hsecLog) {
    if (log == null)
      log = hsecLog; 
  }
  
  public static void debug(String outStr) {
    try {
      if (log != null)
        log.log(outStr); 
    } catch (Exception e) {
      System.out.println(outStr);
    } 
  }
  
  public static String getName(int relId) {
    String relFamilyName = "";
    relFamilyName = MilestoneHelper.getStructureName(relId);
    if (relFamilyName == null || relFamilyName.equals("")) {
      String sqlStr = "SELECT name  FROM vi_Structure   WHERE structure_id = " + 
        
        String.valueOf(relId);
      JdbcConnector connector = MilestoneHelper.getConnector(sqlStr);
      connector.runQuery();
      if (connector.more())
        relFamilyName = connector.getField("name"); 
      connector.close();
    } 
    return relFamilyName;
  }
  
  public static Hashtable get(int userId) {
    String sqlStr = "SELECT rf.*, f.name as relFamilyName, f.abbreviation as abbr  FROM User_Release_Family rf  inner join vi_Structure f on rf.release_family_id = f.structure_id  WHERE rf.user_id = " + 
      
      String.valueOf(userId) + 
      " ORDER BY f.name;";
    JdbcConnector connector = MilestoneHelper.getConnector(sqlStr);
    connector.runQuery();
    Hashtable releasingFamilyHash = new Hashtable();
    Vector releasingFamilies = null;
    while (connector.more()) {
      boolean isDefault = false;
      int familyId = connector.getInt("family_id");
      int relFamilyId = connector.getInt("release_family_id");
      String relFamilyName = connector.getField("relFamilyName");
      isDefault = connector.getBoolean("default_family_flag");
      String relFamilyAbbr = connector.getField("abbr");
      ReleasingFamily releasingFamily = new ReleasingFamily(familyId, relFamilyId, 
          true, isDefault, relFamilyName, relFamilyAbbr);
      String familyIdStr = Integer.toString(familyId);
      if (releasingFamilyHash.containsKey(familyIdStr)) {
        releasingFamilies = (Vector)releasingFamilyHash.get(familyIdStr);
        releasingFamilies.add(releasingFamily);
      } else {
        releasingFamilies = new Vector();
        releasingFamilies.add(releasingFamily);
        releasingFamilyHash.put(familyIdStr, releasingFamilies);
      } 
      connector.next();
    } 
    connector.close();
    return releasingFamilyHash;
  }
  
  public static Hashtable getReleasingFamilyLabelFamily(int userId) {
    String sqlStr = "SELECT rf.*, f.name as relFamilyName, f.abbreviation as abbr  FROM User_Release_Family rf  inner join vi_Structure f on rf.release_family_id = f.structure_id  WHERE rf.user_id = " + 
      
      String.valueOf(userId) + 
      " ORDER BY f.name;";
    JdbcConnector connector = MilestoneHelper.getConnector(sqlStr);
    connector.runQuery();
    Hashtable releasingFamilyLabelFamilyHash = new Hashtable();
    Vector labelFamilies = null;
    while (connector.more()) {
      boolean isDefault = false;
      int familyId = connector.getInt("family_id");
      int relFamilyId = connector.getInt("release_family_id");
      String familyIdStr = Integer.toString(familyId);
      String relFamilyIdStr = Integer.toString(relFamilyId);
      if (releasingFamilyLabelFamilyHash.containsKey(relFamilyIdStr)) {
        labelFamilies = (Vector)releasingFamilyLabelFamilyHash.get(relFamilyIdStr);
        labelFamilies.add(familyIdStr);
      } else {
        labelFamilies = new Vector();
        labelFamilies.add(familyIdStr);
        releasingFamilyLabelFamilyHash.put(relFamilyIdStr, labelFamilies);
      } 
      connector.next();
    } 
    connector.close();
    return releasingFamilyLabelFamilyHash;
  }
  
  public static boolean save(Context context) {
    String userId = context.getRequestValue("RFUserId");
    String labelFamId = context.getRequestValue("RFLabelFamId");
    String defaultRelFamId = context.getRequestValue("RFDftRelFamId");
    String elementPrefix = "RF";
    ArrayList checkedRelFamilies = new ArrayList();
    Vector families = Cache.getFamilies();
    if (families != null)
      for (int i = 0; i < families.size(); i++) {
        Family family = (Family)families.get(i);
        if (family != null) {
          String formElementName = String.valueOf(elementPrefix) + Integer.toString(family.getStructureID());
          String checkedRelFam = context.getRequestValue(formElementName);
          if (checkedRelFam != null)
            checkedRelFamilies.add(checkedRelFam); 
        } 
      }  
    connector = null;
    try {
      String sqlStr = "delete from User_Release_Family WHERE user_id = " + 
        userId + " and family_id = " + labelFamId;
      connector = MilestoneHelper.getConnector(sqlStr);
      connector.getConnection().setAutoCommit(false);
      connector.setQuery(sqlStr);
      connector.runQuery();
      if (checkedRelFamilies != null)
        for (int i = 0; i < checkedRelFamilies.size(); i++) {
          String relFamId = (String)checkedRelFamilies.get(i);
          if (relFamId != null) {
            sqlStr = "insert into User_Release_Family ([user_id],[family_id],[release_family_id],[default_family_flag]) VALUES(" + 
              
              userId + "," + 
              labelFamId + "," + 
              relFamId + "," + (
              relFamId.equals(defaultRelFamId) ? 1 : 0) + 
              ")";
            connector.setQuery(sqlStr);
            connector.runQuery();
          } 
        }  
      connector.getConnection().commit();
      connector.getConnection().setAutoCommit(true);
      connector.close();
    } catch (SQLException se) {
      System.err.println("SQLException: " + se.getMessage());
      System.err.println("SQLState:  " + se.getSQLState());
      System.err.println("Message:  " + se.getMessage());
    } finally {
      if (connector != null)
        connector.close(); 
    } 
    User user = (User)context.getSessionValue("user");
    if (user != null) {
      user.setReleasingFamily(null);
      user.setReleasingFamilyLabelFamily(null);
    } 
    return true;
  }
  
  public static boolean save(Context context, User user, User copiedUser) {
    String userId = Integer.toString(user.getUserId());
    ArrayList checkedRelFamilies = new ArrayList();
    Vector userReleasingFamilies = null;
    Hashtable releasingFamiliesHash = copiedUser.getReleasingFamily();
    if (releasingFamiliesHash != null) {
      Enumeration releasingFamiliesEnum = releasingFamiliesHash.elements();
      while (releasingFamiliesEnum.hasMoreElements()) {
        userReleasingFamilies = (Vector)releasingFamiliesEnum.nextElement();
        if (userReleasingFamilies != null)
          for (int f = 0; f < userReleasingFamilies.size(); f++) {
            ReleasingFamily relFamily = (ReleasingFamily)userReleasingFamilies.get(f);
            if (relFamily != null)
              checkedRelFamilies.add(relFamily); 
          }  
      } 
    } 
    connector = null;
    try {
      String sqlStr = "delete from User_Release_Family WHERE user_id = " + userId;
      connector = MilestoneHelper.getConnector(sqlStr);
      connector.getConnection().setAutoCommit(false);
      connector.setQuery(sqlStr);
      connector.runQuery();
      if (checkedRelFamilies != null)
        for (int i = 0; i < checkedRelFamilies.size(); i++) {
          ReleasingFamily relFamily = (ReleasingFamily)checkedRelFamilies.get(i);
          if (relFamily != null) {
            sqlStr = "insert into User_Release_Family ([user_id],[family_id],[release_family_id],[default_family_flag]) VALUES(" + 
              
              userId + "," + 
              Integer.toString(relFamily.getLabelFamilyId()) + "," + 
              Integer.toString(relFamily.getReleasingFamilyId()) + "," + (
              relFamily.IsDefault() ? 1 : 0) + 
              ")";
            connector.setQuery(sqlStr);
            connector.runQuery();
          } 
        }  
      connector.getConnection().commit();
      connector.getConnection().setAutoCommit(true);
      connector.close();
    } catch (SQLException se) {
      System.err.println("SQLException: " + se.getMessage());
      System.err.println("SQLState:  " + se.getSQLState());
      System.err.println("Message:  " + se.getMessage());
    } finally {
      if (connector != null)
        connector.close(); 
    } 
    if (user != null) {
      user.setReleasingFamily(null);
      user.setReleasingFamilyLabelFamily(null);
    } 
    return true;
  }
  
  public static Vector getUserReleasingFamiliesVectorOfFamilies(Context context) {
    Hashtable releasingFamiliesHash = null;
    User user = (User)context.getSessionValue("user");
    int userId = user.getUserId();
    if (user != null)
      releasingFamiliesHash = user.getReleasingFamily(); 
    if (releasingFamiliesHash == null)
      releasingFamiliesHash = get(userId); 
    Hashtable familiesHash = new Hashtable();
    Vector theFamilyList = null;
    Vector vUserEnvironments = MilestoneHelper.getUserEnvironments(context);
    vUserEnvironments = MilestoneHelper.removeUnusedCSO(vUserEnvironments, context, -1);
    Environment env = null;
    Vector theFamilies = Cache.getFamilies();
    Vector precache = new Vector();
    Family family = null;
    HashMap corpHashMap = MilestoneHelper.buildActiveCorporateStructureHashMap();
    for (int i = 0; i < theFamilies.size(); i++) {
      family = (Family)theFamilies.elementAt(i);
      int structId = family.getStructureID();
      familiesHash.put(Integer.toString(structId), family);
    } 
    Hashtable familyInVectorHash = new Hashtable();
    for (int i = 0; i < theFamilies.size(); i++) {
      family = (Family)theFamilies.elementAt(i);
      int structId = family.getStructureID();
      boolean boolVal = !corpHashMap.containsKey(new Integer(structId));
      if (family != null && boolVal)
        for (int j = 0; j < vUserEnvironments.size(); j++) {
          env = (Environment)vUserEnvironments.elementAt(j);
          if (env != null) {
            Family parent = (Family)MilestoneHelper.getStructureObject(env.getParentID());
            if (parent != null && parent.getStructureID() == family.getStructureID()) {
              Vector labelReleasingFamilies = (Vector)releasingFamiliesHash.get(Integer.toString(parent.getStructureID()));
              if (labelReleasingFamilies != null) {
                for (int x = 0; x < labelReleasingFamilies.size(); x++) {
                  ReleasingFamily releasingFamily = (ReleasingFamily)labelReleasingFamilies.get(x);
                  if (releasingFamily != null) {
                    Family rFamily = (Family)familiesHash.get(Integer.toString(releasingFamily.getReleasingFamilyId()));
                    Family rFamilyClone = cloneFamilyObject(family, rFamily);
                    if (!familyInVectorHash.containsKey(Integer.toString(rFamilyClone.getStructureID())))
                      familyInVectorHash.put(Integer.toString(rFamily.getStructureID()), rFamilyClone); 
                  } 
                } 
                break;
              } 
              Family familyClone = new Family();
              try {
                familyClone = (Family)family.clone();
              } catch (CloneNotSupportedException cloneNotSupportedException) {}
              if (!familyInVectorHash.containsKey(Integer.toString(familyClone.getStructureID())))
                familyInVectorHash.put(Integer.toString(familyClone.getStructureID()), familyClone); 
              break;
            } 
            env = null;
            parent = null;
          } 
        }  
      family = null;
    } 
    return theFamilyList = loadFamiliesFromHash(familyInVectorHash);
  }
  
  public static Vector getUserReleasingFamiliesVectorOfReleasingFamilies(Context context) {
    Hashtable releasingFamiliesHash = null;
    User user = (User)context.getSessionValue("user");
    int userId = user.getUserId();
    if (user != null)
      releasingFamiliesHash = user.getReleasingFamily(); 
    if (releasingFamiliesHash == null)
      releasingFamiliesHash = get(userId); 
    Hashtable familiesHash = new Hashtable();
    Vector theFamilyList = null;
    Vector vUserEnvironments = MilestoneHelper.getUserEnvironments(context);
    vUserEnvironments = MilestoneHelper.removeUnusedCSO(vUserEnvironments, context, -1);
    Environment env = null;
    Vector theFamilies = Cache.getFamilies();
    Vector precache = new Vector();
    Family family = null;
    HashMap corpHashMap = MilestoneHelper.buildActiveCorporateStructureHashMap();
    for (int i = 0; i < theFamilies.size(); i++) {
      family = (Family)theFamilies.elementAt(i);
      int structId = family.getStructureID();
      familiesHash.put(Integer.toString(structId), family);
    } 
    Hashtable familyInVectorHash = new Hashtable();
    for (int i = 0; i < theFamilies.size(); i++) {
      family = (Family)theFamilies.elementAt(i);
      int structId = family.getStructureID();
      boolean boolVal = !corpHashMap.containsKey(new Integer(structId));
      if (family != null && boolVal)
        for (int j = 0; j < vUserEnvironments.size(); j++) {
          env = (Environment)vUserEnvironments.elementAt(j);
          if (env != null) {
            Family parent = (Family)MilestoneHelper.getStructureObject(env.getParentID());
            if (parent != null && parent.getStructureID() == family.getStructureID()) {
              Vector labelReleasingFamilies = (Vector)releasingFamiliesHash.get(Integer.toString(parent.getStructureID()));
              if (labelReleasingFamilies != null) {
                for (int x = 0; x < labelReleasingFamilies.size(); x++) {
                  ReleasingFamily releasingFamily = (ReleasingFamily)labelReleasingFamilies.get(x);
                  if (releasingFamily != null)
                    if (!familyInVectorHash.containsKey(Integer.toString(releasingFamily.getReleasingFamilyId()))) {
                      releasingFamily.getLabelFamilies().clear();
                      releasingFamily.getLabelFamilies().add(family);
                      familyInVectorHash.put(Integer.toString(releasingFamily.getReleasingFamilyId()), releasingFamily);
                    } else {
                      ReleasingFamily relFamily = (ReleasingFamily)familyInVectorHash.get(Integer.toString(releasingFamily.getReleasingFamilyId()));
                      relFamily.getLabelFamilies().add(family);
                    }  
                } 
                break;
              } 
              if (!familyInVectorHash.containsKey(Integer.toString(family.getStructureID()))) {
                ReleasingFamily relFamily = new ReleasingFamily(family.getStructureID(), family.getStructureID(), 
                    true, true, family.getName(), family.getStructureAbbreviation());
                relFamily.getLabelFamilies().add(family);
                familyInVectorHash.put(Integer.toString(family.getStructureID()), relFamily);
                break;
              } 
              ReleasingFamily relFamily = (ReleasingFamily)familyInVectorHash.get(Integer.toString(family.getStructureID()));
              relFamily.getLabelFamilies().add(family);
              break;
            } 
            env = null;
            parent = null;
          } 
        }  
      family = null;
    } 
    return theFamilyList = loadReleasingFamiliesFromHash(familyInVectorHash);
  }
  
  public static Family cloneFamilyObject(Family family, Family rFamily) {
    Family rFamilyClone = new Family();
    try {
      rFamilyClone = (Family)family.clone();
      rFamilyClone.setName(rFamily.getName());
      rFamilyClone.setStructureID(rFamily.getStructureID());
    } catch (CloneNotSupportedException cloneNotSupportedException) {}
    return rFamilyClone;
  }
  
  public static Vector loadFamiliesFromHash(Hashtable familyInVectorHash) {
    Vector returnVector = new Vector();
    for (Iterator i = familyInVectorHash.values().iterator(); i.hasNext(); ) {
      Family family = (Family)i.next();
      returnVector.add(family);
    } 
    return returnVector;
  }
  
  public static Vector loadReleasingFamiliesFromHash(Hashtable familyInVectorHash) {
    Vector returnVector = new Vector();
    for (Iterator i = familyInVectorHash.values().iterator(); i.hasNext(); ) {
      ReleasingFamily relFamily = (ReleasingFamily)i.next();
      returnVector.add(relFamily);
    } 
    return returnVector;
  }
  
  public static Vector getReleasingFamilies(int userId, int labelFamilyId, Context context) {
    Hashtable releasingFamiliesHash = null;
    Vector labelReleasingFamilies = null;
    User user = (User)context.getSessionValue("user");
    if (user != null)
      releasingFamiliesHash = user.getReleasingFamily(); 
    if (releasingFamiliesHash == null)
      releasingFamiliesHash = get(userId); 
    if (releasingFamiliesHash != null && 
      releasingFamiliesHash.get(Integer.toString(labelFamilyId)) != null) {
      labelReleasingFamilies = (Vector)releasingFamiliesHash.get(Integer.toString(labelFamilyId));
    } else if (user.getAdministrator() == 1) {
      labelReleasingFamilies = new Vector();
      Vector families = SelectionHandler.filterCSO(Cache.getFamilies());
      if (families != null)
        for (int i = 0; i < families.size(); i++) {
          Family family = (Family)families.get(i);
          if (family != null) {
            boolean isDefault = false;
            if (family.getStructureID() == labelFamilyId)
              isDefault = true; 
            labelReleasingFamilies.add(new ReleasingFamily(
                  family.getStructureID(), 
                  family.getStructureID(), true, isDefault, family.getName(), 
                  family.getStructureAbbreviation()));
          } 
        }  
    } else {
      labelReleasingFamilies = new Vector();
      labelReleasingFamilies.add(new ReleasingFamily(labelFamilyId, 
            labelFamilyId, true, true, 
            MilestoneHelper.getStructureName(labelFamilyId), ""));
    } 
    return labelReleasingFamilies;
  }
  
  public static ReleasingFamily getDefaultReleasingFamily(int userId, int labelFamilyId, Context context) {
    Hashtable releasingFamiliesHash = null;
    ReleasingFamily releasingFamily = null;
    User user = (User)context.getSessionValue("user");
    if (user != null)
      releasingFamiliesHash = user.getReleasingFamily(); 
    if (releasingFamiliesHash == null)
      releasingFamiliesHash = get(userId); 
    if (releasingFamiliesHash != null) {
      Vector labelReleasingFamilies = (Vector)releasingFamiliesHash.get(Integer.toString(labelFamilyId));
      if (labelReleasingFamilies != null)
        for (int i = 0; i < labelReleasingFamilies.size(); i++) {
          releasingFamily = (ReleasingFamily)labelReleasingFamilies.get(i);
          if (releasingFamily != null && releasingFamily.IsDefault())
            return releasingFamily; 
        }  
    } 
    return new ReleasingFamily(labelFamilyId, labelFamilyId, true, true, 
        MilestoneHelper.getStructureName(labelFamilyId), "");
  }
  
  public static Vector getUserEnvironmentsFromFamily(ReleasingFamily releasingFamily, Context context) {
    HashMap corpHashMap = MilestoneHelper.buildActiveCorporateStructureHashMap();
    Vector userEnvironments = (Vector)MilestoneHelper.getUserEnvironments(context).clone();
    Vector result = new Vector();
    if (releasingFamily != null)
      for (int f = 0; f < releasingFamily.getLabelFamilies().size(); f++) {
        Family family = (Family)releasingFamily.getLabelFamilies().get(f);
        Vector familyEnvironments = family.getChildren();
        if (familyEnvironments != null)
          for (int i = 0; i < familyEnvironments.size(); i++) {
            Environment familyEnvironment = (Environment)familyEnvironments.get(i);
            for (int j = 0; j < userEnvironments.size(); j++) {
              Environment userEnvironment = (Environment)userEnvironments.get(j);
              int structureId = userEnvironment.getStructureID();
              boolean resultStructure = true;
              if (corpHashMap.containsKey(new Integer(structureId)))
                resultStructure = false; 
              if (userEnvironment.getStructureID() == familyEnvironment.getStructureID() && resultStructure) {
                result.add(familyEnvironment);
                break;
              } 
            } 
          }  
      }  
    return result;
  }
  
  public static String getJavaScriptCorporateArrayReleasingFamily(Context context) {
    StringBuffer result = new StringBuffer(100);
    String str = "";
    String value = new String();
    HashMap corpHashMap = MilestoneHelper.buildActiveCorporateStructureHashMap();
    User user = (User)context.getSessionValue("user");
    if (user.getReleasingFamily() == null)
      user.setReleasingFamily(get(user.getUserId())); 
    if (user.getReleasingFamilyLabelFamily() == null)
      user.setReleasingFamilyLabelFamily(getReleasingFamilyLabelFamily(user.getUserId())); 
    Vector vUserCompanies = SelectionHandler.filterSelectionActiveCompanies(user.getUserId(), MilestoneHelper.getUserCompanies(context));
    Vector vUserEnvironments = SelectionHandler.filterSelectionEnvironments(vUserCompanies);
    result.append("\n");
    result.append("var familyArray = new Array();\n");
    result.append("var environmentArray = new Array();\n");
    result.append("var companyArray = new Array();\n");
    int arrayIndex = 0;
    Vector vUserFamilies = getUserReleasingFamiliesVectorOfReleasingFamilies(context);
    result.append("familyArray[0] = new Array( 0, 'All'");
    Hashtable relFamEnvs = new Hashtable();
    for (int i = 0; i < vUserFamilies.size(); i++) {
      ReleasingFamily releasingFamily = (ReleasingFamily)vUserFamilies.elementAt(i);
      Vector environments = getUserEnvironmentsFromFamily(releasingFamily, context);
      if (environments != null) {
        environments = MilestoneHelper.removeUnusedCSO(environments, context, -1);
        for (int x = 0; x < environments.size(); x++) {
          Environment environment = (Environment)environments.get(x);
          if (!relFamEnvs.containsKey(Integer.toString(environment.getStructureID()))) {
            result.append(", ");
            result.append(environment.getStructureID());
            result.append(", '");
            result.append(MilestoneHelper.urlEncode(environment.getName()));
            result.append('\'');
            relFamEnvs.put(Integer.toString(environment.getStructureID()), environment);
          } 
        } 
      } 
    } 
    relFamEnvs = null;
    result.append(");\n");
    result.append("environmentArray[0] = new Array( 0, 'All');\n");
    for (int l = 0; l < vUserFamilies.size(); l++) {
      ReleasingFamily releasingFamily = (ReleasingFamily)vUserFamilies.elementAt(l);
      int structureId = releasingFamily.getReleasingFamilyId();
      boolean familyVal = true;
      if (corpHashMap.containsKey(new Integer(structureId)))
        familyVal = false; 
      if (releasingFamily != null && familyVal) {
        result.append("familyArray[");
        result.append(releasingFamily.getReleasingFamilyId());
        result.append("] = new Array(");
        boolean foundZeroth = false;
        Vector environmentVector = new Vector();
        Vector environments = getUserEnvironmentsFromFamily(releasingFamily, context);
        Hashtable labelFamilyHash = new Hashtable();
        for (int r = 0; r < releasingFamily.getLabelFamilies().size(); r++) {
          Family labelFamily = (Family)releasingFamily.getLabelFamilies().get(r);
          labelFamilyHash.put(Integer.toString(labelFamily.getStructureID()), labelFamily);
        } 
        environments = MilestoneHelper.removeUnusedCSO(environments, context, releasingFamily.getReleasingFamilyId());
        if (environments != null) {
          result.append(" 0, 'All',");
          for (int j = 0; j < environments.size(); j++) {
            Environment environment = (Environment)environments.elementAt(j);
            int structureIdc = environment.getStructureID();
            boolean boolVal = true;
            if (corpHashMap.containsKey(new Integer(structureIdc)))
              boolVal = false; 
            if (labelFamilyHash.containsKey(Integer.toString(environment.getParentID())) && boolVal) {
              if (foundZeroth)
                result.append(','); 
              result.append(' ');
              result.append(environment.getStructureID());
              result.append(", '");
              result.append(MilestoneHelper.urlEncode(environment.getName()));
              result.append('\'');
              foundZeroth = true;
              environmentVector.addElement(environment);
            } 
          } 
          if (foundZeroth) {
            result.append(");\n");
          } else {
            result.append(" 0, 'All');\n");
          } 
          for (int k = 0; k < environmentVector.size(); k++) {
            Environment environmentNode = (Environment)environmentVector.elementAt(k);
            result.append("environmentArray[");
            result.append(environmentNode.getStructureID());
            result.append("] = new Array(");
            Vector companyVector = new Vector();
            Vector companies = environmentNode.getChildren();
            if (companies != null) {
              result.append(" 0, 'All',");
              boolean foundZeroth2 = false;
              companies = MilestoneHelper.removeUnusedCSO(companies, context, -1);
              for (int m = 0; m < companies.size(); m++) {
                Company company = (Company)companies.elementAt(m);
                int structureIdc = company.getStructureID();
                boolean boolVal = true;
                if (corpHashMap.containsKey(new Integer(structureIdc)))
                  boolVal = false; 
                if (company.getParentID() == environmentNode.getStructureID() && boolVal) {
                  if (foundZeroth2)
                    result.append(','); 
                  result.append(' ');
                  result.append(company.getStructureID());
                  result.append(", '");
                  result.append(MilestoneHelper.urlEncode(company.getName()));
                  result.append('\'');
                  foundZeroth2 = true;
                  companyVector.addElement(company);
                } 
              } 
              if (foundZeroth2) {
                result.append(");\n");
              } else {
                result.append(" 0, 'All');\n");
              } 
              for (int n = 0; n < companyVector.size(); n++) {
                Company companyNode = (Company)companyVector.elementAt(n);
                result.append("companyArray[");
                result.append(companyNode.getStructureID());
                result.append("] = new Array(");
                Vector divisions = companyNode.getChildren();
                boolean foundSecond2 = false;
                result.append(" 0, 'All'");
                MilestoneHashtable labelsHash = new MilestoneHashtable();
                divisions = MilestoneHelper.removeUnusedCSO(divisions, context, -1);
                for (int x = 0; x < divisions.size(); x++) {
                  Division division = (Division)divisions.elementAt(x);
                  int structureIds = division.getStructureID();
                  boolean boolRes = true;
                  if (corpHashMap.containsKey(new Integer(structureIds)))
                    boolRes = false; 
                  if (division != null && boolRes) {
                    Vector labels = division.getChildren();
                    labels = MilestoneHelper.removeUnusedCSO(labels, context, -1);
                    for (int y = 0; y < labels.size(); y++) {
                      Label labelNode = (Label)labels.get(y);
                      int structureIdl = labelNode.getStructureID();
                      boolean boolVal = true;
                      if (corpHashMap.containsKey(new Integer(structureIdl)))
                        boolVal = false; 
                      if (labelNode.getParentID() == division.getStructureID() && boolVal) {
                        String labelName = MilestoneHelper.urlEncode(labelNode.getName());
                        if (!labelsHash.containsKey(labelName)) {
                          labelsHash.put(labelName, Integer.toString(labelNode.getStructureID()));
                        } else {
                          String hashValue = (String)labelsHash.get(labelName);
                          hashValue = String.valueOf(hashValue) + "," + Integer.toString(labelNode.getStructureID());
                          labelsHash.put(labelName, hashValue);
                        } 
                        foundSecond2 = true;
                      } 
                    } 
                  } 
                } 
                if (foundSecond2) {
                  boolean firstPass = true;
                  String[] labelKeys = new String[labelsHash.size()];
                  int x = 0;
                  for (Enumeration e = labelsHash.keys(); e.hasMoreElements(); x++) {
                    String hashKey = (String)e.nextElement();
                    labelKeys[x] = hashKey;
                  } 
                  Arrays.sort(labelKeys);
                  for (int i = 0; i < labelKeys.length; i++) {
                    String hashValue = (String)labelsHash.get(labelKeys[i]);
                    if (firstPass)
                      result.append(','); 
                    result.append(' ');
                    result.append("'" + hashValue + "'");
                    result.append(", '");
                    result.append(labelKeys[i]);
                    result.append('\'');
                    firstPass = true;
                  } 
                  result.append(");\n");
                } else {
                  result.append(");\n");
                } 
              } 
            } 
          } 
        } 
      } 
    } 
    corpHashMap = null;
    return result.toString();
  }
  
  public static String getJavaScriptCorporateArrayReleasingFamilySearch(Context context) {
    StringBuffer result = new StringBuffer(100);
    StringBuffer allCompanies = new StringBuffer(1000);
    StringBuffer allLabels = new StringBuffer(1000);
    String str = "";
    String value = new String();
    HashMap corpHashMap = MilestoneHelper.buildActiveCorporateStructureHashMap();
    User user = (User)context.getSessionValue("user");
    if (user.getReleasingFamily() == null)
      user.setReleasingFamily(get(user.getUserId())); 
    if (user.getReleasingFamilyLabelFamily() == null)
      user.setReleasingFamilyLabelFamily(getReleasingFamilyLabelFamily(user.getUserId())); 
    Vector vUserCompanies = SelectionHandler.filterSelectionActiveCompanies(user.getUserId(), MilestoneHelper.getUserCompanies(context));
    Vector vUserEnvironments = SelectionHandler.filterSelectionEnvironments(vUserCompanies);
    result.append("\n");
    result.append("var familyArray = new Array();\n");
    result.append("var environmentArray = new Array();\n");
    result.append("var companyArray = new Array();\n");
    int arrayIndex = 0;
    Vector vUserFamilies = getUserReleasingFamiliesVectorOfReleasingFamilies(context);
    result.append("familyArray[0] = new Array( 0, 'All'");
    Hashtable relFamEnvs = new Hashtable();
    Vector environments = MilestoneHelper.getUserEnvironments(context);
    Vector myCompanies = MilestoneHelper.getUserCompanies(context);
    environments = SelectionHandler.filterSelectionEnvironments(myCompanies);
    environments = MilestoneHelper.removeUnusedCSO(environments, context, -1);
    Vector sortedVector = new Vector();
    sortedVector = MilestoneHelper.sortCorporateVectorByName(environments);
    if (sortedVector != null)
      for (int x = 0; x < sortedVector.size(); x++) {
        Environment environment = (Environment)sortedVector.get(x);
        result.append(", ");
        result.append(environment.getStructureID());
        result.append(", '");
        result.append(MilestoneHelper.urlEncode(environment.getName()));
        result.append('\'');
        relFamEnvs.put(Integer.toString(environment.getStructureID()), environment);
      }  
    relFamEnvs = null;
    result.append(");\n");
    for (int l = 0; l < vUserFamilies.size(); l++) {
      ReleasingFamily releasingFamily = (ReleasingFamily)vUserFamilies.elementAt(l);
      int structureId = releasingFamily.getReleasingFamilyId();
      boolean familyVal = true;
      if (corpHashMap.containsKey(new Integer(structureId)))
        familyVal = false; 
      if (releasingFamily != null && familyVal) {
        result.append("familyArray[");
        result.append(releasingFamily.getReleasingFamilyId());
        result.append("] = new Array(");
        boolean foundZeroth = false;
        Vector environmentVector = new Vector();
        environments = getUserEnvironmentsFromFamily(releasingFamily, context);
        environments = MilestoneHelper.removeUnusedCSO(environments, context, releasingFamily.getReleasingFamilyId());
        Hashtable labelFamilyHash = new Hashtable();
        for (int r = 0; r < releasingFamily.getLabelFamilies().size(); r++) {
          Family labelFamily = (Family)releasingFamily.getLabelFamilies().get(r);
          labelFamilyHash.put(Integer.toString(labelFamily.getStructureID()), labelFamily);
        } 
        if (environments != null) {
          result.append(" 0, 'All',");
          for (int j = 0; j < environments.size(); j++) {
            Environment environment = (Environment)environments.elementAt(j);
            int structureIdc = environment.getStructureID();
            boolean boolVal = true;
            if (corpHashMap.containsKey(new Integer(structureIdc)))
              boolVal = false; 
            if (labelFamilyHash.containsKey(Integer.toString(environment.getParentID())) && boolVal) {
              if (foundZeroth)
                result.append(','); 
              result.append(' ');
              result.append(environment.getStructureID());
              result.append(", '");
              result.append(MilestoneHelper.urlEncode(environment.getName()));
              result.append('\'');
              foundZeroth = true;
              environmentVector.addElement(environment);
            } 
          } 
          if (foundZeroth) {
            result.append(");\n");
          } else {
            result.append(" 0, 'All');\n");
          } 
          for (int k = 0; k < environmentVector.size(); k++) {
            Environment environmentNode = (Environment)environmentVector.elementAt(k);
            result.append("environmentArray[");
            result.append(environmentNode.getStructureID());
            result.append("] = new Array(");
            Vector companyVector = new Vector();
            Vector companies = environmentNode.getChildren();
            companies = MilestoneHelper.removeUnusedCSO(companies, context, -1);
            if (companies != null) {
              result.append(" 0, 'All',");
              boolean foundZeroth2 = false;
              for (int m = 0; m < companies.size(); m++) {
                Company company = (Company)companies.elementAt(m);
                int structureIdc = company.getStructureID();
                boolean boolVal = true;
                if (corpHashMap.containsKey(new Integer(structureIdc)))
                  boolVal = false; 
                if (company.getParentID() == environmentNode.getStructureID() && boolVal) {
                  if (foundZeroth2)
                    result.append(','); 
                  result.append(' ');
                  result.append(company.getStructureID());
                  result.append(", '");
                  result.append(MilestoneHelper.urlEncode(company.getName()));
                  result.append('\'');
                  foundZeroth2 = true;
                  companyVector.addElement(company);
                } 
              } 
              if (foundZeroth2) {
                result.append(");\n");
              } else {
                result.append(" 0, 'All');\n");
              } 
              for (int n = 0; n < companyVector.size(); n++) {
                Company companyNode = (Company)companyVector.elementAt(n);
                result.append("companyArray[");
                result.append(companyNode.getStructureID());
                result.append("] = new Array(");
                Vector divisions = companyNode.getChildren();
                divisions = MilestoneHelper.removeUnusedCSO(divisions, context, -1);
                boolean foundSecond2 = false;
                result.append(" 0, 'All'");
                MilestoneHashtable labelsHash = new MilestoneHashtable();
                for (int x = 0; x < divisions.size(); x++) {
                  Division division = (Division)divisions.elementAt(x);
                  int structureIds = division.getStructureID();
                  boolean boolRes = true;
                  if (corpHashMap.containsKey(new Integer(structureIds)))
                    boolRes = false; 
                  if (division != null && boolRes) {
                    Vector labels = division.getChildren();
                    labels = MilestoneHelper.removeUnusedCSO(labels, context, -1);
                    for (int y = 0; y < labels.size(); y++) {
                      Label labelNode = (Label)labels.get(y);
                      int structureIdl = labelNode.getStructureID();
                      boolean boolVal = true;
                      if (corpHashMap.containsKey(new Integer(structureIdl)))
                        boolVal = false; 
                      if (labelNode.getParentID() == division.getStructureID() && boolVal) {
                        String labelName = MilestoneHelper.urlEncode(labelNode.getName());
                        if (!labelsHash.containsKey(labelName)) {
                          labelsHash.put(labelName, Integer.toString(labelNode.getStructureID()));
                        } else {
                          String hashValue = (String)labelsHash.get(labelName);
                          hashValue = String.valueOf(hashValue) + "," + Integer.toString(labelNode.getStructureID());
                          labelsHash.put(labelName, hashValue);
                        } 
                        foundSecond2 = true;
                      } 
                    } 
                  } 
                } 
                if (foundSecond2) {
                  boolean firstPass = true;
                  String[] labelKeys = new String[labelsHash.size()];
                  int x = 0;
                  for (Enumeration e = labelsHash.keys(); e.hasMoreElements(); x++) {
                    String hashKey = (String)e.nextElement();
                    labelKeys[x] = hashKey;
                  } 
                  Arrays.sort(labelKeys);
                  for (int i = 0; i < labelKeys.length; i++) {
                    String hashValue = (String)labelsHash.get(labelKeys[i]);
                    if (firstPass)
                      result.append(','); 
                    result.append(' ');
                    result.append("'" + hashValue + "'");
                    result.append(", '");
                    result.append(labelKeys[i]);
                    result.append('\'');
                    allLabels.append(',');
                    allLabels.append("'" + hashValue + "'");
                    allLabels.append(", '");
                    allLabels.append(labelKeys[i]);
                    allLabels.append('\'');
                    firstPass = true;
                  } 
                  result.append(");\n");
                } else {
                  result.append(");\n");
                } 
              } 
            } 
          } 
        } 
      } 
    } 
    Vector searchCompanies = null;
    searchCompanies = MilestoneHelper.getUserCompanies(context);
    searchCompanies = MilestoneHelper.removeUnusedCSO(searchCompanies, context, -1);
    sortedVector = MilestoneHelper.sortCorporateVectorByName(searchCompanies);
    result.append("environmentArray[0] = new Array( 0, 'All'");
    for (int c = 0; c < sortedVector.size(); c++) {
      Company comp = (Company)sortedVector.get(c);
      result.append(',');
      result.append(comp.getStructureID());
      result.append(", '");
      result.append(MilestoneHelper.urlEncode(comp.getName()));
      result.append('\'');
    } 
    result.append(");\n");
    Vector searchLabels = MilestoneHelper.getUserLabels(sortedVector);
    searchLabels = MilestoneHelper.removeUnusedCSO(searchLabels, context, -1);
    sortedVector = MilestoneHelper.sortCorporateVectorByName(searchLabels);
    MilestoneHashtable labelsHash = new MilestoneHashtable();
    for (int c = 0; c < sortedVector.size(); c++) {
      Label label = (Label)sortedVector.get(c);
      String labelName = MilestoneHelper.urlEncode(label.getName());
      if (!labelsHash.containsKey(labelName)) {
        labelsHash.put(labelName, Integer.toString(label.getStructureID()));
      } else {
        String hashValue = (String)labelsHash.get(labelName);
        hashValue = String.valueOf(hashValue) + "," + Integer.toString(label.getStructureID());
        labelsHash.put(labelName, hashValue);
      } 
    } 
    String[] labelKeys = new String[labelsHash.size()];
    int x = 0;
    for (Enumeration e = labelsHash.keys(); e.hasMoreElements(); x++) {
      String hashKey = (String)e.nextElement();
      labelKeys[x] = hashKey;
    } 
    Arrays.sort(labelKeys);
    result.append("companyArray[0] = new Array( 0, 'All'");
    for (int i = 0; i < labelKeys.length; i++) {
      String hashValue = (String)labelsHash.get(labelKeys[i]);
      result.append(',');
      result.append("'" + hashValue + "'");
      result.append(", '");
      result.append(labelKeys[i]);
      result.append('\'');
    } 
    result.append(");\n");
    corpHashMap = null;
    return result.toString();
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\ReleasingFamily.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */