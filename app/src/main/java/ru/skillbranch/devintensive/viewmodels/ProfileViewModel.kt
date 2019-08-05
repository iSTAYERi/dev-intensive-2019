package ru.skillbranch.devintensive.viewmodels

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.skillbranch.devintensive.models.Profile
import ru.skillbranch.devintensive.repositories.PreferencesRepository
import ru.skillbranch.devintensive.utils.Utils

class ProfileViewModel: ViewModel() {
    private val repository = PreferencesRepository
    private val profileData = MutableLiveData<Profile>()
    private val appTheme = MutableLiveData<Int>()
    private val repositoryError = MutableLiveData<Boolean>()

    init {
        profileData.value = repository.getProfile()
        appTheme.value = repository.getAppTheme()
    }

    override fun onCleared() {
        super.onCleared()
    }

    fun getProfileData(): LiveData<Profile> = profileData
    fun getTheme(): LiveData<Int> = appTheme
    fun getRepositoryError(): LiveData<Boolean> = repositoryError

    fun saveProfileData(profile: Profile) {
        val prof = if (repositoryError.value!!) profile.copy(repository = "") else profile
        repository.saveProfile(prof)
        profileData.value = prof
    }

    fun switchTheme() {
        if (appTheme.value == AppCompatDelegate.MODE_NIGHT_YES) {
            appTheme.value = AppCompatDelegate.MODE_NIGHT_NO
        } else {
            appTheme.value = AppCompatDelegate.MODE_NIGHT_YES
        }
        repository.saveAppTheme(appTheme.value!!)
    }

    fun onRepositoryChanged(repository: String) {
        repositoryError.value = !Utils.isRepositoryValid(repository)
    }
}