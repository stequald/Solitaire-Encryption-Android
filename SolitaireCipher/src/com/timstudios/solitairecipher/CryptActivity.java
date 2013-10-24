package com.timstudios.solitairecipher;

import java.util.Locale;

import com.timstudios.solitairecipher.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

@SuppressLint("DefaultLocale")
public class CryptActivity extends Activity {
	SolitaireCrypto solitaire;
    private Button encryptButton;
    private Button decryptButton;
    private EditText cleartextEditText;
    private EditText ciphertextEditText;
    private TextView keyStreamTextView;
    private ScrollView svBackground;
    
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crypt_layout);
        solitaire = SolitaireCipherActivity.solitaire;
        encryptButton = (Button) findViewById(R.id.encryptButton);
        decryptButton = (Button) findViewById(R.id.decryptButton);
        cleartextEditText = (EditText) findViewById(R.id.cleartextEditText);
        ciphertextEditText = (EditText) findViewById(R.id.ciphertextEditText);
        keyStreamTextView = (TextView) findViewById(R.id.keyStreamTextView);
        
        encryptButton.setOnClickListener(encryptButtonListener); 
        decryptButton.setOnClickListener(decryptButtonListener); 

        svBackground = (ScrollView)findViewById(R.id.sv_background);
        svBackground.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(svBackground.getWindowToken(), 0);
                return false;
            }
        });
	}
	
	public void updateKeyStreamTextView() {
		String keystream = solitaire.getKeystream();
		keyStreamTextView.setText(keystream);
	}
	
	private OnClickListener encryptButtonListener = new OnClickListener() {
		public void onClick(View v) {
			String keystream = keyStreamTextView.getText().toString().trim();
			if (! SolitaireCipherActivity.isAlpha(keystream)) {
			    SolitaireCipherActivity.showInputErrorDialog(CryptActivity.this, "Error in keystream:", "Should only have alphabetical letters.");
			    return;
			}
			
			String cleartext = cleartextEditText.getText().toString().trim();
			
			if (! SolitaireCipherActivity.isAlpha(cleartext)) {
			    SolitaireCipherActivity.showInputErrorDialog(CryptActivity.this, "Error in clear text:", "Should only have alphabetical letters.");
			    return;
			}
			if (cleartext.length() != solitaire.getKeystream().length()) {
			    SolitaireCipherActivity.showInputErrorDialog(CryptActivity.this, "Error:", "Keystream and clear text length not equivalent.");
			    return;
			}
			
			cleartext = new String(cleartext.toUpperCase(Locale.US));
			cleartextEditText.setText(cleartext);
			cleartextEditText.setSelection(cleartextEditText.getText().length());
			
			solitaire.on_encrypt(cleartext);
			String ciphertext = solitaire.getCiphertext().trim();
			ciphertextEditText.setText(ciphertext, TextView.BufferType.EDITABLE);
		}
    };
     
    private OnClickListener decryptButtonListener = new OnClickListener() {
        public void onClick(View v) {
			String keystream = keyStreamTextView.getText().toString().trim();
			if (! SolitaireCipherActivity.isAlpha(keystream)) {
			    SolitaireCipherActivity.showInputErrorDialog(CryptActivity.this, "Error in keystream:", "Should only have alphabetical letters.");
			    return;
			}
			
			String ciphertext = ciphertextEditText.getText().toString().trim();
			
			if (! SolitaireCipherActivity.isAlpha(ciphertext)) {
			    SolitaireCipherActivity.showInputErrorDialog(CryptActivity.this, "Error in cipher text:", "Should only have alphabetical letters.");
			    return;
			}
			if (ciphertext.length() != solitaire.getKeystream().length()) {
			    SolitaireCipherActivity.showInputErrorDialog(CryptActivity.this, "Error:", "Keystream and cipher text length not equivalent.");
			    return;
			}
			
			ciphertext = new String(ciphertext.toUpperCase(Locale.US));
			ciphertextEditText.setText(ciphertext);
			ciphertextEditText.setSelection(ciphertextEditText.getText().length());
			
	        solitaire.on_decrypt(ciphertext);
			String cleartext = solitaire.getCleartext();
			cleartextEditText.setText(cleartext, TextView.BufferType.EDITABLE);
        } 
    };
}