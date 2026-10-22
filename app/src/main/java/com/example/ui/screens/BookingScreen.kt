package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.BookingEntity
import com.example.data.model.PestService
import com.example.ui.components.getServiceIcon
import com.example.ui.theme.BrandCardBorder
import com.example.ui.theme.BrandGold
import com.example.ui.theme.BrandGreen
import com.example.ui.theme.BrandGreenLight
import com.example.ui.theme.NavyPrimary
import com.example.ui.theme.NavySecondary
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun BookingScreen(
    service: PestService,
    initialPropertyType: String? = null,
    savedCustomerName: String = "",
    savedCustomerPhone: String = "",
    savedCustomerAddress: String = "",
    onBack: () -> Unit,
    onSubmitBooking: (
        service: PestService,
        propertyType: String,
        price: Int,
        name: String,
        phone: String,
        address: String,
        date: String,
        timeSlot: String,
        notes: String
    ) -> Unit,
    onDirectWhatsApp: (String) -> Unit,
    onDirectCall: () -> Unit
) {
    var selectedPropertyType by remember {
        mutableStateOf(initialPropertyType ?: service.pricing.first().propertyType)
    }
    val currentPricing = service.pricing.find { it.propertyType == selectedPropertyType }
        ?: service.pricing.first()

    // Date choices
    val calendar = Calendar.getInstance()
    val sdf = SimpleDateFormat("EEE, dd MMM", Locale.getDefault())
    val dateChoices = remember {
        val list = mutableListOf<String>()
        val cal = Calendar.getInstance()
        list.add("Today (" + SimpleDateFormat("dd MMM", Locale.getDefault()).format(cal.time) + ")")
        cal.add(Calendar.DAY_OF_YEAR, 1)
        list.add("Tomorrow (" + SimpleDateFormat("dd MMM", Locale.getDefault()).format(cal.time) + ")")
        cal.add(Calendar.DAY_OF_YEAR, 1)
        list.add(sdf.format(cal.time))
        cal.add(Calendar.DAY_OF_YEAR, 1)
        list.add(sdf.format(cal.time))
        cal.add(Calendar.DAY_OF_YEAR, 1)
        list.add(sdf.format(cal.time))
        list
    }
    var selectedDateIndex by remember { mutableIntStateOf(1) } // default Tomorrow

    val timeSlots = listOf(
        "09:00 AM - 12:00 PM",
        "12:00 PM - 03:00 PM",
        "03:00 PM - 06:00 PM",
        "06:00 PM - 08:30 PM"
    )
    var selectedTimeSlotIndex by remember { mutableIntStateOf(0) }

    // Form fields
    var name by remember { mutableStateOf(savedCustomerName) }
    var phone by remember { mutableStateOf(savedCustomerPhone.replace("+91 ", "").trim()) }
    var address by remember { mutableStateOf(savedCustomerAddress) }
    var selectedCity by remember { mutableStateOf("Delhi") }
    var notes by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val cities = listOf("Delhi", "Noida", "Gurgaon", "Faridabad", "Ghaziabad", "Greater Noida")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Book Service",
                        color = NavyPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = NavyPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color(0xFFF8FAFC)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Selected Service Header Card
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = NavyPrimary),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White.copy(alpha = 0.12f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = getServiceIcon(service.iconName),
                            contentDescription = null,
                            tint = BrandGold,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = service.title,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Text(
                            text = "Scope: $selectedPropertyType",
                            color = BrandGold,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.sp
                        )
                    }

                    Text(
                        text = "₹${currentPricing.price}",
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Step 1: Select Property Size
            Text(
                text = "1. Property / Unit Scope",
                color = NavyPrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                service.pricing.forEach { p ->
                    val isSelected = p.propertyType == selectedPropertyType
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isSelected) NavySecondary else Color.White)
                            .border(
                                1.dp,
                                if (isSelected) NavySecondary else BrandCardBorder,
                                RoundedCornerShape(10.dp)
                            )
                            .clickable { selectedPropertyType = p.propertyType }
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "${p.propertyType} • ₹${p.price}",
                            color = if (isSelected) Color.White else NavyPrimary,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Step 2: Preferred Service Date
            Text(
                text = "2. Preferred Date",
                color = NavyPrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                dateChoices.forEachIndexed { idx, d ->
                    val isSelected = idx == selectedDateIndex
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isSelected) BrandGreen else Color.White)
                            .border(
                                1.dp,
                                if (isSelected) BrandGreen else BrandCardBorder,
                                RoundedCornerShape(10.dp)
                            )
                            .clickable { selectedDateIndex = idx }
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = d,
                            color = if (isSelected) Color.White else Color(0xFF334155),
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Step 3: Preferred Time Slot
            Text(
                text = "3. Preferred Time Slot",
                color = NavyPrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                timeSlots.forEachIndexed { idx, slot ->
                    val isSelected = idx == selectedTimeSlotIndex
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isSelected) NavyPrimary else Color.White)
                            .border(
                                1.dp,
                                if (isSelected) NavyPrimary else BrandCardBorder,
                                RoundedCornerShape(10.dp)
                            )
                            .clickable { selectedTimeSlotIndex = idx }
                            .padding(horizontal = 10.dp, vertical = 7.dp)
                    ) {
                        Text(
                            text = slot,
                            color = if (isSelected) Color.White else Color(0xFF334155),
                            fontSize = 11.5.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Step 4: Customer Details
            Text(
                text = "4. Service Address & Contact",
                color = NavyPrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Your Full Name") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("booking_input_name")
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = phone,
                onValueChange = { if (it.length <= 10) phone = it },
                label = { Text("10-Digit Mobile Number") },
                prefix = { Text("+91 ") },
                leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("booking_input_phone")
            )

            Spacer(modifier = Modifier.height(10.dp))

            // City Selection
            Text(
                text = "Service City (Delhi NCR)",
                color = Color(0xFF475569),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(6.dp))

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                cities.forEach { city ->
                    val isSelected = city == selectedCity
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isSelected) NavySecondary else Color(0xFFE2E8F0))
                            .clickable { selectedCity = city }
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                    ) {
                        Text(
                            text = city,
                            color = if (isSelected) Color.White else Color(0xFF1E293B),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("Flat / House No, Street, Landmark ($selectedCity)") },
                leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null) },
                minLines = 2,
                maxLines = 3,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("booking_input_address")
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Pest remarks / special requests (optional)") },
                leadingIcon = { Icon(Icons.Default.Notes, contentDescription = null) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            if (errorMessage != null) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = errorMessage!!,
                    color = Color(0xFFDC2626),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Payment Notice
            Card(
                colors = CardDefaults.cardColors(containerColor = BrandGreenLight),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = BrandGreen,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Pay After Inspection & Service • Zero upfront payment required",
                        color = NavyPrimary,
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Submit Button
            Button(
                onClick = {
                    val trimmedName = name.trim()
                    val trimmedPhone = phone.trim()
                    val trimmedAddress = address.trim()

                    if (trimmedName.isEmpty()) {
                        errorMessage = "Please enter your full name"
                        return@Button
                    }
                    if (!Regex("^[6-9]\\d{9}$").matches(trimmedPhone)) {
                        errorMessage = "Please enter a valid 10-digit Indian mobile number"
                        return@Button
                    }
                    if (trimmedAddress.length < 5) {
                        errorMessage = "Please provide your complete service address in $selectedCity"
                        return@Button
                    }

                    errorMessage = null
                    val fullAddress = "$trimmedAddress, $selectedCity"
                    val chosenDate = dateChoices[selectedDateIndex]
                    val chosenSlot = timeSlots[selectedTimeSlotIndex]

                    onSubmitBooking(
                        service,
                        selectedPropertyType,
                        currentPricing.price,
                        trimmedName,
                        trimmedPhone,
                        fullAddress,
                        chosenDate,
                        chosenSlot,
                        notes.trim()
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = NavyPrimary,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("submit_booking_button")
            ) {
                Text(
                    text = "Confirm Booking Request (₹${currentPricing.price})",
                    fontSize = 14.5.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // WhatsApp instant booking option
            OutlinedButton(
                onClick = {
                    val msg = "Hello German Pest Solution,\nI want to book *${service.title}* (${selectedPropertyType} - ₹${currentPricing.price})\nDate: ${dateChoices[selectedDateIndex]} (${timeSlots[selectedTimeSlotIndex]})\nName: ${name.ifBlank { "Customer" }}\nPhone: $phone\nCity: $selectedCity\nAddress: $address"
                    onDirectWhatsApp(msg)
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("whatsapp_booking_btn")
            ) {
                Icon(
                    imageVector = Icons.Default.Chat,
                    contentDescription = null,
                    tint = Color(0xFF25D366),
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Book directly on WhatsApp",
                    color = Color(0xFF1E293B),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
