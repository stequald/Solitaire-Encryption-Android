package com.timstudios.solitairecipher;

import java.lang.String;
import java.util.Arrays;
import java.util.*;
import android.util.Log;

public class SolitaireCrypto {
	public static final int MAXCARDS = 54;
    short deck[];
    String keystream;
    String cleartext;
    String ciphertext;
    String passkey;
    String deckString;

    public SolitaireCrypto() {
    	deck = new short[MAXCARDS];
    	resetDeck();
    	keystream = new String("");
    	cleartext = new String("");
    	ciphertext = new String("");
    	passkey = new String("");
    	deckString = new String(getDeck());
    }

    public void setKeystream(String str) {
    	str = new String(str.toUpperCase(Locale.US));
    	keystream = new String(str);
    }
    public void setCleartext(String str) {
    	str = new String(str.toUpperCase(Locale.US));
    	cleartext = new String(str);
    }
    public void setCiphertext(String str) {
    	str = new String(str.toUpperCase(Locale.US));
    	ciphertext = new String(str);
    }
    public void setPasskey(String str) {
    	str = new String(str.toUpperCase(Locale.US));
    	passkey = new String(str);
    }
    
    public void setDeck(short newDeck[]) {
    	deck = newDeck.clone();
    }
    
    public void setDeckString(String str) {
    	deckString = new String(str);
    }
    
    public String getKeystream() {
    	return keystream;
    }

    public String getCleartext() {
    	return cleartext;
    }

    public String getCiphertext() {
    	return ciphertext;
    }

    public String getPasskey() {
    	return passkey;
    }
    
    public String getDeckString() {
    	return deckString;
    }
    	
    void resetDeck() {
        for(int i=0;i<MAXCARDS;i++) {deck[i]=(short) (i+1);}
    }
    
    public String getDeck() {
    	//System.out.println(Arrays.toString(deck));
	    //Log.i("myLogger", "deck: "+getDeck());
    	return Arrays.toString(deck);
    }
    	
    public void shuffle() {
    	shuffleArray();
    }

    // Implementing FisherÐYates shuffle
    private void shuffleArray() {
      Random rnd = new Random();
      for (int i = deck.length - 1; i > 0; i--)
      {
    	  short index = (short) rnd.nextInt(i + 1);
    	  // Simple swap
    	  short a = deck[index];
    	  deck[index] = deck[i];
    	  deck[i] = a;
      }
    }
    
    private void jo1shift() {
        short jo1pos=0;
        short stor[] = new short[2];

    	for(short i=0;deck[i]!=53;i++) {jo1pos=(short) (i+1);}
    	
    	if(jo1pos==53) {
    		stor[0]=deck[0];
    		stor[1]=deck[0];
            for(short i=1;i<MAXCARDS;i++) {
    			stor[i%2]=deck[i];
    			deck[i]=stor[(i+1)%2];
    		}
    		deck[0]=53;
    	}
    	if(jo1pos!=53) {
    		stor[0]=deck[jo1pos+1];
    		deck[jo1pos+1]=53;
    		deck[jo1pos]=stor[0];
    	}
    }
    
    private void jo2shift() {
    	short jo2pos=0;
        short stor[] = new short[2];

        for(short i=0;deck[i]!=MAXCARDS;i++) {jo2pos=(short) (i+1);}

    	if(jo2pos==52) {
    		stor[0]=deck[jo2pos-52];
    		stor[1]=deck[jo2pos-52];
    		for(short i=(short) (jo2pos-51);i<53;i++) {
    			stor[i%2]=deck[i];
    			deck[i]=stor[(i+1)%2];
    		}
            deck[jo2pos-51]=MAXCARDS;
    	}

    	if(jo2pos==53) {
    		stor[0]=deck[jo2pos-52];
    		stor[1]=deck[jo2pos-52];
            for(short i=(short) (jo2pos-51);i<MAXCARDS;i++) {
    			stor[i%2]=deck[i];
    			deck[i]=stor[(i+1)%2];
    		}
            deck[jo2pos-51]=MAXCARDS;
    	}
    	if(jo2pos<52) {
    		stor[0]=deck[jo2pos+1];
    		stor[1]=deck[jo2pos+2];
            deck[jo2pos+2]=MAXCARDS;
    		deck[jo2pos]=stor[0];
    		deck[jo2pos+1]=stor[1];
    	}
        
    }
    
    private void triplecut() {
    	short jo1pos=0;
    	short jo2pos=0;
    	
    	for(short i=0;deck[i]!=53;i++) {jo1pos=(short) (i+1);}
        for(short i=0;deck[i]!=MAXCARDS;i++) {jo2pos=(short) (i+1);}

    	if(jo1pos<jo2pos) {
            short topdeck[] = new short[jo1pos];
            short bottomdeck[] = new short[53-jo2pos];
            short middeck[] = new short[jo2pos-jo1pos+1];

    		for(short i=0;i<jo1pos;i++) {topdeck[i]=deck[i];}
    		for(short i=0;i<(jo2pos-jo1pos+1);i++) {middeck[i]=deck[i+jo1pos];}
    		for(short i=0;i<(53-jo2pos);i++) {bottomdeck[i]=deck[i+jo2pos+1];}
    		for(short i=0;i<(53-jo2pos);i++) {deck[i]=bottomdeck[i];}
    		for(short i=0;i<(jo2pos-jo1pos+1);i++) {deck[i+53-jo2pos]=middeck[i];}
    		for(short i=0;i<(jo1pos);i++) {deck[i+53-jo2pos+jo2pos-jo1pos+1]=topdeck[i];}
    	}

    	if(jo2pos<jo1pos) {
            short topdeck[] = new short[jo2pos];
            short bottomdeck[] = new short[53-jo1pos];
            short middeck[] = new short[jo1pos-jo2pos+1];

    		for(short i=0;i<jo2pos;i++) {topdeck[i]=deck[i];}
    		for(short i=0;i<(jo1pos-jo2pos+1);i++) {middeck[i]=deck[i+jo2pos];}
    		for(short i=0;i<(53-jo1pos);i++) {bottomdeck[i]=deck[i+jo1pos+1];}
    		for(short i=0;i<(53-jo1pos);i++) {deck[i]=bottomdeck[i];}
    		for(short i=0;i<(jo1pos-jo2pos+1);i++) {deck[i+53-jo1pos]=middeck[i];}
    		for(short i=0;i<(jo2pos);i++) {deck[i+53-jo1pos+jo1pos-jo2pos+1]=topdeck[i];}
    	}
    }
    
    private void countcut(short count) {
        if(count==MAXCARDS) {count=53;}

        short topdeck[] = new short[count];
        short bottomdeck[] = new short[53-count];

    	for(int i=0;i<count;i++) {topdeck[i]=deck[i];}
    	for(int i=0;i<53-count;i++) {bottomdeck[i]=deck[i+count];}
    	for(int i=0;i<53-count;i++) {deck[i]=bottomdeck[i];}
    	for(int i=0;i<count;i++) {deck[i+53-count]=topdeck[i];}   
    }
    
    private short output() {
        short index=deck[0];
        if(index==MAXCARDS) {index=53;}
        return deck[index];
    }
    
    public void solitaire() {
        jo1shift();
        jo2shift();
        triplecut();
        countcut(deck[53]);
    }
    

    private short charCodeAt(char c) {
	    return (short)c;
	}
    
    private char fromCharCode(short i) {
	    return (char)i;
	}
	
	
	public void keydeck() {
		resetDeck();
		for(int i=0;i<passkey.length();i++) {
			solitaire();
			countcut(charCodeAt((char) (passkey.charAt(i)-64)));
		}
	}
	
	public void keygen(int msgLen) {
		keystream = new String("");
		for(int k=0; k< msgLen; k++) {
	        solitaire();
			if(output()<53) {
				keystream = keystream+fromCharCode((short)(((output()-1)%26)+65));

			}
			if(output()>=53) {
				k--;
			}
		}
	}
	
	public String encrypt() {
		if (keystream.length() != cleartext.length()) {
	        ciphertext = new String("");
	        return "";
		}
	 
		String output = new String("");
		int keywork;
		int clearwork;
		int outwork;
	
	    for(int i=0; i<keystream.length(); i++) {
	        keywork=charCodeAt(keystream.charAt(i));
			keywork=((keywork-64)%26);
			clearwork=charCodeAt(cleartext.charAt(i));
			if(clearwork>=65&&clearwork<=90) {clearwork=((clearwork-64)%26);}
			if(clearwork>=97&&clearwork<=122) {clearwork=((clearwork-96)%26);}
			outwork=((clearwork+keywork)%26);
			outwork=(outwork+64);
			if(outwork==64) {outwork=90;}
			output = output+fromCharCode((short)outwork);
		}
	
        ciphertext = new String(output);
	    return output;
	}
	
	
	public String decrypt() {
	    if (keystream.length() != ciphertext.length()) {
	        cleartext = new String("");
	        return "";
	    }
	
		String output = new String("");
		int keywork;
		int cipherwork;
		int outwork;		
	
	    for(int i=0; i<keystream.length(); i++) {
	        keywork=charCodeAt(keystream.charAt(i));
			keywork=((keywork-64)%26);
			cipherwork=charCodeAt(ciphertext.charAt(i));
			cipherwork=((cipherwork-64)%26);
			outwork= ((cipherwork-keywork)%26);
			if(outwork<0) {outwork=(26+outwork);}
			outwork= (outwork+64);
			if(outwork==64) {outwork=90;}
			output = output+fromCharCode((short)outwork);
		}
	    cleartext = new String(output);
		return output;
	}
	
	
	public void on_keyDeck(String passkey) {
		setPasskey(passkey);
		keydeck();
	    Log.i("myLogger", "deck: "+getDeck());
	}
	
	public void on_shuffle() {
		shuffle();
	    Log.i("myLogger", "deck: "+getDeck());
	}
	
	public String on_generateKeystream(int msgLen) {
		keygen(msgLen);
	    Log.i("myLogger", "generateKeystream from current deck. keystream:\n"+getKeystream());
	    return getKeystream();
	}
	
	public String on_encrypt(String clearText) {
		setCleartext(clearText);
		encrypt();
	    Log.i("myLogger", "ciphertext: "+getCiphertext());
	    return getCiphertext();
	}
	
	public String on_decrypt(String cipherText) {	
		setCiphertext(cipherText);
		decrypt();
	    Log.i("myLogger", "clearText: "+getCleartext());
	    return getCleartext();
	}
}
