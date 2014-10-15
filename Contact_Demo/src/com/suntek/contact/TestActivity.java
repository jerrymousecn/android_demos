package com.suntek.contact;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * ��ȡ��ϵ�ˡ�ͨ����¼������
 * 
 * @author wwj
 * 
 */
public class TestActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		queryContacts();
	}

	private void queryContacts() {
		// ��ȡ�����������ݵ���Ķ��󣬶���ϵ�˵Ļ�����������ʹ���������
		ContentResolver cr = getContentResolver();
		// ��ѯcontacts������м�¼
		Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
				null, null, null);
		// �����¼��Ϊ��
		if (cursor.getCount() > 0) {
			// �α��ʼָ���ѯ����ĵ�һ����¼���Ϸ���ִ��moveToNext�������ж�
			// ��һ����¼�Ƿ���ڣ�������ڣ�ָ����һ����¼�����򣬷���false��
			while (cursor.moveToNext()) {
				String rawContactId = "";
				// ��Contacts����ȡ��ContactId
				String id = cursor.getString(cursor
						.getColumnIndex(ContactsContract.Contacts._ID));
				Log.v("contactID", id);

				// ��ȡRawContacts����α�
				Cursor rawContactCur = cr.query(RawContacts.CONTENT_URI, null,
						RawContacts._ID + "=?", new String[] { id }, null);
				// �ò�ѯ���һ��ֻ����һ����¼����������ֱ�����α�ָ���һ����¼
				if (rawContactCur.moveToFirst()) {
					// ��ȡ��һ����¼��RawContacts._ID�е�ֵ
					rawContactId = rawContactCur.getString(rawContactCur
							.getColumnIndex(RawContacts._ID));
					Log.v("rawContactID", rawContactId);

				}
				// �ر��α�
				rawContactCur.close();
				// ��ȡ����
				if (Integer
						.parseInt(cursor.getString(cursor
								.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
					// ���ݲ�ѯRAW_CONTACT_ID��ѯ����ϵ�˵ĺ���
					Cursor phoneCur = cr
							.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
									null,
									ContactsContract.CommonDataKinds.Phone.RAW_CONTACT_ID
											+ "=?",
									new String[] { rawContactId }, null);
					// �����ContactsContract.CommonDataKinds.Phone.CONTENT_URI
					// �����������phoneUri����
					// Uri
					// phoneUri=Uri.parse("content://com.android.contacts/data/phones");

					// һ����ϵ�˿����ж�����룬��Ҫ����
					while (phoneCur.moveToNext()) {
						// ��ȡ����
						String number = phoneCur
								.getString(phoneCur
										.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
						Log.v("number", number);
						// ��ȡ��������
						String type = phoneCur
								.getString(phoneCur
										.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
						Log.v("type", type);

					}
					phoneCur.close();

				}
			}
			cursor.close();
		}
	}

	// �½���ϵ��
	public void addContact(String name, String phoneNum) {
		ContentValues values = new ContentValues();
		Uri rawContactUri = getContentResolver().insert(
				RawContacts.CONTENT_URI, values);
		long rawContactId = ContentUris.parseId(rawContactUri);
		// ��data���������
		if (name != "") {
			values.clear();
			values.put(Data.RAW_CONTACT_ID, rawContactId);
			values.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);
			values.put(StructuredName.GIVEN_NAME, name);
			getContentResolver().insert(ContactsContract.Data.CONTENT_URI,
					values);
		}
		// ��data�����绰����
		if (phoneNum != "") {
			values.clear();
			values.put(Data.RAW_CONTACT_ID, rawContactId);
			values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
			values.put(Phone.NUMBER, phoneNum);
			values.put(Phone.TYPE, Phone.TYPE_MOBILE);
			getContentResolver().insert(ContactsContract.Data.CONTENT_URI,
					values);
		}
	}

	// ɾ����ϵ��
	public void deleteContact(long rawContactId) {
		getContentResolver().delete(
				ContentUris.withAppendedId(RawContacts.CONTENT_URI,
						rawContactId), null, null);
	}

	// ������ϵ��
	public void updataCotact(long rawContactId) {
		ContentValues values = new ContentValues();
		values.put(Phone.NUMBER, "13800138000");
		values.put(Phone.TYPE, Phone.TYPE_MOBILE);
		String where = ContactsContract.Data.RAW_CONTACT_ID + "=? AND "
				+ ContactsContract.Data.MIMETYPE + "=?";
		String[] selectionArgs = new String[] { String.valueOf(rawContactId),
				Phone.CONTENT_ITEM_TYPE };
		getContentResolver().update(ContactsContract.Data.CONTENT_URI, values,
				where, selectionArgs);
	}

	@Override
	public void onClick(View v) {

	}

}
