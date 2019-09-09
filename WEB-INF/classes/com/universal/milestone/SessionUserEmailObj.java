/*    */ package WEB-INF.classes.com.universal.milestone;
/*    */ 
/*    */ import com.universal.milestone.SessionUserEmailObj;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SessionUserEmailObj
/*    */ {
/*    */   String emailAddr;
/*    */   boolean sendEmail;
/*    */   
/*    */   public SessionUserEmailObj(String emailAddr, boolean sendEmail) {
/* 27 */     this.emailAddr = null;
/* 28 */     this.sendEmail = false;
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 33 */     this.emailAddr = emailAddr;
/* 34 */     this.sendEmail = sendEmail;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 39 */   public void setEmailAddr(String emailAddr) { this.emailAddr = emailAddr; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 44 */   public String getEmailAddr() { return this.emailAddr; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 49 */   public void setSendEmail(boolean sendEmail) { this.sendEmail = sendEmail; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 54 */   public boolean getSendEmail() { return this.sendEmail; }
/*    */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\SessionUserEmailObj.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */