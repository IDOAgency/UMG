/**
 * Milestone v2
 *
 * File: include-jscript-toggle-button.js
 */

/**
 * A JavaScript object for toggle buttons.
 */
function ToggleButton( pDocument, pImagePath, pImagePrefix, pLinkCall, pWidth, pHeight ) 
{  
  // First time called, document will be false. Ignore this call.
  if ( pDocument == null ) 
  {
    return;
  }

  this.limages = new Array(3);

  // The first time we are called (and only the first time) we have
  // to do some special stuff. First, now that the prototype object
  // is created, we can set up our methods.
  // Second, we've got to load the images that we'll be using.
  // Doing this will get the images in the cache for when we need them.

  if ( !ToggleButton.prototype.over ) 
  {
    // Initialize the prototype object to create our methods.
    ToggleButton.prototype.over = _ToggleButton_over;
    ToggleButton.prototype.out = _ToggleButton_out;
    ToggleButton.prototype.click = _ToggleButton_click;
    ToggleButton.prototype.reset = _ToggleButton_reset;
  } //end if !ToggleButton.Prototype

  // Now create an array of image objects, and assign URLs to them.
  // The URLs of the images are configurable, and are stored in an
  // array property of the constructor function itself. They will be
  // initialized below. Because of a bug in Navigator, we've got
  // to maintain references to these images, so we store the array
  // in a property of the constructor rather than using a local variable.

  for ( var i = 0; i < 3; i++ ) 
  {
    this.limages[i] = new Image(pWidth, pHeight);
    var lEnd;
    switch(i)
    {
      case 0:
        lEnd = '_On.gif';
        break;
      case 1:
        lEnd = '_Over.gif';
        break;
      default:
        lEnd = '_Off.gif';
    }

    this.limages[i].src = pImagePath + pImagePrefix + lEnd;

  } // end for loop

  // Save some of the arguments we were passed.
  this.isOff = false;

  // Remember that the mouse is not currently on top of us.
  this.isHighlighted = false;

  // Now save a reference to the Image object that it created in the ToggleButton object.

  this.image = eval(document.images[pImagePrefix]);

} // end ToggleButton

// This becomes the over() method.
function _ToggleButton_over()
{
  // Change the image, and remember that we're highlighted.
  if ( !this.isOff )
  {
    this.image.src = this.limages[1].src;
    isHighlighted = true;
  }
} // end function _ToggleButton_over()

// This becomes the out() method.
function _ToggleButton_out()
{
  // Change the image, and remember that we're not highlighted.
  if ( !this.isOff )
  {
    this.image.src = this.limages[0].src;
    this.isHighlighted = false;
  }
} // end function _ToggleButton_out()

// This becomes the click() method.
function _ToggleButton_click()
{
  // Toggle the state of the button, change the image
  this.isOff = !this.isOff;
  if ( this.isOff ) 
  {
    this.image.src = this.limages[2].src;
  }
  else
  {
    this.image.src = this.limages[1].src;
  }
} // end function _ToggleButton_click()

// This becomes the reset() method.
function _ToggleButton_reset()
{
  // Toggle the state of the button, change the image
  this.isOff = !this.isOff;
  if ( this.isHighlighted ) 
  {
    this.image.src = this.limages[1].src;
  } 
  else 
  {
    this.image.src = this.limages[0].src;
  }
} // end function _ToggleButton_reset()
