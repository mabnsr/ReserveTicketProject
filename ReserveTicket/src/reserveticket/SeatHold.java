/*
 */
package reserveticket;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Mahmoud
 */
public class SeatHold {
    //used to hold the ID to be used for the next instance of seatHold.
    private static int holdIDRegister = 1024;
    public static int timeOutMillisconds = 30*1000;

    
    
    private String customerEmail = null;
    private final int holdID;
    private final int numberOfSeats;
    
    private boolean holdTimedOut;
    
    protected ArrayList<Seat> seats;
    

    SeatHold(int numSeats, String customerEmail)
    {
        this.customerEmail = customerEmail;
        this.numberOfSeats = numSeats;
        this.holdID = holdIDRegister;
        this.holdTimedOut = false;
        this.seats = new ArrayList<>();
        SeatHold.holdIDRegister += 1;
    }

    public String getSeatHoldCustomerEmail(){
        return this.customerEmail;
    }
    
    public int getSeatHoldID(){
        return this.holdID;
    }
    
    public void timedOut(){
        this.holdTimedOut = true;
    }
    
    public boolean isTimedOut(){
        return this.holdTimedOut;
    }
    
    public int getSeatHoldNumberOfSeats(){
        return this.numberOfSeats;
    }
    
    public String getInfo(){
        String s = String.format("   Confirmation number: %d%n   Customer email: %s%n   number of seats: %d%n", this.getSeatHoldID(), this.getSeatHoldCustomerEmail(), this.getSeatHoldNumberOfSeats());
        for (int counter = 0; counter < this.seats.size(); counter++) {
            s += String.format("      %d-%s%n", counter + 1, this.seats.get(counter).getInfo());
        }
        return s;
    }

    public String getInfoShort(){
        return String.format("%12d | %40s | %3d seats%n", this.getSeatHoldID(), this.getSeatHoldCustomerEmail(), this.getSeatHoldNumberOfSeats());
    }

    public void addSeat(Seat s) {
        this.seats.add(s);
    }

    public static boolean isValidEmailAddress(String email) {
        //TO DO: complete the implementation a validation for the email entry.
        if (! email.contains("@")){
            return false;
        }
        return true;
    }
    public static String getValidEmailAddress(Scanner scanner) {
    String email;
    boolean valid;
    System.out.println("Enter customer email:");
    email = scanner.nextLine();

    do{
        valid = isValidEmailAddress(email);

        
        if (!valid){
            System.out.print("Not a valid email, try again (type q to quit trying):");
            email = scanner.nextLine();
            if ("q".equals(email) || "Q".equals(email))
                return "";
        }
    } while(!valid);
    return email;
    }
}
