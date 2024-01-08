package juan.project.control;
import juan.project.model.Location;
import java.time.Instant;

public class WeatherData {
    private double temp;
    private double rainProbability;
    private double humidity;
    private double clouds;
    private double windSpeed;
    private Location location;
    private Instant instant;
    private String weatherDescription;
    private String weatherIcon;

    public WeatherData(double temp, double rainProbability, double humidity, double clouds, double windSpeed, Location location, Instant instant, String weatherDescription, String weatherIcon) {
        this.temp = temp;
        this.rainProbability = rainProbability;
        this.humidity = humidity;
        this.clouds = clouds;
        this.windSpeed = windSpeed;
        this.location = location;
        this.instant = instant;
        this.weatherDescription = weatherDescription;
        this.weatherIcon = weatherIcon;
    }

    public double getTemp() {
        return temp;
    }

    public double getRainProbability() {
        return rainProbability;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getClouds() {
        return clouds;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public Location getLocation() {
        return location;
    }

    public Instant getInstant() {
        return instant;
    }
    public String getWeatherDescription() {
        return weatherDescription;
    }
    public String getWeatherIcon() {
        return weatherIcon;
    }
}
