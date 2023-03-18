package com.ajouton.tortee

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajouton.tortee.data.BoardDataProvider
import com.ajouton.tortee.model.Bulletin
import com.ajouton.tortee.data.ViewType
import com.ajouton.tortee.model.User
import com.ajouton.tortee.network.*
import com.ajouton.tortee.ui.state.TorteeUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.*

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

    // sign up
    private val _signUpPageVisibility = MutableStateFlow(false)
    private val _signUpEmail = MutableStateFlow("")
    private val _signUpPassword = MutableStateFlow("")
    private val _signUpName = MutableStateFlow("")
    private val _signUpNickname = MutableStateFlow("")
    private val _signUpDescription = MutableStateFlow("")
    private val _userSignUpResponse = MutableStateFlow(UserSignUpResponse(-1))
    private val _userSignUpResponseValue = MutableStateFlow(-1)

    // sign in
    private val _userIdInput = MutableStateFlow("")
    private val _userPasswordInput = MutableStateFlow("")
    private val _userSignInResponse = MutableStateFlow(UserSignInResponse(false, 0))
    private val _isSignedIn = MutableStateFlow(false)

    // search mentor
    private val _searchMentorResponse = MutableStateFlow<GetUserResponse?>(null)
    private val _mentorList = MutableStateFlow<List<User>?>(null)

    // bulletin
    private val _isBulletinContentShowing = MutableStateFlow(false)
    private val _isBulletinWriterShowing = MutableStateFlow(false)
    private val _selectedBulletin = MutableStateFlow(Bulletin("", "", ""))


    val uiState: StateFlow<TorteeUIState> = _uiState

    // sign up
    val signUpPageVisibility: StateFlow<Boolean> = _signUpPageVisibility
    val signUpEmail: StateFlow<String> = _signUpEmail
    val signUpPassword: StateFlow<String> = _signUpPassword
    val signUpName: StateFlow<String> = _signUpName
    val signUpNickname: StateFlow<String> = _signUpNickname
    val signUpDescription: StateFlow<String> = _signUpDescription
    val userSignUpResponse: StateFlow<UserSignUpResponse> = _userSignUpResponse
    val userSignUpResponseValue: StateFlow<Int> = _userSignUpResponseValue

    // sign in
    val userIdInput: StateFlow<String> = _userIdInput
    val userPasswordInput: StateFlow<String> = _userPasswordInput
    val userSignInResponse: StateFlow<UserSignInResponse> = _userSignInResponse
    val isSignedIn: StateFlow<Boolean> = _isSignedIn

    // search mentor
    val searchMentorResponse: StateFlow<GetUserResponse?> = _searchMentorResponse
    val mentorList: StateFlow<List<User>?> = _mentorList

    // bulletin
    val isBulletinContentShowing: StateFlow<Boolean> = _isBulletinContentShowing
    val isBulletinWriterShowing: StateFlow<Boolean> = _isBulletinWriterShowing
    val selectedBulletin: StateFlow<Bulletin> = _selectedBulletin

    fun updateCurrentView(viewType: ViewType) {
        _uiState.update {
            it.copy(
                currentViewType = viewType
            )
        }
    }

    fun getMentorList(): List<User>? {
        return mentorList.value
    }

    fun updateSignUpEmail(input: String) {
        _signUpEmail.update { input }
    }

    fun updateSignUpPassword(input: String) {
        _signUpPassword.update { input }
    }

    fun updateSignUpName(input: String) {
        _signUpName.update { input }
    }

    fun updateSignUpNickname(input: String) {
        _signUpNickname.update { input }
    }

    fun updateSignUpDescription(input: String) {
        _signUpDescription.update { input }
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

    @SuppressLint("SuspiciousIndentation")
    fun searchMentor(tag: String) {
        Log.e("", tag)
        Log.e("", listOf(tag).toString())
        val searchMentorRequest = GetUserRequest(listOf(tag))
        viewModelScope.launch {
            _searchMentorResponse.update {
                try {
                    Log.e("searchMentor", "Success")
                    retrofitService.searchMentor(searchMentorRequest)
                } catch(e: IOException) {
                    e.printStackTrace()
                    Log.e("searchMentor","IOException")
                    null
                } catch(e: HttpException) {
                    Log.e("searchMentor","HttpException")
                    null
                }
            }
            _mentorList.update { listOf() }
            var list: ArrayList<User> = arrayListOf()
                    Log.e("", searchMentorResponse.value.toString())
            for(mentor in searchMentorResponse.value?.members!!) {
                var taglist: ArrayList<String> = arrayListOf()
                for(memberTag in mentor.memberTags) {
                    taglist.add(memberTag.tag.name)
                }
                list.add(User(mentor.email, mentor.description, mentor.name, mentor.nickname, taglist))
            }
            _mentorList.update {
                list
            }
        }

    }

    fun signUp() {
        val userSignUpRequest = UserSignUpRequest(signUpEmail.value, signUpPassword.value, signUpName.value, signUpNickname.value, signUpDescription.value, listOf("Java", "Spring", "Flask"))
        viewModelScope.launch {
            _userSignUpResponse.update {
                try {
                    Log.e("signup", "Success")
                    retrofitService.signUp(userSignUpRequest)
                } catch(e: IOException) {
                    e.printStackTrace()
                    Log.e("signup","IOException")
                    UserSignUpResponse(-1)
                } catch(e: HttpException) {
                    Log.e("signup","HttpException")
                    UserSignUpResponse(-1)
                }
            }
            _userSignUpResponseValue.update {
                Log.e("", userSignUpResponse.value.result.toString())
                userSignUpResponse.value.result
            }
            if(userSignUpResponseValue.value == 0) {
                _signUpPageVisibility.update { false }
            }
        }
    }

    fun hideSignUpPage() {
        _signUpPageVisibility.update { false }
    }

    fun signIn() {
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