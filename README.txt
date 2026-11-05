UC00619 — Desenvolvimento em Java — Sessão 11
Exercício 3 — Biblioteca com Spring Data JPA e H2
Projeto: library-jpa · Spring Boot 4.1.2 · Java 21 · Maven
================================================================

RESULTADO ESPERADO (no log, entre as linhas do Hibernate "Hibernate: insert into books ...")
4 books saved                     ← só na primeira vez
Total books: 4
1 - Clean Code (2008)
2 - The Clean Coder (2011)
3 - Effective Java (2018)
4 - Head First Java (2005)
By Robert C. Martin:
  - Clean Code
  - The Clean Coder
Published after 2010:
  - The Clean Coder
  - Effective Java
Title contains "java":
  - Effective Java
  - Head First Java

Correr uma segunda vez: já não aparece "4 books saved" e continuam a ser 4 livros
— a base de dados é um ficheiro (pasta data, dentro do projeto) e sobrevive ao reinício.
Para começar do zero: parar a aplicação e apagar a pasta data.


CONSOLA H2
-------------
Com a aplicação a correr:  http://localhost:8080/h2-console
    JDBC URL:   jdbc:h2:file:./data/librarydb      (tem de ser IGUAL à do application.properties)
    User Name:  sa
    Password:   (vazio)
    → Connect →  SELECT * FROM BOOKS;
Se a tabela não aparece: o JDBC URL está diferente (ex.: o valor por defeito jdbc:h2:~/test).

PROBLEMAS FREQUENTES
-----------------------
- /h2-console dá 404 (Spring Boot 4): falta a dependência spring-boot-h2console
  ou spring.h2.console.enabled=true. No Boot 3 basta o h2.
- «Failed to configure a DataSource: 'url' attribute is not specified»: falta o driver (h2) no pom
  ou o spring.datasource.url no application.properties.
- «Not a managed type: class ...Book»: falta @Entity, ou a classe está fora do pacote com.example.library.
- «No property 'autor' found for type 'Book'»: o nome do método derivado não corresponde a um atributo.
- «Database may be already in use»: a aplicação já está a correr noutra janela (o ficheiro fica bloqueado).
- Getters a vermelho no IntelliJ: ativar o annotation processing (Lombok).
- Aviso "spring.jpa.open-in-view is enabled by default": é só um aviso; pode-se pôr
  spring.jpa.open-in-view=false no application.properties.
