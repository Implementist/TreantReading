package com.implementist.ireading;

import android.content.Context;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;

import com.implementist.ireading.activity.MainActivity;

import java.lang.reflect.Field;

public class Utils {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param context 上下文
     * @param dpValue dp值
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param context 上下文
     * @param pxValue px值
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 取消BottomNavigationView各item切换时的位移动效
     *
     * @param navigationView 底部导航栏视图
     */
    public static void disableShiftMode(BottomNavigationView navigationView) {

        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigationView.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);

            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(i);
                itemView.setShiftingMode(false);
                itemView.setChecked(itemView.getItemData().isChecked());
            }

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过计算相对位置之差取得两个Fragment滑入滑出的动画
     *
     * @param frgA FragmentA的位值
     * @param frgB FragmentB的位值
     * @return FragmentA的滑入动画和FragmentB的滑出动画组成的数组
     */
    public static int[] getSlideAnimationByCalculate(int frgA, int frgB) {
        int calculateResult = frgA - frgB;

        //如果差值大于零
        if (calculateResult > 0)
            return new int[]{R.anim.slide_in_right, R.anim.slide_out_left};
        else
            return new int[]{R.anim.slide_in_left, R.anim.slide_out_right};
    }
}  
