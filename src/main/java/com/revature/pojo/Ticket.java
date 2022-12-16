package com.revature.pojo;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Ticket {
    private Integer ticketId;
    private Double amount;
    private String description;
    private String status;
    private LocalDateTime dateSubmitted;
    private LocalDateTime dateProcessed;
    private Integer userId;

    public Ticket() {}

    public Ticket(Integer ticketId, Double amount, String description, String status, LocalDateTime dateSubmitted, LocalDateTime dateProcessed, Integer userId) {
        this.ticketId = ticketId;
        this.amount = amount;
        this.description = description;
        this.status = status;
        this.dateSubmitted = dateSubmitted;
        this.dateProcessed = dateProcessed;
        this.userId = userId;
    }

    public Ticket(Double amount, String description, String status, LocalDateTime dateSubmitted, LocalDateTime dateProcessed, Integer userId) {
        this.amount = amount;
        this.description = description;
        this.status = status;
        this.dateSubmitted = dateSubmitted;
        this.dateProcessed = dateProcessed;
        this.userId = userId;
    }

    public Ticket(Double amount, String description, String status, LocalDateTime dateSubmitted, LocalDateTime dateProcessed) {
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

    public LocalDateTime getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(LocalDateTime dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    public LocalDateTime getDateProcessed() {
        return dateProcessed;
    }

    public void setDateProcessed(LocalDateTime dateProcessed) {
        this.dateProcessed = dateProcessed;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
