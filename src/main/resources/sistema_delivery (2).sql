-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Tempo de geração: 30/07/2025 às 11:53
-- Versão do servidor: 8.0.39
-- Versão do PHP: 8.3.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `sistema_delivery`
--

-- --------------------------------------------------------

--
-- Estrutura para tabela `carrinho`
--

DROP TABLE IF EXISTS `carrinho`;
CREATE TABLE IF NOT EXISTS `carrinho` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ativo` bit(1) DEFAULT NULL,
  `data_criacao` datetime(6) DEFAULT NULL,
  `usuario_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKr4876hogfc76pdmp8hl0xv2nw` (`usuario_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `enderecos`
--

DROP TABLE IF EXISTS `enderecos`;
CREATE TABLE IF NOT EXISTS `enderecos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `bairro` varchar(255) DEFAULT NULL,
  `cep` varchar(255) DEFAULT NULL,
  `cidade` varchar(255) DEFAULT NULL,
  `complemento` varchar(255) DEFAULT NULL,
  `estado` varchar(255) DEFAULT NULL,
  `numero` varchar(255) DEFAULT NULL,
  `rua` varchar(255) DEFAULT NULL,
  `usuario_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKftlds300twyikgdq1eohyitri` (`usuario_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Despejando dados para a tabela `enderecos`
--

INSERT INTO `enderecos` (`id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `numero`, `rua`, `usuario_id`) VALUES
(1, 'Jardim', '01000-000', 'São Paulo', 'Ap. 101', 'SP', '100', 'Rua das Flores', 2),
(2, 'Centro', '02000-000', 'São Paulo', 'Casa C', 'SP', '500', 'Av. Central', 2),
(3, 'Jardim', '01000-000', 'São Paulo', 'Ap. 101', 'SP', '100', 'Rua das Flores', 1),
(4, 'Centro', '02000-000', 'São Paulo', 'Casa C', 'SP', '500', 'Av. Central', 1);

-- --------------------------------------------------------

--
-- Estrutura para tabela `item_carrinho`
--

DROP TABLE IF EXISTS `item_carrinho`;
CREATE TABLE IF NOT EXISTS `item_carrinho` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `preco_com_desconto` double NOT NULL,
  `preco_unitario_no_momento` double NOT NULL,
  `quantidade` int NOT NULL,
  `carrinho_id` bigint DEFAULT NULL,
  `produto_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKr3dusq21jhlttwc4hanxhlbua` (`carrinho_id`),
  KEY `FK7he6x1mtdwm4fmlsa09yxjifx` (`produto_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `item_pedido`
--

DROP TABLE IF EXISTS `item_pedido`;
CREATE TABLE IF NOT EXISTS `item_pedido` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `preco_unitario` double DEFAULT NULL,
  `quantidade` int DEFAULT NULL,
  `pedido_id` bigint DEFAULT NULL,
  `produto_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK60ym08cfoysa17wrn1swyiuda` (`pedido_id`),
  KEY `FKtk55mn6d6bvl5h0no5uagi3sf` (`produto_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `pedido`
--

DROP TABLE IF EXISTS `pedido`;
CREATE TABLE IF NOT EXISTS `pedido` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `data_pedido` datetime(6) DEFAULT NULL,
  `endereco_entrega` varchar(255) DEFAULT NULL,
  `forma_pagamento` varchar(255) DEFAULT NULL,
  `status` enum('CANCELADO','EM_PREPARACAO','EM_TRANSPORTE','ENTREGUE','PENDENTE') DEFAULT NULL,
  `valor_total` double DEFAULT NULL,
  `usuario_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6uxomgomm93vg965o8brugt00` (`usuario_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `pedidos`
--

DROP TABLE IF EXISTS `pedidos`;
CREATE TABLE IF NOT EXISTS `pedidos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `data_hora_pedido` datetime(6) DEFAULT NULL,
  `metodo_pagamento` varchar(255) DEFAULT NULL,
  `status` enum('A_CAMINHO','CANCELADO','EM_PREPARO','ENTREGUE','PENDENTE') DEFAULT NULL,
  `valor_total` double DEFAULT NULL,
  `endereco_id` bigint NOT NULL,
  `usuario_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKekuochlfkprh1dyhpi3nanalj` (`endereco_id`),
  KEY `FKonf32qpq8pb2950dfgiyevy1h` (`usuario_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Despejando dados para a tabela `pedidos`
--

INSERT INTO `pedidos` (`id`, `data_hora_pedido`, `metodo_pagamento`, `status`, `valor_total`, `endereco_id`, `usuario_id`) VALUES
(1, '2025-07-26 19:09:58.929979', 'Dinheiro', 'PENDENTE', 140, 1, 2),
(2, '2025-07-26 19:25:11.100636', 'Dinheiro', 'PENDENTE', 114, 1, 2);

-- --------------------------------------------------------

--
-- Estrutura para tabela `pedido_itens`
--

DROP TABLE IF EXISTS `pedido_itens`;
CREATE TABLE IF NOT EXISTS `pedido_itens` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `preco_unitario` double DEFAULT NULL,
  `quantidade` int DEFAULT NULL,
  `pedido_id` bigint NOT NULL,
  `produto_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKa3dnlbbof21fsp6gnngyqix1e` (`pedido_id`),
  KEY `FKg643g2v6jo93qic9tybfesskv` (`produto_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Despejando dados para a tabela `pedido_itens`
--

INSERT INTO `pedido_itens` (`id`, `preco_unitario`, `quantidade`, `pedido_id`, `produto_id`) VALUES
(1, 24.5, 2, 1, 5),
(2, 7.5, 2, 1, 13),
(3, 38, 2, 1, 4),
(4, 38, 3, 2, 4);

-- --------------------------------------------------------

--
-- Estrutura para tabela `produto`
--

DROP TABLE IF EXISTS `produto`;
CREATE TABLE IF NOT EXISTS `produto` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  `preco` double DEFAULT NULL,
  `restaurante_id` bigint NOT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `ativo` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_restaurante_id` (`restaurante_id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Despejando dados para a tabela `produto`
--

INSERT INTO `produto` (`id`, `nome`, `preco`, `restaurante_id`, `image_url`, `descricao`, `ativo`) VALUES
(1, 'Feijoada', 29.9, 1, '/images/feijoada.png', NULL, b'0'),
(2, 'Macarronada', 24.5, 1, '/images/macarronada.png', NULL, b'0'),
(4, 'Pizza Portuguesa', 38, 2, '/images/pizzaportuguesa.png', NULL, b'0'),
(5, 'Salada de fruta', 24.5, 1, '/images/saladadefruta.png', NULL, b'0'),
(13, 'suco', 7.5, 1, '/images/suco.png', NULL, b'0'),
(22, 'rua', 22, 2, '/images/placeholder_restaurante.png', 'rgwr', b'0'),
(28, 'Chop', 2, 1, '/images/chop.png', 'O melhor da cidade\r\n', b'0'),
(29, 'Feijoada', 29.9, 1, '/images/feijoada.png', 'Seu melhor almoço de toda quinta - feira', b'1'),
(30, 'Macarronada', 24.5, 1, '/images/macarronada.png', 'Seu melhor almoço da segunda - feira', b'1'),
(31, 'Salada de Frutas', 24.5, 1, '/images/saladadefruta.png', 'Uma delicia de sobremesa ', b'1'),
(32, 'Suco', 7.5, 1, '/images/suco.png', 'O melhor suco natural da região(escolha seu sabor)', b'1'),
(33, 'Chop', 10, 1, '/images/chop.png', 'Uma cerveja as vezes cai bem', b'1'),
(34, 'Portuguesa', 38, 2, '/images/pizzaportuguesa.png', 'Uma deliçia de sabor', b'1'),
(35, 'Pizza de chocolate', 40, 2, '/images/pizzachocolate.png', 'Uma diferença de sabor', b'1'),
(40, 'Pizza tamanho Familía', 100, 2, '/images/pizzafamilia.png', 'Grande para toda a sua familía', b'1'),
(41, 'Pizza de Calabresa', 60, 20, '/images/pizzacalabresa.png', 'Um sabor diferenciado', b'1'),
(42, 'Cachorro quente', 15, 20, '/images/cachorroquente.png', 'Vendemos Lanches, experimente o melhor', b'1'),
(43, 'X - Tudo', 30, 20, '/images/xtudo.png', 'O melhor', b'1'),
(44, 'Cerveja', 12, 22, '/images/chop.png', 'A mais gelada da cidade', b'1'),
(45, 'Jantinha', 35, 22, '/images/placeholder_produto.png', 'Janta completa', b'1');

-- --------------------------------------------------------

--
-- Estrutura para tabela `restaurante`
--

DROP TABLE IF EXISTS `restaurante`;
CREATE TABLE IF NOT EXISTS `restaurante` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  `endereço` varchar(255) DEFAULT NULL,
  `endereco` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `telefone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Despejando dados para a tabela `restaurante`
--

INSERT INTO `restaurante` (`id`, `nome`, `endereço`, `endereco`, `image_url`, `telefone`) VALUES
(1, 'Restaurante Chapa Quente', 'Rua do Sol', 'Rua do Sol Nº123', '/images/restaurante1.png', NULL),
(2, 'Pizza da Vila', 'Av. Principal', 'Av.Principal Nº456', '/images/restaurante2.png', NULL),
(20, 'Sabor e cia', NULL, 'Centro Nº20', '/images/restaurante3.png', NULL),
(22, 'Bar Stard', NULL, 'Vermelhão Nº2210', '/images/restaurante4.png', NULL);

-- --------------------------------------------------------

--
-- Estrutura para tabela `usuario`
--

DROP TABLE IF EXISTS `usuario`;
CREATE TABLE IF NOT EXISTS `usuario` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `data_nascimento` date NOT NULL,
  `foto_url` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Despejando dados para a tabela `usuario`
--

INSERT INTO `usuario` (`id`, `nome`, `email`, `data_nascimento`, `foto_url`) VALUES
(1, 'Dyel Caxtro', 'oliveiraadielson@gmail', '2002-02-16', '/images/perfil.png'),
(2, 'Joana Silva', 'joana@example.com', '1990-05-15', ''),
(3, 'Carlos Pereira', 'carlos@example.com', '1985-11-22', '');

--
-- Restrições para tabelas despejadas
--

--
-- Restrições para tabelas `carrinho`
--
ALTER TABLE `carrinho`
  ADD CONSTRAINT `FK8jwo8e9vk1gdcw8ak7if31ahc` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`);

--
-- Restrições para tabelas `enderecos`
--
ALTER TABLE `enderecos`
  ADD CONSTRAINT `FKftlds300twyikgdq1eohyitri` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`);

--
-- Restrições para tabelas `item_carrinho`
--
ALTER TABLE `item_carrinho`
  ADD CONSTRAINT `FK7he6x1mtdwm4fmlsa09yxjifx` FOREIGN KEY (`produto_id`) REFERENCES `produto` (`id`),
  ADD CONSTRAINT `FKr3dusq21jhlttwc4hanxhlbua` FOREIGN KEY (`carrinho_id`) REFERENCES `carrinho` (`id`);

--
-- Restrições para tabelas `item_pedido`
--
ALTER TABLE `item_pedido`
  ADD CONSTRAINT `FK60ym08cfoysa17wrn1swyiuda` FOREIGN KEY (`pedido_id`) REFERENCES `pedido` (`id`),
  ADD CONSTRAINT `FKtk55mn6d6bvl5h0no5uagi3sf` FOREIGN KEY (`produto_id`) REFERENCES `produto` (`id`);

--
-- Restrições para tabelas `pedido`
--
ALTER TABLE `pedido`
  ADD CONSTRAINT `FK6uxomgomm93vg965o8brugt00` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`);

--
-- Restrições para tabelas `pedidos`
--
ALTER TABLE `pedidos`
  ADD CONSTRAINT `FKekuochlfkprh1dyhpi3nanalj` FOREIGN KEY (`endereco_id`) REFERENCES `enderecos` (`id`),
  ADD CONSTRAINT `FKonf32qpq8pb2950dfgiyevy1h` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`);

--
-- Restrições para tabelas `pedido_itens`
--
ALTER TABLE `pedido_itens`
  ADD CONSTRAINT `FKa3dnlbbof21fsp6gnngyqix1e` FOREIGN KEY (`pedido_id`) REFERENCES `pedidos` (`id`),
  ADD CONSTRAINT `FKg643g2v6jo93qic9tybfesskv` FOREIGN KEY (`produto_id`) REFERENCES `produto` (`id`);

--
-- Restrições para tabelas `produto`
--
ALTER TABLE `produto`
  ADD CONSTRAINT `fk_restaurante_id` FOREIGN KEY (`restaurante_id`) REFERENCES `restaurante` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
