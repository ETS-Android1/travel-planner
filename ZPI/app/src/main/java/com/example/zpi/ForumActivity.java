package com.example.zpi;

import static com.example.zpi.ForumListActivity.THREAD_KEY;
import static com.example.zpi.SingleTripFragment.TRIP_KEY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.zpi.data_handling.BaseConnection;
import com.example.zpi.data_handling.SharedPreferencesHandler;
import com.example.zpi.models.Comment;
import com.example.zpi.models.ForumThread;
import com.example.zpi.models.Trip;
import com.example.zpi.models.User;
import com.example.zpi.repositories.CommentDao;
import com.example.zpi.repositories.ForumThreadDao;
import com.example.zpi.repositories.UserDao;
import com.j256.ormlite.dao.DaoManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class ForumActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Comment> commentsForThread=new ArrayList<>();
    ForumAdapter forumAdapter;
    User loggedUser;
    ForumThread current;
    TextView trip;
    TextView thread;
    Trip currentTrip;
    ConstraintLayout response;
    ImageButton respond;
    ImageButton addResponse;
    ImageButton cancel1;
    TextView respCount;
    TextView initials;
    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        recyclerView=findViewById(R.id.rv_comments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loggedUser = SharedPreferencesHandler.getLoggedInUser(getApplicationContext());
        trip=findViewById(R.id.tv_nameOfTrip1);
        thread=findViewById(R.id.tv_name2);

        current=(ForumThread) getIntent().getSerializableExtra(THREAD_KEY);
        thread.setText(current.getTitle());

        currentTrip=(Trip) getIntent().getSerializableExtra(TRIP_KEY);
        trip.setText(currentTrip.getName());

        response=findViewById(R.id.response_layout);
        //response.setVisibility(View.INVISIBLE);

        respond=findViewById(R.id.btn_respond);
        addResponse=findViewById(R.id.btn_add_response);
        cancel1=findViewById(R.id.btn_cancel1);

        //addResponse.setVisibility(View.INVISIBLE);
        //cancel1.setVisibility(View.INVISIBLE);
        hideResponseLayout();

        respCount=findViewById(R.id.tv_respCount);

        initials=findViewById(R.id.tv_initials3);
        name=findViewById(R.id.tv_personName3);
        initials.setText(loggedUser.getName().substring(0,1).toUpperCase()+loggedUser.getSurname().substring(0,1).toUpperCase());
        name.setText(loggedUser.getName()+" "+ loggedUser.getSurname());

        getCommentsForThread();
        getResponseCount();

        new Timer().scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){

                getCommentsForThread();
                getResponseCount();
            }
        }, 0, 60000);

    }

    public void hideResponseLayout(){
        response.setVisibility(View.INVISIBLE);
        addResponse.setVisibility(View.INVISIBLE);
        cancel1.setVisibility(View.INVISIBLE);
    }

    public void getCommentsForThread(){
        List<Comment> list=new ArrayList<>();
        new Thread(()->{
            try{
                ForumThreadDao ftdao=new ForumThreadDao(BaseConnection.getConnectionSource());
                UserDao udao=new UserDao(BaseConnection.getConnectionSource());

                List<Comment> coms=ftdao.getCommentsForThread(current);
                if(coms.size()!=0){
                    for(Comment c:coms){
                        list.add(c);
                        User u=c.getUser();
                        udao.refresh(u);
                    }
                }
                runOnUiThread(()->{
                    ForumAdapter adapter=new ForumAdapter(list);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                });
            }catch (SQLException throwables){
                throwables.printStackTrace();
            }
        }).start();
    }

    public void getResponseCount(){
        AtomicInteger resps = new AtomicInteger();
        new Thread(()->{
            try{
                ForumThreadDao ftdao=new ForumThreadDao(BaseConnection.getConnectionSource());
                resps.set(ftdao.getResponsesCount(current));
                runOnUiThread(()->{respCount.setText(String.valueOf(resps)+ " odpowiedzi");});
            }catch(SQLException throwables){
                throwables.printStackTrace();
            }
        }).start();
    }

    public void respond(View v){
        response.setVisibility(View.VISIBLE);
        addResponse.setVisibility(View.VISIBLE);
        cancel1.setVisibility(View.VISIBLE);
    }

    public void addResponse(View v){
        EditText content= findViewById(R.id.et_comment3);
        if(content!=null && !content.equals("")){
            new Thread(()->{
                try{
                    CommentDao cdao=new CommentDao(BaseConnection.getConnectionSource());
                    //Comment newComment=new Comment(content.getText().toString(), current, loggedUser);
                    cdao.create(new Comment(content.getText().toString(), current, loggedUser));
                    runOnUiThread(()->{
                        content.getEditableText().clear();
                        getCommentsForThread();
                        getResponseCount();
                        hideResponseLayout();
                    });
                }catch (SQLException throwables){
                    throwables.printStackTrace();
                }
            }).start();
        }
    }

    public void cancelResp(View v){
        response.setVisibility(View.INVISIBLE);
        addResponse.setVisibility(View.INVISIBLE);
        cancel1.setVisibility(View.INVISIBLE);
    }

    public void finishForum(View v){
        super.finish();
    }

    private class ForumAdapter extends RecyclerView.Adapter<ForumAdapter.ForumVh> {


        private List<Comment> comments;

        public ForumAdapter(List<Comment> list){
            this.comments=list;
        }

        @NonNull
        @Override
        public ForumAdapter.ForumVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
            View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_in_forum,parent, false);
            return new ForumVh(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ForumVh holder, int position){
            Comment comment=comments.get(position);
            User u=comment.getUser();
            new Thread(()->{
                try {
                    UserDao udao=new UserDao(BaseConnection.getConnectionSource());
                    udao.refresh(u);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }).start();


            String personInitials=u.getName().substring(0,1).toUpperCase()+u.getSurname().substring(0,1).toUpperCase();
            String personName=u.getName()+ " "+u.getSurname();
            holder.initials.setText(personInitials);
            holder.person.setText(personName);
            holder.comment.setText(comment.getContent());
        }

        @Override
        public int getItemCount(){
            return comments.size();
        }

        private class ForumVh extends RecyclerView.ViewHolder{
            TextView comment;
            TextView initials;
            TextView person;

            public ForumVh(@NonNull View itemView){
                super(itemView);
                comment=itemView.findViewById(R.id.tv_comment);
                initials=itemView.findViewById(R.id.tv_initials1);
                person=itemView.findViewById(R.id.tv_personName1);
            }
        }
    }
}