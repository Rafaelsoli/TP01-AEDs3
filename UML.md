|   Streaming     |
|-----------------|
| - id: int       |
| - nome: String  |
|-----------------|
| + getSeries()   |
       I
       I 1
       V N
|     Serie      |
|-----------------|
| - id: int       |
| - nome: String  |
| - idStreaming: int |
|-----------------|
| + getTemporadas() |
       I
       I 1
       V N
|   Temporada    |
|-----------------|
| - id: int       |
| - numero: int   |
| - idSerie: int  |
|-----------------|
| + getEpisodios() |
       I
       I 1
       V N
|   Episodio     |
|-----------------|
| - id: int       |
| - titulo: String |
| - idTemporada: int |
| - idSerie: int |

<hr style="width: 100%; border: 1px solid white; background-color: white; margin: 20px 0;">

|       ArquivoSerie               |
|-----------------------------------|
| - listaSeries: List<Serie>        |
|-----------------------------------|
| + incluir(Serie s)               |
| + excluir(int id)                |
| + buscar(int id): Serie          |
| + listar(): List<Serie>          |

|       ArquivoEpisodio             |
|-----------------------------------|
| - listaEpisodios: List<Episodio>  |
|-----------------------------------|
| + incluir(Episodio e)            |
| + excluir(int id)                |
| + buscar(int id): Episodio        |
| + listar(): List<Episodio>        |

|       ArvoreBMais                 |
|-----------------------------------|
| - indice: Map<int, List<int>>     |
|-----------------------------------|
| + inserir(idSerie, idEpisodio)    |
| + buscar(idSerie): List<int>      |
| + remover(idSerie, idEpisodio)    |
