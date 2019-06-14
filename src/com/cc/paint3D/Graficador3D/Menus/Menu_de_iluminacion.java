
package com.cc.paint3D.Graficador3D.Menus;

import com.cc.paint3D.Graficador3D.Graficador3D;
import com.cc.paint3D.Graficador3D.Escena;
import com.cc.paint3D.Graficador3D.Barra;
import com.cc.paint3D.Graficador3D.interfaces.Fijable;
import com.cc.paint3D.Graficador3D.G3D.Figura_luz;
import com.cc.paint3D.Graficador3D.ES.ES_Utiles;
import com.cc.paint3D.Graficador3D.interfaces.Ajustador_de_rotacion;
import com.cc.paint3D.Graficador3D.Paneles.Panel_de_texto_estado;
import com.cc.paint3D.Graficador3D.ES.Dialogo_de_atenuacion;
import com.cc.paint3D.Graficador3D.interfaces.Ajustador_de_vector;
import com.cc.paint3D.Graficador3D.interfaces.Ajustador_de_cambio_de_real;
import com.cc.paint3D.Graficador3D.interfaces.Ajustador_de_angulo;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Vector;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.SpotLight;
import javax.swing.*;
import javax.vecmath.Vector3f;

public class Menu_de_iluminacion extends JMenu
    implements ActionListener, Fijable
{
	
	public static final int NINGUNO = 0;
    public static final int SELECCIONADO_PUNTUAL = 1;
    public static final int SELECCIONADO_SPOT = 2;
    public static final int ANGULO = 0;
    public static final int DIRECCION = 1;
    public static final int CONCENTRACION = 2;
    
    public String SPOT;
    public String PUNTUAL;
    
    public String AGREGAR_LUZ_DIRECCIONAL;
    public String CAMBIAR_DIRECCIONAL;public static JMenuItem jmi_cambiar_Direccional;
    public String BORRAR_DIRECCIONAL;public static JMenuItem jmi_borrar_Direccional;
    public String OCULTAR_LUCES;
    public String SELECCIONAR_SPOT;public static JMenuItem jmi_seleccionar_Spot;
    public String BORRAR_SPOT;public static JMenuItem jmi_borrar_Spot;
    public String BORRAR_TODAS_LAS_LUCES;
    
    public String ATENUACION_SPOT;public static JMenuItem jmi_atenuacion_Spot;
    public String DIRECCION_SPOT;public static JMenuItem jmi_direccion_Spot;
    public String ANGULO_SPOT;public static JMenuItem jmi_angulo_Spot;
    public String CONCENTRACION_SPOT;public static JMenuItem jmi_concentracion_Spot;
    
    public String ATENUACION_PUNTUAL;public static JMenuItem jmi_atenuacion_Puntual;
    public String SELECCIONAR_PUNTUAL;public static JMenuItem jmi_seleccionar_Puntual;
    public String BORRAR_PUNTUAL;public static JMenuItem jmi_borrar_Puntual;
    
    
    public static Vector vector_de_figura_luz_Puntual;
    public static Vector vector_de_figura_luz_Spot;
            
    public static Figura_luz figura_luz_Direccional;
    public static Figura_luz figura_luz_Spot_actual;
    public static Figura_luz figura_luz_Puntual_actual;
    public static int accion;
    
	
    public Menu_de_iluminacion()
    {
        super(Graficador3D.LIGHTS);
        SPOT = Graficador3D.ADD_SPOT;
        PUNTUAL = Graficador3D.ADD_POINT;
        AGREGAR_LUZ_DIRECCIONAL = Graficador3D.ADD_DIRECTIONAL;
        CAMBIAR_DIRECCIONAL = Graficador3D.CHANGE_DIRECCTION;
        BORRAR_DIRECCIONAL = Graficador3D.DELETE_DIRECTIONAL;
        OCULTAR_LUCES = Graficador3D.HIDE_LIGHTS;
        SELECCIONAR_SPOT = Graficador3D.SELECT_SPOT;
        BORRAR_SPOT = Graficador3D.REMOVE_SPOT;
        BORRAR_TODAS_LAS_LUCES = Graficador3D.REMOVE_ALL_LIGHTS;
        ATENUACION_SPOT = Graficador3D.ALTER_SPOT_ATTENUATION;
        DIRECCION_SPOT = Graficador3D.ALTER_DIRECTION;
        ANGULO_SPOT = Graficador3D.ALTER_SPREAD;
        CONCENTRACION_SPOT = Graficador3D.ALTER_CONCENTRATION;
        ATENUACION_PUNTUAL = Graficador3D.ALTER_POINT_ATTENUATION;
        SELECCIONAR_PUNTUAL = Graficador3D.SELECT_POINT;
        BORRAR_PUNTUAL = Graficador3D.REMOVE_POINT;
        vector_de_figura_luz_Spot = new Vector();
        vector_de_figura_luz_Puntual = new Vector();
        JMenu jmenu = new JMenu(Graficador3D.DIRECTIONAL_LIGHT);
        add(jmenu);
        jmenu.add(ES_Utiles.get_MI(AGREGAR_LUZ_DIRECCIONAL, this));
        jmenu.add(jmi_cambiar_Direccional = ES_Utiles.get_MI(CAMBIAR_DIRECCIONAL, this));
        jmenu.add(jmi_borrar_Direccional = ES_Utiles.get_MI(BORRAR_DIRECCIONAL, this));
        luz_Direccional_Off();
        add(jmenu = new JMenu(Graficador3D.POINT_LIGHT));
        jmenu.add(ES_Utiles.get_MI(PUNTUAL, this));
        jmenu.add(jmi_seleccionar_Puntual = ES_Utiles.get_MI(SELECCIONAR_PUNTUAL, this));
        jmi_seleccionar_Puntual.setEnabled(false);
        jmenu.addSeparator();
        jmenu.add(jmi_atenuacion_Puntual = ES_Utiles.get_MI(ATENUACION_PUNTUAL, this));
        jmenu.addSeparator();
        jmenu.add(jmi_borrar_Puntual = ES_Utiles.get_MI(BORRAR_PUNTUAL, this));
        add(jmenu = new JMenu(Graficador3D.SPOT_LIGHT));
        jmenu.add(ES_Utiles.get_MI(SPOT, this));
        jmenu.add(jmi_seleccionar_Spot = ES_Utiles.get_MI(SELECCIONAR_SPOT, this));
        jmi_seleccionar_Spot.setEnabled(false);
        jmenu.addSeparator();
        jmenu.add(jmi_atenuacion_Spot = ES_Utiles.get_MI(ATENUACION_SPOT, this));
        jmenu.add(jmi_angulo_Spot = ES_Utiles.get_MI(ANGULO_SPOT, this));
        jmenu.add(jmi_direccion_Spot = ES_Utiles.get_MI(DIRECCION_SPOT, this));
        jmenu.add(jmi_concentracion_Spot = ES_Utiles.get_MI(CONCENTRACION_SPOT, this));
        jmenu.addSeparator();
        jmenu.add(jmi_borrar_Spot = ES_Utiles.get_MI(BORRAR_SPOT, this));
        luz_Spot_Off();
        luz_Puntual_Off();
        addSeparator();
        add(ES_Utiles.get_MI(OCULTAR_LUCES, this));
        add(ES_Utiles.get_MI(BORRAR_TODAS_LAS_LUCES, this));
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        String s = actionevent.getActionCommand();
        if(s == CAMBIAR_DIRECCIONAL)
        {
            if(figura_luz_Direccional != null)
                new Ajustador_de_rotacion(figura_luz_Direccional, Graficador3D.ROTATE_DIRECTIONAL);//" Rotate directional"
        } else
        if(s == OCULTAR_LUCES)
        {
            set_On(false, vector_de_figura_luz_Spot);
            set_On(false, vector_de_figura_luz_Puntual);
        } else
        if(s == SELECCIONAR_PUNTUAL)
        {
            set_On(false, vector_de_figura_luz_Spot);
            if(get_numero_de_luces_Puntuales() == 1)
            {
                figura_luz_Puntual_actual = get_primera_luz_Puntual();
                figura_luz_Puntual_actual.encender(true);
                luz_Puntual_On();
            } else
            {
                accion = SELECCIONADO_PUNTUAL;
                Panel_de_texto_estado.set_texto(Graficador3D.CLICK_ON_THE_POINTLIGHT_TO_BE_SELECTED, Color.black, Color.magenta);//"Click on the pointlight to be selected"
                set_On(true, vector_de_figura_luz_Puntual);
            }
        } else
        if(s == SELECCIONAR_SPOT)
        {
            set_On(false, vector_de_figura_luz_Puntual);
            if(get_numero_de_luces_Spot() == 1)
            {
                figura_luz_Spot_actual = get_primera_luz_Spot();
                figura_luz_Spot_actual.encender(true);
                luz_Spot_On();
            } else
            {
                accion = SELECCIONADO_SPOT;
                Panel_de_texto_estado.set_texto(Graficador3D.CLICK_ON_THE_SPOTLIGHT_TO_BE_SELECTED, Color.black, Color.magenta);
                set_On(true, vector_de_figura_luz_Spot);
            }
        } else
        if(s == BORRAR_DIRECCIONAL)
            borrar_luz_Direccional();
        else
        if(s == BORRAR_SPOT)
        {
            figura_luz_Spot_actual.bg.detach();
            vector_de_figura_luz_Spot.setElementAt(null, figura_luz_Spot_actual.pos);
            figura_luz_Spot_actual = null;
            luz_Spot_Off();
            if(get_numero_de_luces_Spot() == 0)
                jmi_seleccionar_Spot.setEnabled(false);
        } else
        if(s == BORRAR_PUNTUAL)
        {
            figura_luz_Puntual_actual.bg.detach();
            vector_de_figura_luz_Puntual.setElementAt(null, figura_luz_Puntual_actual.pos);
            figura_luz_Puntual_actual = null;
            luz_Puntual_Off();
            if(get_numero_de_luces_Puntuales() == 0)
                jmi_seleccionar_Puntual.setEnabled(false);
        } else
        if(s == ATENUACION_SPOT)
            new Dialogo_de_atenuacion(figura_luz_Spot_actual);
        else
        if(s == ATENUACION_PUNTUAL)
            new Dialogo_de_atenuacion(figura_luz_Puntual_actual);
        else
        if(s == ANGULO_SPOT)
        {
            if(get_numero_de_luces_Spot() == 1)
                newAjustador_de_angulo();
        } else
        if(s == DIRECCION_SPOT)
        {
            Vector3f vector3f = new Vector3f();
            ((SpotLight)figura_luz_Spot_actual.light).getDirection(vector3f);
            new Ajustador_de_vector(DIRECCION, vector3f, Graficador3D.ALTER_SPOT_DIRECCION, this);//" Alter spot direction"
        } else
        if(s == CONCENTRACION_SPOT)
            new Ajustador_de_cambio_de_real(CONCENTRACION, this, Graficador3D.CONCENTRATION);//"Concentration"
        else
        if(s == BORRAR_TODAS_LAS_LUCES)
        {
            borrar_todas_las_luces();
        } else
        {
            Color color = JColorChooser.showDialog(Escena.handle, Graficador3D.CHOOSE_THE_COLOR_OF_THE_LIGHT, Color.white);//"Choose the color of the light"
            Barra.handle.repaint();
            if(color == null)
                return;
            if(s == AGREGAR_LUZ_DIRECCIONAL)
            {
                borrar_luz_Direccional();
                figura_luz_Direccional = new Figura_luz(0, color);
                figura_luz_Direccional.addChild_a(Escena.tg);
                luz_Direccional_On();
            } else
            if(s == PUNTUAL)
            {
                
                Figura_luz figura_luz = new Figura_luz(Figura_luz.PUNTUAL, color);
                if(figura_luz != null)
                {
                    figura_luz.pos = vector_de_figura_luz_Puntual.size();
                    vector_de_figura_luz_Puntual.addElement(figura_luz);
                    luz_Puntual_On();
                    figura_luz.addChild_a(Escena.tg);
                }
                jmi_seleccionar_Puntual.setEnabled(true);
                luz_Puntual_On();
                figura_luz_Puntual_actual = figura_luz;
            } else
            if(s == SPOT)
            {
                
                Figura_luz figura_luz1 = new Figura_luz(Figura_luz.SPOT, color);
                if(figura_luz1 != null)
                {
                    figura_luz1.pos = vector_de_figura_luz_Spot.size();
                    vector_de_figura_luz_Spot.addElement(figura_luz1);
                    luz_Spot_On();
                    figura_luz1.addChild_a(Escena.tg);
                }
                jmi_seleccionar_Spot.setEnabled(true);
                luz_Spot_On();
                figura_luz_Spot_actual = figura_luz1;
            }
        }
    }

    public static void apagar_todas_las_luces()
    {
        set_On(false, vector_de_figura_luz_Spot);
        set_On(false, vector_de_figura_luz_Puntual);
    }

    public void newAjustador_de_angulo()
    {
       new Ajustador_de_angulo(ANGULO, Graficador3D.SPREAD, this);
    }

    public void set_Prop(int i, Object obj)
    {
        switch(i)
        {
        case ANGULO: 
            float f = ((Float)obj).floatValue();
            ((SpotLight)figura_luz_Spot_actual.light).setSpreadAngle(f);
            break;

        case DIRECCION: 
            ((SpotLight)figura_luz_Spot_actual.light).setDirection((Vector3f)obj);
            break;

        case CONCENTRACION: 
            float f1 = ((Float)obj).intValue();
            ((SpotLight)figura_luz_Spot_actual.light).setConcentration(f1);
            break;
        }
    }

    public void set_Prop(int i, float f_valor)
    {
        switch(i)
        {
        case CONCENTRACION: 
            ((SpotLight)figura_luz_Spot_actual.light).setConcentration( f_valor );

        case ANGULO: 
            ((SpotLight)figura_luz_Spot_actual.light).setSpreadAngle( f_valor );
        }
        
    }
    
    public Object get_Prop(int i)
    {
        switch(i)
        {
        case CONCENTRACION: 
            return new Float(((SpotLight)figura_luz_Spot_actual.light).getConcentration());

        case ANGULO: 
            return new Float(((SpotLight)figura_luz_Spot_actual.light).getSpreadAngle());
        }
        return null;
    }

    public static void agregar_luz(DataInputStream datainputstream)
        throws IOException
    {
        int i = datainputstream.readInt();
        Figura_luz figura_luz = new Figura_luz(i, new Color(datainputstream.readInt()), datainputstream);
        switch(i)
        {
        case NINGUNO: 
            luz_Direccional_On();
            figura_luz_Direccional = figura_luz;
            break;

        case SELECCIONADO_PUNTUAL:
            jmi_seleccionar_Puntual.setEnabled(true);
            figura_luz.encender(false);
            vector_de_figura_luz_Puntual.addElement(figura_luz);
            break;

        case SELECCIONADO_SPOT:
            jmi_seleccionar_Spot.setEnabled(true);
            figura_luz.encender(false);
            vector_de_figura_luz_Spot.addElement(figura_luz);
            break;
        }
        figura_luz.addChild_a(Escena.tg);
    }

    public static void borrar_todas_las_luces()
    {
        borrar_todas_Spot();
        borrar_todas_Puntual();
        borrar_luz_Direccional();
        System.gc();
        luz_Puntual_Off();
        luz_Spot_Off();
    }

    public static void borrar_todas_Spot()
    {
        int i = vector_de_figura_luz_Spot.size();
        for(int j = 0; j < i; j++)
        {
            Object obj = vector_de_figura_luz_Spot.elementAt(j);
            if(obj != null)
            {
                Figura_luz figura_luz = (Figura_luz)obj;
                figura_luz.bg.detach();
            }
        }

        vector_de_figura_luz_Spot.removeAllElements();
    }

    public static void borrar_todas_Puntual()
    {
        int i = vector_de_figura_luz_Puntual.size();
        for(int j = 0; j < i; j++)
        {
            Object obj = vector_de_figura_luz_Puntual.elementAt(j);
            if(obj != null)
            {
                Figura_luz figura_luz = (Figura_luz)obj;
                figura_luz.bg.detach();
            }
        }

        vector_de_figura_luz_Puntual.removeAllElements();
    }

    public static void luz_Direccional_On()
    {
        jmi_cambiar_Direccional.setEnabled(true);
        jmi_borrar_Direccional.setEnabled(true);
    }

    public static void luz_Direccional_Off()
    {
        jmi_cambiar_Direccional.setEnabled(false);
        jmi_borrar_Direccional.setEnabled(false);
    }

    public static void luz_Spot_On()
    {
        luz_Puntual_Off();
        jmi_borrar_Spot.setEnabled(true);
        jmi_direccion_Spot.setEnabled(true);
        jmi_angulo_Spot.setEnabled(true);
        jmi_concentracion_Spot.setEnabled(true);
        jmi_atenuacion_Spot.setEnabled(true);
    }

    public static void luz_Spot_Off()
    {
        jmi_borrar_Spot.setEnabled(false);
        jmi_direccion_Spot.setEnabled(false);
        jmi_angulo_Spot.setEnabled(false);
        jmi_concentracion_Spot.setEnabled(false);
        jmi_atenuacion_Spot.setEnabled(false);
    }

    public static void luz_Puntual_On()
    {
        luz_Spot_Off();
        jmi_borrar_Puntual.setEnabled(true);
        jmi_atenuacion_Puntual.setEnabled(true);
    }

    public static void luz_Puntual_Off()
    {
        jmi_borrar_Puntual.setEnabled(false);
        jmi_atenuacion_Puntual.setEnabled(false);
    }

    public static void borrar_luz_Direccional()
    {
        if(figura_luz_Direccional != null)
        {
            figura_luz_Direccional.bg.detach();
            figura_luz_Direccional = null;
            luz_Direccional_Off();
        }
    }
	//Escena
    public static void seleccionar_Figura_luz(Figura_luz figura_luz)
    {
        switch(accion)
        {
        case SELECCIONADO_PUNTUAL: 
            figura_luz_Spot_actual = figura_luz;
            figura_luz_Spot_actual = null;
            luz_Puntual_On();
            break;

        case SELECCIONADO_SPOT: 
            figura_luz_Spot_actual = figura_luz;
            figura_luz_Puntual_actual = null;
            luz_Spot_On();
            break;

        default:
            return;
        }
        accion = NINGUNO;
        Panel_de_texto_estado.set_antiguo();
        apagar_todas_las_luces();
        figura_luz.encender(true);
    }

    public static int get_numero_de_luces_Puntuales()
    {
        int i = vector_de_figura_luz_Puntual.size();
        if(i == NINGUNO)
            return NINGUNO;
        int j = 0;
        for(int k = 0; k < i; k++)
            if(vector_de_figura_luz_Puntual.elementAt(k) != null)
                j++;

        return j;
    }

    public static Figura_luz get_primera_luz_Puntual()
    {
        int i = vector_de_figura_luz_Puntual.size();
        for(int j = 0; j < i; j++)
            if(vector_de_figura_luz_Puntual.elementAt(j) != null)
                return (Figura_luz)vector_de_figura_luz_Puntual.elementAt(j);

        return null;
    }

    public static int get_numero_de_luces_Spot()
    {
        int i = vector_de_figura_luz_Spot.size();
        if(i == 0)
            return 0;
        int j = 0;
        for(int k = 0; k < i; k++)
            if(vector_de_figura_luz_Spot.elementAt(k) != null)
                j++;

        return j;
    }

    public static Figura_luz get_primera_luz_Spot()
    {
        int i = vector_de_figura_luz_Spot.size();
        for(int j = 0; j < i; j++)
            if(vector_de_figura_luz_Spot.elementAt(j) != null)
                return (Figura_luz)vector_de_figura_luz_Spot.elementAt(j);

        return null;
    }

    public static void set_On(boolean flag, Vector vector)
    {
        int i = vector.size();
        for(int j = 0; j < i; j++)
        {
            Object obj = vector.elementAt(j);
            if(obj != null)
            {
                Figura_luz figura_luz = (Figura_luz)obj;
                figura_luz.encender(flag);
            }
        }

    }

    
    
}