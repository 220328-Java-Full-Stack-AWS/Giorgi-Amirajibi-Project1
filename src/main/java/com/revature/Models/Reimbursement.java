package com.revature.Models;

import java.sql.Timestamp;

public class Reimbursement {

    private int reimbAmount;
    private Timestamp reimbSubmitted;
    private Timestamp reimbResolved;
    private String reimbDescription;
    private String reimbReceipt;
    private int reimbAuthor;
    private int reimbResolver;
    private int reimbStatusId;
    private int reimbTypeId;


    public Reimbursement(){
        this.reimbAmount = 0;
        this.reimbSubmitted = null;
        this.reimbResolved = null;
        this.reimbDescription = "";
        this.reimbReceipt = "";
        this.reimbAuthor = 0;
        this.reimbResolver = 0;
        this.reimbStatusId = 0;
        this.reimbTypeId = 0;
    }

    public Reimbursement(int reimbAmount, Timestamp reimbSubmitted, Timestamp reimbResolved, String reimbDescription,
                         String reimbReceipt, int reimbAuthor, int reimbResolver, int reimbStatusId, int reimbTypeId) {
        this.reimbAmount = reimbAmount;
        this.reimbSubmitted = reimbSubmitted;
        this.reimbResolved = reimbResolved;
        this.reimbDescription = reimbDescription;
        this.reimbReceipt = reimbReceipt;
        this.reimbAuthor = reimbAuthor;
        this.reimbResolver = reimbResolver;
        this.reimbStatusId = reimbStatusId;
        this.reimbTypeId = reimbTypeId;
    }

    public int getReimbAmount() {
        return reimbAmount;
    }

    public void setReimbAmount(int reimbAmount) {
        this.reimbAmount = reimbAmount;
    }

    public Timestamp getReimbSubmitted() {
        return reimbSubmitted;
    }

    public void setReimbSubmitted(Timestamp reimbSubmitted) {
        this.reimbSubmitted = reimbSubmitted;
    }

    public Timestamp getReimbResolved() {
        return reimbResolved;
    }

    public void setReimbResolved(Timestamp reimbResolved) {
        this.reimbResolved = reimbResolved;
    }

    public String getReimbDescription() {
        return reimbDescription;
    }

    public void setReimbDescription(String reimbDescription) {
        this.reimbDescription = reimbDescription;
    }

    public String getReimbReceipt() {
        return reimbReceipt;
    }

    public void setReimbReceipt(String reimbReceipt) {
        this.reimbReceipt = reimbReceipt;
    }

    public int getReimbAuthor() {
        return reimbAuthor;
    }

    public void setReimbAuthor(int reimbAuthor) {
        this.reimbAuthor = reimbAuthor;
    }

    public int getReimbResolver() {
        return reimbResolver;
    }

    public void setReimbResolver(int reimbResolver) {
        this.reimbResolver = reimbResolver;
    }

    public int getReimbStatusId() {
        return reimbStatusId;
    }

    public void setReimbStatusId(int reimbStatusId) {
        this.reimbStatusId = reimbStatusId;
    }

    public int getReimbTypeId() {
        return reimbTypeId;
    }

    public void setReimbTypeId(int reimbTypeId) {
        this.reimbTypeId = reimbTypeId;
    }

    @Override
    public String toString() {
        return "Reimbursement{" +
                "reimbAmount=" + reimbAmount +
                ", reimbSubmitted=" + reimbSubmitted +
                ", reimbResolved=" + reimbResolved +
                ", reimbDescription='" + reimbDescription + '\'' +
                ", reimbReceipt='" + reimbReceipt + '\'' +
                ", reimbAuthor=" + reimbAuthor +
                ", reimbResolver=" + reimbResolver +
                ", reimbStatusId=" + reimbStatusId +
                ", reimbTypeId=" + reimbTypeId +
                '}';
    }
}
