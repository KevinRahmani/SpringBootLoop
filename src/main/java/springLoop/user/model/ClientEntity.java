package springLoop.user.model;

import jakarta.persistence.*;
import java.sql.Timestamp;
import lombok.Data;

@Data
@Entity
@Table(name = "client", schema = "user_loop", catalog = "")
public class ClientEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "nom")
    private String nom;
    @Basic
    @Column(name = "password")
    private String password;
    @Basic
    @Column(name = "mail")
    private String mail;
    @Basic
    @Column(name = "datesignup")
    private Timestamp datesignup;
    @Basic
    @Column(name = "adresse")
    private String adresse;
    @Basic
    @Column(name = "nbproduct")
    private int nbproduct;
    @Basic
    @Column(name = "histocommand")
    private String histocommand;
    @Basic
    @Column(name = "fidelity")
    private int fidelity;

    public void setUp(String nom, String password, String mail, String adresse){
        setNom(nom);
        setPassword(password);
        setMail(mail);
        setAdresse(adresse);
        setNbproduct(0);
        setHistocommand("");
        setDatesignup(new Timestamp(System.currentTimeMillis()));
        setFidelity(0);
    }
}
