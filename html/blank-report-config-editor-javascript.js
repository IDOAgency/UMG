/**
 * Milestone v2
 *
 * File: report-config-editor-javascript.js
 */

function submitSearch() 
{
  toggle( 'SearchLayer', 'GroupSearch' );
  document.forms[0].cmd.value = search;
  document.forms[0].submit();
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
