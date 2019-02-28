package at.neon.gui.panes.main

import at.neon.gui.controls.neonwebview.VueNeonWebViewBridge
import at.neon.gui.model.MyContestData

class MainWebViewBridge : VueNeonWebViewBridge() {
    fun setData(data: List<MyContestData>) {
        setVue("data", data)
    }

    fun setData2D(data: List<List<Int>>) {
        setVue("data2dr", data.size)
        setVue("data2dc", data[0].size)
        setVue("data2d", data.flatMap { it })
    }
}