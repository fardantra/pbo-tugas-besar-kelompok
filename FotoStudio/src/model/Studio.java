/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Fardan
 */
public class Studio {
    private int studioId;
    private int capacity;
    private boolean status;
    
    public Studio() {
    }

    public Studio(int studioId, int capacity, boolean status) {
        this.studioId = studioId;
        this.capacity = capacity;
        this.status = status;
    }
    
    public int getStudioId() { 
        return studioId; 
    }
    
    public void setStudioId(int studioId) { 
        this.studioId = studioId; 
    }

    public int getCapacity() { 
        return capacity; 
    }
    
    public void setCapacity(int capacity) { 
        this.capacity = capacity; 
    }

    public boolean isStatus() { 
        return status; 
    }
    
    public void setStatus(boolean status) { 
        this.status = status; 
    }
}
