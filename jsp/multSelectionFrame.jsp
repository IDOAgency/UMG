<%
   /* This variable holds the value to be placed in <title> html tag </title>
      for the include file: template-top-html.jsp */
   String htmlTitle = "multiple Selection screen";

%>



<%@ include file="template-top-page.shtml"%>
<%@ include file="template-top-html.shtml"%>

<link rel="stylesheet" type="text/css" href="<%= inf.getHtmlDirectory() %>global.css" title="GlobalStyle">

<script>
// msc Item 1948
function checkFields()
{

 var bReturn = false;
 var len = MultSelectionEditor.multSelectionForm.elements.length;   // number of elements in the form
 var frm = MultSelectionEditor.multSelectionForm;                   // form
 var errSelNo = null;

 for(var i=0; i < len; i++)
 {
    var f  = frm.elements[i];
    var id = f.name;

    if( id.substr(0,11) == "selectionNo" ) {       // selection No validation
      if( f.value == "" )  {
          errSelNo = f;   // save field incase both fields are blank
      } else {
         f.style.backgroundColor = "white";    // if no error, default color
      }
    }

    if( id.substr(0,3) == "upc" ) {       // upc validation

       // msc 09/25/03 ITS 108 validate UPC & Sound scan group codes
       if(f != null && !checkLengthUPC( f.value, "UPC", 12, 14, "UPC", false, false ))
       {
           f.style.backgroundColor = "mistyrose";       // highlight upc
           f.focus();
           return true;
       }
       else {
         f.style.backgroundColor = "white";    // if no error, default color
       }

      // check required fields
      if( f.value == "" && errSelNo != null) {
         alert("Local Product # or UPC is required! ");
         f.style.backgroundColor = "mistyrose";       // highlight upc
         errSelNo.style.backgroundColor = "mistyrose";       // highlight selectionNo
         errSelNo.focus();
         return true;
      } else {
         f.style.backgroundColor = "white";    // if no error, default color
      }
    }
 }

 return bReturn;
}
// msc Item 1948

function submitAdd()
{
	parent.top.bottomFrame.MultSelectionFrame.MultSelectionEditor.submitAdd();
} //end function submitGet()

function submitSave()
{
    if( !checkFields() ) {    // msc Item 1948

        // save multiple selections only
        parent.top.bottomFrame.MultSelectionFrame.MultSelectionEditor.submitSave();

        // mult selections have been saved, change id label style.color to purple
        if( MultSelectionEditor.multSelectionForm.selectionNo0 == null || MultSelectionEditor.iSize == 0)
        {
            parent.anchorMultSelectionsA.style.color = "black";
            parent.anchorMultSelectionsB.style.color = "black";
        } else {
            parent.anchorMultSelectionsA.style.color = "purple";
            parent.anchorMultSelectionsB.style.color = "purple";
        }
        // msc ///////////////////////////////////////////////////////////////////////////////////////////////////

        if( parent.top.bottomFrame.newOrCopy ) { // msc Item 1949 if new/copy save mult sels only
           hide();
        } else {
           hide();
        }

    }   //msc Item 1948
} //end function()

function hide()
{
  layer = eval(parent.top.bottomFrame.document.all["MultSelectionLayer"]);
  layer.style.visibility = "Hidden";
} //end


function submitCancel()
{
      // msc
      if(  MultSelectionEditor.multSelectionForm.selectionNo0 == null || MultSelectionEditor.iSize == 0)
      {
        parent.anchorMultSelectionsA.style.color = "black";
        parent.anchorMultSelectionsB.style.color = "black";
      } else {
        parent.anchorMultSelectionsA.style.color = "purple";
        parent.anchorMultSelectionsB.style.color = "purple";
      }

      parent.top.bottomFrame.MultSelectionFrame.MultSelectionEditor.submitCancel();
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
<script type="text/javascript" language="javascript" src="<%= inf.getHtmlDirectory() %>include-jscript-check-upc-ssg-sgc.js"></script>


</head>

<body topmargin=0 leftmargin=0 >
<table bgcolor="wheat" width="100%" height="130" border="1" cellspacing="0" cellpadding="2">
<tr>
<td>
  <table bgcolor="wheat" width="100%" height="130" border="0" cellspacing="0" cellpadding="3">
  <tr>
   <td valign="top" align="left">
  <IFRAME HEIGHT="130" width="100%" ID="MultSelectionEditor" Name="MultSelectionEditor" FRAMEBORDER=0 SCROLLING=auto SRC="<%= inf.getServletCmdURL("selection-multSelection-editor")%>">
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
