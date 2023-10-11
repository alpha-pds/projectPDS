package com.example.emergencyapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    Button btnHemorragia = (Button) findViewById(R.id.opcionHemorragia);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public Connection connectionDB(){
        Connection cnn = null;

        try{
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            cnn = DriverManager.getConnection("jdbc:jtds:sqlserver:// 192.168.1.14; databaseName=EmergencyDB; integratedSecurity=true");
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return cnn;
    }
    public void askHelpButton(View view){
        setContentView(R.layout.activity_questionary);
    }


    public void finishQuestionaryButton(View view){
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setMessage("¿Deseas terminar la ayuda?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        setContentView(R.layout.activity_main);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog title = alert.create();
        title.setTitle("Salida");
        title.show();
    }

    public void clickHemorragia(View view){
        try {
            Statement stm = connectionDB().createStatement();
            ResultSet rs = stm.executeQuery("SELECT*FROM PERSONAL WHERE EmergencyTypeId= '" + "f47ac10b-58cc-4372-a567-0e02b2c3d479"+"'");
            if(rs.next()){
                btnHemorragia.setText(rs.getString(3));
            }

        }catch(Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}