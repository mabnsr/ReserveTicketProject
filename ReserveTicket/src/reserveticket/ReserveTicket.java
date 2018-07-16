/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reserveticket;

import java.util.Scanner;

/**
 *
 * @author Mahmoud Abunasser
 */
public class ReserveTicket {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int venueSize;
        Venue venue;
        Scanner scanner = new Scanner(System.in);
        int holdSize;
        System.out.println("please, enter the number of seats in the venue");
        System.out.println(" Enter 0 specify the number of rows and number of seats in each row");
        venueSize = scanner.nextInt();
        scanner.nextLine();//Workaround because nextInt does not consume new line symbol
        
        
        if (venueSize > 0){
            venue = new Venue(venueSize);
        }else if (venueSize == 0){
            int rowCount, seatsInRow;
            System.out.println(" Enter the number of rows in the venue");
            rowCount = scanner.nextInt();
            scanner.nextLine();//Workaround because nextInt does not consume new line symbol

            System.out.println(" Enter the number of seats in each row");
            seatsInRow = scanner.nextInt();
            scanner.nextLine();//Workaround because nextInt does not consume new line symbol

            if ((rowCount <= 0) || (seatsInRow <= 0)){
                System.out.println("A venue dimension entery not valid!");
                return;
            }
                
            venue = new RectVenue(rowCount,seatsInRow);
        }else{
            System.out.println("A venue size entery not valid!");
            return;
        }
        
        SeatHold myLastHold;
        myLastHold = null;


        int selection;
        do {
            System.out.println("please select menu item");
            System.out.println("1-Availability check, 2-Hold seats, 3 - Reserve a hold, 4 - Show venue status, 5 - Exit");
            
            
            selection = scanner.nextInt();
            scanner.nextLine();//Workaround because nextInt does not consume new line symbol
            switch (selection) {
                case 1:
                    System.out.printf(venue.availabilityStatement());
                    break;
                case 2:
                    if (venue.numSeatsAvailable() == 0)
                        System.out.println("The venue is fully booked!");
                    else{
                        System.out.println("How many seats would you like to hold?");
                        holdSize = scanner.nextInt();
                        scanner.nextLine();//Workaround because nextInt does not consume new line symbol
                        if ((holdSize > 0) && (holdSize <= venue.numSeatsAvailable())){
                            String customerEmail = SeatHold.getValidEmailAddress(scanner);
                                if (!"".equals(customerEmail)){
                                    SeatHold aHold;
                                    aHold = venue.findAndHoldSeats(holdSize, customerEmail);

                                    if (aHold != null){
                                        myLastHold = aHold;

                                    }

                            }
                        }
                        else if (holdSize <= 0) {
                            System.out.println("Not a valid Entry, number of seats must be more than zero!");
                        }else{
                            System.out.println("Not a valid Entry, Requested seats are not availabe now!");
                            System.out.printf(venue.availabilityStatement());
                        }
                    }
                    break;
                case 3:
                    venue.reserveSeats(myLastHold);
                    break;
                case 4:
                    venue.getInfo();
                    break;
            }
        } while (selection != 5);//Exit
        System.out.println("Thank you for using ReserveTicket!");
    }
}
