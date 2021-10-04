package control;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

	public void verificarArquivo(String caminhoCorrente) {
		try {
			File f = new File(caminhoCorrente);
			if (!f.exists()) {
				String auxCaminho = caminhoCorrente.substring(0, caminhoCorrente.lastIndexOf("\\"));
				File fDir = new File(auxCaminho);
				fDir.mkdir();
				f.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void cadastrar(Scanner ler) {
		System.out.print("Digite o nome do titular da Conta Corrente: ");
		setTitular(ler.nextLine());
		System.out.print("Digite o número da Agência da Conta Corrente: ");
		setAgencia(ler.nextLine());
		System.out.print("Digite o número da Conta Corrente: ");
		setNrConta(ler.nextLine());
		System.out.print("Digite o saldo da Conta Corrente: ");
		setSaldo(Double.parseDouble(ler.nextLine()));
		System.out.print("Digite o valor do limite da Conta Corrente: ");
		setLimite(Double.parseDouble(ler.nextLine()));
	}

	public int lerUltimoRegistro(String caminhoCorrente) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(caminhoCorrente));
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

	public void salvarCorrente(String caminhoCorrente) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoCorrente, true));
			bw.write(lerUltimoRegistro(caminhoCorrente) + " " + getTitular() + "#" + getAgencia() + "#" + getNrConta()
					+ "#" + getLimite() + "#" + getSaldo());
			bw.newLine();
			bw.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public String[] localizarCorrente(String caminhoCorrente, Scanner ler) {
		try {
			System.out.println("Digite o número de cadastramento da Conta a ser localizada: ");
			String nrLocalizacao = ler.nextLine();
			BufferedReader ler2 = new BufferedReader(new FileReader(caminhoCorrente));

			String[] vetor;

			while (ler2.ready()) {
				ler2.ready();

				vetor = ler2.readLine().split(" ");

				if (vetor[0].equalsIgnoreCase(nrLocalizacao)) {
					System.out.println("Conta Corrente localizada no banco de cadastros!");
					ler2.close();
					return vetor;
				}
			}
			System.out.println("Conta Corrente não localizada. Cadastre!");
			ler2.close();
			return null;

		} catch (IOException e) {
			System.out.println("Erro no programa.");
		}
		ler.close();
		return null;
	}

	public String[] imprimirCorrentes(String caminhoCorrente) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(caminhoCorrente));
			String linha;
			System.out.println("Imprimindo relação de Contas Correntes salvas...");
			System.out.println(
					"Informações dispostas em: Nro Cadastro, Titular, Nro Agência, Nro Conta, Limite, Saldo.\n");

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

	public void atualizarCorrente(String caminhoCorrente, Scanner ler) {
		try {
			String i = caminhoCorrente.replace("corrente.txt", "correnteTemporaria.txt");
			File j = new File(i);
			j.createNewFile();

			BufferedReader br = new BufferedReader(new FileReader(caminhoCorrente));
			BufferedWriter bw = new BufferedWriter(new FileWriter(i));

			System.out.println("Digite o número de cadastramento da Conta a ser modificada: ");
			String nrLocalizacao = ler.nextLine();
			String[] vetor;

			while (br.ready()) {
				br.ready();
				String linha = br.readLine();
				vetor = linha.split(" ");

				if (vetor[0].equalsIgnoreCase(nrLocalizacao)) {
					cadastrar(ler);
					bw.write(nrLocalizacao + " " + getTitular() + "#" + getAgencia() + "#" + getNrConta() + "#"
							+ getLimite() + "#" + getSaldo());
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

	public void salvarSaque(String caminhoCorrente, Scanner ler) {
		try {
			String i = caminhoCorrente.replace("corrente.txt", "correnteTemporaria.txt");
			File j = new File(i);
			j.createNewFile();

			BufferedReader br = new BufferedReader(new FileReader(caminhoCorrente));
			BufferedWriter bw = new BufferedWriter(new FileWriter(i));

			System.out.println("Digite o número de cadastramento da Conta de onde será sacado: ");
			String nrLocalizacao = ler.nextLine();
			String[] vetor;

			while (br.ready()) {
				br.ready();
				String linha = br.readLine();
				String m = linha.replace(" ", "#");
				vetor = m.split("#");

				if (vetor[0].equalsIgnoreCase(nrLocalizacao)) {
					String auxSaldo = vetor[5];
					String auxLimite = vetor[4];
					sacar(auxSaldo, auxLimite, ler);
					bw.write(nrLocalizacao + " " + vetor[1] + "#" + vetor[2] + "#" + vetor[3] + "#" + vetor[4] + "#"
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
			System.out.println("O novo saldo da conta é: " + auxSal);
		} else {
			System.out.println("Comando inválido.");
		}
	}

	public void salvarDeposito(String caminhoCorrente, Scanner ler) {
		try {
			String i = caminhoCorrente.replace("corrente.txt", "correnteTemporaria.txt");
			File j = new File(i);
			j.createNewFile();

			BufferedReader br = new BufferedReader(new FileReader(caminhoCorrente));
			BufferedWriter bw = new BufferedWriter(new FileWriter(i));

			System.out.println("Digite o número de cadastramento da Conta para onde será depositado: ");
			String nrLocalizacao = ler.nextLine();
			String[] vetor;

			while (br.ready()) {
				br.ready();
				String linha = br.readLine();
				String m = linha.replace(" ", "#");
				vetor = m.split("#");

				if (vetor[0].equalsIgnoreCase(nrLocalizacao)) {
					String auxSaldo = vetor[5];
					depositar(auxSaldo, ler);
					bw.write(nrLocalizacao + " " + vetor[1] + "#" + vetor[2] + "#" + vetor[3] + "#" + vetor[4] + "#"
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
		System.out.println("O novo saldo da conta é: " + auxSal);
	}

	public void verificarSaldo(String caminhoCorrente, Scanner ler) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(caminhoCorrente));
			System.out.println("Digite o número de cadastramento da Conta que deseja ver o saldo: ");
			String nrLocalizacao = ler.nextLine();
			String[] vetor;

			while (br.ready()) {
				br.ready();
				String linha = br.readLine();
				String m = linha.replace(" ", "#");
				vetor = m.split("#");

				if (vetor[0].equalsIgnoreCase(nrLocalizacao)) {
					System.out.println("Atualmente o saldo disponível na Conta é de: " + vetor[5] + "\n");
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

			System.out.println("Digite o número de cadastramento da Conta que deseja excluir: ");
			String nrLocalizacao = ler.nextLine();
			String[] vetor;

			while (br.ready()) {
				br.ready();
				String linha = br.readLine();
				String m = linha.replace(" ", "#");
				vetor = m.split("#");

				if (vetor[0].equalsIgnoreCase(nrLocalizacao)) {
					System.out.println("Conta Corrente excluída com sucesso!");
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
			System.out.println("Digite o número de cadastramento da Conta que deseja excluir: ");
			String nrLocalizacao = ler.nextLine();
			String[] vetor;

			while (br.ready()) {
				br.ready();
				String linha = br.readLine();
				String m = linha.replace(" ", "#");
				vetor = m.split("#");

				if (vetor[0].equalsIgnoreCase(nrLocalizacao)) {
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
					if (auxVet > j) {
						j = auxVet;
						k = linha;
					}
				}
			}
			System.out.println("A Conta Corrente que possui o saldo mais alto é: " + k);

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
			System.out.println("O total das Correntes é: " + somaC);
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
