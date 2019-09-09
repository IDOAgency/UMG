/*    */ package WEB-INF.classes.com.universal.milestone.b2b;
/*    */ 
/*    */ import com.universal.milestone.b2b.B2bRemote;
/*    */ import com.universal.milestone.b2b.SqlXML;
/*    */ import java.util.Properties;
/*    */ import javax.naming.Context;
/*    */ import javax.naming.InitialContext;
/*    */ 
/*    */ public class B2bClient
/*    */ {
/*    */   public static void main(String[] arg) throws Exception {
/* 12 */     Properties h = new Properties();
/*    */     
/* 14 */     h.put("java.naming.factory.initial", "weblogic.soap.http.SoapInitialContextFactory");
/*    */     
/* 16 */     h.put("weblogic.soap.wsdl.interface", B2bRemote.class.getName());
/*    */     
/* 18 */     Context context = new InitialContext(h);
/*    */     
/* 20 */     SqlXML sqlxml = new SqlXML();
/* 21 */     String wsdlPath = sqlxml.WSDLPATH;
/* 22 */     B2bRemote service = (B2bRemote)context.lookup(wsdlPath);
/*    */   }
/*    */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\b2b\B2bClient.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */