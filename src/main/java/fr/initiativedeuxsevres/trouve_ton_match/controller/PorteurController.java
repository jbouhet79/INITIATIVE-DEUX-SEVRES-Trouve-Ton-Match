package fr.initiativedeuxsevres.trouve_ton_match.controller;

import fr.initiativedeuxsevres.trouve_ton_match.dto.ParrainDto;
import fr.initiativedeuxsevres.trouve_ton_match.dto.PorteurDto;
import fr.initiativedeuxsevres.trouve_ton_match.entity.Parrain;
import fr.initiativedeuxsevres.trouve_ton_match.entity.Porteur;
import fr.initiativedeuxsevres.trouve_ton_match.service.ParrainService;
import fr.initiativedeuxsevres.trouve_ton_match.service.PorteurService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/creationCompte")
public class PorteurController {

    private final PorteurService porteurService;

    @PostMapping(value = "/completercompteporteur", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Porteur> completercompteporteur(@RequestBody PorteurDto porteurDto) {
        // Sauvegarder l'entité
        Porteur updatedPorteur = porteurService.save(porteurDto);

        // Retourner la réponse avec l'entité mise à jour
        return ResponseEntity.ok(updatedPorteur);
    }
}