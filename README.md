✈️ Descolar - Sistema de Gestão de Viagens

Sistema web simples para cadastro, edição e exclusão de viagens, feito com Spring Boot, Thymeleaf, MySQL e Bootstrap.

---

Tecnologias utilizadas

Java 17+
Spring Boot
Thymeleaf 3.5.0
Bootstrap 5.3
MySQL 8+
NetBeans (recomendado)

---

Configuração e execução

1. Clone ou importe o projeto

Abra o projeto no NetBeans.  
Se ele estiver marcado como "unloaded project", clique com o botão direito no projeto e selecione:

- Reload POM 
ou  
- Resolve Project Problems

O NetBeans irá configurar as dependências automaticamente via Maven.


2. Configure o banco de dados

Abra o MySQL Workbench ou outro cliente MySQL e execute o seguinte script SQL:

sql
-- Criação do banco de dados

CREATE DATABASE IF NOT EXISTS descolar_db;
USE descolar_db;

-- Criação da tabela viagens

CREATE TABLE IF NOT EXISTS viagens (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    destino VARCHAR(100) NOT NULL,
    embarcacao TIME NOT NULL,
    duracao INT NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

3. Executar a aplicação

Para executar a aplicação após criação do banco de dados é só clicar com o botão direito no projeto e escolher Run, ou executar a classe DescolarApplication.java

5. Acesso no navegador

Basta abrir localhost:8080 na barra de pesquisa do navegador e será automaticamente direcionado para localhost:8080/cadastro 

Perfis de usuário

Cliente: Pode visualizar viagens 

Administrador: pode cadastrar, editar e excluir viagens.





Projeto acadêmico para fins educacionais.
