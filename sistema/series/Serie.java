//////////////////////////////////////////////////
// Pacote

package sistema.series;

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
// Implementação da série em si

public class Serie implements Registro 
{
    // VARIÁVEIS
    private int                id;
    private String           nome;
    private String        sinopse;
    private short       ageRating; // Classificação indicativa
    private String      streaming;  
    private LocalDate  lancamento;

    // TAMANHOS FIXOS 
    private static final int TAM_STREAMING =  30;
    private static final int TAM_SINOPSE   = 200;
    private static final int TAM_NOME      =  60;

    // CONSTRUTORES
    public Serie 
    (
        int               id,
        String          nome,
        String       sinopse,
        short      ageRating,
        String     streaming,
        LocalDate lancamento
    )
    {
        this.id         =         id;
        this.nome       =       nome;
        this.sinopse    =    sinopse;
        this.ageRating  =  ageRating;
        this.streaming  =  streaming;
        this.lancamento = lancamento;
    }

    public Serie 
    (
        int               id,
        String          nome,
        String       sinopse,
        int        ageRating,
        String     streaming,
        LocalDate lancamento
    )
    {
        this 
        (
            -1, nome, sinopse, (short) ageRating, streaming, lancamento
        );
    }

    public Serie 
    (
        String          nome,
        String       sinopse,
        short      ageRating,
        String     streaming,
        LocalDate lancamento
    )
    {
        this 
        (
            -1, nome, sinopse, ageRating, streaming, lancamento
        );
    }

    public Serie 
    (
        String          nome,
        String       sinopse,
        int        ageRating,
        String     streaming,
        LocalDate lancamento
    )
    {
        this 
        (
            -1, nome, sinopse, ageRating, streaming, lancamento
        );
    }

    public Serie (byte [] ba) throws IOException
    {
        this ();

        fromByteArray (ba);
    }


    public Serie ()
    {
        this 
        (
            "", "", (short) 0, "", LocalDate.now ()
        );
    }

    // GETTERS
    public String getClassificacaoIndicativa () 
    {
        final String RESET    =            "\u001B[00m";
        final String PRETO    =  "\u001B[00m\u001B[37m";  // Fundo Preto e texto Branco
        final String VERMELHO =  "\u001B[41m\u001B[37m";  // Fundo Vermelho e texto Branco
        final String LARANJA  =  "\u001B[43m\u001B[37m";  // Fundo Laranja e texto Branco
        final String VERDE    =  "\u001B[42m\u001B[37m";  // Fundo Verde e texto Branco
        final String AZUL     =  "\u001B[44m\u001B[37m";  // Fundo Azul e texto Branco
        final String AMARELO  = "\u001B[103m\u001B[30m"; // Fundo Amarelo e texto Branco
        
        return (ageRating >= 18)? PRETO    + "[+18]" + RESET :
               (ageRating >= 16)? VERMELHO + "[+16]" + RESET :
               (ageRating >= 14)? LARANJA  + "[+14]" + RESET :
               (ageRating >= 12)? AMARELO  + "[+12]" + RESET :
               (ageRating >= 10)? AZUL     + "[+10]" + RESET :
                                  VERDE    + "[ L ]" + RESET ;
    }
    
    public int       getId         () {return         id;}
    public String    getNome       () {return       nome;}
    public String    getSinopse    () {return    sinopse;}
    public short     getAgeRating  () {return  ageRating;}
    public String    getStreaming  () {return  streaming;}
    public LocalDate getLancamento () {return lancamento;}

    // SETTERS
    public void setId         (int               id) {this.id         =         id;}
    public void setNome       (String          nome) {this.nome       =       nome;}
    public void setSinopse    (String       sinopse) {this.sinopse    =    sinopse;}
    public void setAgeRating  (short      ageRating) {this.ageRating  =  ageRating;}
    public void setStreaming  (String     streaming) {this.streaming  =  streaming;}
    public void setLancamento (LocalDate lancamento) {this.lancamento = lancamento;}

    // TO BYTE ARRAY
    @Override public byte [] toByteArray () throws IOException 
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream (    );
        DataOutputStream      dos  = new DataOutputStream      (baos);

        dos.writeInt   (                         this.id                       );
        dos.writeBytes (Auxiliar.formatarString (this.nome,           TAM_NOME));
        dos.writeBytes (Auxiliar.formatarString (this.sinopse  ,   TAM_SINOPSE));
        dos.writeShort (                         this.ageRating                );
        dos.writeBytes (Auxiliar.formatarString (this.streaming, TAM_STREAMING));
        dos.writeLong  (                         this.lancamento.toEpochDay ( ));

        return baos.toByteArray();
    }

    // FROM BYTE ARRAY
    @Override public void fromByteArray (byte [] b) throws IOException 
    {
        ByteArrayInputStream bais = new ByteArrayInputStream (b   );
        DataInputStream      dis  = new DataInputStream      (bais);

        this.id         = dis.readInt          (                  );
        this.nome       = Auxiliar.lerString   (dis,      TAM_NOME);
        this.sinopse    = Auxiliar.lerString   (dis,   TAM_SINOPSE);
        this.ageRating  = dis.readShort        (                  );
        this.streaming  = Auxiliar.lerString   (dis, TAM_STREAMING);
        this.lancamento = LocalDate.ofEpochDay (dis.readLong (   ));
    }


    // TO STRING
    @Override public String toString ()
    {
        return "| > \""         + nome                   + "\" " + getClassificacaoIndicativa ()       + "\n" +
               "| "             + sinopse                                                              + "\n" +
               "| Lançado em: " + lancamento.toString () + " - no streaming: " + streaming + " - ID: " +  id  ;
    }
}

//////////////////////////////////////////////////