import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Theater_Booking {
    private static String[] lastBookingIDs = new String[0];
    private static int[] lastBookingSessions = new int[0];
    private static String[][] lastBookingSeats = new String[0][0];
    private static int bookingCount = 0;
    public static void PressToShowFunction(){
        Scanner input = new Scanner(System.in);
        System.out.println("Press Enter to show Function.");
        input.nextLine();
    }
    public static void selectSeat(String[][] mornings, String[][] afternoons, String[][] evenings) {
        Scanner input = new Scanner(System.in);
        System.out.println("Select the session:");
        System.out.println("1. Morning");
        System.out.println("2. Afternoon");
        System.out.println("3. Evening");
        int session=0;
        boolean validInput;
        do {
            System.out.print("Please Choose option from 1-3 :");
            String sessionStr = input.nextLine();
            if (sessionStr.matches("^[1-3]$")){
                session = Integer.parseInt(sessionStr);
                validInput = true;
            }else {
                System.out.println("Please input the valid option from 1-3!!");
                validInput = false;
            }
        }while (!validInput);

        String[][] selectedSession;
        switch (session) {
            case 1:
                selectedSession = mornings;
                break;
            case 2:
                selectedSession = afternoons;
                break;
            case 3:
                selectedSession = evenings;
                break;
            default:
                System.out.println("Invalid session choice.");
                return;
        }

        System.out.println("Available seats in the " + (session == 1 ? "morning" : session == 2 ? "afternoon" : "evening") + " session:");
        for (int i = 0; i < selectedSession.length; i++) {
            for (int j = 0; j < selectedSession[i].length; j++) {
                System.out.print("| Seat-" + ((i*selectedSession[i].length)+j+1) + " ::" + selectedSession[i][j] + "| ");
            }
            System.out.println();
        }
        System.out.println("====================<Booking Instruction>================");
        System.out.println("For single seat booking you have to input Seat-1.");
        System.out.println("For multi seat booking you have to input Seat-1,Seat-2");
        System.out.println("==========================================================");
        String seatInput= input.next();

        String[] seatNumbers = seatInput.split(",");
        System.out.print("Enter the booking ID: ");
        String id = input.next();
        System.out.print("Are you sure you want to book these seats? (y/n): ");
        String confirmation = input.next();
        if ("y".equalsIgnoreCase(confirmation)) {
            for (String seatNumber : seatNumbers) {
                int seat = Integer.parseInt(seatNumber.trim().substring(5)); // Extract the seat number from the input
                int row = (seat - 1) / selectedSession[0].length;
                int col = (seat - 1) % selectedSession[0].length;
                if (row >= 0 && row < selectedSession.length && col >= 0 && col < selectedSession[row].length) {
                    if (selectedSession[row][col].equals("AL")) {
                        selectedSession[row][col] = "BO"; // Assuming "B" represents a booked seat
                        System.out.println("Seat successfully booked with ID " + id + " in the " + (session == 1 ? "morning" : session == 2 ? "afternoon" : "evening") + " session!");
                        lastBookingIDs = Arrays.copyOf(lastBookingIDs, bookingCount + 1);
                        lastBookingSessions = Arrays.copyOf(lastBookingSessions, bookingCount + 1);
                        lastBookingSeats = Arrays.copyOf(lastBookingSeats, bookingCount + 1);
                        lastBookingIDs[bookingCount] = id;
                        lastBookingSessions[bookingCount] = session;
                        // Concatenate the seat numbers into a single string
                        lastBookingSeats[bookingCount] = new String[]{String.join(",", seatNumbers)};
                        bookingCount++;
                    } else {
                        System.out.println("This seat is already booked in the " + (session == 1 ? "morning" : session == 2 ? "afternoon" : "evening") + " session.");
                    }
                } else {
                    System.out.println("Invalid seat number for the " + (session == 1 ? "morning" : session == 2 ? "afternoon" : "evening") + " session.");
                }
            }
        } else {
            System.out.println("Booking cancelled.");
        }
    }
    public static void checkHall(String[][] mornings, String[][] afternoons, String[][] evenings) {
        System.out.println("Morning Session:");
        checkSession(mornings);
        System.out.println("\nAfternoon Session:");
        checkSession(afternoons);
        System.out.println("\nEvening Session:");
        checkSession(evenings);
    }
    private static void checkSession(String[][] session) {
        for (int i = 0; i < session.length; i++) {
            for (int j = 0; j < session[i].length; j++) {
                System.out.print("| Seat-" + (i+1) + " ::" + session[i][j] + "| ");
            }
            System.out.println();
        }
    }
    public static void bookingHistory() {
        if(bookingCount != 0) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = LocalDate.now().format(formatter);

            System.out.println("Session\t\tID\t\tSeat\t\t\tDate");
            // Only print the first booking
            if (bookingCount > 0) {
                String session = lastBookingSessions[0] == 1 ? "morning" : lastBookingSessions[0] == 2 ? "afternoon" : "evening";
                String seats = Arrays.toString(lastBookingSeats[0]).replaceAll("\\[|\\]", "").replace(", ", ",");
                System.out.println(String.format("%-10s%-10s%-15s%s", session, lastBookingIDs[0], seats, formattedDate));
            }
        } else {
            System.out.println("=========================================");
            System.out.println("You don't have any booking yet!!");
            System.out.println("=========================================");
        }
    }
    public static void showTime(){
        System.out.println("======================================");
        System.out.println("#Daily Showtime of Theater Hall :");
        System.out.println("# 1. Morning (10:00AM - 12:30PM)");
        System.out.println("# 2. Afternoon (3:00PM - 5:30PM");
        System.out.println("# 3. Evening (7:00PM - 9:30PM)");
        System.out.println("======================================");
    }
    public static void rebootHall(String[][] mornings, String[][] afternoons, String[][] evenings) {
        Scanner input = new Scanner(System.in);
        System.out.print("Are you sure you want to reboot the hall? This will clear all current bookings. (y/n): ");
        String confirmation = input.next();
        if ("y".equalsIgnoreCase(confirmation)) {
            for (int i = 0; i < mornings.length; i++) {
                for (int j = 0; j < mornings[i].length; j++) {
                    mornings[i][j] = "AL";
                }
            }
            for (int i = 0; i < afternoons.length; i++) {
                for (int j = 0; j < afternoons[i].length; j++) {
                    afternoons[i][j] = "AL";
                }
            }
            for (int i = 0; i < evenings.length; i++) {
                for (int j = 0; j < evenings[i].length; j++) {
                    evenings[i][j] = "AL";
                }
            }
            //reset booking history
            lastBookingIDs = new String[0];
            lastBookingSessions = new int[0];
            lastBookingSeats = new String[0][0];
            bookingCount = 0;
            System.out.println("Hall has been rebooted. All current bookings have been cleared.");
        } else {
            System.out.println("Reboot cancelled.");
        }
    }
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int rows , cols=0;
        String[][] mornings = new String[0][], afternoons = new String[0][], evenings = new String[0][];
        int options =0;
        boolean isRowValid = false, isColsValid = false, isInputValid;
        do{
            System.out.println("===============|Set up Hall|===============");
            System.out.print("Enter The Number of ROW : ");
            String rowStr = input.nextLine();
            if(rowStr.matches("^[0-9]*$") && Integer.parseInt(rowStr) > 0){
                isRowValid = true;
                System.out.print("Please Enter The Cols : ");
                String colStr = input.nextLine();
                if(colStr.matches("^[0-9]*$") && Integer.parseInt(colStr) > 0){
                    isColsValid = true;
                    rows = Integer.parseInt(rowStr);
                    cols = Integer.parseInt(colStr);
                    mornings = new String[rows][cols];
                    afternoons = new String[rows][cols];
                    evenings = new String[rows][cols];
                    // Initializing the seats array with empty strings
                    for (int i = 0; i < rows; i++ ) {
                        for (int j = 0; j < cols; j++){
                            mornings[i][j] = "AL";
                            afternoons[i][j] = "AL";
                            evenings[i][j] = "AL";
                        }
                    }
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
            }
            PressToShowFunction();
        }while (!isRowValid || !isColsValid);

        do {
            System.out.println("===============|Theater Seat Booking|===============");
            System.out.println("1. Hall Booking");
            System.out.println("2. Hall Checking");
            System.out.println("3. Show Time Checking");
            System.out.println("4. Booking History");
            System.out.println("5. Rebooting Hall");
            System.out.println("6. Exit");
            do {
                System.out.print("Please Choose option from 1-6 :");
                String optionsStr = input.nextLine();
                if (optionsStr.matches("^[1-6]$")){
                    options = Integer.parseInt(optionsStr);
                    isInputValid = true;
                }else {
                    System.out.println("Please Input the valid input 1-6!!");
                    isInputValid = false;
                }
            }while (!isInputValid);
            switch (options){
                case 1 :
                    selectSeat(mornings, afternoons, evenings);
                    break;
                case 2 :
                    checkHall(mornings, afternoons, evenings);
                    break;
                case 3 :
                    showTime();
                    break;
                case 4 :
                    bookingHistory();
                    break;
                case 5 :
                    rebootHall(mornings, afternoons, evenings);
                    break;
                case 6 :
                    System.exit(0);
                    break;
            }
            PressToShowFunction();
        }while(options !=6);
    }
}
