package com.ajouton.tortee

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajouton.tortee.data.BoardDataProvider
import com.ajouton.tortee.model.Bulletin
import com.ajouton.tortee.data.ViewType
import com.ajouton.tortee.network.TorTeeApiService
import com.ajouton.tortee.network.UserSignInRequest
import com.ajouton.tortee.network.UserSignInResponse
import com.ajouton.tortee.ui.state.TorteeUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class TorTeeViewModel() : ViewModel() {

    private val BASE_URL = "http://144.24.75.46:3000/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService: TorTeeApiService by lazy {
        retrofit.create(TorTeeApiService::class.java)
    }

    private val _uiState = MutableStateFlow(TorteeUIState())
    private val _signUpPageVisibility = MutableStateFlow(false)
    private val _userIdInput = MutableStateFlow("")
    private val _userPasswordInput = MutableStateFlow("")
    private val _isBulletinContentShowing = MutableStateFlow(false)
    private val _isBulletinWriterShowing = MutableStateFlow(false)
    private val _selectedBulletin = MutableStateFlow(Bulletin("", "", ""))
    private val _userSignUpRequest = MutableStateFlow(UserSignInRequest("", ""))
    private val _isSignedIn = MutableStateFlow(false)
    private val _userSignInResponse = MutableStateFlow(UserSignInResponse(false, 0))

    val uiState: StateFlow<TorteeUIState> = _uiState
    val signUpPageVisibility: StateFlow<Boolean> = _signUpPageVisibility
    val userIdInput: StateFlow<String> = _userIdInput
    val userPasswordInput: StateFlow<String> = _userPasswordInput
    val isBulletinContentShowing: StateFlow<Boolean> = _isBulletinContentShowing
    val isBulletinWriterShowing: StateFlow<Boolean> = _isBulletinWriterShowing
    val selectedBulletin: StateFlow<Bulletin> = _selectedBulletin
    val userSignUpRequest: StateFlow<UserSignInRequest> = _userSignUpRequest
    val isSignedIn: StateFlow<Boolean> = _isSignedIn
    val userSignInResponse: StateFlow<UserSignInResponse> = _userSignInResponse

    fun updateCurrentView(viewType: ViewType) {
        _uiState.update {
            it.copy(
                currentViewType = viewType
            )
        }
    }

    fun updateUserIdInput(input: String) {
        _userIdInput.update { input }
    }

    fun updateUserPasswordInput(input: String) {
        _userPasswordInput.update { input }
    }

    fun setSignUpPageVisibility(visibility: Boolean) {
        _signUpPageVisibility.update { visibility }
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

    fun signIn(user: UserSignInRequest) {
        val userSignInRequest = UserSignInRequest(userIdInput.value, userPasswordInput.value)
        viewModelScope.launch {
            _userSignInResponse.update {
                try {
                    Log.e("signup", "Success")
                    retrofitService.signIn(userSignInRequest)
                } catch(e: IOException) {
                    e.printStackTrace()
                    Log.e("signup","IOException")
                    UserSignInResponse(false, 0)
                } catch(e: HttpException) {
                    Log.e("signup","HttpException")
                    UserSignInResponse(false, 0)
                }
            }
            _isSignedIn.update {
                Log.e("", userSignInResponse.value.result.toString())
                Log.e("", userSignInResponse.value.id.toString())
                userSignInResponse.value.result
            }
        }
    }

    fun getBulletinList(): List<Bulletin> {
        return BoardDataProvider.bulletinList
    }
}