package com.shvaiale.irishpub.mapper;

public interface Mapper<F, T> {
    T map(F from);
}
