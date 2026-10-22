package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.PestControl
import androidx.compose.material.icons.filled.PestControlRodent
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.PestService
import com.example.data.model.PestServiceCatalog
import com.example.ui.theme.BrandCardBorder
import com.example.ui.theme.BrandGold
import com.example.ui.theme.BrandGreen
import com.example.ui.theme.BrandGreenLight
import com.example.ui.theme.NavyPrimary
import com.example.ui.theme.NavySecondary

data class PestGuideItem(
    val title: String,
    val icon: ImageVector,
    val relatedServiceId: String,
    val dangerSigns: List<String>,
    val preventionTips: List<String>,
    val professionalSolution: String
)

@Composable
fun PestGuideScreen(
    onBookService: (PestService) -> Unit
) {
    val guides = remember {
        listOf(
            PestGuideItem(
                title = "Cockroach Infestations",
                icon = Icons.Default.PestControl,
                relatedServiceId = "general",
                dangerSigns = listOf(
                    "Black pepper-like droppings in kitchen corners and drawer hinges",
                    "Unpleasant musty oily odor near sinks or refrigerators",
                    "Roaches scurrying when lights turn on at night"
                ),
                preventionTips = listOf(
                    "Wipe kitchen counters completely dry before sleeping",
                    "Store all dry flour, pulses, and grains in airtight glass/metal jars",
                    "Seal crevices around drainage sink pipes using silicone sealant"
                ),
                professionalSolution = "German Gel micro-dots + drainage residual spray eliminates complete colony in 7-10 days."
            ),
            PestGuideItem(
                title = "Termites (White Ants)",
                icon = Icons.Default.Shield,
                relatedServiceId = "termite",
                dangerSigns = listOf(
                    "Mud tubes on walls, skirting boards or doorframes",
                    "Hollow sounding wooden doors or powdery sawdust deposits",
                    "Swarming winged alates around light fixtures during monsoons"
                ),
                preventionTips = listOf(
                    "Never store damp cardboard or scrap wood directly on ground",
                    "Fix leaking bathroom pipes promptly to avoid moisture in sub-walls",
                    "Keep wooden wardrobes at least 2 inches away from damp walls"
                ),
                professionalSolution = "Drill-Fill-Seal non-repellent chemical barrier deep inside walls with up to 2-year warranty."
            ),
            PestGuideItem(
                title = "Bed Bugs",
                icon = Icons.Default.Bed,
                relatedServiceId = "bedbugs",
                dangerSigns = listOf(
                    "Itchy red bite welts in lines or clusters upon waking",
                    "Tiny dark blood specks on bedsheets and pillow covers",
                    "Sweet, musty odor in severe mattress seam infestations"
                ),
                preventionTips = listOf(
                    "Inspect hotel luggage before unpacking inside bedroom",
                    "Wash bed linen in hot water (60°C+) if exposure is suspected",
                    "Encase mattresses in protective zippered covers"
                ),
                professionalSolution = "Two-cycle targeted chemical and heat treatment breaking the complete egg cycle."
            ),
            PestGuideItem(
                title = "Rats & Rodents",
                icon = Icons.Default.PestControlRodent,
                relatedServiceId = "rodent",
                dangerSigns = listOf(
                    "Gnawed electrical wires, plastic pipes, and food containers",
                    "Dark capsule-shaped droppings along walls and ceilings",
                    "Scratching noises inside false ceilings during night hours"
                ),
                preventionTips = listOf(
                    "Seal openings larger than 1/4 inch with steel wool mesh",
                    "Keep garbage bin lids firmly closed at all times",
                    "Trim tree branches overhanging your roof or balcony"
                ),
                professionalSolution = "Industrial bait stations, non-toxic sticky glue runways, and structural entry proofing."
            ),
            PestGuideItem(
                title = "Mosquitoes (Dengue/Malaria)",
                icon = Icons.Default.BugReport,
                relatedServiceId = "mosquito",
                dangerSigns = listOf(
                    "Standing water in air coolers, flower pot saucers, or terrace drains",
                    "High mosquito density during dusk and dawn hours",
                    "Breeding larvae visible in stagnant water containers"
                ),
                preventionTips = listOf(
                    "Empty and scrub water coolers once every week",
                    "Install mesh screens on windows and balcony doors",
                    "Ensure terrace and basement drain lines are unblocked"
                ),
                professionalSolution = "ULV cold misting of indoor surfaces + outdoor thermal fogging and biological larvicides."
            )
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Column {
                Text(
                    text = "Pest Identification & Guide",
                    color = NavyPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Expert prevention tips and warning signs from German Pest Solution",
                    color = Color(0xFF64748B),
                    fontSize = 12.sp
                )
            }
        }

        items(guides) { guide ->
            var expanded by remember { mutableStateOf(false) }

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(BrandCardBorder)),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(BrandGreenLight),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = guide.icon,
                                contentDescription = null,
                                tint = NavyPrimary,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = guide.title,
                                color = NavyPrimary,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = if (expanded) "Tap to collapse" else "Tap to read symptoms & prevention",
                                color = Color(0xFF64748B),
                                fontSize = 11.5.sp
                            )
                        }
                    }

                    if (expanded) {
                        Spacer(modifier = Modifier.height(14.dp))

                        // Warning signs
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.WarningAmber,
                                contentDescription = null,
                                tint = Color(0xFFD97706),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Warning Signs:",
                                color = Color(0xFF92400E),
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.5.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        guide.dangerSigns.forEach { sign ->
                            Text(
                                text = "• $sign",
                                color = Color(0xFF475569),
                                fontSize = 12.sp,
                                lineHeight = 16.sp,
                                modifier = Modifier.padding(vertical = 2.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Prevention tips
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = BrandGreen,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "DIY Prevention Tips:",
                                color = Color(0xFF065F46),
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.5.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        guide.preventionTips.forEach { tip ->
                            Text(
                                text = "• $tip",
                                color = Color(0xFF475569),
                                fontSize = 12.sp,
                                lineHeight = 16.sp,
                                modifier = Modifier.padding(vertical = 2.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Professional solution
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFFEFF6FF))
                                .padding(10.dp)
                        ) {
                            Text(
                                text = "Our Solution: ${guide.professionalSolution}",
                                color = NavySecondary,
                                fontSize = 11.5.sp,
                                fontWeight = FontWeight.Medium,
                                lineHeight = 15.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = {
                                val s = PestServiceCatalog.getServiceById(guide.relatedServiceId)
                                if (s != null) onBookService(s)
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = NavyPrimary,
                                contentColor = Color.White
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp)
                        ) {
                            Text("Book Treatment for This Pest", fontSize = 12.5.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}
