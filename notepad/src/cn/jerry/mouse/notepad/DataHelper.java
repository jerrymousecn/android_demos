package cn.jerry.mouse.notepad;

import java.io.ByteArrayInputStream;

import java.io.ByteArrayOutputStream;

import java.util.ArrayList;

import java.util.List;




import android.content.ContentValues;

import android.content.Context;

import android.database.Cursor;

import android.database.sqlite.SQLiteDatabase;

import android.graphics.Bitmap;

import android.graphics.drawable.Drawable;

import android.util.Log;

public class DataHelper {
     private static String DB_NAME = "ntoepad.db";
     private static int DB_VERSION = 1;
     private SQLiteDatabase db;
     private SqliteHelper dbHelper;
     public DataHelper(Context context) {
           dbHelper = new SqliteHelper(context, DB_NAME, null, DB_VERSION );
           db = dbHelper.getWritableDatabase();
     }

     public void Close() {
           db.close();
           dbHelper.close();
     }

     public List<Note> GetUserList() {
          List<Note> userList = new ArrayList<Note>();
          Cursor cursor = db.query(SqliteHelper. TB_NAME, null, null , null, null,
                    null, Note. ID + " DESC");
          cursor.moveToFirst();
           while (!cursor.isAfterLast() && (cursor.getString(1) != null )) {
              Note note = new Note();
              note.setId(cursor.getInt(0));
              note.setTitle(cursor.getString(1));
              note.setContent(cursor.getString(2));
              userList.add(note);
              cursor.moveToNext();
          }
          cursor.close();
           return userList;
     }

//     public int UpdateNote(String userName, Bitmap userIcon, String UserId) {
//
//          ContentValues values = new ContentValues();
//
//          values.put(Note. USERNAME, userName);
//
//           // BLOB类型
//
//           final ByteArrayOutputStream os = new ByteArrayOutputStream();
//
//           // 将Bitmap压缩成PNG编码，质量为100%存储
//
//          userIcon.compress(Bitmap.CompressFormat. PNG, 100, os);
//
//           // 构造SQLite的Content对象，这里也可以使用raw
//
//          values.put(Note. USERICON, os.toByteArray());
//
//           int id = db.update(SqliteHelper. TB_NAME, values, Note.USERID + "=?" , new String[]{UserId});
//
//          Log. e("UpdateNote2", id + "");
//
//           return id;
//
//     }
//
//
//
//
//     // 更新users表的记录
//
//     public int UpdateNote(Note note) {
//
//          ContentValues values = new ContentValues();
//
//          values.put(Note. USERID, note.getUserId());
//
//          values.put(Note. TOKEN, note.getToken());
//
//          values.put(Note. TOKENSECRET, note.getTokenSecret());
//
//           int id = db.update(SqliteHelper. TB_NAME, values, Note.USERID + "="
//
//                   + note.getUserId(), null);
//
//          Log. e("UpdateNote", id + "");
//
//           return id;
//
//     }


     public Long SaveNote(Note note) {
          ContentValues values = new ContentValues();
          values.put(Note.TITLE, note.getTitle());
          values.put(Note.CONTENT, note.getContent());
          Long uid = db.insert(SqliteHelper. TB_NAME, Note.ID, values);
          Log. e("SaveNote", uid + "");
           return uid;
     }




//     // 删除users表的记录
//
//     public int DelNote(String UserId) {
//
//           int id = db.delete(SqliteHelper. TB_NAME,
//
//                   Note. USERID + "=?", new String[]{UserId});
//
//          Log. e("DelNote", id + "");
//
//           return id;
//
//     }

//     public static Note getUserByName(String userName,List<Note> userList){
//
//          Note Note = null;
//
//           int size = userList.size();
//
//           for( int i=0;i<size;i++){
//
//                if(userName.equals(userList.get(i).getUserName())){
//
//                   Note = userList.get(i);
//
//                    break;
//
//              }
//
//          }
//
//           return Note;
//
//     }    

}