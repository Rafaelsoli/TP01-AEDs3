//////////////////////////////////////////////////
// Pacote

package sistema.episodios;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

//////////////////////////////////////////////////
// Bibliotecas

import java.time.LocalDate; // Para o <lancamento>
import       aed3.Registro; // Para o modelo de entidade <registro>

import    sistema.Auxiliar; // Para LerString & FormatarString

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

    private static final int TAM_NOME      = 128;
    private static final int TAM_SINOPSE   = 200;

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

    public Episodio (byte [] ba) throws IOException
    {
        this ();

        fromByteArray (ba);
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
    @Override public byte [] toByteArray () throws IOException 
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream (    );
        DataOutputStream      dos  = new DataOutputStream      (baos);

        dos.writeInt   (id);
        dos.writeBytes (Auxiliar.formatarString(nome, TAM_NOME));
        dos.writeInt   (numero);
        dos.writeShort (duracao);
        dos.writeInt   (idSerie);
        dos.writeBytes (Auxiliar.formatarString(sinopse, TAM_SINOPSE));
        dos.writeShort (temporada);
        dos.writeLong  (this.lancamento.toEpochDay ( ));

        return baos.toByteArray();
    }

    // FROM BYTE ARRAY
    @Override public void fromByteArray (byte [] b) throws IOException
    {
        ByteArrayInputStream bais = new ByteArrayInputStream (b   );
        DataInputStream      dis  = new DataInputStream      (bais);

        id         = dis.readInt          (                );
        nome       = Auxiliar.lerString   (dis,    TAM_NOME);
        numero     = dis.readInt          (                );
        duracao    = dis.readShort        (                );
        idSerie    = dis.readInt          (                );
        sinopse    = Auxiliar.lerString   (dis, TAM_SINOPSE);
        temporada  = dis.readShort        (                );
        lancamento = LocalDate.ofEpochDay (dis.readLong ( ));
    }

    // TO STRING
    @Override public String toString ()
    {
        return "| > \""         + nome   + "\" Duração: "  + duracao/60 + ":" + ((duracao%60 < 10)?"0":"") + duracao%60 + "\n" +
               "| Episódio "    + numero + " da " + temporada  +   " temporada\n" +
               "| "             + sinopse         +                                 "\n" +
               "| Lançado em: "                   + lancamento.toString ()        + " - ID: "     + id + "@" + idSerie;
    }
}

//////////////////////////////////////////////////