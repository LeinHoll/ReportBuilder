package arranque;

import visual.framePrincipal;

public class inicio {
    
    public static void main(String [] args){
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception exc){
            exc.printStackTrace();
        }
        
        framePrincipal principal = new framePrincipal();
        principal.setLocationRelativeTo(null);
        principal.setVisible(true);
    }
}
