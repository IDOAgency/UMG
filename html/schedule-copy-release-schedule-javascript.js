
 /**
 * Milestone v2
 *
 * File: schedule-copy-release-schedule-javascript.js
 */

 function toggleSearch()
 {
   toggle( 'SearchLayer', 'ArtistSearch' );
 }

function checkPage()
{
  //do nothing
}

var commentsCount = 0;
var commentType = '0';

function Is()
{
   var agent = navigator.userAgent.toLowerCase();
   this.major = parseInt(navigator.appVersion);
   this.minor = parseFloat(navigator.appVersion);
   this.ns = ((agent.indexOf('mozilla') != -1) && ((agent.indexOf('spoofer') == -1) && (agent.indexOf('compatible') == -1)));
   this.ns2 = (this.ns && (this.major == 2));
   this.ns3 = (this.ns && (this.major == 3));
   this.ns4 = (this.ns && (this.major >= 4));
   this.ie = (agent.indexOf("msie")  != -1);
   this.ie3 = (this.ie && (this.major == 2));
   this.ie4 = (this.ie && (this.major >= 4));
}

var is = new Is();
if(is.ns4)
{
   doc = "document";
   sty = "";
   htm = ".document";
}
else if(is.ie4)
{
   doc = "document.all";
   sty = ".style";
   htm = "";
}

function checkField( pField )
{
   var bReturn = true;
     switch( pField.name )
     {
        case "comments":
           bReturn = bReturn && checkLength( pField.value, "Comments", 0, 255 );
           break;
        case "releaseComment":
           bReturn = bReturn && checkLength( pField.value, "Release Comments", 0, 1024 );
           break;
        case "holdReason":
           bReturn = bReturn && checkLength( pField.value, "Hold Reason", 0, 255 );
           break;
        case "streetDate":
           bReturn = bReturn && checkFormat( pField.value, "Street Date", "date", "" );
           break;
        case "StreetDateSearch":
           bReturn = bReturn && checkFormat( pField.value, "Street Date Search", "date", "" );
           break;
     }

     if( pField.name.substring( 0, 'dueDate'.length ) == "dueDate" )
     {
       bReturn = bReturn && checkDate( pField, "Due Date", true );
     }

     if( pField.name.substring( 0, 'completion'.length ) == "completion" )
     {
        var now = new Date();
        var nowTime = now.getTime() ;
        var nextWeek = nowTime + 604800000;
        var testResult = checkDate( pField, "Completion Date", true );
        bReturn = bReturn && testResult ;
        if (testResult)
        {
          var compTimeDate = new Date(pField.value);
          var year = compTimeDate.getFullYear();
          if(year < 1970)
          {
            year = year + 100 ;compTimeDate.setYear(year);
          }
          var compTime  = compTimeDate.getTime() ;
          if (compTime > nowTime)
          {
            alert("The completion date is beyond the current day");
            bReturn =  false;
          }
          else
          {
            bReturn = bReturn && true;
          }
        }//end if testResult
     }//end if pField.name == "completioinDate"

     if( !bReturn )
     {
       pField.focus();
     }
     return bReturn;
   }//end function


   function saveComment()
   {
     //set the right command to save the Release Comment Layer entered comments
     //I don't know if release(selection) comments will have the option to save
     //since the top part of the page - the selection part is read only
     parent.top.bottomFrame.location = editor;
   }

   function submitGet( pselectionID )
   {
	 parent.top.bottomFrame.location = scheduleEditor + "&selection-id=" + pselectionID;
   }//end function

   var mfData = null;

   function saveDetailData( pLayer, pField)
   {
     if( mfData != null )
     {
       layer = eval( document.all[ pLayer ] );
       if( ( typeof layer ) == "object" )
       {
         field = eval( layer.all[ pField ] );
         if( ( typeof field ) == "object" )
         {
           mfData.value = field.value;
            //change the command with the right one to save the  comments that user has been entered
           parent.top.bottomFrame.location = editor;
         }
       }
     }//end if mfData
   }//end function

   function submitSave()
   {
     parent.top.bottomFrame.location = editor;
   }//end function

   function submitDelete()
   {
     if(confirm("You are about to delete all tasks assigned to this release. Confirm?"))//jo ITS 1006
     {
       //set the command to the right one to Delete the schedule
       parent.top.bottomFrame.location = editor;
     }
     else
     {
       eval( 'mtb' + 'Delete' ).reset();
     }
   }//end function

   function submitSearch()
   {
     //set the command to the right one to search from the search layer
     parent.top.bottomFrame.location = editor;
   }//end function

   function submitList( pType )
   {
      //set the right command to sort the notepad by the title that was clicked
      form.OrderBy.value = pType;
      form.cmd.value = sort;
      showWaitMsg(); // mc its 966 show please wait message on screen
      form.submit();
   }//end function

   function getReleaseTasks(releaseId)
   {
     parent.top.bottomFrame.location = copyReleaseAssignSchedule + "&releaseId=" + releaseId;
   }//end function

   function submitRecalc( pType, imagePrefix )
   {
     mtbRecalc.reset();
   }//end function

   function sendRecalc(pType)
   {
     if (pType)
       parent.top.bottomFrame.location = recalc;
     else
       parent.top.bottomFrame.location = recalcAll;
   }//end function



   function submitClearDate( imagePrefix )
   {
     if( confirm( "This will permanently clear all Release Due Dates and Weeks To Release fields. Confirm?" ) )
     {
       //set the right command to Clear
       parent.top.bottomFrame.location = editor;
     }
     else
     {
       eval( 'mtb' + imagePrefix ).reset();
     }
   }//end function

   function clickCurrentFilter(obj)
   {
     parent.top.bottomFrame.location = editor;
   }

   function MM_swapImgRestore()
   { //v3.0
     var i, x, a=document.MM_sr;
     for( i = 0; a && i < a.length && (x = a[i]) && x.oSrc; i++)
     {
       x.src = x.oSrc;
     }
   } //end function

   function MM_preloadImages()
   { //v3.0
     var d = document;
     if(d.images)
     {
       if(!d.MM_p)
       {
         d.MM_p = new Array();
       }
       var i, j = d.MM_p.length, a = MM_preloadImages.arguments;
       for(i = 0; i < a.length; i++)
       {
         if (a[i].indexOf("#")!=0)
         {
           d.MM_p[j] = new Image;
           d.MM_p[j++].src = a[i];
         }
       }
     }
   }//end function

   function MM_findObj(n, d)
   { //v3.0
     var p,i,x;
     if(!d) d=document;
     if((p=n.indexOf("?"))>0&&parent.frames.length)
     {
       d=parent.frames[n.substring(p+1)].document;
       n=n.substring(0,p);
     }
     if(!(x=d[n])&&d.all) x=d.all[n];
     for (i=0;!x&&i<d.forms.length;i++)
     {
       x=d.forms[i][n];
     }
     for(i=0;!x&&d.layers&&i<d.layers.length;i++)
     {
       x=MM_findObj(n,d.layers[i].document);
     }
     return x;
   }//end function

   function MM_swapImage()
   { //v3.0
     var i,j=0,x,a=MM_swapImage.arguments;
     document.MM_sr=new Array;
     for(i=0;i<(a.length-2);i+=3)
     {
       if ((x=MM_findObj(a[i]))!=null)
       {
         document.MM_sr[j++]=x;
         if(!x.oSrc) x.oSrc=x.src;
         x.src=a[i+2];
       }
     }
   }//end function


   function submitShow( pIsRelease )
   {
     //This function is not needed here. The selection notepad here should not have
     //the switch to tasks button wich triggers this function.
     //I am leaving it here now not to cause a Java script error
   }//end function


// JR - ITS 226 03/07/03
function compareText (option1, option2) {
	return option1.text < option2.text ? -1 : option1.text > option2.text ? 1 : 0;
}

/* msc afe 2003 now incldued in include-jscript-check-shortcut.js
function sortSelect (select, compareFunction) {
  if (!compareFunction)
    compareFunction = compareText;
  var options = new Array (select.options.length);
  for (var i = 0; i < options.length; i++)
    options[i] =
      new Option (
        select.options[i].text,
        select.options[i].value,
        select.options[i].defaultSelected,
        select.options[i].selected
      );
  options.sort(compareFunction);
  select.options.length = 0;
  for (var i = 0; i < options.length; i++)
    select.options[i] = options[i];
}
*/

function populateLabelSearch(obj, index)
{
   var j = 0;

   // msc afe 2003 07/09/03 new logic below
   /*
   for(var i=0; i<aSearch[index].length; i=i+2)
	 {
       document.forms[0].LabelSearch.options[j] = new Option(aSearch[index][i+1], aSearch[index][i]);
       j++;
   }
	document.forms[0].LabelSearch.options[0].selected=true;
	// JR - ITS 226 03/07/03
	sortSelect(document.forms[0].LabelSearch,compareText);
  */

  clearCombo(document.forms[0].LabelSearch);

  if(companyArray.length >= index){
    if(companyArray[index]){
      for(var i=0; i < companyArray[index].length; i=i+2)
      {
        document.forms[0].LabelSearch.options[j] = new Option(companyArray[index][i+1], companyArray[index][i]);
        j++;
      }
    } else {
      	document.forms[0].LabelSearch.options[j] = new Option('All',0);
      }
    } else {
      	document.forms[0].LabelSearch.options[j] = new Option('All',0);
  }
  sortSelect(document.forms[0].LabelSearch,compareText);
  document.forms[0].LabelSearch.options[0].selected=true;


} //end function populateLabelSearch()

function clickCompanySearch(obj)
{
  clearCombo(document.forms[0].LabelSearch);
  populateLabelSearch(document.forms[0].LabelSearch, obj[obj.selectedIndex].value);
  return true;
} //end function clickCompany()

//JP 9/16/02 ENV AFE
function clickEnvironmentSearch(obj)
{
  clearCombo(document.forms[0].LabelSearch);
  populateLabelSearch(document.forms[0].LabelSearch, obj[obj.selectedIndex].value);
  return true;
} //end function clickEnvironmentSearch()

function clearCombo(obj)
{
	var i;
  for(i=obj.options.length; i>=0; i--)
		obj.options[i] = null;
   	obj.selectedIndex = -1;
} //end function clearCombo()

 function submitCommentsSave()
 {
   document.forms[0].cmd.value = saveSelectionComments;
   showWaitMsg(); // mc its 966 show please wait message on screen
   document.forms[0].submit();
 }//end function