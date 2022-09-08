package view;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import control.Conta;
import control.Corrente;
import control.Poupanca;

public class Principal {
	public static void main(String[] args) throws IOException {
		
		//ler padrão de projeto: https://refactoring.guru/pt-br/design-patterns/strategy

		String caminhoPoupanca = System.getProperty("user.dir") + "\\poupanca.txt";
		String caminhoCorrente = System.getProperty("user.dir") + "\\corrente.txt";
		String retorno, op = null;
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
						retorno = (cc.salvarCorrente(caminhoCorrente) == true) ? "\nConta Corrente cadastrada com sucesso!\n" : "\nErro. Conta nao cadastrada! \n";
						System.out.println(retorno);
						
						break;
					}
					case "2": {
						do {
							cc.mostrarMenuOpcoesAlteracaoCorrente();
							op = ler.nextLine();

							switch (op) {
							case "1": {
								retorno = (c.atualizarConta(caminhoCorrente, ler, "Corrente") == true) ? "\nConta Corrente atualizada com sucesso!\n" : "\nErro. Conta nao atualizada! \n";
								System.out.println(retorno);
								break;
							}
							case "2": {
								retorno = (c.iniciarProcessoSaque(caminhoCorrente, ler, "Corrente") == true) ? "\nSaque realizado com sucesso!\n" : "\nErro. Saque nao realizado! \n";
								System.out.println(retorno);
								break;
							}
							case "3": {
								retorno = (c.iniciarProcessoDeposito(caminhoCorrente, ler, "Corrente") == true) ? "\nDeposito realizado com sucesso!\n" : "\nErro. Deposito nao realizado! \n";
								System.out.println(retorno);
								break;
							}
							case "4": {
								c.verificarSaldo(caminhoCorrente, ler);
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
						cc.contarTotalDeContasCadastradas(caminhoCorrente, "Correntes");
						break;
					}
					case "4": {
						c.imprimirContas(caminhoCorrente);
						break;
					}
					case "5": {
						retorno = (c.excluirConta(caminhoCorrente, ler, "Corrente") == true) ? "\nExclusao realizada com sucesso!\n" : "\nErro. Exclusao nao realizada! \n";
						System.out.println(retorno);
						break;
					}
					case "6": {
						cc.exibirMaiorSaldo(caminhoCorrente);
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
						System.out.println("Iniciando sessao de cadastro para Conta Poupanca. Aguarde...\n");
						cp.cadastrarPoupanca(ler);
						retorno = (cp.salvarPoupanca(caminhoPoupanca) == true) ? "\nConta Poupanca cadastrada com sucesso! \n" : "\nErro. Conta nao cadastrada!\n";
						System.out.println(retorno);
						
						break;
					}
					case "2": {
						do {
							cp.mostrarMenuOpcoesAlteracaoPoupanca();
							op = ler.nextLine();

							switch (op) {
							case "1": {
								retorno = (c.atualizarConta(caminhoPoupanca, ler, "Poupanca") == true) ? "\nConta Poupanca atualizada com sucesso! \n" : "\nErro. Conta nao atualizada!\n";
								System.out.println(retorno);
								break;
							}
							case "2": {
								retorno = (c.iniciarProcessoSaque(caminhoPoupanca, ler, "Poupanca") == true) ? "\nSaque realizado com sucesso! \n" : "\nErro. Saque nao realizado!\n";
								System.out.println(retorno);
								break;
							}
							case "3": {
								retorno = (c.iniciarProcessoDeposito(caminhoPoupanca, ler, "Poupanca") == true) ? "\nDeposito realizado com sucesso! \n" : "\nErro. Deposito nao realizado!\n";
								System.out.println(retorno);
								break;
							}
							case "4": {
								c.verificarSaldo(caminhoPoupanca, ler);
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
						cp.contarTotalDeContasCadastradas(caminhoPoupanca, "Poupancas");
						break;
					}
					case "4": {
						c.imprimirContas(caminhoPoupanca);
						break;
					}
					case "5": {
						retorno = (c.excluirConta(caminhoPoupanca, ler, "Poupanca") == true) ? "\nExclusao realizada com sucesso! \n" : "\nErro. Exclusao nao realizada!\n";
						break;
					}
					case "6": {
						cp.exibirMenorSaldo(caminhoPoupanca);
						break;
					}
					case "7": {
						cp.exibirSaldoComRendimento(caminhoPoupanca, ler);
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
				double somatorioSaldoCorrentes = c.somarSaldosDasContas(caminhoCorrente, "Correntes");
				double somatorioSaldoPoupancas = c.somarSaldosDasContas(caminhoPoupanca, "Poupancas");
				
				System.out.println("Montante deste Banco: " + c.calcularMontanteDoBanco(somatorioSaldoCorrentes, somatorioSaldoPoupancas));
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
		ler.close();
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
