package com.revature.persistence;

import com.revature.pojo.Ticket;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDao {
    Connection connection;

    public TicketDao(){
        connection = ConnectionManager.getConnection();
    }

    //CRUD
    //Create a new ticket
    //Read existing tickets, filter by status, sort by date submitted/processed?
    //Update a ticket
    //Delete a ticket

    public void create(Ticket ticket){
        try {
            String sql = "INSERT INTO tickets (amount, description, status, date_submitted, date_processed, user_id)" +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setDouble(1, ticket.getAmount());
            pstmt.setString(1, ticket.getDescription());
            pstmt.setString(1, ticket.getStatus());
            pstmt.setDate(1, Date.valueOf(ticket.getDateSubmitted()));
            pstmt.setDate(1, Date.valueOf(ticket.getDateProcessed()));
            pstmt.setInt(1, ticket.getUserId());

            pstmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public List<Ticket> getAllTickets(){
        try{
            String sql = "SELECT * FROM tickets";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet result = pstmt.executeQuery();
            ArrayList<Ticket> tickets = new ArrayList<>();

            while (result.next()){
                Ticket ticket = new Ticket(result.getInt("ticket_id"), result.getDouble("amount"),
                        result.getString("description"), result.getString("status"), result.getDate("date_submitted").toLocalDate(),
                        result.getDate("date_processed").toLocalDate(), result.getInt("user_id"));

                tickets.add(ticket);
            }

            return tickets;

        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Ticket> getAllPendingTickets(){
        try{
            String sql = "SELECT * FROM tickets WHERE status = 'pending'";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet result = pstmt.executeQuery();
            ArrayList<Ticket> tickets = new ArrayList<>();

            while (result.next()){
                Ticket ticket = new Ticket(result.getInt("ticket_id"), result.getDouble("amount"),
                        result.getString("description"), result.getString("status"), result.getDate("date_submitted").toLocalDate(),
                        result.getDate("date_processed").toLocalDate(), result.getInt("user_id"));

                tickets.add(ticket);
            }

            return tickets;

        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Ticket> getAllProcessedTickets(){
        try{
            String sql = "SELECT * FROM tickets WHERE NOT status = 'pending'";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet result = pstmt.executeQuery();
            ArrayList<Ticket> tickets = new ArrayList<>();

            while (result.next()){
                Ticket ticket = new Ticket(result.getInt("ticket_id"), result.getDouble("amount"),
                        result.getString("description"), result.getString("status"), result.getDate("date_submitted").toLocalDate(),
                        result.getDate("date_processed").toLocalDate(), result.getInt("user_id"));

                tickets.add(ticket);
            }

            return tickets;

        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }


    public void update(Ticket ticket){
        try{
            String sql = "UPDATE tickets SET amount = ?, description = ?, status = ?, date_submitted = ?, " +
                    "date_processed = ?, user_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setDouble(1, ticket.getAmount());
            pstmt.setString(2, ticket.getDescription());
            pstmt.setString(3, ticket.getStatus());
            pstmt.setDate(4, Date.valueOf(ticket.getDateSubmitted()));
            pstmt.setDate(5, Date.valueOf(ticket.getDateProcessed()));
            pstmt.setInt(6, ticket.getUserId());

            pstmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void delete(Ticket ticket){
        try{
            String sql = "DELETE FROM ticket WHERE ticket_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, ticket.getTicketId());
            pstmt.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

}
