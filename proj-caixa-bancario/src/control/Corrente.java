package control;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class Corrente extends Conta {
	private double limite, somaC;

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
		System.out.println("2 - para alterar informacoes/sacar/depositar/verificar saldo/localizar uma Conta Corrente;");
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
	
	public void atualizarCorrente(String caminhoCorrente, Scanner ler) {
		try {
			String novoCaminho = caminhoCorrente.replace("corrente.txt", "correnteTemporaria.txt");
			File novoArquivo = new File(novoCaminho);
			novoArquivo.createNewFile();

			BufferedReader br = new BufferedReader(new FileReader(caminhoCorrente));
			BufferedWriter bw = new BufferedWriter(new FileWriter(novoCaminho));

			System.out.println("Digite o n�mero de cadastramento da Conta a ser modificada: ");
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

			BufferedReader braux = new BufferedReader(new FileReader(novoCaminho));
			BufferedWriter bwaux = new BufferedWriter(new FileWriter(caminhoCorrente));

			while (braux.ready()) {
				bwaux.write(braux.readLine());
				bwaux.newLine();
			}
			bwaux.close();
			braux.close();
			novoArquivo.delete();

		} catch (Exception e) {
			System.out.println("Erro no programa.");
		}
	}

	public String[] imprimirCorrentes(String caminhoCorrente) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(caminhoCorrente));
			String linha;
			System.out.println("Imprimindo relacao de Contas Correntes salvas...");
			System.out.println(
					"Informacoes dispostas em: Nro Cadastro, Titular, Nro Agencia, Nro Conta, Limite, Saldo.\n");

			while (br.ready()) {
				linha = br.readLine();
				System.out.println(linha.replace("#", ",\t"));
			}
			br.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	public void contarCorrentes(String caminhoCorrente) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(caminhoCorrente));
			int cont = 0;
			String vazio;
			System.out.println("Calculando, aguarde...");
			while (br.ready()) {
				vazio = br.readLine();
				if (!vazio.isEmpty()) {
					cont++;
				}
			}
			System.out.println(cont + " Contas Correntes cadastradas.");
			br.close();
		} catch (Exception e) {
			System.out.println("Erro no programa.");
		}

	}

	public void salvarSaque(String caminhoCorrente, Scanner ler) {
		try {
			String i = caminhoCorrente.replace("corrente.txt", "correnteTemporaria.txt");
			File j = new File(i);
			j.createNewFile();

			BufferedReader br = new BufferedReader(new FileReader(caminhoCorrente));
			BufferedWriter bw = new BufferedWriter(new FileWriter(i));

			System.out.println("Digite o n�mero de cadastramento da Conta de onde ser� sacado: ");
			String numeroCadastro = ler.nextLine();
			String[] vetor;

			while (br.ready()) {
				br.ready();
				String linha = br.readLine();
				String m = linha.replace(" ", "#");
				vetor = m.split("#");

				if (vetor[0].equalsIgnoreCase(numeroCadastro)) {
					String auxSaldo = vetor[5];
					String auxLimite = vetor[4];
					sacar(auxSaldo, auxLimite, ler);
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

			BufferedReader braux = new BufferedReader(new FileReader(i));
			BufferedWriter bwaux = new BufferedWriter(new FileWriter(caminhoCorrente));

			while (braux.ready()) {
				bwaux.write(braux.readLine());
				bwaux.newLine();
			}
			bwaux.close();
			braux.close();
			j.delete();

		} catch (Exception e) {
			System.out.println("Erro no programa.");
		}
	}

	public void sacar(String auxSaldo, String auxLimite, Scanner ler) {
		System.out.print("Digite o valor a ser sacado: ");
		String valorSacado = ler.nextLine();

		double auxSal = Double.parseDouble(auxSaldo);
		double auxLim = Double.parseDouble(auxLimite);
		double i = Double.parseDouble(valorSacado);
		if (i <= auxSal + auxLim && i > 0) {
			auxSal = auxSal - i;
			setSaldo(auxSal);
			System.out.println("O novo saldo da conta �: " + auxSal);
		} else {
			System.out.println("Comando inv�lido.");
		}
	}

	public void salvarDeposito(String caminhoCorrente, Scanner ler) {
		try {
			String i = caminhoCorrente.replace("corrente.txt", "correnteTemporaria.txt");
			File j = new File(i);
			j.createNewFile();

			BufferedReader br = new BufferedReader(new FileReader(caminhoCorrente));
			BufferedWriter bw = new BufferedWriter(new FileWriter(i));

			System.out.println("Digite o n�mero de cadastramento da Conta para onde ser� depositado: ");
			String numeroCadastro = ler.nextLine();
			String[] vetor;

			while (br.ready()) {
				br.ready();
				String linha = br.readLine();
				String m = linha.replace(" ", "#");
				vetor = m.split("#");

				if (vetor[0].equalsIgnoreCase(numeroCadastro)) {
					String auxSaldo = vetor[5];
					depositar(auxSaldo, ler);
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

			BufferedReader braux = new BufferedReader(new FileReader(i));
			BufferedWriter bwaux = new BufferedWriter(new FileWriter(caminhoCorrente));

			while (braux.ready()) {
				bwaux.write(braux.readLine());
				bwaux.newLine();
			}
			bwaux.close();
			braux.close();
			j.delete();

		} catch (Exception e) {
			System.out.println("Erro no programa.");
		}
	}

	public void depositar(String auxSaldo, Scanner ler) {
		System.out.print("Digite o valor a ser depositado: ");
		String valorDepositado = ler.nextLine();
		double auxSal = Double.parseDouble(auxSaldo);
		double i = Double.parseDouble(valorDepositado);
		auxSal = auxSal + i;
		setSaldo(auxSal);
		System.out.println("O novo saldo da conta �: " + auxSal);
	}

	public void verificarSaldo(String caminhoCorrente, Scanner ler) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(caminhoCorrente));
			System.out.println("Digite o n�mero de cadastramento da Conta que deseja ver o saldo: ");
			String numeroCadastro = ler.nextLine();
			String[] vetor;

			while (br.ready()) {
				br.ready();
				String linha = br.readLine();
				String m = linha.replace(" ", "#");
				vetor = m.split("#");

				if (vetor[0].equalsIgnoreCase(numeroCadastro)) {
					System.out.println("Atualmente o saldo dispon�vel na Conta � de: " + vetor[5] + "\n");
					break;
				}
			}
			br.close();
		} catch (Exception e) {
			System.out.println("Erro no programa.");
		}
	}

	public void excluirConta(String caminhoCorrente, Scanner ler) {
		try {
			String i = caminhoCorrente.replace("corrente.txt", "correnteTemporaria.txt");
			File j = new File(i);
			j.createNewFile();

			BufferedReader br = new BufferedReader(new FileReader(caminhoCorrente));
			BufferedWriter bw = new BufferedWriter(new FileWriter(i));

			System.out.println("Digite o n�mero de cadastramento da Conta que deseja excluir: ");
			String numeroCadastro = ler.nextLine();
			String[] vetor;

			while (br.ready()) {
				br.ready();
				String linha = br.readLine();
				String m = linha.replace(" ", "#");
				vetor = m.split("#");

				if (vetor[0].equalsIgnoreCase(numeroCadastro)) {
					System.out.println("Conta Corrente exclu�da com sucesso!");
				} else {
					bw.write(linha);
					bw.newLine();
				}
			}
			br.close();
			bw.close();

			BufferedReader braux = new BufferedReader(new FileReader(i));
			BufferedWriter bwaux = new BufferedWriter(new FileWriter(caminhoCorrente));

			while (braux.ready()) {
				bwaux.write(braux.readLine());
				bwaux.newLine();
			}
			bwaux.close();
			braux.close();
			j.delete();

		} catch (Exception e) {
			System.out.println("Erro no programa.");
		}
	}

	public void excluir(String caminhoCorrente, Scanner ler) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(caminhoCorrente));
			System.out.println("Digite o n�mero de cadastramento da Conta que deseja excluir: ");
			String numeroCadastro = ler.nextLine();
			String[] vetor;

			while (br.ready()) {
				br.ready();
				String linha = br.readLine();
				String m = linha.replace(" ", "#");
				vetor = m.split("#");

				if (vetor[0].equalsIgnoreCase(numeroCadastro)) {
					linha.substring(linha.indexOf(""), linha.lastIndexOf(""));

					break;
				}
			}
			br.close();
		} catch (Exception e) {
			System.out.println("Erro no programa.");
		}
	}

	public void verificarMaior(String caminhoCorrente) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(caminhoCorrente));
			System.out.println("Verificando, aguarde...");

			String[] vetor;
			double i = 0, j = 0;
			String k = "";

			while (br.ready()) {
				br.ready();

				String linha = br.readLine();
				String m = linha.replace(" ", "#");
				vetor = m.split("#");

				double auxVet = Double.parseDouble(vetor[5]);

				if (i == 0) {
					j = auxVet;
					k = linha;
					i++;
				} else {
					if (auxVet > j) {
						j = auxVet;
						k = linha;
					}
				}
			}
			System.out.println("A Conta Corrente que possui o saldo mais alto �: " + k);

			br.close();

		} catch (Exception e) {
			System.out.println("Erro no programa.");
		}
	}

	public void somaCorrente(String caminhoCorrente) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(caminhoCorrente));
			System.out.println("Somando saldos das Contas Correntes, aguarde...");
			String[] vetor;

			while (br.ready()) {
				br.ready();

				String linha = br.readLine();
				String l = linha.replace(" ", "#");
				vetor = l.split("#");

				double auxVet = Double.parseDouble(vetor[5]);
				somaC = somaC + auxVet;
			}
			setSomaC(somaC);
			System.out.println("O total das Correntes �: " + somaC);
			br.close();

		} catch (Exception e) {
			System.out.println("Erro no programa.");
		}
	}

	public double getSomaC() {
		return somaC;
	}

	public void setSomaC(double somaC) {
		this.somaC = somaC;
	}

	public double getLimite() {
		return limite;
	}

	public void setLimite(double limite) {
		this.limite = limite;
	}
}
