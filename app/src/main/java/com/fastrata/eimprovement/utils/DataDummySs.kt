package com.fastrata.eimprovement.utils

import com.fastrata.eimprovement.featuresglobal.data.model.*
import com.fastrata.eimprovement.features.approval.data.model.ApprovalHistoryStatusModel
import com.fastrata.eimprovement.features.approval.data.model.ApprovalModel
import com.fastrata.eimprovement.features.changespoint.data.model.*
import com.fastrata.eimprovement.features.projectimprovement.data.model.*
import com.fastrata.eimprovement.features.settings.ui.mutasi.data.model.MutasiModel
import com.fastrata.eimprovement.features.suggestionsystem.data.model.*

object DataDummySs {
    fun generateDummyDetailSuggestionSystem(): SuggestionSystemCreateModel {
        val categorySuggestionItem = ArrayList<CategoryImprovementItem?>()
        categorySuggestionItem.add(CategoryImprovementItem(id = 6, category = "Meningkatkan Penjualan", checked = true))
        categorySuggestionItem.add(CategoryImprovementItem(id = 8, category = "Menurunkan Biaya", checked = true))
        categorySuggestionItem.add(CategoryImprovementItem(id = 0, category = "Efisiensi", checked = true))

        val memberNameItem = MemberNameItem(id = 1, name = "budi")
        val memberDepartmentItem = MemberDepartmentItem(id = 1, department = "ICT")
        val memberTaskItem = MemberTaskItem(id = 1, task = "Dokumentasi")

        val teamMemberItem = ArrayList<TeamMemberItem?>()
        teamMemberItem.add(TeamMemberItem(name = memberNameItem, department = memberDepartmentItem, task = memberTaskItem))

        val attachmentItem = ArrayList<AttachmentItem?>()
        attachmentItem.add(AttachmentItem(name = "asd.png", uri = "", size = "1Mb"))

        return SuggestionSystemCreateModel(
            ssNo = "SS-0000/08/2021/0041",
            date = "17-08-2021",
            name = "Maman",
            nik = "11210012",
            statusImplementation = StatusImplementation(
                status = 1,
                from = "20-10-2021",
                to = "11-11-2021"
            ),
            title = "Implementasi system dengan robust dan S.O.L.I.D",
            branchCode = "0000",
            branch = "PUSAT",
            subBranch = "FBPST - Gd Barang Dagang",
            department = "ICT",
            directMgr = "Pak Agung",
            problem = "Code yg terlalu banyak dan berantakan",
            suggestion = "Menggunakan pattern dan membuat pola yang mudah untuk dipelajari",
            categoryImprovement = categorySuggestionItem,
            teamMember = teamMemberItem,
            attachment = attachmentItem,
            statusProposal = StatusProposalItem(
                id = 1,
                status = "Proposal Dibuat"
            ),
            headId = 30,
            userId = 5,
            orgId = 84,
            warehouseId = 89
        )
    }

    fun generateDummyDetailChangePoint(): ChangePointCreateItemModel{
       val rewardArray = ArrayList<RewardItem?>()
       rewardArray.add(RewardItem(hadiahId = 1,hadiah = "PULSA 100K",nilai = 10000,keterangan = "Penukaran"))

       return ChangePointCreateItemModel(
          id = 1,
          saldo = 2000,
          cpNo = "CP-0000/08/2021/0100",
          name = "TEST",
          nik = "101010",
          branch = "PUSAT",
          subBranch = "FBPST",
          department = "ICT",
          position = "STAFF",
          date = "2021-08-06",
          description = "Test Data",
          reward = rewardArray
       )
   }

    fun generateDummyDetailProjectImprovementList(): ProjectImprovementCreateModel {
        val categorySuggestionItem = ArrayList<CategoryImprovementItem?>()
        categorySuggestionItem.add(CategoryImprovementItem(id = 6, category = "Meningkatkan Penjualan", checked = true))
        categorySuggestionItem.add(CategoryImprovementItem(id = 8, category = "Menurunkan Biaya", checked = true))
        categorySuggestionItem.add(CategoryImprovementItem(id = 0, category = "Efisiensi", checked = true))

        val memberNameItem = MemberNameItem(id = 1, name = "budi")
        val memberDepartmentItem = MemberDepartmentItem(id = 1, department = "ICT")
        val memberTaskItem = MemberTaskItem(id = 1, task = "Dokumentasi")

        val teamMemberItem = ArrayList<TeamMemberItem?>()
        teamMemberItem.add(TeamMemberItem(name = memberNameItem, department = memberDepartmentItem, task = memberTaskItem))

        val attachmentItem = ArrayList<AttachmentItem?>()
        attachmentItem.add(AttachmentItem(name = "asd.png", uri = "", size = "1Mb"))

        val sudah = StatusImplementationPiDoneModel(
            from = "2020-01-21",
            to = "2020-02-21"
        )

        val problem = ArrayList<SebabMasalahModel?>()
        problem.add(SebabMasalahModel(
            penyebab = "karena",
            w1 = "xxx", w2 = "", w3 = "", w4 = "", w5 = "",
            prioritas = "test"
        ))

        val akarMasalah = ArrayList<AkarMasalahModel?>()
        akarMasalah.add(AkarMasalahModel(
            sequence = 1,
            kenapa = "test",
            aksi = "aksi",
            detail_langkah = "test"
        ))

        val estimasi = NqiEstimasiModel(
            benefit = 1000, benefit_keterangan = "testter",
            cost = 500, cost_keterangan = "tested",
            nqi = 1500
        )

        val aktual = NqiAktualModel(
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
            date = "17-08-2021",
            branchCode = "0000",
            branch = "PUSAT",
            subBranch = "FBPST",
            title = "Implementasi system dengan robust dan S.O.L.I.D",
            statusImplementationModel = StatusImplementationPiModel(
                sudah = sudah,
                akan = null
            ),
            identification = "identification",
            target = "testing target",
            sebabMasalah = problem,
            akarMasalah = akarMasalah,
            nilaiOutput = "Menggunakan pattern dan membuat pola yang mudah untuk dipelajari",
            nqiModel = nqi,
            teamMember = teamMemberItem,
            categoryFixing = categorySuggestionItem,
            implementationResult = "implementasi result",
            attachment = attachmentItem,
            statusProposal = StatusProposalItem(
                id = 7,
                status = "Laporan Akhir Di Submit"
            ),
            headId = 30,
            userId = 5,
            orgId = 84,
            warehouseId = 89,
            nik = "devl"
        )
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
