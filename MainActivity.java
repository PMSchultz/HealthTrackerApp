package edu.cnm.deepdive.healthtracker;


import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import edu.cnm.deepdive.healthtracker.fragments.ChartFragment;
import edu.cnm.deepdive.healthtracker.fragments.DatePickerFragment;
import edu.cnm.deepdive.healthtracker.fragments.MedicationFragment;


public class MainActivity extends AppCompatActivity
    implements OnNavigationItemSelectedListener, OnItemSelectedListener,
    ChartFragment.OnFragmentInteractionListener {


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    //get the spinner from the xml
    Spinner selectExisting = (Spinner) findViewById(R.id.name_spinner);
    //create a list of items for the spinner
    //in the future this list will come from the server
    String[] items = new String[]{"Mickey", "Minnie", "Donald", "Goofy"};
    //create an adapter to describe how the items are displayed
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
        android.R.layout.simple_spinner_dropdown_item, items);
    //set the spinners adapter to the previously created one.
    selectExisting.setAdapter(adapter);
    selectExisting.setOnItemSelectedListener(this);

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.setDrawerListener(toggle);
    drawer.openDrawer(GravityCompat.START);//Set the drawer to open at start
    toggle.syncState();

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);
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

    ChartFragment fragment = new ChartFragment();
    Bundle bundle = new Bundle();
    bundle.putInt("Record Type", id);
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    fragment.setArguments(bundle);
    ft.replace(R.id.content_panel, fragment);
    ft.commit();

//    if (id == R.id.nav_allergies) {
//      FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//
//      ft.replace(R.id.content_panel, fragment);
//      ft.commit();
//    } else if (id == R.id.nav_immunizations) {
//
//    } else if (id == R.id.nav_medications) {
//      FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//
//      ft.replace(R.id.content_panel, fragment);
//      ft.commit();
//
//    } else if (id == R.id.nav_office_visits) {
//
//    } else if (id == R.id.nav_lab_results) {
//
//    } else if (id == R.id.nav_imaging_results) {
//
//    } else if (id == R.id.nav_hospitalizations) {
//
//    }

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;

  }

  @Override
  public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

  }

  @Override
  public void onNothingSelected(AdapterView<?> adapterView) {

  }

  @Override
  public void onFragmentInteraction(Uri uri) {

  }

  public void openRecord(View view) {
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    ft.replace(R.id.content_panel, new MedicationFragment());
    ft.commit();

  }

  public void editRecord(View view) {
    //TODO will need a checkbox or list view that highlights one selected
  }


  public void cancelReturntoNavigation(View view) {
    //TODO returns user to Navigation menu
  }

  public void cancelRecordActivity(View view) {
    //TODO return user to list view without adding or editing a record
  }


}