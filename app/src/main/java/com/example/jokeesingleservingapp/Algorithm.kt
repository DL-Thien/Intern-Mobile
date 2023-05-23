package com.example.jokeesingleservingapp

import kotlin.random.Random

fun main() {
    val array = IntArray(5)
    val random = Random(100)
    for (i in array.indices) {
        array[i] = random.nextInt(100)
    }
    for (i in array.indices) {
        println("${array[i]}")
    }
    //Sap xep mang tang dan
    val sort: List<Int> = array.sorted().dropLast(1)
//    for (i in sort) {
//        print("$i\t")
//    }
    println()
    //sap xep mang giam dan
    val sortDescending = array.sortedDescending().dropLast(1)
//    for (i in sortDescending) {
//        print("$i\t")
//    }
//    println()
    // Tim so lon nhat trong mag
    println("${array.max()}")
    //Tim so nho nhat trong mang
    println("${array.min()}")
    // in ra tong cua 4 so nho nhat va 4 so lon nhat
    println("${sort.sum()} ${sortDescending.sum()}")
}