import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class OpinionsFinding {

    static private String key = getKey();

    static ArrayList<Opinion> getOpinions(String placeID)
    {
        ArrayList<Opinion> list = new ArrayList<>();
        String baseURL = "https://maps.googleapis.com/maps/api/place/details/json?placeid=";
        String additionToURL = "&fields=name,formatted_address,rating,review&key=" + key;

        JSONObject json = null;

        try {
            json = JSONreader.readJsonFromUrl(baseURL + placeID + additionToURL);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        JSONObject results;
        if( json != null )
            results = json.getJSONObject("result");
        else
            return list;

        String name = results.getString("name");
        String address = results.getString("formatted_address");
        double rating;
        try
        {
            rating = results.getDouble("rating");
        }
        catch (Exception e)
        {
            rating = 0.0;
        }

        Place place = new Place(placeID, name, address, rating);

        JSONArray reviews;
        try
        {
            reviews = results.getJSONArray("reviews");
        }
        catch (Exception e)
        {
            reviews = null;
        }

        if(reviews != null) {

            for(int i = 0 ; i < reviews.length() ; i++) {
                JSONObject jsonObject = reviews.getJSONObject(i);
                String text = jsonObject.getString("text");
                double opinionRating = jsonObject.getDouble("rating");
                list.add(new Opinion(place, text, opinionRating));
            }
        }
        return list;
    }

    static private String getKey() {
        File file = new File("./key.txt");

        try
        {
            BufferedReader br = new BufferedReader(new FileReader(file));
            return br.readLine();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return "";
        }
    }
}
