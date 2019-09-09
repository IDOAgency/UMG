function checkField( pField ) 
{
  var bReturn = true;
  switch( pField.name ) 
	{
    case "SpecialDate":
      bReturn = bReturn && checkDate( pField, "Special Date", true );
      break;
  }
  return bReturn;
} //end function checkField()

function submitSearch() 
{
  toggle( 'SearchLayer', 'GroupSearch' );
  document.all.SpecialDateSearch.value = convertDate( document.all.SpecialDateSearch.value );
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
  toggle( 'SearchLayer', 'GroupSearch' );
}// end function toggleSearch()
