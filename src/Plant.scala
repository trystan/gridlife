import scala.util.Random
import scala.swing.Color
import scala.swing.Graphics2D

class Plant(x: Int, y: Int, color: Color) {

  def this(rng: Random) = this(
    rng.nextInt(200),
    rng.nextInt(200),
    new Color(128 + rng.nextInt(64), 128 + rng.nextInt(64), 128 + rng.nextInt(64)))

  def draw(g: Graphics2D): Unit = {
    g.setColor(color)
    g.fillRect(x * 4 + 1, y * 4 + 1, 2, 2)
  }
}