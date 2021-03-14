package com.dimfcompany.nashprihodadmin.base;

import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import org.jaaksi.pickerview.dialog.DefaultPickerDialog;
import org.jaaksi.pickerview.picker.BasePicker;
import org.jaaksi.pickerview.picker.TimePicker;
import org.jaaksi.pickerview.widget.PickerView;

import java.util.Date;

public class TestJava
{
    public static void showDialog(AppCompatActivity act)
    {
        TimePicker mTimePicker = new TimePicker.Builder(act, TimePicker.TYPE_DATE, new TimePicker.OnTimeSelectListener()
        {
            @Override
            public void onTimeSelect(TimePicker picker, Date date)
            {

            }
        })
                // 设置时间区间
                .setRangDate(1526361240000L, 1893563460000L)
                // 设置选中时间
                //.setSelectedDate()
                // 设置pickerview样式
                .setInterceptor(new BasePicker.Interceptor() {
                    @Override public void intercept(PickerView pickerView, LinearLayout.LayoutParams params) {
                        pickerView.setVisibleItemCount(5);
                        // 将年月设置为循环的
                        int type = (int) pickerView.getTag();
                        if (type == TimePicker.TYPE_YEAR || type == TimePicker.TYPE_MONTH) {
                            pickerView.setIsCirculation(true);
                        }
                    }
                })

                // 设置 Formatter
                .setFormatter(new TimePicker.DefaultFormatter() {
                    // 自定义Formatter显示去年，今年，明年
                    @Override
                    public CharSequence format(TimePicker picker, int type, int position, long num) {
                        if (type == TimePicker.TYPE_YEAR) {
                            long offset = num - 2020;
                            if (offset == -1) return "去年";
                            if (offset == 0) return "今年";
                            if (offset == 1) return "明年";
                            return num + "年";
                        } else if (type == TimePicker.TYPE_MONTH) {
                            return String.format("%d月", num);
                        }

                        return super.format(picker, type, position, num);
                    }
                })
                .create();

        DefaultPickerDialog dialog = (DefaultPickerDialog) mTimePicker.dialog();
        dialog.getTitleView().setText("请选择时间");

        //mTimePicker.setSelectedDate(1549349843000L);
        mTimePicker.show();
    }
}
