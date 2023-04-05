package util;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class filtroFileChooser extends FileFilter{
    
    public static final int VIDEOS      = 0;
    public static final int IMAGENES    = 1;
    public static final int MUSICAS     = 2;
    public static final int XML         = 3;
    public static final int XLS         = 4;
    
    public filtroFileChooser(int filtro){
        this.filtro = filtro;
    }

    @Override
    public boolean accept(File f) {
        if(f.isDirectory())
            return true;
        
        String ext = formatoArchivos.getExtension(f);
        if(ext != null){
            if(formatoArchivos.valido(ext, filtro))
                return true;
            else
                return false;
        }
        
        return false;
    }

    @Override
    public String getDescription() {
        switch(filtro){
            case VIDEOS     : return "solo videos";
            case IMAGENES   : return "solo imagenes";
            case MUSICAS    : return "solo musica";
            case XML        : return "solo XML";
            case XLS        : return "solo XLS";
            default         : return null;
        }
    }
    
    int filtro;
}
