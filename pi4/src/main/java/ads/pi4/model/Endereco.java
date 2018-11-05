package ads.pi4.model;

public class Endereco {
    private int id;
    private String cep;
    private String endereco;
    private int numero;
    private String complemento;    
    private String bairro;    
    private String cidade;
    private String estado;
    
    public Endereco() { }
    
    public Endereco(Endereco ref) {
        this.cep = ref.cep;
        this.endereco = ref.endereco;
        this.numero = ref.numero;
        this.complemento = ref.complemento;
        this.bairro = ref.bairro;
        this.cidade = ref.cidade;
        this.estado = ref.estado;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
    
    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }        

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
}
