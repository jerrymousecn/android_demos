package com.example.contacttest;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.Data;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ContactOPActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_op);

		Button addContactButton = (Button)findViewById(R.id.delContactButton);
		Button delContactButton = (Button)findViewById(R.id.addContactButton);
		
		addContactButton.setOnClickListener(this);
		delContactButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		EditText nameEditText = (EditText)findViewById(R.id.nameEditText);
		EditText phoneNumEditText = (EditText)findViewById(R.id.phoneNumEditText);
		EditText emailEditText = (EditText)findViewById(R.id.emailEditText);
		String name = nameEditText.getText().toString();
		String phoneNum = phoneNumEditText.getText().toString();
		String email = emailEditText.getText().toString();
		
		switch (v.getId()) {
		case R.id.delContactButton:
			delContact(name);
			Toast.makeText(this, "Deleted "+name, Toast.LENGTH_LONG).show();
			break;
		case R.id.addContactButton: 
			addContact(name,phoneNum,email);
			Toast.makeText(this, "Added "+name, Toast.LENGTH_LONG).show();
			break;
		}
	}

	private void delContact(String name) {
		Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
		ContentResolver resolver = this.getApplicationContext()
				.getContentResolver();
		Cursor cursor = resolver.query(uri, new String[] { Data._ID },
				"display_name=?", new String[] { name }, null);
		if (cursor.moveToFirst()) {
			int id = cursor.getInt(0);
			resolver.delete(uri, "display_name=?", new String[] { name });
			uri = Uri.parse("content://com.android.contacts/data");
			resolver.delete(uri, "raw_contact_id=?", new String[] { id + "" });
		}
	}

	private void addContact(String name,String phoneNum,String email) {
		Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
		ContentResolver resolver = this.getApplicationContext()
				.getContentResolver();
		ContentValues values = new ContentValues();
		long contac_id = ContentUris.parseId(resolver.insert(uri, values));
		uri = Uri.parse("content://com.android.contacts/data");
		values.put("raw_contact_id", contac_id);
		values.put(Data.MIMETYPE, "vnd.android.cursor.item/name");
		values.put("data2", name);
		values.put("data1", name);
		resolver.insert(uri, values);
		values.clear();
		values.put("raw_contact_id", contac_id);
		values.put(Data.MIMETYPE, "vnd.android.cursor.item/phone_v2");
		values.put("data2", "2");
		values.put("data1", phoneNum);
		resolver.insert(uri, values);
		values.clear();
		values.put("raw_contact_id", contac_id);
		values.put(Data.MIMETYPE, "vnd.android.cursor.item/email_v2");
		values.put("data2", "2");
		values.put("data1", email);
		resolver.insert(uri, values);
		values.clear();
	}
}
