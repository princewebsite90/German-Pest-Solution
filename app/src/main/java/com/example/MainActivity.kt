package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.data.model.PestServiceCatalog
import com.example.ui.components.AppBottomNav
import com.example.ui.components.AppTopBar
import com.example.ui.components.BookingConfirmationDialog
import com.example.ui.screens.BookingScreen
import com.example.ui.screens.ContactScreen
import com.example.ui.screens.CustomerProfileSheet
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.MyBookingsScreen
import com.example.ui.screens.PestGuideScreen
import com.example.ui.screens.ServiceDetailSheet
import com.example.ui.screens.SplashScreen
import com.example.ui.theme.BrandGreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.AppTab
import com.example.ui.viewmodel.PestViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val viewModel: PestViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainAppContainer(viewModel = viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppContainer(viewModel: PestViewModel) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var showSplash by remember { mutableStateOf(true) }

    val currentTab by viewModel.currentTab.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val allBookings by viewModel.allBookings.collectAsState()
    val detailService by viewModel.detailService.collectAsState()
    val bookingService by viewModel.bookingService.collectAsState()
    val showLoginSheet by viewModel.showLoginSheet.collectAsState()
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()
    val userPhone by viewModel.userPhone.collectAsState()
    val userName by viewModel.userName.collectAsState()
    val userAddress by viewModel.userAddress.collectAsState()
    val lastCreatedBooking by viewModel.lastCreatedBooking.collectAsState()

    val detailSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val profileSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (showSplash) {
        SplashScreen(onFinish = { showSplash = false })
    } else {
        // If user is currently in the Booking Flow sub-screen
        if (bookingService != null) {
            BackHandler {
                viewModel.closeBooking()
            }
            BookingScreen(
                service = bookingService!!,
                savedCustomerName = userName,
                savedCustomerPhone = userPhone,
                savedCustomerAddress = userAddress,
                onBack = { viewModel.closeBooking() },
                onSubmitBooking = { s, prop, price, name, phone, address, date, slot, notes ->
                    viewModel.createBooking(
                        service = s,
                        propertyType = prop,
                        price = price,
                        customerName = name,
                        customerPhone = phone,
                        serviceAddress = address,
                        preferredDate = date,
                        timeSlot = slot,
                        additionalNotes = notes,
                        onSuccess = {
                            viewModel.closeBooking()
                            viewModel.setTab(AppTab.BOOKINGS)
                        }
                    )
                },
                onDirectWhatsApp = { msg ->
                    viewModel.openWhatsApp(context, msg)
                },
                onDirectCall = {
                    viewModel.dialCall(context)
                }
            )
        } else {
            // Main App Scaffold with Tabs
            BackHandler(enabled = currentTab != AppTab.HOME) {
                viewModel.setTab(AppTab.HOME)
            }

            Scaffold(
                topBar = {
                    AppTopBar(
                        isLoggedIn = isLoggedIn,
                        userPhone = userPhone,
                        onCallClick = { viewModel.dialCall(context) },
                        onProfileClick = { viewModel.setShowLogin(true) },
                        onLogoClick = { viewModel.setTab(AppTab.HOME) }
                    )
                },
                bottomBar = {
                    AppBottomNav(
                        currentTab = currentTab,
                        bookingsCount = allBookings.size,
                        onTabSelected = { viewModel.setTab(it) }
                    )
                },
                floatingActionButton = {
                    if (currentTab == AppTab.HOME) {
                        FloatingActionButton(
                            onClick = { viewModel.dialCall(context) },
                            containerColor = BrandGreen,
                            contentColor = Color.White,
                            modifier = Modifier.testTag("fab_quick_call")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Call,
                                contentDescription = "Emergency Call",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                },
                containerColor = Color(0xFFF8FAFC)
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    when (currentTab) {
                        AppTab.HOME -> {
                            HomeScreen(
                                services = PestServiceCatalog.services,
                                selectedCategory = selectedCategory,
                                onSelectCategory = { viewModel.setCategory(it) },
                                onServiceDetail = { viewModel.openServiceDetail(it) },
                                onServiceBook = { viewModel.startBooking(it) },
                                onCallClick = { viewModel.dialCall(context) },
                                onWhatsAppClick = { viewModel.openWhatsApp(context) }
                            )
                        }
                        AppTab.BOOKINGS -> {
                            MyBookingsScreen(
                                bookings = allBookings,
                                onExploreServices = { viewModel.setTab(AppTab.HOME) },
                                onCallTechnician = { viewModel.dialCall(context) },
                                onCancelBooking = { viewModel.cancelBooking(it) }
                            )
                        }
                        AppTab.PEST_GUIDE -> {
                            PestGuideScreen(
                                onBookService = { viewModel.startBooking(it) }
                            )
                        }
                        AppTab.CONTACT -> {
                            ContactScreen(
                                onCallPrimary = { viewModel.dialCall(context, PestServiceCatalog.PRIMARY_PHONE) },
                                onCallSecondary = { viewModel.dialCall(context, PestServiceCatalog.SECONDARY_PHONE) },
                                onOpenWhatsApp = { viewModel.openWhatsApp(context) },
                                onOpenWebsite = { viewModel.openWebsite(context) }
                            )
                        }
                    }
                }
            }

            // Service Detail Bottom Sheet
            if (detailService != null) {
                ServiceDetailSheet(
                    service = detailService!!,
                    sheetState = detailSheetState,
                    onDismiss = { viewModel.closeServiceDetail() },
                    onBookClick = { s, _, _ ->
                        viewModel.startBooking(s)
                    },
                    onCallClick = {
                        viewModel.dialCall(context)
                    }
                )
            }

            // Customer Profile Sheet
            if (showLoginSheet) {
                CustomerProfileSheet(
                    sheetState = profileSheetState,
                    isLoggedIn = isLoggedIn,
                    currentPhone = userPhone,
                    currentName = userName,
                    currentAddress = userAddress,
                    onDismiss = { viewModel.setShowLogin(false) },
                    onSaveProfile = { p, n, a ->
                        viewModel.saveUserSession(p, n, a)
                    },
                    onLogout = {
                        viewModel.logout()
                    }
                )
            }

            // Booking Created Confirmation Dialog
            if (lastCreatedBooking != null) {
                BookingConfirmationDialog(
                    booking = lastCreatedBooking!!,
                    onDismiss = { viewModel.clearLastCreatedBooking() },
                    onShareWhatsApp = { msg ->
                        viewModel.openWhatsApp(context, msg)
                    },
                    onCall = { viewModel.dialCall(context) }
                )
            }
        }
    }
}
