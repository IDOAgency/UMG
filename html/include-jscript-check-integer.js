/**
 * Milestone v2
 *
 * File: include-jscript-check-integer.js
 */

/**
 * Determines if a field's integer value is correctly bounded by a 
 * provided minimum and maximum.
 */
function checkInteger( pValue, pName, pMinimum, pMaximum ) 
{
  if ( !checkFormat( pValue, pName, "integer", "" ) ) 
  {
    return false;
  } 
  else 
  {
    var lNumber = 0 + pValue;
    if ( pMinimum <=  pMaximum && lNumber < pMinimum ) 
    {
      alert( "Field: " + pName + " contains a number less than the minimum: " + pMinimum );
      return false;
    }
    if ( lNumber > pMaximum ) 
    {
      alert( "Field: " + pName + " contains a number greater than the maximum: " + pMaximum );
      return false;
    }
  } //end if/else

  return true;

}  // End function checkInteger
