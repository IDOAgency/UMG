<%
   /* This variable holds the value to be placed in <title> html tag </title>
      for the include file: template-top-html.jsp */
   String htmlTitle = "impactDate screen";

%>



<%@ include file="template-top-page.shtml"%>
<%@ include file="template-top-html.shtml"%>

<link rel="stylesheet" type="text/css" href="<%= inf.getHtmlDirectory() %>global.css" title="GlobalStyle">

<script>
// msc Item 1948
function checkFields()
{

 var bReturn = false;
 var len = ImpactEditor.impactForm.elements.length;   // number of elements in the form
 var frm = ImpactEditor.impactForm;                   // form

 for(var i=0; i < len; i++)
 {
    var f  = frm.elements[i];
    var id = f.name;
    if( id.substr(0,10) == "impactDate" ) {       // impact date validation
        if( !checkDate( f, "Impact date is required:", false )) {
            bReturn = true;
            f.style.backgroundColor = "mistyrose";       // highlight error
	    f.focus();
        } else
            f.style.backgroundColor = "white";    // if no error, default color
    }
 }
 return bReturn;
}
// msc Item 1948

function submitAdd()
{
	//parent.top.bottomFrame.ImpactFrame.ImpactEditor.location = "<%= inf.getServletCmdURL("selection-impactDate-add")%>";
	parent.top.bottomFrame.ImpactFrame.ImpactEditor.submitAdd();
} //end function submitGet()

function submitSave()
{
    if( !checkFields() ) {    // msc Item 1948

        parent.top.bottomFrame.ImpactFrame.ImpactEditor.submitSave();  // save impact dates only

        // impact dates have been saved, change impact date label style.color to purple
        if( ImpactEditor.impactForm.impactDate0 == null || ImpactEditor.iSize == 0)
            parent.anchorImpactDate.style.color = "black";
        else
            parent.anchorImpactDate.style.color = "purple";
        // msc ///////////////////////////////////////////////////////////////////////////////////////////////////

        if( parent.top.bottomFrame.newOrCopy ) { // msc Item 1949 if new/copy save impact dates only
           hide();
        } else {
           // msc Item 2022 parent.top.bottomFrame.submitSave('save');
           hide();
        }

    }   //msc Item 1948
} //end function()

function hide()
{
	layer = eval(parent.top.bottomFrame.document.all["ImpactLayer"]);
  layer.style.visibility = "Hidden";

		//layer.style.visibility = "Visible";
    //layer.all["ImpactLayer"].focus();
} //end


function submitCancel()
{
      // msc
      if(  ImpactEditor.impactForm.impactDate0 == null || ImpactEditor.iSize == 0) {
        parent.anchorImpactDate.style.color = "black";
      } else {
        parent.anchorImpactDate.style.color = "purple";
      }

      parent.top.bottomFrame.ImpactFrame.ImpactEditor.submitCancel();
    hide();
} //end function submitCancel()

</script>

<% //-- msc Item 1948 include js files holding javascript functions --%>
<script type="text/javascript" language="javascript" src="<%= inf.getHtmlDirectory() %>include-jscript-toggle.js"></script>
<script type="text/javascript" language="javascript" src="<%= inf.getHtmlDirectory() %>include-jscript-toggle-button.js"></script>
<script type="text/javascript" language="javascript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-format.js"></script>
<script type="text/javascript" language="javascript" src="<%= inf.getHtmlDirectory() %>include-jscript-focus-field.js"></script>
<script type="text/javascript" language="javascript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-length.js"></script>
<script type="text/javascript" language="javascript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-convert-date.js"></script>
<script type="text/javascript" language="javascript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-integer.js"></script>
<script type="text/javascript" language="javascript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-shortcut.js"></script>
<% //-- msc Item 1948 include js files holding javascript functions --%>


</head>

<body topmargin=0 leftmargin=0 >
<table bgcolor="wheat" width="100%" height="130" border="1" cellspacing="0" cellpadding="2">
<tr>
<td>
  <table bgcolor="wheat" width="100%" height="130" border="0" cellspacing="0" cellpadding="3">
  <tr>
   <td valign="top" align="left">
  <IFRAME HEIGHT="130" width="100%" ID="ImpactEditor" Name="ImpactEditor" FRAMEBORDER=0 SCROLLING=auto SRC="<%= inf.getServletCmdURL("selection-impactDate-editor")%>">
  </IFRAME>
   </td>
  </tr>
  <tr>

  <td>

  <%
  boolean saveVisible = ((String)context.getDelivery("saveVisible")).equals("true");
  String impactSaveVisible = (String)context.getSessionValue("impactSaveVisible");      // msc @@@
  if (saveVisible || (impactSaveVisible != null && impactSaveVisible.equals("true")) )  // msc @@@
  {
  %>
    <input id=SaveIFrame type="button" value="Save" onclick="javascript:submitSave()">&nbsp;&nbsp;
  <%
  }
  %>

  <input type="button" name="Cancel" value="Cancel" onclick="javascript:submitCancel()">&nbsp;&nbsp;

  <% if (saveVisible || (impactSaveVisible != null && impactSaveVisible.equals("true")) )  // msc @@@
  {
  %>
    <input type="button" value="Add" onclick="javascript:submitAdd()">&nbsp;&nbsp;
  <%
  }
  %>

  </td>
  </tr>
  </table>
</td>
</tr>

</table>

<script>

</script>

<%@ include file="include-bottom-html.shtml"%>
