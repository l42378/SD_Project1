Sistemas Distribuídos
1º trabalho
2026/2027
# Sistema Distribuído de Gestão

## 1. Objetivo

Desenvolver uma aplicação distribuída para simular um sistema de gestão num domínio à
escolha dos alunos. O sistema deverá permitir o registo de utilizadores e de elementos
geridos, a gestão de estados, e realizar operações e aprovações remotamente. A aplicação
deverá incluir um servidor e dois clientes: um geral (para consultas e operações básicas) e
um administrador (para gestão e aprovações), acessíveis remotamente para verificar e
atualizar as informações do sistema.

Os alunos deverão identificar e propor um domínio de aplicação relevante e interessante,
demonstrando criatividade na escolha do tema e na modelação do sistema.

---

## 2. Descrição da Tarefa
### Escolha do Domínio
Os alunos escolhem livremente o domínio de aplicação, que deve corresponder a um caso
de uso real: um problema concreto, com utilizadores identificáveis, que o sistema resolve. O
domínio deve também permitir demonstrar os conceitos de sistemas distribuídos. Para que
todos os trabalhos tenham uma dimensão comparável, o sistema deve respeitar os
seguintes limites. O que exceder o máximo não é valorizado.

|Requisito|Mínimo|Máximo|
|---------|------|------|
|Tipos de elemento gerido|1|2|
|Atributos por elemento gerido|4|8|
|Estados operacionais por elemento gerido|3|5|
|Tabelas na base de dados|4|7|
|Funcionalidades próprias (cliente geral)|2|3|
|Funcionalidades próprias (cliente administrador)|1|2|

### Requisitos Mínimos do Sistema
Independentemente do domínio escolhido, o sistema deverá implementar as seguintes
características:
#### Conceitos
No resto do enunciado, os termos seguintes são usados sempre com este significado:
* Utilizador: quem usa o sistema através do cliente geral. Os utilizadores não
necessitam de aprovação.
* Elemento gerido: aquilo que o sistema gere. Tem atributos, estados operacionais e
um estado administrativo (aprovado / não aprovado). O sistema pode ter um ou dois
tipos de elemento gerido.
* Operação principal: a ação que liga um utilizador a um ou mais elementos geridos
durante um período, sujeita a regras de negócio.
#### Modelo de Dados
O sistema deve ter utilizadores e um a dois tipos de elemento gerido, relacionados entre si
através da operação principal.

Cada tipo de elemento gerido deve ter:
* Identificador único
* Entre 4 e 8 atributos relevantes para o domínio
* Entre 3 e 5 estados operacionais, com as transições permitidas definidas pelos
alunos (máquina de estados); o sistema deve rejeitar transições inválidas
* Um estado administrativo (aprovado / não aprovado), independente do estado
operacional
#### Base de Dados
* Utilização de PostgreSQL para persistência de dados
* Entre 4 e 7 tabelas relacionadas, incluindo uma tabela de histórico das operações e
das alterações de estado
* Implementação adequada de chaves primárias e estrangeiras
#### Operação Principal
O sistema deve ter uma operação principal, definida pelos alunos, que:
* relacione um utilizador com pelo menos um elemento gerido;
* tenha uma dimensão temporal, com data e hora de início e de fim;
* altere o estado operacional de pelo menos um elemento gerido;
* esteja sujeita a pelo menos uma regra de negócio que dependa do estado atual do
sistema (ex.: limite, exclusividade, pré-condição de estado);
* fique registada no histórico.

O servidor deve validar cada pedido e rejeitá-lo, com uma mensagem clara, em todos os
casos seguintes:
* utilizador ou elemento gerido inexistente;
* elemento gerido não aprovado, ou num estado operacional que não permite a
operação;
* datas mal formadas, data de fim anterior ou igual à de início, ou período já decorrido;
* conflito com outras operações no mesmo período, quando a regra de negócio o
impeça;
* violação de qualquer outra regra de negócio definida pelos alunos;
* pedidos simultâneos de clientes diferentes que, em conjunto, violem uma das regras
anteriores.

O relatório deve descrever a regra de negócio, a máquina de estados e a forma como cada
um destes casos é tratado.

### Funcionalidades
**Cliente Geral**

O cliente geral deve implementar as 6 funcionalidades obrigatórias seguintes e entre 2 e 3
funcionalidades próprias, definidas pelos alunos:
* **Registo de elemento gerido:** adicionar um novo elemento gerido ao sistema. O
elemento fica no estado administrativo "não aprovado" até ser aprovado pelo
administrador.
* **Registo de utilizadores:** registar utilizadores com os dados relevantes para o
domínio. Os utilizadores não necessitam de aprovação.
* **Registo da operação principal:** executar a operação principal, indicando o
utilizador, o(s) elemento(s) gerido(s) e as datas, com as validações descritas em
Operação Principal.
* **Listagem com filtros:** listar os elementos geridos aprovados com pelo menos 2
critérios de filtragem diferentes.
* **Consulta de estado atual:** mostrar o estado operacional e o estado administrativo
atuais de um elemento gerido.
* **Consulta de histórico:** visualizar o histórico completo de um elemento gerido,
incluindo todas as operações e alterações de estado.

**Cliente Administrador**

O cliente administrador deve implementar as 4 funcionalidades obrigatórias seguintes e
entre 1 e 2 funcionalidades próprias, definidas pelos alunos:
* **Listagem por estado administrativo:** listar elementos geridos por estado
administrativo (aprovado / não aprovado).
* **Aprovação de elementos geridos:** aprovar ou rejeitar elementos geridos não
aprovados. Só os elementos aprovados ficam disponíveis no cliente geral.
* **Edição de elementos geridos:** modificar os valores dos atributos de um elemento
gerido existente.
* **Alteração de estados:** modificar o estado operacional de um elemento gerido,
respeitando as transições permitidas. Deve estar definido o que acontece às
operações já registadas que sejam afetadas pela alteração.

## 3. Implementação
**Requisitos Técnicos:**
* **Linguagem:** Java.
* **Comunicação:** um dos clientes comunica com o servidor por sockets TCP com
serialização de objetos; o outro comunica por Java RMI. A escolha de qual cliente
usa cada tecnologia é livre e deve ser justificada no relatório.
* **Servidor:** deve servir vários clientes em simultâneo, com recurso a threads.
* **Controlo de concorrência:** operações simultâneas de clientes diferentes não
podem violar as regras de negócio nem deixar os dados inconsistentes (ex.:
sincronização no servidor e transações na base de dados).
* **Tratamento de falhas:** os clientes devem tratar a indisponibilidade do servidor ou da
base de dados com mensagens claras, sem terminar abruptamente.
* **Bibliotecas:** não é permitido usar frameworks externos (ex.: Spring, Hibernate,
bibliotecas REST). A única biblioteca externa é o driver JDBC de PostgreSQL,
incluído no projeto como JAR.
* A interface será em linha de comandos, com menus para navegação das opções.
* Não é necessário implementar autenticação de utilizador nesta fase.
* **Funcionalidades próprias:** as funcionalidades próprias de cada cliente devem
acrescentar valor ao domínio ou tratar aspetos de sistemas distribuídos (ex.:
recuperação de falhas de ligação, notificações). Funcionalidades além do máximo
indicado não são valorizadas.

**Configuração:**
* Parâmetros de configuração devem ser externos ao código-fonte, utilizando
argumentos de linha de comando ou ficheiros de propriedades (ex.:
java.util.Properties).
* A solução deve utilizar uma base de dados PostgreSQL para armazenar todas as
informações do sistema, acedida por JDBC.

## 4. Entrega
* Enviar um ficheiro .zip no Moodle, contendo a pasta sd-t01-YYYYY-ZZZZZ (substituir
YYYYY e ZZZZZ pelos números de aluno)
* A pasta deve conter o código-fonte, ficheiros de configuração, o script de execução
das aplicações (makefile).
* Um relatório deve estar incluído com:
	* Identificação dos alunos.
	* **Justificação das escolhas feitas ao longo do trabalho** (ex.: estrutura das
tabelas na base de dados e métodos de comunicação entre cliente e
servidor)
	* Instruções detalhadas para a configuração da base de dados PostgreSQL,
incluindo os passos necessários para criar a base de dados, tabelas,
utilizadores e permissões necessárias para executar o sistema.
	* Observações sobre o desenvolvimento e eventuais desafios encontrados.

**Nota:** O trabalho deve ser realizado em grupos de 2 alunos. A entrega individual só é
possível com aprovação prévia do docente.

**Demonstração:** o trabalho será objeto de uma apresentação e demonstração. Cada aluno
do grupo deve conseguir explicar todo o código, e não apenas demonstrar o funcionamento
da aplicação. Se não conseguir defender a implementação, terá uma classificação abaixo
da nota mínima..
