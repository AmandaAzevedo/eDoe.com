# eDoe.com

Muitas pessoas tem interesse em fazer doações, mas as vezes não tem o tempo necessário para encontrar onde doar ou como doar. Como sabemos, vivemos em um país em que a desigualdade social é muito alta e por isso há muitas pessoas necessitadas, ainda mais agora em plena pandemia… De um lado pessoas que querem fazer algo para colaborar nesse momento tão delicado e por outro pessoas que realmente estão precisando de um apoio. Precisamos de um sistema para dar suporte a essa rede de doações: o eDoe.com.

No eDoe.com usuários de todo o campus IV da UFPB podem cadastrar itens a serem doados. Usuários da região ou do próprio campus devem poder cadastrar itens que gostariam de receber como doação (necessidades) e o sistema deve saber casar doações com necessidades para facilitar a doação. Uma vez fechada uma doação, o sistema ajuda a organizar o encontro de doador/receptor para a finalização.


#### Para acessar a API acesse  [https://edoe.herokuapp.com](https://edoe.herokuapp.com/)
#### Para acessar a documentação da API acesse [https://edoe.herokuapp.com/swagger-ui.html](https://edoe.herokuapp.com/swagger-ui.html)
#### Para acessar o vídeo de demonstração da API acesse [https://youtu.be/]()


## Foi usado no projeto:
* Java 11
* Spring Boot
* JPA
* Hibernate
* PostgreSQL
* H2 Database
* JWT
* Flyway
* Lombok
* Swagger
* Heroku

## Acesso de usuários já criados

* Usuário admin
```
login: admin 
senha: aVfd7$muQioUm
```

* Usuário doador
```
login: doador 
senha: Bd9Oyac&^d^Sokr
```

* Usuário receptor
```
login: receptor 
senha: reszZcJbpOtEo#r4
```

* Usuário doador e receptor
```
login: doador_receptor
senha: d&oneafLorPsre8c8p54ftffover
```

## Tempo de expiração do Token

O tempo de expiração do token é definido em `eDoe/api/v1/jwt/JwtService.java` como sendo 604.800.000 milisegundos, ou 1 semana.
O tempo de expiração foi definido como sendo 1 semana, por não ser um espaço de tempo muito grande, de forma que abra brechas de vulnerabilidade,
mas também não é um espaço de tempo muito pequeno, de forma que o usuário não tenha que realizar login a todo instante.

## Permissões
Ao se cadastrar um usuário possui a permissão de apenas **doador (ONLY_DONATOR)**, mas também exitem as permissões de: 

* Administrador (ADMIN).
* Apenas receptor (ONLY_RECLIVE). 
* Doador-receptor (DONATOR_RECLIVE).

Para que a sua permissão seja alterada o usuário deve solicitar ao administrador a mude. Essa solicitação pode ser feita através do endpoint 
**/api/v1/request/change-role**.


## Descritores
No sistema eDoe, um descritor é uma palavra que descreve um item. 

O sistema mantém um conjunto de descritores de itens genéricos para doação, um exemplo de descritor de item pode ser: "cadeira de rodas", "curso de programação", ou "cobertor".

Existe alguns descritores que foram previamente cadastrados na API, através da migração `src/main/resources/db/migration/data/V20211118164543__create_desriptors.sql`, são eles: `curso de computacao`, `roupa de frio`, `cobertor`, e `cadeira de rodas`.

Os nomes dos descritores não devem conter caractérs especiais, espaços no inicio ou fim, e devem possuir letras minúsculas.

Os endpoints relacionados a descritores são: [`/api/v1/descriptors`](README.md#/api/v1/descriptors) e [`/api/v1/descriptors/create`](README.md#/api/v1/descriptors/create).

## Tipos de item
Um item compreende a um objeto real. O item pode ser de do tipo `item necessário`, que são os itens que os usuários necessitam, e `item doado`, que são itens que os usuários não querem mais e desejam doar.

## Matches
Um match entre itens no eDoe consiste em encontrar itens para doação que possuam o mesmo descritor(es) do item necessário informado. 

O endpoint relacionado a funcionalidade de match é [`/api/v1/matches`](README.md#/api/v1/matches).

## Funcionalidades por end-point

### /api/v1/user/register
Esse endpoint é responsável pelo cadastro de usuários. Para realizar o cadastro deve-se fazer uma requisição utilizando o método POST, informar um email, 
nome da pessoa, ou organização a ser cadastrada, senha, telefone, e categoria do usuário, que pode ser dos tipos: 

* PRIVATE_INDIVIDUAL (pessoa física), 
* CHURCH (igreja), 
* MUNICIPAL_PUBLIC_AGENCY (agência pública municipal), 
* STATE_PUBLIC_AGENCY (agência pública estadual), 
* FERERAL_PUBLIC_AGENCY (agência pública federal), 
* NGO (ONG), 
* ASSOCIATION_OR_SOCIETY (associação ou sociedade).

É utilizado o seguinte JSON para ser realizado o cadastro, substitua os valores abaixo: 

```json
{
  "email": "string",
  "name": "string",
  "password": "string",
  "telephone": "string",
  "userCategory": "PRIVATE_INDIVIDUAL"
}
```


### /api/v1/auth/login
Para obter o token de login, deve-se utilizar o método POST para o endpoint /api/v1/auth/login, informando o login e a senha.
O retorno desse método será um token válido, com prazo de validade de 1 semana.
```json
{
  "email": "string",
  "password": "string"
}
```

### /api/v1/request/change-role
Para solicitar a mudança de permissão, deve-se realizar uma requisição utilizando o método POST para o endpoint /api/v1/request/change-role. Deve-se informar a permissão desejada em _requestedRole_ e o email do usuário que está requerindo esta solicitação em _requestingUserEmail_. Ver mais sobre as permissões existentes no [eDoe](README.md#Permissões).
```json
{
  "requestedRole": "ADMIN",
  "requestingUserEmail": "string"
}
```

### /api/v1/request/{email}
End-point que utiliza o método GET para retornar informações sobre as requisições realizadas por um dado email. O e-mail pesquisado deve ser informado no caminho para o end-point, substituindo _{email}_. Este método só pode ser executados por usuários do tipo ADMIN ou pelo usuário que possui o email pesquisado.

### /api/v1/request/
End-point que utiliza o método GET para retornar informações sobre as requisições realizadas. Ela retorta uma lista com todas as requições feitas no sistema, e só pode ser acessada por um usuário do tipo ADMIN.

### /api/v1/request/evaluate
End-poit para que os usuários do tipo ADMIN aceitem ou recusem a solicitação para que mudem de permissão. O usuário ADMIN deve mandar uma requisição PATCH com os valores de `true`(aceito) ou `false`(negado) para o campo `accept` e o ID da requisição avaliada no campo `id`.
```json
{
  "accept": true,
  "id": 0
}
```

### /api/v1/descriptors/create
End-point utilizado para criar descritores. Deve ser enviada uma requisição POST para o end-point, fornescido o nome do descritor a ser cadastrado `name` e um token válido.

```json
{
  "name": "string"
}
```

### /api/v1/descriptors/
End-point que lista os descritores cadastrados. Deve ser enviada uma requisição GET para o end-point, com um token válido, e um JSON com o campo `optionsToGetTheDescriptorEnum` preenchido com as opções: `ASC`, para listar os descritores em uma ordem ascendente e `DESC` descrescente (ordem alfabética).

```json
{
  "optionsToGetTheDescriptorEnum": "ASC"
}
```

### /api/v1/item/create
End-point que cria um item. Ver mais sobre [tipos de item](README.md#tipos-de-item). Um item pode ser dos tipos doador e receptor. 

Para criar um item necessário deve ser enviada uma requisição do tipo POST, informando os campos do JSON abaixo, além de um token válido. Apenas usuários que tenham permissão de receptor poderá criar um item necessário.
O campo `descriptionOrJustification` deve conter a justificação do item necessário. Já o campo `descriptors` deve conter uma lista de nomes de descritores, caso o nome do descritor informado não exista, o sistema criará um descritor com o nome informado.  
O tipo de item em `itemType` para criar um item necessário deve ser do tipo `GRANTEE`. O campo `quantity` deve conter um número maior do que zero, de números de itens necessários.
Por fim, o campo `userEmail` deve conter o e-mail do usuário que está cadastrando o item necessário.
```json
{
  "descriptionOrJustification": "string",
  "descriptors": [
    "string",
    "string2"
  ],
  "itemType": "DONATION",
  "quantity": 1,
  "userEmail": "string"
}
```

Já para criar um item a ser doado, o usuário deve informar os mesmos campos, com a diferença de que o campo `descriptionOrJustification` deverá conter a descrição do item a ser doado,
o campo `itemType` deve ser do tipo `DONATION`, e só poderá executar esta funcionalidade usuários que tenham permissão de doador.

### /api/v1/item/donation/delete/{id}
End-point para deletar um item a ser doado. No lugar de {id} deve-se informar o id do item, além disso, deve-se informar um token válido. Apenas o dono do item pode deletá-lo

### /api/v1/item/grantee/delete/{id}
End-point para deletar um item necessário. No lugar de {id} deve-se informar o id do item, além disso, deve-se informar um token válido. Apenas o dono do item pode deletá-lo

### /api/v1/item/donation/listByDescriptorId/{id}
End-point GET para listar os itens a serem doados que possuiem determinado descritor. O id do descritor deve ser informado no lugar de {id}.

### /api/v1/item/grantee/listByDescriptorId/{id}
End-point GET para listar os itens a necessários que possuiem determinado descritor. O id do descritor deve ser informado no lugar de {id}.

### /api/v1/item/donation/listByDescriptorName/

```json
{
  "name": "string"
}
```

### /api/v1/item/grantee/listByDescriptorName/

```json
{
  "name": "string"
}
```

### /api/v1/item/donation/top10
End-point GET para listar os descritores que mais possuem itens cadastrados para doação relacionados disponíveis. 


### /api/v1/item/grantee/top10
End-point GET para listar os descritores que mais possuem itens necessários cadastrados relacionados.


### /api/v1/item/donation/update/description/{id}
End-point PATCH para editar a descrição do item a ser doado. O id do item deve ser informado no lugar de {id}, além disso deve ser informado o token do usuário relacionado.
Também deve ser enviado um JSON com a nova descrição.

```json
{
  "newDescriptionOrJustification": "string"
}
```

### /api/v1/item/grantee/update/justification/{id}
End-point PATCH para editar a justificativa do item necessário. O id do item deve ser informado no lugar de {id}, além disso deve ser informado o token do usuário relacionado.
Também deve ser enviado um JSON com a nova justificativa. 

```json
{
  "newDescriptionOrJustification": "string"
}
```

### /api/v1/item/donation/update/quantity/{id}
End-point PATCH para editar a quantidade de itens para doação. O id do item deve ser informado no lugar de {id}, além disso deve ser informado o token do usuário relacionado.
Também deve ser enviado um JSON com a nova quantidade de itens.

```json
{
  "newQuantity": 1
}
```

### /api/v1/item/grantee/update/quantity/{id}
End-point PATCH para editar a quantidade de itens necessários. O id do item deve ser informado no lugar de {id}, além disso deve ser informado o token do usuário relacionado.
Também deve ser enviado um JSON com a nova quantidade de itens.


```json
{
  "newQuantity": 1
}
```

### /api/v1/matches
End-point GET para listar os matches de um determinado item necessário. Ver mais sobre [matches](README.md#matches). Para essa funcionalidade deve ser informado o token do usuário relacionado e deve ser enviado um JSON com o id do item que vai ser buscado o match. Veja o exemplo do JSON abaixo.
```json
{
  "itemId": 0
}
```

### /api/v1/donation/create
End-point PATCH para realizar uma doação. Apenas usuários com a permissão de receptor poderá realizar a doação. 
Para essa funcionalidade deve-se enviar o token do usuário e um JSON com a quantidades de itens que se quer receber a doação, o ID do item que está para doação e o ID do item necessário.

```json
{
  "amountToBeDonated": 0,
  "donatedItemId": 0,
  "requiredItemId": 0
}
```

### /api/v1/donation
End-point GET para listar todas as doações feitas. 

## Observações
* Usuários com a permissão de ADMIN poderão realizar todas as funcionalidades.
* O arquivo testes_insomnia.json contem todas os arquivos .json para realizar testes com os endpoints. Além disso, os testes poderam ser feitos no _/swagger-ui.html_.