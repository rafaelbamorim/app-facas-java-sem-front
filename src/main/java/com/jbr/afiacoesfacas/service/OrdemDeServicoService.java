package com.jbr.afiacoesfacas.service;

import com.jbr.afiacoesfacas.model.Empresa;
import com.jbr.afiacoesfacas.model.Faca;
import com.jbr.afiacoesfacas.model.OrdemDeServico;
import com.jbr.afiacoesfacas.model.FacaOrdemServico;
import com.jbr.afiacoesfacas.repository.OrdemDeServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrdemDeServicoService {

    @Autowired
    private OrdemDeServicoRepository ordemDeServicoRepository;

    public OrdemDeServico gerarOrdemDeServico(String nomeEmpresa, List<FacaOrdemServico> facas) {
        // Calcular o valor total da ordem de serviço
        double valorTotal = calcularValorTotal(facas);

        // Obter a data atual
        LocalDate dataAtual = LocalDate.now();

        // Criar uma instância de Empresa com base no nome fornecido
        Empresa empresa = new Empresa();
        empresa.setNome(nomeEmpresa); // Supondo que o nome seja suficiente para identificar a empresa

        // Criar a ordem de serviço
        OrdemDeServico ordemDeServico = new OrdemDeServico();
        ordemDeServico.setNome(gerarNomeOrdemServico(nomeEmpresa, dataAtual));
        ordemDeServico.setValorTotal(valorTotal);
        ordemDeServico.setData(dataAtual);
        ordemDeServico.setIdEmpresa(empresa);
        // Supondo que a lista de facas está corretamente preenchida
        ordemDeServico.setFacas(converterParaFacas(facas));

        // Salvar a ordem de serviço no repositório
        return ordemDeServicoRepository.save(ordemDeServico);
    }

    private double calcularValorTotal(List<FacaOrdemServico> facas) {
        double valorTotal = 0.0;
        for (FacaOrdemServico faca : facas) {
            valorTotal += faca.getValorTotal();
        }
        return valorTotal;
    }

    private String gerarNomeOrdemServico(String nomeEmpresa, LocalDate data) {
        // Formatar o nome da ordem de serviço com base no nome da empresa e na data atual
        return nomeEmpresa + "-" + data.toString();
    }

    private List<Faca> converterParaFacas(List<FacaOrdemServico> facasOrdemServico) {
        // Implementar a lógica de conversão de FacaOrdemServico para Faca, se necessário
        // Aqui suponho que as facas já estejam no formato correto
        return null;
    }
}
