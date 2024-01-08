package juan.project.control;
import java.util.concurrent.*;

public class WeatherController {
    private WeatherProvider weatherProvider;
    private WeatherStore weatherStore;

    public WeatherController(WeatherProvider weatherProvider, WeatherStore weatherStore) {
        this.weatherProvider = weatherProvider;
        this.weatherStore = weatherStore;
    }

    public void startWeatherUpdateScheduler() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this::updateWeather, 0, 6, TimeUnit.HOURS);
    }

    private void updateWeather() {
        weatherProvider.getWeatherForCanaryIslands().forEach(weather -> {
            try {
                weatherStore.saveWeather(weather);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
