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

    private final ParrainService parrainService;

    private final PorteurService porteurService;

    private final UtilisateurService utilisateurService;

    private final TypeAccompagnementService typeAccompagnementService;

    private final SecteurReseauService secteurReseauService;

//    private final UtilisateurMapper utilisateurMapper;

//    private final ParrainMapper parrainMapper;

//    private final PorteurMapper porteurMapper;


    // @Autowired
    // public UtilisateurController(ParrainService parrainService, PorteurService
    // porteurService, UtilisateurService utilisateurService,
    // TypeAccompagnementService typeAccompagnementService) {
    // this.parrainService = parrainService;
    // this.porteurService = porteurService;
    // this.utilisateurService = utilisateurService;
    // this.typeAccompagnementService = typeAccompagnementService;
    // this.secteurReseauService = secteurReseauService;
    // }

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

//    @PostMapping(value = "/completercompteparrain", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Parrain> completercompteparrain(@RequestBody ParrainDto parrainDto) {
//
//        System.out.println("Données reçues : " + parrainDto);
//
//        Parrain parrain = parrainService.findById(parrainDto.getIdUtilisateur());
//
//        // Vérifier si le parrain existe dans la base
//        if (parrain == null) {
//            // Si le parrain n'existe pas, retourner un objet Parrain vide
//            Parrain emptyParrain = new Parrain();
//            emptyParrain.setIdUtilisateur(parrainDto.getIdUtilisateur());
//            System.out.println("Parrain introuvable, renvoi d'un objet vide.");
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(emptyParrain);
//        }
//
//        System.out.println("Compte complété");
//        System.out.println("id :" + parrainDto.getIdUtilisateur());
//
//        // Mise à jour des données
//        parrain.setPresentationParcours(parrainDto.getPresentationParcours());
//        parrain.setBranchesReseau(parrainDto.getBranchesReseau());
//        parrain.setDomainesExpertise(parrainDto.getDomainesExpertise());
//        parrain.setSecteurGeographique(parrainDto.getSecteurGeographique());
//        parrain.setDisponibilites(parrainDto.getDisponibilites());
//
//        Parrain updatedParrainEntity = parrainMapper.toEntity(parrainDto);  // Utiliser un Mapper pour convertir le DTO en entité
//        Parrain updatedParrain = parrainService.save(updatedParrainEntity);  // Sauvegarder l'entité dans la base
//
//
////        Parrain updatedParrain = parrainService.save(parrain);
//        return ResponseEntity.ok(updatedParrain);
//    }

    @PostMapping (value = "/completercompteparrain", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Parrain> completercompteparrain(@RequestBody ParrainDto parrainDto) {

//        System.out.println("Données reçues : " + parrainDto);
//
//        // Récupérer le parrain existant par son ID
//        Parrain parrainExistant = parrainService.findById(parrainDto.getIdUtilisateur());
//        System.out.println("Récupérer le parrain existant par son ID dans /completercompteparrain : " + parrainDto);
//        System.out.println("Récupérer le NOM du parrain existant par son ID dans /completercompteparrain : " + parrainDto.getNomUtilisateur());
//
//        // Vérifier si le parrain existe dans la base
//        if (parrainExistant == null) {
//            // Si le parrain n'existe pas, retourner un objet Parrain vide
//            Parrain emptyParrain = new Parrain();
//            emptyParrain.setIdUtilisateur(parrainDto.getIdUtilisateur());
//            System.out.println("Parrain introuvable, renvoi d'un objet vide.");
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(emptyParrain);
//        }
//
//        // Récupérer la liste des Accompagnements et Secteurs
//        List<TypeAccompagnementDto> accompagnements = typeAccompagnementService.findAll();
//        List<SecteurReseauDto> secteursReseaux = secteurReseauService.findAll();
//
//        // Mapper le DTO en entité, en incluant les accompagnements et secteurs
//        Parrain updatedParrainEntity =  parrainMapper.toEntity(parrainDto, parrainExistant, accompagnements, secteursReseaux);
//        System.out.println("Entité mise à jour après le mapping : " + updatedParrainEntity);
//        System.out.println("parrainDto dans Mapper le DTO en entité: " + parrainDto);
//        System.out.println("updatedParrainEntity via Mapper le DTO en entité: " + updatedParrainEntity);

        // Sauvegarder l'entité
        Parrain updatedParrain = parrainService.save(parrainDto);

        // Retourner la réponse avec l'entité mise à jour
        return ResponseEntity.ok(updatedParrain);
    }

    @PostMapping(value = "/completercompteporteur", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Porteur> completercompteporteur(@RequestBody PorteurDto porteurDto) {

//        System.out.println("Données reçues : " + porteurDto);
//
//        Porteur porteurExistant = porteurService.findById(porteurDto.getIdUtilisateur());
//
//        // Vérifier si le porteur existe dans la base
//        if (porteurExistant == null) {
//            // Si le porteur n'existe pas, retourner un objet Porteur vide
//            Porteur emptyPorteur = new Porteur();
//            emptyPorteur.setIdUtilisateur(porteurDto.getIdUtilisateur());
//            System.out.println("Porteur introuvable, renvoi d'un objet vide.");
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(emptyPorteur);
//        }
//
//        System.out.println("Compte complété");
//        System.out.println("id :" + porteurDto.getIdUtilisateur());
//
//        // Récupérer la liste des Accompagnements et Secteurs
//        List<TypeAccompagnementDto> accompagnements = typeAccompagnementService.findAll();
//        List<SecteurReseauDto> secteursReseaux = secteurReseauService.findAll();
//
//        // Mapper le DTO en entité, en incluant les accompagnements et secteurs
//        Porteur updatedPorteurEntity =  porteurMapper.toEntity(porteurDto, porteurExistant, accompagnements, secteursReseaux);
//        System.out.println("Entité mise à jour après le mapping : " + updatedPorteurEntity);
//        System.out.println("parrainDto dans Mapper le DTO en entité: " + porteurDto);
//        System.out.println("updatedParrainEntity via Mapper le DTO en entité: " + updatedPorteurEntity);

        // Sauvegarder l'entité
        Porteur updatedPorteur = porteurService.save(porteurDto);

        // Retourner la réponse avec l'entité mise à jour
        return ResponseEntity.ok(updatedPorteur);
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
