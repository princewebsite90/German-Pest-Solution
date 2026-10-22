package com.example.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ContactPhone
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BrandGreen
import com.example.ui.theme.BrandGreenLight
import com.example.ui.theme.NavyPrimary
import com.example.ui.viewmodel.AppTab

@Composable
fun AppBottomNav(
    currentTab: AppTab,
    bookingsCount: Int,
    onTabSelected: (AppTab) -> Unit
) {
    NavigationBar(
        containerColor = Color.White,
        contentColor = NavyPrimary
    ) {
        NavigationBarItem(
            selected = currentTab == AppTab.HOME,
            onClick = { onTabSelected(AppTab.HOME) },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = NavyPrimary,
                selectedTextColor = NavyPrimary,
                indicatorColor = BrandGreenLight,
                unselectedIconColor = Color(0xFF64748B),
                unselectedTextColor = Color(0xFF64748B)
            ),
            modifier = Modifier.testTag("nav_tab_home")
        )

        NavigationBarItem(
            selected = currentTab == AppTab.BOOKINGS,
            onClick = { onTabSelected(AppTab.BOOKINGS) },
            icon = {
                if (bookingsCount > 0) {
                    BadgedBox(badge = {
                        Badge(containerColor = BrandGreen, contentColor = Color.White) {
                            Text("$bookingsCount")
                        }
                    }) {
                        Icon(Icons.Default.CalendarMonth, contentDescription = "Bookings")
                    }
                } else {
                    Icon(Icons.Default.CalendarMonth, contentDescription = "Bookings")
                }
            },
            label = { Text("Bookings", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = NavyPrimary,
                selectedTextColor = NavyPrimary,
                indicatorColor = BrandGreenLight,
                unselectedIconColor = Color(0xFF64748B),
                unselectedTextColor = Color(0xFF64748B)
            ),
            modifier = Modifier.testTag("nav_tab_bookings")
        )

        NavigationBarItem(
            selected = currentTab == AppTab.PEST_GUIDE,
            onClick = { onTabSelected(AppTab.PEST_GUIDE) },
            icon = { Icon(Icons.Default.MenuBook, contentDescription = "Pest Guide") },
            label = { Text("Pest Guide", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = NavyPrimary,
                selectedTextColor = NavyPrimary,
                indicatorColor = BrandGreenLight,
                unselectedIconColor = Color(0xFF64748B),
                unselectedTextColor = Color(0xFF64748B)
            ),
            modifier = Modifier.testTag("nav_tab_guide")
        )

        NavigationBarItem(
            selected = currentTab == AppTab.CONTACT,
            onClick = { onTabSelected(AppTab.CONTACT) },
            icon = { Icon(Icons.Default.ContactPhone, contentDescription = "Contact") },
            label = { Text("Contact", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = NavyPrimary,
                selectedTextColor = NavyPrimary,
                indicatorColor = BrandGreenLight,
                unselectedIconColor = Color(0xFF64748B),
                unselectedTextColor = Color(0xFF64748B)
            ),
            modifier = Modifier.testTag("nav_tab_contact")
        )
    }
}
