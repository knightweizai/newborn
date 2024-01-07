package mangogo.appbase.autolayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import java.lang.reflect.Method;

import mangogo.appbase.autolayout.core.AutoLayoutConfig;
//import mangogo.appbase.autolayout.layout.AutoFrameLayout;
//import mangogo.appbase.autolayout.layout.AutoLinearLayout;
//import mangogo.appbase.autolayout.layout.AutoRelativeLayout;
//import mangogo.appbase.autolayout.ndk.MethodFix;
import mangogo.appbase.util.ReflectUtils;


public class AutoLayout {

    public static void setDesignPpi(float designPpi) {
        AutoLayoutConfig.setDesignPpi(designPpi);
    }

    public static void init(Context context) {
        AutoLayoutConfig.init(context);
        //hookMethodApplyDimension();
    }

//    public static View onCreateView(String name, Context context, AttributeSet attrs) {
//        if (name == null) {
//            return null;
//        }
//
//        String nameLower = name.toLowerCase();
//        if (nameLower.equals("linearlayout") || nameLower.endsWith(".linearlayout")) {
//            return new AutoLinearLayout(context, attrs);
//        }
//
//        if (nameLower.equals("framelayout") || nameLower.endsWith(".framelayout")) {
//            return new AutoFrameLayout(context, attrs);
//        }
//
//        if (nameLower.equals("relativelayout") || nameLower.endsWith(".relativelayout")) {
//            return new AutoRelativeLayout(context, attrs);
//        }
//        return null;
//    }

    public static View onMyCreateView(String name, Context context, AttributeSet attrs) {
//        if ("LinearLayout".equalsIgnoreCase(name)) {
//            return new MyLinearLayout(context, attrs);
//        }
//
//        if ("FrameLayout".equalsIgnoreCase(name)) {
//            return new MyFrameLayout(context, attrs);
//        }
//
//        if ("RelativeLayout".equalsIgnoreCase(name)) {
//            return new MyRelativeLayout(context, attrs);
//        }
        return null;
    }

    private static void hookMethodApplyDimension() {
//        MethodFix.init();
        Method method = ReflectUtils.getMethod(TypedValue.class, "applyDimension", int.class, float.class, DisplayMetrics.class);
        Method myMethod = ReflectUtils.getMethod(AutoLayout.class, "applyDimension", int.class, float.class, DisplayMetrics.class);
//        MethodFix.hookMethod(method, myMethod);
    }

    public static float applyDimension(
            int unit, float value, DisplayMetrics metrics) {
        switch (unit) {
            case TypedValue.COMPLEX_UNIT_PX:
                return AutoUtils.getValueBaseWidthFloat(value);
            case TypedValue.COMPLEX_UNIT_DIP:
                return value * metrics.density;
            case TypedValue.COMPLEX_UNIT_SP:
                return value * metrics.scaledDensity;
            case TypedValue.COMPLEX_UNIT_PT:
                return AutoUtils.getValueFloat(value);
            case TypedValue.COMPLEX_UNIT_IN:
                return AutoUtils.getValueBasePhysicalFloat(value);
            case TypedValue.COMPLEX_UNIT_MM:
                return AutoUtils.getValueBaseHeightFloat(value);
        }
        return 0;
    }
}
