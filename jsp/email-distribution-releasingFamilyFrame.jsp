<%
   /* This variable holds the value to be placed in <title> html tag </title>
      for the include file: template-top-html.jsp */
   String htmlTitle = "Email Distribution Release Family Screen";

  %>



<%@ include file="template-top-page.shtml"%>
<%@ include file="template-top-html.shtml"%>

<%

    // set the debugging log for this object
    //ComponentLog  log = context.getApplication().getLog(SecurityHandler.COMPONENT_CODE);


    // get the email distribution object from the session
    int distributionId = -1;
    String selectedUserName = "";
    EmailDistribution emailDistr = (EmailDistribution)context.getSessionValue("emailDistribution");
    if(emailDistr != null)
    {
       distributionId = emailDistr.getDistributionId();
       selectedUserName = emailDistr.getFirstName() + " " + emailDistr.getLastName();
    }

    // get family data
    String labelFamilyName = context.getRequestValue("labelFamilyName");
    String labelFamilyId  = context.getRequestValue("labelFamilyId");

    // get releasing familes for this label family
    Hashtable emailDistReleasingFamiliesHash = null;
    Vector emailDistReleasingFamilies = new Vector();

    emailDistReleasingFamiliesHash = emailDistr.getReleasingFamily();
    if(emailDistReleasingFamiliesHash != null)
        emailDistReleasingFamilies =(Vector)emailDistReleasingFamiliesHash.get(labelFamilyId);


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
            boolean hasReleaseFamilies = false;
            if(emailDistReleasingFamilies != null)
            {
                for(int x=0; x < emailDistReleasingFamilies.size(); x++)
                {
                    EmailDistributionReleasingFamily releasingFamily = (EmailDistributionReleasingFamily)emailDistReleasingFamilies.get(x);
                    if(releasingFamily.getReleasingFamilyId() == family.getStructureID())
                    {
                       releasingFamilies.add(releasingFamily);
                       found = true;
                       break;  // stop for loop
                    }
                   // if this email distrubution record has releasing families assigned
                   hasReleaseFamilies = true;

                }
            }
            // unassigned releasing family
            if(!found)
            {
                boolean isLabelFamily = false;
                if(Integer.parseInt(labelFamilyId) == family.getStructureID() && !hasReleaseFamilies)
                  isLabelFamily = true;    // set to true if label family selected is equal to the label family object

                EmailDistributionReleasingFamily releasingFamily = new EmailDistributionReleasingFamily(
                                   Integer.parseInt(labelFamilyId),
                                   family.getStructureID(),isLabelFamily,family.getName(),
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

var cmd = "<%=EmailDistributionReleasingFamily.CMD_RELEASING_FAMILY%>";
var save = "<%=EmailDistributionReleasingFamily.CMD_SAVE_RELEASING_FAMILY%>";

function hideReleasingFamilyLayer()
{
   parent.top.bottomFrame.emailDistReleasingFamilyLayer.style.visibility = "hidden";

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


function submitSave()
{
    // submit the form
    document.forms["emailDistReleasingFamilyForm"].cmd.value = save;
    // alert(" << releasing family save command = " + save);
    showWaitMsg(); // mc its 966 show please wait message on screen
    document.forms["emailDistReleasingFamilyForm"].submit();


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
<form name="emailDistReleasingFamilyForm" action="<%=servletURL%>" method="POST" target="_parent">

 <input type="hidden" name="distribution-id" value="<%=distributionId%>" id="distribution-id">
 <input type="hidden" name="cmd" value="<%=EmailDistributionReleasingFamily.CMD_RELEASING_FAMILY%>">
 <input type="hidden" name="RFDistributionId" value="" id="RFDistributionId">
 <input type="hidden" name="RFLabelFamId" value="" id="RFLabelFamId">


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
                    <td colspan="1" width="25%" >Recipient: </td>
                      <td align="left" colspan="3" width="75%"><input class="ctrlMedium" style="font-size: 10pt; font-weight: bold; color: black; background-color: wheat; border: none;" type=text name="userNameRelFam" value="<%=selectedUserName%>">
                      </td>
    		</tr>

              	<tr>
			<td colspan="1" width="25%">Label Family:</td>
                      	<td align="left" colspan="3" width="75%"><input class="ctrlMedium" style="font-size: 10pt; font-weight: bold; color: black; background-color: wheat; border: none;" type=text name="labelFamName" value="<%=labelFamilyName%>">
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
                       EmailDistributionReleasingFamily releasingFamily = (EmailDistributionReleasingFamily)releasingFamilies.get(i);
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
                           <a class="<%=aClass%>" href="#">
                             <input type="checkbox" name="<%=elemName%>" value="<%=labFamId%>" tabindex="11" <%=checkedStr%> id="<%=elemName%>">
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
  layer = eval(parent.document.all["emailDistReleasingFamilyLayer"]);
  layer.style.visibility = "visible";

   document.all.RFDistributionId.value = "<%=distributionId%>";
   document.all.RFLabelFamId.value = "<%=labelFamilyId%>";


   document.all.buttonClose.focus();

   //position to hash position in parent window
   parent.window.location.hash = "#<%=labelFamilyName%>";

</script>

<%@ include file="include-bottom-html.shtml"%>