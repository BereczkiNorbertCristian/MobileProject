package ubbcluj.bnorbert.bookuseller.notifications;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by bnorbert on 11.01.2018.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService
{
    private static final String TAG = "WHAT";
    @Override public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        Toast.makeText(this, "TOKE:" + refreshedToken, Toast.LENGTH_SHORT).show();
        
    }
}
