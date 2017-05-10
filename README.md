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



#### ROTEIRO PARA IMPLANTAR E UTILIZAR A APLICAÇÃO COM DOCKER (*)




1. Certifique-se que o seu serviço **Docker** esteja iniciado e que não haja outros serviços rodando nas seguintes portas: 8082, 8083, 4951, 4952 e 5434.

2. No terminal de linha de comando, execute `docker network create cliente_default_ntw`. Este comando é necessário para criarmos a rede docker que será usada para comunicação entre os containers da aplicação e do banco de dados.

3. Certifique-se que você já esteja rodando o ***Suggestions*** através do Docker. É necessário rodar este projeto em paralelo para tirar proveito de algumaas funcionalidades do mesmo que são reutilizadas dentro do Rhecruta. Para mais detalhes ver o [repositório do projeto](https://github.com/natarajanrodrigues/pos-projeto).

4. Para iniciar os containers do ***Rhecruta***, a partir da pasta raiz deste projeto execute `sh ./run.sh` (\*\*). A partir deste passo, a aplicação já deve estar disponível para uso, logo após os containers terem inicializado.

5. Se desejar parar todos os containers e remover os volumes de persistência de dados, pode ser executado `sh ./stop.sh`(\*\*).

  \*\* Os scripts `run.sh` e `stop.sh` são válidos para sistemas unix-like.

  Os passos acima devem ser suficientes para iniciar os containers/módulos, bem como aqueles responsáveis pela persistência.

6. Depois de inicializados os containers, o serviço web estará disponívesl em [http://localhost:8083/rhecruta-web/](http://localhost:8083/rhecruta-web/).

  Foi inserido um usuário `admin` para início de uso do sistema, com as seguinte credenciais:
    - email: admin@admin.com
    - senha: admin
<hr>

#### O que os scripts fazem:


1. `run.sh`:
  - compila o módulos, construindo os arquivos `.war` a serem implantados no Docker.
  - inicia os containers através do Docker/docker-compose.
  - ao terminar a inicialação do último container, o script é encadeado com a execução de um comando que faz acompanhamento dos logs de inicialização da imagem do `Payara` que abriga o módulo/container `rhecruta-core`. A visualização deste log tem finalidade puramente observatória e pode ser cancelada a qualquer tempo sem prejuízo para execução dos containers no Docker.

2. `stop.sh`:
  - encerra todos os containers iniciados através do docker-compose;
  - remove as imagens do projeto que foram criadas pelo script de inicalização;
  - remove o volume de persistência do container baseado em `Postgresql`. Sinta-se livre, por exemplo, para comentar esta última linha no arquivo com intuito de preservar o conteúdo do volume antes de encerrar a execução dos containers.

<hr>

 ##### (*) o presente manual de implantação serve para o código disponível na branch `docker` deste repositório.  Para implantação local, sem o Docker, ver a branch `master`.
