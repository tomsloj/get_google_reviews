import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class OpinionsFinding {

    static private String key = getKey();

    public static ArrayList<String> getOpinions(String placeID)
    {
        ArrayList<String> list = new ArrayList<>();
        String baseURL = "https://maps.googleapis.com/maps/api/place/details/json?placeid=";
        String additionToURL = "&key=" + key;

        JSONObject json = null;

        try {
            json = JSONreader.readJsonFromUrl(baseURL + placeID + additionToURL);
        } catch (IOException e)
        {
            e.printStackTrace();
            System.out.println("Coś poszło nie tak i nie wiem co nr 2");
        }
        JSONObject results = json.getJSONObject("result");
        JSONArray reviews = results.getJSONArray("reviews");

        if(reviews != null) {

            for(int i = 0 ; i < reviews.length() ; i++) {
                JSONObject jsonObject = reviews.getJSONObject(i);
                list.add(jsonObject.getString("text"));
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
