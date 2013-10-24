package com.timstudios.solitairecipher;

import com.timstudios.solitairecipher.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;


public class AboutDialog extends Dialog {

    private TextView aboutTextView;

	public AboutDialog(Context context) {
		super(context);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.aboutdialog_layout);
		aboutTextView = (TextView) findViewById(R.id.textView1);
		aboutTextView.setMovementMethod(new ScrollingMovementMethod());	
	}
}
