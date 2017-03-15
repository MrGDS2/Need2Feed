package need2feed.george.meliobyte.com.need2feed.BreadofLife;



import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import need2feed.george.meliobyte.com.need2feed.CONSTANTS;
import need2feed.george.meliobyte.com.need2feed.CalendarBreadOfLife.CalendarScreen;
//import need2feed.george.meliobyte.com.need2feed.Info.InfoFragment;
import need2feed.george.meliobyte.com.need2feed.Info.InfoWebView;
import need2feed.george.meliobyte.com.need2feed.StartUp.MainActivity;
import need2feed.george.meliobyte.com.need2feed.Maps.MapsActivity;
import need2feed.george.meliobyte.com.need2feed.Paypal.PayPal;
import need2feed.george.meliobyte.com.need2feed.R;
import need2feed.george.meliobyte.com.need2feed.Feedpage.TheFeed;


public class BreadOfLife extends AppCompatActivity implements View.OnClickListener {

    ImageButton Donate,Maps,Events,Feed,info;
    double latitude,longitude;
   public double Latitude;
   public double Longitude;
MapsActivity m;
    /* On click user feels a vibrate*/
    private Vibrator feedback;


//MainActivity menuview= new MainActivity();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bread_of_life);
        info = (ImageButton) findViewById(R.id.info);

        Events = (ImageButton) findViewById(R.id.events);
        Feed = (ImageButton) findViewById(R.id.feed);

        Maps = (ImageButton) findViewById(R.id.GoogleMaps);
        Donate = (ImageButton) findViewById(R.id.donate);

        feedback = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);


//     this.m.finish();

        try {


            /** Premission request to Find current location**/
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                /**Checks and Enable Gps**/


                LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Toast.makeText(this, "GPS is Enabled on your device", Toast.LENGTH_SHORT).show();
                } else {
                    showGPSDisabledAlertToUser();
                }

                /**Checks and Enable Gps**/

                    /**sets user LatLng**/
          Latitude=m.getLat(latitude);
          Longitude=m.getLong(longitude);
                /**sets user LatLng**/


            }
        //    mMap.setMyLocationEnabled(true);  //this does not work without permission check_campus
 /*   Gets user Location */
            LocationManager locationManager = (LocationManager)
                    getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();

            Location location = locationManager.getLastKnownLocation(locationManager
                    .getBestProvider(criteria, false));
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            String lat=String.valueOf(latitude);
            String lon=String.valueOf(longitude);


        /*ECHO SHOW USER's LAT */
          Log.d("user's lat:" + lat, "user's long:" + lon);


        } catch (Exception e) {


        }
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
            //    menuview.logout();
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


        return super.onOptionsItemSelected(item);
    }







    /*  ALL WORKING BUTTONS * On BreadofLife MainPage*/
    @Override
    public void onClick(View v) {
        switch(v.getId())
        {


       case R.id.GoogleMaps:

           ChangeScreens(MapsActivity.class);
           Toast.makeText(getApplicationContext(), getResources().getText(R.string.On_Google_Maps_Pressed), Toast.LENGTH_LONG).show();
           Virbate(25);
           Animation myAnim = AnimationUtils.loadAnimation(BreadOfLife.this, R.anim.alpha_fade);

           v.startAnimation(myAnim);
                break;
          case R.id.events:

          ChangeScreens(CalendarScreen.class);
                Virbate(25);
                Animation mAnim = AnimationUtils.loadAnimation(BreadOfLife.this, R.anim.alpha_fade);

                v.startAnimation(mAnim);
                break;
         /****/
            case R.id.feed:

                ChangeScreens(TheFeed.class);
                Toast.makeText(getApplicationContext(), getResources().getText(R.string.On_Feed_Pressed), Toast.LENGTH_LONG).show();
                Virbate(25);
                Animation Anim = AnimationUtils.loadAnimation(BreadOfLife.this, R.anim.alpha_fade);

                v.startAnimation(Anim);
                break;
            case R.id.donate:
                ChangeScreens(PayPal.class);
                Virbate(25);

                break;
          case R.id.info:
           //   PutExtra(CONSTANTS.Info_BreadOfLife);
              ChangeScreens(InfoWebView.class);
              Toast.makeText(getApplicationContext(), getResources().getText(R.string.On_More_info_Pressed), Toast.LENGTH_SHORT).show();
              Virbate(25);
              break;
        }

    }

/**
       Fragment frag=new CalendarFragment();
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //swap the fragment
    ft.replace(R.id.BFL, frag, "Bread of Life Events");
     ft.commit();

        Toast.makeText(getApplicationContext(), getResources().getText(R.string.On_Events_Pressed), Toast.LENGTH_SHORT).show();

 **/

   public void MoreInfo()
    {

//TODO: MAKE ACTivity

    }

    public void Virbate(int vib)
    {
        int val= Settings.System.getInt(getContentResolver(),Settings.System.HAPTIC_FEEDBACK_ENABLED,0);
        boolean mSettingEnabled=val!=0;
        if(mSettingEnabled)
            feedback.vibrate(vib);

    }


public void ChangeScreens(Class cl)
{

    Intent intent = new Intent(this, cl);
    startActivity(intent);

}
    /**Checks GPS Enable**/
    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled on your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Go to Settings Page To Enable GPS",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    /**Checks GPS Enable**/

    public void PutExtra(String put) {
        Intent ii = new Intent(this, InfoWebView.class);
        ii.putExtra("tag", put);
      // Anim(v);, View v
        startActivity(ii);
    }


    public void Anim(View button) {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        button.startAnimation(shake);

    }


}
