
function checkField( pField ) 
{
  var bReturn = true;
  return bReturn;
} // end function checkField()

function checkPage() 
{
  if (document.forms[0].cmd.value == editNew)
	{
		if( !checkMandatoryField( document.all.Value, "Value" ) ) 
  	{
    	return false;
  	}
  	if( !checkMandatoryField( document.all.Description, "Description" ) ) 
  	{
    	return false;
  	}
  	return true;
	}
	else
	{
		return false;
	}
} //end function checkPage()

function submitSearch() 
{
  toggle( 'SearchLayer', 'FamilyDescriptionSearch' );
  document.forms[0].cmd.value = search;
  document.forms[0].submit();
} // end function submitSearch()

function submitNew() 
{
  // dispay a new table header
  //parent.top.bottomFrame.location = editNew;
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
      document.forms[0].submit();		
    }
  }
  else 
  {
    parent.top.bottomFrame.location = editor;
		eval( 'mtb' + imagePrefix ).reset();
  }
} //end function submitSave()

function submitDelete( imagePrefix ) 
{
  if( confirm( "You are about to delete a Table Detail record. Confirm?" ) ) 
  {
		parent.top.bottomFrame.location = deleteCommand;
  }
  else 
  {
    eval( 'mtb' + imagePrefix ).reset();
  }
} //end function submitDelete()

function submitList( pType ) 
{
  document.forms[0].submit(); 
} //end function submitList()

function submitGet( pId ) 
{
	parent.top.bottomFrame.location = editor + "&table-id=" + pId;
} //end function submitGet()

function submitGet( pId, pValue ) 
{
  //document.forms[0].Value.value = pValue;
	parent.top.bottomFrame.location = editor + "&table-id=" + pId;
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

function submitShow( pIsHeader ) 
{
  if( pIsHeader ) 
  {
  	parent.top.bottomFrame.location = editor;
  } 
  else 
  {
  	parent.top.bottomFrame.location = editorDetail;
  }
} //end function submitShow()

function toggleSearch() 
{
  toggle( 'SearchLayer', 'DescriptionSearch' );
} //end function toggleSearch()
