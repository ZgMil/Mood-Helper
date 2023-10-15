package com.example.therapyapp.CardPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Spinner;
import com.example.therapyapp.R;

import java.util.ArrayList;

public class CardsActivity extends AppCompatActivity {
    private DBCardHandler dbHandler;
    private ArrayList<CardModal> cardModalArrayList;
    private ArrayList<CardModal> selectedarray;

    private Spinner groupsspinner;
    private TextView cardtxt;
    private int showncard;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards);

        Button newgroupbtn = findViewById(R.id.new_group_menu_open_btn);
        ImageButton nextbtn = findViewById(R.id.btn_next);
        ImageButton prevbtn = findViewById(R.id.btn_previous);
        Button addgroupbtn = findViewById(R.id.add_group_btn);
        EditText groupname = findViewById(R.id.group_name_edt);
        ImageButton addcardbtn = findViewById(R.id.add_card_btn);
        Button donecardbtn = findViewById(R.id.save_card_btn_creation);
        Button delcardbtn = findViewById(R.id.delete_btn_card);
        Button editcardbtn = findViewById(R.id.edit_card_btn);
        Button saveeditcard = findViewById(R.id.save_card_btn_editing);
        Button deletegroupbtn = findViewById(R.id.delete_group_card);
        Button editgroupbtn = findViewById(R.id.edit_group_menu_open_btn);
        Button saveeditgroupbtn = findViewById(R.id.edit_group_btn);
        cardtxt = findViewById(R.id.cardText);
        EditText cardtextedt = findViewById(R.id.card_text_edit);
        View groupnamecontainer = findViewById(R.id.group_name_layout);
        View cardeditcontainer = findViewById(R.id.card_edit_layout);
        groupsspinner = findViewById(R.id.groups_spinner);
        donecardbtn.setVisibility(View.GONE);
        saveeditcard.setVisibility(View.GONE);
        saveeditgroupbtn.setVisibility(View.GONE);
        cardeditcontainer.setVisibility(View.GONE);
        groupnamecontainer.setVisibility(View.GONE);
        dbHandler = new DBCardHandler(CardsActivity.this);
        cardModalArrayList = dbHandler.readEntries();
        if(!cardModalArrayList.isEmpty()) {
            addGroups();
            selectedarray = loadgroupedcards();
            updatecardshow();
        } else cardtxt.setText(R.string.create_new_group);
        groupsspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                updatecardshow();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedarray!=null){
                    if(selectedarray.size()-1>showncard){
                        showncard++;
                        cardtxt.setText(selectedarray.get(showncard).getText());
                    }
                }
            }
        });
        prevbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedarray!=null){
                    if(0<showncard) {
                        showncard--;
                        cardtxt.setText(selectedarray.get(showncard).getText());
                    }
                }

            }
        });

        newgroupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (groupnamecontainer.getVisibility() == View.VISIBLE) {
                    groupnamecontainer.setVisibility(View.GONE);
                    editgroupbtn.setVisibility(View.VISIBLE);
                    deletegroupbtn.setVisibility(View.VISIBLE);
                } else if (groupsspinner.getChildCount() < 7){
                    groupnamecontainer.setVisibility(View.VISIBLE);
                    editgroupbtn.setVisibility(View.GONE);
                    deletegroupbtn.setVisibility(View.GONE);
                }
            }
        });
        addgroupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!groupname.getText().toString().equals("")){
                    String groupnamestr;
                    groupnamestr=groupname.getText().toString();
                    dbHandler.addNewEntry(groupnamestr,getString(R.string.add_cards_to_the_group));
                    cardModalArrayList = dbHandler.readEntries();
                    addGroups();
                    groupnamecontainer.setVisibility(View.GONE);
                    editgroupbtn.setVisibility(View.VISIBLE);
                    deletegroupbtn.setVisibility(View.VISIBLE);
                    groupname.setText("");
                }else Toast.makeText(CardsActivity.this, R.string.field_is_empty,Toast.LENGTH_SHORT).show();
            }
        });
        addcardbtn.setOnClickListener(new View.OnClickListener() {//////////////
            @Override
            public void onClick(View v) {
                if (cardeditcontainer.getVisibility() == View.VISIBLE) {
                    donecardbtn.setVisibility(View.GONE);
                    delcardbtn.setVisibility(View.VISIBLE);
                    cardeditcontainer.setVisibility(View.GONE);
                    editcardbtn.setVisibility(View.VISIBLE);
                    nextbtn.setVisibility(View.VISIBLE);
                    prevbtn.setVisibility(View.VISIBLE);
                } else if(groupsspinner.getSelectedItemPosition()!=-1){
                    cardeditcontainer.setVisibility(View.VISIBLE);
                    donecardbtn.setVisibility(View.VISIBLE);
                    delcardbtn.setVisibility(View.GONE);
                    editcardbtn.setVisibility(View.GONE);
                    nextbtn.setVisibility(View.GONE);
                    prevbtn.setVisibility(View.GONE);
                    cardtextedt.setText("");
                }
            }
        });
        donecardbtn.setOnClickListener(new View.OnClickListener() {////////////
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                String newcardstr;
                newcardstr = cardtextedt.getText().toString();
                if(!newcardstr.isEmpty()){
                    if(selectedarray.size()==1 && selectedarray.get(0).getText().equals(getString(R.string.add_cards_to_the_group))){
                        dbHandler.updatecard(cardtextedt.getText().toString(),selectedarray.get(showncard).getId());
                        updatecardshow();
                    } else {
                        dbHandler.addNewEntry(groupsspinner.getSelectedItem().toString(),newcardstr);
                    }
                    cardtextedt.setText("");
                    cardeditcontainer.setVisibility(View.GONE);
                    donecardbtn.setVisibility(View.GONE);
                    delcardbtn.setVisibility(View.GONE);
                    updatecardshow();
                }else{
                    Toast.makeText(CardsActivity.this, R.string.field_is_empty,Toast.LENGTH_SHORT).show();
                }
                donecardbtn.setVisibility(View.GONE);
                delcardbtn.setVisibility(View.VISIBLE);
                cardeditcontainer.setVisibility(View.GONE);
                editcardbtn.setVisibility(View.VISIBLE);
                nextbtn.setVisibility(View.VISIBLE);
                prevbtn.setVisibility(View.VISIBLE);
            }
        });
        delcardbtn.setOnClickListener(new View.OnClickListener() {////////////
            @Override
            public void onClick(View v) {
                    if(selectedarray.size()==1){
                        dbHandler.updatecard(getString(R.string.add_cards_to_the_group),selectedarray.get(showncard).getId());
                        Toast.makeText(CardsActivity.this, getString(R.string.deleted_successfully),Toast.LENGTH_SHORT).show();
                        updatecardshow();
                    } else{
                        dbHandler.deleteCard(selectedarray.get(showncard).getId());
                        finish();
                        startActivity(getIntent());
                    }
                }
        });
        editcardbtn.setOnClickListener(new View.OnClickListener() {////////////////////////////
            @Override
            public void onClick(View v) {
                cardeditcontainer.setVisibility(View.VISIBLE);
                saveeditcard.setVisibility(View.VISIBLE);
                delcardbtn.setVisibility(View.GONE);
                editcardbtn.setVisibility(View.GONE);
                cardtextedt.setText(selectedarray.get(showncard).getText());
                nextbtn.setVisibility(View.GONE);
                prevbtn.setVisibility(View.GONE);
            }
        });
        saveeditcard.setOnClickListener(new View.OnClickListener() {////////////////////
            @Override
            public void onClick(View v) {
                if(!cardtextedt.getText().toString().isEmpty()){
                    dbHandler.updatecard(cardtextedt.getText().toString(),selectedarray.get(showncard).getId());
                    updatecardshow();
                    nextbtn.setVisibility(View.VISIBLE);
                    prevbtn.setVisibility(View.VISIBLE);
                    cardeditcontainer.setVisibility(View.GONE);
                    saveeditcard.setVisibility(View.GONE);
                    delcardbtn.setVisibility(View.VISIBLE);
                    editcardbtn.setVisibility(View.VISIBLE);
                }else Toast.makeText(CardsActivity.this, R.string.field_is_empty,Toast.LENGTH_SHORT).show();
            }
        });
        deletegroupbtn.setOnClickListener(new View.OnClickListener() {//////////////////
            @Override
            public void onClick(View v) {
                dbHandler.deleteGroup(selectedarray.get(showncard).getGroup());
                finish();
                startActivity(getIntent());
            }
        });
        editgroupbtn.setOnClickListener(new View.OnClickListener() {//////////////
            @Override
            public void onClick(View v) {
                    if(saveeditgroupbtn.getVisibility()==View.GONE){
                        groupnamecontainer.setVisibility(View.VISIBLE);
                        addgroupbtn.setVisibility(View.GONE);
                        saveeditgroupbtn.setVisibility(View.VISIBLE);
                        newgroupbtn.setVisibility(View.GONE);
                        groupname.setText(selectedarray.get(showncard).getGroup());
                    } else{
                        groupnamecontainer.setVisibility(View.GONE);
                        addgroupbtn.setVisibility(View.VISIBLE);
                        saveeditgroupbtn.setVisibility(View.GONE);
                        newgroupbtn.setVisibility(View.VISIBLE);
                        groupname.setText("");
                    }
            }
        });
        saveeditgroupbtn.setOnClickListener(new View.OnClickListener() {///////////////
            @Override
            public void onClick(View v) {
                if(!selectedarray.get(showncard).getGroup().isEmpty()){
                    dbHandler.updateGroup(selectedarray.get(showncard).getGroup(),groupname.getText().toString());
                    finish();
                    startActivity(getIntent());
                    groupname.setText("");
                }else Toast.makeText(CardsActivity.this, R.string.field_is_empty,Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void addGroups() {////////////////
        ArrayList<String> groups = new ArrayList<String>();
        for (int i = 0; i < cardModalArrayList.size(); i++) {
            if(!groups.contains(cardModalArrayList.get(i).getGroup())) {
                groups.add(cardModalArrayList.get(i).getGroup());
            }
        }
        ArrayAdapter<String> adp = new ArrayAdapter<String> (this,android.R.layout.simple_spinner_dropdown_item,groups);
        groupsspinner.setAdapter(adp);
        groupsspinner.setVisibility(View.VISIBLE);
    }
    private ArrayList<CardModal> loadgroupedcards(){////////////////////
        String selectedgroup = groupsspinner.getSelectedItem().toString();
        ArrayList<CardModal> selectedcardModalArrayList = new ArrayList<>();
        for (int i = 0; i < cardModalArrayList.size(); i++) {
            if (cardModalArrayList.get(i).getGroup().equals(selectedgroup)) {
                selectedcardModalArrayList.add(cardModalArrayList.get(i));
            }
        }
        return selectedcardModalArrayList;
    }
    private void updatecardshow(){////////////////////
        showncard=0;
        cardModalArrayList.clear();
        cardModalArrayList = dbHandler.readEntries();
            selectedarray = loadgroupedcards();
            cardtxt.setText(selectedarray.get(showncard).getText());
    }
}