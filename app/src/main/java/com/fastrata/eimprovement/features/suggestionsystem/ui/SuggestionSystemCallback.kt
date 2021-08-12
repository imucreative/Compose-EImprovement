package com.fastrata.eimprovement.features.suggestionsystem.ui

import com.fastrata.eimprovement.features.suggestionsystem.data.model.SuggestionSystemModel

interface SuggestionSystemCallback {
    fun onItemClicked(data: SuggestionSystemModel)
}