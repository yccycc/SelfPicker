package com.example.selecttime;

import android.view.View;

import java.util.Arrays;
import java.util.List;


public class WheelMain {

    private static int START_YEAR = 1990, END_YEAR = 2100;
    public int screenheight;
    private View view;
    private WheelView wv_year;
    private WheelView wv_month;
    private WheelView wv_day;
    private WheelView wv_hours;
    private WheelView wv_mins;
    private WheelView wv_sec;
    private boolean hasSelectTime;
    private boolean hasYear;
    private boolean hasMonth;
    private boolean hasDay;
    private boolean hasHour;
    private boolean hasMinute;
    private boolean hasSecond;

    public WheelMain(View view) {
        super();
        this.view = view;
        setView(view);
    }

    public static int getSTART_YEAR() {
        return START_YEAR;
    }

    public static void setSTART_YEAR(int sTART_YEAR) {
        START_YEAR = sTART_YEAR;
    }

    public static int getEND_YEAR() {
        return END_YEAR;
    }

    public static void setEND_YEAR(int eND_YEAR) {
        END_YEAR = eND_YEAR;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void initDateTimePicker(int year, int month, int day) {
        this.initDateTimePicker(year, month, day, 0, 0);
    }

    /**
     * @Description: TODO 弹出日期时间选择器
     */
    public void initDateTimePicker(int year, int month, int day, int hour, int minute) {
//		int year = calendar.get(Calendar.YEAR);
//		int month = calendar.get(Calendar.MONTH);
//		int day = calendar.get(Calendar.DATE);
        // 添加大小月月份并将其转换为list,方便之后的判断
        String[] months_big = {"1", "3", "5", "7", "8", "10", "12"};
        String[] months_little = {"4", "6", "9", "11"};

        final List<String> list_big = Arrays.asList(months_big);
        final List<String> list_little = Arrays.asList(months_little);
        wv_year = (WheelView) view.findViewById(R.id.year);
        wv_month = (WheelView) view.findViewById(R.id.month);
        wv_day = (WheelView) view.findViewById(R.id.day);
        wv_hours = (WheelView) view.findViewById(R.id.hour);
        wv_mins = (WheelView) view.findViewById(R.id.min);
        wv_sec = (WheelView) view.findViewById(R.id.sec);

        //---年
        if (hasYear) {
            wv_year.setVisibility(View.VISIBLE);
            wv_year.setAdapter(new NumericWheelAdapter(START_YEAR, END_YEAR));// 设置"年"的显示数据
            wv_year.setCyclic(true);// 可循环滚动
            wv_year.setLabel("年");// 添加文字
            wv_year.setCurrentItem(year - START_YEAR);// 初始化时显示的数据
        } else {
            wv_year.setVisibility(View.GONE);
        }

        //---月
        if (hasMonth) {
            wv_month.setVisibility(View.VISIBLE);
            wv_month.setAdapter(new NumericWheelAdapter(1, 12));
            wv_month.setCyclic(true);
            wv_month.setLabel("月");
            wv_month.setCurrentItem(month);
        } else {
            wv_month.setVisibility(View.GONE);
        }
        //---日
        if (hasDay) {
            wv_day.setVisibility(View.VISIBLE);
            wv_day.setCyclic(true);
            // 判断大小月及是否闰年,用来确定"日"的数据
            if (list_big.contains(String.valueOf(month + 1))) {
                wv_day.setAdapter(new NumericWheelAdapter(1, 31));
            } else if (list_little.contains(String.valueOf(month + 1))) {
                wv_day.setAdapter(new NumericWheelAdapter(1, 30));
            } else {
                // 闰年
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
                    wv_day.setAdapter(new NumericWheelAdapter(1, 29));
                else
                    wv_day.setAdapter(new NumericWheelAdapter(1, 28));
            }
            wv_day.setLabel("日");
            wv_day.setCurrentItem(day - 1);
        } else {
            wv_day.setVisibility(View.GONE);
        }
        //---时
        if (hasHour) {
            wv_hours.setVisibility(View.VISIBLE);
            wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
            wv_hours.setCyclic(true);// 可循环滚动
            wv_hours.setLabel("时");// 添加文字
            wv_hours.setCurrentItem(hour);
        } else {
            wv_hours.setVisibility(View.GONE);
        }
        //---分
        if (hasMinute) {
            wv_mins.setVisibility(View.VISIBLE);
            wv_mins.setAdapter(new NumericWheelAdapter(0, 59));
            wv_mins.setCyclic(true);// 可循环滚动
            wv_mins.setLabel("分");// 添加文字
            wv_mins.setCurrentItem(minute);
        } else {
            wv_mins.setVisibility(View.GONE);
        }

        //---秒
        if (hasSecond) {
            wv_sec.setVisibility(View.VISIBLE);
            wv_sec.setAdapter(new NumericWheelAdapter(0, 59));
            wv_sec.setCyclic(true);// 可循环滚动
            wv_sec.setLabel("秒");// 添加文字
            wv_sec.setCurrentItem(minute);
        } else {
            wv_sec.setVisibility(View.GONE);
        }
        // 添加"年"监听
        OnWheelChangedListener wheelListener_year = new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                int year_num = newValue + START_YEAR;
                // 判断大小月及是否闰年,用来确定"日"的数据
                if (list_big
                        .contains(String.valueOf(wv_month.getCurrentItem() + 1))) {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 31));
                } else if (list_little.contains(String.valueOf(wv_month
                        .getCurrentItem() + 1))) {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 30));
                } else {
                    if ((year_num % 4 == 0 && year_num % 100 != 0)
                            || year_num % 400 == 0)
                        wv_day.setAdapter(new NumericWheelAdapter(1, 29));
                    else
                        wv_day.setAdapter(new NumericWheelAdapter(1, 28));
                }
            }
        };
        // 添加"月"监听
        OnWheelChangedListener wheelListener_month = new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                int month_num = newValue + 1;
                // 判断大小月及是否闰年,用来确定"日"的数据
                if (list_big.contains(String.valueOf(month_num))) {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 31));
                } else if (list_little.contains(String.valueOf(month_num))) {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 30));
                } else {
                    if (((wv_year.getCurrentItem() + START_YEAR) % 4 == 0 && (wv_year
                            .getCurrentItem() + START_YEAR) % 100 != 0)
                            || (wv_year.getCurrentItem() + START_YEAR) % 400 == 0)
                        wv_day.setAdapter(new NumericWheelAdapter(1, 29));
                    else
                        wv_day.setAdapter(new NumericWheelAdapter(1, 28));
                }
            }
        };
        wv_year.addChangingListener(wheelListener_year);
        wv_month.addChangingListener(wheelListener_month);

        // 根据屏幕密度来指定选择器字体的大小(不同屏幕可能不同)
        int textSize = screenheight / 40;
        wv_day.TEXT_SIZE = textSize;
        wv_month.TEXT_SIZE = textSize;
        wv_year.TEXT_SIZE = textSize;
        wv_hours.TEXT_SIZE = textSize;
        wv_mins.TEXT_SIZE = textSize;
        wv_sec.TEXT_SIZE = textSize;

    }

    public String getCookedTime() {
        StringBuffer sb = new StringBuffer();
        Object object = hasYear ? sb.append((wv_year.getCurrentItem() + START_YEAR)).append(wv_year.getLabel()) : null;
        object = hasMonth ? sb.append((wv_month.getCurrentItem() + 1)).append(wv_month.getLabel()) : null;
        object = hasDay ? sb.append((wv_day.getCurrentItem() + 1)).append(wv_day.getLabel()) : null;
        object = hasHour ? sb.append(wv_hours.getCurrentItem()).append(wv_hours.getLabel()) : null;
        object = hasMinute ? sb.append(wv_mins.getCurrentItem()).append(wv_mins.getLabel()) : null;
        object = hasSecond ? sb.append(wv_sec.getCurrentItem()).append(wv_sec.getLabel()) : null;
        return sb.toString();
    }

    public String getOriginalTime() {
        StringBuffer sb = new StringBuffer();
        Object object = hasYear ? sb.append((wv_year.getCurrentItem() + START_YEAR)) : null;
        object = hasMonth ? sb.append(String.format("%02d", wv_month.getCurrentItem() + 1)) : null;
        object = hasDay ? sb.append(String.format("%02d", wv_day.getCurrentItem() + 1)) : null;
        object = hasHour ? sb.append(String.format("%02d", wv_hours.getCurrentItem())) : null;
        object = hasMinute ? sb.append(String.format("%02d", wv_mins.getCurrentItem())) : null;
        object = hasSecond ? sb.append(String.format("%02d", wv_sec.getCurrentItem())) : null;
        return sb.toString();
    }

    public boolean isHasMinute() {
        return hasMinute;
    }

    public void setHasMinute(boolean hasMinute) {
        this.hasMinute = hasMinute;
    }

    public boolean isHasHour() {
        return hasHour;
    }

    public void setHasHour(boolean hasHour) {
        this.hasHour = hasHour;
    }

    public boolean isHasDay() {
        return hasDay;
    }

    public void setHasDay(boolean hasDay) {
        this.hasDay = hasDay;
    }

    public boolean isHasMonth() {
        return hasMonth;
    }

    public void setHasMonth(boolean hasMonth) {
        this.hasMonth = hasMonth;
    }

    public boolean isHasYear() {
        return hasYear;
    }

    public void setHasYear(boolean hasYear) {
        this.hasYear = hasYear;
    }

    public boolean isHasSecond() {
        return hasSecond;
    }

    public void setHasSecond(boolean hasSecond) {
        this.hasSecond = hasSecond;
    }
}
