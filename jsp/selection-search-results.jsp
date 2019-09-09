<%@ page import="com.universal.milestone.MilestoneConstants"%>
<%@ include file="template-top-page.shtml"%>

<%
  // show alert box with not found message
  out.println("<script>");
  //out.println("document.all.selectionSearchResultsLayer.style.visibility = "visible";
  out.println("document.all.SearchLayer.style.visibility = \"visible\";"); // show the search div
  out.println("mtbSearch.reset();"); // reset the search button
  out.println("alert(\"There are no records meeting the selection criteria; please adjust your criteria and try again.\");");
  out.println("</script>");
%>
