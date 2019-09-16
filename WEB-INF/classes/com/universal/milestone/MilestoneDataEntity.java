package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.DataEntity;
import com.techempower.DatabaseConnector;
import com.techempower.EnhancedProperties;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Audit;
import com.universal.milestone.AuditItem;
import com.universal.milestone.AuditTable;
import com.universal.milestone.JdbcConnector;
import com.universal.milestone.MilestoneDataEntity;
import com.universal.milestone.MilestoneHelper;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

public abstract class MilestoneDataEntity extends DataEntity {
  public static final String COMPONENT_CODE = "msDE";
  
  protected static boolean auditingEnabled = true;
  
  protected static Hashtable classToFields = new Hashtable();
  
  protected static Hashtable newEntityAudits = new Hashtable();
  
  protected static ComponentLog log;
  
  protected static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
  
  protected Vector newAudits;
  
  public static void configure(EnhancedProperties props, GeminiApplication application) {
    log = application.getLog("msDE");
    auditingEnabled = props.getYesNoProperty("AuditingEnabled", auditingEnabled);
    loadAuditItems();
  }
  
  protected static void loadAuditItems() {
    classesAudited = 0;
    int fieldsAudited = 0;
    int newEntities = 0;
    if (auditingEnabled) {
      try {
        JdbcConnector jdbcConnector = MilestoneHelper.getConnector("SELECT * FROM AuditTable;");
        jdbcConnector.runQuery();
        Vector results = MilestoneHelper.getDataEntities(
            Class.forName("com.universal.milestone.AuditTable"), 
            jdbcConnector);
        for (int i = 0; i < results.size(); i++) {
          AuditTable auditTable = (AuditTable)results.elementAt(i);
          try {
            Class dataEntityClass = Class.forName("com.universal.milestone." + 
                auditTable.className);
            Hashtable auditItems = new Hashtable();
            classToFields.put(dataEntityClass, auditItems);
            classesAudited++;
            JdbcConnector jdbcConnector1 = MilestoneHelper.getConnector(
                "SELECT * FROM AuditItem WHERE AuditEnabled = 'T' AND AuditTableID = " + 
                auditTable.auditTableID + ";");
            jdbcConnector1.runQuery();
            Vector fields = MilestoneHelper.getDataEntities(
                Class.forName("com.universal.milestone.AuditItem"), 
                jdbcConnector1);
            for (int field = 0; field < fields.size(); field++) {
              AuditItem auditItem = (AuditItem)fields.elementAt(field);
              if (auditItem.auditField != null) {
                auditItems.put(auditItem.auditField, auditItem);
                fieldsAudited++;
              } else {
                newEntityAudits.put(dataEntityClass, auditItem);
                newEntities++;
              } 
            } 
          } catch (ClassNotFoundException cnfexc) {
            log.debug("Class-to-audit not found: com.universal.milestone." + 
                
                auditTable.className);
          } 
        } 
      } catch (Exception exc) {
        log.log("Exception while building audit lookups: " + exc);
      } 
      log.debug(String.valueOf(classesAudited) + " classes (" + fieldsAudited + " fields) to be audited.");
      log.debug(String.valueOf(newEntities) + " new entity audits.");
    } else {
      log.debug("Auditing is disabled.");
      classToFields.clear();
      newEntityAudits.clear();
    } 
  }
  
  protected boolean individualAuditing = true;
  
  public Iterator getAudits() {
    if (this.newAudits != null)
      return this.newAudits.iterator(); 
    return null;
  }
  
  public abstract String getTableName();
  
  public abstract int getIdentity();
  
  public boolean isNew() { return (getIdentity() <= 0); }
  
  public void setAuditingEnabled(boolean enabled) { this.individualAuditing = enabled; }
  
  public boolean flushAudits(int userID) {
    if (auditingEnabled) {
      if (this.newAudits != null) {
        JdbcConnector jdbcConnector = MilestoneHelper.getConnector("");
        for (int i = 0; i < this.newAudits.size(); i++) {
          Audit audit = (Audit)this.newAudits.elementAt(i);
          if (audit.userID == 0)
            audit.userID = userID; 
          audit.updateDatabase(jdbcConnector);
        } 
        eraseAudits();
        return true;
      } 
      if (isNew() && this.individualAuditing) {
        AuditItem auditItem = (AuditItem)newEntityAudits.get(getClass());
        if (auditItem != null) {
          log.debug(auditItem.toString());
          Audit audit = new Audit(auditItem, 0, 1, 
              new Date(), "", "", userID);
          JdbcConnector jdbcConnector = MilestoneHelper.getConnector("");
          audit.updateDatabase(jdbcConnector);
          return true;
        } 
      } 
    } 
    return false;
  }
  
  public void eraseAudits() { this.newAudits = null; }
  
  public int updateDatabase(DatabaseConnector connector, int userID) {
    flushAudits(userID);
    return updateDatabase(connector);
  }
  
  protected void addAudit(Audit audit) {
    if (this.newAudits == null)
      this.newAudits = new Vector(); 
    this.newAudits.addElement(audit);
  }
  
  public void auditCheck(String fieldName, String oldValue, String newValue, int userID) {
    if (newValue == null)
      newValue = ""; 
    if (oldValue == null)
      oldValue = ""; 
    if (!newValue.equals(oldValue)) {
      AuditItem auditItem = getAuditItem(fieldName);
      if (auditItem != null) {
        Audit audit = new Audit(auditItem, getIdentity(), 
            2, new Date(), oldValue, newValue, 
            userID);
        addAudit(audit);
      } 
    } 
  }
  
  public void auditCheck(String fieldName, String oldValue, String newValue) { auditCheck(fieldName, oldValue, newValue, 0); }
  
  public void auditCheck(String fieldName, Date oldValue, Date newValue, int userID) {
    String oldValueString = "";
    String newValueString = "";
    if (oldValue != null)
      oldValueString = simpleDateFormat.format(oldValue); 
    if (newValue != null)
      newValueString = simpleDateFormat.format(newValue); 
    auditCheck(fieldName, oldValueString, newValueString, userID);
  }
  
  public void auditCheck(String fieldName, Date oldValue, Date newValue) { auditCheck(fieldName, oldValue, newValue, 0); }
  
  public void auditCheck(String fieldName, Calendar oldValue, Calendar newValue, int userID) {
    String oldValueString = "";
    String newValueString = "";
    if (oldValue != null)
      oldValueString = simpleDateFormat.format(oldValue.getTime()); 
    if (newValue != null)
      newValueString = simpleDateFormat.format(newValue.getTime()); 
    auditCheck(fieldName, oldValueString, newValueString, userID);
  }
  
  public void auditCheck(String fieldName, Calendar oldValue, Calendar newValue) { auditCheck(fieldName, oldValue, newValue, 0); }
  
  public void auditCheck(String fieldName, int oldValue, int newValue, int userID) { // Byte code:
    //   0: iload_2
    //   1: iload_3
    //   2: if_icmpeq -> 76
    //   5: aload_0
    //   6: aload_1
    //   7: invokevirtual getAuditItem : (Ljava/lang/String;)Lcom/universal/milestone/AuditItem;
    //   10: astore #5
    //   12: aload #5
    //   14: ifnull -> 76
    //   17: new com/universal/milestone/Audit
    //   20: dup
    //   21: aload #5
    //   23: aload_0
    //   24: invokevirtual getIdentity : ()I
    //   27: iconst_2
    //   28: new java/util/Date
    //   31: dup
    //   32: invokespecial <init> : ()V
    //   35: new java/lang/StringBuilder
    //   38: dup
    //   39: invokespecial <init> : ()V
    //   42: iload_2
    //   43: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   46: invokevirtual toString : ()Ljava/lang/String;
    //   49: new java/lang/StringBuilder
    //   52: dup
    //   53: invokespecial <init> : ()V
    //   56: iload_3
    //   57: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   60: invokevirtual toString : ()Ljava/lang/String;
    //   63: iload #4
    //   65: invokespecial <init> : (Lcom/universal/milestone/AuditItem;IILjava/util/Date;Ljava/lang/String;Ljava/lang/String;I)V
    //   68: astore #6
    //   70: aload_0
    //   71: aload #6
    //   73: invokevirtual addAudit : (Lcom/universal/milestone/Audit;)V
    //   76: return
    // Line number table:
    //   Java source line number -> byte code offset
    //   #469	-> 0
    //   #471	-> 5
    //   #473	-> 12
    //   #476	-> 17
    //   #477	-> 27
    //   #478	-> 63
    //   #476	-> 65
    //   #479	-> 70
    //   #482	-> 76
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	77	0	this	Lcom/universal/milestone/MilestoneDataEntity;
    //   0	77	1	fieldName	Ljava/lang/String;
    //   0	77	2	oldValue	I
    //   0	77	3	newValue	I
    //   0	77	4	userID	I
    //   12	64	5	auditItem	Lcom/universal/milestone/AuditItem;
    //   70	6	6	audit	Lcom/universal/milestone/Audit; }
  
  public void auditCheck(String fieldName, int oldValue, int newValue) { auditCheck(fieldName, oldValue, newValue, 0); }
  
  protected AuditItem getAuditItem(String fieldName) {
    AuditItem auditItem = null;
    if (auditingEnabled && this.individualAuditing)
      if (!isNew()) {
        Hashtable classFields = (Hashtable)classToFields.get(getClass());
        if (classFields != null)
          auditItem = (AuditItem)classFields.get(fieldName); 
      }  
    return auditItem;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\MilestoneDataEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */