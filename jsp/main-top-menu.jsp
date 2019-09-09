<%@ include file="template-top-page.shtml"%>
<%
   // This variable is set here for include file - include-top-html.jsp

   String htmlTitle = "Milestone Top Frame";

   // This variable will hold the value of the first abailable Menu
   // for this user
   String initialMenu = "";

   // These variables will hold the value of the first abailable Sub Menu
   // for this user
   String labelInitSubMenu = "";
   String adminInitSubMenu = "";
   String securityInitSubMenu = "";
   String utilityInitSubMenu = "";

   // Variables for Label Sub Menu access initialized to false
   boolean selectionAccess = false;
   boolean scheduleAccess = false;
   boolean manufacturingAccess = false;
   boolean pfmAccess = false;
   boolean bomAccess = false;
   boolean reportAccess = false;

   // JR - ITS 578
   //boolean isDigitalSelection = false;

   // Variables for Admin Sub Menu access initialized to false
   boolean templateAccess = false;
   boolean taskAccess = false;
   boolean dayTypeAccess= false;

   // Variables for Security Sub Menu access initialized to false
   boolean userAccess  = false;
   boolean familyAccess = false;
   boolean environmentAccess = false;
   boolean companyAccess = false;
   boolean divisionAccess = false;
   boolean labelAccess = false;
   boolean emailDistAccess = false;

   // Variables for Utilities Sub Menu access initialized to false
   boolean tableAccess = false;
   boolean parameterAccess = false;
   boolean releaseWksAccess = false;
   boolean reportConfigAccess = false;
   boolean priceCodeAccess = false;

   // Get the user
   User user = MilestoneSecurity.getUser(context);
   // Get user acl - access list
   Acl acl = user.getAcl();

   // Get Access for Main Menu (Lables, Logoff, Admin, Security,
   // Utility)
   boolean lbLogoffAccess = true;  //Not ready yet
   boolean lbLabelAccess = acl.getAccessLabels();
   boolean lbAdminAccess = acl.getAccessAdmin();
   boolean lbSecurityAccess = acl.getAccessSecurity();
   boolean lbUtilityAccess  = acl.getAccessUtilities();

   String lastLink = (context.getSessionValue("lastLink") != null) ?(String)context.getSessionValue("lastLink") : "";

   // JR - ITS 578
   //Selection topFrameCurrentSelection = (Selection)context.getSessionValue("Selection");
   //if (topFrameCurrentSelection != null)
     //isDigitalSelection = topFrameCurrentSelection.getIsDigital();

   // Initialize initial menu based on the access for this user.
   // Get the first available menu and make it selected.
   if (lbUtilityAccess)
   {
     initialMenu = "Utilities";

     // Get access for utilities submenu
     tableAccess = acl.getAccessTable();
     parameterAccess = acl.getAccessParameter();

     // ReleaseWksAccess will always be true, does not come from the
     // back end.  The presumption here is that it is accessible to
     // anyone as long the user has Utilities Access.
     releaseWksAccess = true;
     reportConfigAccess = acl.getAccessReportConfig();
     priceCodeAccess = acl.getAccessPriceCode();

     // Get the first available sub menu for Security
     if (tableAccess)
     {
       utilityInitSubMenu = "tables-header";
     }
     else if (releaseWksAccess)
     {
       utilityInitSubMenu = "release-week";
     }
     else if (reportConfigAccess)
     {
       utilityInitSubMenu = "report-config";
     }
     else if (priceCodeAccess)
     {
       utilityInitSubMenu = "price-code";
     }
   }

   if (lbSecurityAccess)
   {
     initialMenu = "Security";

     // Get access for security submenu
     userAccess       = acl.getAccessUser();
     familyAccess     = acl.getAccessFamily();
     companyAccess    = acl.getAccessCompany();

     // JP ENV AFE
     environmentAccess    = true; //acl.getAccessEnvironment();
     divisionAccess   = acl.getAccessDivision();
     labelAccess      = acl.getAccessLabel();
     emailDistAccess   = acl.getAccessUser();


     // Get the first available sub menu for Security
     if(userAccess)
     {
       securityInitSubMenu = "user-security";
     }
     else if (familyAccess)
     {
       securityInitSubMenu = "family";
     }
     else if (environmentAccess)
     {
       securityInitSubMenu = "environment";
     }
     else if (companyAccess)
     {
       securityInitSubMenu = "company";
     }
     else if(divisionAccess)
     {
       securityInitSubMenu = "division";
     }
     else if(labelAccess)
     {
       securityInitSubMenu = "label";
     }
     else if(emailDistAccess)
     {
       securityInitSubMenu = "emailDist";
     }

   }

   if(lbAdminAccess)
   {
     initialMenu = "Admin";

     //get Access for Admin Submenu
     templateAccess = acl.getAccessTemplate();
     taskAccess = acl.getAccessTask();
     dayTypeAccess= acl.getAccessDayType();

     //get the first available sub menu for Admin
     if (templateAccess)
     {
       adminInitSubMenu = "template";
     }
     else if (taskAccess)
     {
       adminInitSubMenu = "task";
     }
     else if (dayTypeAccess)
     {
       adminInitSubMenu = "daytype";
     }
   }

   if (lbLabelAccess)
   {
     initialMenu = "Labels";

     // Get access for label submenu
     selectionAccess     = acl.getAccessSelection();
     scheduleAccess      = acl.getAccessSchedule();
     manufacturingAccess = acl.getAccessManufacturing();
     pfmAccess           = acl.getAccessPfmForm();
     bomAccess           = acl.getAccessBomForm();
     reportAccess        = acl.getAccessReport();

     if (scheduleAccess)
     {
       labelInitSubMenu = "schedule";
     }
     else if(selectionAccess)
     {
       labelInitSubMenu = "selection";
     }
     else if (manufacturingAccess)
     {
       labelInitSubMenu = "selection-manufacturing";
     }
     else if(pfmAccess)
     {
       labelInitSubMenu = "pfm";
     }
     else if(bomAccess)
     {
       labelInitSubMenu = "bom";
     }
     else if(reportAccess)
     {
       labelInitSubMenu = "reports";
     }

     // JP 11/27/03
     if (user.getPreferences().getOpeningScreen() > 0)
     {
       if (user.getPreferences().getOpeningScreen() == 1 && scheduleAccess) //release calendar
       {
         labelInitSubMenu = "release-calendar";
       }
       else if (user.getPreferences().getOpeningScreen() == 2 && scheduleAccess) //Schedule
       {
         labelInitSubMenu = "schedule";
       }
       else if (user.getPreferences().getOpeningScreen() == 3 && selectionAccess) //Selection
       {
         labelInitSubMenu = "selection";
       }
       else if (user.getPreferences().getOpeningScreen() == 4 && manufacturingAccess) //manufacturing
       {
         labelInitSubMenu = "selection-manufacturing";
       }
       else if (user.getPreferences().getOpeningScreen() == 5 && pfmAccess) //pfm
       {
         labelInitSubMenu = "pfm";
       }
       else if (user.getPreferences().getOpeningScreen() == 6 && bomAccess) //bom
       {
         labelInitSubMenu = "bom";
       }
       else if (user.getPreferences().getOpeningScreen() == 7 && reportAccess) //reports
       {
         labelInitSubMenu = "reports";
       }
     }
   }
%>
<%@ include file="template-top-html.shtml"%>

<link rel="stylesheet" type="text/css" href="./server.css" title="ServerStyle">
<script language="JavaScript">

var sRollColor =  16777215;
var sOnColor =  6750156;
var sOffColor = 65638;

//remember what Menu was selected
var selectedMenu = "";
//remember what Menu Item was selected
var selectedMenuItem = "";

//variables to hold the initial sub menu item
//or holds the selected menu item for a particular menu
var labelInitMenuItem = "";
var adminInitMenuItem = "";
var securityInitMenuItem = "";
var utilityInitMenuItem = "";

var lastLink = "<%=(context.getSessionValue("lastLink") != null) ?(String)context.getSessionValue("lastLink") : ""%>";

function setInitialMenu(initMenu)
{
  if (lastLink != "")
  {
    switch (lastLink)
    {
      case "<%= MilestoneHelper.CMD_SCHEDULE_EDITOR%>":
      case "<%= MilestoneHelper.CMD_SCHEDULE_TASK_EDITOR%>":
      case "<%= MilestoneHelper.CMD_SELECTION_EDITOR%>":
      case "<%= MilestoneHelper.CMD_SELECTION_MANUFACTURING_EDITOR%>":
      case "<%= MilestoneHelper.CMD_PFM_EDITOR%>":
      case "<%= MilestoneHelper.CMD_BOM_EDITOR%>":
      case "<%= MilestoneHelper.CMD_REPORTS_EDITOR%>":
      case "<%= MilestoneHelper.CMD_USER_SECURITY_EDITOR_INFO%>":  // msc @@@
      case "<%= MilestoneConstants.CMD_RELEASE_CALENDAR_EDIT%>":  // JR
        initMenu = "Labels";
        break;
      case "<%= MilestoneHelper.CMD_TEMPLATE_EDITOR%>":
      case "<%= MilestoneHelper.CMD_TEMPLATE_TASK_EDITOR%>":
      case "<%= MilestoneHelper.CMD_TASK_EDITOR%>":
      case "<%= MilestoneHelper.CMD_DAYTYPE_EDITOR%>":
        initMenu = "Admin";
        break;
      case "<%= MilestoneHelper.CMD_USER_SECURITY_EDITOR%>":
      //case "<%= MilestoneHelper.CMD_USER_COMPANY_SECURITY_EDITOR%>:
      case "<%= MilestoneHelper.CMD_USER_ENVIRONMENT_SECURITY_EDITOR%>":
      case "<%= MilestoneHelper.CMD_FAMILY_EDITOR%>":
      case "<%= MilestoneHelper.CMD_ENVIRONMENT_EDITOR%>":
      case "<%= MilestoneHelper.CMD_COMPANY_EDITOR%>":
      case "<%= MilestoneHelper.CMD_DIVISION_EDITOR%>":
      case "<%= MilestoneHelper.CMD_LABEL_EDITOR%>":
      case "<%= MilestoneHelper.CMD_EMAIL_DISTRIBUTION_EDITOR%>":
        initMenu = "Security";
        break;
      case "<%= MilestoneHelper.CMD_TABLES_HEADER_EDITOR%>":
      case "<%= MilestoneHelper.CMD_TABLES_DETAIL_EDITOR%>":
      case "<%= MilestoneHelper.CMD_RELEASE_WEEK_EDITOR%>":
      case "<%= MilestoneHelper.CMD_REPORT_CONFIG_EDITOR%>":
      case "<%= MilestoneHelper.CMD_PRICE_CODE_EDITOR%>":
        initMenu = "Utilities";
        break;
      default:
       initMenu = "Labels";
    }
    selectedMenu = initMenu;
    eval("document." + initMenu + ".src = '<%= inf.getImageDirectory() %>" + initMenu + "_On.gif';");
    eval("document.all." + initMenu + "Selection.style.visibility = 'visible'");
  }
  else
  {
    selectedMenu = initMenu;
    eval("document." + initMenu + ".src = '<%= inf.getImageDirectory() %>" + initMenu + "_On.gif';");
    eval("document.all." + initMenu + "Selection.style.visibility = 'visible'");
  }
}

function setInitialSubMenu(labelInitMenu, adminInitMenu, securityInitMenu, utilityInitMenu)
{
  labelInitMenuItem = labelInitMenu;
  adminInitMenuItem = adminInitMenu;
  securityInitMenuItem = securityInitMenu;
  utilityInitMenuItem = utilityInitMenu;

  if (lastLink != "")
  {
     switch (lastLink)
     {
       //Label cases
       case "<%=MilestoneConstants.CMD_RELEASE_CALENDAR_EDIT%>":
         selectedMenuItem = "release-calendar";
         break;
       case "<%= MilestoneHelper.CMD_SCHEDULE_EDITOR%>":
         selectedMenuItem = "schedule";
         break;
       case "<%= MilestoneHelper.CMD_SCHEDULE_TASK_EDITOR%>":
         selectedMenuItem = "schedule-task";
         break;
       case "<%= MilestoneHelper.CMD_SELECTION_EDITOR%>":
         selectedMenuItem = "selection";
         break;
       case "<%= MilestoneHelper.CMD_SELECTION_MANUFACTURING_EDITOR%>":
         selectedMenuItem = "selection-manufacturing";
         break;
       case "<%= MilestoneHelper.CMD_PFM_EDITOR%>":
         selectedMenuItem = "pfm";
         break;
       case "<%= MilestoneHelper.CMD_BOM_EDITOR%>":
         selectedMenuItem = "bom";
         break;
       case "<%= MilestoneHelper.CMD_REPORTS_EDITOR%>":
         selectedMenuItem = "reports";
         break;
       case "<%= MilestoneHelper.CMD_USER_SECURITY_EDITOR_INFO%>":   // msc
         selectedMenuItem = "userInfo";   // msc @@@
         break;

       //Admin cases
       case "<%= MilestoneHelper.CMD_TEMPLATE_EDITOR%>":
         selectedMenuItem = "template";
         break;
       case "<%= MilestoneHelper.CMD_TEMPLATE_TASK_EDITOR%>":
         selectedMenuItem = "template-task";
         break;
       case "<%= MilestoneHelper.CMD_TASK_EDITOR%>":
         selectedMenuItem = "task";
         break;
       case "<%= MilestoneHelper.CMD_DAYTYPE_EDITOR%>":
         selectedMenuItem = "daytype";
         break;

       //Security cases
       case "<%= MilestoneHelper.CMD_USER_SECURITY_EDITOR%>":
         selectedMenuItem = "user-security";
         break;
       //case "<%= MilestoneHelper.CMD_USER_COMPANY_SECURITY_EDITOR%>:
       //  selectedMenuItem = "user-company-security";
       //  break;
       case "<%= MilestoneHelper.CMD_USER_ENVIRONMENT_SECURITY_EDITOR%>":
         selectedMenuItem = "user-environment-security";
         break;
       case "<%= MilestoneHelper.CMD_FAMILY_EDITOR%>":
         selectedMenuItem = "family";
         break;
       case "<%= MilestoneHelper.CMD_ENVIRONMENT_EDITOR%>":
         selectedMenuItem = "environment";
         break;
       case "<%= MilestoneHelper.CMD_COMPANY_EDITOR%>":
         selectedMenuItem = "company";
         break;
       case "<%= MilestoneHelper.CMD_DIVISION_EDITOR%>":
         selectedMenuItem = "division";
         break;
       case "<%= MilestoneHelper.CMD_LABEL_EDITOR%>":
         selectedMenuItem = "label";
         break;
      case "<%= MilestoneHelper.CMD_EMAIL_DISTRIBUTION_EDITOR%>":
         selectedMenuItem = "emailDist";
         break;


       //Utility cases
       case "<%= MilestoneHelper.CMD_TABLES_HEADER_EDITOR%>":
         selectedMenuItem = "tables-header";
         break;
       case "<%= MilestoneHelper.CMD_TABLES_DETAIL_EDITOR%>":
         selectedMenuItem = "tables-detail";
         break;
       case "<%= MilestoneHelper.CMD_RELEASE_WEEK_EDITOR%>":
         selectedMenuItem = "release-week";
         break;
       case "<%= MilestoneHelper.CMD_REPORT_CONFIG_EDITOR%>":
         selectedMenuItem = "report-config";
         break;
       case "<%= MilestoneHelper.CMD_PRICE_CODE_EDITOR%>":
         selectedMenuItem = "price-code";
         break;
       default:
        initMenu = "Labels";
        selectedMenuItem = "schedule";
    } //end switch

    var sourceRef = "parent.top.bottomFrame.location ='home?cmd=" + selectedMenuItem + "-editor&showNotepad=true'";
    eval(sourceRef);

    if (selectedMenuItem == "template-task")
    {
      selectedMenuItem = "template";
    }

    if (selectedMenuItem == "schedule-task")
    {
      selectedMenuItem = "schedule";
    }

    if (selectedMenuItem == "tables-detail")
    {
      selectedMenuItem = "tables-header";
    }

    //if (selectedMenuItem == "user-company-security")
    if (selectedMenuItem == "user-environment-security")
    {
      selectedMenuItem = "user-security";
    }
  }
  else
  {
    if(selectedMenu == "Labels")
    {
      selectedMenuItem = labelInitMenu;
      var sourceRef = "parent.top.bottomFrame.location ='home?cmd=" + selectedMenuItem + "-editor&showNotepad=true;'";
      eval(sourceRef);
      //execScript(sourceRef);
    }
    else if(selectedMenu == "Admin")
    {
      selectedMenuItem = adminInitMenu;
      var sourceRef = "parent.top.bottomFrame.location ='home?cmd=" + selectedMenuItem + "-editor&showNotepad=true;'";
      eval(sourceRef);
      //execScript(sourceRef);
    }
    else if(selectedMenu == "Security")
    {
      selectedMenuItem = securityInitMenu;
      var sourceRef = "parent.top.bottomFrame.location ='home?cmd=" + selectedMenuItem + "-editor&showNotepad=true;'";
      eval(sourceRef);
      //execScript(sourceRef);
    }
    else if(selectedMenu == "Utilities")
    {
      selectedMenuItem = utilityInitMenu;
      var sourceRef = "parent.top.bottomFrame.location ='home?cmd=" + selectedMenuItem + "-editor&showNotepad=true;'";
      eval(sourceRef);
      //execScript(sourceRef);
    }
  }

  var menuItem = selectedMenuItem;
  menuItem = menuItem.replace("-", "");
  eval("document.all.MI" + menuItem + ".style.color = sOnColor");

  lastLink = "";
}

//do not replace this function with the include file include-jscript-toggle-button.js
//it is basicaly the same as the include, but has additional code to satisfy this page needs
function ToggleButton( pDocument, pImagePath, pImagePrefix, pLinkCall, pWidth, pHeight )
{
  // First time called, document will be false. Ignore this call.
  if( pDocument == null )
  {
    return;
  }

  this.limages = new Array(3);

  // The first time we are called (and only the first time) we have
  // to do some special stuff. First, now that the prototype object
  // is created, we can set up our methods.
  // Second, we've got to load the images that we'll be using.
  // Doing this will get the images in the cache for when we need them.

  if( !ToggleButton.prototype.over )
  {
    // Initialize the prototype object to create our methods.
    ToggleButton.prototype.over = _ToggleButton_over;
    ToggleButton.prototype.out = _ToggleButton_out;
    ToggleButton.prototype.click = _ToggleButton_click;
    ToggleButton.prototype.reset = _ToggleButton_reset;
    ToggleButton.prototype.showLabelsSubMenu = _ToggleButton_showLabelsSubMenu;
  }//end if !ToggleButton.Prototype

  // Now create an array of image objects, and assign URLs to them.
  // The URLs of the images are configurable, and are stored in an
  // array property of the constructor function itself. They will be
  // initialized below. Because of a bug in Navigator, we've got
  // to maintain references to these images, so we store the array
  // in a property of the constructor rather than using a local variable.

  for( var i = 0; i < 3; i++ )
  {
    this.limages[i] = new Image(pWidth, pHeight);
    var lEnd;
    switch(i)
    {
       case 0:
          lEnd = '_Off.gif';
          break;
       case 1:
          lEnd = '_Roll.gif';
          break;
       default:
          lEnd = '_On.gif';
    }

    this.limages[i].src = pImagePath + pImagePrefix + lEnd;

  }//end for loop

  // Save some of the arguments we were passed.
  this.isOff = false;

  // Remember that the mouse is not currently on top of us.
  this.isHighlighted = false;

 // Now save a reference to the Image object that it created in the ToggleButton object.

 this.image = eval(document.images[pImagePrefix]);

}//end ToggleButton

// This becomes the over() method.
function _ToggleButton_over()
{
  var whichImage = this.image.src;
  if (!(whichImage.indexOf("_On") != -1))
  {
    // Change the image, and remember that we're highlighted.
    if( !this.isOff )
    {
      this.image.src = this.limages[1].src;
      isHighlighted = true;
    }
  }
}//end function _ToggleButton_over()

// This becomes the out() method.
function _ToggleButton_out()
{
  var whichImage = this.image.src;
  if (!(whichImage.indexOf("_On") != -1))
  {
    // Change the image, and remember that we're not highlighted.
    if( !this.isOff )
    {
      this.image.src = this.limages[0].src;
      this.isHighlighted = false;
    }
  }
}//end function _ToggleButton_out()

// This becomes the click() method.
function _ToggleButton_click()
{
  // Toggle the state of the button, change the image
  this.isOff = !this.isOff;
  if(this.isOff)
  {
    //select the Menu Tab
    this.image.src = this.limages[2].src;

    //get the image prefix to find out which image was clicked
    //and normalize previous selected button
    var whichImage = this.image.src;

    if (whichImage.indexOf("Labels") != -1)
    {
      //only if old selected menu is not Labels proceed
      if(selectedMenu != "Labels")
      {
        //make the submenu available for Labels
        document.all.LabelsSelection.style.visibility = "visible";

        //deselect old Menu tab and make the Submenu for this menu hidden
        if(selectedMenu != "")
        {
          eval("document.all." + selectedMenu + ".src = '<%= inf.getImageDirectory() %>" + selectedMenu + "_Off.gif'");
          eval("document.all." + selectedMenu + "Selection.style.visibility = 'hidden'");
          deselectMenuItem(selectedMenuItem);
        }
        //save what was just selected
        selectedMenu = "Labels";
        setInitialSubMenu(labelInitMenuItem, adminInitMenuItem, securityInitMenuItem, utilityInitMenuItem);

        //display the page for the selected submenu
        // eval("parent.top.bottomFrame.location ='home?cmd=" + labelInitMenuItem + "-editor&showNotepad=true'");
      }
    }
    if (whichImage.indexOf("Admin") != -1)
    {
      //only if old selected menu is not Admin proceed
      if(selectedMenu != "Admin")
      {
        //make the submenu available for Admin
        document.all.AdminSelection.style.visibility = "visible";
        //deselect old Menu tab and make the Submenu for this menu hidden
        if(selectedMenu != "")
        {
          eval("document.all." + selectedMenu + ".src = '<%= inf.getImageDirectory() %>" + selectedMenu + "_Off.gif';");
          eval("document.all." + selectedMenu + "Selection.style.visibility = 'hidden'");
          deselectMenuItem(selectedMenuItem);
        }
        //save what was just selected
        selectedMenu = "Admin";
        setInitialSubMenu(labelInitMenuItem, adminInitMenuItem, securityInitMenuItem, utilityInitMenuItem);

        //display the page for the selected submenu
        // eval("parent.top.bottomFrame.location ='home?cmd=" + adminInitMenuItem + "-editor&showNotepad=true'");
      }
    }
    if (whichImage.indexOf("Security") != -1)
    {
      //only if old selected menu is not Security proceed
      if(selectedMenu != "Security")
      {
        //make the submenu available for Security
        document.all.SecuritySelection.style.visibility = "visible";
        //deselect old Menu tab and make the Submenu for this menu hidden
        if(selectedMenu != "")
        {
          eval("document.all." + selectedMenu + ".src = '<%= inf.getImageDirectory() %>" + selectedMenu + "_Off.gif';");
          eval("document.all." + selectedMenu + "Selection.style.visibility = 'hidden'");
          deselectMenuItem(selectedMenuItem);
        }
        //save what was just selected
        selectedMenu = "Security";
        setInitialSubMenu(labelInitMenuItem, adminInitMenuItem, securityInitMenuItem, utilityInitMenuItem);

        //display the page for the selected submenu
        //eval("parent.top.bottomFrame.location ='home?cmd=" + securityInitMenuItem + "-editor&showNotepad=true'");
      }
    }
    if (whichImage.indexOf("Utilities") != -1)
    {
      //only if old selected menu is not Utilities proceed
      if(selectedMenu != "Utilities")
      {
        //make the submenu available for Utilities
        document.all.UtilitiesSelection.style.visibility = "visible";
        //deselect old Menu tab and make the Submenu for this menu hidden
        if(selectedMenu != "")
        {
          eval("document.all." + selectedMenu + ".src = '<%= inf.getImageDirectory() %>" + selectedMenu + "_Off.gif';");
          eval("document.all." + selectedMenu + "Selection.style.visibility = 'hidden'");
          deselectMenuItem(selectedMenuItem);
        }
        //save what was just selected
        selectedMenu = "Utilities";
        setInitialSubMenu(labelInitMenuItem, adminInitMenuItem, securityInitMenuItem, utilityInitMenuItem);

        //display the page for the selected submenu
        // eval("parent.top.bottomFrame.location ='home?cmd=" + utilityInitMenuItem + "-editor&showNotepad=true'");
      }
    }
    this.isOff = false;
  }
}//end function _ToggleButton_click()

// This becomes the reset() method.
function _ToggleButton_reset()
{
  // Toggle the state of the button, change the image
  this.isOff = !this.isOff;
  if( this.isHighlighted )
  {
    this.image.src = this.limages[1].src;
  }
  else
  {
    this.image.src = this.limages[0].src;
  }
}//end function _ToggleButton_reset()




  function highlightLogoff()
  {
    document.Logoff.src = "<%= inf.getImageDirectory() %>Logoff_Tab_Over.gif";
  }//end function

  function normalizeLogoff()
  {
    document.Logoff.src = "<%= inf.getImageDirectory() %>Logoff_Tab_On.gif";
  }//end function

  function exitApp()
  {
    var sourceRef = "home?cmd=logoff";
    parent.document.location.href = sourceRef;
  }//end function

  function highlightMenuItem( pMenuItem )
  {
    if (selectedMenuItem != "")
    {
      var menuItem = pMenuItem.replace("-", "");
      if(selectedMenuItem != pMenuItem)
      {
        // Only change the text color
        eval("document.all.MI" + menuItem + ".style.color = sRollColor");
      }
    }
  }//end function

  function normalizeMenuItem( pMenuItem )
  {
    if (selectedMenuItem != "")
    {
      var menuItem = pMenuItem.replace("-", "");
      if(selectedMenuItem != pMenuItem)
      {
        eval("document.all.MI" + menuItem + ".style.color = sOffColor");
      }
    }
  }//end function

  function selectMenuItem( pNewMenuItem, menu)
  {
     var newMenuItem = pNewMenuItem.replace("-", "");
     if (pNewMenuItem != selectedMenuItem)
     {
       // Select the new one and memorize it as old one
       eval("document.all.MI" + newMenuItem + ".style.color = sOnColor");

       //deselect the old menu item
       if (selectedMenuItem != "")
       {
         deselectMenuItem(selectedMenuItem);
       }
       selectedMenuItem = pNewMenuItem;

       //if(menu == "Labels")
       //{
       //  labelInitMenuItem = pNewMenuItem;
       //}
       //if(menu == "Admin")
       //{
       //  adminInitMenuItem = pNewMenuItem;
       //}
       //if(menu == "Security")
       //{
       //  securityInitMenuItem = pNewMenuItem;
       //}
       //if(menu == "Utilities")
       //{
       //  utilityInitMenuItem = pNewMenuItem;
       //}

     }//end if pNewMenuItem != selectedMenuItem
  }//end function

  function deselectMenuItem( pMenuItem )
  {
    var menuItem = pMenuItem.replace("-", "");
    eval("document.all.MI" + menuItem + ".style.color = sOffColor");
  }//end function

  // MSC Display the Labels sub-menu
  function _ToggleButton_showLabelsSubMenu()
  {
    // Toggle the state of the button, change the image
    this.isOff = !this.isOff;
    if(this.isOff)
    {
      //select the Menu Tab
      this.image.src = this.limages[2].src;

      //only if old selected menu is not Labels proceed
      if(selectedMenu != "Labels")
      {
        //make the submenu available for Labels
        document.all.LabelsSelection.style.visibility = "visible";

        //deselect old Menu tab and make the Submenu for this menu  hidden
        if(selectedMenu != "")
        {
          eval("document.all." + selectedMenu + ".src = '<%= inf.getImageDirectory() %>" + selectedMenu + "_Off.gif'");
          eval("document.all." + selectedMenu + "Selection.style.visibility = 'hidden'");
          deselectMenuItem(selectedMenuItem);
        }

        //save what was just selected
        selectedMenu = "Labels";
      }

      this.isOff = false;
    }
  } //end function

//set global Javascript value with environment session value
var environment_name = ""
environment_name = "<%=(String)context.getSessionValue("environment_name")%>";

</script>

  <style type="text/css">
   {
	 font size: 10pt;
	 font-family : Arial;
	 font-weight : bold;
   }

   #MilestoneText
   {
     z-index: 0;
     position: absolute;
     top: 7px;
     left: 20px;
   }
   #ButtonBackPanel
   {
     z-index: 0;
     position: absolute;
     top: 7px;
     left: 231px;
   }

   #Buttons
   {
     z-index: 1;
     position: absolute;
     top: 17px;
   }

   #SubMenuPanel
   {
     z-index: 2;
     position: absolute;
     top: 38px;
     left: 0px;
   }

   #SelectionInfo
   {
     z-index: 3;
     position: absolute;
     top: 42px;
     left: 20px;
     font size:10pt;
     font-family:Arial;
     font-weight:bold;
     cursor:hand;
   }

   .SelectionStyle
   {
     position:absolute;
     visibility:hidden;
   }

   .MenuStyle
   {
     font size:10pt;
     font-family:Arial;
     font-weight:bold;
   }
  </style>
  </head>

 <body text="#010066"
  onLoad="setInitialMenu('<%= initialMenu%>'); setInitialSubMenu('<%= labelInitSubMenu%>', '<%= adminInitSubMenu%>', '<%= securityInitSubMenu%>', '<%= utilityInitSubMenu%>')"
 <% //Display relevant Milestone image for Local/Development/Test/Production
    //jo - ITS #871
     String envColor = "white";
     envColor = (String)context.getSessionValue("environment_color");%>
     bgcolor="#<%=envColor%>"
 >

 <div id="MilestoneText">

 <% //Display relevant Milestone image for Local/Development/Test/Production
    //jo - ITS #871
     String envName = "";
     envName = (String)context.getSessionValue("environment_name");
     if (envName.equals("Production")){%>
         <img border=0 src="<%= inf.getImageDirectory() %>Milestone_Text.gif" alt="Milestone Production" border=0>
     <% } else { %>
         <img border=0 src="<%= inf.getImageDirectory() %>Milestone_Text_<%=envName%>.gif" alt="Milestone Production" border=0>
     <% } %>
 </div>

 <div id="ButtonBackPanel">
   <img src="<%= inf.getImageDirectory() %>Button_Back_Panel.gif" width=564 height=51 border=0 alt="Back Panel">
 </div>

 <div id="Buttons">

 <% if( lbLogoffAccess )
    {%>
   <div id="LogoffButton" Style="position: absolute;left: 295px;">
     <img src="<%= inf.getImageDirectory() %>Logoff_Tab_On.gif" width=75 height=45 alt="Logoff"
        border=0 name="Logoff" id="Logoff"
        onMouseOver="highlightLogoff()"
        onMouseOut="normalizeLogoff()"
        onClick="exitApp()">

   </div>
 <%}%>

<% if( lbLabelAccess )
   {%>
  <div id="LabelsButton" Style="position: absolute;left: 470px;">
     <img name="Labels" id="Labels" src="<%= inf.getImageDirectory() %>Labels_Off.gif"
          onMouseOver="Javascript:mtbLabels.over();return true;"
          onMouseOut="Javascript:mtbLabels.out()"
          onClick="Javascript:mtbLabels.click(); return true;"
          width=75 height=45 border=0>
     <script type="text/javascript" language="JavaScript">
     <!--
       var mtbLabels = new ToggleButton(document, '<%= inf.getImageDirectory() %>', 'Labels', "", 75, 45);
     //-->
    </script>
  </div>
<% }
  if( lbAdminAccess )
  {%>
  <div id="AdminButton" Style="position: absolute;left: 550px;">
          <img name="Admin" id="Admin" src="<%= inf.getImageDirectory() %>Admin_Off.gif"
               onMouseOver="Javascript:mtbAdmin.over();return true;"
               onMouseOut="Javascript:mtbAdmin.out()"
               onClick="Javascript:mtbAdmin.click(); return true;"
               width=75 height=45 border=0>
     <script type="text/javascript" language="JavaScript">
     <!--
       var mtbAdmin = new ToggleButton(document, '<%= inf.getImageDirectory() %>', 'Admin', "", 75, 45);
     //-->
    </script>
 </div>
 <% }%>
 <% if( lbSecurityAccess )
    {%>
 <div id="SecurityButton" Style="position: absolute;left: 630px;">
     <img name="Security" id="Security" src="<%= inf.getImageDirectory() %>Security_Off.gif"
          onMouseOver="Javascript:mtbSecurity.over();return true;"
          onMouseOut="Javascript:mtbSecurity.out()"
          onClick="Javascript:mtbSecurity.click(); return true;"
          width=75 height=45 border=0>
     <script type="text/javascript" language="JavaScript">
     <!--
       var mtbSecurity = new ToggleButton(document, '<%= inf.getImageDirectory() %>', 'Security', "", 75, 45);
     //-->
    </script>
 </div>
 <%}
   if( lbUtilityAccess )
   {%>
 <div id="UtilitiesButton" Style="position: absolute;left: 710px;">
    <img name="Utilities" id="Utilities" src="<%= inf.getImageDirectory() %>Utilities_Off.gif"
         onMouseOver="Javascript:mtbUtilities.over();return true;"
         onMouseOut="Javascript:mtbUtilities.out()"
         onClick="Javascript:mtbUtilities.click(); return true;"
         width=75 height=45 border=0>
     <script type="text/javascript" language="JavaScript">
     <!--
       var mtbUtilities = new ToggleButton(document, '<%= inf.getImageDirectory() %>', 'Utilities', "", 75, 45);
     //-->
    </script>
 </div>
 <%}%>
</div>

<div id="SubMenuPanel">
  <img src="<%= inf.getImageDirectory() %>Sub_Menu_Panel.gif" width=795 height=24 alt="" border=0>
</div>

<div id="SelectionInfo">
<% if (lbLabelAccess)
   {%>
  <div id="LabelsSelection" class="SelectionStyle">
    <table cellpadding=0 cellspacing=0 width=768>
      <tr>

        <td>
          <div id="MIschedule" Class="MenuStyle" <%if (scheduleAccess){%> onMouseOver="highlightMenuItem('schedule')"  onMouseOut="normalizeMenuItem('schedule')"  onClick="selectMenuItem('schedule','Labels');parent.top.bottomFrame.location ='home?cmd=schedule-editor&showNotepad=true'"<%}%> >Schedule&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
        </td>
        <td>
          <div id="MIselection" Class="MenuStyle" <% if (selectionAccess){%> onMouseOver="highlightMenuItem('selection')"  onMouseOut="normalizeMenuItem('selection')"  onClick="selectMenuItem('selection','Labels');parent.top.bottomFrame.location ='home?cmd=selection-editor&showNotepad=true'"<%}%>>Selection&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
        </td>
        <td>
	      <% //if (!isDigitalSelection) { %>
          <div name="MIselectionmanufacturing" id="MIselectionmanufacturing" Class="MenuStyle" <% if (manufacturingAccess){%> onMouseOver="highlightMenuItem('selection-manufacturing')"  onMouseOut="normalizeMenuItem('selection-manufacturing')"  onClick="selectMenuItem('selection-manufacturing','Labels');parent.top.bottomFrame.location ='home?cmd=selection-manufacturing-editor&showNotepad=true'"<%}%>>Manufacturing&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
          <% //} %>
        </td>
        <td>
          <div id="MIpfm" Class="MenuStyle" <% if (pfmAccess){%> onMouseOver="highlightMenuItem('pfm')"  onMouseOut="normalizeMenuItem('pfm')"  onClick="selectMenuItem('pfm','Labels');parent.top.bottomFrame.location ='home?cmd=pfm-editor&showNotepad=true'"<%}%>>PFM&nbsp;&nbsp;Form&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
        </td>
        <td>
          <% //if (!isDigitalSelection) { %>
          <div name="MIbom" id="MIbom" Class="MenuStyle" <% if (bomAccess){%> onMouseOver="highlightMenuItem('bom')"  onMouseOut="normalizeMenuItem('bom')"  onClick="selectMenuItem('bom','Labels');parent.top.bottomFrame.location ='home?cmd=bom-editor&showNotepad=true'"<%}%>>BOM&nbsp;&nbsp;Form&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
          <% //} %>
        </td>

        <td nowrap>
          <div id="MIreleasecalendar" Class="MenuStyle" <%if (scheduleAccess){%> onMouseOver="highlightMenuItem('release-calendar')"  onMouseOut="normalizeMenuItem('release-calendar')"  onClick="selectMenuItem('release-calendar','Labels');parent.top.bottomFrame.location ='home?cmd=release-calendar-editor'"<%}%> >Release Calendar&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
        </td>

        <td>
          <div id="MIreports" Class="MenuStyle" <% if (reportAccess){%> onMouseOver="highlightMenuItem('reports')"  onMouseOut="normalizeMenuItem('reports')"  onClick="selectMenuItem('reports','Labels');parent.top.bottomFrame.location ='home?cmd=reports-editor&showNotepad=true'"<%}%>>Reports&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
        </td>
        <td>
           <!-- // msc @@@ -->
          <div id="MIuserinfo" Class="MenuStyle" onMouseOver="highlightMenuItem('userinfo')"  onMouseOut="normalizeMenuItem('userinfo')"  onClick="selectMenuItem('userinfo','Admin');parent.top.bottomFrame.location ='home?cmd=user-security-editor-info'">User&nbsp;Prefs&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
        </td>
        <td width="100%"></td>
      </tr>
    </table>
  </div>
  <%}
    if (lbAdminAccess)
    {%>
  <div id="AdminSelection" Class="SelectionStyle">
  <table cellpadding=0 cellspacing=0 width=768>
    <tr>
      <td>
        <div id="MItemplate" Class="MenuStyle" <% if (templateAccess){%>  onMouseOver="highlightMenuItem('template')"  onMouseOut="normalizeMenuItem('template')"  onClick="selectMenuItem('template','Admin');parent.top.bottomFrame.location ='home?cmd=template-editor&showNotepad=true'"<%}%>>Templates&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
      </td>
      <td>
        <div id="MItask" Class="MenuStyle"  <% if (taskAccess){%> onMouseOver="highlightMenuItem('task')"  onMouseOut="normalizeMenuItem('task')"  onClick="selectMenuItem('task','Admin');parent.top.bottomFrame.location ='home?cmd=task-editor&showNotepad=true'"<%}%>>Tasks&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
      </td>
      <% if (dayTypeAccess){ %>
             <td>
                  <div id="MIdaytype" Class="MenuStyle"  onMouseOver="highlightMenuItem('daytype')"  onMouseOut="normalizeMenuItem('daytype')"  onClick="selectMenuItem('daytype','Admin');parent.top.bottomFrame.location ='home?cmd=daytype-editor&showNotepad=true'">Day&nbsp;&nbsp;Types&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
             </td>
      <% } %>
      <td width="100%"></td>
    </tr>
   </table>
 </div>
 <%}
   if (lbSecurityAccess)
   {%>
 <div id="SecuritySelection" Class="SelectionStyle">
   <table cellpadding=0 cellspacing=0 width=768>
     <tr>
       <td>
         <div id="MIusersecurity" Class="MenuStyle" <% if (userAccess){%> onMouseOver="highlightMenuItem('user-security')"  onMouseOut="normalizeMenuItem('user-security')" onClick="selectMenuItem('user-security','Security');parent.top.bottomFrame.location ='home?cmd=user-security-editor&showNotepad=true'"<%}%>>Users&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
       </td>
       <td>
         <div id="MIfamily" Class="MenuStyle" <% if (familyAccess){%> onMouseOver="highlightMenuItem('family')"  onMouseOut="normalizeMenuItem('family')"  onClick="selectMenuItem('family','Security');parent.top.bottomFrame.location ='home?cmd=family-editor&showNotepad=true'"<%}%>>Family&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
       </td>
       <td>
   	 <div id="MIenvironment" Class="MenuStyle" <% if (environmentAccess){%> onMouseOver="highlightMenuItem('environment')"  onMouseOut="normalizeMenuItem('environment')"  onClick="selectMenuItem('environment','Security');parent.top.bottomFrame.location ='home?cmd=environment-editor&showNotepad=true'"<%}%>>Environment&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
       </td>
       <td>
         <div id="MIcompany" Class="MenuStyle" <% if (companyAccess){%> onMouseOver="highlightMenuItem('company')"  onMouseOut="normalizeMenuItem('company')"  onClick="selectMenuItem('company','Security');parent.top.bottomFrame.location ='home?cmd=company-editor&showNotepad=true'"<%}%>>Company&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
       </td>
       <td>
         <div id="MIdivision" Class="MenuStyle" <% if (divisionAccess){%> onMouseOver="highlightMenuItem('division')"  onMouseOut="normalizeMenuItem('division')"  onClick="selectMenuItem('division','Security');parent.top.bottomFrame.location ='home?cmd=division-editor&showNotepad=true'"<%}%>>Division&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
       </td>
       <td>
         <div id="MIlabel" Class="MenuStyle" <% if (labelAccess){%> onMouseOver="highlightMenuItem('label')"  onMouseOut="normalizeMenuItem('label')"  onClick="selectMenuItem('label','Security');parent.top.bottomFrame.location ='home?cmd=label-editor&showNotepad=true'"<%}%>>Label&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
       </td>
       <td>
         <div id="MIemailDist" Class="MenuStyle" <% if (emailDistAccess){%> onMouseOver="highlightMenuItem('emailDist')"  onMouseOut="normalizeMenuItem('emailDist')"  onClick="selectMenuItem('emailDist','Security');parent.top.bottomFrame.location ='home?cmd=email-distribution-editor&showNotepad=true'"<%}%>>Email&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
       </td>
       <td width="100%"></td>
     </tr>
   </table>
 </div>
 <%}
   if (lbUtilityAccess)
   {%>
 <div id="UtilitiesSelection" Class="SelectionStyle">
   <table cellpadding=0 cellspacing=0 width=768>
     <tr>
       <td>
         <div id="MItablesheader" Class="MenuStyle"  <% if (tableAccess){%> onMouseOver="highlightMenuItem('tables-header')"  onMouseOut="normalizeMenuItem('tables-header')"  onClick="selectMenuItem('tables-header','Utilities');parent.top.bottomFrame.location ='home?cmd=tables-header-editor&showNotepad=true'"<%}%>>Tables&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
       </td>

       <td>
         <div id="MIparameters" Class="MenuStyle" <% if (parameterAccess){%> onMouseOver="highlightMenuItem('parameters')"  onMouseOut="normalizeMenuItem('parameters')"<%}%>>Parameters&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</DIV>
       </td>
       <!-- Release Wks is allways accessible if the user has Utility Access -->
       <td>
         <div id="MIreleaseweek" Class="MenuStyle"  onMouseOver="highlightMenuItem('release-week')"  onMouseOut="normalizeMenuItem('release-week')"  onClick="selectMenuItem('release-week','Utilities');parent.top.bottomFrame.location ='home?cmd=release-week-editor&showNotepad=true'">Release&nbsp;Weeks&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
       </td>
       <td>
         <div id="MIreportconfig" Class="MenuStyle"  <% if (reportAccess){%> onMouseOver="highlightMenuItem('report-config')"  onMouseOut="normalizeMenuItem('report-config')"  onClick="selectMenuItem('report-config','Utilities');parent.top.bottomFrame.location ='home?cmd=report-config-editor&showNotepad=true'"<%}%>>Report&nbsp;Config&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
       </td>
       <td>
         <div id="MIpricecode" Class="MenuStyle" <% if (priceCodeAccess){%> onMouseOver="highlightMenuItem('price-code')"  onMouseOut="normalizeMenuItem('price-code')"  onClick="selectMenuItem('price-code','Utilities');parent.top.bottomFrame.location ='home?cmd=price-code-editor&showNotepad=true'"<%}%>>Price&nbsp;Code&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
       </td>
       <td width="100%"></td>
     </tr>
   </table>
 </div>
 <%}%>
  <img src="<%= inf.getImageDirectory() %>Toggle_On.gif" Height=0 Width=0 >
</div>

<DIV id="WaitLayerDivx"  style="position:absolute; filter:wave(2); display:none; z-index:200; left: 120px; top: 0px;">
<table width="150" border=0 cellspacing=0 cellpadding=0>
  <tr valign="top" align="left">
    <td style="font-size: 10pt; filter:wave(2); font-weight:bold; color:red;" align=left width=150>
      ** Please Wait **
    </td>
  </tr>
</table>
</div>

<DIV id="WaitLayerDivY" style="display:none; BACKGROUND-COLOR: #6699ff">
<img id="WaitImg" style="Z-INDEX: 200; LEFT: 0px; POSITION: absolute; TOP: 0px"
    Height="56px" Width="64px" SRC="images/hourglass1w.gif" Title="Please Wait..." Align="Top">
</DIV>

<DIV id="WaitLayerDiv" style="display:block; BACKGROUND-COLOR: #6699ff">
<img id="WaitImg" style="Z-INDEX: 200; LEFT: 200px; POSITION: absolute; TOP: 0px"
    Height="35px" Width="30px" SRC="images/hourglass_01-over.gif" Title="Please Wait..." Align="Top">
</DIV>


<script>
var bites = document.cookie.split("; "); // break cookie into array of bites
var expiry = new Date((new Date()).getTime() + 2 * 3600000); // 2 hours

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
    // remove the cookie if empty string
    if (value != null && value == "")
      document.cookie=name + "=" + escape(value) + "; expires=Fri, 02-Jan-1970 00:00:00 GMT";
    bites = document.cookie.split("; "); // update cookie bites
  }

</script>

<script for=document event=onclick language="JavaScript">

var saveLastLink = "Labels";  // initial top level menu option
var eID = window.event.srcElement.id;

if( (eID == "Labels" || eID == "Admin" || eID == "Security" || eID == "Utilities")
     && getCookie("lastLink") != "")
  saveLastLink = getCookie("lastLink");

/*
 alert("ID (" + window.event.srcElement.id + ") Name (" + window.event.srcElement.name
       + ") Object (" +  window.event.srcElement + ")"
       + " Type (" + window.event.srcElement.type
       + " tAg (" + window.event.srcElement.tagName
       + ")");
*/

if(document.all.WaitLayerDiv
     && eID != "MIparameters"
     && eID != ""
     && eID != saveLastLink)
{
    document.all.WaitLayerDiv.style.display = "block";
}

// save last link for the top level menu options
if(eID == "Labels" || eID == "Admin" || eID == "Security" || eID == "Utilities")
  setCookie("lastLink",eID);  // save the last link
// if log off remove cookie
if(eID == "Logoff")
   setCookie("lastLink","");  // remove cookie
</script>


</body>
</html>