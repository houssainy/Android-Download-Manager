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

	public int download(String downloadUrl) {
		try {
			downloadUrl = "http://download1179.mediafire.com/fu9ten76bfcg/nhfrnnnmjks96rq/Compilers_Lecture_01.pdf";
			URL url = new URL(downloadUrl);
			int indexStart = downloadUrl.lastIndexOf('/');
			String fileName = downloadUrl.substring(indexStart + 1);

			File sdcard = Environment.getExternalStorageDirectory();
			File file = new File(sdcard, fileName);

			if (file.exists()) {
				return Util.FILE_ALREADY_EXIST;
			} else {
				HttpURLConnection urlConnection = (HttpURLConnection) url
						.openConnection();
				urlConnection.setRequestMethod("GET");
				urlConnection.connect();
				int fileSize = urlConnection.getContentLength();
				
				FileOutputStream fileOutput = new FileOutputStream(file);
				InputStream inputStream = urlConnection.getInputStream();

				byte[] buffer = new byte[1024];
				int bufferLength = 0;

				downloadService.showStartNotification(fileName);

				int currentRead = 0;
				while ((bufferLength = inputStream.read(buffer)) > 0) {
					fileOutput.write(buffer, 0, bufferLength);
					currentRead += bufferLength;
					downloadService.updateProgress((int) ((currentRead * 1.0
							/ fileSize * 1.0) * 100));
				}

				fileOutput.close();
				downloadService.showDownloadFinishNotification(fileName);
			}

		} catch (IOException e) {
			return Util.ERROR;
		}

		return Util.OK;
	}
}
