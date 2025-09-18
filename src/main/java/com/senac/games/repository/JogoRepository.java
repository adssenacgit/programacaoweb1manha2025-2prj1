package com.senac.games.repository;

import com.senac.games.entity.Jogo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JogoRepository extends JpaRepository<Jogo, Integer> {

    @Query("SELECT j FROM Jogo j WHERE j.status>=0")
    List<Jogo> listarJogos();
    @Query("SELECT j FROM Jogo j WHERE j.status>=0 AND j.id=:id")
    Jogo obterJogoPeloId(@Param("id") Integer id);

    @Modifying
    @Transactional
    @Query("UPDATE Jogo j SET j.status=-1 WHERE j.id=:id")
    void apagadoLogico(@Param("id") Integer id);

}
