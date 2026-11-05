UC00619 — Desenvolvimento em Java — Sessão 11
Exercício 3 — Biblioteca com Spring Data JPA e H2
Projeto: library-jpa  ·  Spring Boot 4.1.2  ·  Java 21  ·  Maven
================================================================

O QUE ESTÁ NESTA SOLUÇÃO
    pom.xml                     Spring Web, Spring Data JPA, H2, spring-boot-h2console (só Boot 4), Lombok
    application.properties      H2 em ficheiro (./data/librarydb), ddl-auto=update, show-sql, consola H2
    Book.java                   @Entity com @Table, @Id, @GeneratedValue, @Column (+ Lombok)
    BookRepository.java         JpaRepository<Book, Long> + 3 métodos derivados
    DataLoader.java             CommandLineRunner: guarda 4 livros (só na 1.ª vez) e mostra as consultas

1. ABRIR E CORRER NO INTELLIJ
-----------------------------
1. Descompactar o zip (não abrir o projeto de dentro do zip).
2. IntelliJ: File → Open → escolher a pasta library-jpa (a que tem o pom.xml) → Trust Project.
3. Esperar que o Maven descarregue as dependências. Imports a vermelho: janela Maven → Reload All Maven Projects.
4. Lombok: Settings → Build, Execution, Deployment → Compiler → Annotation Processors → Enable annotation processing.
5. Correr: botão ▶ ao lado do main em LibraryJpaApplication.java.

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


2. CONSOLA H2
-------------
Com a aplicação a correr:  http://localhost:8080/h2-console
    JDBC URL:   jdbc:h2:file:./data/librarydb      (tem de ser IGUAL à do application.properties)
    User Name:  sa
    Password:   (vazio)
    → Connect →  SELECT * FROM BOOKS;
Se a tabela não aparece: o JDBC URL está diferente (ex.: o valor por defeito jdbc:h2:~/test).


3. PROBLEMAS FREQUENTES
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


4. USAR COM SPRING BOOT 3
-------------------------
Regra do curso: Spring Boot 3 no mínimo, nunca 2. Para Spring Boot 3.x, no pom.xml:
    versão do <parent> → 3.x
    spring-boot-starter-webmvc         → spring-boot-starter-web
    retirar spring-boot-h2console      (no Boot 3 a consola H2 vem com o h2)
    spring-boot-starter-data-jpa-test e spring-boot-starter-webmvc-test → spring-boot-starter-test
O código Java é igual nas duas versões (jakarta.persistence nas duas).

Verificação: o código Java foi compilado com Java 21 contra as assinaturas da JPA e do Spring Data e a lógica
do DataLoader foi executada com um repositório em memória; nesta máquina não foi possível descarregar
as dependências do Maven Central. Correr a solução uma vez antes da aula.
