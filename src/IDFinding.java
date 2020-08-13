import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import java.net.URL;
import java.nio.charset.Charset;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class IDFinding
{
    static private String key = getKey();
    static private String baseURLfirstPart = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=";
    static private String baseURLsecondPart = "&radius=500&type=";
    static private String basicURLthirdPart = "&key=" + key;
    static private String additionToURL = "&pagetoken=";

    static ArrayList<String> find( double x, double y, String type )
    {
        String URL = baseURLfirstPart + x + ",%20" + y + baseURLsecondPart + type + basicURLthirdPart;
        String nextTokenURL = "";

        ArrayList<String> list = new ArrayList<>();

        JSONObject json = null;
        try
        {
            json = JSONreader.readJsonFromUrl(URL);
            JSONArray results = json.getJSONArray("results");
            if(results != null) {

                for(int i = 0 ; i < results.length() ; i++)
                {
                    JSONObject jsonObject = results.getJSONObject(i);
                    list.add(jsonObject.getString("place_id"));
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        try
        {
            if( json != null )
            {
                nextTokenURL = json.getString("next_page_token");
                //find(amount - list.size(), x, y, type, nextTokenURL );
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            return list;
        }
        return list;
    }

    static private ArrayList<String> find( double x, double y, String type, String nextToken )
    {
        String URL;

        ArrayList<String> list = new ArrayList<>();

        while( nextToken != null )
        {
            URL = baseURLfirstPart + x + ", " + y + baseURLsecondPart + type + basicURLthirdPart + additionToURL + nextToken;
            System.out.println(URL);

            JSONObject json = null;
            try {
                json = JSONreader.readJsonFromUrl(URL);
                JSONArray results = json.getJSONArray("results");
                if(results != null)
                {
                    for(int i = 0 ; i < results.length() ; i++)
                    {
                        JSONObject jsonObject = results.getJSONObject(i);
                        list.add(jsonObject.getString("place_id"));
                    }
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            try
            {
                if( json != null )
                    nextToken = json.getString("next_page_token");
            }
            catch (JSONException e)
            {
                e.printStackTrace();
                return list;
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
