package ubbcluj.bnorbert.bookuseller;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Book> currentBooks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentBooks.add(new Book().withTitle("Harry Potter"));
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
