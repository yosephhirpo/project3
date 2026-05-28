package hotel;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Room> hotelRooms = new ArrayList<>();

        hotelRooms.add(new StandardRoom(101));
        hotelRooms.add(new StandardRoom(102));
        hotelRooms.add(new SuiteRoom(201));
        hotelRooms.add(new SuiteRoom(202));

        System.out.println(" Welcome to the Simple Hotel Booking System ");

        while (true) {
            System.out.println("\n1. View All Rooms");
            System.out.println("2. Book a Room");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
            catch (InputMismatchException e) {
    System.out.println("Invalid input! Please enter a valid whole number.");
            }
    

                if (choice == 1) {
                    System.out.println("\n--- Current Room Status ---");
                    for (Room r : hotelRooms) {
                        String status = r.isOccupied() ? "Occupied by " + r.getGuestName() : "Available";
                        System.out.println("Room " + r.getRoomNumber() + ": " + status);
                    }

                } else if (choice == 2) {
                    System.out.print("Enter Room Number to book (101, 102, 201, 202): ");
                    int chosenNumber = scanner.nextInt();
                    scanner.nextLine();

                    Room selectedRoom = null;
                    for (Room r : hotelRooms) {
                        if (r.getRoomNumber() == chosenNumber) {
                            selectedRoom = r;
                            break;
                        }
                    }

                    if (selectedRoom == null) {
                        throw new IllegalArgumentException("Error: That room number does not exist!");
                    }
                    if (selectedRoom.isOccupied()) {
                        throw new IllegalStateException("Error: That room is already booked!");
                    }

                    System.out.print("Enter Guest Name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter Number of Nights: ");
                    int nights = scanner.nextInt();
                    scanner.nextLine();

                    if (nights <= 0) {
                        throw new IllegalArgumentException("Error: Nights must be at least 1.");
                    }

                    selectedRoom.checkIn(name);

                    double finalPrice = selectedRoom.calculatePrice(nights);

                    System.out.println("Success! Room " + chosenNumber + " booked for " + name);
                    System.out.println("Total Cost: $" + finalPrice);

                } else if (choice == 3) {
                    System.out.println("Thank you for using the Hotel Booking System. Goodbye!");
                    break;
                } else {
                    System.out.println("Invalid option. Please choose 1, 2, or 3.");
                }

            }
            catch (InputMismatchException e) {
                System.out.println("Invalid input type! Please enter numbers only.");
                scanner.nextLine();
            }
            catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println(e.getMessage());
            }
        }

        scanner.close();
    }
}

abstract class Room {

    private final int roomNumber;
    private String guestName;

    protected boolean isOccupied;

    public Room(int roomNumber) {
        this.roomNumber = roomNumber;
        this.guestName = "Empty";
        this.isOccupied = false;
    }

    public abstract double calculatePrice(int nights);

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void checkIn(String guestName) {
        this.guestName = guestName;
        this.isOccupied = true;
    }
}

class StandardRoom extends Room {

    public StandardRoom(int roomNumber) {
        super(roomNumber);
    }

    @Override
    public double calculatePrice(int nights) {
        return nights * 50.0;
    }
}

class SuiteRoom extends Room {

    public SuiteRoom(int roomNumber) {
        super(roomNumber);
    }

    @Override
    public double calculatePrice(int nights) {
        return (nights * 120.0) + 30.0;
    }
}
