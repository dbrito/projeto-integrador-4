package ads.pi4.controller.admin;

import ads.pi4.DAO.ProdutoDAO;
import ads.pi4.DAO.UsuarioDAO;
import ads.pi4.model.Produto;
import ads.pi4.model.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping({"/"}) //Tela de Login    
    public String login(HttpServletRequest req) {
        System.out.println("/");
        HttpSession sessao = req.getSession(true);            
        if (sessao.getAttribute("admin") != null) {
            return "redirect:/admin/painel";
        }        
        return "admin/login";
    }
    
    @PostMapping("/") //Efetuar Login
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
    
    @GetMapping("/logout") //Efetuar Logout
    public String logout(HttpServletRequest req) {        
        HttpSession sessao = req.getSession(true);            
        sessao.removeAttribute("admin");
        return "redirect:/admin/";
    }
    
    @GetMapping("/painel") //Tela do BackOffice
    public String painel(HttpServletRequest req) {
        HttpSession sessao = req.getSession(true);            
        if (sessao.getAttribute("admin") == null) {
            return "redirect:/admin/";
        }        
        return "admin/painel";
    }
    
    @PostMapping("/api/upload") //Upload de fotos
    public ResponseEntity<Object> login(@RequestParam("arquivo") MultipartFile arquivo, HttpServletRequest req) {                
        try {            
//            if (req.getSession(true).getAttribute("admin") != null) {;;
                byte[] bytesArquivo = arquivo.getBytes();
                String extensao = arquivo.getOriginalFilename().split("\\.")[1];
                String nomeArquivoFinal = (new Date()).getTime() + "." + extensao;
                Path destino = Paths.get("C:/Jobs/uploads/" + nomeArquivoFinal);
                Files.write(destino, bytesArquivo);                
                return ResponseEntity.status(HttpStatus.OK).body(nomeArquivoFinal);                
//            } else {
//                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuario ou senha inválidos.");                
//            }            
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao realizar o upload do arquivo");                
        }                
    }
    
    @PostMapping("/api/produtos") //Cadastro de produto
    public ResponseEntity<Object> cadastar(@RequestBody Produto pd) {
        try {
            int idProduto = ProdutoDAO.inserir(pd);
            return ResponseEntity.status(HttpStatus.OK).body(idProduto);
        } catch (Exception ex) {
            System.out.print(ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao cadastrar o produto.<br>" + ex.getMessage());
        }
    };
    
    @PostMapping("/api/produtos/{id}") //Cadastro de produto
    public ResponseEntity<Object> editar(@PathVariable("id") int id, @RequestBody Produto pd) {
        try {
            ProdutoDAO.atualizar(pd);
            return ResponseEntity.status(HttpStatus.OK).body("produto atualizado com sucesso.");
        } catch (Exception ex) {
            System.out.print(ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao cadastrar o produto.<br>" + ex.getMessage());
        }
    };
            
    @GetMapping("/api/produtos") //JSON com a lista de Produtos
    @ResponseBody
    public List<Produto> listarProdutos(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");                                                                 
        return ProdutoDAO.listar(null, false);                
    }
    
    @DeleteMapping("/api/produtos/{id}") //Exclui um produto especifico
    public ResponseEntity<Object> excluirProduto(@PathVariable("id") int id, HttpServletResponse response) {
        try {
            ProdutoDAO.excluir(id);
            return ResponseEntity.status(HttpStatus.OK).body("Produto excluido com sucesso.");
        } catch (Exception ex) {
            System.out.print(ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao excluir o produto.<br>" + ex.getMessage());
        }        
    }           
}