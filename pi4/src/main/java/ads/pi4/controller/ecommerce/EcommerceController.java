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
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/entrar") //Entrar e Cadastro
    public ModelAndView entrar(HttpServletRequest req) {
        return  new ModelAndView("ecommerce/cadastro");
    }

    @GetMapping("/minha-conta") //Compras do usuário
    public ModelAndView categoria(HttpServletRequest req) {
        Cliente cliente = (Cliente) req.getSession(true).getAttribute("cliente");
        if (cliente == null) return  new ModelAndView("redirect:/");
        return  new ModelAndView("ecommerce/minha-conta")
                .addObject("compras", VendaDAO.listarPorCliente(cliente.getId()));
    }

    @GetMapping("/categoria/{categoria}") //Categoria
    public ModelAndView categoria(@PathVariable("categoria") String categoria) {
        return  new ModelAndView("ecommerce/categoria")
                .addObject("categoria", categoria)
                .addObject("produtos", ProdutoDAO.listar(categoria, true));
    }

    @GetMapping("/buscar") //Categoria
    public ModelAndView buscar(@RequestParam("valor") String valor) {
        return  new ModelAndView("ecommerce/busca")
                .addObject("valor", valor)
                .addObject("produtos", ProdutoDAO.procurar(valor));
    }

    @GetMapping("/produto/{id}") //Detalhe do produto
    public ModelAndView produto(@PathVariable("id") int id) {
        Produto produto = ProdutoDAO.obter(id);
        return  new ModelAndView("ecommerce/produto")
                .addObject("produto", produto)
                .addObject("relacionados", ProdutoDAO.procurar(produto.getMarca()));
    }

    @GetMapping("/carrinho") //Carrinho
    public ModelAndView carrinho(HttpServletRequest req) {
        return  new ModelAndView("ecommerce/carrinho")
                .addObject("relacionados", ProdutoDAO.listar("relacionados", false));
    }

    @GetMapping("/checkout") //Checkout
    public ModelAndView checkout(HttpServletRequest req) {
        if (req.getSession(true).getAttribute("cliente") == null) return  new ModelAndView("redirect:/entrar?redirect=home");
        return  new ModelAndView("ecommerce/checkout");
    }
}
