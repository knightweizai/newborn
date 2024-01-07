package mangogo.appbase.util;


import android.view.View;
import android.view.ViewGroup;


public class ViewUtils {

    public static int[] getViewLocationOnScreen(View view) {
        int[] location = new int[2];
        try {
            view.getLocationOnScreen(location);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return location;
    }

    public static boolean setViewWidth(View view, int width) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams != null && layoutParams.width != width) {
            layoutParams.width = width;
            return true;
        }
        return false;
    }

    public static boolean setViewWH(View view, int width,int height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams != null && layoutParams.width != width) {
            layoutParams.width = width;
        }
        if (layoutParams != null && layoutParams.height != height) {
            layoutParams.height = height;
        }
        return false;
    }

    public static void setViewSize(View view, int size) {
        setViewSize(view, size, size);
    }

    public static void setViewSize(View view, int width, int height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams != null) {
            boolean changed = layoutParams.width != width || layoutParams.height != height;
            if (changed) {
                layoutParams.width = width;
                layoutParams.height = height;
                view.requestLayout();
            }
        }
    }

    public static boolean setViewHeight(View view, int height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams != null && layoutParams.height != height) {
            layoutParams.height = height;
            return true;
        }
        return false;
    }

    public static boolean setViewMarginHorizontal(View view, int margin) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)layoutParams;
            if (params.leftMargin != margin && params.rightMargin!=margin) {
                params.leftMargin = margin;
                params.rightMargin = margin;
                return true;
            }
        }
        return false;
    }

    public static boolean setViewMarginLeft(View view, int leftMargin) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)layoutParams;
            if (params.leftMargin != leftMargin) {
                params.leftMargin = leftMargin;
                return true;
            }
        }
        return false;
    }


    public static boolean setViewMarginRight(View view, int rightMargin) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)layoutParams;
            if (params.rightMargin != rightMargin) {
                params.rightMargin = rightMargin;
                return true;
            }
        }
        return false;
    }

    public static boolean setViewMarginTop(View view, int topMargin) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)layoutParams;
            if (params.topMargin != topMargin) {
                params.topMargin = topMargin;
                return true;
            }
        }
        return false;
    }

    public static boolean setViewMarginBottom(View view, int bottomMargin) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)layoutParams;
            if (params.bottomMargin != bottomMargin) {
                params.bottomMargin = bottomMargin;
                return true;
            }
        }
        return false;
    }

    public static int getViewMarginLeft(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return ((ViewGroup.MarginLayoutParams)layoutParams).leftMargin;
        }
        return 0;
    }

    public static int getViewMarginRight(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return ((ViewGroup.MarginLayoutParams)layoutParams).rightMargin;
        }
        return 0;
    }

    public static int getViewMarginTop(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return ((ViewGroup.MarginLayoutParams)layoutParams).topMargin;
        }
        return 0;
    }

    public static int getViewMarginBottom(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return ((ViewGroup.MarginLayoutParams)layoutParams).bottomMargin;
        }
        return 0;
    }

    public static boolean setViewPaddingLeft(View view, int paddingLeft) {
        if (view.getPaddingLeft() != paddingLeft) {
            int top = view.getPaddingTop();
            int right = view.getPaddingRight();
            int bottom = view.getPaddingBottom();
            view.setPadding(paddingLeft, top, right, bottom);
            return true;
        }
        return false;
    }

    public static boolean setViewPaddingRight(View view, int paddingRight) {
        if (view.getPaddingRight() != paddingRight) {
            int left = view.getPaddingLeft();
            int top = view.getPaddingTop();
            int bottom = view.getPaddingBottom();
            view.setPadding(left, top, paddingRight, bottom);
            return true;
        }
        return false;
    }

    public static boolean setViewPaddingTop(View view, int paddingTop) {
        if (view.getPaddingTop() != paddingTop) {
            int left = view.getPaddingLeft();
            int right = view.getPaddingRight();
            int bottom = view.getPaddingBottom();
            view.setPadding(left, paddingTop, right, bottom);
            return true;
        }
        return false;
    }

    public static boolean setViewPaddingBottom(View view, int paddingBottom) {
        if (view.getPaddingBottom() != paddingBottom) {
            int left = view.getPaddingLeft();
            int top = view.getPaddingTop();
            int right = view.getPaddingRight();
            view.setPadding(left, top, right, paddingBottom);
            return true;
        }
        return false;
    }

    public static boolean isVisible(View view) {
        if (view != null && view.getWindowToken() != null) {
            while (view.getVisibility() == View.VISIBLE) {
                if (view.getParent() instanceof View) {
                    view = (View) view.getParent();
                    continue;
                }
                return true;
            }
        }
        return false;
    }
}
