package com.example.lr2_rpo

import kotlinx.android.synthetic.main.fragment_rss_item.*
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.InputStream

class RssParser {
    private val rssItems = ArrayList<RssItemClass>()
    private var rssItem_ : RssItemClass ?= null
    private var text: String? = null

    fun parse(inputStream: InputStream):List<RssItemClass> {
        try {
            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true
            val parser = factory.newPullParser()
            parser.setInput(inputStream, null)
            var eventType = parser.eventType
            var foundItem = false
            while (eventType != XmlPullParser.END_DOCUMENT) {
                val tagname = parser.name
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        if (tagname.equals("item", ignoreCase = true)) {
                            // create a new instance of employee
                            foundItem = true
                            rssItem_ = RssItemClass()
                        }
                        if (tagname.equals("enclosure", ignoreCase = true) && foundItem==true) {
                            rssItem_!!.description = parser.getAttributeValue("", "url")
                            //rssItem_!!.description = parser.getAttributeValue(3)
                            //foundItem = false
                            // add employee object to list
                            //rssItem_?.let { rssItems.add(it) }
                            //foundItem = true
                           // foundItem = false
                        }
                    }
                    XmlPullParser.TEXT -> text = parser.text
                    XmlPullParser.END_TAG -> if (tagname.equals("item", ignoreCase = true)) {
                        // add employee object to list
                        rssItem_?.let { rssItems.add(it) }
                        foundItem = false
                    }
                   /* XmlPullParser.START_TAG -> if(tagname.equals("enclosure", ignoreCase = true)) {
                        // create a new instance of employee
                        foundItem = true
                        rssItem_ = RssItemClass()
                    }*/
                    else if ( foundItem && tagname.equals("title", ignoreCase = true)) {
                        rssItem_!!.title = text.toString()
                       //rssItem!!.txtTitle.text = text.toString()
                    } else if (foundItem && tagname.equals("link", ignoreCase = true)) {
                        rssItem_!!.link= text.toString()
                    } else if (foundItem && tagname.equals(/*"description"*/"description", ignoreCase = true)) {
                        //rssItem!!.txtContent.text= text.toString()
                        rssItem_!!.description = text.toString()
                    } else if (foundItem && tagname.equals("pubDate", ignoreCase = true)) {
                        rssItem_!!.pubDate = text.toString()
                    }
                }

                eventType = parser.next()

            }

        } catch (e: XmlPullParserException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return rssItems
    }
}