package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.universal.milestone.Cache;
import com.universal.milestone.EmailDistributionReleasingFamily;
import com.universal.milestone.Family;
import com.universal.milestone.JdbcConnector;
import com.universal.milestone.MilestoneHelper;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

public class EmailDistributionReleasingFamily {
  public static final String CMD_RELEASING_FAMILY = "-save-releasing-family";
  
  public static final String CMD_SAVE_RELEASING_FAMILY = "email-distribution-save-releasing-family";
  
  public static ComponentLog log = null;
  
  int labelFamilyId;
  
  int releaseFamilyId;
  
  boolean isChecked;
  
  String familyName;
  
  String familyAbbr;
  
  public EmailDistributionReleasingFamily(int labelFamilyId, int releaseFamilyId, boolean isChecked, String familyName, String familyAbbr) {
    this.isChecked = false;
    this.familyName = "";
    this.familyAbbr = "";
    this.labelFamilyId = labelFamilyId;
    this.releaseFamilyId = releaseFamilyId;
    this.isChecked = isChecked;
    this.familyName = familyName;
    this.familyAbbr = familyAbbr;
  }
  
  public int getLabelFamilyId() { return this.labelFamilyId; }
  
  public int getReleasingFamilyId() { return this.releaseFamilyId; }
  
  public boolean IsChecked() { return this.isChecked; }
  
  public String getFamillyName() { return this.familyName; }
  
  public String getFamilyAbbr() { return this.familyAbbr; }
  
  public static Hashtable get(int distributionId) {
    Hashtable releasingFamilyHash = null;
    String sqlStr = "SELECT rf.*, f.name as relFamilyName, f.abbreviation as abbr  FROM dbo.Email_Distribution_Release_Family rf  inner join vi_Structure f on rf.release_family_id = f.structure_id  WHERE rf.distribution_id = " + 
      
      String.valueOf(distributionId) + 
      " ORDER BY f.name;";
    JdbcConnector connector = MilestoneHelper.getConnector(sqlStr);
    connector.runQuery();
    Vector releasingFamilies = null;
    while (connector.more()) {
      if (releasingFamilyHash == null)
        releasingFamilyHash = new Hashtable(); 
      boolean isDefault = false;
      int familyId = connector.getInt("family_id");
      int relFamilyId = connector.getInt("release_family_id");
      String relFamilyName = connector.getField("relFamilyName");
      String relFamilyAbbr = connector.getField("abbr");
      EmailDistributionReleasingFamily releasingFamily = new EmailDistributionReleasingFamily(familyId, relFamilyId, 
          true, relFamilyName, relFamilyAbbr);
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
  
  public static boolean save(Context context) {
    String distributionId = context.getRequestValue("RFDistributionId");
    String labelFamId = context.getRequestValue("RFLabelFamId");
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
      String sqlStr = "delete from dbo.Email_Distribution_Release_Family WHERE distribution_id = " + 
        distributionId + " and family_id = " + labelFamId;
      connector = MilestoneHelper.getConnector(sqlStr);
      connector.getConnection().setAutoCommit(false);
      connector.setQuery(sqlStr);
      connector.runQuery();
      if (checkedRelFamilies != null)
        for (int i = 0; i < checkedRelFamilies.size(); i++) {
          String relFamId = (String)checkedRelFamilies.get(i);
          if (relFamId != null) {
            sqlStr = "insert into dbo.Email_Distribution_Release_Family ([distribution_id],[family_id],[release_family_id]) VALUES(" + 
              
              distributionId + "," + 
              labelFamId + "," + 
              relFamId + ")";
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
    return true;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\EmailDistributionReleasingFamily.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */