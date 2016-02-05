package com.example.marianilgatest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

/**  
 * 
 * @author Maria Nilga
 *
 */ 

public class MainActivity extends AppCompatActivity implements OnClickListener,
		OnTouchListener, DialogCodeCountry.OnDialogFinishListener {

	private Button butGetCode ;
	private Button butConfirm;
	private EditText editPhoneNumber;
	private EditText editCode;
	
	/** A filter to input a phone code*/
	private final String regexPhone = "\\+\\d{2}\\ \\d{3}\\ \\d{3}\\ \\d{2} \\d{2}"; 
	/** A filter to input a code*/
	private final String regexCode = "\\d{4}"; 
	
	private int lengthCurrent = 0;
	private int lengthPre = 0;
	
	private InputMethodManager lManager;
	
	    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Set all views.  
		setupView();
		
		// Set the filters to input.
		editPhoneNumber.setFilters(  
		    new InputFilter[] {  
		        new RegexInputFilter(regexPhone)  
		    }  
		); 
		
		editCode.setFilters(  
			    new InputFilter[] {  
			        new RegexInputFilter(regexCode)  
			    }  
			); 
		
		// Set the listeners to get a changes in a edittext.
		editPhoneNumber.addTextChangedListener(textWatcherPhone);   
		editCode.addTextChangedListener(textWatcherCode);
		editPhoneNumber.setOnTouchListener(this);
		
		lManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
	}  
    
	    
	/**
	 *  Method sets up all views and listeners.
	 */
	private void setupView() {
		
		ImageButton butCountryCode = (ImageButton)findViewById(R.id.selectCountryCode);
		butGetCode = (Button)findViewById(R.id.butGetCode);
		butConfirm = (Button)findViewById(R.id.butConfirm);
		
		editPhoneNumber = (EditText)findViewById(R.id.editPhoneNumber);
		editCode = (EditText)findViewById(R.id.editCode);
		
		butCountryCode.setOnClickListener(this);
		butGetCode.setOnClickListener(this);
		butConfirm.setOnClickListener(this);
		
	}


	/** A textwatcher to edit phone number   */
	TextWatcher textWatcherPhone = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,	int after) {
			String str = editPhoneNumber.getText().toString(); 
			lengthPre = str.length();
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			String str = editPhoneNumber.getText().toString(); 
			lengthCurrent = str.length();

			// Check if user input text or delete it.
			if (lengthCurrent >= lengthPre) {
				// Set spaces between the numbers of phone.
				if (str.length() == 3 || str.length() == 7
						|| str.length() == 11 || str.length() == 14) {
					editPhoneNumber.append(" ");
				}
			}

			String value = str.toString();
			if (value.matches(regexPhone)) {
				// All numbers of the phone were entered.
				editPhoneNumber.setTextColor(Color.BLACK);
				butGetCode.setEnabled(true);
				
				// Hide a keyboard.
				lManager.hideSoftInputFromWindow(editPhoneNumber.getWindowToken(), 0);
			} else {
				// All numbers of the phone were NOT entered.
				editPhoneNumber.setTextColor(Color.RED);
				butGetCode.setEnabled(false);
				butConfirm.setEnabled(false);
				editCode.setText("");
			}
		}
	};
	 
	
	
	/** A textwatcher to edit code */  
	TextWatcher textWatcherCode = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,	int after) {		}
		
		@Override
		public void afterTextChanged(Editable s) {
			if (s.length()==4) {
				// Hide a keyboard if all numbers of the code were entered.
				lManager.hideSoftInputFromWindow(editCode.getWindowToken(), 0);
			}
		}
	};
	
	
	@Override
	public void onClick(View v) {
		
		int id = v.getId();
		switch(id) {
		case R.id.selectCountryCode:
			
			DialogCodeCountry dialogCodeCountry = new DialogCodeCountry();
			dialogCodeCountry.show(getSupportFragmentManager(), "tag");
			break;
			
		case R.id.butGetCode:
			
			butConfirm.setEnabled(true);
			editCode.setText("");
			Toast.makeText(this, R.string.system_send_sms, Toast.LENGTH_LONG).show();
			break;
		case R.id.butConfirm:
			
			break;
		}
		
	}


	

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event) {

		if (v.getId() == R.id.editPhoneNumber) {
			String str = editPhoneNumber.getText().toString(); 
			if (str.length() == 0) {
				editPhoneNumber.setText("+");
				editPhoneNumber.setSelection(1);
			}
		}
		return false;
	}
 


	// This method is called when the dialog is finished.
	@Override
	public void onDialogFinish(String code) {
		
		if (code != null) {
			Log.i("probaLog", "str.length() = " + code.length());
			if (code.length()==3) {
				editPhoneNumber.setText(code + " ");
			}
			else {
				editPhoneNumber.setText(code);
			}
			
			String str = editPhoneNumber.getText().toString();
			editPhoneNumber.setSelection(str.length());
			editPhoneNumber.requestFocus();
			
			// Show a keyboard.
			InputMethodManager manager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
			manager.showSoftInput(editPhoneNumber, 0);
		}		
	}


	
	
	
}
