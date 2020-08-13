import java.sql.*;
import java.util.ArrayList;

class DataBaseService
{
    final static private String dbName = "scraping.db";
    static void prepareEnvironment()
    {
        //createNewDatabase(dbName); // uncomment when using the app for the first time
        createIDTable(dbName);
        createPlaceTable(dbName);
        createOpinionTable(dbName);
    }

    private static void createNewDatabase(String fileName) {

        String url = "jdbc:sqlite:H:/projekty/scraping/databases/" + fileName;

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void createIDTable(String fileName)
    {
        String url = "jdbc:sqlite:H:/projekty/scraping/databases/" + fileName;

        String query = "CREATE TABLE IF NOT EXISTS IDs" +
                "(" +
                "id text PRIMARY KEY" +
                ")";
        try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement())
        {
            stmt.execute(query);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private static void createPlaceTable(String fileName)
    {
        String url = "jdbc:sqlite:H:/projekty/scraping/databases/" + fileName;

        String query = "CREATE TABLE IF NOT EXISTS place" +
                "(" +
                "id text PRIMARY KEY,\n" +
                "name text NOT NULL,\n" +
                "adress text NOT NULL,\n" +
                "rating real\n" +
                ")";
        try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement())
        {
            stmt.execute(query);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private static void createOpinionTable(String fileName)
    {
        String url = "jdbc:sqlite:H:/projekty/scraping/databases/" + fileName;

        String query = "CREATE TABLE IF NOT EXISTS opinion" +
                "(" +
                "id text NOT NULL,\n" +
                "text text NOT NULL," +
                "rating REAL" +
                ")";
        try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement())
        {
            stmt.execute(query);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    static void addID(String id)
    {
        String url = "jdbc:sqlite:H:/projekty/scraping/databases/" + dbName;

        String query = "INSERT INTO IDs" +
                "(id)\n" +
                "VALUES ('" + id + "')";
        try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement())
        {
            stmt.execute(query);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    static ArrayList<String> getIDs()
    {
        ArrayList<String> list = new ArrayList<>();

        String url = "jdbc:sqlite:H:/projekty/scraping/databases/" + dbName;

        String query = "SELECT id FROM IDs";

        try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement())
        {
            ResultSet rs = stmt.executeQuery(query);

            while( rs.next() )
            {
                String id = rs.getString("id");
                list.add(id);
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return list;
    }

    static void addOpinion( String id, String opinion, double rating )
    {
        String url = "jdbc:sqlite:H:/projekty/scraping/databases/" + dbName;

        opinion = opinion.replace("'", "''");

        String query = "INSERT INTO opinion" +
                "(id, text, rating)\n" +
                "VALUES ('" + id + "', '" + opinion +"'," + rating + ")";
        try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement())
        {
            stmt.execute(query);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    static void addPlace( Place place )
    {
        String url = "jdbc:sqlite:H:/projekty/scraping/databases/" + dbName;

        String id = place.getID();
        String name = place.getName();
        String address = place.getAddress();
        double rating = place.getRating();

        name = name.replace("'", "''");
        address = address.replace("'", "''");

        String query = "INSERT INTO place" +
                "(id, name, adress, rating)\n" +
                "VALUES ('" + id + "', '" + name + "', '" + address + "', " + rating + ")";
        try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement())
        {
            stmt.execute(query);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage() + " nr1 ");
        }
    }

}
