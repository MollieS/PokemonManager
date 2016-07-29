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
    
    private String url;
    
    public PokemonSearch(String url) {
       this.url = url;
    }

    public JsonObject findByName(String name) {
        URL endpoint = getURL(name);
        URLConnection connection = connectToEndPoint(endpoint);
        String response = getResponse(connection);
        return parseResponse(response);
    }

    private JsonObject parseResponse(String response) {
        return new JsonParser().parse(response).getAsJsonObject();
    }

    private String getResponse(URLConnection connection) {
        String response = null;
        try {
            response = readResponse(connection);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private String readResponse(URLConnection connection) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String response = reader.readLine();
        reader.close();
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
            endpoint = new URL(url + name + "/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return endpoint;
    }
}
