/**
 * Milestone v2
 *
 * File: include-jscript-toggle.js
 */

function toggle( pLayer, pFirstFocus )
{
  layer = eval( document.all[ pLayer ] )
  // Check to see if the layer is found (it must be at least an object)
  if ( ( typeof layer ) != "object" )
  {
    // msc 12/17/03 stop message from displaying
    //alert( "Layer to show up is not available." );
    return;
  }
  if ( layer.style.visibility == "visible" )
  {
    layer.style.visibility = "hidden";
  }
  else
  {
    layer.style.visibility = "visible";
    if ( typeof( pFirstFocus ) == "string" && pFirstFocus != "" )
    {
      field = eval( layer.all[ pFirstFocus ] );
      // Check to see if the field was found otherwise it will not focus
      if ( ( typeof field ) == "object" )
      {
        field.focus();
      }
    }
  }
}  // End function toggle

// hide the select form elements
function hideSelectElements(docObj)
{
   var e = docObj.forms[0].elements; // get the document form elements

   // hide the select objects
   for(var i=0; i < e.length; i++)
   {
      // if select object
      var eType = e[i].type.substr(0,6);
      var eName = e[i].name;
      // select objects and the search layer div
      if(eType.toUpperCase() == "SELECT" && eName.toUpperCase().indexOf("SEARCH") == -1
            && eName.toUpperCase().indexOf("SRCH") == -1)
      {
         e[i].style.visibility = 'hidden';
      }
   }

   // hide the search div
   if(docObj.all.SearchLayer)
      docObj.all.SearchLayer.style.visibility = 'hidden';

} // End function hideSelectElements

// show the select form elements
function showSelectElements(docObj)
{
   //var e = formObj.elements
   var e = docObj.forms[0].elements; // get the document form elements
   for(var i=0; i < e.length; i++)
   {
      // if select object
      var eType = e[i].type.substr(0,6);
      var eName = e[i].name;
      // the select objects
      if(eType.toUpperCase() == "SELECT" && eName.toUpperCase().indexOf("SEARCH") == -1
               && eName.toUpperCase().indexOf("SRCH") == -1)
      {
         e[i].style.visibility = 'visible';
      }
   }

} // End function showSelectElements

function showNewProjectSearchInit()
{
  // hide buttons if they exist
  if(document.all.newSelectionDiv)
    document.all.newSelectionDiv.style.visibility = 'hidden';
  if(document.all.saveDiv)
    document.all.saveDiv.style.visibility = 'hidden';
  if(document.all.saveDivBottom)
    document.all.saveDivBottom.style.visibility = 'hidden';
  if(document.all.printWindow)
    document.all.printWindow.style.visibility = 'hidden';
  if(document.all.printWindow2)
    document.all.printWindow2.style.visibility = 'hidden';
  if(document.all.sendOptionDiv)
    document.all.sendOptionDiv.style.visibility = 'hidden';
  if(document.all.newDiv)
    document.all.newDiv.style.visibility = 'hidden';
  if(document.all.deleteDiv)
    document.all.deleteDiv.style.visibility = 'hidden';
  if(document.all.copyDiv)
    document.all.copyDiv.style.visibility = 'hidden';
  if(document.all.copyDigitalDiv)
    document.all.copyDigitalDiv.style.visibility = 'hidden';
  if(document.all.reviseDiv)
    document.all.reviseDiv.style.visibility = 'hidden';

  // hide the select form elements if parentForm object exists
  if(window.document)
    hideSelectElements(window.document);

} // End function showNewProjectSearch

