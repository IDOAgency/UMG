<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Final//EN">
<%@ page import="java.sql.*, javax.sql.*, javax.naming.*, java.util.Date, java.text.DateFormat " %>
<%@ page import="javax.sql.DataSource, java.util.Properties, java.io.*" %>
<%@ page import="java.util.*, com.universal.milestone.*" %>

<%! Connection conn;  Context ctx; int seqNo; String emailMessage = ""; String emailSubject = ""; %>

<%! public void jspInit() {
      // get database connection
      conn = getConnection();
    }

    public Connection getConnection() {


      Connection sConn = null;

      try {

       String filename = "milestone.conf";
       // Get the configuration file from the classpath
       InputStream in = ClassLoader.getSystemResourceAsStream(filename);

       // Open file.
       if (in == null)
         in = new FileInputStream(filename);

        Properties defaultProps = new Properties();
        defaultProps.load(in);
        String jdbcUrlPrefix = defaultProps.getProperty("JDBCURLPrefix");
        String databaseName  = defaultProps.getProperty("DatabaseName");
        this.emailMessage  = defaultProps.getProperty("EmailMessage");
        this.emailSubject  = defaultProps.getProperty("EmailSubject");
        in.close();

         //load the JDBC driver
         Properties props = new Properties();
         props.setProperty("user", "milestone");
         props.setProperty("password", "milestone");
         props.setProperty("className", "com.ashna.jturbo.driver.Driver");
         Class.forName(props.getProperty("className"));

         String url = "";
         if( jdbcUrlPrefix != null && databaseName != null)
            url = jdbcUrlPrefix + databaseName;

         //System.out.println("<<< Db Properties " + url);

         sConn = java.sql.DriverManager.getConnection(url, props);


      } catch (Exception ex) {
        System.out.println("<<< Jsp Exception " + ex.getMessage());
      } finally {
        return sConn;
      }


    }


    public Connection getConnectionDS() {

      String DATASOURCE = "Milestone";
      Hashtable env = new Hashtable();
      env.put(Context.INITIAL_CONTEXT_FACTORY,"weblogic.jndi.WLInitialContextFactory");
      env.put(Context.PROVIDER_URL,"t3://localhost:7003");
      DataSource ds = null;
      Connection sConn = null;
      try {
        ctx = new InitialContext(env);
        ds   = (DataSource) ctx.lookup (DATASOURCE);
        sConn = ds.getConnection();
      } catch (Exception ex) {
        System.out.println("<<< Jsp Exception " + ex.getMessage());
      } finally {
        return sConn;
      }

    }


    public void jspDestroy() {
      try{
       conn.close();
      } catch(SQLException e) {}
    }
%>
<HTML>

<script language="javascript">

  var message =  "<%= emailMessage %>";
  var okToSend = false;
  var refreshFunc;

  function submitForm()
  {
    document.forms[0].submit();
  }

  function buildCheckbox(num)
  {
    var name = 'checkbox' + num;
    var str = '<td><input type="checkbox" name="' + name + '" value="1"></td>'
    document.write(str);
    //document.write(name);
  }

  function submitMsg()
  {

    if( document.all.emailMsg.value == "" )
      newMsg = message;
    else
      newMsg = document.all.emailMsg.value;

    var msg = prompt("Enter Email Message", newMsg);
    if(msg != null)
      document.all.emailMsg.value = msg;
  }

  function submitSel()
  {

    var len = document.all.SESSION.length;   // number of elements in the form
    var frm = document.all.SESSION;          // form

    // toggle switch
    if( document.all.toggleSel.value == "0")
      document.all.toggleSel.value = "1";
    else
      document.all.toggleSel.value = "0";

    // update check boxes
    for(var i=0; i < len; i++)
    {
      var f  = frm.elements[i];
      // update the check box
      if( f.name.substr(0,8) == "checkbox" )
      {
        if( document.all.toggleSel.value == "1" )  {
          f.checked = true;
        } else {
          f.checked = false;
        }
      }
    }

    document.forms[0].submit();  // submit form
  }

  function submitSend()
  {

    var checked = false;

    // check for checked check boxes
    var len = document.all.SESSION.length;   // number of elements in the form
    var frm = document.all.SESSION;          // form

    for(var i=0; i < len; i++)
    {
      var f  = frm.elements[i];
      if( f.name.substr(0,8) == "checkbox" && f.checked )
      {
        checked = true;
        break;
      }
    }

    // if nothing checked alert message
    if( checked ) {
      if( confirm("Are you sure?") ) {

        clearInterval(refreshFunc);  // turn the refresh function off temporarily

        document.all.okToSend.value = "true";
        document.forms[0].submit();  // submit form
      }
    } else {
      alert("No emails selected to send: \n");

    }
  }



</script>

<BODY BGCOLOR="lavender" onLoad="javascript:refreshFunc = setInterval('submitForm()', 60000)" >

<%  Date curDate = new Date(); %>

<div align="center">
<h1><span><b>Milestone User Session Display</b></span></h1>
<div style="color:blue">  <%= curDate %></div>
</div>


<FORM NAME="SESSION" ACTION="<%=request.getRequestURI() %>" METHOD="POST">


<td nowrap><b>Login Date:</b></td>

<INPUT TYPE="text" name="date" size="10" MAXLENGTH="10"
       VALUE="<% if (request.getParameter("date") != null)
                   out.print(request.getParameter("date"));
                 else
                   out.print( DateFormat.getDateInstance(DateFormat.SHORT).format(curDate));
              %>">

<% String mode = request.getParameter("mode"); %>
<td><b>&nbspSession Mode</b></td>

<select name="mode" onchange="javascript:submitForm()" ><option value="Active">Active<option value="All">All</select>

<INPUT TYPE="hidden" NAME="emailMsg"  value="<% if (request.getParameter("emailMsg") != null )
                                                   out.print(request.getParameter("emailMsg"));
                                                 else
                                                   out.print(""); %>">

<INPUT TYPE="hidden" NAME="toggleSel" value="<% if (request.getParameter("toggleSel") != null )
                                                   out.print(request.getParameter("toggleSel"));
                                                 else
                                                   out.print("0"); %>">
<INPUT TYPE="hidden" NAME="okToSend" value="<% if (request.getParameter("okToSend") != null )
                                                   out.print(request.getParameter("okToSend"));
                                                 else
                                                   out.print("false"); %>">


<INPUT TYPE="submit" VALUE="Refresh">
<INPUT TYPE="button" VALUE="Toggle Select" onclick="javascript:submitSel()">
<INPUT TYPE="button" VALUE="Message" onclick="javascript:submitMsg()">
<INPUT TYPE="button" NAME="sendMail" VALUE="Send" onclick="javascript:submitSend()">
<p></p>
<TABLE BORDER="1" CELLPADDING="3" CELLSPACING="2">


   <TR bgcolor="wheat">
       <TD><B>#</B></TD>
       <TD><B>User Name</B></TD>
       <TD><B>User IP</B></TD>
       <TD><B>Server</B></TD>
       <TD><B>Log In Date</B></TD>
       <TD><B>Log Out Date</B></TD>
       <TD><B>Email Address</B></TD>
       <TD><B>Send</B></TD>
       <TD><B>Phone Number</B></TD>
   </TR>
<%
    String searchDate = null;
    if (request.getParameter("date") != null)
       searchDate = request.getParameter("date");
    else
      searchDate = DateFormat.getDateInstance(DateFormat.SHORT).format(curDate);

    String userMode   = request.getParameter("mode");

    StringBuffer sql  = new StringBuffer("SELECT * FROM User_Session ");
    sql.append(" JOIN vi_User ON userId = user_Id ");

    sql.append(" where sessionActiveDate >= ? ");

    if( userMode == null || (userMode != null && userMode.equalsIgnoreCase("Active")) ) {
      sql.append(" and sessionInActiveDate IS NULL ");
    }

    sql.append(" ORDER BY sessionActiveDate, userName ");

    try {

        if( conn == null)
          conn = getConnection();

        PreparedStatement stmt = conn.prepareStatement(sql.toString());
        stmt.setString(1, searchDate );

        ResultSet rset = stmt.executeQuery();
        ArrayList emailList = new ArrayList();
        seqNo = 0;

        while (rset.next()){
         %>
          <TR bgcolor="white" style="FONT-SIZE: 8pt;FONT-FAMILY: Arial,Helvetica;">
          <TD><%= ++seqNo %></TD>
          <TD><%= rset.getString("userName") %></TD>
          <TD><%= rset.getString("userIP") %></TD>
          <TD><%= rset.getString("serverName") %></TD>
          <TD><%= rset.getString("sessionActiveDate") %></TD>
          <TD><%= rset.getString("sessionInActiveDate") %></TD>
          <TD><A NAME="<%out.print("anchor" + seqNo);%>">
                <%= rset.getString("email") %></A></TD>

           <% String ckName = "checkbox" + seqNo;
              String isChecked = request.getParameter(ckName);
              String emailStr = rset.getString("email");
           %>
            <td align="center"><input type="checkbox" name="<% out.print(ckName); %>"
                   <% if(emailStr != null && !emailStr.equals("") && emailStr.indexOf("@") != -1
                        && isChecked != null && isChecked.equals("1")) out.print("checked"); %>
                   <% if(emailStr == null || emailStr.equals("") || emailStr.indexOf("@") == -1)
                            out.print("disabled"); %>
                       value="1"></td>

           <TD><%= rset.getString("phone") %></TD>
           </TR>

          <%
           // add to email string and checked value to ArrayList
           if( emailStr != null && !emailStr.equals("") && isChecked != null && isChecked.equals("1") )
           {
             SessionUserEmailObj emailObj = new SessionUserEmailObj(emailStr, true);
             emailList.add(emailObj);
             emailObj = null;
           }
       }

       request.getSession().setAttribute("emailList", emailList);  // store email object
       request.getSession().setAttribute("emailMsg", request.getParameter("emailMsg") != null ? request.getParameter("emailMsg") : "");
       emailList = null;
       stmt.close();

     } catch(SQLException e) {
        System.out.println("<<< Sql Error " + e.getMessage());
        conn = null;
     }
%>
</TABLE>
</FORM>

<script>
<% if (mode != null &&  mode.equalsIgnoreCase("All") ) { %>
      document.all.mode.options[1].selected = true
     <%} else {%>
      document.all.mode.options[0].selected = true;
<% } %>

  //////////////////////////////////////////////////////////////////////////////////////
  // send email messages
  //////////////////////////////////////////////////////////////////////////////////////
  <%
  String okToSend = request.getParameter("okToSend");
  if( okToSend != null && okToSend.equalsIgnoreCase("true") ) {

    ArrayList eList = (ArrayList)request.getSession().getAttribute("emailList");  // get email List

    if(eList != null) {

      String  eMsg = (String)request.getSession().getAttribute("emailMsg");
      if( eMsg == null || eMsg.equals("") ) {
        eMsg = emailMessage;
      }
      if( this.emailSubject == null || this.emailSubject.equals("") ) {
        this.emailSubject = "Milestone Email Notification";
      }

       SessionUserEmail emailSendObj = (SessionUserEmail)request.getSession().getAttribute("emailSendObj");

       if( emailSendObj == null)
          emailSendObj = new SessionUserEmail();   // create new email object

       ArrayList cc = new ArrayList();
       boolean result = emailSendObj.sendEmail(eList, eMsg, this.emailSubject, "", cc);  // send email with message text

       request.getSession().setAttribute("emailSendObj", emailSendObj);  // store email Send object

       emailSendObj = null;

       if( result ) {
          %> var status = true; <%
       } else {
          %> var status = false;
       <%}%>

       // update alert message
       msgStr = "<%= eMsg %>";

       // clear the check boxes
       var len = document.all.SESSION.length;   // number of elements in the form
       var frm = document.all.SESSION;          // form

      // uncheck check boxes
      for(var i=0; i < len; i++)
      {
        var f  = frm.elements[i];
        if( f.name.substr(0,8) == "checkbox" )
          f.checked = false;
      }
      // display status message
      if( status )
        alert("Message sent successfully: \n\n" +  msgStr);
      else
        alert("Some or All messages failed to send...check error log: \n\n" +  msgStr);
    <%
    }
  }%>


 // reset indicators
 document.all.okToSend.value = "false";

</script>

</BODY>
</HTML>
