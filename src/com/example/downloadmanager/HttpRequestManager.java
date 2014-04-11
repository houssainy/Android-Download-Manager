package com.example.downloadmanager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.Environment;

public class HttpRequestManager {

	
	public void download(){
		try {
		    URL url = new URL("http://download1222.mediafire.com/ilub3m82wwog/e3m448pa4455c24/Compilers_Lecture_02.pdf");
		    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		    urlConnection.setRequestMethod("GET");
		    urlConnection.setDoOutput(true);
		    urlConnection.connect();

		    File sdcard = Environment.getExternalStorageDirectory();
		    File file = new File(sdcard, "Compilers_Lecture_02.pdf");

		    FileOutputStream fileOutput = new FileOutputStream(file);
		    InputStream inputStream = urlConnection.getInputStream();

		    byte[] buffer = new byte[1024];
		    int bufferLength = 0;

		    while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
		        fileOutput.write(buffer, 0, bufferLength);
		    }
		    fileOutput.close();
		    

		} catch (MalformedURLException e) {
		        e.printStackTrace();
		} catch (IOException e) {
		        e.printStackTrace();
		}
		}
	}
	

