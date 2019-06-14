package com.cc.paint3D.Graficador3D.Eventos_teclado;

import java.awt.AWTEvent;
import java.awt.event.KeyEvent;
import java.util.Enumeration;
import javax.media.j3d.Behavior;
import javax.media.j3d.WakeupCriterion;
import javax.media.j3d.WakeupOnAWTEvent;

public class Mi_KeyAction_Navegador_funcionamiento extends Behavior {

    
    /**
     * especifica un (comportamiento)Behavior de (activarse)wakeup cuando un evento AWT especifico ocurra.
     */
    private WakeupOnAWTEvent wakeupOnAWTEvent;

    
     // obtiene la decision para una accion (evento) dada 
     
    private Mi_KeyAction_navegador mi_keyNavegador;

    
    public Mi_KeyAction_Navegador_funcionamiento(Mi_KeyAction_navegador keyNavegador){
        super();
        mi_keyNavegador = keyNavegador;
    }

    /**
     * fija en el Behavior del metodo inicial, el criterio de wakeup (despertase).
     */
     
    public void initialize(){
        
        wakeupOnAWTEvent = new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED);
        wakeupOn(wakeupOnAWTEvent);                // establece criterio inicial de wakeup (despertase) 
    }

    
          
    public void processStimulus(Enumeration criteria){
        // chequeamos los criterios
        while (criteria.hasMoreElements()) {
            WakeupCriterion genericEvt = (WakeupCriterion) criteria.nextElement();
            //estamos interesados solo en los eventos AWT
	    if (genericEvt instanceof WakeupOnAWTEvent) {
                // decode event
                WakeupOnAWTEvent ev = (WakeupOnAWTEvent) genericEvt;
                AWTEvent[] events = ev.getAWTEvent();

                // mas de una tecla puede ser presionada, o muchos eventos son recolectados
                for (int i=0; i<events.length; i++) {
                    // sabemos si es un KeyEvent
                    if (events[i] instanceof KeyEvent) {
                        // obtenemos el codigo de la tecla presionada y delegamos
                        int keyCode = ((KeyEvent) events[i]).getKeyCode();
                        
                        mi_keyNavegador.processKeyEvent(keyCode);
                    }
                }
            }
        }
        // fijamos el criterio de (despertarse) wakeup para la siguiente vez
        wakeupOn(wakeupOnAWTEvent);
    }
}



