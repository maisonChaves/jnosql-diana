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

import java.util.Calendar;

import static org.junit.Assert.*;

public class CalendarReaderTest {

    private ValueReader valueReader;

    @Before
    public void init() {
        valueReader = new CalendarValueReader();
    }

    @Test
    public void shouldValidateCompatibility() {
        assertTrue(valueReader.isCompatible(Calendar.class));
        assertFalse(valueReader.isCompatible(String.class));
        assertFalse(valueReader.isCompatible(Long.class));
    }

    @Test
    public void shouldConvert() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2010, 10, 9);

        assertEquals(calendar, valueReader.read(Calendar.class, calendar));
        assertEquals(calendar, valueReader.read(Calendar.class, calendar.getTimeInMillis()));
        assertEquals(calendar, valueReader.read(Calendar.class, calendar.getTime()));
    }


}
