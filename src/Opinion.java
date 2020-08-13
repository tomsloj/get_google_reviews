public class Opinion
{
    private Place place;
    private String text;
    private double rating;

    Opinion(Place place, String text, double rating) {
        this.place = place;
        this.text = text;
        this.rating = rating;
    }

    Place getPlace() {
        return place;
    }

    public String getText() {
        return text;
    }

    public double getRating() {
        return rating;
    }
}
