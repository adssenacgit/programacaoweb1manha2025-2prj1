package com.senac.games.service;

import com.senac.games.dto.request.ParticipanteDTORequest;
import com.senac.games.dto.request.ParticipanteDTOUpdateRequest;
import com.senac.games.dto.response.ParticipanteDTOResponse;
import com.senac.games.dto.response.ParticipanteDTOUpdateResponse;
import com.senac.games.entity.Participante;
import com.senac.games.entity.Patrocinador;
import com.senac.games.repository.ParticipanteRepository;
import com.senac.games.repository.PatrocinadorRepository;
import jakarta.servlet.http.Part;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipanteService {

    private final ParticipanteRepository participanteRepository;
    @Autowired
    private ModelMapper modelMapper;

    public ParticipanteService(ParticipanteRepository participanteRepository) {
        this.participanteRepository = participanteRepository;
    }

    public List<Participante> listarParticipantes(){
        return this.participanteRepository.findAll();
    }

    public Participante listarPorParticipanteId(Integer participanteId) {
        return this.participanteRepository.findById(participanteId).orElse(null);
    }

    public ParticipanteDTOResponse criarParticipante(ParticipanteDTORequest participanteDTOrequest) {

        Participante participante = modelMapper.map(participanteDTOrequest, Participante.class);
        Participante participanteSave = this.participanteRepository.save(participante);
        ParticipanteDTOResponse participanteDTOResponse = modelMapper.map(participanteSave, ParticipanteDTOResponse.class);
        return participanteDTOResponse;

        /*
        participante.setNome(participanteDTO.getNome());
        participante.setEmail(participanteDTO.getEmail());
        participante.setIdentificacao(participanteDTO.getIdentificacao());
        participante.setEndereco(participanteDTO.getEndereco());
        participante.setStatus(participanteDTO.getStatus());
        ParticipanteDTOResponse participanteDTOResponse = new ParticipanteDTOResponse();
        participanteDTOResponse.setId(participanteSave.getId());
        participanteDTOResponse.setNome(participanteSave.getNome());
        participanteDTOResponse.setEmail(participanteSave.getEmail());
        participanteDTOResponse.setIdentificacao(participanteSave.getIdentificacao());
        participanteDTOResponse.setEndereco(participanteSave.getEndereco());
        participanteDTOResponse.setStatus(participanteSave.getStatus());
        return participanteDTOResponse;
        */
    }


    public ParticipanteDTOResponse atualizarParticipante(Integer participanteId, ParticipanteDTORequest participanteDTORequest) {
        //antes de atualizar busca se existe o registro a ser atualizar
        Participante participante = this.listarPorParticipanteId(participanteId);

        //se encontra o registro a ser atualizado
        if (participante != null){
            //copia os dados a serem atualizados do DTO de entrada para um objeto do tipo participante
            //que é compatível com o repository para atualizar
            modelMapper.map(participanteDTORequest,participante);

            //com o objeto no formato correto tipo "participante" o comando "save" salva
            // no banco de dados o objeto atualizado
            Participante tempResponse = participanteRepository.save(participante);

            return modelMapper.map(tempResponse, ParticipanteDTOResponse.class);
        }else {
            return null;
        }

    }

    public ParticipanteDTOUpdateResponse atualizarStatusParticipante(Integer participanteId, ParticipanteDTOUpdateRequest participanteDTOUpdateRequest) {
        //antes de atualizar busca se existe o registro a ser atualizar
        Participante participante = this.listarPorParticipanteId(participanteId);

        //se encontra o registro a ser atualizado
        if (participante != null) {
            //atualizamos unicamente o campo de status
            participante.setStatus(participanteDTOUpdateRequest.getStatus());

            //com o objeto no formato correto tipo "participante" o comando "save" salva
            // no banco de dados o objeto atualizado
            Participante tempResponse = participanteRepository.save(participante);
            return modelMapper.map(tempResponse, ParticipanteDTOUpdateResponse.class);
        }
        else{
            return null;
        }
    }
}
