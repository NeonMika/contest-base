package at.neon.gui.model

import at.neon.gui.panes.main.MainWebViewBridge
import javafx.beans.property.ReadOnlyIntegerWrapper
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.TextArea
import javafx.scene.control.cell.PropertyValueFactory
import javafx.util.Callback
import java.util.*

class MyContestData(val x: Int, val y: Int) {
}

class MyContestProblem : ContestProblem<MainWebViewBridge, MyContestData> {
    val lines = mutableListOf<String>()
    val data = mutableListOf<MyContestData>()
    val data2D = listOf(listOf(1, 0, 1), listOf(1, 1, 0), listOf(0, 0, 1))
    lateinit var appendToText : (String) -> Unit

    override fun read(absoluteResourcePath: String) {
        var scanner = Scanner(javaClass.getResourceAsStream(absoluteResourcePath))
        while (scanner.hasNextLine()) {
            lines += scanner.nextLine()
        }
        scanner.close()
        scanner = Scanner(javaClass.getResourceAsStream(absoluteResourcePath))
        while (scanner.hasNext()) {
            data += MyContestData(scanner.nextInt(), scanner.nextInt())
        }
        scanner.close()
    }

    override fun configureWebBridge(bridge: MainWebViewBridge) {
        bridge.setData(data)
        bridge.setData2D(data2D)
    }

    override fun configureTable(table: TableView<MyContestData>) {
        val xCol = TableColumn<MyContestData, Number>("x").apply {
            cellValueFactory = PropertyValueFactory<MyContestData, Number>("x")
        }
        val yCol = TableColumn<MyContestData, Number>("y").apply {
            cellValueFactory = Callback { param -> ReadOnlyIntegerWrapper(param!!.value.y) }
        }

        table.columns.clear()
        table.columns += xCol
        table.columns += yCol
        table.items.clear()
        table.items.addAll(data)
    }

    override fun configureText(textArea: TextArea) {
        textArea.text = buildString {
            appendln("Input:")
            appendln(lines.joinToString(separator = "\n"))
        }
        appendToText = { s -> textArea.text = textArea.text + s}
    }

    override fun solve() {
        // TODO
        appendToText("\n")
        appendToText("TODO")
    }
}