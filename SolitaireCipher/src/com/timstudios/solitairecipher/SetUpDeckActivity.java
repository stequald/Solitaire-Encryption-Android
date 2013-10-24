package com.timstudios.solitairecipher;

import java.util.Locale;

import com.timstudios.solitairecipher.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;


public class SetUpDeckActivity extends Activity {
	SolitaireCrypto solitaire;
    private Button keyButton;
    private Button shuffleButton;
    private EditText deckEditText;
    private EditText keyEditText;
    private ScrollView svBackground;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setupdeck_layout);
        solitaire = SolitaireCipherActivity.solitaire;

        keyButton = (Button) findViewById(R.id.keyButton);
        shuffleButton = (Button) findViewById(R.id.shuffleButton);
        deckEditText = (EditText) findViewById(R.id.deckEditText);
        keyEditText = (EditText) findViewById(R.id.keyEditText);

        keyButton.setOnClickListener(keyButtonListener); 
        shuffleButton.setOnClickListener(shuffleButtonListener); 
        
		String deck = solitaire.getDeck();
		deckEditText.setText(deck, TextView.BufferType.EDITABLE);
        
		deckEditText.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
    			String deck = deckEditText.getText().toString().trim();
            	solitaire.setDeckString(deck);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void afterTextChanged(Editable s) {
            }
        });
		
        svBackground = (ScrollView)findViewById(R.id.sv_background);
        svBackground.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(svBackground.getWindowToken(), 0);
                return false;
            }
        });
    }

	public void updateDeckEditText() {
		String deck = solitaire.getDeckString();
		deckEditText.setText(deck);
	}

    private OnClickListener keyButtonListener = new OnClickListener() {
		public void onClick(View v) {
			String key = keyEditText.getText().toString().trim();;
			
			if (! SolitaireCipherActivity.isAlpha(key)) {
			    SolitaireCipherActivity.showInputErrorDialog(SetUpDeckActivity.this, "Error:", "Input only alphabetical letter.");
			    return;
			}
			
			key = new String(key.toUpperCase(Locale.US));
			keyEditText.setText(key);
			keyEditText.setSelection(keyEditText.getText().length());

			solitaire.on_keyDeck(key);
			String deck = solitaire.getDeck();
			deckEditText.setText(deck, TextView.BufferType.EDITABLE);
		}
    };
     
    private OnClickListener shuffleButtonListener = new OnClickListener() {
        public void onClick(View v) {
            solitaire.on_shuffle();
			String deck = solitaire.getDeck();
			deckEditText.setText(deck, TextView.BufferType.EDITABLE);
        } 
    };
}
