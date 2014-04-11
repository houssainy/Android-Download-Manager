package com.example.downloadmanager;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {

	private Button download;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		download = (Button) findViewById(R.id.download);
		download.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(this, DownloadService.class);
		intent.putExtra("download-link", "My Link");
		startService(intent);
	}

}
