package net.jmcnsoft.notepad;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

public class LinedEditText
  extends EditText
{
  private Paint mPaint = new Paint();
  private Rect mRect = new Rect();
  
  public LinedEditText(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mPaint.setStyle(Paint.Style.STROKE);
    this.mPaint.setColor(-2147483393);
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    int i = getLineCount();
    Rect localRect = this.mRect;
    Paint localPaint = this.mPaint;
    for (int j = 0;; j++)
    {
      if (j >= i)
      {
        super.onDraw(paramCanvas);
        return;
      }
      int k = getLineBounds(j, localRect);
      paramCanvas.drawLine(localRect.left, k + 5, localRect.right, k + 5, localPaint);
    }
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    return super.onKeyDown(paramInt, paramKeyEvent);
  }
}


/* Location:           D:\tmp\notepadV1.0_dex2jar1.jar
 * Qualified Name:     cn.jerry.mouse.notepad.LinedEditText
 * JD-Core Version:    0.7.0.1
 */