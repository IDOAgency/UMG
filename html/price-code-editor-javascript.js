/**
 * Milestone v2
 *
 * File: price-code-editor-javascript.js
 */

function toggleSearch()
{
	toggle( 'SearchLayer', 'SellCodeSearch' );
} //end function toggleSearch

function checkField( pField )
{
  var bReturn = true;
  switch( pField.name )
	{
    case "SellCode":
      bReturn = bReturn && checkMandatoryField( pField, "Sell Code" );
      break;
  }
  return bReturn;
} //end function checkField()

function checkPage()
{

	if (document.forms[0].cmd.value == editNew)
	{
		if( !checkField( document.all.SellCode ) )
		{
    	return false;
  	}
		else
		{
			return true;
		}
	}
	else
	{
  	return true;
	}
} //end function checkPage()

function submitSearch()
{
  toggle( 'SearchLayer', 'NameSearch' );
  document.forms[0].cmd.value = search;
  showWaitMsg(); // mc its 966 show please wait message on screen
  document.forms[0].submit();
} //end function submitSearch()

function submitNew()
{
  document.forms[0].cmd.value = editNew;
  showWaitMsg(); // mc its 966 show please wait message on screen
  document.forms[0].submit();
} //end function submitNew()

function submitSave( buttonName )
{
  if( checkPage() )
	{
		if (document.forms[0].cmd.value == editNew)
		{
			document.forms[0].cmd.value = saveNew;
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
    eval( 'mtb' + buttonName ).reset();
  }
} //end function submitSave()

function submitDelete( buttonName )
{
  if( confirm( "You are about to delete a Price Code record. Confirm?" ) )
	{
            showWaitMsg(); // mc its 966 show please wait message on screen
            parent.top.bottomFrame.location = deleteCommand;
  }
  else
	{
    eval( 'mtb' + buttonName ).reset();
  }
} //end function submitDelete()

function submitList( pType )
{
  document.forms[0].OrderBy.value = pType;
  document.forms[0].cmd.value = sort;
  showWaitMsg(); // mc its 966 show please wait message on screen
  document.forms[0].submit();
} //end function submitList()

function submitGet( pSellCode, isDigital )
{
	parent.top.bottomFrame.location = editor + "&pricecode-id=" + pSellCode;
	// + "&pricecode-isDigital=" + isDigital;
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
