/**
 * Milestone v2
 *
 * File: template-editor-javascript.js
 */

function checkField( pField )
{
  var bReturn = true;
  switch( pField.name )
  {
    case "":
      bReturn = bReturn && checkFormat( pField.value, pField.name, "number", "");
      break;
  }
  if( !bReturn )
  {
    pField.focus();
  }
  return bReturn;
}

function checkMandatoryListField( pField, pName )
{
  if( !checkList( pField.value, pName) )
  {
    pField.focus();
    return false;
  }
  return true;
}

function checkList( pValue, pName)
{
    if( pValue == null ) return false;
    if( pValue < 1)
    {
      alert( pName + " field can not be blank"  );
      return false;
    }
      return true;
}

function checkPage()
{
  if( !checkMandatoryField( document.all.templateName, "Template Name" ) )
  {
    return false;
  }
  else if( !checkMandatoryListField( document.all.owner, "Owner" ) )
  {
    return false;
  }
  else if( !checkMandatoryListField( document.all.productLine, "Product Line" ) )
  {
    return false;
  }
  else if( !checkMandatoryListField( document.all.releaseType, "Release Type" ) )
  {
    return false;
  }
  else if( !checkMandatoryListField( document.all.Configuration, "Configuration" ) )
  {
    return false;
  }

  if(document.all.ProdType != null)
  {
    isChecked = false;
    for (i = 0; i < document.all.ProdType.length; i++)
    {
      if (document.all.ProdType[i].checked)
        isChecked = true;
    }

    if (!isChecked)
    {
      alert('Product type is required.');
      return false;
    }
  }

  return true;
}

function submitGet( pId )
{
  parent.top.bottomFrame.location = editor + "&template-id=" + pId;
}

function submitShow( pIsTemplate )
{
  //this function is called to set if Unassigned Task notepad is to be shown or Templates notepad
  //put the right code later
  if( pIsTemplate )
  {
    //document.forms[0].SubAction.value = "template";
  }
  else
  {
    //document.forms[0].SubAction.value = "task";
  }
  //document.forms[0].submit();
  showWaitMsg(); // mc its 966 show please wait message on screen
  parent.top.bottomFrame.location = taskEditor;
}

function submitList( pType )
{
  //put the right command to submit the list in the notepad ordered by the title clicked
  document.forms[0].OrderBy.value = pType;
  document.forms[0].cmd.value = sort;
  document.forms[0].submit();
}

function submitTaskList( pType )
{
  //put the right command to order the tasks whenever the title is clicked in task table
  document.forms[0].OrderTasksBy.value = pType;
  document.forms[0].cmd.value = sortTasks;
  showWaitMsg(); // mc its 966 show please wait message on screen
  document.forms[0].submit();
}

function submitSearch()
{
  toggle( 'templateSearchLayer', 'templateNameSrch' );
  //put the right command to perform a search from the notepad
  document.forms[0].cmd.value = search;
  showWaitMsg(); // mc its 966 show please wait message on screen
  document.forms[0].submit();
}

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

function submitNew()
{
  // dispay a new template
  document.forms[0].cmd.value = editNew;
  showWaitMsg(); // mc its 966 show please wait message on screen
  document.forms[0].submit();
}

function submitAssignTasks()
{
  //put the right command to assign a task to a template
  parent.top.bottomFrame.location = editor;
}

function submitDeleteTask( pTaskId )
{
  //unassignes  a task from a template  CMD_TEMPLATE_EDIT_DELETE_TASK
  showWaitMsg(); // mc its 966 show please wait message on screen
  parent.top.bottomFrame.location = deleteTasksCommand + "&taskID=" + pTaskId;
}

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
      if (document.forms[0].cmd.value == copyCommand)
      {
        document.forms[0].cmd.value = saveCopy;
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
  }
  else
  {
    eval( 'mtb' + imagePrefix ).reset();
  }
}

function submitCopy()
{
   //copies the existing template and creates a new one
   document.forms[0].cmd.value = copyCommand;
   showWaitMsg(); // mc its 966 show please wait message on screen
   document.forms[0].submit();
}

function submitDelete( imagePrefix )
{
  if( confirm( "You are about to delete a Template record. Confirm?" ) )
  {
    showWaitMsg(); // mc its 966 show please wait message on screen
    //put the right command to delete a template
    parent.top.bottomFrame.location = deleteCommand;
  }
  else
  {
    eval( 'mtb' + imagePrefix ).reset();
  }
}
