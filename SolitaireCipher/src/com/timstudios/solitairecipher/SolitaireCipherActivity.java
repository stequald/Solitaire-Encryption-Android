package com.timstudios.solitairecipher;

import com.timstudios.solitairecipher.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public class SolitaireCipherActivity extends TabActivity {
	public static SolitaireCrypto solitaire;
	final public int ABOUT = 0;

	@Override
    public void onCreate(Bundle savedInstanceState) {
    	solitaire = new SolitaireCrypto();
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        TabHost tabHost = getTabHost();
        
        TabSpec setupdeckspec = tabHost.newTabSpec("setupdeck");
        //setupdeckspec.setIndicator("Step 1:", getResources().getDrawable(R.drawable.icon_photos_tab));
        setupdeckspec.setIndicator("Step 1:", null);
        Intent setupdeckIntent = new Intent(this, SetUpDeckActivity.class);
        setupdeckspec.setContent(setupdeckIntent);
        
        TabSpec setkeystreamspec = tabHost.newTabSpec("setkeystream");
        //setkeystreamspec.setIndicator("Step 2:", getResources().getDrawable(R.drawable.icon_songs_tab));
        setkeystreamspec.setIndicator("Step 2:", null);
        Intent setkeystreamIntent = new Intent(this, GenKeyStreamActivity.class);
        setkeystreamspec.setContent(setkeystreamIntent);
        
        TabSpec cryptspec = tabHost.newTabSpec("crypt");
        //cryptspec.setIndicator("Step 3:", getResources().getDrawable(R.drawable.icon_videos_tab));
        cryptspec.setIndicator("Step 3:", null);
        Intent cryptIntent = new Intent(this, CryptActivity.class);
        cryptspec.setContent(cryptIntent);
        
        tabHost.addTab(setupdeckspec);
        tabHost.addTab(setkeystreamspec);
        tabHost.addTab(cryptspec);
        
        tabHost.setOnTabChangedListener(tabChangeListener);        
    }

    public boolean onCreateOptionsMenu(Menu menu) {
    	menu.add(0,ABOUT,0,"About");
    	return true;
    }
    
    public boolean onOptionsItemSelected (MenuItem item){
    	switch (item.getItemId()){
    		case ABOUT:
    			AboutDialog about = new AboutDialog(this);
    			about.setTitle("About this app");
    			about.getWindow().setLayout(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
    			about.show();
    			break;
    	}
    	return true;
    }
	
    private OnTabChangeListener tabChangeListener = new OnTabChangeListener() {
    	public void onTabChanged(String tabId) {
  	      	Activity currentActivity = getCurrentActivity();
  	      	if (currentActivity instanceof SetUpDeckActivity) {
				((SetUpDeckActivity) currentActivity).updateDeckEditText();
  	      	}
  	      	else if (currentActivity instanceof GenKeyStreamActivity) { 
  	      		((GenKeyStreamActivity) currentActivity).updateDeckTextView();
	        }
  	      	else if (currentActivity instanceof CryptActivity) { 
  	      		((CryptActivity) currentActivity).updateKeyStreamTextView();
	        }
    	}
    };	
	
	public static boolean isAlpha(String name) {
	    char[] chars = name.toCharArray();

	    for (char c : chars) {
	        if(!Character.isLetter(c)) {
	            return false;
	        }
	    }

	    return true;
	}

	static public void showInputErrorDialog(Activity activity, String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int which) {
              // Add your code for the button here.
           }
        });
        // Set the Icon for the Dialog
        alertDialog.setIcon(R.drawable.icon);
        alertDialog.show();
	}
}