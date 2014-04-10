package com.example.downloadmanager;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {

	private Button download;
	private Activity activity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		download = (Button) findViewById(R.id.download);
		download.setOnClickListener(this);

		activity = this;
	}

	@Override
	public void onClick(View v) {
		// Intent intent = new Intent(this, DownloadService.class);
		// startService(intent);
		// for (int i = 0; i < 7000; i++) {
		// System.out.println("Activity "+i);
		// }
		new DownloadTask().execute("My Link");
	}

	private class DownloadTask extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(activity, DownloadService.class);
			intent.putExtra("link", params[0]);
			startService(intent);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			System.out.println("Task Done");
			Toast.makeText(activity, "Toast", Toast.LENGTH_SHORT).show();

			super.onPostExecute(result);
		}

	}

}
