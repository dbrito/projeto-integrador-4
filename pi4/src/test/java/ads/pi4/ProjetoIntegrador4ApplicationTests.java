package ads.pi4;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class ProjetoIntegrador4ApplicationTests {

    private static WebDriver driver; //Instanciamos o Selenium

    /*@BeforeClass
    public static void acessar() {
        driver = new ChromeDriver(); //Chrome como navegador
        driver.get("http://127.0.0.1:8080/admin/"); //URL a ser testada
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    }

    @AfterClass
    public static void tearDownTest(){
//            driver.quit();
    }

    @Test
    public void Teste1_LoginIncorreto(){
        //Selecione o elemento
        WebElement user = driver.findElement(By.id("user"));
        WebElement pass = driver.findElement(By.id("pass"));

        //Digito o valor do campo
        user.sendKeys("admin123");
        pass.sendKeys("admin");

        //Seleciono o elemento e disparo uma ação de clique
        driver.findElement(By.cssSelector(".btn")).click();

        //Espero até que o elemento apareça
        new WebDriverWait(driver, 60).until(ExpectedConditions.elementToBeClickable(By.cssSelector(".alert")));

        //Realizo a teste de mensagem
        assertEquals("Usuário ou senha inválidos", driver.findElement(By.cssSelector(".alert")).getText());
    }

    @Test
    public void Teste2_LoginCorreto(){
        //Selecione o elemento
        WebElement user = driver.findElement(By.id("user"));
        WebElement pass = driver.findElement(By.id("pass"));

        //Digito o valor do campo
        user.clear();
        pass.clear();
        user.sendKeys("admin");
        pass.sendKeys("admin");

        //Seleciono o elemento e disparo uma ação de clique
        driver.findElement(By.cssSelector(".btn")).click();

        //Espero o redirect
        new WebDriverWait(driver, 60).until(ExpectedConditions.urlToBe("http://127.0.0.1:8080/admin/painel#/"));

        //Verifico o titulo da página
        assertEquals("Perfumaria: Painel Administrativo", driver.getTitle());
    }

    @Test
    public void Teste3_CriarProduto(){
        new WebDriverWait(driver, 60).until(ExpectedConditions.elementToBeClickable(By.cssSelector("#menu-content li:nth-child(1) a")));
        driver.findElement(By.cssSelector("#menu-content li:nth-child(1) a")).click();
        driver.findElement(By.cssSelector(".content > button")).click();

        new WebDriverWait(driver, 60).until(ExpectedConditions.elementToBeClickable(By.name("nome")));
        driver.findElement(By.name("nome")).sendKeys("Produto 1");
        driver.findElement(By.name("marca")).sendKeys("Marca 1");
        driver.findElement(By.name("descricao")).sendKeys("Descricao 1");
        driver.findElement(By.name("quantidade")).sendKeys("100");
        Select categ = new Select(driver.findElement(By.name("categoria")));
        categ.selectByValue("Perfume");
        driver.findElement(By.name("precoOriginal")).sendKeys("6666");
        driver.findElement(By.name("precoVenda")).sendKeys("6666");
        driver.findElement(By.cssSelector("#modalForm .btn-primary")).click();

        //Espero o envio dos dados
        new WebDriverWait(driver, 60).until(ExpectedConditions.elementToBeClickable(By.cssSelector(".alert-success")));

        List<WebElement> produtos = driver.findElements(By.cssSelector(".content table tr"));
        WebElement ultimoProduto = produtos.get(produtos.size() - 1);

        assertEquals("Produto cadastrado com sucesso.", driver.findElement(By.cssSelector(".alert-success")).getText());
        assertEquals("Produto 1", ultimoProduto.findElement(By.cssSelector("td:nth-child(1)")).getText());
        assertEquals("Marca 1", ultimoProduto.findElement(By.cssSelector("td:nth-child(3)")).getText());
        assertEquals("R$ 66,66", ultimoProduto.findElement(By.cssSelector("td:nth-child(4)")).getText());
        assertEquals("R$ 66,66", ultimoProduto.findElement(By.cssSelector("td:nth-child(5)")).getText());
        assertEquals("100", ultimoProduto.findElement(By.cssSelector("td:nth-child(6)")).getText());
        assertEquals("Perfume", ultimoProduto.findElement(By.cssSelector("td:nth-child(7)")).getText());
    }

    @Test
    public void Teste4_EditarProduto(){
        List<WebElement> produtos = driver.findElements(By.cssSelector(".content table tr"));
        WebElement ultimoProduto = produtos.get(produtos.size() - 1);
        ultimoProduto.findElement(By.cssSelector("td:nth-child(8) a")).click();

        new WebDriverWait(driver, 60).until(ExpectedConditions.elementToBeClickable(By.name("nome")));
        driver.findElement(By.name("nome")).clear();
        driver.findElement(By.name("nome")).sendKeys("Produto 1.2");
        driver.findElement(By.name("marca")).clear();
        driver.findElement(By.name("marca")).sendKeys("Marca 1.2");
        driver.findElement(By.name("descricao")).clear();
        driver.findElement(By.name("descricao")).sendKeys("Descricao 1.2");
        driver.findElement(By.name("quantidade")).clear();
        driver.findElement(By.name("quantidade")).sendKeys("200");
        Select categ = new Select(driver.findElement(By.name("categoria")));
        categ.selectByValue("Sabonete");
        driver.findElement(By.cssSelector("#modalForm .btn-primary")).click();

        //Espero o envio dos dados
        new WebDriverWait(driver, 15).until(ExpectedConditions.elementToBeClickable(By.cssSelector(".alert-success")));

        assertEquals("Produto atualizado com sucesso.", driver.findElement(By.cssSelector(".alert-success")).getText());
        assertEquals("Produto 1.2", ultimoProduto.findElement(By.cssSelector("td:nth-child(1)")).getText());
        assertEquals("Marca 1.2", ultimoProduto.findElement(By.cssSelector("td:nth-child(3)")).getText());
        assertEquals("200", ultimoProduto.findElement(By.cssSelector("td:nth-child(6)")).getText());
        assertEquals("Sabonete", ultimoProduto.findElement(By.cssSelector("td:nth-child(7)")).getText());
    }

    @Test
    public void Teste5_RemoverProduto() throws InterruptedException{
        Thread.sleep(3000);
        List<WebElement> produtos = driver.findElements(By.cssSelector(".content table tr"));
        WebElement ultimoProduto = produtos.get(produtos.size() - 1);
        WebElement btExcluir = ultimoProduto.findElement(By.cssSelector("td:nth-child(8) a:nth-child(2)"));
        new WebDriverWait(driver, 15).until(ExpectedConditions.elementToBeClickable(btExcluir));
        btExcluir.click();

        Alert alert = driver.switchTo().alert();
        alert.accept();
        new WebDriverWait(driver, 15).until(ExpectedConditions.elementToBeClickable(By.cssSelector(".alert-success")));

        List<WebElement> listaFinal = driver.findElements(By.cssSelector(".content table tr"));
        assertEquals("Produto excluido com sucesso.", driver.findElement(By.cssSelector(".alert-success")).getText());
        assertNotEquals("Produto 1.2", listaFinal.get(listaFinal.size() - 1).findElement(By.cssSelector("td:nth-child(1)")).getText());
    }*/
}
