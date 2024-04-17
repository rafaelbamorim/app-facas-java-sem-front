package com.jbr.afiacoesfacas.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tiposFacas")
public class Faca {

    @Id
    @Indexed(unique = true)
    private String id;
    private String nome;
    private Double valor;

    public Faca(String nome, Double valor) {
        this.nome = nome;
        this.valor = valor;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }


}