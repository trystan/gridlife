package com.trystan.gridlife.core

import scala.util.Random

class Climate(width: Int, height: Int, rng: Random) {

  var perlinTime = rng.nextInt(2000) * 1.0
  var cycleTime = 0.0

  var grid = Array.ofDim[Int](width, height)

  def shortCycle = Math.sin(Math.Pi * 2 * cycleTime / 11) * 0.5
  def mediumCycle = Math.sin(Math.Pi * 2 * cycleTime / 197) * 0.75
  def longCycle = Math.sin(Math.Pi * 2 * cycleTime / 499)
  def cycles = shortCycle + mediumCycle + longCycle

  def climateAt(x: Int, y: Int): Int = {
    return Math.max(0, Math.min(grid(x)(y), 8))
  }

  def update = {
    cycleTime += 0.01
    perlinTime += 0.001

    for (x <- 0 until width)
      for (y <- 0 until height)
        grid(x)(y) = ((ImprovedNoise.noise(x * 0.033, y * 0.033, perlinTime) + 1.0) * 4.5
          + cycles).toInt
  }
}