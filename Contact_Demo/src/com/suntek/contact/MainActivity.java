package com.suntek.contact;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.Data;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 获取联系人、通话记录、短信
 * 
 * @author wwj
 * 
 */
public class MainActivity extends Activity implements OnClickListener {
	private Button btnLoadContacts;
	private Button btnGetRecentContacts;
	private Button btnGetSMS;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btnLoadContacts = (Button) findViewById(R.id.btn_load_contacts);
		btnGetRecentContacts = (Button) findViewById(R.id.btn_get_recent_contact);
		btnGetSMS = (Button) findViewById(R.id.btn_get_sms);

		btnLoadContacts.setOnClickListener(this);
		btnGetRecentContacts.setOnClickListener(this);
		btnGetSMS.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.btn_load_contacts: // 获取联系人
			intent = new Intent(this, ContactListActivity.class);
			break;
		case R.id.btn_get_recent_contact: // 获取通话记录
			intent = new Intent(this, ContactRecordListActivity.class);
			break;
		case R.id.btn_get_sms: // 获取短信记录
			intent = new Intent(this, SMSListActivity.class);
			break;
		}
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.contact_menu, menu);
		return true;
	}
	private void delContact()
	{
		String name = "jerry";
		Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
		ContentResolver resolver = this.getApplicationContext().getContentResolver();
		Cursor cursor = resolver.query(uri, new String[]{Data._ID}, "display_name=?", new String[]{name}, null);
		if(cursor.moveToFirst())
		{
			int id = cursor.getInt(0);
			resolver.delete(uri, "display_name=?", new String[]{name});
			uri = Uri.parse("content://com.android.contacts/data");
			resolver.delete(uri, "raw_contact_id=?", new String[]{id+""});
		}
	}
	private void addContact()
	{
		String name = "jerry";
		String phoneNum = "1350000000";
		String email = "test@xx.com";
		Uri uri =  Uri.parse("content://com.android.contacts/raw_contacts");
		ContentResolver resolver = this.getApplicationContext().getContentResolver();
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
