function checkField( pField )
{
  var bReturn = true;
  return bReturn;
} //end function checkField()

function submitGet( pId )
{
  parent.top.bottomFrame.location = editor + "&company-id=" + pId;
} //end function submitGet()


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
} //end function submitSearch()

function submitNew()
{
  document.forms[0].cmd.value = editNew;
	document.forms[0].submit();
} //end function submitNew()

function submitList( pType )
{
  document.forms[0].OrderBy.value = pType;
  document.forms[0].cmd.value = sort;
  document.forms[0].submit();
} //end function submitList()


