package WEB-INF.classes.com.universal.milestone.b2b;

import com.universal.milestone.b2b.SqlXML;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class SqlXML {
  StringBuffer xmlStr;
  
  Connection connection;
  
  DataSource datasource;
  
  Statement statement;
  
  ResultSet resultSet;
  
  Context ctx;
  
  Connection con;
  
  DataSource datasource2;
  
  Statement statement2;
  
  ResultSet resultSet2;
  
  Context ctx2;
  
  String DATASOURCE;
  
  String INITIAL_CONTEXT_FACTORY;
  
  String PROVIDER_URL;
  
  String B2BSERVERPATH;
  
  String B2BPATH;
  
  String WSDLPATH;
  
  String JDBCPREFIX;
  
  String DATABASELOGIN;
  
  String DATABASEPASSWORD;
  
  String B2BDATABASEPATH;
  
  String B2BHOSTSERVER;
  
  String[] ilCharA;
  
  String[] iReplace;
  
  public SqlXML() {
    this.xmlStr = new StringBuffer();
    this.connection = null;
    this.datasource = null;
    this.statement = null;
    this.resultSet = null;
    this.ctx = null;
    this.con = null;
    this.datasource2 = null;
    this.statement2 = null;
    this.resultSet2 = null;
    this.ctx2 = null;
    this.DATASOURCE = null;
    this.INITIAL_CONTEXT_FACTORY = null;
    this.PROVIDER_URL = null;
    this.B2BSERVERPATH = null;
    this.B2BPATH = null;
    this.WSDLPATH = null;
    this.JDBCPREFIX = null;
    this.DATABASELOGIN = null;
    this.DATABASEPASSWORD = null;
    this.B2BDATABASEPATH = null;
    this.B2BHOSTSERVER = null;
    this.ilCharA = new String[] { "<", ">", "&", "'", "\"" };
    this.iReplace = new String[] { "&lt;", "&gt;", "&amp;", "&apos;", "&quot;" };
    getConnection();
    try {
      this.con = DriverManager.getConnection(String.valueOf(this.JDBCPREFIX) + this.B2BDATABASEPATH, this.DATABASELOGIN, this.DATABASEPASSWORD);
      System.out.println("connection good");
    } catch (Exception e) {
      System.out.println("b2b getConnection failed");
      System.err.println("getConnection: " + e.getMessage());
    } 
  }
  
  public String getSQLXML(String query) {
    String releaseID = "";
    String streetdate = "";
    this.xmlStr.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
    try {
      this.statement = this.connection.createStatement(1004, 1007);
      this.resultSet = this.statement.executeQuery(query);
      ResultSetMetaData metaData = this.resultSet.getMetaData();
      this.resultSet.beforeFirst();
      this.xmlStr.append("\n<MilestoneFeed>");
      this.xmlStr.append("\n\t<CreatedOn>");
      DateFormat myformat = new SimpleDateFormat("yyyy.MM.dd");
      this.xmlStr.append(myformat.format(new Date()));
      this.xmlStr.append("</CreatedOn>");
      this.xmlStr.append("\n\t<Data>");
      while (this.resultSet.next()) {
        this.xmlStr.append("\n\t\t<DataRow>");
        for (int x = 1; x <= metaData.getColumnCount(); x++) {
          if (metaData.getColumnName(x).equals("release_id")) {
            releaseID = this.resultSet.getObject(x).toString().trim();
            this.xmlStr.append("\n\t\t\t");
            this.xmlStr.append("<ReleaseWeekCycle>");
            if (getPreDates(streetdate) == null) {
              this.xmlStr.append("");
            } else {
              this.xmlStr.append("<![CDATA[" + getPreDates(streetdate).trim() + "]]>");
            } 
            this.xmlStr.append("</ReleaseWeekCycle>");
          } else {
            if (metaData.getColumnName(x).equals("StreetDate"))
              streetdate = this.resultSet.getObject(x).toString().trim(); 
            this.xmlStr.append("\n\t\t\t");
            this.xmlStr.append("<" + metaData.getColumnName(x) + ">");
            if (metaData.getColumnName(x).equals("ImpactDate"))
              this.xmlStr.append("\n\t\t\t\t<SingleImpactDate>"); 
            if (this.resultSet.getObject(x) == null) {
              this.xmlStr.append("");
            } else {
              this.xmlStr.append("<![CDATA[" + this.resultSet.getObject(x).toString().trim() + "]]>");
            } 
            if (metaData.getColumnName(x).equals("ImpactDate")) {
              this.xmlStr.append("</SingleImpactDate>");
              this.xmlStr.append("\n\t\t\t\t<MultipleImpactDate>");
              this.xmlStr.append(printMultipleImpactDates(releaseID));
              this.xmlStr.append("\n\t\t\t\t</MultipleImpactDate>");
              this.xmlStr.append("\n\t\t\t</ImpactDate>");
            } else {
              this.xmlStr.append("</" + metaData.getColumnName(x) + ">");
            } 
          } 
        } 
        this.xmlStr.append("\n\t\t</DataRow>");
      } 
      this.xmlStr.append("\n\t</Data>");
      this.xmlStr.append("\n</MilestoneFeed>");
      this.con.close();
      this.connection.close();
    } catch (Exception sqlexc) {
      System.err.println("getSQLXML: " + sqlexc.getMessage());
    } 
    return this.xmlStr.toString();
  }
  
  public String getPreDates(String streetdate) {
    String dateDataString = "";
    try {
      CallableStatement cstmt = this.con.prepareCall("{call dbo.sp_getB2bReleaseWeekCycle(?, ?)}");
      cstmt.setString(1, streetdate);
      cstmt.registerOutParameter(2, 12);
      cstmt.executeQuery();
      dateDataString = cstmt.getString(2);
    } catch (Exception sqlexc) {
      System.err.println("dates error message: " + sqlexc.getMessage());
    } 
    return dateDataString;
  }
  
  public String printMultipleImpactDates(String selectionID) {
    StringBuffer impactDateString = new StringBuffer();
    String sqlstr2 = "select impactDate, b.sub_value as 'Format', tbi as 'TBI' from ImpactDates, lookup_subdetail b where selection_id = " + 
      selectionID + " and b.field_id = 58 and b.det_value = [format]";
    System.out.println("sqlstr2:[" + sqlstr2 + "]");
    try {
      this.statement2 = this.connection.createStatement(1004, 1007);
      ResultSet resultSet2 = this.statement2.executeQuery(sqlstr2);
      ResultSetMetaData metaData = resultSet2.getMetaData();
      resultSet2.beforeFirst();
      while (resultSet2.next()) {
        for (int x = 1; x <= metaData.getColumnCount(); x++) {
          impactDateString.append("\n\t\t\t\t\t");
          impactDateString.append("<" + metaData.getColumnName(x) + ">");
          if (resultSet2.getObject(x) == null) {
            impactDateString.append("");
          } else {
            impactDateString.append("<![CDATA[" + resultSet2.getObject(x).toString().trim() + "]]>");
          } 
          impactDateString.append("</" + metaData.getColumnName(x) + ">");
        } 
      } 
    } catch (Exception sqlexc) {
      System.err.println("impactDateString died because: " + sqlexc.getMessage());
    } 
    return impactDateString.toString();
  }
  
  public void getConnection() {
    if (getConfig())
      try {
        Hashtable env = new Hashtable();
        env.put("java.naming.factory.initial", this.INITIAL_CONTEXT_FACTORY);
        env.put("java.naming.provider.url", this.PROVIDER_URL);
        InitialContext ctx = new InitialContext(env);
        this.datasource = (DataSource)ctx.lookup(this.DATASOURCE);
        this.connection = this.datasource.getConnection();
      } catch (Exception sqlexc) {
        System.err.println("Connection Exception: " + sqlexc.getMessage());
        sqlexc.printStackTrace();
        this.connection = null;
      }  
  }
  
  public boolean getConfig() {
    try {
      System.out.println("getConfig1");
      String filename = "milestone.conf";
      InputStream in = ClassLoader.getSystemResourceAsStream(filename);
      if (in == null)
        in = new FileInputStream(filename); 
      Properties defaultProps = new Properties();
      defaultProps.load(in);
      this.DATASOURCE = defaultProps.getProperty("DATASOURCE");
      this.INITIAL_CONTEXT_FACTORY = defaultProps.getProperty("INITIAL_CONTEXT_FACTORY");
      this.PROVIDER_URL = defaultProps.getProperty("PROVIDER_URL");
      this.B2BSERVERPATH = defaultProps.getProperty("b2bServerPath");
      System.out.println("b2bserverPath: " + this.B2BSERVERPATH);
      this.B2BPATH = defaultProps.getProperty("b2bPath");
      System.out.println("B2bPath: " + this.B2BPATH);
      this.WSDLPATH = defaultProps.getProperty("b2bWsdlPath");
      System.out.println("WsdlPath: " + this.WSDLPATH);
      this.JDBCPREFIX = defaultProps.getProperty("JDBCURLPrefix");
      System.out.println("JDBCPREFIX: " + this.JDBCPREFIX);
      this.DATABASELOGIN = defaultProps.getProperty("DatabaseLogin");
      System.out.println("DATABASELOGIN: " + this.DATABASELOGIN);
      this.DATABASEPASSWORD = defaultProps.getProperty("DatabasePassword");
      System.out.println("DATABASEPASSWORD: " + this.DATABASEPASSWORD);
      this.B2BDATABASEPATH = defaultProps.getProperty("b2bDatabasePath");
      System.out.println("DATABASEPATH: " + this.B2BDATABASEPATH);
      this.B2BHOSTSERVER = defaultProps.getProperty("b2bHostServer");
      System.out.println("B2BHOSTSERVER: " + this.B2BHOSTSERVER);
      System.out.println("datasource: " + this.DATASOURCE);
      System.out.println("InitCtxFac: " + this.INITIAL_CONTEXT_FACTORY);
      System.out.println("providerUrl: " + this.PROVIDER_URL);
      in.close();
      return true;
    } catch (Exception ex) {
      System.out.println("<<< Properties Exception " + ex.getMessage());
      return false;
    } 
  }
  
  private String fixForHtml(String xmlstr) {
    String newStr = new String(this.xmlStr);
    for (int x = 0; x < this.ilCharA.length; x++) {
      int f = newStr.indexOf(this.ilCharA[x]);
      int b = 0;
      int e = 0;
      while (f != -1) {
        String hldStr = new String(newStr);
        if (f > 0) {
          newStr = newStr.substring(b, f - 1);
        } else {
          newStr = this.iReplace[x];
        } 
        e = f + 1;
        if (e < hldStr.length())
          newStr = String.valueOf(newStr) + hldStr.substring(e); 
        f = hldStr.indexOf(this.ilCharA[x], e);
      } 
    } 
    return newStr;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\b2b\SqlXML.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */