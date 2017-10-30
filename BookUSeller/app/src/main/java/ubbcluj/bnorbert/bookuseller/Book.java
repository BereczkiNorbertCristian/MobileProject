package ubbcluj.bnorbert.bookuseller;

import java.io.Serializable;

/**
 * Created by bnorbert on 30.10.2017.
 */

public class Book implements Serializable{

    private String  title;
    private String  description;
    private Double  price;
    private String  sellerName;
    private String  sellerEmail;

    public Book(){}

    public Book withTitle(String title){
        this.title = title;
        return this;
    }

    public Book withDescription(String description){
        this.description = description;
        return this;
    }

    public Book withPrice(Double price){
        this.price = price;
        return this;
    }

    public Book withSellerName(String sellerName){
        this.sellerName = sellerName;
        return this;
    }

    public Book withSellerEmail(String sellerEmail){
        this.sellerEmail = sellerEmail;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerEmail() {
        return sellerEmail;
    }

    public void setSellerEmail(String sellerEmail) {
        this.sellerEmail = sellerEmail;
    }

    @Override
    public String toString() {
        return
                "'" + title + '\'' +
                ", " + price + "$";
    }
}
