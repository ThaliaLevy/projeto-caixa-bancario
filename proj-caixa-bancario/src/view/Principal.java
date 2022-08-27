package view;

import java.io.IOException;
import java.util.Scanner;

import control.Corrente;
import control.Poupanca;

public class Principal {
	public static void main(String[] args) throws IOException {

		String caminhoPoupanca = System.getProperty("user.dir") + "\\src\\exerc2526\\poupanca.txt";
		String caminhoCorrente = System.getProperty("user.dir") + "\\src\\exerc2526\\corrente.txt";
		String op = null;
		Scanner ler = new Scanner(System.in);

		do {
			System.out.println("\n Menu \n======");
			System.out.println(" Escolha a opcao: \n==================");
			System.out.println("1 - para acessar opcoes para Conta Corrente;");
			System.out.println("2 - para acessar opcoes para Conta Poupanca;");
			System.out.println("3 - para verificar a soma do montante deste Banco.");
			System.out.println("s - para encerrar o sistema.");

			op = ler.nextLine();

			switch (op) {
			case "1": {
				Corrente cc = new Corrente();
				cc.verificarArquivo(caminhoCorrente);

				do {
					System.out.println("Carregando opcoes para Conta Corrente. Aguarde...\n");
					System.out.println(" Escolha a opcao: \n==================");
					System.out.println("1 - para cadastrar uma Conta Corrente;");
					System.out.println(
							"2 - para alterar informacoes/sacar/depositar/verificar saldo/localizar uma Conta Corrente;");
					System.out.println("3 - para informar quantas Contas Correntes existem;");
					System.out.println("4 - para listar todas as Contas Correntes existentes;");
					System.out.println("5 - para excluir uma Conta Corrente existente;");
					System.out.println("6 - para exibir Conta Corrente com maior saldo;");
					System.out.println("s - para encerrar o sistema.");

					op = ler.nextLine();

					switch (op) {
					case "1": {
						System.out.println("Iniciando sessao de cadastramento para Conta Corrente. Aguarde...\n");
						cc.cadastrar(ler);
						cc.salvarCorrente(caminhoCorrente);

						System.out.println("\nConta Corrente cadastrada com sucesso!\n");
						break;
					}
					case "2": {
						do {
							System.out.println(" Escolha a opcao: \n==================");
							System.out.println("1 - para alterar informacoes da Conta Corrente;");
							System.out.println("2 - para sacar da Conta Corrente;");
							System.out.println("3 - para depositar na Conta Corrente;");
							System.out.println("4 - para verificar saldo da Conta Corrente;");
							System.out.println("5 - para verificar se uma Conta Corrente esta cadastrada;");
							System.out.println("s - para encerrar o sistema.");

							op = ler.nextLine();

							switch (op) {
							case "1": {
								cc.atualizarCorrente(caminhoCorrente, ler);
								break;
							}
							case "2": {
								cc.salvarSaque(caminhoCorrente, ler);
								break;
							}
							case "3": {
								cc.salvarDeposito(caminhoCorrente, ler);
								break;
							}
							case "4": {
								cc.verificarSaldo(caminhoCorrente, ler);
								break;
							}
							case "5": {
								cc.localizarCorrente(caminhoCorrente, ler);
								break;
							}
							case "s":
							case "S": {
								System.out.println("Saindo...\n");
								break;
							}
							default: {
								System.out.println("Opcao invalida!");
								break;
							}
							}
						} while (!op.equalsIgnoreCase("S"));
						break;
					}
					case "3": {
						cc.contarCorrentes(caminhoCorrente);
						break;
					}
					case "4": {
						cc.imprimirCorrentes(caminhoCorrente);
						break;
					}
					case "5": {
						cc.excluirConta(caminhoCorrente, ler);
						break;
					}
					case "6": {
						cc.verificarMaior(caminhoCorrente);
						break;
					}
					case "s":
					case "S": {
						System.out.println("Saindo...\n");
						break;
					}
					default: {
						System.out.println("Opcao invalida!");
						break;
					}
					}
				} while (!op.equalsIgnoreCase("S"));
				break;
			}

			case "2": {
				Poupanca cp = new Poupanca();
				cp.verificarArquivo(caminhoPoupanca);

				do {

					System.out.println("Carregando opcoes para Conta Poupanca. Aguarde...\n");
					System.out.println(" Escolha a opcao: \n==================");
					System.out.println("1 - para cadastrar uma Conta Poupanca;");
					System.out.println(
							"2 - para alterar informacoes/sacar/depositar/verificar saldo/localizar uma Conta Poupanca;");
					System.out.println("3 - para informar quantas Contas Poupancas existem;");
					System.out.println("4 - para listar todas as Contas Poupancas existentes;");
					System.out.println("5 - para excluir uma Conta Poupanca existente;");
					System.out.println("6 - para exibir Conta Poupanca com menor saldo;");
					System.out.println("7 - para aplicar rendimento na Conta Poupanca.");
					System.out.println("s - para encerrar o sistema.");

					op = ler.nextLine();

					switch (op) {
					case "1": {
						System.out.println("Iniciando sessao de cadastramento para Conta Poupanca. Aguarde...\n");

						cp.cadastrar(ler);
						cp.salvarPoupanca(caminhoPoupanca);

						System.out.println("\nConta Poupanca cadastrada com sucesso! \n");
						break;
					}
					case "2": {
						do {
							System.out.println(" Escolha a opcao: \n==================");
							System.out.println("1 - para alterar informacoes da Conta Poupanca;");
							System.out.println("2 - para sacar da Conta Poupanca;");
							System.out.println("3 - para depositar na Conta Poupanca;");
							System.out.println("4 - para verificar saldo da Conta Poupanca.");
							System.out.println("5 - para verificar se uma Conta Poupanca esta cadastrada.");
							System.out.println("s - para encerrar o sistema.");

							op = ler.nextLine();

							switch (op) {
							case "1": {
								cp.atualizarPoupanca(caminhoPoupanca, ler);
								break;
							}
							case "2": {
								cp.salvarSaque(caminhoPoupanca, ler);
								break;
							}
							case "3": {
								cp.salvarDeposito(caminhoPoupanca, ler);
								break;
							}
							case "4": {
								cp.verificarSaldo(caminhoPoupanca, ler);
								break;
							}
							case "5": {
								cp.localizarPoupanca(caminhoPoupanca, ler);
								break;
							}
							case "s":
							case "S": {
								System.out.println("Saindo...\n");
								break;
							}
							default: {
								System.out.println("Opcao invalida!");
								break;
							}
							}
						} while (!op.equalsIgnoreCase("S"));
						break;
					}
					case "3": {
						cp.contarPoupancas(caminhoPoupanca);
						break;
					}
					case "4": {
						cp.imprimirPoupancas(caminhoPoupanca);
						break;
					}
					case "5": {
						cp.excluirConta(caminhoPoupanca, ler);
						break;
					}
					case "6": {
						cp.verificarMaior(caminhoPoupanca);
						break;
					}
					case "7": {
						cp.aplicarRendimento(caminhoPoupanca, ler);
						break;
					}
					default: {
						System.out.println("Opcao invalida!");
						break;
					}
					case "s":
					case "S": {
						System.out.println("Saindo...\n");
						break;
					}
					}
				} while (!op.equalsIgnoreCase("S"));

			}
				break;
			case "3": {
				Corrente cc = new Corrente();
				Poupanca cp = new Poupanca();
				cp.somaPoupanca(caminhoPoupanca);
				cc.somaCorrente(caminhoCorrente);

				double resultado = cp.getSomaP() + cc.getSomaC();
				System.out.println("Montante deste Banco: " + resultado);
			}
			case "s":
			case "S": {
				System.out.println("Saindo...\n");
				break;
			}
			default: {
				System.out.println("Opcao invalida!");
				break;
			}
			}
		} while (!(op.equalsIgnoreCase("s") | op.equals("S")));
		System.out.println("Fim do programa.\n");
	}
}
