package com.example.downloadmanager;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class DownloadService extends Service {

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		stopSelf();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

}
