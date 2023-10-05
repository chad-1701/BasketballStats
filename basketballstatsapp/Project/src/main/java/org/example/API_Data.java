package org.example;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class API_Data {
    public static JSONArray GetTeams() {
        JSONArray teamsArray = new JSONArray();
        teamsArray.put(new JSONObject().put("name", "Los Angeles Lakers").put("teamId", "583ecae2-fb46-11e1-82cb-f4ce4684ea4c"));
        teamsArray.put(new JSONObject().put("name", "Golden State warriors").put("teamId", "583ec825-fb46-11e1-82cb-f4ce4684ea4c"));
        teamsArray.put(new JSONObject().put("name", "Orlando Magic").put("teamId", "583ed157-fb46-11e1-82cb-f4ce4684ea4c"));
        teamsArray.put(new JSONObject().put("name", "Oklahoma City Thunder").put("teamId", "583ecfff-fb46-11e1-82cb-f4ce4684ea4c"));
        teamsArray.put(new JSONObject().put("name", "Houston Rockets").put("teamId", "583ecb3a-fb46-11e1-82cb-f4ce4684ea4c"));
        teamsArray.put(new JSONObject().put("name", "Sacramento Kings").put("teamId", "583ed0ac-fb46-11e1-82cb-f4ce4684ea4c"));
        teamsArray.put(new JSONObject().put("name", "Detroit Pistons").put("teamId", "583ec928-fb46-11e1-82cb-f4ce4684ea4c"));
        teamsArray.put(new JSONObject().put("name", "Indiana Pacers").put("teamId", "583ec7cd-fb46-11e1-82cb-f4ce4684ea4c"));
        teamsArray.put(new JSONObject().put("name", "Portland Trail Blazers").put("teamId", "583ed056-fb46-11e1-82cb-f4ce4684ea4c"));
        teamsArray.put(new JSONObject().put("name", "New Orleans Pelicans").put("teamId", "583ecc9a-fb46-11e1-82cb-f4ce4684ea4c"));
        teamsArray.put(new JSONObject().put("name", "San Antonio Spurs").put("teamId", "583ecd4f-fb46-11e1-82cb-f4ce4684ea4c"));
        teamsArray.put(new JSONObject().put("name", "Washington Wizards").put("teamId", "583ec8d4-fb46-11e1-82cb-f4ce4684ea4c"));
        teamsArray.put(new JSONObject().put("name", "Cleveland Cavaliers").put("teamId", "583ec773-fb46-11e1-82cb-f4ce4684ea4c"));
        teamsArray.put(new JSONObject().put("name", "Toronto Raptors").put("teamId", "583ecda6-fb46-11e1-82cb-f4ce4684ea4c"));
        teamsArray.put(new JSONObject().put("name", "Phoenix Suns").put("teamId", "583ecfa8-fb46-11e1-82cb-f4ce4684ea4c"));
        teamsArray.put(new JSONObject().put("name", "Brooklyn Nets").put("teamId", "583ec9d6-fb46-11e1-82cb-f4ce4684ea4c"));
        teamsArray.put(new JSONObject().put("name", "Philadelphia 76ers").put("teamId", "583ec87d-fb46-11e1-82cb-f4ce4684ea4c"));
        teamsArray.put(new JSONObject().put("name", "Utah Jazz").put("teamId", "583ece50-fb46-11e1-82cb-f4ce4684ea4c"));
        teamsArray.put(new JSONObject().put("name", "Charlotte Hornets").put("teamId", "583ec97e-fb46-11e1-82cb-f4ce4684ea4c"));
        teamsArray.put(new JSONObject().put("name", "Atlanta Hawks").put("teamId", "583ecb8f-fb46-11e1-82cb-f4ce4684ea4c"));
        teamsArray.put(new JSONObject().put("name", "Chicago Bulls").put("teamId", "583ec5fd-fb46-11e1-82cb-f4ce4684ea4c"));
        teamsArray.put(new JSONObject().put("name", "Memphis Grizzlies").put("teamId", "583eca88-fb46-11e1-82cb-f4ce4684ea4c"));
        teamsArray.put(new JSONObject().put("name", "Denver Nuggets").put("teamId", "583ed102-fb46-11e1-82cb-f4ce4684ea4c"));
        teamsArray.put(new JSONObject().put("name", "Minnesota Timberwolves").put("teamId", "583eca2f-fb46-11e1-82cb-f4ce4684ea4c"));
        teamsArray.put(new JSONObject().put("name", "Milwaukee Bucks").put("teamId", "583ecefd-fb46-11e1-82cb-f4ce4684ea4c"));
        teamsArray.put(new JSONObject().put("name", "Miami Heat").put("teamId", "583ecea6-fb46-11e1-82cb-f4ce4684ea4c"));
        teamsArray.put(new JSONObject().put("name", "Dallas Mavericks").put("teamId", "583ecf50-fb46-11e1-82cb-f4ce4684ea4c"));
        teamsArray.put(new JSONObject().put("name", "New York Knicks").put("teamId", "583ec70e-fb46-11e1-82cb-f4ce4684ea4c"));
        teamsArray.put(new JSONObject().put("name", "LA Clippers").put("teamId", "583ecdfb-fb46-11e1-82cb-f4ce4684ea4c"));
        teamsArray.put(new JSONObject().put("name", "Boston Celtics").put("teamId", "583eccfa-fb46-11e1-82cb-f4ce4684ea4c"));
        return teamsArray;
    }

    public static String getTeamID(String ballClub, JSONArray Team) {
        String teamID = "";
        for (int i = 0; i < Team.length(); i++) {
            JSONObject Squad = Team.getJSONObject(i);
            String Ballclub = ballClub.toLowerCase();
            if (Squad.getString("name").toLowerCase().contains(ballClub)) {
                teamID = Squad.getString("teamId");
            }

        }
        return teamID;
    }

    public static HttpResponse<JsonNode> getTeamData(String teamID) {
        String url = String.format("https://api.sportradar.us/nba/trial/v8/en/seasons/2021/REG/teams/%s/statistics" +
                ".json?api_key=k4pvr844best5spnmch6v75m", teamID);
        HttpResponse<JsonNode> response = Unirest.get(url).asJson();
        return response;
    }

    public static JSONArray GetTeamPlayers(HttpResponse<JsonNode> TeamData) {
        JSONArray Players = TeamData.getBody().getObject().getJSONArray("players");
        return Players;
    }


    public static JSONObject GetPlayer(JSONArray Players, String PlayerName) {
        try {
            List<JSONObject> filtered_array = new ArrayList<>();
            for (int i = 0; i < Players.length(); i++) {
                JSONObject item = Players.getJSONObject(i);
                String name = item.getString("full_name").toLowerCase();
                if (name.equalsIgnoreCase(PlayerName.toLowerCase())) {
                    filtered_array.add(item);
                }

            }
            return filtered_array.get(0);
        } catch (Exception e) {
            System.out.println("Player does not exist");
        }
        return new JSONObject();
    }

    public static JSONObject getLeagueSchedule() {
        String url = "https://api.sportradar.us/nba/trial/v8/en/games/2022/REG/schedule.json?api_key=k4pvr844best5spnmch6v75m";
        HttpResponse<JsonNode> response = Unirest.get(url).asJson();
        return response.getBody().getObject();
    }
//    public static JSONObject getLeagueSchedule(){
//        return getSchedule().getBody().getObject();
//    }

    public static String getNextOrLastGame(String TeamID) {
        JSONObject LeagueSchedule = getLeagueSchedule();
        for (int i = 0; i < LeagueSchedule.getJSONArray("games").length(); i++) {
            System.out.println(LeagueSchedule.getJSONArray("games").length());
            if ((LeagueSchedule.getJSONArray("games").getJSONObject(i).getJSONObject("home").getString("id").equalsIgnoreCase(TeamID)
                || LeagueSchedule.getJSONArray("games").getJSONObject(i).getJSONObject("away").getString("id").equalsIgnoreCase(TeamID))
                && LeagueSchedule.getJSONArray("games").getJSONObject(i).getString("status").equalsIgnoreCase("open")) {
                return "Next Game: "+LeagueSchedule.getJSONArray("games").getJSONObject(i).getString("scheduled");
            }else{
                return "Last Game: "+LeagueSchedule.getJSONArray("games").getJSONObject( LeagueSchedule.getJSONArray(
                        "games").length()-1).getString("scheduled");

            }

        }
        return "";
    }

    public static JSONArray getLeagueInjuries() {
        String url = "https://api.sportradar.us/nba/trial/v8/en/league/injuries.json?api_key=k4pvr844best5spnmch6v75m";
        HttpResponse<JsonNode> response = Unirest.get(url).asJson();
        if(response.isSuccess()){
            return response.getBody().getObject().getJSONArray("teams");
        }else{
            return new JSONArray();
        }

    }
    public static JSONObject getTeamInjuries(JSONArray LeagueInjuries, String TeamID) {
        JSONObject TeamInjuries = null;
        for (int i = 0; i < LeagueInjuries.length(); i++) {
            if (LeagueInjuries.getJSONObject(i).getString("id").equalsIgnoreCase(TeamID))
                TeamInjuries = LeagueInjuries.getJSONObject(i);

        }
        if (TeamInjuries == null) {
            return null;
        } else {
            return TeamInjuries;
        }
    }




//&&TimeComparison.isTimeMore(LeagueSchedule.getJSONArray("games").getJSONObject(i).getString(
//                        "scheduled"),currentTime.toString())

}
