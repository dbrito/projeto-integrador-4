-- --------------------------------------------------------
-- Servidor:                     127.0.0.1
-- Versão do servidor:           5.7.18 - MySQL Community Server (GPL)
-- OS do Servidor:               Win64
-- HeidiSQL Versão:              9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Copiando estrutura do banco de dados para projeto_integrador
CREATE DATABASE IF NOT EXISTS `projeto_integrador` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `projeto_integrador`;

-- Copiando estrutura para tabela projeto_integrador.cliente
CREATE TABLE IF NOT EXISTS `cliente` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cpf` varchar(255) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `data_nascimento` date NOT NULL,
  `telefone` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `ativo` bit(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- Copiando dados para a tabela projeto_integrador.cliente: ~3 rows (aproximadamente)
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` (`id`, `cpf`, `nome`, `data_nascimento`, `telefone`, `email`, `ativo`) VALUES
	(16, '139.104.129-18', 'Kevin Caleb Vicente Araújo', '1996-04-15', '(11) 9809-4052', 'user1@uol.com', b'1'),
	(18, '970.168.604-77', 'Guilherme Davi da Costa', '1996-10-13', '(47) 2814-6313', '', b'1'),
	(19, '006.053.111-88', 'Murilo Fábio Fernando Pinto', '1996-11-15', '', '', b'1');
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;

-- Copiando estrutura para tabela projeto_integrador.item_venda
CREATE TABLE IF NOT EXISTS `item_venda` (
  `id_item_venda` int(11) NOT NULL AUTO_INCREMENT,
  `id_produto` int(11) NOT NULL,
  `quantidade` int(11) NOT NULL,
  `id_venda` int(11) NOT NULL,
  `preco` double DEFAULT NULL,
  PRIMARY KEY (`id_item_venda`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- Copiando dados para a tabela projeto_integrador.item_venda: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `item_venda` DISABLE KEYS */;
/*!40000 ALTER TABLE `item_venda` ENABLE KEYS */;

-- Copiando estrutura para tabela projeto_integrador.produto
CREATE TABLE IF NOT EXISTS `produto` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  `marca` varchar(255) DEFAULT NULL,
  `imagem` varchar(500) DEFAULT NULL,
  `preco_original` double NOT NULL,
  `preco_venda` double NOT NULL,
  `quantidade` int(11) NOT NULL,
  `categoria` varchar(255) DEFAULT NULL,
  `descricao` longtext,
  `ativo` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_produto_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8;

-- Copiando dados para a tabela projeto_integrador.produto: ~8 rows (aproximadamente)
/*!40000 ALTER TABLE `produto` DISABLE KEYS */;
INSERT INTO `produto` (`id`, `nome`, `marca`, `imagem`, `preco_original`, `preco_venda`, `quantidade`, `categoria`, `descricao`, `ativo`) VALUES
	(63, 'The Secret Temptation Masculino Eau de Toilette', 'Antonio Banderas', '1540782849148.jpg', 149, 149, 100, 'Perfume', 'Antonio Banderas seduz mesmo sem querer, seu sucesso é algo que simplesmente acontece. E é justamente a pouca importância que atribui ao seu dom que o torna ainda mais desejável. Esse é o segredo do seu sucesso. Uma fragrância sensual, refinada e extremamente sedutora. Um segredo revelado para homens modernos que não se importam com as regras pré-estabelecidas da sedução, para quem o natural se conjuga com o essencial.', b'1'),
	(64, 'King of Seduction Absolute masculino Eau de Toilette', 'Antonio Banderas', '1540783108882.jpg', 94, 94, 100, 'Perfume', 'Antonio Banderas apresenta King of Seduction Absolute, uma nova fragrância refrescante e muito carismática, inspirada por King of Seduction. Um verdadeiro tributo aos poderes da sedução absoluta, encarnados pelo sedutor definitivo.', b'1'),
	(65, 'Seduction in Bblack Eau de Toilette masculino', 'Antonio Banderas', '1540783240318.jpg', 79, 79, 100, 'Perfume', 'Antonio Banderas apresenta King of Seduction Absolute, uma nova fragrância refrescante e muito carismática, inspirada por King of Seduction. Um verdadeiro tributo aos poderes da sedução absoluta, encarnados pelo sedutor definitivo.', b'1'),
	(66, 'Perfume Antonio Banderas The Golden Secret masculino Eau de Toilette', 'Antonio Banderas', '1540783388047.jpg', 74, 74, 100, 'Perfume', 'Para homens que não abrem mão da sedução e poder, The Golden Secrets chegou para potencializar essas sensações. Perfeito paras ser usado em ocasiões especiais, essa fragrância tem como segredo um concentrado de notas marcantes que traduzem esse segredo.', b'1'),
	(67, 'Her Secret Temptation feminino Eau de Toilette', 'Antonio Banderas', '1540783448022.jpg', 99, 99, 100, 'Perfume', 'Uma combinação de frutas e cítricos intensificados por condimentos brilhantes. No coração da fragrância, a íris e a rosa acrescentam um toque colorido a um ramalhete de elegantes flores brancas. Na nota de fundo, a calidez do âmbar se funde em um acorde oriental, trazendo uma irresistível identidade e personalidade com as madeiras exóticas.', b'1'),
	(68, 'Mr Burberry masculino Eau de Toilette', 'Burberry', '1540783778534.jpg', 150, 150, 100, 'Perfume', 'A nova fragrância para homens por Burberry capta a essência de Londres e seus momentos, fundindo aromas clássicos da perfumaria britânica com ingredientes inesperados. Notas de cabeça frescas, trabalhada de grapefruit e cardamomo, cortados com uma base sedutora de vetiver terra e sândalo.', b'1'),
	(69, 'Perfume Antonio Banderas King of Seduction masculino', 'Antonio Banderas', '1540784240198.jpg', 94, 94, 100, 'Perfume', 'Uma combinação elegante e harmônica de frescor cítrico e força masculina, que abre a porta ao momento definitivo da sedução.\nUm furacão de elementos livres compõe uma essência de poderosos contrastes. Intensa, marcante e, ao mesmo tempo, fresca e elegante. Uma dança sensual entre força e delicadeza que transportará você ao reino da sedução.', b'1'),
	(70, 'Udv Masculino Eau de Toilette', 'Ulric de Varens', '1540784417426.jpg', 55, 55, 100, 'Perfume', 'Chipre amadeirado, com notas de mandarina, limão, estragão, mimosa, gerânio, tabaco, musgo de carvalho e madeiras nobres. Para homens elegantes, clássicos e envolventes.', b'1');
/*!40000 ALTER TABLE `produto` ENABLE KEYS */;

-- Copiando estrutura para tabela projeto_integrador.usuario
CREATE TABLE IF NOT EXISTS `usuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `cpf` varchar(255) NOT NULL,
  `user` varchar(255) NOT NULL,
  `pass` varchar(255) NOT NULL,
  `ativo` bit(1) NOT NULL,
  `perfil` varchar(255) DEFAULT 'caixa',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8;

-- Copiando dados para a tabela projeto_integrador.usuario: ~2 rows (aproximadamente)
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` (`id`, `nome`, `cpf`, `user`, `pass`, `ativo`, `perfil`) VALUES
	(73, 'Silvio Santos', '666', 'admin', 'admin', b'1', 'gerente'),
	(75, 'Luke Valentine', '4523145', 'caixa', 'caixa', b'1', 'caixa');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;

-- Copiando estrutura para tabela projeto_integrador.venda
CREATE TABLE IF NOT EXISTS `venda` (
  `id_venda` int(11) NOT NULL AUTO_INCREMENT,
  `data_venda` date NOT NULL,
  `id_cliente` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_venda`),
  UNIQUE KEY `id_venda_UNIQUE` (`id_venda`),
  KEY `fk_cliente_idx` (`id_cliente`),
  CONSTRAINT `fk_cliente` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

-- Copiando dados para a tabela projeto_integrador.venda: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `venda` DISABLE KEYS */;
/*!40000 ALTER TABLE `venda` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
