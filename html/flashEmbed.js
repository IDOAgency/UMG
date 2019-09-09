function runFlash(imageDirectory)
{
	document.write('<OBJECT classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://active.macromedia.com/flash2/cabs/swflash.cab#version=4,0,0,0" ID=Music Splash Movie WIDTH=550 HEIGHT=300>\n');
	document.write('<param name="movie" value="' + imageDirectory + 'Music Splash Movie.swf">\n');
	document.write('<param name="quality" value="high">\n');
	document.write('<param name="bgcolor" value="#FFFFFF">\n');
	document.write('<param name="Loop" value="0">\n');
	document.write('<embed src="' + imageDirectory +'Music Splash Movie.swf" quality="high" bgcolor="#FFFFFF"  width=550 height=300 type="application/x-shockwave-flash" pluginspace="http://www.macromedia.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash"></embed>\n');
	document.write('</object>\n');
}