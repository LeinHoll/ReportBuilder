package util;

import java.io.File;

public class formatoArchivos {
    
    private static final String formatos[][] = {
        {"avi","mp4","mpeg","flv","mov","mkv","wmv"},
        {"jpeg","jpg","tif","tiff","gif","bmp","png"},
        {"mp3","midi","wma"},
        {"xml"},
        {"xls"}
    };
    
    public static String getExtension(File f){
        String ext = null;
        String sou = f.getName();
        int indi = sou.lastIndexOf(".");
        
        if(indi > 0 && indi < sou.length() - 1)
            ext = sou.substring(indi + 1).toLowerCase();
        
        return ext;
    }
    
    public static boolean valido(String ext, int Type){
        for(int i = 0 ; i < formatos[Type].length ; i++){
            if(ext.equals(formatos[Type][i]))
                return true;
        }
        return false;
    }
}
