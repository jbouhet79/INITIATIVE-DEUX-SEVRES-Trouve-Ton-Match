package fr.initiativedeuxsevres.trouve_ton_match.controller;

import fr.initiativedeuxsevres.trouve_ton_match.dto.ParrainDto;
import fr.initiativedeuxsevres.trouve_ton_match.dto.TypeAccompagnementDto;
import fr.initiativedeuxsevres.trouve_ton_match.entity.Parrain;
import fr.initiativedeuxsevres.trouve_ton_match.service.ParrainService;
import fr.initiativedeuxsevres.trouve_ton_match.service.TypeAccompagnementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/creationCompte")
public class ParrainController {

    private final ParrainService parrainService;

    @PostMapping(value = "/completercompteparrain", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Parrain> completercompteparrain(@RequestBody ParrainDto parrainDto) {
        // Sauvegarder l'entité
        Parrain updatedParrain = parrainService.save(parrainDto);

        // Retourner la réponse avec l'entité mise à jour
        return ResponseEntity.ok(updatedParrain);
    }
}

