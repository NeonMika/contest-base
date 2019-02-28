package at.neon.gui.main

import at.neon.gui.model.MyContestData
import at.neon.gui.model.MyContestProblem
import at.neon.gui.panes.main.MainPane
import javafx.application.Application
import javafx.application.Platform
import javafx.scene.Scene
import javafx.stage.Stage
import java.io.IOException

class Main : Application() {

    @Throws(Exception::class)
    override fun start(primaryStage: Stage) {
        // TODO: Init own problem
        val problem = MyContestProblem()
        problem.read("/input.txt")
        val mainPane = MainPane<MyContestData>()
        mainPane.init(problem)

        val scene = Scene(mainPane)
        primaryStage.title = "Contest Base"
        primaryStage.scene = scene
        primaryStage.isMaximized = true
        // TODO is not necessary if all threads are closed properly
        primaryStage.setOnCloseRequest { e ->
            Platform.exit()
            System.exit(0)
        }

        primaryStage.show()
    }

    companion object {
        @Throws(IOException::class)
        @JvmStatic
        fun main(argv: Array<String>) {
            launch(Main::class.java, *argv)
        }
    }
}
