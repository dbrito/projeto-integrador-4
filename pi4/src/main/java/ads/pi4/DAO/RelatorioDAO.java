/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ads.pi4.DAO;

import ads.pi4.model.ItemVenda;
import ads.pi4.model.Relatorio;
import ads.pi4.model.Venda;
import ads.pi4.utils.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author victor
 */
public class RelatorioDAO  {

public static List<Relatorio> listarVendasResumo(Date dtini, Date dtfim) {
        Connection con = ConnectionFactory.getConnetion();
        PreparedStatement stmt = null;
        List<Relatorio> relatorio = new ArrayList<Relatorio>();
        
        try {
            stmt = con.prepareStatement("select \n" +
                "        a.id_venda\n" +
                "	,c.nome\n" +
                "	,a.id_cliente\n" +
                "       ,a.status\n" +
                "       ,a.data_venda\n" +
                "       ,sum(1) vendas\n" +
                "       ,sum(b.quantidade) quantidade_produtos\n" +
                "       ,sum(b.preco_produto) preco_total\n" +
                "from venda a\n" +
                "inner join item_venda b\n" +
                "	on a.id_venda = b.id_venda\n" +
                "inner join cliente c\n" +
                "	on a.id_cliente = c.id\n" +
                "where data_venda between ? and ?" +
                "group by\n" +
                "	 a.id_venda\n" +
                "	,c.nome\n" +
                "	,a.id_cliente\n" +
                "       ,a.data_venda\n" +
                "       ,a.status");
            stmt.setDate(1, (java.sql.Date) dtini); 
            stmt.setDate(2, (java.sql.Date) dtfim); 
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Relatorio relatorioLinha = new Relatorio();
                relatorioLinha.setIdvenda(rs.getInt("id_venda"));
                relatorioLinha.setNome(rs.getString("nome"));
                relatorioLinha.setIdcliente(rs.getInt("id_cliente"));
                relatorioLinha.setStatus(rs.getString("status"));
                relatorioLinha.setDatavenda(rs.getDate("data_venda"));
                relatorioLinha.setVendas(rs.getInt("vendas"));
                relatorioLinha.setQuantidade(rs.getInt("quantidade_produtos"));
                relatorioLinha.setPrecovenda(rs.getDouble("preco_total"));
                
                relatorio.add(relatorioLinha);
            }
            
        }
        catch (SQLException ex) { ex.printStackTrace(); }
        finally{ ConnectionFactory.closeConnection(con, stmt); }
        return relatorio;
    }
    
}
