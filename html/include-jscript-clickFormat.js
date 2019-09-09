/**
 * Milestone v2
 *
 * File: include-jscript-clickFormat.js
 */

////////////////////////////////////////////////////////////
// Define the Format/Schedule Type Drop Down Object
///////////////////////////////////////////////////////////

////////////////////////////////////
// The constructure for the class
////////////////////////////////////
function Format(iFormatObj,iSubFormatObj)
{

  // store the levels
  this.formatObj = iFormatObj;
  this.subFormatObj = iSubFormatObj;
  this.refreshAll = false;
  this.refreshMAC = false;
  this.isMAC = false;
  // determine if a MAC
  var navCom = navigator.platform;
  if( navCom.toUpperCase() == "MACPPC" || navCom.substr(0,3).toUpperCase() == "MAC" )
    this.isMAC = true;

  // Prototype the class methods
  if(!Format.prototype.ClickFormat)
  {
     Format.prototype.clickFormat = ClickFormat;
     Format.prototype.populateSubFormat = PopulateSubFormat;
     Format.prototype.removeSelectFormat = RemoveSelectFormat;
     Format.prototype.setHiddenValuesFormat = SetHiddenValuesFormat;
     Format.prototype.clickCheckFormat = ClickCheckFormat;
     Format.prototype.selectAllFormat = SelectAllFormat;
     Format.prototype.buildLowerLevelsAllFormat = BuildLowerLevelsAllFormat;
  }

  // save the levels original option opbjects for refreshing all
  this.subFormatObjAll = new Object();
  this.subFormatObjAll.options = new Array();
  // because the sub formats are not built from the server
  // this function is performed by the jsp page using this object
  //if(this.subFormatObj)
  //  Format.populateChildFromAllFormat(this.subFormatObjAll,this.subFormatObj);


} //end if Format.constructor

///////////////////////////////////////////////
function ClearCombo(obj)
///////////////////////////////////////////////
{
  obj.options.length = 0;
} //end class method clearCombo()
Format.clearCombo = ClearCombo;  // make it a class method

/////////////////////////////////////////////////
function ClickFormat(obj)
/////////////////////////////////////////////////
{
  if(this.subFormatObj)
  {
    Format.clearCombo(this.subFormatObj);
    this.populateSubFormat(this.subFormatObj);
  }

  return true;

} // end function Format.clickFormat()

/////////////////////////////////////////////////////
function PopulateChildFromAllFormat(obj, objAll)
/////////////////////////////////////////////////////
{
   // clear the target object
   obj.options.length = 0;
   // build the target from the saved all selection object
   for(var i=0; i < objAll.options.length; i++)
       obj.options[i] = new Option(objAll.options[i].text, objAll.options[i].value);

} //end function populateEnvironment()
Format.populateChildFromAllFormat = PopulateChildFromAllFormat;



/////////////////////////////////////////////////////
function PopulateSubFormat(obj)
/////////////////////////////////////////////////////
{

   if (obj == null)
     return;

   obj.selectedIndex = 0;
   var count = 0;

   //for each of the configs chosen
   var selectedConfig = 0;
   for(k=0; k < this.formatObj.options.length; k++)
   {
     currentConfig = this.formatObj.options[k];

     // if option selected
     if(currentConfig.selected || this.refreshAll)
     {

       //get the index by iterating through configsProdType
       //looking for the selected code
       for (j = 1; j < configsProdType.length; j++)
       {
         selectedConfig = j;
         s1 = configsProdType[selectedConfig];
         s2 = s1.substring(0, s1.indexOf(","));
         s3 = s1.substring(s1.indexOf(",")+1);
         s3 = s3.substring(0, s3.indexOf(","));
         // if match found
         if ( currentConfig.value == s3)
         {
           for(i=1; i < configs[selectedConfig].length; i++)
           {
             s1 = configs[selectedConfig][i];
             s2 = s1.substring(0, s1.indexOf(","));
             s3 = s1.substring(s1.indexOf(",")+1);
             s4 = s3.substring(s3.indexOf(",")+1);
             s4 = s4.substring(0,s4.indexOf(",")); // JP 9/30/03

             s3 = s3.substring(0, s3.indexOf(","));
             if(s3 != "" && s3 != "0")  // skip blank and all values
             {
               //s2 = "[" + currentConfig.value + "]" + s2;
               s3 = "[" + currentConfig.text  + "] " + s3;
               obj.options[count] = new Option(s3,s2);
               count++;
             }
           }

            // stop this loop
            break;
         }
       }
     }
   }//for each of the subconfigs chosen

   //obj.length = count;

  this.refreshAll = false;

} //end function populateSubFormat()
//Format.populateSubFormat = PopulateSubFormat; // make it a class variable


//////////////////////////////////////////////////////
// restore the values of the search elements
function SetValuesIndex(obj,valueStr)
//////////////////////////////////////////////////////
{

 // if null object
  if(obj == null || obj.options.length == 0)
    return;

  // return if empty value
  if(valueStr == "" || valueStr == "0" || valueStr == "-1" )
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
Format.setValuesIndex = SetValuesIndex; // make it a class variable


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
    for(var i=0; i < opts.length; i++)
    {
       opts[i].selected = false;
    }
    opts[0].selected = true;
  }
}
Format.clearSelectedValue = ClearSelectedValue; // make it a class variable

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
Format.clearSelectedValueStr = ClearSelectedValueStr; // make it a class variable



///////////////////////////////////////////
// Check Box functions
//////////////////////////////////////////
function ShowSelect(oName)
{
   var obj = eval("document.all." + oName + "_Div");
   var objParent = eval("document.all." + oName);

   // add all check box
   var htmlStr = "<table width=500>";
   if(objParent.options.length > 0)
   {
      htmlStr += "<tr ><td width=20 colspan=1>";
      htmlStr += "<input type=checkbox id=" + oName + "_All" + " type=checkbox" + " onclick=reportFormat.selectAllFormat('" + oName + "')>All";
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
     if(oValue == "0" || oValue == "-1" || oValue == "" || oValue == "0,0"
        || oText == "All" )
       continue;

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

     htmlStr += "<td id=" + oID + "_TD " + styleStr + " width=125 colspan=1>";
     htmlStr += "<input type=checkbox id="
               + oID + " name=" + oID + " onclick=reportFormat.clickCheckFormat('" + oName
               + "','" + oValue + "',this) " + checkedStr + ">" + oText;
     htmlStr += "</td>";

     c++;
     if (c % 4 == 0)
     {
       c=0;
       htmlStr += "</tr><tr valign=top >";
     }

   }

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
Format.showSelect = ShowSelect; // make it a class variable

function ClickCheckFormat(oName,oValue,cbObj)
{

  var obj = eval("document.all." + oName + "_All");
  obj.checked = false;

    var objParent = eval("document.all." + oName);
  if(objParent)
   {

     // set the selection
     var tdObj;
     if(cbObj.checked)
     {
       // The MAC has an issue with the refresh processing
       // Do this only if not a MAC or and a refresh has not been executed
       if(!this.isMAC)
       {
         tdObj = eval(cbObj.id + "_TD");
         if(tdObj)
         {
           if(tdObj.style)
             tdObj.style.color = "red";
           else
             tdObj[0].style.color = "red";
         }
       }
       Format.setValuesIndex(objParent,oValue);
     } else {
       // The MAC has an issue with the refresh processing
       // Do this only if not a MAC or and a refresh has not been executed
       if(!this.isMAC)
       {
         tdObj = eval(cbObj.id + "_TD");
         if(tdObj)
         {
           if(tdObj.style)
             tdObj.style.color = "black";
           else
             tdObj[0].style.color = "black";
         }
       }
       Format.clearSelectedValueStr(objParent,oValue);
     }

     // build the lower level drop downs
     Format.buildLowerLevels(oName);

  }
}
//Format.clickCheckFormat = ClickCheckFormat // make it a class variable

///////////////////////////////////////////////////////
function BuildLowerLevels(oName)
///////////////////////////////////////////////////////
{

  var objParent = eval("document.all." + oName);
  if(objParent)
   {
     // format check box clicked
     if(oName == "configurationList")
       {
       // update the dropdown box
       reportFormat.clickFormat(objParent);

       // show the check boxes
       if(document.all.subconfigurationList && document.all.subconfigurationList_radio[1].checked)
          Format.showSelect("subconfigurationList");
     }

   }
}
Format.buildLowerLevels = BuildLowerLevels; // make it a class variable

///////////////////////////////////////////////////////
function BuildLowerLevelsAllFormat(oName)
///////////////////////////////////////////////////////
{

  var objParent = eval("document.all." + oName);
  if(objParent)
   {
     // format check box clicked
     if(oName == "configurationList")
       {
       // update the dropdown boxes using the saved all objects options
       if(this.subFormatObj)
         Format.populateChildFromAllFormat(this.subFormatObj,this.subFormatObjAll);

       // show the check boxes
       if(document.all.subconfigurationList && document.all.subconfigurationList_radio[1].checked)
          Format.showSelect("subconfigurationList");
     }

   }
}


function RemoveSelectFormat(oName)
{
   document.body.style.cursor="wait";

   // clear the selected options
   this.refreshAll = true;   // forces rebuild of lower levels
   this.refreshMAC = true;  // this variable was added to resolve a MAC issue
   var obj = eval("document.all." + oName);
   Format.clearAllSelectedFormat(obj); // clear all selected options
   this.buildLowerLevelsAllFormat(oName);      // rebuild the lower levels
   this.refreshAll = false;


   var obj = eval("document.all." + oName + "_Div");
   obj.style.display = "none";

   document.body.style.cursor="default";
}
//Format.removeSelectFormat = RemoveSelectFormat; // make it a class variable

function SelectAllFormat(oName)
{
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

           // format check box clicked
           var objParent = null;
           if(oName == "configurationList")
              objParent = eval("document.all.configurationList");
           if(oName == "subconfigurationList")
              objParent = eval("document.all.subconfigurationList");

           // get the value from the checkbox id
           var pos = elements[i].id.indexOf("_");
           if(pos == -1) // if not found
             continue;

           var oValue = elements[i].id.slice(pos + 1);

           // set the selection
           if(elements[i].checked)
              Format.setValuesIndex(objParent,oValue);
           else
              Format.clearSelectedValueStr(objParent,oValue);

       }
   }

   // build the lower level drop downs
   if(obj.checked)
     this.buildLowerLevelsAllFormat(oName);  // build lower levels from the all objects
   else
     Format.buildLowerLevels(oName);  // build lower levels from the click methods

   document.body.style.cursor="default";
   obj.style.cursor="default";

}
//Format.selectAllFormat = SelectAllFormat; // make it a class variable


/////////////////////////////////////////////////////
function SetHiddenValuesFormat(objHidFmt,objHidSubFmt)
/////////////////////////////////////////////////////
{

   ////////////////////////////////////////////////////////////
   // clear the selection options if all radio buttion selected
   ////////////////////////////////////////////////////////////
   if(this.formatObj)
   {
      var objRadio = eval("document.all." + this.formatObj.name + "_radio");
      if(objRadio[0].checked)
        Format.clearAllSelectedFormat(this.formatObj)
   }
   if(this.subFormatObj)
   {
      var objRadio = eval("document.all." + this.subFormatObj.name + "_radio");
      if(objRadio[0].checked)
        Format.clearAllSelectedFormat(this.subFormatObj)
   }


   // do Format
   if(this.formatObj && objHidFmt)
     Format.setHiddenValuesHelperFormat(this.formatObj,objHidFmt)
   // do sub-Format
   if(this.subFormatObj && objHidSubFmt)
     Format.setHiddenValuesHelperFormat(this.subFormatObj,objHidSubFmt)
}

/////////////////////////////////////////////////////
function SetHiddenValuesHelperFormat(obj,objHidden)
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
Format.setHiddenValuesHelperFormat = SetHiddenValuesHelperFormat; // make it a class variable


/////////////////////////////////
// clear all selected values
/////////////////////////////////
function ClearAllSelectedFormat(obj)
{
  // if object exists
  if(obj)
  {
    for(var i=0; i < obj.options.length; i++)
      obj.options[i].selected = false;
  }

}
Format.clearAllSelectedFormat = ClearAllSelectedFormat; // make it a class variable


