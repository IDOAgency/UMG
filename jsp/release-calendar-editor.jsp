<%
	/*
		this include (template-top-page.jsp) does the following:
			extends com.universal.milestone.MilestoneJSP
			import="com.techempower.*,com.techempower.gemini.*, com.universal.milestone.*, java.util.*"
			gets the context, form formValidation and message
	 */
%>

<%@ include file="template-top-page.shtml"%>

<%
	// This variable holds the value to be placed in the <title> html tag for the include file:
	// template-top-html.jsp.
	String htmlTitle = "Release Calendar screen";

        //Used by include-selection-notepad
        String editorName = MilestoneConstants.CMD_RELEASE_CALENDAR_EDIT;
        
      // release upgrade 8.1
    User userRC = (User) context.getSession().getAttribute("user");
    String adminDisplayStyle = "";
    if(MilestoneConstants.IsGDRSactive)
    {	  	   
      adminDisplayStyle =  (userRC != null && userRC.getAdministrator() == 1) ? "block" : "none";
    }  
%>

<%@ include file="template-top-html.shtml"%>


<% //include js files holding javascript functions %>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-toggle.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-toggle-button.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-format.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-focus-field.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-length.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-convert-date.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-integer.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-shortcut.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>calendar.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>bom-editor-javascript.js"></script>

<style>
a.pinkLink:link {color: #990099; font-size: 8pt; font-family: "Arial","Helvetica"; text-decoration:none;}
a.pinkLink:visited {color: #990099; font-size: 8pt; font-family: "Arial","Helvetica"; text-decoration:none;}
a.pinkLink:active {color: #990099; font-size: 8pt; font-family: "Arial","Helvetica"; text-decoration:none;}
a.pinkLink:hover {color: red; font-size: 8pt; font-family: "Arial","Helvetica"; text-decoration:none;}
</style>

<script type="text/javascript" language="JavaScript">

<%= context.getDelivery("selectionArrays") %>

function compareText (option1, option2) {
  if (option1.text.toUpperCase() == "ALL")
    return -1;
  else if (option2.text.toUpperCase() == "ALL")
    return 1;
  else
    return option1.text.toUpperCase() < option2.text.toUpperCase() ? -1 : option1.text.toUpperCase()  > option2.text.toUpperCase() ? 1 : 0;
}

function sortSelect (select, compareFunction) {
  if (!compareFunction)
    compareFunction = compareText;
  var options = new Array (select.options.length);
  for (var i = 0; i < options.length; i++)
    options[i] =
      new Option (
        select.options[i].text,
        select.options[i].value,
        select.options[i].defaultSelected,
        select.options[i].selected
      );
  options.sort(compareFunction);
  select.options.length = 0;
  for (var i = 0; i < options.length; i++)
    select.options[i] = options[i];
}

function clearCombo(obj)
{
	var i;
  for(i=obj.options.length; i>=0; i--)
		obj.options[i] = null;
   	obj.selectedIndex = -1;
} //end function clearCombo()

function populateEnvironment(obj, index)
{
   clearCombo(document.forms[0].environment);

   var j = 0;
   for(var i=0; i < familyArray[index].length; i=i+2){
       document.forms[0].environment.options[j] = new Option(familyArray[index][i+1], familyArray[index][i]);
       j++;
   }
   sortSelect(document.forms[0].environment,compareText);
   document.forms[0].environment.options[0].selected=true;

} //end function populateEnvironment()

function clickFamily(obj)
{

  if(document.forms[0].environment)
    clearCombo(document.forms[0].environment);

  if(document.forms[0].company)
    clearCombo(document.forms[0].company);

  if(document.forms[0].Label)
    clearCombo(document.forms[0].Label);

  if(document.forms[0].environment)
  {
    populateEnvironment(document.forms[0].environment, obj[obj.selectedIndex].value);
  }

  if(document.forms[0].company)
  {
    populateCompanyFromFamily(document.forms[0].company, obj[obj.selectedIndex].value);
  }

  if(document.forms[0].Label)
  {
    populateLabelFromFamily(document.forms[0].Label,obj[obj.selectedIndex].value);
  }

  return true;
} //end function clickFamily()

function SetValuesIndex(obj,valueStr)
//////////////////////////////////////////////////////
{

 // if null object
  if(obj == null || obj.options.length == 0)
    return;

  // return if empty value
  if(valueStr == "" || valueStr == "0" || valueStr == "-1" || valueStr == "0,0")
  {
    obj.options[0].selected = true;
    return;
  }

  var opts = obj.options;
  for(var i=0; i < opts.length; i++)
  {
     if(opts[i].value == valueStr)
     {
       opts[i].selected = true;
       return;
     }
  }
}


function clickEnvironment(obj)
{

  if (obj.selectedIndex == 0 )
    return clickFamily(document.forms[0].family);

//alert("clickEnvironment");
  if(document.forms[0].company) {
//alert("company field found");
    clearCombo(document.forms[0].company);
    populateCompany(document.forms[0].company, obj[obj.selectedIndex].value);
    //clickCompany(document.forms[0].company);
  }
  //else {
    if((document.forms[0].Label)) {
      //alert("step1");
      clearCombo(document.forms[0].Label);
      populateLabelFromEnvironment(document.forms[0].Label,obj[obj.selectedIndex].value)
    }
  //}

  return true;
} //end function clickEnvironment()

function SubmitReleaseCalendar(){
  showWaitMsg();  // mc its 966 show please wait message
  document.forms[0].submit();
}

function GoToSelection(selectionID,selStatus){

//"release-calendar"
// MSC 12/12/03 Added releaseCalendar parm to force the schedule screen
// to display the page the selection id is located on
// msc 12/12/03 Added the selection status as the attribute value to
// allow the updating of the notepad query string to filter in closed/cancels
document.location = "home?cmd=<%=MilestoneConstants.CMD_SCHEDULE_EDITOR%>&showNotepad=true&selection-id=" + selectionID
         + "&releaseCalendar=" + selStatus;

}

function PreviousMonthReleaseCalendar(){
  //alert(document.forms[0].monthList.value);
  if (document.forms[0].monthList.value == 1){
    document.forms[0].monthList.value = 12;
    document.forms[0].yearList.value--;
  } else {
    document.forms[0].monthList.value--;
  }

  showWaitMsg();  // mc its 966 show please wait message

  //alert(document.forms[0].monthList.value);
  document.forms[0].submit();
}

function NextMonthReleaseCalendar(){
  //alert(document.forms[0].monthList.value);
  if (document.forms[0].monthList.value == 12){
    document.forms[0].monthList.value = 1;
    document.forms[0].yearList.value++;
  } else {
    document.forms[0].monthList.value++;
  }

  showWaitMsg();  // mc its 966 show please wait message

  //alert(document.forms[0].monthList.value);
  document.forms[0].submit();
}

function StatusSelected(checkbox){
   if(document.all.AllStatus)
   {
     if (checkbox.id=="AllStatus"){
         document.all.ActiveStatus.checked = document.all.AllStatus.checked;
         //document.all.TBSStatus.checked = document.all.AllStatus.checked;
         document.all.ClosedStatus.checked = document.all.AllStatus.checked;
         //document.all.CancelledStatus.checked = document.all.AllStatus.checked;
     } else if (checkbox.id!="AllStatus" && document.all.AllStatus.checked) {
         document.all.AllStatus.checked = false;
     }
   }
}

function ClearFilters(){
     if(document.all.AllStatus)
       document.all.AllStatus.checked = false;
     document.all.ActiveStatus.checked = true;
     //document.all.TBSStatus.checked = false;
     document.all.ClosedStatus.checked = false;
     //document.all.CancelledStatus.checked = false;

     var myDate = new Date();
     document.forms[0].monthList.value = myDate.getMonth()+1;
     document.forms[0].yearList.value = myDate.getFullYear();

     //clearCombo(document.forms[0].environment);
     document.forms[0].environment.value = 0;
     //clearCombo(document.forms[0].family);
     document.forms[0].family.value = 0;

     document.forms[0].contact.value = 0;
     document.forms[0].configurationList.value = "All";
     document.forms[0].releaseType[2].checked = true;
     document.forms[0].productType[2].checked = true;

     showWaitMsg();  // mc its 966 show please wait message

     document.forms[0].submit();
}

/*
function checkField( pField )
{
  var bReturn = true;
  switch( pField.name )
  {
    case "Date":
      bReturn = bReturn && checkDate( pField, "Date", true );
      break;
    case "DueDate":
      bReturn = bReturn && checkDate( pField, "Due Date", true );
      break;
  }
  return bReturn;
} //end function checkField()

function submitResize()
{
	// get the command from the backend, change it to the jsp in
  //the global Java script varables and here
  parent.top.bottomFrame.location = "home?cmd=notepad-toggle&notepadType=<%=MilestoneConstants.NOTEPAD_BOM_VISIBLE%>&lastCommand=<%=form.getRenderableValue("cmd")%>";
} //end function submitResize()

function submitSearch()
{
  //if(document.all.StreetDateSearch.value != "")
  //{
  //  var tstResult = checkFormat( convertDate( document.all.StreetDateSearch.value ), "Street Date Search", "date", "" );
  //}
  //if(tstResult || document.all.StreetDateSearch.value=="")
  //{
    //document.all.StreetDateSearch.value = convertDate( document.all.StreetDateSearch.value );
  document.forms[0].cmd.value = "<%=MilestoneConstants.CMD_BOM_SEARCH%>";

  //jo its 445 1/30/2003
  //set the hidden variables to the form values:
  document.forms[0].macArtistSearch.value = document.forms[0].ArtistSearch.value;
  document.forms[0].macTitleSearch.value = document.forms[0].TitleSearch.value;
  document.forms[0].macPrefixSearch.value = document.forms[0].PrefixSearch.value;
  document.forms[0].macSelectionSearch.value = document.forms[0].SelectionSearch.value;
  document.forms[0].macUPCSearch.value = document.forms[0].UPCSearch.value;
  document.forms[0].macStreetDateSearch.value = document.forms[0].StreetDateSearch.value;
  document.forms[0].macStreetEndDateSearch.value = document.forms[0].StreetEndDateSearch.value;
  document.forms[0].macCompanySearch.value = document.forms[0].CompanySearch[document.forms[0].CompanySearch.selectedIndex].value;
  document.forms[0].macLabelSearch.value = document.forms[0].LabelSearch[document.forms[0].LabelSearch.selectedIndex].value;
  document.forms[0].macContactSearch.value = document.forms[0].ContactSearch[document.forms[0].ContactSearch.selectedIndex].value;

 if (document.forms[0].SubconfigSearch.length>0){
	document.forms[0].macSubconfigSearch.value = document.forms[0].SubconfigSearch[document.forms[0].SubconfigSearch.selectedIndex].value;
 } else {
	document.forms[0].macSubconfigSearch.value = "";
 }
 //jo its 445 1/30/2003

  document.forms[0].submit();
  //}
} //end function submitSearch

*/

/*
function submitList( pType )
{
  document.forms[0].OrderBy.value = pType;
  document.forms[0].cmd.value = "<%=MilestoneConstants.CMD_BOM_SORT%>";
  document.forms[0].submit();
} //end function submitList()
function submitGroupXXX( alpha, colNo )
{

  if( colNo == 0 || colNo == 7) // artist column
    document.forms[0].OrderBy.value = "0";
  if( colNo == 1 || colNo == 8 ) // title column
    document.forms[0].OrderBy.value = "1";

  document.forms[0].alphaGroupChr.value = alpha;
  document.forms[0].cmd.value = "<%=MilestoneConstants.CMD_BOM_SORT%>";
  document.forms[0].submit();
} //end function submitGroup()

function submitGroup( alpha, colNo )
{

  document.forms[0].OrderBy.value = colNo;
  document.forms[0].alphaGroupChr.value = alpha;
  document.forms[0].cmd.value = "<%=MilestoneConstants.CMD_BOM_GROUP%>";
  document.forms[0].submit();
} //end function submitGroup()
*/
</script>

<!-- JR - ITS #70 -->
<% //-- javascript customized for this particular page --%>
<script type="text/javascript" language="javascript">
// this gets the selection arrays from the delivery package for the
// dynamically created drop-dowms
<% //= context.getDelivery("selectionArrays") %>
</script>
<!-- JR - ITS #70 -->

<script type="text/javascript" language="javascript">
// this gets the selection arrays from the delivery package for the
// dynamically created drop-dowms
<% //= context.getDelivery("searchArray") %>

</script>

<link rel=stylesheet type="text/css" href="<%= inf.getHtmlDirectory() %>global.css" title="GlobalStyle">
</head>

<script for="document" event="onkeydown" language="JavaScript">
//checkShortcut();
</script>

<body bgcolor="white" text="black" topmargin="0" leftmargin="0" onKeyPress="checkForEnter( 'SubmitReleaseCalendar()' );" >

<%=form.renderStart()%>
<%=form.getElement("cmd")%>
<%//= form.getElement("selectionID") %>
<%//=form.getElement("OrderBy")%>

<!-- msc 09/10/03 -->
<%//= form.getElement("bomLastUpdateCheck") %>

<!-- msc 12/13/01 -->
<input type="hidden" name="alphaGroupChr" value="">

<table width="800" cellpadding="0" border="0">
<tr valign="left" align="left">
   <!-- <td width="280">

      This part of the code is made an include to make it easy to manage this page.
       The include just gets and puts the code here as it was written here.
      <% //@ include file="include-newSelection.shtml" %>

   </td> -->
  <td rowspan="2" width="10"><img src="<%= inf.getImageDirectory() %>pixelshim.gif" width="10"></td>
  <td width="200"><span class="title">Release Calendar</span></td>
  <td nowrap colspan=3>
    <a href="javascript:PreviousMonthReleaseCalendar();">
    <img name="Print" id="Print" src="<%=inf.getImageDirectory()%>Rew_On.gif" border=0 hspace=0 vspace=0 align="absmiddle">
    </a>
    <%= form.getElement("monthList") %>
    <%= form.getElement("yearList") %>
    <a href="javascript:NextMonthReleaseCalendar();">
    <img name="Print" id="Print" src="<%=inf.getImageDirectory()%>Fwd_On.gif" border=0 hspace=0 vspace=0 align="absmiddle">
    </a>
  </td>
  <td nowrap colspan=2>
    <div id="printWindow"  align="center">
    <a href="javascript:SubmitReleaseCalendar();"
       onMouseOver="Javascript:mtbSearch.over();return true;"
       onMouseOut="Javascript:mtbSearch.out()"
       onClick="Javascript:mtbSearch.click();return true;">
    <img name="Search" id="Search" src="<%=inf.getImageDirectory()%>Search_On.gif" border=0 hspace=0 vspace=0 align="absmiddle">
    </a>
    <script type="text/javascript" language="JavaScript">
       	var mtbSearch = new ToggleButton(document, '<%= inf.getImageDirectory() %>', 'Search', 'JavaScript:SubmitReleaseCalendar()', 66,14);
    </script>

    <a href="javascript:ClearFilters();"
       onMouseOver="Javascript:mtbClear.over();return true;"
       onMouseOut="Javascript:mtbClear.out()"
       onClick="Javascript:mtbClear.click();return true;">
    <img name="Clear" id="Clear" src="<%=inf.getImageDirectory()%>Clear_On.gif" border=0 hspace=0 vspace=0 align="absmiddle">
    </a>
    <script type="text/javascript" language="JavaScript">
       	var mtbClear = new ToggleButton(document, '<%= inf.getImageDirectory() %>', 'Clear', 'JavaScript:ClearFilters()', 66,14);
    </script>

    <!--<a href="home?cmd=<%=MilestoneConstants.CMD_BOM_PRINT_PDF%>">-->
     <!--  PRINT TO PDF -->
    <a href="javascript:window.print();"
       onMouseOver="Javascript:mtbPrint.over();return true;"
       onMouseOut="Javascript:mtbPrint.out()">
   <img name="Print" id="Print" src="<%=inf.getImageDirectory()%>Print_On.gif" border=0 hspace=0 vspace=0 align="absmiddle">
    </a>
    <script type="text/javascript" language="JavaScript">
       	var mtbPrint = new ToggleButton(document, '<%= inf.getImageDirectory() %>', 'Print', 'JavaScript:window.print()', 66,14);
    </script>

    </div>
  </td>
</tr>

<tr>
  <% //MAIN TABLE %>
  <td bgcolor="lavender" valign="top" colspan="7" width="100%">
    <table width=100% height="100%" cellpadding="0" cellspacing=0 border="0">
    <tr valign="left" align="left">
      <td style="padding-left:10px;padding-top:10px" class="label" nowrap>Releasing Family</td>
      <td style="padding-left:10px;padding-top:10px"><%= form.getElement("family") %></td>
      <td style="padding-left:10px;padding-top:10px" class="label" nowrap>Format/Schedule Type</td>
      <td style="padding-left:10px;padding-top:10px" ><%= form.getElement("configurationList") %></td>
      <td style="padding-left:10px;padding-top:10px" class="label" nowrap>Release Type</td>
      <td style="padding-top:5px"><%= form.getElement("ReleaseType") %></td>
    </tr>

    <tr valign="left" align="left">
      <td style="padding-left:10px;padding-top:5px" valign=top class="label" nowrap>Environment</td>
      <td style="padding-left:10px;padding-top:5px" valign=top><%= form.getElement("environment") %></td>
      <td style="padding-left:10px;padding-top:5px" valign=top rowspan=1 colspan=2 class="label" nowrap>
      Status
      <% // form.getElement("AllStatus") <BR>
         // &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      %>

      <%= form.getElement("ActiveStatus") %>
      <%//= form.getElement("TBSStatus") %>
      <%= form.getElement("ClosedStatus") %>
      <%//= form.getElement("CancelledStatus") %>
      </td>
      <td style="padding-left:10px;padding-top:10px;display:<%=adminDisplayStyle %>" class="label" >Product Type</td>
      <td style="padding-top:5px;display:<%=adminDisplayStyle %>"><%= form.getElement("ProductType") %></td>

    </tr>

    <tr valign="left" align="left">
      <td style="padding-left:10px;padding-top:5px" valign=top class="label" nowrap>Label Contact</td>
      <td style="padding-left:10px;padding-top:5px" valign=top><%= form.getElement("contact") %></td>

      <td style="padding-right:15px;padding-top:15px" align="right" style="padding-left:5px; padding-top:10px" colspan=4> Legend: <img src="<%= inf.getImageDirectory() %>p-icon.gif" width=12> Physical Product<% if(!MilestoneConstants.IsGDRSactive || (userRC != null && userRC.getAdministrator() == 1)) {%>, <img src="<%= inf.getImageDirectory() %>d-icon.gif" width=12> Digital Product<%}%></td>

    </tr>

    <tr>
      <td style="padding-top:10px" colspan=7 width="100%" align="center">


<table width=100%>
<tr>
<%

//convert keys to arrays and sort them, then display them
/*
      //get an array from the vector
      Object[] csoArray = csoVector.toArray();

      //sort the array
      Arrays.sort(csoArray, new CorpStructNameComparator());
*/

String monthHeader = "";
String dateHeader = "";
String myClass = "";
String myBullet = "";

Vector currentSelections;
HashMap currentMonthHash;
Iterator monthIt;

HashMap calendarHash = (HashMap)context.getDelivery("ReleaseCalendarSelections");
Object[] monthsArray = calendarHash.keySet().toArray();
Arrays.sort(monthsArray, new ReleaseCalendarMonthHeaderComparator());
for(int i = 0; i < monthsArray.length; i ++){
  monthHeader = monthsArray[i].toString();
  currentMonthHash = (HashMap)calendarHash.get(monthHeader);
  Object[] datesArray = currentMonthHash.keySet().toArray();
  Arrays.sort(datesArray, new ReleaseCalendarDateHeaderComparator());
  //monthIt = currentMonthHash.keySet().iterator();
%>
<td width="33%" valign=top>
<table width=100%>
<tr><td align="center" bgcolor="black"><font color="white"><%= monthHeader.replace('-',' ') %></font></td></tr>
<%
  //while(monthIt.hasNext()){
  for(int j = 0; j < datesArray.length; j ++){
    dateHeader = datesArray[j].toString(); //monthIt.next().toString();
    currentSelections = (Vector)currentMonthHash.get(dateHeader);
    %>
    <tr><td align="left" bgcolor="silver" class="label">&nbsp;&nbsp;<%= monthHeader.substring(0,monthHeader.indexOf("-"))
             + " " +  dateHeader.substring(0,dateHeader.indexOf("-"))
             + ", " + monthHeader.substring(monthHeader.indexOf("-")+1)
             + "    " + dateHeader.substring(dateHeader.indexOf("-")+1) %></td></tr>
    <%
    for(int a = 0; a < currentSelections.size(); a++)
    {
      Selection selection = (Selection)currentSelections.get(a);
      if (selection.getIsDigital()){
        myClass = "pinkLink";
        myBullet = "<img src='" + inf.getImageDirectory() + "d-icon.gif' width=12>&nbsp;";
      } else {
        myClass = "";
        myBullet = "<img src='" + inf.getImageDirectory() + "p-icon.gif' width=12&nbsp;";
      }
      String selectionPrefix = "";
      //System.out.println(selectionPrefix);
      if (selection.getPrefixID() != null)
        selectionPrefix = selection.getPrefixID().getAbbreviation();
%>
<tr><td>
        <table cellpadding=0 cellspacing=0 width=100%>
        <tr>
        <td style="padding-top:2px" valign="top" width="15"><%=myBullet%></td>
        <td style="padding-left=3px" valign="top">
         <a class="<%=myClass%>"
            title="Cfg: <%= MilestoneHelper.escapeDoubleQuotesForHtml(selection.getSelectionConfig().getSelectionConfigurationName()) %> / <%= MilestoneHelper.escapeDoubleQuotesForHtml(selection.getSelectionSubConfig().getSelectionSubConfigurationName()) %>; UPC: <%= MilestoneHelper.escapeDoubleQuotesForHtml(selection.getUpc()) %>; Prefix/Local Prod #: <%= MilestoneHelper.escapeDoubleQuotesForHtml(selectionPrefix) %> / <%= MilestoneHelper.escapeDoubleQuotesForHtml(selection.getSelectionNo()) %>"
            href="javascript:GoToSelection('<%=selection.getSelectionID()%>','<%=selection.getSelectionStatus().getName()%>');"><%= selection.getArtist() + " / " + selection.getTitle() + " / " + selection.getSelectionConfig().getSelectionConfigurationName() %></a></td>
        </tr>
        </table>
    </td>
</tr>
<%
    }
%>
<tr><td></td></tr>
<%
  }
%>
</table>
</td>
<%
}
%>
</tr>
</table>




      </td>
    </tr>

    </table>
  </td>
</tr>

</table>

<!--
<div class="search" id="ProjectSearchLayer" style="position:absolute;visibility:hidden;width:800px;height:450px;z-index:10;left:75px;top:50px;border: outset wheat 5px">
        <IFRAME HEIGHT="450" width="800" ID="ProjectSearchFrame" name="ProjectSearchFrame"  FRAMEBORDER=0 SCROLLING=NO >">
        </IFRAME>
</div>
-->


<% //DIV definitions %>
<% //start search div %>
<%//@ include file="selection-search-elements.shtml"%>
<% //end search div %>
<% //end DIV DEFINITIONS %>

<%=form.renderEnd()%>

<script>
//////////////////////////////////////////////////////
// if family selected index is not all
// perform family click function
//////////////////////////////////////////////////////
var envValue = "";
// save environment selected index
if(document.all.environment)
    envValue = document.all.environment[document.all.environment.selectedIndex].value;

if(document.all.family && document.all.family.selectedIndex > 0)
    clickFamily(document.all.family);

// restore environment value
if(envValue != "")
   SetValuesIndex(document.all.environment,envValue)

</script>

<%@ include file="include-bottom-html.shtml"%>
