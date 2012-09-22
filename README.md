# DISCLAIMER!
I claim no ownership to any of the images stored in /src/images and all subsequent sub-directories. All artwork on this repo comes from http://xkcd.com/ and more accurately http://xkcd.com/1110/ 

The reason I have included the copies of the original images that I downloaded (using cURL) is because I wanted to save anyone who uses this repo from having to spend ages querying the xkcd server with lots of request that will likely come back blank.

The modified images stored within the sub-directories aforementioned are simply resized versions of the original artwork, used to speed up rendering.

Randall, I am HUGE fan. Please don't sue me.

# Usage

	-p --path    : The path to the folder containing the image tiles
	-r --rebuild : Tells the program to rebuild the resize cache based on the given image directory
	-o --outline : Do you want the outline on squares on by default. Can be toggled using the (O) key
	-c --cross   : Do you want the crosshair on by default. Can be toggled using the (C) key

	Example usage:
	java Stitch -p "C:\xkcd_images" -r
	
# Controls

	Movement : WASD & Arrow Keys to pan
	Zoom     : Ctrl (Zoom in) & Shift (Zoom out)
			 : (Z) will automatically (and smoothly) zoom you into 100%  
	
	Mouse controls coming soon!

## Just a note
Some of the code here might be a little "hack & slash", I tried to keep it as clean and proper as possible but given this project was A. Just for fun, and B. Mainly built by taking files from my Aliens/Pacman Game and re-tooling them, I hope you can see how there may be one or two things which aren't quite kosher. 

## Additionally on how I acquired the images...

	Firstly I ran these commands to download the "4 hemispheres" of the comic
	
	curl "http://imgs.xkcd.com/clickdrag/[1-20]n[1-50]e.png" -o "#1n#2e.png"
	curl "http://imgs.xkcd.com/clickdrag/[1-20]s[1-50]e.png" -o "#1s#2e.png"
	curl "http://imgs.xkcd.com/clickdrag/[1-20]n[1-50]w.png" -o "#1n#2w.png"
	curl "http://imgs.xkcd.com/clickdrag/[1-20]s[1-50]w.png" -o "#1s#2w.png"
	
	Then I ordered the files by Size (asc) and deleted all the empty/corrupt files

# Screenshots

Zoomed out a little
![Zoomed out a little](https://raw.github.com/L2Program/XKCD_Stitcher/master/screenshots/1.png)

Fully zoomed out with outline turned on
![Fully zoomed out with outline turned on](https://raw.github.com/L2Program/XKCD_Stitcher/master/screenshots/2.png)

One of my favorite bits
![One of my favorite bits](https://raw.github.com/L2Program/XKCD_Stitcher/master/screenshots/3.png)

Another favorite
![Another favorite](https://raw.github.com/L2Program/XKCD_Stitcher/master/screenshots/4.png)