package com.example.myapplication;

import java.util.Comparator;

public class SortMovimientos implements Comparator<Movimiento> {

    @Override
    public int compare(Movimiento mov_1, Movimiento mov_2) {

        return mov_1.getFechaCompleta().compareTo(mov_2.getFechaCompleta());
    }

    @Override
    public Comparator<Movimiento> reversed() {
        return Comparator.super.reversed();
    }
}
