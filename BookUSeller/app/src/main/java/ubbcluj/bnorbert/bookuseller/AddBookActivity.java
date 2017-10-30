package ubbcluj.bnorbert.bookuseller;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddBookActivity extends AppCompatActivity {

    private static final String TO          = "bnorbertcristian@gmail.com";
    private static final String SUBJECT     = "New book";
    private static final String NEW_LINE    = "\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

    }

    public void addNewBook(View view){

        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL,TO);
        email.putExtra(Intent.EXTRA_SUBJECT,SUBJECT);
        email.putExtra(Intent.EXTRA_TEXT,prepareBodyEmail());
        email.setData(Uri.parse(prepareEmail()));

        email.setType("message/rfc822");

        startActivity(Intent.createChooser(email,"Please chooses how to send the email."));
    }

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


}
