package at.neon.gui.panes.main

import at.neon.gui.controls.neonwebview.NeonWebView
import at.neon.gui.model.ContestProblem
import at.neon.gui.util.FXMLUtil
import javafx.fxml.FXML
import javafx.scene.control.TableView
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.layout.BorderPane

class MainPane<DATA> : BorderPane() {
    @FXML
    lateinit var text: TextArea

    @FXML
    lateinit var web: NeonWebView<MainWebViewBridge>

    @FXML
    lateinit var table: TableView<DATA>

    @FXML
    lateinit var webCommandText: TextField

    @FXML
    lateinit var webCommandResponse: TextArea

    init {
        FXMLUtil.load(this, MainPane::class.java)
    }

    fun init(contestProblem: ContestProblem<MainWebViewBridge, DATA>) {
        val html = MainPane::class.java.getResource(MainPane::class.java.simpleName + ".html")
        val bridge = MainWebViewBridge()
        web.init(html, bridge) {
            contestProblem.configureText(text)
            contestProblem.configureWebBridge(this)
            contestProblem.configureTable(table)
            contestProblem.solve()
        }
    }

    fun submitWebCommand() {
        try {
            webCommandResponse.text = web.bridge.executeScript(webCommandText.text).toString()
        } catch (ex: Exception) {
            ex.printStackTrace()
            webCommandResponse.text = "Error!\n$ex"
        }
    }
}
