package cn.jerry.mouse.notepad;

import android.content.Context;

import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteDatabase.CursorFactory;

import android.database.sqlite.SQLiteOpenHelper;

import android.util.Log;

public class SqliteHelper extends SQLiteOpenHelper {

	public static final String TB_NAME = "note";

	public SqliteHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	// 创建表

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS " +
		TB_NAME + "(" +
		Note.ID + " integer primary key autoincrement," +
		Note.TITLE + " varchar," +
		Note.CONTENT + " varchar)"
		);
		Log.e("Database", "onCreate");
	}

	// 更新表

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TB_NAME);
		onCreate(db);
		Log.e("Database", "onUpgrade");
	}

	// 更新列

	public void updateColumn(SQLiteDatabase db, String oldColumn,
			String newColumn, String typeColumn) {
		try {
			db.execSQL("ALTER TABLE " +
			TB_NAME + " CHANGE " +
			oldColumn + " " + newColumn +
			" " + typeColumn
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}