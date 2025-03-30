package series;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ParNomeID implements aed3.RegistroHashExtensivel<ParNomeID> 
{
    
    private String nome; // chave
    private int id;     // valor

    public ParNomeID () 
    {
        this.nome = "<sem nome>";
        this.id = -1;
    }

    public ParNomeID (String nome, int id) throws Exception 
    {
        this.nome = nome;
        this.id = id;
    }

    public String getNome () {return nome;}
    public int    getId   () {return   id;}
 
    @Override
    public int hashCode () 
    {
        return hash (this.nome);
    }

    public short size () 
    {
        return (short) (4 + nome.getBytes (StandardCharsets.UTF_8).length); // para garantir caractere 'ç'
    }

    public String toString () 
    {
        return "("+this.nome + ";" + this.id+")";
    }

    public byte [] toByteArray () throws IOException 
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream ();
        DataOutputStream dos = new DataOutputStream (baos);
        byte [] nomeBytes = nome.getBytes (StandardCharsets.UTF_8);
        dos.writeInt(nomeBytes.length);  
        dos.write   (nomeBytes);         
        dos.writeInt(this.id);        
        return baos.toByteArray();
    }

    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        int nomeLength = dis.readInt();    // Read the string length
        byte[] nomeBytes = new byte[nomeLength];
        dis.readFully(nomeBytes);          // Read the string bytes
        this.nome = new String(nomeBytes, StandardCharsets.UTF_8);
        this.id = dis.readInt();           // Read the id
    }


    public static int hash(String nome) throws IllegalArgumentException {
        // Certifique-se de que o nome contém exatamente 11 dígitos
        if (nome.length() != 11 || !nome.matches("\\d{11}")) {
            throw new IllegalArgumentException("nome deve conter exatamente 11 dígitos numéricos.");
        }

        // Converter o nome para um número inteiro longo
        long nomeLong = Long.parseLong(nome);

        // Aplicar uma função de hash usando um número primo grande
        int hashValue = (int) (nomeLong % (int)(1e9 + 7));

        // Retornar um valor positivo
        return Math.abs(hashValue);
    }


}
