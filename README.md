MazeGenerator
=============

This generates a "perfect" maze. The app just shows off its process in real time.

This was one of my first android projects (sometime around 2011). This version was  re-amped (better interface and bug fixes and a few other things) about a year or two later for a small school project. 

My technique was based on this description

Depth-First Search  
  
This is the simplest maze generation algorithm. It works like this: 

1) Start at a random cell in the grid.  
2) Look for a random neighbor cell you haven't been to yet.  
3) If you find one, move there, knocking down the wall between the cells. If you don't find one, back up to the previous cell.  
4) Repeat steps 2 and 3 until you've been to every cell in the grid.

from this webpage
http://www.mazeworks.com/mazegen/mazetut/

License 
Zlib

 Copyright (c) 2014 Aaron Disibio

This software is provided 'as-is', without any express or implied warranty. In no event will the authors be held liable for any damages arising from the use of this software.

Permission is granted to anyone to use this software for any purpose, including commercial applications, and to alter it and redistribute it freely, subject to the following restrictions:

1. The origin of this software must not be misrepresented; you must not claim that you wrote the original software. If you use this software in a product, an acknowledgment in the product documentation would be appreciated but is not required.

2. Altered source versions must be plainly marked as such, and must not be misrepresented as being the original software.

3. This notice may not be removed or altered from any source distribution.

