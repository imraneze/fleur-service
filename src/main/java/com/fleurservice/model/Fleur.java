package com.fleurservice.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.fleurservice.dto.FleurView;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "fleurs")
public class Fleur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(FleurView.Public.class)
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    @Pattern(regexp = "^[^0-9]*$", message = "Le nom ne doit pas contenir de chiffres")
    @Column(nullable = false)
    @JsonView(FleurView.Public.class)
    private String nom;

    @NotBlank(message = "La couleur est obligatoire")
    @JsonView(FleurView.Public.class)
    private String couleur;

    @NotNull(message = "Le prix est obligatoire")
    @Positive(message = "Le prix doit être positif")
    @JsonView(FleurView.Public.class)
    private Double prix;

    @JsonView(FleurView.Public.class)
    private String saisonnalite;

    // Visible seulement pour ADMIN
    @JsonView(FleurView.Internal.class)
    private String fournisseur;

    public Fleur() {}

    public Fleur(Long id, String nom, String couleur, Double prix, String saisonnalite, String fournisseur) {
        this.id = id;
        this.nom = nom;
        this.couleur = couleur;
        this.prix = prix;
        this.saisonnalite = saisonnalite;
        this.fournisseur = fournisseur;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public String getSaisonnalite() {
        return saisonnalite;
    }

    public void setSaisonnalite(String saisonnalite) {
        this.saisonnalite = saisonnalite;
    }

    public String getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(String fournisseur) {
        this.fournisseur = fournisseur;
    }
}