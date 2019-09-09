<%@ page import="com.universal.milestone.MilestoneConstants"%>
<%@ include file="template-top-page.shtml"%>
<%@ include file="callHelp.js"%>

<%
  Vector releasingFamilies = (Vector)context.getDelivery("releasing_family_list");

  out.println("<script>");
  out.println("parent.document.forms[0].releasingFamily.options.length = 0");
  for (int i = 0; i < releasingFamilies.size(); i++)
  {
    ReleasingFamily rFamily = (ReleasingFamily)releasingFamilies.elementAt(i);
    out.println("parent.document.forms[0].releasingFamily.options[" + i + "] = new Option('" + rFamily.getFamillyName() + "'," + Integer.toString(rFamily.getReleasingFamilyId()) + ");");

    if (rFamily.IsDefault())
      out.println("parent.document.forms[0].releasingFamily.options[" + i + "].selected=true;");
  }
  out.println("</script>");
%>