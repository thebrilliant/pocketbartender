package android.assimilated.pocketbartender;

import android.app.Application;

/**
 * Created by iguest on 5/28/15.
 */
public class MainApp extends Application {

    public MainApp() {
        //ensures that there is only one instance of QuizApp
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Cannot create more than one QuizApp");
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
