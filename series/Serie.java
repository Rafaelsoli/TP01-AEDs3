package series;
import java.time.LocalDate;

import aed3.Registro;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class Serie implements Registro 
{
    private int               id; // id Série
    private String          nome;
    private LocalDate lancamento;
    private int     id_streaming;

    public Serie () 
    {
        this (-1, "", LocalDate.now(), -1);
    }
    public Serie (String nome, LocalDate lancamento, int id_streaming) 
    {
        this (-1, nome, lancamento, id_streaming);
    }

    public Serie (int id, String nome, LocalDate lancamento, int id_streaming) 
    {
        this.id = id;
        this.nome = nome;
        this.lancamento = lancamento;
        this.id_streaming = id_streaming;
    }

    public void      setLancamento   (LocalDate lancamento) {this.lancamento   =   lancamento;}
    public LocalDate getLancamento   (                    ) {return                lancamento;}
    public void      setID_Streaming (int     id_streaming) {this.id_streaming = id_streaming;}
    public int       getID_Streaming (                    ) {return              id_streaming;}
    public void      setNome         (String          nome) {this.nome         =         nome;}
    public String    getNome         (                    ) {return                      nome;}
    public void      setId           (int               id) {this.id           =           id;}
    public int       getId           (                    ) {return                        id;}

    public String toString () 
    {
        return "\nID............: " + this.id           +
               "\nNome..........: " + this.nome         +
               "\nLançamento....: " + this.lancamento   +
               "\nIDStreaming...: " + this.id_streaming ;
    }

    public byte [] toByteArray () throws IOException 
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream ();
        DataOutputStream dos = new DataOutputStream (baos);
        dos.writeInt (this.id);
        dos.writeUTF (this.nome);
        dos.writeInt ((int) this.lancamento.toEpochDay ());
        dos.writeInt (this.id_streaming);
        return baos.toByteArray ();
    }


    public void fromByteArray (byte [] b) throws IOException 
    {
        ByteArrayInputStream bais = new ByteArrayInputStream (b);
        DataInputStream dis = new DataInputStream (bais);

        this.id = dis.readInt ();
        this.nome = dis.readUTF ();
        this.lancamento = LocalDate.ofEpochDay (dis.readInt ());
        this.id_streaming = dis.readInt ();
    }
}
