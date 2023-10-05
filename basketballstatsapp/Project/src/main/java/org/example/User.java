package org.example;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class User {
    private String name;
    private File file;
    private PlayerDAO playerDAO;
    public User(String name) throws SQLException {
        this.playerDAO = new PlayerDAO(name);


    }

    public List<String> getData() throws SQLException {
       return this.playerDAO.getAllPlayers();
    }

    public void showData() throws SQLException {
        List<String> playerData = getData();
        for (int i =0; i<playerData.size();i++){
            System.out.println(playerData.get(i));
        }
    }

    public boolean exists(String name){
       File file = new File (name+".sqlite");
       if (file.exists()){
           return true;
       }

        return false;
    }
    public void addPlayer(Player player) throws SQLException {
        playerDAO.createPlayer(player);


    }
    public void deletePlayer(Player player){

    }

    public void saveChanges(){

    }
    public void createFile(String name){
        String url = "jdbc:sqlite:"+this.name+".sqlite";
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }




    }





