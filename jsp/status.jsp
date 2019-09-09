<%@ include file="template-top-page.shtml"%>


<%
String percent = (context.getDelivery("percent") != null) ? (String)context.getDelivery("percent"): "";
if (percent.length() > 0)
{
//System.out.println("percent" + percent);
%>
<script>
//parent.frames[1].percent.value = "<%=percent%>";
</script>
<%
}
%>


<%
   String htmlTitle = "status";
%>
<%@ include file="template-top-html.shtml"%>

<script>
function clearMessages()
{
  parent.frames[1].printStatus0.style.visibility = "hidden";
  parent.frames[1].printStatus10.style.visibility = "hidden";
  parent.frames[1].printStatus20.style.visibility = "hidden";
  parent.frames[1].printStatus30.style.visibility = "hidden";
  parent.frames[1].printStatus40.style.visibility = "hidden";
  parent.frames[1].printStatus50.style.visibility = "hidden";
  parent.frames[1].printStatus60.style.visibility = "hidden";
  parent.frames[1].printStatus70.style.visibility = "hidden";
  parent.frames[1].printStatus80.style.visibility = "hidden";
  parent.frames[1].printStatus90.style.visibility = "hidden";
  parent.frames[1].printStatus100.style.visibility = "hidden";

  parent.frames[1].gathering0.style.visibility = "hidden";
  parent.frames[1].gathering10.style.visibility = "hidden";
  parent.frames[1].gathering20.style.visibility = "hidden";
  parent.frames[1].gathering30.style.visibility = "hidden";
  parent.frames[1].gathering40.style.visibility = "hidden";
  parent.frames[1].gathering50.style.visibility = "hidden";
  parent.frames[1].gathering60.style.visibility = "hidden";
  parent.frames[1].gathering70.style.visibility = "hidden";
  parent.frames[1].gathering80.style.visibility = "hidden";
  parent.frames[1].gathering90.style.visibility = "hidden";
  parent.frames[1].gathering100.style.visibility = "hidden";
  parent.frames[1].downloading.style.visibility = "hidden";
}
</script>

</head>

<%
String status = (context.getDelivery("status") != null) ? (String)context.getDelivery("status"): "";
%>

<body bgcolor=blue>


<%
if (status.equalsIgnoreCase("start_report"))
{
%>
<script>
parent.frames[1].gathering0.style.visibility = "hidden";
parent.frames[1].gathering10.style.visibility = "hidden";
parent.frames[1].gathering20.style.visibility = "hidden";
parent.frames[1].gathering30.style.visibility = "hidden";
parent.frames[1].gathering40.style.visibility = "hidden";
parent.frames[1].gathering50.style.visibility = "hidden";
parent.frames[1].gathering60.style.visibility = "hidden";
parent.frames[1].gathering70.style.visibility = "hidden";
parent.frames[1].gathering80.style.visibility = "hidden";
parent.frames[1].gathering90.style.visibility = "hidden";
parent.frames[1].gathering100.style.visibility = "hidden";

<%if (percent != null && percent.length() > 0 && (Integer.parseInt(percent) <= 100))
{%>
eval("parent.frames[1].printStatus" + <%=percent%> + ".style.visibility = 'visible';");
<%
}
else
{
%>
eval("parent.frames[1].printStatus" + 10 + ".style.visibility = 'visible';");
<%
}
%>

</script>
<%
}
else if (status.equalsIgnoreCase("done"))
{
%>
<script>
parent.frames[1].printStatus0.style.visibility = "hidden";
parent.frames[1].printStatus10.style.visibility = "hidden";
parent.frames[1].printStatus20.style.visibility = "hidden";
parent.frames[1].printStatus30.style.visibility = "hidden";
parent.frames[1].printStatus40.style.visibility = "hidden";
parent.frames[1].printStatus50.style.visibility = "hidden";
parent.frames[1].printStatus60.style.visibility = "hidden";
parent.frames[1].printStatus70.style.visibility = "hidden";
parent.frames[1].printStatus80.style.visibility = "hidden";
parent.frames[1].printStatus90.style.visibility = "hidden";
parent.frames[1].printStatus100.style.visibility = "hidden";

parent.frames[1].gathering0.style.visibility = "hidden";
parent.frames[1].gathering10.style.visibility = "hidden";
parent.frames[1].gathering20.style.visibility = "hidden";
parent.frames[1].gathering30.style.visibility = "hidden";
parent.frames[1].gathering40.style.visibility = "hidden";
parent.frames[1].gathering50.style.visibility = "hidden";
parent.frames[1].gathering60.style.visibility = "hidden";
parent.frames[1].gathering70.style.visibility = "hidden";
parent.frames[1].gathering80.style.visibility = "hidden";
parent.frames[1].gathering90.style.visibility = "hidden";
parent.frames[1].gathering100.style.visibility = "hidden";

parent.frames[1].downloading.style.visibility = "hidden";
parent.frames[1].percentage.style.visibility = "hidden";
</script>
<%
}
else if (status.equalsIgnoreCase("start_gathering"))
{
%>
<script>

parent.frames[1].printStatus0.style.visibility = "hidden";
parent.frames[1].printStatus10.style.visibility = "hidden";
parent.frames[1].printStatus20.style.visibility = "hidden";
parent.frames[1].printStatus30.style.visibility = "hidden";
parent.frames[1].printStatus40.style.visibility = "hidden";
parent.frames[1].printStatus50.style.visibility = "hidden";
parent.frames[1].printStatus60.style.visibility = "hidden";
parent.frames[1].printStatus70.style.visibility = "hidden";
parent.frames[1].printStatus80.style.visibility = "hidden";
parent.frames[1].printStatus90.style.visibility = "hidden";
parent.frames[1].printStatus100.style.visibility = "hidden";

//parent.frames[1].percentage.style.visibility = "visible";

<%
if (percent != null  && percent.length() > 0 && (Integer.parseInt(percent) <= 100))
{%>
eval("parent.frames[1].gathering" + <%=percent%> + ".style.visibility = 'visible';");
<%
}
else
{
%>
eval("parent.frames[1].gathering" + 10 + ".style.visibility = 'visible';");
<%
}
%>

</script>
<%
}
else if (status.equalsIgnoreCase("end_gathering"))
{
%>
<script>
parent.frames[1].printStatus0.style.visibility = "hidden";
parent.frames[1].printStatus10.style.visibility = "hidden";
parent.frames[1].printStatus20.style.visibility = "hidden";
parent.frames[1].printStatus30.style.visibility = "hidden";
parent.frames[1].printStatus40.style.visibility = "hidden";
parent.frames[1].printStatus50.style.visibility = "hidden";
parent.frames[1].printStatus60.style.visibility = "hidden";
parent.frames[1].printStatus70.style.visibility = "hidden";
parent.frames[1].printStatus80.style.visibility = "hidden";
parent.frames[1].printStatus90.style.visibility = "hidden";
parent.frames[1].printStatus100.style.visibility = "hidden";

parent.frames[1].gathering0.style.visibility = "hidden";
parent.frames[1].gathering10.style.visibility = "hidden";
parent.frames[1].gathering20.style.visibility = "hidden";
parent.frames[1].gathering30.style.visibility = "hidden";
parent.frames[1].gathering40.style.visibility = "hidden";
parent.frames[1].gathering50.style.visibility = "hidden";
parent.frames[1].gathering60.style.visibility = "hidden";
parent.frames[1].gathering70.style.visibility = "hidden";
parent.frames[1].gathering80.style.visibility = "hidden";
parent.frames[1].gathering90.style.visibility = "hidden";
parent.frames[1].gathering100.style.visibility = "hidden";
</script>
<%
}
else if (status.equalsIgnoreCase("start_download"))
{
%>
<script>
parent.frames[1].printStatus0.style.visibility = "hidden";
parent.frames[1].printStatus10.style.visibility = "hidden";
parent.frames[1].printStatus20.style.visibility = "hidden";
parent.frames[1].printStatus30.style.visibility = "hidden";
parent.frames[1].printStatus40.style.visibility = "hidden";
parent.frames[1].printStatus50.style.visibility = "hidden";
parent.frames[1].printStatus60.style.visibility = "hidden";
parent.frames[1].printStatus70.style.visibility = "hidden";
parent.frames[1].printStatus80.style.visibility = "hidden";
parent.frames[1].printStatus90.style.visibility = "hidden";
parent.frames[1].printStatus100.style.visibility = "hidden";

parent.frames[1].gathering0.style.visibility = "hidden";
parent.frames[1].gathering10.style.visibility = "hidden";
parent.frames[1].gathering20.style.visibility = "hidden";
parent.frames[1].gathering30.style.visibility = "hidden";
parent.frames[1].gathering40.style.visibility = "hidden";
parent.frames[1].gathering50.style.visibility = "hidden";
parent.frames[1].gathering60.style.visibility = "hidden";
parent.frames[1].gathering70.style.visibility = "hidden";
parent.frames[1].gathering80.style.visibility = "hidden";
parent.frames[1].gathering90.style.visibility = "hidden";
parent.frames[1].gathering100.style.visibility = "hidden";
//parent.frames[1].percentage.style.visibility = "hidden";

parent.frames[1].downloading.style.visibility = "visible";
setTimeout("clearMessages()", 2000);
</script>
<%
}
else if (status.equalsIgnoreCase("end_download"))
{
%>
<script>
//parent.top.hiddenFrame.location = "home?cmd=reports-print-download-done";
</script>
<%
}
%>


<%
if (context.getDelivery("download") != null)
{
%>
<script>
parent.top.hiddenFrame.location = "home?cmd=reports-print-download";
</script>
<%
}
%>

<%@ include file="include-bottom-html.shtml"%>