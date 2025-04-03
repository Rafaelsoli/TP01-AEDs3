//////////////////////////////////////////////////
// Pacote

package sistema.episodios;

//////////////////////////////////////////////////
// Bibliotecas

import java.time.LocalDate; // Para o <lancamento>
import       aed3.Registro; // Para o modelo de entidade <registro>

//////////////////////////////////////////////////
// Implementação dos episódios em si

public class Episodio implements Registro 
{
    // VARIÁVEIS
    private int                id;
    private String           nome;
    private int            numero;
    private short         duracao; // Em minutos
    private int           idSerie;
    private String        sinopse;
    private short       temporada; 
    private LocalDate  lancamento;  

    // CONSTRUTORES
    public Episodio 
    (
        int               id,
        String          nome,
        int           numero,
        short        duracao,
        int          idSerie,
        String       sinopse,
        short      temporada,
        LocalDate lancamento
    )
    {
        this.id         =         id;
        this.nome       =       nome;
        this.numero     =     numero;
        this.duracao    =    duracao;
        this.idSerie    =    idSerie;
        this.sinopse    =    sinopse;
        this.temporada  =  temporada;
        this.lancamento = lancamento;
    }

    public Episodio
    (
        String          nome,
        int           numero,
        short        duracao,
        int          idSerie,
        String       sinopse,
        short      temporada,
        LocalDate lancamento
    )
    {
        this 
        (
            -1, nome, numero, duracao, idSerie, sinopse, temporada, lancamento
        );
    }

    public Episodio ()
    {
        this 
        (
            "", -1, (short) 0, -1, "", (short) -1, LocalDate.now ()
        );
    }

    // GETTERS
    public int       getId         () {return         id;}
    public String    getNome       () {return       nome;}
    public int       getNumero     () {return     numero;}
    public short     getDuracao    () {return    duracao;}
    public int       getIdSerie    () {return    idSerie;}
    public String    getSinopse    () {return    sinopse;}
    public short     getTemporada  () {return  temporada;}
    public LocalDate getLancamento () {return lancamento;}

    // SETTERS
    public void setId         (int               id) {this.id         =         id;}
    public void setNome       (String          nome) {this.nome       =       nome;}
    public void setNumero     (int           numero) {this.numero     =     numero;}
    public void setDuracao    (short        duracao) {this.duracao    =    duracao;}
    public void setIdSerie    (int          idSerie) {this.idSerie    =    idSerie;}
    public void setSinopse    (String       sinopse) {this.sinopse    =    sinopse;}
    public void setTemporada  (short      temporada) {this.temporada  =  temporada;}
    public void setLancamento (LocalDate lancamento) {this.lancamento = lancamento;}

    // TO BYTE ARRAY
    public byte [] toByteArray ()
    {
        return new byte [0];
    }

    // FROM BYTE ARRAY
    public void fromByteArray (byte [] b)
    {

    }

    // TO STRING
    public String toString ()
    {
        return "| > \""         + nome   + "\""   +                        "\n" +
               "| Episódio "    + numero + " da " + temporada +  " temporada\n" +
               "| "             + sinopse         +                        "\n" +
               "| Lançado em: "                   + lancamento.toString ()      + " - ID: "     + id + "@" + idSerie;
    }
}

//////////////////////////////////////////////////