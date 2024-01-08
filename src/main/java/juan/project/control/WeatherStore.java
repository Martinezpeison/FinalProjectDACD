package juan.project.control;

import java.sql.Connection;
import java.sql.SQLException;

public interface WeatherStore {
    void saveWeather(WeatherData weather) throws Exception;
}