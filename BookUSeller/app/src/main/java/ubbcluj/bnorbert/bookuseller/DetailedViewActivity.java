package ubbcluj.bnorbert.bookuseller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DetailedViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);

        Intent intent = getIntent();

        Book book = (Book) intent.getSerializableExtra(Constants.BOOK_KEY);

        putText(R.id.titleEditText,book.getTitle());
        putText(R.id.descriptionEditText,book.getDescription());
        putText(R.id.priceEditText,book.getPrice().toString());
        putText(R.id.sellerNameEditText,book.getSellerName());
        putText(R.id.sellerEmailEditText,book.getSellerEmail());
    }

    public void putText(int id,String toPut){
        ((EditText) findViewById(id)).setText(toPut, TextView.BufferType.EDITABLE);
    }
}
