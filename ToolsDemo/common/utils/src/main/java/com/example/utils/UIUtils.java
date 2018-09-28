package com.example.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

public class UIUtils {

    public static final int LAYOUT_PARAMS_KEEP_OLD = -3;

    public static final char ELLIPSIS_CHAR = '\u2026';

    public static float sp2px(Context context, int sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }

    public static float sp2px(Context context, float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static float dip2Px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return dipValue * scale + 0.5f;
    }

    public static float dip2Px(Context context, int dipValue) {
        return dip2Px(context,(float)dipValue);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public static int setColorAlpha(int color, int alpha) {
        if (alpha > 0xff) {
            alpha = 0xff;
        } else if (alpha < 0) {
            alpha = 0;
        }
        return (color & 0xffffff) | (alpha * 0x1000000);
    }

    public static int[] getLocationInAncestor(View child, View ancestor) {
        if (child == null || ancestor == null) {
            return null;
        }
        int[] location = new int[2];
        float[] position = new float[2];
        position[0] = position[1] = 0.0f;

        position[0] += child.getLeft();
        position[1] += child.getTop();

        boolean matched = false;
        ViewParent viewParent = child.getParent();
        while (viewParent instanceof View) {
            final View view = (View) viewParent;
            if (viewParent == ancestor) {
                matched = true;
                break;
            }
            position[0] -= view.getScrollX();
            position[1] -= view.getScrollY();

            position[0] += view.getLeft();
            position[1] += view.getTop();

            viewParent = view.getParent();
        }
        if (!matched) {
            return null;
        }
        location[0] = (int) (position[0] + 0.5f);
        location[1] = (int) (position[1] + 0.5f);
        return location;
    }

    /**
     * get location of view relative to given upView. get center location if
     * getCenter is true.
     */
    public static void getLocationInUpView(View upView, View view, int[] loc, boolean getCenter) {
        if (upView == null || view == null || loc == null || loc.length < 2) {
            return;
        }
        upView.getLocationInWindow(loc);
        int x1 = loc[0];
        int y1 = loc[1];
        view.getLocationInWindow(loc);
        int x2 = loc[0] - x1;
        int y2 = loc[1] - y1;
        if (getCenter) {
            int w = view.getWidth();
            int h = view.getHeight();
            x2 = x2 + w / 2;
            y2 = y2 + h / 2;
        }
        loc[0] = x2;
        loc[1] = y2;
    }

    public static void updateLayout(View view, int w, int h) {
        if (view == null)
            return;
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params == null || (params.width == w && params.height == h))
            return;
        if (w != LAYOUT_PARAMS_KEEP_OLD)
            params.width = w;
        if (h != LAYOUT_PARAMS_KEEP_OLD)
            params.height = h;
        view.setLayoutParams(params);
    }

    public static void updateLayoutMargin(View view, int l, int t, int r, int b) {
        if (view == null)
            return;
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params == null)
            return;
        if (params instanceof ViewGroup.MarginLayoutParams) {
            updateMargin(view, (ViewGroup.MarginLayoutParams) params, l, t, r, b);
        }
    }

    private static void updateMargin(View view, ViewGroup.MarginLayoutParams params, int l, int t, int r, int b) {
        if (view == null
            || params == null
            || (params.leftMargin == l && params.topMargin == t && params.rightMargin == r && params.bottomMargin == b))
            return;
        if (l != LAYOUT_PARAMS_KEEP_OLD)
            params.leftMargin = l;
        if (t != LAYOUT_PARAMS_KEEP_OLD)
            params.topMargin = t;
        if (r != LAYOUT_PARAMS_KEEP_OLD)
            params.rightMargin = r;
        if (b != LAYOUT_PARAMS_KEEP_OLD)
            params.bottomMargin = b;
        view.setLayoutParams(params);
    }

    /***
     * 如果传入 {@link Integer#MIN_VALUE} ，那么传入的值将会被忽略
     */
    public static void setLayoutParams(View view, int width, int height) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params == null) {
            return;
        }
        // ViewGroup.LayoutParams.FILL_PARENT 的值小于 0，所以不能简单的 if(width > 0)
        if (width != Integer.MIN_VALUE) {
            params.width = width;
        }
        if (height != Integer.MIN_VALUE) {
            params.height = height;
        }
    }

    public static void setTxtAndAdjustVisible(TextView tv, CharSequence txt) {
        if (tv == null) {
            return;
        }
        if (TextUtils.isEmpty(txt)) {
            setViewVisibility(tv, View.GONE);
        } else {
            setViewVisibility(tv, View.VISIBLE);
            tv.setText(txt);
        }
    }

    private static boolean visibilityValid(int visiable) {
        return visiable == View.VISIBLE || visiable == View.GONE || visiable == View.INVISIBLE;
    }

    public static void setViewVisibility(View v, int visiable) {
        if (v == null || !visibilityValid(visiable) || v.getVisibility() == visiable) {
            return;
        }
        v.setVisibility(visiable);
    }

    /**
     * @param visible true -- VISIBLE; false -- GONE
     **/
    public static void setViewVisibility(@Nullable View v, boolean visible) {
        setViewVisibility(v, visible ? View.VISIBLE : View.GONE);
    }

    public static int getScreenWidth(Context context) {
        if (context == null) {
            return 0;
        }

        DisplayMetrics dm = context.getResources().getDisplayMetrics();

        return (dm == null) ? 0 : dm.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        if (context == null) {
            return 0;
        }

        DisplayMetrics dm = context.getResources().getDisplayMetrics();

        return (dm == null) ? 0 : dm.heightPixels;
    }

    public static int getStatusBarHeight(Context context) {
        if (context == null) {
            return 0;
        }
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * DO NOT use this method because it does nothing but sets a background
     */
    @Deprecated
    public static void setViewBackgroundWithPadding(View view, int resid) {
        if (view != null) {
            int left = view.getPaddingLeft();
            int right = view.getPaddingRight();
            int top = view.getPaddingTop();
            int bottom = view.getPaddingBottom();
            view.setBackgroundResource(resid);
            view.setPadding(left, top, right, bottom);
        }
    }

    /**
     * DO NOT use this method because it does nothing but sets a background
     */
    @Deprecated
    public static void setViewBackgroundWithPadding(View view, Resources res, int colorid) {
        if (view != null && res != null) {
            int left = view.getPaddingLeft();
            int right = view.getPaddingRight();
            int top = view.getPaddingTop();
            int bottom = view.getPaddingBottom();
            view.setBackgroundColor(res.getColor(colorid));
            view.setPadding(left, top, right, bottom);
        }
    }

    /**
     * DO NOT use this method because it does nothing but sets a background
     */
    @Deprecated
    @SuppressWarnings("deprecation")
    public static void setViewBackgroundWithPadding(View view, Drawable drawable) {
        if (view == null) {
            return;
        }
        int left = view.getPaddingLeft();
        int right = view.getPaddingRight();
        int top = view.getPaddingTop();
        int bottom = view.getPaddingBottom();
        view.setBackgroundDrawable(drawable);
        view.setPadding(left, top, right, bottom);
    }

    /**
     * DO NOT use this method because it does nothing but sets a background
     */
    @Deprecated
    public static void setViewBackgroundWithPadding(View view, int resid, int left, int top, int right, int bottom) {
        if (view == null)
            return;
        view.setBackgroundResource(resid);
        view.setPadding(left, top, right, bottom);
    }



}
