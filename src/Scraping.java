import java.util.ArrayList;

public class Scraping
{
    //coordinates of area
    private final static double rightX = 28.749855;
    private final static double downY = 76.941338;
    private final static double leftX = 28.511826;
    private final static double upY = 77.337701;

    //ammount of checkpoints
    private final static int samples = 71;

    public static void main(String[] args)
    {
        //loadIDs();
        loadOpinions(0, 30000);
    }

    /**
     * method to load IDs of places from database
     */
    static void loadIDs()
    {
        DataBaseService.prepareEnvironment();
        ArrayList<String> listOfID = new ArrayList<>();

        for( double x = leftX; x < rightX; x += (rightX - leftX) / samples)
        {
            for( double y = downY; y < upY; y += (upY - downY) / samples)
            {
                ArrayList<String> tmpList = IDFinding.find(x, y, "restaurant");
                listOfID.addAll(tmpList);
                tmpList = IDFinding.find( x, y, "bakery");
                listOfID.addAll(tmpList);
                tmpList = IDFinding.find( x, y, "beauty_salon");
                listOfID.addAll(tmpList);
                tmpList = IDFinding.find( x, y, "cafe");
                listOfID.addAll(tmpList);
                tmpList = IDFinding.find( x, y, "gym");
                listOfID.addAll(tmpList);
            }
            System.out.println(x);
        }

        System.out.println("liczba ids:" + listOfID.size() + "\n");

        for (String id: listOfID )
        {
            DataBaseService.addID(id);
        }
    }

    /**
     * method to load opinions of places
     */
    private static void loadOpinions( int p, int k )
    {
        ArrayList<String> ids = DataBaseService.getIDs();
        for( int i = p; i < k; ++i )
        {
            String id = ids.get(i);
            ArrayList<Opinion> opinions = OpinionsFinding.getOpinions(id);
            for( Opinion opinion: opinions )
            {
                Place place = opinion.getPlace();

                String placeID = place.getID();
                String opinionText = opinion.getText();
                double opinionRating = opinion.getRating();

                try
                {
                    DataBaseService.addPlace(place);
                    DataBaseService.addOpinion(placeID, opinionText, opinionRating);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        }
    }
}
