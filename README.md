**Instituto Federal de Educação, Ciência e Tecnologia da Paraíba**

**Campus Cajazeiras**

**Curso Superior de Tecnologia em Análise e Desenvolvimento de Sistemas**

**Programação Orientada a Serviços**

**Professor: Ricardo de Sousa Job**

<h3 align="center">
  Atividade Avaliativa - Microservices RESTful
</h3>

Implemente uma aplicação que dispõem quatro microservices: **hotel**, **passagem**, **agência** e **clientes**.

No serviço de **clientes** deve ser implementado um serviço que mantém um cadastro compartilhado de cliente. Cada entidade cliente deve possuir um identificador, nome, cpf e renda. O serviço deve disponibilizar uma forma de acesso aos dados da entidade cliente. Este serviço será utilizado para realizar as reservas nos demais serviços.

Nos demais serviços deve ser possível realizar uma reserva para um cliente. Os serviços devem possuir no mínimo duas entidades e não deve persistir uma entidade cliente. Deve ser possível, em todos os serviços, realizar as operações de CRUD das entidades.

No serviço de **agência** deve fornecer um recurso que permite solicitar um pacote. O pacote deve possuir uma passagem e uma reserva em um hotel.


**Observações:**

Forma de entrega: um repositório na [organization](https://github.com/ifpb-disciplinas-2016-2).

O projeto deve ser **implantado com o Docker** e deve conter **um roteiro** de como realizar a implantação da aplicação.

Após a implementação, **encaminhar** um email com o link do repositório para o email sousajob@gmail.com.
Prazo para entrega: **2 de maio de 2017, às 23h**.

<hr/>


#### ROTEIRO PARA IMPLANTAR E UTILIZAR A APLICAÇÃO


1. Certifique-se que o seu serviço **Docker** esteja iniciado.

2. No terminal de linha de comando, execute `docker network create cliente_default_ntw`. Este comando é necessário para criarmos a rede docker que será usada para comunicação entre os containers da aplicação e do banco de dados.

3. No seu terminal, navegue até a pasta raiz do projeto `ativ2microservices-rs`.

4. Para iniciar os containers do projeto, execute `sh ./run.sh` (\*\*). A partir deste passo, a aplicação já deve estar disponível para uso, logo após os containers terem inicializado é claro :)

5. Se desejar parar todos os containers e remover os volumes de persistência de dados, pode ser executado `sh ./stop.sh`(\*\*).

  \*\* Os scripts `run.sh` e `stop.sh` são válidos para sistemas unix-like.

  Os passos acima devem ser suficientes para iniciar os containers com serviço restful, bem como aqueles responsáveis pela persistência.


Os recursos estão disponíveis da seguinte forma:

| Recurso | URI |
|----|-----|
| Cliente | http://localhost:8081/cliente-rs/cliente |
| Hotel | http://localhost:8082/hospedagem-rs/hotel |
| Reseva de Hotel | http://localhost:8082/hospedagem-rs/reserva |
| Empresas de Passagens | http://localhost:8083/passagem-rs/empresa |
| Reseva de Passagem | http://localhost:8083/passagem-rs/reserva |
| Pacotes da Agência | http://localhost:8084/agencia-rs/pacote |



<hr>

### Sobre o projeto:

Apesar da descrição do projeto solicitar as operações de CRUD de todas as entidades, foi discutido em sala de aula que o objetivo principal é realizar a persistência através do microserviços. Por isso, as implementações das outras operações de CRUD podem não estar presentes ou incompletas para alguns microservicos (como por exemplo, no de agência).


<hr>

#### EXEMPLO PARA TESTE DA FUNÇÕES

##### Adicionar um cliente
POST http://localhost:8081/cliente-rs/api/cliente/


Body:
```
{
	"nome": "Natarajan",
	"cpf": "007980",
	"renda": "1000.00"
}
```


Resultado:
```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<cliente>
    <cpf>007980</cpf>
    <id>4</id>
    <nome>Natarajan</nome>
    <renda>1000.00</renda>
</cliente>

```

##### Consultar Clientes
GET http://localhost:8081/cliente-rs/api/cliente/


Resultado:
```
[
  {
    "cpf": "12345678900",
    "id": 1,
    "nome": "Kiko",
    "renda": 2000
  },
  {
    "cpf": "00000000001",
    "id": 2,
    "nome": "Chaves",
    "renda": 200
  },
  {
    "cpf": "12312312344",
    "id": 3,
    "nome": "Natarajan",
    "renda": 1000
  },
  {
    "cpf": "007980",
    "id": 4,
    "nome": "Natarajan",
    "renda": 1000
  }
]

```



##### Consultar Hotéis

GET http://localhost:8082/hospedagem-rs/api/hotel/
```
[
  {
    "id": 1,
    "nome": "Ibis Hotel JP"
  },
  {
    "id": 2,
    "nome": "Jardins Plaza Hotel"
  }
]
```

##### Criar reserva para Hotel
POST http://localhost:8082/hospedagem-rs/api/reserva/


Resultado
```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<reservaHotel>
    <id>1</id>
</reservaHotel>
```

##### Inserir hotel de uma reserva de Hotel
PUT http://localhost:8082/hospedagem-rs/api/reserva/1/hotel/1


Resultado
```
{
  "id": 1,
  "cliente": null,
  "hotel": {
    "rel": "hotel",
    "href": "http://localhost:8082/hospedagem-rs/api/hotel/1"
  }
}
```

##### Inserir cliente da reserva de Hotel
PUT http://localhost:8082/hospedagem-rs/api/reserva/1/cliente/1


Resultado
```
{
  "cliente": {
    "href": "http://localhost:8081/cliente-ws/api/cliente/1",
    "rel": "cliente"
  },
  "hotel": {
    "href": "http://localhost:8082/hospedagem-rs/api/hotel/1",
    "rel": "hotel"
  },
  "id": 1
}
```


##### Consultar Empresas de Passagem
GET http://localhost:8083/passagem-rs/api/empresa/1/
```
[
  {
    "id": 1,
    "nome": "Guanabara"
  },
  {
    "id": 2,
    "nome": "Gontijo"
  }
]
```

##### Criar reserva para passagem
POST http://localhost:8083/passagem-rs/api/reserva/


Resultado
```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<reservaPassagem>
    <id>1</id>
</reservaPassagem>
```

##### Informar a empresa de uma reserva de passagem
PUT http://localhost:8083/passagem-rs/api/reserva/1/empresa/1


Resultado
```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<reservaPassagem>
    <empresa>
        <id>1</id>
        <nome>Guanabara</nome>
    </empresa>
    <id>1</id>
</reservaPassagem>
```

##### Inserir cliente da reserva de Passagem
PUT http://localhost:8083/passagem-rs/api/reserva/1/cliente/1


Resultado
```
{
  "cliente": {
    "href": "http://localhost:8081/cliente-ws/api/cliente/1",
    "rel": "cliente"
  },
  "empresa": {
    "href": "http://localhost:8083/passagem-rs/api/empresa/1",
    "rel": "empresa"
  },
  "id": 1
}
```

##### Criar um pacote
POST http://localhost:8084/agencia-rs/api/pacote/


Resultado
```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<pacote>
    <id>7</id>
</pacote>
```

##### Inserir o cliente de um pacote
PUT http://localhost:8084/agencia-rs/api/pacote/7/cliente/2


Resultado
```
{
  "cliente": {
    "href": "http://localhost:8081/cliente-rs/api/cliente/2",
    "rel": "cliente"
  },
  "id": 7
}
```


##### Criar uma reserva de passagem no pacote

É necessário informar a empresa da reserva

PUT http://localhost:8084/agencia-rs/api/pacote/7/passagem/1


Resultado
```
{
  "cliente": {
    "href": "http://localhost:8081/cliente-rs/api/cliente/2",
    "rel": "cliente"
  },
  "id": 7,
  "reservaPassagem": {
    "href": "http://localhost:8083/passagem-rs/api/reserva/4",
    "rel": "reservaHotel"
  }
}
```

##### Criar uma reserva de hotel no pacote

É necessário informar o hotel

PUT http://localhost:8084/agencia-rs/api/pacote/7/hotel/2


Resultado
```
{
  "cliente": {
    "href": "http://localhost:8081/cliente-rs/api/cliente/2",
    "rel": "cliente"
  },
  "id": 7,
  "reservaHotel": {
    "href": "http://localhost:8082/hospedagem-rs/api/reserva/2",
    "rel": "reservaHotel"
  },
  "reservaPassagem": {
    "href": "http://localhost:8083/passagem-rs/api/reserva/4",
    "rel": "reservaHotel"
  }
}
```
