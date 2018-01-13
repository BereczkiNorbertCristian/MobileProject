package ubbcluj.bnorbert.bookuseller.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ubbcluj.bnorbert.bookuseller.MainActivity;
import ubbcluj.bnorbert.bookuseller.R;
import ubbcluj.bnorbert.bookuseller.model.User;

public class SignUpActivity extends AppCompatActivity {

    EditText emailText;
    EditText passwordText;
    EditText nameText;
    CheckBox isPremium;
    FirebaseAuth firebaseAuth;
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        emailText = (EditText) findViewById(R.id.emailUpText);
        passwordText = (EditText) findViewById(R.id.passwordUpText);
        nameText = (EditText) findViewById(R.id.nameUpText);
        isPremium = (CheckBox) findViewById(R.id.isPremium);

        firebaseAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference("users");

        Log.d("WHAT","WE HAVE JUST CREATED");
    }

    public void registerUser(View view) {

        final String email = emailText.getText().toString();
        final String pass = passwordText.getText().toString();
        firebaseAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        try {
                            if (task.isSuccessful()) {
                                dbRef.child(firebaseAuth.getCurrentUser().getUid()).setValue(new User(
                                        nameText.getText().toString(),
                                        email,
                                        pass,
                                        isPremium.isChecked()
                                ));
                                Intent i = new Intent(SignUpActivity.this, MainActivity.class);
                                startActivity(i);
                            } else {
                                Log.d("TASK",task.getException().getMessage());
                                Log.d("TASK",task.getResult().toString());
                                Log.d("TASK",new Boolean(task.isComplete()).toString());
                                Log.d("TASK",new Boolean(task.isSuccessful()).toString());
                                Toast.makeText(SignUpActivity.this, "Registration failed",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                        catch (Exception e) {
                            Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
