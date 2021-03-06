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

package com.gs.collections.test.set.mutable.sorted;

import com.gs.collections.test.UnmodifiableMutableCollectionTestCase;
import com.gs.collections.test.set.sorted.UnmodifiableSortedSetTestCase;
import org.junit.Test;

public interface UnmodifiableMutableSortedSetTestCase extends UnmodifiableMutableCollectionTestCase, UnmodifiableSortedSetTestCase, MutableSortedSetTestCase
{
    @Override
    @Test
    default void Iterable_remove()
    {
        UnmodifiableSortedSetTestCase.super.Iterable_remove();
    }

    @Override
    @Test(expected = UnsupportedOperationException.class)
    default void MutableCollection_removeIf()
    {
        UnmodifiableMutableCollectionTestCase.super.MutableCollection_removeIf();
    }

    @Override
    @Test(expected = UnsupportedOperationException.class)
    default void MutableCollection_removeIfWith()
    {
        UnmodifiableMutableCollectionTestCase.super.MutableCollection_removeIfWith();
    }
}
