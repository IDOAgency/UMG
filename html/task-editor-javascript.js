function checkField( pField )
{
	var bReturn = true;
  switch( pField.name )
	{
		case "weeks2Rel":
      if (pField.value.toUpperCase() != "SOL")
        bReturn = bReturn && checkFormat( pField.value, "Weeks To Release", "numberCond", "0" );
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
  if( !checkMandatoryField( document.all.taskName, "Task Name" ) )
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

  if( !checkField( document.all.weeks2Rel,"Weeks to Release" ) )
	{

			if( ! checkDayField( document.all.dayOfWeek,"Day of Week" ) )
			{
				  alert( "Field: day of week   must be selected" );
				 return false;
			}
			else
			{	// alert( "Field: day of week   must be selected" );
				return true;
			}
			if(document.all.weeks2Rel == null ) return true;


    return false;
  }


  return true;
} //end function checkPage()



function submitSearch()
{
  toggle( 'searchLayer', 'taskNameSrch' );
  //search
  form.cmd.value = search;
  showWaitMsg(); // mc its 966 show please wait message on screen
  form.submit();
} //end function submitSearch()

function submitNew()
{
  //to get a blank task editor
  form.cmd.value = editNew;
  showWaitMsg(); // mc its 966 show please wait message on screen
  form.submit();
} //end function submitNew()

function submitSave( imagePrefix )
{
  var okToSave = true;

  if (multipleMessage.length > 0)
    okToSave = confirm(multipleMessage);

  if( checkPage() && okToSave)
	{
    if (form.cmd.value == editNew)
		{
			form.cmd.value = saveNew;
        showWaitMsg(); // mc its 966 show please wait message on screen
    	form.submit();
		}
		else
		{
   		form.cmd.value = save;
        showWaitMsg(); // mc its 966 show please wait message on screen
    	form.submit();
		}
  }
  else
	{
    if (imagePrefix != '')
    {
      eval( 'mtb' + imagePrefix ).reset();
    }
  }
} //end function submitSave()

function submitDelete( imagePrefix )
{
  if(confirm("You are about to delete a Task record. Confirm?"))
  {
        showWaitMsg(); // mc its 966 show please wait message on screen
	parent.top.bottomFrame.location = deleteCommand;
  }
  else
	{
    eval( 'mtb' + imagePrefix ).reset();
  }
} //end function submitDelete()

function submitCopy( imagePrefix )
{
 var okToSave = true;

      if (form.cmd.value == copy)
		{
			form.cmd.value = copy;
        showWaitMsg(); // mc its 966 show please wait message on screen
    	form.submit();
		}
		else
		{
   		form.cmd.value = copy;
        showWaitMsg(); // mc its 966 show please wait message on screen
    	form.submit();
		}
    if (imagePrefix != '')
    {
      eval( 'mtb' + imagePrefix ).reset();
    }

} //end function submitCopy()

function submitList( pType )
{
  form.OrderBy.value = pType;
  form.cmd.value = sort;
  showWaitMsg(); // mc its 966 show please wait message on screen
  form.submit();
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

function saveComment()
{
  document.forms[0].cmd.value = save;
  showWaitMsg(); // mc its 966 show please wait message on screen
  document.forms[0].submit();
} //end function saveComment()
