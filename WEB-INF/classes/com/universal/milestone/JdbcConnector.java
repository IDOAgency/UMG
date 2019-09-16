package WEB-INF.classes.com.universal.milestone;

import com.techempower.DatabaseConnector;
import com.techempower.EnhancedProperties;
import com.universal.milestone.JdbcConnector;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class JdbcConnector implements DatabaseConnector {
  protected Connection connection;
  
  public static DataSource datasource = null;
  
  protected Statement statement;
  
  protected PreparedStatement preparedStatement;
  
  protected ResultSet resultSet;
  
  protected int resultInt;
  
  protected String query;
  
  protected boolean forwardOnly;
  
  protected boolean readOnly;
  
  protected int resultSetType;
  
  protected int resultSetConcurrency;
  
  protected String username;
  
  protected String password;
  
  protected boolean nextThrewException;
  
  public static final String NULL_VALUE_REPLACE = "[none]";
  
  protected int rowCount;
  
  public static String DATASOURCE = null;
  
  public static String INITIAL_CONTEXT_FACTORY = null;
  
  public static String PROVIDER_URL = null;
  
  public static String ArchimedesDBServer = "";
  
  public static final String DEFAULT_DEBUG_FILE = "d:\\bea\\milestone\\logs\\JdbcConnector-debug.txt";
  
  public static final String debugFilename = "d:\\bea\\milestone\\logs\\JdbcConnector-debug.txt";
  
  public static final int DEBUG_HIGH = 3;
  
  public static final int DEBUG_MEDIUM = 2;
  
  public static final int DEBUG_LOW = 1;
  
  protected static int debugLevel = 3;
  
  protected static long queryNumber = 0L;
  
  public static final long EXCESSIVE_FILE_SIZE = 2000000L;
  
  protected static final SimpleDateFormat DEBUG_DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");
  
  public static final String COMPONENT_CODE = "jdbc";
  
  public static final boolean DEBUG_ALSO_CONSOLE = false;
  
  public int maxRows;
  
  public static final String BEA_DriverMessage01 = "[BEA][SQLServer JDBC Driver]No ResultSet set was produced.";
  
  public JdbcConnector(String jdbcURLPrefix, String connectString, String query) {
    this.connection = null;
    this.statement = null;
    this.preparedStatement = null;
    this.resultSet = null;
    this.resultInt = 0;
    this.query = null;
    this.forwardOnly = false;
    this.readOnly = true;
    this.resultSetType = 1004;
    this.resultSetConcurrency = 1007;
    this.username = "";
    this.password = "";
    this.nextThrewException = true;
    this.rowCount = -1;
    this.maxRows = 0;
    this.query = query;
    getDataSourceConnnection();
  }
  
  public JdbcConnector(String connectString, String query, boolean readOnly, boolean forwardOnly) {
    this.connection = null;
    this.statement = null;
    this.preparedStatement = null;
    this.resultSet = null;
    this.resultInt = 0;
    this.query = null;
    this.forwardOnly = false;
    this.readOnly = true;
    this.resultSetType = 1004;
    this.resultSetConcurrency = 1007;
    this.username = "";
    this.password = "";
    this.nextThrewException = true;
    this.rowCount = -1;
    this.maxRows = 0;
    getDataSourceConnnection();
  }
  
  public JdbcConnector(String connectString, String query) {
    this.connection = null;
    this.statement = null;
    this.preparedStatement = null;
    this.resultSet = null;
    this.resultInt = 0;
    this.query = null;
    this.forwardOnly = false;
    this.readOnly = true;
    this.resultSetType = 1004;
    this.resultSetConcurrency = 1007;
    this.username = "";
    this.password = "";
    this.nextThrewException = true;
    this.rowCount = -1;
    this.maxRows = 0;
    this.query = query;
    getDataSourceConnnection();
  }
  
  public JdbcConnector(String query, boolean isPreparedStatement) {
    this.connection = null;
    this.statement = null;
    this.preparedStatement = null;
    this.resultSet = null;
    this.resultInt = 0;
    this.query = null;
    this.forwardOnly = false;
    this.readOnly = true;
    this.resultSetType = 1004;
    this.resultSetConcurrency = 1007;
    this.username = "";
    this.password = "";
    this.nextThrewException = true;
    this.rowCount = -1;
    this.maxRows = 0;
    this.query = query;
    getDataSourceConnnection();
    if (isPreparedStatement)
      createPreparedStatement(); 
  }
  
  public JdbcConnector() {
    this.connection = null;
    this.statement = null;
    this.preparedStatement = null;
    this.resultSet = null;
    this.resultInt = 0;
    this.query = null;
    this.forwardOnly = false;
    this.readOnly = true;
    this.resultSetType = 1004;
    this.resultSetConcurrency = 1007;
    this.username = "";
    this.password = "";
    this.nextThrewException = true;
    this.rowCount = -1;
    this.maxRows = 0;
    getDataSourceConnnection();
  }
  
  public JdbcConnector(String connectString) {
    this.connection = null;
    this.statement = null;
    this.preparedStatement = null;
    this.resultSet = null;
    this.resultInt = 0;
    this.query = null;
    this.forwardOnly = false;
    this.readOnly = true;
    this.resultSetType = 1004;
    this.resultSetConcurrency = 1007;
    this.username = "";
    this.password = "";
    this.nextThrewException = true;
    this.rowCount = -1;
    this.maxRows = 0;
    getDataSourceConnnection();
  }
  
  public static void getDataSourceProperties(EnhancedProperties defaultProps) {
    DATASOURCE = defaultProps.getProperty("DATASOURCE");
    INITIAL_CONTEXT_FACTORY = defaultProps.getProperty("INITIAL_CONTEXT_FACTORY");
    PROVIDER_URL = defaultProps.getProperty("PROVIDER_URL");
    ArchimedesDBServer = defaultProps.getProperty("ArchimedesDBServer", "");
    System.out.println("<<< DATASOURCE " + DATASOURCE);
    System.out.println("<<< INITIAL_CONTEXT_FACTORY " + INITIAL_CONTEXT_FACTORY);
    System.out.println("<<< PROVIDER_URL " + PROVIDER_URL);
    System.out.println("<<< Archimedes DB Server " + ArchimedesDBServer);
  }
  
  public static DataSource getDataSource() { return datasource; }
  
  public void createPreparedStatement() {
    try {
      this.preparedStatement = this.connection.prepareStatement(this.query, this.resultSetType, this.resultSetConcurrency);
    } catch (Exception se) {
      debugFile("<< Prepared Statement exception ", 3);
      System.out.println("<< Prepared Statement exception " + se.getMessage());
    } 
  }
  
  public void getDataSourceConnnection() {
    try {
      if (this.connection == null) {
        if (datasource == null) {
          System.out.println("<<< null data source ");
          Hashtable env = new Hashtable();
          env.put("java.naming.factory.initial", INITIAL_CONTEXT_FACTORY);
          env.put("java.naming.provider.url", PROVIDER_URL);
          InitialContext ctx = new InitialContext(env);
          datasource = (DataSource)ctx.lookup(DATASOURCE);
        } 
        this.connection = datasource.getConnection();
      } 
    } catch (Exception sqlexc) {
      System.err.println("Exception: " + sqlexc.getMessage());
      sqlexc.printStackTrace();
      debugFile("<status code=\"Exception\">" + sqlexc.getMessage() + "</status>", 3);
      debugFile("<DATASOURCE ERROR/entries>", 3);
      this.connection = null;
    } 
  }
  
  public void runQuery() {
    getDataSourceConnnection();
    this.rowCount = -1;
    try {
      if (this.preparedStatement == null)
        generateStatement(); 
      String nonResult = this.query.toUpperCase().trim();
      if (nonResult.startsWith("UPDATE ") || nonResult.startsWith("INSERT ") || nonResult.startsWith("DELETE ")) {
        generateResultsUpdate();
      } else {
        generateResults();
      } 
    } catch (SQLException sqlexc) {
      debugFile("SQLException in generateResults: " + sqlexc);
      debugFile("SQLState: " + sqlexc.getSQLState());
      System.out.println("SQLException in generateResults: " + sqlexc.getMessage());
      System.out.println("SQLState: " + sqlexc.getSQLState());
      this.resultSet = null;
      this.preparedStatement = null;
      this.statement = null;
      this.connection = null;
    } 
  }
  
  protected void generateStatement() {
    try {
      debugFile("Creating the statement.");
      this.resultSetType = 1004;
      this.resultSetConcurrency = 1007;
      if (this.forwardOnly)
        this.resultSetType = 1003; 
      if (!this.readOnly)
        this.resultSetConcurrency = 1008; 
      this.statement = this.connection.createStatement(this.resultSetType, this.resultSetConcurrency);
    } catch (SQLException sqlexc) {
      throw sqlexc;
    } 
  }
  
  protected void generateResults() {
    try {
      if (this.preparedStatement != null) {
        this.resultSet = this.preparedStatement.executeQuery();
      } else {
        this.resultSet = this.statement.executeQuery(this.query);
      } 
      debugFile("Results generated.");
      next();
    } catch (SQLException sqlexc) {
      if (!sqlexc.getMessage().equals("[BEA][SQLServer JDBC Driver]No ResultSet set was produced."))
        throw sqlexc; 
    } 
  }
  
  protected void generateResultsUpdate() {
    try {
      if (this.preparedStatement != null) {
        this.resultInt = this.preparedStatement.executeUpdate();
      } else {
        this.resultInt = this.statement.executeUpdate(this.query);
      } 
      debugFile("Results generated.");
    } catch (SQLException sqlexc) {
      throw sqlexc;
    } 
  }
  
  public Connection getConnection() { return this.connection; }
  
  public void setUsername(String username) { this.username = username; }
  
  public void setPassword(String password) { this.password = password; }
  
  public void setQuery(String query) {
    debugFile("setQuery: " + query);
    closeStatement();
    getDataSourceConnnection();
    this.query = query;
    this.nextThrewException = true;
  }
  
  public void setQuery(String query, boolean isPreparedStatement) {
    debugFile("setQuery: " + query);
    this.query = query;
    closeStatement();
    getDataSourceConnnection();
    if (isPreparedStatement)
      createPreparedStatement(); 
    this.nextThrewException = true;
  }
  
  public String getQuery() { return this.query; }
  
  public void setObject(int parm, Object object) {
    try {
      this.preparedStatement.setObject(parm, object);
    } catch (Exception sqlexc) {
      System.out.println("Exception while setObject() " + sqlexc.getMessage());
      debugFile("Exception while gathering field names: " + sqlexc);
    } 
  }
  
  public void setReadOnly(boolean readOnly) { this.readOnly = readOnly; }
  
  public boolean getReadOnly() { return this.readOnly; }
  
  public void setForwardOnly(boolean forwardOnly) { this.forwardOnly = forwardOnly; }
  
  public boolean getForwardOnly() { return this.forwardOnly; }
  
  public void first() {
    if (this.forwardOnly) {
      debugFile("call to first on a forward-only query.");
      return;
    } 
    try {
      this.nextThrewException = !this.resultSet.first();
    } catch (Exception exc) {
      debugFile("Exception on first(): " + exc);
      this.nextThrewException = true;
    } 
  }
  
  public void next() {
    try {
      this.nextThrewException = !this.resultSet.next();
    } catch (Exception exc) {
      debugFile("Next exception " + exc.getMessage());
      this.nextThrewException = true;
    } 
  }
  
  public boolean more() { return !this.nextThrewException; }
  
  public void moveAbsolute(int position) {
    if (this.forwardOnly) {
      debugFile("call to moveAbsolute on a forward-only query.");
      return;
    } 
    try {
      this.resultSet.absolute(position + 1);
    } catch (Exception exc) {
      debugFile("Exception while moving to absolute position " + position + ": " + exc);
    } 
  }
  
  public int getRowNumber() {
    try {
      return this.resultSet.getRow();
    } catch (Exception exc) {
      debugFile("Exception while getting row number: " + exc);
      return 0;
    } 
  }
  
  public String[] getFieldNames() {
    try {
      ResultSetMetaData metadata = this.resultSet.getMetaData();
      int fieldCount = metadata.getColumnCount();
      String[] toReturn = new String[fieldCount];
      for (int i = 1; i < fieldCount + 1; i++)
        toReturn[i - 1] = metadata.getColumnName(i); 
      return toReturn;
    } catch (Exception exc) {
      debugFile("Exception while gathering field names: " + exc);
      return new String[0];
    } 
  }
  
  public int[] getFieldTypes() {
    try {
      ResultSetMetaData metadata = this.resultSet.getMetaData();
      int fieldCount = metadata.getColumnCount();
      int[] toReturn = new int[fieldCount];
      for (int i = 1; i < fieldCount + 1; i++)
        toReturn[i - 1] = metadata.getColumnType(i); 
      return toReturn;
    } catch (Exception exc) {
      debugFile("Exception while gathering field names: " + exc);
      return new int[0];
    } 
  }
  
  public String getFieldByName(String fieldName) {
    try {
      return this.resultSet.getString(fieldName);
    } catch (Exception exc) {
      debugFile("Exception while retrieving field " + fieldName + ": " + exc);
      return null;
    } 
  }
  
  public String getField(String fieldName) {
    String result = getFieldByName(fieldName);
    if (result != null)
      return result; 
    return "[none]";
  }
  
  public String getField(String fieldName, String defaultValue) {
    String result = getFieldByName(fieldName);
    if (result != null)
      return result; 
    return defaultValue;
  }
  
  public int getIntegerFieldByName(String fieldName) {
    try {
      return this.resultSet.getInt(fieldName);
    } catch (Exception exc) {
      return 0;
    } 
  }
  
  public int getIntegerField(String fieldName) { return getIntegerFieldByName(fieldName); }
  
  public int getInt(String fieldName) { return getIntegerFieldByName(fieldName); }
  
  public int getInt(String fieldName, int defaultValue) {
    try {
      return this.resultSet.getInt(fieldName);
    } catch (Exception exc) {
      return defaultValue;
    } 
  }
  
  public Date getDate(String fieldName) {
    try {
      return this.resultSet.getDate(fieldName);
    } catch (Exception exc) {
      return null;
    } 
  }
  
  public Timestamp getTimestamp(String fieldName) {
    try {
      return this.resultSet.getTimestamp(fieldName);
    } catch (Exception exc) {
      return null;
    } 
  }
  
  public boolean getBoolean(String fieldName) {
    try {
      return this.resultSet.getBoolean(fieldName);
    } catch (Exception exc) {
      return false;
    } 
  }
  
  public float getFloat(String fieldName) {
    try {
      return this.resultSet.getFloat(fieldName);
    } catch (Exception exc) {
      return 0.0F;
    } 
  }
  
  public double getDouble(String fieldName) {
    try {
      return this.resultSet.getDouble(fieldName);
    } catch (Exception exc) {
      return 0.0D;
    } 
  }
  
  public byte getByte(String fieldName) {
    try {
      return this.resultSet.getByte(fieldName);
    } catch (Exception exc) {
      return 0;
    } 
  }
  
  public short getShort(String fieldName) {
    try {
      return this.resultSet.getShort(fieldName);
    } catch (Exception exc) {
      return 0;
    } 
  }
  
  public long getLong(String fieldName) {
    try {
      return this.resultSet.getLong(fieldName);
    } catch (Exception exc) {
      return 0L;
    } 
  }
  
  public int getRowCount() {
    if (this.resultSet != null) {
      if (this.rowCount > -1)
        return this.rowCount; 
      this.rowCount = 0;
      try {
        this.resultSet.last();
        this.rowCount = this.resultSet.getRow();
      } catch (Exception le) {
        debugFile("Exception while going to last row for count: " + le);
      } 
      first();
      return this.rowCount;
    } 
    return 0;
  }
  
  public void closeStatement() {
    if (this.resultSet != null) {
      try {
        this.resultSet.close();
      } catch (Exception sqlexc) {
        debugFile("DATASOURCE ERROR....Exception while closing result set: " + sqlexc, 3);
      } 
      this.resultSet = null;
    } 
    if (this.preparedStatement != null)
      if (this.preparedStatement != null) {
        try {
          this.preparedStatement.close();
        } catch (Exception sqlexc) {
          debugFile("Exception while closing statement: " + sqlexc);
        } 
        this.preparedStatement = null;
      }  
    if (this.statement != null) {
      try {
        this.statement.close();
      } catch (Exception sqlexc) {
        debugFile("Exception while closing statement: " + sqlexc);
      } 
      this.statement = null;
    } 
  }
  
  public void close() {
    closeStatement();
    if (this.connection != null) {
      try {
        this.connection.close();
      } catch (Exception sqlexc) {
        debugFile("Exception while closing connection: " + sqlexc);
      } 
      this.connection = null;
    } 
  }
  
  public int runUpdateQuery() {
    getDataSourceConnnection();
    this.rowCount = -1;
    try {
      if (this.preparedStatement == null)
        generateStatement(); 
      generateResultsUpdate();
    } catch (SQLException sqlexc) {
      debugFile("SQLException in generateResults: " + sqlexc);
      debugFile("SQLState: " + sqlexc.getSQLState());
      System.out.println("SQLException in generateResults: " + sqlexc.getMessage());
      System.out.println("SQLState: " + sqlexc.getSQLState());
      this.resultSet = null;
      this.preparedStatement = null;
      this.statement = null;
    } 
    return this.resultInt;
  }
  
  public static void loadDriver(String className, String urlPrefix, boolean supportsAbsolute, boolean supportsGetRow, boolean jdbc1, int pooling) {}
  
  public static void setDefaultJdbcURLPrefix(String newPrefix) {}
  
  public void debugFile(String debugString) { debugFile(debugString, 1); }
  
  public void debugFile(String debugString, int debugSeverity) {
    if (debugLevel <= debugSeverity)
      if ("d:\\bea\\milestone\\logs\\JdbcConnector-debug.txt" != null)
        try {
          if (queryNumber % 500L == 0L) {
            File file = new File("d:\\bea\\milestone\\logs\\JdbcConnector-debug.txt");
            if (file.exists())
              if (file.length() > 2000000L)
                file.delete();  
          } 
          String code = "H";
          if (debugSeverity == 2) {
            code = "M";
          } else if (debugSeverity == 1) {
            code = "L";
          } 
          Date date = new Date();
          String toWrite = "JC " + 
            DEBUG_DATE_FORMAT.format(date) + 
            " " + "jdbc" + 
            ": [" + code + "] " + debugString;
          FileWriter fileWriter = new FileWriter("d:\\bea\\milestone\\logs\\JdbcConnector-debug.txt", true);
          fileWriter.write(String.valueOf(toWrite) + "\r\n");
          fileWriter.flush();
          fileWriter.close();
        } catch (IOException iOException) {}  
  }
  
  public void setMaxRows(int maxRows) { this.maxRows = maxRows; }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\JdbcConnector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */