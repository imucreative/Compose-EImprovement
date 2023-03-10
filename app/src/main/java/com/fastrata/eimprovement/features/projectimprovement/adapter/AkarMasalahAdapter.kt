package com.fastrata.eimprovement.features.projectimprovement.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fastrata.eimprovement.databinding.ItemAkarMasalahPiBinding
import com.fastrata.eimprovement.features.projectimprovement.callback.AkarMasalahCallback
import com.fastrata.eimprovement.features.projectimprovement.data.model.AkarMasalahModel
import com.fastrata.eimprovement.utils.APPROVE
import com.fastrata.eimprovement.utils.DETAIL
import java.util.*
import kotlin.collections.ArrayList

class AkarMasalahAdapter(action: String?, statusProposalId: Int?, val clickedItemListener: (akarMasalahModel: AkarMasalahModel, index: Int) -> Unit) : RecyclerView.Adapter<AkarMasalahAdapter.AkarMasalahViewHolder>() {

    private var list = ArrayList<AkarMasalahModel?>()
    private var timer: Timer? = null
    private var action: String? = action
    private var statusProposalId: Int? = statusProposalId

    fun setList(data : ArrayList<AkarMasalahModel?>){
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    private lateinit var akarmslhcallback : AkarMasalahCallback
    fun setAkarMslhCallback(AkarMasalahCallback: AkarMasalahCallback){
        this.akarmslhcallback = AkarMasalahCallback
    }

    inner class AkarMasalahViewHolder(private val binding: ItemAkarMasalahPiBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: AkarMasalahModel){
            binding.root.setOnClickListener {
                akarmslhcallback.onItemClicked(data)
            }

            binding.apply {
                whyTerakhir.setText(data.kenapa)
                imprvementDilakukan.setText(data.aksi)
                detilLangkah.setText(data.detail_langkah)

                if ((action == APPROVE) || (action == DETAIL)) {
                    whyTerakhir.isEnabled = false
                    imprvementDilakukan.isEnabled = false
                    detilLangkah.isEnabled = false
                } else {
                    when (statusProposalId) {
                         6, 7, 9 -> {
                            whyTerakhir.isEnabled = false
                            imprvementDilakukan.isEnabled = false
                            detilLangkah.isEnabled = false
                        }
                    }
                }

                whyTerakhir.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        afterTextChangeFunc(binding, bindingAdapterPosition)
                    }

                    override fun beforeTextChanged(
                        s: CharSequence?, start: Int, count: Int, after: Int
                    ) {}

                    override fun onTextChanged(
                        s: CharSequence?, start: Int, before: Int, count: Int
                    ) {
                        timer?.cancel()
                    }
                })

                imprvementDilakukan.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        afterTextChangeFunc(binding, bindingAdapterPosition)
                    }

                    override fun beforeTextChanged(
                        s: CharSequence?, start: Int, count: Int, after: Int
                    ) {}

                    override fun onTextChanged(
                        s: CharSequence?, start: Int, before: Int, count: Int
                    ) {
                        timer?.cancel()
                    }
                })

                detilLangkah.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        afterTextChangeFunc(binding, bindingAdapterPosition)
                    }

                    override fun beforeTextChanged(
                        s: CharSequence?, start: Int, count: Int, after: Int
                    ) {}

                    override fun onTextChanged(
                        s: CharSequence?, start: Int, before: Int, count: Int
                    ) {
                        timer?.cancel()
                    }
                })
            }
        }
    }

    private fun afterTextChangeFunc(binding: ItemAkarMasalahPiBinding, index: Int) {
        binding.apply {
            timer = Timer()
            timer!!.schedule(object : TimerTask() {
                override fun run() {
                    val akarMasalahItem = AkarMasalahModel(
                        index+1,
                        whyTerakhir.text.toString(),
                        imprvementDilakukan.text.toString(),
                        detilLangkah.text.toString()
                    )

                    clickedItemListener(akarMasalahItem, index)
                }
            }, 300)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AkarMasalahViewHolder{
        val items = ItemAkarMasalahPiBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AkarMasalahViewHolder(items)
    }

    override fun onBindViewHolder(holder: AkarMasalahAdapter.AkarMasalahViewHolder, position: Int){
        list[position]?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}