//package com.mydemo.toolslist;
//
//import io.reactivex.BackpressureStrategy;
//import io.reactivex.Completable;
//import io.reactivex.CompletableSource;
//import io.reactivex.Emitter;
//import io.reactivex.Flowable;
//import io.reactivex.Maybe;
//import io.reactivex.MaybeSource;
//import io.reactivex.Notification;
//import io.reactivex.ObservableConverter;
//import io.reactivex.ObservableOnSubscribe;
//import io.reactivex.ObservableOperator;
//import io.reactivex.ObservableSource;
//import io.reactivex.ObservableTransformer;
//import io.reactivex.Observer;
//import io.reactivex.Scheduler;
//import io.reactivex.Single;
//import io.reactivex.SingleSource;
//import io.reactivex.annotations.*;
//import io.reactivex.disposables.Disposable;
//import io.reactivex.exceptions.Exceptions;
//import io.reactivex.functions.*;
//import io.reactivex.internal.functions.*;
//import io.reactivex.internal.fuseable.ScalarCallable;
//import io.reactivex.internal.fuseable.SimpleQueue;
//import io.reactivex.internal.observers.*;
//import io.reactivex.internal.operators.flowable.*;
//import io.reactivex.internal.operators.mixed.*;
//import io.reactivex.internal.operators.observable.*;
//import io.reactivex.internal.util.*;
//import io.reactivex.observables.*;
//import io.reactivex.observers.*;
//import io.reactivex.plugins.RxJavaPlugins;
//import io.reactivex.schedulers.*;
//import org.reactivestreams.Publisher;
//
//import java.util.*;
//import java.util.concurrent.Callable;
//import java.util.concurrent.Future;
//import java.util.concurrent.TimeUnit;
//
//public abstract class Observable<T> implements ObservableSource<T> {
//
//    @CheckReturnValue
//    @NonNull
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> amb(Iterable<? extends ObservableSource<? extends T>> sources) {
//        ObjectHelper.requireNonNull(sources, "sources is null");
//        return RxJavaPlugins.onAssembly(new ObservableAmb<T>(null, sources));
//    }
//
//    @SuppressWarnings("unchecked")
//    @CheckReturnValue
//    @NonNull
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> ambArray(ObservableSource<? extends T>... sources) {
//        ObjectHelper.requireNonNull(sources, "sources is null");
//        int len = sources.length;
//        if (len == 0) {
//            return empty();
//        }
//        if (len == 1) {
//            return (Observable<T>) wrap(sources[0]);
//        }
//        return RxJavaPlugins.onAssembly(new ObservableAmb<T>(sources, null));
//    }
//
//    public static int bufferSize() {
//        return Flowable.bufferSize();
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T, R> Observable<R> combineLatest(Function<? super Object[], ? extends R> combiner, int bufferSize, ObservableSource<? extends T>... sources) {
//        return combineLatest(sources, combiner, bufferSize);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T, R> Observable<R> combineLatest(Iterable<? extends ObservableSource<? extends T>> sources,
//                                                                   Function<? super Object[], ? extends R> combiner) {
//        return combineLatest(sources, combiner, bufferSize());
//    }
//
//    @CheckReturnValue
//    @NonNull
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T, R> Observable<R> combineLatest(Iterable<? extends ObservableSource<? extends T>> sources,
//                                                                   Function<? super Object[], ? extends R> combiner, int bufferSize) {
//        ObjectHelper.requireNonNull(sources, "sources is null");
//        ObjectHelper.requireNonNull(combiner, "combiner is null");
//        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
//
//        // the queue holds a pair of values so we need to double the capacity
//        int s = bufferSize << 1;
//        return RxJavaPlugins.onAssembly(new ObservableCombineLatest<T, R>(null, sources, combiner, s, false));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T, R> Observable<R> combineLatest(ObservableSource<? extends T>[] sources,
//                                                                   Function<? super Object[], ? extends R> combiner) {
//        return combineLatest(sources, combiner, bufferSize());
//    }
//
//    @CheckReturnValue
//    @NonNull
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T, R> Observable<R> combineLatest(ObservableSource<? extends T>[] sources,
//                                                                   Function<? super Object[], ? extends R> combiner, int bufferSize) {
//        ObjectHelper.requireNonNull(sources, "sources is null");
//        if (sources.length == 0) {
//            return empty();
//        }
//        ObjectHelper.requireNonNull(combiner, "combiner is null");
//        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
//
//        // the queue holds a pair of values so we need to double the capacity
//        int s = bufferSize << 1;
//        return RxJavaPlugins.onAssembly(new ObservableCombineLatest<T, R>(sources, null, combiner, s, false));
//    }
//
//    @SuppressWarnings("unchecked")
//    @CheckReturnValue
//    @NonNull
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T1, T2, R> Observable<R> combineLatest(
//            ObservableSource<? extends T1> source1, ObservableSource<? extends T2> source2,
//            BiFunction<? super T1, ? super T2, ? extends R> combiner) {
//        ObjectHelper.requireNonNull(source1, "source1 is null");
//        ObjectHelper.requireNonNull(source2, "source2 is null");
//        return combineLatest(Functions.toFunction(combiner), bufferSize(), source1, source2);
//    }
//
//    @SuppressWarnings("unchecked")
//    @CheckReturnValue
//    @NonNull
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T1, T2, T3, R> Observable<R> combineLatest(
//            ObservableSource<? extends T1> source1, ObservableSource<? extends T2> source2,
//            ObservableSource<? extends T3> source3,
//            Function3<? super T1, ? super T2, ? super T3, ? extends R> combiner) {
//        ObjectHelper.requireNonNull(source1, "source1 is null");
//        ObjectHelper.requireNonNull(source2, "source2 is null");
//        ObjectHelper.requireNonNull(source3, "source3 is null");
//        return combineLatest(Functions.toFunction(combiner), bufferSize(), source1, source2, source3);
//    }
//
//    @SuppressWarnings("unchecked")
//    @CheckReturnValue
//    @NonNull
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T1, T2, T3, T4, R> Observable<R> combineLatest(
//            ObservableSource<? extends T1> source1, ObservableSource<? extends T2> source2,
//            ObservableSource<? extends T3> source3, ObservableSource<? extends T4> source4,
//            Function4<? super T1, ? super T2, ? super T3, ? super T4, ? extends R> combiner) {
//        ObjectHelper.requireNonNull(source1, "source1 is null");
//        ObjectHelper.requireNonNull(source2, "source2 is null");
//        ObjectHelper.requireNonNull(source3, "source3 is null");
//        ObjectHelper.requireNonNull(source4, "source4 is null");
//        return combineLatest(Functions.toFunction(combiner), bufferSize(), source1, source2, source3, source4);
//    }
//
//    @SuppressWarnings("unchecked")
//    @CheckReturnValue
//    @NonNull
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T1, T2, T3, T4, T5, R> Observable<R> combineLatest(
//            ObservableSource<? extends T1> source1, ObservableSource<? extends T2> source2,
//            ObservableSource<? extends T3> source3, ObservableSource<? extends T4> source4,
//            ObservableSource<? extends T5> source5,
//            Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends R> combiner) {
//        ObjectHelper.requireNonNull(source1, "source1 is null");
//        ObjectHelper.requireNonNull(source2, "source2 is null");
//        ObjectHelper.requireNonNull(source3, "source3 is null");
//        ObjectHelper.requireNonNull(source4, "source4 is null");
//        ObjectHelper.requireNonNull(source5, "source5 is null");
//        return combineLatest(Functions.toFunction(combiner), bufferSize(), source1, source2, source3, source4, source5);
//    }
//
//    @SuppressWarnings("unchecked")
//    @CheckReturnValue
//    @NonNull
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T1, T2, T3, T4, T5, T6, R> Observable<R> combineLatest(
//            ObservableSource<? extends T1> source1, ObservableSource<? extends T2> source2,
//            ObservableSource<? extends T3> source3, ObservableSource<? extends T4> source4,
//            ObservableSource<? extends T5> source5, ObservableSource<? extends T6> source6,
//            Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? extends R> combiner) {
//        ObjectHelper.requireNonNull(source1, "source1 is null");
//        ObjectHelper.requireNonNull(source2, "source2 is null");
//        ObjectHelper.requireNonNull(source3, "source3 is null");
//        ObjectHelper.requireNonNull(source4, "source4 is null");
//        ObjectHelper.requireNonNull(source5, "source5 is null");
//        ObjectHelper.requireNonNull(source6, "source6 is null");
//        return combineLatest(Functions.toFunction(combiner), bufferSize(), source1, source2, source3, source4, source5, source6);
//    }
//
//    @SuppressWarnings("unchecked")
//    @CheckReturnValue
//    @NonNull
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T1, T2, T3, T4, T5, T6, T7, R> Observable<R> combineLatest(
//            ObservableSource<? extends T1> source1, ObservableSource<? extends T2> source2,
//            ObservableSource<? extends T3> source3, ObservableSource<? extends T4> source4,
//            ObservableSource<? extends T5> source5, ObservableSource<? extends T6> source6,
//            ObservableSource<? extends T7> source7,
//            Function7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? extends R> combiner) {
//        ObjectHelper.requireNonNull(source1, "source1 is null");
//        ObjectHelper.requireNonNull(source2, "source2 is null");
//        ObjectHelper.requireNonNull(source3, "source3 is null");
//        ObjectHelper.requireNonNull(source4, "source4 is null");
//        ObjectHelper.requireNonNull(source5, "source5 is null");
//        ObjectHelper.requireNonNull(source6, "source6 is null");
//        ObjectHelper.requireNonNull(source7, "source7 is null");
//        return combineLatest(Functions.toFunction(combiner), bufferSize(), source1, source2, source3, source4, source5, source6, source7);
//    }
//
//    @SuppressWarnings("unchecked")
//    @CheckReturnValue
//    @NonNull
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T1, T2, T3, T4, T5, T6, T7, T8, R> Observable<R> combineLatest(
//            ObservableSource<? extends T1> source1, ObservableSource<? extends T2> source2,
//            ObservableSource<? extends T3> source3, ObservableSource<? extends T4> source4,
//            ObservableSource<? extends T5> source5, ObservableSource<? extends T6> source6,
//            ObservableSource<? extends T7> source7, ObservableSource<? extends T8> source8,
//            Function8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? extends R> combiner) {
//        ObjectHelper.requireNonNull(source1, "source1 is null");
//        ObjectHelper.requireNonNull(source2, "source2 is null");
//        ObjectHelper.requireNonNull(source3, "source3 is null");
//        ObjectHelper.requireNonNull(source4, "source4 is null");
//        ObjectHelper.requireNonNull(source5, "source5 is null");
//        ObjectHelper.requireNonNull(source6, "source6 is null");
//        ObjectHelper.requireNonNull(source7, "source7 is null");
//        ObjectHelper.requireNonNull(source8, "source8 is null");
//        return combineLatest(Functions.toFunction(combiner), bufferSize(), source1, source2, source3, source4, source5, source6, source7, source8);
//    }
//
//    @SuppressWarnings("unchecked")
//    @CheckReturnValue
//    @NonNull
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> Observable<R> combineLatest(
//            ObservableSource<? extends T1> source1, ObservableSource<? extends T2> source2,
//            ObservableSource<? extends T3> source3, ObservableSource<? extends T4> source4,
//            ObservableSource<? extends T5> source5, ObservableSource<? extends T6> source6,
//            ObservableSource<? extends T7> source7, ObservableSource<? extends T8> source8,
//            ObservableSource<? extends T9> source9,
//            Function9<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? extends R> combiner) {
//        ObjectHelper.requireNonNull(source1, "source1 is null");
//        ObjectHelper.requireNonNull(source2, "source2 is null");
//        ObjectHelper.requireNonNull(source3, "source3 is null");
//        ObjectHelper.requireNonNull(source4, "source4 is null");
//        ObjectHelper.requireNonNull(source5, "source5 is null");
//        ObjectHelper.requireNonNull(source6, "source6 is null");
//        ObjectHelper.requireNonNull(source7, "source7 is null");
//        ObjectHelper.requireNonNull(source8, "source8 is null");
//        ObjectHelper.requireNonNull(source9, "source9 is null");
//        return combineLatest(Functions.toFunction(combiner), bufferSize(), source1, source2, source3, source4, source5, source6, source7, source8, source9);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T, R> Observable<R> combineLatestDelayError(ObservableSource<? extends T>[] sources,
//                                                                             Function<? super Object[], ? extends R> combiner) {
//        return combineLatestDelayError(sources, combiner, bufferSize());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T, R> Observable<R> combineLatestDelayError(Function<? super Object[], ? extends R> combiner,
//                                                                             int bufferSize, ObservableSource<? extends T>... sources) {
//        return combineLatestDelayError(sources, combiner, bufferSize);
//    }
//
//    @CheckReturnValue
//    @NonNull
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T, R> Observable<R> combineLatestDelayError(ObservableSource<? extends T>[] sources,
//                                                                             Function<? super Object[], ? extends R> combiner, int bufferSize) {
//        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
//        ObjectHelper.requireNonNull(combiner, "combiner is null");
//        if (sources.length == 0) {
//            return empty();
//        }
//        // the queue holds a pair of values so we need to double the capacity
//        int s = bufferSize << 1;
//        return RxJavaPlugins.onAssembly(new ObservableCombineLatest<T, R>(sources, null, combiner, s, true));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T, R> Observable<R> combineLatestDelayError(Iterable<? extends ObservableSource<? extends T>> sources,
//                                                                             Function<? super Object[], ? extends R> combiner) {
//        return combineLatestDelayError(sources, combiner, bufferSize());
//    }
//
//    @CheckReturnValue
//    @NonNull
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T, R> Observable<R> combineLatestDelayError(Iterable<? extends ObservableSource<? extends T>> sources,
//                                                                             Function<? super Object[], ? extends R> combiner, int bufferSize) {
//        ObjectHelper.requireNonNull(sources, "sources is null");
//        ObjectHelper.requireNonNull(combiner, "combiner is null");
//        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
//
//        // the queue holds a pair of values so we need to double the capacity
//        int s = bufferSize << 1;
//        return RxJavaPlugins.onAssembly(new ObservableCombineLatest<T, R>(null, sources, combiner, s, true));
//    }
//
//    @SuppressWarnings({"unchecked", "rawtypes"})
//    @CheckReturnValue
//    @NonNull
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> concat(Iterable<? extends ObservableSource<? extends T>> sources) {
//        ObjectHelper.requireNonNull(sources, "sources is null");
//        return fromIterable(sources).concatMapDelayError((Function) Functions.identity(), bufferSize(), false);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> concat(ObservableSource<? extends ObservableSource<? extends T>> sources) {
//        return concat(sources, bufferSize());
//    }
//
//    @SuppressWarnings({"unchecked", "rawtypes"})
//    @CheckReturnValue
//    @NonNull
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> concat(ObservableSource<? extends ObservableSource<? extends T>> sources, int prefetch) {
//        ObjectHelper.requireNonNull(sources, "sources is null");
//        ObjectHelper.verifyPositive(prefetch, "prefetch");
//        return RxJavaPlugins.onAssembly(new ObservableConcatMap(sources, Functions.identity(), prefetch, ErrorMode.IMMEDIATE));
//    }
//
//    @SuppressWarnings("unchecked")
//    @CheckReturnValue
//    @NonNull
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> concat(ObservableSource<? extends T> source1, ObservableSource<? extends T> source2) {
//        ObjectHelper.requireNonNull(source1, "source1 is null");
//        ObjectHelper.requireNonNull(source2, "source2 is null");
//        return concatArray(source1, source2);
//    }
//
//    @SuppressWarnings("unchecked")
//    @CheckReturnValue
//    @NonNull
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> concat(
//            ObservableSource<? extends T> source1, ObservableSource<? extends T> source2,
//            ObservableSource<? extends T> source3) {
//        ObjectHelper.requireNonNull(source1, "source1 is null");
//        ObjectHelper.requireNonNull(source2, "source2 is null");
//        ObjectHelper.requireNonNull(source3, "source3 is null");
//        return concatArray(source1, source2, source3);
//    }
//
//    @SuppressWarnings("unchecked")
//    @CheckReturnValue
//    @NonNull
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> concat(
//            ObservableSource<? extends T> source1, ObservableSource<? extends T> source2,
//            ObservableSource<? extends T> source3, ObservableSource<? extends T> source4) {
//        ObjectHelper.requireNonNull(source1, "source1 is null");
//        ObjectHelper.requireNonNull(source2, "source2 is null");
//        ObjectHelper.requireNonNull(source3, "source3 is null");
//        ObjectHelper.requireNonNull(source4, "source4 is null");
//        return concatArray(source1, source2, source3, source4);
//    }
//
//    @SuppressWarnings({"unchecked", "rawtypes"})
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> concatArray(ObservableSource<? extends T>... sources) {
//        if (sources.length == 0) {
//            return empty();
//        }
//        if (sources.length == 1) {
//            return wrap((ObservableSource<T>) sources[0]);
//        }
//        return RxJavaPlugins.onAssembly(new ObservableConcatMap(fromArray(sources), Functions.identity(), bufferSize(), ErrorMode.BOUNDARY));
//    }
//
//    @SuppressWarnings({"unchecked"})
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> concatArrayDelayError(ObservableSource<? extends T>... sources) {
//        if (sources.length == 0) {
//            return empty();
//        }
//        if (sources.length == 1) {
//            return (Observable<T>) wrap(sources[0]);
//        }
//        return concatDelayError(fromArray(sources));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> concatArrayEager(ObservableSource<? extends T>... sources) {
//        return concatArrayEager(bufferSize(), bufferSize(), sources);
//    }
//
//    @SuppressWarnings({"rawtypes", "unchecked"})
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> concatArrayEager(int maxConcurrency, int prefetch, ObservableSource<? extends T>... sources) {
//        return fromArray(sources).concatMapEagerDelayError((Function) Functions.identity(), maxConcurrency, prefetch, false);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> concatArrayEagerDelayError(ObservableSource<? extends T>... sources) {
//        return concatArrayEagerDelayError(bufferSize(), bufferSize(), sources);
//    }
//
//    @SuppressWarnings({"rawtypes", "unchecked"})
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> concatArrayEagerDelayError(int maxConcurrency, int prefetch, ObservableSource<? extends T>... sources) {
//        return fromArray(sources).concatMapEagerDelayError((Function) Functions.identity(), maxConcurrency, prefetch, true);
//    }
//
//    @CheckReturnValue
//    @NonNull
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> concatDelayError(Iterable<? extends ObservableSource<? extends T>> sources) {
//        ObjectHelper.requireNonNull(sources, "sources is null");
//        return concatDelayError(fromIterable(sources));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> concatDelayError(ObservableSource<? extends ObservableSource<? extends T>> sources) {
//        return concatDelayError(sources, bufferSize(), true);
//    }
//
//    @SuppressWarnings({"rawtypes", "unchecked"})
//    @CheckReturnValue
//    @NonNull
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> concatDelayError(ObservableSource<? extends ObservableSource<? extends T>> sources, int prefetch, boolean tillTheEnd) {
//        ObjectHelper.requireNonNull(sources, "sources is null");
//        ObjectHelper.verifyPositive(prefetch, "prefetch is null");
//        return RxJavaPlugins.onAssembly(new ObservableConcatMap(sources, Functions.identity(), prefetch, tillTheEnd ? ErrorMode.END : ErrorMode.BOUNDARY));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> concatEager(ObservableSource<? extends ObservableSource<? extends T>> sources) {
//        return concatEager(sources, bufferSize(), bufferSize());
//    }
//
//    @SuppressWarnings({"unchecked", "rawtypes"})
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> concatEager(ObservableSource<? extends ObservableSource<? extends T>> sources, int maxConcurrency, int prefetch) {
//        return wrap(sources).concatMapEager((Function) Functions.identity(), maxConcurrency, prefetch);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> concatEager(Iterable<? extends ObservableSource<? extends T>> sources) {
//        return concatEager(sources, bufferSize(), bufferSize());
//    }
//
//    @SuppressWarnings({"unchecked", "rawtypes"})
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> concatEager(Iterable<? extends ObservableSource<? extends T>> sources, int maxConcurrency, int prefetch) {
//        return fromIterable(sources).concatMapEagerDelayError((Function) Functions.identity(), maxConcurrency, prefetch, false);
//    }
//
//    @CheckReturnValue
//    @NonNull
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> create(ObservableOnSubscribe<T> source) {
//        ObjectHelper.requireNonNull(source, "source is null");
//        return RxJavaPlugins.onAssembly(new ObservableCreate<T>(source));
//    }
//
//    @CheckReturnValue
//    @NonNull
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> defer(Callable<? extends ObservableSource<? extends T>> supplier) {
//        ObjectHelper.requireNonNull(supplier, "supplier is null");
//        return RxJavaPlugins.onAssembly(new ObservableDefer<T>(supplier));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    @SuppressWarnings("unchecked")
//    public static <T> Observable<T> empty() {
//        return RxJavaPlugins.onAssembly((Observable<T>) ObservableEmpty.INSTANCE);
//    }
//
//    @CheckReturnValue
//    @NonNull
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> error(Callable<? extends Throwable> errorSupplier) {
//        ObjectHelper.requireNonNull(errorSupplier, "errorSupplier is null");
//        return RxJavaPlugins.onAssembly(new ObservableError<T>(errorSupplier));
//    }
//
//    @CheckReturnValue
//    @NonNull
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> error(final Throwable exception) {
//        ObjectHelper.requireNonNull(exception, "exception is null");
//        return error(Functions.justCallable(exception));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    @NonNull
//    public static <T> Observable<T> fromArray(T... items) {
//        ObjectHelper.requireNonNull(items, "items is null");
//        if (items.length == 0) {
//            return empty();
//        }
//        if (items.length == 1) {
//            return just(items[0]);
//        }
//        return RxJavaPlugins.onAssembly(new ObservableFromArray<T>(items));
//    }
//
//    @CheckReturnValue
//    @NonNull
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> fromCallable(Callable<? extends T> supplier) {
//        ObjectHelper.requireNonNull(supplier, "supplier is null");
//        return RxJavaPlugins.onAssembly(new ObservableFromCallable<T>(supplier));
//    }
//
//    @CheckReturnValue
//    @NonNull
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> fromFuture(Future<? extends T> future) {
//        ObjectHelper.requireNonNull(future, "future is null");
//        return RxJavaPlugins.onAssembly(new ObservableFromFuture<T>(future, 0L, null));
//    }
//
//    @CheckReturnValue
//    @NonNull
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> fromFuture(Future<? extends T> future, long timeout, TimeUnit unit) {
//        ObjectHelper.requireNonNull(future, "future is null");
//        ObjectHelper.requireNonNull(unit, "unit is null");
//        return RxJavaPlugins.onAssembly(new ObservableFromFuture<T>(future, timeout, unit));
//    }
//
//    @CheckReturnValue
//    @NonNull
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public static <T> Observable<T> fromFuture(Future<? extends T> future, long timeout, TimeUnit unit, Scheduler scheduler) {
//        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
//        Observable<T> o = fromFuture(future, timeout, unit);
//        return o.subscribeOn(scheduler);
//    }
//
//    @CheckReturnValue
//    @NonNull
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public static <T> Observable<T> fromFuture(Future<? extends T> future, Scheduler scheduler) {
//        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
//        Observable<T> o = fromFuture(future);
//        return o.subscribeOn(scheduler);
//    }
//
//    @CheckReturnValue
//    @NonNull
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> fromIterable(Iterable<? extends T> source) {
//        ObjectHelper.requireNonNull(source, "source is null");
//        return RxJavaPlugins.onAssembly(new ObservableFromIterable<T>(source));
//    }
//
//    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
//    @CheckReturnValue
//    @NonNull
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> fromPublisher(Publisher<? extends T> publisher) {
//        ObjectHelper.requireNonNull(publisher, "publisher is null");
//        return RxJavaPlugins.onAssembly(new ObservableFromPublisher<T>(publisher));
//    }
//
//    @CheckReturnValue
//    @NonNull
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> generate(final Consumer<Emitter<T>> generator) {
//        ObjectHelper.requireNonNull(generator, "generator is null");
//        return generate(Functions.<Object>nullSupplier(),
//                ObservableInternalHelper.simpleGenerator(generator), Functions.<Object>emptyConsumer());
//    }
//
//    @CheckReturnValue
//    @NonNull
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T, S> Observable<T> generate(Callable<S> initialState, final BiConsumer<S, Emitter<T>> generator) {
//        ObjectHelper.requireNonNull(generator, "generator is null");
//        return generate(initialState, ObservableInternalHelper.simpleBiGenerator(generator), Functions.emptyConsumer());
//    }
//
//    @CheckReturnValue
//    @NonNull
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T, S> Observable<T> generate(
//            final Callable<S> initialState,
//            final BiConsumer<S, Emitter<T>> generator,
//            Consumer<? super S> disposeState) {
//        ObjectHelper.requireNonNull(generator, "generator is null");
//        return generate(initialState, ObservableInternalHelper.simpleBiGenerator(generator), disposeState);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T, S> Observable<T> generate(Callable<S> initialState, BiFunction<S, Emitter<T>, S> generator) {
//        return generate(initialState, generator, Functions.emptyConsumer());
//    }
//
//    @CheckReturnValue
//    @NonNull
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T, S> Observable<T> generate(Callable<S> initialState, BiFunction<S, Emitter<T>, S> generator,
//                                                              Consumer<? super S> disposeState) {
//        ObjectHelper.requireNonNull(initialState, "initialState is null");
//        ObjectHelper.requireNonNull(generator, "generator is null");
//        ObjectHelper.requireNonNull(disposeState, "disposeState is null");
//        return RxJavaPlugins.onAssembly(new ObservableGenerate<T, S>(initialState, generator, disposeState));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.COMPUTATION)
//    public static Observable<Long> interval(long initialDelay, long period, TimeUnit unit) {
//        return interval(initialDelay, period, unit, Schedulers.computation());
//    }
//
//    @CheckReturnValue
//    @NonNull
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public static Observable<Long> interval(long initialDelay, long period, TimeUnit unit, Scheduler scheduler) {
//        ObjectHelper.requireNonNull(unit, "unit is null");
//        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
//
//        return RxJavaPlugins.onAssembly(new ObservableInterval(Math.max(0L, initialDelay), Math.max(0L, period), unit, scheduler));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.COMPUTATION)
//    public static Observable<Long> interval(long period, TimeUnit unit) {
//        return interval(period, period, unit, Schedulers.computation());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public static Observable<Long> interval(long period, TimeUnit unit, Scheduler scheduler) {
//        return interval(period, period, unit, scheduler);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.COMPUTATION)
//    public static Observable<Long> intervalRange(long start, long count, long initialDelay, long period, TimeUnit unit) {
//        return intervalRange(start, count, initialDelay, period, unit, Schedulers.computation());
//    }
//
//    @CheckReturnValue
//    @NonNull
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public static Observable<Long> intervalRange(long start, long count, long initialDelay, long period, TimeUnit unit, Scheduler scheduler) {
//        if (count < 0) {
//            throw new IllegalArgumentException("count >= 0 required but it was " + count);
//        }
//
//        if (count == 0L) {
//            return Observable.<Long>empty().delay(initialDelay, unit, scheduler);
//        }
//
//        long end = start + (count - 1);
//        if (start > 0 && end < 0) {
//            throw new IllegalArgumentException("Overflow! start + count is bigger than Long.MAX_VALUE");
//        }
//        ObjectHelper.requireNonNull(unit, "unit is null");
//        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
//
//        return RxJavaPlugins.onAssembly(new ObservableIntervalRange(start, end, Math.max(0L, initialDelay), Math.max(0L, period), unit, scheduler));
//    }
//
//    @CheckReturnValue
//    @NonNull
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> just(T item) {
//        ObjectHelper.requireNonNull(item, "item is null");
//        return RxJavaPlugins.onAssembly(new ObservableJust<T>(item));
//    }
//
//    @SuppressWarnings("unchecked")
//    @CheckReturnValue
//    @NonNull
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> just(T item1, T item2) {
//        ObjectHelper.requireNonNull(item1, "item1 is null");
//        ObjectHelper.requireNonNull(item2, "item2 is null");
//
//        return fromArray(item1, item2);
//    }
//
//    @SuppressWarnings("unchecked")
//    @CheckReturnValue
//    @NonNull
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> just(T item1, T item2, T item3) {
//        ObjectHelper.requireNonNull(item1, "item1 is null");
//        ObjectHelper.requireNonNull(item2, "item2 is null");
//        ObjectHelper.requireNonNull(item3, "item3 is null");
//
//        return fromArray(item1, item2, item3);
//    }
//
//    @SuppressWarnings("unchecked")
//    @CheckReturnValue
//    @NonNull
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> just(T item1, T item2, T item3, T item4) {
//        ObjectHelper.requireNonNull(item1, "item1 is null");
//        ObjectHelper.requireNonNull(item2, "item2 is null");
//        ObjectHelper.requireNonNull(item3, "item3 is null");
//        ObjectHelper.requireNonNull(item4, "item4 is null");
//
//        return fromArray(item1, item2, item3, item4);
//    }
//
//    @SuppressWarnings("unchecked")
//    @CheckReturnValue
//    @NonNull
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> just(T item1, T item2, T item3, T item4, T item5) {
//        ObjectHelper.requireNonNull(item1, "item1 is null");
//        ObjectHelper.requireNonNull(item2, "item2 is null");
//        ObjectHelper.requireNonNull(item3, "item3 is null");
//        ObjectHelper.requireNonNull(item4, "item4 is null");
//        ObjectHelper.requireNonNull(item5, "item5 is null");
//
//        return fromArray(item1, item2, item3, item4, item5);
//    }
//
//    @SuppressWarnings("unchecked")
//    @CheckReturnValue
//    @NonNull
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> just(T item1, T item2, T item3, T item4, T item5, T item6) {
//        ObjectHelper.requireNonNull(item1, "item1 is null");
//        ObjectHelper.requireNonNull(item2, "item2 is null");
//        ObjectHelper.requireNonNull(item3, "item3 is null");
//        ObjectHelper.requireNonNull(item4, "item4 is null");
//        ObjectHelper.requireNonNull(item5, "item5 is null");
//        ObjectHelper.requireNonNull(item6, "item6 is null");
//
//        return fromArray(item1, item2, item3, item4, item5, item6);
//    }
//
//    @SuppressWarnings("unchecked")
//    @CheckReturnValue
//    @NonNull
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> just(T item1, T item2, T item3, T item4, T item5, T item6, T item7) {
//        ObjectHelper.requireNonNull(item1, "item1 is null");
//        ObjectHelper.requireNonNull(item2, "item2 is null");
//        ObjectHelper.requireNonNull(item3, "item3 is null");
//        ObjectHelper.requireNonNull(item4, "item4 is null");
//        ObjectHelper.requireNonNull(item5, "item5 is null");
//        ObjectHelper.requireNonNull(item6, "item6 is null");
//        ObjectHelper.requireNonNull(item7, "item7 is null");
//
//        return fromArray(item1, item2, item3, item4, item5, item6, item7);
//    }
//
//    @SuppressWarnings("unchecked")
//    @CheckReturnValue
//    @NonNull
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> just(T item1, T item2, T item3, T item4, T item5, T item6, T item7, T item8) {
//        ObjectHelper.requireNonNull(item1, "item1 is null");
//        ObjectHelper.requireNonNull(item2, "item2 is null");
//        ObjectHelper.requireNonNull(item3, "item3 is null");
//        ObjectHelper.requireNonNull(item4, "item4 is null");
//        ObjectHelper.requireNonNull(item5, "item5 is null");
//        ObjectHelper.requireNonNull(item6, "item6 is null");
//        ObjectHelper.requireNonNull(item7, "item7 is null");
//        ObjectHelper.requireNonNull(item8, "item8 is null");
//
//        return fromArray(item1, item2, item3, item4, item5, item6, item7, item8);
//    }
//
//    @SuppressWarnings("unchecked")
//    @CheckReturnValue
//    @NonNull
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> just(T item1, T item2, T item3, T item4, T item5, T item6, T item7, T item8, T item9) {
//        ObjectHelper.requireNonNull(item1, "item1 is null");
//        ObjectHelper.requireNonNull(item2, "item2 is null");
//        ObjectHelper.requireNonNull(item3, "item3 is null");
//        ObjectHelper.requireNonNull(item4, "item4 is null");
//        ObjectHelper.requireNonNull(item5, "item5 is null");
//        ObjectHelper.requireNonNull(item6, "item6 is null");
//        ObjectHelper.requireNonNull(item7, "item7 is null");
//        ObjectHelper.requireNonNull(item8, "item8 is null");
//        ObjectHelper.requireNonNull(item9, "item9 is null");
//
//        return fromArray(item1, item2, item3, item4, item5, item6, item7, item8, item9);
//    }
//
//    @SuppressWarnings("unchecked")
//    @CheckReturnValue
//    @NonNull
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> just(T item1, T item2, T item3, T item4, T item5, T item6, T item7, T item8, T item9, T item10) {
//        ObjectHelper.requireNonNull(item1, "item1 is null");
//        ObjectHelper.requireNonNull(item2, "item2 is null");
//        ObjectHelper.requireNonNull(item3, "item3 is null");
//        ObjectHelper.requireNonNull(item4, "item4 is null");
//        ObjectHelper.requireNonNull(item5, "item5 is null");
//        ObjectHelper.requireNonNull(item6, "item6 is null");
//        ObjectHelper.requireNonNull(item7, "item7 is null");
//        ObjectHelper.requireNonNull(item8, "item8 is null");
//        ObjectHelper.requireNonNull(item9, "item9 is null");
//        ObjectHelper.requireNonNull(item10, "item10 is null");
//
//        return fromArray(item1, item2, item3, item4, item5, item6, item7, item8, item9, item10);
//    }
//
//    @SuppressWarnings({"unchecked", "rawtypes"})
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> merge(Iterable<? extends ObservableSource<? extends T>> sources, int maxConcurrency, int bufferSize) {
//        return fromIterable(sources).flatMap((Function) Functions.identity(), false, maxConcurrency, bufferSize);
//    }
//
//    @SuppressWarnings({"unchecked", "rawtypes"})
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> mergeArray(int maxConcurrency, int bufferSize, ObservableSource<? extends T>... sources) {
//        return fromArray(sources).flatMap((Function) Functions.identity(), false, maxConcurrency, bufferSize);
//    }
//
//    @SuppressWarnings({"unchecked", "rawtypes"})
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> merge(Iterable<? extends ObservableSource<? extends T>> sources) {
//        return fromIterable(sources).flatMap((Function) Functions.identity());
//    }
//
//    @SuppressWarnings({"unchecked", "rawtypes"})
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> merge(Iterable<? extends ObservableSource<? extends T>> sources, int maxConcurrency) {
//        return fromIterable(sources).flatMap((Function) Functions.identity(), maxConcurrency);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    @SuppressWarnings({"unchecked", "rawtypes"})
//    public static <T> Observable<T> merge(ObservableSource<? extends ObservableSource<? extends T>> sources) {
//        ObjectHelper.requireNonNull(sources, "sources is null");
//        return RxJavaPlugins.onAssembly(new ObservableFlatMap(sources, Functions.identity(), false, Integer.MAX_VALUE, bufferSize()));
//    }
//
//    @SuppressWarnings({"unchecked", "rawtypes"})
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> merge(ObservableSource<? extends ObservableSource<? extends T>> sources, int maxConcurrency) {
//        ObjectHelper.requireNonNull(sources, "sources is null");
//        ObjectHelper.verifyPositive(maxConcurrency, "maxConcurrency");
//        return RxJavaPlugins.onAssembly(new ObservableFlatMap(sources, Functions.identity(), false, maxConcurrency, bufferSize()));
//    }
//
//    @SuppressWarnings({"unchecked", "rawtypes"})
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> merge(ObservableSource<? extends T> source1, ObservableSource<? extends T> source2) {
//        ObjectHelper.requireNonNull(source1, "source1 is null");
//        ObjectHelper.requireNonNull(source2, "source2 is null");
//        return fromArray(source1, source2).flatMap((Function) Functions.identity(), false, 2);
//    }
//
//    @SuppressWarnings({"unchecked", "rawtypes"})
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> merge(ObservableSource<? extends T> source1, ObservableSource<? extends T> source2, ObservableSource<? extends T> source3) {
//        ObjectHelper.requireNonNull(source1, "source1 is null");
//        ObjectHelper.requireNonNull(source2, "source2 is null");
//        ObjectHelper.requireNonNull(source3, "source3 is null");
//        return fromArray(source1, source2, source3).flatMap((Function) Functions.identity(), false, 3);
//    }
//
//    @SuppressWarnings({"unchecked", "rawtypes"})
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> merge(
//            ObservableSource<? extends T> source1, ObservableSource<? extends T> source2,
//            ObservableSource<? extends T> source3, ObservableSource<? extends T> source4) {
//        ObjectHelper.requireNonNull(source1, "source1 is null");
//        ObjectHelper.requireNonNull(source2, "source2 is null");
//        ObjectHelper.requireNonNull(source3, "source3 is null");
//        ObjectHelper.requireNonNull(source4, "source4 is null");
//        return fromArray(source1, source2, source3, source4).flatMap((Function) Functions.identity(), false, 4);
//    }
//
//    @SuppressWarnings({"unchecked", "rawtypes"})
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> mergeArray(ObservableSource<? extends T>... sources) {
//        return fromArray(sources).flatMap((Function) Functions.identity(), sources.length);
//    }
//
//    @SuppressWarnings({"unchecked", "rawtypes"})
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> mergeDelayError(Iterable<? extends ObservableSource<? extends T>> sources) {
//        return fromIterable(sources).flatMap((Function) Functions.identity(), true);
//    }
//
//    @SuppressWarnings({"unchecked", "rawtypes"})
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> mergeDelayError(Iterable<? extends ObservableSource<? extends T>> sources, int maxConcurrency, int bufferSize) {
//        return fromIterable(sources).flatMap((Function) Functions.identity(), true, maxConcurrency, bufferSize);
//    }
//
//    @SuppressWarnings({"unchecked", "rawtypes"})
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> mergeArrayDelayError(int maxConcurrency, int bufferSize, ObservableSource<? extends T>... sources) {
//        return fromArray(sources).flatMap((Function) Functions.identity(), true, maxConcurrency, bufferSize);
//    }
//
//    @SuppressWarnings({"unchecked", "rawtypes"})
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> mergeDelayError(Iterable<? extends ObservableSource<? extends T>> sources, int maxConcurrency) {
//        return fromIterable(sources).flatMap((Function) Functions.identity(), true, maxConcurrency);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    @SuppressWarnings({"unchecked", "rawtypes"})
//    public static <T> Observable<T> mergeDelayError(ObservableSource<? extends ObservableSource<? extends T>> sources) {
//        ObjectHelper.requireNonNull(sources, "sources is null");
//        return RxJavaPlugins.onAssembly(new ObservableFlatMap(sources, Functions.identity(), true, Integer.MAX_VALUE, bufferSize()));
//    }
//
//    @SuppressWarnings({"unchecked", "rawtypes"})
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> mergeDelayError(ObservableSource<? extends ObservableSource<? extends T>> sources, int maxConcurrency) {
//        ObjectHelper.requireNonNull(sources, "sources is null");
//        ObjectHelper.verifyPositive(maxConcurrency, "maxConcurrency");
//        return RxJavaPlugins.onAssembly(new ObservableFlatMap(sources, Functions.identity(), true, maxConcurrency, bufferSize()));
//    }
//
//    @SuppressWarnings({"unchecked", "rawtypes"})
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> mergeDelayError(ObservableSource<? extends T> source1, ObservableSource<? extends T> source2) {
//        ObjectHelper.requireNonNull(source1, "source1 is null");
//        ObjectHelper.requireNonNull(source2, "source2 is null");
//        return fromArray(source1, source2).flatMap((Function) Functions.identity(), true, 2);
//    }
//
//    @SuppressWarnings({"unchecked", "rawtypes"})
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> mergeDelayError(ObservableSource<? extends T> source1, ObservableSource<? extends T> source2, ObservableSource<? extends T> source3) {
//        ObjectHelper.requireNonNull(source1, "source1 is null");
//        ObjectHelper.requireNonNull(source2, "source2 is null");
//        ObjectHelper.requireNonNull(source3, "source3 is null");
//        return fromArray(source1, source2, source3).flatMap((Function) Functions.identity(), true, 3);
//    }
//
//    @SuppressWarnings({"unchecked", "rawtypes"})
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> mergeDelayError(
//            ObservableSource<? extends T> source1, ObservableSource<? extends T> source2,
//            ObservableSource<? extends T> source3, ObservableSource<? extends T> source4) {
//        ObjectHelper.requireNonNull(source1, "source1 is null");
//        ObjectHelper.requireNonNull(source2, "source2 is null");
//        ObjectHelper.requireNonNull(source3, "source3 is null");
//        ObjectHelper.requireNonNull(source4, "source4 is null");
//        return fromArray(source1, source2, source3, source4).flatMap((Function) Functions.identity(), true, 4);
//    }
//
//    @SuppressWarnings({"unchecked", "rawtypes"})
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> mergeArrayDelayError(ObservableSource<? extends T>... sources) {
//        return fromArray(sources).flatMap((Function) Functions.identity(), true, sources.length);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    @SuppressWarnings("unchecked")
//    public static <T> Observable<T> never() {
//        return RxJavaPlugins.onAssembly((Observable<T>) ObservableNever.INSTANCE);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static Observable<Integer> range(final int start, final int count) {
//        if (count < 0) {
//            throw new IllegalArgumentException("count >= 0 required but it was " + count);
//        }
//        if (count == 0) {
//            return empty();
//        }
//        if (count == 1) {
//            return just(start);
//        }
//        if ((long) start + (count - 1) > Integer.MAX_VALUE) {
//            throw new IllegalArgumentException("Integer overflow");
//        }
//        return RxJavaPlugins.onAssembly(new ObservableRange(start, count));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static Observable<Long> rangeLong(long start, long count) {
//        if (count < 0) {
//            throw new IllegalArgumentException("count >= 0 required but it was " + count);
//        }
//
//        if (count == 0) {
//            return empty();
//        }
//
//        if (count == 1) {
//            return just(start);
//        }
//
//        long end = start + (count - 1);
//        if (start > 0 && end < 0) {
//            throw new IllegalArgumentException("Overflow! start + count is bigger than Long.MAX_VALUE");
//        }
//
//        return RxJavaPlugins.onAssembly(new ObservableRangeLong(start, count));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Single<Boolean> sequenceEqual(ObservableSource<? extends T> source1, ObservableSource<? extends T> source2) {
//        return sequenceEqual(source1, source2, ObjectHelper.equalsPredicate(), bufferSize());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Single<Boolean> sequenceEqual(ObservableSource<? extends T> source1, ObservableSource<? extends T> source2,
//                                                    BiPredicate<? super T, ? super T> isEqual) {
//        return sequenceEqual(source1, source2, isEqual, bufferSize());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Single<Boolean> sequenceEqual(ObservableSource<? extends T> source1, ObservableSource<? extends T> source2,
//                                                    BiPredicate<? super T, ? super T> isEqual, int bufferSize) {
//        ObjectHelper.requireNonNull(source1, "source1 is null");
//        ObjectHelper.requireNonNull(source2, "source2 is null");
//        ObjectHelper.requireNonNull(isEqual, "isEqual is null");
//        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
//        return RxJavaPlugins.onAssembly(new ObservableSequenceEqualSingle<T>(source1, source2, isEqual, bufferSize));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Single<Boolean> sequenceEqual(ObservableSource<? extends T> source1, ObservableSource<? extends T> source2,
//                                                    int bufferSize) {
//        return sequenceEqual(source1, source2, ObjectHelper.equalsPredicate(), bufferSize);
//    }
//
//    @SuppressWarnings({"rawtypes", "unchecked"})
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> switchOnNext(ObservableSource<? extends ObservableSource<? extends T>> sources, int bufferSize) {
//        ObjectHelper.requireNonNull(sources, "sources is null");
//        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
//        return RxJavaPlugins.onAssembly(new ObservableSwitchMap(sources, Functions.identity(), bufferSize, false));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> switchOnNext(ObservableSource<? extends ObservableSource<? extends T>> sources) {
//        return switchOnNext(sources, bufferSize());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> switchOnNextDelayError(ObservableSource<? extends ObservableSource<? extends T>> sources) {
//        return switchOnNextDelayError(sources, bufferSize());
//    }
//
//    @SuppressWarnings({"rawtypes", "unchecked"})
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> switchOnNextDelayError(ObservableSource<? extends ObservableSource<? extends T>> sources, int prefetch) {
//        ObjectHelper.requireNonNull(sources, "sources is null");
//        ObjectHelper.verifyPositive(prefetch, "prefetch");
//        return RxJavaPlugins.onAssembly(new ObservableSwitchMap(sources, Functions.identity(), prefetch, true));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.COMPUTATION)
//    public static Observable<Long> timer(long delay, TimeUnit unit) {
//        return timer(delay, unit, Schedulers.computation());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public static Observable<Long> timer(long delay, TimeUnit unit, Scheduler scheduler) {
//        ObjectHelper.requireNonNull(unit, "unit is null");
//        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
//
//        return RxJavaPlugins.onAssembly(new ObservableTimer(Math.max(delay, 0L), unit, scheduler));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> unsafeCreate(ObservableSource<T> onSubscribe) {
//        ObjectHelper.requireNonNull(onSubscribe, "onSubscribe is null");
//        if (onSubscribe instanceof Observable) {
//            throw new IllegalArgumentException("unsafeCreate(Observable) should be upgraded");
//        }
//        return RxJavaPlugins.onAssembly(new ObservableFromUnsafeSource<T>(onSubscribe));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T, D> Observable<T> using(Callable<? extends D> resourceSupplier, Function<? super D, ? extends ObservableSource<? extends T>> sourceSupplier, Consumer<? super D> disposer) {
//        return using(resourceSupplier, sourceSupplier, disposer, true);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T, D> Observable<T> using(Callable<? extends D> resourceSupplier, Function<? super D, ? extends ObservableSource<? extends T>> sourceSupplier, Consumer<? super D> disposer, boolean eager) {
//        ObjectHelper.requireNonNull(resourceSupplier, "resourceSupplier is null");
//        ObjectHelper.requireNonNull(sourceSupplier, "sourceSupplier is null");
//        ObjectHelper.requireNonNull(disposer, "disposer is null");
//        return RxJavaPlugins.onAssembly(new ObservableUsing<T, D>(resourceSupplier, sourceSupplier, disposer, eager));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T> Observable<T> wrap(ObservableSource<T> source) {
//        ObjectHelper.requireNonNull(source, "source is null");
//        if (source instanceof Observable) {
//            return RxJavaPlugins.onAssembly((Observable<T>) source);
//        }
//        return RxJavaPlugins.onAssembly(new ObservableFromUnsafeSource<T>(source));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T, R> Observable<R> zip(Iterable<? extends ObservableSource<? extends T>> sources, Function<? super Object[], ? extends R> zipper) {
//        ObjectHelper.requireNonNull(zipper, "zipper is null");
//        ObjectHelper.requireNonNull(sources, "sources is null");
//        return RxJavaPlugins.onAssembly(new ObservableZip<T, R>(null, sources, zipper, bufferSize(), false));
//    }
//
//    @SuppressWarnings({"rawtypes", "unchecked"})
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T, R> Observable<R> zip(ObservableSource<? extends ObservableSource<? extends T>> sources, final Function<? super Object[], ? extends R> zipper) {
//        ObjectHelper.requireNonNull(zipper, "zipper is null");
//        ObjectHelper.requireNonNull(sources, "sources is null");
//        return RxJavaPlugins.onAssembly(new ObservableToList(sources, 16)
//                .flatMap(ObservableInternalHelper.zipIterable(zipper)));
//    }
//
//    @SuppressWarnings("unchecked")
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T1, T2, R> Observable<R> zip(
//            ObservableSource<? extends T1> source1, ObservableSource<? extends T2> source2,
//            BiFunction<? super T1, ? super T2, ? extends R> zipper) {
//        ObjectHelper.requireNonNull(source1, "source1 is null");
//        ObjectHelper.requireNonNull(source2, "source2 is null");
//        return zipArray(Functions.toFunction(zipper), false, bufferSize(), source1, source2);
//    }
//
//    @SuppressWarnings("unchecked")
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T1, T2, R> Observable<R> zip(
//            ObservableSource<? extends T1> source1, ObservableSource<? extends T2> source2,
//            BiFunction<? super T1, ? super T2, ? extends R> zipper, boolean delayError) {
//        ObjectHelper.requireNonNull(source1, "source1 is null");
//        ObjectHelper.requireNonNull(source2, "source2 is null");
//        return zipArray(Functions.toFunction(zipper), delayError, bufferSize(), source1, source2);
//    }
//
//    @SuppressWarnings("unchecked")
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T1, T2, R> Observable<R> zip(
//            ObservableSource<? extends T1> source1, ObservableSource<? extends T2> source2,
//            BiFunction<? super T1, ? super T2, ? extends R> zipper, boolean delayError, int bufferSize) {
//        ObjectHelper.requireNonNull(source1, "source1 is null");
//        ObjectHelper.requireNonNull(source2, "source2 is null");
//        return zipArray(Functions.toFunction(zipper), delayError, bufferSize, source1, source2);
//    }
//
//    @SuppressWarnings("unchecked")
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T1, T2, T3, R> Observable<R> zip(
//            ObservableSource<? extends T1> source1, ObservableSource<? extends T2> source2, ObservableSource<? extends T3> source3,
//            Function3<? super T1, ? super T2, ? super T3, ? extends R> zipper) {
//        ObjectHelper.requireNonNull(source1, "source1 is null");
//        ObjectHelper.requireNonNull(source2, "source2 is null");
//        ObjectHelper.requireNonNull(source3, "source3 is null");
//        return zipArray(Functions.toFunction(zipper), false, bufferSize(), source1, source2, source3);
//    }
//
//    @SuppressWarnings("unchecked")
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T1, T2, T3, T4, R> Observable<R> zip(
//            ObservableSource<? extends T1> source1, ObservableSource<? extends T2> source2, ObservableSource<? extends T3> source3,
//            ObservableSource<? extends T4> source4,
//            Function4<? super T1, ? super T2, ? super T3, ? super T4, ? extends R> zipper) {
//        ObjectHelper.requireNonNull(source1, "source1 is null");
//        ObjectHelper.requireNonNull(source2, "source2 is null");
//        ObjectHelper.requireNonNull(source3, "source3 is null");
//        ObjectHelper.requireNonNull(source4, "source4 is null");
//        return zipArray(Functions.toFunction(zipper), false, bufferSize(), source1, source2, source3, source4);
//    }
//
//    @SuppressWarnings("unchecked")
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T1, T2, T3, T4, T5, R> Observable<R> zip(
//            ObservableSource<? extends T1> source1, ObservableSource<? extends T2> source2, ObservableSource<? extends T3> source3,
//            ObservableSource<? extends T4> source4, ObservableSource<? extends T5> source5,
//            Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends R> zipper) {
//        ObjectHelper.requireNonNull(source1, "source1 is null");
//        ObjectHelper.requireNonNull(source2, "source2 is null");
//        ObjectHelper.requireNonNull(source3, "source3 is null");
//        ObjectHelper.requireNonNull(source4, "source4 is null");
//        ObjectHelper.requireNonNull(source5, "source5 is null");
//        return zipArray(Functions.toFunction(zipper), false, bufferSize(), source1, source2, source3, source4, source5);
//    }
//
//    @SuppressWarnings("unchecked")
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T1, T2, T3, T4, T5, T6, R> Observable<R> zip(
//            ObservableSource<? extends T1> source1, ObservableSource<? extends T2> source2, ObservableSource<? extends T3> source3,
//            ObservableSource<? extends T4> source4, ObservableSource<? extends T5> source5, ObservableSource<? extends T6> source6,
//            Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? extends R> zipper) {
//        ObjectHelper.requireNonNull(source1, "source1 is null");
//        ObjectHelper.requireNonNull(source2, "source2 is null");
//        ObjectHelper.requireNonNull(source3, "source3 is null");
//        ObjectHelper.requireNonNull(source4, "source4 is null");
//        ObjectHelper.requireNonNull(source5, "source5 is null");
//        ObjectHelper.requireNonNull(source6, "source6 is null");
//        return zipArray(Functions.toFunction(zipper), false, bufferSize(), source1, source2, source3, source4, source5, source6);
//    }
//
//    @SuppressWarnings("unchecked")
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T1, T2, T3, T4, T5, T6, T7, R> Observable<R> zip(
//            ObservableSource<? extends T1> source1, ObservableSource<? extends T2> source2, ObservableSource<? extends T3> source3,
//            ObservableSource<? extends T4> source4, ObservableSource<? extends T5> source5, ObservableSource<? extends T6> source6,
//            ObservableSource<? extends T7> source7,
//            Function7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? extends R> zipper) {
//        ObjectHelper.requireNonNull(source1, "source1 is null");
//        ObjectHelper.requireNonNull(source2, "source2 is null");
//        ObjectHelper.requireNonNull(source3, "source3 is null");
//        ObjectHelper.requireNonNull(source4, "source4 is null");
//        ObjectHelper.requireNonNull(source5, "source5 is null");
//        ObjectHelper.requireNonNull(source6, "source6 is null");
//        ObjectHelper.requireNonNull(source7, "source7 is null");
//        return zipArray(Functions.toFunction(zipper), false, bufferSize(), source1, source2, source3, source4, source5, source6, source7);
//    }
//
//    @SuppressWarnings("unchecked")
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T1, T2, T3, T4, T5, T6, T7, T8, R> Observable<R> zip(
//            ObservableSource<? extends T1> source1, ObservableSource<? extends T2> source2, ObservableSource<? extends T3> source3,
//            ObservableSource<? extends T4> source4, ObservableSource<? extends T5> source5, ObservableSource<? extends T6> source6,
//            ObservableSource<? extends T7> source7, ObservableSource<? extends T8> source8,
//            Function8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? extends R> zipper) {
//        ObjectHelper.requireNonNull(source1, "source1 is null");
//        ObjectHelper.requireNonNull(source2, "source2 is null");
//        ObjectHelper.requireNonNull(source3, "source3 is null");
//        ObjectHelper.requireNonNull(source4, "source4 is null");
//        ObjectHelper.requireNonNull(source5, "source5 is null");
//        ObjectHelper.requireNonNull(source6, "source6 is null");
//        ObjectHelper.requireNonNull(source7, "source7 is null");
//        ObjectHelper.requireNonNull(source8, "source8 is null");
//        return zipArray(Functions.toFunction(zipper), false, bufferSize(), source1, source2, source3, source4, source5, source6, source7, source8);
//    }
//
//    @SuppressWarnings("unchecked")
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> Observable<R> zip(
//            ObservableSource<? extends T1> source1, ObservableSource<? extends T2> source2, ObservableSource<? extends T3> source3,
//            ObservableSource<? extends T4> source4, ObservableSource<? extends T5> source5, ObservableSource<? extends T6> source6,
//            ObservableSource<? extends T7> source7, ObservableSource<? extends T8> source8, ObservableSource<? extends T9> source9,
//            Function9<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? extends R> zipper) {
//        ObjectHelper.requireNonNull(source1, "source1 is null");
//        ObjectHelper.requireNonNull(source2, "source2 is null");
//        ObjectHelper.requireNonNull(source3, "source3 is null");
//        ObjectHelper.requireNonNull(source4, "source4 is null");
//        ObjectHelper.requireNonNull(source5, "source5 is null");
//        ObjectHelper.requireNonNull(source6, "source6 is null");
//        ObjectHelper.requireNonNull(source7, "source7 is null");
//        ObjectHelper.requireNonNull(source8, "source8 is null");
//        ObjectHelper.requireNonNull(source9, "source9 is null");
//        return zipArray(Functions.toFunction(zipper), false, bufferSize(), source1, source2, source3, source4, source5, source6, source7, source8, source9);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T, R> Observable<R> zipArray(Function<? super Object[], ? extends R> zipper,
//                                                              boolean delayError, int bufferSize, ObservableSource<? extends T>... sources) {
//        if (sources.length == 0) {
//            return empty();
//        }
//        ObjectHelper.requireNonNull(zipper, "zipper is null");
//        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
//        return RxJavaPlugins.onAssembly(new ObservableZip<T, R>(sources, null, zipper, bufferSize, delayError));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public static <T, R> Observable<R> zipIterable(Iterable<? extends ObservableSource<? extends T>> sources,
//                                                                 Function<? super Object[], ? extends R> zipper, boolean delayError,
//                                                                 int bufferSize) {
//        ObjectHelper.requireNonNull(zipper, "zipper is null");
//        ObjectHelper.requireNonNull(sources, "sources is null");
//        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
//        return RxJavaPlugins.onAssembly(new ObservableZip<T, R>(null, sources, zipper, bufferSize, delayError));
//    }
//
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Single<Boolean> all(Predicate<? super T> predicate) {
//        ObjectHelper.requireNonNull(predicate, "predicate is null");
//        return RxJavaPlugins.onAssembly(new ObservableAllSingle<T>(this, predicate));
//    }
//
//    @SuppressWarnings("unchecked")
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> ambWith(ObservableSource<? extends T> other) {
//        ObjectHelper.requireNonNull(other, "other is null");
//        return ambArray(this, other);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Single<Boolean> any(Predicate<? super T> predicate) {
//        ObjectHelper.requireNonNull(predicate, "predicate is null");
//        return RxJavaPlugins.onAssembly(new ObservableAnySingle<T>(this, predicate));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <R> R as(@NonNull ObservableConverter<T, ? extends R> converter) {
//        return ObjectHelper.requireNonNull(converter, "converter is null").apply(this);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final T blockingFirst() {
//        BlockingFirstObserver<T> observer = new BlockingFirstObserver<T>();
//        subscribe(observer);
//        T v = observer.blockingGet();
//        if (v != null) {
//            return v;
//        }
//        throw new NoSuchElementException();
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final T blockingFirst(T defaultItem) {
//        BlockingFirstObserver<T> observer = new BlockingFirstObserver<T>();
//        subscribe(observer);
//        T v = observer.blockingGet();
//        return v != null ? v : defaultItem;
//    }
//
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final void blockingForEach(Consumer<? super T> onNext) {
//        Iterator<T> it = blockingIterable().iterator();
//        while (it.hasNext()) {
//            try {
//                onNext.accept(it.next());
//            } catch (Throwable e) {
//                Exceptions.throwIfFatal(e);
//                ((Disposable) it).dispose();
//                throw ExceptionHelper.wrapOrThrow(e);
//            }
//        }
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Iterable<T> blockingIterable() {
//        return blockingIterable(bufferSize());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Iterable<T> blockingIterable(int bufferSize) {
//        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
//        return new BlockingObservableIterable<T>(this, bufferSize);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final T blockingLast() {
//        BlockingLastObserver<T> observer = new BlockingLastObserver<T>();
//        subscribe(observer);
//        T v = observer.blockingGet();
//        if (v != null) {
//            return v;
//        }
//        throw new NoSuchElementException();
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final T blockingLast(T defaultItem) {
//        BlockingLastObserver<T> observer = new BlockingLastObserver<T>();
//        subscribe(observer);
//        T v = observer.blockingGet();
//        return v != null ? v : defaultItem;
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Iterable<T> blockingLatest() {
//        return new BlockingObservableLatest<T>(this);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Iterable<T> blockingMostRecent(T initialValue) {
//        return new BlockingObservableMostRecent<T>(this, initialValue);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Iterable<T> blockingNext() {
//        return new BlockingObservableNext<T>(this);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final T blockingSingle() {
//        T v = singleElement().blockingGet();
//        if (v == null) {
//            throw new NoSuchElementException();
//        }
//        return v;
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final T blockingSingle(T defaultItem) {
//        return single(defaultItem).blockingGet();
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Future<T> toFuture() {
//        return subscribeWith(new FutureObserver<T>());
//    }
//
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final void blockingSubscribe() {
//        ObservableBlockingSubscribe.subscribe(this);
//    }
//
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final void blockingSubscribe(Consumer<? super T> onNext) {
//        ObservableBlockingSubscribe.subscribe(this, onNext, Functions.ON_ERROR_MISSING, Functions.EMPTY_ACTION);
//    }
//
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final void blockingSubscribe(Consumer<? super T> onNext, Consumer<? super Throwable> onError) {
//        ObservableBlockingSubscribe.subscribe(this, onNext, onError, Functions.EMPTY_ACTION);
//    }
//
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final void blockingSubscribe(Consumer<? super T> onNext, Consumer<? super Throwable> onError, Action onComplete) {
//        ObservableBlockingSubscribe.subscribe(this, onNext, onError, onComplete);
//    }
//
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final void blockingSubscribe(Observer<? super T> observer) {
//        ObservableBlockingSubscribe.subscribe(this, observer);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<List<T>> buffer(int count) {
//        return buffer(count, count);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<List<T>> buffer(int count, int skip) {
//        return buffer(count, skip, ArrayListSupplier.<T>asCallable());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <U extends Collection<? super T>> Observable<U> buffer(int count, int skip, Callable<U> bufferSupplier) {
//        ObjectHelper.verifyPositive(count, "count");
//        ObjectHelper.verifyPositive(skip, "skip");
//        ObjectHelper.requireNonNull(bufferSupplier, "bufferSupplier is null");
//        return RxJavaPlugins.onAssembly(new ObservableBuffer<T, U>(this, count, skip, bufferSupplier));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <U extends Collection<? super T>> Observable<U> buffer(int count, Callable<U> bufferSupplier) {
//        return buffer(count, count, bufferSupplier);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.COMPUTATION)
//    public final Observable<List<T>> buffer(long timespan, long timeskip, TimeUnit unit) {
//        return buffer(timespan, timeskip, unit, Schedulers.computation(), ArrayListSupplier.<T>asCallable());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public final Observable<List<T>> buffer(long timespan, long timeskip, TimeUnit unit, Scheduler scheduler) {
//        return buffer(timespan, timeskip, unit, scheduler, ArrayListSupplier.<T>asCallable());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public final <U extends Collection<? super T>> Observable<U> buffer(long timespan, long timeskip, TimeUnit unit, Scheduler scheduler, Callable<U> bufferSupplier) {
//        ObjectHelper.requireNonNull(unit, "unit is null");
//        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
//        ObjectHelper.requireNonNull(bufferSupplier, "bufferSupplier is null");
//        return RxJavaPlugins.onAssembly(new ObservableBufferTimed<T, U>(this, timespan, timeskip, unit, scheduler, bufferSupplier, Integer.MAX_VALUE, false));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.COMPUTATION)
//    public final Observable<List<T>> buffer(long timespan, TimeUnit unit) {
//        return buffer(timespan, unit, Schedulers.computation(), Integer.MAX_VALUE);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.COMPUTATION)
//    public final Observable<List<T>> buffer(long timespan, TimeUnit unit, int count) {
//        return buffer(timespan, unit, Schedulers.computation(), count);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public final Observable<List<T>> buffer(long timespan, TimeUnit unit, Scheduler scheduler, int count) {
//        return buffer(timespan, unit, scheduler, count, ArrayListSupplier.<T>asCallable(), false);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public final <U extends Collection<? super T>> Observable<U> buffer(
//            long timespan, TimeUnit unit,
//            Scheduler scheduler, int count,
//            Callable<U> bufferSupplier,
//            boolean restartTimerOnMaxSize) {
//        ObjectHelper.requireNonNull(unit, "unit is null");
//        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
//        ObjectHelper.requireNonNull(bufferSupplier, "bufferSupplier is null");
//        ObjectHelper.verifyPositive(count, "count");
//        return RxJavaPlugins.onAssembly(new ObservableBufferTimed<T, U>(this, timespan, timespan, unit, scheduler, bufferSupplier, count, restartTimerOnMaxSize));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public final Observable<List<T>> buffer(long timespan, TimeUnit unit, Scheduler scheduler) {
//        return buffer(timespan, unit, scheduler, Integer.MAX_VALUE, ArrayListSupplier.<T>asCallable(), false);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <TOpening, TClosing> Observable<List<T>> buffer(
//            ObservableSource<? extends TOpening> openingIndicator,
//            Function<? super TOpening, ? extends ObservableSource<? extends TClosing>> closingIndicator) {
//        return buffer(openingIndicator, closingIndicator, ArrayListSupplier.<T>asCallable());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <TOpening, TClosing, U extends Collection<? super T>> Observable<U> buffer(
//            ObservableSource<? extends TOpening> openingIndicator,
//            Function<? super TOpening, ? extends ObservableSource<? extends TClosing>> closingIndicator,
//            Callable<U> bufferSupplier) {
//        ObjectHelper.requireNonNull(openingIndicator, "openingIndicator is null");
//        ObjectHelper.requireNonNull(closingIndicator, "closingIndicator is null");
//        ObjectHelper.requireNonNull(bufferSupplier, "bufferSupplier is null");
//        return RxJavaPlugins.onAssembly(new ObservableBufferBoundary<T, U, TOpening, TClosing>(this, openingIndicator, closingIndicator, bufferSupplier));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <B> Observable<List<T>> buffer(ObservableSource<B> boundary) {
//        return buffer(boundary, ArrayListSupplier.<T>asCallable());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <B> Observable<List<T>> buffer(ObservableSource<B> boundary, final int initialCapacity) {
//        ObjectHelper.verifyPositive(initialCapacity, "initialCapacity");
//        return buffer(boundary, Functions.<T>createArrayList(initialCapacity));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <B, U extends Collection<? super T>> Observable<U> buffer(ObservableSource<B> boundary, Callable<U> bufferSupplier) {
//        ObjectHelper.requireNonNull(boundary, "boundary is null");
//        ObjectHelper.requireNonNull(bufferSupplier, "bufferSupplier is null");
//        return RxJavaPlugins.onAssembly(new ObservableBufferExactBoundary<T, U, B>(this, boundary, bufferSupplier));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <B> Observable<List<T>> buffer(Callable<? extends ObservableSource<B>> boundarySupplier) {
//        return buffer(boundarySupplier, ArrayListSupplier.<T>asCallable());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <B, U extends Collection<? super T>> Observable<U> buffer(Callable<? extends ObservableSource<B>> boundarySupplier, Callable<U> bufferSupplier) {
//        ObjectHelper.requireNonNull(boundarySupplier, "boundarySupplier is null");
//        ObjectHelper.requireNonNull(bufferSupplier, "bufferSupplier is null");
//        return RxJavaPlugins.onAssembly(new ObservableBufferBoundarySupplier<T, U, B>(this, boundarySupplier, bufferSupplier));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> cache() {
//        return cacheWithInitialCapacity(16);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> cacheWithInitialCapacity(int initialCapacity) {
//        ObjectHelper.verifyPositive(initialCapacity, "initialCapacity");
//        return RxJavaPlugins.onAssembly(new ObservableCache<T>(this, initialCapacity));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <U> Observable<U> cast(final Class<U> clazz) {
//        ObjectHelper.requireNonNull(clazz, "clazz is null");
//        return map(Functions.castFunction(clazz));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <U> Single<U> collect(Callable<? extends U> initialValueSupplier, BiConsumer<? super U, ? super T> collector) {
//        ObjectHelper.requireNonNull(initialValueSupplier, "initialValueSupplier is null");
//        ObjectHelper.requireNonNull(collector, "collector is null");
//        return RxJavaPlugins.onAssembly(new ObservableCollectSingle<T, U>(this, initialValueSupplier, collector));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <U> Single<U> collectInto(final U initialValue, BiConsumer<? super U, ? super T> collector) {
//        ObjectHelper.requireNonNull(initialValue, "initialValue is null");
//        return collect(Functions.justCallable(initialValue), collector);
//    }
//
//    @SuppressWarnings("unchecked")
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <R> Observable<R> compose(ObservableTransformer<? super T, ? extends R> composer) {
//        return wrap(((ObservableTransformer<T, R>) ObjectHelper.requireNonNull(composer, "composer is null")).apply(this));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <R> Observable<R> concatMap(Function<? super T, ? extends ObservableSource<? extends R>> mapper) {
//        return concatMap(mapper, 2);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <R> Observable<R> concatMap(Function<? super T, ? extends ObservableSource<? extends R>> mapper, int prefetch) {
//        ObjectHelper.requireNonNull(mapper, "mapper is null");
//        ObjectHelper.verifyPositive(prefetch, "prefetch");
//        if (this instanceof ScalarCallable) {
//            @SuppressWarnings("unchecked")
//            T v = ((ScalarCallable<T>) this).call();
//            if (v == null) {
//                return empty();
//            }
//            return ObservableScalarXMap.scalarXMap(v, mapper);
//        }
//        return RxJavaPlugins.onAssembly(new ObservableConcatMap<T, R>(this, mapper, prefetch, ErrorMode.IMMEDIATE));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <R> Observable<R> concatMapDelayError(Function<? super T, ? extends ObservableSource<? extends R>> mapper) {
//        return concatMapDelayError(mapper, bufferSize(), true);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <R> Observable<R> concatMapDelayError(Function<? super T, ? extends ObservableSource<? extends R>> mapper,
//                                                                     int prefetch, boolean tillTheEnd) {
//        ObjectHelper.requireNonNull(mapper, "mapper is null");
//        ObjectHelper.verifyPositive(prefetch, "prefetch");
//        if (this instanceof ScalarCallable) {
//            @SuppressWarnings("unchecked")
//            T v = ((ScalarCallable<T>) this).call();
//            if (v == null) {
//                return empty();
//            }
//            return ObservableScalarXMap.scalarXMap(v, mapper);
//        }
//        return RxJavaPlugins.onAssembly(new ObservableConcatMap<T, R>(this, mapper, prefetch, tillTheEnd ? ErrorMode.END : ErrorMode.BOUNDARY));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <R> Observable<R> concatMapEager(Function<? super T, ? extends ObservableSource<? extends R>> mapper) {
//        return concatMapEager(mapper, Integer.MAX_VALUE, bufferSize());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <R> Observable<R> concatMapEager(Function<? super T, ? extends ObservableSource<? extends R>> mapper,
//                                                                int maxConcurrency, int prefetch) {
//        ObjectHelper.requireNonNull(mapper, "mapper is null");
//        ObjectHelper.verifyPositive(maxConcurrency, "maxConcurrency");
//        ObjectHelper.verifyPositive(prefetch, "prefetch");
//        return RxJavaPlugins.onAssembly(new ObservableConcatMapEager<T, R>(this, mapper, ErrorMode.IMMEDIATE, maxConcurrency, prefetch));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <R> Observable<R> concatMapEagerDelayError(Function<? super T, ? extends ObservableSource<? extends R>> mapper,
//                                                                          boolean tillTheEnd) {
//        return concatMapEagerDelayError(mapper, Integer.MAX_VALUE, bufferSize(), tillTheEnd);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <R> Observable<R> concatMapEagerDelayError(Function<? super T, ? extends ObservableSource<? extends R>> mapper,
//                                                                          int maxConcurrency, int prefetch, boolean tillTheEnd) {
//        ObjectHelper.requireNonNull(mapper, "mapper is null");
//        ObjectHelper.verifyPositive(maxConcurrency, "maxConcurrency");
//        ObjectHelper.verifyPositive(prefetch, "prefetch");
//        return RxJavaPlugins.onAssembly(new ObservableConcatMapEager<T, R>(this, mapper, tillTheEnd ? ErrorMode.END : ErrorMode.BOUNDARY, maxConcurrency, prefetch));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Completable concatMapCompletable(Function<? super T, ? extends CompletableSource> mapper) {
//        return concatMapCompletable(mapper, 2);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Completable concatMapCompletable(Function<? super T, ? extends CompletableSource> mapper, int capacityHint) {
//        ObjectHelper.requireNonNull(mapper, "mapper is null");
//        ObjectHelper.verifyPositive(capacityHint, "capacityHint");
//        return RxJavaPlugins.onAssembly(new ObservableConcatMapCompletable<T>(this, mapper, ErrorMode.IMMEDIATE, capacityHint));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Completable concatMapCompletableDelayError(Function<? super T, ? extends CompletableSource> mapper) {
//        return concatMapCompletableDelayError(mapper, true, 2);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Completable concatMapCompletableDelayError(Function<? super T, ? extends CompletableSource> mapper, boolean tillTheEnd) {
//        return concatMapCompletableDelayError(mapper, tillTheEnd, 2);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Completable concatMapCompletableDelayError(Function<? super T, ? extends CompletableSource> mapper, boolean tillTheEnd, int prefetch) {
//        ObjectHelper.requireNonNull(mapper, "mapper is null");
//        ObjectHelper.verifyPositive(prefetch, "prefetch");
//        return RxJavaPlugins.onAssembly(new ObservableConcatMapCompletable<T>(this, mapper, tillTheEnd ? ErrorMode.END : ErrorMode.BOUNDARY, prefetch));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <U> Observable<U> concatMapIterable(final Function<? super T, ? extends Iterable<? extends U>> mapper) {
//        ObjectHelper.requireNonNull(mapper, "mapper is null");
//        return RxJavaPlugins.onAssembly(new ObservableFlattenIterable<T, U>(this, mapper));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <U> Observable<U> concatMapIterable(final Function<? super T, ? extends Iterable<? extends U>> mapper, int prefetch) {
//        ObjectHelper.requireNonNull(mapper, "mapper is null");
//        ObjectHelper.verifyPositive(prefetch, "prefetch");
//        return concatMap(ObservableInternalHelper.flatMapIntoIterable(mapper), prefetch);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <R> Observable<R> concatMapMaybe(Function<? super T, ? extends MaybeSource<? extends R>> mapper) {
//        return concatMapMaybe(mapper, 2);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <R> Observable<R> concatMapMaybe(Function<? super T, ? extends MaybeSource<? extends R>> mapper, int prefetch) {
//        ObjectHelper.requireNonNull(mapper, "mapper is null");
//        ObjectHelper.verifyPositive(prefetch, "prefetch");
//        return RxJavaPlugins.onAssembly(new ObservableConcatMapMaybe<T, R>(this, mapper, ErrorMode.IMMEDIATE, prefetch));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <R> Observable<R> concatMapMaybeDelayError(Function<? super T, ? extends MaybeSource<? extends R>> mapper) {
//        return concatMapMaybeDelayError(mapper, true, 2);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <R> Observable<R> concatMapMaybeDelayError(Function<? super T, ? extends MaybeSource<? extends R>> mapper, boolean tillTheEnd) {
//        return concatMapMaybeDelayError(mapper, tillTheEnd, 2);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <R> Observable<R> concatMapMaybeDelayError(Function<? super T, ? extends MaybeSource<? extends R>> mapper, boolean tillTheEnd, int prefetch) {
//        ObjectHelper.requireNonNull(mapper, "mapper is null");
//        ObjectHelper.verifyPositive(prefetch, "prefetch");
//        return RxJavaPlugins.onAssembly(new ObservableConcatMapMaybe<T, R>(this, mapper, tillTheEnd ? ErrorMode.END : ErrorMode.BOUNDARY, prefetch));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <R> Observable<R> concatMapSingle(Function<? super T, ? extends SingleSource<? extends R>> mapper) {
//        return concatMapSingle(mapper, 2);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <R> Observable<R> concatMapSingle(Function<? super T, ? extends SingleSource<? extends R>> mapper, int prefetch) {
//        ObjectHelper.requireNonNull(mapper, "mapper is null");
//        ObjectHelper.verifyPositive(prefetch, "prefetch");
//        return RxJavaPlugins.onAssembly(new ObservableConcatMapSingle<T, R>(this, mapper, ErrorMode.IMMEDIATE, prefetch));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <R> Observable<R> concatMapSingleDelayError(Function<? super T, ? extends SingleSource<? extends R>> mapper) {
//        return concatMapSingleDelayError(mapper, true, 2);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <R> Observable<R> concatMapSingleDelayError(Function<? super T, ? extends SingleSource<? extends R>> mapper, boolean tillTheEnd) {
//        return concatMapSingleDelayError(mapper, tillTheEnd, 2);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <R> Observable<R> concatMapSingleDelayError(Function<? super T, ? extends SingleSource<? extends R>> mapper, boolean tillTheEnd, int prefetch) {
//        ObjectHelper.requireNonNull(mapper, "mapper is null");
//        ObjectHelper.verifyPositive(prefetch, "prefetch");
//        return RxJavaPlugins.onAssembly(new ObservableConcatMapSingle<T, R>(this, mapper, tillTheEnd ? ErrorMode.END : ErrorMode.BOUNDARY, prefetch));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> concatWith(ObservableSource<? extends T> other) {
//        ObjectHelper.requireNonNull(other, "other is null");
//        return concat(this, other);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> concatWith(@NonNull SingleSource<? extends T> other) {
//        ObjectHelper.requireNonNull(other, "other is null");
//        return RxJavaPlugins.onAssembly(new ObservableConcatWithSingle<T>(this, other));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> concatWith(@NonNull MaybeSource<? extends T> other) {
//        ObjectHelper.requireNonNull(other, "other is null");
//        return RxJavaPlugins.onAssembly(new ObservableConcatWithMaybe<T>(this, other));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> concatWith(@NonNull CompletableSource other) {
//        ObjectHelper.requireNonNull(other, "other is null");
//        return RxJavaPlugins.onAssembly(new ObservableConcatWithCompletable<T>(this, other));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Single<Boolean> contains(final Object element) {
//        ObjectHelper.requireNonNull(element, "element is null");
//        return any(Functions.equalsWith(element));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Single<Long> count() {
//        return RxJavaPlugins.onAssembly(new ObservableCountSingle<T>(this));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <U> Observable<T> debounce(Function<? super T, ? extends ObservableSource<U>> debounceSelector) {
//        ObjectHelper.requireNonNull(debounceSelector, "debounceSelector is null");
//        return RxJavaPlugins.onAssembly(new ObservableDebounce<T, U>(this, debounceSelector));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.COMPUTATION)
//    public final Observable<T> debounce(long timeout, TimeUnit unit) {
//        return debounce(timeout, unit, Schedulers.computation());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public final Observable<T> debounce(long timeout, TimeUnit unit, Scheduler scheduler) {
//        ObjectHelper.requireNonNull(unit, "unit is null");
//        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
//        return RxJavaPlugins.onAssembly(new ObservableDebounceTimed<T>(this, timeout, unit, scheduler));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> defaultIfEmpty(T defaultItem) {
//        ObjectHelper.requireNonNull(defaultItem, "defaultItem is null");
//        return switchIfEmpty(just(defaultItem));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <U> Observable<T> delay(final Function<? super T, ? extends ObservableSource<U>> itemDelay) {
//        ObjectHelper.requireNonNull(itemDelay, "itemDelay is null");
//        return flatMap(ObservableInternalHelper.itemDelay(itemDelay));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.COMPUTATION)
//    public final Observable<T> delay(long delay, TimeUnit unit) {
//        return delay(delay, unit, Schedulers.computation(), false);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.COMPUTATION)
//    public final Observable<T> delay(long delay, TimeUnit unit, boolean delayError) {
//        return delay(delay, unit, Schedulers.computation(), delayError);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public final Observable<T> delay(long delay, TimeUnit unit, Scheduler scheduler) {
//        return delay(delay, unit, scheduler, false);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public final Observable<T> delay(long delay, TimeUnit unit, Scheduler scheduler, boolean delayError) {
//        ObjectHelper.requireNonNull(unit, "unit is null");
//        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
//
//        return RxJavaPlugins.onAssembly(new ObservableDelay<T>(this, delay, unit, scheduler, delayError));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <U, V> Observable<T> delay(ObservableSource<U> subscriptionDelay,
//                                                          Function<? super T, ? extends ObservableSource<V>> itemDelay) {
//        return delaySubscription(subscriptionDelay).delay(itemDelay);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <U> Observable<T> delaySubscription(ObservableSource<U> other) {
//        ObjectHelper.requireNonNull(other, "other is null");
//        return RxJavaPlugins.onAssembly(new ObservableDelaySubscriptionOther<T, U>(this, other));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.COMPUTATION)
//    public final Observable<T> delaySubscription(long delay, TimeUnit unit) {
//        return delaySubscription(delay, unit, Schedulers.computation());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public final Observable<T> delaySubscription(long delay, TimeUnit unit, Scheduler scheduler) {
//        return delaySubscription(timer(delay, unit, scheduler));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    @Deprecated
//    @SuppressWarnings({"unchecked", "rawtypes"})
//    public final <T2> Observable<T2> dematerialize() {
//        return RxJavaPlugins.onAssembly(new ObservableDematerialize(this, Functions.identity()));
//    }
//
//    @Experimental
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <R> Observable<R> dematerialize(Function<? super T, Notification<R>> selector) {
//        ObjectHelper.requireNonNull(selector, "selector is null");
//        return RxJavaPlugins.onAssembly(new ObservableDematerialize<T, R>(this, selector));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> distinct() {
//        return distinct(Functions.identity(), Functions.createHashSet());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <K> Observable<T> distinct(Function<? super T, K> keySelector) {
//        return distinct(keySelector, Functions.createHashSet());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <K> Observable<T> distinct(Function<? super T, K> keySelector, Callable<? extends Collection<? super K>> collectionSupplier) {
//        ObjectHelper.requireNonNull(keySelector, "keySelector is null");
//        ObjectHelper.requireNonNull(collectionSupplier, "collectionSupplier is null");
//        return RxJavaPlugins.onAssembly(new ObservableDistinct<T, K>(this, keySelector, collectionSupplier));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> distinctUntilChanged() {
//        return distinctUntilChanged(Functions.identity());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <K> Observable<T> distinctUntilChanged(Function<? super T, K> keySelector) {
//        ObjectHelper.requireNonNull(keySelector, "keySelector is null");
//        return RxJavaPlugins.onAssembly(new ObservableDistinctUntilChanged<T, K>(this, keySelector, ObjectHelper.equalsPredicate()));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> distinctUntilChanged(BiPredicate<? super T, ? super T> comparer) {
//        ObjectHelper.requireNonNull(comparer, "comparer is null");
//        return RxJavaPlugins.onAssembly(new ObservableDistinctUntilChanged<T, T>(this, Functions.<T>identity(), comparer));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> doAfterNext(Consumer<? super T> onAfterNext) {
//        ObjectHelper.requireNonNull(onAfterNext, "onAfterNext is null");
//        return RxJavaPlugins.onAssembly(new ObservableDoAfterNext<T>(this, onAfterNext));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> doAfterTerminate(Action onFinally) {
//        ObjectHelper.requireNonNull(onFinally, "onFinally is null");
//        return doOnEach(Functions.emptyConsumer(), Functions.emptyConsumer(), Functions.EMPTY_ACTION, onFinally);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> doFinally(Action onFinally) {
//        ObjectHelper.requireNonNull(onFinally, "onFinally is null");
//        return RxJavaPlugins.onAssembly(new ObservableDoFinally<T>(this, onFinally));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> doOnDispose(Action onDispose) {
//        return doOnLifecycle(Functions.emptyConsumer(), onDispose);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> doOnComplete(Action onComplete) {
//        return doOnEach(Functions.emptyConsumer(), Functions.emptyConsumer(), onComplete, Functions.EMPTY_ACTION);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    private Observable<T> doOnEach(Consumer<? super T> onNext, Consumer<? super Throwable> onError, Action onComplete, Action onAfterTerminate) {
//        ObjectHelper.requireNonNull(onNext, "onNext is null");
//        ObjectHelper.requireNonNull(onError, "onError is null");
//        ObjectHelper.requireNonNull(onComplete, "onComplete is null");
//        ObjectHelper.requireNonNull(onAfterTerminate, "onAfterTerminate is null");
//        return RxJavaPlugins.onAssembly(new ObservableDoOnEach<T>(this, onNext, onError, onComplete, onAfterTerminate));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> doOnEach(final Consumer<? super Notification<T>> onNotification) {
//        ObjectHelper.requireNonNull(onNotification, "onNotification is null");
//        return doOnEach(
//                Functions.notificationOnNext(onNotification),
//                Functions.notificationOnError(onNotification),
//                Functions.notificationOnComplete(onNotification),
//                Functions.EMPTY_ACTION
//        );
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> doOnEach(final Observer<? super T> observer) {
//        ObjectHelper.requireNonNull(observer, "observer is null");
//        return doOnEach(
//                ObservableInternalHelper.observerOnNext(observer),
//                ObservableInternalHelper.observerOnError(observer),
//                ObservableInternalHelper.observerOnComplete(observer),
//                Functions.EMPTY_ACTION);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> doOnError(Consumer<? super Throwable> onError) {
//        return doOnEach(Functions.emptyConsumer(), onError, Functions.EMPTY_ACTION, Functions.EMPTY_ACTION);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> doOnLifecycle(final Consumer<? super Disposable> onSubscribe, final Action onDispose) {
//        ObjectHelper.requireNonNull(onSubscribe, "onSubscribe is null");
//        ObjectHelper.requireNonNull(onDispose, "onDispose is null");
//        return RxJavaPlugins.onAssembly(new ObservableDoOnLifecycle<T>(this, onSubscribe, onDispose));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> doOnNext(Consumer<? super T> onNext) {
//        return doOnEach(onNext, Functions.emptyConsumer(), Functions.EMPTY_ACTION, Functions.EMPTY_ACTION);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> doOnSubscribe(Consumer<? super Disposable> onSubscribe) {
//        return doOnLifecycle(onSubscribe, Functions.EMPTY_ACTION);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> doOnTerminate(final Action onTerminate) {
//        ObjectHelper.requireNonNull(onTerminate, "onTerminate is null");
//        return doOnEach(Functions.emptyConsumer(),
//                Functions.actionConsumer(onTerminate), onTerminate,
//                Functions.EMPTY_ACTION);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Maybe<T> elementAt(long index) {
//        if (index < 0) {
//            throw new IndexOutOfBoundsException("index >= 0 required but it was " + index);
//        }
//        return RxJavaPlugins.onAssembly(new ObservableElementAtMaybe<T>(this, index));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Single<T> elementAt(long index, T defaultItem) {
//        if (index < 0) {
//            throw new IndexOutOfBoundsException("index >= 0 required but it was " + index);
//        }
//        ObjectHelper.requireNonNull(defaultItem, "defaultItem is null");
//        return RxJavaPlugins.onAssembly(new ObservableElementAtSingle<T>(this, index, defaultItem));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Single<T> elementAtOrError(long index) {
//        if (index < 0) {
//            throw new IndexOutOfBoundsException("index >= 0 required but it was " + index);
//        }
//        return RxJavaPlugins.onAssembly(new ObservableElementAtSingle<T>(this, index, null));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> filter(Predicate<? super T> predicate) {
//        ObjectHelper.requireNonNull(predicate, "predicate is null");
//        return RxJavaPlugins.onAssembly(new ObservableFilter<T>(this, predicate));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Maybe<T> firstElement() {
//        return elementAt(0L);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Single<T> first(T defaultItem) {
//        return elementAt(0L, defaultItem);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Single<T> firstOrError() {
//        return elementAtOrError(0L);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <R> Observable<R> flatMap(Function<? super T, ? extends ObservableSource<? extends R>> mapper) {
//        return flatMap(mapper, false);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <R> Observable<R> flatMap(Function<? super T, ? extends ObservableSource<? extends R>> mapper, boolean delayErrors) {
//        return flatMap(mapper, delayErrors, Integer.MAX_VALUE);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <R> Observable<R> flatMap(Function<? super T, ? extends ObservableSource<? extends R>> mapper, boolean delayErrors, int maxConcurrency) {
//        return flatMap(mapper, delayErrors, maxConcurrency, bufferSize());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <R> Observable<R> flatMap(Function<? super T, ? extends ObservableSource<? extends R>> mapper,
//                                                         boolean delayErrors, int maxConcurrency, int bufferSize) {
//        ObjectHelper.requireNonNull(mapper, "mapper is null");
//        ObjectHelper.verifyPositive(maxConcurrency, "maxConcurrency");
//        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
//        if (this instanceof ScalarCallable) {
//            @SuppressWarnings("unchecked")
//            T v = ((ScalarCallable<T>) this).call();
//            if (v == null) {
//                return empty();
//            }
//            return ObservableScalarXMap.scalarXMap(v, mapper);
//        }
//        return RxJavaPlugins.onAssembly(new ObservableFlatMap<T, R>(this, mapper, delayErrors, maxConcurrency, bufferSize));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <R> Observable<R> flatMap(
//            Function<? super T, ? extends ObservableSource<? extends R>> onNextMapper,
//            Function<? super Throwable, ? extends ObservableSource<? extends R>> onErrorMapper,
//            Callable<? extends ObservableSource<? extends R>> onCompleteSupplier) {
//        ObjectHelper.requireNonNull(onNextMapper, "onNextMapper is null");
//        ObjectHelper.requireNonNull(onErrorMapper, "onErrorMapper is null");
//        ObjectHelper.requireNonNull(onCompleteSupplier, "onCompleteSupplier is null");
//        return merge(new ObservableMapNotification<T, R>(this, onNextMapper, onErrorMapper, onCompleteSupplier));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <R> Observable<R> flatMap(
//            Function<? super T, ? extends ObservableSource<? extends R>> onNextMapper,
//            Function<Throwable, ? extends ObservableSource<? extends R>> onErrorMapper,
//            Callable<? extends ObservableSource<? extends R>> onCompleteSupplier,
//            int maxConcurrency) {
//        ObjectHelper.requireNonNull(onNextMapper, "onNextMapper is null");
//        ObjectHelper.requireNonNull(onErrorMapper, "onErrorMapper is null");
//        ObjectHelper.requireNonNull(onCompleteSupplier, "onCompleteSupplier is null");
//        return merge(new ObservableMapNotification<T, R>(this, onNextMapper, onErrorMapper, onCompleteSupplier), maxConcurrency);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <R> Observable<R> flatMap(Function<? super T, ? extends ObservableSource<? extends R>> mapper, int maxConcurrency) {
//        return flatMap(mapper, false, maxConcurrency, bufferSize());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <U, R> Observable<R> flatMap(Function<? super T, ? extends ObservableSource<? extends U>> mapper,
//                                                            BiFunction<? super T, ? super U, ? extends R> resultSelector) {
//        return flatMap(mapper, resultSelector, false, bufferSize(), bufferSize());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <U, R> Observable<R> flatMap(Function<? super T, ? extends ObservableSource<? extends U>> mapper,
//                                                            BiFunction<? super T, ? super U, ? extends R> combiner, boolean delayErrors) {
//        return flatMap(mapper, combiner, delayErrors, bufferSize(), bufferSize());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <U, R> Observable<R> flatMap(Function<? super T, ? extends ObservableSource<? extends U>> mapper,
//                                                            BiFunction<? super T, ? super U, ? extends R> combiner, boolean delayErrors, int maxConcurrency) {
//        return flatMap(mapper, combiner, delayErrors, maxConcurrency, bufferSize());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <U, R> Observable<R> flatMap(final Function<? super T, ? extends ObservableSource<? extends U>> mapper,
//                                                            final BiFunction<? super T, ? super U, ? extends R> combiner, boolean delayErrors, int maxConcurrency, int bufferSize) {
//        ObjectHelper.requireNonNull(mapper, "mapper is null");
//        ObjectHelper.requireNonNull(combiner, "combiner is null");
//        return flatMap(ObservableInternalHelper.flatMapWithCombiner(mapper, combiner), delayErrors, maxConcurrency, bufferSize);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <U, R> Observable<R> flatMap(Function<? super T, ? extends ObservableSource<? extends U>> mapper,
//                                                            BiFunction<? super T, ? super U, ? extends R> combiner, int maxConcurrency) {
//        return flatMap(mapper, combiner, false, maxConcurrency, bufferSize());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Completable flatMapCompletable(Function<? super T, ? extends CompletableSource> mapper) {
//        return flatMapCompletable(mapper, false);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Completable flatMapCompletable(Function<? super T, ? extends CompletableSource> mapper, boolean delayErrors) {
//        ObjectHelper.requireNonNull(mapper, "mapper is null");
//        return RxJavaPlugins.onAssembly(new ObservableFlatMapCompletableCompletable<T>(this, mapper, delayErrors));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <U> Observable<U> flatMapIterable(final Function<? super T, ? extends Iterable<? extends U>> mapper) {
//        ObjectHelper.requireNonNull(mapper, "mapper is null");
//        return RxJavaPlugins.onAssembly(new ObservableFlattenIterable<T, U>(this, mapper));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <U, V> Observable<V> flatMapIterable(final Function<? super T, ? extends Iterable<? extends U>> mapper,
//                                                                    BiFunction<? super T, ? super U, ? extends V> resultSelector) {
//        ObjectHelper.requireNonNull(mapper, "mapper is null");
//        ObjectHelper.requireNonNull(resultSelector, "resultSelector is null");
//        return flatMap(ObservableInternalHelper.flatMapIntoIterable(mapper), resultSelector, false, bufferSize(), bufferSize());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <R> Observable<R> flatMapMaybe(Function<? super T, ? extends MaybeSource<? extends R>> mapper) {
//        return flatMapMaybe(mapper, false);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <R> Observable<R> flatMapMaybe(Function<? super T, ? extends MaybeSource<? extends R>> mapper, boolean delayErrors) {
//        ObjectHelper.requireNonNull(mapper, "mapper is null");
//        return RxJavaPlugins.onAssembly(new ObservableFlatMapMaybe<T, R>(this, mapper, delayErrors));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <R> Observable<R> flatMapSingle(Function<? super T, ? extends SingleSource<? extends R>> mapper) {
//        return flatMapSingle(mapper, false);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <R> Observable<R> flatMapSingle(Function<? super T, ? extends SingleSource<? extends R>> mapper, boolean delayErrors) {
//        ObjectHelper.requireNonNull(mapper, "mapper is null");
//        return RxJavaPlugins.onAssembly(new ObservableFlatMapSingle<T, R>(this, mapper, delayErrors));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Disposable forEach(Consumer<? super T> onNext) {
//        return subscribe(onNext);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Disposable forEachWhile(Predicate<? super T> onNext) {
//        return forEachWhile(onNext, Functions.ON_ERROR_MISSING, Functions.EMPTY_ACTION);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Disposable forEachWhile(Predicate<? super T> onNext, Consumer<? super Throwable> onError) {
//        return forEachWhile(onNext, onError, Functions.EMPTY_ACTION);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Disposable forEachWhile(final Predicate<? super T> onNext, Consumer<? super Throwable> onError,
//                                         final Action onComplete) {
//        ObjectHelper.requireNonNull(onNext, "onNext is null");
//        ObjectHelper.requireNonNull(onError, "onError is null");
//        ObjectHelper.requireNonNull(onComplete, "onComplete is null");
//
//        ForEachWhileObserver<T> o = new ForEachWhileObserver<T>(onNext, onError, onComplete);
//        subscribe(o);
//        return o;
//    }
//
//    @SuppressWarnings({"unchecked", "rawtypes"})
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <K> Observable<GroupedObservable<K, T>> groupBy(Function<? super T, ? extends K> keySelector) {
//        return groupBy(keySelector, (Function) Functions.identity(), false, bufferSize());
//    }
//
//    @SuppressWarnings({"unchecked", "rawtypes"})
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <K> Observable<GroupedObservable<K, T>> groupBy(Function<? super T, ? extends K> keySelector, boolean delayError) {
//        return groupBy(keySelector, (Function) Functions.identity(), delayError, bufferSize());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <K, V> Observable<GroupedObservable<K, V>> groupBy(Function<? super T, ? extends K> keySelector,
//                                                                                  Function<? super T, ? extends V> valueSelector) {
//        return groupBy(keySelector, valueSelector, false, bufferSize());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <K, V> Observable<GroupedObservable<K, V>> groupBy(Function<? super T, ? extends K> keySelector,
//                                                                                  Function<? super T, ? extends V> valueSelector, boolean delayError) {
//        return groupBy(keySelector, valueSelector, delayError, bufferSize());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <K, V> Observable<GroupedObservable<K, V>> groupBy(Function<? super T, ? extends K> keySelector,
//                                                                                  Function<? super T, ? extends V> valueSelector,
//                                                                                  boolean delayError, int bufferSize) {
//        ObjectHelper.requireNonNull(keySelector, "keySelector is null");
//        ObjectHelper.requireNonNull(valueSelector, "valueSelector is null");
//        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
//
//        return RxJavaPlugins.onAssembly(new ObservableGroupBy<T, K, V>(this, keySelector, valueSelector, bufferSize, delayError));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <TRight, TLeftEnd, TRightEnd, R> Observable<R> groupJoin(
//            ObservableSource<? extends TRight> other,
//            Function<? super T, ? extends ObservableSource<TLeftEnd>> leftEnd,
//            Function<? super TRight, ? extends ObservableSource<TRightEnd>> rightEnd,
//            BiFunction<? super T, ? super Observable<TRight>, ? extends R> resultSelector
//    ) {
//        ObjectHelper.requireNonNull(other, "other is null");
//        ObjectHelper.requireNonNull(leftEnd, "leftEnd is null");
//        ObjectHelper.requireNonNull(rightEnd, "rightEnd is null");
//        ObjectHelper.requireNonNull(resultSelector, "resultSelector is null");
//        return RxJavaPlugins.onAssembly(new ObservableGroupJoin<T, TRight, TLeftEnd, TRightEnd, R>(
//                this, other, leftEnd, rightEnd, resultSelector));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> hide() {
//        return RxJavaPlugins.onAssembly(new ObservableHide<T>(this));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Completable ignoreElements() {
//        return RxJavaPlugins.onAssembly(new ObservableIgnoreElementsCompletable<T>(this));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Single<Boolean> isEmpty() {
//        return all(Functions.alwaysFalse());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <TRight, TLeftEnd, TRightEnd, R> Observable<R> join(
//            ObservableSource<? extends TRight> other,
//            Function<? super T, ? extends ObservableSource<TLeftEnd>> leftEnd,
//            Function<? super TRight, ? extends ObservableSource<TRightEnd>> rightEnd,
//            BiFunction<? super T, ? super TRight, ? extends R> resultSelector
//    ) {
//        ObjectHelper.requireNonNull(other, "other is null");
//        ObjectHelper.requireNonNull(leftEnd, "leftEnd is null");
//        ObjectHelper.requireNonNull(rightEnd, "rightEnd is null");
//        ObjectHelper.requireNonNull(resultSelector, "resultSelector is null");
//        return RxJavaPlugins.onAssembly(new ObservableJoin<T, TRight, TLeftEnd, TRightEnd, R>(
//                this, other, leftEnd, rightEnd, resultSelector));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Maybe<T> lastElement() {
//        return RxJavaPlugins.onAssembly(new ObservableLastMaybe<T>(this));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Single<T> last(T defaultItem) {
//        ObjectHelper.requireNonNull(defaultItem, "defaultItem is null");
//        return RxJavaPlugins.onAssembly(new ObservableLastSingle<T>(this, defaultItem));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Single<T> lastOrError() {
//        return RxJavaPlugins.onAssembly(new ObservableLastSingle<T>(this, null));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <R> Observable<R> lift(ObservableOperator<? extends R, ? super T> lifter) {
//        ObjectHelper.requireNonNull(lifter, "lifter is null");
//        return RxJavaPlugins.onAssembly(new ObservableLift<R, T>(this, lifter));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <R> Observable<R> map(Function<? super T, ? extends R> mapper) {
//        ObjectHelper.requireNonNull(mapper, "mapper is null");
//        return RxJavaPlugins.onAssembly(new ObservableMap<T, R>(this, mapper));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<Notification<T>> materialize() {
//        return RxJavaPlugins.onAssembly(new ObservableMaterialize<T>(this));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> mergeWith(ObservableSource<? extends T> other) {
//        ObjectHelper.requireNonNull(other, "other is null");
//        return merge(this, other);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> mergeWith(@NonNull SingleSource<? extends T> other) {
//        ObjectHelper.requireNonNull(other, "other is null");
//        return RxJavaPlugins.onAssembly(new ObservableMergeWithSingle<T>(this, other));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> mergeWith(@NonNull MaybeSource<? extends T> other) {
//        ObjectHelper.requireNonNull(other, "other is null");
//        return RxJavaPlugins.onAssembly(new ObservableMergeWithMaybe<T>(this, other));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> mergeWith(@NonNull CompletableSource other) {
//        ObjectHelper.requireNonNull(other, "other is null");
//        return RxJavaPlugins.onAssembly(new ObservableMergeWithCompletable<T>(this, other));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public final Observable<T> observeOn(Scheduler scheduler) {
//        return observeOn(scheduler, false, bufferSize());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public final Observable<T> observeOn(Scheduler scheduler, boolean delayError) {
//        return observeOn(scheduler, delayError, bufferSize());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public final Observable<T> observeOn(Scheduler scheduler, boolean delayError, int bufferSize) {
//        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
//        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
//        return RxJavaPlugins.onAssembly(new ObservableObserveOn<T>(this, scheduler, delayError, bufferSize));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <U> Observable<U> ofType(final Class<U> clazz) {
//        ObjectHelper.requireNonNull(clazz, "clazz is null");
//        return filter(Functions.isInstanceOf(clazz)).cast(clazz);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> onErrorResumeNext(Function<? super Throwable, ? extends ObservableSource<? extends T>> resumeFunction) {
//        ObjectHelper.requireNonNull(resumeFunction, "resumeFunction is null");
//        return RxJavaPlugins.onAssembly(new ObservableOnErrorNext<T>(this, resumeFunction, false));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> onErrorResumeNext(final ObservableSource<? extends T> next) {
//        ObjectHelper.requireNonNull(next, "next is null");
//        return onErrorResumeNext(Functions.justFunction(next));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> onErrorReturn(Function<? super Throwable, ? extends T> valueSupplier) {
//        ObjectHelper.requireNonNull(valueSupplier, "valueSupplier is null");
//        return RxJavaPlugins.onAssembly(new ObservableOnErrorReturn<T>(this, valueSupplier));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> onErrorReturnItem(final T item) {
//        ObjectHelper.requireNonNull(item, "item is null");
//        return onErrorReturn(Functions.justFunction(item));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> onExceptionResumeNext(final ObservableSource<? extends T> next) {
//        ObjectHelper.requireNonNull(next, "next is null");
//        return RxJavaPlugins.onAssembly(new ObservableOnErrorNext<T>(this, Functions.justFunction(next), true));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> onTerminateDetach() {
//        return RxJavaPlugins.onAssembly(new ObservableDetach<T>(this));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final ConnectableObservable<T> publish() {
//        return ObservablePublish.create(this);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <R> Observable<R> publish(Function<? super Observable<T>, ? extends ObservableSource<R>> selector) {
//        ObjectHelper.requireNonNull(selector, "selector is null");
//        return RxJavaPlugins.onAssembly(new ObservablePublishSelector<T, R>(this, selector));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Maybe<T> reduce(BiFunction<T, T, T> reducer) {
//        ObjectHelper.requireNonNull(reducer, "reducer is null");
//        return RxJavaPlugins.onAssembly(new ObservableReduceMaybe<T>(this, reducer));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <R> Single<R> reduce(R seed, BiFunction<R, ? super T, R> reducer) {
//        ObjectHelper.requireNonNull(seed, "seed is null");
//        ObjectHelper.requireNonNull(reducer, "reducer is null");
//        return RxJavaPlugins.onAssembly(new ObservableReduceSeedSingle<T, R>(this, seed, reducer));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <R> Single<R> reduceWith(Callable<R> seedSupplier, BiFunction<R, ? super T, R> reducer) {
//        ObjectHelper.requireNonNull(seedSupplier, "seedSupplier is null");
//        ObjectHelper.requireNonNull(reducer, "reducer is null");
//        return RxJavaPlugins.onAssembly(new ObservableReduceWithSingle<T, R>(this, seedSupplier, reducer));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> repeat() {
//        return repeat(Long.MAX_VALUE);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> repeat(long times) {
//        if (times < 0) {
//            throw new IllegalArgumentException("times >= 0 required but it was " + times);
//        }
//        if (times == 0) {
//            return empty();
//        }
//        return RxJavaPlugins.onAssembly(new ObservableRepeat<T>(this, times));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> repeatUntil(BooleanSupplier stop) {
//        ObjectHelper.requireNonNull(stop, "stop is null");
//        return RxJavaPlugins.onAssembly(new ObservableRepeatUntil<T>(this, stop));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> repeatWhen(final Function<? super Observable<Object>, ? extends ObservableSource<?>> handler) {
//        ObjectHelper.requireNonNull(handler, "handler is null");
//        return RxJavaPlugins.onAssembly(new ObservableRepeatWhen<T>(this, handler));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final ConnectableObservable<T> replay() {
//        return ObservableReplay.createFrom(this);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <R> Observable<R> replay(Function<? super Observable<T>, ? extends ObservableSource<R>> selector) {
//        ObjectHelper.requireNonNull(selector, "selector is null");
//        return ObservableReplay.multicastSelector(ObservableInternalHelper.replayCallable(this), selector);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <R> Observable<R> replay(Function<? super Observable<T>, ? extends ObservableSource<R>> selector, final int bufferSize) {
//        ObjectHelper.requireNonNull(selector, "selector is null");
//        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
//        return ObservableReplay.multicastSelector(ObservableInternalHelper.replayCallable(this, bufferSize), selector);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.COMPUTATION)
//    public final <R> Observable<R> replay(Function<? super Observable<T>, ? extends ObservableSource<R>> selector, int bufferSize, long time, TimeUnit unit) {
//        return replay(selector, bufferSize, time, unit, Schedulers.computation());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public final <R> Observable<R> replay(Function<? super Observable<T>, ? extends ObservableSource<R>> selector, final int bufferSize, final long time, final TimeUnit unit, final Scheduler scheduler) {
//        ObjectHelper.requireNonNull(selector, "selector is null");
//        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
//        ObjectHelper.requireNonNull(unit, "unit is null");
//        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
//        return ObservableReplay.multicastSelector(
//                ObservableInternalHelper.replayCallable(this, bufferSize, time, unit, scheduler), selector);
//    }
//
//    /**
//     * Returns an Observable that emits items that are the results of invoking a specified selector on items
//     * emitted by a {@link ConnectableObservable} that shares a single subscription to the source ObservableSource,
//     * replaying a maximum of {@code bufferSize} items.
//     * <p>
//     * Note that due to concurrency requirements, {@code replay(bufferSize)} may hold strong references to more than
//     * {@code bufferSize} source emissions.
//     * <p>
//     * <img width="640" height="362" src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/replay.o.fns.png" alt="">
//     * <dl>
//     *  <dt><b>Scheduler:</b></dt>
//     *  <dd>You specify which {@link Scheduler} this operator will use.</dd>
//     * </dl>
//     *
//     * @param <R>
//     *            the type of items emitted by the resulting ObservableSource
//     * @param selector
//     *            a selector function, which can use the multicasted sequence as many times as needed, without
//     *            causing multiple subscriptions to the ObservableSource
//     * @param bufferSize
//     *            the buffer size that limits the number of items the connectable ObservableSource can replay
//     * @param scheduler
//     *            the Scheduler on which the replay is observed
//     * @return an Observable that emits items that are the results of invoking the selector on items emitted by
//     *         a {@link ConnectableObservable} that shares a single subscription to the source ObservableSource,
//     *         replaying no more than {@code bufferSize} notifications
//     * @see <a href="http://reactivex.io/documentation/operators/replay.html">ReactiveX operators documentation: Replay</a>
//     */
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public final <R> Observable<R> replay(final Function<? super Observable<T>, ? extends ObservableSource<R>> selector, final int bufferSize, final Scheduler scheduler) {
//        ObjectHelper.requireNonNull(selector, "selector is null");
//        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
//        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
//        return ObservableReplay.multicastSelector(ObservableInternalHelper.replayCallable(this, bufferSize),
//                ObservableInternalHelper.replayFunction(selector, scheduler));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.COMPUTATION)
//    public final <R> Observable<R> replay(Function<? super Observable<T>, ? extends ObservableSource<R>> selector, long time, TimeUnit unit) {
//        return replay(selector, time, unit, Schedulers.computation());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public final <R> Observable<R> replay(Function<? super Observable<T>, ? extends ObservableSource<R>> selector, final long time, final TimeUnit unit, final Scheduler scheduler) {
//        ObjectHelper.requireNonNull(selector, "selector is null");
//        ObjectHelper.requireNonNull(unit, "unit is null");
//        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
//        return ObservableReplay.multicastSelector(ObservableInternalHelper.replayCallable(this, time, unit, scheduler), selector);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public final <R> Observable<R> replay(final Function<? super Observable<T>, ? extends ObservableSource<R>> selector, final Scheduler scheduler) {
//        ObjectHelper.requireNonNull(selector, "selector is null");
//        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
//        return ObservableReplay.multicastSelector(ObservableInternalHelper.replayCallable(this),
//                ObservableInternalHelper.replayFunction(selector, scheduler));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final ConnectableObservable<T> replay(final int bufferSize) {
//        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
//        return ObservableReplay.create(this, bufferSize);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.COMPUTATION)
//    public final ConnectableObservable<T> replay(int bufferSize, long time, TimeUnit unit) {
//        return replay(bufferSize, time, unit, Schedulers.computation());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public final ConnectableObservable<T> replay(final int bufferSize, final long time, final TimeUnit unit, final Scheduler scheduler) {
//        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
//        ObjectHelper.requireNonNull(unit, "unit is null");
//        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
//        return ObservableReplay.create(this, time, unit, scheduler, bufferSize);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public final ConnectableObservable<T> replay(final int bufferSize, final Scheduler scheduler) {
//        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
//        return ObservableReplay.observeOn(replay(bufferSize), scheduler);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.COMPUTATION)
//    public final ConnectableObservable<T> replay(long time, TimeUnit unit) {
//        return replay(time, unit, Schedulers.computation());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public final ConnectableObservable<T> replay(final long time, final TimeUnit unit, final Scheduler scheduler) {
//        ObjectHelper.requireNonNull(unit, "unit is null");
//        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
//        return ObservableReplay.create(this, time, unit, scheduler);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public final ConnectableObservable<T> replay(final Scheduler scheduler) {
//        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
//        return ObservableReplay.observeOn(replay(), scheduler);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> retry() {
//        return retry(Long.MAX_VALUE, Functions.alwaysTrue());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> retry(BiPredicate<? super Integer, ? super Throwable> predicate) {
//        ObjectHelper.requireNonNull(predicate, "predicate is null");
//
//        return RxJavaPlugins.onAssembly(new ObservableRetryBiPredicate<T>(this, predicate));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> retry(long times) {
//        return retry(times, Functions.alwaysTrue());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> retry(long times, Predicate<? super Throwable> predicate) {
//        if (times < 0) {
//            throw new IllegalArgumentException("times >= 0 required but it was " + times);
//        }
//        ObjectHelper.requireNonNull(predicate, "predicate is null");
//
//        return RxJavaPlugins.onAssembly(new ObservableRetryPredicate<T>(this, times, predicate));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> retry(Predicate<? super Throwable> predicate) {
//        return retry(Long.MAX_VALUE, predicate);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> retryUntil(final BooleanSupplier stop) {
//        ObjectHelper.requireNonNull(stop, "stop is null");
//        return retry(Long.MAX_VALUE, Functions.predicateReverseFor(stop));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> retryWhen(
//            final Function<? super Observable<Throwable>, ? extends ObservableSource<?>> handler) {
//        ObjectHelper.requireNonNull(handler, "handler is null");
//        return RxJavaPlugins.onAssembly(new ObservableRetryWhen<T>(this, handler));
//    }
//
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final void safeSubscribe(Observer<? super T> observer) {
//        ObjectHelper.requireNonNull(observer, "observer is null");
//        if (observer instanceof SafeObserver) {
//            subscribe(observer);
//        } else {
//            subscribe(new SafeObserver<T>(observer));
//        }
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.COMPUTATION)
//    public final Observable<T> sample(long period, TimeUnit unit) {
//        return sample(period, unit, Schedulers.computation());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.COMPUTATION)
//    public final Observable<T> sample(long period, TimeUnit unit, boolean emitLast) {
//        return sample(period, unit, Schedulers.computation(), emitLast);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public final Observable<T> sample(long period, TimeUnit unit, Scheduler scheduler) {
//        ObjectHelper.requireNonNull(unit, "unit is null");
//        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
//        return RxJavaPlugins.onAssembly(new ObservableSampleTimed<T>(this, period, unit, scheduler, false));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public final Observable<T> sample(long period, TimeUnit unit, Scheduler scheduler, boolean emitLast) {
//        ObjectHelper.requireNonNull(unit, "unit is null");
//        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
//        return RxJavaPlugins.onAssembly(new ObservableSampleTimed<T>(this, period, unit, scheduler, emitLast));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <U> Observable<T> sample(ObservableSource<U> sampler) {
//        ObjectHelper.requireNonNull(sampler, "sampler is null");
//        return RxJavaPlugins.onAssembly(new ObservableSampleWithObservable<T>(this, sampler, false));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <U> Observable<T> sample(ObservableSource<U> sampler, boolean emitLast) {
//        ObjectHelper.requireNonNull(sampler, "sampler is null");
//        return RxJavaPlugins.onAssembly(new ObservableSampleWithObservable<T>(this, sampler, emitLast));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> scan(BiFunction<T, T, T> accumulator) {
//        ObjectHelper.requireNonNull(accumulator, "accumulator is null");
//        return RxJavaPlugins.onAssembly(new ObservableScan<T>(this, accumulator));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <R> Observable<R> scan(final R initialValue, BiFunction<R, ? super T, R> accumulator) {
//        ObjectHelper.requireNonNull(initialValue, "initialValue is null");
//        return scanWith(Functions.justCallable(initialValue), accumulator);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <R> Observable<R> scanWith(Callable<R> seedSupplier, BiFunction<R, ? super T, R> accumulator) {
//        ObjectHelper.requireNonNull(seedSupplier, "seedSupplier is null");
//        ObjectHelper.requireNonNull(accumulator, "accumulator is null");
//        return RxJavaPlugins.onAssembly(new ObservableScanSeed<T, R>(this, seedSupplier, accumulator));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> serialize() {
//        return RxJavaPlugins.onAssembly(new ObservableSerialized<T>(this));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> share() {
//        return publish().refCount();
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Maybe<T> singleElement() {
//        return RxJavaPlugins.onAssembly(new ObservableSingleMaybe<T>(this));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Single<T> single(T defaultItem) {
//        ObjectHelper.requireNonNull(defaultItem, "defaultItem is null");
//        return RxJavaPlugins.onAssembly(new ObservableSingleSingle<T>(this, defaultItem));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Single<T> singleOrError() {
//        return RxJavaPlugins.onAssembly(new ObservableSingleSingle<T>(this, null));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> skip(long count) {
//        if (count <= 0) {
//            return RxJavaPlugins.onAssembly(this);
//        }
//        return RxJavaPlugins.onAssembly(new ObservableSkip<T>(this, count));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.COMPUTATION)
//    public final Observable<T> skip(long time, TimeUnit unit) {
//        return skipUntil(timer(time, unit));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public final Observable<T> skip(long time, TimeUnit unit, Scheduler scheduler) {
//        return skipUntil(timer(time, unit, scheduler));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> skipLast(int count) {
//        if (count < 0) {
//            throw new IndexOutOfBoundsException("count >= 0 required but it was " + count);
//        }
//        if (count == 0) {
//            return RxJavaPlugins.onAssembly(this);
//        }
//        return RxJavaPlugins.onAssembly(new ObservableSkipLast<T>(this, count));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.TRAMPOLINE)
//    public final Observable<T> skipLast(long time, TimeUnit unit) {
//        return skipLast(time, unit, Schedulers.trampoline(), false, bufferSize());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.TRAMPOLINE)
//    public final Observable<T> skipLast(long time, TimeUnit unit, boolean delayError) {
//        return skipLast(time, unit, Schedulers.trampoline(), delayError, bufferSize());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public final Observable<T> skipLast(long time, TimeUnit unit, Scheduler scheduler) {
//        return skipLast(time, unit, scheduler, false, bufferSize());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public final Observable<T> skipLast(long time, TimeUnit unit, Scheduler scheduler, boolean delayError) {
//        return skipLast(time, unit, scheduler, delayError, bufferSize());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public final Observable<T> skipLast(long time, TimeUnit unit, Scheduler scheduler, boolean delayError, int bufferSize) {
//        ObjectHelper.requireNonNull(unit, "unit is null");
//        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
//        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
//        // the internal buffer holds pairs of (timestamp, value) so double the default buffer size
//        int s = bufferSize << 1;
//        return RxJavaPlugins.onAssembly(new ObservableSkipLastTimed<T>(this, time, unit, scheduler, s, delayError));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <U> Observable<T> skipUntil(ObservableSource<U> other) {
//        ObjectHelper.requireNonNull(other, "other is null");
//        return RxJavaPlugins.onAssembly(new ObservableSkipUntil<T, U>(this, other));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> skipWhile(Predicate<? super T> predicate) {
//        ObjectHelper.requireNonNull(predicate, "predicate is null");
//        return RxJavaPlugins.onAssembly(new ObservableSkipWhile<T>(this, predicate));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> sorted() {
//        return toList().toObservable().map(Functions.listSorter(Functions.<T>naturalComparator())).flatMapIterable(Functions.<List<T>>identity());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> sorted(Comparator<? super T> sortFunction) {
//        ObjectHelper.requireNonNull(sortFunction, "sortFunction is null");
//        return toList().toObservable().map(Functions.listSorter(sortFunction)).flatMapIterable(Functions.<List<T>>identity());
//    }
//
//    @SuppressWarnings("unchecked")
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> startWith(Iterable<? extends T> items) {
//        return concatArray(fromIterable(items), this);
//    }
//
//    @SuppressWarnings("unchecked")
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> startWith(ObservableSource<? extends T> other) {
//        ObjectHelper.requireNonNull(other, "other is null");
//        return concatArray(other, this);
//    }
//
//    @SuppressWarnings("unchecked")
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> startWith(T item) {
//        ObjectHelper.requireNonNull(item, "item is null");
//        return concatArray(just(item), this);
//    }
//
//    @SuppressWarnings("unchecked")
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> startWithArray(T... items) {
//        Observable<T> fromArray = fromArray(items);
//        if (fromArray == empty()) {
//            return RxJavaPlugins.onAssembly(this);
//        }
//        return concatArray(fromArray, this);
//    }
//
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Disposable subscribe() {
//        return subscribe(Functions.emptyConsumer(), Functions.ON_ERROR_MISSING, Functions.EMPTY_ACTION, Functions.emptyConsumer());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Disposable subscribe(Consumer<? super T> onNext) {
//        return subscribe(onNext, Functions.ON_ERROR_MISSING, Functions.EMPTY_ACTION, Functions.emptyConsumer());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Disposable subscribe(Consumer<? super T> onNext, Consumer<? super Throwable> onError) {
//        return subscribe(onNext, onError, Functions.EMPTY_ACTION, Functions.emptyConsumer());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Disposable subscribe(Consumer<? super T> onNext, Consumer<? super Throwable> onError,
//                                      Action onComplete) {
//        return subscribe(onNext, onError, onComplete, Functions.emptyConsumer());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Disposable subscribe(Consumer<? super T> onNext, Consumer<? super Throwable> onError,
//                                      Action onComplete, Consumer<? super Disposable> onSubscribe) {
//        ObjectHelper.requireNonNull(onNext, "onNext is null");
//        ObjectHelper.requireNonNull(onError, "onError is null");
//        ObjectHelper.requireNonNull(onComplete, "onComplete is null");
//        ObjectHelper.requireNonNull(onSubscribe, "onSubscribe is null");
//
//        LambdaObserver<T> ls = new LambdaObserver<T>(onNext, onError, onComplete, onSubscribe);
//
//        subscribe(ls);
//
//        return ls;
//    }
//
//    @SchedulerSupport(SchedulerSupport.NONE)
//    @Override
//    public final void subscribe(Observer<? super T> observer) {
//        ObjectHelper.requireNonNull(observer, "observer is null");
//        try {
//            observer = RxJavaPlugins.onSubscribe(this, observer);
//
//            ObjectHelper.requireNonNull(observer, "The RxJavaPlugins.onSubscribe hook returned a null Observer. Please change the handler provided to RxJavaPlugins.setOnObservableSubscribe for invalid null returns. Further reading: https://github.com/ReactiveX/RxJava/wiki/Plugins");
//
//            subscribeActual(observer);
//        } catch (NullPointerException e) { // NOPMD
//            throw e;
//        } catch (Throwable e) {
//            Exceptions.throwIfFatal(e);
//            // can't call onError because no way to know if a Disposable has been set or not
//            // can't call onSubscribe because the call might have set a Subscription already
//            RxJavaPlugins.onError(e);
//
//            NullPointerException npe = new NullPointerException("Actually not, but can't throw other exceptions due to RS");
//            npe.initCause(e);
//            throw npe;
//        }
//    }
//
//    protected abstract void subscribeActual(Observer<? super T> observer);
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <E extends Observer<? super T>> E subscribeWith(E observer) {
//        subscribe(observer);
//        return observer;
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public final Observable<T> subscribeOn(Scheduler scheduler) {
//        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
//        return RxJavaPlugins.onAssembly(new ObservableSubscribeOn<T>(this, scheduler));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> switchIfEmpty(ObservableSource<? extends T> other) {
//        ObjectHelper.requireNonNull(other, "other is null");
//        return RxJavaPlugins.onAssembly(new ObservableSwitchIfEmpty<T>(this, other));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <R> Observable<R> switchMap(Function<? super T, ? extends ObservableSource<? extends R>> mapper) {
//        return switchMap(mapper, bufferSize());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <R> Observable<R> switchMap(Function<? super T, ? extends ObservableSource<? extends R>> mapper, int bufferSize) {
//        ObjectHelper.requireNonNull(mapper, "mapper is null");
//        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
//        if (this instanceof ScalarCallable) {
//            @SuppressWarnings("unchecked")
//            T v = ((ScalarCallable<T>) this).call();
//            if (v == null) {
//                return empty();
//            }
//            return ObservableScalarXMap.scalarXMap(v, mapper);
//        }
//        return RxJavaPlugins.onAssembly(new ObservableSwitchMap<T, R>(this, mapper, bufferSize, false));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Completable switchMapCompletable(@NonNull Function<? super T, ? extends CompletableSource> mapper) {
//        ObjectHelper.requireNonNull(mapper, "mapper is null");
//        return RxJavaPlugins.onAssembly(new ObservableSwitchMapCompletable<T>(this, mapper, false));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Completable switchMapCompletableDelayError(@NonNull Function<? super T, ? extends CompletableSource> mapper) {
//        ObjectHelper.requireNonNull(mapper, "mapper is null");
//        return RxJavaPlugins.onAssembly(new ObservableSwitchMapCompletable<T>(this, mapper, true));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <R> Observable<R> switchMapMaybe(@NonNull Function<? super T, ? extends MaybeSource<? extends R>> mapper) {
//        ObjectHelper.requireNonNull(mapper, "mapper is null");
//        return RxJavaPlugins.onAssembly(new ObservableSwitchMapMaybe<T, R>(this, mapper, false));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <R> Observable<R> switchMapMaybeDelayError(@NonNull Function<? super T, ? extends MaybeSource<? extends R>> mapper) {
//        ObjectHelper.requireNonNull(mapper, "mapper is null");
//        return RxJavaPlugins.onAssembly(new ObservableSwitchMapMaybe<T, R>(this, mapper, true));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    @NonNull
//    public final <R> Observable<R> switchMapSingle(@NonNull Function<? super T, ? extends SingleSource<? extends R>> mapper) {
//        ObjectHelper.requireNonNull(mapper, "mapper is null");
//        return RxJavaPlugins.onAssembly(new ObservableSwitchMapSingle<T, R>(this, mapper, false));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    @NonNull
//    public final <R> Observable<R> switchMapSingleDelayError(@NonNull Function<? super T, ? extends SingleSource<? extends R>> mapper) {
//        ObjectHelper.requireNonNull(mapper, "mapper is null");
//        return RxJavaPlugins.onAssembly(new ObservableSwitchMapSingle<T, R>(this, mapper, true));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <R> Observable<R> switchMapDelayError(Function<? super T, ? extends ObservableSource<? extends R>> mapper) {
//        return switchMapDelayError(mapper, bufferSize());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <R> Observable<R> switchMapDelayError(Function<? super T, ? extends ObservableSource<? extends R>> mapper, int bufferSize) {
//        ObjectHelper.requireNonNull(mapper, "mapper is null");
//        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
//        if (this instanceof ScalarCallable) {
//            @SuppressWarnings("unchecked")
//            T v = ((ScalarCallable<T>) this).call();
//            if (v == null) {
//                return empty();
//            }
//            return ObservableScalarXMap.scalarXMap(v, mapper);
//        }
//        return RxJavaPlugins.onAssembly(new ObservableSwitchMap<T, R>(this, mapper, bufferSize, true));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> take(long count) {
//        if (count < 0) {
//            throw new IllegalArgumentException("count >= 0 required but it was " + count);
//        }
//        return RxJavaPlugins.onAssembly(new ObservableTake<T>(this, count));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> take(long time, TimeUnit unit) {
//        return takeUntil(timer(time, unit));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public final Observable<T> take(long time, TimeUnit unit, Scheduler scheduler) {
//        return takeUntil(timer(time, unit, scheduler));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> takeLast(int count) {
//        if (count < 0) {
//            throw new IndexOutOfBoundsException("count >= 0 required but it was " + count);
//        }
//        if (count == 0) {
//            return RxJavaPlugins.onAssembly(new ObservableIgnoreElements<T>(this));
//        }
//        if (count == 1) {
//            return RxJavaPlugins.onAssembly(new ObservableTakeLastOne<T>(this));
//        }
//        return RxJavaPlugins.onAssembly(new ObservableTakeLast<T>(this, count));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.TRAMPOLINE)
//    public final Observable<T> takeLast(long count, long time, TimeUnit unit) {
//        return takeLast(count, time, unit, Schedulers.trampoline(), false, bufferSize());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public final Observable<T> takeLast(long count, long time, TimeUnit unit, Scheduler scheduler) {
//        return takeLast(count, time, unit, scheduler, false, bufferSize());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public final Observable<T> takeLast(long count, long time, TimeUnit unit, Scheduler scheduler, boolean delayError, int bufferSize) {
//        ObjectHelper.requireNonNull(unit, "unit is null");
//        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
//        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
//        if (count < 0) {
//            throw new IndexOutOfBoundsException("count >= 0 required but it was " + count);
//        }
//        return RxJavaPlugins.onAssembly(new ObservableTakeLastTimed<T>(this, count, time, unit, scheduler, bufferSize, delayError));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.TRAMPOLINE)
//    public final Observable<T> takeLast(long time, TimeUnit unit) {
//        return takeLast(time, unit, Schedulers.trampoline(), false, bufferSize());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.TRAMPOLINE)
//    public final Observable<T> takeLast(long time, TimeUnit unit, boolean delayError) {
//        return takeLast(time, unit, Schedulers.trampoline(), delayError, bufferSize());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public final Observable<T> takeLast(long time, TimeUnit unit, Scheduler scheduler) {
//        return takeLast(time, unit, scheduler, false, bufferSize());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public final Observable<T> takeLast(long time, TimeUnit unit, Scheduler scheduler, boolean delayError) {
//        return takeLast(time, unit, scheduler, delayError, bufferSize());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public final Observable<T> takeLast(long time, TimeUnit unit, Scheduler scheduler, boolean delayError, int bufferSize) {
//        return takeLast(Long.MAX_VALUE, time, unit, scheduler, delayError, bufferSize);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <U> Observable<T> takeUntil(ObservableSource<U> other) {
//        ObjectHelper.requireNonNull(other, "other is null");
//        return RxJavaPlugins.onAssembly(new ObservableTakeUntil<T, U>(this, other));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> takeUntil(Predicate<? super T> stopPredicate) {
//        ObjectHelper.requireNonNull(stopPredicate, "stopPredicate is null");
//        return RxJavaPlugins.onAssembly(new ObservableTakeUntilPredicate<T>(this, stopPredicate));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<T> takeWhile(Predicate<? super T> predicate) {
//        ObjectHelper.requireNonNull(predicate, "predicate is null");
//        return RxJavaPlugins.onAssembly(new ObservableTakeWhile<T>(this, predicate));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.COMPUTATION)
//    public final Observable<T> throttleFirst(long windowDuration, TimeUnit unit) {
//        return throttleFirst(windowDuration, unit, Schedulers.computation());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public final Observable<T> throttleFirst(long skipDuration, TimeUnit unit, Scheduler scheduler) {
//        ObjectHelper.requireNonNull(unit, "unit is null");
//        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
//        return RxJavaPlugins.onAssembly(new ObservableThrottleFirstTimed<T>(this, skipDuration, unit, scheduler));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.COMPUTATION)
//    public final Observable<T> throttleLast(long intervalDuration, TimeUnit unit) {
//        return sample(intervalDuration, unit);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public final Observable<T> throttleLast(long intervalDuration, TimeUnit unit, Scheduler scheduler) {
//        return sample(intervalDuration, unit, scheduler);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.COMPUTATION)
//    public final Observable<T> throttleLatest(long timeout, TimeUnit unit) {
//        return throttleLatest(timeout, unit, Schedulers.computation(), false);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.COMPUTATION)
//    public final Observable<T> throttleLatest(long timeout, TimeUnit unit, boolean emitLast) {
//        return throttleLatest(timeout, unit, Schedulers.computation(), emitLast);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public final Observable<T> throttleLatest(long timeout, TimeUnit unit, Scheduler scheduler) {
//        return throttleLatest(timeout, unit, scheduler, false);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public final Observable<T> throttleLatest(long timeout, TimeUnit unit, Scheduler scheduler, boolean emitLast) {
//        ObjectHelper.requireNonNull(unit, "unit is null");
//        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
//        return RxJavaPlugins.onAssembly(new ObservableThrottleLatest<T>(this, timeout, unit, scheduler, emitLast));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.COMPUTATION)
//    public final Observable<T> throttleWithTimeout(long timeout, TimeUnit unit) {
//        return debounce(timeout, unit);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public final Observable<T> throttleWithTimeout(long timeout, TimeUnit unit, Scheduler scheduler) {
//        return debounce(timeout, unit, scheduler);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<Timed<T>> timeInterval() {
//        return timeInterval(TimeUnit.MILLISECONDS, Schedulers.computation());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE) // Supplied scheduler is only used for creating timestamps.
//    public final Observable<Timed<T>> timeInterval(Scheduler scheduler) {
//        return timeInterval(TimeUnit.MILLISECONDS, scheduler);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<Timed<T>> timeInterval(TimeUnit unit) {
//        return timeInterval(unit, Schedulers.computation());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE) // Supplied scheduler is only used for creating timestamps.
//    public final Observable<Timed<T>> timeInterval(TimeUnit unit, Scheduler scheduler) {
//        ObjectHelper.requireNonNull(unit, "unit is null");
//        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
//        return RxJavaPlugins.onAssembly(new ObservableTimeInterval<T>(this, unit, scheduler));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <V> Observable<T> timeout(Function<? super T, ? extends ObservableSource<V>> itemTimeoutIndicator) {
//        return timeout0(null, itemTimeoutIndicator, null);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <V> Observable<T> timeout(Function<? super T, ? extends ObservableSource<V>> itemTimeoutIndicator,
//                                                         ObservableSource<? extends T> other) {
//        ObjectHelper.requireNonNull(other, "other is null");
//        return timeout0(null, itemTimeoutIndicator, other);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.COMPUTATION)
//    public final Observable<T> timeout(long timeout, TimeUnit timeUnit) {
//        return timeout0(timeout, timeUnit, null, Schedulers.computation());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.COMPUTATION)
//    public final Observable<T> timeout(long timeout, TimeUnit timeUnit, ObservableSource<? extends T> other) {
//        ObjectHelper.requireNonNull(other, "other is null");
//        return timeout0(timeout, timeUnit, other, Schedulers.computation());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public final Observable<T> timeout(long timeout, TimeUnit timeUnit, Scheduler scheduler, ObservableSource<? extends T> other) {
//        ObjectHelper.requireNonNull(other, "other is null");
//        return timeout0(timeout, timeUnit, other, scheduler);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public final Observable<T> timeout(long timeout, TimeUnit timeUnit, Scheduler scheduler) {
//        return timeout0(timeout, timeUnit, null, scheduler);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <U, V> Observable<T> timeout(ObservableSource<U> firstTimeoutIndicator,
//                                                            Function<? super T, ? extends ObservableSource<V>> itemTimeoutIndicator) {
//        ObjectHelper.requireNonNull(firstTimeoutIndicator, "firstTimeoutIndicator is null");
//        return timeout0(firstTimeoutIndicator, itemTimeoutIndicator, null);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <U, V> Observable<T> timeout(
//            ObservableSource<U> firstTimeoutIndicator,
//            Function<? super T, ? extends ObservableSource<V>> itemTimeoutIndicator,
//            ObservableSource<? extends T> other) {
//        ObjectHelper.requireNonNull(firstTimeoutIndicator, "firstTimeoutIndicator is null");
//        ObjectHelper.requireNonNull(other, "other is null");
//        return timeout0(firstTimeoutIndicator, itemTimeoutIndicator, other);
//    }
//
//    private Observable<T> timeout0(long timeout, TimeUnit timeUnit, ObservableSource<? extends T> other,
//                                                 Scheduler scheduler) {
//        ObjectHelper.requireNonNull(timeUnit, "timeUnit is null");
//        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
//        return RxJavaPlugins.onAssembly(new ObservableTimeoutTimed<T>(this, timeout, timeUnit, scheduler, other));
//    }
//
//    private <U, V> Observable<T> timeout0(
//            ObservableSource<U> firstTimeoutIndicator,
//            Function<? super T, ? extends ObservableSource<V>> itemTimeoutIndicator,
//            ObservableSource<? extends T> other) {
//        ObjectHelper.requireNonNull(itemTimeoutIndicator, "itemTimeoutIndicator is null");
//        return RxJavaPlugins.onAssembly(new ObservableTimeout<T, U, V>(this, firstTimeoutIndicator, itemTimeoutIndicator, other));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<Timed<T>> timestamp() {
//        return timestamp(TimeUnit.MILLISECONDS, Schedulers.computation());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE) // Supplied scheduler is only used for creating timestamps.
//    public final Observable<Timed<T>> timestamp(Scheduler scheduler) {
//        return timestamp(TimeUnit.MILLISECONDS, scheduler);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<Timed<T>> timestamp(TimeUnit unit) {
//        return timestamp(unit, Schedulers.computation());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE) // Supplied scheduler is only used for creating timestamps.
//    public final Observable<Timed<T>> timestamp(final TimeUnit unit, final Scheduler scheduler) {
//        ObjectHelper.requireNonNull(unit, "unit is null");
//        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
//        return map(Functions.<T>timestampWith(unit, scheduler));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <R> R to(Function<? super Observable<T>, R> converter) {
//        try {
//            return ObjectHelper.requireNonNull(converter, "converter is null").apply(this);
//        } catch (Throwable ex) {
//            Exceptions.throwIfFatal(ex);
//            throw ExceptionHelper.wrapOrThrow(ex);
//        }
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Single<List<T>> toList() {
//        return toList(16);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Single<List<T>> toList(final int capacityHint) {
//        ObjectHelper.verifyPositive(capacityHint, "capacityHint");
//        return RxJavaPlugins.onAssembly(new ObservableToListSingle<T, List<T>>(this, capacityHint));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <U extends Collection<? super T>> Single<U> toList(Callable<U> collectionSupplier) {
//        ObjectHelper.requireNonNull(collectionSupplier, "collectionSupplier is null");
//        return RxJavaPlugins.onAssembly(new ObservableToListSingle<T, U>(this, collectionSupplier));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <K> Single<Map<K, T>> toMap(final Function<? super T, ? extends K> keySelector) {
//        ObjectHelper.requireNonNull(keySelector, "keySelector is null");
//        return collect(HashMapSupplier.<K, T>asCallable(), Functions.toMapKeySelector(keySelector));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <K, V> Single<Map<K, V>> toMap(
//            final Function<? super T, ? extends K> keySelector,
//            final Function<? super T, ? extends V> valueSelector) {
//        ObjectHelper.requireNonNull(keySelector, "keySelector is null");
//        ObjectHelper.requireNonNull(valueSelector, "valueSelector is null");
//        return collect(HashMapSupplier.<K, V>asCallable(), Functions.toMapKeyValueSelector(keySelector, valueSelector));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <K, V> Single<Map<K, V>> toMap(
//            final Function<? super T, ? extends K> keySelector,
//            final Function<? super T, ? extends V> valueSelector,
//            Callable<? extends Map<K, V>> mapSupplier) {
//        ObjectHelper.requireNonNull(keySelector, "keySelector is null");
//        ObjectHelper.requireNonNull(valueSelector, "valueSelector is null");
//        ObjectHelper.requireNonNull(mapSupplier, "mapSupplier is null");
//        return collect(mapSupplier, Functions.toMapKeyValueSelector(keySelector, valueSelector));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <K> Single<Map<K, Collection<T>>> toMultimap(Function<? super T, ? extends K> keySelector) {
//        @SuppressWarnings({"rawtypes", "unchecked"})
//        Function<? super T, ? extends T> valueSelector = (Function) Functions.identity();
//        Callable<Map<K, Collection<T>>> mapSupplier = HashMapSupplier.asCallable();
//        Function<K, List<T>> collectionFactory = ArrayListSupplier.asFunction();
//        return toMultimap(keySelector, valueSelector, mapSupplier, collectionFactory);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <K, V> Single<Map<K, Collection<V>>> toMultimap(Function<? super T, ? extends K> keySelector, Function<? super T, ? extends V> valueSelector) {
//        Callable<Map<K, Collection<V>>> mapSupplier = HashMapSupplier.asCallable();
//        Function<K, List<V>> collectionFactory = ArrayListSupplier.asFunction();
//        return toMultimap(keySelector, valueSelector, mapSupplier, collectionFactory);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <K, V> Single<Map<K, Collection<V>>> toMultimap(
//            final Function<? super T, ? extends K> keySelector,
//            final Function<? super T, ? extends V> valueSelector,
//            final Callable<? extends Map<K, Collection<V>>> mapSupplier,
//            final Function<? super K, ? extends Collection<? super V>> collectionFactory) {
//        ObjectHelper.requireNonNull(keySelector, "keySelector is null");
//        ObjectHelper.requireNonNull(valueSelector, "valueSelector is null");
//        ObjectHelper.requireNonNull(mapSupplier, "mapSupplier is null");
//        ObjectHelper.requireNonNull(collectionFactory, "collectionFactory is null");
//        return collect(mapSupplier, Functions.toMultimapKeyValueSelector(keySelector, valueSelector, collectionFactory));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <K, V> Single<Map<K, Collection<V>>> toMultimap(
//            Function<? super T, ? extends K> keySelector,
//            Function<? super T, ? extends V> valueSelector,
//            Callable<Map<K, Collection<V>>> mapSupplier
//    ) {
//        return toMultimap(keySelector, valueSelector, mapSupplier, ArrayListSupplier.<V, K>asFunction());
//    }
//
//    @BackpressureSupport(BackpressureKind.SPECIAL)
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Flowable<T> toFlowable(BackpressureStrategy strategy) {
//        Flowable<T> f = new FlowableFromObservable<T>(this);
//
//        switch (strategy) {
//            case DROP:
//                return f.onBackpressureDrop();
//            case LATEST:
//                return f.onBackpressureLatest();
//            case MISSING:
//                return f;
//            case ERROR:
//                return RxJavaPlugins.onAssembly(new FlowableOnBackpressureError<T>(f));
//            default:
//                return f.onBackpressureBuffer();
//        }
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Single<List<T>> toSortedList() {
//        return toSortedList(Functions.naturalOrder());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Single<List<T>> toSortedList(final Comparator<? super T> comparator) {
//        ObjectHelper.requireNonNull(comparator, "comparator is null");
//        return toList().map(Functions.listSorter(comparator));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Single<List<T>> toSortedList(final Comparator<? super T> comparator, int capacityHint) {
//        ObjectHelper.requireNonNull(comparator, "comparator is null");
//        return toList(capacityHint).map(Functions.listSorter(comparator));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Single<List<T>> toSortedList(int capacityHint) {
//        return toSortedList(Functions.<T>naturalOrder(), capacityHint);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public final Observable<T> unsubscribeOn(Scheduler scheduler) {
//        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
//        return RxJavaPlugins.onAssembly(new ObservableUnsubscribeOn<T>(this, scheduler));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<Observable<T>> window(long count) {
//        return window(count, count, bufferSize());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<Observable<T>> window(long count, long skip) {
//        return window(count, skip, bufferSize());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final Observable<Observable<T>> window(long count, long skip, int bufferSize) {
//        ObjectHelper.verifyPositive(count, "count");
//        ObjectHelper.verifyPositive(skip, "skip");
//        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
//        return RxJavaPlugins.onAssembly(new ObservableWindow<T>(this, count, skip, bufferSize));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.COMPUTATION)
//    public final Observable<Observable<T>> window(long timespan, long timeskip, TimeUnit unit) {
//        return window(timespan, timeskip, unit, Schedulers.computation(), bufferSize());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public final Observable<Observable<T>> window(long timespan, long timeskip, TimeUnit unit, Scheduler scheduler) {
//        return window(timespan, timeskip, unit, scheduler, bufferSize());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public final Observable<Observable<T>> window(long timespan, long timeskip, TimeUnit unit, Scheduler scheduler, int bufferSize) {
//        ObjectHelper.verifyPositive(timespan, "timespan");
//        ObjectHelper.verifyPositive(timeskip, "timeskip");
//        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
//        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
//        ObjectHelper.requireNonNull(unit, "unit is null");
//        return RxJavaPlugins.onAssembly(new ObservableWindowTimed<T>(this, timespan, timeskip, unit, scheduler, Long.MAX_VALUE, bufferSize, false));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.COMPUTATION)
//    public final Observable<Observable<T>> window(long timespan, TimeUnit unit) {
//        return window(timespan, unit, Schedulers.computation(), Long.MAX_VALUE, false);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.COMPUTATION)
//    public final Observable<Observable<T>> window(long timespan, TimeUnit unit,
//                                                                              long count) {
//        return window(timespan, unit, Schedulers.computation(), count, false);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.COMPUTATION)
//    public final Observable<Observable<T>> window(long timespan, TimeUnit unit,
//                                                                              long count, boolean restart) {
//        return window(timespan, unit, Schedulers.computation(), count, restart);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public final Observable<Observable<T>> window(long timespan, TimeUnit unit,
//                                                                              Scheduler scheduler) {
//        return window(timespan, unit, scheduler, Long.MAX_VALUE, false);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public final Observable<Observable<T>> window(long timespan, TimeUnit unit,
//                                                                 Scheduler scheduler, long count) {
//        return window(timespan, unit, scheduler, count, false);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public final Observable<Observable<T>> window(long timespan, TimeUnit unit,
//                                                                              Scheduler scheduler, long count, boolean restart) {
//        return window(timespan, unit, scheduler, count, restart, bufferSize());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.CUSTOM)
//    public final Observable<Observable<T>> window(
//            long timespan, TimeUnit unit, Scheduler scheduler,
//            long count, boolean restart, int bufferSize) {
//        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
//        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
//        ObjectHelper.requireNonNull(unit, "unit is null");
//        ObjectHelper.verifyPositive(count, "count");
//        return RxJavaPlugins.onAssembly(new ObservableWindowTimed<T>(this, timespan, timespan, unit, scheduler, count, bufferSize, restart));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <B> Observable<Observable<T>> window(ObservableSource<B> boundary) {
//        return window(boundary, bufferSize());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <B> Observable<Observable<T>> window(ObservableSource<B> boundary, int bufferSize) {
//        ObjectHelper.requireNonNull(boundary, "boundary is null");
//        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
//        return RxJavaPlugins.onAssembly(new ObservableWindowBoundary<T, B>(this, boundary, bufferSize));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <U, V> Observable<Observable<T>> window(
//            ObservableSource<U> openingIndicator,
//            Function<? super U, ? extends ObservableSource<V>> closingIndicator) {
//        return window(openingIndicator, closingIndicator, bufferSize());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <U, V> Observable<Observable<T>> window(
//            ObservableSource<U> openingIndicator,
//            Function<? super U, ? extends ObservableSource<V>> closingIndicator, int bufferSize) {
//        ObjectHelper.requireNonNull(openingIndicator, "openingIndicator is null");
//        ObjectHelper.requireNonNull(closingIndicator, "closingIndicator is null");
//        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
//        return RxJavaPlugins.onAssembly(new ObservableWindowBoundarySelector<T, U, V>(this, openingIndicator, closingIndicator, bufferSize));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <B> Observable<Observable<T>> window(Callable<? extends ObservableSource<B>> boundary) {
//        return window(boundary, bufferSize());
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <B> Observable<Observable<T>> window(Callable<? extends ObservableSource<B>> boundary, int bufferSize) {
//        ObjectHelper.requireNonNull(boundary, "boundary is null");
//        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
//        return RxJavaPlugins.onAssembly(new ObservableWindowBoundarySupplier<T, B>(this, boundary, bufferSize));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <U, R> Observable<R> withLatestFrom(ObservableSource<? extends U> other, BiFunction<? super T, ? super U, ? extends R> combiner) {
//        ObjectHelper.requireNonNull(other, "other is null");
//        ObjectHelper.requireNonNull(combiner, "combiner is null");
//
//        return RxJavaPlugins.onAssembly(new ObservableWithLatestFrom<T, U, R>(this, combiner, other));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <T1, T2, R> Observable<R> withLatestFrom(
//            ObservableSource<T1> o1, ObservableSource<T2> o2,
//            Function3<? super T, ? super T1, ? super T2, R> combiner) {
//        ObjectHelper.requireNonNull(o1, "o1 is null");
//        ObjectHelper.requireNonNull(o2, "o2 is null");
//        ObjectHelper.requireNonNull(combiner, "combiner is null");
//        Function<Object[], R> f = Functions.toFunction(combiner);
//        return withLatestFrom(new ObservableSource[]{o1, o2}, f);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <T1, T2, T3, R> Observable<R> withLatestFrom(
//            ObservableSource<T1> o1, ObservableSource<T2> o2,
//            ObservableSource<T3> o3,
//            Function4<? super T, ? super T1, ? super T2, ? super T3, R> combiner) {
//        ObjectHelper.requireNonNull(o1, "o1 is null");
//        ObjectHelper.requireNonNull(o2, "o2 is null");
//        ObjectHelper.requireNonNull(o3, "o3 is null");
//        ObjectHelper.requireNonNull(combiner, "combiner is null");
//        Function<Object[], R> f = Functions.toFunction(combiner);
//        return withLatestFrom(new ObservableSource[]{o1, o2, o3}, f);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <T1, T2, T3, T4, R> Observable<R> withLatestFrom(
//            ObservableSource<T1> o1, ObservableSource<T2> o2,
//            ObservableSource<T3> o3, ObservableSource<T4> o4,
//            Function5<? super T, ? super T1, ? super T2, ? super T3, ? super T4, R> combiner) {
//        ObjectHelper.requireNonNull(o1, "o1 is null");
//        ObjectHelper.requireNonNull(o2, "o2 is null");
//        ObjectHelper.requireNonNull(o3, "o3 is null");
//        ObjectHelper.requireNonNull(o4, "o4 is null");
//        ObjectHelper.requireNonNull(combiner, "combiner is null");
//        Function<Object[], R> f = Functions.toFunction(combiner);
//        return withLatestFrom(new ObservableSource[]{o1, o2, o3, o4}, f);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <R> Observable<R> withLatestFrom(ObservableSource<?>[] others, Function<? super Object[], R> combiner) {
//        ObjectHelper.requireNonNull(others, "others is null");
//        ObjectHelper.requireNonNull(combiner, "combiner is null");
//        return RxJavaPlugins.onAssembly(new ObservableWithLatestFromMany<T, R>(this, others, combiner));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <R> Observable<R> withLatestFrom(Iterable<? extends ObservableSource<?>> others, Function<? super Object[], R> combiner) {
//        ObjectHelper.requireNonNull(others, "others is null");
//        ObjectHelper.requireNonNull(combiner, "combiner is null");
//        return RxJavaPlugins.onAssembly(new ObservableWithLatestFromMany<T, R>(this, others, combiner));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <U, R> Observable<R> zipWith(Iterable<U> other, BiFunction<? super T, ? super U, ? extends R> zipper) {
//        ObjectHelper.requireNonNull(other, "other is null");
//        ObjectHelper.requireNonNull(zipper, "zipper is null");
//        return RxJavaPlugins.onAssembly(new ObservableZipIterable<T, U, R>(this, other, zipper));
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <U, R> Observable<R> zipWith(ObservableSource<? extends U> other,
//                                                            BiFunction<? super T, ? super U, ? extends R> zipper) {
//        ObjectHelper.requireNonNull(other, "other is null");
//        return zip(this, other, zipper);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <U, R> Observable<R> zipWith(ObservableSource<? extends U> other,
//                                                            BiFunction<? super T, ? super U, ? extends R> zipper, boolean delayError) {
//        return zip(this, other, zipper, delayError);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final <U, R> Observable<R> zipWith(ObservableSource<? extends U> other,
//                                                            BiFunction<? super T, ? super U, ? extends R> zipper, boolean delayError, int bufferSize) {
//        return zip(this, other, zipper, delayError, bufferSize);
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final TestObserver<T> test() { // NoPMD
//        TestObserver<T> to = new TestObserver<T>();
//        subscribe(to);
//        return to;
//    }
//
//    @CheckReturnValue
//    @SchedulerSupport(SchedulerSupport.NONE)
//    public final TestObserver<T> test(boolean dispose) { // NoPMD
//        TestObserver<T> to = new TestObserver<T>();
//        if (dispose) {
//            to.dispose();
//        }
//        subscribe(to);
//        return to;
//    }
//}