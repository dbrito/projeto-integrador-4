package ads.pi4.controller.ecommerce;

import ads.pi4.DAO.ProdutoDAO;
import ads.pi4.model.Produto;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class EcommerceController {
    
    @GetMapping //Home do ecommerce
    public ModelAndView home(HttpServletRequest req) {                
        return  new ModelAndView("ecommerce/index")
                .addObject("novidades", ProdutoDAO.listar("novidades", false))
                .addObject("maisVendidos", ProdutoDAO.listar("mais-vendidos", false));                
    }
    
    @GetMapping("/categoria/{categoria}") //Categoria    
    public ModelAndView categoria(@PathVariable("categoria") String categoria) {                
        return  new ModelAndView("ecommerce/categoria")
                .addObject("novidades", ProdutoDAO.listar("novidades", false))
                .addObject("maisVendidos", ProdutoDAO.listar("mais-vendidos", false));        
    }
    
    @GetMapping("/produto/{id}") //Categoria    
    public ModelAndView produto(@PathVariable("id") int id) {                                        
        return  new ModelAndView("ecommerce/produto")
                .addObject("produto", ProdutoDAO.obter(id))
                .addObject("relacionados", ProdutoDAO.listar("relacionados", false));        
    }
    
    @GetMapping("/carrinho") //Carrinho    
    public ModelAndView carrinho(HttpServletRequest req) {                        
        return  new ModelAndView("ecommerce/carrinho")
            .addObject("relacionados", ProdutoDAO.listar("relacionados", false));                
    }        
    
    
    @GetMapping("/ecommerce/api/carrinho") //Carrinho    
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
}
