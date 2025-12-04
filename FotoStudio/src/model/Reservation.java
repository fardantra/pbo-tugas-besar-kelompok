/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Fardan
 */

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class Reservation {
    private int reservationId;
    private int userId;
    private int packageId;
    private Date reservationDate;
    private Time reservationTime;
    private String statusPayment;
    private double totalPrice;
    private boolean isDone;
    private Timestamp createdAt;  
    
    public Reservation() {
    }
    
    public int getReservationId() { 
        return reservationId; 
    }
    
    public void setReservationId(int reservationId) { 
        this.reservationId = reservationId; 
    }

    public int getUserId() { 
        return userId; 
    }
    
    public void setUserId(int userId) { 
        this.userId = userId; 
    }

    public int getPackageId() { 
        return packageId; 
    }
    
    public void setPackageId(int packageId) { 
        this.packageId = packageId; 
    }

    public Date getReservationDate() { 
        return reservationDate; 
    }
    
    public void setReservationDate(Date reservationDate) { 
        this.reservationDate = reservationDate; 
    }

    public Time getReservationTime() { 
        return reservationTime; 
    }
    
    public void setReservationTime(Time reservationTime) { 
        this.reservationTime = reservationTime; 
    }

    public String getStatusPayment() { 
        return statusPayment; 
    }
    
    public void setStatusPayment(String statusPayment) { 
        this.statusPayment = statusPayment; 
    }

    public double getTotalPrice() { 
        return totalPrice; 
    }
    
    public void setTotalPrice(double totalPrice) { 
        this.totalPrice = totalPrice; 
    }

    public boolean isDone() { 
        return isDone; 
    }
    
    public void setDone(boolean done) { 
        isDone = done; 
    }

    public Timestamp getCreatedAt() { 
        return createdAt; 
    }
    
    public void setCreatedAt(Timestamp createdAt) { 
        this.createdAt = createdAt; 
    }
}