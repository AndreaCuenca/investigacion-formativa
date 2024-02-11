/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espoch.investigacionformativacorr.controladores;

import ec.edu.espoch.investigacionformativacorr.Modelos.Modelo;
import ec.edu.espoch.investigacionformativacorr.vistas.Vistas;

/**
 *
 * @author Usuario
 */
public class Controladores {
    private Vistas vista;
    private Modelo modelo;
    
    public Controladores(Vistas vista){
        this.vista=vista;
        modelo= new Modelo();
    }
    
    public void procesoDerivar(){
        String u;
        modelo.setFuncion(vista.getArgumento());
        modelo.derivar();
        switch (vista.getFuncion()) {
            case "Sin":
                u="("+modelo.getFuncionDerivada()+" * Cos("+vista.getArgumento()+"))";
                break;
            case "Cos":
                u="- ("+modelo.getFuncionDerivada()+" * Sin("+vista.getArgumento()+"))";
                break;
            case "Tan":
                u="("+modelo.getFuncionDerivada()+" * Sec^2("+vista.getArgumento()+"))";
                break;
            case "Csc":
                u="- ("+modelo.getFuncionDerivada()+" * Csc("+vista.getArgumento()+") * Cot("+vista.getArgumento()+"))";
                break;
            case "Sec":
                u="("+modelo.getFuncionDerivada()+" * Sec("+vista.getArgumento()+") * Tan("+vista.getArgumento()+"))";
                break;
            case "Cot":
                u="- ("+modelo.getFuncionDerivada()+" * Csc^2("+vista.getArgumento()+"))";
                break;
            default:
                u="Error";
                
        }
        long startTime = System.nanoTime();
        vista.mostrarResultado(u);
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        System.out.println("Tiempo transcurrido: " + elapsedTime + " nanosegundos");
        
    }
    
}
