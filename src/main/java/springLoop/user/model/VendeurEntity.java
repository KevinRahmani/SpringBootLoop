package springLoop.user.model;


import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "vendeur", schema = "user_loop", catalog = "")
public class VendeurEntity {
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
    @Column(name = "adresse")
    private String adresse;
    @Basic
    @Column(name = "datesignup")
    private Timestamp datesignup;
    @Basic
    @Column(name = "nbtotalsales")
    private int nbtotalsales;
    @Basic
    @Column(name = "addRight")
    private int addRight;
    @Basic
    @Column(name = "removeRight")
    private int removeRight;
    @Basic
    @Column(name = "modifyRight")
    private int modifyRight;
}
