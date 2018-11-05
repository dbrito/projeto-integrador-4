package ads.pi4.controller.ecommerce;

import ads.pi4.DAO.ClienteDAO;
import ads.pi4.DAO.EnderecoDAO;
import ads.pi4.DAO.ProdutoDAO;
import ads.pi4.DAO.VendaDAO;
import ads.pi4.model.Cliente;
import ads.pi4.model.Endereco;
import ads.pi4.model.ItemVenda;
import ads.pi4.model.Produto;
import ads.pi4.model.Venda;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/ecommerce/api")
public class EcommerceApiController {    
    @GetMapping("/carrinho") //Carrinho    
    @ResponseBody
    public List<Produto> pegaItens(HttpServletRequest req) {                                                
        List<Produto> itensCarrinho = new ArrayList<>();
        String[] idsProdutos = req.getParameter("produtos").split(",");
        int[] ids = new int[idsProdutos.length];        
        for (int i=0; i<ids.length; i++) {
            itensCarrinho.add(ProdutoDAO.obter(Integer.parseInt(idsProdutos[i])));
        }
        return itensCarrinho;
    }   
    
    @PostMapping("/cliente") //Cadastro de cliente
    @ResponseBody
    public Cliente cadastar(@RequestBody Cliente cliente) {
        return ClienteDAO.inserir(cliente);
    };       
    
    @GetMapping("/cliente") //Pega dados do cliente
    @ResponseBody
    public Cliente dadosCliente(HttpServletRequest req) {        
        return (Cliente) req.getSession().getAttribute("cliente");
    };       
    
    @PostMapping("/endereco") //Atualiza endereco do cliente
    @ResponseBody
    public Endereco atualizarEndereco(HttpServletRequest req, @RequestBody Endereco end) {        
        Endereco novoEnd = EnderecoDAO.atualizar(end);
        Cliente cliente = (Cliente) req.getSession().getAttribute("cliente");
        if (cliente != null) cliente.setEndereco(novoEnd);        
        return novoEnd;
    };    
    
    @PostMapping("/checkout") //Checkout    
    @ResponseBody
    public Venda realizarVenda(HttpServletRequest req, @RequestBody List<ItemVenda> itens) {                      
        Cliente cliente;
        if (req.getSession(true).getAttribute("cliente") != null) {
            cliente = (Cliente) req.getSession().getAttribute("cliente");
        } else {
            cliente = ClienteDAO.obter(29);
        }                
        Venda venda = new Venda();
        venda.setEndereco(new Endereco(cliente.getEndereco()));
        venda.setCliente(cliente);
        venda.setItens(itens);        
        return  VendaDAO.inserir(venda);                
    }
    
    @GetMapping("/compras") //Checkout    
    @ResponseBody
    public List<Venda> listarComprar(HttpServletRequest req) {                      
        Cliente cliente;
        if (req.getSession(true).getAttribute("cliente") != null) {
            cliente = (Cliente) req.getSession().getAttribute("cliente");
        } else {
            cliente = ClienteDAO.obter(29);
        }                        
        return  VendaDAO.listarPorCliente(cliente.getId());                
    }
}
