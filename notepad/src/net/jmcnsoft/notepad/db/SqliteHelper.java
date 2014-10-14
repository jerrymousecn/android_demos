package net.jmcnsoft.notepad.db;

import net.jmcnsoft.notepad.LogUtil;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteHelper
  extends SQLiteOpenHelper
{
  public static final String TB_NAME = "note";
  private String TAG = SqliteHelper.class.getSimpleName();
  
  public SqliteHelper(Context paramContext, String paramString, SQLiteDatabase.CursorFactory paramCursorFactory, int paramInt)
  {
    super(paramContext, paramString, paramCursorFactory, paramInt);
  }
  
  public void onCreate(SQLiteDatabase paramSQLiteDatabase)
  {
    paramSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS note(id integer primary key autoincrement,title text,content text,created_time integer,modified_time integer)");
    LogUtil.e("Database", "onCreate");
  }
  
  public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
  {
    paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS note");
    onCreate(paramSQLiteDatabase);
  }
  
  public void updateColumn(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2, String paramString3)
  {
    try
    {
      paramSQLiteDatabase.execSQL("ALTER TABLE note CHANGE " + paramString1 + " " + paramString2 + " " + paramString3);
      return;
    }
    catch (Exception localException)
    {
      LogUtil.printException(this.TAG, localException);
    }
  }
}


/* Location:           D:\tmp\notepadV1.0_dex2jar1.jar
 * Qualified Name:     cn.jerry.mouse.notepad.db.SqliteHelper
 * JD-Core Version:    0.7.0.1
 */