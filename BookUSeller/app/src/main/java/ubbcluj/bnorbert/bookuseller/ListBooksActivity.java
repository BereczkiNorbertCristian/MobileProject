package ubbcluj.bnorbert.bookuseller;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListBooksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_books);

        ListView myList = (ListView) findViewById(R.id.listView);
        final ArrayList<Book> itemList = new ArrayList<>();
        itemList.add(new Book().withTitle("Harry Potter").withDescription("Wizardly").withPrice(23D).withSellerName("Norbert").withSellerEmail("bno@gmail.com"));
        itemList.add(new Book().withTitle("Matematica").withDescription("Maths").withPrice(44D).withSellerName("Norbert").withSellerEmail("bno@gmail.com"));
        itemList.add(new Book().withTitle("Introduction to Algorithms").withDescription("Arlgorithms").withPrice(100D).withSellerName("Norbert").withSellerEmail("bno@gmail.com"));

        final ArrayAdapter<Book> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, itemList);
        myList.setAdapter(adapter);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                final Book item = (Book) parent.getItemAtPosition(position);
                Intent intent = new Intent(view.getContext(),DetailedViewActivity.class);

                intent.putExtra(Constants.BOOK_KEY,item);
                startActivity(intent);
            }
        });
    }
}
