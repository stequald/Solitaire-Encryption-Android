package com.timstudios.solitairecipher;

import com.timstudios.solitairecipher.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

public class GenKeyStreamActivity extends Activity {
	SolitaireCrypto solitaire;
    private EditText msgLenEditText;
    private Button keyGenButton;
    private EditText keyStreamEditText;
    private TextView deckTextView;
    private ScrollView svBackground;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.genkeystream_layout);
        solitaire = SolitaireCipherActivity.solitaire;

        msgLenEditText = (EditText) findViewById(R.id.msgLenEditText);
        keyStreamEditText = (EditText) findViewById(R.id.keyStreamEditText);
        keyGenButton = (Button) findViewById(R.id.keyGenButton);
        deckTextView = (TextView) findViewById(R.id.deckTextView);

        keyGenButton.setOnClickListener(generateKeystreamButtonListener);
     
        	
        keyStreamEditText.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
    			String keyStream = keyStreamEditText.getText().toString().trim();
    			solitaire.setKeystream(keyStream);
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
    
	public void updateDeckTextView() {
		String deck = solitaire.getDeckString();
		deckTextView.setText(deck);
	}

    private OnClickListener generateKeystreamButtonListener = new OnClickListener() {
        public void onClick(View v) {
        	updateDeckTextView();
        	String deck = deckTextView.getText().toString();

        	short[] newDeck = StringArrayToShortArray(deck);
        	if (! isValidDeck(GenKeyStreamActivity.this, newDeck)) return;
        	
        	solitaire.setDeck(newDeck);        		

        	String msgLen = msgLenEditText.getText().toString();
			if (msgLen.length() == 0) {
    		    SolitaireCipherActivity.showInputErrorDialog(GenKeyStreamActivity.this, "Error:", "No message length entered.");
    		    return;
			}
        	
			int len = Integer.parseInt(msgLen);
			solitaire.on_generateKeystream(len);
			String keyStream = solitaire.getKeystream();
			keyStreamEditText.setText(keyStream, TextView.BufferType.EDITABLE);
			
			deck = solitaire.getDeck();
			deckTextView.setText(deck);
			solitaire.setDeckString(deck);
        }
    };
    
	static public short[] StringArrayToShortArray(String array) {

		String[] items = array.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(" ", "").split(",");
		short[] results = new short[items.length];

		for (int i = 0; i < items.length; i++) {
		    try {
		    	results[i] = Short.parseShort(items[i]);
		    } catch (NumberFormatException nfe) {
		        return null;
		    };
		}
		return results;
	}	
	static public boolean isValidDeck(Activity activity, short[] deck) {
    	if (deck == null) {
		    SolitaireCipherActivity.showInputErrorDialog(activity, "Error:", "Deck is Invalid.");
		    return false;
    	}
    	if (deck.length != SolitaireCrypto.MAXCARDS) {
		    SolitaireCipherActivity.showInputErrorDialog(activity, "Error:", "Deck length is "+deck.length+", should be 54.");
		    return false;
    	}
    	short invalidNumber = containsInvalidNumbers(deck);
    	if (invalidNumber != -1) {
		    SolitaireCipherActivity.showInputErrorDialog(activity, "Error:", "Deck contains invalid number "+invalidNumber+".");
		    return false;
    	}
    	short duplicateNum = containsDuplicates(deck);
    	if (duplicateNum != -1) {
		    SolitaireCipherActivity.showInputErrorDialog(activity, "Error:", "Deck contains duplicate number "+duplicateNum+".");
		    return false;
    	}
    	return true;
	}	
	
	static private short containsInvalidNumbers(short[] deck) {
		//returns invalid number, if none returns -1
		for (int i = 0; i <deck.length; i++)
			if (deck[i] < 1 || deck[i] > SolitaireCrypto.MAXCARDS)
				return deck[i];

		return -1;		    
	}
	
	static private short containsDuplicates(short[] deck) {
		//returns duplicate number, if none returns -1
		for (int j=0;j<deck.length;j++)
		  for (int k=j+1;k<deck.length;k++)
		    if (k!=j && deck[k] == deck[j])
		    	return deck[k];		
		return -1;		    
	}
}