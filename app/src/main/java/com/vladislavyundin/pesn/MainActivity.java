package com.vladislavyundin.pesn;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    List<HashMap<String, String>> aList;
    SimpleAdapter simpleAdapter;
    ListView androidListView;
    SubMenu subMenu;
    SubMenu subMenu2;
    ArrayList <String> listAuthor;
    ArrayList <String> listTrack;
    ArrayList <Integer> Category;
    ArrayList <String> listOfCategories;

    String[] arrayAuthor = new String[]{
            "Noize MC", "Лагерная", "РБС", "Валентин Стрыкало",
            "Noize MC", "Сплин", "Лагерная", "Нервы",
            "Лагерная", "5'nizza", "5'nizza", "Валентин Стрыкало",
            "Валентин Стрыкало", "Валентин Стрыкало", "Валентин Стрыкало", "Бумбокс",
            "Бабкин", "Лагерная", "Лагерная", "5'nizza",
            "Люмен", "Валентин Стрыкало", "Валентин Стрыкало", "Сплин",
            "Би-2", "5'nizza", "Зарисовка", "Лагерная",
            "5'nizza", "Нервы", "Brainstorm", "Валентин Стрыкало",
            "ДДТ", "Валентин Стрыкало", "Ляпис Трубецкой", "Animal ДжаZ",
            "ATL", "Никита Ермалюк", "Сергей Есенин", "5'nizza"
    };


    String[] arrayTrack = new String[]{
            "Жадина", "Рассвет-закат", "Мы встретились на РБС...", "Гори",
            "Бассейн", "Мое сердце", "Щеночка", "Ярче и теплее",
            "Запоминай день", "Ты кидал", "Некино", "На кайене",
            "Фанк", "Все решено", "Космос нас ждет", "Та что",
            "Забери", "Дом", "Белая гвардия", "Нева",
            "Сид и Ненси", "Песня для девочек", "Сережа", "Прочь из моей головы",
            "Мы не ангелы", "Весна", "Моя Барселона", "Все расстояния",
            "Я солдат", "Батареи", "Ветер", "Колыбельная",
            "Летели облака", "Рустем", "Я верю", "Три полоски",
            "За Русь", "Один момент", "Письмо к женщине", "Ты кидал (меедленно)"
    };

    int[] arrayCategory = new int[]{
            0, 0, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0,
            0, 0, 1, 1,
            1, 1, 1, 1,
            1, 1, 1, 1,
            1, 1, 2, 2,
            2, 2, 2, 2,
            2, 2, 3, 3,
            3, 3, 3, 3
    };

    String[] arrayOfCategories = new String[]{
        "Веселые", "Грустные", "\"Не грустные\"", "Особенные"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initArrays();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Menu menu = navigationView.getMenu();
        subMenu = menu.addSubMenu(R.id.main_menu, Menu.NONE, 1, "Категории");
        //subMenu.setIcon(R.drawable.ic_blur_on_black_24dp);

        for (int i = 0; i < listOfCategories.size(); i++){
            subMenu.add(Menu.NONE, i, 1, listOfCategories.get(i)).setCheckable(true);
        }

        subMenu2 = menu.addSubMenu(R.id.main_menu, Menu.NONE, 2, "Исполнители");
        //subMenu2.setIcon(R.drawable.ic_mic_black_24dp);
        ArrayList <String> passed = new ArrayList<>();
        for (int i = 0; i < listAuthor.size(); i++){
            int j;
            for (j = 0; j < passed.size(); j++){
                if (passed.get(j).equals(listAuthor.get(i)))
                    break;
            }
            if (j == passed.size()){
                subMenu2.add(Menu.NONE, Menu.NONE, 2, listAuthor.get(i)).setCheckable(true);
                passed.add(listAuthor.get(i));
            }
        }

        initRecyclerView(-1);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.all) {
            // Handle the camera action
            initRecyclerView(-1);
        } else if (id == R.id.add) {
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            Menu menu = navigationView.getMenu();
            subMenu.add(R.id.group, listOfCategories.size() + 1, 1, "Новая категория").setCheckable(true);
            return true;
        } else if (id == 0 && !item.getTitle().equals(listOfCategories.get(0))){
            initRecyclerViewByAuthor(item.getTitle().toString());
        } else {
            initRecyclerView(id);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void initRecyclerView(int id){

        aList = new ArrayList<HashMap<String, String>>();

        for (int i = -1; i < listAuthor.size(); i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            if(i == -1){
                hm.put("item_title", "Рандомить здесь");
                hm.put("listview_image", Integer.toString(R.drawable.ic_shuffle_black_24dp));
                aList.add(hm);
            }
            else {
                if (id == -1) {
                    hm.put("listview_title", listAuthor.get(i));
                    hm.put("listview_discription", listTrack.get(i));
                    aList.add(hm);
                }
                else if (Category.get(i) == id){
                    hm.put("listview_title", listAuthor.get(i));
                    hm.put("listview_discription", listTrack.get(i));
                    aList.add(hm);
                }
            }
        }

        String[] from = {"listview_image", "listview_title", "listview_discription", "item_title"};
        int[] to = {R.id.listview_image, R.id.listview_item_title, R.id.listview_item_short_description, R.id.add_item_title};

        simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.recycler_item, from, to);
        androidListView = (ListView) findViewById(R.id.list_view);
        androidListView.setAdapter(simpleAdapter);
    }

    public void initRecyclerViewByAuthor(String name){

        aList = new ArrayList<HashMap<String, String>>();

        for (int i = -1; i < listAuthor.size(); i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            if(i == -1){
                hm.put("item_title", "Рандомить здесь");
                hm.put("listview_image", Integer.toString(R.drawable.ic_shuffle_black_24dp));
                aList.add(hm);
            }
            else {
                if (listAuthor.get(i).equals(name)){
                    hm.put("listview_title", listAuthor.get(i));
                    hm.put("listview_discription", listTrack.get(i));
                    aList.add(hm);
                }
            }
        }

        String[] from = {"listview_image", "listview_title", "listview_discription", "item_title"};
        int[] to = {R.id.listview_image, R.id.listview_item_title, R.id.listview_item_short_description, R.id.add_item_title};

        simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.recycler_item, from, to);
        androidListView = (ListView) findViewById(R.id.list_view);
        androidListView.setAdapter(simpleAdapter);
    }

    private void initArrays(){

        listAuthor = new ArrayList<>();
        for (int i = 0; i < arrayAuthor.length; i++){
            listAuthor.add(arrayAuthor[i]);
        }

        listTrack = new ArrayList<>();
        for (int i = 0; i < arrayTrack.length; i++){
            listTrack.add(arrayTrack[i]);
        }

        Category = new ArrayList<>();
        for (int i = 0; i < arrayCategory.length; i++){
            Category.add(arrayCategory[i]);
        }

        listOfCategories = new ArrayList<>();
        for (int i = 0; i < arrayOfCategories.length; i++){
            listOfCategories.add(arrayOfCategories[i]);
        }
    }
}
