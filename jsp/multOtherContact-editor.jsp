<%
   /* This variable holds the value to be placed in <title> html tag </title>
      for the include file: template-top-html.jsp */
   String htmlTitle = "multiple Other Contact Editor screen";

%>

<%@ include file="template-top-page.shtml"%>
<%@ include file="template-top-html.shtml"%>



<link rel="stylesheet" type="text/css" href="<%= inf.getHtmlDirectory() %>global.css" title="GlobalStyle">
<script>
var iSize = 0;
function submitDelete( id )
{
    document.all.multOtherContactsPK.value = id;
    document.forms[0].cmd.value = "selection-multOtherContact-delete";
    document.forms[0].submit();
} //end function submitGet()


function submitSave()
{
    document.forms[0].cmd.value = "selection-multOtherContact-save";
    document.forms[0].submit();

} //end function saveComment()

function submitAdd()
{
  document.forms[0].cmd.value = "selection-multOtherContact-add";
  document.forms[0].submit();
} //end function saveComment()

function submitCancel()
{
  document.forms[0].cmd.value = "selection-multOtherContact-cancel";
  document.forms[0].submit();
} //end function saveComment()


</script>


</head>

 <body topmargin=0 leftmargin=0 >

<%= form.renderStart()%>
<%=form.getElement("cmd")%>

<input type="hidden" name="multOtherContactsPK" value="-1">

<table width="100%" height="100%" border="1" cellspacing="0" cellpadding="1">
<tr bgcolor="wheat" valign="top" >
<td>
	<table width="300" border="1" cellspacing="0" cellpadding="1">
	<tr class=label valign="bottom" align="middle" height="20" bgcolor="#CCCCCC">
		<td class=label align="left" width="80">Name</td>
		<td class=label align="left" width="100">Description</td>
                <td class=label align="left" width="20">&nbsp;</td>
	</tr>

<%
Selection selection = (Selection)context.getSessionValue("Selection");
Vector multOtherContacts = selection.getMultOtherContacts();

if (multOtherContacts != null) { %>
  <script>iSize = <%=multOtherContacts.size()%></script>
  <% for (int i=0;i < multOtherContacts.size();i++)
  {
    %>
    <tr height="30">
    	<td align="left"><%=form.getElement("name" + i)%></td>
    	<td align="left"><%=form.getElement("description" + i)%></td>
    	<td align="left"><a href="javascript:submitDelete(<%=i%>)" >del</a></td>
    </tr>
    <%
  }
}
%>

	</table>
</td></tr>
</table>


<%=form.renderEnd()%>


<%@ include file="include-bottom-html.shtml"%>
