package sg.sense.taiworldclockwithcalendar;

import android.app.Application;

import net.time4j.android.ApplicationStarter;

/**
 * Created by kumari on 4/29/17.
 */

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationStarter.initialize(this, true); // with prefetch on background thread
    }
}