package com.example.downloadmanager;

import java.io.IOException;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class DownloadService extends Service {
	private String tag = "DownloadService";

	private Service service;

	// Notification Tools
	private NotificationCompat.Builder mBuilder;
	private NotificationManager mNotificationManager;

	private static int notificationID = 0;
	private int nId;

	@Override
	public void onCreate() {
		super.onCreate();

		service = this;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		notificationID++;
		nId = notificationID;

		new DownloadTask().execute(intent.getStringExtra("download-link"));
		return super.onStartCommand(intent, flags, startId);
	}

	/**
	 * Show new Notification.
	 */
	public void showStartNotification(String fileName) {
		mBuilder = new NotificationCompat.Builder(service)
				.setSmallIcon(R.drawable.ic_launcher).setContentTitle(fileName)
				.setContentText("Downloading...");

		mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		// mId allows you to update the notification later on.
		mNotificationManager.notify(nId, mBuilder.build());
	}

	/**
	 * 
	 */
	public void updateProgress(int percent) {
		Log.d(tag, "" + percent);
	}

	/**
	 * Update notification by End message
	 */
	public void showDownloadFinishNotification(String fileName) {
		mBuilder.setContentText("Downloaded");

		// mId allows you to update the notification later on.
		mNotificationManager.notify(nId, mBuilder.build());
	}

	private class DownloadTask extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... params) {
			Log.d(tag, "Stareted doInbackgroud");

			try {
				new HttpRequestManager((DownloadService) service)
						.download(params[0]);
			} catch (IOException e) {
				e.printStackTrace();
				stopSelf();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			Toast.makeText(service, "Downloading Finished...",
					Toast.LENGTH_SHORT).show();
			stopSelf();
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
