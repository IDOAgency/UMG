<%
   /* This variable holds the value to be placed in <title> html tag </title>
      for the include file: template-top-html.jsp */
   String htmlTitle = "multiple Selection Editor screen";

%>

<%@ include file="template-top-page.shtml"%>
<%@ include file="template-top-html.shtml"%>



<link rel="stylesheet" type="text/css" href="<%= inf.getHtmlDirectory() %>global.css" title="GlobalStyle">
<script>
var iSize = 0;
function submitDelete( id )
{
    // parent.top.bottomFrame.MultSelectionFrame.MultSelectionEditor.location = "<%= inf.getServletCmdURL("selection-multSelection-delete")%>&multSelectionsPK=" + id;
    document.all.multSelectionsPK.value = id;
    document.forms[0].cmd.value = "selection-multSelection-delete";
    document.forms[0].submit();
} //end function submitGet()


function submitSave()
{
    document.forms[0].cmd.value = "selection-multSelection-save";
    document.forms[0].submit();

} //end function saveComment()

function submitAdd()
{
  document.forms[0].cmd.value = "selection-multSelection-add";
  document.forms[0].submit();
} //end function saveComment()

function submitCancel()
{
  document.forms[0].cmd.value = "selection-multSelection-cancel";
  document.forms[0].submit();
} //end function saveComment()


</script>
<script type="text/javascript" language="javascript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-upc-ssg-sgc.js"></script>


</head>

 <body topmargin=0 leftmargin=0 >

<%= form.renderStart()%>
<%=form.getElement("cmd")%>

<input type="hidden" name="multSelectionsPK" value="-1">

<table width="100%" height="100%" border="1" cellspacing="0" cellpadding="1">
<tr bgcolor="wheat" valign="top" >
<td>
	<table width="300" border="1" cellspacing="0" cellpadding="1">
	<tr class=label valign="bottom" align="middle" height="20" bgcolor="#CCCCCC">
		<!-- JR ITS 295 -->
                <td class=label align="left" width="20">&nbsp;</td>
		<td class=label align="left" width="80">Component Local Prod #</td>
		<td class=label align="left" width="80">Component UPC</td>
		<td class=label align="left" width="100">Component Description</td>
	</tr>

<%
Selection selection = (Selection)context.getSessionValue("Selection");
Vector multSelections = selection.getMultSelections();

if (multSelections != null) { %>
  <script>iSize = <%=multSelections.size()%></script>
  <% for (int i=0;i < multSelections.size();i++)
  {
    %>
    <tr height="30">
   	<td align="left"><a href="javascript:submitDelete(<%=i%>)" >del</a></td>
    	<td align="left"><%=form.getElement("selectionNo" + i)%></td>
    	<td align="left"><%=form.getElement("upc" + i)%></td>
    	<td align="left"><%=form.getElement("description" + i)%></td>
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
