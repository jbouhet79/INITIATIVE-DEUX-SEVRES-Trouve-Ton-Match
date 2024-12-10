package fr.initiativedeuxsevres.trouve_ton_match.service;

import fr.initiativedeuxsevres.trouve_ton_match.dto.*;
import fr.initiativedeuxsevres.trouve_ton_match.mapper.SecteurReseauMapper;
import fr.initiativedeuxsevres.trouve_ton_match.mapper.TypeAccompagnementMapper;
import fr.initiativedeuxsevres.trouve_ton_match.mapper.UtilisateurMapper;
import fr.initiativedeuxsevres.trouve_ton_match.entity.*;
import fr.initiativedeuxsevres.trouve_ton_match.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor // Cette annotation génère automatiquement un constructeur pour tous les champs
                         // final ou marqués comme @NonNull
public class UtilisateurService {

    private final ParrainService parrainService;
    private final PorteurService porteurService;
    private final SecteurReseauService secteurReseauService;
    private final TypeAccompagnementService typeAccompagnementService;

    private final UtilisateurMapper utilisateurMapper;
    private final TypeAccompagnementMapper typeAccompagnementMapper;
    private final SecteurReseauMapper secteurReseauMapper;

    private final ParrainRepository parrainRepository;
    private final PorteurRepository porteurRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final SecteurReseauRepository secteurReseauRepository;
    private final TypeAccompagnementRepository typeAccompagnementRepository;

    // Méthode pour trouver un utilisateur par ID
    public UtilisateurDto findById(Long idUtilisateur) {

        Utilisateur entity = utilisateurRepository.findById(idUtilisateur)
                .orElseThrow(
                        () -> new EntityNotFoundException("Utilisateur avec ID " + idUtilisateur + " non trouvé."));

        return utilisateurMapper.toDto(entity);
    }

    // La méthode findByCodeUtilisateur recherche d’abord un Parrain par
    // codeUtilisateur.
    // Si aucun Parrain n’est trouvé, elle recherche un Porteur. Si aucun
    // utilisateur n’est trouvé, elle retourne null.
    public Utilisateur findByCodeUtilisateur(String codeUtilisateur) {
        Parrain parrain = parrainRepository.findByCodeUtilisateur(codeUtilisateur);
        if (parrain != null) {
            return  parrain;
        }

        Porteur porteur = porteurRepository.findByCodeUtilisateur(codeUtilisateur);
        if (porteur != null) {
            return porteur;
        }

        return null;
    }

    // Méthode qui renvoie un utilisateur qui peut être un parrain ou un porteur
    public Optional<Utilisateur> getByNomPrenomCode(String nom, String prenom, String code) {
        // Essayez de trouver l'utilisateur dans le repository parrain
        Optional<Parrain> parrain = parrainRepository.findByNomUtilisateurAndPrenomUtilisateurAndCodeUtilisateur(nom,
                prenom, code);

        // Si trouvé dans parrain, renvoie-le
        if (parrain.isPresent()) {
            return Optional.of(parrain.get());
        }

        Optional<Porteur> porteur = porteurRepository.findByNomUtilisateurAndPrenomUtilisateurAndCodeUtilisateur(nom,
                prenom, code);

        if (porteur.isPresent()) {
            return Optional.of(porteur.get());
        }

        // Sinon, essayez de trouver l'utilisateur dans le repository porteur
        return Optional.empty();
    }

    public Utilisateur save(Utilisateur utilisateur) {
        if (utilisateur instanceof Parrain) {
            return parrainRepository.save((Parrain) utilisateur);
        } else {
            return porteurRepository.save((Porteur) utilisateur);
        }
    }

    public Utilisateur save(UtilisateurDto utilisateurDto) {

        // avant le Mapper
//        System.out.println("Type d'utilisateur: " + utilisateurDto.getTypeUtilisateur());
//         if(utilisateurDto.getTypeUtilisateur().equals("parrain")) {
////        if (utilisateurDto.getType() != null && utilisateurDto.getType().name().toLowerCase().equals("parrain")) {
//            return parrainService.createParrain(utilisateurDto);
//        } else {
//            return porteurService.createPorteur(utilisateurDto);
//        }

         // avec le Mapper
        System.out.println("Type d'utilisateur dans le service: " + utilisateurDto.getTypeUtilisateur());

        // Mapper pour convertir le DTO en entité
        List<TypeAccompagnementDto> accompagnements = typeAccompagnementService.findAll(); // Récupérer ou créer la liste
        List<SecteurReseauDto> secteursReseaux = secteurReseauService.findAll(); // Récupérer ou créer la liste
        Utilisateur utilisateur = utilisateurMapper.toEntity(utilisateurDto, accompagnements, secteursReseaux);

        // Sauvegarder l'utilisateur
        if (utilisateur instanceof Parrain) {
            return parrainRepository.save((Parrain) utilisateur);
        } else {
            return porteurRepository.save((Porteur) utilisateur);
        }
    }

//    public Utilisateur mettreAJourFiltres(UtilisateurDto utilisateurDto) {
//        Utilisateur utilisateur = findByCodeUtilisateur(utilisateurDto.getCodeUtilisateur());
//
//        // Mettre à jour les accompagnements
//        if (utilisateurDto.getAccompagnementTypeList() != null) {
//            List<TypeAccompagnement> accompagnements = typeAccompagnementService
//                    .findAllById(utilisateurDto.getAccompagnementTypeList());
//            utilisateur.setAccompagnementTypeList(accompagnements);
//        }
//
//        // Mettre à jour les secteurs/réseaux
//        if (utilisateurDto.getSecteurReseauList() != null) {
//            List<SecteurReseau> secteurs = secteurReseauService.findAllById(utilisateurDto.getSecteurReseauList());
//            utilisateur.setSecteurReseauList(secteurs);
//        }
//
//        return save(utilisateur);
//    }

    public UtilisateurDto selectionFiltres(UtilisateurDto utilisateurDto) {
        // Avec les mappers
        // Mapper le DTO en entité pour l'opération métier
        // Utilisateur utilisateur = utilisateurMapper.toEntity(utilisateurDto, null, null);


        // 1. Récupérer l'utilisateur par son ID
        Utilisateur utilisateur = utilisateurMapper.toEntity(findById(utilisateurDto.getIdUtilisateur()), null, null);
        if (utilisateur == null) {
            throw new EntityNotFoundException("Utilisateur non trouvé"); // Ou une autre exception personnalisée
        }

        // 2. Vérification des listes d'IDs
        if (utilisateurDto.getAccompagnementTypeList() == null || utilisateurDto.getSecteurReseauList() == null) {
            throw new IllegalArgumentException("Les listes d'IDs ne peuvent pas être nulles");
        }

        // 3. Récupérer dans des listes (accompagnements et secteurs), les types d'accompagnement et secteurs par leurs IDs
        List<TypeAccompagnement> accompagnements = utilisateurDto
                .getAccompagnementTypeList()
                .stream()
                .map(dto -> typeAccompagnementRepository.findById(dto.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        List<SecteurReseau> secteurs = utilisateurDto.getSecteurReseauList().stream().map(dto -> secteurReseauRepository.findById(dto.getId())).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());

        // Convertir les entités en DTOs
//        List<TypeAccompagnementDto> accompagnementDtos = accompagnements.stream()
//                .map(typeAccompagnementMapper::toDto)
//                .collect(Collectors.toList());
//
//        List<SecteurReseauDto> secteurDtos = secteurs.stream()
//                .map(secteurReseauMapper::toDto)
//                .collect(Collectors.toList());

        // 4. Associer les entités à l'utilisateur
//        if (accompagnements != null && !accompagnements.isEmpty()) { // vérifie si la liste "accompagnements" n'est pas vide
//            List<Long> idAccompagnements = new ArrayList<Long>(); // Constitue avec la boucle qui suit la liste des Ids
//            for(TypeAccompagnement accomp : accompagnements)
//            {
//                if(accomp.isPresent()){
//                    idAccompagnements.add(accomp.get().getId());
//                }
//
//            }
            utilisateur.setAccompagnementTypeList(accompagnements); // mise à jour de la propriété AccompagnementTypeList de utilisateur avec la liste des accompagnements récupérés
//        }

//        if (secteurs != null && !secteurs.isEmpty()) {
//            List<Long> idSecteurs = new ArrayList<Long>();
//            for(SecteurReseau sect : secteurs)
//            {
//                idSecteurs.add(sect.getId());
//            }
            utilisateur.setSecteurReseauList(secteurs);
//        }

        // 5. Sauvegarder les modifications de l'utilisateur
        Utilisateur updatedUtilisateur = save(utilisateur);

        // 6. Convertir l'entité sauvegardée en DTO
        UtilisateurDto updatedUtilisateurDto = utilisateurMapper.toDto(updatedUtilisateur);

        return updatedUtilisateurDto;


        // version hybride Mapper

//        // 1. Récupérer l'utilisateur par son ID
//        UtilisateurDto utilisateur = findById(utilisateurDto.getIdUtilisateur());
//        if (utilisateur == null) {
//            throw new EntityNotFoundException("Utilisateur non trouvé"); // Ou une autre exception personnalisée
//        }
//
//        // 2. Vérification des listes d'IDs
//        if (utilisateurDto.getAccompagnementTypeList() == null || utilisateurDto.getSecteurReseauList() == null) {
//            throw new IllegalArgumentException("Les listes d'IDs ne peuvent pas être nulles");
//        }
//
//        // 3. Récupérer dans des listes (accompagnements et secteurs), les types d'accompagnement et secteurs par leurs IDs
//        List<TypeAccompagnement> accompagnements = typeAccompagnementService
//                .findAllById(utilisateurDto.getAccompagnementTypeList());
//        List<SecteurReseau> secteurs = secteurReseauService.findAllById(utilisateurDto.getSecteurReseauList());
//
//        // Convertir les entités en DTOs
//        List<TypeAccompagnementDto> accompagnementDtos = accompagnements.stream()
//                .map(typeAccompagnementMapper::toDto)
//                .collect(Collectors.toList());
//
//        List<SecteurReseauDto> secteurDtos = secteurs.stream()
//                .map(secteurReseauMapper::toDto)
//                .collect(Collectors.toList());
//
//        // 4. Associer les entités à l'utilisateur
//        if (accompagnements != null && !accompagnements.isEmpty()) { // vérifie si la liste "accompagnements" n'est pas vide
//            List<Long> idAccompagnements = new ArrayList<Long>(); // Constitue avec la boucle qui suit la liste des Ids
//            for(TypeAccompagnement accomp : accompagnements)
//            {
//                idAccompagnements.add(accomp.getId());
//            }
//            utilisateur.setAccompagnementTypeList(idAccompagnements); // mise à jour de la propriété AccompagnementTypeList de utilisateur avec la liste des accompagnements récupérés
//        }
//
//        if (secteurs != null && !secteurs.isEmpty()) {
//            List<Long> idSecteurs = new ArrayList<Long>();
//            for(SecteurReseau sect : secteurs)
//            {
//                idSecteurs.add(sect.getId());
//            }
//            utilisateur.setSecteurReseauList(idSecteurs);
//        }
//
//        // 5. Sauvegarder les modifications de l'utilisateur
//        // return save(utilisateur);
//
//        // UtilisateurMapper::toEntity attend des DTOs
//        Utilisateur utilisateurEntity = utilisateurMapper.toEntity(utilisateur, accompagnementDtos, secteurDtos);
//        Utilisateur savedUtilisateur = save(utilisateurEntity);
//
//        // 6. Convertir l'entité sauvegardée en DTO
//        UtilisateurDto savedUtilisateurDto = utilisateurMapper.toDto(savedUtilisateur);
//
//        return savedUtilisateurDto;
    }

}
