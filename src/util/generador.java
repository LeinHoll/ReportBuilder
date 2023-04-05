package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import jxl.*;
import jxl.read.biff.BiffException;
import setget.sgCeldas;

public class generador {
    
    public void generaReporte(int pag, int num){
        sgCeldas celda = new sgCeldas();
        try{
            Workbook libro = Workbook.getWorkbook(excel);
            Sheet hoja = libro.getSheet(pag);
            int numCols = hoja.getColumns();
            int numFils = hoja.getRows();
            int index = 1;
            
            reporte = new File(target + "/I.form" + formTabs.format(num));
            if(reporte.exists()){
                reporte.delete();
            }
            reporte.createNewFile();
            try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(reporte)))) {
                pw.println(hoja.getName());
                pw.println(numCols);
                stopper = false;
                for(int i = 0 ; i < numFils ; i++){
                	if(stopper){
                		break;
                	}
                    int counter = 1;
                    int fila[] = new int[hoja.getColumns()];
                    for(int j = 0 ; j < numCols ; j++){
                        Cell cell = hoja.getCell(j, i);
                        if(cell.getType() == CellType.EMPTY){
                            counter++;
                            if(counter > numCols){
                                celda = new sgCeldas();
                                celda.setValor(".");
                                celdas.add(celda);

                                counter = 1;
                            }
                        } else {
                            celda = new sgCeldas();
                            celda.setColumna    (j                                                                  );
                            celda.setFila       (i                                                                  );
                            celda.setTabulacion (counter                                                            );
                            if(cell.getCellFormat().getFont().getBoldWeight() > 400)
                                celda.setFuente("Bold");
                            else
                                celda.setFuente (cell.getCellFormat ().getFont().getName        ()                  );
                            celda.setColorFuente(cell.getCellFormat ().getFont().getColour      ().getDescription() );
                            celda.setTamaño     (cell.getCellFormat ().getFont().getPointSize   ()                  );
                            celda.setFondo      (cell.getCellFormat ().getBackgroundColour      ().getDescription() );
                            celda.setValor      (cell.getContents   ()                                              );
                            celda.setAlineacion (cell.getCellFormat ().getAlignment             ().getDescription() );
                            switch(celda.getAlineacion()){
                                case "general"  : celda.setAlineacion("left"    ); break;
                                case "centre"   : celda.setAlineacion("center"  ); break;
                            }
                            
                            if(!celda.getFondo().equals("default background")){
                                celda.setIndex(index);
                                fila[j] = index;
                                index++;
                                if(index == 2001){
                                    index = 2011;
                                }
                                if(cell.getContents().startsWith("=")){
                                    formulaTranslator(cell.getContents(), celda, fila);
                                } else {
                                    for(int k = 0 ; k < celda.getValor().length() ; k++){
                                        if(celda.getValor().charAt(k) == 8230){
                                            Character Char = (char) 8230;
                                            celda.setValor(celda.getValor().replaceAll(Char.toString(), "..."));
                                            break;
                                        }
                                    }
                                }
                            }
                            if(celda.isRetry()){
                                error.add(celda);
                            }
                            celdas.add(celda);
                            counter = 1;
                        }
                    }
                    celda = new sgCeldas();
                    celda.setValor("N");
                    celdas.add(celda);
                    filas.add(fila);                    
                }
                
                if(!stopper){
	                for(int i = 0 ; i < celdas.size() ; i++){
	                    celda = celdas.get(i);
	                    if(celda.getIndex() != 0){
	                        if(i != 0 && celdas.get(i - 1).getIndex() != 0){
	                            pw.print(",");
	                        } 
	                        pw.print("#" + celda.getIndex() + formato(celda));
	                    } else {
	                        if(i != 0 && celdas.get(i - 1).getIndex() != 0){
	                            pw.println();
	                        }
	                        if(!celda.getValor().equals("N")){
	                            pw.print("T" + formTabs.format(celda.getTabulacion()));
	                            if(!isReservada(celda.getValor())){
	                                pw.print(" ");
	                            }
	                                pw.println(celda.getValor() + formato(celda));
	                        } else {
	                            pw.println(celda.getValor());
	                        }
	                    }
	                }
	                pw.println();
	                pw.println("^");
	                for(int i = 0 ; i < celdas.size() ; i++){
	                    celda = celdas.get(i);
	                    if(celda.getIndex() != 0 && !celda.isRetry()){
	                        pw.println("V" + celda.getIndex() + "=" + celda.getValor() + ";");
	                    }
	                }
	                for(int i = 0 ; i < error.size() ; i++){
	                	if(stopper){ break; }                		
	                    celda = error.get(i);
	                    int[] fila = new int[0];
	                    formulaTranslator(celda.getFormula(), celda, fila);
	                    if(celda.getIndex() != 0 && !celda.isRetry()){
	                        pw.println("V" + celda.getIndex() + "=" + celda.getValor() + ";");
	                    }
	                }
                }
                
	            pw.print("^");
	            pw.close();
	            celdas.clear();
	            filas.clear();
	            error.clear();
                
                if(!stopper) { mensaje.mensaje("Terminado con éxito"); }
            }
        } catch(IOException | BiffException | IndexOutOfBoundsException exc){
            mensaje.error(exc.toString(), celda.toString());
        }
    }
        
    private void formulaTranslator(String formula, sgCeldas celda, int[] fila){
        celda.setRetry(false);
        int buscaColumna = 0;
        String buscaFila = "";
        String valor = "";

        for(int k = 1 ; k < formula.length() ; k++){
            Character letra = formula.charAt(k);
            String gotValor;
            
            if(letra >= 'A' && letra <= 'Z'){
                buscaColumna *= 26;
                buscaColumna += letra - 64;
            } else if(letra >= '0' && letra <= '9'){
                buscaFila += letra.charValue();
            } else if(letra == '^'){
                if(!buscaFila.isEmpty()){
                    gotValor = getValor1(buscaColumna - 1, Integer.parseInt(buscaFila) - 1, fila, celda);
                    if(!gotValor.isEmpty()){
                        valor += gotValor + "*@n1";
                    }
                } else {
                    valor += "*@n1";
                }
                k += 3;
                buscaColumna = 0;
                buscaFila = "";
            } else if(letra == '%'){
                if(!buscaFila.isEmpty()){
                    gotValor = getValor1(buscaColumna - 1, Integer.parseInt(buscaFila) - 1, fila, celda);
                    if(!gotValor.isEmpty()){
                        valor += gotValor + "*@100";
                    }    
                } else {
                    valor += "*@100";
                }
                buscaColumna = 0;
                buscaFila = "";
            } else {
                if(!buscaFila.isEmpty()){
                    gotValor = getValor1(buscaColumna - 1, Integer.parseInt(buscaFila) - 1, fila, celda);
                    if(!gotValor.isEmpty()){
                        valor +=  gotValor + letra;
                    }
                } else {
                    valor += letra;
                }
                buscaColumna = 0;
                buscaFila = "";
            }
        }                                    
        if(buscaFila.isEmpty()){
            celda.setValor(valor);
        } else {
            celda.setValor(valor + getValor1(buscaColumna - 1, Integer.parseInt(buscaFila) - 1, fila, celda));
        }
        if(celda.isRetry()){
            if(celda.getFormula().isEmpty()){
                celda.setFormula(formula);
            } else {
                mensaje.mensaje("error critico de sintaxis, se cerrará el programa\n" + celda.toString());
                stopper = true;
            }
        }
    }
    
    private String getValor1(int col, int row, int[] fila, sgCeldas celda){
    	String valor1 = getValor2(col, row, fila, celda);
    	if(valor1.contains("V0") && !stopper){
    		mensaje.mensaje("error crítico de sintaxis, una celda devolvió V0\n" + celda.toString());
    		stopper = true;
    	}
    	return valor1;
    }
    
    private String getValor2(int col, int row, int[] fila, sgCeldas celda){
        if(row < filas.size()){
            fila = filas.get(row);
            return "V" + fila[col];
        } else if(row == filas.size() && fila[col] != 0){
            return "V" + fila[col];
        }
        celda.setRetry(true);
        return "";
    }
    
    private boolean isReservada(String valor){
        if(valor.startsWith("#") && valor.length() == 5){
            int value = Integer.parseInt(valor.substring(1, 4));
            if(value >= 2001 || value <= 2010){
                return true;
            }
        }
        return false;
    }
    
    private String formato(sgCeldas celda){
        if(celda.getValor().equals(".")){
            return "[color=white]";
        } else if (celda.getIndex() != 0){
            return "";
        }
        return "[color=" + celda.getColorFuente() + ",font=" + celda.getFuente() + ",alin="+ celda.getAlineacion() + "]";
    }
    
    public void setExcel(File excel){
        this.excel = excel;
    }
    
    public void setTarget(String target){
        this.target = target;
    }
    
    public DefaultListModel listaHojas() throws Exception {
        DefaultListModel model = new DefaultListModel();
        Workbook book = Workbook.getWorkbook(excel);
        for(Sheet sheet : book.getSheets())
            model.addElement(sheet.getName());
        return model;
    }
    
    boolean				stopper		= false;
    File                excel                                   ;
    String              target      = ""                        ;
    File                reporte                                 ;
    DecimalFormat       formTabs    = new DecimalFormat("00")   ;
    ArrayList<sgCeldas> celdas      = new ArrayList()           ;
    ArrayList<sgCeldas> error       = new ArrayList()           ;
    ArrayList<int[]>    filas       = new ArrayList()           ;
    Mensajero           mensaje     = new Mensajero()           ;
}