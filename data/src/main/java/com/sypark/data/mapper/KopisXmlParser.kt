package com.sypark.data.mapper

import com.sypark.domain.model.Content
import org.w3c.dom.Element
import java.io.ByteArrayInputStream
import javax.xml.parsers.DocumentBuilderFactory

object KopisXmlParser {

    fun parsePerformanceList(xml: String): List<Content> =
        elementsOf(xml, "db").map { it.toListContent() }

    fun parsePerformanceDetail(xml: String): Content =
        elementsOf(xml, "db").first().toDetailContent()

    fun parseBoxOffice(xml: String): List<Content> =
        elementsOf(xml, "boxof").map { it.toBoxOfficeContent() }

    private fun elementsOf(xml: String, tagName: String): List<Element> {
        val factory = DocumentBuilderFactory.newInstance()
        val builder = factory.newDocumentBuilder()
        val document = builder.parse(ByteArrayInputStream(xml.toByteArray(Charsets.UTF_8)))
        val nodeList = document.getElementsByTagName(tagName)
        return (0 until nodeList.length).map { nodeList.item(it) as Element }
    }

    private fun Element.text(tag: String): String =
        getElementsByTagName(tag).item(0)?.textContent?.trim().orEmpty()

    private fun Element.toListContent(): Content = Content(
        id = text("mt20id"),
        placeId = "",
        title = text("prfnm"),
        startDate = text("prfpdfrom"),
        endDate = text("prfpdto"),
        theater = text("fcltynm"),
        cast = "", crew = "", runtime = "", age = "", company = "", price = "", story = "",
        poster = text("poster"),
        genre = text("genrenm"),
        state = text("prfstate"),
        openRun = text("openrun") == "Y",
        storyUrls = emptyList(),
        schedule = "",
        rank = 0,
    )

    private fun Element.toDetailContent(): Content {
        val styurlNodes = getElementsByTagName("styurl")
        val storyUrls = (0 until styurlNodes.length).map { styurlNodes.item(it).textContent.trim() }
        return Content(
            id = text("mt20id"),
            placeId = text("mt10id"),
            title = text("prfnm"),
            startDate = text("prfpdfrom"),
            endDate = text("prfpdto"),
            theater = text("fcltynm"),
            cast = text("prfcast"),
            crew = text("prfcrew"),
            runtime = text("prfruntime"),
            age = text("prfage"),
            company = text("entrpsnmH"),
            price = text("pcseguidance"),
            poster = text("poster"),
            story = text("sty"),
            genre = text("genrenm"),
            state = text("prfstate"),
            openRun = text("openrun") == "Y",
            storyUrls = storyUrls,
            schedule = text("dtguidance"),
            rank = 0,
        )
    }

    private fun Element.toBoxOfficeContent(): Content = Content(
        id = text("mt20id"),
        placeId = text("mt10id"),
        title = text("prfnm"),
        startDate = text("prfpdfrom"),
        endDate = text("prfpdto"),
        theater = "", cast = "", crew = "", runtime = "", company = "", price = "", story = "",
        age = text("prfage"),
        poster = text("poster"),
        genre = text("genrenm"),
        state = "",
        openRun = false,
        storyUrls = emptyList(),
        schedule = "",
        rank = text("rnum").toLongOrNull() ?: 0,
    )
}
