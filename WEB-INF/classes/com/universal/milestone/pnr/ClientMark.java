/*    */ package WEB-INF.classes.com.universal.milestone.pnr;
/*    */ 
/*    */ import com.softwareag.entirex.aci.Broker;
/*    */ import com.softwareag.entirex.aci.BrokerMessage;
/*    */ import com.softwareag.entirex.aci.BrokerService;
/*    */ import com.softwareag.entirex.aci.Conversation;
/*    */ import com.universal.milestone.pnr.ClientMark;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClientMark
/*    */ {
/* 33 */   static String sServer = "ACLASS/ASERVER/ASERVICE";
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void main(String[] argv) {
/*    */     try {
/* 41 */       String brokerID = "10.130.109.122:18023";
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 48 */       Broker broker = new Broker(brokerID, "JavaUser");
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 53 */       BrokerService bService = new BrokerService(broker, sServer);
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 58 */       BrokerMessage bRequest = new BrokerMessage();
/*    */ 
/*    */ 
/*    */       
/* 62 */       bService.setMaxReceiveLen(156);
/*    */       
/* 64 */       bService.setDefaultWaittime("10s");
/*    */ 
/*    */ 
/*    */       
/* 68 */       broker.logon();
/*    */ 
/*    */ 
/*    */       
/* 72 */       System.out.println("Client started: " + broker.getConnInfo());
/*    */ 
/*    */ 
/*    */       
/* 76 */       Conversation conv = new Conversation(bService);
/*    */       
/* 78 */       bRequest.setMessage("s1dv".toUpperCase());
/* 79 */       BrokerMessage bReply = conv.sendReceive(bRequest);
/* 80 */       System.out.println("Returned: " + bReply);
/*    */       
/* 82 */       conv.end();
/*    */ 
/*    */ 
/*    */       
/* 86 */       broker.logoff();
/*    */ 
/*    */ 
/*    */     
/*    */     }
/* 91 */     catch (Exception bE) {
/* 92 */       System.out.println(bE);
/* 93 */       System.out.println(String.valueOf(bE.toString()) + " " + bE.toString().length());
/* 94 */       System.out.println(bE.getMessage());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\pnr\ClientMark.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */