package com.example.registrarse;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class RegisterActivity extends AppCompatActivity {

    EditText editName, editEmail, editPassword;
    Button registerButton;
    TextView status;
    Connection con;

    Statement stmt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        editName = (EditText) findViewById(R.id.editName);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editPassword = (EditText) findViewById(R.id.editPassword2);
        status = (TextView) findViewById(R.id.status);
        registerButton = (Button) findViewById(R.id.loginBtn);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new RegisterActivity.registerUser().execute("");

            }
        });
    }

    @SuppressLint("NewApi")
    public Connection connectionClass(String user, String password, String database, String server){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String connectionURL = null;

        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectionURL = "jdbc:jtds:sqlserver//"+server+"/"+database+";user=" +user+";password="+password+";";
            connection = DriverManager.getConnection(connectionURL);

        } catch (Exception e){
            Log.e("SQL Connection Error: ", e.getMessage());
        }
        return connection;
    }

    public class registerUser extends AsyncTask<String, String, String>{

        String z = "";
        Boolean isSuccesful = false;

        @Override
        protected void onPreExecute(){
            status.setText("Sending data to Database");
        }

        @Override
        protected void onPostExecute(String s){
            status.setText("Registration succesful");
            editName.setText("");
            editEmail.setText("");
            editPassword.setText("");
            setContentView(R.layout.activity_log_in);
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                ConnectionHelper connection = new ConnectionHelper();
                con = connection.connectionClass();
                if (con==null) {
                    z = "Check your internet connection";
                }
                    else{
                        String sql = "INSERT INTO Register (name,email,password) VALUES ('"+editName.getText()+"','"+editEmail.getText()+"','"+editPassword.getText()+"')";
                        stmt = con.createStatement();
                        stmt.executeUpdate(sql);
                    }
            }catch (Exception e){
                isSuccesful = false;
                z = e.getMessage();

            }
            return z;
        }
    }
}