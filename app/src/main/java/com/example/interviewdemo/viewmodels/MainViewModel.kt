package com.example.interviewdemo.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.interviewdemo.models.WorkshopDetailItem
import com.example.interviewdemo.repositories.WorkshopDetailsRepository
import com.example.interviewdemo.utils.LoadingStatusType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private var workshopDetailsRepository: WorkshopDetailsRepository) :
    BaseViewModel() {
    val workshopDetailLiveData by lazy { MutableLiveData<List<WorkshopDetailItem>>() }

    fun getWorkShopDetails() {
        viewModelScope.launch(coroutineExceptionHandler) {
            loadingStatusLiveData.postValue(LoadingStatusType.Loading)

            val workshopDetailsFromDb = workshopDetailsRepository.getWorkshopsDetails()
            if (workshopDetailsFromDb.isEmpty()) {
                val workshopDetailsFromApi = workshopDetailsRepository.getWorkshopList()
                val workshopDetailsToInsertInDB = mutableListOf<WorkshopDetailItem>()

                if (workshopDetailsFromApi.isSuccessful) {
                    workshopDetailsFromApi.body()?.let {
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

                        workshopDetailLiveData.postValue(workshopDetailsToInsertInDB.sortedWith(
                            compareBy(WorkshopDetailItem::facility_name)
                        ))
                    }
                        workshopDetailsRepository.insertAll(workshopDetailsToInsertInDB)
                    }


                }
            } else {
                workshopDetailLiveData.postValue(workshopDetailsFromDb.sortedWith(
                    compareBy(WorkshopDetailItem::facility_name)))
            }
            loadingStatusLiveData.postValue(LoadingStatusType.Loaded)
        }
    }
}