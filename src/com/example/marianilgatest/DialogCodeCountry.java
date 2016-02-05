package com.example.marianilgatest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;


/**
 * @author Maria Nilga
 * 
 * This dialog shows names of phone codes of countries. 
 *
 */
public class DialogCodeCountry extends DialogFragment {
	
	public interface OnDialogFinishListener {
	    public abstract void onDialogFinish(String code);
	}
	
	private OnDialogFinishListener mListener;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		mListener = (OnDialogFinishListener) getActivity();
		
		String[] nameCodes = getResources().getStringArray(R.array.name_code);
		final String[] codes = getResources().getStringArray(R.array.code);
		
		ListView listView = new ListView(getActivity());
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, nameCodes);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					
				dismiss();		
				mListener.onDialogFinish(codes[position]);
			}
		});
 
		
		// Create dialog.
		AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
		adb.setView(listView);
		return adb.create();
	}
		
}

 
