//////////////////////////////////////////////////
// PACOTE

package series;

//////////////////////////////////////////////////
// BIBLIOTECAS DO SISTEMA

import java.io.ByteArrayOutputStream;
import  java.io.ByteArrayInputStream;
import      java.io.DataOutputStream;
import       java.io.DataInputStream;
import           java.time.LocalDate;

//////////////////////////////////////////////////
// BIBLIOTECAS PRÓPRIAS

import aeds3.EntidadeArquivo;

//////////////////////////////////////////////////
// CLASSE SÉRIE EM SI

public class Serie implements EntidadeArquivo 
{
    // VARIÁVEIS
    private short       classificacaoIndicativa;
    private LocalDate             anoLancamento;
    private String                    streaming;
    private String                      sinopse;
    private String                       genero;
    private String                         nome;
    private int                              id;

    public Serie () throws Exception
    {
        this ("", LocalDate.now (), "", "", "", (short) 0);
    }

    public Serie (String nome, LocalDate anoLancamento, String sinopse, String streaming, String genero, short classificacaoIndicativa) throws Exception 
    {
        this (-1, nome, anoLancamento, sinopse, streaming, genero, classificacaoIndicativa);
    }

    public Serie (int id, String nome, LocalDate anoLancamento, String sinopse, String streaming, String genero, short classificacaoIndicativa) throws Exception 
    {
        this.id = id;
        this.nome = nome;
        this.anoLancamento = anoLancamento;
        this.sinopse = sinopse;
        this.streaming = streaming;
        this.genero = genero;
        this.classificacaoIndicativa = classificacaoIndicativa;

    } 

    //[getters]
    public String getClassificacaoIndicativaColorida () 
    {
        final String RESET    =            "\u001B[00m";
        final String PRETO    =  "\u001B[00m\u001B[37m";  // Fundo Preto e texto Branco
        final String VERMELHO =  "\u001B[41m\u001B[37m";  // Fundo Vermelho e texto Branco
        final String LARANJA  =  "\u001B[43m\u001B[37m";  // Fundo Laranja e texto Branco
        final String VERDE    =  "\u001B[42m\u001B[37m";  // Fundo Verde e texto Branco
        final String AZUL     =  "\u001B[44m\u001B[37m";  // Fundo Azul e texto Branco
        final String AMARELO  = "\u001B[103m\u001B[30m"; // Fundo Amarelo e texto Branco
        
        return (classificacaoIndicativa >= 18)? PRETO    + "[+18]" + RESET :
               (classificacaoIndicativa >= 16)? VERMELHO + "[+16]" + RESET :
               (classificacaoIndicativa >= 14)? LARANJA  + "[+14]" + RESET :
               (classificacaoIndicativa >= 12)? AMARELO  + "[+12]" + RESET :
               (classificacaoIndicativa >= 10)? AZUL     + "[+10]" + RESET :
                                  VERDE    + "[ L ]" + RESET ;
    }

    public short      getClassificacaoIndicativa ()  { return classificacaoIndicativa;   }
    public LocalDate  getAnoLancamento ()            { return anoLancamento;             }
    public String     getStreaming ()                { return streaming;                 }
    public String     getSinopse ()                  { return sinopse;                   }
    public String     getGenero ()                   { return genero;                    }
    public String     getNome ()                     { return nome;                      }
    public int        getID ()                       { return id;                        }

    //[setters]
    public void       setAnoLancamento (LocalDate anoLancamento) { this.anoLancamento = anoLancamento;        }
    public void       setStreaming (String streaming)            { this.streaming = streaming;                }
    public void       setSinopse (String sinopse)                { this.sinopse = sinopse;                    }
    public void       setGenero (String genero)                  { this.genero = genero;                      }
    public void       setNome (String nome)                      { this.nome = nome;                          }
    public void       setID (int id)                             { this.id = id;                              }
    public void       setClassificacaoIndicativa (short classificacaoIndicativa) 
    { this.classificacaoIndicativa = classificacaoIndicativa; }


    public byte [] toByteArray () throws Exception 
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream ();
        DataOutputStream dos = new DataOutputStream (baos);
        
        dos.writeInt (id);
        dos.writeUTF (nome);
        dos.writeInt ( (int)anoLancamento.toEpochDay ());
        dos.writeUTF (sinopse);
        dos.writeUTF (streaming);
        dos.writeUTF (genero);
        dos.writeShort (classificacaoIndicativa);
        
        return baos.toByteArray ();
    }

    public void fromByteArray (byte[] vb) throws Exception 
    {
        ByteArrayInputStream bais = new ByteArrayInputStream (vb);
        DataInputStream dis = new DataInputStream (bais);
    
        id = dis.readInt ();
        nome = dis.readUTF ();
        anoLancamento = LocalDate.ofEpochDay (dis.readInt ());
        sinopse = dis.readUTF ();
        streaming = dis.readUTF ();
        genero = dis.readUTF ();
        classificacaoIndicativa = dis.readShort ();
    }

    public String toString ()
    {
        return "Série = [ID: " + id +
                "\nNome: " + nome +
                "\nAno de lançamento: " + anoLancamento +
                "\nSinopse " + sinopse + 
                "\nStreaming: " + streaming + 
                "\nGênero: " + genero + 
                "\nClassificação Indicativa: " + classificacaoIndicativa + "]";
    }

    @Override
 	public boolean equals (Object obj)
    {
 		return (this.getID () == ( (Serie) obj).getID ());
 	}

  public int compareTo (Serie serie) {
    return Integer.compare (this.id, serie.id);
    }
}

//////////////////////////////////////////////////