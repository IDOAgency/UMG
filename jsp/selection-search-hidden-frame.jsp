<%
   /* This variable holds the value to be placed in <title> html tag </title>
      for the include file: template-top-html.jsp */
   String htmlTitle = "Selection Search Hidden Frame";

  %>



<%@ include file="template-top-page.shtml"%>
<%@ include file="template-top-html.shtml"%>

<script>
// this variable is used to submit the parents form search command
var submitParentSearchCommand = ""; // default to blanks

<%
  // get the search command to execute
  String searchCmdStr = (String)context.getDelivery("submitParentSearchCommand");
  if(searchCmdStr != null)
  {%>
    submitParentSearchCommand = "<%=searchCmdStr%>";
  <%}
%>

</script>

</head>

<body bgcolor="wheat" topmargin=0 leftmargin=0 >
<form name="selectionSearchHiddenFrame" action="<%=inf.getServletURL()%>" method="POST" >

<input type="hidden" name="cmd" value="<%= MilestoneConstants.CMD_SELECTION_SEARCH_RESULTS%>">

<input type=hidden name="macArtistSearch" value="">
<input type=hidden name="macTitleSearch" value="">
<input type=hidden name="macPrefixSearch" value="">
<input type=hidden name="macSelectionSearch" value="">
<input type=hidden name="macUPCSearch" value="">
<input type=hidden name="macStreetDateSearch" value="">
<input type=hidden name="macStreetEndDateSearch" value="">
<input type=hidden name="ConfigSearch" value="">
<input type=hidden name="macSubconfigSearch" value="">
<input type=hidden name="macCompanySearch" value="">
<input type=hidden name="macLabelSearch" value="">
<input type=hidden name="ContactSearch" value="">
<input type=hidden name="FamilySearch" value="">
<input type=hidden name="EnvironmentSearch" value="">
<input type=hidden name="ProdType" value="">
<input type=hidden name="ProjectIDSearch" value="">
<input type=hidden name="ShowAllSearch" value="false">
<input type=hidden name="isSelectionSearchResults" value="false">
<input type=hidden name="selectionSearchCommand" value="false">
</form>
</body>



<script>

  //set the hidden variables to the form values:
  document.forms[0].macArtistSearch.value = parent.document.forms[0].ArtistSearch.value;
  document.forms[0].macTitleSearch.value = parent.document.forms[0].TitleSearch.value;
  document.forms[0].macPrefixSearch.value = parent.document.forms[0].PrefixSearch.value;
  document.forms[0].macSelectionSearch.value = parent.document.forms[0].SelectionSearch.value;
  document.forms[0].macUPCSearch.value = parent.document.forms[0].UPCSearch.value;
  document.forms[0].macStreetDateSearch.value = parent.document.forms[0].StreetDateSearch.value;
  document.forms[0].macStreetEndDateSearch.value = parent.document.forms[0].StreetEndDateSearch.value;
  document.forms[0].FamilySearch.value = parent.document.forms[0].FamilySearch[parent.document.forms[0].FamilySearch.selectedIndex].value;
  document.forms[0].EnvironmentSearch.value = parent.document.forms[0].EnvironmentSearch[parent.document.forms[0].EnvironmentSearch.selectedIndex].value;
  document.forms[0].macCompanySearch.value = parent.document.forms[0].CompanySearch[parent.document.forms[0].CompanySearch.selectedIndex].value;
  document.forms[0].macLabelSearch.value = parent.document.forms[0].LabelSearch[parent.document.forms[0].LabelSearch.selectedIndex].value;
  document.forms[0].ContactSearch.value = parent.document.forms[0].ContactSearch[parent.document.forms[0].ContactSearch.selectedIndex].value;
  document.forms[0].ConfigSearch.value = parent.document.forms[0].ConfigSearch[parent.document.forms[0].ConfigSearch.selectedIndex].value;
  document.forms[0].ProjectIDSearch.value = parent.document.forms[0].ProjectIDSearch.value;

  if(parent.document.forms[0].ShowAllSearch.checked == true)
    document.forms[0].ShowAllSearch.value = "true";

   document.forms[0].ProdType.value = "Both";
   if (parent.document.forms[0].ProdType[0].checked)
     document.forms[0].ProdType.value = "physical";
   else if (parent.document.forms[0].ProdType[1].checked)
     document.forms[0].ProdType.value = "digital";

   if (parent.document.forms[0].company2[0].checked)
	     document.forms[0].ProdType.value = "All";
	   else if (parent.document.forms[0].company2[1].checked)
	     document.forms[0].ProdType.value = "Select";

   if (parent.document.forms[0].Label2[0].checked)
	     document.forms[0].ProdType.value = "All";
	   else if (parent.document.forms[0].Label2[1].checked)
	     document.forms[0].ProdType.value = "Select";

   
   if (parent.document.forms[0].SubconfigSearch.length > 0){
    document.forms[0].macSubconfigSearch.value = parent.document.forms[0].SubconfigSearch[parent.document.forms[0].SubconfigSearch.selectedIndex].value;
   } else {
    document.forms[0].macSubconfigSearch.value = "";
   }


/*
// submit the form
if(submitParentSearchCommand == "true")  // submit the parents search command
{
   document.body.style.cursor="wait";
   document.forms[0].target = "_parent"; // change this forms target to parent window
   document.all.cmd.value = parent.window.search;
   document.all.selectionSearchCommand.value = parent.window.search;
   document.forms[0].isSelectionSearchResults.value = "true";
   document.forms[0].submit();   // submit this form
} else if(submitParentSearchCommand == "false")  // display error message
{
   // display the results error box
   //parent.document.all.selectionSearchResultsLayer.style.visibility = "visible";
   alert("No selection records found...please alter search criteria");
} else {
  document.forms[0].target = "_parent"; // change this forms target to parent window
  document.body.style.cursor="wait";
  document.all.selectionSearchCommand.value = parent.window.search;
  document.forms[0].submit();   // submit this form
}
*/

// new logic for submitting form
document.forms[0].target = "_parent"; // change this forms target to parent window
document.body.style.cursor="wait";
document.all.selectionSearchCommand.value = parent.window.search;
showWaitMsg();   // msc its 966 show please wait message
document.forms[0].submit();   // submit this form

</script>

<%@ include file="include-bottom-html.shtml"%>
