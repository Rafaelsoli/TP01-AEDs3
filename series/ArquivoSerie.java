package series;
import aed3.*;

public class ArquivoSerie extends aed3.Arquivo<Serie> {

    Arquivo<Serie> arqSeries;
    HashExtensivel<ParCPFID> indiceIndiretoCPF;

    public ArquivoSerie() throws Exception {
        super("Series", Serie.class.getConstructor());
        indiceIndiretoCPF = new HashExtensivel<>(
            ParCPFID.class.getConstructor(), 
            4, 
            ".\\dados\\Series\\indiceCPF.d.db",   // diretório
            ".\\dados\\Series\\indiceCPF.c.db"    // cestos 
        );
    }

    @Override
    public int create(Serie c) throws Exception {
        int id = super.create(c);
        indiceIndiretoCPF.create(new ParCPFID(c.getCpf(), id));
        return id;
    }

    public Serie read(String cpf) throws Exception {
        ParCPFID pci = indiceIndiretoCPF.read(ParCPFID.hash(cpf));
        if(pci == null)
            return null;
        return read(pci.getId());
    }
    
    public boolean delete(String cpf) throws Exception {
        ParCPFID pci = indiceIndiretoCPF.read(ParCPFID.hash(cpf));
        if(pci != null) 
            if(delete(pci.getId())) 
                return indiceIndiretoCPF.delete(ParCPFID.hash(cpf));
        return false;
    }

    @Override
    public boolean delete(int id) throws Exception {
        Serie c = super.read(id);
        if(c != null) {
            if(super.delete(id))
                return indiceIndiretoCPF.delete(ParCPFID.hash(c.getCpf()));
        }
        return false;
    }

    @Override
    public boolean update(Serie novoSerie) throws Exception {
        Serie SerieVelho = read(novoSerie.getCpf());
        if(super.update(novoSerie)) {
            if(novoSerie.getCpf().compareTo(SerieVelho.getCpf())!=0) {
                indiceIndiretoCPF.delete(ParCPFID.hash(SerieVelho.getCpf()));
                indiceIndiretoCPF.create(new ParCPFID(novoSerie.getCpf(), novoSerie.getId()));
            }
            return true;
        }
        return false;
    }
}
