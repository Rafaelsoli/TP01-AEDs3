+-----------------+
|   Streaming     |
+-----------------+
| - id: int       |
| - nome: String  |
+-----------------+
| + getSeries()   |
+-----------------+
       |
       | 1
       v N
+-----------------+
|     Serie      |
+-----------------+
| - id: int       |
| - nome: String  |
| - idStreaming: int |
+-----------------+
| + getTemporadas() |
+-----------------+
       |
       | 1
       v N
+-----------------+
|   Temporada    |
+-----------------+
| - id: int       |
| - numero: int   |
| - idSerie: int  |
+-----------------+
| + getEpisodios() |
+-----------------+
       |
       | 1
       v N
+-----------------+
|   Episodio     |
+-----------------+
| - id: int       |
| - titulo: String |
| - idTemporada: int |
| - idSerie: int |
+-----------------+

+-----------------------------------+
|       ArquivoSerie               |
+-----------------------------------+
| - listaSeries: List<Serie>        |
+-----------------------------------+
| + incluir(Serie s)               |
| + excluir(int id)                |
| + buscar(int id): Serie          |
| + listar(): List<Serie>          |
+-----------------------------------+

+-----------------------------------+
|       ArquivoEpisodio             |
+-----------------------------------+
| - listaEpisodios: List<Episodio>  |
+-----------------------------------+
| + incluir(Episodio e)            |
| + excluir(int id)                |
| + buscar(int id): Episodio        |
| + listar(): List<Episodio>        |
+-----------------------------------+

+-----------------------------------+
|       ArvoreBMais                 |
+-----------------------------------+
| - indice: Map<int, List<int>>     |
+-----------------------------------+
| + inserir(idSerie, idEpisodio)    |
| + buscar(idSerie): List<int>      |
| + remover(idSerie, idEpisodio)    |
+-----------------------------------+
