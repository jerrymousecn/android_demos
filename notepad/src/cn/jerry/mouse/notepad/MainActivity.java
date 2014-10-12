package cn.jerry.mouse.notepad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		DataHelper dataHelper = new DataHelper(this);
//		Note note = new Note();
//		note.setTitle("test1");
//		note.setContent("testccccc1");
//		dataHelper.SaveNote(note);
//		Toast.makeText(this, dataHelper.GetUserList().get(0).getContent(), Toast.LENGTH_LONG).show();
//		SimpleAdapter adapter = new SimpleAdapter(this,getData(),R.layout.listview_item,
//				new String[]{"title"},
//				new int[]{R.id.textView1});
//		ListView listView = (ListView)this.findViewById(R.id.listView1);
//		listView.setAdapter(adapter);
		Intent intent = new Intent(this,NewNoteActivity.class);
		this.startActivity(intent);
	}
	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", "G1");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "G2");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "G3");
		list.add(map);
		
		return list;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_new) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
