package com.revature.pojo;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Ticket {
    private Integer ticketId;
    private Double amount;
    private String description;
    private String status;
    private LocalDate dateSubmitted;
    private LocalDate dateProcessed;
    private Integer userId;

    public Ticket() {}

    public Ticket(Integer ticketId, Double amount, String description, String status, LocalDate dateSubmitted, LocalDate dateProcessed, Integer userId) {
        this.ticketId = ticketId;
        this.amount = amount;
        this.description = description;
        this.status = status;
        this.dateSubmitted = dateSubmitted;
        this.dateProcessed = dateProcessed;
        this.userId = userId;
    }

    public Ticket(Double amount, String description, String status, LocalDate dateSubmitted, LocalDate dateProcessed, Integer userId) {
        this.amount = amount;
        this.description = description;
        this.status = status;
        this.dateSubmitted = dateSubmitted;
        this.dateProcessed = dateProcessed;
        this.userId = userId;
    }

    public Ticket(Double amount, String description, String status, LocalDate dateSubmitted, LocalDate dateProcessed) {
        this.amount = amount;
        this.description = description;
        this.status = status;
        this.dateSubmitted = dateSubmitted;
        this.dateProcessed = dateProcessed;
    }

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(LocalDate dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    public LocalDate getDateProcessed() {
        return dateProcessed;
    }

    public void setDateProcessed(LocalDate dateProcessed) {
        this.dateProcessed = dateProcessed;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
