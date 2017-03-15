package need2feed.george.meliobyte.com.need2feed.Feedpage;;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.widget.Button;
import android.widget.ShareActionProvider;


import com.facebook.CallbackManager;
import com.facebook.login.widget.ProfilePictureView;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;

import need2feed.george.meliobyte.com.need2feed.R;

public class TheFeed extends Activity {

    private ShareActionProvider mShareActionProvider;
    private ProfilePictureView mProfilePic;

    private ShareButton mFbShare;

    CallbackManager callbackManager;
    ShareDialog shareDialog;
    private String ContentTitle = " Posted From Need 2 Feed APP";
    private String ContentDescription = "Bread Of Life has joined Need2Feed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thefeed);


//mProfilePic= (ProfilePictureView) findViewById(R.id.profilePic);

        mFbShare = (ShareButton) findViewById(R.id.fb_share_button);
        //  mPhotoShare = (Button) findViewById(R.id.tweetShare);


        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("http://www.givingtreeglobal.org/bread-of-life"))
                .build();


        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentTitle(ContentTitle) //add in custom Text from user
                    .setContentDescription(ContentDescription)
                    .setContentUrl(Uri.parse("http://www.givingtreeglobal.org/bread-of-life"))  //Same link always for organization
                    .build();
            mFbShare.setShareContent(content);
            shareDialog.show(linkContent);


        }

    }
}
