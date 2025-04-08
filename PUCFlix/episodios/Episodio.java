//////////////////////////////////////////////////
// PACOTE

package episodios;

//////////////////////////////////////////////////
// BIBLIOTECAS DO SISTEMA

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.time.LocalDate;

//////////////////////////////////////////////////
// BIBLIOTECAS PRÓPRIAS

import aeds3.EntidadeArquivo;

//////////////////////////////////////////////////
// IMPLEMENTAÇÃO DE EPISÓDIO

public class Episodio implements EntidadeArquivo 
{
  private int id;                   
  private String nome;              
  private short temporada;          
  private LocalDate lancamento; 
  private int minutos;            
  private String descricao;           
  private int id_serie;             
  
  public Episodio() throws Exception
  {
    this("", 0, LocalDate.now(), 0, 0F, "", -1);
  }
  
  public Episodio(String nome, int temporada, LocalDate lancamento, int minutos, float avaliacao, String descricao, int id_serie) throws Exception
  {
    this(-1 , nome, temporada, lancamento, minutos, avaliacao, descricao, id_serie);
  }

  public Episodio(int id, String nome, int temporada, LocalDate lancamento, int minutos, float avaliacao, String descricao, int id_serie) throws Exception
  {
    this.id = id;
    this.nome = nome;
    this.temporada = (short) temporada;
    this.lancamento = lancamento;
    this.minutos = minutos; 
    this.descricao = descricao;
    this.id_serie = id_serie; 
  }

  //[getters]
  public int        getID()         { return this.id;         }
  public int        getID_serie()   { return this.id_serie;   }
  public String     getNome()       { return this.nome;       }
  public int        getTemporada()  { return this.temporada;  }
  public LocalDate  getLancamento() { return this.lancamento; }
  public int        getMinutos()    { return this.minutos;    }

  public String     getDescricao()  { return this.descricao;  }

  //[setters]

  public void       setID(int id)                       { this.id = id;                         }
  public void       setID_serie(int id_serie)           { this.id_serie = id_serie;             }
  public void       setNome(String nome)                { this.nome = nome;                     }
  public void       setTemporada(int temporada)         { this.temporada = (short) temporada;   }
  public void       setLancamento(LocalDate lancamento) { this.lancamento = lancamento;         }
  public void       setMinutos(int minutos)             { this.minutos = minutos;               }
  public void       setDescricao(String descricao)      { this.descricao = descricao;           }


  public byte[] toByteArray() throws Exception
  {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(baos);

    dos.writeInt(id);
    dos.writeUTF(nome);
    dos.writeShort(temporada);
    dos.writeInt((int) lancamento.toEpochDay());
    dos.writeInt(minutos);

    dos.writeUTF(descricao);
    dos.writeInt(id_serie);

    return baos.toByteArray();
  }

  public void fromByteArray(byte[] vb) throws Exception
  {
    ByteArrayInputStream bais = new ByteArrayInputStream(vb);
    DataInputStream dis = new DataInputStream(bais);

    id = dis.readInt();
    nome = dis.readUTF();
    temporada = dis.readShort();
    lancamento = LocalDate.ofEpochDay(dis.readInt());
    minutos = dis.readInt();

    descricao = dis.readUTF();
    id_serie = dis.readInt();
  }

  public String toString()
  {
    return "Episodio = [ID: " + id +
            "\nNome: " + nome +
            "\nTemporada: " + temporada +
            "\nData de lançamento: " + lancamento +
            "\nDuração: " + minutos + 
            "\nDescrição: " + descricao + 
            "\nSerie: " + id_serie +  "]";
  }


  @Override
	public boolean equals(Object obj)
  {
		return (this.getID() == ((Episodio) obj).getID());
	}

  // @Override
  public int compareTo(Episodio outro) 
  {
      return Integer.compare(this.id, outro.id);
  }
}

//////////////////////////////////////////////////