/*
 * Copyright 2015 Goldman Sachs.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gs.collections.impl.set.mutable;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ExecutorService;

import com.gs.collections.api.LazyIterable;
import com.gs.collections.api.block.function.Function;
import com.gs.collections.api.block.function.Function2;
import com.gs.collections.api.block.function.primitive.BooleanFunction;
import com.gs.collections.api.block.function.primitive.ByteFunction;
import com.gs.collections.api.block.function.primitive.CharFunction;
import com.gs.collections.api.block.function.primitive.DoubleFunction;
import com.gs.collections.api.block.function.primitive.FloatFunction;
import com.gs.collections.api.block.function.primitive.IntFunction;
import com.gs.collections.api.block.function.primitive.LongFunction;
import com.gs.collections.api.block.function.primitive.ShortFunction;
import com.gs.collections.api.block.predicate.Predicate;
import com.gs.collections.api.block.predicate.Predicate2;
import com.gs.collections.api.block.procedure.Procedure;
import com.gs.collections.api.multimap.set.MutableSetMultimap;
import com.gs.collections.api.ordered.OrderedIterable;
import com.gs.collections.api.partition.set.PartitionMutableSet;
import com.gs.collections.api.set.ImmutableSet;
import com.gs.collections.api.set.MutableSet;
import com.gs.collections.api.set.ParallelUnsortedSetIterable;
import com.gs.collections.api.set.SetIterable;
import com.gs.collections.api.set.UnsortedSetIterable;
import com.gs.collections.api.set.primitive.MutableBooleanSet;
import com.gs.collections.api.set.primitive.MutableByteSet;
import com.gs.collections.api.set.primitive.MutableCharSet;
import com.gs.collections.api.set.primitive.MutableDoubleSet;
import com.gs.collections.api.set.primitive.MutableFloatSet;
import com.gs.collections.api.set.primitive.MutableIntSet;
import com.gs.collections.api.set.primitive.MutableLongSet;
import com.gs.collections.api.set.primitive.MutableShortSet;
import com.gs.collections.api.tuple.Pair;
import com.gs.collections.impl.collection.mutable.AbstractUnmodifiableMutableCollection;
import com.gs.collections.impl.collection.mutable.UnmodifiableCollectionSerializationProxy;
import com.gs.collections.impl.factory.Sets;

/**
 * An unmodifiable view of a list.
 *
 * @see MutableSet#asUnmodifiable()
 */
public class UnmodifiableMutableSet<T>
        extends AbstractUnmodifiableMutableCollection<T>
        implements MutableSet<T>, Serializable
{
    UnmodifiableMutableSet(MutableSet<? extends T> mutableSet)
    {
        super(mutableSet);
    }

    /**
     * This method will take a MutableSet and wrap it directly in a UnmodifiableMutableSet.  It will
     * take any other non-GS-set and first adapt it will a SetAdapter, and then return a
     * UnmodifiableMutableSet that wraps the adapter.
     */
    public static <E, S extends Set<E>> UnmodifiableMutableSet<E> of(S set)
    {
        if (set == null)
        {
            throw new IllegalArgumentException("cannot create an UnmodifiableMutableSet for null");
        }
        return new UnmodifiableMutableSet<E>(SetAdapter.adapt(set));
    }

    protected MutableSet<T> getMutableSet()
    {
        return (MutableSet<T>) this.getMutableCollection();
    }

    @Override
    public MutableSet<T> asUnmodifiable()
    {
        return this;
    }

    @Override
    public MutableSet<T> asSynchronized()
    {
        return SynchronizedMutableSet.of(this);
    }

    @Override
    public ImmutableSet<T> toImmutable()
    {
        return Sets.immutable.withAll(this.getMutableSet());
    }

    @Override
    public boolean equals(Object obj)
    {
        return this.getMutableSet().equals(obj);
    }

    @Override
    public int hashCode()
    {
        return this.getMutableSet().hashCode();
    }

    @Override
    public UnmodifiableMutableSet<T> clone()
    {
        return this;
    }

    @Override
    public MutableSet<T> newEmpty()
    {
        return this.getMutableSet().newEmpty();
    }

    @Override
    public MutableSet<T> tap(Procedure<? super T> procedure)
    {
        this.forEach(procedure);
        return this;
    }

    @Override
    public MutableSet<T> select(Predicate<? super T> predicate)
    {
        return this.getMutableSet().select(predicate);
    }

    @Override
    public <P> MutableSet<T> selectWith(Predicate2<? super T, ? super P> predicate, P parameter)
    {
        return this.getMutableSet().selectWith(predicate, parameter);
    }

    @Override
    public MutableSet<T> reject(Predicate<? super T> predicate)
    {
        return this.getMutableSet().reject(predicate);
    }

    @Override
    public <P> MutableSet<T> rejectWith(Predicate2<? super T, ? super P> predicate, P parameter)
    {
        return this.getMutableSet().rejectWith(predicate, parameter);
    }

    @Override
    public PartitionMutableSet<T> partition(Predicate<? super T> predicate)
    {
        return this.getMutableSet().partition(predicate);
    }

    @Override
    public <P> PartitionMutableSet<T> partitionWith(Predicate2<? super T, ? super P> predicate, P parameter)
    {
        return this.getMutableSet().partitionWith(predicate, parameter);
    }

    @Override
    public <S> MutableSet<S> selectInstancesOf(Class<S> clazz)
    {
        return this.getMutableSet().selectInstancesOf(clazz);
    }

    @Override
    public <V> MutableSet<V> collect(Function<? super T, ? extends V> function)
    {
        return this.getMutableSet().collect(function);
    }

    @Override
    public MutableBooleanSet collectBoolean(BooleanFunction<? super T> booleanFunction)
    {
        return this.getMutableSet().collectBoolean(booleanFunction);
    }

    @Override
    public MutableByteSet collectByte(ByteFunction<? super T> byteFunction)
    {
        return this.getMutableSet().collectByte(byteFunction);
    }

    @Override
    public MutableCharSet collectChar(CharFunction<? super T> charFunction)
    {
        return this.getMutableSet().collectChar(charFunction);
    }

    @Override
    public MutableDoubleSet collectDouble(DoubleFunction<? super T> doubleFunction)
    {
        return this.getMutableSet().collectDouble(doubleFunction);
    }

    @Override
    public MutableFloatSet collectFloat(FloatFunction<? super T> floatFunction)
    {
        return this.getMutableSet().collectFloat(floatFunction);
    }

    @Override
    public MutableIntSet collectInt(IntFunction<? super T> intFunction)
    {
        return this.getMutableSet().collectInt(intFunction);
    }

    @Override
    public MutableLongSet collectLong(LongFunction<? super T> longFunction)
    {
        return this.getMutableSet().collectLong(longFunction);
    }

    @Override
    public MutableShortSet collectShort(ShortFunction<? super T> shortFunction)
    {
        return this.getMutableSet().collectShort(shortFunction);
    }

    @Override
    public <V> MutableSet<V> flatCollect(Function<? super T, ? extends Iterable<V>> function)
    {
        return this.getMutableSet().flatCollect(function);
    }

    @Override
    public <P, A> MutableSet<A> collectWith(Function2<? super T, ? super P, ? extends A> function, P parameter)
    {
        return this.getMutableSet().collectWith(function, parameter);
    }

    @Override
    public <V> MutableSet<V> collectIf(
            Predicate<? super T> predicate,
            Function<? super T, ? extends V> function)
    {
        return this.getMutableSet().collectIf(predicate, function);
    }

    @Override
    public <V> MutableSetMultimap<V, T> groupBy(Function<? super T, ? extends V> function)
    {
        return this.getMutableSet().groupBy(function);
    }

    @Override
    public <V> MutableSetMultimap<V, T> groupByEach(Function<? super T, ? extends Iterable<V>> function)
    {
        return this.getMutableSet().groupByEach(function);
    }

    /**
     * @deprecated in 6.0. Use {@link OrderedIterable#zip(Iterable)} instead.
     */
    @Deprecated
    @Override
    public <S> MutableSet<Pair<T, S>> zip(Iterable<S> that)
    {
        return this.getMutableSet().zip(that);
    }

    @Override
    public <S, R extends Collection<Pair<T, S>>> R zip(Iterable<S> that, R target)
    {
        return this.getMutableSet().zip(that, target);
    }

    /**
     * @deprecated in 6.0. Use {@link OrderedIterable#zipWithIndex()} instead.
     */
    @Deprecated
    @Override
    public MutableSet<Pair<T, Integer>> zipWithIndex()
    {
        return this.getMutableSet().zipWithIndex();
    }

    @Override
    public <R extends Collection<Pair<T, Integer>>> R zipWithIndex(R target)
    {
        return this.getMutableSet().zipWithIndex(target);
    }

    public MutableSet<T> union(SetIterable<? extends T> set)
    {
        return this.getMutableSet().union(set);
    }

    public <R extends Set<T>> R unionInto(SetIterable<? extends T> set, R targetSet)
    {
        return this.getMutableSet().unionInto(set, targetSet);
    }

    public MutableSet<T> intersect(SetIterable<? extends T> set)
    {
        return this.getMutableSet().intersect(set);
    }

    public <R extends Set<T>> R intersectInto(SetIterable<? extends T> set, R targetSet)
    {
        return this.getMutableSet().intersectInto(set, targetSet);
    }

    public MutableSet<T> difference(SetIterable<? extends T> subtrahendSet)
    {
        return this.getMutableSet().difference(subtrahendSet);
    }

    public <R extends Set<T>> R differenceInto(SetIterable<? extends T> subtrahendSet, R targetSet)
    {
        return this.getMutableSet().differenceInto(subtrahendSet, targetSet);
    }

    public MutableSet<T> symmetricDifference(SetIterable<? extends T> setB)
    {
        return this.getMutableSet().symmetricDifference(setB);
    }

    public <R extends Set<T>> R symmetricDifferenceInto(SetIterable<? extends T> set, R targetSet)
    {
        return this.getMutableSet().symmetricDifferenceInto(set, targetSet);
    }

    public boolean isSubsetOf(SetIterable<? extends T> candidateSuperset)
    {
        return this.getMutableSet().isSubsetOf(candidateSuperset);
    }

    public boolean isProperSubsetOf(SetIterable<? extends T> candidateSuperset)
    {
        return this.getMutableSet().isProperSubsetOf(candidateSuperset);
    }

    public MutableSet<UnsortedSetIterable<T>> powerSet()
    {
        return this.getMutableSet().powerSet();
    }

    public <B> LazyIterable<Pair<T, B>> cartesianProduct(SetIterable<B> set)
    {
        return this.getMutableSet().cartesianProduct(set);
    }

    @Override
    public MutableSet<T> with(T element)
    {
        throw new UnsupportedOperationException("Cannot call with() on " + this.getClass().getSimpleName());
    }

    @Override
    public MutableSet<T> without(T element)
    {
        throw new UnsupportedOperationException("Cannot call without() on " + this.getClass().getSimpleName());
    }

    @Override
    public MutableSet<T> withAll(Iterable<? extends T> elements)
    {
        throw new UnsupportedOperationException("Cannot call withAll() on " + this.getClass().getSimpleName());
    }

    @Override
    public MutableSet<T> withoutAll(Iterable<? extends T> elements)
    {
        throw new UnsupportedOperationException("Cannot call withoutAll() on " + this.getClass().getSimpleName());
    }

    protected Object writeReplace()
    {
        return new UnmodifiableCollectionSerializationProxy<T>(this.getMutableSet());
    }

    public ParallelUnsortedSetIterable<T> asParallel(ExecutorService executorService, int batchSize)
    {
        return this.getMutableSet().asParallel(executorService, batchSize);
    }
}
