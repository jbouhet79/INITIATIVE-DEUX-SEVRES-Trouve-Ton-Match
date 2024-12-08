package fr.initiativedeuxsevres.trouve_ton_match.service;

import fr.initiativedeuxsevres.trouve_ton_match.dto.*;
import fr.initiativedeuxsevres.trouve_ton_match.entity.Porteur;
import fr.initiativedeuxsevres.trouve_ton_match.entity.Porteur;
import fr.initiativedeuxsevres.trouve_ton_match.mapper.ParrainMapper;
import fr.initiativedeuxsevres.trouve_ton_match.mapper.PorteurMapper;
import fr.initiativedeuxsevres.trouve_ton_match.repository.PorteurRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PorteurService {

    private final TypeAccompagnementService typeAccompagnementService;
    private final SecteurReseauService secteurReseauService;
    private final PorteurService porteurService;

    private final PorteurMapper porteurMapper;

    @Autowired
    private PorteurRepository porteurRepository;

    // 1ère version identique au PorteurService

    // public PorteurDto createPorteur (PorteurDto newPorteurDto) {
    //
    // // On transforme un Dto en entité
    // Porteur nouveauPorteurEntity = new Porteur(
    // null,
    // newPorteurDto.getNomUtilisateur(),
    // newPorteurDto.getPrenomUtilisateur(),
    // newPorteurDto.getEntrepriseUtilisateur(),
    // newPorteurDto.getPlateformeUtilisateur(),
    // newPorteurDto.getCodeUtilisateur(),
    // newPorteurDto.getDateLancement(),
    // newPorteurDto.getDomaine(),
    // newPorteurDto.getBesoins(),
    // newPorteurDto.getLieuActivite(),
    // newPorteurDto.getDisponibilites()
    // );
    //
    // Porteur saved = porteurRepository.save(nouveauPorteurEntity);
    //
    // // On transforme l'entité sauvée en un nouveau Dto pour le renvoyer au front
    // (via le controlleur)
    // return new PorteurDto(
    // saved.getIdUtilisateur(),
    // saved.getNomUtilisateur(),
    // saved.getPrenomUtilisateur(),
    // saved.getEntrepriseUtilisateur(),
    // saved.getPlateformeUtilisateur(),
    // saved.getCodeUtilisateur(),
    // saved.getDateLancement(),
    // saved.getDomaine(),
    // saved.getBesoins(),
    // saved.getLieuActivite(),
    // saved.getDisponibilites()
    // );
    // }

    // ou seconde version :

    public Porteur createPorteur(UtilisateurDto newPorteurDto) {

        // On transforme un Dto en entité
        Porteur nouveauPorteurEntity = Porteur.builder()
                .nomUtilisateur(newPorteurDto.getNomUtilisateur())
                .prenomUtilisateur(newPorteurDto.getPrenomUtilisateur())
                .entrepriseUtilisateur(newPorteurDto.getEntrepriseUtilisateur())
                .plateformeUtilisateur(newPorteurDto.getPlateformeUtilisateur())
                .codeUtilisateur(newPorteurDto.getCodeUtilisateur())
                .typeUtilisateur(newPorteurDto.getTypeUtilisateur())
                .build();

        return porteurRepository.save(nouveauPorteurEntity);
    }
    // A REVOIR
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // private static Porteur porteurDtoToEntity(PorteurDto newPorteurDto) {
    // Porteur nouveauPorteurEntity = new Porteur(
    // // --> id à null pour permettre la création d'autres comptes utilisateurs
    // avec un nouvel id
    // // ==> si on veut toujours créer une nouvelle entité avec un nouvel ID
    // null,
    //
    // // Cela signifie que chaque fois que cette méthode est appelée, une nouvelle
    // entité sans ID (ou avec un ID généré automatiquement par la base de données)
    // sera créée
    // // newPorteurDto.getIdUtilisateur(),
    // //L’ID de l’entité Porteur est pris du DTO newPorteurDto.
    // // Cela permet de conserver l’ID existant de l’utilisateur si le DTO en
    // contient un,
    // // ce qui peut être utile pour des opérations de mise à jour où l’ID doit
    // être préservé.
    // // ==> si tu veux conserver l’ID de l’entité existante pour des opérations de
    // mise à jour
    // newPorteurDto.getNomUtilisateur(),
    // newPorteurDto.getPrenomUtilisateur(),
    // newPorteurDto.getEntrepriseUtilisateur(),
    // newPorteurDto.getPlateformeUtilisateur(),
    // newPorteurDto.getCodeUtilisateur(),
    // newPorteurDto.getTypeUtilisateur(),
    // newPorteurDto.getDateLancement(),
    // newPorteurDto.getDomaine(),
    // newPorteurDto.getBesoins(),
    // newPorteurDto.getLieuActivite(),
    // newPorteurDto.getDisponibilites()
    // );
    // return nouveauPorteurEntity;
    // }

    public Porteur completerComptePorteur(Long idUtilisateur, String dateLancement, String domaine,
            String descriptifActivite, String besoins, String lieuActivite, String disponibilites) {
        // Récupérer le porteur par son ID
        Porteur porteur = porteurRepository.findById(idUtilisateur)
                .orElseThrow(() -> new RuntimeException("Porteur non trouvé"));

        // Mettre à jour les champs du porteur
        porteur.setDateLancement(dateLancement);
        porteur.setDomaine(domaine);
        porteur.setDescriptifActivite(descriptifActivite);
        porteur.setBesoins(besoins);
        porteur.setLieuActivite(lieuActivite);
        porteur.setDisponibilites(disponibilites);

        // Sauvegarder le porteur mis à jour
        return porteurRepository.save(porteur);
    }

    public Porteur findById(Long id) {
        Optional<Porteur> porteur = porteurRepository.findById(id);
        return porteur.isPresent() ? porteur.get() : null;
    }

    public Porteur save(Porteur porteur) {
        System.out.println("Sauvegarde en cours pour : " + porteur);
        return porteurRepository.save(porteur);
    }

    public Porteur save(PorteurDto porteurDto) {

        Porteur porteurExistant = porteurService.findById(porteurDto.getIdUtilisateur());
        System.out.println("Récupérer le porteur existant par son ID dans /completercompteporteur : " + porteurDto);
        System.out.println("Récupérer le NOM du porteur existant par son ID dans /completercompteporteur : "
                + porteurDto.getNomUtilisateur());

        // Vérifier si le porteur existe dans la base
        if (porteurExistant == null) {
            // Si le porteur n'existe pas, retourner un objet Porteur vide
            Porteur emptyPorteur = new Porteur();
            emptyPorteur.setIdUtilisateur(porteurDto.getIdUtilisateur());
            System.out.println("Porteur introuvable, renvoi d'un objet vide.");
            return emptyPorteur;
        }

        List<TypeAccompagnementDto> accompagnements = typeAccompagnementService.findAll();
        List<SecteurReseauDto> secteursReseaux = secteurReseauService.findAll();
        Porteur updatedPorteurEntity = porteurMapper.toEntity(porteurDto, porteurExistant, accompagnements,
                secteursReseaux);
        System.out.println("Sauvegarde en cours pour : " + updatedPorteurEntity);
        System.out.println("Sauvegarde en cours (nom) : " + updatedPorteurEntity.getNomUtilisateur());
        return porteurRepository.save(updatedPorteurEntity);
    }
}
