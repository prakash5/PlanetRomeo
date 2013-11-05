package com.pandroid.planetromeo;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends Activity {
		WebView we;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		CookieSyncManager.createInstance(this);
	    CookieManager.getInstance().setAcceptCookie(true);
	    CookieSyncManager.getInstance().startSync();
	    CookieSyncManager.createInstance(getBaseContext());
		we=(WebView)findViewById(R.id.webV);
		we.setWebViewClient(new WebViewClient());
		WebSettings localWebSettings =we.getSettings();
		localWebSettings.setJavaScriptEnabled(true);
	    localWebSettings.setAppCacheEnabled(true);
	    localWebSettings.setGeolocationEnabled(true);
		we.setWebChromeClient(new WebChromeClient(){
			public void onGeolocationPermissionsShowPrompt(final String paramString, final android.webkit.GeolocationPermissions.Callback paramCallback)
	        {
				Log.i("in geo location", "onGeolocationPermissionsShowPrompt()");
	          AlertDialog.Builder localBuilder = new AlertDialog.Builder(MainActivity.this);
	          localBuilder.setTitle("Location");
	          localBuilder.setMessage("Would you like to use your Current Location? ")
	          .setCancelable(true)
	          .setPositiveButton("Allow", new DialogInterface.OnClickListener()
	          {
	            public void onClick(DialogInterface paramDialogInterface, int paramInt)
	            {
	            	Log.i("hello", "Allowed geo!");
	            	paramCallback.invoke(paramString, true, false);
	            }
	          })
	          .setNegativeButton("Don't Allow", new DialogInterface.OnClickListener()
	          {
	            public void onClick(DialogInterface paramDialogInterface, int paramInt)
	            {
	            	Log.i("hello", "Dont allowed geo!");
	            	paramCallback.invoke(paramString, false, false);
	            }
	          });
	          localBuilder.create().show();
	        }

	        public void onProgressChanged(WebView paramWebView, int progress)
	        {
	        	ProgressBar pb=(ProgressBar)findViewById(R.id.progressBar);
	        	Log.w("WebView", "loading...");
				if(progress<100 && pb.getVisibility()==ProgressBar.GONE){
					pb.setVisibility(ProgressBar.VISIBLE);
				}
				pb.setProgress(progress);
				if(progress==100){
					pb.setVisibility(ProgressBar.GONE);
				}
	        }
	      });
		CookieSyncManager.getInstance().startSync();
	    CookieSyncManager.getInstance().sync();
		we.loadUrl("https://touch.planetromeo.com");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		super.onCreateOptionsMenu(menu);
		menu.add(0, 0, 0, "Exit");
		menu.add(0,1,0,"About");
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		case 0:
			finish();
			super.onDestroy();

		case 1:
			Toast.makeText(getApplicationContext(), "Help Please!", Toast.LENGTH_SHORT).show();
		
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
		if ((keyCode == KeyEvent.KEYCODE_BACK) && we.canGoBack()) {
			Log.i("hello", "we are in keyback");
			we.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
