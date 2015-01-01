package in.raynstudios.materialtheme;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;
import android.widget.ImageView;

import java.lang.reflect.Field;


public class SearchActivity extends ActionBarActivity {

    android.support.v7.widget.SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Set toolbar as ActionBar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.search_toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);

        // Set searchView hint
        MenuItem menuItem = menu.findItem(R.id.action_search);
        searchView = (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setQueryHint(getString(R.string.search_actionbar_title));

        // Open searchView
        searchView.setIconified(false);

        // Disabling close button
        ImageView mSearchCloseButton;
        try {
            Field searchField = SearchView.class.getDeclaredField("mCloseButton");
            searchField.setAccessible(true);
            mSearchCloseButton = (ImageView) searchField.get(searchView);
            if (mSearchCloseButton != null) {
                mSearchCloseButton.setEnabled(false);
                mSearchCloseButton.setImageDrawable(getResources().getDrawable(R.drawable.abc_list_divider_mtrl_alpha));
            }
        } catch (Exception e) {
            Log.e("Error: ", "Error finding close button", e);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
