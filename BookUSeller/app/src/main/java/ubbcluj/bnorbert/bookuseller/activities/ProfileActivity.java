package ubbcluj.bnorbert.bookuseller.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ubbcluj.bnorbert.bookuseller.R;

public class ProfileActivity extends AppCompatActivity {

    private static final String USERS_TABLE = "users";

    FirebaseAuth firebaseAuth;
    DatabaseReference usersDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        firebaseAuth = FirebaseAuth.getInstance();
        usersDatabaseRef = FirebaseDatabase.getInstance().getReference(USERS_TABLE);
    }

    public void addBookAction(View view){
        usersDatabaseRef.child(firebaseAuth.getCurrentUser().getUid()).child("premium")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Boolean isPremium = dataSnapshot.getValue(Boolean.class);
                        if(isPremium){
                            startActivity(new Intent(getApplicationContext(),AddBookActivity.class));
                        }
                        else {
                            Toast.makeText(ProfileActivity.this, "Only Premium users can sell books", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

    public void listBooksAction(View view) {
        Intent intent = new Intent(this,ListBooksActivity.class);
        startActivity(intent);
    }

    public void logOut(View view) {
        firebaseAuth.signOut();
        finish();
    }
}
