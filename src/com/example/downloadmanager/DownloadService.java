package com.example.downloadmanager;

import java.io.IOException;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class DownloadService extends Service {

	private Context context;
	private String fileName;

	@Override
	public void onCreate() {
		super.onCreate();

		context = this;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		String link = intent.getStringExtra("download-link");
		String[] temp = link.split("//");
		fileName = temp[temp.length - 1];

		new DownloadTask().execute(link);
		return super.onStartCommand(intent, flags, startId);
	}

	private class DownloadTask extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
					context).setSmallIcon(R.drawable.ic_launcher)
					.setContentTitle(fileName).setContentText("Downloading...");

			mBuilder.setNumber(20);
			NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			int mId = 1;
			// mId allows you to update the notification later on.
			mNotificationManager.notify(mId, mBuilder.build());

		}

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			System.out.println("Back ground");
			HttpRequestManager x = new HttpRequestManager();
			try {
				x.download(params[0], fileName);
			} catch (IOException e) {
				e.printStackTrace();
				stopSelf();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Toast.makeText(context, "Downloading Finished...",
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
