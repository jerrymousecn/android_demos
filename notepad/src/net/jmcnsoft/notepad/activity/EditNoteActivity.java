package net.jmcnsoft.notepad.activity;

import java.lang.reflect.Method;

import net.jmcnsoft.notepad.LogUtil;
import net.jmcnsoft.notepad.Note;
import net.jmcnsoft.notepad.db.DataHelper;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.EditText;

public class EditNoteActivity extends FragmentActivity {
	private String TAG = EditNoteActivity.class.getSimpleName();
	private EditText editConentTextView;
	private EditText editTitleTextView;
	private Note note = new Note();

	private void focusOnContentTextView() {
		this.editConentTextView.requestFocus();
	}

	private void saveNote() {
		try {
			String content = this.editConentTextView.getText().toString();
			String title = this.editTitleTextView.getText().toString();
			DataHelper localDataHelper = new DataHelper(this);
			this.note.setContent(content);
			this.note.setTitle(title);
			this.note.setModifiedTime(Long.valueOf(System.currentTimeMillis()));
			if ((content != null) && (!content.isEmpty())) {
				localDataHelper.saveNote(this.note);
				return;
			}
			localDataHelper.delNote(this.note.getId());
			return;
		} catch (Exception localException) {
			LogUtil.e(this.TAG, localException.getMessage());
		}
	}

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.edit_note);
		this.editConentTextView = ((EditText) findViewById(2131034176));
		this.editTitleTextView = ((EditText) findViewById(2131034175));
		Intent localIntent = getIntent();
		Integer localInteger = Integer
				.valueOf(localIntent.getIntExtra("id", 0));
		String str1 = localIntent.getStringExtra("title");
		String str2 = localIntent.getStringExtra("content");
		this.note.setId(localInteger);
		this.note.setContent(str2);
		this.editTitleTextView.setText(str1);
		this.editConentTextView.setText(str2);
		focusOnContentTextView();
	}

	public boolean onCreateOptionsMenu(Menu paramMenu) {
		getMenuInflater().inflate(R.menu.save_note_menu, paramMenu);
		return true;
	}

	public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
		if ((paramInt == KeyEvent.KEYCODE_BACK)
				&& (paramKeyEvent.getAction() == KeyEvent.ACTION_DOWN)) {
			saveNote();
		}
		return super.onKeyDown(paramInt, paramKeyEvent);
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
		if (paramMenuItem.getItemId() == R.id.action_save) {
			saveNote();
			finish();
			return true;
		}
		return super.onOptionsItemSelected(paramMenuItem);
	}
}
