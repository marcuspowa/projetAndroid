package com.example.remy.mmsongquizz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.UI.GenreAdapter;
import com.example.remy.mmsongquizz.R;

import java.util.ArrayList;

import models.Genre;
import services.GenreManager;
import services.UserManager;
import utils.Logger;

public class GenreActivity extends BaseActivity {

    private GenreManager genreManager;
    private UserManager userManager;

    private Button submitBtn;
    private ListView genreListView;
    private boolean hasChanged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre);

        checkNetwork();

        genreManager = application.getContainer().get(GenreManager.class);
        userManager = application.getContainer().get(UserManager.class);

        submitBtn = (Button) findViewById(R.id.genre_submit);
        genreListView = (ListView) findViewById(R.id.genre_list);

        hasChanged = false;

        initView();
    }

    private void initView(){
        ArrayList<Genre> genres = genreManager.getAll();

        genreListView.setAdapter(new GenreAdapter(this, android.R.layout.simple_list_item_multiple_choice, genres.toArray(new Genre[genres.size()])));

        for (int i = 0; i < genreListView.getAdapter().getCount(); i++) {
            Genre genreInList = (Genre)genreListView.getAdapter().getItem(i);
            for(Genre genre : userManager.getCurrentUser().getPreferedGenres()){
                if(genreInList.getName().equals(genre.getName())){
                    genreListView.setItemChecked(i, true);
                    break;
                }
            }
        }

        genreListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hasChanged = true;
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hasChanged){
                    ArrayList<Genre> selectedGenres = getSelectedGenres();
                    userManager.getCurrentUser().setPreferedGenres(selectedGenres);
                    userManager.setCurrentUser(userManager.getCurrentUser(), true);
                    userManager.updateUser(userManager.getCurrentUser());
                    application.notify("Genres mis à jour");
                }

                Intent toHome = new Intent(GenreActivity.this, MainActivity.class);
                startActivity(toHome);
            }
        });

    }

    private ArrayList<Genre> getSelectedGenres(){
        ArrayList<Genre> selectedGenres = new ArrayList<Genre>();
        SparseBooleanArray checked = genreListView.getCheckedItemPositions();
        for (int i = 0; i < genreListView.getAdapter().getCount(); i++) {
            if (checked.get(i)) {
                Genre genre = (Genre)genreListView.getAdapter().getItem(i);
                Logger.debug("CHECKED " + genre.getName());
                selectedGenres.add(genre);
            }
        }
        return selectedGenres;
    }

}
