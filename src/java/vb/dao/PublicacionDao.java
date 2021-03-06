/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vb.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import vb.dto.PublicacionDto;
import vb.util.sql;

/**
 *
 * @author virtual
 */
public class PublicacionDao {

    sql conector = new sql();

    public ArrayList<PublicacionDto> listPublicacion(String ID_PERFIL, String ID_BIBLIOTECA, String visibilidad) {
        String[] parametros = new String[3];
        ArrayList<PublicacionDto> lPublicacion = new ArrayList<PublicacionDto>();
        PublicacionDto dto = null;
        parametros[0] = ID_PERFIL;
        parametros[1] = ID_BIBLIOTECA;
        parametros[2] = visibilidad;
        ArrayList<Object[]> data = conector.execProcedure("[BV].[SP_LISTAR_DOCUMENTAL_PUBLICADO]", parametros);
        for (Object[] datos : data) {
            dto = new PublicacionDto();
            dto.setID_PUBLICACION(datos[0].toString());
            dto.setID_DOCUMENTAL(datos[1].toString());
            dto.setOTRO(datos[2].toString());
            dto.setTITULO(datos[3].toString());
            dto.setURL(datos[4].toString());
            dto.setNRO_VISITAS(Integer.parseInt(datos[5].toString()));
            dto.setID_USUARIO(Integer.parseInt(datos[6].toString()));
            dto.setNOMBRE(datos[7].toString());
            dto.setAPELLIDO_PATERNO(datos[8].toString());
            dto.setAPELLIDO_MATERNO(datos[9].toString());
            dto.setFECHA_PUBLICACION((Date) datos[10]);
            dto.setVISIBLE(Integer.parseInt(datos[11].toString()));
            if (dto.getVISIBLE() == 1) {
                dto.setCLASS_VISIBLE("GreenBack");
                dto.setICONO_VISIBLE("fa fa-eye");
            } else if (dto.getVISIBLE() == 0) {
                dto.setCLASS_VISIBLE("RedBack");
                dto.setICONO_VISIBLE("fa fa-eye-slash");
            }
            lPublicacion.add(dto);
        }
        return lPublicacion;
    }

    public int cambiaVisibilidad(PublicacionDto pub) {
        int update = 0;
        String[] parametros = new String[2];
        parametros[0] = pub.getID_PUBLICACION();
        if (pub.getVISIBLE() == 1) {
            parametros[1] = "0";
        } else if (pub.getVISIBLE() == 0) {
            parametros[1] = "1";
        }
        ArrayList<Object[]> data = conector.execProcedure("[BV].[SP_UPDATE_PUBLICACION_VISIBLE]", parametros);
        for (Object[] datos : data) {
            update = Integer.parseInt(datos[0].toString());
        }
        return update;
    }

}
