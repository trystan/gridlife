import scala.swing._
import scala.swing.event.ButtonClicked
import scala.swing.event.MouseClicked

object gridlife extends SimpleSwingApplication {

  var world = new World(200, 150)
  world.update

  def top = new MainFrame {
    title = "Grid Life"
    size = new Dimension(800, 700)
    preferredSize = new Dimension(800, 700)
    minimumSize = new Dimension(800, 700)

    val dnaLabel = new Label { text = " No plant selected" }

    var recentClimate = Vector.empty[Double]
    val climateSampleRate = 10
    var climateSampleCounter = 0

    contents = new BoxPanel(Orientation.Vertical) {
      {
        contents += new Panel {
          size = new Dimension(800, 600)
          preferredSize = new Dimension(800, 600)
          minimumSize = new Dimension(800, 600)
          override def paintComponent(g: Graphics2D) = {
            super.paintComponent(g)

            world.view.draw(g)
          }
          listenTo(mouse.clicks)
          reactions += {
            case e: MouseClicked =>
              select(world.plantAt(e.point.x / 4, e.point.y / 4))
          }
        }
        contents += new BoxPanel(Orientation.Horizontal) {
          contents += new Panel {
            size = new Dimension(200, 100)
            preferredSize = new Dimension(200, 100)
            minimumSize = new Dimension(200, 100)
            override def paintComponent(g: Graphics2D) = {
              super.paintComponent(g)
              g.drawString("Climate", 2, 12)
              for (t <- 0 until 200) {
                if (recentClimate.length > t + 1)
                  g.drawLine(t, (recentClimate(t) * -15 + 50).toInt, t + 1, (recentClimate(t + 1) * -15 + 50).toInt)
              }
            }
          }
          contents += new BoxPanel(Orientation.Vertical) {
            size = new Dimension(600, 100)
            preferredSize = new Dimension(600, 100)
            minimumSize = new Dimension(600, 100)
            contents += dnaLabel
          }
        }
      }
    }

    def select(plant: Plant) = {
      if (plant == null) {
        dnaLabel.text = "No plant selected"
      } else {
        dnaLabel.text = "<html>Location: " + plant.x + "," + plant.y + "\t\tClimate: " + (world.climateAt(plant.x, plant.y) + 1)
        dnaLabel.text += "<br/>Color: " + plant.color.getRed() + " " + plant.color.getGreen() + " " + plant.color.getBlue()
        dnaLabel.text += "<br/>Energy gained per climate: "
        for (i <- 0 until plant.energyPerClimate.length)
          dnaLabel.text += " " + plant.energyPerClimate(i)
        dnaLabel.text += "<br/>Spread: " + plant.spread
        dnaLabel.text += "</html>"
      }
    }

    def update = {
      world.update
      climateSampleCounter += 1

      if (climateSampleCounter == climateSampleRate) {
        climateSampleCounter = 0
        recentClimate = recentClimate :+ world.climate.cycles
        if (recentClimate.length > 200)
          recentClimate = recentClimate.tail
      }
      this.repaint
    }

    val timer = new javax.swing.Timer((1.0 / 30.0).toInt,
      new javax.swing.AbstractAction() {
        def actionPerformed(e: java.awt.event.ActionEvent) = update
      })
    timer.setRepeats(true)
    timer.start()
  }
}
