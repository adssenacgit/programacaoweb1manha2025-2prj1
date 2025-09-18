package com.senac.games.service;

import com.senac.games.dto.request.CategoriaDTORequest;
import com.senac.games.dto.request.CategoriaDTOUpdateRequest;
import com.senac.games.dto.request.ParticipanteDTORequest;
import com.senac.games.dto.request.ParticipanteDTOUpdateRequest;
import com.senac.games.dto.response.CategoriaDTOResponse;
import com.senac.games.dto.response.CategoriaDTOUpdateResponse;
import com.senac.games.dto.response.ParticipanteDTOResponse;
import com.senac.games.dto.response.ParticipanteDTOUpdateResponse;
import com.senac.games.entity.Categoria;
import com.senac.games.entity.Participante;
import com.senac.games.entity.Patrocinador;
import com.senac.games.repository.CategoriaRepository;
import com.senac.games.repository.PatrocinadorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    @Autowired
    private ModelMapper modelMapper;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> listarCategorias(){
        return this.categoriaRepository.listarCategorias();
    }

    public Categoria listarPorCategoriaId(Integer categoriaId) {
        return this.categoriaRepository.obterCategoriaPeloId(categoriaId);
    }

    public CategoriaDTOResponse criarCategoria(CategoriaDTORequest categoriaDTOrequest) {

        Categoria categoria = modelMapper.map(categoriaDTOrequest, Categoria.class);
        Categoria categoriaSave = this.categoriaRepository.save(categoria);
        CategoriaDTOResponse categoriaDTOResponse = modelMapper.map(categoriaSave, CategoriaDTOResponse.class);
        return categoriaDTOResponse;
    }

    public CategoriaDTOResponse atualizarCategoria(Integer categoriaId, CategoriaDTORequest categoriaDTORequest) {
        //antes de atualizar busca se existe o registro a ser atualizar
        Categoria categoria = this.listarPorCategoriaId(categoriaId);

        //se encontra o registro a ser atualizado
        if (categoria != null){
            //copia os dados a serem atualizados do DTO de entrada para um objeto do tipo categoria
            //que é compatível com o repository para atualizar
            modelMapper.map(categoriaDTORequest,categoria);

            //com o objeto no formato correto tipo "categoria" o comando "save" salva
            // no banco de dados o objeto atualizado
            Categoria tempResponse = categoriaRepository.save(categoria);

            return modelMapper.map(tempResponse, CategoriaDTOResponse.class);
        }else {
            return null;
        }

    }

    public CategoriaDTOUpdateResponse atualizarStatusCategoria(Integer categoriaId, CategoriaDTOUpdateRequest categoriaDTOUpdateRequest) {
        //antes de atualizar busca se existe o registro a ser atualizar
        Categoria categoria = this.listarPorCategoriaId(categoriaId);

        //se encontra o registro a ser atualizado
        if (categoria != null) {
            //atualizamos unicamente o campo de status
            categoria.setStatus(categoriaDTOUpdateRequest.getStatus());

            //com o objeto no formato correto tipo "participante" o comando "save" salva
            // no banco de dados o objeto atualizado
            Categoria tempResponse = categoriaRepository.save(categoria);
            return modelMapper.map(tempResponse, CategoriaDTOUpdateResponse.class);
        }
        else{
            return null;
        }
    }

    public void apagarCategoria(Integer categoriaId){
        categoriaRepository.apagadoLogicoCategoria(categoriaId);
    }
}
