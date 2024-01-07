package com.zaozhuang.newborn.util;


import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

//import com.ctb.opencar.view.text.MyButton;
//import com.ctb.opencar.view.text.MyEditText;
//import com.ctb.opencar.view.text.MyTextView;

public class TypefaceUtils {

    private static Typeface sTypeface = null;
    private static Typeface sBoldTypeface = null;

    public static void init(Context context) {
//        try {
//            sTypeface = Typeface.createFromAsset(context.getAssets(), "regular.bin");
//            sBoldTypeface = Typeface.createFromAsset(context.getAssets(), "bold.bin");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

//    public static View onCreateView(String name, Context context, AttributeSet attrs) {
//        if (sTypeface != null) {
//            if ("TextView".equalsIgnoreCase(name)) {
//                return new MyTextView(context, attrs);
//            }
//
//            if ("EditText".equalsIgnoreCase(name)) {
//                return new MyEditText(context, attrs);
//            }
//
//            if ("Button".equalsIgnoreCase(name)) {
//                return new MyButton(context, attrs);
//            }
//        }
//        return null;
//    }

    public static Typeface getTypeface(Paint textPaint, Typeface tf) {
        return getTypeface(textPaint, TypefaceUtils.isBold(tf), TypefaceUtils.isItalic(tf));
    }

    public static Typeface getTypeface(Paint textPaint, int style) {
        boolean isBold = ((style & Typeface.BOLD) != 0);
        boolean isItalic = ((style & Typeface.ITALIC) != 0);
        return getTypeface(textPaint, isBold, isItalic);
    }

    private static Typeface getTypeface(Paint textPaint, boolean isBold, boolean isItalic) {
        Typeface typeface = sTypeface;
        if (typeface == null) {
            return null;
        }

        if (isBold) {
            if (sBoldTypeface != null) {
                typeface = sBoldTypeface;
                setFakeBoldText(textPaint, false);
            } else {
                setFakeBoldText(textPaint, true);
            }
        } else {
            setFakeBoldText(textPaint, false);
        }

        setTextItalic(textPaint, isItalic);
        return typeface;
    }

    private static boolean isBold(Typeface typeface) {
        return typeface != null && typeface.isBold();
    }

    private static boolean isItalic(Typeface typeface) {
        return typeface != null && typeface.isItalic();
    }

    private static void setFakeBoldText(Paint textPaint, boolean bold) {
        if (textPaint != null) {
            textPaint.setFakeBoldText(bold);
        }
    }

    private static void setTextItalic(Paint textPaint, boolean italic) {
        if (textPaint != null) {
            textPaint.setTextSkewX(italic ? -0.25f : 0);
        }
    }

    public static void setTextBold(TextView textView) {
        textView.setTypeface(Typeface.DEFAULT_BOLD);
    }

    public static void setTextBold(Paint textPaint, boolean bold) {
        textPaint.setTypeface(bold ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
    }


    public static void setTitleTypeface(Context context,TextView tv) {
        tv.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/titlebar.otf"));
    }

    public static void setTitleTypeface(Context context, TextView tv, String title) {
        tv.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/titlebar.otf"));
        tv.setText(title);
    }

}
