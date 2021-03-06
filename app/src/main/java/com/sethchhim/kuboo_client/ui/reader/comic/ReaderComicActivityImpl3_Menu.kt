package com.sethchhim.kuboo_client.ui.reader.comic

import android.annotation.SuppressLint
import android.view.MenuItem
import com.sethchhim.kuboo_client.Extensions.setStateDisabled
import com.sethchhim.kuboo_client.Extensions.setStateEnabled
import com.sethchhim.kuboo_client.Settings

@SuppressLint("Registered")
open class ReaderComicActivityImpl3_Menu : ReaderComicActivityImpl2_Content() {

    protected lateinit var aspectFillMenuItem: MenuItem
    protected lateinit var aspectFitMenuItem: MenuItem
    protected lateinit var fitWidthMenuItem: MenuItem
    protected lateinit var dualPaneMenuItem: MenuItem
    protected lateinit var mangaModeMenuItem: MenuItem
    protected lateinit var localMenuItem: MenuItem

    protected fun onSelectDualPane() {
        toggleDualPaneMode()
        updateDualPaneMenuItemState()
    }

    protected fun onSelectMangaMode() {
        toggleMangaMode()
        updateMangaModeMenuItemState()
    }

    protected fun setScaleType(menuItem: MenuItem, scaleType: Int) {
        menuItem.isChecked = true
        when (scaleType) {
            0 -> Settings.SCALE_TYPE = 0
            1 -> Settings.SCALE_TYPE = 1
            2 -> Settings.SCALE_TYPE = 2
        }
        sharedPrefsHelper.saveScaleType()
        viewPager.adapter?.notifyDataSetChanged()
    }

    protected fun updateDualPaneMenuItemState() = when (Settings.DUAL_PANE) {
        true -> dualPaneMenuItem.setStateEnabled(this@ReaderComicActivityImpl3_Menu)
        false -> dualPaneMenuItem.setStateDisabled(this@ReaderComicActivityImpl3_Menu)
    }

    protected fun updateMangaModeMenuItemState() = when (Settings.RTL) {
        true -> mangaModeMenuItem.setStateEnabled(this@ReaderComicActivityImpl3_Menu)
        false -> mangaModeMenuItem.setStateDisabled(this@ReaderComicActivityImpl3_Menu)
    }

}


