package org.example;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import kong.unirest.*;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import kong.unirest.json.JSONString;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static org.example.API_Data.getNextOrLastGame;

//import static org.apache.commons.text.WordUtils.capitalizeFully;

public class Main {
    //

    public static String getPlayerName() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the player: ");
        String name = scanner.nextLine();
        return name;
    }
    public static String getTeam() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("which team does this player play for?:");
        String TeamName = scanner.nextLine();
        return TeamName;
    }

    public static String getUserName(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("please enter your user name");
        String userName =scanner.nextLine();
        return userName;

    }


    public static void main(String[] args) throws SQLException {
        try {
            Terminal terminal = TerminalBuilder.terminal();
            LineReader lineReader = LineReaderBuilder.builder()
                    .terminal(terminal).build();

            int choice;
            do {
                displayMenu();
                String input = lineReader.readLine("Enter your choice (1-3, or 0 to exit): ");
                try {
                    choice = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    choice = -1;
                }

                switch (choice) {
                    case 1:
                        String name = getPlayerName();
                        String Team = getTeam();
                        Player player = new Player(name,Team);
                        System.out.println(player.toString());
                        break;
                    case 2:
                        System.out.println("Enter the details for player 1");
                        String player1Name = getPlayerName();
                        String player1Team = getTeam();
                        Player player1 = new Player(player1Name,player1Team);


                        System.out.println("Enter the details for player 2");
                        String player2Name = getPlayerName();
                        String player2Team = getTeam();
                        Player player2 = new Player(player2Name,player2Team);

                        String[] player1Lines = player1.toString().split("\n");
                        String[] player2Lines = player2.toString().split("\n");

                        int maxLength = Math.max(player1Lines.length, player2Lines.length);

                        for (int i = 0; i < maxLength; i++) {
                            String player1Line = (i < player1Lines.length) ? player1Lines[i] : "";
                            String player2Line = (i < player2Lines.length) ? player2Lines[i] : "";
                            System.out.printf("%-50s %s%n", player1Line, player2Line);
                        }

                        if (player1.getWeightedAverage()>player2.getWeightedAverage()){
                            System.out.println("\n"+"According to our stats calculations :"+"\n"+
                                                player1.getPlayerName()+" is a better player");
                        }else{
                            System.out.println("\n"+"According to our stats calculations: "+"\n"+
                                    player2.getPlayerName()+" is a better player");

                        }
                        break;
                    case 3:
                        Boolean saving = true;
                        while(saving) {
                            String userName = getUserName();
                            User user = new User(userName);
                            if (user.exists(userName)) {
                                user.createFile(userName);
                                System.out.println("Successfully created account");
                                saving = false;
                            } else {
                                System.out.println("Sorry....that user name is already taken");
                                break;
                            }

                            boolean continued = true;
                            while (continued) {
                                System.out.println("Do you want to continue(Y/n):");
                                Scanner scanner = new Scanner(System.in);
                                if (scanner.nextLine().equalsIgnoreCase("n")) {
                                    break;
                                }
                                System.out.println("Enter the name and Team of your favourite player/s");
                                String playerName = getPlayerName();
                                String playerTeam = getTeam();
                                Player favPlayer = new Player(playerName, playerTeam);
                                user.addPlayer(favPlayer);


                            }
                        }
                    case 4:
                        String userName = getUserName();
                        User user = new User(userName);
                        if (user.exists(userName)){
                            user.showData();
                        }else {
                            System.out.println("Sorry....we do not have any saved data for that user name");
                        }
                    case 0:
                        System.out.println("Exiting the menu.");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } while (choice != 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private static void displayMenu() {
        System.out.println();
        System.out.println("===== Terminal Selection Menu =====");
        System.out.println("1. Single player stats");
        System.out.println("2. Head-to-head stats");
        System.out.println("3. Save favourite players");
        System.out.println("4. Get favourite players");
        System.out.println("0. Exit");
        System.out.println("===================================");
    }

}


























