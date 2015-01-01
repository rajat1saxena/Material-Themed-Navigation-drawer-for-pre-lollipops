package in.raynstudios.materialtheme;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.app.Fragment;
import android.net.Uri;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.widget.SearchView;


public class MainActivity extends ActionBarActivity
        implements
        Fragment1.OnFragment1InteractionListener,
        Fragment2.OnFragment2InteractionListener,
        Fragment3.OnFragment3InteractionListener,
        Fragment4.OnFragment4InteractionListener
{

    /* Define vars */
    ActionBarDrawerToggle mDrawerToggle;
    DrawerLayout mDrawerLayout;
    ListView mListView,mListViewSecondary;
    private String[] mNavDrawerPrimaryList,mNavDrawerSecondaryList;
    private NavDrawerListViewAdapter mAdapter;
    SearchView searchView;
    String[] frags = {
            "in.raynstudios.materialtheme.Fragment1",
            "in.raynstudios.materialtheme.Fragment2",
            "in.raynstudios.materialtheme.Fragment3",
            "in.raynstudios.materialtheme.Fragment4"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set toolbar as ActionBar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.custom_home_toolbar);
        setSupportActionBar(mToolbar);

        //find DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Get List Views
        mListView = (ListView) findViewById(R.id.drawerListMain);
        mListViewSecondary = (ListView) findViewById(R.id.drawerListSecondary);

        // Get array of strings
        mNavDrawerPrimaryList = getResources().getStringArray(R.array.navDrawerPrimaryList);
        mNavDrawerSecondaryList = getResources().getStringArray(R.array.navDrawerSecondaryList);

        // Setting string array to Drawer list
        //mListView.setAdapter(new ArrayAdapter<String>(this,R.layout.drawerlistitem,mNavDrawerPrimaryList));

        // Setting to nav drawer primary list view to custom adapter
        mAdapter = new NavDrawerListViewAdapter(this);
        mListView.setAdapter(mAdapter);

        // Set secondary nav drawer list view
        mListViewSecondary.setAdapter(new ArrayAdapter<String>(this,R.layout.drawerlistitem,mNavDrawerSecondaryList));


        // Setting item click listeners for primary and secondary listviews
        mListView.setOnItemClickListener(new DrawerPrimaryClickListener());
        mListViewSecondary.setOnItemClickListener(new DrawerSecondaryClickListener());

        // setting drawertoggle
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,R.string.app_name,R.string.app_name){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        // Setting drawer toggle listener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Set searchView hint
        /*
        MenuItem menuItem = menu.findItem(R.id.action_search);
        searchView = (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setQueryHint(getString(R.string.search_actionbar_title));
        */

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
        }else if(id == R.id.action_search){
            Intent intent = new Intent(this,SearchActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(Gravity.START|Gravity.LEFT)){
            mDrawerLayout.closeDrawers();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.d("Fragment: ",""+uri.toString());
    }


    /**
     * Created by rajat on 28/12/14.
     * This will respond for Primary ListView
     */
    public class DrawerPrimaryClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView adapterView, View view, int position, long l) {
            selectItem(position);
        }

        /* Function to swap fragments on item clicks */
        private void selectItem(int position){
            Log.d("Output: Item selected ",""+mNavDrawerPrimaryList[position]);
            getSupportActionBar().setTitle(mNavDrawerPrimaryList[position]);

            // Close the drawer
            if(mDrawerLayout.isDrawerOpen(Gravity.START|Gravity.LEFT)){
                mDrawerLayout.closeDrawers();

                // Set appropriate Fragment
                FragmentTransaction fragTx = getFragmentManager().beginTransaction();
                fragTx.replace(R.id.main_fragment_container, Fragment.instantiate(MainActivity.this,frags[position]));
                fragTx.commit();

                return;
            }

        }
    }

    /**
     * Created by rajat on 28/12/14.
     * This will respond for Primary ListView
     */
    public class DrawerSecondaryClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView adapterView, View view, int position, long l) {
            selectItem(position);
        }

        /* Function to swap fragments on item clicks */
        private void selectItem(int position){
            Log.d("Output: Item selected ",""+mNavDrawerSecondaryList[position]);
            getSupportActionBar().setTitle(mNavDrawerSecondaryList[position]);

            // Close the drawer
            if(mDrawerLayout.isDrawerOpen(Gravity.START|Gravity.LEFT)){
                mDrawerLayout.closeDrawers();
                return;
            }

            // Set selected state for this item

        }
    }

    /**
     * NavDrawerListViewAdapter for showing icons in nav drawer
     */
    public class NavDrawerListViewAdapter extends BaseAdapter{

        private Context ctx;
        String[] listItems;
        int[] images = {
                R.drawable.ic_action_name2,
                R.drawable.ic_tag,
                R.drawable.ic_headphone,
                R.drawable.ic_action_name
        };

        public NavDrawerListViewAdapter(Context ctx){
            this.ctx = ctx;
            listItems = ctx.getResources().getStringArray(R.array.navDrawerPrimaryList);
        }

        @Override
        public int getCount() {
            return listItems.length;
        }

        @Override
        public Object getItem(int i) {
            return listItems[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View row = null;

            if(convertView==null){
                LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.custom_listview,parent,false);
            }else{
                row = convertView;
            }

            TextView txtView = (TextView)row.findViewById(R.id.strListView);
            ImageView imgView = (ImageView)row.findViewById(R.id.imgListView);

            txtView.setText(listItems[position]);
            imgView.setImageResource(images[position]);

            return row;
        }
    }
}
