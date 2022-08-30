package control;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class Poupanca extends Conta {

	private double rendimento, somaP;

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

	public boolean salvarPoupanca(String caminhoPoupanca) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoPoupanca, true));
			bw.write(lerUltimoRegistro(caminhoPoupanca) + " " + getTitular() + "#" + getAgencia() + "#" + getNrConta()
					+ "#" + getRendimento() + "#" + getSaldo());
			bw.newLine();
			bw.close();
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	public String[] imprimirPoupancas(String caminhoPoupanca) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(caminhoPoupanca));
			String linha;
			System.out.println("Imprimindo rela��o de Contas Poupancas salvas...");
			System.out.println(
					"Informa��es dispostas em: Nro Cadastro, Titular, Nro Ag�ncia, Nro Conta, Limite, Saldo.\n");

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

	public void contarPoupancas(String caminhoPoupanca) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(caminhoPoupanca));
			int cont = 0;
			String vazio;
			System.out.println("Calculando, aguarde...");
			while (br.ready()) {
				vazio = br.readLine();
				if (!vazio.isEmpty()) {
					cont++;
				}
			}
			System.out.println(cont + " Contas Poupancas cadastradas.");
			br.close();
		} catch (Exception e) {
			System.out.println("Erro no programa.");
		}
	}

	public boolean atualizarPoupanca(String caminhoPoupanca, Scanner ler) {
		try {
			String novoCaminho = caminhoPoupanca.replace("poupanca.txt", "poupancaTemporaria.txt");
			File novoArquivo = new File(novoCaminho);
			novoArquivo.createNewFile();

			BufferedReader br = new BufferedReader(new FileReader(caminhoPoupanca));
			BufferedWriter bw = new BufferedWriter(new FileWriter(novoCaminho));

			System.out.println("Digite o numero de cadastro da Conta a ser modificada: ");
			String numeroCadastro = ler.nextLine();
			String[] vetor;

			while (br.ready()) {
				br.ready();
				String linha = br.readLine();
				vetor = linha.split(" ");

				if (vetor[0].equalsIgnoreCase(numeroCadastro)) {
					cadastrarPoupanca(ler);
					bw.write(numeroCadastro + " " + getTitular() + "#" + getAgencia() + "#" + getNrConta() + "#"
							+ getRendimento() + "#" + getSaldo());
					bw.newLine();

				} else {
					bw.write(linha);
					bw.newLine();
				}
			}
			br.close();
			bw.close();

			BufferedReader braux = new BufferedReader(new FileReader(novoCaminho));
			BufferedWriter bwaux = new BufferedWriter(new FileWriter(caminhoPoupanca));

			while (braux.ready()) {
				bwaux.write(braux.readLine());
				bwaux.newLine();
			}
			bwaux.close();
			braux.close();
			novoArquivo.delete();
			return true;
		} catch (Exception e) {
			System.out.println("Erro no programa.");
			return false;
		}
	}

	public boolean salvarSaque(String caminhoPoupanca, Scanner ler) {
		try {
			String i = caminhoPoupanca.replace("poupanca.txt", "poupancaTemporaria.txt");
			File j = new File(i);
			j.createNewFile();

			BufferedReader br = new BufferedReader(new FileReader(caminhoPoupanca));
			BufferedWriter bw = new BufferedWriter(new FileWriter(i));

			System.out.println("Digite o numero de cadastro da Conta de onde ser� sacado: ");
			String numeroCadastro = ler.nextLine();
			String[] vetor;

			while (br.ready()) {
				br.ready();
				String linha = br.readLine();
				String m = linha.replace(" ", "#");
				vetor = m.split("#");

				if (vetor[0].equalsIgnoreCase(numeroCadastro)) {
					String auxSaldo = vetor[5];
					sacar(auxSaldo, ler);
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
			BufferedWriter bwaux = new BufferedWriter(new FileWriter(caminhoPoupanca));

			while (braux.ready()) {
				bwaux.write(braux.readLine());
				bwaux.newLine();
			}
			bwaux.close();
			braux.close();
			j.delete();
			return true;
		} catch (Exception e) {
			System.out.println("Erro no programa.");
			return false;
		}
	}

	public void aplicarRend(String auxSaldo, String auxRendimento, Scanner ler) {
		double auxSal = Double.parseDouble(auxSaldo);
		double auxRend = Double.parseDouble(auxRendimento);

		auxSal = auxSal + auxSal * auxRend / 100;
		setSaldo(auxSal);
		System.out.println("Novo saldo com o rendimento aplicado �: " + auxSal);
	}

	public void aplicarRendimento(String caminhoPoupanca, Scanner ler) {
		try {
			String i = caminhoPoupanca.replace("poupanca.txt", "poupancaTemporaria.txt");
			File j = new File(i);
			j.createNewFile();

			BufferedReader br = new BufferedReader(new FileReader(caminhoPoupanca));
			BufferedWriter bw = new BufferedWriter(new FileWriter(i));

			System.out.println("Digite o numero de cadastro da Conta que deseja aplicar rendimento: ");
			String numeroCadastro = ler.nextLine();
			String[] vetor;

			while (br.ready()) {
				br.ready();
				String linha = br.readLine();
				String m = linha.replace(" ", "#");
				vetor = m.split("#");

				if (vetor[0].equalsIgnoreCase(numeroCadastro)) {
					String auxSaldo = vetor[5];
					String auxRendimento = vetor[4];
					aplicarRend(auxSaldo, auxRendimento, ler);
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
			BufferedWriter bwaux = new BufferedWriter(new FileWriter(caminhoPoupanca));

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

	public void sacar(String auxSaldo, Scanner ler) {
		System.out.print("Digite o valor a ser sacado: ");
		String valorSacado = ler.nextLine();

		double auxSal = Double.parseDouble(auxSaldo);
		double i = Double.parseDouble(valorSacado);
		if (i <= auxSal && i > 0) {
			auxSal = auxSal - i;
			setSaldo(auxSal);
			System.out.println("O novo saldo da conta �: " + auxSal);
		} else {
			System.out.println("Comando inv�lido.");
		}
	}

	public boolean salvarDeposito(String caminhoPoupanca, Scanner ler) {
		try {
			String i = caminhoPoupanca.replace("poupanca.txt", "poupancaTemporaria.txt");
			File j = new File(i);
			j.createNewFile();

			BufferedReader br = new BufferedReader(new FileReader(caminhoPoupanca));
			BufferedWriter bw = new BufferedWriter(new FileWriter(i));

			System.out.println("Digite o numero de cadastro da Conta para onde ser� depositado: ");
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
			BufferedWriter bwaux = new BufferedWriter(new FileWriter(caminhoPoupanca));

			while (braux.ready()) {
				bwaux.write(braux.readLine());
				bwaux.newLine();
			}
			bwaux.close();
			braux.close();
			j.delete();
			return true;
		} catch (Exception e) {
			System.out.println("Erro no programa.");
			return false;
		}
	}

	public void depositar(String auxSaldo, Scanner ler) {
		System.out.print("Digite o valor a ser depositado: ");
		String valorDepositado = ler.nextLine();
		double auxSal = Double.parseDouble(auxSaldo);
		double i = Double.parseDouble(valorDepositado);
		auxSal = auxSal + i;
		setSaldo(auxSal);
		System.out.println("Novo saldo da conta: " + auxSal);
	}

	public void verificarSaldo(String caminhoPoupanca, Scanner ler) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(caminhoPoupanca));
			System.out.println("Digite o numero de cadastro da Conta que deseja ver o saldo: ");
			String numeroCadastro = ler.nextLine();
			String[] vetor;

			while (br.ready()) {
				br.ready();
				String linha = br.readLine();
				String m = linha.replace(" ", "#");
				vetor = m.split("#");

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

	public boolean excluirConta(String caminhoPoupanca, Scanner ler) {
		try {
			String i = caminhoPoupanca.replace("poupanca.txt", "poupancaTemporaria.txt");
			File j = new File(i);
			j.createNewFile();

			BufferedReader br = new BufferedReader(new FileReader(caminhoPoupanca));
			BufferedWriter bw = new BufferedWriter(new FileWriter(i));

			System.out.println("Digite o numero de cadastro da Conta que deseja excluir: ");
			String numeroCadastro = ler.nextLine();
			String[] vetor;

			while (br.ready()) {
				br.ready();
				String linha = br.readLine();
				String m = linha.replace(" ", "#");
				vetor = m.split("#");

				if (vetor[0].equalsIgnoreCase(numeroCadastro)) {
					System.out.println("Conta Poupanca excluida com sucesso!");
				} else {
					bw.write(linha);
					bw.newLine();
				}
			}
			br.close();
			bw.close();

			BufferedReader braux = new BufferedReader(new FileReader(i));
			BufferedWriter bwaux = new BufferedWriter(new FileWriter(caminhoPoupanca));

			while (braux.ready()) {
				bwaux.write(braux.readLine());
				bwaux.newLine();
			}
			bwaux.close();
			braux.close();
			j.delete();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void excluir(String caminhoPoupanca, Scanner ler) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(caminhoPoupanca));
			System.out.println("Digite o numero de cadastro da Conta que deseja excluir: ");
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

	public void verificarMaior(String caminhoPoupanca) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(caminhoPoupanca));
			System.out.println("Verificando, aguarde...");

			String[] vetor;
			double i = 0;
			double j = 0;
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
					if (auxVet < j) {
						j = auxVet;
						k = linha;
					}
				}
			}
			System.out.println("A Conta Poupanca que possui o saldo mais baixo �: " + k);

			br.close();

		} catch (Exception e) {
			System.out.println("Erro no programa.");
		}
	}

	public void somaPoupanca(String caminhoPoupanca) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(caminhoPoupanca));
			System.out.println("Somando saldos das Contas Poupancas, aguarde...");
			String[] vetor;

			while (br.ready()) {
				br.ready();

				String linha = br.readLine();
				String l = linha.replace(" ", "#");
				vetor = l.split("#");

				double auxVet = Double.parseDouble(vetor[5]);
				somaP = somaP + auxVet;
			}
			setSomaP(somaP);
			System.out.println("Total das Poupancas: " + somaP);
			br.close();

		} catch (Exception e) {
			System.out.println("Erro no programa.");
		}
	}

	public double getSomaP() {
		return somaP;
	}

	public void setSomaP(double somaP) {
		this.somaP = somaP;
	}

	public double getRendimento() {
		return rendimento;
	}

	public void setRendimento(double rendimento) {
		this.rendimento = rendimento;
	}
}
