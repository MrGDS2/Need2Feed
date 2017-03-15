package need2feed.george.meliobyte.com.need2feed.StartUp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import need2feed.george.meliobyte.com.need2feed.R;

public class SplashScreenStart extends Activity {







    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 1000;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle Need2FeedBegin) {
        super.onCreate(Need2FeedBegin);
   //   TwitterAuthConfig authConfig = new TwitterAuthConfig(CONSTANTS.TWITTER_KEY, CONSTANTS.TWITTER_SECRET);
   // Fabric.with(this, new Twitter(authConfig));

        setContentView(R.layout.activity_splash_screen_start);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashScreenStart.this, MainActivity.class); //Takes to Main Need2Feed Screen
              SplashScreenStart.this.startActivity(mainIntent);
                SplashScreenStart.this.finish();
                overridePendingTransition(R.anim.mainfadein,R.anim.mainfadeout);
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
