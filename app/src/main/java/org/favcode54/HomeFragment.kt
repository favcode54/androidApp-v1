package org.favcode54.favcode54


import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.v4.content.res.ResourcesCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_home.*
import org.favcode54.favcode54.base.BaseFragment
import org.favcode54.favcode54.network.GlideApp


class HomeFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applyFont(view as ViewGroup)
        loadImages()
        setDrawableTops()
    }

    private fun loadImages() {
        loadImage(R.drawable.learn, learnBgImg)
        loadImage(R.drawable.score_sheet, scoreSheetImg)
        loadImage(R.drawable.projects, projectsBgImg)
        loadImage(R.drawable.get_jobs, getJobsBgImg)
    }

    private fun loadImage(drawableRes: Int, imageView: ImageView) {
        GlideApp.with(this)
                .load(drawableRes)
                .centerCrop()
                .into(imageView)
    }

    private fun setDrawableTops() {
        setDrawableTop(learnText, R.drawable.ic_learn)
        setDrawableTop(scoreSheetText, R.drawable.ic_sheet)
        setDrawableTop(projectsText, R.drawable.ic_projects)
        setDrawableTop(getJobsText, R.drawable.ic_work)
    }

    private fun setDrawableTop(textView: TextView, @DrawableRes drawableRes: Int) {
        val drawableCompat = ResourcesCompat.getDrawable(resources, drawableRes, activity!!.theme)
        textView.setCompoundDrawablesWithIntrinsicBounds(null, drawableCompat, null, null)
    }

    companion object {
        fun newInstance(): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

}
