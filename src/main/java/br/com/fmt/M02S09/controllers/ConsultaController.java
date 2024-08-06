package br.com.fmt.M02S09.controllers;

import br.com.fmt.M02S09.controllers.dto.ConsultaRequestDTO;
import br.com.fmt.M02S09.controllers.dto.ConsultaResponseDTO;
import br.com.fmt.M02S09.controllers.dto.ConsultaResponseListDTO;
import br.com.fmt.M02S09.services.ConsultaService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/consultas")
public class ConsultaController {

    private final ConsultaService consultaService;

    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_NUTRICIONISTA', 'SCOPE_ADMIN')")
    @PostMapping()
    public ConsultaResponseDTO salvarConsulta(@RequestBody ConsultaRequestDTO request) {
        return consultaService.salvarConsulta(request);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_NUTRICIONISTA', 'SCOPE_ADMIN')")
    @GetMapping()
    public List<ConsultaResponseListDTO> listarConsultas() {
        var consultas = consultaService.listarConsultas();
        if (consultas.isEmpty()){
            return null;
        } else {
            return consultas;
        }

    }

    @PreAuthorize("hasAnyAuthority('SCOPE_NUTRICIONISTA', 'SCOPE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ConsultaResponseDTO> search(@PathVariable long id) {
        ConsultaResponseDTO response = consultaService.buscarConsulta(id);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            throw new RuntimeException("404");
        }
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_NUTRICIONISTA', 'SCOPE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable long id) {
        consultaService.removerConsulta(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_NUTRICIONISTA', 'SCOPE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ConsultaResponseDTO> update(@PathVariable long id, @RequestBody ConsultaRequestDTO request) {
        ConsultaResponseDTO consulta = consultaService.atualizarConsulta(id, request);
        if (consulta != null) {
            return ResponseEntity.ok(consulta);
        } else {
            throw new RuntimeException("404");
        }
    }
    
}
