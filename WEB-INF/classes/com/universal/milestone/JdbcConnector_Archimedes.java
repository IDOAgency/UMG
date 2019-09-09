/*    */ package WEB-INF.classes.com.universal.milestone;
/*    */ 
/*    */ import com.universal.milestone.JdbcConnector;
/*    */ import com.universal.milestone.JdbcConnector_Archimedes;
/*    */ import java.util.Hashtable;
/*    */ import javax.naming.InitialContext;
/*    */ import javax.sql.DataSource;
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
/*    */ public class JdbcConnector_Archimedes
/*    */   extends JdbcConnector
/*    */ {
/* 22 */   public static DataSource archieDatasource = null;
/*    */   
/*    */   public JdbcConnector_Archimedes(String query) {
/* 25 */     this.query = query;
/*    */     
/* 27 */     setForwardOnly(true);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void getDataSourceConnnection() {
/*    */     try {
/* 34 */       if (this.connection == null) {
/*    */         
/* 36 */         if (archieDatasource == null) {
/*    */           
/* 38 */           System.out.println("<<< null data source ");
/* 39 */           Hashtable env = new Hashtable();
/* 40 */           env.put("java.naming.factory.initial", INITIAL_CONTEXT_FACTORY);
/* 41 */           env.put("java.naming.provider.url", PROVIDER_URL);
/* 42 */           InitialContext ctx = new InitialContext(env);
/* 43 */           archieDatasource = (DataSource)ctx.lookup("Archimedes");
/*    */         } 
/* 45 */         this.connection = archieDatasource.getConnection();
/*    */       } 
/* 47 */     } catch (Exception sqlexc) {
/* 48 */       System.err.println("Exception: " + sqlexc.getMessage());
/* 49 */       sqlexc.printStackTrace();
/* 50 */       debugFile("<status code=\"Exception\">" + sqlexc.getMessage() + "</status>", 3);
/* 51 */       debugFile("<DATASOURCE ERROR/entries>", 3);
/* 52 */       this.connection = null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\JdbcConnector_Archimedes.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */