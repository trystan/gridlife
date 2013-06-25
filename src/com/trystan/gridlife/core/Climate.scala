package com.trystan.gridlife.core

import scala.util.Random

class Climate(width: Int, height: Int, rng: Random) {

  var perlinTime = rng.nextDouble()
  var cycleTime = rng.nextDouble()

  var grid = Array.ofDim[Int](width, height)

  def shortCycle = Math.sin(Math.Pi * 2 * cycleTime / 11) * 0.33
  def mediumCycle = Math.sin(Math.Pi * 2 * cycleTime / 197) * 0.66
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
        grid(x)(y) = (((ImprovedNoise.noise(x * 0.033, y * 0.033, perlinTime) + 1.0) * 5 - 1) + cycles).toInt
  }
}