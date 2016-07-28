package pkmncore;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class PokemonSearch implements SearchEngine {
    
    private URL url;
    
    public PokemonSearch(String url) {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public JsonObject findByName(String name) {
        URL url = getURL(name);
        URLConnection connection = connectToEndPoint(url);
        String response = getResponse(connection);
        return parseResponse(response);
    }

    private JsonObject parseResponse(String response) {
        JsonObject json = new JsonParser().parse(response).getAsJsonObject();
        return json;
    }

    private String getResponse(URLConnection connection) {
        String response = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            response = reader.readLine();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private URLConnection connectToEndPoint(URL url) {
        URLConnection connection = null;
        try {
            connection = url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        connection.setRequestProperty("User-Agent", "pokemon");
        return connection;
    }

    private URL getURL(String name) {
        URL endpoint = null;
        try {
            endpoint = new URL(url, name + "/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return endpoint;
    }
}
