package com.senac.games.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
public class Jogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="jogo_id")
    private int id;

    @Column(name="jogo_nome")
    private String nome;

    @Column(name="jogo_status")
    private int status;

    @Transient
    @JsonProperty("idCategoria")
    private int idCategoria;
    public int getIdCategoria() {
        return this.categoria.getId();
    }

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="categoria_id", nullable=false)
    private Categoria categoria;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

}
