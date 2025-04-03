//////////////////////////////////////////////////
// Pacote

package sistema.series;

//////////////////////////////////////////////////
// Bibliotecas

import java.time.LocalDate; // Para o <lancamento>
import aed3.Registro; // Para o modelo de entidade <registro>

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

    public Serie ()
    {
        this 
        (
            "", "", (short) 0, "", LocalDate.now ()
        );
    }

    // GETTERS
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
        return "";
    }
}

//////////////////////////////////////////////////