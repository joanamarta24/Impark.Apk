// Em: com/example/imparkapk/data/manager/
package com.example.imparkapk.data.manager

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.imparkapk.data.dao.remote.api.dto.PaginationRequest
import com.example.imparkapk.data.dto.PaginationDTO
import com.example.imparkapk.data.dto.PaginationRequest

class PaginationManager<T> {
    private val _currentData = MutableLiveData<List<T>>()
    val currentData: LiveData<List<T>> = _currentData

    private val _paginationState = MutableLiveData<PaginationState>()
    val paginationState: LiveData<PaginationState> = _paginationState

    private var currentPagination: PaginationDTO<T>? = null
    private var currentRequest: PaginationRequest = PaginationRequest()

    fun loadFirstPage(
        request: PaginationRequest = PaginationRequest(),
        apiCall: suspend (PaginationRequest) -> PaginationDTO<T>?
    ) {
        currentRequest = request
        loadPage(1, apiCall)
    }

    fun loadNextPage(apiCall: suspend (PaginationRequest) -> PaginationDTO<T>?) {
        currentPagination?.let { pagination ->
            if (pagination.hasNextPage()) {
                loadPage(pagination.getNextPage()!!, apiCall)
            }
        }
    }

    private fun loadPage(
        page: Int,
        apiCall: suspend (PaginationRequest) -> PaginationDTO<T>?
    ) {
        _paginationState.value = PaginationState.Loading

        // Em uma coroutine:
        // try {
        //     val result = apiCall(currentRequest.withPage(page))
        //     if (result != null) {
        //         handleNewData(result, page == 1)
        //         _paginationState.value = PaginationState.Success
        //     } else {
        //         _paginationState.value = PaginationState.Error("Falha ao carregar dados")
        //     }
        // } catch (e: Exception) {
        //     _paginationState.value = PaginationState.Error(e.message ?: "Erro desconhecido")
        // }
    }

    private fun handleNewData(newData: PaginationDTO<T>, isFirstPage: Boolean) {
        if (isFirstPage) {
            currentPagination = newData
            _currentData.value = newData.data
        } else {
            currentPagination = currentPagination?.append(newData)
            _currentData.value = _currentData.value.orEmpty() + newData.data
        }
    }

    fun refresh(apiCall: suspend (PaginationRequest) -> PaginationDTO<T>?) {
        loadFirstPage(currentRequest, apiCall)
    }

    fun clear() {
        currentPagination = null
        _currentData.value = emptyList()
        _paginationState.value = PaginationState.Idle
    }
}

sealed class PaginationState {
    object Idle : PaginationState()
    object Loading : PaginationState()
    object Success : PaginationState()
    data class Error(val message: String) : PaginationState()
}