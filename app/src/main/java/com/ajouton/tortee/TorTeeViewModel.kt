package com.ajouton.tortee

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.ajouton.tortee.data.BoardDataProvider
import com.ajouton.tortee.model.Bulletin
import com.ajouton.tortee.data.ViewType
import com.ajouton.tortee.model.User
import com.ajouton.tortee.network.TorTeeApiService
import com.ajouton.tortee.network.UserSignUpRequest
import com.ajouton.tortee.ui.state.TorteeUIState
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class TorTeeViewModel() : ViewModel() {

    private val BASE_URL = "http://144.24.75.46:3000/auth/login"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService: TorTeeApiService by lazy {
        retrofit.create(TorTeeApiService::class.java)
    }

    private val _uiState = MutableStateFlow(TorteeUIState())
    private val _isBulletinContentShowing = MutableStateFlow(false)
    private val _isBulletinWriterShowing = MutableStateFlow(false)
    private val _selectedBulletin = MutableStateFlow(Bulletin("", "", ""))
    private val _userSignUpRequest = MutableStateFlow(UserSignUpRequest("", "", "", ""))

    val uiState: StateFlow<TorteeUIState> = _uiState
    val isBulletinContentShowing: StateFlow<Boolean> = _isBulletinContentShowing
    val isBulletinWriterShowing: StateFlow<Boolean> = _isBulletinWriterShowing
    val selectedBulletin: StateFlow<Bulletin> = _selectedBulletin
    val userSignUpRequest: StateFlow<UserSignUpRequest> = _userSignUpRequest
    var isSignUpOK: Boolean = true

    fun updateCurrentView(viewType: ViewType) {
        _uiState.update {
            it.copy(
                currentViewType = viewType
            )
        }
    }

    fun setBulletinWriterVisibility() {

    }

    fun setBulletinContentVisibility(visibility: Boolean, bulletin: Bulletin? = null) {
        _isBulletinContentShowing.update {
            visibility
        }
        if(visibility) {
            _selectedBulletin.update {
                bulletin ?: Bulletin("empty", "empty", "empty")
            }
        }
    }

    fun getUsers() {

    }

    fun signUp(user: UserSignUpRequest) {
        val userSignUpRequest = UserSignUpRequest("email@gmail.com", "name", "nickname", "password")
        viewModelScope.launch {
            isSignUpOK = try {
                Log.e("signup",isSignUpOK.toString())
                retrofitService.signUp("token", userSignUpRequest)
            } catch(e: IOException) {
                Log.e("signup","IOException")
                false
            } catch(e: HttpException) {
                Log.e("signup","HttpException")
                false
            }
        }
    }

    fun getBulletinList(): List<Bulletin> {
        return BoardDataProvider.bulletinList
    }
}