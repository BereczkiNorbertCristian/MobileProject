package ubbcluj.bnorbert.bookuseller.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * Created by bnorbert on 30.10.2017.
 */
@Entity
public class Book implements Serializable{

    @PrimaryKey(autoGenerate = true)
    private Long    id;
    private String  title;
    private String  description;
    private Double  price;
    private String  sellerName;
    private String  sellerEmail;
    private String  publishingDate;

    public Book(){}

    public Book withId(Long id){
        this.id = id;
        return this;
    }

    public Book withTitle(String title){
        this.title = title;
        return this;
    }

    public Book withPublishingDate(String date){
        this.publishingDate = date;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPublishingDate() {
        return publishingDate;
    }

    public void setPublishingDate(String publishingDate) {
        this.publishingDate = publishingDate;
    }

    @Override
    public String toString() {
        return
                "'" + title + '\'' +
                ", " + price + "$";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book other = (Book) o;

        return new EqualsBuilder()
                .append(id, other.getId())
                .append(title,other.getTitle())
                .append(sellerEmail,other.getSellerEmail())
                .append(sellerName,other.getSellerName())
                .append(price,other.getPrice())
                .append(description,other.getDescription())
                .append(publishingDate,other.getPublishingDate())
                .build();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(title).append(description)
                .append(price).append(sellerName)
                .append(sellerEmail).append(publishingDate)
                .build();
    }

}
