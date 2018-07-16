/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reserveticket;

/**
 *
 * @author Mahmoud
 */
public class Seat implements Comparable<Seat> {
    protected byte availability;
    protected Float rank;
    protected int i;
    protected int j;
    /*
    The availablity could have one of three options:
    0: Available
    1: on Hold
    2: Reserved
    */
    
    
    Seat(int i, int j){
        this.availability = 0;
        this.rank = Float.parseFloat("0");
        this.i = i;
        this.j = j;
    }
    
    public boolean isAvailable(){
        return (this.availability == 0);
    }
    
    public boolean setOnHold(){
        if (this.availability == 0){
            this.availability = 1;
            return true;
        }
        return false;
    }
    
    public boolean setReserved(){
        //Checking if the seat is on hold for the same person is not done yet.
        if (this.availability != 2){
            this.availability = 2;
            return true;
        }
        return false;
    }
    
    public void setRank(float rank){
        this.rank = rank;
    }
    
    public float getRank(){
        return this.rank;
    }

    public String getInfo(){
        return String.format("Seat: %d, %d%n", this.i + 1, this.j + 1);//Add one because the index is zero based
    }
    
    public String getInfoWithRank(){
        return String.format("Seat: %d, %d -> %f", this.i + 1, this.j + 1, this.getRank());//Add one because the index is zero based
    }
    
    @Override
    public int compareTo(Seat another) {
        // price fields should be Float instead of float
        return this.rank.compareTo(another.rank);
    }

}
