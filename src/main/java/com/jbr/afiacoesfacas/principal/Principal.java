package com.jbr.afiacoesfacas.principal;

import com.jbr.afiacoesfacas.model.Empresa;
import com.jbr.afiacoesfacas.model.Faca;
import com.jbr.afiacoesfacas.model.FacaOrdemServico;
import com.jbr.afiacoesfacas.model.OrdemDeServico;
import com.jbr.afiacoesfacas.repository.EmpresaRepository;
import com.jbr.afiacoesfacas.repository.FacaRepository;
import com.jbr.afiacoesfacas.repository.OrdemDeServicoRepository;
import com.jbr.afiacoesfacas.service.CadastroService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Principal {

    Scanner leitura = new Scanner(System.in);

    private final CadastroService cadastroService;
    private final FacaRepository facaRepository;
    private final EmpresaRepository empresaRepository;
    private final OrdemDeServicoRepository ordemDeServicoRepository;

    public Principal(CadastroService cadastroService, FacaRepository facaRepository, EmpresaRepository empresaRepository, OrdemDeServicoRepository ordemDeServicoRepository) {
        this.cadastroService = cadastroService;
        this.facaRepository = facaRepository;
        this.empresaRepository = empresaRepository;
        this.ordemDeServicoRepository = ordemDeServicoRepository;
    }


    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                1 - Cadastrar Tipos de Facas
                2 - Cadastrar Empresas
                3 - Exibir Tipos de Facas
                4 - Exibir Empresas Cadastradas
                5 - Gerar Ordem de Serviço
                6 - Editar Faca
                7 - Excluir Facas Cadastradas
                8 - Excluir Empresas Cadastradas
                                
                0 - Sair
                """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarFacas();
                    break;
                case 2:
                    cadastrarEmpresas();
                    break;
                case 3:
                    exibirFacas();
                    break;
                case 4:
                    exibirEmpresas();
                    break;
                case 5:
                    gerarOrdemDeServico();
                    break;
                case 6:
                    editarFaca();
                    break;
                case 7:
                    excluirFacasCadastradas();
                    break;
                case 8:
                    excluirEmpresasCadastradas();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    public void cadastrarFacas() {
        System.out.println("Informe o nome da faca:");
        String nome = leitura.nextLine();
        System.out.println("Informe o valor da faca:");
        Double valor = leitura.nextDouble();
        leitura.nextLine();
        cadastroService.cadastrarFaca(nome, valor);
        System.out.println("Faca cadastrada com sucesso!");
    }

    public void cadastrarEmpresas() {
        System.out.println("Informe o nome da empresa:");
        String nome = leitura.nextLine();
        System.out.println("Informe o CNPJ da empresa:");
        String cnpj = leitura.nextLine();
        cadastroService.cadastrarEmpresa(nome, cnpj);
        System.out.println("Empresa cadastrada com sucesso!");
    }

    public void exibirFacas() {
        System.out.println("Facas cadastradas:");
        List<Faca> facas = facaRepository.findAll();
        if (facas.isEmpty()) {
            System.out.println("Nenhuma faca cadastrada.");
        } else {
            IntStream.range(0, facas.size())
                    .forEach(i -> System.out.println((i + 1) + ". " + facas.get(i).getNome() +
                            " - Valor: R$ " + facas.get(i).getValor()));
        }
    }


    public void exibirEmpresas() {
        System.out.println("Empresas cadastradas:");
        List<Empresa> empresas = empresaRepository.findAll();
        empresas.forEach(e ->
                System.out.println("Nome: " + e.getNome() + ", CNPJ: " + e.getCnpj()));
    }

        private void gerarOrdemDeServico() {

            System.out.println("Empresas disponíveis:");
            exibirEmpresas();
            System.out.println("Digite o nome da empresa para a ordem de serviço:");
            String nomeEmpresa = leitura.nextLine();
            Empresa empresa = empresaRepository.findByNome(nomeEmpresa);
            if (empresa == null) {
                System.out.println("Empresa não encontrada. Saindo...");
                return;
            }

            List<FacaOrdemServico> facasOrdemServico = new ArrayList<>();

            while (true) {
                System.out.println("Facas disponíveis:");
                exibirFacas();
                System.out.println("Digite o nome da faca (ou 0 para finalizar):");
                String nomeFaca = leitura.nextLine();

                if (nomeFaca.equals("0")) {
                    break;
                }

                Faca faca = facaRepository.findByNome(nomeFaca);
                if (faca == null) {
                    System.out.println("Faca não encontrada. Tente novamente.");
                    continue;
                }

                System.out.println("Digite a quantidade de facas:");
                int quantidade = leitura.nextInt();
                leitura.nextLine();
                facasOrdemServico.add(new FacaOrdemServico(nomeFaca, faca.getValor(), quantidade));

                double valorTotal = facasOrdemServico.stream().mapToDouble(FacaOrdemServico::getValorTotal).sum();

                String nomeOrdemServico = LocalDate.now().toString() + " - " + nomeEmpresa;

                OrdemDeServico ordemDeServico = new OrdemDeServico(nomeOrdemServico, valorTotal, LocalDate.now(), empresa, facasOrdemServico);

                ordemDeServicoRepository.save(ordemDeServico);

                System.out.println("Ordem de serviço gerada com sucesso:");
                System.out.println("Nome: " + ordemDeServico.getNome());
                System.out.println("Empresa: " + ordemDeServico.getIdEmpresa().getNome());
                System.out.println("Facas:");
                facasOrdemServico.forEach(facaOrdemServico ->
                        System.out.println("- Nome: " + facaOrdemServico.getNome() + ", Quantidade: " + facaOrdemServico.getQuantidade()));
                System.out.println("Valor total: " + ordemDeServico.getValorTotal());
            }


        }

    public void editarFaca() {
        System.out.println("Selecione a faca que deseja editar:");
        exibirFacas();
        int indice = leitura.nextInt();
        leitura.nextLine(); // Limpar o buffer do scanner

        List<Faca> facas = facaRepository.findAll();

        if (indice < 1 || indice > facas.size()) {
            System.out.println("Índice inválido.");
            return;
        }

        Faca facaSelecionada = facas.get(indice - 1);

        System.out.println("Digite o novo nome da faca (ou deixe em branco para manter o mesmo):");
        String novoNome = leitura.nextLine();

        System.out.println("Digite o novo valor da faca (ou 0 para manter o mesmo):");
        Double novoValor = leitura.nextDouble();
        leitura.nextLine(); // Limpar o buffer do scanner

        if (!novoNome.isBlank()) {
            facaSelecionada.setNome(novoNome);
        }
        if (novoValor != 0) {
            facaSelecionada.setValor(novoValor);
        }

        facaRepository.save(facaSelecionada);
        System.out.println("Faca editada com sucesso.");
    }


    private void excluirFacasCadastradas() {
        System.out.println("Facas cadastradas:");
        facaRepository.findAll().stream()
                .map(Faca::getNome)
                .forEach(System.out::println);

        System.out.println("Digite o nome da faca que deseja excluir:");
        String nomeFaca = leitura.nextLine().toLowerCase();

        Optional<Faca> facaOptional = facaRepository.findByNomeIgnoreCase(nomeFaca);
        if (facaOptional.isPresent()) {
            Faca faca = facaOptional.get();
            facaRepository.delete(faca);
            System.out.println("Faca excluída com sucesso.");
        } else {
            System.out.println("Faca não encontrada.");
        }
    }

    private void excluirEmpresasCadastradas() {
        System.out.println("Empresas cadastradas:");
        empresaRepository.findAll().stream()
                .map(Empresa::getNome)
                .forEach(System.out::println);

        System.out.println("Digite o nome da empresa que deseja excluir:");
        String nomeEmpresa = leitura.nextLine().toLowerCase(); // Converter para minúsculas

        Optional<Empresa> empresaOptional = empresaRepository.findByNomeIgnoreCase(nomeEmpresa);
        if (empresaOptional.isPresent()) {
            Empresa empresa = empresaOptional.get();
            empresaRepository.delete(empresa);
            System.out.println("Empresa excluída com sucesso.");
        } else {
            System.out.println("Empresa não encontrada.");
        }
    }


}