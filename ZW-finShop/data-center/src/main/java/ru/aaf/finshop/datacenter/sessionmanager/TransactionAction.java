package ru.aaf.finshop.datacenter.sessionmanager;

import java.util.function.Supplier;

public interface TransactionAction<T> extends Supplier<T> {}
