/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khmerlibrary.model;

/**
 *
 * @author SRUN VANNARA
 */
public class Book {
    private String b_id, title, sub_title, cat, author, print_year;
    private int qty, num_day;
    private String other;

    public Book(String b_id, String title, String sub_title, String cat, String author, String print_year, int qty, String other) {
        this.b_id = b_id;
        this.title = title;
        this.sub_title = sub_title;
        this.cat = cat;
        this.author = author;
        this.print_year = print_year;
        this.qty = qty;
        this.other = other;
    }

    public Book(String b_id, String title, String sub_title, String cat, String author) {
        this.b_id = b_id;
        this.title = title;
        this.sub_title = sub_title;
        this.cat = cat;
        this.author = author;
    }

    public Book(String b_id, String title, String sub_title, int num_day) {
        this.b_id = b_id;
        this.title = title;
        this.sub_title = sub_title;
        this.num_day = num_day;
    }

    public String getB_id() {
        return b_id;
    }

    public void setB_id(String b_id) {
        this.b_id = b_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSub_title() {
        return sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPrint_year() {
        return print_year;
    }

    public void setPrint_year(String print_year) {
        this.print_year = print_year;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getNum_day() {
        return num_day;
    }

    public void setNum_day(int num_day) {
        this.num_day = num_day;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

}
