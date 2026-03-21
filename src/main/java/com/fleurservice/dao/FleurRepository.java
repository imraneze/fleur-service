package com.fleurservice.dao;

import com.fleurservice.model.Fleur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FleurRepository extends JpaRepository<Fleur, Long> {

    List<Fleur> findByCouleurIgnoreCase(String couleur);

}