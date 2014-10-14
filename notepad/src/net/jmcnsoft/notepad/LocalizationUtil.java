package net.jmcnsoft.notepad;

import android.content.Context;

public class LocalizationUtil
{
  private static String TAG = LocalizationUtil.class.getSimpleName();
  private static Context context = null;
  
  public static String getString(int paramInt)
  {
    if (context != null) {
      return context.getString(paramInt);
    }
    LogUtil.e(TAG, "LocalizationUtil not initilized. context is null.");
    return null;
  }
  
  public static String getString(int paramInt, Object... paramVarArgs)
  {
    if (context != null) {
      return context.getString(paramInt, paramVarArgs);
    }
    LogUtil.e(TAG, "LocalizationUtil not initilized. context is null.");
    return null;
  }
  
  public static void init(Context paramContext)
  {
    context = paramContext;
  }
}


/* Location:           D:\tmp\notepadV1.0_dex2jar1.jar
 * Qualified Name:     cn.jerry.mouse.notepad.LocalizationUtil
 * JD-Core Version:    0.7.0.1
 */