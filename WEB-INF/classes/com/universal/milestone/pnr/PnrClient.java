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
/*    */ 
/*    */ public class PnrClient
/*    */ {
/*    */   public static void main(String[] arg) throws Exception {
/* 37 */     PnrHome home = null;
/* 38 */     PnrRemote pnr = null;
/* 39 */     System.out.println("Hello from PnrClient.");
/*    */     try {
/* 41 */       Properties h = new Properties();
/* 42 */       h.put("java.naming.factory.initial", "weblogic.jndi.WLInitialContextFactory");
/* 43 */       h.put("java.naming.provider.url", "t3://localhost:7001");
/* 44 */       Context initial = new InitialContext(h);
/*    */       
/* 46 */       home = (PnrHome)initial.lookup("PnrBean");
/*    */       
/* 48 */       if (home == null) {
/* 49 */         System.out.println("NULL PnrHome.");
/*    */       } else {
/*    */         
/* 52 */         System.out.println("PnrHome not null.");
/* 53 */         pnr = home.create();
/* 54 */         System.out.println("PnrBean Created.");
/* 55 */         SimpleDateFormat df = new SimpleDateFormat("m/d/yy");
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
/* 73 */         String strReply = pnr.getPnr(arg[0], 
/* 74 */             arg[1], 
/* 75 */             arg[2], 
/* 76 */             arg[3], 
/* 77 */             arg[4], 
/* 78 */             arg[5], 
/* 79 */             arg[6], 
/* 80 */             arg[7], 
/* 81 */             df.parse(arg[8]), 
/* 82 */             arg[9], 
/* 83 */             arg[10], 
/* 84 */             arg[11], 
/* 85 */             arg[12]);
/*    */         
/* 87 */         System.out.println("PnrBean getPnr: " + strReply);
/*    */       }
/*    */     
/* 90 */     } catch (Exception e) {
/* 91 */       System.out.println(e.toString());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\pnr\PnrClient.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */