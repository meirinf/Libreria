package sample.Objetos;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by 53638138e on 20/01/17.
 */

@Entity
public class Prestec implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    @OneToOne(cascade = CascadeType.ALL, targetEntity = Llibre.class, fetch = FetchType.LAZY)
    private Llibre llibre;
    @OneToOne(cascade = CascadeType.ALL, targetEntity = Soci.class, fetch = FetchType.LAZY)
    private Soci soci;
    private Date dataInici;
    private Date dataFinal;
    private Date fechaDebol;

    // Constructor
    public Prestec() {

    }


    // Getters


    public long getId() {
        return id;
    }

    public Llibre getLlibre() {return llibre;}

    public Soci getSoci() {return soci;}

    public Date getDataInici() {return dataInici;}

    public Date getDataFinal() {return dataFinal;}

    public Date getFechaDebol() {
        return fechaDebol;
    }


    // Setters


    public void setId(long id) {
        this.id = id;
    }

    public void setLlibre(Llibre llibre) {this.llibre = llibre;}

    public void setSoci(Soci soci) {this.soci = soci;}

    public void setDataInici(Date dataInici) {this.dataInici = dataInici;}

    public void setDataFinal(Date dataFinal) {this.dataFinal = dataFinal;}

    public void setFechaDebol(Date fechaDebol) {
        this.fechaDebol = fechaDebol;
    }

    public String toString() {
        return "     Fecha inicio : " + dataInici +
                "\n     Fecha fin: " + dataFinal +
                "\n     Fecha de devolución: " + fechaDebol +
                "\n     Socio : " + soci.toString() +
                "\n     Libro : " + llibre.toString();
    }
}
