package serversocket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author superbrucegu
 */
public class Record {
    private String name;
    private String value;
    private String type;
    
    public Record(String name, String value, String type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }
    
    public String getName() {
        return name;
    }
    
    public String getValue() {
        return value;        
    }
    
    public String getType() {
        return type;       
    }
    
    public void setName(String name) {
        this.name = name;
    }
 
    public void setValue(String value) {
        this.value = value;
    }
        
    public void setType(String type) {
        this.type = type;
    }
    
    public boolean compareTo(String name, String type) {
        return this.name.equals(name) && this.type.equals(type);
    }
     
    public boolean compareTo(String name, String value, String type) {
        return this.name.equals(name) && this.type.equals(type);
    }
    
    @Override
    public String toString() {
        return "Name: " + this.getName() + "\tValue: " + this.getValue() + " Type: " + this.getType();
    }
}
