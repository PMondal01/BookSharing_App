package com.example.boighor;

import android.widget.EditText;

import com.google.firebase.database.Exclude;

public class Upload {

    private String BookName;
    private String WritterName;

    private String Price;
    private String imageUrl;
    private String Number;

    private String key;

    public Upload()
    {

    }

    public Upload(String book_name, String writer_name,String price,  String imageUri, String number)
    {
        this.BookName =book_name;
        this.WritterName=writer_name;
        this.Price=price;
        this.imageUrl =imageUri;
        this.Number=number;
    }

    public String getBookName()
    {
        return BookName;
    }

    public void setBookName(String bookName)
    {
        this.BookName = bookName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getWritterName() {
        return WritterName;
    }

    public void setWritterName(String writterName) {
        WritterName = writterName;
    }



    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }


    @Exclude
    public String getKey() {
        return key;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }
}
