/**
 * Milestone v2
 *
 * File: include-jscript-check-upc-ssg-sgc.js
 */

/**
 * Determines if a field's string value is correctly sized between
 * provided size minimum and maximums.
 */
var ISRC_minimum = 12;
var ISRC_maximum = 12;
var UPC_minimum = 12;
var UPC_maximum = 14;

function checkLengthUPC( pValue, pName, pMinimum, pMaximum, strType, isDigital, isRequired )
{
   // if not a required field
  if(pValue.length == 0 && !isRequired)
    return true;

  // Digital product allowable sales grouping codes, UPC/ISRC
  // if digital product type and string type is SSG(Soundscan group)
  // check to see if this is an ISRC sting
  // if ISRC string change parameter values
  if(isDigital && strType == "SSG")
  {
    if(isISRC(pValue,isDigital))
    {
      strType = "ISRC";  // string type
      pName = "ISRC entered in the Sales Grouping Code field";    // parameter name
      pMinimum = ISRC_minimum;     // Minimum length for ISCR code
      pMaximum = ISRC_maximum;     // Maximum length for ISCR code
    }
    else
    {
      strType = "UPC";  // string type
      pName = "UPC entered in the Sales Grouping Code field";    // parameter name
      pMinimum = UPC_minimum;     // Minimum length for ISCR code
      pMaximum = UPC_maximum;     // Maximum length for ISCR code
    }
  }

  var strLen = 0;
  // compute the string length without special characters
  for(var i=0; i < pValue.length; i++)
  {
    // if UPC or Soundscan group code, numbers only allowed
    if(strType == "UPC" || strType == "SSG")
    {
       // increment the string length if numeric character
       // and not dash or spaces
       if( !isNaN( pValue.substr(i,1)) &&  pValue.substr(i,1) != "-"
           && pValue.substr(i,1) != " " )
         strLen += 1;
    }
    else // if Sales grouping code (ISRC), alphanumeric allowed
    {
       // increment the string length if not a dash or space
       if(pValue.substr(i,1) != "-" && pValue.substr(i,1) != " ")
         strLen += 1;
    }
 }
  // check minimum length
  if ( pMinimum > 0 )
  {
    if ( pValue == null ) return false;
    if ( pMinimum > strLen )
    {
       if(strType == "UPC" || strType == "SSG")
          alert(pName + " must be at least " + pMinimum + " digits" );
       else
          alert(pName + " must be " + pMinimum + " characters" );
      return false;
    }
  }

  // check maximum length
  if ( pMaximum > 0 && pMaximum >= pMinimum )
  {
    if ( pValue == null ) return true;
    if ( pMaximum < strLen )
    {
      if(strType == "UPC" || strType == "SSG")
        alert(pName + " must not exceed " + pMaximum + " digits" );
      else
        alert(pName + " must be " + pMinimum + " characters" );
      return false;
    }
  }

  return true;
}
// End function checkLengthUPC

//////////////////////////////////////////
// Allow numbers 0 thru 9 only
//////////////////////////////////////////
function checkNumbersOnly( pKey )
{
  // alert("Key pressed " + pKey + " Character code " + String.fromCharCode(pKey))

  // allow numbers 0 thru 9, dashes or spaces only
  if((pKey >= 48 && pKey <= 57) || String.fromCharCode(pKey) == '-'
        || String.fromCharCode(pKey) == ' ')
    //window.event.returnValue = true msc this statement doesnot work on the MAC
    return true;
  else
    //window.event.returnValue = false;  msc this statement doesnot work on the MAC
    return false;
}
// End function checkNumbersOnly


 /**
  * Validates a digital products Sales Grouping Code
  * Is it a UPC code or an ISRC number
  * @param inputString
  * @param isDigital
  * @return true if ISRC number
 */
function isISRC(pValue, isDigital)
{

  // validate input string, if not valid return false
  if(pValue == null || pValue.length < 2)
    return false;

  // if digital product check for UPC/ISRC
  if(isDigital)
  {
    // if first two characters are not digits flag as ISRC string
    if( isNaN(pValue.substr(0,1)) && isNaN(pValue.substr(1,1)) )
      return true;
  }

  // return variable
  return false;
}




