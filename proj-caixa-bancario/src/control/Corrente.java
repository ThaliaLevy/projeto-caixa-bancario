package control;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class Corrente extends Conta {
	private double limite;
	public Corrente() {

	}

	Corrente(String agencia, String nrConta, double saldo, String titular, double limite) {
		this.setAgencia(agencia);
		this.setNrConta(nrConta);
		this.setSaldo(saldo);
		this.setTitular(titular);
		this.setLimite(limite);
	}

	public void mostrarMenuOpcoesGeraisCorrente() {
		System.out.println(" Escolha a opcao: \n==================");
		System.out.println("1 - para cadastrar uma Conta Corrente;");
		System.out
				.println("2 - para alterar informacoes/sacar/depositar/verificar saldo/localizar uma Conta Corrente;");
		System.out.println("3 - para informar quantas Contas Correntes existem;");
		System.out.println("4 - para listar todas as Contas Correntes existentes;");
		System.out.println("5 - para excluir uma Conta Corrente existente;");
		System.out.println("6 - para exibir Conta Corrente com maior saldo;");
		System.out.println("s - para encerrar o sistema.");
	}

	public void mostrarMenuOpcoesAlteracaoCorrente() {
		System.out.println(" Escolha a opcao: \n==================");
		System.out.println("1 - para alterar informacoes da Conta Corrente;");
		System.out.println("2 - para sacar da Conta Corrente;");
		System.out.println("3 - para depositar na Conta Corrente;");
		System.out.println("4 - para verificar saldo da Conta Corrente;");
		System.out.println("5 - para verificar se uma Conta Corrente esta cadastrada;");
		System.out.println("s - para encerrar o sistema.");
	}

	public void cadastrarCorrente(Scanner ler) {
		System.out.print("Digite o nome do titular da Conta Corrente: ");
		setTitular(ler.nextLine());
		System.out.print("Digite o numero da Agencia da Conta Corrente: ");
		setAgencia(ler.nextLine());
		System.out.print("Digite o numero da Conta Corrente: ");
		setNrConta(ler.nextLine());
		System.out.print("Digite o saldo da Conta Corrente: ");
		setSaldo(Double.parseDouble(ler.nextLine()));
		System.out.print("Digite o valor do limite da Conta Corrente: ");
		setLimite(Double.parseDouble(ler.nextLine()));
	}

	public boolean salvarCorrente(String caminhoCorrente) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoCorrente, true));
			bw.write(lerUltimoRegistro(caminhoCorrente) + " " + getTitular() + "#" + getAgencia() + "#" + getNrConta()
					+ "#" + getLimite() + "#" + getSaldo());
			bw.newLine();
			bw.close();
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	public boolean atualizarCorrente(String caminhoCorrente, Scanner ler) {
		try {
			String caminhoTemporario = criarArquivoTemporario(caminhoCorrente, "Corrente");

			BufferedReader br = new BufferedReader(new FileReader(caminhoCorrente));
			BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoTemporario));

			System.out.println("Digite o numero de cadastro da Conta a ser modificada: ");
			String numeroCadastro = ler.nextLine();
			String[] vetor;

			while (br.ready()) {
				br.ready();
				String linha = br.readLine();
				vetor = linha.split(" ");

				if (vetor[0].equalsIgnoreCase(numeroCadastro)) {
					cadastrarCorrente(ler);
					bw.write(numeroCadastro + " " + getTitular() + "#" + getAgencia() + "#" + getNrConta() + "#"
							+ getLimite() + "#" + getSaldo());
					bw.newLine();

				} else {
					bw.write(linha);
					bw.newLine();
				}
			}
			br.close();
			bw.close();

			reescreverDadosNoArquivoOriginal(caminhoTemporario, caminhoCorrente);
			excluirArquivoTemporario(caminhoTemporario);
			
			return true;
		} catch (Exception e) {
			System.out.println("Erro no programa.");
			return false;
		}
	}

	public void exibirMaiorSaldo(String caminhoCorrente) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(caminhoCorrente));
			System.out.println("Verificando, aguarde...");

			String[] vetor;
			String informacoesContaComMaiorSaldo = "Não há contas cadastradas.";

			while (br.ready()) {
				br.ready();
				String linha = br.readLine();
				vetor = quebrarDadosEmIndicesVetor(linha);
				double saldoConta = Double.parseDouble(vetor[5]);
				
				informacoesContaComMaiorSaldo = verificarMaiorSaldo(saldoConta, linha, informacoesContaComMaiorSaldo);
			}
			
			System.out.println("Conta Corrente que possui o saldo mais alto: " + informacoesContaComMaiorSaldo);
			br.close();
		} catch (Exception e) {
			System.out.println("Erro no programa.");
		}
	}
	
	public double getLimite() {
		return limite;
	}

	public void setLimite(double limite) {
		this.limite = limite;
	}
}
