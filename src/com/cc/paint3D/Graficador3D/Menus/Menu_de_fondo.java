
package com.cc.paint3D.Graficador3D.Menus;

import com.cc.paint3D.Graficador3D.Graficador3D;
import com.cc.paint3D.Graficador3D.Escena;
import com.cc.paint3D.Graficador3D.interfaces.Seleccion_multiple;
import com.cc.paint3D.Graficador3D.IO.Manejador_de_archivos;
import com.cc.paint3D.Graficador3D.G3D.Datos_utiles;
import com.cc.paint3D.Graficador3D.Paneles.Panel_Principal;
import com.cc.paint3D.Graficador3D.ES.ES_Utiles;

import com.sun.j3d.utils.image.TextureLoader;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.media.j3d.*;
import javax.swing.JMenu;

public class Menu_de_fondo extends JMenu
    implements Seleccion_multiple
{
	public static final int NINGUNO = 0;
    public static final int SENCILLA = 1;
    public static final int ESCALADA = 2;
    public static final int MOSAICO = 3;
    public static Menu_de_fondo handle;
    public String opciones[] = {
        Graficador3D.NO_IMAGE , Graficador3D.UNSCALED_IMAGE , Graficador3D.SCALED_IMAGE , Graficador3D.TILED_IMAGE
    };
    public static Menu_de_seleccion_multiple menu_de_seleccion_multiple_image;
    public static BranchGroup bg;
    public static File path;
	
    public Menu_de_fondo()
    {
        super(Graficador3D.BACKGROUND);
        handle = this;
        add(new Menu_de_color_de_fondo());
        add(new Menu_de_color_ambiente());
        add(new Menu_de_color_de_niebla());
        add(menu_de_seleccion_multiple_image = new Menu_de_seleccion_multiple(Graficador3D.BACKGROUND_IMAGE, opciones, this));
    }

    public static void borrar_fondo()
    {
        if(bg != null)
            bg.detach();
        bg = null;
    }

    public void llamada_multiple(Menu_de_seleccion_multiple menu_de_seleccion_multiple, int i)
    {
        if(i == NINGUNO)
        {
            if(bg != null)
            {
                path = null;
                bg.detach();
                bg = null;
                System.gc();
            }
            return;
        }
        File file = Manejador_de_archivos.get_buscador_de_imagen(Graficador3D.CHOOSE_BACKGROUND_IMAGE);
        if(file == null)
        {
            return;
        } else
        {
            agregar_Background(i, file);
            return;
        }
    }

    public static void agregar_Background(int i, File file)
    {
        BufferedImage bufferedimage = ES_Utiles.get_BufferedImage(file);
        if(bufferedimage == null)
            return;
        Dimension dimension = Panel_Principal.handle.getSize();
        Object obj = null;
        Background background = null;
        switch(i)
        {
        default:
            break;

        case SENCILLA: 
            TextureLoader textureloader = new TextureLoader(bufferedimage);
            background = new Background(textureloader.getImage());
            break;

        case ESCALADA: 
            TextureLoader textureloader1 = new TextureLoader(bufferedimage);
            background = new Background(textureloader1.getScaledImage(dimension.width, dimension.height));
            break;

        case MOSAICO: 
            BufferedImage bufferedimage1 = new BufferedImage(dimension.width, dimension.height, 1);
            int j = bufferedimage.getHeight();
            int k = bufferedimage.getWidth();
            java.awt.Graphics2D graphics2d = bufferedimage1.createGraphics();
            for(int l = 0; l < dimension.height; l += j)
            {
                for(int i1 = 0; i1 < dimension.width; i1 += k)
                    graphics2d.drawImage(bufferedimage, i1, l, null);

            }

            bufferedimage = bufferedimage1;
            TextureLoader textureloader2 = new TextureLoader(bufferedimage);
            background = new Background(textureloader2.getImage());
            break;
        }
        borrar_fondo();
        Menu_de_color_de_fondo.borrar_este();
        path = file;
        bg = new BranchGroup();
        bg.setCapability(17);
        background.setApplicationBounds(Datos_utiles.boundingSphere);
        bg.addChild(background);
        Escena.bg.addChild(bg);
        System.gc();
    }

    
}