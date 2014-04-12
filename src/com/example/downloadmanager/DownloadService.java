package com.example.downloadmanager;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
		if (isNetworkAvailable()) {
			notificationID++;
			nId = notificationID;

			new DownloadTask().execute(intent.getStringExtra("download-link"));
		} else {
			Toast.makeText(this, "No Internet access!", Toast.LENGTH_LONG)
					.show();
			stopSelf();

		}
		return super.onStartCommand(intent, flags, startId);
	}

	/**
	 * Show new Notification.
	 */
	public void showStartNotification(String fileName) {
		mBuilder = new NotificationCompat.Builder(service)
				.setSmallIcon(R.drawable.ic_launcher).setContentTitle(fileName)
				.setContentText("Download in Progress")
				.setProgress(100, 0, false).setContentInfo("0%");

		mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		// mId allows you to update the notification later on.
		mNotificationManager.notify(nId, mBuilder.build());
	}

	private int lastPercent = 0;

	/**
	 * To update progress bar
	 */
	public void updateProgress(int percent) {
		Log.d(tag, "" + percent);
		if (lastPercent != percent) {
			mBuilder.setProgress(100, percent, false);
			mBuilder.setContentInfo(percent + "%");

			lastPercent = percent;
			mNotificationManager.notify(nId, mBuilder.build());
		}
	}

	/**
	 * Update notification by End message
	 */
	private void showDownloadFinishNotification() {
		mBuilder.setContentText("Download Complete");

		// mId allows you to update the notification later on.
		mNotificationManager.notify(nId, mBuilder.build());
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	private class DownloadTask extends AsyncTask<String, Void, Integer> {

		@Override
		protected Integer doInBackground(String... params) {
			Log.d(tag, "Stareted doInbackgroud");

			return new HttpRequestManager((DownloadService) service)
					.download(params[0]);

		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			switch (result) {
			case Util.OK:
				Toast.makeText(service, "Download Complete", Toast.LENGTH_SHORT)
						.show();
				showDownloadFinishNotification();
				break;

			case Util.ERROR:
				Toast.makeText(service, "Oops, something went wrong!",
						Toast.LENGTH_SHORT).show();
				break;
			case Util.FILE_ALREADY_EXIST:
				Toast.makeText(service, "File Already Exist!",
						Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}

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
