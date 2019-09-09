/*      */ package WEB-INF.classes.com.universal.milestone;
/*      */ 
/*      */ import com.techempower.DatabaseConnector;
/*      */ import com.techempower.EnhancedProperties;
/*      */ import com.universal.milestone.JdbcConnector;
/*      */ import java.io.File;
/*      */ import java.io.FileWriter;
/*      */ import java.io.IOException;
/*      */ import java.sql.Connection;
/*      */ import java.sql.Date;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.ResultSetMetaData;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Statement;
/*      */ import java.sql.Timestamp;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.Date;
/*      */ import java.util.Hashtable;
/*      */ import javax.naming.InitialContext;
/*      */ import javax.sql.DataSource;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class JdbcConnector
/*      */   implements DatabaseConnector
/*      */ {
/*      */   protected Connection connection;
/*   39 */   public static DataSource datasource = null;
/*      */   
/*      */   protected Statement statement;
/*      */   
/*      */   protected PreparedStatement preparedStatement;
/*      */   
/*      */   protected ResultSet resultSet;
/*      */   
/*      */   protected int resultInt;
/*      */   protected String query;
/*      */   protected boolean forwardOnly;
/*      */   protected boolean readOnly;
/*      */   protected int resultSetType;
/*      */   protected int resultSetConcurrency;
/*      */   protected String username;
/*      */   protected String password;
/*      */   protected boolean nextThrewException;
/*      */   public static final String NULL_VALUE_REPLACE = "[none]";
/*      */   protected int rowCount;
/*   58 */   public static String DATASOURCE = null;
/*   59 */   public static String INITIAL_CONTEXT_FACTORY = null;
/*   60 */   public static String PROVIDER_URL = null;
/*      */   
/*   62 */   public static String ArchimedesDBServer = "";
/*      */   
/*      */   public static final String DEFAULT_DEBUG_FILE = "d:\\bea\\milestone\\logs\\JdbcConnector-debug.txt";
/*      */   
/*      */   public static final String debugFilename = "d:\\bea\\milestone\\logs\\JdbcConnector-debug.txt";
/*      */   
/*      */   public static final int DEBUG_HIGH = 3;
/*      */   public static final int DEBUG_MEDIUM = 2;
/*      */   public static final int DEBUG_LOW = 1;
/*   71 */   protected static int debugLevel = 3;
/*   72 */   protected static long queryNumber = 0L; public static final long EXCESSIVE_FILE_SIZE = 2000000L; public static final String COMPONENT_CODE = "jdbc";
/*      */   public static final boolean DEBUG_ALSO_CONSOLE = false;
/*   74 */   protected static final SimpleDateFormat DEBUG_DATE_FORMAT = new SimpleDateFormat("HH:mm:ss"); public int maxRows; public static final String BEA_DriverMessage01 = "[BEA][SQLServer JDBC Driver]No ResultSet set was produced."; public JdbcConnector(String jdbcURLPrefix, String connectString, String query) { this.connection = null; this.statement = null; this.preparedStatement = null; this.resultSet = null; this.resultInt = 0; this.query = null; this.forwardOnly = false; this.readOnly = true; this.resultSetType = 1004; this.resultSetConcurrency = 1007;
/*      */     this.username = "";
/*      */     this.password = "";
/*      */     this.nextThrewException = true;
/*      */     this.rowCount = -1;
/*   79 */     this.maxRows = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   95 */     this.query = query;
/*   96 */     getDataSourceConnnection(); }
/*      */   
/*      */   public JdbcConnector(String connectString, String query, boolean readOnly, boolean forwardOnly) { this.connection = null;
/*      */     this.statement = null;
/*      */     this.preparedStatement = null;
/*      */     this.resultSet = null;
/*      */     this.resultInt = 0;
/*      */     this.query = null;
/*      */     this.forwardOnly = false;
/*      */     this.readOnly = true;
/*      */     this.resultSetType = 1004;
/*      */     this.resultSetConcurrency = 1007;
/*      */     this.username = "";
/*      */     this.password = "";
/*      */     this.nextThrewException = true;
/*      */     this.rowCount = -1;
/*      */     this.maxRows = 0;
/*  113 */     getDataSourceConnnection(); } public JdbcConnector(String connectString, String query) { this.connection = null; this.statement = null; this.preparedStatement = null;
/*      */     this.resultSet = null;
/*      */     this.resultInt = 0;
/*      */     this.query = null;
/*      */     this.forwardOnly = false;
/*      */     this.readOnly = true;
/*      */     this.resultSetType = 1004;
/*      */     this.resultSetConcurrency = 1007;
/*      */     this.username = "";
/*      */     this.password = "";
/*      */     this.nextThrewException = true;
/*      */     this.rowCount = -1;
/*      */     this.maxRows = 0;
/*  126 */     this.query = query;
/*  127 */     getDataSourceConnnection(); } public JdbcConnector(String query, boolean isPreparedStatement) { this.connection = null; this.statement = null; this.preparedStatement = null;
/*      */     this.resultSet = null;
/*      */     this.resultInt = 0;
/*      */     this.query = null;
/*      */     this.forwardOnly = false;
/*      */     this.readOnly = true;
/*      */     this.resultSetType = 1004;
/*      */     this.resultSetConcurrency = 1007;
/*      */     this.username = "";
/*      */     this.password = "";
/*      */     this.nextThrewException = true;
/*      */     this.rowCount = -1;
/*      */     this.maxRows = 0;
/*  140 */     this.query = query;
/*  141 */     getDataSourceConnnection();
/*      */ 
/*      */     
/*  144 */     if (isPreparedStatement)
/*  145 */       createPreparedStatement();  } public JdbcConnector() { this.connection = null; this.statement = null; this.preparedStatement = null; this.resultSet = null;
/*      */     this.resultInt = 0;
/*      */     this.query = null;
/*      */     this.forwardOnly = false;
/*      */     this.readOnly = true;
/*      */     this.resultSetType = 1004;
/*      */     this.resultSetConcurrency = 1007;
/*      */     this.username = "";
/*      */     this.password = "";
/*      */     this.nextThrewException = true;
/*      */     this.rowCount = -1;
/*      */     this.maxRows = 0;
/*  157 */     getDataSourceConnnection(); } public JdbcConnector(String connectString) { this.connection = null; this.statement = null; this.preparedStatement = null; this.resultSet = null;
/*      */     this.resultInt = 0;
/*      */     this.query = null;
/*      */     this.forwardOnly = false;
/*      */     this.readOnly = true;
/*      */     this.resultSetType = 1004;
/*      */     this.resultSetConcurrency = 1007;
/*      */     this.username = "";
/*      */     this.password = "";
/*      */     this.nextThrewException = true;
/*      */     this.rowCount = -1;
/*      */     this.maxRows = 0;
/*  169 */     getDataSourceConnnection(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void getDataSourceProperties(EnhancedProperties defaultProps) {
/*  185 */     DATASOURCE = defaultProps.getProperty("DATASOURCE");
/*  186 */     INITIAL_CONTEXT_FACTORY = defaultProps.getProperty("INITIAL_CONTEXT_FACTORY");
/*  187 */     PROVIDER_URL = defaultProps.getProperty("PROVIDER_URL");
/*      */     
/*  189 */     ArchimedesDBServer = defaultProps.getProperty("ArchimedesDBServer", "");
/*  190 */     System.out.println("<<< DATASOURCE " + DATASOURCE);
/*  191 */     System.out.println("<<< INITIAL_CONTEXT_FACTORY " + INITIAL_CONTEXT_FACTORY);
/*  192 */     System.out.println("<<< PROVIDER_URL " + PROVIDER_URL);
/*  193 */     System.out.println("<<< Archimedes DB Server " + ArchimedesDBServer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  202 */   public static DataSource getDataSource() { return datasource; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void createPreparedStatement() {
/*      */     try {
/*  211 */       this.preparedStatement = this.connection.prepareStatement(this.query, this.resultSetType, this.resultSetConcurrency);
/*  212 */     } catch (Exception se) {
/*      */       
/*  214 */       debugFile("<< Prepared Statement exception ", 3);
/*  215 */       System.out.println("<< Prepared Statement exception " + se.getMessage());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void getDataSourceConnnection() {
/*      */     try {
/*  225 */       if (this.connection == null)
/*      */       {
/*      */         
/*  228 */         if (datasource == null) {
/*      */           
/*  230 */           System.out.println("<<< null data source ");
/*  231 */           Hashtable env = new Hashtable();
/*  232 */           env.put("java.naming.factory.initial", INITIAL_CONTEXT_FACTORY);
/*  233 */           env.put("java.naming.provider.url", PROVIDER_URL);
/*  234 */           InitialContext ctx = new InitialContext(env);
/*  235 */           datasource = (DataSource)ctx.lookup(DATASOURCE);
/*      */         } 
/*  237 */         this.connection = datasource.getConnection();
/*      */       }
/*      */     
/*  240 */     } catch (Exception sqlexc) {
/*  241 */       System.err.println("Exception: " + sqlexc.getMessage());
/*  242 */       sqlexc.printStackTrace();
/*  243 */       debugFile("<status code=\"Exception\">" + sqlexc.getMessage() + "</status>", 3);
/*  244 */       debugFile("<DATASOURCE ERROR/entries>", 3);
/*  245 */       this.connection = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void runQuery() {
/*  258 */     getDataSourceConnnection();
/*      */ 
/*      */     
/*  261 */     this.rowCount = -1;
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  266 */       if (this.preparedStatement == null) {
/*  267 */         generateStatement();
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  274 */       String nonResult = this.query.toUpperCase().trim();
/*  275 */       if (nonResult.startsWith("UPDATE ") || nonResult.startsWith("INSERT ") || nonResult.startsWith("DELETE ")) {
/*  276 */         generateResultsUpdate();
/*      */       } else {
/*  278 */         generateResults();
/*      */       } 
/*  280 */     } catch (SQLException sqlexc) {
/*      */ 
/*      */       
/*  283 */       debugFile("SQLException in generateResults: " + sqlexc);
/*  284 */       debugFile("SQLState: " + sqlexc.getSQLState());
/*  285 */       System.out.println("SQLException in generateResults: " + sqlexc.getMessage());
/*  286 */       System.out.println("SQLState: " + sqlexc.getSQLState());
/*      */       
/*  288 */       this.resultSet = null;
/*  289 */       this.preparedStatement = null;
/*  290 */       this.statement = null;
/*  291 */       this.connection = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void generateStatement() {
/*      */     try {
/*  306 */       debugFile("Creating the statement.");
/*      */ 
/*      */       
/*  309 */       this.resultSetType = 1004;
/*  310 */       this.resultSetConcurrency = 1007;
/*      */ 
/*      */       
/*  313 */       if (this.forwardOnly)
/*  314 */         this.resultSetType = 1003; 
/*  315 */       if (!this.readOnly) {
/*  316 */         this.resultSetConcurrency = 1008;
/*      */       }
/*  318 */       this.statement = this.connection.createStatement(this.resultSetType, this.resultSetConcurrency);
/*      */     
/*      */     }
/*  321 */     catch (SQLException sqlexc) {
/*      */       
/*  323 */       throw sqlexc;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void generateResults() {
/*      */     try {
/*  337 */       if (this.preparedStatement != null) {
/*      */ 
/*      */ 
/*      */         
/*  341 */         this.resultSet = this.preparedStatement.executeQuery();
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */         
/*  347 */         this.resultSet = this.statement.executeQuery(this.query);
/*      */       } 
/*      */       
/*  350 */       debugFile("Results generated.");
/*      */       
/*  352 */       next();
/*      */     
/*      */     }
/*  355 */     catch (SQLException sqlexc) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  364 */       if (!sqlexc.getMessage().equals("[BEA][SQLServer JDBC Driver]No ResultSet set was produced.")) {
/*  365 */         throw sqlexc;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void generateResultsUpdate() {
/*      */     try {
/*  380 */       if (this.preparedStatement != null) {
/*  381 */         this.resultInt = this.preparedStatement.executeUpdate();
/*      */       } else {
/*  383 */         this.resultInt = this.statement.executeUpdate(this.query);
/*      */       } 
/*  385 */       debugFile("Results generated.");
/*      */     
/*      */     }
/*  388 */     catch (SQLException sqlexc) {
/*      */       
/*  390 */       throw sqlexc;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  401 */   public Connection getConnection() { return this.connection; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  412 */   public void setUsername(String username) { this.username = username; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  421 */   public void setPassword(String password) { this.password = password; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setQuery(String query) {
/*  432 */     debugFile("setQuery: " + query);
/*      */     
/*  434 */     closeStatement();
/*      */     
/*  436 */     getDataSourceConnnection();
/*      */ 
/*      */     
/*  439 */     this.query = query;
/*      */ 
/*      */     
/*  442 */     this.nextThrewException = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setQuery(String query, boolean isPreparedStatement) {
/*  455 */     debugFile("setQuery: " + query);
/*      */     
/*  457 */     this.query = query;
/*      */     
/*  459 */     closeStatement();
/*      */     
/*  461 */     getDataSourceConnnection();
/*      */ 
/*      */     
/*  464 */     if (isPreparedStatement) {
/*  465 */       createPreparedStatement();
/*      */     }
/*      */     
/*  468 */     this.nextThrewException = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  477 */   public String getQuery() { return this.query; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setObject(int parm, Object object) {
/*      */     try {
/*  487 */       this.preparedStatement.setObject(parm, object);
/*      */     }
/*  489 */     catch (Exception sqlexc) {
/*  490 */       System.out.println("Exception while setObject() " + sqlexc.getMessage());
/*  491 */       debugFile("Exception while gathering field names: " + sqlexc);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  510 */   public void setReadOnly(boolean readOnly) { this.readOnly = readOnly; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  518 */   public boolean getReadOnly() { return this.readOnly; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  534 */   public void setForwardOnly(boolean forwardOnly) { this.forwardOnly = forwardOnly; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  542 */   public boolean getForwardOnly() { return this.forwardOnly; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void first() {
/*  554 */     if (this.forwardOnly) {
/*      */       
/*  556 */       debugFile("call to first on a forward-only query.");
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*      */     try {
/*  562 */       this.nextThrewException = !this.resultSet.first();
/*      */     }
/*  564 */     catch (Exception exc) {
/*      */       
/*  566 */       debugFile("Exception on first(): " + exc);
/*  567 */       this.nextThrewException = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void next() {
/*      */     try {
/*  578 */       this.nextThrewException = !this.resultSet.next();
/*      */     }
/*  580 */     catch (Exception exc) {
/*      */       
/*  582 */       debugFile("Next exception " + exc.getMessage());
/*  583 */       this.nextThrewException = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  595 */   public boolean more() { return !this.nextThrewException; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void moveAbsolute(int position) {
/*  605 */     if (this.forwardOnly) {
/*      */       
/*  607 */       debugFile("call to moveAbsolute on a forward-only query.");
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*      */     try {
/*  614 */       this.resultSet.absolute(position + 1);
/*      */     }
/*  616 */     catch (Exception exc) {
/*      */       
/*  618 */       debugFile("Exception while moving to absolute position " + position + ": " + exc);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRowNumber() {
/*      */     try {
/*  630 */       return this.resultSet.getRow();
/*      */     }
/*  632 */     catch (Exception exc) {
/*      */       
/*  634 */       debugFile("Exception while getting row number: " + exc);
/*      */ 
/*      */       
/*  637 */       return 0;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] getFieldNames() {
/*      */     try {
/*  647 */       ResultSetMetaData metadata = this.resultSet.getMetaData();
/*  648 */       int fieldCount = metadata.getColumnCount();
/*      */ 
/*      */       
/*  651 */       String[] toReturn = new String[fieldCount];
/*  652 */       for (int i = 1; i < fieldCount + 1; i++)
/*      */       {
/*  654 */         toReturn[i - 1] = metadata.getColumnName(i);
/*      */       }
/*      */       
/*  657 */       return toReturn;
/*      */     }
/*  659 */     catch (Exception exc) {
/*      */       
/*  661 */       debugFile("Exception while gathering field names: " + exc);
/*      */       
/*  663 */       return new String[0];
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int[] getFieldTypes() {
/*      */     try {
/*  673 */       ResultSetMetaData metadata = this.resultSet.getMetaData();
/*  674 */       int fieldCount = metadata.getColumnCount();
/*      */ 
/*      */       
/*  677 */       int[] toReturn = new int[fieldCount];
/*  678 */       for (int i = 1; i < fieldCount + 1; i++)
/*      */       {
/*  680 */         toReturn[i - 1] = metadata.getColumnType(i);
/*      */       }
/*      */       
/*  683 */       return toReturn;
/*      */     }
/*  685 */     catch (Exception exc) {
/*      */       
/*  687 */       debugFile("Exception while gathering field names: " + exc);
/*      */       
/*  689 */       return new int[0];
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getFieldByName(String fieldName) {
/*      */     try {
/*  701 */       return this.resultSet.getString(fieldName);
/*      */     }
/*  703 */     catch (Exception exc) {
/*      */       
/*  705 */       debugFile("Exception while retrieving field " + fieldName + ": " + exc);
/*  706 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getField(String fieldName) {
/*  718 */     String result = getFieldByName(fieldName);
/*  719 */     if (result != null) {
/*  720 */       return result;
/*      */     }
/*  722 */     return "[none]";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getField(String fieldName, String defaultValue) {
/*  734 */     String result = getFieldByName(fieldName);
/*  735 */     if (result != null) {
/*  736 */       return result;
/*      */     }
/*  738 */     return defaultValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getIntegerFieldByName(String fieldName) {
/*      */     try {
/*  751 */       return this.resultSet.getInt(fieldName);
/*      */     }
/*  753 */     catch (Exception exc) {
/*      */       
/*  755 */       return 0;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  767 */   public int getIntegerField(String fieldName) { return getIntegerFieldByName(fieldName); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  778 */   public int getInt(String fieldName) { return getIntegerFieldByName(fieldName); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getInt(String fieldName, int defaultValue) {
/*      */     try {
/*  791 */       return this.resultSet.getInt(fieldName);
/*      */     }
/*  793 */     catch (Exception exc) {
/*      */       
/*  795 */       return defaultValue;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Date getDate(String fieldName) {
/*      */     try {
/*  810 */       return this.resultSet.getDate(fieldName);
/*      */     }
/*  812 */     catch (Exception exc) {
/*      */       
/*  814 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Timestamp getTimestamp(String fieldName) {
/*      */     try {
/*  828 */       return this.resultSet.getTimestamp(fieldName);
/*      */     }
/*  830 */     catch (Exception exc) {
/*      */       
/*  832 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getBoolean(String fieldName) {
/*      */     try {
/*  847 */       return this.resultSet.getBoolean(fieldName);
/*      */     }
/*  849 */     catch (Exception exc) {
/*      */       
/*  851 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getFloat(String fieldName) {
/*      */     try {
/*  865 */       return this.resultSet.getFloat(fieldName);
/*      */     }
/*  867 */     catch (Exception exc) {
/*      */       
/*  869 */       return 0.0F;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getDouble(String fieldName) {
/*      */     try {
/*  883 */       return this.resultSet.getDouble(fieldName);
/*      */     }
/*  885 */     catch (Exception exc) {
/*      */       
/*  887 */       return 0.0D;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte getByte(String fieldName) {
/*      */     try {
/*  901 */       return this.resultSet.getByte(fieldName);
/*      */     }
/*  903 */     catch (Exception exc) {
/*      */       
/*  905 */       return 0;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public short getShort(String fieldName) {
/*      */     try {
/*  919 */       return this.resultSet.getShort(fieldName);
/*      */     }
/*  921 */     catch (Exception exc) {
/*      */       
/*  923 */       return 0;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getLong(String fieldName) {
/*      */     try {
/*  937 */       return this.resultSet.getLong(fieldName);
/*      */     }
/*  939 */     catch (Exception exc) {
/*      */       
/*  941 */       return 0L;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRowCount() {
/*  955 */     if (this.resultSet != null) {
/*      */ 
/*      */       
/*  958 */       if (this.rowCount > -1) {
/*  959 */         return this.rowCount;
/*      */       }
/*  961 */       this.rowCount = 0;
/*      */       
/*      */       try {
/*  964 */         this.resultSet.last();
/*  965 */         this.rowCount = this.resultSet.getRow();
/*      */       }
/*  967 */       catch (Exception le) {
/*      */         
/*  969 */         debugFile("Exception while going to last row for count: " + le);
/*      */       } 
/*      */       
/*  972 */       first();
/*      */       
/*  974 */       return this.rowCount;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  979 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void closeStatement() {
/*  989 */     if (this.resultSet != null) {
/*      */ 
/*      */       
/*      */       try {
/*  993 */         this.resultSet.close();
/*      */       }
/*  995 */       catch (Exception sqlexc) {
/*      */         
/*  997 */         debugFile("DATASOURCE ERROR....Exception while closing result set: " + sqlexc, 3);
/*      */       } 
/*  999 */       this.resultSet = null;
/*      */     } 
/*      */ 
/*      */     
/* 1003 */     if (this.preparedStatement != null)
/*      */     {
/* 1005 */       if (this.preparedStatement != null) {
/*      */ 
/*      */         
/*      */         try {
/* 1009 */           this.preparedStatement.close();
/*      */         }
/* 1011 */         catch (Exception sqlexc) {
/*      */           
/* 1013 */           debugFile("Exception while closing statement: " + sqlexc);
/*      */         } 
/* 1015 */         this.preparedStatement = null;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 1020 */     if (this.statement != null) {
/*      */ 
/*      */       
/*      */       try {
/* 1024 */         this.statement.close();
/*      */       }
/* 1026 */       catch (Exception sqlexc) {
/*      */         
/* 1028 */         debugFile("Exception while closing statement: " + sqlexc);
/*      */       } 
/* 1030 */       this.statement = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void close() {
/* 1041 */     closeStatement();
/*      */ 
/*      */     
/* 1044 */     if (this.connection != null) {
/*      */ 
/*      */       
/*      */       try {
/* 1048 */         this.connection.close();
/*      */       }
/* 1050 */       catch (Exception sqlexc) {
/*      */         
/* 1052 */         debugFile("Exception while closing connection: " + sqlexc);
/*      */       } 
/* 1054 */       this.connection = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int runUpdateQuery() {
/* 1069 */     getDataSourceConnnection();
/*      */ 
/*      */     
/* 1072 */     this.rowCount = -1;
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1077 */       if (this.preparedStatement == null) {
/* 1078 */         generateStatement();
/*      */       }
/* 1080 */       generateResultsUpdate();
/*      */     
/*      */     }
/* 1083 */     catch (SQLException sqlexc) {
/*      */       
/* 1085 */       debugFile("SQLException in generateResults: " + sqlexc);
/* 1086 */       debugFile("SQLState: " + sqlexc.getSQLState());
/* 1087 */       System.out.println("SQLException in generateResults: " + sqlexc.getMessage());
/* 1088 */       System.out.println("SQLState: " + sqlexc.getSQLState());
/*      */       
/* 1090 */       this.resultSet = null;
/* 1091 */       this.preparedStatement = null;
/* 1092 */       this.statement = null;
/*      */     } 
/*      */ 
/*      */     
/* 1096 */     return this.resultInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void loadDriver(String className, String urlPrefix, boolean supportsAbsolute, boolean supportsGetRow, boolean jdbc1, int pooling) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setDefaultJdbcURLPrefix(String newPrefix) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1118 */   public void debugFile(String debugString) { debugFile(debugString, 1); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void debugFile(String debugString, int debugSeverity) {
/* 1128 */     if (debugLevel <= debugSeverity)
/*      */     {
/* 1130 */       if ("d:\\bea\\milestone\\logs\\JdbcConnector-debug.txt" != null) {
/*      */         
/*      */         try {
/*      */ 
/*      */ 
/*      */           
/* 1136 */           if (queryNumber % 500L == 0L) {
/*      */             
/* 1138 */             File file = new File("d:\\bea\\milestone\\logs\\JdbcConnector-debug.txt");
/* 1139 */             if (file.exists())
/*      */             {
/* 1141 */               if (file.length() > 2000000L)
/*      */               {
/* 1143 */                 file.delete();
/*      */               }
/*      */             }
/*      */           } 
/*      */           
/* 1148 */           String code = "H";
/* 1149 */           if (debugSeverity == 2) {
/* 1150 */             code = "M";
/* 1151 */           } else if (debugSeverity == 1) {
/* 1152 */             code = "L";
/*      */           } 
/* 1154 */           Date date = new Date();
/* 1155 */           String toWrite = "JC " + 
/* 1156 */             DEBUG_DATE_FORMAT.format(date) + 
/* 1157 */             " " + "jdbc" + 
/* 1158 */             ": [" + code + "] " + debugString;
/*      */           
/* 1160 */           FileWriter fileWriter = new FileWriter("d:\\bea\\milestone\\logs\\JdbcConnector-debug.txt", true);
/* 1161 */           fileWriter.write(String.valueOf(toWrite) + "\r\n");
/* 1162 */           fileWriter.flush();
/* 1163 */           fileWriter.close();
/*      */ 
/*      */ 
/*      */         
/*      */         }
/* 1168 */         catch (IOException iOException) {}
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1183 */   public void setMaxRows(int maxRows) { this.maxRows = maxRows; }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\JdbcConnector.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */