package com.suntek.contact;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.ListView;

import com.suntek.contact.adapter.ContactListAdapter;
import com.suntek.contact.model.ContactBean;
import com.suntek.contact.view.QuickAlphabeticBar;

/**
 * 联系人列表
 * 
 * @author Administrator
 * 
 */
public class ContactListActivity extends Activity {

	private ContactListAdapter adapter;
	private ListView contactListView;
	private List<ContactBean> list;
	private AsyncQueryHandler asyncQueryHandler; // 异步查询数据库类对象
	private QuickAlphabeticBar alphabeticBar; // 快速索引条

	private Map<Integer, ContactBean> contactIdMap = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_list_view);
		contactListView = (ListView) findViewById(R.id.contact_list);
		
		contactListView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {  
			  
			@Override  
			public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {  
				MenuInflater inflater = getMenuInflater();
				inflater.inflate(R.menu.contact_menu, menu);
				menu.setHeaderTitle("联系人操作");

				menu.add(0,123,0,"test");

//				super.onCreateContextMenu(menu, v, menuInfo);
			}  
		});  
		//registerForContextMenu(contactListView); 
		
		alphabeticBar = (QuickAlphabeticBar) findViewById(R.id.fast_scroller);

		// 实例化
		asyncQueryHandler = new MyAsyncQueryHandler(getContentResolver());
		init();

	}

	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.contact_menu, menu);

		super.onCreateContextMenu(menu, v, menuInfo);
	}

	public boolean onContextItemSelected(MenuItem item) {
		final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		// info.position
		switch (item.getItemId()) {
		case 123:
			Log.v("Contact menu test", "test");
			break;
		case R.id.call_contact:
			ListView listView = (ListView)info.targetView;
			Object obj = listView.getItemAtPosition(info.position);
			Log.v("Contact menu test", "Call");
			break;
		case R.id.del_contact:
			Log.v("Contact menu test", "Del");
			break;
		}
		return super.onContextItemSelected(item);
	}

	/**
	 * 初始化数据库查询参数
	 */
	private void init() {
		Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI; // 联系人Uri；
		// 查询的字段
		String[] projection = { ContactsContract.CommonDataKinds.Phone._ID,
				ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
				ContactsContract.CommonDataKinds.Phone.DATA1, "sort_key",
				ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
				ContactsContract.CommonDataKinds.Phone.PHOTO_ID,
				ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY };
		// 按照sort_key升序查詢
		asyncQueryHandler.startQuery(0, null, uri, projection, null, null,
				"sort_key COLLATE LOCALIZED asc");

	}

	/**
	 * 
	 * @author Administrator
	 * 
	 */
	private class MyAsyncQueryHandler extends AsyncQueryHandler {

		public MyAsyncQueryHandler(ContentResolver cr) {
			super(cr);
		}

		@Override
		protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
			if (cursor != null && cursor.getCount() > 0) {
				contactIdMap = new HashMap<Integer, ContactBean>();
				list = new ArrayList<ContactBean>();
				cursor.moveToFirst(); // 游标移动到第一项
				for (int i = 0; i < cursor.getCount(); i++) {
					cursor.moveToPosition(i);
					String name = cursor.getString(1);
					String number = cursor.getString(2);
					String sortKey = cursor.getString(3);
					int contactId = cursor.getInt(4);
					Long photoId = cursor.getLong(5);
					String lookUpKey = cursor.getString(6);

					if (contactIdMap.containsKey(contactId)) {
						// 无操作
					} else {
						// 创建联系人对象
						ContactBean contact = new ContactBean();
						contact.setDesplayName(name);
						contact.setPhoneNum(number);
						contact.setSortKey(sortKey);
						contact.setPhotoId(photoId);
						contact.setLookUpKey(lookUpKey);
						list.add(contact);

						contactIdMap.put(contactId, contact);
					}
				}
				if (list.size() > 0) {
					setAdapter(list);
				}
			}

			super.onQueryComplete(token, cookie, cursor);
		}

	}

	private void setAdapter(List<ContactBean> list) {
		adapter = new ContactListAdapter(this, list, alphabeticBar);
		contactListView.setAdapter(adapter);
		alphabeticBar.init(ContactListActivity.this);
		alphabeticBar.setListView(contactListView);
		alphabeticBar.setHight(alphabeticBar.getHeight());
		alphabeticBar.setVisibility(View.VISIBLE);
	}
}
