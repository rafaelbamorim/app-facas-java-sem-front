package com.jbr.afiacoesfacas;

import com.jbr.afiacoesfacas.principal.Principal;
import com.jbr.afiacoesfacas.repository.EmpresaRepository;
import com.jbr.afiacoesfacas.repository.FacaRepository;
import com.jbr.afiacoesfacas.repository.OrdemDeServicoRepository;
import com.jbr.afiacoesfacas.service.CadastroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AfiacoesFacasApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(AfiacoesFacasApplication.class, args);
    }

    @Autowired
    private FacaRepository facaRepository;
    @Autowired
    private EmpresaRepository empresaRepository;
    @Autowired
    private CadastroService cadastroService;
    @Autowired
    private OrdemDeServicoRepository ordemDeServicoRepository;

    @Override
    public void run(String... args) throws Exception {
        Principal principal = new Principal(cadastroService, facaRepository, empresaRepository, ordemDeServicoRepository);
        principal.exibeMenu();
    }


}
