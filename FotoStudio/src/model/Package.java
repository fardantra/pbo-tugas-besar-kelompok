/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Fardan
 */
public class Package {
    private int packageId;
    private int studioId;
    private String name;
    private double price;
    private int minPerson;
    private int maxPerson;
    private int duration;
    
    public Package() {
    }
    
    public Package(int packageId, int studioId, String name, double price, int minPerson, int maxPerson, int duration) {
        this.packageId = packageId;
        this.studioId = studioId;
        this.name = name;
        this.price = price;
        this.minPerson = minPerson;
        this.maxPerson = maxPerson;
        this.duration = duration;
    }
    
    public int getPackageId() { 
        return packageId; 
    }
    
    public void setPackageId(int packageId) { 
        this.packageId = packageId; 
    }

    public int getStudioId() { 
        return studioId; 
    }
    
    public void setStudioId(int studioId) { 
        this.studioId = studioId; 
    }

    public String getName() { 
        return name; 
    }
    
    public void setName(String name) { 
        this.name = name; 
    }

    public double getPrice() { 
        return price; 
    }
    
    public void setPrice(double price) { 
        this.price = price; 
    }

    public int getMinPerson() { 
        return minPerson; 
    }
    
    public void setMinPerson(int minPerson) { 
        this.minPerson = minPerson; 
    }

    public int getMaxPerson() { 
        return maxPerson; 
    }
    
    public void setMaxPerson(int maxPerson) { 
        this.maxPerson = maxPerson; 
    }

    public int getDuration() { 
        return duration; 
    }
    
    public void setDuration(int duration) { 
        this.duration = duration; 
    }
}
