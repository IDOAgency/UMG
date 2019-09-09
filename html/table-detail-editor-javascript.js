
function checkField( pField )
{
  var bReturn = true;
  return bReturn;
} // end function checkField()

function checkPage()
{
  if( document.all.Value != null && !checkMandatoryField( document.all.Value, "Value" ) )
  {
    alert('Value is required.');
    return false;
  }

  if( !checkMandatoryField( document.all.Description, "Description" ) )
  {
    alert('Description is required.');
    return false;
  }

  if( document.all.ProdType != null)
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
} //end function checkPage()

function submitSearch()
{
  toggle( 'SearchLayer', 'FamilyDescriptionSearch' );
  document.forms[0].cmd.value = search;
  showWaitMsg(); // mc its 966 show please wait message on screen
  document.forms[0].submit();
} // end function submitSearch()


function submitNew()
{
  // dispay a new table header
  document.forms[0].cmd.value = editNew;
  showWaitMsg(); // mc its 966 show please wait message on screen
  document.forms[0].submit();
} //end function submitNew()

function submitSave( isNew )
{
  if( checkPage() )
  {
    if (isNew)
   	  document.forms[0].cmd.value = saveNew;
    else
   	  document.forms[0].cmd.value = save;

    showWaitMsg(); // mc its 966 show please wait message on screen
    document.forms[0].submit();
  }
/*
  else
  {
    //eval( 'mtb' + imagePrefix ).reset();
    alert('All fields are required.');
  }
*/
} //end function submitSave()


function submitDelete( imagePrefix )
{
  if( confirm( "You are about to delete a Table Detail record. Confirm?" ) )
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
  showWaitMsg(); // mc its 966 show please wait message on screen
  document.forms[0].submit();
} //end function submitList()

function submitGet( pId )
{
	parent.top.bottomFrame.location = editor + "&table-id=" + pId;
} //end function submitGet()

function submitGet( pId, pValue )
{
	parent.top.bottomFrame.location = editor + "&table-id=" + pId + "&table-detail-id=" + pValue;
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
  	parent.top.bottomFrame.location = "home?cmd=tables-header-editor";
  }
  else
  {
  	parent.top.bottomFrame.location = editor;
  }
} //end function submitShow()

function toggleSearch()
{
  toggle( 'SearchLayer', 'DescriptionSearch' );
} //end function toggleSearch()
