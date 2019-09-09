/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.gemini.Context;
/*     */ import com.universal.milestone.Cache;
/*     */ import com.universal.milestone.EmailDistributionReleasingFamily;
/*     */ import com.universal.milestone.Family;
/*     */ import com.universal.milestone.JdbcConnector;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EmailDistributionReleasingFamily
/*     */ {
/*     */   public static final String CMD_RELEASING_FAMILY = "-save-releasing-family";
/*     */   public static final String CMD_SAVE_RELEASING_FAMILY = "email-distribution-save-releasing-family";
/*  23 */   public static ComponentLog log = null;
/*     */   
/*     */   int labelFamilyId;
/*     */   
/*     */   int releaseFamilyId;
/*     */   
/*     */   public EmailDistributionReleasingFamily(int labelFamilyId, int releaseFamilyId, boolean isChecked, String familyName, String familyAbbr) {
/*  30 */     this.isChecked = false;
/*  31 */     this.familyName = "";
/*  32 */     this.familyAbbr = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  40 */     this.labelFamilyId = labelFamilyId;
/*  41 */     this.releaseFamilyId = releaseFamilyId;
/*  42 */     this.isChecked = isChecked;
/*  43 */     this.familyName = familyName;
/*  44 */     this.familyAbbr = familyAbbr;
/*     */   }
/*     */ 
/*     */   
/*     */   boolean isChecked;
/*     */   String familyName;
/*     */   String familyAbbr;
/*     */   
/*  52 */   public int getLabelFamilyId() { return this.labelFamilyId; }
/*     */ 
/*     */ 
/*     */   
/*  56 */   public int getReleasingFamilyId() { return this.releaseFamilyId; }
/*     */ 
/*     */ 
/*     */   
/*  60 */   public boolean IsChecked() { return this.isChecked; }
/*     */ 
/*     */ 
/*     */   
/*  64 */   public String getFamillyName() { return this.familyName; }
/*     */ 
/*     */ 
/*     */   
/*  68 */   public String getFamilyAbbr() { return this.familyAbbr; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Hashtable get(int distributionId) {
/*  78 */     Hashtable releasingFamilyHash = null;
/*     */ 
/*     */     
/*  81 */     String sqlStr = "SELECT rf.*, f.name as relFamilyName, f.abbreviation as abbr  FROM dbo.Email_Distribution_Release_Family rf  inner join vi_Structure f on rf.release_family_id = f.structure_id  WHERE rf.distribution_id = " + 
/*     */ 
/*     */       
/*  84 */       String.valueOf(distributionId) + 
/*  85 */       " ORDER BY f.name;";
/*     */ 
/*     */     
/*  88 */     JdbcConnector connector = MilestoneHelper.getConnector(sqlStr);
/*  89 */     connector.runQuery();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  96 */     Vector releasingFamilies = null;
/*     */     
/*  98 */     while (connector.more()) {
/*     */ 
/*     */       
/* 101 */       if (releasingFamilyHash == null) {
/* 102 */         releasingFamilyHash = new Hashtable();
/*     */       }
/* 104 */       boolean isDefault = false;
/* 105 */       int familyId = connector.getInt("family_id");
/* 106 */       int relFamilyId = connector.getInt("release_family_id");
/* 107 */       String relFamilyName = connector.getField("relFamilyName");
/* 108 */       String relFamilyAbbr = connector.getField("abbr");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 113 */       EmailDistributionReleasingFamily releasingFamily = new EmailDistributionReleasingFamily(familyId, relFamilyId, 
/* 114 */           true, relFamilyName, relFamilyAbbr);
/*     */       
/* 116 */       String familyIdStr = Integer.toString(familyId);
/* 117 */       if (releasingFamilyHash.containsKey(familyIdStr)) {
/*     */         
/* 119 */         releasingFamilies = (Vector)releasingFamilyHash.get(familyIdStr);
/* 120 */         releasingFamilies.add(releasingFamily);
/*     */       }
/*     */       else {
/*     */         
/* 124 */         releasingFamilies = new Vector();
/* 125 */         releasingFamilies.add(releasingFamily);
/* 126 */         releasingFamilyHash.put(familyIdStr, releasingFamilies);
/*     */       } 
/*     */       
/* 129 */       connector.next();
/*     */     } 
/*     */     
/* 132 */     connector.close();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 137 */     return releasingFamilyHash;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean save(Context context) {
/* 153 */     String distributionId = context.getRequestValue("RFDistributionId");
/* 154 */     String labelFamId = context.getRequestValue("RFLabelFamId");
/* 155 */     String elementPrefix = "RF";
/*     */ 
/*     */     
/* 158 */     ArrayList checkedRelFamilies = new ArrayList();
/*     */ 
/*     */     
/* 161 */     Vector families = Cache.getFamilies();
/*     */     
/* 163 */     if (families != null)
/*     */     {
/* 165 */       for (int i = 0; i < families.size(); i++) {
/*     */         
/* 167 */         Family family = (Family)families.get(i);
/* 168 */         if (family != null) {
/*     */           
/* 170 */           String formElementName = String.valueOf(elementPrefix) + Integer.toString(family.getStructureID());
/* 171 */           String checkedRelFam = context.getRequestValue(formElementName);
/* 172 */           if (checkedRelFam != null)
/*     */           {
/* 174 */             checkedRelFamilies.add(checkedRelFam);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 181 */     connector = null;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 187 */       String sqlStr = "delete from dbo.Email_Distribution_Release_Family WHERE distribution_id = " + 
/* 188 */         distributionId + " and family_id = " + labelFamId;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 193 */       connector = MilestoneHelper.getConnector(sqlStr);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 199 */       connector.getConnection().setAutoCommit(false);
/*     */ 
/*     */       
/* 202 */       connector.setQuery(sqlStr);
/*     */       
/* 204 */       connector.runQuery();
/*     */ 
/*     */       
/* 207 */       if (checkedRelFamilies != null)
/*     */       {
/* 209 */         for (int i = 0; i < checkedRelFamilies.size(); i++) {
/*     */           
/* 211 */           String relFamId = (String)checkedRelFamilies.get(i);
/* 212 */           if (relFamId != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 217 */             sqlStr = "insert into dbo.Email_Distribution_Release_Family ([distribution_id],[family_id],[release_family_id]) VALUES(" + 
/*     */ 
/*     */               
/* 220 */               distributionId + "," + 
/* 221 */               labelFamId + "," + 
/* 222 */               relFamId + ")";
/*     */ 
/*     */             
/* 225 */             connector.setQuery(sqlStr);
/* 226 */             connector.runQuery();
/*     */           } 
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 233 */       connector.getConnection().commit();
/*     */ 
/*     */       
/* 236 */       connector.getConnection().setAutoCommit(true);
/*     */ 
/*     */       
/* 239 */       connector.close();
/*     */     }
/* 241 */     catch (SQLException se) {
/*     */       
/* 243 */       System.err.println("SQLException: " + se.getMessage());
/* 244 */       System.err.println("SQLState:  " + se.getSQLState());
/* 245 */       System.err.println("Message:  " + se.getMessage());
/*     */     } finally {
/*     */       
/* 248 */       if (connector != null) {
/* 249 */         connector.close();
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 255 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\EmailDistributionReleasingFamily.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */