package ubbcluj.bnorbert.bookuseller;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;

import ubbcluj.bnorbert.bookuseller.activities.AddBookActivity;
import ubbcluj.bnorbert.bookuseller.activities.ListBooksActivity;
import ubbcluj.bnorbert.bookuseller.activities.ProfileActivity;
import ubbcluj.bnorbert.bookuseller.activities.SignUpActivity;
import ubbcluj.bnorbert.bookuseller.model.Book;

public class MainActivity extends AppCompatActivity {

    EditText emailText;
    EditText passwordText;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailText = (EditText) findViewById(R.id.emailText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        firebaseAuth = FirebaseAuth.getInstance();

    }

    public void signIn(View view) {
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                            goToProfileIntent();
                        else
                            Toast.makeText(MainActivity.this, "Auth failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void goToProfileIntent() {
        startActivity(new Intent(this, ProfileActivity.class));
    }

    public void signUp(View view) {
        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(intent);
    }
}
