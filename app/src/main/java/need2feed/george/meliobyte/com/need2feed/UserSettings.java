package need2feed.george.meliobyte.com.need2feed;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Map;

/**
 * Created by Mrgds on 3/20/2016.
 */
public class UserSettings extends AppCompatActivity implements View.OnClickListener{
    private Button bLanguageOption;
    private Button bProfileOption;
    private Button bPasswordOption;
    private Button bNotificationOption;
    private Button bLogoutOption;
    private Button bOption;
    /*Call to Database *******/
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usersettings_activity);

        bLanguageOption.findViewById(R.id.SettingsLanguage);
        bProfileOption.findViewById(R.id.Profile);
        bPasswordOption.findViewById(R.id.SettingsPassword);
        bNotificationOption.findViewById(R.id.Notifications);
        bLogoutOption.findViewById(R.id.logoutButton);





        mAuth = FirebaseAuth.getInstance();
        Locale locale2 = new Locale("fr");
        Locale.setDefault(locale2);
        Configuration config2 = new Configuration();
        config2.locale = locale2;
        getBaseContext().getResources().updateConfiguration(config2, getBaseContext().getResources().getDisplayMetrics());

        Toast.makeText(this, "Locale en Français !", Toast.LENGTH_LONG).show();
        break;


    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.SettingsLanguage:

Log.v("Language Settings","Active");
                break;

            case R.id.Profile:

                Log.v("Profile Settings","Active");

                break;

            case R.id.Password:

                Log.v("Password Settings","Active");
                break;

            case R.id.Notifications:

                Log.v("Notifications Settings","Active");
                break;

            case R.id.login_button:

                Log.v("Logout Settings","Active");
                break;




        }

    }



    private void LaguageSettings()
    {

        Intent i=new Intent(this,LaguageSetting.class);

        startActivity(i);



    }



}
