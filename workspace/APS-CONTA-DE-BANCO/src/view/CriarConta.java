package view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import control.Cliente;
import control.Control;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.InputMismatchException;
import java.util.Random;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.JPasswordField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CriarConta extends JFrame {

	// Instancias
	Control control = new Control();

	// Para armazenar a resolucao da maquina
	public static Toolkit toolkit = Toolkit.getDefaultToolkit();
	public static Dimension dimension = toolkit.getScreenSize();

	// Para armazenar o tipo de conta que ser� criada
	private static String TIPO_CONTA = "CORRENTE";

	// Para limitar o tamanho do campo Senha
	static int count = 1;

	// Timer utilizado para fazer as letras aparecerem devagar
	Timer timer;
	// Como as letras dever�o ficar
	String objetivoFinal = "";
	// Como as letras est�o
	String conteudoAtual = "";
	// Letra que deve ser inserida
	String letra = "";

	// Componentes
	private JPanel contentPane;
	private JTextField txtNome;
	private JTextField txtCPF;
	private JTextField txtEmail;
	public JRadioButton rdbtnCorrente;
	public JRadioButton rdbtnPoupanca;
	public JRadioButton rdbtnEspecial;
	private JPasswordField txtSenha;
	private JLabel lblCPF;
	private JButton btnPronto1;
	private JButton btnPronto2;
	private JButton btnPronto3;
	private JButton btnPronto4;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CriarConta frame = new CriarConta();
					frame.setLocationRelativeTo(null); // Deixa a janela centralizada
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public CriarConta() {

		setSize((dimension.width / 2), (dimension.height - 50)); // Muda o tamanho da janela
		setResizable(false); // Bloqueia que o usuario modifique o tamanho da janela
		setAlwaysOnTop(false); // Obriga a janela ficar em primeiro plano sempre
		setTitle("Otaner Bank - Criar conta"); // Altera o titulo da janela
		setBounds(100, 100, (dimension.width / 2), (dimension.height - 50)); // Altera o tamanho e a posicao da janela
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblVoltar = new JLabel("voltar");
		lblVoltar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				Acesso.main(null);
				dispose();
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
				lblVoltar.setCursor(cursor);
			}
		});
		lblVoltar.setBounds(10, 11, 36, 14);
		contentPane.add(lblVoltar);

		// Animacao das Letras -------------------------------------------
		timer = new Timer((50), MostrarCPF);
		timer.setRepeats(true);
		timer.start();
		// ---------------------------------------------------------------

		lblCPF = new JLabel("");
		lblCPF.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblCPF.setHorizontalAlignment(SwingConstants.CENTER);
		lblCPF.setBounds(10, 47, 647, 20);
		contentPane.add(lblCPF);

		txtCPF = new JFormattedTextField(Mascara("###.###.###-##"));
		txtCPF.setBounds(262, 78, 163, 30);
		txtCPF.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(txtCPF);
		txtCPF.setColumns(10);
		txtCPF.setVisible(false);

		JLabel lblNome = new JLabel("Certo, agora seu nome completo");
		lblNome.setHorizontalAlignment(SwingConstants.CENTER);
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNome.setBounds(10, 153, 647, 20);
		lblNome.setVisible(false);
		contentPane.add(lblNome);

		txtNome = new JTextField();
		txtNome.setHorizontalAlignment(SwingConstants.CENTER);
		txtNome.setBounds(217, 184, 250, 30);
		contentPane.add(txtNome);
		txtNome.setColumns(10);
		txtNome.setVisible(false);

		JLabel lblEmail = new JLabel(
				"Seu e-mail tamb\u00E9m porfavor ... \nele ser\u00E1 utilizado para um poss\u00EDvel \"esqueci minha\" :p");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmail.setBounds(10, 225, 647, 20);
		lblEmail.setVisible(false);
		contentPane.add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setHorizontalAlignment(SwingConstants.CENTER);
		txtEmail.setBounds(217, 256, 250, 30);
		contentPane.add(txtEmail);
		txtEmail.setColumns(10);
		txtEmail.setVisible(false);

		JLabel lblTipoConta = new JLabel("BELEZA, Agora me diz qual o tipo de conta voc\u00EA deseja criar");
		lblTipoConta.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblTipoConta.setHorizontalAlignment(SwingConstants.CENTER);
		lblTipoConta.setBounds(10, 335, 647, 20);
		lblTipoConta.setVisible(false);
		contentPane.add(lblTipoConta);

		rdbtnCorrente = new JRadioButton("Conta Corrente");
		rdbtnCorrente.setSelected(true);
		rdbtnCorrente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnCorrente.isSelected()) {
					TIPO_CONTA = "CORRENTE";
					rdbtnPoupanca.setSelected(false);
					rdbtnEspecial.setSelected(false);
				} else {
					rdbtnCorrente.setSelected(false);
				}
			}
		});
		rdbtnCorrente.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtnCorrente.setBackground(Color.WHITE);
		rdbtnCorrente.setForeground(Color.BLACK);
		rdbtnCorrente.setBounds(262, 362, 163, 23);
		rdbtnCorrente.setVisible(false);
		contentPane.add(rdbtnCorrente);

		rdbtnPoupanca = new JRadioButton("Conta Poupan\u00E7a");
		rdbtnPoupanca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnPoupanca.isSelected()) {
					TIPO_CONTA = "POUPANCA";
					rdbtnCorrente.setSelected(false);
					rdbtnEspecial.setSelected(false);
				} else {
					rdbtnPoupanca.setSelected(false);
				}
			}
		});
		rdbtnPoupanca.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtnPoupanca.setBackground(Color.WHITE);
		rdbtnPoupanca.setForeground(Color.BLACK);
		rdbtnPoupanca.setBounds(262, 388, 163, 23);
		rdbtnPoupanca.setVisible(false);
		contentPane.add(rdbtnPoupanca);

		rdbtnEspecial = new JRadioButton("Conta Especial");
		rdbtnEspecial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnEspecial.isSelected()) {
					TIPO_CONTA = "ESPECIAL";
					rdbtnCorrente.setSelected(false);
					rdbtnPoupanca.setSelected(false);
				} else {
					rdbtnEspecial.setSelected(false);
				}
			}
		});
		rdbtnEspecial.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtnEspecial.setBackground(Color.WHITE);
		rdbtnEspecial.setForeground(Color.BLACK);
		rdbtnEspecial.setBounds(262, 414, 163, 23);
		rdbtnEspecial.setVisible(false);
		contentPane.add(rdbtnEspecial);

		JLabel lblSenha = new JLabel(
				"POR FIM, precisamos definir uma senha de acesso \"de quatro\" digitos para sua conta:");
		lblSenha.setHorizontalAlignment(SwingConstants.CENTER);
		lblSenha.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblSenha.setBounds(10, 484, 647, 20);
		lblSenha.setVisible(false);
		contentPane.add(lblSenha);

		txtSenha = new JPasswordField();
		txtSenha.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (txtSenha.getText().length() > 3) {
					txtSenha.setText(txtSenha.getText().substring(0, 3));
				}
			}
		});
		txtSenha.setHorizontalAlignment(SwingConstants.CENTER);
		txtSenha.setFont(new Font("Tahoma", Font.PLAIN, 99));
		txtSenha.setBounds(170, 520, 360, 75);
		txtSenha.setVisible(false);
		contentPane.add(txtSenha);

		btnPronto4 = new JButton("PRONTO");
		btnPronto4.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// Desabiltando os campos atuais
				if (!txtSenha.getText().equals("") && txtSenha.getText().length() == 4) {
					txtSenha.setEnabled(false);
					btnPronto4.setVisible(false);

					Cliente c = new Cliente();

					int numeroConta = control.numeroContaDisponivel() + 1;

					if (numeroConta <= 9)
						c.conta = "000" + numeroConta;
					else if (numeroConta <= 99)
						c.conta = "00" + numeroConta;
					else if (numeroConta <= 999)
						c.conta = "0" + numeroConta;
					else
						c.conta = String.valueOf(numeroConta);

					if (TIPO_CONTA.equals("CORRENTE")) {
						c.conta += "-1";
					} else if (TIPO_CONTA.equals("POUPANCA")) {
						c.conta += "-2";
					} else if (TIPO_CONTA.equals("ESPECIAL")) {
						c.conta += "-3";
					}

					c.CPF = txtCPF.getText();
					c.setNome(txtNome.getText());
					c.setEmail(txtEmail.getText());
					c.setSenha(txtSenha.getText());

					c.saldo = "R$ 00,00";

					control.adicionarConta(c);

					String conta = c.getConta();
					c = null;

					JOptionPane.showMessageDialog(null,
							"A conta \"" + conta + "\" foi criada com sucesso\nvoce ja pode acessar sua conta ;)",
							"Conta Criada com SUCESSO !!!", JOptionPane.OK_OPTION);

					Acesso.main(null);
					dispose();

				} else {
					JOptionPane.showMessageDialog(null, "Erro na senha", "Ops", JOptionPane.WARNING_MESSAGE);
				}

			}

		});
		btnPronto4.setBounds(304, 627, 89, 23);
		btnPronto4.setVisible(false);
		contentPane.add(btnPronto4);

		btnPronto3 = new JButton("PRONTO");
		btnPronto3.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (!(rdbtnCorrente.isSelected()) && !(rdbtnPoupanca.isSelected()) && !(rdbtnEspecial.isSelected())) {
					JOptionPane.showMessageDialog(null, "Ative pelo menos uma das op��es porfavor xD", "Ops",
							JOptionPane.WARNING_MESSAGE);
				} else {
					// Desabiltando os campos atuais
					rdbtnCorrente.setEnabled(false);
					rdbtnPoupanca.setEnabled(false);
					rdbtnEspecial.setEnabled(false);
					btnPronto3.setVisible(false);

					// Habilitando os proximos campos
					lblSenha.setVisible(true);
					txtSenha.setVisible(true);
					btnPronto4.setVisible(true);

					// Definindo o foco para o proximo campo
					txtSenha.grabFocus();

				}
			}

		});
		btnPronto3.setBounds(568, 440, 89, 23);
		btnPronto3.setVisible(false);
		contentPane.add(btnPronto3);

		btnPronto2 = new JButton("PRONTO");
		btnPronto2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (txtNome.getText().equals("") || txtEmail.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Prencha todos os campos porfavor xD", "Ops",
							JOptionPane.WARNING_MESSAGE);
				} else {
					// Desabiltando os campos atuais
					txtNome.setEnabled(false);
					txtEmail.setEnabled(false);
					btnPronto2.setVisible(false);

					// Habilitando os proximos campos
					btnPronto3.setVisible(true);
					lblTipoConta.setVisible(true);
					rdbtnCorrente.setVisible(true);
					rdbtnPoupanca.setVisible(true);
					rdbtnEspecial.setVisible(true);
				}
			}

		});
		btnPronto2.setBounds(568, 299, 89, 25);
		btnPronto2.setVisible(false);
		contentPane.add(btnPronto2);

		btnPronto1 = new JButton("PRONTO");
		btnPronto1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (txtCPF.getText().toString().replace(".", "").replace("-", "").equals("")
						|| txtCPF.getText().equals("000.000.000 00") || txtCPF.getText().equals("111.111.111-11")
						|| txtCPF.getText().equals("222.222.222-22") || txtCPF.getText().equals("333.333.333-33")
						|| txtCPF.getText().equals("444.444.444-44") || txtCPF.getText().equals("555.555.555-55")
						|| txtCPF.getText().equals("666.666.666-66") || txtCPF.getText().equals("777.777.777-77")
						|| txtCPF.getText().equals("888.888.888-88") || txtCPF.getText().equals("999.999.999-99")) {

					txtCPF.setForeground(Color.RED);
					JOptionPane.showMessageDialog(null, "O CPF informado est� incorreto !", "Ops",
							JOptionPane.ERROR_MESSAGE);
					txtCPF.grabFocus();

				} else {
					if (ValidacaoCPF(txtCPF.getText().toString().replace(".", "").replace("-", ""))) {

						if (control.BuscarCPF(txtCPF.getText())) {
							txtCPF.setForeground(Color.RED);
							JOptionPane.showMessageDialog(null, "O CPF informado j� est� em uso !", "Ops",
									JOptionPane.ERROR_MESSAGE);
							txtCPF.grabFocus();
						} else {

							// Desabiltando os campos atuais
							txtCPF.setEnabled(false);
							txtCPF.setForeground(Color.BLACK);
							btnPronto1.setVisible(false);

							// Habilitando os proximos campos
							lblNome.setVisible(true);
							txtNome.setVisible(true);
							lblEmail.setVisible(true);
							txtEmail.setVisible(true);
							btnPronto2.setVisible(true);

							// Definindo foco para o campo "nome"
							txtNome.grabFocus();

						}

					} else {
						txtCPF.setForeground(Color.RED);
						JOptionPane.showMessageDialog(null, "O CPF informado � inv�lido !", "Ops",
								JOptionPane.ERROR_MESSAGE);
					}

				}

			}
		});
		btnPronto1.setBounds(568, 117, 89, 25);
		btnPronto1.setVisible(false);
		contentPane.add(btnPronto1);

	}

	// Metodo para criar mascaras nos campos
	public static MaskFormatter Mascara(String Mascara) {

		MaskFormatter F_Mascara = new MaskFormatter();
		try {
			F_Mascara.setMask(Mascara); // Atribui a mascara
			F_Mascara.setPlaceholderCharacter(' '); // Caracter para preencimento
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Ocorreu um erro: " + e.getMessage(), "ERRO",
					JOptionPane.ERROR_MESSAGE);
		}
		return F_Mascara;
	}

	private static boolean ValidacaoCPF(String CPF) {

		try {
			CPF += CPF.replace(".", "").replace("-", "");
		} catch (Exception e) {

		}

		char dig10, dig11;
		int sm, i, r, num, peso;

		// "try" - protege o codigo para eventuais erros de conversao de tipo (int)
		try {
			// Calculo do 1o. Digito Verificador
			sm = 0;
			peso = 10;
			for (i = 0; i < 9; i++) {
				// converte o i-esimo caractere do CPF em um numero:
				// por exemplo, transforma o caractere '0' no inteiro 0
				// (48 eh a posicao de '0' na tabela ASCII)
				num = (int) (CPF.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig10 = '0';
			else
				dig10 = (char) (r + 48); // converte no respectivo caractere numerico

			// Calculo do 2o. Digito Verificador
			sm = 0;
			peso = 11;
			for (i = 0; i < 10; i++) {
				num = (int) (CPF.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig11 = '0';
			else
				dig11 = (char) (r + 48);

			// Verifica se os digitos calculados conferem com os digitos informados.
			if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
				return true;
			else
				return false;
		} catch (InputMismatchException erro) {
			return false;
		}
	}

	// Faz o movimento da progressBar
	ActionListener MostrarCPF = new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
			try {

				String conteudo = lblCPF.getText();
				String objetivoFinal = "Eai ser humaninho ! Para come�armos a cria��o de sua conta, porfavor me informe seu CPF:";

				if (!(conteudo.equals(objetivoFinal))) {

					letra = "" + objetivoFinal.charAt(conteudoAtual.length());
					conteudoAtual += letra;

					if ((letra.equals("!")) || (letra.equals(","))) {
						timer.setDelay(500);
					} else {
						Random random = new Random();
						int delay = random.nextInt((70 - 20) + 1) + 20; // Um numero entre 20 e 70
						timer.setDelay(delay);
					}

					lblCPF.setText(conteudoAtual);

				} else {
					txtCPF.setVisible(true);
					txtCPF.grabFocus();
					// timer.setDelay(10000);
					btnPronto1.setVisible(true);
					timer.stop();
				}

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Ocorreu um erro: " + e.getMessage(), "ERRO",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	};

}
