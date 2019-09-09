/*
function submitResize()
{
	// put the right command to resize the notepad
	parent.top.bottomFrame.location = editor;
} //end function submitResize()
*/

function submitGet( pId )
{
  parent.top.bottomFrame.location = editor + "&family-id=" + pId;
} //end function submitGet()


function checkField( pField )
{
  var bReturn = true;
  return bReturn;
} //end function checkField()

function submitSearchWithEnter()
{
	if( window.event.keyCode != 13 )
	{
  	return;
	}
  else
	{
		// do nothing
  }
   toggle( 'SearchLayer', 'FamilyDescriptionSearch' );
   document.forms[0].cmd.value = search;
   document.forms[0].submit();

} // end function submitSearchWithEnter()

function submitSearch()
{
  if( document.all[ 'SearchLayer' ].style.visibility == "visible" )
	{
    toggle( 'SearchLayer', 'FamilyDescriptionSearch' );
   document.forms[0].cmd.value = search;
   document.forms[0].submit();
  }
} // end function submitSearch()

function submitNew()
{
	document.forms[0].cmd.value = editNew;
	document.forms[0].submit();
} //end function submitNew()

function submitList( pType )
{
	parent.top.bottomFrame.location = editor;
} //end function submitList()

