package com.fastrata.eimprovement.utils

import com.fastrata.eimprovement.features.ChangesPoint.data.model.ChangePointRewardModel
import com.fastrata.eimprovement.features.ChangesPoint.data.model.ChangePointSystemModel
import com.fastrata.eimprovement.features.approval.data.model.ApprovalModel
import com.fastrata.eimprovement.features.projectimprovement.data.model.AkarMasalahModel
import com.fastrata.eimprovement.features.projectimprovement.data.model.ProjectImprovementModel
import com.fastrata.eimprovement.features.suggestionsystem.data.model.SuggestionSystemModel
import com.fastrata.eimprovement.features.suggestionsystem.data.model.TeamMemberItem

object DataDummySs {
    fun generateDummySuggestionSystem(): ArrayList<SuggestionSystemModel> {

        val suggestionSystem = ArrayList<SuggestionSystemModel>()

        suggestionSystem.add(SuggestionSystemModel(
            "SS-0000/08/2021/0041",
            "17-08-2021",
            "Membuat ide baru",
            "Implementasi Project",
            "test",
            "PUSAT",
            "FBPST - Gd Barang Dagang"))
        suggestionSystem.add(SuggestionSystemModel(
            "SS-0000/08/2021/0042",
            "17-08-2021",
            "Membuat ide baru new",
            "Implementasi Project",
            "test",
            "PUSAT",
            "FBPST - Gd Barang Dagang"))
        suggestionSystem.add(SuggestionSystemModel(
            "SS-0000/08/2021/0043",
            "17-08-2021",
            "Membuat ide baru new",
            "Implementasi Project",
            "test",
            "PUSAT",
            "FBPST - Gd Barang Dagang"))
        suggestionSystem.add(SuggestionSystemModel(
            "SS-0000/08/2021/0044",
            "17-08-2021",
            "Membuat ide baru new",
            "Implementasi Project",
            "test",
            "PUSAT",
            "FBPST - Gd Barang Dagang"))
        suggestionSystem.add(SuggestionSystemModel(
            "SS-0000/08/2021/0045",
            "17-08-2021",
            "Membuat ide baru new",
            "Implementasi Project",
            "test",
            "PUSAT",
            "FBPST - Gd Barang Dagang"))
        suggestionSystem.add(SuggestionSystemModel(
            "SS-0000/08/2021/0046",
            "17-08-2021",
            "Membuat ide baru new",
            "Implementasi Project",
            "test",
            "PUSAT",
            "FBPST - Gd Barang Dagang"))

        return suggestionSystem
    }

    fun generateDummyTeamMember(): ArrayList<TeamMemberItem> {

        val suggestionSystem = ArrayList<TeamMemberItem>()

        suggestionSystem.add(TeamMemberItem(
            "Jery",
            "ICT",
            "Membuat dokumentasi"))
        suggestionSystem.add(TeamMemberItem(
            "Tom",
            "ICT",
            "Implementasi Project"))
        suggestionSystem.add(TeamMemberItem(
            "Budi",
            "ICT",
            "Menyiapkan peralatan"))
        suggestionSystem.add(TeamMemberItem(
            "Anto",
            "ICT",
            "Menyiapkan peralatan"))
        suggestionSystem.add(TeamMemberItem(
            "User",
            "ICT",
            "Menyiapkan peralatan"))


        return suggestionSystem
    }

    fun generateDummyApproval(): ArrayList<ApprovalModel> {

        val approval = ArrayList<ApprovalModel>()

        approval.add(ApprovalModel(
            "SS-0000/08/2021/0001",
            "PEMBUATAN SISTEM SARAN FASTRATA BUANA",
            "Laporan Akhir Di Submit",
            "SS",
            "User Development",
            "00043087",
            "PUSAT",
            "FBPST - Gd Barang Dagang",
            "2021-08-06 17:19:22",))
        approval.add(ApprovalModel(
            "SS-0000/08/2021/0002",
            "PEMBUATAN SISTEM SARAN FASTRATA BUANA",
            "Laporan Akhir Di Submit",
            "SS",
            "User Development",
            "00043087",
            "PUSAT",
            "FBPST - Gd Barang Dagang",
            "2021-08-06 17:19:22"))
        approval.add(ApprovalModel(
            "SS-0000/08/2021/0003",
            "PEMBUATAN SISTEM SARAN FASTRATA BUANA",
            "Laporan Akhir Di Submit",
            "SS",
            "User Development",
            "00043087",
            "PUSAT",
            "FBPST - Gd Barang Dagang",
            "2021-08-06 17:19:22"))
        approval.add(ApprovalModel(
            "SS-0000/08/2021/0004",
            "PEMBUATAN SISTEM SARN FASTRATA BUANA",
            "Laporan Akhir Di Submit",
            "SS",
            "User Development",
            "00043087",
            "PUSAT",
            "FBPST - Gd Barang Dagang",
            "2021-08-06 17:19:22"))
        approval.add(ApprovalModel(
            "SS-0000/08/2021/0005",
            "PEMBUATAN SISTEM SARAN FASTRATA BUANA",
            "Laporan Akhir Di Submit",
            "SS",
            "User Development",
            "00043087",
            "PUSAT",
            "FBPST - Gd Barang Dagang",
            "2021-08-06 17:19:22"))

        return approval
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

    fun generateDummyChangePointList(): ArrayList<ChangePointSystemModel>{
        val changepoint = ArrayList<ChangePointSystemModel>()

        changepoint.add(
            ChangePointSystemModel(
            "CP-0001/08/2021/0005",
            "AKTIF",
            "2021-08-06 17:19:22",
        "Reward",
            "PUSAT",
            "FDTB",
            "0"
        ))
        changepoint.add(
            ChangePointSystemModel(
                "CP-0001/08/2021/0005",
                "AKTIF",
                "2021-08-06 17:19:22",
                "Reward",
                "PUSAT",
                "FDTB",
                "0"
            ))
        changepoint.add(
            ChangePointSystemModel(
                "CP-0001/08/2021/0005",
                "AKTIF",
                "2021-08-06 17:19:22",
                "Reward",
                "PUSAT",
                "FDTB",
                "0"
            ))
        changepoint.add(
            ChangePointSystemModel(
                "CP-0001/08/2021/0005",
                "AKTIF",
                "2021-08-06 17:19:22",
                "Reward",
                "PUSAT",
                "FDTB",
                "0"
            ))
        changepoint.add(
            ChangePointSystemModel(
                "CP-0001/08/2021/0005",
                "AKTIF",
                "2021-08-06 17:19:22",
                "Reward",
                "PUSAT",
                "FDTB",
                "0"
            ))
        changepoint.add(
            ChangePointSystemModel(
                "CP-0001/08/2021/0005",
                "AKTIF",
                "2021-08-06 17:19:22",
                "Reward",
                "PUSAT",
                "FDTB",
                "0"
            ))
        changepoint.add(
            ChangePointSystemModel(
                "CP-0001/08/2021/0005",
                "AKTIF",
                "2021-08-06 17:19:22",
                "Reward",
                "PUSAT",
                "FDTB",
                "0"
            ))

        return changepoint
    }

    fun generateDummyProjectImprovementList(): ArrayList<ProjectImprovementModel>{
        val projectimprov = ArrayList<ProjectImprovementModel>()

        projectimprov.add(
            ProjectImprovementModel(
            "PI-0001/08/2021/0005",
            "Improvement",
            "Aktif",
            "Perbaikan",
            "Pusat",
            "FDTB",
            "2021-08-06 17:19:22",
            "John"
        ))
        projectimprov.add(
            ProjectImprovementModel(
                "PI-0001/08/2021/0005",
                "Improvement",
                "Aktif",
                "Perbaikan",
                "Pusat",
                "FDTB",
                "2021-08-06 17:19:22",
                "John"
            ))
        projectimprov.add(
            ProjectImprovementModel(
                "PI-0001/08/2021/0005",
                "Improvement",
                "Aktif",
                "Perbaikan",
                "Pusat",
                "FDTB",
                "2021-08-06 17:19:22",
                "John"
            ))
        projectimprov.add(
            ProjectImprovementModel(
                "PI-0001/08/2021/0005",
                "Improvement",
                "Aktif",
                "Perbaikan",
                "Pusat",
                "FDTB",
                "2021-08-06 17:19:22",
                "John"
            ))
        projectimprov.add(
            ProjectImprovementModel(
                "PI-0001/08/2021/0005",
                "Improvement",
                "Aktif",
                "Perbaikan",
                "Pusat",
                "FDTB",
                "2021-08-06 17:19:22",
                "John"
            ))
        projectimprov.add(
            ProjectImprovementModel(
                "PI-0001/08/2021/0005",
                "Improvement",
                "Aktif",
                "Perbaikan",
                "Pusat",
                "FDTB",
                "2021-08-06 17:19:22",
                "John"
            ))
        projectimprov.add(
            ProjectImprovementModel(
                "PI-0001/08/2021/0005",
                "Improvement",
                "Aktif",
                "Perbaikan",
                "Pusat",
                "FDTB",
                "2021-08-06 17:19:22",
                "John"
            ))
        projectimprov.add(
            ProjectImprovementModel(
                "PI-0001/08/2021/0005",
                "Improvement",
                "Aktif",
                "Perbaikan",
                "Pusat",
                "FDTB",
                "2021-08-06 17:19:22",
                "John"
            ))
        return projectimprov
    }

    fun generateAkarMasalah(): ArrayList<AkarMasalahModel> {
        val akarmslh = ArrayList<AkarMasalahModel>()

        akarmslh.add(
            AkarMasalahModel(
            "satu",
            "1",
            "2",
            "3",
            "4",
            "5",
            "test"
        ))

        akarmslh.add(
            AkarMasalahModel(
                "satu",
                "1",
                "2",
                "3",
                "4",
                "5",
                "test"
            ))

        akarmslh.add(
            AkarMasalahModel(
                "satu",
                "1",
                "2",
                "3",
                "4",
                "5",
                "test"
            ))


        akarmslh.add(
            AkarMasalahModel(
                "satu",
                "1",
                "2",
                "3",
                "4",
                "5",
                "test"
            ))

        akarmslh.add(
            AkarMasalahModel(
                "satu",
                "1",
                "2",
                "3",
                "4",
                "5",
                "test"
            ))
        return akarmslh
    }


}
