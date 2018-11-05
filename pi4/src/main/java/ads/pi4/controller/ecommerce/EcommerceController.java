package ads.pi4.controller.ecommerce;

import ads.pi4.DAO.ClienteDAO;
import ads.pi4.DAO.ProdutoDAO;
import ads.pi4.DAO.VendaDAO;
import ads.pi4.model.Cliente;
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
        req.getSession(true).setAttribute("cliente", ClienteDAO.obter(29));
        return  new ModelAndView("ecommerce/index")
                .addObject("cliente", ClienteDAO.obter(29))
                .addObject("novidades", ProdutoDAO.listar("novidades", false))
                .addObject("maisVendidos", ProdutoDAO.listar("mais-vendidos", false));                
    }
    
    @GetMapping("/minha-conta") //Categoria    
    public ModelAndView categoria(HttpServletRequest req) {     
        Cliente cliente = (Cliente) req.getSession(true).getAttribute("cliente");
        if (cliente == null) return  new ModelAndView("redirect:/");
        return  new ModelAndView("ecommerce/minha-conta")
                .addObject("compras", VendaDAO.listarPorCliente(cliente.getId()));
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
    
    @GetMapping("/checkout") //Checkout    
    public ModelAndView checkout(HttpServletRequest req) {                        
        if (req.getSession(true).getAttribute("cliente") == null) return  new ModelAndView("redirect:/carrinho");
        return  new ModelAndView("ecommerce/checkout");                
    }
}
