<%
   /* This variable holds the value to be placed in <title> html tag </title>
      for the include file: template-top-html.jsp */
   String htmlTitle = "impactDate screen";

%>

<%@ include file="template-top-page.shtml"%>
<%@ include file="template-top-html.shtml"%>



<link rel="stylesheet" type="text/css" href="<%= inf.getHtmlDirectory() %>global.css" title="GlobalStyle">
<script>
var iSize = 0;
function submitDelete( id )
{
	parent.top.bottomFrame.ImpactFrame.ImpactEditor.location = "<%= inf.getServletCmdURL("selection-impactDate-delete")%>&impactDateID=" + id;
  //document.forms[0].cmd.value = "selection-impactDate-delete&impactDateID=" + id;
  //document.forms[0].submit();
} //end function submitGet()

function submitSave()
{
    document.forms[0].cmd.value = "selection-impactDate-save";
    document.forms[0].submit();

} //end function saveComment()

function submitAdd()
{
  document.forms[0].cmd.value = "selection-impactDate-add";
  document.forms[0].submit();
} //end function saveComment()

function submitCancel()
{
  document.forms[0].cmd.value = "selection-impactDate-cancel";
  document.forms[0].submit();
} //end function saveComment()


</script>

<!-- msc Item 1948 - These are js include files holding the global java script functions -->
<script type="text/javascript" language="javascript" src="<%= inf.getHtmlDirectory() %>calendar.js"></script>
<!-- <script type="text/javascript" language="javascript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-convert-date.js"></script> -->
<!-- These are js include files holding the global java script functions -->


</head>

 <body topmargin=0 leftmargin=0 >

<%= form.renderStart()%>
<%=form.getElement("cmd")%>

<table width="100%" height="100%" border="1" cellspacing="0" cellpadding="1">
<tr bgcolor="wheat" valign="top" >
<td>
	<table width="249" border="1" cellspacing="0" cellpadding="1">
	<tr class=label valign="bottom" align="middle" height="20" bgcolor="#CCCCCC">
		<td class=label align="center" width="120">
		Format
		</td>
		<td class=label align="center" width="80">
		Impact<BR>
		Date
		</td>
		<td class=label align="center" width="20">
		TBI
		</td>
		<td class=label align="center" width="20">
		&nbsp;
		</td>
	</tr>

<%
Selection selection = (Selection)context.getSessionValue("Selection");
Vector impactDates = selection.getImpactDates();

if (impactDates != null) { %>
  <script>iSize = <%=impactDates.size()%></script>
  <% for (int i=0;i < impactDates.size();i++)
  {
    %>
    <tr height="30">
    	<td align="center">
    	<%=form.getElement("format" + i)%>
    	</td>
    	<td align="center">
    	<%=form.getElement("impactDate" + i)%>
    	</td>
    	<td align="center">
    	<%=form.getElement("tbi" + i)%>
    	</td>
    	<td align="center">
    		<a href="javascript:submitDelete(<%=i%>)" >del</a>
    	</td>
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
