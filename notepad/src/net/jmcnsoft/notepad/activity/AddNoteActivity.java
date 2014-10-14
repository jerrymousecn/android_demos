package net.jmcnsoft.notepad.activity;

import java.lang.reflect.Method;

import net.jmcnsoft.notepad.LogUtil;
import net.jmcnsoft.notepad.Note;
import net.jmcnsoft.notepad.db.DataHelper;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.EditText;

public class AddNoteActivity extends FragmentActivity {
	private String TAG = AddNoteActivity.class.getSimpleName();
	private EditText addContentTextView;
	private EditText addTitleTextView;

	private void focusOnContentTextView() {
		this.addContentTextView.requestFocus();
	}

	private void saveNote() {
		try {
			String content = this.addContentTextView.getText().toString();
			String title = this.addTitleTextView.getText().toString();
			if ((content != null) && (!content.isEmpty())) {
				DataHelper localDataHelper = new DataHelper(this);
				Note localNote = new Note();
				localNote.setCreatedTime(Long.valueOf(System
						.currentTimeMillis()));
				localNote.setModifiedTime(Long.valueOf(System
						.currentTimeMillis()));
				localNote.setTitle(title);
				localNote.setContent(content);
				localDataHelper.saveNote(localNote);
			}
			return;
		} catch (Exception localException) {
			LogUtil.e(this.TAG, localException.getMessage());
		}
	}

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.add_note);
		this.addTitleTextView = ((EditText) findViewById(R.id.addTitleTextView));
		this.addContentTextView = ((EditText) findViewById(R.id.addContentTextView));
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