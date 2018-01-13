package ubbcluj.bnorbert.bookuseller.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ubbcluj.bnorbert.bookuseller.Constants;
import ubbcluj.bnorbert.bookuseller.R;
import ubbcluj.bnorbert.bookuseller.db.BookDao;
import ubbcluj.bnorbert.bookuseller.db.DatabaseProvider;
import ubbcluj.bnorbert.bookuseller.model.Book;

public class DetailedViewActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, View.OnClickListener {

    BookDao bookDao;
    private Long bookId;
    private EditText descriptionET;
    private EditText priceET;
    private EditText sellerNameET;
    private EditText publishingDateET;
    private PieChart pieChart;
    private TextView emailView;
    private TextView titleView;
    private Boolean sameUser = false;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);
        initEditTexts();

        Intent intent = getIntent();
        Book book = (Book) intent.getSerializableExtra(Constants.BOOK_KEY);
        doEditTextSetText(book);
        publishingDateET.setOnClickListener(this);
        bookDao = DatabaseProvider.getInstance(getApplicationContext()).getBookDao();

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("books");

        sameUser = firebaseAuth.getCurrentUser().getEmail().equals(book.getSellerEmail());

        setPieChart();
    }

    private Book makeBookFromCurrentState(){
        return new Book()
                .withId(bookId)
                .withTitle(titleView.getText().toString())
                .withDescription(descriptionET.getText().toString())
                .withSellerName(sellerNameET.getText().toString())
                .withSellerEmail(emailView.getText().toString())
                .withPrice(Double.parseDouble(priceET.getText().toString()))
                .withPublishingDate(publishingDateET.getText().toString());
    }

    public void updateBook(View view) {
        if(sameUser) {
            Book updatedBook = makeBookFromCurrentState();
            bookDao.update(makeBookFromCurrentState());
            databaseReference.child(updatedBook.getTitle()).setValue(updatedBook);
            finish();
        }
        else{
            Toast.makeText(this, "This is not your book to update", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteBook(View view) {
        if(!sameUser) {
            bookDao.delete(makeBookFromCurrentState());
            databaseReference.child(titleView.getText().toString()).removeValue();
            finish();
        }
        else{
            Toast.makeText(this, "You cannot buy your own book!", Toast.LENGTH_SHORT).show();
        }
    }

    private void initEditTexts() {
        pieChart = (PieChart) findViewById(R.id.details_chart);
        titleView = (TextView) findViewById(R.id.titleView);
        descriptionET = (EditText) findViewById(R.id.descriptionEditText);
        priceET = (EditText) findViewById(R.id.priceEditText);

        emailView = (TextView) findViewById(R.id.emailView);
        sellerNameET = (EditText) findViewById(R.id.sellerNameEditText);
        publishingDateET = (EditText) findViewById(R.id.publishingDateEditText);
    }

    private void doEditTextSetText(Book book) {
        bookId = book.getId();
        titleView.setText(book.getTitle());
        descriptionET.setText(book.getDescription());
        priceET.setText(book.getPrice().toString());
        sellerNameET.setText(book.getSellerName());
        emailView.setText(book.getSellerEmail());
        publishingDateET.setText(book.getPublishingDate());

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        this.publishingDateET.setText(new StringBuilder()
                .append(view.getDayOfMonth())
                .append("-")
                .append(view.getMonth())
                .append("-")
                .append(view.getYear())
        );
    }

    @Override
    public void onClick(View v) {
        String[] dateString = publishingDateET.getText().toString().split("-");
        Date date = new Date(Integer.valueOf(dateString[2]), Integer.valueOf(dateString[1]), Integer.valueOf(dateString[0]));
        DatePickerDialog datePickerDialog = new DatePickerDialog(DetailedViewActivity.this, this,
                date.getYear(), date.getMonth(), date.getDate()
        );
        datePickerDialog.show();
    }

    private void setPieChart() {
        List<PieEntry> entries = new ArrayList<>(2);
        entries.add(new PieEntry(getAveragePrice(),"Average price"));
        entries.add(new PieEntry(Float.parseFloat(priceET.getText().toString()),"This book's price"));
        PieDataSet pieDataSet = new PieDataSet(entries, "Price comparison");
//        pieDataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        List<Integer> cols = new ArrayList<>();
        cols.add(Color.BLUE);
        cols.add(Color.RED);
        pieDataSet.setColors(cols);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
    }

    private float getAveragePrice() {
        List<Book> books = bookDao.findAll();
        float sum = 0f;
        for(Book book : books){
            sum += (float) book.getPrice().doubleValue();
        }
        return sum / books.size();
    }

}
