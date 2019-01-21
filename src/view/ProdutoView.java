package view;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dao.ProdutoDAO;
import model.Produto;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import java.awt.Toolkit;

public class ProdutoView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6371598699261801930L;
	private JPanel contentPane;
	private JTextField txtQuantidade;
	private JTextField txtPreco;
	private JTextField txtDescricao;
	private JTextField txtCodigo;
	private JTable table;
	private JTextField txtPesquisar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProdutoView frame = new ProdutoView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ProdutoView() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ProdutoView.class.getResource("/IMG/iconfinder_java_386470.png")));
		setTitle("CRUD EM JAVA COM SQLITE");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 608, 592);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCodigo = new JLabel("CODIGO");
		lblCodigo.setBounds(29, 55, 46, 14);
		contentPane.add(lblCodigo);
		
		JLabel lblDescricao = new JLabel("DESCRICAO");
		lblDescricao.setBounds(29, 102, 72, 14);
		contentPane.add(lblDescricao);
		
		JLabel lblPreco = new JLabel("PRECO");
		lblPreco.setBounds(29, 143, 46, 14);
		contentPane.add(lblPreco);
		
		JLabel lblQuantidade = new JLabel("QUANTIDADE");
		lblQuantidade.setBounds(29, 187, 83, 14);
		contentPane.add(lblQuantidade);
		
		txtQuantidade = new JTextField();
		txtQuantidade.setBounds(122, 184, 355, 20);
		contentPane.add(txtQuantidade);
		txtQuantidade.setColumns(10);
		
		txtPreco = new JTextField();
		txtPreco.setBounds(122, 140, 355, 20);
		contentPane.add(txtPreco);
		txtPreco.setColumns(10);
		
		txtDescricao = new JTextField();
		txtDescricao.setBounds(122, 99, 355, 20);
		contentPane.add(txtDescricao);
		txtDescricao.setColumns(10);
		
		txtCodigo = new JTextField();
		txtCodigo.setEditable(false);
		txtCodigo.setBounds(122, 52, 355, 20);
		contentPane.add(txtCodigo);
		txtCodigo.setColumns(10);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setIcon(new ImageIcon(ProdutoView.class.getResource("/IMG/iconfinder_floppy_285657 (2).png")));
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(txtCodigo.getText().equals("")) {
					Produto produto = new Produto();
					ProdutoDAO dao  = new ProdutoDAO();
					produto.setDescricao(txtDescricao.getText());
					produto.setPreco(Double.parseDouble(txtPreco.getText().replace(",",".")));
					produto.setQuantidade(Double.parseDouble(txtQuantidade.getText().replace(",",".")));
					dao.inserir(produto);
					mostrarTabela();
					limpar();
				}else {
					Produto produto = new Produto();
					ProdutoDAO dao  = new ProdutoDAO();
					produto.setCodigo(Integer.parseInt(txtCodigo.getText()));
					produto.setDescricao(txtDescricao.getText());
					produto.setPreco(Double.parseDouble(txtPreco.getText().replace(",",".")));
					produto.setQuantidade(Double.parseDouble(txtQuantidade.getText().replace(",",".")));
					dao.alterar(produto);
					mostrarTabela();
					limpar();
				}
			}
		});
		btnSalvar.setBounds(122, 235, 97, 23);
		contentPane.add(btnSalvar);
		
		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.setIcon(new ImageIcon(ProdutoView.class.getResource("/IMG/iconfinder_sign-info_299086.png")));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		btnLimpar.setBounds(379, 235, 97, 23);
		contentPane.add(btnLimpar);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.setIcon(new ImageIcon(ProdutoView.class.getResource("/IMG/iconfinder_sign-error_299045 (1).png")));
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int i = JOptionPane.showConfirmDialog(null, "Deseja realmente remover", "Deseja Remover", JOptionPane.YES_NO_OPTION);
				if (i == 0) {
					ProdutoDAO dao = new ProdutoDAO();
					dao.excluir(Integer.parseInt(txtCodigo.getText()));
					JOptionPane.showMessageDialog(null,"O Produto "+ txtDescricao.getText() + "foi removido com successo");
					limpar();
				}
			}	
		});
		btnExcluir.setBounds(250, 235, 97, 23);
		contentPane.add(btnExcluir);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 296, 547, 246);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				selecionaDados();
			}
		});
		scrollPane.setViewportView(table);
		
		txtPesquisar = new JTextField();
		txtPesquisar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				String descricao = txtPesquisar.getText();
				pesquisarTablela(descricao);
			}
		});
		txtPesquisar.setBounds(122, 269, 355, 20);
		contentPane.add(txtPesquisar);
		txtPesquisar.setColumns(10);
		
		JLabel lblPesquisar = new JLabel("Pesquisar");
		lblPesquisar.setIcon(new ImageIcon(ProdutoView.class.getResource("/IMG/iconfinder_search_285651.png")));
		lblPesquisar.setBounds(29, 272, 83, 14);
		contentPane.add(lblPesquisar);
		
		JButton btnCriarDatabase = new JButton("Tabela");
		btnCriarDatabase.setIcon(new ImageIcon(ProdutoView.class.getResource("/IMG/iconfinder_Database_4_40097.png")));
		btnCriarDatabase.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent arg0) {
				ProdutoDAO dao = new ProdutoDAO();
				dao.criarDatabase();
			}
		});
		btnCriarDatabase.setBounds(29, 11, 155, 23);
		contentPane.add(btnCriarDatabase);
		
		
		mostrarTabela();
	}
	
	public void mostrarTabela() {
		
		DefaultTableModel model = new DefaultTableModel(){
			 //bloqueia editat coluna
			private static final long serialVersionUID = 1L;

			@Override
			    public boolean isCellEditable(int row, int column) {
			       //all cells false
			       return false;
			    }
		};
		model.addColumn("CODIGO");
		model.addColumn("DESCRICÃO");
		model.addColumn("PRECO");
		model.addColumn("QUANTIDADE");
		table.setModel(model);		

		ProdutoDAO dao = new ProdutoDAO();
		List<Produto> listaProduto = new ArrayList<>();
		listaProduto = dao.listarTodos();
		
		for (Produto prod : listaProduto) {
			String[] linha = new String[5];
			linha[0] = Integer.toString(prod.getCodigo());
			linha[1] = prod.getDescricao();
			linha[2] = prod.getPreco().toString().replace(".",",");
			linha[3] = prod.getQuantidade().toString().replace(".",",");
			model.addRow(linha);
		}
		
	}
	
public void pesquisarTablela(String descricao) {
		
		DefaultTableModel model = new DefaultTableModel(){
			 //bloqueia editat coluna
			private static final long serialVersionUID = 1L;

			@Override
			    public boolean isCellEditable(int row, int column) {
			       //all cells false
			       return false;
			    }
		};
		model.addColumn("CODIGO");
		model.addColumn("DESCRICÃO");
		model.addColumn("PRECO");
		model.addColumn("QUANTIDADE");
		table.setModel(model);		

		ProdutoDAO dao = new ProdutoDAO();
		List<Produto> listaProduto = new ArrayList<>();
		listaProduto = dao.listarDescricao(descricao);
		
		for (Produto prod : listaProduto) {
			String[] linha = new String[5];
			linha[0] = Integer.toString(prod.getCodigo());
			linha[1] = prod.getDescricao();
			linha[2] = prod.getPreco().toString().replace(".",",");
			linha[3] = prod.getQuantidade().toString().replace(".",",");
			model.addRow(linha);
		}
		
	}
	public void selecionaDados() {
		int row = table.getSelectedRow();
		txtCodigo.setText((String) table.getValueAt(row, 0));
		txtDescricao.setText((String) table.getValueAt(row, 1));
		txtPreco.setText((String) table.getValueAt(row, 2));
		txtQuantidade.setText((String) table.getValueAt(row, 3));
	}
	public void limpar() {
		txtCodigo.setText("");
		txtDescricao.setText("");
		txtPreco.setText("");
		txtQuantidade.setText("");
	}
}
