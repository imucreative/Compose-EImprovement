package com.fastrata.eimprovement.utils

import com.fastrata.eimprovement.features.ChangesPoint.data.model.ChangePointRewardModel
import com.fastrata.eimprovement.features.ChangesPoint.data.model.ChangePointSystemModel
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

    fun generateDummyChangePointList() : ArrayList<ChangePointSystemModel>{
        val suggestionSystem = ArrayList<ChangePointSystemModel>()

        suggestionSystem.add(ChangePointSystemModel(
            "CP-0000/08/2021/0042",
            "Aktif",
            "Wed, 20 Jan 19, 05:08 PM",
            "testing",
            "PUSAT",
            "FBPST - Gd Barang Dagang",
            "1"
        ))

        suggestionSystem.add(ChangePointSystemModel(
            "CP-0001/08/2021/0042",
            "Aktif",
            "Wed, 20 Jan 19, 05:08 PM",
            "testing",
            "PUSAT",
            "FBPST - Gd Barang Dagang",
            "1"
        ))

        suggestionSystem.add(ChangePointSystemModel(
            "CP-0002/08/2021/0042",
            "Aktif",
            "Wed, 20 Jan 19, 05:08 PM",
            "testing",
            "PUSAT",
            "FBPST - Gd Barang Dagang",
            "1"
        ))

        suggestionSystem.add(ChangePointSystemModel(
            "CP-0003/08/2021/0042",
            "Aktif",
            "Wed, 20 Jan 19, 05:08 PM",
            "testing",
            "PUSAT",
            "FBPST - Gd Barang Dagang",
            "1"
        ))

        suggestionSystem.add(ChangePointSystemModel(
            "CP-0004/08/2021/0042",
            "Aktif",
            "Wed, 20 Jan 19, 05:08 PM",
            "testing",
            "PUSAT",
            "FBPST - Gd Barang Dagang",
            "1"
        ))

        suggestionSystem.add(ChangePointSystemModel(
            "CP-0005/08/2021/0042",
            "Aktif",
            "Wed, 20 Jan 19, 05:08 PM",
            "testing",
            "PUSAT",
            "FBPST - Gd Barang Dagang",
            "1"
        ))

       return suggestionSystem
    }

    fun generaterewardmodel() : ArrayList<ChangePointRewardModel>{

        val suggestionSystem = ArrayList<ChangePointRewardModel>()

        suggestionSystem.add(
            ChangePointRewardModel(
            "PULSA 10K",
            "1",
            "Pulsa",

        ))
        suggestionSystem.add(
            ChangePointRewardModel(
                "PULSA 20K",
                "1",
                "Pulsa",
            ))

        suggestionSystem.add(
            ChangePointRewardModel(
                "PULSA 10K",
                "1",
                "Pulsa",

                ))
        suggestionSystem.add(
            ChangePointRewardModel(
                "PULSA 20K",
                "1",
                "Pulsa",
            ))

        suggestionSystem.add(
            ChangePointRewardModel(
                "PULSA 10K",
                "1",
                "Pulsa",

                ))
        suggestionSystem.add(
            ChangePointRewardModel(
                "PULSA 20K",
                "1",
                "Pulsa",
            ))
        suggestionSystem.add(
            ChangePointRewardModel(
                "PULSA 10K",
                "1",
                "Pulsa",

                ))
        suggestionSystem.add(
            ChangePointRewardModel(
                "PULSA 20K",
                "1",
                "Pulsa",
            ))
        return suggestionSystem
    }


}