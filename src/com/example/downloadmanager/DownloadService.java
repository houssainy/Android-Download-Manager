package com.example.downloadmanager;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.widget.Toast;

public class DownloadService extends Service {

	private Context context;

	@Override
	public void onCreate() {
		super.onCreate();

		context = this;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		new DownloadTask().execute(intent.getStringExtra("download-link"));
		return super.onStartCommand(intent, flags, startId);
	}

	private class DownloadTask extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Toast.makeText(context, "Starting Downloading...",
					Toast.LENGTH_SHORT).show();
		}

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			System.out.println("Back ground");
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Toast.makeText(context, "Downloading Finished...",
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Must Override this method and return null to avoid Bind Service
	 */
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
