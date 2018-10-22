package ads.pi4.model;

import java.text.NumberFormat;
import java.util.Locale;

public class Produto {
    //Atributos
    private Integer enabled;
    private Integer id;
    private String codigo;
    private String nome;
    private String marca;
    private double preco;
    private int quantidade;
    private String categoria;
    private String descricao;

    
    //Métodos de acesso
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public Integer getEnabled() {
        return enabled;
    }
    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }
    
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
    
    public String getPrecoFormatado() {
        Locale ptBr = new Locale("pt", "BR");        
        return NumberFormat.getCurrencyInstance(ptBr).format(this.preco).replace("R$ ", "");
    }
    
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public int getQuantidade() {
        return quantidade;
    }   
}