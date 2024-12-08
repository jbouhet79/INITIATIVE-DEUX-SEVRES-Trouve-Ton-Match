package fr.initiativedeuxsevres.trouve_ton_match.mapper;

import fr.initiativedeuxsevres.trouve_ton_match.dto.SecteurReseauDto;
import fr.initiativedeuxsevres.trouve_ton_match.dto.UtilisateurDto;
import fr.initiativedeuxsevres.trouve_ton_match.entity.SecteurReseau;
import fr.initiativedeuxsevres.trouve_ton_match.entity.Utilisateur;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SecteurReseauMapper {

    private final UtilisateurMapper utilisateurMapper;

    // Injection du mapper des utilisateurs
    public SecteurReseauMapper(UtilisateurMapper utilisateurMapper) {
        this.utilisateurMapper = utilisateurMapper;
    }

    /**
     * Convertit une entité SecteursReseaux en DTO SecteurReseauDto.
     *
     * @param entity L'entité SecteursReseaux à convertir.
     * @return Un DTO SecteurReseauDto.
     */
    public SecteurReseauDto toDto(SecteurReseau entity) {
        if (entity == null) {
            return null;
        }

        // Version optimisée
//        return new SecteurReseauDto(
//                entity.getId(),
//                entity.getLabel(),
//                entity.getUtilisateurs() != null
//                        ? entity.getUtilisateurs().stream()
//                        .map(utilisateurMapper::toDto) // Utilisation du mapper injecté
//                        .collect(Collectors.toList())
//                        : null
//        );

        // Version détaillée
        List<UtilisateurDto> utilisateursDto = new ArrayList<>();
        if (entity.getUtilisateurs() != null) {
            for (Utilisateur utilisateur : entity.getUtilisateurs()) {
                utilisateursDto.add(utilisateurMapper.toDto(utilisateur));
            }
        }

        return new SecteurReseauDto(
                entity.getId(),
                entity.getLabel(),
                utilisateursDto
        );
    }

    /**
     * Convertit un DTO SecteurReseauDto en entité SecteursReseaux.
     *
     * @param dto Le DTO SecteurReseauDto à convertir.
     * @return Une entité SecteursReseaux.
     */
    public SecteurReseau toEntity(SecteurReseauDto dto) {
        if (dto == null) {
            return null;
        }

        return new SecteurReseau(
                dto.getId(),
                dto.getLabel(),
                dto.getUtilisateurs() != null
                        ? dto.getUtilisateurs().stream()
                        .map(utilisateurDto -> utilisateurMapper.toEntity(utilisateurDto, null, null)) // Utilisation du mapper pour les utilisateurs avec les listes nulles
                        .collect(Collectors.toList())
                        : null
        );
    }
}

/**
 * Convertit un DTO SecteurReseauDto en entité SecteursReseaux.
 *
 * @param dto Le DTO SecteurReseauDto à convertir.
 * @return Une entité SecteursReseaux.
 */
//    public SecteurReseau toEntity(SecteurReseauDto dto) {
//        if (dto == null) {
//            return null;
//        }
//
//        SecteurReseau entity = new SecteurReseau();
//        entity.setId(dto.getId());
//        entity.setLabel(dto.getLabel());
//
//        // Si vous souhaitez mapper les utilisateurs du DTO vers les entités,
//        // ajoutez la logique ici en utilisant le mapper utilisateur.
//        if (dto.getUtilisateurs() != null) {
//            entity.setUtilisateurs(
//                    dto.getUtilisateurs().stream()
//                            .map(utilisateurMapper::toEntity) // Utilisation du mapper injecté
//                            .collect(Collectors.toList())
//            );
//        }
//
//        return entity;
//    }
// }