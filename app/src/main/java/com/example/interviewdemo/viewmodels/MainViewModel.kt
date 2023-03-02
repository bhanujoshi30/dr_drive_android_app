package com.example.interviewdemo.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.interviewdemo.models.UserDetail
import com.example.interviewdemo.models.WorkshopDetail
import com.example.interviewdemo.models.WorkshopDetailItem
import com.example.interviewdemo.repositories.user.UserDataRepository
import com.example.interviewdemo.repositories.workshop.WorkshopDetailsRepository
import com.example.interviewdemo.utils.LoadingStatusType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private var workshopDetailsRepository: WorkshopDetailsRepository,
    private var userDetailsRepository: UserDataRepository
) :
    BaseViewModel() {
    val workshopDetailLiveData by lazy { MutableLiveData<List<WorkshopDetailItem>>() }
    val userDetailLiveData by lazy { MutableLiveData<List<UserDetail>>() }
    val dbClearStatus by lazy {MutableLiveData<Boolean>()}

    fun getWorkShopDetails() {
        viewModelScope.launch(coroutineExceptionHandler) {
            loadingStatusLiveData.postValue(LoadingStatusType.Loading)
            userDetailLiveData.postValue(userDetailsRepository.getUserData()) //gets user name
            val workshopDetailsFromDb = workshopDetailsRepository.getWorkshopsDetails()
            if (workshopDetailsFromDb.isEmpty()) {
                val workshopDetailsFromApi = workshopDetailsRepository.getWorkshopList()
                if (workshopDetailsFromApi.isSuccessful) {
                    saveApiResponseToDb(workshopDetailsFromApi)
                }
            } else {
                workshopDetailLiveData.postValue(
                    workshopDetailsFromDb.sortedWith(
                        compareBy(WorkshopDetailItem::facility_name)
                    )
                )
            }
            loadingStatusLiveData.postValue(LoadingStatusType.Loaded)
        }
    }
    fun logout(){
        clearDb()
    }
    private fun clearDb(){
        viewModelScope.launch {
            loadingStatusLiveData.postValue(LoadingStatusType.Loading)
            workshopDetailsRepository.deleteAll() //delete all local workshop details
            userDetailsRepository.deleteUserData()
            loadingStatusLiveData.postValue(LoadingStatusType.Loaded)
            dbClearStatus.postValue(true)
        }
    }
    fun recallDataFromServer() {
        viewModelScope.launch(coroutineExceptionHandler) {
            loadingStatusLiveData.postValue(LoadingStatusType.Loading)
            val workshopDetailsFromApi = workshopDetailsRepository.getWorkshopList()
            if (workshopDetailsFromApi.isSuccessful) {
                workshopDetailsRepository.deleteAll() //delete all local workshop details
                saveApiResponseToDb(workshopDetailsFromApi)
            }
            loadingStatusLiveData.postValue(LoadingStatusType.Loaded)
        }
    }

    suspend fun saveApiResponseToDb(workshopDetailsFrmApi: Response<WorkshopDetail>) {
        val workshopDetailsToInsertInDB = mutableListOf<WorkshopDetailItem>()
        workshopDetailsFrmApi.body()?.let {
            for (eachWorkshopDetail in it) {
                val workshopDetailItem = WorkshopDetailItem(
                    eachWorkshopDetail.business_type,
                    eachWorkshopDetail.expiration_date,
                    eachWorkshopDetail.facility,
                    eachWorkshopDetail.facility_city,
                    eachWorkshopDetail.facility_county,
                    eachWorkshopDetail.facility_name,
                    eachWorkshopDetail.facility_name_overflow,
                    eachWorkshopDetail.facility_state,
                    eachWorkshopDetail.facility_street,
                    eachWorkshopDetail.facility_zip_code,
                    eachWorkshopDetail.last_renewal_date,
                    eachWorkshopDetail.origional_issuance_date,
                    eachWorkshopDetail.owner_name,
                    eachWorkshopDetail.owner_name_overflow
                )
                workshopDetailsToInsertInDB.add(workshopDetailItem)

                workshopDetailLiveData.postValue(
                    workshopDetailsToInsertInDB.sortedWith(
                        compareBy(WorkshopDetailItem::facility_name)
                    )
                )
            }
            workshopDetailsRepository.insertAll(workshopDetailsToInsertInDB)
        }
    }
}