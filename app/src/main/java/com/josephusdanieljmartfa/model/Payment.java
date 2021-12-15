package com.josephusdanieljmartfa.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Payment extends Invoice {

    public static class Record {
        public final Date date;
        public String message;
        public Status status;

        public Record(Status status, String message) {
            this.date = java.util.Calendar.getInstance().getTime();
            this.status = status;
            this.message = message;
        }
    }

    public List<Record> history = new ArrayList<>();
    public Shipment shipment;
    public int productCount;

    public Payment(int buyerId, int productId, int productCount, Shipment shipment) {
        super(buyerId, productId);
        this.productCount = productCount;
        this.shipment = shipment;
    }

    @Override
    public double getTotalPay(Product product) {
        return product.price - ((product.discount/100) * product.price);
    }
}
