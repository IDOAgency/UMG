/*    */ package WEB-INF.classes.com.universal.milestone.jms;
/*    */ 
/*    */ import com.universal.milestone.Bom;
/*    */ import com.universal.milestone.Pfm;
/*    */ import com.universal.milestone.Selection;
/*    */ import com.universal.milestone.jms.MessageObject;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class MessageObject
/*    */   implements Serializable
/*    */ {
/* 12 */   public int selectionId = -1;
/* 13 */   public String emailCC = "";
/* 14 */   public String emailBody = "";
/* 15 */   public String emailSubject = "";
/* 16 */   public String emailTo = "";
/* 17 */   public String emailFrom = "";
/* 18 */   public String emailAttach = "";
/* 19 */   public Pfm pfmObj = null;
/* 20 */   public Bom bomObj = null;
/* 21 */   public Selection selectionObj = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean IsPushOnDemand = false;
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean IsPushPFM = false;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 35 */   public void setEmailBody(String emailBody) { this.emailBody = emailBody; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 43 */   public String getEmailBody() { return this.emailBody; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 51 */   public void setEmailSubject(String emailSubject) { this.emailSubject = emailSubject; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 59 */   public String getEmailSubject() { return this.emailSubject; }
/*    */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\jms\MessageObject.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */