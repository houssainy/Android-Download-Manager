package com.example.downloadmanager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.Environment;

public class HttpRequestManager {

	private DownloadService service;

	public HttpRequestManager(DownloadService service) {
		this.service = service;
	}

	public void download(String downloadUrl, String fileName)
			throws IOException {

		URL url = new URL(downloadUrl);
		HttpURLConnection urlConnection = (HttpURLConnection) url
				.openConnection();
		urlConnection.setRequestMethod("GET");
		urlConnection.setDoOutput(true);
		urlConnection.connect();

		File sdcard = Environment.getExternalStorageDirectory();
		File file = new File(sdcard, fileName);

		FileOutputStream fileOutput = new FileOutputStream(file);
		InputStream inputStream = urlConnection.getInputStream();

		byte[] buffer = new byte[1024];
		int bufferLength = 0;

		service.showStartNotification();
		while ((bufferLength = inputStream.read(buffer)) > 0) {
			fileOutput.write(buffer, 0, bufferLength);
		}
		
		fileOutput.close();
		service.showDownloadFinishNotification();
	}
}
