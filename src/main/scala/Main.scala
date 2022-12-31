import com.indoorvivants.tauri.tauriAppsApi.*
import scalajs.js.Thenable.Implicits.*
import concurrent.ExecutionContext.Implicits.global

import org.scalajs.dom
import org.scalablytyped.runtime.StringDictionary

import com.raquo.laminar.api.L._
import com.indoorvivants.tauri.std.stdStrings.dialog

@main def hello =
  documentEvents.onDomContentLoaded.foreach { _ =>
    val appContainer = dom.document.querySelector("#appContainer")
    val agreed = Var(false)
    windowMod.appWindow.setTitle("Twitter argument simulator")
    val appElement = div(
      h1(
        fontSize := "6rem",
        "You ",
        child <-- agreed.signal.map {
          case true  => "agree"
          case false => "disagree"
        }
      ),
      button(
        fontSize := "5rem",
        "Well ackchually",
        onClick --> { _ =>
          dialogMod.ask("Are you absolutely sure?").collect { case true =>
            agreed.update(!_)
          }
        }
      )
    )
    render(appContainer, appElement)
  }(unsafeWindowOwner)
