package model;

import java.sql.*;
import java.util.ArrayList;
import java.awt.List;

public class DataBase {
    /*
    * dbList - to get and insert content to the DB
    * idList - to get the right ID from the DB to delete the right row
    * contentList - for the UI to see the content in the DB
    */
    private ArrayList<String> dbList = new ArrayList<>();
    private ArrayList<Integer> idList = new ArrayList<>();
    private List contentList = new List();

    // bulid the connection to the DB
    private Connection connect(){
        String url = "jdbc:sqlite:SportCalender.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    // get the content and show it in the UI
    public List showContent(){
        dbList.clear();
        contentList.removeAll();

        String sql = "SELECT Calendar.eventDateTime, SportArt.sportName, Calendar.description\n"
               + "FROM Calendar\n"
               + "INNER JOIN SportArt\n"
               + "ON Calendar._sportName = SportArt.ID";
        try {
            Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                dbList.add(rs.getString("eventDateTime") + ", " +
                        rs.getString("sportName") + ", " +
                        rs.getString("description"));
            }
            for (String data: dbList) {
                contentList.add(data);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return contentList;
    }

    // for new entries - more events
    public void insertContent(String eventDateTime, String sportName, String description){
        dbList.clear();

        String getSportName = "SELECT * FROM SportArt";
        String insertSportArt = "INSERT INTO SportArt(sportName) VALUES(?)";
        String insertCalendar = "INSERT INTO Calendar(eventDateTime, description, _sportName) VALUES(?,?,?)";

        try {
            // connect to the DB, get the Data from SportArt table
            Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(getSportName);

            while (rs.next()) {
                dbList.add(rs.getString("sportName")
            );}

            PreparedStatement pstmt = conn.prepareStatement(insertSportArt);

            // is the name of the sport already in the table? When not we insert it!
            if (!dbList.contains(sportName)) {
                dbList.add(sportName);
                pstmt.setString(1, sportName);
                pstmt.executeUpdate();
            }

            // after we altered (or not) the SportArt table we move to the Calendar and insert the new Event data
            pstmt = conn.prepareStatement(insertCalendar);
            pstmt.setString(1,eventDateTime);
            pstmt.setString(2,description);
            pstmt.setInt(3, dbList.indexOf(sportName) + 1);

            pstmt.executeUpdate();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    // used in the Controller class for the removeContent method
    public int getListIndex(){
        return contentList.getSelectedIndex();
    }

    // gets the ID from the controller class and delete the desired row from the DB
    public void deleteContent(int id) {
        idList.clear();
        String getID = "SELECT ID FROM Calendar";

        try{
            Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(getID);

            while (rs.next()) {
                idList.add(rs.getInt("ID"));
            }

            String delete = "DELETE FROM Calendar WHERE Calendar.ID = " + idList.get(id);
            PreparedStatement pstmt = conn.prepareStatement(delete);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}