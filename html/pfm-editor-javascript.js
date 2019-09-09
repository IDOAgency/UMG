function hidePrintButtons( pLayer, pFirstFocus )
{
  if (document.all[ pLayer ] == null)
	return;

  layer = eval( document.all[ pLayer ][0] )
  // Check to see if the layer is found (it must be at least an object)
  if ( ( typeof layer ) != "object" )
  {
    return;
  }
  layer.style.visibility = "hidden";

  layer = eval( document.all[ pLayer ][1] )
  // Check to see if the layer is found (it must be at least an object)
  if ( ( typeof layer ) != "object" )
  {
    return;
  }
  layer.style.visibility = "hidden";
}  // End function hidePrintButtons


function checkField( pField )
{
  var bReturn = true;
  switch( pField.name )
  {
	case "comments":
      bReturn = bReturn && checkLength( pField.value, "comments", 0, 255 );  // msc itm 2020 was 80
  }
  return bReturn;
} // end function checkField

function checkPage()
{
  if( !checkField(document.all.comments))
  {
	return false;
  }
  return true;
} // end function checkPage()

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
 showWaitMsg();  // mc its 966 show please wait message
 document.forms[0].submit();

} //end function submitSearch()

// jo - ITS#1101 6/21/2005 - Add validation to ensure that selected fields have
// been completed before saving
//ITS 1131 - Consolidated code for Physical and Digital PFMs
function verifyFinalField(fieldName, textAlertStr, changeOrSaveText){

    var fieldStr = "(document.forms[0]." + fieldName + ".value=='') || (document.forms[0]." + fieldName + ".value=='-1')";
    if (eval(fieldStr)){
       if (changeOrSaveText=="change"){
              alert("The " + textAlertStr + " value must be entered in order to change a PFM to Final status");
              if (document.forms[0].printOption[0]){
                      document.forms[0].printOption[0].checked = true;
              }
       } else {
             alert("The " + textAlertStr + " value must be entered in order to save a PFM in Final status");
       }
       return true;
    }
    return false;
}

//this function caters for both the Physical and Digital PFMs
//where "type" indicates if its Physical or Digital
function checkPFMFields(textToDisplay, isDigital){
      //check fields
      if (verifyFinalField("company_code", "Company Code", textToDisplay)) {
           return false;
      }
       if (verifyFinalField("po_merge_code", "PO Merge Code", textToDisplay)) {
           return false;
      }
       if (verifyFinalField("music_line", "Music Line", textToDisplay)) {
           return false;
      }
       if (verifyFinalField("repertoire_owner", "Rep Owner", textToDisplay)) {
           return false;
      }
       if (verifyFinalField("return_code", "Return/Scrap Code", textToDisplay)) {
           return false;
      }
       if (verifyFinalField("music_type", "Music Type", textToDisplay)) {
           return false;
      }
       if (verifyFinalField("compilation_code", "Compilation/Soundtrack", textToDisplay)) {
           return false;
      }
       if (verifyFinalField("config_code", "Config Code", textToDisplay)) {
           return false;
      }
       if (verifyFinalField("price_point", "Price Point", textToDisplay)) {
           return false;
      }
       if (!isDigital && verifyFinalField("price_code", "Price Code", textToDisplay)) {
           return false;
      }

      return true;
}

function submitSave( imagePrefix, isDigital )
{
  //Validate the following fields are not blank/-1 and that the PFM is in Final status before continuing
  //Its 'FInal' if either there are two radio buttons and the second ('Final') is true OR
  //if there is one radio button with the value 'Final'
  if (document.forms[0].printOption != null)  {
  if (
        (document.forms[0].printOption[1] && document.forms[0].printOption[1].checked == true) ||
        (document.forms[0].printOption.value=='Final' ) ){//final

         //Validate that the form fields have been completed before changing to Final
         if (!checkPFMFields("save", isDigital)){
               return false;
         }
  }

  if( checkPage() )
  {
      // JP 12/10/02
      document.forms[0].ParentalAdv.disabled = false;
      document.forms[0].cmd.value = save;
      showWaitMsg();  // mc its 966 show please wait message
      document.forms[0].submit();

  }
  else
  {
    eval( 'mtb' + imagePrefix ).reset();
  }
 }
} // end function submitSave()

function submitCopy()
{
  document.forms[0].cmd.value = editCopy;
  showWaitMsg();  // mc its 966 show please wait message
  document.forms[0].submit();
}

function submitPaste()
{
  document.forms[0].cmd.value = pasteCopy;
  showWaitMsg();  // mc its 966 show please wait message
  document.forms[0].submit();
}

function saveComments()
{
  document.forms[0].cmd.value = saveComment;
  showWaitMsg();  // mc its 966 show please wait message
  document.forms[0].submit();
} //end function saveComment()



function submitPrintPdf( id )
{
  if (ieCheck)
  {
    document.forms[0].cmd.value = printPdf;
    document.forms[0].submit();
  }
  else
  {
    document.forms[0].cmd.value = printPdf4;
    document.forms[0].submit();
  }
} // end function submitPrint()

function submitPrintRtf( id )
{
  if (ieCheck)
  {
    document.forms[0].cmd.value = printRtf;
    document.forms[0].submit();
  }
  else
  {
    document.forms[0].cmd.value = printRtf4;
    document.forms[0].submit();
  }
} // end function submitPrint()

function submitList( pType )
{
  //set the right command to sort the notepad by the title that was clicked
  document.forms[0].OrderBy.value = pType;
  document.forms[0].cmd.value = sort;
  showWaitMsg();  // mc its 966 show please wait message
  document.forms[0].submit();
} // end function submitList()

function submitGet( pId )
{
  document.forms[0].selectionID.value = pId;
  parent.top.bottomFrame.location = editor + "&selection-id=" + pId;
} // end function submitGet()

function validateDate(str)
{
  if(str.length > 0)
  {
   errorMsg = "";
   for(i=0; i<str.length; i++)
	 {
	   s = str.substring(i,i+1);
       if(isNaN(s) && s != "/" && s != "-")
				errorMsg = "Character is not allowed. Please retry again!";
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
   else if(str.indexOf("-") > -1)
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
  else
	errorMsg="Data entered is not a valid Date. Please retry again";
  	if(errorMsg.length > 0)
	{
	  alert(errorMsg);
  	  document.forms[0].streetDate.focus();
	}
  }
} // end function validateDate()

function validateNumericFields(theField)
{
  var checkOK = "0123456789";
  var checkStr = theField.value;
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
} // end function validateNumericFields()

function toggleSearch()
{
  toggle( 'SearchLayer', 'ArtistSearch' );
}// end function toggleSearch

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

function clearCombo(obj)
{
	var i;
  for(i=obj.options.length; i>=0; i--)
		obj.options[i] = null;
   	obj.selectedIndex = -1;
} //end function clearCombo()


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
      document.forms[0].subConfiguration.options[1].selected=true;

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

//JP #ITS#185
function toggleSave(infDir)
{
	if (document.all.save == null)
		return;

  if(document.all.printOption[0].checked)
  {
    eval("document.all.save.src = '" + infDir + "Save_On.gif';");
    eval("document.all.save2.src = '" + infDir + "Save_On.gif';");
  }
  else
  {
    eval("document.all.save.src = '" + infDir + "Save_Send_On.gif';");
    eval("document.all.save2.src = '" + infDir + "Save_Send_On.gif';");
  }
}

//JP 9/22/03
function toggleSaveSend(infDir)
{
  if (document.all.save == null)
    return;

  if (document.all.sendOption[0] && document.all.sendOption[0].checked)
  {
    eval("document.all.save.src = '" + infDir + "Save_Send_On.gif';");
    eval("document.all.save2.src = '" + infDir + "Save_Send_On.gif';");
  }
  else
  {
    eval("document.all.save.src = '" + infDir + "Save_On.gif';");
    eval("document.all.save2.src = '" + infDir + "Save_On.gif';");
  }
}

function toggleSaveHover(infDir)
{
  if (document.all.save.src.indexOf("Save_Send") > 0)
  {
    if (document.all.save.src.indexOf("_On") > 0)
      eval("document.all.save.src = '" + infDir + "Save_Send_Over.gif';");
    else
      eval("document.all.save.src = '" + infDir + "Save_Send_On.gif';");
  }
  else
  {
    if (document.all.save.src.indexOf("_On") > 0)
      eval("document.all.save.src = '" + infDir + "Save_Over.gif';");
    else
      eval("document.all.save.src = '" + infDir + "Save_On.gif';");
  }
}

function toggleSaveHoverBottom(infDir)
{
  if (document.all.save2.src.indexOf("Save_Send") > 0)
  {
    if (document.all.save2.src.indexOf("_On") > 0)
      eval("document.all.save2.src = '" + infDir + "Save_Send_Over.gif';");
    else
      eval("document.all.save2.src = '" + infDir + "Save_Send_On.gif';");
  }
  else
  {
    if (document.all.save2.src.indexOf("_On") > 0)
      eval("document.all.save2.src = '" + infDir + "Save_Over.gif';");
    else
      eval("document.all.save2.src = '" + infDir + "Save_On.gif';");
  }
}

////////////////////////////////////////
// PFM Push data validations          //
////////////////////////////////////////
function PFMPushValidation()
{
    var result = false;   // default to unsuccessfull

    //If Final validate pfm push variables
    if (isFinal)
    {
       //document.getElementById("operating_company").value

       result = true;   // validation successfull
    }
    else
       result = true;   // not final ignore validation

    return result;      // return validation results
}

//JP END




