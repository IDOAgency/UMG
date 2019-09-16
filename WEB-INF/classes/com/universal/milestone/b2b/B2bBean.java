package WEB-INF.classes.com.universal.milestone.b2b;

import com.universal.milestone.b2b.B2bBean;
import com.universal.milestone.b2b.SqlXML;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class B2bBean implements SessionBean {
  private static final boolean VERBOSE = true;
  
  private SessionContext ctx;
  
  private void log(String s) { System.out.println(s); }
  
  public void ejbActivate() { log("ejbActivate called"); }
  
  public void ejbRemove() { log("ejbRemove called"); }
  
  public void ejbPassivate() { log("ejbPassivate called"); }
  
  public void setSessionContext(SessionContext ctx) {
    log("setSessionContext called");
    this.ctx = ctx;
  }
  
  public void ejbCreate() {
    log("ejbCreate called");
    try {
      InitialContext ic = new InitialContext();
    } catch (NamingException ne) {
      throw new CreateException("Failed to find environment value " + ne);
    } 
  }
  
  public String getRelease(String full) {
    log("getRelease called");
    SqlXML sqlxml = new SqlXML();
    String sqlstr = "select street_date as 'StreetDate', release_id, international_date as 'InternationalDate', impact_date as 'ImpactDate', status as 'Status', hold_indicator as 'Hold', project_no as 'ProjectID', upc as 'UPC', selection_no as 'SelectionNumber', artist_first_name as 'ArtistFirstName', artist_last_name as 'ArtistLastName', title as 'Title', configuration as 'Configuration', release_type as 'ReleaseType', sub_configuration as 'SubConfiguration', a.[name] as 'Company', b.[name] as 'Division', c.[name] as 'Label', Release_header.last_updated_on as 'LastUpdated', entered_on as 'OriginDate', last_changed_on as 'LastStreetDateUpdated',  territory as 'Territory' from Release_header, structure a, structure b, structure c where street_date is not null and (a.structure_id = company_id and a.type=2) and (b.structure_id = division_id and b.type=3) and (c.structure_id = label_id and c.type=4) and status = 'ACTIVE' order by street_date ";
    String providerURL = sqlxml.PROVIDER_URL;
    String serverPath = sqlxml.B2BSERVERPATH;
    String filePath = sqlxml.B2BPATH;
    long milli = System.currentTimeMillis();
    String milliString = Long.toString(milli);
    String serverFileName = "\\\\" + sqlxml.B2BHOSTSERVER + serverPath + milliString + ".xml";
    System.out.println("serverFileName:[" + serverFileName + "]");
    String fileName = String.valueOf(filePath) + milliString + ".xml";
    System.out.println("****sqlstr:[" + sqlstr);
    String xmlString = sqlxml.getSQLXML(sqlstr);
    System.out.println("file name:[" + fileName + "]");
    File inputFile = new File(fileName);
    try {
      FileOutputStream out = new FileOutputStream(inputFile);
      PrintStream p = new PrintStream(out);
      p.print(xmlString);
      p.close();
    } catch (Exception e) {
      System.err.println("Error writing to file");
      serverFileName = "error";
    } 
    return serverFileName;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\b2b\B2bBean.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */