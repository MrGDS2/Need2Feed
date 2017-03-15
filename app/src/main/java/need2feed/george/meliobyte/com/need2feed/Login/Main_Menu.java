package need2feed.george.meliobyte.com.need2feed.Login;

import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;


import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import need2feed.george.meliobyte.com.need2feed.BreadofLife.BreadOfLife;
import need2feed.george.meliobyte.com.need2feed.StartUp.MainActivity;
import need2feed.george.meliobyte.com.need2feed.R;
import need2feed.george.meliobyte.com.need2feed.StartUp.Settings.SettingsActivity;
//import need2feed.george.meliobyte.com.need2feed.SettingsActivity;

public class Main_Menu extends AppCompatActivity implements View.OnClickListener
{
   Button BreadOfLife;
MainActivity E;
    /* Client used to interact with Google APIs. */
    private GoogleApiClient mGoogleApiClient;

    private FirebaseAuth mAuth; //We can use all methods and variables from here
    private FirebaseAuth.AuthStateListener mAuthListener;
        private static  final String TAG="Main Menu Logout";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__menu);
 BreadOfLife= (Button) findViewById(R.id.BreadOfLife);


Toast.makeText(Main_Menu.this,getResources().getText(R.string.GreetingUsers),Toast.LENGTH_SHORT).show(); //welcomes user on entry




        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull final FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.i("AuthStateChanged", "UserSettings is signed in with uid: " + user.getUid());
                } else {
                    Log.i("AuthStateChanged", "No user is signed in.");
                }
            }
        });

      mAuth=auth;
    }








    @Override
    public void onClick(View v) {

        Animation myAnim = AnimationUtils.loadAnimation(Main_Menu.this, R.anim.barrel_roll);

        v.startAnimation(myAnim);
                Organization();
    }


    public void Organization()
    {
      ChangeScreens(BreadOfLife.class);
        Toast.makeText(getApplicationContext(), getResources().getText(R.string.OrganizationPrompt), Toast.LENGTH_SHORT).show();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //If a user is currently authenticated, display a logout menu




            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.logoutButton) {

            if(mAuth!=null) {
                mAuth.signOut();
                LoginManager.getInstance().logOut();
                Log.d(TAG, "user Loggged out via Facebook");
                startActivity(new Intent(this, MainActivity.class));    //TODO: CHECK THIS
                Main_Menu.this.finish();

/**if(mAuth.getCurrentUser().getProviderId().equals(GoogleAuthProvider.PROVIDER_ID)) {
                                                                       //:TODO: Check Google sign out  5/26/16
    GoogleSignOut();
    Log.d(TAG, "user Loggged out via Google");
}
else if (mAuth.getCurrentUser().getProviderId().equals(TwitterAuthProvider.PROVIDER_ID)) {
 Twitter.logOut();
 Log.d(TAG, "user Loggged out via Twitter") ;
}
else if(mAuth.getCurrentUser().getProviderId().equals(FacebookAuthProvider.PROVIDER_ID)) {
    mAuth.signOut();

    //        //closes out of Main _Menu
}
else{

    mAuth.signOut();
    Log.d(TAG,"user Loggged out via Firebase");

} **/
            }


            return true;
        }
        if (id == R.id.Share_menu) {
            String shareBody = getResources().getString(R.string.MessageSent);
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getResources().getString(R.string.Hello));
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_using)));
            return true;
        }
        if (id == R.id.UserSettings) {

            Intent i = new Intent(this,SettingsActivity.class);
           startActivity(i);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Log.d(this.getClass().getName(), "back button pressed");
        }
        return super.onKeyDown(keyCode, event);
    }

    private void GoogleSignOut() {
        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {

                    }
                });
    }

    public void ChangeScreens(Class cl)
    {

        Intent intent = new Intent(this, cl);
        startActivity(intent);

    }


}
