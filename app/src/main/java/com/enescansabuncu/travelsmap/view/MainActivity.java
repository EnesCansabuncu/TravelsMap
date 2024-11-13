package com.enescansabuncu.travelsmap.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import com.enescansabuncu.travelsmap.R;
import com.enescansabuncu.travelsmap.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import adapter.PlaceAdapter;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import roomdb.PlaceDao;
import roomdb.PlaceDatabase;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    PlaceDatabase db;
    PlaceDao placeDao;
 private CompositeDisposable compositeDisposable=new CompositeDisposable();
ArrayList<Place > places;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        places=new ArrayList<>();
      PlaceDatabase db= Room.databaseBuilder(MainActivity.this,PlaceDatabase.class,"Places").allowMainThreadQueries().build();
        placeDao=db.placeDao();
        compositeDisposable.add(placeDao.getAll().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(MainActivity.this::handleResponse));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }
    private void handleResponse(List<Place> placeList){
binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        PlaceAdapter placeAdapter=new PlaceAdapter(placeList);
        binding.recyclerView.setAdapter(placeAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.travel_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.add_place){
            Intent intent=new Intent(MainActivity.this, MapsActivity.class);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}