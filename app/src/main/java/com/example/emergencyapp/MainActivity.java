package com.example.emergencyapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.emergencyapp.entities.EmergencyType;
import com.example.emergencyapp.entities.Response;

import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String ConnectionResult = "";
    final ConnectionHelper connectionHelper = new ConnectionHelper();
    Connection connect = connectionHelper.connectionClass();
    List<EmergencyType> emergencyTypeList = new ArrayList<>();
    boolean actionExecuted = false;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void askHelpButton(View view) {
        setContentView(R.layout.activity_questionary);
        getTextFromSQL(view);
    }

    public void finishQuestionaryButton(View view) {
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
    public void sendQuestionaryButton(View view, EmergencyType emergencyType) {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setMessage("Desea dar información sobre los involucrados")
            .setCancelable(false)
            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    setContentView(R.layout.activity_pacient_info);
                    sendEmergencySQL(view, emergencyType);
                }
            })
            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    try {
                        if(connect != null){
                            String query = "INSERT INTO Emergency ( emergencyTypeId, creationDateTime) VALUES ("+ emergencyType.getId() +",'"+ LocalDateTime.now()+"')";
                            Statement stmt = connect.createStatement();
                            stmt.executeUpdate(query);
                        }
                    }
                    catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    setContentView(R.layout.activity_main);
                }
            });
        AlertDialog title = alert.create();
        title.setTitle("Salida");
        title.show();
    }
    public void expand(View view, View object, LinearLayout layout){
        int v = (object.getVisibility() == View.GONE)? View.VISIBLE: View.GONE;

        TransitionManager.beginDelayedTransition(layout, new AutoTransition());
        object.setVisibility(v);
    }

    public void sendEmergencySQL(View v, EmergencyType emergencyType){
        Button confirmBtn = findViewById(R.id.sendBtn);
        confirmBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                EditText affected = findViewById(R.id.editTxtAffected);
                EditText helper = findViewById(R.id.editTxtHelper);
                EditText informant = findViewById(R.id.editTxtInformant);
                try {
                    if (connect != null) {

                        String query = "INSERT INTO Emergency ( emergencyTypeId, creationDateTime, reporterName, patientName, assistantName) VALUES (" + emergencyType.getId() + ",'" + LocalDateTime.now() + "','" + informant.getText() + "','" + affected.getText() + "','" + helper.getText() + "')";
                        Statement stmt = connect.createStatement();
                        stmt.executeUpdate(query);
                    }
                }
                catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                setContentView(R.layout.activity_main);
            }
        });
    }

    public void getTextFromSQL(View v) {
        LinearLayout parentLinearLayout = findViewById(R.id.optionsContainer);
        System.out.println("Funciona");
        try {
            if (connect != null) {
                String query = "SELECT * FROM EmergencyType ";
                Statement st = connect.createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next() && !actionExecuted) {
                    EmergencyType emergencyType = new EmergencyType();
                    emergencyType.setId(rs.getInt(1));
                    String queryResponses = "SELECT * FROM Response INNER JOIN EmergencyT_Response ON Response.responseId = EmergencyT_Response.responseId WHERE EmergencyT_Response.emergencyTypeId="+ rs.getInt(1);
                    ResultSet rsResponses = connect.createStatement().executeQuery(queryResponses);
                    while(rsResponses.next()){
                        Response response = new Response();
                        response.setId(rsResponses.getInt(1));
                        response.setPriority(rsResponses.getInt(5));
                        response.setDescription(rsResponses.getString(3));
                        response.setTitle(rsResponses.getString(2));
                        response.setUrlImg(rsResponses.getString(4));
                        emergencyType.responses.add(response);
                    }
                    emergencyType.setTitle(rs.getString(2));
                    emergencyType.setDescription(rs.getString(3));
                    emergencyType.setImgUrl(rs.getString(4));
                    emergencyTypeList.add(emergencyType);
                }
            }
        } catch (Exception ex) {
            ConnectionResult = "check connection";
        }
        if(emergencyTypeList != null) {
            for (EmergencyType item : emergencyTypeList) {
                CardView card = new CardView(this);
                LinearLayout innerLinearLayout = new LinearLayout(this);
                TextView textViewTitle = new TextView(this);
                TextView textViewDescription = new TextView(this);
                ImageView imageView = new ImageView(this);
                Button button = new Button(this);
                card.setId(item.getId()+200);
                card.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT)
                );
                card.setBackgroundResource(R.drawable.card_option);
                card.setContentPadding(40,40,40,40);
                innerLinearLayout.setId(item.getId());
                innerLinearLayout.setOrientation(LinearLayout.VERTICAL);
                card.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT)
                );
                textViewTitle.setText(item.getTitle());
                textViewTitle.setTextColor(getResources().getColor(R.color.black10));
                if(item.getDescription() != null){
                    textViewDescription.setText(item.getDescription());
                    textViewDescription.setTextColor(getResources().getColor(R.color.black10));
                }
                if(item.getImgUrl() != null){
                    byte[] bytes = Base64.decode(item.getImgUrl(), Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    imageView.setImageBitmap(bitmap);
                }
                button.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                button.setId(item.getId()+100);
                button.setVisibility(View.GONE);
                textViewDescription.setVisibility(View.GONE);
                imageView.setId(item.getId()+300);
                imageView.setVisibility(View.GONE);
                textViewDescription.setId(item.getId()+400);
                textViewDescription.setVisibility(View.GONE);
                textViewTitle.setId(item.getId()+500);
                button.setText("Continuar");
                innerLinearLayout.addView(textViewTitle);
                innerLinearLayout.addView(textViewDescription);
                innerLinearLayout.addView(imageView);
                innerLinearLayout.addView(button);
                card.addView(innerLinearLayout);
                parentLinearLayout.addView(card);
            }
        }
        for(int id = 1; id<=emergencyTypeList.size(); id++ ) {
            LinearLayout linearLayout = (LinearLayout) findViewById(id);
            CardView cardView = (CardView) findViewById(id+200);
            Button btn = (Button) findViewById(id+100);
            ImageView imgView = (ImageView) findViewById(id+300);
            TextView txtView = (TextView) findViewById(id+400);
            ((LinearLayout.LayoutParams) cardView.getLayoutParams()).setMargins(0, 50, 0, 0);
            cardView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    expand(v, btn, linearLayout);
                    expand(v, imgView, linearLayout);
                    expand(v, txtView, linearLayout);
                }
            });
            int finalId = id-1;
            btn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    System.out.println(emergencyTypeList.get(finalId).responses.get(0).getTitle());
                    setContentView(R.layout.activity_response);
                    getResponse(view, emergencyTypeList.get(finalId), 0);
                }
            });
        }
        actionExecuted = true;
    }
    public void getResponse(View v, EmergencyType emergencyType, int  count){
        if(emergencyType != null){
            ArrayList<Response> responses = emergencyType.responses;
            Response response = responses.get(count);
            TextView textView = findViewById(R.id.textView);
            TextView textView2 = findViewById(R.id.textView2);
            textView.setText(response.getTitle());
            textView2.setText(response.getDescription());
            Button btn = findViewById(R.id.btnContinue);
            int counta = count+1;
            if(counta != (responses.size()-1)){
                btn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        getResponse(view, emergencyType, counta);
                    }
                });
            }
            else{
                btn.setText("Enviar");
                btn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        sendQuestionaryButton(view, emergencyType);
                    }
                });
            }

        }
    }
}