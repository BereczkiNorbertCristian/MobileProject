package ubbcluj.bnorbert.bookuseller.activities;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import ubbcluj.bnorbert.bookuseller.R;
import ubbcluj.bnorbert.bookuseller.db.AppDatabase;
import ubbcluj.bnorbert.bookuseller.db.BookDao;
import ubbcluj.bnorbert.bookuseller.db.DatabaseProvider;
import ubbcluj.bnorbert.bookuseller.model.Book;

public class AddBookActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, View.OnClickListener {

    private static final String TO          = "bnorbertcristian@gmail.com";
    private static final String SUBJECT     = "New book";
    private static final String NEW_LINE    = "\n";

    private BookDao     bookDao;
    private Calendar    calendar;
    private EditText    publishingDateET;
    private EditText    titleET;
    private EditText    sellerNameET;
    private EditText    sellerEmailET;
    private EditText    priceET;
    private EditText    descriptionET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        titleET = (EditText) findViewById(R.id.titleEditText);
        priceET = (EditText) findViewById(R.id.priceEditText);
        sellerEmailET = (EditText) findViewById(R.id.sellerEmailEditText);
        sellerNameET = (EditText) findViewById(R.id.sellerNameEditText);
        descriptionET = (EditText) findViewById(R.id.descriptionEditText);
        publishingDateET = (EditText) findViewById(R.id.publishingDateEditText);
        calendar = Calendar.getInstance();
        publishingDateET.setOnClickListener(this);

        AppDatabase appDatabase = DatabaseProvider.getInstance(getApplicationContext());
        bookDao = appDatabase.getBookDao();

    }

    public void addNewBook(View view){
        bookDao.save(new Book()
                .withTitle(titleET.getText().toString())
                .withDescription(descriptionET.getText().toString())
                .withPrice(Double.parseDouble(priceET.getText().toString()))
                .withSellerEmail(sellerEmailET.getText().toString())
                .withSellerName(sellerNameET.getText().toString())
                .withPublishingDate(publishingDateET.getText().toString()));
        finish();
    }

//    public void addNewBook(View view){
//
//        String preparedEmailBody;
//        try {
//            preparedEmailBody = prepareBodyEmail();
//        }
//        catch (Exception e){
//            new AlertDialog.Builder(AddBookActivity.this)
//                    .setTitle("Incorrect input")
//                    .setMessage("Be sure to use only digits for the price and to not leave any blanks.")
//                    .setCancelable(false)
//                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                        }
//                    })
//                    .show();
//            return;
//        }
//        Intent email = new Intent(Intent.ACTION_SEND);
//        email.putExtra(Intent.EXTRA_EMAIL,TO);
//        email.putExtra(Intent.EXTRA_SUBJECT,SUBJECT);
//        email.putExtra(Intent.EXTRA_TEXT,preparedEmailBody);
//        email.setData(Uri.parse(prepareEmail()));
//
//        email.setType("message/rfc822");
//
//        startActivity(Intent.createChooser(email,"Please chooses how to send the email."));
//    }

    public String prepareEmail(){

        return new StringBuilder()
                .append("mailto:").append(TO)
                .append("&subject=").append(Uri.encode(SUBJECT))
                .append("&body=").append(Uri.encode(prepareBodyEmail()))
                .toString();
    }

    public String prepareBodyEmail(){

        String title = unwrap(R.id.titleEditText);
        String description = unwrap(R.id.descriptionEditText);
        Double price = Double.parseDouble(unwrap(R.id.priceEditText));
        String sellerName = unwrap(R.id.sellerNameEditText);
        String sellerEmail = unwrap(R.id.sellerEmailEditText);

        return new StringBuilder()
                .append("Title: ").append(title).append(NEW_LINE)
                .append("Description: ").append(description).append(NEW_LINE)
                .append("Price: ").append(price.toString()).append(NEW_LINE)
                .append("Seller Name: ").append(sellerName).append(NEW_LINE)
                .append("Seller Email: ").append(sellerEmail).append(NEW_LINE)
                .toString();
    }

    public String unwrap(int id){

        return ((EditText) findViewById(id)).getText().toString();
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

        DatePickerDialog datePickerDialog = new DatePickerDialog(AddBookActivity.this, this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
        datePickerDialog.show();
    }
}
