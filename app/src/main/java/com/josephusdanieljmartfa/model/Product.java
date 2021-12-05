package com.josephusdanieljmartfa.model;

public class Product extends Serializable {

    public String name;
    public int weight;
    public boolean conditionUsed;
    public double price;
    public double discount;
    public ProductCategory category;
    public int accountId;
    public byte shipmentPlans;

    public Product(int accountId, String name, int weight, boolean conditionUsed, double price, double discount, ProductCategory category, byte shipmentPlans) {
        this.accountId = accountId;
        this.name = name;
        this.weight = weight;
        this.conditionUsed = conditionUsed;
        this.price = price;
        this.discount = discount;
        this.category = category;
        this.shipmentPlans = shipmentPlans;
    }

    public String toString() {
        return "Name: " + this.name + "\n" + "accountId: " + this.accountId + "\n" + "Weight: " + this.weight + "\n" + "conditionUsed: " + this.conditionUsed;
    }
}
