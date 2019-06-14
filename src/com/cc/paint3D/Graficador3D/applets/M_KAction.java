
import java.awt.event.KeyEvent;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3f;



public class M_KAction{

    
    protected TransformGroup _tg;
    
    // paso de las operaciones de traslacion.
     
    private float _paso;

    
     // angulo para las operaciones de rotacion.
     
    private double _angulo;

    
    public M_KAction(TransformGroup TG, float paso_tras,
            double paso_rot ){
        super();
        _tg = TG;
        _paso = paso_tras;
        _angulo = paso_rot;
    }

    
    // procesamos lo eventos del teclado
    
    public void processKeyEvent(int keyCode) {
        Vector3f vec;
        Transform3D tr = new Transform3D();
        Transform3D tgr = new Transform3D();
        _tg.getTransform(tgr);         // obtenemos una copia de un componente de transformacion
        // decide que key (tecla) ha sido activada 
        switch (keyCode) {
            case KeyEvent.VK_UP:        // zoom in
                tr.setTranslation(new Vector3f(0.0f,0.0f,-_paso));
                break;
            case KeyEvent.VK_DOWN:      // zoom out
                tr.setTranslation(new Vector3f(0.0f,0.0f,_paso));
                break;
            case KeyEvent.VK_LEFT:      // rotar camara izquierda
                tr.setTranslation(new Vector3f(-_paso,0.0f,0.0f));
                break;
            case KeyEvent.VK_RIGHT:     // rotar camara derecha
                tr.setTranslation(new Vector3f(_paso,0.0f,0.0f));
                break;
            case KeyEvent.VK_PAGE_UP:   // rotar camara arriba 
                tr.rotX(Math.toRadians(_angulo));
                break;
            case KeyEvent.VK_PAGE_DOWN: // rotar camara abajo
                tr.rotX(-Math.toRadians(_angulo));
                break;
            case KeyEvent.VK_X: // translacion -X
                {	
                	tr.setTranslation(new Vector3f(-1f,0.0f,0.0f));
                }
                break;
            case KeyEvent.VK_C: // translacion -y
                {
                	tr.setTranslation(new Vector3f(0.0f,-1f,0.0f));	
                }
                break;
            case KeyEvent.VK_Z: // translacion -z
                {	
                	tr.setTranslation(new Vector3f(0.0f,0.0f,-1f));
                }
                break;
                
            case KeyEvent.VK_S: // translacion +X
                {	
                	tr.setTranslation(new Vector3f(1f,0.0f,0.0f));
                }
                break;
            case KeyEvent.VK_D: // translacion +Y
                {	                	                	
                	tr.setTranslation(new Vector3f(0.0f,1f,0.0f));	
                }
                break;
            case KeyEvent.VK_A: // translacion +Z
                {	
                	tr.setTranslation(new Vector3f(0.0f,0.0f,1f));                	
            	}
                break;               
                
            default:                    // default: no hacemos nada
                tr.set(new Vector3f(0.0f, 0.0f, 0.0f));
        }
        tgr.mul(tr);                    // calculamos la nueva transformacion
        _tg.setTransform(tgr);         // fijamos la nueva transformacion
    }
}



