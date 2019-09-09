/**
 * Milestone v2
 *
 * File: include-jscript-check-length.js
 */

/**
 * Determines if a field's string value is correctly sized between
 * provided size minimum and maximums.
 */
function checkLength( pValue, pName, pMinimum, pMaximum )
{
  if ( pMinimum > 0 )
  {
    if ( pValue == null ) return false;
    if ( pMinimum > pValue.length )
    {
      alert( "Field: " + pName + " is too short\n\nMust be at least: " + pMinimum + " character(s)" );
      return false;
    }
  }
  if ( pMaximum > 0 && pMaximum > pMinimum )
  {
    if ( pValue == null ) return true;
    if ( pMaximum < pValue.length )
    {
      alert( "Field: " + pName + " is too long (" + pValue.length +  ")\n\nMust not exceed: " + pMaximum + " characters" );
      return false;
    }
  }

  return true;

}  // End function checkLength