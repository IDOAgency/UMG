/**
 * Milestone v2
 *
 * File: report-config-editor-javascript.js
 */

function checkField( pField )
{
  var bReturn = true;
  return bReturn;
} //end function checkField()

function checkPage()
{
  if (document.forms[0].cmd.value == editNew)
	{
		if( !checkMandatoryField( document.all.ReportName, "ReportName" ) )
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
  toggle( 'SearchLayer', 'GroupSearch' );
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
  if( confirm( "You are about to delete a Report record. Confirm?" ) )
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
  parent.top.bottomFrame.location = editor + "&report-config-id=" + pId;
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
} //end function submitPagging()

function showDescription( pMessage )
{
  alert( pMessage );
} //end function showDescription()

function toggleSearch()
{
  toggle( 'SearchLayer', 'ReportNameSearch' );
} //end function toggleSearch()

// JR - ITS #73
function isConfigSelected()
{
	// if config is not selected then unselect subconfig and raise an alert
	if (!document.all("Configuration").checked)
	{
		var txtMessage = "In order to filter by subconfiguration, you must first select ";
		txtMessage += "configuration as a filter.\n Click 'OK' if you would like to ";
		txtMessage += "select configuration at this time.";

		if (confirm(txtMessage))
			document.all("Configuration").checked = true;
		else
			document.all("Subconfiguration").checked = false;
	}
	// if config is selected do nothing
}

// JR - ITS #73
function isSubconfigSelected()
{
	// if config is unselected then unselect subconfig as well
	if (!document.all("Configuration").checked &&
		document.all("Subconfiguration").checked )
	{
		document.all("Subconfiguration").checked = false;
	}
	// if config is selected do nothing
}
