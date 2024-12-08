package fr.initiativedeuxsevres.trouve_ton_match.mapper;

import fr.initiativedeuxsevres.trouve_ton_match.dto.SecteurReseauDto;
import fr.initiativedeuxsevres.trouve_ton_match.dto.TypeAccompagnementDto;
import fr.initiativedeuxsevres.trouve_ton_match.dto.UtilisateurDto;
import fr.initiativedeuxsevres.trouve_ton_match.entity.TypeAccompagnement;
import fr.initiativedeuxsevres.trouve_ton_match.entity.Utilisateur;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TypeAccompagnementMapper {

    private final UtilisateurMapper utilisateurMapper;

    // Injection du mapper des utilisateurs
    public TypeAccompagnementMapper(UtilisateurMapper utilisateurMapper) {
        this.utilisateurMapper = utilisateurMapper;
    }

    /**
     * Convertit une entité TypeAccompagnement en DTO TypeAccompagnementDto.
     *
     * @param entity L'entité TypeAccompagnement à convertir.
     * @return Un DTO TypeAccompagnementDto.
     */
    public TypeAccompagnementDto toDto(TypeAccompagnement entity) {
        if (entity == null) {
            return null;
        }

        // Version courte
//        return new TypeAccompagnementDto(
//                entity.getId(),
//                entity.getLabel(),
//                null
//        );

        // Version détaillée
        List<UtilisateurDto> utilisateursDto = new ArrayList<>();
        if (entity.getUtilisateurs() != null) {
            for (Utilisateur utilisateur : entity.getUtilisateurs()) {
                utilisateursDto.add(utilisateurMapper.toDto(utilisateur));
            }
        }

        return new TypeAccompagnementDto(
                entity.getId(),
                entity.getLabel(),
                utilisateursDto
        );
    }

    /**
     * Convertit un DTO TypeAccompagnementDto en entité TypeAccompagnement.
     *
     * @param dto Le DTO TypeAccompagnementDto à convertir.
     * @return Une entité TypeAccompagnement.
     */
    public TypeAccompagnement toEntity(TypeAccompagnementDto dto) {
        if (dto == null) {
            return null;
        }

        return new TypeAccompagnement(
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