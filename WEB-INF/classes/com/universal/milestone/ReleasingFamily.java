/*      */ package WEB-INF.classes.com.universal.milestone;
/*      */ import com.techempower.gemini.Context;
/*      */ import com.universal.milestone.Company;
/*      */ import com.universal.milestone.Division;
/*      */ import com.universal.milestone.Environment;
/*      */ import com.universal.milestone.Family;
/*      */ import com.universal.milestone.JdbcConnector;
/*      */ import com.universal.milestone.Label;
/*      */ import com.universal.milestone.MilestoneHashtable;
/*      */ import com.universal.milestone.MilestoneHelper;
/*      */ import com.universal.milestone.ReleasingFamily;
/*      */ import com.universal.milestone.User;
/*      */ import java.sql.SQLException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.Vector;
/*      */ 
/*      */ public class ReleasingFamily {
/*      */   public static final String CMD_RELEASING_FAMILY = "-save-releasing-family";
/*   23 */   public static ComponentLog log = null; public static final String CMD_SAVE_RELEASING_FAMILY = "user-security-save-releasing-family"; int labelFamilyId; int releaseFamilyId; boolean isChecked;
/*      */   boolean isDefault;
/*      */   String familyName;
/*      */   String familyAbbr;
/*      */   Vector labelFamilies;
/*      */   
/*      */   public ReleasingFamily(int labelFamilyId, int releaseFamilyId, boolean isChecked, boolean isDefault, String familyName, String familyAbbr) {
/*   30 */     this.isChecked = false;
/*   31 */     this.isDefault = false;
/*   32 */     this.familyName = "";
/*   33 */     this.familyAbbr = "";
/*   34 */     this.labelFamilies = new Vector();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   42 */     this.labelFamilyId = labelFamilyId;
/*   43 */     this.releaseFamilyId = releaseFamilyId;
/*   44 */     this.isChecked = isChecked;
/*   45 */     this.isDefault = isDefault;
/*   46 */     this.familyName = familyName;
/*   47 */     this.familyAbbr = familyAbbr;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   55 */   public int getLabelFamilyId() { return this.labelFamilyId; }
/*      */ 
/*      */ 
/*      */   
/*   59 */   public int getReleasingFamilyId() { return this.releaseFamilyId; }
/*      */ 
/*      */ 
/*      */   
/*   63 */   public boolean IsChecked() { return this.isChecked; }
/*      */ 
/*      */ 
/*      */   
/*   67 */   public boolean IsDefault() { return this.isDefault; }
/*      */ 
/*      */ 
/*      */   
/*   71 */   public String getFamillyName() { return this.familyName; }
/*      */ 
/*      */ 
/*      */   
/*   75 */   public String getFamilyAbbr() { return this.familyAbbr; }
/*      */ 
/*      */ 
/*      */   
/*   79 */   public Vector getLabelFamilies() { return this.labelFamilies; }
/*      */ 
/*      */ 
/*      */   
/*   83 */   public void setLabelFamilies(Vector labelFamilies) { this.labelFamilies = labelFamilies; }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setDebugLog(Context context) {
/*   89 */     if (log == null) {
/*   90 */       log = context.getApplication().getLog("hSec");
/*      */     }
/*      */   }
/*      */   
/*      */   public static void setDebugLog(ComponentLog hsecLog) {
/*   95 */     if (log == null)
/*   96 */       log = hsecLog; 
/*      */   }
/*      */   
/*      */   public static void debug(String outStr) {
/*      */     try {
/*  101 */       if (log != null)
/*  102 */         log.log(outStr); 
/*  103 */     } catch (Exception e) {
/*      */ 
/*      */       
/*  106 */       System.out.println(outStr);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getName(int relId) {
/*  116 */     String relFamilyName = "";
/*      */     
/*  118 */     relFamilyName = MilestoneHelper.getStructureName(relId);
/*      */ 
/*      */     
/*  121 */     if (relFamilyName == null || relFamilyName.equals("")) {
/*      */       
/*  123 */       String sqlStr = "SELECT name  FROM vi_Structure   WHERE structure_id = " + 
/*      */         
/*  125 */         String.valueOf(relId);
/*      */ 
/*      */       
/*  128 */       JdbcConnector connector = MilestoneHelper.getConnector(sqlStr);
/*  129 */       connector.runQuery();
/*      */       
/*  131 */       if (connector.more()) {
/*  132 */         relFamilyName = connector.getField("name");
/*      */       }
/*  134 */       connector.close();
/*      */     } 
/*      */     
/*  137 */     return relFamilyName;
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
/*      */   public static Hashtable get(int userId) {
/*  151 */     String sqlStr = "SELECT rf.*, f.name as relFamilyName, f.abbreviation as abbr  FROM User_Release_Family rf  inner join vi_Structure f on rf.release_family_id = f.structure_id  WHERE rf.user_id = " + 
/*      */ 
/*      */       
/*  154 */       String.valueOf(userId) + 
/*  155 */       " ORDER BY f.name;";
/*      */ 
/*      */     
/*  158 */     JdbcConnector connector = MilestoneHelper.getConnector(sqlStr);
/*  159 */     connector.runQuery();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  165 */     Hashtable releasingFamilyHash = new Hashtable();
/*  166 */     Vector releasingFamilies = null;
/*      */     
/*  168 */     while (connector.more()) {
/*      */ 
/*      */       
/*  171 */       boolean isDefault = false;
/*  172 */       int familyId = connector.getInt("family_id");
/*  173 */       int relFamilyId = connector.getInt("release_family_id");
/*  174 */       String relFamilyName = connector.getField("relFamilyName");
/*  175 */       isDefault = connector.getBoolean("default_family_flag");
/*  176 */       String relFamilyAbbr = connector.getField("abbr");
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  181 */       ReleasingFamily releasingFamily = new ReleasingFamily(familyId, relFamilyId, 
/*  182 */           true, isDefault, relFamilyName, relFamilyAbbr);
/*      */       
/*  184 */       String familyIdStr = Integer.toString(familyId);
/*  185 */       if (releasingFamilyHash.containsKey(familyIdStr)) {
/*      */         
/*  187 */         releasingFamilies = (Vector)releasingFamilyHash.get(familyIdStr);
/*  188 */         releasingFamilies.add(releasingFamily);
/*      */       }
/*      */       else {
/*      */         
/*  192 */         releasingFamilies = new Vector();
/*  193 */         releasingFamilies.add(releasingFamily);
/*  194 */         releasingFamilyHash.put(familyIdStr, releasingFamilies);
/*      */       } 
/*      */       
/*  197 */       connector.next();
/*      */     } 
/*      */     
/*  200 */     connector.close();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  205 */     return releasingFamilyHash;
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
/*      */   public static Hashtable getReleasingFamilyLabelFamily(int userId) {
/*  219 */     String sqlStr = "SELECT rf.*, f.name as relFamilyName, f.abbreviation as abbr  FROM User_Release_Family rf  inner join vi_Structure f on rf.release_family_id = f.structure_id  WHERE rf.user_id = " + 
/*      */ 
/*      */       
/*  222 */       String.valueOf(userId) + 
/*  223 */       " ORDER BY f.name;";
/*      */ 
/*      */     
/*  226 */     JdbcConnector connector = MilestoneHelper.getConnector(sqlStr);
/*  227 */     connector.runQuery();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  233 */     Hashtable releasingFamilyLabelFamilyHash = new Hashtable();
/*  234 */     Vector labelFamilies = null;
/*      */     
/*  236 */     while (connector.more()) {
/*      */ 
/*      */       
/*  239 */       boolean isDefault = false;
/*  240 */       int familyId = connector.getInt("family_id");
/*  241 */       int relFamilyId = connector.getInt("release_family_id");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  247 */       String familyIdStr = Integer.toString(familyId);
/*  248 */       String relFamilyIdStr = Integer.toString(relFamilyId);
/*  249 */       if (releasingFamilyLabelFamilyHash.containsKey(relFamilyIdStr)) {
/*      */         
/*  251 */         labelFamilies = (Vector)releasingFamilyLabelFamilyHash.get(relFamilyIdStr);
/*  252 */         labelFamilies.add(familyIdStr);
/*      */       }
/*      */       else {
/*      */         
/*  256 */         labelFamilies = new Vector();
/*  257 */         labelFamilies.add(familyIdStr);
/*  258 */         releasingFamilyLabelFamilyHash.put(relFamilyIdStr, labelFamilies);
/*      */       } 
/*      */       
/*  261 */       connector.next();
/*      */     } 
/*      */     
/*  264 */     connector.close();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  269 */     return releasingFamilyLabelFamilyHash;
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
/*      */   public static boolean save(Context context) {
/*  284 */     String userId = context.getRequestValue("RFUserId");
/*  285 */     String labelFamId = context.getRequestValue("RFLabelFamId");
/*  286 */     String defaultRelFamId = context.getRequestValue("RFDftRelFamId");
/*  287 */     String elementPrefix = "RF";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  294 */     ArrayList checkedRelFamilies = new ArrayList();
/*      */ 
/*      */     
/*  297 */     Vector families = Cache.getFamilies();
/*      */     
/*  299 */     if (families != null)
/*      */     {
/*  301 */       for (int i = 0; i < families.size(); i++) {
/*      */         
/*  303 */         Family family = (Family)families.get(i);
/*  304 */         if (family != null) {
/*      */           
/*  306 */           String formElementName = String.valueOf(elementPrefix) + Integer.toString(family.getStructureID());
/*  307 */           String checkedRelFam = context.getRequestValue(formElementName);
/*  308 */           if (checkedRelFam != null)
/*      */           {
/*  310 */             checkedRelFamilies.add(checkedRelFam);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  318 */     connector = null;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  324 */       String sqlStr = "delete from User_Release_Family WHERE user_id = " + 
/*  325 */         userId + " and family_id = " + labelFamId;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  330 */       connector = MilestoneHelper.getConnector(sqlStr);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  336 */       connector.getConnection().setAutoCommit(false);
/*      */ 
/*      */       
/*  339 */       connector.setQuery(sqlStr);
/*      */       
/*  341 */       connector.runQuery();
/*      */ 
/*      */       
/*  344 */       if (checkedRelFamilies != null)
/*      */       {
/*  346 */         for (int i = 0; i < checkedRelFamilies.size(); i++) {
/*      */           
/*  348 */           String relFamId = (String)checkedRelFamilies.get(i);
/*  349 */           if (relFamId != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  354 */             sqlStr = "insert into User_Release_Family ([user_id],[family_id],[release_family_id],[default_family_flag]) VALUES(" + 
/*      */ 
/*      */               
/*  357 */               userId + "," + 
/*  358 */               labelFamId + "," + 
/*  359 */               relFamId + "," + (
/*  360 */               relFamId.equals(defaultRelFamId) ? 1 : 0) + 
/*  361 */               ")";
/*      */ 
/*      */             
/*  364 */             connector.setQuery(sqlStr);
/*      */             
/*  366 */             connector.runQuery();
/*      */           } 
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  373 */       connector.getConnection().commit();
/*      */ 
/*      */       
/*  376 */       connector.getConnection().setAutoCommit(true);
/*      */ 
/*      */       
/*  379 */       connector.close();
/*      */     }
/*  381 */     catch (SQLException se) {
/*      */       
/*  383 */       System.err.println("SQLException: " + se.getMessage());
/*  384 */       System.err.println("SQLState:  " + se.getSQLState());
/*  385 */       System.err.println("Message:  " + se.getMessage());
/*      */     } finally {
/*      */       
/*  388 */       if (connector != null) {
/*  389 */         connector.close();
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  394 */     User user = (User)context.getSessionValue("user");
/*  395 */     if (user != null) {
/*      */       
/*  397 */       user.setReleasingFamily(null);
/*  398 */       user.setReleasingFamilyLabelFamily(null);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  404 */     return true;
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
/*      */   public static boolean save(Context context, User user, User copiedUser) {
/*  420 */     String userId = Integer.toString(user.getUserId());
/*      */ 
/*      */     
/*  423 */     ArrayList checkedRelFamilies = new ArrayList();
/*      */ 
/*      */     
/*  426 */     Vector userReleasingFamilies = null;
/*  427 */     Hashtable releasingFamiliesHash = copiedUser.getReleasingFamily();
/*  428 */     if (releasingFamiliesHash != null) {
/*      */       
/*  430 */       Enumeration releasingFamiliesEnum = releasingFamiliesHash.elements();
/*  431 */       while (releasingFamiliesEnum.hasMoreElements()) {
/*      */         
/*  433 */         userReleasingFamilies = (Vector)releasingFamiliesEnum.nextElement();
/*  434 */         if (userReleasingFamilies != null)
/*      */         {
/*  436 */           for (int f = 0; f < userReleasingFamilies.size(); f++) {
/*      */             
/*  438 */             ReleasingFamily relFamily = (ReleasingFamily)userReleasingFamilies.get(f);
/*  439 */             if (relFamily != null)
/*      */             {
/*      */               
/*  442 */               checkedRelFamilies.add(relFamily);
/*      */             }
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  450 */     connector = null;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  456 */       String sqlStr = "delete from User_Release_Family WHERE user_id = " + userId;
/*      */ 
/*      */       
/*  459 */       connector = MilestoneHelper.getConnector(sqlStr);
/*      */ 
/*      */       
/*  462 */       connector.getConnection().setAutoCommit(false);
/*      */ 
/*      */       
/*  465 */       connector.setQuery(sqlStr);
/*      */       
/*  467 */       connector.runQuery();
/*      */ 
/*      */       
/*  470 */       if (checkedRelFamilies != null)
/*      */       {
/*  472 */         for (int i = 0; i < checkedRelFamilies.size(); i++) {
/*      */           
/*  474 */           ReleasingFamily relFamily = (ReleasingFamily)checkedRelFamilies.get(i);
/*  475 */           if (relFamily != null) {
/*      */             
/*  477 */             sqlStr = "insert into User_Release_Family ([user_id],[family_id],[release_family_id],[default_family_flag]) VALUES(" + 
/*      */ 
/*      */               
/*  480 */               userId + "," + 
/*  481 */               Integer.toString(relFamily.getLabelFamilyId()) + "," + 
/*  482 */               Integer.toString(relFamily.getReleasingFamilyId()) + "," + (
/*  483 */               relFamily.IsDefault() ? 1 : 0) + 
/*  484 */               ")";
/*      */ 
/*      */             
/*  487 */             connector.setQuery(sqlStr);
/*      */             
/*  489 */             connector.runQuery();
/*      */           } 
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  496 */       connector.getConnection().commit();
/*      */ 
/*      */       
/*  499 */       connector.getConnection().setAutoCommit(true);
/*      */ 
/*      */       
/*  502 */       connector.close();
/*      */     }
/*  504 */     catch (SQLException se) {
/*      */       
/*  506 */       System.err.println("SQLException: " + se.getMessage());
/*  507 */       System.err.println("SQLState:  " + se.getSQLState());
/*  508 */       System.err.println("Message:  " + se.getMessage());
/*      */     } finally {
/*      */       
/*  511 */       if (connector != null) {
/*  512 */         connector.close();
/*      */       }
/*      */     } 
/*      */     
/*  516 */     if (user != null) {
/*      */       
/*  518 */       user.setReleasingFamily(null);
/*  519 */       user.setReleasingFamilyLabelFamily(null);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  525 */     return true;
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
/*      */   public static Vector getUserReleasingFamiliesVectorOfFamilies(Context context) {
/*  538 */     Hashtable releasingFamiliesHash = null;
/*      */ 
/*      */     
/*  541 */     User user = (User)context.getSessionValue("user");
/*  542 */     int userId = user.getUserId();
/*      */ 
/*      */     
/*  545 */     if (user != null) {
/*  546 */       releasingFamiliesHash = user.getReleasingFamily();
/*      */     }
/*  548 */     if (releasingFamiliesHash == null) {
/*  549 */       releasingFamiliesHash = get(userId);
/*      */     }
/*      */     
/*  552 */     Hashtable familiesHash = new Hashtable();
/*      */     
/*  554 */     Vector theFamilyList = null;
/*      */     
/*  556 */     Vector vUserEnvironments = MilestoneHelper.getUserEnvironments(context);
/*      */ 
/*      */     
/*  559 */     vUserEnvironments = MilestoneHelper.removeUnusedCSO(vUserEnvironments, context, -1);
/*      */     
/*  561 */     Environment env = null;
/*  562 */     Vector theFamilies = Cache.getFamilies();
/*      */     
/*  564 */     Vector precache = new Vector();
/*  565 */     Family family = null;
/*      */     
/*  567 */     HashMap corpHashMap = MilestoneHelper.buildActiveCorporateStructureHashMap();
/*      */ 
/*      */     
/*  570 */     for (int i = 0; i < theFamilies.size(); i++) {
/*      */       
/*  572 */       family = (Family)theFamilies.elementAt(i);
/*  573 */       int structId = family.getStructureID();
/*  574 */       familiesHash.put(Integer.toString(structId), family);
/*      */     } 
/*      */ 
/*      */     
/*  578 */     Hashtable familyInVectorHash = new Hashtable();
/*      */ 
/*      */     
/*  581 */     for (int i = 0; i < theFamilies.size(); i++) {
/*      */       
/*  583 */       family = (Family)theFamilies.elementAt(i);
/*  584 */       int structId = family.getStructureID();
/*      */ 
/*      */       
/*  587 */       boolean boolVal = !corpHashMap.containsKey(new Integer(structId));
/*      */       
/*  589 */       if (family != null && boolVal)
/*      */       {
/*  591 */         for (int j = 0; j < vUserEnvironments.size(); j++) {
/*      */           
/*  593 */           env = (Environment)vUserEnvironments.elementAt(j);
/*  594 */           if (env != null) {
/*      */             
/*  596 */             Family parent = (Family)MilestoneHelper.getStructureObject(env.getParentID());
/*  597 */             if (parent != null && parent.getStructureID() == family.getStructureID()) {
/*      */ 
/*      */               
/*  600 */               Vector labelReleasingFamilies = (Vector)releasingFamiliesHash.get(Integer.toString(parent.getStructureID()));
/*  601 */               if (labelReleasingFamilies != null) {
/*      */                 
/*  603 */                 for (int x = 0; x < labelReleasingFamilies.size(); x++) {
/*      */                   
/*  605 */                   ReleasingFamily releasingFamily = (ReleasingFamily)labelReleasingFamilies.get(x);
/*  606 */                   if (releasingFamily != null) {
/*      */ 
/*      */                     
/*  609 */                     Family rFamily = (Family)familiesHash.get(Integer.toString(releasingFamily.getReleasingFamilyId()));
/*      */ 
/*      */                     
/*  612 */                     Family rFamilyClone = cloneFamilyObject(family, rFamily);
/*      */ 
/*      */                     
/*  615 */                     if (!familyInVectorHash.containsKey(Integer.toString(rFamilyClone.getStructureID())))
/*      */                     {
/*  617 */                       familyInVectorHash.put(Integer.toString(rFamily.getStructureID()), rFamilyClone);
/*      */                     }
/*      */                   } 
/*      */                 } 
/*      */                 
/*      */                 break;
/*      */               } 
/*      */               
/*  625 */               Family familyClone = new Family();
/*      */               try {
/*  627 */                 familyClone = (Family)family.clone();
/*      */               }
/*  629 */               catch (CloneNotSupportedException cloneNotSupportedException) {}
/*      */ 
/*      */ 
/*      */               
/*  633 */               if (!familyInVectorHash.containsKey(Integer.toString(familyClone.getStructureID()))) {
/*  634 */                 familyInVectorHash.put(Integer.toString(familyClone.getStructureID()), familyClone);
/*      */               }
/*      */ 
/*      */               
/*      */               break;
/*      */             } 
/*      */             
/*  641 */             env = null;
/*  642 */             parent = null;
/*      */           } 
/*      */         } 
/*      */       }
/*  646 */       family = null;
/*      */     } 
/*      */     
/*  649 */     return theFamilyList = loadFamiliesFromHash(familyInVectorHash);
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
/*      */   public static Vector getUserReleasingFamiliesVectorOfReleasingFamilies(Context context) {
/*  661 */     Hashtable releasingFamiliesHash = null;
/*      */ 
/*      */     
/*  664 */     User user = (User)context.getSessionValue("user");
/*  665 */     int userId = user.getUserId();
/*      */ 
/*      */     
/*  668 */     if (user != null) {
/*  669 */       releasingFamiliesHash = user.getReleasingFamily();
/*      */     }
/*  671 */     if (releasingFamiliesHash == null) {
/*  672 */       releasingFamiliesHash = get(userId);
/*      */     }
/*      */     
/*  675 */     Hashtable familiesHash = new Hashtable();
/*      */     
/*  677 */     Vector theFamilyList = null;
/*      */     
/*  679 */     Vector vUserEnvironments = MilestoneHelper.getUserEnvironments(context);
/*      */ 
/*      */     
/*  682 */     vUserEnvironments = MilestoneHelper.removeUnusedCSO(vUserEnvironments, context, -1);
/*      */     
/*  684 */     Environment env = null;
/*  685 */     Vector theFamilies = Cache.getFamilies();
/*      */     
/*  687 */     Vector precache = new Vector();
/*  688 */     Family family = null;
/*      */     
/*  690 */     HashMap corpHashMap = MilestoneHelper.buildActiveCorporateStructureHashMap();
/*      */ 
/*      */     
/*  693 */     for (int i = 0; i < theFamilies.size(); i++) {
/*      */       
/*  695 */       family = (Family)theFamilies.elementAt(i);
/*  696 */       int structId = family.getStructureID();
/*  697 */       familiesHash.put(Integer.toString(structId), family);
/*      */     } 
/*      */ 
/*      */     
/*  701 */     Hashtable familyInVectorHash = new Hashtable();
/*      */ 
/*      */     
/*  704 */     for (int i = 0; i < theFamilies.size(); i++) {
/*      */       
/*  706 */       family = (Family)theFamilies.elementAt(i);
/*  707 */       int structId = family.getStructureID();
/*      */ 
/*      */       
/*  710 */       boolean boolVal = !corpHashMap.containsKey(new Integer(structId));
/*      */       
/*  712 */       if (family != null && boolVal)
/*      */       {
/*  714 */         for (int j = 0; j < vUserEnvironments.size(); j++) {
/*      */           
/*  716 */           env = (Environment)vUserEnvironments.elementAt(j);
/*  717 */           if (env != null) {
/*      */             
/*  719 */             Family parent = (Family)MilestoneHelper.getStructureObject(env.getParentID());
/*  720 */             if (parent != null && parent.getStructureID() == family.getStructureID()) {
/*      */ 
/*      */               
/*  723 */               Vector labelReleasingFamilies = (Vector)releasingFamiliesHash.get(Integer.toString(parent.getStructureID()));
/*  724 */               if (labelReleasingFamilies != null) {
/*      */                 
/*  726 */                 for (int x = 0; x < labelReleasingFamilies.size(); x++) {
/*      */                   
/*  728 */                   ReleasingFamily releasingFamily = (ReleasingFamily)labelReleasingFamilies.get(x);
/*  729 */                   if (releasingFamily != null)
/*      */                   {
/*      */                     
/*  732 */                     if (!familyInVectorHash.containsKey(Integer.toString(releasingFamily.getReleasingFamilyId()))) {
/*      */                       
/*  734 */                       releasingFamily.getLabelFamilies().clear();
/*  735 */                       releasingFamily.getLabelFamilies().add(family);
/*  736 */                       familyInVectorHash.put(Integer.toString(releasingFamily.getReleasingFamilyId()), releasingFamily);
/*      */                     
/*      */                     }
/*      */                     else {
/*      */                       
/*  741 */                       ReleasingFamily relFamily = (ReleasingFamily)familyInVectorHash.get(Integer.toString(releasingFamily.getReleasingFamilyId()));
/*  742 */                       relFamily.getLabelFamilies().add(family);
/*      */                     } 
/*      */                   }
/*      */                 } 
/*      */                 
/*      */                 break;
/*      */               } 
/*      */               
/*  750 */               if (!familyInVectorHash.containsKey(Integer.toString(family.getStructureID()))) {
/*      */ 
/*      */                 
/*  753 */                 ReleasingFamily relFamily = new ReleasingFamily(family.getStructureID(), family.getStructureID(), 
/*  754 */                     true, true, family.getName(), family.getStructureAbbreviation());
/*  755 */                 relFamily.getLabelFamilies().add(family);
/*      */ 
/*      */                 
/*  758 */                 familyInVectorHash.put(Integer.toString(family.getStructureID()), relFamily);
/*      */                 
/*      */                 break;
/*      */               } 
/*      */               
/*  763 */               ReleasingFamily relFamily = (ReleasingFamily)familyInVectorHash.get(Integer.toString(family.getStructureID()));
/*  764 */               relFamily.getLabelFamilies().add(family);
/*      */ 
/*      */               
/*      */               break;
/*      */             } 
/*      */ 
/*      */             
/*  771 */             env = null;
/*  772 */             parent = null;
/*      */           } 
/*      */         } 
/*      */       }
/*  776 */       family = null;
/*      */     } 
/*      */     
/*  779 */     return theFamilyList = loadReleasingFamiliesFromHash(familyInVectorHash);
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
/*      */   public static Family cloneFamilyObject(Family family, Family rFamily) {
/*  795 */     Family rFamilyClone = new Family();
/*      */     try {
/*  797 */       rFamilyClone = (Family)family.clone();
/*  798 */       rFamilyClone.setName(rFamily.getName());
/*  799 */       rFamilyClone.setStructureID(rFamily.getStructureID());
/*      */     }
/*  801 */     catch (CloneNotSupportedException cloneNotSupportedException) {}
/*      */ 
/*      */     
/*  804 */     return rFamilyClone;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector loadFamiliesFromHash(Hashtable familyInVectorHash) {
/*  813 */     Vector returnVector = new Vector();
/*  814 */     for (Iterator i = familyInVectorHash.values().iterator(); i.hasNext(); ) {
/*      */       
/*  816 */       Family family = (Family)i.next();
/*  817 */       returnVector.add(family);
/*      */     } 
/*  819 */     return returnVector;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector loadReleasingFamiliesFromHash(Hashtable familyInVectorHash) {
/*  828 */     Vector returnVector = new Vector();
/*  829 */     for (Iterator i = familyInVectorHash.values().iterator(); i.hasNext(); ) {
/*      */       
/*  831 */       ReleasingFamily relFamily = (ReleasingFamily)i.next();
/*  832 */       returnVector.add(relFamily);
/*      */     } 
/*  834 */     return returnVector;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getReleasingFamilies(int userId, int labelFamilyId, Context context) {
/*  845 */     Hashtable releasingFamiliesHash = null;
/*  846 */     Vector labelReleasingFamilies = null;
/*      */ 
/*      */     
/*  849 */     User user = (User)context.getSessionValue("user");
/*  850 */     if (user != null) {
/*  851 */       releasingFamiliesHash = user.getReleasingFamily();
/*      */     }
/*      */     
/*  854 */     if (releasingFamiliesHash == null) {
/*  855 */       releasingFamiliesHash = get(userId);
/*      */     }
/*      */     
/*  858 */     if (releasingFamiliesHash != null && 
/*  859 */       releasingFamiliesHash.get(Integer.toString(labelFamilyId)) != null) {
/*  860 */       labelReleasingFamilies = (Vector)releasingFamiliesHash.get(Integer.toString(labelFamilyId));
/*      */ 
/*      */     
/*      */     }
/*  864 */     else if (user.getAdministrator() == 1) {
/*      */       
/*  866 */       labelReleasingFamilies = new Vector();
/*      */       
/*  868 */       Vector families = SelectionHandler.filterCSO(Cache.getFamilies());
/*      */       
/*  870 */       if (families != null) {
/*  871 */         for (int i = 0; i < families.size(); i++) {
/*  872 */           Family family = (Family)families.get(i);
/*  873 */           if (family != null) {
/*  874 */             boolean isDefault = false;
/*      */             
/*  876 */             if (family.getStructureID() == labelFamilyId)
/*  877 */               isDefault = true; 
/*  878 */             labelReleasingFamilies.add(new ReleasingFamily(family
/*  879 */                   .getStructureID(), 
/*  880 */                   family.getStructureID(), true, isDefault, family.getName(), 
/*  881 */                   family.getStructureAbbreviation()));
/*      */           }
/*      */         
/*      */         }
/*      */       
/*      */       }
/*      */     } else {
/*      */       
/*  889 */       labelReleasingFamilies = new Vector();
/*  890 */       labelReleasingFamilies.add(new ReleasingFamily(labelFamilyId, 
/*  891 */             labelFamilyId, true, true, 
/*  892 */             MilestoneHelper.getStructureName(labelFamilyId), ""));
/*      */     } 
/*      */     
/*  895 */     return labelReleasingFamilies;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ReleasingFamily getDefaultReleasingFamily(int userId, int labelFamilyId, Context context) {
/*  906 */     Hashtable releasingFamiliesHash = null;
/*      */     
/*  908 */     ReleasingFamily releasingFamily = null;
/*      */ 
/*      */     
/*  911 */     User user = (User)context.getSessionValue("user");
/*  912 */     if (user != null) {
/*  913 */       releasingFamiliesHash = user.getReleasingFamily();
/*      */     }
/*      */     
/*  916 */     if (releasingFamiliesHash == null) {
/*  917 */       releasingFamiliesHash = get(userId);
/*      */     }
/*      */     
/*  920 */     if (releasingFamiliesHash != null) {
/*      */       
/*  922 */       Vector labelReleasingFamilies = (Vector)releasingFamiliesHash.get(Integer.toString(labelFamilyId));
/*  923 */       if (labelReleasingFamilies != null)
/*      */       {
/*  925 */         for (int i = 0; i < labelReleasingFamilies.size(); i++) {
/*  926 */           releasingFamily = (ReleasingFamily)labelReleasingFamilies.get(i);
/*  927 */           if (releasingFamily != null && releasingFamily.IsDefault()) {
/*  928 */             return releasingFamily;
/*      */           }
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/*  934 */     return new ReleasingFamily(labelFamilyId, labelFamilyId, true, true, 
/*  935 */         MilestoneHelper.getStructureName(labelFamilyId), "");
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
/*      */   public static Vector getUserEnvironmentsFromFamily(ReleasingFamily releasingFamily, Context context) {
/*  948 */     HashMap corpHashMap = MilestoneHelper.buildActiveCorporateStructureHashMap();
/*      */     
/*  950 */     Vector userEnvironments = (Vector)MilestoneHelper.getUserEnvironments(context).clone();
/*  951 */     Vector result = new Vector();
/*      */     
/*  953 */     if (releasingFamily != null)
/*      */     {
/*      */       
/*  956 */       for (int f = 0; f < releasingFamily.getLabelFamilies().size(); f++) {
/*      */ 
/*      */         
/*  959 */         Family family = (Family)releasingFamily.getLabelFamilies().get(f);
/*  960 */         Vector familyEnvironments = family.getChildren();
/*  961 */         if (familyEnvironments != null)
/*      */         {
/*  963 */           for (int i = 0; i < familyEnvironments.size(); i++) {
/*  964 */             Environment familyEnvironment = (Environment)familyEnvironments.get(i);
/*      */ 
/*      */             
/*  967 */             for (int j = 0; j < userEnvironments.size(); j++) {
/*  968 */               Environment userEnvironment = (Environment)userEnvironments.get(j);
/*  969 */               int structureId = userEnvironment.getStructureID();
/*      */               
/*  971 */               boolean resultStructure = true;
/*  972 */               if (corpHashMap.containsKey(new Integer(structureId))) {
/*  973 */                 resultStructure = false;
/*      */               }
/*  975 */               if (userEnvironment.getStructureID() == familyEnvironment.getStructureID() && resultStructure) {
/*      */                 
/*  977 */                 result.add(familyEnvironment);
/*      */                 
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*  987 */     return result;
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
/*      */   public static String getJavaScriptCorporateArrayReleasingFamily(Context context) {
/* 1000 */     StringBuffer result = new StringBuffer(100);
/* 1001 */     String str = "";
/* 1002 */     String value = new String();
/*      */     
/* 1004 */     HashMap corpHashMap = MilestoneHelper.buildActiveCorporateStructureHashMap();
/*      */     
/* 1006 */     User user = (User)context.getSessionValue("user");
/*      */ 
/*      */ 
/*      */     
/* 1010 */     if (user.getReleasingFamily() == null)
/* 1011 */       user.setReleasingFamily(get(user.getUserId())); 
/* 1012 */     if (user.getReleasingFamilyLabelFamily() == null) {
/* 1013 */       user.setReleasingFamilyLabelFamily(getReleasingFamilyLabelFamily(user.getUserId()));
/*      */     }
/* 1015 */     Vector vUserCompanies = SelectionHandler.filterSelectionActiveCompanies(user.getUserId(), MilestoneHelper.getUserCompanies(context));
/* 1016 */     Vector vUserEnvironments = SelectionHandler.filterSelectionEnvironments(vUserCompanies);
/*      */ 
/*      */     
/* 1019 */     result.append("\n");
/* 1020 */     result.append("var familyArray = new Array();\n");
/* 1021 */     result.append("var environmentArray = new Array();\n");
/* 1022 */     result.append("var companyArray = new Array();\n");
/* 1023 */     int arrayIndex = 0;
/*      */ 
/*      */     
/* 1026 */     Vector vUserFamilies = getUserReleasingFamiliesVectorOfReleasingFamilies(context);
/*      */ 
/*      */     
/* 1029 */     result.append("familyArray[0] = new Array( 0, 'All'");
/* 1030 */     Hashtable relFamEnvs = new Hashtable();
/* 1031 */     for (int i = 0; i < vUserFamilies.size(); i++) {
/*      */ 
/*      */       
/* 1034 */       ReleasingFamily releasingFamily = (ReleasingFamily)vUserFamilies.elementAt(i);
/* 1035 */       Vector environments = getUserEnvironmentsFromFamily(releasingFamily, context);
/* 1036 */       if (environments != null) {
/*      */ 
/*      */         
/* 1039 */         environments = MilestoneHelper.removeUnusedCSO(environments, context, -1);
/*      */         
/* 1041 */         for (int x = 0; x < environments.size(); x++) {
/*      */ 
/*      */           
/* 1044 */           Environment environment = (Environment)environments.get(x);
/*      */           
/* 1046 */           if (!relFamEnvs.containsKey(Integer.toString(environment.getStructureID()))) {
/*      */             
/* 1048 */             result.append(", ");
/* 1049 */             result.append(environment.getStructureID());
/* 1050 */             result.append(", '");
/* 1051 */             result.append(MilestoneHelper.urlEncode(environment.getName()));
/* 1052 */             result.append('\'');
/*      */             
/* 1054 */             relFamEnvs.put(Integer.toString(environment.getStructureID()), environment);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/* 1059 */     relFamEnvs = null;
/* 1060 */     result.append(");\n");
/* 1061 */     result.append("environmentArray[0] = new Array( 0, 'All');\n");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1066 */     for (int l = 0; l < vUserFamilies.size(); l++) {
/*      */ 
/*      */       
/* 1069 */       ReleasingFamily releasingFamily = (ReleasingFamily)vUserFamilies.elementAt(l);
/* 1070 */       int structureId = releasingFamily.getReleasingFamilyId();
/*      */ 
/*      */       
/* 1073 */       boolean familyVal = true;
/* 1074 */       if (corpHashMap.containsKey(new Integer(structureId))) {
/* 1075 */         familyVal = false;
/*      */       }
/* 1077 */       if (releasingFamily != null && familyVal) {
/*      */         
/* 1079 */         result.append("familyArray[");
/* 1080 */         result.append(releasingFamily.getReleasingFamilyId());
/* 1081 */         result.append("] = new Array(");
/*      */         
/* 1083 */         boolean foundZeroth = false;
/*      */ 
/*      */         
/* 1086 */         Vector environmentVector = new Vector();
/*      */         
/* 1088 */         Vector environments = getUserEnvironmentsFromFamily(releasingFamily, context);
/*      */ 
/*      */         
/* 1091 */         Hashtable labelFamilyHash = new Hashtable();
/* 1092 */         for (int r = 0; r < releasingFamily.getLabelFamilies().size(); r++) {
/*      */ 
/*      */           
/* 1095 */           Family labelFamily = (Family)releasingFamily.getLabelFamilies().get(r);
/* 1096 */           labelFamilyHash.put(Integer.toString(labelFamily.getStructureID()), labelFamily);
/*      */         } 
/*      */ 
/*      */         
/* 1100 */         environments = MilestoneHelper.removeUnusedCSO(environments, context, releasingFamily.getReleasingFamilyId());
/*      */         
/* 1102 */         if (environments != null) {
/*      */           
/* 1104 */           result.append(" 0, 'All',");
/* 1105 */           for (int j = 0; j < environments.size(); j++) {
/*      */             
/* 1107 */             Environment environment = (Environment)environments.elementAt(j);
/* 1108 */             int structureIdc = environment.getStructureID();
/*      */             
/* 1110 */             boolean boolVal = true;
/* 1111 */             if (corpHashMap.containsKey(new Integer(structureIdc))) {
/* 1112 */               boolVal = false;
/*      */             }
/* 1114 */             if (labelFamilyHash.containsKey(Integer.toString(environment.getParentID())) && boolVal) {
/*      */               
/* 1116 */               if (foundZeroth) {
/* 1117 */                 result.append(',');
/*      */               }
/* 1119 */               result.append(' ');
/* 1120 */               result.append(environment.getStructureID());
/* 1121 */               result.append(", '");
/* 1122 */               result.append(MilestoneHelper.urlEncode(environment.getName()));
/* 1123 */               result.append('\'');
/* 1124 */               foundZeroth = true;
/* 1125 */               environmentVector.addElement(environment);
/*      */             } 
/*      */           } 
/* 1128 */           if (foundZeroth) {
/*      */             
/* 1130 */             result.append(");\n");
/*      */           }
/*      */           else {
/*      */             
/* 1134 */             result.append(" 0, 'All');\n");
/*      */           } 
/*      */           
/* 1137 */           for (int k = 0; k < environmentVector.size(); k++) {
/* 1138 */             Environment environmentNode = (Environment)environmentVector.elementAt(k);
/* 1139 */             result.append("environmentArray[");
/* 1140 */             result.append(environmentNode.getStructureID());
/* 1141 */             result.append("] = new Array(");
/* 1142 */             Vector companyVector = new Vector();
/* 1143 */             Vector companies = environmentNode.getChildren();
/*      */             
/* 1145 */             if (companies != null) {
/* 1146 */               result.append(" 0, 'All',");
/*      */               
/* 1148 */               boolean foundZeroth2 = false;
/*      */ 
/*      */               
/* 1151 */               companies = MilestoneHelper.removeUnusedCSO(companies, context, -1);
/*      */               
/* 1153 */               for (int m = 0; m < companies.size(); m++) {
/*      */                 
/* 1155 */                 Company company = (Company)companies.elementAt(m);
/* 1156 */                 int structureIdc = company.getStructureID();
/*      */ 
/*      */                 
/* 1159 */                 boolean boolVal = true;
/* 1160 */                 if (corpHashMap.containsKey(new Integer(structureIdc))) {
/* 1161 */                   boolVal = false;
/*      */                 }
/*      */                 
/* 1164 */                 if (company.getParentID() == environmentNode.getStructureID() && boolVal) {
/*      */                   
/* 1166 */                   if (foundZeroth2)
/* 1167 */                     result.append(','); 
/* 1168 */                   result.append(' ');
/* 1169 */                   result.append(company.getStructureID());
/* 1170 */                   result.append(", '");
/* 1171 */                   result.append(MilestoneHelper.urlEncode(company.getName()));
/* 1172 */                   result.append('\'');
/* 1173 */                   foundZeroth2 = true;
/* 1174 */                   companyVector.addElement(company);
/*      */                 } 
/*      */               } 
/*      */               
/* 1178 */               if (foundZeroth2) {
/*      */                 
/* 1180 */                 result.append(");\n");
/*      */               }
/*      */               else {
/*      */                 
/* 1184 */                 result.append(" 0, 'All');\n");
/*      */               } 
/*      */               
/* 1187 */               for (int n = 0; n < companyVector.size(); n++) {
/*      */                 
/* 1189 */                 Company companyNode = (Company)companyVector.elementAt(n);
/* 1190 */                 result.append("companyArray[");
/* 1191 */                 result.append(companyNode.getStructureID());
/* 1192 */                 result.append("] = new Array(");
/*      */                 
/* 1194 */                 Vector divisions = companyNode.getChildren();
/*      */                 
/* 1196 */                 boolean foundSecond2 = false;
/*      */                 
/* 1198 */                 result.append(" 0, 'All'");
/*      */                 
/* 1200 */                 MilestoneHashtable labelsHash = new MilestoneHashtable();
/*      */ 
/*      */                 
/* 1203 */                 divisions = MilestoneHelper.removeUnusedCSO(divisions, context, -1);
/*      */                 
/* 1205 */                 for (int x = 0; x < divisions.size(); x++) {
/*      */                   
/* 1207 */                   Division division = (Division)divisions.elementAt(x);
/* 1208 */                   int structureIds = division.getStructureID();
/*      */ 
/*      */ 
/*      */                   
/* 1212 */                   boolean boolRes = true;
/* 1213 */                   if (corpHashMap.containsKey(new Integer(structureIds))) {
/* 1214 */                     boolRes = false;
/*      */                   }
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1220 */                   if (division != null && boolRes) {
/*      */ 
/*      */                     
/* 1223 */                     Vector labels = division.getChildren();
/*      */ 
/*      */                     
/* 1226 */                     labels = MilestoneHelper.removeUnusedCSO(labels, context, -1);
/*      */                     
/* 1228 */                     for (int y = 0; y < labels.size(); y++) {
/*      */                       
/* 1230 */                       Label labelNode = (Label)labels.get(y);
/* 1231 */                       int structureIdl = labelNode.getStructureID();
/*      */ 
/*      */ 
/*      */                       
/* 1235 */                       boolean boolVal = true;
/* 1236 */                       if (corpHashMap.containsKey(new Integer(structureIdl))) {
/* 1237 */                         boolVal = false;
/*      */                       }
/*      */                       
/* 1240 */                       if (labelNode.getParentID() == division.getStructureID() && boolVal) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 1254 */                         String labelName = MilestoneHelper.urlEncode(labelNode.getName());
/* 1255 */                         if (!labelsHash.containsKey(labelName)) {
/*      */ 
/*      */                           
/* 1258 */                           labelsHash.put(labelName, Integer.toString(labelNode.getStructureID()));
/*      */                         
/*      */                         }
/*      */                         else {
/*      */                           
/* 1263 */                           String hashValue = (String)labelsHash.get(labelName);
/* 1264 */                           hashValue = String.valueOf(hashValue) + "," + Integer.toString(labelNode.getStructureID());
/* 1265 */                           labelsHash.put(labelName, hashValue);
/*      */                         } 
/*      */                         
/* 1268 */                         foundSecond2 = true;
/*      */                       } 
/*      */                     } 
/*      */                   } 
/*      */                 } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 1279 */                 if (foundSecond2) {
/*      */                   
/* 1281 */                   boolean firstPass = true;
/*      */                   
/* 1283 */                   String[] labelKeys = new String[labelsHash.size()];
/*      */ 
/*      */                   
/* 1286 */                   int x = 0;
/* 1287 */                   for (Enumeration e = labelsHash.keys(); e.hasMoreElements(); x++) {
/*      */                     
/* 1289 */                     String hashKey = (String)e.nextElement();
/* 1290 */                     labelKeys[x] = hashKey;
/*      */                   } 
/*      */                   
/* 1293 */                   Arrays.sort(labelKeys);
/*      */ 
/*      */                   
/* 1296 */                   for (int i = 0; i < labelKeys.length; i++) {
/*      */                     
/* 1298 */                     String hashValue = (String)labelsHash.get(labelKeys[i]);
/*      */                     
/* 1300 */                     if (firstPass) {
/* 1301 */                       result.append(',');
/*      */                     }
/* 1303 */                     result.append(' ');
/* 1304 */                     result.append("'" + hashValue + "'");
/* 1305 */                     result.append(", '");
/* 1306 */                     result.append(labelKeys[i]);
/* 1307 */                     result.append('\'');
/*      */                     
/* 1309 */                     firstPass = true;
/*      */                   } 
/*      */                   
/* 1312 */                   result.append(");\n");
/*      */                 
/*      */                 }
/*      */                 else {
/*      */                   
/* 1317 */                   result.append(");\n");
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1326 */     corpHashMap = null;
/* 1327 */     return result.toString();
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
/*      */   public static String getJavaScriptCorporateArrayReleasingFamilySearch(Context context) {
/* 1343 */     StringBuffer result = new StringBuffer(100);
/* 1344 */     StringBuffer allCompanies = new StringBuffer(1000);
/* 1345 */     StringBuffer allLabels = new StringBuffer(1000);
/*      */     
/* 1347 */     String str = "";
/* 1348 */     String value = new String();
/*      */     
/* 1350 */     HashMap corpHashMap = MilestoneHelper.buildActiveCorporateStructureHashMap();
/*      */     
/* 1352 */     User user = (User)context.getSessionValue("user");
/*      */ 
/*      */ 
/*      */     
/* 1356 */     if (user.getReleasingFamily() == null)
/* 1357 */       user.setReleasingFamily(get(user.getUserId())); 
/* 1358 */     if (user.getReleasingFamilyLabelFamily() == null) {
/* 1359 */       user.setReleasingFamilyLabelFamily(getReleasingFamilyLabelFamily(user.getUserId()));
/*      */     }
/* 1361 */     Vector vUserCompanies = SelectionHandler.filterSelectionActiveCompanies(user.getUserId(), MilestoneHelper.getUserCompanies(context));
/* 1362 */     Vector vUserEnvironments = SelectionHandler.filterSelectionEnvironments(vUserCompanies);
/*      */ 
/*      */     
/* 1365 */     result.append("\n");
/* 1366 */     result.append("var familyArray = new Array();\n");
/* 1367 */     result.append("var environmentArray = new Array();\n");
/* 1368 */     result.append("var companyArray = new Array();\n");
/* 1369 */     int arrayIndex = 0;
/*      */ 
/*      */     
/* 1372 */     Vector vUserFamilies = getUserReleasingFamiliesVectorOfReleasingFamilies(context);
/*      */ 
/*      */     
/* 1375 */     result.append("familyArray[0] = new Array( 0, 'All'");
/* 1376 */     Hashtable relFamEnvs = new Hashtable();
/*      */     
/* 1378 */     Vector environments = MilestoneHelper.getUserEnvironments(context);
/* 1379 */     Vector myCompanies = MilestoneHelper.getUserCompanies(context);
/* 1380 */     environments = SelectionHandler.filterSelectionEnvironments(myCompanies);
/*      */ 
/*      */     
/* 1383 */     environments = MilestoneHelper.removeUnusedCSO(environments, context, -1);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1388 */     Vector sortedVector = new Vector();
/* 1389 */     sortedVector = MilestoneHelper.sortCorporateVectorByName(environments);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1400 */     if (sortedVector != null)
/*      */     {
/* 1402 */       for (int x = 0; x < sortedVector.size(); x++) {
/*      */ 
/*      */         
/* 1405 */         Environment environment = (Environment)sortedVector.get(x);
/*      */ 
/*      */ 
/*      */         
/* 1409 */         result.append(", ");
/* 1410 */         result.append(environment.getStructureID());
/* 1411 */         result.append(", '");
/* 1412 */         result.append(MilestoneHelper.urlEncode(environment.getName()));
/* 1413 */         result.append('\'');
/*      */         
/* 1415 */         relFamEnvs.put(Integer.toString(environment.getStructureID()), environment);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 1420 */     relFamEnvs = null;
/* 1421 */     result.append(");\n");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1428 */     for (int l = 0; l < vUserFamilies.size(); l++) {
/*      */ 
/*      */       
/* 1431 */       ReleasingFamily releasingFamily = (ReleasingFamily)vUserFamilies.elementAt(l);
/* 1432 */       int structureId = releasingFamily.getReleasingFamilyId();
/*      */ 
/*      */       
/* 1435 */       boolean familyVal = true;
/* 1436 */       if (corpHashMap.containsKey(new Integer(structureId))) {
/* 1437 */         familyVal = false;
/*      */       }
/* 1439 */       if (releasingFamily != null && familyVal) {
/*      */         
/* 1441 */         result.append("familyArray[");
/* 1442 */         result.append(releasingFamily.getReleasingFamilyId());
/* 1443 */         result.append("] = new Array(");
/*      */         
/* 1445 */         boolean foundZeroth = false;
/*      */ 
/*      */         
/* 1448 */         Vector environmentVector = new Vector();
/*      */         
/* 1450 */         environments = getUserEnvironmentsFromFamily(releasingFamily, context);
/*      */ 
/*      */         
/* 1453 */         environments = MilestoneHelper.removeUnusedCSO(environments, context, releasingFamily.getReleasingFamilyId());
/*      */ 
/*      */         
/* 1456 */         Hashtable labelFamilyHash = new Hashtable();
/* 1457 */         for (int r = 0; r < releasingFamily.getLabelFamilies().size(); r++) {
/*      */ 
/*      */           
/* 1460 */           Family labelFamily = (Family)releasingFamily.getLabelFamilies().get(r);
/* 1461 */           labelFamilyHash.put(Integer.toString(labelFamily.getStructureID()), labelFamily);
/*      */         } 
/*      */         
/* 1464 */         if (environments != null) {
/*      */           
/* 1466 */           result.append(" 0, 'All',");
/* 1467 */           for (int j = 0; j < environments.size(); j++) {
/*      */             
/* 1469 */             Environment environment = (Environment)environments.elementAt(j);
/* 1470 */             int structureIdc = environment.getStructureID();
/*      */             
/* 1472 */             boolean boolVal = true;
/* 1473 */             if (corpHashMap.containsKey(new Integer(structureIdc))) {
/* 1474 */               boolVal = false;
/*      */             }
/* 1476 */             if (labelFamilyHash.containsKey(Integer.toString(environment.getParentID())) && boolVal) {
/*      */               
/* 1478 */               if (foundZeroth) {
/* 1479 */                 result.append(',');
/*      */               }
/* 1481 */               result.append(' ');
/* 1482 */               result.append(environment.getStructureID());
/* 1483 */               result.append(", '");
/* 1484 */               result.append(MilestoneHelper.urlEncode(environment.getName()));
/* 1485 */               result.append('\'');
/*      */               
/* 1487 */               foundZeroth = true;
/* 1488 */               environmentVector.addElement(environment);
/*      */             } 
/*      */           } 
/* 1491 */           if (foundZeroth) {
/*      */             
/* 1493 */             result.append(");\n");
/*      */           }
/*      */           else {
/*      */             
/* 1497 */             result.append(" 0, 'All');\n");
/*      */           } 
/*      */           
/* 1500 */           for (int k = 0; k < environmentVector.size(); k++) {
/* 1501 */             Environment environmentNode = (Environment)environmentVector.elementAt(k);
/* 1502 */             result.append("environmentArray[");
/* 1503 */             result.append(environmentNode.getStructureID());
/* 1504 */             result.append("] = new Array(");
/* 1505 */             Vector companyVector = new Vector();
/* 1506 */             Vector companies = environmentNode.getChildren();
/*      */ 
/*      */             
/* 1509 */             companies = MilestoneHelper.removeUnusedCSO(companies, context, -1);
/*      */             
/* 1511 */             if (companies != null) {
/* 1512 */               result.append(" 0, 'All',");
/*      */               
/* 1514 */               boolean foundZeroth2 = false;
/*      */               
/* 1516 */               for (int m = 0; m < companies.size(); m++) {
/*      */                 
/* 1518 */                 Company company = (Company)companies.elementAt(m);
/* 1519 */                 int structureIdc = company.getStructureID();
/*      */ 
/*      */                 
/* 1522 */                 boolean boolVal = true;
/* 1523 */                 if (corpHashMap.containsKey(new Integer(structureIdc))) {
/* 1524 */                   boolVal = false;
/*      */                 }
/*      */                 
/* 1527 */                 if (company.getParentID() == environmentNode.getStructureID() && boolVal) {
/*      */                   
/* 1529 */                   if (foundZeroth2)
/* 1530 */                     result.append(','); 
/* 1531 */                   result.append(' ');
/* 1532 */                   result.append(company.getStructureID());
/* 1533 */                   result.append(", '");
/* 1534 */                   result.append(MilestoneHelper.urlEncode(company.getName()));
/* 1535 */                   result.append('\'');
/* 1536 */                   foundZeroth2 = true;
/* 1537 */                   companyVector.addElement(company);
/*      */                 } 
/*      */               } 
/*      */ 
/*      */               
/* 1542 */               if (foundZeroth2) {
/*      */                 
/* 1544 */                 result.append(");\n");
/*      */               }
/*      */               else {
/*      */                 
/* 1548 */                 result.append(" 0, 'All');\n");
/*      */               } 
/*      */               
/* 1551 */               for (int n = 0; n < companyVector.size(); n++) {
/*      */                 
/* 1553 */                 Company companyNode = (Company)companyVector.elementAt(n);
/* 1554 */                 result.append("companyArray[");
/* 1555 */                 result.append(companyNode.getStructureID());
/* 1556 */                 result.append("] = new Array(");
/*      */                 
/* 1558 */                 Vector divisions = companyNode.getChildren();
/*      */ 
/*      */                 
/* 1561 */                 divisions = MilestoneHelper.removeUnusedCSO(divisions, context, -1);
/*      */                 
/* 1563 */                 boolean foundSecond2 = false;
/*      */                 
/* 1565 */                 result.append(" 0, 'All'");
/*      */                 
/* 1567 */                 MilestoneHashtable labelsHash = new MilestoneHashtable();
/*      */                 
/* 1569 */                 for (int x = 0; x < divisions.size(); x++) {
/*      */                   
/* 1571 */                   Division division = (Division)divisions.elementAt(x);
/* 1572 */                   int structureIds = division.getStructureID();
/*      */ 
/*      */ 
/*      */                   
/* 1576 */                   boolean boolRes = true;
/* 1577 */                   if (corpHashMap.containsKey(new Integer(structureIds))) {
/* 1578 */                     boolRes = false;
/*      */                   }
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1584 */                   if (division != null && boolRes) {
/*      */ 
/*      */                     
/* 1587 */                     Vector labels = division.getChildren();
/*      */ 
/*      */                     
/* 1590 */                     labels = MilestoneHelper.removeUnusedCSO(labels, context, -1);
/*      */                     
/* 1592 */                     for (int y = 0; y < labels.size(); y++) {
/*      */                       
/* 1594 */                       Label labelNode = (Label)labels.get(y);
/* 1595 */                       int structureIdl = labelNode.getStructureID();
/*      */ 
/*      */ 
/*      */                       
/* 1599 */                       boolean boolVal = true;
/* 1600 */                       if (corpHashMap.containsKey(new Integer(structureIdl))) {
/* 1601 */                         boolVal = false;
/*      */                       }
/*      */                       
/* 1604 */                       if (labelNode.getParentID() == division.getStructureID() && boolVal) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 1618 */                         String labelName = MilestoneHelper.urlEncode(labelNode.getName());
/* 1619 */                         if (!labelsHash.containsKey(labelName)) {
/*      */ 
/*      */                           
/* 1622 */                           labelsHash.put(labelName, Integer.toString(labelNode.getStructureID()));
/*      */                         
/*      */                         }
/*      */                         else {
/*      */                           
/* 1627 */                           String hashValue = (String)labelsHash.get(labelName);
/* 1628 */                           hashValue = String.valueOf(hashValue) + "," + Integer.toString(labelNode.getStructureID());
/* 1629 */                           labelsHash.put(labelName, hashValue);
/*      */                         } 
/*      */                         
/* 1632 */                         foundSecond2 = true;
/*      */                       } 
/*      */                     } 
/*      */                   } 
/*      */                 } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 1643 */                 if (foundSecond2) {
/*      */ 
/*      */                   
/* 1646 */                   boolean firstPass = true;
/*      */                   
/* 1648 */                   String[] labelKeys = new String[labelsHash.size()];
/*      */ 
/*      */                   
/* 1651 */                   int x = 0;
/* 1652 */                   for (Enumeration e = labelsHash.keys(); e.hasMoreElements(); x++) {
/*      */                     
/* 1654 */                     String hashKey = (String)e.nextElement();
/* 1655 */                     labelKeys[x] = hashKey;
/*      */                   } 
/*      */                   
/* 1658 */                   Arrays.sort(labelKeys);
/*      */ 
/*      */                   
/* 1661 */                   for (int i = 0; i < labelKeys.length; i++) {
/*      */                     
/* 1663 */                     String hashValue = (String)labelsHash.get(labelKeys[i]);
/*      */                     
/* 1665 */                     if (firstPass) {
/* 1666 */                       result.append(',');
/*      */                     }
/* 1668 */                     result.append(' ');
/* 1669 */                     result.append("'" + hashValue + "'");
/* 1670 */                     result.append(", '");
/* 1671 */                     result.append(labelKeys[i]);
/* 1672 */                     result.append('\'');
/*      */                     
/* 1674 */                     allLabels.append(',');
/* 1675 */                     allLabels.append("'" + hashValue + "'");
/* 1676 */                     allLabels.append(", '");
/* 1677 */                     allLabels.append(labelKeys[i]);
/* 1678 */                     allLabels.append('\'');
/*      */                     
/* 1680 */                     firstPass = true;
/*      */                   } 
/*      */                   
/* 1683 */                   result.append(");\n");
/*      */                 
/*      */                 }
/*      */                 else {
/*      */                   
/* 1688 */                   result.append(");\n");
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1697 */     Vector searchCompanies = null;
/*      */ 
/*      */ 
/*      */     
/* 1701 */     searchCompanies = MilestoneHelper.getUserCompanies(context);
/*      */ 
/*      */     
/* 1704 */     searchCompanies = MilestoneHelper.removeUnusedCSO(searchCompanies, context, -1);
/*      */     
/* 1706 */     sortedVector = MilestoneHelper.sortCorporateVectorByName(searchCompanies);
/*      */     
/* 1708 */     result.append("environmentArray[0] = new Array( 0, 'All'");
/*      */     
/* 1710 */     for (int c = 0; c < sortedVector.size(); c++) {
/*      */       
/* 1712 */       Company comp = (Company)sortedVector.get(c);
/* 1713 */       result.append(',');
/* 1714 */       result.append(comp.getStructureID());
/* 1715 */       result.append(", '");
/* 1716 */       result.append(MilestoneHelper.urlEncode(comp.getName()));
/* 1717 */       result.append('\'');
/*      */     } 
/* 1719 */     result.append(");\n");
/*      */     
/* 1721 */     Vector searchLabels = MilestoneHelper.getUserLabels(sortedVector);
/*      */ 
/*      */     
/* 1724 */     searchLabels = MilestoneHelper.removeUnusedCSO(searchLabels, context, -1);
/* 1725 */     sortedVector = MilestoneHelper.sortCorporateVectorByName(searchLabels);
/*      */ 
/*      */     
/* 1728 */     MilestoneHashtable labelsHash = new MilestoneHashtable();
/* 1729 */     for (int c = 0; c < sortedVector.size(); c++) {
/*      */       
/* 1731 */       Label label = (Label)sortedVector.get(c);
/*      */       
/* 1733 */       String labelName = MilestoneHelper.urlEncode(label.getName());
/* 1734 */       if (!labelsHash.containsKey(labelName)) {
/*      */         
/* 1736 */         labelsHash.put(labelName, Integer.toString(label.getStructureID()));
/*      */       }
/*      */       else {
/*      */         
/* 1740 */         String hashValue = (String)labelsHash.get(labelName);
/* 1741 */         hashValue = String.valueOf(hashValue) + "," + Integer.toString(label.getStructureID());
/* 1742 */         labelsHash.put(labelName, hashValue);
/*      */       } 
/*      */     } 
/*      */     
/* 1746 */     String[] labelKeys = new String[labelsHash.size()];
/*      */ 
/*      */     
/* 1749 */     int x = 0;
/* 1750 */     for (Enumeration e = labelsHash.keys(); e.hasMoreElements(); x++) {
/* 1751 */       String hashKey = (String)e.nextElement();
/* 1752 */       labelKeys[x] = hashKey;
/*      */     } 
/*      */     
/* 1755 */     Arrays.sort(labelKeys);
/*      */     
/* 1757 */     result.append("companyArray[0] = new Array( 0, 'All'");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1770 */     for (int i = 0; i < labelKeys.length; i++) {
/*      */       
/* 1772 */       String hashValue = (String)labelsHash.get(labelKeys[i]);
/* 1773 */       result.append(',');
/* 1774 */       result.append("'" + hashValue + "'");
/* 1775 */       result.append(", '");
/* 1776 */       result.append(labelKeys[i]);
/* 1777 */       result.append('\'');
/*      */     } 
/*      */     
/* 1780 */     result.append(");\n");
/*      */     
/* 1782 */     corpHashMap = null;
/* 1783 */     return result.toString();
/*      */   }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\ReleasingFamily.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */