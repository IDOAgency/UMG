/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.universal.milestone.JdbcConnector;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.SessionUser;
/*     */ import javax.servlet.http.HttpSessionBindingEvent;
/*     */ import javax.servlet.http.HttpSessionBindingListener;
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
/*     */ public class SessionUser
/*     */   implements HttpSessionBindingListener
/*     */ {
/*     */   String userName;
/*     */   String sessionId;
/*     */   int userId;
/*     */   String clientIP;
/*     */   int recordPK;
/*     */   String serverName;
/*     */   public static final String UserSessionValue = "UserSession";
/*     */   
/*     */   public SessionUser(String userName, String sessionId, int userId, String clientIP, String serverName) {
/*  42 */     this.userName = null;
/*  43 */     this.sessionId = null;
/*     */     
/*  45 */     this.clientIP = null;
/*     */     
/*  47 */     this.serverName = null;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  52 */     this.userName = userName;
/*  53 */     this.sessionId = sessionId;
/*  54 */     this.userId = userId;
/*  55 */     this.clientIP = clientIP;
/*  56 */     this.recordPK = -1;
/*  57 */     this.serverName = serverName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void valueBound(HttpSessionBindingEvent event) {
/*  66 */     System.out.println("<<< User Logged In " + this.userName);
/*  67 */     System.out.println("<<< User Session Id " + this.sessionId);
/*     */ 
/*     */     
/*  70 */     String query = "sp_sav_User_Session " + 
/*  71 */       this.recordPK + "," + 
/*  72 */       "'" + this.userName + "'," + 
/*  73 */       "'" + this.sessionId + "'," + 
/*  74 */       this.userId + "," + 
/*  75 */       "'" + this.clientIP + "'," + 
/*  76 */       "'" + this.serverName + "'";
/*     */     
/*  78 */     System.out.println("Query >>>>>>>>>>>>>> : " + query);
/*     */     
/*     */     try {
/*  81 */       JdbcConnector connector = MilestoneHelper.getConnector(query);
/*  82 */       connector.runQuery();
/*  83 */       this.recordPK = connector.getIntegerField("ReturnId");
/*  84 */       System.out.println("<<< user session active " + this.recordPK);
/*  85 */       connector.close();
/*  86 */     } catch (Exception ex) {
/*  87 */       System.out.println("<<< sql error  " + ex.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void valueUnbound(HttpSessionBindingEvent event) {
/*  97 */     System.out.println("<<< Session Unbound " + this.recordPK);
/*     */ 
/*     */     
/* 100 */     String query = "sp_sav_User_Session " + 
/* 101 */       this.recordPK + "," + 
/* 102 */       "'" + this.userName + "'," + 
/* 103 */       "'" + this.sessionId + "'," + 
/* 104 */       this.userId + "," + 
/* 105 */       "'" + this.clientIP + "'," + 
/* 106 */       "'" + this.serverName + "'";
/*     */ 
/*     */     
/* 109 */     System.out.println("Query >>>>>>>>>>>>>> : " + query);
/*     */     
/*     */     try {
/* 112 */       JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 113 */       connector.runQuery();
/* 114 */       System.out.println("<<< user session inactive ");
/* 115 */       connector.close();
/* 116 */     } catch (Exception ex) {
/* 117 */       System.out.println("<<< sql error  " + ex.getMessage());
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
/*     */   
/*     */   public void setLastActiveDate(String command) {
/* 130 */     String query = "sp_sav_User_Session_Last_Active_Date " + 
/* 131 */       this.recordPK + ",'" + command + "'";
/*     */     
/*     */     try {
/* 134 */       JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 135 */       connector.runUpdateQuery();
/* 136 */       connector.close();
/* 137 */     } catch (Exception ex) {
/* 138 */       System.out.println("<<< sql error  " + ex.getMessage());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\SessionUser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */