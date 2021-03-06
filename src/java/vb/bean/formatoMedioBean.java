package vb.bean;

import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import vb.dao.formatoMedioDao;
import vb.entidad.FormatoMedio;

@ManagedBean
@ViewScoped
public class formatoMedioBean {

    private formatoMedioDao formatoMedioDao = new formatoMedioDao();
    private List<FormatoMedio> cboFormatoMedio;
    private FormatoMedio formatomedio = new FormatoMedio();
    
    public formatoMedioBean() {
        rfrhCboFrmtMedio();
    }

    public FormatoMedio getFormatomedio() {
        return formatomedio;
    }

    public void setFormatomedio(FormatoMedio formatomedio) {
        this.formatomedio = formatomedio;
    }

    public List<FormatoMedio> getCboFormatoMedio() {
        
        return cboFormatoMedio;
    }
    
    public void rfrhCboFrmtMedio(){
        List<Object[]> lista = formatoMedioDao.llenaComboFormatoMedio();
        cboFormatoMedio = new ArrayList<FormatoMedio>();
        if (lista != null) {
            for (Object[] fila : lista) {
                FormatoMedio entformatomedio = new FormatoMedio();
                entformatomedio.setID_FORMATO_MEDIO(Integer.parseInt(fila[0].toString()));
                entformatomedio.setDESCRIPCION(fila[1].toString());
                cboFormatoMedio.add(entformatomedio);
            }
        }
        RequestContext.getCurrentInstance().update("frmAddDocumental:cboFormatoMedio");
    }
    
    public void setCboFormatoMedio(List<FormatoMedio> cboFormatoMedio) {
        this.cboFormatoMedio = cboFormatoMedio;
    }
    
    public void agregarFormatoMedio(){
         int n = formatoMedioDao.crearEntidad(formatomedio);
         if(n == 0){
            FacesContext.getCurrentInstance().addMessage("gMensaje", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Ocurrió un error al ejecutar este proceso."));
        }else{
            RequestContext rc = RequestContext.getCurrentInstance();
            rc.execute("PF('dlgAddFormatoMedio').hide()");
            rc.execute("PF('cboFormatoMedio').selectValue(PF('cboFormatoMedio').options.length)");
            FacesContext.getCurrentInstance().addMessage("gMensaje", new FacesMessage(FacesMessage.SEVERITY_INFO,"Inserción correcta", "Se registro formato correctamente."));
        }
    }
    
}
