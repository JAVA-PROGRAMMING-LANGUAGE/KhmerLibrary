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
public class Material {
    private int id;
    private String name;
    Double price;
    int qty;
    String donate, other;

    public Material(int id, String name, Double price, int qty, String donate, String other) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.qty = qty;
        this.donate = donate;
        this.other = other;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getDonate() {
        return donate;
    }

    public void setDonate(String donate) {
        this.donate = donate;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }


}
