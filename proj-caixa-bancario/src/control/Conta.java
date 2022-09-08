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

	public void imprimirContas(String caminho) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(caminho));
			String linha;
			System.out.println("Imprimindo relacao de Contas salvas...");
			System.out.println("\nInformacoes dispostas em: ");
			System.out.println("\nNro Cadastro | Titular | Nro Agencia | Nro Conta | Limite | Saldo");
			while (br.ready()) {
				linha = br.readLine();
				System.out.println(linha.replaceFirst(" ", "\t | ").replace("#", "\t | "));
			}
			System.out.println("\n");
			br.close();
		} catch (Exception e) {
			System.out.println("Erro no programa.");
		}
	}

	public String verificarMenorSaldo(double saldoConta, String linha, String informacoesContaComMenorSaldo) {
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

	public String verificarMaiorSaldo(double saldoConta, String linha, String informacoesContaComMaiorSaldo) {
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
			if (tipoConta.equals("Poupanca")) {
				caminhoTemporario = caminho.replace("poupanca.txt", "poupancaTemporaria.txt");
			} else {
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

	public double somarSaldosDasContas(String caminho, String tipoConta) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(caminho));
			System.out.println("Somando saldos das Contas " + tipoConta + ", aguarde...");
			String[] vetor;

			while (br.ready()) {
				br.ready();

				String linha = br.readLine();
				vetor = quebrarDadosEmIndicesVetor(linha);

				double valorPorConta = Double.parseDouble(vetor[5]);
				saldo = saldo + valorPorConta;
			}

			br.close();
			return saldo;
		} catch (Exception e) {
			System.out.println("Erro no programa.");
			return 0;
		}
	}

	public double calcularMontanteDoBanco(double somatorioSaldoCorrentes, double somatorioSaldoPoupancas) {
		double somatorioSaldoTodasAsContas = somatorioSaldoCorrentes + somatorioSaldoPoupancas;
		return somatorioSaldoTodasAsContas;
	}

	public String[] quebrarDadosEmIndicesVetor(String linhaDoDocumento) {
		return linhaDoDocumento.replace(" ", "#").split("#");
	}

	public boolean excluirConta(String caminho, Scanner ler, String tipoConta) {
		try {
			String caminhoTemporario = criarArquivoTemporario(caminho, tipoConta);

			BufferedReader br = new BufferedReader(new FileReader(caminho));
			BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoTemporario));

			System.out.println("Digite o numero de cadastro da Conta que deseja excluir: ");
			String numeroCadastro = ler.nextLine();
			String[] vetor;

			while (br.ready()) {
				br.ready();
				String linha = br.readLine();
				vetor = quebrarDadosEmIndicesVetor(linha);

				if (vetor[0].equalsIgnoreCase(numeroCadastro)) {
					System.out.println("Conta Poupanca excluida com sucesso!");
				} else {
					bw.write(linha);
					bw.newLine();
				}
			}
			br.close();
			bw.close();

			reescreverDadosNoArquivoOriginal(caminhoTemporario, caminho);
			excluirArquivoTemporario(caminhoTemporario);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void verificarSaldo(String caminho, Scanner ler) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(caminho));
			System.out.println("Digite o numero de cadastro da Conta que deseja ver o saldo: ");
			String numeroCadastro = ler.nextLine();
			String[] vetor;

			while (br.ready()) {
				br.ready();
				String linha = br.readLine();
				vetor = quebrarDadosEmIndicesVetor(linha);

				if (vetor[0].equalsIgnoreCase(numeroCadastro)) {
					System.out.println("Atual saldo disponivel na Conta: " + vetor[5] + "\n");
					break;
				}
			}

			br.close();
		} catch (Exception e) {
			System.out.println("Erro no programa.");
		}
	}

	public boolean iniciarProcessoDeposito(String caminho, Scanner ler, String tipoConta) {
		try {
			String caminhoTemporario = criarArquivoTemporario(caminho, tipoConta);

			BufferedReader br = new BufferedReader(new FileReader(caminho));
			BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoTemporario));

			System.out.println("Digite o numero de cadastro da Conta para onde sera depositado: ");
			String numeroCadastro = ler.nextLine();
			String[] vetor;

			while (br.ready()) {
				br.ready();
				String linha = br.readLine();
				vetor = quebrarDadosEmIndicesVetor(linha);

				if (vetor[0].equalsIgnoreCase(numeroCadastro)) {
					saldo = Double.parseDouble(vetor[5]);
					depositar(saldo, ler);
					bw.write(numeroCadastro + " " + vetor[1] + "#" + vetor[2] + "#" + vetor[3] + "#" + vetor[4] + "#"
							+ getSaldo());
					bw.newLine();

				} else {
					bw.write(linha);
					bw.newLine();
				}
			}
			br.close();
			bw.close();

			reescreverDadosNoArquivoOriginal(caminhoTemporario, caminho);
			excluirArquivoTemporario(caminhoTemporario);

			return true;
		} catch (Exception e) {
			System.out.println("Erro no programa.");
			return false;
		}
	}

	public void depositar(double saldo, Scanner ler) {
		System.out.print("Digite o valor a ser depositado: ");
		double valorDepositado = ler.nextDouble();
		setSaldo(saldo + valorDepositado);
	}

	public boolean iniciarProcessoSaque(String caminho, Scanner ler, String tipoConta) {
		try {
			String caminhoTemporario = criarArquivoTemporario(caminho, tipoConta);

			BufferedReader br = new BufferedReader(new FileReader(caminho));
			BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoTemporario));

			System.out.println("Digite o numero de cadastro da Conta de onde sera sacado: ");
			String numeroCadastro = ler.nextLine();
			String[] vetor;

			while (br.ready()) {
				br.ready();
				String linha = br.readLine();
				vetor = quebrarDadosEmIndicesVetor(linha);

				if (vetor[0].equalsIgnoreCase(numeroCadastro)) {
					double saldoAtual = Double.parseDouble(vetor[5]);
					double limite = Double.parseDouble(vetor[4]);
					sacar(saldoAtual, limite, ler, tipoConta);
					bw.write(numeroCadastro + " " + vetor[1] + "#" + vetor[2] + "#" + vetor[3] + "#" + vetor[4] + "#"
							+ getSaldo());
					bw.newLine();

				} else {
					bw.write(linha);
					bw.newLine();
				}
			}
			br.close();
			bw.close();

			reescreverDadosNoArquivoOriginal(caminhoTemporario, caminho);
			excluirArquivoTemporario(caminhoTemporario);

			return true;
		} catch (Exception e) {
			System.out.println("Erro no programa.");
			return false;
		}
	}

	public void sacar(double saldoAtual, double limite, Scanner ler, String tipoConta) {
		System.out.print("Digite o valor a ser sacado: ");
		double valorSacado = ler.nextDouble();

		if (tipoConta.equals("Corrente")) {
			if (valorSacado <= saldoAtual + limite && valorSacado > 0) {
				saldoAtual = saldoAtual - valorSacado;
				setSaldo(saldoAtual);
			} else {
				System.out.println("Confira os valores e tente novamente.");
			}
		} else {
			if (valorSacado <= saldoAtual && valorSacado > 0) {
				saldoAtual = saldoAtual - valorSacado;
				setSaldo(saldoAtual);
			} else {
				System.out.println("Confira os valores e tente novamente.");
			}
		}
	}

	public boolean atualizarConta(String caminho, Scanner ler, String tipoConta) {
		try {
			String caminhoTemporario = criarArquivoTemporario(caminho, tipoConta);

			BufferedReader br = new BufferedReader(new FileReader(caminho));
			BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoTemporario));

			System.out.println("Digite o numero de cadastro da Conta a ser modificada: ");
			String numeroCadastro = ler.nextLine();
			String[] vetor;

			while (br.ready()) {
				br.ready();
				String linha = br.readLine();
				vetor = linha.split(" ");

				if (tipoConta.equals("Corrente")) {
					if (vetor[0].equalsIgnoreCase(numeroCadastro)) {
						Corrente cc = new Corrente();
						cc.cadastrarCorrente(ler);
						bw.write(numeroCadastro + " " + cc.getTitular() + "#" + cc.getAgencia() + "#" + cc.getNrConta() + "#"
								+ cc.getLimite() + "#" + cc.getSaldo());
						bw.newLine();
					} else {
						bw.write(linha);
						bw.newLine();
					}
				} else {
					if (vetor[0].equalsIgnoreCase(numeroCadastro)) {
						Poupanca cp = new Poupanca();
						cp.cadastrarPoupanca(ler);
						bw.write(numeroCadastro + " " + cp.getTitular() + "#" + cp.getAgencia() + "#" + cp.getNrConta() + "#"
								+ cp.getRendimento() + "#" + cp.getSaldo());
						bw.newLine();
					} else {
						bw.write(linha);
						bw.newLine();
					}
				}
			}
			br.close();
			bw.close();

			reescreverDadosNoArquivoOriginal(caminhoTemporario, caminho);
			excluirArquivoTemporario(caminhoTemporario);

			return true;
		} catch (Exception e) {
			System.out.println("Erro no programa.");
			return false;
		}
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
