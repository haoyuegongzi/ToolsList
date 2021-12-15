package com.mydemo.toolslist.executor;

import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 作者：Created by chen1 on 2020/3/22.
 * 邮箱：chen126jie@163.com
 * TODO：
 */
public class ThreadPool implements BlockingQueue {
    @Override
    public boolean add(Object o) {
        return false;
    }

    @Override
    public boolean offer(Object o) {
        return false;
    }

    @Override
    public Object remove() {
        return null;
    }

    @Nullable
    @Override
    public Object poll() {
        return null;
    }

    @Override
    public Object element() {
        return null;
    }

    @Nullable
    @Override
    public Object peek() {
        return null;
    }

    @Override
    public void put(@NonNull Object o) throws InterruptedException {

    }

    @Override
    public boolean offer(Object o, long timeout, @NonNull TimeUnit unit) throws InterruptedException {
        return false;
    }

    @NonNull
    @Override
    public Object take() throws InterruptedException {
        return null;
    }

    @Override
    public Object poll(long timeout, @NonNull TimeUnit unit) throws InterruptedException {
        return null;
    }

    @Override
    public int remainingCapacity() {
        return 0;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean addAll(@NonNull Collection c) {
        return false;
    }

    @Override
    public boolean removeIf(@NonNull Predicate filter) {
        return false;
    }

    @Override
    public void clear() {

    }

    @NonNull
    @Override
    public Spliterator spliterator() {
        return null;
    }

    @NonNull
    @Override
    public Stream stream() {
        return null;
    }

    @NonNull
    @Override
    public Stream parallelStream() {
        return null;
    }

    @Override
    public boolean retainAll(@NonNull Collection c) {
        return false;
    }

    @Override
    public boolean removeAll(@NonNull Collection c) {
        return false;
    }

    @Override
    public boolean containsAll(@NonNull Collection c) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @NonNull
    @Override
    public Iterator iterator() {
        return null;
    }

    @Override
    public void forEach(@NonNull Consumer action) {

    }

    @NonNull
    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @NonNull
    @Override
    public Object[] toArray(@NonNull Object[] a) {
        return new Object[0];
    }

    @Override
    public int drainTo(@NonNull Collection c) {
        return 0;
    }

    @Override
    public int drainTo(@NonNull Collection c, int maxElements) {
        return 0;
    }
}
