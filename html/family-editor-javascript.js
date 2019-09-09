/*
function submitResize()
{
	// put the right command to resize the notepad
	parent.top.bottomFrame.location = editor;
} //end function submitResize()
*/

function checkField( pField )
{
  var bReturn = true;
  return bReturn;
} //end function checkField()

function checkPage()
{
  if( !checkMandatoryField( document.all.CorporateDescription, "Description" ) )
  {
    return false;
  }
  if( !checkMandatoryField( document.all.CorporateAbbreviation, "Abbreviation" ) )
  {
    return false;
  }
  // jasen will you be passing in this as a hidden field
	//if( !checkMandatoryField( document.all.Parent1Selection, "?? ==>> Undefined" ) )
  //{
  //  return false;
  //}
  return true;
}//end function checkPage()

function submitSearchWithEnter()
{
	if( window.event.keyCode != 13 )
	{
  	return;
	}
  else
	{
		// do nothing
  }
   toggle( 'SearchLayer', 'FamilyDescriptionSearch' );
   document.forms[0].cmd.value = search;
   showWaitMsg();   // msc its 966 show please wait message
   document.forms[0].submit();

} // end function submitSearchWithEnter()

function submitSearch()
{
  if( document.all[ 'SearchLayer' ].style.visibility == "visible" )
	{
    toggle( 'SearchLayer', 'FamilyDescriptionSearch' );
   document.forms[0].cmd.value = search;
   showWaitMsg();   // msc its 966 show please wait message
   document.forms[0].submit();
  }
} // end function submitSearch()

function submitNew()
{
	document.forms[0].cmd.value = editNew;
        showWaitMsg();   // msc its 966 show please wait message
	document.forms[0].submit();
} //end function submitNew()

function submitSave( imagePrefix )
{
  if( checkPage() )
  {
      hideButtons('save');
   	if (document.forms[0].cmd.value != editNew)
    {
   	  document.forms[0].cmd.value = save;
      showWaitMsg();   // msc its 966 show please wait message
      document.forms[0].submit();
    }
    else
    {
      document.forms[0].cmd.value = saveNew;
      showWaitMsg();   // msc its 966 show please wait message
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
  if( confirm( "You are about to delete a Corporate record. Confirm?" ) )
  {
      hideButtons('delete');
      showWaitMsg();   // msc its 966 show please wait message
      parent.top.bottomFrame.location = deleteCommand;
  }
  else
	{
    eval( 'mtb' + imagePrefix ).reset();
  }
} //end function submitDelete()

function submitList( pType )
{
	parent.top.bottomFrame.location = editor;
} //end function submitList()

function submitGet( pId )
{
  parent.top.bottomFrame.location = editor + "&family-id=" + pId;
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

// hide buttons on cache refresh
function hideButtons(sButton)
{
     // msc 03/05/04 hide buttons on save, due to rebuild of cache
     if(document.all.Save && sButton != "save")
         document.all.Save.style.visibility = "hidden";
     if(document.all.New && sButton != "new")
         document.all.New.style.visibility = "hidden";
     if(document.all.Delete && sButton != "delete")
         document.all.Delete.style.visibility = "hidden";
     if(document.all.Search)
         document.all.Search.style.visibility = "hidden";
     if(document.all.Last)
         document.all.Last.style.visibility = "hidden";
     if(document.all.First)
         document.all.First.style.visibility = "hidden";
     if(document.all.Next)
         document.all.Next.style.visibility = "hidden";
     if(document.all.Prev)
         document.all.Prev.style.visibility = "hidden";
     if(document.all.Switch_Environments)
         document.all.Switch_Environments.style.visibility = "hidden";
     // MC its 966 now using <script for> logic
     //if(document.all.waitLayer)
     //    document.all.waitLayer.style.visibility = "visible";

}