<%@ include file="template-top-page.shtml"%>

<% /* This variable holds the value to be placed in <title> html tag </title>
      for the include file: template-top-html.jsp */
   String htmlTitle = "Templates screen";
%>

<%@ include file="template-top-html.shtml"%>

<script>
// JR - ITS# 683
function HideLayer()
{
 mtbSearch.click();
 toggle( 'templateSearchLayer', 'templateNameSrch');
}
</script>

<script type="text/javascript" language="JavaScript">
  //global variables needed in include division-editor-javascript.js
  var form;
  var sort = "<%= MilestoneConstants.CMD_TEMPLATE_SORT%>";
  var editor = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_TEMPLATE_EDITOR)%>";
  var notepad = "<%= MilestoneConstants.NOTEPAD_TEMPLATES%>";
  var cmd = "<%= form.getRenderableValue("cmd")%>";
	var deleteCommand = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_TEMPLATE_EDIT_DELETE) %>";
	var deleteTasksCommand = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_TEMPLATE_EDIT_DELETE_TASK) %>";
	var copyCommand = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_TEMPLATE_EDIT_COPY) %>";
  var save = "<%= MilestoneConstants.CMD_TEMPLATE_EDIT_SAVE%>";
  var saveNew = "<%= MilestoneConstants.CMD_TEMPLATE_EDIT_SAVE_NEW%>";
	var saveCopy = "<%= MilestoneConstants.CMD_TEMPLATE_COPY_SAVE%>";
  var editNew = "<%= MilestoneConstants.CMD_TEMPLATE_EDIT_NEW%>";
  var search = "<%= MilestoneConstants.CMD_TEMPLATE_SEARCH%>";
  var next = "<%= inf.getServletCmdURL("notepad-next")%>";
  var previous = "<%= inf.getServletCmdURL("notepad-previous")%>";
  var lRoot;

function processLoad()
{
  form = document.forms[0];
}
</script>

<% //These are js include files holding the global java script functions %>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-toggle.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-toggle-button.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-format.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-focus-field.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-length.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-convert-date.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-integer.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-shortcut.js"></script>
<script type="text/javascript" language="javascript" src="<%= inf.getHtmlDirectory() %>blank-template-editor-javascript.js"></script>


<SCRIPT type="text/javascript" language="JavaScript">
function submitResize()
{
  //
  //note that as with milestone v2, changes the user makes are not kept when resize is clicked
  //
  parent.top.bottomFrame.location = "home?cmd=notepad-toggle&notepadType=<%=MilestoneConstants.NOTEPAD_TEMPLATES_VISIBLE%>&lastCommand=<%=form.getRenderableValue("cmd")%>";
}

//gets the Java Script Function createChildren() which is generated dynamicaly in the back end
<%= context.getDelivery("Configuration") %>

function Node( pID, pName, pProdType, pChildren )
{
  this.ID = pID;
  this.Name = pName;
  this.Children = pChildren;
  this.ProdType = pProdType;
}

createChildren();

function clearBox( pSelection )
{
  pSelection.options.length = 0;
}

function initSearchConfigs()
{
  clearBox( document.forms[0].configurationSrch );

  var prodType;
  prodType = 2;

  if (document.all.ProdTypeSearch[0].checked)
    prodType = 0;
  else if (document.all.ProdTypeSearch[1].checked)
    prodType = 1;

  var currIndex = 1;
  var newOption = new Option( 'All', '0', '', '');
  document.forms[0].configurationSrch.options[0] = newOption;
  for( i = 0; i < lRoot.Children.length; i++ )
  {
    if (lRoot.Children[i].ProdType == prodType || prodType == 2 || lRoot.Children[i].ProdType == 2)
    {
      var newOption = new Option( lRoot.Children[ i ].Name, lRoot.Children[ i ].ID, lRoot.Children[ i ].ID == 0, lRoot.Children[ i ].ID == 0 );
      document.forms[0].configurationSrch.options[ currIndex ] = newOption;
      currIndex++;
    }
  }
}

</SCRIPT>

<link rel="stylesheet" type="text/css" href="<%= inf.getHtmlDirectory() %>global.css" title="GlobalStyle">
</head>
<script for=document event=onkeydown language="JavaScript">
checkShortcut();
</script>

<body bgcolor="white" text="black" topmargin=0 leftmargin=0 onLoad="processLoad();">

<%= form.renderStart()%>
<%= form.getElement("cmd")%>

<table width="780" cellpadding=0 border=0>
  <tr valign="middle" align="left" width="100%">
    <td width="280">
      <div align="left">
        <a href ="JavaScript:submitResize()"
          onMouseOver="Javascript:mtbToggle.over();return true;"
          onMouseOut="Javascript:mtbToggle.out()"
          onClick="Javascript:mtbToggle.click(); return true;">
          <img name="Toggle" id="Toggle" src="<%= inf.getImageDirectory() %>Toggle_On.gif" width=27 height=14 border=0 hspace=0 vspace=0 align="absmiddle">
        </a>
        <script type="text/javascript" language="JavaScript" >
          var mtbToggle = new ToggleButton(document, '<%= inf.getImageDirectory() %>', 'Toggle', "JavaScript:submitResize()", 27, 14);
        </script>
      </div>
    </td>
    <td rowspan=2 width=10><img src="<%= inf.getImageDirectory() %>pixelshim.gif" width=10></td>
    <td width="170">
      <SPAN class="title">Templates</SPAN>
    </td>
    <td width="85"></td>
    <td width="85">
		<div align="center">
			<a href="JavaScript:submitNew('New')"
				onMouseOver="Javascript:mtbNew.over();return true;"
				onMouseOut="Javascript:mtbNew.out()"
				onClick="Javascript:mtbNew.click(); return true;">
        <img name="New" src="<%= inf.getImageDirectory() %>New_On.gif" width="66" height="14" border="0" hspace="0" vspace="0" align="absmiddle">
      </a>
      <script type="text/javascript" language="JavaScript">
      	var mtbNew = new ToggleButton(document, '<%= inf.getImageDirectory() %>', 'New', "JavaScript:submitNew( 'New' )", 66,14);
      </script>
		</div>
	</td>
  </tr>
  <tr>
    <td valign="top">
<%
if(context.getDelivery("isNotePadVisible") == null || (context.getDelivery("isNotePadVisible") != null && ((Boolean)context.getDelivery("isNotePadVisible")).booleanValue()))
{%>
     <% //===============start navigation panel=========================== %>

     	<%@ include file="include-template-notepad.shtml" %>

     <% //===============end navigation panel=========================== %>
<%} //end notepad visibility check
%>
    </td>

    <!-- start selection form -->
	<td bgcolor="lavender" valign="top" colspan="6" width="100%">

    <!--MAIN TABLE-->
    <table width="516" bgcolor="lavender">
      <tr><td align="center"><b><h4>Milestone System<br><br><br>Please search for Templates on the left panel to this view </h4></b></td></tr>
    </table>
  </td>
</tr>
</table>

<div id="templateSearchLayer"  class="search" onKeyPress="checkForEnter( 'submitSearch()' );" style="visibility:hidden">
  <table width="100%" border=1 cellspacing=0 cellpadding=1>
    <tr>
      <td>
        <table width="100%" >
          <tr>
            <th colspan=2 >Schedule Template Search</th>
          </tr>
          <tr>
			<!-- JR - ITS# 683 -->
            <td class="label" >Label Name</td>
            <td><%= form.getElement("templateNameSrch")%></td>
          </tr>
          <tr>
            <!-- JP Digital AFE -->
            <td class="label" >Product Type</td>
            <td>
              <%= form.getElement("ProdTypeSearch")%>
            </td>
          </tr>
          <tr>
			<!-- JR ITS - 277 -->
            <td class="label" >Format/Schedule Type</td>
            <td>
              <%= form.getElement("configurationSrch")%>
            </td>
          </tr>
          <tr>
            <td class="label" >Owner</td>
            <td>
              <%= form.getElement("ownerSrch")%>
            </td>
          </tr>
          <tr>
            <td colspan=2>
              <input type="button" value="Submit Search" onClick="submitSearch();">
              <input type="button" name="SubmitCancel" id="SubmitCancel" value="Cancel" onClick="Javascript:HideLayer()">
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</div>

 <%=form.renderEnd()%>

<%@ include file="include-bottom-html.shtml"%>

