package baptistin.com.applicationsolo;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import baptistin.com.applicationsolo.R;

public class MyCameraActivity extends Activity {
    private static final int TAKE_PHOTO_REQUEST = 1;
    private ImageView mImageView;
    String mCurrentPhotoPath;
    private File photoFile;
    public String namePhoto="test";
    public String namePhoto1="";
    public String commentaire="blabla";
    public String photoLatitude="1";
    public String photoLongitude="2";
    public Button validationPhoto;
    public Button annulationPhoto;
    public static final String INTENT_GPS_longitude = "baptistin.com.applicationsolo.INTENT_MESSAGE";
    public static final String INTENT_GPS_latitude = "baptistin.com.applicationsolo.INTENT_MESSAGE1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);

        Intent it = getIntent();
        if (it != null)
        {
            Bundle params = it.getExtras();
            if  (params != null)
            {
                photoLongitude = Double.toString(params.getDouble(INTENT_GPS_longitude));
                photoLatitude = Double.toString(params.getDouble(INTENT_GPS_latitude));
            }
        }

        //photoLongitude = Double.toString(this.getIntent().getDoubleExtra("INTENT_GPS_longitude",1));
        //photoLatitude =Double.toString(this.getIntent().getDoubleExtra("INTENT_GPS_latitude",1));
        mImageView = (ImageView) findViewById(R.id.imageView1);
        dispatchTakePictureIntent();
        validationPhoto = (Button) findViewById(R.id.validation);
        annulationPhoto = (Button) findViewById(R.id.retour);

        validationPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    enregistrePhotos(namePhoto,photoLatitude,photoLongitude,commentaire);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        annulationPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    affichephoto();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    protected void affichephoto() throws SQLException {
        TodoOpenDatabaseHelper todoOpenDatabaseHelper = OpenHelperManager.getHelper(this,
                TodoOpenDatabaseHelper.class);
        Dao<Photos, Long> todoDao = todoOpenDatabaseHelper.getDaoPhotos();

        List<Photos> listUser = todoDao.queryForAll();
        for(Photos t : listUser) {
            //todoDao.delete(t);

            Log.d("namePhoto :", t.getName());
            Log.d("latitude :", t.getLatitude());
            Log.d("longitudeo :", t.getLongitude());
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PHOTO_REQUEST && resultCode == RESULT_OK) {

            // set the dimensions of the image
            int targetW =100;
            int targetH = 100;

            // Get the dimensions of the bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(photoFile.getAbsolutePath(), bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            // Determine how much to scale down the image
            int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;

            // stream = getContentResolver().openInputStream(data.getData());
            Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath(),bmOptions);
            mImageView.setImageBitmap(bitmap);
        }

    }


    private File createImageFile() throws IOException {
        // Create an image file name
        namePhoto = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "EPSI_" + namePhoto + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }


    private void dispatchTakePictureIntent() {

        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            photoFile = createImageFile();
            takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
            startActivityForResult(takePhotoIntent, TAKE_PHOTO_REQUEST);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void enregistrePhotos(String nom,String latitude,String longitude, String comment) throws java.sql.SQLException {
        TodoOpenDatabaseHelper todoOpenDatabaseHelper = OpenHelperManager.getHelper(this,
                TodoOpenDatabaseHelper.class);

        Dao<Photos, Long> todoDao1 = todoOpenDatabaseHelper.getDaoPhotos();

        todoDao1.create(new Photos(nom,latitude,longitude,comment));

        Context context = getApplicationContext();
        CharSequence text = "Votre image a bien été ajouté";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);

    }
}