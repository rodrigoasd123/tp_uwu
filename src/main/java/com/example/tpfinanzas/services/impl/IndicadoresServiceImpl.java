package com.example.tpfinanzas.services.impl;

import com.example.tpfinanzas.entities.Cronograma;
import com.example.tpfinanzas.services.IndicadoresService;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class IndicadoresServiceImpl implements IndicadoresService {

    @Override
    public double npv(double i, List<Double> cf) {
        double v = 0.0;
        for (int t=0; t<cf.size(); t++) v += cf.get(t) / Math.pow(1+i, t);
        return round(v);
    }

    @Override
    public double irr(List<Double> cf) {
        double lo=-0.9, hi=1.0;
        for (int k=0; k<200; k++){
            double mid=(lo+hi)/2.0;
            double v=npv(mid, cf);
            double vlo=npv(lo, cf);
            if (Math.abs(v) < 1e-9) return round(mid);
            if (Math.signum(v)==Math.signum(vlo)) lo=mid; else hi=mid;
        }
        return round((lo+hi)/2.0);
    }

    @Override
    public double effAnnual(double im){ return round(Math.pow(1+im,12)-1); }

    @Override
    public Map<String, Double> durationConvexity(double y, List<Double> cf) {
        double pv=0, w=0, conv=0;
        for (int t=0; t<cf.size(); t++){
            double pv_t = cf.get(t)/Math.pow(1+y, t);
            pv += pv_t; w += t*pv_t; conv += t*(t+1)*pv_t;
        }
        double D = w/pv;
        double Dm = D/(1+y);
        double C = conv/(pv*Math.pow(1+y,2));
        Map<String, Double> m = new LinkedHashMap<>();
        m.put("duracion_meses", round(D));
        m.put("duracion_modificada_mensual", round(Dm));
        m.put("convexidad_mensual", round(C));
        m.put("duracion_anios", round(D/12.0));
        m.put("duracion_modificada_anual", round(Dm/12.0));
        return m;
    }

    @Override
    public Map<String, Double> indicadoresDesdeCronograma(List<Cronograma> filas, double gastosFijos) {
        if (filas.isEmpty()) return Map.of();
        double monto = filas.get(0).getSaldoInicial();
        List<Double> cf = new ArrayList<>();
        cf.add(monto);
        for (var f: filas) cf.add(-(f.getCuota() + gastosFijos));
        double irrM = irr(cf);
        Map<String, Double> out = new LinkedHashMap<>();
        out.put("irr_mensual", irrM);
        out.put("tcea_aprox", effAnnual(irrM));
        out.put("npv_0_9pct_mensual", npv(0.009, cf));
        out.putAll(durationConvexity(irrM, cf));
        return out;
    }

    private double round(double x){ return Math.round(x*100000.0)/100000.0; }
}
