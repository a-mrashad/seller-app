package com.mazaj.seller

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.github.ivanshafran.sharedpreferencesmock.SPMockBuilder
import de.mazaj.seller.repository.Repository
import de.mazaj.seller.repository.repository
import org.junit.Before
import org.junit.Rule

open class BaseViewModelUnitTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repository = Repository(SPMockBuilder().createContext())
        initialiseViewModel()
    }

    open fun initialiseViewModel() {}
}
