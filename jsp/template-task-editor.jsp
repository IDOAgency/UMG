<%@ include file="template-top-page.shtml"%>

<% /* This variable holds the value to be placed in <title> html tag </title>
      for the include file: template-top-html.jsp */
   String htmlTitle = "Templates Task screen";

   Template currentTemplate = (Template)context.getSessionValue("Template");
   Vector templateRights = new Vector();

   if (currentTemplate != null)
   {
     templateRights = (Vector)context.getDelivery("templateRights");
   }

	 // this will check the access right for a user
	 boolean saveVisible = false;
   boolean deleteVisible = false;
   boolean copyVisible = false;
   boolean newVisible = false;

   //indicate if it is a key task
   boolean isKeyTask = true;
   //indicate who is the owner Lable or UML
   boolean isLabelOwner = true;

	 saveVisible = ((String)context.getDelivery("saveVisible")).equals("true");
   deleteVisible = ((String)context.getDelivery("deleteVisible")).equals("true");
   copyVisible = ((String)context.getDelivery("copyVisible")).equals("true");
   newVisible = ((String)context.getDelivery("newVisible")).equals("true");
%>

<%@ include file="template-top-html.shtml"%>

<script type="text/javascript" language="JavaScript">
  //global variables needed in include division-editor-javascript.js
  var form;

  var addTask = "<%= MilestoneConstants.CMD_TEMPLATE_ADD_TASK%>";
  var editor = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_TEMPLATE_TASK_EDITOR)%>";
  var templateEditor = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_TEMPLATE_EDITOR)%>";
	var deleteCommand = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_TEMPLATE_TASK_EDIT_DELETE) %>";
	var deleteTasksCommand = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_TEMPLATE_TASK_EDIT_DELETE_TASK) %>";
	var copyCommand = "<%= MilestoneConstants.CMD_TEMPLATE_TASK_EDIT_COPY %>";
  var save = "<%= MilestoneConstants.CMD_TEMPLATE_TASK_EDIT_SAVE%>";
  var saveNew = "<%= MilestoneConstants.CMD_TEMPLATE_TASK_EDIT_SAVE_NEW%>";
	var saveCopy = "<%= MilestoneConstants.CMD_TEMPLATE_TASK_COPY_SAVE%>";
  var editNew = "<%= MilestoneConstants.CMD_TEMPLATE_TASK_EDIT_NEW%>";

  //PUT THE RIGHT COMMANDS FOR THOSE  THREE VARIABLES
  var sort = "<%=MilestoneConstants.CMD_TEMPLATE_NOTEPAD_TASKS_SORT%>";
  var search = "<%=MilestoneConstants.CMD_TEMPLATE_TASK_SEARCH%>";
  var sortTasks = "<%= MilestoneConstants.CMD_TEMPLATE_SORT_TASK_TASKS%>";

  //notepad variables
  var cmd = "<%= form.getRenderableValue("cmd")%>";
  var notepad = "<%= MilestoneConstants.NOTEPAD_UNASSIGNED_TASKS%>";
  var next = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_NOTEPAD_NEXT)%>";
  var previous = "<%= inf.getServletCmdURL(MilestoneConstants.CMD_NOTEPAD_PREVIOUS)%>";

function processLoad(pFocusField)
{
  focusField(pFocusField);
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
<script type="text/javascript" language="javascript" src="<%= inf.getHtmlDirectory() %>template-task-editor-javascript.js"></script>


<SCRIPT type="text/javascript" language="JavaScript">
function submitResize()
{
  //
  //note that as with milestone v2, changes the user makes are not kept when resize is clicked
  //
  parent.top.bottomFrame.location = "home?cmd=notepad-toggle&notepadType=<%=MilestoneConstants.NOTEPAD_UNASSIGNED_TASKS_VISIBLE%>&lastCommand=<%=form.getRenderableValue("cmd")%>";
}

//gets the Java Script Function createChildren() which is generated dynamicaly in the back end
<%= context.getDelivery("Configuration") %>

function initSelections()
{
  if (document.forms[0].Configuration != null)
    lSelectedConfiguration = document.forms[0].Configuration.options[document.forms[0].Configuration.selectedIndex].value;

  if (document.forms[0].SubConfiguration != null)
    lSelectedSubConfiguration = document.forms[0].SubConfiguration.options[document.forms[0].SubConfiguration.selectedIndex].value;

  if (document.forms[0].productLine != null)
    lSelectedProductCategory = document.forms[0].productLine.options[document.forms[0].productLine.selectedIndex].value;

  var isConfigurationAvailable = eval(document.forms[0]["Configuration"]);
  if ((typeof isConfigurationAvailable) == "object")
  {
    createChildren();
  }

  initConfigs();

  return true;
}

function initConfigs()
{
  clearBox( document.forms[0].Configuration );
  clearBox( document.forms[0].SubConfiguration );
  clearBox( document.forms[0].productLine );

  var prodType;
  prodType = 2;

  if (document.all.ProdType[0].checked)
    prodType = 0;
  else if (document.all.ProdType[1].checked)
    prodType = 1;

  var currIndex = 0;
  for( i = 0; i < lRoot.Children.length; i++ )
  {
    if (lRoot.Children[i].ProdType == prodType || prodType == 2 || lRoot.Children[i].ProdType == 2)
    {
      var newOption = new Option( lRoot.Children[ i ].Name, lRoot.Children[ i ].ID, lRoot.Children[ i ].ID == lSelectedConfiguration, lRoot.Children[ i ].ID == lSelectedConfiguration );
      document.forms[0].Configuration.options[ currIndex ] = newOption;
      currIndex++;
    }
  }

  adjustSelection(document.forms[0].Configuration);

  // Product Categories
  currIndex = 0;
  for( i = 0; i < productCategories.Children.length; i++ )
  {
    if (productCategories.Children[i].ProdType == prodType || prodType == 2 || productCategories.Children[i].ProdType == 2)
    {
      var newOption = new Option( productCategories.Children[ i ].Name, productCategories.Children[ i ].ID, productCategories.Children[ i ].ID == lSelectedProductCategory, productCategories.Children[ i ].ID == lSelectedProductCategory );
      document.forms[0].productLine.options[ currIndex ] = newOption;
      currIndex++;
    }
  }

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

var lRoot;
var lSelectedConfiguration = '';
var lSelectedSubConfiguration = '';
var lSelectedProductCategory = '';

function Node( pID, pName, pProdType, pChildren )
{
  this.ID = pID;
  this.Name = pName;
  this.Children = pChildren;
  this.ProdType = pProdType;
}

function adjustSelection( pSelection )
{
  switch( pSelection.name )
  {
    case "Configuration":
        clearBox( document.forms[0].SubConfiguration );

        var selectedIndex;
        selectedIndex = 0;
        for( i = 0; i < lRoot.Children.length; i++ )
        {
          if (lRoot.Children[i].ID == document.forms[0].Configuration.options[document.forms[0].Configuration.selectedIndex].value)
          {
             selectedIndex = i;
             break;
          }
        }

        if( lRoot.Children[selectedIndex] != null
            && lRoot.Children[selectedIndex].Children.length > 0 )
        {
          fillSelection( document.forms[0].SubConfiguration,
                         lRoot.Children[selectedIndex],
                         lSelectedSubConfiguration );
          lSelectedSubConfiguration = '';
        }
  }
}

function clearBox( pSelection )
{
  pSelection.options.length = 0;
}

function fillSelection( pSelection, pEntry, pDefault )
{
  var lChildren = pEntry.Children;
  for( i = 0; i < lChildren.length; i++ )
  {
    if(pDefault == '')
    {
      var newOption = new Option(lChildren[i].Name, lChildren[ i ].ID, i == 0, i == 0);
    }
    else
    {
      var newOption = new Option( lChildren[ i ].Name, lChildren[ i ].ID, lChildren[ i ].ID == pDefault, lChildren[ i ].ID == pDefault );
    }
   pSelection.options[ i ] = newOption;
  }
}
</SCRIPT>

<link rel="stylesheet" type="text/css" href="<%= inf.getHtmlDirectory() %>global.css" title="GlobalStyle">
</head>
<script for=document event=onkeydown language="JavaScript">
checkShortcut();
</script>

<body topmargin=0 leftmargin=0 onLoad="processLoad( 'templateName' );initSelections();">

<%= form.renderStart()%>
<%= form.getElement("cmd")%>
<%= form.getElement("OrderBy")%>
<%= form.getElement("OrderTasksBy")%>

<%
String alertMessage = (String)context.getDelivery("AlertMessage");

if (alertMessage != null)
{
%>
 <script language="JavaScript">
alert('<%=alertMessage%>');
</script>
<%
}
%>

<table width="780" cellpadding=0 border=0>
  <tr valign="middle" align="left">
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
    <td width="85">
      <div align="center">
       <% if (copyVisible)
          {%>
        <a href="JavaScript:submitCopy('Copy')"
          onMouseOver="Javascript:mtbCopy.over();return true;"
          onMouseOut="Javascript:mtbCopy.out()"
          onClick="Javascript:mtbCopy.click(); return true;">
          <img name="Copy" id="Copy" src="<%= inf.getImageDirectory() %>Copy_On.gif" width=66 height=14 border=0 hspace=0 vspace=0 align="absmiddle">
        </a>
        <script type="text/javascript" language="JavaScript" >
          var mtbCopy = new ToggleButton(document, '<%= inf.getImageDirectory() %>', 'Copy', "JavaScript:submitCopy( 'Copy' )", 66, 14 );
        </script>
       <%}%>
      </div>
    </td>
    <td width="85"></td>
    <td width="85">
      <div align="center">
      <% if (saveVisible)
         {%>
	       <a href="JavaScript:submitSave('Save')"
          onMouseOver="Javascript:mtbSave.over();return true;"
          onMouseOut="Javascript:mtbSave.out()"
          onClick="Javascript:mtbSave.click(); return true;">
          <img name="Save" id="Save" SRC="<%= inf.getImageDirectory() %>Save_On.gif" width=66 height=14 border=0 hspace=0 vspace=0 align="absmiddle">
        </a>
        <script type="text/javascript" language="JavaScript" >
          var mtbSave = new ToggleButton(document, '<%= inf.getImageDirectory() %>', 'Save', "JavaScript:submitSave( 'Save' )", 66, 14 );
        </script>
        <%}%>
      </div>
    </td>
    <td width=85>
      <div align="center">
      <% if (newVisible)
         {%>
        <a href="JavaScript:submitNew()"
          onMouseOver="Javascript:mtbNew.over();return true;"
          onMouseOut="Javascript:mtbNew.out()"
          onClick="Javascript:mtbNew.click(); return true;">
          <img name="New" id="New" src="<%= inf.getImageDirectory() %>New_On.gif" width=66 height=14 border=0 hspace=0 vspace=0 align="absmiddle">
        </a>
        <script type="text/javascript" language="JavaScript" >
          var mtbNew = new ToggleButton(document, '<%= inf.getImageDirectory() %>', 'New', "JavaScript:submitNew()", 66, 14 );
        </script>
      <%}%>
      </div>
    </td>
    <td width="85">
      <div align="center">
      <% if(deleteVisible)
       {%>
        <a href ="JavaScript:submitDelete('Delete')"
          onMouseOver="Javascript:mtbDelete.over();return true;"
          onMouseOut="Javascript:mtbDelete.out()"
          onClick="Javascript:mtbDelete.click(); return true;">
          <img name="Delete" id="Delete" src="<%= inf.getImageDirectory() %>Delete_On.gif" width=66 height=14 border=0 hspace=0 vspace=0 align="absmiddle">
        </a>
        <script type="text/javascript" language="JavaScript" >
           var mtbDelete = new ToggleButton(document, '<%= inf.getImageDirectory() %>', 'Delete', "JavaScript:submitDelete( 'Delete' )", 66, 14);
        </script>
      <%}%>
      </div>
    </td>
  </tr>
   <% if (formValidation != null)
     {%>
  <tr><td colspan=6 height=10>&nbsp;</td></tr>
      <% Vector instructions = formValidation.getInstructions();
         for (int i = 0; i < instructions.size(); i++)
         {%>
 <tr>
   <td></td>
   <td></td>
   <td colspan=4 align="left">
           <%=  instructions.get(i)%>
   </td>
 </tr>
       <%}%>
 <tr><td colspan=6 height=10>&nbsp;</td></tr>
    <%}%>
  <tr>

<%
if(context.getDelivery("isNotePadVisible") == null || (context.getDelivery("isNotePadVisible") != null && ((Boolean)context.getDelivery("isNotePadVisible")).booleanValue()))
{%>
     <% //===============start navigation panel=========================== %>
     <td valign="top">
     	<%@ include file="include-template-task-notepad.shtml" %>
     <% //===============end navigation panel=========================== %>
    </td>
<%} //end notepad visibility check
   // System.out.println(">>>>>>> ***** >>>> got here in the JSP");
    %>

<%
  String bgcolor = "lavender";

  if (currentTemplate.getProdType() == 1)
    bgcolor = MilestoneConstants.DIGITAL_PINK;
%>

    <td bgcolor="<%=bgcolor%>" valign="top"  align="left" colspan=6 width="100%">
      <table width="100%" height="100%" cellpadding=5 border=0>
        <tr width="100%">
          <td valign="top" width="100%">
            <input type="hidden" name="deleteFlag" value="false">
              <table width="100%" border=0>
                <tr>
                  <td class="label" nowrap>Schedule Template Name</td>
                  <td>
                    <%= form.getElement("templateName")%>
                  </td>
                  <td class="label" >Owner</td>
                  <td>
                    <%= form.getElement("owner")%>
                  </td>
                </tr>
                <tr>
                  <td colspan=4><hr noshade></td>
                </tr>

                <!-- JP Digital AFE -->
                <tr>
                  <td class="label" >Product Type</td>
                  <td colspan=3 align=left>
                     <%= form.getElement("ProdType")%>
                  </td>
                </tr>

                <tr>
                  <td class="label" >Product Category</td>
                  <td>
                    <%= form.getElement("productLine")%>
                  </td>
                  <td class="label" >Configuration</td>
                  <td>
                    <%= form.getElement("Configuration")%>
                  </td>
                </tr>
                <tr>
                  <td class="label" >Release Type</td>
                  <td>
                    <%= form.getElement("releaseType")%>
                  </td>
                  <td class="label" nowrap>Sub Configuration</td>
                  <td>
                    <%= form.getElement("SubConfiguration")%>
                  </td>
                </tr>
                <tr>
                  <td colspan=4>
                    <table class="detailList" width="100%" border=1 cellspacing=0>
                      <tr>
                        <th width="50%">
                          <a href="Javascript:submitTaskList(0);">Task Assignment</a>
                        </th>

                        <!--  msc Item 1917 -->
                        <th width="5%">
                          <a href="Javascript:submitTaskList(3);">Task Abbr</a>
                        </th>
                        <!--  msc Item 1917 -->

                        <th width="10%">  <!--  msc Item 1917 .. was 20% -->
                          <a href="Javascript:submitTaskList(1);">Wks to Rls</a>
                        </th>
                        <th width="20%">
                          <a href="Javascript:submitTaskList(2);">Owner</a>
                        </th>
                        <th>&nbsp;</th>
                      </tr>
                      <% //System.out.println(">>>>>>> ***** >>>> got here in the JSP 1");
                    if (!form.getRenderableValue("cmd").equalsIgnoreCase(MilestoneConstants.CMD_TEMPLATE_TASK_EDIT_NEW) &&
                        !form.getRenderableValue("cmd").equalsIgnoreCase(MilestoneConstants.CMD_TEMPLATE_TASK_EDIT_SAVE_NEW))
                    {
                      Template template = (Template)context.getSessionValue("Template");
                      Vector tasks = new Vector();
                      //System.out.println(">>>>>> template in the JSP = " + template);
                      if (template != null)
                      {
                        tasks = template.getTasks();
                      }

                     // System.out.println(">>>>>>>> ***** >>>>> tasks.size() in the JSP = " + tasks.size());
                      for (int j = 0; j < tasks.size(); j++)
                      {
                        Task task = (Task)tasks.get(j);
                      %>
                      <tr>
                      <%
                        String fontColor = "";
                        isKeyTask = task.getIsKeyTask();
                        isLabelOwner = !MilestoneHelper.isUml(task);
                        if (isKeyTask)
                        {
                          if (!isLabelOwner)
                          {
                            fontColor = "class=\"umlKey\"";
                          }
                          else
                          {
                            fontColor = "class=\"umlKey\"";
                          }
                        }
                        else
                        {
                          fontColor = "";
                        }
                       %>
                        <td <%= fontColor%> width="50%">
                          <%=task.getTaskName()%>
                        </td>

                        <!--  msc Item 1917 -->
                        <td width="5%">
                         <%if( task.getTaskAbbrStr() == null || task.getTaskAbbrStr().equals("") )
                           {%>
                             &nbsp;
                           <%
                           } else {%>
                              <%=task.getTaskAbbrStr()%>
                           <%
                           }%>
                        </td>
                        <!--  msc Item 1917 -->


                        <td width="10%">  <!--  msc Item 1917 ... was 20% -->
                        <%if (task.getWeeksToRelease() == MilestoneConstants.SOL)
                            {%>
                              <%=MilestoneConstants.SOL_LABEL%>
                            <%
                            }
                            else
                            {
                            %>
                          <%=(task.getDayOfTheWeek().getDay() + " " + task.getWeeksToRelease())%>
                          <%
                            }
                          %>
                        </td>
                        <td width="20%">
                        <% if (task.getOwner() != null)
                          {%>
                          <%=MilestoneHelper.getStructureName(task.getOwner().getStructureID())%>
                        <%}
                          else
                          {%>
                          &nbsp;
                        <%}%>
                        </td>
                        <td>
                         <%
                           int editable = Integer.parseInt((String)templateRights.elementAt(j));
                           if (editable == MilestoneConstants.READ_WRITE)
                           {%>
                            <a href="Javascript:submitDeleteTask(<%= task.getTaskID()%>)">delete</a>
                         <%}
                           else
                           {%>
                           &nbsp;
                         <%}%>
                       </td>
                      </tr>
                      <%}%>
                    <%} //end if cmd != template-task-edit-new
                   // System.out.println(">>>>>>> ***** >>>> got here in the JSP 2");
                    %>
                    </table>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td valign=bottom>
              <table>
                <tr>
                  <td class="label">Last Updated: </td>
		          <td><%= form.getRenderableValue("lastUpdatedDate")%></td>
				      <td><%= form.getRenderableValue("lastUpdatedBy")%></td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>

<!-- DIV definitions -->
<!-- start search div -->
<div id="taskSearchLayer" class="search" onKeyPress="checkForEnter('submitSearch()');" style="visibility:hidden">
<table width="100%" border="1" cellspacing="0" cellpadding="1">
<tr>
	<td>
		<table width="100%">
		<tr><th colspan="2">Task Search</th></tr>
		<tr>
			<td>Task Name</td>
			<td><%= form.getElement("TaskNameSearch") %></td>
		</tr>
		<tr>
			<td>Key Task</td>
			<td align="center"><%= form.getElement("KeyTaskSearch") %></td>
		</tr>
		<tr>
			<td>Owner</td>
			<td><%= form.getElement("TaskOwnerSearch") %></td>
		</tr>
		<tr>
			<td>Department</td>
			<td><%= form.getElement("TaskDepartmentSearch") %></td>
		</tr><tr><td colspan="2"><input type="button" name="SubmitSearch" value="Submit Search" onClick="submitSearch()"></td></tr>
		</table>
</td></tr></table>
</div>
<!-- end search div -->

 <%=form.renderEnd()%>

 <%@ include file="template-bottom-html.shtml"%>

