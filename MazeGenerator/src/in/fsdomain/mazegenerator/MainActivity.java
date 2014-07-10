
/*
 * Copyright (c) 2014 Aaron Disibio

This software is provided 'as-is', without any express or implied warranty. In no event will the authors be held liable for any damages arising from the use of this software.

Permission is granted to anyone to use this software for any purpose, including commercial applications, and to alter it and redistribute it freely, subject to the following restrictions:

    1. The origin of this software must not be misrepresented; you must not claim that you wrote the original software. If you use this software in a product, an acknowledgment in the product documentation would be appreciated but is not required.

    2. Altered source versions must be plainly marked as such, and must not be misrepresented as being the original software.

    3. This notice may not be removed or altered from any source distribution.

 */

package in.fsdomain.mazegenerator;


import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity{
	MainViewPanel viewPanel;

	// Option variables
	int space=15; // amount of space between cellster draw step.
	int speed=25; // Number of generations steps per draw step.
	int color=Color.parseColor("#33B5E5");
	boolean drawStack=false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		viewPanel=new MainViewPanel(this);
		setContentView(viewPanel);

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main,menu);
		return super.onCreateOptionsMenu(menu);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId())
			{
				case (R.id.menu_restart):
					viewPanel.stopThread();
					viewPanel=new MainViewPanel(this);
					setContentView(viewPanel);
					break;
				case (R.id.menu_space):
					viewPanel.stopThread();
					askForSpacing();

					viewPanel=new MainViewPanel(this);
					setContentView(viewPanel);
					break;
				case (R.id.menu_speed):
					askForSpeed();
					viewPanel.stopThread();
					viewPanel=new MainViewPanel(this);
					setContentView(viewPanel);
					break;
				case (R.id.menu_color):
					askForColor();
					viewPanel.stopThread();
					viewPanel=new MainViewPanel(this);
					setContentView(viewPanel);
					break;
				case (R.id.menu_drawStack):
					drawStack=!drawStack;
					break;
			}
		return super.onOptionsItemSelected(item);
	}

	private int askForColor() {
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		LayoutInflater inflater=getLayoutInflater();
		View v=((View) inflater.inflate(R.layout.getintvalue,null));
		builder.setView(v);
		final AlertDialog alertDialog=builder.create();
		TextView tv=(TextView) v.findViewById(R.id.textView1);
		final EditText et=(EditText) v.findViewById(R.id.editText1);
		Button bt=(Button) v.findViewById(R.id.button1);
		tv.setText("Type in the hex color you want without #. Default is 33B5E5");
		bt.setText("OK");
		bt.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				color=Color.parseColor("#"+et.getText());
				alertDialog.dismiss();
			}
		});

		alertDialog.setCanceledOnTouchOutside(true);
		alertDialog.show();
		return 0;
	}


	private int askForSpeed() {
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		LayoutInflater inflater=getLayoutInflater();
		View v=((View) inflater.inflate(R.layout.getintvalue,null));
		builder.setView(v);
		final AlertDialog alertDialog=builder.create();
		TextView tv=(TextView) v.findViewById(R.id.textView1);
		final EditText et=(EditText) v.findViewById(R.id.editText1);
		Button bt=(Button) v.findViewById(R.id.button1);
		tv.setText("How fast do you want to draw? Higher is faster. Default is 25.\n The larger the maze the higher you want this to be. \n Must be positive.");
		bt.setText("OK");
		bt.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				speed=Integer.parseInt(et.getText().toString());
				alertDialog.dismiss();
			}
		});


		alertDialog.setCanceledOnTouchOutside(true);
		alertDialog.show();
		return 0;
	}


	private int askForSpacing() {
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		LayoutInflater inflater=getLayoutInflater();
		View v=((View) inflater.inflate(R.layout.getintvalue,null));
		builder.setView(v);
		final AlertDialog alertDialog=builder.create();


		TextView tv=(TextView) v.findViewById(R.id.textView1);
		final EditText et=(EditText) v.findViewById(R.id.editText1);
		Button bt=(Button) v.findViewById(R.id.button1);
		tv.setText("Type in the pixel spacing between cells. \n The higher the number the less cells. \n The default is 15 \n (Too low of a number and it will run slowy) \n Must be positive.");
		bt.setText("OK");
		bt.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				space=Integer.parseInt(et.getText().toString());
				alertDialog.dismiss();
			}
		});

		alertDialog.setCanceledOnTouchOutside(true);

		alertDialog.show();



		return 0;
	}


	@Override
	protected void onPause() {
		// TODO Auto-generated method stub

		super.onPause();
	}


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub

		super.onResume();
	}



	@Override
	public void onBackPressed() {
		viewPanel.stopThread();
		super.onBackPressed();
	}


}
