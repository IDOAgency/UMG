<% /*
		this include (template-top-page.jsp) does the following:
			extends com.universal.milestone.MilestoneJSP
			import="com.techempower.*,com.techempower.gemini.*, com.universal.milestone.*, java.util.*"
			gets the context, form formValidation and message
   */
%>

<%@ include file="template-top-page.shtml"%>

<%
	// holds the selected release from the nav menu that the user clicked and passes it to the hidden
	// field userID in order to be preserved when the form submits. The same with the action and
	// subAction. Also, defined here are those varaibles which are accessible from the entire page
	// when needed.
	String userID = null;
	String showCompanies = null;
	String action = null;
	String subAction = null;
	boolean showCompanyButton = true;

	// This variable holds the value to be placed in the <title> html tag for the include file:
	// template-top-html.jsp.
	String htmlTitle = "User-Company Security Editor";

	// check if user has edit rights
	boolean editRights = true;
	// indicate who is the owner: Label or UML
	//boolean isLabelOwner = true;

	// initialize notepad width to the default. This variable holds the notepad width and passes
	// it subAction to preserve the value.
	int notepadWidth = 35;

   // get the value of "Action" parameter passed
   action = request.getParameter("Action");

   // get the value of subAction parameter used
   subAction = request.getParameter("SubAction");

  // check to see if action is null
  if (action != null)
  {
    // check if toggle (%) button was clicked to hide or show the nav menu
    if (action.equals("resize"))
    {
    	// check for null
      if (subAction != null)
      {
     		// try/catch code here to catch if exception is thrown when converting to integer
        try
        {
          notepadWidth = Integer.parseInt(subAction);
        }
        catch (NumberFormatException e)
        {
        	notepadWidth = 35;
        }
      } // end if subAction != null
    } // end if action == "resize"

		// check if switchToTasks or switchToSelections button was clicked if so change the nav menu
    // to show "Users" or "Companies"
    if (action.equals("show"))
    {
    	// do nothing
    }// end if Action == "show"
  }// end if Action != null

	// getting the login name
	User user = (User)context.getSessionValue("user");

	int userid = user.getUserId();
%>

<%@ include file="template-top-html.shtml" %>

<!-- include js files holding javascript functions -->
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-toggle.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-toggle-button.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-format.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-focus-field.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-length.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-convert-date.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-integer.js"></script>
<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-shortcut.js"></script>

<!-- Java script functions specific to this page -->
<script type="text/javascript" language="JavaScript">


function submitResize()
{
	// get the command from the backend, change it to the jsp in
  //the global Java script varables and here
  //parent.top.bottomFrame.location = "home?cmd=notepad-toggle&notepadType=<%=MilestoneConstants.NOTEPAD_USER_COMPANIES_VISIBLE%>&lastCommand=<%=form.getRenderableValue("cmd")%>";
  parent.top.bottomFrame.location = "home?cmd=notepad-toggle&notepadType=<%=MilestoneConstants.NOTEPAD_USER_ENVIRONMENTS_VISIBLE%>&lastCommand=<%=form.getRenderableValue("cmd")%>";
 } //end function submitResize()


</script>

<script type="text/javascript" language="JavaScript">
function checkField( pField )
{
 var bReturn = true;
  switch( pField.name )
	{
		//do nothing
  }
  if( !bReturn )
	{
    pField.focus();
  }
  return bReturn;
} //end function checkField()

function checkPage()
{
  return true;
} //end function checkPage()

function submitNewHeader()
{
	document.forms[0].editAction.value = "Add Security User";
	document.forms[0].submit();
} //end function submitNewHeader()

function submitSearchWithEnter()
{
   if( window.event.keyCode != 13 )
	 {
	   return;
	 }
   else
	 {
	 	//do nothing
   }
   toggle( 'searchLayer', 'nameSrch' );
   document.forms[0].editAction.value = "search";
   document.forms[0].submit();
} //end function submitSearchWithEnter()

function submitSearch()
{
  toggle( 'searchLayer', 'nameSrch' );
  //document.forms[0].cmd.value = "<%=MilestoneConstants.CMD_USER_COMPANY_SECURITY_SEARCH%>";
  document.forms[0].cmd.value = "<%=MilestoneConstants.CMD_USER_ENVIRONMENT_SECURITY_SEARCH%>";
  document.forms[0].submit();
} //end function submitSearch()

function submitShow( pIsUser )
{
  parent.top.bottomFrame.location = "home?cmd=user-security-editor";
} //end function submitShow()

function submitNew()
{
  document.forms[0].editAction.value = "new";
  document.forms[0].submit();
} //end function submitNew()

function submitSave( imagePrefix )
{
  if( checkPage() )
	{
	  //document.forms[0].cmd.value = "<%=MilestoneConstants.CMD_USER_COMPANY_SECURITY_SAVE%>";
	  document.forms[0].cmd.value = "<%=MilestoneConstants.CMD_USER_ENVIRONMENT_SECURITY_SAVE%>";
    document.forms[0].submit();
  }
  else
	{
    eval( 'mtb' + imagePrefix ).reset();
  }
} //end function submitSave()

function submitDelete( imagePrefix )
{
  if(confirm("You are about to delete a record. Confirm?"))
	{
    document.forms[0].editAction.value = "delete";
    document.forms[0].SubAction.value = "user";
    document.forms[0].submit();
  }
  else
	{
    eval( 'mtb' + imagePrefix ).reset();
  }
} //end function submitDelete()

function submitList( pType )
{
  document.forms[0].editAction.value = "list";
  document.forms[0].orderBy.value = pType;
  document.forms[0].submit();
} //end function submitList()

function submitLogOff( )
{
  document.forms[0].editAction.value = "LogOff";
  document.forms[0].submit();
} //end function submitLogOff()

function submitGet(pCompanyId)
{
  //parent.top.bottomFrame.location = "home?cmd=user-company-security-editor&company-id=" + pCompanyId;
  parent.top.bottomFrame.location = "home?cmd=user-company-environment-editor&company-id=" + pCompanyId;
} //end function submitGet()

function submitPagging( pForward )
{
  if( pForward )
  {
  	//parent.top.bottomFrame.location = "home?cmd=notepad-next&notepadType=<%=MilestoneConstants.NOTEPAD_USER_COMPANIES%>&lastCommand=<%=form.getRenderableValue("cmd")%>";
  	parent.top.bottomFrame.location = "home?cmd=notepad-next&notepadType=<%=MilestoneConstants.NOTEPAD_USER_ENVIRONMENTS%>&lastCommand=<%=form.getRenderableValue("cmd")%>";
  }
  else
  {
  	//parent.top.bottomFrame.location = "home?cmd=notepad-previous&notepadType=<%=MilestoneConstants.NOTEPAD_USER_COMPANIES%>&lastCommand=<%=form.getRenderableValue("cmd")%>";
  	parent.top.bottomFrame.location = "home?cmd=notepad-previous&notepadType=<%=MilestoneConstants.NOTEPAD_USER_ENVIRONMENTS%>&lastCommand=<%=form.getRenderableValue("cmd")%>";
  }
} //end function submitPagging()

//getting browser specs
NS4 = (document.layers);
IE4 = (document.all);
ver4 = (NS4 || IE4);
isMac = (navigator.appVersion.indexOf("Mac") != -1);
isMenu = (NS4 || (IE4 && !isMac));

function popUp(){return};
function popDown(){return};
function startIt(){return};
if (!ver4) event = null;

function MM_swapImgRestore()
{ //v3.0
	var i,x,a=document.MM_sr;
	for(i=0;a&&i < a.length&&(x=a[i])&&x.oSrc;i++)
		x.src=x.oSrc;
} //end function MM_sampImgRestore()

function MM_preloadImages()
{ //v3.0
	var d=document;
	if(d.images)
	{
		if(!d.MM_p)
			d.MM_p=new Array();
			var i,j=d.MM_p.length,a=MM_preloadImages.arguments;
			for(i=0; i < a.length; i++)
			if (a[i].indexOf("#")!=0)
			{
				d.MM_p[j]=new Image;
				d.MM_p[j++].src=a[i];
			}
	}
} //end function MM_preloadImages()

function MM_findObj(n, d)
{ //v3.0
	var p,i,x;
	if(!d)
		d=document;
	if((p=n.indexOf("?"))>0&&parent.frames.length)
	{
		d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);
	}
	if(!(x=d[n])&&d.all)
		x=d.all[n];
	for (i=0;!x&&i<d.forms.length;i++)
		x=d.forms[i][n];
	for(i=0;!x&&d.layers&&i<d.layers.length;i++)
		x=MM_findObj(n,d.layers[i].document);
		return x;
} //end function MM_findObj()

function MM_swapImage()
{ //v3.0
	var i,j=0,x,a=MM_swapImage.arguments;
	document.MM_sr=new Array;
	for(i=0;i<(a.length-2);i+=3)
		if ((x=MM_findObj(a[i]))!=null)
		{
			document.MM_sr[j++]=x;
			if(!x.oSrc)
				x.oSrc=x.src;
				x.src=a[i+2];
		}
} //end function MM_swapImage()

function openWindow(which, title, wd, ht)
{
	eval('desktop = parent.window.open("' + which + '","' + title + '","width=' + wd + ',height=' + ht + ',toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no");')
} //end function openWindow()
</script>

<link rel="stylesheet" type="text/css" href="<%= inf.getHtmlDirectory() %>global.css" title="GlobalStyle">
</head>

<script for="document" event="onkeydown" language="JavaScript">
	checkShortcut();
</script>

<!-- start body -->
<body bgcolor="white" text="black" topmargin="0" leftmargin="0" onLoad="focusField( 'password' );">

<%= form.renderStart()%>
<%=form.getElement("cmd")%>

<input type="hidden" name="userId" value="<%= userID %>">
<input type="hidden" name="Action" id="Action" value="">
<input type="hidden" name="SubAction" id="SubAction" value="<%= notepadWidth%>">
<input type="hidden" name="editAction" value="">
<input type="hidden" name="companyId" value="13">
<input type="hidden" name="id" value="76~59~17027">
<input type="hidden" name="orderBy" value="-1">
<input type="hidden" name="SessionKey" value="4040">
<INPUT type="hidden" name="PageEndMarker" value="true">


<table width="780" cellpadding="0" border="0">
<tr valign="middle" align="left">
	<td width="280">
		<div align="left">
    	<a href ="JavaScript:submitResize()"
          onMouseOver="Javascript:mtbToggle.over();return true;"
          onMouseOut="Javascript:mtbToggle.out()"
          onClick="Javascript:mtbToggle.click(); return true;">
          <img name="Toggle" id="Toggle" src="<%= inf.getImageDirectory() %>Toggle_On.gif" width="27" height="14" border="0" hspace="0" vspace="0" align="absmiddle">
    	</a>
      <script type="text/javascript" language="JavaScript">
      	var mtbToggle = new ToggleButton(document, '<%= inf.getImageDirectory() %>', 'Toggle', "JavaScript:submitResize()", 27, 14);
			</script>
		</div>
	</td>
	<td rowspan="2" width="10"><img src="<%= inf.getImageDirectory() %>pixelshim.gif" width="10"></td>
	<td width="170"><span class="title">User</span></td>
  <td width="85"></td>
	<td width="85"></td>
	<td width="85">
		<!--<div align="center">
			<a href="JavaScript:submitSave('Save')"
          onMouseOver="Javascript:mtbSave.over();return true;"
          onMouseOut="Javascript:mtbSave.out()"
          onClick="Javascript:mtbSave.click(); return true;">
          <img name="Save" id="Save" SRC="<%= inf.getImageDirectory() %>Save_On.gif" width="66" height="14" border="0" hspace="0" vspace="0" align="absmiddle">
        </a>
        <script type="text/javascript" language="JavaScript">
          var mtbSave = new ToggleButton(document, '<%= inf.getImageDirectory() %>', 'Save', "JavaScript:submitSave( 'Save' )", 66, 14 );
        </script>
		</div>-->
	</td>
	<td width="85"></td>
	<td width="85"></td>
</tr>
<tr>
	<td valign="top">
    <%
     if(context.getDelivery("isNotePadVisible") == null || (context.getDelivery("isNotePadVisible") != null && ((Boolean)context.getDelivery("isNotePadVisible")).booleanValue()))
     {  // display the navigation panel
  %>
  	<% /* ===============start users navigation panel===========================
  	     This part of the code is made an include to make it easy to manage this page.
       	 The the java script needed resides in this page.
       	 The include just gets and puts the code here as it was written here.
      */%>

       	 <%@ include file="include-user-company-notepad.shtml" %>

 		<% /* ===============end selection navigation panel=========================== */ %>
		<% } %>
	</td>

	<!-- start user-company form -->
  <td>
  <table width="516" bgcolor="lavender">
    <tr><td align="center"><b><h4>Milestone System<br><br><br>Please search for Selections on the left panel to this view </h4></b></td></tr>
  </table>

<!-- DIV definitions -->
<!-- start search div -->
<div class="search" id=searchLayer onKeyDown="submitSearchWithEnter()" style="visibility:hidden">
<table width="100%" border="1" cellspacing="0" cellpadding="1">
<tr>
	<td>
		<table width="100%">
		<tr><th colspan="2">Company Search</th></tr>
		<tr>
			<td class="label">Company Name</td>
			<td><INPUT type=text name="nameSrch" size=20></td>
		</tr>
		<tr><td colspan="2"><INPUT type=button value="Submit Search" onClick="submitSearch()"></td></tr>
		</table>
	</td>
</tr>
</table>
</div>


<!-- end search div -->
<!-- END DIV DEFINITIONS -->


<%=form.renderEnd()%>


<%@ include file="include-bottom-html.shtml"%>


