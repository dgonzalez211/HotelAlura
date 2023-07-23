package com.alura.hotel.services.request;

import java.util.function.Consumer;

@FunctionalInterface
public interface RequestResultSingle<T> extends Consumer<T> {

}
