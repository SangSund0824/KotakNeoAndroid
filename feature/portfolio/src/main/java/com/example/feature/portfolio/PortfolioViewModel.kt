package com.example.feature.portfolio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.network.datasource.PortfolioDataSource
import com.example.core.network.model.portfolio.Holding
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PortfolioState(
    val isLoading: Boolean = false,
    val holdings: List<Holding> = emptyList(),
    val totalInvestment: Double = 0.0,
    val totalCurrentValue: Double = 0.0,
    val error: String? = null
)

@HiltViewModel
class PortfolioViewModel @Inject constructor(
    private val portfolioDataSource: PortfolioDataSource
) : ViewModel() {

    private val _state = MutableStateFlow(PortfolioState())
    val state: StateFlow<PortfolioState> = _state.asStateFlow()

    init {
        loadHoldings()
    }

    fun loadHoldings() {
        viewModelScope.launch {
            _state.value = PortfolioState(isLoading = true)
            try {
                val response = portfolioDataSource.getHoldings()
                val holdings = response.data ?: emptyList()
                val totalInvestment = holdings.sumOf { it.holdingCost ?: 0.0 }
                val totalCurrentValue = holdings.sumOf { it.mktValue ?: 0.0 }
                _state.value = PortfolioState(
                    holdings = holdings,
                    totalInvestment = totalInvestment,
                    totalCurrentValue = totalCurrentValue
                )
            } catch (e: Exception) {
                _state.value = PortfolioState(error = e.message ?: "Failed to load portfolio")
            }
        }
    }
}
