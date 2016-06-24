package baptistin.com.applicationsolo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "photos")
public class Photos {

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField
    private String nom;

    @DatabaseField
    private String latitude;

    @DatabaseField
    private String longitude;

    @DatabaseField
    private String commentaire;

    public Photos() {

    }

    public Photos(String nom, String latitude, String longitude, String commentaire) {
        this.nom = nom;
        this.latitude = latitude;
        this.longitude = longitude;
        this.commentaire = commentaire;
    }

    public String getName() {
        return nom;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getCommentaire() {
        return commentaire;
    }


}