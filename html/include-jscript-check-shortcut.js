/**
 * Milestone v2
 *
 * File: include-jscript-check-shortcut.js
 */

/*
 * Functions included in this file:
 * doSearch(), doCopy(); doSave(), doPrevious(), doNext(), checkShortcut(),
 * checkForKey(), checkForEnter(), getRadioValue() and pushValue()
 */

function doSearch()
{
  if ( typeof( mtbSearch ) == 'object' )
  {
    mtbSearch.click();
    toggleSearch();
  }
}

function doCopy()
{
  if ( typeof( mtbCopy ) == 'object' )
  {
    mtbCopy.click();
    submitCopy( 'Copy' );
  }
}

function doSave()
{
  if ( typeof( mtbSave ) == 'object' )
  {
    mtbSave.click();
    submitSave( 'Save' );
  }
}

function doPrevious()
{
  if ( typeof( mtbPrev ) == 'object' )
  {
    mtbPrev.click();
    submitPagging( false );
  }
}

function doNext()
{
  if ( typeof( mtbNext ) == 'object' )
  {
    mtbNext.click();
    submitPagging( true );
  }
}

function checkShortcut()
{
  var lEvent = window.event;
  if ( lEvent.ctrlKey )
  {
    switch( lEvent.keyCode )
    {
      case 68:
        if( typeof( doCopy ) == 'function' )
        {
          doCopy();
        }
        break;
      case 81:
        if( typeof( doSearch ) == 'function' )
        {
          doSearch();
        }
        break;
      case 83:
        if( typeof( doSave ) == 'function' )
        {
          doSave();
        }
        break;
      case 37:
        if( typeof( doPrevious ) == 'function' )
        {
          doPrevious();
        }
        break;
      case 39:
        if( typeof( doNext ) == 'function' )
        {
          doNext();
        }
        break;
      // Prevent CTRL+N from opening multiple windows of the same instance
      case 78:
        this.blur();
        alert( 'Open multiple windows of the same instance is not allowed!' );
        this.focus();
    }  // end switch
  }  // end lEvent
}  // End function checkShortcut

function checkForKey( pKeyCode, pMethod )
{
  if ( window.event.keyCode == pKeyCode )
  {
    if( window.event.ctrlKey )
    {
      alert( 'Got key: ' + pKeyCode + ' with CTRL key pressed' );
    }
  }
}  // End function checkForKey

function checkForEnter( pMethod )
{
  if( window.event.keyCode == 13 )
  {
    //pMethod();
    eval(pMethod);
  }
}  // End function checkForEnter

function getRadioValue( pRadioButton )
{
  var lsReturn = '';
  for( i = 0; i < pRadioButton.length; i++ )
  {
    if( pRadioButton[ i ].checked )
    {
      lsReturn = pRadioButton[ i ].value;
      break;
    }
  }
  return lsReturn;
}  // End function getRadioValue


function pushValue( pSource, pTarget )
{
  if( ( typeof pSource ) == "object" || ( typeof pTarget ) == "object" )
  {
    pTarget.value = pSource.value;
  }
}  // End function pushValue


// Sort select options object and remove duplicate names
function sortSelect (select, compareFunction) {

 ////////////////////////////////////////
 var isDuplicate = false;
 var z = -1;
 ///////////////////////////////////////

  if (!compareFunction)
    compareFunction = compareText;
  var options = new Array (select.options.length);
  for (var i=0; i < options.length; i++)
  {
     ////////////////////////////////////////
     // check for duplicate text names if label object
     isDuplicate = false;

     // msc for now only check for duplicates on Label object
     //alert("Select Name " + select.name);
     if(select.name.toUpperCase().indexOf("LABEL") != -1)
     {
       for(var x=0; x < (z + 1); x++)
       {
         if(options[x] != null)
         {
            if(options[x].text.toUpperCase() == select.options[i].text.toUpperCase())  // if duplicate found
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
         else
         {
            break;
         }
       }
     }

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
} // End function getRadioValue



