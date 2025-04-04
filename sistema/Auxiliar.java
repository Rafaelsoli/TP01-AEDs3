//////////////////////////////////////////////////
// PACOTE

package sistema;

//////////////////////////////////////////////////
// BIBLIOTECAS

import java.io.DataInputStream;
import java.io.IOException;

//////////////////////////////////////////////////
// CLASSE AUXILIAR

public class Auxiliar 
{
    // FUNÇÕES
    public static String formatarString (String str, int tamanho) 
    {
        return String.format ("%-" + tamanho + "s", str).substring (0, tamanho);
    }

    public static String lerString (DataInputStream dis, int tamanho) throws IOException 
    {
        byte []            bytes = new byte [tamanho];
        dis.read          (bytes);
        return new String (bytes).trim ();
    }
}

//////////////////////////////////////////////////
