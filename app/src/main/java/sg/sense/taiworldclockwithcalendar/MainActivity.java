package sg.sense.taiworldclockwithcalendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import net.time4j.Moment;
import net.time4j.scale.TimeScale;

import sg.sense.taiworldclockwithcalendar.R;

interface ClockThreadIface {
    public void displayTime(long newTime);
}

public class MainActivity extends Activity implements ClockThreadIface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ClockThread thread = new ClockThread(this);
        thread.start();
    }

    public void displayTime(long newTime) {
        final long curTime = newTime;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String timeStr = new String();
                long curTimeVal = curTime;

                for(int i = 0; i < 10; ++i) {
                    long digit = curTimeVal % 10;
                    timeStr = timeStr + digit;
                    if((i+1)%3 == 0)
                        timeStr += " ";

                    curTimeVal /= 10;
                }

                timeStr = new StringBuilder(timeStr).reverse().toString();
                TextView tv = (TextView) findViewById(R.id.textView2);
                tv.setText(timeStr);
            }
        });
    }

    public void showCalendar(View view) {
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);
    }

    public void showAbout(View view) {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }}

class ClockThread extends Thread {
    private ClockThreadIface iface;

    ClockThread(ClockThreadIface iface) {
        this.iface = iface;
    }

    public void run() {
        /*
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            // ignore
        }
        */

        while(true) {
            // Fetch the current time.
            Moment moment = Moment.nowInSystemTime();
            long curTime = moment.getElapsedTime(TimeScale.TAI);
            //long curTime = 0;

            iface.displayTime(curTime);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // ignore
            }
        }
    }
}