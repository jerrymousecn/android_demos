package net.jmcnsoft.notepad.activity;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import net.jmcnsoft.notepad.LocalizationUtil;
import net.jmcnsoft.notepad.LogUtil;
import net.jmcnsoft.notepad.db.DataHelper;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
	private String TAG = MainActivity.class.getSimpleName();
	private long exitTime = 0L;
	private ListView listView;

	private List<Map<String, Object>> getData() {
		return new DataHelper(this).getNoteList();
	}

	private Map<String, Object> getItemMap(int paramInt) {
		return (Map) this.listView.getItemAtPosition(paramInt);
	}

	private void initLocalizationUtil() {
		LocalizationUtil.init(this);
	}

	private void refreshDataList() {
		SimpleAdapter localSimpleAdapter = new SimpleAdapter(this, getData(),
				R.layout.listview_item, new String[] { "title" }, new int[] { R.id.titleTextView});
		this.listView.setAdapter(localSimpleAdapter);
		this.listView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(
							AdapterView<?> paramAnonymousAdapterView,
							View paramAnonymousView, int paramAnonymousInt,
							long paramAnonymousLong) {
						Map localMap = MainActivity.this
								.getItemMap(paramAnonymousInt);
						int id = ((Integer) localMap.get("id")).intValue();
						String title = (String) localMap.get("title");
						String content = (String) localMap.get("content");
						Intent localIntent = new Intent(MainActivity.this,
								EditNoteActivity.class);
						Bundle localBundle = new Bundle();
						localBundle.putInt("id", id);
						localBundle.putString("title", title);
						localBundle.putString("content", content);
						localIntent.putExtras(localBundle);
						MainActivity.this.startActivity(localIntent);
					}
				});
		this.listView
				.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
					public void onCreateContextMenu(
							ContextMenu paramAnonymousContextMenu,
							View paramAnonymousView,
							ContextMenu.ContextMenuInfo paramAnonymousContextMenuInfo) {
						MainActivity.this.getMenuInflater().inflate(R.menu.long_press_menu,
								paramAnonymousContextMenu);
						paramAnonymousContextMenu
								.setHeaderTitle(LocalizationUtil
										.getString(R.string.op_on_item));
					}
				});
	}

	public boolean onContextItemSelected(MenuItem paramMenuItem) {
		Integer noteID = (Integer) getItemMap(
				((AdapterView.AdapterContextMenuInfo) paramMenuItem
						.getMenuInfo()).position).get("id");
		switch (paramMenuItem.getItemId()) {
		default:
			return super.onContextItemSelected(paramMenuItem);
		case R.id.action_del:
			new DataHelper(this).delNote(noteID);
			refreshDataList();
			return true;
		}
	}

	protected void onCreate(Bundle paramBundle) {
		LogUtil.v(this.TAG, "onCreate");
		super.onCreate(paramBundle);
		initLocalizationUtil();
		setContentView(R.layout.activity_main);
		this.listView = ((ListView) findViewById(R.id.listView1));
		refreshDataList();
	}

	public boolean onCreateOptionsMenu(Menu paramMenu) {
		getMenuInflater().inflate(R.menu.main_menu, paramMenu);
		return true;
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {// 如果两次按键时间间隔大于2000毫秒，则不退出
				String str = LocalizationUtil
						.getString(R.string.notice_on_double_back);
				Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();// 更新mExitTime
			} else {
				System.exit(0);// 否则退出程序
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
			if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
				try {
					Method m = menu.getClass().getDeclaredMethod(
							"setOptionalIconsVisible", Boolean.TYPE);
					m.setAccessible(true);
					m.invoke(menu, true);
				} catch (Exception e) {
				}
			}
		}
		return super.onMenuOpened(featureId, menu);
	}

	public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
		if (paramMenuItem.getItemId() == R.id.action_new) {
			startActivity(new Intent(this, AddNoteActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(paramMenuItem);
	}

	protected void onPause() {
		super.onPause();
		LogUtil.v(this.TAG, "onPause");
	}

	protected void onRestart() {
		super.onRestart();
		LogUtil.v(this.TAG, "onRestart");
	}

	protected void onRestoreInstanceState(Bundle paramBundle) {
		LogUtil.v(this.TAG, paramBundle.getString("data"));
		super.onRestoreInstanceState(paramBundle);
		LogUtil.v(this.TAG, "onRestoreInstanceState");
	}

	protected void onResume() {
		super.onResume();
		refreshDataList();
		LogUtil.v(this.TAG, "onResume");
	}

	protected void onSaveInstanceState(Bundle paramBundle) {
		paramBundle.putString("data", "example");
		super.onSaveInstanceState(paramBundle);
		LogUtil.v(this.TAG, "onSaveInstanceState");
	}

	protected void onStart() {
		super.onStart();
		LogUtil.v(this.TAG, "onStart");
	}
}