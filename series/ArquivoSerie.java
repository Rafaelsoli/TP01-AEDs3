package series;
import aed3.*;

public class ArquivoSerie extends aed3.Arquivo<Serie> {

    Arquivo<Serie> arqSeries;
    HashExtensivel<ParNomeID> indiceIndiretoNome;

    public ArquivoSerie() throws Exception {
        super("Series", Serie.class.getConstructor());
        indiceIndiretoNome = new HashExtensivel<>(
            ParNomeID.class.getConstructor(), 
            4, 
            ".\\dados\\Series\\indiceNome.d.db",   // diretório
            ".\\dados\\Series\\indiceNome.c.db"    // cestos 
        );
    }

    @Override
    public int create(Serie c) throws Exception {
        int id = super.create(c);
        indiceIndiretoNome.create (new ParNomeID(c.getNome (), id));
        return id;
    }

    public Serie read(String Nome) throws Exception {
        ParNomeID pci = indiceIndiretoNome.read(ParNomeID.hash(Nome));
        if(pci == null)
            return null;
        return read(pci.getId());
    }
    
    public boolean delete(String Nome) throws Exception {
        ParNomeID pci = indiceIndiretoNome.read(ParNomeID.hash(Nome));
        if(pci != null) 
            if(delete(pci.getId())) 
                return indiceIndiretoNome.delete(ParNomeID.hash(Nome));
        return false;
    }

    @Override
    public boolean delete(int id) throws Exception {
        Serie c = super.read(id);
        if(c != null) {
            if(super.delete(id))
                return indiceIndiretoNome.delete(ParNomeID.hash(c.getNome ()));
        }
        return false;
    }

    @Override
    public boolean update(Serie novoSerie) throws Exception {
        Serie SerieVelho = read(novoSerie.getNome ());
        if(super.update(novoSerie)) {
            if(novoSerie.getNome ().compareTo(SerieVelho.getNome ())!=0) {
                indiceIndiretoNome.delete(ParNomeID.hash(SerieVelho.getNome ()));
                indiceIndiretoNome.create(new ParNomeID(novoSerie.getNome (), novoSerie.getId()));
            }
            return true;
        }
        return false;
    }
}
