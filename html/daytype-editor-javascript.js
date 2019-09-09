function checkField( pField )
{
  var bReturn = true;
  switch( pField.name )
	{
    case "SpecialDate":
      bReturn = bReturn && checkDate( pField, "Special Date", true );
      break;
  }
  return bReturn;
} //end function checkField()

function checkPage()
{
  if( !checkMandatoryField( document.all.Group, "Group" ) )
	{
    return false;
  }
  if( !checkMandatoryField( document.all.SpecialDate, "Special Date" ) )
	{
    return false;
  }
  if( !checkField( document.all.SpecialDate ) )
	{
    return false;
  }
  return true;
} //end function checkPage()


function submitSearch()
{
  toggle( 'SearchLayer', 'GroupSearch' );
  document.all.SpecialDateSearch.value = convertDate( document.all.SpecialDateSearch.value );
   document.forms[0].cmd.value = search;
   showWaitMsg(); // mc its 966 show please wait message on screen
   document.forms[0].submit();

} //end function submitSearch()

function submitNew()
{
	document.forms[0].cmd.value = editNew;
	document.forms[0].submit();
} //end function submitNew()

function submitSave( imagePrefix )
{
 	if( checkPage() )
	{
   	if (document.forms[0].cmd.value == editNew)
		{
			document.forms[0].cmd.value = saveNew;
            showWaitMsg(); // mc its 966 show please wait message on screen
	    document.forms[0].submit();
		}
		else
		{
			document.forms[0].cmd.value = save;
            showWaitMsg(); // mc its 966 show please wait message on screen
 	    document.forms[0].submit();
		}
  }
  else
	{
    eval( 'mtb' + imagePrefix ).reset();
  }


} //end function submitSave()

function submitDelete( imagePrefix )
{
  if( confirm( "You are about to delete a Day Type record. Confirm?" ) )
	{
          showWaitMsg(); // mc its 966 show please wait message on screen
          parent.top.bottomFrame.location = deleteCommand;
  }
  else
	{
    eval( 'mtb' + imagePrefix ).reset();
  }
} //end function submitDelete()

function submitList( pType )
{
  document.forms[0].OrderBy.value = pType;
  document.forms[0].cmd.value = sort;
  showWaitMsg(); // mc its 966 show please wait message on screen
  document.forms[0].submit();

} //end function submitList()

function submitGet( pId )
{
	parent.top.bottomFrame.location = editor + "&day-id=" + pId;
} //end function submitGet()

function submitPagging( pForward )
{
  if( pForward )
  {
  	parent.top.bottomFrame.location = next + "&notepadType=" + notepad + "&lastCommand=" + cmd;
  }
  else
  {
  	parent.top.bottomFrame.location = previous + "&notepadType=" + notepad + "&lastCommand=" + cmd;
  }
}//end function submitPagging()

function toggleSearch()
{
  toggle( 'SearchLayer', 'GroupSearch' );
}// end function toggleSearch()
