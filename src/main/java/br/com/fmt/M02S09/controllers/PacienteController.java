package br.com.fmt.M02S09.controllers;

import br.com.fmt.M02S09.controllers.dto.PacienteRequestDTO;
import br.com.fmt.M02S09.controllers.dto.PacienteResponseDTO;
import br.com.fmt.M02S09.services.PacienteService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    @PostMapping()
    public PacienteResponseDTO salvarPaciente(@RequestBody PacienteRequestDTO request,
                                              //@RequestParam("date")
                                              @DateTimeFormat(pattern = "dd/MM/yyyy") Date date) {
        return pacienteService.salvarPaciente(request);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    @GetMapping()
    public List<PacienteResponseDTO> listarPacientes() {
        var Pacientes = pacienteService.listarPacientes();
        if (Pacientes.isEmpty()){
            return null;
        } else {
            return Pacientes;
        }

    }

    @PreAuthorize("hasAnyAuthority('SCOPE_PACIENTE', 'SCOPE_NUTRICIONISTA', 'SCOPE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> search(
            @PathVariable long id,
            @RequestHeader(name="Authorization") String token
    ) {
        PacienteResponseDTO response = pacienteService.buscarPaciente(id);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            throw new RuntimeException("404");
        }
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable long id) {
        pacienteService.removerPaciente(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_PACIENTE', 'SCOPE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> update(@PathVariable long id,
                                                      @RequestBody PacienteRequestDTO request,
                                                      //@RequestParam("date")
                                                      @DateTimeFormat(pattern = "dd/MM/yyyy") Date date) {
        PacienteResponseDTO paciente = pacienteService.atualizarPaciente(id, request);
        if (paciente != null) {
            return ResponseEntity.ok(paciente);
        } else {
            throw new RuntimeException("404");
        }
    }



    
}
