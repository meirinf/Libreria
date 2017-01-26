package sample.Objetos;

import javax.persistence.*;
import java.io.Serializable;
/**
 * Created by 53638138e on 20/01/17.
 */
@Entity
public class Soci implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private String nom;
    private String cognom;
    private String edat;
    private String direccio;
    private String telefon;

    // Constructor

    public Soci(){}

    // Getters

    public long getId() {return id;}

    public String getNom() {
        return nom;
    }

    public String getCognom() {
        return cognom;
    }

    public String getEdat() {
        return edat;
    }

    public String getDireccio() {
        return direccio;
    }

    public String getTelefon() {
        return telefon;
    }

    // Setters

    public void setId(long id) {this.id = id;}

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setCognom(String cognom) {
        this.cognom = cognom;
    }

    public void setEdat(String edat) {
        this.edat = edat;
    }

    public void setDireccio(String direccio) {
        this.direccio = direccio;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }


    public String toString() {
        return "     Nombre: " + nom +
                "\n     Apellido: " + cognom +
                "\n     Edat: " + edat +
                "\n     Dirección: " + direccio +
                "\n     Telefono: " + telefon;
    }
}
