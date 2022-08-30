package control;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Conta {
	private String agencia;
	private String nrConta;
	private double saldo;
	private String titular;
	
	public void mostrarMenuOpcoesIniciais() {
		System.out.println("\n Menu \n======");
		System.out.println(" Escolha a opcao: \n==================");
		System.out.println("1 - para acessar opcoes para Conta Corrente;");
		System.out.println("2 - para acessar opcoes para Conta Poupanca;");
		System.out.println("3 - para verificar a soma do montante deste Banco.");
		System.out.println("s - para encerrar o sistema.");
	}
	
	public int lerUltimoRegistro(String caminho) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(caminho));
			int i = 99;
			String[] aux;
			while (br.ready()) {
				br.ready();
				aux = br.readLine().split(" ");
				i = Integer.parseInt(aux[0]);
			}
			br.close();
			return ++i;
		} catch (IOException e) {
			System.out.print("Erro no programa.");
		}
		return 0;
	}
	
	public String[] localizarConta(String caminho, Scanner ler, String tipoConta) {
		try {
			System.out.println("Digite o numero de cadastro da Conta a ser localizada: ");
			String numeroCadastro = ler.nextLine();
			BufferedReader br = new BufferedReader(new FileReader(caminho));

			String[] vetor;

			while (br.ready()) {
				br.ready();

				vetor = br.readLine().split(" ");

				if (vetor[0].equalsIgnoreCase(numeroCadastro)) {
					System.out.println("Conta " + tipoConta + " localizada no banco de cadastros!");
					br.close();
					return vetor;
				}
			}
			System.out.println("Conta " + tipoConta + " nao localizada!");
			br.close();
			return null;

		} catch (IOException e) {
			System.out.println("Erro no programa.");
		}
		ler.close();
		return null;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getNrConta() {
		return nrConta;
	}

	public void setNrConta(String nrConta) {
		this.nrConta = nrConta;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}
}
