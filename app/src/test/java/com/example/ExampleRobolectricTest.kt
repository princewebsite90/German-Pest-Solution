package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.model.PestServiceCatalog
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    @Test
    fun `read app name string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("German Pest Solution", appName)
    }

    @Test
    fun `verify pest service catalog contains core services`() {
        val services = PestServiceCatalog.services
        assertTrue("Services list should not be empty", services.isNotEmpty())
        assertTrue("Contains General Pest Control", services.any { it.id == "general" })
        assertTrue("Contains Anti-Termite Treatment", services.any { it.id == "termite" })
        assertEquals("9870515182", PestServiceCatalog.PRIMARY_PHONE)
        assertEquals("9711545655", PestServiceCatalog.SECONDARY_PHONE)
    }
}
