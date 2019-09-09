/**
 * Milestone v2
 *
 * File: schedule-suggested-template-javascript.js
 */

function toggleSearch()
 {
   toggle( 'SearchLayer', 'ArtistSearch' );
 }

function checkPage()
{
  //do nothing
}

var commentsCount = 0;
var commentType = '0';

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
}

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
           bReturn = bReturn && checkLength( pField.value, "Comments", 0, 255 );
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
        var nowTime = now.getTime() ;
        var nextWeek = nowTime + 604800000;
        var testResult = checkDate( pField, "Completion Date", true );
        bReturn = bReturn && testResult ;
        if (testResult)
        {
          var compTimeDate = new Date(pField.value);
          var year = compTimeDate.getFullYear();
          if(year < 1970)
          {
            year = year + 100 ;compTimeDate.setYear(year);
          }
          var compTime  = compTimeDate.getTime() ;
          if (compTime > nowTime)
          {
            alert("The completion date is beyond the current day.");
            bReturn =  false;
          }
          else
          {
            bReturn = bReturn && true;
          }
        }//end if testResult
     }//end if pField.name == "completioinDate"

     if( !bReturn )
     {
       pField.focus();
     }
     return bReturn;
   }//end function

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
       //document.forms[0].searchIndex.value = "2";
       document.forms[0].TemplateNameSearch.focus();
       layer2Count = 1;
     }
     else
     {
       layer.visibility = "Hidden";
       layer2Count = 0;
     }
   }//end function

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
       //document.forms[0].searchIndex.value = "3";
       document.forms[0].ArtistSearchSource.focus();
       layer3Count = 1;
     }
     else
     {
       layer.visibility = "Hidden";
       layer3Count = 0;
     }
   }//end function

   var mfData = null;

   function submitGet( pselectionID )
   {
     parent.top.bottomFrame.location = scheduleEditor + "&selection-id=" + pselectionID;
   }//end function

   function submitSave()
   {
   	 parent.top.bottomFrame.location = scheduleEditor;
   }//end function

   function submitDelete()
   {
     if(confirm("You are about to delete all tasks assigned to this release. Confirm?"))//jo ITS 1006
     {
       //set the command to the right one to Delete the schedule
       parent.top.bottomFrame.location = deleteAllTasks;
     }
     else
     {
       eval( 'mtb' + 'Delete' ).reset();
     }
   }//end function

   function submitSearch()
   {
     //set the command to the right one to search from the search layer
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

     document.forms[0].submit();
   }//end function

   function submitTemplateSourceSearch()
   {
     //this command is set to submit the form to the shcedule-select-template.jsp when
     //user clicks Submit Search button  on the layer
   	 document.forms[0].cmd.value = editor;
     document.forms[0].submit();
   }//end function

   function submitReleaseSourceSearch()
   {
     document.all.StreetDateSearchSource.value = convertDate( document.all.StreetDateSearchSource.value );
   	 document.forms[0].cmd.value = copySchedule;
     document.forms[0].submit();
   }//end function

   function getTemplateTasks(templateId)
   {
     //this command is set so when user clicks on the radio button next to suggested
     //template the form submits to the schedule-editor and the template is assigned to
     //the schedule
     parent.top.bottomFrame.location = assignTemplate + "&templateId=" + templateId;
   }//end function

   function submitList( pType )
   {
     //set the right command to sort the notepad by the title that was clicked
    document.forms[0].OrderBy.value = pType;
    document.forms[0].cmd.value = sort;
    document.forms[0].submit();
   }//end function

   function submitRecalc( pType, imagePrefix )
   {
     mtbRecalc.reset();
   }//end function

   function sendRecalc(pType)
   {
     if (pType)
       parent.top.bottomFrame.location = recalc;
     else
       parent.top.bottomFrame.location = recalcAll;
   }//end function

   function submitClearDate( imagePrefix )
   {
     if( confirm( "This will permanently clear all Release Due Dates and Weeks To Release fields. Confirm?" ) )
     {
       //set the right command to Clear
       parent.top.bottomFrame.location = scheduleEditor;
     }
     else
     {
       eval( 'mtb' + imagePrefix ).reset();
     }
   }//end function

   function clickCurrentFilter(obj)
   {
     document.forms[0].cmd.value = filter;
     document.forms[0].submit();
   }

   function saveDetailData( pLayer, pField)
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
           //change the command with the right one to save the  comments that user has been entered
           parent.top.bottomFrame.location = suggestedTemplate;
         }
       }
     }//end if mfData
   }//end function

   function submitGet( pselectionID )
   {
	  parent.top.bottomFrame.location = scheduleEditor + "&selection-id=" + pselectionID;
   }//end function

   function saveComment()
   {
     //set the right command to save the Release Comment Layer entered comments
     //I don't know if release(selection) comments will have the option to save
     //since the top part of the page - the selection part is read only
     parent.top.bottomFrame.location = suggestedTemplate;
   }

   function MM_swapImgRestore()
   { //v3.0
     var i, x, a=document.MM_sr;
     for( i = 0; a && i < a.length && (x = a[i]) && x.oSrc; i++)
     {
       x.src = x.oSrc;
     }
   } //end function

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
   }//end function

   function MM_findObj(n, d)
   { //v3.0
     var p,i,x;
     if(!d) d=document;
     if((p=n.indexOf("?"))>0&&parent.frames.length)
     {
       d=parent.frames[n.substring(p+1)].document;
       n=n.substring(0,p);
     }
     if(!(x=d[n])&&d.all) x=d.all[n];
     for (i=0;!x&&i<d.forms.length;i++)
     {
       x=d.forms[i][n];
     }
     for(i=0;!x&&d.layers&&i<d.layers.length;i++)
     {
       x=MM_findObj(n,d.layers[i].document);
     }
     return x;
   }//end function

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
   }//end function

   function submitShow( pIsRelease )
   {
     if( pIsRelease )
     {
       //set the command to the right one to show the selection notepad
       parent.top.bottomFrame.location = taskEditor;
     }
     else
     {
       //set the command to the right one to show the tasks notepad
       parent.top.bottomFrame.location = taskEditor;
     }
   }//end function


function compareText (option1, option2) {
  if (option1.text.toUpperCase() == "ALL")
    return -1;
  else if (option2.text.toUpperCase() == "ALL")
    return 1;
  else
    return option1.text.toUpperCase() < option2.text.toUpperCase() ? -1 : option1.text.toUpperCase()  > option2.text.toUpperCase() ? 1 : 0;
}

/*
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

// JP - ITS#282
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

 function submitCommentsSave()
 {
       document.forms[0].cmd.value = saveSelectionComments;
   document.forms[0].submit();
 }//end function
