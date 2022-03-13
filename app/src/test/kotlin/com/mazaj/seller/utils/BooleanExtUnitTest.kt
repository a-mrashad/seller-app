package com.mazaj.seller.utils

import de.mazaj.seller.extensions.orFalse
import org.junit.Assert.assertEquals
import org.junit.Test

class BooleanExtUnitTest {
    @Test
    fun orFalseUnitTest() {
        assertEquals(null.orFalse(), false)
        assertEquals(false.orFalse(), false)
        assertEquals(true.orFalse(), true)
    }
}
