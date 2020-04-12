package com.example.blood;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.annotations.NotNull;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class List_Of_Users extends AppCompatActivity {
    ListView lv;
    FirebaseListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list__of__users);
        lv=findViewById(R.id.list_view);
        Query query= FirebaseDatabase.getInstance().getReference().orderByChild("blood").equalTo("O+");
        FirebaseListOptions<user_information> options=new FirebaseListOptions.Builder<user_information>()
                .setLifecycleOwner(this)
                .setLayout(R.layout.user_list)
                .setQuery(query,user_information.class).build();
        adapter=new FirebaseListAdapter(options) {
            @Override
            protected void populateView(View v, Object model, int position) {
                TextView name=v.findViewById(R.id.list_name);
                TextView phone=v.findViewById(R.id.list_phone);
                TextView age=v.findViewById(R.id.list_age);
                TextView gender=v.findViewById(R.id.list_gender);
                TextView adress=v.findViewById(R.id.list_adress);
                TextView blood=v.findViewById(R.id.list_blood);
                TextView userID=v.findViewById(R.id.userID);
                final ImageView prof=v.findViewById(R.id.list_dp);
                TextView email=v.findViewById(R.id.list_email);
                final user_information users=(user_information)model;
                //calculation part if user is pressed near by options
                Picasso.get().load(users.getUrl()).networkPolicy(NetworkPolicy.OFFLINE).into(prof, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Picasso.get().load(users.getUrl()).into(prof);
                    }
                });
                name.setText("Name:"+users.getName());
                adress.setText(users.getAdress());
                email.setText(users.getEmail());
                phone.setText(users.getPhone());
                age.setText("Age:"+users.getAge());
                gender.setText("Gender:"+users.getGender());
                blood.setText("Blood Group:"+users.getBlood());
                userID.setText(users.getUserid());


            }
        };
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //call next page and send this user id
                String userid=String.valueOf(((TextView)view.findViewById(R.id.userID)).getText());
                Toast.makeText(List_Of_Users.this,((TextView)view.findViewById(R.id.userID)).getText(),Toast.LENGTH_LONG).show();
            }
        });
    }

}
