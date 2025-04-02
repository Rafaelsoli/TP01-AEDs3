# Trabalho Prático: Implementação de Relacionamento 1:N

## Objetivo

O primeiro trabalho prático da nossa disciplina é a implementação de um relacionamento **1:N** entre duas entidades. Para isso, você precisará partir do **CRUD genérico** que trabalhamos em sala de aula.

### Entidade: Série

Neste projeto, o objetivo é criar um CRUD para **séries de streaming**. O sistema deve ser capaz de gerenciar a inclusão, alteração, exclusão e consulta de séries. Cada série deve possuir, pelo menos, os seguintes dados:

- **Nome**
- **Ano de lançamento**
- **Sinopse**
- **Streaming** (Netflix, Prime, Max, Disney, etc.)

Você pode adicionar mais atributos que achar necessários, mas lembre-se de que toda entidade precisa de um identificador único (ID), que será um número sequencial inteiro. Note que esse tipo de entidade não tem um atributo exclusivo, como CPF ou ISBN.

### Entidade: Episódio

Cada **série** possui vários **episódios** e cada episódio pertence a uma única série. Portanto, o relacionamento é de **1:N (um para muitos)**:

- **1 série** tem **N episódios**
- **1 episódio** pertence a **1 única série**

Cada episódio deve ter, pelo menos, os seguintes atributos (além do ID):

- **Nome**
- **Temporada**
- **Data de lançamento**
- **Duração** (em minutos)

Você pode criar mais atributos, como uma sinopse própria do episódio. Lembre-se de que, para associar cada episódio a uma série, será necessário incluir uma **chave estrangeira** (ID da série) na entidade episódio.

---

## Código Base

Este projeto deve ser desenvolvido com base no **CRUD genérico** que já foi trabalhado na disciplina. O CRUD deve criar registros com a seguinte estrutura:

- **Lápide**: Byte que indica se o registro é válido ou se foi excluído.
- **Indicador de tamanho do registro**: Número inteiro (short) que indica o tamanho do vetor de bytes.
- **Vetor de bytes**: Bytes que descrevem a entidade (obtido através do método `toByteArray()` do próprio objeto).

Além disso, você deverá usar as classes **TabelaHashExtensível** e **Árvore B+** para criar os índices. Não será permitido criar novas estruturas de dados para os índices no projeto.

---

## Programa Principal

O programa principal deve oferecer uma interface para o usuário, onde ele possa realizar as operações de inclusão, alteração, busca e exclusão para todas as entidades. A interface inicial deve ser parecida com a seguinte:

```
PUCFlix 1.0
-----------
> Início

1) Séries
2) Episódios
3) Atores
0) Sair
```


Para cada entidade, a interface deve ser semelhante a esta:

```
PUCFlix 1.0
-----------
> Início > Séries

1) Incluir
2) Buscar
3) Alterar
4) Excluir
0) Retornar ao menu anterior
```


Atenção: Não deve haver código de interface com o usuário (visão) dentro da classe de acesso aos dados (modelo). Devemos seguir o padrão **MVC** (Modelo-Visão-Controlador). Assim, crie uma classe **VisaoSeries**, que será responsável por todas as operações de entrada e saída de dados relacionadas a séries (não inclui o menu acima). Por exemplo, você deve ter pelo menos uma função `leSerie()` e outra `mostraSerie()`. 

Uma outra classe será responsável pela lógica das operações, chamada **ControleSeries**, que gerenciará o menu e a lógica das operações de inclusão, alteração e exclusão. Essa classe acessará os arquivos necessários e chamará as funções da visão.

---

## O que Deve Ser Feito?

1. **Implementar o CRUD de Séries.**
2. **Implementar o CRUD de Episódios**, garantindo que cada episódio pertença a uma série específica.
3. **Implementar o relacionamento 1:N** entre séries e episódios utilizando a **Árvore B+**.
4. Criar a visão e o controle de **séries**, garantindo que uma série não possa ser excluída se houver episódios vinculados a ela.
5. Criar a visão e o controle de **episódios**, permitindo que o usuário escolha a série à qual os episódios pertencem.
6. Na visão de séries, permitir a exibição dos episódios por temporada.

---

## Forma de Entrega

Você deve publicar o seu trabalho no **GitHub** e enviar o **URL** do seu projeto. Crie um repositório específico para este projeto (evite enviar o repositório pessoal de qualquer um do grupo). Adicione um arquivo `readme.md` ao repositório com um relatório explicando o que foi feito.

### O conteúdo do `readme.md` deve conter:

1. **Descrição do trabalho**: Explique o que o trabalho faz.
2. **Participantes**: Liste os nomes dos membros do grupo.
3. **Classes criadas**: Descreva as classes e seus métodos principais.
4. **Experiência de desenvolvimento**: Relate sua experiência, desafios e como os resultados foram alcançados.
5. **Checklist**: Responda as perguntas abaixo.

---

## Checklist

Responda sim/não às seguintes perguntas no seu relatório:

1. As operações de inclusão, busca, alteração e exclusão de séries estão implementadas e funcionando corretamente?
2. As operações de inclusão, busca, alteração e exclusão de episódios, por série, estão implementadas e funcionando corretamente?
3. Essas operações utilizam a classe **CRUD genérica** para a construção do arquivo e as classes **Tabela Hash Extensível** e **Árvore B+** como índices diretos e indiretos?
4. O atributo de **ID de série** foi criado na classe de episódios como chave estrangeira?
5. Há uma **Árvore B+** que registra o relacionamento 1:N entre episódios e séries?
6. A visualização das séries mostra os episódios por temporada?
7. A remoção de séries verifica se há episódios vinculados a ela?
8. A inclusão de episódios limita a escolha de séries às que já existem?
9. O trabalho está funcionando corretamente?
10. O trabalho está completo?
11. O trabalho é original e não é uma cópia de outro grupo?

---

## Distribuição de Pontos

Este trabalho vale **5 pontos**. A rubrica de avaliação estabelece os critérios que serão usados na correção.

**Atenção:** O projeto é específico por grupo. TPs copiados de outros grupos, que não evidenciem esforço mínimo do próprio grupo, serão anulados.

---

## Dúvidas

Se você tiver alguma dúvida sobre o trabalho, não hesite em perguntar. Lembre-se de que o URL do seu código no GitHub deve ser entregue até a data especificada na atividade.
