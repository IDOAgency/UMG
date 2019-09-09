<%@ include file="template-top-page.shtml"%>
<%
   /* This variable holds the value to be placed in <title> html tag </title>
      for the include file: template-top-html.jsp */
   String htmlTitle = "Login - Milestone";
%>
<%@ include file="template-top-html.shtml"%>
<%@ include file="callHelp.js"%>

<script language="javascript">
liLoginOn = new Image( 81, 45 );
liLoginOn.src = "<%= inf.getImageDirectory() %>Login_On.gif";
liLoginOff = new Image( 81, 45 );
liLoginOff.src = "<%= inf.getImageDirectory() %>Login_Off.gif";

var bites = document.cookie.split("; "); // break cookie into array of bites
var today = new Date();
var expiry = new Date(today.getTime() + 28 * 24 * 60 * 60 * 1000); // plus 28 days


function getCookie(name) { // use: getCookie("name");
    for (var i=0; i < bites.length; i++) {
      nextbite = bites[i].split("="); // break into name and value
      if (nextbite[0] == name) // if name matches
        return unescape(nextbite[1]); // return value
    }
    return "";
  }

function setCookie(name, value) { // use: setCookie("name", value);
    if (value != null && value != "")
      document.cookie=name + "=" + escape(value) + "; expires=" + expiry.toGMTString();
    bites = document.cookie.split("; "); // update cookie bites
  }

function removeCookie(name) { // use: removeCookie("name", value);
    // remove the cookie if empty string
    document.cookie=name + "=" + escape("") + "; expires=Fri, 02-Jan-1970 00:00:00 GMT";
    bites = document.cookie.split("; "); // update cookie bites
  }
// Utility function to trim spaces from both ends of a string.
function Trim(inString)
{
  var retVal = ""
  var start = 0;
  while ((start < inString.length) && (inString.charAt(start) == ' '))
  {
    ++start;
  }
  var end = inString.length;
  while ((end > 0) && (inString.charAt(end - 1) == ' '))
  {
    --end;
  }
  retVal = inString.substring(start, end);
  return retVal;
}//end function Trim


function BrowserDetector(ua)
{
  //default
  this.browser = "Unknown";
  this.platform = "Unknown";
  this.version = "";
  this.majorver = "";
  this.minorver = "";
  uaLen = ua.length;
  // ##### Split into stuff before parens and stuff in parens
  var preparens = "";
  var parenthesized = "";
  i = ua.indexOf("(");
  if (i >= 0)
  {
    preparens = Trim(ua.substring(0,i));
    parenthesized = ua.substring(i+1, uaLen);
    j = parenthesized.indexOf(")");
    if (j >= 0)
    {
      parenthesized = parenthesized.substring(0, j);
    }
  }
  else
  {
    preparens = ua;
  }
  // ##### First assume browser and version are in preparens
  // ##### override later if we find them in the parenthesized stuff
  var browVer = preparens;
  var tokens = parenthesized.split(";");
  var token = "";
  // # Now go through parenthesized tokens
  for (var i=0; i < tokens.length; i++)
  {
    token = Trim(tokens[i]);
    //## compatible - might want to reset from Netscape
    if (token == "compatible")
    {
      //## One might want to reset browVer to a null string
      //## here, but instead, we'll assume that if we don't
      //## find out otherwise, then it really is Mozilla
      //## (or whatever showed up before the parens).
      //## browser - try for Opera or IE
    }
    else if (token.indexOf("MSIE") >= 0)
    {
      browVer = token;
    }
    else if (token.indexOf("Oper") >= 0)
    {
      browVer = token;
    }
    //'## platform - try for X11, SunOS, Win, Mac, PPC
    else
    if ((token.indexOf("X11") >= 0) || (token.indexOf("SunOS") >= 0) ||
       (token.indexOf("Linux") >= 0))
    {
       this.platform = "Unix";
    }
    else if (token.indexOf("Win") >= 0)
    {
      this.platform = token;
    }
    else if ((token.indexOf("Mac") >= 0) || (token.indexOf("PPC") >= 0))
    {
      this.platform = token;
    }
  }
  var msieIndex = browVer.indexOf("MSIE");
  if (msieIndex >= 0)
  {
    browVer = browVer.substring(msieIndex, browVer.length);
  }
  var leftover = "";
  if (browVer.substring(0, "Mozilla".length) == "Mozilla")
  {
    this.browser = "Netscape";
    leftover = browVer.substring("Mozilla".length+1, browVer.length);
  }
  else if (browVer.substring(0, "Lynx".length) == "Lynx")
  {
    this.browser = "Lynx";
    leftover = browVer.substring("Lynx".length+1, browVer.length);
  }
  else if (browVer.substring(0, "MSIE".length) == "MSIE")
  {
    this.browser = "IE";
    leftover = browVer.substring("MSIE".length+1, browVer.length);
  }
  else if (browVer.substring(0, "Microsoft Internet Explorer".length) ==
    "Microsoft Internet Explorer")
  {
    this.browser = "IE"
    leftover = browVer.substring("Microsoft Internet Explorer".length+1,browVer.length);
  }
  else if (browVer.substring(0, "Opera".length) == "Opera")
  {
    this.browser = "Opera"
    leftover = browVer.substring("Opera".length+1, browVer.length);
  }
  leftover = Trim(leftover);
  // # Try to get version info out of leftover stuff
  i = leftover.indexOf(" ");
  if (i >= 0)
  {
    this.version = leftover.substring(0, i);
  }
  else
  {
    this.version = leftover;
  }
  j = this.version.indexOf(".");
  if (j >= 0)
  {
    this.majorver = this.version.substring(0,j);
    this.minorver = this.version.substring(j+1, this.version.length);
  }
  else
  {
    this.majorver = this.version;
  }
} // function BrowserCap

function GetBrowerInfo(querySysInfo)
{
  var bd = new BrowserDetector(navigator.userAgent);
  if (querySysInfo == "Browser")
  {
     querySysInfo = bd.browser;
  }
  if (querySysInfo == "Platform")
  {
     querySysInfo = bd.platform;
  }
  if (querySysInfo == "Version")
  {
     querySysInfo = bd.version;
  }
  if (querySysInfo == "MajorVersion")
  {
     querySysInfo = bd.majorver;
  }
  if (querySysInfo == "MinorVersion")
  {
     querySysInfo = bd.minorver;
  }
  return querySysInfo;
}//end function GetBrowser info

function over()
{
	if( document.images )
  {
    document.login.src = liLoginOff.src;
  }
  document.forms[0].Browser.value = GetBrowerInfo("Browser");
  document.forms[0].Platform.value = GetBrowerInfo("Platform");
  document.forms[0].Version.value = GetBrowerInfo("Version");
  document.forms[0].MinorVersion.value = GetBrowerInfo("MinorVersion");
  document.forms[0].MajorVersion.value = GetBrowerInfo("MajorVersion");
  document.forms[0].cmd.value = "login";
  setCookie("username", document.forms[0].username.value);
  // mc its 966 added please wait message to screen
  removeCookie("lastLink");  // remove this cookie at login
  document.forms[0].submit();
}//end function over

function helpConext()
{
callHelp('Help/','Logging_On.htm');
}//end function helpContext

function checkInput( pKey )
{
  if( pKey == 13 )
	{
    document.forms[0].Browser.value = GetBrowerInfo("Browser");
    document.forms[0].Platform.value = GetBrowerInfo("Platform");
    document.forms[0].Version.value = GetBrowerInfo("Version");
    document.forms[0].MinorVersion.value = GetBrowerInfo("MinorVersion");
    document.forms[0].MajorVersion.value = GetBrowerInfo("MajorVersion");
    document.forms[0].cmd.value = "login";
    setCookie("username", document.forms[0].username.value);
    // mc its 966 added please wait message to screen
    removeCookie("lastLink");  // remove this cookie at login
    document.forms[0].submit();
  }
}//end function checkInput
</script>

<script type="text/javascript" language="JavaScript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-shortcut.js"></script>
<script for="document" event="onkeydown" language="JavaScript">
checkShortcut();
</script>
<script src="<%= inf.getHtmlDirectory() %>flashEmbed.js" language="JavaScript" type="text/javascript"></script>

</head>



<body onLoad="document.all[ 'password' ].focus();document.forms[0].username.value = getCookie('username');"

 <% //Display relevant Milestone image for Local/Development/Test/Production
    //jo - ITS #871
     String envColor = "white";
     envColor = (String)context.getSessionValue("environment_color");%>
     bgcolor="#<%=envColor%>"
>


<%
// message will be displayed when a user has entered in an incorrect username and password.
// a pop-up box on a blank screen will appear.
if (message != null)
{
%>
<script language="Javascript">
	alert("<%= message %>");
</script>
<%}%>

<font face="arial" size="1" >
  <table border="0" width="580">
    <tr>
      <td>
     <% //Display relevant Milestone image for Local/Development/Test/Production
        //jo - ITS #871
     String envName = "";
     envName = (String)context.getSessionValue("environment_name");
     if (envName.equals("Production")){%>
         <script language="JavaScript" type="text/javascript">runFlash('<%= inf.getImageDirectory()%>');</script>
     <% } else { %>
         <img src="<%= inf.getImageDirectory()%>MilestoneFlash<%=envName%>.jpg" border=0">
     <% } %>
      </td>
    </tr>
  </table>

	<%= form.renderStart() %>
  <%= form.getElement(MilestoneConstants.CMD) %>

  <table width="580" border="0">

    <tr>
      <td align="right" width="200">User Login</td>
      <td align="left" width="100">
          <%=form.getElement("username")%>
      </td>
      <td rowspan="2" align="left">
        <a href="Javascript:over();" border="2" tabindex="3"><img width="81" height="45" src="<%= inf.getImageDirectory() %>Login_On.gif" border="0" name="login"></a>
        <a href="Javascript:helpConext()" border="2" tabindex="3"><img src="<%= inf.getImageDirectory() %>Help_On.gif" border="0" name="help"></a>
      </td>
    </tr>

    <tr>
      <td><div align="right" width="200">Password</div></td>
      <td align="left" width="100">
        <%=form.getElement("password")%><BR>
      </td>
    </tr>

  </table>

  <input type=hidden name="Browser" size="25" value="">
  <input type=hidden name="Platform" size="25" value="">
  <input type=hidden name="Version" size="25" value="">
  <input type=hidden name="MajorVersion" size="25" value="">
  <input type=hidden name="MinorVersion" size="25" value="">
  <%=form.renderEnd()%>


<%
String closeWindow = (String)context.getDelivery("CloseWindow");
if(closeWindow != null && closeWindow.equals("true"))
{%>
<script>
window.close();
</script>
<%}%>

<%@ include file="include-bottom-html.shtml"%>