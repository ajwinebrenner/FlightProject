import util.Tools;
import util.UI;
import flight.FlightDao;
import flight.FlightFileDao;
import flight.FlightService;
import passenger.Passenger;
import passenger.PassengerDao;
import passenger.PassengerFileDao;
import passenger.PassengerService;

public class Main {

    static FlightDao flightDao = new FlightFileDao("FlightDB.txt");
    static PassengerDao passengerDao = new PassengerFileDao("PassengerDB.txt");
    static FlightService flightService = new FlightService(flightDao);
    static PassengerService passengerService = new PassengerService(passengerDao);


    public static void main(String[] args) {

        System.out.println("Welcome to the Flight Management CLI!\n");

        String[] options = {
                "Manage Flights",
                "Manage Passengers",
                "Quit the program"
        };

        boolean back;
        do {
            System.out.println("----- Main Menu -----");

            int option = UI.getOption("Enter a number:", options);
            back = false;
            // Switch statements here
            switch (option) {
                case 1 -> manageFlights();
                case 2 -> managePassengers();
                case 3 -> back = true;
            }
        } while (!back);
        System.out.println("Thanks for using our management system!");
        System.exit(0);
    }

    private static void manageFlights() {
        String[] options = {
                "Add a flight",
                "Display all flights",
                "Display fully-booked flights",
                "Cancel flight",
                "Back"
        };

        boolean back;
        do {
            int option = UI.getOption("----- Manage Flights -----", options);
            back = false;
            switch (option) {
                case 1 -> flightService.promptAddFlight();
                case 2 -> flightService.displayFlights(
                        "All flights:",
                        flightDao.getAllFlights()
                );
                case 3 -> flightService.displayFlights(
                        "Fully booked flights:",
                        flightService.getFullyBooked(flightDao.getAllFlights())
                );
                case 4 -> flightService.promptCancelFlight();
                case 5 -> back = true;
            }
        } while (!back);
    }

    private static void managePassengers() {
        String[] options = {
                "Create a new user",
                "Manage specific user by Id",
                "Search for specific user", //TODO change to go to search user options (any field)
                "Back"
        };

        boolean back;
        do {
            int option = UI.getOption("----- Manage Passengers -----", options);
            back = false;
            switch (option) {
                case 1 -> passengerService.promptCreateNewUser();
                case 2 -> ManageUser(passengerService.promptEnterId());
                case 3 -> ManageUser(passengerService.promptEnterName());
                case 4 -> back = true;
            }
        } while (!back);
    }

    public static void ManageUser(Passenger p) {
        if (p == null) return;

        String[] options = { // TODO add cancel a booking
                "Book a flight for " + p.getName(),
                "Display booked flights for " + p.getName(),
                "Back"
        };

        boolean back;
        do {
            int option = UI.getOption("----- Manage User: " + p.getId() + " -----", options);
            back = false;
            switch (option) {
                case 1 -> flightService.promptBookFlight(p);
                case 2 -> flightService.displayFlights(
                    "Flights for " + p.getName() + ":",
                    flightService.getPassengerFlights(p)
                );
                case 3 -> back = true;
            }
        } while (!back);
    }
}








