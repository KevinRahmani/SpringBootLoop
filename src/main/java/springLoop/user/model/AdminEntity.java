package springLoop.user.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "admin", schema = "user_loop", catalog = "")
public class AdminEntity {
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
    @Column(name = "nbtotalsales")
    private int nbtotalsales;
    @Basic
    @Column(name = "adresse")
    private String adresse;
}
