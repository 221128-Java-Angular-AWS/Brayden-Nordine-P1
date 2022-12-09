package com.revature.persistence;

import com.revature.pojo.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

            //Figure out what to do with these dates, might just use strings instead
            //pstmt.setDate(1, ticket.getDateSubmitted());
            //pstmt.setDate(1, ticket.getDateProcessed());
            pstmt.setInt(1, ticket.getUserId());
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

}
