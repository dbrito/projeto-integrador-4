package ads.pi4.DAO;

import ads.pi4.model.Cliente;
import ads.pi4.model.Endereco;
import ads.pi4.model.Usuario;
import ads.pi4.utils.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class ClienteDAO {

    public static Cliente inserir (Cliente cliente){
        Connection con = ConnectionFactory.getConnetion();
        PreparedStatement stmt = null;
        Cliente novo = null;
        //Insiro o endereco
        // cliente.setEndereco(EnderecoDAO.inserir(cliente.getEndereco()));

        try {
            stmt = con.prepareStatement(""
                    + "INSERT INTO cliente (cpf, nome, data_nascimento, telefone, email, senha) "
                    + "VALUES(?,?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
            // passando os dados para o insert
            stmt.setString(1, cliente.getCpf());
            stmt.setString(2, cliente.getNome());
            stmt.setDate(3, new java.sql.Date(cliente.getDataNascimento().getTime()));
            stmt.setString(4, cliente.getTelefone());
            stmt.setString(5, cliente.getEmail());
            stmt.setString(6, cliente.getSenha()); //Vinculo o endereco ao cliente
            stmt.execute();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int novoId = rs.getInt(1);
                novo = obter(novoId);
            }
        }
        catch (Exception ex) { System.out.print(ex); }
        finally{ ConnectionFactory.closeConnection(con, stmt); }
        return novo;
    }

    public static Cliente atualizar (Cliente end){
        Connection con = ConnectionFactory.getConnetion();
        PreparedStatement stmt = null;
        Cliente cliente = null;

        try {
            stmt = con.prepareStatement("UPDATE cliente SET id_endereco=? WHERE id=?");
            stmt.setInt(1, end.getEndereco().getId());
            stmt.setInt(2, end.getId());
            stmt.execute();
        }
        catch (SQLException ex) { System.out.print(ex); }
        finally{ ConnectionFactory.closeConnection(con, stmt); }
        return cliente;
    }

    public static Cliente obter (int id){
        Connection con = ConnectionFactory.getConnetion();
        PreparedStatement stmt = null;
        Cliente cliente = null;

        try {
            stmt = con.prepareStatement("SELECT * FROM  cliente WHERE id=?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) cliente = parseResult(rs);
        }
        catch (SQLException ex) { System.out.print(ex); }
        finally{ ConnectionFactory.closeConnection(con, stmt); }
        return cliente;
    }

    public static Cliente logar (Cliente cliente){
        Connection con = ConnectionFactory.getConnetion();
        PreparedStatement stmt = null;
        Cliente logado = null;

        try {
            stmt = con.prepareStatement("SELECT * FROM  cliente WHERE email=? AND senha=?");
            stmt.setString(1, cliente.getEmail());
            stmt.setString(2, cliente.getSenha());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) logado = parseResult(rs);
        }
        catch (SQLException ex) { System.out.print(ex); }
        finally{ ConnectionFactory.closeConnection(con, stmt); }
        return logado;
    }

    private static Cliente parseResult(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setId(rs.getInt("id"));
        cliente.setCpf(rs.getString("cpf"));
        cliente.setNome(rs.getString("nome"));
        cliente.setTelefone(rs.getString("telefone"));
        cliente.setEmail(rs.getString("email"));
        cliente.setEndereco(EnderecoDAO.obter(rs.getInt("id_endereco")));
        cliente.setAtivo(rs.getInt("ativo"));
        return cliente;
    }

}
