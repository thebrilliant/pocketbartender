package android.assimilated.pocketbartender;

import android.app.Application;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by iguest on 5/28/15.
 */
public class MainApp extends Application {
    public static int today;

    public MainApp() {
        //ensures that there is only one instance of QuizApp
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Cannot create more than one MainApp");
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    }
}
