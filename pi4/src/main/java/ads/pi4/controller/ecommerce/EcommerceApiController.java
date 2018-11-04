package ads.pi4.controller.ecommerce;

import ads.pi4.DAO.ClienteDAO;
import ads.pi4.DAO.ProdutoDAO;
import ads.pi4.model.Cliente;
import ads.pi4.model.Produto;
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
    
    @PostMapping("/cliente") //Cadastro de produto
    @ResponseBody
    public Cliente cadastar(@RequestBody Cliente cliente) {
        return ClienteDAO.inserir(cliente);
    };
}
