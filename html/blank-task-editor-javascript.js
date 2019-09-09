function checkField( pField ) 
{
	var bReturn = true;
  switch( pField.name ) 
	{
		case "weeks2Rel":
      bReturn = bReturn && checkFormat( pField.value, "Weeks To Release", "number", "" );
      break;
    case "weekAdjustment":
      bReturn = bReturn && checkInteger( pField.value, "Week Adjustment", -99, 99 );
      break;
    case "comments":
      bReturn = bReturn && checkLength( pField.value, "Comment", 0, 255 );
      break;
  }
  if( !bReturn ) 
	{
    pField.focus();
  }
  return bReturn;
} //end function checkField()

function checkPage() 
{
  
	if (document.forms[0].cmd.value == editNew)
	{
		if( !checkMandatoryField( document.all.taskName, "Task Name" ) ) 
		{
   		return false;
  	}
  	if( !checkMandatoryField( document.all.weeks2Rel,"Weeks to Release" ) ) 
		{
    	return false;
  	}
  	if( !checkField( document.all.weekAdjustment ) ) 
		{
    	return false;
  	}
  	if( !checkField( document.all.comments ) ) 
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
  toggle( 'searchLayer', 'taskNameSrch' );  
  //search
  document.forms[0].cmd.value = search;
  document.forms[0].submit();
} //end function submitSearch()

function submitNew() 
{  
  //to get a blank task editor
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
  if(confirm("You are about to delete a Task record. Confirm?")) 
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
  document.forms[0].OrderBy.value = pType;
  document.forms[0].cmd.value = sort;  
  document.forms[0].submit();
} //end function submitList()

function submitGet( pId ) 
{  
  parent.top.bottomFrame.location = editor + "&task-id=" + pId;
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
  toggle( 'searchLayer', 'taskNameSrch' );
} //end function toggleSearch()
