package mangogo.appbase.autolayout;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import mangogo.appbase.autolayout.core.AutoLayoutConfig;
import mangogo.appbase.util.DecimalUtils;

public class AutoUtils {

    public static int getAutoLayoutSize(Context context, int id) {
        float val = getAutoLayoutSizeFloat(context, id, 0);
        return floatToInt(val);
    }

    public static int getAutoLayoutSize(Context context, int id, int defValue) {
        float val = getAutoLayoutSizeFloat(context, id, defValue);
        return floatToInt(val);
    }

    public static float getAutoLayoutSizeFloat(Context context, int id) {
        return getAutoLayoutSizeFloat(context, id, 0);
    }

    public static float getAutoLayoutSizeFloat(Context context, int id, float defValue) {
        TypedValue typedValue = new TypedValue();
        context.getResources().getValue(id, typedValue, true);
        float value = getValueFloat(typedValue);
        if (!Float.isNaN(value)) {
            return value;
        }

        if (typedValue.type == TypedValue.TYPE_DIMENSION) {
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            return TypedValue.complexToDimensionPixelOffset(typedValue.data, metrics);
        }
        return defValue;
    }

    public static int getAutoLayoutSize(Context context, TypedArray typedArray, int index, int defValue) {
        float val = getAutoLayoutSizeFloat(context, typedArray, index, defValue);
        return floatToInt(val);
    }

    public static float getAutoLayoutSizeFloat(Context context, TypedArray typedArray, int index, float defValue) {
        TypedValue typedValue = typedArray.peekValue(index);
        float value = getValueFloat(typedValue);
        if (!Float.isNaN(value)) {
            return value;
        }
        return typedArray.getDimension(index, defValue);
    }

    public static int getValue(float value) {
        float val = getValueFloat(value);
        return floatToInt(val);
    }

    public static int getValueBaseWidth(float value) {
        float val = getValueBaseWidthFloat(value);
        return floatToInt(val);
    }

    public static int getValueBaseHeight(float value) {
        float val = getValueBaseHeightFloat(value);
        return floatToInt(val);
    }

    public static int getValueBasePhysical(float value) {
        float val = getValueBasePhysicalFloat(value);
        return floatToInt(val);
    }

    public static float getValueBaseWidthFloat(float value) {
        return value * AutoLayoutConfig.getWidthRate();
    }

    public static float getValueBaseHeightFloat(float value) {
        return value * AutoLayoutConfig.getHeightRate();
    }

    public static float getValueFloat(float value) {
        return value * AutoLayoutConfig.getDefaultRate();
    }

    public static float getValueBasePhysicalFloat(float value) {
        return value * AutoLayoutConfig.getPhysicalRate();
    }

    public static int floatToInt(float val) {
        if (DecimalUtils.compare(val, 0) != 0) {
            return (val > 0) ? Math.max(Math.round(val), 1) : Math.min(-Math.round(-val), -1);
        }
        return 0;
    }

    public static float getValueFloat(TypedValue typedValue) {
        if (typedValue == null || typedValue.type != TypedValue.TYPE_DIMENSION) {
            return Float.NaN;
        }

        float value = TypedValue.complexToFloat(typedValue.data);
        int complexUnit = TypedValue.COMPLEX_UNIT_MASK & (typedValue.data >> TypedValue.COMPLEX_UNIT_SHIFT);
        switch (complexUnit) {
            case TypedValue.COMPLEX_UNIT_PX:
                return getValueBaseWidthFloat(value);

            case TypedValue.COMPLEX_UNIT_DIP:
                return Float.NaN;

            case TypedValue.COMPLEX_UNIT_SP:
                return Float.NaN;

            case TypedValue.COMPLEX_UNIT_PT:
                return getValueFloat(value);

            case TypedValue.COMPLEX_UNIT_IN:
                return getValueBaseHeightFloat(value);

            case TypedValue.COMPLEX_UNIT_MM:
                return getValueBaseHeightFloat(value);

            default:
                return Float.NaN;
        }
    }
}
