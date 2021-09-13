package com.fastrata.eimprovement.utils

import com.fastrata.eimprovement.features.changespoint.data.model.ChangePointRewardItem
import com.fastrata.eimprovement.features.changespoint.data.model.ChangePointModel
import com.fastrata.eimprovement.features.approval.data.model.ApprovalModel
import com.fastrata.eimprovement.features.changespoint.data.model.hadiahItem
import com.fastrata.eimprovement.features.projectimprovement.data.model.AkarMasalahItem
import com.fastrata.eimprovement.features.projectimprovement.data.model.SebabMasalahItem
import com.fastrata.eimprovement.features.projectimprovement.data.model.ProjectImprovementModel
import com.fastrata.eimprovement.features.suggestionsystem.data.model.*

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

    fun generateDummyDetailSuggestionSystem(): SuggestionSystemCreateModel {
        val categorySuggestionItem = ArrayList<CategorySuggestionItem?>()
        categorySuggestionItem.add(CategorySuggestionItem(id = 1, category = "Meningkatkan Penjualan", checked = true))
        categorySuggestionItem.add(CategorySuggestionItem(id = 2, category = "Menurunkan Biaya", checked = true))
        categorySuggestionItem.add(CategorySuggestionItem(id = 0, category = "Efisiensi", checked = true))

        val teamMemberItem = ArrayList<TeamMemberItem?>()
        teamMemberItem.add(TeamMemberItem(name = "budi", department = "ICT", task = "Dokumentasi"))

        val attachmentItem = ArrayList<AttachmentItem?>()
        attachmentItem.add(AttachmentItem(name = "asd.png", uri = "", size = "1Mb"))

        return SuggestionSystemCreateModel(
            ssNo = "SS-0000/08/2021/0041",
            date = "17-08-2021",
            name = "Maman",
            nik = "11210012",
            statusImplementation = StatusImplementation(
                status = "1",
                from = "20-10-2021",
                to = "11-11-2021"
            ),
            title = "Implementasi system dengan robust dan S.O.L.I.D",
            branch = "PUSAT",
            subBranch = "FBPST - Gd Barang Dagang",
            department = "ICT",
            directMgr = "Pak Agung",
            problem = "Code yg terlalu banyak dan berantakan",
            suggestion = "Menggunakan pattern dan membuat pola yang mudah untuk dipelajari",
            categorySuggestion = categorySuggestionItem,
            teamMember = teamMemberItem,
            attachment = attachmentItem

        )
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

    fun generaterewardmodel() : ArrayList<ChangePointRewardItem>{

        val suggestionSystem = ArrayList<ChangePointRewardItem>()

        suggestionSystem.add(
            ChangePointRewardItem(
                1,
            "PULSA 10K",
            "1",
            "Pulsa",

        ))
        suggestionSystem.add(
            ChangePointRewardItem(
                2,
                "PULSA 20K",
                "1",
                "Pulsa",
            ))

        suggestionSystem.add(
            ChangePointRewardItem(
                3,
                "PULSA 10K",
                "1",
                "Pulsa",

                ))
        suggestionSystem.add(
            ChangePointRewardItem(
                4,
                "PULSA 20K",
                "1",
                "Pulsa",
            ))

        suggestionSystem.add(
            ChangePointRewardItem(
                5,
                "PULSA 10K",
                "1",
                "Pulsa",

                ))
        suggestionSystem.add(
            ChangePointRewardItem(
                6,
                "PULSA 20K",
                "1",
                "Pulsa",
            ))
        suggestionSystem.add(
            ChangePointRewardItem(
                7,
                "PULSA 10K",
                "1",
                "Pulsa",

                ))
        suggestionSystem.add(
            ChangePointRewardItem(
                8,
                "PULSA 20K",
                "1",
                "Pulsa",
            ))
        return suggestionSystem
    }

    fun generateDummyChangePointList(): ArrayList<ChangePointModel>{
        val changepoint = ArrayList<ChangePointModel>()

        changepoint.add(
            ChangePointModel(
            "CP-0001/08/2021/0005",
            "AKTIF",
            "2021-08-06 17:19:22",
        "Reward",
            "PUSAT",
            "FDTB",
            "0"
        ))
        changepoint.add(
            ChangePointModel(
                "CP-0001/08/2021/0005",
                "AKTIF",
                "2021-08-06 17:19:22",
                "Reward",
                "PUSAT",
                "FDTB",
                "0"
            ))
        changepoint.add(
            ChangePointModel(
                "CP-0001/08/2021/0005",
                "AKTIF",
                "2021-08-06 17:19:22",
                "Reward",
                "PUSAT",
                "FDTB",
                "0"
            ))
        changepoint.add(
            ChangePointModel(
                "CP-0001/08/2021/0005",
                "AKTIF",
                "2021-08-06 17:19:22",
                "Reward",
                "PUSAT",
                "FDTB",
                "0"
            ))
        changepoint.add(
            ChangePointModel(
                "CP-0001/08/2021/0005",
                "AKTIF",
                "2021-08-06 17:19:22",
                "Reward",
                "PUSAT",
                "FDTB",
                "0"
            ))
        changepoint.add(
            ChangePointModel(
                "CP-0001/08/2021/0005",
                "AKTIF",
                "2021-08-06 17:19:22",
                "Reward",
                "PUSAT",
                "FDTB",
                "0"
            ))
        changepoint.add(
            ChangePointModel(
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

    fun generateSebabMasalah(): ArrayList<SebabMasalahItem> {
        val akarmslh = ArrayList<SebabMasalahItem>()

        akarmslh.add(
            SebabMasalahItem(
            1,
            "1",
            "2",
            "3",
            "4",
            "5",
            "test",
                "Penting"
        ))

        akarmslh.add(
            SebabMasalahItem(
                2,
                "1",
                "2",
                "3",
                "4",
                "5",
                "test","Penting"
            ))

        akarmslh.add(
            SebabMasalahItem(
                3,
                "1",
                "2",
                "3",
                "4",
                "5",
                "test","Penting"
            ))


        akarmslh.add(
            SebabMasalahItem(
                4,
                "1",
                "2",
                "3",
                "4",
                "5",
                "test",
                "Penting"
            ))

        akarmslh.add(
            SebabMasalahItem(
                5,
                "1",
                "2",
                "3",
                "4",
                "5",
                "test",
                "Penting"
            ))
        return akarmslh
    }

    fun generateDummyAkarMasalah(): ArrayList<AkarMasalahItem>{
        val data = ArrayList<AkarMasalahItem>()
        data.add(
            AkarMasalahItem(
                1,
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.")
        )
        data.add(
            AkarMasalahItem(
                2,
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.")
        )
        return data
    }

    fun generateDummyNameMember(): ArrayList<MemberNameItem> {
        val data = ArrayList<MemberNameItem>()
        data.add(MemberNameItem(name = "budi"))
        data.add(MemberNameItem(name = "Jerry"))
        data.add(MemberNameItem(name = "Tom"))
        data.add(MemberNameItem(name = "Ali"))
        data.add(MemberNameItem(name = "Ahmad"))
        return data
    }

    fun generateDummyDepartmentMember(): ArrayList<MemberDepartmentItem> {
        val data = ArrayList<MemberDepartmentItem>()
        data.add(MemberDepartmentItem(department = "ICT"))
        data.add(MemberDepartmentItem(department = "Accounting"))
        data.add(MemberDepartmentItem(department = "HR"))
        data.add(MemberDepartmentItem(department = "Tax"))
        data.add(MemberDepartmentItem(department = "Marketing"))
        return data
    }

    fun generateDummyTaskMember(): ArrayList<MemberTaskItem> {
        val data = ArrayList<MemberTaskItem>()
        data.add(MemberTaskItem(task = "Ketua"))
        data.add(MemberTaskItem(task = "Dokumentasi"))
        data.add(MemberTaskItem(task = "Anggota"))
        return data
    }

    fun generateDummyCategorySuggestion(): ArrayList<CategorySuggestionItem?> {
        val data = ArrayList<CategorySuggestionItem?>()
        data.add(CategorySuggestionItem(
            id = 1, category = "Meningkatkan Penjualan", false
        ))
        data.add(CategorySuggestionItem(
            id = 2, category = "Menurunkan Biaya", false
        ))
        data.add(CategorySuggestionItem(
            id = 3, category = "Mencegah Pelanggaran atau Kecurangan", false
        ))
        data.add(CategorySuggestionItem(
            id = 4, category = "Menyederhanakan Proses Kerja", false
        ))
        return data
    }

    fun generateDummyReward(): ArrayList<hadiahItem> {
        val data = ArrayList<hadiahItem>()
        data.add(hadiahItem(hadiah = "PULSA 10K"))
        data.add(hadiahItem(hadiah = "PULSA 20K"))
        data.add(hadiahItem(hadiah = "PULSA 50K"))
        data.add(hadiahItem(hadiah = "PULSA 100K"))
        data.add(hadiahItem(hadiah = "PULSA 500K"))
        return data
    }
}
