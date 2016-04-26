package epsi.com.spacepic.entity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import epsi.com.spacepic.DAOBase;

public class PhotoDAO extends DAOBase {
    public static final String PHOTO_TABLE_NAME = "Photo";

    public static final String PHOTO_KEY = "id";
    public static final String PHOTO_NOMFICHIER = "nomfichier";
    public static final String PHOTO_COMMENTAIRE = "commentaire";
    public static final String PHOTO_DATE = "date_creation";
    public static final String PHOTO_LOCALISATION_LONGITUDE = "localisation_longitude";
    public static final String PHOTO_LOCALISATION_LATITUDE = "localisation_latitude";
    public static final String PHOTO_LOCALISATION_ORIENTATION = "localisation_orientation";

    public static final String PHOTO_TABLE_CREATE =
            "CREATE TABLE " + PHOTO_TABLE_NAME + " (" +
                    PHOTO_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    PHOTO_NOMFICHIER + " TEXT, " +
                    PHOTO_COMMENTAIRE + " TEXT, " +
                    PHOTO_DATE + " TEXT, " +
                    PHOTO_LOCALISATION_LONGITUDE + " REAL, " +
                    PHOTO_LOCALISATION_LATITUDE + " REAL, " +
                    PHOTO_LOCALISATION_ORIENTATION + " REAL);";

    public static final String PHOTO_TABLE_DROP = "DROP TABLE IF EXISTS " + PHOTO_TABLE_NAME + ";";

    public PhotoDAO(Context pContext) {
        super(pContext);
    }

    /**
     * @param m la photo à ajouter à la base
     */

    public void ajouter(Photo m) {

        ContentValues value = new ContentValues();

        value.put(PhotoDAO.PHOTO_NOMFICHIER, m.getNomFichier());
        value.put(PhotoDAO.PHOTO_COMMENTAIRE, m.getCommentaire());
        value.put(PhotoDAO.PHOTO_DATE, m.getDateCreation());
        value.put(PhotoDAO.PHOTO_LOCALISATION_LONGITUDE, m.getLocalisationLongitude());
        value.put(PhotoDAO.PHOTO_LOCALISATION_LATITUDE, m.getLocalisationLatitude());
        value.put(PhotoDAO.PHOTO_LOCALISATION_ORIENTATION, m.getLocalisationOrientation());

        mDb.insert(PhotoDAO.PHOTO_TABLE_NAME, null, value);

    }


    /**
     * @param id l'identifiant de la photo à supprimer
     */

    public void supprimer(long id) {

        mDb.delete(PHOTO_TABLE_NAME, PHOTO_KEY + " = ?", new String[] {String.valueOf(id)});

    }


    /**
     * @param m la photo modifié
     */

    public void modifier(Photo m) {

        ContentValues value = new ContentValues();

        value.put(PhotoDAO.PHOTO_NOMFICHIER, m.getNomFichier());
        value.put(PhotoDAO.PHOTO_COMMENTAIRE, m.getCommentaire());
        value.put(PhotoDAO.PHOTO_DATE, m.getDateCreation());
        value.put(PhotoDAO.PHOTO_LOCALISATION_LONGITUDE, m.getLocalisationLongitude());
        value.put(PhotoDAO.PHOTO_LOCALISATION_LATITUDE, m.getLocalisationLatitude());
        value.put(PhotoDAO.PHOTO_LOCALISATION_ORIENTATION, m.getLocalisationOrientation());

        mDb.update(PHOTO_TABLE_NAME, value, PHOTO_KEY  + " = ?", new String[] {String.valueOf(m.getId())});

    }


    /**
     * @param id l'identifiant de la photo à récupérer
     */

    public Photo selectionner(long id) {

        Cursor mCursor = mDb.rawQuery(
                "SELECT * FROM '" + PHOTO_TABLE_NAME + "' WHERE id= '"+id+"'" , null);

        mCursor.moveToFirst();
        long idPhoto = mCursor.getLong(mCursor.getColumnIndex(PHOTO_KEY));
        String nomfichier = mCursor.getString(mCursor.getColumnIndex(PHOTO_NOMFICHIER));
        String commentaire = mCursor.getString(mCursor.getColumnIndex(PHOTO_COMMENTAIRE));
        String date = mCursor.getString(mCursor.getColumnIndex(PHOTO_DATE));
        float longitude = mCursor.getFloat(mCursor.getColumnIndex(PHOTO_LOCALISATION_LONGITUDE));
        float latitude = mCursor.getFloat(mCursor.getColumnIndex(PHOTO_LOCALISATION_LATITUDE));
        float orientation = mCursor.getFloat(mCursor.getColumnIndex(PHOTO_LOCALISATION_ORIENTATION));

        Photo selectedPhoto = new Photo(idPhoto, nomfichier, commentaire, date, longitude, latitude, orientation);

        return selectedPhoto;
        
    }

    public List<Photo> lister() {

        List<Photo> list = new ArrayList<>();

        Cursor mCursor = mDb.rawQuery(
                "SELECT * FROM '" + PHOTO_TABLE_NAME, null);

        if (!mCursor.moveToFirst()) {
            while(mCursor.isAfterLast() == false) {
                long idPhoto = mCursor.getLong(mCursor.getColumnIndex(PHOTO_KEY));
                String nomfichier = mCursor.getString(mCursor.getColumnIndex(PHOTO_NOMFICHIER));
                String commentaire = mCursor.getString(mCursor.getColumnIndex(PHOTO_COMMENTAIRE));
                String date = mCursor.getString(mCursor.getColumnIndex(PHOTO_DATE));
                float longitude = mCursor.getFloat(mCursor.getColumnIndex(PHOTO_LOCALISATION_LONGITUDE));
                float latitude = mCursor.getFloat(mCursor.getColumnIndex(PHOTO_LOCALISATION_LATITUDE));
                float orientation = mCursor.getFloat(mCursor.getColumnIndex(PHOTO_LOCALISATION_ORIENTATION));

                Photo selectedPhoto = new Photo(idPhoto, nomfichier, commentaire, date, longitude, latitude, orientation);

                list.add(selectedPhoto);
                mCursor.moveToNext();
            }
        }

        return list;
    }
}
