package com.example.horsegame

import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TableRow
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private var cellSelected_x = 0
    private var cellSelecd_y = 0

    private lateinit var board: Array<IntArray>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initScreenGame()
        resetboard()
        setFirstPosition()
    }

    fun checkChellClick(v: View) {
        var name = v.tag.toString()
        var x= name.subSequence(1,2).toString().toInt()
        var y= name.subSequence(2,3).toString().toInt()

        selectCell(x,y)
    }

    private fun resetboard() {
        board = arrayOf(
            intArrayOf(0, 0, 0, 0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0, 0, 0, 0)

        )
    }

    private fun initScreenGame() {
        setSizeBoard()
        hice_message()
    }

    private fun setFirstPosition() {
        var x = 0
        var y = 0

        x = (0..7).random()
        y = (0..7).random()
        cellSelected_x = 0
        cellSelecd_y = 0
        selectCell(x, y)
    }

    private fun selectCell(x: Int, y: Int) {

        board[x][y] = 1
        paintHorseCell(cellSelected_x, cellSelecd_y, "previus_cell")

        cellSelected_x = x
        cellSelecd_y = y

        paintHorseCell(x, y, "selected_cell")
    }

    private fun paintHorseCell(x: Int, y: Int, color: String) {
        var iv: ImageView = findViewById(
            resources.getIdentifier(
                "c$x$y", "id",
                packageName
            )
        )
        iv.setBackgroundColor(
            ContextCompat.getColor(
                this,
                resources.getIdentifier(color, "color", packageName)
            )
        )
        iv.setImageResource(R.drawable.bojackhorse)

    }

    private fun setSizeBoard() {
        var iv: ImageView
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val width = size.x
        val width_dp = (width / getResources().getDisplayMetrics().density)

        var lateralMarginDP = 0
        val width_cell = (width_dp - lateralMarginDP) / 8
        val height_cell = width_cell

        for (i in 0..7) {
            for (j in 0..7) {
                iv = findViewById(
                    resources.getIdentifier(
                        "c$i$j", "id",
                        packageName
                    )
                )

                var height = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    height_cell,
                    getResources().getDisplayMetrics()
                ).toInt()

                var width = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    width_cell,
                    getResources().getDisplayMetrics()
                ).toInt()
                iv.setLayoutParams(TableRow.LayoutParams(width, height))
            }
        }
    }

    private fun hice_message() {
        var lyMessage = findViewById<LinearLayout>(R.id.lyMessage)
        lyMessage.visibility = View.INVISIBLE
    }

}