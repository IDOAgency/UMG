function toggleSearch() 
{
	toggle( 'searchLayer', 'nameSrch' );
} //end function toggleSearch

function checkField( pField ) 
{
	var bReturn = true;
  switch( pField.name ) 
	{
		// do nothing
  }
  if( !bReturn ) 
	{
		pField.focus();
  }
  return bReturn;
} //end function checkField()

function checkPage() 
{
	if (document.forms[0].cmd.value == editNew)
	{	
		if( !checkMandatoryField( document.all.password, "Password" ) ) 
		{
			return false;
  	}
		if( !checkMandatoryField( document.all.fullname, "User Name" ) ) 
		{
			return false;
  	}
		return true;
	}
	else
	{
		return false;
	}
} //end function checkPage()

function submitNewHeader() 
{
  document.forms[0].cmd.value = editNew;
  document.forms[0].submit();  
} //end function submitNewHeader()

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
  document.forms[0].cmd.value = search;
  document.forms[0].submit();
	toggle( 'searchLayer', 'nameSrch' );
} //end function submitSearchWithEnter()

function submitSearch() 
{
	toggle( 'searchLayer', 'nameSrch' );
  document.forms[0].cmd.value = search;
  document.forms[0].submit();
} //end function submitSearch()

function submitShow( pIsUser ) 
{
  //parent.top.bottomFrame.location = "home?cmd=user-company-security-editor";
  parent.top.bottomFrame.location = "home?cmd=user-environment-security-editor";
} //end function submitShow()

function submitNew() 
{
  document.forms[0].cmd.value = editNew;
  document.forms[0].submit();  
} //end function submitNew()


function submitSave( imagePrefix ) 
{
	if( checkPage() ) 
	{
	   if (document.forms[0].cmd.value == editNew)
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
} //end function submitSave()

function submitDelete( imagePrefix ) 
{
  if(confirm("You are about to delete a record. Confirm?")) 
  {
		parent.top.bottomFrame.location = deleteCommand;
  }
  else 
  {
    eval( 'mtb' + imagePrefix ).reset();
  }
} //end function submitDelete()

function submitList( pType ) 
{
  parent.top.bottomFrame.location = sort + "&OrderBy=" + pType;
} //end function submitList()

function submitLogOff( ) 
{
  parent.top.bottomFrame.location = editor;
} //end function submitLogOff()

function submitGet( pUserId) 
{
  parent.top.bottomFrame.location = editor + "&user-id=" + pUserId;
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


NS4 = (document.layers);
IE4 = (document.all);
ver4 = (NS4 || IE4);
isMac = (navigator.appVersion.indexOf("Mac") != -1);
isMenu = (NS4 || (IE4 && !isMac));
function popUp(){return};
function popDown(){return};
function startIt(){return};
if (!ver4) event = null;

function MM_swapImgRestore() 
{ //v3.0 
	var i,x,a=document.MM_sr; 
	for(i=0; a && i < a.length && (x=a[i]) && x.oSrc; i++) 
		x.src=x.oSrc;
} //end function MM_swapImgRestore()

function MM_preloadImages() 
{ //v3.0  
	var d=document; 
	if(d.images)
	{ 
		if(!d.MM_p) d.MM_p=new Array();
		var i,j=d.MM_p.length,a=MM_preloadImages.arguments; 
		for(i=0; i < a.length; i++)
		{
			if (a[i].indexOf("#")!=0)
			{ 
				d.MM_p[j]=new Image; 
				d.MM_p[j++].src=a[i];
			}
		}
	}
} //end function MM_preloadImages()

function MM_findObj(n, d) 
{ //v3.0   
	var p,i,x;  
	if(!d) 
		d=document; 
		if((p=n.indexOf("?"))>0&&parent.frames.length) 
		{ 
			d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);
		} 
		if(!(x=d[n])&&d.all) 
			x=d.all[n]; 
		for (i=0;!x&&i<d.forms.length;i++) 
			x=d.forms[i][n];
		for(i=0;!x&&d.layers&&i<d.layers.length;i++) 
			x=MM_findObj(n,d.layers[i].document); 
			return x; 
} //end function MM_findObj()

function MM_swapImage() 
{ //v3.0  
	var i,j=0,x,a=MM_swapImage.arguments; 
	document.MM_sr=new Array; 
	for(i=0;i<(a.length-2);i+=3)
		if ((x=MM_findObj(a[i]))!=null)
		{
			document.MM_sr[j++]=x; 
			if(!x.oSrc) 
				x.oSrc=x.src; 
				x.src=a[i+2];
		}
} //end function MM_swapImage()

function openWindow(which, title, wd, ht) 
{
	eval('desktop = parent.window.open("' + which + '","' + title + '","width=' + wd + ',height=' + ht + ',toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no");')
} //end function openWindow()