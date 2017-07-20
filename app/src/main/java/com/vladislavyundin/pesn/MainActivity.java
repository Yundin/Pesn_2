package com.vladislavyundin.pesn;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import java.util.*;

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
    Toolbar toolbar;
    NavigationView navigationView;
    public static final String APP_PREFERENCES = "mysettings";
    SharedPreferences mSettings;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initArrays();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FabClick();
            }
        });

        initNavDrawer();

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
            if(getCheckedItem() == -1){
                Snackbar.make(findViewById(R.id.fab), "Удалять можно только категории", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
            else {
                AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);
                ad.setTitle("Удалить?");
                ad.setMessage("Все песни категории тоже удалятся");
                ad.setPositiveButton("Окок", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int i) {

                        String cat = subMenu.getItem(getCheckedItem()).getTitle().toString();

                        int cat_num = listOfCategories.indexOf(cat);

                        listOfCategories.remove(cat);

                        for (int num = 0; num < Category.size(); num++){
                            if (Category.get(num) == cat_num){
                                listAuthor.remove(num);
                                listTrack.remove(num);
                                Category.remove(num);
                                num--;
                            }
                        }

                        initRecyclerView(-1);
                        toolbar.setTitle("Все");
                        initNavDrawer();

                        Snackbar.make(findViewById(R.id.fab), "Ктегория удалена", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });
                ad.setNegativeButton("Не надо", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int i) {
                        return;
                    }
                });
                ad.setCancelable(true);
                AlertDialog alert = ad.create();
                alert.show();
                return true;
            }
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
            toolbar.setTitle("Все");
        } else if (id == R.id.add) {
            Intent intent = new Intent(this, NewCat.class);
            startActivityForResult(intent, 0);
        } else if (id == 0 && !item.getTitle().equals(listOfCategories.get(0))){
            initRecyclerViewByAuthor(item.getTitle().toString());
            toolbar.setTitle(item.getTitle().toString());
        } else {
            initRecyclerView(id);
            toolbar.setTitle(listOfCategories.get(id));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        if (requestCode == 0) {
            String name = data.getStringExtra("name");
            listOfCategories.add(name);
            int id = listOfCategories.size() - 1;
            initRecyclerView(id);
            initNavDrawer();
            navigationView.setCheckedItem(id);
            toolbar.setTitle(listOfCategories.get(id));
        }
        else {
            String author = data.getStringExtra("author");
            String track = data.getStringExtra("track");
            String category = data.getStringExtra("category");
            listAuthor.add(author);
            listTrack.add(track);
            Category.add(listOfCategories.indexOf(category));
            if(getCheckedItem() != -1) {
                initRecyclerView(listOfCategories.indexOf(category));
                initNavDrawer();
                navigationView.setCheckedItem(listOfCategories.indexOf(category));
                toolbar.setTitle(listOfCategories.get(listOfCategories.indexOf(category)));
            } else {
                initNavDrawer();
            }
            Snackbar.make(findViewById(R.id.fab), "Клевая песня!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor = mSettings.edit();

        String listAuthorString = TextUtils.join("·", listAuthor);
        editor.putString("listAuthor", listAuthorString);

        Set<String> listTrackSet = new LinkedHashSet<String>();
        String listTrackString = TextUtils.join("·", listTrack);
        editor.putString("listTrack", listTrackString);

        Set<String> CategorySet = new LinkedHashSet<String>();
        String s = "";
        int count = 0;
        for (int i : Category) {
            if (count != 0) {
                s += "·";
            }
            s += Integer.toString(i);
            count++;
        }
        editor.putString("Category", s);

        String listOfCategoriesString = TextUtils.join("·", listOfCategories);
        editor.putString("listOfCategories", listOfCategoriesString);
        editor.apply();
    }

    private void FabClick(){
        Intent intent = new Intent(this, NewTrack.class);
        intent.putExtra("Cats", listOfCategories);
        intent.putExtra("Category", getCheckedItem());
        startActivityForResult(intent, 1);
    }

    private void initNavDrawer(){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Menu menu = navigationView.getMenu();
        if(subMenu != null)
            subMenu.clear();
        subMenu = menu.addSubMenu(R.id.main_menu, Menu.NONE, 1, "Категории");
        //subMenu.setIcon(R.drawable.ic_blur_on_black_24dp);

        for (int i = 0; i < listOfCategories.size(); i++){
            subMenu.add(Menu.NONE, i, 1, listOfCategories.get(i)).setCheckable(true);
        }

        if(subMenu2 != null)
            subMenu2.clear();
        subMenu2 = menu.addSubMenu(R.id.main_menu, Menu.NONE, 2, "Исполнители");
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
    }

    public void initRecyclerView(int id) {

        aList = new ArrayList<HashMap<String, String>>();

        for (int i = -1; i < listAuthor.size(); i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            if (i == -1) {
                hm.put("item_title", "Рандомить здесь");
                hm.put("listview_image", Integer.toString(R.drawable.ic_shuffle_black_24dp));
                aList.add(hm);
            } else {
                if (id == -1) {
                    hm.put("listview_title", listAuthor.get(i));
                    hm.put("listview_discription", listTrack.get(i));
                    aList.add(hm);
                } else if (Category.get(i) == id) {
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
        setListeners(id, "");
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
        setListeners(0, name);
    }

    private void setListeners(final int id, final String name){
        androidListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0){
                    if(adapterView.getCount() == 1) {
                        Snackbar.make(view, "То, что мертво, умереть не может", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else if (adapterView.getCount() == 2){
                        Snackbar.make(view, "Сам подумай, что выпадет", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else {
                        Random random = new Random();
                        int num = 1 + random.nextInt(adapterView.getCount() - 1);
                        final HashMap<String, String> item = (HashMap<String, String>) adapterView.getItemAtPosition(num);
                        String item_data = item.get("listview_title") + " — " + item.get("listview_discription");
                        AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);
                        ad.setTitle(item_data);
                        ad.setMessage("Найти слова?");
                        ad.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int i) {
                                Uri address = Uri.parse("https://www.google.ru/?gws_rd=ssl#newwindow=1&q=" + item.get("listview_title") + "+-+" + item.get("listview_discription") + "+аккорды+");
                                Intent openlinkIntent = new Intent(Intent.ACTION_VIEW, address);
                                startActivity(openlinkIntent);
                            }
                        });
                        ad.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int i) {
                                return;
                            }
                        });
                        ad.setCancelable(true);
                        AlertDialog alert = ad.create();
                        alert.show();
//                        Snackbar.make(view, item_data, Snackbar.LENGTH_LONG)
//                                .setAction("Action", null).show();
                    }
                }
                else {
                    AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);
                    final HashMap<String, String> item = (HashMap<String, String>) adapterView.getItemAtPosition(i);
                    ad.setTitle("Найти слова " + item.get("listview_discription") + " ?");
                    ad.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int i) {
                            Uri address = Uri.parse("https://www.google.ru/?gws_rd=ssl#newwindow=1&q=" + item.get("listview_title") + "+-+" + item.get("listview_discription") + "+аккорды+");
                            Intent openlinkIntent = new Intent(Intent.ACTION_VIEW, address);
                            startActivity(openlinkIntent);
                        }
                    });
                    ad.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int i) {
                            return;
                        }
                    });
                    ad.setCancelable(true);
                    AlertDialog alert = ad.create();
                    alert.show();
                }
            }

        });
        androidListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);
                final HashMap<String, String> item = (HashMap<String, String>) adapterView.getItemAtPosition(i);
                ad.setTitle("Удалить " + item.get("listview_title") + " — " + item.get("listview_discription") + "?");
                ad.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int i) {
                        int index = listTrack.indexOf(item.get("listview_discription"));
                        listTrack.remove(index);
                        listAuthor.remove(index);
                        Category.remove(index);
                        if (!name.equals("")){
                            initRecyclerViewByAuthor(name);
                        } else {
                            initRecyclerView(id);
                        }
                        Snackbar.make(findViewById(R.id.fab), "Удалено", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });
                ad.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int i) {
                        return;
                    }
                });
                ad.setCancelable(true);
                AlertDialog alert = ad.create();
                alert.show();
                return false;
            }
        });
    }

    private void initArrays(){

        Category = new ArrayList<>();
        listOfCategories = new ArrayList<>();

        if (mSettings.contains("listAuthor")) {

            String la = mSettings.getString("listAuthor", "");
            listAuthor = new ArrayList<>(Arrays.asList(la.split("·")));

            String lt = mSettings.getString("listTrack", "");
            listTrack = new ArrayList<>(Arrays.asList(lt.split("·")));

            String s = mSettings.getString("Category", "");
            List<String> strings = new ArrayList<>(Arrays.asList(s.split("·")));
            for (String str: strings){
                Category.add(Integer.parseInt(str));
            }

            String loc = mSettings.getString("listOfCategories", "");
            listOfCategories = new ArrayList<>(Arrays.asList(loc.split("·")));
        }
        else {

            listAuthor = new ArrayList<>();
            for (int i = 0; i < arrayAuthor.length; i++) {
                listAuthor.add(arrayAuthor[i]);
            }

            listTrack = new ArrayList<>();
            for (int i = 0; i < arrayTrack.length; i++) {
                listTrack.add(arrayTrack[i]);
            }

            Category = new ArrayList<>();
            for (int i = 0; i < arrayCategory.length; i++) {
                Category.add(arrayCategory[i]);
            }

            listOfCategories = new ArrayList<>();
            for (int i = 0; i < arrayOfCategories.length; i++) {
                listOfCategories.add(arrayOfCategories[i]);
            }
        }
    }

    private int getCheckedItem() {
        for (int i = 0; i < subMenu.size(); i++) {
            MenuItem item = subMenu.getItem(i);
            if (item.isChecked()) {
                return i;
            }
        }
        return -1;
    }

}
