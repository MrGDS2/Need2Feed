package need2feed.george.meliobyte.com.need2feed.Signup;


import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import need2feed.george.meliobyte.com.need2feed.Login.Main_Menu;
import need2feed.george.meliobyte.com.need2feed.R;

public class RegisterN2FUser extends AppCompatActivity {

    Button bRegister;
    EditText etName, etAge, etUsername, etPassword,etConfirmPass;
   // UserSettings registeredData;

    private static final String TAG="RegisterN2F";

private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_n2_fuser);


        mAuth = FirebaseAuth.getInstance();
        etName = (EditText) findViewById(R.id.etName);
      //etAge = (EditText) findViewById(R.id.etAge);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfirmPass=(EditText) findViewById(R.id.ConfirmPass);
        bRegister = (Button) findViewById(R.id.bRegister);




        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String name = etName.getText().toString();
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String ConfirmPass = etConfirmPass.getText().toString();


//               int age = Integer.parseInt(etAge.getText().toString());


/*Checks if user left ALL VALUES BLANK & Pressed register  */



                if (!etName.getText().toString().equals("") && !etUsername.getText().toString().equals("")
                        &&!etPassword.getText().toString().equals("") && !etConfirmPass.getText().toString().equals("")) {

/*checks if Password is NOT the same*/

                    if (!password.equals(ConfirmPass)) {


                        Toast.makeText(RegisterN2FUser.this, " Sorry,  Passwords don't match", Toast.LENGTH_SHORT).show();

                        Animation myAnim = AnimationUtils.loadAnimation(RegisterN2FUser.this, R.anim.shake);

                        v.startAnimation(myAnim);
                    } else {

                                /* takes data and puts in data base */
                                    CreateUser(username,password);    //TODO:Get this to work

                        // ***ends
                        Log.i("reading from else ", "of onSignupClicked");
                        Toast.makeText(RegisterN2FUser.this, " Sign up Successful, Welcome to Need2Feed " + username, Toast.LENGTH_SHORT).show();

                    }

                }

                else {
                    Toast.makeText(RegisterN2FUser.this, "Make sure all values are filled before registration", Toast.LENGTH_LONG).show();

                    Animation myAnim = AnimationUtils.loadAnimation(RegisterN2FUser.this, R.anim.shake);

                    v.startAnimation(myAnim);


                    Log.d("what's the problem?", "all entries filled?");
                }

            }
        });



    }




    // [END on_stop_remove_listener]
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
        // Checks whether a hardware keyboard is available
        if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO) {

            Toast.makeText(this, "keyboard visible", Toast.LENGTH_SHORT).show();
        }
        else if (newConfig.hardKeyboardHidden ==
                Configuration.HARDKEYBOARDHIDDEN_YES) {





                Toast.makeText(RegisterN2FUser.this, " Sorry,  Passwords don't match", Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(this, "keyboard hidden", Toast.LENGTH_SHORT).show();
        }
public void CreateUser(String username,String password) {

     /* takes data and puts in data base */
    mAuth.createUserWithEmailAndPassword(username, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                    // UserSettings is signed in
                    Intent i = new Intent(RegisterN2FUser.this, Main_Menu.class);    // user authenticated takes to main page
                    startActivity(i);

                    // UserSettings can't go back to register page " Closed"
                    RegisterN2FUser.this.finish();
                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful()) {
                        Log.d("Login not", " succuessful");
                    }

                    // ...
                }
            });
}

}










