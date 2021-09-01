package com.fastrata.eimprovement.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.fastrata.eimprovement.data.Result.Status.ERROR
import com.fastrata.eimprovement.data.Result.Status.SUCCESS
import kotlinx.coroutines.Dispatchers

/**
 * The database serves as the single source of truth.
 * Therefore UI can receive data updates from database only.
 * Function notify UI about:
 * [Result.Status.SUCCESS] - with data from database
 * [Result.Status.ERROR] - if error has occurred from any source
 * [Result.Status.LOADING]
 */
fun <T, A> resultLiveData(databaseQuery: () -> LiveData<T>,
                          networkCall: suspend () -> Result<A>,
                          saveCallResult: suspend (A) -> Unit): LiveData<Result<T>> =
    liveData(Dispatchers.IO) {
        emit(Result.loading<T>())
        val source = databaseQuery.invoke().map {
            Result.success(it)
        }
        emitSource(source)

        val responseStatus = networkCall.invoke()
        if (responseStatus.status == SUCCESS) {
            saveCallResult(responseStatus.data!!)
        } else if (responseStatus.status == ERROR) {
            emit(Result.error<T>(responseStatus.message!!))
            emitSource(source)
        }
    }

fun <A> resultLiveDataFetchInsert(networkCall: suspend () -> Result<A>,
                                  saveCallResult: suspend (A) -> Unit): LiveData<Result<A>> =
    liveData(Dispatchers.IO) {
        emit(Result.loading<A>())
        val responseStatus = networkCall.invoke()
        if (responseStatus.status == SUCCESS) {
            saveCallResult(responseStatus.data!!)
        } else if (responseStatus.status == ERROR) {
            emit(Result.error<A>(responseStatus.message!!))
        }
    }

fun <T, A> resultLiveDataRetryOffline(databaseQuery: suspend () -> List<T>,
                                      networkCall: suspend (T) -> Result<A>,
                                      saveCallResult: suspend (A) -> Unit): LiveData<Result<T>> =
    liveData(Dispatchers.IO) {
        emit(Result.loading<T>())
        val databaseResult = databaseQuery.invoke()
        var responseStatus: Result<A>
        if (databaseResult.isNotEmpty()) {
            for (i in databaseResult.indices) {
                responseStatus = networkCall.invoke(databaseResult[i])
                if (responseStatus.status == SUCCESS) {
                    emit(Result.success(databaseResult[i]))
                    saveCallResult(responseStatus.data!!)
                } else if (responseStatus.status == ERROR) {
                    emit(Result.error<T>(responseStatus.message!!))
                }
            }
        } else {
            emit(Result.error<T>("Data Not Found"))
        }
    }

fun <T> resultLiveDataRemote(
    networkCall: suspend () -> Result<T>
): LiveData<Result<T>> =
    liveData(Dispatchers.IO) {
        emit(Result.loading<T>())
        val responseStatus = networkCall.invoke()
        if (responseStatus.status == SUCCESS) {
            emit(Result.success(responseStatus.data!!))
        } else if (responseStatus.status == ERROR) {
            emit(Result.error<T>(responseStatus.message!!))
        }
    }

fun <T> resultMutableLiveDataRemote(
    networkCall: suspend () -> Result<T>
): MutableLiveData<Result<T>> =
    liveData(Dispatchers.IO) {
        emit(Result.loading<T>())
        val responseStatus = networkCall.invoke()
        if (responseStatus.status == SUCCESS) {
            emit(Result.success(responseStatus.data!!))
        } else if (responseStatus.status == ERROR) {
            emit(Result.error<T>(responseStatus.message!!))
        }
    } as MutableLiveData<Result<T>>

