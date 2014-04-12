package com.example.downloadmanager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.Environment;

public class HttpRequestManager {

	private DownloadService downloadService;

	public HttpRequestManager(DownloadService service) {
		this.downloadService = service;
	}

	public void download(String downloadUrl) throws IOException {
		String fileName = "Ch06+-+Decision.ppt";
		URL url = new URL(
				"http://download1156.mediafire.com/208dd21218lg/3qql3l4qkn95obp/Ch06+-+Decision.ppt");
		HttpURLConnection urlConnection = (HttpURLConnection) url
				.openConnection();
		urlConnection.setRequestMethod("GET");

		urlConnection.connect();
		int fileSize = urlConnection.getContentLength();

		File sdcard = Environment.getExternalStorageDirectory();
		File file = new File(sdcard, fileName);
		
		FileOutputStream fileOutput = new FileOutputStream(file);
		InputStream inputStream = urlConnection.getInputStream();

		byte[] buffer = new byte[1024];
		int bufferLength = 0;

		downloadService.showStartNotification(fileName);

		int currentRead = 0;
		while ((bufferLength = inputStream.read(buffer)) > 0) {
			fileOutput.write(buffer, 0, bufferLength);
			currentRead += bufferLength;
			downloadService
					.updateProgress((int) ((currentRead*1.0 / fileSize * 1.0 )* 100));
		}

		fileOutput.close();
		downloadService.showDownloadFinishNotification(fileName);
	}
}
