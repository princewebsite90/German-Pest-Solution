package com.example.data.model

data class PropertyPrice(
    val propertyType: String,
    val price: Int,
    val description: String = ""
)

data class PestService(
    val id: String,
    val title: String,
    val subtitle: String,
    val iconName: String,
    val imageUrl: String,
    val category: String, // "Home", "Commercial", "Targeted"
    val startingPrice: Int,
    val pricing: List<PropertyPrice>,
    val benefits: List<String>,
    val treatmentProcess: List<String>,
    val warrantyText: String,
    val chemicalsInfo: String,
    val estimatedDuration: String
)

object PestServiceCatalog {
    const val COMPANY_NAME = "German Pest Solution"
    const val WEBSITE = "https://germanpestsolution.com"
    const val PRIMARY_PHONE = "9870515182"
    const val SECONDARY_PHONE = "9711545655"
    const val WHATSAPP_NUMBER = "919870515182"
    const val SERVICE_AREAS = "Delhi • Noida • Gurgaon • Faridabad • Ghaziabad"

    val services: List<PestService> = listOf(
        PestService(
            id = "general",
            title = "General Pest Control",
            subtitle = "Cockroach, ants, silverfish & common crawling household insects",
            iconName = "pest_control",
            imageUrl = "https://images.unsplash.com/photo-1581578731548-c64695cc6952?w=800",
            category = "Home",
            startingPrice = 1350,
            pricing = listOf(
                PropertyPrice("1 BHK", 1350, "Standard 1 bedroom apartment"),
                PropertyPrice("2 BHK", 1650, "2 bedroom apartment / floor"),
                PropertyPrice("3 BHK", 1800, "3 bedroom apartment / floor"),
                PropertyPrice("4 BHK", 2050, "4 bedroom apartment / floor"),
                PropertyPrice("Duplex / Villa", 2750, "Independent house up to 2500 sq ft")
            ),
            benefits = listOf(
                "Odourless and non-staining German gel formulation",
                "100% child and pet safe application",
                "No need to vacate premises during or after treatment",
                "Complete kitchen, drainage, and cabinet protection",
                "Certified technicians with identity verification"
            ),
            treatmentProcess = listOf(
                "Thorough inspection of hidden cockroach and insect breeding spots",
                "Dot application of micro-encapsulated bait gel inside hinges and electrical sockets",
                "Synthetic pyrethroid spray along baseboards, bathrooms, and wet areas",
                "Drain baiting to eliminate sewer roaches"
            ),
            warrantyText = "Includes 45-day warranty with free follow-up service if pests reappear.",
            chemicalsInfo = "Certified Bayer & BASF government-approved herbal gel & synthetic pyrethroids.",
            estimatedDuration = "45 - 60 minutes"
        ),
        PestService(
            id = "termite",
            title = "Anti-Termite Treatment",
            subtitle = "Targeted subterranean termite drilling & barrier treatment with warranty",
            iconName = "shield",
            imageUrl = "https://images.unsplash.com/photo-1513694203232-719a280e022f?w=800",
            category = "Targeted",
            startingPrice = 1550,
            pricing = listOf(
                PropertyPrice("1 BHK", 1550, "Inspection + localized barrier injection"),
                PropertyPrice("2 BHK", 2250, "Comprehensive drill-fill-seal perimeter"),
                PropertyPrice("3 BHK", 2800, "Full wooden fittings & wall-floor junctions"),
                PropertyPrice("4 BHK / Villa", 3800, "Heavy-duty comprehensive termite proofing"),
                PropertyPrice("Pre-Construction (per sqft)", 12, "Foundation soil chemical barrier")
            ),
            benefits = listOf(
                "Up to 2-Year written service warranty certificate",
                "Precision 12mm drill-fill-seal method with matching chalk plugs",
                "Protects expensive wooden cabinets, doors, wardrobes and baseboards",
                "Long-lasting non-repellent termiticide colony elimination",
                "Free semi-annual check-up visit"
            ),
            treatmentProcess = listOf(
                "Acoustic and visual inspection of infested wooden structures",
                "Drilling holes at 45-degree angle along skirting at 1-foot intervals",
                "High-pressure injection of chemical emulsion deep into walls and wood",
                "Sealing all drilled holes with color-matching waterproof white cement"
            ),
            warrantyText = "1 to 2 Years written service guarantee based on package chosen.",
            chemicalsInfo = "Imidacloprid 30.5% SC / Fipronil odourless non-repellent termiticides.",
            estimatedDuration = "2 - 3 hours"
        ),
        PestService(
            id = "bedbugs",
            title = "Bed Bug Treatment",
            subtitle = "Thermal steam & 2-round intensive chemical eradication for deep relief",
            iconName = "bed",
            imageUrl = "https://images.unsplash.com/photo-1505693416388-ac5ce068fe85?w=800",
            category = "Home",
            startingPrice = 1199,
            pricing = listOf(
                PropertyPrice("1 BHK", 1199, "2 treatment visits included"),
                PropertyPrice("2 BHK", 1699, "2 treatment visits included"),
                PropertyPrice("3 BHK", 2199, "2 treatment visits included"),
                PropertyPrice("Commercial / Hostel Room", 999, "Per room bulk treatment rate")
            ),
            benefits = listOf(
                "Double-visit treatment schedule ensures egg-cycle breakdown",
                "Deep mattress seam, sofa fold, and bed frame penetration",
                "Safe contact spray that kills bugs on touch and leaves long residual",
                "Quick relief from night bites, skin rashes, and sleep disturbances",
                "Technician safety instructions provided before treatment"
            ),
            treatmentProcess = listOf(
                "Detailed inspection of seams, mattresses, headboards, and switchboards",
                "First intensive spray targeting adult bed bugs, nymphs, and hiding spots",
                "Follow-up visit on Day 12-15 to kill newly hatched eggs before breeding",
                "Preventative barrier spray along baseboards and curtains"
            ),
            warrantyText = "60-day complete eradication guarantee including the mandatory 2nd visit.",
            chemicalsInfo = "Micro-encapsulated contact sprays safe for sleeping areas after airing.",
            estimatedDuration = "1 - 2 hours per session"
        ),
        PestService(
            id = "rodent",
            title = "Rodent & Rat Control",
            subtitle = "Tamper-proof bait stations, glue boards, entry proofing & rodent repelling",
            iconName = "pest_control_rodent",
            imageUrl = "https://images.unsplash.com/photo-1584622650111-993a426fbf0a?w=800",
            category = "Targeted",
            startingPrice = 799,
            pricing = listOf(
                PropertyPrice("1 BHK", 799, "Strategic baiting & 4 glue boards"),
                PropertyPrice("2 BHK", 1099, "6 glue boards + bromadiolone cake stations"),
                PropertyPrice("3 BHK", 1399, "Full home entry audit & multi-point baiting"),
                PropertyPrice("Commercial (up to 1000 sqft)", 2199, "Tamper-proof outdoor bait boxes + traps")
            ),
            benefits = listOf(
                "Safe anticoagulant baits that cause rodents to seek outdoor water and light",
                "Industrial sticky glue boards placed in strategic travel runways",
                "Protects expensive car cables, electronic wiring, and stored grains",
                "Identification of pipe gaps, AC pipe holes, and duct entry points",
                "Follow-up removal assistance provided"
            ),
            treatmentProcess = listOf(
                "Identification of droppings, gnaw marks, and entry corridors",
                "Deployment of heavy-duty cardboard & plastic glue pads",
                "Placement of weather-resistant bait cakes in enclosed tamper-resistant boxes",
                "Advice and wire mesh recommendations on sealing pipe intrusions"
            ),
            warrantyText = "30-day rodent-free assurance with free re-trapping visits if needed.",
            chemicalsInfo = "Bromadiolone wax blocks and food-grade peanut-aroma non-toxic glue attractants.",
            estimatedDuration = "45 minutes"
        ),
        PestService(
            id = "mosquito",
            title = "Mosquito & Fly Fogging",
            subtitle = "Indoor residual spraying, outdoor thermal fogging & larvicide treatment",
            iconName = "bug_report",
            imageUrl = "https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=800",
            category = "Home",
            startingPrice = 699,
            pricing = listOf(
                PropertyPrice("1 BHK", 699, "Indoor misting + balcony spray"),
                PropertyPrice("2 BHK", 999, "Full apartment residual spray"),
                PropertyPrice("3 BHK", 1299, "Apartment + balconies + shafts"),
                PropertyPrice("Society / Lawn (up to 5000 sqft)", 2999, "Thermal fogging + water body larvicide")
            ),
            benefits = listOf(
                "Immediate knockdown of active adult mosquitoes and flies",
                "Larvicide application to prevent breeding in drains and standing water",
                "Protection against Dengue, Malaria, and Chikungunya",
                "High-performance cold ULV foggers reach dense foliage and corners",
                "Fast-dissipating treatment allowing reentry within 30 minutes"
            ),
            treatmentProcess = listOf(
                "Inspection of coolers, garden pots, drain pits, and shafts",
                "Ultra-Low Volume (ULV) cold misting of dark corners, curtains, and ceilings",
                "Thermal pulse fogging of outdoor gardens and perimeter bushes",
                "Application of eco-friendly biological granules in stagnant water"
            ),
            warrantyText = "Instant knockdown guarantee; recommended bi-weekly during monsoon seasons.",
            chemicalsInfo = "WHO-approved Deltamethrin & Pyrethrum natural flower extract.",
            estimatedDuration = "30 - 45 minutes"
        ),
        PestService(
            id = "commercial",
            title = "Commercial Pest Control",
            subtitle = "Customized HACCP & FSSAI compliant pest management for businesses",
            iconName = "domain",
            imageUrl = "https://images.unsplash.com/photo-1497366216548-37526070297c?w=800",
            category = "Commercial",
            startingPrice = 1999,
            pricing = listOf(
                PropertyPrice("Up to 1000 sqft", 1999, "Small office, clinic, retail showroom"),
                PropertyPrice("Up to 2500 sqft", 3499, "Restaurant kitchen, bank, fitness center"),
                PropertyPrice("5000+ sqft / Enterprise", 5999, "Warehouse, hotel, school, factory plant")
            ),
            benefits = listOf(
                "Audit-ready service log sheet, chemical safety data sheets (MSDS)",
                "Flexible after-hours & weekend scheduling without interrupting operations",
                "Integrated Pest Management (IPM) covering insects, termites, and rodents",
                "Dedicated corporate account manager & emergency response within 4 hours",
                "GST invoices and formal service agreements"
            ),
            treatmentProcess = listOf(
                "Comprehensive site survey & risk zoning (pantry, storage, server room)",
                "Multi-tiered gel baiting, spray barrier, and electronic insect light trap checks",
                "Perimeter bait station maintenance and audit reporting",
                "Staff pest awareness recommendations"
            ),
            warrantyText = "Quarterly & Annual Maintenance Contracts (AMC) available with continuous guarantee.",
            chemicalsInfo = "FSSAI & HACCP approved food-grade compliant treatments.",
            estimatedDuration = "1.5 - 3 hours"
        ),
        PestService(
            id = "bird_netting",
            title = "Bird Netting & Spikes",
            subtitle = "Durable UV-stabilized nylon netting & stainless steel spikes for balconies",
            iconName = "flight",
            imageUrl = "https://images.unsplash.com/photo-1548767797-d8c844163c4c?w=800",
            category = "Targeted",
            startingPrice = 1499,
            pricing = listOf(
                PropertyPrice("Small Balcony (up to 50 sqft)", 1499, "High grade nylon netting + hooks"),
                PropertyPrice("Medium Balcony (up to 120 sqft)", 2499, "Heavy duty wire rope & anchors"),
                PropertyPrice("Large Balcony / Terrace (up to 250 sqft)", 4499, "Comprehensive netting"),
                PropertyPrice("AC Unit Spikes (per unit)", 599, "304 Grade Stainless steel spikes")
            ),
            benefits = listOf(
                "3-Year durability guarantee against sun rot and bird peck",
                "Translucent invisible finish that doesn't block light or ventilation",
                "Humane solution — keeps pigeons away without harming them",
                "Prevents bird droppings, foul odor, and respiratory allergies",
                "Rust-proof stainless steel wire rope framework"
            ),
            treatmentProcess = listOf(
                "Measurement of balcony openings and structural support points",
                "Deep cleaning and disinfection of existing pigeon droppings",
                "Installation of perimeter anchor bolts and tensioned stainless cable",
                "Precision clipping of UV-stabilized mesh tight as a drum"
            ),
            warrantyText = "3-Year replacement warranty on netting fabric against UV wear.",
            chemicalsInfo = "100% mechanical humane barrier, zero toxic chemicals.",
            estimatedDuration = "2 - 3 hours"
        ),
        PestService(
            id = "sanitization",
            title = "Disinfection & Sanitization",
            subtitle = "Hospital-grade cold fogging eliminating 99.9% viruses, bacteria & fungi",
            iconName = "clean_hands",
            imageUrl = "https://images.unsplash.com/photo-1584483766114-2cea6facdf57?w=800",
            category = "Home",
            startingPrice = 899,
            pricing = listOf(
                PropertyPrice("1 BHK", 899, "High-touch surface wipe + cold ULV mist"),
                PropertyPrice("2 BHK", 1299, "Complete apartment misting"),
                PropertyPrice("3 BHK", 1599, "All rooms, kitchen & washrooms"),
                PropertyPrice("Office (up to 2000 sqft)", 2499, "Full workspace sanitization")
            ),
            benefits = listOf(
                "Eliminates 99.99% of bacteria, flu viruses, and mildew",
                "Neutralizes persistent pet and damp odors",
                "Safe on electronics, upholstery, wooden polish, and glass",
                "Leaves a fresh pleasant citrus aroma",
                "Rapid re-entry in just 20 minutes"
            ),
            treatmentProcess = listOf(
                "Surface wipe-down of doorknobs, switches, and countertops",
                "High-pressure ULV atomized fogging creating micron mist",
                "Air conditioning intake disinfection",
                "Short aeration period before occupancy"
            ),
            warrantyText = "Instant antimicrobial protection; laboratory tested efficacy.",
            chemicalsInfo = "Quaternary Ammonium Compound (QAC) / Silver Hydrogen Peroxide approved by EPA.",
            estimatedDuration = "30 - 45 minutes"
        )
    )

    fun getServiceById(id: String): PestService? = services.find { it.id == id }
}
