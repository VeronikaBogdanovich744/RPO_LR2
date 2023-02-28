package com.example.lr2_rpo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.lr2_rpo.*
import kotlinx.android.synthetic.main.fragment_rss_item.view.*
import kotlinx.android.synthetic.main.fragment_rss.view.*
import kotlinx.android.synthetic.main.fragment_rss_item.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException
import com.bumptech.glide.Glide;

class RssRecyclerViewAdapter(
    private val mValues: List<RssItemClass>,
    private val mListener: RSSFragment.OnListFragmentInteractionListener?,
    private val context: FragmentActivity?
) : RecyclerView.Adapter<RssRecyclerViewAdapter.RssViewHolder>() {

    class RssViewHolder(view: View) : RecyclerView.ViewHolder(view){
    }

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->

            //val fragement = NewsFragment.newInstance()
                //fragement.setNote(notes)
           // replaceFragment(fragement,true)

            // val item = v.tag as DummyItem
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            // mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RssViewHolder {
        return RssViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_rss_item,parent,false)
        )
    }

    override fun onBindViewHolder(holder: RssViewHolder, position: Int) {
        val item = mValues[position]

        holder.itemView.txtTitle.text = item.title
        holder.itemView.txtPubdate.text = item.pubDate
        holder.itemView.txtContent.text  = item.description
        holder.itemView.txtLink.text = item.link

        holder.itemView.cardView.btnRead.setOnClickListener {
            val fragement = NewsFragment.newInstance()
            fragement.setLink(mValues[position].link)
            replaceFragment(fragement,true)
        }

       //var link = getFeaturedImageLink(item.link)

        if(item.description/*link*/ != "") {
            context?.let {
                holder.itemView.featuredImg?.let { it1 ->
                    Glide.with(it)
                        .load(item.description/*link*/)
                        .centerCrop()
                        .into(it1)
                }
            }
        }else{
            holder.itemView.featuredImg.layoutParams.height = 0
        }


       // holder.itemView.cardView.setOnClickListener(mOnClickListener)
        /*with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }*/
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        //val titleTV: TextView? = mView.findViewById(R.id.txtTitle)
       // val linkTV: TextView? = mView.findViewById(R.id.txtLink)
       // val contentTV: TextView? = mView.findViewById(R.id.txtContent)
       // val pubDateTV: TextView? = mView.findViewById(R.id.txtPubdate)
       // val featuredImg: ImageView? = mView.findViewById(R.id.featuredImg);
    }


    private fun getFeaturedImageLink(htmlText: String): String? {
        var result: String? = null

        val stringBuilder = StringBuilder()
        try {
            val doc: Document = Jsoup.parse(htmlText)
            val imgs: Elements = doc.select("img")

            for (img in imgs) {
                var src = img.attr("src")
                result = src
            }

        } catch (e: IOException) {

        }
        return result

    }

    fun replaceFragment(fragment: Fragment, istransition:Boolean){
        //val act = requireActivity()
        val fragmentTransition= context!!.supportFragmentManager.beginTransaction()
        if (istransition){
            fragmentTransition.setCustomAnimations(android.R.anim.slide_out_right,android.R.anim.slide_in_left)
        }
        fragmentTransition.replace(R.id.frame_layout,fragment).addToBackStack(fragment.javaClass.simpleName).commit()
    }
}
