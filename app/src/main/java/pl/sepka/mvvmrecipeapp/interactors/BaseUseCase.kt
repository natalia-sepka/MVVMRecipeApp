package pl.sepka.mvvmrecipeapp.interactors

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import pl.sepka.mvvmrecipeapp.domain.data.DataState

abstract class BaseUseCase<Input, Output> {

    protected abstract fun action(params: Input): Flow<DataState<Output>>

    private fun defaultBackgroundScheduler() = Dispatchers.IO

    open fun invoke(params: Input): Flow<DataState<Output>> = flow {
        try {
            emitAll(action(params))
        } catch (e: Exception) {
            emit(DataState.error(e.message ?: "Unknown error"))
        }
    }.flowOn(defaultBackgroundScheduler())
}
