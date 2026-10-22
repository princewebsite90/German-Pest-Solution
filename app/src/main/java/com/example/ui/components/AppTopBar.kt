package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.BrandCardBorder
import com.example.ui.theme.BrandGreen
import com.example.ui.theme.NavyPrimary
import com.example.ui.theme.NavySecondary

@Composable
fun AppTopBar(
    isLoggedIn: Boolean,
    userPhone: String,
    onCallClick: () -> Unit,
    onProfileClick: () -> Unit,
    onLogoClick: () -> Unit = {}
) {
    Surface(
        color = Color.White,
        shadowElevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // App / Company Logo
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(NavyPrimary)
                    .clickable { onLogoClick() }
                    .testTag("app_logo_button"),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_company_logo),
                    contentDescription = "German Pest Solution Logo",
                    modifier = Modifier.size(42.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "German Pest Solution",
                    color = NavyPrimary,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Delhi NCR • Safe & Certified",
                    color = Color(0xFF64748B),
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1
                )
            }

            // Quick Call Action
            FilledIconButton(
                onClick = onCallClick,
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = BrandGreen,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .size(38.dp)
                    .testTag("top_bar_call_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = "Call German Pest Solution",
                    modifier = Modifier.size(19.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // User Profile / Sign in button
            IconButton(
                onClick = onProfileClick,
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (isLoggedIn) Color(0xFFEFF6FF) else Color(0xFFF1F5F9))
                    .testTag("top_bar_profile_button")
            ) {
                Icon(
                    imageVector = if (isLoggedIn) Icons.Filled.Person else Icons.Outlined.PersonOutline,
                    contentDescription = if (isLoggedIn) "User: $userPhone" else "Sign In",
                    tint = if (isLoggedIn) NavySecondary else Color(0xFF475569),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
