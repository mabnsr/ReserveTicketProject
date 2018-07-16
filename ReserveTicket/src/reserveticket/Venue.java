/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reserveticket;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author Mahmoud
 */
public class Venue implements TicketService{
    protected int seatCount;
    protected LinkedList<SeatHold> seatsHeld;
    protected LinkedList<SeatHold> seatsReserved;
    
    Venue(int numberOfSeats){
        this.seatsHeld = new LinkedList<>();
        this.seatsReserved = new LinkedList<>();
        
        this.setCapacity(numberOfSeats);
    }
    
    private void setCapacity(int numberOfSeats){
        if (numberOfSeats > 0)
            this.seatCount = numberOfSeats;
        else
            this.seatCount = 0;
    }
    
    public int getCapacity(){
        return this.seatCount;
    }
    
    public String availabilityStatement(){
        return String.format("At the moment: %d seats are available out of the total capacity of %d seats%n", this.numSeatsAvailable(), this.getCapacity());
    }
    public void setHoldTimerForTimeout(SeatHold targetHold){

        new java.util.Timer().schedule( 
        new java.util.TimerTask() {
            @Override
            public void run() {
                targetHold.timedOut();
                //System.out.println("hold Timedout:" + targetHold.getInfoShort());
            }
        }, 
        SeatHold.timeOutMillisconds 
        );
    }
    
    protected int numSeatsHeldAndCleanup(){
        Iterator it;
        SeatHold aHold;
        int holdCount = 0;//used to count number of seats held
        
        it = this.seatsHeld.iterator();
        while(it.hasNext()){
            aHold = (SeatHold) it.next();
            if (aHold.isTimedOut()){
                System.out.println("Deleting hold:" + aHold.getInfoShort());
                it.remove();
            }
            else{
                holdCount += aHold.getSeatHoldNumberOfSeats();
            }
        }
        return holdCount;
    }
    
    public int numSeatsReserved(){
        Iterator it;
        SeatHold aHold;
        int reservationCount = 0;
        
        it = this.seatsReserved.iterator();
        while(it.hasNext()){
            aHold = (SeatHold) it.next();
            reservationCount += aHold.getSeatHoldNumberOfSeats();
        }
        return reservationCount;
    }
    public void getInfo(){
        this.availabilityStatement();
        
        Iterator it;
        SeatHold aHold;
        int holdNumber = 0;
        
        it = this.seatsHeld.iterator();
        while(it.hasNext()){
            holdNumber += 1;
            aHold = (SeatHold) it.next();
            System.out.printf("Hold number %d%n%s", holdNumber, aHold.getInfo());
        }
        it = this.seatsReserved.iterator();
        while(it.hasNext()){
            holdNumber += 1;
            aHold = (SeatHold) it.next();
            System.out.printf("Reservation number %d%n%s", holdNumber, aHold.getInfo());
        }
    }
    
    
    public String pickSeatsForHold(SeatHold seatHold){
        return "Select seats upon entry";
    }
    
    @Override
    public int numSeatsAvailable(){
        return this.seatCount - (this.numSeatsHeldAndCleanup() + this.numSeatsReserved());
    }

    
    @Override
    public SeatHold findAndHoldSeats(int numSeats, String customerEmail){
        if ((numSeats > 0) && (numSeats <= this.numSeatsAvailable())){
            SeatHold seatHold = new SeatHold(numSeats, customerEmail);
            System.out.println(pickSeatsForHold(seatHold));
            //Use addFirst to have the newly added items appear on top of the list.
            this.seatsHeld.addFirst(seatHold);
            this.setHoldTimerForTimeout(seatHold);
            return seatHold;
        }else{
            System.out.println("Unfortunately, Requested seats are not availabe now!");
        }
        return null;
    }
    
    @Override
    //reserve held seats in a vanue
    public boolean reserveSeats(SeatHold currentHold){
        this.numSeatsHeldAndCleanup();
        Iterator it;
        SeatHold aHold, selectedHold = null;
        String currentStr = "";
        if (this.seatsHeld.size() > 0){
            it = this.seatsHeld.iterator();
            while(it.hasNext()){
                aHold = (SeatHold) it.next();
                String current = "";
                if (aHold == currentHold){
                    current = " (last hold)";
                    currentStr = "(0 to reserve last hold)";
                }
                System.out.println(aHold.getInfoShort() + current);
            }
            System.out.print("Enter the ID for a hold to reserve " + currentStr + " :");

            Scanner scanner = new Scanner(System.in);
            int holdID = scanner.nextInt();
            scanner.nextLine();//Workaround because nextInt does not consume new line symbol

            if (holdID == 0){
                selectedHold = currentHold;
            }else{
                it = this.seatsHeld.iterator();
                while(it.hasNext()){
                    aHold = (SeatHold) it.next();
                    if (aHold.getSeatHoldID() == holdID){
                        selectedHold = aHold;
                    }
                }
                System.out.println("A hold not found based on the entered ID!");
            }
            if (selectedHold != null){
                this.seatsHeld.remove(selectedHold);
                this.seatsReserved.add(selectedHold);
                System.out.println("Reservation completed!");
            }else{
                System.out.println("Reservation not completed!");
            }
        }else{
            System.out.println("There are no holds availabe!");
        }
        return true;
    }
}
