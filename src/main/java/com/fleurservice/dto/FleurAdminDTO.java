package com.fleurservice.dto;

import com.fleurservice.model.Fleur;

public class FleurAdminDTO {

    private Long id;
    private String nom;
    private String couleur;
    private Double prix;
    private String saisonnalite;
    private String fournisseur; // visible ici

    public FleurAdminDTO(Fleur fleur) {
        this.id = fleur.getId();
        this.nom = fleur.getNom();
        this.couleur = fleur.getCouleur();
        this.prix = fleur.getPrix();
        this.saisonnalite = fleur.getSaisonnalite();
        this.fournisseur = fleur.getFournisseur();
    }

    public Long getId() { return id; }
    public String getNom() { return nom; }
    public String getCouleur() { return couleur; }
    public Double getPrix() { return prix; }
    public String getSaisonnalite() { return saisonnalite; }
    public String getFournisseur() { return fournisseur; }
}