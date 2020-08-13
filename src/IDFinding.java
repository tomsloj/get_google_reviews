import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class IDFinding
{
    String key = getKey();
    String baseURL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=28.636295,%2077.126851&radius=15000&type=restaurant&key=" + key;
    String additionToURL = "&pagetoken=";
    String nextTokenURL = null;

    ArrayList<String> find()
    {
        String URL = baseURL;

        ArrayList<String> list = new ArrayList<>();
        do {
            nextTokenURL = null;
            JSONObject json = null;
            try {
                json = readJsonFromUrl(URL);
                JSONArray results = json.getJSONArray("results");
                if(results != null) {

                    for(int i = 0 ; i < results.length() ; i++) {
                        JSONObject jsonObject = results.getJSONObject(i);
                        list.add(jsonObject.getString("place_id"));
                    }
                }
            }
            catch (IOException e)
            {
                System.out.println("Coś poszło nie tak i nie wiem co");
            }

            if( json != null )
                nextTokenURL = json.getString("next_page_token");
            URL = baseURL + additionToURL + nextTokenURL;
        }while( nextTokenURL != null );

        return list;
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    String getKey() {
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
