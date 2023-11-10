package com.example.emergencyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.Statement;

public class RegisterActivity extends AppCompatActivity {
    EditText name, lastName, email, password;
    Button registerButton;
    TextView status;
    Connection con;
    Statement stmt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        name = (EditText) findViewById(R.id.editName);
        lastName = (EditText) findViewById(R.id.editLastName);
        email = (EditText) findViewById(R.id.editEmail);
        password = (EditText) findViewById(R.id.editPassword);
        registerButton = (Button) findViewById(R.id.loginBtn);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();

            }
        });
    }

    public String registerUser() //extends AsyncTask<String, String, String>
    {

        String z = "";
        //Boolean isSuccesful = false;
        /*@Override
        protected void onPreExecute(){
            status.setText("Sending data to Database");
        }*/

        /*@Override
        protected void onPostExecute(String s){
            status.setText("Registration succesful");

        }
        */
        //@Override
        //protected String doInBackground(String... strings) {
        try{
            ConnectionHelper connection = new ConnectionHelper();
            con = connection.connectionClass();
            if (con==null) {
                z = "Check your internet connection";
            }
            else{
                String sql = "INSERT INTO [User] ( name, lastName, email, password) VALUES ('"+ name.getText()+"','"+lastName.getText()+"','"+email.getText()+"','"+ password.getText()+"')";
                stmt = con.createStatement();
                stmt.executeUpdate(sql);
            }
        }catch (Exception e){
            //isSuccesful = false;
            System.out.println(e);
            return e.getMessage();
        }
        System.out.println("funciona");
        //}
        name.setText("");
        lastName.setText("");
        email.setText("");
        password.setText("");
        setContentView(R.layout.activity_log_in);
        return z;
    }
}