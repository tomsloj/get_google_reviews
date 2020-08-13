import java.util.ArrayList;

public class Scraping
{
    //coordinates of area
    final static double rightX = 28.749855;
    final static double downY = 76.941338;
    final static double leftX = 28.511826;
    final static double upY = 77.337701;

    //ammount of checkpoints
    final static int samples = 71;

    public static void main(String[] args)
    {
        //loadIDs();
        loadOpinions();
    }

    /**
     * method to load IDs of places in area
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
    private static void loadOpinions()
    {
        ArrayList<String> ids = DataBaseService.getIDs();
        for( String id : ids )
        {
            ArrayList<String> opinions = OpinionsFinding.getOpinions(id);
            for( String opinion : opinions )
            {
                DataBaseService.addOpinion(id, opinion);
            }
        }
    }
}
