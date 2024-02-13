import java.util.*;

abstract class Vehicle {
    private String make;
    private String model;
    private int year;
    private int rentalPrice;

    public Vehicle(String make, String model, int year, int rentalPrice) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.rentalPrice = rentalPrice;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public int getRentalPrice() {
        return rentalPrice;
    }

}

class RentalAgency {
    public static Map<String, Car> vehicles = new HashMap<>();
    public Map<String, Customer> mp = new HashMap<>();

    public void account(Customer c) {
        mp.put(c.getemail(), c);
    }

    // Display All Available Vehicles
    public boolean displayAll() {
        if (vehicles.isEmpty()) {
            System.out.println("No Vehicle Available.............");
            return false;
        } else if (!vehicles.isEmpty()) {
            System.out.println("Available Vehicles are =>");
            for (Map.Entry<String, Car> entry : vehicles.entrySet()) {
                System.out
                        .println("Model =  " + entry.getKey() + ", Name = " + entry.getValue().getMake() + ", Price = "
                                + entry.getValue().getRentalPrice() + ", Seats = " + entry.getValue().getNumSeats());
            }
        }
        return true;
    }

    // ADD NEW VEHICLE
    public void addVehicle(Car car) {
        vehicles.put(car.getModel(), car);
        System.out.println("vehicle Added Successfulyy......");
    }

    // RENT VEHICLE TO CUSTOMER
    public void rentVehicle(String email, String model) {
        if (!mp.containsKey(email)) {
            System.out.println("First create a account...........");
        } else {
            if (!vehicles.containsKey(model)) {
                System.out.println("Vehicle is not available..........");
            } else {
                Car car = vehicles.get(model);
                mp.get(email).rentedVehicles.add(car);
                vehicles.remove(model);
                System.out.println("Successfully Rented............");

            }

        }

    }

    // RETURN A VEHICLE AND ADD IT TO AVAILABLE VEHICLES
    public void returnVehicle(String email, String model) {
        if (!mp.containsKey(email)) {
            System.out.println("First create a account........");
        } else {
            Customer c = mp.get(email);
            ArrayList<Car> temp = c.rentCar();
            for (int i = 0; i < temp.size(); i++) {
                String s = temp.get(i).getModel();
                if (s.equals(model)) {
                    Car car = temp.get(i);
                    c.rentedVehicles.remove(car);
                    vehicles.put(model, car);
                }
            }
            System.out.println("Successfully Returned.....");

        }
    }

    // CALCULATE RENT PRICE OF VEHICLE ACCORDING TO DAYS
    public void rentprice(String model, int days) {
        if (!vehicles.containsKey(model)) {
            System.out.println("Vehicle is not available.......");
        } else {
            System.out.println("Total Rent-Price is " + (vehicles.get(model).getRentalPrice()) * days);
        }

    }

    public void showrent(String id) {
        if (!mp.containsKey(id)) {
            System.out.println("First create a account........");
        } else {
            Customer c = mp.get(id);
            ArrayList<Car> temp = c.rentCar();
            if (temp.size() == 0) {
                System.out.println("You have not rented vehicle yet.....");
            } else {
                System.out.println("List of rented vehicles are =>");
                for (int i = 0; i < temp.size(); i++) {
                    System.out.println("Name = " + temp.get(i).getMake() + " , Model = " + temp.get(i).getMake()
                            + " , Price = " + temp.get(i).getRentalPrice() + " , Seats = " + temp.get(i).getNumSeats());
                }
            }

        }

    }
}

class Car extends Vehicle {
    private int numSeats;
    String fuelType;

    public Car(String make, String model, int year, int rentalPrice, int numSeats, String fuelType) {
        super(make, model, year, rentalPrice);
        this.fuelType = fuelType;
        this.numSeats = numSeats;

    }

    public int getNumSeats() {
        return numSeats;
    }

    public String getFuelType() {
        return fuelType;
    }

}

class Customer {
    String name;
    String email;
    public ArrayList<Car> rentedVehicles;

    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
        this.rentedVehicles = new ArrayList<>();

    }

    // RETURN RENTED VEHICLES OF A CUSTOMER
    public ArrayList<Car> rentCar() {
        return rentedVehicles;
    }

    public String getemail() {
        return email;
    }

    // ADD VEHICLE RENTED BY CUSTOMER TO ITS RENTED VEHICLE LIST
    public void Addrentvehicle(Customer c, Car car) {
        c.rentedVehicles.add(car);
    }

    void display() {
        for (int i = 0; i < rentedVehicles.size(); i++) {
            System.out.println(rentedVehicles.get(i).getMake());
        }
    }
}

class CarRentalSystem {
    public static void main(String[] args) {
        RentalAgency g = new RentalAgency();

        Scanner sc = new Scanner(System.in);
        boolean f = true;
        do {
            System.out.println(".........Welcome to Car Rental System...............");
            System.out.println("1. Create A Account\n" +
                    "2. Add a car\n" +
                    "3. Rent a Car\n" +
                    "4. Return a Car\n" +
                    "5. Check Return Price\n" +
                    "6. See All Cars\n" +
                    "7. Show my rented vehicles\n" +
                    "8. Exit");
            System.out.println("Enter ur choice");
            int n = sc.nextInt();
            switch (n) {
                case 1:
                    System.out.println("Enter your name");
                    String nam = sc.next();
                    System.out.println("Enter u email-id");
                    String email = sc.next();
                    Customer cus = new Customer(nam, email);
                    g.account(cus);
                    break;
                case 2:
                    System.out.println("Enter name of car");
                    String name = sc.next();
                    System.out.println("Enter model of car");
                    String m = sc.next();
                    System.out.println("Enter year of manufacturing ");
                    int i = sc.nextInt();
                    System.out.println("Enter rental price ");
                    int p = sc.nextInt();
                    System.out.println("Enter no. of seats ");
                    int si = sc.nextInt();
                    System.out.println("Enter fuel type ");
                    String fuel = sc.next();
                    Car c = new Car(name, m, i, p, si, fuel);
                    g.addVehicle(c);
                    break;
                case 3:
                    if (g.displayAll() == true) {
                        System.out.println("Enter model of car which u want to rent");
                        String mo = sc.next();
                        System.out.println("Enter ur email ");
                        String em = sc.next();
                        g.rentVehicle(em, mo);
                    }
                    break;
                case 4:
                    System.out.println("Enter ur email ");
                    String ema = sc.next();
                    System.out.println("Enter model of car");
                    String mp = sc.next();
                    g.returnVehicle(ema, mp);
                    break;
                case 5:
                    g.displayAll();
                    System.out.println("Enter model of car");
                    String mpp = sc.next();
                    System.out.println("Enter no. of days");
                    int d = sc.nextInt();
                    g.rentprice(mpp, d);
                    break;
                case 6:
                    g.displayAll();
                    break;
                case 7:
                    System.out.println("Enter ur email ");
                    String id = sc.next();
                    g.showrent(id);
                    break;
                case 8:
                    f = false;
                    break;

            }

        } while (f);
        sc.close();
    }

}
