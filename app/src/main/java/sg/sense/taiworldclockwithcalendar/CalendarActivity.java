package sg.sense.taiworldclockwithcalendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import net.time4j.Moment;
import net.time4j.PlainTimestamp;

import sg.sense.taiworldclockwithcalendar.R;

import static net.time4j.CalendarUnit.YEARS;

public class CalendarActivity extends Activity {
    PlainTimestamp curTimestamp;
    PlainTimestamp displayTimestamp;

    private void updateYear() {
        GridView gridView = (GridView) findViewById(R.id.gridView01);
        gridView.setAdapter(new CalendarAdapter(this, displayTimestamp));

        TextView textView = (TextView) findViewById(R.id.yearTextView01);
        textView.setText("Year: " + displayTimestamp.getYear());

        if (curTimestamp == displayTimestamp) {
            gridView.setSelection(curTimestamp.getCalendarDate().getDayOfYear() - 1);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        Moment curMoment = Moment.nowInSystemTime();
        curTimestamp = curMoment.toLocalTimestamp();
        displayTimestamp = curTimestamp;

        updateYear();
    }

    public void prevYear(View view) {
        if(displayTimestamp.getYear() > 1972) {
            displayTimestamp = displayTimestamp.minus(1, YEARS);

            updateYear();
        }
    }

    public void nextYear(View view) {
        displayTimestamp = displayTimestamp.plus(1, YEARS);

        updateYear();
    }
}
