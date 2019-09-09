<%@ page import="com.universal.milestone.MilestoneConstants"%>
<%@ include file="template-top-page.shtml"%>

<%

    // MSC 10/24/03  clear the user search data elements
    User user = (User)context.getSessionValue("user");
    if(user != null)
    {
         user.SS_artistSearch = "";
         user.SS_titleSearch = "";
         user.SS_selectionNoSearch = "";
         user.SS_prefixIDSearch = "";
         user.SS_upcSearch = "";
         user.SS_streetDateSearch = "";
         user.SS_streetEndDateSearch = "";
         user.SS_configSearch = "";
         user.SS_subconfigSearch = "";
         user.SS_labelSearch = "";
         user.SS_companySearch = "";
         user.SS_contactSearch = "";
         user.SS_familySearch = "";
         user.SS_environmentSearch = "";
         user.SS_projectIDSearch = "";
         user.SS_productTypeSearch = "";
         user.SS_showAllSearch = "";
    }


%>



