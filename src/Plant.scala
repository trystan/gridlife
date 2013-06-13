import scala.util.Random
import scala.swing.Color
import scala.swing.Graphics2D

class Plant(var x: Int, var y: Int, color: Color) {

  var age = 0
  var energy = 1
  
  def this(rng: Random) = this(
    rng.nextInt(200),
    rng.nextInt(200),
    new Color(128 + rng.nextInt(64), 128 + rng.nextInt(64), 128 + rng.nextInt(64))
  )
  
  def alive = { age < 20 }
  
  def update(w: World) = {
    age += 1
    energy += 1
    reproduce(w)
  }
  
  def reproduce(w: World): Unit = {
    if (energy < 10) return
    
    energy -= 5
    w.addSeed(new Plant(x, y, color))
  }
  
  def draw(g: Graphics2D): Unit = {
    g.setColor(color)
    g.fillRect(x * 4 + 1, y * 4 + 1, 2, 2)
  }
}