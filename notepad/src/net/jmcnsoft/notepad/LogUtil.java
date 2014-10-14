package net.jmcnsoft.notepad;

import android.util.Log;

public class LogUtil
{
  public static int DEBUG = 4;
  public static int ERROR;
  public static int INFO;
  public static int LOG_LEVEL = 6;
  public static int VERBOS = 5;
  public static int WARN;
  
  static
  {
    ERROR = 1;
    WARN = 2;
    INFO = 3;
  }
  
  public static void d(String paramString1, String paramString2)
  {
    if (LOG_LEVEL > DEBUG) {
      Log.d(paramString1, paramString2);
    }
  }
  
  public static void e(String paramString1, String paramString2)
  {
    if (LOG_LEVEL > ERROR) {
      Log.e(paramString1, paramString2);
    }
  }
  
  private static String getStackString(Throwable paramThrowable)
  {
    StackTraceElement[] arrayOfStackTraceElement = paramThrowable.getStackTrace();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramThrowable.toString());
    localStringBuilder.append("\n");
    int i = arrayOfStackTraceElement.length;
    for (int j = 0;; j++)
    {
      if (j >= i)
      {
        String str = localStringBuilder.toString();
        Throwable localThrowable = paramThrowable.getCause();
        if (localThrowable != null) {
          str = printStackTraceAsCause(arrayOfStackTraceElement, localThrowable) + str;
        }
        return str;
      }
      localStringBuilder.append(arrayOfStackTraceElement[j].toString());
      localStringBuilder.append("\n");
    }
  }
  
  public static void i(String paramString1, String paramString2)
  {
    if (LOG_LEVEL > INFO) {
      Log.i(paramString1, paramString2);
    }
  }
  
  public static void printException(String paramString, Exception paramException)
  {
    if (LOG_LEVEL > ERROR) {
      Log.e(paramString, getStackString(paramException));
    }
  }
  
  private static String printStackTraceAsCause(StackTraceElement[] paramArrayOfStackTraceElement, Throwable paramThrowable)
  {
    StackTraceElement[] arrayOfStackTraceElement = paramThrowable.getStackTrace();
    int i = -1 + arrayOfStackTraceElement.length;
    int j = -1 + paramArrayOfStackTraceElement.length;
    StringBuilder localStringBuilder = new StringBuilder();
    if ((i < 0) || (j < 0) || (!arrayOfStackTraceElement[i].equals(paramArrayOfStackTraceElement[j])))
    {
      localStringBuilder.append(paramThrowable.toString());
      localStringBuilder.append("\n");
    }
    for (int k = 0;; k++)
    {
      if (k > i)
      {
        String str = localStringBuilder.toString();
        Throwable localThrowable = paramThrowable.getCause();
        if (localThrowable != null) {
          str = printStackTraceAsCause(arrayOfStackTraceElement, localThrowable) + str;
        }
        return str;
      }
      localStringBuilder.append(arrayOfStackTraceElement[k].toString());
      localStringBuilder.append("\n");
    }
  }
  
  public static void v(String paramString1, String paramString2)
  {
    if (LOG_LEVEL > VERBOS) {
      Log.v(paramString1, paramString2);
    }
  }
  
  public static void w(String paramString1, String paramString2)
  {
    if (LOG_LEVEL > WARN) {
      Log.w(paramString1, paramString2);
    }
  }
}


/* Location:           D:\tmp\notepadV1.0_dex2jar1.jar
 * Qualified Name:     cn.jerry.mouse.notepad.LogUtil
 * JD-Core Version:    0.7.0.1
 */