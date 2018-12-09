package ads.pi4.DAO;

import ads.pi4.utils.ConnectionFactory;
import ads.pi4.model.Produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ProdutoDAO {

    public static int inserir (Produto produto){
        Connection con = ConnectionFactory.getConnetion();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("INSERT INTO produto (nome, marca, imagem, preco_original, preco_venda, quantidade, categoria, descricao, ativo) VALUES(?,?,?,?,?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
            // passando os dados para o insert
            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getMarca());
            stmt.setString(3, produto.getImagem());
            stmt.setDouble(4, produto.getPrecoOriginal());
            stmt.setDouble(5, produto.getPrecoVenda());
            stmt.setInt(6, produto.getQuantidade());
            stmt.setString(7, produto.getCategoria());
            stmt.setString(8, produto.getDescricao());
            stmt.setInt(9, 1);
            stmt.execute();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.print(ex);
        } finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
        return 0;
    }

    public static void atualizar(Produto produto) throws SQLException, Exception {
        Connection con = ConnectionFactory.getConnetion();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE produto SET nome=?, marca=?, imagem=?, preco_original=?, preco_venda=?, quantidade=?, categoria=?, descricao=? WHERE (id=?)");
            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getMarca());
            stmt.setString(3, produto.getImagem());
            stmt.setDouble(4, produto.getPrecoOriginal());
            stmt.setDouble(5, produto.getPrecoVenda());
            stmt.setInt(6, produto.getQuantidade());
            stmt.setString(7, produto.getCategoria());
            stmt.setString(8, produto.getDescricao());
            stmt.setInt(9, produto.getId());
            stmt.execute();
        } catch (SQLException ex) {
            System.out.print(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public static void excluir(int id) throws SQLException, Exception {
        Connection con = ConnectionFactory.getConnetion();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE produto SET ativo=0 WHERE (id=?)");
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException ex) {
            System.out.print(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    // listar os produtos
    public static List<Produto>  listar (String filtro, Boolean categoria){
        Connection con = ConnectionFactory.getConnetion();
        PreparedStatement stmt = null;
        List<Produto> produtos = new ArrayList<>();
        try {
            String query = "SELECT * FROM produto WHERE ativo=1";
            if (categoria) {
                query += " AND categoria='"+ filtro +"'";
            } else if (filtro.equals("novidades")) {
                query += "  ORDER BY id DESC";
            }
            stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                produtos.add(parseResultItem(rs));
            }
        } catch (SQLException ex) {
            System.out.print(ex);
        } finally{
            ConnectionFactory.closeConnection(con, stmt);
        }

        if (filtro.equals("maisVendidos") || filtro.equals("relacionados")) Collections.shuffle(produtos);
        return produtos;
    }

    public static List<Produto> procurar(String valor) {
        Connection con = ConnectionFactory.getConnetion();
        PreparedStatement stmt = null;
        List<Produto> produtos = new ArrayList<>();

        try {
            String query = "SELECT * FROM produto WHERE ((UPPER(nome) LIKE UPPER(?) "
            + "OR UPPER(marca) LIKE UPPER(?)) AND ativo=1)";
            stmt = con.prepareStatement(query);
            stmt.setString(1, "%" + valor + "%");
            stmt.setString(2, "%" + valor + "%");
            ResultSet rs = stmt.executeQuery();
            rs = stmt.executeQuery();

            while (rs.next()) {
                produtos.add(parseResultItem(rs));
            }
        } catch (SQLException ex) {
            System.out.print(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
        return produtos;
    }

    public static Produto obter(int id) {
        Connection con = ConnectionFactory.getConnetion();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("SELECT * FROM produto WHERE id=?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return parseResultItem(rs);
            }
        } catch (SQLException ex) {
            System.out.print(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
        return null;
    }

    public static List<Produto> obter(String ids) {
        Connection con = ConnectionFactory.getConnetion();
        PreparedStatement stmt = null;
        List<Produto> produtos = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT * FROM produto WHERE id IN (?)");
            stmt.setString(1, ids);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                produtos.add(parseResultItem(rs));
            }
        } catch (SQLException ex) {
            System.out.print(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
        return null;
    }

    private static Produto parseResultItem(ResultSet rs) throws SQLException {
        Produto produto = new Produto();
        produto.setId(rs.getInt("id"));
        produto.setNome(rs.getString("nome"));
        produto.setMarca(rs.getString("marca"));
        produto.setImagem(rs.getString("imagem"));
        produto.setPrecoOriginal(rs.getDouble("preco_original"));
        produto.setPrecoVenda(rs.getDouble("preco_venda"));
        produto.setQuantidade(rs.getInt("quantidade"));
        produto.setCategoria(rs.getString("categoria"));
        produto.setDescricao(rs.getString("descricao"));
        produto.setEnabled(rs.getInt("ativo"));
        return produto;
    }
}