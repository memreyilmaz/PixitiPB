package com.example.pixiti.utils

import android.view.View

/*
 * Sets View visibility to Visible
 */
fun View.show() {
    this.visibility = View.VISIBLE
}

/*
 * Sets View visibility to Invisible
 */
fun View.hide() {
    this.visibility = View.INVISIBLE
}

/*
 * Sets View visibility to Gone
 */
fun View.dismiss() {
    this.visibility = View.GONE
}

/*
 * Sets View visibility due to condition
 */
fun View.showIf(statement: Boolean?) {
    this.visibility = if (statement != null && statement) View.VISIBLE else View.GONE
}

/*
 * Sets View visibility to Gone if view is shown else Visible
 */
fun View.showIfNotVisible() {
    this.visibility = if (this.visibility == View.VISIBLE) View.GONE else View.VISIBLE
}

/*
 * Sets View selected status to true
 */
fun View.select(){
    this.isSelected = true
}

/*
 * Sets View selected status to false
 */
fun View.unSelect(){
    this.isSelected = false
}