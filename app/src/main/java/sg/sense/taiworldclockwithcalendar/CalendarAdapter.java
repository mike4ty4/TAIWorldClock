package sg.sense.taiworldclockwithcalendar;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.time4j.Moment;
import net.time4j.PlainDate;
import net.time4j.PlainTimestamp;
import net.time4j.scale.TimeScale;

/**
 * Created by kumari on 7/18/17.
 */

public class CalendarAdapter extends BaseAdapter {
    private Context mContext;
    long timeVal;
    PlainTimestamp curTimestamp;

    public CalendarAdapter(Context c, PlainTimestamp curTimestamp) {
        mContext = c;

        this.curTimestamp = curTimestamp;
    }

    public int getCount() {
        return curTimestamp.getCalendarDate().lengthOfYear();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public String formatTimeStr(long curTime) {
        String timeStr = new String();
        long curTimeVal = curTime;

        int i = 0;
        while(curTimeVal != 0) {
            long digit = curTimeVal % 10;
            timeStr = timeStr + digit;
            if (((i + 1) % 3 == 0) && (curTimeVal != 0))
                timeStr += " ";

            curTimeVal /= 10;
            ++i;
        }

        timeStr = new StringBuilder(timeStr).reverse().toString();
        return timeStr;
    }

    private String makeTileStr(int position) {
        // Find the beginning of the year in local time
        int curYear = curTimestamp.getYear();
        Moment dayMoment = PlainDate.of(curYear, position + 1).atStartOfDay().inStdTimezone();
        long timeVal = dayMoment.getElapsedTime(TimeScale.TAI);
        PlainTimestamp dayTimestamp = dayMoment.toLocalTimestamp();

        String imgStr = new String("");
        imgStr += dayTimestamp.getCalendarDate().toString();
        imgStr += "\n";
        imgStr += "Begins:\n";
        imgStr += formatTimeStr(timeVal);
        return imgStr;
    }

    /*
    private int makeBkgRes(int position) {
        Moment dayMoment = PlainDate.of(curYear, position+1).atStartOfDay().inStdTimezone();
        PlainTimestamp dayTimestamp = dayMoment.toLocalTimestamp();
        if(dayTimestamp.getMonth() % 2 == 0) {
            return mContext.getResources().getColor(android.R.color.holo_green_light);
        } else {
            return mContext.getResources().getColor(android.R.color.holo_green_dark);
        }
    }
    */
    private int makeBkgColor(int position) {
        int curYear = curTimestamp.getYear();
        int curDayOfYear = curTimestamp.getCalendarDate().getDayOfYear();
        Moment dayMoment = PlainDate.of(curYear, position+1).atStartOfDay().inStdTimezone();
        PlainTimestamp dayTimestamp = dayMoment.toLocalTimestamp();
        if(position == curDayOfYear-1) {
            return ContextCompat.getColor(mContext, android.R.color.darker_gray);
        }

        if(dayTimestamp.getMonth() % 2 == 0) {
            return ContextCompat.getColor(mContext, android.R.color.holo_blue_light);
        } else {
            return ContextCompat.getColor(mContext, android.R.color.holo_blue_bright);
        }
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if(convertView == null) {
            // intialization
            textView = new TextView(mContext);
            //textView.setLayoutParams(new GridView.LayoutParams(85, 85));
            textView.setText(makeTileStr(position));
            textView.setBackgroundColor(makeBkgColor(position));
        } else {
            textView = (TextView) convertView;
            textView.setText(makeTileStr(position));
            textView.setBackgroundColor(makeBkgColor(position));
        }

        return textView;
    }
}
