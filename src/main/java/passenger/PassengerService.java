package passenger;

import flight.FlightService;
import util.IdGenerator;
import util.Interface;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public record PassengerService(PassengerDao passengerDao, FlightService flightService, IdGenerator idGenerator) {

    public Passenger getById(String id) {
        for (Passenger p : passengerDao.getAllPassengers()) {
            if (id.trim().equalsIgnoreCase(p.getId())) {
                return p;
            }
        }
        return null;
    }

    public List<Passenger> filterByName(String name) { //TODO use index numbers to ref allPassengers in a list (return Int)
        List<Passenger> allPassengers = passengerDao.getAllPassengers();
        List<Passenger> matched = new ArrayList<>();
        if (allPassengers.size() > 0) {
            for (Passenger p : allPassengers) {
                if (p.getName().trim().toLowerCase().contains(name.trim().toLowerCase())) { // watch for trims
                    matched.add(p);
                }
            }
        }
        return matched;
    }

    public Passenger promptEnterId() { //TODO promptGetPassengerById return passenger and pass into goToMenu...()
        System.out.println("Please enter your Id:");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        Passenger p = getById(input);
        if (p == null) {
            System.out.println("No Users found matching Id");
        }
        return p;
    }

    public Passenger promptEnterName() { //TODO promptGetPassengerByName return passenger and pass into goToMenu...()
        System.out.println("Please enter your name:");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        List<Passenger> filteredList = filterByName(input);
        if (filteredList.size() == 0) {
            System.out.println("No Users found");
            return null;
        } else {
            String[] options = new String[filteredList.size() + 1];
            for (int i = 0; i < filteredList.size(); i++) {
                options[i] = formatPassenger(filteredList.get(i));
            }
            options[filteredList.size()] = "Back";
            int option = Interface.getOption("Choose a passenger from the list below:", options);
            if (option <= filteredList.size()) {
                return filteredList.get(option - 1);
            }
            return null;
        }
    }

    public void promptCreateNewUser() {

        List<Passenger> allPassengers = passengerDao.getAllPassengers();
        String id = idGenerator.randomIdGenerator(allPassengers);

        System.out.println("Please enter your full name:");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine().trim();

        System.out.println("Please enter your email:");
        String email = scanner.nextLine().trim();

        System.out.println("Please enter your phone number");
        String phoneNum = scanner.nextLine().trim();

        System.out.println("Please enter your passport number:");
        String passportNum = scanner.nextLine().trim();

        Passenger passenger = new Passenger(id, name, email, phoneNum, passportNum);

        allPassengers.add(passenger);
        passengerDao.updateAllPassengers(allPassengers);

        System.out.println(name + " created! Your ID is " + id);
    }

    public String formatPassenger(Passenger p) {
        return p.getId() + " | " + p.getName() + " | " + p.getEmail() + " | " + p.getPassport();
    }
}

