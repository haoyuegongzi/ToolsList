package com.mydemo.toolslist.tools;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.TextView;

/**
 * Created by chen1 on 2017/10/30.
 * TO DO:
 */

public class AdaptiveSizeTextView extends TextView {
    private static float DEFAULT_MIN_TEXT_SIZE = 10;
    private static float DEFAULT_MAX_TEXT_SIZE = 85;


    private Paint testPaint;
    private float minTextSize, maxTextSize;
    private int textWidth;
    private String sText;

    public AdaptiveSizeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialise();
    }

    public AdaptiveSizeTextView(Context context) {
        super(context);
    }

    public AdaptiveSizeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        textWidth = this.getWidth();
        sText = text.toString();
        //        refitText(sText);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        refitText(sText);
    }

    private void initialise() {
        testPaint = new Paint();
        testPaint.set(this.getPaint());
        // max size defaults to the intially specified text size unless it is
        // too small
        maxTextSize = this.getTextSize();
        if (maxTextSize <= DEFAULT_MAX_TEXT_SIZE) {
            maxTextSize = DEFAULT_MAX_TEXT_SIZE;
        }
        minTextSize = DEFAULT_MIN_TEXT_SIZE;
    }

    ;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    }

    /**
     * Re size the font so the specified text fits in the text box * assuming
     * the text box is the specified width.
     */
    public void refitText(String text) {
        //直接根据字符长度来调整字体大小  最大长度为20
        if (textWidth > 0) {
            //获取改TextView的画布可用大小
            int availableWidth = textWidth - this.getPaddingLeft() - this.getPaddingRight();
            float trySize = maxTextSize;
            float scaled = getContext().getResources().getDisplayMetrics().scaledDensity;
            //模拟 注意乘以scaled
            testPaint.setTextSize(trySize * scaled);
            while ((trySize > minTextSize) && (testPaint.measureText(text) > availableWidth)) {
                trySize -= 2;
                Paint.FontMetrics fm = testPaint.getFontMetrics();
                double rowFontHeight = (Math.ceil(fm.descent - fm.top) + 2);
                //也是行数  所有字的总长度/textview的有效宽度
                float scaled2 = (float) ((testPaint.measureText(text) / availableWidth));
                //1.9代表是1.9的行高（1个字体本身，0.9的行距 ，大致差不多，没有实际测过）
                if ((scaled2 * rowFontHeight * 1.9) < this.getHeight()) {
                    break;
                }
                if (trySize <= minTextSize) {
                    trySize = minTextSize;
                    break;
                }
                testPaint.setTextSize(trySize * scaled);
            }
            this.setTextSize(trySize);
        }
        postInvalidate();
    }
}
