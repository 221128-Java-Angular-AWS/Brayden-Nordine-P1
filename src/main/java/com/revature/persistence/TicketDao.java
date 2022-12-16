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
            pstmt.setString(2, ticket.getDescription());
            pstmt.setString(3, ticket.getStatus());
            pstmt.setTimestamp(4, Timestamp.valueOf(ticket.getDateSubmitted()));
            pstmt.setTimestamp(5, ticket.getDateProcessed() != null ? Timestamp.valueOf(ticket.getDateProcessed()) : null);
            pstmt.setInt(6, ticket.getUserId());

            pstmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public String getTicketStatus(int ticketId){
        try{
            String sql = "SELECT status FROM tickets WHERE ticket_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, ticketId);
            ResultSet result = pstmt.executeQuery();

            if(result.next()){
                return result.getString("status");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return "";
    }

    public List<Ticket> getAllTickets(){
        try{
            String sql = "SELECT * FROM tickets ORDER BY date_submitted";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet result = pstmt.executeQuery();
            ArrayList<Ticket> tickets = new ArrayList<>();

            while (result.next()){
                Ticket ticket = new Ticket(result.getInt("ticket_id"), result.getDouble("amount"),
                        result.getString("description"), result.getString("status"), result.getTimestamp("date_submitted").toLocalDateTime(),
                        result.getTimestamp("date_processed") != null? result.getTimestamp("date_processed").toLocalDateTime() : null,
                        result.getInt("user_id"));

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
            String sql = "SELECT * FROM tickets WHERE status = 'pending' ORDER BY date_submitted";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet result = pstmt.executeQuery();
            ArrayList<Ticket> tickets = new ArrayList<>();

            while (result.next()){
                Ticket ticket = new Ticket(result.getInt("ticket_id"), result.getDouble("amount"),
                        result.getString("description"), result.getString("status"), result.getTimestamp("date_submitted").toLocalDateTime(),
                        result.getTimestamp("date_processed") != null? result.getTimestamp("date_processed").toLocalDateTime() : null,
                        result.getInt("user_id"));

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
            String sql = "SELECT * FROM tickets WHERE NOT status = 'pending' ORDER BY date_submitted";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet result = pstmt.executeQuery();
            ArrayList<Ticket> tickets = new ArrayList<>();

            while (result.next()){
                Ticket ticket = new Ticket(result.getInt("ticket_id"), result.getDouble("amount"),
                        result.getString("description"), result.getString("status"), result.getTimestamp("date_submitted").toLocalDateTime(),
                        result.getTimestamp("date_processed").toLocalDateTime(), result.getInt("user_id"));

                tickets.add(ticket);
            }

            return tickets;

        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Ticket> getAllTicketsForUser(int userId){
        try{
            String sql = "SELECT * FROM tickets WHERE user_id = ? ORDER BY date_submitted";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, userId);
            ResultSet result = pstmt.executeQuery();
            ArrayList<Ticket> tickets = new ArrayList<>();

            while (result.next()){
                Ticket ticket = new Ticket(result.getInt("ticket_id"), result.getDouble("amount"),
                        result.getString("description"), result.getString("status"), result.getTimestamp("date_submitted").toLocalDateTime(),
                        result.getTimestamp("date_processed") != null? result.getTimestamp("date_processed").toLocalDateTime() : null,
                        result.getInt("user_id"));

                tickets.add(ticket);
            }

            return tickets;

        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Ticket> getAllPendingTicketsForUser(int userId){
        try{
            String sql = "SELECT * FROM tickets WHERE user_id = ? AND status = 'pending' ORDER BY date_submitted";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, userId);
            ResultSet result = pstmt.executeQuery();
            ArrayList<Ticket> tickets = new ArrayList<>();

            while (result.next()){
                Ticket ticket = new Ticket(result.getInt("ticket_id"), result.getDouble("amount"),
                        result.getString("description"), result.getString("status"), result.getTimestamp("date_submitted").toLocalDateTime(),
                        result.getTimestamp("date_processed") != null? result.getTimestamp("date_processed").toLocalDateTime() : null,
                        result.getInt("user_id"));

                tickets.add(ticket);
            }

            return tickets;

        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Ticket> getAllApprovedTicketsForUser(int userId){
        try{
            String sql = "SELECT * FROM tickets WHERE user_id = ? AND status = 'approved' ORDER BY date_submitted";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, userId);
            ResultSet result = pstmt.executeQuery();
            ArrayList<Ticket> tickets = new ArrayList<>();

            while (result.next()){
                Ticket ticket = new Ticket(result.getInt("ticket_id"), result.getDouble("amount"),
                        result.getString("description"), result.getString("status"), result.getTimestamp("date_submitted").toLocalDateTime(),
                        result.getTimestamp("date_processed").toLocalDateTime(), result.getInt("user_id"));

                tickets.add(ticket);
            }

            return tickets;

        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Ticket> getAllRejectedTicketsForUser(int userId){
        try{
            String sql = "SELECT * FROM tickets WHERE user_id = ? AND status = 'rejected' ORDER BY date_submitted";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, userId);
            ResultSet result = pstmt.executeQuery();
            ArrayList<Ticket> tickets = new ArrayList<>();

            while (result.next()){
                Ticket ticket = new Ticket(result.getInt("ticket_id"), result.getDouble("amount"),
                        result.getString("description"), result.getString("status"), result.getTimestamp("date_submitted").toLocalDateTime(),
                        result.getTimestamp("date_processed").toLocalDateTime(), result.getInt("user_id"));

                tickets.add(ticket);
            }

            return tickets;

        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Ticket> getAllProcessedTicketsForUser(int userId){
        try{
            String sql = "SELECT * FROM tickets WHERE user_id = ? AND NOT status = 'pending' ORDER BY date_submitted";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, userId);
            ResultSet result = pstmt.executeQuery();
            ArrayList<Ticket> tickets = new ArrayList<>();

            while (result.next()){
                Ticket ticket = new Ticket(result.getInt("ticket_id"), result.getDouble("amount"),
                        result.getString("description"), result.getString("status"), result.getTimestamp("date_submitted").toLocalDateTime(),
                        result.getTimestamp("date_processed").toLocalDateTime(), result.getInt("user_id"));

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
                    "date_processed = ?, user_id = ? WHERE ticket_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setDouble(1, ticket.getAmount());
            pstmt.setString(2, ticket.getDescription());
            pstmt.setString(3, ticket.getStatus());
            pstmt.setTimestamp(4, Timestamp.valueOf(ticket.getDateSubmitted()));
            pstmt.setTimestamp(5, ticket.getDateProcessed() != null ? Timestamp.valueOf(ticket.getDateProcessed()) : null);
            pstmt.setInt(6, ticket.getUserId());
            pstmt.setInt(7, ticket.getTicketId());

            pstmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void delete(int ticketId){
        try{
            String sql = "DELETE FROM ticket WHERE ticket_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, ticketId);
            pstmt.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

}
