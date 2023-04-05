package setget;

public class sgCeldas {
    
    public void setTabulacion(int tabulacion){
        this.tabulacion = tabulacion;
    }
    
    public int getTabulacion(){
        return tabulacion;
    }
    
    public void setColumna(int columna){
        this.columna = columna;
    }
    
    public int getColumna(){
       return columna;
    }
    
    public void setFila(int fila){
        this.fila = fila;
    }
    
    public int getFila(){
        return fila;
    }
    
    public void setIndex(int index){
        this.index = index;
    }
    
    public int getIndex(){
        return index;
    }
    
    public void setValor(String valor){
        this.valor = valor;
    }
    
    public String getValor(){
        return valor;
    }

    public int getTamaño() {
        return tamaño;
    }

    public void setTamaño(int tamaño) {
        this.tamaño = tamaño;
    }

    public String getFuente() {
        return fuente;
    }

    public void setFuente(String fuente) {
        this.fuente = fuente;
    }

    public String getFondo() {
        return fondo;
    }

    public void setFondo(String fondo) {
        this.fondo = fondo;
    }

    public String getColorFuente() {
        return colorFuente;
    }

    public void setColorFuente(String colorFuente) {
        this.colorFuente = colorFuente;
    }

    public String getAlineacion() {
        return alineacion;
    }

    public void setAlineacion(String alineacion) {
        this.alineacion = alineacion;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public boolean isRetry() {
        return retry;
    }

    public void setRetry(boolean retry) {
        this.retry = retry;
    }
    
    public String toString(){
    	return 	"index " + index + " \n"
    			+ "columna " + columna + 1 + " \n"
    			+ "fila " + fila + 1 + " \n"
    			+ "tabulación " + tabulacion + " \n"
    			+ "tamaño " + tamaño + " \n"
    			+ "fuente " + fuente + " \n"
    			+ "alineación " + alineacion + " \n"
    			+ "valor " + valor + " \n"
    			+ "fondo " + fondo + " \n"
    			+ "colorFuente " + colorFuente + " \n"
    			+ "formula " + formula + " \n"
    			+ "retry " + retry;
    }
    
    int         index       = 0;
    int         columna     = 0;
    int         fila        = 0;
    int         tabulacion  = 1;
    int         tamaño      = 0;
    String      fuente      = "";
    String      alineacion  = "";
    String      valor       = "";
    String      fondo       = "";
    String      colorFuente = "";
    String      formula     = "";
    boolean     retry       = false;
}