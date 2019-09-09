/**
 * Milestone v2
 *
 * File: schedule-task-editor-javascript.js
 */

function toggleSearch()
 {
   toggle( 'SearchLayer', 'ArtistSearch' );
 }

function checkPage()
{
  //do nothing
}

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
            alert("The completion date is beyond the current day.");
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

   var mfData = null;

   function showDetailData( pField, pLayer, pTargetField )
   {
     mfData = pField;
     if( mfData != null )
     {
       layer = eval( document.all[ pLayer ] );
       if( ( typeof layer ) == "object" )
       {
         field = eval( layer.all[ pTargetField ] );
         if( ( typeof field ) == "object" )
         {
           field.value = mfData.value;
         }
         if( window.event != null )
         {
            // msc 1016 - required for the MAC computer
            var navCom = navigator.platform;
            if( navCom.toUpperCase() != "MACPPC" || navCom.substr(0,3).toUpperCase() != "MAC" ) {       // impact date validation
               layer.style.pixelTop = window.event.y - 60;
            }
         }
       }
     }
     toggle( pLayer, pTargetField );
   }//end function

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
           //saves the comments
           document.forms[0].cmd.value = save;
           document.forms[0].submit();
         }
       }
     }//end if mfData
   }//end function

   function getCalendar()
   {
     layer = eval(document.all["calendar"]);
     if(count == 0)
     {
       layer.style.visibility = "Visible";
       count = 1;
     }
     else
     {
       layer.style.visibility = "Hidden";
       count = 0;
     }
   }//end function

   function submitShow( pIsRelease )
   {
     if( pIsRelease )
     {
       //go to schedule-task-editor.jsp
       parent.top.bottomFrame.location = scheduleEditor;
     }
     else
     {
       //go to schedule-editor.jsp
       parent.top.bottomFrame.location = scheduleEditor;
     }
   }//end function

   function submitGet( pselectionID )
   {
	 parent.top.bottomFrame.location = scheduleEditor + "&selection-id=" + pselectionID;
   }//end function

   function submitSave()
   {
   	 document.forms[0].cmd.value = save;
     document.forms[0].submit();
   }//end function

   function submitDelete()
   {
     if(confirm("You are about to delete all tasks assigned to this release. Confirm?"))//jo ITS 1006
     {
       //set the command to the right one to Delete the schedule
       parent.top.bottomFrame.location = deleteAllTasks;
     }
     else
     {
       eval( 'mtb' + 'Delete' ).reset();
     }
   }//end function

   function submitSearch()
   {
     //performs search from search layer
     document.forms[0].cmd.value = search;

	// JR - ITS 513 03/04/03
	if (document.forms[0].macArtistSearch != null) {
		//jo its 445 1/30/2003
		//set the hidden variables to the form values:
		document.forms[0].macArtistSearch.value = document.forms[0].ArtistSearch.value;
		document.forms[0].macTitleSearch.value = document.forms[0].TitleSearch.value;
		document.forms[0].macPrefixSearch.value = document.forms[0].PrefixSearch.value;
		document.forms[0].macSelectionSearch.value = document.forms[0].SelectionSearch.value;
		document.forms[0].macUPCSearch.value = document.forms[0].UPCSearch.value;
		document.forms[0].macStreetDateSearch.value = document.forms[0].StreetDateSearch.value;
		document.forms[0].macStreetEndDateSearch.value = document.forms[0].StreetEndDateSearch.value;
		document.forms[0].macCompanySearch.value = document.forms[0].CompanySearch[document.forms[0].CompanySearch.selectedIndex].value;
		document.forms[0].macLabelSearch.value = document.forms[0].LabelSearch[document.forms[0].LabelSearch.selectedIndex].value;
		document.forms[0].macContactSearch.value = document.forms[0].ContactSearch[document.forms[0].ContactSearch.selectedIndex].value;

		if (document.forms[0].SubconfigSearch.length>0){
		document.forms[0].macSubconfigSearch.value = document.forms[0].SubconfigSearch[document.forms[0].SubconfigSearch.selectedIndex].value;
		} else {
		document.forms[0].macSubconfigSearch.value = "";
		}
		//jo its 445 1/30/2003
	}// JR - ITS 513 03/04/03

     document.forms[0].submit();
   }//end function

   function saveComment()
   {
     //set the right command to save the Release Comment Layer entered comments
     //I don't know if release(selection) comments will have the option to save
     //since the top part of the page - the selection part is read only
     parent.top.bottomFrame.location = scheduleEditor;
   }

   function submitList( pType )
   {
     //set the right command to sort the notepad by the title that was clicked
    document.forms[0].OrderBy.value = pType;
    document.forms[0].cmd.value = sort;
    document.forms[0].submit();

   }//end function

   function submitDetailList( pType )
   {
    document.forms[0].OrderTasksBy.value = pType;
    document.forms[0].cmd.value = sortTasks;
    document.forms[0].submit();
   }//end function

   function submitRecalc( pType, imagePrefix )
   {
     if( pType )
     {
        recalcLayer.style.visibility='visible';
     }
     else  //if (pType)
     {
        recalcLayer.style.visibility='visible';
     }
   }//end function

   function sendRecalc(pType)
   {
     if (pType)
       parent.top.bottomFrame.location = recalc;
     else
       parent.top.bottomFrame.location = recalcAll;
   }//end function

   function submitClose()
   {
     parent.top.bottomFrame.location = close;
   }//end function

   function submitClearDate( imagePrefix )
   {
     if( confirm( "This will permanently clear all Release Due Dates and Weeks To Release fields. Confirm?" ) )
     {
       parent.top.bottomFrame.location = clearDate;
     }
     else
     {
       eval( 'mtb' + imagePrefix ).reset();
     }
   }//end function

   function submitAssign()
   {
   	 document.forms[0].cmd.value = addTask;
     document.forms[0].submit();
   }//end function

   function validateWks2Rel( pField, pWksToRel, pSOL )
   {
     var str = pField.value;
     if( str == "" )
     {
       return true;
     }

     if( str.toUpperCase() == "SOL" )
     {
       // Item 1465 check to see if the Task is an SOL /////////////////////////////////////////
       if( pWksToRel != pSOL ) {
          alert("SOL is not valid for this task. Please retry again!");
          pField.focus();
          return false;
       }
       return true;
     }

     var valid = true;
     if( isNaN( str ) )
     {
       if( str.indexOf( " " ) > -1 )
       {
         s1 = str.substring( 0, str.indexOf( " " ) ).toUpperCase();
         s2 = str.substring( str.indexOf( " " ) + 1 );
         if( s1 == "M" || s1 == "T" || s1 == "W" || s1 == "TH" || s1 == "F"  || s1 == "S" || s1 == "SU" || s1 == "D" )
         {
           if( isNaN( s2 ) || s2 <= 0 )
           {
             valid = false;
           }
         }
         else
         {
           valid = false;
         }
       }
       else
       {
         valid = false;
       }
     }
     else
     {
       valid = true;
     }

     if( !valid )
     {
       alert( pField.value + " is not a valid Value. Please retry again!");
       pField.focus();
     }
   }//end function

   function submitUnassign( pTaskId )
   {
     if( confirm( "You are about to delete an assigned Task. Confirm?" ) )
     {
       parent.top.bottomFrame.location = deleteTask + "&taskId=" + pTaskId;
     }
   }//end function

   function clickCurrentFilter(obj)
   {
     document.forms[0].cmd.value = filter;
     document.forms[0].submit();
   }

   // item 1957 - department security
   function clickDeptFilter(obj)
   {
     document.forms[0].cmd.value = deptFilter;
     document.forms[0].submit();
   }
   // item 1957 - department security

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
