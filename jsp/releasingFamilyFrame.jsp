<%
   /* This variable holds the value to be placed in <title> html tag </title>
      for the include file: template-top-html.jsp */
   String htmlTitle = "Releasing Family Screen";

  %>



<%@ include file="template-top-page.shtml"%>
<%@ include file="template-top-html.shtml"%>

<%

    // set the debugging log for this object
    //ComponentLog  log = context.getApplication().getLog(SecurityHandler.COMPONENT_CODE);


    // get user data
    User selectedUserRF = null;
    String selectedUserName = "";
    int selectedUserId = -1;
    selectedUserRF = (User)context.getSessionValue("securityUser");
    if(selectedUserRF != null)
    {
        selectedUserName = selectedUserRF.getName();
        selectedUserId = selectedUserRF.getUserId();
    }
    // The request/response Context
    //  MilestoneContext        context = (MilestoneContext)request.getAttribute("Context");

    // get family data
    String labelFamilyName = context.getRequestValue("labelFamilyName");
    String labelFamilyId  = context.getRequestValue("labelFamilyId");


    // get releasing familes for this label family
    Hashtable releasingFamiliesHash = null;
    Vector userReleasingFamilies = new Vector();


    releasingFamiliesHash = selectedUserRF.getReleasingFamily();
    if(releasingFamiliesHash != null)
        userReleasingFamilies =(Vector)releasingFamiliesHash.get(labelFamilyId);


    // defaults for releasing family
    String defaultReleasingFamilyName = labelFamilyName;
    int    defaultReleasingFamilyId = -1;

    // get label families
    Vector families = Cache.getFamilies();

    Vector releasingFamilies = new Vector();

    if(families != null)
    {
      for(int i=0; i < families.size(); i++)
      {
        Family family = (Family)families.get(i);
        if(family != null)
        {
            boolean found = false;
            if(userReleasingFamilies != null)
            {
                for(int x=0; x < userReleasingFamilies.size(); x++)
                {
                    ReleasingFamily releasingFamily = (ReleasingFamily)userReleasingFamilies.get(x);
                    if(releasingFamily.getReleasingFamilyId() == family.getStructureID())
                    {
                       // default release family
                       //log.log("default = " + releasingFamily.IsDefault() +   "  Name = " + releasingFamily.getFamillyName());
                       if(releasingFamily.IsDefault())
                       {
                          defaultReleasingFamilyName = releasingFamily.getFamillyName();
                          defaultReleasingFamilyId = releasingFamily.getReleasingFamilyId();
                       }
                       releasingFamilies.add(releasingFamily);
                       found = true;
                       break;  // stop for loop
                    }
                }
            }
            // unassigned releasing family
            if(!found)
            {
                ReleasingFamily releasingFamily = new ReleasingFamily(Integer.parseInt(labelFamilyId),
                                   family.getStructureID(),false,false,family.getName(),
                                   family.getStructureAbbreviation());
                releasingFamilies.add(releasingFamily);
            }
        }
      }
    }


    String servletURL = inf.getServletURL();

    //log.log("  servlet URL = " + servletURL);



%>

<link rel="stylesheet" type="text/css" href="<%= inf.getHtmlDirectory() %>global.css" title="GlobalStyle">

<script language="javascript">

var cmd = "<%=ReleasingFamily.CMD_RELEASING_FAMILY%>";
var save = "<%=ReleasingFamily.CMD_SAVE_RELEASING_FAMILY%>";

function hideReleasingFamilyLayer()
{
   parent.top.bottomFrame.releasingFamilyLayer.style.visibility = "hidden";

 /*
  //layer = eval( document.all.[ pLayer ] )
  // Check to see if the layer is found (it must be at least an object)
  if ( ( typeof layer ) != "object" )
  {
    alert( "Layer to hide is not available." );
    return;
  }
      layer.style.visibility = "hidden";
*/
}  // End show release family

function setRelFamDefault(relFamNam,relFamId,relFamElemNam,setDefault)
{
  //alert("test setRelFamDft " + relFamNam);
  var defaultRelFamElemNam = eval(document.all[relFamElemNam]);
  defaultRelFamElemNam.checked = true;
  if(setDefault == "true")
  {
    document.all.defaultRelFamily.value = relFamNam;
    document.all.RFDftRelFamId.value = relFamId;
  }


}

function submitSave()
{
    if(document.all.RFDftRelFamId.value == "" || document.all.RFDftRelFamId.value == -1)
    {
        // check to see if any of the checkboxes are check
        var IsOneChecked = false;
        var elements = document.forms["userReleasingFamilyForm"].elements;
        for(x=0; x < elements.length; x++)
        {
            if(typeof elements[x] == "object" && elements[x].type == "checkbox" &&
                   elements[x].checked == true)
            {
                IsOneChecked = true;
                break;
            }
        }
        // if one the check boxes is checked
        if(IsOneChecked == true)
        {
            alert("Default releasing family is required! Please select a default releasing family");
            return false;
        }
    }

    // submit the form
    document.forms["userReleasingFamilyForm"].cmd.value = save;
    //alert(" << releasing family save command = " + save);
    showWaitMsg();  // mc its 966 show please wait message
    document.forms["userReleasingFamilyForm"].submit();


} //end function submitSave()


function checkDefault(obj)
{
   // alert("check box = " + obj.checked + " value  = " + obj.value);
    // if uncheck of
    if(obj.checked == false && obj.value == document.all.RFDftRelFamId.value)
    {
        document.all.RFDftRelFamId.value = "";
        document.all.defaultRelFamily.value = "";
    }
}


</script>

</head>

<body bgcolor="wheat" topmargin=0 leftmargin=0 >
<form name="userReleasingFamilyForm" action="<%=servletURL%>" method="POST" target="_parent">

 <input type="hidden" name="user-id" value="<%=selectedUserId%>" id="user-id">
 <input type="hidden" name="cmd" value="<%=ReleasingFamily.CMD_RELEASING_FAMILY%>">
 <input type="hidden" name="RFUserId" value="" id="RFUserId">
 <input type="hidden" name="RFLabelFamId" value="" id="RFLabelFamId">
 <input type="hidden" name="RFDftRelFamId" value="" id="RFDftRelFamId">


<table width="100%" bgcolor='wheat'>
<tr>
	<td>
          <table width="100%">

                <tr>
		  <th align="middle" colspan="4"  style="font-weight: bold; background-color: #bbbbbb;">Releasing Family Selection</th>
		</tr>

     		<tr>
       		  <td align="left" colspan="1" width="25%"><input type=button name="buttonClose" value="Close window" onClick="Javascript:hideReleasingFamilyLayer();">
                  <td align="middle" colspan="3" width="75%"><input type=button value="Save Releasing Family" onClick="Javascript:submitSave();">
        	</tr>

               <tr>
                  <td>&nbsp;</td>
		</tr>

          	<tr>
                    <td colspan="1" width="25%" >User: </td>
                      <td align="left" colspan="3" width="75%"><input class="ctrlMedium" style="font-size: 10pt; font-weight: bold; color: black; background-color: wheat; border: none;" type=text name="userNameRelFam" value="<%=selectedUserName%>">
                      </td>
    		</tr>

              	<tr>
			<td colspan="1" width="25%">Label Family:</td>
                      	<td align="left" colspan="3" width="75%"><input class="ctrlMedium" style="font-size: 10pt; font-weight: bold; color: black; background-color: wheat; border: none;" type=text name="labelFamName" value="<%=labelFamilyName%>">
                        </td>

    		</tr>

    		<tr>
			<td colspan="1" width="25%" >Default Releasing Family:</td>
                       <td align="left" colspan="3" width="75%"><input class="ctrlMedium" style="font-size: 10pt; font-weight: bold; color: red; background-color: wheat; border: none;" type=text name="defaultRelFamily" value="<%=defaultReleasingFamilyName%>">
                        </td>
		</tr>

                <tr>
                  <td>&nbsp;</td>
		</tr>

        	<tr>
			<td>Select Releasing Families:</td>
		</tr>

               	 <tr>
                <%
                  if(releasingFamilies != null)
                  {
                    int c = 0;
                    for(int i=0; i < releasingFamilies.size(); i++)
                    {
                       ReleasingFamily releasingFamily = (ReleasingFamily)releasingFamilies.get(i);
                       if(releasingFamily != null)
                       {
                         String labFamName = releasingFamily.getFamillyName();
                         String elemName = "RF" + Integer.toString(releasingFamily.getReleasingFamilyId());
                          String labFamId = Integer.toString(releasingFamily.getReleasingFamilyId());
                         String checkedStr = "";
                         String aClass = "";
                         if(releasingFamily.IsChecked())
                         {
                            checkedStr = "checked";
                            aClass = "releaseFamily";  // show in red
                         }
                         %>
                           <td colspan="1" width="25%" >
                           <a class="<%=aClass%>" href="JavaScript:setRelFamDefault('<%=labFamName%>','<%=labFamId%>','<%=elemName%>','false')" onDblClick="JavaScript:setRelFamDefault('<%=labFamName%>','<%=labFamId%>','<%=elemName%>','true')"
                                title="Double Click To Set As Default Releasing Family">
                             <input type="checkbox" name="<%=elemName%>" value="<%=labFamId%>" tabindex="11" <%=checkedStr%> id="<%=elemName%>" onclick="checkDefault(this)"
                                     onDblClick="JavaScript:setRelFamDefault('<%=labFamName%>','<%=labFamId%>','<%=elemName%>','true')">
                              &nbsp;&nbsp;<%=labFamName%>
                           </a>
                          </td>
                         <%
                         c++;
                         if (c % 4 == 0)
                         {
                           %></tr><tr><%
                         }
                       }
                    }
                  }
                %>


		<tr>
                 <td> &nbsp;</td>
                </tr>

                <tr>
                 <td style="color: purple;" colspan="4">Double Click To Set Default Releasing Family</td>
                </tr>

		<tr>
                 <td> &nbsp;</td>
                </tr>

                  <tr>
                 <td> &nbsp;</td>
                </tr>
            <tr>
                 <td> &nbsp;</td>
                </tr>

		</table>
	</td>
</tr>
</table>
</form>
</body>

<script language="javascript">

  // make visible
  layer = eval(parent.document.all["releasingFamilyLayer"]);
  layer.style.visibility = "visible";

   document.all.RFUserId.value = "<%=selectedUserId%>";
   document.all.RFLabelFamId.value = "<%=labelFamilyId%>";
   document.all.RFDftRelFamId.value = "<%=defaultReleasingFamilyId%>";
   // set default releasing family id as checked if none selected
   if(document.all.RFDftRelFamId.value == -1) // not selected
   {
      // set as label family id and set as checked
      document.all.RFDftRelFamId.value = document.all.RFLabelFamId.value;
      relFamElemNam = "RF" + document.all.RFLabelFamId.value;
      var defaultRelFamElemNam = eval(document.all[relFamElemNam]);
      defaultRelFamElemNam.checked = true;
   }
   document.all.buttonClose.focus();

   //position to hash position in parent window
   parent.window.location.hash = "#<%=labelFamilyName%>";

</script>

<%@ include file="include-bottom-html.shtml"%>