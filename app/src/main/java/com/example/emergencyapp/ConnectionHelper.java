package com.example.emergencyapp;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.io.Console;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionHelper {
    Connection con;
    String user, pass, ip, port, database;
    @SuppressLint("NewApi")
    public Connection connectionClass(){
        ip = "10.164.5.103";
        database = "EmergencyDB";
        port = "50002";
        user = "sa";
        pass = "#Nadieselasabe1";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String connectionURL;
        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectionURL = "jdbc:jtds:sqlserver://"+ip+":"+port+"/"+ database;
            connection = DriverManager.getConnection(connectionURL, user, pass);
        }
        catch (Exception ex){
            Log.e("Error ", ex.getMessage());
        }
        return connection;
    }
}
