package fleurs.web.controller;

import fleurs.dao.FleurRepository;
import fleurs.model.Fleur;
import fleurs.web.exceptions.FleurNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fleurs")
public class FleurController {

    private final FleurRepository fleurRepository;

    public FleurController(FleurRepository fleurRepository) {
        this.fleurRepository = fleurRepository;
    }

    @GetMapping
    public ResponseEntity<List<Fleur>> getAllFleurs() {
        return ResponseEntity.ok(fleurRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fleur> getFleurById(@PathVariable Long id) {
        Fleur fleur = fleurRepository.findById(id)
                .orElseThrow(() -> new FleurNotFoundException(id));
        return ResponseEntity.ok(fleur);
    }

    @GetMapping("/couleur/{couleur}")
    public ResponseEntity<List<Fleur>> getFleursByCouleur(@PathVariable String couleur) {
        return ResponseEntity.ok(fleurRepository.findByCouleur(couleur));
    }

    @PostMapping
    public ResponseEntity<Fleur> createFleur(@Valid @RequestBody Fleur fleur) {
        Fleur savedFleur = fleurRepository.save(fleur);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFleur);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fleur> updateFleur(@PathVariable Long id, @Valid @RequestBody Fleur fleurDetails) {
        Fleur fleur = fleurRepository.findById(id)
                .orElseThrow(() -> new FleurNotFoundException(id));
        fleur.setNom(fleurDetails.getNom());
        fleur.setCouleur(fleurDetails.getCouleur());
        fleur.setPrix(fleurDetails.getPrix());
        fleur.setSaisonnalite(fleurDetails.getSaisonnalite());
        return ResponseEntity.ok(fleurRepository.save(fleur));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFleur(@PathVariable Long id) {
        Fleur fleur = fleurRepository.findById(id)
                .orElseThrow(() -> new FleurNotFoundException(id));
        fleurRepository.delete(fleur);
        return ResponseEntity.noContent().build();
    }
}