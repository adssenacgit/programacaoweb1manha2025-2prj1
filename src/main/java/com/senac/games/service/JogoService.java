package com.senac.games.service;

import com.senac.games.dto.request.CategoriaDTORequest;
import com.senac.games.dto.request.JogoDTORequest;
import com.senac.games.dto.response.CategoriaDTOResponse;
import com.senac.games.dto.response.JogoDTOResponse;
import com.senac.games.entity.Categoria;
import com.senac.games.entity.Jogo;
import com.senac.games.repository.CategoriaRepository;
import com.senac.games.repository.JogoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JogoService {
    @Autowired
    private ModelMapper modelMapper;
    private final JogoRepository jogoRepository;
    private final CategoriaRepository categoriaRepository;

    public JogoService(JogoRepository jogoRepository, CategoriaRepository categoriaRepository) {
        this.jogoRepository = jogoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public List<Jogo> listarJogos(){
        return this.jogoRepository.listarJogos();
    }


    public Jogo listarPorJogoId(Integer jogoId) {
        return this.jogoRepository.obterJogoPeloId(jogoId);
    }

    public JogoDTOResponse criarJogo(JogoDTORequest jogoDTOrequest) {

        Jogo jogo = new Jogo();
        jogo.setNome(jogoDTOrequest.getNome());
        jogo.setStatus(jogoDTOrequest.getStatus());
        jogo.setCategoria(categoriaRepository.obterCategoriaPeloId(jogoDTOrequest.getIdCategoria())          );

        Jogo jogoSave = this.jogoRepository.save(jogo);
        JogoDTOResponse jogoDTOResponse = modelMapper.map(jogoSave, JogoDTOResponse.class);
        return jogoDTOResponse;
    }
}