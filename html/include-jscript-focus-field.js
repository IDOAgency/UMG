/**
 * Milestone v2
 *
 * File: include-jscript-focus-field.js
 */

/**
 * Places input focus on a specific field.
 */
function focusField( pField ) 
{
  var check = eval( document.all[ pField ] );
  // Check if the given field is an object on the page
  if ( ( typeof check ) == "object" ) 
  {
    // Then go through all available elements in the form
    for ( i = 0; i < document.forms[0].elements.length; i++ )
    {
      // Check if it matches with the given field name
      if ( document.forms[0].elements[i].name == pField ) 
      {
        // And if this element is not of type hidden then it can take the focus
        if ( document.forms[0].elements[i].type != "hidden" ) 
        {
          // We set the focus in this case and return
          check.focus();
          return true;
        }
      }
    }  // end for
  }  // end if
      
  return true;
    
}  // End function focusField