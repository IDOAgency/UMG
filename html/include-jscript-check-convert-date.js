/**
 * Milestone v2
 *
 * File: include-jscript-check-convert-data.js
 */

function convertDate( pDate )
{
  pDate = pDate.replace( /-/g, "/" );
  pDate = pDate.replace( /\./g, "/" );
  return pDate;
} // End convertDate

function checkDate( pField, pName, pCanBeEmpty )
{
  if( pCanBeEmpty && pField.value.length == 0 )
  {
    return true;
  }
  var lValue = convertDate( pField.value );
  if( checkFormat( lValue, pName, "date", "" ) )
  {
    if( pField.value != lValue )
    {
      pField.value = lValue;
    }
    return true;
  }
  else
  {
    return false;
  }

} // End checkDate

  //ITS 1023 - jo - Remove spaces within dates
  function removeSpacesInDate(str,pName){
       pName.value = pName.value.replace(/\s/g, ""); //strip spaces from field value
       str = str.replace(/\s/g, ""); //strip spaces from input string
  }
