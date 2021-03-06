/*
 *
 *  Copyright (c) 2017 Otávio Santana and others
 *   All rights reserved. This program and the accompanying materials
 *   are made available under the terms of the Eclipse Public License v1.0
 *   and Apache License v2.0 which accompanies this distribution.
 *   The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 *   and the Apache License v2.0 is available at http://www.opensource.org/licenses/apache2.0.php.
 *
 *   You may elect to redistribute this code under either of these licenses.
 *
 *   Contributors:
 *
 *   Otavio Santana
 *
 */

package org.jnosql.diana.api.reader;

import org.jnosql.diana.api.ValueReader;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;


public class OptionalReaderTest {

    private ValueReader valueReader;

    @Before
    public void init() {
        valueReader = new OptionalValueReader();
    }

    @Test
    public void shouldValidateCompatibility() {
        assertTrue(valueReader.isCompatible(Optional.class));
        assertFalse(valueReader.isCompatible(AtomicBoolean.class));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldConvert() {
        Optional<String> optional = Optional.of("value");
        assertEquals(optional, valueReader.read(Optional.class, optional));

        Optional<String> result = valueReader.read(Optional.class, "12");
        assertEquals(Optional.of("12"), result);

    }


}
