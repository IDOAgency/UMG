package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.JdbcConnector;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.SessionUser;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

public class SessionUser implements HttpSessionBindingListener {
  String userName;
  
  String sessionId;
  
  int userId;
  
  String clientIP;
  
  int recordPK;
  
  String serverName;
  
  public static final String UserSessionValue = "UserSession";
  
  public SessionUser(String userName, String sessionId, int userId, String clientIP, String serverName) {
    this.userName = null;
    this.sessionId = null;
    this.clientIP = null;
    this.serverName = null;
    this.userName = userName;
    this.sessionId = sessionId;
    this.userId = userId;
    this.clientIP = clientIP;
    this.recordPK = -1;
    this.serverName = serverName;
  }
  
  public void valueBound(HttpSessionBindingEvent event) {
    System.out.println("<<< User Logged In " + this.userName);
    System.out.println("<<< User Session Id " + this.sessionId);
    String query = "sp_sav_User_Session " + 
      this.recordPK + "," + 
      "'" + this.userName + "'," + 
      "'" + this.sessionId + "'," + 
      this.userId + "," + 
      "'" + this.clientIP + "'," + 
      "'" + this.serverName + "'";
    System.out.println("Query >>>>>>>>>>>>>> : " + query);
    try {
      JdbcConnector connector = MilestoneHelper.getConnector(query);
      connector.runQuery();
      this.recordPK = connector.getIntegerField("ReturnId");
      System.out.println("<<< user session active " + this.recordPK);
      connector.close();
    } catch (Exception ex) {
      System.out.println("<<< sql error  " + ex.getMessage());
    } 
  }
  
  public void valueUnbound(HttpSessionBindingEvent event) {
    System.out.println("<<< Session Unbound " + this.recordPK);
    String query = "sp_sav_User_Session " + 
      this.recordPK + "," + 
      "'" + this.userName + "'," + 
      "'" + this.sessionId + "'," + 
      this.userId + "," + 
      "'" + this.clientIP + "'," + 
      "'" + this.serverName + "'";
    System.out.println("Query >>>>>>>>>>>>>> : " + query);
    try {
      JdbcConnector connector = MilestoneHelper.getConnector(query);
      connector.runQuery();
      System.out.println("<<< user session inactive ");
      connector.close();
    } catch (Exception ex) {
      System.out.println("<<< sql error  " + ex.getMessage());
    } 
  }
  
  public void setLastActiveDate(String command) {
    String query = "sp_sav_User_Session_Last_Active_Date " + 
      this.recordPK + ",'" + command + "'";
    try {
      JdbcConnector connector = MilestoneHelper.getConnector(query);
      connector.runUpdateQuery();
      connector.close();
    } catch (Exception ex) {
      System.out.println("<<< sql error  " + ex.getMessage());
    } 
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\SessionUser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */