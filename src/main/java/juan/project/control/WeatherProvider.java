package juan.project.control;
import juan.project.model.Location;
import java.time.Instant;
import java.util.List;

public interface WeatherProvider {
    List<WeatherData> getWeatherForCanaryIslands();
}
