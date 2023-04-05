package util;

import javax.swing.JOptionPane;

public class Mensajero {
    
    public void mensaje(String txt){
        JOptionPane.showMessageDialog(null, txt);
    }
    
    public boolean confirme(String txt){
        int opt = JOptionPane.showConfirmDialog(null, txt, "Confirme", JOptionPane.YES_NO_OPTION);
        if(opt == 0) { return true; }
        return false;
    }
    
    public void error(String titulo, StackTraceElement[] stacks){
        String trace = "";
        for(StackTraceElement stack : stacks){
            trace += stack.toString() + "\n";
        }
        JOptionPane.showMessageDialog(null, trace, titulo, JOptionPane.YES_OPTION);
    }
    
    public void error(String titulo, String errores){
    	JOptionPane.showMessageDialog(null, errores, titulo, JOptionPane.YES_OPTION);
    }
}