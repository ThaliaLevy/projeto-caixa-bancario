package control;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Conta {
	private String agencia, nrConta, titular;
	private double saldo;
	double contador = 0, menorSaldo = 0, maiorSaldo = 0;
	
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
	
	public String verificarMenorSaldo (double saldoConta, String linha, String informacoesContaComMenorSaldo) {
		if (contador == 0) {
			menorSaldo = saldoConta;
			informacoesContaComMenorSaldo = linha;
			contador++;
		} else {
			if (saldoConta < menorSaldo) {
				menorSaldo = saldoConta;
				informacoesContaComMenorSaldo = linha;
			}
		}
		return informacoesContaComMenorSaldo;
	}
	
	public String verificarMaiorSaldo (double saldoConta, String linha, String informacoesContaComMaiorSaldo) {
		if (contador == 0) {
			maiorSaldo = saldoConta;
			informacoesContaComMaiorSaldo = linha;
			contador++;
		} else {
			if (saldoConta > maiorSaldo) {
				maiorSaldo = saldoConta;
				informacoesContaComMaiorSaldo = linha;
			}
		}
		return informacoesContaComMaiorSaldo;
	}
	
	public void contarTotalDeContasCadastradas(String caminho, String tipoConta) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(caminho));
			System.out.println("Calculando, aguarde...");
			
			int contador = 0;
			while (br.ready()) {
				if (!br.readLine().isEmpty()) {
					contador++;
				}
			}
			
			System.out.println(contador + " Contas " + tipoConta + " cadastradas.\n");
			br.close();
		} catch (Exception e) {
			System.out.println("Erro no programa.");
		}
	}
	
	public String criarArquivoTemporario(String caminho, String tipoConta) {
			try {
				String caminhoTemporario;
				if(tipoConta.equals("Poupanca")) {
					caminhoTemporario = caminho.replace("poupanca.txt", "poupancaTemporaria.txt");
				}else {
					caminhoTemporario = caminho.replace("corrente.txt", "correnteTemporaria.txt");
				}
				File novoArquivo = new File(caminhoTemporario);
				novoArquivo.createNewFile();
	
				return caminhoTemporario;
			} catch (IOException e) {
				System.out.println("Erro!");
				return null;
			}
	}
	
	public void excluirArquivoTemporario(String caminhoTemporario) {
		File arquivoTemporario = new File(caminhoTemporario);
		if (arquivoTemporario.exists()) {
			arquivoTemporario.delete();
		}
	}
	
	public void reescreverDadosNoArquivoOriginal(String caminhoTemporario, String caminho) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(caminhoTemporario));
			BufferedWriter bw = new BufferedWriter(new FileWriter(caminho));
			
			while (br.ready()) {
				bw.write(br.readLine());
				bw.newLine();
			}
			
			bw.close();
			br.close();
		} catch (IOException e) {
			System.out.println("Erro!");
		}
	}
	
	public String[] quebrarDadosEmIndicesVetor(String linhaDoDocumento) {
		return linhaDoDocumento.replace(" ", "#").split("#");
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
