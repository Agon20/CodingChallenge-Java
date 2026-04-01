package com.example.codingchallengejava.application.dtos;

import java.time.LocalDateTime;

public class ApplicationResponse {

    private long id;
    private double premium;
    private LocalDateTime createdDate;

    public ApplicationResponse(long id, double premium, LocalDateTime createdDate) {
        this.id = id;
        this.premium = premium;
        this.createdDate = createdDate;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public double getPremium() {
        return premium;
    }
    public void setPremium(double premium) {
        this.premium = premium;
    }
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
