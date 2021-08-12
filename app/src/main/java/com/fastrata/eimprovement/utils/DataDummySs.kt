package com.fastrata.eimprovement.utils

import com.fastrata.eimprovement.features.projectimprovement.data.model.ProjectImprovementModel
import com.fastrata.eimprovement.features.suggestionsystem.data.model.SuggestionSystemModel
import com.fastrata.eimprovement.features.suggestionsystem.data.model.SuggestionSystemTeamMemberModel

object DataDummySs {
    fun generateDummySuggestionSystem(): ArrayList<SuggestionSystemModel> {

        val suggestionSystem = ArrayList<SuggestionSystemModel>()

        suggestionSystem.add(SuggestionSystemModel(
            "SS-0000/08/2021/0041",
            "Membuat ide baru",
            "Implementasi Project",
            "test",
            "PUSAT",
            "FBPST - Gd Barang Dagang"))
        suggestionSystem.add(SuggestionSystemModel(
            "SS-0000/08/2021/0042",
            "Membuat ide baru new",
            "Implementasi Project",
            "test",
            "PUSAT",
            "FBPST - Gd Barang Dagang"))
        suggestionSystem.add(SuggestionSystemModel(
            "SS-0000/08/2021/0042",
            "Membuat ide baru new",
            "Implementasi Project",
            "test",
            "PUSAT",
            "FBPST - Gd Barang Dagang"))
        suggestionSystem.add(SuggestionSystemModel(
            "SS-0000/08/2021/0042",
            "Membuat ide baru new",
            "Implementasi Project",
            "test",
            "PUSAT",
            "FBPST - Gd Barang Dagang"))
        suggestionSystem.add(SuggestionSystemModel(
            "SS-0000/08/2021/0042",
            "Membuat ide baru new",
            "Implementasi Project",
            "test",
            "PUSAT",
            "FBPST - Gd Barang Dagang"))
        suggestionSystem.add(SuggestionSystemModel(
            "SS-0000/08/2021/0042",
            "Membuat ide baru new",
            "Implementasi Project",
            "test",
            "PUSAT",
            "FBPST - Gd Barang Dagang"))
        suggestionSystem.add(SuggestionSystemModel(
            "SS-0000/08/2021/0042",
            "Membuat ide baru new",
            "Implementasi Project",
            "test",
            "PUSAT",
            "FBPST - Gd Barang Dagang"))
        suggestionSystem.add(SuggestionSystemModel(
            "SS-0000/08/2021/0042",
            "Membuat ide baru new",
            "Implementasi Project",
            "test",
            "PUSAT",
            "FBPST - Gd Barang Dagang"))

        return suggestionSystem
    }

    fun generateDummyTeamMember(): ArrayList<SuggestionSystemTeamMemberModel> {

        val suggestionSystem = ArrayList<SuggestionSystemTeamMemberModel>()

        suggestionSystem.add(SuggestionSystemTeamMemberModel(
            "Jery",
            "ICT",
            "Membuat dokumentasi"))
        suggestionSystem.add(SuggestionSystemTeamMemberModel(
            "Tom",
            "ICT",
            "Implementasi Project"))
        suggestionSystem.add(SuggestionSystemTeamMemberModel(
            "Budi",
            "ICT",
            "Menyiapkan peralatan"))
        suggestionSystem.add(SuggestionSystemTeamMemberModel(
            "Budi",
            "ICT",
            "Menyiapkan peralatan"))
        suggestionSystem.add(SuggestionSystemTeamMemberModel(
            "Budi",
            "ICT",
            "Menyiapkan peralatan"))


        return suggestionSystem
    }

    fun generateDummyProjectImprovementList() : ArrayList<ProjectImprovementModel> {
        val suggestionSystem = ArrayList<ProjectImprovementModel>()

        suggestionSystem.add(ProjectImprovementModel(
            "PI-0000/08/2021/0042",
            "Membuat ide baru new",
            "Implementasi Project",
            "test",
            "PUSAT",
            "FBPST - Gd Barang Dagang",
            "Wed, 20 Jan 19, 05:08 PM",
            "John"
        ))

        suggestionSystem.add(ProjectImprovementModel(
            "PI-0000/08/2021/0042",
            "Membuat ide baru new",
            "Implementasi Project",
            "test",
            "PUSAT",
            "FBPST - Gd Barang Dagang",
            "Wed, 20 Jan 19, 05:08 PM",
            "John"
        ))

        suggestionSystem.add(ProjectImprovementModel(
            "PI-0000/08/2021/0042",
            "Membuat ide baru new",
            "Implementasi Project",
            "test",
            "PUSAT",
            "FBPST - Gd Barang Dagang",
            "Wed, 20 Jan 19, 05:08 PM",
            "John"
        ))

        suggestionSystem.add(ProjectImprovementModel(
            "PI-0000/08/2021/0042",
            "Membuat ide baru new",
            "Implementasi Project",
            "test",
            "PUSAT",
            "FBPST - Gd Barang Dagang",
            "Wed, 20 Jan 19, 05:08 PM",
            "John"
        ))

        suggestionSystem.add(ProjectImprovementModel(
            "PI-0000/08/2021/0042",
            "Membuat ide baru new",
            "Implementasi Project",
            "test",
            "PUSAT",
            "FBPST - Gd Barang Dagang",
            "Wed, 20 Jan 19, 05:08 PM",
            "John"
        ))

        suggestionSystem.add(ProjectImprovementModel(
            "PI-0000/08/2021/0042",
            "Membuat ide baru new",
            "Implementasi Project",
            "test",
            "PUSAT",
            "FBPST - Gd Barang Dagang",
            "Wed, 20 Jan 19, 05:08 PM",
            "John"
        ))

        suggestionSystem.add(ProjectImprovementModel(
            "PI-0000/08/2021/0042",
            "Membuat ide baru new",
            "Implementasi Project",
            "test",
            "PUSAT",
            "FBPST - Gd Barang Dagang",
            "Wed, 20 Jan 19, 05:08 PM",
            "John"
        ))

        return suggestionSystem
    }
}