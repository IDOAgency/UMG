/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.BasicHelper;
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.EnhancedProperties;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.MilestoneConstants;
/*     */ import com.universal.milestone.MilestoneMessage;
/*     */ import java.util.Vector;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MilestoneMessage
/*     */   extends BasicHelper
/*     */   implements MilestoneConstants
/*     */ {
/*     */   public static final String COMPONENT_CODE = "mmsg";
/*  48 */   protected static Vector bomSuppliers = null;
/*  49 */   protected static String databaseName = "Milestone2";
/*  50 */   protected static String dbLoginName = "";
/*  51 */   protected static String dbLoginPass = "";
/*  52 */   protected static String urlPrefix = "jdbc:odbc:";
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
/*     */   public static void configure(EnhancedProperties props, GeminiApplication application) {
/*  65 */     databaseName = props.getProperty("DatabaseName", databaseName);
/*  66 */     dbLoginName = props.getProperty("DatabaseLogin", dbLoginName);
/*  67 */     dbLoginPass = props.getProperty("DatabasePassword", dbLoginPass);
/*  68 */     urlPrefix = props.getProperty("JDBCURLPrefix", urlPrefix);
/*     */ 
/*     */     
/*  71 */     log = application.getLog("mmsg");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  76 */   public static String getMessage(int pId) { return getMessage(pId, new String[0]); }
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
/*  91 */   public static String getMessage(int pId, String pParameter) { return getMessage(pId, new String[] { pParameter }); }
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
/*     */   public static String getMessage(int pId, String[] pParameters) {
/* 108 */     if (pId < 0 || pId >= ssMessages.length) {
/* 109 */       return getMessage(0, new String[] { pId });
/*     */     }
/*     */     
/* 112 */     String lsMessage = ssMessages[pId];
/*     */     
/* 114 */     for (int i = 0; i < pParameters.length; i++) {
/*     */       
/* 116 */       if (pParameters[i] == null) {
/* 117 */         pParameters[i] = "";
/*     */       }
/*     */       
/* 120 */       String lsPlaceholder = "~%" + i + "~";
/* 121 */       int liIndex = -1;
/* 122 */       while ((liIndex = lsMessage.indexOf(lsPlaceholder)) >= 0) {
/* 123 */         lsMessage = String.valueOf(lsMessage.substring(0, liIndex)) + pParameters[i] + ((lsMessage.length() > liIndex + lsPlaceholder.length() + 1) ? 
/* 124 */           lsMessage.substring(liIndex + lsPlaceholder.length()) : 
/* 125 */           "");
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 130 */     int liIndex = lsMessage.indexOf("~%");
/* 131 */     while (liIndex >= 0) {
/* 132 */       int liEndIndex = lsMessage.indexOf('~', liIndex + 1);
/* 133 */       if (liEndIndex >= 0 && liEndIndex + 1 < lsMessage.length()) {
/* 134 */         lsMessage = String.valueOf(lsMessage.substring(0, liIndex)) + "''" + 
/* 135 */           lsMessage.substring(liEndIndex + 1);
/*     */       } else {
/* 137 */         lsMessage = String.valueOf(lsMessage.substring(0, liIndex)) + "''";
/*     */       } 
/* 139 */       liIndex = lsMessage.indexOf("~%");
/*     */     } 
/* 141 */     return lsMessage;
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\MilestoneMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */