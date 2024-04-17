package com.jbr.afiacoesfacas.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "ordensDeServico")
public class OrdemDeServico {
        @Id
        private String id;
        private String nome;
        private Double valorTotal;
        private LocalDate data;
        private Empresa idEmpresa;
        private List<Faca> facas;

    public OrdemDeServico() {
    }

    public OrdemDeServico(String nome, Double valorTotal, LocalDate data, Empresa empresa, List<Faca> facas) {
        this.nome = nome;
        this.valorTotal = valorTotal;
        this.data = data;
        this.id = String.valueOf(empresa);
        this.facas = facas;
    }

    public OrdemDeServico(String nomeOrdemServico, double valorTotal, LocalDate now, Empresa empresa, List<FacaOrdemServico> facasOrdemServico) {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Empresa getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Empresa idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public List<Faca> getFacas() {
        return facas;
    }

    public void setFacas(List<Faca> facas) {
        this.facas = facas;
    }


}
