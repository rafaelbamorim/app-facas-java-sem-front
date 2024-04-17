package com.jbr.afiacoesfacas.service;

import com.jbr.afiacoesfacas.model.Empresa;
import com.jbr.afiacoesfacas.model.Faca;
import com.jbr.afiacoesfacas.repository.EmpresaRepository;
import com.jbr.afiacoesfacas.repository.FacaRepository;
import org.springframework.stereotype.Service;

@Service
public class CadastroService {

    private final EmpresaRepository empresaRepository;
    private final FacaRepository facaRepository;

    public CadastroService(EmpresaRepository empresaRepository, FacaRepository facaRepository) {
        this.empresaRepository = empresaRepository;
        this.facaRepository = facaRepository;
    }

    public Empresa cadastrarEmpresa(String nome, String cnpj) {
        Empresa empresa = new Empresa(nome, cnpj);
        return empresaRepository.save(empresa);
    }

    public Faca cadastrarFaca(String nome, Double valor) {
        Faca faca = new Faca(nome, valor);
        return facaRepository.save(faca);
    }
}
