package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDAO {
    private Connection connection;

    public PlayerDAO(String userName) throws SQLException{

            String url = "jdbc:sqlite:"+userName+".sqlite";
            Connection conn = DriverManager.getConnection(url);
                if (conn != null) {
                    DatabaseMetaData meta = conn.getMetaData();
                    this.connection = conn;
                    createPlayersTableIfNotExists();

            }




            }
    private void createPlayersTableIfNotExists() throws SQLException {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS players (" +
                "name TEXT PRIMARY KEY," +
                "assists FLOAT," +
                "points FLOAT," +
                "blocks FLOAT," +
                "rebounds FLOAT," +
                "efficiency FLOAT," +
                "trueShooting FLOAT," +
                "steals FLOAT," +
                "turnovers FLOAT," +
                "injuryStatus TEXT," +
                "nextGame TEXT," +
                "teamName TEXT" +
                ")";
        try (Statement statement = connection.createStatement()) {
            statement.execute(createTableQuery);
        }
    }

    public void createPlayer(Player player) throws SQLException {
        String insertQuery = "INSERT INTO players (name, assists, points, blocks, rebounds, efficiency, trueShooting," +
                " steals, turnovers, injuryStatus, nextGame,teamName) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, player.getPlayerName());
            preparedStatement.setFloat(2, player.getAssists());
            preparedStatement.setFloat(3, player.getPoints());
            preparedStatement.setFloat(4, player.getBlocks());
            preparedStatement.setFloat(5, player.getRebounds());
            preparedStatement.setFloat(6, player.getEfficiency());
            preparedStatement.setDouble(7, player.getTrueShooting());
            preparedStatement.setFloat(8, player.getSteals());
            preparedStatement.setFloat(9, player.getTurnovers());
            preparedStatement.setString(10, player.getInjuryStatus(player.getPlayerName()));
            preparedStatement.setString(11, player.nextGame);
            preparedStatement.setString(12, player.getTeamName());

            preparedStatement.executeUpdate();
        }
    }

    // Method to retrieve a Player record from the database by name.
    public Player getPlayerByName(String name) throws SQLException {
        String selectQuery = "SELECT * FROM players WHERE name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Player player = new Player(name, resultSet.getString("teamName"));
                    return player;
                }
            }
        }
        return null;
    }

    // Method to retrieve a list of all Player records from the database.
    public List<String> getAllPlayers() throws SQLException {
        List<String> players = new ArrayList<>();
        String selectQuery = "SELECT * FROM players";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectQuery)) {
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String team = resultSet.getString("teamName");
                Player player = new Player(name,team);
                players.add(player.toString());
            }
        }
        return players;
    }

    public void updatePlayer(Player player) throws SQLException {
        String updateQuery = "UPDATE players SET assists = ?, points = ?, blocks = ?, rebounds = ?, efficiency = ?, trueShooting = ?, steals = ?, turnovers = ?, injuryStatus = ?, nextGame = ? WHERE name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setFloat(1, player.getAssists());
            preparedStatement.setFloat(2, player.getPoints());
            preparedStatement.setFloat(3, player.getBlocks());
            preparedStatement.setFloat(4, player.getRebounds());
            preparedStatement.setFloat(5, player.getEfficiency());
            preparedStatement.setDouble(6, player.getTrueShooting());
            preparedStatement.setFloat(7, player.getSteals());
            preparedStatement.setFloat(8, player.getTurnovers());
            preparedStatement.setString(9, player.injuryStatus);
            preparedStatement.setString(10, player.nextGame);
            preparedStatement.setString(11, player.getPlayerName());
            preparedStatement.executeUpdate();
        }
    }


    public void deletePlayer(String playerName) throws SQLException {
        String deleteQuery = "DELETE FROM players WHERE name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setString(1, playerName);
            preparedStatement.executeUpdate();
        }
    }

    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}

