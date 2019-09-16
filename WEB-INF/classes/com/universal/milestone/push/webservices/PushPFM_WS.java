package WEB-INF.classes.com.universal.milestone.push.webservices;

import com.techempower.EnhancedProperties;
import com.universal.milestone.JdbcConnector;
import com.universal.milestone.push.PushCommunication;
import com.universal.milestone.push.PushPFM;
import com.universal.milestone.push.webservices.PushPFM_WS;
import com.universal.milestone.xml.XMLUtil;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import weblogic.webservice.context.ContextNotFoundException;
import weblogic.webservice.context.WebServiceContext;
import weblogic.webservice.context.WebServiceSession;

public final class PushPFM_WS {
  private String CONFIGURATION_FILENAME = "milestone.conf";
  
  private JdbcConnector connector = null;
  
  private boolean IsDBConfigured = false;
  
  private boolean IsWebConfigured = false;
  
  private String MilestoneServer = "";
  
  public static final String configFile = "pushbean.config";
  
  public static final String[] milestoneNodeList = { "configuration", "milestone" };
  
  public static final String COMPONENT_CODE = "pushPFM_WS";
  
  private void configure() {
    if (JdbcConnector.DATASOURCE == null) {
      try {
        InputStream is = ClassLoader.getSystemResourceAsStream(this.CONFIGURATION_FILENAME);
        if (is == null)
          is = new FileInputStream(this.CONFIGURATION_FILENAME); 
        EnhancedProperties props = new EnhancedProperties();
        props.load(is);
        is.close();
        JdbcConnector.getDataSourceProperties(props);
        this.IsDBConfigured = true;
        PushCommunication.log("PushPFM configure() Configuration successfull");
      } catch (IOException ioexc) {
        PushCommunication.log("PushPFM configure() Cannot read configuration file: " + ioexc);
        PushCommunication.WriteCrossRoadsAuditLog("ErrorEmail", "pushPFM_WS", "PushPFM configure() end abnormally: " + this.MilestoneServer, ioexc.toString(), PushPFM.emailSubject);
      } 
    } else {
      this.IsDBConfigured = true;
    } 
  }
  
  private void getSessionInfo() {
    try {
      WebServiceContext wsContext = WebServiceContext.currentContext();
      WebServiceSession webServiceSession = wsContext.getSession();
    } catch (ContextNotFoundException e) {
      e.printStackTrace();
    } 
  }
  
  private void getWegConfig() {
    XMLUtil xmlUtil = new XMLUtil("pushbean.config");
    if (xmlUtil.getDocument() != null)
      this.MilestoneServer = xmlUtil.getElementValueByParentList(milestoneNodeList, "server", ""); 
    this.IsWebConfigured = true;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\push\webservices\PushPFM_WS.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */