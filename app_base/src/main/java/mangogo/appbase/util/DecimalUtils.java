package mangogo.appbase.util;



public class DecimalUtils {

    public static final double DOUBLE_MIN_SCALE = 1.0E-15;
    public static final double FLOAT_MIN_SCALE_D = 1.0E-6;
    public static final float FLOAT_MIN_SCALE_F = 1.0E-6f;

    public static int compare(float value1, float value2) {
        return compare(value1, value2, FLOAT_MIN_SCALE_F);
    }

    public static int compare(double value1, double value2) {
        return compare(value1, value2, true);
    }

    public static int compare(float value1, double value2) {
        return compare(value1, value2, true);
    }

    public static int compare(double value1, float value2) {
        return compare(value1, value2, true);
    }

    public static int compare(double value1, double value2, boolean useFloatMinScale) {
        if (useFloatMinScale) {
            return compare(value1, value2, FLOAT_MIN_SCALE_D);
        }
        return compare(value1, value2, DOUBLE_MIN_SCALE);
    }

    public static int compare(double value1, double value2, double scale) {
        double value = Math.abs(value1 - value2);
        if (value < Math.abs(scale)) {
            return 0;
        }

        if (value1 > value2) {
            return 1;
        }

        if (value2 > value1) {
            return -1;
        }

        if (Double.isNaN(value1)) {
            if (Double.isNaN(value2)) {
                return 0;
            }
            return 1;
        }

        if (Double.isNaN(value2)) {
            return -1;
        }

        return 0;
    }

    public static int compare(float value1, float value2, float scale) {
        float value = Math.abs(value1 - value2);
        if (value < Math.abs(scale)) {
            return 0;
        }

        if (value1 > value2) {
            return 1;
        }

        if (value2 > value1) {
            return -1;
        }

        if (Float.isNaN(value1)) {
            if (Float.isNaN(value2)) {
                return 0;
            }
            return 1;
        }

        if (Float.isNaN(value2)) {
            return -1;
        }

        return 0;
    }
}
