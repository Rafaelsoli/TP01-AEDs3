//////////////////////////////////////////////////
// Pacote

package sistema.series;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException; // Para falar que input não está correto

//////////////////////////////////////////////////
// Bibliotecas

// > Nenhuma necessária de documentar

//////////////////////////////////////////////////
// Implementação do par em si

public class ParIDNome implements aed3.RegistroHashExtensivel <ParIDNome>
{
    // VARIÁVEIS
    private       int        id;
    private       String   nome; 

    // TAMANHOS FIXOS 
    private final short TAMANHO; // TAMANHO fixo em bytes

    // CONSTRUTORES
    public ParIDNome (int id, String nome)
    {
        this.id      =   id;
        this.nome    = nome.length () > 60 ? // Garatir que o TAMANHO não passe 64
                       nome.substring (0, 60) : nome;
        this.TAMANHO =   64; // 4 para o int ID, 60 para a String Serie
    }

    public ParIDNome (String nome)
    {
        this (-1, nome);
    }

    public ParIDNome ()
    {
        this ("");
    }

    // GETTERS
    public short  size       () {return TAMANHO        ;}
    public int    getId      () {return      id        ;}
    public String getNome    () {return    nome.trim ();}
    public short  getTamanho () {return TAMANHO        ;}

    // HASH
    @Override
    public int hashCode ()
    {
        return nome.hashCode ();
    }

    // TO BYTE ARRAY
    public byte [] toByteArray () throws IOException  
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream (    );
        DataOutputStream      dos  = new DataOutputStream      (baos);
        
        dos.writeInt (id);
        
        // Preenche o nome até 60 bytes
        byte [] nomeBytes = new byte [60];
        byte [] nomeOriginal = nome.getBytes ("ISO-8859-1"); 
        System.arraycopy 
        (
            nomeOriginal, 0, nomeBytes, 0, Math.min (nomeOriginal.length, 60)
        );

        dos.write (nomeBytes);
        return baos.toByteArray ();
    }

    // FROM BYTE ARRAY
    public void fromByteArray (byte [] ba) throws IOException  
    {
        ByteArrayInputStream bais = new ByteArrayInputStream (ba  );
        DataInputStream      dis  = new DataInputStream      (bais);

        this.id = dis.readInt ();

        byte []                 nomeBytes = new byte [60];
        dis.read               (nomeBytes);
        this.nome = new String (nomeBytes, "ISO-8859-1").trim (); // Remove espaços extras
    }

    // TO STRING
    public String toString ()
    {
        return "(" + nome + ";" + id + ")";
    }

}

//////////////////////////////////////////////////