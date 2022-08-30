package view;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import control.Conta;
import control.Corrente;
import control.Poupanca;

public class Principal {
	public static void main(String[] args) throws IOException {

		String caminhoPoupanca = System.getProperty("user.dir") + "\\poupanca.txt";
		String caminhoCorrente = System.getProperty("user.dir") + "\\corrente.txt";
		String op = null;
		Scanner ler = new Scanner(System.in);

		do {
			Conta c = new Conta();
			c.mostrarMenuOpcoesIniciais();
			op = ler.nextLine();

			switch (op) {
			case "1": {
				Corrente cc = new Corrente();
				verificarArquivo(caminhoCorrente);
				System.out.println("Carregando opcoes para Conta Corrente. Aguarde...\n");
				
				do {
					cc.mostrarMenuOpcoesGeraisCorrente();
					op = ler.nextLine();

					switch (op) {
					case "1": {
						System.out.println("Iniciando sessao de cadastro de Conta Corrente. Aguarde...\n");
						cc.cadastrarCorrente(ler);
						String retorno = (cc.salvarCorrente(caminhoCorrente) == true) ? "\nConta Corrente cadastrada com sucesso!\n" : "\nErro. Conta não cadastrada! \n";
						System.out.println(retorno);
						
						break;
					}
					case "2": {
						do {
							cc.mostrarMenuOpcoesAlteracaoCorrente();
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
								c.localizarConta(caminhoCorrente, ler, "Corrente");
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
				verificarArquivo(caminhoPoupanca);

				do {
					cp.mostrarMenuOpcoesGeraisPoupanca();
					op = ler.nextLine();

					switch (op) {
					case "1": {
						System.out.println("Iniciando sessao de cadastramento para Conta Poupanca. Aguarde...\n");
						cp.cadastrarPoupanca(ler);
						String retorno = (cp.salvarPoupanca(caminhoPoupanca) == true) ? "\nConta Poupanca cadastrada com sucesso! \n" : "\nErro. Conta não cadastrada!\n";
						System.out.println(retorno);
						
						break;
					}
					case "2": {
						do {
							cp.mostrarMenuOpcoesAlteracaoPoupanca();
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
								c.localizarConta(caminhoPoupanca, ler, "Poupanca");
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
	
	public static void verificarArquivo(String caminho) {
		try {
			File f = new File(caminho);
			if (!f.exists()) {
				String auxCaminho = caminho.substring(0, caminho.lastIndexOf("\\"));
				File fDir = new File(auxCaminho);
				fDir.mkdir();
				f.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
