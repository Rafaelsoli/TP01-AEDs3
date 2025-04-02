# Diagrama de Classes - TP01-AEDs3

## Estrutura do Sistema
O sistema deve gerenciar um serviço de streaming, organizando séries e seus episódios de maneira hierárquica.

## Diagrama de Classes

### **Streaming**
```
+----------------+
| Streaming      |
+----------------+
| - id: int      |
| - nome: String |
+----------------+
| + cadastrar()  |
| + buscar()     |
| + atualizar()  |
| + excluir()    |
+----------------+
```
### **Série**
```
+-------------------------+
| Série                   |
+-------------------------+
| - id: int               |
| - nome: String          |
| - lancamento: localDate |
| - Sinopse: String       |
| - ageRating: enumIDADE  |
| - Streaming: String     |
+-------------------------+
| + cadastrar()           |
| + buscar()              |
| + atualizar()           |
| + excluir()             |
+-------------------------+

<!--

```
### **Temporada**
```
+----------------+
| Temporada      |
+----------------+
| - id: int      |
| - numero: int  |
| - idSerie: int |
+----------------+
| + cadastrar()  |
| + buscar()     |
| + atualizar()  |
| + excluir()    |
+----------------+
```

-->

### **Episódio**
```
+--------------------+
| Episódio           |
+--------------------+
| - id: int          |
| - titulo: String   |
| - numero: int      |
| - idTemporada: int |
+--------------------+
| + cadastrar()      |
| + buscar()         |
| + atualizar()      |
| + excluir()        |
+--------------------+  
```
## Relacionamentos
```
Streaming 1 ----- N Série
Série 1 --------- N Temporada
Temporada 1 ----- N Episódio
```

## Regras de Negócio
1. Uma **série** só pode ser removida se não houver episódios vinculados.
2. Um **usuário** só pode gerenciar episódios de uma **série** previamente selecionada.
3. Na visão de **séries**, os episódios devem ser exibidos organizados por **temporada**.