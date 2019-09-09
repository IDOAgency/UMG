/*     */ package WEB-INF.classes.com.universal.milestone.b2b;public class SqlXML { StringBuffer xmlStr; Connection connection;
/*     */   DataSource datasource;
/*     */   Statement statement;
/*     */   ResultSet resultSet;
/*     */   Context ctx;
/*     */   Connection con;
/*     */   DataSource datasource2;
/*     */   Statement statement2;
/*     */   ResultSet resultSet2;
/*     */   Context ctx2;
/*     */   String DATASOURCE;
/*     */   
/*     */   public SqlXML() {
/*  14 */     this.xmlStr = new StringBuffer();
/*  15 */     this.connection = null;
/*  16 */     this.datasource = null;
/*  17 */     this.statement = null;
/*  18 */     this.resultSet = null;
/*  19 */     this.ctx = null;
/*  20 */     this.con = null;
/*  21 */     this.datasource2 = null;
/*  22 */     this.statement2 = null;
/*  23 */     this.resultSet2 = null;
/*  24 */     this.ctx2 = null;
/*     */     
/*  26 */     this.DATASOURCE = null;
/*  27 */     this.INITIAL_CONTEXT_FACTORY = null;
/*  28 */     this.PROVIDER_URL = null;
/*  29 */     this.B2BSERVERPATH = null;
/*  30 */     this.B2BPATH = null;
/*  31 */     this.WSDLPATH = null;
/*  32 */     this.JDBCPREFIX = null;
/*  33 */     this.DATABASELOGIN = null;
/*  34 */     this.DATABASEPASSWORD = null;
/*  35 */     this.B2BDATABASEPATH = null;
/*  36 */     this.B2BHOSTSERVER = null;
/*     */ 
/*     */     
/*  39 */     this.ilCharA = new String[] { "<", ">", "&", "'", "\"" };
/*  40 */     this.iReplace = new String[] { "&lt;", "&gt;", "&amp;", "&apos;", "&quot;" };
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  45 */     getConnection();
/*     */     try {
/*  47 */       this.con = DriverManager.getConnection(String.valueOf(this.JDBCPREFIX) + this.B2BDATABASEPATH, this.DATABASELOGIN, this.DATABASEPASSWORD);
/*  48 */       System.out.println("connection good");
/*  49 */     } catch (Exception e) {
/*  50 */       System.out.println("b2b getConnection failed");
/*  51 */       System.err.println("getConnection: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */   String INITIAL_CONTEXT_FACTORY; String PROVIDER_URL; String B2BSERVERPATH; String B2BPATH; String WSDLPATH; String JDBCPREFIX; String DATABASELOGIN; String DATABASEPASSWORD; String B2BDATABASEPATH; String B2BHOSTSERVER; String[] ilCharA; String[] iReplace;
/*     */   
/*     */   public String getSQLXML(String query) {
/*  57 */     String releaseID = "";
/*  58 */     String streetdate = "";
/*     */ 
/*     */     
/*  61 */     this.xmlStr.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
/*     */     
/*     */     try {
/*  64 */       this.statement = this.connection.createStatement(1004, 1007);
/*  65 */       this.resultSet = this.statement.executeQuery(query);
/*  66 */       ResultSetMetaData metaData = this.resultSet.getMetaData();
/*  67 */       this.resultSet.beforeFirst();
/*     */       
/*  69 */       this.xmlStr.append("\n<MilestoneFeed>");
/*     */ 
/*     */       
/*  72 */       this.xmlStr.append("\n\t<CreatedOn>");
/*  73 */       DateFormat myformat = new SimpleDateFormat("yyyy.MM.dd");
/*  74 */       this.xmlStr.append(myformat.format(new Date()));
/*  75 */       this.xmlStr.append("</CreatedOn>");
/*  76 */       this.xmlStr.append("\n\t<Data>");
/*  77 */       while (this.resultSet.next()) {
/*  78 */         this.xmlStr.append("\n\t\t<DataRow>");
/*     */         
/*  80 */         for (int x = 1; x <= metaData.getColumnCount(); x++) {
/*     */           
/*  82 */           if (metaData.getColumnName(x).equals("release_id")) {
/*     */             
/*  84 */             releaseID = this.resultSet.getObject(x).toString().trim();
/*     */             
/*  86 */             this.xmlStr.append("\n\t\t\t");
/*  87 */             this.xmlStr.append("<ReleaseWeekCycle>");
/*     */             
/*  89 */             if (getPreDates(streetdate) == null) {
/*  90 */               this.xmlStr.append("");
/*     */             } else {
/*  92 */               this.xmlStr.append("<![CDATA[" + getPreDates(streetdate).trim() + "]]>");
/*     */             } 
/*  94 */             this.xmlStr.append("</ReleaseWeekCycle>");
/*     */           } else {
/*     */             
/*  97 */             if (metaData.getColumnName(x).equals("StreetDate")) {
/*  98 */               streetdate = this.resultSet.getObject(x).toString().trim();
/*     */             }
/*     */ 
/*     */             
/* 102 */             this.xmlStr.append("\n\t\t\t");
/* 103 */             this.xmlStr.append("<" + metaData.getColumnName(x) + ">");
/*     */             
/* 105 */             if (metaData.getColumnName(x).equals("ImpactDate")) {
/* 106 */               this.xmlStr.append("\n\t\t\t\t<SingleImpactDate>");
/*     */             }
/*     */             
/* 109 */             if (this.resultSet.getObject(x) == null) {
/* 110 */               this.xmlStr.append("");
/*     */             } else {
/* 112 */               this.xmlStr.append("<![CDATA[" + this.resultSet.getObject(x).toString().trim() + "]]>");
/*     */             } 
/*     */             
/* 115 */             if (metaData.getColumnName(x).equals("ImpactDate")) {
/* 116 */               this.xmlStr.append("</SingleImpactDate>");
/* 117 */               this.xmlStr.append("\n\t\t\t\t<MultipleImpactDate>");
/* 118 */               this.xmlStr.append(printMultipleImpactDates(releaseID));
/* 119 */               this.xmlStr.append("\n\t\t\t\t</MultipleImpactDate>");
/* 120 */               this.xmlStr.append("\n\t\t\t</ImpactDate>");
/*     */             } else {
/* 122 */               this.xmlStr.append("</" + metaData.getColumnName(x) + ">");
/*     */             } 
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 128 */         this.xmlStr.append("\n\t\t</DataRow>");
/*     */       } 
/*     */       
/* 131 */       this.xmlStr.append("\n\t</Data>");
/* 132 */       this.xmlStr.append("\n</MilestoneFeed>");
/*     */ 
/*     */       
/* 135 */       this.con.close();
/* 136 */       this.connection.close();
/*     */     }
/* 138 */     catch (Exception sqlexc) {
/* 139 */       System.err.println("getSQLXML: " + sqlexc.getMessage());
/*     */     } 
/*     */     
/* 142 */     return this.xmlStr.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPreDates(String streetdate) {
/* 147 */     String dateDataString = "";
/*     */     try {
/* 149 */       CallableStatement cstmt = this.con.prepareCall("{call dbo.sp_getB2bReleaseWeekCycle(?, ?)}");
/* 150 */       cstmt.setString(1, streetdate);
/* 151 */       cstmt.registerOutParameter(2, 12);
/* 152 */       cstmt.executeQuery();
/* 153 */       dateDataString = cstmt.getString(2);
/* 154 */     } catch (Exception sqlexc) {
/* 155 */       System.err.println("dates error message: " + sqlexc.getMessage());
/*     */     } 
/* 157 */     return dateDataString;
/*     */   }
/*     */ 
/*     */   
/*     */   public String printMultipleImpactDates(String selectionID) {
/* 162 */     StringBuffer impactDateString = new StringBuffer();
/*     */ 
/*     */     
/* 165 */     String sqlstr2 = "select impactDate, b.sub_value as 'Format', tbi as 'TBI' from ImpactDates, lookup_subdetail b where selection_id = " + 
/* 166 */       selectionID + " and b.field_id = 58 and b.det_value = [format]";
/*     */     
/* 168 */     System.out.println("sqlstr2:[" + sqlstr2 + "]");
/*     */ 
/*     */     
/*     */     try {
/* 172 */       this.statement2 = this.connection.createStatement(1004, 1007);
/* 173 */       ResultSet resultSet2 = this.statement2.executeQuery(sqlstr2);
/* 174 */       ResultSetMetaData metaData = resultSet2.getMetaData();
/* 175 */       resultSet2.beforeFirst();
/*     */       
/* 177 */       while (resultSet2.next()) {
/* 178 */         for (int x = 1; x <= metaData.getColumnCount(); x++) {
/* 179 */           impactDateString.append("\n\t\t\t\t\t");
/* 180 */           impactDateString.append("<" + metaData.getColumnName(x) + ">");
/*     */           
/* 182 */           if (resultSet2.getObject(x) == null) {
/* 183 */             impactDateString.append("");
/*     */           } else {
/* 185 */             impactDateString.append("<![CDATA[" + resultSet2.getObject(x).toString().trim() + "]]>");
/*     */           } 
/* 187 */           impactDateString.append("</" + metaData.getColumnName(x) + ">");
/*     */         }
/*     */       
/*     */       } 
/* 191 */     } catch (Exception sqlexc) {
/* 192 */       System.err.println("impactDateString died because: " + sqlexc.getMessage());
/*     */     } 
/* 194 */     return impactDateString.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public void getConnection() {
/* 199 */     if (getConfig()) {
/*     */       try {
/* 201 */         Hashtable env = new Hashtable();
/* 202 */         env.put("java.naming.factory.initial", this.INITIAL_CONTEXT_FACTORY);
/* 203 */         env.put("java.naming.provider.url", this.PROVIDER_URL);
/* 204 */         InitialContext ctx = new InitialContext(env);
/* 205 */         this.datasource = (DataSource)ctx.lookup(this.DATASOURCE);
/* 206 */         this.connection = this.datasource.getConnection();
/* 207 */       } catch (Exception sqlexc) {
/* 208 */         System.err.println("Connection Exception: " + sqlexc.getMessage());
/* 209 */         sqlexc.printStackTrace();
/* 210 */         this.connection = null;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean getConfig() {
/*     */     try {
/* 217 */       System.out.println("getConfig1");
/* 218 */       String filename = "milestone.conf";
/*     */       
/* 220 */       InputStream in = ClassLoader.getSystemResourceAsStream(filename);
/*     */ 
/*     */       
/* 223 */       if (in == null) {
/* 224 */         in = new FileInputStream(filename);
/*     */       }
/* 226 */       Properties defaultProps = new Properties();
/* 227 */       defaultProps.load(in);
/*     */ 
/*     */       
/* 230 */       this.DATASOURCE = defaultProps.getProperty("DATASOURCE");
/* 231 */       this.INITIAL_CONTEXT_FACTORY = defaultProps.getProperty("INITIAL_CONTEXT_FACTORY");
/* 232 */       this.PROVIDER_URL = defaultProps.getProperty("PROVIDER_URL");
/* 233 */       this.B2BSERVERPATH = defaultProps.getProperty("b2bServerPath");
/* 234 */       System.out.println("b2bserverPath: " + this.B2BSERVERPATH);
/* 235 */       this.B2BPATH = defaultProps.getProperty("b2bPath");
/* 236 */       System.out.println("B2bPath: " + this.B2BPATH);
/* 237 */       this.WSDLPATH = defaultProps.getProperty("b2bWsdlPath");
/* 238 */       System.out.println("WsdlPath: " + this.WSDLPATH);
/* 239 */       this.JDBCPREFIX = defaultProps.getProperty("JDBCURLPrefix");
/* 240 */       System.out.println("JDBCPREFIX: " + this.JDBCPREFIX);
/* 241 */       this.DATABASELOGIN = defaultProps.getProperty("DatabaseLogin");
/* 242 */       System.out.println("DATABASELOGIN: " + this.DATABASELOGIN);
/* 243 */       this.DATABASEPASSWORD = defaultProps.getProperty("DatabasePassword");
/* 244 */       System.out.println("DATABASEPASSWORD: " + this.DATABASEPASSWORD);
/* 245 */       this.B2BDATABASEPATH = defaultProps.getProperty("b2bDatabasePath");
/* 246 */       System.out.println("DATABASEPATH: " + this.B2BDATABASEPATH);
/* 247 */       this.B2BHOSTSERVER = defaultProps.getProperty("b2bHostServer");
/* 248 */       System.out.println("B2BHOSTSERVER: " + this.B2BHOSTSERVER);
/*     */       
/* 250 */       System.out.println("datasource: " + this.DATASOURCE);
/* 251 */       System.out.println("InitCtxFac: " + this.INITIAL_CONTEXT_FACTORY);
/* 252 */       System.out.println("providerUrl: " + this.PROVIDER_URL);
/*     */       
/* 254 */       in.close();
/* 255 */       return true;
/* 256 */     } catch (Exception ex) {
/* 257 */       System.out.println("<<< Properties Exception " + ex.getMessage());
/*     */ 
/*     */       
/* 260 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String fixForHtml(String xmlstr) {
/* 266 */     String newStr = new String(this.xmlStr);
/*     */     
/* 268 */     for (int x = 0; x < this.ilCharA.length; x++) {
/*     */       
/* 270 */       int f = newStr.indexOf(this.ilCharA[x]);
/* 271 */       int b = 0;
/* 272 */       int e = 0;
/*     */       
/* 274 */       while (f != -1) {
/* 275 */         String hldStr = new String(newStr);
/*     */         
/* 277 */         if (f > 0) {
/* 278 */           newStr = newStr.substring(b, f - 1);
/*     */         } else {
/* 280 */           newStr = this.iReplace[x];
/*     */         } 
/* 282 */         e = f + 1;
/*     */         
/* 284 */         if (e < hldStr.length()) {
/* 285 */           newStr = String.valueOf(newStr) + hldStr.substring(e);
/*     */         }
/* 287 */         f = hldStr.indexOf(this.ilCharA[x], e);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 292 */     return newStr;
/*     */   } }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\b2b\SqlXML.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */