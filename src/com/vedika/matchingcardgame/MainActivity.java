package com.vedika.matchingcardgame;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

public class MainActivity extends Activity {

	final Context context = this;
	public static int flipCount = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final TextView textView = (TextView) findViewById(R.id.textFlipCount);
		textView.setText("FLIP COUNT: " + flipCount);

		GridView grid = (GridView) findViewById(R.id.gridView1);
		final ImageAdapter imageAdapter = new ImageAdapter(this);
		grid.setAdapter(imageAdapter);

		grid.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				flipCount++;
				imageAdapter.swapImageIds(position);
				imageAdapter.notifyDataSetChanged();
				textView.setText("FLIP COUNT: " + flipCount);

				//when all the 12 cards are matched, game is over
				if (imageAdapter.getCardsMatched() == 12) {
					final Dialog dialog = new Dialog(context);
					// tell the Dialog to use the dialog.xml as it's layout
					// description
					dialog.setContentView(R.layout.dialog);
					dialog.setTitle(R.string.congratulations);

					TextView txt = (TextView) dialog.findViewById(R.id.txt);
					txt.setText("Your flipcount is: " + flipCount);
					Button newGameButton = (Button) dialog
							.findViewById(R.id.newGameButton);
					Button quitGameButton = (Button) dialog
							.findViewById(R.id.quitGameButton);

					newGameButton.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							dialog.dismiss();
							flipCount = 0;
							Intent i = new Intent(getApplicationContext(),
									MainActivity.class);
							startActivity(i);
							finish();
						}
					});

					quitGameButton.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							flipCount = 0;
							finish();
						}
					});
					dialog.show();
				}
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		flipCount = 0;
		Intent i = new Intent(getApplicationContext(), MainActivity.class);
		startActivity(i);
		finish();
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
