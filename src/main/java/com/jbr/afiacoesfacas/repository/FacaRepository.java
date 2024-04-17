package com.jbr.afiacoesfacas.repository;

import com.jbr.afiacoesfacas.model.Faca;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FacaRepository extends MongoRepository<Faca, String> {

    Faca findByNome(String nome);

    Optional<Faca> findByNomeIgnoreCase(String nomeFaca);
}
