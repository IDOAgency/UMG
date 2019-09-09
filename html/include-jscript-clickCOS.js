/**
 * Milestone v2
 *
 * File: include-jscript-clickCOS.js
 */

////////////////////////////////////////////////////////////
// Define the corporate structure drop down click object
///////////////////////////////////////////////////////////

////////////////////////////////////
// The constructure for the class
////////////////////////////////////
function COS(familySelObj,environmentSelObj,companySelObj,
             divisionSelObj,labelSelObj,isMultipleSel) {

  // store the five level corporate structures
  this.famSelObj = familySelObj;
  this.envSelObj = environmentSelObj;
  this.comSelObj = companySelObj;
  this.divSelObj = divisionSelObj;
  this.labSelObj = labelSelObj;
  this.isMultSel = isMultipleSel;

  this.index = 0;
  this.refreshAll = false;
  this.refreshMAC = false;
  this.isMAC = false;
  // determine if a MAC
  var navCom = navigator.platform;
  if( navCom.toUpperCase() == "MACPPC" || navCom.substr(0,3).toUpperCase() == "MAC" )
    this.isMAC = true;

  // Prototype the class methods
  if(!COS.prototype.ClickFamilySearch)
  {
     COS.prototype.clickFamilySearch = ClickFamilySearch;
     COS.prototype.clickFamilySearchMult = ClickFamilySearchMult;
     COS.prototype.clickEnvironmentSearch = ClickEnvironmentSearch;
     COS.prototype.clickEnvironmentSearchMult = ClickEnvironmentSearchMult;
     COS.prototype.clickCompanySearch = ClickCompanySearch;
     COS.prototype.clickCompanySearchMult = ClickCompanySearchMult;

     // Multiple Selection Methods
     COS.prototype.populateChildMultipleSelect = PopulateChildMultipleSelect;
     COS.prototype.populateEnvironmentSearchMult = PopulateEnvironmentSearchMult;
     COS.prototype.populateLabelFromFamilyMult = PopulateLabelFromFamilyMult;
     COS.prototype.populateCompanyFromFamilyMult = PopulateCompanyFromFamilyMult;
     COS.prototype.setHiddenValuesCOS  = SetHiddenValuesCOS
     COS.prototype.refreshValues = RefreshValues;
     COS.prototype.removeSelectCOS = RemoveSelectCOS;
     COS.prototype.selectAllCOS = SelectAllCOS;
     COS.prototype.clickCheckCOS = ClickCheckCOS;
     COS.prototype.buildLowerLevelsAll = BuildLowerLevelsAll;
     COS.prototype.isUpLvlCheckedCOS = IsUpLvlCheckedCOS;


     // old onchange methods
     COS.prototype.clickEnvironment = ClickEnvironment;
     COS.prototype.clickCompany = ClickCompany;

  }

  // save the levels original option opbjects for refreshing all
  this.envSelObjAll = new Object();
  this.envSelObjAll.options = new Array();
  this.comSelObjAll = new Object();
  this.comSelObjAll.options = new Array();
  this.divSelObjAll = new Object();
  this.divSelObjAll.options = new Array();
  this.labSelObjAll = new Object();
  this.labSelObjAll.options = new Array();
  if(this.envSelObj)
    COS.populateChildFromAll(this.envSelObjAll,this.envSelObj);
  if(this.comSelObj)
    COS.populateChildFromAll(this.comSelObjAll,this.comSelObj);
  if(this.divSelObj)
    COS.populateChildFromAll(this.divSelObjAll,this.divSelObj);
  if(this.labSelObj)
    COS.populateChildFromAll(this.labSelObjAll,this.labSelObj);

} //end if COS.constructor

///////////////////////////////////////////////
function ClearCombo(obj)
///////////////////////////////////////////////
{
  /*
  var i;
  for(i=obj.options.length; i>=0; i--)
   obj.options[i] = null;
  obj.selectedIndex = -1;
  */

  obj.options.length = 0; // faster way to clear the select box
} //end class method clearCombo()
COS.clearCombo = ClearCombo;  // make it a class method

/////////////////////////////////////////////////
function ClickFamilySearch(obj)
/////////////////////////////////////////////////
{

    if(this.envSelObj)
      COS.clearCombo(this.envSelObj);

    if(this.comSelObj)
      COS.clearCombo(this.comSelObj);

    if(this.labSelObj)
      COS.clearCombo(this.labSelObj);


   // msc its 999 Added this logic to speed up processing with all selected
   // if family object and all selected
   if( obj[obj.selectedIndex].value == 0 )
   {
     this.buildLowerLevelsAll(obj.name,false);    // rebuild the lower levels using the all options object

   } else {

      if(this.envSelObj)
        COS.populateEnvironmentSearch(this.envSelObj, obj[obj.selectedIndex].value);

      if(this.comSelObj)
        COS.populateCompanyFromFamily(this.comSelObj, obj[obj.selectedIndex].value);

      if(this.labSelObj)
        COS.populateLabelFromFamily(this.labSelObj, obj[obj.selectedIndex].value);
   }

  return true;

} // end function COS.clickFamilySearch()


/////////////////////////////////////////////////
function ClickFamilySearchMult(obj)
/////////////////////////////////////////////////
{
  if(this.envSelObj)
    COS.clearCombo(this.envSelObj);

  if(this.comSelObj)
    COS.clearCombo(this.comSelObj);

  if(this.labSelObj)
    COS.clearCombo(this.labSelObj);

  if(this.envSelObj)
     this.populateChildMultipleSelect(obj,this.envSelObj);

  if(this.comSelObj)
    this.populateChildMultipleSelect(obj,this.comSelObj);

  if(this.labSelObj)
    this.populateChildMultipleSelect(obj,this.labSelObj);

  return true;

} // end function COS.clickFamilySearch()


/////////////////////////////////////////////////////
function PopulateChildMultipleSelect(objParent,objChild)
/////////////////////////////////////////////////////
{
       this.index = 0;

       // process the selected values
       for(var i=0; i < objParent.options.length; i++)
       {
         // skip all check box
         if( (objParent.options[i].selected || this.refreshAll) && objParent.options[i].value != "0")
         {
           if(objChild.name == "environment")
             this.populateEnvironmentSearchMult(objChild,objParent[objParent.options[i].index].value);
           if(objChild.name == "company")
             this.populateCompanyFromFamilyMult(objChild,objParent[objParent.options[i].index].value);
           if(objChild.name == "Label")
             this.populateLabelFromFamilyMult(objChild,objParent[objParent.options[i].index].value);
         }
       }

       // sort the options by text name
       COS.sortSelect(objChild,COS.compareText);


} // end function COS.MultipleSelectValue()


/////////////////////////////////////////////////////
function PopulateEnvironmentSearch(obj, index)
/////////////////////////////////////////////////////
{
   COS.clearCombo(obj);

   // mc 05/25/04 familyArray must be an object
   if(typeof familyArray != "object")
     return;

   var j = 0;
   for(var i=0; i < familyArray[index].length; i=i+2){
       obj.options[j] = new Option(familyArray[index][i+1], familyArray[index][i]);
       j++;
   }
   COS.sortSelect(obj,COS.compareText);
   obj.options[0].selected=true;

} //end function populateEnvironment()
COS.populateEnvironmentSearch = PopulateEnvironmentSearch; // make it a class variable


/////////////////////////////////////////////////////
function PopulateEnvironmentSearchMult(obj, index)
/////////////////////////////////////////////////////
{
   // mc 05/25/04 familyArray must be an object
   if(typeof familyArray != "object")
     return;

   for(var i=0; i < familyArray[index].length; i=i+2) {
       obj.options[this.index] = new Option(familyArray[index][i+1], familyArray[index][i]);
       this.index++;
   }

} //end function populateEnvironment()

/////////////////////////////////////////////////////
function PopulateChildFromAll(obj, objAll)
/////////////////////////////////////////////////////
{
   // clear the target object
   obj.options.length = 0;
   // build the target from the saved all selection object
   for(var i=0; i < objAll.options.length; i++)
       obj.options[i] = new Option(objAll.options[i].text, objAll.options[i].value);

} //end function populateEnvironment()
COS.populateChildFromAll = PopulateChildFromAll;


//////////////////////////////////////////////////////
function SortSelect (select, compareFunction) {
//////////////////////////////////////////////////////

 ////////////////////////////////////////
 var isDuplicate = false;
 var z = -1;
 ///////////////////////////////////////

  if (!compareFunction)
    compareFunction = COS.compareText;

  var options = new Array (select.options.length);
  for (var i=0; i < options.length; i++)
  {
     ////////////////////////////////////////
     // check for duplicate text names if label object
     isDuplicate = false;

     // msc for now only check for duplicates on Label object
     //if(select.name.toUpperCase().indexOf("LABEL") != -1)
     //{
       for(var x=0; x < (z + 1); x++)
       {
         if(options[x] != null)
         {
            //////////////////////////////////////////////////////
            // If this is a label object, check case insensitive
            //////////////////////////////////////////////////////
            if(select.name.toUpperCase().indexOf("LABEL") != -1)
            {
               // if duplicate found, add duplicate value to the options value variable
              if(options[x].text.toUpperCase() == select.options[i].text.toUpperCase())
              {

                if(options[x].value != "")  // if not blanks
                {
                   options[x].value = options[x].value + "," + select.options[i].value;
                } else {   // if blanks
                   options[x].value = select.options[i].value;
                }
                //alert(" optioins text " + options[x].text + " options value " + options[x].value +  " select value " + select.options[i].value);
                isDuplicate = true;  // duplicate text name
                break;
              }
            }
            else // if not a label object, check case sensitive
            {
              // if duplicate found, ignore option value
              if(options[x].text == select.options[i].text)
              {
                isDuplicate = true;  // duplicate text name
                break;
              }
           }
         }
         else
         {
            break;
         }
       }
     //}

    // if no duplicate found, insert in array
    if(!isDuplicate)
    {
       z++;
       options[z] = new Option (
          select.options[i].text,
          select.options[i].value,
          select.options[i].defaultSelected,
          select.options[i].selected);
    }
  }

  options.sort(compareFunction);
  select.options.length = 0;
  for (var i = 0; i < (z + 1); i++)
  {
    select.options[i] = options[i];
  }
} // End function sortselect
COS.sortSelect = SortSelect; // make it a class variable

///////////////////////////////////////////
function CompareText (option1, option2) {
///////////////////////////////////////////
  if (option1.text.toUpperCase() == "ALL")
    return -1;
  else if (option2.text.toUpperCase() == "ALL")
    return 1;
  else
    return option1.text.toUpperCase() < option2.text.toUpperCase() ? -1 : option1.text.toUpperCase()  > option2.text.toUpperCase() ? 1 : 0;
}
COS.compareText = CompareText; // make it a class variable


///////////////////////////////////////////////////
function PopulateCompanyFromFamily(obj, index){
//////////////////////////////////////////////////
var j = 1;

 //For every entry in the family array
 COS.clearCombo(obj);

 // mc 05/25/04 familyArray must be an object
 if(typeof familyArray != "object")
   return;

 obj.options[0] = new Option('All',0);

 for(var i=2; i < familyArray[index].length; i=i+2)
 {
    var environmentId = familyArray[index][i];
    //for every environment entry, get its company
    if (environmentId ==0)
    {
      obj.options[0] = new Option(environmentArray[environmentId][1], environmentArray[environmentId][0]);
      j++;
    } else {
      for(var m=2; m < environmentArray[environmentId].length; m=m+2)
      {
        var companyId = environmentArray[environmentId][m];
        if(  companyId != 0 )
        {
          obj.options[j] = new Option(environmentArray[environmentId][m+1], environmentArray[environmentId][m]);
          j++;
        }
      }
    }
  }
  COS.sortSelect(obj,COS.compareText);
}
COS.populateCompanyFromFamily = PopulateCompanyFromFamily; // make it a class variable



///////////////////////////////////////////////////
function PopulateCompanyFromFamilyMult(obj, index){
//////////////////////////////////////////////////

 // mc 05/25/04 familyArray must be an object
 if(typeof familyArray != "object")
   return;

 for(var i=2; i < familyArray[index].length; i=i+2)
 {
    var environmentId = familyArray[index][i];
    //for every environment entry, get its company
    if (environmentId == 0)
    {
      obj.options[0] = new Option(environmentArray[environmentId][1], environmentArray[environmentId][0]);
      this.index++;
    } else {
      for(var m=2; m < environmentArray[environmentId].length; m=m+2)
      {
        var companyId = environmentArray[environmentId][m];
        if(  companyId != 0 )
        {
          obj.options[this.index] = new Option(environmentArray[environmentId][m+1], environmentArray[environmentId][m]);
          this.index++;
        }
      }
    }
  }
}


///////////////////////////////////////////////////
function PopulateLabelFromFamily(obj, index){
///////////////////////////////////////////////////
var j = 0;
var jk = 1;
  //For every entry in the family array
  COS.clearCombo(obj);

  // mc 05/25/04 familyArray must be an object
  if(typeof familyArray != "object")
    return;

  obj.options[0] = new Option('All',0);
  for(var i=2; i < familyArray[index].length; i=i+2){
    var environmentId = familyArray[index][i];
    if(environmentArray[environmentId].length!=null || environmentArray[environmentId].length == undefinded) 
    {	    for(var m=2; m < environmentArray[environmentId].length; m=m+2)
		    {
		      var companyId = environmentArray[environmentId][m];
				      for(var n=2; n < companyArray[companyId].length; n=n+2)
				      {
				        var labelId = companyArray[companyId][n];
				        obj.options[jk] = new Option(companyArray[companyId][n+1], companyArray[companyId][n]);
				        jk++;
				      }
		    }
    }
   }
  COS.sortSelect(obj,COS.compareText);
}
COS.populateLabelFromFamily = PopulateLabelFromFamily; // make it a class variable


///////////////////////////////////////////////////
function PopulateLabelFromFamilyMult(obj, index)
///////////////////////////////////////////////////
{
  // mc 05/25/04 familyArray must be an object
  if(typeof familyArray != "object")
    return;

  for(var i=2; i < familyArray[index].length; i=i+2){
    var environmentId = familyArray[index][i];
    for(var m=2; m < environmentArray[environmentId].length; m=m+2){
      var companyId = environmentArray[environmentId][m];
      for(var n=2; n < companyArray[companyId].length; n=n+2){
        var labelId = companyArray[companyId][n];
        obj.options[this.index] = new Option(companyArray[companyId][n+1], companyArray[companyId][n]);
        this.index++;
      }
    }
  }
}

///////////////////////////////////////////////////
function ClickEnvironmentSearch(obj)
//////////////////////////////////////////////////
{
  if (obj.selectedIndex == 0)
    return this.clickFamilySearch(this.famSelObj);

  if(this.comSelObj) {
    COS.clearCombo(this.comSelObj);
    COS.populateCompanySearch(this.comSelObj, obj[obj.selectedIndex].value);
  }
  if((this.labSelObj)) {
    COS.clearCombo(this.labSelObj);
    COS.populateLabelFromEnvironment(this.labSelObj,obj[obj.selectedIndex].value)
  }

  return true;
} //end function clickEnvironmentSearch()


///////////////////////////////////////////////////
function ClickEnvironmentSearchMult(obj)
//////////////////////////////////////////////////
{
  // if all selected
  //if (obj.selectedIndex == 0)
  //  return this.clickFamilySearchMult(this.famSelObj);

  // process the selected values
  var jC = 0;
  var jL = 0

  if(this.comSelObj)
    COS.clearCombo(this.comSelObj);
  if(this.labSelObj)
    COS.clearCombo(this.labSelObj);

  for(var x=0; x < obj.options.length; x++)
  {
    // if selected
    if((obj.options[x].selected || this.refreshAll) && obj.options[x].value != "0")
    {
      var index = obj[obj.options[x].index].value;
      // company
      if(this.comSelObj) {
        for(var i=0; i < environmentArray[index].length; i=i+2)
        {
          // skip all
          if(environmentArray[index][i] != "0")
          {
   	    this.comSelObj.options[jC] = new Option(environmentArray[index][i+1], environmentArray[index][i]);
            jC++;
          }
        }
      }
      // label
      if((this.labSelObj))
      {
        for(var i=2; i < environmentArray[index].length; i=i+2)
        {
          // skip all
          if(environmentArray[index][i] != "0")
          {
            var companyId = environmentArray[index][i];
            for(var m=2; m < companyArray[companyId].length; m=m+2)
            {
              var labelId = companyArray[companyId][m];
              this.labSelObj.options[jL] = new Option(companyArray[companyId][m+1], companyArray[companyId][m]);
              jL++;
            }
          }
        }
      }
    }
  }

  // sort the lower level arrays
  COS.sortSelect(this.comSelObj,COS.compareText);
  COS.sortSelect(this.labSelObj,COS.compareText);

  return true;
} //end function clickEnvironmentSearchMult()



////////////////////////////////////////////////
function ClickCompanySearch(obj)
///////////////////////////////////////////////
{
  if (obj.selectedIndex == 0)
    return this.clickEnvironmentSearch(this.envSelObj);

  if(this.labSelObj)
  {
    COS.clearCombo(this.labSelObj);
    COS.populateLabelSearch(this.labSelObj, obj[obj.selectedIndex].value);
  }
  return true;
} //end function clickCompany()
//COS.clickCompanySearch = ClickCompanySearch; // make it a class variable


////////////////////////////////////////////////
function ClickCompanySearchMult(obj)
///////////////////////////////////////////////
{
  // if not object exists, return
  if(!this.labSelObj)
    return;

  //if (obj.selectedIndex == 0)
  //  return this.clickEnvironmentSearchMult(this.envSelObj);

  COS.clearCombo(this.labSelObj);

  var jL = 0;

  for(var x=0; x < obj.options.length; x++)
  {
    // if selected
    if((obj.options[x].selected || this.refreshAll) && obj.options[x].value != "0" && obj.options[x].value != "-1")
    {
      var index = obj[obj.options[x].index].value;
      if(this.labSelObj)
      {
        for(var i=0; i < companyArray[index].length; i=i+2)
        {
          // skip all
          if(companyArray[index][i] != "0")
          {
            this.labSelObj.options[jL] = new Option(companyArray[index][i+1], companyArray[index][i]);
            jL++;
          }
        }
      }
    }
  }
  // sort the array
  COS.sortSelect(this.labSelObj,COS.compareText);

  return true;
} //end function clickCompany()


//////////////////////////////////////////////
function PopulateLabelSearch(obj, index)
/////////////////////////////////////////////
{
  var j = 0;

  COS.clearCombo(obj);

  if(companyArray.length >= index){
    if(companyArray[index]){
      for(var i=0; i < companyArray[index].length; i=i+2)
      {
        obj.options[j] = new Option(companyArray[index][i+1], companyArray[index][i]);
        j++;
      }
    } else {
      	obj.options[j] = new Option('All',0);
      }
    } else {
      	obj.options[j] = new Option('All',0);
  }
  COS.sortSelect(obj,COS.compareText);
  obj.options[0].selected=true;
} //end function populateLabel()
COS.populateLabelSearch = PopulateLabelSearch; // make it a class variable



// need to remember these values for the selection dropdowns
COS.cValue = "";
COS.dValue = "";
COS.lValue = "";

//////////////////////////////////////////
function ClickEnvironment(obj)
/////////////////////////////////////////
{
  cValue = this.comSelObj.value; // JR - ITS 327
  COS.clearCombo(this.comSelObj);
  COS.populateCompany(this.comSelObj, obj[obj.selectedIndex].value);

  for (var i =0; i < this.comSelObj.length; i++)
  {
	if (this.comSelObj[i].value == cValue)
	  this.comSelObj[i].selected = true;
  }
  COS.clickCompany(this.comSelObj);
  return true;
} //end function clickEnvironment()
//COS.clickEnvironment = ClickEnvironment; // make it a class variable

////////////////////////////////////////////////////
function ClickCompany(obj)
/////////////////////////////////////////////////////
{
  dValue = this.divSelObj.value; // JR - ITS 327
  COS.clearCombo(this.divSelObj);
  COS.populateDivision(this.divSelObj, obj[obj.selectedIndex].value);

  //traverse the Divisions to reset the old value
  for (var i =0; i < this.divSelObj.options.length; i++)
  {
     if (this.divSelObj.options[i].value == dValue)
	this.divSelObj.options[i].selected = true;
  }
  COS.clickDivision(this.divSelObj);
  return true;
} //end function clickCompany()
//COS.clickCompany = ClickCompany; // make it a class variable

///////////////////////////////////////////////
function ClickDivision(obj)
///////////////////////////////////////////////
{
  lValue = this.labSelObj.value; // JR - ITS 327
  if (obj.selectedIndex > -1)
    COS.populateLabel(this.labSelObj, obj[obj.selectedIndex].value);

  // JR - ITS 327
  for (var i =0; i < this.labSelObj.options.length; i++)
  {
     if (this.labSelObj.options[i].value == lValue)
	this.labSelObj.options[i].selected = true;
  }
  return true;
} //end function clickDivision()
COS.clickDivision = ClickDivision; // make it a class variable

///////////////////////////////////////////
function PopulateCompany(obj, index)
//////////////////////////////////////////
{
   var j = 0;

   if (index > -1)
   {
     for(var i=0; i<a[index].length; i=i+2)
     {
       this.comSelObj.options[j] = new Option(a[index][i+1], a[index][i]);
       j++;
     }
     this.comSelObj.options[0].selected=true;
   }
} //end function populateCompany()
COS.populateCompany = PopulateCompany; // make it a class variable

//////////////////////////////////////////
function PopulateDivision(obj, index)
//////////////////////////////////////////
{
   var j = 0;
   if (b[index])
   {
     for(var i=0; i<b[index].length; i=i+2)
     {
       this.divSelObj.options[j] = new Option(b[index][i+1], b[index][i]);
       j++;
     }
     this.divSelObj.options[0].selected=true;
   }
} //end function populateDivision()
COS.populateDivision = PopulateDivision; // make it a class variable

////////////////////////////////////////
function PopulateLabel(obj, index)
///////////////////////////////////////
{
  var currentLabelValue = this.labSelObj.value;
  COS.clearCombo(obj);
  var j = 0;

  if(c.length >= index)
  {
    if(c[index])
    {
      for(var i=0; i<c[index].length; i=i+2)
      {
        this.labSelObj.options[j] = new Option(c[index][i+1], c[index][i]);
        j++;
      }
    }
    else
    {
      this.labSelObj.options[j] = new Option('[none available]',0);
    }
  }
  else
  {
    this.labSelObj.options[j] = new Option('[none available]',0);
  }

  var selfnd = false;  // msc
  for (var k=0; k<this.labSelObj.options.length;k++)
  {
    if (this.labSelObj.options[k].value == currentLabelValue)
    {
      this.labSelObj.options[k].selected = true;
      selfnd = true;  // msc
    }
  }
  if(!selfnd)  // msc
      this.labSelObj.options[0].selected=true;  // msc
} //end function populateLabel()
COS.populateLabel = PopulateLabel; // make it a class variable

//////////////////////////////////////////////
function PopulateCompanySearch(obj, index)
/////////////////////////////////////////////
{
   var j = 0;

   COS.clearCombo(obj);
   for(var i=0; i < environmentArray[index].length; i=i+2){
	obj.options[j] = new Option(environmentArray[index][i+1], environmentArray[index][i]);
       j++;
   }
   COS.sortSelect(obj,COS.compareText);
   obj.options[0].selected=true;

} //end function populateCompany()
COS.populateCompanySearch = PopulateCompanySearch; // make it a class variable


///////////////////////////////////////////
function PopulateLabelSearch(obj, index)
///////////////////////////////////////////
{
  var j = 0;

  COS.clearCombo(obj);

  if(companyArray.length >= index)
  {
    if(companyArray[index])
    {
      for(var i=0; i < companyArray[index].length; i=i+2)
      {
        obj.options[j] = new Option(companyArray[index][i+1], companyArray[index][i]);
        j++;
      }
    } else {
      	obj.options[j] = new Option('All',0);
      }
    } else {
      	obj.options[j] = new Option('All',0);
  }
  COS.sortSelect(obj,COS.compareText);
  obj.options[0].selected=true;
} //end function populateLabel()
COS.populateLabelSearch = PopulateLabelSearch; // make it a class variable

/////////////////////////////////////////////////////
function PopulateLabelFromEnvironment(obj, index){
/////////////////////////////////////////////////////
var j = 1;
 //For every entry in the environment array
 COS.clearCombo(obj);
 obj.options[0] = new Option('All',0);
 for(var i=2; i < environmentArray[index].length; i=i+2){
   var companyId = environmentArray[index][i];
   for(var m=2; m < companyArray[companyId].length; m=m+2){
     var labelId = companyArray[companyId][m];
     obj.options[j] = new Option(companyArray[companyId][m+1], companyArray[companyId][m]);
     j++;
   }
 }
 COS.sortSelect(obj,compareText);
}
//////////////////////////////////////////////////
COS.populateLabelFromEnvironment = PopulateLabelFromEnvironment; // make it a class variable


//////////////////////////////////////////////////////
// restore the values of the search elements
function SetValuesIndex(obj,valueStr)
//////////////////////////////////////////////////////
{

 // if null object
  if(obj == null || obj.options.length == 0)
    return;

  // return if empty value
  if(valueStr == "" || valueStr == "0" || valueStr == "-1" || valueStr == "0,0")
  {
    obj.options[0].selected = true;
    return;
  }

  var opts = obj.options;
  for(var i=0; i < opts.length; i++)
  {
     //alert(valueStr + " --  " + opts[i].value);
     if(opts[i].value == valueStr)
     {
       opts[i].selected = true;
       return;
     }
  }
}
COS.setValuesIndex = SetValuesIndex; // make it a class variable


/////////////////////////////////////////////////
// clear the search elements select objects
function ClearSelectedValue(obj)
////////////////////////////////////////////////
{
  // if null object
  if(obj == null)
    return;

  var opts = obj.options;
  if(opts.length > 0)
  {
    if(this.isMultSel)  // if multiple selection in effect
    {
      for(var i=0; i < opts.length; i++)
      {
         opts[i].selected = false;
      }
    }
    opts[0].selected = true;
  }
}
COS.clearSelectedValue = ClearSelectedValue; // make it a class variable

/////////////////////////////////////////////////
// clear the search element by value
function ClearSelectedValueStr(obj,oValue)
////////////////////////////////////////////////
{
  // if null object
  if(obj == null)
    return;

  var opts = obj.options;
  if(opts.length > 0)
  {
     for(var i=0; i < opts.length; i++)
      {
         // if equal to value
         if(opts[i].value == oValue)
         {
           opts[i].selected = false;
           break;
         }
      }
  }
}
COS.clearSelectedValueStr = ClearSelectedValueStr; // make it a class variable



///////////////////////////////////////////
// Check Box functions
//////////////////////////////////////////

function showSelect(oName)
{
   var obj = eval("document.all." + oName + "_Div");
   var objParent = eval("document.all." + oName);

   // add all check box
   var htmlStr = "<table width=500>";
   if(objParent.options.length > 0)
   {
      htmlStr += "<tr><td width=20 colspan=1>";
      htmlStr += "<input type=checkbox id=" + oName + "_All" + " type=checkbox" + " onclick=reportCOS.selectAllCOS('" + oName + "')>All";
      htmlStr += "</td></tr>";
   }

   // build children check boxes
   var c = 0;

   htmlStr += "<tr valign=top>";

   for(var i=0; i < objParent.options.length; i++)
   {

     var oValue = objParent.options[i].value;
     var oText  = objParent.options[i].text;

     // skip the all checkbox
     if(oValue == "0" || oValue == "-1" || oValue == "" || oValue == "0,0")
       continue;

     // convert comma delimitter duplicates to underscore
     var re = new RegExp (',', 'gi');
     oValue = oValue.replace(re,'_');

     // create checkbox id
     var oID= oName + "_" + oValue;

     if(c == 0)
       htmlStr += "<td>&nbsp;</td>";

     var checkedStr = "";
     var styleStr = "";
     if(objParent.options[i].selected)
     {
       checkedStr = "checked";
       styleStr = "style=\"color: red\"";
     }

     htmlStr += "<td  id=" + oID + "_TD " + styleStr + " width=125 colspan=1>";
     htmlStr += "<input type=checkbox id="
               + oID + " onclick=reportCOS.clickCheckCOS('" + oName
               + "','" + oValue + "',this) " + checkedStr + ">" + oText;
     htmlStr += "</td>";

     // if divisible by 4 returns 0, new row
     c++;
     if (c % 4 == 0)
     {
       c=0;
       htmlStr += "</tr>";
       htmlStr += "<tr valign=top>";
     }
   }

   // add close row if needed
   htmlStr  += "</tr>";

   htmlStr  += "</table>";

   obj.innerHTML = htmlStr;
   obj.style.display = "";
   obj.style.visibility = "visible";
   if(objParent.options.length > 20)
   {
     //obj.style.height = "200px";
     //obj.style.overflow = "auto";
   }
   else {
     obj.style.height = "";
     obj.style.overflow = "hidden";
   }

 }

function ClickCheckCOS(oName,oValue,cbObj)
{

  showWaitMsg();

  var obj = eval("document.all." + oName + "_All");
  obj.checked = false;

  var objParent = eval("document.all." + oName);
  if(objParent)
   {

     // convert comma delimitter duplicates back to comma, was underscore
     var re = new RegExp ('_', 'gi');
     oValue = oValue.replace(re,',');

     // set the selection
     var tdObj = null;
     if(cbObj.checked)
     {
       COS.setValuesIndex(objParent,oValue);
       // The MAC has an issue with the refresh processing
       // Do this only if not a MAC or and a refresh has not been executed
       if(!this.isMAC)
       {
         tdObj = eval(cbObj.id + "_TD");
         if(tdObj.style)
           tdObj.style.color = "red";
         else
           tdObj[0].style.color = "red";
       }
     } else {
       COS.clearSelectedValueStr(objParent,oValue);
       // The MAC has an issue with the refresh processing
       // Do this only if not a MAC or and a refresh has not been executed
       if(!this.isMAC)
       {
         tdObj = eval(cbObj.id + "_TD");
         if(tdObj.style)
           tdObj.style.color = "black";
         else
           tdObj[0].style.color = "black";
       }
     }

     // build the lower level drop downs
     COS.buildLowerLevels(oName);
  }

  hideWaitMsg();

}

///////////////////////////////////////////////////////
function BuildLowerLevels(oName)
///////////////////////////////////////////////////////
{

  var objParent = eval("document.all." + oName);
  if(objParent)
   {
     // family check box clicked
     if(oName == "family")
       {
       // update the dropdown box
       reportCOS.clickFamilySearchMult(objParent);

       // show the check boxes
       if(document.all.environment && document.all.environment_radio[1].checked)
          showSelect("environment");
       if(document.all.company && document.all.company_radio[1].checked)
          showSelect("company");
       if(document.all.Label && document.all.Label_radio[1].checked)
          showSelect("Label");
     }

     // Environment check box clicked
     if(oName == "environment")
     {
       // update the dropdown box
       reportCOS.clickEnvironmentSearchMult(objParent);

       // show the check boxes
       if(document.all.company && document.all.company_radio[1].checked)
          showSelect("company");
       if(document.all.Label && document.all.Label_radio[1].checked)
          showSelect("Label");
     }

     // Label check box clicked
     if(oName == "company")
     {
       // update the dropdown box
       reportCOS.clickCompanySearchMult(objParent);

       // show the check boxes
       if(document.all.Label && document.all.Label_radio[1].checked)
          showSelect("Label");
     }
   }
}
COS.buildLowerLevels = BuildLowerLevels;   // make a class variable

///////////////////////////////////////////////////////
function BuildLowerLevelsAll(oName,isCheckBoxs)
///////////////////////////////////////////////////////
{

  var objParent = eval("document.all." + oName);
  if(objParent)
   {
     // family check box clicked
     if(oName == "family" || oName == "FamilySearch")
       {

       // update the dropdown boxes using the saved all objects options
       if(this.envSelObj)
         COS.populateChildFromAll(this.envSelObj,this.envSelObjAll);

       if(this.comSelObj)
         COS.populateChildFromAll(this.comSelObj,this.comSelObjAll);

       if(this.labSelObj)
         COS.populateChildFromAll(this.labSelObj,this.labSelObjAll);

       if(isCheckBoxs)
       {
         // show the check boxes
         if(document.all.environment && document.all.environment_radio[1].checked)
            showSelect("environment");
         if(document.all.company && document.all.company_radio[1].checked)
            showSelect("company");
         if(document.all.Label && document.all.Label_radio[1].checked)
            showSelect("Label");
       }
     }

     // Environment check box clicked
     if(oName == "environment" || oName == "EnvironemntSearch")
     {
       // update the dropdown box
       if(this.comSelObj)
         COS.populateChildFromAll(this.comSelObj,this.comSelObjAll);

       if(this.labSelObj)
         COS.populateChildFromAll(this.labSelObj,this.labSelObjAll);

       if(isCheckBoxs)
       {
         // show the check boxes
         if(document.all.company && document.all.company_radio[1].checked)
            showSelect("company");
         if(document.all.Label && document.all.Label_radio[1].checked)
            showSelect("Label");
       }
     }

     // Label check box clicked
     if(oName == "company" || oName == "CompanySearch")
     {
       // update the dropdown box
       if(this.labSelObj)
         COS.populateChildFromAll(this.labSelObj,this.labSelObjAll);

       if(isCheckBoxs)
       {
         // show the check boxes
         if(document.all.Label && document.all.Label_radio[1].checked)
            showSelect("Label");
       }
     }
   }
}



function RemoveSelectCOS(oName)
{

   document.body.style.cursor="wait";

   // clear the selected options
   this.refreshAll = true;   // forces rebuild of lower levels
   this.refreshMAC = true;  // this variable was added to resolve a MAC issue
   var obj = eval("document.all." + oName);
   COS.clearAllSelectedCOS(obj);  // clear all selected options
   if(oName == "family" || !this.isUpLvlCheckedCOS(oName) )
     this.buildLowerLevelsAll(oName,true);    // rebuild the lower levels using the all options object
   else
     COS.buildLowerLevels(oName);  // build the lower levels using the click methods

   this.refreshAll = false;   // stops rebuild of lower levels

   var obj = eval("document.all." + oName + "_Div");
   obj.style.display = "none";

   document.body.style.cursor="default";
}

function SelectAllCOS(oName)
{
   showWaitMsg();

   document.body.style.cursor="wait";

   var obj = eval("document.all." + oName + "_All");
   obj.style.cursor="wait";

   var elements = document.all.tags("input");
   for(var i =0; i < elements.length; i++)
   {
       //alert(elements[i].id);
       if(elements[i].id.indexOf(oName) != -1
	   && elements[i].type == "checkbox"
	   && elements[i].id.indexOf("_All") == -1 )
       {
           // change the state of the check box
           if(obj.checked)
           {
             elements[i].checked = true;
             // The MAC has an issue with the refresh processing
             // Do this only if not a MAC or and a refresh has not been executed
             if(!this.isMAC)
             {
               tdObj = eval(elements[i].id + "_TD");
               if(tdObj.style)
                  tdObj.style.color = "red";
               else
                  tdObj[0].style.color = "red";
             }
           } else {
	     elements[i].checked = false;
             // The MAC has an issue with the refresh processing
             // Do this only if not a MAC or and a refresh has not been executed
             if(!this.isMAC)
             {
               tdObj = eval(elements[i].id + "_TD");
               if(tdObj.style)
                  tdObj.style.color = "black";
               else
                  tdObj[0].style.color = "black";
             }
          }

           // family check box clicked
           var objParent = null;
           if(oName == "family")
              objParent = eval("document.all.family");
           if(oName == "environment")
              objParent = eval("document.all.environment");
           if(oName == "company")
              objParent = eval("document.all.company");
           if(oName == "Label")
              objParent = eval("document.all.Label");

           // get the value from the checkbox id
           var pos = elements[i].id.indexOf("_");
           if(pos == -1) // if not found
             continue;

           var oValue = elements[i].id.slice(pos + 1);

           // convert the value back to a comma delimitted string
           var re = new RegExp ('_', 'gi');
           oValue = oValue.replace(re,',');

           // set the selection
           if(elements[i].checked)
              COS.setValuesIndex(objParent,oValue);
           else
              COS.clearSelectedValueStr(objParent,oValue);

       }
   }

   // build the lower level drop downs
   if(obj.checked && (oName == "family" || !this.isUpLvlCheckedCOS(oName)) ) // if checked and top level(family)
     this.buildLowerLevelsAll(oName,true);  // build lower levels from the all objects
   else
     COS.buildLowerLevels(oName);  // build the lower levels using the click methods

   document.body.style.cursor="default";
   obj.style.cursor="default";

   hideWaitMsg();

}


///////////////////////////////////////////////
function RefreshValues()
///////////////////////////////////////////////
{
  var showSelected = false;
  // do the family
  if(this.famSelObj)
  {
     for(var i=0; i < family_ValuesArray.length; i++)
     {
       // skip all and empty array elements
       if(family_ValuesArray[i] != 0 && family_ValuesArray != -1)
       {
         showSelected = true;
         COS.setValuesIndex(this.famSelObj,family_ValuesArray[i]);
       }
     }
     // if user selected atleast one value
     if(showSelected) {
       var objRadio = eval("document.all." + this.famSelObj.name + "_radio");
       if(objRadio)  // check the selected button
          objRadio[1].checked = true;
       reportCOS.clickFamilySearchMult(this.famSelObj);
       showSelect('family')
     }
  }

} //end class method RefreshValues()



/////////////////////////////////////////////////////
function SetHiddenValuesCOS(objHidFam,objHidEnv,objHidCom,objHidLab)
/////////////////////////////////////////////////////
{
   ////////////////////////////////////////////////////////////
   // clear the selection options if all radio buttion selected
   ////////////////////////////////////////////////////////////
   if(this.famSelObj)
   {
      var objRadio = eval("document.all." + this.famSelObj.name + "_radio");
      if(objRadio[0].checked)
        COS.clearAllSelectedCOS(this.famSelObj)
   }
  if(this.envSelObj)
  {
     var objRadio = eval("document.all." + this.envSelObj.name + "_radio");
     if(objRadio[0].checked)
        COS.clearAllSelectedCOS(this.envSelObj)
  }
  if(this.comSelObj)
  {
     var objRadio = eval("document.all." + this.comSelObj.name + "_radio");
     if(objRadio[0].checked)
        COS.clearAllSelectedCOS(this.comSelObj)
  }
  if(this.labSelObj)
  {
     var objRadio = eval("document.all." + this.labSelObj.name + "_radio");
     if(objRadio[0].checked)
       COS.clearAllSelectedCOS(this.labSelObj)
  }

  // do family
   if(this.famSelObj && objHidFam)
     COS.setHiddenValuesHelperCOS(this.famSelObj,objHidFam)
   // do environment
   if(this.envSelObj && objHidEnv)
     COS.setHiddenValuesHelperCOS(this.envSelObj,objHidEnv)
   // do company
   if(this.comSelObj && objHidCom)
     COS.setHiddenValuesHelperCOS(this.comSelObj,objHidCom)
   // do label
   if(this.labSelObj && objHidLab)
     COS.setHiddenValuesHelperCOS(this.labSelObj,objHidLab)
}

/////////////////////////////////////////////////////
function SetHiddenValuesHelperCOS(obj,objHidden)
/////////////////////////////////////////////////////
{
   objHidden.value = "";
   for(var i=0; i < obj.options.length; i++)
   {
      // if option is selected
      if(obj.options[i].selected)
      {
        var valueStr = obj.options[i].value;
        // skip all and null list
        if(valueStr != "0" && valueStr != "-1" && valueStr != "")
        {
          if(objHidden.value == "")
            objHidden.value = valueStr;
          else  // add | as a token delimiter
            objHidden.value = objHidden.value + "|" + valueStr;
        }
      }
   }

}
COS.setHiddenValuesHelperCOS = SetHiddenValuesHelperCOS; // make it a class variable

/////////////////////////////////
// clear all selected values
/////////////////////////////////
function ClearAllSelectedCOS(obj)
{
  // if object exists
  if(obj)
  {
    for(var i=0; i < obj.options.length; i++)
      obj.options[i].selected = false;
  }

}
COS.clearAllSelectedCOS = ClearAllSelectedCOS; // make it a class variable

/////////////////////////////////
// is the higher level checked
/////////////////////////////////
function IsUpLvlCheckedCOS(oName)
{
  // if object exists
  var obj = null;
  if(oName == "environment")
     obj = this.famSelObj;
  if(oName == "company")
     obj = this.envSelObj;
  if(oName == "Label")
     obj = this.comSelObj;
  if(obj)
  {
    //for(var i=0; i < obj.options.length; i++)
    //{
    //  if(obj.options[i].selected)
    //    return true;  // upper level option selected
    //}
    // faster way
    if(obj.selectedIndex != -1)
       return true;
  }
  // no upper level option selected
  return false;
}

