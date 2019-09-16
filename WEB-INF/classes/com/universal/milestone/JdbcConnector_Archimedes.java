package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.JdbcConnector;
import com.universal.milestone.JdbcConnector_Archimedes;
import java.util.Hashtable;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class JdbcConnector_Archimedes extends JdbcConnector {
  public static DataSource archieDatasource = null;
  
  public JdbcConnector_Archimedes(String query) {
    this.query = query;
    setForwardOnly(true);
  }
  
  public void getDataSourceConnnection() {
    try {
      if (this.connection == null) {
        if (archieDatasource == null) {
          System.out.println("<<< null data source ");
          Hashtable env = new Hashtable();
          env.put("java.naming.factory.initial", INITIAL_CONTEXT_FACTORY);
          env.put("java.naming.provider.url", PROVIDER_URL);
          InitialContext ctx = new InitialContext(env);
          archieDatasource = (DataSource)ctx.lookup("Archimedes");
        } 
        this.connection = archieDatasource.getConnection();
      } 
    } catch (Exception sqlexc) {
      System.err.println("Exception: " + sqlexc.getMessage());
      sqlexc.printStackTrace();
      debugFile("<status code=\"Exception\">" + sqlexc.getMessage() + "</status>", 3);
      debugFile("<DATASOURCE ERROR/entries>", 3);
      this.connection = null;
    } 
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\JdbcConnector_Archimedes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */