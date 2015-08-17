package me.laekh.skypoints.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import me.laekh.skypoints.SkyPoints;
import me.laekh.skypoints.profiles.SkyProfile;

public class SkyDB {
	
	public static Connection c = null;
	
	public SkyDB() {
		try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"+SkyPoints.getInstance().getDataFolder()+"/SkyProfiles.db");
            c.setAutoCommit(true);
	    	Statement statement = c.createStatement();
	    	statement.executeUpdate("CREATE TABLE IF NOT EXISTS `SkyProfiles` ("
	    			+ " `UUID` char(35) NOT NULL,"
	    			+ " `Username` varchar(16) NOT NULL,"
	    			+ " `Points` int NOT NULL DEFAULT '0',"
	    			+ " `LastLogin` long NOT NULL DEFAULT '0',"
	    			+ "PRIMARY KEY (`UUID`)"
	    			+ ")");
        } catch(Exception ignored) {}
	}
	
	public void close() {
		try {
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void loadProfile(SkyProfile profile) {
		try {
			Statement statement = c.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM `SkyProfiles` WHERE `UUID` = '"+ profile.getUUID() +"'");
			while(rs.next()) {
				String name = rs.getString(2);
				Integer points = rs.getInt(3);
				long lastLogin = rs.getLong(4);
				profile.setPoints(points);
				profile.setLastLogin(lastLogin);
				if(profile.getName() == null) {
					profile.setName(name);
				} else {
					if(!profile.getName().equals(name)) {
						profile.setName(name);
						profile.save();
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void saveProfile(SkyProfile profile) {
		Statement statement;
		try {
			statement = c.createStatement();
			if(hasProfile(profile.getUUID())) {
				statement.executeUpdate("UPDATE `SkyProfiles` SET "
		    			+ "`Username` = '"+ profile.getName() +"', "
		    			+ "`Points` = '"+ profile.getPoints() +"', "
				    	+ "`LastLogin` = '"+ profile.getLastLogin().getTime() +"' "
		    			+ "WHERE `UUID` = '"+profile.getUUID()+"'");
			} else {
				statement.executeUpdate("INSERT INTO `SkyProfiles` (`UUID`, `Username`, `Points`, `LastLogin`) "
		    			+ "Values ('"+ profile.getUUID() + "', '"+ profile.getName() +"', '"+ profile.getPoints() +"', '"+ profile.getLastLogin().getTime() +"')");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean hasProfile(UUID id) {
		try {
	    	Statement statement = c.createStatement();
	    	String querySelect = "SELECT `UUID` FROM `SkyProfiles` WHERE `UUID`='"+ id + "'";
	    	ResultSet rs = statement.executeQuery(querySelect);
	    	if (!rs.next())
	    		return false;
	    	return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
    }

}
