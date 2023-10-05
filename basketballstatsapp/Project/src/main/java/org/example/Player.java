package org.example;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Player  {
    private  float assists;

    private  float points;
    private float blocks;
    private float rebounds;
    private float efficiency;
    private float trueShooting;
    private float steals;
    private float turnovers;
    public String injuryStatus;
    public String nextGame;
    private JSONObject PlayerData;
    private JSONObject MatchData;
    private JSONArray LeagueInjuries;
    private JSONObject TeamInjuryData;
    private String playerName;
    private String teamName;

    String InjuryState =InjuryStatus.HEALTHY.name();
    String id;

    Player(String name, String TeamName) {
        this.playerName = name;
        this.teamName = TeamName;
        JSONArray Teams = API_Data.GetTeams();
        String TeamID = API_Data.getTeamID(TeamName,Teams);
        this.nextGame = API_Data.getNextOrLastGame(TeamID);
        HttpResponse<JsonNode> PlayerTeam = API_Data.getTeamData(TeamID);
        JSONArray AllPlayers = API_Data.GetTeamPlayers(PlayerTeam);
        LeagueInjuries = API_Data.getLeagueInjuries();
        PlayerData = API_Data.GetPlayer(AllPlayers,name);
        TeamInjuryData = API_Data.getTeamInjuries(LeagueInjuries,TeamID);

    }

    public JSONObject getTeamInjuryData(){
        return TeamInjuryData;
    }

    public boolean isInjured(){
        if(InjuryState.equalsIgnoreCase("INJURED")){
            return true;
        }
        return false;
    }

    public String getInjuryStatus(String PlayerName){
        if (TeamInjuryData != null){
            for (int i =0; i<TeamInjuryData.length()-1;i++){
                if (this.TeamInjuryData.getJSONArray("players").getJSONObject(i).getString("full_name").equalsIgnoreCase(PlayerName)){
                    this.InjuryState = InjuryStatus.INJURED.name();
                    String InjuryDetails = TeamInjuryData.getJSONArray("players").getJSONObject(i).getJSONArray("injuries").getJSONObject(0).getString("comment");
                    return "Injury status: "+InjuryState+"\n"+"Injury Details: "+InjuryDetails;
                }
            }
        }

        return "Injury status: "+InjuryState;
    }

    public String getPlayerName(){
        return this.playerName;
    }

    public String getTeamName(){
        return this.teamName;
    }
    public double getTrueShooting() {
        float fieldGoalAttempts = PlayerData.getJSONObject("average").getLong("true_shooting_att");
        float points = this.points;
        float freeThrowAttempts = PlayerData.getJSONObject("average").getLong("free_throws_att");
        double trueShooting = ((0.5 * points)/(fieldGoalAttempts+(freeThrowAttempts*0.475)));
        return trueShooting*100;
    }

    public float getAssists() {
        this.assists = PlayerData.getJSONObject("average").getFloat("assists");
        return this.assists;

    }

    public float getPoints() {
        this.points= PlayerData.getJSONObject("average").getFloat("points");
        return this.points;
    }

    public float getRebounds() {
        this.rebounds =  PlayerData.getJSONObject("average").getFloat("rebounds");
        return this.rebounds;
    }

    public float getSteals() {
        this.steals = PlayerData.getJSONObject("average").getLong("steals");
        return this.steals;
    }
    public float getBlocks() {
        this.blocks = PlayerData.getJSONObject("average").getLong("blocks");
        return this.steals;
    }

    public float getTurnovers() {
        return PlayerData.getJSONObject("average").getFloat("turnovers");
    }

    public float getEfficiency() {
        return PlayerData.getJSONObject("average").getFloat("efficiency");
    }

    public double getWeightedAverage(){
        double avg = this.assists-this.turnovers *0.2
                +this.rebounds *0.2
                +this.points*(100-getTrueShooting()) *0.2
                +this.assists * 0.2
                +this.steals *0.2;

        return avg;
    }

    public JSONObject getPlayerData() {
        return PlayerData;
    }
    public String getNextGame(String teamId){

        return this.nextGame;
    }


    @Override
    public String toString(){
        String stats =
                "Player: "+this.playerName+"\n"
                +"Team :"+this.teamName+"\n"
                +"Points: "+getPoints()+"\n"
                +"Assists: "+getAssists()+"\n"
                +"Rebounds: "+getRebounds()+"\n"
                +"Blocks: "+getBlocks()+"\n"
                +"Steals: "+getSteals()+"\n"
                +"Turnovers pg: "+getTurnovers()+"\n"
                +"True Shooting %: "+getTrueShooting()+"\n"
                +getInjuryStatus(this.playerName)+"\n"
                +"Weighted average: "+getWeightedAverage()+"\n"
                +this.nextGame;
                    ;
        return stats;
    }
}