package com.example.interviewdemo.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.interviewdemo.models.UserDetail
import com.example.interviewdemo.repositories.user.UserDataRepository
import com.example.interviewdemo.utils.LoadingStatusType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailFormViewModel @Inject constructor(private var userDataRepository: UserDataRepository) :
    BaseViewModel() {
   fun setUserData(userData : UserDetail){
       loadingStatusLiveData.postValue(LoadingStatusType.Loading)
        viewModelScope.launch {
            val userDataList = mutableListOf<UserDetail>()
            userDataList.add(userData)
            userDataRepository.insertUserData(userDataList)
        }
       loadingStatusLiveData.postValue(LoadingStatusType.Loaded)
   }
}