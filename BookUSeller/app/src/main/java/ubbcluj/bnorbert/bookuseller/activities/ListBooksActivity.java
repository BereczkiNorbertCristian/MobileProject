package ubbcluj.bnorbert.bookuseller.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ubbcluj.bnorbert.bookuseller.R;
import ubbcluj.bnorbert.bookuseller.db.AppDatabase;
import ubbcluj.bnorbert.bookuseller.db.BookDao;
import ubbcluj.bnorbert.bookuseller.db.DatabaseProvider;
import ubbcluj.bnorbert.bookuseller.model.Book;

import static ubbcluj.bnorbert.bookuseller.Constants.BOOK_KEY;

public class ListBooksActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, ValueEventListener {

    private static final String BOOKS_TABLE = "books";

    List<Book> books;
    BookDao bookDao;
    BookAdapter bookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_books);
        ListView bookList = (ListView) findViewById(R.id.listView);

        AppDatabase db = DatabaseProvider.getInstance(getApplicationContext());
        bookDao = db.getBookDao();
        books = new ArrayList<>();

        FirebaseDatabase
                .getInstance()
                .getReference(BOOKS_TABLE)
                .addValueEventListener(this);

        bookAdapter = new BookAdapter(this,books);
        bookList.setAdapter(bookAdapter);
        bookList.setOnItemClickListener(this);
    }

    @Override
    protected void onResume(){
        super.onResume();

        books.clear();
        books.addAll(bookDao.findAll());
        bookAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this,DetailedViewActivity.class);

        Book clickedBook = books.get(position);

        intent.putExtra(BOOK_KEY, clickedBook);

        startActivity(intent);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {

        for(DataSnapshot bookSnapshot : dataSnapshot.getChildren()) {
            Book book = bookSnapshot.getValue(Book.class);
            if(bookDao.findOne(book.getTitle()) == null)
                bookDao.save(book);
            else
                bookDao.update(book);
        }
        books.clear();
        books.addAll(bookDao.findAll());
        bookAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }


    private class BookAdapter extends ArrayAdapter<Book> {

        private final List<Book>    values;
        private final Context       context;

        BookAdapter(Context context,List<Book> values) {
            super(context, -1, values);
            this.values = values;
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.row_layout,parent,false);
            TextView bookTitle = (TextView) rowView.findViewById(R.id.book_title);
            TextView bookPrice = (TextView) rowView.findViewById(R.id.book_price);

            bookTitle.setText(values.get(position).getTitle());
            bookPrice.setText(values.get(position).getPrice().toString());

            return rowView;
        }
    }
}
