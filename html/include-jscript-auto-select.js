/*autoSelect.js
 Functions to be used for autofiltering Select boxes.
 7/29/2002 - JO - Created
*/
/////////////////////////////////////////////////////////////////////////////////////////////
var scan = new String();   // variable to store key strokes
var setInt = false;        // Interval set flag
var setIntId;              // Interval function Identifier
/////////////////////////////////////////////////////////////////////////////////////////////
// This function calculates the max length of
// strings in the select
/////////////////////////////////////////////////////////////////////////////////////////////
function getMaxLength(currentOptions){

 maxlength = 0;
 var currentValue = 0;
  // loop through the array
  for(var i=0; i < currentOptions.length; i++ )
  {
       //alert("value of field:[" + currentOptions.options[i].value + "]");
	currentValue = currentOptions.options[i].value.length;
	if( currentValue > maxlength ){
		maxlength = currentValue
	}
  }
return maxlength;
}

/////////////////////////////////////////////////////////////////////////////////////////////
// This function stores the key strokes and calls the function to
// do the string comparison
/////////////////////////////////////////////////////////////////////////////////////////////
function scanKey(pKey, currentOptions, maxlength)
{

  if(pKey == 13)
  	 return false;

scan += String.fromCharCode(pKey);   // store key entered

  // set call of scan function to 1 second later
  if( !setInt ) {
    setInt = true;
 setIntId = setInterval("doScan(" + currentOptions + ",  " + maxlength + " )", 1000);  // execute scan function 1 second later
 //alert("doScan(" + field.value + ",  " + maxlength + " )");
 //setIntId = setInterval("doScan(" + field + ",  " + maxlength + " )", 1400);  // execute scan function 1 second later
  }
  window.event.returnValue = false;   // stop execution of default event handler

 return false;
}


/////////////////////////////////////////////////////////////////////////////////////////////
// This function compares the key stroke string to the select options
// and establishes the found option as the selection option
/////////////////////////////////////////////////////////////////////////////////////////////
function doScan(field, maxlength)
{

  // loop through the array
  for(var i=0; i < field.options.length; i++ )
  {

//alert("trying to match[" + scan.substr(0,scan.length).toUpperCase().substr(0,maxlength) + "]");
//alert("current value[" + field.options[i].text.substr(0,scan.length).toUpperCase() + "]");

	if(scan.substr(0,scan.length).toUpperCase().substr(0,maxlength) == field.options[i].text.substr(0,scan.length).toUpperCase() )
	{
		field.value = field.options[i].value;  // set the selected options value
		var opt = field.options[i];
		opt.selected = true;
		field.focus();

		break;  // stop the loop
	}
  }

  clearInterval(setIntId);   // stop executing the this function
  setInt = false;            // allow calling program to call this function again
  scan = "";                 // clear the key stoke buffer

 return true;
}
//////////////////////////////