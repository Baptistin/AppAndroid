package baptistin.com.applicationsolo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "user")
public class User {

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField
    private String nom;

    @DatabaseField
    private String prenom;

    @DatabaseField
    private String email;

    @DatabaseField
    private Date datenaissance;

    @DatabaseField
    private String login;

    @DatabaseField
    private String motdepasse;



    public User() {

    }

    public User(String nom, String prenom, String email, String login, String motdepasse, Date datenaissance) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.login= login;
        this.motdepasse = motdepasse;
        this.datenaissance = datenaissance;
    }

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDatenaissance() {
        return datenaissance;
    }

    public void setDatenaissance(Date datenaissance) {
        this.datenaissance = datenaissance;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMotdepasse() {
        return motdepasse;
    }

    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }
}