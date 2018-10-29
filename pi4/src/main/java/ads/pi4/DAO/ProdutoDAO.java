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
    
    private static int totalProdutos = 0;
    private static List<Produto> listaProdutos = new ArrayList<Produto>();
        
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
            stmt = con.prepareStatement("SELECT * FROM produto where ativo=1");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {                
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
                produtos.add(produto);
            }
        } catch (SQLException ex) {
            System.out.print(ex);            
        } finally{
            ConnectionFactory.closeConnection(con, stmt);
        }      
        Collections.shuffle(produtos);
        return produtos;
    }
        
    public static List<Produto> procurar(String valor) throws SQLException, Exception {        
        String sql = "SELECT * FROM produto WHERE ((UPPER(nome) LIKE UPPER(?) "
            + "OR UPPER(codigo) LIKE UPPER(?) OR UPPER(marca) LIKE UPPER(?)) AND enabled=1)";        
        List<Produto> listaProdutos = null;        
        Connection connection = null;        
        PreparedStatement preparedStatement = null;
        //Armazenará os resultados do banco de dados
        ResultSet result = null;
        try {
            //Abre uma conexão com o banco de dados
            connection = ConnectionFactory.getConnetion();
            //Cria um statement para execução de instruções SQL
            preparedStatement = connection.prepareStatement(sql);
            //Configura os parâmetros do "PreparedStatement"
            preparedStatement.setString(1, "%" + valor + "%");
            preparedStatement.setString(2, "%" + valor + "%");
            preparedStatement.setString(3, "%" + valor + "%");            
            //Executa a consulta SQL no banco de dados
            result = preparedStatement.executeQuery();
            
            //Itera por cada item do resultado
            while (result.next()) {
                //Se a lista não foi inicializada, a inicializa
                if (listaProdutos == null) {
                    listaProdutos = new ArrayList<Produto>();
                }
                //Cria uma instância de Cliente e popula com os valores do BD
                Produto produto = new Produto();
                produto.setId(result.getInt("id"));
                produto.setCodigo(result.getString("codigo"));
                produto.setNome(result.getString("nome"));
                produto.setMarca(result.getString("marca"));
                produto.setPrecoOriginal(result.getDouble("preco"));
                produto.setQuantidade(result.getInt("quantidade"));
                produto.setDescricao(result.getString("descricao"));
                produto.setCategoria(result.getString("categoria"));
                produto.setEnabled(result.getInt("enabled"));
                //Adiciona a instância na lista
                listaProdutos.add(produto);
            }
        } finally {
            //Se o result ainda estiver aberto, realiza seu fechamento
            if (result != null && !result.isClosed()) {
                result.close();
            }
            //Se o statement ainda estiver aberto, realiza seu fechamento
            if (preparedStatement != null && !preparedStatement.isClosed()) {
                preparedStatement.close();
            }
            //Se a conexão ainda estiver aberta, realiza seu fechamento
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        //Retorna a lista de clientes do banco de dados
        return listaProdutos;        
    }    
    
    public static Produto obter(int id) throws SQLException, Exception {
        //Compõe uma String de consulta que considera apenas o cliente
        //com o ID informado e que esteja ativo ("enabled" com "true")
        String sql = "SELECT * FROM produto WHERE (id=?)";

        //Conexão para abertura e fechamento
        Connection connection = null;
        //Statement para obtenção através da conexão, execução de
        //comandos SQL e fechamentos
        PreparedStatement preparedStatement = null;
        //Armazenará os resultados do banco de dados
        ResultSet result = null;
        try {
            //Abre uma conexão com o banco de dados
            connection = ConnectionFactory.getConnetion();
            //Cria um statement para execução de instruções SQL
            preparedStatement = connection.prepareStatement(sql);
            //Configura os parâmetros do "PreparedStatement"
            preparedStatement.setInt(1, id);            
            
            //Executa a consulta SQL no banco de dados
            result = preparedStatement.executeQuery();
            
            //Verifica se há pelo menos um resultado
            if (result.next()) {                
                //Cria uma instância de Cliente e popula com os valores do BD
                Produto produto = new Produto();
                produto.setId(result.getInt("id"));
                produto.setNome(result.getString("nome"));
                produto.setMarca(result.getString("marca"));                
                produto.setPrecoVenda(result.getDouble("preco"));
                produto.setCategoria(result.getString("categoria"));
                produto.setQuantidade(result.getInt("quantidade"));
                produto.setDescricao(result.getString("descricao"));
                produto.setEnabled(result.getInt("ativo"));                
                //Retorna o resultado
                return produto;
            }            
        } finally {
            //Se o result ainda estiver aberto, realiza seu fechamento
            if (result != null && !result.isClosed()) {
                result.close();
            }
            //Se o statement ainda estiver aberto, realiza seu fechamento
            if (preparedStatement != null && !preparedStatement.isClosed()) {
                preparedStatement.close();
            }
            //Se a conexão ainda estiver aberta, realiza seu fechamento
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }

        //Se chegamos aqui, o "return" anterior não foi executado porque
        //a pesquisa não teve resultados
        //Neste caso, não há um elemento a retornar, então retornamos "null"
        return null;
    }
}