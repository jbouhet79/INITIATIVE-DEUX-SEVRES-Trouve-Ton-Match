package fr.initiativedeuxsevres.trouve_ton_match.controller;

import fr.initiativedeuxsevres.trouve_ton_match.dto.*;
import fr.initiativedeuxsevres.trouve_ton_match.entity.*;
import fr.initiativedeuxsevres.trouve_ton_match.mapper.UtilisateurMapper;
import fr.initiativedeuxsevres.trouve_ton_match.mapper.ParrainMapper;
import fr.initiativedeuxsevres.trouve_ton_match.mapper.PorteurMapper;
import fr.initiativedeuxsevres.trouve_ton_match.service.ParrainService;
import fr.initiativedeuxsevres.trouve_ton_match.service.PorteurService;
import fr.initiativedeuxsevres.trouve_ton_match.service.TypeAccompagnementService;
import fr.initiativedeuxsevres.trouve_ton_match.service.SecteurReseauService;
import fr.initiativedeuxsevres.trouve_ton_match.service.UtilisateurService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/creationCompte")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    private final TypeAccompagnementService typeAccompagnementService;

    private final SecteurReseauService secteurReseauService;

    @PostMapping(value = "/createutilisateur", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Utilisateur> createUtilisateur(@RequestBody UtilisateurDto utilisateurDto) {
        // sans Mapper
//        Utilisateur utilisateur = utilisateurService.save(utilisateurDto);

        // avec Mapper
//        List<TypeAccompagnementDto> accompagnements = typeAccompagnementService.findAll(); // Récupérer ou créer la liste
//        List<SecteurReseauDto> secteursReseaux = secteurReseauService.findAll(); // Récupérer ou créer la liste

//        Utilisateur utilisateur = utilisateurMapper.toEntity(utilisateurDto, accompagnements, secteursReseaux);
        Utilisateur utilisateur = utilisateurService.save(utilisateurDto);
        System.out.println("utilisateur dans le controlleur: " + utilisateur);

        return ResponseEntity.ok(utilisateur);
    }

    // La méthode getConnexionUtilisateur utilise @RequestParam pour obtenir le
    // codeUtilisateur de la requête.
    // Elle appelle ensuite le service pour récupérer les informations de
    // l’utilisateur et retourne les informations avec un statut HTTP approprié.
    @GetMapping(value = "/connexionutilisateur", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Utilisateur> getConnexionUtilisateur(@RequestParam String codeUtilisateur) {
        Utilisateur utilisateur = utilisateurService.findByCodeUtilisateur(codeUtilisateur);

        if (utilisateur != null) {
            return ResponseEntity.ok(utilisateur);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // La méthode répond aux requêtes HTTP POST envoyées à l’URL /checkutilisateur.
    // La réponse sera au format JSON
    @PostMapping(value = "/checkutilisateur", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Utilisateur> checkUtilisateur(@RequestBody UtilisateurDto utilisateurDto) {
        Optional<Utilisateur> utilisateur = utilisateurService.getByNomPrenomCode(
                utilisateurDto.getNomUtilisateur(),
                utilisateurDto.getPrenomUtilisateur(),
                utilisateurDto.getCodeUtilisateur());

        // Vérifie si un utilisateur est trouvé et renvoie une réponse adéquate
        return utilisateur
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping(value = "/accompagnementutilisateur", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Utilisateur> accompagnementUtilisateur(@RequestBody UtilisateurDto utilisateurDto) {
        List<TypeAccompagnement> accompagnementTypeList = typeAccompagnementService
                .findAllById(utilisateurDto.getAccompagnementTypeList().stream().map(dto -> dto.getId()).collect(Collectors.toList()));

        Utilisateur utilisateur = utilisateurService.findByCodeUtilisateur(utilisateurDto.getCodeUtilisateur());
        utilisateur.setAccompagnementTypeList(accompagnementTypeList);

        Utilisateur newUser = utilisateurService.save(utilisateur);

        return ResponseEntity.ok(newUser);
    }

    @PostMapping(value = "/secteursreseauxutilisateur", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Utilisateur> secteurReseauUtilisateur(@RequestBody UtilisateurDto utilisateurDto) {
        List<SecteurReseau> secteurReseauList = secteurReseauService
                .findAllById(utilisateurDto.getSecteurReseauList().stream().map(dto -> dto.getId()).collect(Collectors.toList()));

        Utilisateur utilisateur = utilisateurService.findByCodeUtilisateur(utilisateurDto.getCodeUtilisateur());
        utilisateur.setSecteurReseauList(secteurReseauList);

        Utilisateur newUser = utilisateurService.save(utilisateur);

        return ResponseEntity.ok(newUser);
    }

    @PostMapping(value = "/filtres", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UtilisateurDto> selectionFiltres(@RequestBody UtilisateurDto utilisateurDto) {
        try {
            UtilisateurDto savedUtilisateur = utilisateurService.selectionFiltres(utilisateurDto);
            return ResponseEntity.ok(savedUtilisateur);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UtilisateurDto> getUtilisateurById(@PathVariable Long id) {
        UtilisateurDto utilisateur = utilisateurService.findById(id);
        return ResponseEntity.ok(utilisateur);
    }

}
