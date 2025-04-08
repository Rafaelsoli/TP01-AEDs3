//////////////////////////////////////////////////
// PACOTE

package series;

//////////////////////////////////////////////////
// BIBLIOTECAS DO SISTEMA

import java.nio.charset.StandardCharsets;
import     java.io.ByteArrayOutputStream;
import      java.io.ByteArrayInputStream;
import          java.io.DataOutputStream;  
import           java.io.DataInputStream;
import           java.util.regex.Pattern;
import              java.text.Normalizer;
import               java.io.IOException;

//////////////////////////////////////////////////
// BIBLIOTECAS PRÓPRIAS

import episodios.*;
import aeds3.*;

//////////////////////////////////////////////////
// CLASSE PAR NOME SERIE ID
 
public class ParNomeSerieId implements RegistroArvoreBMais <ParNomeSerieId> 
{
  // VARIÁVEIS
  private String nome;
  private int id;

  // TAMANHOS
  private short TAMANHO = 34;
  private short TAMANHO_NOME = 30;

  // CONSTRUTORES
  public ParNomeSerieId () throws Exception 
  {
    this ("", -1);
  }

  public ParNomeSerieId (String n) throws Exception 
  {
    this (n, -1);
  }

  public ParNomeSerieId (String t, int i) throws Exception 
  {

    if (!t.isEmpty ()) 
    {

      byte [] vb = t.getBytes (StandardCharsets.UTF_8);

      
      if (vb.length > TAMANHO_NOME) 
      {

        
        byte[] vb2 = new byte[TAMANHO_NOME];
        System.arraycopy (vb, 0, vb2, 0, vb2.length);

        
        int n = TAMANHO_NOME-1;
        while (n>0 &&  (vb2[n]<0 || vb2[n]>127))
          n--;

        
        byte[] vb3 = new byte[n+1];
        System.arraycopy (vb2, 0, vb3, 0, vb3.length);
        vb2 = vb3;


        t = new String (vb2);
      }
    }
    this.nome = t; 
    this.id = i; 
  }
  
  public String getNome () { return nome; }
  public int getId () {  return id; }

  @Override
  public ParNomeSerieId clone () 
  {
    try 
    {
      return new ParNomeSerieId (this.nome, this.id);
    } catch  (Exception e) 
    {
      e.printStackTrace ();
    }
    return null;
  }

  public short size () 
  {
    return this.TAMANHO;
  }

  public int compareTo (ParNomeSerieId a) 
  {
    String str1 = transforma (this.nome);
    String str2 = transforma (a.nome);

    
    if (str2.length () > str1.length ())
      str2 = str2.substring (0, str1.length ());    
    if (str1.compareTo (str2)==0)
      if (this.id == -1)
        return 0;
      else
        return this.id - a.id;
    else
      return str1.compareTo (str2);
  }

  public String toString () 
  {
    return this.nome + ";" + String.format ("%-3d", this.id);
  }

  public byte[] toByteArray () throws IOException 
  {
    ByteArrayOutputStream baos = new ByteArrayOutputStream ();
    DataOutputStream dos = new DataOutputStream (baos);
    byte[] vb = new byte[TAMANHO_NOME];
    byte[] vbNome = this.nome.getBytes ();
    int i=0;
    while (i<vbNome.length) 
    {
      vb[i] = vbNome[i];
      i++;
    }
    while (i<TAMANHO_NOME) 
    {
      vb[i] = ' ';
      i++;
    }
    dos.write (vb);
    dos.writeInt (this.id);
    return baos.toByteArray ();
  }

  public void fromByteArray (byte[] ba) throws IOException 
  {
    ByteArrayInputStream bais = new ByteArrayInputStream (ba);
    DataInputStream dis = new DataInputStream (bais);
    byte[] vb = new byte[TAMANHO_NOME];
    dis.read (vb);
    this.nome =  (new String (vb)).trim ();
    this.id = dis.readInt ();
  }

  public static String transforma (String str) 
  {
    String nfdNormalizedString = Normalizer.normalize (str, Normalizer.Form.NFD);
    Pattern pattern = Pattern.compile ("\\p{InCombiningDiacriticalMarks}+");
    return pattern.matcher (nfdNormalizedString).replaceAll ("").toLowerCase ();
  }

}

//////////////////////////////////////////////////