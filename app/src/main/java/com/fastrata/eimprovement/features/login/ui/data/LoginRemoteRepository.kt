package com.fastrata.eimprovement.features.login.data

import com.fastrata.eimprovement.data.resultLiveDataFetchInsert
import com.fastrata.eimprovement.data.resultMutableLiveDataRemote
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRemoteRepository @Inject constructor(private val remoteDataSource: LoginRemoteDataSource) {

    fun observeLoginMutable(loginRequest: LoginRemoteRequest) = resultMutableLiveDataRemote(
        networkCall = { remoteDataSource.requestLogin(loginRequest) }
    )

    /*fun observeMasterCustomers(surveyorCode: String) = resultLiveDataFetchInsert(
        networkCall = { remoteDataSource.fetchCustomerMasterData(surveyorCode) },
        saveCallResult = { dao.insertAll(it.data) }
    )*/

    /*fun observeMasterCustomers2(surveyorCode: String) = resultMutableLiveDataRemote(
            networkCall = { remoteDataSource.fetchCustomerMasterData(surveyorCode) }
    )*/

    /*fun observeCustomers(surveyorCode: String) : LiveData<Result<List<MasterCustomerEntity>>> {
        GlobalScope.launch {
            try {
                remoteDataSource.fetchCustomerMasterData(surveyorCode)
                Timber.d("Sukses Get Customers")
            } catch (e: Exception) {
                Timber.d("Error Get Customers")
            }
        }
    }*/
}