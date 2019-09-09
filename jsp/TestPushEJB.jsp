<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Final//EN">
<%@ page import="java.sql.*, javax.sql.*, javax.naming.*, java.util.Date, java.text.DateFormat " %>
<%@ page import="javax.sql.DataSource, java.util.Properties, java.io.*" %>
<%@ page import="java.util.*, com.universal.milestone.*, com.universal.milestone.push.*" %>

<%! Connection conn;  Context ctx; int seqNo; String emailMessage = ""; String emailSubject = ""; %>

<%! public void jspInit() {
      // TEST EJB
     // TestEJB();
    }


    public void jspDestroy() {
    }


%>

<HTML>

<script language="javascript">


</script>

<%
  // public void TestEJB() {

      String strReply = "";

      //get bean and send message
      PushHome pushHome = null;
      PushRemote pushRemote = null;

      out.clear();

      //System.out.println("got here 1");
      try {
        Properties h = new Properties();
        h.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY,"weblogic.jndi.WLInitialContextFactory");
        h.put(javax.naming.Context.PROVIDER_URL,"t3://localhost:7001");
        javax.naming.Context initial = new InitialContext(h);

        //System.out.println("got here 2");

        pushHome = (PushHome)initial.lookup("PushBean");
        //home = (PushHome)Naming.lookup("//localhost:7001/PushBean");
        if (pushHome == null)
        {
           //System.out.println("NULL PushHome.");
           System.out.println("** Unable to find EJB home object for (PushBean) **" );   // msc 1/14/03
           out.println("** Unable to find EJB home object for (PushBean) **" );   // msc 1/14/03
        }
        else
        {
          //System.out.println("PushHome not null.");
          pushRemote = pushHome.create();
          if(pushRemote == null)
          {
            System.out.println("** Unable to create EJB remote object for (PushBean) **" );   // msc 1/14/03
            out.println("** Unable to create EJB remote object for (PushBean) **" );   // msc 1/14/03
          } else {
            System.out.println("** PushBean EJB remote object create **" );   // msc 1/14/03
            System.out.println(pushRemote.TestEJB());
            out.println("Returned from EJB: " + pushRemote.TestEJB());
          }
         }

       }
       catch (Exception e) {
         System.out.println(e.getMessage());
        }
  // }

 %>

<BODY BGCOLOR="lavender" onLoad="javascript:refreshFunc = setInterval('submitForm()', 60000)" >


<FORM NAME="TestEJB" ACTION="<%=request.getRequestURI() %>" METHOD="POST">

  <INPUT TYPE="submit" onclick="Submit()" VALUE="Test EJB">


</FORM>

<script>
 function Submit()
 {
  //alert("test");
  document.forms[0].submit();  // submit form
 }
</script>

</BODY>
</HTML>
