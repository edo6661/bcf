package com.example.slicingbcf.data.local

data class FeedbackMentor(
    val issueSharingRating: Int,
    val stakeholderMappingRating: Int,
    val fundingStrategyRating: Int,
    val comprehensiveExplanation: Int = 0,
    val sessionSuitability: Int = 0,
    val clearInstructions: Int = 0
)
