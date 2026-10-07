package app.netlify.icarro.model;

public class Car {
    public String city, manufacture, model, year, fuel, seats, carClass, serialNumber, pricePerDay;

    public Car() {}

    public Car(String city, String manufacture, String model, String year, String fuel, String seats, String carClass, String serialNumber, String pricePerDay) {
        this.city = city;
        this.manufacture = manufacture;
        this.model = model;
        this.year = year;
        this.fuel = fuel;
        this.seats = seats;
        this.carClass = carClass;
        this.serialNumber = serialNumber;
        this.pricePerDay = pricePerDay;
    }

    @Override
    public String toString() {
        return "Car{" +
                "city='" + city + '\'' +
                ", manufacture='" + manufacture + '\'' +
                ", model='" + model + '\'' +
                ", year='" + year + '\'' +
                ", fuel='" + fuel + '\'' +
                ", seats='" + seats + '\'' +
                ", carClass='" + carClass + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", pricePerDay='" + pricePerDay + '\'' +
                '}';
    }
}
