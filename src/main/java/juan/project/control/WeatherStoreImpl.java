package juan.project.control;

import java.sql.*;

public class WeatherStoreImpl implements WeatherStore {
    private static final String DATABASE_URL = "jdbc:sqlite:weather_datos.db";

    @Override
    public void saveWeather(WeatherData weather) throws SQLException {
        String tableName = weather.getLocation().getName().replaceAll("\\s+","_"); // Reemplaza espacios con guiones bajos y crea un nombre de tabla único
        createTableIfNotExists(tableName);

        // Luego procede a guardar los datos del clima.
        String sql = "INSERT INTO " + tableName + " (instant, temp, rain_probability, humidity, clouds, wind_speed) " +
                "VALUES (?, ?, ?, ?, ?, ?);";

        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, weather.getInstant().toString());
            pstmt.setDouble(2, weather.getTemp());
            pstmt.setDouble(3, weather.getRainProbability());
            pstmt.setDouble(4, weather.getHumidity());
            pstmt.setDouble(5, weather.getClouds());
            pstmt.setDouble(6, weather.getWindSpeed());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    private void createTableIfNotExists(String tableName) throws SQLException {
        String createTableSQL = String.format(
                "CREATE TABLE IF NOT EXISTS %s (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "instant TEXT," +
                        "temp REAL," +
                        "rain_probability REAL," +
                        "humidity REAL," +
                        "clouds REAL," +
                        "wind_speed REAL" +
                        ");", tableName);

        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             Statement stmt = conn.createStatement()) {

            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            throw e;
        }
    }
}
