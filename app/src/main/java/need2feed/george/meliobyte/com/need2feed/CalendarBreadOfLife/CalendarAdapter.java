package need2feed.george.meliobyte.com.need2feed.CalendarBreadOfLife;

/**
 * Created by Mrgds & Jacob on 8/18/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.CalendarContract;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import need2feed.george.meliobyte.com.need2feed.CONSTANTS;
import need2feed.george.meliobyte.com.need2feed.R;


public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder> {
    //private ArrayList<CalendarScreen.CalendarInfo> mDataset;
    private List<com.github.sundeepk.compactcalendarview.domain.Event> mDataset;

    CalendarScreen in;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        //public Button addToCalendar;
        public CardView cv;
        public int colored;

        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.calendarTexted);
            //addToCalendar = (Button) v.findViewById(R.id.addCal);
            cv = (CardView) v.findViewById(R.id.card_view1);
            colored = Color.RED; //change back to blue
            cv.setCardBackgroundColor(colored);

        }
    }

    /*// Provide a suitable constructor (depends on the kind of dataset)
    public CalendarAdapter(ArrayList<CalendarScreen.CalendarInfo> myDataset, CalendarScreen in) {
        mDataset = myDataset;
        this.in = in;
    }*/

    // Provide a suitable constructor (depends on the kind of dataset)
    public CalendarAdapter(List<com.github.sundeepk.compactcalendarview.domain.Event> myDataset, CalendarScreen in) {
        mDataset = myDataset;
        this.in = in;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CalendarAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.calendartext, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        String info = "";
        final com.google.api.services.calendar.model.Event event = ((CalendarScreen.CalendarInfo) mDataset.get(position).getData()).getEvent();//.getEvent();

        info += event.getSummary() + "\n" +
                "Description: " + event.getDescription() + "\n" +
                "Date: " + event.getStart().getDate() + "\n" + event.getEnd().getDate();

        //TODO: Work here on getting this looking nicer

        final com.google.api.services.calendar.model.Event ever = event;

        holder.mTextView.setText(info);


        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                readCalendarEvent(in);

                if (eventCheck(ever)) {

                    //if (holder.colored==Color.WHITE) {

                    final ContentValues events = new ContentValues();
                    events.put(CalendarContract.Events.CALENDAR_ID, 1);

                    events.put(CalendarContract.Events.TITLE, event.getSummary());
                    events.put(CalendarContract.Events.DESCRIPTION, event.getDescription());
                    event.setStart(event.getStart());
                    events.put(CalendarContract.Events.DTSTART, event.getStart().getDate().getValue() + (1000 * 60 * 60 * 24));
                    events.put(CalendarContract.Events.DTEND, event.getEnd().getDate().getValue());
                    String timeZone = TimeZone.getDefault().getID();
                    events.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone);


                    Uri baseUri;
                    if (Build.VERSION.SDK_INT >= 8) {
                        baseUri = Uri.parse("content://com.android.calendar/events");
                    } else {
                        baseUri = Uri.parse("content://calendar/events");
                    }

                    in.getContentResolver().insert(baseUri, events);

                    Toast.makeText(in, "Event Added", Toast.LENGTH_SHORT).show();


                    float[] hsv = new float[3];
                    int color = holder.colored;
                    Color.colorToHSV(color, hsv);
                    hsv[2] *= 0.8f; // value component
                    color = Color.HSVToColor(hsv);
                    holder.colored = color;
                    holder.cv.setCardBackgroundColor(color);


                    Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                    emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{CONSTANTS.BREADOFLIFE_EMAIL});
                    emailIntent.setType("message/rfc822");
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "From an Need2Feed app user");
                    String emailText = "Hello Mrs.Blank,\nI would like to attend " +  //TODO:UPdate This
                            event.getSummary() + " at " +
                            event.getStart().getDate() + " to " +
                            event.getEnd().getDate();
                    emailIntent.putExtra(Intent.EXTRA_TEXT, emailText);
                    /**Checks to see if user has email app/ if not takes to market to download**/
                    try {
                        in.startActivity(Intent.createChooser(emailIntent,
                                "Send email using..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Log.wtf("WTF", ex);
                    }


                } else {
                    Toast.makeText(in, "You already have it", Toast.LENGTH_SHORT).show();
                }



            }
        });

    }


    public boolean eventCheck(com.google.api.services.calendar.model.Event e) {


        for (int i = 0; i < nameOfEvent.size(); i++) {

            //Log.e("Line 172", nameOfEvent.get(i) + " name");
            //Log.e("Line 172", e.getSummary() + " e");
            //Log.e("Line 173", descriptions.get(i) + "");
            //Log.e("Line 174", startDates.get(i) + "");
            if (e.getSummary() != null) {
                if (e.getSummary().equals(nameOfEvent.get(i))) {
                    if (e.getDescription() == null || descriptions.get(i) == null) {
                        return false;
                    } else if (e.getDescription().equals(descriptions.get(i))) {
                        return false;
                    }

                }

                if (getDate(e.getStart().getDate().getValue() + (1000 * 60 * 60 * 24)) == startDates.get(i)) {
                    return false;
                }
            }
        }

        return true;
    }

    public static ArrayList<String> nameOfEvent = new ArrayList<String>();
    public static ArrayList<String> startDates = new ArrayList<String>();
    public static ArrayList<String> endDates = new ArrayList<String>();
    public static ArrayList<String> descriptions = new ArrayList<String>();

    public static ArrayList<String> readCalendarEvent(Context context) {
        Cursor cursor = context.getContentResolver()
                .query(
                        Uri.parse("content://com.android.calendar/events"),
                        new String[]{"calendar_id", "title", "description",
                                "dtstart", "dtend", "eventLocation"}, null,
                        null, null);
        cursor.moveToFirst();
        // fetching calendars name
        String CNames[] = new String[cursor.getCount()];

        // fetching calendars id
        nameOfEvent.clear();
        startDates.clear();
        endDates.clear();
        descriptions.clear();
        for (int i = 0; i < CNames.length; i++) {

            nameOfEvent.add(cursor.getString(1));
            startDates.add(getDate(Long.parseLong(cursor.getString(3))));
            try {
                endDates.add(getDate(Long.parseLong(cursor.getString(4))));
            } catch (NumberFormatException e) {
                endDates.add(-1 + "");
            }
            descriptions.add(cursor.getString(2));
            CNames[i] = cursor.getString(1);

            //Log.e("Line 210", CNames[i]);

            cursor.moveToNext();

        }
        return nameOfEvent;
    }

    public static String getDate(long milliSeconds) {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "dd/MM/yyyy hh:mm:ss a");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}
