public class Place
{
    private String ID;
    private String name;
    private String address;
    private double rating;

    Place(String ID, String name, String address, double rating) {
        this.ID = ID;
        this.name = name;
        this.address = address;
        this.rating = rating;
    }

    String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public double getRating() {
        return rating;
    }
}
