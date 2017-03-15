package need2feed.george.meliobyte.com.need2feed.Maps;

import android.*;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import need2feed.george.meliobyte.com.need2feed.*;
import need2feed.george.meliobyte.com.need2feed.BreadofLife.BreadOfLife;
import need2feed.george.meliobyte.com.need2feed.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private double BREAD_OF_LIFE_LNG = -73.6874;
    private double BREAD_OF_LIFE_LAT = 40.9807;
 BreadOfLife Usrlatlng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(need2feed.george.meliobyte.com.need2feed.R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng BreadOfLifeLocate = new LatLng(BREAD_OF_LIFE_LAT, BREAD_OF_LIFE_LNG);
        mMap.addMarker(new MarkerOptions().position(BreadOfLifeLocate).title("Bread of Life Rye,NY").icon(BitmapDescriptorFactory.fromResource(R.drawable.bofmkr)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(BreadOfLifeLocate, 15));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
        }
        mMap.setMyLocationEnabled(true);
        mMap.getMaxZoomLevel();

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                double DirectionsOnClickLat = marker.getPosition().latitude;
                double DirectionsOnClickLong = marker.getPosition().longitude;//gets clicked info window position
                OnLocationGo(DirectionsOnClickLat, DirectionsOnClickLong, Usrlatlng.Latitude, Usrlatlng.Longitude);//passes to google maps
                //   Log.d("Pace marker clicked",DirectionsOnClickLong);
               // Tracks("Used GPS on Maps", marker.getTitle() + " " + marker.getPosition());
            }
        });

    }
    /*On info Window Clicked End*/



public double getLat(double lat)
{

    return lat;
}
    public double getLong(double longit)
    {

        return longit;
    }







    public void onSearch(View view)
    {

        EditText location_tf= (EditText)findViewById(R.id.address);
        String location= location_tf.getText().toString();

        List<Address> addressList=null;
        if(location !=null || location.equals(" "))
        {
            Geocoder geocoder= new Geocoder(this);

            try {
                addressList = geocoder.getFromLocationName(location, 1);
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }

            Address address=addressList.get(0);

            LatLng latLng= new LatLng(address.getLatitude(),address.getLongitude());

/*Adds Starting view for Bread of Life  Location      */
            mMap.addMarker(new MarkerOptions().position(latLng).title(location).icon(BitmapDescriptorFactory.fromResource(R.drawable.n2fmkr)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }


    public double FindDistance() {
        double lat1 = mMap.getMyLocation().getLatitude();  /*Gets user Location      */
        double lon1 = mMap.getMyLocation().getLongitude();
        double lat2 = BREAD_OF_LIFE_LAT;
        double lon2 = BREAD_OF_LIFE_LNG;                    /*Gets Bread of Life Location     */
        double distance = distance(lat1, lon1, lat2, lon2);


        return  (distance/8 *5);  //distance in miles
    }



    public static final double distance(double lat1, double lon1, double lat2, double lon2)
    {
/*Gets longitude and latitude of both points     */
        double RadiusofEarth=6371;
        double dlat=deg2rad(lat2-lat1);
        double dlon=deg2rad(lon2-lon1);

        double nesscaryMath= Math.sin(dlat/2)*Math.sin(dlat/2)+Math.cos(deg2rad(lat1))*Math.cos(deg2rad(lat2))
                *Math.sin(dlon/2)*Math.sin(dlon/2);

        double calculation=2 * Math.atan2(Math.sqrt(nesscaryMath),Math.sqrt(1-nesscaryMath));
        double dist=RadiusofEarth*calculation;  //Distance in km



        return (dist);
    }


    /**
     * Checks Takes user to location on map with chooser
     **/
    private void OnLocationGo(final double goLat, final double goLong, double userLat, double userLong) {

        final String UserLat = String.valueOf(userLat);
        final String UserLong = String.valueOf(userLong); //gets user latitude & longitude


        final String MarkerLong = String.valueOf(goLong); //gets user longitude & latitude
        final String MarkerLat = String.valueOf(goLat);

        Log.d("UserLong", UserLong);
        Log.d("UserLat", UserLat);

        AlertDialog ad = new AlertDialog.Builder(this).setMessage(
                R.string.OnPressMessage).setTitle(
                R.string.UndertitleLocation).setCancelable(false)
                .setNeutralButton(android.R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                Toast.makeText(MapsActivity.this, "Maybe next time then...", Toast.LENGTH_LONG).show();  // User selects Cancel, discard all changes
                            }
                        }).setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                        Uri.parse("http://maps.google.com/maps?saddr=" + UserLat
                                                + "," + UserLong + "&" + "daddr=" + MarkerLat + "," + MarkerLong));
                                startActivity(intent);
                                Log.d("OkDirections", "user is getting directions");

                            }
                        }).show();

    }
    /**Checks Takes user to location on map with chooser**/






    /*Converts Degrees to radians      */
    private static final double deg2rad(double deg)
    {
        return (deg * Math.PI / 180.0);
    }


    /*Gets user Location versus Bread of Life Distance Only   */
    public  void ShowMyDistance(View view)
    {   try {
        Toast.makeText(getApplicationContext(), "You are " + ((int) FindDistance()) + " Miles Away From Bread Of Life", Toast.LENGTH_LONG).show();
    }

    catch (Exception nd)
    {


    }


    }

}
