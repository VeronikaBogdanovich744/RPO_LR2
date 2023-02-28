package com.example.lr2_rpo

import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lr2_rpo.adapter.RssRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_rss_item.*
import java.io.IOException
import java.io.InputStream
import java.lang.ref.WeakReference
import java.net.HttpURLConnection
import java.net.URL


class RSSFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
          //  param1 = it.getString(ARG_PARAM1)
           // param2 = it.getString(ARG_PARAM2)
        }
    }

  /*  override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rss, container, false)
    }*/

    companion object {

        @JvmStatic
        fun newInstance() =
            RSSFragment().apply {
                arguments = Bundle().apply {
                  //  putString(ARG_PARAM1, param1)
                  //  putString(ARG_PARAM2, param2)
                }
            }
    }

    // TODO: Customize parameters
    private var columnCount = 1

    private var listener: OnListFragmentInteractionListener? = null

    val RSS_FEED_LINK = "https://ria.ru/export/rss2/archive/index.xml";//"https://proweb63.ru/feed.xml";

    var adapter: RssRecyclerViewAdapter? = null
    var rssItems = ArrayList<RssItemClass>()

    var listV : RecyclerView?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_rss, container, false)

        listV = view.findViewById(R.id.listV)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = RssRecyclerViewAdapter(rssItems, listener,activity)

        listV?.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        listV?.adapter = adapter
        val url = URL(RSS_FEED_LINK)
        RssFeedFetcher(this).execute(url)
    }

   /* override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = RssRecyclerViewAdapter(rssItems, listener,activity)
        listV?.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        listV?.adapter = adapter

        val url = URL(RSS_FEED_LINK)
        RssFeedFetcher(this).execute(url)

    }*/

    fun updateRV(rssItemsL: List<RssItemClass>) {
        if (rssItemsL != null && !rssItemsL.isEmpty()) {
            rssItems.addAll(rssItemsL)
            adapter?.notifyDataSetChanged()
        }
    }


    class RssFeedFetcher(val context: RSSFragment) : AsyncTask<URL, Void, List<RssItemClass>>() {
        val reference = WeakReference(context)
        private var stream: InputStream? = null;

        override fun doInBackground(vararg params: URL?): List<RssItemClass>? {
            val connect = params[0]?.openConnection() as HttpURLConnection
            connect.readTimeout = 8000
            connect.connectTimeout = 8000
            connect.requestMethod = "GET"
            connect.connect();

            val responseCode: Int = connect.responseCode;
            var rssItems: List<RssItemClass>? = null
            if (responseCode == 200) {
                stream = connect.inputStream;


                try {
                    val parser = RssParser()
                    rssItems = parser.parse(stream!!)

                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            return rssItems
        }

        override fun onPostExecute(result: List<RssItemClass>?) {
            super.onPostExecute(result)
            if (result != null && !result.isEmpty()) {
                reference.get()?.updateRV(result)
            }

        }

    }

    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: RssItemClass?)
    }

}