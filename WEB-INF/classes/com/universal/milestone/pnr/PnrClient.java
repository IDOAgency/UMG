/*    */ package WEB-INF.classes.com.universal.milestone.pnr;
/*    */ 
/*    */ import com.universal.milestone.pnr.PnrHome;
/*    */ import com.universal.milestone.pnr.PnrRemote;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Properties;
/*    */ import javax.naming.Context;
/*    */ import javax.naming.InitialContext;
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
/*    */ 
/*    */ public class PnrClient
/*    */ {
/*    */   public static void main(String[] arg) throws Exception {
/* 36 */     PnrHome home = null;
/* 37 */     PnrRemote pnr = null;
/* 38 */     System.out.println("Hello from PnrClient.");
/*    */     try {
/* 40 */       Properties h = new Properties();
/* 41 */       h.put("java.naming.factory.initial", 
/* 42 */           "weblogic.jndi.WLInitialContextFactory");
/* 43 */       h.put("java.naming.provider.url", "t3://localhost:7001");
/* 44 */       Context initial = new InitialContext(h);
/*    */       
/* 46 */       home = (PnrHome)initial.lookup("PnrBean");
/*    */       
/* 48 */       if (home == null) {
/* 49 */         System.out.println("NULL PnrHome.");
/*    */       } else {
/* 51 */         System.out.println("PnrHome not null.");
/* 52 */         pnr = home.create();
/* 53 */         System.out.println("PnrBean Created.");
/* 54 */         SimpleDateFormat df = new SimpleDateFormat("m/d/yy");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 64 */         String strReply = pnr.getPnr(arg[0], arg[1], arg[2], arg[3], 
/* 65 */             arg[4], arg[5], arg[6], arg[7], df.parse(arg[8]), 
/* 66 */             arg[9], arg[10], arg[11], arg[12]);
/*    */         
/* 68 */         System.out.println("PnrBean getPnr: " + strReply);
/*    */       } 
/* 70 */     } catch (Exception e) {
/* 71 */       System.out.println(e.toString());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\pnr\PnrClient.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */