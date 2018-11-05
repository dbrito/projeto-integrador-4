package ads.pi4.DAO;

import ads.pi4.model.Endereco;
import ads.pi4.model.Produto;
import ads.pi4.utils.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EnderecoDAO {

    public static Endereco inserir (Endereco end){
        Connection con = ConnectionFactory.getConnetion();
        PreparedStatement stmt = null;
        Endereco novo = null;

        try {
            stmt = con.prepareStatement(""
                    + "INSERT INTO endereco (cep, endereco, numero, complemento, cidade, estado) "
                    + "VALUES(?,?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, end.getCep());
            stmt.setString(2, end.getEndereco());
            stmt.setInt(3, end.getNumero());
            stmt.setString(4, end.getComplemento());
            stmt.setString(5, end.getCidade());
            stmt.setString(6, end.getEstado());
            stmt.execute();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) novo = obter(rs.getInt(1));
        }
        catch (SQLException ex) { System.out.print(ex); }
        finally{ ConnectionFactory.closeConnection(con, stmt); }
        return novo;
    }
    
    public static Endereco atualizar (Endereco end){
        Connection con = ConnectionFactory.getConnetion();
        PreparedStatement stmt = null; 
        Endereco endereco = null;
        
        try {            
            stmt = con.prepareStatement("UPDATE endereco SET cep=?, endereco=?, numero=?, complemento=?, bairro=?, cidade=?, estado=? WHERE (id=?)");
            stmt.setString(1, end.getCep());
            stmt.setString(2, end.getEndereco());
            stmt.setInt(3, end.getNumero());
            stmt.setString(4, end.getComplemento());
            stmt.setString(5, end.getBairro());
            stmt.setString(6, end.getCidade());
            stmt.setString(7, end.getEstado());            
            stmt.setInt(8, end.getId());                        
            stmt.execute();
            endereco = obter(end.getId());
        }
        catch (SQLException ex) { System.out.print(ex); }
        finally{ ConnectionFactory.closeConnection(con, stmt); }
        return endereco;
    }

    public static Endereco obter (int id){
        Connection con = ConnectionFactory.getConnetion();
        PreparedStatement stmt = null;
        Endereco end = null;

        try {
            stmt = con.prepareStatement("SELECT * FROM  endereco WHERE id=?");
            stmt.setInt(1, id);
            stmt.execute();
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) end = parseResult(rs);
        }
        catch (SQLException ex) { System.out.print(ex); }
        finally{ ConnectionFactory.closeConnection(con, stmt); }
        return end;
    }

    private static Endereco parseResult(ResultSet rs) throws SQLException {
        Endereco end = new Endereco();
        end.setId(rs.getInt("id"));
        end.setCep(rs.getString("cep"));
        end.setEndereco(rs.getString("endereco"));
        end.setNumero(rs.getInt("numero"));
        end.setComplemento(rs.getString("complemento"));
        end.setBairro(rs.getString("bairro"));
        end.setCidade(rs.getString("cidade"));
        end.setEstado(rs.getString("estado"));
        return end;
    }
}
