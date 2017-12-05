package ubbcluj.bnorbert.bookuseller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import ubbcluj.bnorbert.bookuseller.activities.AddBookActivity;
import ubbcluj.bnorbert.bookuseller.activities.ListBooksActivity;
import ubbcluj.bnorbert.bookuseller.model.Book;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addBookAction(View view){
        Intent intent = new Intent(this,AddBookActivity.class);
        startActivity(intent);
    }

    public void listBooksAction(View view) {
        Intent intent = new Intent(this,ListBooksActivity.class);
        startActivity(intent);

    }
}
