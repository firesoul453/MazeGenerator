
package in.fsdomain.mazegenerator;

/*
 * Copyright (c) 2014 Aaron Disibio

This software is provided 'as-is', without any express or implied warranty. In no event will the authors be held liable for any damages arising from the use of this software.

Permission is granted to anyone to use this software for any purpose, including commercial applications, and to alter it and redistribute it freely, subject to the following restrictions:

    1. The origin of this software must not be misrepresented; you must not claim that you wrote the original software. If you use this software in a product, an acknowledgment in the product documentation would be appreciated but is not required.

    2. Altered source versions must be plainly marked as such, and must not be misrepresented as being the original software.

    3. This notice may not be removed or altered from any source distribution.

 */

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface.OutOfResourcesException;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

public class MainViewPanel extends SurfaceView implements SurfaceHolder.Callback{
	int width; // Width of device
	int height;  // height of device
	MainActivity context;


	int totalCells; // Total cells
	Paint paint;
	Paint paint2;
	int[][][] cube;
	StepThroughMazeGen _thread;
	int offset=0; // number of pixels to offset the maze

	Stack<int[]> processed=new Stack<int[]>(); // Cells processed
	Bitmap canvasbit;
	int visitedCells=0; // Cells visited

	int[] currentCell=new int[2];

	public MainViewPanel(MainActivity context) {
		super(context);
		this.context=context;
		getHolder().addCallback(this);
		_thread=new StepThroughMazeGen(this); // This does the loop to generate the maze
		paint=new Paint();
		paint2=new Paint();
		paint.setColor(context.color);
		paint2.setColor(Color.RED);
		offset=context.space/2;

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		int xvalue;
		int yvalue;
		try{
			Canvas c=holder.getSurface().lockCanvas(null);

			width=c.getWidth();
			height=c.getHeight();
			Log.d("",width+" - "+height);
			holder.getSurface().unlockCanvasAndPost(c);
			xvalue=(int) Math.floor((width-1)/context.space); // number of x cells
			yvalue=(int) Math.floor((height-1)/context.space); // number of y cells
			Log.d("",xvalue+" -- "+yvalue);
			cube=new int[xvalue][yvalue][2];
			totalCells=xvalue*yvalue;
			currentCell[0]=(int) Math.floor(Math.random()*xvalue);
			currentCell[1]=(int) Math.floor(Math.random()*xvalue);
		} catch (IllegalArgumentException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OutOfResourcesException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NegativeArraySizeException e){
			Toast.makeText(context,"Error. Is cell spacing neagtive?",Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}


		if (_thread.isAlive()){
			_thread=new StepThroughMazeGen(this);
		}

		_thread.setRun(true);
		_thread.start();

		canvasbit=Bitmap.createBitmap(getWidth(),getHeight(),Bitmap.Config.ARGB_8888);

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub

	}



	public ArrayList<int[]> getPossibleCells(int x, int y) {
		ArrayList<int[]> possible=new ArrayList<int[]>();
		if ((y-1>=0&&x+1<cube.length)&&cube[x][y][0]==0&&cube[x][y-1][0]==0&&cube[x][y-1][1]==0&&cube[x+1][y-1][1]==0){

			int[] temp=new int[4];

			temp[0]=x;
			temp[1]=y;
			temp[2]=0;
			temp[3]=11;
			possible.add(temp);
		}
		// This is for the box to the left of the current box
		if ((x-1>=0&&y+1<cube[0].length)&&cube[x][y][1]==0&&cube[x-1][y][0]==0&&cube[x-1][y][1]==0&&cube[x-1][y+1][0]==0){

			int[] temp=new int[4];
			temp[0]=x;
			temp[1]=y;
			temp[2]=1;
			temp[3]=1;
			possible.add(temp);
		}
		// This is for the box to the right of the current box
		if ((x+2<cube.length&&y+1<cube[0].length)&&cube[x+1][y][1]==0&&cube[x+1][y][0]==0&&cube[x+2][y][1]==0&&cube[x+1][y+1][0]==0){

			int[] temp=new int[4];
			temp[0]=x+1;
			temp[1]=y;
			temp[2]=1;
			temp[3]=2;
			possible.add(temp);
		}
		// This is for the box below the current box
		if ((x+1<cube.length&&y+2<cube[0].length)&&cube[x][y+1][0]==0&&cube[x][y+1][1]==0&&cube[x][y+2][0]==0&&cube[x+1][y+1][1]==0){

			int[] temp=new int[4];
			temp[0]=x;
			temp[1]=y+1;
			temp[2]=0;
			temp[3]=3;
			possible.add(temp);
		}

		return possible;
	}

	protected void StepEvent() {
		if (visitedCells<totalCells){
			// Each cube has an x and y position and the two walls (top which is 0 and left which is 1 )
			// if the value == 0 then there is a wall so its a possibility

			// Here we check to see if the boxes around the current box have all four walls intact
			// If all four are intact then the wall between the two boxes is
			// (the current one and the one just checked) added to the possible list

			// The temp value added to the "possible" list is the exact wall that is possible to be removed

			// The 4th value added to the temp is the box we are switching to
			// since the wall might be part of this block and not the block we need to go to.

			// This is for the box above the current box

			ArrayList<int[]> possible=getPossibleCells(currentCell[0],currentCell[1]);

			if (possible.size()>=1){
				// If we have a possible next position
				// Pick a possibility at random
				int theChoosenOne=(int) (Math.random()*possible.size());

				int[] temp=possible.get(theChoosenOne);
				cube[temp[0]][temp[1]][temp[2]]=1; // Set the value to 1, which means no wall while 0 meant there was a wall
				processed.push(currentCell.clone());
				if (temp[3]==11){
					// If the new cell is above
					currentCell[1]--;
				} else if (temp[3]==1){
					// If the new cell is left
					currentCell[0]--;
				} else if (temp[3]==2){
					// If the new cell is right
					currentCell[0]++;

				} else if (temp[3]==3){
					// If the new cell is below
					currentCell[1]++;
				}
				visitedCells++;
			} else{

				try{
					currentCell=processed.pop();
				} catch (EmptyStackException e){
				}
			}
			possible=null;
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		synchronized (getHolder()){
			try{
				canvas.drawColor(Color.rgb(1,22,55));

				for (int x=0;x<cube.length;x++){
					for (int y=0;y<cube[x].length;y++){

						if (cube[x][y][0]==0&&x!=cube.length-1){
							canvas.drawLine(((float) offset+(x*context.space)),(float) offset+(y*context.space),(float) offset+((x+1)*context.space),(float) offset+(y*context.space),paint);
						}
						if (cube[x][y][1]==0&&y!=cube[0].length-1){
							canvas.drawLine(((float) offset+(x*context.space)),(float) offset+(y*context.space),(float) offset+(x*context.space),(float) offset+((y+1)*context.space),paint);
						}
					}
				}
				if (context.drawStack){
					try{
						for (int i=0;i<processed.size();i++){
							canvas.drawCircle(offset+processed.get(i)[0]*context.space,offset+processed.get(i)[1]*context.space,2,paint2);
						}
					} catch (Exception e){
					}
				}
				canvas.drawCircle(offset+currentCell[0]*context.space,offset+currentCell[1]*context.space,5,paint2);
			} catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	public void stopThread() {
		// TODO Auto-generated method stub
		_thread.setRun(false);
	}
}
