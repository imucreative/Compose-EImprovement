package com.fastrata.eimprovement.features.suggestionsystem.data

import com.fastrata.eimprovement.api.AppService
import com.fastrata.eimprovement.api.BaseDataSource
import javax.inject.Inject

class SsRemoteDataSource @Inject constructor(private val service: AppService) : BaseDataSource() {

}