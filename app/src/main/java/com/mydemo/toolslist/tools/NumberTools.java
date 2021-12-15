package com.mydemo.toolslist.tools;

import android.graphics.Paint;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class NumberTools {
    /**
     * 返回当前单位附加值，万或亿
     * @Name getUnit
     * @Description TODO
     * @param scale
     * @return
     * @return String
     * @Author chen1
     * @Date 2019年8月1日 下午2:44:37
     *
     */
    public static String getUnit(double scale) {
        String unit = "";
        if (scale >= 100000000) {
            unit = "亿";
        } else if (scale >= 10000000) {
            unit = "千万";
        } else if (scale >= 1000000) {
            unit = "百万";
        } else if (scale >= 100000) {
            unit = "十万";
        } else if (scale >= 10000) {
            unit = "万";
        } else if (scale >= 1000) {
            unit = "千";
        }else {
            unit = "";
        }
        return unit;
    }

    /**
     * 保留小数点后2位
     * @Name getDecimal
     * @Description TODO
     * @param d
     * @return
     * @return String
     * @Author chen1
     * @Date 2019年8月1日 下午2:45:49
     *
     */
    public static String getDecimal(double d) {
        DecimalFormat df2 = new DecimalFormat("0.00");
        return df2.format(d);
    }

    /**
     * 保留小数点后2位
     * @Name getDecimal
     * @Description TODO
     * @param d
     * @return
     * @return String
     * @Author chen1
     * @Date 2019年8月1日 下午2:46:30
     *
     */
    public static String getDecimal(float d) {
        DecimalFormat df2 = new DecimalFormat("0.00");
        return df2.format(d);
    }

    /**
     * 禁止数据显示出科学计数法的格式
     * @Name forbidScientificNotate
     * @Description TODO
     * @param limit
     * @return
     * @return String
     * @Author chen1
     * @Date 2019年8月1日 下午2:47:01
     *
     */
    public static String forbidScientificNotate(double limit){
        String s = "";
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        s = nf.format(limit);
        return s;
    }

    /**
     * 禁止数据显示出科学计数法的格式
     * @Name forbidScientificNotate
     * @Description TODO
     * @param limit
     * @return
     * @return String
     * @Author chen1
     * @Date 2019年8月1日 下午2:47:38
     *
     */
    public static String forbidScientificNotate(float limit){
        String s = "";
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        s = nf.format(limit);
        return s;
    }

    /**
     * 保留小数点后指定的位数
     * @Name getDecimal
     * @Description TODO
     * @param d
     * @param format:"0.00" "0.000"
     * @return
     * @return String
     * @Author chen1
     * @Date 2019年8月1日 下午2:48:56
     *
     */
    public static String getDecimal(double d, String format) {
        DecimalFormat df2 = new DecimalFormat(format);
        return df2.format(d);
    }

    /**
     *禁止使用科学计数法
     * @Name forbidScientify
     * @Description TODO
     * @param zxje
     * @return
     * @return String
     * @Author chen1
     * @Date 2019年8月1日 下午2:48:19
     */
    public static String forbidScientify(String zxje){
        try{
            BigDecimal bd = new BigDecimal(zxje);
            double zxjeF = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            DecimalFormat df = new DecimalFormat("0.00");
            zxje = df.format(zxjeF) + "";
        }catch(Exception e){
            e.printStackTrace();
        }
        return zxje;
    }

    /**
     * 得到字符串总体长度
     * @param text
     * @param size
     * @return
     */
    public static int getCharWidth(String text, float size) {
        if (null == text || "".equals(text)) {
            return 0;
        }
        Paint paint = new Paint();
        paint.setTextSize(size);
        int text_width = (int) paint.measureText(text);
        return text_width;
    }

    /**
     * 每一个字符的长度
     * @param text
     * @param size
     * @return
     */
    public static int getEveryCharWidth(String text, float size) {
        if (null == text || "".equals(text)) {
            return 0;
        }
        Paint paint = new Paint();
        paint.setTextSize(size);
        int text_width = (int) paint.measureText(text);
        int width = text_width/text.length();
        return width;
    }

    /**
     * edittext只能输入数值的时候做最大最小的限制
     * @param edit
     * @param MIN_MARK
     * @param MAX_MARK
     */
    public static void setRegion(final EditText edit, final double MIN_MARK, final double MAX_MARK) {
        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (start > 1) {
                    if (MIN_MARK != -1 && MAX_MARK != -1) {
                        double num = Double.parseDouble(s.toString());
                        if (num > MAX_MARK) {
                            s = String.valueOf(MAX_MARK);
                            edit.setText(s);
                        } else if (num < MIN_MARK) {
                            s = String.valueOf(MIN_MARK);
                            edit.setText(s);
                        }
                        edit.setSelection(s.length());
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable != null && !editable.equals("")) {
                    if (MIN_MARK != -1 && MAX_MARK != -1) {
                        double markVal = 0;
                        try {
                            markVal = Double.parseDouble(editable.toString());
                        } catch (NumberFormatException e) {
                            markVal = 0;
                        }
                        if (markVal > MAX_MARK) {
                            edit.setText(String.valueOf(MAX_MARK));
                            edit.setSelection(String.valueOf(MAX_MARK).length());
                        }
                        return;
                    }
                }
            }
        });
    }
}
