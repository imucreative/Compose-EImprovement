package com.fastrata.eimprovement.utils

import com.fastrata.eimprovement.features.approval.data.model.ApprovalHistoryStatusModel
import com.fastrata.eimprovement.features.changespoint.data.model.RewardItem
import com.fastrata.eimprovement.features.changespoint.data.model.ChangePointModel
import com.fastrata.eimprovement.features.approval.data.model.ApprovalModel
import com.fastrata.eimprovement.features.changespoint.data.model.hadiahItem
import com.fastrata.eimprovement.features.projectimprovement.data.model.*
import com.fastrata.eimprovement.features.suggestionsystem.data.model.*
import com.fastrata.eimprovement.ui.model.*

object DataDummySs {
    fun generateDummySuggestionSystem(): ArrayList<SuggestionSystemModel> {

        val suggestionSystem = ArrayList<SuggestionSystemModel>()

        suggestionSystem.add(SuggestionSystemModel(
            "SS-0000/08/2021/0041",
            "17-08-2021",
            "Membuat ide baru",
            StatusProposal(
                6, "Implementasi Project"
            ),
            "test",
            "PUSAT",
            "FBPST - Gd Barang Dagang"))
        suggestionSystem.add(SuggestionSystemModel(
            "SS-0000/08/2021/0042",
            "17-08-2021",
            "Membuat ide baru new",
            StatusProposal(
                3, "Proposal Dalam Pengecekan"
            ),
            "test",
            "PUSAT",
            "FBPST - Gd Barang Dagang"))
        suggestionSystem.add(SuggestionSystemModel(
            "SS-0000/08/2021/0043",
            "17-08-2021",
            "Membuat ide baru new",
            StatusProposal(
                1, "Proposal Dibuat"
            ),
            "test",
            "PUSAT",
            "FBPST - Gd Barang Dagang"))
        suggestionSystem.add(SuggestionSystemModel(
            "SS-0000/08/2021/0044",
            "17-08-2021",
            "Membuat ide baru new",
            StatusProposal(
                10, "Project Valid"
            ),
            "test",
            "PUSAT",
            "FBPST - Gd Barang Dagang"))
        suggestionSystem.add(SuggestionSystemModel(
            "SS-0000/08/2021/0045",
            "17-08-2021",
            "Membuat ide baru new",
            StatusProposal(
                2, "Proposal Disubmit"
            ),
            "test",
            "PUSAT",
            "FBPST - Gd Barang Dagang"))
        suggestionSystem.add(SuggestionSystemModel(
            "SS-0000/08/2021/0046",
            "17-08-2021",
            "Membuat ide baru new",
            StatusProposal(
                6, "Implementasi Project"
            ),
            "test",
            "PUSAT",
            "FBPST - Gd Barang Dagang"))

        return suggestionSystem
    }

    fun generateDummyDetailSuggestionSystem(): SuggestionSystemCreateModel {
        val categorySuggestionItem = ArrayList<CategoryImprovementItem?>()
        categorySuggestionItem.add(CategoryImprovementItem(id = 1, category = "Meningkatkan Penjualan", checked = true))
        categorySuggestionItem.add(CategoryImprovementItem(id = 2, category = "Menurunkan Biaya", checked = true))
        categorySuggestionItem.add(CategoryImprovementItem(id = 0, category = "Efisiensi", checked = true))

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
            categoryImprovement = categorySuggestionItem,
            teamMember = teamMemberItem,
            attachment = attachmentItem,
            statusProposal = StatusProposal(
                id = 1,
                status = "Proposal Dibuat"
            )
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
            "PI-0000/08/2021/0100",
            "PEMBUATAN SISTEM SARAN FASTRATA BUANA PI",
            "Laporan Akhir Di Submit",
            "PI",
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
            "CP-0001/05/2021/0010",
            "PEMBUATAN SISTEM SARAN FASTRATA BUANA CP",
            "Laporan Akhir Di Submit",
            "CP",
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
                StatusProposal(
                    6, "Implementasi Project"
                ),
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
                StatusProposal(
                    10, "Project Valid"
                ),
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
                StatusProposal(
                    6, "Implementasi Project"
                ),
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
                StatusProposal(
                    3, "Proposal Dalam Pengecekan"
                ),
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
                StatusProposal(
                    6, "Implementasi Project"
                ),
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
                StatusProposal(
                    6, "Implementasi Project"
                ),
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
                StatusProposal(
                    6, "Implementasi Project"
                ),
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
                StatusProposal(
                    6, "Implementasi Project"
                ),
                "Aktif",
                "Perbaikan",
                "Pusat",
                "FDTB",
                "2021-08-06 17:19:22",
                "John"
            ))
        return projectimprov
    }

    fun generateDummyDetailProjectImprovementList(): ProjectImprovementCreateModel {
        val categorySuggestionItem = ArrayList<CategoryImprovementItem?>()
        categorySuggestionItem.add(CategoryImprovementItem(id = 1, category = "Meningkatkan Penjualan", checked = true))
        categorySuggestionItem.add(CategoryImprovementItem(id = 2, category = "Menurunkan Biaya", checked = true))
        categorySuggestionItem.add(CategoryImprovementItem(id = 0, category = "Efisiensi", checked = true))

        val teamMemberItem = ArrayList<TeamMemberItem?>()
        teamMemberItem.add(TeamMemberItem(name = "budi", department = "ICT", task = "Dokumentasi"))

        val attachmentItem = ArrayList<AttachmentItem?>()
        attachmentItem.add(AttachmentItem(name = "asd.png", uri = "", size = "1Mb"))

        val sudah = Sudah(
            from = "2020-01-21",
            to = "2020-02-21"
        )

        val problem = ArrayList<SebabMasalahItem?>()
        problem.add(SebabMasalahItem(
            penyebab = "karena",
            w1 = "xxx", w2 = "", w3 = "", w4 = "", w5 = "",
            prioritas = "test"
        ))

        val akarMasalah = ArrayList<AkarMasalahItem?>()
        akarMasalah.add(AkarMasalahItem(
            sequence = 1,
            kenapa = "test",
            aksi = "aksi",
            detail_langkah = "test"
        ))

        val estimasi = Estimasi(
            benefit = 1000, benefit_keterangan = "testter",
            cost = 500, cost_keterangan = "tested",
            nqi = 1500
        )

        val aktual = Aktual(
            benefit = 2000, benefit_keterangan = "terrr",
            cost = 1000, cost_keterangan = "testerre",
            nqi = 3000
        )

        val nqi = NqiModel(estimasi, aktual)

        return ProjectImprovementCreateModel(
            id = 10,
            piNo = "PI-0001/08/2021/0005",
            department = "ICT",
            years = "2021",
            createdDate = "17-08-2021",
            branch = "PUSAT",
            subBranch = "FBPST - Gd Barang Dagang",
            title = "Implementasi system dengan robust dan S.O.L.I.D",
            statusImplementation = StatusImplementationPi(
                sudah = sudah,
                akan = null
            ),
            identification = "identification",
            setTarget = "testing target",
            problem = problem,
            akarMasalah = akarMasalah,
            outputValue = "Menggunakan pattern dan membuat pola yang mudah untuk dipelajari",
            nqi = nqi,
            teamMember = teamMemberItem,
            categoryFixing = categorySuggestionItem,
            implementationResult = "implementasi result",
            attachment = attachmentItem,
            statusProposal = StatusProposal(
                id = 7,
                status = "Laporan Akhir Di Submit"
            )
        )
    }

    fun generateSebabMasalah(): ArrayList<SebabMasalahItem> {
        val akarMslh = ArrayList<SebabMasalahItem>()

        akarMslh.add(
            SebabMasalahItem(
            "input masih manual",
            "belum ada sistem", "", "", "", "",
                "dibuatkan sistem"
        ))
        akarMslh.add(SebabMasalahItem(
            "input masih manual",
            "belum ada sistem", "", "", "", "",
            "dibuatkan sistem"
        ))
        akarMslh.add(SebabMasalahItem(
            "input masih manual",
            "belum ada sistem", "", "", "", "",
            "dibuatkan sistem"
        ))
        akarMslh.add(SebabMasalahItem(
            "input masih manual",
            "belum ada sistem", "", "", "", "",
            "dibuatkan sistem"
        ))
        akarMslh.add(SebabMasalahItem(
            "input masih manual",
            "belum ada sistem", "", "", "", "",
            "dibuatkan sistem"
        ))
        return akarMslh
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

    fun generateDummyCategorySuggestion(): ArrayList<CategoryImprovementItem?> {
        val data = ArrayList<CategoryImprovementItem?>()
        data.add(CategoryImprovementItem(
            id = 1, category = "Meningkatkan Penjualan", false
        ))
        data.add(CategoryImprovementItem(
            id = 2, category = "Menurunkan Biaya", false
        ))
        data.add(CategoryImprovementItem(
            id = 3, category = "Mencegah Pelanggaran atau Kecurangan", false
        ))
        data.add(CategoryImprovementItem(
            id = 4, category = "Menyederhanakan Proses Kerja", false
        ))
        return data
    }

    fun generateDummyReward(): ArrayList<hadiahItem> {
        val data = ArrayList<hadiahItem>()
        data.add(hadiahItem(hadiah = "PULSA 50K",1,"1000"))
        data.add(hadiahItem(hadiah = "PULSA 100K",2,"5000"))
        data.add(hadiahItem(hadiah = "IPHONE 13",3,"10000"))
        return data
    }

    fun generateDummyApprovalHistoryStatus(): ArrayList<ApprovalHistoryStatusModel> {
        val data = ArrayList<ApprovalHistoryStatusModel>()
        data.add(ApprovalHistoryStatusModel(
            pic = "Tyas Febriatmoko", status = "Proposal Disubmit", comment = "Proposal Disubmit", date = "2021-08-31 09:39:26"
        ))
        data.add(ApprovalHistoryStatusModel(
            pic = "Tyas Febriatmoko", status = "Proposal Dalam Pengecekan", comment = "Proposal Dalam Pengecekan", date = "2021-08-31 09:39:52"
        ))
        data.add(ApprovalHistoryStatusModel(
            pic = "Tyas Febriatmoko", status = "Proposal Disetujui", comment = "mantap", date = "2021-08-31 09:40:03"
        ))
        data.add(ApprovalHistoryStatusModel(
            pic = "Tyas Febriatmoko", status = "Implementasi Project", comment = "Implementasi Project", date = "2021-08-31 09:40:39"
        ))
        return data
    }
}
