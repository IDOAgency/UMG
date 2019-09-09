/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.EnhancedProperties;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.CorporateStructureManager;
/*     */ import com.universal.milestone.CorporateStructureObject;
/*     */ import com.universal.milestone.JdbcConnector;
/*     */ import com.universal.milestone.MilestoneConstants;
/*     */ import com.universal.milestone.MilestoneHelper;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CorporateStructureManager
/*     */   implements MilestoneConstants
/*     */ {
/*     */   public static final String COMPONENT_CODE = "mCso";
/*  44 */   protected static CorporateStructureManager corporateStructureManager = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static ComponentLog log;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getLog("mCso"); }
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static CorporateStructureManager getInstance() {
/*  74 */     if (corporateStructureManager == null)
/*     */     {
/*  76 */       corporateStructureManager = new CorporateStructureManager();
/*     */     }
/*  78 */     return corporateStructureManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String delete(CorporateStructureObject corporateStructureObject, int userid) {
/*  86 */     String errorMsg = null;
/*     */     
/*  88 */     int structureId = corporateStructureObject.getStructureID();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  93 */     if (corporateStructureObject.getChildren() == null || (
/*  94 */       corporateStructureObject.getChildren() != null && 
/*  95 */       corporateStructureObject.getChildren().size() < 1)) {
/*     */       String query, query, query;
/*  97 */       boolean error = false;
/*     */       
/*  99 */       JdbcConnector connector = null;
/*     */ 
/*     */       
/* 102 */       switch (corporateStructureObject.getStructureType()) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 1:
/* 109 */           query = "Select * FROM vi_Template_Header WHERE Owner = " + structureId;
/* 110 */           connector = MilestoneHelper.getConnector(query);
/* 111 */           connector.setForwardOnly(false);
/* 112 */           connector.runQuery();
/*     */           
/* 114 */           if (connector.getRowCount() > 0) {
/* 115 */             error = true;
/*     */           }
/* 117 */           query = "Select * FROM vi_Task WHERE Owner = " + structureId;
/* 118 */           connector.runQuery();
/*     */           
/* 120 */           if (connector.getRowCount() > 0) {
/* 121 */             error = true;
/*     */           }
/* 123 */           connector.close();
/*     */           
/* 125 */           if (error) {
/* 126 */             errorMsg = "This family cannot be deleted because it is used in a Template or Task.";
/*     */           }
/*     */           break;
/*     */ 
/*     */         
/*     */         case 4:
/* 132 */           query = "Select * FROM vi_Template_Header WHERE Owner = " + structureId;
/* 133 */           connector = MilestoneHelper.getConnector(query);
/* 134 */           connector.setForwardOnly(false);
/* 135 */           connector.runQuery();
/*     */           
/* 137 */           if (connector.getRowCount() > 0) {
/*     */             
/* 139 */             error = true;
/* 140 */             errorMsg = "This label cannot be deleted because it is being used in a Release.";
/*     */           } 
/* 142 */           connector.close();
/*     */           
/* 144 */           query = "Select * FROM vi_Release_Header WHERE label_id = " + structureId;
/* 145 */           connector = MilestoneHelper.getConnector(query);
/* 146 */           connector.setForwardOnly(false);
/* 147 */           connector.runQuery();
/*     */           
/* 149 */           if (connector.getRowCount() > 0) {
/*     */             
/* 151 */             error = true;
/* 152 */             errorMsg = "This label cannot be deleted because it is being used in a Release.";
/*     */           } 
/*     */           
/* 155 */           connector.close();
/*     */           break;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 2:
/* 162 */           query = "Select * FROM vi_user_company WHERE company_id = " + structureId;
/* 163 */           connector = MilestoneHelper.getConnector(query);
/* 164 */           connector.setForwardOnly(false);
/* 165 */           connector.runQuery();
/* 166 */           if (connector.getRowCount() > 0) {
/*     */             
/* 168 */             error = true;
/* 169 */             errorMsg = "This company cannot be deleted because there are users assigned to it.";
/*     */           } 
/*     */           
/* 172 */           connector.close();
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 182 */       if (!error)
/*     */       {
/* 184 */         if (!deleteFromDatabase(structureId)) {
/* 185 */           errorMsg = "DB Exception, Could not delete the record. \nPlease try it again later";
/*     */         
/*     */         }
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 192 */       errorMsg = "This cannot be deleted because it has children.";
/*     */     } 
/*     */ 
/*     */     
/* 196 */     return errorMsg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean deleteFromDatabase(int structureId) {
/*     */     try {
/* 206 */       String query = "sp_del_Structure " + 
/* 207 */         structureId;
/*     */ 
/*     */       
/* 210 */       JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 211 */       connector.runQuery();
/* 212 */       connector.close();
/*     */       
/* 214 */       return true;
/*     */     }
/* 216 */     catch (Exception e) {
/*     */       
/* 218 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDuplicate(String name, int type, int id) {
/* 230 */     boolean isDuplicate = false;
/*     */ 
/*     */     
/* 233 */     if (type == 1) {
/*     */       
/* 235 */       String query = "SELECT * from vi_structure where  name = '" + 
/* 236 */         MilestoneHelper.escapeSingleQuotes(name) + "' " + 
/* 237 */         " and type = " + type + 
/* 238 */         " and structure_id <> " + id;
/*     */       
/* 240 */       JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 241 */       connector.runQuery();
/*     */       
/* 243 */       if (connector.more()) {
/* 244 */         isDuplicate = true;
/*     */       }
/* 246 */       connector.close();
/*     */     } 
/*     */ 
/*     */     
/* 250 */     return isDuplicate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isArchiStructure(String name, int type, int id) {
/* 260 */     boolean isArchiStructure = false;
/*     */     
/* 262 */     String query = "SELECT 'x' from dbo.ArchimedesLabels where  ArchimedesId is not null and (ms_familyId = " + 
/*     */       
/* 264 */       id + 
/* 265 */       " or ms_environmentId = " + id + 
/* 266 */       " or ms_companyId = " + id + 
/* 267 */       " or ms_divisionId = " + id + 
/* 268 */       " or ms_labelId = " + id + ")";
/*     */     
/* 270 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 271 */     connector.runQuery();
/*     */     
/* 273 */     if (connector.more()) {
/* 274 */       isArchiStructure = true;
/*     */     }
/* 276 */     connector.close();
/*     */ 
/*     */     
/* 279 */     return isArchiStructure;
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\CorporateStructureManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */