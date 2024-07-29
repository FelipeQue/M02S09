package br.com.fmt.M02S09.controllers;

import br.com.fmt.M02S09.controllers.dto.NutricionistaRequestDTO;
import br.com.fmt.M02S09.controllers.dto.NutricionistaResponseDTO;
import br.com.fmt.M02S09.services.NutricionistaService;
import br.com.fmt.M02S09.services.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/nutricionistas")
public class NutricionistaController {

    private final NutricionistaService nutricionistaService;
    private final TokenService tokenService;

    public NutricionistaController(NutricionistaService nutricionistaService, TokenService tokenService) {
        this.nutricionistaService = nutricionistaService;
        this.tokenService = tokenService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping()
    public NutricionistaResponseDTO salvarNutricionista(@RequestBody NutricionistaRequestDTO request) {
        return nutricionistaService.salvarNutricionista(request);
    }

    @PreAuthorize("hasAnyAuthority('NUTRICIONISTA') or hasAnyAuthority('ADMIN') or hasAnyAuthority('PACIENTE')")
    @GetMapping()
    public List<NutricionistaResponseDTO> listarnutricionistas(
            @RequestHeader(name="Authorization") String token
    ) {
        //tokenService.validaToken(token, "NUTRICIONISTA");
        var nutricionistas = nutricionistaService.listarNutricionistas();
        if (nutricionistas.isEmpty()){
            return null;
        } else {
            return nutricionistas;
        }

    }

    @PreAuthorize("hasAnyAuthority('NUTRICIONISTA') or hasAnyAuthority('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<NutricionistaResponseDTO> search(@PathVariable long id) {
        NutricionistaResponseDTO response = nutricionistaService.buscarNutricionista(id);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            throw new RuntimeException("404");
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable long id) {
        nutricionistaService.removerNutricionista(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyAuthority('NUTRICIONISTA') or hasAnyAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<NutricionistaResponseDTO> update(@PathVariable long id, @RequestBody NutricionistaRequestDTO request) {
        NutricionistaResponseDTO nutricionista = nutricionistaService.atualizarNutricionista(id, request);
        if (nutricionista != null) {
            return ResponseEntity.ok(nutricionista);
        } else {
            throw new RuntimeException("404");
        }
    }
    
    
    
    
}
