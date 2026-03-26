package com.fleurservice.controller;

import com.fleurservice.dao.FleurRepository;
import com.fleurservice.dto.FleurView;
import com.fleurservice.exceptions.FleurNotFoundException;
import com.fleurservice.model.Fleur;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/fleurs")
public class FleurController {

    private final FleurRepository fleurRepository;

    @Autowired
    public FleurController(FleurRepository fleurRepository) {
        this.fleurRepository = fleurRepository;
    }

    @GetMapping
    @JsonView(FleurView.Public.class)
    public ResponseEntity<List<Fleur>> getAllFleurs() {
        List<Fleur> fleurs = fleurRepository.findAll();
        return ResponseEntity.ok(fleurs);
    }

    @GetMapping("/{id}")
    @JsonView(FleurView.Public.class)
    public ResponseEntity<Fleur> getFleurById(@PathVariable Long id) {
        Fleur fleur = fleurRepository.findById(id)
                .orElseThrow(() -> new FleurNotFoundException(id));
        return ResponseEntity.ok(fleur);
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFleur(@PathVariable Long id) {
        Fleur fleur = fleurRepository.findById(id)
                .orElseThrow(() -> new FleurNotFoundException(id));

        fleurRepository.delete(fleur);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/couleur/{couleur}")
    @JsonView(FleurView.Public.class)
    public ResponseEntity<List<Fleur>> getFleursByCouleur(@PathVariable String couleur) {
        List<Fleur> fleurs = fleurRepository.findByCouleurIgnoreCase(couleur);

        if (fleurs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(fleurs);
    }

    @GetMapping("/prix/max/{maxPrix}")
    @JsonView(FleurView.Public.class)
    public ResponseEntity<List<Fleur>> getFleursByMaxPrice(@PathVariable Double maxPrix) {
        List<Fleur> fleurs = fleurRepository.findByPrixLessThanEqual(maxPrix);

        if (fleurs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(fleurs);
    }

    @GetMapping("/admin/{id}")
    @JsonView(FleurView.Internal.class)
    public ResponseEntity<Fleur> getFleurAdmin(@PathVariable Long id) {
        Fleur fleur = fleurRepository.findById(id)
                .orElseThrow(() -> new FleurNotFoundException(id));
        return ResponseEntity.ok(fleur);
    }
}