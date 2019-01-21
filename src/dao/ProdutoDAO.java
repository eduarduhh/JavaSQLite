package dao;


import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import db.Connexao;
import model.Produto;

public class ProdutoDAO {
	
	public void criarDatabase() {
		String sql = "CREATE TABLE IF NOT EXISTS produto (codigo INTEGER PRIMARY KEY AUTOINCREMENT, descricao VARCHAR (200), preco DECIMAL (10, 8), quantidade DECIMAL (10, 8));";
		try {
			Statement s = Connexao.getConexao().createStatement();
			s.execute(sql);
			
			s.close();
			JOptionPane.showMessageDialog(null, "Tabela Criada com Sucesso");

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Problema ao Criar Tabela :" +e );
		}
	}
	
	public void inserir(Produto produto) {
		String sql = "insert into produto (descricao, preco, quantidade) values (?,?,?)";
		try {
			
			Connection conn = Connexao.getConexao();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, produto.getDescricao());
			ps.setDouble(2, produto.getPreco());
			ps.setDouble(3, produto.getQuantidade());
			ps.execute();	
			JOptionPane.showMessageDialog(null, "Cadastrado com Sucesso");
			ps.close();
			conn.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Problemas no cadastro :" +e );
		}
	}
	
	
	public void alterar(Produto produto) {
		try {
			String sql = "update produto set  descricao = ? , preco = ? , quantidade = ? where codigo = ?";
			Connection conn = Connexao.getConexao();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, produto.getDescricao());
			ps.setDouble(2, produto.getPreco());
			ps.setDouble(3, produto.getQuantidade());
			ps.setInt(4, produto.getCodigo());
			ps.execute();
			ps.close();
			conn.close();
			JOptionPane.showMessageDialog(null, "Alterado com Sucesso");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Problemas na alteracao  :" +e );
		}
	}
	
	public void excluir(int codigo) {
		try {
			String sql = "delete from produto where codigo = ?";
			Connection conn = Connexao.getConexao();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, codigo);
			ps.execute();
			conn.close();
			ps.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Problemas ao excluir :" +e );
		}
	}
	public List<Produto> listarTodos() {
		List<Produto> lista = new ArrayList<Produto>();
		try {
			String sql = "select * from produto";
			Connection conn = Connexao.getConexao();
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			while(rs.next()) {
				Produto produto = new Produto();
				produto.setCodigo(rs.getInt("codigo"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setPreco(rs.getDouble("preco"));
				produto.setQuantidade(rs.getDouble("quantidade"));
				lista.add(produto);
			}
			s.close();
			conn.close();
			return lista;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Problemas ao listar :" +e );
			e.printStackTrace();
		}
		return lista;
	}
	
	
	public List<Produto> listarDescricao(String descricao) {
		List<Produto> lista = new ArrayList<Produto>();
		try {
			String sql = "select * from produto where descricao like '%"+descricao+"%'";
			Connection conn = Connexao.getConexao();
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			while(rs.next()) {
				Produto produto = new Produto();
				produto.setCodigo(rs.getInt("codigo"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setPreco(rs.getDouble("preco"));
				produto.setQuantidade(rs.getDouble("quantidade"));
				lista.add(produto);
			}
			s.close();
			conn.close();
			return lista;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Problemas ao listar :" +e );
			e.printStackTrace();
		}
		return lista;
	}
	
}
