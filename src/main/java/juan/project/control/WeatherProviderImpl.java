package juan.project.control;

import juan.project.model.Location;
import juan.project.control.WeatherData;
import juan.project.control.WeatherProvider;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class WeatherProviderImpl implements WeatherProvider {
    private final String apiKey;
    private static final String API_URL_TEMPLATE = "https://api.openweathermap.org/data/2.5/forecast?lat=%f&lon=%f&appid=%s";

    public WeatherProviderImpl(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public List<WeatherData> getWeatherForCanaryIslands() {
        List<WeatherData> weatherDataList = new ArrayList<>();
        for (Location island : getCanaryIslands()) {
            String url = buildApiUrl(island);
            try {
                String jsonData = fetchWeatherDataData(url);
                weatherDataList.addAll(parseWeatherDataData(jsonData, island));
            } catch (IOException e) {
                e.printStackTrace(); // Log error or handle it according to your needs
            }
        }
        return weatherDataList;
    }

    private String buildApiUrl(Location location) {
        return String.format(API_URL_TEMPLATE, location.getLat(), location.getLon(), apiKey);
    }

    private String fetchWeatherDataData(String url) throws IOException {
        Document doc = Jsoup.connect(url).ignoreContentType(true).get();
        return doc.body().text();
    }

    private List<WeatherData> parseWeatherDataData(String jsonData, Location location) {
        List<WeatherData> parsedData = new ArrayList<>();
        JsonArray forecastList = JsonParser.parseString(jsonData).getAsJsonObject().getAsJsonArray("list");

        for (JsonElement element : forecastList) {
            JsonObject forecast = element.getAsJsonObject();
            Instant instant = Instant.ofEpochSecond(forecast.getAsJsonPrimitive("dt").getAsLong());

            if (isMidday(instant)) {
                WeatherData weather = createWeatherData(forecast, location, instant);
                parsedData.add(weather);
            }
        }
        return parsedData;
    }

    private boolean isMidday(Instant instant) {
        return instant.atZone(ZoneId.of("UTC")).toLocalTime().equals(LocalTime.NOON);
    }

    private WeatherData createWeatherData(JsonObject forecast, Location location, Instant instant) {
        JsonObject main = forecast.getAsJsonObject("main");
        JsonObject wind = forecast.getAsJsonObject("wind");
        double rainProbability = forecast.has("pop") ? forecast.get("pop").getAsDouble() : 0.0;
        JsonObject weatherInfo = forecast.getAsJsonArray("weather").get(0).getAsJsonObject();

        return new WeatherData(
                main.getAsJsonPrimitive("temp").getAsDouble() - 273.15,
                rainProbability,
                main.getAsJsonPrimitive("humidity").getAsDouble(),
                forecast.getAsJsonObject("clouds").getAsJsonPrimitive("all").getAsDouble(),
                wind.getAsJsonPrimitive("speed").getAsDouble(),
                location,
                instant,
                weatherInfo.getAsJsonPrimitive("description").getAsString(),
                weatherInfo.getAsJsonPrimitive("icon").getAsString()
        );
    }

    private List<Location> getCanaryIslands() {
        List<Location> islands = new ArrayList<>();
        islands.add(new Location(28.1248, -15.4300, "Las Palmas"));
        islands.add(new Location(28.4636, -16.2518, "Santa Cruz de Tenerife"));
        islands.add(new Location(27.9406, -15.6035, "Fuerteventura"));
        islands.add(new Location(28.2916, -16.6291, "La Gomera"));
        islands.add(new Location(27.7610, -18.2239, "El Hierro"));
        islands.add(new Location(28.1171, -17.8830, "La Palma"));
        islands.add(new Location(27.9202, -15.5474, "Lanzarote"));
        return islands;
    }
}
