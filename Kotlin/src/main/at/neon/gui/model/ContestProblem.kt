package at.neon.gui.model

import at.neon.gui.controls.neonwebview.NeonWebViewBridge
import javafx.scene.control.TableView
import javafx.scene.control.TextArea
import java.util.*

interface ContestProblem<WEB_BRIDGE : NeonWebViewBridge, DATA> {
    fun read(absoluteResourcePath : String)
    fun configureWebBridge(bridge: WEB_BRIDGE)
    fun configureTable(table: TableView<DATA>)
    fun configureText(textArea: TextArea)
    fun solve()
}