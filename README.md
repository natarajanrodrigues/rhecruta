**Instituto Federal de Educação, Ciência e Tecnologia da Paraíba**

**Campus Cajazeiras**

**Curso Superior de Tecnologia em Análise e Desenvolvimento de Sistemas**

**Desenvolvimento de Aplicações Corporativas**

**Professor**: Ricardo de Sousa Job

**Alunos**: Natarajan, Pedro Arthur, Wensttay

<h3 align="center">
  Projeto: Rhecruta
</h3>

Para ver detalhes sobre especificaçÕes do projeto juntamente com uma breve
documentação preliminar, acesse o seguinte [gitbook](https://natarajanrodrigues.gitbooks.io/rhecruta/)



#### ROTEIRO PARA IMPLANTAR E UTILIZAR A APLICAÇÃO LOCALMENTE




1. Inicie o servidor de aplicação Java (prefeencialmente o `Payara`). Antes de tudo, devemos criar os recursos JMS que serão utilizados pelo Rhecruta. Para isso há várias formas de fazer isso; aqui deixaremos 2 opções:
  - o arquivo `jmsresources.md` na pasta raiz deste projeto contém os comandos que podem ser executados a partir da pasta do Payara para que os recursos sejam criados.
  - ou então, faça um backup do arquivo 'domain.xml' que encontra-se no caminho `payara41/glassfish/domains/domain1/config` e substitua-o pelo arquivo `domain.xml` anexado na pasta raiz deste repositório. Mais detalhes sobre o porquê desta abordagem e não o uso de @JMSDestinationDefinitions na apresentação do projeto.

2. Banco de dados

  2.a.  Crie um banco de dados Postgres com as seguintes informações para uso da solução:


| propriedade | valor |
|-----|-----|
| nome do banco | rhecruta |
| usuário | postgres |
| senha | 12345 |
| porta | 5432 |
|-----|-----|


  2.b. Utilize o arquivo presente no caminho `rhecruta-core/postgres/rhecruta-sql.sql` para construir as tabelas no banco de dados criado.


3. No terminal de linha de comando, estando na pasta raiz do projeto, execute `mvn clean install`.

4. Certifique-se que você já esteja rodando o ***Suggestions*** através do Docker. É necessário rodar este projeto em paralelo para tirar proveito de algumaas funcionalidades do mesmo que são reutilizadas dentro do Rhecruta. Para mais detalhes ver o [repositório do projeto](https://github.com/natarajanrodrigues/pos-projeto).

5. Implante o módulo `rhecruta-core` no servidor. Logo em seguida à correta execução deste primeiro, implante o módulo `rhecruta-web` no servidor. A criação automática de tabelas pelo JPA não deve ser utilizada.

6. Despois de iniciados os módulos acima já será possível a solução web proposta já estará disponível em  [http://localhost:8080/rhecruta-web/](http://localhost:8080/rhecruta-web/).

  Foi inserido um usuário `admin` para início de uso do sistema, com as seguinte credenciais:
    - email: admin@admin.com
    - senha: admin

7. O módulo `rhecruta-jse` pode ser inicilizado da seguintes formas:
  - abri o referido módulo e executar a classe `br.edu.ifpb.dac.rhecruta.jse.frames.LoginFrame`, ou

  - A partir da pasta raiz deste projeto execute:
  ```
  java -jar rhecruta-jse/target/rhecruta-jse-jar-with-dependencies.jar
  ```

De qualquer forma é necessário ter um usuário do tipo Appraiser("avaliador") cadastrado e aceito previamente no sistema.


<hr>


 ##### (*) o presente manual de implantação serve para o código disponível na branch `MASTER` deste repositório.  Para implantação no `Docker`, ver a branch [docker](https://github.com/natarajanrodrigues/rhecruta/tree/docker) deste repositório.
