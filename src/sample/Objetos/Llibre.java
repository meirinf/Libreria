package sample.Objetos;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by 53638138e on 20/01/17.
 */

@Entity
public class Llibre implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private String titol;
    private String autor;
    private String nombreExemplars;
    private String editorial;
    private String nombrePagines;
    private String anyEdicio;

    // Constructor
    public Llibre() {

    }

    // Getters

    public long getId() {return id;}
    
    public String getTitol() {return titol;}

    public String getAutor() {return autor;}

    public String getNombreExemplars() {return nombreExemplars;}

    public String getEditorial() {return editorial;}

    public String getNombrePagines() {return nombrePagines;}

    public String getAnyEdicio() {return anyEdicio;}


    // Setters
    public void setId(long id) {
        this.id = id;
    }

    public void setTitol(String titol) {this.titol = titol;}

    public void setAutor(String autor) {this.autor = autor;}

    public void setNombreExemplars(String numExemplars) {this.nombreExemplars = numExemplars;}

    public void setEditorial(String editorial) {this.editorial = editorial;}

    public void setNombrePagines(String numPagines) {this.nombrePagines = numPagines;}

    public void setAnyEdicio(String anyEdicio) {this.anyEdicio = anyEdicio;}

    public String toString() {
        return "     Titulo: " + titol +
                "\n     Autor: "+ autor +
                "\n     Numero de ejemplares: " + nombreExemplars +
                "\n     Editorial: " + editorial +
                "\n     Numero de Paginas: " + nombrePagines +
                "\n     Año de edición: " + anyEdicio;
    }
}
