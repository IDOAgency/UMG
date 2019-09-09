/**
	* Milestone v2
	* 
	* File: release-week-editor-javascript.js
	*/


function submitSearch() 
{
  toggle( 'SearchLayer', 'NameSearch' );
  document.all.StartDateSearch.value = convertDate( document.all.StartDateSearch.value );
  document.all.EndDateSearch.value = convertDate( document.all.EndDateSearch.value );
  document.forms[0].cmd.value = search;
  document.forms[0].submit();
}//end function submitSearch()

function submitNew() 
{
  document.forms[0].cmd.value = editNew;
	document.forms[0].submit();
}//end function submitNew()

function submitSave( imagePrefix ) 
{
  if( checkPage() ) 
	{
   	if(document.forms[0].cmd.value == editNew)
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
}//end function submitSave()

function submitList( pType ) 
{
  document.forms[0].OrderBy.value = pType;
  document.forms[0].cmd.value = sort;
  document.forms[0].submit();
}//end function sumitList()

function toggleSearch() 
{
  toggle( 'SearchLayer', 'NameSearch' );
} //end function toggleSearch()
