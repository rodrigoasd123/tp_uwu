package com.example.tpfinanzas.services;

import com.example.tpfinanzas.entities.Cronograma;
import java.util.List;
import java.util.Map;

public interface IndicadoresService {
    double npv(double tasaMensual, List<Double> flujos);
    double irr(List<Double> flujos);                 // mensual
    double effAnnual(double tasaMensual);            // (1+i)^12 - 1
    Map<String, Double> durationConvexity(double y, List<Double> flujos);
    Map<String, Double> indicadoresDesdeCronograma(List<Cronograma> filas, double gastosFijosMensuales);
}
