/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ads.pi4.model;

import java.util.Date;

/**
 *
 * @author victor
 */
public class Relatorio {
    
    private Integer idvenda;
    private String nome;
    private Integer idcliente;
    private String status;
    private Date   datavenda;
    private Integer vendas;
    private Integer quantidade;
    private double precovenda;

    public Relatorio() {
    }

    public Integer getIdvenda() {
        return idvenda;
    }

    public void setIdvenda(Integer idvenda) {
        this.idvenda = idvenda;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Integer idcliente) {
        this.idcliente = idcliente;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDatavenda() {
        return datavenda;
    }

    public void setDatavenda(Date datavenda) {
        this.datavenda = datavenda;
    }

    public Integer getVendas() {
        return vendas;
    }

    public void setVendas(Integer vendas) {
        this.vendas = vendas;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public double getPrecovenda() {
        return precovenda;
    }

    public void setPrecovenda(double preco_venda) {
        this.precovenda = preco_venda;
    }
    
    
    
    
    
}


