 <%
  /*
		this include (template-top-page.jsp) does the following:
			extends com.universal.milestone.MilestoneJSP
			import="com.techempower.*,com.techempower.gemini.*, com.universal.milestone.*, java.util.*"
			gets the context, form formValidation and message
  */
%>

<%@ page import="com.universal.milestone.MilestoneConstants"%>
<%@ include file="template-top-page.shtml"%>
<%@ include file="callHelp.js"%>

<%
  boolean isMac = false;


	// This variable holds the value to be placed in the <title> html tag for the include file:
	// template-top-html.jsp.
	String htmlTitle = "Project Search Screen";

	// getting the user object from the session
	User user = (User)context.getSessionValue("user");

        String sortingColumn = "6";//Default is Create Date
	if (context.getSessionValue("sortingColumn")!=null){
	       sortingColumn = (String)context.getSessionValue("sortingColumn");
	}

	String sortingDirection = "Descending";
	if (context.getSessionValue("sortingDirection")!=null){
	       sortingDirection = (String)context.getSessionValue("sortingDirection");
	}

        Vector jdeExceptionFamilies = ProjectSearchManager.getInstance().getProjectSearchJDEFamilies();
        boolean isUmvdUser = ProjectSearchManager.getInstance().isUmvdProjectSearchUser(user, context, jdeExceptionFamilies);

        // ITS 867 - jo - 1/6/2004
        // If Canada then default Project Search to digital
        boolean isCanadaUser =  ProjectSearchManager.getInstance().isCanadaUser(user, context);
        // ITS 1003 - mc - 8/3/2004
        // If Mexico user then default Project Search to digital
        boolean isMexicoUser =  ProjectSearchManager.isMexicoUser(user, context);

	String radioSelected = "";
	if (context.getParameter("isPhysical")!=null){
    		if (context.getParameter("isPhysical").equals("Digital")){
      			radioSelected = "Digital";
    		} else if (context.getParameter("isPhysical").equals("Physical")){
      			radioSelected = "Physical";
		}
	}

        String selectionScreenTypeString = (String)context.getSessionValue("selectionScreenType");


	
%>

<% //-- includes the top header -- %>
<%@ include file="template-top-html.shtml" %>

<script type="text/javascript" language="JavaScript">
//global variables needed in include selection-editor-javascript.js


  var cmd = "<%= form.getRenderableValue("cmd")%>";

  var searchCommand = "<%= MilestoneConstants.CMD_PROJECT_SEARCH_RESULTS%>";
  var searchCommandAndOrder = "<%= MilestoneConstants.CMD_PROJECT_SEARCH_RESULTS_ORDER%>";
  var gotoSelectionScreenCommand = "<%= MilestoneConstants.CMD_PROJECT_SEARCH_GOTO_SELECTION%>";
  var selectionCancelCommand = "<%= MilestoneConstants.CMD_PROJECT_SEARCH_CANCEL%>";
  var selectionClearCommand  = "<%= MilestoneConstants.CMD_PROJECT_SEARCH_CLEAR%>";

  var imageDir = "<%= inf.getImageDirectory()%>";
  var navCom = navigator.platform;
  if( navCom.toUpperCase() == "MACPPC" || navCom.substr(0,3).toUpperCase() == "MAC" ) {
   // <% isMac = true; %>
  }


</script>

<% //-- include js files holding javascript functions --%>
<script type="text/javascript" language="javascript" src="<%= inf.getHtmlDirectory() %>include-jscript-toggle.js"></script>
<script type="text/javascript" language="javascript" src="<%= inf.getHtmlDirectory() %>include-jscript-toggle-button.js"></script>
<script type="text/javascript" language="javascript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-format.js"></script>
<script type="text/javascript" language="javascript" src="<%= inf.getHtmlDirectory() %>include-jscript-focus-field.js"></script>
<script type="text/javascript" language="javascript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-length.js"></script>
<script type="text/javascript" language="javascript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-convert-date.js"></script>
<script type="text/javascript" language="javascript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-integer.js"></script>
<script type="text/javascript" language="javascript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-shortcut.js"></script>
 <script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>selection-editor-javascript.js"></script>
<script type="text/javascript" language="javascript" src="<%= inf.getHtmlDirectory() %>include-jscript-auto-select.js"></script>

<link rel="stylesheet" type="text/css" href="<%= inf.getHtmlDirectory() %>global.css" title="GlobalStyle">

<Script>

//Gets the current Product Type by checking the value of the Product Type radio button
function getIsPhysical(){

	return (document.all.isPhysical[0].checked==true)
}

//Validates the form, sets command and submits form
function SortNow(cn)
{
   //alert("sorting column:[" + cn + "]");
   //alert("sorting direction:[" + document.forms[0].sortDirection.value + "]");
   if (validateFields() ) {
        showWaitMsg();   // msc its 966 show please wait message
   	document.forms[0].cmd.value = searchCommandAndOrder;
   	document.forms[0].sortColumn.value = cn;
   	document.forms[0].submit();
   }
}

//Switches the sorting direction and calls SortNow above
function UpdateSortingDirection(cn)
{

 //Do not sort if the sortDirection hidden field
 //is not in memory yet
 if (document.forms[0].sortDirection){

   //only switch the sort direction if on the same column
   if (document.forms[0].sortColumn.value==cn){
   	if (document.forms[0].sortDirection.value=="Ascending"){
      		document.forms[0].sortDirection.value = "Descending";
   	} else {
      		document.forms[0].sortDirection.value = "Ascending";
   	}
   } else {
   //clicked a different column so set the default sorting direction for that column ITS# 711
   //all columns have the default sorting direction "Ascending" except for "Create Date"(=Column 6)
        if (cn==6){
              document.forms[0].sortDirection.value = "Descending";
        } else {
              document.forms[0].sortDirection.value = "Ascending";
        }
   }
   SortNow(cn);

 }

}

//search projects - reset the sort column and sort order
function searchProjects(){

        if (validateFields() ) {
        	window.console && console.log(searchCommand);
            showWaitMsg();   // msc its 966 show please wait message
            document.forms[0].sortDirection.value = "Descending";
            document.forms[0].sortColumn.value = "6";
	    	document.forms[0].cmd.value = searchCommand;
            document.forms[0].submit();
	    return true;
	} else {
           return false;
	}
}

//Form validation
function validateFields(){
	//Physical/Digital must be selected
	if ( document.all.isPhysical[0].checked!=true && document.all.isPhysical[1].checked!=true ){
		alert("The Product Type (Physical/Digital) must be selected.");
		document.all.isPhysical[0].focus();
		return false;
	} //at least one search has to be used
	else if (  document.forms[0].artistFirstName2.value=="" && document.forms[0].artistLastName.value==""
		&& document.forms[0].labels[document.forms[0].labels.selectedIndex].value==0
		&& document.forms[0].title.value=="" && document.forms[0].projectDesc.value==""
		&& document.forms[0].projectID.value=="" ) {
		<% if (isUmvdUser) { %>
			alert("Please select a single label or enter a Project ID");
		<% } else { %>
			alert("At least one search field must be applied");
		<% } %>
		return false;
	} //form validated
	else {
		return true;
	}


}

//Checks the radio button to find out if the project is
//digital or physical, then sets colors of baclgrounds and tables
//If there is no selected Product Type then resets screen to "wheat" color
//background
function updateBackground(){

	// release upgrade 8.1 hide buttons
	<%if(MilestoneConstants.IsGDRSactive)
    {%>
	  document.all.isPhysical[0].checked = true;
 	  document.all.productTypeSpan.style.visibility = "hidden";
 	  document.all.RevisedProductType.innerText = "Physical";
	  document.all.isPhysical[0].checked = true;
	 document.all.isPhysical[1].checked = false;
    <%}%>
	// release upgrade 8.1 hide buttons

	//if selection type is "REVISED" then set focus to Label dropdown
	//else set focus to the Product Type radio buttons
        var selectionType = "";
        selectionType = "<%=selectionScreenTypeString%>";
	if (selectionType=="revise"){
		//hide the product type radio buttons
		document.all.productTypeSpan.style.visibility = "hidden";
		//get the product type from the parent screen
		isDigital = parent.document.all.isDigital.value;
		if (isDigital==1){
			document.all.RevisedProductType.innerText = "Digital";
			document.all.isPhysical[1].checked = true;
			document.all.isPhysical[0].checked = false;
		} else {
			document.all.RevisedProductType.innerText = "Physical";
			document.all.isPhysical[0].checked = true;
			document.all.isPhysical[1].checked = false;
		}
	}

	//if umvd user and product type selected
	//set the inital focus
	//1. if UMVD and Revise -> Focus on Labels
	//2. if UMVD and New -> Focus on Labels
	//3. if not UMVD and Revise -> Focus on Artist First Name
	//4. if not UMVD and New -> Focus on Physical radio button
	<%if (isUmvdUser){ %>
		if (document.all.isPhysical[0].checked==true || document.all.isPhysical[1].checked==true){
			//hide all search boxes except for Label dropdown and Project ID
			removeSearchBoxesForUMVD();
		}
		document.all.labels.focus();
	<% } else { %>
		if (selectionType=="revise"){
			document.all.artistFirstName2.focus();
		} else {
		    if(document.all.isPhysical[0].checked)   // release upgrade 8.1 hide buttons
		    	document.all.artistFirstName2.focus(); // release upgrade 8.1 hide buttons
		    else // release upgrade 8.1 hide buttons	 
		    	document.all.isPhysical[0].focus();
		}
	<% } %>


	//Set up screen colors/borders for selected Product Type
	if (document.all.isPhysical[0].checked==true){

		//physical

		document.all.radioSpan.style.backgroundColor='lavender';
		document.body.style.backgroundColor='lavender';

 		//update the color of the search results IFRAME background
		document.frames.searchResultsFrame.document.body.style.backgroundColor="lavender";

		//call the function shadeRows in the search results IFrame to update the Colors
		document.frames.searchResultsFrame.shadeRowsFromMainFrame();

		//update the main IFrame border
		parent.document.all.ProjectSearchLayer.style.border = "outset lavender 5px";

	} else if (document.all.isPhysical[1].checked==true){

		//digital

		document.all.radioSpan.style.backgroundColor='<%=MilestoneConstants.DIGITAL_PINK%>';
		document.body.style.backgroundColor='<%=MilestoneConstants.DIGITAL_PINK%>';

		//update the color of the search results IFRAME background
		document.frames.searchResultsFrame.document.body.style.backgroundColor='<%=MilestoneConstants.DIGITAL_PINK%>';

 		//call the function shadeRows in the search results IFrame to update the Colors
		document.frames.searchResultsFrame.shadeRowsFromMainFrame();

		//update the main IFrame border
		parent.document.all.ProjectSearchLayer.style.border = "outset <%=MilestoneConstants.DIGITAL_PINK%> 5px";


	} else {//none selected yet

		<%// if a UMVD user then default to Physical
		if (isUmvdUser){ %>

			document.all.isPhysical[0].checked=true;
			document.all.radioSpan.style.backgroundColor='lavender';
			document.body.style.backgroundColor='lavender';
 			//update the color of the search results IFRAME background
			document.frames.searchResultsFrame.document.body.style.backgroundColor="lavender";
			//call the function shadeRows in the search results IFrame to update the Colors
			document.frames.searchResultsFrame.shadeRowsFromMainFrame();

			//hide all search boxes except for Label dropdown and Project ID
			removeSearchBoxesForUMVD();


		<% //else a Canada user then default to Digital
		} else if (isCanadaUser || isMexicoUser ) {  %>

			document.all.isPhysical[1].checked=true;
			document.all.radioSpan.style.backgroundColor='<%=MilestoneConstants.DIGITAL_PINK%>';
			document.body.style.backgroundColor='<%=MilestoneConstants.DIGITAL_PINK%>';

 			//update the color of the search results IFRAME background
			document.frames.searchResultsFrame.document.body.style.backgroundColor="<%=MilestoneConstants.DIGITAL_PINK%>";

			//call the function shadeRows in the search results IFrame to update the Colors
			document.frames.searchResultsFrame.shadeRowsFromMainFrame();

                <% } else { %>
			document.all.radioSpan.style.backgroundColor='wheat';
			document.body.style.backgroundColor='wheat';
			//update the color of the search results IFRAME background
			document.frames.searchResultsFrame.document.body.style.backgroundColor="wheat";
		<% } %>

	}
}

//Called when a row is double-clicked in the Project Search Results JSP table
function selectProject(selectedCount){

	var selectionType = "<%=(String)context.getSessionValue("selectionScreenType")%>";
	//alert("selectionType:[" + selectionType + "]");

	if (selectionType=="new"){

                showWaitMsg();  // msc its 966 show please wait message

                //Replace the search results table with the text 'Processing'
                document.frames.searchResultsFrame.document.all.resultsTable.innerHTML = "<br><br><br><br><br><center><h1>Processing</h1></center>";

		document.forms[0].target = "_parent";
		document.forms[0].selectedCounter.value = selectedCount;
		document.forms[0].cmd.value = gotoSelectionScreenCommand;
		document.forms[0].submit();
	} else {

                showWaitMsg();  // msc its 966 show please wait message

		//update Selection values
		getRevisionValues(selectedCount);

                // JP Digital AFE - Retrieve releasing families
                parent.document.all.ReleasingFamilyFrame.src = "<%= inf.getServletCmdURL("selection-get-releasing-families")%>&eID=" + parent.document.all.environment.value;

		//Close the Project Search IFrame
		layer = eval(parent.document.all["ProjectSearchLayer"]);
		layer.style.visibility = "hidden";

                parent.mtbRevise.reset();
                if(parent.mtbNew)
                  parent.mtbNew.reset();
                if(parent.mtbNew_Selection)
                  parent.mtbNew_Selection.reset();

                // Jp Digital AFE
               if(parent.document.all.newSelectionDiv)
                 parent.document.all.newSelectionDiv.style.visibility = 'visible';
               if(parent.document.all.newDiv)
                 parent.document.all.newDiv.style.visibility = 'visible';
               parent.document.all.reviseDiv.style.visibility = 'visible';
               parent.document.all.copyDiv.style.visibility = 'visible';
               if (parent.document.all.copyDigitalDiv)
                 parent.document.all.copyDigitalDiv.style.visibility = 'visible';
               parent.document.all.deleteDiv.style.visibility = 'visible';
               parent.document.all.saveDiv.style.visibility = 'visible';

                hideWaitMsg()   // msc its 966 show please wait message

	}
}


//Updates the parent values in the Selection screen - except for ArtistFirst&LastNames and Title
function getRevisionValues(rowId){

	//Label
	labelText = "document.frames.searchResultsFrame.document.all.Label" + rowId + ".innerText";
	parent.document.all.labeldiv.innerText = eval(labelText);
	labelId = "document.frames.searchResultsFrame.document.all.LabelId" + rowId + ".value";
	parent.document.all.label.value = eval(labelId);

	//Company
	companyText = "document.frames.searchResultsFrame.document.all.Company" + rowId + ".value";
	parent.document.all.companydiv.innerText = eval(companyText);
	companyId = "document.frames.searchResultsFrame.document.all.CompanyId" + rowId + ".value";
	parent.document.all.company.value = eval(companyId);

	//Division
	divisionText = "document.frames.searchResultsFrame.document.all.Division" + rowId + ".value";
	parent.document.all.divisiondiv.innerText = eval(divisionText);
	divisionId = "document.frames.searchResultsFrame.document.all.DivisionId" + rowId + ".value";
	parent.document.all.division.value = eval(divisionId);

	//Environment
	environmentText = "document.frames.searchResultsFrame.document.all.Environment" + rowId + ".value";
	parent.document.all.environmentdiv.innerText = eval(environmentText);
	environmentId = "document.frames.searchResultsFrame.document.all.EnvironmentId" + rowId + ".value";
	parent.document.all.environment.value = eval(environmentId)

	//ProjectID
	projectIdText = "document.frames.searchResultsFrame.document.all.ProjectId" + rowId + ".innerText";
	parent.document.all.projectId.value = eval(projectIdText);
	parent.document.all.projectIddiv.innerText = eval(projectIdText);

	//OpCo
	OperCompanyText = "document.frames.searchResultsFrame.document.all.OperCompany" + rowId + ".innerText";
	OperCompanyDisplayDivText = "document.frames.searchResultsFrame.document.all.operCompanyDisplayString" + rowId + ".value";
	parent.document.all.opercompany.value = eval(OperCompanyText)
	parent.document.all.opercompanydiv.innerText = eval(OperCompanyDisplayDivText);

	//Super Label
	SuperLabelText = "document.frames.searchResultsFrame.document.all.SuperLabel" + rowId + ".innerText";
	parent.document.all.superlabel.value = eval(SuperLabelText);
	parent.document.all.superlabeldiv.innerText = eval(SuperLabelText );

	//Sub Label
	SubLabelText = "document.frames.searchResultsFrame.document.all.SubLabel" + rowId + ".innerText";
	parent.document.all.sublabel.value = eval(SubLabelText);
	parent.document.all.sublabeldiv.innerText = eval(SubLabelText );

	//P&D
	PDText = "document.frames.searchResultsFrame.document.all.PandD" + rowId + ".value";
	if (eval(PDText)==0){
		parent.document.all.pdIndicator.checked = false;
	} else {
		parent.document.all.pdIndicator.checked = true;
	}

	//Imprint
	labelImprintText = "document.frames.searchResultsFrame.document.all.Label" + rowId + ".innerText";
	parent.document.all.imprint.value = eval(labelImprintText);

        //DistCo ITS 1069 - jo
        //only update for Physical, Digital doesn't have distCoDiv field
        if (parent.document.all.distCoDiv){
              distCoText = "document.frames.searchResultsFrame.document.all.DistCo" + rowId + ".innerText";
	      parent.document.all.distCoDiv.innerText = eval(distCoText);
        }

}

function cancelProjectSearch(){

	//to avoid script error in parent
	//parent.document.all.fromProjectSearchCancel = true;
	//close the IFRAME
	layer = eval(parent.document.all["ProjectSearchLayer"]);
	layer.style.visibility = "hidden";

        // MSC ITS 703 10/09/03 Added new selection button to label tab menu options
        // Now checking to see if the button exists
        if(parent.mtbRevise)
          parent.mtbRevise.reset();
        if(parent.mtbNew)
          parent.mtbNew.reset();
        if(parent.mtbNew_Selection)
          parent.mtbNew_Selection.reset();

        // Jp Digital AFE
        if(parent.document.all.newDiv)
          parent.document.all.newDiv.style.visibility = 'visible';
        if(parent.document.all.reviseDiv)
          parent.document.all.reviseDiv.style.visibility = 'visible';
        if(parent.document.all.copyDiv)
          parent.document.all.copyDiv.style.visibility = 'visible';
        if (parent.document.all.copyDigitalDiv)
          parent.document.all.copyDigitalDiv.style.visibility = 'visible';
        if(parent.document.all.deleteDiv)
          parent.document.all.deleteDiv.style.visibility = 'visible';
        if(parent.document.all.saveDiv)
          parent.document.all.saveDiv.style.visibility = 'visible';

        // MSC ITS 703 New buttons
        if(parent.document.all.newSelectionDiv)
          parent.document.all.newSelectionDiv.style.visibility = 'visible';
        // don't make the report-editor.jsp print window visible at this time
        if(parent.document.all.printWindow && !parent.document.all.reportsEditPrintWindow)
          parent.document.all.printWindow.style.visibility = 'visible';
        if(parent.document.all.printWindow2 && !parent.document.all.reportsEditPrintWindow)
          parent.document.all.printWindow2.style.visibility = 'visible';
        if(parent.document.all.sendOptionDiv)
          parent.document.all.sendOptionDiv.style.visibility = 'visible';
        if(parent.document.all.saveDivBottom)
          parent.document.all.saveDivBottom.style.visibility = 'visible';

        // show the select form elements, if parentForm object exists
        if(parent.document)
          showSelectElements(parent.document);

	//document.forms[0].target = "_parent";
	//document.forms[0].cmd.value = selectionCancelCommand ;
	//document.forms[0].submit();

}

//reset project search screen
function clearAll(){
	document.forms[0].cmd.value = selectionClearCommand ;
	document.forms[0].submit();
}

//remove all search boxes except ProjectID and their text headings
//used for UMVD users
function removeSearchBoxesForUMVD(){

	//hide all search boxes except for Label dropdown and Project ID
	document.forms[0].artistFirstName2.style.visibility = "hidden";
	document.forms[0].artistLastName.style.visibility = "hidden";
	document.forms[0].title.style.visibility = "hidden";
	document.forms[0].projectDesc.style.visibility = "hidden";

	//remove the titles
	document.all.firstNameSearchTitle.style.visibility = "hidden";
	document.all.projectNameSearchTitle.style.visibility = "hidden";
	document.all.groupNameSearchTitle.style.visibility = "hidden";
	document.all.projectDescNameSearchTitle.style.visibility = "hidden";
}

</script>

</head>

<script for="document" event="onkeydown" language="JavaScript">
	checkShortcut();
</script>

<% //start body %>
<body onLoad="updateBackground();" topmargin="0" leftmargin="0"

      <% if (radioSelected.equals("Physical")){%>
	       STYLE="background-color: lavender"
      <%} else if (radioSelected.equals("Digital")){%>
      	       STYLE="background-color: '<%=MilestoneConstants.DIGITAL_PINK%>'"
      <%} else {%>
	       STYLE="background-color: wheat"
      <% } %>

      onKeyPress="checkForEnter( 'searchProjects()' );"  >



<form name="projectSearchForm" action="<%=inf.getServletURL()%>" method="POST">

<%=form.getElement("cmd")%>

<table width="100%" border="0" cellspacing=0 cellpadding=0 style="padding-bottom:4px;padding-top:0px;padding-right:4px;padding-left:4px">

<tr>
   <td>
	<% //Search Criteria %>
      <table border="0" width="100%">
	<tr>
		<td><span class="title">Project Search</span></td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td id="productTypeText" align="right" colspan=2>
Product Type:&nbsp;
<span id="RevisedProductType">
</span>
<span id="productTypeSpan"
      style="visibility: '<% if (selectionScreenTypeString.equals("revise")){%>hidden<%} else {%>visible<%}%>'" >
      <span id="radioSpan" onClick="updateBackground();"><%=form.getElement("isPhysical") %></span>
</span>
			&nbsp;<input type="button" name="Search_PS" value="Search" onClick="searchProjects();">
                        &nbsp;<input type="button" name="cancel" value="Cancel" onClick="cancelProjectSearch();">
			&nbsp;<input type="button" name="Clear"  value="Clear"  onClick="clearAll();">
		</td>
	</tr>
	</table>
     </td>
</tr>

<tr>
   <td>
	<div STYLE="border: outset 1px #bbbbbb;">
	<table border="0" width="100%">
	<tr>
		<td><span id="firstNameSearchTitle"><b>First Name</b></span>&nbsp;&nbsp;</td>
		<td><%= form.getElement("artistFirstName2") %></td>
		<td><span id="projectNameSearchTitle"><b>Project Title</b></span>&nbsp;</td>
		<td><%= form.getElement("title") %></td>
		<td  align="right" ><b>Label</b>&nbsp;</td>
		<td><%= form.getElement("labels") %></td>
	</tr>
	<tr>
		<td><span id="groupNameSearchTitle"><b>Last/Group Name</b></span>&nbsp;&nbsp;</td>
		<td><%= form.getElement("artistLastName") %></td>
		<td><span id="projectDescNameSearchTitle"><b>Project Desc.</b></span>&nbsp;</td>
		<td><%= form.getElement("projectDesc") %></td>
		<td  align="right" ><b>Project ID</b>&nbsp;</td>
		<td><%= form.getElement("projectID") %></td>
	</tr>
      </table>
      </div>
    </td>
</tr>
<!-- blank row -->
<tr>
<td></td>
</tr>
</table>


<iframe
name="searchResultsFrame"
src="<%=inf.getStandardDomain() + inf.getJspDirectory()%>project-search-results.jsp"
scrolling="auto"
width="100%"
hspace=4
height=320
>
</iframe>

<!-- hidden fields used for sorting defaulted to first column in Ascending order -->
<input type="hidden" name="sortColumn" value="<%=sortingColumn%>">
<input type="hidden" name="sortDirection" value="<%=sortingDirection%>">
<!-- hidden fields used to pass data -->
<input type="hidden" name="selectedCounter">

<%=form.renderEnd()%>

<div style="padding-bottom:0px;padding-top:0px;padding-right:0px;padding-left:4px">
Double Click to select a Project
</div>

<%@ include file="template-bottom-html.shtml"%>



