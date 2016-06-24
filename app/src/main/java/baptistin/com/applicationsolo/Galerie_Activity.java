package baptistin.com.applicationsolo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.app.Activity;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class Galerie_Activity extends Activity {
    ListView list;
    String[] web;
    Integer[] imageId;
    int i =0;
    TodoOpenDatabaseHelper todoOpenDatabaseHelper = OpenHelperManager.getHelper(this,
            TodoOpenDatabaseHelper.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.galerie_main);

        try {
            initialize();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        CustomList adapter = new
                CustomList(Galerie_Activity.this, web, imageId);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(Galerie_Activity.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();

            }
        });

    }

    protected void initialize() throws SQLException {

        String[] web = {
                "Google Plus",
                "Twitter",
                "Windows",
                "Bing",
                "Itunes",
                "Wordpress",
                "Drupal"
        } ;
        Integer[] imageId = {
                R.drawable.common_full_open_on_phone,
                R.drawable.common_full_open_on_phone,
                R.drawable.common_full_open_on_phone,
                R.drawable.common_full_open_on_phone,
                R.drawable.common_full_open_on_phone,
                R.drawable.common_full_open_on_phone,
                R.drawable.common_full_open_on_phone
        };

        Dao<Photos, Long> todoDao = todoOpenDatabaseHelper.getDaoPhotos();

        List<Photos> listUser = todoDao.queryForAll();
        for(Photos t : listUser) {
            //todoDao.delete(t);

            Log.d("namePhoto :", t.getName());
            Log.d("latitude :", t.getLatitude());
            Log.d("longitudeo :", t.getLongitude());
        }
    }

}