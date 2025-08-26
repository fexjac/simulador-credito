package com.fex.simulador.service;

import com.fex.simulador.dto.ParcelaDTO;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class AmortizationService {

    private static final MathContext MC = new MathContext(20, RoundingMode.HALF_UP);

    private BigDecimal round2(BigDecimal v) {
        return v.setScale(2, RoundingMode.HALF_UP);
    }

    public List<ParcelaDTO> sac(BigDecimal principal, BigDecimal i, int n) {
        List<ParcelaDTO> out = new ArrayList<>(n);
        BigDecimal amortConst = principal.divide(BigDecimal.valueOf(n), MC);
        amortConst = round2(amortConst);

        BigDecimal saldo = principal;
        for (int k = 1; k <= n; k++) {
            BigDecimal juros = round2(saldo.multiply(i, MC));
            BigDecimal prest = round2(amortConst.add(juros));
            out.add(new ParcelaDTO(k, amortConst, juros, prest));
            saldo = saldo.subtract(amortConst, MC);
        }
        return out;
    }

    public List<ParcelaDTO> price(BigDecimal principal, BigDecimal i, int n) {
        List<ParcelaDTO> out = new ArrayList<>(n);


        double id = i.doubleValue();
        double P = principal.doubleValue();
        double fator = Math.pow(1.0 + id, n);
        double A = P * id * fator / (fator - 1.0);
        BigDecimal prestConst = round2(BigDecimal.valueOf(A));

        BigDecimal saldo = principal;
        for (int k = 1; k <= n; k++) {
            BigDecimal juros = round2(saldo.multiply(i, MC));
            BigDecimal amort = round2(prestConst.subtract(juros, MC));
            out.add(new ParcelaDTO(k, amort, juros, prestConst));
            saldo = saldo.subtract(amort, MC);
        }
        return out;
    }
}
