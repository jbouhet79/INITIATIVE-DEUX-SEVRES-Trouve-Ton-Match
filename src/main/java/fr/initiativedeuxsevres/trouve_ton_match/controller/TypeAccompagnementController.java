package fr.initiativedeuxsevres.trouve_ton_match.controller;

import fr.initiativedeuxsevres.trouve_ton_match.dto.SecteurReseauDto;
import fr.initiativedeuxsevres.trouve_ton_match.dto.TypeAccompagnementDto;
import fr.initiativedeuxsevres.trouve_ton_match.entity.TypeAccompagnement;
import fr.initiativedeuxsevres.trouve_ton_match.service.TypeAccompagnementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accompagnement")
public class TypeAccompagnementController {

    private final TypeAccompagnementService typeAccompagnementService;

    @GetMapping(value = "/listeAccompagnement", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TypeAccompagnementDto>> getTypesAccompagnement() {
        List<TypeAccompagnementDto> typeAccompagnement = typeAccompagnementService.findAll();
        System.out.println("typeAccompagnementService.findAll(): " + typeAccompagnement);
        return ResponseEntity.ok(typeAccompagnement);
    }

}
