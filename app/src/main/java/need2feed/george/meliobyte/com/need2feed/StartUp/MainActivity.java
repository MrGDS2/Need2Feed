package need2feed.george.meliobyte.com.need2feed.StartUp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;

import android.content.Intent;

import android.util.Log;


import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;


import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;


import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;

import com.facebook.login.LoginResult;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.TwitterAuthProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.android.gms.common.ConnectionResult;
;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import io.fabric.sdk.android.Fabric;
import need2feed.george.meliobyte.com.need2feed.*;
import need2feed.george.meliobyte.com.need2feed.Login.LoginN2F;
import need2feed.george.meliobyte.com.need2feed.Login.Main_Menu;
import need2feed.george.meliobyte.com.need2feed.R;
import need2feed.george.meliobyte.com.need2feed.Signup.RegisterN2FUser;


import com.twitter.sdk.android.core.*;
import com.twitter.sdk.android.core.identity.*;

public class MainActivity extends AppCompatActivity implements   GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    /* For onclick Startpage Login and Signup*/
private Button mLogin,mRegister, mAnonymousLoginButton;

    private LoginButton mFacebookloginButton;
    /* The callback manager for Facebook */
    private CallbackManager mFacebookCallbackManager;
    /* Used to track user logging in/out off Facebook */
    private AccessTokenTracker mFacebookAccessTokenTracker;

    private AccessToken accessToken;

    /* Data from the authenticated user */
    private FirebaseAuth mAuth;


    //Google
    /* Request code used to invoke sign in user interactions for Google+ */
    public static final int RC_GOOGLE_LOGIN = 1;

    /* Client used to interact with Google APIs. */
    private GoogleApiClient mGoogleApiClient;

    /* A flag indicating that a PendingIntent is in progress and prevents us from starting further intents. */
    private boolean mGoogleIntentInProgress;

    /* Track whether the sign-in button has been clicked so that we know to resolve all issues preventing sign-in
     * without waiting. */
    private boolean mGoogleLoginClicked;

    /* Store the connection result from onConnectionFailed callbacks so that we can resolve them when the user clicks
     * sign-in. */
    private ConnectionResult mGoogleConnectionResult;

    /* The login button for Google */
    private SignInButton mGoogleLoginButton;

    /*Twitter Button login      */
        private TwitterLoginButton loginButton;





    /*  FIRE BASE   REF VARIABLE   */
private DatabaseReference rootRef;

  /* Listener for Firebase session changes */
  private FirebaseAuth.AuthStateListener mAuthListener;

    /* A dialog that is presented until the DatabaseReference authentication finished. */
    private ProgressDialog mAuthProgressDialog;

    private static final String TAG = MainActivity.class.getSimpleName(); //Tag string for log.d output



/* On click user feels a vibrate*/
    private Vibrator feedback;


  //  final Animation animAlpha= AnimationUtils.loadAnimation(this,R.anim.alpha_fade);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//TODO:testfairy import
    //    TestFairy.begin(this, "bde19002b059bcf18e75d5029b2862d582033bf4");
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        final TwitterAuthConfig authConfig = new TwitterAuthConfig(CONSTANTS.TWITTER_KEY, CONSTANTS.TWITTER_SECRET);
        Fabric.with(getApplicationContext(), new TwitterCore(authConfig));

        setContentView(need2feed.george.meliobyte.com.need2feed.R.layout.activity_main);                                                             /*Initialize all variables    */
        mAuth = FirebaseAuth.getInstance();
        feedback = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);

        // FirebaseDatabase.setAndroidContext(getApplicationContext());

        //TODO: TEMPORARY DEMO:ALPHA
        Button Demo = (Button) findViewById(R.id.demo);
        Demo.setVisibility(View.VISIBLE);
        Demo.setBackgroundColor(Color.TRANSPARENT);
        Demo.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {

                switch (v.getId()) {

                    case R.id.demo:
                        LoginDemo();

                        //vibrates  Virbate(25);
                        //  v.startAnimation(animAlpha);
                        break;
                }
            }
        });


        //    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();




        /* Register || Login Start   ***************************************/

/* Register || SignUp  MainPage    */

        mRegister = (Button) findViewById(R.id.SignupN2F);
        //  Register through DatabaseReference NewServer
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SignUpClicked();
                feedback.vibrate(25);//vibrates
                Animation myAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.alpha_fade);
                v.startAnimation(myAnim);

            }

        });




         /* Login With username and password  MainPage*/
        //  Login on MainPage
        mLogin = (Button) findViewById(R.id.LoginN2F);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                LoginClicked();
                feedback.vibrate(25);//vibrates
                Animation myAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.alpha_fade);
                v.startAnimation(myAnim);


            }
        });

                                        /* Register || Login End    ***************************************/


                                                    /****************************************Facebook LOGIN*********************/
        mFacebookloginButton = (LoginButton) findViewById(R.id.login_button);

         /* Load the Facebook login button and set up the tracker to monitor access token changes */


        mFacebookloginButton.setReadPermissions("user_friends", "email", "public_profile");
        mFacebookCallbackManager = CallbackManager.Factory.create();

        mFacebookloginButton.registerCallback(mFacebookCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());// Handels login to Firebase "Pass-Pass"
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");

            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);

            }
        });
        // [END initialize_fblogin]

        /******   mFacebookAccessTokenTracker = new AccessTokenTracker() {
        @Override protected void onCurrentAccessTokenChanged(
        AccessToken oldAccessToken,
        AccessToken currentAccessToken) {
        // Set the access token using
        // currentAccessToken when it's loaded or set.
        }
        };
         // If the access token is available already assign it.
         accessToken = AccessToken.getCurrentAccessToken();


         // If the access token is available already assign it.

                                                                     / ************************Facebook LOGIN  END*******************************/


        //Google
                                                     /* Load the Google login button *********************************************/
        mGoogleLoginButton = (SignInButton) findViewById(R.id.login_with_google);
        mGoogleLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoogleSignin();

            }
        });


        // [START config_signin]
        // Configure Google Sign In
        Log.d(TAG, "Trying to resolve sign in ");
        GoogleSignInOptions GooSigninCon = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]
   /* Setup the Google API object to allow Google+ logins */

        // ATTENTION: This "addApi(AppIndex.API)"was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, GooSigninCon)
                .addApi(AppIndex.API).build();


                                                       /*******************************************Google end **************************************  */


                                                                            /****** Twitter    Login*******************  ********************/


        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // The TwitterSession is also available through:
                // Twitter.getInstance().core.getSessionManager().getActiveSession()
                TwitterSession session = result.data;
                // TODO: Remove toast and use the TwitterSession's userID
                // with your app's user model

                String msg = "@" + session.getUserName() + " logged in! (#" + session.getUserId() + ")";
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                AuthCredential  credential = TwitterAuthProvider.getCredential(session.getAuthToken().token, session.getAuthToken().secret);
                TwitterAuthFirebase(credential); //pass credential
            }
            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });


        /******                           Twitter    Login   end ***************************************/






                                                                 /* ******************************       FIRE BASE *********************************                                                                       */
        /*
rootRef.addValueEventListener(new ValueEventListener() {
    @Override
     public void onDataChange(DataSnapshot dataSnapshot) {   See Data from Fire Base InReal Time
        String superData=(String)dataSnapshot.getValue();
        rootRef.se;
    }

    @Override
    public void onCancelled(DatabaseError DatabaseError) {

    }
}); */
        /*Checks if user is already logged in to need2feed */

        rootRef = FirebaseDatabase.getInstance().getReference();
        if (FirebaseAuth.getInstance() != null) {
            //TODO:*take user to login screen   fragments for future

        }







  /* Setup the progress dialog that is displayed later when authenticating with DatabaseReference
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle("Loading");
        mAuthProgressDialog.setMessage("Authenticating with DatabaseReference...");
        mAuthProgressDialog.setCancelable(false);
        mAuthProgressDialog.show();
        */
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {        /*         changed with updated 5/22/16                     */
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    // UserSettings is signed in
                    String name = user.getDisplayName();

                    String email = user.getEmail();

                    Need2FeedLogin();
                    /*for twitter issue of no email pulling */
                    if(email==null) {
                        Toast.makeText(MainActivity.this, "Successful Login as " + name, Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                    }
                    Toast.makeText(MainActivity.this, "Successful Login as " + email, Toast.LENGTH_SHORT).show();
                }

                else {
                    // UserSettings is signed out

                    //  Toast.makeText(MainActivity.this, "Soon you soon " + name, Toast.LENGTH_LONG).show();
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...

            }
        };
    }
    @Override
    public void onStart() {
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mGoogleApiClient.connect();
        mAuth.addAuthStateListener(mAuthListener);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://need2feed.george.meliobyte.com.need2feed/http/host/path")
        );
        AppIndex.AppIndexApi.start(mGoogleApiClient, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://need2feed.george.meliobyte.com.need2feed/http/host/path")
        );
        AppIndex.AppIndexApi.end(mGoogleApiClient, viewAction);
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
        // ...


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mGoogleApiClient.disconnect();
    }
                                                /* ******************************       FIRE BASE END*********************************   */










    @Override
    protected void onDestroy() {
        super.onDestroy();
        // if user logged in with Facebook, stop tracking their token
        if (mFacebookAccessTokenTracker != null) {
            mFacebookAccessTokenTracker.stopTracking();
        }

        // if changing configurations, stop tracking DatabaseReference session.
     //   rootRef.
    }

    /**
     * This method fires when any startActivityForResult finishes. The requestCode maps to
     * the value passed into startActivityForResult.
     */




                                                  /**Activity Result For Google   ************************************************************************/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_GOOGLE_LOGIN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // ...

            }
        }
        /*Facebook Login button Results     */
        mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data);

         /*twitter Login button Results     */
            loginButton.onActivityResult(requestCode, resultCode, data);



    }



    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGooogle:" + acct.getId());
        // ...
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());    /*  Update firebase 5/22/16     */

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",  //TODO:Change For language
                                    Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });
    }


                                                        /***  Activity Google End       *****************************************************/





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* If a user is currently authenticated, display a logout menu */
        if (this.mAuth != null) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        } else {
            return false;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logoutButton) {
            logout();
            return true;
        }
        if (id == R.id.Share_menu) {
            ShareVia();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    public void ShareVia(){

        String shareBody = getResources().getString(R.string.MessageSent);
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.Hello));
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_using)));

    }









    private void logout() {
        if (this.mAuth != null) {
            /* logout of DatabaseReference */
            FirebaseAuth.getInstance().signOut();


            /* Update authenticated user and show login buttons */}
            setAuthenticatedUser(null);
        }




    /**
     * Once a user is logged in, take the mAuthData provided from DatabaseReference and "use" it.
     */
 public void setAuthenticatedUser(FirebaseAuth authData) {
        if (authData != null) {
            /* Hide all the login buttons */
           mFacebookloginButton.setVisibility(View.GONE);
         mGoogleLoginButton.setVisibility(View.GONE);
      //    mAnonymousLoginButton.setVisibility(View.GONE);
        mLogin.setVisibility(View.GONE);
        mRegister.setVisibility(View.GONE);



              Need2FeedLogin(); //TODO: fix GOES TO MAIN PAGE FOR ALL THROUGH DatabaseReference

      MainActivity.this.finish();

            Log.i(TAG, "GOING TO MAIN MENU FAILED ON GOOGLE AUTH");


            /* show a provider specific status text */



            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    // Name, email address, and profile photo Url
                    String name = user.getDisplayName();
                    String email = user.getEmail();
                    Uri photoUrl = user.getPhotoUrl();
                    Toast.makeText(MainActivity.this,"Logged in as "
                            + name + "" ,Toast.LENGTH_LONG).show();
                    // The user's ID, unique to the Firebase project. Do NOT use this value to
                    // authenticate with your backend server, if you have one. Use
                    // FirebaseUser.getToken() instead.
                    String uid = user.getUid();
            }

        } else {
            /* No authenticated user show all the login buttons */
            mFacebookloginButton.setVisibility(View.VISIBLE);
            mGoogleLoginButton.setVisibility(View.VISIBLE);

        }
      this.mAuth = authData;
        /* invalidate options menu to hide/show the logout button */
        supportInvalidateOptionsMenu();

    }

    /**
     * Show errors to users
     */
    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }




    //******************************************************************************Facebook Token
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        // ...
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ****************************************************************************Facebook token end
                    }
                });
    }

    // ****************************************************************************Twitter token
private void TwitterAuthFirebase(  AuthCredential credential)
{

    mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task task) {
                    Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.                                      //TODO: CHECK THIS LOGIN does not get name?
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "signInWithCredential", task.getException());
                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }

                    // ...
                }
            });
}

    // ****************************************************************************Twitter firebase in END


   //trash code google
    /* A helper method to resolve the current ConnectionResult error.
    private void resolveSignInError() {
        if (mGoogleConnectionResult.hasResolution()) {
            try {
                mGoogleIntentInProgress = true;
                mGoogleConnectionResult.startResolutionForResult(this, RC_GOOGLE_LOGIN);
            } catch (IntentSender.SendIntentException e) {
                // The intent was canceled before it was sent.  Return to the default
                // state and attempt to connect to get an updated ConnectionResult.
                mGoogleIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }*/


    //Google
    private void GoogleSignin() {

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_GOOGLE_LOGIN);




    }

    @Override
    public void onConnected(final Bundle bundle) {
        /* Connected with Google API, use this to authenticate with DatabaseReference */

        //startActivity(new Intent(this, MainPageActivity.class));
    }


    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.d(TAG, "onConnectionFailed:" + result);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
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




    /**
     * Login intent "Need2Feed"
     */
    private void LoginClicked() {
        Intent i = new Intent(this, LoginN2F.class);        //Login  Button Action
        startActivity(i);
    }
    /**
     * SignUp intent "Need2Feed"
     */
    private void SignUpClicked() {
        Intent i = new Intent(this, RegisterN2FUser.class);        //SignUp Button Action
        startActivity(i);
    }








    public void Need2FeedLogin()
    {
        Intent i=new Intent(this,Main_Menu.class);

        startActivity(i);


    }
    public void LoginDemo()
    {    //TODO:   TEMP DEMO BUTTON
        Intent i=new Intent(this,Main_Menu.class);

        startActivity(i);
    }

/*  may implement later  2/15/16 cancelled
    public void TwitterLoginButton() {
        if (!(Success = true)) {
            FailedLogin();
        }                  //  //Have it's own main menu with Twitter sharing
        else {
            Intent i = new Intent(this, Main_Menu.class);
            startActivity(i);
        }

    }


*/





    @Override
    public void onConnectionSuspended(int i) {

    }


public void Virbate(int vib)
{
int val= Settings.System.getInt(getContentResolver(),Settings.System.HAPTIC_FEEDBACK_ENABLED,0);
boolean mSettingEnabled=val!=0;
    if(mSettingEnabled)
    feedback.vibrate(vib);

}



}
