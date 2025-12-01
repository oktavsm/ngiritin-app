package com.ngiritin.app.ui.onboarding

import com.ngiritin.app.R

data class OnboardingItem(
    val title: String,
    val description: String,
    val imageResId: Int
)

object OnboardingContent {
    val list = listOf(
        OnboardingItem(
            title = "Stay in Control of Your Money",
            description = "Track your cash flow effortlessly and keep your finances organized without the stress",
            imageResId = R.drawable.bg_onboarding_0
        ),
        OnboardingItem(
            title = "Add Transactions in Seconds",
            description = "Use manual input or let AI fill it for you with voice or casual text \n‘super quick, super smooth’.",
            imageResId = R.drawable.bg_onboarding_1
        ),
        OnboardingItem(
            title = "Stay Within Your Budget",
            description = "Get notified when your spending hits certain limits so your budget stays safe and steady.",
            imageResId = R.drawable.bg_onboarding_2
        ),
        OnboardingItem(
            title = "See Where Your Money Goes",
            description = "Visual charts and insights help you understand your habits and make smarter financial moves.",
            imageResId = R.drawable.bg_onboarding_3
        )
    )
}