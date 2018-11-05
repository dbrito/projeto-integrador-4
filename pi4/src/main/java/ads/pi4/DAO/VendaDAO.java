package ads.pi4.DAO;

import ads.pi4.model.Endereco;
import ads.pi4.model.ItemVenda;
import ads.pi4.model.Produto;
import ads.pi4.model.Venda;
import ads.pi4.utils.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VendaDAO {

    public static Venda inserir (Venda venda){
        Connection con = ConnectionFactory.getConnetion();
        PreparedStatement stmt = null;
        Venda nova = null;
        
        venda.setEndereco(EnderecoDAO.inserir(venda.getEndereco()));
        try {
            stmt = con.prepareStatement(""
                    + "INSERT INTO venda (id_cliente, identificador, id_endereco, status, data_venda) "
                    + "VALUES(?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, venda.getCliente().getId());
            stmt.setString(2, "P" + (new Date()).getTime());
            stmt.setInt(3, venda.getEndereco().getId());
            stmt.setString(4, "Pagamento Pendente");
            stmt.setDate(5, new java.sql.Date((new Date()).getTime()));
            stmt.execute();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int idVenda = rs.getInt(1);
                salvarItensVenda(venda, idVenda);
                nova = obter(idVenda);
            }
        }
        catch (Exception ex) { ex.printStackTrace(); }
        finally{ ConnectionFactory.closeConnection(con, stmt); }
        return nova;
    }
    
    private static void salvarItensVenda(Venda venda, int idVenda) throws SQLException, Exception {
        for (ItemVenda item : venda.getItens()) {
            Produto prd = ProdutoDAO.obter(item.getProduto().getId());
            prd.setQuantidade(prd.getQuantidade() - item.getQuantidade());
            salvarItemVenda(item, idVenda);
            atualizarEstoque(prd);
        }
    }
    
    private static void salvarItemVenda(ItemVenda item, int idVenda) throws SQLException, Exception {
        Connection con = ConnectionFactory.getConnetion();
        PreparedStatement stmt = null;                        
        try {
            stmt = con.prepareStatement("INSERT INTO item_venda (id_produto, id_venda, quantidade, preco_produto, preco_total) VALUES (?, ?, ?, ?, ?)");                        
            stmt.setInt(1, item.getProduto().getId());
            stmt.setInt(2, idVenda);
            stmt.setInt(3, item.getQuantidade());
            stmt.setDouble(4, item.getPrecoProduto());
            stmt.setDouble(5, item.getPrecoTotal());            
            stmt.execute();
        }
        catch (SQLException ex) { ex.printStackTrace(); }
        finally{ ConnectionFactory.closeConnection(con, stmt); }
    }
    
    public static void atualizarEstoque(Produto prd) throws SQLException, Exception {        
        Connection con = ConnectionFactory.getConnetion();
        PreparedStatement stmt = null;                                        
        
        try {
            stmt = con.prepareStatement("UPDATE produto SET quantidade=? WHERE (id=?)");
            stmt.setInt(1, prd.getQuantidade());
            stmt.setInt(2, prd.getId());
            stmt.execute();
        }
        catch (SQLException ex) { ex.printStackTrace(); }
        finally{ ConnectionFactory.closeConnection(con, stmt); }
    }
    
    public static Venda obter (int id){
        Connection con = ConnectionFactory.getConnetion();
        PreparedStatement stmt = null;
        Venda venda = null;

        try {
            stmt = con.prepareStatement("SELECT * FROM  venda WHERE id_venda=?");
            stmt.setInt(1, id);            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) venda = parseResult(rs);
        }
        catch (SQLException ex) { ex.printStackTrace(); }
        finally{ ConnectionFactory.closeConnection(con, stmt); }
        return venda;
    } 
    
    public static List<Venda> listarPorCliente (int idCliente){
        Connection con = ConnectionFactory.getConnetion();
        PreparedStatement stmt = null;
        List<Venda> vendas = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM  venda WHERE id_cliente=?");
            stmt.setInt(1, idCliente);            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                vendas.add(parseResult(rs));
            } 
        }
        catch (SQLException ex) { ex.printStackTrace(); }
        finally{ ConnectionFactory.closeConnection(con, stmt); }
        return vendas;
    } 

    private static Venda parseResult(ResultSet rs) throws SQLException {
        Venda venda = new Venda();
        venda.setId(rs.getInt("id_venda"));
        venda.setCliente(ClienteDAO.obter(rs.getInt("id_cliente")));
        venda.setIdentificador(rs.getString("identificador"));
        venda.setStatus(rs.getString("status"));
        venda.setEndereco(EnderecoDAO.obter(rs.getInt("id_endereco")));
        venda.setData(rs.getDate("data_venda")); 
        venda.setItens(obterItensVenda(venda));
        return venda;
    }
    
    private static List<ItemVenda> obterItensVenda(Venda venda) {
        Connection con = ConnectionFactory.getConnetion();
        PreparedStatement stmt = null;
        List<ItemVenda> itens = new ArrayList<ItemVenda>();

        try {
            stmt = con.prepareStatement("SELECT * FROM  item_venda WHERE id_venda=?");
            stmt.setInt(1, venda.getId());            
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                itens.add(parseResultItem(rs));
            }
            if (rs.next()) venda = parseResult(rs);
        }
        catch (SQLException ex) { ex.printStackTrace(); }
        finally{ ConnectionFactory.closeConnection(con, stmt); }
        return itens;
    }
    
    private static ItemVenda parseResultItem(ResultSet rs) throws SQLException {
        ItemVenda item = new ItemVenda();
        item.setProduto(ProdutoDAO.obter(rs.getInt("id_produto")));
        item.setQuantidade(rs.getInt("quantidade"));
        item.setPrecoProduto(rs.getDouble("preco_produto"));
        item.setPrecoTotal(rs.getDouble("preco_total"));
        return item;
    }
}
