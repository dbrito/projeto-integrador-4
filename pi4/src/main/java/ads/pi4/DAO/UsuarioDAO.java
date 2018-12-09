package ads.pi4.DAO;

import ads.pi4.utils.ConnectionFactory;
import ads.pi4.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class UsuarioDAO {
    
    private static int totalUsuario = 0;
    private static List<Usuario> listaUsuarios = new ArrayList<Usuario>();
    
    // inserir no banco de dados
    public static int inserir (Usuario usuario){
        Connection con = ConnectionFactory.getConnetion();
        PreparedStatement stmt = null;
        
        try {
            // insert para o banco
            stmt = con.prepareStatement("INSERT INTO usuario (nome, cpf, user, pass, perfil, ativo) VALUES(?,?,?,?,?,?)");
            // passando os dados para o insert            
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getCpf());
            stmt.setString(3, usuario.getUser());
            stmt.setString(4, usuario.getPass());
            stmt.setString(5, usuario.getPerfil());             
            stmt.setInt(6, 1); 
            stmt.execute();        
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.print(ex);
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
        return 0;
 }
    
    public static void atualizar(Usuario usuario) throws SQLException, Exception {
        //Monta a string de atualização do cliente no BD, utilizando
        //prepared statement
        String sql = "UPDATE usuario SET nome=?, cpf=?, user=?, pass=?, perfil=?"
            + " WHERE (id=?)";
        //Conexão para abertura e fechamento
        Connection connection = null;
        //Statement para obtenção através da conexão, execução de
        //comandos SQL e fechamentos
        PreparedStatement preparedStatement = null;
        try {
            //Abre uma conexão com o banco de dados
            connection = ConnectionFactory.getConnetion();
            //connection = ConnectionUtils.getConnection();
            //Cria um statement para execução de instruções SQL
            preparedStatement = connection.prepareStatement(sql);
            //Configura os parâmetros do "PreparedStatement"
            preparedStatement.setString(1, usuario.getNome());
            preparedStatement.setString(2, usuario.getCpf());
            preparedStatement.setString(3, usuario.getUser());
            preparedStatement.setString(4, usuario.getPass());
            preparedStatement.setString(5, usuario.getPerfil());            
            preparedStatement.setInt(6, usuario.getId());
            
            //Executa o comando no banco de dados
            preparedStatement.execute();
        } finally {
            //Se o statement ainda estiver aberto, realiza seu fechamento
            if (preparedStatement != null && !preparedStatement.isClosed()) {
                preparedStatement.close();
            }
            //Se a conexão ainda estiver aberta, realiza seu fechamento
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
    }
     
    public static void excluir(int id) throws SQLException, Exception {
        //Monta a string de atualização do cliente no BD, utilizando
        //prepared statement
        String sql = "UPDATE usuario SET ativo=0 WHERE (id=?)";
        //Conexão para abertura e fechamento
        Connection connection = null;
        //Statement para obtenção através da conexão, execução de
        //comandos SQL e fechamentos
        PreparedStatement preparedStatement = null;
        try {
            //Abre uma conexão com o banco de dados
            connection = ConnectionFactory.getConnetion();
            //Cria um statement para execução de instruções SQL
            preparedStatement = connection.prepareStatement(sql);
            //Configura os parâmetros do "PreparedStatement"
            preparedStatement.setInt(1, id);
            
            //Executa o comando no banco de dados
            preparedStatement.execute();
        } finally {
            //Se o statement ainda estiver aberto, realiza seu fechamento
            if (preparedStatement != null && !preparedStatement.isClosed()) {
                preparedStatement.close();
            }
            //Se a conexão ainda estiver aberta, realiza seu fechamento
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
    }
     
    // listar os usuarios
    public static List <Usuario>  listar () throws Exception{
        Connection con = ConnectionFactory.getConnetion();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        List<Usuario> usuarios = new ArrayList<>();
        
        try {
            stmt = con.prepareStatement("SELECT * FROM usuario where ativo=1");
            rs = stmt.executeQuery();
            while (rs.next()) {                
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));                
                usuario.setNome(rs.getString("nome"));
                usuario.setCpf(rs.getString("cpf"));
                usuario.setUser(rs.getString("user"));
                usuario.setPass(rs.getString("pass"));
                usuario.setEnabled(rs.getInt("ativo"));
                usuario.setPerfil(rs.getString("perfil"));                
                usuarios.add(usuario);
            }
        } catch (SQLException ex) {
            System.out.print(ex);
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            //JOptionPane.showMessageDialog(, "");
            
        }finally{
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        
        return usuarios;
    }        
    
    public static Usuario obter(int id) throws SQLException, Exception {
        //Compõe uma String de consulta que considera apenas o cliente
        //com o ID informado e que esteja ativo ("enabled" com "true")
        String sql = "SELECT * FROM usuario WHERE (id=?)";

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
                Usuario usuario = new Usuario();
                usuario.setId(result.getInt("id"));
                usuario.setNome(result.getString("nome"));
                usuario.setCpf(result.getString("cpf"));  
                usuario.setUser(result.getString("user"));
                usuario.setPass(result.getString("pass"));   
                usuario.setPerfil(result.getString("perfil"));                   
                usuario.setEnabled(result.getInt("ativo"));             
                //Retorna o resultado
                return usuario;
            }            
        } finally {            
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
    
    public static Usuario efetuarLogin(String user, String pass) throws SQLException, Exception {        
        String sql = "SELECT * FROM usuario WHERE (user=?) and (pass=?) and ativo=1";
        
        Connection connection = ConnectionFactory.getConnetion();        
        PreparedStatement preparedStatement = null;        
        ResultSet result = null;
        try {            
            preparedStatement = connection.prepareStatement(sql);            
            preparedStatement.setString(1, user);            
            preparedStatement.setString(2, pass);                                    
            result = preparedStatement.executeQuery();            
            //Verifica se há pelo menos um resultado
            if (result.next()) {                
                //Cria uma instância de Cliente e popula com os valores do BD
                Usuario usuario = new Usuario();
                usuario.setId(result.getInt("id"));
                usuario.setNome(result.getString("nome"));
                usuario.setCpf(result.getString("cpf"));  
                usuario.setUser(result.getString("user"));
                usuario.setPass(result.getString("pass"));   
                usuario.setPerfil(result.getString("perfil"));                   
                usuario.setEnabled(result.getInt("ativo"));             
                //Retorna o resultado
                return usuario;
            }            
        } finally {            
            if (result != null && !result.isClosed()) result.close();            
            if (preparedStatement != null && !preparedStatement.isClosed()) preparedStatement.close();            
            if (connection != null && !connection.isClosed()) connection.close();
        }
        
        return null;
    }
}