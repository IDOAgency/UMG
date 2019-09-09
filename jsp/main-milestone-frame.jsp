<%@ include file="template-top-page.shtml"%>
<html>
<head>
  <title>Milestone</title>
  <meta http-equiv="X-UA-Compatible" content="IE=11" />
</head>
   <frameset rows="62,*,0" border="0" frameborder="no" >
     <frame name="menuFrame" src="home?cmd=main-top-menu" scrolling=no noresize marginwidth=0 marginheight=0>
     <%
     if (context.getSessionValue("lastLink") != null)
     {
       String lastLink = (String)context.getSessionValue("lastLink");
     %>
       <frame name="bottomFrame" src="home?cmd=<%=lastLink%>" scrolling="yes" noresize marginwidth=0 marginheight=0>
     <%
     }
     else
     {
     %>
       <frame name="bottomFrame" src="" scrolling="yes" noresize marginwidth=0 marginheight=0>
     <%
     }
     %>
     <frame name="hiddenFrame" src="" scrolling="no" noresize marginwidth=0 marginheight=0>
   </frame>
</html>
