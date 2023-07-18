package com.example.horsegame

import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private var widht_bonus = 0
    private var cellSelected_x = 0
    private var cellSelected_y = 0
    private var levelMoves = 64

    private var moves = 64
    private var movesRequired = 4
    private var options = 0
    private var bonus = 0


    private var nameColorBlack = "black_cell"
    private var nameColorWhite = "white_cell"
    private lateinit var board: Array<IntArray>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initScreenGame()
        resetboard()
        setFirstPosition()
    }

    private fun setFirstPosition() {

        val x: Int = (0..7).random()
        val y: Int = (0..7).random()
        cellSelected_x = x
        cellSelected_y = y
        selectCell(x, y)
    }

    fun checkChellClick(v: View) {
        val name = v.tag.toString()
        val x = name.subSequence(1, 2).toString().toInt()
        val y = name.subSequence(2, 3).toString().toInt()

        checkCell(x, y)
        board
    }

    private fun checkCell(x: Int, y: Int) {
        val dif_x = x - cellSelected_x
        val dif_y = y - cellSelected_y

        val validMoves = setOf(
            Pair(1, 2), Pair(1, -2), Pair(2, 1), Pair(2, -1),
            Pair(-1, 2), Pair(-1, -2), Pair(-2, 1), Pair(-2, -1)
        )

        if (Pair(dif_x, dif_y) in validMoves && board[x][y] != 1) {
            selectCell(x, y)
        }
    }

    private fun resetboard() {

        // 0 esta libre
        //1 casilla marcada
        //2 es de un bonus
        //9 es una opcion del movimiento actual
        board = arrayOf(
            intArrayOf(0, 0, 0, 0, 0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0, 0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0, 0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0, 0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0, 0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0, 0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0, 0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0, 0, 0, 0, 0)

        )

    }

    private fun initScreenGame() {
        setSizeBoard()
        hice_message()
    }


    private fun selectCell(x: Int, y: Int) {
        moves--
        var tvMovesData = findViewById<TextView>(R.id.tvMovesData)
        tvMovesData.text = moves.toString()

        growProgressBonus()
        if (board[x][y] == 2) {
            bonus++
            var tvBonusData = findViewById<TextView>(R.id.tvBonusData)
            tvBonusData.text = " + $bonus "
        }


        board[x][y] = 1
        paintHorseCell(cellSelected_x, cellSelected_y, "previus_cell")

        cellSelected_x = x
        cellSelected_y = y

        clearOptions()
        paintHorseCell(x, y, "selected_cell")

        checkOption(x, y)
        if (moves > 0) {
            checkNewBonus()
            //  checkGameOver(x,y)
        } else {
            //    checkSuccesFulEnd
        }
    }

    private fun growProgressBonus() {
        var moves_done = levelMoves - moves
        var bonus_done = moves_done / movesRequired
        var moves_rest = movesRequired * (bonus_done)
        var bonus_grow = moves_done - moves_rest
        var v = findViewById<View>(R.id.vNewBonus)
        var widthBonus = ((widht_bonus / movesRequired )* bonus_grow).toFloat()

        var height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,8f,getResources().getDisplayMetrics()).toInt()
        var width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,widthBonus,getResources().getDisplayMetrics()).toInt()
        v.setLayoutParams(TableRow.LayoutParams(width,height))

    }

    private fun checkNewBonus() {
        if (moves % movesRequired == 0) {
            var bonusCell_x = 0
            var bonusCell_y = 0

            var bonusCell = false
            while (bonusCell == false) {
                bonusCell_x = (0..7).random()
                bonusCell_y = (0..7).random()

                if (board[bonusCell_x][bonusCell_y] == 0) bonusCell = true

            }
            board[bonusCell_x][bonusCell_y] = 2
            paintBonusCell(bonusCell_x, bonusCell_y)
        }
    }

    private fun paintBonusCell(x: Int, y: Int) {
        var iv: ImageView =
            findViewById<ImageView>(resources.getIdentifier("c$x$y", "id", packageName))
        iv.setImageResource(R.drawable.alcohol_verde)
    }

    private fun clearOptions() {
        for (i in 0..7) {
            for (j in 0..7) {
                if (board[i][j] == 9 || board[i][j] == 2) {
                    if (board[i][j] == 9) board[i][j] = 0
                    clearOption(i, j)
                }
            }
        }
    }

    private fun clearOption(x: Int, y: Int) {
        var iv: ImageView = findViewById(resources.getIdentifier("c$x$y", "id", packageName))
        if (checkColorCell(x, y) == "black")
            iv.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    resources.getIdentifier(nameColorBlack, "color", packageName)
                )
            )
        else
            iv.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    resources.getIdentifier(nameColorWhite, "color", packageName)
                )
            )
        if (board[x][y] == 1) iv.setBackgroundColor(
            ContextCompat.getColor(
                this,
                resources.getIdentifier("previus_cell", "color", packageName)
            )
        )
    }

    private fun checkOption(x: Int, y: Int) {
        options = 0
        checkMove(x, y, 1, 2)
        checkMove(x, y, 2, 1)
        checkMove(x, y, 1, -2)
        checkMove(x, y, 2, -1)
        checkMove(x, y, -1, 2)
        checkMove(x, y, -2, 1)
        checkMove(x, y, -1, -2)
        checkMove(x, y, -2, -1)
        val tvOptionsData = findViewById<TextView>(R.id.tvOptionsData)
        tvOptionsData.text = options.toString()
    }

    private fun checkMove(x: Int, y: Int, mov_x: Int, mov_y: Int) {


        var option_x = x + mov_x
        var option_y = y + mov_y

        if (option_x < 8 && option_y < 8 && option_x >= 0 && option_y >= 0) {
            if (board[option_x][option_y] == 0 || board[option_x][option_y] == 2) {
                options++
                paintOptions(option_x, option_y)
                if (board[option_x][option_y] == 0) board[option_x][option_y] = 9
            }
        }

    }

    private fun paintOptions(x: Int, y: Int) {
        val iv: ImageView = findViewById(resources.getIdentifier("c$x$y", "id", packageName))
        if (checkColorCell(x, y) == "black") iv.setBackgroundResource(R.drawable.option_black)
        else iv.setBackgroundResource(R.drawable.option_white)
    }

    private fun checkColorCell(x: Int, y: Int): String {
        var color = ""
        val blackColumn_x = arrayOf(0, 2, 4, 6)
        val blackRow_x = arrayOf(1, 3, 5, 7)
        color = if (blackColumn_x.contains(x) && blackColumn_x.contains(y)
            || (blackRow_x.contains(x) && blackRow_x.contains(y))
        ) "black"
        else "white"

        return color

    }

    private fun paintHorseCell(x: Int, y: Int, color: String) {
        val iv: ImageView = findViewById(resources.getIdentifier("c$x$y", "id", packageName))
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
        widht_bonus = 2 * width_cell.toInt()
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