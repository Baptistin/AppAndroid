package epsi.com.spacepic.entity;

import java.sql.Timestamp;

public class Photo {
    private long id;
    private String nomFichier;
    private String commentaire;
    private String dateCreation;
    private float localisationLongitude;
    private float localisationLatitude;
    private float localisationOrientation;

    public Photo(long id, String nomFichier, String commentaire, String dateCreation,
                 float localisationLongitude, float localisationLatitude, float localisationOrientation) {
        super();
        this.id = id;
        this.nomFichier = nomFichier;
        this.commentaire = commentaire;
        this.dateCreation = dateCreation;
        this.localisationLongitude = localisationLongitude;
        this.localisationLatitude = localisationLatitude;
        this.localisationOrientation = localisationOrientation;
    }

    public Photo(String nomFichier, String commentaire, String dateCreation,
                 float localisationLongitude, float localisationLatitude, float localisationOrientation) {
        super();
        this.nomFichier = nomFichier;
        this.commentaire = commentaire;
        this.dateCreation = dateCreation;
        this.localisationLongitude = localisationLongitude;
        this.localisationLatitude = localisationLatitude;
        this.localisationOrientation = localisationOrientation;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNomFichier() {
        return nomFichier;
    }

    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public float getLocalisationLongitude() {
        return localisationLongitude;
    }

    public void setLocalisationLongitude(float localisationLongitude) {
        this.localisationLongitude = localisationLongitude;
    }

    public float getLocalisationLatitude() {
        return localisationLatitude;
    }

    public void setLocalisationLatitude(float localisationLatitude) {
        this.localisationLatitude = localisationLatitude;
    }

    public float getLocalisationOrientation() {
        return localisationOrientation;
    }

    public void setLocalisationOrientation(float localisationOrientation) {
        this.localisationOrientation = localisationOrientation;
    }
}
