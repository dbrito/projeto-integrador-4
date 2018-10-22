package ads.pi4.controller.admin;

import ads.pi4.DAO.ProdutoDAO;
import ads.pi4.DAO.UsuarioDAO;
import ads.pi4.model.Produto;
import ads.pi4.model.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping({"/"})    
    public String login(HttpServletRequest req) {
        System.out.println("/");
        HttpSession sessao = req.getSession(true);            
        if (sessao.getAttribute("admin") != null) {
            return "redirect:/admin/painel";
        }        
        return "admin/login";
    }
    
    @PostMapping("/")
    public ResponseEntity<Object> login(@RequestParam(value = "user", required = true) String user, @RequestParam(value = "pass", required = true) String pass, HttpServletRequest req) {                
        try {
            HttpSession sessao = req.getSession(true);            
            Usuario admin;
            admin = UsuarioDAO.efetuarLogin(user, pass);            
            if (admin != null) {
                sessao.setAttribute("admin", admin);  
                return ResponseEntity.status(HttpStatus.OK).body("Login efetuado com sucesso.");                
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuario ou senha inválidos.");                
            }            
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuario ou senha inválidos.");                
        }                
    }
    
    @GetMapping("/logout")    
    public String logout(HttpServletRequest req) {        
        HttpSession sessao = req.getSession(true);            
        sessao.removeAttribute("admin");
        return "redirect:/admin/";
    }
    
    @GetMapping("/painel")
    public String painel(HttpServletRequest req) {
        HttpSession sessao = req.getSession(true);            
        if (sessao.getAttribute("admin") == null) {
            return "redirect:/admin/";
        }
        
        return "admin/painel";
    }
    
//    public ResponseEntity<Object> cadastar(@RequestBody Produto pd) {
//        try {
//            ProdutoDAO.inserir(pd);
//            return ResponseEntity.status(HttpStatus.OK).body("Produto cadastrado com sucesso.");
//        } catch (Exception ex) {
//            System.out.print(ex);
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao cadastrar o produto.<br>" + ex.getMessage());
//        }
//    };
        
    
    @GetMapping("/api/produtos")
    public void listarProdutos(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");                                                                 
        List<Produto> produtos = ProdutoDAO.listar();
        ObjectMapper mapper = new ObjectMapper();        
        try {
            response.getWriter().print(mapper.writeValueAsString(produtos));
        } catch (IOException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @DeleteMapping("/api/produtos/{id}")
    public ResponseEntity<Object> excluirProduto(@PathVariable("id") int id, HttpServletResponse response) {
        try {
            ProdutoDAO.excluir(id);
            return ResponseEntity.status(HttpStatus.OK).body("Produto excluido com sucesso.");
        } catch (Exception ex) {
            System.out.print(ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao excluir o produto.<br>" + ex.getMessage());
        }        
    }

    @GetMapping("/ex3")
    public ModelAndView exemplo3(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "idade", defaultValue = "99") int idade) {
        ModelAndView resposta = new ModelAndView("view-exemplo3");
        if (nome == null || nome.trim().length() == 0) {
            nome = "Desconhecido";
        }
        resposta.addObject("nome", nome);
        resposta.addObject("idade", idade);
        resposta.addObject("dataHora", LocalDateTime.now());
        return resposta;
    }

    @GetMapping("/ex4/{nomeParam}")
    public ModelAndView exemplo4(@PathVariable("nomeParam") String nome) {
        ModelAndView resposta = new ModelAndView("view-exemplo4");
        resposta.addObject("nome", nome);
        resposta.addObject("dataHora", LocalDateTime.now());
        return resposta;
    }

}