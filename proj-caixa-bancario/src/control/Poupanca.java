package control;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

	public void verificarArquivo(String caminhoPoupanca) {
		try {
			File f = new File(caminhoPoupanca);
			if (!f.exists()) {
				String auxCaminho = caminhoPoupanca.substring(0, caminhoPoupanca.lastIndexOf("\\"));
				File fDir = new File(auxCaminho);
				fDir.mkdir();
				f.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void cadastrar(Scanner ler) {
		System.out.print("Digite o nome do titular da Conta Poupan�a: ");
		setTitular(ler.nextLine());
		System.out.print("Digite o n�mero da ag�ncia da Conta Poupan�a: ");
		setAgencia(ler.nextLine());
		System.out.print("Digite o n�mero da Conta Poupan�a: ");
		setNrConta(ler.nextLine());
		System.out.print("Digite o saldo da Conta Poupan�a: ");
		setSaldo(Double.parseDouble(ler.nextLine()));
		System.out.print("Digite o valor do rendimento da Conta Poupan�a: ");
		setRendimento(Double.parseDouble(ler.nextLine()));
	}

	public int lerUltimoRegistro(String caminhoPoupanca) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(caminhoPoupanca));
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

	public void salvarPoupanca(String caminhoPoupanca) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoPoupanca, true));
			bw.write(lerUltimoRegistro(caminhoPoupanca) + " " + getTitular() + "#" + getAgencia() + "#" + getNrConta()
					+ "#" + getRendimento() + "#" + getSaldo());
			bw.newLine();
			bw.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public String[] localizarPoupanca(String caminhoPoupanca, Scanner ler) {
		try {
			System.out.println("Digite o n�mero de cadastramento da Conta a ser localizada: ");
			String nrLocalizacao = ler.nextLine();
			BufferedReader ler2 = new BufferedReader(new FileReader(caminhoPoupanca));

			String[] vetor;

			while (ler2.ready()) {
				ler2.ready();

				vetor = ler2.readLine().split(" ");

				if (vetor[0].equalsIgnoreCase(nrLocalizacao)) {
					System.out.println("Conta Poupanca localizada no banco de cadastros!");
					ler2.close();
					return vetor;
				}
			}
			System.out.println("Conta Poupanca nao localizada!");
			ler2.close();
			return null;

		} catch (IOException e) {
			System.out.println("Erro no programa.");
		}
		ler.close();
		return null;
	}

	public String[] imprimirPoupancas(String caminhoPoupanca) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(caminhoPoupanca));
			String linha;
			System.out.println("Imprimindo rela��o de Contas Poupan�as salvas...");
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
			System.out.println(cont + " Contas Poupan�as cadastradas.");
			br.close();
		} catch (Exception e) {
			System.out.println("Erro no programa.");
		}
	}

	public void atualizarPoupanca(String caminhoPoupanca, Scanner ler) {
		try {
			String i = caminhoPoupanca.replace("poupanca.txt", "poupancaTemporaria.txt");
			File j = new File(i);
			j.createNewFile();

			BufferedReader br = new BufferedReader(new FileReader(caminhoPoupanca));
			BufferedWriter bw = new BufferedWriter(new FileWriter(i));

			System.out.println("Digite o n�mero de cadastramento da Conta a ser modificada: ");
			String nrLocalizacao = ler.nextLine();
			String[] vetor;

			while (br.ready()) {
				br.ready();
				String linha = br.readLine();
				vetor = linha.split(" ");

				if (vetor[0].equalsIgnoreCase(nrLocalizacao)) {
					cadastrar(ler);
					bw.write(nrLocalizacao + " " + getTitular() + "#" + getAgencia() + "#" + getNrConta() + "#"
							+ getRendimento() + "#" + getSaldo());
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

	public void salvarSaque(String caminhoPoupanca, Scanner ler) {
		try {
			String i = caminhoPoupanca.replace("poupanca.txt", "poupancaTemporaria.txt");
			File j = new File(i);
			j.createNewFile();

			BufferedReader br = new BufferedReader(new FileReader(caminhoPoupanca));
			BufferedWriter bw = new BufferedWriter(new FileWriter(i));

			System.out.println("Digite o n�mero de cadastramento da Conta de onde ser� sacado: ");
			String nrLocalizacao = ler.nextLine();
			String[] vetor;

			while (br.ready()) {
				br.ready();
				String linha = br.readLine();
				String m = linha.replace(" ", "#");
				vetor = m.split("#");

				if (vetor[0].equalsIgnoreCase(nrLocalizacao)) {
					String auxSaldo = vetor[5];
					sacar(auxSaldo, ler);
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

			System.out.println("Digite o n�mero de cadastramento da Conta que deseja aplicar rendimento: ");
			String nrLocalizacao = ler.nextLine();
			String[] vetor;

			while (br.ready()) {
				br.ready();
				String linha = br.readLine();
				String m = linha.replace(" ", "#");
				vetor = m.split("#");

				if (vetor[0].equalsIgnoreCase(nrLocalizacao)) {
					String auxSaldo = vetor[5];
					String auxRendimento = vetor[4];
					aplicarRend(auxSaldo, auxRendimento, ler);
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

	public void salvarDeposito(String caminhoPoupanca, Scanner ler) {
		try {
			String i = caminhoPoupanca.replace("poupanca.txt", "poupancaTemporaria.txt");
			File j = new File(i);
			j.createNewFile();

			BufferedReader br = new BufferedReader(new FileReader(caminhoPoupanca));
			BufferedWriter bw = new BufferedWriter(new FileWriter(i));

			System.out.println("Digite o n�mero de cadastramento da Conta para onde ser� depositado: ");
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

	public void depositar(String auxSaldo, Scanner ler) {
		System.out.print("Digite o valor a ser depositado: ");
		String valorDepositado = ler.nextLine();
		double auxSal = Double.parseDouble(auxSaldo);
		double i = Double.parseDouble(valorDepositado);
		auxSal = auxSal + i;
		setSaldo(auxSal);
		System.out.println("O novo saldo da conta �: " + auxSal);
	}

	public void verificarSaldo(String caminhoPoupanca, Scanner ler) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(caminhoPoupanca));
			System.out.println("Digite o n�mero de cadastramento da Conta que deseja ver o saldo: ");
			String nrLocalizacao = ler.nextLine();
			String[] vetor;

			while (br.ready()) {
				br.ready();
				String linha = br.readLine();
				String m = linha.replace(" ", "#");
				vetor = m.split("#");

				if (vetor[0].equalsIgnoreCase(nrLocalizacao)) {
					System.out.println("Atualmente o saldo dispon�vel na Conta � de: " + vetor[5] + "\n");
					break;
				}
			}
			br.close();
		} catch (Exception e) {
			System.out.println("Erro no programa.");
		}
	}

	public void excluirConta(String caminhoPoupanca, Scanner ler) {
		try {
			String i = caminhoPoupanca.replace("poupanca.txt", "poupancaTemporaria.txt");
			File j = new File(i);
			j.createNewFile();

			BufferedReader br = new BufferedReader(new FileReader(caminhoPoupanca));
			BufferedWriter bw = new BufferedWriter(new FileWriter(i));

			System.out.println("Digite o n�mero de cadastramento da Conta que deseja excluir: ");
			String nrLocalizacao = ler.nextLine();
			String[] vetor;

			while (br.ready()) {
				br.ready();
				String linha = br.readLine();
				String m = linha.replace(" ", "#");
				vetor = m.split("#");

				if (vetor[0].equalsIgnoreCase(nrLocalizacao)) {
					System.out.println("Conta Poupan�a exclu�da com sucesso!");
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

	public void excluir(String caminhoPoupanca, Scanner ler) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(caminhoPoupanca));
			System.out.println("Digite o n�mero de cadastramento da Conta que deseja excluir: ");
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
			System.out.println("A Conta Poupan�a que possui o saldo mais baixo �: " + k);

			br.close();

		} catch (Exception e) {
			System.out.println("Erro no programa.");
		}
	}

	public void somaPoupanca(String caminhoPoupanca) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(caminhoPoupanca));
			System.out.println("Somando saldos das Contas Poupan�as, aguarde...");
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
			System.out.println("O total das Poupan�as �: " + somaP);
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
