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

    val climateHistoryPanel = new ClimateHistoryPanel {
        size = new Dimension(200, 100)
        preferredSize = new Dimension(200, 100)
        minimumSize = new Dimension(200, 100)
      }
    
    val selectedPlantInfoPanel = new SelectedPlantInfoPanel {
        size = new Dimension(600, 100)
        preferredSize = new Dimension(600, 100)
        minimumSize = new Dimension(600, 100)
    }

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
              selectedPlantInfoPanel.select(world.plantAt(e.point.x / 4, e.point.y / 4))
          }
        }
        contents += new BoxPanel(Orientation.Horizontal) {
          contents += climateHistoryPanel
          contents += selectedPlantInfoPanel
        }
      }
    }

    def update = {
      world.update
      climateHistoryPanel.update(world)
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
