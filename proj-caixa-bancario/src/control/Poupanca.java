package control;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class Poupanca extends Conta {

	private double rendimento;

	public Poupanca() {

	}

	Poupanca(String agencia, String nrConta, double saldo, String titular, double rendimento) {
		this.setAgencia(agencia);
		this.setNrConta(nrConta);
		this.setSaldo(saldo);
		this.setTitular(titular);
		this.setRendimento(rendimento);
	}
	
	public void mostrarMenuOpcoesGeraisPoupanca() {
		System.out.println("Carregando opcoes para Conta Poupanca. Aguarde...\n");
		System.out.println(" Escolha a opcao: \n==================");
		System.out.println("1 - para cadastrar uma Conta Poupanca;");
		System.out.println("2 - para alterar informacoes/sacar/depositar/verificar saldo/localizar uma Conta Poupanca;");
		System.out.println("3 - para informar quantas Contas Poupancas existem;");
		System.out.println("4 - para listar todas as Contas Poupancas existentes;");
		System.out.println("5 - para excluir uma Conta Poupanca existente;");
		System.out.println("6 - para exibir Conta Poupanca com menor saldo;");
		System.out.println("7 - para aplicar rendimento na Conta Poupanca.");
		System.out.println("s - para encerrar o sistema.");
	}
	
	public void mostrarMenuOpcoesAlteracaoPoupanca() {
		System.out.println(" Escolha a opcao: \n==================");
		System.out.println("1 - para alterar informacoes da Conta Poupanca;");
		System.out.println("2 - para sacar da Conta Poupanca;");
		System.out.println("3 - para depositar na Conta Poupanca;");
		System.out.println("4 - para verificar saldo da Conta Poupanca.");
		System.out.println("5 - para verificar se uma Conta Poupanca esta cadastrada.");
		System.out.println("s - para encerrar o sistema.");
	}

	public void cadastrarPoupanca(Scanner ler) {
		System.out.print("Digite o nome do titular da Conta Poupanca: ");
		setTitular(ler.nextLine());
		System.out.print("Digite o numero da agencia da Conta Poupanca: ");
		setAgencia(ler.nextLine());
		System.out.print("Digite o numero da Conta Poupanca: ");
		setNrConta(ler.nextLine());
		System.out.print("Digite o saldo da Conta Poupanca: ");
		setSaldo(Double.parseDouble(ler.nextLine()));
		System.out.print("Digite o valor do rendimento da Conta Poupanca: ");
		setRendimento(Double.parseDouble(ler.nextLine()));
	}
	//refatorar
	public boolean salvarPoupanca(String caminhoPoupanca) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoPoupanca, true));
			bw.write(lerUltimoRegistro(caminhoPoupanca) + " " + getTitular() + "#" + getAgencia() + "#" + getNrConta()
					+ "#" + getRendimento() + "#" + getSaldo());
			bw.newLine();
			bw.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void aplicarRendimento(String saldo, String rendimento) {
		double porcentagemRendimento = Double.parseDouble(rendimento) / 100;
		double saldoAtual = Double.parseDouble(saldo);
		double novoSaldoComRendimento = saldoAtual + (saldoAtual * porcentagemRendimento);
		setSaldo(novoSaldoComRendimento);
		
		System.out.println("Novo saldo com o rendimento aplicado: " + novoSaldoComRendimento);
	}

	public void exibirSaldoComRendimento(String caminhoPoupanca, Scanner ler) {
		try {
			String caminhoTemporario = criarArquivoTemporario(caminhoPoupanca, "Poupanca");

			BufferedReader br = new BufferedReader(new FileReader(caminhoPoupanca));
			BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoTemporario));

			System.out.println("Digite o numero de cadastro da Conta que deseja aplicar rendimento: ");
			String numeroCadastro = ler.nextLine();
			String[] vetor;

			while (br.ready()) {
				br.ready();
				String linha = br.readLine();
				vetor = quebrarDadosEmIndicesVetor(linha);

				if (vetor[0].equalsIgnoreCase(numeroCadastro)) {
					String auxSaldo = vetor[5];
					String auxRendimento = vetor[4];
					aplicarRendimento(auxSaldo, auxRendimento);
					bw.write(numeroCadastro + " " + vetor[1] + "#" + vetor[2] + "#" + vetor[3] + "#" + vetor[4] + "#" + getSaldo());
					bw.newLine();

				} else {
					bw.write(linha);
					bw.newLine();
				}
			}
			br.close();
			bw.close();

			reescreverDadosNoArquivoOriginal(caminhoTemporario, caminhoPoupanca);
			excluirArquivoTemporario(caminhoTemporario);

		} catch (Exception e) {
			System.out.println("Erro no programa.");
		}
	}
	
	public void exibirMenorSaldo(String caminhoPoupanca) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(caminhoPoupanca));
			System.out.println("Verificando, aguarde...");

			String[] vetor;
			String informacoesContaComMenorSaldo = "Não há contas cadastradas.";

			while (br.ready()) {
				br.ready();
				String linha = br.readLine();
				vetor = quebrarDadosEmIndicesVetor(linha);
				double saldoConta = Double.parseDouble(vetor[5]);
				
				informacoesContaComMenorSaldo = verificarMenorSaldo(saldoConta, linha, informacoesContaComMenorSaldo);
			}
			
			System.out.println("Conta Poupanca que possui o saldo mais baixo: " + informacoesContaComMenorSaldo);
			br.close();
		} catch (Exception e) {
			System.out.println("Erro no programa.");
		}
	}

	public double getRendimento() {
		return rendimento;
	}

	public void setRendimento(double rendimento) {
		this.rendimento = rendimento;
	}
}
