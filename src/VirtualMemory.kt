@file: JvmName("VirtualMemory")

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.InvalidArgumentException
import org.knowm.xchart.SwingWrapper
import org.knowm.xchart.XYChart
import org.knowm.xchart.XYChartBuilder
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle
import org.knowm.xchart.style.Styler.LegendPosition

/**
 * Adds series to [chart] with name [seriesName].
 * Series x-data is number of input line(consecutive numbers from 0),
 * y-data is amount of times non -1 element is present in those lines.
 */
fun addSeriesToChart(chart: XYChart, seriesName: String, values: List<Int>) {
    val result = ArrayList<Int>(listOf(0))
    for (element in values) {
        result.add(result.last() + if (element != -1) 1 else 0)
    }
    chart.addSeries(
        seriesName,
        List(values.size + 1) { it },
        result,
    )
}

/**
 * draws plot as described in README.md from data in the format described in DOC.md(output format)
 */
fun plot(values: List<Triple<Int, Int, Int>>) {
    val chart = XYChartBuilder()
        .width(600)
        .height(400)
        .title("Virtual memory efficiency chart(lower is better)")
        .build()

    chart.styler.legendPosition = LegendPosition.InsideNW
    chart.styler.defaultSeriesRenderStyle = XYSeriesRenderStyle.Area
    chart.styler.setAxisTitlesVisible(false)

    addSeriesToChart(chart, "FIFO", List(values.size) { values[it].first })
    addSeriesToChart(chart, "LRU", List(values.size) { values[it].second })
    addSeriesToChart(chart, "OPT", List(values.size) { values[it].third })

    SwingWrapper(chart).setTitle("Virtual memory types comparison").displayChart()
}

fun main(args: Array<String>) {
    ArgParser(args).parseInto(::ArgumentsParser).run {
        if (tests.isEmpty() && input.isEmpty()) {
            throw InvalidArgumentException("No input values present. Please refer to --help")
        }
        val pagesQueue = if (tests.isEmpty()) input else tests

        val fifoMemory = FIFOMemory(RAMPages, totalPages)
        val lruMemory = LRUMemory(RAMPages, totalPages)
        val optMemory = OPTMemory(RAMPages, totalPages)
        optMemory.constructFutureQueries(pagesQueue)

        val result = pagesQueue.map { Triple(fifoMemory.getPage(it), lruMemory.getPage(it), optMemory.getPage(it)) }

        result.forEach { println(it) }
        output.flush()
        output.close()

        if (plot) {
            plot(result)
        }
    }
}