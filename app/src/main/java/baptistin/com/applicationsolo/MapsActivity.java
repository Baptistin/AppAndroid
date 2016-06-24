package baptistin.com.applicationsolo;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements LocationProvider.LocationCallback {

    public static final String TAG = MapsActivity.class.getSimpleName();
    private Button goretour;
    private GoogleMap mMap;
    private Button prendrephoto;
    private Button listephoto;
    public static final String INTENT_GPS_longitude = "baptistin.com.applicationsolo.INTENT_MESSAGE";
    public static final String INTENT_GPS_latitude = "baptistin.com.applicationsolo.INTENT_MESSAGE1";
    public double latitude = 0;
    public double longitude = 0;

    private LocationProvider mLocationProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
        mLocationProvider = new LocationProvider(this, this);

        prendrephoto = (Button) findViewById(R.id.prendrephoto);
        listephoto = (Button) findViewById(R.id.listephoto);

        prendrephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goPhoto();
            }
        });


    }
    private void goPhoto(){
        Intent intent = new Intent(this, MyCameraActivity.class);
        Bundle params = new Bundle();
        params.putDouble(INTENT_GPS_latitude, latitude);
        params.putDouble(INTENT_GPS_longitude, longitude);
        intent.putExtras(params);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        mLocationProvider.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocationProvider.disconnect();
    }

    private void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }

    public void handleNewLocation(Location location) {
        Log.d(TAG, location.toString());
         latitude= location.getLatitude();
         longitude= location.getLongitude();
        Log.d(TAG,Double.toString(latitude));
        LatLng latLng = new LatLng(latitude, longitude);

        LatLng latIng2 = new LatLng(45.769293,4.859062);

        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title("Geolocalisation");

        MarkerOptions options1 = new MarkerOptions()
                .position(latIng2)
                .title("Geolocalisation2")
                .draggable(true);

        mMap.addMarker(options);
        mMap.addMarker(options1);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,14));
    }
}
