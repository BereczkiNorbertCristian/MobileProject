package ubbcluj.bnorbert.bookuseller.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

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
    private EditText titleET;
    private EditText descriptionET;
    private EditText priceET;
    private EditText sellerEmailET;
    private EditText sellerNameET;
    private EditText publishingDateET;
    private PieChart pieChart;

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

        setPieChart();
    }

    private Book makeBookFromCurrentState(){
        return new Book()
                .withId(bookId)
                .withTitle(titleET.getText().toString())
                .withDescription(descriptionET.getText().toString())
                .withSellerName(sellerNameET.getText().toString())
                .withSellerEmail(sellerEmailET.getText().toString())
                .withPrice(Double.parseDouble(priceET.getText().toString()))
                .withPublishingDate(publishingDateET.getText().toString());
    }

    public void updateBook(View view) {
        bookDao.update(makeBookFromCurrentState());
        finish();
    }

    public void deleteBook(View view) {
        bookDao.delete(makeBookFromCurrentState());
        finish();
    }

    private void initEditTexts() {
        pieChart = (PieChart) findViewById(R.id.details_chart);
        titleET = (EditText) findViewById(R.id.titleEditText);
        descriptionET = (EditText) findViewById(R.id.descriptionEditText);
        priceET = (EditText) findViewById(R.id.priceEditText);
        sellerEmailET = (EditText) findViewById(R.id.sellerEmailEditText);
        sellerNameET = (EditText) findViewById(R.id.sellerNameEditText);
        publishingDateET = (EditText) findViewById(R.id.publishingDateEditText);
    }

    private void doEditTextSetText(Book book) {
        bookId = book.getId();
        titleET.setText(book.getTitle());
        descriptionET.setText(book.getDescription());
        priceET.setText(book.getPrice().toString());
        sellerNameET.setText(book.getSellerName());
        sellerEmailET.setText(book.getSellerEmail());
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
