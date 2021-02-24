package com.example.hearthelper


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.hearthelper.model.User
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class ParameterViewModelTest {

    private lateinit var viewModel: ParameterViewModel

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule() // rule for waiting LiveData to update

    @Before
    fun setup() {
        viewModel = ParameterViewModel()
    }


    @Test
    fun `updating anemia parameter with 1`() {
        viewModel.updateAnaemia(1)

        assertThat(viewModel.currentAnaemiaLD.getOrAwaitValueTest()).isEqualTo(1)
    }

    @Test
    fun `updating anemia parameter with 0`() {
        viewModel.updateAnaemia(0)

        assertThat(viewModel.currentAnaemiaLD.getOrAwaitValueTest()).isEqualTo(0)
    }

    @Test
    fun `create user object with null LiveData parameters, will replace them with zero`() {
        val user = viewModel.createUser(21.00, 21, 21, 10.00, 21.00, 21)
        assertThat(user.toString()).isEqualTo(User("1mka", 21.00, 0, 21, 0, 21, 0, 10.00, 21.00,21, 0, 0).toString())
    }

    @Test
    fun `create user object when LiveData objects are changed`() {
        viewModel.updateAnaemia(1)

        val anemiaValue = viewModel.currentAnaemiaLD.getOrAwaitValueTest()
        val user = viewModel.createUser(21.00, 21, 21, 10.00, 21.00, 21)
        assertThat(user.toString()).isEqualTo(User("1mka", 21.00, anemiaValue, 21, 0, 21, 0, 10.00, 21.00,21, 0, 0).toString())
    }


}