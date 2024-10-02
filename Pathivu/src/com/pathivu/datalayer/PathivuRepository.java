package com.pathivu.datalayer;

import com.pathivu.dto.*;

import java.util.*;

public class PathivuRepository {
    private static PathivuRepository pathivuRepository;

    private Set<User> users=new HashSet<>(){{
        add(new User("111","Ajay",20, Gender.MALE));
        add(new User("222","Bejoy",25,Gender.MALE));
        add(new User("333","Falin",18,Gender.FEMALE));
        add(new User("444","Malli",23,Gender.FEMALE));
    }};

    private Map<String,User> userPasswordMap=new HashMap<>(){{
        for(User user:users){
            put(user.getUserID(), user);
        }
    }};

    private Set<Bus> buses=new HashSet<>(){{
        //make it simple keeping less number of seats
        add(new Bus("B1","Sundhara Travels", BusType.SLEEPER,new BusDriver("Ram","D1",30),"9:00","16:00","Chennai","Tiruppur",4,"20-3-24"));
        add(new Bus("B2","Madhura Travels", BusType.SEMI_SLEEPER,new BusDriver("Vetri","D2",29),"12:00","20:00","Tiruppur","Chennai",2,"20-3-24"));
        add(new Bus("B3","Anil Travels", BusType.SLEEPER,new BusDriver("Xavier","D3",32),"8:00","10:00","Coimbatore","Tiruppur",1,"20-3-24"));
        add(new Bus("B4","Ambani Travels", BusType.DELUXE,new BusDriver("Karim Bhai","D4",35),"12:00","14:00","Chennai","Kancheepuram",4,"20-3-24"));
        add(new Bus("B6","Baba Travels", BusType.SLEEPER,new BusDriver("Ravi","D5",37),"9:00","16:00","Chennai","Tiruppur",6,"20-3-24"));
    }};

    private PathivuRepository(){

    }

    public static PathivuRepository getInstance(){
        if(pathivuRepository==null){
            pathivuRepository=new PathivuRepository();
        }
        return pathivuRepository;
    }


    public User userValidation(String userID,String userPassword) {
        if(userPasswordMap.containsKey(userID)){
            if(userPasswordMap.get(userID).getUserPassword().equals(userPassword)){
                return userPasswordMap.get(userID);
            }
        }

        return null;
    }

    public List<Bus> getBusList() {
        return buses.stream().toList();
    }

    public List<Bus> getAvailableBusesFromDB(String boardingPlace, String droppingPlace, Date date) {
        List<Bus> availableBuses=new ArrayList<>();
        for(Bus bus:buses){
            if(bus.getBoardingPlace().equalsIgnoreCase(boardingPlace) && bus.getDroppingPlace().equalsIgnoreCase(droppingPlace) && bus.getDate().equals(date)){
                availableBuses.add(bus);
            }
        }
        return availableBuses;
    }

    public boolean checkAvailableSeatsInBusByID(String busID, int neededSeats) {
        for(Bus bus:buses){
            if(bus.getBusID().equals(busID)){
                if(neededSeats<=bus.getSeats()){
                    return true;
                }else{
                    return false;
                }
            }
        }
        return false;
    }

    public void bookTicketOfUser(String busID, int bookingSeats, User user) {
        for(Bus bus:buses){
            if(bus.getBusID().equals(busID)){
                bus.setSeats(bus.getSeats()-bookingSeats);
                Ticket ticket=new Ticket(user,bus,bus.getDriver(),bus.getStartTime(),bus.getEndTime(),bus.getBoardingPlace(),bus.getDroppingPlace(),bookingSeats,bus.getDate());
                user.getTicketsBookedByUser().add(ticket);
            }
        }
    }

    public User addUserToDB(String userPassword, String userName, int userAge, Gender userGender) {
        User newUser=new User(userPassword,userName,userAge,userGender);
        users.add(newUser);
        userPasswordMap.put(newUser.getUserID(),newUser);
        return newUser;
    }
}
