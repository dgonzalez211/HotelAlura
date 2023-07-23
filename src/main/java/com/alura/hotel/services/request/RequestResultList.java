package com.alura.hotel.services.request;

import java.util.List;
import java.util.function.Consumer;

@FunctionalInterface
public interface RequestResultList<T> extends Consumer<List<T>> {

}
