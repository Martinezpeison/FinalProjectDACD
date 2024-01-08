package juan.project.control;

public class Main {
    public static void main(String[] args) {
        WeatherProvider weatherProvider = new WeatherProviderImpl("8adffe0098eb976877801bcca2007629");
        WeatherStore weatherStore = new WeatherStoreImpl();
        WeatherController weatherController = new WeatherController(weatherProvider, weatherStore);
        weatherController.startWeatherUpdateScheduler();
    }
}