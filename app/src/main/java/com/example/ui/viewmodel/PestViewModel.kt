package com.example.ui.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.BookingEntity
import com.example.data.local.UserSession
import com.example.data.model.PestService
import com.example.data.model.PestServiceCatalog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.random.Random

enum class AppTab {
    HOME,
    BOOKINGS,
    PEST_GUIDE,
    CONTACT
}

class PestViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    private val bookingDao = database.bookingDao()
    private val userSession = UserSession(application)

    val allBookings: StateFlow<List<BookingEntity>> = bookingDao.getAllBookings()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _currentTab = MutableStateFlow(AppTab.HOME)
    val currentTab: StateFlow<AppTab> = _currentTab.asStateFlow()

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _detailService = MutableStateFlow<PestService?>(null)
    val detailService: StateFlow<PestService?> = _detailService.asStateFlow()

    private val _bookingService = MutableStateFlow<PestService?>(null)
    val bookingService: StateFlow<PestService?> = _bookingService.asStateFlow()

    private val _showLoginSheet = MutableStateFlow(false)
    val showLoginSheet: StateFlow<Boolean> = _showLoginSheet.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(userSession.isLoggedIn)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _userPhone = MutableStateFlow(userSession.userPhone)
    val userPhone: StateFlow<String> = _userPhone.asStateFlow()

    private val _userName = MutableStateFlow(userSession.userName)
    val userName: StateFlow<String> = _userName.asStateFlow()

    private val _userAddress = MutableStateFlow(userSession.userAddress)
    val userAddress: StateFlow<String> = _userAddress.asStateFlow()

    private val _lastCreatedBooking = MutableStateFlow<BookingEntity?>(null)
    val lastCreatedBooking: StateFlow<BookingEntity?> = _lastCreatedBooking.asStateFlow()

    fun setTab(tab: AppTab) {
        _currentTab.value = tab
    }

    fun setCategory(category: String) {
        _selectedCategory.value = category
    }

    fun openServiceDetail(service: PestService) {
        _detailService.value = service
    }

    fun closeServiceDetail() {
        _detailService.value = null
    }

    fun startBooking(service: PestService) {
        _detailService.value = null
        _bookingService.value = service
    }

    fun closeBooking() {
        _bookingService.value = null
    }

    fun setShowLogin(show: Boolean) {
        _showLoginSheet.value = show
    }

    fun saveUserSession(phone: String, name: String, address: String) {
        userSession.login(phone, name, address)
        _isLoggedIn.value = true
        _userPhone.value = phone
        _userName.value = name
        _userAddress.value = address
    }

    fun logout() {
        userSession.logout()
        _isLoggedIn.value = false
        _userPhone.value = ""
        _userName.value = ""
        _userAddress.value = ""
    }

    fun createBooking(
        service: PestService,
        propertyType: String,
        price: Int,
        customerName: String,
        customerPhone: String,
        serviceAddress: String,
        preferredDate: String,
        timeSlot: String,
        additionalNotes: String,
        onSuccess: (BookingEntity) -> Unit
    ) {
        viewModelScope.launch {
            val randomRef = "GPS-${Random.nextInt(10000, 99999)}"
            val booking = BookingEntity(
                bookingReference = randomRef,
                serviceId = service.id,
                serviceTitle = service.title,
                propertyType = propertyType,
                price = price,
                customerName = customerName,
                customerPhone = customerPhone,
                serviceAddress = serviceAddress,
                preferredDate = preferredDate,
                timeSlot = timeSlot,
                additionalNotes = additionalNotes,
                status = "Confirmed"
            )
            val id = bookingDao.insertBooking(booking)
            val savedBooking = booking.copy(id = id)
            _lastCreatedBooking.value = savedBooking

            // Also remember user info for next time
            saveUserSession(customerPhone, customerName, serviceAddress)
            onSuccess(savedBooking)
        }
    }

    fun cancelBooking(bookingId: Long) {
        viewModelScope.launch {
            bookingDao.updateStatus(bookingId, "Cancelled")
        }
    }

    fun deleteBooking(bookingId: Long) {
        viewModelScope.launch {
            bookingDao.deleteById(bookingId)
        }
    }

    fun clearLastCreatedBooking() {
        _lastCreatedBooking.value = null
    }

    fun dialCall(context: Context, phone: String = PestServiceCatalog.PRIMARY_PHONE) {
        try {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phone")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Unable to launch dialer", Toast.LENGTH_SHORT).show()
        }
    }

    fun openWhatsApp(context: Context, customMessage: String? = null) {
        try {
            val msg = customMessage ?: "Hello German Pest Solution, I would like to inquire about your professional pest control services."
            val encoded = Uri.encode(msg)
            val url = "https://api.whatsapp.com/send?phone=${PestServiceCatalog.WHATSAPP_NUMBER}&text=$encoded"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            // Fallback to website or dialer
            dialCall(context)
        }
    }

    fun openWebsite(context: Context) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(PestServiceCatalog.WEBSITE)).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Unable to open browser", Toast.LENGTH_SHORT).show()
        }
    }
}
