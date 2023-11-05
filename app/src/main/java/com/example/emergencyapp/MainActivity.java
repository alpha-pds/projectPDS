package com.example.emergencyapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.os.Bundle;

import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.emergencyapp.entities.EmergencyType;

import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Connection connect;
    String ConnectionResult = "";
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
    public void expand(View view, View object, LinearLayout layout){
        int v = (object.getVisibility() == View.GONE)? View.VISIBLE: View.GONE;

        TransitionManager.beginDelayedTransition(layout, new AutoTransition());
        object.setVisibility(v);
    }

    public void getTextFromSQL(View v) {
        LinearLayout parentLinearLayout = findViewById(R.id.optionsContainer);
        System.out.println("Funciona");
        if(!actionExecuted) {
            try {
                ConnectionHelper connectionHelper = new ConnectionHelper();
                connect = connectionHelper.connectionClass();
                if (connect != null) {
                    String query = "SELECT * FROM EmergencyType ";
                    Statement st = connect.createStatement();
                    ResultSet rs = st.executeQuery(query);
                    while (rs.next()) {
                        EmergencyType emergencyType = new EmergencyType();
                        emergencyType.setId(rs.getInt(1));
                        emergencyType.setTitle(rs.getString(2));
                        emergencyType.setDescription(rs.getString(3));
                        emergencyType.setImgUrl(rs.getString(4));
                        emergencyTypeList.add(emergencyType);
                    }
                }
            } catch (Exception ex) {
                ConnectionResult = "check connection";
            }
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
                card.setContentPadding(20,20,20,20);
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
                }
                if(item.getImgUrl() != null){
                        

                }
                button.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                button.setId(item.getId()+100);
                button.setVisibility(View.GONE);
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
        }
        actionExecuted = true;
    }


}