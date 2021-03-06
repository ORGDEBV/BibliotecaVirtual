package vb.dao;

import java.util.ArrayList;
import java.util.List;
import vb.entidad.Editorial;
import vb.servicio.entidadService;
import vb.util.sql;

public class editorialDao implements entidadService<Editorial> {

    sql conector = new sql();

    @Override
    public int crearEntidad(Editorial ed) {
        int n = 0;
        ArrayList<Object[]> data = null;
        String[] parametros = new String[4];
        parametros[0] = "";
        parametros[1] = ed.getNOMBRE_EDITORIAL();
        parametros[2] = "EDITORIAL_INS";
        parametros[3] = ed.getID_BIBLIOTECA_REGISTRO();
        data = conector.execProcedure("BV.SP_MANTENIMIENTO_EDITORIAL", parametros);
        for (Object[] dat : data) {
            if (dat[0].toString().equals("1")) {
                n = 1;
            }
        }
        return n;
    }

    @Override
    public int eliminarEntidad(int codigo) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int actualizarEntidad(Editorial ed) {
        int n = 0;
        List<Object[]> listaIn;
        String[] parametros = new String[4];
        parametros[0] = String.valueOf(ed.getID_EDITORIAL());
        parametros[1] = ed.getNOMBRE_EDITORIAL();
        parametros[2] = "EDITORIAL_UPD";
        parametros[3] = "";
        listaIn = conector.execProcedure("BV.SP_MANTENIMIENTO_EDITORIAL", parametros);
        for (Object[] dato : listaIn) {
            if (dato[0].toString().equals("1")) {
                n = 1;
            }
        }
        return n;
    }

    @Override
    public List<Editorial> obtenerEntidades() {
        Editorial editorial = null;
        List<Object[]> objEditorial;
        List<Editorial> listEditorial = new ArrayList<>();
        String[] parametros = new String[4];
        parametros[0] = "";
        parametros[1] = "";
        parametros[2] = "EDITORIAL_LIST";
        parametros[3] = "";
        objEditorial = conector.execProcedure("BV.SP_MANTENIMIENTO_EDITORIAL", parametros);
        for (Object[] obj : objEditorial) {
            editorial = new Editorial();
            editorial.setID_EDITORIAL(Integer.parseInt(obj[0].toString()));
            editorial.setNOMBRE_EDITORIAL(obj[1].toString());
            listEditorial.add(editorial);
        }
        return listEditorial;
    }

    @Override
    public Editorial buscarEntidad(int codigo) {
        Editorial editorial = null;
        String[] parametros = new String[4];
        parametros[0] = String.valueOf(codigo);
        parametros[1] = "";
        parametros[2] = "EDITORIAL_FIND";
        parametros[3] = "";
        List<Object[]> listaIn = conector.execProcedure("BV.SP_MANTENIMIENTO_EDITORIAL", parametros);
        for (Object[] dato : listaIn) {
            editorial = new Editorial();
            editorial.setID_EDITORIAL(Integer.parseInt(dato[0].toString()));
            editorial.setNOMBRE_EDITORIAL(dato[1].toString());
        }
        return editorial;
    }

    public List<Object[]> llenaComboEditorial(String idBiblioteca) {
        String[] parametros = new String[4];
        parametros[0] = "";
        parametros[1] = "";
        parametros[2] = "EDITORIAL_LIST";
        parametros[3] = idBiblioteca;
        List<Object[]> list = conector.execProcedure("BV.SP_MANTENIMIENTO_EDITORIAL", parametros);
        return list;
    }

}
