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

	// get the search results from the session
	Vector searchResults = new Vector();
	if (context.getSessionValue("searchResults")!=null){
	       searchResults = (Vector)context.getSessionValue("searchResults");
              //System.out.println("****on search results jsp size:[" + searchResults.size() + "]");
	}

        //build vector of Project Search JDE Families
        //these are taken from the Utilities table
        //ITS 1032 - Modified umvd code to use now jde exception vector
        Vector jdeExceptionFamilies = ProjectSearchManager.getInstance().getProjectSearchJDEFamilies();
        boolean isUmvdUser = ProjectSearchManager.getInstance().isUmvdProjectSearchUser(user, context, jdeExceptionFamilies);

%>

<% //-- includes the top header -- %>
<%@ include file="template-top-html.shtml" %>

<script type="text/javascript" language="JavaScript">
//global variables needed in include selection-editor-javascript.js
  var sort = "<%= MilestoneConstants.CMD_SELECTION_SORT%>";
  var sortGroup = "<%= MilestoneConstants.CMD_SELECTION_GROUP%>";

  var editor = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_SELECTION_EDITOR)%>";
  var notepad = "<%= MilestoneConstants.NOTEPAD_SELECTION%>";
  var deleteCommand = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_SELECTION_EDIT_DELETE) %>";
  var copyCommand = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_SELECTION_EDIT_COPY) %>";
  var save = "<%= MilestoneConstants.CMD_SELECTION_EDIT_SAVE%>";
  var saveNew = "<%= MilestoneConstants.CMD_SELECTION_EDIT_SAVE%>";
	var saveCopy = "<%= MilestoneConstants.CMD_SELECTION_EDIT_SAVE%>";
  var editNew = "<%= MilestoneConstants.CMD_SELECTION_EDIT_NEW%>";
  var search = "<%= MilestoneConstants.CMD_SELECTION_SEARCH%>";

  var next = "<%= inf.getServletCmdURL("notepad-next")%>";
  var previous = "<%= inf.getServletCmdURL("notepad-previous")%>";
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

<script>

//sorting variables
var sortingColumn;
var sortingDirection;

//variable to hold previous row color
//for onmouseon/onmouseover events
var previousBgColor;

//Row colors////////////////////////////////////////

var lightPhysicalRowColor = '#ffffff';
var darkPhysicalRowColor  = '#e6e6fa';
var hoverPhysicalRowColor = '#b3b6f9';

var lightDigitalRowColor    = '#ffffff';
var darkDigitalRowColor     = '#f3dbdf';
var hoverDigitalRowColor    = '#e68f9f';

////////////////////////////////////////////////////



	//Calls the function UpdateSortingDirection in parent search page
	function sortTable(columnNumber){
                parent.UpdateSortingDirection(columnNumber);
	}

	//Gets the column and sorting directions and uses eval to
	//dynamically display sorting arrow next to column heading
	function showSortArrow(){

                var gifName;
		//alert("showing sorting arrow");

		//get the column and sorting direction from parent
		sortingColumn = parent.document.forms[0].sortColumn.value;
		sortingDirection = parent.document.forms[0].sortDirection.value;

		//Set default sorting column/direction
		//   Column='Create Date' (6)
		//   Direction='Descending'
		if (sortingColumn=="") sortingColumn="6";
		if (sortingDirection=="") sortingDirection="Descending";

		//build image html
                //alert("span id:[" + "heading"+ sortingColumn +"]");
                var spanName = "heading" + sortingColumn;
		if (sortingDirection=="Ascending"){
			gifName = "<%= inf.getImageDirectory() %>" + "ascend.gif";
		} else {
			gifName = "<%= inf.getImageDirectory() %>" + "descend.gif";
		}

		//build innerHTML text
		iText = "<img src = " + gifName + ">";
                //width='14' height='7' border='0' hspace='0' vspace='0' align='absmiddle'

		//dynamically place the arrow text in the span of its table heading
                var inTextCommand = "document.all." + spanName + ".innerHTML = '" + iText + " ';"
		//alert(inTextCommand);
                eval(inTextCommand);

	}

	//function to shade table from the main frame
	function shadeRowsFromMainFrame(){
		shadeRows(<%=searchResults.size()%>);
	}


	//Shades the rows of the project search results table
	function shadeRows(numberRows){

		//iterate through table and shade alternate
		//rows depending on the Product Type (Physical/Digital)
		//that has been selected.
		var lightShade;
		var darkShade;
		if (parent.getIsPhysical()) {
			lightShade = lightPhysicalRowColor;
			darkShade  = darkPhysicalRowColor;
		} else {
			lightShade = lightDigitalRowColor;
			darkShade  = darkDigitalRowColor;
		}

		for (c=0; c<numberRows; c++){
			mod = c%2;
			if (mod==0){
				str = "document.all.projectRow" + c + ".style.backgroundColor = '" + lightShade + "'";
			} else {
				str = "document.all.projectRow" + c + ".style.backgroundColor = '" + darkShade + "'";
			}
			//alert(str);
			eval(str);
		}

	}

	function mouseOnSearchRow(row){
		previousBgColor = row.style.backgroundColor;
		if (parent.getIsPhysical()) {
			row.style.backgroundColor = hoverPhysicalRowColor;
		} else {
			row.style.backgroundColor = hoverDigitalRowColor;
		}
	}

	function mouseOutSearchRow(row){
		row.style.backgroundColor = previousBgColor;
	}


</script>



</head>

<script for="document" event="onkeydown" language="JavaScript">
	checkShortcut();
</script>

<% //start body %>
<body onload="showSortArrow();shadeRows(<%=searchResults.size()%>);" topmargin="0" leftmargin="0" STYLE="background-color: wheat" >

<script>
//set loaded var to 0
var loaded = 0;
</script>

<%
 if (context.getSessionValue("searchResults") != null) { %>
  <center>
       <font color="red">
       <b>Results Count:  <%=searchResults.size()%></b>
       </font>
  </center>
 <% } %>
  
 <%//
 if (searchResults.size()>200) { %>
       <center>
       <br>
       <font color="red">
       <b>
       Your search has exceeded the limit of 200 Projects. The first 200 Projects are displayed below.
       <br>
       You may need to narrow your search if your Project is not listed.
       </b>
       </font>
       </center>
<% } %>


<form name="projectSearchResultsForm">

<div name='resultsTable' id='resultsTable'>

<table width="100%" border="0" cellspacing=0 cellpadding=0 style="padding-bottom:4px;padding-top:4px;padding-right:4px;padding-left:4px">
    <tr>
	<td>
      <% // Search Results %>
	<table STYLE="border: outset 3px #bbbbbb;" width="100%">
		<tr bgcolor="#bbbbbb">
		<td style="width:60px" nowrap>

                <a id="sort1" href="javascript: if (loaded==1) sortTable(0);">
		<b>Project ID</b>
		</a><span id="heading0"></span></td>

		<td><a id="sort2" href="javascript: if (loaded==1) sortTable(1);">
		<b>First Name</b></a><span id="heading1"></td>

		<td><a id="sort3" href="javascript: if (loaded==1) sortTable(2);">
		<b>Last/Group Name</b></a><span id="heading2"></td>

                <td><a id="sort4" href="javascript: if (loaded==1) sortTable(3);">
		<b>Project Description</b></a><span id="heading3"></td>

		<td><a id="sort5" href="javascript: if (loaded==1) sortTable(4);">
		<b>Project Title</b></a><span id="heading4"></td>

		<td><a id="sort6" href="javascript: if (loaded==1) sortTable(5);">
		<b>Label</b></a><span id="heading5"></td>

		<td style="width:30px"><b>Oper<br>Co</b></td>
		<td style="width:34px"><b>Super Label</b></td>
		<td style="width:34px"><b>Sub Label</b></td>
                <td style="width:34px"><b>Dist Co</b></td><!--ITS 1069 - jo-->
                <td style="width:45px">
                <a href="javascript: if (loaded==1) sortTable(6);">
                <b>Create Date</b></a><span id="heading6"/></a></td>

                </tr>

	<% // Iterate through the rows %>
	<% for (int counter=0; counter<searchResults.size(); counter++){
                  ProjectSearch project = (ProjectSearch)(searchResults.elementAt(counter));
        %>
               	<tr id="projectRow<%=counter%>"
		    onDblClick="parent.selectProject(<%=counter%>);"
                    onMouseOver="mouseOnSearchRow(this);"
                    onMouseOut="mouseOutSearchRow(this);"
                    style="cursor: hand">
		<td><div id="ProjectId<%=counter%>">
			<%=project.getRMSProjectNo() %>
		</div></td>
		<td><div id="ArtistFirstName<%=counter%>"><%=project.getArtistFirstName() %></div></td>
		<td><div id="ArtistLastName<%=counter%>"><%=project.getArtistLastName() %></div></td>
		<td><div id="ProjectDesc<%=counter%>"><%=project.getProjectDesc() %></div></td>
		<td><div id="Title<%=counter%>"><%=project.getTitle() %></div></td>
		<td><div id="Label<%=counter%>"><%=MilestoneHelper.getStructureName(project.getMSLabelId()) %></div></td>
		<td><div id="OperCompany<%=counter%>"><%=project.getOperCompany() %></div></td>
		<td><div id="SuperLabel<%=counter%>"><%=project.getSuperLabel() %></div></td>
		<td><div id="SubLabel<%=counter%>"><%=project.getSubLabel() %></div></td>
                <td><div id="DistCo<%=counter%>"><%=MilestoneHelper_2.getLabelDistCo(project.getMSLabelId()) %></div><!--ITS 1069 - jo-->
                </td>
	        <td><div id="CreateDate<%=counter%>">
			<% if ( MilestoneHelper.getFormatedDate(project.getCreateDate()).equals("9/9/99")){%>
				&nbsp;
			<% } else { %>
				<%=MilestoneHelper.getFormatedDate(project.getCreateDate()) %>
			<% } %>
		</div>


		<!-- add additional values to dynamically populate Selection screen with
                     when the 'Revise' option was selected -->
		<input type="hidden" id="EnvironmentId<%=counter%>" value="<%=project.getMSEnvironmentId() %>">
		<input type="hidden" id="CompanyId<%=counter%>"     value="<%=project.getMSCompanyId() %>">
		<input type="hidden" id="DivisionId<%=counter%>"    value="<%=project.getMSDivisionId() %>">
		<input type="hidden" id="LabelId<%=counter%>"       value="<%=project.getMSLabelId() %>">
		<input type="hidden" id="Environment<%=counter%>"   value="<%=MilestoneHelper.getStructureName(project.getMSEnvironmentId()) %>">
		<input type="hidden" id="Company<%=counter%>"       value="<%=MilestoneHelper.getStructureName(project.getMSCompanyId()) %>">
		<input type="hidden" id="Division<%=counter%>"      value="<%=MilestoneHelper.getStructureName(project.getMSDivisionId()) %>">
		<input type="hidden" id="PandD<%=counter%>" value="<%=project.getPD_Indicator() %>">

		<!--operCompany -->
		<%
		String operCompanyDisplayString = "";
		//if an Unknown project then set the operating company to be '***'
		if (project.getOperCompany().equals("***")){
		  operCompanyDisplayString = "***";
		} else {

		  LookupObject oc = MilestoneHelper.getLookupObject(project.getOperCompany(), Cache.getOperatingCompanies());
      		  String ocAbbr = "";
      		  String ocName = "";

                  //If a new operating company then no description may exist,
                  //so only print the abbreviation
                  //jo 4/19/2005  ITS 1094
                  if (oc==null){
                      ocAbbr = project.getOperCompany();
                  } else {
                      if (oc.getAbbreviation() != null)
                         ocAbbr = oc.getAbbreviation();
                      if (oc.getName() != null)
                         ocName = ":" +  oc.getName();
                  }
      		  operCompanyDisplayString = ocAbbr + ocName;
		}
		%>

		<input type="hidden" id="operCompanyDisplayString<%=counter%>" value="<%=operCompanyDisplayString%>">

		</td>

		</tr>
	<% } %>

	</table>


         </td>
	</tr>
</table>
</div>

</form>


<!-- set variable to 1 to verify that the project search screen has been fully loaded -->
<script>
loaded = 1;
//setup the hyperlinks on the sorting headers
/*document.all.sort1.style.visibility = "visible";
document.all.sort2.style.visibility = "visible";
document.all.sort3.style.visibility = "visible";
document.all.sort4.style.visibility = "visible";
document.all.sort5.style.visibility = "visible";
document.all.sort6.style.visibility = "visible";*/
</script>


<!-- Display all parent IFRAMES -->
<script>
//closeIFrameFromSelectionScreen
if (parent.parent.document.all["ProjectSearchLayer"].style.visibility=="hidden"){
	layer = eval(parent.parent.document.all["ProjectSearchLayer"]);
	layer.style.visibility = "Visible";
}
</script>

<%@ include file="template-bottom-html.shtml"%>
