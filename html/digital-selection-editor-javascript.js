/*
Development History:
- 2004-04-29 lg ITS 977 Modified ValidateDate to remove spaces from dates; Dates entered
  with blanks (e.g. ' 4/24/04') were saved as nulls without warning or error;
  2006-11-27 - mc - ITS 53079 Update Label task completion date on manual close
*/

// global variables
var count = 0;
var holdReasonCount = 0;
var commentsCount = 0;
var PackagingCount = 0;
var commentsCount = 0;
var commentType = '0';
var TerritoryCount = 0;
function toggleSearch()
{
	toggle( 'SearchLayer', 'ArtistSearch' );
} // end function toggleSearch

function checkPage()
{
	// do nothing
} // end function checkPage()

function Is()
{
	var agent = navigator.userAgent.toLowerCase();
	this.major = parseInt(navigator.appVersion);
  this.minor = parseFloat(navigator.appVersion);
  this.ns = ((agent.indexOf('mozilla') != -1) && ((agent.indexOf('spoofer') == -1) && (agent.indexOf('compatible') == -1)));
  this.ns2 = (this.ns && (this.major == 2));
  this.ns3 = (this.ns && (this.major == 3));
  this.ns4 = (this.ns && (this.major >= 4));
  this.ie = (agent.indexOf("msie")  != -1);
  this.ie3 = (this.ie && (this.major == 2));
  this.ie4 = (this.ie && (this.major >= 4));
} //end function Is()

var is = new Is();
if(is.ns4)
{
	doc = "document";
  sty = "";
  htm = ".document";
}
else if(is.ie4)
{
  doc = "document.all";
  sty = ".style";
  htm = "";
}

function checkField( pField )
{
	var bReturn = true;
  switch( pField.name )
  {
		case "comments":
    	bReturn = bReturn && checkLength( pField.value, "Comments", 0, 4000 );
      break;
    case "releaseComment":
      bReturn = bReturn && checkLength( pField.value, "Release Comments", 0, 1024 );
      break;
		case "holdReason":
      bReturn = bReturn && checkLength( pField.value, "Hold Reason", 0, 255 );
      break;
		case "streetDate":
    	bReturn = bReturn && checkFormat( pField.value, "Street Date", "date", "" );
      break;
		case "impactdate":
    	bReturn = bReturn && checkFormat( pField.value, "Impact Date", "date", "" );
      break;
		case "internationalDate":
    	bReturn = bReturn && checkFormat( pField.value, "International Date", "date", "" );
      break;
		case "StreetDateSearch":
			bReturn = bReturn && checkFormat( pField.value, "Street Date Search", "date", "" );
      break;
	}
	if( pField.name.substring( 0, 'dueDate'.length ) == "dueDate" )
	{
		bReturn = bReturn && checkDate( pField, "Due Date", true );
	}
	if( pField.name.substring( 0, 'completionDate'.length ) == "completionDate" )
	{
		var now = new Date();
    var nowTime = now.getTime();
    var nextWeek = nowTime + 604800000;
    var testResult = checkDate( pField, "Completion Date", true );
    bReturn = bReturn && testResult;
    if (testResult)
    {
    	var compTimeDate = new Date(pField.value);
      var year = compTimeDate.getFullYear();
      if(year < 1970)
      {
      	year = year + 100 ;compTimeDate.setYear(year);
      }
			var compTime = compTimeDate.getTime();
      if (compTime > nextWeek)
      {
      	alert("The completion time is beyond 7 days.");
        bReturn = false;
			}
			else
			{
      	bReturn = bReturn && true;
      }
    } //end if testResult
  } //end if pField.name == "completioinDate"
	if( !bReturn )
  {
  	pField.focus();
	}
	return bReturn;
} //end function checkField()

var percentCount = 0;
var percent = "0%";
var layer2Count = 0;

function toggleSearch2()
{
	layer = eval(doc + '["searchLayer2"]' + sty);
  if(layer2Count == 0)
  {
  	layer3Count = 0;
    otherLayer = eval(doc + '["searchReleaseSource"]' + sty);
    otherLayer.visibility = "Hidden";
    layer.visibility = "Visible";
    document.forms[0].searchIndex.value = "2";
    document.forms[0].TemplateNameSearch.focus();
    layer2Count = 1;
  }
  else
  {
  	layer.visibility = "Hidden";
    layer2Count = 0;
  }
} //end function toggleSearch2()

var layer3Count = 0;

function toggleSearch3()
{
	layer = eval(doc + '["searchReleaseSource"]' + sty);
  if(layer3Count == 0)
  {
  	layer2Count = 0;
    otherLayer = eval(doc + '["searchLayer2"]' + sty);
    otherLayer.visibility = "Hidden";
    layer.visibility = "Visible";
    document.forms[0].searchIndex.value = "3";
    document.forms[0].ArtistSearchSource.focus();
    layer3Count = 1;
	}
	else
  {
  	layer.visibility = "Hidden";
    layer3Count = 0;
	}
} //end function toggleSearch3()

function saveComment()
{

  // msc 09/25/03 ITS 108 validate UPC & Sound scan group codes
  if(!validate_UPC_SSG())
    return;

  //document.forms[0].editAction.value = "Save Release Header";
  document.forms[0].cmd.value = save;
  showWaitMsg(); // mc its 966 show please wait message on screen
  document.forms[0].submit();
} //end function saveComment()

var mfData = null;

function showDetailData( pField, pLayer, pTargetField )
{
	mfData = pField;
  if( mfData != null )
  {
  	layer = eval( document.all[ pLayer ] );
    if( ( typeof layer ) == "object" )
    {
    	field = eval( layer.all[ pTargetField ] );
      if( ( typeof field ) == "object" )
      {
      	field.value = mfData.value;
      }
      if( window.event != null )
      {
      	layer.style.pixelTop = window.event.y - 60;
      }
    }
  }
  toggle( pLayer, pTargetField );
} //end function showDetailData()

function saveDetailData( pLayer, pField, pAction )
{
	if( mfData != null )
  {
		layer = eval( document.all[ pLayer ] );
    if( ( typeof layer ) == "object" )
    {
    	field = eval( layer.all[ pField ] );
      if( ( typeof field ) == "object" )
      {
      	mfData.value = field.value;
        document.forms[0].editAction.value = pAction;
				parent.top.bottomFrame.location = "home?cmd=selection-editor";
      }
    }
  }
} //end function saveDetailData()

function getCalendar()
{
	layer = eval(document.all["calendar"]);
  if(count == 0)
  {
  	layer.style.visibility = "Visible";
    count = 1;
  }
  else
  {
    layer.style.visibility = "Hidden";
    count = 0;
  }
} //end function getCalendar()

function submitShow( pIsRelease )
{
	parent.top.bottomFrame.location = editor;
} //end function submitShow()

function submitGet( preleaseId )
{
	parent.top.bottomFrame.location = editor + "&selection-id=" + preleaseId;
} //end function submitGet()

function submitNewHeader()
{
  //document.forms[0].editAction.value = "Add Release Header";
  document.forms[0].cmd.value = editNew;
  showWaitMsg(); // mc its 966 show please wait message on screen
  document.forms[0].submit();
} //end function submitNewHeader()



///////////////////////  JR - PNR AFE ////////////////////////////

function trim(inputString) {
   // Removes leading and trailing spaces from the passed string. Also removes
   // consecutive spaces and replaces it with one space. If something besides
   // a string is passed in (null, custom object, etc.) then return the input.
   if (typeof inputString != "string") { return inputString; }
   var retValue = inputString;
   var ch = retValue.substring(0, 1);
   while (ch == " ") { // Check for spaces at the beginning of the string
      retValue = retValue.substring(1, retValue.length);
      ch = retValue.substring(0, 1);
   }
   ch = retValue.substring(retValue.length-1, retValue.length);
   while (ch == " ") { // Check for spaces at the end of the string
      retValue = retValue.substring(0, retValue.length-1);
      ch = retValue.substring(retValue.length-1, retValue.length);
   }
   while (retValue.indexOf("  ") != -1) { // Note that there are two spaces in the string - look for multiple spaces within the string
      retValue = retValue.substring(0, retValue.indexOf("  ")) + retValue.substring(retValue.indexOf("  ")+1, retValue.length); // Again, there are two spaces in each of the strings
   }
   return retValue; // Return the trimmed string back to the user
}

function validateRequiredFields(imagePrefix) {
	//alert('validateRequiredFields');

	// set up some variables for the numeric test
	var strProjectID = trim(document.forms[0].projectId.value);
	var strUPC = trim(document.forms[0].UPC.value);
	var strSoundScan = trim(document.forms[0].soundscan.value);
	var strUnits = ""; //trim(document.forms[0].numOfUnits.value);

	// take '-' out
	var re = new RegExp ('-', 'gi') ;
	if (strProjectID != '') {
		strProjectID = strProjectID.replace(re,'');
	}
	if (strUPC != '') {
		strUPC = strUPC.replace(re,'');
	}
	if (strSoundScan != '') {
		strSoundScan = strSoundScan.replace(re,'');
	}

	// take ' ' out
	re = new RegExp (' ', 'gi') ;
	if (strProjectID != '') {
		strProjectID = strProjectID.replace(re,'');
	}
	if (strUPC != '') {
		strUPC = strUPC.replace(re,'');
	}
	if (strSoundScan != '') {
		strSoundScan = strSoundScan.replace(re,'');
	}

	// take '/' out
	re = new RegExp ('/', 'gi') ;
	if (strProjectID != '') {
		strProjectID = strProjectID.replace(re,'');
	}
	if (strUPC != '') {
		strUPC = strUPC.replace(re,'');
	}
	if (strSoundScan != '') {
		strSoundScan = strSoundScan.replace(re,'');
	}

	if((trim(document.forms[0].artistLastName.value) == "")) {
		alert("'Artist Last Name' can not be empty. Please enter a value!");
         if (imagePrefix != '') {
			eval( 'mtb' + imagePrefix ).reset();
		}
   		document.forms[0].artistLastName.focus();
   		return false;
	} else if(trim(document.forms[0].title.value) == "") {
		alert("'Title' field can not be empty. Please enter a value!");
		if (imagePrefix != ''){
			eval( 'mtb' + imagePrefix ).reset();
		}
   		document.forms[0].title.focus();
   		return false;
	} else if(!document.forms[0].newBundle[0].checked && !document.forms[0].newBundle[1].checked) {
		alert("Please choose 'New Bundle' or 'Exact Duplicate of Physical Product'.");
		if (imagePrefix != ''){
			eval( 'mtb' + imagePrefix ).reset();
		}
   		document.forms[0].newBundle.focus();
   		return false;
	//} else if(document.forms[0].label.value <= 0)	{
   	//	alert("Please select 'environment', 'company', 'division', and 'label' before saving!");
	//	if (imagePrefix != ''){
	//		eval( 'mtb' + imagePrefix ).reset();
	//	}
   	//	return false;
	} else if(document.forms[0].productLine.value <= 0)	{
		alert("Please select 'Product Category', 'Release Type', 'Schedule Type' and 'Sub-Format' before saving!");
		if (imagePrefix != ''){
			eval( 'mtb' + imagePrefix ).reset();
		}
   		return false;
	} else if(document.forms[0].releaseType.value <= 0)	{
		alert("Please select 'Product Category', 'Release Type', 'Schedule Type' and 'Sub-Format' before saving!");
		if (imagePrefix != ''){
			eval( 'mtb' + imagePrefix ).reset();
		}
   		return false;
	} else if(document.forms[0].configuration.value <= 0) {
		alert("Please select 'Product Category', 'Release Type', 'Schedule Type' and 'Sub-Format' before saving!");
		if (imagePrefix != '') {
			eval( 'mtb' + imagePrefix ).reset();
		}
   		return false;
	} else if(document.forms[0].subConfiguration.value <= 0) {
		alert("Please select 'Product Category', 'Release Type', 'Schedule Type' and 'Sub-Format' before saving!");
		if (imagePrefix != '') {
			eval( 'mtb' + imagePrefix ).reset();
		}
   		return false;
	} else if(document.forms[0].status.value == "ACTIVE" && trim(document.forms[0].digitalDate.value) == "") {
		alert("'Digital Release Date' Date field can not be empty unless the selection Status is TBS. Please enter a Date or change Status!"); //ITS 480
		if (imagePrefix != '') {
			eval( 'mtb' + imagePrefix ).reset();
		}
		return false;
	} else if(document.forms[0].status.value == "CLOSED" && isOkToClose == false) {
		 // ITS 53079 11/27/2006
		alert("All UML/eCommerce tasks must have completion dates before a selection can be closed.");
		if (imagePrefix != '') {
			eval( 'mtb' + imagePrefix ).reset();
		}
   		return false;
	}
	// check that UPC, SoundScan Grp and PROJECT ID are numeric fields
	// but account for the possibility of '-', ' ', and/or '/'
/* JR - ITS 832 Canada
	else if( strProjectID != "" && isNaN(strProjectID) ) {
		alert("'Project ID' must be a numeric field.");
		if (imagePrefix != '') {
			eval( 'mtb' + imagePrefix ).reset();
		}
		//document.forms[0].projectId.focus();
		return false;
	}
*/
	else if( strUPC != "" && isNaN(strUPC) ) {
		alert("'UPC' must be a numeric field.");
		if (imagePrefix != '') {
			eval( 'mtb' + imagePrefix ).reset();
		}
		document.forms[0].UPC.focus();
		return false;
	}

/*
	else if( strSoundScan != "" && isNaN(strSoundScan) ) {
		alert("'Sales Grouping Cd' must be a numeric field.");
		if (imagePrefix != '') {
			eval( 'mtb' + imagePrefix ).reset();
		}
		document.forms[0].soundscan.focus();
		return false;
	}
*/
	// JR - ITS 486 check to see that # of Units is greater than 0
	//else if( strUnits == "" || strUnits < 1 ) {
	//	alert("'# of Units' must be a numeric field greater than zero.");
	//	if (imagePrefix != '') {
	//		eval( 'mtb' + imagePrefix ).reset();
	//	}
		//document.forms[0].numOfUnits.focus();
	//	return false;
	//}
	//alert('end of validateRequiredFields');
	return true;

	// JR - ITS 396
	/*
	if((!document.forms[0].pdIndicator.checked) && (Number(document.forms[0].projectId.value) < 1) ) {
		alert("'P&D' field should be checked AND/OR 'Project ID' value should be specified!");
		if (imagePrefix != '') {
			eval( 'mtb' + imagePrefix ).reset();
		}
   		document.forms[0].projectId.focus();
   		return false;
	} else
	// JR - ITS 394
	if(trim(document.forms[0].titleId.value) == "") {
		alert("TitleID field can not be empty. Please enter a value!");
		if (imagePrefix != '') {
			eval( 'mtb' + imagePrefix ).reset();
		}
		document.forms[0].isFocus.value = "true";
		//document.forms[0].titleId.disabled = false;
		document.forms[0].titleId.focus();
		return false;
	} else if(document.forms[0].selectionNo.value == "" && document.forms[0].UPC.value == "") {
		// before asking for Prfix/ID or UPC chekc if Status is Active and if so check to see
		// if street date is not empty. If it is empty ask to be entered.
		if(document.forms[0].status.value == "ACTIVE" && trim(document.forms[0].streetDate.value) == "") {
			alert("'Street/Ship' Date field can not be empty unless the selection Status is TBS. Please enter a Date or change Status!"); //ITS 480
			if (imagePrefix != '') {
  				eval( 'mtb' + imagePrefix ).reset();
  			}
		} else {
   			alert("Either 'Prefix/Local Product #' or 'UPC'  must be entered.");
  			if (imagePrefix != '') {
  				eval( 'mtb' + imagePrefix ).reset();
  			}
   		}
   		return false;
	*/
}

function submitGenerateLPN(imagePrefix){

	//alert('submitGenerateLPN');
	isSubConfigParent(document.forms[0].subConfiguration.selectedIndex);

	var theCC = new String(document.forms[0].configcode.value)

	if (!validateRequiredFields(imagePrefix)) {
		return;
	}
	// make sure the release is NOT promotional
	else if (document.forms[0].releaseType.value == "PR") {
		alert("Promotional Releases are not allowed to generate a Local Product Number.");
		if (imagePrefix != '') {
			eval( 'mtb' + imagePrefix ).reset();
		}
	}
	// make sure that release is NOT an international release
	else if (document.forms[0].intlFlag.checked) {
		alert("International product is not allowed to generate a Local Product Number.");
		if (imagePrefix != '') {
			eval( 'mtb' + imagePrefix ).reset();
		}
	}
	// check for LPNG required fields as well
	//else if(trim(document.forms[0].opercompany.value) == "" || document.forms[0].opercompany.value == "-1") {
	//	alert("'Operating Company' is required to generate a Local Product Number.  Please enter and submit again.");
	//	if (imagePrefix != '') {
	//		eval( 'mtb' + imagePrefix ).reset();
	//	}
	//	document.forms[0].opercompany.focus();
	//}
	// check for LPNG required fields as well
	//else if(trim(document.forms[0].superlabel.value) == "") {
	//	alert("'Super Label' is required to generate a Local Product Number.  Please enter and submit again.");
	//	if (imagePrefix != '') {
	//		eval( 'mtb' + imagePrefix ).reset();
	//	}
	//	document.forms[0].superlabel.focus();
	//}
	// check for LPNG required fields as well
	//else if(trim(document.forms[0].sublabel.value) == "") {
	//	alert("'Sub-Label' is required to generate a Local Product Number.  Please enter and submit again.");
	//	if (imagePrefix != '') {
	//		eval( 'mtb' + imagePrefix ).reset();
	//	}
	//	document.forms[0].sublabel.focus();
	//}
	// check for LPNG required fields as well
	else if(trim(document.forms[0].configcode.value) == "" || document.forms[0].configcode.value == "-1") {
		alert("'Configuration Code' is required to generate a Local Product Number.  Please enter and submit again.");
		if (imagePrefix != '') {
			eval( 'mtb' + imagePrefix ).reset();
		}
		document.forms[0].configcode.focus();
	}
	// check for LPNG required fields as well
	else if(trim(document.forms[0].projectId.value) == "") {
		alert("'Project ID' is required to generate a Local Product Number.  Please enter and submit again.");
		if (imagePrefix != '') {
			eval( 'mtb' + imagePrefix ).reset();
		}
		//document.forms[0].projectId.focus();
	}
	// check for LPNG required fields as well
	else if(trim(document.forms[0].UPC.value) == "") {
		alert("'UPC' is required to generate a Local Product Number.  Please enter and submit again.");
		if (imagePrefix != '') {
			eval( 'mtb' + imagePrefix ).reset();
		}
		document.forms[0].UPC.focus();
	}
	// check for LPNG required fields as well
	//else if(trim(document.forms[0].soundscan.value) == "") {
	//	alert("'SoundScan Group' is required to generate a Local Product Number.  Please enter and submit again.");
	//	if (imagePrefix != '') {
	//		eval( 'mtb' + imagePrefix ).reset();
	//	}
	//	document.forms[0].configcode.focus();
	//}
	// check to see if titleID is empty or it starts with MLST
	// if it's a single however, skip this
	/*
        else if (!theCC.indexOf("S") == 0 &&
			(trim(document.forms[0].titleId.value)=="" ||
	         document.forms[0].titleId.value.indexOf("MLST") > -1)) {

		var response = confirm("Does the 'TitleID' for this release match the 'Local Product #'?");
		if (response){
			if( (trim(document.forms[0].prefix.value) == "" || document.forms[0].prefix.value == "-1") &&
				     (trim(document.forms[0].selectionNo.value) != "") &&
				(document.forms[0].selectionNo.value.indexOf("MLST") > -1 || document.forms[0].selectionNo.value.indexOf("mlst") > -1)) {
				var response2 = confirm("This selection already has a product number.  Generating a Local Product Number will override the existing product number.  Do you want to continue?");
				if (response2){
					document.forms[0].titleId.value = ""; // JR - ITS 385
					document.forms[0].generateSelection.value = "LPNG"; // JR PNR AFE
					document.forms[0].cmd.value = save;
					document.forms[0].submit();
				}
				if (imagePrefix != '') {
					eval( 'mtb' + imagePrefix ).reset();
				}
			} else {
				document.forms[0].titleId.value = ""; // JR - ITS 385
				document.forms[0].generateSelection.value = "LPNG"; // JR PNR AFE
				document.forms[0].cmd.value = save;
				document.forms[0].submit();
			}
		} else {
			alert("Enter a value for 'TitleID'.");
		}
		if (imagePrefix != '') {
			eval( 'mtb' + imagePrefix ).reset();
		}
	}
        */
	// check for an existing APNG.  If exists, warn user
	else if( (trim(document.forms[0].prefix.value) == "" || document.forms[0].prefix.value == "-1") &&
		     (trim(document.forms[0].selectionNo.value) != "" &&
		(document.forms[0].selectionNo.value.indexOf("B") > -1 || document.forms[0].selectionNo.value.indexOf("b") > -1))) {
		var response = confirm("This selection already has a product number.  Generating a Local Product Number will override the existing product number.  Do you want to continue?");
		if (response){
			document.forms[0].generateSelection.value = "LPNG"; // JR PNR AFE
			document.forms[0].cmd.value = save;
                        showWaitMsg(); // mc its 966 show please wait message on screen
			document.forms[0].submit();
		}
		if (imagePrefix != '') {
			eval( 'mtb' + imagePrefix ).reset();
		}
	} else {
			document.forms[0].generateSelection.value = "LPNG"; // JR PNR AFE
			document.forms[0].cmd.value = save;
                        showWaitMsg(); // mc its 966 show please wait message on screen
			document.forms[0].submit();
	}

	// JR - Per Conversation with Marla on 01/15/03
	/*
	//verified
	else if( (newOrCopy || (document.forms[0].selectionNo.value != oldSelectionNumber)) &&
	            document.forms[0].releaseType.value == "CO" &&
	            document.forms[0].hidTitleId.value == "" &&
	            isParent == "true") {
		//if( document.forms[0].selectionNo.value != "") {
			str= "Does the 'TitleID' for this release match the 'Local Product #'"
		//} else {
		//	str= "Does the 'TitleID' for this release match the 'Local Product #' generated from the entered UPC code ? "
		//}
		if(confirm(str  )) {
			//document.forms[0].titleId.disabled = false;
			//if(document.forms[0].prefix.value == "" || document.forms[0].prefix.value == "-1") {
			//	document.forms[0].titleId.value = document.forms[0].selectionNo.value;
			//} else {
			//	document.forms[0].titleId.value = document.forms[0].prefix.value + document.forms[0].selectionNo.value;
			//}
			document.forms[0].isFocus.value = "false";
			document.forms[0].hidTitleId.value = "filledconf";
			document.forms[0].generateSelection.value = "LPNG"; // JR PNR AFE
			document.forms[0].cmd.value = save;
			document.forms[0].submit();
		} else {
			if (imagePrefix != '') {
				eval( 'mtb' + imagePrefix ).reset();
			}
			alert("Enter a value for 'TitleID' .");
			document.forms[0].hidTitleId.value = "filled";
			//document.forms[0].titleId.value = "";
			//document.forms[0].titleId.disabled = false;
			document.forms[0].isFocus.value = "true";
			document.forms[0].titleId.focus();
		}
	}
	//verified
	else if(!newOrCopy && document.forms[0].releaseType.value == "CO" &&
			document.forms[0].hidTitleId.value == "" &&
			isParent == "true") {
  		//document.forms[0].titleId.disabled = false;
		//if(document.forms[0].selectionNo.value == "") {
			len = document.forms[0].UPC.value.length -1;
			//if(document.forms[0].prefix.value == "" || document.forms[0].prefix.value == "-1") {
			//	document.forms[0].titleId.value = document.forms[0].UPC.value.substring(1,len);
			//} else {
			//	document.forms[0].titleId.value = document.forms[0].prefix.value + document.forms[0].UPC.value.substring(1,len);
			//}
  		//}
  		document.forms[0].isFocus.value = "false";
  		document.forms[0].hidTitleId.value = "filledconf";
  		document.forms[0].generateSelection.value = "LPNG"; // JR PNR AFE
  		document.forms[0].cmd.value = save;
		document.forms[0].submit();
	}
	//verified
	else if(!newOrCopy &&  document.forms[0].releaseType.value == "CO" &&
			document.forms[0].hidTitleId.value == "" &&
			isParent != "true") {
		//document.forms[0].titleId.disabled = false;
		if(document.forms[0].selectionNo.value == "") {
			len = document.forms[0].UPC.value.length -1;
			document.forms[0].selectionNo.value = document.forms[0].UPC.value.substring(1,len)
		}
		if(document.forms[0].prefix.value == "" ||
			document.forms[0].prefix.value =="-1" ||
			document.forms[0].prefix.value == "0") {
			//document.forms[0].titleId.value = document.forms[0].selectionNo.value;
		} else {
   			//document.forms[0].titleId.value = document.forms[0].prefix.value + document.forms[0].selectionNo.value;
		}
		document.forms[0].isFocus.value = "false";
		document.forms[0].generateSelection.value = "LPNG"; // JR PNR AFE
		document.forms[0].cmd.value = save;
		document.forms[0].submit();
	}
	//verified
	else if(newOrCopy && document.forms[0].releaseType.value == "CO" && document.forms[0].titleId != "") {
  		//document.forms[0].titleId.disabled = false;
  		document.forms[0].generateSelection.value = "LPNG"; // JR PNR AFE
		document.forms[0].cmd.value = save;
		document.forms[0].submit();
	}
	//verified
	else if(document.forms[0].releaseType.value == "PR" &&
			document.forms[0].selectionNo != "" &&
			document.forms[0].hidTitleId.value == "" ) {
		//document.forms[0].titleId.disabled = false;
		//if(document.forms[0].prefix.value == "" || document.forms[0].prefix.value == "-1") {
		//	document.forms[0].titleId.value = document.forms[0].selectionNo.value;
		//} else {
		//	document.forms[0].titleId.value = document.forms[0].prefix.value + document.forms[0].selectionNo.value;
		//}
		document.forms[0].isFocus.value = "false";
		document.forms[0].generateSelection.value = "LPNG"; // JR PNR AFE
		document.forms[0].cmd.value = save;
		document.forms[0].submit();
	} else {
 		document.forms[0].generateSelection.value = "LPNG"; // JR PNR AFE
   		document.forms[0].cmd.value = save;
		document.forms[0].submit();
	}
	*/
}

function submitTempLPN(imagePrefix){

	isSubConfigParent(document.forms[0].subConfiguration.selectedIndex);
	var singles = (document.forms[0].configcode.value).substring(0,1);

	if (!validateRequiredFields(imagePrefix)) {
		//alert('validation failed');
		return;
	}
	// JR - ITS 366 if a selection no already exists don't generate a temp
	if (trim(document.forms[0].selectionNo.value) != "") {
		alert("Can not generate a Temp Product Number for elements that already have a 'Local Product #'");
			if (imagePrefix != '') {
				eval( 'mtb' + imagePrefix ).reset();
			}
	}
        // msc 2/11/03 ITS 477 Promos populate Title ID with pref and LPN
        else if ( document.forms[0].releaseType.value == "PR" )
        {
          document.forms[0].generateSelection.value = "TPNG";
	  document.forms[0].cmd.value = save;
          showWaitMsg(); // mc its 966 show please wait message on screen
	  document.forms[0].submit();
        }
        // jo 2/25/03 ITS 504 Commercial Singles - no messages
        else if ( (document.forms[0].releaseType.value == "CO") && (singles=="S") )
        {
          document.forms[0].generateSelection.value = "TPNG";
	  document.forms[0].cmd.value = save;
          showWaitMsg(); // mc its 966 show please wait message on screen
	  document.forms[0].submit();
        }
	// do the same checks as with the LPNG
	// check to see if titleID is empty or it starts with MLST
	/*
        else if (trim(document.forms[0].titleId.value)=="" ||
	         document.forms[0].titleId.value.indexOf("MLST") > -1) {
		var response = confirm("Does the 'TitleID' for this release match the 'Local Product #'?");
		if (response){
			document.forms[0].generateSelection.value = "TPNG"; // JR PNR AFE
			document.forms[0].cmd.value = save;
			document.forms[0].submit();
		} else {
			alert("Enter a value for 'TitleID'.");
		}
		if (imagePrefix != '') {
			eval( 'mtb' + imagePrefix ).reset();
		}
	}
        */
        else {
			document.forms[0].generateSelection.value = "TPNG"; // JR PNR AFE
			document.forms[0].cmd.value = save;
                        showWaitMsg(); // mc its 966 show please wait message on screen
			document.forms[0].submit();
	}

	// JR - Per Conversation with Marla on 01/15/03
	/*
	//verified
	else if( (newOrCopy || (document.forms[0].selectionNo.value != oldSelectionNumber)) &&
	            document.forms[0].releaseType.value == "CO" &&
	            document.forms[0].hidTitleId.value == "" &&
	            isParent == "true") {
		//if( document.forms[0].selectionNo.value != "") {
			str= "Does the 'TitleID' for this release match the 'Local Product #'?"
		//} else {
		//	str= "Does the 'TitleID' for this release match the 'Local Product #' generated from the entered UPC code ? "
		//}
		if(confirm(str  )) {
			document.forms[0].titleId.disabled = false;
			if(document.forms[0].prefix.value == "" || document.forms[0].prefix.value == "-1") {
				document.forms[0].titleId.value = document.forms[0].selectionNo.value;
			} else {
				document.forms[0].titleId.value = document.forms[0].prefix.value + document.forms[0].selectionNo.value;
			}
			document.forms[0].isFocus.value = "false";
			document.forms[0].hidTitleId.value = "filledconf";
			document.forms[0].generateSelection.value = "TPNG"; // JR PNR AFE
			document.forms[0].cmd.value = save;
			document.forms[0].submit();
		} else {
			if (imagePrefix != '') {
				eval( 'mtb' + imagePrefix ).reset();
			}
			alert("Enter a value for TitleID .");
			document.forms[0].hidTitleId.value = "filled";
			//document.forms[0].titleId.value = "";
			//document.forms[0].titleId.disabled = false;
			document.forms[0].isFocus.value = "true";
			document.forms[0].titleId.focus();
		}
	}
	//verified
	else if(!newOrCopy && document.forms[0].releaseType.value == "CO" &&
			document.forms[0].hidTitleId.value == "" &&
			isParent == "true") {
  		//document.forms[0].titleId.disabled = false;
		//if(document.forms[0].selectionNo.value == "") {
		//	len = document.forms[0].UPC.value.length -1;
		//	if(document.forms[0].prefix.value == "" || document.forms[0].prefix.value == "-1") {
		//		document.forms[0].titleId.value = document.forms[0].UPC.value.substring(1,len);
		//	} else {
		//		document.forms[0].titleId.value = document.forms[0].prefix.value + document.forms[0].UPC.value.substring(1,len);
		//	}
  		//}
  		document.forms[0].isFocus.value = "false";
  		document.forms[0].hidTitleId.value = "filledconf";
  		document.forms[0].generateSelection.value = "TPNG"; // JR PNR AFE
  		document.forms[0].cmd.value = save;
		document.forms[0].submit();
	}
	//verified
	else if(!newOrCopy &&  document.forms[0].releaseType.value == "CO" &&
			document.forms[0].hidTitleId.value == "" &&
			isParent != "true") {
		//document.forms[0].titleId.disabled = false;
		if(document.forms[0].selectionNo.value == "") {
			len = document.forms[0].UPC.value.length -1;
			document.forms[0].selectionNo.value = document.forms[0].UPC.value.substring(1,len)
		}
		if(document.forms[0].prefix.value == "" ||
			document.forms[0].prefix.value =="-1" ||
			document.forms[0].prefix.value == "0") {
			//document.forms[0].titleId.value = document.forms[0].selectionNo.value;
		} else {
   			//document.forms[0].titleId.value = document.forms[0].prefix.value + document.forms[0].selectionNo.value;
		}
		document.forms[0].isFocus.value = "false";
		document.forms[0].generateSelection.value = "TPNG"; // JR PNR AFE
		document.forms[0].cmd.value = save;
		document.forms[0].submit();
	}
	//verified
	else if(newOrCopy && document.forms[0].releaseType.value == "CO" && trim(document.forms[0].titleId) != "") {
  		//document.forms[0].titleId.disabled = false;
  		document.forms[0].generateSelection.value = "TPNG"; // JR PNR AFE
		document.forms[0].cmd.value = save;
		document.forms[0].submit();
	}
	//verified
	else if(document.forms[0].releaseType.value == "PR" &&
			trim(document.forms[0].selectionNo) != "" &&
			document.forms[0].hidTitleId.value == "" ) {
		//document.forms[0].titleId.disabled = false;
		//if(document.forms[0].prefix.value == "" || document.forms[0].prefix.value == "-1") {
		//	document.forms[0].titleId.value = document.forms[0].selectionNo.value;
		//} else {
		//	document.forms[0].titleId.value = document.forms[0].prefix.value + document.forms[0].selectionNo.value;
		//}
		document.forms[0].isFocus.value = "false";
		document.forms[0].generateSelection.value = "TPNG"; // JR PNR AFE
		document.forms[0].cmd.value = save;
		document.forms[0].submit();
	} else {
 		//alert('submitTempLPN submitting...');
 		document.forms[0].generateSelection.value = "TPNG"; // JR PNR AFE
   		document.forms[0].cmd.value = save;
		document.forms[0].submit();
	}
	*/
}

///////////////////////  JR - PNR AFE ////////////////////////////

function submitSave(imagePrefix)
{

  // msc 09/25/03 ITS 108 validate UPC & Sound scan group codes
  if(!validate_UPC_SSG())
  {
     // reset button
     if (imagePrefix != '')
     {
	eval( 'mtb' + imagePrefix ).reset();
     }
    return;
  }

	//alert('validateRequiredFields');

	// set up some variables for the numeric test
	var strProjectID = trim(document.forms[0].projectId.value);
	var strUPC = trim(document.forms[0].UPC.value);
	var strSoundScan = trim(document.forms[0].soundscan.value);
	var strUnits = ""; //trim(document.forms[0].numOfUnits.value);

	// take '-' out
	var re = new RegExp ('-', 'gi') ;
	if (strProjectID != '') {
		strProjectID = strProjectID.replace(re,'');
	}
	if (strUPC != '') {
		strUPC = strUPC.replace(re,'');
	}
	if (strSoundScan != '') {
		strSoundScan = strSoundScan.replace(re,'');
	}

	// take ' ' out
	re = new RegExp (' ', 'gi') ;
	if (strProjectID != '') {
		strProjectID = strProjectID.replace(re,'');
	}
	if (strUPC != '') {
		strUPC = strUPC.replace(re,'');
	}
	if (strSoundScan != '') {
		strSoundScan = strSoundScan.replace(re,'');
	}

	// take '/' out
	re = new RegExp ('/', 'gi') ;
	if (strProjectID != '') {
      	strProjectID = strProjectID.replace(re,'');
	}
	if (strUPC != '') {
		strUPC = strUPC.replace(re,'');
	}
	if (strSoundScan != '') {
		strSoundScan = strSoundScan.replace(re,'');
	}


 // msc
// alert("<<< label value " + document.forms[0].label.value + "\n index " + document.forms[0].label.selectedIndex);
// alert("<<< division value " + document.forms[0].division.value + "\n index " + document.forms[0].division.selectedIndex);

  isSubConfigParent(document.forms[0].subConfiguration.selectedIndex);

// JR - ITS 396
/*
  if((!document.forms[0].pdIndicator.checked) && (Number(document.forms[0].projectId.value) < 1) )
  {
	alert("'P&D' field should be checked AND/OR 'Project ID' value should be specified!");

    if (imagePrefix != '')
	{
			eval( 'mtb' + imagePrefix ).reset();
	}
   	document.forms[0].projectId.focus();
  }
  else
 */
        /*
	if(trim(document.forms[0].titleId.value) == "" && document.forms[0].releaseType.value != "PR") {
		alert("'Title ID' field can not be empty. Please enter a value!");
		if (imagePrefix != '') {
			eval( 'mtb' + imagePrefix ).reset();
		}
		document.forms[0].isFocus.value = "true";
		//document.forms[0].titleId.disabled = false;
		document.forms[0].titleId.focus();
	} else
        */
        if((trim(document.forms[0].artistLastName.value) == "")) {
		alert("'Artist Last Name' can not be empty. Please enter a value!");
         if (imagePrefix != '')
		{
			eval( 'mtb' + imagePrefix ).reset();
		}
   	document.forms[0].artistLastName.focus();
	}
	else if(trim(document.forms[0].title.value) == "")
	{
		alert("'Title' field can not be empty. Please enter a value!");
   if (imagePrefix != '')
		{
			eval( 'mtb' + imagePrefix ).reset();
		}
   	document.forms[0].title.focus();
	}
	//else if(document.forms[0].label.value <= 0)
	//{
   	//alert("Please select 'environment', 'company', 'division', and 'label' before saving!");
             //if (imagePrefix != '')
		//{
		//	eval( 'mtb' + imagePrefix ).reset();
		//}
	//}
        else if(!document.forms[0].newBundle[0].checked && !document.forms[0].newBundle[1].checked)
        {
              alert("Please choose 'New Bundle' or 'Exact Duplicate of Physical Product'.");
              if (imagePrefix != ''){
                      eval( 'mtb' + imagePrefix ).reset();
              }
              //document.forms[0].newBundle[0].focus();
	}
	else if(document.forms[0].releasingFamily.value <= 0)
	{
   	   alert("Please select releasing family before saving!");
           if (imagePrefix != '')
           {
           	eval( 'mtb' + imagePrefix ).reset();
           }
	}
	else if(document.forms[0].productLine.value <= 0)
	{
		alert("Please select 'Product Category', 'Release Type', 'Schedule Type' and 'Sub-Format' before saving!");
    if (imagePrefix != '')
		{
			eval( 'mtb' + imagePrefix ).reset();
		}
	}
	else if(document.forms[0].releaseType.value <= 0)
	{
		alert("Please select 'Product Category', 'Release Type', 'Schedule Type' and 'Sub-Format' before saving!");
    if (imagePrefix != '')
		{
			eval( 'mtb' + imagePrefix ).reset();
		}
	}
	else if(document.forms[0].configuration.value <= 0)
	{
		alert("Please select 'Product Category', 'Release Type', 'Schedule Type' and 'Sub-Format' before saving!");
    if (imagePrefix != '')
		{
			eval( 'mtb' + imagePrefix ).reset();
		}
	}
	else if(document.forms[0].subConfiguration.value <= 0)
	{
		alert("Please select 'Product Category', 'Release Type', 'Schedule Type' and 'Sub-Format' before saving!");
    if (imagePrefix != '')
		{
			eval( 'mtb' + imagePrefix ).reset();
		}
	}
	// check that UPC, SoundScan Grp and PROJECT ID are numeric fields
	// but account for the possibility of '-', ' ', and/or '/'
/* JR - ITS 832 Canada
	else if( strProjectID != "" && isNaN(strProjectID) ) {
		alert("'Project ID' must be a numeric field.");
		if (imagePrefix != '') {
			eval( 'mtb' + imagePrefix ).reset();
		}
		//document.forms[0].projectId.focus();
	}
*/
	else if( strUPC != "" && isNaN(strUPC) ) {
		alert("'UPC' must be a numeric field.");
		if (imagePrefix != '') {
			eval( 'mtb' + imagePrefix ).reset();
		}
		document.forms[0].UPC.focus();
	}
/*
	else if( strSoundScan != "" && isNaN(strSoundScan) ) {
		alert("'Sales Grouping Cd' must be a numeric field.");
		if (imagePrefix != '') {
			eval( 'mtb' + imagePrefix ).reset();
		}
		document.forms[0].soundscan.focus();
	}
*/
	// JR - ITS 486 check to see that # of Units is greater than 0
	//else if( strUnits == "" || strUnits < 1 ) {
	//	alert("'# of Units' must be a numeric field greater than zero.");
	//	if (imagePrefix != '') {
	//		eval( 'mtb' + imagePrefix ).reset();
	//	}
		//document.forms[0].numOfUnits.focus();
		//return false;
	//}
	//else if(trim(document.forms[0].selectionNo.value) == "") // && document.forms[0].UPC.value == "")
	//{

    // before asking for Prfix/ID or UPC chekc if Status is Active and if so check to see
    // if street date is not empty. If it is empty ask to be entered.
    //if(document.forms[0].status.value == "ACTIVE" && document.forms[0].streetDate.value == "")
    //{
      //alert("Street/Ship date field can not be empty. Please enter a value!");
      //if (imagePrefix != '')
  		//{
  		//	eval( 'mtb' + imagePrefix ).reset();
  		//}
     //}
     //else
     //{
		//
  		//if(confirm("Either 'Prefix/Local Prod No' or 'UPC'  must be entered. Do you want Milestone to generate a temporary ID  number ? "))
  		//{
  		//	document.forms[0].generateSelection.value = "Yes";
        //document.forms[0].cmd.value = save;
        //document.forms[0].submit();
     	//}
  		//else
  		//{
  		//	if (imagePrefix != '')
  		//	{
  		//		eval( 'mtb' + imagePrefix ).reset();
  		//	}
   		//}
   		//
   		//alert("Either 'Prefix/Local Prod No' or 'UPC'  must be entered.");
   		//alert("'Prefix/Local Prod No' must be entered.");
  		//	if (imagePrefix != '')
  		//	{
  		//		eval( 'mtb' + imagePrefix ).reset();
  		//	}

    //}
	//}
	else if(document.forms[0].status.value == "ACTIVE" && trim(document.forms[0].digitalDate.value) == "")
	{
		alert("'Digital Release Date' Date field can not be empty unless the selection Status is TBS. Please enter a Date or change Status!"); //ITS 480
    if (imagePrefix != '')
		{
			eval( 'mtb' + imagePrefix ).reset();
		}
	}
	else if(document.forms[0].status.value == "CLOSED" && isOkToClose == false)
	{
		// ITS 53079 11/27/2006 
		alert("All UML/eCommerce tasks must have completion dates before a selection can be closed.");
	    if (imagePrefix != '')
		{
			eval( 'mtb' + imagePrefix ).reset();
		}
	}
        else if(trim(document.forms[0].imprint.value) == "") { // JP 6/30/03 -- added imprint
		alert("'Imprint' field can not be empty. Please enter a value!");
		if (imagePrefix != ''){
			eval( 'mtb' + imagePrefix ).reset();
		}
   		document.forms[0].imprint.focus();
	}


	// JR - Commented out per Marla's conversation on 01/15/03 for PNR AFE
	/*
			//verified
			else if( (newOrCopy || (document.forms[0].selectionNo.value != oldSelectionNumber)) && document.forms[0].releaseType.value == "CO" && document.forms[0].hidTitleId.value == "" && isParent == "true")
				{
			  //if( document.forms[0].selectionNo.value != "")
				//	{
						str= "Does the 'TitleID' for this release match the 'Local Product #'?"
				//	}
			  //else
				//	{
				//		str= "Does the 'TitleID' for this release match the 'Local Product #' generated from the entered UPC code ? "
				//	}
					if(confirm(str  ))
					{
						document.forms[0].titleId.disabled = false;
						if(document.forms[0].prefix.value == "" || document.forms[0].prefix.value == "-1")
						{
							document.forms[0].titleId.value = document.forms[0].selectionNo.value;
						}
						else
						{
							document.forms[0].titleId.value = document.forms[0].prefix.value + document.forms[0].selectionNo.value;
						}
						document.forms[0].isFocus.value = "false";
						document.forms[0].hidTitleId.value = "filledconf";
						document.forms[0].cmd.value = save;
						document.forms[0].submit();
					}
					else
					{
						if (imagePrefix != '')
						{
							eval( 'mtb' + imagePrefix ).reset();
						}
						alert("Enter a value for TitleID.");
						document.forms[0].hidTitleId.value = "filled";
						document.forms[0].titleId.value = "";
						//document.forms[0].titleId.disabled = false;
						document.forms[0].isFocus.value = "true";
						document.forms[0].titleId.focus();
					}
				}
			//verified
				else if(!newOrCopy && document.forms[0].releaseType.value == "CO" && document.forms[0].hidTitleId.value == "" && isParent == "true")
				{
				//document.forms[0].titleId.disabled = false;
			  //if(document.forms[0].selectionNo.value == "")
				//	{
				//		len = document.forms[0].UPC.value.length -1;
			  // if(document.forms[0].prefix.value == "" || document.forms[0].prefix.value == "-1")
				//		{
			  //    document.forms[0].titleId.value = document.forms[0].UPC.value.substring(1,len);
			  //  }
				//		else
				//		{
			  //    document.forms[0].titleId.value = document.forms[0].prefix.value + document.forms[0].UPC.value.substring(1,len);
				//		}
				//}
				document.forms[0].isFocus.value = "false";
				document.forms[0].hidTitleId.value = "filledconf";
				document.forms[0].cmd.value = save;
			  document.forms[0].submit();
				}
			//verified
				else if(!newOrCopy &&  document.forms[0].releaseType.value == "CO" && document.forms[0].hidTitleId.value == "" && isParent != "true")
			{
			  //document.forms[0].titleId.disabled = false;
			  if(document.forms[0].selectionNo.value == "")
					{
						len = document.forms[0].UPC.value.length -1;
			    document.forms[0].selectionNo.value = document.forms[0].UPC.value.substring(1,len)
					}
					if(document.forms[0].prefix.value == "" || document.forms[0].prefix.value =="-1" || document.forms[0].prefix.value == "0")
					{
						//document.forms[0].titleId.value = document.forms[0].selectionNo.value;
			  }
					else
					{
			 		//document.forms[0].titleId.value = document.forms[0].prefix.value + document.forms[0].selectionNo.value;
			  }
			  document.forms[0].isFocus.value = "false";
			  document.forms[0].cmd.value = save;
			  document.forms[0].submit();
				}
			//verified
				else if(newOrCopy && document.forms[0].releaseType.value == "CO" && trim(document.forms[0].titleId) != "")
				{
				//document.forms[0].titleId.disabled = false;
			  document.forms[0].cmd.value = save;
			  document.forms[0].submit();
				}
			//verified
				else if(document.forms[0].releaseType.value == "PR" && trim(document.forms[0].selectionNo) != "" && document.forms[0].hidTitleId.value == "" )
				{
			  //document.forms[0].titleId.disabled = false;
			  //if(document.forms[0].prefix.value == "" || document.forms[0].prefix.value == "-1")
				//	{
			  //  document.forms[0].titleId.value = document.forms[0].selectionNo.value;
			  //}
				//	else
				//	{
			    //document.forms[0].titleId.value = document.forms[0].prefix.value + document.forms[0].selectionNo.value;
			  //}
					document.forms[0].isFocus.value = "false";
			  document.forms[0].cmd.value = save;
			  document.forms[0].submit();
				}

	else if(trim(document.forms[0].selectionNo.value) == "")
	{
		alert("'Local Product #' field can not be empty. Please enter a value!");
    if (imagePrefix != '')
		{
			eval( 'mtb' + imagePrefix ).reset();
		}
    document.forms[0].selectionNo.focus();
	}
        */
	else
	{
          // JP 6/19/03
          document.all.selectionNo.disabled = false;
          document.all.prefix.disabled = false;

   	document.forms[0].cmd.value = save;
    showWaitMsg(); // mc its 966 show please wait message on screen
    document.forms[0].submit();
	}
} //end function submitSave()

function submitCopy( pImagePrefix )
{
	parent.top.bottomFrame.location = copyCommand;
} //end function submitCopy()

// JP Digital AFE
function submitCopyDigital()

{
  document.forms[0].cmd.value = copyDigitalCommand;
  showWaitMsg(); // mc its 966 show please wait message on screen
  document.forms[0].submit();
} //end function submitCopyDigital()

function submitDelete()
{
  if(confirm("You are about to delete a record, Confirm?"))
  {
          showWaitMsg(); // mc its 966 show please wait message on screen
	  parent.top.bottomFrame.location = deleteCommand;
  }
  else
  {
    eval( 'mtb' + 'Delete' ).reset();
  }
} //end function submitDelete()

function submitSearch()
{

document.forms[0].cmd.value = search;

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
showWaitMsg(); // mc its 966 show please wait message on screen
document.forms[0].submit();

} //end function submitSearch()

function submitTemplateSourceSearch()
{
	parent.top.bottomFrame.location = editor;
} //end function submitTemplateSourceSearch()

function submitReleaseSourceSearch()
{
  document.all.StreetDateSearchSource.value = convertDate( document.all.StreetDateSearchSource.value );
	parent.top.bottomFrame.location = editor;
} //end function submitReleaseSourceSearch()

function submitSearch4()
{
  submitSearchMaster(4);
} //end function submitSearch4()

function submitSearchMaster( index )
{
  document.forms[0].cmd.value = search;
  showWaitMsg(); // mc its 966 show please wait message on screen
  document.forms[0].submit();
} //end function submitSearchMaster()

function getTemplateTasks()
{
	parent.top.bottomFrame.location = editor;
} //end function getTemplateTasks()

function submitList( pType )
{
  document.forms[0].OrderBy.value = pType;
  document.forms[0].cmd.value = sort;
  showWaitMsg(); // mc its 966 show please wait message on screen
  document.forms[0].submit();
} //end function submitList()

function submitDetailList( pType )
{
  document.forms[0].detailOrder.value = pType;
	parent.top.bottomFrame.location = editor;
} //end function submitDetailList()


function getReleaseTasks()
{
	parent.top.bottomFrame.location = editor;
} //end function getReleaseTasks()

function submitRecalc( pType, imagePrefix )
{
  if( pType )
  {
    if( confirm( "This will permanently recalculate All Release Due Dates. Confirm?" ) )
    {
			parent.top.bottomFrame.location = editor;
    }
    else
    {
      eval( 'mtb' + imagePrefix ).reset();
    }
  }
  else
  {
    if( confirm( "This will permanently recalculate Release Due Dates. Confirm?" ) )
    {
			parent.top.bottomFrame.location = editor;
    }
    else
    {
      eval( 'mtb' + imagePrefix ).reset();
    }
  }
} //end function submitRecalc()

function submitClearDate( imagePrefix )
{
  if( confirm( "This will permanently clear all Release Due Dates and Weeks To Release fields. Confirm?" ) )
  {
		parent.top.bottomFrame.location = editor;
  }
  else
  {
    eval( 'mtb' + imagePrefix ).reset();
  }
} //end function submitClearDate()

function submitAssign()
{
	parent.top.bottomFrame.location = editor;
} //end function submitAssign()

function validateWks2Rel( pField )
{
  var str = pField.value;
  if( str == "" )
  {
    return true;
  }
  var valid = true;
  if( isNaN( str ) )
  {
    if( str.indexOf( " " ) > -1 )
    {
      s1 = str.substring( 0, str.indexOf( " " ) ).toUpperCase();
      s2 = str.substring( str.indexOf( " " ) + 1 );
      if( s1 == "M" || s1 == "T" || s1 == "W" || s1 == "TH" || s1 == "F"  || s1 == "S" || s1 == "SU" || s1 == "D" )
      {
        if( isNaN( s2 ) || s2 <= 0 )
        {
          valid = false;
        }
      }
      else
      {
        valid = false;
      }
    }
    else
    {
      valid = false;
    }
  }
  else
  {
    valid = true;
  }
  if( !valid )
  {
    alert( pField.value + " is not a valid Value. Please retry again!");
    pField.focus();
  }
} //end function validateWks2Rel()

function submitUnassign( pTaskId )
{
  if( confirm( "You are about to delete an assigned Task. Confirm?" ) )
  {
    document.forms[0].deleteIdx.value = pTaskId;
		parent.top.bottomFrame.location = editor;
  }
} //end function submitUnassign()

function clickCurrentFilter(obj)
{
  document.forms[0].CurrentFilter.value = obj[obj.selectedIndex].value;
	parent.top.bottomFrame.location = editor;
} //end function clickCurrentFilter()

function MM_swapImgRestore()
{ //v3.0
  var i, x, a = document.MM_sr;
  for( i = 0; a && i < a.length && (x = a[i]) && x.oSrc; i++)
  {
    x.src = x.oSrc;
  }
} //end function MM_swapImgRestore()

function MM_preloadImages()
{ //v3.0
  var d = document;
  if(d.images)
  {
    if(!d.MM_p)
    {
      d.MM_p = new Array();
    }
    var i, j = d.MM_p.length, a = MM_preloadImages.arguments;
    for(i = 0; i < a.length; i++)
    {
      if (a[i].indexOf("#")!=0)
      {
        d.MM_p[j] = new Image;
        d.MM_p[j++].src = a[i];
      }
    }
  }
} //end function MM_preloadImages()

function MM_findObj(n, d)
{ //v3.0
  var p,i,x;
  if(!d)
		d = document;
  if((p = n.indexOf("?")) > 0 && parent.frames.length)
  {
   	d = parent.frames[n.substring(p+1)].document;
    n = n.substring(0,p);
  }
  if(!(x=d[n])&&d.all)
		x=d.all[n];
  for (i=0;!x&&i<d.forms.length;i++)
  {
    x=d.forms[i][n];
  }
  for(i=0;!x&&d.layers&&i<d.layers.length;i++)
  {
    x=MM_findObj(n,d.layers[i].document);
  }
  return x;
}//end function MM_findObj()

function MM_swapImage()
{ //v3.0
  var i,j=0,x,a=MM_swapImage.arguments;
  document.MM_sr=new Array;
  for(i=0;i<(a.length-2);i+=3)
  {
    if ((x=MM_findObj(a[i]))!=null)
    {
      document.MM_sr[j++]=x;
      if(!x.oSrc) x.oSrc=x.src;
      x.src=a[i+2];
    }
  }
} //end function MM_swapImage()

function clearCombo(obj)
{
	var i;
  for(i=obj.options.length; i>=0; i--)
		obj.options[i] = null;
   	obj.selectedIndex = -1;
} //end function clearCombo()

function populateCompany(obj, index)
{
   var j = 0;

   if (index > -1)
   {
     for(var i=0; i<a[index].length; i=i+2)
     {
       document.forms[0].company.options[j] = new Option(a[index][i+1], a[index][i]);
       j++;
     }
     document.forms[0].company.options[0].selected=true;
   }
} //end function populateCompany()


function populateDivision(obj, index)
{
   var j = 0;
   if (b[index])
   {
     for(var i=0; i<b[index].length; i=i+2)
     {
       document.forms[0].division.options[j] = new Option(b[index][i+1], b[index][i]);
       j++;
     }
     document.forms[0].division.options[0].selected=true;
   }
} //end function populateDivision()

function populateLabel(obj, index)
{
	var currentLabelValue = document.forms[0].label.value;
	clearCombo(document.forms[0].label);
	var j = 0;

  if(c.length >= index)
	{
		if(c[index])
		{
			for(var i=0; i<c[index].length; i=i+2)
			{
				document.forms[0].label.options[j] = new Option(c[index][i+1], c[index][i]);
        j++;
      }
    }
		else
		{
			document.forms[0].label.options[j] = new Option('[none available]',0);
    }
  }
	else
	{
		document.forms[0].label.options[j] = new Option('[none available]',0);
  }

  var selfnd = false;  // msc
  for (var k=0; k<document.forms[0].label.options.length;k++)
  {
    if (document.forms[0].label.options[k].value == currentLabelValue)
    {
      document.forms[0].label.options[k].selected = true;
      selfnd = true;  // msc
    }
  }
  if(!selfnd)  // msc
      document.forms[0].label.options[0].selected=true;  // msc
} //end function populateLabel()

function compareText (option1, option2) {
  if (option1.text.toUpperCase() == "ALL")
    return -1;
  else if (option2.text.toUpperCase() == "ALL")
    return 1;
  else
    return option1.text.toUpperCase() < option2.text.toUpperCase() ? -1 : option1.text.toUpperCase()  > option2.text.toUpperCase() ? 1 : 0;
}

/* msc afe 2003 now incldued in include-jscript-check-shortcut.js
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
*/

function clickFamilySearch(obj)
{

  if(document.forms[0].EnvironmentSearch)
    clearCombo(document.forms[0].EnvironmentSearch);

  if(document.forms[0].CompanySearch)
    clearCombo(document.forms[0].CompanySearch);

  if(document.forms[0].LabelSearch)
    clearCombo(document.forms[0].LabelSearch);

  if(document.forms[0].EnvironmentSearch)
  {
    populateEnvironmentSearch(document.forms[0].EnvironmentSearch, obj[obj.selectedIndex].value);
//alert("in clickFamily");
    //clickEnvironmentSearch(document.forms[0].EnvironmentSearch);
  }

  if(document.forms[0].CompanySearch)
  {
    populateCompanyFromFamily(document.forms[0].CompanySearch, obj[obj.selectedIndex].value);
    //clickCompanySearch(document.forms[0].CompanySearch);
  }

  if(document.forms[0].LabelSearch)
  {
    populateLabelFromFamily(document.forms[0].LabelSearch,obj[obj.selectedIndex].value);
  }

  return true;
} //end function clickFamily()

function clickEnvironmentSearch(obj)
{
  if (obj.selectedIndex == 0)
    return clickFamilySearch(document.forms[0].FamilySearch);

//alert("clickEnvironment");
  if(document.forms[0].CompanySearch) {
//alert("company field found");
    clearCombo(document.forms[0].CompanySearch);
    populateCompanySearch(document.forms[0].CompanySearch, obj[obj.selectedIndex].value);
    //clickCompanySearch(document.forms[0].CompanySearch);
  } //else {

    if((document.forms[0].LabelSearch)) {
      //alert("step1");
      clearCombo(document.forms[0].LabelSearch);
      populateLabelFromEnvironment(document.forms[0].LabelSearch,obj[obj.selectedIndex].value)
    }
  //}

  return true;
} //end function clickCompany()


function clickCompanySearch(obj)
{
  if (obj.selectedIndex == 0)
    return clickEnvironmentSearch(document.forms[0].EnvironmentSearch);

//alert("clickCompany1");
  if(document.forms[0].LabelSearch)
  {
   // alert("clickCompany1a");
    clearCombo(document.forms[0].LabelSearch);
    populateLabelSearch(document.forms[0].LabelSearch, obj[obj.selectedIndex].value);
  }
//alert("clickCompany2");
  return true;
} //end function clickCompany()


function populateEnvironmentSearch(obj, index)
{
   clearCombo(document.forms[0].EnvironmentSearch);

   var j = 0;
   for(var i=0; i < familyArray[index].length; i=i+2){
       document.forms[0].EnvironmentSearch.options[j] = new Option(familyArray[index][i+1], familyArray[index][i]);
       j++;
   }
   sortSelect(document.forms[0].EnvironmentSearch,compareText);
   document.forms[0].EnvironmentSearch.options[0].selected=true;

} //end function populateEnvironment()


function populateCompanySearch(obj, index)
{

   var j = 0;

/*
  // MSC AFE 2003 - if all selected
  objEnv = document.forms[0].EnvironmentSearch.options;
  //alert(" Env value " + objEnv[objEnv.selectedIndex].value);
  // if selected environment is equal to all, show all companies

  if(objEnv[objEnv.selectedIndex].value == "0")
  {
    //alert(" options company length " + saveCompanyOptions.length);
    if(saveCompanyOptions.length > 0)
    {
      for(var i=0; i < saveCompanyOptions.length; i++)
      {
         document.forms[0].CompanySearch.options[i] = new Option(saveCompanyOptions[i].text, saveCompanyOptions[i].value);
      }
    }
    else  // empty company drop down
    {
      	document.forms[0].CompanySearch.options[0] = new Option('All',0);
    }
    document.forms[0].CompanySearch.options[0].selected=true;
    return true;
  }
  // MSC AFE 2003 - IF ALL SELECTED
*/
   clearCombo(document.forms[0].CompanySearch);
   for(var i=0; i < environmentArray[index].length; i=i+2){
	document.forms[0].CompanySearch.options[j] = new Option(environmentArray[index][i+1], environmentArray[index][i]);
       j++;
   }
   sortSelect(document.forms[0].CompanySearch,compareText);
   document.forms[0].CompanySearch.options[0].selected=true;

} //end function populateCompany()

function populateLabelSearch(obj, index)
{
//alert("populateLabel - company index:[" + index + "]");
  var j = 0;

/*
  // MSC AFE 2003 - if all selected
  objEnv = document.forms[0].EnvironmentSearch.options;
  objCo = document.forms[0].CompanySearch.options;
  // if selected environment and company are equal to all, show all labels
  if(objEnv[objEnv.selectedIndex].value == "0" && objCo[objCo.selectedIndex].value == "0")
  {
    //alert(" options Label length " + saveLabelOptions.length);
    if(saveLabelOptions.length > 0)
    {
      for(var i=0; i < saveLabelOptions.length; i++)
      {
         document.forms[0].LabelSearch.options[i] = new Option(saveLabelOptions[i].text, saveLabelOptions[i].value);
      }
    }
    else  // empty label drop down
    {
      	document.forms[0].LabelSearch.options[0] = new Option('All',0);
    }
    document.forms[0].LabelSearch.options[0].selected=true;
    return true;
  }
  // MSC AFE 2003 - IF ALL SELECTED
*/

  clearCombo(document.forms[0].LabelSearch);

  if(companyArray.length >= index){
    if(companyArray[index]){
      for(var i=0; i < companyArray[index].length; i=i+2)
      {
        document.forms[0].LabelSearch.options[j] = new Option(companyArray[index][i+1], companyArray[index][i]);
        j++;
      }
    } else {
      	document.forms[0].LabelSearch.options[j] = new Option('All',0);
      }
    } else {
      	document.forms[0].LabelSearch.options[j] = new Option('All',0);
  }
  sortSelect(document.forms[0].LabelSearch,compareText);
  document.forms[0].LabelSearch.options[0].selected=true;
} //end function populateLabel()


///////////////////////////////////////////////////
function populateCompanyFromFamily(obj, index){
var j = 1;
 //For every entry in the family array
//alert("index:[" + index + "]");
 clearCombo(document.forms[0].CompanySearch);
 document.forms[0].CompanySearch.options[0] = new Option('All',0);
 for(var i=2; i < familyArray[index].length; i=i+2){
    var environmentId = familyArray[index][i];
    //alert("environmentId:[" + environmentId + "]");
    //for every environment entry, get its company
    if (environmentId ==0)
    {
      //alert("environmentid is zero so skipping");
      //document.forms[0].company.options[0] = new Option('[none available]',0);
      document.forms[0].CompanySearch.options[0] = new Option(environmentArray[environmentId][1], environmentArray[environmentId][0]);
      j++; // msc 1/03/02, its 294, remove multiple (Alls) in drop down list box
    } else {
      //alert("about to loop --- environmentId:[" + environmentId + "]");
      for(var m=2; m < environmentArray[environmentId].length; m=m+2)
      {
        var companyId = environmentArray[environmentId][m];
        if(  companyId != 0 ) // msc 1/03/02, its 294 , remove multiple alls in list box
        {
          //alert("add companyid:[" + companyId + "]");
          document.forms[0].CompanySearch.options[j] = new Option(environmentArray[environmentId][m+1], environmentArray[environmentId][m]);
          j++;
        }
      }
    }
 }
 sortSelect(document.forms[0].CompanySearch,compareText);

}

///////////////////////////////////////////////////
function populateLabelFromEnvironment(obj, index){
//alert("inside populateLabelFromEnvironment");
var j = 1;
 //For every entry in the environment array
//alert("index:[" + index + "]");
 clearCombo(document.forms[0].LabelSearch);
 document.forms[0].LabelSearch.options[0] = new Option('All',0);
 for(var i=2; i < environmentArray[index].length; i=i+2){
	var companyId = environmentArray[index][i];
	//alert("companyId:[" + companyId + "]");
	//for every company entry, get its label
	//if (companyId==0){
		//alert("companyid is zero so skipping");
	//} else {
		//alert("about to loop --- companyId:[" + companyId + "]");
		for(var m=2; m < companyArray[companyId].length; m=m+2){
			var labelId = companyArray[companyId][m];
			//alert("add labelid:[" + labelId + "]");
                        document.forms[0].LabelSearch.options[j] = new Option(companyArray[companyId][m+1], companyArray[companyId][m]);
			j++;
		}
	//}
 }
 sortSelect(document.forms[0].LabelSearch,compareText);
}
///////////////////////////////////////////////////
function populateLabelFromFamily(obj, index){
var j = 0;
var jk = 1;
 //For every entry in the family array
//alert("index:[" + index + "]");
 clearCombo(document.forms[0].LabelSearch);
 document.forms[0].LabelSearch.options[0] = new Option('All',0);
 for(var i=2; i < familyArray[index].length; i=i+2){
	//alert("<--- looping on families --->");
	var environmentId = familyArray[index][i];
	//alert("environmentId:[" + environmentId + "]");
	//for every environment entry, get its company
	//if (environmentId ==0){
		//alert("environmentid is zero so skipping");
	//	document.forms[0].LabelSearch.options[0] = new Option('All',0);
	//} else {
		//alert("<--- about to loop --- environmentId:[" + environmentId + "] --->");
		for(var m=2; m < environmentArray[environmentId].length; m=m+2){
			var companyId = environmentArray[environmentId][m];
			//alert("add companyid:[" + companyId + "]");
                        //if (companyId ==0){
				//alert("companyId is zero so skipping");
			//	document.forms[0].LabelSearch.options[0] = new Option('All',0);
			//} else {
				for(var n=2; n < companyArray[companyId].length; n=n+2){
					var labelId = companyArray[companyId][n];
					//alert("add labelid:[" + labelId + "]");
                                        document.forms[0].LabelSearch.options[jk] = new Option(companyArray[companyId][n+1], companyArray[companyId][n]);
					jk++;
				}
			//}
		}
	//}
   }
 sortSelect(document.forms[0].LabelSearch,compareText);
//alert("end of populateLabelFromFamily");
}
///////////////////////////////////////////////////


//fixed bug related to the sell code
function populateSell(obj, index)
{
	var indx =index.toUpperCase();
 	var k = 0;
 	
 	for( var j=0; j < dpc.length; j++)
	{
       if( dpc[j][1].toUpperCase().substr(0,indx.length) == indx || !(index.length > 0) )
	   {
			obj.options[k] = new Option(dpc[j][1], dpc[j][1]);
  			k++;
  		}
	}

	// if not found display message
	if(k == 0)
		alert("Not a valid Digital Price Code value ");
	else // if found select option
		obj.options[0].selected=true;
	
	
} // end function populateSell()


// JR - ITS 327 need to remember these values for the selection dropdowns
var cValue = "";
var dValue = "";
var lValue = "";

function clickEnvironment(obj)
{
  cValue = document.forms[0].company.value; // JR - ITS 327
  clearCombo(document.forms[0].company);
  //clearCombo(document.forms[0].division); // JR - ITS 327
  //clearCombo(document.forms[0].label); // JR - ITS 327
  populateCompany(document.forms[0].company, obj[obj.selectedIndex].value);

  // JR - ITS 327
  for (var i =0; i < document.forms[0].company.length; i++)
  {
	if (document.forms[0].company[i].value == cValue)
		document.forms[0].company[i].selected = true;
  }

  clickCompany(document.forms[0].company);
  return true;
} //end function clickEnvironment()

function clickCompany(obj)
{
  dValue = document.forms[0].division.value; // JR - ITS 327
  clearCombo(document.forms[0].division);
  //clearCombo(document.forms[0].label); // JR - ITS 327
  populateDivision(document.forms[0].division, obj[obj.selectedIndex].value);

  //traverse the Divisions to reset the old value
  // JR - ITS 327
  for (var i =0; i < document.forms[0].division.options.length; i++)
  {
	if (document.forms[0].division.options[i].value == dValue)
		document.forms[0].division.options[i].selected = true;
  }

  clickDivision(document.forms[0].division);
  return true;
} //end function clickCompany()

function clickDivision(obj)
{
  lValue = document.forms[0].label.value; // JR - ITS 327
  if (obj.selectedIndex > -1)
    populateLabel(document.forms[0].label, obj[obj.selectedIndex].value);

  // JR - ITS 327
  for (var i =0; i < document.forms[0].label.options.length; i++)
  {
	if (document.forms[0].label.options[i].value == lValue)
		document.forms[0].label.options[i].selected = true;
  }

  return true;
} //end function clickDivision()

function clickSell(obj)
{
  clearCombo(document.forms[0].priceCode);
  populateSell(document.forms[0].priceCode, obj.value);
  document.forms[0].priceCode.focus();
  return true;
} //end function clickSell()

function buildSubConfigs(num)
{
  document.forms[0].subConfiguration.selectedIndex = 0;
  document.forms[0].subConfiguration.length = 0;
  document.forms[0].subConfiguration.disabled = true;

  selectedConfig = 1;
  for (j = 1; j < configsProdType.length; j++)
  {
    selectedConfig = j;

    s1 = configsProdType[selectedConfig];
    s2 = s1.substring(0, s1.indexOf(","));
    s3 = s1.substring(s1.indexOf(",")+1);
    s3 = s3.substring(0, s3.indexOf(","));

    if (document.forms[0].configuration.options[num].value == s3)
      break;
  }

  disabledDD = true;
  if(num > 0)
  {
    currIndex = 1;
    for(i=1; i<configs[selectedConfig].length; i++)
    {
      s1 = configs[selectedConfig][i];
      s2 = s1.substring(0, s1.indexOf(","));
      s3 = s1.substring(s1.indexOf(",")+1);
      s4 = s3.substring(s3.indexOf(",")+1);
      s4 = s4.substring(s4.indexOf(",")+1);
      s3 = s3.substring(0, s3.indexOf(","));

      //if (document.all.isDigital.value == s4 || s4 == 2)
      //{
        document.forms[0].subConfiguration.options[currIndex] = new Option(s3,s2);
        currIndex++;
        disabledDD = false;
      //}
    }
    document.forms[0].subConfiguration.length = currIndex; //configs[num].length;

    if (!disabledDD)
      document.forms[0].subConfiguration.options[0].selected=true;

    isSubConfigParent(1);
  }

  document.forms[0].subConfiguration.disabled = disabledDD;
} //end function buildSubConfigs()

// JR - ITS#70
// JP Digital AFE
function buildSearchSubConfigs(num)
{
  document.forms[0].SubconfigSearch.selectedIndex = 0;
  document.forms[0].SubconfigSearch.length = 0;
  document.forms[0].SubconfigSearch.disabled = true;

  prodTypeVal = 2;
  if (document.forms[0].ProdType[0].checked)
    prodTypeVal = 0;
  else if (document.forms[0].ProdType[1].checked)
    prodTypeVal = 1;

  selectedConfig = 0;
  for (j = 1; j < configsProdType.length; j++)
  {
    s1 = configsProdType[j];
    s2 = s1.substring(0, s1.indexOf(","));
    s3 = s1.substring(s1.indexOf(",")+1);
    s3 = s3.substring(0, s3.indexOf(","));

    if (document.forms[0].ConfigSearch.options[num].value == s3)
    {
      selectedConfig = j;
      break;
    }
  }

  disabledDD = true;
  if(selectedConfig > 0)
  {
    document.forms[0].SubconfigSearch.options[0] = new Option('All','');

    currIndex = 1;
    for(i=1; i<configs[selectedConfig].length; i++)
    {
      s1 = configs[selectedConfig][i];
      s2 = s1.substring(0, s1.indexOf(","));
      //s3 = s1.substring(s1.indexOf(",")+1);
      s3 = s1.substring(s1.indexOf(",")+1);
      s4 = s3.substring(s3.indexOf(",")+1);
      s4 = s4.substring(s4.indexOf(",")+1);
      s3 = s3.substring(0, s3.indexOf(","));

      //if (prodTypeVal == s4 || prodTypeVal == 2)
      //{
        document.forms[0].SubconfigSearch.options[currIndex] = new Option(s3,s2);
        currIndex++;
        disabledDD = false;
      //}
    }
    document.forms[0].SubconfigSearch.length = currIndex; //configs[num].length;
    //document.forms[0].SubconfigSearch.options[1].selected=true;
    //isSubConfigParent(1);
  }
  document.forms[0].SubconfigSearch.disabled = disabledDD;

} //end function buildSearchSubConfigs()

function buildSearchConfigs(x)
{
  document.forms[0].ConfigSearch.selectedIndex = 0;
  document.forms[0].ConfigSearch.length = 0;
  document.forms[0].ConfigSearch.disabled = true;

  currIndex = 1;
  disabledDD = true;

  prodTypeVal = 2;
  if (document.forms[0].ProdType[0].checked)
    prodTypeVal = 0;
  else if (document.forms[0].ProdType[1].checked)
    prodTypeVal = 1;

  document.forms[0].ConfigSearch.options[0] = new Option('All','');

  for(i=1; i<configsProdType.length; i++)
  {
    s1 = configsProdType[i];
    configName = s1.substring(0, s1.indexOf(","));
    s3 = s1.substring(s1.indexOf(",")+1);
    // JP 9/30/03
    s4 = s3.substring(s3.indexOf(",")+1);
    configType = s4.substring(0,s4.indexOf(","));
    configAbbreviation = s3.substring(0, s3.indexOf(","));

    if (prodTypeVal == configType || prodTypeVal == "2" || configType == "2")
    {
      document.forms[0].ConfigSearch.options[currIndex] = new Option(configName,configAbbreviation);
      currIndex++;
      disabledDD = false;
    }
  }
  document.forms[0].ConfigSearch.length = currIndex;
  document.forms[0].ConfigSearch.disabled = disabledDD;

  buildSearchSubConfigs(0);

  /*
  if (prodTypeVal == 0)
    document.all.formatLabel.innerHTML  = "Format";
  else if (prodTypeVal == 1)
    document.all.formatLabel.innerHTML  = "Schedule Type";
  else
    document.all.formatLabel.innerHTML  = "Format/Schedule Type";
  */

} //end function buildSearchConfigs()


// JR - ITS#70

function isSubConfigParent(num)
{
  if(num != 0)
  {
    if(document.forms[0].configuration.selectedIndex > 0)
    {
      configNum = document.forms[0].configuration.selectedIndex;

      //isSubParent =  configs[configNum][num];
      //isSubParent = isSubParent.substring(isSubParent.indexOf(",")+1);
      //isSubParent = isSubParent.substring(isSubParent.indexOf(",")+1);
      //isParent = isSubParent;
      isParent = false;  // MSC AFE 2003 temporary fix for now
    }
  }
 } //end function isSubConfigParent()


function togglePackaging()
{
	layer = eval(document.all["PackagingLayer"]);
  if(PackagingCount == 0)
	{
		layer.style.visibility = "Visible";
    layer.all["PackagingHelper"].focus();
    PackagingCount = 1;
	}
	else
	{
		layer.style.visibility = "Hidden";
    PackagingCount = 0;
	}
} //end function togglePackaging

function validateNumericFields(theForm)
{
	var checkOK = "0123456789";
	var checkStr = ""; //thedocument.forms[0].numOfUnits.value;
	var allValid = true;
	var allNum = "";
	for (i = 0;  i < checkStr.length;  i++)
	{
  	ch = checkStr.charAt(i);
  	for (j = 0;  j < checkOK.length;  j++)
    if (ch == checkOK.charAt(j))
    	break;
  	if (j == checkOK.length)
  	{
    	allValid = false;
    	break;
  	}
  	if (ch != ",")
    	allNum += ch;
	}
 	return allValid;
} //end function validateNumericFields

function saveComments(isNew)
{
	layer.style.visibility = "Hidden";
  holdReasonCount = 0;
  commentsCount = 0;
  PackagingCount = 0;

  if (!isNew)
	{
    //save should only occur on hitting the main save button
    submitSave('');
    return true;
  }
  // msc Item 1983 /////////////////////////////////////////////////////
  else {
    // toggle image of comments/holdreason for new/copy
    // msc 01/14/04 ITS 901 added code check for the existance of the holdreasonImage
    if(document.all.holdReasonImage)
    {
      if( document.all.holdReason.value.length > 0 )
  	document.all.holdReasonImage.src = imageDir + "afile.gif";
      else
	document.all.holdReasonImage.src = imageDir + "file.gif";
    }
    if(document.all.commentsImage)
    {
      if( document.all.comments.value.length > 0 )
        document.all.commentsImage.src = imageDir + "afile.gif";
      else
        document.all.commentsImage.src = imageDir + "file.gif";
    }
    // MC ITS 1047
    window.event.cancelBubble = true;  // mc stop wait message
    return false
  }
  // msc Item 1983 /////////////////////////////////////////////////////


} //end function saveComments()

// ITS 977 - added code to remove spaces from date
function validateDate(str,pName)
{
  pName.value = pName.value.replace(/\s/g, ""); //strip spaces from field value
  str = str.replace(/\s/g, ""); //strip spaces from input string
  var sYear = "";
  var j = 0;
  if(str.length > 0)
	{
		errorMsg = "";
   	for(i=0; i<str.length; i++)
		{
			s = str.substring(i,i+1);
      if(isNaN(s) && s != "/" && s != "-" && s !=".")
				errorMsg = "Character is not allowed. Please retry again!";
      if( s == "/" || s == "-" || s == ".")
			{
				j++;
        if( j == 2 )
				{
					sYear = str.substring(i+1);
				}
			}
   }
  	if( sYear.length != 2 && sYear.length != 4)
		{
			alert("Year value is invalid. Please retry again!");
      pName.focus();
      return false;
   	}
   	if( isNaN(s) )
		{
     	alert("Year must be numeric value. Please retry again!");
     	pName.focus();
    	return false;
   	}
   	if(str.indexOf("/") > -1)
		{
			s = str;
      while(s.indexOf("/") > -1)
			{
				s1 = s.substring(0,s.indexOf("/"));
        s = s.substring(s.indexOf("/")+1);
        if(s1.length==0 || s1.length>2)
				{
					errorMsg = "Date number formatting error. Please try again";
					break;
				}
      	if(s.length==3)
				{
					errorMsg = "Date number formatting error. Please try again";
					break;
				}
			}
   	}
  	else if(str.indexOf(".") > -1)
		{
			s = str;
      while(s.indexOf(".") > -1)
			{
				s1 = s.substring(0,s.indexOf("."));
        s = s.substring(s.indexOf(".")+1);
        if(s1.length==0 || s1.length>2)
				{
					errorMsg = "Date number formatting error. Please try again";
					break;
				}
				if(s.length==3)
				{
					errorMsg = "Date number formatting error. Please try again";
					break;
				}
    	}
   	}
 		else  if(str.indexOf("-") > -1)
		{
			s = str;
      while(s.indexOf("-") > -1)
			{
				s1 = s.substring(0,s.indexOf("-"));
        s = s.substring(s.indexOf("-")+1);
        if(s1.length==0 || s1.length>2)
				{
					errorMsg = "Date number formatting error. Please try again";
					break;
				}
				if(s.length==3)
				{
					errorMsg = "Date number formatting error. Please try again";
					break;
				}
			}
		}
		if(errorMsg.length == 0)
		{
			pDate = str.replace( /-/g, "/" );
     	pDate = str.replace( /\./g, "/" );
      myMonthArray = new Array(31,28,31,30,31,30,31,31,30,31,30,31)
      arrayOfStrings = pDate.split("/")
      pos = arrayOfStrings[0]
      pos = pos -1;
      if(pos <0 || pos > 11)
			{
				alert( "Field: " + pName.name + " must be a valid month" );
        if(pName.name=="streetDate")
					document.forms[0].streetDate.focus();
        if(pName.name=="impactdate")
					document.forms[0].impactdate.focus();
        if(pName.name=="internationalDate")
					document.forms[0].internationalDate.focus();
        return false;
			}
      if(pos == 1)
			{
				dateTimeVal = new Date(pDate);
        year = dateTimeVal.getFullYear();
        if(year < 1970)
				{
					year = year + 100}
          if( (year % 4 ==0 ) && (year % 100 !=0))
						myMonthArray[1]=29;
          if( (year % 4 ==0 ) && (year % 100 ==0)&& (year % 400 ==0))
						myMonthArray[1]=29;
        }
      	if((arrayOfStrings[1] > myMonthArray[pos]) || (arrayOfStrings[1]< 1) )
				{
					alert( "Field: " + pName.name + "  must be a valid day of month" );
          if(pName.name=="streetDate")
						document.forms[0].streetDate.focus();
          if(pName.name=="impactdate")
						document.forms[0].impactdate.focus();
          if(pName.name=="internationalDate")
						document.forms[0].internationalDate.focus();
          return false;
    		}
			}
   		else
   			errorMsg="Data entered is not a valid Date. Please retry again";
   	if(errorMsg.length > 0)
		{
			alert(errorMsg);
     	pName.focus();
   	}
	}
} //end function validateDate()

function toggleTerritory()
{
	layer = eval(document.all["TerritoryLayer"]);
	  if(TerritoryCount == 0)
	{
		layer.style.visibility = "Visible";
 		   layer.all["TerritoryHelper"].focus();
 		   TerritoryCount = 1;
	}
	else
	{
		layer.style.visibility = "Hidden";
  		  TerritoryCount = 0;
	}
} //end function toggleTerritory


function submitPagging( pForward )
{
  if( pForward )
  {
  	parent.top.bottomFrame.location = next + "&notepadType=" + notepad + "&lastCommand=" + cmd;
  }
  else
  {
  	parent.top.bottomFrame.location = previous + "&notepadType=" + notepad + "&lastCommand=" + cmd;
  }
}//end function submitPagging()

function sendPfmBom(x)
{
  //alert(x);
  // JR -  ITS 702
  if (document.forms[0].button_sendPfm)
    document.forms[0].button_sendPfm.disabled = true;

  if (document.forms[0].button_sendBom)
  document.forms[0].button_sendBom.disabled = true;

  if (document.forms[0].button_sendPfmBom)
  document.forms[0].button_sendPfmBom.disabled = true;

  if (document.forms[0].button_cancel)
    document.forms[0].button_cancel.disabled = true;

  document.forms[0].cmd.value = x;
  showWaitMsg(); // mc its 966 show please wait message on screen
  document.forms[0].submit();
} //end function sendPfmBom()

function filterScheduleType(isNew)
{
  //if (document.forms[0].configuration.selectedIndex == 0 || !isNew)
  //{
    currentConfig = document.forms[0].configuration.options[document.forms[0].configuration.selectedIndex].value

    document.forms[0].configuration.selectedIndex = 0;
    document.forms[0].configuration.length = 0;
    document.forms[0].subConfiguration.disabled = true;

    currIndex = 1;
    for (j = 1; j < configsProdType.length; j++)
    {
      s1 = configsProdType[j];
      s2 = s1.substring(0, s1.indexOf(",")); // Name
      s3 = s1.substring(s1.indexOf(",")+1);
      s4 = s3.substring(s3.indexOf(",")+1);
      s5 = s4.substring(s4.indexOf(",")+1);  // isNewBundle
      s4 = s4.substring(0, s4.indexOf(",")); // isDigital
      s3 = s3.substring(0, s3.indexOf(",")); // Value

      newBundle = 0;

      if (document.forms[0].newBundle[0].checked)
        newBundle = 1;

      if ((s4 == 1 && s5 == newBundle))
      {
        document.forms[0].configuration.options[currIndex] = new Option(s2,s3);

        if (currentConfig == s3)
          document.forms[0].configuration.selectedIndex = currIndex

        currIndex++;
        document.forms[0].configuration.length = currIndex;
      }
    }

    if (currIndex > 1)
      buildSubConfigs(document.forms[0].configuration.selectedIndex);
  //}
}

function validate_UPC_SSG()
{

  // msc 09/25/03 ITS 108 validate UPC & Sound scan group codes
  if(document.all.UPC != null && !checkLengthUPC( document.all.UPC.value, "UPC", 12, 14, "UPC", true, false ))
  {
     document.all.UPC.focus();
     return false;
  }
  if(document.all.soundscan != null && !checkLengthUPC( document.all.soundscan.value, "Sales Grouping Code", 12, 14, "SSG", true, false ))
  {
     document.all.soundscan.focus();
     return false;
  }

  return true;
}

// MC 01/07/08 
//This function is called when the releaseType dropdown is changed
function releaseTypeChanged()
{
	if (document.forms[0].releaseType.value == "PR")
	{
	    //moving to Promo so 
	    // MC 01/07/08 clear the digital price code field
	    document.all.priceCode.selectedIndex = -1;
	    for(var x=0; x < document.all.priceCode.options.length; x++)
	    	document.all.priceCode.options[x].selected = false;
        document.all.priceCode.options[0].selected = true;
	} 
}

