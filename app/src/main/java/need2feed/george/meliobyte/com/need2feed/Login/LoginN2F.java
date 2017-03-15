package need2feed.george.meliobyte.com.need2feed.Login;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;



import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import need2feed.george.meliobyte.com.need2feed.R;


public class LoginN2F extends AppCompatActivity  {
    private EditText etUsername, etPassword;
    private String Username, Password;
private LoginN2F m;
    private CheckBox remember;
    private boolean saveMemory;
Button bLogin;
 /* Listener for Firebase session changes */
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
// ...
private static final String TAG = LoginN2F.class.getSimpleName();




    /* A dialog that is presented until the Firebase authentication finished. */
    private ProgressDialog mAuthProgressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_n2_f);


        mAuth = FirebaseAuth.getInstance();

       bLogin = (Button) findViewById(R.id.login_with_password);
m=this;



        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                    //user.
                    // UserSettings is signed in
                    Toast.makeText(LoginN2F.this,R.string.HelloUser + "check "+ user.getEmail(),Toast.LENGTH_LONG).show(); //TODO: SHOWS NUMBERS NOW ?
                    Intent i = new Intent(getApplicationContext(), Main_Menu.class);    // user authenticated takes to main page
                    startActivity(i);
                    // UserSettings can't go back to Login page " Closed"

                    LoginN2F.this.finish();

                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // UserSettings is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
        //






/*   *****************When remember me is checked it save the users data on return**** */
        remember= (CheckBox) findViewById(R.id.checkBox);
        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                saveMemory = isChecked;
            }
        });


        /*   *****************Loads the data from sharepref**** */
        SharedPreferences sharepref=getPreferences(Context.MODE_PRIVATE);
        saveMemory=sharepref.getBoolean("save", false);
        remember.setChecked(saveMemory);
        String usrname = sharepref.getString("username", "");
        String psword = sharepref.getString("password", "");

  /*   *****************Takes user input put into variables for firebase auth**** */
        etUsername = (EditText) findViewById(R.id.email);
        etUsername.setText(usrname);
        etPassword = (EditText) findViewById(R.id.Password);
        etPassword.setText(psword);

        //:TODO : PROBLEM- Does not read from edit text line? username and password






        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Username = etUsername.getText().toString();
                Password = etPassword.getText().toString();
                System.out.println("USERNAME " + Username + "Password " + Password);

                if(saveMemory)
                {

                    SharedPreferences savepref=getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=savepref.edit();
                    editor.putString("username",Username);
                    editor.putString("password",Password);
                    editor.putBoolean("save", true);                          //stores username and password on device when checked
                    editor.commit();

                }
                onLogin(Username, Password);


                if (mAuth== null) {      //login failed
                    Log.i("Auth doesn't=Null", "----->=D");
                    Animation myAnim = AnimationUtils.loadAnimation(LoginN2F.this, R.anim.shake);

                    v.startAnimation(myAnim);
                }

            }

        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }

    }


/* onLogin passes arguments to this method    */


    public void onLogin( final String Username,  String password) {


        Log.i("----->>", "onLogin");


/*  Handles Login through Need2Feed ******************************************   */



        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(Username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail", task.getException());
                            Toast.makeText(LoginN2F.this, "Login failed.",
                                    Toast.LENGTH_SHORT).show();
                        }


                    }
                });
        // [END sign_in_with_email]
    }




//                mAuthProgressDialog.hide();// dialog goes away on success





                    /* Logged in user*/
                /**  Map<String, String> map = new HashMap<String, String>();
                 map.put("provider", authData.getProvider());
                 if(authData.getProviderData().containsKey("displayName")) {
                 map.put("displayName", authData.getProviderData().get("displayName").toString());
                 }
                 ref.child("users").child(authData.getUid()).setValue(map);
                 **/
            }




















