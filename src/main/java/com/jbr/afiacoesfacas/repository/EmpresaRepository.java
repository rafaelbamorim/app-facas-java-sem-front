package com.jbr.afiacoesfacas.repository;

import com.jbr.afiacoesfacas.model.Empresa;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EmpresaRepository extends MongoRepository<Empresa, String> {

    Empresa findByNome(String nome);

    Optional<Empresa> findByNomeIgnoreCase(String nomeEmpresa);
}
