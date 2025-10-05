package com.example.tpfinanzas.services.impl;

import com.example.tpfinanzas.dtos.CronogramaResponseDTO;
import com.example.tpfinanzas.dtos.CuotaDTO;
import com.example.tpfinanzas.dtos.GenerarCronogramaRequest;
import com.example.tpfinanzas.services.CronogramaService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CronogramaServiceImpl implements CronogramaService {

    private double tasaMensualDesdeTEA(double tea) {
        return Math.pow(1.0 + tea, 1.0/12.0) - 1.0;
    }

    private double tasaMensualDesdeTNA(double tna, int m) {
        return tna / (double) m;
    }

    private double pagoFrances(double P, double i, int n) {
        if (n == 0) return 0;
        if (Math.abs(i) < 1e-12) return P / n;
        return P * i / (1.0 - Math.pow(1.0 + i, -n));
    }

    // Conserva el día cuando se puede; si no, clampa al último día del mes siguiente
    private LocalDate addOneMonth(LocalDate d) {
        LocalDate next = d.plusMonths(1);
        int day = Math.min(d.getDayOfMonth(), next.lengthOfMonth());
        return LocalDate.of(next.getYear(), next.getMonth(), day);
    }

    @Override
    public CronogramaResponseDTO generar(GenerarCronogramaRequest req) {
        // Defaults seguros
        double P = nvl(req.getMonto(), 0d);
        int n = nvl(req.getPlazoMeses(), 0);
        int gTot = Math.max(0, nvl(req.getGraciaTotal(), 0));
        int gPar = Math.max(0, nvl(req.getGraciaParcial(), 0));
        String tipoTasa = req.getTipoTasa() == null ? "TEA" : req.getTipoTasa();
        double valorTasa = nvl(req.getValorTasa(), 0.0);
        int m = Math.max(1, nvl(req.getCapitalizacion(), 12));

        // Tasa mensual
        double i = "TEA".equalsIgnoreCase(tipoTasa)
                ? tasaMensualDesdeTEA(valorTasa)
                : tasaMensualDesdeTNA(valorTasa, m);

        double saldo = P;
        LocalDate fecha = req.getFechaDesembolso() != null ? req.getFechaDesembolso() : LocalDate.now();
        List<CuotaDTO> filas = new ArrayList<>();
        int periodo = 0;

        // Gracia total: capitaliza intereses
        for (int k = 0; k < gTot; k++) {
            double saldoInicial = saldo;
            double interes = saldoInicial * i;
            saldo += interes; // capitaliza
            periodo++;
            filas.add(CuotaDTO.builder()
                    .periodo(periodo).fecha(fecha)
                    .saldoInicial(r(saldoInicial))
                    .interes(r(interes))
                    .amortizacion(0.0).cuota(0.0)
                    .saldoFinal(r(saldo))
                    .nota("Gracia total (capitalizado)")
                    .build());
            fecha = addOneMonth(fecha);
        }

        // Gracia parcial: paga solo interés
        for (int k = 0; k < gPar; k++) {
            double saldoInicial = saldo;
            double interes = saldoInicial * i;
            periodo++;
            filas.add(CuotaDTO.builder()
                    .periodo(periodo).fecha(fecha)
                    .saldoInicial(r(saldoInicial))
                    .interes(r(interes))
                    .amortizacion(0.0).cuota(r(interes))
                    .saldoFinal(r(saldoInicial))
                    .nota("Gracia parcial (solo interés)")
                    .build());
            fecha = addOneMonth(fecha);
        }

        // Sistema francés
        double cuotaConst = pagoFrances(saldo, i, n);
        for (int t = 1; t <= n; t++) {
            double saldoInicial = saldo;
            double interes = saldoInicial * i;
            double amort = cuotaConst - interes;
            saldo = saldoInicial - amort;

            periodo++;
            filas.add(CuotaDTO.builder()
                    .periodo(periodo).fecha(fecha)
                    .saldoInicial(r(saldoInicial))
                    .interes(r(interes))
                    .amortizacion(r(amort))
                    .cuota(r(cuotaConst))
                    .saldoFinal(r(saldo))
                    .nota("Sistema francés")
                    .build());
            fecha = addOneMonth(fecha);
        }

        return CronogramaResponseDTO.builder()
                .tasaMensual(r(i))
                .cuotaConstante(r(cuotaConst))
                .cronograma(filas)
                .build();
    }

    private int nvl(Integer v, int def) { return v == null ? def : v; }
    private double nvl(Double v, double def) { return v == null ? def : v; }
    private double r(double x) { return Math.round(x * 100.0) / 100.0; }
}
