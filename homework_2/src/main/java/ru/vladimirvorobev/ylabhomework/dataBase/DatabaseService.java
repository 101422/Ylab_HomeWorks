package ru.vladimirvorobev.ylabhomework.dataBase;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * Сервис для работы с базой данных.
 **/
public class DatabaseService {

    private static String URL;
    private static String USER_NAME;
    private static String PASSWORD;
    private static String CHANGELOG_FILE;
    static Properties property;

    static {
        try {
            InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("JDBCSettings.properties");

            property = new Properties();
            property.load(in);

            URL = property.getProperty("db.conn.url");
            USER_NAME = property.getProperty("db.username");
            PASSWORD = property.getProperty("db.password");
            CHANGELOG_FILE = property.getProperty("db.changelog_file");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);

            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));

            Liquibase liquibase = new Liquibase(CHANGELOG_FILE, new ClassLoaderResourceAccessor(), database);

            liquibase.update();

        } catch (SQLException | DatabaseException e) {
            System.out.println("SQL Exception during migration " + e.getMessage());
        } catch (LiquibaseException e) {
            throw new RuntimeException(e);
        }

    }

    public DatabaseService() {
    }

    /**
     * Устанавливает подключение к базе данных
     *
     * @return сессия соединения с базой данных
     **/
    public Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER_NAME, PASSWORD);
    }

}
