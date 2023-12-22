package springLoop.product.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "stock", schema = "stock_loop", catalog = "")
public class ArticleEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "nom")
    private String nom;
    @Basic
    @Column(name = "marque")
    private String marque;
    @Basic
    @Column(name = "prix")
    private int prix;
    @Basic
    @Column(name = "vendeur")
    private String vendeur;
    @Basic
    @Column(name = "stock")
    private int stock;
    @Basic
    @Column(name = "type")
    private String type;
    @Basic
    @Column(name = "couleur")
    private String couleur;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "sales")
    private int sales;
    @Basic
    @Column(name = "image")
    private String image;
    @Basic
    @Column(name = "categorie")
    private String categorie;


    public void setUp(String nom, String marque, int prix, String vendeur, int stock, String type, String couleur, String description, int sales, String image, String categorie) {
        this.nom = nom;
        this.marque = marque;
        this.prix = prix;
        this.vendeur = vendeur;
        this.stock = stock;
        this.type = type;
        this.couleur = couleur;
        this.description = description;
        this.sales = sales;
        this.image = image;
        this.categorie = categorie;
    }
}
