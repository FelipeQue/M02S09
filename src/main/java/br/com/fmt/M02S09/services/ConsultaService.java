package br.com.fmt.M02S09.services;

import br.com.fmt.M02S09.controllers.dto.*;
import br.com.fmt.M02S09.entities.Consulta;
import br.com.fmt.M02S09.repositories.ConsultaRepository;
import br.com.fmt.M02S09.repositories.NutricionistaRepository;
import br.com.fmt.M02S09.repositories.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final NutricionistaRepository nutricionistaRepository;
    private final PacienteRepository pacienteRepository;

    public ConsultaService(ConsultaRepository consultaRepository, NutricionistaRepository nutricionistaRepository, PacienteRepository pacienteRepository) {
        this.consultaRepository = consultaRepository;
        this.nutricionistaRepository = nutricionistaRepository;
        this.pacienteRepository = pacienteRepository;
    }

    // Listar consultas apenas com Data/Hora, nome de Nutricionista e nome de Paciente.
    public List<ConsultaResponseListDTO> listarConsultas() {
        return consultaRepository.findAll().stream().map(
                consulta -> new ConsultaResponseListDTO(
                        consulta.getId(),
                        consulta.getNutricionista().getNome(),
                        consulta.getPaciente().getNome(),
                        consulta.getData()
                )
        ).collect(Collectors.toList());
    }

    public ConsultaResponseDTO buscarConsulta(Long id){
        Consulta consulta = consultaRepository.findById(id).orElse(null);
        if (consulta != null) {
            return new ConsultaResponseDTO(
                    consulta.getId(),
                    consulta.getNutricionista(),
                    consulta.getPaciente(),
                    consulta.getData(),
                    consulta.getObservacoes()
            );
        }
        return null;
    }

    public ConsultaResponseDTO salvarConsulta(ConsultaRequestDTO request) {
        Consulta consulta = mapearRequest(request);
        Consulta entitySalva = consultaRepository.save(consulta);

        return new ConsultaResponseDTO(entitySalva.getId(),
                entitySalva.getNutricionista(),
                entitySalva.getPaciente(),
                entitySalva.getData(),
                entitySalva.getObservacoes()
        );
    }

    private Consulta mapearRequest(ConsultaRequestDTO source){
        Consulta target = new Consulta();
        target.setData(source.getData());
        target.setObservacoes(source.getObservacoes());
        target.setNutricionista(nutricionistaRepository.findById(source.getIdNutricionista()).orElse(null));
        target.setPaciente(pacienteRepository.findById(source.getIdPaciente()).orElse(null));
        return target;
    }

    public ConsultaResponseDTO atualizarConsulta(Long id, ConsultaRequestDTO request) {
        Consulta consulta = consultaRepository.findById(id).orElse(null);
        assert consulta != null;
        consulta.setNutricionista(nutricionistaRepository.findById(request.getIdNutricionista()).orElse(null));
        consulta.setPaciente(pacienteRepository.findById(request.getIdPaciente()).orElse(null));
        consulta.setData(request.getData());
        consulta.setObservacoes(request.getObservacoes());
        consultaRepository.save(consulta);

        return new ConsultaResponseDTO(consulta.getId(),
                consulta.getNutricionista(),
                consulta.getPaciente(),
                consulta.getData(),
                consulta.getObservacoes());
    }

    public void removerConsulta(Long id) {
        consultaRepository.deleteById(id);
    }

}
