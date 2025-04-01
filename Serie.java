import java.time.LocalDate;

import aed3.Registro;

import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class Serie implements Registro {

    private int id;
    public String nome;
    public LocalDate lancamento;
    public int idStreaming;

    public Serie() {
        this(-1, "", LocalDate.now(), -1);
    }
    public Serie(String nome, LocalDate lancamento, int idStreaming) {
        this(-1, nome, lancamento, idStreaming);
    }

    public Serie(int id, String nome, LocalDate lancamento, int idStreaming) {
        this.id = id;
        this.nome = nome;
        this.lancamento = lancamento;
        this.idStreaming = idStreaming;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String toString() {
        return "\nID...........: " + this.id         +
               "\nNome.........: " + this.nome       +
               "\nLancamento...: " + this.lancamento +
               "\nIdStreaming..: " + this.idStreaming;
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(this.id);
        dos.writeUTF(this.nome);
        dos.writeInt((int) this.lancamento.toEpochDay());
        dos.writeInt(this.idStreaming);
        return baos.toByteArray();
    }


    public void fromByteArray(byte[] b) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        DataInputStream dis = new DataInputStream(bais);

        this.id = dis.readInt();
        this.nome = dis.readUTF();
        this.lancamento = LocalDate.ofEpochDay(dis.readInt());
        this.idStreaming = dis.readInt();
    }
}
