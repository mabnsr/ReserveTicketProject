/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reserveticket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Mahmoud
 */
public class RectVenue extends Venue implements TicketService{
    protected Seat [][]seats;
    protected ArrayList<Seat> sortedSeats;
    protected int numberOfRows;
    protected int numberOfSeatsInRow;
    
    protected int frontRowToStage;
    protected int fullSeatDepth;
    protected int fullSeatWidth;
    
    RectVenue(int numberOfRows, int numberOfSeatsInRow){
        this(numberOfRows, numberOfSeatsInRow, 0, 0, 0);
    }
    
    RectVenue(int numberOfRows, int numberOfSeatsInRow, int firstRowToStage, int seatDepth, int seatWidth){
        super(numberOfRows*numberOfSeatsInRow);
        if (firstRowToStage == 0)
            this.frontRowToStage = 200;
        else
            this.frontRowToStage = firstRowToStage;
            
        if (seatDepth == 0)
            this.fullSeatDepth = 50;
        else
            this.fullSeatDepth = seatDepth;
            
        if (seatWidth == 0)
            this.fullSeatWidth = 40;
        else
            this.fullSeatWidth = seatWidth;
        
        this.numberOfRows = numberOfRows;
        this.numberOfSeatsInRow = numberOfSeatsInRow;
        
        this.seats = new Seat[this.numberOfRows][this.numberOfSeatsInRow];
        this.sortedSeats = new ArrayList<>();
        setupSeats();
    }
    
    private void setupSeats(){
        for(int i = 0; i < this.numberOfRows; i++){
            for(int j = 0; j < this.numberOfSeatsInRow; j++){
                //seat coordinates with reference to the conter front side of the stage
                Seat s = new Seat(i, j);
                this.sortedSeats.add(s);
                this.seats[i][j] = s;
                float x = ((this.numberOfSeatsInRow * this.fullSeatWidth) / (float) 2) /*center of row*/
                        -   (
                                    (this.fullSeatWidth / (float) 2) /*center of the seat */
                              +     (j * this.fullSeatWidth ) /*seats away from the edge*/
                            );

                float y = this.frontRowToStage + (i * this.fullSeatDepth);
                this.seats[i][j].setRank((float) Math.sqrt((x*x)+(y*y)));
                
                //System.out.printf(this.seats[i][j].getInfo());
            }
        }
        
        Collections.sort(this.sortedSeats);
        /*
        for (int counter = 0; counter < this.sortedSeats.size(); counter++) { 		      
            System.out.printf(this.sortedSeats.get(counter).getInfoWithRank()); 		
        }*/
    }
    
    public String pickSeatsForHold(SeatHold seatHold){
        int counter = 0;
        int seatsHeldCounter = 0;
        Seat aSeat;
        String s;
        s = "The best Available Seats are: ";
        while ((counter < this.sortedSeats.size()) && (seatsHeldCounter < seatHold.getSeatHoldNumberOfSeats())){
            aSeat = this.sortedSeats.get(counter);
            if (aSeat.setOnHold()){
                seatHold.addSeat(aSeat);
                s += aSeat.getInfo();
                seatsHeldCounter++;
            }
            counter++;
        }
        return s;
    }
}
