package com.example.emergencyapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {
    Connection connect;
    String ConnectionResult = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void askHelpButton(View view){
        setContentView(R.layout.activity_questionary);
    }
    public void finishQuestionaryButton(View view)
    {
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

    public void getTextFromSQL(View v)
    {
        Button tx1 = (Button) findViewById(R.id.opcionHemorragia);
        System.out.println("Funciona");
        tx1.setText("Hola");
        try
        {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionClass();
            if(connect != null)
            {
                String query = "SELECT * FROM EmergencyType WHERE emergencyTypeId=1";
                Statement st = connect.createStatement();
                ResultSet rs = st.executeQuery(query);
                if (rs.next())
                {
                    tx1.setText(rs.getString(2));
                }
                else{
                    tx1.setText("Conexión invalida");
                }
            }
            else{
                tx1.setText("conexion invalida 2");
            }
        }
        catch (Exception ex)
        {
            ConnectionResult = "check connection";
        }
    }

    /*public void clickHemorragia(View view){
        try {
            Statement stm = connectionDB().createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM EmergencyType WHERE EmergencyTypeId='12345678-1234-1234-1234-123456789012'");
            if(rs.next()){
                btnHemorragia.setText("hola");
            }
            else{
                btnHemorragia.setText("adios");
            }

        }catch(Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }*/

}