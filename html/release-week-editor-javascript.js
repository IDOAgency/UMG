/**
	* Milestone v2
	*
	* File: release-week-editor-javascript.js
	*/

function checkField( pField )
{
  var bReturn = true;
  switch( pField.name )
	{
    case "StartDate":
      bReturn = bReturn && checkDate( pField, "Start Date", true );
      break;
    case "EndDate":
      bReturn = bReturn && checkDate( pField, "End Date", true );
      break;
  }
  return bReturn;
}//end function checkField()

function checkPage()
{
  if( !checkMandatoryField( document.all.theName, "Name" ) )
	{
    return false;
  }
  if( !checkMandatoryField( document.all.Cycle, "Cycle" ) )
	{
    return false;
  }
  if( !checkMandatoryField( document.all.StartDate, "Start Date" ) )
	{
    return false;
  }
  if( !checkMandatoryField( document.all.EndDate, "End Date" ) )
	{
    return false;
  }
  if( !checkField( document.all.StartDate ) )
	{
    return false;
  }
  if( !checkField( document.all.EndDate ) )
	{
    return false;
  }
  return true;
}//end function checkPage()

function submitSearch()
{
  toggle( 'SearchLayer', 'NameSearch' );
  document.all.StartDateSearch.value = convertDate( document.all.StartDateSearch.value );
  document.all.EndDateSearch.value = convertDate( document.all.EndDateSearch.value );
  document.forms[0].cmd.value = search;
  showWaitMsg(); // mc its 966 show please wait message on screen
  document.forms[0].submit();
}//end function submitSearch()

function submitNew()
{
  document.forms[0].cmd.value = editNew;
  showWaitMsg(); // mc its 966 show please wait message on screen
  document.forms[0].submit();
}//end function submitNew()

function submitSave( imagePrefix )
{
  if( checkPage() )
	{
   	if(document.forms[0].cmd.value == editNew)
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
}//end function submitSave()

function submitDelete( imagePrefix )
{
  if( confirm( "You are about to delete a Release Week record. Confirm?" ) )
	{
          showWaitMsg(); // mc its 966 show please wait message on screen
          parent.top.bottomFrame.location = deleteCommand;
  }
  else
	{
    eval( 'mtb' + imagePrefix ).reset();
  }
}//end function submitDelete()

function submitList( pType )
{
  document.forms[0].OrderBy.value = pType;
  document.forms[0].cmd.value = sort;
  showWaitMsg(); // mc its 966 show please wait message on screen
  document.forms[0].submit();
}//end function sumitList()

function submitGet( pName )
{
	parent.top.bottomFrame.location = editor + "&releaseweek-id=" + pName;
}//end function submitGet()

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
  toggle( 'SearchLayer', 'NameSearch' );
} //end function toggleSearch()
