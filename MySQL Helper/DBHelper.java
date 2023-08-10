import javax.swing.plaf.nimbus.State;
import java.sql.*;

public class DBHelper{

    String userName, password;
    String dbUrl = "jdbc:mysql://localhost:3306/world";

    DBHelper(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public Connection getConnection () throws SQLException {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(dbUrl, userName, password);
            System.out.println("Connection was successful");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void query(Connection conn) throws SQLException{
        Statement statement = null;
        String query = "SELECT ID, Name, CountryCode, District, Population FROM city";
        try {
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                System.out.print(resultSet.getInt("ID") + " ");
                System.out.print(resultSet.getString("Name") + " ");
                System.out.print(resultSet.getString("CountryCode") + " ");
                System.out.print(resultSet.getString("District") + " ");
                System.out.print(resultSet.getInt("Population") + " ");
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("Error Code : " + e.getErrorCode());
            System.out.println("Error Message : " + e.getMessage());
        }
        finally {
            statement.close();
        }
    }

    public void insert(Connection connection,String name, String countryCode, String district, int population) throws SQLException {
        PreparedStatement preparedStatement = null;
        String SQL = "INSERT INTO city (Name, CountryCode, District, Population) values(?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(SQL);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, countryCode);
            preparedStatement.setString(3, district);
            preparedStatement.setInt(4, population);

            preparedStatement.executeUpdate();

            System.out.println("insert was successful");
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Error Code : " + e.getErrorCode());
            System.out.println("Error Message : " + e.getMessage());
        }

    }

    public void update(Connection connection, int population, String cityName) {
        PreparedStatement preparedStatement = null;
        String query;
        try {
            query = "UPDATE city set population=? WHERE Name=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, population);
            preparedStatement.setString(2, cityName);
            preparedStatement.executeUpdate();
            System.out.println("Data is updated");
        } catch (SQLException e) {
            System.out.println("Error Code : " + e.getErrorCode());
            System.out.println("Error Message : " + e.getMessage());
        }
    }

    public void delete(Connection connection, String cityName) {
        PreparedStatement preparedStatement = null;
        String SQL;
        try {
            SQL = "DELETE FROM city WHERE Name = ?";
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, cityName);
            preparedStatement.executeUpdate();
            System.out.println("Data is deleted");
        } catch (SQLException e) {
            System.out.println("Error Code : " + e.getErrorCode());
            System.out.println("Error Message : " + e.getMessage());
        }
    }

}
