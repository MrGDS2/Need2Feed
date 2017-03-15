package need2feed.george.meliobyte.com.need2feed;

/**
 * Created by Mrgds on 1/15/2016.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import need2feed.george.meliobyte.com.need2feed.Feedpage.TheFeed;
import need2feed.george.meliobyte.com.need2feed.Maps.MapsActivity;
import need2feed.george.meliobyte.com.need2feed.Paypal.PayPal;


public class ShaeMainFacebook extends AppCompatActivity  {
    Button inbox;
    Button Feed;
    Button Events;
    Button Maps;
    Button Donate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facebook_bread_of_life);
        inbox = (Button) findViewById(R.id.inbox);

        Events = (Button) findViewById(R.id.events);
        Feed = (Button) findViewById(R.id.feed);

        Maps = (Button) findViewById(R.id.GoogleMaps);
        Donate = (Button) findViewById(R.id.donate);

    }
/**
    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.inbox:
                messages();
                break;

            case R.id.GoogleMaps:
                Map();
                break;
            case R.id.events:
                EventLoadOut();


                break;
            case R.id.feed:
                Feed();
                break;
            case R.id.donate:
                Donation();
        }
    **/





    public void Feed()
    {
        Intent i= new Intent(this,TheFeed.class);
        startActivity(i);
        Toast.makeText(getApplicationContext(), "Share with the World!", Toast.LENGTH_LONG).show();


    }
    public void Donation() {


        Intent i = new Intent(this,PayPal.class);
        startActivity(i);

    }



    public void Map()
    {
        Intent i= new Intent(this,MapsActivity.class);
        startActivity(i);
        Toast.makeText(getApplicationContext(), "Map: Find Bread For Life", Toast.LENGTH_LONG).show();


    }



    public void messages()
    {
        Intent i= new Intent(this,Inbox.class);
        startActivity(i);
        Toast.makeText(getApplicationContext(), "Personal Messages", Toast.LENGTH_LONG).show();


    }

    public void EventLoadOut()
    {

        Intent i = new Intent(this, EventsGooglemaps.class);

        startActivity(i);

        Toast.makeText(getApplicationContext(), "Events for Bread of Life", Toast.LENGTH_LONG).show();

    }


}
