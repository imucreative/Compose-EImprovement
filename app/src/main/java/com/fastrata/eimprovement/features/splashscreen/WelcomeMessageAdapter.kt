package com.fastrata.eimprovement.features.splashscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.fastrata.eimprovement.R

class WelcomeMessageAdapter(var layoutInflater: LayoutInflater): PagerAdapter() {

    val aboutTitleArray = arrayOf(
        "Ready to Travel",
        "Pick the Ticket",
        "Flight to Destination",
        "Enjoy Holiday"
    )

    private val aboutDescriptionArray = arrayOf(
        "Choose your destination, plan Your trip. Pick the best place for Your holiday",
        "Select the day, pick Your ticket. We give you the best prices. We guarantee!",
        "Safe and Comfort flight is our priority. Professional crew and services.",
        "Enjoy your holiday, Don't forget to feel the moment and take a photo!"
    )

    private val aboutImagesArray = intArrayOf(
        R.drawable.img_wizard_1,
        R.drawable.img_wizard_2,
        R.drawable.img_wizard_3,
        R.drawable.img_wizard_4
    )

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = LayoutInflater.from(container.context)

        val view: View = layoutInflater.inflate(R.layout.item_card_wizard, container, false)
        (view.findViewById(R.id.title) as TextView).text = aboutTitleArray[position]
        (view.findViewById(R.id.description) as TextView).text = aboutDescriptionArray[position]
        (view.findViewById(R.id.image) as ImageView).setImageResource(
            aboutImagesArray[position]
        )

        container.addView(view)
        return view
    }

    override fun getCount(): Int {
        return aboutTitleArray.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view === obj
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        val view = obj as View
        container.removeView(view)
    }
}
