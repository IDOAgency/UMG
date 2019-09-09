/**
 * Milestone v2
 *
 * File: include-jscript-check-format.js
 */

/** 
 * Checks the format of a value. At the moment it only check if a value
 * is a number or an integer.
 * @param pValue            String: Value to be checked
 * @param pName             String: Name of the field to identify it in the alert
 *                          box
 * @param pType             String: Type of the value. It supports 'number',
 *                          'integer' and 'date'.
 * @param pPattern          String: Pattern to check. NOT supported now.
 * @return                  boolean: True if everything is OK otherwise false.
 */
function checkFormat( pValue, pName, pType, pPattern ) 
{
  if ( pType == "number" && isNaN( pValue ) ) 
  {
    alert( "Field: " + pName + " must be a number" );
    return false;
  }



 /* if ( pType == "numberCond" && isNaN( pValue ) ) 
  {
    alert( "Field: " + pName + " must be a number" );
    return false;
  }

  if ( pType == "numberCond" ) 
  {
      if(pValue == null)   return false;
  }*/


  if ( pType == "integer" && ( isNaN( pValue ) || pValue.indexOf( "." ) >= 0 ) ) 
  {
    alert( "Field: " + pName + " must be a integer" );
    return false;
  }
  if ( pType == "date" && isNaN( new Date( pValue ).getTime() ) ) 
  {
    alert( "Field: " + pName + " must be a date" );
    return false;
  }
  if ( pType == "date" && !isNaN( new Date( pValue ).getTime() ) ) 
  {
    myMonthArray = new Array(31,28,31,30,31,30,31,31,30,31,30,31);
    arrayOfStrings = pValue.split("/");
    pos = arrayOfStrings[0];
    pos = pos -1;
    if (pos < 0 || pos > 11)
    {  
      alert( "Field: " + pName + " must be a valid month" );
      return false;
    }
   
    if (pos == 1)
    {
      dateTimeVal = new Date(pValue);
      year = dateTimeVal.getFullYear();
      if(year < 1970)
      { 
        year = year + 100;
      }
      if( (year % 4 ==0 ) && (year % 100 !=0))
      {
        myMonthArray[1]=29;
      }
      if( (year % 4 ==0 ) && (year % 100 ==0)&& (year % 400 ==0))
      {
        myMonthArray[1]=29;
      }
    } // end if pos == 1
         
    if ((arrayOfStrings[1] > myMonthArray[pos]) || (arrayOfStrings[1]< 1) )
    { 
      alert( "Field: " + pName + " must be a valid day of month" ); 
      return false;
    }

    // Get the year data for validation perform at the end of the block
    var sYear = "";
    var j = 0;
    
    for (i = 0; i < pValue.length; i++)
    {
      s = pValue.substring(i,i+1);
      // Get the year digits
      if( s == "/" || s == "-" || s == ".")
      {
        j++;
        if (j == 2)
        {
          sYear = pValue.substring(i+1);
        }
      }
    } // End the for loop

    // Validate the year input value
    if (sYear.length != 2 && sYear.length != 4)
    {
      alert("Year value is invalid. Please retry again!");
      return false;
    }
    if (isNaN(s))
    { 
      alert("Year must be numeric value. Please retry again!");
      return false;
    }
  }  // End if pType == 'date'

 if ( pType == "numberCond" ) 
  {
	if(pPattern == "0") 
	{
	     if ( isNaN( pValue ) ) 
  	    {
    		alert( "Field: week to release must be a number" );
    		return false;
  	    }
	   if(pValue == "0" ||  pValue == "")
	   {
		return true;
	   }
	   else
	  {
		return false;
	  }
	}  //Should not go to else !!!!
	else
	{
	        if ( isNaN( pValue ) ) 
  	    {
    		alert( "Field: week to release must be a number" );
    		return false;
  	    }
		//return true to indicate  that the field is not a blank
	                   return true;
	}
  }

  return true;

}  // End function checkFormat

/**
 * Checks if the given field contains something because the field is
 * mandatory.
 * @param pField            document.forms[0].Element: Field object to be checked
 * @param pName             String: Name of the field to identify it in the alert
 *                          box
 * @return                  boolean: True if everything is OK otherwise false.
 */
function checkMandatoryField( pField, pName ) 
{
  if (!checkLength(pField.value, pName, 1, 0))
  {
    pField.focus();
    return false;
  }
  return true;
}

/**
 * Checks if the given field contains something because the field is
 * mandatory.
 * @param pField            document.forms[0].Element: Field object to be checked
 * @param pName             String: Name of the field to identify it in the alert
 *                          box
 * @return                  boolean: True if everything is OK otherwise false.
 */
function checkDayField( pField, pName ) 
{
	 if(pField.value == "0" || pField.value == "")
	{	
		
		//alert("false checkDay Field  :pField value = " + pField.value);
		return false;
	
	}
	//alert("true check Day field  :pField value = " + pField.value);
	return true;
}
