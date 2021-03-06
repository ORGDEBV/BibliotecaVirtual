/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vb.bean;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.primefaces.context.RequestContext;
import vb.dao.PublicacionDao;
import vb.dao.perfilDocumentalDetalleDao;
import vb.dto.PublicacionDto;

/**
 *
 * @author virtual
 */
@ManagedBean
@ViewScoped
public class publicacionBean {

    /**
     * Creates a new instance of publicacionBean
     */
    private perfilDocumentalDetalleDao pddDao;
    private List<PublicacionDto> lpublicacion = new ArrayList<>();
    private List<PublicacionDto> lpublicacionfiltro = new ArrayList<>();
    private PublicacionDao pubDao;
    String perfilControl;
    private List<SelectItem> cboPerfiles;
    String visibilidad;
    private List<SelectItem> cboVisibilidad;

    public publicacionBean() {
        pubDao = new PublicacionDao();
        pddDao = new perfilDocumentalDetalleDao();
    }

    public List<PublicacionDto> getLpublicacion() {
        return lpublicacion;
    }

    public void setLpublicacion(List<PublicacionDto> lpublicacion) {
        this.lpublicacion = lpublicacion;
    }

    public String getPerfilControl() {
        return perfilControl;
    }

    public void setPerfilControl(String perfilControl) {
        this.perfilControl = perfilControl;
    }

    public List<SelectItem> getCboPerfiles() {
        List<Object[]> lista = pddDao.obtenerPerfiles();
        cboPerfiles = new ArrayList<>();
        if (lista != null) {
            for (Object[] fila : lista) {
                cboPerfiles.add(new SelectItem(fila[0], fila[1].toString()));
            }
        }

        return cboPerfiles;
    }

    public void setCboPerfiles(List<SelectItem> cboPerfiles) {
        this.cboPerfiles = cboPerfiles;
    }

    public String getVisibilidad() {
        return visibilidad;
    }

    public void setVisibilidad(String visibilidad) {
        this.visibilidad = visibilidad;
    }

    public List<SelectItem> getCboVisibilidad() {
        cboVisibilidad= new ArrayList<>();
        cboVisibilidad.add(new SelectItem("1","Visibles"));
        cboVisibilidad.add(new SelectItem("0","No Visibles"));
        return cboVisibilidad;
    }

    public void setCboVisibilidad(List<SelectItem> cboVisibilidad) {
        this.cboVisibilidad = cboVisibilidad;
    }

    public void listarDocumentalPublicado() {
        String idBiblioteca = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("personalidBibliotecaFuente").toString();
        lpublicacion = pubDao.listPublicacion(perfilControl, idBiblioteca, visibilidad);
        RequestContext.getCurrentInstance().update("frmPub:tblPub");
    }

    public void cambiaVisiblidad(PublicacionDto pub) {
        int update = pubDao.cambiaVisibilidad(pub);
        listarDocumentalPublicado();
    }

}
