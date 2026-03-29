package com.fleurservice.controller;

import com.fleurservice.dao.FleurRepository;
import com.fleurservice.dto.FleurAdminDTO;
import com.fleurservice.exceptions.FleurNotFoundException;
import com.fleurservice.model.Fleur;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(name = "Fleurs", description = "API de gestion de la boutique de fleurs")
@RestController
@RequestMapping("/fleurs")
public class FleurController {

    private final FleurRepository fleurRepository;

    @Autowired
    public FleurController(FleurRepository fleurRepository) {
        this.fleurRepository = fleurRepository;
    }

    @Operation(summary = "Récupérer toutes les fleurs")
    @GetMapping
    public ResponseEntity<List<Fleur>> getAllFleurs() {
        List<Fleur> fleurs = fleurRepository.findAll();
        return ResponseEntity.ok(fleurs);
    }

    @Operation(summary = "Récupérer une fleur par son ID")
    @GetMapping("/{id}")
    public ResponseEntity<Fleur> getFleurById(@PathVariable Long id) {
        Fleur fleur = fleurRepository.findById(id)
                .orElseThrow(() -> new FleurNotFoundException(id));
        return ResponseEntity.ok(fleur);
    }

    @Operation(summary = "Créer une nouvelle fleur")
    @PostMapping
    public ResponseEntity<Fleur> createFleur(@Valid @RequestBody Fleur fleur) {
        Fleur savedFleur = fleurRepository.save(fleur);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedFleur.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedFleur);
    }


    @Operation(summary = "Modifier une fleur existante")
    @PutMapping("/{id}")
    public ResponseEntity<Fleur> updateFleur(
            @PathVariable Long id,
            @Valid @RequestBody Fleur fleurDetails) {

        Fleur existingFleur = fleurRepository.findById(id)
                .orElseThrow(() -> new FleurNotFoundException(id));

        existingFleur.setNom(fleurDetails.getNom());
        existingFleur.setCouleur(fleurDetails.getCouleur());
        existingFleur.setPrix(fleurDetails.getPrix());
        existingFleur.setSaisonnalite(fleurDetails.getSaisonnalite());
        existingFleur.setFournisseur(fleurDetails.getFournisseur());

        Fleur updatedFleur = fleurRepository.save(existingFleur);
        return ResponseEntity.ok(updatedFleur);
    }

    @Operation(summary = "Supprimer une fleur")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFleur(@PathVariable Long id) {
        Fleur fleur = fleurRepository.findById(id)
                .orElseThrow(() -> new FleurNotFoundException(id));

        fleurRepository.delete(fleur);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Rechercher les fleurs par couleur")
    @GetMapping("/couleur/{couleur}")
    public ResponseEntity<List<Fleur>> getFleursByCouleur(@PathVariable String couleur) {
        List<Fleur> fleurs = fleurRepository.findByCouleurIgnoreCase(couleur);

        if (fleurs.isEmpty()) {
            throw new FleurNotFoundException(couleur);
        }
        return ResponseEntity.ok(fleurs);
    }

    @Operation(summary = "Rechercher les fleurs par prix maximum")
    @GetMapping("/prix/max/{maxPrix}")
    public ResponseEntity<List<Fleur>> getFleursByMaxPrice(@PathVariable Double maxPrix) {
        List<Fleur> fleurs = fleurRepository.findByPrixLessThanEqual(maxPrix);

        if (fleurs.isEmpty()) {
            throw new FleurNotFoundException(maxPrix);
        }
        return ResponseEntity.ok(fleurs);
    }

    @Operation(summary = "Admin — voir toutes les fleurs avec fournisseur")
    @GetMapping("/admin")
    public ResponseEntity<List<FleurAdminDTO>> getFleurAdmin() {
        List<FleurAdminDTO> fleur = fleurRepository.findAll()
                .stream()
                .map(FleurAdminDTO::new)
                .collect(java.util.stream.Collectors.toList());
        return ResponseEntity.ok(fleur);
    }

    @Operation(summary = "Admin — voir une fleur avec fournisseur")
    @GetMapping("/admin/{id}")
    public ResponseEntity<FleurAdminDTO> getFleurAdmin(@PathVariable Long id) {
        Fleur fleur = fleurRepository.findById(id)
                .orElseThrow(() -> new FleurNotFoundException(id));
        return ResponseEntity.ok(new FleurAdminDTO(fleur));
    }
}