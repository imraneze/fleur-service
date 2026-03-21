package fleurs.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "fleur")
public class Fleur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    @Pattern(regexp = "^[^0-9]*$", message = "Le nom ne peut pas contenir de chiffres")
    @Size(min = 2, max = 100, message = "Le nom doit avoir entre 2 et 100 caractères")
    private String nom;

    @NotBlank(message = "La couleur est obligatoire")
    private String couleur;

    @Min(value = 0, message = "Le prix doit être positif")
    private Double prix;

    private String saisonnalite;

    public Fleur() {}

    public Fleur(String nom, String couleur, Double prix, String saisonnalite) {
        this.nom = nom;
        this.couleur = couleur;
        this.prix = prix;
        this.saisonnalite = saisonnalite;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getCouleur() { return couleur; }
    public void setCouleur(String couleur) { this.couleur = couleur; }

    public Double getPrix() { return prix; }
    public void setPrix(Double prix) { this.prix = prix; }

    public String getSaisonnalite() { return saisonnalite; }
    public void setSaisonnalite(String saisonnalite) { this.saisonnalite = saisonnalite; }
}