/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espoch.investigacionformativacorr.Modelos;

import org.lsmp.djep.djep.DJep;
import org.nfunk.jep.Node;
import org.nfunk.jep.ParseException;

/**
 *
 * @author Usuario
 */
public class Modelo {
    private String funcion;
    private String funcionDerivada;
    private DJep djep;
    private Node nodeFuncion;
    private Node nodeDerivada;
    
    public void setFuncion(String funcion){
        this.funcion=funcion;
    }
    
    public String getFuncion(){
        return funcion;
    }
    
    public String getFuncionDerivada(){
        return this.funcionDerivada;
    }
    
    public void derivar(){
        try{
            
            this.djep =new DJep ();
            
            this.djep.addStandardFunctions();
            
            this.djep.addStandardConstants();
            
            this.djep.addComplex();
            
            this.djep.setAllowUndeclared(true);
            
            this.djep.setAllowAssignment(true);
            
            this.djep.setImplicitMul(true);
            
            this.djep.addStandardDiffRules();
            
            this.nodeFuncion = this.djep.parse(this.funcion);
            
            Node funcionx = this.djep.differentiate(nodeFuncion, "x");
            
            this.nodeDerivada = this.djep.simplify(funcionx);
            
            this.funcionDerivada = this.djep.toString(this.nodeDerivada);
            
        } catch (ParseException e){
            System.out.println("Error" +e.getErrorInfo());
        }
    }
}
