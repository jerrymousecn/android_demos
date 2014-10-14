package net.jmcnsoft.notepad.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.jmcnsoft.notepad.Note;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DataHelper
{
  private static String DB_NAME = "ntoepad.db";
  private static int DB_VERSION = 1;
  private SQLiteDatabase db;
  private SqliteHelper dbHelper;
  
  public DataHelper(Context paramContext)
  {
    this.dbHelper = new SqliteHelper(paramContext, DB_NAME, null, DB_VERSION);
    this.db = this.dbHelper.getWritableDatabase();
  }
  
  private String getTitleWhenNull(String title, String content)
  {
    int i;
    if ((title == null) || (title.isEmpty()))
    {
      if (content.length() <= 10) {
        return content;
      }
      else
      {
    	  int index = content.indexOf("\n");
    	  if(index!=-1)
    	  {
    		  title = content.substring(0, index);  
    	  }
      }
    }
    return title;
  }
  
  public void Close()
  {
    this.db.close();
    this.dbHelper.close();
  }
  
  public void delNote(Integer noteID)
  {
    SQLiteDatabase localSQLiteDatabase = this.db;
    localSQLiteDatabase.delete("note", "id=?", new String[]{noteID+""});
  }
  
  public List<Map<String, Object>> getNoteList()
  {
    ArrayList localArrayList = new ArrayList();
    Cursor localCursor = this.db.query("note", null, null, null, null, null, "modified_time DESC");
    localCursor.moveToFirst();
    for (;;)
    {
      if (localCursor.isAfterLast())
      {
        localCursor.close();
        return localArrayList;
      }
      Integer localInteger = Integer.valueOf(localCursor.getInt(0));
      String str1 = localCursor.getString(1);
      String str2 = localCursor.getString(2);
      String str3 = getTitleWhenNull(str1, str2);
      HashMap localHashMap = new HashMap();
      localHashMap.put("id", localInteger);
      localHashMap.put("title", str3);
      localHashMap.put("content", str2);
      localArrayList.add(localHashMap);
      localCursor.moveToNext();
    }
  }
  
  public void saveNote(Note paramNote)
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("title", paramNote.getTitle());
    localContentValues.put("content", paramNote.getContent());
    localContentValues.put("modified_time", paramNote.getModifiedTime());
    if (paramNote.getId() != null)
    {
      localContentValues.put("id", paramNote.getId());
      this.db.replaceOrThrow("note", "id", localContentValues);
      return;
    }
    localContentValues.put("created_time", paramNote.getCreatedTime());
    this.db.insert("note", "id", localContentValues);
  }
}


/* Location:           D:\tmp\notepadV1.0_dex2jar1.jar
 * Qualified Name:     cn.jerry.mouse.notepad.db.DataHelper
 * JD-Core Version:    0.7.0.1
 */