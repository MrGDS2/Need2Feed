package need2feed.george.meliobyte.com.need2feed.Info;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import need2feed.george.meliobyte.com.need2feed.CONSTANTS;
import need2feed.george.meliobyte.com.need2feed.R;

public class InfoWebView extends AppCompatActivity {
    FloatingActionButton CallFab, EmailFab;
    ToggleButton plusmore;
    String link= CONSTANTS.Info_BreadOfLife;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_web_view);
        Intent i = getIntent();
        Bundle b = i.getExtras();
      WebView viewfrag = (WebView) findViewById(R.id.wv);
        viewfrag.getSettings().setJavaScriptEnabled(true);
    //    viewfrag.loadUrl((String) b.get("tag")); //this is used for any URL not just BOL
        viewfrag.loadUrl(link);
        CallFab = (FloatingActionButton) findViewById(R.id.callfab);
        Ripple(CallFab); //ripple animation

        EmailFab = (FloatingActionButton) findViewById(R.id.emailfab);
        Ripple(EmailFab);//ripple animation

        CallFab.setVisibility(View.INVISIBLE);
        EmailFab.setVisibility(View.INVISIBLE);
        /**toggle more options******/
        plusmore = (ToggleButton) findViewById(R.id.plusemore);

        plusmore.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
             //   Tracks("More Options", "More Options==>Email");

                if (isChecked) {
                    CallFab.setVisibility(View.VISIBLE);
                    EmailFab.setVisibility(View.VISIBLE);
                    plusmore.setBackgroundResource(R.drawable.minussign);
                    Log.d("Minussign==>", "showing");

                    // The toggle show options

                } else {
                    CallFab.setVisibility(View.INVISIBLE);
                    EmailFab.setVisibility(View.INVISIBLE);

                    plusmore.setBackgroundResource(R.drawable.plussign);
                    Log.d("Plussign==>", "showing ");
                    // The toggle show no options

                }
            }
        });

        /*** Toggle For more options    *******************************************************/

    }

    public void Ripple(FloatingActionButton fab) {
        ColorStateList ripple = ContextCompat.getColorStateList(this, R.drawable.fab_ripple_color);
        fab.setBackgroundTintList(ripple);


    }

}


/**

 public void LoadOutsidePdf(String pdf_url) {
 Intent browserIntent = new Intent(Intent.ACTION_VIEW);
 browserIntent.setDataAndType(Uri.parse(pdf_url), "text/html");

 Intent chooser = Intent.createChooser(browserIntent, getString(R.string.chooser_title));
 chooser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // optional

 startActivity(chooser);


 // Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdf_url));

 //   startActivity(browserIntent);
 }

 public void MethodOfContact(final String number, final String Email, final String Greeting) {

 ad = new AlertDialog.Builder(this).setIcon(R.drawable.contact).setTitle(
 R.string.methodofcontact).setMessage(
 R.string.calloremail).setCancelable(false)
 .setNegativeButton(R.string.Email,
 new DialogInterface.OnClickListener() {
 public void onClick(DialogInterface dialog,
 int whichButton) {
 // user emails SSS Employer

 Intent emailj = new Intent(android.content.Intent.ACTION_SEND);
 emailj.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{Email});
 emailj.setType("plain/text");
 emailj.putExtra(Intent.EXTRA_SUBJECT, "From an SSS app user");
 emailj.putExtra(Intent.EXTRA_TEXT, Greeting);
 /**Checks to see if user has email app/ if not takes to market to download
try {
        startActivity(Intent.createChooser(emailj,
        "Send email using..."));
        } catch (android.content.ActivityNotFoundException ex) {
        Toast.makeText(SSSprogram.this,
        "No email clients installed.",
        Toast.LENGTH_SHORT).show();
        market.TakeUserToMarket(SSSprogram.this, "com.microsoft.exchange.mowa");
        }

        }
        }).setNeutralButton(R.string.Call,
        new DialogInterface.OnClickListener() {
public void onClick(DialogInterface dialog,
        int whichButton) {


        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + number));
        startActivity(intent);

        ;  // User Calls SSS Employer
        }
        }).setPositiveButton(android.R.string.cancel,
        new DialogInterface.OnClickListener() {
public void onClick(DialogInterface dialog,
        int whichButton) {
        ad.cancel();

        // cancel
        }
        }).show();


        }


public void Tracks(String catogory, String action) {
        mTracker.send(new HitBuilders.EventBuilder()
        .setCategory(catogory)
        .setAction(action)
        .build());

        }


        ***/