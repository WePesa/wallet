package wepesa.data;

import wepesa.core.Constants;

import java.sql.*;

/**
 * Created by Harsh.Pokharna on 6/30/2015.
 */
public class DatabaseService {

    public static final String INSERT_USER = "INSERT INTO user (email, first_name, last_name, password) VALUES (?, ?, ?, ?);";
    public static final String FIND_USER = "SELECT * FROM user WHERE email = ?";


    public static int insertUserIntoTable(String email, String first_name, String last_name, String password) {

        Connection databaseConnection = null;
        PreparedStatement preparedStatement = null;
        DatabaseAdapter databaseAdapter = DatabaseAdapter.getInstance();

        try {
            databaseConnection = databaseAdapter.getConnection();

            preparedStatement = databaseConnection.prepareStatement(INSERT_USER);

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, first_name);
            preparedStatement.setString(3, last_name);
            preparedStatement.setString(4, password);

            preparedStatement.executeUpdate();

            preparedStatement.close();
            databaseConnection.close();

            return Constants.USER_INSERTED;

        } catch (SQLException e) {

            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }

                if (databaseConnection != null) {
                    databaseConnection.close();
                }
            } catch (Exception e1) {

            }
                if(e.getErrorCode() == 19)
                {
                    return Constants.USER_EMAIL_EXISTS;
                }

                throw new RuntimeException();

        }
    }

    public static int readUserFromTable(String email, String password) {

        Connection databaseConnection = null;
        PreparedStatement preparedStatement = null;
        DatabaseAdapter databaseAdapter = DatabaseAdapter.getInstance();

        try {
            databaseConnection = databaseAdapter.getConnection();

            preparedStatement = databaseConnection.prepareStatement(FIND_USER);

            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();
            String actualPassword = resultSet.getString(4);

            preparedStatement.close();
            databaseConnection.close();

            if(password.equals(actualPassword))
            {
                return Constants.LOGIN_SUCCESSFUL;
            }else{

                return Constants.LOGIN_FAIL;
            }

        } catch (SQLException e) {

            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }

                if (databaseConnection != null) {
                    databaseConnection.close();
                }
            } catch (Exception e1) {

            }

            if(e.getMessage().equals("ResultSet closed"))
            {
                return Constants.USER_DOES_NOT_EXIST;

            }else
            {
                throw new RuntimeException();
            }

        }
    }

}
