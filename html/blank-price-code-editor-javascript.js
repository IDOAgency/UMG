/**
 * Milestone v2
 *
 * File: price-code-editor-javascript.js
 */

function submitSearch() 
{
  toggle( 'SearchLayer', 'NameSearch' );
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

function toggleSearch() 
{
  toggle( 'SearchLayer', 'SellCodeSearch' );
} //end function toggleSearch()
